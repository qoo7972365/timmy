/*    */ package java.lang;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BootstrapMethodError
/*    */   extends LinkageError
/*    */ {
/*    */   private static final long serialVersionUID = 292L;
/*    */   
/*    */   public BootstrapMethodError() {}
/*    */   
/*    */   public BootstrapMethodError(String paramString) {
/* 55 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BootstrapMethodError(String paramString, Throwable paramThrowable) {
/* 66 */     super(paramString, paramThrowable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BootstrapMethodError(Throwable paramThrowable) {
/* 77 */     super((paramThrowable == null) ? null : paramThrowable.toString());
/* 78 */     initCause(paramThrowable);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/BootstrapMethodError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */