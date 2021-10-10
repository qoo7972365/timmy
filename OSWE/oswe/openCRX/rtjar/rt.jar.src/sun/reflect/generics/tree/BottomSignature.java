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
/*    */ public class BottomSignature
/*    */   implements FieldTypeSignature
/*    */ {
/* 31 */   private static final BottomSignature singleton = new BottomSignature();
/*    */ 
/*    */   
/*    */   public static BottomSignature make() {
/* 35 */     return singleton;
/*    */   } public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 37 */     paramTypeTreeVisitor.visitBottomSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/BottomSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */