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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsToggleButtonUI
/*     */   extends BasicToggleButtonUI
/*     */ {
/*     */   protected int dashedRectGapX;
/*     */   protected int dashedRectGapY;
/*     */   protected int dashedRectGapWidth;
/*     */   protected int dashedRectGapHeight;
/*     */   protected Color focusColor;
/*  61 */   private static final Object WINDOWS_TOGGLE_BUTTON_UI_KEY = new Object();
/*     */   
/*     */   private boolean defaults_initialized = false;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  66 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  68 */     WindowsToggleButtonUI windowsToggleButtonUI = (WindowsToggleButtonUI)appContext.get(WINDOWS_TOGGLE_BUTTON_UI_KEY);
/*  69 */     if (windowsToggleButtonUI == null) {
/*  70 */       windowsToggleButtonUI = new WindowsToggleButtonUI();
/*  71 */       appContext.put(WINDOWS_TOGGLE_BUTTON_UI_KEY, windowsToggleButtonUI);
/*     */     } 
/*  73 */     return windowsToggleButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(AbstractButton paramAbstractButton) {
/*  81 */     super.installDefaults(paramAbstractButton);
/*  82 */     if (!this.defaults_initialized) {
/*  83 */       String str = getPropertyPrefix();
/*  84 */       this.dashedRectGapX = ((Integer)UIManager.get("Button.dashedRectGapX")).intValue();
/*  85 */       this.dashedRectGapY = ((Integer)UIManager.get("Button.dashedRectGapY")).intValue();
/*  86 */       this.dashedRectGapWidth = ((Integer)UIManager.get("Button.dashedRectGapWidth")).intValue();
/*  87 */       this.dashedRectGapHeight = ((Integer)UIManager.get("Button.dashedRectGapHeight")).intValue();
/*  88 */       this.focusColor = UIManager.getColor(str + "focus");
/*  89 */       this.defaults_initialized = true;
/*     */     } 
/*     */     
/*  92 */     XPStyle xPStyle = XPStyle.getXP();
/*  93 */     if (xPStyle != null) {
/*  94 */       paramAbstractButton.setBorder(xPStyle.getBorder(paramAbstractButton, WindowsButtonUI.getXPButtonType(paramAbstractButton)));
/*  95 */       LookAndFeel.installProperty(paramAbstractButton, "opaque", Boolean.FALSE);
/*  96 */       LookAndFeel.installProperty(paramAbstractButton, "rolloverEnabled", Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 101 */     super.uninstallDefaults(paramAbstractButton);
/* 102 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Color getFocusColor() {
/* 107 */     return this.focusColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private transient Color cachedSelectedColor = null;
/* 116 */   private transient Color cachedBackgroundColor = null;
/* 117 */   private transient Color cachedHighlightColor = null;
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, AbstractButton paramAbstractButton) {
/* 120 */     if (XPStyle.getXP() == null && paramAbstractButton.isContentAreaFilled()) {
/* 121 */       Color color1 = paramGraphics.getColor();
/* 122 */       Color color2 = paramAbstractButton.getBackground();
/* 123 */       Color color3 = UIManager.getColor("ToggleButton.highlight");
/* 124 */       if (color2 != this.cachedBackgroundColor || color3 != this.cachedHighlightColor) {
/* 125 */         int i = color2.getRed(), j = color3.getRed();
/* 126 */         int k = color2.getGreen(), m = color3.getGreen();
/* 127 */         int n = color2.getBlue(), i1 = color3.getBlue();
/* 128 */         this
/*     */ 
/*     */           
/* 131 */           .cachedSelectedColor = new Color(Math.min(i, j) + Math.abs(i - j) / 2, Math.min(k, m) + Math.abs(k - m) / 2, Math.min(n, i1) + Math.abs(n - i1) / 2);
/*     */         
/* 133 */         this.cachedBackgroundColor = color2;
/* 134 */         this.cachedHighlightColor = color3;
/*     */       } 
/* 136 */       paramGraphics.setColor(this.cachedSelectedColor);
/* 137 */       paramGraphics.fillRect(0, 0, paramAbstractButton.getWidth(), paramAbstractButton.getHeight());
/* 138 */       paramGraphics.setColor(color1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 143 */     if (XPStyle.getXP() != null) {
/* 144 */       WindowsButtonUI.paintXPButtonBackground(paramGraphics, paramJComponent);
/*     */     }
/* 146 */     super.paint(paramGraphics, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle, String paramString) {
/* 154 */     WindowsGraphicsUtils.paintText(paramGraphics, paramAbstractButton, paramRectangle, paramString, getTextShiftOffset());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3) {
/* 159 */     paramGraphics.setColor(getFocusColor());
/* 160 */     BasicGraphicsUtils.drawDashedRect(paramGraphics, this.dashedRectGapX, this.dashedRectGapY, paramAbstractButton
/* 161 */         .getWidth() - this.dashedRectGapWidth, paramAbstractButton
/* 162 */         .getHeight() - this.dashedRectGapHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 169 */     Dimension dimension = super.getPreferredSize(paramJComponent);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 175 */     if (dimension != null && abstractButton.isFocusPainted()) {
/* 176 */       if (dimension.width % 2 == 0) dimension.width++; 
/* 177 */       if (dimension.height % 2 == 0) dimension.height++; 
/*     */     } 
/* 179 */     return dimension;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsToggleButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */