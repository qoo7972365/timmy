/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class XHorizontalScrollbar
/*    */   extends XScrollbar
/*    */ {
/*    */   public XHorizontalScrollbar(XScrollbarClient paramXScrollbarClient) {
/* 37 */     super(2, paramXScrollbarClient);
/*    */   }
/*    */   
/*    */   public void setSize(int paramInt1, int paramInt2) {
/* 41 */     super.setSize(paramInt1, paramInt2);
/* 42 */     this.barWidth = paramInt2;
/* 43 */     this.barLength = paramInt1;
/* 44 */     calculateArrowWidth();
/* 45 */     rebuildArrows();
/*    */   }
/*    */   protected void rebuildArrows() {
/* 48 */     this.firstArrow = createArrowShape(false, true);
/* 49 */     this.secondArrow = createArrowShape(false, false);
/*    */   }
/*    */   
/*    */   boolean beforeThumb(int paramInt1, int paramInt2) {
/* 53 */     Rectangle rectangle = calculateThumbRect();
/* 54 */     return (paramInt1 < rectangle.x);
/*    */   }
/*    */   
/*    */   protected Rectangle getThumbArea() {
/* 58 */     return new Rectangle(getArrowAreaWidth(), 2, this.width - 2 * getArrowAreaWidth(), this.height - 4);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XHorizontalScrollbar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */