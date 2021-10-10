/*    */ package sun.reflect.generics.tree;
/*    */ 
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassTypeSignature
/*    */   implements FieldTypeSignature
/*    */ {
/*    */   private final List<SimpleClassTypeSignature> path;
/*    */   
/*    */   private ClassTypeSignature(List<SimpleClassTypeSignature> paramList) {
/* 40 */     this.path = paramList;
/*    */   }
/*    */   
/*    */   public static ClassTypeSignature make(List<SimpleClassTypeSignature> paramList) {
/* 44 */     return new ClassTypeSignature(paramList);
/*    */   }
/*    */   public List<SimpleClassTypeSignature> getPath() {
/* 47 */     return this.path;
/*    */   } public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 49 */     paramTypeTreeVisitor.visitClassTypeSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/ClassTypeSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */