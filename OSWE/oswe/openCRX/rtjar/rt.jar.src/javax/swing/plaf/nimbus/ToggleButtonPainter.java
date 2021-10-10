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
/*     */ final class ToggleButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_FOCUSED = 3;
/*     */   static final int BACKGROUND_MOUSEOVER = 4;
/*     */   static final int BACKGROUND_MOUSEOVER_FOCUSED = 5;
/*     */   static final int BACKGROUND_PRESSED = 6;
/*     */   static final int BACKGROUND_PRESSED_FOCUSED = 7;
/*     */   static final int BACKGROUND_SELECTED = 8;
/*     */   static final int BACKGROUND_SELECTED_FOCUSED = 9;
/*     */   static final int BACKGROUND_PRESSED_SELECTED = 10;
/*     */   static final int BACKGROUND_PRESSED_SELECTED_FOCUSED = 11;
/*     */   static final int BACKGROUND_MOUSEOVER_SELECTED = 12;
/*     */   static final int BACKGROUND_MOUSEOVER_SELECTED_FOCUSED = 13;
/*     */   static final int BACKGROUND_DISABLED_SELECTED = 14;
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
/*  67 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.06885965F, -0.36862746F, -232);
/*  68 */   private Color color2 = decodeColor("nimbusBlueGrey", 0.0F, -0.06766917F, 0.07843137F, 0);
/*  69 */   private Color color3 = decodeColor("nimbusBlueGrey", 0.0F, -0.06484103F, 0.027450979F, 0);
/*  70 */   private Color color4 = decodeColor("nimbusBlueGrey", 0.0F, -0.08477524F, 0.16862744F, 0);
/*  71 */   private Color color5 = decodeColor("nimbusBlueGrey", -0.015872955F, -0.080091536F, 0.15686274F, 0);
/*  72 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.0F, -0.07016757F, 0.12941176F, 0);
/*  73 */   private Color color7 = decodeColor("nimbusBlueGrey", 0.0F, -0.07052632F, 0.1372549F, 0);
/*  74 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.0F, -0.070878744F, 0.14509803F, 0);
/*  75 */   private Color color9 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.06885965F, -0.36862746F, -190);
/*  76 */   private Color color10 = decodeColor("nimbusBlueGrey", -0.055555522F, -0.05356429F, -0.12549019F, 0);
/*  77 */   private Color color11 = decodeColor("nimbusBlueGrey", 0.0F, -0.0147816315F, -0.3764706F, 0);
/*  78 */   private Color color12 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.10655806F, 0.24313724F, 0);
/*  79 */   private Color color13 = decodeColor("nimbusBlueGrey", 0.0F, -0.09823123F, 0.2117647F, 0);
/*  80 */   private Color color14 = decodeColor("nimbusBlueGrey", 0.0F, -0.0749532F, 0.24705881F, 0);
/*  81 */   private Color color15 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  82 */   private Color color16 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  83 */   private Color color17 = decodeColor("nimbusBlueGrey", 0.0F, -0.020974077F, -0.21960783F, 0);
/*  84 */   private Color color18 = decodeColor("nimbusBlueGrey", 0.0F, 0.11169591F, -0.53333336F, 0);
/*  85 */   private Color color19 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.10658931F, 0.25098038F, 0);
/*  86 */   private Color color20 = decodeColor("nimbusBlueGrey", 0.0F, -0.098526314F, 0.2352941F, 0);
/*  87 */   private Color color21 = decodeColor("nimbusBlueGrey", 0.0F, -0.07333623F, 0.20392156F, 0);
/*  88 */   private Color color22 = new Color(245, 250, 255, 160);
/*  89 */   private Color color23 = decodeColor("nimbusBlueGrey", 0.055555582F, 0.8894737F, -0.7176471F, 0);
/*  90 */   private Color color24 = decodeColor("nimbusBlueGrey", 0.0F, 5.847961E-4F, -0.32156864F, 0);
/*  91 */   private Color color25 = decodeColor("nimbusBlueGrey", -0.00505054F, -0.05960039F, 0.10196078F, 0);
/*  92 */   private Color color26 = decodeColor("nimbusBlueGrey", -0.008547008F, -0.04772438F, 0.06666666F, 0);
/*  93 */   private Color color27 = decodeColor("nimbusBlueGrey", -0.0027777553F, -0.0018306673F, -0.02352941F, 0);
/*  94 */   private Color color28 = decodeColor("nimbusBlueGrey", -0.0027777553F, -0.0212406F, 0.13333333F, 0);
/*  95 */   private Color color29 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.030845039F, 0.23921567F, 0);
/*  96 */   private Color color30 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -86);
/*  97 */   private Color color31 = decodeColor("nimbusBlueGrey", 0.0F, -0.06472479F, -0.23137254F, 0);
/*  98 */   private Color color32 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.06959064F, -0.0745098F, 0);
/*  99 */   private Color color33 = decodeColor("nimbusBlueGrey", 0.0138888955F, -0.06401469F, -0.07058823F, 0);
/* 100 */   private Color color34 = decodeColor("nimbusBlueGrey", 0.0F, -0.06530018F, 0.035294116F, 0);
/* 101 */   private Color color35 = decodeColor("nimbusBlueGrey", 0.0F, -0.06507177F, 0.031372547F, 0);
/* 102 */   private Color color36 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.05338346F, -0.47058824F, 0);
/* 103 */   private Color color37 = decodeColor("nimbusBlueGrey", 0.0F, -0.049301825F, -0.36078432F, 0);
/* 104 */   private Color color38 = decodeColor("nimbusBlueGrey", -0.018518567F, -0.03909774F, -0.2509804F, 0);
/* 105 */   private Color color39 = decodeColor("nimbusBlueGrey", -0.00505054F, -0.040013492F, -0.13333333F, 0);
/* 106 */   private Color color40 = decodeColor("nimbusBlueGrey", 0.01010108F, -0.039558575F, -0.1372549F, 0);
/* 107 */   private Color color41 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.060526315F, -0.3529412F, 0);
/* 108 */   private Color color42 = decodeColor("nimbusBlueGrey", 0.0F, -0.064372465F, -0.2352941F, 0);
/* 109 */   private Color color43 = decodeColor("nimbusBlueGrey", -0.006944418F, -0.0595709F, -0.12941176F, 0);
/* 110 */   private Color color44 = decodeColor("nimbusBlueGrey", 0.0F, -0.061075766F, -0.031372547F, 0);
/* 111 */   private Color color45 = decodeColor("nimbusBlueGrey", 0.0F, -0.06080256F, -0.035294116F, 0);
/* 112 */   private Color color46 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -220);
/* 113 */   private Color color47 = decodeColor("nimbusBlueGrey", 0.0F, -0.066408664F, 0.054901958F, 0);
/* 114 */   private Color color48 = decodeColor("nimbusBlueGrey", 0.0F, -0.06807348F, 0.086274505F, 0);
/* 115 */   private Color color49 = decodeColor("nimbusBlueGrey", 0.0F, -0.06924191F, 0.109803915F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ToggleButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 123 */     this.state = paramInt;
/* 124 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 130 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 133 */     switch (this.state) { case 1:
/* 134 */         paintBackgroundDisabled(paramGraphics2D); break;
/* 135 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/* 136 */       case 3: paintBackgroundFocused(paramGraphics2D); break;
/* 137 */       case 4: paintBackgroundMouseOver(paramGraphics2D); break;
/* 138 */       case 5: paintBackgroundMouseOverAndFocused(paramGraphics2D); break;
/* 139 */       case 6: paintBackgroundPressed(paramGraphics2D); break;
/* 140 */       case 7: paintBackgroundPressedAndFocused(paramGraphics2D); break;
/* 141 */       case 8: paintBackgroundSelected(paramGraphics2D); break;
/* 142 */       case 9: paintBackgroundSelectedAndFocused(paramGraphics2D); break;
/* 143 */       case 10: paintBackgroundPressedAndSelected(paramGraphics2D); break;
/* 144 */       case 11: paintBackgroundPressedAndSelectedAndFocused(paramGraphics2D); break;
/* 145 */       case 12: paintBackgroundMouseOverAndSelected(paramGraphics2D); break;
/* 146 */       case 13: paintBackgroundMouseOverAndSelectedAndFocused(paramGraphics2D); break;
/* 147 */       case 14: paintBackgroundDisabledAndSelected(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   protected Object[] getExtendedCacheKeys(JComponent paramJComponent) {
/* 153 */     Object[] arrayOfObject = null;
/* 154 */     switch (this.state) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 161 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color12, -0.10655806F, 0.24313724F, 0), getComponentColor(paramJComponent, "background", this.color13, -0.09823123F, 0.2117647F, 0), getComponentColor(paramJComponent, "background", this.color6, -0.07016757F, 0.12941176F, 0), getComponentColor(paramJComponent, "background", this.color14, -0.0749532F, 0.24705881F, 0), getComponentColor(paramJComponent, "background", this.color15, -0.110526316F, 0.25490195F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 169 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color12, -0.10655806F, 0.24313724F, 0), getComponentColor(paramJComponent, "background", this.color13, -0.09823123F, 0.2117647F, 0), getComponentColor(paramJComponent, "background", this.color6, -0.07016757F, 0.12941176F, 0), getComponentColor(paramJComponent, "background", this.color14, -0.0749532F, 0.24705881F, 0), getComponentColor(paramJComponent, "background", this.color15, -0.110526316F, 0.25490195F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 176 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color19, -0.10658931F, 0.25098038F, 0), getComponentColor(paramJComponent, "background", this.color20, -0.098526314F, 0.2352941F, 0), getComponentColor(paramJComponent, "background", this.color21, -0.07333623F, 0.20392156F, 0), getComponentColor(paramJComponent, "background", this.color15, -0.110526316F, 0.25490195F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 183 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color19, -0.10658931F, 0.25098038F, 0), getComponentColor(paramJComponent, "background", this.color20, -0.098526314F, 0.2352941F, 0), getComponentColor(paramJComponent, "background", this.color21, -0.07333623F, 0.20392156F, 0), getComponentColor(paramJComponent, "background", this.color15, -0.110526316F, 0.25490195F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 191 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color25, -0.05960039F, 0.10196078F, 0), getComponentColor(paramJComponent, "background", this.color26, -0.04772438F, 0.06666666F, 0), getComponentColor(paramJComponent, "background", this.color27, -0.0018306673F, -0.02352941F, 0), getComponentColor(paramJComponent, "background", this.color28, -0.0212406F, 0.13333333F, 0), getComponentColor(paramJComponent, "background", this.color29, -0.030845039F, 0.23921567F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 7:
/* 199 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color25, -0.05960039F, 0.10196078F, 0), getComponentColor(paramJComponent, "background", this.color26, -0.04772438F, 0.06666666F, 0), getComponentColor(paramJComponent, "background", this.color27, -0.0018306673F, -0.02352941F, 0), getComponentColor(paramJComponent, "background", this.color28, -0.0212406F, 0.13333333F, 0), getComponentColor(paramJComponent, "background", this.color29, -0.030845039F, 0.23921567F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 205 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color33, -0.06401469F, -0.07058823F, 0), getComponentColor(paramJComponent, "background", this.color34, -0.06530018F, 0.035294116F, 0), getComponentColor(paramJComponent, "background", this.color35, -0.06507177F, 0.031372547F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 9:
/* 211 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color33, -0.06401469F, -0.07058823F, 0), getComponentColor(paramJComponent, "background", this.color34, -0.06530018F, 0.035294116F, 0), getComponentColor(paramJComponent, "background", this.color35, -0.06507177F, 0.031372547F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 217 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color38, -0.03909774F, -0.2509804F, 0), getComponentColor(paramJComponent, "background", this.color39, -0.040013492F, -0.13333333F, 0), getComponentColor(paramJComponent, "background", this.color40, -0.039558575F, -0.1372549F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 11:
/* 223 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color38, -0.03909774F, -0.2509804F, 0), getComponentColor(paramJComponent, "background", this.color39, -0.040013492F, -0.13333333F, 0), getComponentColor(paramJComponent, "background", this.color40, -0.039558575F, -0.1372549F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 12:
/* 229 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color43, -0.0595709F, -0.12941176F, 0), getComponentColor(paramJComponent, "background", this.color44, -0.061075766F, -0.031372547F, 0), getComponentColor(paramJComponent, "background", this.color45, -0.06080256F, -0.035294116F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 13:
/* 235 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color43, -0.0595709F, -0.12941176F, 0), getComponentColor(paramJComponent, "background", this.color44, -0.061075766F, -0.031372547F, 0), getComponentColor(paramJComponent, "background", this.color45, -0.06080256F, -0.035294116F, 0) };
/*     */         break;
/*     */     } 
/* 238 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 243 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 247 */     this.roundRect = decodeRoundRect1();
/* 248 */     paramGraphics2D.setPaint(this.color1);
/* 249 */     paramGraphics2D.fill(this.roundRect);
/* 250 */     this.roundRect = decodeRoundRect2();
/* 251 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/* 252 */     paramGraphics2D.fill(this.roundRect);
/* 253 */     this.roundRect = decodeRoundRect3();
/* 254 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/* 255 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 260 */     this.roundRect = decodeRoundRect1();
/* 261 */     paramGraphics2D.setPaint(this.color9);
/* 262 */     paramGraphics2D.fill(this.roundRect);
/* 263 */     this.roundRect = decodeRoundRect2();
/* 264 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 265 */     paramGraphics2D.fill(this.roundRect);
/* 266 */     this.roundRect = decodeRoundRect3();
/* 267 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 268 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocused(Graphics2D paramGraphics2D) {
/* 273 */     this.roundRect = decodeRoundRect4();
/* 274 */     paramGraphics2D.setPaint(this.color16);
/* 275 */     paramGraphics2D.fill(this.roundRect);
/* 276 */     this.roundRect = decodeRoundRect2();
/* 277 */     paramGraphics2D.setPaint(decodeGradient3(this.roundRect));
/* 278 */     paramGraphics2D.fill(this.roundRect);
/* 279 */     this.roundRect = decodeRoundRect3();
/* 280 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/* 281 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 286 */     this.roundRect = decodeRoundRect1();
/* 287 */     paramGraphics2D.setPaint(this.color9);
/* 288 */     paramGraphics2D.fill(this.roundRect);
/* 289 */     this.roundRect = decodeRoundRect2();
/* 290 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 291 */     paramGraphics2D.fill(this.roundRect);
/* 292 */     this.roundRect = decodeRoundRect3();
/* 293 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 294 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndFocused(Graphics2D paramGraphics2D) {
/* 299 */     this.roundRect = decodeRoundRect4();
/* 300 */     paramGraphics2D.setPaint(this.color16);
/* 301 */     paramGraphics2D.fill(this.roundRect);
/* 302 */     this.roundRect = decodeRoundRect2();
/* 303 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/* 304 */     paramGraphics2D.fill(this.roundRect);
/* 305 */     this.roundRect = decodeRoundRect3();
/* 306 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/* 307 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 312 */     this.roundRect = decodeRoundRect1();
/* 313 */     paramGraphics2D.setPaint(this.color22);
/* 314 */     paramGraphics2D.fill(this.roundRect);
/* 315 */     this.roundRect = decodeRoundRect2();
/* 316 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 317 */     paramGraphics2D.fill(this.roundRect);
/* 318 */     this.roundRect = decodeRoundRect3();
/* 319 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 320 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndFocused(Graphics2D paramGraphics2D) {
/* 325 */     this.roundRect = decodeRoundRect4();
/* 326 */     paramGraphics2D.setPaint(this.color16);
/* 327 */     paramGraphics2D.fill(this.roundRect);
/* 328 */     this.roundRect = decodeRoundRect2();
/* 329 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/* 330 */     paramGraphics2D.fill(this.roundRect);
/* 331 */     this.roundRect = decodeRoundRect3();
/* 332 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/* 333 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelected(Graphics2D paramGraphics2D) {
/* 338 */     this.roundRect = decodeRoundRect5();
/* 339 */     paramGraphics2D.setPaint(this.color30);
/* 340 */     paramGraphics2D.fill(this.roundRect);
/* 341 */     this.roundRect = decodeRoundRect2();
/* 342 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 343 */     paramGraphics2D.fill(this.roundRect);
/* 344 */     this.roundRect = decodeRoundRect3();
/* 345 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 346 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndFocused(Graphics2D paramGraphics2D) {
/* 351 */     this.roundRect = decodeRoundRect6();
/* 352 */     paramGraphics2D.setPaint(this.color16);
/* 353 */     paramGraphics2D.fill(this.roundRect);
/* 354 */     this.roundRect = decodeRoundRect2();
/* 355 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/* 356 */     paramGraphics2D.fill(this.roundRect);
/* 357 */     this.roundRect = decodeRoundRect3();
/* 358 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 359 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndSelected(Graphics2D paramGraphics2D) {
/* 364 */     this.roundRect = decodeRoundRect5();
/* 365 */     paramGraphics2D.setPaint(this.color30);
/* 366 */     paramGraphics2D.fill(this.roundRect);
/* 367 */     this.roundRect = decodeRoundRect2();
/* 368 */     paramGraphics2D.setPaint(decodeGradient11(this.roundRect));
/* 369 */     paramGraphics2D.fill(this.roundRect);
/* 370 */     this.roundRect = decodeRoundRect3();
/* 371 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 372 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndSelectedAndFocused(Graphics2D paramGraphics2D) {
/* 377 */     this.roundRect = decodeRoundRect6();
/* 378 */     paramGraphics2D.setPaint(this.color16);
/* 379 */     paramGraphics2D.fill(this.roundRect);
/* 380 */     this.roundRect = decodeRoundRect2();
/* 381 */     paramGraphics2D.setPaint(decodeGradient11(this.roundRect));
/* 382 */     paramGraphics2D.fill(this.roundRect);
/* 383 */     this.roundRect = decodeRoundRect3();
/* 384 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 385 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndSelected(Graphics2D paramGraphics2D) {
/* 390 */     this.roundRect = decodeRoundRect5();
/* 391 */     paramGraphics2D.setPaint(this.color30);
/* 392 */     paramGraphics2D.fill(this.roundRect);
/* 393 */     this.roundRect = decodeRoundRect2();
/* 394 */     paramGraphics2D.setPaint(decodeGradient12(this.roundRect));
/* 395 */     paramGraphics2D.fill(this.roundRect);
/* 396 */     this.roundRect = decodeRoundRect3();
/* 397 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 398 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndSelectedAndFocused(Graphics2D paramGraphics2D) {
/* 403 */     this.roundRect = decodeRoundRect6();
/* 404 */     paramGraphics2D.setPaint(this.color16);
/* 405 */     paramGraphics2D.fill(this.roundRect);
/* 406 */     this.roundRect = decodeRoundRect2();
/* 407 */     paramGraphics2D.setPaint(decodeGradient12(this.roundRect));
/* 408 */     paramGraphics2D.fill(this.roundRect);
/* 409 */     this.roundRect = decodeRoundRect3();
/* 410 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/* 411 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabledAndSelected(Graphics2D paramGraphics2D) {
/* 416 */     this.roundRect = decodeRoundRect5();
/* 417 */     paramGraphics2D.setPaint(this.color46);
/* 418 */     paramGraphics2D.fill(this.roundRect);
/* 419 */     this.roundRect = decodeRoundRect2();
/* 420 */     paramGraphics2D.setPaint(decodeGradient13(this.roundRect));
/* 421 */     paramGraphics2D.fill(this.roundRect);
/* 422 */     this.roundRect = decodeRoundRect3();
/* 423 */     paramGraphics2D.setPaint(decodeGradient14(this.roundRect));
/* 424 */     paramGraphics2D.fill(this.roundRect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 431 */     this.roundRect.setRoundRect(decodeX(0.2857143F), 
/* 432 */         decodeY(0.42857143F), (
/* 433 */         decodeX(2.7142859F) - decodeX(0.2857143F)), (
/* 434 */         decodeY(2.857143F) - decodeY(0.42857143F)), 12.0D, 12.0D);
/*     */     
/* 436 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 440 */     this.roundRect.setRoundRect(decodeX(0.2857143F), 
/* 441 */         decodeY(0.2857143F), (
/* 442 */         decodeX(2.7142859F) - decodeX(0.2857143F)), (
/* 443 */         decodeY(2.7142859F) - decodeY(0.2857143F)), 9.0D, 9.0D);
/*     */     
/* 445 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect3() {
/* 449 */     this.roundRect.setRoundRect(decodeX(0.42857143F), 
/* 450 */         decodeY(0.42857143F), (
/* 451 */         decodeX(2.5714285F) - decodeX(0.42857143F)), (
/* 452 */         decodeY(2.5714285F) - decodeY(0.42857143F)), 7.0D, 7.0D);
/*     */     
/* 454 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect4() {
/* 458 */     this.roundRect.setRoundRect(decodeX(0.08571429F), 
/* 459 */         decodeY(0.08571429F), (
/* 460 */         decodeX(2.914286F) - decodeX(0.08571429F)), (
/* 461 */         decodeY(2.914286F) - decodeY(0.08571429F)), 11.0D, 11.0D);
/*     */     
/* 463 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect5() {
/* 467 */     this.roundRect.setRoundRect(decodeX(0.2857143F), 
/* 468 */         decodeY(0.42857143F), (
/* 469 */         decodeX(2.7142859F) - decodeX(0.2857143F)), (
/* 470 */         decodeY(2.857143F) - decodeY(0.42857143F)), 9.0D, 9.0D);
/*     */     
/* 472 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect6() {
/* 476 */     this.roundRect.setRoundRect(decodeX(0.08571429F), 
/* 477 */         decodeY(0.08571429F), (
/* 478 */         decodeX(2.914286F) - decodeX(0.08571429F)), (
/* 479 */         decodeY(2.9142857F) - decodeY(0.08571429F)), 11.0D, 11.0D);
/*     */     
/* 481 */     return this.roundRect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 487 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 488 */     float f1 = (float)rectangle2D.getX();
/* 489 */     float f2 = (float)rectangle2D.getY();
/* 490 */     float f3 = (float)rectangle2D.getWidth();
/* 491 */     float f4 = (float)rectangle2D.getHeight();
/* 492 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.09F, 0.52F, 0.95F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 495 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 500 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 501 */     float f1 = (float)rectangle2D.getX();
/* 502 */     float f2 = (float)rectangle2D.getY();
/* 503 */     float f3 = (float)rectangle2D.getWidth();
/* 504 */     float f4 = (float)rectangle2D.getHeight();
/* 505 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.03F, 0.06F, 0.33F, 0.6F, 0.65F, 0.7F, 0.825F, 0.95F, 0.975F, 1.0F }, new Color[] { this.color4, 
/*     */ 
/*     */           
/* 508 */           decodeColor(this.color4, this.color5, 0.5F), this.color5, 
/*     */           
/* 510 */           decodeColor(this.color5, this.color6, 0.5F), this.color6, 
/*     */           
/* 512 */           decodeColor(this.color6, this.color6, 0.5F), this.color6, 
/*     */           
/* 514 */           decodeColor(this.color6, this.color7, 0.5F), this.color7, 
/*     */           
/* 516 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 521 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 522 */     float f1 = (float)rectangle2D.getX();
/* 523 */     float f2 = (float)rectangle2D.getY();
/* 524 */     float f3 = (float)rectangle2D.getWidth();
/* 525 */     float f4 = (float)rectangle2D.getHeight();
/* 526 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.09F, 0.52F, 0.95F }, new Color[] { this.color10, 
/*     */ 
/*     */           
/* 529 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 534 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 535 */     float f1 = (float)rectangle2D.getX();
/* 536 */     float f2 = (float)rectangle2D.getY();
/* 537 */     float f3 = (float)rectangle2D.getWidth();
/* 538 */     float f4 = (float)rectangle2D.getHeight();
/* 539 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.024F, 0.06F, 0.276F, 0.6F, 0.65F, 0.7F, 0.856F, 0.96F, 0.98399997F, 1.0F }, new Color[] { (Color)this.componentColors[0], 
/*     */ 
/*     */           
/* 542 */           decodeColor((Color)this.componentColors[0], (Color)this.componentColors[1], 0.5F), (Color)this.componentColors[1], 
/*     */           
/* 544 */           decodeColor((Color)this.componentColors[1], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2], 
/*     */           
/* 546 */           decodeColor((Color)this.componentColors[2], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2], 
/*     */           
/* 548 */           decodeColor((Color)this.componentColors[2], (Color)this.componentColors[3], 0.5F), (Color)this.componentColors[3], 
/*     */           
/* 550 */           decodeColor((Color)this.componentColors[3], (Color)this.componentColors[4], 0.5F), (Color)this.componentColors[4] });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 555 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 556 */     float f1 = (float)rectangle2D.getX();
/* 557 */     float f2 = (float)rectangle2D.getY();
/* 558 */     float f3 = (float)rectangle2D.getWidth();
/* 559 */     float f4 = (float)rectangle2D.getHeight();
/* 560 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.03F, 0.06F, 0.33F, 0.6F, 0.65F, 0.7F, 0.825F, 0.95F, 0.975F, 1.0F }, new Color[] { (Color)this.componentColors[0], 
/*     */ 
/*     */           
/* 563 */           decodeColor((Color)this.componentColors[0], (Color)this.componentColors[1], 0.5F), (Color)this.componentColors[1], 
/*     */           
/* 565 */           decodeColor((Color)this.componentColors[1], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2], 
/*     */           
/* 567 */           decodeColor((Color)this.componentColors[2], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2], 
/*     */           
/* 569 */           decodeColor((Color)this.componentColors[2], (Color)this.componentColors[3], 0.5F), (Color)this.componentColors[3], 
/*     */           
/* 571 */           decodeColor((Color)this.componentColors[3], (Color)this.componentColors[4], 0.5F), (Color)this.componentColors[4] });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 576 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 577 */     float f1 = (float)rectangle2D.getX();
/* 578 */     float f2 = (float)rectangle2D.getY();
/* 579 */     float f3 = (float)rectangle2D.getWidth();
/* 580 */     float f4 = (float)rectangle2D.getHeight();
/* 581 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.09F, 0.52F, 0.95F }, new Color[] { this.color17, 
/*     */ 
/*     */           
/* 584 */           decodeColor(this.color17, this.color18, 0.5F), this.color18 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 589 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 590 */     float f1 = (float)rectangle2D.getX();
/* 591 */     float f2 = (float)rectangle2D.getY();
/* 592 */     float f3 = (float)rectangle2D.getWidth();
/* 593 */     float f4 = (float)rectangle2D.getHeight();
/* 594 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.024F, 0.06F, 0.276F, 0.6F, 0.65F, 0.7F, 0.856F, 0.96F, 0.98F, 1.0F }, new Color[] { (Color)this.componentColors[0], 
/*     */ 
/*     */           
/* 597 */           decodeColor((Color)this.componentColors[0], (Color)this.componentColors[1], 0.5F), (Color)this.componentColors[1], 
/*     */           
/* 599 */           decodeColor((Color)this.componentColors[1], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2], 
/*     */           
/* 601 */           decodeColor((Color)this.componentColors[2], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2], 
/*     */           
/* 603 */           decodeColor((Color)this.componentColors[2], (Color)this.componentColors[3], 0.5F), (Color)this.componentColors[3], 
/*     */           
/* 605 */           decodeColor((Color)this.componentColors[3], (Color)this.componentColors[3], 0.5F), (Color)this.componentColors[3] });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 610 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 611 */     float f1 = (float)rectangle2D.getX();
/* 612 */     float f2 = (float)rectangle2D.getY();
/* 613 */     float f3 = (float)rectangle2D.getWidth();
/* 614 */     float f4 = (float)rectangle2D.getHeight();
/* 615 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.05F, 0.5F, 0.95F }, new Color[] { this.color23, 
/*     */ 
/*     */           
/* 618 */           decodeColor(this.color23, this.color24, 0.5F), this.color24 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 623 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 624 */     float f1 = (float)rectangle2D.getX();
/* 625 */     float f2 = (float)rectangle2D.getY();
/* 626 */     float f3 = (float)rectangle2D.getWidth();
/* 627 */     float f4 = (float)rectangle2D.getHeight();
/* 628 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color31, 
/*     */ 
/*     */           
/* 631 */           decodeColor(this.color31, this.color32, 0.5F), this.color32 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 636 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 637 */     float f1 = (float)rectangle2D.getX();
/* 638 */     float f2 = (float)rectangle2D.getY();
/* 639 */     float f3 = (float)rectangle2D.getWidth();
/* 640 */     float f4 = (float)rectangle2D.getHeight();
/* 641 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.06684492F, 0.13368984F, 0.56684494F, 1.0F }, new Color[] { (Color)this.componentColors[0], 
/*     */ 
/*     */           
/* 644 */           decodeColor((Color)this.componentColors[0], (Color)this.componentColors[1], 0.5F), (Color)this.componentColors[1], 
/*     */           
/* 646 */           decodeColor((Color)this.componentColors[1], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2] });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient11(Shape paramShape) {
/* 651 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 652 */     float f1 = (float)rectangle2D.getX();
/* 653 */     float f2 = (float)rectangle2D.getY();
/* 654 */     float f3 = (float)rectangle2D.getWidth();
/* 655 */     float f4 = (float)rectangle2D.getHeight();
/* 656 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color36, 
/*     */ 
/*     */           
/* 659 */           decodeColor(this.color36, this.color37, 0.5F), this.color37 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient12(Shape paramShape) {
/* 664 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 665 */     float f1 = (float)rectangle2D.getX();
/* 666 */     float f2 = (float)rectangle2D.getY();
/* 667 */     float f3 = (float)rectangle2D.getWidth();
/* 668 */     float f4 = (float)rectangle2D.getHeight();
/* 669 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color41, 
/*     */ 
/*     */           
/* 672 */           decodeColor(this.color41, this.color42, 0.5F), this.color42 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient13(Shape paramShape) {
/* 677 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 678 */     float f1 = (float)rectangle2D.getX();
/* 679 */     float f2 = (float)rectangle2D.getY();
/* 680 */     float f3 = (float)rectangle2D.getWidth();
/* 681 */     float f4 = (float)rectangle2D.getHeight();
/* 682 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color47, 
/*     */ 
/*     */           
/* 685 */           decodeColor(this.color47, this.color48, 0.5F), this.color48 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient14(Shape paramShape) {
/* 690 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 691 */     float f1 = (float)rectangle2D.getX();
/* 692 */     float f2 = (float)rectangle2D.getY();
/* 693 */     float f3 = (float)rectangle2D.getWidth();
/* 694 */     float f4 = (float)rectangle2D.getHeight();
/* 695 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.06684492F, 0.13368984F, 0.56684494F, 1.0F }, new Color[] { this.color48, 
/*     */ 
/*     */           
/* 698 */           decodeColor(this.color48, this.color49, 0.5F), this.color49, 
/*     */           
/* 700 */           decodeColor(this.color49, this.color49, 0.5F), this.color49 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ToggleButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */