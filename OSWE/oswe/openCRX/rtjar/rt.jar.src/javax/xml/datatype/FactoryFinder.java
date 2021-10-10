/*     */ package javax.xml.datatype;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FactoryFinder
/*     */ {
/*     */   private static final String DEFAULT_PACKAGE = "com.sun.org.apache.xerces.internal";
/*     */   private static boolean debug = false;
/*  55 */   private static final Properties cacheProps = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile boolean firstTime = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final SecuritySupport ss = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  74 */       String val = ss.getSystemProperty("jaxp.debug");
/*     */       
/*  76 */       debug = (val != null && !"false".equals(val));
/*     */     }
/*  78 */     catch (SecurityException se) {
/*  79 */       debug = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void dPrint(String msg) {
/*  84 */     if (debug) {
/*  85 */       System.err.println("JAXP: " + msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getProviderClass(String className, ClassLoader cl, boolean doFallback, boolean useBSClsLoader) throws ClassNotFoundException {
/*     */     try {
/* 104 */       if (cl == null) {
/* 105 */         if (useBSClsLoader) {
/* 106 */           return Class.forName(className, false, FactoryFinder.class.getClassLoader());
/*     */         }
/* 108 */         cl = ss.getContextClassLoader();
/* 109 */         if (cl == null) {
/* 110 */           throw new ClassNotFoundException();
/*     */         }
/*     */         
/* 113 */         return Class.forName(className, false, cl);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 118 */       return Class.forName(className, false, cl);
/*     */     
/*     */     }
/* 121 */     catch (ClassNotFoundException e1) {
/* 122 */       if (doFallback)
/*     */       {
/* 124 */         return Class.forName(className, false, FactoryFinder.class.getClassLoader());
/*     */       }
/*     */       
/* 127 */       throw e1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T newInstance(Class<T> type, String className, ClassLoader cl, boolean doFallback) throws DatatypeConfigurationException {
/* 151 */     return newInstance(type, className, cl, doFallback, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T newInstance(Class<T> type, String className, ClassLoader cl, boolean doFallback, boolean useBSClsLoader) throws DatatypeConfigurationException {
/* 177 */     assert type != null;
/*     */ 
/*     */     
/* 180 */     if (System.getSecurityManager() != null && 
/* 181 */       className != null && className.startsWith("com.sun.org.apache.xerces.internal")) {
/* 182 */       cl = null;
/* 183 */       useBSClsLoader = true;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 188 */       Class<?> providerClass = getProviderClass(className, cl, doFallback, useBSClsLoader);
/* 189 */       if (!type.isAssignableFrom(providerClass)) {
/* 190 */         throw new ClassCastException(className + " cannot be cast to " + type.getName());
/*     */       }
/* 192 */       Object instance = providerClass.newInstance();
/* 193 */       if (debug) {
/* 194 */         dPrint("created new instance of " + providerClass + " using ClassLoader: " + cl);
/*     */       }
/*     */       
/* 197 */       return type.cast(instance);
/*     */     }
/* 199 */     catch (ClassNotFoundException x) {
/* 200 */       throw new DatatypeConfigurationException("Provider " + className + " not found", x);
/*     */     
/*     */     }
/* 203 */     catch (Exception x) {
/* 204 */       throw new DatatypeConfigurationException("Provider " + className + " could not be instantiated: " + x, x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T find(Class<T> type, String fallbackClassName) throws DatatypeConfigurationException {
/* 225 */     String factoryId = type.getName();
/* 226 */     dPrint("find factoryId =" + factoryId);
/*     */ 
/*     */     
/*     */     try {
/* 230 */       String systemProp = ss.getSystemProperty(factoryId);
/* 231 */       if (systemProp != null) {
/* 232 */         dPrint("found system property, value=" + systemProp);
/* 233 */         return newInstance(type, systemProp, null, true);
/*     */       }
/*     */     
/* 236 */     } catch (SecurityException se) {
/* 237 */       if (debug) se.printStackTrace();
/*     */     
/*     */     } 
/*     */     
/*     */     try {
/* 242 */       if (firstTime) {
/* 243 */         synchronized (cacheProps) {
/* 244 */           if (firstTime) {
/* 245 */             String configFile = ss.getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */             
/* 247 */             File f = new File(configFile);
/* 248 */             firstTime = false;
/* 249 */             if (ss.doesFileExist(f)) {
/* 250 */               dPrint("Read properties file " + f);
/* 251 */               cacheProps.load(ss.getFileInputStream(f));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 256 */       String factoryClassName = cacheProps.getProperty(factoryId);
/*     */       
/* 258 */       if (factoryClassName != null) {
/* 259 */         dPrint("found in $java.home/jaxp.properties, value=" + factoryClassName);
/* 260 */         return newInstance(type, factoryClassName, null, true);
/*     */       }
/*     */     
/* 263 */     } catch (Exception ex) {
/* 264 */       if (debug) ex.printStackTrace();
/*     */     
/*     */     } 
/*     */     
/* 268 */     T provider = findServiceProvider(type);
/* 269 */     if (provider != null) {
/* 270 */       return provider;
/*     */     }
/* 272 */     if (fallbackClassName == null) {
/* 273 */       throw new DatatypeConfigurationException("Provider for " + factoryId + " cannot be found");
/*     */     }
/*     */ 
/*     */     
/* 277 */     dPrint("loaded from fallback value: " + fallbackClassName);
/* 278 */     return newInstance(type, fallbackClassName, null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T findServiceProvider(final Class<T> type) throws DatatypeConfigurationException {
/*     */     try {
/* 292 */       return AccessController.doPrivileged(new PrivilegedAction<T>() {
/*     */             public T run() {
/* 294 */               ServiceLoader<T> serviceLoader = ServiceLoader.load(type);
/* 295 */               Iterator<T> iterator = serviceLoader.iterator();
/* 296 */               if (iterator.hasNext()) {
/* 297 */                 return iterator.next();
/*     */               }
/* 299 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 303 */     catch (ServiceConfigurationError e) {
/* 304 */       DatatypeConfigurationException error = new DatatypeConfigurationException("Provider for " + type + " cannot be found", e);
/*     */ 
/*     */       
/* 307 */       throw error;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/datatype/FactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */