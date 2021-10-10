/*    */ package sun.font;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TextRecord
/*    */ {
/*    */   public char[] text;
/*    */   public int start;
/*    */   public int limit;
/*    */   public int min;
/*    */   public int max;
/*    */   
/*    */   public void init(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 44 */     this.text = paramArrayOfchar;
/* 45 */     this.start = paramInt1;
/* 46 */     this.limit = paramInt2;
/* 47 */     this.min = paramInt3;
/* 48 */     this.max = paramInt4;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/TextRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */