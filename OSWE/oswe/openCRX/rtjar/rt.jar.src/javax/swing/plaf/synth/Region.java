/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIDefaults;
/*     */ import sun.awt.AppContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Region
/*     */ {
/*  75 */   private static final Object UI_TO_REGION_MAP_KEY = new Object();
/*  76 */   private static final Object LOWER_CASE_NAME_MAP_KEY = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final Region ARROW_BUTTON = new Region("ArrowButton", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static final Region BUTTON = new Region("Button", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static final Region CHECK_BOX = new Region("CheckBox", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final Region CHECK_BOX_MENU_ITEM = new Region("CheckBoxMenuItem", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final Region COLOR_CHOOSER = new Region("ColorChooser", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static final Region COMBO_BOX = new Region("ComboBox", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public static final Region DESKTOP_PANE = new Region("DesktopPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final Region DESKTOP_ICON = new Region("DesktopIcon", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public static final Region EDITOR_PANE = new Region("EditorPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public static final Region FILE_CHOOSER = new Region("FileChooser", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static final Region FORMATTED_TEXT_FIELD = new Region("FormattedTextField", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public static final Region INTERNAL_FRAME = new Region("InternalFrame", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static final Region INTERNAL_FRAME_TITLE_PANE = new Region("InternalFrameTitlePane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final Region LABEL = new Region("Label", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public static final Region LIST = new Region("List", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public static final Region MENU = new Region("Menu", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public static final Region MENU_BAR = new Region("MenuBar", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public static final Region MENU_ITEM = new Region("MenuItem", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public static final Region MENU_ITEM_ACCELERATOR = new Region("MenuItemAccelerator", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   public static final Region OPTION_PANE = new Region("OptionPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public static final Region PANEL = new Region("Panel", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public static final Region PASSWORD_FIELD = new Region("PasswordField", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   public static final Region POPUP_MENU = new Region("PopupMenu", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   public static final Region POPUP_MENU_SEPARATOR = new Region("PopupMenuSeparator", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public static final Region PROGRESS_BAR = new Region("ProgressBar", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   public static final Region RADIO_BUTTON = new Region("RadioButton", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public static final Region RADIO_BUTTON_MENU_ITEM = new Region("RadioButtonMenuItem", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   public static final Region ROOT_PANE = new Region("RootPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   public static final Region SCROLL_BAR = new Region("ScrollBar", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public static final Region SCROLL_BAR_TRACK = new Region("ScrollBarTrack", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 269 */   public static final Region SCROLL_BAR_THUMB = new Region("ScrollBarThumb", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 275 */   public static final Region SCROLL_PANE = new Region("ScrollPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 281 */   public static final Region SEPARATOR = new Region("Separator", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 287 */   public static final Region SLIDER = new Region("Slider", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public static final Region SLIDER_TRACK = new Region("SliderTrack", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public static final Region SLIDER_THUMB = new Region("SliderThumb", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   public static final Region SPINNER = new Region("Spinner", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   public static final Region SPLIT_PANE = new Region("SplitPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 318 */   public static final Region SPLIT_PANE_DIVIDER = new Region("SplitPaneDivider", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 324 */   public static final Region TABBED_PANE = new Region("TabbedPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   public static final Region TABBED_PANE_TAB = new Region("TabbedPaneTab", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 336 */   public static final Region TABBED_PANE_TAB_AREA = new Region("TabbedPaneTabArea", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 342 */   public static final Region TABBED_PANE_CONTENT = new Region("TabbedPaneContent", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 348 */   public static final Region TABLE = new Region("Table", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 354 */   public static final Region TABLE_HEADER = new Region("TableHeader", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 360 */   public static final Region TEXT_AREA = new Region("TextArea", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 366 */   public static final Region TEXT_FIELD = new Region("TextField", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 372 */   public static final Region TEXT_PANE = new Region("TextPane", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 378 */   public static final Region TOGGLE_BUTTON = new Region("ToggleButton", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 384 */   public static final Region TOOL_BAR = new Region("ToolBar", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 390 */   public static final Region TOOL_BAR_CONTENT = new Region("ToolBarContent", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 396 */   public static final Region TOOL_BAR_DRAG_WINDOW = new Region("ToolBarDragWindow", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 402 */   public static final Region TOOL_TIP = new Region("ToolTip", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 408 */   public static final Region TOOL_BAR_SEPARATOR = new Region("ToolBarSeparator", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 414 */   public static final Region TREE = new Region("Tree", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 420 */   public static final Region TREE_CELL = new Region("TreeCell", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 426 */   public static final Region VIEWPORT = new Region("Viewport", false); private final String name;
/*     */   
/*     */   private static Map<String, Region> getUItoRegionMap() {
/* 429 */     AppContext appContext = AppContext.getAppContext();
/* 430 */     Map<Object, Object> map = (Map)appContext.get(UI_TO_REGION_MAP_KEY);
/* 431 */     if (map == null) {
/* 432 */       map = new HashMap<>();
/* 433 */       map.put("ArrowButtonUI", ARROW_BUTTON);
/* 434 */       map.put("ButtonUI", BUTTON);
/* 435 */       map.put("CheckBoxUI", CHECK_BOX);
/* 436 */       map.put("CheckBoxMenuItemUI", CHECK_BOX_MENU_ITEM);
/* 437 */       map.put("ColorChooserUI", COLOR_CHOOSER);
/* 438 */       map.put("ComboBoxUI", COMBO_BOX);
/* 439 */       map.put("DesktopPaneUI", DESKTOP_PANE);
/* 440 */       map.put("DesktopIconUI", DESKTOP_ICON);
/* 441 */       map.put("EditorPaneUI", EDITOR_PANE);
/* 442 */       map.put("FileChooserUI", FILE_CHOOSER);
/* 443 */       map.put("FormattedTextFieldUI", FORMATTED_TEXT_FIELD);
/* 444 */       map.put("InternalFrameUI", INTERNAL_FRAME);
/* 445 */       map.put("InternalFrameTitlePaneUI", INTERNAL_FRAME_TITLE_PANE);
/* 446 */       map.put("LabelUI", LABEL);
/* 447 */       map.put("ListUI", LIST);
/* 448 */       map.put("MenuUI", MENU);
/* 449 */       map.put("MenuBarUI", MENU_BAR);
/* 450 */       map.put("MenuItemUI", MENU_ITEM);
/* 451 */       map.put("OptionPaneUI", OPTION_PANE);
/* 452 */       map.put("PanelUI", PANEL);
/* 453 */       map.put("PasswordFieldUI", PASSWORD_FIELD);
/* 454 */       map.put("PopupMenuUI", POPUP_MENU);
/* 455 */       map.put("PopupMenuSeparatorUI", POPUP_MENU_SEPARATOR);
/* 456 */       map.put("ProgressBarUI", PROGRESS_BAR);
/* 457 */       map.put("RadioButtonUI", RADIO_BUTTON);
/* 458 */       map.put("RadioButtonMenuItemUI", RADIO_BUTTON_MENU_ITEM);
/* 459 */       map.put("RootPaneUI", ROOT_PANE);
/* 460 */       map.put("ScrollBarUI", SCROLL_BAR);
/* 461 */       map.put("ScrollPaneUI", SCROLL_PANE);
/* 462 */       map.put("SeparatorUI", SEPARATOR);
/* 463 */       map.put("SliderUI", SLIDER);
/* 464 */       map.put("SpinnerUI", SPINNER);
/* 465 */       map.put("SplitPaneUI", SPLIT_PANE);
/* 466 */       map.put("TabbedPaneUI", TABBED_PANE);
/* 467 */       map.put("TableUI", TABLE);
/* 468 */       map.put("TableHeaderUI", TABLE_HEADER);
/* 469 */       map.put("TextAreaUI", TEXT_AREA);
/* 470 */       map.put("TextFieldUI", TEXT_FIELD);
/* 471 */       map.put("TextPaneUI", TEXT_PANE);
/* 472 */       map.put("ToggleButtonUI", TOGGLE_BUTTON);
/* 473 */       map.put("ToolBarUI", TOOL_BAR);
/* 474 */       map.put("ToolTipUI", TOOL_TIP);
/* 475 */       map.put("ToolBarSeparatorUI", TOOL_BAR_SEPARATOR);
/* 476 */       map.put("TreeUI", TREE);
/* 477 */       map.put("ViewportUI", VIEWPORT);
/* 478 */       appContext.put(UI_TO_REGION_MAP_KEY, map);
/*     */     } 
/* 480 */     return (Map)map;
/*     */   }
/*     */   private final boolean subregion;
/*     */   private static Map<Region, String> getLowerCaseNameMap() {
/* 484 */     AppContext appContext = AppContext.getAppContext();
/* 485 */     Map<Object, Object> map = (Map)appContext.get(LOWER_CASE_NAME_MAP_KEY);
/* 486 */     if (map == null) {
/* 487 */       map = new HashMap<>();
/* 488 */       appContext.put(LOWER_CASE_NAME_MAP_KEY, map);
/*     */     } 
/* 490 */     return (Map)map;
/*     */   }
/*     */   
/*     */   static Region getRegion(JComponent paramJComponent) {
/* 494 */     return getUItoRegionMap().get(paramJComponent.getUIClassID());
/*     */   }
/*     */   
/*     */   static void registerUIs(UIDefaults paramUIDefaults) {
/* 498 */     for (String str : getUItoRegionMap().keySet()) {
/* 499 */       paramUIDefaults.put(str, "javax.swing.plaf.synth.SynthLookAndFeel");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Region(String paramString, boolean paramBoolean) {
/* 507 */     if (paramString == null) {
/* 508 */       throw new NullPointerException("You must specify a non-null name");
/*     */     }
/* 510 */     this.name = paramString;
/* 511 */     this.subregion = paramBoolean;
/*     */   }
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
/*     */   protected Region(String paramString1, String paramString2, boolean paramBoolean) {
/* 526 */     this(paramString1, paramBoolean);
/* 527 */     if (paramString2 != null) {
/* 528 */       getUItoRegionMap().put(paramString2, this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSubregion() {
/* 541 */     return this.subregion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 550 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getLowerCaseName() {
/* 559 */     Map<Region, String> map = getLowerCaseNameMap();
/* 560 */     String str = map.get(this);
/* 561 */     if (str == null) {
/* 562 */       str = this.name.toLowerCase(Locale.ENGLISH);
/* 563 */       map.put(this, str);
/*     */     } 
/* 565 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 575 */     return this.name;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/Region.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */