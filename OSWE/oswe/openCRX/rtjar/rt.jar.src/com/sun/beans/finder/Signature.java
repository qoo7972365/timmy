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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Signature
/*     */ {
/*     */   private final Class<?> type;
/*     */   private final String name;
/*     */   private final Class<?>[] args;
/*     */   private volatile int code;
/*     */   
/*     */   Signature(Class<?> paramClass, Class<?>[] paramArrayOfClass) {
/*  49 */     this(paramClass, null, paramArrayOfClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Signature(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass) {
/*  60 */     this.type = paramClass;
/*  61 */     this.name = paramString;
/*  62 */     this.args = paramArrayOfClass;
/*     */   }
/*     */   
/*     */   Class<?> getType() {
/*  66 */     return this.type;
/*     */   }
/*     */   
/*     */   String getName() {
/*  70 */     return this.name;
/*     */   }
/*     */   
/*     */   Class<?>[] getArgs() {
/*  74 */     return this.args;
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
/*     */   public boolean equals(Object paramObject) {
/*  87 */     if (this == paramObject) {
/*  88 */       return true;
/*     */     }
/*  90 */     if (paramObject instanceof Signature) {
/*  91 */       Signature signature = (Signature)paramObject;
/*  92 */       return (isEqual(signature.type, this.type) && 
/*  93 */         isEqual(signature.name, this.name) && 
/*  94 */         isEqual(signature.args, this.args));
/*     */     } 
/*  96 */     return false;
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
/*     */   private static boolean isEqual(Object paramObject1, Object paramObject2) {
/* 109 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1
/*     */       
/* 111 */       .equals(paramObject2);
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
/*     */   private static boolean isEqual(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/* 124 */     if (paramArrayOfClass1 == null || paramArrayOfClass2 == null) {
/* 125 */       return (paramArrayOfClass1 == paramArrayOfClass2);
/*     */     }
/* 127 */     if (paramArrayOfClass1.length != paramArrayOfClass2.length) {
/* 128 */       return false;
/*     */     }
/* 130 */     for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/* 131 */       if (!isEqual(paramArrayOfClass1[b], paramArrayOfClass2[b])) {
/* 132 */         return false;
/*     */       }
/*     */     } 
/* 135 */     return true;
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
/*     */   public int hashCode() {
/* 150 */     if (this.code == 0) {
/* 151 */       int i = 17;
/* 152 */       i = addHashCode(i, this.type);
/* 153 */       i = addHashCode(i, this.name);
/*     */       
/* 155 */       if (this.args != null) {
/* 156 */         for (Class<?> clazz : this.args) {
/* 157 */           i = addHashCode(i, clazz);
/*     */         }
/*     */       }
/* 160 */       this.code = i;
/*     */     } 
/* 162 */     return this.code;
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
/*     */   private static int addHashCode(int paramInt, Object paramObject) {
/* 176 */     paramInt *= 37;
/* 177 */     return (paramObject != null) ? (paramInt + paramObject
/* 178 */       .hashCode()) : paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/Signature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */