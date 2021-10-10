/*     */ package com.sun.beans.finder;
/*     */ 
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClassFinder
/*     */ {
/*     */   public static Class<?> findClass(String paramString) throws ClassNotFoundException {
/*  59 */     ReflectUtil.checkPackageAccess(paramString);
/*     */     try {
/*  61 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  62 */       if (classLoader == null)
/*     */       {
/*  64 */         classLoader = ClassLoader.getSystemClassLoader();
/*     */       }
/*  66 */       if (classLoader != null) {
/*  67 */         return Class.forName(paramString, false, classLoader);
/*     */       }
/*     */     }
/*  70 */     catch (ClassNotFoundException classNotFoundException) {
/*     */     
/*  72 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */     
/*  75 */     return Class.forName(paramString);
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
/*     */   public static Class<?> findClass(String paramString, ClassLoader paramClassLoader) throws ClassNotFoundException {
/* 100 */     ReflectUtil.checkPackageAccess(paramString);
/* 101 */     if (paramClassLoader != null) {
/*     */       try {
/* 103 */         return Class.forName(paramString, false, paramClassLoader);
/* 104 */       } catch (ClassNotFoundException classNotFoundException) {
/*     */       
/* 106 */       } catch (SecurityException securityException) {}
/*     */     }
/*     */ 
/*     */     
/* 110 */     return findClass(paramString);
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
/*     */   
/*     */   public static Class<?> resolveClass(String paramString) throws ClassNotFoundException {
/* 137 */     return resolveClass(paramString, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> resolveClass(String paramString, ClassLoader paramClassLoader) throws ClassNotFoundException {
/* 169 */     Class<?> clazz = PrimitiveTypeMap.getType(paramString);
/* 170 */     return (clazz == null) ? 
/* 171 */       findClass(paramString, paramClassLoader) : clazz;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/ClassFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */