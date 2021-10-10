/*     */ package com.sun.corba.se.impl.util;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.corba.Bridge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JDKClassLoader
/*     */ {
/*  48 */   private static final JDKClassLoaderCache classCache = new JDKClassLoaderCache();
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final Bridge bridge = AccessController.<Bridge>doPrivileged(new PrivilegedAction<Bridge>()
/*     */       {
/*     */         public Object run() {
/*  55 */           return Bridge.get();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class loadClass(Class paramClass, String paramString) throws ClassNotFoundException {
/*     */     ClassLoader classLoader;
/*  64 */     if (paramString == null) {
/*  65 */       throw new NullPointerException();
/*     */     }
/*  67 */     if (paramString.length() == 0) {
/*  68 */       throw new ClassNotFoundException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     if (paramClass != null) {
/*  83 */       classLoader = paramClass.getClassLoader();
/*     */     } else {
/*  85 */       classLoader = bridge.getLatestUserDefinedLoader();
/*     */     } 
/*     */     
/*  88 */     Object object = classCache.createKey(paramString, classLoader);
/*     */     
/*  90 */     if (classCache.knownToFail(object)) {
/*  91 */       throw new ClassNotFoundException(paramString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  97 */       return Class.forName(paramString, false, classLoader);
/*  98 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */ 
/*     */       
/* 102 */       classCache.recordFailure(object);
/* 103 */       throw classNotFoundException;
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
/*     */   private static class JDKClassLoaderCache
/*     */   {
/*     */     public final void recordFailure(Object param1Object) {
/* 117 */       this.cache.put(param1Object, KNOWN_TO_FAIL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final Object createKey(String param1String, ClassLoader param1ClassLoader) {
/* 127 */       return new CacheKey(param1String, param1ClassLoader);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean knownToFail(Object param1Object) {
/* 133 */       return (this.cache.get(param1Object) == KNOWN_TO_FAIL);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 138 */     private final Map cache = Collections.synchronizedMap(new WeakHashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     private static final Object KNOWN_TO_FAIL = new Object();
/*     */     
/*     */     private JDKClassLoaderCache() {}
/*     */     
/*     */     private static class CacheKey
/*     */     {
/*     */       String className;
/*     */       ClassLoader loader;
/*     */       
/*     */       public CacheKey(String param2String, ClassLoader param2ClassLoader) {
/* 153 */         this.className = param2String;
/* 154 */         this.loader = param2ClassLoader;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public int hashCode() {
/* 160 */         if (this.loader == null) {
/* 161 */           return this.className.hashCode();
/*     */         }
/* 163 */         return this.className.hashCode() ^ this.loader.hashCode();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean equals(Object param2Object) {
/*     */         try {
/* 170 */           if (param2Object == null) {
/* 171 */             return false;
/*     */           }
/* 173 */           CacheKey cacheKey = (CacheKey)param2Object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           return (this.className.equals(cacheKey.className) && this.loader == cacheKey.loader);
/*     */         
/*     */         }
/* 186 */         catch (ClassCastException classCastException) {
/* 187 */           return false;
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/JDKClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */