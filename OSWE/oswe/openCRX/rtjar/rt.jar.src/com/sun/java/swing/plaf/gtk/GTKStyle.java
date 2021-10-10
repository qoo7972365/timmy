/*      */ package com.sun.java.swing.plaf.gtk;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.DimensionUIResource;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.synth.ColorType;
/*      */ import javax.swing.plaf.synth.Region;
/*      */ import javax.swing.plaf.synth.SynthContext;
/*      */ import javax.swing.plaf.synth.SynthGraphicsUtils;
/*      */ import javax.swing.plaf.synth.SynthPainter;
/*      */ import javax.swing.plaf.synth.SynthStyle;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.UNIXToolkit;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.plaf.synth.SynthIcon;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class GTKStyle
/*      */   extends SynthStyle
/*      */   implements GTKConstants
/*      */ {
/*      */   private static final String ICON_PROPERTY_PREFIX = "gtk.icon.";
/*   59 */   static final Color BLACK_COLOR = new ColorUIResource(Color.BLACK);
/*   60 */   static final Color WHITE_COLOR = new ColorUIResource(Color.WHITE);
/*      */   
/*   62 */   static final Font DEFAULT_FONT = new FontUIResource("sansserif", 0, 10);
/*      */   
/*   64 */   static final Insets BUTTON_DEFAULT_BORDER_INSETS = new Insets(1, 1, 1, 1);
/*      */   
/*   66 */   private static final GTKGraphicsUtils GTK_GRAPHICS = new GTKGraphicsUtils();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   GTKStyle(Font paramFont, GTKEngine.WidgetType paramWidgetType) {
/*      */     String str;
/*   93 */     this.widgetType = paramWidgetType.ordinal();
/*      */ 
/*      */     
/*   96 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*   97 */       this.xThickness = nativeGetXThickness(this.widgetType);
/*   98 */       this.yThickness = nativeGetYThickness(this.widgetType);
/*   99 */       str = nativeGetPangoFontName(this.widgetType);
/*      */     } 
/*      */     
/*  102 */     Font font = null;
/*  103 */     if (str != null) {
/*  104 */       font = PangoFonts.lookupFont(str);
/*      */     }
/*  106 */     if (font != null) {
/*  107 */       this.font = font;
/*  108 */     } else if (paramFont != null) {
/*  109 */       this.font = paramFont;
/*      */     } else {
/*  111 */       this.font = DEFAULT_FONT;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void installDefaults(SynthContext paramSynthContext) {
/*  117 */     super.installDefaults(paramSynthContext);
/*  118 */     if (!paramSynthContext.getRegion().isSubregion()) {
/*  119 */       paramSynthContext.getComponent().putClientProperty(SwingUtilities2.AA_TEXT_PROPERTY_KEY, GTKLookAndFeel.aaTextInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SynthGraphicsUtils getGraphicsUtils(SynthContext paramSynthContext) {
/*  127 */     return GTK_GRAPHICS;
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
/*      */   public SynthPainter getPainter(SynthContext paramSynthContext) {
/*  139 */     return GTKPainter.INSTANCE;
/*      */   }
/*      */   
/*      */   protected Color getColorForState(SynthContext paramSynthContext, ColorType paramColorType) {
/*  143 */     if (paramColorType == ColorType.FOCUS || paramColorType == GTKColorType.BLACK) {
/*  144 */       return BLACK_COLOR;
/*      */     }
/*  146 */     if (paramColorType == GTKColorType.WHITE) {
/*  147 */       return WHITE_COLOR;
/*      */     }
/*      */     
/*  150 */     Region region = paramSynthContext.getRegion();
/*  151 */     int i = paramSynthContext.getComponentState();
/*  152 */     i = GTKLookAndFeel.synthStateToGTKState(region, i);
/*      */     
/*  154 */     if (paramColorType == ColorType.TEXT_FOREGROUND && (region == Region.BUTTON || region == Region.CHECK_BOX || region == Region.CHECK_BOX_MENU_ITEM || region == Region.MENU || region == Region.MENU_ITEM || region == Region.RADIO_BUTTON || region == Region.RADIO_BUTTON_MENU_ITEM || region == Region.TABBED_PANE_TAB || region == Region.TOGGLE_BUTTON || region == Region.TOOL_TIP || region == Region.MENU_ITEM_ACCELERATOR || region == Region.TABBED_PANE_TAB)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  167 */       paramColorType = ColorType.FOREGROUND;
/*  168 */     } else if (region == Region.TABLE || region == Region.LIST || region == Region.TREE || region == Region.TREE_CELL) {
/*      */ 
/*      */ 
/*      */       
/*  172 */       if (paramColorType == ColorType.FOREGROUND) {
/*  173 */         paramColorType = ColorType.TEXT_FOREGROUND;
/*  174 */         if (i == 4) {
/*  175 */           i = 512;
/*      */         }
/*  177 */       } else if (paramColorType == ColorType.BACKGROUND) {
/*  178 */         paramColorType = ColorType.TEXT_BACKGROUND;
/*      */       } 
/*      */     } 
/*      */     
/*  182 */     return getStyleSpecificColor(paramSynthContext, i, paramColorType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Color getStyleSpecificColor(SynthContext paramSynthContext, int paramInt, ColorType paramColorType) {
/*  192 */     paramInt = GTKLookAndFeel.synthStateToGTKStateType(paramInt).ordinal();
/*  193 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  194 */       int i = nativeGetColorForState(this.widgetType, paramInt, paramColorType
/*  195 */           .getID());
/*  196 */       return new ColorUIResource(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   Color getGTKColor(int paramInt, ColorType paramColorType) {
/*  201 */     return getGTKColor((SynthContext)null, paramInt, paramColorType);
/*      */   }
/*      */   
/*      */   Color getGTKColor(int paramInt1, int paramInt2, int paramInt3) {
/*  205 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  206 */       int i = nativeGetColorForState(paramInt1, paramInt2, paramInt3);
/*      */       
/*  208 */       return new ColorUIResource(i);
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
/*      */   Color getGTKColor(SynthContext paramSynthContext, int paramInt, ColorType paramColorType) {
/*  221 */     if (paramSynthContext != null) {
/*  222 */       JComponent jComponent = paramSynthContext.getComponent();
/*  223 */       Region region = paramSynthContext.getRegion();
/*      */       
/*  225 */       paramInt = GTKLookAndFeel.synthStateToGTKState(region, paramInt);
/*  226 */       if (!region.isSubregion() && (paramInt & 0x1) != 0)
/*      */       {
/*  228 */         if (paramColorType == ColorType.BACKGROUND || paramColorType == ColorType.TEXT_BACKGROUND) {
/*      */           
/*  230 */           Color color = jComponent.getBackground();
/*  231 */           if (!(color instanceof javax.swing.plaf.UIResource)) {
/*  232 */             return color;
/*      */           }
/*      */         }
/*  235 */         else if (paramColorType == ColorType.FOREGROUND || paramColorType == ColorType.TEXT_FOREGROUND) {
/*      */           
/*  237 */           Color color = jComponent.getForeground();
/*  238 */           if (!(color instanceof javax.swing.plaf.UIResource)) {
/*  239 */             return color;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  245 */     return getStyleSpecificColor(paramSynthContext, paramInt, paramColorType);
/*      */   }
/*      */ 
/*      */   
/*      */   public Color getColor(SynthContext paramSynthContext, ColorType paramColorType) {
/*  250 */     JComponent jComponent = paramSynthContext.getComponent();
/*  251 */     Region region = paramSynthContext.getRegion();
/*  252 */     int i = paramSynthContext.getComponentState();
/*      */     
/*  254 */     if (jComponent.getName() == "Table.cellRenderer") {
/*  255 */       if (paramColorType == ColorType.BACKGROUND) {
/*  256 */         return jComponent.getBackground();
/*      */       }
/*  258 */       if (paramColorType == ColorType.FOREGROUND) {
/*  259 */         return jComponent.getForeground();
/*      */       }
/*      */     } 
/*      */     
/*  263 */     if (region == Region.LABEL && paramColorType == ColorType.TEXT_FOREGROUND) {
/*  264 */       paramColorType = ColorType.FOREGROUND;
/*      */     }
/*      */ 
/*      */     
/*  268 */     if (!region.isSubregion() && (i & 0x1) != 0) {
/*  269 */       if (paramColorType == ColorType.BACKGROUND) {
/*  270 */         return jComponent.getBackground();
/*      */       }
/*  272 */       if (paramColorType == ColorType.FOREGROUND) {
/*  273 */         return jComponent.getForeground();
/*      */       }
/*  275 */       if (paramColorType == ColorType.TEXT_FOREGROUND) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  280 */         Color color = jComponent.getForeground();
/*  281 */         if (color != null && !(color instanceof javax.swing.plaf.UIResource)) {
/*  282 */           return color;
/*      */         }
/*      */       } 
/*      */     } 
/*  286 */     return getColorForState(paramSynthContext, paramColorType);
/*      */   }
/*      */   
/*      */   Font getDefaultFont() {
/*  290 */     return this.font;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Font getFontForState(SynthContext paramSynthContext) {
/*  295 */     Font font = UIManager.getFont(paramSynthContext.getRegion().getName() + ".font");
/*  296 */     if (font != null)
/*      */     {
/*  298 */       return font;
/*      */     }
/*  300 */     return this.font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getXThickness() {
/*  309 */     return this.xThickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getYThickness() {
/*  318 */     return this.yThickness;
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
/*      */   public Insets getInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  332 */     Region region = paramSynthContext.getRegion();
/*  333 */     JComponent jComponent = paramSynthContext.getComponent();
/*  334 */     String str = region.isSubregion() ? null : jComponent.getName();
/*      */     
/*  336 */     if (paramInsets == null) {
/*  337 */       paramInsets = new Insets(0, 0, 0, 0);
/*      */     } else {
/*  339 */       paramInsets.top = paramInsets.bottom = paramInsets.left = paramInsets.right = 0;
/*      */     } 
/*      */     
/*  342 */     if (region == Region.ARROW_BUTTON || region == Region.BUTTON || region == Region.TOGGLE_BUTTON) {
/*      */       
/*  344 */       if ("Spinner.previousButton" == str || "Spinner.nextButton" == str)
/*      */       {
/*  346 */         return getSimpleInsets(paramSynthContext, paramInsets, 1);
/*      */       }
/*  348 */       return getButtonInsets(paramSynthContext, paramInsets);
/*      */     } 
/*      */     
/*  351 */     if (region == Region.CHECK_BOX || region == Region.RADIO_BUTTON) {
/*  352 */       return getRadioInsets(paramSynthContext, paramInsets);
/*      */     }
/*  354 */     if (region == Region.MENU_BAR) {
/*  355 */       return getMenuBarInsets(paramSynthContext, paramInsets);
/*      */     }
/*  357 */     if (region == Region.MENU || region == Region.MENU_ITEM || region == Region.CHECK_BOX_MENU_ITEM || region == Region.RADIO_BUTTON_MENU_ITEM)
/*      */     {
/*      */ 
/*      */       
/*  361 */       return getMenuItemInsets(paramSynthContext, paramInsets);
/*      */     }
/*  363 */     if (region == Region.FORMATTED_TEXT_FIELD) {
/*  364 */       return getTextFieldInsets(paramSynthContext, paramInsets);
/*      */     }
/*  366 */     if (region == Region.INTERNAL_FRAME) {
/*  367 */       paramInsets = Metacity.INSTANCE.getBorderInsets(paramSynthContext, paramInsets);
/*      */     }
/*  369 */     else if (region == Region.LABEL) {
/*  370 */       if ("TableHeader.renderer" == str) {
/*  371 */         return getButtonInsets(paramSynthContext, paramInsets);
/*      */       }
/*  373 */       if (jComponent instanceof javax.swing.ListCellRenderer) {
/*  374 */         return getTextFieldInsets(paramSynthContext, paramInsets);
/*      */       }
/*  376 */       if ("Tree.cellRenderer" == str) {
/*  377 */         return getSimpleInsets(paramSynthContext, paramInsets, 1);
/*      */       }
/*      */     } else {
/*  380 */       if (region == Region.OPTION_PANE) {
/*  381 */         return getSimpleInsets(paramSynthContext, paramInsets, 6);
/*      */       }
/*  383 */       if (region == Region.POPUP_MENU) {
/*  384 */         return getSimpleInsets(paramSynthContext, paramInsets, 2);
/*      */       }
/*  386 */       if (region == Region.PROGRESS_BAR || region == Region.SLIDER || region == Region.TABBED_PANE || region == Region.TABBED_PANE_CONTENT || region == Region.TOOL_BAR || region == Region.TOOL_BAR_DRAG_WINDOW || region == Region.TOOL_TIP)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  391 */         return getThicknessInsets(paramSynthContext, paramInsets);
/*      */       }
/*  393 */       if (region == Region.SCROLL_BAR) {
/*  394 */         return getScrollBarInsets(paramSynthContext, paramInsets);
/*      */       }
/*  396 */       if (region == Region.SLIDER_TRACK) {
/*  397 */         return getSliderTrackInsets(paramSynthContext, paramInsets);
/*      */       }
/*  399 */       if (region == Region.TABBED_PANE_TAB) {
/*  400 */         return getTabbedPaneTabInsets(paramSynthContext, paramInsets);
/*      */       }
/*  402 */       if (region == Region.TEXT_FIELD || region == Region.PASSWORD_FIELD) {
/*  403 */         if (str == "Tree.cellEditor") {
/*  404 */           return getSimpleInsets(paramSynthContext, paramInsets, 1);
/*      */         }
/*  406 */         return getTextFieldInsets(paramSynthContext, paramInsets);
/*  407 */       }  if (region == Region.SEPARATOR || region == Region.POPUP_MENU_SEPARATOR || region == Region.TOOL_BAR_SEPARATOR)
/*      */       {
/*      */         
/*  410 */         return getSeparatorInsets(paramSynthContext, paramInsets); } 
/*  411 */       if (region == GTKEngine.CustomRegion.TITLED_BORDER)
/*  412 */         return getThicknessInsets(paramSynthContext, paramInsets); 
/*      */     } 
/*  414 */     return paramInsets;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Insets getButtonInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  420 */     byte b = 1;
/*  421 */     int i = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*  422 */     int j = getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*  423 */     int k = getXThickness();
/*  424 */     int m = getYThickness();
/*  425 */     int n = i + j + k + b;
/*  426 */     int i1 = i + j + m + b;
/*  427 */     paramInsets.left = paramInsets.right = n;
/*  428 */     paramInsets.top = paramInsets.bottom = i1;
/*      */     
/*  430 */     JComponent jComponent = paramSynthContext.getComponent();
/*  431 */     if (jComponent instanceof JButton && 
/*  432 */       !(jComponent.getParent() instanceof javax.swing.JToolBar) && ((JButton)jComponent)
/*  433 */       .isDefaultCapable()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  440 */       Insets insets = getClassSpecificInsetsValue(paramSynthContext, "default-border", BUTTON_DEFAULT_BORDER_INSETS);
/*      */       
/*  442 */       paramInsets.left += insets.left;
/*  443 */       paramInsets.right += insets.right;
/*  444 */       paramInsets.top += insets.top;
/*  445 */       paramInsets.bottom += insets.bottom;
/*      */     } 
/*      */     
/*  448 */     return paramInsets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Insets getRadioInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  458 */     int i = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*      */     
/*  460 */     int j = getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*  461 */     int k = i + j;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  467 */     paramInsets.top = k;
/*  468 */     paramInsets.bottom = k;
/*  469 */     if (paramSynthContext.getComponent().getComponentOrientation().isLeftToRight()) {
/*  470 */       paramInsets.left = 0;
/*  471 */       paramInsets.right = k;
/*      */     } else {
/*  473 */       paramInsets.left = k;
/*  474 */       paramInsets.right = 0;
/*      */     } 
/*      */     
/*  477 */     return paramInsets;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Insets getMenuBarInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  483 */     int i = getClassSpecificIntValue(paramSynthContext, "internal-padding", 1);
/*      */     
/*  485 */     int j = getXThickness();
/*  486 */     int k = getYThickness();
/*  487 */     paramInsets.left = paramInsets.right = j + i;
/*  488 */     paramInsets.top = paramInsets.bottom = k + i;
/*  489 */     return paramInsets;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Insets getMenuItemInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  495 */     int i = getClassSpecificIntValue(paramSynthContext, "horizontal-padding", 3);
/*      */     
/*  497 */     int j = getXThickness();
/*  498 */     int k = getYThickness();
/*  499 */     paramInsets.left = paramInsets.right = j + i;
/*  500 */     paramInsets.top = paramInsets.bottom = k;
/*  501 */     return paramInsets;
/*      */   }
/*      */   
/*      */   private Insets getThicknessInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  505 */     paramInsets.left = paramInsets.right = getXThickness();
/*  506 */     paramInsets.top = paramInsets.bottom = getYThickness();
/*  507 */     return paramInsets;
/*      */   }
/*      */   
/*      */   private Insets getSeparatorInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  511 */     int i = 0;
/*  512 */     if (paramSynthContext.getRegion() == Region.POPUP_MENU_SEPARATOR)
/*      */     {
/*  514 */       i = getClassSpecificIntValue(paramSynthContext, "horizontal-padding", 3);
/*      */     }
/*  516 */     paramInsets.right = paramInsets.left = getXThickness() + i;
/*  517 */     paramInsets.top = paramInsets.bottom = getYThickness();
/*  518 */     return paramInsets;
/*      */   }
/*      */   
/*      */   private Insets getSliderTrackInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  522 */     int i = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*  523 */     int j = getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*  524 */     paramInsets.top = paramInsets.bottom = paramInsets.left = paramInsets.right = i + j;
/*      */     
/*  526 */     return paramInsets;
/*      */   }
/*      */   
/*      */   private Insets getSimpleInsets(SynthContext paramSynthContext, Insets paramInsets, int paramInt) {
/*  530 */     paramInsets.top = paramInsets.bottom = paramInsets.right = paramInsets.left = paramInt;
/*  531 */     return paramInsets;
/*      */   }
/*      */   
/*      */   private Insets getTabbedPaneTabInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  535 */     int i = getXThickness();
/*  536 */     int j = getYThickness();
/*  537 */     int k = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*  538 */     byte b = 2;
/*      */     
/*  540 */     paramInsets.left = paramInsets.right = k + b + i;
/*  541 */     paramInsets.top = paramInsets.bottom = k + b + j;
/*  542 */     return paramInsets;
/*      */   }
/*      */ 
/*      */   
/*      */   private Insets getTextFieldInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  547 */     paramInsets = getClassSpecificInsetsValue(paramSynthContext, "inner-border", 
/*  548 */         getSimpleInsets(paramSynthContext, paramInsets, 2));
/*      */     
/*  550 */     int i = getXThickness();
/*  551 */     int j = getYThickness();
/*      */     
/*  553 */     boolean bool = getClassSpecificBoolValue(paramSynthContext, "interior-focus", true);
/*  554 */     int k = 0;
/*      */     
/*  556 */     if (!bool) {
/*  557 */       k = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*      */     }
/*      */     
/*  560 */     paramInsets.left += k + i;
/*  561 */     paramInsets.right += k + i;
/*  562 */     paramInsets.top += k + j;
/*  563 */     paramInsets.bottom += k + j;
/*  564 */     return paramInsets;
/*      */   }
/*      */ 
/*      */   
/*      */   private Insets getScrollBarInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  569 */     int i = getClassSpecificIntValue(paramSynthContext, "trough-border", 1);
/*  570 */     paramInsets.left = paramInsets.right = paramInsets.top = paramInsets.bottom = i;
/*      */     
/*  572 */     JComponent jComponent = paramSynthContext.getComponent();
/*  573 */     if (jComponent.getParent() instanceof javax.swing.JScrollPane) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  578 */       int j = getClassSpecificIntValue(GTKEngine.WidgetType.SCROLL_PANE, "scrollbar-spacing", 3);
/*      */       
/*  580 */       if (((JScrollBar)jComponent).getOrientation() == 0) {
/*  581 */         paramInsets.top += j;
/*      */       }
/*  583 */       else if (jComponent.getComponentOrientation().isLeftToRight()) {
/*  584 */         paramInsets.left += j;
/*      */       } else {
/*  586 */         paramInsets.right += j;
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  592 */     else if (jComponent.isFocusable()) {
/*      */       
/*  594 */       int j = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*      */       
/*  596 */       int k = getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*  597 */       int m = j + k;
/*  598 */       paramInsets.left += m;
/*  599 */       paramInsets.right += m;
/*  600 */       paramInsets.top += m;
/*  601 */       paramInsets.bottom += m;
/*      */     } 
/*      */     
/*  604 */     return paramInsets;
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
/*      */   private static Object getClassSpecificValue(GTKEngine.WidgetType paramWidgetType, String paramString) {
/*  620 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  621 */       return nativeGetClassValue(paramWidgetType.ordinal(), paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getClassSpecificIntValue(GTKEngine.WidgetType paramWidgetType, String paramString, int paramInt) {
/*  638 */     Object object = getClassSpecificValue(paramWidgetType, paramString);
/*  639 */     if (object instanceof Number) {
/*  640 */       return ((Number)object).intValue();
/*      */     }
/*  642 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object getClassSpecificValue(String paramString) {
/*  653 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  654 */       return nativeGetClassValue(this.widgetType, paramString);
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
/*      */ 
/*      */ 
/*      */   
/*      */   int getClassSpecificIntValue(SynthContext paramSynthContext, String paramString, int paramInt) {
/*  670 */     Object object = getClassSpecificValue(paramString);
/*      */     
/*  672 */     if (object instanceof Number) {
/*  673 */       return ((Number)object).intValue();
/*      */     }
/*  675 */     return paramInt;
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
/*      */   Insets getClassSpecificInsetsValue(SynthContext paramSynthContext, String paramString, Insets paramInsets) {
/*  690 */     Object object = getClassSpecificValue(paramString);
/*      */     
/*  692 */     if (object instanceof Insets) {
/*  693 */       return (Insets)object;
/*      */     }
/*  695 */     return paramInsets;
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
/*      */   boolean getClassSpecificBoolValue(SynthContext paramSynthContext, String paramString, boolean paramBoolean) {
/*  710 */     Object object = getClassSpecificValue(paramString);
/*      */     
/*  712 */     if (object instanceof Boolean) {
/*  713 */       return ((Boolean)object).booleanValue();
/*      */     }
/*  715 */     return paramBoolean;
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
/*      */   public boolean isOpaque(SynthContext paramSynthContext) {
/*  728 */     Region region = paramSynthContext.getRegion();
/*  729 */     if (region == Region.COMBO_BOX || region == Region.DESKTOP_PANE || region == Region.DESKTOP_ICON || region == Region.INTERNAL_FRAME || region == Region.LIST || region == Region.MENU_BAR || region == Region.PANEL || region == Region.POPUP_MENU || region == Region.PROGRESS_BAR || region == Region.ROOT_PANE || region == Region.SCROLL_PANE || region == Region.SPLIT_PANE_DIVIDER || region == Region.TABLE || region == Region.TEXT_AREA || region == Region.TOOL_BAR_DRAG_WINDOW || region == Region.TOOL_TIP || region == Region.TREE || region == Region.VIEWPORT || region == Region.TEXT_PANE)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  748 */       return true;
/*      */     }
/*  750 */     if (!GTKLookAndFeel.is3() && (
/*  751 */       region == Region.EDITOR_PANE || region == Region.FORMATTED_TEXT_FIELD || region == Region.PASSWORD_FIELD || region == Region.SPINNER || region == Region.TEXT_FIELD))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  756 */       return true;
/*      */     }
/*      */     
/*  759 */     JComponent jComponent = paramSynthContext.getComponent();
/*  760 */     String str = jComponent.getName();
/*  761 */     if (str == "ComboBox.renderer" || str == "ComboBox.listRenderer") {
/*  762 */       return true;
/*      */     }
/*  764 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(SynthContext paramSynthContext, Object paramObject) {
/*  770 */     String str = CLASS_SPECIFIC_MAP.get(paramObject);
/*  771 */     if (str != null) {
/*  772 */       Object object = getClassSpecificValue(str);
/*  773 */       if (object != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  779 */         if ("Slider.thumbWidth".equals(paramObject) && object.equals(Integer.valueOf(31))) {
/*  780 */           return Integer.valueOf(14);
/*      */         }
/*  782 */         return object;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  787 */     if (paramObject == "ScrollPane.viewportBorderInsets") {
/*  788 */       return getThicknessInsets(paramSynthContext, new Insets(0, 0, 0, 0));
/*      */     }
/*  790 */     if (paramObject == "Slider.tickColor") {
/*  791 */       return getColorForState(paramSynthContext, ColorType.FOREGROUND);
/*      */     }
/*  793 */     if (paramObject == "ScrollBar.minimumThumbSize") {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  799 */       int i = getClassSpecificIntValue(paramSynthContext, "min-slider-length", 21);
/*  800 */       if (i == 21) {
/*  801 */         i = 40;
/*      */       }
/*  803 */       JScrollBar jScrollBar = (JScrollBar)paramSynthContext.getComponent();
/*  804 */       if (jScrollBar.getOrientation() == 0) {
/*  805 */         return new DimensionUIResource(i, 0);
/*      */       }
/*  807 */       return new DimensionUIResource(0, i);
/*      */     } 
/*      */     
/*  810 */     if (paramObject == "Separator.thickness") {
/*  811 */       JSeparator jSeparator = (JSeparator)paramSynthContext.getComponent();
/*  812 */       if (jSeparator.getOrientation() == 0) {
/*  813 */         return Integer.valueOf(getYThickness());
/*      */       }
/*  815 */       return Integer.valueOf(getXThickness());
/*      */     } 
/*      */     
/*  818 */     if (paramObject == "ToolBar.separatorSize") {
/*  819 */       int i = getClassSpecificIntValue(GTKEngine.WidgetType.TOOL_BAR, "space-size", 12);
/*      */       
/*  821 */       return new DimensionUIResource(i, i);
/*      */     } 
/*  823 */     if (paramObject == "ScrollBar.buttonSize") {
/*  824 */       JScrollBar jScrollBar = (JScrollBar)paramSynthContext.getComponent().getParent();
/*  825 */       boolean bool = (jScrollBar.getOrientation() == 0) ? true : false;
/*  826 */       GTKEngine.WidgetType widgetType = bool ? GTKEngine.WidgetType.HSCROLL_BAR : GTKEngine.WidgetType.VSCROLL_BAR;
/*      */       
/*  828 */       int i = getClassSpecificIntValue(widgetType, "slider-width", 14);
/*  829 */       int j = getClassSpecificIntValue(widgetType, "stepper-size", 14);
/*  830 */       return bool ? new DimensionUIResource(j, i) : new DimensionUIResource(i, j);
/*      */     } 
/*      */ 
/*      */     
/*  834 */     if (paramObject == "ArrowButton.size") {
/*  835 */       String str1 = paramSynthContext.getComponent().getName();
/*  836 */       if (str1 != null && str1.startsWith("Spinner")) {
/*      */         String str2;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  842 */         synchronized (UNIXToolkit.GTK_LOCK) {
/*      */           
/*  844 */           str2 = nativeGetPangoFontName(GTKEngine.WidgetType.SPINNER.ordinal());
/*      */         } 
/*      */         
/*  847 */         byte b = (str2 != null) ? PangoFonts.getFontSize(str2) : 10;
/*  848 */         return Integer.valueOf(b + getXThickness() * 2);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  855 */       if ("CheckBox.iconTextGap".equals(paramObject) || "RadioButton.iconTextGap"
/*  856 */         .equals(paramObject)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  863 */         int i = getClassSpecificIntValue(paramSynthContext, "indicator-spacing", 2);
/*      */         
/*  865 */         int j = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*      */         
/*  867 */         int k = getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*  868 */         return Integer.valueOf(i + j + k);
/*  869 */       }  if (GTKLookAndFeel.is3() && "ComboBox.forceOpaque".equals(paramObject))
/*  870 */         return Boolean.valueOf(true); 
/*  871 */       if ("Tree.expanderSize".equals(paramObject)) {
/*  872 */         Object object = getClassSpecificValue("expander-size");
/*  873 */         if (object instanceof Integer) {
/*  874 */           return Integer.valueOf(((Integer)object).intValue() + 4);
/*      */         }
/*  876 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/*  880 */     GTKStockIcon gTKStockIcon = null;
/*  881 */     synchronized (ICONS_MAP) {
/*  882 */       gTKStockIcon = ICONS_MAP.get(paramObject);
/*      */     } 
/*      */     
/*  885 */     if (gTKStockIcon != null) {
/*  886 */       return gTKStockIcon;
/*      */     }
/*      */ 
/*      */     
/*  890 */     if (paramObject != "engine") {
/*      */ 
/*      */ 
/*      */       
/*  894 */       Object object = UIManager.get(paramObject);
/*  895 */       if (paramObject == "Table.rowHeight") {
/*  896 */         int i = getClassSpecificIntValue(paramSynthContext, "focus-line-width", 0);
/*      */         
/*  898 */         if (object == null && i > 0) {
/*  899 */           object = Integer.valueOf(16 + 2 * i);
/*      */         }
/*      */       } 
/*  902 */       return object;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  907 */     return null;
/*      */   }
/*      */   
/*      */   private Icon getStockIcon(SynthContext paramSynthContext, String paramString, int paramInt) {
/*  911 */     GTKConstants.TextDirection textDirection = GTKConstants.TextDirection.LTR;
/*      */     
/*  913 */     if (paramSynthContext != null) {
/*      */       
/*  915 */       ComponentOrientation componentOrientation = paramSynthContext.getComponent().getComponentOrientation();
/*      */       
/*  917 */       if (componentOrientation != null && !componentOrientation.isLeftToRight()) {
/*  918 */         textDirection = GTKConstants.TextDirection.RTL;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  924 */     Icon icon = getStyleSpecificIcon(paramString, textDirection, paramInt);
/*  925 */     if (icon != null) {
/*  926 */       return icon;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  931 */     String str = "gtk.icon." + paramString + '.' + paramInt + '.' + ((textDirection == GTKConstants.TextDirection.RTL) ? "rtl" : "ltr");
/*      */ 
/*      */     
/*  934 */     Image image = (Image)Toolkit.getDefaultToolkit().getDesktopProperty(str);
/*  935 */     if (image != null) {
/*  936 */       return new ImageIcon(image);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  942 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Icon getStyleSpecificIcon(String paramString, GTKConstants.TextDirection paramTextDirection, int paramInt) {
/*  948 */     UNIXToolkit uNIXToolkit = (UNIXToolkit)Toolkit.getDefaultToolkit();
/*      */     
/*  950 */     BufferedImage bufferedImage = uNIXToolkit.getStockIcon(this.widgetType, paramString, paramInt, paramTextDirection.ordinal(), null);
/*  951 */     return (bufferedImage != null) ? new ImageIcon(bufferedImage) : null;
/*      */   }
/*      */   
/*      */   static class GTKStockIconInfo {
/*      */     private static Map<String, Integer> ICON_TYPE_MAP;
/*  956 */     private static final Object ICON_SIZE_KEY = new StringBuffer("IconSize");
/*      */     
/*      */     private static Dimension[] getIconSizesMap() {
/*  959 */       AppContext appContext = AppContext.getAppContext();
/*  960 */       Dimension[] arrayOfDimension = (Dimension[])appContext.get(ICON_SIZE_KEY);
/*      */       
/*  962 */       if (arrayOfDimension == null) {
/*  963 */         arrayOfDimension = new Dimension[7];
/*  964 */         arrayOfDimension[0] = null;
/*  965 */         arrayOfDimension[1] = new Dimension(16, 16);
/*  966 */         arrayOfDimension[2] = new Dimension(18, 18);
/*  967 */         arrayOfDimension[3] = new Dimension(24, 24);
/*  968 */         arrayOfDimension[4] = new Dimension(20, 20);
/*  969 */         arrayOfDimension[5] = new Dimension(32, 32);
/*  970 */         arrayOfDimension[6] = new Dimension(48, 48);
/*  971 */         appContext.put(ICON_SIZE_KEY, arrayOfDimension);
/*      */       } 
/*  973 */       return arrayOfDimension;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Dimension getIconSize(int param1Int) {
/*  983 */       Dimension[] arrayOfDimension = getIconSizesMap();
/*  984 */       return (param1Int >= 0 && param1Int < arrayOfDimension.length) ? arrayOfDimension[param1Int] : null;
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
/*      */     
/*      */     public static void setIconSize(int param1Int1, int param1Int2, int param1Int3) {
/*  997 */       Dimension[] arrayOfDimension = getIconSizesMap();
/*  998 */       if (param1Int1 >= 0 && param1Int1 < arrayOfDimension.length) {
/*  999 */         arrayOfDimension[param1Int1] = new Dimension(param1Int2, param1Int3);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int getIconType(String param1String) {
/* 1011 */       if (param1String == null) {
/* 1012 */         return -100;
/*      */       }
/* 1014 */       if (ICON_TYPE_MAP == null) {
/* 1015 */         initIconTypeMap();
/*      */       }
/* 1017 */       Integer integer = ICON_TYPE_MAP.get(param1String);
/* 1018 */       return (integer != null) ? integer.intValue() : -100;
/*      */     }
/*      */     
/*      */     private static void initIconTypeMap() {
/* 1022 */       ICON_TYPE_MAP = new HashMap<>();
/* 1023 */       ICON_TYPE_MAP.put("gtk-menu", Integer.valueOf(1));
/* 1024 */       ICON_TYPE_MAP.put("gtk-small-toolbar", Integer.valueOf(2));
/* 1025 */       ICON_TYPE_MAP.put("gtk-large-toolbar", Integer.valueOf(3));
/* 1026 */       ICON_TYPE_MAP.put("gtk-button", Integer.valueOf(4));
/* 1027 */       ICON_TYPE_MAP.put("gtk-dnd", Integer.valueOf(5));
/* 1028 */       ICON_TYPE_MAP.put("gtk-dialog", Integer.valueOf(6));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class GTKStockIcon
/*      */     extends SynthIcon
/*      */   {
/*      */     private String key;
/*      */     
/*      */     private int size;
/*      */     private boolean loadedLTR;
/*      */     private boolean loadedRTL;
/*      */     private Icon ltrIcon;
/*      */     private Icon rtlIcon;
/*      */     private SynthStyle style;
/*      */     
/*      */     GTKStockIcon(String param1String, int param1Int) {
/* 1046 */       this.key = param1String;
/* 1047 */       this.size = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintIcon(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1052 */       Icon icon = getIcon(param1SynthContext);
/*      */       
/* 1054 */       if (icon != null) {
/* 1055 */         if (param1SynthContext == null) {
/* 1056 */           icon.paintIcon(null, param1Graphics, param1Int1, param1Int2);
/*      */         } else {
/*      */           
/* 1059 */           icon.paintIcon(param1SynthContext.getComponent(), param1Graphics, param1Int1, param1Int2);
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     public int getIconWidth(SynthContext param1SynthContext) {
/* 1065 */       Icon icon = getIcon(param1SynthContext);
/*      */       
/* 1067 */       if (icon != null) {
/* 1068 */         return icon.getIconWidth();
/*      */       }
/* 1070 */       return 0;
/*      */     }
/*      */     
/*      */     public int getIconHeight(SynthContext param1SynthContext) {
/* 1074 */       Icon icon = getIcon(param1SynthContext);
/*      */       
/* 1076 */       if (icon != null) {
/* 1077 */         return icon.getIconHeight();
/*      */       }
/* 1079 */       return 0;
/*      */     }
/*      */     
/*      */     private Icon getIcon(SynthContext param1SynthContext) {
/* 1083 */       if (param1SynthContext != null) {
/*      */         
/* 1085 */         ComponentOrientation componentOrientation = param1SynthContext.getComponent().getComponentOrientation();
/* 1086 */         SynthStyle synthStyle = param1SynthContext.getStyle();
/*      */         
/* 1088 */         if (synthStyle != this.style) {
/* 1089 */           this.style = synthStyle;
/* 1090 */           this.loadedLTR = this.loadedRTL = false;
/*      */         } 
/* 1092 */         if (componentOrientation == null || componentOrientation.isLeftToRight()) {
/* 1093 */           if (!this.loadedLTR) {
/* 1094 */             this.loadedLTR = true;
/* 1095 */             this.ltrIcon = ((GTKStyle)param1SynthContext.getStyle()).getStockIcon(param1SynthContext, this.key, this.size);
/*      */           } 
/*      */           
/* 1098 */           return this.ltrIcon;
/*      */         } 
/* 1100 */         if (!this.loadedRTL) {
/* 1101 */           this.loadedRTL = true;
/* 1102 */           this.rtlIcon = ((GTKStyle)param1SynthContext.getStyle()).getStockIcon(param1SynthContext, this.key, this.size);
/*      */         } 
/*      */         
/* 1105 */         return this.rtlIcon;
/*      */       } 
/* 1107 */       return this.ltrIcon;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class GTKLazyValue
/*      */     implements UIDefaults.LazyValue
/*      */   {
/*      */     private String className;
/*      */ 
/*      */     
/*      */     private String methodName;
/*      */ 
/*      */ 
/*      */     
/*      */     GTKLazyValue(String param1String) {
/* 1124 */       this(param1String, null);
/*      */     }
/*      */     
/*      */     GTKLazyValue(String param1String1, String param1String2) {
/* 1128 */       this.className = param1String1;
/* 1129 */       this.methodName = param1String2;
/*      */     }
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/*      */       
/* 1134 */       try { Class<?> clazz = Class.forName(this.className, true, Thread.currentThread()
/* 1135 */             .getContextClassLoader());
/*      */         
/* 1137 */         if (this.methodName == null) {
/* 1138 */           return clazz.newInstance();
/*      */         }
/* 1140 */         Method method = clazz.getMethod(this.methodName, (Class[])null);
/*      */         
/* 1142 */         return method.invoke(clazz, (Object[])null); }
/* 1143 */       catch (ClassNotFoundException classNotFoundException) {  }
/* 1144 */       catch (IllegalAccessException illegalAccessException) {  }
/* 1145 */       catch (InvocationTargetException invocationTargetException) {  }
/* 1146 */       catch (NoSuchMethodException noSuchMethodException) {  }
/* 1147 */       catch (InstantiationException instantiationException) {}
/*      */       
/* 1149 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1154 */   private static final Map<String, String> CLASS_SPECIFIC_MAP = new HashMap<>(); static {
/* 1155 */     CLASS_SPECIFIC_MAP.put("Slider.thumbHeight", "slider-width");
/* 1156 */     CLASS_SPECIFIC_MAP.put("Slider.thumbWidth", "slider-length");
/* 1157 */     CLASS_SPECIFIC_MAP.put("Slider.trackBorder", "trough-border");
/* 1158 */     CLASS_SPECIFIC_MAP.put("SplitPane.size", "handle-size");
/* 1159 */     CLASS_SPECIFIC_MAP.put("ScrollBar.thumbHeight", "slider-width");
/* 1160 */     CLASS_SPECIFIC_MAP.put("ScrollBar.width", "slider-width");
/* 1161 */     CLASS_SPECIFIC_MAP.put("TextArea.caretForeground", "cursor-color");
/* 1162 */     CLASS_SPECIFIC_MAP.put("TextArea.caretAspectRatio", "cursor-aspect-ratio");
/* 1163 */     CLASS_SPECIFIC_MAP.put("TextField.caretForeground", "cursor-color");
/* 1164 */     CLASS_SPECIFIC_MAP.put("TextField.caretAspectRatio", "cursor-aspect-ratio");
/* 1165 */     CLASS_SPECIFIC_MAP.put("PasswordField.caretForeground", "cursor-color");
/* 1166 */     CLASS_SPECIFIC_MAP.put("PasswordField.caretAspectRatio", "cursor-aspect-ratio");
/* 1167 */     CLASS_SPECIFIC_MAP.put("FormattedTextField.caretForeground", "cursor-color");
/* 1168 */     CLASS_SPECIFIC_MAP.put("FormattedTextField.caretAspectRatio", "cursor-aspect-");
/* 1169 */     CLASS_SPECIFIC_MAP.put("TextPane.caretForeground", "cursor-color");
/* 1170 */     CLASS_SPECIFIC_MAP.put("TextPane.caretAspectRatio", "cursor-aspect-ratio");
/* 1171 */     CLASS_SPECIFIC_MAP.put("EditorPane.caretForeground", "cursor-color");
/* 1172 */     CLASS_SPECIFIC_MAP.put("EditorPane.caretAspectRatio", "cursor-aspect-ratio");
/*      */   }
/* 1174 */   private static final Map<String, GTKStockIcon> ICONS_MAP = new HashMap<>(); static {
/* 1175 */     ICONS_MAP.put("FileChooser.cancelIcon", new GTKStockIcon("gtk-cancel", 4));
/* 1176 */     ICONS_MAP.put("FileChooser.okIcon", new GTKStockIcon("gtk-ok", 4));
/* 1177 */     ICONS_MAP.put("OptionPane.yesIcon", new GTKStockIcon("gtk-yes", 4));
/* 1178 */     ICONS_MAP.put("OptionPane.noIcon", new GTKStockIcon("gtk-no", 4));
/* 1179 */     ICONS_MAP.put("OptionPane.cancelIcon", new GTKStockIcon("gtk-cancel", 4));
/* 1180 */     ICONS_MAP.put("OptionPane.okIcon", new GTKStockIcon("gtk-ok", 4));
/*      */ 
/*      */ 
/*      */     
/* 1184 */     UNIXToolkit uNIXToolkit = (UNIXToolkit)Toolkit.getDefaultToolkit();
/* 1185 */     if (uNIXToolkit.checkGtkVersion(3, 10, 0)) {
/* 1186 */       ICONS_MAP.put("OptionPane.errorIcon", new GTKStockIcon("dialog-error", 6));
/* 1187 */       ICONS_MAP.put("OptionPane.informationIcon", new GTKStockIcon("dialog-information", 6));
/* 1188 */       ICONS_MAP.put("OptionPane.warningIcon", new GTKStockIcon("dialog-warning", 6));
/* 1189 */       ICONS_MAP.put("OptionPane.questionIcon", new GTKStockIcon("dialog-question", 6));
/*      */     } else {
/* 1191 */       ICONS_MAP.put("OptionPane.errorIcon", new GTKStockIcon("gtk-dialog-error", 6));
/* 1192 */       ICONS_MAP.put("OptionPane.informationIcon", new GTKStockIcon("gtk-dialog-info", 6));
/* 1193 */       ICONS_MAP.put("OptionPane.warningIcon", new GTKStockIcon("gtk-dialog-warning", 6));
/* 1194 */       ICONS_MAP.put("OptionPane.questionIcon", new GTKStockIcon("gtk-dialog-question", 6));
/*      */     } 
/*      */   }
/*      */   
/*      */   private final Font font;
/*      */   private final int widgetType;
/*      */   private final int xThickness;
/*      */   private final int yThickness;
/*      */   
/*      */   private static native int nativeGetXThickness(int paramInt);
/*      */   
/*      */   private static native int nativeGetYThickness(int paramInt);
/*      */   
/*      */   private static native int nativeGetColorForState(int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */   private static native Object nativeGetClassValue(int paramInt, String paramString);
/*      */   
/*      */   private static native String nativeGetPangoFontName(int paramInt);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */