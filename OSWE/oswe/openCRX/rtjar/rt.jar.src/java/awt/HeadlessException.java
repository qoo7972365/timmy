/*    */ package java.awt;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeadlessException
/*    */   extends UnsupportedOperationException
/*    */ {
/*    */   private static final long serialVersionUID = 167183644944358563L;
/*    */   
/*    */   public HeadlessException() {}
/*    */   
/*    */   public HeadlessException(String paramString) {
/* 43 */     super(paramString);
/*    */   }
/*    */   public String getMessage() {
/* 46 */     String str1 = super.getMessage();
/* 47 */     String str2 = GraphicsEnvironment.getHeadlessMessage();
/*    */     
/* 49 */     if (str1 == null)
/* 50 */       return str2; 
/* 51 */     if (str2 == null) {
/* 52 */       return str1;
/*    */     }
/* 54 */     return str1 + str2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/HeadlessException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */