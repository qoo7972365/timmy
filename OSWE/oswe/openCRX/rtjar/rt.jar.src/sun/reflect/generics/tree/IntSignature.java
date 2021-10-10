/*    */ package sun.reflect.generics.tree;
/*    */ 
/*    */ import sun.reflect.generics.visitor.TypeTreeVisitor;
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
/*    */ public class IntSignature
/*    */   implements BaseType
/*    */ {
/* 32 */   private static final IntSignature singleton = new IntSignature();
/*    */ 
/*    */   
/*    */   public static IntSignature make() {
/* 36 */     return singleton;
/*    */   } public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 38 */     paramTypeTreeVisitor.visitIntSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/IntSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */