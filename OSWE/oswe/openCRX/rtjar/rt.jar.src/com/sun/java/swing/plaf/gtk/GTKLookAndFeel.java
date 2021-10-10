/*      */ package com.sun.java.swing.plaf.gtk;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Locale;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.LayoutStyle;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.colorchooser.AbstractColorChooserPanel;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.DimensionUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.synth.Region;
/*      */ import javax.swing.plaf.synth.SynthLookAndFeel;
/*      */ import sun.awt.OSInfo;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.UNIXToolkit;
/*      */ import sun.java2d.SunGraphicsEnvironment;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.swing.DefaultLayoutStyle;
/*      */ import sun.swing.SwingLazyValue;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GTKLookAndFeel
/*      */   extends SynthLookAndFeel
/*      */ {
/*      */   private static boolean IS_22;
/*      */   private static boolean IS_3;
/*      */   static Object aaTextInfo;
/*      */   private static boolean isSunCJK;
/*      */   private static boolean gtkAAFontSettingsCond;
/*      */   private Font fallbackFont;
/*      */   private boolean inInitialize;
/*      */   private boolean pclInstalled;
/*      */   private GTKStyleFactory styleFactory;
/*  106 */   private static String gtkThemeName = "Default";
/*      */ 
/*      */   
/*      */   static {
/*  110 */     String str = Locale.getDefault().getLanguage();
/*      */ 
/*      */ 
/*      */     
/*  114 */     boolean bool = (Locale.CHINESE.getLanguage().equals(str) || Locale.JAPANESE.getLanguage().equals(str) || Locale.KOREAN.getLanguage().equals(str)) ? true : false;
/*      */     
/*  116 */     if (bool) {
/*  117 */       Boolean bool2; boolean bool1 = false;
/*  118 */       switch (OSInfo.getOSType()) {
/*      */         case INDENT:
/*  120 */           bool1 = true;
/*      */           break;
/*      */         
/*      */         case RELATED:
/*  124 */           bool2 = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */               {
/*      */                 public Boolean run() {
/*  127 */                   File file = new File("/etc/sun-release");
/*  128 */                   return Boolean.valueOf(file.exists());
/*      */                 }
/*      */               });
/*  131 */           bool1 = bool2.booleanValue(); break;
/*      */       } 
/*  133 */       if (bool1 && !SunGraphicsEnvironment.isOpenSolaris) {
/*  134 */         isSunCJK = true;
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
/*      */   static boolean is2_2() {
/*  147 */     return IS_22;
/*      */   }
/*      */   
/*      */   static boolean is3() {
/*  151 */     return IS_3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static GTKConstants.PositionType SwingOrientationConstantToGTK(int paramInt) {
/*  158 */     switch (paramInt) {
/*      */       case 2:
/*  160 */         return GTKConstants.PositionType.LEFT;
/*      */       case 4:
/*  162 */         return GTKConstants.PositionType.RIGHT;
/*      */       case 1:
/*  164 */         return GTKConstants.PositionType.TOP;
/*      */       case 3:
/*  166 */         return GTKConstants.PositionType.BOTTOM;
/*      */     } 
/*  168 */     assert false : "Unknown orientation: " + paramInt;
/*  169 */     return GTKConstants.PositionType.TOP;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static GTKConstants.StateType synthStateToGTKStateType(int paramInt) {
/*  178 */     switch (paramInt)
/*      */     { case 4:
/*  180 */         stateType = GTKConstants.StateType.ACTIVE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  196 */         return stateType;case 2: stateType = GTKConstants.StateType.PRELIGHT; return stateType;case 512: stateType = GTKConstants.StateType.SELECTED; return stateType;case 8: stateType = GTKConstants.StateType.INSENSITIVE; return stateType; }  GTKConstants.StateType stateType = GTKConstants.StateType.NORMAL; return stateType;
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
/*      */   static int synthStateToGTKState(Region paramRegion, int paramInt) {
/*  213 */     if ((paramInt & 0x4) != 0) {
/*  214 */       if (paramRegion == Region.RADIO_BUTTON || paramRegion == Region.CHECK_BOX || paramRegion == Region.MENU || paramRegion == Region.MENU_ITEM || paramRegion == Region.RADIO_BUTTON_MENU_ITEM || paramRegion == Region.CHECK_BOX_MENU_ITEM || paramRegion == Region.SPLIT_PANE) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  221 */         paramInt = 2;
/*      */       } else {
/*  223 */         paramInt = 4;
/*      */       }
/*      */     
/*  226 */     } else if (paramRegion == Region.TABBED_PANE_TAB) {
/*  227 */       if ((paramInt & 0x8) != 0) {
/*  228 */         paramInt = 8;
/*      */       }
/*  230 */       else if ((paramInt & 0x200) != 0) {
/*  231 */         paramInt = 1;
/*      */       } else {
/*  233 */         paramInt = 4;
/*      */       }
/*      */     
/*  236 */     } else if ((paramInt & 0x200) != 0) {
/*  237 */       if (paramRegion == Region.MENU) {
/*  238 */         paramInt = 2;
/*  239 */       } else if (paramRegion == Region.RADIO_BUTTON || paramRegion == Region.TOGGLE_BUTTON || paramRegion == Region.RADIO_BUTTON_MENU_ITEM || paramRegion == Region.CHECK_BOX_MENU_ITEM || paramRegion == Region.CHECK_BOX || paramRegion == Region.BUTTON) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  245 */         if ((paramInt & 0x8) != 0) {
/*  246 */           paramInt = 8;
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  251 */         else if ((paramInt & 0x2) != 0) {
/*  252 */           paramInt = 2;
/*      */         } else {
/*  254 */           paramInt = 4;
/*      */         } 
/*      */       } else {
/*  257 */         paramInt = 512;
/*      */       }
/*      */     
/*      */     }
/*  261 */     else if ((paramInt & 0x2) != 0) {
/*  262 */       paramInt = 2;
/*      */     }
/*  264 */     else if ((paramInt & 0x8) != 0) {
/*  265 */       paramInt = 8;
/*      */     
/*      */     }
/*  268 */     else if (paramRegion == Region.SLIDER_TRACK) {
/*  269 */       paramInt = 4;
/*      */     } else {
/*  271 */       paramInt = 1;
/*      */     } 
/*      */     
/*  274 */     return paramInt;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean isText(Region paramRegion) {
/*  279 */     return (paramRegion == Region.TEXT_FIELD || paramRegion == Region.FORMATTED_TEXT_FIELD || paramRegion == Region.LIST || paramRegion == Region.PASSWORD_FIELD || paramRegion == Region.SPINNER || paramRegion == Region.TABLE || paramRegion == Region.TEXT_AREA || paramRegion == Region.TEXT_FIELD || paramRegion == Region.TEXT_PANE || paramRegion == Region.TREE);
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
/*      */   public UIDefaults getDefaults() {
/*  293 */     UIDefaults uIDefaults = super.getDefaults();
/*      */ 
/*      */     
/*  296 */     uIDefaults.put("TabbedPane.isTabRollover", Boolean.FALSE);
/*      */ 
/*      */     
/*  299 */     uIDefaults.put("Synth.doNotSetTextAA", Boolean.valueOf(true));
/*      */     
/*  301 */     initResourceBundle(uIDefaults);
/*      */ 
/*      */     
/*  304 */     initSystemColorDefaults(uIDefaults);
/*  305 */     initComponentDefaults(uIDefaults);
/*  306 */     installPropertyChangeListeners();
/*  307 */     return uIDefaults;
/*      */   }
/*      */   
/*      */   private void installPropertyChangeListeners() {
/*  311 */     if (!this.pclInstalled) {
/*  312 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  313 */       WeakPCL weakPCL = new WeakPCL(this, toolkit, "gnome.Net/ThemeName");
/*  314 */       toolkit.addPropertyChangeListener(weakPCL.getKey(), weakPCL);
/*  315 */       weakPCL = new WeakPCL(this, toolkit, "gnome.Gtk/FontName");
/*  316 */       toolkit.addPropertyChangeListener(weakPCL.getKey(), weakPCL);
/*  317 */       weakPCL = new WeakPCL(this, toolkit, "gnome.Xft/DPI");
/*  318 */       toolkit.addPropertyChangeListener(weakPCL.getKey(), weakPCL);
/*      */       
/*  320 */       flushUnreferenced();
/*  321 */       this.pclInstalled = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initResourceBundle(UIDefaults paramUIDefaults) {
/*  326 */     paramUIDefaults.addResourceBundle("com.sun.java.swing.plaf.gtk.resources.gtk");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initComponentDefaults(UIDefaults paramUIDefaults) {
/*      */     Integer integer2;
/*  332 */     super.initComponentDefaults(paramUIDefaults);
/*      */     
/*  334 */     Integer integer1 = Integer.valueOf(0);
/*  335 */     SwingLazyValue swingLazyValue = new SwingLazyValue("javax.swing.plaf.BorderUIResource$EmptyBorderUIResource", new Object[] { integer1, integer1, integer1, integer1 });
/*      */ 
/*      */     
/*  338 */     GTKStyle.GTKLazyValue gTKLazyValue1 = new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKPainter$ListTableFocusBorder", "getUnselectedCellBorder");
/*      */ 
/*      */     
/*  341 */     GTKStyle.GTKLazyValue gTKLazyValue2 = new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKPainter$ListTableFocusBorder", "getSelectedCellBorder");
/*      */ 
/*      */     
/*  344 */     GTKStyle.GTKLazyValue gTKLazyValue3 = new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKPainter$ListTableFocusBorder", "getNoFocusCellBorder");
/*      */ 
/*      */ 
/*      */     
/*  348 */     GTKStyleFactory gTKStyleFactory = (GTKStyleFactory)getStyleFactory();
/*  349 */     GTKStyle gTKStyle1 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.TREE);
/*  350 */     Color color1 = gTKStyle1.getGTKColor(1, GTKColorType.TEXT_BACKGROUND);
/*      */     
/*  352 */     Color color2 = gTKStyle1.getGTKColor(1, GTKColorType.BACKGROUND);
/*      */     
/*  354 */     Color color3 = gTKStyle1.getGTKColor(1, GTKColorType.FOREGROUND);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  361 */     GTKStyle gTKStyle2 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.PROGRESS_BAR);
/*  362 */     int i = gTKStyle2.getXThickness();
/*  363 */     int j = gTKStyle2.getYThickness();
/*  364 */     int k = 150 - i * 2;
/*  365 */     int m = 20 - j * 2;
/*  366 */     int n = 22 - i * 2;
/*  367 */     int i1 = 80 - j * 2;
/*      */ 
/*      */     
/*  370 */     if (Boolean.FALSE.equals(GTKEngine.INSTANCE.getSetting(GTKEngine.Settings.GTK_CURSOR_BLINK))) {
/*      */       
/*  372 */       integer2 = Integer.valueOf(0);
/*      */     } else {
/*  374 */       integer2 = (Integer)GTKEngine.INSTANCE.getSetting(GTKEngine.Settings.GTK_CURSOR_BLINK_TIME);
/*      */       
/*  376 */       if (integer2 == null) {
/*  377 */         integer2 = Integer.valueOf(500);
/*      */       }
/*      */     } 
/*  380 */     InsetsUIResource insetsUIResource1 = new InsetsUIResource(0, 0, 0, 0);
/*      */     
/*  382 */     Double double_ = new Double(0.025D);
/*  383 */     Color color4 = paramUIDefaults.getColor("caretColor");
/*  384 */     Color color5 = paramUIDefaults.getColor("controlText");
/*      */     
/*  386 */     UIDefaults.LazyInputMap lazyInputMap1 = new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-previous-word", "ctrl KP_LEFT", "caret-previous-word", "ctrl RIGHT", "caret-next-word", "ctrl KP_RIGHT", "caret-next-word", "ctrl shift LEFT", "selection-previous-word", "ctrl shift KP_LEFT", "selection-previous-word", "ctrl shift RIGHT", "selection-next-word", "ctrl shift KP_RIGHT", "selection-next-word", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "ctrl BACK_SLASH", "unselect", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  428 */     UIDefaults.LazyInputMap lazyInputMap2 = new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-begin-line", "ctrl KP_LEFT", "caret-begin-line", "ctrl RIGHT", "caret-end-line", "ctrl KP_RIGHT", "caret-end-line", "ctrl shift LEFT", "selection-begin-line", "ctrl shift KP_LEFT", "selection-begin-line", "ctrl shift RIGHT", "selection-end-line", "ctrl shift KP_RIGHT", "selection-end-line", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "ctrl BACK_SLASH", "unselect", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  468 */     InsetsUIResource insetsUIResource2 = new InsetsUIResource(3, 3, 3, 3);
/*      */     
/*  470 */     UIDefaults.LazyInputMap lazyInputMap3 = new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-previous-word", "ctrl KP_LEFT", "caret-previous-word", "ctrl RIGHT", "caret-next-word", "ctrl KP_RIGHT", "caret-next-word", "ctrl shift LEFT", "selection-previous-word", "ctrl shift KP_LEFT", "selection-previous-word", "ctrl shift RIGHT", "selection-next-word", "ctrl shift KP_RIGHT", "selection-next-word", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "UP", "caret-up", "KP_UP", "caret-up", "DOWN", "caret-down", "KP_DOWN", "caret-down", "PAGE_UP", "page-up", "PAGE_DOWN", "page-down", "shift PAGE_UP", "selection-page-up", "shift PAGE_DOWN", "selection-page-down", "ctrl shift PAGE_UP", "selection-page-left", "ctrl shift PAGE_DOWN", "selection-page-right", "shift UP", "selection-up", "shift KP_UP", "selection-up", "shift DOWN", "selection-down", "shift KP_DOWN", "selection-down", "ENTER", "insert-break", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "TAB", "insert-tab", "ctrl BACK_SLASH", "unselect", "ctrl HOME", "caret-begin", "ctrl END", "caret-end", "ctrl shift HOME", "selection-begin", "ctrl shift END", "selection-end", "ctrl T", "next-link-action", "ctrl shift T", "previous-link-action", "ctrl SPACE", "activate-link-action", "control shift O", "toggle-componentOrientation" });
/*      */     class FontLazyValue
/*      */       implements UIDefaults.LazyValue
/*      */     {
/*      */       private Region region;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       FontLazyValue(Region param1Region) {
/*  538 */         this.region = param1Region;
/*      */       }
/*      */       public Object createValue(UIDefaults param1UIDefaults) {
/*  541 */         GTKStyleFactory gTKStyleFactory = (GTKStyleFactory)SynthLookAndFeel.getStyleFactory();
/*  542 */         GTKStyle gTKStyle = (GTKStyle)gTKStyleFactory.getStyle(null, this.region);
/*  543 */         return gTKStyle.getDefaultFont();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1249 */     Object[] arrayOfObject = { "ArrowButton.size", Integer.valueOf(13), "Button.defaultButtonFollowsFocus", Boolean.FALSE, "Button.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released" }), "Button.font", new FontLazyValue(Region.BUTTON), "Button.margin", insetsUIResource1, "CheckBox.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "CheckBox.icon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getCheckBoxIcon"), "CheckBox.font", new FontLazyValue(this, Region.CHECK_BOX), "CheckBox.margin", insetsUIResource1, "CheckBoxMenuItem.arrowIcon", null, "CheckBoxMenuItem.checkIcon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getCheckBoxMenuItemCheckIcon"), "CheckBoxMenuItem.font", new FontLazyValue(this, Region.CHECK_BOX_MENU_ITEM), "CheckBoxMenuItem.margin", insetsUIResource1, "CheckBoxMenuItem.alignAcceleratorText", Boolean.FALSE, "ColorChooser.showPreviewPanelText", Boolean.FALSE, "ColorChooser.panels", new UIDefaults.ActiveValue() { public Object createValue(UIDefaults param1UIDefaults) { return new AbstractColorChooserPanel[] { new GTKColorChooserPanel() }; } }, "ColorChooser.font", new FontLazyValue(this, Region.COLOR_CHOOSER), "ComboBox.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "hidePopup", "PAGE_UP", "pageUpPassThrough", "PAGE_DOWN", "pageDownPassThrough", "HOME", "homePassThrough", "END", "endPassThrough", "DOWN", "selectNext", "KP_DOWN", "selectNext", "alt DOWN", "togglePopup", "alt KP_DOWN", "togglePopup", "alt UP", "togglePopup", "alt KP_UP", "togglePopup", "SPACE", "spacePopup", "ENTER", "enterPressed", "UP", "selectPrevious", "KP_UP", "selectPrevious" }), "ComboBox.font", new FontLazyValue(this, Region.COMBO_BOX), "ComboBox.isEnterSelectablePopup", Boolean.TRUE, "EditorPane.caretForeground", color4, "EditorPane.caretAspectRatio", double_, "EditorPane.caretBlinkRate", integer2, "EditorPane.margin", insetsUIResource2, "EditorPane.focusInputMap", lazyInputMap3, "EditorPane.font", new FontLazyValue(this, Region.EDITOR_PANE), "FileChooser.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "cancelSelection", "ctrl ENTER", "approveSelection" }), "FileChooserUI", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel", "FormattedTextField.caretForeground", color4, "FormattedTextField.caretAspectRatio", double_, "FormattedTextField.caretBlinkRate", integer2, "FormattedTextField.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-previous-word", "ctrl KP_LEFT", "caret-previous-word", "ctrl RIGHT", "caret-next-word", "ctrl KP_RIGHT", "caret-next-word", "ctrl shift LEFT", "selection-previous-word", "ctrl shift KP_LEFT", "selection-previous-word", "ctrl shift RIGHT", "selection-next-word", "ctrl shift KP_RIGHT", "selection-next-word", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "ctrl BACK_SLASH", "unselect", "control shift O", "toggle-componentOrientation", "ESCAPE", "reset-field-edit", "UP", "increment", "KP_UP", "increment", "DOWN", "decrement", "KP_DOWN", "decrement" }), "FormattedTextField.font", new FontLazyValue(this, Region.FORMATTED_TEXT_FIELD), "InternalFrameTitlePane.titlePaneLayout", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.Metacity", "getTitlePaneLayout"), "InternalFrame.windowBindings", { "shift ESCAPE", "showSystemMenu", "ctrl SPACE", "showSystemMenu", "ESCAPE", "hideSystemMenu" }, "InternalFrame.layoutTitlePaneAtOrigin", Boolean.TRUE, "InternalFrame.useTaskBar", Boolean.TRUE, "InternalFrameTitlePane.iconifyButtonOpacity", null, "InternalFrameTitlePane.maximizeButtonOpacity", null, "InternalFrameTitlePane.closeButtonOpacity", null, "Label.font", new FontLazyValue(this, Region.LABEL), "List.background", color1, "List.focusCellHighlightBorder", gTKLazyValue1, "List.focusSelectedCellHighlightBorder", gTKLazyValue2, "List.noFocusBorder", gTKLazyValue3, "List.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "UP", "selectPreviousRow", "KP_UP", "selectPreviousRow", "shift UP", "selectPreviousRowExtendSelection", "shift KP_UP", "selectPreviousRowExtendSelection", "ctrl shift UP", "selectPreviousRowExtendSelection", "ctrl shift KP_UP", "selectPreviousRowExtendSelection", "ctrl UP", "selectPreviousRowChangeLead", "ctrl KP_UP", "selectPreviousRowChangeLead", "DOWN", "selectNextRow", "KP_DOWN", "selectNextRow", "shift DOWN", "selectNextRowExtendSelection", "shift KP_DOWN", "selectNextRowExtendSelection", "ctrl shift DOWN", "selectNextRowExtendSelection", "ctrl shift KP_DOWN", "selectNextRowExtendSelection", "ctrl DOWN", "selectNextRowChangeLead", "ctrl KP_DOWN", "selectNextRowChangeLead", "LEFT", "selectPreviousColumn", "KP_LEFT", "selectPreviousColumn", "shift LEFT", "selectPreviousColumnExtendSelection", "shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl shift LEFT", "selectPreviousColumnExtendSelection", "ctrl shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl LEFT", "selectPreviousColumnChangeLead", "ctrl KP_LEFT", "selectPreviousColumnChangeLead", "RIGHT", "selectNextColumn", "KP_RIGHT", "selectNextColumn", "shift RIGHT", "selectNextColumnExtendSelection", "shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl shift RIGHT", "selectNextColumnExtendSelection", "ctrl shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl RIGHT", "selectNextColumnChangeLead", "ctrl KP_RIGHT", "selectNextColumnChangeLead", "HOME", "selectFirstRow", "shift HOME", "selectFirstRowExtendSelection", "ctrl shift HOME", "selectFirstRowExtendSelection", "ctrl HOME", "selectFirstRowChangeLead", "END", "selectLastRow", "shift END", "selectLastRowExtendSelection", "ctrl shift END", "selectLastRowExtendSelection", "ctrl END", "selectLastRowChangeLead", "PAGE_UP", "scrollUp", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollUpExtendSelection", "ctrl PAGE_UP", "scrollUpChangeLead", "PAGE_DOWN", "scrollDown", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl PAGE_DOWN", "scrollDownChangeLead", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo" }), "List.focusInputMap.RightToLeft", new UIDefaults.LazyInputMap(new Object[] { "LEFT", "selectNextColumn", "KP_LEFT", "selectNextColumn", "shift LEFT", "selectNextColumnExtendSelection", "shift KP_LEFT", "selectNextColumnExtendSelection", "ctrl shift LEFT", "selectNextColumnExtendSelection", "ctrl shift KP_LEFT", "selectNextColumnExtendSelection", "ctrl LEFT", "selectNextColumnChangeLead", "ctrl KP_LEFT", "selectNextColumnChangeLead", "RIGHT", "selectPreviousColumn", "KP_RIGHT", "selectPreviousColumn", "shift RIGHT", "selectPreviousColumnExtendSelection", "shift KP_RIGHT", "selectPreviousColumnExtendSelection", "ctrl shift RIGHT", "selectPreviousColumnExtendSelection", "ctrl shift KP_RIGHT", "selectPreviousColumnExtendSelection", "ctrl RIGHT", "selectPreviousColumnChangeLead", "ctrl KP_RIGHT", "selectPreviousColumnChangeLead" }), "List.font", new FontLazyValue(this, Region.LIST), "List.rendererUseUIBorder", Boolean.FALSE, "Menu.arrowIcon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getMenuArrowIcon"), "Menu.checkIcon", null, "Menu.font", new FontLazyValue(this, Region.MENU), "Menu.margin", insetsUIResource1, "Menu.cancelMode", "hideMenuTree", "Menu.alignAcceleratorText", Boolean.FALSE, "Menu.useMenuBarForTopLevelMenus", Boolean.TRUE, "MenuBar.windowBindings", { "F10", "takeFocus" }, "MenuBar.font", new FontLazyValue(this, Region.MENU_BAR), "MenuItem.arrowIcon", null, "MenuItem.checkIcon", null, "MenuItem.font", new FontLazyValue(this, Region.MENU_ITEM), "MenuItem.margin", insetsUIResource1, "MenuItem.alignAcceleratorText", Boolean.FALSE, "OptionPane.setButtonMargin", Boolean.FALSE, "OptionPane.sameSizeButtons", Boolean.TRUE, "OptionPane.buttonOrientation", new Integer(4), "OptionPane.minimumSize", new DimensionUIResource(262, 90), "OptionPane.buttonPadding", new Integer(10), "OptionPane.windowBindings", { "ESCAPE", "close" }, "OptionPane.buttonClickThreshhold", new Integer(500), "OptionPane.isYesLast", Boolean.TRUE, "OptionPane.font", new FontLazyValue(this, Region.OPTION_PANE), "Panel.font", new FontLazyValue(this, Region.PANEL), "PasswordField.caretForeground", color4, "PasswordField.caretAspectRatio", double_, "PasswordField.caretBlinkRate", integer2, "PasswordField.margin", insetsUIResource1, "PasswordField.focusInputMap", lazyInputMap2, "PasswordField.font", new FontLazyValue(this, Region.PASSWORD_FIELD), "PopupMenu.consumeEventOnClose", Boolean.TRUE, "PopupMenu.selectedWindowInputMapBindings", { "ESCAPE", "cancel", "DOWN", "selectNext", "KP_DOWN", "selectNext", "UP", "selectPrevious", "KP_UP", "selectPrevious", "LEFT", "selectParent", "KP_LEFT", "selectParent", "RIGHT", "selectChild", "KP_RIGHT", "selectChild", "ENTER", "return", "SPACE", "return" }, "PopupMenu.selectedWindowInputMapBindings.RightToLeft", { "LEFT", "selectChild", "KP_LEFT", "selectChild", "RIGHT", "selectParent", "KP_RIGHT", "selectParent" }, "PopupMenu.font", new FontLazyValue(this, Region.POPUP_MENU), "ProgressBar.horizontalSize", new DimensionUIResource(k, m), "ProgressBar.verticalSize", new DimensionUIResource(n, i1), "ProgressBar.font", new FontLazyValue(this, Region.PROGRESS_BAR), "RadioButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "RETURN", "pressed" }), "RadioButton.icon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getRadioButtonIcon"), "RadioButton.font", new FontLazyValue(this, Region.RADIO_BUTTON), "RadioButton.margin", insetsUIResource1, "RadioButtonMenuItem.arrowIcon", null, "RadioButtonMenuItem.checkIcon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getRadioButtonMenuItemCheckIcon"), "RadioButtonMenuItem.font", new FontLazyValue(this, Region.RADIO_BUTTON_MENU_ITEM), "RadioButtonMenuItem.margin", insetsUIResource1, "RadioButtonMenuItem.alignAcceleratorText", Boolean.FALSE, "RootPane.defaultButtonWindowKeyBindings", { "ENTER", "press", "released ENTER", "release", "ctrl ENTER", "press", "ctrl released ENTER", "release" }, "ScrollBar.squareButtons", Boolean.FALSE, "ScrollBar.thumbHeight", Integer.valueOf(14), "ScrollBar.width", Integer.valueOf(16), "ScrollBar.minimumThumbSize", new Dimension(8, 8), "ScrollBar.maximumThumbSize", new Dimension(4096, 4096), "ScrollBar.allowsAbsolutePositioning", Boolean.TRUE, "ScrollBar.alwaysShowThumb", Boolean.TRUE, "ScrollBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "positiveUnitIncrement", "KP_RIGHT", "positiveUnitIncrement", "DOWN", "positiveUnitIncrement", "KP_DOWN", "positiveUnitIncrement", "PAGE_DOWN", "positiveBlockIncrement", "LEFT", "negativeUnitIncrement", "KP_LEFT", "negativeUnitIncrement", "UP", "negativeUnitIncrement", "KP_UP", "negativeUnitIncrement", "PAGE_UP", "negativeBlockIncrement", "HOME", "minScroll", "END", "maxScroll" }), "ScrollBar.ancestorInputMap.RightToLeft", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "negativeUnitIncrement", "KP_RIGHT", "negativeUnitIncrement", "LEFT", "positiveUnitIncrement", "KP_LEFT", "positiveUnitIncrement" }), "Spinner.disableOnBoundaryValues", Boolean.TRUE, "ScrollPane.fillUpperCorner", Boolean.TRUE, "ScrollPane.fillLowerCorner", Boolean.TRUE, "ScrollPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "unitScrollRight", "KP_RIGHT", "unitScrollRight", "DOWN", "unitScrollDown", "KP_DOWN", "unitScrollDown", "LEFT", "unitScrollLeft", "KP_LEFT", "unitScrollLeft", "UP", "unitScrollUp", "KP_UP", "unitScrollUp", "PAGE_UP", "scrollUp", "PAGE_DOWN", "scrollDown", "ctrl PAGE_UP", "scrollLeft", "ctrl PAGE_DOWN", "scrollRight", "ctrl HOME", "scrollHome", "ctrl END", "scrollEnd" }), "ScrollPane.ancestorInputMap.RightToLeft", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_UP", "scrollRight", "ctrl PAGE_DOWN", "scrollLeft" }), "ScrollPane.font", new FontLazyValue(this, Region.SCROLL_PANE), "Separator.insets", insetsUIResource1, "Separator.thickness", Integer.valueOf(2), "Slider.paintValue", Boolean.TRUE, "Slider.thumbWidth", Integer.valueOf(30), "Slider.thumbHeight", Integer.valueOf(14), "Slider.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "positiveUnitIncrement", "KP_RIGHT", "positiveUnitIncrement", "DOWN", "negativeUnitIncrement", "KP_DOWN", "negativeUnitIncrement", "PAGE_DOWN", "negativeBlockIncrement", "LEFT", "negativeUnitIncrement", "KP_LEFT", "negativeUnitIncrement", "UP", "positiveUnitIncrement", "KP_UP", "positiveUnitIncrement", "PAGE_UP", "positiveBlockIncrement", "HOME", "minScroll", "END", "maxScroll" }), "Slider.focusInputMap.RightToLeft", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "negativeUnitIncrement", "KP_RIGHT", "negativeUnitIncrement", "LEFT", "positiveUnitIncrement", "KP_LEFT", "positiveUnitIncrement" }), "Slider.onlyLeftMouseButtonDrag", Boolean.FALSE, "Spinner.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "increment", "KP_UP", "increment", "DOWN", "decrement", "KP_DOWN", "decrement" }), "Spinner.font", new FontLazyValue(this, Region.SPINNER), "Spinner.editorAlignment", Integer.valueOf(10), "SplitPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "negativeIncrement", "DOWN", "positiveIncrement", "LEFT", "negativeIncrement", "RIGHT", "positiveIncrement", "KP_UP", "negativeIncrement", "KP_DOWN", "positiveIncrement", "KP_LEFT", "negativeIncrement", "KP_RIGHT", "positiveIncrement", "HOME", "selectMin", "END", "selectMax", "F8", "startResize", "F6", "toggleFocus", "ctrl TAB", "focusOutForward", "ctrl shift TAB", "focusOutBackward" }), "SplitPane.size", Integer.valueOf(7), "SplitPane.oneTouchOffset", Integer.valueOf(2), "SplitPane.oneTouchButtonSize", Integer.valueOf(5), "SplitPane.supportsOneTouchButtons", Boolean.FALSE, "TabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent", "SPACE", "selectTabWithFocus" }), "TabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl TAB", "navigateNext", "ctrl shift TAB", "navigatePrevious", "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "TabbedPane.labelShift", Integer.valueOf(3), "TabbedPane.selectedLabelShift", Integer.valueOf(3), "TabbedPane.font", new FontLazyValue(this, Region.TABBED_PANE), "TabbedPane.selectedTabPadInsets", new InsetsUIResource(2, 2, 0, 1), "Table.scrollPaneBorder", swingLazyValue, "Table.background", color1, "Table.focusCellBackground", color2, "Table.focusCellForeground", color3, "Table.focusCellHighlightBorder", gTKLazyValue1, "Table.focusSelectedCellHighlightBorder", gTKLazyValue2, "Table.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "RIGHT", "selectNextColumn", "KP_RIGHT", "selectNextColumn", "shift RIGHT", "selectNextColumnExtendSelection", "shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl shift RIGHT", "selectNextColumnExtendSelection", "ctrl shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl RIGHT", "selectNextColumnChangeLead", "ctrl KP_RIGHT", "selectNextColumnChangeLead", "LEFT", "selectPreviousColumn", "KP_LEFT", "selectPreviousColumn", "shift LEFT", "selectPreviousColumnExtendSelection", "shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl shift LEFT", "selectPreviousColumnExtendSelection", "ctrl shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl LEFT", "selectPreviousColumnChangeLead", "ctrl KP_LEFT", "selectPreviousColumnChangeLead", "DOWN", "selectNextRow", "KP_DOWN", "selectNextRow", "shift DOWN", "selectNextRowExtendSelection", "shift KP_DOWN", "selectNextRowExtendSelection", "ctrl shift DOWN", "selectNextRowExtendSelection", "ctrl shift KP_DOWN", "selectNextRowExtendSelection", "ctrl DOWN", "selectNextRowChangeLead", "ctrl KP_DOWN", "selectNextRowChangeLead", "UP", "selectPreviousRow", "KP_UP", "selectPreviousRow", "shift UP", "selectPreviousRowExtendSelection", "shift KP_UP", "selectPreviousRowExtendSelection", "ctrl shift UP", "selectPreviousRowExtendSelection", "ctrl shift KP_UP", "selectPreviousRowExtendSelection", "ctrl UP", "selectPreviousRowChangeLead", "ctrl KP_UP", "selectPreviousRowChangeLead", "HOME", "selectFirstColumn", "shift HOME", "selectFirstColumnExtendSelection", "ctrl shift HOME", "selectFirstRowExtendSelection", "ctrl HOME", "selectFirstRow", "END", "selectLastColumn", "shift END", "selectLastColumnExtendSelection", "ctrl shift END", "selectLastRowExtendSelection", "ctrl END", "selectLastRow", "PAGE_UP", "scrollUpChangeSelection", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollLeftExtendSelection", "ctrl PAGE_UP", "scrollLeftChangeSelection", "PAGE_DOWN", "scrollDownChangeSelection", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollRightExtendSelection", "ctrl PAGE_DOWN", "scrollRightChangeSelection", "TAB", "selectNextColumnCell", "shift TAB", "selectPreviousColumnCell", "ENTER", "selectNextRowCell", "shift ENTER", "selectPreviousRowCell", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "ESCAPE", "cancel", "F2", "startEditing", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo", "F8", "focusHeader" }), "Table.ancestorInputMap.RightToLeft", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "selectPreviousColumn", "KP_RIGHT", "selectPreviousColumn", "shift RIGHT", "selectPreviousColumnExtendSelection", "shift KP_RIGHT", "selectPreviousColumnExtendSelection", "ctrl shift RIGHT", "selectPreviousColumnExtendSelection", "ctrl shift KP_RIGHT", "selectPreviousColumnExtendSelection", "shift RIGHT", "selectPreviousColumnChangeLead", "shift KP_RIGHT", "selectPreviousColumnChangeLead", "LEFT", "selectNextColumn", "KP_LEFT", "selectNextColumn", "shift LEFT", "selectNextColumnExtendSelection", "shift KP_LEFT", "selectNextColumnExtendSelection", "ctrl shift LEFT", "selectNextColumnExtendSelection", "ctrl shift KP_LEFT", "selectNextColumnExtendSelection", "ctrl LEFT", "selectNextColumnChangeLead", "ctrl KP_LEFT", "selectNextColumnChangeLead", "ctrl PAGE_UP", "scrollRightChangeSelection", "ctrl PAGE_DOWN", "scrollLeftChangeSelection", "ctrl shift PAGE_UP", "scrollRightExtendSelection", "ctrl shift PAGE_DOWN", "scrollLeftExtendSelection" }), "Table.font", new FontLazyValue(this, Region.TABLE), "Table.ascendingSortIcon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getAscendingSortIcon"), "Table.descendingSortIcon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getDescendingSortIcon"), "TableHeader.font", new FontLazyValue(this, Region.TABLE_HEADER), "TableHeader.alignSorterArrow", Boolean.TRUE, "TextArea.caretForeground", color4, "TextArea.caretAspectRatio", double_, "TextArea.caretBlinkRate", integer2, "TextArea.margin", insetsUIResource1, "TextArea.focusInputMap", lazyInputMap3, "TextArea.font", new FontLazyValue(this, Region.TEXT_AREA), "TextField.caretForeground", color4, "TextField.caretAspectRatio", double_, "TextField.caretBlinkRate", integer2, "TextField.margin", insetsUIResource1, "TextField.focusInputMap", lazyInputMap1, "TextField.font", new FontLazyValue(this, Region.TEXT_FIELD), "TextPane.caretForeground", color4, "TextPane.caretAspectRatio", double_, "TextPane.caretBlinkRate", integer2, "TextPane.margin", insetsUIResource2, "TextPane.focusInputMap", lazyInputMap3, "TextPane.font", new FontLazyValue(this, Region.TEXT_PANE), "TitledBorder.titleColor", color5, "TitledBorder.border", new UIDefaults.LazyValue() { public Object createValue(UIDefaults param1UIDefaults) { return new GTKPainter.TitledBorder(); } }, "ToggleButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "ToggleButton.font", new FontLazyValue(this, Region.TOGGLE_BUTTON), "ToggleButton.margin", insetsUIResource1, "ToolBar.separatorSize", new DimensionUIResource(10, 10), "ToolBar.handleIcon", new UIDefaults.ActiveValue() { public Object createValue(UIDefaults param1UIDefaults) { return GTKIconFactory.getToolBarHandleIcon(); } }, "ToolBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "ToolBar.font", new FontLazyValue(this, Region.TOOL_BAR), "ToolTip.font", new FontLazyValue(this, Region.TOOL_TIP), "Tree.padding", Integer.valueOf(4), "Tree.background", color1, "Tree.drawHorizontalLines", Boolean.FALSE, "Tree.drawVerticalLines", Boolean.FALSE, "Tree.rowHeight", Integer.valueOf(-1), "Tree.scrollsOnExpand", Boolean.FALSE, "Tree.expanderSize", Integer.valueOf(10), "Tree.repaintWholeRow", Boolean.TRUE, "Tree.closedIcon", null, "Tree.leafIcon", null, "Tree.openIcon", null, "Tree.expandedIcon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getTreeExpandedIcon"), "Tree.collapsedIcon", new GTKStyle.GTKLazyValue("com.sun.java.swing.plaf.gtk.GTKIconFactory", "getTreeCollapsedIcon"), "Tree.leftChildIndent", Integer.valueOf(2), "Tree.rightChildIndent", Integer.valueOf(12), "Tree.scrollsHorizontallyAndVertically", Boolean.FALSE, "Tree.drawsFocusBorder", Boolean.TRUE, "Tree.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "UP", "selectPrevious", "KP_UP", "selectPrevious", "shift UP", "selectPreviousExtendSelection", "shift KP_UP", "selectPreviousExtendSelection", "ctrl shift UP", "selectPreviousExtendSelection", "ctrl shift KP_UP", "selectPreviousExtendSelection", "ctrl UP", "selectPreviousChangeLead", "ctrl KP_UP", "selectPreviousChangeLead", "DOWN", "selectNext", "KP_DOWN", "selectNext", "shift DOWN", "selectNextExtendSelection", "shift KP_DOWN", "selectNextExtendSelection", "ctrl shift DOWN", "selectNextExtendSelection", "ctrl shift KP_DOWN", "selectNextExtendSelection", "ctrl DOWN", "selectNextChangeLead", "ctrl KP_DOWN", "selectNextChangeLead", "RIGHT", "selectChild", "KP_RIGHT", "selectChild", "LEFT", "selectParent", "KP_LEFT", "selectParent", "typed +", "expand", "typed -", "collapse", "BACK_SPACE", "moveSelectionToParent", "PAGE_UP", "scrollUpChangeSelection", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollUpExtendSelection", "ctrl PAGE_UP", "scrollUpChangeLead", "PAGE_DOWN", "scrollDownChangeSelection", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl PAGE_DOWN", "scrollDownChangeLead", "HOME", "selectFirst", "shift HOME", "selectFirstExtendSelection", "ctrl shift HOME", "selectFirstExtendSelection", "ctrl HOME", "selectFirstChangeLead", "END", "selectLast", "shift END", "selectLastExtendSelection", "ctrl shift END", "selectLastExtendSelection", "ctrl END", "selectLastChangeLead", "F2", "startEditing", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "ctrl LEFT", "scrollLeft", "ctrl KP_LEFT", "scrollLeft", "ctrl RIGHT", "scrollRight", "ctrl KP_RIGHT", "scrollRight", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo" }), "Tree.focusInputMap.RightToLeft", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "selectParent", "KP_RIGHT", "selectParent", "LEFT", "selectChild", "KP_LEFT", "selectChild" }), "Tree.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "cancel" }), "Tree.font", new FontLazyValue(this, Region.TREE), "Viewport.font", new FontLazyValue(this, Region.VIEWPORT) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1330 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */     
/* 1332 */     if (this.fallbackFont != null) {
/* 1333 */       paramUIDefaults.put("TitledBorder.font", this.fallbackFont);
/*      */     }
/* 1335 */     paramUIDefaults.put(SwingUtilities2.AA_TEXT_PROPERTY_KEY, aaTextInfo);
/*      */   }
/*      */   
/*      */   protected void initSystemColorDefaults(UIDefaults paramUIDefaults) {
/* 1339 */     GTKStyleFactory gTKStyleFactory = (GTKStyleFactory)getStyleFactory();
/*      */     
/* 1341 */     GTKStyle gTKStyle1 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.INTERNAL_FRAME);
/* 1342 */     paramUIDefaults.put("window", gTKStyle1.getGTKColor(1, GTKColorType.BACKGROUND));
/*      */     
/* 1344 */     paramUIDefaults.put("windowText", gTKStyle1.getGTKColor(1, GTKColorType.TEXT_FOREGROUND));
/*      */ 
/*      */     
/* 1347 */     GTKStyle gTKStyle2 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.TEXT_FIELD);
/* 1348 */     paramUIDefaults.put("text", gTKStyle2.getGTKColor(1, GTKColorType.TEXT_BACKGROUND));
/*      */     
/* 1350 */     paramUIDefaults.put("textText", gTKStyle2.getGTKColor(1, GTKColorType.TEXT_FOREGROUND));
/*      */     
/* 1352 */     paramUIDefaults.put("textHighlight", gTKStyle2
/* 1353 */         .getGTKColor(512, GTKColorType.TEXT_BACKGROUND));
/*      */     
/* 1355 */     paramUIDefaults.put("textHighlightText", gTKStyle2
/* 1356 */         .getGTKColor(512, GTKColorType.TEXT_FOREGROUND));
/*      */     
/* 1358 */     paramUIDefaults.put("textInactiveText", gTKStyle2
/* 1359 */         .getGTKColor(8, GTKColorType.TEXT_FOREGROUND));
/*      */ 
/*      */     
/* 1362 */     Object object = gTKStyle2.getClassSpecificValue("cursor-color");
/* 1363 */     if (object == null) {
/* 1364 */       object = GTKStyle.BLACK_COLOR;
/*      */     }
/* 1366 */     paramUIDefaults.put("caretColor", object);
/*      */     
/* 1368 */     GTKStyle gTKStyle3 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.MENU_ITEM);
/* 1369 */     paramUIDefaults.put("menu", gTKStyle3.getGTKColor(1, GTKColorType.BACKGROUND));
/*      */     
/* 1371 */     paramUIDefaults.put("menuText", gTKStyle3.getGTKColor(1, GTKColorType.TEXT_FOREGROUND));
/*      */ 
/*      */     
/* 1374 */     GTKStyle gTKStyle4 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.SCROLL_BAR);
/* 1375 */     paramUIDefaults.put("scrollbar", gTKStyle4.getGTKColor(1, GTKColorType.BACKGROUND));
/*      */ 
/*      */     
/* 1378 */     GTKStyle gTKStyle5 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.OPTION_PANE);
/* 1379 */     paramUIDefaults.put("info", gTKStyle5.getGTKColor(1, GTKColorType.BACKGROUND));
/*      */     
/* 1381 */     paramUIDefaults.put("infoText", gTKStyle5.getGTKColor(1, GTKColorType.TEXT_FOREGROUND));
/*      */ 
/*      */     
/* 1384 */     GTKStyle gTKStyle6 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.DESKTOP_PANE);
/* 1385 */     paramUIDefaults.put("desktop", gTKStyle6.getGTKColor(1, GTKColorType.BACKGROUND));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1392 */     GTKStyle gTKStyle7 = (GTKStyle)gTKStyleFactory.getStyle(null, Region.LABEL);
/* 1393 */     Color color = gTKStyle7.getGTKColor(1, GTKColorType.BACKGROUND);
/*      */     
/* 1395 */     paramUIDefaults.put("control", color);
/* 1396 */     paramUIDefaults.put("controlHighlight", color);
/* 1397 */     paramUIDefaults.put("controlText", gTKStyle7.getGTKColor(1, GTKColorType.TEXT_FOREGROUND));
/*      */     
/* 1399 */     paramUIDefaults.put("controlLtHighlight", gTKStyle7.getGTKColor(1, GTKColorType.LIGHT));
/*      */     
/* 1401 */     paramUIDefaults.put("controlShadow", gTKStyle7.getGTKColor(1, GTKColorType.DARK));
/*      */     
/* 1403 */     paramUIDefaults.put("controlDkShadow", gTKStyle7.getGTKColor(1, GTKColorType.BLACK));
/*      */     
/* 1405 */     paramUIDefaults.put("light", gTKStyle7.getGTKColor(1, GTKColorType.LIGHT));
/*      */     
/* 1407 */     paramUIDefaults.put("mid", gTKStyle7.getGTKColor(1, GTKColorType.MID));
/*      */     
/* 1409 */     paramUIDefaults.put("dark", gTKStyle7.getGTKColor(1, GTKColorType.DARK));
/*      */     
/* 1411 */     paramUIDefaults.put("black", gTKStyle7.getGTKColor(1, GTKColorType.BLACK));
/*      */     
/* 1413 */     paramUIDefaults.put("white", gTKStyle7.getGTKColor(1, GTKColorType.WHITE));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 1421 */     String str = paramJComponent.getUIClassID().intern();
/*      */     
/* 1423 */     if (str == "FileChooserUI") {
/* 1424 */       return GTKFileChooserUI.createUI(paramJComponent);
/*      */     }
/* 1426 */     return SynthLookAndFeel.createUI(paramJComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getGtkThemeName() {
/* 1433 */     return gtkThemeName;
/*      */   }
/*      */   
/*      */   static boolean isLeftToRight(Component paramComponent) {
/* 1437 */     return paramComponent.getComponentOrientation().isLeftToRight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialize() {
/* 1448 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 1449 */     if (toolkit instanceof UNIXToolkit && 
/* 1450 */       !((UNIXToolkit)toolkit).loadGTK())
/*      */     {
/* 1452 */       throw new InternalError("Unable to load native GTK libraries");
/*      */     }
/*      */     
/* 1455 */     if (UNIXToolkit.getGtkVersion() == UNIXToolkit.GtkVersions.GTK2) {
/* 1456 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.gtk.version"));
/*      */       
/* 1458 */       if (str != null) {
/* 1459 */         IS_22 = str.equals("2.2");
/*      */       } else {
/* 1461 */         IS_22 = true;
/*      */       } 
/* 1463 */     } else if (UNIXToolkit.getGtkVersion() == UNIXToolkit.GtkVersions.GTK3) {
/*      */       
/* 1465 */       IS_3 = true;
/*      */     } 
/*      */     
/* 1468 */     super.initialize();
/* 1469 */     this.inInitialize = true;
/* 1470 */     loadStyles();
/* 1471 */     this.inInitialize = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1487 */     gtkAAFontSettingsCond = (!isSunCJK && SwingUtilities2.isLocalDisplay());
/* 1488 */     aaTextInfo = SwingUtilities2.AATextInfo.getAATextInfo(gtkAAFontSettingsCond);
/*      */   }
/*      */   
/* 1491 */   static ReferenceQueue<GTKLookAndFeel> queue = new ReferenceQueue<>();
/*      */ 
/*      */   
/*      */   private static void flushUnreferenced() {
/*      */     WeakPCL weakPCL;
/* 1496 */     while ((weakPCL = (WeakPCL)queue.poll()) != null)
/* 1497 */       weakPCL.dispose(); 
/*      */   }
/*      */   
/*      */   static class WeakPCL
/*      */     extends WeakReference<GTKLookAndFeel>
/*      */     implements PropertyChangeListener {
/*      */     private Toolkit kit;
/*      */     private String key;
/*      */     
/*      */     WeakPCL(GTKLookAndFeel param1GTKLookAndFeel, Toolkit param1Toolkit, String param1String) {
/* 1507 */       super(param1GTKLookAndFeel, GTKLookAndFeel.queue);
/* 1508 */       this.kit = param1Toolkit;
/* 1509 */       this.key = param1String;
/*      */     }
/*      */     public String getKey() {
/* 1512 */       return this.key;
/*      */     }
/*      */     public void propertyChange(final PropertyChangeEvent pce) {
/* 1515 */       final GTKLookAndFeel lnf = get();
/*      */       
/* 1517 */       if (gTKLookAndFeel == null || UIManager.getLookAndFeel() != gTKLookAndFeel) {
/*      */ 
/*      */         
/* 1520 */         dispose();
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1525 */         SwingUtilities.invokeLater(new Runnable() {
/*      */               public void run() {
/* 1527 */                 String str = pce.getPropertyName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1534 */                 if ("gnome.Net/ThemeName".equals(str)) {
/* 1535 */                   GTKEngine.INSTANCE.themeChanged();
/* 1536 */                   GTKIconFactory.resetIcons();
/*      */                 } 
/* 1538 */                 lnf.loadStyles();
/* 1539 */                 Window[] arrayOfWindow = Window.getWindows();
/* 1540 */                 for (byte b = 0; b < arrayOfWindow.length; b++) {
/* 1541 */                   SynthLookAndFeel.updateStyles(arrayOfWindow[b]);
/*      */                 }
/*      */               }
/*      */             });
/*      */       } 
/*      */     }
/*      */     
/*      */     void dispose() {
/* 1549 */       this.kit.removePropertyChangeListener(this.key, this);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isSupportedLookAndFeel() {
/* 1554 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 1555 */     return (toolkit instanceof SunToolkit && ((SunToolkit)toolkit)
/* 1556 */       .isNativeGTKAvailable());
/*      */   }
/*      */   
/*      */   public boolean isNativeLookAndFeel() {
/* 1560 */     return true;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/* 1564 */     return "GTK look and feel";
/*      */   }
/*      */   
/*      */   public String getName() {
/* 1568 */     return "GTK look and feel";
/*      */   }
/*      */   
/*      */   public String getID() {
/* 1572 */     return "GTK";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadSystemColors(UIDefaults paramUIDefaults, String[] paramArrayOfString, boolean paramBoolean) {
/* 1578 */     super.loadSystemColors(paramUIDefaults, paramArrayOfString, false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadStyles() {
/* 1583 */     gtkThemeName = (String)Toolkit.getDefaultToolkit().getDesktopProperty("gnome.Net/ThemeName");
/*      */     
/* 1585 */     setStyleFactory(getGTKStyleFactory());
/*      */ 
/*      */ 
/*      */     
/* 1589 */     if (!this.inInitialize) {
/* 1590 */       UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 1591 */       initSystemColorDefaults(uIDefaults);
/* 1592 */       initComponentDefaults(uIDefaults);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private GTKStyleFactory getGTKStyleFactory() {
/* 1598 */     GTKEngine gTKEngine = GTKEngine.INSTANCE;
/* 1599 */     Object object1 = gTKEngine.getSetting(GTKEngine.Settings.GTK_ICON_SIZES);
/* 1600 */     if (object1 instanceof String && 
/* 1601 */       !configIconSizes((String)object1)) {
/* 1602 */       System.err.println("Error parsing gtk-icon-sizes string: '" + object1 + "'");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1607 */     Object object2 = Toolkit.getDefaultToolkit().getDesktopProperty("gnome.Gtk/FontName");
/*      */ 
/*      */     
/* 1610 */     if (!(object2 instanceof String)) {
/* 1611 */       object2 = gTKEngine.getSetting(GTKEngine.Settings.GTK_FONT_NAME);
/* 1612 */       if (!(object2 instanceof String)) {
/* 1613 */         object2 = "sans 10";
/*      */       }
/*      */     } 
/*      */     
/* 1617 */     if (this.styleFactory == null) {
/* 1618 */       this.styleFactory = new GTKStyleFactory();
/*      */     }
/*      */     
/* 1621 */     Font font = PangoFonts.lookupFont((String)object2);
/* 1622 */     this.fallbackFont = font;
/* 1623 */     this.styleFactory.initStyles(font);
/*      */     
/* 1625 */     return this.styleFactory;
/*      */   }
/*      */   
/*      */   private boolean configIconSizes(String paramString) {
/* 1629 */     String[] arrayOfString = paramString.split(":");
/* 1630 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1631 */       int i, j; String[] arrayOfString1 = arrayOfString[b].split("=");
/*      */       
/* 1633 */       if (arrayOfString1.length != 2) {
/* 1634 */         return false;
/*      */       }
/*      */       
/* 1637 */       String str1 = arrayOfString1[0].trim().intern();
/* 1638 */       if (str1.length() < 1) {
/* 1639 */         return false;
/*      */       }
/*      */       
/* 1642 */       arrayOfString1 = arrayOfString1[1].split(",");
/*      */       
/* 1644 */       if (arrayOfString1.length != 2) {
/* 1645 */         return false;
/*      */       }
/*      */       
/* 1648 */       String str2 = arrayOfString1[0].trim();
/* 1649 */       String str3 = arrayOfString1[1].trim();
/*      */       
/* 1651 */       if (str2.length() < 1 || str3.length() < 1) {
/* 1652 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1659 */         i = Integer.parseInt(str2);
/* 1660 */         j = Integer.parseInt(str3);
/* 1661 */       } catch (NumberFormatException numberFormatException) {
/* 1662 */         return false;
/*      */       } 
/*      */       
/* 1665 */       if (i > 0 && j > 0) {
/* 1666 */         int k = GTKStyle.GTKStockIconInfo.getIconType(str1);
/* 1667 */         GTKStyle.GTKStockIconInfo.setIconSize(k, i, j);
/*      */       } else {
/* 1669 */         System.err.println("Invalid size in gtk-icon-sizes: " + i + "," + j);
/*      */       } 
/*      */     } 
/*      */     
/* 1673 */     return true;
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
/*      */   public boolean shouldUpdateStyleOnAncestorChanged() {
/* 1686 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutStyle getLayoutStyle() {
/* 1693 */     return GnomeLayoutStyle.INSTANCE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class GnomeLayoutStyle
/*      */     extends DefaultLayoutStyle
/*      */   {
/* 1705 */     private static GnomeLayoutStyle INSTANCE = new GnomeLayoutStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getPreferredGap(JComponent param1JComponent1, JComponent param1JComponent2, LayoutStyle.ComponentPlacement param1ComponentPlacement, int param1Int, Container param1Container) {
/* 1712 */       super.getPreferredGap(param1JComponent1, param1JComponent2, param1ComponentPlacement, param1Int, param1Container);
/*      */ 
/*      */       
/* 1715 */       switch (param1ComponentPlacement) {
/*      */         case INDENT:
/* 1717 */           if (param1Int == 3 || param1Int == 7)
/*      */           {
/*      */ 
/*      */             
/* 1721 */             return 12;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case RELATED:
/* 1736 */           if (isLabelAndNonlabel(param1JComponent1, param1JComponent2, param1Int)) {
/* 1737 */             return 12;
/*      */           }
/* 1739 */           return 6;
/*      */         case UNRELATED:
/* 1741 */           return 12;
/*      */       } 
/* 1743 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getContainerGap(JComponent param1JComponent, int param1Int, Container param1Container) {
/* 1750 */       super.getContainerGap(param1JComponent, param1Int, param1Container);
/*      */ 
/*      */ 
/*      */       
/* 1754 */       return 12;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKLookAndFeel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */