/*    */ package java.io;
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
/*    */ public class InvalidClassException
/*    */   extends ObjectStreamException
/*    */ {
/*    */   private static final long serialVersionUID = -4333316296251054416L;
/*    */   public String classname;
/*    */   
/*    */   public InvalidClassException(String paramString) {
/* 58 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidClassException(String paramString1, String paramString2) {
/* 68 */     super(paramString2);
/* 69 */     this.classname = paramString1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 76 */     if (this.classname == null) {
/* 77 */       return super.getMessage();
/*    */     }
/* 79 */     return this.classname + "; " + super.getMessage();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/InvalidClassException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */