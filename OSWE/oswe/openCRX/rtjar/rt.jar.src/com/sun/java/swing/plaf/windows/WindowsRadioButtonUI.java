/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
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
/*     */ public class WindowsRadioButtonUI
/*     */   extends BasicRadioButtonUI
/*     */ {
/*  49 */   private static final Object WINDOWS_RADIO_BUTTON_UI_KEY = new Object();
/*     */   
/*     */   protected int dashedRectGapX;
/*     */   
/*     */   protected int dashedRectGapY;
/*     */   
/*     */   protected int dashedRectGapWidth;
/*     */   
/*     */   protected int dashedRectGapHeight;
/*     */   
/*     */   protected Color focusColor;
/*     */   
/*     */   private boolean initialized = false;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  64 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  66 */     WindowsRadioButtonUI windowsRadioButtonUI = (WindowsRadioButtonUI)appContext.get(WINDOWS_RADIO_BUTTON_UI_KEY);
/*  67 */     if (windowsRadioButtonUI == null) {
/*  68 */       windowsRadioButtonUI = new WindowsRadioButtonUI();
/*  69 */       appContext.put(WINDOWS_RADIO_BUTTON_UI_KEY, windowsRadioButtonUI);
/*     */     } 
/*  71 */     return windowsRadioButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(AbstractButton paramAbstractButton) {
/*  78 */     super.installDefaults(paramAbstractButton);
/*  79 */     if (!this.initialized) {
/*  80 */       this.dashedRectGapX = ((Integer)UIManager.get("Button.dashedRectGapX")).intValue();
/*  81 */       this.dashedRectGapY = ((Integer)UIManager.get("Button.dashedRectGapY")).intValue();
/*  82 */       this.dashedRectGapWidth = ((Integer)UIManager.get("Button.dashedRectGapWidth")).intValue();
/*  83 */       this.dashedRectGapHeight = ((Integer)UIManager.get("Button.dashedRectGapHeight")).intValue();
/*  84 */       this.focusColor = UIManager.getColor(getPropertyPrefix() + "focus");
/*  85 */       this.initialized = true;
/*     */     } 
/*  87 */     if (XPStyle.getXP() != null) {
/*  88 */       LookAndFeel.installProperty(paramAbstractButton, "rolloverEnabled", Boolean.TRUE);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/*  93 */     super.uninstallDefaults(paramAbstractButton);
/*  94 */     this.initialized = false;
/*     */   }
/*     */   
/*     */   protected Color getFocusColor() {
/*  98 */     return this.focusColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle, String paramString) {
/* 109 */     WindowsGraphicsUtils.paintText(paramGraphics, paramAbstractButton, paramRectangle, paramString, getTextShiftOffset());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, Rectangle paramRectangle, Dimension paramDimension) {
/* 114 */     paramGraphics.setColor(getFocusColor());
/* 115 */     BasicGraphicsUtils.drawDashedRect(paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 122 */     Dimension dimension = super.getPreferredSize(paramJComponent);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 128 */     if (dimension != null && abstractButton.isFocusPainted()) {
/* 129 */       if (dimension.width % 2 == 0) dimension.width++; 
/* 130 */       if (dimension.height % 2 == 0) dimension.height++; 
/*     */     } 
/* 132 */     return dimension;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsRadioButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */