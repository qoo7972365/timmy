/*    */ package com.sun.java.swing.plaf.windows;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsPopupMenuSeparatorUI
/*    */   extends BasicPopupMenuSeparatorUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 48 */     return new WindowsPopupMenuSeparatorUI();
/*    */   }
/*    */   
/*    */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 52 */     Dimension dimension = paramJComponent.getSize();
/* 53 */     XPStyle xPStyle = XPStyle.getXP();
/* 54 */     if (WindowsMenuItemUI.isVistaPainting(xPStyle)) {
/* 55 */       int i = 1;
/* 56 */       Container container = paramJComponent.getParent();
/* 57 */       if (container instanceof JComponent) {
/*    */         
/* 59 */         Object object = ((JComponent)container).getClientProperty(WindowsPopupMenuUI.GUTTER_OFFSET_KEY);
/*    */         
/* 61 */         if (object instanceof Integer) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 67 */           i = ((Integer)object).intValue() - paramJComponent.getX();
/* 68 */           i += WindowsPopupMenuUI.getGutterWidth();
/*    */         } 
/*    */       } 
/* 71 */       XPStyle.Skin skin = xPStyle.getSkin(paramJComponent, TMSchema.Part.MP_POPUPSEPARATOR);
/* 72 */       int j = skin.getHeight();
/* 73 */       int k = (dimension.height - j) / 2;
/* 74 */       skin.paintSkin(paramGraphics, i, k, dimension.width - i - 1, j, TMSchema.State.NORMAL);
/*    */     } else {
/* 76 */       int i = dimension.height / 2;
/* 77 */       paramGraphics.setColor(paramJComponent.getForeground());
/* 78 */       paramGraphics.drawLine(1, i - 1, dimension.width - 2, i - 1);
/*    */       
/* 80 */       paramGraphics.setColor(paramJComponent.getBackground());
/* 81 */       paramGraphics.drawLine(1, i, dimension.width - 2, i);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 86 */     int i = 0;
/* 87 */     Font font = paramJComponent.getFont();
/* 88 */     if (font != null) {
/* 89 */       i = paramJComponent.getFontMetrics(font).getHeight();
/*    */     }
/*    */     
/* 92 */     return new Dimension(0, i / 2 + 2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsPopupMenuSeparatorUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */