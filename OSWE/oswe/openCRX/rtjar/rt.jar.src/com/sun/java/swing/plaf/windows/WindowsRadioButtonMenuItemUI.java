/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;
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
/*     */ public class WindowsRadioButtonMenuItemUI
/*     */   extends BasicRadioButtonMenuItemUI
/*     */ {
/*  48 */   final WindowsMenuItemUIAccessor accessor = new WindowsMenuItemUIAccessor()
/*     */     {
/*     */       public JMenuItem getMenuItem()
/*     */       {
/*  52 */         return WindowsRadioButtonMenuItemUI.this.menuItem;
/*     */       }
/*     */       
/*     */       public TMSchema.State getState(JMenuItem param1JMenuItem) {
/*  56 */         return WindowsMenuItemUI.getState(this, param1JMenuItem);
/*     */       }
/*     */       
/*     */       public TMSchema.Part getPart(JMenuItem param1JMenuItem) {
/*  60 */         return WindowsMenuItemUI.getPart(this, param1JMenuItem);
/*     */       }
/*     */     };
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  64 */     return new WindowsRadioButtonMenuItemUI();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintBackground(Graphics paramGraphics, JMenuItem paramJMenuItem, Color paramColor) {
/*  70 */     if (WindowsMenuItemUI.isVistaPainting()) {
/*  71 */       WindowsMenuItemUI.paintBackground(this.accessor, paramGraphics, paramJMenuItem, paramColor);
/*     */       return;
/*     */     } 
/*  74 */     super.paintBackground(paramGraphics, paramJMenuItem, paramColor);
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
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, JMenuItem paramJMenuItem, Rectangle paramRectangle, String paramString) {
/*  88 */     if (WindowsMenuItemUI.isVistaPainting()) {
/*  89 */       WindowsMenuItemUI.paintText(this.accessor, paramGraphics, paramJMenuItem, paramRectangle, paramString);
/*     */       return;
/*     */     } 
/*  92 */     ButtonModel buttonModel = paramJMenuItem.getModel();
/*  93 */     Color color = paramGraphics.getColor();
/*     */     
/*  95 */     if (buttonModel.isEnabled() && buttonModel.isArmed()) {
/*  96 */       paramGraphics.setColor(this.selectionForeground);
/*     */     }
/*     */     
/*  99 */     WindowsGraphicsUtils.paintText(paramGraphics, paramJMenuItem, paramRectangle, paramString, 0);
/*     */     
/* 101 */     paramGraphics.setColor(color);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsRadioButtonMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */