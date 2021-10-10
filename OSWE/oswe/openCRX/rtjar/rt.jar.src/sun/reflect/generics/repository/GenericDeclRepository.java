/*    */ package sun.reflect.generics.repository;
/*    */ 
/*    */ import java.lang.reflect.TypeVariable;
/*    */ import sun.reflect.generics.factory.GenericsFactory;
/*    */ import sun.reflect.generics.tree.FormalTypeParameter;
/*    */ import sun.reflect.generics.tree.Signature;
/*    */ import sun.reflect.generics.visitor.Reifier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GenericDeclRepository<S extends Signature>
/*    */   extends AbstractRepository<S>
/*    */ {
/*    */   private volatile TypeVariable<?>[] typeParams;
/*    */   
/*    */   protected GenericDeclRepository(String paramString, GenericsFactory paramGenericsFactory) {
/* 49 */     super(paramString, paramGenericsFactory);
/*    */   }
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
/*    */   public TypeVariable<?>[] getTypeParameters() {
/*    */     TypeVariable[] arrayOfTypeVariable;
/* 68 */     TypeVariable<?>[] arrayOfTypeVariable1 = this.typeParams;
/* 69 */     if (arrayOfTypeVariable1 == null) {
/*    */       
/* 71 */       FormalTypeParameter[] arrayOfFormalTypeParameter = ((Signature)getTree()).getFormalTypeParameters();
/*    */       
/* 73 */       arrayOfTypeVariable = new TypeVariable[arrayOfFormalTypeParameter.length];
/*    */       
/* 75 */       for (byte b = 0; b < arrayOfFormalTypeParameter.length; b++) {
/* 76 */         Reifier reifier = getReifier();
/* 77 */         arrayOfFormalTypeParameter[b].accept(reifier);
/*    */         
/* 79 */         arrayOfTypeVariable[b] = (TypeVariable)reifier.getResult();
/*    */       } 
/* 81 */       this.typeParams = (TypeVariable<?>[])arrayOfTypeVariable;
/*    */     } 
/* 83 */     return (TypeVariable<?>[])arrayOfTypeVariable.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/repository/GenericDeclRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */