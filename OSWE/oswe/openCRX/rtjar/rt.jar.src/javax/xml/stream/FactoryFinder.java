/*     */ package javax.xml.stream;
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
/*     */ class FactoryFinder
/*     */ {
/*     */   private static final String DEFAULT_PACKAGE = "com.sun.xml.internal.";
/*     */   private static boolean debug = false;
/*  56 */   private static final Properties cacheProps = new Properties();
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
/*  68 */   private static final SecuritySupport ss = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  75 */       String val = ss.getSystemProperty("jaxp.debug");
/*     */       
/*  77 */       debug = (val != null && !"false".equals(val));
/*     */     }
/*  79 */     catch (SecurityException se) {
/*  80 */       debug = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void dPrint(String msg) {
/*  85 */     if (debug) {
/*  86 */       System.err.println("JAXP: " + msg);
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
/*     */   private static Class getProviderClass(String className, ClassLoader cl, boolean doFallback, boolean useBSClsLoader) throws ClassNotFoundException {
/*     */     try {
/* 105 */       if (cl == null) {
/* 106 */         if (useBSClsLoader) {
/* 107 */           return Class.forName(className, false, FactoryFinder.class.getClassLoader());
/*     */         }
/* 109 */         cl = ss.getContextClassLoader();
/* 110 */         if (cl == null) {
/* 111 */           throw new ClassNotFoundException();
/*     */         }
/*     */         
/* 114 */         return Class.forName(className, false, cl);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 119 */       return Class.forName(className, false, cl);
/*     */     
/*     */     }
/* 122 */     catch (ClassNotFoundException e1) {
/* 123 */       if (doFallback)
/*     */       {
/* 125 */         return Class.forName(className, false, FactoryFinder.class.getClassLoader());
/*     */       }
/*     */       
/* 128 */       throw e1;
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
/*     */     
/* 181 */     if (System.getSecurityManager() != null && 
/* 182 */       className != null && className.startsWith("com.sun.xml.internal.")) {
/* 183 */       cl = null;
/* 184 */       useBSClsLoader = true;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 189 */       Class<?> providerClass = getProviderClass(className, cl, doFallback, useBSClsLoader);
/* 190 */       if (!type.isAssignableFrom(providerClass)) {
/* 191 */         throw new ClassCastException(className + " cannot be cast to " + type.getName());
/*     */       }
/* 193 */       Object instance = providerClass.newInstance();
/* 194 */       if (debug) {
/* 195 */         dPrint("created new instance of " + providerClass + " using ClassLoader: " + cl);
/*     */       }
/*     */       
/* 198 */       return type.cast(instance);
/*     */     }
/* 200 */     catch (ClassNotFoundException x) {
/* 201 */       throw new FactoryConfigurationError("Provider " + className + " not found", x);
/*     */     
/*     */     }
/* 204 */     catch (Exception x) {
/* 205 */       throw new FactoryConfigurationError("Provider " + className + " could not be instantiated: " + x, x);
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
/*     */   static <T> T find(Class<T> type, String fallbackClassName) throws FactoryConfigurationError {
/* 227 */     return find(type, type.getName(), null, fallbackClassName);
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
/*     */   static <T> T find(Class<T> type, String factoryId, ClassLoader cl, String fallbackClassName) throws FactoryConfigurationError {
/* 252 */     dPrint("find factoryId =" + factoryId);
/*     */ 
/*     */     
/*     */     try {
/*     */       String systemProp;
/*     */       
/* 258 */       if (type.getName().equals(factoryId)) {
/* 259 */         systemProp = ss.getSystemProperty(factoryId);
/*     */       } else {
/* 261 */         systemProp = System.getProperty(factoryId);
/*     */       } 
/* 263 */       if (systemProp != null) {
/* 264 */         dPrint("found system property, value=" + systemProp);
/* 265 */         return newInstance(type, systemProp, cl, true);
/*     */       }
/*     */     
/* 268 */     } catch (SecurityException se) {
/* 269 */       throw new FactoryConfigurationError("Failed to read factoryId '" + factoryId + "'", se);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     String configFile = null;
/*     */     try {
/* 277 */       if (firstTime) {
/* 278 */         synchronized (cacheProps) {
/* 279 */           if (firstTime) {
/* 280 */             configFile = ss.getSystemProperty("java.home") + File.separator + "lib" + File.separator + "stax.properties";
/*     */             
/* 282 */             File f = new File(configFile);
/* 283 */             firstTime = false;
/* 284 */             if (ss.doesFileExist(f)) {
/* 285 */               dPrint("Read properties file " + f);
/* 286 */               cacheProps.load(ss.getFileInputStream(f));
/*     */             } else {
/*     */               
/* 289 */               configFile = ss.getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */               
/* 291 */               f = new File(configFile);
/* 292 */               if (ss.doesFileExist(f)) {
/* 293 */                 dPrint("Read properties file " + f);
/* 294 */                 cacheProps.load(ss.getFileInputStream(f));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 300 */       String factoryClassName = cacheProps.getProperty(factoryId);
/*     */       
/* 302 */       if (factoryClassName != null) {
/* 303 */         dPrint("found in " + configFile + " value=" + factoryClassName);
/* 304 */         return newInstance(type, factoryClassName, cl, true);
/*     */       }
/*     */     
/* 307 */     } catch (Exception ex) {
/* 308 */       if (debug) ex.printStackTrace();
/*     */     
/*     */     } 
/* 311 */     if (type.getName().equals(factoryId)) {
/*     */       
/* 313 */       T provider = findServiceProvider(type, cl);
/* 314 */       if (provider != null) {
/* 315 */         return provider;
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 321 */       assert fallbackClassName == null;
/*     */     } 
/* 323 */     if (fallbackClassName == null) {
/* 324 */       throw new FactoryConfigurationError("Provider for " + factoryId + " cannot be found", null);
/*     */     }
/*     */ 
/*     */     
/* 328 */     dPrint("loaded from fallback value: " + fallbackClassName);
/* 329 */     return newInstance(type, fallbackClassName, cl, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T findServiceProvider(final Class<T> type, final ClassLoader cl) {
/*     */     try {
/* 341 */       return AccessController.doPrivileged(new PrivilegedAction<T>()
/*     */           {
/*     */             public T run() {
/*     */               ServiceLoader<T> serviceLoader;
/* 345 */               if (cl == null) {
/*     */                 
/* 347 */                 serviceLoader = ServiceLoader.load(type);
/*     */               } else {
/* 349 */                 serviceLoader = ServiceLoader.load(type, cl);
/*     */               } 
/* 351 */               Iterator<T> iterator = serviceLoader.iterator();
/* 352 */               if (iterator.hasNext()) {
/* 353 */                 return iterator.next();
/*     */               }
/* 355 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 359 */     catch (ServiceConfigurationError e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 367 */       RuntimeException x = new RuntimeException("Provider for " + type + " cannot be created", e);
/*     */ 
/*     */       
/* 370 */       FactoryConfigurationError error = new FactoryConfigurationError(x, x.getMessage());
/* 371 */       throw error;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/FactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */