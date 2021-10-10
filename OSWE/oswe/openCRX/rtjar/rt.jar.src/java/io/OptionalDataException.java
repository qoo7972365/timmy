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
/*    */ public class OptionalDataException
/*    */   extends ObjectStreamException
/*    */ {
/*    */   private static final long serialVersionUID = -8011121865681257820L;
/*    */   public int length;
/*    */   public boolean eof;
/*    */   
/*    */   OptionalDataException(int paramInt) {
/* 56 */     this.eof = false;
/* 57 */     this.length = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   OptionalDataException(boolean paramBoolean) {
/* 65 */     this.length = 0;
/* 66 */     this.eof = paramBoolean;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/OptionalDataException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */