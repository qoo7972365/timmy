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
/*     */ final class PopupMenuPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  47 */   private Path2D path = new Path2D.Float();
/*  48 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  49 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  50 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, -0.39607844F, 0);
/*  56 */   private Color color2 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*  57 */   private Color color3 = decodeColor("nimbusBase", 0.021348298F, -0.6150531F, 0.39999998F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public PopupMenuPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  65 */     this.state = paramInt;
/*  66 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  72 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  75 */     switch (this.state) { case 1:
/*  76 */         paintBackgroundDisabled(paramGraphics2D); break;
/*  77 */       case 2: paintBackgroundEnabled(paramGraphics2D);
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
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/*  90 */     this.rect = decodeRect1();
/*  91 */     paramGraphics2D.setPaint(this.color1);
/*  92 */     paramGraphics2D.fill(this.rect);
/*  93 */     this.rect = decodeRect2();
/*  94 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/*  95 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 100 */     this.rect = decodeRect3();
/* 101 */     paramGraphics2D.setPaint(this.color1);
/* 102 */     paramGraphics2D.fill(this.rect);
/* 103 */     this.rect = decodeRect4();
/* 104 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 105 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 112 */     this.rect.setRect(decodeX(1.0F), 
/* 113 */         decodeY(0.0F), (
/* 114 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 115 */         decodeY(3.0F) - decodeY(0.0F)));
/* 116 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 120 */     this.rect.setRect(decodeX(1.0045455F), 
/* 121 */         decodeY(0.11111111F), (
/* 122 */         decodeX(1.9954545F) - decodeX(1.0045455F)), (
/* 123 */         decodeY(2.909091F) - decodeY(0.11111111F)));
/* 124 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 128 */     this.rect.setRect(decodeX(0.0F), 
/* 129 */         decodeY(0.0F), (
/* 130 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 131 */         decodeY(3.0F) - decodeY(0.0F)));
/* 132 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect4() {
/* 136 */     this.rect.setRect(decodeX(0.5F), 
/* 137 */         decodeY(0.09090909F), (
/* 138 */         decodeX(2.5F) - decodeX(0.5F)), (
/* 139 */         decodeY(2.909091F) - decodeY(0.09090909F)));
/* 140 */     return this.rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 146 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 147 */     float f1 = (float)rectangle2D.getX();
/* 148 */     float f2 = (float)rectangle2D.getY();
/* 149 */     float f3 = (float)rectangle2D.getWidth();
/* 150 */     float f4 = (float)rectangle2D.getHeight();
/* 151 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.003F, 0.02F, 0.5F, 0.98F, 0.996F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 154 */           decodeColor(this.color2, this.color3, 0.5F), this.color3, 
/*     */           
/* 156 */           decodeColor(this.color3, this.color3, 0.5F), this.color3, 
/*     */           
/* 158 */           decodeColor(this.color3, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/PopupMenuPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */