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
/*     */ final class ToolBarButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_ENABLED = 1;
/*     */   static final int BACKGROUND_FOCUSED = 2;
/*     */   static final int BACKGROUND_MOUSEOVER = 3;
/*     */   static final int BACKGROUND_MOUSEOVER_FOCUSED = 4;
/*     */   static final int BACKGROUND_PRESSED = 5;
/*     */   static final int BACKGROUND_PRESSED_FOCUSED = 6;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  51 */   private Path2D path = new Path2D.Float();
/*  52 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  53 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  54 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private Color color1 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  60 */   private Color color2 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.06885965F, -0.36862746F, -153);
/*  61 */   private Color color3 = decodeColor("nimbusBlueGrey", 0.0F, -0.020974077F, -0.21960783F, 0);
/*  62 */   private Color color4 = decodeColor("nimbusBlueGrey", 0.0F, 0.11169591F, -0.53333336F, 0);
/*  63 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.10658931F, 0.25098038F, 0);
/*  64 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.0F, -0.098526314F, 0.2352941F, 0);
/*  65 */   private Color color7 = decodeColor("nimbusBlueGrey", 0.0F, -0.07333623F, 0.20392156F, 0);
/*  66 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  67 */   private Color color9 = decodeColor("nimbusBlueGrey", -0.00505054F, -0.05960039F, 0.10196078F, 0);
/*  68 */   private Color color10 = decodeColor("nimbusBlueGrey", -0.008547008F, -0.04772438F, 0.06666666F, 0);
/*  69 */   private Color color11 = decodeColor("nimbusBlueGrey", -0.0027777553F, -0.0018306673F, -0.02352941F, 0);
/*  70 */   private Color color12 = decodeColor("nimbusBlueGrey", -0.0027777553F, -0.0212406F, 0.13333333F, 0);
/*  71 */   private Color color13 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.030845039F, 0.23921567F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolBarButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  79 */     this.state = paramInt;
/*  80 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  86 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  89 */     switch (this.state) { case 2:
/*  90 */         paintBackgroundFocused(paramGraphics2D); break;
/*  91 */       case 3: paintBackgroundMouseOver(paramGraphics2D); break;
/*  92 */       case 4: paintBackgroundMouseOverAndFocused(paramGraphics2D); break;
/*  93 */       case 5: paintBackgroundPressed(paramGraphics2D); break;
/*  94 */       case 6: paintBackgroundPressedAndFocused(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 103 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundFocused(Graphics2D paramGraphics2D) {
/* 107 */     this.path = decodePath1();
/* 108 */     paramGraphics2D.setPaint(this.color1);
/* 109 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 114 */     this.roundRect = decodeRoundRect1();
/* 115 */     paramGraphics2D.setPaint(this.color2);
/* 116 */     paramGraphics2D.fill(this.roundRect);
/* 117 */     this.roundRect = decodeRoundRect2();
/* 118 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 119 */     paramGraphics2D.fill(this.roundRect);
/* 120 */     this.roundRect = decodeRoundRect3();
/* 121 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 122 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndFocused(Graphics2D paramGraphics2D) {
/* 127 */     this.roundRect = decodeRoundRect4();
/* 128 */     paramGraphics2D.setPaint(this.color1);
/* 129 */     paramGraphics2D.fill(this.roundRect);
/* 130 */     this.roundRect = decodeRoundRect2();
/* 131 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 132 */     paramGraphics2D.fill(this.roundRect);
/* 133 */     this.roundRect = decodeRoundRect3();
/* 134 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 135 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 140 */     this.roundRect = decodeRoundRect1();
/* 141 */     paramGraphics2D.setPaint(this.color2);
/* 142 */     paramGraphics2D.fill(this.roundRect);
/* 143 */     this.roundRect = decodeRoundRect2();
/* 144 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 145 */     paramGraphics2D.fill(this.roundRect);
/* 146 */     this.roundRect = decodeRoundRect3();
/* 147 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 148 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndFocused(Graphics2D paramGraphics2D) {
/* 153 */     this.roundRect = decodeRoundRect4();
/* 154 */     paramGraphics2D.setPaint(this.color1);
/* 155 */     paramGraphics2D.fill(this.roundRect);
/* 156 */     this.roundRect = decodeRoundRect2();
/* 157 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 158 */     paramGraphics2D.fill(this.roundRect);
/* 159 */     this.roundRect = decodeRoundRect3();
/* 160 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 161 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path2D decodePath1() {
/* 168 */     this.path.reset();
/* 169 */     this.path.moveTo(decodeX(1.4133738F), decodeY(0.120000005F));
/* 170 */     this.path.lineTo(decodeX(1.9893618F), decodeY(0.120000005F));
/* 171 */     this.path.curveTo(decodeAnchorX(1.9893618F, 3.0F), decodeAnchorY(0.120000005F, 0.0F), decodeAnchorX(2.8857148F, 0.0F), decodeAnchorY(1.0434783F, -3.0F), decodeX(2.8857148F), decodeY(1.0434783F));
/* 172 */     this.path.lineTo(decodeX(2.9F), decodeY(1.9565217F));
/* 173 */     this.path.curveTo(decodeAnchorX(2.9F, 0.0F), decodeAnchorY(1.9565217F, 3.0F), decodeAnchorX(1.9893618F, 3.0F), decodeAnchorY(2.8714287F, 0.0F), decodeX(1.9893618F), decodeY(2.8714287F));
/* 174 */     this.path.lineTo(decodeX(1.0106384F), decodeY(2.8714287F));
/* 175 */     this.path.curveTo(decodeAnchorX(1.0106384F, -3.0F), decodeAnchorY(2.8714287F, 0.0F), decodeAnchorX(0.120000005F, 0.0F), decodeAnchorY(1.9565217F, 3.0F), decodeX(0.120000005F), decodeY(1.9565217F));
/* 176 */     this.path.lineTo(decodeX(0.120000005F), decodeY(1.0465839F));
/* 177 */     this.path.curveTo(decodeAnchorX(0.120000005F, 0.0F), decodeAnchorY(1.0465839F, -3.0F), decodeAnchorX(1.0106384F, -3.0F), decodeAnchorY(0.120000005F, 0.0F), decodeX(1.0106384F), decodeY(0.120000005F));
/* 178 */     this.path.lineTo(decodeX(1.4148936F), decodeY(0.120000005F));
/* 179 */     this.path.lineTo(decodeX(1.4148936F), decodeY(0.4857143F));
/* 180 */     this.path.lineTo(decodeX(1.0106384F), decodeY(0.4857143F));
/* 181 */     this.path.curveTo(decodeAnchorX(1.0106384F, -1.9285715F), decodeAnchorY(0.4857143F, 0.0F), decodeAnchorX(0.47142857F, -0.044279482F), decodeAnchorY(1.0403726F, -2.429218F), decodeX(0.47142857F), decodeY(1.0403726F));
/* 182 */     this.path.lineTo(decodeX(0.47142857F), decodeY(1.9565217F));
/* 183 */     this.path.curveTo(decodeAnchorX(0.47142857F, 0.0F), decodeAnchorY(1.9565217F, 2.2142856F), decodeAnchorX(1.0106384F, -1.7857143F), decodeAnchorY(2.5142856F, 0.0F), decodeX(1.0106384F), decodeY(2.5142856F));
/* 184 */     this.path.lineTo(decodeX(1.9893618F), decodeY(2.5142856F));
/* 185 */     this.path.curveTo(decodeAnchorX(1.9893618F, 2.0714285F), decodeAnchorY(2.5142856F, 0.0F), decodeAnchorX(2.5F, 0.0F), decodeAnchorY(1.9565217F, 2.2142856F), decodeX(2.5F), decodeY(1.9565217F));
/* 186 */     this.path.lineTo(decodeX(2.5142853F), decodeY(1.0434783F));
/* 187 */     this.path.curveTo(decodeAnchorX(2.5142853F, 0.0F), decodeAnchorY(1.0434783F, -2.142857F), decodeAnchorX(1.9901216F, 2.142857F), decodeAnchorY(0.47142857F, 0.0F), decodeX(1.9901216F), decodeY(0.47142857F));
/* 188 */     this.path.lineTo(decodeX(1.4148936F), decodeY(0.4857143F));
/* 189 */     this.path.lineTo(decodeX(1.4133738F), decodeY(0.120000005F));
/* 190 */     this.path.closePath();
/* 191 */     return this.path;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 195 */     this.roundRect.setRoundRect(decodeX(0.4F), 
/* 196 */         decodeY(0.6F), (
/* 197 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 198 */         decodeY(2.8F) - decodeY(0.6F)), 12.0D, 12.0D);
/*     */     
/* 200 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 204 */     this.roundRect.setRoundRect(decodeX(0.4F), 
/* 205 */         decodeY(0.4F), (
/* 206 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 207 */         decodeY(2.6F) - decodeY(0.4F)), 12.0D, 12.0D);
/*     */     
/* 209 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect3() {
/* 213 */     this.roundRect.setRoundRect(decodeX(0.6F), 
/* 214 */         decodeY(0.6F), (
/* 215 */         decodeX(2.4F) - decodeX(0.6F)), (
/* 216 */         decodeY(2.4F) - decodeY(0.6F)), 9.0D, 9.0D);
/*     */     
/* 218 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect4() {
/* 222 */     this.roundRect.setRoundRect(decodeX(0.120000005F), 
/* 223 */         decodeY(0.120000005F), (
/* 224 */         decodeX(2.8800004F) - decodeX(0.120000005F)), (
/* 225 */         decodeY(2.8800004F) - decodeY(0.120000005F)), 13.0D, 13.0D);
/*     */     
/* 227 */     return this.roundRect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 233 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 234 */     float f1 = (float)rectangle2D.getX();
/* 235 */     float f2 = (float)rectangle2D.getY();
/* 236 */     float f3 = (float)rectangle2D.getWidth();
/* 237 */     float f4 = (float)rectangle2D.getHeight();
/* 238 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.09F, 0.52F, 0.95F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 241 */           decodeColor(this.color3, this.color4, 0.5F), this.color4 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 246 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 247 */     float f1 = (float)rectangle2D.getX();
/* 248 */     float f2 = (float)rectangle2D.getY();
/* 249 */     float f3 = (float)rectangle2D.getWidth();
/* 250 */     float f4 = (float)rectangle2D.getHeight();
/* 251 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.03F, 0.06F, 0.33F, 0.6F, 0.65F, 0.7F, 0.825F, 0.95F, 0.975F, 1.0F }, new Color[] { this.color5, 
/*     */ 
/*     */           
/* 254 */           decodeColor(this.color5, this.color6, 0.5F), this.color6, 
/*     */           
/* 256 */           decodeColor(this.color6, this.color7, 0.5F), this.color7, 
/*     */           
/* 258 */           decodeColor(this.color7, this.color7, 0.5F), this.color7, 
/*     */           
/* 260 */           decodeColor(this.color7, this.color8, 0.5F), this.color8, 
/*     */           
/* 262 */           decodeColor(this.color8, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 267 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 268 */     float f1 = (float)rectangle2D.getX();
/* 269 */     float f2 = (float)rectangle2D.getY();
/* 270 */     float f3 = (float)rectangle2D.getWidth();
/* 271 */     float f4 = (float)rectangle2D.getHeight();
/* 272 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.03F, 0.06F, 0.33F, 0.6F, 0.65F, 0.7F, 0.825F, 0.95F, 0.975F, 1.0F }, new Color[] { this.color9, 
/*     */ 
/*     */           
/* 275 */           decodeColor(this.color9, this.color10, 0.5F), this.color10, 
/*     */           
/* 277 */           decodeColor(this.color10, this.color11, 0.5F), this.color11, 
/*     */           
/* 279 */           decodeColor(this.color11, this.color11, 0.5F), this.color11, 
/*     */           
/* 281 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*     */           
/* 283 */           decodeColor(this.color12, this.color13, 0.5F), this.color13 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ToolBarButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */