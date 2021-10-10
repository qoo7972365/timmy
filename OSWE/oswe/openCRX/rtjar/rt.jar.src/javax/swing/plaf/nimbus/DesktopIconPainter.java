/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.RoundRectangle2D;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DesktopIconPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_ENABLED = 1;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  46 */   private Path2D path = new Path2D.Float();
/*  47 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  48 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  49 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private Color color1 = decodeColor("nimbusBase", 0.02551502F, -0.47885156F, -0.34901965F, 0);
/*  55 */   private Color color2 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.102261856F, 0.20392156F, 0);
/*  56 */   private Color color3 = decodeColor("nimbusBlueGrey", 0.0F, -0.0682728F, 0.09019607F, 0);
/*  57 */   private Color color4 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.088974595F, 0.16470587F, 0);
/*  58 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.0F, -0.029445238F, -0.019607842F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public DesktopIconPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  66 */     this.state = paramInt;
/*  67 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  73 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  76 */     switch (this.state) { case 1:
/*  77 */         paintBackgroundEnabled(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  86 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/*  90 */     this.roundRect = decodeRoundRect1();
/*  91 */     paramGraphics2D.setPaint(this.color1);
/*  92 */     paramGraphics2D.fill(this.roundRect);
/*  93 */     this.roundRect = decodeRoundRect2();
/*  94 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/*  95 */     paramGraphics2D.fill(this.roundRect);
/*  96 */     this.rect = decodeRect1();
/*  97 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/*  98 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 105 */     this.roundRect.setRoundRect(decodeX(0.4F), 
/* 106 */         decodeY(0.0F), (
/* 107 */         decodeX(2.8F) - decodeX(0.4F)), (
/* 108 */         decodeY(2.6F) - decodeY(0.0F)), 4.833333492279053D, 4.833333492279053D);
/*     */     
/* 110 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 114 */     this.roundRect.setRoundRect(decodeX(0.6F), 
/* 115 */         decodeY(0.2F), (
/* 116 */         decodeX(2.8F) - decodeX(0.6F)), (
/* 117 */         decodeY(2.4F) - decodeY(0.2F)), 3.0999999046325684D, 3.0999999046325684D);
/*     */     
/* 119 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 123 */     this.rect.setRect(decodeX(0.8F), 
/* 124 */         decodeY(0.4F), (
/* 125 */         decodeX(2.4F) - decodeX(0.8F)), (
/* 126 */         decodeY(2.2F) - decodeY(0.4F)));
/* 127 */     return this.rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 133 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 134 */     float f1 = (float)rectangle2D.getX();
/* 135 */     float f2 = (float)rectangle2D.getY();
/* 136 */     float f3 = (float)rectangle2D.getWidth();
/* 137 */     float f4 = (float)rectangle2D.getHeight();
/* 138 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 141 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 146 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 147 */     float f1 = (float)rectangle2D.getX();
/* 148 */     float f2 = (float)rectangle2D.getY();
/* 149 */     float f3 = (float)rectangle2D.getWidth();
/* 150 */     float f4 = (float)rectangle2D.getHeight();
/* 151 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.24F, 1.0F }, new Color[] { this.color4, 
/*     */ 
/*     */           
/* 154 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/DesktopIconPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */