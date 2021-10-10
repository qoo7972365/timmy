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
/*     */ final class FormattedTextFieldPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_SELECTED = 3;
/*     */   static final int BORDER_DISABLED = 4;
/*     */   static final int BORDER_FOCUSED = 5;
/*     */   static final int BORDER_ENABLED = 6;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  51 */   private Path2D path = new Path2D.Float();
/*  52 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  53 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  54 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.015872955F, -0.07995863F, 0.15294117F, 0);
/*  60 */   private Color color2 = decodeColor("nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  61 */   private Color color3 = decodeColor("nimbusBlueGrey", -0.006944418F, -0.07187897F, 0.06666666F, 0);
/*  62 */   private Color color4 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07826825F, 0.10588235F, 0);
/*  63 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07856284F, 0.11372548F, 0);
/*  64 */   private Color color6 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07796818F, 0.09803921F, 0);
/*  65 */   private Color color7 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.0965403F, -0.18431371F, 0);
/*  66 */   private Color color8 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.1048766F, -0.05098039F, 0);
/*  67 */   private Color color9 = decodeColor("nimbusLightBackground", 0.6666667F, 0.004901961F, -0.19999999F, 0);
/*  68 */   private Color color10 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.10512091F, -0.019607842F, 0);
/*  69 */   private Color color11 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.105344966F, 0.011764705F, 0);
/*  70 */   private Color color12 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public FormattedTextFieldPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  78 */     this.state = paramInt;
/*  79 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  85 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  88 */     switch (this.state) { case 1:
/*  89 */         paintBackgroundDisabled(paramGraphics2D); break;
/*  90 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/*  91 */       case 3: paintBackgroundSelected(paramGraphics2D); break;
/*  92 */       case 4: paintBorderDisabled(paramGraphics2D); break;
/*  93 */       case 5: paintBorderFocused(paramGraphics2D); break;
/*  94 */       case 6: paintBorderEnabled(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   protected Object[] getExtendedCacheKeys(JComponent paramJComponent) {
/* 100 */     Object[] arrayOfObject = null;
/* 101 */     switch (this.state) {
/*     */       
/*     */       case 2:
/* 104 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color2, 0.0F, 0.0F, 0) };
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 109 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color9, 0.004901961F, -0.19999999F, 0), getComponentColor(paramJComponent, "background", this.color2, 0.0F, 0.0F, 0) };
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 114 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color9, 0.004901961F, -0.19999999F, 0), getComponentColor(paramJComponent, "background", this.color2, 0.0F, 0.0F, 0) };
/*     */         break;
/*     */     } 
/* 117 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 122 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 126 */     this.rect = decodeRect1();
/* 127 */     paramGraphics2D.setPaint(this.color1);
/* 128 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 133 */     this.rect = decodeRect1();
/* 134 */     paramGraphics2D.setPaint((Color)this.componentColors[0]);
/* 135 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelected(Graphics2D paramGraphics2D) {
/* 140 */     this.rect = decodeRect1();
/* 141 */     paramGraphics2D.setPaint(this.color2);
/* 142 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBorderDisabled(Graphics2D paramGraphics2D) {
/* 147 */     this.rect = decodeRect2();
/* 148 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 149 */     paramGraphics2D.fill(this.rect);
/* 150 */     this.rect = decodeRect3();
/* 151 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 152 */     paramGraphics2D.fill(this.rect);
/* 153 */     this.rect = decodeRect4();
/* 154 */     paramGraphics2D.setPaint(this.color6);
/* 155 */     paramGraphics2D.fill(this.rect);
/* 156 */     this.rect = decodeRect5();
/* 157 */     paramGraphics2D.setPaint(this.color4);
/* 158 */     paramGraphics2D.fill(this.rect);
/* 159 */     this.rect = decodeRect6();
/* 160 */     paramGraphics2D.setPaint(this.color4);
/* 161 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBorderFocused(Graphics2D paramGraphics2D) {
/* 166 */     this.rect = decodeRect7();
/* 167 */     paramGraphics2D.setPaint(decodeGradient3(this.rect));
/* 168 */     paramGraphics2D.fill(this.rect);
/* 169 */     this.rect = decodeRect8();
/* 170 */     paramGraphics2D.setPaint(decodeGradient4(this.rect));
/* 171 */     paramGraphics2D.fill(this.rect);
/* 172 */     this.rect = decodeRect9();
/* 173 */     paramGraphics2D.setPaint(this.color10);
/* 174 */     paramGraphics2D.fill(this.rect);
/* 175 */     this.rect = decodeRect10();
/* 176 */     paramGraphics2D.setPaint(this.color10);
/* 177 */     paramGraphics2D.fill(this.rect);
/* 178 */     this.rect = decodeRect11();
/* 179 */     paramGraphics2D.setPaint(this.color11);
/* 180 */     paramGraphics2D.fill(this.rect);
/* 181 */     this.path = decodePath1();
/* 182 */     paramGraphics2D.setPaint(this.color12);
/* 183 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBorderEnabled(Graphics2D paramGraphics2D) {
/* 188 */     this.rect = decodeRect7();
/* 189 */     paramGraphics2D.setPaint(decodeGradient5(this.rect));
/* 190 */     paramGraphics2D.fill(this.rect);
/* 191 */     this.rect = decodeRect8();
/* 192 */     paramGraphics2D.setPaint(decodeGradient4(this.rect));
/* 193 */     paramGraphics2D.fill(this.rect);
/* 194 */     this.rect = decodeRect9();
/* 195 */     paramGraphics2D.setPaint(this.color10);
/* 196 */     paramGraphics2D.fill(this.rect);
/* 197 */     this.rect = decodeRect10();
/* 198 */     paramGraphics2D.setPaint(this.color10);
/* 199 */     paramGraphics2D.fill(this.rect);
/* 200 */     this.rect = decodeRect11();
/* 201 */     paramGraphics2D.setPaint(this.color11);
/* 202 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 209 */     this.rect.setRect(decodeX(0.4F), 
/* 210 */         decodeY(0.4F), (
/* 211 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 212 */         decodeY(2.6F) - decodeY(0.4F)));
/* 213 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 217 */     this.rect.setRect(decodeX(0.6666667F), 
/* 218 */         decodeY(0.4F), (
/* 219 */         decodeX(2.3333333F) - decodeX(0.6666667F)), (
/* 220 */         decodeY(1.0F) - decodeY(0.4F)));
/* 221 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 225 */     this.rect.setRect(decodeX(1.0F), 
/* 226 */         decodeY(0.6F), (
/* 227 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 228 */         decodeY(1.0F) - decodeY(0.6F)));
/* 229 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect4() {
/* 233 */     this.rect.setRect(decodeX(0.6666667F), 
/* 234 */         decodeY(1.0F), (
/* 235 */         decodeX(1.0F) - decodeX(0.6666667F)), (
/* 236 */         decodeY(2.0F) - decodeY(1.0F)));
/* 237 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect5() {
/* 241 */     this.rect.setRect(decodeX(0.6666667F), 
/* 242 */         decodeY(2.3333333F), (
/* 243 */         decodeX(2.3333333F) - decodeX(0.6666667F)), (
/* 244 */         decodeY(2.0F) - decodeY(2.3333333F)));
/* 245 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect6() {
/* 249 */     this.rect.setRect(decodeX(2.0F), 
/* 250 */         decodeY(1.0F), (
/* 251 */         decodeX(2.3333333F) - decodeX(2.0F)), (
/* 252 */         decodeY(2.0F) - decodeY(1.0F)));
/* 253 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect7() {
/* 257 */     this.rect.setRect(decodeX(0.4F), 
/* 258 */         decodeY(0.4F), (
/* 259 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 260 */         decodeY(1.0F) - decodeY(0.4F)));
/* 261 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect8() {
/* 265 */     this.rect.setRect(decodeX(0.6F), 
/* 266 */         decodeY(0.6F), (
/* 267 */         decodeX(2.4F) - decodeX(0.6F)), (
/* 268 */         decodeY(1.0F) - decodeY(0.6F)));
/* 269 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect9() {
/* 273 */     this.rect.setRect(decodeX(0.4F), 
/* 274 */         decodeY(1.0F), (
/* 275 */         decodeX(0.6F) - decodeX(0.4F)), (
/* 276 */         decodeY(2.6F) - decodeY(1.0F)));
/* 277 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect10() {
/* 281 */     this.rect.setRect(decodeX(2.4F), 
/* 282 */         decodeY(1.0F), (
/* 283 */         decodeX(2.6F) - decodeX(2.4F)), (
/* 284 */         decodeY(2.6F) - decodeY(1.0F)));
/* 285 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect11() {
/* 289 */     this.rect.setRect(decodeX(0.6F), 
/* 290 */         decodeY(2.4F), (
/* 291 */         decodeX(2.4F) - decodeX(0.6F)), (
/* 292 */         decodeY(2.6F) - decodeY(2.4F)));
/* 293 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 297 */     this.path.reset();
/* 298 */     this.path.moveTo(decodeX(0.4F), decodeY(0.4F));
/* 299 */     this.path.lineTo(decodeX(0.4F), decodeY(2.6F));
/* 300 */     this.path.lineTo(decodeX(2.6F), decodeY(2.6F));
/* 301 */     this.path.lineTo(decodeX(2.6F), decodeY(0.4F));
/* 302 */     this.path.curveTo(decodeAnchorX(2.6F, 0.0F), decodeAnchorY(0.4F, 0.0F), decodeAnchorX(2.8800004F, 0.1F), decodeAnchorY(0.4F, 0.0F), decodeX(2.8800004F), decodeY(0.4F));
/* 303 */     this.path.curveTo(decodeAnchorX(2.8800004F, 0.1F), decodeAnchorY(0.4F, 0.0F), decodeAnchorX(2.8800004F, 0.0F), decodeAnchorY(2.8799999F, 0.0F), decodeX(2.8800004F), decodeY(2.8799999F));
/* 304 */     this.path.lineTo(decodeX(0.120000005F), decodeY(2.8799999F));
/* 305 */     this.path.lineTo(decodeX(0.120000005F), decodeY(0.120000005F));
/* 306 */     this.path.lineTo(decodeX(2.8800004F), decodeY(0.120000005F));
/* 307 */     this.path.lineTo(decodeX(2.8800004F), decodeY(0.4F));
/* 308 */     this.path.lineTo(decodeX(0.4F), decodeY(0.4F));
/* 309 */     this.path.closePath();
/* 310 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 316 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 317 */     float f1 = (float)rectangle2D.getX();
/* 318 */     float f2 = (float)rectangle2D.getY();
/* 319 */     float f3 = (float)rectangle2D.getWidth();
/* 320 */     float f4 = (float)rectangle2D.getHeight();
/* 321 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 324 */           decodeColor(this.color3, this.color4, 0.5F), this.color4 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 329 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 330 */     float f1 = (float)rectangle2D.getX();
/* 331 */     float f2 = (float)rectangle2D.getY();
/* 332 */     float f3 = (float)rectangle2D.getWidth();
/* 333 */     float f4 = (float)rectangle2D.getHeight();
/* 334 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color5, 
/*     */ 
/*     */           
/* 337 */           decodeColor(this.color5, this.color1, 0.5F), this.color1 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 342 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 343 */     float f1 = (float)rectangle2D.getX();
/* 344 */     float f2 = (float)rectangle2D.getY();
/* 345 */     float f3 = (float)rectangle2D.getWidth();
/* 346 */     float f4 = (float)rectangle2D.getHeight();
/* 347 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25F * f3 + f1, 0.1625F * f4 + f2, new float[] { 0.1F, 0.49999997F, 0.9F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 350 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 355 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 356 */     float f1 = (float)rectangle2D.getX();
/* 357 */     float f2 = (float)rectangle2D.getY();
/* 358 */     float f3 = (float)rectangle2D.getWidth();
/* 359 */     float f4 = (float)rectangle2D.getHeight();
/* 360 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.1F, 0.49999997F, 0.9F }, new Color[] { (Color)this.componentColors[0], 
/*     */ 
/*     */           
/* 363 */           decodeColor((Color)this.componentColors[0], (Color)this.componentColors[1], 0.5F), (Color)this.componentColors[1] });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 368 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 369 */     float f1 = (float)rectangle2D.getX();
/* 370 */     float f2 = (float)rectangle2D.getY();
/* 371 */     float f3 = (float)rectangle2D.getWidth();
/* 372 */     float f4 = (float)rectangle2D.getHeight();
/* 373 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.1F, 0.49999997F, 0.9F }, new Color[] { this.color7, 
/*     */ 
/*     */           
/* 376 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/FormattedTextFieldPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */