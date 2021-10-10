/*     */ package javax.xml.stream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FactoryConfigurationError
/*     */   extends Error
/*     */ {
/*     */   private static final long serialVersionUID = -2994412584589975744L;
/*     */   Exception nested;
/*     */   
/*     */   public FactoryConfigurationError() {}
/*     */   
/*     */   public FactoryConfigurationError(Exception e) {
/*  54 */     this.nested = e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FactoryConfigurationError(Exception e, String msg) {
/*  65 */     super(msg);
/*  66 */     this.nested = e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FactoryConfigurationError(String msg, Exception e) {
/*  77 */     super(msg);
/*  78 */     this.nested = e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FactoryConfigurationError(String msg) {
/*  87 */     super(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getException() {
/*  96 */     return this.nested;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCause() {
/* 103 */     return this.nested;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 112 */     String msg = super.getMessage();
/* 113 */     if (msg != null)
/* 114 */       return msg; 
/* 115 */     if (this.nested != null) {
/* 116 */       msg = this.nested.getMessage();
/* 117 */       if (msg == null)
/* 118 */         msg = this.nested.getClass().toString(); 
/*     */     } 
/* 120 */     return msg;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/FactoryConfigurationError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */