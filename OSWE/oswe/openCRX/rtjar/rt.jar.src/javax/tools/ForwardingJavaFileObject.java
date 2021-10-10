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
/*    */ 
/*    */ 
/*    */ public class ForwardingJavaFileObject<F extends JavaFileObject>
/*    */   extends ForwardingFileObject<F>
/*    */   implements JavaFileObject
/*    */ {
/*    */   protected ForwardingJavaFileObject(F paramF) {
/* 50 */     super(paramF);
/*    */   }
/*    */   
/*    */   public JavaFileObject.Kind getKind() {
/* 54 */     return ((JavaFileObject)this.fileObject).getKind();
/*    */   }
/*    */   
/*    */   public boolean isNameCompatible(String paramString, JavaFileObject.Kind paramKind) {
/* 58 */     return ((JavaFileObject)this.fileObject).isNameCompatible(paramString, paramKind);
/*    */   }
/*    */   public NestingKind getNestingKind() {
/* 61 */     return ((JavaFileObject)this.fileObject).getNestingKind();
/*    */   } public Modifier getAccessLevel() {
/* 63 */     return ((JavaFileObject)this.fileObject).getAccessLevel();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/ForwardingJavaFileObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */