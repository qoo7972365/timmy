/*     */ package com.sun.xml.internal.ws.api.model;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ParameterBinding
/*     */ {
/*  47 */   public static final ParameterBinding BODY = new ParameterBinding(Kind.BODY, null);
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final ParameterBinding HEADER = new ParameterBinding(Kind.HEADER, null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final ParameterBinding UNBOUND = new ParameterBinding(Kind.UNBOUND, null);
/*     */ 
/*     */   
/*     */   public final Kind kind;
/*     */ 
/*     */   
/*     */   private String mimeType;
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParameterBinding createAttachment(String mimeType) {
/*  67 */     return new ParameterBinding(Kind.ATTACHMENT, mimeType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Kind
/*     */   {
/*  74 */     BODY, HEADER, UNBOUND, ATTACHMENT;
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
/*     */   private ParameterBinding(Kind kind, String mimeType) {
/*  90 */     this.kind = kind;
/*  91 */     this.mimeType = mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  97 */     return this.kind.toString();
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
/*     */   public String getMimeType() {
/* 110 */     if (!isAttachment())
/* 111 */       throw new IllegalStateException(); 
/* 112 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public boolean isBody() {
/* 116 */     return (this == BODY);
/*     */   }
/*     */   
/*     */   public boolean isHeader() {
/* 120 */     return (this == HEADER);
/*     */   }
/*     */   
/*     */   public boolean isUnbound() {
/* 124 */     return (this == UNBOUND);
/*     */   }
/*     */   
/*     */   public boolean isAttachment() {
/* 128 */     return (this.kind == Kind.ATTACHMENT);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/ParameterBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */