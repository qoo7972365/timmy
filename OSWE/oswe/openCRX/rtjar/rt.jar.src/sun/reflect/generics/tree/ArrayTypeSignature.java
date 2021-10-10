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
/*    */ public class ArrayTypeSignature
/*    */   implements FieldTypeSignature
/*    */ {
/*    */   private final TypeSignature componentType;
/*    */   
/*    */   private ArrayTypeSignature(TypeSignature paramTypeSignature) {
/* 33 */     this.componentType = paramTypeSignature;
/*    */   }
/*    */   public static ArrayTypeSignature make(TypeSignature paramTypeSignature) {
/* 36 */     return new ArrayTypeSignature(paramTypeSignature);
/*    */   }
/*    */   public TypeSignature getComponentType() {
/* 39 */     return this.componentType;
/*    */   }
/*    */   public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 42 */     paramTypeTreeVisitor.visitArrayTypeSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/ArrayTypeSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */