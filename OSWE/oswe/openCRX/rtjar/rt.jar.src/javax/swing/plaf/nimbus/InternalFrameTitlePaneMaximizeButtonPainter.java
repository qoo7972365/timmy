/*      */ package javax.swing.plaf.nimbus;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Path2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.RoundRectangle2D;
/*      */ import javax.swing.JComponent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class InternalFrameTitlePaneMaximizeButtonPainter
/*      */   extends AbstractRegionPainter
/*      */ {
/*      */   static final int BACKGROUND_DISABLED_WINDOWMAXIMIZED = 1;
/*      */   static final int BACKGROUND_ENABLED_WINDOWMAXIMIZED = 2;
/*      */   static final int BACKGROUND_MOUSEOVER_WINDOWMAXIMIZED = 3;
/*      */   static final int BACKGROUND_PRESSED_WINDOWMAXIMIZED = 4;
/*      */   static final int BACKGROUND_ENABLED_WINDOWNOTFOCUSED_WINDOWMAXIMIZED = 5;
/*      */   static final int BACKGROUND_MOUSEOVER_WINDOWNOTFOCUSED_WINDOWMAXIMIZED = 6;
/*      */   static final int BACKGROUND_PRESSED_WINDOWNOTFOCUSED_WINDOWMAXIMIZED = 7;
/*      */   static final int BACKGROUND_DISABLED = 8;
/*      */   static final int BACKGROUND_ENABLED = 9;
/*      */   static final int BACKGROUND_MOUSEOVER = 10;
/*      */   static final int BACKGROUND_PRESSED = 11;
/*      */   static final int BACKGROUND_ENABLED_WINDOWNOTFOCUSED = 12;
/*      */   static final int BACKGROUND_MOUSEOVER_WINDOWNOTFOCUSED = 13;
/*      */   static final int BACKGROUND_PRESSED_WINDOWNOTFOCUSED = 14;
/*      */   private int state;
/*      */   private AbstractRegionPainter.PaintContext ctx;
/*   59 */   private Path2D path = new Path2D.Float();
/*   60 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*   61 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*   62 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   67 */   private Color color1 = decodeColor("nimbusGreen", 0.43362403F, -0.6792196F, 0.054901958F, 0);
/*   68 */   private Color color2 = decodeColor("nimbusGreen", 0.44056845F, -0.631913F, -0.039215684F, 0);
/*   69 */   private Color color3 = decodeColor("nimbusGreen", 0.44056845F, -0.67475206F, 0.06666666F, 0);
/*   70 */   private Color color4 = new Color(255, 200, 0, 255);
/*   71 */   private Color color5 = decodeColor("nimbusGreen", 0.4355179F, -0.6581704F, -0.011764705F, 0);
/*   72 */   private Color color6 = decodeColor("nimbusGreen", 0.44484192F, -0.644647F, -0.031372547F, 0);
/*   73 */   private Color color7 = decodeColor("nimbusGreen", 0.44484192F, -0.6480447F, 0.0F, 0);
/*   74 */   private Color color8 = decodeColor("nimbusGreen", 0.4366002F, -0.6368381F, -0.04705882F, 0);
/*   75 */   private Color color9 = decodeColor("nimbusGreen", 0.44484192F, -0.6423572F, -0.05098039F, 0);
/*   76 */   private Color color10 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.062449392F, 0.07058823F, 0);
/*   77 */   private Color color11 = decodeColor("nimbusBlueGrey", -0.008547008F, -0.04174325F, -0.0039215684F, -13);
/*   78 */   private Color color12 = decodeColor("nimbusBlueGrey", 0.0F, -0.049920253F, 0.031372547F, 0);
/*   79 */   private Color color13 = decodeColor("nimbusBlueGrey", 0.0055555105F, -0.0029994324F, -0.38039216F, -185);
/*   80 */   private Color color14 = decodeColor("nimbusGreen", 0.1627907F, 0.2793296F, -0.6431373F, 0);
/*   81 */   private Color color15 = decodeColor("nimbusGreen", 0.025363803F, 0.2454313F, -0.2392157F, 0);
/*   82 */   private Color color16 = decodeColor("nimbusGreen", 0.02642706F, -0.3456704F, -0.011764705F, 0);
/*   83 */   private Color color17 = decodeColor("nimbusGreen", 0.025363803F, 0.2373128F, -0.23529413F, 0);
/*   84 */   private Color color18 = decodeColor("nimbusGreen", 0.025363803F, 0.0655365F, -0.13333333F, 0);
/*   85 */   private Color color19 = decodeColor("nimbusGreen", -0.0087068975F, -0.009330213F, -0.32156864F, 0);
/*   86 */   private Color color20 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -13);
/*   87 */   private Color color21 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -33);
/*   88 */   private Color color22 = decodeColor("nimbusGreen", 0.1627907F, 0.2793296F, -0.627451F, 0);
/*   89 */   private Color color23 = decodeColor("nimbusGreen", 0.04572721F, 0.2793296F, -0.37254903F, 0);
/*   90 */   private Color color24 = decodeColor("nimbusGreen", 0.009822637F, -0.34243205F, 0.054901958F, 0);
/*   91 */   private Color color25 = decodeColor("nimbusGreen", 0.010559708F, 0.13167858F, -0.11764705F, 0);
/*   92 */   private Color color26 = decodeColor("nimbusGreen", 0.010559708F, 0.12599629F, -0.11372548F, 0);
/*   93 */   private Color color27 = decodeColor("nimbusGreen", 0.010559708F, 9.2053413E-4F, -0.011764705F, 0);
/*   94 */   private Color color28 = decodeColor("nimbusGreen", 0.015249729F, 0.2793296F, -0.22352943F, -49);
/*   95 */   private Color color29 = decodeColor("nimbusGreen", 0.01279068F, 0.2793296F, -0.19215685F, 0);
/*   96 */   private Color color30 = decodeColor("nimbusGreen", 0.013319805F, 0.2793296F, -0.20784315F, 0);
/*   97 */   private Color color31 = decodeColor("nimbusGreen", 0.009604409F, 0.2793296F, -0.16862744F, 0);
/*   98 */   private Color color32 = decodeColor("nimbusGreen", 0.011600211F, 0.2793296F, -0.15294117F, 0);
/*   99 */   private Color color33 = decodeColor("nimbusGreen", 0.011939123F, 0.2793296F, -0.16470587F, 0);
/*  100 */   private Color color34 = decodeColor("nimbusGreen", 0.009506017F, 0.257901F, -0.15294117F, 0);
/*  101 */   private Color color35 = decodeColor("nimbusGreen", -0.17054264F, -0.7206704F, -0.7019608F, 0);
/*  102 */   private Color color36 = decodeColor("nimbusGreen", 0.07804492F, 0.2793296F, -0.47058827F, 0);
/*  103 */   private Color color37 = decodeColor("nimbusGreen", 0.03592503F, -0.23865601F, -0.15686274F, 0);
/*  104 */   private Color color38 = decodeColor("nimbusGreen", 0.035979107F, 0.23766291F, -0.3254902F, 0);
/*  105 */   private Color color39 = decodeColor("nimbusGreen", 0.03690417F, 0.2793296F, -0.33333334F, 0);
/*  106 */   private Color color40 = decodeColor("nimbusGreen", 0.09681849F, 0.2793296F, -0.5137255F, 0);
/*  107 */   private Color color41 = decodeColor("nimbusGreen", 0.06535478F, 0.2793296F, -0.44705883F, 0);
/*  108 */   private Color color42 = decodeColor("nimbusGreen", 0.0675526F, 0.2793296F, -0.454902F, 0);
/*  109 */   private Color color43 = decodeColor("nimbusGreen", 0.060800627F, 0.2793296F, -0.4392157F, 0);
/*  110 */   private Color color44 = decodeColor("nimbusGreen", 0.06419912F, 0.2793296F, -0.42352942F, 0);
/*  111 */   private Color color45 = decodeColor("nimbusGreen", 0.06375685F, 0.2793296F, -0.43137255F, 0);
/*  112 */   private Color color46 = decodeColor("nimbusGreen", 0.048207358F, 0.2793296F, -0.3882353F, 0);
/*  113 */   private Color color47 = decodeColor("nimbusGreen", 0.057156876F, 0.2793296F, -0.42352942F, 0);
/*  114 */   private Color color48 = decodeColor("nimbusGreen", 0.44056845F, -0.62133265F, -0.109803915F, 0);
/*  115 */   private Color color49 = decodeColor("nimbusGreen", 0.44056845F, -0.5843068F, -0.27058825F, 0);
/*  116 */   private Color color50 = decodeColor("nimbusGreen", 0.4294573F, -0.698349F, 0.17647058F, 0);
/*  117 */   private Color color51 = decodeColor("nimbusGreen", 0.45066953F, -0.665394F, 0.07843137F, 0);
/*  118 */   private Color color52 = decodeColor("nimbusGreen", 0.44056845F, -0.65913194F, 0.062745094F, 0);
/*  119 */   private Color color53 = decodeColor("nimbusGreen", 0.44056845F, -0.6609689F, 0.086274505F, 0);
/*  120 */   private Color color54 = decodeColor("nimbusGreen", 0.44056845F, -0.6578432F, 0.04705882F, 0);
/*  121 */   private Color color55 = decodeColor("nimbusGreen", 0.4355179F, -0.6633787F, 0.05098039F, 0);
/*  122 */   private Color color56 = decodeColor("nimbusGreen", 0.4355179F, -0.664548F, 0.06666666F, 0);
/*  123 */   private Color color57 = decodeColor("nimbusBlueGrey", 0.0F, -0.029445238F, -0.30980393F, -13);
/*  124 */   private Color color58 = decodeColor("nimbusBlueGrey", 0.0F, -0.027957506F, -0.31764707F, -33);
/*  125 */   private Color color59 = decodeColor("nimbusGreen", 0.43202144F, -0.64722407F, -0.007843137F, 0);
/*  126 */   private Color color60 = decodeColor("nimbusGreen", 0.44056845F, -0.6339652F, -0.02352941F, 0);
/*  127 */   private Color color61 = new Color(165, 169, 176, 255);
/*  128 */   private Color color62 = decodeColor("nimbusBlueGrey", -0.00505054F, -0.057128258F, 0.062745094F, 0);
/*  129 */   private Color color63 = decodeColor("nimbusBlueGrey", -0.003968239F, -0.035257496F, -0.015686274F, 0);
/*  130 */   private Color color64 = new Color(64, 88, 0, 255);
/*  131 */   private Color color65 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*  132 */   private Color color66 = decodeColor("nimbusBlueGrey", 0.004830897F, -0.00920473F, 0.14509803F, -101);
/*  133 */   private Color color67 = decodeColor("nimbusGreen", 0.009564877F, 0.100521624F, -0.109803915F, 0);
/*  134 */   private Color color68 = new Color(113, 125, 0, 255);
/*  135 */   private Color color69 = decodeColor("nimbusBlueGrey", 0.0025252104F, -0.0067527294F, 0.086274505F, -65);
/*  136 */   private Color color70 = decodeColor("nimbusGreen", 0.03129223F, 0.2793296F, -0.27450982F, 0);
/*  137 */   private Color color71 = new Color(19, 48, 0, 255);
/*  138 */   private Color color72 = decodeColor("nimbusBlueGrey", 0.0F, -0.029445238F, -0.30980393F, 0);
/*      */ 
/*      */   
/*      */   private Object[] componentColors;
/*      */ 
/*      */ 
/*      */   
/*      */   public InternalFrameTitlePaneMaximizeButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  146 */     this.state = paramInt;
/*  147 */     this.ctx = paramPaintContext;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  153 */     this.componentColors = paramArrayOfObject;
/*      */ 
/*      */     
/*  156 */     switch (this.state) { case 1:
/*  157 */         paintBackgroundDisabledAndWindowMaximized(paramGraphics2D); break;
/*  158 */       case 2: paintBackgroundEnabledAndWindowMaximized(paramGraphics2D); break;
/*  159 */       case 3: paintBackgroundMouseOverAndWindowMaximized(paramGraphics2D); break;
/*  160 */       case 4: paintBackgroundPressedAndWindowMaximized(paramGraphics2D); break;
/*  161 */       case 5: paintBackgroundEnabledAndWindowNotFocusedAndWindowMaximized(paramGraphics2D); break;
/*  162 */       case 6: paintBackgroundMouseOverAndWindowNotFocusedAndWindowMaximized(paramGraphics2D); break;
/*  163 */       case 7: paintBackgroundPressedAndWindowNotFocusedAndWindowMaximized(paramGraphics2D); break;
/*  164 */       case 8: paintBackgroundDisabled(paramGraphics2D); break;
/*  165 */       case 9: paintBackgroundEnabled(paramGraphics2D); break;
/*  166 */       case 10: paintBackgroundMouseOver(paramGraphics2D); break;
/*  167 */       case 11: paintBackgroundPressed(paramGraphics2D); break;
/*  168 */       case 12: paintBackgroundEnabledAndWindowNotFocused(paramGraphics2D); break;
/*  169 */       case 13: paintBackgroundMouseOverAndWindowNotFocused(paramGraphics2D); break;
/*  170 */       case 14: paintBackgroundPressedAndWindowNotFocused(paramGraphics2D);
/*      */         break; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  179 */     return this.ctx;
/*      */   }
/*      */   
/*      */   private void paintBackgroundDisabledAndWindowMaximized(Graphics2D paramGraphics2D) {
/*  183 */     this.roundRect = decodeRoundRect1();
/*  184 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/*  185 */     paramGraphics2D.fill(this.roundRect);
/*  186 */     this.roundRect = decodeRoundRect2();
/*  187 */     paramGraphics2D.setPaint(decodeGradient2(this.roundRect));
/*  188 */     paramGraphics2D.fill(this.roundRect);
/*  189 */     this.rect = decodeRect1();
/*  190 */     paramGraphics2D.setPaint(this.color4);
/*  191 */     paramGraphics2D.fill(this.rect);
/*  192 */     this.rect = decodeRect2();
/*  193 */     paramGraphics2D.setPaint(this.color5);
/*  194 */     paramGraphics2D.fill(this.rect);
/*  195 */     this.rect = decodeRect3();
/*  196 */     paramGraphics2D.setPaint(this.color6);
/*  197 */     paramGraphics2D.fill(this.rect);
/*  198 */     this.rect = decodeRect4();
/*  199 */     paramGraphics2D.setPaint(this.color6);
/*  200 */     paramGraphics2D.fill(this.rect);
/*  201 */     this.rect = decodeRect5();
/*  202 */     paramGraphics2D.setPaint(this.color7);
/*  203 */     paramGraphics2D.fill(this.rect);
/*  204 */     this.rect = decodeRect6();
/*  205 */     paramGraphics2D.setPaint(this.color8);
/*  206 */     paramGraphics2D.fill(this.rect);
/*  207 */     this.rect = decodeRect7();
/*  208 */     paramGraphics2D.setPaint(this.color9);
/*  209 */     paramGraphics2D.fill(this.rect);
/*  210 */     this.rect = decodeRect8();
/*  211 */     paramGraphics2D.setPaint(this.color7);
/*  212 */     paramGraphics2D.fill(this.rect);
/*  213 */     this.path = decodePath1();
/*  214 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/*  215 */     paramGraphics2D.fill(this.path);
/*  216 */     this.path = decodePath2();
/*  217 */     paramGraphics2D.setPaint(this.color12);
/*  218 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundEnabledAndWindowMaximized(Graphics2D paramGraphics2D) {
/*  223 */     this.roundRect = decodeRoundRect3();
/*  224 */     paramGraphics2D.setPaint(this.color13);
/*  225 */     paramGraphics2D.fill(this.roundRect);
/*  226 */     this.roundRect = decodeRoundRect1();
/*  227 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/*  228 */     paramGraphics2D.fill(this.roundRect);
/*  229 */     this.roundRect = decodeRoundRect2();
/*  230 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/*  231 */     paramGraphics2D.fill(this.roundRect);
/*  232 */     this.rect = decodeRect1();
/*  233 */     paramGraphics2D.setPaint(this.color4);
/*  234 */     paramGraphics2D.fill(this.rect);
/*  235 */     this.rect = decodeRect2();
/*  236 */     paramGraphics2D.setPaint(this.color19);
/*  237 */     paramGraphics2D.fill(this.rect);
/*  238 */     this.rect = decodeRect3();
/*  239 */     paramGraphics2D.setPaint(this.color19);
/*  240 */     paramGraphics2D.fill(this.rect);
/*  241 */     this.rect = decodeRect4();
/*  242 */     paramGraphics2D.setPaint(this.color19);
/*  243 */     paramGraphics2D.fill(this.rect);
/*  244 */     this.rect = decodeRect5();
/*  245 */     paramGraphics2D.setPaint(this.color19);
/*  246 */     paramGraphics2D.fill(this.rect);
/*  247 */     this.rect = decodeRect9();
/*  248 */     paramGraphics2D.setPaint(this.color19);
/*  249 */     paramGraphics2D.fill(this.rect);
/*  250 */     this.rect = decodeRect7();
/*  251 */     paramGraphics2D.setPaint(this.color19);
/*  252 */     paramGraphics2D.fill(this.rect);
/*  253 */     this.rect = decodeRect10();
/*  254 */     paramGraphics2D.setPaint(this.color19);
/*  255 */     paramGraphics2D.fill(this.rect);
/*  256 */     this.rect = decodeRect8();
/*  257 */     paramGraphics2D.setPaint(this.color19);
/*  258 */     paramGraphics2D.fill(this.rect);
/*  259 */     this.path = decodePath1();
/*  260 */     paramGraphics2D.setPaint(this.color20);
/*  261 */     paramGraphics2D.fill(this.path);
/*  262 */     this.path = decodePath2();
/*  263 */     paramGraphics2D.setPaint(this.color21);
/*  264 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundMouseOverAndWindowMaximized(Graphics2D paramGraphics2D) {
/*  269 */     this.roundRect = decodeRoundRect3();
/*  270 */     paramGraphics2D.setPaint(this.color13);
/*  271 */     paramGraphics2D.fill(this.roundRect);
/*  272 */     this.roundRect = decodeRoundRect1();
/*  273 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/*  274 */     paramGraphics2D.fill(this.roundRect);
/*  275 */     this.roundRect = decodeRoundRect2();
/*  276 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/*  277 */     paramGraphics2D.fill(this.roundRect);
/*  278 */     this.rect = decodeRect1();
/*  279 */     paramGraphics2D.setPaint(this.color4);
/*  280 */     paramGraphics2D.fill(this.rect);
/*  281 */     this.rect = decodeRect2();
/*  282 */     paramGraphics2D.setPaint(this.color28);
/*  283 */     paramGraphics2D.fill(this.rect);
/*  284 */     this.rect = decodeRect3();
/*  285 */     paramGraphics2D.setPaint(this.color29);
/*  286 */     paramGraphics2D.fill(this.rect);
/*  287 */     this.rect = decodeRect4();
/*  288 */     paramGraphics2D.setPaint(this.color30);
/*  289 */     paramGraphics2D.fill(this.rect);
/*  290 */     this.rect = decodeRect5();
/*  291 */     paramGraphics2D.setPaint(this.color31);
/*  292 */     paramGraphics2D.fill(this.rect);
/*  293 */     this.rect = decodeRect9();
/*  294 */     paramGraphics2D.setPaint(this.color32);
/*  295 */     paramGraphics2D.fill(this.rect);
/*  296 */     this.rect = decodeRect7();
/*  297 */     paramGraphics2D.setPaint(this.color33);
/*  298 */     paramGraphics2D.fill(this.rect);
/*  299 */     this.rect = decodeRect10();
/*  300 */     paramGraphics2D.setPaint(this.color34);
/*  301 */     paramGraphics2D.fill(this.rect);
/*  302 */     this.rect = decodeRect8();
/*  303 */     paramGraphics2D.setPaint(this.color31);
/*  304 */     paramGraphics2D.fill(this.rect);
/*  305 */     this.path = decodePath1();
/*  306 */     paramGraphics2D.setPaint(this.color20);
/*  307 */     paramGraphics2D.fill(this.path);
/*  308 */     this.path = decodePath2();
/*  309 */     paramGraphics2D.setPaint(this.color21);
/*  310 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundPressedAndWindowMaximized(Graphics2D paramGraphics2D) {
/*  315 */     this.roundRect = decodeRoundRect3();
/*  316 */     paramGraphics2D.setPaint(this.color13);
/*  317 */     paramGraphics2D.fill(this.roundRect);
/*  318 */     this.roundRect = decodeRoundRect1();
/*  319 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/*  320 */     paramGraphics2D.fill(this.roundRect);
/*  321 */     this.roundRect = decodeRoundRect2();
/*  322 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/*  323 */     paramGraphics2D.fill(this.roundRect);
/*  324 */     this.rect = decodeRect1();
/*  325 */     paramGraphics2D.setPaint(this.color4);
/*  326 */     paramGraphics2D.fill(this.rect);
/*  327 */     this.rect = decodeRect2();
/*  328 */     paramGraphics2D.setPaint(this.color40);
/*  329 */     paramGraphics2D.fill(this.rect);
/*  330 */     this.rect = decodeRect3();
/*  331 */     paramGraphics2D.setPaint(this.color41);
/*  332 */     paramGraphics2D.fill(this.rect);
/*  333 */     this.rect = decodeRect4();
/*  334 */     paramGraphics2D.setPaint(this.color42);
/*  335 */     paramGraphics2D.fill(this.rect);
/*  336 */     this.rect = decodeRect5();
/*  337 */     paramGraphics2D.setPaint(this.color43);
/*  338 */     paramGraphics2D.fill(this.rect);
/*  339 */     this.rect = decodeRect6();
/*  340 */     paramGraphics2D.setPaint(this.color44);
/*  341 */     paramGraphics2D.fill(this.rect);
/*  342 */     this.rect = decodeRect7();
/*  343 */     paramGraphics2D.setPaint(this.color45);
/*  344 */     paramGraphics2D.fill(this.rect);
/*  345 */     this.rect = decodeRect10();
/*  346 */     paramGraphics2D.setPaint(this.color46);
/*  347 */     paramGraphics2D.fill(this.rect);
/*  348 */     this.rect = decodeRect8();
/*  349 */     paramGraphics2D.setPaint(this.color47);
/*  350 */     paramGraphics2D.fill(this.rect);
/*  351 */     this.path = decodePath1();
/*  352 */     paramGraphics2D.setPaint(this.color20);
/*  353 */     paramGraphics2D.fill(this.path);
/*  354 */     this.path = decodePath2();
/*  355 */     paramGraphics2D.setPaint(this.color21);
/*  356 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundEnabledAndWindowNotFocusedAndWindowMaximized(Graphics2D paramGraphics2D) {
/*  361 */     this.roundRect = decodeRoundRect1();
/*  362 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/*  363 */     paramGraphics2D.fill(this.roundRect);
/*  364 */     this.roundRect = decodeRoundRect2();
/*  365 */     paramGraphics2D.setPaint(decodeGradient11(this.roundRect));
/*  366 */     paramGraphics2D.fill(this.roundRect);
/*  367 */     this.rect = decodeRect1();
/*  368 */     paramGraphics2D.setPaint(this.color4);
/*  369 */     paramGraphics2D.fill(this.rect);
/*  370 */     this.rect = decodeRect4();
/*  371 */     paramGraphics2D.setPaint(this.color54);
/*  372 */     paramGraphics2D.fill(this.rect);
/*  373 */     this.rect = decodeRect5();
/*  374 */     paramGraphics2D.setPaint(this.color55);
/*  375 */     paramGraphics2D.fill(this.rect);
/*  376 */     this.rect = decodeRect8();
/*  377 */     paramGraphics2D.setPaint(this.color56);
/*  378 */     paramGraphics2D.fill(this.rect);
/*  379 */     this.path = decodePath1();
/*  380 */     paramGraphics2D.setPaint(this.color57);
/*  381 */     paramGraphics2D.fill(this.path);
/*  382 */     this.path = decodePath2();
/*  383 */     paramGraphics2D.setPaint(this.color58);
/*  384 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundMouseOverAndWindowNotFocusedAndWindowMaximized(Graphics2D paramGraphics2D) {
/*  389 */     this.roundRect = decodeRoundRect3();
/*  390 */     paramGraphics2D.setPaint(this.color13);
/*  391 */     paramGraphics2D.fill(this.roundRect);
/*  392 */     this.roundRect = decodeRoundRect1();
/*  393 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/*  394 */     paramGraphics2D.fill(this.roundRect);
/*  395 */     this.roundRect = decodeRoundRect2();
/*  396 */     paramGraphics2D.setPaint(decodeGradient7(this.roundRect));
/*  397 */     paramGraphics2D.fill(this.roundRect);
/*  398 */     this.rect = decodeRect1();
/*  399 */     paramGraphics2D.setPaint(this.color4);
/*  400 */     paramGraphics2D.fill(this.rect);
/*  401 */     this.rect = decodeRect2();
/*  402 */     paramGraphics2D.setPaint(this.color28);
/*  403 */     paramGraphics2D.fill(this.rect);
/*  404 */     this.rect = decodeRect3();
/*  405 */     paramGraphics2D.setPaint(this.color29);
/*  406 */     paramGraphics2D.fill(this.rect);
/*  407 */     this.rect = decodeRect4();
/*  408 */     paramGraphics2D.setPaint(this.color30);
/*  409 */     paramGraphics2D.fill(this.rect);
/*  410 */     this.rect = decodeRect5();
/*  411 */     paramGraphics2D.setPaint(this.color31);
/*  412 */     paramGraphics2D.fill(this.rect);
/*  413 */     this.rect = decodeRect9();
/*  414 */     paramGraphics2D.setPaint(this.color32);
/*  415 */     paramGraphics2D.fill(this.rect);
/*  416 */     this.rect = decodeRect7();
/*  417 */     paramGraphics2D.setPaint(this.color33);
/*  418 */     paramGraphics2D.fill(this.rect);
/*  419 */     this.rect = decodeRect10();
/*  420 */     paramGraphics2D.setPaint(this.color34);
/*  421 */     paramGraphics2D.fill(this.rect);
/*  422 */     this.rect = decodeRect8();
/*  423 */     paramGraphics2D.setPaint(this.color31);
/*  424 */     paramGraphics2D.fill(this.rect);
/*  425 */     this.path = decodePath1();
/*  426 */     paramGraphics2D.setPaint(this.color20);
/*  427 */     paramGraphics2D.fill(this.path);
/*  428 */     this.path = decodePath2();
/*  429 */     paramGraphics2D.setPaint(this.color21);
/*  430 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundPressedAndWindowNotFocusedAndWindowMaximized(Graphics2D paramGraphics2D) {
/*  435 */     this.roundRect = decodeRoundRect3();
/*  436 */     paramGraphics2D.setPaint(this.color13);
/*  437 */     paramGraphics2D.fill(this.roundRect);
/*  438 */     this.roundRect = decodeRoundRect1();
/*  439 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/*  440 */     paramGraphics2D.fill(this.roundRect);
/*  441 */     this.roundRect = decodeRoundRect2();
/*  442 */     paramGraphics2D.setPaint(decodeGradient9(this.roundRect));
/*  443 */     paramGraphics2D.fill(this.roundRect);
/*  444 */     this.rect = decodeRect1();
/*  445 */     paramGraphics2D.setPaint(this.color4);
/*  446 */     paramGraphics2D.fill(this.rect);
/*  447 */     this.rect = decodeRect2();
/*  448 */     paramGraphics2D.setPaint(this.color40);
/*  449 */     paramGraphics2D.fill(this.rect);
/*  450 */     this.rect = decodeRect3();
/*  451 */     paramGraphics2D.setPaint(this.color41);
/*  452 */     paramGraphics2D.fill(this.rect);
/*  453 */     this.rect = decodeRect4();
/*  454 */     paramGraphics2D.setPaint(this.color42);
/*  455 */     paramGraphics2D.fill(this.rect);
/*  456 */     this.rect = decodeRect5();
/*  457 */     paramGraphics2D.setPaint(this.color43);
/*  458 */     paramGraphics2D.fill(this.rect);
/*  459 */     this.rect = decodeRect6();
/*  460 */     paramGraphics2D.setPaint(this.color44);
/*  461 */     paramGraphics2D.fill(this.rect);
/*  462 */     this.rect = decodeRect7();
/*  463 */     paramGraphics2D.setPaint(this.color45);
/*  464 */     paramGraphics2D.fill(this.rect);
/*  465 */     this.rect = decodeRect10();
/*  466 */     paramGraphics2D.setPaint(this.color46);
/*  467 */     paramGraphics2D.fill(this.rect);
/*  468 */     this.rect = decodeRect8();
/*  469 */     paramGraphics2D.setPaint(this.color47);
/*  470 */     paramGraphics2D.fill(this.rect);
/*  471 */     this.path = decodePath1();
/*  472 */     paramGraphics2D.setPaint(this.color20);
/*  473 */     paramGraphics2D.fill(this.path);
/*  474 */     this.path = decodePath2();
/*  475 */     paramGraphics2D.setPaint(this.color21);
/*  476 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/*  481 */     this.roundRect = decodeRoundRect1();
/*  482 */     paramGraphics2D.setPaint(decodeGradient1(this.roundRect));
/*  483 */     paramGraphics2D.fill(this.roundRect);
/*  484 */     this.roundRect = decodeRoundRect2();
/*  485 */     paramGraphics2D.setPaint(decodeGradient12(this.roundRect));
/*  486 */     paramGraphics2D.fill(this.roundRect);
/*  487 */     this.rect = decodeRect1();
/*  488 */     paramGraphics2D.setPaint(this.color4);
/*  489 */     paramGraphics2D.fill(this.rect);
/*  490 */     this.path = decodePath3();
/*  491 */     paramGraphics2D.setPaint(this.color61);
/*  492 */     paramGraphics2D.fill(this.path);
/*  493 */     this.path = decodePath4();
/*  494 */     paramGraphics2D.setPaint(decodeGradient13(this.path));
/*  495 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/*  500 */     this.roundRect = decodeRoundRect3();
/*  501 */     paramGraphics2D.setPaint(this.color13);
/*  502 */     paramGraphics2D.fill(this.roundRect);
/*  503 */     this.roundRect = decodeRoundRect1();
/*  504 */     paramGraphics2D.setPaint(decodeGradient4(this.roundRect));
/*  505 */     paramGraphics2D.fill(this.roundRect);
/*  506 */     this.roundRect = decodeRoundRect2();
/*  507 */     paramGraphics2D.setPaint(decodeGradient5(this.roundRect));
/*  508 */     paramGraphics2D.fill(this.roundRect);
/*  509 */     this.rect = decodeRect1();
/*  510 */     paramGraphics2D.setPaint(this.color4);
/*  511 */     paramGraphics2D.fill(this.rect);
/*  512 */     this.path = decodePath3();
/*  513 */     paramGraphics2D.setPaint(this.color64);
/*  514 */     paramGraphics2D.fill(this.path);
/*  515 */     this.path = decodePath4();
/*  516 */     paramGraphics2D.setPaint(this.color65);
/*  517 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/*  522 */     this.roundRect = decodeRoundRect3();
/*  523 */     paramGraphics2D.setPaint(this.color66);
/*  524 */     paramGraphics2D.fill(this.roundRect);
/*  525 */     this.roundRect = decodeRoundRect1();
/*  526 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/*  527 */     paramGraphics2D.fill(this.roundRect);
/*  528 */     this.roundRect = decodeRoundRect2();
/*  529 */     paramGraphics2D.setPaint(decodeGradient14(this.roundRect));
/*  530 */     paramGraphics2D.fill(this.roundRect);
/*  531 */     this.rect = decodeRect1();
/*  532 */     paramGraphics2D.setPaint(this.color4);
/*  533 */     paramGraphics2D.fill(this.rect);
/*  534 */     this.path = decodePath3();
/*  535 */     paramGraphics2D.setPaint(this.color68);
/*  536 */     paramGraphics2D.fill(this.path);
/*  537 */     this.path = decodePath4();
/*  538 */     paramGraphics2D.setPaint(this.color65);
/*  539 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/*  544 */     this.roundRect = decodeRoundRect3();
/*  545 */     paramGraphics2D.setPaint(this.color69);
/*  546 */     paramGraphics2D.fill(this.roundRect);
/*  547 */     this.roundRect = decodeRoundRect1();
/*  548 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/*  549 */     paramGraphics2D.fill(this.roundRect);
/*  550 */     this.roundRect = decodeRoundRect2();
/*  551 */     paramGraphics2D.setPaint(decodeGradient15(this.roundRect));
/*  552 */     paramGraphics2D.fill(this.roundRect);
/*  553 */     this.rect = decodeRect1();
/*  554 */     paramGraphics2D.setPaint(this.color4);
/*  555 */     paramGraphics2D.fill(this.rect);
/*  556 */     this.path = decodePath3();
/*  557 */     paramGraphics2D.setPaint(this.color71);
/*  558 */     paramGraphics2D.fill(this.path);
/*  559 */     this.path = decodePath4();
/*  560 */     paramGraphics2D.setPaint(this.color65);
/*  561 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundEnabledAndWindowNotFocused(Graphics2D paramGraphics2D) {
/*  566 */     this.roundRect = decodeRoundRect1();
/*  567 */     paramGraphics2D.setPaint(decodeGradient10(this.roundRect));
/*  568 */     paramGraphics2D.fill(this.roundRect);
/*  569 */     this.roundRect = decodeRoundRect2();
/*  570 */     paramGraphics2D.setPaint(decodeGradient16(this.roundRect));
/*  571 */     paramGraphics2D.fill(this.roundRect);
/*  572 */     this.rect = decodeRect1();
/*  573 */     paramGraphics2D.setPaint(this.color4);
/*  574 */     paramGraphics2D.fill(this.rect);
/*  575 */     this.path = decodePath4();
/*  576 */     paramGraphics2D.setPaint(this.color72);
/*  577 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundMouseOverAndWindowNotFocused(Graphics2D paramGraphics2D) {
/*  582 */     this.roundRect = decodeRoundRect3();
/*  583 */     paramGraphics2D.setPaint(this.color66);
/*  584 */     paramGraphics2D.fill(this.roundRect);
/*  585 */     this.roundRect = decodeRoundRect1();
/*  586 */     paramGraphics2D.setPaint(decodeGradient6(this.roundRect));
/*  587 */     paramGraphics2D.fill(this.roundRect);
/*  588 */     this.roundRect = decodeRoundRect2();
/*  589 */     paramGraphics2D.setPaint(decodeGradient14(this.roundRect));
/*  590 */     paramGraphics2D.fill(this.roundRect);
/*  591 */     this.rect = decodeRect1();
/*  592 */     paramGraphics2D.setPaint(this.color4);
/*  593 */     paramGraphics2D.fill(this.rect);
/*  594 */     this.path = decodePath3();
/*  595 */     paramGraphics2D.setPaint(this.color68);
/*  596 */     paramGraphics2D.fill(this.path);
/*  597 */     this.path = decodePath4();
/*  598 */     paramGraphics2D.setPaint(this.color65);
/*  599 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintBackgroundPressedAndWindowNotFocused(Graphics2D paramGraphics2D) {
/*  604 */     this.roundRect = decodeRoundRect3();
/*  605 */     paramGraphics2D.setPaint(this.color69);
/*  606 */     paramGraphics2D.fill(this.roundRect);
/*  607 */     this.roundRect = decodeRoundRect1();
/*  608 */     paramGraphics2D.setPaint(decodeGradient8(this.roundRect));
/*  609 */     paramGraphics2D.fill(this.roundRect);
/*  610 */     this.roundRect = decodeRoundRect2();
/*  611 */     paramGraphics2D.setPaint(decodeGradient15(this.roundRect));
/*  612 */     paramGraphics2D.fill(this.roundRect);
/*  613 */     this.rect = decodeRect1();
/*  614 */     paramGraphics2D.setPaint(this.color4);
/*  615 */     paramGraphics2D.fill(this.rect);
/*  616 */     this.path = decodePath3();
/*  617 */     paramGraphics2D.setPaint(this.color71);
/*  618 */     paramGraphics2D.fill(this.path);
/*  619 */     this.path = decodePath4();
/*  620 */     paramGraphics2D.setPaint(this.color65);
/*  621 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RoundRectangle2D decodeRoundRect1() {
/*  628 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/*  629 */         decodeY(1.0F), (
/*  630 */         decodeX(2.0F) - decodeX(1.0F)), (
/*  631 */         decodeY(1.9444444F) - decodeY(1.0F)), 8.600000381469727D, 8.600000381469727D);
/*      */     
/*  633 */     return this.roundRect;
/*      */   }
/*      */   
/*      */   private RoundRectangle2D decodeRoundRect2() {
/*  637 */     this.roundRect.setRoundRect(decodeX(1.0526316F), 
/*  638 */         decodeY(1.0555556F), (
/*  639 */         decodeX(1.9473684F) - decodeX(1.0526316F)), (
/*  640 */         decodeY(1.8888888F) - decodeY(1.0555556F)), 6.75D, 6.75D);
/*      */     
/*  642 */     return this.roundRect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect1() {
/*  646 */     this.rect.setRect(decodeX(1.0F), 
/*  647 */         decodeY(1.0F), (
/*  648 */         decodeX(1.0F) - decodeX(1.0F)), (
/*  649 */         decodeY(1.0F) - decodeY(1.0F)));
/*  650 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect2() {
/*  654 */     this.rect.setRect(decodeX(1.2165072F), 
/*  655 */         decodeY(1.2790405F), (
/*  656 */         decodeX(1.6746411F) - decodeX(1.2165072F)), (
/*  657 */         decodeY(1.3876263F) - decodeY(1.2790405F)));
/*  658 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect3() {
/*  662 */     this.rect.setRect(decodeX(1.2212919F), 
/*  663 */         decodeY(1.6047981F), (
/*  664 */         decodeX(1.270335F) - decodeX(1.2212919F)), (
/*  665 */         decodeY(1.3876263F) - decodeY(1.6047981F)));
/*  666 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect4() {
/*  670 */     this.rect.setRect(decodeX(1.2643541F), 
/*  671 */         decodeY(1.5542929F), (
/*  672 */         decodeX(1.6315789F) - decodeX(1.2643541F)), (
/*  673 */         decodeY(1.5997474F) - decodeY(1.5542929F)));
/*  674 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect5() {
/*  678 */     this.rect.setRect(decodeX(1.6267943F), 
/*  679 */         decodeY(1.3888888F), (
/*  680 */         decodeX(1.673445F) - decodeX(1.6267943F)), (
/*  681 */         decodeY(1.6085858F) - decodeY(1.3888888F)));
/*  682 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect6() {
/*  686 */     this.rect.setRect(decodeX(1.3684211F), 
/*  687 */         decodeY(1.6111112F), (
/*  688 */         decodeX(1.4210527F) - decodeX(1.3684211F)), (
/*  689 */         decodeY(1.7777778F) - decodeY(1.6111112F)));
/*  690 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect7() {
/*  694 */     this.rect.setRect(decodeX(1.4389952F), 
/*  695 */         decodeY(1.7209597F), (
/*  696 */         decodeX(1.7882775F) - decodeX(1.4389952F)), (
/*  697 */         decodeY(1.7765152F) - decodeY(1.7209597F)));
/*  698 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect8() {
/*  702 */     this.rect.setRect(decodeX(1.5645933F), 
/*  703 */         decodeY(1.4078283F), (
/*  704 */         decodeX(1.7870812F) - decodeX(1.5645933F)), (
/*  705 */         decodeY(1.5239899F) - decodeY(1.4078283F)));
/*  706 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath1() {
/*  710 */     this.path.reset();
/*  711 */     this.path.moveTo(decodeX(1.2105263F), decodeY(1.2222222F));
/*  712 */     this.path.lineTo(decodeX(1.6315789F), decodeY(1.2222222F));
/*  713 */     this.path.lineTo(decodeX(1.6315789F), decodeY(1.5555556F));
/*  714 */     this.path.lineTo(decodeX(1.2105263F), decodeY(1.5555556F));
/*  715 */     this.path.lineTo(decodeX(1.2105263F), decodeY(1.3333334F));
/*  716 */     this.path.lineTo(decodeX(1.2631578F), decodeY(1.3333334F));
/*  717 */     this.path.lineTo(decodeX(1.2631578F), decodeY(1.5F));
/*  718 */     this.path.lineTo(decodeX(1.5789473F), decodeY(1.5F));
/*  719 */     this.path.lineTo(decodeX(1.5789473F), decodeY(1.3333334F));
/*  720 */     this.path.lineTo(decodeX(1.2105263F), decodeY(1.3333334F));
/*  721 */     this.path.lineTo(decodeX(1.2105263F), decodeY(1.2222222F));
/*  722 */     this.path.closePath();
/*  723 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath2() {
/*  727 */     this.path.reset();
/*  728 */     this.path.moveTo(decodeX(1.6842105F), decodeY(1.3888888F));
/*  729 */     this.path.lineTo(decodeX(1.6842105F), decodeY(1.5F));
/*  730 */     this.path.lineTo(decodeX(1.7368422F), decodeY(1.5F));
/*  731 */     this.path.lineTo(decodeX(1.7368422F), decodeY(1.6666667F));
/*  732 */     this.path.lineTo(decodeX(1.4210527F), decodeY(1.6666667F));
/*  733 */     this.path.lineTo(decodeX(1.4210527F), decodeY(1.6111112F));
/*  734 */     this.path.lineTo(decodeX(1.3684211F), decodeY(1.6111112F));
/*  735 */     this.path.lineTo(decodeX(1.3684211F), decodeY(1.7222222F));
/*  736 */     this.path.lineTo(decodeX(1.7894738F), decodeY(1.7222222F));
/*  737 */     this.path.lineTo(decodeX(1.7894738F), decodeY(1.3888888F));
/*  738 */     this.path.lineTo(decodeX(1.6842105F), decodeY(1.3888888F));
/*  739 */     this.path.closePath();
/*  740 */     return this.path;
/*      */   }
/*      */   
/*      */   private RoundRectangle2D decodeRoundRect3() {
/*  744 */     this.roundRect.setRoundRect(decodeX(1.0F), 
/*  745 */         decodeY(1.6111112F), (
/*  746 */         decodeX(2.0F) - decodeX(1.0F)), (
/*  747 */         decodeY(2.0F) - decodeY(1.6111112F)), 6.0D, 6.0D);
/*      */     
/*  749 */     return this.roundRect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect9() {
/*  753 */     this.rect.setRect(decodeX(1.3815789F), 
/*  754 */         decodeY(1.6111112F), (
/*  755 */         decodeX(1.4366028F) - decodeX(1.3815789F)), (
/*  756 */         decodeY(1.7739899F) - decodeY(1.6111112F)));
/*  757 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect10() {
/*  761 */     this.rect.setRect(decodeX(1.7918661F), 
/*  762 */         decodeY(1.7752526F), (
/*  763 */         decodeX(1.8349283F) - decodeX(1.7918661F)), (
/*  764 */         decodeY(1.4217172F) - decodeY(1.7752526F)));
/*  765 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath3() {
/*  769 */     this.path.reset();
/*  770 */     this.path.moveTo(decodeX(1.1913875F), decodeY(1.2916666F));
/*  771 */     this.path.lineTo(decodeX(1.1925838F), decodeY(1.7462121F));
/*  772 */     this.path.lineTo(decodeX(1.8157895F), decodeY(1.7449496F));
/*  773 */     this.path.lineTo(decodeX(1.819378F), decodeY(1.2916666F));
/*  774 */     this.path.lineTo(decodeX(1.722488F), decodeY(1.2916666F));
/*  775 */     this.path.lineTo(decodeX(1.7320573F), decodeY(1.669192F));
/*  776 */     this.path.lineTo(decodeX(1.2799044F), decodeY(1.6565657F));
/*  777 */     this.path.lineTo(decodeX(1.284689F), decodeY(1.3863636F));
/*  778 */     this.path.lineTo(decodeX(1.7260766F), decodeY(1.385101F));
/*  779 */     this.path.lineTo(decodeX(1.722488F), decodeY(1.2904041F));
/*  780 */     this.path.lineTo(decodeX(1.1913875F), decodeY(1.2916666F));
/*  781 */     this.path.closePath();
/*  782 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath4() {
/*  786 */     this.path.reset();
/*  787 */     this.path.moveTo(decodeX(1.2105263F), decodeY(1.2222222F));
/*  788 */     this.path.lineTo(decodeX(1.2105263F), decodeY(1.7222222F));
/*  789 */     this.path.lineTo(decodeX(1.7894738F), decodeY(1.7222222F));
/*  790 */     this.path.lineTo(decodeX(1.7894738F), decodeY(1.3333334F));
/*  791 */     this.path.lineTo(decodeX(1.7368422F), decodeY(1.3333334F));
/*  792 */     this.path.lineTo(decodeX(1.7368422F), decodeY(1.6666667F));
/*  793 */     this.path.lineTo(decodeX(1.2631578F), decodeY(1.6666667F));
/*  794 */     this.path.lineTo(decodeX(1.2631578F), decodeY(1.3333334F));
/*  795 */     this.path.lineTo(decodeX(1.7894738F), decodeY(1.3333334F));
/*  796 */     this.path.lineTo(decodeX(1.7894738F), decodeY(1.2222222F));
/*  797 */     this.path.lineTo(decodeX(1.2105263F), decodeY(1.2222222F));
/*  798 */     this.path.closePath();
/*  799 */     return this.path;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Paint decodeGradient1(Shape paramShape) {
/*  805 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  806 */     float f1 = (float)rectangle2D.getX();
/*  807 */     float f2 = (float)rectangle2D.getY();
/*  808 */     float f3 = (float)rectangle2D.getWidth();
/*  809 */     float f4 = (float)rectangle2D.getHeight();
/*  810 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*      */ 
/*      */           
/*  813 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient2(Shape paramShape) {
/*  818 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  819 */     float f1 = (float)rectangle2D.getX();
/*  820 */     float f2 = (float)rectangle2D.getY();
/*  821 */     float f3 = (float)rectangle2D.getWidth();
/*  822 */     float f4 = (float)rectangle2D.getHeight();
/*  823 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color3, 
/*      */ 
/*      */           
/*  826 */           decodeColor(this.color3, this.color2, 0.5F), this.color2 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient3(Shape paramShape) {
/*  831 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  832 */     float f1 = (float)rectangle2D.getX();
/*  833 */     float f2 = (float)rectangle2D.getY();
/*  834 */     float f3 = (float)rectangle2D.getWidth();
/*  835 */     float f4 = (float)rectangle2D.getHeight();
/*  836 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color10, 
/*      */ 
/*      */           
/*  839 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient4(Shape paramShape) {
/*  844 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  845 */     float f1 = (float)rectangle2D.getX();
/*  846 */     float f2 = (float)rectangle2D.getY();
/*  847 */     float f3 = (float)rectangle2D.getWidth();
/*  848 */     float f4 = (float)rectangle2D.getHeight();
/*  849 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color14, 
/*      */ 
/*      */           
/*  852 */           decodeColor(this.color14, this.color15, 0.5F), this.color15 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient5(Shape paramShape) {
/*  857 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  858 */     float f1 = (float)rectangle2D.getX();
/*  859 */     float f2 = (float)rectangle2D.getY();
/*  860 */     float f3 = (float)rectangle2D.getWidth();
/*  861 */     float f4 = (float)rectangle2D.getHeight();
/*  862 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color16, 
/*      */ 
/*      */           
/*  865 */           decodeColor(this.color16, this.color15, 0.5F), this.color15, 
/*      */           
/*  867 */           decodeColor(this.color15, this.color17, 0.5F), this.color17, 
/*      */           
/*  869 */           decodeColor(this.color17, this.color18, 0.5F), this.color18 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient6(Shape paramShape) {
/*  874 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  875 */     float f1 = (float)rectangle2D.getX();
/*  876 */     float f2 = (float)rectangle2D.getY();
/*  877 */     float f3 = (float)rectangle2D.getWidth();
/*  878 */     float f4 = (float)rectangle2D.getHeight();
/*  879 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color22, 
/*      */ 
/*      */           
/*  882 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient7(Shape paramShape) {
/*  887 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  888 */     float f1 = (float)rectangle2D.getX();
/*  889 */     float f2 = (float)rectangle2D.getY();
/*  890 */     float f3 = (float)rectangle2D.getWidth();
/*  891 */     float f4 = (float)rectangle2D.getHeight();
/*  892 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color24, 
/*      */ 
/*      */           
/*  895 */           decodeColor(this.color24, this.color25, 0.5F), this.color25, 
/*      */           
/*  897 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*      */           
/*  899 */           decodeColor(this.color26, this.color27, 0.5F), this.color27 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient8(Shape paramShape) {
/*  904 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  905 */     float f1 = (float)rectangle2D.getX();
/*  906 */     float f2 = (float)rectangle2D.getY();
/*  907 */     float f3 = (float)rectangle2D.getWidth();
/*  908 */     float f4 = (float)rectangle2D.getHeight();
/*  909 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color35, 
/*      */ 
/*      */           
/*  912 */           decodeColor(this.color35, this.color36, 0.5F), this.color36 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient9(Shape paramShape) {
/*  917 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  918 */     float f1 = (float)rectangle2D.getX();
/*  919 */     float f2 = (float)rectangle2D.getY();
/*  920 */     float f3 = (float)rectangle2D.getWidth();
/*  921 */     float f4 = (float)rectangle2D.getHeight();
/*  922 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color37, 
/*      */ 
/*      */           
/*  925 */           decodeColor(this.color37, this.color38, 0.5F), this.color38, 
/*      */           
/*  927 */           decodeColor(this.color38, this.color39, 0.5F), this.color39, 
/*      */           
/*  929 */           decodeColor(this.color39, this.color18, 0.5F), this.color18 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient10(Shape paramShape) {
/*  934 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  935 */     float f1 = (float)rectangle2D.getX();
/*  936 */     float f2 = (float)rectangle2D.getY();
/*  937 */     float f3 = (float)rectangle2D.getWidth();
/*  938 */     float f4 = (float)rectangle2D.getHeight();
/*  939 */     return decodeGradient(0.24868421F * f3 + f1, 0.0014705883F * f4 + f2, 0.24868421F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color48, 
/*      */ 
/*      */           
/*  942 */           decodeColor(this.color48, this.color49, 0.5F), this.color49 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient11(Shape paramShape) {
/*  947 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  948 */     float f1 = (float)rectangle2D.getX();
/*  949 */     float f2 = (float)rectangle2D.getY();
/*  950 */     float f3 = (float)rectangle2D.getWidth();
/*  951 */     float f4 = (float)rectangle2D.getHeight();
/*  952 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color50, 
/*      */ 
/*      */           
/*  955 */           decodeColor(this.color50, this.color51, 0.5F), this.color51, 
/*      */           
/*  957 */           decodeColor(this.color51, this.color52, 0.5F), this.color52, 
/*      */           
/*  959 */           decodeColor(this.color52, this.color53, 0.5F), this.color53 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient12(Shape paramShape) {
/*  964 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  965 */     float f1 = (float)rectangle2D.getX();
/*  966 */     float f2 = (float)rectangle2D.getY();
/*  967 */     float f3 = (float)rectangle2D.getWidth();
/*  968 */     float f4 = (float)rectangle2D.getHeight();
/*  969 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.6082097F, 0.6766467F, 0.83832335F, 1.0F }, new Color[] { this.color3, 
/*      */ 
/*      */           
/*  972 */           decodeColor(this.color3, this.color59, 0.5F), this.color59, 
/*      */           
/*  974 */           decodeColor(this.color59, this.color60, 0.5F), this.color60, 
/*      */           
/*  976 */           decodeColor(this.color60, this.color2, 0.5F), this.color2 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient13(Shape paramShape) {
/*  981 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  982 */     float f1 = (float)rectangle2D.getX();
/*  983 */     float f2 = (float)rectangle2D.getY();
/*  984 */     float f3 = (float)rectangle2D.getWidth();
/*  985 */     float f4 = (float)rectangle2D.getHeight();
/*  986 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.26047903F, 0.6302395F, 1.0F }, new Color[] { this.color62, 
/*      */ 
/*      */           
/*  989 */           decodeColor(this.color62, this.color63, 0.5F), this.color63 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient14(Shape paramShape) {
/*  994 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*  995 */     float f1 = (float)rectangle2D.getX();
/*  996 */     float f2 = (float)rectangle2D.getY();
/*  997 */     float f3 = (float)rectangle2D.getWidth();
/*  998 */     float f4 = (float)rectangle2D.getHeight();
/*  999 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.5951705F, 0.6505682F, 0.8252841F, 1.0F }, new Color[] { this.color24, 
/*      */ 
/*      */           
/* 1002 */           decodeColor(this.color24, this.color67, 0.5F), this.color67, 
/*      */           
/* 1004 */           decodeColor(this.color67, this.color25, 0.5F), this.color25, 
/*      */           
/* 1006 */           decodeColor(this.color25, this.color27, 0.5F), this.color27 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient15(Shape paramShape) {
/* 1011 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1012 */     float f1 = (float)rectangle2D.getX();
/* 1013 */     float f2 = (float)rectangle2D.getY();
/* 1014 */     float f3 = (float)rectangle2D.getWidth();
/* 1015 */     float f4 = (float)rectangle2D.getHeight();
/* 1016 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.66659296F, 0.79341316F, 0.8967066F, 1.0F }, new Color[] { this.color37, 
/*      */ 
/*      */           
/* 1019 */           decodeColor(this.color37, this.color38, 0.5F), this.color38, 
/*      */           
/* 1021 */           decodeColor(this.color38, this.color39, 0.5F), this.color39, 
/*      */           
/* 1023 */           decodeColor(this.color39, this.color70, 0.5F), this.color70 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient16(Shape paramShape) {
/* 1028 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1029 */     float f1 = (float)rectangle2D.getX();
/* 1030 */     float f2 = (float)rectangle2D.getY();
/* 1031 */     float f3 = (float)rectangle2D.getWidth();
/* 1032 */     float f4 = (float)rectangle2D.getHeight();
/* 1033 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.25441176F * f3 + f1, 1.0016667F * f4 + f2, new float[] { 0.0F, 0.26988637F, 0.53977275F, 0.6291678F, 0.7185629F, 0.8592814F, 1.0F }, new Color[] { this.color50, 
/*      */ 
/*      */           
/* 1036 */           decodeColor(this.color50, this.color52, 0.5F), this.color52, 
/*      */           
/* 1038 */           decodeColor(this.color52, this.color52, 0.5F), this.color52, 
/*      */           
/* 1040 */           decodeColor(this.color52, this.color53, 0.5F), this.color53 });
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/InternalFrameTitlePaneMaximizeButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */