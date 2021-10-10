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
/*    */ public class LongSignature
/*    */   implements BaseType
/*    */ {
/* 32 */   private static final LongSignature singleton = new LongSignature();
/*    */ 
/*    */   
/*    */   public static LongSignature make() {
/* 36 */     return singleton;
/*    */   } public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 38 */     paramTypeTreeVisitor.visitLongSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/LongSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */