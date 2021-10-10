/*     */ package com.sun.beans.finder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InstanceFinder<T>
/*     */ {
/*  37 */   private static final String[] EMPTY = new String[0];
/*     */   
/*     */   private final Class<? extends T> type;
/*     */   private final boolean allow;
/*     */   private final String suffix;
/*     */   private volatile String[] packages;
/*     */   
/*     */   InstanceFinder(Class<? extends T> paramClass, boolean paramBoolean, String paramString, String... paramVarArgs) {
/*  45 */     this.type = paramClass;
/*  46 */     this.allow = paramBoolean;
/*  47 */     this.suffix = paramString;
/*  48 */     this.packages = (String[])paramVarArgs.clone();
/*     */   }
/*     */   
/*     */   public String[] getPackages() {
/*  52 */     return (String[])this.packages.clone();
/*     */   }
/*     */   
/*     */   public void setPackages(String... paramVarArgs) {
/*  56 */     this
/*  57 */       .packages = (paramVarArgs != null && paramVarArgs.length > 0) ? (String[])paramVarArgs.clone() : EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public T find(Class<?> paramClass) {
/*  62 */     if (paramClass == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     String str = paramClass.getName() + this.suffix;
/*  66 */     T t = instantiate(paramClass, str);
/*  67 */     if (t != null) {
/*  68 */       return t;
/*     */     }
/*  70 */     if (this.allow) {
/*  71 */       t = instantiate(paramClass, null);
/*  72 */       if (t != null) {
/*  73 */         return t;
/*     */       }
/*     */     } 
/*  76 */     int i = str.lastIndexOf('.') + 1;
/*  77 */     if (i > 0) {
/*  78 */       str = str.substring(i);
/*     */     }
/*  80 */     for (String str1 : this.packages) {
/*  81 */       t = instantiate(paramClass, str1, str);
/*  82 */       if (t != null) {
/*  83 */         return t;
/*     */       }
/*     */     } 
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   protected T instantiate(Class<?> paramClass, String paramString) {
/*  90 */     if (paramClass != null) {
/*     */       try {
/*  92 */         if (paramString != null) {
/*  93 */           paramClass = ClassFinder.findClass(paramString, paramClass.getClassLoader());
/*     */         }
/*  95 */         if (this.type.isAssignableFrom(paramClass)) {
/*  96 */           return (T)paramClass.newInstance();
/*     */         }
/*     */       }
/*  99 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 103 */     return null;
/*     */   }
/*     */   
/*     */   protected T instantiate(Class<?> paramClass, String paramString1, String paramString2) {
/* 107 */     return instantiate(paramClass, paramString1 + '.' + paramString2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/InstanceFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */