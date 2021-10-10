/*    */ package javax.swing.plaf.basic;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JSeparator;
/*    */ import javax.swing.JToolBar;
/*    */ import javax.swing.UIManager;
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
/*    */ public class BasicToolBarSeparatorUI
/*    */   extends BasicSeparatorUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 51 */     return new BasicToolBarSeparatorUI();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void installDefaults(JSeparator paramJSeparator) {
/* 56 */     Dimension dimension = ((JToolBar.Separator)paramJSeparator).getSeparatorSize();
/*    */     
/* 58 */     if (dimension == null || dimension instanceof javax.swing.plaf.UIResource) {
/*    */       
/* 60 */       JToolBar.Separator separator = (JToolBar.Separator)paramJSeparator;
/* 61 */       dimension = (Dimension)UIManager.get("ToolBar.separatorSize");
/* 62 */       if (dimension != null) {
/* 63 */         if (separator.getOrientation() == 0) {
/* 64 */           dimension = new Dimension(dimension.height, dimension.width);
/*    */         }
/* 66 */         separator.setSeparatorSize(dimension);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {}
/*    */ 
/*    */   
/*    */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 77 */     Dimension dimension = ((JToolBar.Separator)paramJComponent).getSeparatorSize();
/*    */     
/* 79 */     if (dimension != null)
/*    */     {
/* 81 */       return dimension.getSize();
/*    */     }
/*    */ 
/*    */     
/* 85 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicToolBarSeparatorUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */