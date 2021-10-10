/*     */ package javax.xml.parsers;
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
/*     */   static volatile boolean firstTime = true;
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
/*     */   
/*     */   static <T> T newInstance(Class<T> type, String className, ClassLoader cl, boolean doFallback) throws FactoryConfigurationError {
/* 152 */     return newInstance(type, className, cl, doFallback, false);
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
/*     */   static <T> T newInstance(Class<T> type, String className, ClassLoader cl, boolean doFallback, boolean useBSClsLoader) throws FactoryConfigurationError {
/* 178 */     assert type != null;
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
/* 200 */       throw new FactoryConfigurationError(x, "Provider " + className + " not found");
/*     */     
/*     */     }
/* 203 */     catch (Exception x) {
/* 204 */       throw new FactoryConfigurationError(x, "Provider " + className + " could not be instantiated: " + x);
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
/*     */   static <T> T find(Class<T> type, String fallbackClassName) throws FactoryConfigurationError {
/* 224 */     String factoryId = type.getName();
/* 225 */     dPrint("find factoryId =" + factoryId);
/*     */ 
/*     */     
/*     */     try {
/* 229 */       String systemProp = ss.getSystemProperty(factoryId);
/* 230 */       if (systemProp != null) {
/* 231 */         dPrint("found system property, value=" + systemProp);
/* 232 */         return newInstance(type, systemProp, null, true);
/*     */       }
/*     */     
/* 235 */     } catch (SecurityException se) {
/* 236 */       if (debug) se.printStackTrace();
/*     */     
/*     */     } 
/*     */     
/*     */     try {
/* 241 */       if (firstTime) {
/* 242 */         synchronized (cacheProps) {
/* 243 */           if (firstTime) {
/* 244 */             String configFile = ss.getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */             
/* 246 */             File f = new File(configFile);
/* 247 */             firstTime = false;
/* 248 */             if (ss.doesFileExist(f)) {
/* 249 */               dPrint("Read properties file " + f);
/* 250 */               cacheProps.load(ss.getFileInputStream(f));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 255 */       String factoryClassName = cacheProps.getProperty(factoryId);
/*     */       
/* 257 */       if (factoryClassName != null) {
/* 258 */         dPrint("found in $java.home/jaxp.properties, value=" + factoryClassName);
/* 259 */         return newInstance(type, factoryClassName, null, true);
/*     */       }
/*     */     
/* 262 */     } catch (Exception ex) {
/* 263 */       if (debug) ex.printStackTrace();
/*     */     
/*     */     } 
/*     */     
/* 267 */     T provider = findServiceProvider(type);
/* 268 */     if (provider != null) {
/* 269 */       return provider;
/*     */     }
/* 271 */     if (fallbackClassName == null) {
/* 272 */       throw new FactoryConfigurationError("Provider for " + factoryId + " cannot be found");
/*     */     }
/*     */ 
/*     */     
/* 276 */     dPrint("loaded from fallback value: " + fallbackClassName);
/* 277 */     return newInstance(type, fallbackClassName, null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T findServiceProvider(final Class<T> type) {
/*     */     try {
/* 289 */       return AccessController.doPrivileged(new PrivilegedAction<T>() {
/*     */             public T run() {
/* 291 */               ServiceLoader<T> serviceLoader = ServiceLoader.load(type);
/* 292 */               Iterator<T> iterator = serviceLoader.iterator();
/* 293 */               if (iterator.hasNext()) {
/* 294 */                 return iterator.next();
/*     */               }
/* 296 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 300 */     catch (ServiceConfigurationError e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 308 */       RuntimeException x = new RuntimeException("Provider for " + type + " cannot be created", e);
/*     */ 
/*     */       
/* 311 */       FactoryConfigurationError error = new FactoryConfigurationError(x, x.getMessage());
/* 312 */       throw error;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/parsers/FactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */