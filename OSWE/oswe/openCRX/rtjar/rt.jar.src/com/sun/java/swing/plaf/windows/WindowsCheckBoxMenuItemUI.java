/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
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
/*     */ public class WindowsCheckBoxMenuItemUI
/*     */   extends BasicCheckBoxMenuItemUI
/*     */ {
/*  49 */   final WindowsMenuItemUIAccessor accessor = new WindowsMenuItemUIAccessor()
/*     */     {
/*     */       public JMenuItem getMenuItem()
/*     */       {
/*  53 */         return WindowsCheckBoxMenuItemUI.this.menuItem;
/*     */       }
/*     */       
/*     */       public TMSchema.State getState(JMenuItem param1JMenuItem) {
/*  57 */         return WindowsMenuItemUI.getState(this, param1JMenuItem);
/*     */       }
/*     */       
/*     */       public TMSchema.Part getPart(JMenuItem param1JMenuItem) {
/*  61 */         return WindowsMenuItemUI.getPart(this, param1JMenuItem);
/*     */       }
/*     */     };
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  65 */     return new WindowsCheckBoxMenuItemUI();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintBackground(Graphics paramGraphics, JMenuItem paramJMenuItem, Color paramColor) {
/*  71 */     if (WindowsMenuItemUI.isVistaPainting()) {
/*  72 */       WindowsMenuItemUI.paintBackground(this.accessor, paramGraphics, paramJMenuItem, paramColor);
/*     */       return;
/*     */     } 
/*  75 */     super.paintBackground(paramGraphics, paramJMenuItem, paramColor);
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
/*  88 */     if (WindowsMenuItemUI.isVistaPainting()) {
/*  89 */       WindowsMenuItemUI.paintText(this.accessor, paramGraphics, paramJMenuItem, paramRectangle, paramString);
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     ButtonModel buttonModel = paramJMenuItem.getModel();
/*  94 */     Color color = paramGraphics.getColor();
/*     */     
/*  96 */     if (buttonModel.isEnabled() && buttonModel.isArmed()) {
/*  97 */       paramGraphics.setColor(this.selectionForeground);
/*     */     }
/*     */     
/* 100 */     WindowsGraphicsUtils.paintText(paramGraphics, paramJMenuItem, paramRectangle, paramString, 0);
/*     */     
/* 102 */     paramGraphics.setColor(color);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsCheckBoxMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */