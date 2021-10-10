/*     */ package com.sun.org.apache.xml.internal.security.signature.reference;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceOctetStreamData
/*     */   implements ReferenceData
/*     */ {
/*     */   private InputStream octetStream;
/*     */   private String uri;
/*     */   private String mimeType;
/*     */   
/*     */   public ReferenceOctetStreamData(InputStream paramInputStream) {
/*  49 */     if (paramInputStream == null) {
/*  50 */       throw new NullPointerException("octetStream is null");
/*     */     }
/*  52 */     this.octetStream = paramInputStream;
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
/*     */   public ReferenceOctetStreamData(InputStream paramInputStream, String paramString1, String paramString2) {
/*  68 */     if (paramInputStream == null) {
/*  69 */       throw new NullPointerException("octetStream is null");
/*     */     }
/*  71 */     this.octetStream = paramInputStream;
/*  72 */     this.uri = paramString1;
/*  73 */     this.mimeType = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getOctetStream() {
/*  82 */     return this.octetStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/*  92 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 102 */     return this.mimeType;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/reference/ReferenceOctetStreamData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */