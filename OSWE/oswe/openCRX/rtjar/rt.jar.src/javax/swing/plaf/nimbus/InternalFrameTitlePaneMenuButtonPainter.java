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
/*     */ final class InternalFrameTitlePaneMenuButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int ICON_ENABLED = 1;
/*     */   static final int ICON_DISABLED = 2;
/*     */   static final int ICON_MOUSEOVER = 3;
/*     */   static final int ICON_PRESSED = 4;
/*     */   static final int ICON_ENABLED_WINDOWNOTFOCUSED = 5;
/*     */   static final int ICON_MOUSEOVER_WINDOWNOTFOCUSED = 6;
/*     */   static final int ICON_PRESSED_WINDOWNOTFOCUSED = 7;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  52 */   private Path2D path = new Path2D.Float();
/*  53 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  54 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  55 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private Color color1 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.0029994324F, -0.38039216F, -185);
/*  61 */   private Color color2 = decodeColor("nimbusBase", 0.08801502F, 0.3642857F, -0.5019608F, 0);
/*  62 */   private Color color3 = decodeColor("nimbusBase", 0.030543745F, -0.3835404F, -0.09803924F, 0);
/*  63 */   private Color color4 = decodeColor("nimbusBase", 0.029191494F, -0.53801316F, 0.13333333F, 0);
/*  64 */   private Color color5 = decodeColor("nimbusBase", 0.030543745F, -0.3857143F, -0.09411767F, 0);
/*  65 */   private Color color6 = decodeColor("nimbusBase", 0.030543745F, -0.43148893F, 0.007843137F, 0);
/*  66 */   private Color color7 = decodeColor("nimbusBase", 0.029191494F, -0.24935067F, -0.20392159F, -132);
/*  67 */   private Color color8 = decodeColor("nimbusBase", 0.029191494F, -0.24935067F, -0.20392159F, 0);
/*  68 */   private Color color9 = decodeColor("nimbusBase", 0.029191494F, -0.24935067F, -0.20392159F, -123);
/*  69 */   private Color color10 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*  70 */   private Color color11 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.0029994324F, -0.38039216F, -208);
/*  71 */   private Color color12 = decodeColor("nimbusBase", 0.02551502F, -0.5942635F, 0.20784312F, 0);
/*  72 */   private Color color13 = decodeColor("nimbusBase", 0.032459438F, -0.5490091F, 0.12941176F, 0);
/*  73 */   private Color color14 = decodeColor("nimbusBase", 0.032459438F, -0.5469569F, 0.11372548F, 0);
/*  74 */   private Color color15 = decodeColor("nimbusBase", 0.032459438F, -0.5760128F, 0.23921567F, 0);
/*  75 */   private Color color16 = decodeColor("nimbusBase", 0.08801502F, 0.3642857F, -0.4901961F, 0);
/*  76 */   private Color color17 = decodeColor("nimbusBase", 0.032459438F, -0.1857143F, -0.23529413F, 0);
/*  77 */   private Color color18 = decodeColor("nimbusBase", 0.029191494F, -0.5438224F, 0.17647058F, 0);
/*  78 */   private Color color19 = decodeColor("nimbusBase", 0.030543745F, -0.41929638F, -0.02352941F, 0);
/*  79 */   private Color color20 = decodeColor("nimbusBase", 0.030543745F, -0.45559007F, 0.082352936F, 0);
/*  80 */   private Color color21 = decodeColor("nimbusBase", 0.03409344F, -0.329408F, -0.11372551F, -132);
/*  81 */   private Color color22 = decodeColor("nimbusBase", 0.03409344F, -0.329408F, -0.11372551F, 0);
/*  82 */   private Color color23 = decodeColor("nimbusBase", 0.03409344F, -0.329408F, -0.11372551F, -123);
/*  83 */   private Color color24 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  84 */   private Color color25 = decodeColor("nimbusBase", 0.031104386F, 0.12354499F, -0.33725494F, 0);
/*  85 */   private Color color26 = decodeColor("nimbusBase", 0.032459438F, -0.4592437F, -0.015686274F, 0);
/*  86 */   private Color color27 = decodeColor("nimbusBase", 0.029191494F, -0.2579365F, -0.19607845F, 0);
/*  87 */   private Color color28 = decodeColor("nimbusBase", 0.03409344F, -0.3149596F, -0.13333336F, 0);
/*  88 */   private Color color29 = decodeColor("nimbusBase", 0.029681683F, 0.07857144F, -0.3294118F, -132);
/*  89 */   private Color color30 = decodeColor("nimbusBase", 0.029681683F, 0.07857144F, -0.3294118F, 0);
/*  90 */   private Color color31 = decodeColor("nimbusBase", 0.029681683F, 0.07857144F, -0.3294118F, -123);
/*  91 */   private Color color32 = decodeColor("nimbusBase", 0.032459438F, -0.53637654F, 0.043137252F, 0);
/*  92 */   private Color color33 = decodeColor("nimbusBase", 0.032459438F, -0.49935067F, -0.11764708F, 0);
/*  93 */   private Color color34 = decodeColor("nimbusBase", 0.021348298F, -0.6133929F, 0.32941175F, 0);
/*  94 */   private Color color35 = decodeColor("nimbusBase", 0.042560518F, -0.5804379F, 0.23137254F, 0);
/*  95 */   private Color color36 = decodeColor("nimbusBase", 0.032459438F, -0.57417583F, 0.21568626F, 0);
/*  96 */   private Color color37 = decodeColor("nimbusBase", 0.027408898F, -0.5784226F, 0.20392156F, -132);
/*  97 */   private Color color38 = decodeColor("nimbusBase", 0.042560518F, -0.5665319F, 0.0745098F, 0);
/*  98 */   private Color color39 = decodeColor("nimbusBase", 0.036732912F, -0.5642857F, 0.16470587F, -123);
/*  99 */   private Color color40 = decodeColor("nimbusBase", 0.021348298F, -0.54480517F, -0.11764708F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public InternalFrameTitlePaneMenuButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 107 */     this.state = paramInt;
/* 108 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 114 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 117 */     switch (this.state) { case 1:
/* 118 */         painticonEnabled(paramGraphics2D); break;
/* 119 */       case 2: painticonDisabled(paramGraphics2D); break;
/* 120 */       case 3: painticonMouseOver(paramGraphics2D); break;
/* 121 */       case 4: painticonPressed(paramGraphics2D); break;
/* 122 */       case 5: painticonEnabledAndWindowNotFocused(paramGraphics2D); break;
/* 123 */       case 6: painticonMouseOverAndWindowNotFocused(paramGraphics2D); break;
/* 124 */       case 7: painticonPressedAndWindowNotFocused(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 133 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void painticonEnabled(Graphics2D paramGraphics2D) {
/* 137 */     this.roundRect = decodeRoundRect1();
/* 138 */     paramGraphics2D.setPaint(this.color1);
/* 139 */     paramGraphics2D.fill(this.roundRect);
/* 140 */     this.roundRect = decodeRoundRect2();
/* 141 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 142 */     paramGraphics2D.fill(this.roundRect);
/* 143 */     this.roundRect = decodeRoundRect3();
/* 144 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 145 */     paramGraphics2D.fill(this.roundRect);
/* 146 */     this.path = decodePath1();
/* 147 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 148 */     paramGraphics2D.fill(this.path);
/* 149 */     this.path = decodePath2();
/* 150 */     paramGraphics2D.setPaint(this.color10);
/* 151 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonDisabled(Graphics2D paramGraphics2D) {
/* 156 */     this.roundRect = decodeRoundRect1();
/* 157 */     paramGraphics2D.setPaint(this.color11);
/* 158 */     paramGraphics2D.fill(this.roundRect);
/* 159 */     this.roundRect = decodeRoundRect2();
/* 160 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 161 */     paramGraphics2D.fill(this.roundRect);
/* 162 */     this.path = decodePath2();
/* 163 */     paramGraphics2D.setPaint(this.color15);
/* 164 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonMouseOver(Graphics2D paramGraphics2D) {
/* 169 */     this.roundRect = decodeRoundRect1();
/* 170 */     paramGraphics2D.setPaint(this.color1);
/* 171 */     paramGraphics2D.fill(this.roundRect);
/* 172 */     this.roundRect = decodeRoundRect2();
/* 173 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 174 */     paramGraphics2D.fill(this.roundRect);
/* 175 */     this.roundRect = decodeRoundRect3();
/* 176 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 177 */     paramGraphics2D.fill(this.roundRect);
/* 178 */     this.path = decodePath1();
/* 179 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 180 */     paramGraphics2D.fill(this.path);
/* 181 */     this.path = decodePath2();
/* 182 */     paramGraphics2D.setPaint(this.color10);
/* 183 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonPressed(Graphics2D paramGraphics2D) {
/* 188 */     this.roundRect = decodeRoundRect1();
/* 189 */     paramGraphics2D.setPaint(this.color1);
/* 190 */     paramGraphics2D.fill(this.roundRect);
/* 191 */     this.roundRect = decodeRoundRect2();
/* 192 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 193 */     paramGraphics2D.fill(this.roundRect);
/* 194 */     this.roundRect = decodeRoundRect3();
/* 195 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 196 */     paramGraphics2D.fill(this.roundRect);
/* 197 */     this.path = decodePath1();
/* 198 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/* 199 */     paramGraphics2D.fill(this.path);
/* 200 */     this.path = decodePath2();
/* 201 */     paramGraphics2D.setPaint(this.color10);
/* 202 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonEnabledAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 207 */     this.roundRect = decodeRoundRect2();
/* 208 */     paramGraphics2D.setPaint(decodeGradient11(this.roundRect));
/* 209 */     paramGraphics2D.fill(this.roundRect);
/* 210 */     this.roundRect = decodeRoundRect3();
/* 211 */     paramGraphics2D.setPaint(decodeGradient12(this.roundRect));
/* 212 */     paramGraphics2D.fill(this.roundRect);
/* 213 */     this.path = decodePath3();
/* 214 */     paramGraphics2D.setPaint(decodeGradient13(this.path));
/* 215 */     paramGraphics2D.fill(this.path);
/* 216 */     this.path = decodePath2();
/* 217 */     paramGraphics2D.setPaint(this.color40);
/* 218 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonMouseOverAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 223 */     this.roundRect = decodeRoundRect1();
/* 224 */     paramGraphics2D.setPaint(this.color1);
/* 225 */     paramGraphics2D.fill(this.roundRect);
/* 226 */     this.roundRect = decodeRoundRect2();
/* 227 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 228 */     paramGraphics2D.fill(this.roundRect);
/* 229 */     this.roundRect = decodeRoundRect3();
/* 230 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 231 */     paramGraphics2D.fill(this.roundRect);
/* 232 */     this.path = decodePath1();
/* 233 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 234 */     paramGraphics2D.fill(this.path);
/* 235 */     this.path = decodePath2();
/* 236 */     paramGraphics2D.setPaint(this.color10);
/* 237 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonPressedAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 242 */     this.roundRect = decodeRoundRect1();
/* 243 */     paramGraphics2D.setPaint(this.color1);
/* 244 */     paramGraphics2D.fill(this.roundRect);
/* 245 */     this.roundRect = decodeRoundRect2();
/* 246 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 247 */     paramGraphics2D.fill(this.roundRect);
/* 248 */     this.roundRect = decodeRoundRect3();
/* 249 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 250 */     paramGraphics2D.fill(this.roundRect);
/* 251 */     this.path = decodePath1();
/* 252 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/* 253 */     paramGraphics2D.fill(this.path);
/* 254 */     this.path = decodePath2();
/* 255 */     paramGraphics2D.setPaint(this.color10);
/* 256 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 263 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/* 264 */         decodeY(1.6111112F), (
/* 265 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 266 */         decodeY(2.0F) - decodeY(1.6111112F)), 6.0D, 6.0D);
/*     */     
/* 268 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 272 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/* 273 */         decodeY(1.0F), (
/* 274 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 275 */         decodeY(1.9444444F) - decodeY(1.0F)), 8.600000381469727D, 8.600000381469727D);
/*     */     
/* 277 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect3() {
/* 281 */     this.roundRect.setRoundRect(decodeX(1.0526316F), 
/* 282 */         decodeY(1.0555556F), (
/* 283 */         decodeX(1.9473684F) - decodeX(1.0526316F)), (
/* 284 */         decodeY(1.8888888F) - decodeY(1.0555556F)), 6.75D, 6.75D);
/*     */     
/* 286 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 290 */     this.path.reset();
/* 291 */     this.path.moveTo(decodeX(1.3157895F), decodeY(1.4444444F));
/* 292 */     this.path.lineTo(decodeX(1.6842105F), decodeY(1.4444444F));
/* 293 */     this.path.lineTo(decodeX(1.5013158F), decodeY(1.7208333F));
/* 294 */     this.path.lineTo(decodeX(1.3157895F), decodeY(1.4444444F));
/* 295 */     this.path.closePath();
/* 296 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 300 */     this.path.reset();
/* 301 */     this.path.moveTo(decodeX(1.3157895F), decodeY(1.3333334F));
/* 302 */     this.path.lineTo(decodeX(1.6842105F), decodeY(1.3333334F));
/* 303 */     this.path.lineTo(decodeX(1.5F), decodeY(1.6083333F));
/* 304 */     this.path.lineTo(decodeX(1.3157895F), decodeY(1.3333334F));
/* 305 */     this.path.closePath();
/* 306 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 310 */     this.path.reset();
/* 311 */     this.path.moveTo(decodeX(1.3157895F), decodeY(1.3888888F));
/* 312 */     this.path.lineTo(decodeX(1.6842105F), decodeY(1.3888888F));
/* 313 */     this.path.lineTo(decodeX(1.4952153F), decodeY(1.655303F));
/* 314 */     this.path.lineTo(decodeX(1.3157895F), decodeY(1.3888888F));
/* 315 */     this.path.closePath();
/* 316 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 322 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 323 */     float f1 = (float)rectangle2D.getX();
/* 324 */     float f2 = (float)rectangle2D.getY();
/* 325 */     float f3 = (float)rectangle2D.getWidth();
/* 326 */     float f4 = (float)rectangle2D.getHeight();
/* 327 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 330 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 335 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 336 */     float f1 = (float)rectangle2D.getX();
/* 337 */     float f2 = (float)rectangle2D.getY();
/* 338 */     float f3 = (float)rectangle2D.getWidth();
/* 339 */     float f4 = (float)rectangle2D.getHeight();
/* 340 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color4, 
/*     */ 
/*     */           
/* 343 */           decodeColor(this.color4, this.color5, 0.5F), this.color5, 
/*     */           
/* 345 */           decodeColor(this.color5, this.color3, 0.5F), this.color3, 
/*     */           
/* 347 */           decodeColor(this.color3, this.color6, 0.5F), this.color6 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 352 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 353 */     float f1 = (float)rectangle2D.getX();
/* 354 */     float f2 = (float)rectangle2D.getY();
/* 355 */     float f3 = (float)rectangle2D.getWidth();
/* 356 */     float f4 = (float)rectangle2D.getHeight();
/* 357 */     return decodeGradient(0.50714284F * f3 + f1, 0.095F * f4 + f2, 0.49285713F * f3 + f1, 0.91F * f4 + f2, new float[] { 0.0F, 0.24289773F, 0.48579547F, 0.74289775F, 1.0F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 360 */           decodeColor(this.color7, this.color8, 0.5F), this.color8, 
/*     */           
/* 362 */           decodeColor(this.color8, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 367 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 368 */     float f1 = (float)rectangle2D.getX();
/* 369 */     float f2 = (float)rectangle2D.getY();
/* 370 */     float f3 = (float)rectangle2D.getWidth();
/* 371 */     float f4 = (float)rectangle2D.getHeight();
/* 372 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.31107953F, 0.62215906F, 0.8110795F, 1.0F }, new Color[] { this.color12, 
/*     */ 
/*     */           
/* 375 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*     */           
/* 377 */           decodeColor(this.color13, this.color14, 0.5F), this.color14 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 382 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 383 */     float f1 = (float)rectangle2D.getX();
/* 384 */     float f2 = (float)rectangle2D.getY();
/* 385 */     float f3 = (float)rectangle2D.getWidth();
/* 386 */     float f4 = (float)rectangle2D.getHeight();
/* 387 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color16, 
/*     */ 
/*     */           
/* 390 */           decodeColor(this.color16, this.color17, 0.5F), this.color17 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 395 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 396 */     float f1 = (float)rectangle2D.getX();
/* 397 */     float f2 = (float)rectangle2D.getY();
/* 398 */     float f3 = (float)rectangle2D.getWidth();
/* 399 */     float f4 = (float)rectangle2D.getHeight();
/* 400 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color18, 
/*     */ 
/*     */           
/* 403 */           decodeColor(this.color18, this.color19, 0.5F), this.color19, 
/*     */           
/* 405 */           decodeColor(this.color19, this.color19, 0.5F), this.color19, 
/*     */           
/* 407 */           decodeColor(this.color19, this.color20, 0.5F), this.color20 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 412 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 413 */     float f1 = (float)rectangle2D.getX();
/* 414 */     float f2 = (float)rectangle2D.getY();
/* 415 */     float f3 = (float)rectangle2D.getWidth();
/* 416 */     float f4 = (float)rectangle2D.getHeight();
/* 417 */     return decodeGradient(0.50714284F * f3 + f1, 0.095F * f4 + f2, 0.49285713F * f3 + f1, 0.91F * f4 + f2, new float[] { 0.0F, 0.24289773F, 0.48579547F, 0.74289775F, 1.0F }, new Color[] { this.color21, 
/*     */ 
/*     */           
/* 420 */           decodeColor(this.color21, this.color22, 0.5F), this.color22, 
/*     */           
/* 422 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 427 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 428 */     float f1 = (float)rectangle2D.getX();
/* 429 */     float f2 = (float)rectangle2D.getY();
/* 430 */     float f3 = (float)rectangle2D.getWidth();
/* 431 */     float f4 = (float)rectangle2D.getHeight();
/* 432 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 435 */           decodeColor(this.color24, this.color25, 0.5F), this.color25 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 440 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 441 */     float f1 = (float)rectangle2D.getX();
/* 442 */     float f2 = (float)rectangle2D.getY();
/* 443 */     float f3 = (float)rectangle2D.getWidth();
/* 444 */     float f4 = (float)rectangle2D.getHeight();
/* 445 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color26, 
/*     */ 
/*     */           
/* 448 */           decodeColor(this.color26, this.color27, 0.5F), this.color27, 
/*     */           
/* 450 */           decodeColor(this.color27, this.color27, 0.5F), this.color27, 
/*     */           
/* 452 */           decodeColor(this.color27, this.color28, 0.5F), this.color28 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 457 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 458 */     float f1 = (float)rectangle2D.getX();
/* 459 */     float f2 = (float)rectangle2D.getY();
/* 460 */     float f3 = (float)rectangle2D.getWidth();
/* 461 */     float f4 = (float)rectangle2D.getHeight();
/* 462 */     return decodeGradient(0.50714284F * f3 + f1, 0.095F * f4 + f2, 0.49285713F * f3 + f1, 0.91F * f4 + f2, new float[] { 0.0F, 0.24289773F, 0.48579547F, 0.74289775F, 1.0F }, new Color[] { this.color29, 
/*     */ 
/*     */           
/* 465 */           decodeColor(this.color29, this.color30, 0.5F), this.color30, 
/*     */           
/* 467 */           decodeColor(this.color30, this.color31, 0.5F), this.color31 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient11(Shape paramShape) {
/* 472 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 473 */     float f1 = (float)rectangle2D.getX();
/* 474 */     float f2 = (float)rectangle2D.getY();
/* 475 */     float f3 = (float)rectangle2D.getWidth();
/* 476 */     float f4 = (float)rectangle2D.getHeight();
/* 477 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color32, 
/*     */ 
/*     */           
/* 480 */           decodeColor(this.color32, this.color33, 0.5F), this.color33 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient12(Shape paramShape) {
/* 485 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 486 */     float f1 = (float)rectangle2D.getX();
/* 487 */     float f2 = (float)rectangle2D.getY();
/* 488 */     float f3 = (float)rectangle2D.getWidth();
/* 489 */     float f4 = (float)rectangle2D.getHeight();
/* 490 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color34, 
/*     */ 
/*     */           
/* 493 */           decodeColor(this.color34, this.color35, 0.5F), this.color35, 
/*     */           
/* 495 */           decodeColor(this.color35, this.color36, 0.5F), this.color36, 
/*     */           
/* 497 */           decodeColor(this.color36, this.color15, 0.5F), this.color15 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient13(Shape paramShape) {
/* 502 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 503 */     float f1 = (float)rectangle2D.getX();
/* 504 */     float f2 = (float)rectangle2D.getY();
/* 505 */     float f3 = (float)rectangle2D.getWidth();
/* 506 */     float f4 = (float)rectangle2D.getHeight();
/* 507 */     return decodeGradient(0.50714284F * f3 + f1, 0.095F * f4 + f2, 0.49285713F * f3 + f1, 0.91F * f4 + f2, new float[] { 0.0F, 0.24289773F, 0.48579547F, 0.74289775F, 1.0F }, new Color[] { this.color37, 
/*     */ 
/*     */           
/* 510 */           decodeColor(this.color37, this.color38, 0.5F), this.color38, 
/*     */           
/* 512 */           decodeColor(this.color38, this.color39, 0.5F), this.color39 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/InternalFrameTitlePaneMenuButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */