/*     */ package com.sun.beans.decoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ElementHandler
/*     */ {
/*     */   private DocumentHandler owner;
/*     */   private ElementHandler parent;
/*     */   private String id;
/*     */   
/*     */   public final DocumentHandler getOwner() {
/*  48 */     return this.owner;
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
/*     */   final void setOwner(DocumentHandler paramDocumentHandler) {
/*  60 */     if (paramDocumentHandler == null) {
/*  61 */       throw new IllegalArgumentException("Every element should have owner");
/*     */     }
/*  63 */     this.owner = paramDocumentHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ElementHandler getParent() {
/*  72 */     return this.parent;
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
/*     */   final void setParent(ElementHandler paramElementHandler) {
/*  84 */     this.parent = paramElementHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object getVariable(String paramString) {
/*  94 */     if (paramString.equals(this.id)) {
/*  95 */       ValueObject valueObject = getValueObject();
/*  96 */       if (valueObject.isVoid()) {
/*  97 */         throw new IllegalStateException("The element does not return value");
/*     */       }
/*  99 */       return valueObject.getValue();
/*     */     } 
/* 101 */     return (this.parent != null) ? this.parent
/* 102 */       .getVariable(paramString) : this.owner
/* 103 */       .getVariable(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getContextBean() {
/* 112 */     if (this.parent != null) {
/* 113 */       ValueObject valueObject = this.parent.getValueObject();
/* 114 */       if (!valueObject.isVoid()) {
/* 115 */         return valueObject.getValue();
/*     */       }
/* 117 */       throw new IllegalStateException("The outer element does not return value");
/*     */     } 
/* 119 */     Object object = this.owner.getOwner();
/* 120 */     if (object != null) {
/* 121 */       return object;
/*     */     }
/* 123 */     throw new IllegalStateException("The topmost element does not have context");
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
/*     */   public void addAttribute(String paramString1, String paramString2) {
/* 139 */     if (paramString1.equals("id")) {
/* 140 */       this.id = paramString2;
/*     */     } else {
/* 142 */       throw new IllegalArgumentException("Unsupported attribute: " + paramString1);
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
/*     */   public void startElement() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement() {
/* 169 */     ValueObject valueObject = getValueObject();
/* 170 */     if (!valueObject.isVoid()) {
/* 171 */       if (this.id != null) {
/* 172 */         this.owner.setVariable(this.id, valueObject.getValue());
/*     */       }
/* 174 */       if (isArgument()) {
/* 175 */         if (this.parent != null) {
/* 176 */           this.parent.addArgument(valueObject.getValue());
/*     */         } else {
/* 178 */           this.owner.addObject(valueObject.getValue());
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCharacter(char paramChar) {
/* 191 */     if (paramChar != ' ' && paramChar != '\n' && paramChar != '\t' && paramChar != '\r') {
/* 192 */       throw new IllegalStateException("Illegal character with code " + paramChar);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addArgument(Object paramObject) {
/* 203 */     throw new IllegalStateException("Could not add argument to simple element");
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
/*     */   protected boolean isArgument() {
/* 215 */     return (this.id == null);
/*     */   }
/*     */   
/*     */   protected abstract ValueObject getValueObject();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/decoder/ElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */