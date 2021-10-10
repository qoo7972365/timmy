/*    */ package javax.swing.text.html;
/*    */ 
/*    */ import javax.swing.text.AttributeSet;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.PlainDocument;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TextAreaDocument
/*    */   extends PlainDocument
/*    */ {
/*    */   String initialText;
/*    */   
/*    */   void reset() {
/*    */     try {
/* 50 */       remove(0, getLength());
/* 51 */       if (this.initialText != null) {
/* 52 */         insertString(0, this.initialText, (AttributeSet)null);
/*    */       }
/* 54 */     } catch (BadLocationException badLocationException) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void storeInitialText() {
/*    */     try {
/* 64 */       this.initialText = getText(0, getLength());
/* 65 */     } catch (BadLocationException badLocationException) {}
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/TextAreaDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */