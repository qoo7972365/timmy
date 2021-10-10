/*      */ package com.sun.java.swing.plaf.windows;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.FilteredImageSource;
/*      */ import java.awt.image.RGBImageFilter;
/*      */ import java.security.AccessController;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.LayoutStyle;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.MenuSelectionManager;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.plaf.BorderUIResource;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.basic.BasicLookAndFeel;
/*      */ import sun.awt.OSInfo;
/*      */ import sun.awt.shell.ShellFolder;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.swing.DefaultLayoutStyle;
/*      */ import sun.swing.ImageIconUIResource;
/*      */ import sun.swing.StringUIClientPropertyKey;
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
/*      */ public class WindowsLookAndFeel
/*      */   extends BasicLookAndFeel
/*      */ {
/*  102 */   static final Object HI_RES_DISABLED_ICON_CLIENT_KEY = new StringUIClientPropertyKey("WindowsLookAndFeel.generateHiResDisabledIcon");
/*      */ 
/*      */   
/*      */   private boolean updatePending = false;
/*      */   
/*      */   private boolean useSystemFontSettings = true;
/*      */   
/*      */   private boolean useSystemFontSizeSettings;
/*      */   
/*      */   private DesktopProperty themeActive;
/*      */   
/*      */   private DesktopProperty dllName;
/*      */   
/*      */   private DesktopProperty colorName;
/*      */   
/*      */   private DesktopProperty sizeName;
/*      */   
/*      */   private DesktopProperty aaSettings;
/*      */   
/*      */   private transient LayoutStyle style;
/*      */   
/*      */   private int baseUnitX;
/*      */   
/*      */   private int baseUnitY;
/*      */ 
/*      */   
/*      */   public String getName() {
/*  129 */     return "Windows";
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  133 */     return "The Microsoft Windows Look and Feel";
/*      */   }
/*      */   
/*      */   public String getID() {
/*  137 */     return "Windows";
/*      */   }
/*      */   
/*      */   public boolean isNativeLookAndFeel() {
/*  141 */     return (OSInfo.getOSType() == OSInfo.OSType.WINDOWS);
/*      */   }
/*      */   
/*      */   public boolean isSupportedLookAndFeel() {
/*  145 */     return isNativeLookAndFeel();
/*      */   }
/*      */   
/*      */   public void initialize() {
/*  149 */     super.initialize();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  154 */     if (OSInfo.getWindowsVersion().compareTo(OSInfo.WINDOWS_95) <= 0) {
/*  155 */       isClassicWindows = true;
/*      */     } else {
/*  157 */       isClassicWindows = false;
/*  158 */       XPStyle.invalidateStyle();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  165 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("swing.useSystemFontSettings"));
/*      */     
/*  167 */     this
/*  168 */       .useSystemFontSettings = (str == null || Boolean.valueOf(str).booleanValue());
/*      */     
/*  170 */     if (this.useSystemFontSettings) {
/*  171 */       Object object = UIManager.get("Application.useSystemFontSettings");
/*      */       
/*  173 */       this
/*  174 */         .useSystemFontSettings = (object == null || Boolean.TRUE.equals(object));
/*      */     } 
/*  176 */     KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  177 */       .addKeyEventPostProcessor(WindowsRootPaneUI.altProcessor);
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
/*      */   protected void initClassDefaults(UIDefaults paramUIDefaults) {
/*  192 */     super.initClassDefaults(paramUIDefaults);
/*      */ 
/*      */ 
/*      */     
/*  196 */     Object[] arrayOfObject = { "ButtonUI", "com.sun.java.swing.plaf.windows.WindowsButtonUI", "CheckBoxUI", "com.sun.java.swing.plaf.windows.WindowsCheckBoxUI", "CheckBoxMenuItemUI", "com.sun.java.swing.plaf.windows.WindowsCheckBoxMenuItemUI", "LabelUI", "com.sun.java.swing.plaf.windows.WindowsLabelUI", "RadioButtonUI", "com.sun.java.swing.plaf.windows.WindowsRadioButtonUI", "RadioButtonMenuItemUI", "com.sun.java.swing.plaf.windows.WindowsRadioButtonMenuItemUI", "ToggleButtonUI", "com.sun.java.swing.plaf.windows.WindowsToggleButtonUI", "ProgressBarUI", "com.sun.java.swing.plaf.windows.WindowsProgressBarUI", "SliderUI", "com.sun.java.swing.plaf.windows.WindowsSliderUI", "SeparatorUI", "com.sun.java.swing.plaf.windows.WindowsSeparatorUI", "SplitPaneUI", "com.sun.java.swing.plaf.windows.WindowsSplitPaneUI", "SpinnerUI", "com.sun.java.swing.plaf.windows.WindowsSpinnerUI", "TabbedPaneUI", "com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI", "TextAreaUI", "com.sun.java.swing.plaf.windows.WindowsTextAreaUI", "TextFieldUI", "com.sun.java.swing.plaf.windows.WindowsTextFieldUI", "PasswordFieldUI", "com.sun.java.swing.plaf.windows.WindowsPasswordFieldUI", "TextPaneUI", "com.sun.java.swing.plaf.windows.WindowsTextPaneUI", "EditorPaneUI", "com.sun.java.swing.plaf.windows.WindowsEditorPaneUI", "TreeUI", "com.sun.java.swing.plaf.windows.WindowsTreeUI", "ToolBarUI", "com.sun.java.swing.plaf.windows.WindowsToolBarUI", "ToolBarSeparatorUI", "com.sun.java.swing.plaf.windows.WindowsToolBarSeparatorUI", "ComboBoxUI", "com.sun.java.swing.plaf.windows.WindowsComboBoxUI", "TableHeaderUI", "com.sun.java.swing.plaf.windows.WindowsTableHeaderUI", "InternalFrameUI", "com.sun.java.swing.plaf.windows.WindowsInternalFrameUI", "DesktopPaneUI", "com.sun.java.swing.plaf.windows.WindowsDesktopPaneUI", "DesktopIconUI", "com.sun.java.swing.plaf.windows.WindowsDesktopIconUI", "FileChooserUI", "com.sun.java.swing.plaf.windows.WindowsFileChooserUI", "MenuUI", "com.sun.java.swing.plaf.windows.WindowsMenuUI", "MenuItemUI", "com.sun.java.swing.plaf.windows.WindowsMenuItemUI", "MenuBarUI", "com.sun.java.swing.plaf.windows.WindowsMenuBarUI", "PopupMenuUI", "com.sun.java.swing.plaf.windows.WindowsPopupMenuUI", "PopupMenuSeparatorUI", "com.sun.java.swing.plaf.windows.WindowsPopupMenuSeparatorUI", "ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI", "RootPaneUI", "com.sun.java.swing.plaf.windows.WindowsRootPaneUI" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  233 */     paramUIDefaults.putDefaults(arrayOfObject);
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
/*      */   protected void initSystemColorDefaults(UIDefaults paramUIDefaults) {
/*  246 */     String[] arrayOfString = { "desktop", "#005C5C", "activeCaption", "#000080", "activeCaptionText", "#FFFFFF", "activeCaptionBorder", "#C0C0C0", "inactiveCaption", "#808080", "inactiveCaptionText", "#C0C0C0", "inactiveCaptionBorder", "#C0C0C0", "window", "#FFFFFF", "windowBorder", "#000000", "windowText", "#000000", "menu", "#C0C0C0", "menuPressedItemB", "#000080", "menuPressedItemF", "#FFFFFF", "menuText", "#000000", "text", "#C0C0C0", "textText", "#000000", "textHighlight", "#000080", "textHighlightText", "#FFFFFF", "textInactiveText", "#808080", "control", "#C0C0C0", "controlText", "#000000", "controlHighlight", "#C0C0C0", "controlLtHighlight", "#FFFFFF", "controlShadow", "#808080", "controlDkShadow", "#000000", "scrollbar", "#E0E0E0", "info", "#FFFFE1", "infoText", "#000000" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  279 */     loadSystemColors(paramUIDefaults, arrayOfString, isNativeLookAndFeel());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initResourceBundle(UIDefaults paramUIDefaults) {
/*  287 */     paramUIDefaults.addResourceBundle("com.sun.java.swing.plaf.windows.resources.windows");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initComponentDefaults(UIDefaults paramUIDefaults) {
/*      */     Object object1, object2, object3, object4, object5, object6, object7;
/*  294 */     super.initComponentDefaults(paramUIDefaults);
/*      */     
/*  296 */     initResourceBundle(paramUIDefaults);
/*      */ 
/*      */     
/*  299 */     Integer integer1 = Integer.valueOf(12);
/*  300 */     Integer integer2 = Integer.valueOf(0);
/*  301 */     Integer integer3 = Integer.valueOf(1);
/*      */     
/*  303 */     SwingLazyValue swingLazyValue1 = new SwingLazyValue("javax.swing.plaf.FontUIResource", null, new Object[] { "Dialog", integer2, integer1 });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  308 */     SwingLazyValue swingLazyValue2 = new SwingLazyValue("javax.swing.plaf.FontUIResource", null, new Object[] { "SansSerif", integer2, integer1 });
/*      */ 
/*      */ 
/*      */     
/*  312 */     SwingLazyValue swingLazyValue3 = new SwingLazyValue("javax.swing.plaf.FontUIResource", null, new Object[] { "Monospaced", integer2, integer1 });
/*      */ 
/*      */ 
/*      */     
/*  316 */     SwingLazyValue swingLazyValue4 = new SwingLazyValue("javax.swing.plaf.FontUIResource", null, new Object[] { "Dialog", integer3, integer1 });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  323 */     ColorUIResource colorUIResource1 = new ColorUIResource(Color.red);
/*  324 */     ColorUIResource colorUIResource2 = new ColorUIResource(Color.black);
/*  325 */     ColorUIResource colorUIResource3 = new ColorUIResource(Color.white);
/*  326 */     ColorUIResource colorUIResource4 = new ColorUIResource(Color.gray);
/*  327 */     ColorUIResource colorUIResource5 = new ColorUIResource(Color.darkGray);
/*  328 */     ColorUIResource colorUIResource6 = colorUIResource5;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  333 */     isClassicWindows = (OSInfo.getWindowsVersion().compareTo(OSInfo.WINDOWS_95) <= 0);
/*      */ 
/*      */     
/*  336 */     Icon icon1 = WindowsTreeUI.ExpandedIcon.createExpandedIcon();
/*      */     
/*  338 */     Icon icon2 = WindowsTreeUI.CollapsedIcon.createCollapsedIcon();
/*      */ 
/*      */ 
/*      */     
/*  342 */     UIDefaults.LazyInputMap lazyInputMap1 = new UIDefaults.LazyInputMap(new Object[] { "control C", "copy-to-clipboard", "control V", "paste-from-clipboard", "control X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "control A", "select-all", "control BACK_SLASH", "unselect", "shift LEFT", "selection-backward", "shift RIGHT", "selection-forward", "control LEFT", "caret-previous-word", "control RIGHT", "caret-next-word", "control shift LEFT", "selection-previous-word", "control shift RIGHT", "selection-next-word", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  378 */     UIDefaults.LazyInputMap lazyInputMap2 = new UIDefaults.LazyInputMap(new Object[] { "control C", "copy-to-clipboard", "control V", "paste-from-clipboard", "control X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "control A", "select-all", "control BACK_SLASH", "unselect", "shift LEFT", "selection-backward", "shift RIGHT", "selection-forward", "control LEFT", "caret-begin-line", "control RIGHT", "caret-end-line", "control shift LEFT", "selection-begin-line", "control shift RIGHT", "selection-end-line", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  412 */     UIDefaults.LazyInputMap lazyInputMap3 = new UIDefaults.LazyInputMap(new Object[] { "control C", "copy-to-clipboard", "control V", "paste-from-clipboard", "control X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift RIGHT", "selection-forward", "control LEFT", "caret-previous-word", "control RIGHT", "caret-next-word", "control shift LEFT", "selection-previous-word", "control shift RIGHT", "selection-next-word", "control A", "select-all", "control BACK_SLASH", "unselect", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "control HOME", "caret-begin", "control END", "caret-end", "control shift HOME", "selection-begin", "control shift END", "selection-end", "UP", "caret-up", "DOWN", "caret-down", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "PAGE_UP", "page-up", "PAGE_DOWN", "page-down", "shift PAGE_UP", "selection-page-up", "shift PAGE_DOWN", "selection-page-down", "ctrl shift PAGE_UP", "selection-page-left", "ctrl shift PAGE_DOWN", "selection-page-right", "shift UP", "selection-up", "shift DOWN", "selection-down", "ENTER", "insert-break", "TAB", "insert-tab", "control T", "next-link-action", "control shift T", "previous-link-action", "control SPACE", "activate-link-action", "control shift O", "toggle-componentOrientation" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  466 */     String str = "+";
/*      */ 
/*      */ 
/*      */     
/*  470 */     DesktopProperty desktopProperty1 = new DesktopProperty("win.3d.backgroundColor", paramUIDefaults.get("control"));
/*      */ 
/*      */     
/*  473 */     DesktopProperty desktopProperty2 = new DesktopProperty("win.3d.lightColor", paramUIDefaults.get("controlHighlight"));
/*      */ 
/*      */     
/*  476 */     DesktopProperty desktopProperty3 = new DesktopProperty("win.3d.highlightColor", paramUIDefaults.get("controlLtHighlight"));
/*      */ 
/*      */     
/*  479 */     DesktopProperty desktopProperty4 = new DesktopProperty("win.3d.shadowColor", paramUIDefaults.get("controlShadow"));
/*      */ 
/*      */     
/*  482 */     DesktopProperty desktopProperty5 = new DesktopProperty("win.3d.darkShadowColor", paramUIDefaults.get("controlDkShadow"));
/*      */ 
/*      */     
/*  485 */     DesktopProperty desktopProperty6 = new DesktopProperty("win.button.textColor", paramUIDefaults.get("controlText"));
/*      */ 
/*      */     
/*  488 */     DesktopProperty desktopProperty7 = new DesktopProperty("win.menu.backgroundColor", paramUIDefaults.get("menu"));
/*      */ 
/*      */     
/*  491 */     DesktopProperty desktopProperty8 = new DesktopProperty("win.menubar.backgroundColor", paramUIDefaults.get("menu"));
/*      */ 
/*      */     
/*  494 */     DesktopProperty desktopProperty9 = new DesktopProperty("win.menu.textColor", paramUIDefaults.get("menuText"));
/*      */ 
/*      */     
/*  497 */     DesktopProperty desktopProperty10 = new DesktopProperty("win.item.highlightColor", paramUIDefaults.get("textHighlight"));
/*      */ 
/*      */     
/*  500 */     DesktopProperty desktopProperty11 = new DesktopProperty("win.item.highlightTextColor", paramUIDefaults.get("textHighlightText"));
/*      */ 
/*      */     
/*  503 */     DesktopProperty desktopProperty12 = new DesktopProperty("win.frame.backgroundColor", paramUIDefaults.get("window"));
/*      */ 
/*      */     
/*  506 */     DesktopProperty desktopProperty13 = new DesktopProperty("win.frame.textColor", paramUIDefaults.get("windowText"));
/*      */ 
/*      */     
/*  509 */     DesktopProperty desktopProperty14 = new DesktopProperty("win.frame.sizingBorderWidth", Integer.valueOf(1));
/*      */ 
/*      */     
/*  512 */     DesktopProperty desktopProperty15 = new DesktopProperty("win.frame.captionHeight", Integer.valueOf(18));
/*      */ 
/*      */     
/*  515 */     DesktopProperty desktopProperty16 = new DesktopProperty("win.frame.captionButtonWidth", Integer.valueOf(16));
/*      */ 
/*      */     
/*  518 */     DesktopProperty desktopProperty17 = new DesktopProperty("win.frame.captionButtonHeight", Integer.valueOf(16));
/*      */ 
/*      */     
/*  521 */     DesktopProperty desktopProperty18 = new DesktopProperty("win.text.grayedTextColor", paramUIDefaults.get("textInactiveText"));
/*      */ 
/*      */     
/*  524 */     DesktopProperty desktopProperty19 = new DesktopProperty("win.scrollbar.backgroundColor", paramUIDefaults.get("scrollbar"));
/*  525 */     FocusColorProperty focusColorProperty = new FocusColorProperty();
/*      */     
/*  527 */     XPColorValue xPColorValue = new XPColorValue(TMSchema.Part.EP_EDIT, null, TMSchema.Prop.FILLCOLOR, desktopProperty12);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  537 */     DesktopProperty desktopProperty20 = desktopProperty1;
/*  538 */     DesktopProperty desktopProperty21 = desktopProperty1;
/*      */     
/*  540 */     SwingLazyValue swingLazyValue5 = swingLazyValue1;
/*  541 */     SwingLazyValue swingLazyValue6 = swingLazyValue3;
/*  542 */     SwingLazyValue swingLazyValue7 = swingLazyValue1;
/*  543 */     SwingLazyValue swingLazyValue8 = swingLazyValue1;
/*  544 */     SwingLazyValue swingLazyValue9 = swingLazyValue4;
/*  545 */     SwingLazyValue swingLazyValue10 = swingLazyValue2;
/*  546 */     SwingLazyValue swingLazyValue11 = swingLazyValue7;
/*      */     
/*  548 */     DesktopProperty desktopProperty22 = new DesktopProperty("win.scrollbar.width", Integer.valueOf(16));
/*      */     
/*  550 */     DesktopProperty desktopProperty23 = new DesktopProperty("win.menu.height", null);
/*      */     
/*  552 */     DesktopProperty desktopProperty24 = new DesktopProperty("win.item.hotTrackingOn", Boolean.valueOf(true));
/*      */     
/*  554 */     DesktopProperty desktopProperty25 = new DesktopProperty("win.menu.keyboardCuesOn", Boolean.TRUE);
/*      */     
/*  556 */     if (this.useSystemFontSettings) {
/*  557 */       object1 = getDesktopFontValue("win.menu.font", swingLazyValue5);
/*  558 */       object2 = getDesktopFontValue("win.ansiFixed.font", swingLazyValue6);
/*  559 */       object3 = getDesktopFontValue("win.defaultGUI.font", swingLazyValue7);
/*  560 */       object4 = getDesktopFontValue("win.messagebox.font", swingLazyValue8);
/*  561 */       object5 = getDesktopFontValue("win.frame.captionFont", swingLazyValue9);
/*  562 */       object7 = getDesktopFontValue("win.icon.font", swingLazyValue11);
/*  563 */       object6 = getDesktopFontValue("win.tooltip.font", swingLazyValue10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  571 */       SwingUtilities2.AATextInfo aATextInfo = SwingUtilities2.AATextInfo.getAATextInfo(true);
/*  572 */       paramUIDefaults.put(SwingUtilities2.AA_TEXT_PROPERTY_KEY, aATextInfo);
/*  573 */       this.aaSettings = new FontDesktopProperty("awt.font.desktophints");
/*      */     } 
/*      */     
/*  576 */     if (this.useSystemFontSizeSettings) {
/*  577 */       object1 = new WindowsFontSizeProperty("win.menu.font.height", "Dialog", 0, 12);
/*  578 */       object2 = new WindowsFontSizeProperty("win.ansiFixed.font.height", "Monospaced", 0, 12);
/*      */       
/*  580 */       object3 = new WindowsFontSizeProperty("win.defaultGUI.font.height", "Dialog", 0, 12);
/*  581 */       object4 = new WindowsFontSizeProperty("win.messagebox.font.height", "Dialog", 0, 12);
/*  582 */       object5 = new WindowsFontSizeProperty("win.frame.captionFont.height", "Dialog", 1, 12);
/*  583 */       object6 = new WindowsFontSizeProperty("win.tooltip.font.height", "SansSerif", 0, 12);
/*  584 */       object7 = new WindowsFontSizeProperty("win.icon.font.height", "Dialog", 0, 12);
/*      */     } 
/*      */ 
/*      */     
/*  588 */     if (!(this instanceof WindowsClassicLookAndFeel) && 
/*  589 */       OSInfo.getOSType() == OSInfo.OSType.WINDOWS && 
/*  590 */       OSInfo.getWindowsVersion().compareTo(OSInfo.WINDOWS_XP) >= 0 && 
/*  591 */       AccessController.doPrivileged(new GetPropertyAction("swing.noxp")) == null) {
/*      */ 
/*      */ 
/*      */       
/*  595 */       this.themeActive = new TriggerDesktopProperty("win.xpstyle.themeActive");
/*  596 */       this.dllName = new TriggerDesktopProperty("win.xpstyle.dllName");
/*  597 */       this.colorName = new TriggerDesktopProperty("win.xpstyle.colorName");
/*  598 */       this.sizeName = new TriggerDesktopProperty("win.xpstyle.sizeName");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1495 */     Object[] arrayOfObject = { "AuditoryCues.playList", null, "Application.useSystemFontSettings", Boolean.valueOf(this.useSystemFontSettings), "TextField.focusInputMap", lazyInputMap1, "PasswordField.focusInputMap", lazyInputMap2, "TextArea.focusInputMap", lazyInputMap3, "TextPane.focusInputMap", lazyInputMap3, "EditorPane.focusInputMap", lazyInputMap3, "Button.font", object3, "Button.background", desktopProperty1, "Button.foreground", desktopProperty6, "Button.shadow", desktopProperty4, "Button.darkShadow", desktopProperty5, "Button.light", desktopProperty2, "Button.highlight", desktopProperty3, "Button.disabledForeground", desktopProperty18, "Button.disabledShadow", desktopProperty3, "Button.focus", focusColorProperty, "Button.dashedRectGapX", new XPValue(Integer.valueOf(3), Integer.valueOf(5)), "Button.dashedRectGapY", new XPValue(Integer.valueOf(3), Integer.valueOf(4)), "Button.dashedRectGapWidth", new XPValue(Integer.valueOf(6), Integer.valueOf(10)), "Button.dashedRectGapHeight", new XPValue(Integer.valueOf(6), Integer.valueOf(8)), "Button.textShiftOffset", new XPValue(Integer.valueOf(0), Integer.valueOf(1)), "Button.showMnemonics", desktopProperty25, "Button.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "Caret.width", new DesktopProperty("win.caret.width", null), "CheckBox.font", object3, "CheckBox.interiorBackground", desktopProperty12, "CheckBox.background", desktopProperty1, "CheckBox.foreground", desktopProperty13, "CheckBox.shadow", desktopProperty4, "CheckBox.darkShadow", desktopProperty5, "CheckBox.light", desktopProperty2, "CheckBox.highlight", desktopProperty3, "CheckBox.focus", focusColorProperty, "CheckBox.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "CheckBox.totalInsets", new Insets(4, 4, 4, 4), "CheckBoxMenuItem.font", object1, "CheckBoxMenuItem.background", desktopProperty7, "CheckBoxMenuItem.foreground", desktopProperty9, "CheckBoxMenuItem.selectionForeground", desktopProperty11, "CheckBoxMenuItem.selectionBackground", desktopProperty10, "CheckBoxMenuItem.acceleratorForeground", desktopProperty9, "CheckBoxMenuItem.acceleratorSelectionForeground", desktopProperty11, "CheckBoxMenuItem.commandSound", "win.sound.menuCommand", "ComboBox.font", object3, "ComboBox.background", desktopProperty12, "ComboBox.foreground", desktopProperty13, "ComboBox.buttonBackground", desktopProperty1, "ComboBox.buttonShadow", desktopProperty4, "ComboBox.buttonDarkShadow", desktopProperty5, "ComboBox.buttonHighlight", desktopProperty3, "ComboBox.selectionBackground", desktopProperty10, "ComboBox.selectionForeground", desktopProperty11, "ComboBox.editorBorder", new XPValue(new EmptyBorder(1, 2, 1, 1), new EmptyBorder(1, 4, 1, 4)), "ComboBox.disabledBackground", new XPColorValue(TMSchema.Part.CP_COMBOBOX, TMSchema.State.DISABLED, TMSchema.Prop.FILLCOLOR, desktopProperty21), "ComboBox.disabledForeground", new XPColorValue(TMSchema.Part.CP_COMBOBOX, TMSchema.State.DISABLED, TMSchema.Prop.TEXTCOLOR, desktopProperty18), "ComboBox.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "hidePopup", "PAGE_UP", "pageUpPassThrough", "PAGE_DOWN", "pageDownPassThrough", "HOME", "homePassThrough", "END", "endPassThrough", "DOWN", "selectNext2", "KP_DOWN", "selectNext2", "UP", "selectPrevious2", "KP_UP", "selectPrevious2", "ENTER", "enterPressed", "F4", "togglePopup", "alt DOWN", "togglePopup", "alt KP_DOWN", "togglePopup", "alt UP", "togglePopup", "alt KP_UP", "togglePopup" }), "Desktop.background", new DesktopProperty("win.desktop.backgroundColor", paramUIDefaults.get("desktop")), "Desktop.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl F5", "restore", "ctrl F4", "close", "ctrl F7", "move", "ctrl F8", "resize", "RIGHT", "right", "KP_RIGHT", "right", "LEFT", "left", "KP_LEFT", "left", "UP", "up", "KP_UP", "up", "DOWN", "down", "KP_DOWN", "down", "ESCAPE", "escape", "ctrl F9", "minimize", "ctrl F10", "maximize", "ctrl F6", "selectNextFrame", "ctrl TAB", "selectNextFrame", "ctrl alt F6", "selectNextFrame", "shift ctrl alt F6", "selectPreviousFrame", "ctrl F12", "navigateNext", "shift ctrl F12", "navigatePrevious" }), "DesktopIcon.width", Integer.valueOf(160), "EditorPane.font", object3, "EditorPane.background", desktopProperty12, "EditorPane.foreground", desktopProperty13, "EditorPane.selectionBackground", desktopProperty10, "EditorPane.selectionForeground", desktopProperty11, "EditorPane.caretForeground", desktopProperty13, "EditorPane.inactiveForeground", desktopProperty18, "EditorPane.inactiveBackground", desktopProperty12, "EditorPane.disabledBackground", desktopProperty21, "FileChooser.homeFolderIcon", new LazyWindowsIcon(null, "icons/HomeFolder.gif"), "FileChooser.listFont", object7, "FileChooser.listViewBackground", new XPColorValue(TMSchema.Part.LVP_LISTVIEW, null, TMSchema.Prop.FILLCOLOR, desktopProperty12), "FileChooser.listViewBorder", new XPBorderValue(TMSchema.Part.LVP_LISTVIEW, new SwingLazyValue("javax.swing.plaf.BorderUIResource", "getLoweredBevelBorderUIResource")), "FileChooser.listViewIcon", new LazyWindowsIcon("fileChooserIcon ListView", "icons/ListView.gif"), "FileChooser.listViewWindowsStyle", Boolean.TRUE, "FileChooser.detailsViewIcon", new LazyWindowsIcon("fileChooserIcon DetailsView", "icons/DetailsView.gif"), "FileChooser.viewMenuIcon", new LazyWindowsIcon("fileChooserIcon ViewMenu", "icons/ListView.gif"), "FileChooser.upFolderIcon", new LazyWindowsIcon("fileChooserIcon UpFolder", "icons/UpFolder.gif"), "FileChooser.newFolderIcon", new LazyWindowsIcon("fileChooserIcon NewFolder", "icons/NewFolder.gif"), "FileChooser.useSystemExtensionHiding", Boolean.TRUE, "FileChooser.usesSingleFilePane", Boolean.TRUE, "FileChooser.noPlacesBar", new DesktopProperty("win.comdlg.noPlacesBar", Boolean.FALSE), "FileChooser.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "cancelSelection", "F2", "editFileName", "F5", "refresh", "BACK_SPACE", "Go Up" }), "FileView.directoryIcon", SwingUtilities2.makeIcon(getClass(), WindowsLookAndFeel.class, "icons/Directory.gif"), "FileView.fileIcon", SwingUtilities2.makeIcon(getClass(), WindowsLookAndFeel.class, "icons/File.gif"), "FileView.computerIcon", SwingUtilities2.makeIcon(getClass(), WindowsLookAndFeel.class, "icons/Computer.gif"), "FileView.hardDriveIcon", SwingUtilities2.makeIcon(getClass(), WindowsLookAndFeel.class, "icons/HardDrive.gif"), "FileView.floppyDriveIcon", SwingUtilities2.makeIcon(getClass(), WindowsLookAndFeel.class, "icons/FloppyDrive.gif"), "FormattedTextField.font", object3, "InternalFrame.titleFont", object5, "InternalFrame.titlePaneHeight", desktopProperty15, "InternalFrame.titleButtonWidth", desktopProperty16, "InternalFrame.titleButtonHeight", desktopProperty17, "InternalFrame.titleButtonToolTipsOn", desktopProperty24, "InternalFrame.borderColor", desktopProperty1, "InternalFrame.borderShadow", desktopProperty4, "InternalFrame.borderDarkShadow", desktopProperty5, "InternalFrame.borderHighlight", desktopProperty3, "InternalFrame.borderLight", desktopProperty2, "InternalFrame.borderWidth", desktopProperty14, "InternalFrame.minimizeIconBackground", desktopProperty1, "InternalFrame.resizeIconHighlight", desktopProperty2, "InternalFrame.resizeIconShadow", desktopProperty4, "InternalFrame.activeBorderColor", new DesktopProperty("win.frame.activeBorderColor", paramUIDefaults.get("windowBorder")), "InternalFrame.inactiveBorderColor", new DesktopProperty("win.frame.inactiveBorderColor", paramUIDefaults.get("windowBorder")), "InternalFrame.activeTitleBackground", new DesktopProperty("win.frame.activeCaptionColor", paramUIDefaults.get("activeCaption")), "InternalFrame.activeTitleGradient", new DesktopProperty("win.frame.activeCaptionGradientColor", paramUIDefaults.get("activeCaption")), "InternalFrame.activeTitleForeground", new DesktopProperty("win.frame.captionTextColor", paramUIDefaults.get("activeCaptionText")), "InternalFrame.inactiveTitleBackground", new DesktopProperty("win.frame.inactiveCaptionColor", paramUIDefaults.get("inactiveCaption")), "InternalFrame.inactiveTitleGradient", new DesktopProperty("win.frame.inactiveCaptionGradientColor", paramUIDefaults.get("inactiveCaption")), "InternalFrame.inactiveTitleForeground", new DesktopProperty("win.frame.inactiveCaptionTextColor", paramUIDefaults.get("inactiveCaptionText")), "InternalFrame.maximizeIcon", WindowsIconFactory.createFrameMaximizeIcon(), "InternalFrame.minimizeIcon", WindowsIconFactory.createFrameMinimizeIcon(), "InternalFrame.iconifyIcon", WindowsIconFactory.createFrameIconifyIcon(), "InternalFrame.closeIcon", WindowsIconFactory.createFrameCloseIcon(), "InternalFrame.icon", new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane$ScalableIconUIResource", (Object[])new Object[][] { { SwingUtilities2.makeIcon(getClass(), BasicLookAndFeel.class, "icons/JavaCup16.png"), SwingUtilities2.makeIcon(getClass(), WindowsLookAndFeel.class, "icons/JavaCup32.png") } }), "InternalFrame.closeSound", "win.sound.close", "InternalFrame.maximizeSound", "win.sound.maximize", "InternalFrame.minimizeSound", "win.sound.minimize", "InternalFrame.restoreDownSound", "win.sound.restoreDown", "InternalFrame.restoreUpSound", "win.sound.restoreUp", "InternalFrame.windowBindings", { "shift ESCAPE", "showSystemMenu", "ctrl SPACE", "showSystemMenu", "ESCAPE", "hideSystemMenu" }, "Label.font", object3, "Label.background", desktopProperty1, "Label.foreground", desktopProperty13, "Label.disabledForeground", desktopProperty18, "Label.disabledShadow", desktopProperty3, "List.font", object3, "List.background", desktopProperty12, "List.foreground", desktopProperty13, "List.selectionBackground", desktopProperty10, "List.selectionForeground", desktopProperty11, "List.lockToPositionOnScroll", Boolean.TRUE, "List.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "UP", "selectPreviousRow", "KP_UP", "selectPreviousRow", "shift UP", "selectPreviousRowExtendSelection", "shift KP_UP", "selectPreviousRowExtendSelection", "ctrl shift UP", "selectPreviousRowExtendSelection", "ctrl shift KP_UP", "selectPreviousRowExtendSelection", "ctrl UP", "selectPreviousRowChangeLead", "ctrl KP_UP", "selectPreviousRowChangeLead", "DOWN", "selectNextRow", "KP_DOWN", "selectNextRow", "shift DOWN", "selectNextRowExtendSelection", "shift KP_DOWN", "selectNextRowExtendSelection", "ctrl shift DOWN", "selectNextRowExtendSelection", "ctrl shift KP_DOWN", "selectNextRowExtendSelection", "ctrl DOWN", "selectNextRowChangeLead", "ctrl KP_DOWN", "selectNextRowChangeLead", "LEFT", "selectPreviousColumn", "KP_LEFT", "selectPreviousColumn", "shift LEFT", "selectPreviousColumnExtendSelection", "shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl shift LEFT", "selectPreviousColumnExtendSelection", "ctrl shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl LEFT", "selectPreviousColumnChangeLead", "ctrl KP_LEFT", "selectPreviousColumnChangeLead", "RIGHT", "selectNextColumn", "KP_RIGHT", "selectNextColumn", "shift RIGHT", "selectNextColumnExtendSelection", "shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl shift RIGHT", "selectNextColumnExtendSelection", "ctrl shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl RIGHT", "selectNextColumnChangeLead", "ctrl KP_RIGHT", "selectNextColumnChangeLead", "HOME", "selectFirstRow", "shift HOME", "selectFirstRowExtendSelection", "ctrl shift HOME", "selectFirstRowExtendSelection", "ctrl HOME", "selectFirstRowChangeLead", "END", "selectLastRow", "shift END", "selectLastRowExtendSelection", "ctrl shift END", "selectLastRowExtendSelection", "ctrl END", "selectLastRowChangeLead", "PAGE_UP", "scrollUp", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollUpExtendSelection", "ctrl PAGE_UP", "scrollUpChangeLead", "PAGE_DOWN", "scrollDown", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl PAGE_DOWN", "scrollDownChangeLead", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo" }), "PopupMenu.font", object1, "PopupMenu.background", desktopProperty7, "PopupMenu.foreground", desktopProperty9, "PopupMenu.popupSound", "win.sound.menuPopup", "PopupMenu.consumeEventOnClose", Boolean.TRUE, "Menu.font", object1, "Menu.foreground", desktopProperty9, "Menu.background", desktopProperty7, "Menu.useMenuBarBackgroundForTopLevel", Boolean.TRUE, "Menu.selectionForeground", desktopProperty11, "Menu.selectionBackground", desktopProperty10, "Menu.acceleratorForeground", desktopProperty9, "Menu.acceleratorSelectionForeground", desktopProperty11, "Menu.menuPopupOffsetX", Integer.valueOf(0), "Menu.menuPopupOffsetY", Integer.valueOf(0), "Menu.submenuPopupOffsetX", Integer.valueOf(-4), "Menu.submenuPopupOffsetY", Integer.valueOf(-3), "Menu.crossMenuMnemonic", Boolean.FALSE, "Menu.preserveTopLevelSelection", Boolean.TRUE, "MenuBar.font", object1, "MenuBar.background", new XPValue(desktopProperty8, desktopProperty7), "MenuBar.foreground", desktopProperty9, "MenuBar.shadow", desktopProperty4, "MenuBar.highlight", desktopProperty3, "MenuBar.height", desktopProperty23, "MenuBar.rolloverEnabled", desktopProperty24, "MenuBar.windowBindings", { "F10", "takeFocus" }, "MenuItem.font", object1, "MenuItem.acceleratorFont", object1, "MenuItem.foreground", desktopProperty9, "MenuItem.background", desktopProperty7, "MenuItem.selectionForeground", desktopProperty11, "MenuItem.selectionBackground", desktopProperty10, "MenuItem.disabledForeground", desktopProperty18, "MenuItem.acceleratorForeground", desktopProperty9, "MenuItem.acceleratorSelectionForeground", desktopProperty11, "MenuItem.acceleratorDelimiter", str, "MenuItem.commandSound", "win.sound.menuCommand", "MenuItem.disabledAreNavigable", Boolean.TRUE, "RadioButton.font", object3, "RadioButton.interiorBackground", desktopProperty12, "RadioButton.background", desktopProperty1, "RadioButton.foreground", desktopProperty13, "RadioButton.shadow", desktopProperty4, "RadioButton.darkShadow", desktopProperty5, "RadioButton.light", desktopProperty2, "RadioButton.highlight", desktopProperty3, "RadioButton.focus", focusColorProperty, "RadioButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "RadioButton.totalInsets", new Insets(4, 4, 4, 4), "RadioButtonMenuItem.font", object1, "RadioButtonMenuItem.foreground", desktopProperty9, "RadioButtonMenuItem.background", desktopProperty7, "RadioButtonMenuItem.selectionForeground", desktopProperty11, "RadioButtonMenuItem.selectionBackground", desktopProperty10, "RadioButtonMenuItem.disabledForeground", desktopProperty18, "RadioButtonMenuItem.acceleratorForeground", desktopProperty9, "RadioButtonMenuItem.acceleratorSelectionForeground", desktopProperty11, "RadioButtonMenuItem.commandSound", "win.sound.menuCommand", "OptionPane.font", object4, "OptionPane.messageFont", object4, "OptionPane.buttonFont", object4, "OptionPane.background", desktopProperty1, "OptionPane.foreground", desktopProperty13, "OptionPane.buttonMinimumWidth", new XPDLUValue(50, 50, 3), "OptionPane.messageForeground", desktopProperty6, "OptionPane.errorIcon", new LazyWindowsIcon("optionPaneIcon Error", "icons/Error.gif"), "OptionPane.informationIcon", new LazyWindowsIcon("optionPaneIcon Information", "icons/Inform.gif"), "OptionPane.questionIcon", new LazyWindowsIcon("optionPaneIcon Question", "icons/Question.gif"), "OptionPane.warningIcon", new LazyWindowsIcon("optionPaneIcon Warning", "icons/Warn.gif"), "OptionPane.windowBindings", { "ESCAPE", "close" }, "OptionPane.errorSound", "win.sound.hand", "OptionPane.informationSound", "win.sound.asterisk", "OptionPane.questionSound", "win.sound.question", "OptionPane.warningSound", "win.sound.exclamation", "FormattedTextField.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy-to-clipboard", "ctrl V", "paste-from-clipboard", "ctrl X", "cut-to-clipboard", "COPY", "copy-to-clipboard", "PASTE", "paste-from-clipboard", "CUT", "cut-to-clipboard", "control INSERT", "copy-to-clipboard", "shift INSERT", "paste-from-clipboard", "shift DELETE", "cut-to-clipboard", "shift LEFT", "selection-backward", "shift KP_LEFT", "selection-backward", "shift RIGHT", "selection-forward", "shift KP_RIGHT", "selection-forward", "ctrl LEFT", "caret-previous-word", "ctrl KP_LEFT", "caret-previous-word", "ctrl RIGHT", "caret-next-word", "ctrl KP_RIGHT", "caret-next-word", "ctrl shift LEFT", "selection-previous-word", "ctrl shift KP_LEFT", "selection-previous-word", "ctrl shift RIGHT", "selection-next-word", "ctrl shift KP_RIGHT", "selection-next-word", "ctrl A", "select-all", "HOME", "caret-begin-line", "END", "caret-end-line", "shift HOME", "selection-begin-line", "shift END", "selection-end-line", "BACK_SPACE", "delete-previous", "shift BACK_SPACE", "delete-previous", "ctrl H", "delete-previous", "DELETE", "delete-next", "ctrl DELETE", "delete-next-word", "ctrl BACK_SPACE", "delete-previous-word", "RIGHT", "caret-forward", "LEFT", "caret-backward", "KP_RIGHT", "caret-forward", "KP_LEFT", "caret-backward", "ENTER", "notify-field-accept", "ctrl BACK_SLASH", "unselect", "control shift O", "toggle-componentOrientation", "ESCAPE", "reset-field-edit", "UP", "increment", "KP_UP", "increment", "DOWN", "decrement", "KP_DOWN", "decrement" }), "FormattedTextField.inactiveBackground", desktopProperty20, "FormattedTextField.disabledBackground", desktopProperty21, "Panel.font", object3, "Panel.background", desktopProperty1, "Panel.foreground", desktopProperty13, "PasswordField.font", object3, "PasswordField.background", xPColorValue, "PasswordField.foreground", desktopProperty13, "PasswordField.inactiveForeground", desktopProperty18, "PasswordField.inactiveBackground", desktopProperty20, "PasswordField.disabledBackground", desktopProperty21, "PasswordField.selectionBackground", desktopProperty10, "PasswordField.selectionForeground", desktopProperty11, "PasswordField.caretForeground", desktopProperty13, "PasswordField.echoChar", new XPValue(new Character('●'), new Character('*')), "ProgressBar.font", object3, "ProgressBar.foreground", desktopProperty10, "ProgressBar.background", desktopProperty1, "ProgressBar.shadow", desktopProperty4, "ProgressBar.highlight", desktopProperty3, "ProgressBar.selectionForeground", desktopProperty1, "ProgressBar.selectionBackground", desktopProperty10, "ProgressBar.cellLength", Integer.valueOf(7), "ProgressBar.cellSpacing", Integer.valueOf(2), "ProgressBar.indeterminateInsets", new Insets(3, 3, 3, 3), "RootPane.defaultButtonWindowKeyBindings", { "ENTER", "press", "released ENTER", "release", "ctrl ENTER", "press", "ctrl released ENTER", "release" }, "ScrollBar.background", desktopProperty19, "ScrollBar.foreground", desktopProperty1, "ScrollBar.track", colorUIResource3, "ScrollBar.trackForeground", desktopProperty19, "ScrollBar.trackHighlight", colorUIResource2, "ScrollBar.trackHighlightForeground", colorUIResource6, "ScrollBar.thumb", desktopProperty1, "ScrollBar.thumbHighlight", desktopProperty3, "ScrollBar.thumbDarkShadow", desktopProperty5, "ScrollBar.thumbShadow", desktopProperty4, "ScrollBar.width", desktopProperty22, "ScrollBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "positiveUnitIncrement", "KP_RIGHT", "positiveUnitIncrement", "DOWN", "positiveUnitIncrement", "KP_DOWN", "positiveUnitIncrement", "PAGE_DOWN", "positiveBlockIncrement", "ctrl PAGE_DOWN", "positiveBlockIncrement", "LEFT", "negativeUnitIncrement", "KP_LEFT", "negativeUnitIncrement", "UP", "negativeUnitIncrement", "KP_UP", "negativeUnitIncrement", "PAGE_UP", "negativeBlockIncrement", "ctrl PAGE_UP", "negativeBlockIncrement", "HOME", "minScroll", "END", "maxScroll" }), "ScrollPane.font", object3, "ScrollPane.background", desktopProperty1, "ScrollPane.foreground", desktopProperty6, "ScrollPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "unitScrollRight", "KP_RIGHT", "unitScrollRight", "DOWN", "unitScrollDown", "KP_DOWN", "unitScrollDown", "LEFT", "unitScrollLeft", "KP_LEFT", "unitScrollLeft", "UP", "unitScrollUp", "KP_UP", "unitScrollUp", "PAGE_UP", "scrollUp", "PAGE_DOWN", "scrollDown", "ctrl PAGE_UP", "scrollLeft", "ctrl PAGE_DOWN", "scrollRight", "ctrl HOME", "scrollHome", "ctrl END", "scrollEnd" }), "Separator.background", desktopProperty3, "Separator.foreground", desktopProperty4, "Slider.font", object3, "Slider.foreground", desktopProperty1, "Slider.background", desktopProperty1, "Slider.highlight", desktopProperty3, "Slider.shadow", desktopProperty4, "Slider.focus", desktopProperty5, "Slider.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "positiveUnitIncrement", "KP_RIGHT", "positiveUnitIncrement", "DOWN", "negativeUnitIncrement", "KP_DOWN", "negativeUnitIncrement", "PAGE_DOWN", "negativeBlockIncrement", "LEFT", "negativeUnitIncrement", "KP_LEFT", "negativeUnitIncrement", "UP", "positiveUnitIncrement", "KP_UP", "positiveUnitIncrement", "PAGE_UP", "positiveBlockIncrement", "HOME", "minScroll", "END", "maxScroll" }), "Spinner.font", object3, "Spinner.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "increment", "KP_UP", "increment", "DOWN", "decrement", "KP_DOWN", "decrement" }), "SplitPane.background", desktopProperty1, "SplitPane.highlight", desktopProperty3, "SplitPane.shadow", desktopProperty4, "SplitPane.darkShadow", desktopProperty5, "SplitPane.dividerSize", Integer.valueOf(5), "SplitPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "negativeIncrement", "DOWN", "positiveIncrement", "LEFT", "negativeIncrement", "RIGHT", "positiveIncrement", "KP_UP", "negativeIncrement", "KP_DOWN", "positiveIncrement", "KP_LEFT", "negativeIncrement", "KP_RIGHT", "positiveIncrement", "HOME", "selectMin", "END", "selectMax", "F8", "startResize", "F6", "toggleFocus", "ctrl TAB", "focusOutForward", "ctrl shift TAB", "focusOutBackward" }), "TabbedPane.tabsOverlapBorder", new XPValue(Boolean.TRUE, Boolean.FALSE), "TabbedPane.tabInsets", new XPValue(new InsetsUIResource(1, 4, 1, 4), new InsetsUIResource(0, 4, 1, 4)), "TabbedPane.tabAreaInsets", new XPValue(new InsetsUIResource(3, 2, 2, 2), new InsetsUIResource(3, 2, 0, 2)), "TabbedPane.font", object3, "TabbedPane.background", desktopProperty1, "TabbedPane.foreground", desktopProperty6, "TabbedPane.highlight", desktopProperty3, "TabbedPane.light", desktopProperty2, "TabbedPane.shadow", desktopProperty4, "TabbedPane.darkShadow", desktopProperty5, "TabbedPane.focus", desktopProperty6, "TabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "TabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl TAB", "navigateNext", "ctrl shift TAB", "navigatePrevious", "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "Table.font", object3, "Table.foreground", desktopProperty6, "Table.background", desktopProperty12, "Table.highlight", desktopProperty3, "Table.light", desktopProperty2, "Table.shadow", desktopProperty4, "Table.darkShadow", desktopProperty5, "Table.selectionForeground", desktopProperty11, "Table.selectionBackground", desktopProperty10, "Table.gridColor", colorUIResource4, "Table.focusCellBackground", desktopProperty12, "Table.focusCellForeground", desktopProperty6, "Table.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "RIGHT", "selectNextColumn", "KP_RIGHT", "selectNextColumn", "shift RIGHT", "selectNextColumnExtendSelection", "shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl shift RIGHT", "selectNextColumnExtendSelection", "ctrl shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl RIGHT", "selectNextColumnChangeLead", "ctrl KP_RIGHT", "selectNextColumnChangeLead", "LEFT", "selectPreviousColumn", "KP_LEFT", "selectPreviousColumn", "shift LEFT", "selectPreviousColumnExtendSelection", "shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl shift LEFT", "selectPreviousColumnExtendSelection", "ctrl shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl LEFT", "selectPreviousColumnChangeLead", "ctrl KP_LEFT", "selectPreviousColumnChangeLead", "DOWN", "selectNextRow", "KP_DOWN", "selectNextRow", "shift DOWN", "selectNextRowExtendSelection", "shift KP_DOWN", "selectNextRowExtendSelection", "ctrl shift DOWN", "selectNextRowExtendSelection", "ctrl shift KP_DOWN", "selectNextRowExtendSelection", "ctrl DOWN", "selectNextRowChangeLead", "ctrl KP_DOWN", "selectNextRowChangeLead", "UP", "selectPreviousRow", "KP_UP", "selectPreviousRow", "shift UP", "selectPreviousRowExtendSelection", "shift KP_UP", "selectPreviousRowExtendSelection", "ctrl shift UP", "selectPreviousRowExtendSelection", "ctrl shift KP_UP", "selectPreviousRowExtendSelection", "ctrl UP", "selectPreviousRowChangeLead", "ctrl KP_UP", "selectPreviousRowChangeLead", "HOME", "selectFirstColumn", "shift HOME", "selectFirstColumnExtendSelection", "ctrl shift HOME", "selectFirstRowExtendSelection", "ctrl HOME", "selectFirstRow", "END", "selectLastColumn", "shift END", "selectLastColumnExtendSelection", "ctrl shift END", "selectLastRowExtendSelection", "ctrl END", "selectLastRow", "PAGE_UP", "scrollUpChangeSelection", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollLeftExtendSelection", "ctrl PAGE_UP", "scrollLeftChangeSelection", "PAGE_DOWN", "scrollDownChangeSelection", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollRightExtendSelection", "ctrl PAGE_DOWN", "scrollRightChangeSelection", "TAB", "selectNextColumnCell", "shift TAB", "selectPreviousColumnCell", "ENTER", "selectNextRowCell", "shift ENTER", "selectPreviousRowCell", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "ESCAPE", "cancel", "F2", "startEditing", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo", "F8", "focusHeader" }), "Table.sortIconHighlight", desktopProperty4, "Table.sortIconLight", colorUIResource3, "TableHeader.font", object3, "TableHeader.foreground", desktopProperty6, "TableHeader.background", desktopProperty1, "TableHeader.focusCellBackground", new XPValue(XPValue.NULL_VALUE, desktopProperty12), "TextArea.font", object2, "TextArea.background", desktopProperty12, "TextArea.foreground", desktopProperty13, "TextArea.inactiveForeground", desktopProperty18, "TextArea.inactiveBackground", desktopProperty12, "TextArea.disabledBackground", desktopProperty21, "TextArea.selectionBackground", desktopProperty10, "TextArea.selectionForeground", desktopProperty11, "TextArea.caretForeground", desktopProperty13, "TextField.font", object3, "TextField.background", xPColorValue, "TextField.foreground", desktopProperty13, "TextField.shadow", desktopProperty4, "TextField.darkShadow", desktopProperty5, "TextField.light", desktopProperty2, "TextField.highlight", desktopProperty3, "TextField.inactiveForeground", desktopProperty18, "TextField.inactiveBackground", desktopProperty20, "TextField.disabledBackground", desktopProperty21, "TextField.selectionBackground", desktopProperty10, "TextField.selectionForeground", desktopProperty11, "TextField.caretForeground", desktopProperty13, "TextPane.font", object3, "TextPane.background", desktopProperty12, "TextPane.foreground", desktopProperty13, "TextPane.selectionBackground", desktopProperty10, "TextPane.selectionForeground", desktopProperty11, "TextPane.inactiveBackground", desktopProperty12, "TextPane.disabledBackground", desktopProperty21, "TextPane.caretForeground", desktopProperty13, "TitledBorder.font", object3, "TitledBorder.titleColor", new XPColorValue(TMSchema.Part.BP_GROUPBOX, null, TMSchema.Prop.TEXTCOLOR, desktopProperty13), "ToggleButton.font", object3, "ToggleButton.background", desktopProperty1, "ToggleButton.foreground", desktopProperty6, "ToggleButton.shadow", desktopProperty4, "ToggleButton.darkShadow", desktopProperty5, "ToggleButton.light", desktopProperty2, "ToggleButton.highlight", desktopProperty3, "ToggleButton.focus", desktopProperty6, "ToggleButton.textShiftOffset", Integer.valueOf(1), "ToggleButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "ToolBar.font", object1, "ToolBar.background", desktopProperty1, "ToolBar.foreground", desktopProperty6, "ToolBar.shadow", desktopProperty4, "ToolBar.darkShadow", desktopProperty5, "ToolBar.light", desktopProperty2, "ToolBar.highlight", desktopProperty3, "ToolBar.dockingBackground", desktopProperty1, "ToolBar.dockingForeground", colorUIResource1, "ToolBar.floatingBackground", desktopProperty1, "ToolBar.floatingForeground", colorUIResource5, "ToolBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "ToolBar.separatorSize", null, "ToolTip.font", object6, "ToolTip.background", new DesktopProperty("win.tooltip.backgroundColor", paramUIDefaults.get("info")), "ToolTip.foreground", new DesktopProperty("win.tooltip.textColor", paramUIDefaults.get("infoText")), "ToolTipManager.enableToolTipMode", "activeApplication", "Tree.selectionBorderColor", colorUIResource2, "Tree.drawDashedFocusIndicator", Boolean.TRUE, "Tree.lineTypeDashed", Boolean.TRUE, "Tree.font", object3, "Tree.background", desktopProperty12, "Tree.foreground", desktopProperty13, "Tree.hash", colorUIResource4, "Tree.leftChildIndent", Integer.valueOf(8), "Tree.rightChildIndent", Integer.valueOf(11), "Tree.textForeground", desktopProperty13, "Tree.textBackground", desktopProperty12, "Tree.selectionForeground", desktopProperty11, "Tree.selectionBackground", desktopProperty10, "Tree.expandedIcon", icon1, "Tree.collapsedIcon", icon2, "Tree.openIcon", new ActiveWindowsIcon("win.icon.shellIconBPP", "shell32Icon 5", "icons/TreeOpen.gif"), "Tree.closedIcon", new ActiveWindowsIcon("win.icon.shellIconBPP", "shell32Icon 4", "icons/TreeClosed.gif"), "Tree.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ADD", "expand", "SUBTRACT", "collapse", "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "UP", "selectPrevious", "KP_UP", "selectPrevious", "shift UP", "selectPreviousExtendSelection", "shift KP_UP", "selectPreviousExtendSelection", "ctrl shift UP", "selectPreviousExtendSelection", "ctrl shift KP_UP", "selectPreviousExtendSelection", "ctrl UP", "selectPreviousChangeLead", "ctrl KP_UP", "selectPreviousChangeLead", "DOWN", "selectNext", "KP_DOWN", "selectNext", "shift DOWN", "selectNextExtendSelection", "shift KP_DOWN", "selectNextExtendSelection", "ctrl shift DOWN", "selectNextExtendSelection", "ctrl shift KP_DOWN", "selectNextExtendSelection", "ctrl DOWN", "selectNextChangeLead", "ctrl KP_DOWN", "selectNextChangeLead", "RIGHT", "selectChild", "KP_RIGHT", "selectChild", "LEFT", "selectParent", "KP_LEFT", "selectParent", "PAGE_UP", "scrollUpChangeSelection", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollUpExtendSelection", "ctrl PAGE_UP", "scrollUpChangeLead", "PAGE_DOWN", "scrollDownChangeSelection", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl PAGE_DOWN", "scrollDownChangeLead", "HOME", "selectFirst", "shift HOME", "selectFirstExtendSelection", "ctrl shift HOME", "selectFirstExtendSelection", "ctrl HOME", "selectFirstChangeLead", "END", "selectLast", "shift END", "selectLastExtendSelection", "ctrl shift END", "selectLastExtendSelection", "ctrl END", "selectLastChangeLead", "F2", "startEditing", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "ctrl LEFT", "scrollLeft", "ctrl KP_LEFT", "scrollLeft", "ctrl RIGHT", "scrollRight", "ctrl KP_RIGHT", "scrollRight", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo" }), "Tree.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ESCAPE", "cancel" }), "Viewport.font", object3, "Viewport.background", desktopProperty1, "Viewport.foreground", desktopProperty13 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1581 */     paramUIDefaults.putDefaults(arrayOfObject);
/* 1582 */     paramUIDefaults.putDefaults(getLazyValueDefaults());
/* 1583 */     initVistaComponentDefaults(paramUIDefaults);
/*      */   }
/*      */   
/*      */   static boolean isOnVista() {
/* 1587 */     return (OSInfo.getOSType() == OSInfo.OSType.WINDOWS && 
/* 1588 */       OSInfo.getWindowsVersion().compareTo(OSInfo.WINDOWS_VISTA) >= 0);
/*      */   }
/*      */   
/*      */   private void initVistaComponentDefaults(UIDefaults paramUIDefaults) {
/* 1592 */     if (!isOnVista()) {
/*      */       return;
/*      */     }
/*      */     
/* 1596 */     String[] arrayOfString = { "MenuItem", "Menu", "CheckBoxMenuItem", "RadioButtonMenuItem" };
/*      */ 
/*      */ 
/*      */     
/* 1600 */     Object[] arrayOfObject = new Object[arrayOfString.length * 2];
/*      */     
/*      */     byte b1, b2;
/* 1603 */     for (b1 = 0, b2 = 0; b1 < arrayOfString.length; b1++) {
/* 1604 */       String str1 = arrayOfString[b1] + ".opaque";
/* 1605 */       Object object = paramUIDefaults.get(str1);
/* 1606 */       arrayOfObject[b2++] = str1;
/* 1607 */       arrayOfObject[b2++] = new XPValue(Boolean.FALSE, object);
/*      */     } 
/*      */     
/* 1610 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1616 */     for (b1 = 0, b2 = 0; b1 < arrayOfString.length; b1++) {
/* 1617 */       String str1 = arrayOfString[b1] + ".acceleratorSelectionForeground";
/* 1618 */       Object object = paramUIDefaults.get(str1);
/* 1619 */       arrayOfObject[b2++] = str1;
/* 1620 */       arrayOfObject[b2++] = new XPValue(paramUIDefaults
/*      */           
/* 1622 */           .getColor(arrayOfString[b1] + ".acceleratorForeground"), object);
/*      */     } 
/*      */ 
/*      */     
/* 1626 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */ 
/*      */     
/* 1630 */     WindowsIconFactory.VistaMenuItemCheckIconFactory vistaMenuItemCheckIconFactory = WindowsIconFactory.getMenuItemCheckIconFactory(); byte b3;
/* 1631 */     for (b2 = 0, b3 = 0; b2 < arrayOfString.length; b2++) {
/* 1632 */       String str1 = arrayOfString[b2] + ".checkIconFactory";
/* 1633 */       Object object = paramUIDefaults.get(str1);
/* 1634 */       arrayOfObject[b3++] = str1;
/* 1635 */       arrayOfObject[b3++] = new XPValue(vistaMenuItemCheckIconFactory, object);
/*      */     } 
/*      */     
/* 1638 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */     
/* 1640 */     for (b2 = 0, b3 = 0; b2 < arrayOfString.length; b2++) {
/* 1641 */       String str1 = arrayOfString[b2] + ".checkIcon";
/* 1642 */       Object object = paramUIDefaults.get(str1);
/* 1643 */       arrayOfObject[b3++] = str1;
/* 1644 */       arrayOfObject[b3++] = new XPValue(vistaMenuItemCheckIconFactory
/* 1645 */           .getIcon(arrayOfString[b2]), object);
/*      */     } 
/*      */     
/* 1648 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */ 
/*      */     
/* 1652 */     for (b2 = 0, b3 = 0; b2 < arrayOfString.length; b2++) {
/* 1653 */       String str1 = arrayOfString[b2] + ".evenHeight";
/* 1654 */       Object object = paramUIDefaults.get(str1);
/* 1655 */       arrayOfObject[b3++] = str1;
/* 1656 */       arrayOfObject[b3++] = new XPValue(Boolean.TRUE, object);
/*      */     } 
/* 1658 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */     
/* 1661 */     InsetsUIResource insetsUIResource = new InsetsUIResource(0, 0, 0, 0); byte b4;
/* 1662 */     for (b3 = 0, b4 = 0; b3 < arrayOfString.length; b3++) {
/* 1663 */       String str1 = arrayOfString[b3] + ".margin";
/* 1664 */       Object object = paramUIDefaults.get(str1);
/* 1665 */       arrayOfObject[b4++] = str1;
/* 1666 */       arrayOfObject[b4++] = new XPValue(insetsUIResource, object);
/*      */     } 
/* 1668 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */ 
/*      */     
/* 1672 */     Integer integer1 = Integer.valueOf(0); byte b5;
/* 1673 */     for (b4 = 0, b5 = 0; b4 < arrayOfString.length; b4++) {
/* 1674 */       String str1 = arrayOfString[b4] + ".checkIconOffset";
/* 1675 */       Object object = paramUIDefaults.get(str1);
/* 1676 */       arrayOfObject[b5++] = str1;
/* 1677 */       arrayOfObject[b5++] = new XPValue(integer1, object);
/*      */     } 
/*      */     
/* 1680 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */     
/* 1683 */     Integer integer2 = Integer.valueOf(WindowsPopupMenuUI.getSpanBeforeGutter() + 
/* 1684 */         WindowsPopupMenuUI.getGutterWidth() + 
/* 1685 */         WindowsPopupMenuUI.getSpanAfterGutter()); byte b6;
/* 1686 */     for (b5 = 0, b6 = 0; b5 < arrayOfString.length; b5++) {
/* 1687 */       String str1 = arrayOfString[b5] + ".afterCheckIconGap";
/* 1688 */       Object object = paramUIDefaults.get(str1);
/* 1689 */       arrayOfObject[b6++] = str1;
/* 1690 */       arrayOfObject[b6++] = new XPValue(integer2, object);
/*      */     } 
/*      */     
/* 1693 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */     
/* 1696 */     UIDefaults.ActiveValue activeValue = new UIDefaults.ActiveValue() {
/*      */         public Object createValue(UIDefaults param1UIDefaults) {
/* 1698 */           return Integer.valueOf(WindowsIconFactory.VistaMenuItemCheckIconFactory.getIconWidth() + 
/* 1699 */               WindowsPopupMenuUI.getSpanBeforeGutter() + 
/* 1700 */               WindowsPopupMenuUI.getGutterWidth() + 
/* 1701 */               WindowsPopupMenuUI.getSpanAfterGutter()); }
/*      */       };
/*      */     byte b7;
/* 1704 */     for (b6 = 0, b7 = 0; b6 < arrayOfString.length; b6++) {
/* 1705 */       String str1 = arrayOfString[b6] + ".minimumTextOffset";
/* 1706 */       Object object = paramUIDefaults.get(str1);
/* 1707 */       arrayOfObject[b7++] = str1;
/* 1708 */       arrayOfObject[b7++] = new XPValue(activeValue, object);
/*      */     } 
/* 1710 */     paramUIDefaults.putDefaults(arrayOfObject);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1715 */     String str = "PopupMenu.border";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1721 */     XPBorderValue xPBorderValue = new XPBorderValue(TMSchema.Part.MENU, new SwingLazyValue("javax.swing.plaf.basic.BasicBorders", "getInternalFrameBorder"), BorderFactory.createEmptyBorder(2, 2, 2, 2));
/* 1722 */     paramUIDefaults.put(str, xPBorderValue);
/*      */ 
/*      */ 
/*      */     
/* 1726 */     paramUIDefaults.put("Table.ascendingSortIcon", new XPValue(new SkinIcon(TMSchema.Part.HP_HEADERSORTARROW, TMSchema.State.SORTEDDOWN), new SwingLazyValue("sun.swing.plaf.windows.ClassicSortArrowIcon", null, new Object[] { Boolean.TRUE })));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1731 */     paramUIDefaults.put("Table.descendingSortIcon", new XPValue(new SkinIcon(TMSchema.Part.HP_HEADERSORTARROW, TMSchema.State.SORTEDUP), new SwingLazyValue("sun.swing.plaf.windows.ClassicSortArrowIcon", null, new Object[] { Boolean.FALSE })));
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
/*      */   private Object getDesktopFontValue(String paramString, Object paramObject) {
/* 1746 */     if (this.useSystemFontSettings) {
/* 1747 */       return new WindowsFontProperty(paramString, paramObject);
/*      */     }
/* 1749 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object[] getLazyValueDefaults() {
/* 1758 */     XPBorderValue xPBorderValue1 = new XPBorderValue(TMSchema.Part.BP_PUSHBUTTON, new SwingLazyValue("javax.swing.plaf.basic.BasicBorders", "getButtonBorder"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1764 */     XPBorderValue xPBorderValue2 = new XPBorderValue(TMSchema.Part.EP_EDIT, new SwingLazyValue("javax.swing.plaf.basic.BasicBorders", "getTextFieldBorder"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1770 */     XPValue xPValue1 = new XPValue(new InsetsUIResource(2, 2, 2, 2), new InsetsUIResource(1, 1, 1, 1));
/*      */ 
/*      */ 
/*      */     
/* 1774 */     XPBorderValue xPBorderValue3 = new XPBorderValue(TMSchema.Part.EP_EDIT, xPBorderValue2, new EmptyBorder(2, 2, 2, 2));
/*      */ 
/*      */ 
/*      */     
/* 1778 */     XPValue xPValue2 = new XPValue(new InsetsUIResource(1, 1, 1, 1), null);
/*      */ 
/*      */ 
/*      */     
/* 1782 */     XPBorderValue xPBorderValue4 = new XPBorderValue(TMSchema.Part.CP_COMBOBOX, xPBorderValue2);
/*      */ 
/*      */     
/* 1785 */     SwingLazyValue swingLazyValue1 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsBorders", "getFocusCellHighlightBorder");
/*      */ 
/*      */ 
/*      */     
/* 1789 */     SwingLazyValue swingLazyValue2 = new SwingLazyValue("javax.swing.plaf.BorderUIResource", "getEtchedBorderUIResource");
/*      */ 
/*      */ 
/*      */     
/* 1793 */     SwingLazyValue swingLazyValue3 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsBorders", "getInternalFrameBorder");
/*      */ 
/*      */ 
/*      */     
/* 1797 */     SwingLazyValue swingLazyValue4 = new SwingLazyValue("javax.swing.plaf.BorderUIResource", "getLoweredBevelBorderUIResource");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1802 */     SwingLazyValue swingLazyValue5 = new SwingLazyValue("javax.swing.plaf.basic.BasicBorders$MarginBorder");
/*      */ 
/*      */     
/* 1805 */     SwingLazyValue swingLazyValue6 = new SwingLazyValue("javax.swing.plaf.basic.BasicBorders", "getMenuBarBorder");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1810 */     XPBorderValue xPBorderValue5 = new XPBorderValue(TMSchema.Part.MENU, new SwingLazyValue("javax.swing.plaf.basic.BasicBorders", "getInternalFrameBorder"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1816 */     SwingLazyValue swingLazyValue7 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsBorders", "getProgressBarBorder");
/*      */ 
/*      */ 
/*      */     
/* 1820 */     SwingLazyValue swingLazyValue8 = new SwingLazyValue("javax.swing.plaf.basic.BasicBorders", "getRadioButtonBorder");
/*      */ 
/*      */ 
/*      */     
/* 1824 */     XPBorderValue xPBorderValue6 = new XPBorderValue(TMSchema.Part.LBP_LISTBOX, xPBorderValue2);
/*      */ 
/*      */     
/* 1827 */     XPBorderValue xPBorderValue7 = new XPBorderValue(TMSchema.Part.LBP_LISTBOX, swingLazyValue4);
/*      */ 
/*      */     
/* 1830 */     SwingLazyValue swingLazyValue9 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsBorders", "getTableHeaderBorder");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1835 */     SwingLazyValue swingLazyValue10 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsBorders", "getToolBarBorder");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1840 */     SwingLazyValue swingLazyValue11 = new SwingLazyValue("javax.swing.plaf.BorderUIResource", "getBlackLineBorderUIResource");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1846 */     SwingLazyValue swingLazyValue12 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsIconFactory", "getCheckBoxIcon");
/*      */ 
/*      */ 
/*      */     
/* 1850 */     SwingLazyValue swingLazyValue13 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsIconFactory", "getRadioButtonIcon");
/*      */ 
/*      */ 
/*      */     
/* 1854 */     SwingLazyValue swingLazyValue14 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsIconFactory", "getRadioButtonMenuItemIcon");
/*      */ 
/*      */ 
/*      */     
/* 1858 */     SwingLazyValue swingLazyValue15 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsIconFactory", "getMenuItemCheckIcon");
/*      */ 
/*      */ 
/*      */     
/* 1862 */     SwingLazyValue swingLazyValue16 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsIconFactory", "getMenuItemArrowIcon");
/*      */ 
/*      */ 
/*      */     
/* 1866 */     SwingLazyValue swingLazyValue17 = new SwingLazyValue("com.sun.java.swing.plaf.windows.WindowsIconFactory", "getMenuArrowIcon");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1871 */     return new Object[] { "Button.border", xPBorderValue1, "CheckBox.border", swingLazyValue8, "ComboBox.border", xPBorderValue4, "DesktopIcon.border", swingLazyValue3, "FormattedTextField.border", xPBorderValue2, "FormattedTextField.margin", xPValue1, "InternalFrame.border", swingLazyValue3, "List.focusCellHighlightBorder", swingLazyValue1, "Table.focusCellHighlightBorder", swingLazyValue1, "Menu.border", swingLazyValue5, "MenuBar.border", swingLazyValue6, "MenuItem.border", swingLazyValue5, "PasswordField.border", xPBorderValue2, "PasswordField.margin", xPValue1, "PopupMenu.border", xPBorderValue5, "ProgressBar.border", swingLazyValue7, "RadioButton.border", swingLazyValue8, "ScrollPane.border", xPBorderValue6, "Spinner.border", xPBorderValue3, "Spinner.arrowButtonInsets", xPValue2, "Spinner.arrowButtonSize", new Dimension(17, 9), "Table.scrollPaneBorder", xPBorderValue7, "TableHeader.cellBorder", swingLazyValue9, "TextArea.margin", xPValue1, "TextField.border", xPBorderValue2, "TextField.margin", xPValue1, "TitledBorder.border", new XPBorderValue(TMSchema.Part.BP_GROUPBOX, swingLazyValue2), "ToggleButton.border", swingLazyValue8, "ToolBar.border", swingLazyValue10, "ToolTip.border", swingLazyValue11, "CheckBox.icon", swingLazyValue12, "Menu.arrowIcon", swingLazyValue17, "MenuItem.checkIcon", swingLazyValue15, "MenuItem.arrowIcon", swingLazyValue16, "RadioButton.icon", swingLazyValue13, "RadioButtonMenuItem.checkIcon", swingLazyValue14, "InternalFrame.layoutTitlePaneAtOrigin", new XPValue(Boolean.TRUE, Boolean.FALSE), "Table.ascendingSortIcon", new XPValue(new SwingLazyValue("sun.swing.icon.SortArrowIcon", null, new Object[] { Boolean.TRUE, "Table.sortIconColor" }), new SwingLazyValue("sun.swing.plaf.windows.ClassicSortArrowIcon", null, new Object[] { Boolean.TRUE })), "Table.descendingSortIcon", new XPValue(new SwingLazyValue("sun.swing.icon.SortArrowIcon", null, new Object[] { Boolean.FALSE, "Table.sortIconColor" }), new SwingLazyValue("sun.swing.plaf.windows.ClassicSortArrowIcon", null, new Object[] { Boolean.FALSE })) };
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
/*      */   public void uninitialize() {
/* 1934 */     super.uninitialize();
/*      */     
/* 1936 */     if (WindowsPopupMenuUI.mnemonicListener != null) {
/* 1937 */       MenuSelectionManager.defaultManager()
/* 1938 */         .removeChangeListener(WindowsPopupMenuUI.mnemonicListener);
/*      */     }
/* 1940 */     KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 1941 */       .removeKeyEventPostProcessor(WindowsRootPaneUI.altProcessor);
/* 1942 */     DesktopProperty.flushUnreferencedProperties();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isMnemonicHidden = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isClassicWindows = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setMnemonicHidden(boolean paramBoolean) {
/* 1963 */     if (UIManager.getBoolean("Button.showMnemonics") == true) {
/*      */       
/* 1965 */       isMnemonicHidden = false;
/*      */     } else {
/* 1967 */       isMnemonicHidden = paramBoolean;
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
/*      */   public static boolean isMnemonicHidden() {
/* 1980 */     if (UIManager.getBoolean("Button.showMnemonics") == true)
/*      */     {
/* 1982 */       isMnemonicHidden = false;
/*      */     }
/* 1984 */     return isMnemonicHidden;
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
/*      */   public static boolean isClassicWindows() {
/* 1998 */     return isClassicWindows;
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
/*      */   public void provideErrorFeedback(Component paramComponent) {
/* 2023 */     super.provideErrorFeedback(paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutStyle getLayoutStyle() {
/* 2030 */     LayoutStyle layoutStyle = this.style;
/* 2031 */     if (layoutStyle == null) {
/* 2032 */       layoutStyle = new WindowsLayoutStyle();
/* 2033 */       this.style = layoutStyle;
/*      */     } 
/* 2035 */     return layoutStyle;
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
/*      */   protected Action createAudioAction(Object paramObject) {
/* 2060 */     if (paramObject != null) {
/* 2061 */       String str1 = (String)paramObject;
/* 2062 */       String str2 = (String)UIManager.get(paramObject);
/* 2063 */       return new AudioAction(str1, str2);
/*      */     } 
/* 2065 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   static void repaintRootPane(Component paramComponent) {
/* 2070 */     JRootPane jRootPane = null;
/* 2071 */     for (; paramComponent != null; paramComponent = paramComponent.getParent()) {
/* 2072 */       if (paramComponent instanceof JRootPane) {
/* 2073 */         jRootPane = (JRootPane)paramComponent;
/*      */       }
/*      */     } 
/*      */     
/* 2077 */     if (jRootPane != null) {
/* 2078 */       jRootPane.repaint();
/*      */     } else {
/* 2080 */       paramComponent.repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class AudioAction
/*      */     extends AbstractAction
/*      */   {
/*      */     private Runnable audioRunnable;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String audioResource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AudioAction(String param1String1, String param1String2) {
/* 2103 */       super(param1String1);
/* 2104 */       this.audioResource = param1String2;
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2107 */       if (this.audioRunnable == null) {
/* 2108 */         this.audioRunnable = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty(this.audioResource);
/*      */       }
/* 2110 */       if (this.audioRunnable != null)
/*      */       {
/*      */         
/* 2113 */         (new Thread(this.audioRunnable)).start();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LazyWindowsIcon
/*      */     implements UIDefaults.LazyValue
/*      */   {
/*      */     private String nativeImage;
/*      */     
/*      */     private String resource;
/*      */     
/*      */     LazyWindowsIcon(String param1String1, String param1String2) {
/* 2127 */       this.nativeImage = param1String1;
/* 2128 */       this.resource = param1String2;
/*      */     }
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/* 2132 */       if (this.nativeImage != null) {
/* 2133 */         Image image = (Image)ShellFolder.get(this.nativeImage);
/* 2134 */         if (image != null) {
/* 2135 */           return new ImageIcon(image);
/*      */         }
/*      */       } 
/* 2138 */       return SwingUtilities2.makeIcon(getClass(), WindowsLookAndFeel.class, this.resource);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class ActiveWindowsIcon
/*      */     implements UIDefaults.ActiveValue
/*      */   {
/*      */     private Icon icon;
/*      */     
/*      */     private String nativeImageName;
/*      */     
/*      */     private String fallbackName;
/*      */     
/*      */     private DesktopProperty desktopProperty;
/*      */ 
/*      */     
/*      */     ActiveWindowsIcon(String param1String1, String param1String2, String param1String3) {
/* 2157 */       this.nativeImageName = param1String2;
/* 2158 */       this.fallbackName = param1String3;
/*      */       
/* 2160 */       if (OSInfo.getOSType() == OSInfo.OSType.WINDOWS && 
/* 2161 */         OSInfo.getWindowsVersion().compareTo(OSInfo.WINDOWS_XP) < 0)
/*      */       {
/*      */         
/* 2164 */         this.desktopProperty = new WindowsLookAndFeel.TriggerDesktopProperty(param1String1) {
/*      */             protected void updateUI() {
/* 2166 */               WindowsLookAndFeel.ActiveWindowsIcon.this.icon = null;
/* 2167 */               super.updateUI();
/*      */             }
/*      */           };
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/* 2175 */       if (this.icon == null) {
/* 2176 */         Image image = (Image)ShellFolder.get(this.nativeImageName);
/* 2177 */         if (image != null) {
/* 2178 */           this.icon = new ImageIconUIResource(image);
/*      */         }
/*      */       } 
/* 2181 */       if (this.icon == null && this.fallbackName != null) {
/*      */         
/* 2183 */         UIDefaults.LazyValue lazyValue = (UIDefaults.LazyValue)SwingUtilities2.makeIcon(WindowsLookAndFeel.class, BasicLookAndFeel.class, this.fallbackName);
/*      */         
/* 2185 */         this.icon = (Icon)lazyValue.createValue(param1UIDefaults);
/*      */       } 
/* 2187 */       return this.icon;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SkinIcon
/*      */     implements Icon, UIResource
/*      */   {
/*      */     private final TMSchema.Part part;
/*      */     private final TMSchema.State state;
/*      */     
/*      */     SkinIcon(TMSchema.Part param1Part, TMSchema.State param1State) {
/* 2198 */       this.part = param1Part;
/* 2199 */       this.state = param1State;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2208 */       XPStyle xPStyle = XPStyle.getXP();
/* 2209 */       assert xPStyle != null;
/* 2210 */       if (xPStyle != null) {
/* 2211 */         XPStyle.Skin skin = xPStyle.getSkin(null, this.part);
/* 2212 */         skin.paintSkin(param1Graphics, param1Int1, param1Int2, this.state);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIconWidth() {
/* 2222 */       int i = 0;
/* 2223 */       XPStyle xPStyle = XPStyle.getXP();
/* 2224 */       assert xPStyle != null;
/* 2225 */       if (xPStyle != null) {
/* 2226 */         XPStyle.Skin skin = xPStyle.getSkin(null, this.part);
/* 2227 */         i = skin.getWidth();
/*      */       } 
/* 2229 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIconHeight() {
/* 2238 */       int i = 0;
/* 2239 */       XPStyle xPStyle = XPStyle.getXP();
/* 2240 */       if (xPStyle != null) {
/* 2241 */         XPStyle.Skin skin = xPStyle.getSkin(null, this.part);
/* 2242 */         i = skin.getHeight();
/*      */       } 
/* 2244 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class WindowsFontProperty
/*      */     extends DesktopProperty
/*      */   {
/*      */     WindowsFontProperty(String param1String, Object param1Object) {
/* 2255 */       super(param1String, param1Object);
/*      */     }
/*      */     
/*      */     public void invalidate(LookAndFeel param1LookAndFeel) {
/* 2259 */       if ("win.defaultGUI.font.height".equals(getKey())) {
/* 2260 */         ((WindowsLookAndFeel)param1LookAndFeel).style = null;
/*      */       }
/* 2262 */       super.invalidate(param1LookAndFeel);
/*      */     }
/*      */     
/*      */     protected Object configureValue(Object param1Object) {
/* 2266 */       if (param1Object instanceof Font) {
/* 2267 */         Font font = (Font)param1Object;
/* 2268 */         if ("MS Sans Serif".equals(font.getName())) {
/* 2269 */           byte b; int i = font.getSize();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2275 */             b = Toolkit.getDefaultToolkit().getScreenResolution();
/* 2276 */           } catch (HeadlessException headlessException) {
/* 2277 */             b = 96;
/*      */           } 
/* 2279 */           if (Math.round(i * 72.0F / b) < 8) {
/* 2280 */             i = Math.round((8 * b) / 72.0F);
/*      */           }
/*      */           
/* 2283 */           FontUIResource fontUIResource = new FontUIResource("Microsoft Sans Serif", font.getStyle(), i);
/* 2284 */           if (fontUIResource.getName() != null && fontUIResource
/* 2285 */             .getName().equals(fontUIResource.getFamily())) {
/* 2286 */             font = fontUIResource;
/* 2287 */           } else if (i != font.getSize()) {
/*      */             
/* 2289 */             font = new FontUIResource("MS Sans Serif", font.getStyle(), i);
/*      */           } 
/*      */         } 
/*      */         
/* 2293 */         if (FontUtilities.fontSupportsDefaultEncoding(font)) {
/* 2294 */           if (!(font instanceof UIResource)) {
/* 2295 */             font = new FontUIResource(font);
/*      */           }
/*      */         } else {
/*      */           
/* 2299 */           font = FontUtilities.getCompositeFontUIResource(font);
/*      */         } 
/* 2301 */         return font;
/*      */       } 
/*      */       
/* 2304 */       return super.configureValue(param1Object);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class WindowsFontSizeProperty
/*      */     extends DesktopProperty
/*      */   {
/*      */     private String fontName;
/*      */     
/*      */     private int fontSize;
/*      */     
/*      */     private int fontStyle;
/*      */ 
/*      */     
/*      */     WindowsFontSizeProperty(String param1String1, String param1String2, int param1Int1, int param1Int2) {
/* 2320 */       super(param1String1, (Object)null);
/* 2321 */       this.fontName = param1String2;
/* 2322 */       this.fontSize = param1Int2;
/* 2323 */       this.fontStyle = param1Int1;
/*      */     }
/*      */     
/*      */     protected Object configureValue(Object param1Object) {
/* 2327 */       if (param1Object == null) {
/* 2328 */         param1Object = new FontUIResource(this.fontName, this.fontStyle, this.fontSize);
/*      */       }
/* 2330 */       else if (param1Object instanceof Integer) {
/*      */         
/* 2332 */         param1Object = new FontUIResource(this.fontName, this.fontStyle, ((Integer)param1Object).intValue());
/*      */       } 
/* 2334 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class XPValue
/*      */     implements UIDefaults.ActiveValue
/*      */   {
/*      */     protected Object classicValue;
/*      */     
/*      */     protected Object xpValue;
/*      */     
/* 2347 */     private static final Object NULL_VALUE = new Object();
/*      */     
/*      */     XPValue(Object param1Object1, Object param1Object2) {
/* 2350 */       this.xpValue = param1Object1;
/* 2351 */       this.classicValue = param1Object2;
/*      */     }
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/* 2355 */       Object object = null;
/* 2356 */       if (XPStyle.getXP() != null) {
/* 2357 */         object = getXPValue(param1UIDefaults);
/*      */       }
/*      */       
/* 2360 */       if (object == null) {
/* 2361 */         object = getClassicValue(param1UIDefaults);
/* 2362 */       } else if (object == NULL_VALUE) {
/* 2363 */         object = null;
/*      */       } 
/*      */       
/* 2366 */       return object;
/*      */     }
/*      */     
/*      */     protected Object getXPValue(UIDefaults param1UIDefaults) {
/* 2370 */       return recursiveCreateValue(this.xpValue, param1UIDefaults);
/*      */     }
/*      */     
/*      */     protected Object getClassicValue(UIDefaults param1UIDefaults) {
/* 2374 */       return recursiveCreateValue(this.classicValue, param1UIDefaults);
/*      */     }
/*      */     
/*      */     private Object recursiveCreateValue(Object param1Object, UIDefaults param1UIDefaults) {
/* 2378 */       if (param1Object instanceof UIDefaults.LazyValue) {
/* 2379 */         param1Object = ((UIDefaults.LazyValue)param1Object).createValue(param1UIDefaults);
/*      */       }
/* 2381 */       if (param1Object instanceof UIDefaults.ActiveValue) {
/* 2382 */         return ((UIDefaults.ActiveValue)param1Object).createValue(param1UIDefaults);
/*      */       }
/* 2384 */       return param1Object;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class XPBorderValue
/*      */     extends XPValue {
/*      */     private final Border extraMargin;
/*      */     
/*      */     XPBorderValue(TMSchema.Part param1Part, Object param1Object) {
/* 2393 */       this(param1Part, param1Object, null);
/*      */     }
/*      */     
/*      */     XPBorderValue(TMSchema.Part param1Part, Object param1Object, Border param1Border) {
/* 2397 */       super(param1Part, param1Object);
/* 2398 */       this.extraMargin = param1Border;
/*      */     }
/*      */     
/*      */     public Object getXPValue(UIDefaults param1UIDefaults) {
/* 2402 */       XPStyle xPStyle = XPStyle.getXP();
/* 2403 */       Border border = (xPStyle != null) ? xPStyle.getBorder(null, (TMSchema.Part)this.xpValue) : null;
/* 2404 */       if (border != null && this.extraMargin != null) {
/* 2405 */         return new BorderUIResource.CompoundBorderUIResource(border, this.extraMargin);
/*      */       }
/*      */       
/* 2408 */       return border;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class XPColorValue
/*      */     extends XPValue {
/*      */     XPColorValue(TMSchema.Part param1Part, TMSchema.State param1State, TMSchema.Prop param1Prop, Object param1Object) {
/* 2415 */       super(new XPColorValueKey(param1Part, param1State, param1Prop), param1Object);
/*      */     }
/*      */     
/*      */     public Object getXPValue(UIDefaults param1UIDefaults) {
/* 2419 */       XPColorValueKey xPColorValueKey = (XPColorValueKey)this.xpValue;
/* 2420 */       XPStyle xPStyle = XPStyle.getXP();
/* 2421 */       return (xPStyle != null) ? xPStyle.getColor(xPColorValueKey.skin, xPColorValueKey.prop, null) : null;
/*      */     }
/*      */     
/*      */     private static class XPColorValueKey {
/*      */       XPStyle.Skin skin;
/*      */       TMSchema.Prop prop;
/*      */       
/*      */       XPColorValueKey(TMSchema.Part param2Part, TMSchema.State param2State, TMSchema.Prop param2Prop) {
/* 2429 */         this.skin = new XPStyle.Skin(param2Part, param2State);
/* 2430 */         this.prop = param2Prop;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class XPDLUValue extends XPValue {
/*      */     private int direction;
/*      */     
/*      */     XPDLUValue(int param1Int1, int param1Int2, int param1Int3) {
/* 2439 */       super(Integer.valueOf(param1Int1), Integer.valueOf(param1Int2));
/* 2440 */       this.direction = param1Int3;
/*      */     }
/*      */     
/*      */     public Object getXPValue(UIDefaults param1UIDefaults) {
/* 2444 */       int i = WindowsLookAndFeel.this.dluToPixels(((Integer)this.xpValue).intValue(), this.direction);
/* 2445 */       return Integer.valueOf(i);
/*      */     }
/*      */     
/*      */     public Object getClassicValue(UIDefaults param1UIDefaults) {
/* 2449 */       int i = WindowsLookAndFeel.this.dluToPixels(((Integer)this.classicValue).intValue(), this.direction);
/* 2450 */       return Integer.valueOf(i);
/*      */     }
/*      */   }
/*      */   
/*      */   private class TriggerDesktopProperty extends DesktopProperty {
/*      */     TriggerDesktopProperty(String param1String) {
/* 2456 */       super(param1String, (Object)null);
/*      */ 
/*      */ 
/*      */       
/* 2460 */       getValueFromDesktop();
/*      */     }
/*      */     
/*      */     protected void updateUI() {
/* 2464 */       super.updateUI();
/*      */ 
/*      */       
/* 2467 */       getValueFromDesktop();
/*      */     }
/*      */   }
/*      */   
/*      */   private class FontDesktopProperty extends TriggerDesktopProperty {
/*      */     FontDesktopProperty(String param1String) {
/* 2473 */       super(param1String);
/*      */     }
/*      */     
/*      */     protected void updateUI() {
/* 2477 */       SwingUtilities2.AATextInfo aATextInfo = SwingUtilities2.AATextInfo.getAATextInfo(true);
/* 2478 */       UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 2479 */       uIDefaults.put(SwingUtilities2.AA_TEXT_PROPERTY_KEY, aATextInfo);
/* 2480 */       super.updateUI();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class WindowsLayoutStyle
/*      */     extends DefaultLayoutStyle
/*      */   {
/*      */     private WindowsLayoutStyle() {}
/*      */ 
/*      */     
/*      */     public int getPreferredGap(JComponent param1JComponent1, JComponent param1JComponent2, LayoutStyle.ComponentPlacement param1ComponentPlacement, int param1Int, Container param1Container) {
/* 2492 */       super.getPreferredGap(param1JComponent1, param1JComponent2, param1ComponentPlacement, param1Int, param1Container);
/*      */ 
/*      */       
/* 2495 */       switch (param1ComponentPlacement) {
/*      */         
/*      */         case INDENT:
/* 2498 */           if (param1Int == 3 || param1Int == 7) {
/*      */             
/* 2500 */             int i = getIndent(param1JComponent1, param1Int);
/* 2501 */             if (i > 0) {
/* 2502 */               return i;
/*      */             }
/* 2504 */             return 10;
/*      */           } 
/*      */         
/*      */         case RELATED:
/* 2508 */           if (isLabelAndNonlabel(param1JComponent1, param1JComponent2, param1Int))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2518 */             return getButtonGap(param1JComponent1, param1JComponent2, param1Int, WindowsLookAndFeel.this
/* 2519 */                 .dluToPixels(3, param1Int));
/*      */           }
/*      */           
/* 2522 */           return getButtonGap(param1JComponent1, param1JComponent2, param1Int, WindowsLookAndFeel.this
/* 2523 */               .dluToPixels(4, param1Int));
/*      */         
/*      */         case UNRELATED:
/* 2526 */           return getButtonGap(param1JComponent1, param1JComponent2, param1Int, WindowsLookAndFeel.this
/* 2527 */               .dluToPixels(7, param1Int));
/*      */       } 
/* 2529 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getContainerGap(JComponent param1JComponent, int param1Int, Container param1Container) {
/* 2536 */       super.getContainerGap(param1JComponent, param1Int, param1Container);
/* 2537 */       return getButtonGap(param1JComponent, param1Int, WindowsLookAndFeel.this.dluToPixels(7, param1Int));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int dluToPixels(int paramInt1, int paramInt2) {
/* 2547 */     if (this.baseUnitX == 0) {
/* 2548 */       calculateBaseUnits();
/*      */     }
/* 2550 */     if (paramInt2 == 3 || paramInt2 == 7)
/*      */     {
/* 2552 */       return paramInt1 * this.baseUnitX / 4;
/*      */     }
/* 2554 */     assert paramInt2 == 1 || paramInt2 == 5;
/*      */     
/* 2556 */     return paramInt1 * this.baseUnitY / 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void calculateBaseUnits() {
/* 2565 */     FontMetrics fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(
/* 2566 */         UIManager.getFont("Button.font"));
/* 2567 */     this.baseUnitX = fontMetrics.stringWidth("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
/*      */     
/* 2569 */     this.baseUnitX = (this.baseUnitX / 26 + 1) / 2;
/*      */     
/* 2571 */     this.baseUnitY = fontMetrics.getAscent() + fontMetrics.getDescent() - 1;
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
/*      */   public Icon getDisabledIcon(JComponent paramJComponent, Icon paramIcon) {
/* 2584 */     if (paramIcon != null && paramJComponent != null && Boolean.TRUE
/*      */       
/* 2586 */       .equals(paramJComponent.getClientProperty(HI_RES_DISABLED_ICON_CLIENT_KEY)) && paramIcon
/* 2587 */       .getIconWidth() > 0 && paramIcon
/* 2588 */       .getIconHeight() > 0) {
/*      */       
/* 2590 */       BufferedImage bufferedImage = new BufferedImage(paramIcon.getIconWidth(), paramIcon.getIconWidth(), 2);
/* 2591 */       paramIcon.paintIcon(paramJComponent, bufferedImage.getGraphics(), 0, 0);
/* 2592 */       RGBGrayFilter rGBGrayFilter = new RGBGrayFilter();
/* 2593 */       FilteredImageSource filteredImageSource = new FilteredImageSource(bufferedImage.getSource(), rGBGrayFilter);
/* 2594 */       Image image = paramJComponent.createImage(filteredImageSource);
/* 2595 */       return new ImageIconUIResource(image);
/*      */     } 
/* 2597 */     return super.getDisabledIcon(paramJComponent, paramIcon);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class RGBGrayFilter
/*      */     extends RGBImageFilter
/*      */   {
/*      */     public int filterRGB(int param1Int1, int param1Int2, int param1Int3) {
/* 2606 */       float f1 = ((param1Int3 >> 16 & 0xFF) / 255.0F + (param1Int3 >> 8 & 0xFF) / 255.0F + (param1Int3 & 0xFF) / 255.0F) / 3.0F;
/*      */ 
/*      */ 
/*      */       
/* 2610 */       float f2 = (param1Int3 >> 24 & 0xFF) / 255.0F;
/*      */       
/* 2612 */       f1 = Math.min(1.0F, (1.0F - f1) / 2.857143F + f1);
/*      */       
/* 2614 */       return (int)(f2 * 255.0F) << 24 | (int)(f1 * 255.0F) << 16 | (int)(f1 * 255.0F) << 8 | (int)(f1 * 255.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class FocusColorProperty
/*      */     extends DesktopProperty
/*      */   {
/*      */     public FocusColorProperty() {
/* 2625 */       super("win.3d.backgroundColor", Color.BLACK);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object configureValue(Object param1Object) {
/* 2631 */       Object object = Toolkit.getDefaultToolkit().getDesktopProperty("win.highContrast.on");
/* 2632 */       if (object == null || !((Boolean)object).booleanValue()) {
/* 2633 */         return Color.BLACK;
/*      */       }
/* 2635 */       return Color.BLACK.equals(param1Object) ? Color.WHITE : Color.BLACK;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsLookAndFeel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */