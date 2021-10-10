/*    */ package sun.reflect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
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
/*    */ class BootstrapConstructorAccessorImpl
/*    */   extends ConstructorAccessorImpl
/*    */ {
/*    */   private final Constructor<?> constructor;
/*    */   
/*    */   BootstrapConstructorAccessorImpl(Constructor<?> paramConstructor) {
/* 38 */     this.constructor = paramConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object newInstance(Object[] paramArrayOfObject) throws IllegalArgumentException, InvocationTargetException {
/*    */     try {
/* 45 */       return UnsafeFieldAccessorImpl.unsafe
/* 46 */         .allocateInstance(this.constructor.getDeclaringClass());
/* 47 */     } catch (InstantiationException instantiationException) {
/* 48 */       throw new InvocationTargetException(instantiationException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/BootstrapConstructorAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */