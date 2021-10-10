/*    */ package javax.swing.plaf.metal;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetalPopupMenuSeparatorUI
/*    */   extends MetalSeparatorUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 48 */     return new MetalPopupMenuSeparatorUI();
/*    */   }
/*    */ 
/*    */   
/*    */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 53 */     Dimension dimension = paramJComponent.getSize();
/*    */     
/* 55 */     paramGraphics.setColor(paramJComponent.getForeground());
/* 56 */     paramGraphics.drawLine(0, 1, dimension.width, 1);
/*    */     
/* 58 */     paramGraphics.setColor(paramJComponent.getBackground());
/* 59 */     paramGraphics.drawLine(0, 2, dimension.width, 2);
/* 60 */     paramGraphics.drawLine(0, 0, 0, 0);
/* 61 */     paramGraphics.drawLine(0, 3, 0, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 66 */     return new Dimension(0, 4);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalPopupMenuSeparatorUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */