/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ContentTypeImpl
/*     */   implements ContentType
/*     */ {
/*     */   @NotNull
/*     */   private final String contentType;
/*     */   @NotNull
/*     */   private final String soapAction;
/*     */   private String accept;
/*     */   @Nullable
/*     */   private final String charset;
/*     */   private String boundary;
/*     */   private String boundaryParameter;
/*     */   private String rootId;
/*     */   private ContentType internalContentType;
/*     */   
/*     */   public ContentTypeImpl(String contentType) {
/*  45 */     this(contentType, null, null);
/*     */   }
/*     */   
/*     */   public ContentTypeImpl(String contentType, @Nullable String soapAction) {
/*  49 */     this(contentType, soapAction, null);
/*     */   }
/*     */   
/*     */   public ContentTypeImpl(String contentType, @Nullable String soapAction, @Nullable String accept) {
/*  53 */     this(contentType, soapAction, accept, null);
/*     */   }
/*     */   
/*     */   public ContentTypeImpl(String contentType, @Nullable String soapAction, @Nullable String accept, String charsetParam) {
/*  57 */     this.contentType = contentType;
/*  58 */     this.accept = accept;
/*  59 */     this.soapAction = getQuotedSOAPAction(soapAction);
/*  60 */     if (charsetParam == null) {
/*  61 */       String tmpCharset = null;
/*     */       try {
/*  63 */         this.internalContentType = new ContentType(contentType);
/*  64 */         tmpCharset = this.internalContentType.getParameter("charset");
/*  65 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*  68 */       this.charset = tmpCharset;
/*     */     } else {
/*  70 */       this.charset = charsetParam;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getCharSet() {
/*  80 */     return this.charset;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getQuotedSOAPAction(String soapAction) {
/*  85 */     if (soapAction == null || soapAction.length() == 0)
/*  86 */       return "\"\""; 
/*  87 */     if (soapAction.charAt(0) != '"' && soapAction.charAt(soapAction.length() - 1) != '"')
/*     */     {
/*  89 */       return "\"" + soapAction + "\"";
/*     */     }
/*  91 */     return soapAction;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/*  97 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSOAPActionHeader() {
/* 102 */     return this.soapAction;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAcceptHeader() {
/* 107 */     return this.accept;
/*     */   }
/*     */   
/*     */   public void setAcceptHeader(String accept) {
/* 111 */     this.accept = accept;
/*     */   }
/*     */   
/*     */   public String getBoundary() {
/* 115 */     if (this.boundary == null) {
/* 116 */       if (this.internalContentType == null) this.internalContentType = new ContentType(this.contentType); 
/* 117 */       this.boundary = this.internalContentType.getParameter("boundary");
/*     */     } 
/* 119 */     return this.boundary;
/*     */   }
/*     */   
/*     */   public void setBoundary(String boundary) {
/* 123 */     this.boundary = boundary;
/*     */   }
/*     */   
/*     */   public String getBoundaryParameter() {
/* 127 */     return this.boundaryParameter;
/*     */   }
/*     */   
/*     */   public void setBoundaryParameter(String boundaryParameter) {
/* 131 */     this.boundaryParameter = boundaryParameter;
/*     */   }
/*     */   
/*     */   public String getRootId() {
/* 135 */     if (this.rootId == null) {
/* 136 */       if (this.internalContentType == null) this.internalContentType = new ContentType(this.contentType); 
/* 137 */       this.rootId = this.internalContentType.getParameter("start");
/*     */     } 
/* 139 */     return this.rootId;
/*     */   }
/*     */   
/*     */   public void setRootId(String rootId) {
/* 143 */     this.rootId = rootId;
/*     */   }
/*     */   
/*     */   public static class Builder { public String contentType;
/*     */     public String soapAction;
/*     */     public String accept;
/*     */     public String charset;
/*     */     
/*     */     public ContentTypeImpl build() {
/* 152 */       return new ContentTypeImpl(this.contentType, this.soapAction, this.accept, this.charset);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/ContentTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */