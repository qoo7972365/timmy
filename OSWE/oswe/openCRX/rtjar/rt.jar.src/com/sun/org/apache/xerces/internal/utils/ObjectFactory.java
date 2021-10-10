/*     */ package com.sun.org.apache.xerces.internal.utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectFactory
/*     */ {
/*     */   private static final String JAXP_INTERNAL = "com.sun.org.apache";
/*     */   private static final String STAX_INTERNAL = "com.sun.xml.internal";
/*  46 */   private static final boolean DEBUG = isDebugEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isDebugEnabled() {
/*     */     try {
/*  56 */       String val = SecuritySupport.getSystemProperty("xerces.debug");
/*     */       
/*  58 */       return (val != null && !"false".equals(val));
/*     */     }
/*  60 */     catch (SecurityException securityException) {
/*  61 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void debugPrintln(String msg) {
/*  66 */     if (DEBUG) {
/*  67 */       System.err.println("XERCES: " + msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader findClassLoader() throws ConfigurationError {
/*  78 */     if (System.getSecurityManager() != null)
/*     */     {
/*  80 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  84 */     ClassLoader context = SecuritySupport.getContextClassLoader();
/*  85 */     ClassLoader system = SecuritySupport.getSystemClassLoader();
/*     */     
/*  87 */     ClassLoader chain = system;
/*     */     while (true) {
/*  89 */       if (context == chain) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  98 */         ClassLoader current = ObjectFactory.class.getClassLoader();
/*     */         
/* 100 */         chain = system;
/*     */         while (true) {
/* 102 */           if (current == chain)
/*     */           {
/*     */             
/* 105 */             return system;
/*     */           }
/* 107 */           if (chain == null) {
/*     */             break;
/*     */           }
/* 110 */           chain = SecuritySupport.getParentClassLoader(chain);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 115 */         return current;
/*     */       } 
/*     */       
/* 118 */       if (chain == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       chain = SecuritySupport.getParentClassLoader(chain);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object newInstance(String className, boolean doFallback) throws ConfigurationError {
/* 140 */     if (System.getSecurityManager() != null) {
/* 141 */       return newInstance(className, null, doFallback);
/*     */     }
/* 143 */     return newInstance(className, 
/* 144 */         findClassLoader(), doFallback);
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
/*     */   public static Object newInstance(String className, ClassLoader cl, boolean doFallback) throws ConfigurationError {
/*     */     try {
/* 157 */       Class providerClass = findProviderClass(className, cl, doFallback);
/* 158 */       Object instance = providerClass.newInstance();
/* 159 */       if (DEBUG) debugPrintln("created new instance of " + providerClass + " using ClassLoader: " + cl);
/*     */       
/* 161 */       return instance;
/* 162 */     } catch (ClassNotFoundException x) {
/* 163 */       throw new ConfigurationError("Provider " + className + " not found", x);
/*     */     }
/* 165 */     catch (Exception x) {
/* 166 */       throw new ConfigurationError("Provider " + className + " could not be instantiated: " + x, x);
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
/*     */   public static Class findProviderClass(String className, boolean doFallback) throws ClassNotFoundException, ConfigurationError {
/* 179 */     return findProviderClass(className, 
/* 180 */         findClassLoader(), doFallback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class findProviderClass(String className, ClassLoader cl, boolean doFallback) throws ClassNotFoundException, ConfigurationError {
/*     */     Class<?> providerClass;
/* 191 */     SecurityManager security = System.getSecurityManager();
/* 192 */     if (security != null) {
/* 193 */       if (className.startsWith("com.sun.org.apache") || className
/* 194 */         .startsWith("com.sun.xml.internal")) {
/* 195 */         cl = null;
/*     */       } else {
/* 197 */         int lastDot = className.lastIndexOf(".");
/* 198 */         String packageName = className;
/* 199 */         if (lastDot != -1) packageName = className.substring(0, lastDot); 
/* 200 */         security.checkPackageAccess(packageName);
/*     */       } 
/*     */     }
/*     */     
/* 204 */     if (cl == null) {
/*     */       
/* 206 */       providerClass = Class.forName(className, false, ObjectFactory.class.getClassLoader());
/*     */     } else {
/*     */       try {
/* 209 */         providerClass = cl.loadClass(className);
/* 210 */       } catch (ClassNotFoundException x) {
/* 211 */         if (doFallback) {
/*     */           
/* 213 */           ClassLoader current = ObjectFactory.class.getClassLoader();
/* 214 */           if (current == null) {
/* 215 */             providerClass = Class.forName(className);
/* 216 */           } else if (cl != current) {
/* 217 */             cl = current;
/* 218 */             providerClass = cl.loadClass(className);
/*     */           } else {
/* 220 */             throw x;
/*     */           } 
/*     */         } else {
/* 223 */           throw x;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 228 */     return providerClass;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/utils/ObjectFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */