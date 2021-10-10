/*    */ package sun.reflect.generics.repository;
/*    */ 
/*    */ import java.lang.reflect.Type;
/*    */ import sun.reflect.generics.factory.GenericsFactory;
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
/*    */ public class MethodRepository
/*    */   extends ConstructorRepository
/*    */ {
/*    */   private Type returnType;
/*    */   
/*    */   private MethodRepository(String paramString, GenericsFactory paramGenericsFactory) {
/* 46 */     super(paramString, paramGenericsFactory);
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
/*    */   public static MethodRepository make(String paramString, GenericsFactory paramGenericsFactory) {
/* 59 */     return new MethodRepository(paramString, paramGenericsFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getReturnType() {
/* 65 */     if (this.returnType == null) {
/* 66 */       Reifier reifier = getReifier();
/*    */       
/* 68 */       getTree().getReturnType().accept(reifier);
/*    */       
/* 70 */       this.returnType = reifier.getResult();
/*    */     } 
/* 72 */     return this.returnType;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/repository/MethodRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */