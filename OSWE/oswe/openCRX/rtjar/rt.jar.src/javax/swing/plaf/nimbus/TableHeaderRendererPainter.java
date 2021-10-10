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
/*     */ final class TableHeaderRendererPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_ENABLED_FOCUSED = 3;
/*     */   static final int BACKGROUND_MOUSEOVER = 4;
/*     */   static final int BACKGROUND_PRESSED = 5;
/*     */   static final int BACKGROUND_ENABLED_SORTED = 6;
/*     */   static final int BACKGROUND_ENABLED_FOCUSED_SORTED = 7;
/*     */   static final int BACKGROUND_DISABLED_SORTED = 8;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  53 */   private Path2D path = new Path2D.Float();
/*  54 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  55 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  56 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private Color color1 = decodeColor("nimbusBorder", -0.013888836F, 5.823001E-4F, -0.12941176F, 0);
/*  62 */   private Color color2 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.08625447F, 0.062745094F, 0);
/*  63 */   private Color color3 = decodeColor("nimbusBlueGrey", -0.013888836F, -0.028334536F, -0.17254901F, 0);
/*  64 */   private Color color4 = decodeColor("nimbusBlueGrey", -0.013888836F, -0.029445238F, -0.16470587F, 0);
/*  65 */   private Color color5 = decodeColor("nimbusBlueGrey", -0.02020204F, -0.053531498F, 0.011764705F, 0);
/*  66 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.10655806F, 0.24313724F, 0);
/*  67 */   private Color color7 = decodeColor("nimbusBlueGrey", 0.0F, -0.08455229F, 0.1607843F, 0);
/*  68 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.0F, -0.07016757F, 0.12941176F, 0);
/*  69 */   private Color color9 = decodeColor("nimbusBlueGrey", 0.0F, -0.07466974F, 0.23921567F, 0);
/*  70 */   private Color color10 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  71 */   private Color color11 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.10658931F, 0.25098038F, 0);
/*  72 */   private Color color12 = decodeColor("nimbusBlueGrey", 0.0F, -0.08613607F, 0.21960783F, 0);
/*  73 */   private Color color13 = decodeColor("nimbusBlueGrey", 0.0F, -0.07333623F, 0.20392156F, 0);
/*  74 */   private Color color14 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  75 */   private Color color15 = decodeColor("nimbusBlueGrey", -0.00505054F, -0.05960039F, 0.10196078F, 0);
/*  76 */   private Color color16 = decodeColor("nimbusBlueGrey", 0.0F, -0.017742813F, 0.015686274F, 0);
/*  77 */   private Color color17 = decodeColor("nimbusBlueGrey", -0.0027777553F, -0.0018306673F, -0.02352941F, 0);
/*  78 */   private Color color18 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.020436227F, 0.12549019F, 0);
/*  79 */   private Color color19 = decodeColor("nimbusBase", -0.023096085F, -0.62376213F, 0.4352941F, 0);
/*  80 */   private Color color20 = decodeColor("nimbusBase", -0.0012707114F, -0.50901747F, 0.31764704F, 0);
/*  81 */   private Color color21 = decodeColor("nimbusBase", -0.002461195F, -0.47139505F, 0.2862745F, 0);
/*  82 */   private Color color22 = decodeColor("nimbusBase", -0.0051222444F, -0.49103343F, 0.372549F, 0);
/*  83 */   private Color color23 = decodeColor("nimbusBase", -8.738637E-4F, -0.49872798F, 0.3098039F, 0);
/*  84 */   private Color color24 = decodeColor("nimbusBase", -2.2029877E-4F, -0.4916465F, 0.37647057F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public TableHeaderRendererPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  92 */     this.state = paramInt;
/*  93 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  99 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 102 */     switch (this.state) { case 1:
/* 103 */         paintBackgroundDisabled(paramGraphics2D); break;
/* 104 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/* 105 */       case 3: paintBackgroundEnabledAndFocused(paramGraphics2D); break;
/* 106 */       case 4: paintBackgroundMouseOver(paramGraphics2D); break;
/* 107 */       case 5: paintBackgroundPressed(paramGraphics2D); break;
/* 108 */       case 6: paintBackgroundEnabledAndSorted(paramGraphics2D); break;
/* 109 */       case 7: paintBackgroundEnabledAndFocusedAndSorted(paramGraphics2D); break;
/* 110 */       case 8: paintBackgroundDisabledAndSorted(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 119 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 123 */     this.rect = decodeRect1();
/* 124 */     paramGraphics2D.setPaint(this.color1);
/* 125 */     paramGraphics2D.fill(this.rect);
/* 126 */     this.rect = decodeRect2();
/* 127 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 128 */     paramGraphics2D.fill(this.rect);
/* 129 */     this.rect = decodeRect3();
/* 130 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 131 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 136 */     this.rect = decodeRect1();
/* 137 */     paramGraphics2D.setPaint(this.color1);
/* 138 */     paramGraphics2D.fill(this.rect);
/* 139 */     this.rect = decodeRect2();
/* 140 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 141 */     paramGraphics2D.fill(this.rect);
/* 142 */     this.rect = decodeRect3();
/* 143 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 144 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndFocused(Graphics2D paramGraphics2D) {
/* 149 */     this.rect = decodeRect1();
/* 150 */     paramGraphics2D.setPaint(this.color1);
/* 151 */     paramGraphics2D.fill(this.rect);
/* 152 */     this.rect = decodeRect2();
/* 153 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 154 */     paramGraphics2D.fill(this.rect);
/* 155 */     this.rect = decodeRect3();
/* 156 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 157 */     paramGraphics2D.fill(this.rect);
/* 158 */     this.path = decodePath1();
/* 159 */     paramGraphics2D.setPaint(this.color10);
/* 160 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 165 */     this.rect = decodeRect1();
/* 166 */     paramGraphics2D.setPaint(this.color1);
/* 167 */     paramGraphics2D.fill(this.rect);
/* 168 */     this.rect = decodeRect2();
/* 169 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 170 */     paramGraphics2D.fill(this.rect);
/* 171 */     this.rect = decodeRect3();
/* 172 */     paramGraphics2D.setPaint(decodeGradient3(this.rect));
/* 173 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 178 */     this.rect = decodeRect1();
/* 179 */     paramGraphics2D.setPaint(this.color1);
/* 180 */     paramGraphics2D.fill(this.rect);
/* 181 */     this.rect = decodeRect2();
/* 182 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 183 */     paramGraphics2D.fill(this.rect);
/* 184 */     this.rect = decodeRect3();
/* 185 */     paramGraphics2D.setPaint(decodeGradient4(this.rect));
/* 186 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndSorted(Graphics2D paramGraphics2D) {
/* 191 */     this.rect = decodeRect1();
/* 192 */     paramGraphics2D.setPaint(this.color1);
/* 193 */     paramGraphics2D.fill(this.rect);
/* 194 */     this.rect = decodeRect2();
/* 195 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 196 */     paramGraphics2D.fill(this.rect);
/* 197 */     this.rect = decodeRect3();
/* 198 */     paramGraphics2D.setPaint(decodeGradient5(this.rect));
/* 199 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndFocusedAndSorted(Graphics2D paramGraphics2D) {
/* 204 */     this.rect = decodeRect1();
/* 205 */     paramGraphics2D.setPaint(this.color1);
/* 206 */     paramGraphics2D.fill(this.rect);
/* 207 */     this.rect = decodeRect2();
/* 208 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 209 */     paramGraphics2D.fill(this.rect);
/* 210 */     this.rect = decodeRect3();
/* 211 */     paramGraphics2D.setPaint(decodeGradient6(this.rect));
/* 212 */     paramGraphics2D.fill(this.rect);
/* 213 */     this.path = decodePath1();
/* 214 */     paramGraphics2D.setPaint(this.color10);
/* 215 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabledAndSorted(Graphics2D paramGraphics2D) {
/* 220 */     this.rect = decodeRect1();
/* 221 */     paramGraphics2D.setPaint(this.color1);
/* 222 */     paramGraphics2D.fill(this.rect);
/* 223 */     this.rect = decodeRect2();
/* 224 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 225 */     paramGraphics2D.fill(this.rect);
/* 226 */     this.rect = decodeRect3();
/* 227 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 228 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 235 */     this.rect.setRect(decodeX(0.0F), 
/* 236 */         decodeY(2.8F), (
/* 237 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 238 */         decodeY(3.0F) - decodeY(2.8F)));
/* 239 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 243 */     this.rect.setRect(decodeX(2.8F), 
/* 244 */         decodeY(0.0F), (
/* 245 */         decodeX(3.0F) - decodeX(2.8F)), (
/* 246 */         decodeY(2.8F) - decodeY(0.0F)));
/* 247 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 251 */     this.rect.setRect(decodeX(0.0F), 
/* 252 */         decodeY(0.0F), (
/* 253 */         decodeX(2.8F) - decodeX(0.0F)), (
/* 254 */         decodeY(2.8F) - decodeY(0.0F)));
/* 255 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 259 */     this.path.reset();
/* 260 */     this.path.moveTo(decodeX(0.0F), decodeY(0.0F));
/* 261 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 262 */     this.path.lineTo(decodeX(3.0F), decodeY(3.0F));
/* 263 */     this.path.lineTo(decodeX(3.0F), decodeY(0.0F));
/* 264 */     this.path.lineTo(decodeX(0.24000001F), decodeY(0.0F));
/* 265 */     this.path.lineTo(decodeX(0.24000001F), decodeY(0.24000001F));
/* 266 */     this.path.lineTo(decodeX(2.7599998F), decodeY(0.24000001F));
/* 267 */     this.path.lineTo(decodeX(2.7599998F), decodeY(2.7599998F));
/* 268 */     this.path.lineTo(decodeX(0.24000001F), decodeY(2.7599998F));
/* 269 */     this.path.lineTo(decodeX(0.24000001F), decodeY(0.0F));
/* 270 */     this.path.lineTo(decodeX(0.0F), decodeY(0.0F));
/* 271 */     this.path.closePath();
/* 272 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 278 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 279 */     float f1 = (float)rectangle2D.getX();
/* 280 */     float f2 = (float)rectangle2D.getY();
/* 281 */     float f3 = (float)rectangle2D.getWidth();
/* 282 */     float f4 = (float)rectangle2D.getHeight();
/* 283 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.14441223F, 0.43703705F, 0.59444445F, 0.75185186F, 0.8759259F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 286 */           decodeColor(this.color2, this.color3, 0.5F), this.color3, 
/*     */           
/* 288 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 290 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 295 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 296 */     float f1 = (float)rectangle2D.getX();
/* 297 */     float f2 = (float)rectangle2D.getY();
/* 298 */     float f3 = (float)rectangle2D.getWidth();
/* 299 */     float f4 = (float)rectangle2D.getHeight();
/* 300 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.07147767F, 0.2888889F, 0.5490909F, 0.7037037F, 0.8518518F, 1.0F }, new Color[] { this.color6, 
/*     */ 
/*     */           
/* 303 */           decodeColor(this.color6, this.color7, 0.5F), this.color7, 
/*     */           
/* 305 */           decodeColor(this.color7, this.color8, 0.5F), this.color8, 
/*     */           
/* 307 */           decodeColor(this.color8, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 312 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 313 */     float f1 = (float)rectangle2D.getX();
/* 314 */     float f2 = (float)rectangle2D.getY();
/* 315 */     float f3 = (float)rectangle2D.getWidth();
/* 316 */     float f4 = (float)rectangle2D.getHeight();
/* 317 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.07147767F, 0.2888889F, 0.5490909F, 0.7037037F, 0.7919203F, 0.88013697F }, new Color[] { this.color11, 
/*     */ 
/*     */           
/* 320 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*     */           
/* 322 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*     */           
/* 324 */           decodeColor(this.color13, this.color14, 0.5F), this.color14 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 329 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 330 */     float f1 = (float)rectangle2D.getX();
/* 331 */     float f2 = (float)rectangle2D.getY();
/* 332 */     float f3 = (float)rectangle2D.getWidth();
/* 333 */     float f4 = (float)rectangle2D.getHeight();
/* 334 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.07147767F, 0.2888889F, 0.5490909F, 0.7037037F, 0.8518518F, 1.0F }, new Color[] { this.color15, 
/*     */ 
/*     */           
/* 337 */           decodeColor(this.color15, this.color16, 0.5F), this.color16, 
/*     */           
/* 339 */           decodeColor(this.color16, this.color17, 0.5F), this.color17, 
/*     */           
/* 341 */           decodeColor(this.color17, this.color18, 0.5F), this.color18 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 346 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 347 */     float f1 = (float)rectangle2D.getX();
/* 348 */     float f2 = (float)rectangle2D.getY();
/* 349 */     float f3 = (float)rectangle2D.getWidth();
/* 350 */     float f4 = (float)rectangle2D.getHeight();
/* 351 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.08049711F, 0.32534248F, 0.56267816F, 0.7037037F, 0.83986557F, 0.97602737F }, new Color[] { this.color19, 
/*     */ 
/*     */           
/* 354 */           decodeColor(this.color19, this.color20, 0.5F), this.color20, 
/*     */           
/* 356 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 358 */           decodeColor(this.color21, this.color22, 0.5F), this.color22 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 363 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 364 */     float f1 = (float)rectangle2D.getX();
/* 365 */     float f2 = (float)rectangle2D.getY();
/* 366 */     float f3 = (float)rectangle2D.getWidth();
/* 367 */     float f4 = (float)rectangle2D.getHeight();
/* 368 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.07147767F, 0.2888889F, 0.5490909F, 0.7037037F, 0.8518518F, 1.0F }, new Color[] { this.color19, 
/*     */ 
/*     */           
/* 371 */           decodeColor(this.color19, this.color23, 0.5F), this.color23, 
/*     */           
/* 373 */           decodeColor(this.color23, this.color21, 0.5F), this.color21, 
/*     */           
/* 375 */           decodeColor(this.color21, this.color24, 0.5F), this.color24 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/TableHeaderRendererPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */