/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicRadioButtonUI;
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
/*     */ 
/*     */ public class MotifRadioButtonUI
/*     */   extends BasicRadioButtonUI
/*     */ {
/*  52 */   private static final Object MOTIF_RADIO_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */   
/*     */   protected Color focusColor;
/*     */ 
/*     */   
/*     */   private boolean defaults_initialized = false;
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  62 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  64 */     MotifRadioButtonUI motifRadioButtonUI = (MotifRadioButtonUI)appContext.get(MOTIF_RADIO_BUTTON_UI_KEY);
/*  65 */     if (motifRadioButtonUI == null) {
/*  66 */       motifRadioButtonUI = new MotifRadioButtonUI();
/*  67 */       appContext.put(MOTIF_RADIO_BUTTON_UI_KEY, motifRadioButtonUI);
/*     */     } 
/*  69 */     return motifRadioButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(AbstractButton paramAbstractButton) {
/*  76 */     super.installDefaults(paramAbstractButton);
/*  77 */     if (!this.defaults_initialized) {
/*  78 */       this.focusColor = UIManager.getColor(getPropertyPrefix() + "focus");
/*  79 */       this.defaults_initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/*  84 */     super.uninstallDefaults(paramAbstractButton);
/*  85 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getFocusColor() {
/*  93 */     return this.focusColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, Rectangle paramRectangle, Dimension paramDimension) {
/* 100 */     paramGraphics.setColor(getFocusColor());
/* 101 */     paramGraphics.drawRect(0, 0, paramDimension.width - 1, paramDimension.height - 1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifRadioButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */