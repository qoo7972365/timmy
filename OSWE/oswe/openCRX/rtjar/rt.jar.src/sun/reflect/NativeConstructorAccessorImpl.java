/*    */ package sun.reflect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.InvocationTargetException;
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
/*    */ class NativeConstructorAccessorImpl
/*    */   extends ConstructorAccessorImpl
/*    */ {
/*    */   private final Constructor<?> c;
/*    */   private DelegatingConstructorAccessorImpl parent;
/*    */   private int numInvocations;
/*    */   
/*    */   NativeConstructorAccessorImpl(Constructor<?> paramConstructor) {
/* 40 */     this.c = paramConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object newInstance(Object[] paramArrayOfObject) throws InstantiationException, IllegalArgumentException, InvocationTargetException {
/* 51 */     if (++this.numInvocations > ReflectionFactory.inflationThreshold() && 
/* 52 */       !ReflectUtil.isVMAnonymousClass(this.c.getDeclaringClass())) {
/*    */ 
/*    */       
/* 55 */       ConstructorAccessorImpl constructorAccessorImpl = (ConstructorAccessorImpl)(new MethodAccessorGenerator()).generateConstructor(this.c.getDeclaringClass(), this.c
/* 56 */           .getParameterTypes(), this.c
/* 57 */           .getExceptionTypes(), this.c
/* 58 */           .getModifiers());
/* 59 */       this.parent.setDelegate(constructorAccessorImpl);
/*    */     } 
/*    */     
/* 62 */     return newInstance0(this.c, paramArrayOfObject);
/*    */   }
/*    */   
/*    */   void setParent(DelegatingConstructorAccessorImpl paramDelegatingConstructorAccessorImpl) {
/* 66 */     this.parent = paramDelegatingConstructorAccessorImpl;
/*    */   }
/*    */   
/*    */   private static native Object newInstance0(Constructor<?> paramConstructor, Object[] paramArrayOfObject) throws InstantiationException, IllegalArgumentException, InvocationTargetException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/NativeConstructorAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */