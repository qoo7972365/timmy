/*     */ package javax.xml.bind;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeConstraintException
/*     */   extends RuntimeException
/*     */ {
/*     */   private String errorCode;
/*     */   private volatile Throwable linkedException;
/*     */   static final long serialVersionUID = -3059799699420143848L;
/*     */   
/*     */   public TypeConstraintException(String message) {
/*  71 */     this(message, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeConstraintException(String message, String errorCode) {
/*  82 */     this(message, errorCode, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeConstraintException(Throwable exception) {
/*  92 */     this(null, null, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeConstraintException(String message, Throwable exception) {
/* 103 */     this(message, null, exception);
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
/*     */   public TypeConstraintException(String message, String errorCode, Throwable exception) {
/* 115 */     super(message);
/* 116 */     this.errorCode = errorCode;
/* 117 */     this.linkedException = exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getErrorCode() {
/* 126 */     return this.errorCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getLinkedException() {
/* 135 */     return this.linkedException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkedException(Throwable exception) {
/* 146 */     this.linkedException = exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 154 */     return (this.linkedException == null) ? super
/* 155 */       .toString() : (super
/* 156 */       .toString() + "\n - with linked exception:\n[" + this.linkedException
/* 157 */       .toString() + "]");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintStream s) {
/* 167 */     if (this.linkedException != null) {
/* 168 */       this.linkedException.printStackTrace(s);
/* 169 */       s.println("--------------- linked to ------------------");
/*     */     } 
/*     */     
/* 172 */     super.printStackTrace(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace() {
/* 181 */     printStackTrace(System.err);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/TypeConstraintException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */