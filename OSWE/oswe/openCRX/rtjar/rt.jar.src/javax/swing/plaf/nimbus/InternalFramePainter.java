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
/*     */ final class InternalFramePainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_ENABLED = 1;
/*     */   static final int BACKGROUND_ENABLED_WINDOWFOCUSED = 2;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  47 */   private Path2D path = new Path2D.Float();
/*  48 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  49 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  50 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private Color color1 = decodeColor("nimbusBase", 0.032459438F, -0.53637654F, 0.043137252F, 0);
/*  56 */   private Color color2 = decodeColor("nimbusBlueGrey", 0.004273474F, -0.039488062F, -0.027450979F, 0);
/*  57 */   private Color color3 = decodeColor("nimbusBlueGrey", -0.00505054F, -0.056339122F, 0.05098039F, 0);
/*  58 */   private Color color4 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.06357796F, 0.09019607F, 0);
/*  59 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.0F, -0.023821115F, -0.06666666F, 0);
/*  60 */   private Color color6 = decodeColor("control", 0.0F, 0.0F, 0.0F, 0);
/*  61 */   private Color color7 = decodeColor("nimbusBlueGrey", -0.006944418F, -0.07399663F, 0.11372548F, 0);
/*  62 */   private Color color8 = decodeColor("nimbusBase", 0.02551502F, -0.47885156F, -0.34901965F, 0);
/*  63 */   private Color color9 = new Color(255, 200, 0, 255);
/*  64 */   private Color color10 = decodeColor("nimbusBase", 0.004681647F, -0.6274498F, 0.39999998F, 0);
/*  65 */   private Color color11 = decodeColor("nimbusBase", 0.032459438F, -0.5934608F, 0.2862745F, 0);
/*  66 */   private Color color12 = new Color(204, 207, 213, 255);
/*  67 */   private Color color13 = decodeColor("nimbusBase", 0.032459438F, -0.55506915F, 0.18039215F, 0);
/*  68 */   private Color color14 = decodeColor("nimbusBase", 0.004681647F, -0.52792984F, 0.10588235F, 0);
/*  69 */   private Color color15 = decodeColor("nimbusBase", 0.03801495F, -0.4794643F, -0.04705882F, 0);
/*  70 */   private Color color16 = decodeColor("nimbusBase", 0.021348298F, -0.61416256F, 0.3607843F, 0);
/*  71 */   private Color color17 = decodeColor("nimbusBase", 0.032459438F, -0.5546332F, 0.17647058F, 0);
/*  72 */   private Color color18 = new Color(235, 236, 238, 255);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public InternalFramePainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  80 */     this.state = paramInt;
/*  81 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  87 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  90 */     switch (this.state) { case 1:
/*  91 */         paintBackgroundEnabled(paramGraphics2D); break;
/*  92 */       case 2: paintBackgroundEnabledAndWindowFocused(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 101 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 105 */     this.roundRect = decodeRoundRect1();
/* 106 */     paramGraphics2D.setPaint(this.color1);
/* 107 */     paramGraphics2D.fill(this.roundRect);
/* 108 */     this.path = decodePath1();
/* 109 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 110 */     paramGraphics2D.fill(this.path);
/* 111 */     this.path = decodePath2();
/* 112 */     paramGraphics2D.setPaint(this.color3);
/* 113 */     paramGraphics2D.fill(this.path);
/* 114 */     this.path = decodePath3();
/* 115 */     paramGraphics2D.setPaint(this.color4);
/* 116 */     paramGraphics2D.fill(this.path);
/* 117 */     this.path = decodePath4();
/* 118 */     paramGraphics2D.setPaint(this.color5);
/* 119 */     paramGraphics2D.fill(this.path);
/* 120 */     this.rect = decodeRect1();
/* 121 */     paramGraphics2D.setPaint(this.color6);
/* 122 */     paramGraphics2D.fill(this.rect);
/* 123 */     this.rect = decodeRect2();
/* 124 */     paramGraphics2D.setPaint(this.color7);
/* 125 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndWindowFocused(Graphics2D paramGraphics2D) {
/* 130 */     this.roundRect = decodeRoundRect2();
/* 131 */     paramGraphics2D.setPaint(this.color8);
/* 132 */     paramGraphics2D.fill(this.roundRect);
/* 133 */     this.path = decodePath5();
/* 134 */     paramGraphics2D.setPaint(this.color9);
/* 135 */     paramGraphics2D.fill(this.path);
/* 136 */     this.path = decodePath1();
/* 137 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 138 */     paramGraphics2D.fill(this.path);
/* 139 */     this.path = decodePath6();
/* 140 */     paramGraphics2D.setPaint(this.color12);
/* 141 */     paramGraphics2D.fill(this.path);
/* 142 */     this.path = decodePath7();
/* 143 */     paramGraphics2D.setPaint(this.color13);
/* 144 */     paramGraphics2D.fill(this.path);
/* 145 */     this.path = decodePath8();
/* 146 */     paramGraphics2D.setPaint(this.color14);
/* 147 */     paramGraphics2D.fill(this.path);
/* 148 */     this.path = decodePath9();
/* 149 */     paramGraphics2D.setPaint(this.color15);
/* 150 */     paramGraphics2D.fill(this.path);
/* 151 */     this.rect = decodeRect1();
/* 152 */     paramGraphics2D.setPaint(this.color6);
/* 153 */     paramGraphics2D.fill(this.rect);
/* 154 */     this.rect = decodeRect3();
/* 155 */     paramGraphics2D.setPaint(this.color9);
/* 156 */     paramGraphics2D.fill(this.rect);
/* 157 */     this.rect = decodeRect3();
/* 158 */     paramGraphics2D.setPaint(this.color9);
/* 159 */     paramGraphics2D.fill(this.rect);
/* 160 */     this.rect = decodeRect3();
/* 161 */     paramGraphics2D.setPaint(this.color9);
/* 162 */     paramGraphics2D.fill(this.rect);
/* 163 */     this.rect = decodeRect4();
/* 164 */     paramGraphics2D.setPaint(decodeGradient3(this.rect));
/* 165 */     paramGraphics2D.fill(this.rect);
/* 166 */     this.rect = decodeRect2();
/* 167 */     paramGraphics2D.setPaint(this.color18);
/* 168 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 175 */     this.roundRect.setRoundRect(decodeX(0.0F), 
/* 176 */         decodeY(0.0F), (
/* 177 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 178 */         decodeY(3.0F) - decodeY(0.0F)), 4.666666507720947D, 4.666666507720947D);
/*     */     
/* 180 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 184 */     this.path.reset();
/* 185 */     this.path.moveTo(decodeX(0.16666667F), decodeY(0.12F));
/* 186 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.12F, -1.0F), decodeAnchorX(0.5F, -1.0F), decodeAnchorY(0.04F, 0.0F), decodeX(0.5F), decodeY(0.04F));
/* 187 */     this.path.curveTo(decodeAnchorX(0.5F, 1.0F), decodeAnchorY(0.04F, 0.0F), decodeAnchorX(2.5F, -1.0F), decodeAnchorY(0.04F, 0.0F), decodeX(2.5F), decodeY(0.04F));
/* 188 */     this.path.curveTo(decodeAnchorX(2.5F, 1.0F), decodeAnchorY(0.04F, 0.0F), decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(0.12F, -1.0F), decodeX(2.8333333F), decodeY(0.12F));
/* 189 */     this.path.curveTo(decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(0.12F, 1.0F), decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeX(2.8333333F), decodeY(0.96F));
/* 190 */     this.path.lineTo(decodeX(0.16666667F), decodeY(0.96F));
/* 191 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.12F, 1.0F), decodeX(0.16666667F), decodeY(0.12F));
/* 192 */     this.path.closePath();
/* 193 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 197 */     this.path.reset();
/* 198 */     this.path.moveTo(decodeX(0.6666667F), decodeY(0.96F));
/* 199 */     this.path.lineTo(decodeX(0.16666667F), decodeY(0.96F));
/* 200 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(2.5F, -1.0F), decodeX(0.16666667F), decodeY(2.5F));
/* 201 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(2.5F, 1.0F), decodeAnchorX(0.5F, -1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeX(0.5F), decodeY(2.8333333F));
/* 202 */     this.path.curveTo(decodeAnchorX(0.5F, 1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeAnchorX(2.5F, -1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeX(2.5F), decodeY(2.8333333F));
/* 203 */     this.path.curveTo(decodeAnchorX(2.5F, 1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(2.5F, 1.0F), decodeX(2.8333333F), decodeY(2.5F));
/* 204 */     this.path.curveTo(decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(2.5F, -1.0F), decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeX(2.8333333F), decodeY(0.96F));
/* 205 */     this.path.lineTo(decodeX(2.3333333F), decodeY(0.96F));
/* 206 */     this.path.lineTo(decodeX(2.3333333F), decodeY(2.3333333F));
/* 207 */     this.path.lineTo(decodeX(0.6666667F), decodeY(2.3333333F));
/* 208 */     this.path.lineTo(decodeX(0.6666667F), decodeY(0.96F));
/* 209 */     this.path.closePath();
/* 210 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 214 */     this.path.reset();
/* 215 */     this.path.moveTo(decodeX(0.8333333F), decodeY(0.96F));
/* 216 */     this.path.lineTo(decodeX(0.6666667F), decodeY(0.96F));
/* 217 */     this.path.lineTo(decodeX(0.6666667F), decodeY(2.3333333F));
/* 218 */     this.path.lineTo(decodeX(2.3333333F), decodeY(2.3333333F));
/* 219 */     this.path.lineTo(decodeX(2.3333333F), decodeY(0.96F));
/* 220 */     this.path.lineTo(decodeX(2.1666667F), decodeY(0.96F));
/* 221 */     this.path.lineTo(decodeX(2.1666667F), decodeY(2.1666667F));
/* 222 */     this.path.lineTo(decodeX(0.8333333F), decodeY(2.1666667F));
/* 223 */     this.path.lineTo(decodeX(0.8333333F), decodeY(0.96F));
/* 224 */     this.path.closePath();
/* 225 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 229 */     this.path.reset();
/* 230 */     this.path.moveTo(decodeX(2.1666667F), decodeY(1.0F));
/* 231 */     this.path.lineTo(decodeX(1.0F), decodeY(1.0F));
/* 232 */     this.path.lineTo(decodeX(1.0F), decodeY(2.0F));
/* 233 */     this.path.lineTo(decodeX(2.0F), decodeY(2.0F));
/* 234 */     this.path.lineTo(decodeX(2.0F), decodeY(1.0F));
/* 235 */     this.path.lineTo(decodeX(2.1666667F), decodeY(1.0F));
/* 236 */     this.path.lineTo(decodeX(2.1666667F), decodeY(2.1666667F));
/* 237 */     this.path.lineTo(decodeX(0.8333333F), decodeY(2.1666667F));
/* 238 */     this.path.lineTo(decodeX(0.8333333F), decodeY(0.96F));
/* 239 */     this.path.lineTo(decodeX(2.1666667F), decodeY(0.96F));
/* 240 */     this.path.lineTo(decodeX(2.1666667F), decodeY(1.0F));
/* 241 */     this.path.closePath();
/* 242 */     return this.path;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 246 */     this.rect.setRect(decodeX(1.0F), 
/* 247 */         decodeY(1.0F), (
/* 248 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 249 */         decodeY(2.0F) - decodeY(1.0F)));
/* 250 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 254 */     this.rect.setRect(decodeX(0.33333334F), 
/* 255 */         decodeY(2.6666667F), (
/* 256 */         decodeX(2.6666667F) - decodeX(0.33333334F)), (
/* 257 */         decodeY(2.8333333F) - decodeY(2.6666667F)));
/* 258 */     return this.rect;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect2() {
/* 262 */     this.roundRect.setRoundRect(decodeX(0.0F), 
/* 263 */         decodeY(0.0F), (
/* 264 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 265 */         decodeY(3.0F) - decodeY(0.0F)), 4.833333492279053D, 4.833333492279053D);
/*     */     
/* 267 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 271 */     this.path.reset();
/* 272 */     this.path.moveTo(decodeX(0.16666667F), decodeY(0.08F));
/* 273 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.08F, 1.0F), decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.08F, -1.0F), decodeX(0.16666667F), decodeY(0.08F));
/* 274 */     this.path.closePath();
/* 275 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath6() {
/* 279 */     this.path.reset();
/* 280 */     this.path.moveTo(decodeX(0.5F), decodeY(0.96F));
/* 281 */     this.path.lineTo(decodeX(0.16666667F), decodeY(0.96F));
/* 282 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(2.5F, -1.0F), decodeX(0.16666667F), decodeY(2.5F));
/* 283 */     this.path.curveTo(decodeAnchorX(0.16666667F, 0.0F), decodeAnchorY(2.5F, 1.0F), decodeAnchorX(0.5F, -1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeX(0.5F), decodeY(2.8333333F));
/* 284 */     this.path.curveTo(decodeAnchorX(0.5F, 1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeAnchorX(2.5F, -1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeX(2.5F), decodeY(2.8333333F));
/* 285 */     this.path.curveTo(decodeAnchorX(2.5F, 1.0F), decodeAnchorY(2.8333333F, 0.0F), decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(2.5F, 1.0F), decodeX(2.8333333F), decodeY(2.5F));
/* 286 */     this.path.curveTo(decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(2.5F, -1.0F), decodeAnchorX(2.8333333F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeX(2.8333333F), decodeY(0.96F));
/* 287 */     this.path.lineTo(decodeX(2.5F), decodeY(0.96F));
/* 288 */     this.path.lineTo(decodeX(2.5F), decodeY(2.5F));
/* 289 */     this.path.lineTo(decodeX(0.5F), decodeY(2.5F));
/* 290 */     this.path.lineTo(decodeX(0.5F), decodeY(0.96F));
/* 291 */     this.path.closePath();
/* 292 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath7() {
/* 296 */     this.path.reset();
/* 297 */     this.path.moveTo(decodeX(0.6666667F), decodeY(0.96F));
/* 298 */     this.path.lineTo(decodeX(0.33333334F), decodeY(0.96F));
/* 299 */     this.path.curveTo(decodeAnchorX(0.33333334F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeAnchorX(0.33333334F, 0.0F), decodeAnchorY(2.3333333F, -1.0F), decodeX(0.33333334F), decodeY(2.3333333F));
/* 300 */     this.path.curveTo(decodeAnchorX(0.33333334F, 0.0F), decodeAnchorY(2.3333333F, 1.0F), decodeAnchorX(0.6666667F, -1.0F), decodeAnchorY(2.6666667F, 0.0F), decodeX(0.6666667F), decodeY(2.6666667F));
/* 301 */     this.path.curveTo(decodeAnchorX(0.6666667F, 1.0F), decodeAnchorY(2.6666667F, 0.0F), decodeAnchorX(2.3333333F, -1.0F), decodeAnchorY(2.6666667F, 0.0F), decodeX(2.3333333F), decodeY(2.6666667F));
/* 302 */     this.path.curveTo(decodeAnchorX(2.3333333F, 1.0F), decodeAnchorY(2.6666667F, 0.0F), decodeAnchorX(2.6666667F, 0.0F), decodeAnchorY(2.3333333F, 1.0F), decodeX(2.6666667F), decodeY(2.3333333F));
/* 303 */     this.path.curveTo(decodeAnchorX(2.6666667F, 0.0F), decodeAnchorY(2.3333333F, -1.0F), decodeAnchorX(2.6666667F, 0.0F), decodeAnchorY(0.96F, 0.0F), decodeX(2.6666667F), decodeY(0.96F));
/* 304 */     this.path.lineTo(decodeX(2.3333333F), decodeY(0.96F));
/* 305 */     this.path.lineTo(decodeX(2.3333333F), decodeY(2.3333333F));
/* 306 */     this.path.lineTo(decodeX(0.6666667F), decodeY(2.3333333F));
/* 307 */     this.path.lineTo(decodeX(0.6666667F), decodeY(0.96F));
/* 308 */     this.path.closePath();
/* 309 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath8() {
/* 313 */     this.path.reset();
/* 314 */     this.path.moveTo(decodeX(2.3333333F), decodeY(0.96F));
/* 315 */     this.path.lineTo(decodeX(2.1666667F), decodeY(0.96F));
/* 316 */     this.path.lineTo(decodeX(2.1666667F), decodeY(2.1666667F));
/* 317 */     this.path.lineTo(decodeX(0.8333333F), decodeY(2.1666667F));
/* 318 */     this.path.lineTo(decodeX(0.8333333F), decodeY(0.96F));
/* 319 */     this.path.lineTo(decodeX(0.6666667F), decodeY(0.96F));
/* 320 */     this.path.lineTo(decodeX(0.6666667F), decodeY(2.3333333F));
/* 321 */     this.path.lineTo(decodeX(2.3333333F), decodeY(2.3333333F));
/* 322 */     this.path.lineTo(decodeX(2.3333333F), decodeY(0.96F));
/* 323 */     this.path.closePath();
/* 324 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath9() {
/* 328 */     this.path.reset();
/* 329 */     this.path.moveTo(decodeX(0.8333333F), decodeY(1.0F));
/* 330 */     this.path.lineTo(decodeX(0.8333333F), decodeY(2.1666667F));
/* 331 */     this.path.lineTo(decodeX(2.1666667F), decodeY(2.1666667F));
/* 332 */     this.path.lineTo(decodeX(2.1666667F), decodeY(0.96F));
/* 333 */     this.path.lineTo(decodeX(0.8333333F), decodeY(0.96F));
/* 334 */     this.path.lineTo(decodeX(0.8333333F), decodeY(1.0F));
/* 335 */     this.path.lineTo(decodeX(2.0F), decodeY(1.0F));
/* 336 */     this.path.lineTo(decodeX(2.0F), decodeY(2.0F));
/* 337 */     this.path.lineTo(decodeX(1.0F), decodeY(2.0F));
/* 338 */     this.path.lineTo(decodeX(1.0F), decodeY(1.0F));
/* 339 */     this.path.lineTo(decodeX(0.8333333F), decodeY(1.0F));
/* 340 */     this.path.closePath();
/* 341 */     return this.path;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 345 */     this.rect.setRect(decodeX(0.0F), 
/* 346 */         decodeY(0.0F), (
/* 347 */         decodeX(0.0F) - decodeX(0.0F)), (
/* 348 */         decodeY(0.0F) - decodeY(0.0F)));
/* 349 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect4() {
/* 353 */     this.rect.setRect(decodeX(0.33333334F), 
/* 354 */         decodeY(0.08F), (
/* 355 */         decodeX(2.6666667F) - decodeX(0.33333334F)), (
/* 356 */         decodeY(0.96F) - decodeY(0.08F)));
/* 357 */     return this.rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 363 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 364 */     float f1 = (float)rectangle2D.getX();
/* 365 */     float f2 = (float)rectangle2D.getY();
/* 366 */     float f3 = (float)rectangle2D.getWidth();
/* 367 */     float f4 = (float)rectangle2D.getHeight();
/* 368 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.3203593F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 371 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 376 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 377 */     float f1 = (float)rectangle2D.getX();
/* 378 */     float f2 = (float)rectangle2D.getY();
/* 379 */     float f3 = (float)rectangle2D.getWidth();
/* 380 */     float f4 = (float)rectangle2D.getHeight();
/* 381 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color10, 
/*     */ 
/*     */           
/* 384 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 389 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 390 */     float f1 = (float)rectangle2D.getX();
/* 391 */     float f2 = (float)rectangle2D.getY();
/* 392 */     float f3 = (float)rectangle2D.getWidth();
/* 393 */     float f4 = (float)rectangle2D.getHeight();
/* 394 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.24251497F, 1.0F }, new Color[] { this.color16, 
/*     */ 
/*     */           
/* 397 */           decodeColor(this.color16, this.color17, 0.5F), this.color17 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/InternalFramePainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */