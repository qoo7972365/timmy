/*     */ package com.sun.beans.finder;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ public final class FieldFinder
/*     */ {
/*     */   public static Field findField(Class<?> paramClass, String paramString) throws NoSuchFieldException {
/*  54 */     if (paramString == null) {
/*  55 */       throw new IllegalArgumentException("Field name is not set");
/*     */     }
/*  57 */     Field field = paramClass.getField(paramString);
/*  58 */     if (!Modifier.isPublic(field.getModifiers())) {
/*  59 */       throw new NoSuchFieldException("Field '" + paramString + "' is not public");
/*     */     }
/*  61 */     paramClass = field.getDeclaringClass();
/*  62 */     if (!Modifier.isPublic(paramClass.getModifiers()) || !ReflectUtil.isPackageAccessible(paramClass)) {
/*  63 */       throw new NoSuchFieldException("Field '" + paramString + "' is not accessible");
/*     */     }
/*  65 */     return field;
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
/*     */   public static Field findInstanceField(Class<?> paramClass, String paramString) throws NoSuchFieldException {
/*  79 */     Field field = findField(paramClass, paramString);
/*  80 */     if (Modifier.isStatic(field.getModifiers())) {
/*  81 */       throw new NoSuchFieldException("Field '" + paramString + "' is static");
/*     */     }
/*  83 */     return field;
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
/*     */   public static Field findStaticField(Class<?> paramClass, String paramString) throws NoSuchFieldException {
/*  97 */     Field field = findField(paramClass, paramString);
/*  98 */     if (!Modifier.isStatic(field.getModifiers())) {
/*  99 */       throw new NoSuchFieldException("Field '" + paramString + "' is not static");
/*     */     }
/* 101 */     return field;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/FieldFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */