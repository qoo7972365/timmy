/*    */ package sun.reflect;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
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
/*    */ class DelegatingMethodAccessorImpl
/*    */   extends MethodAccessorImpl
/*    */ {
/*    */   private MethodAccessorImpl delegate;
/*    */   
/*    */   DelegatingMethodAccessorImpl(MethodAccessorImpl paramMethodAccessorImpl) {
/* 37 */     setDelegate(paramMethodAccessorImpl);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object invoke(Object paramObject, Object[] paramArrayOfObject) throws IllegalArgumentException, InvocationTargetException {
/* 43 */     return this.delegate.invoke(paramObject, paramArrayOfObject);
/*    */   }
/*    */   
/*    */   void setDelegate(MethodAccessorImpl paramMethodAccessorImpl) {
/* 47 */     this.delegate = paramMethodAccessorImpl;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/DelegatingMethodAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */