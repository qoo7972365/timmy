/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyEventPostProcessor;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.SwingPropertyChangeSupport;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.OSInfo;
/*      */ import sun.awt.PaintEventDispatcher;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.swing.DefaultLookup;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UIManager
/*      */   implements Serializable
/*      */ {
/*      */   private static class LAFState
/*      */   {
/*      */     Properties swingProps;
/*  192 */     private UIDefaults[] tables = new UIDefaults[2];
/*      */     
/*      */     boolean initialized = false;
/*      */     boolean focusPolicyInitialized = false;
/*  196 */     MultiUIDefaults multiUIDefaults = new MultiUIDefaults(this.tables);
/*      */     LookAndFeel lookAndFeel;
/*  198 */     LookAndFeel multiLookAndFeel = null;
/*  199 */     Vector<LookAndFeel> auxLookAndFeels = null;
/*      */     SwingPropertyChangeSupport changeSupport;
/*      */     UIManager.LookAndFeelInfo[] installedLAFs;
/*      */     
/*      */     UIDefaults getLookAndFeelDefaults() {
/*  204 */       return this.tables[0]; } void setLookAndFeelDefaults(UIDefaults param1UIDefaults) {
/*  205 */       this.tables[0] = param1UIDefaults;
/*      */     }
/*  207 */     UIDefaults getSystemDefaults() { return this.tables[1]; } void setSystemDefaults(UIDefaults param1UIDefaults) {
/*  208 */       this.tables[1] = param1UIDefaults;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized SwingPropertyChangeSupport getPropertyChangeSupport(boolean param1Boolean) {
/*  219 */       if (param1Boolean && this.changeSupport == null) {
/*  220 */         this.changeSupport = new SwingPropertyChangeSupport(UIManager.class);
/*      */       }
/*      */       
/*  223 */       return this.changeSupport;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private LAFState() {}
/*      */   }
/*      */ 
/*      */   
/*  232 */   private static final Object classLock = new Object();
/*      */   
/*      */   private static final String defaultLAFKey = "swing.defaultlaf";
/*      */   
/*      */   private static final String auxiliaryLAFsKey = "swing.auxiliarylaf";
/*      */   private static final String multiplexingLAFKey = "swing.plaf.multiplexinglaf";
/*      */   private static final String installedLAFsKey = "swing.installedlafs";
/*      */   private static final String disableMnemonicKey = "swing.disablenavaids";
/*      */   private static LookAndFeelInfo[] installedLAFs;
/*      */   
/*      */   private static LAFState getLAFState() {
/*  243 */     LAFState lAFState = (LAFState)SwingUtilities.appContextGet(SwingUtilities2.LAF_STATE_KEY);
/*      */     
/*  245 */     if (lAFState == null) {
/*  246 */       synchronized (classLock) {
/*  247 */         lAFState = (LAFState)SwingUtilities.appContextGet(SwingUtilities2.LAF_STATE_KEY);
/*      */         
/*  249 */         if (lAFState == null) {
/*  250 */           SwingUtilities.appContextPut(SwingUtilities2.LAF_STATE_KEY, lAFState = new LAFState());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  256 */     return lAFState;
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
/*      */   private static String makeInstalledLAFKey(String paramString1, String paramString2) {
/*  276 */     return "swing.installedlaf." + paramString1 + "." + paramString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String makeSwingPropertiesFilename() {
/*  287 */     String str1 = File.separator;
/*      */ 
/*      */     
/*  290 */     String str2 = System.getProperty("java.home");
/*  291 */     if (str2 == null) {
/*  292 */       str2 = "<java.home undefined>";
/*      */     }
/*  294 */     return str2 + str1 + "lib" + str1 + "swing.properties";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LookAndFeelInfo
/*      */   {
/*      */     private String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String className;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LookAndFeelInfo(String param1String1, String param1String2) {
/*  320 */       this.name = param1String1;
/*  321 */       this.className = param1String2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/*  331 */       return this.name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getClassName() {
/*  341 */       return this.className;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  351 */       return getClass().getName() + "[" + getName() + " " + getClassName() + "]";
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
/*      */   static {
/*  367 */     ArrayList<LookAndFeelInfo> arrayList = new ArrayList(4);
/*  368 */     arrayList.add(new LookAndFeelInfo("Metal", "javax.swing.plaf.metal.MetalLookAndFeel"));
/*      */     
/*  370 */     arrayList.add(new LookAndFeelInfo("Nimbus", "javax.swing.plaf.nimbus.NimbusLookAndFeel"));
/*      */     
/*  372 */     arrayList.add(new LookAndFeelInfo("CDE/Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"));
/*      */ 
/*      */ 
/*      */     
/*  376 */     OSInfo.OSType oSType = AccessController.<OSInfo.OSType>doPrivileged(OSInfo.getOSTypeAction());
/*  377 */     if (oSType == OSInfo.OSType.WINDOWS) {
/*  378 */       arrayList.add(new LookAndFeelInfo("Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
/*      */       
/*  380 */       if (Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive") != null)
/*      */       {
/*  382 */         arrayList.add(new LookAndFeelInfo("Windows Classic", "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"));
/*      */       
/*      */       }
/*      */     }
/*  386 */     else if (oSType == OSInfo.OSType.MACOSX) {
/*  387 */       arrayList.add(new LookAndFeelInfo("Mac OS X", "com.apple.laf.AquaLookAndFeel"));
/*      */     }
/*      */     else {
/*      */       
/*  391 */       arrayList.add(new LookAndFeelInfo("GTK+", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"));
/*      */     } 
/*      */     
/*  394 */     installedLAFs = arrayList.<LookAndFeelInfo>toArray(new LookAndFeelInfo[arrayList.size()]);
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
/*      */   public static LookAndFeelInfo[] getInstalledLookAndFeels() {
/*  419 */     maybeInitialize();
/*  420 */     LookAndFeelInfo[] arrayOfLookAndFeelInfo1 = (getLAFState()).installedLAFs;
/*  421 */     if (arrayOfLookAndFeelInfo1 == null) {
/*  422 */       arrayOfLookAndFeelInfo1 = installedLAFs;
/*      */     }
/*  424 */     LookAndFeelInfo[] arrayOfLookAndFeelInfo2 = new LookAndFeelInfo[arrayOfLookAndFeelInfo1.length];
/*  425 */     System.arraycopy(arrayOfLookAndFeelInfo1, 0, arrayOfLookAndFeelInfo2, 0, arrayOfLookAndFeelInfo1.length);
/*  426 */     return arrayOfLookAndFeelInfo2;
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
/*      */   public static void setInstalledLookAndFeels(LookAndFeelInfo[] paramArrayOfLookAndFeelInfo) throws SecurityException {
/*  445 */     maybeInitialize();
/*  446 */     LookAndFeelInfo[] arrayOfLookAndFeelInfo = new LookAndFeelInfo[paramArrayOfLookAndFeelInfo.length];
/*  447 */     System.arraycopy(paramArrayOfLookAndFeelInfo, 0, arrayOfLookAndFeelInfo, 0, paramArrayOfLookAndFeelInfo.length);
/*  448 */     (getLAFState()).installedLAFs = arrayOfLookAndFeelInfo;
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
/*      */   public static void installLookAndFeel(LookAndFeelInfo paramLookAndFeelInfo) {
/*  462 */     LookAndFeelInfo[] arrayOfLookAndFeelInfo1 = getInstalledLookAndFeels();
/*  463 */     LookAndFeelInfo[] arrayOfLookAndFeelInfo2 = new LookAndFeelInfo[arrayOfLookAndFeelInfo1.length + 1];
/*  464 */     System.arraycopy(arrayOfLookAndFeelInfo1, 0, arrayOfLookAndFeelInfo2, 0, arrayOfLookAndFeelInfo1.length);
/*  465 */     arrayOfLookAndFeelInfo2[arrayOfLookAndFeelInfo1.length] = paramLookAndFeelInfo;
/*  466 */     setInstalledLookAndFeels(arrayOfLookAndFeelInfo2);
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
/*      */   public static void installLookAndFeel(String paramString1, String paramString2) {
/*  481 */     installLookAndFeel(new LookAndFeelInfo(paramString1, paramString2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookAndFeel getLookAndFeel() {
/*  492 */     maybeInitialize();
/*  493 */     return (getLAFState()).lookAndFeel;
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
/*      */   public static void setLookAndFeel(LookAndFeel paramLookAndFeel) throws UnsupportedLookAndFeelException {
/*  524 */     if (paramLookAndFeel != null && !paramLookAndFeel.isSupportedLookAndFeel()) {
/*  525 */       String str = paramLookAndFeel.toString() + " not supported on this platform";
/*  526 */       throw new UnsupportedLookAndFeelException(str);
/*      */     } 
/*      */     
/*  529 */     LAFState lAFState = getLAFState();
/*  530 */     LookAndFeel lookAndFeel = lAFState.lookAndFeel;
/*  531 */     if (lookAndFeel != null) {
/*  532 */       lookAndFeel.uninitialize();
/*      */     }
/*      */     
/*  535 */     lAFState.lookAndFeel = paramLookAndFeel;
/*  536 */     if (paramLookAndFeel != null) {
/*  537 */       DefaultLookup.setDefaultLookup(null);
/*  538 */       paramLookAndFeel.initialize();
/*  539 */       lAFState.setLookAndFeelDefaults(paramLookAndFeel.getDefaults());
/*      */     } else {
/*      */       
/*  542 */       lAFState.setLookAndFeelDefaults(null);
/*      */     } 
/*      */ 
/*      */     
/*  546 */     SwingPropertyChangeSupport swingPropertyChangeSupport = lAFState.getPropertyChangeSupport(false);
/*  547 */     if (swingPropertyChangeSupport != null) {
/*  548 */       swingPropertyChangeSupport.firePropertyChange("lookAndFeel", lookAndFeel, paramLookAndFeel);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLookAndFeel(String paramString) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
/*  577 */     if ("javax.swing.plaf.metal.MetalLookAndFeel".equals(paramString)) {
/*      */       
/*  579 */       setLookAndFeel(new MetalLookAndFeel());
/*      */     } else {
/*      */       
/*  582 */       Class<?> clazz = SwingUtilities.loadSystemClass(paramString);
/*  583 */       setLookAndFeel((LookAndFeel)clazz.newInstance());
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
/*      */   
/*      */   public static String getSystemLookAndFeelClassName() {
/*  601 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("swing.systemlaf"));
/*      */     
/*  603 */     if (str1 != null) {
/*  604 */       return str1;
/*      */     }
/*  606 */     OSInfo.OSType oSType = AccessController.<OSInfo.OSType>doPrivileged(OSInfo.getOSTypeAction());
/*  607 */     if (oSType == OSInfo.OSType.WINDOWS) {
/*  608 */       return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
/*      */     }
/*  610 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.desktop"));
/*  611 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  612 */     if ("gnome".equals(str2) && toolkit instanceof SunToolkit && ((SunToolkit)toolkit)
/*      */       
/*  614 */       .isNativeGTKAvailable())
/*      */     {
/*  616 */       return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
/*      */     }
/*  618 */     if (oSType == OSInfo.OSType.MACOSX && 
/*  619 */       toolkit.getClass().getName()
/*  620 */       .equals("sun.lwawt.macosx.LWCToolkit")) {
/*  621 */       return "com.apple.laf.AquaLookAndFeel";
/*      */     }
/*      */     
/*  624 */     if (oSType == OSInfo.OSType.SOLARIS) {
/*  625 */       return "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
/*      */     }
/*      */     
/*  628 */     return getCrossPlatformLookAndFeelClassName();
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
/*      */   public static String getCrossPlatformLookAndFeelClassName() {
/*  643 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("swing.crossplatformlaf"));
/*      */     
/*  645 */     if (str != null) {
/*  646 */       return str;
/*      */     }
/*  648 */     return "javax.swing.plaf.metal.MetalLookAndFeel";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UIDefaults getDefaults() {
/*  659 */     maybeInitialize();
/*  660 */     return (getLAFState()).multiUIDefaults;
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
/*      */   public static Font getFont(Object paramObject) {
/*  672 */     return getDefaults().getFont(paramObject);
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
/*      */   public static Font getFont(Object paramObject, Locale paramLocale) {
/*  689 */     return getDefaults().getFont(paramObject, paramLocale);
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
/*      */   public static Color getColor(Object paramObject) {
/*  701 */     return getDefaults().getColor(paramObject);
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
/*      */   public static Color getColor(Object paramObject, Locale paramLocale) {
/*  718 */     return getDefaults().getColor(paramObject, paramLocale);
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
/*      */   public static Icon getIcon(Object paramObject) {
/*  730 */     return getDefaults().getIcon(paramObject);
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
/*      */   public static Icon getIcon(Object paramObject, Locale paramLocale) {
/*  747 */     return getDefaults().getIcon(paramObject, paramLocale);
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
/*      */   public static Border getBorder(Object paramObject) {
/*  759 */     return getDefaults().getBorder(paramObject);
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
/*      */   public static Border getBorder(Object paramObject, Locale paramLocale) {
/*  776 */     return getDefaults().getBorder(paramObject, paramLocale);
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
/*      */   public static String getString(Object paramObject) {
/*  788 */     return getDefaults().getString(paramObject);
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
/*      */   public static String getString(Object paramObject, Locale paramLocale) {
/*  805 */     return getDefaults().getString(paramObject, paramLocale);
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
/*      */   static String getString(Object paramObject, Component paramComponent) {
/*  821 */     Locale locale = (paramComponent == null) ? Locale.getDefault() : paramComponent.getLocale();
/*  822 */     return getString(paramObject, locale);
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
/*      */   public static int getInt(Object paramObject) {
/*  835 */     return getDefaults().getInt(paramObject);
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
/*      */   public static int getInt(Object paramObject, Locale paramLocale) {
/*  853 */     return getDefaults().getInt(paramObject, paramLocale);
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
/*      */   public static boolean getBoolean(Object paramObject) {
/*  867 */     return getDefaults().getBoolean(paramObject);
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
/*      */   public static boolean getBoolean(Object paramObject, Locale paramLocale) {
/*  886 */     return getDefaults().getBoolean(paramObject, paramLocale);
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
/*      */   public static Insets getInsets(Object paramObject) {
/*  898 */     return getDefaults().getInsets(paramObject);
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
/*      */   public static Insets getInsets(Object paramObject, Locale paramLocale) {
/*  915 */     return getDefaults().getInsets(paramObject, paramLocale);
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
/*      */   public static Dimension getDimension(Object paramObject) {
/*  927 */     return getDefaults().getDimension(paramObject);
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
/*      */   public static Dimension getDimension(Object paramObject, Locale paramLocale) {
/*  944 */     return getDefaults().getDimension(paramObject, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object get(Object paramObject) {
/*  955 */     return getDefaults().get(paramObject);
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
/*      */   public static Object get(Object paramObject, Locale paramLocale) {
/*  971 */     return getDefaults().get(paramObject, paramLocale);
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
/*      */   public static Object put(Object paramObject1, Object paramObject2) {
/*  988 */     return getDefaults().put(paramObject1, paramObject2);
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
/*      */   public static ComponentUI getUI(JComponent paramJComponent) {
/* 1006 */     maybeInitialize();
/* 1007 */     maybeInitializeFocusPolicy(paramJComponent);
/* 1008 */     ComponentUI componentUI = null;
/* 1009 */     LookAndFeel lookAndFeel = (getLAFState()).multiLookAndFeel;
/* 1010 */     if (lookAndFeel != null)
/*      */     {
/*      */       
/* 1013 */       componentUI = lookAndFeel.getDefaults().getUI(paramJComponent);
/*      */     }
/* 1015 */     if (componentUI == null) {
/* 1016 */       componentUI = getDefaults().getUI(paramJComponent);
/*      */     }
/* 1018 */     return componentUI;
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
/*      */   public static UIDefaults getLookAndFeelDefaults() {
/* 1037 */     maybeInitialize();
/* 1038 */     return getLAFState().getLookAndFeelDefaults();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static LookAndFeel getMultiLookAndFeel() {
/* 1045 */     LookAndFeel lookAndFeel = (getLAFState()).multiLookAndFeel;
/* 1046 */     if (lookAndFeel == null) {
/* 1047 */       String str1 = "javax.swing.plaf.multi.MultiLookAndFeel";
/* 1048 */       String str2 = (getLAFState()).swingProps.getProperty("swing.plaf.multiplexinglaf", str1);
/*      */       try {
/* 1050 */         Class<?> clazz = SwingUtilities.loadSystemClass(str2);
/* 1051 */         lookAndFeel = (LookAndFeel)clazz.newInstance();
/* 1052 */       } catch (Exception exception) {
/* 1053 */         System.err.println("UIManager: failed loading " + str2);
/*      */       } 
/*      */     } 
/* 1056 */     return lookAndFeel;
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
/*      */   public static void addAuxiliaryLookAndFeel(LookAndFeel paramLookAndFeel) {
/* 1076 */     maybeInitialize();
/*      */     
/* 1078 */     if (!paramLookAndFeel.isSupportedLookAndFeel()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1083 */     Vector<LookAndFeel> vector = (getLAFState()).auxLookAndFeels;
/* 1084 */     if (vector == null) {
/* 1085 */       vector = new Vector<>();
/*      */     }
/*      */     
/* 1088 */     if (!vector.contains(paramLookAndFeel)) {
/* 1089 */       vector.addElement(paramLookAndFeel);
/* 1090 */       paramLookAndFeel.initialize();
/* 1091 */       (getLAFState()).auxLookAndFeels = vector;
/*      */       
/* 1093 */       if ((getLAFState()).multiLookAndFeel == null) {
/* 1094 */         (getLAFState()).multiLookAndFeel = getMultiLookAndFeel();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean removeAuxiliaryLookAndFeel(LookAndFeel paramLookAndFeel) {
/* 1115 */     maybeInitialize();
/*      */ 
/*      */ 
/*      */     
/* 1119 */     Vector<LookAndFeel> vector = (getLAFState()).auxLookAndFeels;
/* 1120 */     if (vector == null || vector.size() == 0) {
/* 1121 */       return false;
/*      */     }
/*      */     
/* 1124 */     boolean bool = vector.removeElement(paramLookAndFeel);
/* 1125 */     if (bool) {
/* 1126 */       if (vector.size() == 0) {
/* 1127 */         (getLAFState()).auxLookAndFeels = null;
/* 1128 */         (getLAFState()).multiLookAndFeel = null;
/*      */       } else {
/* 1130 */         (getLAFState()).auxLookAndFeels = vector;
/*      */       } 
/*      */     }
/* 1133 */     paramLookAndFeel.uninitialize();
/*      */     
/* 1135 */     return bool;
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
/*      */   public static LookAndFeel[] getAuxiliaryLookAndFeels() {
/* 1153 */     maybeInitialize();
/*      */     
/* 1155 */     Vector<LookAndFeel> vector = (getLAFState()).auxLookAndFeels;
/* 1156 */     if (vector == null || vector.size() == 0) {
/* 1157 */       return null;
/*      */     }
/*      */     
/* 1160 */     LookAndFeel[] arrayOfLookAndFeel = new LookAndFeel[vector.size()];
/* 1161 */     for (byte b = 0; b < arrayOfLookAndFeel.length; b++) {
/* 1162 */       arrayOfLookAndFeel[b] = vector.elementAt(b);
/*      */     }
/* 1164 */     return arrayOfLookAndFeel;
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
/*      */   public static void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 1178 */     synchronized (classLock) {
/* 1179 */       getLAFState().getPropertyChangeSupport(true)
/* 1180 */         .addPropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public static void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 1195 */     synchronized (classLock) {
/* 1196 */       getLAFState().getPropertyChangeSupport(true)
/* 1197 */         .removePropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public static PropertyChangeListener[] getPropertyChangeListeners() {
/* 1211 */     synchronized (classLock) {
/* 1212 */       return getLAFState().getPropertyChangeSupport(true)
/* 1213 */         .getPropertyChangeListeners();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Properties loadSwingProperties() {
/* 1222 */     if (UIManager.class.getClassLoader() != null) {
/* 1223 */       return new Properties();
/*      */     }
/*      */     
/* 1226 */     final Properties props = new Properties();
/*      */     
/* 1228 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/* 1231 */             OSInfo.OSType oSType = AccessController.<OSInfo.OSType>doPrivileged(OSInfo.getOSTypeAction());
/* 1232 */             if (oSType == OSInfo.OSType.MACOSX) {
/* 1233 */               props.put("swing.defaultlaf", UIManager.getSystemLookAndFeelClassName());
/*      */             }
/*      */             
/*      */             try {
/* 1237 */               File file = new File(UIManager.makeSwingPropertiesFilename());
/*      */               
/* 1239 */               if (file.exists())
/*      */               {
/*      */                 
/* 1242 */                 FileInputStream fileInputStream = new FileInputStream(file);
/* 1243 */                 props.load(fileInputStream);
/* 1244 */                 fileInputStream.close();
/*      */               }
/*      */             
/* 1247 */             } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1253 */             UIManager.checkProperty(props, "swing.defaultlaf");
/* 1254 */             UIManager.checkProperty(props, "swing.auxiliarylaf");
/* 1255 */             UIManager.checkProperty(props, "swing.plaf.multiplexinglaf");
/* 1256 */             UIManager.checkProperty(props, "swing.installedlafs");
/* 1257 */             UIManager.checkProperty(props, "swing.disablenavaids");
/*      */             
/* 1259 */             return null;
/*      */           }
/*      */         });
/* 1262 */     return properties;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkProperty(Properties paramProperties, String paramString) {
/* 1269 */     String str = System.getProperty(paramString);
/* 1270 */     if (str != null) {
/* 1271 */       paramProperties.put(paramString, str);
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
/*      */   private static void initializeInstalledLAFs(Properties paramProperties) {
/* 1285 */     String str = paramProperties.getProperty("swing.installedlafs");
/* 1286 */     if (str == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1294 */     Vector<String> vector = new Vector();
/* 1295 */     StringTokenizer stringTokenizer = new StringTokenizer(str, ",", false);
/* 1296 */     while (stringTokenizer.hasMoreTokens()) {
/* 1297 */       vector.addElement(stringTokenizer.nextToken());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1304 */     Vector<LookAndFeelInfo> vector1 = new Vector(vector.size());
/* 1305 */     for (String str1 : vector) {
/* 1306 */       String str2 = paramProperties.getProperty(makeInstalledLAFKey(str1, "name"), str1);
/* 1307 */       String str3 = paramProperties.getProperty(makeInstalledLAFKey(str1, "class"));
/* 1308 */       if (str3 != null) {
/* 1309 */         vector1.addElement(new LookAndFeelInfo(str2, str3));
/*      */       }
/*      */     } 
/*      */     
/* 1313 */     LookAndFeelInfo[] arrayOfLookAndFeelInfo = new LookAndFeelInfo[vector1.size()];
/* 1314 */     for (byte b = 0; b < vector1.size(); b++) {
/* 1315 */       arrayOfLookAndFeelInfo[b] = vector1.elementAt(b);
/*      */     }
/* 1317 */     (getLAFState()).installedLAFs = arrayOfLookAndFeelInfo;
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
/*      */   private static void initializeDefaultLAF(Properties paramProperties) {
/* 1331 */     if ((getLAFState()).lookAndFeel != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1337 */     String str = null;
/*      */     
/* 1339 */     HashMap hashMap = (HashMap)AppContext.getAppContext().remove("swing.lafdata");
/* 1340 */     if (hashMap != null) {
/* 1341 */       str = (String)hashMap.remove("defaultlaf");
/*      */     }
/* 1343 */     if (str == null) {
/* 1344 */       str = getCrossPlatformLookAndFeelClassName();
/*      */     }
/* 1346 */     str = paramProperties.getProperty("swing.defaultlaf", str);
/*      */     
/*      */     try {
/* 1349 */       setLookAndFeel(str);
/* 1350 */     } catch (Exception exception) {
/* 1351 */       throw new Error("Cannot load " + str);
/*      */     } 
/*      */ 
/*      */     
/* 1355 */     if (hashMap != null) {
/* 1356 */       for (Object object : hashMap.keySet()) {
/* 1357 */         put(object, hashMap.get(object));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void initializeAuxiliaryLAFs(Properties paramProperties) {
/* 1365 */     String str = paramProperties.getProperty("swing.auxiliarylaf");
/* 1366 */     if (str == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1370 */     Vector<LookAndFeel> vector = new Vector();
/*      */     
/* 1372 */     StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1378 */     while (stringTokenizer.hasMoreTokens()) {
/* 1379 */       String str1 = stringTokenizer.nextToken();
/*      */       try {
/* 1381 */         Class<?> clazz = SwingUtilities.loadSystemClass(str1);
/* 1382 */         LookAndFeel lookAndFeel = (LookAndFeel)clazz.newInstance();
/* 1383 */         lookAndFeel.initialize();
/* 1384 */         vector.addElement(lookAndFeel);
/*      */       }
/* 1386 */       catch (Exception exception) {
/* 1387 */         System.err.println("UIManager: failed loading auxiliary look and feel " + str1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1396 */     if (vector.size() == 0) {
/* 1397 */       vector = null;
/*      */     } else {
/*      */       
/* 1400 */       (getLAFState()).multiLookAndFeel = getMultiLookAndFeel();
/* 1401 */       if ((getLAFState()).multiLookAndFeel == null) {
/* 1402 */         vector = null;
/*      */       }
/*      */     } 
/*      */     
/* 1406 */     (getLAFState()).auxLookAndFeels = vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void initializeSystemDefaults(Properties paramProperties) {
/* 1411 */     (getLAFState()).swingProps = paramProperties;
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
/*      */   private static void maybeInitialize() {
/* 1423 */     synchronized (classLock) {
/* 1424 */       if (!(getLAFState()).initialized) {
/* 1425 */         (getLAFState()).initialized = true;
/* 1426 */         initialize();
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
/*      */   private static void maybeInitializeFocusPolicy(JComponent paramJComponent) {
/* 1438 */     if (paramJComponent instanceof JRootPane) {
/* 1439 */       synchronized (classLock) {
/* 1440 */         if (!(getLAFState()).focusPolicyInitialized) {
/* 1441 */           (getLAFState()).focusPolicyInitialized = true;
/*      */           
/* 1443 */           if (FocusManager.isFocusManagerEnabled()) {
/* 1444 */             KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 1445 */               .setDefaultFocusTraversalPolicy(new LayoutFocusTraversalPolicy());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void initialize() {
/* 1457 */     Properties properties = loadSwingProperties();
/* 1458 */     initializeSystemDefaults(properties);
/* 1459 */     initializeDefaultLAF(properties);
/* 1460 */     initializeAuxiliaryLAFs(properties);
/* 1461 */     initializeInstalledLAFs(properties);
/*      */ 
/*      */     
/* 1464 */     if (RepaintManager.HANDLE_TOP_LEVEL_PAINT) {
/* 1465 */       PaintEventDispatcher.setPaintEventDispatcher(new SwingPaintEventDispatcher());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1475 */     KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 1476 */       .addKeyEventPostProcessor(new KeyEventPostProcessor() {
/*      */           public boolean postProcessKeyEvent(KeyEvent param1KeyEvent) {
/* 1478 */             Component component = param1KeyEvent.getComponent();
/*      */             
/* 1480 */             if ((!(component instanceof JComponent) || (component != null && 
/* 1481 */               !component.isEnabled())) && 
/* 1482 */               JComponent.KeyboardState.shouldProcess(param1KeyEvent) && 
/* 1483 */               SwingUtilities.processKeyBindings(param1KeyEvent)) {
/* 1484 */               param1KeyEvent.consume();
/* 1485 */               return true;
/*      */             } 
/* 1487 */             return false;
/*      */           }
/*      */         });
/* 1490 */     AWTAccessor.getComponentAccessor()
/* 1491 */       .setRequestFocusController(JComponent.focusController);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/UIManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */