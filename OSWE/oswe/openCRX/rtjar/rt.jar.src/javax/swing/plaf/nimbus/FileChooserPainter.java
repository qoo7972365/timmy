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
/*      */ final class FileChooserPainter
/*      */   extends AbstractRegionPainter
/*      */ {
/*      */   static final int BACKGROUND_ENABLED = 1;
/*      */   static final int FILEICON_ENABLED = 2;
/*      */   static final int DIRECTORYICON_ENABLED = 3;
/*      */   static final int UPFOLDERICON_ENABLED = 4;
/*      */   static final int NEWFOLDERICON_ENABLED = 5;
/*      */   static final int COMPUTERICON_ENABLED = 6;
/*      */   static final int HARDDRIVEICON_ENABLED = 7;
/*      */   static final int FLOPPYDRIVEICON_ENABLED = 8;
/*      */   static final int HOMEFOLDERICON_ENABLED = 9;
/*      */   static final int DETAILSVIEWICON_ENABLED = 10;
/*      */   static final int LISTVIEWICON_ENABLED = 11;
/*      */   private int state;
/*      */   private AbstractRegionPainter.PaintContext ctx;
/*   56 */   private Path2D path = new Path2D.Float();
/*   57 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*   58 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*   59 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   private Color color1 = decodeColor("control", 0.0F, 0.0F, 0.0F, 0);
/*   65 */   private Color color2 = decodeColor("nimbusBlueGrey", 0.007936537F, -0.065654516F, -0.13333333F, 0);
/*   66 */   private Color color3 = new Color(97, 98, 102, 255);
/*   67 */   private Color color4 = decodeColor("nimbusBlueGrey", -0.032679737F, -0.043332636F, 0.24705881F, 0);
/*   68 */   private Color color5 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*   69 */   private Color color6 = decodeColor("nimbusBase", 0.0077680945F, -0.51781034F, 0.3490196F, 0);
/*   70 */   private Color color7 = decodeColor("nimbusBase", 0.013940871F, -0.599277F, 0.41960782F, 0);
/*   71 */   private Color color8 = decodeColor("nimbusBase", 0.004681647F, -0.4198052F, 0.14117646F, 0);
/*   72 */   private Color color9 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, -127);
/*   73 */   private Color color10 = decodeColor("nimbusBlueGrey", 0.0F, 0.0F, -0.21F, -99);
/*   74 */   private Color color11 = decodeColor("nimbusBase", 2.9569864E-4F, -0.45978838F, 0.2980392F, 0);
/*   75 */   private Color color12 = decodeColor("nimbusBase", 0.0015952587F, -0.34848025F, 0.18823528F, 0);
/*   76 */   private Color color13 = decodeColor("nimbusBase", 0.0015952587F, -0.30844158F, 0.09803921F, 0);
/*   77 */   private Color color14 = decodeColor("nimbusBase", 0.0015952587F, -0.27329817F, 0.035294116F, 0);
/*   78 */   private Color color15 = decodeColor("nimbusBase", 0.004681647F, -0.6198413F, 0.43921566F, 0);
/*   79 */   private Color color16 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, -125);
/*   80 */   private Color color17 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, -50);
/*   81 */   private Color color18 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, -100);
/*   82 */   private Color color19 = decodeColor("nimbusBase", 0.0012094378F, -0.23571429F, -0.0784314F, 0);
/*   83 */   private Color color20 = decodeColor("nimbusBase", 2.9569864E-4F, -0.115166366F, -0.2627451F, 0);
/*   84 */   private Color color21 = decodeColor("nimbusBase", 0.0027436614F, -0.335015F, 0.011764705F, 0);
/*   85 */   private Color color22 = decodeColor("nimbusBase", 0.0024294257F, -0.3857143F, 0.031372547F, 0);
/*   86 */   private Color color23 = decodeColor("nimbusBase", 0.0018081069F, -0.3595238F, -0.13725492F, 0);
/*   87 */   private Color color24 = new Color(255, 200, 0, 255);
/*   88 */   private Color color25 = decodeColor("nimbusBase", 0.004681647F, -0.44904763F, 0.039215684F, 0);
/*   89 */   private Color color26 = decodeColor("nimbusBase", 0.0015952587F, -0.43718487F, -0.015686274F, 0);
/*   90 */   private Color color27 = decodeColor("nimbusBase", 2.9569864E-4F, -0.39212453F, -0.24313727F, 0);
/*   91 */   private Color color28 = decodeColor("nimbusBase", 0.004681647F, -0.6117143F, 0.43137252F, 0);
/*   92 */   private Color color29 = decodeColor("nimbusBase", 0.0012094378F, -0.28015873F, -0.019607842F, 0);
/*   93 */   private Color color30 = decodeColor("nimbusBase", 0.00254488F, -0.07049692F, -0.2784314F, 0);
/*   94 */   private Color color31 = decodeColor("nimbusBase", 0.0015952587F, -0.28045115F, 0.04705882F, 0);
/*   95 */   private Color color32 = decodeColor("nimbusBlueGrey", 0.0F, 5.847961E-4F, -0.21568626F, 0);
/*   96 */   private Color color33 = decodeColor("nimbusBase", -0.0061469674F, 0.3642857F, 0.14509803F, 0);
/*   97 */   private Color color34 = decodeColor("nimbusBase", 0.0053939223F, 0.3642857F, -0.0901961F, 0);
/*   98 */   private Color color35 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*   99 */   private Color color36 = decodeColor("nimbusBase", -0.006044388F, -0.23963585F, 0.45098037F, 0);
/*  100 */   private Color color37 = decodeColor("nimbusBase", -0.0063245893F, 0.01592505F, 0.4078431F, 0);
/*  101 */   private Color color38 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, -170);
/*  102 */   private Color color39 = decodeColor("nimbusOrange", -0.032758567F, -0.018273294F, 0.25098038F, 0);
/*  103 */   private Color color40 = new Color(255, 255, 255, 255);
/*  104 */   private Color color41 = new Color(252, 255, 92, 255);
/*  105 */   private Color color42 = new Color(253, 191, 4, 255);
/*  106 */   private Color color43 = new Color(160, 161, 163, 255);
/*  107 */   private Color color44 = new Color(0, 0, 0, 255);
/*  108 */   private Color color45 = new Color(239, 241, 243, 255);
/*  109 */   private Color color46 = new Color(197, 201, 205, 255);
/*  110 */   private Color color47 = new Color(105, 110, 118, 255);
/*  111 */   private Color color48 = new Color(63, 67, 72, 255);
/*  112 */   private Color color49 = new Color(56, 51, 25, 255);
/*  113 */   private Color color50 = new Color(144, 255, 0, 255);
/*  114 */   private Color color51 = new Color(243, 245, 246, 255);
/*  115 */   private Color color52 = new Color(208, 212, 216, 255);
/*  116 */   private Color color53 = new Color(191, 193, 194, 255);
/*  117 */   private Color color54 = new Color(170, 172, 175, 255);
/*  118 */   private Color color55 = new Color(152, 155, 158, 255);
/*  119 */   private Color color56 = new Color(59, 62, 66, 255);
/*  120 */   private Color color57 = new Color(46, 46, 46, 255);
/*  121 */   private Color color58 = new Color(64, 64, 64, 255);
/*  122 */   private Color color59 = new Color(43, 43, 43, 255);
/*  123 */   private Color color60 = new Color(164, 179, 206, 255);
/*  124 */   private Color color61 = new Color(97, 123, 170, 255);
/*  125 */   private Color color62 = new Color(53, 86, 146, 255);
/*  126 */   private Color color63 = new Color(48, 82, 144, 255);
/*  127 */   private Color color64 = new Color(71, 99, 150, 255);
/*  128 */   private Color color65 = new Color(224, 224, 224, 255);
/*  129 */   private Color color66 = new Color(232, 232, 232, 255);
/*  130 */   private Color color67 = new Color(231, 234, 237, 255);
/*  131 */   private Color color68 = new Color(205, 211, 215, 255);
/*  132 */   private Color color69 = new Color(149, 153, 156, 54);
/*  133 */   private Color color70 = new Color(255, 122, 101, 255);
/*  134 */   private Color color71 = new Color(54, 78, 122, 255);
/*  135 */   private Color color72 = new Color(51, 60, 70, 255);
/*  136 */   private Color color73 = new Color(228, 232, 237, 255);
/*  137 */   private Color color74 = new Color(27, 57, 87, 255);
/*  138 */   private Color color75 = new Color(75, 109, 137, 255);
/*  139 */   private Color color76 = new Color(77, 133, 185, 255);
/*  140 */   private Color color77 = new Color(81, 59, 7, 255);
/*  141 */   private Color color78 = new Color(97, 74, 18, 255);
/*  142 */   private Color color79 = new Color(137, 115, 60, 255);
/*  143 */   private Color color80 = new Color(174, 151, 91, 255);
/*  144 */   private Color color81 = new Color(114, 92, 13, 255);
/*  145 */   private Color color82 = new Color(64, 48, 0, 255);
/*  146 */   private Color color83 = new Color(244, 222, 143, 255);
/*  147 */   private Color color84 = new Color(160, 161, 162, 255);
/*  148 */   private Color color85 = new Color(226, 230, 233, 255);
/*  149 */   private Color color86 = new Color(221, 225, 230, 255);
/*  150 */   private Color color87 = decodeColor("nimbusBase", 0.004681647F, -0.48756614F, 0.19215685F, 0);
/*  151 */   private Color color88 = decodeColor("nimbusBase", 0.004681647F, -0.48399013F, 0.019607842F, 0);
/*  152 */   private Color color89 = decodeColor("nimbusBase", -0.0028941035F, -0.5906323F, 0.4078431F, 0);
/*  153 */   private Color color90 = decodeColor("nimbusBase", 0.004681647F, -0.51290727F, 0.34509802F, 0);
/*  154 */   private Color color91 = decodeColor("nimbusBase", 0.009583652F, -0.5642857F, 0.3843137F, 0);
/*  155 */   private Color color92 = decodeColor("nimbusBase", -0.0072231293F, -0.6074885F, 0.4235294F, 0);
/*  156 */   private Color color93 = decodeColor("nimbusBase", 7.13408E-4F, -0.52158386F, 0.17254901F, 0);
/*  157 */   private Color color94 = decodeColor("nimbusBase", 0.012257397F, -0.5775132F, 0.19215685F, 0);
/*  158 */   private Color color95 = decodeColor("nimbusBase", 0.08801502F, -0.6164835F, -0.14117649F, 0);
/*  159 */   private Color color96 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.5019608F, 0);
/*  160 */   private Color color97 = decodeColor("nimbusBase", -0.0036516786F, -0.555393F, 0.42745095F, 0);
/*  161 */   private Color color98 = decodeColor("nimbusBase", -0.0010654926F, -0.3634138F, 0.2862745F, 0);
/*  162 */   private Color color99 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.29803923F, 0);
/*  163 */   private Color color100 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, 0.12156862F, 0);
/*  164 */   private Color color101 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  165 */   private Color color102 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.48627454F, 0);
/*  166 */   private Color color103 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.007843137F, 0);
/*  167 */   private Color color104 = decodeColor("nimbusBase", -0.0028941035F, -0.5408867F, -0.09411767F, 0);
/*  168 */   private Color color105 = decodeColor("nimbusBase", -0.011985004F, -0.54721874F, -0.10588238F, 0);
/*  169 */   private Color color106 = decodeColor("nimbusBase", -0.0022627711F, -0.4305861F, -0.0901961F, 0);
/*  170 */   private Color color107 = decodeColor("nimbusBase", -0.00573498F, -0.447479F, -0.21568629F, 0);
/*  171 */   private Color color108 = decodeColor("nimbusBase", 0.004681647F, -0.53271F, 0.36470586F, 0);
/*  172 */   private Color color109 = decodeColor("nimbusBase", 0.004681647F, -0.5276062F, -0.11372551F, 0);
/*  173 */   private Color color110 = decodeColor("nimbusBase", -8.738637E-4F, -0.5278006F, -0.0039215684F, 0);
/*  174 */   private Color color111 = decodeColor("nimbusBase", -0.0028941035F, -0.5338625F, -0.12549022F, 0);
/*  175 */   private Color color112 = decodeColor("nimbusBlueGrey", -0.03535354F, -0.008674465F, -0.32156864F, 0);
/*  176 */   private Color color113 = decodeColor("nimbusBlueGrey", -0.027777791F, -0.010526314F, -0.3529412F, 0);
/*  177 */   private Color color114 = decodeColor("nimbusBase", -0.0028941035F, -0.5234694F, -0.1647059F, 0);
/*  178 */   private Color color115 = decodeColor("nimbusBase", 0.004681647F, -0.53401935F, -0.086274534F, 0);
/*  179 */   private Color color116 = decodeColor("nimbusBase", 0.004681647F, -0.52077174F, -0.20784315F, 0);
/*  180 */   private Color color117 = new Color(108, 114, 120, 255);
/*  181 */   private Color color118 = new Color(77, 82, 87, 255);
/*  182 */   private Color color119 = decodeColor("nimbusBase", -0.004577577F, -0.52179027F, -0.2392157F, 0);
/*  183 */   private Color color120 = decodeColor("nimbusBase", -0.004577577F, -0.547479F, -0.14901963F, 0);
/*  184 */   private Color color121 = new Color(186, 186, 186, 50);
/*  185 */   private Color color122 = new Color(186, 186, 186, 40);
/*      */ 
/*      */   
/*      */   private Object[] componentColors;
/*      */ 
/*      */ 
/*      */   
/*      */   public FileChooserPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  193 */     this.state = paramInt;
/*  194 */     this.ctx = paramPaintContext;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  200 */     this.componentColors = paramArrayOfObject;
/*      */ 
/*      */     
/*  203 */     switch (this.state) { case 1:
/*  204 */         paintBackgroundEnabled(paramGraphics2D); break;
/*  205 */       case 2: paintfileIconEnabled(paramGraphics2D); break;
/*  206 */       case 3: paintdirectoryIconEnabled(paramGraphics2D); break;
/*  207 */       case 4: paintupFolderIconEnabled(paramGraphics2D); break;
/*  208 */       case 5: paintnewFolderIconEnabled(paramGraphics2D); break;
/*  209 */       case 7: painthardDriveIconEnabled(paramGraphics2D); break;
/*  210 */       case 8: paintfloppyDriveIconEnabled(paramGraphics2D); break;
/*  211 */       case 9: painthomeFolderIconEnabled(paramGraphics2D); break;
/*  212 */       case 10: paintdetailsViewIconEnabled(paramGraphics2D); break;
/*  213 */       case 11: paintlistViewIconEnabled(paramGraphics2D);
/*      */         break; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  222 */     return this.ctx;
/*      */   }
/*      */   
/*      */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/*  226 */     this.rect = decodeRect1();
/*  227 */     paramGraphics2D.setPaint(this.color1);
/*  228 */     paramGraphics2D.fill(this.rect);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintfileIconEnabled(Graphics2D paramGraphics2D) {
/*  233 */     this.path = decodePath1();
/*  234 */     paramGraphics2D.setPaint(this.color2);
/*  235 */     paramGraphics2D.fill(this.path);
/*  236 */     this.rect = decodeRect2();
/*  237 */     paramGraphics2D.setPaint(this.color3);
/*  238 */     paramGraphics2D.fill(this.rect);
/*  239 */     this.path = decodePath2();
/*  240 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/*  241 */     paramGraphics2D.fill(this.path);
/*  242 */     this.path = decodePath3();
/*  243 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/*  244 */     paramGraphics2D.fill(this.path);
/*  245 */     this.path = decodePath4();
/*  246 */     paramGraphics2D.setPaint(this.color8);
/*  247 */     paramGraphics2D.fill(this.path);
/*  248 */     this.path = decodePath5();
/*  249 */     paramGraphics2D.setPaint(this.color9);
/*  250 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintdirectoryIconEnabled(Graphics2D paramGraphics2D) {
/*  255 */     this.path = decodePath6();
/*  256 */     paramGraphics2D.setPaint(this.color10);
/*  257 */     paramGraphics2D.fill(this.path);
/*  258 */     this.path = decodePath7();
/*  259 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/*  260 */     paramGraphics2D.fill(this.path);
/*  261 */     this.path = decodePath8();
/*  262 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/*  263 */     paramGraphics2D.fill(this.path);
/*  264 */     this.rect = decodeRect3();
/*  265 */     paramGraphics2D.setPaint(this.color16);
/*  266 */     paramGraphics2D.fill(this.rect);
/*  267 */     this.rect = decodeRect4();
/*  268 */     paramGraphics2D.setPaint(this.color17);
/*  269 */     paramGraphics2D.fill(this.rect);
/*  270 */     this.rect = decodeRect5();
/*  271 */     paramGraphics2D.setPaint(this.color18);
/*  272 */     paramGraphics2D.fill(this.rect);
/*  273 */     this.path = decodePath9();
/*  274 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/*  275 */     paramGraphics2D.fill(this.path);
/*  276 */     this.path = decodePath10();
/*  277 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/*  278 */     paramGraphics2D.fill(this.path);
/*  279 */     this.path = decodePath11();
/*  280 */     paramGraphics2D.setPaint(this.color24);
/*  281 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintupFolderIconEnabled(Graphics2D paramGraphics2D) {
/*  286 */     this.path = decodePath12();
/*  287 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/*  288 */     paramGraphics2D.fill(this.path);
/*  289 */     this.path = decodePath13();
/*  290 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/*  291 */     paramGraphics2D.fill(this.path);
/*  292 */     this.path = decodePath14();
/*  293 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/*  294 */     paramGraphics2D.fill(this.path);
/*  295 */     this.path = decodePath15();
/*  296 */     paramGraphics2D.setPaint(decodeGradient10(this.path));
/*  297 */     paramGraphics2D.fill(this.path);
/*  298 */     this.path = decodePath16();
/*  299 */     paramGraphics2D.setPaint(this.color32);
/*  300 */     paramGraphics2D.fill(this.path);
/*  301 */     this.path = decodePath17();
/*  302 */     paramGraphics2D.setPaint(decodeGradient11(this.path));
/*  303 */     paramGraphics2D.fill(this.path);
/*  304 */     this.path = decodePath18();
/*  305 */     paramGraphics2D.setPaint(this.color35);
/*  306 */     paramGraphics2D.fill(this.path);
/*  307 */     this.path = decodePath19();
/*  308 */     paramGraphics2D.setPaint(decodeGradient12(this.path));
/*  309 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintnewFolderIconEnabled(Graphics2D paramGraphics2D) {
/*  314 */     this.path = decodePath6();
/*  315 */     paramGraphics2D.setPaint(this.color10);
/*  316 */     paramGraphics2D.fill(this.path);
/*  317 */     this.path = decodePath7();
/*  318 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/*  319 */     paramGraphics2D.fill(this.path);
/*  320 */     this.path = decodePath8();
/*  321 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/*  322 */     paramGraphics2D.fill(this.path);
/*  323 */     this.rect = decodeRect3();
/*  324 */     paramGraphics2D.setPaint(this.color16);
/*  325 */     paramGraphics2D.fill(this.rect);
/*  326 */     this.rect = decodeRect4();
/*  327 */     paramGraphics2D.setPaint(this.color17);
/*  328 */     paramGraphics2D.fill(this.rect);
/*  329 */     this.rect = decodeRect5();
/*  330 */     paramGraphics2D.setPaint(this.color18);
/*  331 */     paramGraphics2D.fill(this.rect);
/*  332 */     this.path = decodePath9();
/*  333 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/*  334 */     paramGraphics2D.fill(this.path);
/*  335 */     this.path = decodePath10();
/*  336 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/*  337 */     paramGraphics2D.fill(this.path);
/*  338 */     this.path = decodePath11();
/*  339 */     paramGraphics2D.setPaint(this.color24);
/*  340 */     paramGraphics2D.fill(this.path);
/*  341 */     this.path = decodePath20();
/*  342 */     paramGraphics2D.setPaint(this.color38);
/*  343 */     paramGraphics2D.fill(this.path);
/*  344 */     this.path = decodePath21();
/*  345 */     paramGraphics2D.setPaint(this.color39);
/*  346 */     paramGraphics2D.fill(this.path);
/*  347 */     this.path = decodePath22();
/*  348 */     paramGraphics2D.setPaint(decodeRadial1(this.path));
/*  349 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void painthardDriveIconEnabled(Graphics2D paramGraphics2D) {
/*  354 */     this.rect = decodeRect6();
/*  355 */     paramGraphics2D.setPaint(this.color43);
/*  356 */     paramGraphics2D.fill(this.rect);
/*  357 */     this.rect = decodeRect7();
/*  358 */     paramGraphics2D.setPaint(this.color44);
/*  359 */     paramGraphics2D.fill(this.rect);
/*  360 */     this.rect = decodeRect8();
/*  361 */     paramGraphics2D.setPaint(decodeGradient13(this.rect));
/*  362 */     paramGraphics2D.fill(this.rect);
/*  363 */     this.path = decodePath23();
/*  364 */     paramGraphics2D.setPaint(decodeGradient14(this.path));
/*  365 */     paramGraphics2D.fill(this.path);
/*  366 */     this.rect = decodeRect9();
/*  367 */     paramGraphics2D.setPaint(this.color49);
/*  368 */     paramGraphics2D.fill(this.rect);
/*  369 */     this.rect = decodeRect10();
/*  370 */     paramGraphics2D.setPaint(this.color49);
/*  371 */     paramGraphics2D.fill(this.rect);
/*  372 */     this.ellipse = decodeEllipse1();
/*  373 */     paramGraphics2D.setPaint(this.color50);
/*  374 */     paramGraphics2D.fill(this.ellipse);
/*  375 */     this.path = decodePath24();
/*  376 */     paramGraphics2D.setPaint(decodeGradient15(this.path));
/*  377 */     paramGraphics2D.fill(this.path);
/*  378 */     this.ellipse = decodeEllipse2();
/*  379 */     paramGraphics2D.setPaint(this.color53);
/*  380 */     paramGraphics2D.fill(this.ellipse);
/*  381 */     this.ellipse = decodeEllipse3();
/*  382 */     paramGraphics2D.setPaint(this.color53);
/*  383 */     paramGraphics2D.fill(this.ellipse);
/*  384 */     this.ellipse = decodeEllipse4();
/*  385 */     paramGraphics2D.setPaint(this.color54);
/*  386 */     paramGraphics2D.fill(this.ellipse);
/*  387 */     this.ellipse = decodeEllipse5();
/*  388 */     paramGraphics2D.setPaint(this.color55);
/*  389 */     paramGraphics2D.fill(this.ellipse);
/*  390 */     this.ellipse = decodeEllipse6();
/*  391 */     paramGraphics2D.setPaint(this.color55);
/*  392 */     paramGraphics2D.fill(this.ellipse);
/*  393 */     this.ellipse = decodeEllipse7();
/*  394 */     paramGraphics2D.setPaint(this.color55);
/*  395 */     paramGraphics2D.fill(this.ellipse);
/*  396 */     this.rect = decodeRect11();
/*  397 */     paramGraphics2D.setPaint(this.color56);
/*  398 */     paramGraphics2D.fill(this.rect);
/*  399 */     this.rect = decodeRect12();
/*  400 */     paramGraphics2D.setPaint(this.color56);
/*  401 */     paramGraphics2D.fill(this.rect);
/*  402 */     this.rect = decodeRect13();
/*  403 */     paramGraphics2D.setPaint(this.color56);
/*  404 */     paramGraphics2D.fill(this.rect);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintfloppyDriveIconEnabled(Graphics2D paramGraphics2D) {
/*  409 */     this.path = decodePath25();
/*  410 */     paramGraphics2D.setPaint(decodeGradient16(this.path));
/*  411 */     paramGraphics2D.fill(this.path);
/*  412 */     this.path = decodePath26();
/*  413 */     paramGraphics2D.setPaint(decodeGradient17(this.path));
/*  414 */     paramGraphics2D.fill(this.path);
/*  415 */     this.path = decodePath27();
/*  416 */     paramGraphics2D.setPaint(decodeGradient18(this.path));
/*  417 */     paramGraphics2D.fill(this.path);
/*  418 */     this.path = decodePath28();
/*  419 */     paramGraphics2D.setPaint(decodeGradient19(this.path));
/*  420 */     paramGraphics2D.fill(this.path);
/*  421 */     this.path = decodePath29();
/*  422 */     paramGraphics2D.setPaint(this.color69);
/*  423 */     paramGraphics2D.fill(this.path);
/*  424 */     this.rect = decodeRect14();
/*  425 */     paramGraphics2D.setPaint(this.color70);
/*  426 */     paramGraphics2D.fill(this.rect);
/*  427 */     this.rect = decodeRect15();
/*  428 */     paramGraphics2D.setPaint(this.color40);
/*  429 */     paramGraphics2D.fill(this.rect);
/*  430 */     this.rect = decodeRect16();
/*  431 */     paramGraphics2D.setPaint(this.color67);
/*  432 */     paramGraphics2D.fill(this.rect);
/*  433 */     this.rect = decodeRect17();
/*  434 */     paramGraphics2D.setPaint(this.color71);
/*  435 */     paramGraphics2D.fill(this.rect);
/*  436 */     this.rect = decodeRect18();
/*  437 */     paramGraphics2D.setPaint(this.color44);
/*  438 */     paramGraphics2D.fill(this.rect);
/*      */   }
/*      */ 
/*      */   
/*      */   private void painthomeFolderIconEnabled(Graphics2D paramGraphics2D) {
/*  443 */     this.path = decodePath30();
/*  444 */     paramGraphics2D.setPaint(this.color72);
/*  445 */     paramGraphics2D.fill(this.path);
/*  446 */     this.path = decodePath31();
/*  447 */     paramGraphics2D.setPaint(this.color73);
/*  448 */     paramGraphics2D.fill(this.path);
/*  449 */     this.rect = decodeRect19();
/*  450 */     paramGraphics2D.setPaint(decodeGradient20(this.rect));
/*  451 */     paramGraphics2D.fill(this.rect);
/*  452 */     this.rect = decodeRect20();
/*  453 */     paramGraphics2D.setPaint(this.color76);
/*  454 */     paramGraphics2D.fill(this.rect);
/*  455 */     this.path = decodePath32();
/*  456 */     paramGraphics2D.setPaint(decodeGradient21(this.path));
/*  457 */     paramGraphics2D.fill(this.path);
/*  458 */     this.rect = decodeRect21();
/*  459 */     paramGraphics2D.setPaint(decodeGradient22(this.rect));
/*  460 */     paramGraphics2D.fill(this.rect);
/*  461 */     this.path = decodePath33();
/*  462 */     paramGraphics2D.setPaint(decodeGradient23(this.path));
/*  463 */     paramGraphics2D.fill(this.path);
/*  464 */     this.path = decodePath34();
/*  465 */     paramGraphics2D.setPaint(this.color83);
/*  466 */     paramGraphics2D.fill(this.path);
/*  467 */     this.path = decodePath35();
/*  468 */     paramGraphics2D.setPaint(decodeGradient24(this.path));
/*  469 */     paramGraphics2D.fill(this.path);
/*  470 */     this.path = decodePath36();
/*  471 */     paramGraphics2D.setPaint(decodeGradient25(this.path));
/*  472 */     paramGraphics2D.fill(this.path);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintdetailsViewIconEnabled(Graphics2D paramGraphics2D) {
/*  477 */     this.rect = decodeRect22();
/*  478 */     paramGraphics2D.setPaint(decodeGradient26(this.rect));
/*  479 */     paramGraphics2D.fill(this.rect);
/*  480 */     this.rect = decodeRect23();
/*  481 */     paramGraphics2D.setPaint(decodeGradient27(this.rect));
/*  482 */     paramGraphics2D.fill(this.rect);
/*  483 */     this.rect = decodeRect24();
/*  484 */     paramGraphics2D.setPaint(this.color93);
/*  485 */     paramGraphics2D.fill(this.rect);
/*  486 */     this.rect = decodeRect5();
/*  487 */     paramGraphics2D.setPaint(this.color93);
/*  488 */     paramGraphics2D.fill(this.rect);
/*  489 */     this.rect = decodeRect25();
/*  490 */     paramGraphics2D.setPaint(this.color93);
/*  491 */     paramGraphics2D.fill(this.rect);
/*  492 */     this.rect = decodeRect26();
/*  493 */     paramGraphics2D.setPaint(this.color94);
/*  494 */     paramGraphics2D.fill(this.rect);
/*  495 */     this.ellipse = decodeEllipse8();
/*  496 */     paramGraphics2D.setPaint(decodeGradient28(this.ellipse));
/*  497 */     paramGraphics2D.fill(this.ellipse);
/*  498 */     this.ellipse = decodeEllipse9();
/*  499 */     paramGraphics2D.setPaint(decodeRadial2(this.ellipse));
/*  500 */     paramGraphics2D.fill(this.ellipse);
/*  501 */     this.path = decodePath37();
/*  502 */     paramGraphics2D.setPaint(decodeGradient29(this.path));
/*  503 */     paramGraphics2D.fill(this.path);
/*  504 */     this.path = decodePath38();
/*  505 */     paramGraphics2D.setPaint(decodeGradient30(this.path));
/*  506 */     paramGraphics2D.fill(this.path);
/*  507 */     this.rect = decodeRect27();
/*  508 */     paramGraphics2D.setPaint(this.color104);
/*  509 */     paramGraphics2D.fill(this.rect);
/*  510 */     this.rect = decodeRect28();
/*  511 */     paramGraphics2D.setPaint(this.color105);
/*  512 */     paramGraphics2D.fill(this.rect);
/*  513 */     this.rect = decodeRect29();
/*  514 */     paramGraphics2D.setPaint(this.color106);
/*  515 */     paramGraphics2D.fill(this.rect);
/*  516 */     this.rect = decodeRect30();
/*  517 */     paramGraphics2D.setPaint(this.color107);
/*  518 */     paramGraphics2D.fill(this.rect);
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintlistViewIconEnabled(Graphics2D paramGraphics2D) {
/*  523 */     this.rect = decodeRect31();
/*  524 */     paramGraphics2D.setPaint(decodeGradient26(this.rect));
/*  525 */     paramGraphics2D.fill(this.rect);
/*  526 */     this.rect = decodeRect32();
/*  527 */     paramGraphics2D.setPaint(decodeGradient31(this.rect));
/*  528 */     paramGraphics2D.fill(this.rect);
/*  529 */     this.rect = decodeRect33();
/*  530 */     paramGraphics2D.setPaint(this.color109);
/*  531 */     paramGraphics2D.fill(this.rect);
/*  532 */     this.rect = decodeRect34();
/*  533 */     paramGraphics2D.setPaint(decodeGradient32(this.rect));
/*  534 */     paramGraphics2D.fill(this.rect);
/*  535 */     this.rect = decodeRect35();
/*  536 */     paramGraphics2D.setPaint(this.color111);
/*  537 */     paramGraphics2D.fill(this.rect);
/*  538 */     this.rect = decodeRect36();
/*  539 */     paramGraphics2D.setPaint(this.color112);
/*  540 */     paramGraphics2D.fill(this.rect);
/*  541 */     this.rect = decodeRect37();
/*  542 */     paramGraphics2D.setPaint(this.color113);
/*  543 */     paramGraphics2D.fill(this.rect);
/*  544 */     this.rect = decodeRect38();
/*  545 */     paramGraphics2D.setPaint(decodeGradient33(this.rect));
/*  546 */     paramGraphics2D.fill(this.rect);
/*  547 */     this.rect = decodeRect39();
/*  548 */     paramGraphics2D.setPaint(this.color116);
/*  549 */     paramGraphics2D.fill(this.rect);
/*  550 */     this.rect = decodeRect40();
/*  551 */     paramGraphics2D.setPaint(decodeGradient34(this.rect));
/*  552 */     paramGraphics2D.fill(this.rect);
/*  553 */     this.rect = decodeRect41();
/*  554 */     paramGraphics2D.setPaint(decodeGradient35(this.rect));
/*  555 */     paramGraphics2D.fill(this.rect);
/*  556 */     this.rect = decodeRect42();
/*  557 */     paramGraphics2D.setPaint(this.color119);
/*  558 */     paramGraphics2D.fill(this.rect);
/*  559 */     this.rect = decodeRect43();
/*  560 */     paramGraphics2D.setPaint(this.color121);
/*  561 */     paramGraphics2D.fill(this.rect);
/*  562 */     this.rect = decodeRect44();
/*  563 */     paramGraphics2D.setPaint(this.color121);
/*  564 */     paramGraphics2D.fill(this.rect);
/*  565 */     this.rect = decodeRect45();
/*  566 */     paramGraphics2D.setPaint(this.color121);
/*  567 */     paramGraphics2D.fill(this.rect);
/*  568 */     this.rect = decodeRect46();
/*  569 */     paramGraphics2D.setPaint(this.color122);
/*  570 */     paramGraphics2D.fill(this.rect);
/*  571 */     this.rect = decodeRect47();
/*  572 */     paramGraphics2D.setPaint(this.color121);
/*  573 */     paramGraphics2D.fill(this.rect);
/*  574 */     this.rect = decodeRect48();
/*  575 */     paramGraphics2D.setPaint(this.color122);
/*  576 */     paramGraphics2D.fill(this.rect);
/*  577 */     this.rect = decodeRect49();
/*  578 */     paramGraphics2D.setPaint(this.color122);
/*  579 */     paramGraphics2D.fill(this.rect);
/*  580 */     this.rect = decodeRect50();
/*  581 */     paramGraphics2D.setPaint(this.color121);
/*  582 */     paramGraphics2D.fill(this.rect);
/*  583 */     this.rect = decodeRect51();
/*  584 */     paramGraphics2D.setPaint(this.color122);
/*  585 */     paramGraphics2D.fill(this.rect);
/*  586 */     this.rect = decodeRect52();
/*  587 */     paramGraphics2D.setPaint(this.color122);
/*  588 */     paramGraphics2D.fill(this.rect);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle2D decodeRect1() {
/*  595 */     this.rect.setRect(decodeX(1.0F), 
/*  596 */         decodeY(1.0F), (
/*  597 */         decodeX(2.0F) - decodeX(1.0F)), (
/*  598 */         decodeY(2.0F) - decodeY(1.0F)));
/*  599 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath1() {
/*  603 */     this.path.reset();
/*  604 */     this.path.moveTo(decodeX(0.2F), decodeY(0.0F));
/*  605 */     this.path.lineTo(decodeX(0.2F), decodeY(3.0F));
/*  606 */     this.path.lineTo(decodeX(0.4F), decodeY(3.0F));
/*  607 */     this.path.lineTo(decodeX(0.4F), decodeY(0.2F));
/*  608 */     this.path.lineTo(decodeX(1.9197531F), decodeY(0.2F));
/*  609 */     this.path.lineTo(decodeX(2.6F), decodeY(0.9F));
/*  610 */     this.path.lineTo(decodeX(2.6F), decodeY(3.0F));
/*  611 */     this.path.lineTo(decodeX(2.8F), decodeY(3.0F));
/*  612 */     this.path.lineTo(decodeX(2.8F), decodeY(0.88888896F));
/*  613 */     this.path.lineTo(decodeX(1.9537036F), decodeY(0.0F));
/*  614 */     this.path.lineTo(decodeX(0.2F), decodeY(0.0F));
/*  615 */     this.path.closePath();
/*  616 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect2() {
/*  620 */     this.rect.setRect(decodeX(0.4F), 
/*  621 */         decodeY(2.8F), (
/*  622 */         decodeX(2.6F) - decodeX(0.4F)), (
/*  623 */         decodeY(3.0F) - decodeY(2.8F)));
/*  624 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath2() {
/*  628 */     this.path.reset();
/*  629 */     this.path.moveTo(decodeX(1.8333333F), decodeY(0.2F));
/*  630 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.0F));
/*  631 */     this.path.lineTo(decodeX(2.6F), decodeY(1.0F));
/*  632 */     this.path.lineTo(decodeX(1.8333333F), decodeY(0.2F));
/*  633 */     this.path.closePath();
/*  634 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath3() {
/*  638 */     this.path.reset();
/*  639 */     this.path.moveTo(decodeX(1.8333333F), decodeY(0.2F));
/*  640 */     this.path.lineTo(decodeX(0.4F), decodeY(0.2F));
/*  641 */     this.path.lineTo(decodeX(0.4F), decodeY(2.8F));
/*  642 */     this.path.lineTo(decodeX(2.6F), decodeY(2.8F));
/*  643 */     this.path.lineTo(decodeX(2.6F), decodeY(1.0F));
/*  644 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.0F));
/*  645 */     this.path.lineTo(decodeX(1.8333333F), decodeY(0.2F));
/*  646 */     this.path.closePath();
/*  647 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath4() {
/*  651 */     this.path.reset();
/*  652 */     this.path.moveTo(decodeX(1.8333333F), decodeY(0.2F));
/*  653 */     this.path.lineTo(decodeX(1.6234567F), decodeY(0.2F));
/*  654 */     this.path.lineTo(decodeX(1.6296296F), decodeY(1.2037038F));
/*  655 */     this.path.lineTo(decodeX(2.6F), decodeY(1.2006173F));
/*  656 */     this.path.lineTo(decodeX(2.6F), decodeY(1.0F));
/*  657 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.0F));
/*  658 */     this.path.lineTo(decodeX(1.8333333F), decodeY(0.2F));
/*  659 */     this.path.closePath();
/*  660 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath5() {
/*  664 */     this.path.reset();
/*  665 */     this.path.moveTo(decodeX(1.8333333F), decodeY(0.4F));
/*  666 */     this.path.lineTo(decodeX(1.8333333F), decodeY(0.2F));
/*  667 */     this.path.lineTo(decodeX(0.4F), decodeY(0.2F));
/*  668 */     this.path.lineTo(decodeX(0.4F), decodeY(2.8F));
/*  669 */     this.path.lineTo(decodeX(2.6F), decodeY(2.8F));
/*  670 */     this.path.lineTo(decodeX(2.6F), decodeY(1.0F));
/*  671 */     this.path.lineTo(decodeX(2.4F), decodeY(1.0F));
/*  672 */     this.path.lineTo(decodeX(2.4F), decodeY(2.6F));
/*  673 */     this.path.lineTo(decodeX(0.6F), decodeY(2.6F));
/*  674 */     this.path.lineTo(decodeX(0.6F), decodeY(0.4F));
/*  675 */     this.path.lineTo(decodeX(1.8333333F), decodeY(0.4F));
/*  676 */     this.path.closePath();
/*  677 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath6() {
/*  681 */     this.path.reset();
/*  682 */     this.path.moveTo(decodeX(0.0F), decodeY(2.4F));
/*  683 */     this.path.lineTo(decodeX(0.0F), decodeY(2.6F));
/*  684 */     this.path.lineTo(decodeX(0.2F), decodeY(3.0F));
/*  685 */     this.path.lineTo(decodeX(2.6F), decodeY(3.0F));
/*  686 */     this.path.lineTo(decodeX(2.8F), decodeY(2.6F));
/*  687 */     this.path.lineTo(decodeX(2.8F), decodeY(2.4F));
/*  688 */     this.path.lineTo(decodeX(0.0F), decodeY(2.4F));
/*  689 */     this.path.closePath();
/*  690 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath7() {
/*  694 */     this.path.reset();
/*  695 */     this.path.moveTo(decodeX(0.6F), decodeY(2.6F));
/*  696 */     this.path.lineTo(decodeX(0.6037037F), decodeY(1.8425925F));
/*  697 */     this.path.lineTo(decodeX(0.8F), decodeY(1.0F));
/*  698 */     this.path.lineTo(decodeX(2.8F), decodeY(1.0F));
/*  699 */     this.path.lineTo(decodeX(2.8F), decodeY(1.3333334F));
/*  700 */     this.path.lineTo(decodeX(2.6F), decodeY(2.6F));
/*  701 */     this.path.lineTo(decodeX(0.6F), decodeY(2.6F));
/*  702 */     this.path.closePath();
/*  703 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath8() {
/*  707 */     this.path.reset();
/*  708 */     this.path.moveTo(decodeX(0.2F), decodeY(2.6F));
/*  709 */     this.path.lineTo(decodeX(0.4F), decodeY(2.6F));
/*  710 */     this.path.lineTo(decodeX(0.40833336F), decodeY(1.8645833F));
/*  711 */     this.path.lineTo(decodeX(0.79583335F), decodeY(0.8F));
/*  712 */     this.path.lineTo(decodeX(2.4F), decodeY(0.8F));
/*  713 */     this.path.lineTo(decodeX(2.4F), decodeY(0.6F));
/*  714 */     this.path.lineTo(decodeX(1.5F), decodeY(0.6F));
/*  715 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.4F));
/*  716 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.2F));
/*  717 */     this.path.lineTo(decodeX(0.6F), decodeY(0.2F));
/*  718 */     this.path.lineTo(decodeX(0.6F), decodeY(0.4F));
/*  719 */     this.path.lineTo(decodeX(0.4F), decodeY(0.6F));
/*  720 */     this.path.lineTo(decodeX(0.2F), decodeY(0.6F));
/*  721 */     this.path.lineTo(decodeX(0.2F), decodeY(2.6F));
/*  722 */     this.path.closePath();
/*  723 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect3() {
/*  727 */     this.rect.setRect(decodeX(0.2F), 
/*  728 */         decodeY(0.6F), (
/*  729 */         decodeX(0.4F) - decodeX(0.2F)), (
/*  730 */         decodeY(0.8F) - decodeY(0.6F)));
/*  731 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect4() {
/*  735 */     this.rect.setRect(decodeX(0.6F), 
/*  736 */         decodeY(0.2F), (
/*  737 */         decodeX(1.3333334F) - decodeX(0.6F)), (
/*  738 */         decodeY(0.4F) - decodeY(0.2F)));
/*  739 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect5() {
/*  743 */     this.rect.setRect(decodeX(1.5F), 
/*  744 */         decodeY(0.6F), (
/*  745 */         decodeX(2.4F) - decodeX(1.5F)), (
/*  746 */         decodeY(0.8F) - decodeY(0.6F)));
/*  747 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath9() {
/*  751 */     this.path.reset();
/*  752 */     this.path.moveTo(decodeX(3.0F), decodeY(0.8F));
/*  753 */     this.path.lineTo(decodeX(3.0F), decodeY(1.0F));
/*  754 */     this.path.lineTo(decodeX(2.4F), decodeY(1.0F));
/*  755 */     this.path.lineTo(decodeX(2.4F), decodeY(0.6F));
/*  756 */     this.path.lineTo(decodeX(1.5F), decodeY(0.6F));
/*  757 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.4F));
/*  758 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.2F));
/*  759 */     this.path.lineTo(decodeX(0.5888889F), decodeY(0.20370372F));
/*  760 */     this.path.lineTo(decodeX(0.5962963F), decodeY(0.34814817F));
/*  761 */     this.path.lineTo(decodeX(0.34814817F), decodeY(0.6F));
/*  762 */     this.path.lineTo(decodeX(0.2F), decodeY(0.6F));
/*  763 */     this.path.lineTo(decodeX(0.2F), decodeY(2.6F));
/*  764 */     this.path.lineTo(decodeX(2.6F), decodeY(2.6F));
/*  765 */     this.path.lineTo(decodeX(2.6F), decodeY(1.3333334F));
/*  766 */     this.path.lineTo(decodeX(2.774074F), decodeY(1.1604939F));
/*  767 */     this.path.lineTo(decodeX(2.8F), decodeY(1.0F));
/*  768 */     this.path.lineTo(decodeX(3.0F), decodeY(1.0F));
/*  769 */     this.path.lineTo(decodeX(2.8925927F), decodeY(1.1882716F));
/*  770 */     this.path.lineTo(decodeX(2.8F), decodeY(1.3333334F));
/*  771 */     this.path.lineTo(decodeX(2.8F), decodeY(2.6F));
/*  772 */     this.path.lineTo(decodeX(2.6F), decodeY(2.8F));
/*  773 */     this.path.lineTo(decodeX(0.2F), decodeY(2.8F));
/*  774 */     this.path.lineTo(decodeX(0.0F), decodeY(2.6F));
/*  775 */     this.path.lineTo(decodeX(0.0F), decodeY(0.65185183F));
/*  776 */     this.path.lineTo(decodeX(0.63703704F), decodeY(0.0F));
/*  777 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.0F));
/*  778 */     this.path.lineTo(decodeX(1.5925925F), decodeY(0.4F));
/*  779 */     this.path.lineTo(decodeX(2.4F), decodeY(0.4F));
/*  780 */     this.path.lineTo(decodeX(2.6F), decodeY(0.6F));
/*  781 */     this.path.lineTo(decodeX(2.6F), decodeY(0.8F));
/*  782 */     this.path.lineTo(decodeX(3.0F), decodeY(0.8F));
/*  783 */     this.path.closePath();
/*  784 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath10() {
/*  788 */     this.path.reset();
/*  789 */     this.path.moveTo(decodeX(2.4F), decodeY(1.0F));
/*  790 */     this.path.lineTo(decodeX(2.4F), decodeY(0.8F));
/*  791 */     this.path.lineTo(decodeX(0.74814814F), decodeY(0.8F));
/*  792 */     this.path.lineTo(decodeX(0.4037037F), decodeY(1.8425925F));
/*  793 */     this.path.lineTo(decodeX(0.4F), decodeY(2.6F));
/*  794 */     this.path.lineTo(decodeX(0.6F), decodeY(2.6F));
/*  795 */     this.path.lineTo(decodeX(0.5925926F), decodeY(2.225926F));
/*  796 */     this.path.lineTo(decodeX(0.916F), decodeY(0.996F));
/*  797 */     this.path.lineTo(decodeX(2.4F), decodeY(1.0F));
/*  798 */     this.path.closePath();
/*  799 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath11() {
/*  803 */     this.path.reset();
/*  804 */     this.path.moveTo(decodeX(2.2F), decodeY(2.2F));
/*  805 */     this.path.lineTo(decodeX(2.2F), decodeY(2.2F));
/*  806 */     this.path.closePath();
/*  807 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath12() {
/*  811 */     this.path.reset();
/*  812 */     this.path.moveTo(decodeX(0.0F), decodeY(2.8F));
/*  813 */     this.path.lineTo(decodeX(0.2F), decodeY(3.0F));
/*  814 */     this.path.lineTo(decodeX(2.6F), decodeY(3.0F));
/*  815 */     this.path.lineTo(decodeX(2.8F), decodeY(2.8F));
/*  816 */     this.path.lineTo(decodeX(2.8F), decodeY(1.8333333F));
/*  817 */     this.path.lineTo(decodeX(3.0F), decodeY(1.3333334F));
/*  818 */     this.path.lineTo(decodeX(3.0F), decodeY(1.0F));
/*  819 */     this.path.lineTo(decodeX(1.5F), decodeY(1.0F));
/*  820 */     this.path.lineTo(decodeX(1.5F), decodeY(0.4F));
/*  821 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.2F));
/*  822 */     this.path.lineTo(decodeX(0.6F), decodeY(0.2F));
/*  823 */     this.path.lineTo(decodeX(0.4F), decodeY(0.4F));
/*  824 */     this.path.lineTo(decodeX(0.4F), decodeY(0.6F));
/*  825 */     this.path.lineTo(decodeX(0.2F), decodeY(0.6F));
/*  826 */     this.path.lineTo(decodeX(0.0F), decodeY(0.8F));
/*  827 */     this.path.lineTo(decodeX(0.0F), decodeY(2.8F));
/*  828 */     this.path.closePath();
/*  829 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath13() {
/*  833 */     this.path.reset();
/*  834 */     this.path.moveTo(decodeX(0.2F), decodeY(2.8F));
/*  835 */     this.path.lineTo(decodeX(0.2F), decodeY(0.8F));
/*  836 */     this.path.lineTo(decodeX(0.4F), decodeY(0.8F));
/*  837 */     this.path.lineTo(decodeX(0.6F), decodeY(0.6F));
/*  838 */     this.path.lineTo(decodeX(0.6F), decodeY(0.4F));
/*  839 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.4F));
/*  840 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.6F));
/*  841 */     this.path.lineTo(decodeX(1.5F), decodeY(0.6F));
/*  842 */     this.path.lineTo(decodeX(1.5F), decodeY(2.8F));
/*  843 */     this.path.lineTo(decodeX(0.2F), decodeY(2.8F));
/*  844 */     this.path.closePath();
/*  845 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath14() {
/*  849 */     this.path.reset();
/*  850 */     this.path.moveTo(decodeX(0.4F), decodeY(2.0F));
/*  851 */     this.path.lineTo(decodeX(0.6F), decodeY(1.1666666F));
/*  852 */     this.path.lineTo(decodeX(0.8F), decodeY(1.0F));
/*  853 */     this.path.lineTo(decodeX(2.8F), decodeY(1.0F));
/*  854 */     this.path.lineTo(decodeX(2.8F), decodeY(2.8F));
/*  855 */     this.path.lineTo(decodeX(2.4F), decodeY(3.0F));
/*  856 */     this.path.lineTo(decodeX(0.4F), decodeY(3.0F));
/*  857 */     this.path.lineTo(decodeX(0.4F), decodeY(2.0F));
/*  858 */     this.path.closePath();
/*  859 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath15() {
/*  863 */     this.path.reset();
/*  864 */     this.path.moveTo(decodeX(0.6F), decodeY(2.8F));
/*  865 */     this.path.lineTo(decodeX(0.6F), decodeY(2.0F));
/*  866 */     this.path.lineTo(decodeX(0.8F), decodeY(1.1666666F));
/*  867 */     this.path.lineTo(decodeX(2.8F), decodeY(1.1666666F));
/*  868 */     this.path.lineTo(decodeX(2.6F), decodeY(2.0F));
/*  869 */     this.path.lineTo(decodeX(2.6F), decodeY(2.8F));
/*  870 */     this.path.lineTo(decodeX(0.6F), decodeY(2.8F));
/*  871 */     this.path.closePath();
/*  872 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath16() {
/*  876 */     this.path.reset();
/*  877 */     this.path.moveTo(decodeX(1.1702899F), decodeY(1.2536231F));
/*  878 */     this.path.lineTo(decodeX(1.1666666F), decodeY(1.0615941F));
/*  879 */     this.path.lineTo(decodeX(3.0F), decodeY(1.0978261F));
/*  880 */     this.path.lineTo(decodeX(2.7782607F), decodeY(1.25F));
/*  881 */     this.path.lineTo(decodeX(2.3913045F), decodeY(1.3188406F));
/*  882 */     this.path.lineTo(decodeX(2.3826087F), decodeY(1.7246377F));
/*  883 */     this.path.lineTo(decodeX(2.173913F), decodeY(1.9347827F));
/*  884 */     this.path.lineTo(decodeX(1.8695652F), decodeY(1.923913F));
/*  885 */     this.path.lineTo(decodeX(1.710145F), decodeY(1.7246377F));
/*  886 */     this.path.lineTo(decodeX(1.710145F), decodeY(1.3115941F));
/*  887 */     this.path.lineTo(decodeX(1.1702899F), decodeY(1.2536231F));
/*  888 */     this.path.closePath();
/*  889 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath17() {
/*  893 */     this.path.reset();
/*  894 */     this.path.moveTo(decodeX(1.1666666F), decodeY(1.1666666F));
/*  895 */     this.path.lineTo(decodeX(1.1666666F), decodeY(0.9130435F));
/*  896 */     this.path.lineTo(decodeX(1.9456522F), decodeY(0.0F));
/*  897 */     this.path.lineTo(decodeX(2.0608697F), decodeY(0.0F));
/*  898 */     this.path.lineTo(decodeX(2.9956522F), decodeY(0.9130435F));
/*  899 */     this.path.lineTo(decodeX(3.0F), decodeY(1.1666666F));
/*  900 */     this.path.lineTo(decodeX(2.4F), decodeY(1.1666666F));
/*  901 */     this.path.lineTo(decodeX(2.4F), decodeY(1.6666667F));
/*  902 */     this.path.lineTo(decodeX(2.2F), decodeY(1.8333333F));
/*  903 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.8333333F));
/*  904 */     this.path.lineTo(decodeX(1.6666667F), decodeY(1.6666667F));
/*  905 */     this.path.lineTo(decodeX(1.6666667F), decodeY(1.1666666F));
/*  906 */     this.path.lineTo(decodeX(1.1666666F), decodeY(1.1666666F));
/*  907 */     this.path.closePath();
/*  908 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath18() {
/*  912 */     this.path.reset();
/*  913 */     this.path.moveTo(decodeX(1.2717391F), decodeY(0.9956522F));
/*  914 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.0F));
/*  915 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.6666667F));
/*  916 */     this.path.lineTo(decodeX(2.2F), decodeY(1.6666667F));
/*  917 */     this.path.lineTo(decodeX(2.2F), decodeY(1.0F));
/*  918 */     this.path.lineTo(decodeX(2.8652174F), decodeY(1.0F));
/*  919 */     this.path.lineTo(decodeX(2.0F), decodeY(0.13043478F));
/*  920 */     this.path.lineTo(decodeX(1.2717391F), decodeY(0.9956522F));
/*  921 */     this.path.closePath();
/*  922 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath19() {
/*  926 */     this.path.reset();
/*  927 */     this.path.moveTo(decodeX(1.8333333F), decodeY(1.6666667F));
/*  928 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.0F));
/*  929 */     this.path.lineTo(decodeX(1.3913044F), decodeY(1.0F));
/*  930 */     this.path.lineTo(decodeX(1.9963768F), decodeY(0.25652176F));
/*  931 */     this.path.lineTo(decodeX(2.6608696F), decodeY(1.0F));
/*  932 */     this.path.lineTo(decodeX(2.2F), decodeY(1.0F));
/*  933 */     this.path.lineTo(decodeX(2.2F), decodeY(1.6666667F));
/*  934 */     this.path.lineTo(decodeX(1.8333333F), decodeY(1.6666667F));
/*  935 */     this.path.closePath();
/*  936 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath20() {
/*  940 */     this.path.reset();
/*  941 */     this.path.moveTo(decodeX(0.22692308F), decodeY(0.061538465F));
/*  942 */     this.path.lineTo(decodeX(0.75384617F), decodeY(0.37692308F));
/*  943 */     this.path.lineTo(decodeX(0.91923076F), decodeY(0.01923077F));
/*  944 */     this.path.lineTo(decodeX(1.2532052F), decodeY(0.40769228F));
/*  945 */     this.path.lineTo(decodeX(1.7115386F), decodeY(0.13846155F));
/*  946 */     this.path.lineTo(decodeX(1.6923077F), decodeY(0.85F));
/*  947 */     this.path.lineTo(decodeX(2.169231F), decodeY(0.9115385F));
/*  948 */     this.path.lineTo(decodeX(1.7852564F), decodeY(1.3333334F));
/*  949 */     this.path.lineTo(decodeX(1.9166667F), decodeY(1.9679487F));
/*  950 */     this.path.lineTo(decodeX(1.3685898F), decodeY(1.8301282F));
/*  951 */     this.path.lineTo(decodeX(1.1314102F), decodeY(2.2115386F));
/*  952 */     this.path.lineTo(decodeX(0.63076925F), decodeY(1.8205128F));
/*  953 */     this.path.lineTo(decodeX(0.22692308F), decodeY(1.9262822F));
/*  954 */     this.path.lineTo(decodeX(0.31153846F), decodeY(1.4871795F));
/*  955 */     this.path.lineTo(decodeX(0.0F), decodeY(1.1538461F));
/*  956 */     this.path.lineTo(decodeX(0.38461536F), decodeY(0.68076926F));
/*  957 */     this.path.lineTo(decodeX(0.22692308F), decodeY(0.061538465F));
/*  958 */     this.path.closePath();
/*  959 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath21() {
/*  963 */     this.path.reset();
/*  964 */     this.path.moveTo(decodeX(0.23461537F), decodeY(0.33076924F));
/*  965 */     this.path.lineTo(decodeX(0.32692307F), decodeY(0.21538463F));
/*  966 */     this.path.lineTo(decodeX(0.9653846F), decodeY(0.74615383F));
/*  967 */     this.path.lineTo(decodeX(1.0160257F), decodeY(0.01923077F));
/*  968 */     this.path.lineTo(decodeX(1.1506411F), decodeY(0.01923077F));
/*  969 */     this.path.lineTo(decodeX(1.2275641F), decodeY(0.72307694F));
/*  970 */     this.path.lineTo(decodeX(1.6987178F), decodeY(0.20769231F));
/*  971 */     this.path.lineTo(decodeX(1.8237178F), decodeY(0.37692308F));
/*  972 */     this.path.lineTo(decodeX(1.3878205F), decodeY(0.94230765F));
/*  973 */     this.path.lineTo(decodeX(1.9775641F), decodeY(1.0256411F));
/*  974 */     this.path.lineTo(decodeX(1.9839742F), decodeY(1.1474359F));
/*  975 */     this.path.lineTo(decodeX(1.4070512F), decodeY(1.2083334F));
/*  976 */     this.path.lineTo(decodeX(1.7980769F), decodeY(1.7307692F));
/*  977 */     this.path.lineTo(decodeX(1.7532051F), decodeY(1.8269231F));
/*  978 */     this.path.lineTo(decodeX(1.2211539F), decodeY(1.3365384F));
/*  979 */     this.path.lineTo(decodeX(1.1506411F), decodeY(1.9839742F));
/*  980 */     this.path.lineTo(decodeX(1.0288461F), decodeY(1.9775641F));
/*  981 */     this.path.lineTo(decodeX(0.95384616F), decodeY(1.3429488F));
/*  982 */     this.path.lineTo(decodeX(0.28846154F), decodeY(1.8012822F));
/*  983 */     this.path.lineTo(decodeX(0.20769231F), decodeY(1.7371795F));
/*  984 */     this.path.lineTo(decodeX(0.75F), decodeY(1.173077F));
/*  985 */     this.path.lineTo(decodeX(0.011538462F), decodeY(1.1634616F));
/*  986 */     this.path.lineTo(decodeX(0.015384616F), decodeY(1.0224359F));
/*  987 */     this.path.lineTo(decodeX(0.79615384F), decodeY(0.94230765F));
/*  988 */     this.path.lineTo(decodeX(0.23461537F), decodeY(0.33076924F));
/*  989 */     this.path.closePath();
/*  990 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath22() {
/*  994 */     this.path.reset();
/*  995 */     this.path.moveTo(decodeX(0.58461535F), decodeY(0.6615385F));
/*  996 */     this.path.lineTo(decodeX(0.68846154F), decodeY(0.56923074F));
/*  997 */     this.path.lineTo(decodeX(0.9884615F), decodeY(0.80769235F));
/*  998 */     this.path.lineTo(decodeX(1.0352564F), decodeY(0.43076926F));
/*  999 */     this.path.lineTo(decodeX(1.1282052F), decodeY(0.43846154F));
/* 1000 */     this.path.lineTo(decodeX(1.1891025F), decodeY(0.80769235F));
/* 1001 */     this.path.lineTo(decodeX(1.4006411F), decodeY(0.59615386F));
/* 1002 */     this.path.lineTo(decodeX(1.4967948F), decodeY(0.70384616F));
/* 1003 */     this.path.lineTo(decodeX(1.3173077F), decodeY(0.9384615F));
/* 1004 */     this.path.lineTo(decodeX(1.625F), decodeY(1.0256411F));
/* 1005 */     this.path.lineTo(decodeX(1.6282051F), decodeY(1.1346154F));
/* 1006 */     this.path.lineTo(decodeX(1.2564102F), decodeY(1.176282F));
/* 1007 */     this.path.lineTo(decodeX(1.4711539F), decodeY(1.3910257F));
/* 1008 */     this.path.lineTo(decodeX(1.4070512F), decodeY(1.4807693F));
/* 1009 */     this.path.lineTo(decodeX(1.1858975F), decodeY(1.2724359F));
/* 1010 */     this.path.lineTo(decodeX(1.1474359F), decodeY(1.6602564F));
/* 1011 */     this.path.lineTo(decodeX(1.0416666F), decodeY(1.6602564F));
/* 1012 */     this.path.lineTo(decodeX(0.9769231F), decodeY(1.2884616F));
/* 1013 */     this.path.lineTo(decodeX(0.6923077F), decodeY(1.5F));
/* 1014 */     this.path.lineTo(decodeX(0.6423077F), decodeY(1.3782052F));
/* 1015 */     this.path.lineTo(decodeX(0.83076924F), decodeY(1.176282F));
/* 1016 */     this.path.lineTo(decodeX(0.46923074F), decodeY(1.1474359F));
/* 1017 */     this.path.lineTo(decodeX(0.48076925F), decodeY(1.0064102F));
/* 1018 */     this.path.lineTo(decodeX(0.8230769F), decodeY(0.98461545F));
/* 1019 */     this.path.lineTo(decodeX(0.58461535F), decodeY(0.6615385F));
/* 1020 */     this.path.closePath();
/* 1021 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect6() {
/* 1025 */     this.rect.setRect(decodeX(0.2F), 
/* 1026 */         decodeY(0.0F), (
/* 1027 */         decodeX(2.8F) - decodeX(0.2F)), (
/* 1028 */         decodeY(2.2F) - decodeY(0.0F)));
/* 1029 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect7() {
/* 1033 */     this.rect.setRect(decodeX(0.2F), 
/* 1034 */         decodeY(2.2F), (
/* 1035 */         decodeX(2.8F) - decodeX(0.2F)), (
/* 1036 */         decodeY(3.0F) - decodeY(2.2F)));
/* 1037 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect8() {
/* 1041 */     this.rect.setRect(decodeX(0.4F), 
/* 1042 */         decodeY(0.2F), (
/* 1043 */         decodeX(2.6F) - decodeX(0.4F)), (
/* 1044 */         decodeY(2.2F) - decodeY(0.2F)));
/* 1045 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath23() {
/* 1049 */     this.path.reset();
/* 1050 */     this.path.moveTo(decodeX(0.4F), decodeY(2.2F));
/* 1051 */     this.path.lineTo(decodeX(0.4F), decodeY(2.8F));
/* 1052 */     this.path.lineTo(decodeX(0.6F), decodeY(2.8F));
/* 1053 */     this.path.lineTo(decodeX(0.6F), decodeY(2.6F));
/* 1054 */     this.path.lineTo(decodeX(2.4F), decodeY(2.6F));
/* 1055 */     this.path.lineTo(decodeX(2.4F), decodeY(2.8F));
/* 1056 */     this.path.lineTo(decodeX(2.6F), decodeY(2.8F));
/* 1057 */     this.path.lineTo(decodeX(2.6F), decodeY(2.2F));
/* 1058 */     this.path.lineTo(decodeX(0.4F), decodeY(2.2F));
/* 1059 */     this.path.closePath();
/* 1060 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect9() {
/* 1064 */     this.rect.setRect(decodeX(0.6F), 
/* 1065 */         decodeY(2.8F), (
/* 1066 */         decodeX(1.6666667F) - decodeX(0.6F)), (
/* 1067 */         decodeY(3.0F) - decodeY(2.8F)));
/* 1068 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect10() {
/* 1072 */     this.rect.setRect(decodeX(1.8333333F), 
/* 1073 */         decodeY(2.8F), (
/* 1074 */         decodeX(2.4F) - decodeX(1.8333333F)), (
/* 1075 */         decodeY(3.0F) - decodeY(2.8F)));
/* 1076 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse1() {
/* 1080 */     this.ellipse.setFrame(decodeX(0.6F), 
/* 1081 */         decodeY(2.4F), (
/* 1082 */         decodeX(0.8F) - decodeX(0.6F)), (
/* 1083 */         decodeY(2.6F) - decodeY(2.4F)));
/* 1084 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Path2D decodePath24() {
/* 1088 */     this.path.reset();
/* 1089 */     this.path.moveTo(decodeX(1.0F), decodeY(0.4F));
/* 1090 */     this.path.curveTo(decodeAnchorX(1.0F, 1.0F), decodeAnchorY(0.4F, -1.0F), decodeAnchorX(2.0F, -1.0F), decodeAnchorY(0.4F, -1.0F), decodeX(2.0F), decodeY(0.4F));
/* 1091 */     this.path.curveTo(decodeAnchorX(2.0F, 1.0F), decodeAnchorY(0.4F, 1.0F), decodeAnchorX(2.2F, 0.0F), decodeAnchorY(1.0F, -1.0F), decodeX(2.2F), decodeY(1.0F));
/* 1092 */     this.path.curveTo(decodeAnchorX(2.2F, 0.0F), decodeAnchorY(1.0F, 1.0F), decodeAnchorX(2.2F, 0.0F), decodeAnchorY(1.5F, -2.0F), decodeX(2.2F), decodeY(1.5F));
/* 1093 */     this.path.curveTo(decodeAnchorX(2.2F, 0.0F), decodeAnchorY(1.5F, 2.0F), decodeAnchorX(1.6666667F, 1.0F), decodeAnchorY(1.8333333F, 0.0F), decodeX(1.6666667F), decodeY(1.8333333F));
/* 1094 */     this.path.curveTo(decodeAnchorX(1.6666667F, -1.0F), decodeAnchorY(1.8333333F, 0.0F), decodeAnchorX(1.3333334F, 1.0F), decodeAnchorY(1.8333333F, 0.0F), decodeX(1.3333334F), decodeY(1.8333333F));
/* 1095 */     this.path.curveTo(decodeAnchorX(1.3333334F, -1.0F), decodeAnchorY(1.8333333F, 0.0F), decodeAnchorX(0.8F, 0.0F), decodeAnchorY(1.5F, 2.0F), decodeX(0.8F), decodeY(1.5F));
/* 1096 */     this.path.curveTo(decodeAnchorX(0.8F, 0.0F), decodeAnchorY(1.5F, -2.0F), decodeAnchorX(0.8F, 0.0F), decodeAnchorY(1.0F, 1.0F), decodeX(0.8F), decodeY(1.0F));
/* 1097 */     this.path.curveTo(decodeAnchorX(0.8F, 0.0F), decodeAnchorY(1.0F, -1.0F), decodeAnchorX(1.0F, -1.0F), decodeAnchorY(0.4F, 1.0F), decodeX(1.0F), decodeY(0.4F));
/* 1098 */     this.path.closePath();
/* 1099 */     return this.path;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse2() {
/* 1103 */     this.ellipse.setFrame(decodeX(0.6F), 
/* 1104 */         decodeY(0.2F), (
/* 1105 */         decodeX(0.8F) - decodeX(0.6F)), (
/* 1106 */         decodeY(0.4F) - decodeY(0.2F)));
/* 1107 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse3() {
/* 1111 */     this.ellipse.setFrame(decodeX(2.2F), 
/* 1112 */         decodeY(0.2F), (
/* 1113 */         decodeX(2.4F) - decodeX(2.2F)), (
/* 1114 */         decodeY(0.4F) - decodeY(0.2F)));
/* 1115 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse4() {
/* 1119 */     this.ellipse.setFrame(decodeX(2.2F), 
/* 1120 */         decodeY(1.0F), (
/* 1121 */         decodeX(2.4F) - decodeX(2.2F)), (
/* 1122 */         decodeY(1.1666666F) - decodeY(1.0F)));
/* 1123 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse5() {
/* 1127 */     this.ellipse.setFrame(decodeX(2.2F), 
/* 1128 */         decodeY(1.6666667F), (
/* 1129 */         decodeX(2.4F) - decodeX(2.2F)), (
/* 1130 */         decodeY(1.8333333F) - decodeY(1.6666667F)));
/* 1131 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse6() {
/* 1135 */     this.ellipse.setFrame(decodeX(0.6F), 
/* 1136 */         decodeY(1.6666667F), (
/* 1137 */         decodeX(0.8F) - decodeX(0.6F)), (
/* 1138 */         decodeY(1.8333333F) - decodeY(1.6666667F)));
/* 1139 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse7() {
/* 1143 */     this.ellipse.setFrame(decodeX(0.6F), 
/* 1144 */         decodeY(1.0F), (
/* 1145 */         decodeX(0.8F) - decodeX(0.6F)), (
/* 1146 */         decodeY(1.1666666F) - decodeY(1.0F)));
/* 1147 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect11() {
/* 1151 */     this.rect.setRect(decodeX(0.8F), 
/* 1152 */         decodeY(2.2F), (
/* 1153 */         decodeX(1.0F) - decodeX(0.8F)), (
/* 1154 */         decodeY(2.6F) - decodeY(2.2F)));
/* 1155 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect12() {
/* 1159 */     this.rect.setRect(decodeX(1.1666666F), 
/* 1160 */         decodeY(2.2F), (
/* 1161 */         decodeX(1.3333334F) - decodeX(1.1666666F)), (
/* 1162 */         decodeY(2.6F) - decodeY(2.2F)));
/* 1163 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect13() {
/* 1167 */     this.rect.setRect(decodeX(1.5F), 
/* 1168 */         decodeY(2.2F), (
/* 1169 */         decodeX(1.6666667F) - decodeX(1.5F)), (
/* 1170 */         decodeY(2.6F) - decodeY(2.2F)));
/* 1171 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath25() {
/* 1175 */     this.path.reset();
/* 1176 */     this.path.moveTo(decodeX(0.0F), decodeY(0.2F));
/* 1177 */     this.path.lineTo(decodeX(0.2F), decodeY(0.0F));
/* 1178 */     this.path.lineTo(decodeX(2.6F), decodeY(0.0F));
/* 1179 */     this.path.lineTo(decodeX(3.0F), decodeY(0.4F));
/* 1180 */     this.path.lineTo(decodeX(3.0F), decodeY(2.8F));
/* 1181 */     this.path.lineTo(decodeX(2.8F), decodeY(3.0F));
/* 1182 */     this.path.lineTo(decodeX(0.2F), decodeY(3.0F));
/* 1183 */     this.path.lineTo(decodeX(0.0F), decodeY(2.8F));
/* 1184 */     this.path.lineTo(decodeX(0.0F), decodeY(0.2F));
/* 1185 */     this.path.closePath();
/* 1186 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath26() {
/* 1190 */     this.path.reset();
/* 1191 */     this.path.moveTo(decodeX(0.2F), decodeY(0.4F));
/* 1192 */     this.path.lineTo(decodeX(0.4F), decodeY(0.2F));
/* 1193 */     this.path.lineTo(decodeX(2.4F), decodeY(0.2F));
/* 1194 */     this.path.lineTo(decodeX(2.8F), decodeY(0.6F));
/* 1195 */     this.path.lineTo(decodeX(2.8F), decodeY(2.8F));
/* 1196 */     this.path.lineTo(decodeX(0.2F), decodeY(2.8F));
/* 1197 */     this.path.lineTo(decodeX(0.2F), decodeY(0.4F));
/* 1198 */     this.path.closePath();
/* 1199 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath27() {
/* 1203 */     this.path.reset();
/* 1204 */     this.path.moveTo(decodeX(0.8F), decodeY(1.6666667F));
/* 1205 */     this.path.lineTo(decodeX(1.0F), decodeY(1.5F));
/* 1206 */     this.path.lineTo(decodeX(2.0F), decodeY(1.5F));
/* 1207 */     this.path.lineTo(decodeX(2.2F), decodeY(1.6666667F));
/* 1208 */     this.path.lineTo(decodeX(2.2F), decodeY(2.6F));
/* 1209 */     this.path.lineTo(decodeX(0.8F), decodeY(2.6F));
/* 1210 */     this.path.lineTo(decodeX(0.8F), decodeY(1.6666667F));
/* 1211 */     this.path.closePath();
/* 1212 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath28() {
/* 1216 */     this.path.reset();
/* 1217 */     this.path.moveTo(decodeX(1.1666666F), decodeY(0.2F));
/* 1218 */     this.path.lineTo(decodeX(1.1666666F), decodeY(1.1666666F));
/* 1219 */     this.path.lineTo(decodeX(2.2F), decodeY(1.1666666F));
/* 1220 */     this.path.lineTo(decodeX(2.2F), decodeY(0.4F));
/* 1221 */     this.path.lineTo(decodeX(2.0F), decodeY(0.4F));
/* 1222 */     this.path.lineTo(decodeX(2.0F), decodeY(1.0F));
/* 1223 */     this.path.lineTo(decodeX(1.6666667F), decodeY(1.0F));
/* 1224 */     this.path.lineTo(decodeX(1.6666667F), decodeY(0.4F));
/* 1225 */     this.path.lineTo(decodeX(2.2F), decodeY(0.4F));
/* 1226 */     this.path.lineTo(decodeX(2.2F), decodeY(0.2F));
/* 1227 */     this.path.lineTo(decodeX(1.1666666F), decodeY(0.2F));
/* 1228 */     this.path.closePath();
/* 1229 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath29() {
/* 1233 */     this.path.reset();
/* 1234 */     this.path.moveTo(decodeX(0.8F), decodeY(0.2F));
/* 1235 */     this.path.lineTo(decodeX(1.0F), decodeY(0.2F));
/* 1236 */     this.path.lineTo(decodeX(1.0F), decodeY(1.0F));
/* 1237 */     this.path.lineTo(decodeX(1.3333334F), decodeY(1.0F));
/* 1238 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.2F));
/* 1239 */     this.path.lineTo(decodeX(1.5F), decodeY(0.2F));
/* 1240 */     this.path.lineTo(decodeX(1.5F), decodeY(1.0F));
/* 1241 */     this.path.lineTo(decodeX(1.6666667F), decodeY(1.0F));
/* 1242 */     this.path.lineTo(decodeX(1.6666667F), decodeY(1.1666666F));
/* 1243 */     this.path.lineTo(decodeX(0.8F), decodeY(1.1666666F));
/* 1244 */     this.path.lineTo(decodeX(0.8F), decodeY(0.2F));
/* 1245 */     this.path.closePath();
/* 1246 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect14() {
/* 1250 */     this.rect.setRect(decodeX(0.8F), 
/* 1251 */         decodeY(2.6F), (
/* 1252 */         decodeX(2.2F) - decodeX(0.8F)), (
/* 1253 */         decodeY(2.8F) - decodeY(2.6F)));
/* 1254 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect15() {
/* 1258 */     this.rect.setRect(decodeX(0.36153847F), 
/* 1259 */         decodeY(2.3576922F), (
/* 1260 */         decodeX(0.63461536F) - decodeX(0.36153847F)), (
/* 1261 */         decodeY(2.6807692F) - decodeY(2.3576922F)));
/* 1262 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect16() {
/* 1266 */     this.rect.setRect(decodeX(2.376923F), 
/* 1267 */         decodeY(2.3807693F), (
/* 1268 */         decodeX(2.6384616F) - decodeX(2.376923F)), (
/* 1269 */         decodeY(2.6846154F) - decodeY(2.3807693F)));
/* 1270 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect17() {
/* 1274 */     this.rect.setRect(decodeX(0.4F), 
/* 1275 */         decodeY(2.4F), (
/* 1276 */         decodeX(0.6F) - decodeX(0.4F)), (
/* 1277 */         decodeY(2.6F) - decodeY(2.4F)));
/* 1278 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect18() {
/* 1282 */     this.rect.setRect(decodeX(2.4F), 
/* 1283 */         decodeY(2.4F), (
/* 1284 */         decodeX(2.6F) - decodeX(2.4F)), (
/* 1285 */         decodeY(2.6F) - decodeY(2.4F)));
/* 1286 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath30() {
/* 1290 */     this.path.reset();
/* 1291 */     this.path.moveTo(decodeX(0.4F), decodeY(1.5F));
/* 1292 */     this.path.lineTo(decodeX(0.4F), decodeY(2.6F));
/* 1293 */     this.path.lineTo(decodeX(0.6F), decodeY(2.8F));
/* 1294 */     this.path.lineTo(decodeX(2.4F), decodeY(2.8F));
/* 1295 */     this.path.lineTo(decodeX(2.6F), decodeY(2.6F));
/* 1296 */     this.path.lineTo(decodeX(2.6F), decodeY(1.5F));
/* 1297 */     this.path.lineTo(decodeX(0.4F), decodeY(1.5F));
/* 1298 */     this.path.closePath();
/* 1299 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath31() {
/* 1303 */     this.path.reset();
/* 1304 */     this.path.moveTo(decodeX(0.6F), decodeY(1.5F));
/* 1305 */     this.path.lineTo(decodeX(0.6F), decodeY(2.6F));
/* 1306 */     this.path.lineTo(decodeX(2.4F), decodeY(2.6F));
/* 1307 */     this.path.lineTo(decodeX(2.4F), decodeY(1.5F));
/* 1308 */     this.path.lineTo(decodeX(1.5F), decodeY(0.8F));
/* 1309 */     this.path.lineTo(decodeX(0.6F), decodeY(1.5F));
/* 1310 */     this.path.closePath();
/* 1311 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect19() {
/* 1315 */     this.rect.setRect(decodeX(1.6666667F), 
/* 1316 */         decodeY(1.6666667F), (
/* 1317 */         decodeX(2.2F) - decodeX(1.6666667F)), (
/* 1318 */         decodeY(2.2F) - decodeY(1.6666667F)));
/* 1319 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect20() {
/* 1323 */     this.rect.setRect(decodeX(1.8333333F), 
/* 1324 */         decodeY(1.8333333F), (
/* 1325 */         decodeX(2.0F) - decodeX(1.8333333F)), (
/* 1326 */         decodeY(2.0F) - decodeY(1.8333333F)));
/* 1327 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath32() {
/* 1331 */     this.path.reset();
/* 1332 */     this.path.moveTo(decodeX(1.0F), decodeY(2.8F));
/* 1333 */     this.path.lineTo(decodeX(1.5F), decodeY(2.8F));
/* 1334 */     this.path.lineTo(decodeX(1.5F), decodeY(1.8333333F));
/* 1335 */     this.path.lineTo(decodeX(1.3333334F), decodeY(1.6666667F));
/* 1336 */     this.path.lineTo(decodeX(1.1666666F), decodeY(1.6666667F));
/* 1337 */     this.path.lineTo(decodeX(1.0F), decodeY(1.8333333F));
/* 1338 */     this.path.lineTo(decodeX(1.0F), decodeY(2.8F));
/* 1339 */     this.path.closePath();
/* 1340 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect21() {
/* 1344 */     this.rect.setRect(decodeX(1.1666666F), 
/* 1345 */         decodeY(1.8333333F), (
/* 1346 */         decodeX(1.3333334F) - decodeX(1.1666666F)), (
/* 1347 */         decodeY(2.6F) - decodeY(1.8333333F)));
/* 1348 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Path2D decodePath33() {
/* 1352 */     this.path.reset();
/* 1353 */     this.path.moveTo(decodeX(0.0F), decodeY(1.3333334F));
/* 1354 */     this.path.lineTo(decodeX(0.0F), decodeY(1.6666667F));
/* 1355 */     this.path.lineTo(decodeX(0.4F), decodeY(1.6666667F));
/* 1356 */     this.path.lineTo(decodeX(1.3974359F), decodeY(0.6F));
/* 1357 */     this.path.lineTo(decodeX(1.596154F), decodeY(0.6F));
/* 1358 */     this.path.lineTo(decodeX(2.6F), decodeY(1.6666667F));
/* 1359 */     this.path.lineTo(decodeX(3.0F), decodeY(1.6666667F));
/* 1360 */     this.path.lineTo(decodeX(3.0F), decodeY(1.3333334F));
/* 1361 */     this.path.lineTo(decodeX(1.6666667F), decodeY(0.0F));
/* 1362 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.0F));
/* 1363 */     this.path.lineTo(decodeX(0.0F), decodeY(1.3333334F));
/* 1364 */     this.path.closePath();
/* 1365 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath34() {
/* 1369 */     this.path.reset();
/* 1370 */     this.path.moveTo(decodeX(0.2576923F), decodeY(1.3717948F));
/* 1371 */     this.path.lineTo(decodeX(0.2F), decodeY(1.5F));
/* 1372 */     this.path.lineTo(decodeX(0.3230769F), decodeY(1.4711539F));
/* 1373 */     this.path.lineTo(decodeX(1.4006411F), decodeY(0.40384617F));
/* 1374 */     this.path.lineTo(decodeX(1.5929487F), decodeY(0.4F));
/* 1375 */     this.path.lineTo(decodeX(2.6615386F), decodeY(1.4615384F));
/* 1376 */     this.path.lineTo(decodeX(2.8F), decodeY(1.5F));
/* 1377 */     this.path.lineTo(decodeX(2.7461538F), decodeY(1.3653846F));
/* 1378 */     this.path.lineTo(decodeX(1.6089742F), decodeY(0.19615385F));
/* 1379 */     this.path.lineTo(decodeX(1.4070512F), decodeY(0.2F));
/* 1380 */     this.path.lineTo(decodeX(0.2576923F), decodeY(1.3717948F));
/* 1381 */     this.path.closePath();
/* 1382 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath35() {
/* 1386 */     this.path.reset();
/* 1387 */     this.path.moveTo(decodeX(0.6F), decodeY(1.5F));
/* 1388 */     this.path.lineTo(decodeX(1.3333334F), decodeY(0.6F));
/* 1389 */     this.path.lineTo(decodeX(1.5F), decodeY(0.6F));
/* 1390 */     this.path.lineTo(decodeX(1.5F), decodeY(1.1666666F));
/* 1391 */     this.path.lineTo(decodeX(1.0F), decodeY(1.6666667F));
/* 1392 */     this.path.lineTo(decodeX(0.6F), decodeY(1.6666667F));
/* 1393 */     this.path.lineTo(decodeX(0.6F), decodeY(1.5F));
/* 1394 */     this.path.closePath();
/* 1395 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath36() {
/* 1399 */     this.path.reset();
/* 1400 */     this.path.moveTo(decodeX(1.6666667F), decodeY(0.6F));
/* 1401 */     this.path.lineTo(decodeX(1.5F), decodeY(0.6F));
/* 1402 */     this.path.lineTo(decodeX(1.5F), decodeY(1.1666666F));
/* 1403 */     this.path.lineTo(decodeX(2.0F), decodeY(1.6666667F));
/* 1404 */     this.path.lineTo(decodeX(2.4F), decodeY(1.6666667F));
/* 1405 */     this.path.lineTo(decodeX(2.4F), decodeY(1.3333334F));
/* 1406 */     this.path.lineTo(decodeX(1.6666667F), decodeY(0.6F));
/* 1407 */     this.path.closePath();
/* 1408 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect22() {
/* 1412 */     this.rect.setRect(decodeX(0.2F), 
/* 1413 */         decodeY(0.0F), (
/* 1414 */         decodeX(3.0F) - decodeX(0.2F)), (
/* 1415 */         decodeY(2.8F) - decodeY(0.0F)));
/* 1416 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect23() {
/* 1420 */     this.rect.setRect(decodeX(0.4F), 
/* 1421 */         decodeY(0.2F), (
/* 1422 */         decodeX(2.8F) - decodeX(0.4F)), (
/* 1423 */         decodeY(2.6F) - decodeY(0.2F)));
/* 1424 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect24() {
/* 1428 */     this.rect.setRect(decodeX(1.0F), 
/* 1429 */         decodeY(0.6F), (
/* 1430 */         decodeX(1.3333334F) - decodeX(1.0F)), (
/* 1431 */         decodeY(0.8F) - decodeY(0.6F)));
/* 1432 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect25() {
/* 1436 */     this.rect.setRect(decodeX(1.5F), 
/* 1437 */         decodeY(1.3333334F), (
/* 1438 */         decodeX(2.4F) - decodeX(1.5F)), (
/* 1439 */         decodeY(1.5F) - decodeY(1.3333334F)));
/* 1440 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect26() {
/* 1444 */     this.rect.setRect(decodeX(1.5F), 
/* 1445 */         decodeY(2.0F), (
/* 1446 */         decodeX(2.4F) - decodeX(1.5F)), (
/* 1447 */         decodeY(2.2F) - decodeY(2.0F)));
/* 1448 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse8() {
/* 1452 */     this.ellipse.setFrame(decodeX(0.6F), 
/* 1453 */         decodeY(0.8F), (
/* 1454 */         decodeX(2.2F) - decodeX(0.6F)), (
/* 1455 */         decodeY(2.4F) - decodeY(0.8F)));
/* 1456 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Ellipse2D decodeEllipse9() {
/* 1460 */     this.ellipse.setFrame(decodeX(0.8F), 
/* 1461 */         decodeY(1.0F), (
/* 1462 */         decodeX(2.0F) - decodeX(0.8F)), (
/* 1463 */         decodeY(2.2F) - decodeY(1.0F)));
/* 1464 */     return this.ellipse;
/*      */   }
/*      */   
/*      */   private Path2D decodePath37() {
/* 1468 */     this.path.reset();
/* 1469 */     this.path.moveTo(decodeX(0.0F), decodeY(2.8F));
/* 1470 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 1471 */     this.path.lineTo(decodeX(0.4F), decodeY(3.0F));
/* 1472 */     this.path.lineTo(decodeX(1.0F), decodeY(2.2F));
/* 1473 */     this.path.lineTo(decodeX(0.8F), decodeY(1.8333333F));
/* 1474 */     this.path.lineTo(decodeX(0.0F), decodeY(2.8F));
/* 1475 */     this.path.closePath();
/* 1476 */     return this.path;
/*      */   }
/*      */   
/*      */   private Path2D decodePath38() {
/* 1480 */     this.path.reset();
/* 1481 */     this.path.moveTo(decodeX(0.1826087F), decodeY(2.7217393F));
/* 1482 */     this.path.lineTo(decodeX(0.2826087F), decodeY(2.8217392F));
/* 1483 */     this.path.lineTo(decodeX(1.0181159F), decodeY(2.095652F));
/* 1484 */     this.path.lineTo(decodeX(0.9130435F), decodeY(1.9891305F));
/* 1485 */     this.path.lineTo(decodeX(0.1826087F), decodeY(2.7217393F));
/* 1486 */     this.path.closePath();
/* 1487 */     return this.path;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect27() {
/* 1491 */     this.rect.setRect(decodeX(1.0F), 
/* 1492 */         decodeY(1.3333334F), (
/* 1493 */         decodeX(1.3333334F) - decodeX(1.0F)), (
/* 1494 */         decodeY(1.5F) - decodeY(1.3333334F)));
/* 1495 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect28() {
/* 1499 */     this.rect.setRect(decodeX(1.5F), 
/* 1500 */         decodeY(1.3333334F), (
/* 1501 */         decodeX(1.8333333F) - decodeX(1.5F)), (
/* 1502 */         decodeY(1.5F) - decodeY(1.3333334F)));
/* 1503 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect29() {
/* 1507 */     this.rect.setRect(decodeX(1.5F), 
/* 1508 */         decodeY(1.6666667F), (
/* 1509 */         decodeX(1.8333333F) - decodeX(1.5F)), (
/* 1510 */         decodeY(1.8333333F) - decodeY(1.6666667F)));
/* 1511 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect30() {
/* 1515 */     this.rect.setRect(decodeX(1.0F), 
/* 1516 */         decodeY(1.6666667F), (
/* 1517 */         decodeX(1.3333334F) - decodeX(1.0F)), (
/* 1518 */         decodeY(1.8333333F) - decodeY(1.6666667F)));
/* 1519 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect31() {
/* 1523 */     this.rect.setRect(decodeX(0.0F), 
/* 1524 */         decodeY(0.0F), (
/* 1525 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 1526 */         decodeY(2.8F) - decodeY(0.0F)));
/* 1527 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect32() {
/* 1531 */     this.rect.setRect(decodeX(0.2F), 
/* 1532 */         decodeY(0.2F), (
/* 1533 */         decodeX(2.8F) - decodeX(0.2F)), (
/* 1534 */         decodeY(2.6F) - decodeY(0.2F)));
/* 1535 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect33() {
/* 1539 */     this.rect.setRect(decodeX(0.8F), 
/* 1540 */         decodeY(0.6F), (
/* 1541 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1542 */         decodeY(0.8F) - decodeY(0.6F)));
/* 1543 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect34() {
/* 1547 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1548 */         decodeY(0.6F), (
/* 1549 */         decodeX(2.2F) - decodeX(1.3333334F)), (
/* 1550 */         decodeY(0.8F) - decodeY(0.6F)));
/* 1551 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect35() {
/* 1555 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1556 */         decodeY(1.0F), (
/* 1557 */         decodeX(2.0F) - decodeX(1.3333334F)), (
/* 1558 */         decodeY(1.1666666F) - decodeY(1.0F)));
/* 1559 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect36() {
/* 1563 */     this.rect.setRect(decodeX(0.8F), 
/* 1564 */         decodeY(1.0F), (
/* 1565 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1566 */         decodeY(1.1666666F) - decodeY(1.0F)));
/* 1567 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect37() {
/* 1571 */     this.rect.setRect(decodeX(0.8F), 
/* 1572 */         decodeY(1.3333334F), (
/* 1573 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1574 */         decodeY(1.5F) - decodeY(1.3333334F)));
/* 1575 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect38() {
/* 1579 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1580 */         decodeY(1.3333334F), (
/* 1581 */         decodeX(2.2F) - decodeX(1.3333334F)), (
/* 1582 */         decodeY(1.5F) - decodeY(1.3333334F)));
/* 1583 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect39() {
/* 1587 */     this.rect.setRect(decodeX(0.8F), 
/* 1588 */         decodeY(1.6666667F), (
/* 1589 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1590 */         decodeY(1.8333333F) - decodeY(1.6666667F)));
/* 1591 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect40() {
/* 1595 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1596 */         decodeY(1.6666667F), (
/* 1597 */         decodeX(2.0F) - decodeX(1.3333334F)), (
/* 1598 */         decodeY(1.8333333F) - decodeY(1.6666667F)));
/* 1599 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect41() {
/* 1603 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1604 */         decodeY(2.0F), (
/* 1605 */         decodeX(2.2F) - decodeX(1.3333334F)), (
/* 1606 */         decodeY(2.2F) - decodeY(2.0F)));
/* 1607 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect42() {
/* 1611 */     this.rect.setRect(decodeX(0.8F), 
/* 1612 */         decodeY(2.0F), (
/* 1613 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1614 */         decodeY(2.2F) - decodeY(2.0F)));
/* 1615 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect43() {
/* 1619 */     this.rect.setRect(decodeX(0.8F), 
/* 1620 */         decodeY(0.8F), (
/* 1621 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1622 */         decodeY(1.0F) - decodeY(0.8F)));
/* 1623 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect44() {
/* 1627 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1628 */         decodeY(0.8F), (
/* 1629 */         decodeX(2.2F) - decodeX(1.3333334F)), (
/* 1630 */         decodeY(1.0F) - decodeY(0.8F)));
/* 1631 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect45() {
/* 1635 */     this.rect.setRect(decodeX(0.8F), 
/* 1636 */         decodeY(1.1666666F), (
/* 1637 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1638 */         decodeY(1.3333334F) - decodeY(1.1666666F)));
/* 1639 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect46() {
/* 1643 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1644 */         decodeY(1.1666666F), (
/* 1645 */         decodeX(2.0F) - decodeX(1.3333334F)), (
/* 1646 */         decodeY(1.3333334F) - decodeY(1.1666666F)));
/* 1647 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect47() {
/* 1651 */     this.rect.setRect(decodeX(0.8F), 
/* 1652 */         decodeY(1.5F), (
/* 1653 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1654 */         decodeY(1.6666667F) - decodeY(1.5F)));
/* 1655 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect48() {
/* 1659 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1660 */         decodeY(1.5F), (
/* 1661 */         decodeX(2.2F) - decodeX(1.3333334F)), (
/* 1662 */         decodeY(1.6666667F) - decodeY(1.5F)));
/* 1663 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect49() {
/* 1667 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1668 */         decodeY(1.8333333F), (
/* 1669 */         decodeX(2.0F) - decodeX(1.3333334F)), (
/* 1670 */         decodeY(2.0F) - decodeY(1.8333333F)));
/* 1671 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect50() {
/* 1675 */     this.rect.setRect(decodeX(0.8F), 
/* 1676 */         decodeY(1.8333333F), (
/* 1677 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1678 */         decodeY(2.0F) - decodeY(1.8333333F)));
/* 1679 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect51() {
/* 1683 */     this.rect.setRect(decodeX(0.8F), 
/* 1684 */         decodeY(2.2F), (
/* 1685 */         decodeX(1.1666666F) - decodeX(0.8F)), (
/* 1686 */         decodeY(2.4F) - decodeY(2.2F)));
/* 1687 */     return this.rect;
/*      */   }
/*      */   
/*      */   private Rectangle2D decodeRect52() {
/* 1691 */     this.rect.setRect(decodeX(1.3333334F), 
/* 1692 */         decodeY(2.2F), (
/* 1693 */         decodeX(2.2F) - decodeX(1.3333334F)), (
/* 1694 */         decodeY(2.4F) - decodeY(2.2F)));
/* 1695 */     return this.rect;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Paint decodeGradient1(Shape paramShape) {
/* 1701 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1702 */     float f1 = (float)rectangle2D.getX();
/* 1703 */     float f2 = (float)rectangle2D.getY();
/* 1704 */     float f3 = (float)rectangle2D.getWidth();
/* 1705 */     float f4 = (float)rectangle2D.getHeight();
/* 1706 */     return decodeGradient(0.046296295F * f3 + f1, 0.9675926F * f4 + f2, 0.4861111F * f3 + f1, 0.5324074F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color4, 
/*      */ 
/*      */           
/* 1709 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient2(Shape paramShape) {
/* 1714 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1715 */     float f1 = (float)rectangle2D.getX();
/* 1716 */     float f2 = (float)rectangle2D.getY();
/* 1717 */     float f3 = (float)rectangle2D.getWidth();
/* 1718 */     float f4 = (float)rectangle2D.getHeight();
/* 1719 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color6, 
/*      */ 
/*      */           
/* 1722 */           decodeColor(this.color6, this.color7, 0.5F), this.color7 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient3(Shape paramShape) {
/* 1727 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1728 */     float f1 = (float)rectangle2D.getX();
/* 1729 */     float f2 = (float)rectangle2D.getY();
/* 1730 */     float f3 = (float)rectangle2D.getWidth();
/* 1731 */     float f4 = (float)rectangle2D.getHeight();
/* 1732 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.04191617F, 0.10329342F, 0.16467066F, 0.24550897F, 0.3263473F, 0.6631737F, 1.0F }, new Color[] { this.color11, 
/*      */ 
/*      */           
/* 1735 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*      */           
/* 1737 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*      */           
/* 1739 */           decodeColor(this.color13, this.color14, 0.5F), this.color14 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient4(Shape paramShape) {
/* 1744 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1745 */     float f1 = (float)rectangle2D.getX();
/* 1746 */     float f2 = (float)rectangle2D.getY();
/* 1747 */     float f3 = (float)rectangle2D.getWidth();
/* 1748 */     float f4 = (float)rectangle2D.getHeight();
/* 1749 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color6, 
/*      */ 
/*      */           
/* 1752 */           decodeColor(this.color6, this.color15, 0.5F), this.color15 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient5(Shape paramShape) {
/* 1757 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1758 */     float f1 = (float)rectangle2D.getX();
/* 1759 */     float f2 = (float)rectangle2D.getY();
/* 1760 */     float f3 = (float)rectangle2D.getWidth();
/* 1761 */     float f4 = (float)rectangle2D.getHeight();
/* 1762 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color19, 
/*      */ 
/*      */           
/* 1765 */           decodeColor(this.color19, this.color20, 0.5F), this.color20 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient6(Shape paramShape) {
/* 1770 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1771 */     float f1 = (float)rectangle2D.getX();
/* 1772 */     float f2 = (float)rectangle2D.getY();
/* 1773 */     float f3 = (float)rectangle2D.getWidth();
/* 1774 */     float f4 = (float)rectangle2D.getHeight();
/* 1775 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.12724552F, 0.25449103F, 0.62724555F, 1.0F }, new Color[] { this.color21, 
/*      */ 
/*      */           
/* 1778 */           decodeColor(this.color21, this.color22, 0.5F), this.color22, 
/*      */           
/* 1780 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient7(Shape paramShape) {
/* 1785 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1786 */     float f1 = (float)rectangle2D.getX();
/* 1787 */     float f2 = (float)rectangle2D.getY();
/* 1788 */     float f3 = (float)rectangle2D.getWidth();
/* 1789 */     float f4 = (float)rectangle2D.getHeight();
/* 1790 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.06392045F, 0.1278409F, 0.5213069F, 0.91477275F }, new Color[] { this.color25, 
/*      */ 
/*      */           
/* 1793 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*      */           
/* 1795 */           decodeColor(this.color26, this.color27, 0.5F), this.color27 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient8(Shape paramShape) {
/* 1800 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1801 */     float f1 = (float)rectangle2D.getX();
/* 1802 */     float f2 = (float)rectangle2D.getY();
/* 1803 */     float f3 = (float)rectangle2D.getWidth();
/* 1804 */     float f4 = (float)rectangle2D.getHeight();
/* 1805 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.048295453F, 0.09659091F, 0.5482955F, 1.0F }, new Color[] { this.color28, 
/*      */ 
/*      */           
/* 1808 */           decodeColor(this.color28, this.color6, 0.5F), this.color6, 
/*      */           
/* 1810 */           decodeColor(this.color6, this.color15, 0.5F), this.color15 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient9(Shape paramShape) {
/* 1815 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1816 */     float f1 = (float)rectangle2D.getX();
/* 1817 */     float f2 = (float)rectangle2D.getY();
/* 1818 */     float f3 = (float)rectangle2D.getWidth();
/* 1819 */     float f4 = (float)rectangle2D.getHeight();
/* 1820 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color29, 
/*      */ 
/*      */           
/* 1823 */           decodeColor(this.color29, this.color30, 0.5F), this.color30 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient10(Shape paramShape) {
/* 1828 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1829 */     float f1 = (float)rectangle2D.getX();
/* 1830 */     float f2 = (float)rectangle2D.getY();
/* 1831 */     float f3 = (float)rectangle2D.getWidth();
/* 1832 */     float f4 = (float)rectangle2D.getHeight();
/* 1833 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.06534091F, 0.13068181F, 0.3096591F, 0.48863637F, 0.7443182F, 1.0F }, new Color[] { this.color11, 
/*      */ 
/*      */           
/* 1836 */           decodeColor(this.color11, this.color12, 0.5F), this.color12, 
/*      */           
/* 1838 */           decodeColor(this.color12, this.color31, 0.5F), this.color31, 
/*      */           
/* 1840 */           decodeColor(this.color31, this.color14, 0.5F), this.color14 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient11(Shape paramShape) {
/* 1845 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1846 */     float f1 = (float)rectangle2D.getX();
/* 1847 */     float f2 = (float)rectangle2D.getY();
/* 1848 */     float f3 = (float)rectangle2D.getWidth();
/* 1849 */     float f4 = (float)rectangle2D.getHeight();
/* 1850 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color33, 
/*      */ 
/*      */           
/* 1853 */           decodeColor(this.color33, this.color34, 0.5F), this.color34 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient12(Shape paramShape) {
/* 1858 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1859 */     float f1 = (float)rectangle2D.getX();
/* 1860 */     float f2 = (float)rectangle2D.getY();
/* 1861 */     float f3 = (float)rectangle2D.getWidth();
/* 1862 */     float f4 = (float)rectangle2D.getHeight();
/* 1863 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color36, 
/*      */ 
/*      */           
/* 1866 */           decodeColor(this.color36, this.color37, 0.5F), this.color37 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeRadial1(Shape paramShape) {
/* 1871 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1872 */     float f1 = (float)rectangle2D.getX();
/* 1873 */     float f2 = (float)rectangle2D.getY();
/* 1874 */     float f3 = (float)rectangle2D.getWidth();
/* 1875 */     float f4 = (float)rectangle2D.getHeight();
/* 1876 */     return decodeRadialGradient(0.5F * f3 + f1, 1.0F * f4 + f2, 0.53913116F, new float[] { 0.11290322F, 0.17419355F, 0.23548387F, 0.31129032F, 0.38709676F, 0.47903225F, 0.57096773F }, new Color[] { this.color40, 
/*      */ 
/*      */           
/* 1879 */           decodeColor(this.color40, this.color41, 0.5F), this.color41, 
/*      */           
/* 1881 */           decodeColor(this.color41, this.color41, 0.5F), this.color41, 
/*      */           
/* 1883 */           decodeColor(this.color41, this.color42, 0.5F), this.color42 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient13(Shape paramShape) {
/* 1888 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1889 */     float f1 = (float)rectangle2D.getX();
/* 1890 */     float f2 = (float)rectangle2D.getY();
/* 1891 */     float f3 = (float)rectangle2D.getWidth();
/* 1892 */     float f4 = (float)rectangle2D.getHeight();
/* 1893 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color45, 
/*      */ 
/*      */           
/* 1896 */           decodeColor(this.color45, this.color46, 0.5F), this.color46 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient14(Shape paramShape) {
/* 1901 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1902 */     float f1 = (float)rectangle2D.getX();
/* 1903 */     float f2 = (float)rectangle2D.getY();
/* 1904 */     float f3 = (float)rectangle2D.getWidth();
/* 1905 */     float f4 = (float)rectangle2D.getHeight();
/* 1906 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color47, 
/*      */ 
/*      */           
/* 1909 */           decodeColor(this.color47, this.color48, 0.5F), this.color48 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient15(Shape paramShape) {
/* 1914 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1915 */     float f1 = (float)rectangle2D.getX();
/* 1916 */     float f2 = (float)rectangle2D.getY();
/* 1917 */     float f3 = (float)rectangle2D.getWidth();
/* 1918 */     float f4 = (float)rectangle2D.getHeight();
/* 1919 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.3983871F, 0.7967742F, 0.8983871F, 1.0F }, new Color[] { this.color51, 
/*      */ 
/*      */           
/* 1922 */           decodeColor(this.color51, this.color52, 0.5F), this.color52, 
/*      */           
/* 1924 */           decodeColor(this.color52, this.color51, 0.5F), this.color51 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient16(Shape paramShape) {
/* 1929 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1930 */     float f1 = (float)rectangle2D.getX();
/* 1931 */     float f2 = (float)rectangle2D.getY();
/* 1932 */     float f3 = (float)rectangle2D.getWidth();
/* 1933 */     float f4 = (float)rectangle2D.getHeight();
/* 1934 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.061290324F, 0.12258065F, 0.5016129F, 0.88064516F, 0.9403226F, 1.0F }, new Color[] { this.color57, 
/*      */ 
/*      */           
/* 1937 */           decodeColor(this.color57, this.color58, 0.5F), this.color58, 
/*      */           
/* 1939 */           decodeColor(this.color58, this.color59, 0.5F), this.color59, 
/*      */           
/* 1941 */           decodeColor(this.color59, this.color44, 0.5F), this.color44 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient17(Shape paramShape) {
/* 1946 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1947 */     float f1 = (float)rectangle2D.getX();
/* 1948 */     float f2 = (float)rectangle2D.getY();
/* 1949 */     float f3 = (float)rectangle2D.getWidth();
/* 1950 */     float f4 = (float)rectangle2D.getHeight();
/* 1951 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.05F, 0.1F, 0.19193548F, 0.28387097F, 0.5209677F, 0.7580645F, 0.87903225F, 1.0F }, new Color[] { this.color60, 
/*      */ 
/*      */           
/* 1954 */           decodeColor(this.color60, this.color61, 0.5F), this.color61, 
/*      */           
/* 1956 */           decodeColor(this.color61, this.color62, 0.5F), this.color62, 
/*      */           
/* 1958 */           decodeColor(this.color62, this.color63, 0.5F), this.color63, 
/*      */           
/* 1960 */           decodeColor(this.color63, this.color64, 0.5F), this.color64 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient18(Shape paramShape) {
/* 1965 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1966 */     float f1 = (float)rectangle2D.getX();
/* 1967 */     float f2 = (float)rectangle2D.getY();
/* 1968 */     float f3 = (float)rectangle2D.getWidth();
/* 1969 */     float f4 = (float)rectangle2D.getHeight();
/* 1970 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.058064517F, 0.090322584F, 0.12258065F, 0.15645161F, 0.19032258F, 0.22741935F, 0.26451612F, 0.31290323F, 0.36129034F, 0.38225806F, 0.4032258F, 0.4596774F, 0.516129F, 0.54193544F, 0.56774193F, 0.61451614F, 0.66129035F, 0.70645165F, 0.7516129F }, new Color[] { this.color65, 
/*      */ 
/*      */           
/* 1973 */           decodeColor(this.color65, this.color40, 0.5F), this.color40, 
/*      */           
/* 1975 */           decodeColor(this.color40, this.color40, 0.5F), this.color40, 
/*      */           
/* 1977 */           decodeColor(this.color40, this.color65, 0.5F), this.color65, 
/*      */           
/* 1979 */           decodeColor(this.color65, this.color65, 0.5F), this.color65, 
/*      */           
/* 1981 */           decodeColor(this.color65, this.color40, 0.5F), this.color40, 
/*      */           
/* 1983 */           decodeColor(this.color40, this.color40, 0.5F), this.color40, 
/*      */           
/* 1985 */           decodeColor(this.color40, this.color66, 0.5F), this.color66, 
/*      */           
/* 1987 */           decodeColor(this.color66, this.color66, 0.5F), this.color66, 
/*      */           
/* 1989 */           decodeColor(this.color66, this.color40, 0.5F), this.color40 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient19(Shape paramShape) {
/* 1994 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 1995 */     float f1 = (float)rectangle2D.getX();
/* 1996 */     float f2 = (float)rectangle2D.getY();
/* 1997 */     float f3 = (float)rectangle2D.getWidth();
/* 1998 */     float f4 = (float)rectangle2D.getHeight();
/* 1999 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color67, 
/*      */ 
/*      */           
/* 2002 */           decodeColor(this.color67, this.color67, 0.5F), this.color67 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient20(Shape paramShape) {
/* 2007 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2008 */     float f1 = (float)rectangle2D.getX();
/* 2009 */     float f2 = (float)rectangle2D.getY();
/* 2010 */     float f3 = (float)rectangle2D.getWidth();
/* 2011 */     float f4 = (float)rectangle2D.getHeight();
/* 2012 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color74, 
/*      */ 
/*      */           
/* 2015 */           decodeColor(this.color74, this.color75, 0.5F), this.color75 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient21(Shape paramShape) {
/* 2020 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2021 */     float f1 = (float)rectangle2D.getX();
/* 2022 */     float f2 = (float)rectangle2D.getY();
/* 2023 */     float f3 = (float)rectangle2D.getWidth();
/* 2024 */     float f4 = (float)rectangle2D.getHeight();
/* 2025 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color77, 
/*      */ 
/*      */           
/* 2028 */           decodeColor(this.color77, this.color78, 0.5F), this.color78 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient22(Shape paramShape) {
/* 2033 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2034 */     float f1 = (float)rectangle2D.getX();
/* 2035 */     float f2 = (float)rectangle2D.getY();
/* 2036 */     float f3 = (float)rectangle2D.getWidth();
/* 2037 */     float f4 = (float)rectangle2D.getHeight();
/* 2038 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color79, 
/*      */ 
/*      */           
/* 2041 */           decodeColor(this.color79, this.color80, 0.5F), this.color80 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient23(Shape paramShape) {
/* 2046 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2047 */     float f1 = (float)rectangle2D.getX();
/* 2048 */     float f2 = (float)rectangle2D.getY();
/* 2049 */     float f3 = (float)rectangle2D.getWidth();
/* 2050 */     float f4 = (float)rectangle2D.getHeight();
/* 2051 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color81, 
/*      */ 
/*      */           
/* 2054 */           decodeColor(this.color81, this.color82, 0.5F), this.color82 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient24(Shape paramShape) {
/* 2059 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2060 */     float f1 = (float)rectangle2D.getX();
/* 2061 */     float f2 = (float)rectangle2D.getY();
/* 2062 */     float f3 = (float)rectangle2D.getWidth();
/* 2063 */     float f4 = (float)rectangle2D.getHeight();
/* 2064 */     return decodeGradient(0.43076923F * f3 + f1, 0.37820512F * f4 + f2, 0.7076923F * f3 + f1, 0.6730769F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color84, 
/*      */ 
/*      */           
/* 2067 */           decodeColor(this.color84, this.color85, 0.5F), this.color85 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient25(Shape paramShape) {
/* 2072 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2073 */     float f1 = (float)rectangle2D.getX();
/* 2074 */     float f2 = (float)rectangle2D.getY();
/* 2075 */     float f3 = (float)rectangle2D.getWidth();
/* 2076 */     float f4 = (float)rectangle2D.getHeight();
/* 2077 */     return decodeGradient(0.63076925F * f3 + f1, 0.3621795F * f4 + f2, 0.28846154F * f3 + f1, 0.73397434F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color84, 
/*      */ 
/*      */           
/* 2080 */           decodeColor(this.color84, this.color86, 0.5F), this.color86 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient26(Shape paramShape) {
/* 2085 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2086 */     float f1 = (float)rectangle2D.getX();
/* 2087 */     float f2 = (float)rectangle2D.getY();
/* 2088 */     float f3 = (float)rectangle2D.getWidth();
/* 2089 */     float f4 = (float)rectangle2D.getHeight();
/* 2090 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color87, 
/*      */ 
/*      */           
/* 2093 */           decodeColor(this.color87, this.color88, 0.5F), this.color88 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient27(Shape paramShape) {
/* 2098 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2099 */     float f1 = (float)rectangle2D.getX();
/* 2100 */     float f2 = (float)rectangle2D.getY();
/* 2101 */     float f3 = (float)rectangle2D.getWidth();
/* 2102 */     float f4 = (float)rectangle2D.getHeight();
/* 2103 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.056818184F, 0.11363637F, 0.34232956F, 0.57102275F, 0.7855114F, 1.0F }, new Color[] { this.color89, 
/*      */ 
/*      */           
/* 2106 */           decodeColor(this.color89, this.color90, 0.5F), this.color90, 
/*      */           
/* 2108 */           decodeColor(this.color90, this.color91, 0.5F), this.color91, 
/*      */           
/* 2110 */           decodeColor(this.color91, this.color92, 0.5F), this.color92 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient28(Shape paramShape) {
/* 2115 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2116 */     float f1 = (float)rectangle2D.getX();
/* 2117 */     float f2 = (float)rectangle2D.getY();
/* 2118 */     float f3 = (float)rectangle2D.getWidth();
/* 2119 */     float f4 = (float)rectangle2D.getHeight();
/* 2120 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.75F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color95, 
/*      */ 
/*      */           
/* 2123 */           decodeColor(this.color95, this.color96, 0.5F), this.color96 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeRadial2(Shape paramShape) {
/* 2128 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2129 */     float f1 = (float)rectangle2D.getX();
/* 2130 */     float f2 = (float)rectangle2D.getY();
/* 2131 */     float f3 = (float)rectangle2D.getWidth();
/* 2132 */     float f4 = (float)rectangle2D.getHeight();
/* 2133 */     return decodeRadialGradient(0.49223602F * f3 + f1, 0.9751553F * f4 + f2, 0.73615754F, new float[] { 0.0F, 0.40625F, 1.0F }, new Color[] { this.color97, 
/*      */ 
/*      */           
/* 2136 */           decodeColor(this.color97, this.color98, 0.5F), this.color98 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient29(Shape paramShape) {
/* 2141 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2142 */     float f1 = (float)rectangle2D.getX();
/* 2143 */     float f2 = (float)rectangle2D.getY();
/* 2144 */     float f3 = (float)rectangle2D.getWidth();
/* 2145 */     float f4 = (float)rectangle2D.getHeight();
/* 2146 */     return decodeGradient(0.0F * f3 + f1, 0.0F * f4 + f2, 1.0F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.38352272F, 0.4190341F, 0.45454547F, 0.484375F, 0.51420456F }, new Color[] { this.color99, 
/*      */ 
/*      */           
/* 2149 */           decodeColor(this.color99, this.color100, 0.5F), this.color100, 
/*      */           
/* 2151 */           decodeColor(this.color100, this.color101, 0.5F), this.color101 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient30(Shape paramShape) {
/* 2156 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2157 */     float f1 = (float)rectangle2D.getX();
/* 2158 */     float f2 = (float)rectangle2D.getY();
/* 2159 */     float f3 = (float)rectangle2D.getWidth();
/* 2160 */     float f4 = (float)rectangle2D.getHeight();
/* 2161 */     return decodeGradient(1.0F * f3 + f1, 0.0F * f4 + f2, 0.0F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.12215909F, 0.16051137F, 0.19886364F, 0.2627841F, 0.32670453F, 0.43039775F, 0.53409094F }, new Color[] { this.color102, 
/*      */ 
/*      */           
/* 2164 */           decodeColor(this.color102, this.color35, 0.5F), this.color35, 
/*      */           
/* 2166 */           decodeColor(this.color35, this.color35, 0.5F), this.color35, 
/*      */           
/* 2168 */           decodeColor(this.color35, this.color103, 0.5F), this.color103 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient31(Shape paramShape) {
/* 2173 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2174 */     float f1 = (float)rectangle2D.getX();
/* 2175 */     float f2 = (float)rectangle2D.getY();
/* 2176 */     float f3 = (float)rectangle2D.getWidth();
/* 2177 */     float f4 = (float)rectangle2D.getHeight();
/* 2178 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.038352273F, 0.07670455F, 0.24289773F, 0.4090909F, 0.7045455F, 1.0F }, new Color[] { this.color89, 
/*      */ 
/*      */           
/* 2181 */           decodeColor(this.color89, this.color90, 0.5F), this.color90, 
/*      */           
/* 2183 */           decodeColor(this.color90, this.color108, 0.5F), this.color108, 
/*      */           
/* 2185 */           decodeColor(this.color108, this.color92, 0.5F), this.color92 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient32(Shape paramShape) {
/* 2190 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2191 */     float f1 = (float)rectangle2D.getX();
/* 2192 */     float f2 = (float)rectangle2D.getY();
/* 2193 */     float f3 = (float)rectangle2D.getWidth();
/* 2194 */     float f4 = (float)rectangle2D.getHeight();
/* 2195 */     return decodeGradient(0.0F * f3 + f1, 0.0F * f4 + f2, 1.0F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.25F, 0.33522725F, 0.42045453F, 0.50142044F, 0.5823864F }, new Color[] { this.color109, 
/*      */ 
/*      */           
/* 2198 */           decodeColor(this.color109, this.color110, 0.5F), this.color110, 
/*      */           
/* 2200 */           decodeColor(this.color110, this.color109, 0.5F), this.color109 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient33(Shape paramShape) {
/* 2205 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2206 */     float f1 = (float)rectangle2D.getX();
/* 2207 */     float f2 = (float)rectangle2D.getY();
/* 2208 */     float f3 = (float)rectangle2D.getWidth();
/* 2209 */     float f4 = (float)rectangle2D.getHeight();
/* 2210 */     return decodeGradient(0.25F * f3 + f1, 0.0F * f4 + f2, 0.75F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.24147727F, 0.48295453F, 0.74147725F, 1.0F }, new Color[] { this.color114, 
/*      */ 
/*      */           
/* 2213 */           decodeColor(this.color114, this.color115, 0.5F), this.color115, 
/*      */           
/* 2215 */           decodeColor(this.color115, this.color114, 0.5F), this.color114 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient34(Shape paramShape) {
/* 2220 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2221 */     float f1 = (float)rectangle2D.getX();
/* 2222 */     float f2 = (float)rectangle2D.getY();
/* 2223 */     float f3 = (float)rectangle2D.getWidth();
/* 2224 */     float f4 = (float)rectangle2D.getHeight();
/* 2225 */     return decodeGradient(0.0F * f3 + f1, 0.0F * f4 + f2, 1.0F * f3 + f1, 0.0F * f4 + f2, new float[] { 0.0F, 0.21732955F, 0.4346591F }, new Color[] { this.color117, 
/*      */ 
/*      */           
/* 2228 */           decodeColor(this.color117, this.color118, 0.5F), this.color118 });
/*      */   }
/*      */ 
/*      */   
/*      */   private Paint decodeGradient35(Shape paramShape) {
/* 2233 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 2234 */     float f1 = (float)rectangle2D.getX();
/* 2235 */     float f2 = (float)rectangle2D.getY();
/* 2236 */     float f3 = (float)rectangle2D.getWidth();
/* 2237 */     float f4 = (float)rectangle2D.getHeight();
/* 2238 */     return decodeGradient(0.0F * f3 + f1, 0.0F * f4 + f2, 1.0F * f3 + f1, 0.0F * f4 + f2, new float[] { 0.0F, 0.21448864F, 0.42897728F, 0.7144886F, 1.0F }, new Color[] { this.color119, 
/*      */ 
/*      */           
/* 2241 */           decodeColor(this.color119, this.color120, 0.5F), this.color120, 
/*      */           
/* 2243 */           decodeColor(this.color120, this.color119, 0.5F), this.color119 });
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/FileChooserPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */