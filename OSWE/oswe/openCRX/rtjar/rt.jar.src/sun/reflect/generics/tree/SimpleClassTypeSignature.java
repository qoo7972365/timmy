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
/*    */ public class SimpleClassTypeSignature
/*    */   implements FieldTypeSignature
/*    */ {
/*    */   private final boolean dollar;
/*    */   private final String name;
/*    */   private final TypeArgument[] typeArgs;
/*    */   
/*    */   private SimpleClassTypeSignature(String paramString, boolean paramBoolean, TypeArgument[] paramArrayOfTypeArgument) {
/* 36 */     this.name = paramString;
/* 37 */     this.dollar = paramBoolean;
/* 38 */     this.typeArgs = paramArrayOfTypeArgument;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleClassTypeSignature make(String paramString, boolean paramBoolean, TypeArgument[] paramArrayOfTypeArgument) {
/* 44 */     return new SimpleClassTypeSignature(paramString, paramBoolean, paramArrayOfTypeArgument);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getDollar() {
/* 53 */     return this.dollar;
/* 54 */   } public String getName() { return this.name; } public TypeArgument[] getTypeArguments() {
/* 55 */     return this.typeArgs;
/*    */   }
/*    */   public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 58 */     paramTypeTreeVisitor.visitSimpleClassTypeSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/SimpleClassTypeSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */