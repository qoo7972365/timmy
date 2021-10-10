/*    */ package javax.tools;
/*    */ 
/*    */ import javax.lang.model.element.Modifier;
/*    */ import javax.lang.model.element.NestingKind;
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
/*    */ 
/*    */ 
/*    */ public interface JavaFileObject
/*    */   extends FileObject
/*    */ {
/*    */   Kind getKind();
/*    */   
/*    */   boolean isNameCompatible(String paramString, Kind paramKind);
/*    */   
/*    */   NestingKind getNestingKind();
/*    */   
/*    */   Modifier getAccessLevel();
/*    */   
/*    */   public enum Kind
/*    */   {
/* 56 */     SOURCE(".java"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     CLASS(".class"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 68 */     HTML(".html"),
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 73 */     OTHER("");
/*    */ 
/*    */     
/*    */     public final String extension;
/*    */ 
/*    */ 
/*    */     
/*    */     Kind(String param1String1) {
/* 81 */       param1String1.getClass();
/* 82 */       this.extension = param1String1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/JavaFileObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */