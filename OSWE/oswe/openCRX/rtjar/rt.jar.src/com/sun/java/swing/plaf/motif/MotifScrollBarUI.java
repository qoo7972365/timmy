/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicScrollBarUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifScrollBarUI
/*     */   extends BasicScrollBarUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  59 */     return new MotifScrollBarUI();
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  63 */     Insets insets = paramJComponent.getInsets();
/*  64 */     int i = insets.left + insets.right;
/*  65 */     int j = insets.top + insets.bottom;
/*  66 */     return (this.scrollbar.getOrientation() == 1) ? new Dimension(i + 11, j + 33) : new Dimension(i + 33, j + 11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createDecreaseButton(int paramInt) {
/*  72 */     return new MotifScrollBarButton(paramInt);
/*     */   }
/*     */   
/*     */   protected JButton createIncreaseButton(int paramInt) {
/*  76 */     return new MotifScrollBarButton(paramInt);
/*     */   }
/*     */   
/*     */   public void paintTrack(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/*  80 */     paramGraphics.setColor(this.trackColor);
/*  81 */     paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */   
/*     */   public void paintThumb(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/*  85 */     if (paramRectangle.isEmpty() || !this.scrollbar.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     int i = paramRectangle.width;
/*  90 */     int j = paramRectangle.height;
/*     */     
/*  92 */     paramGraphics.translate(paramRectangle.x, paramRectangle.y);
/*  93 */     paramGraphics.setColor(this.thumbColor);
/*  94 */     paramGraphics.fillRect(0, 0, i - 1, j - 1);
/*     */     
/*  96 */     paramGraphics.setColor(this.thumbHighlightColor);
/*  97 */     SwingUtilities2.drawVLine(paramGraphics, 0, 0, j - 1);
/*  98 */     SwingUtilities2.drawHLine(paramGraphics, 1, i - 1, 0);
/*     */     
/* 100 */     paramGraphics.setColor(this.thumbLightShadowColor);
/* 101 */     SwingUtilities2.drawHLine(paramGraphics, 1, i - 1, j - 1);
/* 102 */     SwingUtilities2.drawVLine(paramGraphics, i - 1, 1, j - 2);
/*     */     
/* 104 */     paramGraphics.translate(-paramRectangle.x, -paramRectangle.y);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifScrollBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */