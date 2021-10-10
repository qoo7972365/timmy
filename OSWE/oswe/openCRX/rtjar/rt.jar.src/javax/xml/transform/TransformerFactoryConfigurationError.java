/*     */ package javax.xml.transform;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformerFactoryConfigurationError
/*     */   extends Error
/*     */ {
/*     */   private static final long serialVersionUID = -6527718720676281516L;
/*     */   private Exception exception;
/*     */   
/*     */   public TransformerFactoryConfigurationError() {
/*  51 */     this.exception = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformerFactoryConfigurationError(String msg) {
/*  62 */     super(msg);
/*     */     
/*  64 */     this.exception = null;
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
/*     */   public TransformerFactoryConfigurationError(Exception e) {
/*  76 */     super(e.toString());
/*     */     
/*  78 */     this.exception = e;
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
/*     */   public TransformerFactoryConfigurationError(Exception e, String msg) {
/*  91 */     super(msg);
/*     */     
/*  93 */     this.exception = e;
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
/*     */   public String getMessage() {
/* 105 */     String message = super.getMessage();
/*     */     
/* 107 */     if (message == null && this.exception != null) {
/* 108 */       return this.exception.getMessage();
/*     */     }
/*     */     
/* 111 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getException() {
/* 121 */     return this.exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCause() {
/* 128 */     return this.exception;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/TransformerFactoryConfigurationError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */