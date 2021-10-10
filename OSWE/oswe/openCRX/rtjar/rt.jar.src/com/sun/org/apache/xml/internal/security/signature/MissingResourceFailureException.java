/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MissingResourceFailureException
/*     */   extends XMLSignatureException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  41 */   private Reference uninitializedReference = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MissingResourceFailureException(String paramString, Reference paramReference) {
/*  50 */     super(paramString);
/*     */     
/*  52 */     this.uninitializedReference = paramReference;
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
/*     */   public MissingResourceFailureException(String paramString, Object[] paramArrayOfObject, Reference paramReference) {
/*  64 */     super(paramString, paramArrayOfObject);
/*     */     
/*  66 */     this.uninitializedReference = paramReference;
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
/*     */   public MissingResourceFailureException(String paramString, Exception paramException, Reference paramReference) {
/*  80 */     super(paramString, paramException);
/*     */     
/*  82 */     this.uninitializedReference = paramReference;
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
/*     */   public MissingResourceFailureException(String paramString, Object[] paramArrayOfObject, Exception paramException, Reference paramReference) {
/*  97 */     super(paramString, paramArrayOfObject, paramException);
/*     */     
/*  99 */     this.uninitializedReference = paramReference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReference(Reference paramReference) {
/* 109 */     this.uninitializedReference = paramReference;
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
/*     */   public Reference getReference() {
/* 122 */     return this.uninitializedReference;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/MissingResourceFailureException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */