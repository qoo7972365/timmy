/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicButtonListener;
/*     */ import javax.swing.plaf.basic.BasicButtonUI;
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
/*     */ public class MotifButtonUI
/*     */   extends BasicButtonUI
/*     */ {
/*     */   protected Color selectColor;
/*     */   private boolean defaults_initialized = false;
/*  55 */   private static final Object MOTIF_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  61 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  63 */     MotifButtonUI motifButtonUI = (MotifButtonUI)appContext.get(MOTIF_BUTTON_UI_KEY);
/*  64 */     if (motifButtonUI == null) {
/*  65 */       motifButtonUI = new MotifButtonUI();
/*  66 */       appContext.put(MOTIF_BUTTON_UI_KEY, motifButtonUI);
/*     */     } 
/*  68 */     return motifButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicButtonListener createButtonListener(AbstractButton paramAbstractButton) {
/*  75 */     return new MotifButtonListener(paramAbstractButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(AbstractButton paramAbstractButton) {
/*  82 */     super.installDefaults(paramAbstractButton);
/*  83 */     if (!this.defaults_initialized) {
/*  84 */       this.selectColor = UIManager.getColor(getPropertyPrefix() + "select");
/*  85 */       this.defaults_initialized = true;
/*     */     } 
/*  87 */     LookAndFeel.installProperty(paramAbstractButton, "opaque", Boolean.FALSE);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/*  91 */     super.uninstallDefaults(paramAbstractButton);
/*  92 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getSelectColor() {
/* 100 */     return this.selectColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 107 */     fillContentArea(paramGraphics, (AbstractButton)paramJComponent, paramJComponent.getBackground());
/* 108 */     super.paint(paramGraphics, paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintIcon(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/* 113 */     Shape shape = paramGraphics.getClip();
/*     */     
/* 115 */     Rectangle rectangle1 = AbstractBorder.getInteriorRectangle(paramJComponent, paramJComponent.getBorder(), 0, 0, paramJComponent
/* 116 */         .getWidth(), paramJComponent.getHeight());
/*     */     
/* 118 */     Rectangle rectangle2 = shape.getBounds();
/*     */     
/* 120 */     rectangle1 = SwingUtilities.computeIntersection(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height, rectangle1);
/*     */     
/* 122 */     paramGraphics.setClip(rectangle1);
/* 123 */     super.paintIcon(paramGraphics, paramJComponent, paramRectangle);
/* 124 */     paramGraphics.setClip(shape);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3) {}
/*     */ 
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, AbstractButton paramAbstractButton) {
/* 133 */     fillContentArea(paramGraphics, paramAbstractButton, this.selectColor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillContentArea(Graphics paramGraphics, AbstractButton paramAbstractButton, Color paramColor) {
/* 139 */     if (paramAbstractButton.isContentAreaFilled()) {
/* 140 */       Insets insets1 = paramAbstractButton.getMargin();
/* 141 */       Insets insets2 = paramAbstractButton.getInsets();
/* 142 */       Dimension dimension = paramAbstractButton.getSize();
/* 143 */       paramGraphics.setColor(paramColor);
/* 144 */       paramGraphics.fillRect(insets2.left - insets1.left, insets2.top - insets1.top, dimension.width - insets2.left - insets1.left - insets2.right - insets1.right, dimension.height - insets2.top - insets1.top - insets2.bottom - insets1.bottom);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */