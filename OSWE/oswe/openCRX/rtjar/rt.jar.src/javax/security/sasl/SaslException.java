/*     */ package javax.security.sasl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SaslException
/*     */   extends IOException
/*     */ {
/*     */   private Throwable _exception;
/*     */   private static final long serialVersionUID = 4579784287983423626L;
/*     */   
/*     */   public SaslException() {}
/*     */   
/*     */   public SaslException(String paramString) {
/*  63 */     super(paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SaslException(String paramString, Throwable paramThrowable) {
/*  83 */     super(paramString);
/*  84 */     if (paramThrowable != null) {
/*  85 */       initCause(paramThrowable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCause() {
/*  94 */     return this._exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable initCause(Throwable paramThrowable) {
/* 102 */     super.initCause(paramThrowable);
/* 103 */     this._exception = paramThrowable;
/* 104 */     return this;
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
/*     */   public String toString() {
/* 120 */     String str = super.toString();
/* 121 */     if (this._exception != null && this._exception != this) {
/* 122 */       str = str + " [Caused by " + this._exception.toString() + "]";
/*     */     }
/* 124 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/sasl/SaslException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */