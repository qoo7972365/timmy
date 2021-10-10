/*     */ package com.sun.beans.decoder;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ArrayElementHandler
/*     */   extends NewElementHandler
/*     */ {
/*     */   private Integer length;
/*     */   
/*     */   public void addAttribute(String paramString1, String paramString2) {
/*  94 */     if (paramString1.equals("length")) {
/*  95 */       this.length = Integer.valueOf(paramString2);
/*     */     } else {
/*  97 */       super.addAttribute(paramString1, paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement() {
/* 107 */     if (this.length != null) {
/* 108 */       getValueObject();
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
/*     */   protected boolean isArgument() {
/* 122 */     return true;
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
/*     */   protected ValueObject getValueObject(Class<?> paramClass, Object[] paramArrayOfObject) {
/* 135 */     if (paramClass == null) {
/* 136 */       paramClass = Object.class;
/*     */     }
/* 138 */     if (this.length != null) {
/* 139 */       return ValueObjectImpl.create(Array.newInstance(paramClass, this.length.intValue()));
/*     */     }
/* 141 */     Object object = Array.newInstance(paramClass, paramArrayOfObject.length);
/* 142 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 143 */       Array.set(object, b, paramArrayOfObject[b]);
/*     */     }
/* 145 */     return ValueObjectImpl.create(object);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/decoder/ArrayElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */