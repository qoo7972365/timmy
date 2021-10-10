/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicSliderUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifSliderUI
/*     */   extends BasicSliderUI
/*     */ {
/*  54 */   static final Dimension PREFERRED_HORIZONTAL_SIZE = new Dimension(164, 15);
/*  55 */   static final Dimension PREFERRED_VERTICAL_SIZE = new Dimension(15, 164);
/*     */   
/*  57 */   static final Dimension MINIMUM_HORIZONTAL_SIZE = new Dimension(43, 15);
/*  58 */   static final Dimension MINIMUM_VERTICAL_SIZE = new Dimension(15, 43);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MotifSliderUI(JSlider paramJSlider) {
/*  64 */     super(paramJSlider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  71 */     return new MotifSliderUI((JSlider)paramJComponent);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredHorizontalSize() {
/*  75 */     return PREFERRED_HORIZONTAL_SIZE;
/*     */   }
/*     */   
/*     */   public Dimension getPreferredVerticalSize() {
/*  79 */     return PREFERRED_VERTICAL_SIZE;
/*     */   }
/*     */   
/*     */   public Dimension getMinimumHorizontalSize() {
/*  83 */     return MINIMUM_HORIZONTAL_SIZE;
/*     */   }
/*     */   
/*     */   public Dimension getMinimumVerticalSize() {
/*  87 */     return MINIMUM_VERTICAL_SIZE;
/*     */   }
/*     */   
/*     */   protected Dimension getThumbSize() {
/*  91 */     if (this.slider.getOrientation() == 0) {
/*  92 */       return new Dimension(30, 15);
/*     */     }
/*     */     
/*  95 */     return new Dimension(15, 30);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintFocus(Graphics paramGraphics) {}
/*     */ 
/*     */   
/*     */   public void paintTrack(Graphics paramGraphics) {}
/*     */ 
/*     */   
/*     */   public void paintThumb(Graphics paramGraphics) {
/* 106 */     Rectangle rectangle = this.thumbRect;
/*     */     
/* 108 */     int i = rectangle.x;
/* 109 */     int j = rectangle.y;
/* 110 */     int k = rectangle.width;
/* 111 */     int m = rectangle.height;
/*     */     
/* 113 */     if (this.slider.isEnabled()) {
/* 114 */       paramGraphics.setColor(this.slider.getForeground());
/*     */     }
/*     */     else {
/*     */       
/* 118 */       paramGraphics.setColor(this.slider.getForeground().darker());
/*     */     } 
/*     */     
/* 121 */     if (this.slider.getOrientation() == 0) {
/* 122 */       paramGraphics.translate(i, rectangle.y - 1);
/*     */ 
/*     */       
/* 125 */       paramGraphics.fillRect(0, 1, k, m - 1);
/*     */ 
/*     */       
/* 128 */       paramGraphics.setColor(getHighlightColor());
/* 129 */       SwingUtilities2.drawHLine(paramGraphics, 0, k - 1, 1);
/* 130 */       SwingUtilities2.drawVLine(paramGraphics, 0, 1, m);
/* 131 */       SwingUtilities2.drawVLine(paramGraphics, k / 2, 2, m - 1);
/*     */ 
/*     */       
/* 134 */       paramGraphics.setColor(getShadowColor());
/* 135 */       SwingUtilities2.drawHLine(paramGraphics, 0, k - 1, m);
/* 136 */       SwingUtilities2.drawVLine(paramGraphics, k - 1, 1, m);
/* 137 */       SwingUtilities2.drawVLine(paramGraphics, k / 2 - 1, 2, m);
/*     */       
/* 139 */       paramGraphics.translate(-i, -(rectangle.y - 1));
/*     */     } else {
/*     */       
/* 142 */       paramGraphics.translate(rectangle.x - 1, 0);
/*     */ 
/*     */       
/* 145 */       paramGraphics.fillRect(1, j, k - 1, m);
/*     */ 
/*     */       
/* 148 */       paramGraphics.setColor(getHighlightColor());
/* 149 */       SwingUtilities2.drawHLine(paramGraphics, 1, k, j);
/* 150 */       SwingUtilities2.drawVLine(paramGraphics, 1, j + 1, j + m - 1);
/* 151 */       SwingUtilities2.drawHLine(paramGraphics, 2, k - 1, j + m / 2);
/*     */ 
/*     */       
/* 154 */       paramGraphics.setColor(getShadowColor());
/* 155 */       SwingUtilities2.drawHLine(paramGraphics, 2, k, j + m - 1);
/* 156 */       SwingUtilities2.drawVLine(paramGraphics, k, j + m - 1, j);
/* 157 */       SwingUtilities2.drawHLine(paramGraphics, 2, k - 1, j + m / 2 - 1);
/*     */       
/* 159 */       paramGraphics.translate(-(rectangle.x - 1), 0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifSliderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */