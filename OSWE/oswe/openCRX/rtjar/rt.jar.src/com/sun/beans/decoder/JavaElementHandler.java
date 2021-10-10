/*     */ package com.sun.beans.decoder;
/*     */ 
/*     */ import java.beans.XMLDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class JavaElementHandler
/*     */   extends ElementHandler
/*     */ {
/*     */   private Class<?> type;
/*     */   private ValueObject value;
/*     */   
/*     */   public void addAttribute(String paramString1, String paramString2) {
/*  72 */     if (!paramString1.equals("version"))
/*     */     {
/*  74 */       if (paramString1.equals("class")) {
/*     */         
/*  76 */         this.type = getOwner().findClass(paramString2);
/*     */       } else {
/*  78 */         super.addAttribute(paramString1, paramString2);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addArgument(Object paramObject) {
/*  89 */     getOwner().addObject(paramObject);
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
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ValueObject getValueObject() {
/* 112 */     if (this.value == null) {
/* 113 */       this.value = ValueObjectImpl.create(getValue());
/*     */     }
/* 115 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getValue() {
/* 125 */     Object object = getOwner().getOwner();
/* 126 */     if (this.type == null || isValid(object)) {
/* 127 */       return object;
/*     */     }
/* 129 */     if (object instanceof XMLDecoder) {
/* 130 */       XMLDecoder xMLDecoder = (XMLDecoder)object;
/* 131 */       object = xMLDecoder.getOwner();
/* 132 */       if (isValid(object)) {
/* 133 */         return object;
/*     */       }
/*     */     } 
/* 136 */     throw new IllegalStateException("Unexpected owner class: " + object.getClass().getName());
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
/*     */   private boolean isValid(Object paramObject) {
/* 149 */     return (paramObject == null || this.type.isInstance(paramObject));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/decoder/JavaElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */