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
/*     */ final class ComboBoxPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_DISABLED_PRESSED = 2;
/*     */   static final int BACKGROUND_ENABLED = 3;
/*     */   static final int BACKGROUND_FOCUSED = 4;
/*     */   static final int BACKGROUND_MOUSEOVER_FOCUSED = 5;
/*     */   static final int BACKGROUND_MOUSEOVER = 6;
/*     */   static final int BACKGROUND_PRESSED_FOCUSED = 7;
/*     */   static final int BACKGROUND_PRESSED = 8;
/*     */   static final int BACKGROUND_ENABLED_SELECTED = 9;
/*     */   static final int BACKGROUND_DISABLED_EDITABLE = 10;
/*     */   static final int BACKGROUND_ENABLED_EDITABLE = 11;
/*     */   static final int BACKGROUND_FOCUSED_EDITABLE = 12;
/*     */   static final int BACKGROUND_MOUSEOVER_EDITABLE = 13;
/*     */   static final int BACKGROUND_PRESSED_EDITABLE = 14;
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
/*  67 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, -0.74509805F, -247);
/*  68 */   private Color color2 = decodeColor("nimbusBase", 0.032459438F, -0.5928571F, 0.2745098F, 0);
/*  69 */   private Color color3 = decodeColor("nimbusBase", 0.032459438F, -0.590029F, 0.2235294F, 0);
/*  70 */   private Color color4 = decodeColor("nimbusBase", 0.032459438F, -0.60996324F, 0.36470586F, 0);
/*  71 */   private Color color5 = decodeColor("nimbusBase", 0.040395975F, -0.60474086F, 0.33725488F, 0);
/*  72 */   private Color color6 = decodeColor("nimbusBase", 0.032459438F, -0.5953556F, 0.32549018F, 0);
/*  73 */   private Color color7 = decodeColor("nimbusBase", 0.032459438F, -0.5957143F, 0.3333333F, 0);
/*  74 */   private Color color8 = decodeColor("nimbusBase", 0.021348298F, -0.56289876F, 0.2588235F, 0);
/*  75 */   private Color color9 = decodeColor("nimbusBase", 0.010237217F, -0.55799407F, 0.20784312F, 0);
/*  76 */   private Color color10 = decodeColor("nimbusBase", 0.021348298F, -0.59223604F, 0.35294116F, 0);
/*  77 */   private Color color11 = decodeColor("nimbusBase", 0.02391243F, -0.5774183F, 0.32549018F, 0);
/*  78 */   private Color color12 = decodeColor("nimbusBase", 0.021348298F, -0.56722116F, 0.3098039F, 0);
/*  79 */   private Color color13 = decodeColor("nimbusBase", 0.021348298F, -0.567841F, 0.31764704F, 0);
/*  80 */   private Color color14 = decodeColor("nimbusBlueGrey", 0.0F, 0.0F, -0.22F, -176);
/*  81 */   private Color color15 = decodeColor("nimbusBase", 0.032459438F, -0.5787523F, 0.07058823F, 0);
/*  82 */   private Color color16 = decodeColor("nimbusBase", 0.032459438F, -0.5399696F, -0.18039218F, 0);
/*  83 */   private Color color17 = decodeColor("nimbusBase", 0.08801502F, -0.63174605F, 0.43921566F, 0);
/*  84 */   private Color color18 = decodeColor("nimbusBase", 0.040395975F, -0.6054113F, 0.35686272F, 0);
/*  85 */   private Color color19 = decodeColor("nimbusBase", 0.032459438F, -0.5998577F, 0.4352941F, 0);
/*  86 */   private Color color20 = decodeColor("nimbusBase", 5.1498413E-4F, -0.34585923F, -0.007843137F, 0);
/*  87 */   private Color color21 = decodeColor("nimbusBase", 5.1498413E-4F, -0.095173776F, -0.25882354F, 0);
/*  88 */   private Color color22 = decodeColor("nimbusBase", 0.004681647F, -0.6197143F, 0.43137252F, 0);
/*  89 */   private Color color23 = decodeColor("nimbusBase", -0.0028941035F, -0.4800539F, 0.28235292F, 0);
/*  90 */   private Color color24 = decodeColor("nimbusBase", 5.1498413E-4F, -0.43866998F, 0.24705881F, 0);
/*  91 */   private Color color25 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4625541F, 0.35686272F, 0);
/*  92 */   private Color color26 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  93 */   private Color color27 = decodeColor("nimbusBase", 0.032459438F, -0.54616207F, -0.02352941F, 0);
/*  94 */   private Color color28 = decodeColor("nimbusBase", 0.032459438F, -0.41349208F, -0.33725494F, 0);
/*  95 */   private Color color29 = decodeColor("nimbusBase", 0.08801502F, -0.6317773F, 0.4470588F, 0);
/*  96 */   private Color color30 = decodeColor("nimbusBase", 0.032459438F, -0.6113241F, 0.41568625F, 0);
/*  97 */   private Color color31 = decodeColor("nimbusBase", 0.032459438F, -0.5985242F, 0.39999998F, 0);
/*  98 */   private Color color32 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*  99 */   private Color color33 = decodeColor("nimbusBase", 0.0013483167F, -0.1769987F, -0.12156865F, 0);
/* 100 */   private Color color34 = decodeColor("nimbusBase", 0.059279382F, 0.3642857F, -0.43529415F, 0);
/* 101 */   private Color color35 = decodeColor("nimbusBase", 0.004681647F, -0.6198413F, 0.43921566F, 0);
/* 102 */   private Color color36 = decodeColor("nimbusBase", -8.738637E-4F, -0.50527954F, 0.35294116F, 0);
/* 103 */   private Color color37 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4555341F, 0.3215686F, 0);
/* 104 */   private Color color38 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4757143F, 0.43137252F, 0);
/* 105 */   private Color color39 = decodeColor("nimbusBase", 0.08801502F, 0.3642857F, -0.52156866F, 0);
/* 106 */   private Color color40 = decodeColor("nimbusBase", 0.032459438F, -0.5246032F, -0.12549022F, 0);
/* 107 */   private Color color41 = decodeColor("nimbusBase", 0.027408898F, -0.5847884F, 0.2980392F, 0);
/* 108 */   private Color color42 = decodeColor("nimbusBase", 0.026611507F, -0.53623784F, 0.19999999F, 0);
/* 109 */   private Color color43 = decodeColor("nimbusBase", 0.029681683F, -0.52701867F, 0.17254901F, 0);
/* 110 */   private Color color44 = decodeColor("nimbusBase", 0.03801495F, -0.5456242F, 0.3215686F, 0);
/* 111 */   private Color color45 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/* 112 */   private Color color46 = decodeColor("nimbusBase", -3.528595E-5F, 0.018606722F, -0.23137257F, 0);
/* 113 */   private Color color47 = decodeColor("nimbusBase", -4.2033195E-4F, -0.38050595F, 0.20392156F, 0);
/* 114 */   private Color color48 = decodeColor("nimbusBase", 4.081726E-4F, -0.12922078F, 0.054901958F, 0);
/* 115 */   private Color color49 = decodeColor("nimbusBase", 0.0F, -0.00895375F, 0.007843137F, 0);
/* 116 */   private Color color50 = decodeColor("nimbusBase", -0.0015907288F, -0.1436508F, 0.19215685F, 0);
/* 117 */   private Color color51 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -83);
/* 118 */   private Color color52 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -88);
/* 119 */   private Color color53 = decodeColor("nimbusBlueGrey", 0.0F, -0.005263157F, -0.52156866F, -191);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ComboBoxPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 127 */     this.state = paramInt;
/* 128 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 134 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 137 */     switch (this.state) { case 1:
/* 138 */         paintBackgroundDisabled(paramGraphics2D); break;
/* 139 */       case 2: paintBackgroundDisabledAndPressed(paramGraphics2D); break;
/* 140 */       case 3: paintBackgroundEnabled(paramGraphics2D); break;
/* 141 */       case 4: paintBackgroundFocused(paramGraphics2D); break;
/* 142 */       case 5: paintBackgroundMouseOverAndFocused(paramGraphics2D); break;
/* 143 */       case 6: paintBackgroundMouseOver(paramGraphics2D); break;
/* 144 */       case 7: paintBackgroundPressedAndFocused(paramGraphics2D); break;
/* 145 */       case 8: paintBackgroundPressed(paramGraphics2D); break;
/* 146 */       case 9: paintBackgroundEnabledAndSelected(paramGraphics2D); break;
/* 147 */       case 10: paintBackgroundDisabledAndEditable(paramGraphics2D); break;
/* 148 */       case 11: paintBackgroundEnabledAndEditable(paramGraphics2D); break;
/* 149 */       case 12: paintBackgroundFocusedAndEditable(paramGraphics2D); break;
/* 150 */       case 13: paintBackgroundMouseOverAndEditable(paramGraphics2D); break;
/* 151 */       case 14: paintBackgroundPressedAndEditable(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   protected Object[] getExtendedCacheKeys(JComponent paramJComponent) {
/* 157 */     Object[] arrayOfObject = null;
/* 158 */     switch (this.state) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 168 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color17, -0.63174605F, 0.43921566F, 0), getComponentColor(paramJComponent, "background", this.color18, -0.6054113F, 0.35686272F, 0), getComponentColor(paramJComponent, "background", this.color6, -0.5953556F, 0.32549018F, 0), getComponentColor(paramJComponent, "background", this.color19, -0.5998577F, 0.4352941F, 0), getComponentColor(paramJComponent, "background", this.color22, -0.6197143F, 0.43137252F, 0), getComponentColor(paramJComponent, "background", this.color23, -0.4800539F, 0.28235292F, 0), getComponentColor(paramJComponent, "background", this.color24, -0.43866998F, 0.24705881F, 0), getComponentColor(paramJComponent, "background", this.color25, -0.4625541F, 0.35686272F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 179 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color17, -0.63174605F, 0.43921566F, 0), getComponentColor(paramJComponent, "background", this.color18, -0.6054113F, 0.35686272F, 0), getComponentColor(paramJComponent, "background", this.color6, -0.5953556F, 0.32549018F, 0), getComponentColor(paramJComponent, "background", this.color19, -0.5998577F, 0.4352941F, 0), getComponentColor(paramJComponent, "background", this.color22, -0.6197143F, 0.43137252F, 0), getComponentColor(paramJComponent, "background", this.color23, -0.4800539F, 0.28235292F, 0), getComponentColor(paramJComponent, "background", this.color24, -0.43866998F, 0.24705881F, 0), getComponentColor(paramJComponent, "background", this.color25, -0.4625541F, 0.35686272F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 191 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color29, -0.6317773F, 0.4470588F, 0), getComponentColor(paramJComponent, "background", this.color30, -0.6113241F, 0.41568625F, 0), getComponentColor(paramJComponent, "background", this.color31, -0.5985242F, 0.39999998F, 0), getComponentColor(paramJComponent, "background", this.color32, -0.6357143F, 0.45098037F, 0), getComponentColor(paramJComponent, "background", this.color35, -0.6198413F, 0.43921566F, 0), getComponentColor(paramJComponent, "background", this.color36, -0.50527954F, 0.35294116F, 0), getComponentColor(paramJComponent, "background", this.color37, -0.4555341F, 0.3215686F, 0), getComponentColor(paramJComponent, "background", this.color25, -0.4625541F, 0.35686272F, 0), getComponentColor(paramJComponent, "background", this.color38, -0.4757143F, 0.43137252F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 203 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color29, -0.6317773F, 0.4470588F, 0), getComponentColor(paramJComponent, "background", this.color30, -0.6113241F, 0.41568625F, 0), getComponentColor(paramJComponent, "background", this.color31, -0.5985242F, 0.39999998F, 0), getComponentColor(paramJComponent, "background", this.color32, -0.6357143F, 0.45098037F, 0), getComponentColor(paramJComponent, "background", this.color35, -0.6198413F, 0.43921566F, 0), getComponentColor(paramJComponent, "background", this.color36, -0.50527954F, 0.35294116F, 0), getComponentColor(paramJComponent, "background", this.color37, -0.4555341F, 0.3215686F, 0), getComponentColor(paramJComponent, "background", this.color25, -0.4625541F, 0.35686272F, 0), getComponentColor(paramJComponent, "background", this.color38, -0.4757143F, 0.43137252F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 7:
/* 214 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color41, -0.5847884F, 0.2980392F, 0), getComponentColor(paramJComponent, "background", this.color42, -0.53623784F, 0.19999999F, 0), getComponentColor(paramJComponent, "background", this.color43, -0.52701867F, 0.17254901F, 0), getComponentColor(paramJComponent, "background", this.color44, -0.5456242F, 0.3215686F, 0), getComponentColor(paramJComponent, "background", this.color47, -0.38050595F, 0.20392156F, 0), getComponentColor(paramJComponent, "background", this.color48, -0.12922078F, 0.054901958F, 0), getComponentColor(paramJComponent, "background", this.color49, -0.00895375F, 0.007843137F, 0), getComponentColor(paramJComponent, "background", this.color50, -0.1436508F, 0.19215685F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 225 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color41, -0.5847884F, 0.2980392F, 0), getComponentColor(paramJComponent, "background", this.color42, -0.53623784F, 0.19999999F, 0), getComponentColor(paramJComponent, "background", this.color43, -0.52701867F, 0.17254901F, 0), getComponentColor(paramJComponent, "background", this.color44, -0.5456242F, 0.3215686F, 0), getComponentColor(paramJComponent, "background", this.color47, -0.38050595F, 0.20392156F, 0), getComponentColor(paramJComponent, "background", this.color48, -0.12922078F, 0.054901958F, 0), getComponentColor(paramJComponent, "background", this.color49, -0.00895375F, 0.007843137F, 0), getComponentColor(paramJComponent, "background", this.color50, -0.1436508F, 0.19215685F, 0) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 9:
/* 236 */         arrayOfObject = new Object[] { getComponentColor(paramJComponent, "background", this.color41, -0.5847884F, 0.2980392F, 0), getComponentColor(paramJComponent, "background", this.color42, -0.53623784F, 0.19999999F, 0), getComponentColor(paramJComponent, "background", this.color43, -0.52701867F, 0.17254901F, 0), getComponentColor(paramJComponent, "background", this.color44, -0.5456242F, 0.3215686F, 0), getComponentColor(paramJComponent, "background", this.color47, -0.38050595F, 0.20392156F, 0), getComponentColor(paramJComponent, "background", this.color48, -0.12922078F, 0.054901958F, 0), getComponentColor(paramJComponent, "background", this.color49, -0.00895375F, 0.007843137F, 0), getComponentColor(paramJComponent, "background", this.color50, -0.1436508F, 0.19215685F, 0) };
/*     */         break;
/*     */     } 
/* 239 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 244 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 248 */     this.path = decodePath1();
/* 249 */     paramGraphics2D.setPaint(this.color1);
/* 250 */     paramGraphics2D.fill(this.path);
/* 251 */     this.path = decodePath2();
/* 252 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 253 */     paramGraphics2D.fill(this.path);
/* 254 */     this.path = decodePath3();
/* 255 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 256 */     paramGraphics2D.fill(this.path);
/* 257 */     this.path = decodePath4();
/* 258 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 259 */     paramGraphics2D.fill(this.path);
/* 260 */     this.path = decodePath5();
/* 261 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 262 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabledAndPressed(Graphics2D paramGraphics2D) {
/* 267 */     this.path = decodePath1();
/* 268 */     paramGraphics2D.setPaint(this.color1);
/* 269 */     paramGraphics2D.fill(this.path);
/* 270 */     this.path = decodePath2();
/* 271 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 272 */     paramGraphics2D.fill(this.path);
/* 273 */     this.path = decodePath3();
/* 274 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 275 */     paramGraphics2D.fill(this.path);
/* 276 */     this.path = decodePath4();
/* 277 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 278 */     paramGraphics2D.fill(this.path);
/* 279 */     this.path = decodePath5();
/* 280 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 281 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 286 */     this.path = decodePath1();
/* 287 */     paramGraphics2D.setPaint(this.color14);
/* 288 */     paramGraphics2D.fill(this.path);
/* 289 */     this.path = decodePath2();
/* 290 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 291 */     paramGraphics2D.fill(this.path);
/* 292 */     this.path = decodePath3();
/* 293 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 294 */     paramGraphics2D.fill(this.path);
/* 295 */     this.path = decodePath4();
/* 296 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 297 */     paramGraphics2D.fill(this.path);
/* 298 */     this.path = decodePath5();
/* 299 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 300 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocused(Graphics2D paramGraphics2D) {
/* 305 */     this.roundRect = decodeRoundRect1();
/* 306 */     paramGraphics2D.setPaint(this.color26);
/* 307 */     paramGraphics2D.fill(this.roundRect);
/* 308 */     this.path = decodePath2();
/* 309 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 310 */     paramGraphics2D.fill(this.path);
/* 311 */     this.path = decodePath3();
/* 312 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 313 */     paramGraphics2D.fill(this.path);
/* 314 */     this.path = decodePath4();
/* 315 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 316 */     paramGraphics2D.fill(this.path);
/* 317 */     this.path = decodePath5();
/* 318 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 319 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndFocused(Graphics2D paramGraphics2D) {
/* 324 */     this.roundRect = decodeRoundRect1();
/* 325 */     paramGraphics2D.setPaint(this.color26);
/* 326 */     paramGraphics2D.fill(this.roundRect);
/* 327 */     this.path = decodePath2();
/* 328 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 329 */     paramGraphics2D.fill(this.path);
/* 330 */     this.path = decodePath3();
/* 331 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 332 */     paramGraphics2D.fill(this.path);
/* 333 */     this.path = decodePath4();
/* 334 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/* 335 */     paramGraphics2D.fill(this.path);
/* 336 */     this.path = decodePath5();
/* 337 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 338 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 343 */     this.path = decodePath1();
/* 344 */     paramGraphics2D.setPaint(this.color14);
/* 345 */     paramGraphics2D.fill(this.path);
/* 346 */     this.path = decodePath2();
/* 347 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 348 */     paramGraphics2D.fill(this.path);
/* 349 */     this.path = decodePath3();
/* 350 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 351 */     paramGraphics2D.fill(this.path);
/* 352 */     this.path = decodePath4();
/* 353 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/* 354 */     paramGraphics2D.fill(this.path);
/* 355 */     this.path = decodePath5();
/* 356 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 357 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndFocused(Graphics2D paramGraphics2D) {
/* 362 */     this.roundRect = decodeRoundRect1();
/* 363 */     paramGraphics2D.setPaint(this.color26);
/* 364 */     paramGraphics2D.fill(this.roundRect);
/* 365 */     this.path = decodePath2();
/* 366 */     paramGraphics2D.setPaint(decodeGradient11(this.path));
/* 367 */     paramGraphics2D.fill(this.path);
/* 368 */     this.path = decodePath3();
/* 369 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 370 */     paramGraphics2D.fill(this.path);
/* 371 */     this.path = decodePath4();
/* 372 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/* 373 */     paramGraphics2D.fill(this.path);
/* 374 */     this.path = decodePath5();
/* 375 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 376 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 381 */     this.path = decodePath1();
/* 382 */     paramGraphics2D.setPaint(this.color51);
/* 383 */     paramGraphics2D.fill(this.path);
/* 384 */     this.path = decodePath2();
/* 385 */     paramGraphics2D.setPaint(decodeGradient11(this.path));
/* 386 */     paramGraphics2D.fill(this.path);
/* 387 */     this.path = decodePath3();
/* 388 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 389 */     paramGraphics2D.fill(this.path);
/* 390 */     this.path = decodePath4();
/* 391 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/* 392 */     paramGraphics2D.fill(this.path);
/* 393 */     this.path = decodePath5();
/* 394 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 395 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndSelected(Graphics2D paramGraphics2D) {
/* 400 */     this.path = decodePath1();
/* 401 */     paramGraphics2D.setPaint(this.color52);
/* 402 */     paramGraphics2D.fill(this.path);
/* 403 */     this.path = decodePath2();
/* 404 */     paramGraphics2D.setPaint(decodeGradient11(this.path));
/* 405 */     paramGraphics2D.fill(this.path);
/* 406 */     this.path = decodePath3();
/* 407 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 408 */     paramGraphics2D.fill(this.path);
/* 409 */     this.path = decodePath4();
/* 410 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/* 411 */     paramGraphics2D.fill(this.path);
/* 412 */     this.path = decodePath5();
/* 413 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 414 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabledAndEditable(Graphics2D paramGraphics2D) {
/* 419 */     this.rect = decodeRect1();
/* 420 */     paramGraphics2D.setPaint(this.color53);
/* 421 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndEditable(Graphics2D paramGraphics2D) {
/* 426 */     this.rect = decodeRect1();
/* 427 */     paramGraphics2D.setPaint(this.color53);
/* 428 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundFocusedAndEditable(Graphics2D paramGraphics2D) {
/* 433 */     this.path = decodePath6();
/* 434 */     paramGraphics2D.setPaint(this.color26);
/* 435 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndEditable(Graphics2D paramGraphics2D) {
/* 440 */     this.rect = decodeRect2();
/* 441 */     paramGraphics2D.setPaint(this.color53);
/* 442 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndEditable(Graphics2D paramGraphics2D) {
/* 447 */     this.rect = decodeRect2();
/* 448 */     paramGraphics2D.setPaint(this.color53);
/* 449 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path2D decodePath1() {
/* 456 */     this.path.reset();
/* 457 */     this.path.moveTo(decodeX(0.22222222F), decodeY(2.0F));
/* 458 */     this.path.lineTo(decodeX(0.22222222F), decodeY(2.25F));
/* 459 */     this.path.curveTo(decodeAnchorX(0.22222222F, 0.0F), decodeAnchorY(2.25F, 3.0F), decodeAnchorX(0.7777778F, -3.0F), decodeAnchorY(2.875F, 0.0F), decodeX(0.7777778F), decodeY(2.875F));
/* 460 */     this.path.lineTo(decodeX(2.631579F), decodeY(2.875F));
/* 461 */     this.path.curveTo(decodeAnchorX(2.631579F, 3.0F), decodeAnchorY(2.875F, 0.0F), decodeAnchorX(2.8947368F, 0.0F), decodeAnchorY(2.25F, 3.0F), decodeX(2.8947368F), decodeY(2.25F));
/* 462 */     this.path.lineTo(decodeX(2.8947368F), decodeY(2.0F));
/* 463 */     this.path.lineTo(decodeX(0.22222222F), decodeY(2.0F));
/* 464 */     this.path.closePath();
/* 465 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 469 */     this.path.reset();
/* 470 */     this.path.moveTo(decodeX(0.22222222F), decodeY(0.875F));
/* 471 */     this.path.lineTo(decodeX(0.22222222F), decodeY(2.125F));
/* 472 */     this.path.curveTo(decodeAnchorX(0.22222222F, 0.0F), decodeAnchorY(2.125F, 3.0F), decodeAnchorX(0.7777778F, -3.0F), decodeAnchorY(2.75F, 0.0F), decodeX(0.7777778F), decodeY(2.75F));
/* 473 */     this.path.lineTo(decodeX(2.0F), decodeY(2.75F));
/* 474 */     this.path.lineTo(decodeX(2.0F), decodeY(0.25F));
/* 475 */     this.path.lineTo(decodeX(0.7777778F), decodeY(0.25F));
/* 476 */     this.path.curveTo(decodeAnchorX(0.7777778F, -3.0F), decodeAnchorY(0.25F, 0.0F), decodeAnchorX(0.22222222F, 0.0F), decodeAnchorY(0.875F, -3.0F), decodeX(0.22222222F), decodeY(0.875F));
/* 477 */     this.path.closePath();
/* 478 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 482 */     this.path.reset();
/* 483 */     this.path.moveTo(decodeX(0.8888889F), decodeY(0.375F));
/* 484 */     this.path.lineTo(decodeX(2.0F), decodeY(0.375F));
/* 485 */     this.path.lineTo(decodeX(2.0F), decodeY(2.625F));
/* 486 */     this.path.lineTo(decodeX(0.8888889F), decodeY(2.625F));
/* 487 */     this.path.curveTo(decodeAnchorX(0.8888889F, -4.0F), decodeAnchorY(2.625F, 0.0F), decodeAnchorX(0.33333334F, 0.0F), decodeAnchorY(2.0F, 4.0F), decodeX(0.33333334F), decodeY(2.0F));
/* 488 */     this.path.lineTo(decodeX(0.33333334F), decodeY(0.875F));
/* 489 */     this.path.curveTo(decodeAnchorX(0.33333334F, 0.0F), decodeAnchorY(0.875F, -3.0F), decodeAnchorX(0.8888889F, -4.0F), decodeAnchorY(0.375F, 0.0F), decodeX(0.8888889F), decodeY(0.375F));
/* 490 */     this.path.closePath();
/* 491 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 495 */     this.path.reset();
/* 496 */     this.path.moveTo(decodeX(2.0F), decodeY(0.25F));
/* 497 */     this.path.lineTo(decodeX(2.631579F), decodeY(0.25F));
/* 498 */     this.path.curveTo(decodeAnchorX(2.631579F, 3.0F), decodeAnchorY(0.25F, 0.0F), decodeAnchorX(2.8947368F, 0.0F), decodeAnchorY(0.875F, -3.0F), decodeX(2.8947368F), decodeY(0.875F));
/* 499 */     this.path.lineTo(decodeX(2.8947368F), decodeY(2.125F));
/* 500 */     this.path.curveTo(decodeAnchorX(2.8947368F, 0.0F), decodeAnchorY(2.125F, 3.0F), decodeAnchorX(2.631579F, 3.0F), decodeAnchorY(2.75F, 0.0F), decodeX(2.631579F), decodeY(2.75F));
/* 501 */     this.path.lineTo(decodeX(2.0F), decodeY(2.75F));
/* 502 */     this.path.lineTo(decodeX(2.0F), decodeY(0.25F));
/* 503 */     this.path.closePath();
/* 504 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 508 */     this.path.reset();
/* 509 */     this.path.moveTo(decodeX(2.0131578F), decodeY(0.375F));
/* 510 */     this.path.lineTo(decodeX(2.5789473F), decodeY(0.375F));
/* 511 */     this.path.curveTo(decodeAnchorX(2.5789473F, 4.0F), decodeAnchorY(0.375F, 0.0F), decodeAnchorX(2.8421054F, 0.0F), decodeAnchorY(1.0F, -4.0F), decodeX(2.8421054F), decodeY(1.0F));
/* 512 */     this.path.lineTo(decodeX(2.8421054F), decodeY(2.0F));
/* 513 */     this.path.curveTo(decodeAnchorX(2.8421054F, 0.0F), decodeAnchorY(2.0F, 4.0F), decodeAnchorX(2.5789473F, 4.0F), decodeAnchorY(2.625F, 0.0F), decodeX(2.5789473F), decodeY(2.625F));
/* 514 */     this.path.lineTo(decodeX(2.0131578F), decodeY(2.625F));
/* 515 */     this.path.lineTo(decodeX(2.0131578F), decodeY(0.375F));
/* 516 */     this.path.closePath();
/* 517 */     return this.path;
/*     */   }
/*     */   
/*     */   private RoundRectangle2D decodeRoundRect1() {
/* 521 */     this.roundRect.setRoundRect(decodeX(0.06666667F), 
/* 522 */         decodeY(0.075F), (
/* 523 */         decodeX(2.9684212F) - decodeX(0.06666667F)), (
/* 524 */         decodeY(2.925F) - decodeY(0.075F)), 13.0D, 13.0D);
/*     */     
/* 526 */     return this.roundRect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 530 */     this.rect.setRect(decodeX(1.4385965F), 
/* 531 */         decodeY(1.4444444F), (
/* 532 */         decodeX(1.4385965F) - decodeX(1.4385965F)), (
/* 533 */         decodeY(1.4444444F) - decodeY(1.4444444F)));
/* 534 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath6() {
/* 538 */     this.path.reset();
/* 539 */     this.path.moveTo(decodeX(0.120000005F), decodeY(0.120000005F));
/* 540 */     this.path.lineTo(decodeX(1.9954545F), decodeY(0.120000005F));
/* 541 */     this.path.curveTo(decodeAnchorX(1.9954545F, 3.0F), decodeAnchorY(0.120000005F, 0.0F), decodeAnchorX(2.8799987F, 0.0F), decodeAnchorY(1.0941176F, -3.0F), decodeX(2.8799987F), decodeY(1.0941176F));
/* 542 */     this.path.lineTo(decodeX(2.8799987F), decodeY(1.964706F));
/* 543 */     this.path.curveTo(decodeAnchorX(2.8799987F, 0.0F), decodeAnchorY(1.964706F, 3.0F), decodeAnchorX(1.9954545F, 3.0F), decodeAnchorY(2.8799999F, 0.0F), decodeX(1.9954545F), decodeY(2.8799999F));
/* 544 */     this.path.lineTo(decodeX(0.120000005F), decodeY(2.8799999F));
/* 545 */     this.path.lineTo(decodeX(0.120000005F), decodeY(0.120000005F));
/* 546 */     this.path.closePath();
/* 547 */     return this.path;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 551 */     this.rect.setRect(decodeX(1.4385965F), 
/* 552 */         decodeY(1.5F), (
/* 553 */         decodeX(1.4385965F) - decodeX(1.4385965F)), (
/* 554 */         decodeY(1.5F) - decodeY(1.5F)));
/* 555 */     return this.rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 561 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 562 */     float f1 = (float)rectangle2D.getX();
/* 563 */     float f2 = (float)rectangle2D.getY();
/* 564 */     float f3 = (float)rectangle2D.getWidth();
/* 565 */     float f4 = (float)rectangle2D.getHeight();
/* 566 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 569 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 574 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 575 */     float f1 = (float)rectangle2D.getX();
/* 576 */     float f2 = (float)rectangle2D.getY();
/* 577 */     float f3 = (float)rectangle2D.getWidth();
/* 578 */     float f4 = (float)rectangle2D.getHeight();
/* 579 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.2002841F, 0.4005682F, 0.5326705F, 0.66477275F, 0.8323864F, 1.0F }, new Color[] { this.color4, 
/*     */ 
/*     */           
/* 582 */           decodeColor(this.color4, this.color5, 0.5F), this.color5, 
/*     */           
/* 584 */           decodeColor(this.color5, this.color6, 0.5F), this.color6, 
/*     */           
/* 586 */           decodeColor(this.color6, this.color7, 0.5F), this.color7 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 591 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 592 */     float f1 = (float)rectangle2D.getX();
/* 593 */     float f2 = (float)rectangle2D.getY();
/* 594 */     float f3 = (float)rectangle2D.getWidth();
/* 595 */     float f4 = (float)rectangle2D.getHeight();
/* 596 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color8, 
/*     */ 
/*     */           
/* 599 */           decodeColor(this.color8, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 604 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 605 */     float f1 = (float)rectangle2D.getX();
/* 606 */     float f2 = (float)rectangle2D.getY();
/* 607 */     float f3 = (float)rectangle2D.getWidth();
/* 608 */     float f4 = (float)rectangle2D.getHeight();
/* 609 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.171875F, 0.34375F, 0.4815341F, 0.6193182F, 0.8096591F, 1.0F }, new Color[] { this.color10, 
/*     */ 
/*     */           
/* 612 */           decodeColor(this.color10, this.color11, 0.5F), this.color11, 
/*     */           
/* 614 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*     */           
/* 616 */           decodeColor(this.color12, this.color13, 0.5F), this.color13 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 621 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 622 */     float f1 = (float)rectangle2D.getX();
/* 623 */     float f2 = (float)rectangle2D.getY();
/* 624 */     float f3 = (float)rectangle2D.getWidth();
/* 625 */     float f4 = (float)rectangle2D.getHeight();
/* 626 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color15, 
/*     */ 
/*     */           
/* 629 */           decodeColor(this.color15, this.color16, 0.5F), this.color16 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 634 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 635 */     float f1 = (float)rectangle2D.getX();
/* 636 */     float f2 = (float)rectangle2D.getY();
/* 637 */     float f3 = (float)rectangle2D.getWidth();
/* 638 */     float f4 = (float)rectangle2D.getHeight();
/* 639 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.2002841F, 0.4005682F, 0.5326705F, 0.66477275F, 0.8323864F, 1.0F }, new Color[] { (Color)this.componentColors[0], 
/*     */ 
/*     */           
/* 642 */           decodeColor((Color)this.componentColors[0], (Color)this.componentColors[1], 0.5F), (Color)this.componentColors[1], 
/*     */           
/* 644 */           decodeColor((Color)this.componentColors[1], (Color)this.componentColors[2], 0.5F), (Color)this.componentColors[2], 
/*     */           
/* 646 */           decodeColor((Color)this.componentColors[2], (Color)this.componentColors[3], 0.5F), (Color)this.componentColors[3] });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 651 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 652 */     float f1 = (float)rectangle2D.getX();
/* 653 */     float f2 = (float)rectangle2D.getY();
/* 654 */     float f3 = (float)rectangle2D.getWidth();
/* 655 */     float f4 = (float)rectangle2D.getHeight();
/* 656 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color20, 
/*     */ 
/*     */           
/* 659 */           decodeColor(this.color20, this.color21, 0.5F), this.color21 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 664 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 665 */     float f1 = (float)rectangle2D.getX();
/* 666 */     float f2 = (float)rectangle2D.getY();
/* 667 */     float f3 = (float)rectangle2D.getWidth();
/* 668 */     float f4 = (float)rectangle2D.getHeight();
/* 669 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.171875F, 0.34375F, 0.4815341F, 0.6193182F, 0.8096591F, 1.0F }, new Color[] { (Color)this.componentColors[4], 
/*     */ 
/*     */           
/* 672 */           decodeColor((Color)this.componentColors[4], (Color)this.componentColors[5], 0.5F), (Color)this.componentColors[5], 
/*     */           
/* 674 */           decodeColor((Color)this.componentColors[5], (Color)this.componentColors[6], 0.5F), (Color)this.componentColors[6], 
/*     */           
/* 676 */           decodeColor((Color)this.componentColors[6], (Color)this.componentColors[7], 0.5F), (Color)this.componentColors[7] });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 681 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 682 */     float f1 = (float)rectangle2D.getX();
/* 683 */     float f2 = (float)rectangle2D.getY();
/* 684 */     float f3 = (float)rectangle2D.getWidth();
/* 685 */     float f4 = (float)rectangle2D.getHeight();
/* 686 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color27, 
/*     */ 
/*     */           
/* 689 */           decodeColor(this.color27, this.color28, 0.5F), this.color28 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient10(Shape paramShape) {
/* 694 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 695 */     float f1 = (float)rectangle2D.getX();
/* 696 */     float f2 = (float)rectangle2D.getY();
/* 697 */     float f3 = (float)rectangle2D.getWidth();
/* 698 */     float f4 = (float)rectangle2D.getHeight();
/* 699 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color33, 
/*     */ 
/*     */           
/* 702 */           decodeColor(this.color33, this.color34, 0.5F), this.color34 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient11(Shape paramShape) {
/* 707 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 708 */     float f1 = (float)rectangle2D.getX();
/* 709 */     float f2 = (float)rectangle2D.getY();
/* 710 */     float f3 = (float)rectangle2D.getWidth();
/* 711 */     float f4 = (float)rectangle2D.getHeight();
/* 712 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color39, 
/*     */ 
/*     */           
/* 715 */           decodeColor(this.color39, this.color40, 0.5F), this.color40 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient12(Shape paramShape) {
/* 720 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 721 */     float f1 = (float)rectangle2D.getX();
/* 722 */     float f2 = (float)rectangle2D.getY();
/* 723 */     float f3 = (float)rectangle2D.getWidth();
/* 724 */     float f4 = (float)rectangle2D.getHeight();
/* 725 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color45, 
/*     */ 
/*     */           
/* 728 */           decodeColor(this.color45, this.color46, 0.5F), this.color46 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ComboBoxPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */