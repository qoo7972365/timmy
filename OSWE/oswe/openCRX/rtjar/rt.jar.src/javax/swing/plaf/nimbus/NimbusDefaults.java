/*      */ package javax.swing.plaf.nimbus;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JInternalFrame;
/*      */ import javax.swing.Painter;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.plaf.BorderUIResource;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.DimensionUIResource;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.synth.Region;
/*      */ import javax.swing.plaf.synth.SynthStyle;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.swing.plaf.synth.DefaultSynthStyle;
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
/*      */ final class NimbusDefaults
/*      */ {
/*      */   private Map<Region, List<LazyStyle>> m;
/*   91 */   private Map<String, Region> registeredRegions = new HashMap<>();
/*      */ 
/*      */   
/*   94 */   private Map<JComponent, Map<Region, SynthStyle>> overridesCache = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DefaultSynthStyle defaultStyle;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FontUIResource defaultFont;
/*      */ 
/*      */ 
/*      */   
/*  108 */   private ColorTree colorTree = new ColorTree();
/*      */ 
/*      */   
/*  111 */   private DefaultsListener defaultsListener = new DefaultsListener();
/*      */   
/*      */   private Map<DerivedColor, DerivedColor> derivedColors;
/*      */   
/*      */   void initialize() {
/*  116 */     UIManager.addPropertyChangeListener(this.defaultsListener);
/*  117 */     UIManager.getDefaults().addPropertyChangeListener(this.colorTree);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void uninitialize() {
/*  123 */     UIManager.removePropertyChangeListener(this.defaultsListener);
/*  124 */     UIManager.getDefaults().removePropertyChangeListener(this.colorTree);
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
/*      */   void initializeDefaults(UIDefaults paramUIDefaults) {
/*  246 */     addColor(paramUIDefaults, "text", 0, 0, 0, 255);
/*  247 */     addColor(paramUIDefaults, "control", 214, 217, 223, 255);
/*  248 */     addColor(paramUIDefaults, "nimbusBase", 51, 98, 140, 255);
/*  249 */     addColor(paramUIDefaults, "nimbusBlueGrey", "nimbusBase", 0.032459438F, -0.52518797F, 0.19607842F, 0);
/*  250 */     addColor(paramUIDefaults, "nimbusOrange", 191, 98, 4, 255);
/*  251 */     addColor(paramUIDefaults, "nimbusGreen", 176, 179, 50, 255);
/*  252 */     addColor(paramUIDefaults, "nimbusRed", 169, 46, 34, 255);
/*  253 */     addColor(paramUIDefaults, "nimbusBorder", "nimbusBlueGrey", 0.0F, -0.017358616F, -0.11372548F, 0);
/*  254 */     addColor(paramUIDefaults, "nimbusSelection", "nimbusBase", -0.010750473F, -0.04875779F, -0.007843137F, 0);
/*  255 */     addColor(paramUIDefaults, "nimbusInfoBlue", 47, 92, 180, 255);
/*  256 */     addColor(paramUIDefaults, "nimbusAlertYellow", 255, 220, 35, 255);
/*  257 */     addColor(paramUIDefaults, "nimbusFocus", 115, 164, 209, 255);
/*  258 */     addColor(paramUIDefaults, "nimbusSelectedText", 255, 255, 255, 255);
/*  259 */     addColor(paramUIDefaults, "nimbusSelectionBackground", 57, 105, 138, 255);
/*  260 */     addColor(paramUIDefaults, "nimbusDisabledText", 142, 143, 145, 255);
/*  261 */     addColor(paramUIDefaults, "nimbusLightBackground", 255, 255, 255, 255);
/*  262 */     addColor(paramUIDefaults, "infoText", "text", 0.0F, 0.0F, 0.0F, 0);
/*  263 */     addColor(paramUIDefaults, "info", 242, 242, 189, 255);
/*  264 */     addColor(paramUIDefaults, "menuText", "text", 0.0F, 0.0F, 0.0F, 0);
/*  265 */     addColor(paramUIDefaults, "menu", "nimbusBase", 0.021348298F, -0.6150531F, 0.39999998F, 0);
/*  266 */     addColor(paramUIDefaults, "scrollbar", "nimbusBlueGrey", -0.006944418F, -0.07296763F, 0.09019607F, 0);
/*  267 */     addColor(paramUIDefaults, "controlText", "text", 0.0F, 0.0F, 0.0F, 0);
/*  268 */     addColor(paramUIDefaults, "controlHighlight", "nimbusBlueGrey", 0.0F, -0.07333623F, 0.20392156F, 0);
/*  269 */     addColor(paramUIDefaults, "controlLHighlight", "nimbusBlueGrey", 0.0F, -0.098526314F, 0.2352941F, 0);
/*  270 */     addColor(paramUIDefaults, "controlShadow", "nimbusBlueGrey", -0.0027777553F, -0.0212406F, 0.13333333F, 0);
/*  271 */     addColor(paramUIDefaults, "controlDkShadow", "nimbusBlueGrey", -0.0027777553F, -0.0018306673F, -0.02352941F, 0);
/*  272 */     addColor(paramUIDefaults, "textHighlight", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*  273 */     addColor(paramUIDefaults, "textHighlightText", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  274 */     addColor(paramUIDefaults, "textInactiveText", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  275 */     addColor(paramUIDefaults, "desktop", "nimbusBase", -0.009207249F, -0.13984653F, -0.07450983F, 0);
/*  276 */     addColor(paramUIDefaults, "activeCaption", "nimbusBlueGrey", 0.0F, -0.049920253F, 0.031372547F, 0);
/*  277 */     addColor(paramUIDefaults, "inactiveCaption", "nimbusBlueGrey", -0.00505054F, -0.055526316F, 0.039215684F, 0);
/*      */ 
/*      */     
/*  280 */     paramUIDefaults.put("defaultFont", new FontUIResource(this.defaultFont));
/*  281 */     paramUIDefaults.put("InternalFrame.titleFont", new DerivedFont("defaultFont", 1.0F, Boolean.valueOf(true), null));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  286 */     addColor(paramUIDefaults, "textForeground", "text", 0.0F, 0.0F, 0.0F, 0);
/*  287 */     addColor(paramUIDefaults, "textBackground", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*  288 */     addColor(paramUIDefaults, "background", "control", 0.0F, 0.0F, 0.0F, 0);
/*  289 */     paramUIDefaults.put("TitledBorder.position", "ABOVE_TOP");
/*  290 */     paramUIDefaults.put("FileView.fullRowSelection", Boolean.TRUE);
/*      */ 
/*      */     
/*  293 */     paramUIDefaults.put("ArrowButton.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  294 */     paramUIDefaults.put("ArrowButton.size", new Integer(16));
/*  295 */     paramUIDefaults.put("ArrowButton[Disabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ArrowButtonPainter", 2, new Insets(0, 0, 0, 0), new Dimension(10, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  296 */     paramUIDefaults.put("ArrowButton[Enabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ArrowButtonPainter", 3, new Insets(0, 0, 0, 0), new Dimension(10, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  299 */     paramUIDefaults.put("Button.contentMargins", new InsetsUIResource(6, 14, 6, 14));
/*  300 */     paramUIDefaults.put("Button.defaultButtonFollowsFocus", Boolean.FALSE);
/*  301 */     paramUIDefaults.put("Button[Default].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 1, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  302 */     paramUIDefaults.put("Button[Default+Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 2, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  303 */     paramUIDefaults.put("Button[Default+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 3, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  304 */     paramUIDefaults.put("Button[Default+Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 4, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  305 */     addColor(paramUIDefaults, "Button[Default+Pressed].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  306 */     paramUIDefaults.put("Button[Default+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 5, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  307 */     paramUIDefaults.put("Button[Default+Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 6, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  308 */     addColor(paramUIDefaults, "Button[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  309 */     paramUIDefaults.put("Button[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 7, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  310 */     paramUIDefaults.put("Button[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 8, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  311 */     paramUIDefaults.put("Button[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 9, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  312 */     paramUIDefaults.put("Button[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 10, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  313 */     paramUIDefaults.put("Button[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 11, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  314 */     paramUIDefaults.put("Button[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 12, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  315 */     paramUIDefaults.put("Button[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ButtonPainter", 13, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*      */ 
/*      */     
/*  318 */     paramUIDefaults.put("ToggleButton.contentMargins", new InsetsUIResource(6, 14, 6, 14));
/*  319 */     addColor(paramUIDefaults, "ToggleButton[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  320 */     paramUIDefaults.put("ToggleButton[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 1, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  321 */     paramUIDefaults.put("ToggleButton[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 2, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  322 */     paramUIDefaults.put("ToggleButton[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 3, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  323 */     paramUIDefaults.put("ToggleButton[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 4, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  324 */     paramUIDefaults.put("ToggleButton[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 5, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  325 */     paramUIDefaults.put("ToggleButton[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 6, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  326 */     paramUIDefaults.put("ToggleButton[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 7, new Insets(7, 7, 7, 7), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  327 */     paramUIDefaults.put("ToggleButton[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 8, new Insets(7, 7, 7, 7), new Dimension(72, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  328 */     paramUIDefaults.put("ToggleButton[Focused+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 9, new Insets(7, 7, 7, 7), new Dimension(72, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  329 */     paramUIDefaults.put("ToggleButton[Pressed+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 10, new Insets(7, 7, 7, 7), new Dimension(72, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  330 */     paramUIDefaults.put("ToggleButton[Focused+Pressed+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 11, new Insets(7, 7, 7, 7), new Dimension(72, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  331 */     paramUIDefaults.put("ToggleButton[MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 12, new Insets(7, 7, 7, 7), new Dimension(72, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  332 */     paramUIDefaults.put("ToggleButton[Focused+MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 13, new Insets(7, 7, 7, 7), new Dimension(72, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  333 */     addColor(paramUIDefaults, "ToggleButton[Disabled+Selected].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  334 */     paramUIDefaults.put("ToggleButton[Disabled+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToggleButtonPainter", 14, new Insets(7, 7, 7, 7), new Dimension(72, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*      */ 
/*      */     
/*  337 */     paramUIDefaults.put("RadioButton.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  338 */     addColor(paramUIDefaults, "RadioButton[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  339 */     paramUIDefaults.put("RadioButton[Disabled].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 3, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  340 */     paramUIDefaults.put("RadioButton[Enabled].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 4, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  341 */     paramUIDefaults.put("RadioButton[Focused].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 5, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  342 */     paramUIDefaults.put("RadioButton[MouseOver].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 6, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  343 */     paramUIDefaults.put("RadioButton[Focused+MouseOver].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 7, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  344 */     paramUIDefaults.put("RadioButton[Pressed].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 8, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  345 */     paramUIDefaults.put("RadioButton[Focused+Pressed].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 9, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  346 */     paramUIDefaults.put("RadioButton[Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 10, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  347 */     paramUIDefaults.put("RadioButton[Focused+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 11, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  348 */     paramUIDefaults.put("RadioButton[Pressed+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 12, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  349 */     paramUIDefaults.put("RadioButton[Focused+Pressed+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 13, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  350 */     paramUIDefaults.put("RadioButton[MouseOver+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 14, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  351 */     paramUIDefaults.put("RadioButton[Focused+MouseOver+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 15, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  352 */     paramUIDefaults.put("RadioButton[Disabled+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonPainter", 16, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  353 */     paramUIDefaults.put("RadioButton.icon", new NimbusIcon("RadioButton", "iconPainter", 18, 18));
/*      */ 
/*      */     
/*  356 */     paramUIDefaults.put("CheckBox.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  357 */     addColor(paramUIDefaults, "CheckBox[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  358 */     paramUIDefaults.put("CheckBox[Disabled].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 3, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  359 */     paramUIDefaults.put("CheckBox[Enabled].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 4, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  360 */     paramUIDefaults.put("CheckBox[Focused].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 5, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  361 */     paramUIDefaults.put("CheckBox[MouseOver].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 6, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  362 */     paramUIDefaults.put("CheckBox[Focused+MouseOver].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 7, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  363 */     paramUIDefaults.put("CheckBox[Pressed].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 8, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  364 */     paramUIDefaults.put("CheckBox[Focused+Pressed].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 9, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  365 */     paramUIDefaults.put("CheckBox[Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 10, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  366 */     paramUIDefaults.put("CheckBox[Focused+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 11, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  367 */     paramUIDefaults.put("CheckBox[Pressed+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 12, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  368 */     paramUIDefaults.put("CheckBox[Focused+Pressed+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 13, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  369 */     paramUIDefaults.put("CheckBox[MouseOver+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 14, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  370 */     paramUIDefaults.put("CheckBox[Focused+MouseOver+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 15, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  371 */     paramUIDefaults.put("CheckBox[Disabled+Selected].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxPainter", 16, new Insets(5, 5, 5, 5), new Dimension(18, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  372 */     paramUIDefaults.put("CheckBox.icon", new NimbusIcon("CheckBox", "iconPainter", 18, 18));
/*      */ 
/*      */     
/*  375 */     paramUIDefaults.put("ColorChooser.contentMargins", new InsetsUIResource(5, 0, 0, 0));
/*  376 */     addColor(paramUIDefaults, "ColorChooser.swatchesDefaultRecentColor", 255, 255, 255, 255);
/*  377 */     paramUIDefaults.put("ColorChooser:\"ColorChooser.previewPanelHolder\".contentMargins", new InsetsUIResource(0, 5, 10, 5));
/*  378 */     paramUIDefaults.put("ColorChooser:\"ColorChooser.previewPanelHolder\":\"OptionPane.label\".contentMargins", new InsetsUIResource(0, 10, 10, 10));
/*      */ 
/*      */     
/*  381 */     paramUIDefaults.put("ComboBox.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  382 */     paramUIDefaults.put("ComboBox.States", "Enabled,MouseOver,Pressed,Selected,Disabled,Focused,Editable");
/*  383 */     paramUIDefaults.put("ComboBox.Editable", new ComboBoxEditableState());
/*  384 */     paramUIDefaults.put("ComboBox.forceOpaque", Boolean.TRUE);
/*  385 */     paramUIDefaults.put("ComboBox.buttonWhenNotEditable", Boolean.TRUE);
/*  386 */     paramUIDefaults.put("ComboBox.rendererUseListColors", Boolean.FALSE);
/*  387 */     paramUIDefaults.put("ComboBox.pressedWhenPopupVisible", Boolean.TRUE);
/*  388 */     paramUIDefaults.put("ComboBox.squareButton", Boolean.FALSE);
/*  389 */     paramUIDefaults.put("ComboBox.popupInsets", new InsetsUIResource(-2, 2, 0, 2));
/*  390 */     paramUIDefaults.put("ComboBox.padding", new InsetsUIResource(3, 3, 3, 3));
/*  391 */     paramUIDefaults.put("ComboBox[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 1, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  392 */     paramUIDefaults.put("ComboBox[Disabled+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 2, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  393 */     paramUIDefaults.put("ComboBox[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 3, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  394 */     paramUIDefaults.put("ComboBox[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 4, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  395 */     paramUIDefaults.put("ComboBox[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 5, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  396 */     paramUIDefaults.put("ComboBox[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 6, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  397 */     paramUIDefaults.put("ComboBox[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 7, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  398 */     paramUIDefaults.put("ComboBox[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 8, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  399 */     paramUIDefaults.put("ComboBox[Enabled+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 9, new Insets(8, 9, 8, 19), new Dimension(83, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  400 */     paramUIDefaults.put("ComboBox[Disabled+Editable].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 10, new Insets(6, 5, 6, 17), new Dimension(79, 21), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  401 */     paramUIDefaults.put("ComboBox[Editable+Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 11, new Insets(6, 5, 6, 17), new Dimension(79, 21), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  402 */     paramUIDefaults.put("ComboBox[Editable+Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 12, new Insets(5, 5, 5, 5), new Dimension(142, 27), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  403 */     paramUIDefaults.put("ComboBox[Editable+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 13, new Insets(4, 5, 5, 17), new Dimension(79, 21), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  404 */     paramUIDefaults.put("ComboBox[Editable+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxPainter", 14, new Insets(4, 5, 5, 17), new Dimension(79, 21), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  405 */     paramUIDefaults.put("ComboBox:\"ComboBox.textField\".contentMargins", new InsetsUIResource(0, 6, 0, 3));
/*  406 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.textField\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  407 */     paramUIDefaults.put("ComboBox:\"ComboBox.textField\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxTextFieldPainter", 1, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  408 */     paramUIDefaults.put("ComboBox:\"ComboBox.textField\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxTextFieldPainter", 2, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  409 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.textField\"[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  410 */     paramUIDefaults.put("ComboBox:\"ComboBox.textField\"[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxTextFieldPainter", 3, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  411 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  412 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\".States", "Enabled,MouseOver,Pressed,Disabled,Editable");
/*  413 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\".Editable", new ComboBoxArrowButtonEditableState());
/*  414 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\".size", new Integer(19));
/*  415 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Disabled+Editable].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 5, new Insets(8, 1, 8, 8), new Dimension(20, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  416 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Editable+Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 6, new Insets(8, 1, 8, 8), new Dimension(20, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  417 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Editable+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 7, new Insets(8, 1, 8, 8), new Dimension(20, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  418 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Editable+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 8, new Insets(8, 1, 8, 8), new Dimension(20, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  419 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Editable+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 9, new Insets(8, 1, 8, 8), new Dimension(20, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  420 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Enabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 10, new Insets(6, 9, 6, 10), new Dimension(24, 19), true, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  421 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[MouseOver].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 11, new Insets(6, 9, 6, 10), new Dimension(24, 19), true, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  422 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Disabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 12, new Insets(6, 9, 6, 10), new Dimension(24, 19), true, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  423 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Pressed].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 13, new Insets(6, 9, 6, 10), new Dimension(24, 19), true, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  424 */     paramUIDefaults.put("ComboBox:\"ComboBox.arrowButton\"[Selected].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ComboBoxArrowButtonPainter", 14, new Insets(6, 9, 6, 10), new Dimension(24, 19), true, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  425 */     paramUIDefaults.put("ComboBox:\"ComboBox.listRenderer\".contentMargins", new InsetsUIResource(2, 4, 2, 4));
/*  426 */     paramUIDefaults.put("ComboBox:\"ComboBox.listRenderer\".opaque", Boolean.TRUE);
/*  427 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.listRenderer\".background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  428 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.listRenderer\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  429 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.listRenderer\"[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  430 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.listRenderer\"[Selected].background", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*  431 */     paramUIDefaults.put("ComboBox:\"ComboBox.renderer\".contentMargins", new InsetsUIResource(2, 4, 2, 4));
/*  432 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.renderer\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  433 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.renderer\"[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  434 */     addColor(paramUIDefaults, "ComboBox:\"ComboBox.renderer\"[Selected].background", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*      */ 
/*      */     
/*  437 */     paramUIDefaults.put("\"ComboBox.scrollPane\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*      */ 
/*      */     
/*  440 */     paramUIDefaults.put("FileChooser.contentMargins", new InsetsUIResource(10, 10, 10, 10));
/*  441 */     paramUIDefaults.put("FileChooser.opaque", Boolean.TRUE);
/*  442 */     paramUIDefaults.put("FileChooser.usesSingleFilePane", Boolean.TRUE);
/*  443 */     paramUIDefaults.put("FileChooser[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 1, new Insets(0, 0, 0, 0), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  444 */     paramUIDefaults.put("FileChooser[Enabled].fileIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 2, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  445 */     paramUIDefaults.put("FileChooser.fileIcon", new NimbusIcon("FileChooser", "fileIconPainter", 16, 16));
/*  446 */     paramUIDefaults.put("FileChooser[Enabled].directoryIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 3, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  447 */     paramUIDefaults.put("FileChooser.directoryIcon", new NimbusIcon("FileChooser", "directoryIconPainter", 16, 16));
/*  448 */     paramUIDefaults.put("FileChooser[Enabled].upFolderIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 4, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  449 */     paramUIDefaults.put("FileChooser.upFolderIcon", new NimbusIcon("FileChooser", "upFolderIconPainter", 16, 16));
/*  450 */     paramUIDefaults.put("FileChooser[Enabled].newFolderIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 5, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  451 */     paramUIDefaults.put("FileChooser.newFolderIcon", new NimbusIcon("FileChooser", "newFolderIconPainter", 16, 16));
/*  452 */     paramUIDefaults.put("FileChooser[Enabled].hardDriveIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 7, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  453 */     paramUIDefaults.put("FileChooser.hardDriveIcon", new NimbusIcon("FileChooser", "hardDriveIconPainter", 16, 16));
/*  454 */     paramUIDefaults.put("FileChooser[Enabled].floppyDriveIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 8, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  455 */     paramUIDefaults.put("FileChooser.floppyDriveIcon", new NimbusIcon("FileChooser", "floppyDriveIconPainter", 16, 16));
/*  456 */     paramUIDefaults.put("FileChooser[Enabled].homeFolderIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 9, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  457 */     paramUIDefaults.put("FileChooser.homeFolderIcon", new NimbusIcon("FileChooser", "homeFolderIconPainter", 16, 16));
/*  458 */     paramUIDefaults.put("FileChooser[Enabled].detailsViewIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 10, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  459 */     paramUIDefaults.put("FileChooser.detailsViewIcon", new NimbusIcon("FileChooser", "detailsViewIconPainter", 16, 16));
/*  460 */     paramUIDefaults.put("FileChooser[Enabled].listViewIconPainter", new LazyPainter("javax.swing.plaf.nimbus.FileChooserPainter", 11, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  461 */     paramUIDefaults.put("FileChooser.listViewIcon", new NimbusIcon("FileChooser", "listViewIconPainter", 16, 16));
/*      */ 
/*      */     
/*  464 */     paramUIDefaults.put("InternalFrameTitlePane.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  465 */     paramUIDefaults.put("InternalFrameTitlePane.maxFrameIconSize", new DimensionUIResource(18, 18));
/*      */ 
/*      */     
/*  468 */     paramUIDefaults.put("InternalFrame.contentMargins", new InsetsUIResource(1, 6, 6, 6));
/*  469 */     paramUIDefaults.put("InternalFrame.States", "Enabled,WindowFocused");
/*  470 */     paramUIDefaults.put("InternalFrame.WindowFocused", new InternalFrameWindowFocusedState());
/*  471 */     paramUIDefaults.put("InternalFrame[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFramePainter", 1, new Insets(25, 6, 6, 6), new Dimension(25, 36), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  472 */     paramUIDefaults.put("InternalFrame[Enabled+WindowFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFramePainter", 2, new Insets(25, 6, 6, 6), new Dimension(25, 36), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  473 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane.contentMargins", new InsetsUIResource(3, 0, 3, 0));
/*  474 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane.States", "Enabled,WindowFocused");
/*  475 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane.WindowFocused", new InternalFrameTitlePaneWindowFocusedState());
/*  476 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane.titleAlignment", "CENTER");
/*  477 */     addColor(paramUIDefaults, "InternalFrame:InternalFrameTitlePane[Enabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  478 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  479 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\".States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,WindowNotFocused");
/*  480 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\".WindowNotFocused", new InternalFrameTitlePaneMenuButtonWindowNotFocusedState());
/*  481 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\".test", "am InternalFrameTitlePane.menuButton");
/*  482 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"[Enabled].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMenuButtonPainter", 1, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  483 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"[Disabled].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMenuButtonPainter", 2, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  484 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"[MouseOver].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMenuButtonPainter", 3, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  485 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"[Pressed].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMenuButtonPainter", 4, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  486 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"[Enabled+WindowNotFocused].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMenuButtonPainter", 5, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  487 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"[MouseOver+WindowNotFocused].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMenuButtonPainter", 6, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  488 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"[Pressed+WindowNotFocused].iconPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMenuButtonPainter", 7, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  489 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\".icon", new NimbusIcon("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\"", "iconPainter", 19, 18));
/*  490 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\".contentMargins", new InsetsUIResource(9, 9, 9, 9));
/*  491 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\".States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,WindowNotFocused");
/*  492 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\".WindowNotFocused", new InternalFrameTitlePaneIconifyButtonWindowNotFocusedState());
/*  493 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneIconifyButtonPainter", 1, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  494 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneIconifyButtonPainter", 2, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  495 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneIconifyButtonPainter", 3, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  496 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneIconifyButtonPainter", 4, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  497 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Enabled+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneIconifyButtonPainter", 5, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  498 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[MouseOver+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneIconifyButtonPainter", 6, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  499 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Pressed+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneIconifyButtonPainter", 7, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  500 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\".contentMargins", new InsetsUIResource(9, 9, 9, 9));
/*  501 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\".States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,WindowNotFocused,WindowMaximized");
/*  502 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\".WindowNotFocused", new InternalFrameTitlePaneMaximizeButtonWindowNotFocusedState());
/*  503 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\".WindowMaximized", new InternalFrameTitlePaneMaximizeButtonWindowMaximizedState());
/*  504 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Disabled+WindowMaximized].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 1, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  505 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Enabled+WindowMaximized].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 2, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  506 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver+WindowMaximized].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 3, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  507 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Pressed+WindowMaximized].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 4, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  508 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Enabled+WindowMaximized+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 5, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  509 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver+WindowMaximized+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 6, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  510 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Pressed+WindowMaximized+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 7, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  511 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 8, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  512 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 9, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  513 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 10, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  514 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 11, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  515 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Enabled+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 12, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  516 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 13, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  517 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Pressed+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneMaximizeButtonPainter", 14, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  518 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\".contentMargins", new InsetsUIResource(9, 9, 9, 9));
/*  519 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\".States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,WindowNotFocused");
/*  520 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\".WindowNotFocused", new InternalFrameTitlePaneCloseButtonWindowNotFocusedState());
/*  521 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneCloseButtonPainter", 1, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  522 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneCloseButtonPainter", 2, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  523 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneCloseButtonPainter", 3, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  524 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneCloseButtonPainter", 4, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  525 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Enabled+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneCloseButtonPainter", 5, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  526 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[MouseOver+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneCloseButtonPainter", 6, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  527 */     paramUIDefaults.put("InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Pressed+WindowNotFocused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.InternalFrameTitlePaneCloseButtonPainter", 7, new Insets(0, 0, 0, 0), new Dimension(19, 18), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  530 */     paramUIDefaults.put("DesktopIcon.contentMargins", new InsetsUIResource(4, 6, 5, 4));
/*  531 */     paramUIDefaults.put("DesktopIcon[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.DesktopIconPainter", 1, new Insets(5, 5, 5, 5), new Dimension(28, 26), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  534 */     paramUIDefaults.put("DesktopPane.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  535 */     paramUIDefaults.put("DesktopPane.opaque", Boolean.TRUE);
/*  536 */     paramUIDefaults.put("DesktopPane[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.DesktopPanePainter", 1, new Insets(0, 0, 0, 0), new Dimension(300, 232), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  539 */     paramUIDefaults.put("Label.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  540 */     addColor(paramUIDefaults, "Label[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*      */ 
/*      */     
/*  543 */     paramUIDefaults.put("List.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  544 */     paramUIDefaults.put("List.opaque", Boolean.TRUE);
/*  545 */     addColor(paramUIDefaults, "List.background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  546 */     paramUIDefaults.put("List.rendererUseListColors", Boolean.FALSE);
/*  547 */     paramUIDefaults.put("List.rendererUseUIBorder", Boolean.TRUE);
/*  548 */     paramUIDefaults.put("List.cellNoFocusBorder", new BorderUIResource(BorderFactory.createEmptyBorder(2, 5, 2, 5)));
/*  549 */     paramUIDefaults.put("List.focusCellHighlightBorder", new BorderUIResource(new PainterBorder("Tree:TreeCell[Enabled+Focused].backgroundPainter", new Insets(2, 5, 2, 5))));
/*  550 */     addColor(paramUIDefaults, "List.dropLineColor", "nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  551 */     addColor(paramUIDefaults, "List[Selected].textForeground", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  552 */     addColor(paramUIDefaults, "List[Selected].textBackground", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*  553 */     addColor(paramUIDefaults, "List[Disabled+Selected].textBackground", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*  554 */     addColor(paramUIDefaults, "List[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  555 */     paramUIDefaults.put("List:\"List.cellRenderer\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  556 */     paramUIDefaults.put("List:\"List.cellRenderer\".opaque", Boolean.TRUE);
/*  557 */     addColor(paramUIDefaults, "List:\"List.cellRenderer\"[Selected].textForeground", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  558 */     addColor(paramUIDefaults, "List:\"List.cellRenderer\"[Selected].background", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*  559 */     addColor(paramUIDefaults, "List:\"List.cellRenderer\"[Disabled+Selected].background", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0);
/*  560 */     addColor(paramUIDefaults, "List:\"List.cellRenderer\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*      */ 
/*      */     
/*  563 */     paramUIDefaults.put("MenuBar.contentMargins", new InsetsUIResource(2, 6, 2, 6));
/*  564 */     paramUIDefaults.put("MenuBar[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuBarPainter", 1, new Insets(1, 0, 0, 0), new Dimension(18, 22), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  565 */     paramUIDefaults.put("MenuBar[Enabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuBarPainter", 2, new Insets(0, 0, 1, 0), new Dimension(30, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  566 */     paramUIDefaults.put("MenuBar:Menu.contentMargins", new InsetsUIResource(1, 4, 2, 4));
/*  567 */     addColor(paramUIDefaults, "MenuBar:Menu[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  568 */     addColor(paramUIDefaults, "MenuBar:Menu[Enabled].textForeground", 35, 35, 36, 255);
/*  569 */     addColor(paramUIDefaults, "MenuBar:Menu[Selected].textForeground", 255, 255, 255, 255);
/*  570 */     paramUIDefaults.put("MenuBar:Menu[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuBarMenuPainter", 3, new Insets(0, 0, 0, 0), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  571 */     paramUIDefaults.put("MenuBar:Menu:MenuItemAccelerator.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*      */ 
/*      */     
/*  574 */     paramUIDefaults.put("MenuItem.contentMargins", new InsetsUIResource(1, 12, 2, 13));
/*  575 */     paramUIDefaults.put("MenuItem.textIconGap", new Integer(5));
/*  576 */     addColor(paramUIDefaults, "MenuItem[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  577 */     addColor(paramUIDefaults, "MenuItem[Enabled].textForeground", 35, 35, 36, 255);
/*  578 */     addColor(paramUIDefaults, "MenuItem[MouseOver].textForeground", 255, 255, 255, 255);
/*  579 */     paramUIDefaults.put("MenuItem[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuItemPainter", 3, new Insets(0, 0, 0, 0), new Dimension(100, 3), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  580 */     paramUIDefaults.put("MenuItem:MenuItemAccelerator.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  581 */     addColor(paramUIDefaults, "MenuItem:MenuItemAccelerator[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  582 */     addColor(paramUIDefaults, "MenuItem:MenuItemAccelerator[MouseOver].textForeground", 255, 255, 255, 255);
/*      */ 
/*      */     
/*  585 */     paramUIDefaults.put("RadioButtonMenuItem.contentMargins", new InsetsUIResource(1, 12, 2, 13));
/*  586 */     paramUIDefaults.put("RadioButtonMenuItem.textIconGap", new Integer(5));
/*  587 */     addColor(paramUIDefaults, "RadioButtonMenuItem[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  588 */     addColor(paramUIDefaults, "RadioButtonMenuItem[Enabled].textForeground", 35, 35, 36, 255);
/*  589 */     addColor(paramUIDefaults, "RadioButtonMenuItem[MouseOver].textForeground", 255, 255, 255, 255);
/*  590 */     paramUIDefaults.put("RadioButtonMenuItem[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonMenuItemPainter", 3, new Insets(0, 0, 0, 0), new Dimension(100, 3), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  591 */     addColor(paramUIDefaults, "RadioButtonMenuItem[MouseOver+Selected].textForeground", 255, 255, 255, 255);
/*  592 */     paramUIDefaults.put("RadioButtonMenuItem[MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonMenuItemPainter", 4, new Insets(0, 0, 0, 0), new Dimension(100, 3), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  593 */     paramUIDefaults.put("RadioButtonMenuItem[Disabled+Selected].checkIconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonMenuItemPainter", 5, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  594 */     paramUIDefaults.put("RadioButtonMenuItem[Enabled+Selected].checkIconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonMenuItemPainter", 6, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  595 */     paramUIDefaults.put("RadioButtonMenuItem[MouseOver+Selected].checkIconPainter", new LazyPainter("javax.swing.plaf.nimbus.RadioButtonMenuItemPainter", 7, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  596 */     paramUIDefaults.put("RadioButtonMenuItem.checkIcon", new NimbusIcon("RadioButtonMenuItem", "checkIconPainter", 9, 10));
/*  597 */     paramUIDefaults.put("RadioButtonMenuItem:MenuItemAccelerator.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  598 */     addColor(paramUIDefaults, "RadioButtonMenuItem:MenuItemAccelerator[MouseOver].textForeground", 255, 255, 255, 255);
/*      */ 
/*      */     
/*  601 */     paramUIDefaults.put("CheckBoxMenuItem.contentMargins", new InsetsUIResource(1, 12, 2, 13));
/*  602 */     paramUIDefaults.put("CheckBoxMenuItem.textIconGap", new Integer(5));
/*  603 */     addColor(paramUIDefaults, "CheckBoxMenuItem[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  604 */     addColor(paramUIDefaults, "CheckBoxMenuItem[Enabled].textForeground", 35, 35, 36, 255);
/*  605 */     addColor(paramUIDefaults, "CheckBoxMenuItem[MouseOver].textForeground", 255, 255, 255, 255);
/*  606 */     paramUIDefaults.put("CheckBoxMenuItem[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxMenuItemPainter", 3, new Insets(0, 0, 0, 0), new Dimension(100, 3), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  607 */     addColor(paramUIDefaults, "CheckBoxMenuItem[MouseOver+Selected].textForeground", 255, 255, 255, 255);
/*  608 */     paramUIDefaults.put("CheckBoxMenuItem[MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxMenuItemPainter", 4, new Insets(0, 0, 0, 0), new Dimension(100, 3), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  609 */     paramUIDefaults.put("CheckBoxMenuItem[Disabled+Selected].checkIconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxMenuItemPainter", 5, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  610 */     paramUIDefaults.put("CheckBoxMenuItem[Enabled+Selected].checkIconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxMenuItemPainter", 6, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  611 */     paramUIDefaults.put("CheckBoxMenuItem[MouseOver+Selected].checkIconPainter", new LazyPainter("javax.swing.plaf.nimbus.CheckBoxMenuItemPainter", 7, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  612 */     paramUIDefaults.put("CheckBoxMenuItem.checkIcon", new NimbusIcon("CheckBoxMenuItem", "checkIconPainter", 9, 10));
/*  613 */     paramUIDefaults.put("CheckBoxMenuItem:MenuItemAccelerator.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  614 */     addColor(paramUIDefaults, "CheckBoxMenuItem:MenuItemAccelerator[MouseOver].textForeground", 255, 255, 255, 255);
/*      */ 
/*      */     
/*  617 */     paramUIDefaults.put("Menu.contentMargins", new InsetsUIResource(1, 12, 2, 5));
/*  618 */     paramUIDefaults.put("Menu.textIconGap", new Integer(5));
/*  619 */     addColor(paramUIDefaults, "Menu[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  620 */     addColor(paramUIDefaults, "Menu[Enabled].textForeground", 35, 35, 36, 255);
/*  621 */     addColor(paramUIDefaults, "Menu[Enabled+Selected].textForeground", 255, 255, 255, 255);
/*  622 */     paramUIDefaults.put("Menu[Enabled+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuPainter", 3, new Insets(0, 0, 0, 0), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  623 */     paramUIDefaults.put("Menu[Disabled].arrowIconPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuPainter", 4, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  624 */     paramUIDefaults.put("Menu[Enabled].arrowIconPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuPainter", 5, new Insets(5, 5, 5, 5), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  625 */     paramUIDefaults.put("Menu[Enabled+Selected].arrowIconPainter", new LazyPainter("javax.swing.plaf.nimbus.MenuPainter", 6, new Insets(1, 1, 1, 1), new Dimension(9, 10), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  626 */     paramUIDefaults.put("Menu.arrowIcon", new NimbusIcon("Menu", "arrowIconPainter", 9, 10));
/*  627 */     paramUIDefaults.put("Menu:MenuItemAccelerator.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  628 */     addColor(paramUIDefaults, "Menu:MenuItemAccelerator[MouseOver].textForeground", 255, 255, 255, 255);
/*      */ 
/*      */     
/*  631 */     paramUIDefaults.put("PopupMenu.contentMargins", new InsetsUIResource(6, 1, 6, 1));
/*  632 */     paramUIDefaults.put("PopupMenu.opaque", Boolean.TRUE);
/*  633 */     paramUIDefaults.put("PopupMenu.consumeEventOnClose", Boolean.TRUE);
/*  634 */     paramUIDefaults.put("PopupMenu[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.PopupMenuPainter", 1, new Insets(9, 0, 11, 0), new Dimension(220, 313), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  635 */     paramUIDefaults.put("PopupMenu[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.PopupMenuPainter", 2, new Insets(11, 2, 11, 2), new Dimension(220, 313), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  638 */     paramUIDefaults.put("PopupMenuSeparator.contentMargins", new InsetsUIResource(1, 0, 2, 0));
/*  639 */     paramUIDefaults.put("PopupMenuSeparator[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.PopupMenuSeparatorPainter", 1, new Insets(1, 1, 1, 1), new Dimension(3, 3), true, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  642 */     paramUIDefaults.put("OptionPane.contentMargins", new InsetsUIResource(15, 15, 15, 15));
/*  643 */     paramUIDefaults.put("OptionPane.opaque", Boolean.TRUE);
/*  644 */     paramUIDefaults.put("OptionPane.buttonOrientation", new Integer(4));
/*  645 */     paramUIDefaults.put("OptionPane.messageAnchor", new Integer(17));
/*  646 */     paramUIDefaults.put("OptionPane.separatorPadding", new Integer(0));
/*  647 */     paramUIDefaults.put("OptionPane.sameSizeButtons", Boolean.FALSE);
/*  648 */     paramUIDefaults.put("OptionPane:\"OptionPane.separator\".contentMargins", new InsetsUIResource(1, 0, 0, 0));
/*  649 */     paramUIDefaults.put("OptionPane:\"OptionPane.messageArea\".contentMargins", new InsetsUIResource(0, 0, 10, 0));
/*  650 */     paramUIDefaults.put("OptionPane:\"OptionPane.messageArea\":\"OptionPane.label\".contentMargins", new InsetsUIResource(0, 10, 10, 10));
/*  651 */     paramUIDefaults.put("OptionPane:\"OptionPane.messageArea\":\"OptionPane.label\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.OptionPaneMessageAreaOptionPaneLabelPainter", 1, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  652 */     paramUIDefaults.put("OptionPane[Enabled].errorIconPainter", new LazyPainter("javax.swing.plaf.nimbus.OptionPanePainter", 2, new Insets(0, 0, 0, 0), new Dimension(48, 48), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  653 */     paramUIDefaults.put("OptionPane.errorIcon", new NimbusIcon("OptionPane", "errorIconPainter", 48, 48));
/*  654 */     paramUIDefaults.put("OptionPane[Enabled].informationIconPainter", new LazyPainter("javax.swing.plaf.nimbus.OptionPanePainter", 3, new Insets(0, 0, 0, 0), new Dimension(48, 48), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  655 */     paramUIDefaults.put("OptionPane.informationIcon", new NimbusIcon("OptionPane", "informationIconPainter", 48, 48));
/*  656 */     paramUIDefaults.put("OptionPane[Enabled].questionIconPainter", new LazyPainter("javax.swing.plaf.nimbus.OptionPanePainter", 4, new Insets(0, 0, 0, 0), new Dimension(48, 48), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  657 */     paramUIDefaults.put("OptionPane.questionIcon", new NimbusIcon("OptionPane", "questionIconPainter", 48, 48));
/*  658 */     paramUIDefaults.put("OptionPane[Enabled].warningIconPainter", new LazyPainter("javax.swing.plaf.nimbus.OptionPanePainter", 5, new Insets(0, 0, 0, 0), new Dimension(48, 48), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  659 */     paramUIDefaults.put("OptionPane.warningIcon", new NimbusIcon("OptionPane", "warningIconPainter", 48, 48));
/*      */ 
/*      */     
/*  662 */     paramUIDefaults.put("Panel.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  663 */     paramUIDefaults.put("Panel.opaque", Boolean.TRUE);
/*      */ 
/*      */     
/*  666 */     paramUIDefaults.put("ProgressBar.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  667 */     paramUIDefaults.put("ProgressBar.States", "Enabled,Disabled,Indeterminate,Finished");
/*  668 */     paramUIDefaults.put("ProgressBar.Indeterminate", new ProgressBarIndeterminateState());
/*  669 */     paramUIDefaults.put("ProgressBar.Finished", new ProgressBarFinishedState());
/*  670 */     paramUIDefaults.put("ProgressBar.tileWhenIndeterminate", Boolean.TRUE);
/*  671 */     paramUIDefaults.put("ProgressBar.tileWidth", new Integer(27));
/*  672 */     paramUIDefaults.put("ProgressBar.paintOutsideClip", Boolean.TRUE);
/*  673 */     paramUIDefaults.put("ProgressBar.rotateText", Boolean.TRUE);
/*  674 */     paramUIDefaults.put("ProgressBar.vertictalSize", new DimensionUIResource(19, 150));
/*  675 */     paramUIDefaults.put("ProgressBar.horizontalSize", new DimensionUIResource(150, 19));
/*  676 */     paramUIDefaults.put("ProgressBar.cycleTime", new Integer(250));
/*  677 */     paramUIDefaults.put("ProgressBar.minBarSize", new DimensionUIResource(6, 6));
/*  678 */     paramUIDefaults.put("ProgressBar.glowWidth", new Integer(2));
/*  679 */     paramUIDefaults.put("ProgressBar[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 1, new Insets(5, 5, 5, 5), new Dimension(29, 19), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  680 */     addColor(paramUIDefaults, "ProgressBar[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  681 */     paramUIDefaults.put("ProgressBar[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 2, new Insets(5, 5, 5, 5), new Dimension(29, 19), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  682 */     paramUIDefaults.put("ProgressBar[Enabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 3, new Insets(3, 3, 3, 3), new Dimension(27, 19), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  683 */     paramUIDefaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 4, new Insets(3, 3, 3, 3), new Dimension(27, 19), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  684 */     paramUIDefaults.put("ProgressBar[Enabled+Indeterminate].progressPadding", new Integer(3));
/*  685 */     paramUIDefaults.put("ProgressBar[Enabled+Indeterminate].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 5, new Insets(3, 3, 3, 3), new Dimension(30, 13), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  686 */     paramUIDefaults.put("ProgressBar[Disabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 6, new Insets(3, 3, 3, 3), new Dimension(27, 19), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  687 */     paramUIDefaults.put("ProgressBar[Disabled+Finished].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 7, new Insets(3, 3, 3, 3), new Dimension(27, 19), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  688 */     paramUIDefaults.put("ProgressBar[Disabled+Indeterminate].progressPadding", new Integer(3));
/*  689 */     paramUIDefaults.put("ProgressBar[Disabled+Indeterminate].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ProgressBarPainter", 8, new Insets(3, 3, 3, 3), new Dimension(30, 13), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*      */ 
/*      */     
/*  692 */     paramUIDefaults.put("Separator.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  693 */     paramUIDefaults.put("Separator[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SeparatorPainter", 1, new Insets(0, 40, 0, 40), new Dimension(100, 3), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  696 */     paramUIDefaults.put("ScrollBar.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  697 */     paramUIDefaults.put("ScrollBar.opaque", Boolean.TRUE);
/*  698 */     paramUIDefaults.put("ScrollBar.incrementButtonGap", new Integer(-8));
/*  699 */     paramUIDefaults.put("ScrollBar.decrementButtonGap", new Integer(-8));
/*  700 */     paramUIDefaults.put("ScrollBar.thumbHeight", new Integer(15));
/*  701 */     paramUIDefaults.put("ScrollBar.minimumThumbSize", new DimensionUIResource(29, 29));
/*  702 */     paramUIDefaults.put("ScrollBar.maximumThumbSize", new DimensionUIResource(1000, 1000));
/*  703 */     paramUIDefaults.put("ScrollBar:\"ScrollBar.button\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  704 */     paramUIDefaults.put("ScrollBar:\"ScrollBar.button\".size", new Integer(25));
/*  705 */     paramUIDefaults.put("ScrollBar:\"ScrollBar.button\"[Enabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarButtonPainter", 1, new Insets(1, 1, 1, 1), new Dimension(25, 15), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  706 */     paramUIDefaults.put("ScrollBar:\"ScrollBar.button\"[Disabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarButtonPainter", 2, new Insets(1, 1, 1, 1), new Dimension(25, 15), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  707 */     paramUIDefaults.put("ScrollBar:\"ScrollBar.button\"[MouseOver].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarButtonPainter", 3, new Insets(1, 1, 1, 1), new Dimension(25, 15), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  708 */     paramUIDefaults.put("ScrollBar:\"ScrollBar.button\"[Pressed].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarButtonPainter", 4, new Insets(1, 1, 1, 1), new Dimension(25, 15), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  709 */     paramUIDefaults.put("ScrollBar:ScrollBarThumb.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  710 */     paramUIDefaults.put("ScrollBar:ScrollBarThumb[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarThumbPainter", 2, new Insets(0, 15, 0, 15), new Dimension(38, 15), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  711 */     paramUIDefaults.put("ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarThumbPainter", 4, new Insets(0, 15, 0, 15), new Dimension(38, 15), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  712 */     paramUIDefaults.put("ScrollBar:ScrollBarThumb[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarThumbPainter", 5, new Insets(0, 15, 0, 15), new Dimension(38, 15), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  713 */     paramUIDefaults.put("ScrollBar:ScrollBarTrack.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  714 */     paramUIDefaults.put("ScrollBar:ScrollBarTrack[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarTrackPainter", 1, new Insets(5, 5, 5, 5), new Dimension(18, 15), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  715 */     paramUIDefaults.put("ScrollBar:ScrollBarTrack[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollBarTrackPainter", 2, new Insets(5, 10, 5, 9), new Dimension(34, 15), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  718 */     paramUIDefaults.put("ScrollPane.contentMargins", new InsetsUIResource(3, 3, 3, 3));
/*  719 */     paramUIDefaults.put("ScrollPane.useChildTextComponentFocus", Boolean.TRUE);
/*  720 */     paramUIDefaults.put("ScrollPane[Enabled+Focused].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollPanePainter", 2, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  721 */     paramUIDefaults.put("ScrollPane[Enabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.ScrollPanePainter", 3, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  724 */     paramUIDefaults.put("Viewport.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  725 */     paramUIDefaults.put("Viewport.opaque", Boolean.TRUE);
/*      */ 
/*      */     
/*  728 */     paramUIDefaults.put("Slider.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  729 */     paramUIDefaults.put("Slider.States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,ArrowShape");
/*  730 */     paramUIDefaults.put("Slider.ArrowShape", new SliderArrowShapeState());
/*  731 */     paramUIDefaults.put("Slider.thumbWidth", new Integer(17));
/*  732 */     paramUIDefaults.put("Slider.thumbHeight", new Integer(17));
/*  733 */     paramUIDefaults.put("Slider.trackBorder", new Integer(0));
/*  734 */     paramUIDefaults.put("Slider.paintValue", Boolean.FALSE);
/*  735 */     addColor(paramUIDefaults, "Slider.tickColor", 35, 40, 48, 255);
/*  736 */     paramUIDefaults.put("Slider:SliderThumb.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  737 */     paramUIDefaults.put("Slider:SliderThumb.States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,ArrowShape");
/*  738 */     paramUIDefaults.put("Slider:SliderThumb.ArrowShape", new SliderThumbArrowShapeState());
/*  739 */     paramUIDefaults.put("Slider:SliderThumb[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 1, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  740 */     paramUIDefaults.put("Slider:SliderThumb[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 2, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  741 */     paramUIDefaults.put("Slider:SliderThumb[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 3, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  742 */     paramUIDefaults.put("Slider:SliderThumb[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 4, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  743 */     paramUIDefaults.put("Slider:SliderThumb[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 5, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  744 */     paramUIDefaults.put("Slider:SliderThumb[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 6, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  745 */     paramUIDefaults.put("Slider:SliderThumb[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 7, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  746 */     paramUIDefaults.put("Slider:SliderThumb[ArrowShape+Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 8, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  747 */     paramUIDefaults.put("Slider:SliderThumb[ArrowShape+Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 9, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  748 */     paramUIDefaults.put("Slider:SliderThumb[ArrowShape+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 10, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  749 */     paramUIDefaults.put("Slider:SliderThumb[ArrowShape+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 11, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  750 */     paramUIDefaults.put("Slider:SliderThumb[ArrowShape+Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 12, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  751 */     paramUIDefaults.put("Slider:SliderThumb[ArrowShape+Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 13, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  752 */     paramUIDefaults.put("Slider:SliderThumb[ArrowShape+Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderThumbPainter", 14, new Insets(5, 5, 5, 5), new Dimension(17, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  753 */     paramUIDefaults.put("Slider:SliderTrack.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  754 */     paramUIDefaults.put("Slider:SliderTrack.States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,ArrowShape");
/*  755 */     paramUIDefaults.put("Slider:SliderTrack.ArrowShape", new SliderTrackArrowShapeState());
/*  756 */     paramUIDefaults.put("Slider:SliderTrack[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderTrackPainter", 1, new Insets(6, 5, 6, 5), new Dimension(23, 17), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, 2.0D));
/*  757 */     paramUIDefaults.put("Slider:SliderTrack[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SliderTrackPainter", 2, new Insets(6, 5, 6, 5), new Dimension(23, 17), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  760 */     paramUIDefaults.put("Spinner.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  761 */     paramUIDefaults.put("Spinner:\"Spinner.editor\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  762 */     paramUIDefaults.put("Spinner:Panel:\"Spinner.formattedTextField\".contentMargins", new InsetsUIResource(6, 6, 5, 6));
/*  763 */     addColor(paramUIDefaults, "Spinner:Panel:\"Spinner.formattedTextField\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  764 */     paramUIDefaults.put("Spinner:Panel:\"Spinner.formattedTextField\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPanelSpinnerFormattedTextFieldPainter", 1, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  765 */     paramUIDefaults.put("Spinner:Panel:\"Spinner.formattedTextField\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPanelSpinnerFormattedTextFieldPainter", 2, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  766 */     paramUIDefaults.put("Spinner:Panel:\"Spinner.formattedTextField\"[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPanelSpinnerFormattedTextFieldPainter", 3, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  767 */     addColor(paramUIDefaults, "Spinner:Panel:\"Spinner.formattedTextField\"[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  768 */     paramUIDefaults.put("Spinner:Panel:\"Spinner.formattedTextField\"[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPanelSpinnerFormattedTextFieldPainter", 4, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  769 */     addColor(paramUIDefaults, "Spinner:Panel:\"Spinner.formattedTextField\"[Focused+Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  770 */     paramUIDefaults.put("Spinner:Panel:\"Spinner.formattedTextField\"[Focused+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPanelSpinnerFormattedTextFieldPainter", 5, new Insets(5, 3, 3, 1), new Dimension(64, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  771 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  772 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\".size", new Integer(20));
/*  773 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 1, new Insets(0, 1, 6, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  774 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 2, new Insets(0, 1, 6, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  775 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 3, new Insets(0, 1, 6, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  776 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 4, new Insets(3, 1, 6, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  777 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 5, new Insets(0, 1, 6, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  778 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 6, new Insets(0, 1, 6, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  779 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 7, new Insets(0, 1, 6, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  780 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Disabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 8, new Insets(3, 6, 5, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  781 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Enabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 9, new Insets(3, 6, 5, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  782 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Focused].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 10, new Insets(3, 6, 5, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  783 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Focused+MouseOver].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 11, new Insets(3, 6, 5, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  784 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Focused+Pressed].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 12, new Insets(3, 6, 5, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  785 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[MouseOver].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 13, new Insets(3, 6, 5, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  786 */     paramUIDefaults.put("Spinner:\"Spinner.previousButton\"[Pressed].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerPreviousButtonPainter", 14, new Insets(3, 6, 5, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  787 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  788 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\".size", new Integer(20));
/*  789 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 1, new Insets(7, 1, 1, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  790 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 2, new Insets(7, 1, 1, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  791 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 3, new Insets(7, 1, 1, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  792 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 4, new Insets(7, 1, 1, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  793 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 5, new Insets(7, 1, 1, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  794 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 6, new Insets(7, 1, 1, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  795 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 7, new Insets(7, 1, 1, 7), new Dimension(20, 12), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  796 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Disabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 8, new Insets(5, 6, 3, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  797 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Enabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 9, new Insets(5, 6, 3, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  798 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Focused].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 10, new Insets(3, 6, 3, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  799 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Focused+MouseOver].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 11, new Insets(3, 6, 3, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  800 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Focused+Pressed].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 12, new Insets(5, 6, 3, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  801 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[MouseOver].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 13, new Insets(5, 6, 3, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  802 */     paramUIDefaults.put("Spinner:\"Spinner.nextButton\"[Pressed].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SpinnerNextButtonPainter", 14, new Insets(5, 6, 3, 9), new Dimension(20, 12), true, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  805 */     paramUIDefaults.put("SplitPane.contentMargins", new InsetsUIResource(1, 1, 1, 1));
/*  806 */     paramUIDefaults.put("SplitPane.States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,Vertical");
/*  807 */     paramUIDefaults.put("SplitPane.Vertical", new SplitPaneVerticalState());
/*  808 */     paramUIDefaults.put("SplitPane.size", new Integer(10));
/*  809 */     paramUIDefaults.put("SplitPane.dividerSize", new Integer(10));
/*  810 */     paramUIDefaults.put("SplitPane.centerOneTouchButtons", Boolean.TRUE);
/*  811 */     paramUIDefaults.put("SplitPane.oneTouchButtonOffset", new Integer(30));
/*  812 */     paramUIDefaults.put("SplitPane.oneTouchExpandable", Boolean.FALSE);
/*  813 */     paramUIDefaults.put("SplitPane.continuousLayout", Boolean.TRUE);
/*  814 */     paramUIDefaults.put("SplitPane:SplitPaneDivider.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  815 */     paramUIDefaults.put("SplitPane:SplitPaneDivider.States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,Vertical");
/*  816 */     paramUIDefaults.put("SplitPane:SplitPaneDivider.Vertical", new SplitPaneDividerVerticalState());
/*  817 */     paramUIDefaults.put("SplitPane:SplitPaneDivider[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SplitPaneDividerPainter", 1, new Insets(3, 0, 3, 0), new Dimension(68, 10), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  818 */     paramUIDefaults.put("SplitPane:SplitPaneDivider[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SplitPaneDividerPainter", 2, new Insets(3, 0, 3, 0), new Dimension(68, 10), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  819 */     paramUIDefaults.put("SplitPane:SplitPaneDivider[Enabled].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SplitPaneDividerPainter", 3, new Insets(0, 24, 0, 24), new Dimension(68, 10), true, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  820 */     paramUIDefaults.put("SplitPane:SplitPaneDivider[Enabled+Vertical].foregroundPainter", new LazyPainter("javax.swing.plaf.nimbus.SplitPaneDividerPainter", 4, new Insets(5, 0, 5, 0), new Dimension(10, 38), true, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  823 */     paramUIDefaults.put("TabbedPane.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  824 */     paramUIDefaults.put("TabbedPane.tabAreaStatesMatchSelectedTab", Boolean.TRUE);
/*  825 */     paramUIDefaults.put("TabbedPane.nudgeSelectedLabel", Boolean.FALSE);
/*  826 */     paramUIDefaults.put("TabbedPane.tabRunOverlay", new Integer(2));
/*  827 */     paramUIDefaults.put("TabbedPane.tabOverlap", new Integer(-1));
/*  828 */     paramUIDefaults.put("TabbedPane.extendTabsToBase", Boolean.TRUE);
/*  829 */     paramUIDefaults.put("TabbedPane.useBasicArrows", Boolean.TRUE);
/*  830 */     addColor(paramUIDefaults, "TabbedPane.shadow", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  831 */     addColor(paramUIDefaults, "TabbedPane.darkShadow", "text", 0.0F, 0.0F, 0.0F, 0);
/*  832 */     addColor(paramUIDefaults, "TabbedPane.highlight", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  833 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab.contentMargins", new InsetsUIResource(2, 8, 3, 8));
/*  834 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 1, new Insets(7, 7, 1, 7), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  835 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Enabled+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 2, new Insets(7, 7, 1, 7), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  836 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Enabled+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 3, new Insets(7, 6, 1, 7), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  837 */     addColor(paramUIDefaults, "TabbedPane:TabbedPaneTab[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  838 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 4, new Insets(6, 7, 1, 7), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  839 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Disabled+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 5, new Insets(7, 7, 0, 7), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  840 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 6, new Insets(7, 7, 0, 7), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  841 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 7, new Insets(7, 9, 0, 9), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  842 */     addColor(paramUIDefaults, "TabbedPane:TabbedPaneTab[Pressed+Selected].textForeground", 255, 255, 255, 255);
/*  843 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Pressed+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 8, new Insets(7, 9, 0, 9), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  844 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Focused+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 9, new Insets(7, 7, 3, 7), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  845 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Focused+MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 10, new Insets(7, 9, 3, 9), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  846 */     addColor(paramUIDefaults, "TabbedPane:TabbedPaneTab[Focused+Pressed+Selected].textForeground", 255, 255, 255, 255);
/*  847 */     paramUIDefaults.put("TabbedPane:TabbedPaneTab[Focused+Pressed+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabPainter", 11, new Insets(7, 9, 3, 9), new Dimension(44, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  848 */     paramUIDefaults.put("TabbedPane:TabbedPaneTabArea.contentMargins", new InsetsUIResource(3, 10, 4, 10));
/*  849 */     paramUIDefaults.put("TabbedPane:TabbedPaneTabArea[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabAreaPainter", 1, new Insets(0, 5, 6, 5), new Dimension(5, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  850 */     paramUIDefaults.put("TabbedPane:TabbedPaneTabArea[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabAreaPainter", 2, new Insets(0, 5, 6, 5), new Dimension(5, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  851 */     paramUIDefaults.put("TabbedPane:TabbedPaneTabArea[Enabled+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabAreaPainter", 3, new Insets(0, 5, 6, 5), new Dimension(5, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  852 */     paramUIDefaults.put("TabbedPane:TabbedPaneTabArea[Enabled+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TabbedPaneTabAreaPainter", 4, new Insets(0, 5, 6, 5), new Dimension(5, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  853 */     paramUIDefaults.put("TabbedPane:TabbedPaneContent.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*      */ 
/*      */     
/*  856 */     paramUIDefaults.put("Table.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  857 */     paramUIDefaults.put("Table.opaque", Boolean.TRUE);
/*  858 */     addColor(paramUIDefaults, "Table.textForeground", 35, 35, 36, 255);
/*  859 */     addColor(paramUIDefaults, "Table.background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  860 */     paramUIDefaults.put("Table.showGrid", Boolean.FALSE);
/*  861 */     paramUIDefaults.put("Table.intercellSpacing", new DimensionUIResource(0, 0));
/*  862 */     addColor(paramUIDefaults, "Table.alternateRowColor", "nimbusLightBackground", 0.0F, 0.0F, -0.05098039F, 0, false);
/*  863 */     paramUIDefaults.put("Table.rendererUseTableColors", Boolean.TRUE);
/*  864 */     paramUIDefaults.put("Table.rendererUseUIBorder", Boolean.TRUE);
/*  865 */     paramUIDefaults.put("Table.cellNoFocusBorder", new BorderUIResource(BorderFactory.createEmptyBorder(2, 5, 2, 5)));
/*  866 */     paramUIDefaults.put("Table.focusCellHighlightBorder", new BorderUIResource(new PainterBorder("Tree:TreeCell[Enabled+Focused].backgroundPainter", new Insets(2, 5, 2, 5))));
/*  867 */     addColor(paramUIDefaults, "Table.dropLineColor", "nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*  868 */     addColor(paramUIDefaults, "Table.dropLineShortColor", "nimbusOrange", 0.0F, 0.0F, 0.0F, 0);
/*  869 */     addColor(paramUIDefaults, "Table[Enabled+Selected].textForeground", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0, false);
/*  870 */     addColor(paramUIDefaults, "Table[Enabled+Selected].textBackground", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0, false);
/*  871 */     addColor(paramUIDefaults, "Table[Disabled+Selected].textBackground", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0, false);
/*  872 */     paramUIDefaults.put("Table:\"Table.cellRenderer\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  873 */     paramUIDefaults.put("Table:\"Table.cellRenderer\".opaque", Boolean.TRUE);
/*  874 */     addColor(paramUIDefaults, "Table:\"Table.cellRenderer\".background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0, false);
/*      */ 
/*      */     
/*  877 */     paramUIDefaults.put("TableHeader.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/*  878 */     paramUIDefaults.put("TableHeader.opaque", Boolean.TRUE);
/*  879 */     paramUIDefaults.put("TableHeader.rightAlignSortArrow", Boolean.TRUE);
/*  880 */     paramUIDefaults.put("TableHeader[Enabled].ascendingSortIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderPainter", 1, new Insets(0, 0, 0, 2), new Dimension(7, 7), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  881 */     paramUIDefaults.put("Table.ascendingSortIcon", new NimbusIcon("TableHeader", "ascendingSortIconPainter", 7, 7));
/*  882 */     paramUIDefaults.put("TableHeader[Enabled].descendingSortIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderPainter", 2, new Insets(0, 0, 0, 0), new Dimension(7, 7), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/*  883 */     paramUIDefaults.put("Table.descendingSortIcon", new NimbusIcon("TableHeader", "descendingSortIconPainter", 7, 7));
/*  884 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\".contentMargins", new InsetsUIResource(2, 5, 4, 5));
/*  885 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\".opaque", Boolean.TRUE);
/*  886 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\".States", "Enabled,MouseOver,Pressed,Disabled,Focused,Selected,Sorted");
/*  887 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\".Sorted", new TableHeaderRendererSortedState());
/*  888 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 1, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  889 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 2, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  890 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[Enabled+Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 3, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  891 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 4, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  892 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 5, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  893 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[Enabled+Sorted].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 6, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  894 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[Enabled+Focused+Sorted].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 7, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  895 */     paramUIDefaults.put("TableHeader:\"TableHeader.renderer\"[Disabled+Sorted].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableHeaderRendererPainter", 8, new Insets(5, 5, 5, 5), new Dimension(22, 20), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  898 */     paramUIDefaults.put("\"Table.editor\".contentMargins", new InsetsUIResource(3, 5, 3, 5));
/*  899 */     paramUIDefaults.put("\"Table.editor\".opaque", Boolean.TRUE);
/*  900 */     addColor(paramUIDefaults, "\"Table.editor\".background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  901 */     addColor(paramUIDefaults, "\"Table.editor\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  902 */     paramUIDefaults.put("\"Table.editor\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableEditorPainter", 2, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  903 */     paramUIDefaults.put("\"Table.editor\"[Enabled+Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TableEditorPainter", 3, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  904 */     addColor(paramUIDefaults, "\"Table.editor\"[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*      */ 
/*      */     
/*  907 */     paramUIDefaults.put("\"Tree.cellEditor\".contentMargins", new InsetsUIResource(2, 5, 2, 5));
/*  908 */     paramUIDefaults.put("\"Tree.cellEditor\".opaque", Boolean.TRUE);
/*  909 */     addColor(paramUIDefaults, "\"Tree.cellEditor\".background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  910 */     addColor(paramUIDefaults, "\"Tree.cellEditor\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  911 */     paramUIDefaults.put("\"Tree.cellEditor\"[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TreeCellEditorPainter", 2, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  912 */     paramUIDefaults.put("\"Tree.cellEditor\"[Enabled+Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TreeCellEditorPainter", 3, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  913 */     addColor(paramUIDefaults, "\"Tree.cellEditor\"[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*      */ 
/*      */     
/*  916 */     paramUIDefaults.put("TextField.contentMargins", new InsetsUIResource(6, 6, 6, 6));
/*  917 */     addColor(paramUIDefaults, "TextField.background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  918 */     addColor(paramUIDefaults, "TextField[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  919 */     paramUIDefaults.put("TextField[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextFieldPainter", 1, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  920 */     paramUIDefaults.put("TextField[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextFieldPainter", 2, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  921 */     addColor(paramUIDefaults, "TextField[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  922 */     paramUIDefaults.put("TextField[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextFieldPainter", 3, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  923 */     addColor(paramUIDefaults, "TextField[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  924 */     paramUIDefaults.put("TextField[Disabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.TextFieldPainter", 4, new Insets(5, 3, 3, 3), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  925 */     paramUIDefaults.put("TextField[Focused].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.TextFieldPainter", 5, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  926 */     paramUIDefaults.put("TextField[Enabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.TextFieldPainter", 6, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  929 */     paramUIDefaults.put("FormattedTextField.contentMargins", new InsetsUIResource(6, 6, 6, 6));
/*  930 */     addColor(paramUIDefaults, "FormattedTextField[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  931 */     paramUIDefaults.put("FormattedTextField[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.FormattedTextFieldPainter", 1, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  932 */     paramUIDefaults.put("FormattedTextField[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.FormattedTextFieldPainter", 2, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  933 */     addColor(paramUIDefaults, "FormattedTextField[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  934 */     paramUIDefaults.put("FormattedTextField[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.FormattedTextFieldPainter", 3, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  935 */     addColor(paramUIDefaults, "FormattedTextField[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  936 */     paramUIDefaults.put("FormattedTextField[Disabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.FormattedTextFieldPainter", 4, new Insets(5, 3, 3, 3), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  937 */     paramUIDefaults.put("FormattedTextField[Focused].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.FormattedTextFieldPainter", 5, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  938 */     paramUIDefaults.put("FormattedTextField[Enabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.FormattedTextFieldPainter", 6, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  941 */     paramUIDefaults.put("PasswordField.contentMargins", new InsetsUIResource(6, 6, 6, 6));
/*  942 */     addColor(paramUIDefaults, "PasswordField[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  943 */     paramUIDefaults.put("PasswordField[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.PasswordFieldPainter", 1, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  944 */     paramUIDefaults.put("PasswordField[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.PasswordFieldPainter", 2, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  945 */     addColor(paramUIDefaults, "PasswordField[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  946 */     paramUIDefaults.put("PasswordField[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.PasswordFieldPainter", 3, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  947 */     addColor(paramUIDefaults, "PasswordField[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  948 */     paramUIDefaults.put("PasswordField[Disabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.PasswordFieldPainter", 4, new Insets(5, 3, 3, 3), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  949 */     paramUIDefaults.put("PasswordField[Focused].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.PasswordFieldPainter", 5, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  950 */     paramUIDefaults.put("PasswordField[Enabled].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.PasswordFieldPainter", 6, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  953 */     paramUIDefaults.put("TextArea.contentMargins", new InsetsUIResource(6, 6, 6, 6));
/*  954 */     paramUIDefaults.put("TextArea.States", "Enabled,MouseOver,Pressed,Selected,Disabled,Focused,NotInScrollPane");
/*  955 */     paramUIDefaults.put("TextArea.NotInScrollPane", new TextAreaNotInScrollPaneState());
/*  956 */     addColor(paramUIDefaults, "TextArea[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  957 */     paramUIDefaults.put("TextArea[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 1, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  958 */     paramUIDefaults.put("TextArea[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 2, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  959 */     addColor(paramUIDefaults, "TextArea[Disabled+NotInScrollPane].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  960 */     paramUIDefaults.put("TextArea[Disabled+NotInScrollPane].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 3, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  961 */     paramUIDefaults.put("TextArea[Enabled+NotInScrollPane].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 4, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  962 */     addColor(paramUIDefaults, "TextArea[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  963 */     paramUIDefaults.put("TextArea[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 5, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  964 */     addColor(paramUIDefaults, "TextArea[Disabled+NotInScrollPane].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  965 */     paramUIDefaults.put("TextArea[Disabled+NotInScrollPane].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 6, new Insets(5, 3, 3, 3), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  966 */     paramUIDefaults.put("TextArea[Focused+NotInScrollPane].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 7, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*  967 */     paramUIDefaults.put("TextArea[Enabled+NotInScrollPane].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.TextAreaPainter", 8, new Insets(5, 5, 5, 5), new Dimension(122, 24), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/*  970 */     paramUIDefaults.put("TextPane.contentMargins", new InsetsUIResource(4, 6, 4, 6));
/*  971 */     paramUIDefaults.put("TextPane.opaque", Boolean.TRUE);
/*  972 */     addColor(paramUIDefaults, "TextPane[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  973 */     paramUIDefaults.put("TextPane[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextPanePainter", 1, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  974 */     paramUIDefaults.put("TextPane[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextPanePainter", 2, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  975 */     addColor(paramUIDefaults, "TextPane[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  976 */     paramUIDefaults.put("TextPane[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TextPanePainter", 3, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  979 */     paramUIDefaults.put("EditorPane.contentMargins", new InsetsUIResource(4, 6, 4, 6));
/*  980 */     paramUIDefaults.put("EditorPane.opaque", Boolean.TRUE);
/*  981 */     addColor(paramUIDefaults, "EditorPane[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/*  982 */     paramUIDefaults.put("EditorPane[Disabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.EditorPanePainter", 1, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  983 */     paramUIDefaults.put("EditorPane[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.EditorPanePainter", 2, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  984 */     addColor(paramUIDefaults, "EditorPane[Selected].textForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0);
/*  985 */     paramUIDefaults.put("EditorPane[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.EditorPanePainter", 3, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*      */ 
/*      */     
/*  988 */     paramUIDefaults.put("ToolBar.contentMargins", new InsetsUIResource(2, 2, 2, 2));
/*  989 */     paramUIDefaults.put("ToolBar.opaque", Boolean.TRUE);
/*  990 */     paramUIDefaults.put("ToolBar.States", "North,East,West,South");
/*  991 */     paramUIDefaults.put("ToolBar.North", new ToolBarNorthState());
/*  992 */     paramUIDefaults.put("ToolBar.East", new ToolBarEastState());
/*  993 */     paramUIDefaults.put("ToolBar.West", new ToolBarWestState());
/*  994 */     paramUIDefaults.put("ToolBar.South", new ToolBarSouthState());
/*  995 */     paramUIDefaults.put("ToolBar[North].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarPainter", 1, new Insets(0, 0, 1, 0), new Dimension(30, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  996 */     paramUIDefaults.put("ToolBar[South].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarPainter", 2, new Insets(1, 0, 0, 0), new Dimension(30, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  997 */     paramUIDefaults.put("ToolBar[East].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarPainter", 3, new Insets(1, 0, 0, 0), new Dimension(30, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  998 */     paramUIDefaults.put("ToolBar[West].borderPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarPainter", 4, new Insets(0, 0, 1, 0), new Dimension(30, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*  999 */     paramUIDefaults.put("ToolBar[Enabled].handleIconPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarPainter", 5, new Insets(5, 5, 5, 5), new Dimension(11, 38), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1000 */     paramUIDefaults.put("ToolBar.handleIcon", new NimbusIcon("ToolBar", "handleIconPainter", 11, 38));
/* 1001 */     paramUIDefaults.put("ToolBar:Button.contentMargins", new InsetsUIResource(4, 4, 4, 4));
/* 1002 */     paramUIDefaults.put("ToolBar:Button[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarButtonPainter", 2, new Insets(5, 5, 5, 5), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1003 */     paramUIDefaults.put("ToolBar:Button[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarButtonPainter", 3, new Insets(5, 5, 5, 5), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1004 */     paramUIDefaults.put("ToolBar:Button[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarButtonPainter", 4, new Insets(5, 5, 5, 5), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1005 */     paramUIDefaults.put("ToolBar:Button[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarButtonPainter", 5, new Insets(5, 5, 5, 5), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1006 */     paramUIDefaults.put("ToolBar:Button[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarButtonPainter", 6, new Insets(5, 5, 5, 5), new Dimension(104, 33), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1007 */     paramUIDefaults.put("ToolBar:ToggleButton.contentMargins", new InsetsUIResource(4, 4, 4, 4));
/* 1008 */     paramUIDefaults.put("ToolBar:ToggleButton[Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 2, new Insets(5, 5, 5, 5), new Dimension(104, 34), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1009 */     paramUIDefaults.put("ToolBar:ToggleButton[MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 3, new Insets(5, 5, 5, 5), new Dimension(104, 34), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1010 */     paramUIDefaults.put("ToolBar:ToggleButton[Focused+MouseOver].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 4, new Insets(5, 5, 5, 5), new Dimension(104, 34), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1011 */     paramUIDefaults.put("ToolBar:ToggleButton[Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 5, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1012 */     paramUIDefaults.put("ToolBar:ToggleButton[Focused+Pressed].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 6, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1013 */     paramUIDefaults.put("ToolBar:ToggleButton[Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 7, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1014 */     paramUIDefaults.put("ToolBar:ToggleButton[Focused+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 8, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1015 */     paramUIDefaults.put("ToolBar:ToggleButton[Pressed+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 9, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1016 */     paramUIDefaults.put("ToolBar:ToggleButton[Focused+Pressed+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 10, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1017 */     paramUIDefaults.put("ToolBar:ToggleButton[MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 11, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1018 */     paramUIDefaults.put("ToolBar:ToggleButton[Focused+MouseOver+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 12, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/* 1019 */     addColor(paramUIDefaults, "ToolBar:ToggleButton[Disabled+Selected].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/* 1020 */     paramUIDefaults.put("ToolBar:ToggleButton[Disabled+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolBarToggleButtonPainter", 13, new Insets(5, 5, 5, 5), new Dimension(72, 25), false, AbstractRegionPainter.PaintContext.CacheMode.NINE_SQUARE_SCALE, 2.0D, Double.POSITIVE_INFINITY));
/*      */ 
/*      */     
/* 1023 */     paramUIDefaults.put("ToolBarSeparator.contentMargins", new InsetsUIResource(2, 0, 3, 0));
/* 1024 */     addColor(paramUIDefaults, "ToolBarSeparator.textForeground", "nimbusBorder", 0.0F, 0.0F, 0.0F, 0);
/*      */ 
/*      */     
/* 1027 */     paramUIDefaults.put("ToolTip.contentMargins", new InsetsUIResource(4, 4, 4, 4));
/* 1028 */     paramUIDefaults.put("ToolTip[Enabled].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.ToolTipPainter", 1, new Insets(1, 1, 1, 1), new Dimension(10, 10), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/*      */ 
/*      */     
/* 1031 */     paramUIDefaults.put("Tree.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/* 1032 */     paramUIDefaults.put("Tree.opaque", Boolean.TRUE);
/* 1033 */     addColor(paramUIDefaults, "Tree.textForeground", "text", 0.0F, 0.0F, 0.0F, 0, false);
/* 1034 */     addColor(paramUIDefaults, "Tree.textBackground", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0, false);
/* 1035 */     addColor(paramUIDefaults, "Tree.background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/* 1036 */     paramUIDefaults.put("Tree.rendererFillBackground", Boolean.FALSE);
/* 1037 */     paramUIDefaults.put("Tree.leftChildIndent", new Integer(12));
/* 1038 */     paramUIDefaults.put("Tree.rightChildIndent", new Integer(4));
/* 1039 */     paramUIDefaults.put("Tree.drawHorizontalLines", Boolean.FALSE);
/* 1040 */     paramUIDefaults.put("Tree.drawVerticalLines", Boolean.FALSE);
/* 1041 */     paramUIDefaults.put("Tree.showRootHandles", Boolean.FALSE);
/* 1042 */     paramUIDefaults.put("Tree.rendererUseTreeColors", Boolean.TRUE);
/* 1043 */     paramUIDefaults.put("Tree.repaintWholeRow", Boolean.TRUE);
/* 1044 */     paramUIDefaults.put("Tree.rowHeight", new Integer(0));
/* 1045 */     paramUIDefaults.put("Tree.rendererMargins", new InsetsUIResource(2, 0, 1, 5));
/* 1046 */     addColor(paramUIDefaults, "Tree.selectionForeground", "nimbusSelectedText", 0.0F, 0.0F, 0.0F, 0, false);
/* 1047 */     addColor(paramUIDefaults, "Tree.selectionBackground", "nimbusSelectionBackground", 0.0F, 0.0F, 0.0F, 0, false);
/* 1048 */     addColor(paramUIDefaults, "Tree.dropLineColor", "nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/* 1049 */     paramUIDefaults.put("Tree:TreeCell.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/* 1050 */     addColor(paramUIDefaults, "Tree:TreeCell[Enabled].background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/* 1051 */     addColor(paramUIDefaults, "Tree:TreeCell[Enabled+Focused].background", "nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/* 1052 */     paramUIDefaults.put("Tree:TreeCell[Enabled+Focused].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TreeCellPainter", 2, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/* 1053 */     addColor(paramUIDefaults, "Tree:TreeCell[Enabled+Selected].textForeground", 255, 255, 255, 255);
/* 1054 */     paramUIDefaults.put("Tree:TreeCell[Enabled+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TreeCellPainter", 3, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/* 1055 */     addColor(paramUIDefaults, "Tree:TreeCell[Focused+Selected].textForeground", 255, 255, 255, 255);
/* 1056 */     paramUIDefaults.put("Tree:TreeCell[Focused+Selected].backgroundPainter", new LazyPainter("javax.swing.plaf.nimbus.TreeCellPainter", 4, new Insets(5, 5, 5, 5), new Dimension(100, 30), false, AbstractRegionPainter.PaintContext.CacheMode.NO_CACHING, 1.0D, 1.0D));
/* 1057 */     paramUIDefaults.put("Tree:\"Tree.cellRenderer\".contentMargins", new InsetsUIResource(0, 0, 0, 0));
/* 1058 */     addColor(paramUIDefaults, "Tree:\"Tree.cellRenderer\"[Disabled].textForeground", "nimbusDisabledText", 0.0F, 0.0F, 0.0F, 0);
/* 1059 */     paramUIDefaults.put("Tree[Enabled].leafIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TreePainter", 4, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/* 1060 */     paramUIDefaults.put("Tree.leafIcon", new NimbusIcon("Tree", "leafIconPainter", 16, 16));
/* 1061 */     paramUIDefaults.put("Tree[Enabled].closedIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TreePainter", 5, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/* 1062 */     paramUIDefaults.put("Tree.closedIcon", new NimbusIcon("Tree", "closedIconPainter", 16, 16));
/* 1063 */     paramUIDefaults.put("Tree[Enabled].openIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TreePainter", 6, new Insets(5, 5, 5, 5), new Dimension(16, 16), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/* 1064 */     paramUIDefaults.put("Tree.openIcon", new NimbusIcon("Tree", "openIconPainter", 16, 16));
/* 1065 */     paramUIDefaults.put("Tree[Enabled].collapsedIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TreePainter", 7, new Insets(5, 5, 5, 5), new Dimension(18, 7), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/* 1066 */     paramUIDefaults.put("Tree[Enabled+Selected].collapsedIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TreePainter", 8, new Insets(5, 5, 5, 5), new Dimension(18, 7), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/* 1067 */     paramUIDefaults.put("Tree.collapsedIcon", new NimbusIcon("Tree", "collapsedIconPainter", 18, 7));
/* 1068 */     paramUIDefaults.put("Tree[Enabled].expandedIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TreePainter", 9, new Insets(5, 5, 5, 5), new Dimension(18, 7), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/* 1069 */     paramUIDefaults.put("Tree[Enabled+Selected].expandedIconPainter", new LazyPainter("javax.swing.plaf.nimbus.TreePainter", 10, new Insets(5, 5, 5, 5), new Dimension(18, 7), false, AbstractRegionPainter.PaintContext.CacheMode.FIXED_SIZES, 1.0D, 1.0D));
/* 1070 */     paramUIDefaults.put("Tree.expandedIcon", new NimbusIcon("Tree", "expandedIconPainter", 18, 7));
/*      */ 
/*      */     
/* 1073 */     paramUIDefaults.put("RootPane.contentMargins", new InsetsUIResource(0, 0, 0, 0));
/* 1074 */     paramUIDefaults.put("RootPane.opaque", Boolean.TRUE);
/* 1075 */     addColor(paramUIDefaults, "RootPane.background", "control", 0.0F, 0.0F, 0.0F, 0);
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
/*      */   void register(Region paramRegion, String paramString) {
/* 1097 */     if (paramRegion == null || paramString == null) {
/* 1098 */       throw new IllegalArgumentException("Neither Region nor Prefix may be null");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1103 */     List<LazyStyle> list = this.m.get(paramRegion);
/* 1104 */     if (list == null) {
/* 1105 */       list = new LinkedList();
/* 1106 */       list.add(new LazyStyle(paramString));
/* 1107 */       this.m.put(paramRegion, list);
/*      */     }
/*      */     else {
/*      */       
/* 1111 */       for (LazyStyle lazyStyle : list) {
/* 1112 */         if (paramString.equals(lazyStyle.prefix)) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1116 */       list.add(new LazyStyle(paramString));
/*      */     } 
/*      */ 
/*      */     
/* 1120 */     this.registeredRegions.put(paramRegion.getName(), paramRegion);
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
/*      */   SynthStyle getStyle(JComponent paramJComponent, Region paramRegion) {
/* 1145 */     if (paramJComponent == null || paramRegion == null) {
/* 1146 */       throw new IllegalArgumentException("Neither comp nor r may be null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     List list = this.m.get(paramRegion);
/* 1153 */     if (list == null || list.size() == 0) {
/* 1154 */       return this.defaultStyle;
/*      */     }
/*      */ 
/*      */     
/* 1158 */     LazyStyle lazyStyle = null;
/* 1159 */     for (LazyStyle lazyStyle1 : list) {
/* 1160 */       if (lazyStyle1.matches(paramJComponent))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1168 */         if (lazyStyle == null || lazyStyle
/* 1169 */           .parts.length < lazyStyle1.parts.length || (lazyStyle
/* 1170 */           .parts.length == lazyStyle1.parts.length && lazyStyle
/* 1171 */           .simple && !lazyStyle1.simple)) {
/* 1172 */           lazyStyle = lazyStyle1;
/*      */         }
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1178 */     return (lazyStyle == null) ? this.defaultStyle : lazyStyle.getStyle(paramJComponent, paramRegion);
/*      */   }
/*      */   
/*      */   public void clearOverridesCache(JComponent paramJComponent) {
/* 1182 */     this.overridesCache.remove(paramJComponent);
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
/*      */   static final class DerivedFont
/*      */     implements UIDefaults.ActiveValue
/*      */   {
/*      */     private float sizeOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Boolean bold;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Boolean italic;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String parentKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DerivedFont(String param1String, float param1Float, Boolean param1Boolean1, Boolean param1Boolean2) {
/* 1232 */       if (param1String == null) {
/* 1233 */         throw new IllegalArgumentException("You must specify a key");
/*      */       }
/*      */ 
/*      */       
/* 1237 */       this.parentKey = param1String;
/* 1238 */       this.sizeOffset = param1Float;
/* 1239 */       this.bold = param1Boolean1;
/* 1240 */       this.italic = param1Boolean2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/* 1248 */       Font font = param1UIDefaults.getFont(this.parentKey);
/* 1249 */       if (font != null) {
/*      */ 
/*      */         
/* 1252 */         float f = Math.round(font.getSize2D() * this.sizeOffset);
/* 1253 */         int i = font.getStyle();
/* 1254 */         if (this.bold != null) {
/* 1255 */           if (this.bold.booleanValue()) {
/* 1256 */             i |= 0x1;
/*      */           } else {
/* 1258 */             i &= 0xFFFFFFFE;
/*      */           } 
/*      */         }
/* 1261 */         if (this.italic != null) {
/* 1262 */           if (this.italic.booleanValue()) {
/* 1263 */             i |= 0x2;
/*      */           } else {
/* 1265 */             i &= 0xFFFFFFFD;
/*      */           } 
/*      */         }
/* 1268 */         return font.deriveFont(i, f);
/*      */       } 
/* 1270 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class LazyPainter
/*      */     implements UIDefaults.LazyValue
/*      */   {
/*      */     private int which;
/*      */ 
/*      */ 
/*      */     
/*      */     private AbstractRegionPainter.PaintContext ctx;
/*      */ 
/*      */     
/*      */     private String className;
/*      */ 
/*      */ 
/*      */     
/*      */     LazyPainter(String param1String, int param1Int, Insets param1Insets, Dimension param1Dimension, boolean param1Boolean) {
/* 1292 */       if (param1String == null) {
/* 1293 */         throw new IllegalArgumentException("The className must be specified");
/*      */       }
/*      */ 
/*      */       
/* 1297 */       this.className = param1String;
/* 1298 */       this.which = param1Int;
/* 1299 */       this.ctx = new AbstractRegionPainter.PaintContext(param1Insets, param1Dimension, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LazyPainter(String param1String, int param1Int, Insets param1Insets, Dimension param1Dimension, boolean param1Boolean, AbstractRegionPainter.PaintContext.CacheMode param1CacheMode, double param1Double1, double param1Double2) {
/* 1307 */       if (param1String == null) {
/* 1308 */         throw new IllegalArgumentException("The className must be specified");
/*      */       }
/*      */ 
/*      */       
/* 1312 */       this.className = param1String;
/* 1313 */       this.which = param1Int;
/* 1314 */       this.ctx = new AbstractRegionPainter.PaintContext(param1Insets, param1Dimension, param1Boolean, param1CacheMode, param1Double1, param1Double2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/*      */       try {
/*      */         Object object;
/* 1324 */         if (param1UIDefaults == null || !(object = param1UIDefaults.get("ClassLoader") instanceof ClassLoader)) {
/*      */ 
/*      */           
/* 1327 */           object = Thread.currentThread().getContextClassLoader();
/* 1328 */           if (object == null)
/*      */           {
/* 1330 */             object = ClassLoader.getSystemClassLoader();
/*      */           }
/*      */         } 
/*      */         
/* 1334 */         Class<?> clazz = Class.forName(this.className, true, (ClassLoader)object);
/* 1335 */         Constructor<?> constructor = clazz.getConstructor(new Class[] { AbstractRegionPainter.PaintContext.class, int.class });
/*      */         
/* 1337 */         if (constructor == null) {
/* 1338 */           throw new NullPointerException("Failed to find the constructor for the class: " + this.className);
/*      */         }
/*      */ 
/*      */         
/* 1342 */         return constructor.newInstance(new Object[] { this.ctx, Integer.valueOf(this.which) });
/* 1343 */       } catch (Exception exception) {
/* 1344 */         exception.printStackTrace();
/* 1345 */         return null;
/*      */       } 
/*      */     }
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
/*      */   private final class LazyStyle
/*      */   {
/*      */     private String prefix;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean simple = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Part[] parts;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private NimbusStyle style;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private LazyStyle(String param1String) {
/* 1395 */       if (param1String == null) {
/* 1396 */         throw new IllegalArgumentException("The prefix must not be null");
/*      */       }
/*      */ 
/*      */       
/* 1400 */       this.prefix = param1String;
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
/* 1413 */       String str = param1String;
/* 1414 */       if (str.endsWith("cellRenderer\"") || str
/* 1415 */         .endsWith("renderer\"") || str
/* 1416 */         .endsWith("listRenderer\"")) {
/* 1417 */         str = str.substring(str.lastIndexOf(":\"") + 1);
/*      */       }
/*      */ 
/*      */       
/* 1421 */       List<String> list = split(str);
/* 1422 */       this.parts = new Part[list.size()];
/* 1423 */       for (byte b = 0; b < this.parts.length; b++) {
/* 1424 */         this.parts[b] = new Part(list.get(b));
/* 1425 */         if ((this.parts[b]).named) {
/* 1426 */           this.simple = false;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SynthStyle getStyle(JComponent param1JComponent, Region param1Region) {
/* 1438 */       if (param1JComponent.getClientProperty("Nimbus.Overrides") != null) {
/* 1439 */         Map<Object, Object> map = (Map)NimbusDefaults.this.overridesCache.get(param1JComponent);
/* 1440 */         SynthStyle synthStyle = null;
/* 1441 */         if (map == null) {
/* 1442 */           map = new HashMap<>();
/* 1443 */           NimbusDefaults.this.overridesCache.put(param1JComponent, map);
/*      */         } else {
/* 1445 */           synthStyle = (SynthStyle)map.get(param1Region);
/*      */         } 
/* 1447 */         if (synthStyle == null) {
/* 1448 */           synthStyle = new NimbusStyle(this.prefix, param1JComponent);
/* 1449 */           map.put(param1Region, synthStyle);
/*      */         } 
/* 1451 */         return synthStyle;
/*      */       } 
/*      */ 
/*      */       
/* 1455 */       if (this.style == null) {
/* 1456 */         this.style = new NimbusStyle(this.prefix, null);
/*      */       }
/*      */       
/* 1459 */       return this.style;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean matches(JComponent param1JComponent) {
/* 1471 */       return matches(param1JComponent, this.parts.length - 1);
/*      */     }
/*      */     
/*      */     private boolean matches(Component param1Component, int param1Int) {
/* 1475 */       if (param1Int < 0) return true; 
/* 1476 */       if (param1Component == null) return false;
/*      */ 
/*      */       
/* 1479 */       String str = param1Component.getName();
/* 1480 */       if ((this.parts[param1Int]).named && (this.parts[param1Int]).s.equals(str))
/*      */       {
/* 1482 */         return matches(param1Component.getParent(), param1Int - 1); } 
/* 1483 */       if (!(this.parts[param1Int]).named) {
/*      */ 
/*      */ 
/*      */         
/* 1487 */         Class clazz = (this.parts[param1Int]).c;
/* 1488 */         if (clazz != null && clazz.isAssignableFrom(param1Component.getClass()))
/*      */         {
/* 1490 */           return matches(param1Component.getParent(), param1Int - 1); } 
/* 1491 */         if (clazz == null && NimbusDefaults.this
/* 1492 */           .registeredRegions.containsKey((this.parts[param1Int]).s)) {
/* 1493 */           Region region = (Region)NimbusDefaults.this.registeredRegions.get((this.parts[param1Int]).s);
/* 1494 */           Component component = region.isSubregion() ? param1Component : param1Component.getParent();
/*      */ 
/*      */           
/* 1497 */           if (region == Region.INTERNAL_FRAME_TITLE_PANE && component != null && component instanceof JInternalFrame.JDesktopIcon) {
/*      */             
/* 1499 */             JInternalFrame.JDesktopIcon jDesktopIcon = (JInternalFrame.JDesktopIcon)component;
/*      */             
/* 1501 */             component = jDesktopIcon.getInternalFrame();
/*      */           } 
/*      */           
/* 1504 */           return matches(component, param1Int - 1);
/*      */         } 
/*      */       } 
/*      */       
/* 1508 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<String> split(String param1String) {
/* 1519 */       ArrayList<String> arrayList = new ArrayList();
/* 1520 */       byte b1 = 0;
/* 1521 */       boolean bool = false;
/* 1522 */       int i = 0;
/* 1523 */       for (byte b2 = 0; b2 < param1String.length(); b2++) {
/* 1524 */         char c = param1String.charAt(b2);
/*      */         
/* 1526 */         if (c == '[') {
/* 1527 */           b1++;
/*      */         }
/* 1529 */         else if (c == '"') {
/* 1530 */           bool = !bool ? true : false;
/*      */         }
/* 1532 */         else if (c == ']') {
/* 1533 */           b1--;
/* 1534 */           if (b1 < 0) {
/* 1535 */             throw new RuntimeException("Malformed prefix: " + param1String);
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 1541 */         else if (c == ':' && !bool && b1 == 0) {
/*      */           
/* 1543 */           arrayList.add(param1String.substring(i, b2));
/* 1544 */           i = b2 + 1;
/*      */         } 
/*      */       } 
/* 1547 */       if (i < param1String.length() - 1 && !bool && b1 == 0)
/*      */       {
/* 1549 */         arrayList.add(param1String.substring(i));
/*      */       }
/* 1551 */       return arrayList;
/*      */     }
/*      */ 
/*      */     
/*      */     private final class Part
/*      */     {
/*      */       private String s;
/*      */       private boolean named;
/*      */       private Class c;
/*      */       
/*      */       Part(String param2String) {
/* 1562 */         this.named = (param2String.charAt(0) == '"' && param2String.charAt(param2String.length() - 1) == '"');
/* 1563 */         if (this.named)
/* 1564 */         { this.s = param2String.substring(1, param2String.length() - 1); }
/*      */         else
/* 1566 */         { this.s = param2String;
/*      */ 
/*      */           
/*      */           try {
/* 1570 */             this.c = Class.forName("javax.swing.J" + param2String);
/* 1571 */           } catch (Exception exception) {}
/*      */           
/*      */           try {
/* 1574 */             this.c = Class.forName(param2String.replace("_", "."));
/* 1575 */           } catch (Exception exception) {} }  } } } private final class Part { private String s; private boolean named; private Class c; Part(String param1String) { this.named = (param1String.charAt(0) == '"' && param1String.charAt(param1String.length() - 1) == '"'); if (this.named) { this.s = param1String.substring(1, param1String.length() - 1); } else { this.s = param1String; try { this.c = Class.forName("javax.swing.J" + param1String); } catch (Exception exception) {} try { this.c = Class.forName(param1String.replace("_", ".")); } catch (Exception exception) {} }
/*      */        }
/*      */      }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addColor(UIDefaults paramUIDefaults, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1583 */     ColorUIResource colorUIResource = new ColorUIResource(new Color(paramInt1, paramInt2, paramInt3, paramInt4));
/* 1584 */     this.colorTree.addColor(paramString, colorUIResource);
/* 1585 */     paramUIDefaults.put(paramString, colorUIResource);
/*      */   }
/*      */ 
/*      */   
/*      */   private void addColor(UIDefaults paramUIDefaults, String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt) {
/* 1590 */     addColor(paramUIDefaults, paramString1, paramString2, paramFloat1, paramFloat2, paramFloat3, paramInt, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addColor(UIDefaults paramUIDefaults, String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, boolean paramBoolean) {
/* 1596 */     DerivedColor derivedColor = getDerivedColor(paramString1, paramString2, paramFloat1, paramFloat2, paramFloat3, paramInt, paramBoolean);
/*      */     
/* 1598 */     paramUIDefaults.put(paramString1, derivedColor);
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
/*      */   public DerivedColor getDerivedColor(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, boolean paramBoolean) {
/* 1618 */     return getDerivedColor(null, paramString, paramFloat1, paramFloat2, paramFloat3, paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DerivedColor getDerivedColor(String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, boolean paramBoolean) {
/*      */     DerivedColor derivedColor;
/* 1627 */     if (paramBoolean) {
/* 1628 */       derivedColor = new DerivedColor.UIResource(paramString2, paramFloat1, paramFloat2, paramFloat3, paramInt);
/*      */     } else {
/*      */       
/* 1631 */       derivedColor = new DerivedColor(paramString2, paramFloat1, paramFloat2, paramFloat3, paramInt);
/*      */     } 
/*      */ 
/*      */     
/* 1635 */     if (this.derivedColors.containsKey(derivedColor)) {
/* 1636 */       return this.derivedColors.get(derivedColor);
/*      */     }
/* 1638 */     this.derivedColors.put(derivedColor, derivedColor);
/* 1639 */     derivedColor.rederiveColor();
/* 1640 */     this.colorTree.addColor(paramString1, derivedColor);
/* 1641 */     return derivedColor;
/*      */   }
/*      */   
/*      */   NimbusDefaults() {
/* 1645 */     this.derivedColors = new HashMap<>(); this.m = new HashMap<>(); this.defaultFont = FontUtilities.getFontConfigFUIR("sans", 0, 12); this.defaultStyle = new DefaultSynthStyle(); this.defaultStyle.setFont(this.defaultFont); register(Region.ARROW_BUTTON, "ArrowButton"); register(Region.BUTTON, "Button"); register(Region.TOGGLE_BUTTON, "ToggleButton"); register(Region.RADIO_BUTTON, "RadioButton"); register(Region.CHECK_BOX, "CheckBox"); register(Region.COLOR_CHOOSER, "ColorChooser"); register(Region.PANEL, "ColorChooser:\"ColorChooser.previewPanelHolder\""); register(Region.LABEL, "ColorChooser:\"ColorChooser.previewPanelHolder\":\"OptionPane.label\""); register(Region.COMBO_BOX, "ComboBox"); register(Region.TEXT_FIELD, "ComboBox:\"ComboBox.textField\""); register(Region.ARROW_BUTTON, "ComboBox:\"ComboBox.arrowButton\""); register(Region.LABEL, "ComboBox:\"ComboBox.listRenderer\""); register(Region.LABEL, "ComboBox:\"ComboBox.renderer\""); register(Region.SCROLL_PANE, "\"ComboBox.scrollPane\""); register(Region.FILE_CHOOSER, "FileChooser"); register(Region.INTERNAL_FRAME_TITLE_PANE, "InternalFrameTitlePane"); register(Region.INTERNAL_FRAME, "InternalFrame"); register(Region.INTERNAL_FRAME_TITLE_PANE, "InternalFrame:InternalFrameTitlePane"); register(Region.BUTTON, "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.menuButton\""); register(Region.BUTTON, "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\""); register(Region.BUTTON, "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\""); register(Region.BUTTON, "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\""); register(Region.DESKTOP_ICON, "DesktopIcon"); register(Region.DESKTOP_PANE, "DesktopPane"); register(Region.LABEL, "Label"); register(Region.LIST, "List"); register(Region.LABEL, "List:\"List.cellRenderer\""); register(Region.MENU_BAR, "MenuBar"); register(Region.MENU, "MenuBar:Menu"); register(Region.MENU_ITEM_ACCELERATOR, "MenuBar:Menu:MenuItemAccelerator"); register(Region.MENU_ITEM, "MenuItem"); register(Region.MENU_ITEM_ACCELERATOR, "MenuItem:MenuItemAccelerator"); register(Region.RADIO_BUTTON_MENU_ITEM, "RadioButtonMenuItem"); register(Region.MENU_ITEM_ACCELERATOR, "RadioButtonMenuItem:MenuItemAccelerator"); register(Region.CHECK_BOX_MENU_ITEM, "CheckBoxMenuItem"); register(Region.MENU_ITEM_ACCELERATOR, "CheckBoxMenuItem:MenuItemAccelerator"); register(Region.MENU, "Menu"); register(Region.MENU_ITEM_ACCELERATOR, "Menu:MenuItemAccelerator"); register(Region.POPUP_MENU, "PopupMenu"); register(Region.POPUP_MENU_SEPARATOR, "PopupMenuSeparator"); register(Region.OPTION_PANE, "OptionPane"); register(Region.SEPARATOR, "OptionPane:\"OptionPane.separator\""); register(Region.PANEL, "OptionPane:\"OptionPane.messageArea\""); register(Region.LABEL, "OptionPane:\"OptionPane.messageArea\":\"OptionPane.label\""); register(Region.PANEL, "Panel"); register(Region.PROGRESS_BAR, "ProgressBar"); register(Region.SEPARATOR, "Separator"); register(Region.SCROLL_BAR, "ScrollBar"); register(Region.ARROW_BUTTON, "ScrollBar:\"ScrollBar.button\""); register(Region.SCROLL_BAR_THUMB, "ScrollBar:ScrollBarThumb"); register(Region.SCROLL_BAR_TRACK, "ScrollBar:ScrollBarTrack"); register(Region.SCROLL_PANE, "ScrollPane"); register(Region.VIEWPORT, "Viewport"); register(Region.SLIDER, "Slider"); register(Region.SLIDER_THUMB, "Slider:SliderThumb"); register(Region.SLIDER_TRACK, "Slider:SliderTrack"); register(Region.SPINNER, "Spinner"); register(Region.PANEL, "Spinner:\"Spinner.editor\""); register(Region.FORMATTED_TEXT_FIELD, "Spinner:Panel:\"Spinner.formattedTextField\""); register(Region.ARROW_BUTTON, "Spinner:\"Spinner.previousButton\""); register(Region.ARROW_BUTTON, "Spinner:\"Spinner.nextButton\""); register(Region.SPLIT_PANE, "SplitPane"); register(Region.SPLIT_PANE_DIVIDER, "SplitPane:SplitPaneDivider"); register(Region.TABBED_PANE, "TabbedPane"); register(Region.TABBED_PANE_TAB, "TabbedPane:TabbedPaneTab"); register(Region.TABBED_PANE_TAB_AREA, "TabbedPane:TabbedPaneTabArea"); register(Region.TABBED_PANE_CONTENT, "TabbedPane:TabbedPaneContent"); register(Region.TABLE, "Table"); register(Region.LABEL, "Table:\"Table.cellRenderer\""); register(Region.TABLE_HEADER, "TableHeader"); register(Region.LABEL, "TableHeader:\"TableHeader.renderer\""); register(Region.TEXT_FIELD, "\"Table.editor\""); register(Region.TEXT_FIELD, "\"Tree.cellEditor\""); register(Region.TEXT_FIELD, "TextField"); register(Region.FORMATTED_TEXT_FIELD, "FormattedTextField"); register(Region.PASSWORD_FIELD, "PasswordField"); register(Region.TEXT_AREA, "TextArea"); register(Region.TEXT_PANE, "TextPane"); register(Region.EDITOR_PANE, "EditorPane"); register(Region.TOOL_BAR, "ToolBar"); register(Region.BUTTON, "ToolBar:Button"); register(Region.TOGGLE_BUTTON, "ToolBar:ToggleButton"); register(Region.TOOL_BAR_SEPARATOR, "ToolBarSeparator"); register(Region.TOOL_TIP, "ToolTip");
/*      */     register(Region.TREE, "Tree");
/*      */     register(Region.TREE_CELL, "Tree:TreeCell");
/*      */     register(Region.LABEL, "Tree:\"Tree.cellRenderer\"");
/* 1649 */     register(Region.ROOT_PANE, "RootPane"); } private class ColorTree implements PropertyChangeListener { private Node root = new Node(null, null);
/* 1650 */     private Map<String, Node> nodes = new HashMap<>();
/*      */     
/*      */     public Color getColor(String param1String) {
/* 1653 */       return ((Node)this.nodes.get(param1String)).color;
/*      */     }
/*      */     
/*      */     public void addColor(String param1String, Color param1Color) {
/* 1657 */       Node node1 = getParentNode(param1Color);
/* 1658 */       Node node2 = new Node(param1Color, node1);
/* 1659 */       node1.children.add(node2);
/* 1660 */       if (param1String != null) {
/* 1661 */         this.nodes.put(param1String, node2);
/*      */       }
/*      */     }
/*      */     
/*      */     private Node getParentNode(Color param1Color) {
/* 1666 */       Node node = this.root;
/* 1667 */       if (param1Color instanceof DerivedColor) {
/* 1668 */         String str = ((DerivedColor)param1Color).getUiDefaultParentName();
/* 1669 */         Node node1 = this.nodes.get(str);
/* 1670 */         if (node1 != null) {
/* 1671 */           node = node1;
/*      */         }
/*      */       } 
/* 1674 */       return node;
/*      */     }
/*      */     
/*      */     public void update() {
/* 1678 */       this.root.update();
/*      */     }
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1683 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1684 */       Node node = this.nodes.get(str);
/* 1685 */       if (node != null) {
/*      */         
/* 1687 */         node.parent.children.remove(node);
/* 1688 */         Color color = (Color)param1PropertyChangeEvent.getNewValue();
/* 1689 */         Node node1 = getParentNode(color);
/* 1690 */         node.set(color, node1);
/* 1691 */         node1.children.add(node);
/* 1692 */         node.update();
/*      */       } 
/*      */     }
/*      */     private ColorTree() {}
/*      */     
/*      */     class Node { Color color;
/*      */       Node parent;
/* 1699 */       List<Node> children = new LinkedList<>();
/*      */       
/*      */       Node(Color param2Color, Node param2Node) {
/* 1702 */         set(param2Color, param2Node);
/*      */       }
/*      */       
/*      */       public void set(Color param2Color, Node param2Node) {
/* 1706 */         this.color = param2Color;
/* 1707 */         this.parent = param2Node;
/*      */       }
/*      */       
/*      */       public void update() {
/* 1711 */         if (this.color instanceof DerivedColor) {
/* 1712 */           ((DerivedColor)this.color).rederiveColor();
/*      */         }
/* 1714 */         for (Node node : this.children) {
/* 1715 */           node.update();
/*      */         }
/*      */       } }
/*      */      }
/*      */ 
/*      */   
/*      */   private class DefaultsListener
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     private DefaultsListener() {}
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1727 */       if ("lookAndFeel".equals(param1PropertyChangeEvent.getPropertyName()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1732 */         NimbusDefaults.this.colorTree.update();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class PainterBorder implements Border, UIResource {
/*      */     private Insets insets;
/*      */     private Painter painter;
/*      */     private String painterKey;
/*      */     
/*      */     PainterBorder(String param1String, Insets param1Insets) {
/* 1743 */       this.insets = param1Insets;
/* 1744 */       this.painterKey = param1String;
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1749 */       if (this.painter == null) {
/* 1750 */         this.painter = (Painter)UIManager.get(this.painterKey);
/* 1751 */         if (this.painter == null)
/*      */           return; 
/*      */       } 
/* 1754 */       param1Graphics.translate(param1Int1, param1Int2);
/* 1755 */       if (param1Graphics instanceof Graphics2D) {
/* 1756 */         this.painter.paint((Graphics2D)param1Graphics, param1Component, param1Int3, param1Int4);
/*      */       } else {
/* 1758 */         BufferedImage bufferedImage = new BufferedImage(param1Int3, param1Int4, 2);
/* 1759 */         Graphics2D graphics2D = bufferedImage.createGraphics();
/* 1760 */         this.painter.paint(graphics2D, param1Component, param1Int3, param1Int4);
/* 1761 */         graphics2D.dispose();
/* 1762 */         param1Graphics.drawImage(bufferedImage, param1Int1, param1Int2, null);
/* 1763 */         bufferedImage = null;
/*      */       } 
/* 1765 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */ 
/*      */     
/*      */     public Insets getBorderInsets(Component param1Component) {
/* 1770 */       return (Insets)this.insets.clone();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isBorderOpaque() {
/* 1775 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/NimbusDefaults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */