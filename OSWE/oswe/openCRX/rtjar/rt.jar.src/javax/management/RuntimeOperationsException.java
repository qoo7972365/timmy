/*    */ package javax.management;
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
/*    */ public class RuntimeOperationsException
/*    */   extends JMRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -8408923047489133588L;
/*    */   private RuntimeException runtimeException;
/*    */   
/*    */   public RuntimeOperationsException(RuntimeException paramRuntimeException) {
/* 54 */     this.runtimeException = paramRuntimeException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RuntimeOperationsException(RuntimeException paramRuntimeException, String paramString) {
/* 65 */     super(paramString);
/* 66 */     this.runtimeException = paramRuntimeException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RuntimeException getTargetException() {
/* 75 */     return this.runtimeException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 84 */     return this.runtimeException;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/RuntimeOperationsException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */