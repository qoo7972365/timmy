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
/*     */ final class ComboBoxTextFieldPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_SELECTED = 3;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  48 */   private Path2D path = new Path2D.Float();
/*  49 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  50 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  51 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, -0.74509805F, -237);
/*  57 */   private Color color2 = decodeColor("nimbusBlueGrey", -0.006944418F, -0.07187897F, 0.06666666F, 0);
/*  58 */   private Color color3 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07703349F, 0.0745098F, 0);
/*  59 */   private Color color4 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07968931F, 0.14509803F, 0);
/*  60 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.07856284F, 0.11372548F, 0);
/*  61 */   private Color color6 = decodeColor("nimbusBase", 0.040395975F, -0.60315615F, 0.29411763F, 0);
/*  62 */   private Color color7 = decodeColor("nimbusBase", 0.016586483F, -0.6051466F, 0.3490196F, 0);
/*  63 */   private Color color8 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.0965403F, -0.18431371F, 0);
/*  64 */   private Color color9 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.1048766F, -0.05098039F, 0);
/*  65 */   private Color color10 = decodeColor("nimbusLightBackground", 0.6666667F, 0.004901961F, -0.19999999F, 0);
/*  66 */   private Color color11 = decodeColor("nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  67 */   private Color color12 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.105344966F, 0.011764705F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ComboBoxTextFieldPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  75 */     this.state = paramInt;
/*  76 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  82 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  85 */     switch (this.state) { case 1:
/*  86 */         paintBackgroundDisabled(paramGraphics2D); break;
/*  87 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/*  88 */       case 3: paintBackgroundSelected(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  97 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 101 */     this.rect = decodeRect1();
/* 102 */     paramGraphics2D.setPaint(this.color1);
/* 103 */     paramGraphics2D.fill(this.rect);
/* 104 */     this.rect = decodeRect2();
/* 105 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 106 */     paramGraphics2D.fill(this.rect);
/* 107 */     this.rect = decodeRect3();
/* 108 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 109 */     paramGraphics2D.fill(this.rect);
/* 110 */     this.rect = decodeRect4();
/* 111 */     paramGraphics2D.setPaint(this.color6);
/* 112 */     paramGraphics2D.fill(this.rect);
/* 113 */     this.rect = decodeRect5();
/* 114 */     paramGraphics2D.setPaint(this.color7);
/* 115 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 120 */     this.rect = decodeRect1();
/* 121 */     paramGraphics2D.setPaint(this.color1);
/* 122 */     paramGraphics2D.fill(this.rect);
/* 123 */     this.rect = decodeRect2();
/* 124 */     paramGraphics2D.setPaint(decodeGradient3(this.rect));
/* 125 */     paramGraphics2D.fill(this.rect);
/* 126 */     this.rect = decodeRect3();
/* 127 */     paramGraphics2D.setPaint(decodeGradient4(this.rect));
/* 128 */     paramGraphics2D.fill(this.rect);
/* 129 */     this.rect = decodeRect4();
/* 130 */     paramGraphics2D.setPaint(this.color12);
/* 131 */     paramGraphics2D.fill(this.rect);
/* 132 */     this.rect = decodeRect5();
/* 133 */     paramGraphics2D.setPaint(this.color11);
/* 134 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelected(Graphics2D paramGraphics2D) {
/* 139 */     this.rect = decodeRect1();
/* 140 */     paramGraphics2D.setPaint(this.color1);
/* 141 */     paramGraphics2D.fill(this.rect);
/* 142 */     this.rect = decodeRect2();
/* 143 */     paramGraphics2D.setPaint(decodeGradient3(this.rect));
/* 144 */     paramGraphics2D.fill(this.rect);
/* 145 */     this.rect = decodeRect3();
/* 146 */     paramGraphics2D.setPaint(decodeGradient4(this.rect));
/* 147 */     paramGraphics2D.fill(this.rect);
/* 148 */     this.rect = decodeRect4();
/* 149 */     paramGraphics2D.setPaint(this.color12);
/* 150 */     paramGraphics2D.fill(this.rect);
/* 151 */     this.rect = decodeRect5();
/* 152 */     paramGraphics2D.setPaint(this.color11);
/* 153 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 160 */     this.rect.setRect(decodeX(0.6666667F), 
/* 161 */         decodeY(2.3333333F), (
/* 162 */         decodeX(3.0F) - decodeX(0.6666667F)), (
/* 163 */         decodeY(2.6666667F) - decodeY(2.3333333F)));
/* 164 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 168 */     this.rect.setRect(decodeX(0.6666667F), 
/* 169 */         decodeY(0.4F), (
/* 170 */         decodeX(3.0F) - decodeX(0.6666667F)), (
/* 171 */         decodeY(1.0F) - decodeY(0.4F)));
/* 172 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect3() {
/* 176 */     this.rect.setRect(decodeX(1.0F), 
/* 177 */         decodeY(0.6F), (
/* 178 */         decodeX(3.0F) - decodeX(1.0F)), (
/* 179 */         decodeY(1.0F) - decodeY(0.6F)));
/* 180 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect4() {
/* 184 */     this.rect.setRect(decodeX(0.6666667F), 
/* 185 */         decodeY(1.0F), (
/* 186 */         decodeX(3.0F) - decodeX(0.6666667F)), (
/* 187 */         decodeY(2.3333333F) - decodeY(1.0F)));
/* 188 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect5() {
/* 192 */     this.rect.setRect(decodeX(1.0F), 
/* 193 */         decodeY(1.0F), (
/* 194 */         decodeX(3.0F) - decodeX(1.0F)), (
/* 195 */         decodeY(2.0F) - decodeY(1.0F)));
/* 196 */     return this.rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 202 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 203 */     float f1 = (float)rectangle2D.getX();
/* 204 */     float f2 = (float)rectangle2D.getY();
/* 205 */     float f3 = (float)rectangle2D.getWidth();
/* 206 */     float f4 = (float)rectangle2D.getHeight();
/* 207 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 210 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 215 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 216 */     float f1 = (float)rectangle2D.getX();
/* 217 */     float f2 = (float)rectangle2D.getY();
/* 218 */     float f3 = (float)rectangle2D.getWidth();
/* 219 */     float f4 = (float)rectangle2D.getHeight();
/* 220 */     return decodeGradient(0.5F * f3 + f1, 1.0F * f4 + f2, 0.5F * f3 + f1, 0.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color4, 
/*     */ 
/*     */           
/* 223 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 228 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 229 */     float f1 = (float)rectangle2D.getX();
/* 230 */     float f2 = (float)rectangle2D.getY();
/* 231 */     float f3 = (float)rectangle2D.getWidth();
/* 232 */     float f4 = (float)rectangle2D.getHeight();
/* 233 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.49573863F, 0.99147725F }, new Color[] { this.color8, 
/*     */ 
/*     */           
/* 236 */           decodeColor(this.color8, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 241 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 242 */     float f1 = (float)rectangle2D.getX();
/* 243 */     float f2 = (float)rectangle2D.getY();
/* 244 */     float f3 = (float)rectangle2D.getWidth();
/* 245 */     float f4 = (float)rectangle2D.getHeight();
/* 246 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.1F, 0.49999997F, 0.9F }, new Color[] { this.color10, 
/*     */ 
/*     */           
/* 249 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ComboBoxTextFieldPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */