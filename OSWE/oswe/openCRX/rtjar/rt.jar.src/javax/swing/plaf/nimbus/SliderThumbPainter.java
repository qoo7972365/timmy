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
/*     */ final class SliderThumbPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_FOCUSED = 3;
/*     */   static final int BACKGROUND_FOCUSED_MOUSEOVER = 4;
/*     */   static final int BACKGROUND_FOCUSED_PRESSED = 5;
/*     */   static final int BACKGROUND_MOUSEOVER = 6;
/*     */   static final int BACKGROUND_PRESSED = 7;
/*     */   static final int BACKGROUND_ENABLED_ARROWSHAPE = 8;
/*     */   static final int BACKGROUND_DISABLED_ARROWSHAPE = 9;
/*     */   static final int BACKGROUND_MOUSEOVER_ARROWSHAPE = 10;
/*     */   static final int BACKGROUND_PRESSED_ARROWSHAPE = 11;
/*     */   static final int BACKGROUND_FOCUSED_ARROWSHAPE = 12;
/*     */   static final int BACKGROUND_FOCUSED_MOUSEOVER_ARROWSHAPE = 13;
/*     */   static final int BACKGROUND_FOCUSED_PRESSED_ARROWSHAPE = 14;
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
/*  67 */   private Color color1 = decodeColor("nimbusBase", 0.021348298F, -0.5625436F, 0.25490195F, 0);
/*  68 */   private Color color2 = decodeColor("nimbusBase", 0.015098333F, -0.55105823F, 0.19215685F, 0);
/*  69 */   private Color color3 = decodeColor("nimbusBase", 0.021348298F, -0.5924243F, 0.35686272F, 0);
/*  70 */   private Color color4 = decodeColor("nimbusBase", 0.021348298F, -0.56722116F, 0.3098039F, 0);
/*  71 */   private Color color5 = decodeColor("nimbusBase", 0.021348298F, -0.56844974F, 0.32549018F, 0);
/*  72 */   private Color color6 = decodeColor("nimbusBlueGrey", -0.003968239F, 0.0014736876F, -0.25490198F, -156);
/*  73 */   private Color color7 = decodeColor("nimbusBase", 5.1498413E-4F, -0.34585923F, -0.007843137F, 0);
/*  74 */   private Color color8 = decodeColor("nimbusBase", -0.0017285943F, -0.11571431F, -0.25490198F, 0);
/*  75 */   private Color color9 = decodeColor("nimbusBase", -0.023096085F, -0.6238095F, 0.43921566F, 0);
/*  76 */   private Color color10 = decodeColor("nimbusBase", 5.1498413E-4F, -0.43866998F, 0.24705881F, 0);
/*  77 */   private Color color11 = decodeColor("nimbusBase", 5.1498413E-4F, -0.45714286F, 0.32941175F, 0);
/*  78 */   private Color color12 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  79 */   private Color color13 = decodeColor("nimbusBase", -0.0038217902F, -0.15532213F, -0.14901963F, 0);
/*  80 */   private Color color14 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54509807F, 0);
/*  81 */   private Color color15 = decodeColor("nimbusBase", 0.004681647F, -0.62780917F, 0.44313723F, 0);
/*  82 */   private Color color16 = decodeColor("nimbusBase", 2.9569864E-4F, -0.4653107F, 0.32549018F, 0);
/*  83 */   private Color color17 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4563421F, 0.32549018F, 0);
/*  84 */   private Color color18 = decodeColor("nimbusBase", -0.0017285943F, -0.4732143F, 0.39215684F, 0);
/*  85 */   private Color color19 = decodeColor("nimbusBase", 0.0015952587F, -0.04875779F, -0.18823531F, 0);
/*  86 */   private Color color20 = decodeColor("nimbusBase", 2.9569864E-4F, -0.44943976F, 0.25098038F, 0);
/*  87 */   private Color color21 = decodeColor("nimbusBase", 0.0F, 0.0F, 0.0F, 0);
/*  88 */   private Color color22 = decodeColor("nimbusBase", 8.9377165E-4F, -0.121094406F, 0.12156862F, 0);
/*  89 */   private Color color23 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -121);
/*  90 */   private Color color24 = new Color(150, 156, 168, 146);
/*  91 */   private Color color25 = decodeColor("nimbusBase", -0.0033828616F, -0.40608466F, -0.019607842F, 0);
/*  92 */   private Color color26 = decodeColor("nimbusBase", 5.1498413E-4F, -0.17594418F, -0.20784315F, 0);
/*  93 */   private Color color27 = decodeColor("nimbusBase", 0.0023007393F, -0.11332625F, -0.28627452F, 0);
/*  94 */   private Color color28 = decodeColor("nimbusBase", -0.023096085F, -0.62376213F, 0.4352941F, 0);
/*  95 */   private Color color29 = decodeColor("nimbusBase", 0.004681647F, -0.594392F, 0.39999998F, 0);
/*  96 */   private Color color30 = decodeColor("nimbusBase", -0.0017285943F, -0.4454704F, 0.25490195F, 0);
/*  97 */   private Color color31 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4625541F, 0.35686272F, 0);
/*  98 */   private Color color32 = decodeColor("nimbusBase", 5.1498413E-4F, -0.47442397F, 0.4235294F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public SliderThumbPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 106 */     this.state = paramInt;
/* 107 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 113 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 116 */     switch (this.state) { case 1:
/* 117 */         paintBackgroundDisabled(paramGraphics2D); break;
/* 118 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/* 119 */       case 3: paintBackgroundFocused(paramGraphics2D); break;
/* 120 */       case 4: paintBackgroundFocusedAndMouseOver(paramGraphics2D); break;
/* 121 */       case 5: paintBackgroundFocusedAndPressed(paramGraphics2D); break;
/* 122 */       case 6: paintBackgroundMouseOver(paramGraphics2D); break;
/* 123 */       case 7: paintBackgroundPressed(paramGraphics2D); break;
/* 124 */       case 8: paintBackgroundEnabledAndArrowShape(paramGraphics2D); break;
/* 125 */       case 9: paintBackgroundDisabledAndArrowShape(paramGraphics2D); break;
/* 126 */       case 10: paintBackgroundMouseOverAndArrowShape(paramGraphics2D); break;
/* 127 */       case 11: paintBackgroundPressedAndArrowShape(paramGraphics2D); break;
/* 128 */       case 12: paintBackgroundFocusedAndArrowShape(paramGraphics2D); break;
/* 129 */       case 13: paintBackgroundFocusedAndMouseOverAndArrowShape(paramGraphics2D); break;
/* 130 */       case 14: paintBackgroundFocusedAndPressedAndArrowShape(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 139 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 143 */     this.ellipse = decodeEllipse1();
/* 144 */     paramGraphics2D.setPaint(decodeGradient1(this.ellipse));
/* 145 */     paramGraphics2D.fill(this.ellipse);
/* 146 */     this.ellipse = decodeEllipse2();
/* 147 */     paramGraphics2D.setPaint(decodeGradient2(this.ellipse));
/* 148 */     paramGraphics2D.fill(this.ellipse);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 153 */     this.ellipse = decodeEllipse3();
/* 154 */     paramGraphics2D.setPaint(this.color6);
/* 155 */     paramGraphics2D.fill(this.ellipse);
/* 156 */     this.ellipse = decodeEllipse1();
/* 157 */     paramGraphics2D.setPaint(decodeGradient3(this.ellipse));
/* 158 */     paramGraphics2D.fill(this.ellipse);
/* 159 */     this.ellipse = decodeEllipse2();
/* 160 */     paramGraphics2D.setPaint(decodeGradient4(this.ellipse));
/* 161 */     paramGraphics2D.fill(this.ellipse);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocused(Graphics2D paramGraphics2D) {
/* 166 */     this.ellipse = decodeEllipse4();
/* 167 */     paramGraphics2D.setPaint(this.color12);
/* 168 */     paramGraphics2D.fill(this.ellipse);
/* 169 */     this.ellipse = decodeEllipse1();
/* 170 */     paramGraphics2D.setPaint(decodeGradient3(this.ellipse));
/* 171 */     paramGraphics2D.fill(this.ellipse);
/* 172 */     this.ellipse = decodeEllipse2();
/* 173 */     paramGraphics2D.setPaint(decodeGradient4(this.ellipse));
/* 174 */     paramGraphics2D.fill(this.ellipse);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocusedAndMouseOver(Graphics2D paramGraphics2D) {
/* 179 */     this.ellipse = decodeEllipse4();
/* 180 */     paramGraphics2D.setPaint(this.color12);
/* 181 */     paramGraphics2D.fill(this.ellipse);
/* 182 */     this.ellipse = decodeEllipse1();
/* 183 */     paramGraphics2D.setPaint(decodeGradient5(this.ellipse));
/* 184 */     paramGraphics2D.fill(this.ellipse);
/* 185 */     this.ellipse = decodeEllipse2();
/* 186 */     paramGraphics2D.setPaint(decodeGradient6(this.ellipse));
/* 187 */     paramGraphics2D.fill(this.ellipse);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocusedAndPressed(Graphics2D paramGraphics2D) {
/* 192 */     this.ellipse = decodeEllipse4();
/* 193 */     paramGraphics2D.setPaint(this.color12);
/* 194 */     paramGraphics2D.fill(this.ellipse);
/* 195 */     this.ellipse = decodeEllipse1();
/* 196 */     paramGraphics2D.setPaint(decodeGradient7(this.ellipse));
/* 197 */     paramGraphics2D.fill(this.ellipse);
/* 198 */     this.ellipse = decodeEllipse2();
/* 199 */     paramGraphics2D.setPaint(decodeGradient8(this.ellipse));
/* 200 */     paramGraphics2D.fill(this.ellipse);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 205 */     this.ellipse = decodeEllipse3();
/* 206 */     paramGraphics2D.setPaint(this.color6);
/* 207 */     paramGraphics2D.fill(this.ellipse);
/* 208 */     this.ellipse = decodeEllipse1();
/* 209 */     paramGraphics2D.setPaint(decodeGradient5(this.ellipse));
/* 210 */     paramGraphics2D.fill(this.ellipse);
/* 211 */     this.ellipse = decodeEllipse2();
/* 212 */     paramGraphics2D.setPaint(decodeGradient6(this.ellipse));
/* 213 */     paramGraphics2D.fill(this.ellipse);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 218 */     this.ellipse = decodeEllipse3();
/* 219 */     paramGraphics2D.setPaint(this.color23);
/* 220 */     paramGraphics2D.fill(this.ellipse);
/* 221 */     this.ellipse = decodeEllipse1();
/* 222 */     paramGraphics2D.setPaint(decodeGradient7(this.ellipse));
/* 223 */     paramGraphics2D.fill(this.ellipse);
/* 224 */     this.ellipse = decodeEllipse2();
/* 225 */     paramGraphics2D.setPaint(decodeGradient8(this.ellipse));
/* 226 */     paramGraphics2D.fill(this.ellipse);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndArrowShape(Graphics2D paramGraphics2D) {
/* 231 */     this.path = decodePath1();
/* 232 */     paramGraphics2D.setPaint(this.color24);
/* 233 */     paramGraphics2D.fill(this.path);
/* 234 */     this.path = decodePath2();
/* 235 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 236 */     paramGraphics2D.fill(this.path);
/* 237 */     this.path = decodePath3();
/* 238 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/* 239 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabledAndArrowShape(Graphics2D paramGraphics2D) {
/* 244 */     this.path = decodePath2();
/* 245 */     paramGraphics2D.setPaint(decodeGradient11(this.path));
/* 246 */     paramGraphics2D.fill(this.path);
/* 247 */     this.path = decodePath3();
/* 248 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/* 249 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndArrowShape(Graphics2D paramGraphics2D) {
/* 254 */     this.path = decodePath1();
/* 255 */     paramGraphics2D.setPaint(this.color24);
/* 256 */     paramGraphics2D.fill(this.path);
/* 257 */     this.path = decodePath2();
/* 258 */     paramGraphics2D.setPaint(decodeGradient13(this.path));
/* 259 */     paramGraphics2D.fill(this.path);
/* 260 */     this.path = decodePath3();
/* 261 */     paramGraphics2D.setPaint(decodeGradient14(this.path));
/* 262 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndArrowShape(Graphics2D paramGraphics2D) {
/* 267 */     this.path = decodePath1();
/* 268 */     paramGraphics2D.setPaint(this.color24);
/* 269 */     paramGraphics2D.fill(this.path);
/* 270 */     this.path = decodePath2();
/* 271 */     paramGraphics2D.setPaint(decodeGradient15(this.path));
/* 272 */     paramGraphics2D.fill(this.path);
/* 273 */     this.path = decodePath3();
/* 274 */     paramGraphics2D.setPaint(decodeGradient16(this.path));
/* 275 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocusedAndArrowShape(Graphics2D paramGraphics2D) {
/* 280 */     this.path = decodePath4();
/* 281 */     paramGraphics2D.setPaint(this.color12);
/* 282 */     paramGraphics2D.fill(this.path);
/* 283 */     this.path = decodePath2();
/* 284 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 285 */     paramGraphics2D.fill(this.path);
/* 286 */     this.path = decodePath3();
/* 287 */     paramGraphics2D.setPaint(decodeGradient17(this.path));
/* 288 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocusedAndMouseOverAndArrowShape(Graphics2D paramGraphics2D) {
/* 293 */     this.path = decodePath4();
/* 294 */     paramGraphics2D.setPaint(this.color12);
/* 295 */     paramGraphics2D.fill(this.path);
/* 296 */     this.path = decodePath2();
/* 297 */     paramGraphics2D.setPaint(decodeGradient13(this.path));
/* 298 */     paramGraphics2D.fill(this.path);
/* 299 */     this.path = decodePath3();
/* 300 */     paramGraphics2D.setPaint(decodeGradient14(this.path));
/* 301 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocusedAndPressedAndArrowShape(Graphics2D paramGraphics2D) {
/* 306 */     this.path = decodePath4();
/* 307 */     paramGraphics2D.setPaint(this.color12);
/* 308 */     paramGraphics2D.fill(this.path);
/* 309 */     this.path = decodePath2();
/* 310 */     paramGraphics2D.setPaint(decodeGradient15(this.path));
/* 311 */     paramGraphics2D.fill(this.path);
/* 312 */     this.path = decodePath3();
/* 313 */     paramGraphics2D.setPaint(decodeGradient16(this.path));
/* 314 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ellipse2D decodeEllipse1() {
/* 321 */     this.ellipse.setFrame(decodeX(0.4F), 
/* 322 */         decodeY(0.4F), (
/* 323 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 324 */         decodeY(2.6F) - decodeY(0.4F)));
/* 325 */     return this.ellipse;
/*     */   }
/*     */   
/*     */   private Ellipse2D decodeEllipse2() {
/* 329 */     this.ellipse.setFrame(decodeX(0.6F), 
/* 330 */         decodeY(0.6F), (
/* 331 */         decodeX(2.4F) - decodeX(0.6F)), (
/* 332 */         decodeY(2.4F) - decodeY(0.6F)));
/* 333 */     return this.ellipse;
/*     */   }
/*     */   
/*     */   private Ellipse2D decodeEllipse3() {
/* 337 */     this.ellipse.setFrame(decodeX(0.4F), 
/* 338 */         decodeY(0.6F), (
/* 339 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 340 */         decodeY(2.8F) - decodeY(0.6F)));
/* 341 */     return this.ellipse;
/*     */   }
/*     */   
/*     */   private Ellipse2D decodeEllipse4() {
/* 345 */     this.ellipse.setFrame(decodeX(0.120000005F), 
/* 346 */         decodeY(0.120000005F), (
/* 347 */         decodeX(2.8799999F) - decodeX(0.120000005F)), (
/* 348 */         decodeY(2.8799999F) - decodeY(0.120000005F)));
/* 349 */     return this.ellipse;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 353 */     this.path.reset();
/* 354 */     this.path.moveTo(decodeX(0.8166667F), decodeY(0.5007576F));
/* 355 */     this.path.curveTo(decodeAnchorX(0.8166667F, 1.5643269F), decodeAnchorY(0.5007576F, -0.3097513F), decodeAnchorX(2.7925456F, 0.058173586F), decodeAnchorY(1.6116884F, -0.4647635F), decodeX(2.7925456F), decodeY(1.6116884F));
/* 356 */     this.path.curveTo(decodeAnchorX(2.7925456F, -0.34086856F), decodeAnchorY(1.6116884F, 2.7232852F), decodeAnchorX(0.7006364F, 4.568128F), decodeAnchorY(2.7693636F, -0.006014915F), decodeX(0.7006364F), decodeY(2.7693636F));
/* 357 */     this.path.curveTo(decodeAnchorX(0.7006364F, -3.5233955F), decodeAnchorY(2.7693636F, 0.004639302F), decodeAnchorX(0.8166667F, -1.8635255F), decodeAnchorY(0.5007576F, 0.36899543F), decodeX(0.8166667F), decodeY(0.5007576F));
/* 358 */     this.path.closePath();
/* 359 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 363 */     this.path.reset();
/* 364 */     this.path.moveTo(decodeX(0.6155303F), decodeY(2.5954547F));
/* 365 */     this.path.curveTo(decodeAnchorX(0.6155303F, 0.90980893F), decodeAnchorY(2.5954547F, 1.3154242F), decodeAnchorX(2.6151516F, 0.014588808F), decodeAnchorY(1.6112013F, 0.9295521F), decodeX(2.6151516F), decodeY(1.6112013F));
/* 366 */     this.path.curveTo(decodeAnchorX(2.6151516F, -0.01365518F), decodeAnchorY(1.6112013F, -0.8700643F), decodeAnchorX(0.60923916F, 0.9729935F), decodeAnchorY(0.40716404F, -1.4248644F), decodeX(0.60923916F), decodeY(0.40716404F));
/* 367 */     this.path.curveTo(decodeAnchorX(0.60923916F, -0.7485209F), decodeAnchorY(0.40716404F, 1.0961438F), decodeAnchorX(0.6155303F, -0.74998796F), decodeAnchorY(2.5954547F, -1.0843511F), decodeX(0.6155303F), decodeY(2.5954547F));
/* 368 */     this.path.closePath();
/* 369 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 373 */     this.path.reset();
/* 374 */     this.path.moveTo(decodeX(0.8055606F), decodeY(0.6009697F));
/* 375 */     this.path.curveTo(decodeAnchorX(0.8055606F, 0.50820893F), decodeAnchorY(0.6009697F, -0.8490881F), decodeAnchorX(2.3692727F, 0.0031846066F), decodeAnchorY(1.613117F, -0.60668826F), decodeX(2.3692727F), decodeY(1.613117F));
/* 376 */     this.path.curveTo(decodeAnchorX(2.3692727F, -0.003890196F), decodeAnchorY(1.613117F, 0.74110764F), decodeAnchorX(0.7945455F, 0.3870974F), decodeAnchorY(2.3932729F, 1.240782F), decodeX(0.7945455F), decodeY(2.3932729F));
/* 377 */     this.path.curveTo(decodeAnchorX(0.7945455F, -0.38636583F), decodeAnchorY(2.3932729F, -1.2384372F), decodeAnchorX(0.8055606F, -0.995154F), decodeAnchorY(0.6009697F, 1.6626496F), decodeX(0.8055606F), decodeY(0.6009697F));
/* 378 */     this.path.closePath();
/* 379 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 383 */     this.path.reset();
/* 384 */     this.path.moveTo(decodeX(0.60059524F), decodeY(0.11727543F));
/* 385 */     this.path.curveTo(decodeAnchorX(0.60059524F, 1.5643269F), decodeAnchorY(0.11727543F, -0.3097513F), decodeAnchorX(2.7925456F, 0.004405844F), decodeAnchorY(1.6116884F, -1.1881162F), decodeX(2.7925456F), decodeY(1.6116884F));
/* 386 */     this.path.curveTo(decodeAnchorX(2.7925456F, -0.007364541F), decodeAnchorY(1.6116884F, 1.9859827F), decodeAnchorX(0.7006364F, 2.7716863F), decodeAnchorY(2.8693638F, -0.008974582F), decodeX(0.7006364F), decodeY(2.8693638F));
/* 387 */     this.path.curveTo(decodeAnchorX(0.7006364F, -3.754899F), decodeAnchorY(2.8693638F, 0.012158176F), decodeAnchorX(0.60059524F, -1.8635255F), decodeAnchorY(0.11727543F, 0.36899543F), decodeX(0.60059524F), decodeY(0.11727543F));
/* 388 */     this.path.closePath();
/* 389 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 395 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 396 */     float f1 = (float)rectangle2D.getX();
/* 397 */     float f2 = (float)rectangle2D.getY();
/* 398 */     float f3 = (float)rectangle2D.getWidth();
/* 399 */     float f4 = (float)rectangle2D.getHeight();
/* 400 */     return decodeGradient(0.5106101F * f3 + f1, -4.553649E-18F * f4 + f2, 0.49933687F * f3 + f1, 1.0039787F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 403 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 408 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 409 */     float f1 = (float)rectangle2D.getX();
/* 410 */     float f2 = (float)rectangle2D.getY();
/* 411 */     float f3 = (float)rectangle2D.getWidth();
/* 412 */     float f4 = (float)rectangle2D.getHeight();
/* 413 */     return decodeGradient(0.5023511F * f3 + f1, 0.0015673981F * f4 + f2, 0.5023511F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.21256684F, 0.42513368F, 0.71256685F, 1.0F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 416 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 418 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 423 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 424 */     float f1 = (float)rectangle2D.getX();
/* 425 */     float f2 = (float)rectangle2D.getY();
/* 426 */     float f3 = (float)rectangle2D.getWidth();
/* 427 */     float f4 = (float)rectangle2D.getHeight();
/* 428 */     return decodeGradient(0.51F * f3 + f1, -4.553649E-18F * f4 + f2, 0.51F * f3 + f1, 1.0039787F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 431 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 436 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 437 */     float f1 = (float)rectangle2D.getX();
/* 438 */     float f2 = (float)rectangle2D.getY();
/* 439 */     float f3 = (float)rectangle2D.getWidth();
/* 440 */     float f4 = (float)rectangle2D.getHeight();
/* 441 */     return decodeGradient(0.5F * f3 + f1, 0.0015673981F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.21256684F, 0.42513368F, 0.56149733F, 0.69786096F, 0.8489305F, 1.0F }, new Color[] { this.color9, 
/*     */ 
/*     */           
/* 444 */           decodeColor(this.color9, this.color10, 0.5F), this.color10, 
/*     */           
/* 446 */           decodeColor(this.color10, this.color10, 0.5F), this.color10, 
/*     */           
/* 448 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 453 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 454 */     float f1 = (float)rectangle2D.getX();
/* 455 */     float f2 = (float)rectangle2D.getY();
/* 456 */     float f3 = (float)rectangle2D.getWidth();
/* 457 */     float f4 = (float)rectangle2D.getHeight();
/* 458 */     return decodeGradient(0.5106101F * f3 + f1, -4.553649E-18F * f4 + f2, 0.49933687F * f3 + f1, 1.0039787F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color13, 
/*     */ 
/*     */           
/* 461 */           decodeColor(this.color13, this.color14, 0.5F), this.color14 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 466 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 467 */     float f1 = (float)rectangle2D.getX();
/* 468 */     float f2 = (float)rectangle2D.getY();
/* 469 */     float f3 = (float)rectangle2D.getWidth();
/* 470 */     float f4 = (float)rectangle2D.getHeight();
/* 471 */     return decodeGradient(0.5023511F * f3 + f1, 0.0015673981F * f4 + f2, 0.5023511F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.21256684F, 0.42513368F, 0.56149733F, 0.69786096F, 0.8489305F, 1.0F }, new Color[] { this.color15, 
/*     */ 
/*     */           
/* 474 */           decodeColor(this.color15, this.color16, 0.5F), this.color16, 
/*     */           
/* 476 */           decodeColor(this.color16, this.color17, 0.5F), this.color17, 
/*     */           
/* 478 */           decodeColor(this.color17, this.color18, 0.5F), this.color18 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 483 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 484 */     float f1 = (float)rectangle2D.getX();
/* 485 */     float f2 = (float)rectangle2D.getY();
/* 486 */     float f3 = (float)rectangle2D.getWidth();
/* 487 */     float f4 = (float)rectangle2D.getHeight();
/* 488 */     return decodeGradient(0.5106101F * f3 + f1, -4.553649E-18F * f4 + f2, 0.49933687F * f3 + f1, 1.0039787F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color14, 
/*     */ 
/*     */           
/* 491 */           decodeColor(this.color14, this.color19, 0.5F), this.color19 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 496 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 497 */     float f1 = (float)rectangle2D.getX();
/* 498 */     float f2 = (float)rectangle2D.getY();
/* 499 */     float f3 = (float)rectangle2D.getWidth();
/* 500 */     float f4 = (float)rectangle2D.getHeight();
/* 501 */     return decodeGradient(0.5023511F * f3 + f1, 0.0015673981F * f4 + f2, 0.5023511F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.23796791F, 0.47593582F, 0.5360962F, 0.5962567F, 0.79812837F, 1.0F }, new Color[] { this.color20, 
/*     */ 
/*     */           
/* 504 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 506 */           decodeColor(this.color21, this.color21, 0.5F), this.color21, 
/*     */           
/* 508 */           decodeColor(this.color21, this.color22, 0.5F), this.color22 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 513 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 514 */     float f1 = (float)rectangle2D.getX();
/* 515 */     float f2 = (float)rectangle2D.getY();
/* 516 */     float f3 = (float)rectangle2D.getWidth();
/* 517 */     float f4 = (float)rectangle2D.getHeight();
/* 518 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.24032257F, 0.48064515F, 0.7403226F, 1.0F }, new Color[] { this.color25, 
/*     */ 
/*     */           
/* 521 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*     */           
/* 523 */           decodeColor(this.color26, this.color27, 0.5F), this.color27 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 528 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 529 */     float f1 = (float)rectangle2D.getX();
/* 530 */     float f2 = (float)rectangle2D.getY();
/* 531 */     float f3 = (float)rectangle2D.getWidth();
/* 532 */     float f4 = (float)rectangle2D.getHeight();
/* 533 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.061290324F, 0.1016129F, 0.14193548F, 0.3016129F, 0.46129033F, 0.5983871F, 0.7354839F, 0.7935484F, 0.8516129F }, new Color[] { this.color28, 
/*     */ 
/*     */           
/* 536 */           decodeColor(this.color28, this.color29, 0.5F), this.color29, 
/*     */           
/* 538 */           decodeColor(this.color29, this.color30, 0.5F), this.color30, 
/*     */           
/* 540 */           decodeColor(this.color30, this.color31, 0.5F), this.color31, 
/*     */           
/* 542 */           decodeColor(this.color31, this.color32, 0.5F), this.color32 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient11(Shape paramShape) {
/* 547 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 548 */     float f1 = (float)rectangle2D.getX();
/* 549 */     float f2 = (float)rectangle2D.getY();
/* 550 */     float f3 = (float)rectangle2D.getWidth();
/* 551 */     float f4 = (float)rectangle2D.getHeight();
/* 552 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 555 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient12(Shape paramShape) {
/* 560 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 561 */     float f1 = (float)rectangle2D.getX();
/* 562 */     float f2 = (float)rectangle2D.getY();
/* 563 */     float f3 = (float)rectangle2D.getWidth();
/* 564 */     float f4 = (float)rectangle2D.getHeight();
/* 565 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.21256684F, 0.42513368F, 0.71256685F, 1.0F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 568 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 570 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient13(Shape paramShape) {
/* 575 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 576 */     float f1 = (float)rectangle2D.getX();
/* 577 */     float f2 = (float)rectangle2D.getY();
/* 578 */     float f3 = (float)rectangle2D.getWidth();
/* 579 */     float f4 = (float)rectangle2D.getHeight();
/* 580 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color13, 
/*     */ 
/*     */           
/* 583 */           decodeColor(this.color13, this.color14, 0.5F), this.color14 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient14(Shape paramShape) {
/* 588 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 589 */     float f1 = (float)rectangle2D.getX();
/* 590 */     float f2 = (float)rectangle2D.getY();
/* 591 */     float f3 = (float)rectangle2D.getWidth();
/* 592 */     float f4 = (float)rectangle2D.getHeight();
/* 593 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.21256684F, 0.42513368F, 0.56149733F, 0.69786096F, 0.8489305F, 1.0F }, new Color[] { this.color15, 
/*     */ 
/*     */           
/* 596 */           decodeColor(this.color15, this.color16, 0.5F), this.color16, 
/*     */           
/* 598 */           decodeColor(this.color16, this.color17, 0.5F), this.color17, 
/*     */           
/* 600 */           decodeColor(this.color17, this.color18, 0.5F), this.color18 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient15(Shape paramShape) {
/* 605 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 606 */     float f1 = (float)rectangle2D.getX();
/* 607 */     float f2 = (float)rectangle2D.getY();
/* 608 */     float f3 = (float)rectangle2D.getWidth();
/* 609 */     float f4 = (float)rectangle2D.getHeight();
/* 610 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color14, 
/*     */ 
/*     */           
/* 613 */           decodeColor(this.color14, this.color19, 0.5F), this.color19 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient16(Shape paramShape) {
/* 618 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 619 */     float f1 = (float)rectangle2D.getX();
/* 620 */     float f2 = (float)rectangle2D.getY();
/* 621 */     float f3 = (float)rectangle2D.getWidth();
/* 622 */     float f4 = (float)rectangle2D.getHeight();
/* 623 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.23796791F, 0.47593582F, 0.5360962F, 0.5962567F, 0.79812837F, 1.0F }, new Color[] { this.color20, 
/*     */ 
/*     */           
/* 626 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 628 */           decodeColor(this.color21, this.color21, 0.5F), this.color21, 
/*     */           
/* 630 */           decodeColor(this.color21, this.color22, 0.5F), this.color22 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient17(Shape paramShape) {
/* 635 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 636 */     float f1 = (float)rectangle2D.getX();
/* 637 */     float f2 = (float)rectangle2D.getY();
/* 638 */     float f3 = (float)rectangle2D.getWidth();
/* 639 */     float f4 = (float)rectangle2D.getHeight();
/* 640 */     return decodeGradient(0.4925773F * f3 + f1, 0.082019866F * f4 + f2, 0.4925773F * f3 + f1, 0.91798013F * f4 + f2, new float[] { 0.061290324F, 0.1016129F, 0.14193548F, 0.3016129F, 0.46129033F, 0.5983871F, 0.7354839F, 0.7935484F, 0.8516129F }, new Color[] { this.color28, 
/*     */ 
/*     */           
/* 643 */           decodeColor(this.color28, this.color29, 0.5F), this.color29, 
/*     */           
/* 645 */           decodeColor(this.color29, this.color30, 0.5F), this.color30, 
/*     */           
/* 647 */           decodeColor(this.color30, this.color31, 0.5F), this.color31, 
/*     */           
/* 649 */           decodeColor(this.color31, this.color32, 0.5F), this.color32 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/SliderThumbPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */