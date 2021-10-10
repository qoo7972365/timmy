/*    */ package sun.text;
/*    */ 
/*    */ import java.text.CharacterIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CodePointIterator
/*    */ {
/*    */   public static final int DONE = -1;
/*    */   
/*    */   public abstract void setToStart();
/*    */   
/*    */   public abstract void setToLimit();
/*    */   
/*    */   public abstract int next();
/*    */   
/*    */   public abstract int prev();
/*    */   
/*    */   public abstract int charIndex();
/*    */   
/*    */   public static CodePointIterator create(char[] paramArrayOfchar) {
/* 52 */     return new CharArrayCodePointIterator(paramArrayOfchar);
/*    */   }
/*    */   
/*    */   public static CodePointIterator create(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 56 */     return new CharArrayCodePointIterator(paramArrayOfchar, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public static CodePointIterator create(CharSequence paramCharSequence) {
/* 60 */     return new CharSequenceCodePointIterator(paramCharSequence);
/*    */   }
/*    */   
/*    */   public static CodePointIterator create(CharacterIterator paramCharacterIterator) {
/* 64 */     return new CharacterIteratorCodePointIterator(paramCharacterIterator);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/CodePointIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */