/*    */ package sun.reflect.generics.scope;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
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
/*    */ public class ConstructorScope
/*    */   extends AbstractScope<Constructor<?>>
/*    */ {
/*    */   private ConstructorScope(Constructor<?> paramConstructor) {
/* 39 */     super(paramConstructor);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Class<?> getEnclosingClass() {
/* 45 */     return getRecvr().getDeclaringClass();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Scope computeEnclosingScope() {
/* 55 */     return ClassScope.make(getEnclosingClass());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ConstructorScope make(Constructor<?> paramConstructor) {
/* 65 */     return new ConstructorScope(paramConstructor);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/scope/ConstructorScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */