/*    */ package com.sun.java.swing.plaf.windows;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicToolBarUI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsToolBarUI
/*    */   extends BasicToolBarUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 51 */     return new WindowsToolBarUI();
/*    */   }
/*    */   
/*    */   protected void installDefaults() {
/* 55 */     if (XPStyle.getXP() != null) {
/* 56 */       setRolloverBorders(true);
/*    */     }
/* 58 */     super.installDefaults();
/*    */   }
/*    */   
/*    */   protected Border createRolloverBorder() {
/* 62 */     if (XPStyle.getXP() != null) {
/* 63 */       return new EmptyBorder(3, 3, 3, 3);
/*    */     }
/* 65 */     return super.createRolloverBorder();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Border createNonRolloverBorder() {
/* 70 */     if (XPStyle.getXP() != null) {
/* 71 */       return new EmptyBorder(3, 3, 3, 3);
/*    */     }
/* 73 */     return super.createNonRolloverBorder();
/*    */   }
/*    */ 
/*    */   
/*    */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 78 */     XPStyle xPStyle = XPStyle.getXP();
/* 79 */     if (xPStyle != null) {
/* 80 */       xPStyle.getSkin(paramJComponent, TMSchema.Part.TP_TOOLBAR).paintSkin(paramGraphics, 0, 0, paramJComponent
/* 81 */           .getWidth(), paramJComponent.getHeight(), null, true);
/*    */     } else {
/* 83 */       super.paint(paramGraphics, paramJComponent);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Border getRolloverBorder(AbstractButton paramAbstractButton) {
/* 92 */     XPStyle xPStyle = XPStyle.getXP();
/* 93 */     if (xPStyle != null) {
/* 94 */       return xPStyle.getBorder(paramAbstractButton, WindowsButtonUI.getXPButtonType(paramAbstractButton));
/*    */     }
/* 96 */     return super.getRolloverBorder(paramAbstractButton);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsToolBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */