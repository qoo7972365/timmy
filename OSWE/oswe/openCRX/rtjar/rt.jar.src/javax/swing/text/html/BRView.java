/*    */ package javax.swing.text.html;
/*    */ 
/*    */ import javax.swing.text.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BRView
/*    */   extends InlineView
/*    */ {
/*    */   public BRView(Element paramElement) {
/* 42 */     super(paramElement);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBreakWeight(int paramInt, float paramFloat1, float paramFloat2) {
/* 51 */     if (paramInt == 0) {
/* 52 */       return 3000;
/*    */     }
/* 54 */     return super.getBreakWeight(paramInt, paramFloat1, paramFloat2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/BRView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */