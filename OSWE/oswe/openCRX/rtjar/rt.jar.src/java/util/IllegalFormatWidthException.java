/*    */ package java.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IllegalFormatWidthException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 16660902L;
/*    */   private int w;
/*    */   
/*    */   public IllegalFormatWidthException(int paramInt) {
/* 47 */     this.w = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 56 */     return this.w;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 60 */     return Integer.toString(this.w);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/IllegalFormatWidthException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */