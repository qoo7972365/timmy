/*      */ package javax.swing.plaf.synth;
/*      */ 
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JSlider;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.basic.BasicSliderUI;
/*      */ import sun.swing.SwingUtilities2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SynthSliderUI
/*      */   extends BasicSliderUI
/*      */   implements PropertyChangeListener, SynthUI
/*      */ {
/*   53 */   private Rectangle valueRect = new Rectangle();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean paintValue;
/*      */ 
/*      */ 
/*      */   
/*      */   private Dimension lastSize;
/*      */ 
/*      */   
/*      */   private int trackHeight;
/*      */ 
/*      */   
/*      */   private int trackBorder;
/*      */ 
/*      */   
/*      */   private int thumbWidth;
/*      */ 
/*      */   
/*      */   private int thumbHeight;
/*      */ 
/*      */   
/*      */   private SynthStyle style;
/*      */ 
/*      */   
/*      */   private SynthStyle sliderTrackStyle;
/*      */ 
/*      */   
/*      */   private SynthStyle sliderThumbStyle;
/*      */ 
/*      */   
/*      */   private transient boolean thumbActive;
/*      */ 
/*      */   
/*      */   private transient boolean thumbPressed;
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*   93 */     return new SynthSliderUI((JSlider)paramJComponent);
/*      */   }
/*      */   
/*      */   protected SynthSliderUI(JSlider paramJSlider) {
/*   97 */     super(paramJSlider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installDefaults(JSlider paramJSlider) {
/*  105 */     updateStyle(paramJSlider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallDefaults(JSlider paramJSlider) {
/*  113 */     SynthContext synthContext = getContext(paramJSlider, 1);
/*  114 */     this.style.uninstallDefaults(synthContext);
/*  115 */     synthContext.dispose();
/*  116 */     this.style = null;
/*      */     
/*  118 */     synthContext = getContext(paramJSlider, Region.SLIDER_TRACK, 1);
/*  119 */     this.sliderTrackStyle.uninstallDefaults(synthContext);
/*  120 */     synthContext.dispose();
/*  121 */     this.sliderTrackStyle = null;
/*      */     
/*  123 */     synthContext = getContext(paramJSlider, Region.SLIDER_THUMB, 1);
/*  124 */     this.sliderThumbStyle.uninstallDefaults(synthContext);
/*  125 */     synthContext.dispose();
/*  126 */     this.sliderThumbStyle = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners(JSlider paramJSlider) {
/*  134 */     super.installListeners(paramJSlider);
/*  135 */     paramJSlider.addPropertyChangeListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallListeners(JSlider paramJSlider) {
/*  143 */     paramJSlider.removePropertyChangeListener(this);
/*  144 */     super.uninstallListeners(paramJSlider);
/*      */   }
/*      */   
/*      */   private void updateStyle(JSlider paramJSlider) {
/*  148 */     SynthContext synthContext = getContext(paramJSlider, 1);
/*  149 */     SynthStyle synthStyle = this.style;
/*  150 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*      */     
/*  152 */     if (this.style != synthStyle) {
/*  153 */       this
/*  154 */         .thumbWidth = this.style.getInt(synthContext, "Slider.thumbWidth", 30);
/*      */       
/*  156 */       this
/*  157 */         .thumbHeight = this.style.getInt(synthContext, "Slider.thumbHeight", 14);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  162 */       String str = (String)this.slider.getClientProperty("JComponent.sizeVariant");
/*      */       
/*  164 */       if (str != null) {
/*  165 */         if ("large".equals(str)) {
/*  166 */           this.thumbWidth = (int)(this.thumbWidth * 1.15D);
/*  167 */           this.thumbHeight = (int)(this.thumbHeight * 1.15D);
/*  168 */         } else if ("small".equals(str)) {
/*  169 */           this.thumbWidth = (int)(this.thumbWidth * 0.857D);
/*  170 */           this.thumbHeight = (int)(this.thumbHeight * 0.857D);
/*  171 */         } else if ("mini".equals(str)) {
/*  172 */           this.thumbWidth = (int)(this.thumbWidth * 0.784D);
/*  173 */           this.thumbHeight = (int)(this.thumbHeight * 0.784D);
/*      */         } 
/*      */       }
/*      */       
/*  177 */       this
/*  178 */         .trackBorder = this.style.getInt(synthContext, "Slider.trackBorder", 1);
/*      */       
/*  180 */       this.trackHeight = this.thumbHeight + this.trackBorder * 2;
/*      */       
/*  182 */       this.paintValue = this.style.getBoolean(synthContext, "Slider.paintValue", true);
/*      */       
/*  184 */       if (synthStyle != null) {
/*  185 */         uninstallKeyboardActions(paramJSlider);
/*  186 */         installKeyboardActions(paramJSlider);
/*      */       } 
/*      */     } 
/*  189 */     synthContext.dispose();
/*      */     
/*  191 */     synthContext = getContext(paramJSlider, Region.SLIDER_TRACK, 1);
/*  192 */     this
/*  193 */       .sliderTrackStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/*  194 */     synthContext.dispose();
/*      */     
/*  196 */     synthContext = getContext(paramJSlider, Region.SLIDER_THUMB, 1);
/*  197 */     this
/*  198 */       .sliderThumbStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/*  199 */     synthContext.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BasicSliderUI.TrackListener createTrackListener(JSlider paramJSlider) {
/*  207 */     return new SynthTrackListener();
/*      */   }
/*      */   
/*      */   private void updateThumbState(int paramInt1, int paramInt2) {
/*  211 */     setThumbActive(this.thumbRect.contains(paramInt1, paramInt2));
/*      */   }
/*      */   
/*      */   private void updateThumbState(int paramInt1, int paramInt2, boolean paramBoolean) {
/*  215 */     updateThumbState(paramInt1, paramInt2);
/*  216 */     setThumbPressed(paramBoolean);
/*      */   }
/*      */   
/*      */   private void setThumbActive(boolean paramBoolean) {
/*  220 */     if (this.thumbActive != paramBoolean) {
/*  221 */       this.thumbActive = paramBoolean;
/*  222 */       this.slider.repaint(this.thumbRect);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setThumbPressed(boolean paramBoolean) {
/*  227 */     if (this.thumbPressed != paramBoolean) {
/*  228 */       this.thumbPressed = paramBoolean;
/*  229 */       this.slider.repaint(this.thumbRect);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  238 */     if (paramJComponent == null) {
/*  239 */       throw new NullPointerException("Component must be non-null");
/*      */     }
/*  241 */     if (paramInt1 < 0 || paramInt2 < 0) {
/*  242 */       throw new IllegalArgumentException("Width and height must be >= 0");
/*      */     }
/*      */     
/*  245 */     if (this.slider.getPaintLabels() && labelsHaveSameBaselines()) {
/*      */       
/*  247 */       Insets insets = new Insets(0, 0, 0, 0);
/*  248 */       SynthContext synthContext = getContext(this.slider, Region.SLIDER_TRACK);
/*      */       
/*  250 */       this.style.getInsets(synthContext, insets);
/*  251 */       synthContext.dispose();
/*  252 */       if (this.slider.getOrientation() == 0) {
/*  253 */         int i = 0;
/*  254 */         if (this.paintValue) {
/*  255 */           SynthContext synthContext1 = getContext(this.slider);
/*      */           
/*  257 */           i = synthContext1.getStyle().getGraphicsUtils(synthContext1).getMaximumCharHeight(synthContext1);
/*  258 */           synthContext1.dispose();
/*      */         } 
/*  260 */         int j = 0;
/*  261 */         if (this.slider.getPaintTicks()) {
/*  262 */           j = getTickLength();
/*      */         }
/*  264 */         int k = getHeightOfTallestLabel();
/*  265 */         int m = i + this.trackHeight + insets.top + insets.bottom + j + k + 4;
/*      */ 
/*      */         
/*  268 */         int n = paramInt2 / 2 - m / 2;
/*  269 */         n += i + 2;
/*  270 */         n += this.trackHeight + insets.top + insets.bottom;
/*  271 */         n += j + 2;
/*  272 */         JComponent jComponent = this.slider.getLabelTable().elements().nextElement();
/*  273 */         Dimension dimension = jComponent.getPreferredSize();
/*  274 */         return n + jComponent.getBaseline(dimension.width, dimension.height);
/*      */       } 
/*      */ 
/*      */       
/*  278 */       Integer integer = this.slider.getInverted() ? getLowestValue() : getHighestValue();
/*  279 */       if (integer != null) {
/*  280 */         int i = this.insetCache.top;
/*  281 */         int j = 0;
/*  282 */         if (this.paintValue) {
/*  283 */           SynthContext synthContext1 = getContext(this.slider);
/*      */           
/*  285 */           j = synthContext1.getStyle().getGraphicsUtils(synthContext1).getMaximumCharHeight(synthContext1);
/*  286 */           synthContext1.dispose();
/*      */         } 
/*  288 */         int k = paramInt2 - this.insetCache.top - this.insetCache.bottom;
/*      */         
/*  290 */         int m = i + j;
/*  291 */         int n = k - j;
/*  292 */         int i1 = yPositionForValue(integer.intValue(), m, n);
/*      */         
/*  294 */         JComponent jComponent = (JComponent)this.slider.getLabelTable().get(integer);
/*  295 */         Dimension dimension = jComponent.getPreferredSize();
/*  296 */         return i1 - dimension.height / 2 + jComponent
/*  297 */           .getBaseline(dimension.width, dimension.height);
/*      */       } 
/*      */     } 
/*      */     
/*  301 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  309 */     recalculateIfInsetsChanged();
/*  310 */     Dimension dimension = new Dimension(this.contentRect.width, this.contentRect.height);
/*  311 */     if (this.slider.getOrientation() == 1) {
/*  312 */       dimension.height = 200;
/*      */     } else {
/*  314 */       dimension.width = 200;
/*      */     } 
/*  316 */     Insets insets = this.slider.getInsets();
/*  317 */     dimension.width += insets.left + insets.right;
/*  318 */     dimension.height += insets.top + insets.bottom;
/*  319 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  327 */     recalculateIfInsetsChanged();
/*  328 */     Dimension dimension = new Dimension(this.contentRect.width, this.contentRect.height);
/*  329 */     if (this.slider.getOrientation() == 1) {
/*  330 */       dimension.height = this.thumbRect.height + this.insetCache.top + this.insetCache.bottom;
/*      */     } else {
/*  332 */       dimension.width = this.thumbRect.width + this.insetCache.left + this.insetCache.right;
/*      */     } 
/*  334 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculateGeometry() {
/*  342 */     calculateThumbSize();
/*  343 */     layout();
/*  344 */     calculateThumbLocation();
/*      */   }
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
/*      */   protected void layout() {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: aload_0
/*      */     //   2: getfield slider : Ljavax/swing/JSlider;
/*      */     //   5: invokevirtual getContext : (Ljavax/swing/JComponent;)Ljavax/swing/plaf/synth/SynthContext;
/*      */     //   8: astore_1
/*      */     //   9: aload_0
/*      */     //   10: getfield style : Ljavax/swing/plaf/synth/SynthStyle;
/*      */     //   13: aload_1
/*      */     //   14: invokevirtual getGraphicsUtils : (Ljavax/swing/plaf/synth/SynthContext;)Ljavax/swing/plaf/synth/SynthGraphicsUtils;
/*      */     //   17: astore_2
/*      */     //   18: new java/awt/Insets
/*      */     //   21: dup
/*      */     //   22: iconst_0
/*      */     //   23: iconst_0
/*      */     //   24: iconst_0
/*      */     //   25: iconst_0
/*      */     //   26: invokespecial <init> : (IIII)V
/*      */     //   29: astore_3
/*      */     //   30: aload_0
/*      */     //   31: aload_0
/*      */     //   32: getfield slider : Ljavax/swing/JSlider;
/*      */     //   35: getstatic javax/swing/plaf/synth/Region.SLIDER_TRACK : Ljavax/swing/plaf/synth/Region;
/*      */     //   38: invokespecial getContext : (Ljavax/swing/JComponent;Ljavax/swing/plaf/synth/Region;)Ljavax/swing/plaf/synth/SynthContext;
/*      */     //   41: astore #4
/*      */     //   43: aload_0
/*      */     //   44: getfield style : Ljavax/swing/plaf/synth/SynthStyle;
/*      */     //   47: aload #4
/*      */     //   49: aload_3
/*      */     //   50: invokevirtual getInsets : (Ljavax/swing/plaf/synth/SynthContext;Ljava/awt/Insets;)Ljava/awt/Insets;
/*      */     //   53: pop
/*      */     //   54: aload #4
/*      */     //   56: invokevirtual dispose : ()V
/*      */     //   59: aload_0
/*      */     //   60: getfield slider : Ljavax/swing/JSlider;
/*      */     //   63: invokevirtual getOrientation : ()I
/*      */     //   66: ifne -> 629
/*      */     //   69: aload_0
/*      */     //   70: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   73: iconst_0
/*      */     //   74: putfield height : I
/*      */     //   77: aload_0
/*      */     //   78: getfield paintValue : Z
/*      */     //   81: ifeq -> 96
/*      */     //   84: aload_0
/*      */     //   85: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   88: aload_2
/*      */     //   89: aload_1
/*      */     //   90: invokevirtual getMaximumCharHeight : (Ljavax/swing/plaf/synth/SynthContext;)I
/*      */     //   93: putfield height : I
/*      */     //   96: aload_0
/*      */     //   97: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   100: aload_0
/*      */     //   101: getfield trackHeight : I
/*      */     //   104: putfield height : I
/*      */     //   107: aload_0
/*      */     //   108: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   111: iconst_0
/*      */     //   112: putfield height : I
/*      */     //   115: aload_0
/*      */     //   116: getfield slider : Ljavax/swing/JSlider;
/*      */     //   119: invokevirtual getPaintTicks : ()Z
/*      */     //   122: ifeq -> 136
/*      */     //   125: aload_0
/*      */     //   126: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   129: aload_0
/*      */     //   130: invokevirtual getTickLength : ()I
/*      */     //   133: putfield height : I
/*      */     //   136: aload_0
/*      */     //   137: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   140: iconst_0
/*      */     //   141: putfield height : I
/*      */     //   144: aload_0
/*      */     //   145: getfield slider : Ljavax/swing/JSlider;
/*      */     //   148: invokevirtual getPaintLabels : ()Z
/*      */     //   151: ifeq -> 165
/*      */     //   154: aload_0
/*      */     //   155: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   158: aload_0
/*      */     //   159: invokevirtual getHeightOfTallestLabel : ()I
/*      */     //   162: putfield height : I
/*      */     //   165: aload_0
/*      */     //   166: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   169: aload_0
/*      */     //   170: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   173: getfield height : I
/*      */     //   176: aload_0
/*      */     //   177: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   180: getfield height : I
/*      */     //   183: iadd
/*      */     //   184: aload_3
/*      */     //   185: getfield top : I
/*      */     //   188: iadd
/*      */     //   189: aload_3
/*      */     //   190: getfield bottom : I
/*      */     //   193: iadd
/*      */     //   194: aload_0
/*      */     //   195: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   198: getfield height : I
/*      */     //   201: iadd
/*      */     //   202: aload_0
/*      */     //   203: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   206: getfield height : I
/*      */     //   209: iadd
/*      */     //   210: iconst_4
/*      */     //   211: iadd
/*      */     //   212: putfield height : I
/*      */     //   215: aload_0
/*      */     //   216: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   219: aload_0
/*      */     //   220: getfield slider : Ljavax/swing/JSlider;
/*      */     //   223: invokevirtual getWidth : ()I
/*      */     //   226: aload_0
/*      */     //   227: getfield insetCache : Ljava/awt/Insets;
/*      */     //   230: getfield left : I
/*      */     //   233: isub
/*      */     //   234: aload_0
/*      */     //   235: getfield insetCache : Ljava/awt/Insets;
/*      */     //   238: getfield right : I
/*      */     //   241: isub
/*      */     //   242: putfield width : I
/*      */     //   245: iconst_0
/*      */     //   246: istore #5
/*      */     //   248: aload_0
/*      */     //   249: getfield slider : Ljavax/swing/JSlider;
/*      */     //   252: invokevirtual getPaintLabels : ()Z
/*      */     //   255: ifeq -> 418
/*      */     //   258: aload_0
/*      */     //   259: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   262: aload_0
/*      */     //   263: getfield insetCache : Ljava/awt/Insets;
/*      */     //   266: getfield left : I
/*      */     //   269: putfield x : I
/*      */     //   272: aload_0
/*      */     //   273: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   276: aload_0
/*      */     //   277: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   280: getfield width : I
/*      */     //   283: putfield width : I
/*      */     //   286: aload_0
/*      */     //   287: getfield slider : Ljavax/swing/JSlider;
/*      */     //   290: invokevirtual getLabelTable : ()Ljava/util/Dictionary;
/*      */     //   293: astore #6
/*      */     //   295: aload #6
/*      */     //   297: ifnull -> 418
/*      */     //   300: aload_0
/*      */     //   301: getfield slider : Ljavax/swing/JSlider;
/*      */     //   304: invokevirtual getMinimum : ()I
/*      */     //   307: istore #7
/*      */     //   309: aload_0
/*      */     //   310: getfield slider : Ljavax/swing/JSlider;
/*      */     //   313: invokevirtual getMaximum : ()I
/*      */     //   316: istore #8
/*      */     //   318: ldc 2147483647
/*      */     //   320: istore #9
/*      */     //   322: ldc -2147483648
/*      */     //   324: istore #10
/*      */     //   326: aload #6
/*      */     //   328: invokevirtual keys : ()Ljava/util/Enumeration;
/*      */     //   331: astore #11
/*      */     //   333: aload #11
/*      */     //   335: invokeinterface hasMoreElements : ()Z
/*      */     //   340: ifeq -> 397
/*      */     //   343: aload #11
/*      */     //   345: invokeinterface nextElement : ()Ljava/lang/Object;
/*      */     //   350: checkcast java/lang/Integer
/*      */     //   353: invokevirtual intValue : ()I
/*      */     //   356: istore #12
/*      */     //   358: iload #12
/*      */     //   360: iload #7
/*      */     //   362: if_icmplt -> 376
/*      */     //   365: iload #12
/*      */     //   367: iload #9
/*      */     //   369: if_icmpge -> 376
/*      */     //   372: iload #12
/*      */     //   374: istore #9
/*      */     //   376: iload #12
/*      */     //   378: iload #8
/*      */     //   380: if_icmpgt -> 394
/*      */     //   383: iload #12
/*      */     //   385: iload #10
/*      */     //   387: if_icmple -> 394
/*      */     //   390: iload #12
/*      */     //   392: istore #10
/*      */     //   394: goto -> 333
/*      */     //   397: aload_0
/*      */     //   398: iload #9
/*      */     //   400: invokespecial getPadForLabel : (I)I
/*      */     //   403: istore #5
/*      */     //   405: iload #5
/*      */     //   407: aload_0
/*      */     //   408: iload #10
/*      */     //   410: invokespecial getPadForLabel : (I)I
/*      */     //   413: invokestatic max : (II)I
/*      */     //   416: istore #5
/*      */     //   418: aload_0
/*      */     //   419: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   422: aload_0
/*      */     //   423: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   426: aload_0
/*      */     //   427: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   430: aload_0
/*      */     //   431: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   434: aload_0
/*      */     //   435: getfield insetCache : Ljava/awt/Insets;
/*      */     //   438: getfield left : I
/*      */     //   441: iload #5
/*      */     //   443: iadd
/*      */     //   444: dup_x1
/*      */     //   445: putfield x : I
/*      */     //   448: dup_x1
/*      */     //   449: putfield x : I
/*      */     //   452: dup_x1
/*      */     //   453: putfield x : I
/*      */     //   456: putfield x : I
/*      */     //   459: aload_0
/*      */     //   460: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   463: aload_0
/*      */     //   464: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   467: aload_0
/*      */     //   468: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   471: aload_0
/*      */     //   472: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   475: aload_0
/*      */     //   476: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   479: getfield width : I
/*      */     //   482: iload #5
/*      */     //   484: iconst_2
/*      */     //   485: imul
/*      */     //   486: isub
/*      */     //   487: dup_x1
/*      */     //   488: putfield width : I
/*      */     //   491: dup_x1
/*      */     //   492: putfield width : I
/*      */     //   495: dup_x1
/*      */     //   496: putfield width : I
/*      */     //   499: putfield width : I
/*      */     //   502: aload_0
/*      */     //   503: getfield slider : Ljavax/swing/JSlider;
/*      */     //   506: invokevirtual getHeight : ()I
/*      */     //   509: iconst_2
/*      */     //   510: idiv
/*      */     //   511: aload_0
/*      */     //   512: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   515: getfield height : I
/*      */     //   518: iconst_2
/*      */     //   519: idiv
/*      */     //   520: isub
/*      */     //   521: istore #6
/*      */     //   523: aload_0
/*      */     //   524: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   527: iload #6
/*      */     //   529: putfield y : I
/*      */     //   532: iload #6
/*      */     //   534: aload_0
/*      */     //   535: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   538: getfield height : I
/*      */     //   541: iconst_2
/*      */     //   542: iadd
/*      */     //   543: iadd
/*      */     //   544: istore #6
/*      */     //   546: aload_0
/*      */     //   547: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   550: iload #6
/*      */     //   552: aload_3
/*      */     //   553: getfield top : I
/*      */     //   556: iadd
/*      */     //   557: putfield y : I
/*      */     //   560: iload #6
/*      */     //   562: aload_0
/*      */     //   563: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   566: getfield height : I
/*      */     //   569: aload_3
/*      */     //   570: getfield top : I
/*      */     //   573: iadd
/*      */     //   574: aload_3
/*      */     //   575: getfield bottom : I
/*      */     //   578: iadd
/*      */     //   579: iadd
/*      */     //   580: istore #6
/*      */     //   582: aload_0
/*      */     //   583: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   586: iload #6
/*      */     //   588: putfield y : I
/*      */     //   591: iload #6
/*      */     //   593: aload_0
/*      */     //   594: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   597: getfield height : I
/*      */     //   600: iconst_2
/*      */     //   601: iadd
/*      */     //   602: iadd
/*      */     //   603: istore #6
/*      */     //   605: aload_0
/*      */     //   606: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   609: iload #6
/*      */     //   611: putfield y : I
/*      */     //   614: iload #6
/*      */     //   616: aload_0
/*      */     //   617: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   620: getfield height : I
/*      */     //   623: iadd
/*      */     //   624: istore #6
/*      */     //   626: goto -> 1234
/*      */     //   629: aload_0
/*      */     //   630: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   633: aload_0
/*      */     //   634: getfield trackHeight : I
/*      */     //   637: putfield width : I
/*      */     //   640: aload_0
/*      */     //   641: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   644: iconst_0
/*      */     //   645: putfield width : I
/*      */     //   648: aload_0
/*      */     //   649: getfield slider : Ljavax/swing/JSlider;
/*      */     //   652: invokevirtual getPaintTicks : ()Z
/*      */     //   655: ifeq -> 669
/*      */     //   658: aload_0
/*      */     //   659: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   662: aload_0
/*      */     //   663: invokevirtual getTickLength : ()I
/*      */     //   666: putfield width : I
/*      */     //   669: aload_0
/*      */     //   670: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   673: iconst_0
/*      */     //   674: putfield width : I
/*      */     //   677: aload_0
/*      */     //   678: getfield slider : Ljavax/swing/JSlider;
/*      */     //   681: invokevirtual getPaintLabels : ()Z
/*      */     //   684: ifeq -> 698
/*      */     //   687: aload_0
/*      */     //   688: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   691: aload_0
/*      */     //   692: invokevirtual getWidthOfWidestLabel : ()I
/*      */     //   695: putfield width : I
/*      */     //   698: aload_0
/*      */     //   699: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   702: aload_0
/*      */     //   703: getfield insetCache : Ljava/awt/Insets;
/*      */     //   706: getfield top : I
/*      */     //   709: putfield y : I
/*      */     //   712: aload_0
/*      */     //   713: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   716: iconst_0
/*      */     //   717: putfield height : I
/*      */     //   720: aload_0
/*      */     //   721: getfield paintValue : Z
/*      */     //   724: ifeq -> 739
/*      */     //   727: aload_0
/*      */     //   728: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   731: aload_2
/*      */     //   732: aload_1
/*      */     //   733: invokevirtual getMaximumCharHeight : (Ljavax/swing/plaf/synth/SynthContext;)I
/*      */     //   736: putfield height : I
/*      */     //   739: aload_0
/*      */     //   740: getfield slider : Ljavax/swing/JSlider;
/*      */     //   743: aload_0
/*      */     //   744: getfield slider : Ljavax/swing/JSlider;
/*      */     //   747: invokevirtual getFont : ()Ljava/awt/Font;
/*      */     //   750: invokevirtual getFontMetrics : (Ljava/awt/Font;)Ljava/awt/FontMetrics;
/*      */     //   753: astore #5
/*      */     //   755: aload_0
/*      */     //   756: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   759: aload_2
/*      */     //   760: aload_1
/*      */     //   761: aload_0
/*      */     //   762: getfield slider : Ljavax/swing/JSlider;
/*      */     //   765: invokevirtual getFont : ()Ljava/awt/Font;
/*      */     //   768: aload #5
/*      */     //   770: new java/lang/StringBuilder
/*      */     //   773: dup
/*      */     //   774: invokespecial <init> : ()V
/*      */     //   777: ldc ''
/*      */     //   779: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   782: aload_0
/*      */     //   783: getfield slider : Ljavax/swing/JSlider;
/*      */     //   786: invokevirtual getMaximum : ()I
/*      */     //   789: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   792: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   795: invokevirtual computeStringWidth : (Ljavax/swing/plaf/synth/SynthContext;Ljava/awt/Font;Ljava/awt/FontMetrics;Ljava/lang/String;)I
/*      */     //   798: aload_2
/*      */     //   799: aload_1
/*      */     //   800: aload_0
/*      */     //   801: getfield slider : Ljavax/swing/JSlider;
/*      */     //   804: invokevirtual getFont : ()Ljava/awt/Font;
/*      */     //   807: aload #5
/*      */     //   809: new java/lang/StringBuilder
/*      */     //   812: dup
/*      */     //   813: invokespecial <init> : ()V
/*      */     //   816: ldc ''
/*      */     //   818: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   821: aload_0
/*      */     //   822: getfield slider : Ljavax/swing/JSlider;
/*      */     //   825: invokevirtual getMinimum : ()I
/*      */     //   828: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   831: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   834: invokevirtual computeStringWidth : (Ljavax/swing/plaf/synth/SynthContext;Ljava/awt/Font;Ljava/awt/FontMetrics;Ljava/lang/String;)I
/*      */     //   837: invokestatic max : (II)I
/*      */     //   840: putfield width : I
/*      */     //   843: aload_0
/*      */     //   844: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   847: getfield width : I
/*      */     //   850: iconst_2
/*      */     //   851: idiv
/*      */     //   852: istore #6
/*      */     //   854: aload_3
/*      */     //   855: getfield left : I
/*      */     //   858: aload_0
/*      */     //   859: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   862: getfield width : I
/*      */     //   865: iconst_2
/*      */     //   866: idiv
/*      */     //   867: iadd
/*      */     //   868: istore #7
/*      */     //   870: aload_0
/*      */     //   871: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   874: getfield width : I
/*      */     //   877: iconst_2
/*      */     //   878: idiv
/*      */     //   879: aload_3
/*      */     //   880: getfield right : I
/*      */     //   883: iadd
/*      */     //   884: aload_0
/*      */     //   885: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   888: getfield width : I
/*      */     //   891: iadd
/*      */     //   892: aload_0
/*      */     //   893: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   896: getfield width : I
/*      */     //   899: iadd
/*      */     //   900: istore #8
/*      */     //   902: aload_0
/*      */     //   903: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   906: iload #7
/*      */     //   908: iload #6
/*      */     //   910: invokestatic max : (II)I
/*      */     //   913: iload #8
/*      */     //   915: iload #6
/*      */     //   917: invokestatic max : (II)I
/*      */     //   920: iadd
/*      */     //   921: iconst_2
/*      */     //   922: iadd
/*      */     //   923: aload_0
/*      */     //   924: getfield insetCache : Ljava/awt/Insets;
/*      */     //   927: getfield left : I
/*      */     //   930: iadd
/*      */     //   931: aload_0
/*      */     //   932: getfield insetCache : Ljava/awt/Insets;
/*      */     //   935: getfield right : I
/*      */     //   938: iadd
/*      */     //   939: putfield width : I
/*      */     //   942: aload_0
/*      */     //   943: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   946: aload_0
/*      */     //   947: getfield slider : Ljavax/swing/JSlider;
/*      */     //   950: invokevirtual getHeight : ()I
/*      */     //   953: aload_0
/*      */     //   954: getfield insetCache : Ljava/awt/Insets;
/*      */     //   957: getfield top : I
/*      */     //   960: isub
/*      */     //   961: aload_0
/*      */     //   962: getfield insetCache : Ljava/awt/Insets;
/*      */     //   965: getfield bottom : I
/*      */     //   968: isub
/*      */     //   969: putfield height : I
/*      */     //   972: aload_0
/*      */     //   973: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   976: aload_0
/*      */     //   977: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   980: aload_0
/*      */     //   981: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   984: aload_0
/*      */     //   985: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   988: getfield y : I
/*      */     //   991: aload_0
/*      */     //   992: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   995: getfield height : I
/*      */     //   998: iadd
/*      */     //   999: dup_x1
/*      */     //   1000: putfield y : I
/*      */     //   1003: dup_x1
/*      */     //   1004: putfield y : I
/*      */     //   1007: putfield y : I
/*      */     //   1010: aload_0
/*      */     //   1011: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   1014: aload_0
/*      */     //   1015: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   1018: aload_0
/*      */     //   1019: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   1022: aload_0
/*      */     //   1023: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   1026: getfield height : I
/*      */     //   1029: aload_0
/*      */     //   1030: getfield valueRect : Ljava/awt/Rectangle;
/*      */     //   1033: getfield height : I
/*      */     //   1036: isub
/*      */     //   1037: dup_x1
/*      */     //   1038: putfield height : I
/*      */     //   1041: dup_x1
/*      */     //   1042: putfield height : I
/*      */     //   1045: putfield height : I
/*      */     //   1048: aload_0
/*      */     //   1049: getfield slider : Ljavax/swing/JSlider;
/*      */     //   1052: invokevirtual getWidth : ()I
/*      */     //   1055: iconst_2
/*      */     //   1056: idiv
/*      */     //   1057: aload_0
/*      */     //   1058: getfield contentRect : Ljava/awt/Rectangle;
/*      */     //   1061: getfield width : I
/*      */     //   1064: iconst_2
/*      */     //   1065: idiv
/*      */     //   1066: isub
/*      */     //   1067: istore #9
/*      */     //   1069: aload_0
/*      */     //   1070: getfield slider : Ljavax/swing/JSlider;
/*      */     //   1073: invokestatic isLeftToRight : (Ljava/awt/Component;)Z
/*      */     //   1076: ifeq -> 1163
/*      */     //   1079: iload #6
/*      */     //   1081: iload #7
/*      */     //   1083: if_icmple -> 1096
/*      */     //   1086: iload #9
/*      */     //   1088: iload #6
/*      */     //   1090: iload #7
/*      */     //   1092: isub
/*      */     //   1093: iadd
/*      */     //   1094: istore #9
/*      */     //   1096: aload_0
/*      */     //   1097: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   1100: iload #9
/*      */     //   1102: aload_3
/*      */     //   1103: getfield left : I
/*      */     //   1106: iadd
/*      */     //   1107: putfield x : I
/*      */     //   1110: iload #9
/*      */     //   1112: aload_3
/*      */     //   1113: getfield left : I
/*      */     //   1116: aload_0
/*      */     //   1117: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   1120: getfield width : I
/*      */     //   1123: iadd
/*      */     //   1124: aload_3
/*      */     //   1125: getfield right : I
/*      */     //   1128: iadd
/*      */     //   1129: iadd
/*      */     //   1130: istore #9
/*      */     //   1132: aload_0
/*      */     //   1133: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   1136: iload #9
/*      */     //   1138: putfield x : I
/*      */     //   1141: aload_0
/*      */     //   1142: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   1145: iload #9
/*      */     //   1147: aload_0
/*      */     //   1148: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   1151: getfield width : I
/*      */     //   1154: iadd
/*      */     //   1155: iconst_2
/*      */     //   1156: iadd
/*      */     //   1157: putfield x : I
/*      */     //   1160: goto -> 1234
/*      */     //   1163: iload #6
/*      */     //   1165: iload #8
/*      */     //   1167: if_icmple -> 1180
/*      */     //   1170: iload #9
/*      */     //   1172: iload #6
/*      */     //   1174: iload #8
/*      */     //   1176: isub
/*      */     //   1177: iadd
/*      */     //   1178: istore #9
/*      */     //   1180: aload_0
/*      */     //   1181: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   1184: iload #9
/*      */     //   1186: putfield x : I
/*      */     //   1189: iload #9
/*      */     //   1191: aload_0
/*      */     //   1192: getfield labelRect : Ljava/awt/Rectangle;
/*      */     //   1195: getfield width : I
/*      */     //   1198: iconst_2
/*      */     //   1199: iadd
/*      */     //   1200: iadd
/*      */     //   1201: istore #9
/*      */     //   1203: aload_0
/*      */     //   1204: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   1207: iload #9
/*      */     //   1209: putfield x : I
/*      */     //   1212: aload_0
/*      */     //   1213: getfield trackRect : Ljava/awt/Rectangle;
/*      */     //   1216: iload #9
/*      */     //   1218: aload_0
/*      */     //   1219: getfield tickRect : Ljava/awt/Rectangle;
/*      */     //   1222: getfield width : I
/*      */     //   1225: iadd
/*      */     //   1226: aload_3
/*      */     //   1227: getfield left : I
/*      */     //   1230: iadd
/*      */     //   1231: putfield x : I
/*      */     //   1234: aload_1
/*      */     //   1235: invokevirtual dispose : ()V
/*      */     //   1238: aload_0
/*      */     //   1239: aload_0
/*      */     //   1240: getfield slider : Ljavax/swing/JSlider;
/*      */     //   1243: invokevirtual getSize : ()Ljava/awt/Dimension;
/*      */     //   1246: putfield lastSize : Ljava/awt/Dimension;
/*      */     //   1249: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #351	-> 0
/*      */     //   #352	-> 9
/*      */     //   #355	-> 18
/*      */     //   #356	-> 30
/*      */     //   #357	-> 43
/*      */     //   #358	-> 54
/*      */     //   #360	-> 59
/*      */     //   #363	-> 69
/*      */     //   #364	-> 77
/*      */     //   #365	-> 84
/*      */     //   #366	-> 90
/*      */     //   #369	-> 96
/*      */     //   #371	-> 107
/*      */     //   #372	-> 115
/*      */     //   #373	-> 125
/*      */     //   #376	-> 136
/*      */     //   #377	-> 144
/*      */     //   #378	-> 154
/*      */     //   #381	-> 165
/*      */     //   #384	-> 215
/*      */     //   #388	-> 245
/*      */     //   #389	-> 248
/*      */     //   #392	-> 258
/*      */     //   #393	-> 272
/*      */     //   #395	-> 286
/*      */     //   #396	-> 295
/*      */     //   #397	-> 300
/*      */     //   #398	-> 309
/*      */     //   #403	-> 318
/*      */     //   #404	-> 322
/*      */     //   #405	-> 326
/*      */     //   #406	-> 333
/*      */     //   #407	-> 343
/*      */     //   #408	-> 358
/*      */     //   #409	-> 372
/*      */     //   #411	-> 376
/*      */     //   #412	-> 390
/*      */     //   #414	-> 394
/*      */     //   #417	-> 397
/*      */     //   #418	-> 405
/*      */     //   #423	-> 418
/*      */     //   #425	-> 459
/*      */     //   #428	-> 502
/*      */     //   #430	-> 523
/*      */     //   #431	-> 532
/*      */     //   #433	-> 546
/*      */     //   #434	-> 560
/*      */     //   #436	-> 582
/*      */     //   #437	-> 591
/*      */     //   #439	-> 605
/*      */     //   #440	-> 614
/*      */     //   #441	-> 626
/*      */     //   #444	-> 629
/*      */     //   #446	-> 640
/*      */     //   #447	-> 648
/*      */     //   #448	-> 658
/*      */     //   #451	-> 669
/*      */     //   #452	-> 677
/*      */     //   #453	-> 687
/*      */     //   #456	-> 698
/*      */     //   #457	-> 712
/*      */     //   #458	-> 720
/*      */     //   #459	-> 727
/*      */     //   #460	-> 733
/*      */     //   #464	-> 739
/*      */     //   #465	-> 755
/*      */     //   #466	-> 765
/*      */     //   #467	-> 786
/*      */     //   #466	-> 795
/*      */     //   #468	-> 804
/*      */     //   #469	-> 825
/*      */     //   #468	-> 834
/*      */     //   #465	-> 837
/*      */     //   #471	-> 843
/*      */     //   #472	-> 854
/*      */     //   #473	-> 870
/*      */     //   #475	-> 902
/*      */     //   #477	-> 942
/*      */     //   #481	-> 972
/*      */     //   #483	-> 1010
/*      */     //   #486	-> 1048
/*      */     //   #487	-> 1069
/*      */     //   #488	-> 1079
/*      */     //   #489	-> 1086
/*      */     //   #491	-> 1096
/*      */     //   #493	-> 1110
/*      */     //   #494	-> 1132
/*      */     //   #495	-> 1141
/*      */     //   #497	-> 1163
/*      */     //   #498	-> 1170
/*      */     //   #500	-> 1180
/*      */     //   #502	-> 1189
/*      */     //   #503	-> 1203
/*      */     //   #504	-> 1212
/*      */     //   #507	-> 1234
/*      */     //   #508	-> 1238
/*      */     //   #509	-> 1249
/*      */   }
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
/*      */   private int getPadForLabel(int paramInt) {
/*  518 */     int i = 0;
/*      */     
/*  520 */     JComponent jComponent = (JComponent)this.slider.getLabelTable().get(Integer.valueOf(paramInt));
/*  521 */     if (jComponent != null) {
/*  522 */       int j = xPositionForValue(paramInt);
/*  523 */       int k = (jComponent.getPreferredSize()).width / 2;
/*  524 */       if (j - k < this.insetCache.left) {
/*  525 */         i = Math.max(i, this.insetCache.left - j - k);
/*      */       }
/*      */       
/*  528 */       if (j + k > this.slider.getWidth() - this.insetCache.right) {
/*  529 */         i = Math.max(i, j + k - this.slider
/*  530 */             .getWidth() - this.insetCache.right);
/*      */       }
/*      */     } 
/*  533 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculateThumbLocation() {
/*  541 */     super.calculateThumbLocation();
/*  542 */     if (this.slider.getOrientation() == 0) {
/*  543 */       this.thumbRect.y += this.trackBorder;
/*      */     } else {
/*  545 */       this.thumbRect.x += this.trackBorder;
/*      */     } 
/*  547 */     Point point = this.slider.getMousePosition();
/*  548 */     if (point != null) {
/*  549 */       updateThumbState(point.x, point.y);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThumbLocation(int paramInt1, int paramInt2) {
/*  558 */     super.setThumbLocation(paramInt1, paramInt2);
/*      */ 
/*      */     
/*  561 */     this.slider.repaint(this.valueRect.x, this.valueRect.y, this.valueRect.width, this.valueRect.height);
/*      */     
/*  563 */     setThumbActive(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int xPositionForValue(int paramInt) {
/*  571 */     int i = this.slider.getMinimum();
/*  572 */     int j = this.slider.getMaximum();
/*  573 */     int k = this.trackRect.x + this.thumbRect.width / 2 + this.trackBorder;
/*  574 */     int m = this.trackRect.x + this.trackRect.width - this.thumbRect.width / 2 - this.trackBorder;
/*      */     
/*  576 */     int n = m - k;
/*  577 */     double d1 = j - i;
/*  578 */     double d2 = n / d1;
/*      */ 
/*      */     
/*  581 */     if (!drawInverted()) {
/*  582 */       i1 = k;
/*  583 */       i1 = (int)(i1 + Math.round(d2 * (paramInt - i)));
/*      */     } else {
/*  585 */       i1 = m;
/*  586 */       i1 = (int)(i1 - Math.round(d2 * (paramInt - i)));
/*      */     } 
/*      */     
/*  589 */     int i1 = Math.max(k, i1);
/*  590 */     i1 = Math.min(m, i1);
/*      */     
/*  592 */     return i1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int yPositionForValue(int paramInt1, int paramInt2, int paramInt3) {
/*  600 */     int i = this.slider.getMinimum();
/*  601 */     int j = this.slider.getMaximum();
/*  602 */     int k = paramInt2 + this.thumbRect.height / 2 + this.trackBorder;
/*  603 */     int m = paramInt2 + paramInt3 - this.thumbRect.height / 2 - this.trackBorder;
/*      */     
/*  605 */     int n = m - k;
/*  606 */     double d1 = j - i;
/*  607 */     double d2 = n / d1;
/*      */ 
/*      */     
/*  610 */     if (!drawInverted()) {
/*  611 */       i1 = k;
/*  612 */       i1 = (int)(i1 + Math.round(d2 * (j - paramInt1)));
/*      */     } else {
/*  614 */       i1 = k;
/*  615 */       i1 = (int)(i1 + Math.round(d2 * (paramInt1 - i)));
/*      */     } 
/*      */     
/*  618 */     int i1 = Math.max(k, i1);
/*  619 */     i1 = Math.min(m, i1);
/*      */     
/*  621 */     return i1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int valueForYPosition(int paramInt) {
/*  630 */     int i, j = this.slider.getMinimum();
/*  631 */     int k = this.slider.getMaximum();
/*  632 */     int m = this.trackRect.y + this.thumbRect.height / 2 + this.trackBorder;
/*  633 */     int n = this.trackRect.y + this.trackRect.height - this.thumbRect.height / 2 - this.trackBorder;
/*      */     
/*  635 */     int i1 = n - m;
/*      */     
/*  637 */     if (paramInt <= m) {
/*  638 */       i = drawInverted() ? j : k;
/*  639 */     } else if (paramInt >= n) {
/*  640 */       i = drawInverted() ? k : j;
/*      */     } else {
/*  642 */       int i2 = paramInt - m;
/*  643 */       double d1 = k - j;
/*  644 */       double d2 = d1 / i1;
/*      */       
/*  646 */       int i3 = (int)Math.round(i2 * d2);
/*  647 */       i = drawInverted() ? (j + i3) : (k - i3);
/*      */     } 
/*      */     
/*  650 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int valueForXPosition(int paramInt) {
/*  659 */     int i, j = this.slider.getMinimum();
/*  660 */     int k = this.slider.getMaximum();
/*  661 */     int m = this.trackRect.x + this.thumbRect.width / 2 + this.trackBorder;
/*  662 */     int n = this.trackRect.x + this.trackRect.width - this.thumbRect.width / 2 - this.trackBorder;
/*      */     
/*  664 */     int i1 = n - m;
/*      */     
/*  666 */     if (paramInt <= m) {
/*  667 */       i = drawInverted() ? k : j;
/*  668 */     } else if (paramInt >= n) {
/*  669 */       i = drawInverted() ? j : k;
/*      */     } else {
/*  671 */       int i2 = paramInt - m;
/*  672 */       double d1 = k - j;
/*  673 */       double d2 = d1 / i1;
/*      */       
/*  675 */       int i3 = (int)Math.round(i2 * d2);
/*  676 */       i = drawInverted() ? (k - i3) : (j + i3);
/*      */     } 
/*      */     
/*  679 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dimension getThumbSize() {
/*  687 */     Dimension dimension = new Dimension();
/*      */     
/*  689 */     if (this.slider.getOrientation() == 1) {
/*  690 */       dimension.width = this.thumbHeight;
/*  691 */       dimension.height = this.thumbWidth;
/*      */     } else {
/*  693 */       dimension.width = this.thumbWidth;
/*  694 */       dimension.height = this.thumbHeight;
/*      */     } 
/*  696 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void recalculateIfInsetsChanged() {
/*  704 */     SynthContext synthContext = getContext(this.slider);
/*  705 */     Insets insets1 = this.style.getInsets(synthContext, null);
/*  706 */     Insets insets2 = this.slider.getInsets();
/*  707 */     insets1.left += insets2.left; insets1.right += insets2.right;
/*  708 */     insets1.top += insets2.top; insets1.bottom += insets2.bottom;
/*  709 */     if (!insets1.equals(this.insetCache)) {
/*  710 */       this.insetCache = insets1;
/*  711 */       calculateGeometry();
/*      */     } 
/*  713 */     synthContext.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SynthContext getContext(JComponent paramJComponent) {
/*  721 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*      */   }
/*      */   
/*      */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/*  725 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*      */   }
/*      */   
/*      */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion) {
/*  729 */     return getContext(paramJComponent, paramRegion, getComponentState(paramJComponent, paramRegion));
/*      */   }
/*      */   
/*      */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, int paramInt) {
/*  733 */     SynthStyle synthStyle = null;
/*      */     
/*  735 */     if (paramRegion == Region.SLIDER_TRACK) {
/*  736 */       synthStyle = this.sliderTrackStyle;
/*  737 */     } else if (paramRegion == Region.SLIDER_THUMB) {
/*  738 */       synthStyle = this.sliderThumbStyle;
/*      */     } 
/*  740 */     return SynthContext.getContext(paramJComponent, paramRegion, synthStyle, paramInt);
/*      */   }
/*      */   
/*      */   private int getComponentState(JComponent paramJComponent, Region paramRegion) {
/*  744 */     if (paramRegion == Region.SLIDER_THUMB && this.thumbActive && paramJComponent.isEnabled()) {
/*  745 */       int i = this.thumbPressed ? 4 : 2;
/*  746 */       if (paramJComponent.isFocusOwner()) i |= 0x100; 
/*  747 */       return i;
/*      */     } 
/*  749 */     return SynthLookAndFeel.getComponentState(paramJComponent);
/*      */   }
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
/*      */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/*  766 */     SynthContext synthContext = getContext(paramJComponent);
/*  767 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/*  768 */     synthContext.getPainter().paintSliderBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/*  769 */         .getWidth(), paramJComponent.getHeight(), this.slider
/*  770 */         .getOrientation());
/*  771 */     paint(synthContext, paramGraphics);
/*  772 */     synthContext.dispose();
/*      */   }
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
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  786 */     SynthContext synthContext = getContext(paramJComponent);
/*  787 */     paint(synthContext, paramGraphics);
/*  788 */     synthContext.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/*  799 */     recalculateIfInsetsChanged();
/*  800 */     recalculateIfOrientationChanged();
/*  801 */     Rectangle rectangle = paramGraphics.getClipBounds();
/*      */     
/*  803 */     if (this.lastSize == null || !this.lastSize.equals(this.slider.getSize())) {
/*  804 */       calculateGeometry();
/*      */     }
/*      */     
/*  807 */     if (this.paintValue) {
/*  808 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.slider, paramGraphics);
/*      */       
/*  810 */       int i = paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext).computeStringWidth(paramSynthContext, paramGraphics.getFont(), fontMetrics, "" + this.slider
/*  811 */           .getValue());
/*  812 */       this.thumbRect.x += (this.thumbRect.width - i) / 2;
/*      */ 
/*      */ 
/*      */       
/*  816 */       if (this.slider.getOrientation() == 0) {
/*  817 */         if (this.valueRect.x + i > this.insetCache.left + this.contentRect.width) {
/*  818 */           this.valueRect.x = this.insetCache.left + this.contentRect.width - i;
/*      */         }
/*  820 */         this.valueRect.x = Math.max(this.valueRect.x, 0);
/*      */       } 
/*      */       
/*  823 */       paramGraphics.setColor(paramSynthContext.getStyle().getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*      */       
/*  825 */       paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, "" + this.slider
/*  826 */           .getValue(), this.valueRect.x, this.valueRect.y, -1);
/*      */     } 
/*      */ 
/*      */     
/*  830 */     if (this.slider.getPaintTrack() && rectangle.intersects(this.trackRect)) {
/*  831 */       SynthContext synthContext = getContext(this.slider, Region.SLIDER_TRACK);
/*  832 */       paintTrack(synthContext, paramGraphics, this.trackRect);
/*  833 */       synthContext.dispose();
/*      */     } 
/*      */     
/*  836 */     if (rectangle.intersects(this.thumbRect)) {
/*  837 */       SynthContext synthContext = getContext(this.slider, Region.SLIDER_THUMB);
/*  838 */       paintThumb(synthContext, paramGraphics, this.thumbRect);
/*  839 */       synthContext.dispose();
/*      */     } 
/*      */     
/*  842 */     if (this.slider.getPaintTicks() && rectangle.intersects(this.tickRect)) {
/*  843 */       paintTicks(paramGraphics);
/*      */     }
/*      */     
/*  846 */     if (this.slider.getPaintLabels() && rectangle.intersects(this.labelRect)) {
/*  847 */       paintLabels(paramGraphics);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  857 */     paramSynthContext.getPainter().paintSliderBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, this.slider
/*  858 */         .getOrientation());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintThumb(SynthContext paramSynthContext, Graphics paramGraphics, Rectangle paramRectangle) {
/*  870 */     int i = this.slider.getOrientation();
/*  871 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, paramRectangle);
/*  872 */     paramSynthContext.getPainter().paintSliderThumbBackground(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, i);
/*      */ 
/*      */     
/*  875 */     paramSynthContext.getPainter().paintSliderThumbBorder(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, i);
/*      */   }
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
/*      */   protected void paintTrack(SynthContext paramSynthContext, Graphics paramGraphics, Rectangle paramRectangle) {
/*  889 */     int i = this.slider.getOrientation();
/*  890 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, paramRectangle);
/*  891 */     paramSynthContext.getPainter().paintSliderTrackBackground(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, i);
/*      */ 
/*      */     
/*  894 */     paramSynthContext.getPainter().paintSliderTrackBorder(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  904 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/*  905 */       updateStyle((JSlider)paramPropertyChangeEvent.getSource());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class SynthTrackListener
/*      */     extends BasicSliderUI.TrackListener
/*      */   {
/*      */     private SynthTrackListener() {}
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/*  918 */       SynthSliderUI.this.setThumbActive(false);
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  922 */       super.mousePressed(param1MouseEvent);
/*  923 */       SynthSliderUI.this.setThumbPressed(SynthSliderUI.this.thumbRect.contains(param1MouseEvent.getX(), param1MouseEvent.getY()));
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  927 */       super.mouseReleased(param1MouseEvent);
/*  928 */       SynthSliderUI.this.updateThumbState(param1MouseEvent.getX(), param1MouseEvent.getY(), false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/*      */       int i, j, k, m, n, i1, i2, i3, i4, i5, i6;
/*  934 */       if (!SynthSliderUI.this.slider.isEnabled()) {
/*      */         return;
/*      */       }
/*      */       
/*  938 */       this.currentMouseX = param1MouseEvent.getX();
/*  939 */       this.currentMouseY = param1MouseEvent.getY();
/*      */       
/*  941 */       if (!SynthSliderUI.this.isDragging()) {
/*      */         return;
/*      */       }
/*      */       
/*  945 */       SynthSliderUI.this.slider.setValueIsAdjusting(true);
/*      */       
/*  947 */       switch (SynthSliderUI.this.slider.getOrientation()) {
/*      */         case 1:
/*  949 */           j = SynthSliderUI.this.thumbRect.height / 2;
/*  950 */           k = param1MouseEvent.getY() - this.offset;
/*  951 */           m = SynthSliderUI.this.trackRect.y;
/*      */           
/*  953 */           n = SynthSliderUI.this.trackRect.y + SynthSliderUI.this.trackRect.height - j - SynthSliderUI.this.trackBorder;
/*  954 */           i1 = SynthSliderUI.this.yPositionForValue(SynthSliderUI.this.slider.getMaximum() - SynthSliderUI.this
/*  955 */               .slider.getExtent());
/*      */           
/*  957 */           if (SynthSliderUI.this.drawInverted()) {
/*  958 */             n = i1;
/*  959 */             m += j;
/*      */           } else {
/*  961 */             m = i1;
/*      */           } 
/*  963 */           k = Math.max(k, m - j);
/*  964 */           k = Math.min(k, n - j);
/*      */           
/*  966 */           SynthSliderUI.this.setThumbLocation(SynthSliderUI.this.thumbRect.x, k);
/*      */           
/*  968 */           i = k + j;
/*  969 */           SynthSliderUI.this.slider.setValue(SynthSliderUI.this.valueForYPosition(i));
/*      */           break;
/*      */         case 0:
/*  972 */           i2 = SynthSliderUI.this.thumbRect.width / 2;
/*  973 */           i3 = param1MouseEvent.getX() - this.offset;
/*  974 */           i4 = SynthSliderUI.this.trackRect.x + i2 + SynthSliderUI.this.trackBorder;
/*      */           
/*  976 */           i5 = SynthSliderUI.this.trackRect.x + SynthSliderUI.this.trackRect.width - i2 - SynthSliderUI.this.trackBorder;
/*  977 */           i6 = SynthSliderUI.this.xPositionForValue(SynthSliderUI.this.slider.getMaximum() - SynthSliderUI.this
/*  978 */               .slider.getExtent());
/*      */           
/*  980 */           if (SynthSliderUI.this.drawInverted()) {
/*  981 */             i4 = i6;
/*      */           } else {
/*  983 */             i5 = i6;
/*      */           } 
/*  985 */           i3 = Math.max(i3, i4 - i2);
/*  986 */           i3 = Math.min(i3, i5 - i2);
/*      */           
/*  988 */           SynthSliderUI.this.setThumbLocation(i3, SynthSliderUI.this.thumbRect.y);
/*      */           
/*  990 */           i = i3 + i2;
/*  991 */           SynthSliderUI.this.slider.setValue(SynthSliderUI.this.valueForXPosition(i));
/*      */           break;
/*      */         
/*      */         default:
/*      */           return;
/*      */       } 
/*  997 */       if (SynthSliderUI.this.slider.getValueIsAdjusting()) {
/*  998 */         SynthSliderUI.this.setThumbActive(true);
/*      */       }
/*      */     }
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 1003 */       SynthSliderUI.this.updateThumbState(param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthSliderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */