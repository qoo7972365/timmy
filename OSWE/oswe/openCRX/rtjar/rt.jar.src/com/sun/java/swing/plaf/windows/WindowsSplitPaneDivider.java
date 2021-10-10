/*    */ package com.sun.java.swing.plaf.windows;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*    */ import javax.swing.plaf.basic.BasicSplitPaneUI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsSplitPaneDivider
/*    */   extends BasicSplitPaneDivider
/*    */ {
/*    */   public WindowsSplitPaneDivider(BasicSplitPaneUI paramBasicSplitPaneUI) {
/* 54 */     super(paramBasicSplitPaneUI);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paint(Graphics paramGraphics) {
/* 63 */     Color color = this.splitPane.hasFocus() ? UIManager.getColor("SplitPane.shadow") : getBackground();
/* 64 */     Dimension dimension = getSize();
/*    */     
/* 66 */     if (color != null) {
/* 67 */       paramGraphics.setColor(color);
/* 68 */       paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/*    */     } 
/* 70 */     super.paint(paramGraphics);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsSplitPaneDivider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */