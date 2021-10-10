/*    */ package sun.awt;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharsetString
/*    */ {
/*    */   public char[] charsetChars;
/*    */   public int offset;
/*    */   public int length;
/*    */   public FontDescriptor fontDescriptor;
/*    */   
/*    */   public CharsetString(char[] paramArrayOfchar, int paramInt1, int paramInt2, FontDescriptor paramFontDescriptor) {
/* 54 */     this.charsetChars = paramArrayOfchar;
/* 55 */     this.offset = paramInt1;
/* 56 */     this.length = paramInt2;
/* 57 */     this.fontDescriptor = paramFontDescriptor;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/CharsetString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */