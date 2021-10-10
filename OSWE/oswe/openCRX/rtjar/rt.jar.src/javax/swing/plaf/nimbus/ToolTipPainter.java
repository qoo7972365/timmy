/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
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
/*     */ 
/*     */ 
/*     */ final class ToolTipPainter
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
/*  54 */   private Color color1 = decodeColor("nimbusBorder", 0.0F, 0.0F, 0.0F, 0);
/*  55 */   private Color color2 = decodeColor("info", 0.0F, 0.0F, 0.0F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolTipPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  63 */     this.state = paramInt;
/*  64 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  70 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  73 */     switch (this.state) { case 1:
/*  74 */         paintBackgroundEnabled(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  83 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/*  87 */     this.rect = decodeRect1();
/*  88 */     paramGraphics2D.setPaint(this.color1);
/*  89 */     paramGraphics2D.fill(this.rect);
/*  90 */     this.rect = decodeRect2();
/*  91 */     paramGraphics2D.setPaint(this.color1);
/*  92 */     paramGraphics2D.fill(this.rect);
/*  93 */     this.rect = decodeRect3();
/*  94 */     paramGraphics2D.setPaint(this.color1);
/*  95 */     paramGraphics2D.fill(this.rect);
/*  96 */     this.rect = decodeRect4();
/*  97 */     paramGraphics2D.setPaint(this.color1);
/*  98 */     paramGraphics2D.fill(this.rect);
/*  99 */     this.rect = decodeRect5();
/* 100 */     paramGraphics2D.setPaint(this.color2);
/* 101 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 108 */     this.rect.setRect(decodeX(2.0F), 
/* 109 */         decodeY(1.0F), (
/* 110 */         decodeX(3.0F) - decodeX(2.0F)), (
/* 111 */         decodeY(2.0F) - decodeY(1.0F)));
/* 112 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 116 */     this.rect.setRect(decodeX(0.0F), 
/* 117 */         decodeY(1.0F), (
/* 118 */         decodeX(1.0F) - decodeX(0.0F)), (
/* 119 */         decodeY(2.0F) - decodeY(1.0F)));
/* 120 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 124 */     this.rect.setRect(decodeX(0.0F), 
/* 125 */         decodeY(2.0F), (
/* 126 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 127 */         decodeY(3.0F) - decodeY(2.0F)));
/* 128 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect4() {
/* 132 */     this.rect.setRect(decodeX(0.0F), 
/* 133 */         decodeY(0.0F), (
/* 134 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 135 */         decodeY(1.0F) - decodeY(0.0F)));
/* 136 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect5() {
/* 140 */     this.rect.setRect(decodeX(1.0F), 
/* 141 */         decodeY(1.0F), (
/* 142 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 143 */         decodeY(2.0F) - decodeY(1.0F)));
/* 144 */     return this.rect;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ToolTipPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */