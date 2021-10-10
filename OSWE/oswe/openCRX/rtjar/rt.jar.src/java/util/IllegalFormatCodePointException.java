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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IllegalFormatCodePointException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 19080630L;
/*    */   private int c;
/*    */   
/*    */   public IllegalFormatCodePointException(int paramInt) {
/* 53 */     this.c = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCodePoint() {
/* 63 */     return this.c;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 67 */     return String.format("Code point = %#x", new Object[] { Integer.valueOf(this.c) });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/IllegalFormatCodePointException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */