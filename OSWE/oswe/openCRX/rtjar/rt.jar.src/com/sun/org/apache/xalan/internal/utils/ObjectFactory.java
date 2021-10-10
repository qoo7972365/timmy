/*     */ package com.sun.org.apache.xalan.internal.utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectFactory
/*     */ {
/*     */   private static final String JAXP_INTERNAL = "com.sun.org.apache";
/*     */   private static final String STAX_INTERNAL = "com.sun.xml.internal";
/*     */   private static final boolean DEBUG = false;
/*     */   
/*     */   private static void debugPrintln(String msg) {}
/*     */   
/*     */   public static ClassLoader findClassLoader() {
/*  62 */     if (System.getSecurityManager() != null)
/*     */     {
/*  64 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  69 */     ClassLoader context = SecuritySupport.getContextClassLoader();
/*  70 */     ClassLoader system = SecuritySupport.getSystemClassLoader();
/*     */     
/*  72 */     ClassLoader chain = system;
/*     */     while (true) {
/*  74 */       if (context == chain) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  83 */         ClassLoader current = ObjectFactory.class.getClassLoader();
/*     */         
/*  85 */         chain = system;
/*     */         while (true) {
/*  87 */           if (current == chain)
/*     */           {
/*     */             
/*  90 */             return system;
/*     */           }
/*  92 */           if (chain == null) {
/*     */             break;
/*     */           }
/*  95 */           chain = SecuritySupport.getParentClassLoader(chain);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 100 */         return current;
/*     */       } 
/*     */       
/* 103 */       if (chain == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       chain = SecuritySupport.getParentClassLoader(chain);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 115 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object newInstance(String className, boolean doFallback) throws ConfigurationError {
/* 125 */     if (System.getSecurityManager() != null) {
/* 126 */       return newInstance(className, null, doFallback);
/*     */     }
/* 128 */     return newInstance(className, 
/* 129 */         findClassLoader(), doFallback);
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
/*     */   static Object newInstance(String className, ClassLoader cl, boolean doFallback) throws ConfigurationError {
/*     */     try {
/* 142 */       Class<?> providerClass = findProviderClass(className, cl, doFallback);
/* 143 */       Object instance = providerClass.newInstance();
/*     */ 
/*     */       
/* 146 */       return instance;
/* 147 */     } catch (ClassNotFoundException x) {
/* 148 */       throw new ConfigurationError("Provider " + className + " not found", x);
/*     */     }
/* 150 */     catch (Exception x) {
/* 151 */       throw new ConfigurationError("Provider " + className + " could not be instantiated: " + x, x);
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
/*     */   public static Class<?> findProviderClass(String className, boolean doFallback) throws ClassNotFoundException, ConfigurationError {
/* 164 */     return findProviderClass(className, 
/* 165 */         findClassLoader(), doFallback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> findProviderClass(String className, ClassLoader cl, boolean doFallback) throws ClassNotFoundException, ConfigurationError {
/*     */     Class<?> providerClass;
/* 177 */     SecurityManager security = System.getSecurityManager();
/*     */     try {
/* 179 */       if (security != null) {
/* 180 */         if (className.startsWith("com.sun.org.apache") || className
/* 181 */           .startsWith("com.sun.xml.internal")) {
/* 182 */           cl = null;
/*     */         } else {
/* 184 */           int lastDot = className.lastIndexOf(".");
/* 185 */           String packageName = className;
/* 186 */           if (lastDot != -1) packageName = className.substring(0, lastDot); 
/* 187 */           security.checkPackageAccess(packageName);
/*     */         } 
/*     */       }
/* 190 */     } catch (SecurityException e) {
/* 191 */       throw e;
/*     */     } 
/*     */ 
/*     */     
/* 195 */     if (cl == null) {
/* 196 */       providerClass = Class.forName(className, false, ObjectFactory.class.getClassLoader());
/*     */     } else {
/*     */       try {
/* 199 */         providerClass = cl.loadClass(className);
/* 200 */       } catch (ClassNotFoundException x) {
/* 201 */         if (doFallback) {
/*     */           
/* 203 */           ClassLoader current = ObjectFactory.class.getClassLoader();
/* 204 */           if (current == null) {
/* 205 */             providerClass = Class.forName(className);
/* 206 */           } else if (cl != current) {
/* 207 */             cl = current;
/* 208 */             providerClass = cl.loadClass(className);
/*     */           } else {
/* 210 */             throw x;
/*     */           } 
/*     */         } else {
/* 213 */           throw x;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     return providerClass;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/utils/ObjectFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */