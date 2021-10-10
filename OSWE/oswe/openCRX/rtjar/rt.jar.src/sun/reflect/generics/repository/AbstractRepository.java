/*    */ package sun.reflect.generics.repository;
/*    */ 
/*    */ import sun.reflect.generics.factory.GenericsFactory;
/*    */ import sun.reflect.generics.tree.Tree;
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
/*    */ 
/*    */ public abstract class AbstractRepository<T extends Tree>
/*    */ {
/*    */   private final GenericsFactory factory;
/*    */   private final T tree;
/*    */   
/*    */   private GenericsFactory getFactory() {
/* 48 */     return this.factory;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected T getTree() {
/* 54 */     return this.tree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Reifier getReifier() {
/* 62 */     return Reifier.make(getFactory());
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
/*    */   protected AbstractRepository(String paramString, GenericsFactory paramGenericsFactory) {
/* 74 */     this.tree = parse(paramString);
/* 75 */     this.factory = paramGenericsFactory;
/*    */   }
/*    */   
/*    */   protected abstract T parse(String paramString);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/repository/AbstractRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */