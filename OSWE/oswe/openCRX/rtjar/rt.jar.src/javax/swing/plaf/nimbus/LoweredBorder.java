/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.border.Border;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LoweredBorder
/*     */   extends AbstractRegionPainter
/*     */   implements Border
/*     */ {
/*     */   private static final int IMG_SIZE = 30;
/*     */   private static final int RADIUS = 13;
/*  48 */   private static final Insets INSETS = new Insets(10, 10, 10, 10);
/*  49 */   private static final AbstractRegionPainter.PaintContext PAINT_CONTEXT = new AbstractRegionPainter.PaintContext(INSETS, new Dimension(30, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.147483647E9D, 2.147483647E9D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] getExtendedCacheKeys(JComponent paramJComponent) {
/*  59 */     (new Object[1])[0] = paramJComponent
/*  60 */       .getBackground(); return (paramJComponent != null) ? new Object[1] : null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  88 */     Color color = (paramJComponent == null) ? Color.BLACK : paramJComponent.getBackground();
/*  89 */     BufferedImage bufferedImage1 = new BufferedImage(30, 30, 2);
/*     */     
/*  91 */     BufferedImage bufferedImage2 = new BufferedImage(30, 30, 2);
/*     */ 
/*     */     
/*  94 */     Graphics2D graphics2D = (Graphics2D)bufferedImage1.getGraphics();
/*  95 */     graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */     
/*  97 */     graphics2D.setColor(color);
/*  98 */     graphics2D.fillRoundRect(2, 0, 26, 26, 13, 13);
/*  99 */     graphics2D.dispose();
/*     */     
/* 101 */     InnerShadowEffect innerShadowEffect = new InnerShadowEffect();
/* 102 */     innerShadowEffect.setDistance(1);
/* 103 */     innerShadowEffect.setSize(3);
/* 104 */     innerShadowEffect.setColor(getLighter(color, 2.1F));
/* 105 */     innerShadowEffect.setAngle(90);
/* 106 */     innerShadowEffect.applyEffect(bufferedImage1, bufferedImage2, 30, 30);
/*     */     
/* 108 */     graphics2D = (Graphics2D)bufferedImage2.getGraphics();
/* 109 */     graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */     
/* 111 */     graphics2D.setClip(0, 28, 30, 1);
/* 112 */     graphics2D.setColor(getLighter(color, 0.9F));
/* 113 */     graphics2D.drawRoundRect(2, 1, 25, 25, 13, 13);
/* 114 */     graphics2D.dispose();
/*     */     
/* 116 */     if (paramInt1 != 30 || paramInt2 != 30) {
/* 117 */       ImageScalingHelper.paint(paramGraphics2D, 0, 0, paramInt1, paramInt2, bufferedImage2, INSETS, INSETS, ImageScalingHelper.PaintType.PAINT9_STRETCH, 512);
/*     */     }
/*     */     else {
/*     */       
/* 121 */       paramGraphics2D.drawImage(bufferedImage2, 0, 0, paramJComponent);
/*     */     } 
/* 123 */     bufferedImage1 = null;
/* 124 */     bufferedImage2 = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractRegionPainter.PaintContext getPaintContext() {
/* 142 */     return PAINT_CONTEXT;
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
/*     */   public Insets getBorderInsets(Component paramComponent) {
/* 154 */     return (Insets)INSETS.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBorderOpaque() {
/* 162 */     return false;
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
/*     */ 
/*     */   
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 178 */     JComponent jComponent = (paramComponent instanceof JComponent) ? (JComponent)paramComponent : null;
/* 179 */     if (paramGraphics instanceof Graphics2D) {
/* 180 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 181 */       graphics2D.translate(paramInt1, paramInt2);
/* 182 */       paint(graphics2D, jComponent, paramInt3, paramInt4);
/* 183 */       graphics2D.translate(-paramInt1, -paramInt2);
/*     */     } else {
/* 185 */       BufferedImage bufferedImage = new BufferedImage(30, 30, 2);
/*     */       
/* 187 */       Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
/* 188 */       paint(graphics2D, jComponent, paramInt3, paramInt4);
/* 189 */       graphics2D.dispose();
/* 190 */       ImageScalingHelper.paint(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, bufferedImage, INSETS, INSETS, ImageScalingHelper.PaintType.PAINT9_STRETCH, 512);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Color getLighter(Color paramColor, float paramFloat) {
/* 197 */     return new Color(Math.min((int)(paramColor.getRed() / paramFloat), 255), 
/* 198 */         Math.min((int)(paramColor.getGreen() / paramFloat), 255), 
/* 199 */         Math.min((int)(paramColor.getBlue() / paramFloat), 255));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/LoweredBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */