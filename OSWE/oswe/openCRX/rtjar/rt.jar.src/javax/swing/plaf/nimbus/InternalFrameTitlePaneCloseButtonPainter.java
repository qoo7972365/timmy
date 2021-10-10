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
/*     */ final class InternalFrameTitlePaneCloseButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
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
/*  60 */   private Color color1 = decodeColor("nimbusRed", 0.5893519F, -0.75736576F, 0.09411764F, 0);
/*  61 */   private Color color2 = decodeColor("nimbusRed", 0.5962963F, -0.71005917F, 0.0F, 0);
/*  62 */   private Color color3 = decodeColor("nimbusRed", 0.6005698F, -0.7200287F, -0.015686274F, -122);
/*  63 */   private Color color4 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.062449392F, 0.07058823F, 0);
/*  64 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.0029994324F, -0.38039216F, -185);
/*  65 */   private Color color6 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.4431373F, 0);
/*  66 */   private Color color7 = decodeColor("nimbusRed", -2.7342606E-4F, 0.13829035F, -0.039215684F, 0);
/*  67 */   private Color color8 = decodeColor("nimbusRed", 6.890595E-4F, -0.36665577F, 0.11764705F, 0);
/*  68 */   private Color color9 = decodeColor("nimbusRed", -0.001021713F, 0.101804554F, -0.031372547F, 0);
/*  69 */   private Color color10 = decodeColor("nimbusRed", -2.7342606E-4F, 0.13243341F, -0.035294116F, 0);
/*  70 */   private Color color11 = decodeColor("nimbusRed", -2.7342606E-4F, 0.002258718F, 0.06666666F, 0);
/*  71 */   private Color color12 = decodeColor("nimbusRed", 0.0056530247F, 0.0040003657F, -0.38431373F, -122);
/*  72 */   private Color color13 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  73 */   private Color color14 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.3882353F, 0);
/*  74 */   private Color color15 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.13333333F, 0);
/*  75 */   private Color color16 = decodeColor("nimbusRed", 6.890595E-4F, -0.38929275F, 0.1607843F, 0);
/*  76 */   private Color color17 = decodeColor("nimbusRed", 2.537202E-5F, 0.012294531F, 0.043137252F, 0);
/*  77 */   private Color color18 = decodeColor("nimbusRed", -2.7342606E-4F, 0.033585668F, 0.039215684F, 0);
/*  78 */   private Color color19 = decodeColor("nimbusRed", -2.7342606E-4F, -0.07198727F, 0.14117646F, 0);
/*  79 */   private Color color20 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, 0.0039215684F, -122);
/*  80 */   private Color color21 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -140);
/*  81 */   private Color color22 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.49411768F, 0);
/*  82 */   private Color color23 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.20392159F, 0);
/*  83 */   private Color color24 = decodeColor("nimbusRed", -0.014814814F, -0.21260965F, 0.019607842F, 0);
/*  84 */   private Color color25 = decodeColor("nimbusRed", -0.014814814F, 0.17340565F, -0.09803921F, 0);
/*  85 */   private Color color26 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.10588235F, 0);
/*  86 */   private Color color27 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.04705882F, 0);
/*  87 */   private Color color28 = decodeColor("nimbusRed", -0.014814814F, 0.20118344F, -0.31764707F, -122);
/*  88 */   private Color color29 = decodeColor("nimbusRed", 0.5962963F, -0.6994788F, -0.07058823F, 0);
/*  89 */   private Color color30 = decodeColor("nimbusRed", 0.5962963F, -0.66245294F, -0.23137257F, 0);
/*  90 */   private Color color31 = decodeColor("nimbusRed", 0.58518517F, -0.77649516F, 0.21568626F, 0);
/*  91 */   private Color color32 = decodeColor("nimbusRed", 0.5962963F, -0.7372781F, 0.10196078F, 0);
/*  92 */   private Color color33 = decodeColor("nimbusRed", 0.5962963F, -0.73911506F, 0.12549019F, 0);
/*  93 */   private Color color34 = decodeColor("nimbusBlueGrey", 0.0F, -0.027957506F, -0.31764707F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public InternalFrameTitlePaneCloseButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 101 */     this.state = paramInt;
/* 102 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 108 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 111 */     switch (this.state) { case 1:
/* 112 */         paintBackgroundDisabled(paramGraphics2D); break;
/* 113 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/* 114 */       case 3: paintBackgroundMouseOver(paramGraphics2D); break;
/* 115 */       case 4: paintBackgroundPressed(paramGraphics2D); break;
/* 116 */       case 5: paintBackgroundEnabledAndWindowNotFocused(paramGraphics2D); break;
/* 117 */       case 6: paintBackgroundMouseOverAndWindowNotFocused(paramGraphics2D); break;
/* 118 */       case 7: paintBackgroundPressedAndWindowNotFocused(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 127 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 131 */     this.roundRect = decodeRoundRect1();
/* 132 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 133 */     paramGraphics2D.fill(this.roundRect);
/* 134 */     this.path = decodePath1();
/* 135 */     paramGraphics2D.setPaint(this.color3);
/* 136 */     paramGraphics2D.fill(this.path);
/* 137 */     this.path = decodePath2();
/* 138 */     paramGraphics2D.setPaint(this.color4);
/* 139 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 144 */     this.roundRect = decodeRoundRect2();
/* 145 */     paramGraphics2D.setPaint(this.color5);
/* 146 */     paramGraphics2D.fill(this.roundRect);
/* 147 */     this.roundRect = decodeRoundRect1();
/* 148 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 149 */     paramGraphics2D.fill(this.roundRect);
/* 150 */     this.roundRect = decodeRoundRect3();
/* 151 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 152 */     paramGraphics2D.fill(this.roundRect);
/* 153 */     this.path = decodePath1();
/* 154 */     paramGraphics2D.setPaint(this.color12);
/* 155 */     paramGraphics2D.fill(this.path);
/* 156 */     this.path = decodePath2();
/* 157 */     paramGraphics2D.setPaint(this.color13);
/* 158 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 163 */     this.roundRect = decodeRoundRect2();
/* 164 */     paramGraphics2D.setPaint(this.color5);
/* 165 */     paramGraphics2D.fill(this.roundRect);
/* 166 */     this.roundRect = decodeRoundRect1();
/* 167 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 168 */     paramGraphics2D.fill(this.roundRect);
/* 169 */     this.roundRect = decodeRoundRect4();
/* 170 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 171 */     paramGraphics2D.fill(this.roundRect);
/* 172 */     this.path = decodePath1();
/* 173 */     paramGraphics2D.setPaint(this.color20);
/* 174 */     paramGraphics2D.fill(this.path);
/* 175 */     this.path = decodePath2();
/* 176 */     paramGraphics2D.setPaint(this.color13);
/* 177 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 182 */     this.roundRect = decodeRoundRect2();
/* 183 */     paramGraphics2D.setPaint(this.color21);
/* 184 */     paramGraphics2D.fill(this.roundRect);
/* 185 */     this.roundRect = decodeRoundRect1();
/* 186 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 187 */     paramGraphics2D.fill(this.roundRect);
/* 188 */     this.roundRect = decodeRoundRect3();
/* 189 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 190 */     paramGraphics2D.fill(this.roundRect);
/* 191 */     this.path = decodePath1();
/* 192 */     paramGraphics2D.setPaint(this.color28);
/* 193 */     paramGraphics2D.fill(this.path);
/* 194 */     this.path = decodePath2();
/* 195 */     paramGraphics2D.setPaint(this.color13);
/* 196 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 201 */     this.roundRect = decodeRoundRect1();
/* 202 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 203 */     paramGraphics2D.fill(this.roundRect);
/* 204 */     this.roundRect = decodeRoundRect3();
/* 205 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 206 */     paramGraphics2D.fill(this.roundRect);
/* 207 */     this.path = decodePath2();
/* 208 */     paramGraphics2D.setPaint(this.color34);
/* 209 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 214 */     this.roundRect = decodeRoundRect2();
/* 215 */     paramGraphics2D.setPaint(this.color5);
/* 216 */     paramGraphics2D.fill(this.roundRect);
/* 217 */     this.roundRect = decodeRoundRect1();
/* 218 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 219 */     paramGraphics2D.fill(this.roundRect);
/* 220 */     this.roundRect = decodeRoundRect4();
/* 221 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 222 */     paramGraphics2D.fill(this.roundRect);
/* 223 */     this.path = decodePath1();
/* 224 */     paramGraphics2D.setPaint(this.color20);
/* 225 */     paramGraphics2D.fill(this.path);
/* 226 */     this.path = decodePath2();
/* 227 */     paramGraphics2D.setPaint(this.color13);
/* 228 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndWindowNotFocused(Graphics2D paramGraphics2D) {
/* 233 */     this.roundRect = decodeRoundRect2();
/* 234 */     paramGraphics2D.setPaint(this.color21);
/* 235 */     paramGraphics2D.fill(this.roundRect);
/* 236 */     this.roundRect = decodeRoundRect1();
/* 237 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 238 */     paramGraphics2D.fill(this.roundRect);
/* 239 */     this.roundRect = decodeRoundRect3();
/* 240 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 241 */     paramGraphics2D.fill(this.roundRect);
/* 242 */     this.path = decodePath1();
/* 243 */     paramGraphics2D.setPaint(this.color28);
/* 244 */     paramGraphics2D.fill(this.path);
/* 245 */     this.path = decodePath2();
/* 246 */     paramGraphics2D.setPaint(this.color13);
/* 247 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 254 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/* 255 */         decodeY(1.0F), (
/* 256 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 257 */         decodeY(1.9444444F) - decodeY(1.0F)), 8.600000381469727D, 8.600000381469727D);
/*     */     
/* 259 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 263 */     this.path.reset();
/* 264 */     this.path.moveTo(decodeX(1.25F), decodeY(1.7373737F));
/* 265 */     this.path.lineTo(decodeX(1.3002392F), decodeY(1.794192F));
/* 266 */     this.path.lineTo(decodeX(1.5047847F), decodeY(1.5909091F));
/* 267 */     this.path.lineTo(decodeX(1.6842105F), decodeY(1.7954545F));
/* 268 */     this.path.lineTo(decodeX(1.7595694F), decodeY(1.719697F));
/* 269 */     this.path.lineTo(decodeX(1.5956938F), decodeY(1.5239899F));
/* 270 */     this.path.lineTo(decodeX(1.7535884F), decodeY(1.3409091F));
/* 271 */     this.path.lineTo(decodeX(1.6830144F), decodeY(1.2537879F));
/* 272 */     this.path.lineTo(decodeX(1.5083733F), decodeY(1.4406565F));
/* 273 */     this.path.lineTo(decodeX(1.3301436F), decodeY(1.2563131F));
/* 274 */     this.path.lineTo(decodeX(1.257177F), decodeY(1.3320707F));
/* 275 */     this.path.lineTo(decodeX(1.4270334F), decodeY(1.5252526F));
/* 276 */     this.path.lineTo(decodeX(1.25F), decodeY(1.7373737F));
/* 277 */     this.path.closePath();
/* 278 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 282 */     this.path.reset();
/* 283 */     this.path.moveTo(decodeX(1.257177F), decodeY(1.2828283F));
/* 284 */     this.path.lineTo(decodeX(1.3217703F), decodeY(1.2133838F));
/* 285 */     this.path.lineTo(decodeX(1.5F), decodeY(1.4040405F));
/* 286 */     this.path.lineTo(decodeX(1.673445F), decodeY(1.2108586F));
/* 287 */     this.path.lineTo(decodeX(1.7440192F), decodeY(1.2853535F));
/* 288 */     this.path.lineTo(decodeX(1.5669856F), decodeY(1.4709597F));
/* 289 */     this.path.lineTo(decodeX(1.7488039F), decodeY(1.6527778F));
/* 290 */     this.path.lineTo(decodeX(1.673445F), decodeY(1.7398989F));
/* 291 */     this.path.lineTo(decodeX(1.4988039F), decodeY(1.5416667F));
/* 292 */     this.path.lineTo(decodeX(1.3313397F), decodeY(1.7424242F));
/* 293 */     this.path.lineTo(decodeX(1.2523923F), decodeY(1.6565657F));
/* 294 */     this.path.lineTo(decodeX(1.4366028F), decodeY(1.4722222F));
/* 295 */     this.path.lineTo(decodeX(1.257177F), decodeY(1.2828283F));
/* 296 */     this.path.closePath();
/* 297 */     return this.path;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 301 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/* 302 */         decodeY(1.6111112F), (
/* 303 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 304 */         decodeY(2.0F) - decodeY(1.6111112F)), 6.0D, 6.0D);
/*     */     
/* 306 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect3() {
/* 310 */     this.roundRect.setRoundRect(decodeX(1.0526316F), 
/* 311 */         decodeY(1.0530303F), (
/* 312 */         decodeX(1.9473684F) - decodeX(1.0526316F)), (
/* 313 */         decodeY(1.8863636F) - decodeY(1.0530303F)), 6.75D, 6.75D);
/*     */     
/* 315 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect4() {
/* 319 */     this.roundRect.setRoundRect(decodeX(1.0526316F), 
/* 320 */         decodeY(1.0517677F), (
/* 321 */         decodeX(1.9473684F) - decodeX(1.0526316F)), (
/* 322 */         decodeY(1.8851011F) - decodeY(1.0517677F)), 6.75D, 6.75D);
/*     */     
/* 324 */     return this.roundRect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 330 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 331 */     float f1 = (float)rectangle2D.getX();
/* 332 */     float f2 = (float)rectangle2D.getY();
/* 333 */     float f3 = (float)rectangle2D.getWidth();
/* 334 */     float f4 = (float)rectangle2D.getHeight();
/* 335 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 338 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 343 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 344 */     float f1 = (float)rectangle2D.getX();
/* 345 */     float f2 = (float)rectangle2D.getY();
/* 346 */     float f3 = (float)rectangle2D.getWidth();
/* 347 */     float f4 = (float)rectangle2D.getHeight();
/* 348 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color6, 
/*     */ 
/*     */           
/* 351 */           decodeColor(this.color6, this.color7, 0.5F), this.color7 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 356 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 357 */     float f1 = (float)rectangle2D.getX();
/* 358 */     float f2 = (float)rectangle2D.getY();
/* 359 */     float f3 = (float)rectangle2D.getWidth();
/* 360 */     float f4 = (float)rectangle2D.getHeight();
/* 361 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color8, 
/*     */ 
/*     */           
/* 364 */           decodeColor(this.color8, this.color9, 0.5F), this.color9, 
/*     */           
/* 366 */           decodeColor(this.color9, this.color10, 0.5F), this.color10, 
/*     */           
/* 368 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 373 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 374 */     float f1 = (float)rectangle2D.getX();
/* 375 */     float f2 = (float)rectangle2D.getY();
/* 376 */     float f3 = (float)rectangle2D.getWidth();
/* 377 */     float f4 = (float)rectangle2D.getHeight();
/* 378 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color14, 
/*     */ 
/*     */           
/* 381 */           decodeColor(this.color14, this.color15, 0.5F), this.color15 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 386 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 387 */     float f1 = (float)rectangle2D.getX();
/* 388 */     float f2 = (float)rectangle2D.getY();
/* 389 */     float f3 = (float)rectangle2D.getWidth();
/* 390 */     float f4 = (float)rectangle2D.getHeight();
/* 391 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.81480503F, 0.97904193F }, new Color[] { this.color16, 
/*     */ 
/*     */           
/* 394 */           decodeColor(this.color16, this.color17, 0.5F), this.color17, 
/*     */           
/* 396 */           decodeColor(this.color17, this.color18, 0.5F), this.color18, 
/*     */           
/* 398 */           decodeColor(this.color18, this.color19, 0.5F), this.color19 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 403 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 404 */     float f1 = (float)rectangle2D.getX();
/* 405 */     float f2 = (float)rectangle2D.getY();
/* 406 */     float f3 = (float)rectangle2D.getWidth();
/* 407 */     float f4 = (float)rectangle2D.getHeight();
/* 408 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color22, 
/*     */ 
/*     */           
/* 411 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 416 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 417 */     float f1 = (float)rectangle2D.getX();
/* 418 */     float f2 = (float)rectangle2D.getY();
/* 419 */     float f3 = (float)rectangle2D.getWidth();
/* 420 */     float f4 = (float)rectangle2D.getHeight();
/* 421 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.81630206F, 0.98203593F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 424 */           decodeColor(this.color24, this.color25, 0.5F), this.color25, 
/*     */           
/* 426 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*     */           
/* 428 */           decodeColor(this.color26, this.color27, 0.5F), this.color27 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 433 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 434 */     float f1 = (float)rectangle2D.getX();
/* 435 */     float f2 = (float)rectangle2D.getY();
/* 436 */     float f3 = (float)rectangle2D.getWidth();
/* 437 */     float f4 = (float)rectangle2D.getHeight();
/* 438 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color29, 
/*     */ 
/*     */           
/* 441 */           decodeColor(this.color29, this.color30, 0.5F), this.color30 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 446 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 447 */     float f1 = (float)rectangle2D.getX();
/* 448 */     float f2 = (float)rectangle2D.getY();
/* 449 */     float f3 = (float)rectangle2D.getWidth();
/* 450 */     float f4 = (float)rectangle2D.getHeight();
/* 451 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.24101797F, 0.48203593F, 0.5838324F, 0.6856288F, 0.8428144F, 1.0F }, new Color[] { this.color31, 
/*     */ 
/*     */           
/* 454 */           decodeColor(this.color31, this.color32, 0.5F), this.color32, 
/*     */           
/* 456 */           decodeColor(this.color32, this.color32, 0.5F), this.color32, 
/*     */           
/* 458 */           decodeColor(this.color32, this.color33, 0.5F), this.color33 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/InternalFrameTitlePaneCloseButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */