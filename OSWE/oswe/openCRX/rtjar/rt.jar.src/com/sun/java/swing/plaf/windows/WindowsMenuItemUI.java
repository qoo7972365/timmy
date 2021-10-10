/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuItemUI;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsMenuItemUI
/*     */   extends BasicMenuItemUI
/*     */ {
/*  52 */   final WindowsMenuItemUIAccessor accessor = new WindowsMenuItemUIAccessor()
/*     */     {
/*     */       public JMenuItem getMenuItem()
/*     */       {
/*  56 */         return WindowsMenuItemUI.this.menuItem;
/*     */       }
/*     */       
/*     */       public TMSchema.State getState(JMenuItem param1JMenuItem) {
/*  60 */         return WindowsMenuItemUI.getState(this, param1JMenuItem);
/*     */       }
/*     */       
/*     */       public TMSchema.Part getPart(JMenuItem param1JMenuItem) {
/*  64 */         return WindowsMenuItemUI.getPart(this, param1JMenuItem);
/*     */       }
/*     */     };
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  68 */     return new WindowsMenuItemUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, JMenuItem paramJMenuItem, Rectangle paramRectangle, String paramString) {
/*  81 */     if (isVistaPainting()) {
/*  82 */       paintText(this.accessor, paramGraphics, paramJMenuItem, paramRectangle, paramString);
/*     */       return;
/*     */     } 
/*  85 */     ButtonModel buttonModel = paramJMenuItem.getModel();
/*  86 */     Color color = paramGraphics.getColor();
/*     */     
/*  88 */     if (buttonModel.isEnabled() && (buttonModel
/*  89 */       .isArmed() || (paramJMenuItem instanceof javax.swing.JMenu && buttonModel
/*  90 */       .isSelected()))) {
/*  91 */       paramGraphics.setColor(this.selectionForeground);
/*     */     }
/*     */     
/*  94 */     WindowsGraphicsUtils.paintText(paramGraphics, paramJMenuItem, paramRectangle, paramString, 0);
/*     */     
/*  96 */     paramGraphics.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintBackground(Graphics paramGraphics, JMenuItem paramJMenuItem, Color paramColor) {
/* 102 */     if (isVistaPainting()) {
/* 103 */       paintBackground(this.accessor, paramGraphics, paramJMenuItem, paramColor);
/*     */       return;
/*     */     } 
/* 106 */     super.paintBackground(paramGraphics, paramJMenuItem, paramColor);
/*     */   }
/*     */ 
/*     */   
/*     */   static void paintBackground(WindowsMenuItemUIAccessor paramWindowsMenuItemUIAccessor, Graphics paramGraphics, JMenuItem paramJMenuItem, Color paramColor) {
/* 111 */     XPStyle xPStyle = XPStyle.getXP();
/* 112 */     assert isVistaPainting(xPStyle);
/* 113 */     if (isVistaPainting(xPStyle)) {
/* 114 */       int i = paramJMenuItem.getWidth();
/* 115 */       int j = paramJMenuItem.getHeight();
/* 116 */       if (paramJMenuItem.isOpaque()) {
/* 117 */         Color color = paramGraphics.getColor();
/* 118 */         paramGraphics.setColor(paramJMenuItem.getBackground());
/* 119 */         paramGraphics.fillRect(0, 0, i, j);
/* 120 */         paramGraphics.setColor(color);
/*     */       } 
/* 122 */       TMSchema.Part part = paramWindowsMenuItemUIAccessor.getPart(paramJMenuItem);
/* 123 */       XPStyle.Skin skin = xPStyle.getSkin(paramJMenuItem, part);
/* 124 */       skin.paintSkin(paramGraphics, 0, 0, i, j, paramWindowsMenuItemUIAccessor
/*     */ 
/*     */           
/* 127 */           .getState(paramJMenuItem));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void paintText(WindowsMenuItemUIAccessor paramWindowsMenuItemUIAccessor, Graphics paramGraphics, JMenuItem paramJMenuItem, Rectangle paramRectangle, String paramString) {
/* 134 */     assert isVistaPainting();
/* 135 */     if (isVistaPainting()) {
/* 136 */       TMSchema.State state = paramWindowsMenuItemUIAccessor.getState(paramJMenuItem);
/*     */ 
/*     */       
/* 139 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJMenuItem, paramGraphics);
/* 140 */       int i = paramJMenuItem.getDisplayedMnemonicIndex();
/*     */       
/* 142 */       if (WindowsLookAndFeel.isMnemonicHidden() == true) {
/* 143 */         i = -1;
/*     */       }
/* 145 */       WindowsGraphicsUtils.paintXPText(paramJMenuItem, paramWindowsMenuItemUIAccessor
/* 146 */           .getPart(paramJMenuItem), state, paramGraphics, paramRectangle.x, paramRectangle.y + fontMetrics
/*     */           
/* 148 */           .getAscent(), paramString, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static TMSchema.State getState(WindowsMenuItemUIAccessor paramWindowsMenuItemUIAccessor, JMenuItem paramJMenuItem) {
/*     */     TMSchema.State state;
/* 155 */     ButtonModel buttonModel = paramJMenuItem.getModel();
/* 156 */     if (buttonModel.isArmed()) {
/* 157 */       state = buttonModel.isEnabled() ? TMSchema.State.HOT : TMSchema.State.DISABLEDHOT;
/*     */     } else {
/* 159 */       state = buttonModel.isEnabled() ? TMSchema.State.NORMAL : TMSchema.State.DISABLED;
/*     */     } 
/* 161 */     return state;
/*     */   }
/*     */   
/*     */   static TMSchema.Part getPart(WindowsMenuItemUIAccessor paramWindowsMenuItemUIAccessor, JMenuItem paramJMenuItem) {
/* 165 */     return TMSchema.Part.MP_POPUPITEM;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isVistaPainting(XPStyle paramXPStyle) {
/* 174 */     return (paramXPStyle != null && paramXPStyle.isSkinDefined(null, TMSchema.Part.MP_POPUPITEM));
/*     */   }
/*     */   
/*     */   static boolean isVistaPainting() {
/* 178 */     return isVistaPainting(XPStyle.getXP());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */