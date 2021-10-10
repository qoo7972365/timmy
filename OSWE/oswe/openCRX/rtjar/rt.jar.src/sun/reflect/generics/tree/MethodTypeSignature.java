/*    */ package sun.reflect.generics.tree;
/*    */ 
/*    */ import sun.reflect.generics.visitor.Visitor;
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
/*    */ public class MethodTypeSignature
/*    */   implements Signature
/*    */ {
/*    */   private final FormalTypeParameter[] formalTypeParams;
/*    */   private final TypeSignature[] parameterTypes;
/*    */   private final ReturnType returnType;
/*    */   private final FieldTypeSignature[] exceptionTypes;
/*    */   
/*    */   private MethodTypeSignature(FormalTypeParameter[] paramArrayOfFormalTypeParameter, TypeSignature[] paramArrayOfTypeSignature, ReturnType paramReturnType, FieldTypeSignature[] paramArrayOfFieldTypeSignature) {
/* 40 */     this.formalTypeParams = paramArrayOfFormalTypeParameter;
/* 41 */     this.parameterTypes = paramArrayOfTypeSignature;
/* 42 */     this.returnType = paramReturnType;
/* 43 */     this.exceptionTypes = paramArrayOfFieldTypeSignature;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MethodTypeSignature make(FormalTypeParameter[] paramArrayOfFormalTypeParameter, TypeSignature[] paramArrayOfTypeSignature, ReturnType paramReturnType, FieldTypeSignature[] paramArrayOfFieldTypeSignature) {
/* 50 */     return new MethodTypeSignature(paramArrayOfFormalTypeParameter, paramArrayOfTypeSignature, paramReturnType, paramArrayOfFieldTypeSignature);
/*    */   }
/*    */   
/*    */   public FormalTypeParameter[] getFormalTypeParameters() {
/* 54 */     return this.formalTypeParams;
/*    */   }
/* 56 */   public TypeSignature[] getParameterTypes() { return this.parameterTypes; }
/* 57 */   public ReturnType getReturnType() { return this.returnType; } public FieldTypeSignature[] getExceptionTypes() {
/* 58 */     return this.exceptionTypes;
/*    */   } public void accept(Visitor<?> paramVisitor) {
/* 60 */     paramVisitor.visitMethodTypeSignature(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/tree/MethodTypeSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */