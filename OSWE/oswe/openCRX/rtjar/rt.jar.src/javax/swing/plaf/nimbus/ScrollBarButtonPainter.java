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
/*     */ final class ScrollBarButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int FOREGROUND_ENABLED = 1;
/*     */   static final int FOREGROUND_DISABLED = 2;
/*     */   static final int FOREGROUND_MOUSEOVER = 3;
/*     */   static final int FOREGROUND_PRESSED = 4;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  49 */   private Path2D path = new Path2D.Float();
/*  50 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  51 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  52 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private Color color1 = new Color(255, 200, 0, 255);
/*  58 */   private Color color2 = decodeColor("nimbusBlueGrey", -0.01111114F, -0.07763158F, -0.1490196F, 0);
/*  59 */   private Color color3 = decodeColor("nimbusBlueGrey", -0.111111104F, -0.10580933F, 0.086274505F, 0);
/*  60 */   private Color color4 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.102261856F, 0.20392156F, 0);
/*  61 */   private Color color5 = decodeColor("nimbusBlueGrey", -0.039682567F, -0.079276316F, 0.13333333F, 0);
/*  62 */   private Color color6 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.07382907F, 0.109803915F, 0);
/*  63 */   private Color color7 = decodeColor("nimbusBlueGrey", -0.039682567F, -0.08241387F, 0.23137254F, 0);
/*  64 */   private Color color8 = decodeColor("nimbusBlueGrey", -0.055555522F, -0.08443936F, -0.29411766F, -136);
/*  65 */   private Color color9 = decodeColor("nimbusBlueGrey", -0.055555522F, -0.09876161F, 0.25490195F, -178);
/*  66 */   private Color color10 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.08878718F, -0.5647059F, 0);
/*  67 */   private Color color11 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.080223285F, -0.4862745F, 0);
/*  68 */   private Color color12 = decodeColor("nimbusBlueGrey", -0.111111104F, -0.09525914F, -0.23137254F, 0);
/*  69 */   private Color color13 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -165);
/*  70 */   private Color color14 = decodeColor("nimbusBlueGrey", -0.04444444F, -0.080223285F, -0.09803921F, 0);
/*  71 */   private Color color15 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, 0.10588235F, 0);
/*  72 */   private Color color16 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  73 */   private Color color17 = decodeColor("nimbusBlueGrey", -0.039682567F, -0.081719734F, 0.20784312F, 0);
/*  74 */   private Color color18 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.07677104F, 0.18431371F, 0);
/*  75 */   private Color color19 = decodeColor("nimbusBlueGrey", -0.04444444F, -0.080223285F, -0.09803921F, -69);
/*  76 */   private Color color20 = decodeColor("nimbusBlueGrey", -0.055555522F, -0.09876161F, 0.25490195F, -39);
/*  77 */   private Color color21 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.0951417F, -0.49019608F, 0);
/*  78 */   private Color color22 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.086996906F, -0.4117647F, 0);
/*  79 */   private Color color23 = decodeColor("nimbusBlueGrey", -0.111111104F, -0.09719298F, -0.15686274F, 0);
/*  80 */   private Color color24 = decodeColor("nimbusBlueGrey", -0.037037015F, -0.043859646F, -0.21568626F, 0);
/*  81 */   private Color color25 = decodeColor("nimbusBlueGrey", -0.06349206F, -0.07309316F, -0.011764705F, 0);
/*  82 */   private Color color26 = decodeColor("nimbusBlueGrey", -0.048611104F, -0.07296763F, 0.09019607F, 0);
/*  83 */   private Color color27 = decodeColor("nimbusBlueGrey", -0.03535354F, -0.05497076F, 0.031372547F, 0);
/*  84 */   private Color color28 = decodeColor("nimbusBlueGrey", -0.034188032F, -0.043168806F, 0.011764705F, 0);
/*  85 */   private Color color29 = decodeColor("nimbusBlueGrey", -0.03535354F, -0.0600676F, 0.109803915F, 0);
/*  86 */   private Color color30 = decodeColor("nimbusBlueGrey", -0.037037015F, -0.043859646F, -0.21568626F, -44);
/*  87 */   private Color color31 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, -0.74509805F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBarButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  95 */     this.state = paramInt;
/*  96 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 102 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 105 */     switch (this.state) { case 1:
/* 106 */         paintForegroundEnabled(paramGraphics2D); break;
/* 107 */       case 2: paintForegroundDisabled(paramGraphics2D); break;
/* 108 */       case 3: paintForegroundMouseOver(paramGraphics2D); break;
/* 109 */       case 4: paintForegroundPressed(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 118 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintForegroundEnabled(Graphics2D paramGraphics2D) {
/* 122 */     this.path = decodePath1();
/* 123 */     paramGraphics2D.setPaint(this.color1);
/* 124 */     paramGraphics2D.fill(this.path);
/* 125 */     this.path = decodePath2();
/* 126 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 127 */     paramGraphics2D.fill(this.path);
/* 128 */     this.path = decodePath3();
/* 129 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 130 */     paramGraphics2D.fill(this.path);
/* 131 */     this.path = decodePath4();
/* 132 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 133 */     paramGraphics2D.fill(this.path);
/* 134 */     this.path = decodePath5();
/* 135 */     paramGraphics2D.setPaint(this.color13);
/* 136 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundDisabled(Graphics2D paramGraphics2D) {
/* 141 */     this.path = decodePath1();
/* 142 */     paramGraphics2D.setPaint(this.color1);
/* 143 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundMouseOver(Graphics2D paramGraphics2D) {
/* 148 */     this.path = decodePath1();
/* 149 */     paramGraphics2D.setPaint(this.color1);
/* 150 */     paramGraphics2D.fill(this.path);
/* 151 */     this.path = decodePath2();
/* 152 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 153 */     paramGraphics2D.fill(this.path);
/* 154 */     this.path = decodePath3();
/* 155 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 156 */     paramGraphics2D.fill(this.path);
/* 157 */     this.path = decodePath4();
/* 158 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 159 */     paramGraphics2D.fill(this.path);
/* 160 */     this.path = decodePath5();
/* 161 */     paramGraphics2D.setPaint(this.color13);
/* 162 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundPressed(Graphics2D paramGraphics2D) {
/* 167 */     this.path = decodePath1();
/* 168 */     paramGraphics2D.setPaint(this.color1);
/* 169 */     paramGraphics2D.fill(this.path);
/* 170 */     this.path = decodePath2();
/* 171 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 172 */     paramGraphics2D.fill(this.path);
/* 173 */     this.path = decodePath3();
/* 174 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 175 */     paramGraphics2D.fill(this.path);
/* 176 */     this.path = decodePath4();
/* 177 */     paramGraphics2D.setPaint(this.color31);
/* 178 */     paramGraphics2D.fill(this.path);
/* 179 */     this.path = decodePath5();
/* 180 */     paramGraphics2D.setPaint(this.color13);
/* 181 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path2D decodePath1() {
/* 188 */     this.path.reset();
/* 189 */     this.path.moveTo(decodeX(3.0F), decodeY(3.0F));
/* 190 */     this.path.lineTo(decodeX(3.0F), decodeY(3.0F));
/* 191 */     this.path.closePath();
/* 192 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 196 */     this.path.reset();
/* 197 */     this.path.moveTo(decodeX(0.0F), decodeY(0.0F));
/* 198 */     this.path.lineTo(decodeX(1.6956522F), decodeY(0.0F));
/* 199 */     this.path.curveTo(decodeAnchorX(1.6956522F, 0.0F), decodeAnchorY(0.0F, 0.0F), decodeAnchorX(1.6956522F, -0.7058824F), decodeAnchorY(1.3076923F, -3.0294118F), decodeX(1.6956522F), decodeY(1.3076923F));
/* 200 */     this.path.curveTo(decodeAnchorX(1.6956522F, 0.7058824F), decodeAnchorY(1.3076923F, 3.0294118F), decodeAnchorX(1.826087F, -2.0F), decodeAnchorY(1.7692308F, -1.9411764F), decodeX(1.826087F), decodeY(1.7692308F));
/* 201 */     this.path.curveTo(decodeAnchorX(1.826087F, 2.0F), decodeAnchorY(1.7692308F, 1.9411764F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(2.0F, 0.0F), decodeX(3.0F), decodeY(2.0F));
/* 202 */     this.path.lineTo(decodeX(3.0F), decodeY(3.0F));
/* 203 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 204 */     this.path.lineTo(decodeX(0.0F), decodeY(0.0F));
/* 205 */     this.path.closePath();
/* 206 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 210 */     this.path.reset();
/* 211 */     this.path.moveTo(decodeX(0.0F), decodeY(1.0022625F));
/* 212 */     this.path.lineTo(decodeX(0.9705882F), decodeY(1.0384616F));
/* 213 */     this.path.lineTo(decodeX(1.0409207F), decodeY(1.0791855F));
/* 214 */     this.path.lineTo(decodeX(1.0409207F), decodeY(3.0F));
/* 215 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 216 */     this.path.lineTo(decodeX(0.0F), decodeY(1.0022625F));
/* 217 */     this.path.closePath();
/* 218 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 222 */     this.path.reset();
/* 223 */     this.path.moveTo(decodeX(1.4782609F), decodeY(1.2307693F));
/* 224 */     this.path.lineTo(decodeX(1.4782609F), decodeY(1.7692308F));
/* 225 */     this.path.lineTo(decodeX(1.1713555F), decodeY(1.5F));
/* 226 */     this.path.lineTo(decodeX(1.4782609F), decodeY(1.2307693F));
/* 227 */     this.path.closePath();
/* 228 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 232 */     this.path.reset();
/* 233 */     this.path.moveTo(decodeX(1.6713555F), decodeY(1.0769231F));
/* 234 */     this.path.curveTo(decodeAnchorX(1.6713555F, 0.7352941F), decodeAnchorY(1.0769231F, 0.0F), decodeAnchorX(1.7186701F, -0.9117647F), decodeAnchorY(1.4095023F, -2.2058823F), decodeX(1.7186701F), decodeY(1.4095023F));
/* 235 */     this.path.curveTo(decodeAnchorX(1.7186701F, 0.9117647F), decodeAnchorY(1.4095023F, 2.2058823F), decodeAnchorX(1.8439897F, -2.3529413F), decodeAnchorY(1.7941177F, -1.8529412F), decodeX(1.8439897F), decodeY(1.7941177F));
/* 236 */     this.path.curveTo(decodeAnchorX(1.8439897F, 2.3529413F), decodeAnchorY(1.7941177F, 1.8529412F), decodeAnchorX(2.5F, 0.0F), decodeAnchorY(2.2352943F, 0.0F), decodeX(2.5F), decodeY(2.2352943F));
/* 237 */     this.path.lineTo(decodeX(2.3529415F), decodeY(2.8235292F));
/* 238 */     this.path.curveTo(decodeAnchorX(2.3529415F, 0.0F), decodeAnchorY(2.8235292F, 0.0F), decodeAnchorX(1.8184143F, 1.5588236F), decodeAnchorY(1.8438914F, 1.382353F), decodeX(1.8184143F), decodeY(1.8438914F));
/* 239 */     this.path.curveTo(decodeAnchorX(1.8184143F, -1.5588236F), decodeAnchorY(1.8438914F, -1.382353F), decodeAnchorX(1.6943734F, 0.7941176F), decodeAnchorY(1.4841628F, 2.0F), decodeX(1.6943734F), decodeY(1.4841628F));
/* 240 */     this.path.curveTo(decodeAnchorX(1.6943734F, -0.7941176F), decodeAnchorY(1.4841628F, -2.0F), decodeAnchorX(1.6713555F, -0.7352941F), decodeAnchorY(1.0769231F, 0.0F), decodeX(1.6713555F), decodeY(1.0769231F));
/* 241 */     this.path.closePath();
/* 242 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 248 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 249 */     float f1 = (float)rectangle2D.getX();
/* 250 */     float f2 = (float)rectangle2D.getY();
/* 251 */     float f3 = (float)rectangle2D.getWidth();
/* 252 */     float f4 = (float)rectangle2D.getHeight();
/* 253 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.032934133F, 0.065868266F, 0.089820355F, 0.11377245F, 0.23053892F, 0.3473054F, 0.494012F, 0.6407186F, 0.78443116F, 0.92814374F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 256 */           decodeColor(this.color2, this.color3, 0.5F), this.color3, 
/*     */           
/* 258 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 260 */           decodeColor(this.color4, this.color5, 0.5F), this.color5, 
/*     */           
/* 262 */           decodeColor(this.color5, this.color6, 0.5F), this.color6, 
/*     */           
/* 264 */           decodeColor(this.color6, this.color7, 0.5F), this.color7 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 269 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 270 */     float f1 = (float)rectangle2D.getX();
/* 271 */     float f2 = (float)rectangle2D.getY();
/* 272 */     float f3 = (float)rectangle2D.getWidth();
/* 273 */     float f4 = (float)rectangle2D.getHeight();
/* 274 */     return decodeGradient(0.0F * f3 + f1, 0.5F * f4 + f2, 0.5735294F * f3 + f1, 0.5F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color8, 
/*     */ 
/*     */           
/* 277 */           decodeColor(this.color8, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 282 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 283 */     float f1 = (float)rectangle2D.getX();
/* 284 */     float f2 = (float)rectangle2D.getY();
/* 285 */     float f3 = (float)rectangle2D.getWidth();
/* 286 */     float f4 = (float)rectangle2D.getHeight();
/* 287 */     return decodeGradient(0.925F * f3 + f1, 0.9285714F * f4 + f2, 0.925F * f3 + f1, 0.004201681F * f4 + f2, new float[] { 0.0F, 0.2964072F, 0.5928144F, 0.79341316F, 0.994012F }, new Color[] { this.color10, 
/*     */ 
/*     */           
/* 290 */           decodeColor(this.color10, this.color11, 0.5F), this.color11, 
/*     */           
/* 292 */           decodeColor(this.color11, this.color12, 0.5F), this.color12 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 297 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 298 */     float f1 = (float)rectangle2D.getX();
/* 299 */     float f2 = (float)rectangle2D.getY();
/* 300 */     float f3 = (float)rectangle2D.getWidth();
/* 301 */     float f4 = (float)rectangle2D.getHeight();
/* 302 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.032934133F, 0.065868266F, 0.089820355F, 0.11377245F, 0.23053892F, 0.3473054F, 0.494012F, 0.6407186F, 0.78443116F, 0.92814374F }, new Color[] { this.color14, 
/*     */ 
/*     */           
/* 305 */           decodeColor(this.color14, this.color15, 0.5F), this.color15, 
/*     */           
/* 307 */           decodeColor(this.color15, this.color16, 0.5F), this.color16, 
/*     */           
/* 309 */           decodeColor(this.color16, this.color17, 0.5F), this.color17, 
/*     */           
/* 311 */           decodeColor(this.color17, this.color18, 0.5F), this.color18, 
/*     */           
/* 313 */           decodeColor(this.color18, this.color16, 0.5F), this.color16 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 318 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 319 */     float f1 = (float)rectangle2D.getX();
/* 320 */     float f2 = (float)rectangle2D.getY();
/* 321 */     float f3 = (float)rectangle2D.getWidth();
/* 322 */     float f4 = (float)rectangle2D.getHeight();
/* 323 */     return decodeGradient(0.0F * f3 + f1, 0.5F * f4 + f2, 0.5735294F * f3 + f1, 0.5F * f4 + f2, new float[] { 0.19518717F, 0.5975936F, 1.0F }, new Color[] { this.color19, 
/*     */ 
/*     */           
/* 326 */           decodeColor(this.color19, this.color20, 0.5F), this.color20 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 331 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 332 */     float f1 = (float)rectangle2D.getX();
/* 333 */     float f2 = (float)rectangle2D.getY();
/* 334 */     float f3 = (float)rectangle2D.getWidth();
/* 335 */     float f4 = (float)rectangle2D.getHeight();
/* 336 */     return decodeGradient(0.925F * f3 + f1, 0.9285714F * f4 + f2, 0.925F * f3 + f1, 0.004201681F * f4 + f2, new float[] { 0.0F, 0.2964072F, 0.5928144F, 0.79341316F, 0.994012F }, new Color[] { this.color21, 
/*     */ 
/*     */           
/* 339 */           decodeColor(this.color21, this.color22, 0.5F), this.color22, 
/*     */           
/* 341 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 346 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 347 */     float f1 = (float)rectangle2D.getX();
/* 348 */     float f2 = (float)rectangle2D.getY();
/* 349 */     float f3 = (float)rectangle2D.getWidth();
/* 350 */     float f4 = (float)rectangle2D.getHeight();
/* 351 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.032934133F, 0.065868266F, 0.089820355F, 0.11377245F, 0.23053892F, 0.3473054F, 0.494012F, 0.6407186F, 0.78443116F, 0.92814374F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 354 */           decodeColor(this.color24, this.color25, 0.5F), this.color25, 
/*     */           
/* 356 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*     */           
/* 358 */           decodeColor(this.color26, this.color27, 0.5F), this.color27, 
/*     */           
/* 360 */           decodeColor(this.color27, this.color28, 0.5F), this.color28, 
/*     */           
/* 362 */           decodeColor(this.color28, this.color29, 0.5F), this.color29 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 367 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 368 */     float f1 = (float)rectangle2D.getX();
/* 369 */     float f2 = (float)rectangle2D.getY();
/* 370 */     float f3 = (float)rectangle2D.getWidth();
/* 371 */     float f4 = (float)rectangle2D.getHeight();
/* 372 */     return decodeGradient(0.0F * f3 + f1, 0.5F * f4 + f2, 0.5735294F * f3 + f1, 0.5F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color30, 
/*     */ 
/*     */           
/* 375 */           decodeColor(this.color30, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ScrollBarButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */