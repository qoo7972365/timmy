/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ContentType
/*     */ {
/*     */   private String primaryType;
/*     */   private String subType;
/*     */   private ParameterList list;
/*     */   
/*     */   public ContentType(String s) throws WebServiceException {
/*  59 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*     */ 
/*     */     
/*  63 */     HeaderTokenizer.Token tk = h.next();
/*  64 */     if (tk.getType() != -1)
/*  65 */       throw new WebServiceException(); 
/*  66 */     this.primaryType = tk.getValue();
/*     */ 
/*     */     
/*  69 */     tk = h.next();
/*  70 */     if ((char)tk.getType() != '/') {
/*  71 */       throw new WebServiceException();
/*     */     }
/*     */     
/*  74 */     tk = h.next();
/*  75 */     if (tk.getType() != -1)
/*  76 */       throw new WebServiceException(); 
/*  77 */     this.subType = tk.getValue();
/*     */ 
/*     */     
/*  80 */     String rem = h.getRemainder();
/*  81 */     if (rem != null) {
/*  82 */       this.list = new ParameterList(rem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrimaryType() {
/*  91 */     return this.primaryType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubType() {
/*  99 */     return this.subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseType() {
/* 110 */     return this.primaryType + '/' + this.subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String name) {
/* 121 */     if (this.list == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     return this.list.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterList getParameterList() {
/* 134 */     return this.list;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/ContentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */