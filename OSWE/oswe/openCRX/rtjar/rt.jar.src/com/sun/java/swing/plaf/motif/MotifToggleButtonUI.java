/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicToggleButtonUI;
/*     */ import sun.awt.AppContext;
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
/*     */ public class MotifToggleButtonUI
/*     */   extends BasicToggleButtonUI
/*     */ {
/*  53 */   private static final Object MOTIF_TOGGLE_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */   
/*     */   protected Color selectColor;
/*     */ 
/*     */   
/*     */   private boolean defaults_initialized = false;
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  65 */     MotifToggleButtonUI motifToggleButtonUI = (MotifToggleButtonUI)appContext.get(MOTIF_TOGGLE_BUTTON_UI_KEY);
/*  66 */     if (motifToggleButtonUI == null) {
/*  67 */       motifToggleButtonUI = new MotifToggleButtonUI();
/*  68 */       appContext.put(MOTIF_TOGGLE_BUTTON_UI_KEY, motifToggleButtonUI);
/*     */     } 
/*  70 */     return motifToggleButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(AbstractButton paramAbstractButton) {
/*  77 */     super.installDefaults(paramAbstractButton);
/*  78 */     if (!this.defaults_initialized) {
/*  79 */       this.selectColor = UIManager.getColor(getPropertyPrefix() + "select");
/*  80 */       this.defaults_initialized = true;
/*     */     } 
/*  82 */     LookAndFeel.installProperty(paramAbstractButton, "opaque", Boolean.FALSE);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/*  86 */     super.uninstallDefaults(paramAbstractButton);
/*  87 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getSelectColor() {
/*  95 */     return this.selectColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, AbstractButton paramAbstractButton) {
/* 102 */     if (paramAbstractButton.isContentAreaFilled()) {
/* 103 */       Color color = paramGraphics.getColor();
/* 104 */       Dimension dimension = paramAbstractButton.getSize();
/* 105 */       Insets insets1 = paramAbstractButton.getInsets();
/* 106 */       Insets insets2 = paramAbstractButton.getMargin();
/*     */       
/* 108 */       if (paramAbstractButton.getBackground() instanceof javax.swing.plaf.UIResource) {
/* 109 */         paramGraphics.setColor(getSelectColor());
/*     */       }
/* 111 */       paramGraphics.fillRect(insets1.left - insets2.left, insets1.top - insets2.top, dimension.width - insets1.left - insets2.left - insets1.right - insets2.right, dimension.height - insets1.top - insets2.top - insets1.bottom - insets2.bottom);
/*     */ 
/*     */ 
/*     */       
/* 115 */       paramGraphics.setColor(color);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Insets getInsets(JComponent paramJComponent) {
/* 120 */     Border border = paramJComponent.getBorder();
/* 121 */     return (border != null) ? border.getBorderInsets(paramJComponent) : new Insets(0, 0, 0, 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifToggleButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */