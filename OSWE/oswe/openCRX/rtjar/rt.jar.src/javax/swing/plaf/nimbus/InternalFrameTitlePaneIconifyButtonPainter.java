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
/*     */ final class InternalFrameTitlePaneIconifyButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_ENABLED = 1;
/*     */   static final int BACKGROUND_DISABLED = 2;
/*     */   static final int BACKGROUND_MOUSEOVER = 3;
/*     */   static final int BACKGROUND_PRESSED = 4;
/*     */   static final int BACKGROUND_ENABLED_WINDOWNOTFOCUSED = 5;
/*     */   static final int BACKGROUND_MOUSEOVER_WINDOWNOTFOCUSED = 6;
/*     */   static final int BACKGROUND_PRESSED_WINDOWNOTFOCUSED = 7;
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
/*  61 */   private Color color2 = decodeColor("nimbusOrange", -0.08377897F, 0.02094239F, -0.40392157F, 0);
/*  62 */   private Color color3 = decodeColor("nimbusOrange", 0.0F, 0.0F, 0.0F, 0);
/*  63 */   private Color color4 = decodeColor("nimbusOrange", -4.4563413E-4F, -0.48364475F, 0.10588235F, 0);
/*  64 */   private Color color5 = decodeColor("nimbusOrange", 0.0F, -0.0050992966F, 0.0039215684F, 0);
/*  65 */   private Color color6 = decodeColor("nimbusOrange", 0.0F, -0.12125945F, 0.10588235F, 0);
/*  66 */   private Color color7 = decodeColor("nimbusOrange", -0.08377897F, 0.02094239F, -0.40392157F, -106);
/*  67 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  68 */   private Color color9 = decodeColor("nimbusOrange", 0.5203877F, -0.9376068F, 0.007843137F, 0);
/*  69 */   private Color color10 = decodeColor("nimbusOrange", 0.5273321F, -0.8903002F, -0.086274505F, 0);
/*  70 */   private Color color11 = decodeColor("nimbusOrange", 0.5273321F, -0.93313926F, 0.019607842F, 0);
/*  71 */   private Color color12 = decodeColor("nimbusOrange", 0.53526866F, -0.8995122F, -0.058823526F, 0);
/*  72 */   private Color color13 = decodeColor("nimbusOrange", 0.5233639F, -0.8971863F, -0.07843137F, 0);
/*  73 */   private Color color14 = decodeColor("nimbusBlueGrey", -0.0808081F, 0.015910469F, -0.40392157F, -216);
/*  74 */   private Color color15 = decodeColor("nimbusBlueGrey", -0.003968239F, -0.03760965F, 0.007843137F, 0);
/*  75 */   private Color color16 = new Color(255, 200, 0, 255);
/*  76 */   private Color color17 = decodeColor("nimbusOrange", -0.08377897F, 0.02094239F, -0.31764707F, 0);
/*  77 */   private Color color18 = decodeColor("nimbusOrange", -0.02758849F, 0.02094239F, -0.062745094F, 0);
/*  78 */   private Color color19 = decodeColor("nimbusOrange", -4.4563413E-4F, -0.5074419F, 0.1490196F, 0);
/*  79 */   private Color color20 = decodeColor("nimbusOrange", 9.745359E-6F, -0.11175901F, 0.07843137F, 0);
/*  80 */   private Color color21 = decodeColor("nimbusOrange", 0.0F, -0.09280169F, 0.07843137F, 0);
/*  81 */   private Color color22 = decodeColor("nimbusOrange", 0.0F, -0.19002807F, 0.18039215F, 0);
/*  82 */   private Color color23 = decodeColor("nimbusOrange", -0.025772434F, 0.02094239F, 0.05098039F, 0);
/*  83 */   private Color color24 = decodeColor("nimbusOrange", -0.08377897F, 0.02094239F, -0.4F, 0);
/*  84 */   private Color color25 = decodeColor("nimbusOrange", -0.053104125F, 0.02094239F, -0.109803915F, 0);
/*  85 */   private Color color26 = decodeColor("nimbusOrange", -0.017887495F, -0.33726656F, 0.039215684F, 0);
/*  86 */   private Color color27 = decodeColor("nimbusOrange", -0.018038228F, 0.02094239F, -0.043137252F, 0);
/*  87 */   private Color color28 = decodeColor("nimbusOrange", -0.015844189F, 0.02094239F, -0.027450979F, 0);
/*  88 */   private Color color29 = decodeColor("nimbusOrange", -0.010274701F, 0.02094239F, 0.015686274F, 0);
/*  89 */   private Color color30 = decodeColor("nimbusOrange", -0.08377897F, 0.02094239F, -0.14509803F, -91);
/*  90 */   private Color color31 = decodeColor("nimbusOrange", 0.5273321F, -0.87971985F, -0.15686274F, 0);
/*  91 */   private Color color32 = decodeColor("nimbusOrange", 0.5273321F, -0.842694F, -0.31764707F, 0);
/*  92 */   private Color color33 = decodeColor("nimbusOrange", 0.516221F, -0.9567362F, 0.12941176F, 0);
/*  93 */   private Color color34 = decodeColor("nimbusOrange", 0.5222816F, -0.9229352F, 0.019607842F, 0);
/*  94 */   private Color color35 = decodeColor("nimbusOrange", 0.5273321F, -0.91751915F, 0.015686274F, 0);
/*  95 */   private Color color36 = decodeColor("nimbusOrange", 0.5273321F, -0.9193561F, 0.039215684F, 0);
/*  96 */   private Color color37 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.017933726F, -0.32156864F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public InternalFrameTitlePaneIconifyButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
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
/* 115 */         paintBackgroundEnabled(paramGraphics2D); break;
/* 116 */       case 2: paintBackgroundDisabled(paramGraphics2D); break;
/* 117 */       case 3: paintBackgroundMouseOver(paramGraphics2D); break;
/* 118 */       case 4: paintBackgroundPressed(paramGraphics2D); break;
/* 119 */       case 5: paintBackgroundEnabledAndWindowNotFocused(paramGraphics2D); break;
/* 120 */       case 6: paintBackgroundMouseOverAndWindowNotFocused(paramGraphics2D); break;
/* 121 */       case 7: paintBackgroundPressedAndWindowNotFocused(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 130 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 134 */     this.roundRect = decodeRoundRect1();
/* 135 */     paramGraphics2D.setPaint(this.color1);
/* 136 */     paramGraphics2D.fill(this.roundRect);
/* 137 */     this.roundRect = decodeRoundRect2();
/* 138 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 139 */     paramGraphics2D.fill(this.roundRect);
/* 140 */     this.roundRect = decodeRoundRect3();
/* 141 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 142 */     paramGraphics2D.fill(this.roundRect);
/* 143 */     this.rect = decodeRect1();
/* 144 */     paramGraphics2D.setPaint(this.color7);
/* 145 */     paramGraphics2D.fill(this.rect);
/* 146 */     this.rect = decodeRect2();
/* 147 */     paramGraphics2D.setPaint(this.color8);
/* 148 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 153 */     this.roundRect = decodeRoundRect1();
/* 154 */     paramGraphics2D.setPaint(this.color1);
/* 155 */     paramGraphics2D.fill(this.roundRect);
/* 156 */     this.roundRect = decodeRoundRect2();
/* 157 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 158 */     paramGraphics2D.fill(this.roundRect);
/* 159 */     this.roundRect = decodeRoundRect3();
/* 160 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 161 */     paramGraphics2D.fill(this.roundRect);
/* 162 */     this.rect = decodeRect1();
/* 163 */     paramGraphics2D.setPaint(this.color14);
/* 164 */     paramGraphics2D.fill(this.rect);
/* 165 */     this.rect = decodeRect2();
/* 166 */     paramGraphics2D.setPaint(this.color15);
/* 167 */     paramGraphics2D.fill(this.rect);
/* 168 */     this.rect = decodeRect3();
/* 169 */     paramGraphics2D.setPaint(this.color16);
/* 170 */     paramGraphics2D.fill(this.rect);
/* 171 */     this.rect = decodeRect3();
/* 172 */     paramGraphics2D.setPaint(this.color16);
/* 173 */     paramGraphics2D.fill(this.rect);
/* 174 */     this.rect = decodeRect3();
/* 175 */     paramGraphics2D.setPaint(this.color16);
/* 176 */     paramGraphics2D.fill(this.rect);
/* 177 */     this.rect = decodeRect3();
/* 178 */     paramGraphics2D.setPaint(this.color16);
/* 179 */     paramGraphics2D.fill(this.rect);
/* 180 */     this.rect = decodeRect3();
/* 181 */     paramGraphics2D.setPaint(this.color16);
/* 182 */     paramGraphics2D.fill(this.rect);
/* 183 */     this.rect = decodeRect3();
/* 184 */     paramGraphics2D.setPaint(this.color16);
/* 185 */     paramGraphics2D.fill(this.rect);
/* 186 */     this.rect = decodeRect3();
/* 187 */     paramGraphics2D.setPaint(this.color16);
/* 188 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 193 */     this.roundRect = decodeRoundRect1();
/* 194 */     paramGraphics2D.setPaint(this.color1);
/* 195 */     paramGraphics2D.fill(this.roundRect);
/* 196 */     this.roundRect = decodeRoundRect2();
/* 197 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 198 */     paramGraphics2D.fill(this.roundRect);
/* 199 */     this.roundRect = decodeRoundRect3();
/* 200 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 201 */     paramGraphics2D.fill(this.roundRect);
/* 202 */     this.rect = decodeRect1();
/* 203 */     paramGraphics2D.setPaint(this.color23);
/* 204 */     paramGraphics2D.fill(this.rect);
/* 205 */     this.rect = decodeRect2();
/* 206 */     paramGraphics2D.setPaint(this.color8);
/* 207 */     paramGraphics2D.fill(this.rect);
/* 208 */     this.rect = decodeRect3();
/* 209 */     paramGraphics2D.setPaint(this.color16);
/* 210 */     paramGraphics2D.fill(this.rect);
/* 211 */     this.rect = decodeRect3();
/* 212 */     paramGraphics2D.setPaint(this.color16);
/* 213 */     paramGraphics2D.fill(this.rect);
/* 214 */     this.rect = decodeRect3();
/* 215 */     paramGraphics2D.setPaint(this.color16);
/* 216 */     paramGraphics2D.fill(this.rect);
/* 217 */     this.rect = decodeRect3();
/* 218 */     paramGraphics2D.setPaint(this.color16);
/* 219 */     paramGraphics2D.fill(this.rect);
/* 220 */     this.rect = decodeRect3();
/* 221 */     paramGraphics2D.setPaint(this.color16);
/* 222 */     paramGraphics2D.fill(this.rect);
/* 223 */     this.rect = decodeRect3();
/* 224 */     paramGraphics2D.setPaint(this.color16);
/* 225 */     paramGraphics2D.fill(this.rect);
/* 226 */     this.rect = decodeRect3();
/* 227 */     paramGraphics2D.setPaint(this.color16);
/* 228 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 233 */     this.roundRect = decodeRoundRect1();
/* 234 */     paramGraphics2D.setPaint(this.color1);
/* 235 */     paramGraphics2D.fill(this.roundRect);
/* 236 */     this.roundRect = decodeRoundRect2();
/* 237 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 238 */     paramGraphics2D.fill(this.roundRect);
/* 239 */     this.roundRect = decodeRoundRect3();
/* 240 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 241 */     paramGraphics2D.fill(this.roundRect);
/* 242 */     this.rect = decodeRect4();
/* 243 */     paramGraphics2D.setPaint(this.color30);
/* 244 */     paramGraphics2D.fill(this.rect);
/* 245 */     this.rect = decodeRect2();
/* 246 */     paramGraphics2D.setPaint(this.color8);
/* 247 */     paramGraphics2D.fill(this.rect);
/* 248 */     this.rect = decodeRect3();
/* 249 */     paramGraphics2D.setPaint(this.color16);
/* 250 */     paramGraphics2D.fill(this.rect);
/* 251 */     this.rect = decodeRect3();
/* 252 */     paramGraphics2D.setPaint(this.color16);
/* 253 */     paramGraphics2D.fill(this.rect);
/* 254 */     this.rect = decodeRect3();
/* 255 */     paramGraphics2D.setPaint(this.color16);
/* 256 */     paramGraphics2D.fill(this.rect);
/* 257 */     this.rect = decodeRect3();
/* 258 */     paramGraphics2D.setPaint(this.color16);
/* 259 */     paramGraphics2D.fill(this.rect);
/* 260 */     this.rect = decodeRect3();
/* 261 */     paramGraphics2D.setPaint(this.color16);
/* 262 */     paramGraphics2D.fill(this.rect);
/* 263 */     this.rect = decodeRect3();
/* 264 */     paramGraphics2D.setPaint(this.color16);
/* 265 */     paramGraphics2D.fill(this.rect);
/* 266 */     this.rect = decodeRect3();
/* 267 */     paramGraphics2D.setPaint(this.color16);
/* 268 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 273 */     this.roundRect = decodeRoundRect1();
/* 274 */     paramGraphics2D.setPaint(this.color1);
/* 275 */     paramGraphics2D.fill(this.roundRect);
/* 276 */     this.roundRect = decodeRoundRect2();
/* 277 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 278 */     paramGraphics2D.fill(this.roundRect);
/* 279 */     this.roundRect = decodeRoundRect3();
/* 280 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 281 */     paramGraphics2D.fill(this.roundRect);
/* 282 */     this.rect = decodeRect1();
/* 283 */     paramGraphics2D.setPaint(this.color14);
/* 284 */     paramGraphics2D.fill(this.rect);
/* 285 */     this.rect = decodeRect2();
/* 286 */     paramGraphics2D.setPaint(this.color37);
/* 287 */     paramGraphics2D.fill(this.rect);
/* 288 */     this.rect = decodeRect3();
/* 289 */     paramGraphics2D.setPaint(this.color16);
/* 290 */     paramGraphics2D.fill(this.rect);
/* 291 */     this.rect = decodeRect3();
/* 292 */     paramGraphics2D.setPaint(this.color16);
/* 293 */     paramGraphics2D.fill(this.rect);
/* 294 */     this.rect = decodeRect3();
/* 295 */     paramGraphics2D.setPaint(this.color16);
/* 296 */     paramGraphics2D.fill(this.rect);
/* 297 */     this.rect = decodeRect3();
/* 298 */     paramGraphics2D.setPaint(this.color16);
/* 299 */     paramGraphics2D.fill(this.rect);
/* 300 */     this.rect = decodeRect3();
/* 301 */     paramGraphics2D.setPaint(this.color16);
/* 302 */     paramGraphics2D.fill(this.rect);
/* 303 */     this.rect = decodeRect3();
/* 304 */     paramGraphics2D.setPaint(this.color16);
/* 305 */     paramGraphics2D.fill(this.rect);
/* 306 */     this.rect = decodeRect3();
/* 307 */     paramGraphics2D.setPaint(this.color16);
/* 308 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 313 */     this.roundRect = decodeRoundRect1();
/* 314 */     paramGraphics2D.setPaint(this.color1);
/* 315 */     paramGraphics2D.fill(this.roundRect);
/* 316 */     this.roundRect = decodeRoundRect2();
/* 317 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 318 */     paramGraphics2D.fill(this.roundRect);
/* 319 */     this.roundRect = decodeRoundRect3();
/* 320 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 321 */     paramGraphics2D.fill(this.roundRect);
/* 322 */     this.rect = decodeRect1();
/* 323 */     paramGraphics2D.setPaint(this.color23);
/* 324 */     paramGraphics2D.fill(this.rect);
/* 325 */     this.rect = decodeRect2();
/* 326 */     paramGraphics2D.setPaint(this.color8);
/* 327 */     paramGraphics2D.fill(this.rect);
/* 328 */     this.rect = decodeRect3();
/* 329 */     paramGraphics2D.setPaint(this.color16);
/* 330 */     paramGraphics2D.fill(this.rect);
/* 331 */     this.rect = decodeRect3();
/* 332 */     paramGraphics2D.setPaint(this.color16);
/* 333 */     paramGraphics2D.fill(this.rect);
/* 334 */     this.rect = decodeRect3();
/* 335 */     paramGraphics2D.setPaint(this.color16);
/* 336 */     paramGraphics2D.fill(this.rect);
/* 337 */     this.rect = decodeRect3();
/* 338 */     paramGraphics2D.setPaint(this.color16);
/* 339 */     paramGraphics2D.fill(this.rect);
/* 340 */     this.rect = decodeRect3();
/* 341 */     paramGraphics2D.setPaint(this.color16);
/* 342 */     paramGraphics2D.fill(this.rect);
/* 343 */     this.rect = decodeRect3();
/* 344 */     paramGraphics2D.setPaint(this.color16);
/* 345 */     paramGraphics2D.fill(this.rect);
/* 346 */     this.rect = decodeRect3();
/* 347 */     paramGraphics2D.setPaint(this.color16);
/* 348 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 353 */     this.roundRect = decodeRoundRect1();
/* 354 */     paramGraphics2D.setPaint(this.color1);
/* 355 */     paramGraphics2D.fill(this.roundRect);
/* 356 */     this.roundRect = decodeRoundRect2();
/* 357 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 358 */     paramGraphics2D.fill(this.roundRect);
/* 359 */     this.roundRect = decodeRoundRect3();
/* 360 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 361 */     paramGraphics2D.fill(this.roundRect);
/* 362 */     this.rect = decodeRect4();
/* 363 */     paramGraphics2D.setPaint(this.color30);
/* 364 */     paramGraphics2D.fill(this.rect);
/* 365 */     this.rect = decodeRect2();
/* 366 */     paramGraphics2D.setPaint(this.color8);
/* 367 */     paramGraphics2D.fill(this.rect);
/* 368 */     this.rect = decodeRect3();
/* 369 */     paramGraphics2D.setPaint(this.color16);
/* 370 */     paramGraphics2D.fill(this.rect);
/* 371 */     this.rect = decodeRect3();
/* 372 */     paramGraphics2D.setPaint(this.color16);
/* 373 */     paramGraphics2D.fill(this.rect);
/* 374 */     this.rect = decodeRect3();
/* 375 */     paramGraphics2D.setPaint(this.color16);
/* 376 */     paramGraphics2D.fill(this.rect);
/* 377 */     this.rect = decodeRect3();
/* 378 */     paramGraphics2D.setPaint(this.color16);
/* 379 */     paramGraphics2D.fill(this.rect);
/* 380 */     this.rect = decodeRect3();
/* 381 */     paramGraphics2D.setPaint(this.color16);
/* 382 */     paramGraphics2D.fill(this.rect);
/* 383 */     this.rect = decodeRect3();
/* 384 */     paramGraphics2D.setPaint(this.color16);
/* 385 */     paramGraphics2D.fill(this.rect);
/* 386 */     this.rect = decodeRect3();
/* 387 */     paramGraphics2D.setPaint(this.color16);
/* 388 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 395 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/* 396 */         decodeY(1.6111112F), (
/* 397 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 398 */         decodeY(2.0F) - decodeY(1.6111112F)), 6.0D, 6.0D);
/*     */     
/* 400 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 404 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/* 405 */         decodeY(1.0F), (
/* 406 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 407 */         decodeY(1.9444444F) - decodeY(1.0F)), 8.600000381469727D, 8.600000381469727D);
/*     */     
/* 409 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect3() {
/* 413 */     this.roundRect.setRoundRect(decodeX(1.0526316F), 
/* 414 */         decodeY(1.0555556F), (
/* 415 */         decodeX(1.9473684F) - decodeX(1.0526316F)), (
/* 416 */         decodeY(1.8888888F) - decodeY(1.0555556F)), 6.75D, 6.75D);
/*     */     
/* 418 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 422 */     this.rect.setRect(decodeX(1.25F), 
/* 423 */         decodeY(1.6628788F), (
/* 424 */         decodeX(1.75F) - decodeX(1.25F)), (
/* 425 */         decodeY(1.7487373F) - decodeY(1.6628788F)));
/* 426 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 430 */     this.rect.setRect(decodeX(1.2870814F), 
/* 431 */         decodeY(1.6123737F), (
/* 432 */         decodeX(1.7165072F) - decodeX(1.2870814F)), (
/* 433 */         decodeY(1.7222222F) - decodeY(1.6123737F)));
/* 434 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 438 */     this.rect.setRect(decodeX(1.0F), 
/* 439 */         decodeY(1.0F), (
/* 440 */         decodeX(1.0F) - decodeX(1.0F)), (
/* 441 */         decodeY(1.0F) - decodeY(1.0F)));
/* 442 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect4() {
/* 446 */     this.rect.setRect(decodeX(1.25F), 
/* 447 */         decodeY(1.6527778F), (
/* 448 */         decodeX(1.7511961F) - decodeX(1.25F)), (
/* 449 */         decodeY(1.7828283F) - decodeY(1.6527778F)));
/* 450 */     return this.rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 456 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 457 */     float f1 = (float)rectangle2D.getX();
/* 458 */     float f2 = (float)rectangle2D.getY();
/* 459 */     float f3 = (float)rectangle2D.getWidth();
/* 460 */     float f4 = (float)rectangle2D.getHeight();
/* 461 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 464 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 469 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 470 */     float f1 = (float)rectangle2D.getX();
/* 471 */     float f2 = (float)rectangle2D.getY();
/* 472 */     float f3 = (float)rectangle2D.getWidth();
/* 473 */     float f4 = (float)rectangle2D.getHeight();
/* 474 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color4, 
/*     */ 
/*     */           
/* 477 */           decodeColor(this.color4, this.color3, 0.5F), this.color3, 
/*     */           
/* 479 */           decodeColor(this.color3, this.color5, 0.5F), this.color5, 
/*     */           
/* 481 */           decodeColor(this.color5, this.color6, 0.5F), this.color6 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 486 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 487 */     float f1 = (float)rectangle2D.getX();
/* 488 */     float f2 = (float)rectangle2D.getY();
/* 489 */     float f3 = (float)rectangle2D.getWidth();
/* 490 */     float f4 = (float)rectangle2D.getHeight();
/* 491 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color9, 
/*     */ 
/*     */           
/* 494 */           decodeColor(this.color9, this.color10, 0.5F), this.color10 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 499 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 500 */     float f1 = (float)rectangle2D.getX();
/* 501 */     float f2 = (float)rectangle2D.getY();
/* 502 */     float f3 = (float)rectangle2D.getWidth();
/* 503 */     float f4 = (float)rectangle2D.getHeight();
/* 504 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color11, 
/*     */ 
/*     */           
/* 507 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*     */           
/* 509 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*     */           
/* 511 */           decodeColor(this.color13, this.color10, 0.5F), this.color10 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 516 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 517 */     float f1 = (float)rectangle2D.getX();
/* 518 */     float f2 = (float)rectangle2D.getY();
/* 519 */     float f3 = (float)rectangle2D.getWidth();
/* 520 */     float f4 = (float)rectangle2D.getHeight();
/* 521 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color17, 
/*     */ 
/*     */           
/* 524 */           decodeColor(this.color17, this.color18, 0.5F), this.color18 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 529 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 530 */     float f1 = (float)rectangle2D.getX();
/* 531 */     float f2 = (float)rectangle2D.getY();
/* 532 */     float f3 = (float)rectangle2D.getWidth();
/* 533 */     float f4 = (float)rectangle2D.getHeight();
/* 534 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color19, 
/*     */ 
/*     */           
/* 537 */           decodeColor(this.color19, this.color20, 0.5F), this.color20, 
/*     */           
/* 539 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 541 */           decodeColor(this.color21, this.color22, 0.5F), this.color22 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 546 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 547 */     float f1 = (float)rectangle2D.getX();
/* 548 */     float f2 = (float)rectangle2D.getY();
/* 549 */     float f3 = (float)rectangle2D.getWidth();
/* 550 */     float f4 = (float)rectangle2D.getHeight();
/* 551 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 554 */           decodeColor(this.color24, this.color25, 0.5F), this.color25 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 559 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 560 */     float f1 = (float)rectangle2D.getX();
/* 561 */     float f2 = (float)rectangle2D.getY();
/* 562 */     float f3 = (float)rectangle2D.getWidth();
/* 563 */     float f4 = (float)rectangle2D.getHeight();
/* 564 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color26, 
/*     */ 
/*     */           
/* 567 */           decodeColor(this.color26, this.color27, 0.5F), this.color27, 
/*     */           
/* 569 */           decodeColor(this.color27, this.color28, 0.5F), this.color28, 
/*     */           
/* 571 */           decodeColor(this.color28, this.color29, 0.5F), this.color29 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 576 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 577 */     float f1 = (float)rectangle2D.getX();
/* 578 */     float f2 = (float)rectangle2D.getY();
/* 579 */     float f3 = (float)rectangle2D.getWidth();
/* 580 */     float f4 = (float)rectangle2D.getHeight();
/* 581 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color31, 
/*     */ 
/*     */           
/* 584 */           decodeColor(this.color31, this.color32, 0.5F), this.color32 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 589 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 590 */     float f1 = (float)rectangle2D.getX();
/* 591 */     float f2 = (float)rectangle2D.getY();
/* 592 */     float f3 = (float)rectangle2D.getWidth();
/* 593 */     float f4 = (float)rectangle2D.getHeight();
/* 594 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.78336793F, 0.9161677F }, new Color[] { this.color33, 
/*     */ 
/*     */           
/* 597 */           decodeColor(this.color33, this.color34, 0.5F), this.color34, 
/*     */           
/* 599 */           decodeColor(this.color34, this.color35, 0.5F), this.color35, 
/*     */           
/* 601 */           decodeColor(this.color35, this.color36, 0.5F), this.color36 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/InternalFrameTitlePaneIconifyButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */