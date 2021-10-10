/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UndeclaredThrowableException
/*     */   extends RuntimeException
/*     */ {
/*     */   static final long serialVersionUID = 330127114055056639L;
/*     */   private Throwable undeclaredThrowable;
/*     */   
/*     */   public UndeclaredThrowableException(Throwable paramThrowable) {
/*  75 */     super((Throwable)null);
/*  76 */     this.undeclaredThrowable = paramThrowable;
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
/*     */   public UndeclaredThrowableException(Throwable paramThrowable, String paramString) {
/*  90 */     super(paramString, null);
/*  91 */     this.undeclaredThrowable = paramThrowable;
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
/*     */   public Throwable getUndeclaredThrowable() {
/* 105 */     return this.undeclaredThrowable;
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
/*     */   public Throwable getCause() {
/* 117 */     return this.undeclaredThrowable;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/UndeclaredThrowableException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */