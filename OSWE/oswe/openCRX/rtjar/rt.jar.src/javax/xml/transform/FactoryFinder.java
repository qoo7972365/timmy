/*     */ package javax.xml.transform;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class FactoryFinder
/*     */ {
/*     */   private static final String DEFAULT_PACKAGE = "com.sun.org.apache.xalan.internal.";
/*     */   private static boolean debug = false;
/*  58 */   private static final Properties cacheProps = new Properties();
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
/*  70 */   private static final SecuritySupport ss = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  77 */       String val = ss.getSystemProperty("jaxp.debug");
/*     */       
/*  79 */       debug = (val != null && !"false".equals(val));
/*     */     }
/*  81 */     catch (SecurityException se) {
/*  82 */       debug = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void dPrint(String msg) {
/*  87 */     if (debug) {
/*  88 */       System.err.println("JAXP: " + msg);
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
/* 107 */       if (cl == null) {
/* 108 */         if (useBSClsLoader) {
/* 109 */           return Class.forName(className, false, FactoryFinder.class.getClassLoader());
/*     */         }
/* 111 */         cl = ss.getContextClassLoader();
/* 112 */         if (cl == null) {
/* 113 */           throw new ClassNotFoundException();
/*     */         }
/*     */         
/* 116 */         return Class.forName(className, false, cl);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 121 */       return Class.forName(className, false, cl);
/*     */     
/*     */     }
/* 124 */     catch (ClassNotFoundException e1) {
/* 125 */       if (doFallback)
/*     */       {
/* 127 */         return Class.forName(className, false, FactoryFinder.class.getClassLoader());
/*     */       }
/*     */       
/* 130 */       throw e1;
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
/*     */   
/*     */   static <T> T newInstance(Class<T> type, String className, ClassLoader cl, boolean doFallback) throws TransformerFactoryConfigurationError {
/* 156 */     assert type != null;
/*     */     
/* 158 */     boolean useBSClsLoader = false;
/*     */     
/* 160 */     if (System.getSecurityManager() != null && 
/* 161 */       className != null && className.startsWith("com.sun.org.apache.xalan.internal.")) {
/* 162 */       cl = null;
/* 163 */       useBSClsLoader = true;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 168 */       Class<?> providerClass = getProviderClass(className, cl, doFallback, useBSClsLoader);
/* 169 */       if (!type.isAssignableFrom(providerClass)) {
/* 170 */         throw new ClassCastException(className + " cannot be cast to " + type.getName());
/*     */       }
/* 172 */       Object instance = providerClass.newInstance();
/*     */       
/* 174 */       if (debug) {
/* 175 */         dPrint("created new instance of " + providerClass + " using ClassLoader: " + cl);
/*     */       }
/*     */       
/* 178 */       return type.cast(instance);
/*     */     }
/* 180 */     catch (ClassNotFoundException x) {
/* 181 */       throw new TransformerFactoryConfigurationError(x, "Provider " + className + " not found");
/*     */     
/*     */     }
/* 184 */     catch (Exception x) {
/* 185 */       throw new TransformerFactoryConfigurationError(x, "Provider " + className + " could not be instantiated: " + x);
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
/*     */   static <T> T find(Class<T> type, String fallbackClassName) throws TransformerFactoryConfigurationError {
/* 206 */     assert type != null;
/*     */     
/* 208 */     String factoryId = type.getName();
/*     */     
/* 210 */     dPrint("find factoryId =" + factoryId);
/*     */     
/*     */     try {
/* 213 */       String systemProp = ss.getSystemProperty(factoryId);
/* 214 */       if (systemProp != null) {
/* 215 */         dPrint("found system property, value=" + systemProp);
/* 216 */         return newInstance(type, systemProp, null, true);
/*     */       }
/*     */     
/* 219 */     } catch (SecurityException se) {
/* 220 */       if (debug) se.printStackTrace();
/*     */     
/*     */     } 
/*     */     
/*     */     try {
/* 225 */       if (firstTime) {
/* 226 */         synchronized (cacheProps) {
/* 227 */           if (firstTime) {
/* 228 */             String configFile = ss.getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */             
/* 230 */             File f = new File(configFile);
/* 231 */             firstTime = false;
/* 232 */             if (ss.doesFileExist(f)) {
/* 233 */               dPrint("Read properties file " + f);
/* 234 */               cacheProps.load(ss.getFileInputStream(f));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 239 */       String factoryClassName = cacheProps.getProperty(factoryId);
/*     */       
/* 241 */       if (factoryClassName != null) {
/* 242 */         dPrint("found in $java.home/jaxp.properties, value=" + factoryClassName);
/* 243 */         return newInstance(type, factoryClassName, null, true);
/*     */       }
/*     */     
/* 246 */     } catch (Exception ex) {
/* 247 */       if (debug) ex.printStackTrace();
/*     */     
/*     */     } 
/*     */     
/* 251 */     T provider = findServiceProvider(type);
/* 252 */     if (provider != null) {
/* 253 */       return provider;
/*     */     }
/* 255 */     if (fallbackClassName == null) {
/* 256 */       throw new TransformerFactoryConfigurationError(null, "Provider for " + factoryId + " cannot be found");
/*     */     }
/*     */ 
/*     */     
/* 260 */     dPrint("loaded from fallback value: " + fallbackClassName);
/* 261 */     return newInstance(type, fallbackClassName, null, true);
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
/*     */   private static <T> T findServiceProvider(final Class<T> type) throws TransformerFactoryConfigurationError {
/*     */     try {
/* 275 */       return AccessController.doPrivileged(new PrivilegedAction<T>() {
/*     */             public T run() {
/* 277 */               ServiceLoader<T> serviceLoader = ServiceLoader.load(type);
/* 278 */               Iterator<T> iterator = serviceLoader.iterator();
/* 279 */               if (iterator.hasNext()) {
/* 280 */                 return iterator.next();
/*     */               }
/* 282 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 286 */     catch (ServiceConfigurationError e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 294 */       RuntimeException x = new RuntimeException("Provider for " + type + " cannot be created", e);
/*     */ 
/*     */       
/* 297 */       TransformerFactoryConfigurationError error = new TransformerFactoryConfigurationError(x, x.getMessage());
/* 298 */       throw error;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/FactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */