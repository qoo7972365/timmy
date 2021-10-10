/*    */ package javax.swing.plaf.basic;
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
/*    */ public class BasicPopupMenuSeparatorUI
/*    */   extends BasicSeparatorUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 47 */     return new BasicPopupMenuSeparatorUI();
/*    */   }
/*    */ 
/*    */   
/*    */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 52 */     Dimension dimension = paramJComponent.getSize();
/*    */     
/* 54 */     paramGraphics.setColor(paramJComponent.getForeground());
/* 55 */     paramGraphics.drawLine(0, 0, dimension.width, 0);
/*    */     
/* 57 */     paramGraphics.setColor(paramJComponent.getBackground());
/* 58 */     paramGraphics.drawLine(0, 1, dimension.width, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 63 */     return new Dimension(0, 2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicPopupMenuSeparatorUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */