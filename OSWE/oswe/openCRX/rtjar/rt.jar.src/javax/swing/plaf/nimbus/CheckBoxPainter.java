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
/*     */ final class CheckBoxPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int ICON_DISABLED = 3;
/*     */   static final int ICON_ENABLED = 4;
/*     */   static final int ICON_FOCUSED = 5;
/*     */   static final int ICON_MOUSEOVER = 6;
/*     */   static final int ICON_MOUSEOVER_FOCUSED = 7;
/*     */   static final int ICON_PRESSED = 8;
/*     */   static final int ICON_PRESSED_FOCUSED = 9;
/*     */   static final int ICON_SELECTED = 10;
/*     */   static final int ICON_SELECTED_FOCUSED = 11;
/*     */   static final int ICON_PRESSED_SELECTED = 12;
/*     */   static final int ICON_PRESSED_SELECTED_FOCUSED = 13;
/*     */   static final int ICON_MOUSEOVER_SELECTED = 14;
/*     */   static final int ICON_MOUSEOVER_SELECTED_FOCUSED = 15;
/*     */   static final int ICON_DISABLED_SELECTED = 16;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  61 */   private Path2D path = new Path2D.Float();
/*  62 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  63 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  64 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private Color color1 = decodeColor("nimbusBlueGrey", 0.0F, -0.06766917F, 0.07843137F, 0);
/*  70 */   private Color color2 = decodeColor("nimbusBlueGrey", 0.0F, -0.06484103F, 0.027450979F, 0);
/*  71 */   private Color color3 = decodeColor("nimbusBase", 0.032459438F, -0.60996324F, 0.36470586F, 0);
/*  72 */   private Color color4 = decodeColor("nimbusBase", 0.02551502F, -0.5996783F, 0.3215686F, 0);
/*  73 */   private Color color5 = decodeColor("nimbusBase", 0.032459438F, -0.59624064F, 0.34509802F, 0);
/*  74 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.0F, 0.0F, 0.0F, -89);
/*  75 */   private Color color7 = decodeColor("nimbusBlueGrey", 0.0F, -0.05356429F, -0.12549019F, 0);
/*  76 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.0F, -0.015789472F, -0.37254903F, 0);
/*  77 */   private Color color9 = decodeColor("nimbusBase", 0.08801502F, -0.63174605F, 0.43921566F, 0);
/*  78 */   private Color color10 = decodeColor("nimbusBase", 0.032459438F, -0.5953556F, 0.32549018F, 0);
/*  79 */   private Color color11 = decodeColor("nimbusBase", 0.032459438F, -0.59942394F, 0.4235294F, 0);
/*  80 */   private Color color12 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  81 */   private Color color13 = decodeColor("nimbusBlueGrey", 0.0F, -0.020974077F, -0.21960783F, 0);
/*  82 */   private Color color14 = decodeColor("nimbusBlueGrey", 0.01010108F, 0.08947369F, -0.5294118F, 0);
/*  83 */   private Color color15 = decodeColor("nimbusBase", 0.08801502F, -0.6317773F, 0.4470588F, 0);
/*  84 */   private Color color16 = decodeColor("nimbusBase", 0.032459438F, -0.5985242F, 0.39999998F, 0);
/*  85 */   private Color color17 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*  86 */   private Color color18 = decodeColor("nimbusBlueGrey", 0.055555582F, 0.8894737F, -0.7176471F, 0);
/*  87 */   private Color color19 = decodeColor("nimbusBlueGrey", 0.0F, 0.0016232133F, -0.3254902F, 0);
/*  88 */   private Color color20 = decodeColor("nimbusBase", 0.027408898F, -0.5847884F, 0.2980392F, 0);
/*  89 */   private Color color21 = decodeColor("nimbusBase", 0.029681683F, -0.52701867F, 0.17254901F, 0);
/*  90 */   private Color color22 = decodeColor("nimbusBase", 0.029681683F, -0.5376751F, 0.25098038F, 0);
/*  91 */   private Color color23 = decodeColor("nimbusBase", 5.1498413E-4F, -0.34585923F, -0.007843137F, 0);
/*  92 */   private Color color24 = decodeColor("nimbusBase", 5.1498413E-4F, -0.10238093F, -0.25490198F, 0);
/*  93 */   private Color color25 = decodeColor("nimbusBase", 0.004681647F, -0.6197143F, 0.43137252F, 0);
/*  94 */   private Color color26 = decodeColor("nimbusBase", 5.1498413E-4F, -0.44153953F, 0.2588235F, 0);
/*  95 */   private Color color27 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4602757F, 0.34509802F, 0);
/*  96 */   private Color color28 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  97 */   private Color color29 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  98 */   private Color color30 = decodeColor("nimbusBase", -3.528595E-5F, 0.026785731F, -0.23529413F, 0);
/*  99 */   private Color color31 = decodeColor("nimbusBase", -4.2033195E-4F, -0.38050595F, 0.20392156F, 0);
/* 100 */   private Color color32 = decodeColor("nimbusBase", -0.0021489263F, -0.2891234F, 0.14117646F, 0);
/* 101 */   private Color color33 = decodeColor("nimbusBase", -0.006362498F, -0.016311288F, -0.02352941F, 0);
/* 102 */   private Color color34 = decodeColor("nimbusBase", 0.0F, -0.17930403F, 0.21568626F, 0);
/* 103 */   private Color color35 = decodeColor("nimbusBase", 0.0013483167F, -0.1769987F, -0.12156865F, 0);
/* 104 */   private Color color36 = decodeColor("nimbusBase", 0.05468172F, 0.3642857F, -0.43137258F, 0);
/* 105 */   private Color color37 = decodeColor("nimbusBase", 0.004681647F, -0.6198413F, 0.43921566F, 0);
/* 106 */   private Color color38 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4555341F, 0.3215686F, 0);
/* 107 */   private Color color39 = decodeColor("nimbusBase", 5.1498413E-4F, -0.47377098F, 0.41960782F, 0);
/* 108 */   private Color color40 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.03771078F, 0.062745094F, 0);
/* 109 */   private Color color41 = decodeColor("nimbusBlueGrey", -0.02222222F, -0.032806106F, 0.011764705F, 0);
/* 110 */   private Color color42 = decodeColor("nimbusBase", 0.021348298F, -0.59223604F, 0.35294116F, 0);
/* 111 */   private Color color43 = decodeColor("nimbusBase", 0.021348298F, -0.56722116F, 0.3098039F, 0);
/* 112 */   private Color color44 = decodeColor("nimbusBase", 0.021348298F, -0.56875F, 0.32941175F, 0);
/* 113 */   private Color color45 = decodeColor("nimbusBase", 0.027408898F, -0.5735674F, 0.14509803F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckBoxPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 121 */     this.state = paramInt;
/* 122 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 128 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 131 */     switch (this.state) { case 3:
/* 132 */         painticonDisabled(paramGraphics2D); break;
/* 133 */       case 4: painticonEnabled(paramGraphics2D); break;
/* 134 */       case 5: painticonFocused(paramGraphics2D); break;
/* 135 */       case 6: painticonMouseOver(paramGraphics2D); break;
/* 136 */       case 7: painticonMouseOverAndFocused(paramGraphics2D); break;
/* 137 */       case 8: painticonPressed(paramGraphics2D); break;
/* 138 */       case 9: painticonPressedAndFocused(paramGraphics2D); break;
/* 139 */       case 10: painticonSelected(paramGraphics2D); break;
/* 140 */       case 11: painticonSelectedAndFocused(paramGraphics2D); break;
/* 141 */       case 12: painticonPressedAndSelected(paramGraphics2D); break;
/* 142 */       case 13: painticonPressedAndSelectedAndFocused(paramGraphics2D); break;
/* 143 */       case 14: painticonMouseOverAndSelected(paramGraphics2D); break;
/* 144 */       case 15: painticonMouseOverAndSelectedAndFocused(paramGraphics2D); break;
/* 145 */       case 16: painticonDisabledAndSelected(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 154 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void painticonDisabled(Graphics2D paramGraphics2D) {
/* 158 */     this.roundRect = decodeRoundRect1();
/* 159 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 160 */     paramGraphics2D.fill(this.roundRect);
/* 161 */     this.roundRect = decodeRoundRect2();
/* 162 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 163 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonEnabled(Graphics2D paramGraphics2D) {
/* 168 */     this.roundRect = decodeRoundRect3();
/* 169 */     paramGraphics2D.setPaint(this.color6);
/* 170 */     paramGraphics2D.fill(this.roundRect);
/* 171 */     this.roundRect = decodeRoundRect1();
/* 172 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 173 */     paramGraphics2D.fill(this.roundRect);
/* 174 */     this.roundRect = decodeRoundRect2();
/* 175 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 176 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonFocused(Graphics2D paramGraphics2D) {
/* 181 */     this.roundRect = decodeRoundRect4();
/* 182 */     paramGraphics2D.setPaint(this.color12);
/* 183 */     paramGraphics2D.fill(this.roundRect);
/* 184 */     this.roundRect = decodeRoundRect1();
/* 185 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 186 */     paramGraphics2D.fill(this.roundRect);
/* 187 */     this.roundRect = decodeRoundRect2();
/* 188 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 189 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonMouseOver(Graphics2D paramGraphics2D) {
/* 194 */     this.roundRect = decodeRoundRect3();
/* 195 */     paramGraphics2D.setPaint(this.color6);
/* 196 */     paramGraphics2D.fill(this.roundRect);
/* 197 */     this.roundRect = decodeRoundRect1();
/* 198 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 199 */     paramGraphics2D.fill(this.roundRect);
/* 200 */     this.roundRect = decodeRoundRect2();
/* 201 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 202 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonMouseOverAndFocused(Graphics2D paramGraphics2D) {
/* 207 */     this.roundRect = decodeRoundRect4();
/* 208 */     paramGraphics2D.setPaint(this.color12);
/* 209 */     paramGraphics2D.fill(this.roundRect);
/* 210 */     this.roundRect = decodeRoundRect1();
/* 211 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 212 */     paramGraphics2D.fill(this.roundRect);
/* 213 */     this.roundRect = decodeRoundRect2();
/* 214 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 215 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonPressed(Graphics2D paramGraphics2D) {
/* 220 */     this.roundRect = decodeRoundRect3();
/* 221 */     paramGraphics2D.setPaint(this.color6);
/* 222 */     paramGraphics2D.fill(this.roundRect);
/* 223 */     this.roundRect = decodeRoundRect1();
/* 224 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 225 */     paramGraphics2D.fill(this.roundRect);
/* 226 */     this.roundRect = decodeRoundRect2();
/* 227 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 228 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonPressedAndFocused(Graphics2D paramGraphics2D) {
/* 233 */     this.roundRect = decodeRoundRect4();
/* 234 */     paramGraphics2D.setPaint(this.color12);
/* 235 */     paramGraphics2D.fill(this.roundRect);
/* 236 */     this.roundRect = decodeRoundRect1();
/* 237 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 238 */     paramGraphics2D.fill(this.roundRect);
/* 239 */     this.roundRect = decodeRoundRect2();
/* 240 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 241 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonSelected(Graphics2D paramGraphics2D) {
/* 246 */     this.roundRect = decodeRoundRect3();
/* 247 */     paramGraphics2D.setPaint(this.color6);
/* 248 */     paramGraphics2D.fill(this.roundRect);
/* 249 */     this.roundRect = decodeRoundRect1();
/* 250 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 251 */     paramGraphics2D.fill(this.roundRect);
/* 252 */     this.roundRect = decodeRoundRect2();
/* 253 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 254 */     paramGraphics2D.fill(this.roundRect);
/* 255 */     this.path = decodePath1();
/* 256 */     paramGraphics2D.setPaint(this.color28);
/* 257 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonSelectedAndFocused(Graphics2D paramGraphics2D) {
/* 262 */     this.roundRect = decodeRoundRect4();
/* 263 */     paramGraphics2D.setPaint(this.color12);
/* 264 */     paramGraphics2D.fill(this.roundRect);
/* 265 */     this.roundRect = decodeRoundRect1();
/* 266 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 267 */     paramGraphics2D.fill(this.roundRect);
/* 268 */     this.roundRect = decodeRoundRect2();
/* 269 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 270 */     paramGraphics2D.fill(this.roundRect);
/* 271 */     this.path = decodePath1();
/* 272 */     paramGraphics2D.setPaint(this.color28);
/* 273 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonPressedAndSelected(Graphics2D paramGraphics2D) {
/* 278 */     this.roundRect = decodeRoundRect3();
/* 279 */     paramGraphics2D.setPaint(this.color29);
/* 280 */     paramGraphics2D.fill(this.roundRect);
/* 281 */     this.roundRect = decodeRoundRect1();
/* 282 */     paramGraphics2D.setPaint(decodeGradient11(this.roundRect));
/* 283 */     paramGraphics2D.fill(this.roundRect);
/* 284 */     this.roundRect = decodeRoundRect2();
/* 285 */     paramGraphics2D.setPaint(decodeGradient12(this.roundRect));
/* 286 */     paramGraphics2D.fill(this.roundRect);
/* 287 */     this.path = decodePath1();
/* 288 */     paramGraphics2D.setPaint(this.color28);
/* 289 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonPressedAndSelectedAndFocused(Graphics2D paramGraphics2D) {
/* 294 */     this.roundRect = decodeRoundRect4();
/* 295 */     paramGraphics2D.setPaint(this.color12);
/* 296 */     paramGraphics2D.fill(this.roundRect);
/* 297 */     this.roundRect = decodeRoundRect1();
/* 298 */     paramGraphics2D.setPaint(decodeGradient11(this.roundRect));
/* 299 */     paramGraphics2D.fill(this.roundRect);
/* 300 */     this.roundRect = decodeRoundRect2();
/* 301 */     paramGraphics2D.setPaint(decodeGradient12(this.roundRect));
/* 302 */     paramGraphics2D.fill(this.roundRect);
/* 303 */     this.path = decodePath1();
/* 304 */     paramGraphics2D.setPaint(this.color28);
/* 305 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonMouseOverAndSelected(Graphics2D paramGraphics2D) {
/* 310 */     this.roundRect = decodeRoundRect3();
/* 311 */     paramGraphics2D.setPaint(this.color6);
/* 312 */     paramGraphics2D.fill(this.roundRect);
/* 313 */     this.roundRect = decodeRoundRect1();
/* 314 */     paramGraphics2D.setPaint(decodeGradient13(this.roundRect));
/* 315 */     paramGraphics2D.fill(this.roundRect);
/* 316 */     this.roundRect = decodeRoundRect2();
/* 317 */     paramGraphics2D.setPaint(decodeGradient14(this.roundRect));
/* 318 */     paramGraphics2D.fill(this.roundRect);
/* 319 */     this.path = decodePath1();
/* 320 */     paramGraphics2D.setPaint(this.color28);
/* 321 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonMouseOverAndSelectedAndFocused(Graphics2D paramGraphics2D) {
/* 326 */     this.roundRect = decodeRoundRect4();
/* 327 */     paramGraphics2D.setPaint(this.color12);
/* 328 */     paramGraphics2D.fill(this.roundRect);
/* 329 */     this.roundRect = decodeRoundRect1();
/* 330 */     paramGraphics2D.setPaint(decodeGradient13(this.roundRect));
/* 331 */     paramGraphics2D.fill(this.roundRect);
/* 332 */     this.roundRect = decodeRoundRect2();
/* 333 */     paramGraphics2D.setPaint(decodeGradient14(this.roundRect));
/* 334 */     paramGraphics2D.fill(this.roundRect);
/* 335 */     this.path = decodePath1();
/* 336 */     paramGraphics2D.setPaint(this.color28);
/* 337 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void painticonDisabledAndSelected(Graphics2D paramGraphics2D) {
/* 342 */     this.roundRect = decodeRoundRect1();
/* 343 */     paramGraphics2D.setPaint(decodeGradient15(this.roundRect));
/* 344 */     paramGraphics2D.fill(this.roundRect);
/* 345 */     this.roundRect = decodeRoundRect2();
/* 346 */     paramGraphics2D.setPaint(decodeGradient16(this.roundRect));
/* 347 */     paramGraphics2D.fill(this.roundRect);
/* 348 */     this.path = decodePath1();
/* 349 */     paramGraphics2D.setPaint(this.color45);
/* 350 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 357 */     this.roundRect.setRoundRect(decodeX(0.4F), 
/* 358 */         decodeY(0.4F), (
/* 359 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 360 */         decodeY(2.6F) - decodeY(0.4F)), 3.7058823108673096D, 3.7058823108673096D);
/*     */     
/* 362 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 366 */     this.roundRect.setRoundRect(decodeX(0.6F), 
/* 367 */         decodeY(0.6F), (
/* 368 */         decodeX(2.4F) - decodeX(0.6F)), (
/* 369 */         decodeY(2.4F) - decodeY(0.6F)), 3.7647058963775635D, 3.7647058963775635D);
/*     */     
/* 371 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect3() {
/* 375 */     this.roundRect.setRoundRect(decodeX(0.4F), 
/* 376 */         decodeY(1.75F), (
/* 377 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 378 */         decodeY(2.8F) - decodeY(1.75F)), 5.176470756530762D, 5.176470756530762D);
/*     */     
/* 380 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect4() {
/* 384 */     this.roundRect.setRoundRect(decodeX(0.120000005F), 
/* 385 */         decodeY(0.120000005F), (
/* 386 */         decodeX(2.8799999F) - decodeX(0.120000005F)), (
/* 387 */         decodeY(2.8799999F) - decodeY(0.120000005F)), 8.0D, 8.0D);
/*     */     
/* 389 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 393 */     this.path.reset();
/* 394 */     this.path.moveTo(decodeX(1.0036764F), decodeY(1.382353F));
/* 395 */     this.path.lineTo(decodeX(1.2536764F), decodeY(1.382353F));
/* 396 */     this.path.lineTo(decodeX(1.430147F), decodeY(1.757353F));
/* 397 */     this.path.lineTo(decodeX(1.8235294F), decodeY(0.62352943F));
/* 398 */     this.path.lineTo(decodeX(2.2F), decodeY(0.61764705F));
/* 399 */     this.path.lineTo(decodeX(1.492647F), decodeY(2.0058823F));
/* 400 */     this.path.lineTo(decodeX(1.382353F), decodeY(2.0058823F));
/* 401 */     this.path.lineTo(decodeX(1.0036764F), decodeY(1.382353F));
/* 402 */     this.path.closePath();
/* 403 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 409 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 410 */     float f1 = (float)rectangle2D.getX();
/* 411 */     float f2 = (float)rectangle2D.getY();
/* 412 */     float f3 = (float)rectangle2D.getWidth();
/* 413 */     float f4 = (float)rectangle2D.getHeight();
/* 414 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 417 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 422 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 423 */     float f1 = (float)rectangle2D.getX();
/* 424 */     float f2 = (float)rectangle2D.getY();
/* 425 */     float f3 = (float)rectangle2D.getWidth();
/* 426 */     float f4 = (float)rectangle2D.getHeight();
/* 427 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.32228917F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 430 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 432 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 437 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 438 */     float f1 = (float)rectangle2D.getX();
/* 439 */     float f2 = (float)rectangle2D.getY();
/* 440 */     float f3 = (float)rectangle2D.getWidth();
/* 441 */     float f4 = (float)rectangle2D.getHeight();
/* 442 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 445 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 450 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 451 */     float f1 = (float)rectangle2D.getX();
/* 452 */     float f2 = (float)rectangle2D.getY();
/* 453 */     float f3 = (float)rectangle2D.getWidth();
/* 454 */     float f4 = (float)rectangle2D.getHeight();
/* 455 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.32228917F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color9, 
/*     */ 
/*     */           
/* 458 */           decodeColor(this.color9, this.color10, 0.5F), this.color10, 
/*     */           
/* 460 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 465 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 466 */     float f1 = (float)rectangle2D.getX();
/* 467 */     float f2 = (float)rectangle2D.getY();
/* 468 */     float f3 = (float)rectangle2D.getWidth();
/* 469 */     float f4 = (float)rectangle2D.getHeight();
/* 470 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color13, 
/*     */ 
/*     */           
/* 473 */           decodeColor(this.color13, this.color14, 0.5F), this.color14 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 478 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 479 */     float f1 = (float)rectangle2D.getX();
/* 480 */     float f2 = (float)rectangle2D.getY();
/* 481 */     float f3 = (float)rectangle2D.getWidth();
/* 482 */     float f4 = (float)rectangle2D.getHeight();
/* 483 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.32228917F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color15, 
/*     */ 
/*     */           
/* 486 */           decodeColor(this.color15, this.color16, 0.5F), this.color16, 
/*     */           
/* 488 */           decodeColor(this.color16, this.color17, 0.5F), this.color17 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 493 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 494 */     float f1 = (float)rectangle2D.getX();
/* 495 */     float f2 = (float)rectangle2D.getY();
/* 496 */     float f3 = (float)rectangle2D.getWidth();
/* 497 */     float f4 = (float)rectangle2D.getHeight();
/* 498 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color18, 
/*     */ 
/*     */           
/* 501 */           decodeColor(this.color18, this.color19, 0.5F), this.color19 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 506 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 507 */     float f1 = (float)rectangle2D.getX();
/* 508 */     float f2 = (float)rectangle2D.getY();
/* 509 */     float f3 = (float)rectangle2D.getWidth();
/* 510 */     float f4 = (float)rectangle2D.getHeight();
/* 511 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.32228917F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color20, 
/*     */ 
/*     */           
/* 514 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 516 */           decodeColor(this.color21, this.color22, 0.5F), this.color22 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 521 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 522 */     float f1 = (float)rectangle2D.getX();
/* 523 */     float f2 = (float)rectangle2D.getY();
/* 524 */     float f3 = (float)rectangle2D.getWidth();
/* 525 */     float f4 = (float)rectangle2D.getHeight();
/* 526 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color23, 
/*     */ 
/*     */           
/* 529 */           decodeColor(this.color23, this.color24, 0.5F), this.color24 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 534 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 535 */     float f1 = (float)rectangle2D.getX();
/* 536 */     float f2 = (float)rectangle2D.getY();
/* 537 */     float f3 = (float)rectangle2D.getWidth();
/* 538 */     float f4 = (float)rectangle2D.getHeight();
/* 539 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.32228917F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color25, 
/*     */ 
/*     */           
/* 542 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*     */           
/* 544 */           decodeColor(this.color26, this.color27, 0.5F), this.color27 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient11(Shape paramShape) {
/* 549 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 550 */     float f1 = (float)rectangle2D.getX();
/* 551 */     float f2 = (float)rectangle2D.getY();
/* 552 */     float f3 = (float)rectangle2D.getWidth();
/* 553 */     float f4 = (float)rectangle2D.getHeight();
/* 554 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color28, 
/*     */ 
/*     */           
/* 557 */           decodeColor(this.color28, this.color30, 0.5F), this.color30 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient12(Shape paramShape) {
/* 562 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 563 */     float f1 = (float)rectangle2D.getX();
/* 564 */     float f2 = (float)rectangle2D.getY();
/* 565 */     float f3 = (float)rectangle2D.getWidth();
/* 566 */     float f4 = (float)rectangle2D.getHeight();
/* 567 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.05775076F, 0.11550152F, 0.38003993F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color31, 
/*     */ 
/*     */           
/* 570 */           decodeColor(this.color31, this.color32, 0.5F), this.color32, 
/*     */           
/* 572 */           decodeColor(this.color32, this.color33, 0.5F), this.color33, 
/*     */           
/* 574 */           decodeColor(this.color33, this.color34, 0.5F), this.color34 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient13(Shape paramShape) {
/* 579 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 580 */     float f1 = (float)rectangle2D.getX();
/* 581 */     float f2 = (float)rectangle2D.getY();
/* 582 */     float f3 = (float)rectangle2D.getWidth();
/* 583 */     float f4 = (float)rectangle2D.getHeight();
/* 584 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color35, 
/*     */ 
/*     */           
/* 587 */           decodeColor(this.color35, this.color36, 0.5F), this.color36 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient14(Shape paramShape) {
/* 592 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 593 */     float f1 = (float)rectangle2D.getX();
/* 594 */     float f2 = (float)rectangle2D.getY();
/* 595 */     float f3 = (float)rectangle2D.getWidth();
/* 596 */     float f4 = (float)rectangle2D.getHeight();
/* 597 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.32228917F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color37, 
/*     */ 
/*     */           
/* 600 */           decodeColor(this.color37, this.color38, 0.5F), this.color38, 
/*     */           
/* 602 */           decodeColor(this.color38, this.color39, 0.5F), this.color39 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient15(Shape paramShape) {
/* 607 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 608 */     float f1 = (float)rectangle2D.getX();
/* 609 */     float f2 = (float)rectangle2D.getY();
/* 610 */     float f3 = (float)rectangle2D.getWidth();
/* 611 */     float f4 = (float)rectangle2D.getHeight();
/* 612 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25210086F * f3 + f1, 0.9957983F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color40, 
/*     */ 
/*     */           
/* 615 */           decodeColor(this.color40, this.color41, 0.5F), this.color41 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient16(Shape paramShape) {
/* 620 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 621 */     float f1 = (float)rectangle2D.getX();
/* 622 */     float f2 = (float)rectangle2D.getY();
/* 623 */     float f3 = (float)rectangle2D.getWidth();
/* 624 */     float f4 = (float)rectangle2D.getHeight();
/* 625 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.997549F * f4 + f2, new float[] { 0.0F, 0.32228917F, 0.64457834F, 0.82228917F, 1.0F }, new Color[] { this.color42, 
/*     */ 
/*     */           
/* 628 */           decodeColor(this.color42, this.color43, 0.5F), this.color43, 
/*     */           
/* 630 */           decodeColor(this.color43, this.color44, 0.5F), this.color44 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/CheckBoxPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */