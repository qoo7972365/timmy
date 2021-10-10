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
/*     */ final class ProgressBarPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_ENABLED = 1;
/*     */   static final int BACKGROUND_DISABLED = 2;
/*     */   static final int FOREGROUND_ENABLED = 3;
/*     */   static final int FOREGROUND_ENABLED_FINISHED = 4;
/*     */   static final int FOREGROUND_ENABLED_INDETERMINATE = 5;
/*     */   static final int FOREGROUND_DISABLED = 6;
/*     */   static final int FOREGROUND_DISABLED_FINISHED = 7;
/*     */   static final int FOREGROUND_DISABLED_INDETERMINATE = 8;
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
/*  61 */   private Color color1 = decodeColor("nimbusBlueGrey", 0.0F, -0.04845735F, -0.17647058F, 0);
/*  62 */   private Color color2 = decodeColor("nimbusBlueGrey", 0.0F, -0.061345987F, -0.027450979F, 0);
/*  63 */   private Color color3 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  64 */   private Color color4 = decodeColor("nimbusBlueGrey", 0.0F, -0.097921275F, 0.18823528F, 0);
/*  65 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.0138888955F, -0.0925083F, 0.12549019F, 0);
/*  66 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.0F, -0.08222443F, 0.086274505F, 0);
/*  67 */   private Color color7 = decodeColor("nimbusBlueGrey", 0.0F, -0.08477524F, 0.16862744F, 0);
/*  68 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.0F, -0.086996906F, 0.25490195F, 0);
/*  69 */   private Color color9 = decodeColor("nimbusBlueGrey", 0.0F, -0.061613273F, -0.02352941F, 0);
/*  70 */   private Color color10 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.061265234F, 0.05098039F, 0);
/*  71 */   private Color color11 = decodeColor("nimbusBlueGrey", 0.0138888955F, -0.09378991F, 0.19215685F, 0);
/*  72 */   private Color color12 = decodeColor("nimbusBlueGrey", 0.0F, -0.08455229F, 0.1607843F, 0);
/*  73 */   private Color color13 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.08362049F, 0.12941176F, 0);
/*  74 */   private Color color14 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07826825F, 0.10588235F, 0);
/*  75 */   private Color color15 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07982456F, 0.1490196F, 0);
/*  76 */   private Color color16 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.08099045F, 0.18431371F, 0);
/*  77 */   private Color color17 = decodeColor("nimbusOrange", 0.0F, 0.0F, 0.0F, -156);
/*  78 */   private Color color18 = decodeColor("nimbusOrange", -0.015796512F, 0.02094239F, -0.15294117F, 0);
/*  79 */   private Color color19 = decodeColor("nimbusOrange", -0.004321605F, 0.02094239F, -0.0745098F, 0);
/*  80 */   private Color color20 = decodeColor("nimbusOrange", -0.008021399F, 0.02094239F, -0.10196078F, 0);
/*  81 */   private Color color21 = decodeColor("nimbusOrange", -0.011706904F, -0.1790576F, -0.02352941F, 0);
/*  82 */   private Color color22 = decodeColor("nimbusOrange", -0.048691254F, 0.02094239F, -0.3019608F, 0);
/*  83 */   private Color color23 = decodeColor("nimbusOrange", 0.003940329F, -0.7375322F, 0.17647058F, 0);
/*  84 */   private Color color24 = decodeColor("nimbusOrange", 0.005506739F, -0.46764207F, 0.109803915F, 0);
/*  85 */   private Color color25 = decodeColor("nimbusOrange", 0.0042127445F, -0.18595415F, 0.04705882F, 0);
/*  86 */   private Color color26 = decodeColor("nimbusOrange", 0.0047626942F, 0.02094239F, 0.0039215684F, 0);
/*  87 */   private Color color27 = decodeColor("nimbusOrange", 0.0047626942F, -0.15147138F, 0.1607843F, 0);
/*  88 */   private Color color28 = decodeColor("nimbusOrange", 0.010665476F, -0.27317524F, 0.25098038F, 0);
/*  89 */   private Color color29 = decodeColor("nimbusBlueGrey", -0.54444444F, -0.08748484F, 0.10588235F, 0);
/*  90 */   private Color color30 = decodeColor("nimbusOrange", 0.0047626942F, -0.21715283F, 0.23921567F, 0);
/*  91 */   private Color color31 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -173);
/*  92 */   private Color color32 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -170);
/*  93 */   private Color color33 = decodeColor("nimbusOrange", 0.024554357F, -0.8873145F, 0.10588235F, -156);
/*  94 */   private Color color34 = decodeColor("nimbusOrange", -0.023593787F, -0.7963165F, 0.02352941F, 0);
/*  95 */   private Color color35 = decodeColor("nimbusOrange", -0.010608241F, -0.7760873F, 0.043137252F, 0);
/*  96 */   private Color color36 = decodeColor("nimbusOrange", -0.015402906F, -0.7840576F, 0.035294116F, 0);
/*  97 */   private Color color37 = decodeColor("nimbusOrange", -0.017112307F, -0.8091547F, 0.058823526F, 0);
/*  98 */   private Color color38 = decodeColor("nimbusOrange", -0.07044564F, -0.844649F, -0.019607842F, 0);
/*  99 */   private Color color39 = decodeColor("nimbusOrange", -0.009704903F, -0.9381485F, 0.11372548F, 0);
/* 100 */   private Color color40 = decodeColor("nimbusOrange", -4.4563413E-4F, -0.86742973F, 0.09411764F, 0);
/* 101 */   private Color color41 = decodeColor("nimbusOrange", -4.4563413E-4F, -0.79896283F, 0.07843137F, 0);
/* 102 */   private Color color42 = decodeColor("nimbusOrange", 0.0013274103F, -0.7530961F, 0.06666666F, 0);
/* 103 */   private Color color43 = decodeColor("nimbusOrange", 0.0013274103F, -0.7644457F, 0.109803915F, 0);
/* 104 */   private Color color44 = decodeColor("nimbusOrange", 0.009244293F, -0.78794646F, 0.13333333F, 0);
/* 105 */   private Color color45 = decodeColor("nimbusBlueGrey", -0.015872955F, -0.0803539F, 0.16470587F, 0);
/* 106 */   private Color color46 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07968931F, 0.14509803F, 0);
/* 107 */   private Color color47 = decodeColor("nimbusBlueGrey", 0.02222228F, -0.08779904F, 0.11764705F, 0);
/* 108 */   private Color color48 = decodeColor("nimbusBlueGrey", 0.0138888955F, -0.075128086F, 0.14117646F, 0);
/* 109 */   private Color color49 = decodeColor("nimbusBlueGrey", 0.0138888955F, -0.07604356F, 0.16470587F, 0);
/* 110 */   private Color color50 = decodeColor("nimbusOrange", 0.0014062226F, -0.77816474F, 0.12941176F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ProgressBarPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 118 */     this.state = paramInt;
/* 119 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 125 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 128 */     switch (this.state) { case 1:
/* 129 */         paintBackgroundEnabled(paramGraphics2D); break;
/* 130 */       case 2: paintBackgroundDisabled(paramGraphics2D); break;
/* 131 */       case 3: paintForegroundEnabled(paramGraphics2D); break;
/* 132 */       case 4: paintForegroundEnabledAndFinished(paramGraphics2D); break;
/* 133 */       case 5: paintForegroundEnabledAndIndeterminate(paramGraphics2D); break;
/* 134 */       case 6: paintForegroundDisabled(paramGraphics2D); break;
/* 135 */       case 7: paintForegroundDisabledAndFinished(paramGraphics2D); break;
/* 136 */       case 8: paintForegroundDisabledAndIndeterminate(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 145 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 149 */     this.rect = decodeRect1();
/* 150 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 151 */     paramGraphics2D.fill(this.rect);
/* 152 */     this.rect = decodeRect2();
/* 153 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 154 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 159 */     this.rect = decodeRect1();
/* 160 */     paramGraphics2D.setPaint(decodeGradient3(this.rect));
/* 161 */     paramGraphics2D.fill(this.rect);
/* 162 */     this.rect = decodeRect2();
/* 163 */     paramGraphics2D.setPaint(decodeGradient4(this.rect));
/* 164 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundEnabled(Graphics2D paramGraphics2D) {
/* 169 */     this.path = decodePath1();
/* 170 */     paramGraphics2D.setPaint(this.color17);
/* 171 */     paramGraphics2D.fill(this.path);
/* 172 */     this.rect = decodeRect3();
/* 173 */     paramGraphics2D.setPaint(decodeGradient5(this.rect));
/* 174 */     paramGraphics2D.fill(this.rect);
/* 175 */     this.rect = decodeRect4();
/* 176 */     paramGraphics2D.setPaint(decodeGradient6(this.rect));
/* 177 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundEnabledAndFinished(Graphics2D paramGraphics2D) {
/* 182 */     this.path = decodePath2();
/* 183 */     paramGraphics2D.setPaint(this.color17);
/* 184 */     paramGraphics2D.fill(this.path);
/* 185 */     this.rect = decodeRect5();
/* 186 */     paramGraphics2D.setPaint(decodeGradient5(this.rect));
/* 187 */     paramGraphics2D.fill(this.rect);
/* 188 */     this.rect = decodeRect6();
/* 189 */     paramGraphics2D.setPaint(decodeGradient6(this.rect));
/* 190 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundEnabledAndIndeterminate(Graphics2D paramGraphics2D) {
/* 195 */     this.rect = decodeRect7();
/* 196 */     paramGraphics2D.setPaint(decodeGradient7(this.rect));
/* 197 */     paramGraphics2D.fill(this.rect);
/* 198 */     this.path = decodePath3();
/* 199 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 200 */     paramGraphics2D.fill(this.path);
/* 201 */     this.rect = decodeRect8();
/* 202 */     paramGraphics2D.setPaint(this.color31);
/* 203 */     paramGraphics2D.fill(this.rect);
/* 204 */     this.rect = decodeRect9();
/* 205 */     paramGraphics2D.setPaint(this.color32);
/* 206 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundDisabled(Graphics2D paramGraphics2D) {
/* 211 */     this.path = decodePath1();
/* 212 */     paramGraphics2D.setPaint(this.color33);
/* 213 */     paramGraphics2D.fill(this.path);
/* 214 */     this.rect = decodeRect3();
/* 215 */     paramGraphics2D.setPaint(decodeGradient9(this.rect));
/* 216 */     paramGraphics2D.fill(this.rect);
/* 217 */     this.rect = decodeRect4();
/* 218 */     paramGraphics2D.setPaint(decodeGradient10(this.rect));
/* 219 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundDisabledAndFinished(Graphics2D paramGraphics2D) {
/* 224 */     this.path = decodePath4();
/* 225 */     paramGraphics2D.setPaint(this.color33);
/* 226 */     paramGraphics2D.fill(this.path);
/* 227 */     this.rect = decodeRect5();
/* 228 */     paramGraphics2D.setPaint(decodeGradient9(this.rect));
/* 229 */     paramGraphics2D.fill(this.rect);
/* 230 */     this.rect = decodeRect6();
/* 231 */     paramGraphics2D.setPaint(decodeGradient10(this.rect));
/* 232 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundDisabledAndIndeterminate(Graphics2D paramGraphics2D) {
/* 237 */     this.rect = decodeRect7();
/* 238 */     paramGraphics2D.setPaint(decodeGradient11(this.rect));
/* 239 */     paramGraphics2D.fill(this.rect);
/* 240 */     this.path = decodePath5();
/* 241 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/* 242 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 249 */     this.rect.setRect(decodeX(0.4F), 
/* 250 */         decodeY(0.4F), (
/* 251 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 252 */         decodeY(2.6F) - decodeY(0.4F)));
/* 253 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 257 */     this.rect.setRect(decodeX(0.6F), 
/* 258 */         decodeY(0.6F), (
/* 259 */         decodeX(2.4F) - decodeX(0.6F)), (
/* 260 */         decodeY(2.4F) - decodeY(0.6F)));
/* 261 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 265 */     this.path.reset();
/* 266 */     this.path.moveTo(decodeX(1.0F), decodeY(0.21111111F));
/* 267 */     this.path.curveTo(decodeAnchorX(1.0F, -2.0F), decodeAnchorY(0.21111111F, 0.0F), decodeAnchorX(0.21111111F, 0.0F), decodeAnchorY(1.0F, -2.0F), decodeX(0.21111111F), decodeY(1.0F));
/* 268 */     this.path.curveTo(decodeAnchorX(0.21111111F, 0.0F), decodeAnchorY(1.0F, 2.0F), decodeAnchorX(0.21111111F, 0.0F), decodeAnchorY(2.0F, -2.0F), decodeX(0.21111111F), decodeY(2.0F));
/* 269 */     this.path.curveTo(decodeAnchorX(0.21111111F, 0.0F), decodeAnchorY(2.0F, 2.0F), decodeAnchorX(1.0F, -2.0F), decodeAnchorY(2.8222225F, 0.0F), decodeX(1.0F), decodeY(2.8222225F));
/* 270 */     this.path.curveTo(decodeAnchorX(1.0F, 2.0F), decodeAnchorY(2.8222225F, 0.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(2.8222225F, 0.0F), decodeX(3.0F), decodeY(2.8222225F));
/* 271 */     this.path.lineTo(decodeX(3.0F), decodeY(2.3333333F));
/* 272 */     this.path.lineTo(decodeX(0.6666667F), decodeY(2.3333333F));
/* 273 */     this.path.lineTo(decodeX(0.6666667F), decodeY(0.6666667F));
/* 274 */     this.path.lineTo(decodeX(3.0F), decodeY(0.6666667F));
/* 275 */     this.path.lineTo(decodeX(3.0F), decodeY(0.2F));
/* 276 */     this.path.curveTo(decodeAnchorX(3.0F, 0.0F), decodeAnchorY(0.2F, 0.0F), decodeAnchorX(1.0F, 2.0F), decodeAnchorY(0.21111111F, 0.0F), decodeX(1.0F), decodeY(0.21111111F));
/* 277 */     this.path.closePath();
/* 278 */     return this.path;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 282 */     this.rect.setRect(decodeX(0.6666667F), 
/* 283 */         decodeY(0.6666667F), (
/* 284 */         decodeX(3.0F) - decodeX(0.6666667F)), (
/* 285 */         decodeY(2.3333333F) - decodeY(0.6666667F)));
/* 286 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect4() {
/* 290 */     this.rect.setRect(decodeX(1.0F), 
/* 291 */         decodeY(1.0F), (
/* 292 */         decodeX(2.6666667F) - decodeX(1.0F)), (
/* 293 */         decodeY(2.0F) - decodeY(1.0F)));
/* 294 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 298 */     this.path.reset();
/* 299 */     this.path.moveTo(decodeX(0.9111111F), decodeY(0.21111111F));
/* 300 */     this.path.curveTo(decodeAnchorX(0.9111111F, -2.0F), decodeAnchorY(0.21111111F, 0.0F), decodeAnchorX(0.2F, 0.0F), decodeAnchorY(1.0025641F, -2.0F), decodeX(0.2F), decodeY(1.0025641F));
/* 301 */     this.path.lineTo(decodeX(0.2F), decodeY(2.0444443F));
/* 302 */     this.path.curveTo(decodeAnchorX(0.2F, 0.0F), decodeAnchorY(2.0444443F, 2.0F), decodeAnchorX(0.9666667F, -2.0F), decodeAnchorY(2.8F, 0.0F), decodeX(0.9666667F), decodeY(2.8F));
/* 303 */     this.path.lineTo(decodeX(2.0F), decodeY(2.788889F));
/* 304 */     this.path.curveTo(decodeAnchorX(2.0F, 1.9709293F), decodeAnchorY(2.788889F, 0.01985704F), decodeAnchorX(2.777778F, -0.033333335F), decodeAnchorY(2.0555553F, 1.9333333F), decodeX(2.777778F), decodeY(2.0555553F));
/* 305 */     this.path.lineTo(decodeX(2.788889F), decodeY(1.8051281F));
/* 306 */     this.path.lineTo(decodeX(2.777778F), decodeY(1.2794871F));
/* 307 */     this.path.lineTo(decodeX(2.777778F), decodeY(1.0025641F));
/* 308 */     this.path.curveTo(decodeAnchorX(2.777778F, 0.0042173304F), decodeAnchorY(1.0025641F, -1.9503378F), decodeAnchorX(2.0999997F, 1.9659461F), decodeAnchorY(0.22222222F, 0.017122267F), decodeX(2.0999997F), decodeY(0.22222222F));
/* 309 */     this.path.lineTo(decodeX(0.9111111F), decodeY(0.21111111F));
/* 310 */     this.path.closePath();
/* 311 */     return this.path;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect5() {
/* 315 */     this.rect.setRect(decodeX(0.6666667F), 
/* 316 */         decodeY(0.6666667F), (
/* 317 */         decodeX(2.3333333F) - decodeX(0.6666667F)), (
/* 318 */         decodeY(2.3333333F) - decodeY(0.6666667F)));
/* 319 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect6() {
/* 323 */     this.rect.setRect(decodeX(1.0F), 
/* 324 */         decodeY(1.0F), (
/* 325 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 326 */         decodeY(2.0F) - decodeY(1.0F)));
/* 327 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect7() {
/* 331 */     this.rect.setRect(decodeX(0.0F), 
/* 332 */         decodeY(0.0F), (
/* 333 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 334 */         decodeY(3.0F) - decodeY(0.0F)));
/* 335 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 339 */     this.path.reset();
/* 340 */     this.path.moveTo(decodeX(0.0F), decodeY(1.4285715F));
/* 341 */     this.path.curveTo(decodeAnchorX(0.0F, 2.6785715F), decodeAnchorY(1.4285715F, 8.881784E-16F), decodeAnchorX(1.3898809F, -6.214286F), decodeAnchorY(0.3452381F, -0.035714287F), decodeX(1.3898809F), decodeY(0.3452381F));
/* 342 */     this.path.lineTo(decodeX(1.5535715F), decodeY(0.3452381F));
/* 343 */     this.path.curveTo(decodeAnchorX(1.5535715F, 8.32967F), decodeAnchorY(0.3452381F, 0.0027472528F), decodeAnchorX(2.3333333F, -5.285714F), decodeAnchorY(1.4285715F, 0.035714287F), decodeX(2.3333333F), decodeY(1.4285715F));
/* 344 */     this.path.lineTo(decodeX(3.0F), decodeY(1.4285715F));
/* 345 */     this.path.lineTo(decodeX(3.0F), decodeY(1.5714285F));
/* 346 */     this.path.lineTo(decodeX(2.3333333F), decodeY(1.5714285F));
/* 347 */     this.path.curveTo(decodeAnchorX(2.3333333F, -5.321429F), decodeAnchorY(1.5714285F, 0.035714287F), decodeAnchorX(1.5535715F, 8.983517F), decodeAnchorY(2.6666667F, 0.03846154F), decodeX(1.5535715F), decodeY(2.6666667F));
/* 348 */     this.path.lineTo(decodeX(1.4077381F), decodeY(2.6666667F));
/* 349 */     this.path.curveTo(decodeAnchorX(1.4077381F, -6.714286F), decodeAnchorY(2.6666667F, 0.0F), decodeAnchorX(0.0F, 2.607143F), decodeAnchorY(1.5714285F, 0.035714287F), decodeX(0.0F), decodeY(1.5714285F));
/* 350 */     this.path.lineTo(decodeX(0.0F), decodeY(1.4285715F));
/* 351 */     this.path.closePath();
/* 352 */     return this.path;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect8() {
/* 356 */     this.rect.setRect(decodeX(1.2916666F), 
/* 357 */         decodeY(0.0F), (
/* 358 */         decodeX(1.3333334F) - decodeX(1.2916666F)), (
/* 359 */         decodeY(3.0F) - decodeY(0.0F)));
/* 360 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect9() {
/* 364 */     this.rect.setRect(decodeX(1.7083333F), 
/* 365 */         decodeY(0.0F), (
/* 366 */         decodeX(1.75F) - decodeX(1.7083333F)), (
/* 367 */         decodeY(3.0F) - decodeY(0.0F)));
/* 368 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 372 */     this.path.reset();
/* 373 */     this.path.moveTo(decodeX(0.9888889F), decodeY(0.2F));
/* 374 */     this.path.curveTo(decodeAnchorX(0.9888889F, -2.0F), decodeAnchorY(0.2F, 0.0F), decodeAnchorX(0.2F, 0.0F), decodeAnchorY(0.9888889F, -2.0F), decodeX(0.2F), decodeY(0.9888889F));
/* 375 */     this.path.curveTo(decodeAnchorX(0.2F, 0.0F), decodeAnchorY(0.9888889F, 2.0F), decodeAnchorX(0.2F, 0.0F), decodeAnchorY(1.9974358F, -2.0F), decodeX(0.2F), decodeY(1.9974358F));
/* 376 */     this.path.curveTo(decodeAnchorX(0.2F, 0.0F), decodeAnchorY(1.9974358F, 2.0F), decodeAnchorX(0.9888889F, -2.0F), decodeAnchorY(2.8111107F, 0.0F), decodeX(0.9888889F), decodeY(2.8111107F));
/* 377 */     this.path.curveTo(decodeAnchorX(0.9888889F, 2.0F), decodeAnchorY(2.8111107F, 0.0F), decodeAnchorX(2.5F, 0.0F), decodeAnchorY(2.8F, 0.0F), decodeX(2.5F), decodeY(2.8F));
/* 378 */     this.path.lineTo(decodeX(2.7444446F), decodeY(2.488889F));
/* 379 */     this.path.lineTo(decodeX(2.7555554F), decodeY(1.5794872F));
/* 380 */     this.path.lineTo(decodeX(2.7666664F), decodeY(1.4358975F));
/* 381 */     this.path.lineTo(decodeX(2.7666664F), decodeY(0.62222224F));
/* 382 */     this.path.lineTo(decodeX(2.5999997F), decodeY(0.22222222F));
/* 383 */     this.path.curveTo(decodeAnchorX(2.5999997F, 0.0F), decodeAnchorY(0.22222222F, 0.0F), decodeAnchorX(0.9888889F, 2.0F), decodeAnchorY(0.2F, 0.0F), decodeX(0.9888889F), decodeY(0.2F));
/* 384 */     this.path.closePath();
/* 385 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 389 */     this.path.reset();
/* 390 */     this.path.moveTo(decodeX(0.0F), decodeY(1.4285715F));
/* 391 */     this.path.curveTo(decodeAnchorX(0.0F, 2.6785715F), decodeAnchorY(1.4285715F, 8.881784E-16F), decodeAnchorX(1.3898809F, -6.357143F), decodeAnchorY(0.3452381F, -0.035714287F), decodeX(1.3898809F), decodeY(0.3452381F));
/* 392 */     this.path.lineTo(decodeX(1.5535715F), decodeY(0.3452381F));
/* 393 */     this.path.curveTo(decodeAnchorX(1.5535715F, 4.0F), decodeAnchorY(0.3452381F, 0.0F), decodeAnchorX(2.3333333F, -5.285714F), decodeAnchorY(1.4285715F, 0.035714287F), decodeX(2.3333333F), decodeY(1.4285715F));
/* 394 */     this.path.lineTo(decodeX(3.0F), decodeY(1.4285715F));
/* 395 */     this.path.lineTo(decodeX(3.0F), decodeY(1.5714285F));
/* 396 */     this.path.lineTo(decodeX(2.3333333F), decodeY(1.5714285F));
/* 397 */     this.path.curveTo(decodeAnchorX(2.3333333F, -5.321429F), decodeAnchorY(1.5714285F, 0.035714287F), decodeAnchorX(1.5535715F, 4.0F), decodeAnchorY(2.6666667F, 0.0F), decodeX(1.5535715F), decodeY(2.6666667F));
/* 398 */     this.path.lineTo(decodeX(1.4077381F), decodeY(2.6666667F));
/* 399 */     this.path.curveTo(decodeAnchorX(1.4077381F, -6.571429F), decodeAnchorY(2.6666667F, -0.035714287F), decodeAnchorX(0.0F, 2.607143F), decodeAnchorY(1.5714285F, 0.035714287F), decodeX(0.0F), decodeY(1.5714285F));
/* 400 */     this.path.lineTo(decodeX(0.0F), decodeY(1.4285715F));
/* 401 */     this.path.closePath();
/* 402 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 408 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 409 */     float f1 = (float)rectangle2D.getX();
/* 410 */     float f2 = (float)rectangle2D.getY();
/* 411 */     float f3 = (float)rectangle2D.getWidth();
/* 412 */     float f4 = (float)rectangle2D.getHeight();
/* 413 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 416 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 421 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 422 */     float f1 = (float)rectangle2D.getX();
/* 423 */     float f2 = (float)rectangle2D.getY();
/* 424 */     float f3 = (float)rectangle2D.getWidth();
/* 425 */     float f4 = (float)rectangle2D.getHeight();
/* 426 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038709678F, 0.05967742F, 0.08064516F, 0.23709677F, 0.3935484F, 0.41612905F, 0.43870968F, 0.67419356F, 0.90967745F, 0.91451615F, 0.91935486F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 429 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 431 */           decodeColor(this.color4, this.color5, 0.5F), this.color5, 
/*     */           
/* 433 */           decodeColor(this.color5, this.color6, 0.5F), this.color6, 
/*     */           
/* 435 */           decodeColor(this.color6, this.color7, 0.5F), this.color7, 
/*     */           
/* 437 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 442 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 443 */     float f1 = (float)rectangle2D.getX();
/* 444 */     float f2 = (float)rectangle2D.getY();
/* 445 */     float f3 = (float)rectangle2D.getWidth();
/* 446 */     float f4 = (float)rectangle2D.getHeight();
/* 447 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.05483871F, 0.5032258F, 0.9516129F }, new Color[] { this.color9, 
/*     */ 
/*     */           
/* 450 */           decodeColor(this.color9, this.color10, 0.5F), this.color10 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 455 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 456 */     float f1 = (float)rectangle2D.getX();
/* 457 */     float f2 = (float)rectangle2D.getY();
/* 458 */     float f3 = (float)rectangle2D.getWidth();
/* 459 */     float f4 = (float)rectangle2D.getHeight();
/* 460 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038709678F, 0.05967742F, 0.08064516F, 0.23709677F, 0.3935484F, 0.41612905F, 0.43870968F, 0.67419356F, 0.90967745F, 0.91612905F, 0.92258066F }, new Color[] { this.color11, 
/*     */ 
/*     */           
/* 463 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*     */           
/* 465 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*     */           
/* 467 */           decodeColor(this.color13, this.color14, 0.5F), this.color14, 
/*     */           
/* 469 */           decodeColor(this.color14, this.color15, 0.5F), this.color15, 
/*     */           
/* 471 */           decodeColor(this.color15, this.color16, 0.5F), this.color16 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 476 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 477 */     float f1 = (float)rectangle2D.getX();
/* 478 */     float f2 = (float)rectangle2D.getY();
/* 479 */     float f3 = (float)rectangle2D.getWidth();
/* 480 */     float f4 = (float)rectangle2D.getHeight();
/* 481 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038709678F, 0.05483871F, 0.07096774F, 0.28064516F, 0.4903226F, 0.6967742F, 0.9032258F, 0.9241935F, 0.9451613F }, new Color[] { this.color18, 
/*     */ 
/*     */           
/* 484 */           decodeColor(this.color18, this.color19, 0.5F), this.color19, 
/*     */           
/* 486 */           decodeColor(this.color19, this.color20, 0.5F), this.color20, 
/*     */           
/* 488 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 490 */           decodeColor(this.color21, this.color22, 0.5F), this.color22 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 495 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 496 */     float f1 = (float)rectangle2D.getX();
/* 497 */     float f2 = (float)rectangle2D.getY();
/* 498 */     float f3 = (float)rectangle2D.getWidth();
/* 499 */     float f4 = (float)rectangle2D.getHeight();
/* 500 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038709678F, 0.061290324F, 0.08387097F, 0.27258065F, 0.46129033F, 0.4903226F, 0.5193548F, 0.71774197F, 0.91612905F, 0.92419356F, 0.93225807F }, new Color[] { this.color23, 
/*     */ 
/*     */           
/* 503 */           decodeColor(this.color23, this.color24, 0.5F), this.color24, 
/*     */           
/* 505 */           decodeColor(this.color24, this.color25, 0.5F), this.color25, 
/*     */           
/* 507 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*     */           
/* 509 */           decodeColor(this.color26, this.color27, 0.5F), this.color27, 
/*     */           
/* 511 */           decodeColor(this.color27, this.color28, 0.5F), this.color28 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 516 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 517 */     float f1 = (float)rectangle2D.getX();
/* 518 */     float f2 = (float)rectangle2D.getY();
/* 519 */     float f3 = (float)rectangle2D.getWidth();
/* 520 */     float f4 = (float)rectangle2D.getHeight();
/* 521 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.051612902F, 0.06612903F, 0.08064516F, 0.2935484F, 0.5064516F, 0.6903226F, 0.87419355F, 0.88870966F, 0.9032258F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 524 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 526 */           decodeColor(this.color4, this.color29, 0.5F), this.color29, 
/*     */           
/* 528 */           decodeColor(this.color29, this.color7, 0.5F), this.color7, 
/*     */           
/* 530 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 535 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 536 */     float f1 = (float)rectangle2D.getX();
/* 537 */     float f2 = (float)rectangle2D.getY();
/* 538 */     float f3 = (float)rectangle2D.getWidth();
/* 539 */     float f4 = (float)rectangle2D.getHeight();
/* 540 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.20645161F, 0.41290322F, 0.44193548F, 0.47096774F, 0.7354839F, 1.0F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 543 */           decodeColor(this.color24, this.color25, 0.5F), this.color25, 
/*     */           
/* 545 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*     */           
/* 547 */           decodeColor(this.color26, this.color30, 0.5F), this.color30 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 552 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 553 */     float f1 = (float)rectangle2D.getX();
/* 554 */     float f2 = (float)rectangle2D.getY();
/* 555 */     float f3 = (float)rectangle2D.getWidth();
/* 556 */     float f4 = (float)rectangle2D.getHeight();
/* 557 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038709678F, 0.05483871F, 0.07096774F, 0.28064516F, 0.4903226F, 0.6967742F, 0.9032258F, 0.9241935F, 0.9451613F }, new Color[] { this.color34, 
/*     */ 
/*     */           
/* 560 */           decodeColor(this.color34, this.color35, 0.5F), this.color35, 
/*     */           
/* 562 */           decodeColor(this.color35, this.color36, 0.5F), this.color36, 
/*     */           
/* 564 */           decodeColor(this.color36, this.color37, 0.5F), this.color37, 
/*     */           
/* 566 */           decodeColor(this.color37, this.color38, 0.5F), this.color38 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 571 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 572 */     float f1 = (float)rectangle2D.getX();
/* 573 */     float f2 = (float)rectangle2D.getY();
/* 574 */     float f3 = (float)rectangle2D.getWidth();
/* 575 */     float f4 = (float)rectangle2D.getHeight();
/* 576 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038709678F, 0.061290324F, 0.08387097F, 0.27258065F, 0.46129033F, 0.4903226F, 0.5193548F, 0.71774197F, 0.91612905F, 0.92419356F, 0.93225807F }, new Color[] { this.color39, 
/*     */ 
/*     */           
/* 579 */           decodeColor(this.color39, this.color40, 0.5F), this.color40, 
/*     */           
/* 581 */           decodeColor(this.color40, this.color41, 0.5F), this.color41, 
/*     */           
/* 583 */           decodeColor(this.color41, this.color42, 0.5F), this.color42, 
/*     */           
/* 585 */           decodeColor(this.color42, this.color43, 0.5F), this.color43, 
/*     */           
/* 587 */           decodeColor(this.color43, this.color44, 0.5F), this.color44 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient11(Shape paramShape) {
/* 592 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 593 */     float f1 = (float)rectangle2D.getX();
/* 594 */     float f2 = (float)rectangle2D.getY();
/* 595 */     float f3 = (float)rectangle2D.getWidth();
/* 596 */     float f4 = (float)rectangle2D.getHeight();
/* 597 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.051612902F, 0.06612903F, 0.08064516F, 0.2935484F, 0.5064516F, 0.6903226F, 0.87419355F, 0.88870966F, 0.9032258F }, new Color[] { this.color45, 
/*     */ 
/*     */           
/* 600 */           decodeColor(this.color45, this.color46, 0.5F), this.color46, 
/*     */           
/* 602 */           decodeColor(this.color46, this.color47, 0.5F), this.color47, 
/*     */           
/* 604 */           decodeColor(this.color47, this.color48, 0.5F), this.color48, 
/*     */           
/* 606 */           decodeColor(this.color48, this.color49, 0.5F), this.color49 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient12(Shape paramShape) {
/* 611 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 612 */     float f1 = (float)rectangle2D.getX();
/* 613 */     float f2 = (float)rectangle2D.getY();
/* 614 */     float f3 = (float)rectangle2D.getWidth();
/* 615 */     float f4 = (float)rectangle2D.getHeight();
/* 616 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.20645161F, 0.41290322F, 0.44193548F, 0.47096774F, 0.7354839F, 1.0F }, new Color[] { this.color40, 
/*     */ 
/*     */           
/* 619 */           decodeColor(this.color40, this.color41, 0.5F), this.color41, 
/*     */           
/* 621 */           decodeColor(this.color41, this.color42, 0.5F), this.color42, 
/*     */           
/* 623 */           decodeColor(this.color42, this.color50, 0.5F), this.color50 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ProgressBarPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */