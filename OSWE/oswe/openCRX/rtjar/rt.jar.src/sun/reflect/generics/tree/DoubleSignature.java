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
/*    */ public class DoubleSignature
/*    */   implements BaseType
/*    */ {
/* 32 */   private static final DoubleSignature singleton = new DoubleSignature();
/*    */ 
/*    */   
/*    */   public static DoubleSignature make() {
/* 36 */     return singleton;
/*    */   } public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 38 */     paramTypeTreeVisitor.visitDoubleSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/DoubleSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */