/*      */ package javax.swing.plaf.metal;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessController;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.DefaultButtonModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.LayoutStyle;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.basic.BasicLookAndFeel;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.OSInfo;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MetalLookAndFeel
/*      */   extends BasicLookAndFeel
/*      */ {
/*      */   private static boolean METAL_LOOK_AND_FEEL_INITED = false;
/*      */   private static boolean checkedWindows;
/*      */   private static boolean isWindows;
/*      */   private static boolean checkedSystemFontSettings;
/*      */   private static boolean useSystemFonts;
/*      */   
/*      */   static boolean isWindows() {
/*  114 */     if (!checkedWindows) {
/*  115 */       OSInfo.OSType oSType = AccessController.<OSInfo.OSType>doPrivileged(OSInfo.getOSTypeAction());
/*  116 */       if (oSType == OSInfo.OSType.WINDOWS) {
/*  117 */         isWindows = true;
/*  118 */         String str = AccessController.<String>doPrivileged(new GetPropertyAction("swing.useSystemFontSettings"));
/*      */ 
/*      */         
/*  121 */         useSystemFonts = (str != null && Boolean.valueOf(str).booleanValue());
/*      */       } 
/*  123 */       checkedWindows = true;
/*      */     } 
/*  125 */     return isWindows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean useSystemFonts() {
/*  133 */     if (isWindows() && useSystemFonts) {
/*  134 */       if (METAL_LOOK_AND_FEEL_INITED) {
/*  135 */         Object object = UIManager.get("Application.useSystemFontSettings");
/*      */ 
/*      */         
/*  138 */         return (object == null || Boolean.TRUE.equals(object));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  145 */       return true;
/*      */     } 
/*  147 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean useHighContrastTheme() {
/*  155 */     if (isWindows() && useSystemFonts()) {
/*      */       
/*  157 */       Boolean bool = (Boolean)Toolkit.getDefaultToolkit().getDesktopProperty("win.highContrast.on");
/*      */       
/*  159 */       return (bool == null) ? false : bool
/*  160 */         .booleanValue();
/*      */     } 
/*  162 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean usingOcean() {
/*  169 */     return getCurrentTheme() instanceof OceanTheme;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  179 */     return "Metal";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getID() {
/*  189 */     return "Metal";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  199 */     return "The Java(tm) Look and Feel";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNativeLookAndFeel() {
/*  209 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSupportedLookAndFeel() {
/*  219 */     return true;
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
/*      */   public boolean getSupportsWindowDecorations() {
/*  234 */     return true;
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
/*      */   protected void initClassDefaults(UIDefaults paramUIDefaults) {
/*  257 */     super.initClassDefaults(paramUIDefaults);
/*      */ 
/*      */     
/*  260 */     Object[] arrayOfObject = { "ButtonUI", "javax.swing.plaf.metal.MetalButtonUI", "CheckBoxUI", "javax.swing.plaf.metal.MetalCheckBoxUI", "ComboBoxUI", "javax.swing.plaf.metal.MetalComboBoxUI", "DesktopIconUI", "javax.swing.plaf.metal.MetalDesktopIconUI", "FileChooserUI", "javax.swing.plaf.metal.MetalFileChooserUI", "InternalFrameUI", "javax.swing.plaf.metal.MetalInternalFrameUI", "LabelUI", "javax.swing.plaf.metal.MetalLabelUI", "PopupMenuSeparatorUI", "javax.swing.plaf.metal.MetalPopupMenuSeparatorUI", "ProgressBarUI", "javax.swing.plaf.metal.MetalProgressBarUI", "RadioButtonUI", "javax.swing.plaf.metal.MetalRadioButtonUI", "ScrollBarUI", "javax.swing.plaf.metal.MetalScrollBarUI", "ScrollPaneUI", "javax.swing.plaf.metal.MetalScrollPaneUI", "SeparatorUI", "javax.swing.plaf.metal.MetalSeparatorUI", "SliderUI", "javax.swing.plaf.metal.MetalSliderUI", "SplitPaneUI", "javax.swing.plaf.metal.MetalSplitPaneUI", "TabbedPaneUI", "javax.swing.plaf.metal.MetalTabbedPaneUI", "TextFieldUI", "javax.swing.plaf.metal.MetalTextFieldUI", "ToggleButtonUI", "javax.swing.plaf.metal.MetalToggleButtonUI", "ToolBarUI", "javax.swing.plaf.metal.MetalToolBarUI", "ToolTipUI", "javax.swing.plaf.metal.MetalToolTipUI", "TreeUI", "javax.swing.plaf.metal.MetalTreeUI", "RootPaneUI", "javax.swing.plaf.metal.MetalRootPaneUI" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  285 */     paramUIDefaults.putDefaults(arrayOfObject);
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
/*      */   protected void initSystemColorDefaults(UIDefaults paramUIDefaults) {
/*  382 */     MetalTheme metalTheme = getCurrentTheme();
/*  383 */     ColorUIResource colorUIResource = metalTheme.getControl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  410 */     Object[] arrayOfObject = { "desktop", metalTheme.getDesktopColor(), "activeCaption", metalTheme.getWindowTitleBackground(), "activeCaptionText", metalTheme.getWindowTitleForeground(), "activeCaptionBorder", metalTheme.getPrimaryControlShadow(), "inactiveCaption", metalTheme.getWindowTitleInactiveBackground(), "inactiveCaptionText", metalTheme.getWindowTitleInactiveForeground(), "inactiveCaptionBorder", metalTheme.getControlShadow(), "window", metalTheme.getWindowBackground(), "windowBorder", colorUIResource, "windowText", metalTheme.getUserTextColor(), "menu", metalTheme.getMenuBackground(), "menuText", metalTheme.getMenuForeground(), "text", metalTheme.getWindowBackground(), "textText", metalTheme.getUserTextColor(), "textHighlight", metalTheme.getTextHighlightColor(), "textHighlightText", metalTheme.getHighlightedTextColor(), "textInactiveText", metalTheme.getInactiveSystemTextColor(), "control", colorUIResource, "controlText", metalTheme.getControlTextColor(), "controlHighlight", metalTheme.getControlHighlight(), "controlLtHighlight", metalTheme.getControlHighlight(), "controlShadow", metalTheme.getControlShadow(), "controlDkShadow", metalTheme.getControlDarkShadow(), "scrollbar", colorUIResource, "info", metalTheme.getPrimaryControl(), "infoText", metalTheme.getPrimaryControlInfo() };
/*      */ 
/*      */     
/*  413 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initResourceBundle(UIDefaults paramUIDefaults) {
/*  421 */     paramUIDefaults.addResourceBundle("com.sun.swing.internal.plaf.metal.resources.metal");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initComponentDefaults(UIDefaults paramUIDefaults) {
/*  431 */     super.initComponentDefaults(paramUIDefaults);
/*      */     
/*  433 */     initResourceBundle(paramUIDefaults);
/*      */     
/*  435 */     ColorUIResource colorUIResource1 = getAcceleratorForeground();
/*  436 */     ColorUIResource colorUIResource2 = getAcceleratorSelectedForeground();
/*  437 */     ColorUIResource colorUIResource3 = getControl();
/*  438 */     ColorUIResource colorUIResource4 = getControlHighlight();
/*  439 */     ColorUIResource colorUIResource5 = getControlShadow();
/*  440 */     ColorUIResource colorUIResource6 = getControlDarkShadow();
/*  441 */     ColorUIResource colorUIResource7 = getControlTextColor();
/*  442 */     ColorUIResource colorUIResource8 = getFocusColor();
/*  443 */     ColorUIResource colorUIResource9 = getInactiveControlTextColor();
/*  444 */     ColorUIResource colorUIResource10 = getMenuBackground();
/*  445 */     ColorUIResource colorUIResource11 = getMenuSelectedBackground();
/*  446 */     ColorUIResource colorUIResource12 = getMenuDisabledForeground();
/*  447 */     ColorUIResource colorUIResource13 = getMenuSelectedForeground();
/*  448 */     ColorUIResource colorUIResource14 = getPrimaryControl();
/*  449 */     ColorUIResource colorUIResource15 = getPrimaryControlDarkShadow();
/*  450 */     ColorUIResource colorUIResource16 = getPrimaryControlShadow();
/*  451 */     ColorUIResource colorUIResource17 = getSystemTextColor();
/*      */     
/*  453 */     InsetsUIResource insetsUIResource1 = new InsetsUIResource(0, 0, 0, 0);
/*      */     
/*  455 */     Integer integer = Integer.valueOf(0);
/*      */     
/*  457 */     SwingLazyValue swingLazyValue1 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders", "getTextFieldBorder");
/*      */ 
/*      */ 
/*      */     
/*  461 */     UIDefaults.LazyValue lazyValue1 = paramUIDefaults -> new MetalBorders.DialogBorder();
/*      */     
/*  463 */     UIDefaults.LazyValue lazyValue2 = paramUIDefaults -> new MetalBorders.QuestionDialogBorder();
/*      */     
/*  465 */     UIDefaults.LazyInputMap lazyInputMap1 = new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-previous-word", "ctrl KP_LEFT", "caret-previous-word", "ctrl RIGHT", "caret-next-word", "ctrl KP_RIGHT", "caret-next-word", "ctrl shift LEFT", "selection-previous-word", "ctrl shift KP_LEFT", "selection-previous-word", "ctrl shift RIGHT", "selection-next-word", "ctrl shift KP_RIGHT", "selection-next-word", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "ctrl BACK_SLASH", "unselect", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  507 */     UIDefaults.LazyInputMap lazyInputMap2 = new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-begin-line", "ctrl KP_LEFT", "caret-begin-line", "ctrl RIGHT", "caret-end-line", "ctrl KP_RIGHT", "caret-end-line", "ctrl shift LEFT", "selection-begin-line", "ctrl shift KP_LEFT", "selection-begin-line", "ctrl shift RIGHT", "selection-end-line", "ctrl shift KP_RIGHT", "selection-end-line", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "ctrl BACK_SLASH", "unselect", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  547 */     UIDefaults.LazyInputMap lazyInputMap3 = new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-previous-word", "ctrl KP_LEFT", "caret-previous-word", "ctrl RIGHT", "caret-next-word", "ctrl KP_RIGHT", "caret-next-word", "ctrl shift LEFT", "selection-previous-word", "ctrl shift KP_LEFT", "selection-previous-word", "ctrl shift RIGHT", "selection-next-word", "ctrl shift KP_RIGHT", "selection-next-word", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "UP", "caret-up", "KP_UP", "caret-up", "DOWN", "caret-down", "KP_DOWN", "caret-down", "PAGE_UP", "page-up", "PAGE_DOWN", "page-down", "shift PAGE_UP", "selection-page-up", "shift PAGE_DOWN", "selection-page-down", "ctrl shift PAGE_UP", "selection-page-left", "ctrl shift PAGE_DOWN", "selection-page-right", "shift UP", "selection-up", "shift KP_UP", "selection-up", "shift DOWN", "selection-down", "shift KP_DOWN", "selection-down", "ENTER", "insert-break", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "TAB", "insert-tab", "ctrl BACK_SLASH", "unselect", "ctrl HOME", "caret-begin", "ctrl END", "caret-end", "ctrl shift HOME", "selection-begin", "ctrl shift END", "selection-end", "ctrl T", "next-link-action", "ctrl shift T", "previous-link-action", "ctrl SPACE", "activate-link-action", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     SwingLazyValue swingLazyValue2 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$ScrollPaneBorder");
/*  613 */     SwingLazyValue swingLazyValue3 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders", "getButtonBorder");
/*      */ 
/*      */ 
/*      */     
/*  617 */     SwingLazyValue swingLazyValue4 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders", "getToggleButtonBorder");
/*      */ 
/*      */ 
/*      */     
/*  621 */     SwingLazyValue swingLazyValue5 = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] { colorUIResource5 });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  626 */     SwingLazyValue swingLazyValue6 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders", "getDesktopIconBorder");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     SwingLazyValue swingLazyValue7 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$MenuBarBorder");
/*      */ 
/*      */ 
/*      */     
/*  635 */     SwingLazyValue swingLazyValue8 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$PopupMenuBorder");
/*      */ 
/*      */     
/*  638 */     SwingLazyValue swingLazyValue9 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$MenuItemBorder");
/*      */ 
/*      */ 
/*      */     
/*  642 */     String str = "-";
/*  643 */     SwingLazyValue swingLazyValue10 = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$ToolBarBorder");
/*      */     
/*  645 */     SwingLazyValue swingLazyValue11 = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] { colorUIResource6, new Integer(1) });
/*      */ 
/*      */ 
/*      */     
/*  649 */     SwingLazyValue swingLazyValue12 = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] { colorUIResource15 });
/*      */ 
/*      */ 
/*      */     
/*  653 */     SwingLazyValue swingLazyValue13 = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] { colorUIResource6 });
/*      */ 
/*      */ 
/*      */     
/*  657 */     SwingLazyValue swingLazyValue14 = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] { colorUIResource8 });
/*      */ 
/*      */ 
/*      */     
/*  661 */     InsetsUIResource insetsUIResource2 = new InsetsUIResource(4, 2, 0, 6);
/*      */     
/*  663 */     InsetsUIResource insetsUIResource3 = new InsetsUIResource(0, 9, 1, 9);
/*      */     
/*  665 */     Object[] arrayOfObject1 = new Object[1];
/*  666 */     arrayOfObject1[0] = new Integer(16);
/*      */     
/*  668 */     Object[] arrayOfObject2 = { "OptionPane.errorSound", "OptionPane.informationSound", "OptionPane.questionSound", "OptionPane.warningSound" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  674 */     MetalTheme metalTheme = getCurrentTheme();
/*  675 */     FontActiveValue fontActiveValue1 = new FontActiveValue(metalTheme, 3);
/*      */     
/*  677 */     FontActiveValue fontActiveValue2 = new FontActiveValue(metalTheme, 0);
/*      */     
/*  679 */     FontActiveValue fontActiveValue3 = new FontActiveValue(metalTheme, 2);
/*      */     
/*  681 */     FontActiveValue fontActiveValue4 = new FontActiveValue(metalTheme, 4);
/*      */     
/*  683 */     FontActiveValue fontActiveValue5 = new FontActiveValue(metalTheme, 5);
/*      */     
/*  685 */     FontActiveValue fontActiveValue6 = new FontActiveValue(metalTheme, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1487 */     Object[] arrayOfObject3 = { "AuditoryCues.defaultCueList", arrayOfObject2, "AuditoryCues.playList", null, "TextField.border", swingLazyValue1, "TextField.font", fontActiveValue3, "PasswordField.border", swingLazyValue1, "PasswordField.font", fontActiveValue3, "PasswordField.echoChar", Character.valueOf('•'), "TextArea.font", fontActiveValue3, "TextPane.background", paramUIDefaults.get("window"), "TextPane.font", fontActiveValue3, "EditorPane.background", paramUIDefaults.get("window"), "EditorPane.font", fontActiveValue3, "TextField.focusInputMap", lazyInputMap1, "PasswordField.focusInputMap", lazyInputMap2, "TextArea.focusInputMap", lazyInputMap3, "TextPane.focusInputMap", lazyInputMap3, "EditorPane.focusInputMap", lazyInputMap3, "FormattedTextField.border", swingLazyValue1, "FormattedTextField.font", fontActiveValue3, "FormattedTextField.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-previous-word", "ctrl KP_LEFT", "caret-previous-word", "ctrl RIGHT", "caret-next-word", "ctrl KP_RIGHT", "caret-next-word", "ctrl shift LEFT", "selection-previous-word", "ctrl shift KP_LEFT", "selection-previous-word", "ctrl shift RIGHT", "selection-next-word", "ctrl shift KP_RIGHT", "selection-next-word", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "ctrl BACK_SLASH", "unselect", "control shift O", "toggle-componentOrientation", "ESCAPE", "reset-field-edit", "UP", "increment", "KP_UP", "increment", "DOWN", "decrement", "KP_DOWN", "decrement" }), "Button.defaultButtonFollowsFocus", Boolean.FALSE, "Button.disabledText", colorUIResource9, "Button.select", colorUIResource5, "Button.border", swingLazyValue3, "Button.font", fontActiveValue2, "Button.focus", colorUIResource8, "Button.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "CheckBox.disabledText", colorUIResource9, "Checkbox.select", colorUIResource5, "CheckBox.font", fontActiveValue2, "CheckBox.focus", colorUIResource8, "CheckBox.icon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getCheckBoxIcon"), "CheckBox.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "CheckBox.totalInsets", new Insets(4, 4, 4, 4), "RadioButton.disabledText", colorUIResource9, "RadioButton.select", colorUIResource5, "RadioButton.icon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getRadioButtonIcon"), "RadioButton.font", fontActiveValue2, "RadioButton.focus", colorUIResource8, "RadioButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "RadioButton.totalInsets", new Insets(4, 4, 4, 4), "ToggleButton.select", colorUIResource5, "ToggleButton.disabledText", colorUIResource9, "ToggleButton.focus", colorUIResource8, "ToggleButton.border", swingLazyValue4, "ToggleButton.font", fontActiveValue2, "ToggleButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "FileView.directoryIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeFolderIcon"), "FileView.fileIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeLeafIcon"), "FileView.computerIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeComputerIcon"), "FileView.hardDriveIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeHardDriveIcon"), "FileView.floppyDriveIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeFloppyDriveIcon"), "FileChooser.detailsViewIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getFileChooserDetailViewIcon"), "FileChooser.homeFolderIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getFileChooserHomeFolderIcon"), "FileChooser.listViewIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getFileChooserListViewIcon"), "FileChooser.newFolderIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getFileChooserNewFolderIcon"), "FileChooser.upFolderIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getFileChooserUpFolderIcon"), "FileChooser.usesSingleFilePane", Boolean.TRUE, "FileChooser.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "cancelSelection", "F2", "editFileName", "F5", "refresh", "BACK_SPACE", "Go Up" }), "ToolTip.font", fontActiveValue6, "ToolTip.border", swingLazyValue12, "ToolTip.borderInactive", swingLazyValue13, "ToolTip.backgroundInactive", colorUIResource3, "ToolTip.foregroundInactive", colorUIResource6, "ToolTip.hideAccelerator", Boolean.FALSE, "ToolTipManager.enableToolTipMode", "activeApplication", "Slider.font", fontActiveValue2, "Slider.border", null, "Slider.foreground", colorUIResource16, "Slider.focus", colorUIResource8, "Slider.focusInsets", insetsUIResource1, "Slider.trackWidth", new Integer(7), "Slider.majorTickLength", new Integer(6), "Slider.horizontalThumbIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getHorizontalSliderThumbIcon"), "Slider.verticalThumbIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getVerticalSliderThumbIcon"), "Slider.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "positiveUnitIncrement", "KP_RIGHT", "positiveUnitIncrement", "DOWN", "negativeUnitIncrement", "KP_DOWN", "negativeUnitIncrement", "PAGE_DOWN", "negativeBlockIncrement", "ctrl PAGE_DOWN", "negativeBlockIncrement", "LEFT", "negativeUnitIncrement", "KP_LEFT", "negativeUnitIncrement", "UP", "positiveUnitIncrement", "KP_UP", "positiveUnitIncrement", "PAGE_UP", "positiveBlockIncrement", "ctrl PAGE_UP", "positiveBlockIncrement", "HOME", "minScroll", "END", "maxScroll" }), "ProgressBar.font", fontActiveValue2, "ProgressBar.foreground", colorUIResource16, "ProgressBar.selectionBackground", colorUIResource15, "ProgressBar.border", swingLazyValue11, "ProgressBar.cellSpacing", integer, "ProgressBar.cellLength", Integer.valueOf(1), "ComboBox.background", colorUIResource3, "ComboBox.foreground", colorUIResource7, "ComboBox.selectionBackground", colorUIResource16, "ComboBox.selectionForeground", colorUIResource7, "ComboBox.font", fontActiveValue2, "ComboBox.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "hidePopup", "PAGE_UP", "pageUpPassThrough", "PAGE_DOWN", "pageDownPassThrough", "HOME", "homePassThrough", "END", "endPassThrough", "DOWN", "selectNext", "KP_DOWN", "selectNext", "alt DOWN", "togglePopup", "alt KP_DOWN", "togglePopup", "alt UP", "togglePopup", "alt KP_UP", "togglePopup", "SPACE", "spacePopup", "ENTER", "enterPressed", "UP", "selectPrevious", "KP_UP", "selectPrevious" }), "InternalFrame.icon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getInternalFrameDefaultMenuIcon"), "InternalFrame.border", new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$InternalFrameBorder"), "InternalFrame.optionDialogBorder", new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$OptionDialogBorder"), "InternalFrame.paletteBorder", new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$PaletteBorder"), "InternalFrame.paletteTitleHeight", new Integer(11), "InternalFrame.paletteCloseIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory$PaletteCloseIcon"), "InternalFrame.closeIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getInternalFrameCloseIcon", arrayOfObject1), "InternalFrame.maximizeIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getInternalFrameMaximizeIcon", arrayOfObject1), "InternalFrame.iconifyIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getInternalFrameMinimizeIcon", arrayOfObject1), "InternalFrame.minimizeIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getInternalFrameAltMaximizeIcon", arrayOfObject1), "InternalFrame.titleFont", fontActiveValue4, "InternalFrame.windowBindings", null, "InternalFrame.closeSound", "sounds/FrameClose.wav", "InternalFrame.maximizeSound", "sounds/FrameMaximize.wav", "InternalFrame.minimizeSound", "sounds/FrameMinimize.wav", "InternalFrame.restoreDownSound", "sounds/FrameRestoreDown.wav", "InternalFrame.restoreUpSound", "sounds/FrameRestoreUp.wav", "DesktopIcon.border", swingLazyValue6, "DesktopIcon.font", fontActiveValue2, "DesktopIcon.foreground", colorUIResource7, "DesktopIcon.background", colorUIResource3, "DesktopIcon.width", Integer.valueOf(160), "Desktop.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl F5", "restore", "ctrl F4", "close", "ctrl F7", "move", "ctrl F8", "resize", "RIGHT", "right", "KP_RIGHT", "right", "shift RIGHT", "shrinkRight", "shift KP_RIGHT", "shrinkRight", "LEFT", "left", "KP_LEFT", "left", "shift LEFT", "shrinkLeft", "shift KP_LEFT", "shrinkLeft", "UP", "up", "KP_UP", "up", "shift UP", "shrinkUp", "shift KP_UP", "shrinkUp", "DOWN", "down", "KP_DOWN", "down", "shift DOWN", "shrinkDown", "shift KP_DOWN", "shrinkDown", "ESCAPE", "escape", "ctrl F9", "minimize", "ctrl F10", "maximize", "ctrl F6", "selectNextFrame", "ctrl TAB", "selectNextFrame", "ctrl alt F6", "selectNextFrame", "shift ctrl alt F6", "selectPreviousFrame", "ctrl F12", "navigateNext", "shift ctrl F12", "navigatePrevious" }), "TitledBorder.font", fontActiveValue2, "TitledBorder.titleColor", colorUIResource17, "TitledBorder.border", swingLazyValue5, "Label.font", fontActiveValue2, "Label.foreground", colorUIResource17, "Label.disabledForeground", getInactiveSystemTextColor(), "List.font", fontActiveValue2, "List.focusCellHighlightBorder", swingLazyValue14, "List.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "UP", "selectPreviousRow", "KP_UP", "selectPreviousRow", "shift UP", "selectPreviousRowExtendSelection", "shift KP_UP", "selectPreviousRowExtendSelection", "ctrl shift UP", "selectPreviousRowExtendSelection", "ctrl shift KP_UP", "selectPreviousRowExtendSelection", "ctrl UP", "selectPreviousRowChangeLead", "ctrl KP_UP", "selectPreviousRowChangeLead", "DOWN", "selectNextRow", "KP_DOWN", "selectNextRow", "shift DOWN", "selectNextRowExtendSelection", "shift KP_DOWN", "selectNextRowExtendSelection", "ctrl shift DOWN", "selectNextRowExtendSelection", "ctrl shift KP_DOWN", "selectNextRowExtendSelection", "ctrl DOWN", "selectNextRowChangeLead", "ctrl KP_DOWN", "selectNextRowChangeLead", "LEFT", "selectPreviousColumn", "KP_LEFT", "selectPreviousColumn", "shift LEFT", "selectPreviousColumnExtendSelection", "shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl shift LEFT", "selectPreviousColumnExtendSelection", "ctrl shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl LEFT", "selectPreviousColumnChangeLead", "ctrl KP_LEFT", "selectPreviousColumnChangeLead", "RIGHT", "selectNextColumn", "KP_RIGHT", "selectNextColumn", "shift RIGHT", "selectNextColumnExtendSelection", "shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl shift RIGHT", "selectNextColumnExtendSelection", "ctrl shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl RIGHT", "selectNextColumnChangeLead", "ctrl KP_RIGHT", "selectNextColumnChangeLead", "HOME", "selectFirstRow", "shift HOME", "selectFirstRowExtendSelection", "ctrl shift HOME", "selectFirstRowExtendSelection", "ctrl HOME", "selectFirstRowChangeLead", "END", "selectLastRow", "shift END", "selectLastRowExtendSelection", "ctrl shift END", "selectLastRowExtendSelection", "ctrl END", "selectLastRowChangeLead", "PAGE_UP", "scrollUp", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollUpExtendSelection", "ctrl PAGE_UP", "scrollUpChangeLead", "PAGE_DOWN", "scrollDown", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl PAGE_DOWN", "scrollDownChangeLead", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo" }), "ScrollBar.background", colorUIResource3, "ScrollBar.highlight", colorUIResource4, "ScrollBar.shadow", colorUIResource5, "ScrollBar.darkShadow", colorUIResource6, "ScrollBar.thumb", colorUIResource16, "ScrollBar.thumbShadow", colorUIResource15, "ScrollBar.thumbHighlight", colorUIResource14, "ScrollBar.width", new Integer(17), "ScrollBar.allowsAbsolutePositioning", Boolean.TRUE, "ScrollBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "positiveUnitIncrement", "KP_RIGHT", "positiveUnitIncrement", "DOWN", "positiveUnitIncrement", "KP_DOWN", "positiveUnitIncrement", "PAGE_DOWN", "positiveBlockIncrement", "LEFT", "negativeUnitIncrement", "KP_LEFT", "negativeUnitIncrement", "UP", "negativeUnitIncrement", "KP_UP", "negativeUnitIncrement", "PAGE_UP", "negativeBlockIncrement", "HOME", "minScroll", "END", "maxScroll" }), "ScrollPane.border", swingLazyValue2, "ScrollPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "unitScrollRight", "KP_RIGHT", "unitScrollRight", "DOWN", "unitScrollDown", "KP_DOWN", "unitScrollDown", "LEFT", "unitScrollLeft", "KP_LEFT", "unitScrollLeft", "UP", "unitScrollUp", "KP_UP", "unitScrollUp", "PAGE_UP", "scrollUp", "PAGE_DOWN", "scrollDown", "ctrl PAGE_UP", "scrollLeft", "ctrl PAGE_DOWN", "scrollRight", "ctrl HOME", "scrollHome", "ctrl END", "scrollEnd" }), "TabbedPane.font", fontActiveValue2, "TabbedPane.tabAreaBackground", colorUIResource3, "TabbedPane.background", colorUIResource5, "TabbedPane.light", colorUIResource3, "TabbedPane.focus", colorUIResource15, "TabbedPane.selected", colorUIResource3, "TabbedPane.selectHighlight", colorUIResource4, "TabbedPane.tabAreaInsets", insetsUIResource2, "TabbedPane.tabInsets", insetsUIResource3, "TabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "TabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "Table.font", fontActiveValue3, "Table.focusCellHighlightBorder", swingLazyValue14, "Table.scrollPaneBorder", swingLazyValue2, "Table.dropLineColor", colorUIResource8, "Table.dropLineShortColor", colorUIResource15, "Table.gridColor", colorUIResource5, "Table.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "RIGHT", "selectNextColumn", "KP_RIGHT", "selectNextColumn", "shift RIGHT", "selectNextColumnExtendSelection", "shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl shift RIGHT", "selectNextColumnExtendSelection", "ctrl shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl RIGHT", "selectNextColumnChangeLead", "ctrl KP_RIGHT", "selectNextColumnChangeLead", "LEFT", "selectPreviousColumn", "KP_LEFT", "selectPreviousColumn", "shift LEFT", "selectPreviousColumnExtendSelection", "shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl shift LEFT", "selectPreviousColumnExtendSelection", "ctrl shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl LEFT", "selectPreviousColumnChangeLead", "ctrl KP_LEFT", "selectPreviousColumnChangeLead", "DOWN", "selectNextRow", "KP_DOWN", "selectNextRow", "shift DOWN", "selectNextRowExtendSelection", "shift KP_DOWN", "selectNextRowExtendSelection", "ctrl shift DOWN", "selectNextRowExtendSelection", "ctrl shift KP_DOWN", "selectNextRowExtendSelection", "ctrl DOWN", "selectNextRowChangeLead", "ctrl KP_DOWN", "selectNextRowChangeLead", "UP", "selectPreviousRow", "KP_UP", "selectPreviousRow", "shift UP", "selectPreviousRowExtendSelection", "shift KP_UP", "selectPreviousRowExtendSelection", "ctrl shift UP", "selectPreviousRowExtendSelection", "ctrl shift KP_UP", "selectPreviousRowExtendSelection", "ctrl UP", "selectPreviousRowChangeLead", "ctrl KP_UP", "selectPreviousRowChangeLead", "HOME", "selectFirstColumn", "shift HOME", "selectFirstColumnExtendSelection", "ctrl shift HOME", "selectFirstRowExtendSelection", "ctrl HOME", "selectFirstRow", "END", "selectLastColumn", "shift END", "selectLastColumnExtendSelection", "ctrl shift END", "selectLastRowExtendSelection", "ctrl END", "selectLastRow", "PAGE_UP", "scrollUpChangeSelection", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollLeftExtendSelection", "ctrl PAGE_UP", "scrollLeftChangeSelection", "PAGE_DOWN", "scrollDownChangeSelection", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollRightExtendSelection", "ctrl PAGE_DOWN", "scrollRightChangeSelection", "TAB", "selectNextColumnCell", "shift TAB", "selectPreviousColumnCell", "ENTER", "selectNextRowCell", "shift ENTER", "selectPreviousRowCell", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "ESCAPE", "cancel", "F2", "startEditing", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo", "F8", "focusHeader" }), "Table.ascendingSortIcon", SwingUtilities2.makeIcon(getClass(), MetalLookAndFeel.class, "icons/sortUp.png"), "Table.descendingSortIcon", SwingUtilities2.makeIcon(getClass(), MetalLookAndFeel.class, "icons/sortDown.png"), "TableHeader.font", fontActiveValue3, "TableHeader.cellBorder", new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$TableHeaderBorder"), "MenuBar.border", swingLazyValue7, "MenuBar.font", fontActiveValue1, "MenuBar.windowBindings", { "F10", "takeFocus" }, "Menu.border", swingLazyValue9, "Menu.borderPainted", Boolean.TRUE, "Menu.menuPopupOffsetX", integer, "Menu.menuPopupOffsetY", integer, "Menu.submenuPopupOffsetX", new Integer(-4), "Menu.submenuPopupOffsetY", new Integer(-3), "Menu.font", fontActiveValue1, "Menu.selectionForeground", colorUIResource13, "Menu.selectionBackground", colorUIResource11, "Menu.disabledForeground", colorUIResource12, "Menu.acceleratorFont", fontActiveValue5, "Menu.acceleratorForeground", colorUIResource1, "Menu.acceleratorSelectionForeground", colorUIResource2, "Menu.checkIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getMenuItemCheckIcon"), "Menu.arrowIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getMenuArrowIcon"), "MenuItem.border", swingLazyValue9, "MenuItem.borderPainted", Boolean.TRUE, "MenuItem.font", fontActiveValue1, "MenuItem.selectionForeground", colorUIResource13, "MenuItem.selectionBackground", colorUIResource11, "MenuItem.disabledForeground", colorUIResource12, "MenuItem.acceleratorFont", fontActiveValue5, "MenuItem.acceleratorForeground", colorUIResource1, "MenuItem.acceleratorSelectionForeground", colorUIResource2, "MenuItem.acceleratorDelimiter", str, "MenuItem.checkIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getMenuItemCheckIcon"), "MenuItem.arrowIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getMenuItemArrowIcon"), "MenuItem.commandSound", "sounds/MenuItemCommand.wav", "OptionPane.windowBindings", { "ESCAPE", "close" }, "OptionPane.informationSound", "sounds/OptionPaneInformation.wav", "OptionPane.warningSound", "sounds/OptionPaneWarning.wav", "OptionPane.errorSound", "sounds/OptionPaneError.wav", "OptionPane.questionSound", "sounds/OptionPaneQuestion.wav", "OptionPane.errorDialog.border.background", new ColorUIResource(153, 51, 51), "OptionPane.errorDialog.titlePane.foreground", new ColorUIResource(51, 0, 0), "OptionPane.errorDialog.titlePane.background", new ColorUIResource(255, 153, 153), "OptionPane.errorDialog.titlePane.shadow", new ColorUIResource(204, 102, 102), "OptionPane.questionDialog.border.background", new ColorUIResource(51, 102, 51), "OptionPane.questionDialog.titlePane.foreground", new ColorUIResource(0, 51, 0), "OptionPane.questionDialog.titlePane.background", new ColorUIResource(153, 204, 153), "OptionPane.questionDialog.titlePane.shadow", new ColorUIResource(102, 153, 102), "OptionPane.warningDialog.border.background", new ColorUIResource(153, 102, 51), "OptionPane.warningDialog.titlePane.foreground", new ColorUIResource(102, 51, 0), "OptionPane.warningDialog.titlePane.background", new ColorUIResource(255, 204, 153), "OptionPane.warningDialog.titlePane.shadow", new ColorUIResource(204, 153, 102), "Separator.background", getSeparatorBackground(), "Separator.foreground", getSeparatorForeground(), "PopupMenu.border", swingLazyValue8, "PopupMenu.popupSound", "sounds/PopupMenuPopup.wav", "PopupMenu.font", fontActiveValue1, "CheckBoxMenuItem.border", swingLazyValue9, "CheckBoxMenuItem.borderPainted", Boolean.TRUE, "CheckBoxMenuItem.font", fontActiveValue1, "CheckBoxMenuItem.selectionForeground", colorUIResource13, "CheckBoxMenuItem.selectionBackground", colorUIResource11, "CheckBoxMenuItem.disabledForeground", colorUIResource12, "CheckBoxMenuItem.acceleratorFont", fontActiveValue5, "CheckBoxMenuItem.acceleratorForeground", colorUIResource1, "CheckBoxMenuItem.acceleratorSelectionForeground", colorUIResource2, "CheckBoxMenuItem.checkIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getCheckBoxMenuItemIcon"), "CheckBoxMenuItem.arrowIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getMenuItemArrowIcon"), "CheckBoxMenuItem.commandSound", "sounds/MenuItemCommand.wav", "RadioButtonMenuItem.border", swingLazyValue9, "RadioButtonMenuItem.borderPainted", Boolean.TRUE, "RadioButtonMenuItem.font", fontActiveValue1, "RadioButtonMenuItem.selectionForeground", colorUIResource13, "RadioButtonMenuItem.selectionBackground", colorUIResource11, "RadioButtonMenuItem.disabledForeground", colorUIResource12, "RadioButtonMenuItem.acceleratorFont", fontActiveValue5, "RadioButtonMenuItem.acceleratorForeground", colorUIResource1, "RadioButtonMenuItem.acceleratorSelectionForeground", colorUIResource2, "RadioButtonMenuItem.checkIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getRadioButtonMenuItemIcon"), "RadioButtonMenuItem.arrowIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getMenuItemArrowIcon"), "RadioButtonMenuItem.commandSound", "sounds/MenuItemCommand.wav", "Spinner.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "increment", "KP_UP", "increment", "DOWN", "decrement", "KP_DOWN", "decrement" }), "Spinner.arrowButtonInsets", insetsUIResource1, "Spinner.border", swingLazyValue1, "Spinner.arrowButtonBorder", swingLazyValue3, "Spinner.font", fontActiveValue2, "SplitPane.dividerSize", new Integer(10), "SplitPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "negativeIncrement", "DOWN", "positiveIncrement", "LEFT", "negativeIncrement", "RIGHT", "positiveIncrement", "KP_UP", "negativeIncrement", "KP_DOWN", "positiveIncrement", "KP_LEFT", "negativeIncrement", "KP_RIGHT", "positiveIncrement", "HOME", "selectMin", "END", "selectMax", "F8", "startResize", "F6", "toggleFocus", "ctrl TAB", "focusOutForward", "ctrl shift TAB", "focusOutBackward" }), "SplitPane.centerOneTouchButtons", Boolean.FALSE, "SplitPane.dividerFocusColor", colorUIResource14, "Tree.font", fontActiveValue3, "Tree.textBackground", getWindowBackground(), "Tree.selectionBorderColor", colorUIResource8, "Tree.openIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeFolderIcon"), "Tree.closedIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeFolderIcon"), "Tree.leafIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeLeafIcon"), "Tree.expandedIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeControlIcon", new Object[] { Boolean.valueOf(false) }), "Tree.collapsedIcon", new SwingLazyValue("javax.swing.plaf.metal.MetalIconFactory", "getTreeControlIcon", new Object[] { Boolean.valueOf(true) }), "Tree.line", colorUIResource14, "Tree.hash", colorUIResource14, "Tree.rowHeight", integer, "Tree.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ADD", "expand", "SUBTRACT", "collapse", "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "UP", "selectPrevious", "KP_UP", "selectPrevious", "shift UP", "selectPreviousExtendSelection", "shift KP_UP", "selectPreviousExtendSelection", "ctrl shift UP", "selectPreviousExtendSelection", "ctrl shift KP_UP", "selectPreviousExtendSelection", "ctrl UP", "selectPreviousChangeLead", "ctrl KP_UP", "selectPreviousChangeLead", "DOWN", "selectNext", "KP_DOWN", "selectNext", "shift DOWN", "selectNextExtendSelection", "shift KP_DOWN", "selectNextExtendSelection", "ctrl shift DOWN", "selectNextExtendSelection", "ctrl shift KP_DOWN", "selectNextExtendSelection", "ctrl DOWN", "selectNextChangeLead", "ctrl KP_DOWN", "selectNextChangeLead", "RIGHT", "selectChild", "KP_RIGHT", "selectChild", "LEFT", "selectParent", "KP_LEFT", "selectParent", "PAGE_UP", "scrollUpChangeSelection", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollUpExtendSelection", "ctrl PAGE_UP", "scrollUpChangeLead", "PAGE_DOWN", "scrollDownChangeSelection", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl PAGE_DOWN", "scrollDownChangeLead", "HOME", "selectFirst", "shift HOME", "selectFirstExtendSelection", "ctrl shift HOME", "selectFirstExtendSelection", "ctrl HOME", "selectFirstChangeLead", "END", "selectLast", "shift END", "selectLastExtendSelection", "ctrl shift END", "selectLastExtendSelection", "ctrl END", "selectLastChangeLead", "F2", "startEditing", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "ctrl LEFT", "scrollLeft", "ctrl KP_LEFT", "scrollLeft", "ctrl RIGHT", "scrollRight", "ctrl KP_RIGHT", "scrollRight", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo" }), "Tree.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "cancel" }), "ToolBar.border", swingLazyValue10, "ToolBar.background", colorUIResource10, "ToolBar.foreground", getMenuForeground(), "ToolBar.font", fontActiveValue1, "ToolBar.dockingBackground", colorUIResource10, "ToolBar.floatingBackground", colorUIResource10, "ToolBar.dockingForeground", colorUIResource15, "ToolBar.floatingForeground", colorUIResource14, "ToolBar.rolloverBorder", paramUIDefaults -> MetalBorders.getToolBarRolloverBorder(), "ToolBar.nonrolloverBorder", paramUIDefaults -> MetalBorders.getToolBarNonrolloverBorder(), "ToolBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "RootPane.frameBorder", paramUIDefaults -> new MetalBorders.FrameBorder(), "RootPane.plainDialogBorder", lazyValue1, "RootPane.informationDialogBorder", lazyValue1, "RootPane.errorDialogBorder", paramUIDefaults -> new MetalBorders.ErrorDialogBorder(), "RootPane.colorChooserDialogBorder", lazyValue2, "RootPane.fileChooserDialogBorder", lazyValue2, "RootPane.questionDialogBorder", lazyValue2, "RootPane.warningDialogBorder", paramUIDefaults -> new MetalBorders.WarningDialogBorder(), "RootPane.defaultButtonWindowKeyBindings", { "ENTER", "press", "released ENTER", "release", "ctrl ENTER", "press", "ctrl released ENTER", "release" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1526 */     paramUIDefaults.putDefaults(arrayOfObject3);
/*      */     
/* 1528 */     if (isWindows() && useSystemFonts() && metalTheme.isSystemTheme()) {
/* 1529 */       MetalFontDesktopProperty metalFontDesktopProperty = new MetalFontDesktopProperty("win.messagebox.font.height", 0);
/*      */ 
/*      */       
/* 1532 */       arrayOfObject3 = new Object[] { "OptionPane.messageFont", metalFontDesktopProperty, "OptionPane.buttonFont", metalFontDesktopProperty };
/*      */ 
/*      */ 
/*      */       
/* 1536 */       paramUIDefaults.putDefaults(arrayOfObject3);
/*      */     } 
/*      */     
/* 1539 */     flushUnreferenced();
/*      */     
/* 1541 */     boolean bool = SwingUtilities2.isLocalDisplay();
/* 1542 */     SwingUtilities2.AATextInfo aATextInfo = SwingUtilities2.AATextInfo.getAATextInfo(bool);
/* 1543 */     paramUIDefaults.put(SwingUtilities2.AA_TEXT_PROPERTY_KEY, aATextInfo);
/* 1544 */     new AATextListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createDefaultTheme() {
/* 1554 */     getCurrentTheme();
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
/*      */   public UIDefaults getDefaults() {
/* 1574 */     METAL_LOOK_AND_FEEL_INITED = true;
/*      */     
/* 1576 */     createDefaultTheme();
/* 1577 */     UIDefaults uIDefaults = super.getDefaults();
/* 1578 */     MetalTheme metalTheme = getCurrentTheme();
/* 1579 */     metalTheme.addCustomEntriesToTable(uIDefaults);
/* 1580 */     metalTheme.install();
/* 1581 */     return uIDefaults;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void provideErrorFeedback(Component paramComponent) {
/* 1590 */     super.provideErrorFeedback(paramComponent);
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
/*      */   public static void setCurrentTheme(MetalTheme paramMetalTheme) {
/* 1619 */     if (paramMetalTheme == null) {
/* 1620 */       throw new NullPointerException("Can't have null theme");
/*      */     }
/* 1622 */     AppContext.getAppContext().put("currentMetalTheme", paramMetalTheme);
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
/*      */   public static MetalTheme getCurrentTheme() {
/* 1635 */     AppContext appContext = AppContext.getAppContext();
/* 1636 */     MetalTheme metalTheme = (MetalTheme)appContext.get("currentMetalTheme");
/* 1637 */     if (metalTheme == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1645 */       if (useHighContrastTheme()) {
/* 1646 */         metalTheme = new MetalHighContrastTheme();
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1651 */         String str = AccessController.<String>doPrivileged(new GetPropertyAction("swing.metalTheme"));
/*      */         
/* 1653 */         if ("steel".equals(str)) {
/* 1654 */           metalTheme = new DefaultMetalTheme();
/*      */         } else {
/*      */           
/* 1657 */           metalTheme = new OceanTheme();
/*      */         } 
/*      */       } 
/* 1660 */       setCurrentTheme(metalTheme);
/*      */     } 
/* 1662 */     return metalTheme;
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
/*      */   public Icon getDisabledIcon(JComponent paramJComponent, Icon paramIcon) {
/* 1684 */     if (paramIcon instanceof ImageIcon && usingOcean()) {
/* 1685 */       return MetalUtils.getOceanDisabledButtonIcon(((ImageIcon)paramIcon)
/* 1686 */           .getImage());
/*      */     }
/* 1688 */     return super.getDisabledIcon(paramJComponent, paramIcon);
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
/*      */   public Icon getDisabledSelectedIcon(JComponent paramJComponent, Icon paramIcon) {
/* 1712 */     if (paramIcon instanceof ImageIcon && usingOcean()) {
/* 1713 */       return MetalUtils.getOceanDisabledButtonIcon(((ImageIcon)paramIcon)
/* 1714 */           .getImage());
/*      */     }
/* 1716 */     return super.getDisabledSelectedIcon(paramJComponent, paramIcon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontUIResource getControlTextFont() {
/* 1727 */     return getCurrentTheme().getControlTextFont();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontUIResource getSystemTextFont() {
/* 1737 */     return getCurrentTheme().getSystemTextFont();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontUIResource getUserTextFont() {
/* 1747 */     return getCurrentTheme().getUserTextFont();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontUIResource getMenuTextFont() {
/* 1757 */     return getCurrentTheme().getMenuTextFont();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontUIResource getWindowTitleFont() {
/* 1767 */     return getCurrentTheme().getWindowTitleFont();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontUIResource getSubTextFont() {
/* 1777 */     return getCurrentTheme().getSubTextFont();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getDesktopColor() {
/* 1787 */     return getCurrentTheme().getDesktopColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getFocusColor() {
/* 1797 */     return getCurrentTheme().getFocusColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getWhite() {
/* 1807 */     return getCurrentTheme().getWhite();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getBlack() {
/* 1817 */     return getCurrentTheme().getBlack();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getControl() {
/* 1827 */     return getCurrentTheme().getControl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getControlShadow() {
/* 1837 */     return getCurrentTheme().getControlShadow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getControlDarkShadow() {
/* 1847 */     return getCurrentTheme().getControlDarkShadow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getControlInfo() {
/* 1857 */     return getCurrentTheme().getControlInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getControlHighlight() {
/* 1867 */     return getCurrentTheme().getControlHighlight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getControlDisabled() {
/* 1877 */     return getCurrentTheme().getControlDisabled();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getPrimaryControl() {
/* 1887 */     return getCurrentTheme().getPrimaryControl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getPrimaryControlShadow() {
/* 1897 */     return getCurrentTheme().getPrimaryControlShadow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getPrimaryControlDarkShadow() {
/* 1908 */     return getCurrentTheme().getPrimaryControlDarkShadow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getPrimaryControlInfo() {
/* 1918 */     return getCurrentTheme().getPrimaryControlInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getPrimaryControlHighlight() {
/* 1929 */     return getCurrentTheme().getPrimaryControlHighlight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getSystemTextColor() {
/* 1939 */     return getCurrentTheme().getSystemTextColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getControlTextColor() {
/* 1949 */     return getCurrentTheme().getControlTextColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getInactiveControlTextColor() {
/* 1960 */     return getCurrentTheme().getInactiveControlTextColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getInactiveSystemTextColor() {
/* 1971 */     return getCurrentTheme().getInactiveSystemTextColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getUserTextColor() {
/* 1981 */     return getCurrentTheme().getUserTextColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getTextHighlightColor() {
/* 1991 */     return getCurrentTheme().getTextHighlightColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getHighlightedTextColor() {
/* 2001 */     return getCurrentTheme().getHighlightedTextColor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getWindowBackground() {
/* 2011 */     return getCurrentTheme().getWindowBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getWindowTitleBackground() {
/* 2022 */     return getCurrentTheme().getWindowTitleBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getWindowTitleForeground() {
/* 2033 */     return getCurrentTheme().getWindowTitleForeground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getWindowTitleInactiveBackground() {
/* 2044 */     return getCurrentTheme().getWindowTitleInactiveBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getWindowTitleInactiveForeground() {
/* 2055 */     return getCurrentTheme().getWindowTitleInactiveForeground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getMenuBackground() {
/* 2065 */     return getCurrentTheme().getMenuBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getMenuForeground() {
/* 2075 */     return getCurrentTheme().getMenuForeground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getMenuSelectedBackground() {
/* 2086 */     return getCurrentTheme().getMenuSelectedBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getMenuSelectedForeground() {
/* 2097 */     return getCurrentTheme().getMenuSelectedForeground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getMenuDisabledForeground() {
/* 2108 */     return getCurrentTheme().getMenuDisabledForeground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getSeparatorBackground() {
/* 2118 */     return getCurrentTheme().getSeparatorBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getSeparatorForeground() {
/* 2128 */     return getCurrentTheme().getSeparatorForeground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getAcceleratorForeground() {
/* 2138 */     return getCurrentTheme().getAcceleratorForeground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorUIResource getAcceleratorSelectedForeground() {
/* 2149 */     return getCurrentTheme().getAcceleratorSelectedForeground();
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
/*      */   public LayoutStyle getLayoutStyle() {
/* 2162 */     return MetalLayoutStyle.INSTANCE;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class FontActiveValue
/*      */     implements UIDefaults.ActiveValue
/*      */   {
/*      */     private int type;
/*      */     
/*      */     private MetalTheme theme;
/*      */     
/*      */     FontActiveValue(MetalTheme param1MetalTheme, int param1Int) {
/* 2174 */       this.theme = param1MetalTheme;
/* 2175 */       this.type = param1Int;
/*      */     }
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/* 2179 */       FontUIResource fontUIResource = null;
/* 2180 */       switch (this.type) {
/*      */         case 0:
/* 2182 */           fontUIResource = this.theme.getControlTextFont();
/*      */           break;
/*      */         case 1:
/* 2185 */           fontUIResource = this.theme.getSystemTextFont();
/*      */           break;
/*      */         case 2:
/* 2188 */           fontUIResource = this.theme.getUserTextFont();
/*      */           break;
/*      */         case 3:
/* 2191 */           fontUIResource = this.theme.getMenuTextFont();
/*      */           break;
/*      */         case 4:
/* 2194 */           fontUIResource = this.theme.getWindowTitleFont();
/*      */           break;
/*      */         case 5:
/* 2197 */           fontUIResource = this.theme.getSubTextFont();
/*      */           break;
/*      */       } 
/* 2200 */       return fontUIResource;
/*      */     }
/*      */   }
/*      */   
/* 2204 */   static ReferenceQueue<LookAndFeel> queue = new ReferenceQueue<>();
/*      */   
/*      */   static void flushUnreferenced() {
/*      */     AATextListener aATextListener;
/* 2208 */     while ((aATextListener = (AATextListener)queue.poll()) != null)
/* 2209 */       aATextListener.dispose(); 
/*      */   }
/*      */   
/*      */   static class AATextListener
/*      */     extends WeakReference<LookAndFeel>
/*      */     implements PropertyChangeListener
/*      */   {
/* 2216 */     private String key = "awt.font.desktophints"; private static boolean updatePending;
/*      */     
/*      */     AATextListener(LookAndFeel param1LookAndFeel) {
/* 2219 */       super(param1LookAndFeel, MetalLookAndFeel.queue);
/* 2220 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 2221 */       toolkit.addPropertyChangeListener(this.key, this);
/*      */     }
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 2225 */       LookAndFeel lookAndFeel = get();
/* 2226 */       if (lookAndFeel == null || lookAndFeel != UIManager.getLookAndFeel()) {
/* 2227 */         dispose();
/*      */         return;
/*      */       } 
/* 2230 */       UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 2231 */       boolean bool = SwingUtilities2.isLocalDisplay();
/*      */       
/* 2233 */       SwingUtilities2.AATextInfo aATextInfo = SwingUtilities2.AATextInfo.getAATextInfo(bool);
/* 2234 */       uIDefaults.put(SwingUtilities2.AA_TEXT_PROPERTY_KEY, aATextInfo);
/* 2235 */       updateUI();
/*      */     }
/*      */     
/*      */     void dispose() {
/* 2239 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 2240 */       toolkit.removePropertyChangeListener(this.key, this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void updateWindowUI(Window param1Window) {
/* 2247 */       SwingUtilities.updateComponentTreeUI(param1Window);
/* 2248 */       Window[] arrayOfWindow = param1Window.getOwnedWindows();
/* 2249 */       for (Window window : arrayOfWindow) {
/* 2250 */         updateWindowUI(window);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void updateAllUIs() {
/* 2258 */       Frame[] arrayOfFrame = Frame.getFrames();
/* 2259 */       for (Frame frame : arrayOfFrame) {
/* 2260 */         updateWindowUI(frame);
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
/*      */     
/*      */     private static synchronized void setUpdatePending(boolean param1Boolean) {
/* 2273 */       updatePending = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static synchronized boolean isUpdatePending() {
/* 2280 */       return updatePending;
/*      */     }
/*      */     
/*      */     protected void updateUI() {
/* 2284 */       if (!isUpdatePending()) {
/* 2285 */         setUpdatePending(true);
/* 2286 */         Runnable runnable = new Runnable() {
/*      */             public void run() {
/* 2288 */               MetalLookAndFeel.AATextListener.updateAllUIs();
/* 2289 */               MetalLookAndFeel.AATextListener.setUpdatePending(false);
/*      */             }
/*      */           };
/* 2292 */         SwingUtilities.invokeLater(runnable);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class MetalLayoutStyle
/*      */     extends DefaultLayoutStyle
/*      */   {
/* 2300 */     private static MetalLayoutStyle INSTANCE = new MetalLayoutStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getPreferredGap(JComponent param1JComponent1, JComponent param1JComponent2, LayoutStyle.ComponentPlacement param1ComponentPlacement, int param1Int, Container param1Container) {
/* 2307 */       super.getPreferredGap(param1JComponent1, param1JComponent2, param1ComponentPlacement, param1Int, param1Container);
/*      */ 
/*      */       
/* 2310 */       byte b = 0;
/*      */       
/* 2312 */       switch (param1ComponentPlacement) {
/*      */         
/*      */         case INDENT:
/* 2315 */           if (param1Int == 3 || param1Int == 7) {
/*      */             
/* 2317 */             int i = getIndent(param1JComponent1, param1Int);
/* 2318 */             if (i > 0) {
/* 2319 */               return i;
/*      */             }
/* 2321 */             return 12;
/*      */           } 
/*      */         
/*      */         case RELATED:
/* 2325 */           if (param1JComponent1.getUIClassID() == "ToggleButtonUI" && param1JComponent2
/* 2326 */             .getUIClassID() == "ToggleButtonUI") {
/*      */             
/* 2328 */             ButtonModel buttonModel1 = ((JToggleButton)param1JComponent1).getModel();
/*      */             
/* 2330 */             ButtonModel buttonModel2 = ((JToggleButton)param1JComponent2).getModel();
/* 2331 */             if (buttonModel1 instanceof DefaultButtonModel && buttonModel2 instanceof DefaultButtonModel && ((DefaultButtonModel)buttonModel1)
/*      */               
/* 2333 */               .getGroup() == ((DefaultButtonModel)buttonModel2)
/* 2334 */               .getGroup() && ((DefaultButtonModel)buttonModel1)
/* 2335 */               .getGroup() != null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2344 */               return 2;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 2349 */             if (MetalLookAndFeel.usingOcean()) {
/* 2350 */               return 6;
/*      */             }
/* 2352 */             return 5;
/*      */           } 
/* 2354 */           b = 6;
/*      */           break;
/*      */         case UNRELATED:
/* 2357 */           b = 12;
/*      */           break;
/*      */       } 
/* 2360 */       if (isLabelAndNonlabel(param1JComponent1, param1JComponent2, param1Int))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2368 */         return getButtonGap(param1JComponent1, param1JComponent2, param1Int, b + 6);
/*      */       }
/*      */       
/* 2371 */       return getButtonGap(param1JComponent1, param1JComponent2, param1Int, b);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getContainerGap(JComponent param1JComponent, int param1Int, Container param1Container) {
/* 2377 */       super.getContainerGap(param1JComponent, param1Int, param1Container);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2394 */       return getButtonGap(param1JComponent, param1Int, 12 - 
/* 2395 */           getButtonAdjustment(param1JComponent, param1Int));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getButtonGap(JComponent param1JComponent1, JComponent param1JComponent2, int param1Int1, int param1Int2) {
/* 2401 */       param1Int2 = super.getButtonGap(param1JComponent1, param1JComponent2, param1Int1, param1Int2);
/* 2402 */       if (param1Int2 > 0) {
/* 2403 */         int i = getButtonAdjustment(param1JComponent1, param1Int1);
/* 2404 */         if (i == 0) {
/* 2405 */           i = getButtonAdjustment(param1JComponent2, 
/* 2406 */               flipDirection(param1Int1));
/*      */         }
/* 2408 */         param1Int2 -= i;
/*      */       } 
/* 2410 */       if (param1Int2 < 0) {
/* 2411 */         return 0;
/*      */       }
/* 2413 */       return param1Int2;
/*      */     }
/*      */     
/*      */     private int getButtonAdjustment(JComponent param1JComponent, int param1Int) {
/* 2417 */       String str = param1JComponent.getUIClassID();
/* 2418 */       if (str == "ButtonUI" || str == "ToggleButtonUI") {
/* 2419 */         if (!MetalLookAndFeel.usingOcean() && (param1Int == 3 || param1Int == 5))
/*      */         {
/* 2421 */           if (param1JComponent.getBorder() instanceof javax.swing.plaf.UIResource) {
/* 2422 */             return 1;
/*      */           }
/*      */         }
/*      */       }
/* 2426 */       else if (param1Int == 5 && (
/* 2427 */         str == "RadioButtonUI" || str == "CheckBoxUI") && 
/* 2428 */         !MetalLookAndFeel.usingOcean()) {
/* 2429 */         return 1;
/*      */       } 
/*      */       
/* 2432 */       return 0;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalLookAndFeel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */