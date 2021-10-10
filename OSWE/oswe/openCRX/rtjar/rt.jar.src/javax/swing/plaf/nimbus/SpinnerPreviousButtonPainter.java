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
/*     */ final class SpinnerPreviousButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_FOCUSED = 3;
/*     */   static final int BACKGROUND_MOUSEOVER_FOCUSED = 4;
/*     */   static final int BACKGROUND_PRESSED_FOCUSED = 5;
/*     */   static final int BACKGROUND_MOUSEOVER = 6;
/*     */   static final int BACKGROUND_PRESSED = 7;
/*     */   static final int FOREGROUND_DISABLED = 8;
/*     */   static final int FOREGROUND_ENABLED = 9;
/*     */   static final int FOREGROUND_FOCUSED = 10;
/*     */   static final int FOREGROUND_MOUSEOVER_FOCUSED = 11;
/*     */   static final int FOREGROUND_PRESSED_FOCUSED = 12;
/*     */   static final int FOREGROUND_MOUSEOVER = 13;
/*     */   static final int FOREGROUND_PRESSED = 14;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  59 */   private Path2D path = new Path2D.Float();
/*  60 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  61 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  62 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private Color color1 = decodeColor("nimbusBase", 0.015098333F, -0.5557143F, 0.2352941F, 0);
/*  68 */   private Color color2 = decodeColor("nimbusBase", 0.010237217F, -0.55799407F, 0.20784312F, 0);
/*  69 */   private Color color3 = decodeColor("nimbusBase", 0.018570602F, -0.5821429F, 0.32941175F, 0);
/*  70 */   private Color color4 = decodeColor("nimbusBase", 0.021348298F, -0.56722116F, 0.3098039F, 0);
/*  71 */   private Color color5 = decodeColor("nimbusBase", 0.021348298F, -0.567841F, 0.31764704F, 0);
/*  72 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.0F, -0.0033834577F, -0.30588236F, -148);
/*  73 */   private Color color7 = decodeColor("nimbusBase", 5.1498413E-4F, -0.2583558F, -0.13333336F, 0);
/*  74 */   private Color color8 = decodeColor("nimbusBase", 5.1498413E-4F, -0.095173776F, -0.25882354F, 0);
/*  75 */   private Color color9 = decodeColor("nimbusBase", 0.004681647F, -0.5383692F, 0.33725488F, 0);
/*  76 */   private Color color10 = decodeColor("nimbusBase", -0.0017285943F, -0.44453782F, 0.25098038F, 0);
/*  77 */   private Color color11 = decodeColor("nimbusBase", 5.1498413E-4F, -0.43866998F, 0.24705881F, 0);
/*  78 */   private Color color12 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4625541F, 0.35686272F, 0);
/*  79 */   private Color color13 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  80 */   private Color color14 = decodeColor("nimbusBase", 0.0013483167F, 0.088923395F, -0.2784314F, 0);
/*  81 */   private Color color15 = decodeColor("nimbusBase", 0.059279382F, 0.3642857F, -0.43529415F, 0);
/*  82 */   private Color color16 = decodeColor("nimbusBase", 0.0010585189F, -0.541452F, 0.4078431F, 0);
/*  83 */   private Color color17 = decodeColor("nimbusBase", 0.00254488F, -0.4608264F, 0.32549018F, 0);
/*  84 */   private Color color18 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4555341F, 0.3215686F, 0);
/*  85 */   private Color color19 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4757143F, 0.43137252F, 0);
/*  86 */   private Color color20 = decodeColor("nimbusBase", 0.061133325F, 0.3642857F, -0.427451F, 0);
/*  87 */   private Color color21 = decodeColor("nimbusBase", -3.528595E-5F, 0.018606722F, -0.23137257F, 0);
/*  88 */   private Color color22 = decodeColor("nimbusBase", 8.354783E-4F, -0.2578073F, 0.12549019F, 0);
/*  89 */   private Color color23 = decodeColor("nimbusBase", 8.9377165E-4F, -0.01599598F, 0.007843137F, 0);
/*  90 */   private Color color24 = decodeColor("nimbusBase", 0.0F, -0.00895375F, 0.007843137F, 0);
/*  91 */   private Color color25 = decodeColor("nimbusBase", 8.9377165E-4F, -0.13853917F, 0.14509803F, 0);
/*  92 */   private Color color26 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, -0.63529414F, -179);
/*  93 */   private Color color27 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -186);
/*  94 */   private Color color28 = decodeColor("nimbusBase", 0.018570602F, -0.56714284F, 0.1372549F, 0);
/*  95 */   private Color color29 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  96 */   private Color color30 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public SpinnerPreviousButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 104 */     this.state = paramInt;
/* 105 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 111 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 114 */     switch (this.state) { case 1:
/* 115 */         paintBackgroundDisabled(paramGraphics2D); break;
/* 116 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/* 117 */       case 3: paintBackgroundFocused(paramGraphics2D); break;
/* 118 */       case 4: paintBackgroundMouseOverAndFocused(paramGraphics2D); break;
/* 119 */       case 5: paintBackgroundPressedAndFocused(paramGraphics2D); break;
/* 120 */       case 6: paintBackgroundMouseOver(paramGraphics2D); break;
/* 121 */       case 7: paintBackgroundPressed(paramGraphics2D); break;
/* 122 */       case 8: paintForegroundDisabled(paramGraphics2D); break;
/* 123 */       case 9: paintForegroundEnabled(paramGraphics2D); break;
/* 124 */       case 10: paintForegroundFocused(paramGraphics2D); break;
/* 125 */       case 11: paintForegroundMouseOverAndFocused(paramGraphics2D); break;
/* 126 */       case 12: paintForegroundPressedAndFocused(paramGraphics2D); break;
/* 127 */       case 13: paintForegroundMouseOver(paramGraphics2D); break;
/* 128 */       case 14: paintForegroundPressed(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 137 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 141 */     this.path = decodePath1();
/* 142 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 143 */     paramGraphics2D.fill(this.path);
/* 144 */     this.path = decodePath2();
/* 145 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 146 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 151 */     this.path = decodePath3();
/* 152 */     paramGraphics2D.setPaint(this.color6);
/* 153 */     paramGraphics2D.fill(this.path);
/* 154 */     this.path = decodePath1();
/* 155 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 156 */     paramGraphics2D.fill(this.path);
/* 157 */     this.path = decodePath2();
/* 158 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 159 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocused(Graphics2D paramGraphics2D) {
/* 164 */     this.path = decodePath4();
/* 165 */     paramGraphics2D.setPaint(this.color13);
/* 166 */     paramGraphics2D.fill(this.path);
/* 167 */     this.path = decodePath1();
/* 168 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 169 */     paramGraphics2D.fill(this.path);
/* 170 */     this.path = decodePath2();
/* 171 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 172 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndFocused(Graphics2D paramGraphics2D) {
/* 177 */     this.path = decodePath5();
/* 178 */     paramGraphics2D.setPaint(this.color13);
/* 179 */     paramGraphics2D.fill(this.path);
/* 180 */     this.path = decodePath6();
/* 181 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 182 */     paramGraphics2D.fill(this.path);
/* 183 */     this.path = decodePath7();
/* 184 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 185 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndFocused(Graphics2D paramGraphics2D) {
/* 190 */     this.path = decodePath4();
/* 191 */     paramGraphics2D.setPaint(this.color13);
/* 192 */     paramGraphics2D.fill(this.path);
/* 193 */     this.path = decodePath1();
/* 194 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 195 */     paramGraphics2D.fill(this.path);
/* 196 */     this.path = decodePath2();
/* 197 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 198 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 203 */     this.path = decodePath3();
/* 204 */     paramGraphics2D.setPaint(this.color26);
/* 205 */     paramGraphics2D.fill(this.path);
/* 206 */     this.path = decodePath1();
/* 207 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 208 */     paramGraphics2D.fill(this.path);
/* 209 */     this.path = decodePath2();
/* 210 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 211 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 216 */     this.path = decodePath8();
/* 217 */     paramGraphics2D.setPaint(this.color27);
/* 218 */     paramGraphics2D.fill(this.path);
/* 219 */     this.path = decodePath1();
/* 220 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 221 */     paramGraphics2D.fill(this.path);
/* 222 */     this.path = decodePath2();
/* 223 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 224 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundDisabled(Graphics2D paramGraphics2D) {
/* 229 */     this.path = decodePath9();
/* 230 */     paramGraphics2D.setPaint(this.color28);
/* 231 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundEnabled(Graphics2D paramGraphics2D) {
/* 236 */     this.path = decodePath9();
/* 237 */     paramGraphics2D.setPaint(this.color29);
/* 238 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundFocused(Graphics2D paramGraphics2D) {
/* 243 */     this.path = decodePath9();
/* 244 */     paramGraphics2D.setPaint(this.color29);
/* 245 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundMouseOverAndFocused(Graphics2D paramGraphics2D) {
/* 250 */     this.path = decodePath9();
/* 251 */     paramGraphics2D.setPaint(this.color29);
/* 252 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundPressedAndFocused(Graphics2D paramGraphics2D) {
/* 257 */     this.path = decodePath9();
/* 258 */     paramGraphics2D.setPaint(this.color30);
/* 259 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundMouseOver(Graphics2D paramGraphics2D) {
/* 264 */     this.path = decodePath9();
/* 265 */     paramGraphics2D.setPaint(this.color29);
/* 266 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundPressed(Graphics2D paramGraphics2D) {
/* 271 */     this.path = decodePath9();
/* 272 */     paramGraphics2D.setPaint(this.color30);
/* 273 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path2D decodePath1() {
/* 280 */     this.path.reset();
/* 281 */     this.path.moveTo(decodeX(0.0F), decodeY(1.0F));
/* 282 */     this.path.lineTo(decodeX(0.0F), decodeY(2.6666667F));
/* 283 */     this.path.lineTo(decodeX(2.142857F), decodeY(2.6666667F));
/* 284 */     this.path.curveTo(decodeAnchorX(2.142857F, 3.0F), decodeAnchorY(2.6666667F, 0.0F), decodeAnchorX(2.7142859F, 0.0F), decodeAnchorY(2.0F, 2.0F), decodeX(2.7142859F), decodeY(2.0F));
/* 285 */     this.path.lineTo(decodeX(2.7142859F), decodeY(1.0F));
/* 286 */     this.path.lineTo(decodeX(0.0F), decodeY(1.0F));
/* 287 */     this.path.closePath();
/* 288 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 292 */     this.path.reset();
/* 293 */     this.path.moveTo(decodeX(1.0F), decodeY(1.0F));
/* 294 */     this.path.lineTo(decodeX(1.0F), decodeY(2.5F));
/* 295 */     this.path.lineTo(decodeX(2.142857F), decodeY(2.5F));
/* 296 */     this.path.curveTo(decodeAnchorX(2.142857F, 2.0F), decodeAnchorY(2.5F, 0.0F), decodeAnchorX(2.5714285F, 0.0F), decodeAnchorY(2.0F, 1.0F), decodeX(2.5714285F), decodeY(2.0F));
/* 297 */     this.path.lineTo(decodeX(2.5714285F), decodeY(1.0F));
/* 298 */     this.path.lineTo(decodeX(1.0F), decodeY(1.0F));
/* 299 */     this.path.closePath();
/* 300 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 304 */     this.path.reset();
/* 305 */     this.path.moveTo(decodeX(0.0F), decodeY(2.6666667F));
/* 306 */     this.path.lineTo(decodeX(0.0F), decodeY(2.8333333F));
/* 307 */     this.path.lineTo(decodeX(2.0324676F), decodeY(2.8333333F));
/* 308 */     this.path.curveTo(decodeAnchorX(2.0324676F, 2.1136363F), decodeAnchorY(2.8333333F, 0.0F), decodeAnchorX(2.7142859F, 0.0F), decodeAnchorY(2.0F, 3.0F), decodeX(2.7142859F), decodeY(2.0F));
/* 309 */     this.path.lineTo(decodeX(0.0F), decodeY(2.6666667F));
/* 310 */     this.path.closePath();
/* 311 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 315 */     this.path.reset();
/* 316 */     this.path.moveTo(decodeX(0.0F), decodeY(1.0F));
/* 317 */     this.path.lineTo(decodeX(0.0F), decodeY(2.8999999F));
/* 318 */     this.path.lineTo(decodeX(2.2F), decodeY(2.8999999F));
/* 319 */     this.path.curveTo(decodeAnchorX(2.2F, 3.0F), decodeAnchorY(2.8999999F, 0.0F), decodeAnchorX(2.9142857F, 0.0F), decodeAnchorY(2.2333333F, 3.0F), decodeX(2.9142857F), decodeY(2.2333333F));
/* 320 */     this.path.lineTo(decodeX(2.9142857F), decodeY(1.0F));
/* 321 */     this.path.lineTo(decodeX(0.0F), decodeY(1.0F));
/* 322 */     this.path.closePath();
/* 323 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 327 */     this.path.reset();
/* 328 */     this.path.moveTo(decodeX(0.0F), decodeY(0.0F));
/* 329 */     this.path.lineTo(decodeX(0.0F), decodeY(2.8999999F));
/* 330 */     this.path.lineTo(decodeX(2.2F), decodeY(2.8999999F));
/* 331 */     this.path.curveTo(decodeAnchorX(2.2F, 3.0F), decodeAnchorY(2.8999999F, 0.0F), decodeAnchorX(2.9142857F, 0.0F), decodeAnchorY(2.2333333F, 3.0F), decodeX(2.9142857F), decodeY(2.2333333F));
/* 332 */     this.path.lineTo(decodeX(2.9142857F), decodeY(0.0F));
/* 333 */     this.path.lineTo(decodeX(0.0F), decodeY(0.0F));
/* 334 */     this.path.closePath();
/* 335 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath6() {
/* 339 */     this.path.reset();
/* 340 */     this.path.moveTo(decodeX(0.0F), decodeY(0.0F));
/* 341 */     this.path.lineTo(decodeX(0.0F), decodeY(2.6666667F));
/* 342 */     this.path.lineTo(decodeX(2.142857F), decodeY(2.6666667F));
/* 343 */     this.path.curveTo(decodeAnchorX(2.142857F, 3.0F), decodeAnchorY(2.6666667F, 0.0F), decodeAnchorX(2.7142859F, 0.0F), decodeAnchorY(2.0F, 2.0F), decodeX(2.7142859F), decodeY(2.0F));
/* 344 */     this.path.lineTo(decodeX(2.7142859F), decodeY(0.0F));
/* 345 */     this.path.lineTo(decodeX(0.0F), decodeY(0.0F));
/* 346 */     this.path.closePath();
/* 347 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath7() {
/* 351 */     this.path.reset();
/* 352 */     this.path.moveTo(decodeX(1.0F), decodeY(0.0F));
/* 353 */     this.path.lineTo(decodeX(1.0F), decodeY(2.5F));
/* 354 */     this.path.lineTo(decodeX(2.142857F), decodeY(2.5F));
/* 355 */     this.path.curveTo(decodeAnchorX(2.142857F, 2.0F), decodeAnchorY(2.5F, 0.0F), decodeAnchorX(2.5714285F, 0.0F), decodeAnchorY(2.0F, 1.0F), decodeX(2.5714285F), decodeY(2.0F));
/* 356 */     this.path.lineTo(decodeX(2.5714285F), decodeY(0.0F));
/* 357 */     this.path.lineTo(decodeX(1.0F), decodeY(0.0F));
/* 358 */     this.path.closePath();
/* 359 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath8() {
/* 363 */     this.path.reset();
/* 364 */     this.path.moveTo(decodeX(0.0F), decodeY(2.6666667F));
/* 365 */     this.path.lineTo(decodeX(0.0F), decodeY(2.8333333F));
/* 366 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(2.8333333F, 0.0F), decodeAnchorX(2.0324676F, -2.1136363F), decodeAnchorY(2.8333333F, 0.0F), decodeX(2.0324676F), decodeY(2.8333333F));
/* 367 */     this.path.curveTo(decodeAnchorX(2.0324676F, 2.1136363F), decodeAnchorY(2.8333333F, 0.0F), decodeAnchorX(2.7142859F, 0.0F), decodeAnchorY(2.0F, 3.0F), decodeX(2.7142859F), decodeY(2.0F));
/* 368 */     this.path.lineTo(decodeX(0.0F), decodeY(2.6666667F));
/* 369 */     this.path.closePath();
/* 370 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath9() {
/* 374 */     this.path.reset();
/* 375 */     this.path.moveTo(decodeX(1.0F), decodeY(1.0F));
/* 376 */     this.path.lineTo(decodeX(1.5045455F), decodeY(1.9943181F));
/* 377 */     this.path.lineTo(decodeX(2.0F), decodeY(1.0F));
/* 378 */     this.path.lineTo(decodeX(1.0F), decodeY(1.0F));
/* 379 */     this.path.closePath();
/* 380 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 386 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 387 */     float f1 = (float)rectangle2D.getX();
/* 388 */     float f2 = (float)rectangle2D.getY();
/* 389 */     float f3 = (float)rectangle2D.getWidth();
/* 390 */     float f4 = (float)rectangle2D.getHeight();
/* 391 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 394 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 399 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 400 */     float f1 = (float)rectangle2D.getX();
/* 401 */     float f2 = (float)rectangle2D.getY();
/* 402 */     float f3 = (float)rectangle2D.getWidth();
/* 403 */     float f4 = (float)rectangle2D.getHeight();
/* 404 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.05748663F, 0.11497326F, 0.55748665F, 1.0F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 407 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 409 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 414 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 415 */     float f1 = (float)rectangle2D.getX();
/* 416 */     float f2 = (float)rectangle2D.getY();
/* 417 */     float f3 = (float)rectangle2D.getWidth();
/* 418 */     float f4 = (float)rectangle2D.getHeight();
/* 419 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 422 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 427 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 428 */     float f1 = (float)rectangle2D.getX();
/* 429 */     float f2 = (float)rectangle2D.getY();
/* 430 */     float f3 = (float)rectangle2D.getWidth();
/* 431 */     float f4 = (float)rectangle2D.getHeight();
/* 432 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.05748663F, 0.11497326F, 0.2419786F, 0.36898395F, 0.684492F, 1.0F }, new Color[] { this.color9, 
/*     */ 
/*     */           
/* 435 */           decodeColor(this.color9, this.color10, 0.5F), this.color10, 
/*     */           
/* 437 */           decodeColor(this.color10, this.color11, 0.5F), this.color11, 
/*     */           
/* 439 */           decodeColor(this.color11, this.color12, 0.5F), this.color12 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 444 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 445 */     float f1 = (float)rectangle2D.getX();
/* 446 */     float f2 = (float)rectangle2D.getY();
/* 447 */     float f3 = (float)rectangle2D.getWidth();
/* 448 */     float f4 = (float)rectangle2D.getHeight();
/* 449 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color14, 
/*     */ 
/*     */           
/* 452 */           decodeColor(this.color14, this.color15, 0.5F), this.color15 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 457 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 458 */     float f1 = (float)rectangle2D.getX();
/* 459 */     float f2 = (float)rectangle2D.getY();
/* 460 */     float f3 = (float)rectangle2D.getWidth();
/* 461 */     float f4 = (float)rectangle2D.getHeight();
/* 462 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.05748663F, 0.11497326F, 0.2419786F, 0.36898395F, 0.684492F, 1.0F }, new Color[] { this.color16, 
/*     */ 
/*     */           
/* 465 */           decodeColor(this.color16, this.color17, 0.5F), this.color17, 
/*     */           
/* 467 */           decodeColor(this.color17, this.color18, 0.5F), this.color18, 
/*     */           
/* 469 */           decodeColor(this.color18, this.color19, 0.5F), this.color19 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 474 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 475 */     float f1 = (float)rectangle2D.getX();
/* 476 */     float f2 = (float)rectangle2D.getY();
/* 477 */     float f3 = (float)rectangle2D.getWidth();
/* 478 */     float f4 = (float)rectangle2D.getHeight();
/* 479 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color20, 
/*     */ 
/*     */           
/* 482 */           decodeColor(this.color20, this.color21, 0.5F), this.color21 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 487 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 488 */     float f1 = (float)rectangle2D.getX();
/* 489 */     float f2 = (float)rectangle2D.getY();
/* 490 */     float f3 = (float)rectangle2D.getWidth();
/* 491 */     float f4 = (float)rectangle2D.getHeight();
/* 492 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.05748663F, 0.11497326F, 0.2419786F, 0.36898395F, 0.684492F, 1.0F }, new Color[] { this.color22, 
/*     */ 
/*     */           
/* 495 */           decodeColor(this.color22, this.color23, 0.5F), this.color23, 
/*     */           
/* 497 */           decodeColor(this.color23, this.color24, 0.5F), this.color24, 
/*     */           
/* 499 */           decodeColor(this.color24, this.color25, 0.5F), this.color25 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/SpinnerPreviousButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */