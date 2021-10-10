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
/*     */ final class SliderTrackPainter
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
/*  55 */   private Color color1 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -245);
/*  56 */   private Color color2 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.061265234F, 0.05098039F, 0);
/*  57 */   private Color color3 = decodeColor("nimbusBlueGrey", 0.01010108F, -0.059835073F, 0.10588235F, 0);
/*  58 */   private Color color4 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.061982628F, 0.062745094F, 0);
/*  59 */   private Color color5 = decodeColor("nimbusBlueGrey", -0.00505054F, -0.058639523F, 0.086274505F, 0);
/*  60 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -111);
/*  61 */   private Color color7 = decodeColor("nimbusBlueGrey", 0.0F, -0.034093194F, -0.12941176F, 0);
/*  62 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.01111114F, -0.023821115F, -0.06666666F, 0);
/*  63 */   private Color color9 = decodeColor("nimbusBlueGrey", -0.008547008F, -0.03314536F, -0.086274505F, 0);
/*  64 */   private Color color10 = decodeColor("nimbusBlueGrey", 0.004273474F, -0.040256046F, -0.019607842F, 0);
/*  65 */   private Color color11 = decodeColor("nimbusBlueGrey", 0.0F, -0.03626889F, 0.04705882F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public SliderTrackPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  73 */     this.state = paramInt;
/*  74 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  80 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  83 */     switch (this.state) { case 1:
/*  84 */         paintBackgroundDisabled(paramGraphics2D); break;
/*  85 */       case 2: paintBackgroundEnabled(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  94 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/*  98 */     this.roundRect = decodeRoundRect1();
/*  99 */     paramGraphics2D.setPaint(this.color1);
/* 100 */     paramGraphics2D.fill(this.roundRect);
/* 101 */     this.roundRect = decodeRoundRect2();
/* 102 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 103 */     paramGraphics2D.fill(this.roundRect);
/* 104 */     this.roundRect = decodeRoundRect3();
/* 105 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 106 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 111 */     this.roundRect = decodeRoundRect4();
/* 112 */     paramGraphics2D.setPaint(this.color6);
/* 113 */     paramGraphics2D.fill(this.roundRect);
/* 114 */     this.roundRect = decodeRoundRect2();
/* 115 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 116 */     paramGraphics2D.fill(this.roundRect);
/* 117 */     this.roundRect = decodeRoundRect5();
/* 118 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 119 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 126 */     this.roundRect.setRoundRect(decodeX(0.2F), 
/* 127 */         decodeY(1.6F), (
/* 128 */         decodeX(2.8F) - decodeX(0.2F)), (
/* 129 */         decodeY(2.8333333F) - decodeY(1.6F)), 8.70588207244873D, 8.70588207244873D);
/*     */     
/* 131 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 135 */     this.roundRect.setRoundRect(decodeX(0.0F), 
/* 136 */         decodeY(1.0F), (
/* 137 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 138 */         decodeY(2.0F) - decodeY(1.0F)), 4.941176414489746D, 4.941176414489746D);
/*     */     
/* 140 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect3() {
/* 144 */     this.roundRect.setRoundRect(decodeX(0.29411763F), 
/* 145 */         decodeY(1.2F), (
/* 146 */         decodeX(2.7058823F) - decodeX(0.29411763F)), (
/* 147 */         decodeY(2.0F) - decodeY(1.2F)), 4.0D, 4.0D);
/*     */     
/* 149 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect4() {
/* 153 */     this.roundRect.setRoundRect(decodeX(0.2F), 
/* 154 */         decodeY(1.6F), (
/* 155 */         decodeX(2.8F) - decodeX(0.2F)), (
/* 156 */         decodeY(2.1666667F) - decodeY(1.6F)), 8.70588207244873D, 8.70588207244873D);
/*     */     
/* 158 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect5() {
/* 162 */     this.roundRect.setRoundRect(decodeX(0.28823528F), 
/* 163 */         decodeY(1.2F), (
/* 164 */         decodeX(2.7F) - decodeX(0.28823528F)), (
/* 165 */         decodeY(2.0F) - decodeY(1.2F)), 4.0D, 4.0D);
/*     */     
/* 167 */     return this.roundRect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 173 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 174 */     float f1 = (float)rectangle2D.getX();
/* 175 */     float f2 = (float)rectangle2D.getY();
/* 176 */     float f3 = (float)rectangle2D.getWidth();
/* 177 */     float f4 = (float)rectangle2D.getHeight();
/* 178 */     return decodeGradient(0.25F * f3 + f1, 0.07647059F * f4 + f2, 0.25F * f3 + f1, 0.9117647F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 181 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 186 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 187 */     float f1 = (float)rectangle2D.getX();
/* 188 */     float f2 = (float)rectangle2D.getY();
/* 189 */     float f3 = (float)rectangle2D.getWidth();
/* 190 */     float f4 = (float)rectangle2D.getHeight();
/* 191 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.13770053F, 0.27540106F, 0.63770056F, 1.0F }, new Color[] { this.color4, 
/*     */ 
/*     */           
/* 194 */           decodeColor(this.color4, this.color5, 0.5F), this.color5, 
/*     */           
/* 196 */           decodeColor(this.color5, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 201 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 202 */     float f1 = (float)rectangle2D.getX();
/* 203 */     float f2 = (float)rectangle2D.getY();
/* 204 */     float f3 = (float)rectangle2D.getWidth();
/* 205 */     float f4 = (float)rectangle2D.getHeight();
/* 206 */     return decodeGradient(0.25F * f3 + f1, 0.07647059F * f4 + f2, 0.25F * f3 + f1, 0.9117647F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 209 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 214 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 215 */     float f1 = (float)rectangle2D.getX();
/* 216 */     float f2 = (float)rectangle2D.getY();
/* 217 */     float f3 = (float)rectangle2D.getWidth();
/* 218 */     float f4 = (float)rectangle2D.getHeight();
/* 219 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.13770053F, 0.27540106F, 0.4906417F, 0.7058824F }, new Color[] { this.color9, 
/*     */ 
/*     */           
/* 222 */           decodeColor(this.color9, this.color10, 0.5F), this.color10, 
/*     */           
/* 224 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/SliderTrackPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */