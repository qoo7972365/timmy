/*     */ package com.sun.java.swing.plaf.gtk;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.util.HashMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.plaf.synth.ColorType;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthContext;
/*     */ import sun.awt.UNIXToolkit;
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ import sun.swing.ImageCache;
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
/*     */ class GTKEngine
/*     */ {
/*  59 */   static final GTKEngine INSTANCE = new GTKEngine();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum WidgetType
/*     */   {
/*  66 */     BUTTON, CHECK_BOX, CHECK_BOX_MENU_ITEM, COLOR_CHOOSER,
/*  67 */     COMBO_BOX, COMBO_BOX_ARROW_BUTTON, COMBO_BOX_TEXT_FIELD,
/*  68 */     DESKTOP_ICON, DESKTOP_PANE, EDITOR_PANE, FORMATTED_TEXT_FIELD,
/*  69 */     HANDLE_BOX, HPROGRESS_BAR,
/*  70 */     HSCROLL_BAR, HSCROLL_BAR_BUTTON_LEFT, HSCROLL_BAR_BUTTON_RIGHT,
/*  71 */     HSCROLL_BAR_TRACK, HSCROLL_BAR_THUMB,
/*  72 */     HSEPARATOR, HSLIDER, HSLIDER_TRACK, HSLIDER_THUMB, HSPLIT_PANE_DIVIDER,
/*  73 */     INTERNAL_FRAME, INTERNAL_FRAME_TITLE_PANE, IMAGE, LABEL, LIST, MENU,
/*  74 */     MENU_BAR, MENU_ITEM, MENU_ITEM_ACCELERATOR, OPTION_PANE, PANEL,
/*  75 */     PASSWORD_FIELD, POPUP_MENU, POPUP_MENU_SEPARATOR,
/*  76 */     RADIO_BUTTON, RADIO_BUTTON_MENU_ITEM, ROOT_PANE, SCROLL_PANE,
/*  77 */     SPINNER, SPINNER_ARROW_BUTTON, SPINNER_TEXT_FIELD,
/*  78 */     SPLIT_PANE, TABBED_PANE, TABBED_PANE_TAB_AREA, TABBED_PANE_CONTENT,
/*  79 */     TABBED_PANE_TAB, TABLE, TABLE_HEADER, TEXT_AREA, TEXT_FIELD, TEXT_PANE,
/*  80 */     TITLED_BORDER,
/*  81 */     TOGGLE_BUTTON, TOOL_BAR, TOOL_BAR_DRAG_WINDOW, TOOL_BAR_SEPARATOR,
/*  82 */     TOOL_TIP, TREE, TREE_CELL, VIEWPORT, VPROGRESS_BAR,
/*  83 */     VSCROLL_BAR, VSCROLL_BAR_BUTTON_UP, VSCROLL_BAR_BUTTON_DOWN,
/*  84 */     VSCROLL_BAR_TRACK, VSCROLL_BAR_THUMB,
/*  85 */     VSEPARATOR, VSLIDER, VSLIDER_TRACK, VSLIDER_THUMB,
/*  86 */     VSPLIT_PANE_DIVIDER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum Settings
/*     */   {
/*  95 */     GTK_FONT_NAME,
/*  96 */     GTK_ICON_SIZES,
/*  97 */     GTK_CURSOR_BLINK,
/*  98 */     GTK_CURSOR_BLINK_TIME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class CustomRegion
/*     */     extends Region
/*     */   {
/* 109 */     static Region TITLED_BORDER = new CustomRegion("TitledBorder");
/*     */     
/*     */     private CustomRegion(String param1String) {
/* 112 */       super(param1String, null, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 118 */   private ImageCache cache = new ImageCache(50);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 180 */     Toolkit.getDefaultToolkit();
/*     */   }
/*     */   
/* 183 */   private static HashMap<Region, Object> regionToWidgetTypeMap = new HashMap<>(50); static {
/* 184 */     regionToWidgetTypeMap.put(Region.ARROW_BUTTON, new WidgetType[] { WidgetType.SPINNER_ARROW_BUTTON, WidgetType.COMBO_BOX_ARROW_BUTTON, WidgetType.HSCROLL_BAR_BUTTON_LEFT, WidgetType.HSCROLL_BAR_BUTTON_RIGHT, WidgetType.VSCROLL_BAR_BUTTON_UP, WidgetType.VSCROLL_BAR_BUTTON_DOWN });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     regionToWidgetTypeMap.put(Region.BUTTON, WidgetType.BUTTON);
/* 192 */     regionToWidgetTypeMap.put(Region.CHECK_BOX, WidgetType.CHECK_BOX);
/* 193 */     regionToWidgetTypeMap.put(Region.CHECK_BOX_MENU_ITEM, WidgetType.CHECK_BOX_MENU_ITEM);
/*     */     
/* 195 */     regionToWidgetTypeMap.put(Region.COLOR_CHOOSER, WidgetType.COLOR_CHOOSER);
/* 196 */     regionToWidgetTypeMap.put(Region.FILE_CHOOSER, WidgetType.OPTION_PANE);
/* 197 */     regionToWidgetTypeMap.put(Region.COMBO_BOX, WidgetType.COMBO_BOX);
/* 198 */     regionToWidgetTypeMap.put(Region.DESKTOP_ICON, WidgetType.DESKTOP_ICON);
/* 199 */     regionToWidgetTypeMap.put(Region.DESKTOP_PANE, WidgetType.DESKTOP_PANE);
/* 200 */     regionToWidgetTypeMap.put(Region.EDITOR_PANE, WidgetType.EDITOR_PANE);
/* 201 */     regionToWidgetTypeMap.put(Region.FORMATTED_TEXT_FIELD, new WidgetType[] { WidgetType.FORMATTED_TEXT_FIELD, WidgetType.SPINNER_TEXT_FIELD });
/*     */     
/* 203 */     regionToWidgetTypeMap.put(GTKRegion.HANDLE_BOX, WidgetType.HANDLE_BOX);
/* 204 */     regionToWidgetTypeMap.put(Region.INTERNAL_FRAME, WidgetType.INTERNAL_FRAME);
/*     */     
/* 206 */     regionToWidgetTypeMap.put(Region.INTERNAL_FRAME_TITLE_PANE, WidgetType.INTERNAL_FRAME_TITLE_PANE);
/*     */     
/* 208 */     regionToWidgetTypeMap.put(Region.LABEL, new WidgetType[] { WidgetType.LABEL, WidgetType.COMBO_BOX_TEXT_FIELD });
/*     */     
/* 210 */     regionToWidgetTypeMap.put(Region.LIST, WidgetType.LIST);
/* 211 */     regionToWidgetTypeMap.put(Region.MENU, WidgetType.MENU);
/* 212 */     regionToWidgetTypeMap.put(Region.MENU_BAR, WidgetType.MENU_BAR);
/* 213 */     regionToWidgetTypeMap.put(Region.MENU_ITEM, WidgetType.MENU_ITEM);
/* 214 */     regionToWidgetTypeMap.put(Region.MENU_ITEM_ACCELERATOR, WidgetType.MENU_ITEM_ACCELERATOR);
/*     */     
/* 216 */     regionToWidgetTypeMap.put(Region.OPTION_PANE, WidgetType.OPTION_PANE);
/* 217 */     regionToWidgetTypeMap.put(Region.PANEL, WidgetType.PANEL);
/* 218 */     regionToWidgetTypeMap.put(Region.PASSWORD_FIELD, WidgetType.PASSWORD_FIELD);
/*     */     
/* 220 */     regionToWidgetTypeMap.put(Region.POPUP_MENU, WidgetType.POPUP_MENU);
/* 221 */     regionToWidgetTypeMap.put(Region.POPUP_MENU_SEPARATOR, WidgetType.POPUP_MENU_SEPARATOR);
/*     */     
/* 223 */     regionToWidgetTypeMap.put(Region.PROGRESS_BAR, new WidgetType[] { WidgetType.HPROGRESS_BAR, WidgetType.VPROGRESS_BAR });
/*     */     
/* 225 */     regionToWidgetTypeMap.put(Region.RADIO_BUTTON, WidgetType.RADIO_BUTTON);
/* 226 */     regionToWidgetTypeMap.put(Region.RADIO_BUTTON_MENU_ITEM, WidgetType.RADIO_BUTTON_MENU_ITEM);
/*     */     
/* 228 */     regionToWidgetTypeMap.put(Region.ROOT_PANE, WidgetType.ROOT_PANE);
/* 229 */     regionToWidgetTypeMap.put(Region.SCROLL_BAR, new WidgetType[] { WidgetType.HSCROLL_BAR, WidgetType.VSCROLL_BAR });
/*     */     
/* 231 */     regionToWidgetTypeMap.put(Region.SCROLL_BAR_THUMB, new WidgetType[] { WidgetType.HSCROLL_BAR_THUMB, WidgetType.VSCROLL_BAR_THUMB });
/*     */     
/* 233 */     regionToWidgetTypeMap.put(Region.SCROLL_BAR_TRACK, new WidgetType[] { WidgetType.HSCROLL_BAR_TRACK, WidgetType.VSCROLL_BAR_TRACK });
/*     */     
/* 235 */     regionToWidgetTypeMap.put(Region.SCROLL_PANE, WidgetType.SCROLL_PANE);
/* 236 */     regionToWidgetTypeMap.put(Region.SEPARATOR, new WidgetType[] { WidgetType.HSEPARATOR, WidgetType.VSEPARATOR });
/*     */     
/* 238 */     regionToWidgetTypeMap.put(Region.SLIDER, new WidgetType[] { WidgetType.HSLIDER, WidgetType.VSLIDER });
/*     */     
/* 240 */     regionToWidgetTypeMap.put(Region.SLIDER_THUMB, new WidgetType[] { WidgetType.HSLIDER_THUMB, WidgetType.VSLIDER_THUMB });
/*     */     
/* 242 */     regionToWidgetTypeMap.put(Region.SLIDER_TRACK, new WidgetType[] { WidgetType.HSLIDER_TRACK, WidgetType.VSLIDER_TRACK });
/*     */     
/* 244 */     regionToWidgetTypeMap.put(Region.SPINNER, WidgetType.SPINNER);
/* 245 */     regionToWidgetTypeMap.put(Region.SPLIT_PANE, WidgetType.SPLIT_PANE);
/* 246 */     regionToWidgetTypeMap.put(Region.SPLIT_PANE_DIVIDER, new WidgetType[] { WidgetType.HSPLIT_PANE_DIVIDER, WidgetType.VSPLIT_PANE_DIVIDER });
/*     */     
/* 248 */     regionToWidgetTypeMap.put(Region.TABBED_PANE, WidgetType.TABBED_PANE);
/* 249 */     regionToWidgetTypeMap.put(Region.TABBED_PANE_CONTENT, WidgetType.TABBED_PANE_CONTENT);
/*     */     
/* 251 */     regionToWidgetTypeMap.put(Region.TABBED_PANE_TAB, WidgetType.TABBED_PANE_TAB);
/*     */     
/* 253 */     regionToWidgetTypeMap.put(Region.TABBED_PANE_TAB_AREA, WidgetType.TABBED_PANE_TAB_AREA);
/*     */     
/* 255 */     regionToWidgetTypeMap.put(Region.TABLE, WidgetType.TABLE);
/* 256 */     regionToWidgetTypeMap.put(Region.TABLE_HEADER, WidgetType.TABLE_HEADER);
/* 257 */     regionToWidgetTypeMap.put(Region.TEXT_AREA, WidgetType.TEXT_AREA);
/* 258 */     regionToWidgetTypeMap.put(Region.TEXT_FIELD, new WidgetType[] { WidgetType.TEXT_FIELD, WidgetType.COMBO_BOX_TEXT_FIELD });
/*     */     
/* 260 */     regionToWidgetTypeMap.put(Region.TEXT_PANE, WidgetType.TEXT_PANE);
/* 261 */     regionToWidgetTypeMap.put(CustomRegion.TITLED_BORDER, WidgetType.TITLED_BORDER);
/* 262 */     regionToWidgetTypeMap.put(Region.TOGGLE_BUTTON, WidgetType.TOGGLE_BUTTON);
/* 263 */     regionToWidgetTypeMap.put(Region.TOOL_BAR, WidgetType.TOOL_BAR);
/* 264 */     regionToWidgetTypeMap.put(Region.TOOL_BAR_CONTENT, WidgetType.TOOL_BAR);
/* 265 */     regionToWidgetTypeMap.put(Region.TOOL_BAR_DRAG_WINDOW, WidgetType.TOOL_BAR_DRAG_WINDOW);
/*     */     
/* 267 */     regionToWidgetTypeMap.put(Region.TOOL_BAR_SEPARATOR, WidgetType.TOOL_BAR_SEPARATOR);
/*     */     
/* 269 */     regionToWidgetTypeMap.put(Region.TOOL_TIP, WidgetType.TOOL_TIP);
/* 270 */     regionToWidgetTypeMap.put(Region.TREE, WidgetType.TREE);
/* 271 */     regionToWidgetTypeMap.put(Region.TREE_CELL, WidgetType.TREE_CELL);
/* 272 */     regionToWidgetTypeMap.put(Region.VIEWPORT, WidgetType.VIEWPORT);
/*     */   }
/*     */ 
/*     */   
/*     */   static WidgetType getWidgetType(JComponent paramJComponent, Region paramRegion) {
/* 277 */     Object object = regionToWidgetTypeMap.get(paramRegion);
/*     */     
/* 279 */     if (object instanceof WidgetType) {
/* 280 */       return (WidgetType)object;
/*     */     }
/*     */     
/* 283 */     WidgetType[] arrayOfWidgetType = (WidgetType[])object;
/* 284 */     if (paramJComponent == null) {
/* 285 */       return arrayOfWidgetType[0];
/*     */     }
/*     */     
/* 288 */     if (paramJComponent instanceof JScrollBar) {
/* 289 */       return (((JScrollBar)paramJComponent).getOrientation() == 0) ? arrayOfWidgetType[0] : arrayOfWidgetType[1];
/*     */     }
/* 291 */     if (paramJComponent instanceof JSeparator) {
/* 292 */       JSeparator jSeparator = (JSeparator)paramJComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       if (jSeparator.getParent() instanceof javax.swing.JPopupMenu)
/* 298 */         return WidgetType.POPUP_MENU_SEPARATOR; 
/* 299 */       if (jSeparator.getParent() instanceof javax.swing.JToolBar) {
/* 300 */         return WidgetType.TOOL_BAR_SEPARATOR;
/*     */       }
/*     */       
/* 303 */       return (jSeparator.getOrientation() == 0) ? arrayOfWidgetType[0] : arrayOfWidgetType[1];
/*     */     } 
/* 305 */     if (paramJComponent instanceof JSlider) {
/* 306 */       return (((JSlider)paramJComponent).getOrientation() == 0) ? arrayOfWidgetType[0] : arrayOfWidgetType[1];
/*     */     }
/* 308 */     if (paramJComponent instanceof JProgressBar) {
/* 309 */       return (((JProgressBar)paramJComponent).getOrientation() == 0) ? arrayOfWidgetType[0] : arrayOfWidgetType[1];
/*     */     }
/* 311 */     if (paramJComponent instanceof JSplitPane) {
/* 312 */       return (((JSplitPane)paramJComponent).getOrientation() == 1) ? arrayOfWidgetType[1] : arrayOfWidgetType[0];
/*     */     }
/* 314 */     if (paramRegion == Region.LABEL) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       if (paramJComponent instanceof javax.swing.ListCellRenderer) {
/* 321 */         return arrayOfWidgetType[1];
/*     */       }
/* 323 */       return arrayOfWidgetType[0];
/*     */     } 
/* 325 */     if (paramRegion == Region.TEXT_FIELD) {
/* 326 */       String str = paramJComponent.getName();
/* 327 */       if (str != null && str.startsWith("ComboBox")) {
/* 328 */         return arrayOfWidgetType[1];
/*     */       }
/* 330 */       return arrayOfWidgetType[0];
/*     */     } 
/* 332 */     if (paramRegion == Region.FORMATTED_TEXT_FIELD) {
/* 333 */       String str = paramJComponent.getName();
/* 334 */       if (str != null && str.startsWith("Spinner")) {
/* 335 */         return arrayOfWidgetType[1];
/*     */       }
/* 337 */       return arrayOfWidgetType[0];
/*     */     } 
/* 339 */     if (paramRegion == Region.ARROW_BUTTON) {
/* 340 */       if (paramJComponent.getParent() instanceof JScrollBar) {
/*     */         
/* 342 */         Integer integer = (Integer)paramJComponent.getClientProperty("__arrow_direction__");
/*     */         
/* 344 */         boolean bool = (integer != null) ? integer.intValue() : true;
/* 345 */         switch (bool) {
/*     */           case true:
/* 347 */             return WidgetType.HSCROLL_BAR_BUTTON_LEFT;
/*     */           case true:
/* 349 */             return WidgetType.HSCROLL_BAR_BUTTON_RIGHT;
/*     */           case true:
/* 351 */             return WidgetType.VSCROLL_BAR_BUTTON_UP;
/*     */           case true:
/* 353 */             return WidgetType.VSCROLL_BAR_BUTTON_DOWN;
/*     */         } 
/* 355 */         return null;
/*     */       } 
/* 357 */       if (paramJComponent.getParent() instanceof javax.swing.JComboBox) {
/* 358 */         return WidgetType.COMBO_BOX_ARROW_BUTTON;
/*     */       }
/* 360 */       return WidgetType.SPINNER_ARROW_BUTTON;
/*     */     } 
/*     */ 
/*     */     
/* 364 */     return null;
/*     */   }
/*     */   
/*     */   private static int getTextDirection(SynthContext paramSynthContext) {
/* 368 */     GTKConstants.TextDirection textDirection = GTKConstants.TextDirection.NONE;
/* 369 */     JComponent jComponent = paramSynthContext.getComponent();
/* 370 */     if (jComponent != null) {
/* 371 */       ComponentOrientation componentOrientation = jComponent.getComponentOrientation();
/* 372 */       if (componentOrientation != null) {
/* 373 */         textDirection = componentOrientation.isLeftToRight() ? GTKConstants.TextDirection.LTR : GTKConstants.TextDirection.RTL;
/*     */       }
/*     */     } 
/*     */     
/* 377 */     return textDirection.ordinal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintArrow(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, GTKConstants.ArrowType paramArrowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 384 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 385 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 386 */     native_paint_arrow(i, paramInt1, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, paramArrowType
/* 387 */         .ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBox(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 395 */     int i = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 396 */     int j = paramSynthContext.getComponentState();
/* 397 */     int k = getTextDirection(paramSynthContext);
/* 398 */     int m = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 399 */     native_paint_box(m, i, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBoxGap(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, GTKConstants.PositionType paramPositionType, int paramInt6, int paramInt7) {
/* 408 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 409 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 410 */     native_paint_box_gap(i, paramInt1, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, paramPositionType
/* 411 */         .ordinal(), paramInt6, paramInt7);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintCheck(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 417 */     int i = paramSynthContext.getComponentState();
/* 418 */     int j = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 419 */     native_paint_check(j, i, paramString, paramInt1 - this.x0, paramInt2 - this.y0, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintExpander(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ExpanderStyle paramExpanderStyle, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 426 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 427 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 428 */     native_paint_expander(i, paramInt1, paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, paramExpanderStyle
/* 429 */         .ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintExtension(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, GTKConstants.PositionType paramPositionType, int paramInt6) {
/* 436 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 437 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 438 */     native_paint_extension(i, paramInt1, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, paramPositionType
/* 439 */         .ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintFlatBox(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, ColorType paramColorType) {
/* 446 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 447 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 448 */     native_paint_flat_box(i, paramInt1, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, paramSynthContext
/*     */         
/* 450 */         .getComponent().hasFocus());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintFocus(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 456 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 457 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 458 */     native_paint_focus(i, paramInt1, paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintHandle(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, GTKConstants.Orientation paramOrientation) {
/* 465 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 466 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 467 */     native_paint_handle(i, paramInt1, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, paramOrientation
/* 468 */         .ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintHline(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 474 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 475 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 476 */     native_paint_hline(i, paramInt1, paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintOption(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 482 */     int i = paramSynthContext.getComponentState();
/* 483 */     int j = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 484 */     native_paint_option(j, i, paramString, paramInt1 - this.x0, paramInt2 - this.y0, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintShadow(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 492 */     int i = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 493 */     int j = paramSynthContext.getComponentState();
/* 494 */     Container container = paramSynthContext.getComponent().getParent();
/* 495 */     if (GTKLookAndFeel.is3() && 
/* 496 */       container != null && container.getParent() instanceof javax.swing.JComboBox && 
/* 497 */       container.getParent().hasFocus()) {
/* 498 */       j |= 0x100;
/*     */     }
/*     */ 
/*     */     
/* 502 */     int k = getTextDirection(paramSynthContext);
/* 503 */     int m = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 504 */     native_paint_shadow(m, i, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintSlider(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, GTKConstants.ShadowType paramShadowType, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, GTKConstants.Orientation paramOrientation, boolean paramBoolean) {
/* 512 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 513 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 514 */     native_paint_slider(i, paramInt1, paramShadowType.ordinal(), paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5, paramOrientation
/* 515 */         .ordinal(), paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintVline(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 521 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 522 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 523 */     native_paint_vline(i, paramInt1, paramString, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBackground(Graphics paramGraphics, SynthContext paramSynthContext, Region paramRegion, int paramInt1, Color paramColor, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 529 */     paramInt1 = GTKLookAndFeel.synthStateToGTKStateType(paramInt1).ordinal();
/* 530 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 531 */     native_paint_background(i, paramInt1, paramInt2 - this.x0, paramInt3 - this.y0, paramInt4, paramInt5);
/*     */   }
/*     */   
/* 534 */   private static final ColorModel[] COLOR_MODELS = new ColorModel[] { new DirectColorModel(24, 16711680, 65280, 255, 0), new DirectColorModel(25, 16711680, 65280, 255, 16777216), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 540 */       ColorModel.getRGBdefault() };
/*     */ 
/*     */   
/* 543 */   private static final int[][] BAND_OFFSETS = new int[][] { { 16711680, 65280, 255 }, { 16711680, 65280, 255, 16777216 }, { 16711680, 65280, 255, -16777216 } };
/*     */   
/*     */   private static final int CACHE_SIZE = 50;
/*     */   
/*     */   private int x0;
/*     */   
/*     */   private int y0;
/*     */   
/*     */   private int w0;
/*     */   
/*     */   private int h0;
/*     */   private Graphics graphics;
/*     */   private Object[] cacheArgs;
/*     */   
/*     */   public boolean paintCachedImage(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object... paramVarArgs) {
/* 558 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/* 559 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 563 */     Image image = this.cache.getImage(getClass(), null, paramInt3, paramInt4, paramVarArgs);
/* 564 */     if (image != null) {
/* 565 */       paramGraphics.drawImage(image, paramInt1, paramInt2, null);
/* 566 */       return true;
/*     */     } 
/* 568 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPainting(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object... paramVarArgs) {
/* 576 */     nativeStartPainting(paramInt3, paramInt4);
/* 577 */     this.x0 = paramInt1;
/* 578 */     this.y0 = paramInt2;
/* 579 */     this.w0 = paramInt3;
/* 580 */     this.h0 = paramInt4;
/* 581 */     this.graphics = paramGraphics;
/* 582 */     this.cacheArgs = paramVarArgs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage finishPainting() {
/* 590 */     return finishPainting(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage finishPainting(boolean paramBoolean) {
/* 599 */     DataBufferInt dataBufferInt = new DataBufferInt(this.w0 * this.h0);
/*     */ 
/*     */ 
/*     */     
/* 603 */     int i = nativeFinishPainting(SunWritableRaster.stealData(dataBufferInt, 0), this.w0, this.h0);
/*     */     
/* 605 */     SunWritableRaster.markDirty(dataBufferInt);
/*     */     
/* 607 */     int[] arrayOfInt = BAND_OFFSETS[i - 1];
/* 608 */     WritableRaster writableRaster = Raster.createPackedRaster(dataBufferInt, this.w0, this.h0, this.w0, arrayOfInt, (Point)null);
/*     */ 
/*     */     
/* 611 */     ColorModel colorModel = COLOR_MODELS[i - 1];
/* 612 */     BufferedImage bufferedImage = new BufferedImage(colorModel, writableRaster, false, null);
/* 613 */     if (paramBoolean) {
/* 614 */       this.cache.setImage(getClass(), null, this.w0, this.h0, this.cacheArgs, bufferedImage);
/*     */     }
/* 616 */     this.graphics.drawImage(bufferedImage, this.x0, this.y0, null);
/* 617 */     return bufferedImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void themeChanged() {
/* 624 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 625 */       native_switch_theme();
/*     */     } 
/* 627 */     this.cache.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getSetting(Settings paramSettings) {
/* 632 */     synchronized (UNIXToolkit.GTK_LOCK) {
/* 633 */       return native_get_gtk_setting(paramSettings.ordinal());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setRangeValue(SynthContext paramSynthContext, Region paramRegion, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 643 */     int i = getWidgetType(paramSynthContext.getComponent(), paramRegion).ordinal();
/* 644 */     nativeSetRangeValue(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */   
/*     */   private native void native_paint_arrow(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*     */   
/*     */   private native void native_paint_box(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9);
/*     */   
/*     */   private native void native_paint_box_gap(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);
/*     */   
/*     */   private native void native_paint_check(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private native void native_paint_expander(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */   
/*     */   private native void native_paint_extension(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*     */   
/*     */   private native void native_paint_flat_box(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean);
/*     */   
/*     */   private native void native_paint_focus(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private native void native_paint_handle(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*     */   
/*     */   private native void native_paint_hline(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private native void native_paint_option(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private native void native_paint_shadow(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9);
/*     */   
/*     */   private native void native_paint_slider(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean);
/*     */   
/*     */   private native void native_paint_vline(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private native void native_paint_background(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private native Object native_get_gtk_setting(int paramInt);
/*     */   
/*     */   private native void nativeSetRangeValue(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   private native void nativeStartPainting(int paramInt1, int paramInt2);
/*     */   
/*     */   private native int nativeFinishPainting(int[] paramArrayOfint, int paramInt1, int paramInt2);
/*     */   
/*     */   private native void native_switch_theme();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */