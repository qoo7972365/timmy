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
/*    */ 
/*    */ public class RuntimeMBeanException
/*    */   extends JMRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 5274912751982730171L;
/*    */   private RuntimeException runtimeException;
/*    */   
/*    */   public RuntimeMBeanException(RuntimeException paramRuntimeException) {
/* 55 */     this.runtimeException = paramRuntimeException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RuntimeMBeanException(RuntimeException paramRuntimeException, String paramString) {
/* 66 */     super(paramString);
/* 67 */     this.runtimeException = paramRuntimeException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RuntimeException getTargetException() {
/* 76 */     return this.runtimeException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 85 */     return this.runtimeException;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/RuntimeMBeanException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */