/*      */ package com.sun.java.swing.plaf.gtk;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JSlider;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.border.AbstractBorder;
/*      */ import javax.swing.plaf.LabelUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.synth.ColorType;
/*      */ import javax.swing.plaf.synth.Region;
/*      */ import javax.swing.plaf.synth.SynthContext;
/*      */ import javax.swing.plaf.synth.SynthLookAndFeel;
/*      */ import javax.swing.plaf.synth.SynthPainter;
/*      */ import javax.swing.plaf.synth.SynthStyle;
/*      */ import javax.swing.plaf.synth.SynthUI;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.UNIXToolkit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class GTKPainter
/*      */   extends SynthPainter
/*      */ {
/*   59 */   private static final GTKConstants.PositionType[] POSITIONS = new GTKConstants.PositionType[] { GTKConstants.PositionType.BOTTOM, GTKConstants.PositionType.RIGHT, GTKConstants.PositionType.TOP, GTKConstants.PositionType.LEFT };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   private static final GTKConstants.ShadowType[] SHADOWS = new GTKConstants.ShadowType[] { GTKConstants.ShadowType.NONE, GTKConstants.ShadowType.IN, GTKConstants.ShadowType.OUT, GTKConstants.ShadowType.ETCHED_IN, GTKConstants.ShadowType.OUT };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   private static final GTKEngine ENGINE = GTKEngine.INSTANCE;
/*   70 */   static final GTKPainter INSTANCE = new GTKPainter();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getName(SynthContext paramSynthContext) {
/*   76 */     return paramSynthContext.getRegion().isSubregion() ? null : paramSynthContext
/*   77 */       .getComponent().getName();
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintCheckBoxBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*   82 */     paintRadioButtonBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintCheckBoxMenuItemBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*   87 */     paintRadioButtonMenuItemBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintFormattedTextFieldBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*   94 */     paintTextBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintToolBarDragWindowBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  103 */     paintToolBarBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintToolBarBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  113 */     Region region = paramSynthContext.getRegion();
/*  114 */     int i = paramSynthContext.getComponentState();
/*  115 */     int j = GTKLookAndFeel.synthStateToGTKState(region, i);
/*  116 */     int k = ((JToolBar)paramSynthContext.getComponent()).getOrientation();
/*  117 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  118 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  119 */             Integer.valueOf(i), Integer.valueOf(k) })) {
/*      */         
/*  121 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), Integer.valueOf(k) });
/*  122 */         ENGINE.paintBox(paramGraphics, paramSynthContext, region, j, GTKConstants.ShadowType.OUT, "handlebox_bin", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         
/*  124 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintToolBarContentBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  132 */     Region region = paramSynthContext.getRegion();
/*  133 */     int i = ((JToolBar)paramSynthContext.getComponent()).getOrientation();
/*  134 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  135 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) })) {
/*  136 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) });
/*  137 */         ENGINE.paintBox(paramGraphics, paramSynthContext, region, 1, GTKConstants.ShadowType.OUT, "toolbar", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         
/*  139 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintPasswordFieldBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  150 */     paintTextBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTextFieldBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  158 */     if (getName(paramSynthContext) == "Tree.cellEditor") {
/*  159 */       paintTreeCellEditorBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } else {
/*  161 */       paintTextBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintRadioButtonBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  172 */     Region region = paramSynthContext.getRegion();
/*  173 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  174 */         .getComponentState());
/*  175 */     if (i == 2) {
/*  176 */       synchronized (UNIXToolkit.GTK_LOCK) {
/*  177 */         if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region })) {
/*  178 */           ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region });
/*  179 */           ENGINE.paintFlatBox(paramGraphics, paramSynthContext, region, 2, GTKConstants.ShadowType.ETCHED_OUT, "checkbutton", paramInt1, paramInt2, paramInt3, paramInt4, ColorType.BACKGROUND);
/*      */ 
/*      */           
/*  182 */           ENGINE.finishPainting();
/*      */         } 
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
/*      */   public void paintRadioButtonMenuItemBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  195 */     Region region = paramSynthContext.getRegion();
/*  196 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  197 */         .getComponentState());
/*  198 */     if (i == 2) {
/*  199 */       synchronized (UNIXToolkit.GTK_LOCK) {
/*  200 */         if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region })) {
/*  201 */           GTKConstants.ShadowType shadowType = GTKLookAndFeel.is2_2() ? GTKConstants.ShadowType.NONE : GTKConstants.ShadowType.OUT;
/*      */           
/*  203 */           ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region });
/*  204 */           ENGINE.paintBox(paramGraphics, paramSynthContext, region, i, shadowType, "menuitem", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */           
/*  206 */           ENGINE.finishPainting();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintLabelBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  218 */     String str = getName(paramSynthContext);
/*  219 */     JComponent jComponent = paramSynthContext.getComponent();
/*  220 */     Container container = jComponent.getParent();
/*      */     
/*  222 */     if (str == "TableHeader.renderer" || str == "GTKFileChooser.directoryListLabel" || str == "GTKFileChooser.fileListLabel") {
/*      */ 
/*      */ 
/*      */       
/*  226 */       paintButtonBackgroundImpl(paramSynthContext, paramGraphics, Region.BUTTON, "button", paramInt1, paramInt2, paramInt3, paramInt4, true, false, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  234 */     else if (jComponent instanceof javax.swing.ListCellRenderer && container != null && container
/*      */       
/*  236 */       .getParent() instanceof javax.swing.JComboBox) {
/*  237 */       paintTextBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintInternalFrameBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  247 */     Metacity.INSTANCE.paintFrameBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintDesktopPaneBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  257 */     fillArea(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, ColorType.BACKGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintDesktopIconBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  266 */     Metacity.INSTANCE.paintFrameBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintButtonBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  271 */     String str = getName(paramSynthContext);
/*  272 */     if (str != null && str.startsWith("InternalFrameTitlePane.")) {
/*  273 */       Metacity.INSTANCE.paintButtonBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } else {
/*      */       
/*  276 */       AbstractButton abstractButton = (AbstractButton)paramSynthContext.getComponent();
/*      */       
/*  278 */       boolean bool1 = (abstractButton.isContentAreaFilled() && abstractButton.isBorderPainted()) ? true : false;
/*  279 */       boolean bool2 = abstractButton.isFocusPainted();
/*      */       
/*  281 */       boolean bool3 = (abstractButton instanceof JButton && ((JButton)abstractButton).isDefaultCapable()) ? true : false;
/*  282 */       boolean bool4 = abstractButton.getParent() instanceof JToolBar;
/*  283 */       paintButtonBackgroundImpl(paramSynthContext, paramGraphics, Region.BUTTON, "button", paramInt1, paramInt2, paramInt3, paramInt4, bool1, bool2, bool3, bool4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void paintButtonBackgroundImpl(SynthContext paramSynthContext, Graphics paramGraphics, Region paramRegion, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
/*  292 */     int i = paramSynthContext.getComponentState();
/*  293 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  294 */       boolean bool1; if (ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { paramRegion, Integer.valueOf(i), paramString, 
/*  295 */             Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2), Boolean.valueOf(paramBoolean3), Boolean.valueOf(paramBoolean4) })) {
/*      */         return;
/*      */       }
/*  298 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { paramRegion, Integer.valueOf(i), paramString, 
/*  299 */             Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2), Boolean.valueOf(paramBoolean3), Boolean.valueOf(paramBoolean4) });
/*      */ 
/*      */       
/*  302 */       GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*  303 */       if (paramBoolean3 && !paramBoolean4) {
/*  304 */         Insets insets = gTKStyle.getClassSpecificInsetsValue(paramSynthContext, "default-border", GTKStyle.BUTTON_DEFAULT_BORDER_INSETS);
/*      */ 
/*      */ 
/*      */         
/*  308 */         if (paramBoolean1 && (i & 0x400) != 0) {
/*  309 */           ENGINE.paintBox(paramGraphics, paramSynthContext, paramRegion, 1, GTKConstants.ShadowType.IN, "buttondefault", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         }
/*      */         
/*  312 */         paramInt1 += insets.left;
/*  313 */         paramInt2 += insets.top;
/*  314 */         paramInt3 -= insets.left + insets.right;
/*  315 */         paramInt4 -= insets.top + insets.bottom;
/*      */       } 
/*      */       
/*  318 */       boolean bool = gTKStyle.getClassSpecificBoolValue(paramSynthContext, "interior-focus", true);
/*      */       
/*  320 */       int j = gTKStyle.getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*      */       
/*  322 */       int k = gTKStyle.getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*      */ 
/*      */       
/*  325 */       int m = j + k;
/*  326 */       int n = gTKStyle.getXThickness();
/*  327 */       int i1 = gTKStyle.getYThickness();
/*      */ 
/*      */       
/*  330 */       if (!bool && (i & 0x100) == 256) {
/*      */         
/*  332 */         paramInt1 += m;
/*  333 */         paramInt2 += m;
/*  334 */         paramInt3 -= 2 * m;
/*  335 */         paramInt4 -= 2 * m;
/*      */       } 
/*      */       
/*  338 */       int i2 = GTKLookAndFeel.synthStateToGTKState(paramRegion, i);
/*      */       
/*  340 */       if (paramBoolean4) {
/*      */ 
/*      */         
/*  343 */         bool1 = (i2 != 1 && i2 != 8) ? true : false;
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  349 */         bool1 = (paramBoolean1 || i2 != 1) ? true : false;
/*      */       } 
/*      */ 
/*      */       
/*  353 */       if (bool1) {
/*  354 */         GTKConstants.ShadowType shadowType = GTKConstants.ShadowType.OUT;
/*  355 */         if ((i & 0x204) != 0)
/*      */         {
/*  357 */           shadowType = GTKConstants.ShadowType.IN;
/*      */         }
/*  359 */         ENGINE.paintBox(paramGraphics, paramSynthContext, paramRegion, i2, shadowType, paramString, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  364 */       if (paramBoolean2 && (i & 0x100) != 0) {
/*  365 */         if (bool) {
/*  366 */           paramInt1 += n + k;
/*  367 */           paramInt2 += i1 + k;
/*  368 */           paramInt3 -= 2 * (n + k);
/*  369 */           paramInt4 -= 2 * (i1 + k);
/*      */         } else {
/*  371 */           paramInt1 -= m;
/*  372 */           paramInt2 -= m;
/*  373 */           paramInt3 += 2 * m;
/*  374 */           paramInt4 += 2 * m;
/*      */         } 
/*  376 */         ENGINE.paintFocus(paramGraphics, paramSynthContext, paramRegion, i2, paramString, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       } 
/*  378 */       ENGINE.finishPainting();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintArrowButtonForeground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  388 */     Region region = paramSynthContext.getRegion();
/*  389 */     JComponent jComponent = paramSynthContext.getComponent();
/*  390 */     String str1 = jComponent.getName();
/*      */     
/*  392 */     GTKConstants.ArrowType arrowType = null;
/*  393 */     switch (paramInt5) {
/*      */       case 1:
/*  395 */         arrowType = GTKConstants.ArrowType.UP; break;
/*      */       case 5:
/*  397 */         arrowType = GTKConstants.ArrowType.DOWN; break;
/*      */       case 3:
/*  399 */         arrowType = GTKConstants.ArrowType.RIGHT; break;
/*      */       case 7:
/*  401 */         arrowType = GTKConstants.ArrowType.LEFT;
/*      */         break;
/*      */     } 
/*  404 */     String str2 = "arrow";
/*  405 */     if (str1 == "ScrollBar.button" || str1 == "TabbedPane.button")
/*  406 */     { if (arrowType == GTKConstants.ArrowType.UP || arrowType == GTKConstants.ArrowType.DOWN) {
/*  407 */         str2 = "vscrollbar";
/*      */       } else {
/*  409 */         str2 = "hscrollbar";
/*      */       }  }
/*  411 */     else if (str1 == "Spinner.nextButton" || str1 == "Spinner.previousButton")
/*      */     
/*  413 */     { str2 = "spinbutton"; }
/*  414 */     else if (str1 != "ComboBox.arrowButton" && 
/*  415 */       !$assertionsDisabled) { throw new AssertionError("unexpected name: " + str1); }
/*      */ 
/*      */     
/*  418 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  419 */         .getComponentState());
/*  420 */     GTKConstants.ShadowType shadowType = (i == 4) ? GTKConstants.ShadowType.IN : GTKConstants.ShadowType.OUT;
/*      */     
/*  422 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  423 */       if (ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] {
/*  424 */             Integer.valueOf(i), str1, Integer.valueOf(paramInt5)
/*      */           }))
/*      */         return; 
/*  427 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { Integer.valueOf(i), str1, Integer.valueOf(paramInt5) });
/*  428 */       ENGINE.paintArrow(paramGraphics, paramSynthContext, region, i, shadowType, arrowType, str2, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/*  430 */       ENGINE.finishPainting();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintArrowButtonBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  436 */     Region region = paramSynthContext.getRegion();
/*  437 */     AbstractButton abstractButton = (AbstractButton)paramSynthContext.getComponent();
/*      */     
/*  439 */     String str1 = abstractButton.getName();
/*  440 */     String str2 = "button";
/*  441 */     boolean bool = false;
/*  442 */     if (str1 == "ScrollBar.button" || str1 == "TabbedPane.button")
/*      */     
/*  444 */     { Integer integer = (Integer)abstractButton.getClientProperty("__arrow_direction__");
/*      */       
/*  446 */       bool = (integer != null) ? integer.intValue() : true;
/*  447 */       switch (bool) {
/*      */ 
/*      */         
/*      */         default:
/*  451 */           str2 = "hscrollbar";
/*      */           break;
/*      */         case true:
/*      */         case true:
/*  455 */           str2 = "vscrollbar";
/*      */           break;
/*      */       }  }
/*  458 */     else if (str1 == "Spinner.previousButton")
/*  459 */     { str2 = "spinbutton_down"; }
/*  460 */     else if (str1 == "Spinner.nextButton")
/*  461 */     { str2 = "spinbutton_up"; }
/*  462 */     else if (str1 != "ComboBox.arrowButton" && 
/*  463 */       !$assertionsDisabled) { throw new AssertionError("unexpected name: " + str1); }
/*      */ 
/*      */     
/*  466 */     int i = paramSynthContext.getComponentState();
/*  467 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  468 */       if (ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  469 */             Integer.valueOf(i), str2, Integer.valueOf(bool) })) {
/*      */         return;
/*      */       }
/*      */       
/*  473 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  474 */             Integer.valueOf(i), str2, Integer.valueOf(bool) });
/*      */       
/*  476 */       if (str2.startsWith("spin")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  484 */         boolean bool1 = abstractButton.getParent().isEnabled() ? true : true;
/*      */         
/*  486 */         int k = (str2 == "spinbutton_up") ? paramInt2 : (paramInt2 - paramInt4);
/*  487 */         int m = paramInt4 * 2;
/*  488 */         ENGINE.paintBox(paramGraphics, paramSynthContext, region, bool1, GTKConstants.ShadowType.IN, "spinbutton", paramInt1, k, paramInt3, m);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  493 */       int j = GTKLookAndFeel.synthStateToGTKState(region, i);
/*  494 */       GTKConstants.ShadowType shadowType = GTKConstants.ShadowType.OUT;
/*  495 */       if ((j & 0x204) != 0)
/*      */       {
/*      */         
/*  498 */         shadowType = GTKConstants.ShadowType.IN;
/*      */       }
/*  500 */       ENGINE.paintBox(paramGraphics, paramSynthContext, region, j, shadowType, str2, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*      */ 
/*      */       
/*  504 */       ENGINE.finishPainting();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintListBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  515 */     fillArea(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, GTKColorType.TEXT_BACKGROUND);
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintMenuBarBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  520 */     Region region = paramSynthContext.getRegion();
/*  521 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  522 */       if (ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region })) {
/*      */         return;
/*      */       }
/*  525 */       GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*  526 */       int i = gTKStyle.getClassSpecificIntValue(paramSynthContext, "shadow-type", 2);
/*      */       
/*  528 */       GTKConstants.ShadowType shadowType = SHADOWS[i];
/*  529 */       int j = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  530 */           .getComponentState());
/*  531 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region });
/*  532 */       ENGINE.paintBox(paramGraphics, paramSynthContext, region, j, shadowType, "menubar", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/*  534 */       ENGINE.finishPainting();
/*      */     } 
/*      */   }
/*      */   
/*      */   private int getBrightness(Color paramColor) {
/*  539 */     return Math.max(paramColor.getRed(), Math.max(paramColor.getGreen(), paramColor.getBlue()));
/*      */   }
/*      */   
/*      */   private int getMaxColorDiff(Color paramColor1, Color paramColor2) {
/*  543 */     return Math.max(Math.abs(paramColor1.getRed() - paramColor2.getRed()), 
/*  544 */         Math.max(Math.abs(paramColor1.getGreen() - paramColor2.getGreen()), 
/*  545 */           Math.abs(paramColor1.getBlue() - paramColor2.getBlue())));
/*      */   }
/*      */   
/*      */   private int scaleColorComponent(int paramInt, double paramDouble) {
/*  549 */     return (int)(paramInt + paramInt * paramDouble);
/*      */   } private Color deriveColor(Color paramColor, int paramInt1, int paramInt2) {
/*      */     int i;
/*      */     int j;
/*      */     int k;
/*  554 */     if (paramInt1 == 0) {
/*  555 */       i = j = k = paramInt2;
/*      */     } else {
/*  557 */       double d = ((paramInt2 - paramInt1) / paramInt1);
/*      */       
/*  559 */       i = scaleColorComponent(paramColor.getRed(), d);
/*  560 */       j = scaleColorComponent(paramColor.getGreen(), d);
/*  561 */       k = scaleColorComponent(paramColor.getBlue(), d);
/*      */     } 
/*  563 */     return new Color(i, j, k);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintMenuBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  572 */     paintMenuItemBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintMenuItemBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  579 */     int i = GTKLookAndFeel.synthStateToGTKState(paramSynthContext
/*  580 */         .getRegion(), paramSynthContext.getComponentState());
/*  581 */     if (i == 2) {
/*  582 */       if (GTKLookAndFeel.is3() && paramSynthContext.getRegion() == Region.MENU) {
/*  583 */         GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*  584 */         Color color1 = gTKStyle.getGTKColor(GTKEngine.WidgetType.MENU_ITEM
/*  585 */             .ordinal(), i, ColorType.BACKGROUND
/*  586 */             .getID());
/*  587 */         Color color2 = gTKStyle.getGTKColor(GTKEngine.WidgetType.MENU_BAR
/*  588 */             .ordinal(), 1, ColorType.BACKGROUND
/*  589 */             .getID());
/*      */         
/*  591 */         byte b1 = 0; char c = 'ÿ';
/*  592 */         byte b2 = 100;
/*      */         
/*  594 */         int j = getMaxColorDiff(color1, color2);
/*  595 */         if (j < b2) {
/*      */           
/*  597 */           int k = getBrightness(color1);
/*      */           
/*  599 */           int m = getBrightness(color2);
/*  600 */           int n = k;
/*      */           
/*  602 */           if (k >= m) {
/*  603 */             if (m + b2 <= c)
/*      */             {
/*  605 */               k = m + b2;
/*      */             }
/*      */             else
/*      */             {
/*  609 */               k = m - b2;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  614 */           else if (m - b2 >= b1) {
/*      */             
/*  616 */             k = m - b2;
/*      */           }
/*      */           else {
/*      */             
/*  620 */             k = m + b2;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  626 */           paramGraphics.setColor(deriveColor(color1, n, k));
/*      */ 
/*      */           
/*  629 */           paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */           return;
/*      */         } 
/*      */       } 
/*  633 */       Region region = Region.MENU_ITEM;
/*  634 */       synchronized (UNIXToolkit.GTK_LOCK) {
/*  635 */         if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region })) {
/*  636 */           GTKConstants.ShadowType shadowType = GTKLookAndFeel.is2_2() ? GTKConstants.ShadowType.NONE : GTKConstants.ShadowType.OUT;
/*      */           
/*  638 */           ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region });
/*  639 */           ENGINE.paintBox(paramGraphics, paramSynthContext, region, i, shadowType, "menuitem", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */           
/*  641 */           ENGINE.finishPainting();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintPopupMenuBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  649 */     Region region = paramSynthContext.getRegion();
/*  650 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  651 */         .getComponentState());
/*  652 */     boolean bool = SunToolkit.getHeavyweightComponent(paramSynthContext
/*  653 */         .getComponent()) instanceof sun.awt.ModalExclude;
/*  654 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  655 */       if (ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), Boolean.valueOf(bool) })) {
/*      */         return;
/*      */       }
/*  658 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) });
/*  659 */       ENGINE.paintBox(paramGraphics, paramSynthContext, region, i, GTKConstants.ShadowType.OUT, "menu", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*      */       
/*  662 */       GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*  663 */       int j = gTKStyle.getXThickness();
/*  664 */       int k = gTKStyle.getYThickness();
/*  665 */       ENGINE.paintBackground(paramGraphics, paramSynthContext, region, i, gTKStyle
/*  666 */           .getGTKColor(paramSynthContext, i, GTKColorType.BACKGROUND), paramInt1 + j, paramInt2 + k, paramInt3 - j - j, paramInt4 - k - k);
/*      */ 
/*      */       
/*  669 */       BufferedImage bufferedImage = ENGINE.finishPainting();
/*  670 */       if (!bool) {
/*  671 */         int m = bufferedImage.getRGB(0, paramInt4 / 2);
/*  672 */         if (bufferedImage != null && m == bufferedImage.getRGB(paramInt3 / 2, paramInt4 / 2)) {
/*      */           
/*  674 */           Graphics graphics = bufferedImage.getGraphics();
/*  675 */           Color color = new Color(m);
/*  676 */           graphics.setColor(new Color(Math.max((int)(color.getRed() * 0.8D), 0), 
/*  677 */                 Math.max((int)(color.getGreen() * 0.8D), 0), 
/*  678 */                 Math.max((int)(color.getBlue() * 0.8D), 0)));
/*  679 */           graphics.drawLine(0, 0, paramInt3 - 1, 0);
/*  680 */           graphics.drawLine(paramInt3 - 1, 0, paramInt3 - 1, paramInt4 - 1);
/*  681 */           graphics.drawLine(0, paramInt4 - 1, 0, 1);
/*  682 */           graphics.setColor(color.darker());
/*  683 */           graphics.drawLine(paramInt3 - 1, paramInt4 - 1, 0, paramInt4 - 1);
/*  684 */           graphics.dispose();
/*  685 */           paramGraphics.drawImage(bufferedImage, paramInt1, paramInt2, null);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintProgressBarBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  694 */     Region region = paramSynthContext.getRegion();
/*  695 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  696 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region })) {
/*  697 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region });
/*  698 */         ENGINE.paintBox(paramGraphics, paramSynthContext, region, 1, GTKConstants.ShadowType.IN, "trough", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         
/*  700 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintProgressBarForeground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  708 */     Region region = paramSynthContext.getRegion();
/*  709 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  714 */       if (paramInt3 <= 0 || paramInt4 <= 0) {
/*      */         return;
/*      */       }
/*  717 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, "fg" });
/*  718 */       ENGINE.paintBox(paramGraphics, paramSynthContext, region, 2, GTKConstants.ShadowType.OUT, "bar", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/*  720 */       ENGINE.finishPainting(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintViewportBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  726 */     Region region = paramSynthContext.getRegion();
/*  727 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  728 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region })) {
/*  729 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region });
/*  730 */         ENGINE.paintShadow(paramGraphics, paramSynthContext, region, 1, GTKConstants.ShadowType.IN, "scrolled_window", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         
/*  732 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintSeparatorBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*      */     String str;
/*  741 */     Region region = paramSynthContext.getRegion();
/*  742 */     int i = paramSynthContext.getComponentState();
/*  743 */     JComponent jComponent = paramSynthContext.getComponent();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  756 */     if (jComponent instanceof JToolBar.Separator) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  779 */       str = "toolbar";
/*  780 */       float f = 0.2F;
/*  781 */       JToolBar.Separator separator = (JToolBar.Separator)jComponent;
/*  782 */       Dimension dimension = separator.getSeparatorSize();
/*  783 */       GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*  784 */       if (paramInt5 == 0) {
/*  785 */         paramInt1 += (int)(paramInt3 * f);
/*  786 */         paramInt3 -= (int)(paramInt3 * f * 2.0F);
/*  787 */         paramInt2 += (dimension.height - gTKStyle.getYThickness()) / 2;
/*      */       } else {
/*  789 */         paramInt2 += (int)(paramInt4 * f);
/*  790 */         paramInt4 -= (int)(paramInt4 * f * 2.0F);
/*  791 */         paramInt1 += (dimension.width - gTKStyle.getXThickness()) / 2;
/*      */       } 
/*      */     } else {
/*      */       
/*  795 */       str = "separator";
/*  796 */       Insets insets = jComponent.getInsets();
/*  797 */       paramInt1 += insets.left;
/*  798 */       paramInt2 += insets.top;
/*  799 */       if (paramInt5 == 0) {
/*  800 */         paramInt3 -= insets.left + insets.right;
/*      */       } else {
/*  802 */         paramInt4 -= insets.top + insets.bottom;
/*      */       } 
/*  804 */       if (GTKLookAndFeel.is3()) {
/*  805 */         if (region == Region.POPUP_MENU_SEPARATOR) {
/*  806 */           str = "menuitem";
/*  807 */           paramInt4 -= insets.top + insets.bottom;
/*      */         } else {
/*  809 */           str = "separator";
/*      */         } 
/*      */       } else {
/*  812 */         str = (paramInt5 == 0) ? "hseparator" : "vseparator";
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  817 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  818 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  819 */             Integer.valueOf(i), str, Integer.valueOf(paramInt5) })) {
/*  820 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  821 */               Integer.valueOf(i), str, Integer.valueOf(paramInt5) });
/*  822 */         if (paramInt5 == 0) {
/*  823 */           ENGINE.paintHline(paramGraphics, paramSynthContext, region, i, str, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         } else {
/*      */           
/*  826 */           ENGINE.paintVline(paramGraphics, paramSynthContext, region, i, str, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         } 
/*      */         
/*  829 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintSliderTrackBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  837 */     Region region = paramSynthContext.getRegion();
/*  838 */     int i = paramSynthContext.getComponentState();
/*      */ 
/*      */ 
/*      */     
/*  842 */     boolean bool = ((i & 0x100) != 0) ? true : false;
/*  843 */     int j = 0;
/*  844 */     if (bool) {
/*  845 */       GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*      */ 
/*      */       
/*  848 */       j = gTKStyle.getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1) + gTKStyle.getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*      */       
/*  850 */       paramInt1 -= j;
/*  851 */       paramInt2 -= j;
/*  852 */       paramInt3 += j * 2;
/*  853 */       paramInt4 += j * 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  858 */     JSlider jSlider = (JSlider)paramSynthContext.getComponent();
/*  859 */     if (GTKLookAndFeel.is3()) {
/*  860 */       if (jSlider.getOrientation() == 1) {
/*  861 */         paramInt2++;
/*  862 */         paramInt4 -= 2;
/*      */       } else {
/*  864 */         paramInt1++;
/*  865 */         paramInt3 -= 2;
/*      */       } 
/*      */     }
/*  868 */     double d1 = jSlider.getValue();
/*  869 */     double d2 = jSlider.getMinimum();
/*  870 */     double d3 = jSlider.getMaximum();
/*  871 */     double d4 = 20.0D;
/*      */     
/*  873 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  879 */       if (paramInt3 <= 0 || paramInt4 <= 0) {
/*      */         return;
/*      */       }
/*  882 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), Double.valueOf(d1) });
/*  883 */       int k = GTKLookAndFeel.synthStateToGTKState(region, i);
/*  884 */       ENGINE.setRangeValue(paramSynthContext, region, d1, d2, d3, d4);
/*  885 */       ENGINE.paintBox(paramGraphics, paramSynthContext, region, k, GTKConstants.ShadowType.IN, "trough", paramInt1 + j, paramInt2 + j, paramInt3 - 2 * j, paramInt4 - 2 * j);
/*      */ 
/*      */       
/*  888 */       if (bool) {
/*  889 */         ENGINE.paintFocus(paramGraphics, paramSynthContext, region, 1, "trough", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       }
/*      */       
/*  892 */       ENGINE.finishPainting(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintSliderThumbBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  898 */     Region region = paramSynthContext.getRegion();
/*  899 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  900 */         .getComponentState());
/*      */     
/*  902 */     boolean bool = (GTKLookAndFeel.is3() && (paramSynthContext.getComponentState() & 0x100) != 0) ? true : false;
/*  903 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  904 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), Integer.valueOf(paramInt5), Boolean.valueOf(bool) })) {
/*  905 */         GTKConstants.Orientation orientation = (paramInt5 == 0) ? GTKConstants.Orientation.HORIZONTAL : GTKConstants.Orientation.VERTICAL;
/*      */         
/*  907 */         String str = (paramInt5 == 0) ? "hscale" : "vscale";
/*      */         
/*  909 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), Integer.valueOf(paramInt5) });
/*  910 */         ENGINE.paintSlider(paramGraphics, paramSynthContext, region, i, GTKConstants.ShadowType.OUT, str, paramInt1, paramInt2, paramInt3, paramInt4, orientation, bool);
/*      */         
/*  912 */         ENGINE.finishPainting();
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
/*      */   public void paintSpinnerBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintSplitPaneDividerBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  932 */     Region region = paramSynthContext.getRegion();
/*  933 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  934 */         .getComponentState());
/*  935 */     JSplitPane jSplitPane = (JSplitPane)paramSynthContext.getComponent();
/*      */     
/*  937 */     GTKConstants.Orientation orientation = (jSplitPane.getOrientation() == 1) ? GTKConstants.Orientation.VERTICAL : GTKConstants.Orientation.HORIZONTAL;
/*      */     
/*  939 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  940 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  941 */             Integer.valueOf(i), orientation })) {
/*  942 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), orientation });
/*  943 */         ENGINE.paintHandle(paramGraphics, paramSynthContext, region, i, GTKConstants.ShadowType.OUT, "paned", paramInt1, paramInt2, paramInt3, paramInt4, orientation);
/*      */         
/*  945 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintSplitPaneDragDivider(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  953 */     paintSplitPaneDividerForeground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintTabbedPaneContentBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  958 */     JTabbedPane jTabbedPane = (JTabbedPane)paramSynthContext.getComponent();
/*  959 */     int i = jTabbedPane.getSelectedIndex();
/*  960 */     GTKConstants.PositionType positionType = GTKLookAndFeel.SwingOrientationConstantToGTK(jTabbedPane
/*  961 */         .getTabPlacement());
/*      */     
/*  963 */     int j = 0;
/*  964 */     int k = 0;
/*  965 */     if (i != -1) {
/*  966 */       Rectangle rectangle = jTabbedPane.getBoundsAt(i);
/*      */       
/*  968 */       if (positionType == GTKConstants.PositionType.TOP || positionType == GTKConstants.PositionType.BOTTOM) {
/*      */ 
/*      */         
/*  971 */         j = rectangle.x - paramInt1;
/*  972 */         k = rectangle.width;
/*      */       } else {
/*      */         
/*  975 */         j = rectangle.y - paramInt2;
/*  976 */         k = rectangle.height;
/*      */       } 
/*      */     } 
/*      */     
/*  980 */     Region region = paramSynthContext.getRegion();
/*  981 */     int m = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/*  982 */         .getComponentState());
/*  983 */     synchronized (UNIXToolkit.GTK_LOCK) {
/*  984 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  985 */             Integer.valueOf(m), positionType, Integer.valueOf(j), Integer.valueOf(k) })) {
/*  986 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/*  987 */               Integer.valueOf(m), positionType, Integer.valueOf(j), Integer.valueOf(k) });
/*  988 */         ENGINE.paintBoxGap(paramGraphics, paramSynthContext, region, m, GTKConstants.ShadowType.OUT, "notebook", paramInt1, paramInt2, paramInt3, paramInt4, positionType, j, k);
/*      */         
/*  990 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTabbedPaneTabBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  999 */     Region region = paramSynthContext.getRegion();
/* 1000 */     int i = paramSynthContext.getComponentState();
/* 1001 */     boolean bool = ((i & 0x200) != 0) ? true : true;
/*      */     
/* 1003 */     JTabbedPane jTabbedPane = (JTabbedPane)paramSynthContext.getComponent();
/* 1004 */     int j = jTabbedPane.getTabPlacement();
/*      */     
/* 1006 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1007 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/* 1008 */             Integer.valueOf(bool), Integer.valueOf(j), Integer.valueOf(paramInt5) })) {
/* 1009 */         GTKConstants.PositionType positionType = POSITIONS[j - 1];
/* 1010 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, 
/* 1011 */               Integer.valueOf(bool), Integer.valueOf(j), Integer.valueOf(paramInt5) });
/* 1012 */         ENGINE.paintExtension(paramGraphics, paramSynthContext, region, bool, GTKConstants.ShadowType.OUT, "tab", paramInt1, paramInt2, paramInt3, paramInt4, positionType, paramInt5);
/*      */         
/* 1014 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTextPaneBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1024 */     paintTextAreaBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintEditorPaneBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1032 */     paintTextAreaBackground(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTextAreaBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1041 */     fillArea(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, GTKColorType.TEXT_BACKGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void paintTextBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1052 */     JComponent jComponent = paramSynthContext.getComponent();
/* 1053 */     Container container1 = jComponent.getParent();
/* 1054 */     Container container2 = null;
/* 1055 */     GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/* 1056 */     Region region = paramSynthContext.getRegion();
/* 1057 */     int i = paramSynthContext.getComponentState();
/*      */     
/* 1059 */     if (jComponent instanceof javax.swing.ListCellRenderer && container1 != null) {
/* 1060 */       container2 = container1.getParent();
/* 1061 */       if (container2 instanceof javax.swing.JComboBox && container2
/* 1062 */         .hasFocus()) {
/* 1063 */         i |= 0x100;
/*      */       }
/*      */     } 
/*      */     
/* 1067 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1068 */       if (ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) })) {
/*      */         return;
/*      */       }
/*      */       
/* 1072 */       int j = GTKLookAndFeel.synthStateToGTKState(region, i);
/* 1073 */       int k = 0;
/* 1074 */       boolean bool = gTKStyle.getClassSpecificBoolValue(paramSynthContext, "interior-focus", true);
/*      */ 
/*      */       
/* 1077 */       k = gTKStyle.getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*      */       
/* 1079 */       if (!bool && (i & 0x100) != 0) {
/* 1080 */         paramInt1 += k;
/* 1081 */         paramInt2 += k;
/* 1082 */         paramInt3 -= 2 * k;
/* 1083 */         paramInt4 -= 2 * k;
/*      */       } 
/*      */       
/* 1086 */       int m = gTKStyle.getXThickness();
/* 1087 */       int n = gTKStyle.getYThickness();
/*      */       
/* 1089 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) });
/* 1090 */       if (GTKLookAndFeel.is3()) {
/* 1091 */         ENGINE.paintBackground(paramGraphics, paramSynthContext, region, j, null, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       }
/*      */       
/* 1094 */       ENGINE.paintShadow(paramGraphics, paramSynthContext, region, j, GTKConstants.ShadowType.IN, "entry", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/* 1096 */       if (!GTKLookAndFeel.is3()) {
/* 1097 */         ENGINE.paintFlatBox(paramGraphics, paramSynthContext, region, j, GTKConstants.ShadowType.NONE, "entry_bg", paramInt1 + m, paramInt2 + n, paramInt3 - 2 * m, paramInt4 - 2 * n, ColorType.TEXT_BACKGROUND);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1106 */       if (k > 0 && (i & 0x100) != 0) {
/* 1107 */         if (!bool) {
/* 1108 */           paramInt1 -= k;
/* 1109 */           paramInt2 -= k;
/* 1110 */           paramInt3 += 2 * k;
/* 1111 */           paramInt4 += 2 * k;
/*      */         }
/* 1113 */         else if (container2 instanceof javax.swing.JComboBox) {
/* 1114 */           paramInt1 += k + 2;
/* 1115 */           paramInt2 += k + (GTKLookAndFeel.is3() ? 3 : 1);
/* 1116 */           paramInt3 -= 2 * k + (GTKLookAndFeel.is3() ? 4 : 1);
/* 1117 */           paramInt4 -= 2 * k + (GTKLookAndFeel.is3() ? 6 : 2);
/*      */         } else {
/* 1119 */           paramInt1 += k + (GTKLookAndFeel.is3() ? 2 : 0);
/* 1120 */           paramInt2 += k + (GTKLookAndFeel.is3() ? 2 : 0);
/* 1121 */           paramInt3 -= 2 * k + (GTKLookAndFeel.is3() ? 4 : 0);
/* 1122 */           paramInt4 -= 2 * k + (GTKLookAndFeel.is3() ? 4 : 0);
/*      */         } 
/*      */         
/* 1125 */         ENGINE.paintFocus(paramGraphics, paramSynthContext, region, j, "entry", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       } 
/*      */       
/* 1128 */       ENGINE.finishPainting();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintTreeCellEditorBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1134 */     Region region = paramSynthContext.getRegion();
/* 1135 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/* 1136 */         .getComponentState());
/* 1137 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1138 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) })) {
/* 1139 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) });
/* 1140 */         ENGINE.paintFlatBox(paramGraphics, paramSynthContext, region, i, GTKConstants.ShadowType.NONE, "entry_bg", paramInt1, paramInt2, paramInt3, paramInt4, ColorType.TEXT_BACKGROUND);
/*      */         
/* 1142 */         ENGINE.finishPainting();
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
/*      */   public void paintRootPaneBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1154 */     fillArea(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, GTKColorType.BACKGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintToggleButtonBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1163 */     Region region = paramSynthContext.getRegion();
/* 1164 */     JToggleButton jToggleButton = (JToggleButton)paramSynthContext.getComponent();
/*      */     
/* 1166 */     boolean bool = (jToggleButton.isContentAreaFilled() && jToggleButton.isBorderPainted()) ? true : false;
/* 1167 */     boolean bool1 = jToggleButton.isFocusPainted();
/* 1168 */     boolean bool2 = jToggleButton.getParent() instanceof JToolBar;
/* 1169 */     paintButtonBackgroundImpl(paramSynthContext, paramGraphics, region, "button", paramInt1, paramInt2, paramInt3, paramInt4, bool, bool1, false, bool2);
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
/*      */   public void paintScrollBarBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1181 */     Region region = paramSynthContext.getRegion();
/*      */     
/* 1183 */     boolean bool = ((paramSynthContext.getComponentState() & 0x100) != 0) ? true : false;
/* 1184 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1185 */       if (ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Boolean.valueOf(bool) })) {
/*      */         return;
/*      */       }
/* 1188 */       ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Boolean.valueOf(bool) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1195 */       Insets insets = paramSynthContext.getComponent().getInsets();
/* 1196 */       GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*      */       
/* 1198 */       int i = gTKStyle.getClassSpecificIntValue(paramSynthContext, "trough-border", 1);
/* 1199 */       insets.left -= i;
/* 1200 */       insets.right -= i;
/* 1201 */       insets.top -= i;
/* 1202 */       insets.bottom -= i;
/*      */       
/* 1204 */       ENGINE.paintBox(paramGraphics, paramSynthContext, region, 4, GTKConstants.ShadowType.IN, "trough", paramInt1 + insets.left, paramInt2 + insets.top, paramInt3 - insets.left - insets.right, paramInt4 - insets.top - insets.bottom);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1211 */       if (bool) {
/* 1212 */         ENGINE.paintFocus(paramGraphics, paramSynthContext, region, 1, "trough", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       }
/*      */       
/* 1215 */       ENGINE.finishPainting();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintScrollBarThumbBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*      */     double d4;
/* 1225 */     Region region = paramSynthContext.getRegion();
/* 1226 */     int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/* 1227 */         .getComponentState());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1238 */     JScrollBar jScrollBar = (JScrollBar)paramSynthContext.getComponent();
/*      */ 
/*      */     
/* 1241 */     boolean bool = (jScrollBar.getOrientation() == 0 && !jScrollBar.getComponentOrientation().isLeftToRight()) ? true : false;
/* 1242 */     double d1 = 0.0D;
/* 1243 */     double d2 = 100.0D;
/* 1244 */     double d3 = 20.0D;
/*      */     
/* 1246 */     if (jScrollBar.getMaximum() - jScrollBar.getMinimum() == jScrollBar.getVisibleAmount()) {
/*      */ 
/*      */       
/* 1249 */       d4 = 0.0D;
/* 1250 */       d3 = 100.0D;
/* 1251 */     } else if (jScrollBar.getValue() == jScrollBar.getMinimum()) {
/*      */       
/* 1253 */       d4 = bool ? 100.0D : 0.0D;
/* 1254 */     } else if (jScrollBar.getValue() >= jScrollBar.getMaximum() - jScrollBar.getVisibleAmount()) {
/*      */       
/* 1256 */       d4 = bool ? 0.0D : 100.0D;
/*      */     } else {
/*      */       
/* 1259 */       d4 = 50.0D;
/*      */     } 
/*      */     
/* 1262 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1263 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), 
/* 1264 */             Integer.valueOf(paramInt5), Double.valueOf(d4), Double.valueOf(d3), Boolean.valueOf(bool) })) {
/*      */         
/* 1266 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i), 
/* 1267 */               Integer.valueOf(paramInt5), Double.valueOf(d4), Double.valueOf(d3), Boolean.valueOf(bool) });
/* 1268 */         GTKConstants.Orientation orientation = (paramInt5 == 0) ? GTKConstants.Orientation.HORIZONTAL : GTKConstants.Orientation.VERTICAL;
/*      */         
/* 1270 */         ENGINE.setRangeValue(paramSynthContext, region, d4, d1, d2, d3);
/* 1271 */         ENGINE.paintSlider(paramGraphics, paramSynthContext, region, i, GTKConstants.ShadowType.OUT, "slider", paramInt1, paramInt2, paramInt3, paramInt4, orientation, false);
/*      */         
/* 1273 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintToolTipBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1283 */     Region region = paramSynthContext.getRegion();
/* 1284 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1285 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region })) {
/* 1286 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region });
/* 1287 */         ENGINE.paintFlatBox(paramGraphics, paramSynthContext, region, 1, GTKConstants.ShadowType.OUT, "tooltip", paramInt1, paramInt2, paramInt3, paramInt4, ColorType.BACKGROUND);
/*      */ 
/*      */         
/* 1290 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTreeCellBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1301 */     Region region = paramSynthContext.getRegion();
/* 1302 */     int i = paramSynthContext.getComponentState();
/* 1303 */     int j = GTKLookAndFeel.synthStateToGTKState(region, i);
/* 1304 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1305 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) })) {
/* 1306 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { region, Integer.valueOf(i) });
/*      */ 
/*      */         
/* 1309 */         ENGINE.paintFlatBox(paramGraphics, paramSynthContext, region, j, GTKConstants.ShadowType.NONE, "cell_odd", paramInt1, paramInt2, paramInt3, paramInt4, ColorType.TEXT_BACKGROUND);
/*      */         
/* 1311 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintTreeCellFocus(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1318 */     Region region = Region.TREE_CELL;
/* 1319 */     int i = paramSynthContext.getComponentState();
/* 1320 */     paintFocus(paramSynthContext, paramGraphics, region, i, "treeview", paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTreeBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1330 */     fillArea(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, GTKColorType.TEXT_BACKGROUND);
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
/*      */   public void paintViewportBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1343 */     fillArea(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, GTKColorType.TEXT_BACKGROUND);
/*      */   }
/*      */ 
/*      */   
/*      */   void paintFocus(SynthContext paramSynthContext, Graphics paramGraphics, Region paramRegion, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1348 */     int i = GTKLookAndFeel.synthStateToGTKState(paramRegion, paramInt1);
/* 1349 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1350 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt2, paramInt3, paramInt4, paramInt5, new Object[] { paramRegion, Integer.valueOf(i), "focus" })) {
/* 1351 */         ENGINE.startPainting(paramGraphics, paramInt2, paramInt3, paramInt4, paramInt5, new Object[] { paramRegion, Integer.valueOf(i), "focus" });
/* 1352 */         ENGINE.paintFocus(paramGraphics, paramSynthContext, paramRegion, i, paramString, paramInt2, paramInt3, paramInt4, paramInt5);
/* 1353 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void paintMetacityElement(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, GTKConstants.ShadowType paramShadowType, GTKConstants.ArrowType paramArrowType) {
/* 1361 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1362 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt2, paramInt3, paramInt4, paramInt5, new Object[] {
/* 1363 */             Integer.valueOf(paramInt1), paramString, paramShadowType, paramArrowType })) {
/* 1364 */         ENGINE.startPainting(paramGraphics, paramInt2, paramInt3, paramInt4, paramInt5, new Object[] {
/* 1365 */               Integer.valueOf(paramInt1), paramString, paramShadowType, paramArrowType });
/* 1366 */         if (paramString == "metacity-arrow") {
/* 1367 */           ENGINE.paintArrow(paramGraphics, paramSynthContext, Region.INTERNAL_FRAME_TITLE_PANE, paramInt1, paramShadowType, paramArrowType, "", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */         
/*      */         }
/* 1370 */         else if (paramString == "metacity-box") {
/* 1371 */           ENGINE.paintBox(paramGraphics, paramSynthContext, Region.INTERNAL_FRAME_TITLE_PANE, paramInt1, paramShadowType, "", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */         
/*      */         }
/* 1374 */         else if (paramString == "metacity-vline") {
/* 1375 */           ENGINE.paintVline(paramGraphics, paramSynthContext, Region.INTERNAL_FRAME_TITLE_PANE, paramInt1, "", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */         } 
/*      */         
/* 1378 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void paintIcon(SynthContext paramSynthContext, Graphics paramGraphics, Method paramMethod, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1385 */     int i = paramSynthContext.getComponentState();
/* 1386 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1387 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { Integer.valueOf(i), paramMethod })) {
/* 1388 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { Integer.valueOf(i), paramMethod });
/*      */         try {
/* 1390 */           paramMethod.invoke(this, new Object[] { paramSynthContext, paramGraphics, Integer.valueOf(i), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
/* 1391 */         } catch (IllegalAccessException illegalAccessException) {
/*      */           assert false;
/* 1393 */         } catch (InvocationTargetException invocationTargetException) {
/*      */           assert false;
/*      */         } 
/* 1396 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void paintIcon(SynthContext paramSynthContext, Graphics paramGraphics, Method paramMethod, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/* 1403 */     int i = paramSynthContext.getComponentState();
/* 1404 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 1405 */       if (!ENGINE.paintCachedImage(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] {
/* 1406 */             Integer.valueOf(i), paramMethod, paramObject })) {
/* 1407 */         ENGINE.startPainting(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] {
/* 1408 */               Integer.valueOf(i), paramMethod, paramObject });
/*      */         try {
/* 1410 */           paramMethod.invoke(this, new Object[] { paramSynthContext, paramGraphics, 
/* 1411 */                 Integer.valueOf(i), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), paramObject });
/* 1412 */         } catch (IllegalAccessException illegalAccessException) {
/*      */           assert false;
/* 1414 */         } catch (InvocationTargetException invocationTargetException) {
/*      */           assert false;
/*      */         } 
/* 1417 */         ENGINE.finishPainting();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTreeExpandedIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1426 */     ENGINE.paintExpander(paramGraphics, paramSynthContext, Region.TREE, 
/* 1427 */         GTKLookAndFeel.synthStateToGTKState(paramSynthContext.getRegion(), paramInt1), GTKConstants.ExpanderStyle.EXPANDED, "expander", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintTreeCollapsedIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1433 */     ENGINE.paintExpander(paramGraphics, paramSynthContext, Region.TREE, 
/* 1434 */         GTKLookAndFeel.synthStateToGTKState(paramSynthContext.getRegion(), paramInt1), GTKConstants.ExpanderStyle.COLLAPSED, "expander", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintCheckBoxIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1440 */     GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/* 1441 */     int i = gTKStyle.getClassSpecificIntValue(paramSynthContext, "indicator-size", 13);
/*      */     
/* 1443 */     int j = gTKStyle.getClassSpecificIntValue(paramSynthContext, "indicator-spacing", 2);
/*      */ 
/*      */     
/* 1446 */     ENGINE.paintCheck(paramGraphics, paramSynthContext, Region.CHECK_BOX, "checkbutton", paramInt2 + j, paramInt3 + j, i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintRadioButtonIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1452 */     GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/* 1453 */     int i = gTKStyle.getClassSpecificIntValue(paramSynthContext, "indicator-size", 13);
/*      */     
/* 1455 */     int j = gTKStyle.getClassSpecificIntValue(paramSynthContext, "indicator-spacing", 2);
/*      */ 
/*      */     
/* 1458 */     ENGINE.paintOption(paramGraphics, paramSynthContext, Region.RADIO_BUTTON, "radiobutton", paramInt2 + j, paramInt3 + j, i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintMenuArrowIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, GTKConstants.ArrowType paramArrowType) {
/* 1464 */     int i = GTKLookAndFeel.synthStateToGTKState(paramSynthContext
/* 1465 */         .getRegion(), paramInt1);
/* 1466 */     GTKConstants.ShadowType shadowType = GTKConstants.ShadowType.OUT;
/* 1467 */     if (i == 2) {
/* 1468 */       shadowType = GTKConstants.ShadowType.IN;
/*      */     }
/* 1470 */     if (!GTKLookAndFeel.is3()) {
/* 1471 */       paramInt2 += 3;
/* 1472 */       paramInt3 += 3;
/* 1473 */       paramInt4 = paramInt5 = 7;
/*      */     } 
/* 1475 */     ENGINE.paintArrow(paramGraphics, paramSynthContext, Region.MENU_ITEM, i, shadowType, paramArrowType, "menuitem", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintCheckBoxMenuItemCheckIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1482 */     GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/* 1483 */     int i = gTKStyle.getClassSpecificIntValue(paramSynthContext, "indicator-size", 12);
/*      */ 
/*      */     
/* 1486 */     ENGINE.paintCheck(paramGraphics, paramSynthContext, Region.CHECK_BOX_MENU_ITEM, "check", paramInt2 + 1, paramInt3 + 1, i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintRadioButtonMenuItemCheckIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1495 */     GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/* 1496 */     int i = gTKStyle.getClassSpecificIntValue(paramSynthContext, "indicator-size", 12);
/*      */ 
/*      */     
/* 1499 */     ENGINE.paintOption(paramGraphics, paramSynthContext, Region.RADIO_BUTTON_MENU_ITEM, "option", paramInt2 + 1, paramInt3 + 1, i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintToolBarHandleIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, GTKConstants.Orientation paramOrientation) {
/* 1507 */     int i = GTKLookAndFeel.synthStateToGTKState(paramSynthContext
/* 1508 */         .getRegion(), paramInt1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1515 */     paramOrientation = (paramOrientation == GTKConstants.Orientation.HORIZONTAL) ? GTKConstants.Orientation.VERTICAL : GTKConstants.Orientation.HORIZONTAL;
/*      */ 
/*      */     
/* 1518 */     ENGINE.paintHandle(paramGraphics, paramSynthContext, Region.TOOL_BAR, i, GTKConstants.ShadowType.OUT, "handlebox", paramInt2, paramInt3, paramInt4, paramInt5, paramOrientation);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintAscendingSortIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1524 */     ENGINE.paintArrow(paramGraphics, paramSynthContext, Region.TABLE, 1, GTKConstants.ShadowType.IN, GTKConstants.ArrowType.UP, "arrow", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintDescendingSortIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1530 */     ENGINE.paintArrow(paramGraphics, paramSynthContext, Region.TABLE, 1, GTKConstants.ShadowType.IN, GTKConstants.ArrowType.DOWN, "arrow", paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fillArea(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorType paramColorType) {
/* 1540 */     if (paramSynthContext.getComponent().isOpaque()) {
/* 1541 */       Region region = paramSynthContext.getRegion();
/* 1542 */       int i = GTKLookAndFeel.synthStateToGTKState(region, paramSynthContext
/* 1543 */           .getComponentState());
/* 1544 */       GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*      */       
/* 1546 */       paramGraphics.setColor(gTKStyle.getGTKColor(paramSynthContext, i, paramColorType));
/* 1547 */       paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*      */   }
/*      */   
/*      */   static class ListTableFocusBorder
/*      */     extends AbstractBorder
/*      */     implements UIResource
/*      */   {
/*      */     private boolean selectedCell;
/*      */     private boolean focusedCell;
/*      */     
/*      */     public static ListTableFocusBorder getSelectedCellBorder() {
/* 1559 */       return new ListTableFocusBorder(true, true);
/*      */     }
/*      */     
/*      */     public static ListTableFocusBorder getUnselectedCellBorder() {
/* 1563 */       return new ListTableFocusBorder(false, true);
/*      */     }
/*      */     
/*      */     public static ListTableFocusBorder getNoFocusCellBorder() {
/* 1567 */       return new ListTableFocusBorder(false, false);
/*      */     }
/*      */     
/*      */     public ListTableFocusBorder(boolean param1Boolean1, boolean param1Boolean2) {
/* 1571 */       this.selectedCell = param1Boolean1;
/* 1572 */       this.focusedCell = param1Boolean2;
/*      */     }
/*      */     
/*      */     private SynthContext getContext(Component param1Component) {
/* 1576 */       SynthContext synthContext = null;
/*      */       
/* 1578 */       LabelUI labelUI = null;
/* 1579 */       if (param1Component instanceof JLabel) {
/* 1580 */         labelUI = ((JLabel)param1Component).getUI();
/*      */       }
/*      */       
/* 1583 */       if (labelUI instanceof SynthUI) {
/* 1584 */         synthContext = ((SynthUI)labelUI).getContext((JComponent)param1Component);
/*      */       }
/*      */       
/* 1587 */       return synthContext;
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1592 */       if (this.focusedCell) {
/* 1593 */         SynthContext synthContext = getContext(param1Component);
/* 1594 */         char c = this.selectedCell ? 'Ȁ' : 'ā';
/*      */ 
/*      */         
/* 1597 */         if (synthContext != null) {
/* 1598 */           GTKPainter.INSTANCE.paintFocus(synthContext, param1Graphics, Region.TABLE, c, "", param1Int1, param1Int2, param1Int3, param1Int4);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 1605 */       SynthContext synthContext = getContext(param1Component);
/*      */       
/* 1607 */       if (synthContext != null) {
/* 1608 */         param1Insets = synthContext.getStyle().getInsets(synthContext, param1Insets);
/*      */       }
/*      */       
/* 1611 */       return param1Insets;
/*      */     }
/*      */     
/*      */     public boolean isBorderOpaque() {
/* 1615 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class TitledBorder
/*      */     extends AbstractBorder
/*      */     implements UIResource
/*      */   {
/*      */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1624 */       SynthContext synthContext = getContext((JComponent)param1Component);
/* 1625 */       Region region = synthContext.getRegion();
/* 1626 */       int i = synthContext.getComponentState();
/* 1627 */       int j = GTKLookAndFeel.synthStateToGTKState(region, i);
/*      */       
/* 1629 */       synchronized (UNIXToolkit.GTK_LOCK) {
/* 1630 */         if (!GTKPainter.ENGINE.paintCachedImage(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, new Object[] { region })) {
/* 1631 */           GTKPainter.ENGINE.startPainting(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, new Object[] { region });
/* 1632 */           GTKPainter.ENGINE.paintShadow(param1Graphics, synthContext, region, j, GTKConstants.ShadowType.ETCHED_IN, "frame", param1Int1, param1Int2, param1Int3, param1Int4);
/*      */           
/* 1634 */           GTKPainter.ENGINE.finishPainting();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 1640 */       SynthContext synthContext = getContext((JComponent)param1Component);
/* 1641 */       return synthContext.getStyle().getInsets(synthContext, param1Insets);
/*      */     }
/*      */     
/*      */     public boolean isBorderOpaque() {
/* 1645 */       return true;
/*      */     }
/*      */     
/*      */     private SynthStyle getStyle(JComponent param1JComponent) {
/* 1649 */       return SynthLookAndFeel.getStyle(param1JComponent, GTKEngine.CustomRegion.TITLED_BORDER);
/*      */     }
/*      */     
/*      */     private SynthContext getContext(JComponent param1JComponent) {
/* 1653 */       char c = 'Ѐ';
/* 1654 */       return new SynthContext(param1JComponent, GTKEngine.CustomRegion.TITLED_BORDER, 
/* 1655 */           getStyle(param1JComponent), c);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */