/*    */ package sun.reflect;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import sun.reflect.misc.ReflectUtil;
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
/*    */ class NativeMethodAccessorImpl
/*    */   extends MethodAccessorImpl
/*    */ {
/*    */   private final Method method;
/*    */   private DelegatingMethodAccessorImpl parent;
/*    */   private int numInvocations;
/*    */   
/*    */   NativeMethodAccessorImpl(Method paramMethod) {
/* 40 */     this.method = paramMethod;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object invoke(Object paramObject, Object[] paramArrayOfObject) throws IllegalArgumentException, InvocationTargetException {
/* 49 */     if (++this.numInvocations > ReflectionFactory.inflationThreshold() && 
/* 50 */       !ReflectUtil.isVMAnonymousClass(this.method.getDeclaringClass())) {
/*    */ 
/*    */       
/* 53 */       MethodAccessorImpl methodAccessorImpl = (MethodAccessorImpl)(new MethodAccessorGenerator()).generateMethod(this.method.getDeclaringClass(), this.method
/* 54 */           .getName(), this.method
/* 55 */           .getParameterTypes(), this.method
/* 56 */           .getReturnType(), this.method
/* 57 */           .getExceptionTypes(), this.method
/* 58 */           .getModifiers());
/* 59 */       this.parent.setDelegate(methodAccessorImpl);
/*    */     } 
/*    */     
/* 62 */     return invoke0(this.method, paramObject, paramArrayOfObject);
/*    */   }
/*    */   
/*    */   void setParent(DelegatingMethodAccessorImpl paramDelegatingMethodAccessorImpl) {
/* 66 */     this.parent = paramDelegatingMethodAccessorImpl;
/*    */   }
/*    */   
/*    */   private static native Object invoke0(Method paramMethod, Object paramObject, Object[] paramArrayOfObject);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/NativeMethodAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */