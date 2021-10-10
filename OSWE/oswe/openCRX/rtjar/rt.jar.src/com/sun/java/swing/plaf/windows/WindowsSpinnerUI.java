/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicSpinnerUI;
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
/*     */ public class WindowsSpinnerUI
/*     */   extends BasicSpinnerUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  42 */     return new WindowsSpinnerUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  50 */     if (XPStyle.getXP() != null) {
/*  51 */       paintXPBackground(paramGraphics, paramJComponent);
/*     */     }
/*  53 */     super.paint(paramGraphics, paramJComponent);
/*     */   }
/*     */   
/*     */   private TMSchema.State getXPState(JComponent paramJComponent) {
/*  57 */     TMSchema.State state = TMSchema.State.NORMAL;
/*  58 */     if (!paramJComponent.isEnabled()) {
/*  59 */       state = TMSchema.State.DISABLED;
/*     */     }
/*  61 */     return state;
/*     */   }
/*     */   
/*     */   private void paintXPBackground(Graphics paramGraphics, JComponent paramJComponent) {
/*  65 */     XPStyle xPStyle = XPStyle.getXP();
/*  66 */     if (xPStyle == null) {
/*     */       return;
/*     */     }
/*  69 */     XPStyle.Skin skin = xPStyle.getSkin(paramJComponent, TMSchema.Part.EP_EDIT);
/*  70 */     TMSchema.State state = getXPState(paramJComponent);
/*  71 */     skin.paintSkin(paramGraphics, 0, 0, paramJComponent.getWidth(), paramJComponent.getHeight(), state);
/*     */   }
/*     */   
/*     */   protected Component createPreviousButton() {
/*  75 */     if (XPStyle.getXP() != null) {
/*  76 */       XPStyle.GlyphButton glyphButton = new XPStyle.GlyphButton(this.spinner, TMSchema.Part.SPNP_DOWN);
/*  77 */       Dimension dimension = UIManager.getDimension("Spinner.arrowButtonSize");
/*  78 */       glyphButton.setPreferredSize(dimension);
/*  79 */       glyphButton.setRequestFocusEnabled(false);
/*  80 */       installPreviousButtonListeners(glyphButton);
/*  81 */       return glyphButton;
/*     */     } 
/*  83 */     return super.createPreviousButton();
/*     */   }
/*     */   
/*     */   protected Component createNextButton() {
/*  87 */     if (XPStyle.getXP() != null) {
/*  88 */       XPStyle.GlyphButton glyphButton = new XPStyle.GlyphButton(this.spinner, TMSchema.Part.SPNP_UP);
/*  89 */       Dimension dimension = UIManager.getDimension("Spinner.arrowButtonSize");
/*  90 */       glyphButton.setPreferredSize(dimension);
/*  91 */       glyphButton.setRequestFocusEnabled(false);
/*  92 */       installNextButtonListeners(glyphButton);
/*  93 */       return glyphButton;
/*     */     } 
/*  95 */     return super.createNextButton();
/*     */   }
/*     */   
/*     */   private UIResource getUIResource(Object[] paramArrayOfObject) {
/*  99 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 100 */       if (paramArrayOfObject[b] instanceof UIResource) {
/* 101 */         return (UIResource)paramArrayOfObject[b];
/*     */       }
/*     */     } 
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsSpinnerUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */