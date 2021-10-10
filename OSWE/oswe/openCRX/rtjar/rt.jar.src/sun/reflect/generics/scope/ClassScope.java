/*    */ package sun.reflect.generics.scope;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class ClassScope
/*    */   extends AbstractScope<Class<?>>
/*    */   implements Scope
/*    */ {
/*    */   private ClassScope(Class<?> paramClass) {
/* 40 */     super(paramClass);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Scope computeEnclosingScope() {
/* 48 */     Class<?> clazz1 = getRecvr();
/*    */     
/* 50 */     Method method = clazz1.getEnclosingMethod();
/* 51 */     if (method != null)
/*    */     {
/*    */       
/* 54 */       return MethodScope.make(method);
/*    */     }
/* 56 */     Constructor<?> constructor = clazz1.getEnclosingConstructor();
/* 57 */     if (constructor != null)
/*    */     {
/*    */       
/* 60 */       return ConstructorScope.make(constructor);
/*    */     }
/* 62 */     Class<?> clazz2 = clazz1.getEnclosingClass();
/*    */ 
/*    */     
/* 65 */     if (clazz2 != null)
/*    */     {
/*    */       
/* 68 */       return make(clazz2);
/*    */     }
/*    */ 
/*    */     
/* 72 */     return DummyScope.make();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ClassScope make(Class<?> paramClass) {
/* 81 */     return new ClassScope(paramClass);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/scope/ClassScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */