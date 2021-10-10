/*    */ package javax.swing.plaf.synth;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.plaf.basic.DefaultMenuLayout;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SynthMenuLayout
/*    */   extends DefaultMenuLayout
/*    */ {
/*    */   public SynthMenuLayout(Container paramContainer, int paramInt) {
/* 41 */     super(paramContainer, paramInt);
/*    */   }
/*    */   
/*    */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 45 */     if (paramContainer instanceof JPopupMenu) {
/* 46 */       JPopupMenu jPopupMenu = (JPopupMenu)paramContainer;
/* 47 */       jPopupMenu.putClientProperty(SynthMenuItemLayoutHelper.MAX_ACC_OR_ARROW_WIDTH, null);
/*    */     } 
/*    */ 
/*    */     
/* 51 */     return super.preferredLayoutSize(paramContainer);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthMenuLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */