/*    */ package java.lang;
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
/*    */ public class TypeNotPresentException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -5101214195716534496L;
/*    */   private String typeName;
/*    */   
/*    */   public TypeNotPresentException(String paramString, Throwable paramThrowable) {
/* 60 */     super("Type " + paramString + " not present", paramThrowable);
/* 61 */     this.typeName = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String typeName() {
/* 69 */     return this.typeName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/TypeNotPresentException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */