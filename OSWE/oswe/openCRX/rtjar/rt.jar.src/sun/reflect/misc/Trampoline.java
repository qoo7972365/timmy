/*    */ package sun.reflect.misc;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
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
/*    */ class Trampoline
/*    */ {
/*    */   static {
/* 51 */     if (Trampoline.class.getClassLoader() == null) {
/* 52 */       throw new Error("Trampoline must not be defined by the bootstrap classloader");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void ensureInvocableMethod(Method paramMethod) throws InvocationTargetException {
/* 60 */     Class<?> clazz = paramMethod.getDeclaringClass();
/* 61 */     if (clazz.equals(AccessController.class) || clazz
/* 62 */       .equals(Method.class) || clazz
/* 63 */       .getName().startsWith("java.lang.invoke.")) {
/* 64 */       throw new InvocationTargetException(new UnsupportedOperationException("invocation not supported"));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Object invoke(Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws InvocationTargetException, IllegalAccessException {
/* 71 */     ensureInvocableMethod(paramMethod);
/* 72 */     return paramMethod.invoke(paramObject, paramArrayOfObject);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/misc/Trampoline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */