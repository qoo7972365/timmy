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
/*     */ final class TabbedPaneTabPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_ENABLED = 1;
/*     */   static final int BACKGROUND_ENABLED_MOUSEOVER = 2;
/*     */   static final int BACKGROUND_ENABLED_PRESSED = 3;
/*     */   static final int BACKGROUND_DISABLED = 4;
/*     */   static final int BACKGROUND_SELECTED_DISABLED = 5;
/*     */   static final int BACKGROUND_SELECTED = 6;
/*     */   static final int BACKGROUND_SELECTED_MOUSEOVER = 7;
/*     */   static final int BACKGROUND_SELECTED_PRESSED = 8;
/*     */   static final int BACKGROUND_SELECTED_FOCUSED = 9;
/*     */   static final int BACKGROUND_SELECTED_MOUSEOVER_FOCUSED = 10;
/*     */   static final int BACKGROUND_SELECTED_PRESSED_FOCUSED = 11;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  56 */   private Path2D path = new Path2D.Float();
/*  57 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  58 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  59 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private Color color1 = decodeColor("nimbusBase", 0.032459438F, -0.55535716F, -0.109803945F, 0);
/*  65 */   private Color color2 = decodeColor("nimbusBase", 0.08801502F, 0.3642857F, -0.4784314F, 0);
/*  66 */   private Color color3 = decodeColor("nimbusBase", 0.08801502F, -0.63174605F, 0.43921566F, 0);
/*  67 */   private Color color4 = decodeColor("nimbusBase", 0.05468172F, -0.6145278F, 0.37647057F, 0);
/*  68 */   private Color color5 = decodeColor("nimbusBase", 0.032459438F, -0.5953556F, 0.32549018F, 0);
/*  69 */   private Color color6 = decodeColor("nimbusBase", 0.032459438F, -0.54616207F, -0.02352941F, 0);
/*  70 */   private Color color7 = decodeColor("nimbusBase", 0.08801502F, -0.6317773F, 0.4470588F, 0);
/*  71 */   private Color color8 = decodeColor("nimbusBase", 0.021348298F, -0.61547136F, 0.41960782F, 0);
/*  72 */   private Color color9 = decodeColor("nimbusBase", 0.032459438F, -0.5985242F, 0.39999998F, 0);
/*  73 */   private Color color10 = decodeColor("nimbusBase", 0.08801502F, 0.3642857F, -0.52156866F, 0);
/*  74 */   private Color color11 = decodeColor("nimbusBase", 0.027408898F, -0.5847884F, 0.2980392F, 0);
/*  75 */   private Color color12 = decodeColor("nimbusBase", 0.035931647F, -0.5553123F, 0.23137254F, 0);
/*  76 */   private Color color13 = decodeColor("nimbusBase", 0.029681683F, -0.5281874F, 0.18039215F, 0);
/*  77 */   private Color color14 = decodeColor("nimbusBase", 0.03801495F, -0.5456242F, 0.3215686F, 0);
/*  78 */   private Color color15 = decodeColor("nimbusBase", 0.032459438F, -0.59181184F, 0.25490195F, 0);
/*  79 */   private Color color16 = decodeColor("nimbusBase", 0.05468172F, -0.58308274F, 0.19607842F, 0);
/*  80 */   private Color color17 = decodeColor("nimbusBase", 0.046348333F, -0.6006266F, 0.34509802F, 0);
/*  81 */   private Color color18 = decodeColor("nimbusBase", 0.046348333F, -0.60015875F, 0.3333333F, 0);
/*  82 */   private Color color19 = decodeColor("nimbusBase", 0.004681647F, -0.6197143F, 0.43137252F, 0);
/*  83 */   private Color color20 = decodeColor("nimbusBase", 7.13408E-4F, -0.543609F, 0.34509802F, 0);
/*  84 */   private Color color21 = decodeColor("nimbusBase", -0.0020751357F, -0.45610264F, 0.2588235F, 0);
/*  85 */   private Color color22 = decodeColor("nimbusBase", 5.1498413E-4F, -0.43866998F, 0.24705881F, 0);
/*  86 */   private Color color23 = decodeColor("nimbusBase", 5.1498413E-4F, -0.44879842F, 0.29019606F, 0);
/*  87 */   private Color color24 = decodeColor("nimbusBase", 5.1498413E-4F, -0.08776909F, -0.2627451F, 0);
/*  88 */   private Color color25 = decodeColor("nimbusBase", 0.06332368F, 0.3642857F, -0.4431373F, 0);
/*  89 */   private Color color26 = decodeColor("nimbusBase", 0.004681647F, -0.6198413F, 0.43921566F, 0);
/*  90 */   private Color color27 = decodeColor("nimbusBase", -0.0022627711F, -0.5335866F, 0.372549F, 0);
/*  91 */   private Color color28 = decodeColor("nimbusBase", -0.0017285943F, -0.4608264F, 0.32549018F, 0);
/*  92 */   private Color color29 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4555341F, 0.3215686F, 0);
/*  93 */   private Color color30 = decodeColor("nimbusBase", 5.1498413E-4F, -0.46404046F, 0.36470586F, 0);
/*  94 */   private Color color31 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  95 */   private Color color32 = decodeColor("nimbusBase", -4.2033195E-4F, -0.38050595F, 0.20392156F, 0);
/*  96 */   private Color color33 = decodeColor("nimbusBase", 0.0013483167F, -0.16401619F, 0.0745098F, 0);
/*  97 */   private Color color34 = decodeColor("nimbusBase", -0.0010001659F, -0.01599598F, 0.007843137F, 0);
/*  98 */   private Color color35 = decodeColor("nimbusBase", 0.0F, 0.0F, 0.0F, 0);
/*  99 */   private Color color36 = decodeColor("nimbusBase", 0.0018727183F, -0.038398862F, 0.035294116F, 0);
/* 100 */   private Color color37 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedPaneTabPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 108 */     this.state = paramInt;
/* 109 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 115 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 118 */     switch (this.state) { case 1:
/* 119 */         paintBackgroundEnabled(paramGraphics2D); break;
/* 120 */       case 2: paintBackgroundEnabledAndMouseOver(paramGraphics2D); break;
/* 121 */       case 3: paintBackgroundEnabledAndPressed(paramGraphics2D); break;
/* 122 */       case 4: paintBackgroundDisabled(paramGraphics2D); break;
/* 123 */       case 5: paintBackgroundSelectedAndDisabled(paramGraphics2D); break;
/* 124 */       case 6: paintBackgroundSelected(paramGraphics2D); break;
/* 125 */       case 7: paintBackgroundSelectedAndMouseOver(paramGraphics2D); break;
/* 126 */       case 8: paintBackgroundSelectedAndPressed(paramGraphics2D); break;
/* 127 */       case 9: paintBackgroundSelectedAndFocused(paramGraphics2D); break;
/* 128 */       case 10: paintBackgroundSelectedAndMouseOverAndFocused(paramGraphics2D); break;
/* 129 */       case 11: paintBackgroundSelectedAndPressedAndFocused(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 138 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 142 */     this.path = decodePath1();
/* 143 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 144 */     paramGraphics2D.fill(this.path);
/* 145 */     this.path = decodePath2();
/* 146 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 147 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndMouseOver(Graphics2D paramGraphics2D) {
/* 152 */     this.path = decodePath1();
/* 153 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 154 */     paramGraphics2D.fill(this.path);
/* 155 */     this.path = decodePath2();
/* 156 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 157 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndPressed(Graphics2D paramGraphics2D) {
/* 162 */     this.path = decodePath3();
/* 163 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 164 */     paramGraphics2D.fill(this.path);
/* 165 */     this.path = decodePath4();
/* 166 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 167 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 172 */     this.path = decodePath5();
/* 173 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 174 */     paramGraphics2D.fill(this.path);
/* 175 */     this.path = decodePath6();
/* 176 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 177 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndDisabled(Graphics2D paramGraphics2D) {
/* 182 */     this.path = decodePath7();
/* 183 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 184 */     paramGraphics2D.fill(this.path);
/* 185 */     this.path = decodePath2();
/* 186 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 187 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelected(Graphics2D paramGraphics2D) {
/* 192 */     this.path = decodePath7();
/* 193 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/* 194 */     paramGraphics2D.fill(this.path);
/* 195 */     this.path = decodePath2();
/* 196 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 197 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndMouseOver(Graphics2D paramGraphics2D) {
/* 202 */     this.path = decodePath8();
/* 203 */     paramGraphics2D.setPaint(decodeGradient11(this.path));
/* 204 */     paramGraphics2D.fill(this.path);
/* 205 */     this.path = decodePath9();
/* 206 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/* 207 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndPressed(Graphics2D paramGraphics2D) {
/* 212 */     this.path = decodePath8();
/* 213 */     paramGraphics2D.setPaint(decodeGradient13(this.path));
/* 214 */     paramGraphics2D.fill(this.path);
/* 215 */     this.path = decodePath9();
/* 216 */     paramGraphics2D.setPaint(decodeGradient14(this.path));
/* 217 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndFocused(Graphics2D paramGraphics2D) {
/* 222 */     this.path = decodePath1();
/* 223 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/* 224 */     paramGraphics2D.fill(this.path);
/* 225 */     this.path = decodePath10();
/* 226 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 227 */     paramGraphics2D.fill(this.path);
/* 228 */     this.path = decodePath11();
/* 229 */     paramGraphics2D.setPaint(this.color37);
/* 230 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndMouseOverAndFocused(Graphics2D paramGraphics2D) {
/* 235 */     this.path = decodePath12();
/* 236 */     paramGraphics2D.setPaint(decodeGradient11(this.path));
/* 237 */     paramGraphics2D.fill(this.path);
/* 238 */     this.path = decodePath13();
/* 239 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/* 240 */     paramGraphics2D.fill(this.path);
/* 241 */     this.path = decodePath14();
/* 242 */     paramGraphics2D.setPaint(this.color37);
/* 243 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndPressedAndFocused(Graphics2D paramGraphics2D) {
/* 248 */     this.path = decodePath12();
/* 249 */     paramGraphics2D.setPaint(decodeGradient13(this.path));
/* 250 */     paramGraphics2D.fill(this.path);
/* 251 */     this.path = decodePath13();
/* 252 */     paramGraphics2D.setPaint(decodeGradient14(this.path));
/* 253 */     paramGraphics2D.fill(this.path);
/* 254 */     this.path = decodePath14();
/* 255 */     paramGraphics2D.setPaint(this.color37);
/* 256 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path2D decodePath1() {
/* 263 */     this.path.reset();
/* 264 */     this.path.moveTo(decodeX(0.0F), decodeY(0.71428573F));
/* 265 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeAnchorX(0.71428573F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(0.71428573F), decodeY(0.0F));
/* 266 */     this.path.curveTo(decodeAnchorX(0.71428573F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(2.2857144F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(2.2857144F), decodeY(0.0F));
/* 267 */     this.path.curveTo(decodeAnchorX(2.2857144F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeX(3.0F), decodeY(0.71428573F));
/* 268 */     this.path.curveTo(decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeX(3.0F), decodeY(3.0F));
/* 269 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 270 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeX(0.0F), decodeY(0.71428573F));
/* 271 */     this.path.closePath();
/* 272 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 276 */     this.path.reset();
/* 277 */     this.path.moveTo(decodeX(0.14285715F), decodeY(2.0F));
/* 278 */     this.path.curveTo(decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(0.85714287F, 3.5555556F), decodeX(0.14285715F), decodeY(0.85714287F));
/* 279 */     this.path.curveTo(decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(0.85714287F, -3.5555556F), decodeAnchorX(0.85714287F, -3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeX(0.85714287F), decodeY(0.14285715F));
/* 280 */     this.path.curveTo(decodeAnchorX(0.85714287F, 3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.142857F, -3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeX(2.142857F), decodeY(0.14285715F));
/* 281 */     this.path.curveTo(decodeAnchorX(2.142857F, 3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(0.85714287F, -3.2777777F), decodeX(2.857143F), decodeY(0.85714287F));
/* 282 */     this.path.curveTo(decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(0.85714287F, 3.2777777F), decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeX(2.857143F), decodeY(2.0F));
/* 283 */     this.path.lineTo(decodeX(0.14285715F), decodeY(2.0F));
/* 284 */     this.path.closePath();
/* 285 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 289 */     this.path.reset();
/* 290 */     this.path.moveTo(decodeX(0.0F), decodeY(0.71428573F));
/* 291 */     this.path.curveTo(decodeAnchorX(0.0F, 0.055555556F), decodeAnchorY(0.71428573F, 2.6111112F), decodeAnchorX(0.8333333F, -2.5F), decodeAnchorY(0.0F, 0.0F), decodeX(0.8333333F), decodeY(0.0F));
/* 292 */     this.path.curveTo(decodeAnchorX(0.8333333F, 2.5F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(2.2857144F, -2.7222223F), decodeAnchorY(0.0F, 0.0F), decodeX(2.2857144F), decodeY(0.0F));
/* 293 */     this.path.curveTo(decodeAnchorX(2.2857144F, 2.7222223F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(3.0F, -0.055555556F), decodeAnchorY(0.71428573F, -2.7222223F), decodeX(3.0F), decodeY(0.71428573F));
/* 294 */     this.path.curveTo(decodeAnchorX(3.0F, 0.055555556F), decodeAnchorY(0.71428573F, 2.7222223F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeX(3.0F), decodeY(3.0F));
/* 295 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 296 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeAnchorX(0.0F, -0.055555556F), decodeAnchorY(0.71428573F, -2.6111112F), decodeX(0.0F), decodeY(0.71428573F));
/* 297 */     this.path.closePath();
/* 298 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 302 */     this.path.reset();
/* 303 */     this.path.moveTo(decodeX(0.16666667F), decodeY(2.0F));
/* 304 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.85714287F, 3.6666667F), decodeX(0.16666667F), decodeY(0.85714287F));
/* 305 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.85714287F, -3.6666667F), decodeAnchorX(1.0F, -3.5555556F), decodeAnchorY(0.14285715F, 0.0F), decodeX(1.0F), decodeY(0.14285715F));
/* 306 */     this.path.curveTo(decodeAnchorX(1.0F, 3.5555556F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.142857F, -3.5F), decodeAnchorY(0.14285715F, 0.055555556F), decodeX(2.142857F), decodeY(0.14285715F));
/* 307 */     this.path.curveTo(decodeAnchorX(2.142857F, 3.5F), decodeAnchorY(0.14285715F, -0.055555556F), decodeAnchorX(2.857143F, 0.055555556F), decodeAnchorY(0.85714287F, -3.6666667F), decodeX(2.857143F), decodeY(0.85714287F));
/* 308 */     this.path.curveTo(decodeAnchorX(2.857143F, -0.055555556F), decodeAnchorY(0.85714287F, 3.6666667F), decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeX(2.857143F), decodeY(2.0F));
/* 309 */     this.path.lineTo(decodeX(0.16666667F), decodeY(2.0F));
/* 310 */     this.path.closePath();
/* 311 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 315 */     this.path.reset();
/* 316 */     this.path.moveTo(decodeX(0.0F), decodeY(0.8333333F));
/* 317 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.8333333F, -3.0F), decodeAnchorX(0.71428573F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(0.71428573F), decodeY(0.0F));
/* 318 */     this.path.curveTo(decodeAnchorX(0.71428573F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(2.2857144F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(2.2857144F), decodeY(0.0F));
/* 319 */     this.path.curveTo(decodeAnchorX(2.2857144F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.8333333F, -3.0F), decodeX(3.0F), decodeY(0.8333333F));
/* 320 */     this.path.curveTo(decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.8333333F, 3.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeX(3.0F), decodeY(3.0F));
/* 321 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 322 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.8333333F, 3.0F), decodeX(0.0F), decodeY(0.8333333F));
/* 323 */     this.path.closePath();
/* 324 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath6() {
/* 328 */     this.path.reset();
/* 329 */     this.path.moveTo(decodeX(0.14285715F), decodeY(2.0F));
/* 330 */     this.path.curveTo(decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(1.0F, 3.5555556F), decodeX(0.14285715F), decodeY(1.0F));
/* 331 */     this.path.curveTo(decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(1.0F, -3.5555556F), decodeAnchorX(0.85714287F, -3.4444444F), decodeAnchorY(0.16666667F, 0.0F), decodeX(0.85714287F), decodeY(0.16666667F));
/* 332 */     this.path.curveTo(decodeAnchorX(0.85714287F, 3.4444444F), decodeAnchorY(0.16666667F, 0.0F), decodeAnchorX(2.142857F, -3.3333333F), decodeAnchorY(0.16666667F, 0.0F), decodeX(2.142857F), decodeY(0.16666667F));
/* 333 */     this.path.curveTo(decodeAnchorX(2.142857F, 3.3333333F), decodeAnchorY(0.16666667F, 0.0F), decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(1.0F, -3.2777777F), decodeX(2.857143F), decodeY(1.0F));
/* 334 */     this.path.curveTo(decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(1.0F, 3.2777777F), decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeX(2.857143F), decodeY(2.0F));
/* 335 */     this.path.lineTo(decodeX(0.14285715F), decodeY(2.0F));
/* 336 */     this.path.closePath();
/* 337 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath7() {
/* 341 */     this.path.reset();
/* 342 */     this.path.moveTo(decodeX(0.0F), decodeY(0.71428573F));
/* 343 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeAnchorX(0.71428573F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(0.71428573F), decodeY(0.0F));
/* 344 */     this.path.curveTo(decodeAnchorX(0.71428573F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(2.2857144F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(2.2857144F), decodeY(0.0F));
/* 345 */     this.path.curveTo(decodeAnchorX(2.2857144F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeX(3.0F), decodeY(0.71428573F));
/* 346 */     this.path.curveTo(decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeX(3.0F), decodeY(2.0F));
/* 347 */     this.path.lineTo(decodeX(0.0F), decodeY(2.0F));
/* 348 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeX(0.0F), decodeY(0.71428573F));
/* 349 */     this.path.closePath();
/* 350 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath8() {
/* 354 */     this.path.reset();
/* 355 */     this.path.moveTo(decodeX(0.0F), decodeY(0.71428573F));
/* 356 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeAnchorX(0.5555556F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(0.5555556F), decodeY(0.0F));
/* 357 */     this.path.curveTo(decodeAnchorX(0.5555556F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(2.4444444F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(2.4444444F), decodeY(0.0F));
/* 358 */     this.path.curveTo(decodeAnchorX(2.4444444F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeX(3.0F), decodeY(0.71428573F));
/* 359 */     this.path.curveTo(decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeX(3.0F), decodeY(2.0F));
/* 360 */     this.path.lineTo(decodeX(0.0F), decodeY(2.0F));
/* 361 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeX(0.0F), decodeY(0.71428573F));
/* 362 */     this.path.closePath();
/* 363 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath9() {
/* 367 */     this.path.reset();
/* 368 */     this.path.moveTo(decodeX(0.11111111F), decodeY(2.0F));
/* 369 */     this.path.curveTo(decodeAnchorX(0.11111111F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeAnchorX(0.11111111F, 0.0F), decodeAnchorY(0.85714287F, 3.5555556F), decodeX(0.11111111F), decodeY(0.85714287F));
/* 370 */     this.path.curveTo(decodeAnchorX(0.11111111F, 0.0F), decodeAnchorY(0.85714287F, -3.5555556F), decodeAnchorX(0.6666667F, -3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeX(0.6666667F), decodeY(0.14285715F));
/* 371 */     this.path.curveTo(decodeAnchorX(0.6666667F, 3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.3333333F, -3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeX(2.3333333F), decodeY(0.14285715F));
/* 372 */     this.path.curveTo(decodeAnchorX(2.3333333F, 3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.8888888F, 0.0F), decodeAnchorY(0.85714287F, -3.2777777F), decodeX(2.8888888F), decodeY(0.85714287F));
/* 373 */     this.path.curveTo(decodeAnchorX(2.8888888F, 0.0F), decodeAnchorY(0.85714287F, 3.2777777F), decodeAnchorX(2.8888888F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeX(2.8888888F), decodeY(2.0F));
/* 374 */     this.path.lineTo(decodeX(0.11111111F), decodeY(2.0F));
/* 375 */     this.path.closePath();
/* 376 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath10() {
/* 380 */     this.path.reset();
/* 381 */     this.path.moveTo(decodeX(0.14285715F), decodeY(3.0F));
/* 382 */     this.path.curveTo(decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(0.85714287F, 3.5555556F), decodeX(0.14285715F), decodeY(0.85714287F));
/* 383 */     this.path.curveTo(decodeAnchorX(0.14285715F, 0.0F), decodeAnchorY(0.85714287F, -3.5555556F), decodeAnchorX(0.85714287F, -3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeX(0.85714287F), decodeY(0.14285715F));
/* 384 */     this.path.curveTo(decodeAnchorX(0.85714287F, 3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.142857F, -3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeX(2.142857F), decodeY(0.14285715F));
/* 385 */     this.path.curveTo(decodeAnchorX(2.142857F, 3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(0.85714287F, -3.2777777F), decodeX(2.857143F), decodeY(0.85714287F));
/* 386 */     this.path.curveTo(decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(0.85714287F, 3.2777777F), decodeAnchorX(2.857143F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeX(2.857143F), decodeY(3.0F));
/* 387 */     this.path.lineTo(decodeX(0.14285715F), decodeY(3.0F));
/* 388 */     this.path.closePath();
/* 389 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath11() {
/* 393 */     this.path.reset();
/* 394 */     this.path.moveTo(decodeX(1.4638889F), decodeY(2.25F));
/* 395 */     this.path.lineTo(decodeX(1.4652778F), decodeY(2.777778F));
/* 396 */     this.path.lineTo(decodeX(0.3809524F), decodeY(2.777778F));
/* 397 */     this.path.lineTo(decodeX(0.375F), decodeY(0.88095236F));
/* 398 */     this.path.curveTo(decodeAnchorX(0.375F, 0.0F), decodeAnchorY(0.88095236F, -2.25F), decodeAnchorX(0.8452381F, -1.9166666F), decodeAnchorY(0.3809524F, 0.0F), decodeX(0.8452381F), decodeY(0.3809524F));
/* 399 */     this.path.lineTo(decodeX(2.1011903F), decodeY(0.3809524F));
/* 400 */     this.path.curveTo(decodeAnchorX(2.1011903F, 2.125F), decodeAnchorY(0.3809524F, 0.0F), decodeAnchorX(2.6309526F, 0.0F), decodeAnchorY(0.8630952F, -2.5833333F), decodeX(2.6309526F), decodeY(0.8630952F));
/* 401 */     this.path.lineTo(decodeX(2.625F), decodeY(2.7638886F));
/* 402 */     this.path.lineTo(decodeX(1.4666667F), decodeY(2.777778F));
/* 403 */     this.path.lineTo(decodeX(1.4638889F), decodeY(2.2361114F));
/* 404 */     this.path.lineTo(decodeX(2.3869045F), decodeY(2.222222F));
/* 405 */     this.path.lineTo(decodeX(2.375F), decodeY(0.86904764F));
/* 406 */     this.path.curveTo(decodeAnchorX(2.375F, -7.1054274E-15F), decodeAnchorY(0.86904764F, -0.9166667F), decodeAnchorX(2.0952382F, 1.0833334F), decodeAnchorY(0.60714287F, -1.7763568E-15F), decodeX(2.0952382F), decodeY(0.60714287F));
/* 407 */     this.path.lineTo(decodeX(0.8333334F), decodeY(0.6130952F));
/* 408 */     this.path.curveTo(decodeAnchorX(0.8333334F, -1.0F), decodeAnchorY(0.6130952F, 0.0F), decodeAnchorX(0.625F, 0.041666668F), decodeAnchorY(0.86904764F, -0.9583333F), decodeX(0.625F), decodeY(0.86904764F));
/* 409 */     this.path.lineTo(decodeX(0.6130952F), decodeY(2.2361114F));
/* 410 */     this.path.lineTo(decodeX(1.4638889F), decodeY(2.25F));
/* 411 */     this.path.closePath();
/* 412 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath12() {
/* 416 */     this.path.reset();
/* 417 */     this.path.moveTo(decodeX(0.0F), decodeY(0.71428573F));
/* 418 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeAnchorX(0.5555556F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(0.5555556F), decodeY(0.0F));
/* 419 */     this.path.curveTo(decodeAnchorX(0.5555556F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(2.4444444F, -3.0F), decodeAnchorY(0.0F, 0.0F), decodeX(2.4444444F), decodeY(0.0F));
/* 420 */     this.path.curveTo(decodeAnchorX(2.4444444F, 3.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, -3.0F), decodeX(3.0F), decodeY(0.71428573F));
/* 421 */     this.path.curveTo(decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeX(3.0F), decodeY(3.0F));
/* 422 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 423 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeAnchorX(0.0F, 0.0F), decodeAnchorY(0.71428573F, 3.0F), decodeX(0.0F), decodeY(0.71428573F));
/* 424 */     this.path.closePath();
/* 425 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath13() {
/* 429 */     this.path.reset();
/* 430 */     this.path.moveTo(decodeX(0.11111111F), decodeY(3.0F));
/* 431 */     this.path.curveTo(decodeAnchorX(0.11111111F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeAnchorX(0.11111111F, 0.0F), decodeAnchorY(0.85714287F, 3.5555556F), decodeX(0.11111111F), decodeY(0.85714287F));
/* 432 */     this.path.curveTo(decodeAnchorX(0.11111111F, 0.0F), decodeAnchorY(0.85714287F, -3.5555556F), decodeAnchorX(0.6666667F, -3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeX(0.6666667F), decodeY(0.14285715F));
/* 433 */     this.path.curveTo(decodeAnchorX(0.6666667F, 3.4444444F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.3333333F, -3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeX(2.3333333F), decodeY(0.14285715F));
/* 434 */     this.path.curveTo(decodeAnchorX(2.3333333F, 3.3333333F), decodeAnchorY(0.14285715F, 0.0F), decodeAnchorX(2.8888888F, 0.0F), decodeAnchorY(0.85714287F, -3.2777777F), decodeX(2.8888888F), decodeY(0.85714287F));
/* 435 */     this.path.curveTo(decodeAnchorX(2.8888888F, 0.0F), decodeAnchorY(0.85714287F, 3.2777777F), decodeAnchorX(2.8888888F, 0.0F), decodeAnchorY(3.0F, 0.0F), decodeX(2.8888888F), decodeY(3.0F));
/* 436 */     this.path.lineTo(decodeX(0.11111111F), decodeY(3.0F));
/* 437 */     this.path.closePath();
/* 438 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath14() {
/* 442 */     this.path.reset();
/* 443 */     this.path.moveTo(decodeX(1.4583333F), decodeY(2.25F));
/* 444 */     this.path.lineTo(decodeX(1.4599359F), decodeY(2.777778F));
/* 445 */     this.path.lineTo(decodeX(0.2962963F), decodeY(2.777778F));
/* 446 */     this.path.lineTo(decodeX(0.29166666F), decodeY(0.88095236F));
/* 447 */     this.path.curveTo(decodeAnchorX(0.29166666F, 0.0F), decodeAnchorY(0.88095236F, -2.25F), decodeAnchorX(0.6574074F, -1.9166666F), decodeAnchorY(0.3809524F, 0.0F), decodeX(0.6574074F), decodeY(0.3809524F));
/* 448 */     this.path.lineTo(decodeX(2.3009257F), decodeY(0.3809524F));
/* 449 */     this.path.curveTo(decodeAnchorX(2.3009257F, 2.125F), decodeAnchorY(0.3809524F, 0.0F), decodeAnchorX(2.712963F, 0.0F), decodeAnchorY(0.8630952F, -2.5833333F), decodeX(2.712963F), decodeY(0.8630952F));
/* 450 */     this.path.lineTo(decodeX(2.7083333F), decodeY(2.7638886F));
/* 451 */     this.path.lineTo(decodeX(1.4615384F), decodeY(2.777778F));
/* 452 */     this.path.lineTo(decodeX(1.4583333F), decodeY(2.2361114F));
/* 453 */     this.path.lineTo(decodeX(2.523148F), decodeY(2.222222F));
/* 454 */     this.path.lineTo(decodeX(2.5138888F), decodeY(0.86904764F));
/* 455 */     this.path.curveTo(decodeAnchorX(2.5138888F, -7.1054274E-15F), decodeAnchorY(0.86904764F, -0.9166667F), decodeAnchorX(2.2962964F, 1.0833334F), decodeAnchorY(0.60714287F, -1.7763568E-15F), decodeX(2.2962964F), decodeY(0.60714287F));
/* 456 */     this.path.lineTo(decodeX(0.6481482F), decodeY(0.6130952F));
/* 457 */     this.path.curveTo(decodeAnchorX(0.6481482F, -1.0F), decodeAnchorY(0.6130952F, 0.0F), decodeAnchorX(0.4861111F, 0.041666668F), decodeAnchorY(0.86904764F, -0.9583333F), decodeX(0.4861111F), decodeY(0.86904764F));
/* 458 */     this.path.lineTo(decodeX(0.47685182F), decodeY(2.2361114F));
/* 459 */     this.path.lineTo(decodeX(1.4583333F), decodeY(2.25F));
/* 460 */     this.path.closePath();
/* 461 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 467 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 468 */     float f1 = (float)rectangle2D.getX();
/* 469 */     float f2 = (float)rectangle2D.getY();
/* 470 */     float f3 = (float)rectangle2D.getWidth();
/* 471 */     float f4 = (float)rectangle2D.getHeight();
/* 472 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 475 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 480 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 481 */     float f1 = (float)rectangle2D.getX();
/* 482 */     float f2 = (float)rectangle2D.getY();
/* 483 */     float f3 = (float)rectangle2D.getWidth();
/* 484 */     float f4 = (float)rectangle2D.getHeight();
/* 485 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.1F, 0.2F, 0.6F, 1.0F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 488 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 490 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 495 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 496 */     float f1 = (float)rectangle2D.getX();
/* 497 */     float f2 = (float)rectangle2D.getY();
/* 498 */     float f3 = (float)rectangle2D.getWidth();
/* 499 */     float f4 = (float)rectangle2D.getHeight();
/* 500 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color6, 
/*     */ 
/*     */           
/* 503 */           decodeColor(this.color6, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 508 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 509 */     float f1 = (float)rectangle2D.getX();
/* 510 */     float f2 = (float)rectangle2D.getY();
/* 511 */     float f3 = (float)rectangle2D.getWidth();
/* 512 */     float f4 = (float)rectangle2D.getHeight();
/* 513 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.1F, 0.2F, 0.6F, 1.0F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 516 */           decodeColor(this.color7, this.color8, 0.5F), this.color8, 
/*     */           
/* 518 */           decodeColor(this.color8, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 523 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 524 */     float f1 = (float)rectangle2D.getX();
/* 525 */     float f2 = (float)rectangle2D.getY();
/* 526 */     float f3 = (float)rectangle2D.getWidth();
/* 527 */     float f4 = (float)rectangle2D.getHeight();
/* 528 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color10, 
/*     */ 
/*     */           
/* 531 */           decodeColor(this.color10, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 536 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 537 */     float f1 = (float)rectangle2D.getX();
/* 538 */     float f2 = (float)rectangle2D.getY();
/* 539 */     float f3 = (float)rectangle2D.getWidth();
/* 540 */     float f4 = (float)rectangle2D.getHeight();
/* 541 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.1F, 0.2F, 0.42096776F, 0.64193547F, 0.82096773F, 1.0F }, new Color[] { this.color11, 
/*     */ 
/*     */           
/* 544 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*     */           
/* 546 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*     */           
/* 548 */           decodeColor(this.color13, this.color14, 0.5F), this.color14 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 553 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 554 */     float f1 = (float)rectangle2D.getX();
/* 555 */     float f2 = (float)rectangle2D.getY();
/* 556 */     float f3 = (float)rectangle2D.getWidth();
/* 557 */     float f4 = (float)rectangle2D.getHeight();
/* 558 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color15, 
/*     */ 
/*     */           
/* 561 */           decodeColor(this.color15, this.color16, 0.5F), this.color16 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 566 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 567 */     float f1 = (float)rectangle2D.getX();
/* 568 */     float f2 = (float)rectangle2D.getY();
/* 569 */     float f3 = (float)rectangle2D.getWidth();
/* 570 */     float f4 = (float)rectangle2D.getHeight();
/* 571 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.1F, 0.2F, 0.6F, 1.0F }, new Color[] { this.color17, 
/*     */ 
/*     */           
/* 574 */           decodeColor(this.color17, this.color18, 0.5F), this.color18, 
/*     */           
/* 576 */           decodeColor(this.color18, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 581 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 582 */     float f1 = (float)rectangle2D.getX();
/* 583 */     float f2 = (float)rectangle2D.getY();
/* 584 */     float f3 = (float)rectangle2D.getWidth();
/* 585 */     float f4 = (float)rectangle2D.getHeight();
/* 586 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.12419355F, 0.2483871F, 0.42580646F, 0.6032258F, 0.6854839F, 0.7677419F, 0.88387096F, 1.0F }, new Color[] { this.color19, 
/*     */ 
/*     */           
/* 589 */           decodeColor(this.color19, this.color20, 0.5F), this.color20, 
/*     */           
/* 591 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 593 */           decodeColor(this.color21, this.color22, 0.5F), this.color22, 
/*     */           
/* 595 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 600 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 601 */     float f1 = (float)rectangle2D.getX();
/* 602 */     float f2 = (float)rectangle2D.getY();
/* 603 */     float f3 = (float)rectangle2D.getWidth();
/* 604 */     float f4 = (float)rectangle2D.getHeight();
/* 605 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 608 */           decodeColor(this.color24, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient11(Shape paramShape) {
/* 613 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 614 */     float f1 = (float)rectangle2D.getX();
/* 615 */     float f2 = (float)rectangle2D.getY();
/* 616 */     float f3 = (float)rectangle2D.getWidth();
/* 617 */     float f4 = (float)rectangle2D.getHeight();
/* 618 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color25, 
/*     */ 
/*     */           
/* 621 */           decodeColor(this.color25, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient12(Shape paramShape) {
/* 626 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 627 */     float f1 = (float)rectangle2D.getX();
/* 628 */     float f2 = (float)rectangle2D.getY();
/* 629 */     float f3 = (float)rectangle2D.getWidth();
/* 630 */     float f4 = (float)rectangle2D.getHeight();
/* 631 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.12419355F, 0.2483871F, 0.42580646F, 0.6032258F, 0.6854839F, 0.7677419F, 0.86774194F, 0.9677419F }, new Color[] { this.color26, 
/*     */ 
/*     */           
/* 634 */           decodeColor(this.color26, this.color27, 0.5F), this.color27, 
/*     */           
/* 636 */           decodeColor(this.color27, this.color28, 0.5F), this.color28, 
/*     */           
/* 638 */           decodeColor(this.color28, this.color29, 0.5F), this.color29, 
/*     */           
/* 640 */           decodeColor(this.color29, this.color30, 0.5F), this.color30 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient13(Shape paramShape) {
/* 645 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 646 */     float f1 = (float)rectangle2D.getX();
/* 647 */     float f2 = (float)rectangle2D.getY();
/* 648 */     float f3 = (float)rectangle2D.getWidth();
/* 649 */     float f4 = (float)rectangle2D.getHeight();
/* 650 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color25, 
/*     */ 
/*     */           
/* 653 */           decodeColor(this.color25, this.color31, 0.5F), this.color31 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient14(Shape paramShape) {
/* 658 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 659 */     float f1 = (float)rectangle2D.getX();
/* 660 */     float f2 = (float)rectangle2D.getY();
/* 661 */     float f3 = (float)rectangle2D.getWidth();
/* 662 */     float f4 = (float)rectangle2D.getHeight();
/* 663 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.12419355F, 0.2483871F, 0.42580646F, 0.6032258F, 0.6854839F, 0.7677419F, 0.8548387F, 0.9419355F }, new Color[] { this.color32, 
/*     */ 
/*     */           
/* 666 */           decodeColor(this.color32, this.color33, 0.5F), this.color33, 
/*     */           
/* 668 */           decodeColor(this.color33, this.color34, 0.5F), this.color34, 
/*     */           
/* 670 */           decodeColor(this.color34, this.color35, 0.5F), this.color35, 
/*     */           
/* 672 */           decodeColor(this.color35, this.color36, 0.5F), this.color36 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/TabbedPaneTabPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */