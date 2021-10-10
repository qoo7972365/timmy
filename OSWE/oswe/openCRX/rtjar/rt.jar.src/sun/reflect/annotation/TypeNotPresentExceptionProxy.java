/*    */ package sun.reflect.annotation;
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
/*    */ public class TypeNotPresentExceptionProxy
/*    */   extends ExceptionProxy
/*    */ {
/*    */   private static final long serialVersionUID = 5565925172427947573L;
/*    */   String typeName;
/*    */   Throwable cause;
/*    */   
/*    */   public TypeNotPresentExceptionProxy(String paramString, Throwable paramThrowable) {
/* 41 */     this.typeName = paramString;
/* 42 */     this.cause = paramThrowable;
/*    */   }
/*    */   
/*    */   protected RuntimeException generateException() {
/* 46 */     return new TypeNotPresentException(this.typeName, this.cause);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/TypeNotPresentExceptionProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */