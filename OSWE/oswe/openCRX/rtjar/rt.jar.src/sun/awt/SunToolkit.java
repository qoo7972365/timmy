/*      */ package sun.awt;
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Canvas;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Image;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.DataBuffer;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.ImageProducer;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.awt.peer.CanvasPeer;
/*      */ import java.awt.peer.FramePeer;
/*      */ import java.awt.peer.PanelPeer;
/*      */ import java.io.IOException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.awt.image.FileImageSource;
/*      */ import sun.awt.image.ImageRepresentation;
/*      */ import sun.awt.image.MultiResolutionToolkitImage;
/*      */ import sun.awt.image.ToolkitImage;
/*      */ import sun.awt.image.URLImageSource;
/*      */ import sun.misc.SoftCache;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ public abstract class SunToolkit extends Toolkit implements WindowClosingSupport, WindowClosingListener, ComponentFactory, InputMethodSupport, KeyboardFocusManagerPeerProvider {
/*      */   public static final int GRAB_EVENT_MASK = -2147483648;
/*      */   private static final String POST_EVENT_QUEUE_KEY = "PostEventQueue";
/*      */   protected static int numberOfButtons;
/*      */   public static final int MAX_BUTTONS_SUPPORTED = 20;
/*      */   private static final ReentrantLock AWT_LOCK;
/*      */   private static final Condition AWT_LOCK_COND;
/*      */   private static final Map<Object, AppContext> appContextMap;
/*      */   static final SoftCache fileImgCache;
/*      */   static final SoftCache urlImgCache;
/*      */   private static Locale startupLocale;
/*      */   private transient WindowClosingListener windowClosingListener;
/*      */   private static DefaultMouseInfoPeer mPeer;
/*      */   private static Dialog.ModalExclusionType DEFAULT_MODAL_EXCLUSION_TYPE;
/*      */   private ModalityListenerList modalityListeners;
/*      */   public static final int DEFAULT_WAIT_TIME = 10000;
/*      */   private static final int MAX_ITERS = 20;
/*      */   private static final int MIN_ITERS = 0;
/*      */   private static final int MINIMAL_EDELAY = 0;
/*      */   private boolean eventDispatched;
/*      */   private boolean queueEmpty;
/*      */   private final Object waitLock;
/*      */   
/*      */   static {
/*   70 */     if (((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.nativedebug"))).booleanValue())
/*   71 */       DebugSettings.init(); 
/*      */   }
/*   73 */   private static boolean touchKeyboardAutoShowIsEnabled = Boolean.valueOf(
/*   74 */       AccessController.<String>doPrivileged(new GetPropertyAction("awt.touchKeyboardAutoShowIsEnabled", "true"))).booleanValue();
/*      */ 
/*      */   
/*      */   private static boolean checkedSystemAAFontSettings;
/*      */ 
/*      */   
/*      */   private static boolean useSystemAAFontSettings;
/*      */   
/*      */   private static boolean lastExtraCondition;
/*      */   
/*      */   private static RenderingHints desktopFontHints;
/*      */   
/*      */   public static final String DESKTOPFONTHINTS = "awt.font.desktophints";
/*      */   
/*      */   private static Boolean sunAwtDisableMixing;
/*      */   
/*      */   private static final Object DEACTIVATION_TIMES_MAP_KEY;
/*      */ 
/*      */   
/*      */   static {
/*   94 */     numberOfButtons = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  252 */     AWT_LOCK = new ReentrantLock();
/*  253 */     AWT_LOCK_COND = AWT_LOCK.newCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  349 */     appContextMap = Collections.synchronizedMap(new WeakHashMap<>());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  721 */     fileImgCache = new SoftCache();
/*      */     
/*  723 */     urlImgCache = new SoftCache();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1176 */     startupLocale = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1253 */     mPeer = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1295 */     DEFAULT_MODAL_EXCLUSION_TYPE = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1741 */     lastExtraCondition = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1919 */     sunAwtDisableMixing = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1942 */     DEACTIVATION_TIMES_MAP_KEY = new Object();
/*      */   } private static void initEQ(AppContext paramAppContext) { EventQueue eventQueue; String str = System.getProperty("AWT.EventQueueClass", "java.awt.EventQueue"); try { eventQueue = (EventQueue)Class.forName(str).newInstance(); } catch (Exception exception) { exception.printStackTrace(); System.err.println("Failed loading " + str + ": " + exception); eventQueue = new EventQueue(); }  paramAppContext.put(AppContext.EVENT_QUEUE_KEY, eventQueue); PostEventQueue postEventQueue = new PostEventQueue(eventQueue); paramAppContext.put("PostEventQueue", postEventQueue); } public SunToolkit() { this.windowClosingListener = null; this.modalityListeners = new ModalityListenerList(); this.eventDispatched = false; this.queueEmpty = false; this.waitLock = "Wait Lock"; } public boolean useBufferPerWindow() { return false; } public static final void awtLock() { AWT_LOCK.lock(); } public static final boolean awtTryLock() { return AWT_LOCK.tryLock(); } public static final void awtUnlock() { AWT_LOCK.unlock(); } public static final void awtLockWait() throws InterruptedException { AWT_LOCK_COND.await(); } public static final void awtLockWait(long paramLong) throws InterruptedException { AWT_LOCK_COND.await(paramLong, TimeUnit.MILLISECONDS); } public static final void awtLockNotify() { AWT_LOCK_COND.signal(); } public static final void awtLockNotifyAll() { AWT_LOCK_COND.signalAll(); } public static final boolean isAWTLockHeldByCurrentThread() { return AWT_LOCK.isHeldByCurrentThread(); } public static AppContext createNewAppContext() { ThreadGroup threadGroup = Thread.currentThread().getThreadGroup(); return createNewAppContext(threadGroup); } static final AppContext createNewAppContext(ThreadGroup paramThreadGroup) { AppContext appContext = new AppContext(paramThreadGroup); initEQ(appContext); return appContext; } static void wakeupEventQueue(EventQueue paramEventQueue, boolean paramBoolean) { AWTAccessor.getEventQueueAccessor().wakeup(paramEventQueue, paramBoolean); } protected static Object targetToPeer(Object paramObject) { if (paramObject != null && !GraphicsEnvironment.isHeadless()) return AWTAutoShutdown.getInstance().getPeer(paramObject);  return null; } protected static void targetCreatedPeer(Object paramObject1, Object paramObject2) { if (paramObject1 != null && paramObject2 != null && !GraphicsEnvironment.isHeadless()) AWTAutoShutdown.getInstance().registerPeer(paramObject1, paramObject2);  } protected static void targetDisposedPeer(Object paramObject1, Object paramObject2) { if (paramObject1 != null && paramObject2 != null && !GraphicsEnvironment.isHeadless()) AWTAutoShutdown.getInstance().unregisterPeer(paramObject1, paramObject2);  } private static boolean setAppContext(Object paramObject, AppContext paramAppContext) { if (paramObject instanceof Component) { AWTAccessor.getComponentAccessor().setAppContext((Component)paramObject, paramAppContext); } else if (paramObject instanceof MenuComponent) { AWTAccessor.getMenuComponentAccessor().setAppContext((MenuComponent)paramObject, paramAppContext); } else { return false; }  return true; } private static AppContext getAppContext(Object paramObject) { if (paramObject instanceof Component) return AWTAccessor.getComponentAccessor().getAppContext((Component)paramObject);  if (paramObject instanceof MenuComponent) return AWTAccessor.getMenuComponentAccessor().getAppContext((MenuComponent)paramObject);  return null; } public static AppContext targetToAppContext(Object paramObject) { if (paramObject == null) return null;  AppContext appContext = getAppContext(paramObject); if (appContext == null) appContext = appContextMap.get(paramObject);  return appContext; } public static void setLWRequestStatus(Window paramWindow, boolean paramBoolean) { AWTAccessor.getWindowAccessor().setLWRequestStatus(paramWindow, paramBoolean); } public static void checkAndSetPolicy(Container paramContainer) { FocusTraversalPolicy focusTraversalPolicy = KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalPolicy(); paramContainer.setFocusTraversalPolicy(focusTraversalPolicy); } private static FocusTraversalPolicy createLayoutPolicy() { FocusTraversalPolicy focusTraversalPolicy = null; try { Class<?> clazz = Class.forName("javax.swing.LayoutFocusTraversalPolicy"); focusTraversalPolicy = (FocusTraversalPolicy)clazz.newInstance(); } catch (ClassNotFoundException classNotFoundException) { assert false; } catch (InstantiationException instantiationException) { assert false; } catch (IllegalAccessException illegalAccessException) { assert false; }  return focusTraversalPolicy; } public static void insertTargetMapping(Object paramObject, AppContext paramAppContext) { if (!setAppContext(paramObject, paramAppContext)) appContextMap.put(paramObject, paramAppContext);  } public static void postEvent(AppContext paramAppContext, AWTEvent paramAWTEvent) { if (paramAWTEvent == null) throw new NullPointerException();  AWTAccessor.SequencedEventAccessor sequencedEventAccessor = AWTAccessor.getSequencedEventAccessor(); if (sequencedEventAccessor != null && sequencedEventAccessor.isSequencedEvent(paramAWTEvent)) { AWTEvent aWTEvent = sequencedEventAccessor.getNested(paramAWTEvent); if (aWTEvent.getID() == 208 && aWTEvent instanceof TimedWindowEvent) { TimedWindowEvent timedWindowEvent = (TimedWindowEvent)aWTEvent; ((SunToolkit)Toolkit.getDefaultToolkit()).setWindowDeactivationTime((Window)timedWindowEvent.getSource(), timedWindowEvent.getWhen()); }  }  setSystemGenerated(paramAWTEvent); AppContext appContext = targetToAppContext(paramAWTEvent.getSource()); if (appContext != null && !appContext.equals(paramAppContext)) throw new RuntimeException("Event posted on wrong app context : " + paramAWTEvent);  PostEventQueue postEventQueue = (PostEventQueue)paramAppContext.get("PostEventQueue"); if (postEventQueue != null) postEventQueue.postEvent(paramAWTEvent);  } public static void postPriorityEvent(final AWTEvent e) { PeerEvent peerEvent = new PeerEvent(Toolkit.getDefaultToolkit(), new Runnable() { public void run() { AWTAccessor.getAWTEventAccessor().setPosted(e); ((Component)e.getSource()).dispatchEvent(e); }
/*      */         },  2L); postEvent(targetToAppContext(e.getSource()), peerEvent); } public static void flushPendingEvents() { AppContext appContext = AppContext.getAppContext(); flushPendingEvents(appContext); } public static void flushPendingEvents(AppContext paramAppContext) { PostEventQueue postEventQueue = (PostEventQueue)paramAppContext.get("PostEventQueue"); if (postEventQueue != null) postEventQueue.flush();  } public static void executeOnEventHandlerThread(Object paramObject, Runnable paramRunnable) { executeOnEventHandlerThread(new PeerEvent(paramObject, paramRunnable, 1L)); } public static void executeOnEventHandlerThread(Object paramObject, Runnable paramRunnable, final long when) { executeOnEventHandlerThread(new PeerEvent(paramObject, paramRunnable, 1L) { public long getWhen() { return when; } }
/* 1945 */       ); } public static void executeOnEventHandlerThread(PeerEvent paramPeerEvent) { postEvent(targetToAppContext(paramPeerEvent.getSource()), paramPeerEvent); } public static void invokeLaterOnAppContext(AppContext paramAppContext, Runnable paramRunnable) { postEvent(paramAppContext, new PeerEvent(Toolkit.getDefaultToolkit(), paramRunnable, 1L)); } public static void executeOnEDTAndWait(Object paramObject, Runnable paramRunnable) throws InterruptedException, InvocationTargetException { if (EventQueue.isDispatchThread()) throw new Error("Cannot call executeOnEDTAndWait from any event dispatcher thread");  class AWTInvocationLock {}; AWTInvocationLock aWTInvocationLock = new AWTInvocationLock(); PeerEvent peerEvent = new PeerEvent(paramObject, paramRunnable, aWTInvocationLock, true, 1L); synchronized (aWTInvocationLock) { executeOnEventHandlerThread(peerEvent); while (!peerEvent.isDispatched()) aWTInvocationLock.wait();  }  Throwable throwable = peerEvent.getThrowable(); if (throwable != null) throw new InvocationTargetException(throwable);  } public static boolean isDispatchThreadForAppContext(Object paramObject) { AppContext appContext = targetToAppContext(paramObject); EventQueue eventQueue = (EventQueue)appContext.get(AppContext.EVENT_QUEUE_KEY); AWTAccessor.EventQueueAccessor eventQueueAccessor = AWTAccessor.getEventQueueAccessor(); return eventQueueAccessor.isDispatchThreadImpl(eventQueue); } public Dimension getScreenSize() { return new Dimension(getScreenWidth(), getScreenHeight()); } public FontMetrics getFontMetrics(Font paramFont) { return FontDesignMetrics.getMetrics(paramFont); } public String[] getFontList() { return new String[] { "Dialog", "SansSerif", "Serif", "Monospaced", "DialogInput" }; } public PanelPeer createPanel(Panel paramPanel) { return (PanelPeer)createComponent(paramPanel); } public CanvasPeer createCanvas(Canvas paramCanvas) { return (CanvasPeer)createComponent(paramCanvas); } public void disableBackgroundErase(Canvas paramCanvas) { disableBackgroundEraseImpl(paramCanvas); } public void disableBackgroundErase(Component paramComponent) { disableBackgroundEraseImpl(paramComponent); } private void disableBackgroundEraseImpl(Component paramComponent) { AWTAccessor.getComponentAccessor().setBackgroundEraseDisabled(paramComponent, true); } public static boolean getSunAwtNoerasebackground() { return ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.noerasebackground"))).booleanValue(); } public static boolean getSunAwtErasebackgroundonresize() { return ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.erasebackgroundonresize"))).booleanValue(); } static Image getImageFromHash(Toolkit paramToolkit, URL paramURL) { checkPermissions(paramURL); synchronized (urlImgCache) { String str = paramURL.toString(); Image image = (Image)urlImgCache.get(str); if (image == null) try { image = paramToolkit.createImage(new URLImageSource(paramURL)); urlImgCache.put(str, image); } catch (Exception exception) {}  return image; }  } static Image getImageFromHash(Toolkit paramToolkit, String paramString) { checkPermissions(paramString); synchronized (fileImgCache) { Image image = (Image)fileImgCache.get(paramString); if (image == null) try { image = paramToolkit.createImage(new FileImageSource(paramString)); fileImgCache.put(paramString, image); } catch (Exception exception) {}  return image; }  } public Image getImage(String paramString) { return getImageFromHash(this, paramString); } public Image getImage(URL paramURL) { return getImageFromHash(this, paramURL); } protected Image getImageWithResolutionVariant(String paramString1, String paramString2) { synchronized (fileImgCache) { Image image1 = getImageFromHash(this, paramString1); if (image1 instanceof sun.awt.image.MultiResolutionImage) return image1;  Image image2 = getImageFromHash(this, paramString2); image1 = createImageWithResolutionVariant(image1, image2); fileImgCache.put(paramString1, image1); return image1; }  } protected Image getImageWithResolutionVariant(URL paramURL1, URL paramURL2) { synchronized (urlImgCache) { Image image1 = getImageFromHash(this, paramURL1); if (image1 instanceof sun.awt.image.MultiResolutionImage) return image1;  Image image2 = getImageFromHash(this, paramURL2); image1 = createImageWithResolutionVariant(image1, image2); String str = paramURL1.toString(); urlImgCache.put(str, image1); return image1; }  } public Image createImage(String paramString) { checkPermissions(paramString); return createImage(new FileImageSource(paramString)); } public Image createImage(URL paramURL) { checkPermissions(paramURL); return createImage(new URLImageSource(paramURL)); } public Image createImage(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { return createImage(new ByteArrayImageSource(paramArrayOfbyte, paramInt1, paramInt2)); } public Image createImage(ImageProducer paramImageProducer) { return new ToolkitImage(paramImageProducer); } public static Image createImageWithResolutionVariant(Image paramImage1, Image paramImage2) { return new MultiResolutionToolkitImage(paramImage1, paramImage2); } public int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) { int i; if (!(paramImage instanceof ToolkitImage)) return 32;  ToolkitImage toolkitImage = (ToolkitImage)paramImage; if (paramInt1 == 0 || paramInt2 == 0) { i = 32; } else { i = toolkitImage.getImageRep().check(paramImageObserver); }  return (toolkitImage.check(paramImageObserver) | i) & checkResolutionVariant(paramImage, paramInt1, paramInt2, paramImageObserver); } public boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) { if (paramInt1 == 0 || paramInt2 == 0) return true;  if (!(paramImage instanceof ToolkitImage)) return true;  ToolkitImage toolkitImage = (ToolkitImage)paramImage; if (toolkitImage.hasError()) { if (paramImageObserver != null) paramImageObserver.imageUpdate(paramImage, 192, -1, -1, -1, -1);  return false; }  ImageRepresentation imageRepresentation = toolkitImage.getImageRep(); return imageRepresentation.prepare(paramImageObserver) & prepareResolutionVariant(paramImage, paramInt1, paramInt2, paramImageObserver); } private int checkResolutionVariant(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) { ToolkitImage toolkitImage = getResolutionVariant(paramImage); int i = getRVSize(paramInt1); int j = getRVSize(paramInt2); return (toolkitImage == null || toolkitImage.hasError()) ? 65535 : checkImage(toolkitImage, i, j, MultiResolutionToolkitImage.getResolutionVariantObserver(paramImage, paramImageObserver, paramInt1, paramInt2, i, j, true)); } private boolean prepareResolutionVariant(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) { ToolkitImage toolkitImage = getResolutionVariant(paramImage); int i = getRVSize(paramInt1); int j = getRVSize(paramInt2); return (toolkitImage == null || toolkitImage.hasError() || prepareImage(toolkitImage, i, j, MultiResolutionToolkitImage.getResolutionVariantObserver(paramImage, paramImageObserver, paramInt1, paramInt2, i, j, true))); } private static int getRVSize(int paramInt) { return (paramInt == -1) ? -1 : (2 * paramInt); } private static ToolkitImage getResolutionVariant(Image paramImage) { if (paramImage instanceof MultiResolutionToolkitImage) { Image image = ((MultiResolutionToolkitImage)paramImage).getResolutionVariant(); if (image instanceof ToolkitImage) return (ToolkitImage)image;  }  return null; } public synchronized void setWindowDeactivationTime(Window paramWindow, long paramLong) { AppContext appContext = getAppContext(paramWindow);
/* 1946 */     WeakHashMap<Object, Object> weakHashMap = (WeakHashMap)appContext.get(DEACTIVATION_TIMES_MAP_KEY);
/* 1947 */     if (weakHashMap == null) {
/* 1948 */       weakHashMap = new WeakHashMap<>();
/* 1949 */       appContext.put(DEACTIVATION_TIMES_MAP_KEY, weakHashMap);
/*      */     } 
/* 1951 */     weakHashMap.put(paramWindow, Long.valueOf(paramLong)); }
/*      */   protected static boolean imageCached(String paramString) { return fileImgCache.containsKey(paramString); }
/*      */   protected static boolean imageCached(URL paramURL) { String str = paramURL.toString(); return urlImgCache.containsKey(str); }
/*      */   protected static boolean imageExists(String paramString) { if (paramString != null) { checkPermissions(paramString); return (new File(paramString)).exists(); }  return false; }
/* 1955 */   protected static boolean imageExists(URL paramURL) { if (paramURL != null) { checkPermissions(paramURL); try (InputStream null = paramURL.openStream()) { return true; } catch (IOException iOException) { return false; }  }  return false; } private static void checkPermissions(String paramString) { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkRead(paramString);  } private static void checkPermissions(URL paramURL) { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) try { Permission permission = URLUtil.getConnectPermission(paramURL); if (permission != null) try { securityManager.checkPermission(permission); } catch (SecurityException securityException) { if (permission instanceof java.io.FilePermission && permission.getActions().indexOf("read") != -1) { securityManager.checkRead(permission.getName()); } else if (permission instanceof java.net.SocketPermission && permission.getActions().indexOf("connect") != -1) { securityManager.checkConnect(paramURL.getHost(), paramURL.getPort()); } else { throw securityException; }  }   } catch (IOException iOException) { securityManager.checkConnect(paramURL.getHost(), paramURL.getPort()); }   } public static BufferedImage getScaledIconImage(List<Image> paramList, int paramInt1, int paramInt2) { if (paramInt1 == 0 || paramInt2 == 0) return null;  Image image = null; int i = 0; int j = 0; double d1 = 3.0D; double d2 = 0.0D; for (Image image1 : paramList) { int k; int m; if (image1 == null) continue;  if (image1 instanceof ToolkitImage) { ImageRepresentation imageRepresentation = ((ToolkitImage)image1).getImageRep(); imageRepresentation.reconstruct(32); }  try { k = image1.getWidth(null); m = image1.getHeight(null); } catch (Exception exception) { continue; }  if (k > 0 && m > 0) { double d3 = Math.min(paramInt1 / k, paramInt2 / m); int n = 0; int i1 = 0; double d4 = 1.0D; if (d3 >= 2.0D) { d3 = Math.floor(d3); n = k * (int)d3; i1 = m * (int)d3; d4 = 1.0D - 0.5D / d3; } else if (d3 >= 1.0D) { d3 = 1.0D; n = k; i1 = m; d4 = 0.0D; } else if (d3 >= 0.75D) { d3 = 0.75D; n = k * 3 / 4; i1 = m * 3 / 4; d4 = 0.3D; } else if (d3 >= 0.6666D) { d3 = 0.6666D; n = k * 2 / 3; i1 = m * 2 / 3; d4 = 0.33D; } else { double d = Math.ceil(1.0D / d3); d3 = 1.0D / d; n = (int)Math.round(k / d); i1 = (int)Math.round(m / d); d4 = 1.0D - 1.0D / d; }  double d5 = (paramInt1 - n) / paramInt1 + (paramInt2 - i1) / paramInt2 + d4; if (d5 < d1) { d1 = d5; d2 = d3; image = image1; i = n; j = i1; }  if (d5 == 0.0D) break;  }  }  if (image == null) return null;  BufferedImage bufferedImage = new BufferedImage(paramInt1, paramInt2, 2); Graphics2D graphics2D = bufferedImage.createGraphics(); graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); try { int k = (paramInt1 - i) / 2; int m = (paramInt2 - j) / 2; graphics2D.drawImage(image, k, m, i, j, null); } finally { graphics2D.dispose(); }  return bufferedImage; } public static DataBufferInt getScaledIconData(List<Image> paramList, int paramInt1, int paramInt2) { BufferedImage bufferedImage = getScaledIconImage(paramList, paramInt1, paramInt2); if (bufferedImage == null) return null;  WritableRaster writableRaster = bufferedImage.getRaster(); DataBuffer dataBuffer = writableRaster.getDataBuffer(); return (DataBufferInt)dataBuffer; } protected EventQueue getSystemEventQueueImpl() { return getSystemEventQueueImplPP(); } static EventQueue getSystemEventQueueImplPP() { return getSystemEventQueueImplPP(AppContext.getAppContext()); } public static EventQueue getSystemEventQueueImplPP(AppContext paramAppContext) { return (EventQueue)paramAppContext.get(AppContext.EVENT_QUEUE_KEY); } public static Container getNativeContainer(Component paramComponent) { return Toolkit.getNativeContainer(paramComponent); } public static Component getHeavyweightComponent(Component paramComponent) { while (paramComponent != null && AWTAccessor.getComponentAccessor().isLightweight(paramComponent)) paramComponent = AWTAccessor.getComponentAccessor().getParent(paramComponent);  return paramComponent; } public int getFocusAcceleratorKeyMask() { return 8; } public boolean isPrintableCharacterModifiersMask(int paramInt) { return ((paramInt & 0x8) == (paramInt & 0x2)); } public boolean canPopupOverlapTaskBar() { boolean bool = true; try { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkPermission(SecurityConstants.AWT.SET_WINDOW_ALWAYS_ON_TOP_PERMISSION);  } catch (SecurityException securityException) { bool = false; }  return bool; } public Window createInputMethodWindow(String paramString, InputContext paramInputContext) { return new SimpleInputMethodWindow(paramString, paramInputContext); } public boolean enableInputMethodsForTextComponent() { return false; } public static Locale getStartupLocale() { if (startupLocale == null) { String str3, str4, str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.language", "en")); String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("user.region")); if (str2 != null) { int i = str2.indexOf('_'); if (i >= 0) { str3 = str2.substring(0, i); str4 = str2.substring(i + 1); } else { str3 = str2; str4 = ""; }  } else { str3 = AccessController.<String>doPrivileged(new GetPropertyAction("user.country", "")); str4 = AccessController.<String>doPrivileged(new GetPropertyAction("user.variant", "")); }  startupLocale = new Locale(str1, str3, str4); }  return startupLocale; } public Locale getDefaultKeyboardLocale() { return getStartupLocale(); } public WindowClosingListener getWindowClosingListener() { return this.windowClosingListener; } public void setWindowClosingListener(WindowClosingListener paramWindowClosingListener) { this.windowClosingListener = paramWindowClosingListener; } public RuntimeException windowClosingNotify(WindowEvent paramWindowEvent) { if (this.windowClosingListener != null) return this.windowClosingListener.windowClosingNotify(paramWindowEvent);  return null; } public RuntimeException windowClosingDelivered(WindowEvent paramWindowEvent) { if (this.windowClosingListener != null) return this.windowClosingListener.windowClosingDelivered(paramWindowEvent);  return null; } protected synchronized MouseInfoPeer getMouseInfoPeer() { if (mPeer == null) mPeer = new DefaultMouseInfoPeer();  return mPeer; } public static boolean needsXEmbed() { String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.awt.noxembed", "false")); if ("true".equals(str)) return false;  Toolkit toolkit = Toolkit.getDefaultToolkit(); if (toolkit instanceof SunToolkit) return ((SunToolkit)toolkit).needsXEmbedImpl();  return false; } protected boolean needsXEmbedImpl() { return false; } protected final boolean isXEmbedServerRequested() { return ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.xembedserver"))).booleanValue(); } public static boolean isModalExcludedSupported() { Toolkit toolkit = Toolkit.getDefaultToolkit(); return toolkit.isModalExclusionTypeSupported(DEFAULT_MODAL_EXCLUSION_TYPE); } protected boolean isModalExcludedSupportedImpl() { return false; } public synchronized long getWindowDeactivationTime(Window paramWindow) { AppContext appContext = getAppContext(paramWindow);
/* 1956 */     WeakHashMap weakHashMap = (WeakHashMap)appContext.get(DEACTIVATION_TIMES_MAP_KEY);
/* 1957 */     if (weakHashMap == null) {
/* 1958 */       return -1L;
/*      */     }
/* 1960 */     Long long_ = (Long)weakHashMap.get(paramWindow);
/* 1961 */     return (long_ == null) ? -1L : long_.longValue(); } public static void setModalExcluded(Window paramWindow) { if (DEFAULT_MODAL_EXCLUSION_TYPE == null) DEFAULT_MODAL_EXCLUSION_TYPE = Dialog.ModalExclusionType.APPLICATION_EXCLUDE;  paramWindow.setModalExclusionType(DEFAULT_MODAL_EXCLUSION_TYPE); } public static boolean isModalExcluded(Window paramWindow) { if (DEFAULT_MODAL_EXCLUSION_TYPE == null) DEFAULT_MODAL_EXCLUSION_TYPE = Dialog.ModalExclusionType.APPLICATION_EXCLUDE;  return (paramWindow.getModalExclusionType().compareTo(DEFAULT_MODAL_EXCLUSION_TYPE) >= 0); } public boolean isModalityTypeSupported(Dialog.ModalityType paramModalityType) { return (paramModalityType == Dialog.ModalityType.MODELESS || paramModalityType == Dialog.ModalityType.APPLICATION_MODAL); } public boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType paramModalExclusionType) { return (paramModalExclusionType == Dialog.ModalExclusionType.NO_EXCLUDE); } public void addModalityListener(ModalityListener paramModalityListener) { this.modalityListeners.add(paramModalityListener); } public void removeModalityListener(ModalityListener paramModalityListener) { this.modalityListeners.remove(paramModalityListener); } public void notifyModalityPushed(Dialog paramDialog) { notifyModalityChange(1300, paramDialog); } public void notifyModalityPopped(Dialog paramDialog) { notifyModalityChange(1301, paramDialog); } final void notifyModalityChange(int paramInt, Dialog paramDialog) { ModalityEvent modalityEvent = new ModalityEvent(paramDialog, this.modalityListeners, paramInt); modalityEvent.dispatch(); } static class ModalityListenerList implements ModalityListener {
/*      */     Vector<ModalityListener> listeners = new Vector<>(); void add(ModalityListener param1ModalityListener) { this.listeners.addElement(param1ModalityListener); } void remove(ModalityListener param1ModalityListener) { this.listeners.removeElement(param1ModalityListener); } public void modalityPushed(ModalityEvent param1ModalityEvent) { Iterator<ModalityListener> iterator = this.listeners.iterator(); while (iterator.hasNext()) ((ModalityListener)iterator.next()).modalityPushed(param1ModalityEvent);  } public void modalityPopped(ModalityEvent param1ModalityEvent) { Iterator<ModalityListener> iterator = this.listeners.iterator(); while (iterator.hasNext()) ((ModalityListener)iterator.next()).modalityPopped(param1ModalityEvent);  } } public static boolean isLightweightOrUnknown(Component paramComponent) { if (paramComponent.isLightweight() || !(getDefaultToolkit() instanceof SunToolkit)) return true;  return (!(paramComponent instanceof Button) && !(paramComponent instanceof Canvas) && !(paramComponent instanceof Checkbox) && !(paramComponent instanceof Choice) && !(paramComponent instanceof Label) && !(paramComponent instanceof List) && !(paramComponent instanceof Panel) && !(paramComponent instanceof Scrollbar) && !(paramComponent instanceof ScrollPane) && !(paramComponent instanceof TextArea) && !(paramComponent instanceof TextField) && !(paramComponent instanceof Window)); } public static class OperationTimedOut extends RuntimeException {
/*      */     public OperationTimedOut(String param1String) { super(param1String); } public OperationTimedOut() {} } public static class InfiniteLoop extends RuntimeException {} public static class IllegalThreadException extends RuntimeException {
/*      */     public IllegalThreadException(String param1String) { super(param1String); } public IllegalThreadException() {}
/*      */   } public void realSync() throws OperationTimedOut, InfiniteLoop { realSync(10000L); } public void realSync(long paramLong) throws OperationTimedOut, InfiniteLoop { if (EventQueue.isDispatchThread()) throw new IllegalThreadException("The SunToolkit.realSync() method cannot be used on the event dispatch thread (EDT).");  byte b = 0; do { sync(); byte b1 = 0; while (b1) { syncNativeQueue(paramLong); b1++; }  while (syncNativeQueue(paramLong) && b1 < 20) b1++;  if (b1 >= 20) throw new InfiniteLoop();  b1 = 0; while (b1 < 0) { waitForIdle(paramLong); b1++; }  while (waitForIdle(paramLong) && b1 < 20) b1++;  if (b1 >= 20) throw new InfiniteLoop();  b++; } while ((syncNativeQueue(paramLong) || waitForIdle(paramLong)) && b < 20); } private boolean isEQEmpty() { EventQueue eventQueue = getSystemEventQueueImpl(); return AWTAccessor.getEventQueueAccessor().noEvents(eventQueue); } protected final boolean waitForIdle(final long timeout) { flushPendingEvents(); boolean bool = isEQEmpty(); this.queueEmpty = false; this.eventDispatched = false; synchronized (this.waitLock) { postEvent(AppContext.getAppContext(), new PeerEvent(getSystemEventQueueImpl(), null, 4L) { public void dispatch() { byte b = 0; while (b) { SunToolkit.this.syncNativeQueue(timeout); b++; }  while (SunToolkit.this.syncNativeQueue(timeout) && b < 20) b++;  SunToolkit.flushPendingEvents(); synchronized (SunToolkit.this.waitLock) { SunToolkit.this.queueEmpty = SunToolkit.this.isEQEmpty(); SunToolkit.this.eventDispatched = true; SunToolkit.this.waitLock.notifyAll(); }  } }
/* 1966 */         ); try { while (!this.eventDispatched) this.waitLock.wait();  } catch (InterruptedException interruptedException) { return false; }  }  try { Thread.sleep(0L); } catch (InterruptedException interruptedException) { throw new RuntimeException("Interrupted"); }  flushPendingEvents(); synchronized (this.waitLock) { return (!this.queueEmpty || !isEQEmpty() || !bool); }  } public void showOrHideTouchKeyboard(Component paramComponent, AWTEvent paramAWTEvent) {} public static boolean isTouchKeyboardAutoShowEnabled() { return touchKeyboardAutoShowIsEnabled; } private void fireDesktopFontPropertyChanges() { setDesktopProperty("awt.font.desktophints", getDesktopFontHints()); } public static void setAAFontSettingsCondition(boolean paramBoolean) { if (paramBoolean != lastExtraCondition) { lastExtraCondition = paramBoolean; if (checkedSystemAAFontSettings) { checkedSystemAAFontSettings = false; Toolkit toolkit = Toolkit.getDefaultToolkit(); if (toolkit instanceof SunToolkit) ((SunToolkit)toolkit).fireDesktopFontPropertyChanges();  }  }  } private static RenderingHints getDesktopAAHintsByName(String paramString) { Object object = null; paramString = paramString.toLowerCase(Locale.ENGLISH); if (paramString.equals("on")) { object = RenderingHints.VALUE_TEXT_ANTIALIAS_ON; } else if (paramString.equals("gasp")) { object = RenderingHints.VALUE_TEXT_ANTIALIAS_GASP; } else if (paramString.equals("lcd") || paramString.equals("lcd_hrgb")) { object = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB; } else if (paramString.equals("lcd_hbgr")) { object = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR; } else if (paramString.equals("lcd_vrgb")) { object = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB; } else if (paramString.equals("lcd_vbgr")) { object = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR; }  if (object != null) { RenderingHints renderingHints = new RenderingHints(null); renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, object); return renderingHints; }  return null; } private static boolean useSystemAAFontSettings() { if (!checkedSystemAAFontSettings) { useSystemAAFontSettings = true; String str = null; Toolkit toolkit = Toolkit.getDefaultToolkit(); if (toolkit instanceof SunToolkit) str = AccessController.<String>doPrivileged(new GetPropertyAction("awt.useSystemAAFontSettings"));  if (str != null) { useSystemAAFontSettings = Boolean.valueOf(str).booleanValue(); if (!useSystemAAFontSettings) desktopFontHints = getDesktopAAHintsByName(str);  }  if (useSystemAAFontSettings) useSystemAAFontSettings = lastExtraCondition;  checkedSystemAAFontSettings = true; }  return useSystemAAFontSettings; } protected RenderingHints getDesktopAAHints() { return null; } public static RenderingHints getDesktopFontHints() { if (useSystemAAFontSettings()) { Toolkit toolkit = Toolkit.getDefaultToolkit(); if (toolkit instanceof SunToolkit) return ((SunToolkit)toolkit).getDesktopAAHints();  return null; }  if (desktopFontHints != null) return (RenderingHints)desktopFontHints.clone();  return null; } public static synchronized void consumeNextKeyTyped(KeyEvent paramKeyEvent) { try { AWTAccessor.getDefaultKeyboardFocusManagerAccessor().consumeNextKeyTyped((DefaultKeyboardFocusManager)KeyboardFocusManager.getCurrentKeyboardFocusManager(), paramKeyEvent); } catch (ClassCastException classCastException) { classCastException.printStackTrace(); }  } protected static void dumpPeers(PlatformLogger paramPlatformLogger) { AWTAutoShutdown.getInstance().dumpPeers(paramPlatformLogger); } public static Window getContainingWindow(Component paramComponent) { while (paramComponent != null && !(paramComponent instanceof Window)) paramComponent = paramComponent.getParent();  return (Window)paramComponent; } public static synchronized boolean getSunAwtDisableMixing() { if (sunAwtDisableMixing == null) sunAwtDisableMixing = AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.disableMixing"));  return sunAwtDisableMixing.booleanValue(); } public boolean isNativeGTKAvailable() { return false; } public boolean isWindowOpacitySupported() { return false; }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWindowShapingSupported() {
/* 1971 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWindowTranslucencySupported() {
/* 1976 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isTranslucencyCapable(GraphicsConfiguration paramGraphicsConfiguration) {
/* 1980 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSwingBackbufferTranslucencySupported() {
/* 1987 */     return false;
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
/*      */   public static boolean isContainingTopLevelOpaque(Component paramComponent) {
/* 2002 */     Window window = getContainingWindow(paramComponent);
/* 2003 */     return (window != null && window.isOpaque());
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
/*      */   public static boolean isContainingTopLevelTranslucent(Component paramComponent) {
/* 2018 */     Window window = getContainingWindow(paramComponent);
/* 2019 */     return (window != null && window.getOpacity() < 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needUpdateWindow() {
/* 2030 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberOfButtons() {
/* 2037 */     return 3;
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
/*      */   public static boolean isInstanceOf(Object paramObject, String paramString) {
/* 2055 */     if (paramObject == null) return false; 
/* 2056 */     if (paramString == null) return false;
/*      */     
/* 2058 */     return isInstanceOf(paramObject.getClass(), paramString);
/*      */   }
/*      */   
/*      */   private static boolean isInstanceOf(Class<?> paramClass, String paramString) {
/* 2062 */     if (paramClass == null) return false;
/*      */     
/* 2064 */     if (paramClass.getName().equals(paramString)) {
/* 2065 */       return true;
/*      */     }
/*      */     
/* 2068 */     for (Class<?> clazz : paramClass.getInterfaces()) {
/* 2069 */       if (clazz.getName().equals(paramString)) {
/* 2070 */         return true;
/*      */       }
/*      */     } 
/* 2073 */     return isInstanceOf(paramClass.getSuperclass(), paramString);
/*      */   }
/*      */   
/*      */   protected static LightweightFrame getLightweightFrame(Component paramComponent) {
/* 2077 */     for (; paramComponent != null; paramComponent = paramComponent.getParent()) {
/* 2078 */       if (paramComponent instanceof LightweightFrame) {
/* 2079 */         return (LightweightFrame)paramComponent;
/*      */       }
/* 2081 */       if (paramComponent instanceof Window)
/*      */       {
/* 2083 */         return null;
/*      */       }
/*      */     } 
/* 2086 */     return null;
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
/*      */   public static void setSystemGenerated(AWTEvent paramAWTEvent) {
/* 2100 */     AWTAccessor.getAWTEventAccessor().setSystemGenerated(paramAWTEvent);
/*      */   }
/*      */   
/*      */   public static boolean isSystemGenerated(AWTEvent paramAWTEvent) {
/* 2104 */     return AWTAccessor.getAWTEventAccessor().isSystemGenerated(paramAWTEvent);
/*      */   }
/*      */   
/*      */   public abstract WindowPeer createWindow(Window paramWindow) throws HeadlessException;
/*      */   
/*      */   public abstract FramePeer createFrame(Frame paramFrame) throws HeadlessException;
/*      */   
/*      */   public abstract FramePeer createLightweightFrame(LightweightFrame paramLightweightFrame) throws HeadlessException;
/*      */   
/*      */   public abstract DialogPeer createDialog(Dialog paramDialog) throws HeadlessException;
/*      */   
/*      */   public abstract ButtonPeer createButton(Button paramButton) throws HeadlessException;
/*      */   
/*      */   public abstract TextFieldPeer createTextField(TextField paramTextField) throws HeadlessException;
/*      */   
/*      */   public abstract ChoicePeer createChoice(Choice paramChoice) throws HeadlessException;
/*      */   
/*      */   public abstract LabelPeer createLabel(Label paramLabel) throws HeadlessException;
/*      */   
/*      */   public abstract ListPeer createList(List paramList) throws HeadlessException;
/*      */   
/*      */   public abstract CheckboxPeer createCheckbox(Checkbox paramCheckbox) throws HeadlessException;
/*      */   
/*      */   public abstract ScrollbarPeer createScrollbar(Scrollbar paramScrollbar) throws HeadlessException;
/*      */   
/*      */   public abstract ScrollPanePeer createScrollPane(ScrollPane paramScrollPane) throws HeadlessException;
/*      */   
/*      */   public abstract TextAreaPeer createTextArea(TextArea paramTextArea) throws HeadlessException;
/*      */   
/*      */   public abstract FileDialogPeer createFileDialog(FileDialog paramFileDialog) throws HeadlessException;
/*      */   
/*      */   public abstract MenuBarPeer createMenuBar(MenuBar paramMenuBar) throws HeadlessException;
/*      */   
/*      */   public abstract MenuPeer createMenu(Menu paramMenu) throws HeadlessException;
/*      */   
/*      */   public abstract PopupMenuPeer createPopupMenu(PopupMenu paramPopupMenu) throws HeadlessException;
/*      */   
/*      */   public abstract MenuItemPeer createMenuItem(MenuItem paramMenuItem) throws HeadlessException;
/*      */   
/*      */   public abstract CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem paramCheckboxMenuItem) throws HeadlessException;
/*      */   
/*      */   public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent paramDragGestureEvent) throws InvalidDnDOperationException;
/*      */   
/*      */   public abstract TrayIconPeer createTrayIcon(TrayIcon paramTrayIcon) throws HeadlessException, AWTException;
/*      */   
/*      */   public abstract SystemTrayPeer createSystemTray(SystemTray paramSystemTray);
/*      */   
/*      */   public abstract boolean isTraySupported();
/*      */   
/*      */   public abstract FontPeer getFontPeer(String paramString, int paramInt);
/*      */   
/*      */   public abstract RobotPeer createRobot(Robot paramRobot, GraphicsDevice paramGraphicsDevice) throws AWTException;
/*      */   
/*      */   public abstract KeyboardFocusManagerPeer getKeyboardFocusManagerPeer() throws HeadlessException;
/*      */   
/*      */   protected abstract int getScreenWidth();
/*      */   
/*      */   protected abstract int getScreenHeight();
/*      */   
/*      */   protected abstract boolean syncNativeQueue(long paramLong);
/*      */   
/*      */   public abstract void grab(Window paramWindow);
/*      */   
/*      */   public abstract void ungrab(Window paramWindow);
/*      */   
/*      */   public static native void closeSplashScreen();
/*      */   
/*      */   public abstract boolean isDesktopSupported();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/SunToolkit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */