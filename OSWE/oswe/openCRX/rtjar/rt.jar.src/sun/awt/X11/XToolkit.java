/*      */ package sun.awt.X11;
/*      */ import java.awt.AWTException;
/*      */ import java.awt.Button;
/*      */ import java.awt.Canvas;
/*      */ import java.awt.Checkbox;
/*      */ import java.awt.Choice;
/*      */ import java.awt.Component;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Label;
/*      */ import java.awt.List;
/*      */ import java.awt.MenuBar;
/*      */ import java.awt.Panel;
/*      */ import java.awt.Point;
/*      */ import java.awt.PopupMenu;
/*      */ import java.awt.PrintJob;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.ScrollPane;
/*      */ import java.awt.Scrollbar;
/*      */ import java.awt.TextArea;
/*      */ import java.awt.TextField;
/*      */ import java.awt.TrayIcon;
/*      */ import java.awt.Window;
/*      */ import java.awt.dnd.DragGestureEvent;
/*      */ import java.awt.dnd.DragGestureListener;
/*      */ import java.awt.dnd.DragSource;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.im.InputMethodHighlight;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.DisplayChangedListener;
/*      */ import sun.awt.LightweightFrame;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.X11GraphicsConfig;
/*      */ import sun.awt.X11GraphicsDevice;
/*      */ import sun.awt.X11GraphicsEnvironment;
/*      */ import sun.font.FontConfigManager;
/*      */ import sun.misc.ThreadGroupUtils;
/*      */ import sun.print.PrintJob2D;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ public final class XToolkit extends UNIXToolkit implements Runnable {
/*   61 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XToolkit");
/*   62 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("sun.awt.X11.event.XToolkit");
/*   63 */   private static final PlatformLogger timeoutTaskLog = PlatformLogger.getLogger("sun.awt.X11.timeoutTask.XToolkit");
/*   64 */   private static final PlatformLogger keyEventLog = PlatformLogger.getLogger("sun.awt.X11.kye.XToolkit");
/*   65 */   private static final PlatformLogger backingStoreLog = PlatformLogger.getLogger("sun.awt.X11.backingStore.XToolkit");
/*      */ 
/*      */   
/*      */   private static final int AWT_MULTICLICK_DEFAULT_TIME = 500;
/*      */   
/*      */   static final boolean PRIMARY_LOOP = false;
/*      */   
/*      */   static final boolean SECONDARY_LOOP = true;
/*      */   
/*   74 */   private static String awtAppClassName = null;
/*      */ 
/*      */ 
/*      */   
/*      */   XClipboard clipboard;
/*      */ 
/*      */ 
/*      */   
/*      */   XClipboard selection;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean dynamicLayoutSetting = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean areExtraMouseButtonsEnabled = true;
/*      */ 
/*      */   
/*      */   private boolean loadedXSettings;
/*      */ 
/*      */   
/*      */   private XSettings xs;
/*      */ 
/*      */   
/*   99 */   private FontConfigManager fcManager = new FontConfigManager();
/*      */   
/*      */   static int arrowCursor;
/*  102 */   static TreeMap winMap = new TreeMap<>();
/*  103 */   static HashMap specialPeerMap = new HashMap<>();
/*  104 */   static HashMap winToDispatcher = new HashMap<>();
/*      */   
/*      */   static UIDefaults uidefaults;
/*      */   static final X11GraphicsEnvironment localEnv;
/*      */   private static final X11GraphicsDevice device;
/*      */   private static final X11GraphicsConfig config;
/*      */   private static final long display;
/*      */   static int awt_multiclick_time;
/*      */   static boolean securityWarningEnabled;
/*  113 */   private static volatile int screenWidth = -1; private static volatile int screenHeight = -1;
/*      */   
/*      */   static long awt_defaultFg;
/*      */   
/*      */   private static XMouseInfoPeer xPeer;
/*      */   private static Boolean checkSTRUT;
/*      */   static Thread toolkitThread;
/*      */   private Point lastCursorPos;
/*      */   private Collection<XEventListener> listeners;
/*      */   
/*      */   static {
/*  124 */     initSecurityWarning();
/*  125 */     if (GraphicsEnvironment.isHeadless()) {
/*  126 */       localEnv = null;
/*  127 */       device = null;
/*  128 */       config = null;
/*  129 */       display = 0L;
/*      */     } else {
/*      */       
/*  132 */       localEnv = (X11GraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  133 */       device = (X11GraphicsDevice)localEnv.getDefaultScreenDevice();
/*  134 */       config = (X11GraphicsConfig)device.getDefaultConfiguration();
/*  135 */       display = device.getDisplay();
/*  136 */       setupModifierMap();
/*  137 */       initIDs();
/*  138 */       setBackingStoreType();
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
/*      */   static boolean isToolkitThread() {
/*  152 */     return (Thread.currentThread() == toolkitThread);
/*      */   }
/*      */ 
/*      */   
/*      */   static void initSecurityWarning() {
/*  157 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.runtime.version"));
/*      */     
/*  159 */     securityWarningEnabled = (str != null && str.contains("internal"));
/*      */   }
/*      */   
/*      */   static boolean isSecurityWarningEnabled() {
/*  163 */     return securityWarningEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final void awtFUnlock() {
/*  169 */     awtUnlock();
/*  170 */     awt_output_flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static UIDefaults getUIDefaults() {
/*  177 */     if (uidefaults == null) {
/*  178 */       initUIDefaults();
/*      */     }
/*  180 */     return uidefaults;
/*      */   }
/*      */   
/*      */   public void loadSystemColors(int[] paramArrayOfint) {
/*  184 */     nativeLoadSystemColors(paramArrayOfint);
/*  185 */     MotifColorUtilities.loadSystemColors(paramArrayOfint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void initUIDefaults() {
/*      */     try {
/*  195 */       SystemColor systemColor = SystemColor.text;
/*      */       
/*  197 */       XAWTLookAndFeel xAWTLookAndFeel = new XAWTLookAndFeel();
/*  198 */       uidefaults = xAWTLookAndFeel.getDefaults();
/*      */     }
/*  200 */     catch (Exception exception) {
/*      */       
/*  202 */       exception.printStackTrace();
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
/*      */   public static long getDisplay() {
/*  214 */     if (localEnv == null) {
/*  215 */       throw new AWTError("Local GraphicsEnvironment must not be null");
/*      */     }
/*  217 */     return display;
/*      */   }
/*      */   
/*      */   public static long getDefaultRootWindow() {
/*  221 */     awtLock();
/*      */     try {
/*  223 */       long l = XlibWrapper.RootWindow(getDisplay(), 
/*  224 */           XlibWrapper.DefaultScreen(getDisplay()));
/*      */       
/*  226 */       if (l == 0L) {
/*  227 */         throw new IllegalStateException("Root window must not be null");
/*      */       }
/*  229 */       return l;
/*      */     } finally {
/*  231 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   void init() {
/*  236 */     awtLock();
/*      */     try {
/*  238 */       XlibWrapper.XSupportsLocale();
/*  239 */       if (XlibWrapper.XSetLocaleModifiers("") == null) {
/*  240 */         log.finer("X locale modifiers are not supported, using default");
/*      */       }
/*  242 */       tryXKB();
/*      */       
/*  244 */       AwtScreenData awtScreenData = new AwtScreenData(getDefaultScreenData());
/*  245 */       awt_defaultFg = awtScreenData.get_blackpixel();
/*      */       
/*  247 */       arrowCursor = XlibWrapper.XCreateFontCursor(getDisplay(), 2);
/*      */       
/*  249 */       areExtraMouseButtonsEnabled = Boolean.parseBoolean(System.getProperty("sun.awt.enableExtraMouseButtons", "true"));
/*      */       
/*  251 */       System.setProperty("sun.awt.enableExtraMouseButtons", "" + areExtraMouseButtonsEnabled);
/*      */ 
/*      */       
/*  254 */       XlibWrapper.XSelectInput(getDisplay(), getDefaultRootWindow(), 131072L);
/*  255 */       addEventDispatcher(getDefaultRootWindow(), new XEventDispatcher()
/*      */           {
/*      */             public void dispatchEvent(XEvent param1XEvent) {
/*  258 */               if (param1XEvent.get_type() == 22) {
/*  259 */                 SunToolkit.awtUnlock();
/*      */                 
/*      */                 try {
/*  262 */                   ((X11GraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment())
/*  263 */                     .displayChanged();
/*      */                 } finally {
/*  265 */                   SunToolkit.awtLock();
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           });
/*      */     } finally {
/*  271 */       awtUnlock();
/*      */     } 
/*  273 */     PrivilegedAction<?> privilegedAction = () -> {
/*      */         Thread thread = new Thread(ThreadGroupUtils.getRootThreadGroup(), "XToolkt-Shutdown-Thread") {
/*      */             public void run() {
/*  276 */               XSystemTrayPeer xSystemTrayPeer = XSystemTrayPeer.getPeerInstance();
/*  277 */               if (xSystemTrayPeer != null) {
/*  278 */                 xSystemTrayPeer.dispose();
/*      */               }
/*  280 */               if (XToolkit.this.xs != null) {
/*  281 */                 ((XAWTXSettings)XToolkit.this.xs).dispose();
/*      */               }
/*  283 */               XToolkit.this.freeXKB();
/*  284 */               if (XToolkit.log.isLoggable(PlatformLogger.Level.FINE)) {
/*  285 */                 XToolkit.dumpPeers();
/*      */               }
/*      */             }
/*      */           };
/*      */         thread.setContextClassLoader(null);
/*      */         Runtime.getRuntime().addShutdownHook(thread);
/*      */         return null;
/*      */       };
/*  293 */     AccessController.doPrivileged(privilegedAction);
/*      */   }
/*      */   
/*      */   static String getCorrectXIDString(String paramString) {
/*  297 */     if (paramString != null) {
/*  298 */       return paramString.replace('.', '-');
/*      */     }
/*  300 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getAWTAppClassName() {
/*  308 */     return awtAppClassName;
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
/*      */   public ButtonPeer createButton(Button paramButton) {
/*  344 */     XButtonPeer xButtonPeer = new XButtonPeer(paramButton);
/*  345 */     targetCreatedPeer(paramButton, xButtonPeer);
/*  346 */     return xButtonPeer;
/*      */   }
/*      */   
/*      */   public FramePeer createLightweightFrame(LightweightFrame paramLightweightFrame) {
/*  350 */     XLightweightFramePeer xLightweightFramePeer = new XLightweightFramePeer(paramLightweightFrame);
/*  351 */     targetCreatedPeer(paramLightweightFrame, xLightweightFramePeer);
/*  352 */     return xLightweightFramePeer;
/*      */   }
/*      */   
/*      */   public FramePeer createFrame(Frame paramFrame) {
/*  356 */     XFramePeer xFramePeer = new XFramePeer(paramFrame);
/*  357 */     targetCreatedPeer(paramFrame, xFramePeer);
/*  358 */     return xFramePeer;
/*      */   }
/*      */ 
/*      */   
/*      */   static void addToWinMap(long paramLong, XBaseWindow paramXBaseWindow) {
/*  363 */     synchronized (winMap) {
/*  364 */       winMap.put(Long.valueOf(paramLong), paramXBaseWindow);
/*      */     } 
/*      */   }
/*      */   
/*      */   static void removeFromWinMap(long paramLong, XBaseWindow paramXBaseWindow) {
/*  369 */     synchronized (winMap) {
/*  370 */       winMap.remove(Long.valueOf(paramLong));
/*      */     } 
/*      */   }
/*      */   static XBaseWindow windowToXWindow(long paramLong) {
/*  374 */     synchronized (winMap) {
/*  375 */       return (XBaseWindow)winMap.get(Long.valueOf(paramLong));
/*      */     } 
/*      */   }
/*      */   
/*      */   static void addEventDispatcher(long paramLong, XEventDispatcher paramXEventDispatcher) {
/*  380 */     synchronized (winToDispatcher) {
/*  381 */       Long long_ = Long.valueOf(paramLong);
/*  382 */       Collection<XEventDispatcher> collection = (Collection)winToDispatcher.get(long_);
/*  383 */       if (collection == null) {
/*  384 */         collection = new Vector();
/*  385 */         winToDispatcher.put(long_, collection);
/*      */       } 
/*  387 */       collection.add(paramXEventDispatcher);
/*      */     } 
/*      */   }
/*      */   static void removeEventDispatcher(long paramLong, XEventDispatcher paramXEventDispatcher) {
/*  391 */     synchronized (winToDispatcher) {
/*  392 */       Long long_ = Long.valueOf(paramLong);
/*  393 */       Collection collection = (Collection)winToDispatcher.get(long_);
/*  394 */       if (collection != null) {
/*  395 */         collection.remove(paramXEventDispatcher);
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
/*      */   boolean getLastCursorPos(Point paramPoint) {
/*  410 */     awtLock();
/*      */     try {
/*  412 */       if (this.lastCursorPos == null) {
/*  413 */         return false;
/*      */       }
/*  415 */       paramPoint.setLocation(this.lastCursorPos);
/*  416 */       return true;
/*      */     } finally {
/*  418 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processGlobalMotionEvent(XEvent paramXEvent) {
/*  426 */     if (paramXEvent.get_type() == 6) {
/*  427 */       XMotionEvent xMotionEvent = paramXEvent.get_xmotion();
/*  428 */       awtLock();
/*      */       try {
/*  430 */         if (this.lastCursorPos == null) {
/*  431 */           this.lastCursorPos = new Point(xMotionEvent.get_x_root(), xMotionEvent.get_y_root());
/*      */         } else {
/*  433 */           this.lastCursorPos.setLocation(xMotionEvent.get_x_root(), xMotionEvent.get_y_root());
/*      */         } 
/*      */       } finally {
/*  436 */         awtUnlock();
/*      */       } 
/*  438 */     } else if (paramXEvent.get_type() == 8) {
/*      */       
/*  440 */       awtLock();
/*      */       try {
/*  442 */         this.lastCursorPos = null;
/*      */       } finally {
/*  444 */         awtUnlock();
/*      */       } 
/*  446 */     } else if (paramXEvent.get_type() == 7) {
/*      */       
/*  448 */       XCrossingEvent xCrossingEvent = paramXEvent.get_xcrossing();
/*  449 */       awtLock();
/*      */       try {
/*  451 */         if (this.lastCursorPos == null) {
/*  452 */           this.lastCursorPos = new Point(xCrossingEvent.get_x_root(), xCrossingEvent.get_y_root());
/*      */         } else {
/*  454 */           this.lastCursorPos.setLocation(xCrossingEvent.get_x_root(), xCrossingEvent.get_y_root());
/*      */         } 
/*      */       } finally {
/*  457 */         awtUnlock();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XToolkit() {
/*  466 */     this.listeners = new LinkedList<>(); if (PerformanceLogger.loggingEnabled())
/*      */       PerformanceLogger.setTime("XToolkit construction");  if (!GraphicsEnvironment.isHeadless()) { String str = null; StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace(); int i = arrayOfStackTraceElement.length - 1; if (i >= 0)
/*      */         str = arrayOfStackTraceElement[i].getClassName();  if (str == null || str.equals(""))
/*  469 */         str = "AWT";  awtAppClassName = getCorrectXIDString(str); init(); XWM.init(); toolkitThread = AccessController.<Thread>doPrivileged(() -> { Thread thread = new Thread(ThreadGroupUtils.getRootThreadGroup(), this, "AWT-XAWT"); thread.setContextClassLoader(null); thread.setPriority(6); thread.setDaemon(true); return (PrivilegedAction)thread; }); toolkitThread.start(); }  } public void addXEventListener(XEventListener paramXEventListener) { synchronized (this.listeners) {
/*  470 */       this.listeners.add(paramXEventListener);
/*      */     }  }
/*      */ 
/*      */   
/*      */   private void notifyListeners(XEvent paramXEvent) {
/*  475 */     synchronized (this.listeners) {
/*  476 */       if (this.listeners.size() == 0)
/*      */         return; 
/*  478 */       XEvent xEvent = paramXEvent.clone();
/*      */       try {
/*  480 */         for (XEventListener xEventListener : this.listeners) {
/*  481 */           xEventListener.eventProcessed(xEvent);
/*      */         }
/*      */       } finally {
/*  484 */         xEvent.dispose();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void dispatchEvent(XEvent paramXEvent) {
/*  490 */     XAnyEvent xAnyEvent = paramXEvent.get_xany();
/*      */     
/*  492 */     if (windowToXWindow(xAnyEvent.get_window()) != null && (paramXEvent
/*  493 */       .get_type() == 6 || paramXEvent.get_type() == 7 || paramXEvent.get_type() == 8))
/*      */     {
/*  495 */       processGlobalMotionEvent(paramXEvent);
/*      */     }
/*      */     
/*  498 */     if (paramXEvent.get_type() == 34) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  504 */       XlibWrapper.XRefreshKeyboardMapping(paramXEvent.pData);
/*  505 */       resetKeyboardSniffer();
/*  506 */       setupModifierMap();
/*      */     } 
/*  508 */     XBaseWindow.dispatchToWindow(paramXEvent);
/*      */     
/*  510 */     Collection<?> collection = null;
/*  511 */     synchronized (winToDispatcher) {
/*  512 */       Long long_ = Long.valueOf(xAnyEvent.get_window());
/*  513 */       collection = (Collection)winToDispatcher.get(long_);
/*  514 */       if (collection != null) {
/*  515 */         collection = new Vector(collection);
/*      */       }
/*      */     } 
/*  518 */     if (collection != null) {
/*  519 */       Iterator<?> iterator = collection.iterator();
/*  520 */       while (iterator.hasNext()) {
/*  521 */         XEventDispatcher xEventDispatcher = (XEventDispatcher)iterator.next();
/*  522 */         xEventDispatcher.dispatchEvent(paramXEvent);
/*      */       } 
/*      */     } 
/*  525 */     notifyListeners(paramXEvent);
/*      */   }
/*      */   
/*      */   static void processException(Throwable paramThrowable) {
/*  529 */     if (log.isLoggable(PlatformLogger.Level.WARNING)) {
/*  530 */       log.warning("Exception on Toolkit thread", paramThrowable);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*  537 */     awt_toolkit_init();
/*  538 */     run(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void run(boolean paramBoolean) {
/*  543 */     XEvent xEvent = new XEvent();
/*      */     
/*      */     while (true) {
/*  546 */       if (Thread.currentThread().isInterrupted())
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  552 */         if (AppContext.getAppContext().isDisposed()) {
/*      */           break;
/*      */         }
/*      */       }
/*  556 */       awtLock();
/*      */       
/*  558 */       try { if (paramBoolean == true)
/*      */         
/*      */         { 
/*      */ 
/*      */           
/*  563 */           if (!XlibWrapper.XNextSecondaryLoopEvent(getDisplay(), xEvent.pData))
/*      */           
/*      */           { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  630 */             awtUnlock(); break; }  } else { callTimeoutTasks(); while (XlibWrapper.XEventsQueued(getDisplay(), 1) == 0 && XlibWrapper.XEventsQueued(getDisplay(), 2) == 0) { callTimeoutTasks(); waitForEvents(getNextTaskTime()); }  XlibWrapper.XNextEvent(getDisplay(), xEvent.pData); }  if (xEvent.get_type() != 14) eventNumber++;  if (awt_UseXKB_Calls && xEvent.get_type() == awt_XKBBaseEventCode) processXkbChanges(xEvent);  if (XDropTargetEventProcessor.processEvent(xEvent) || XDragSourceContextPeer.processEvent(xEvent)) { awtUnlock(); continue; }  if (eventLog.isLoggable(PlatformLogger.Level.FINER)) eventLog.finer("{0}", new Object[] { xEvent });  long l = 0L; if (windowToXWindow(xEvent.get_xany().get_window()) != null) { Component component = XKeyboardFocusManagerPeer.getInstance().getCurrentFocusOwner(); if (component != null) { XWindow xWindow = AWTAccessor.getComponentAccessor().<XWindow>getPeer(component); if (xWindow != null) l = xWindow.getContentWindow();  }  }  if (keyEventLog.isLoggable(PlatformLogger.Level.FINE) && (xEvent.get_type() == 2 || xEvent.get_type() == 3)) keyEventLog.fine("before XFilterEvent:" + xEvent);  if (XlibWrapper.XFilterEvent(xEvent.getPData(), l)) { awtUnlock(); continue; }  if (keyEventLog.isLoggable(PlatformLogger.Level.FINE) && (xEvent.get_type() == 2 || xEvent.get_type() == 3)) keyEventLog.fine("after XFilterEvent:" + xEvent);  dispatchEvent(xEvent); } catch (ThreadDeath threadDeath) { XBaseWindow.ungrabInput(); return; } catch (Throwable throwable) { XBaseWindow.ungrabInput(); processException(throwable); } finally { awtUnlock(); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  638 */   private static final DisplayChangedListener displayChangedHandler = new DisplayChangedListener()
/*      */     {
/*      */       
/*      */       public void displayChanged()
/*      */       {
/*  643 */         XToolkit.screenWidth = -1;
/*  644 */         XToolkit.screenHeight = -1;
/*      */       }
/*      */ 
/*      */       
/*      */       public void paletteChanged() {}
/*      */     };
/*      */ 
/*      */   
/*      */   static {
/*  653 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  654 */     if (graphicsEnvironment instanceof SunGraphicsEnvironment) {
/*  655 */       ((SunGraphicsEnvironment)graphicsEnvironment).addDisplayChangedListener(displayChangedHandler);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void initScreenSize() {
/*  661 */     if (screenWidth == -1 || screenHeight == -1) {
/*  662 */       awtLock();
/*      */       try {
/*  664 */         XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*      */         try {
/*  666 */           XlibWrapper.XGetWindowAttributes(getDisplay(), 
/*  667 */               getDefaultRootWindow(), xWindowAttributes.pData);
/*      */           
/*  669 */           screenWidth = xWindowAttributes.get_width();
/*  670 */           screenHeight = xWindowAttributes.get_height();
/*      */         } finally {
/*  672 */           xWindowAttributes.dispose();
/*      */         } 
/*      */       } finally {
/*  675 */         awtUnlock();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static int getDefaultScreenWidth() {
/*  681 */     initScreenSize();
/*  682 */     return screenWidth;
/*      */   }
/*      */   
/*      */   static int getDefaultScreenHeight() {
/*  686 */     initScreenSize();
/*  687 */     return screenHeight;
/*      */   }
/*      */   
/*      */   protected int getScreenWidth() {
/*  691 */     return getDefaultScreenWidth();
/*      */   }
/*      */   
/*      */   protected int getScreenHeight() {
/*  695 */     return getDefaultScreenHeight();
/*      */   }
/*      */ 
/*      */   
/*      */   private static Rectangle getWorkArea(long paramLong) {
/*  700 */     XAtom xAtom = XAtom.get("_NET_WORKAREA");
/*      */     
/*  702 */     long l = Native.allocateLongArray(4);
/*      */     
/*      */     try {
/*  705 */       boolean bool = xAtom.getAtomData(paramLong, 6L, l, 4);
/*      */       
/*  707 */       if (bool)
/*      */       {
/*  709 */         int i = (int)Native.getLong(l, 0);
/*  710 */         int j = (int)Native.getLong(l, 1);
/*  711 */         int k = (int)Native.getLong(l, 2);
/*  712 */         int m = (int)Native.getLong(l, 3);
/*      */         
/*  714 */         return new Rectangle(i, j, k, m);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  719 */       XlibWrapper.unsafe.freeMemory(l);
/*      */     } 
/*      */     
/*  722 */     return null;
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
/*      */   public Insets getScreenInsets(GraphicsConfiguration paramGraphicsConfiguration) {
/*  749 */     XNETProtocol xNETProtocol = XWM.getWM().getNETProtocol();
/*  750 */     if (xNETProtocol == null || !xNETProtocol.active())
/*      */     {
/*  752 */       return super.getScreenInsets(paramGraphicsConfiguration);
/*      */     }
/*      */     
/*  755 */     awtLock();
/*      */     
/*      */     try {
/*  758 */       X11GraphicsConfig x11GraphicsConfig = (X11GraphicsConfig)paramGraphicsConfiguration;
/*  759 */       X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsConfig.getDevice();
/*  760 */       long l = XlibUtil.getRootWindow(x11GraphicsDevice.getScreen());
/*      */       
/*  762 */       X11GraphicsEnvironment x11GraphicsEnvironment = (X11GraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  763 */       if (x11GraphicsEnvironment.runningXinerama() && checkSTRUT()) {
/*      */         
/*  765 */         Rectangle rectangle = XlibUtil.getWindowGeometry(l);
/*  766 */         Insets insets = getScreenInsetsManually(l, rectangle, paramGraphicsConfiguration
/*  767 */             .getBounds());
/*  768 */         if ((insets.left | insets.top | insets.bottom | insets.right) != 0 || rectangle == null)
/*      */         {
/*  770 */           return insets;
/*      */         }
/*      */       } 
/*  773 */       Rectangle rectangle1 = getWorkArea(l);
/*  774 */       Rectangle rectangle2 = paramGraphicsConfiguration.getBounds();
/*  775 */       if (rectangle1 != null && rectangle2.contains(rectangle1.getLocation())) {
/*  776 */         rectangle1 = rectangle1.intersection(rectangle2);
/*  777 */         int i = rectangle1.y - rectangle2.y;
/*  778 */         int j = rectangle1.x - rectangle2.x;
/*  779 */         int k = rectangle2.height - rectangle1.height - i;
/*  780 */         int m = rectangle2.width - rectangle1.width - j;
/*  781 */         return new Insets(i, j, k, m);
/*      */       } 
/*      */       
/*  784 */       return new Insets(0, 0, 0, 0);
/*      */     }
/*      */     finally {
/*      */       
/*  788 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean checkSTRUT() {
/*  797 */     if (checkSTRUT == null) {
/*  798 */       checkSTRUT = AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.X11.checkSTRUT"));
/*      */     }
/*      */     
/*  801 */     return checkSTRUT.booleanValue();
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
/*      */   private Insets getScreenInsetsManually(long paramLong, Rectangle paramRectangle1, Rectangle paramRectangle2) {
/*  830 */     XAtom xAtom1 = XAtom.get("_NET_WM_STRUT");
/*  831 */     XAtom xAtom2 = XAtom.get("_NET_WM_STRUT_PARTIAL");
/*      */     
/*  833 */     Insets insets = new Insets(0, 0, 0, 0);
/*      */     
/*  835 */     LinkedList<Long> linkedList = new LinkedList();
/*  836 */     linkedList.add(Long.valueOf(paramLong));
/*  837 */     linkedList.add(Integer.valueOf(0));
/*  838 */     while (!linkedList.isEmpty()) {
/*      */       
/*  840 */       long l1 = ((Long)linkedList.remove(0)).longValue();
/*  841 */       int i = ((Integer)linkedList.remove(0)).intValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  849 */       if (XlibUtil.getWindowMapState(l1) == 0) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  854 */       long l2 = Native.allocateLongArray(4);
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  859 */         boolean bool = xAtom2.getAtomData(l1, 6L, l2, 4);
/*  860 */         if (!bool)
/*      */         {
/*  862 */           bool = xAtom1.getAtomData(l1, 6L, l2, 4);
/*      */         }
/*  864 */         if (bool) {
/*      */ 
/*      */           
/*  867 */           Rectangle rectangle = XlibUtil.getWindowGeometry(l1);
/*  868 */           if (i > 1)
/*      */           {
/*  870 */             rectangle = XlibUtil.translateCoordinates(l1, paramLong, rectangle);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  875 */           if (rectangle != null && rectangle.intersects(paramRectangle2))
/*      */           {
/*  877 */             int j = (int)Native.getLong(l2, 0);
/*  878 */             int k = (int)Native.getLong(l2, 1);
/*  879 */             int m = (int)Native.getLong(l2, 2);
/*  880 */             int n = (int)Native.getLong(l2, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  886 */             j = (paramRectangle1.x + j > paramRectangle2.x) ? (paramRectangle1.x + j - paramRectangle2.x) : 0;
/*      */             
/*  888 */             k = (paramRectangle1.x + paramRectangle1.width - k < paramRectangle2.x + paramRectangle2.width) ? (paramRectangle2.x + paramRectangle2.width - paramRectangle1.x + paramRectangle1.width - k) : 0;
/*      */ 
/*      */ 
/*      */             
/*  892 */             m = (paramRectangle1.y + m > paramRectangle2.y) ? (paramRectangle1.y + m - paramRectangle2.y) : 0;
/*      */             
/*  894 */             n = (paramRectangle1.y + paramRectangle1.height - n < paramRectangle2.y + paramRectangle2.height) ? (paramRectangle2.y + paramRectangle2.height - paramRectangle1.y + paramRectangle1.height - n) : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  899 */             insets.left = Math.max(j, insets.left);
/*  900 */             insets.right = Math.max(k, insets.right);
/*  901 */             insets.top = Math.max(m, insets.top);
/*  902 */             insets.bottom = Math.max(n, insets.bottom);
/*      */           }
/*      */         
/*      */         } 
/*      */       } finally {
/*      */         
/*  908 */         XlibWrapper.unsafe.freeMemory(l2);
/*      */       } 
/*      */       
/*  911 */       if (i < 3) {
/*      */         
/*  913 */         Set<Long> set = XlibUtil.getChildWindows(l1);
/*  914 */         for (Iterator<Long> iterator = set.iterator(); iterator.hasNext(); ) { long l = ((Long)iterator.next()).longValue();
/*      */           
/*  916 */           linkedList.add(Long.valueOf(l));
/*  917 */           linkedList.add(Integer.valueOf(i + 1)); }
/*      */       
/*      */       } 
/*      */     } 
/*      */     
/*  922 */     return insets;
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
/*      */   protected static final Object targetToPeer(Object paramObject) {
/*  947 */     Object object = null;
/*  948 */     if (paramObject != null && !GraphicsEnvironment.isHeadless()) {
/*  949 */       object = specialPeerMap.get(paramObject);
/*      */     }
/*  951 */     if (object != null) return object;
/*      */     
/*  953 */     return SunToolkit.targetToPeer(paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static final void targetDisposedPeer(Object paramObject1, Object paramObject2) {
/*  958 */     SunToolkit.targetDisposedPeer(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   public RobotPeer createRobot(Robot paramRobot, GraphicsDevice paramGraphicsDevice) {
/*  962 */     return new XRobotPeer(paramGraphicsDevice.getDefaultConfiguration());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDynamicLayout(boolean paramBoolean) {
/*  973 */     dynamicLayoutSetting = paramBoolean;
/*      */   }
/*      */   
/*      */   protected boolean isDynamicLayoutSet() {
/*  977 */     return dynamicLayoutSetting;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDynamicLayoutSupported() {
/*  984 */     return XWM.getWM().supportsDynamicLayout();
/*      */   }
/*      */   
/*      */   public boolean isDynamicLayoutActive() {
/*  988 */     return isDynamicLayoutSupported();
/*      */   }
/*      */ 
/*      */   
/*      */   public FontPeer getFontPeer(String paramString, int paramInt) {
/*  993 */     return new XFontPeer(paramString, paramInt);
/*      */   }
/*      */   
/*      */   public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent paramDragGestureEvent) throws InvalidDnDOperationException {
/*  997 */     LightweightFrame lightweightFrame = SunToolkit.getLightweightFrame(paramDragGestureEvent.getComponent());
/*  998 */     if (lightweightFrame != null) {
/*  999 */       return lightweightFrame.createDragSourceContextPeer(paramDragGestureEvent);
/*      */     }
/*      */     
/* 1002 */     return XDragSourceContextPeer.createDragSourceContextPeer(paramDragGestureEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends java.awt.dnd.DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener) {
/* 1012 */     LightweightFrame lightweightFrame = SunToolkit.getLightweightFrame(paramComponent);
/* 1013 */     if (lightweightFrame != null) {
/* 1014 */       return lightweightFrame.createDragGestureRecognizer(paramClass, paramDragSource, paramComponent, paramInt, paramDragGestureListener);
/*      */     }
/*      */     
/* 1017 */     if (MouseDragGestureRecognizer.class.equals(paramClass)) {
/* 1018 */       return (T)new XMouseDragGestureRecognizer(paramDragSource, paramComponent, paramInt, paramDragGestureListener);
/*      */     }
/* 1020 */     return null;
/*      */   }
/*      */   
/*      */   public CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem paramCheckboxMenuItem) {
/* 1024 */     return new XCheckboxMenuItemPeer(paramCheckboxMenuItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MenuItemPeer createMenuItem(MenuItem paramMenuItem) {
/* 1032 */     return new XMenuItemPeer(paramMenuItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextFieldPeer createTextField(TextField paramTextField) {
/* 1040 */     XTextFieldPeer xTextFieldPeer = new XTextFieldPeer(paramTextField);
/* 1041 */     targetCreatedPeer(paramTextField, xTextFieldPeer);
/* 1042 */     return xTextFieldPeer;
/*      */   }
/*      */   
/*      */   public LabelPeer createLabel(Label paramLabel) {
/* 1046 */     XLabelPeer xLabelPeer = new XLabelPeer(paramLabel);
/* 1047 */     targetCreatedPeer(paramLabel, xLabelPeer);
/* 1048 */     return xLabelPeer;
/*      */   }
/*      */   
/*      */   public ListPeer createList(List paramList) {
/* 1052 */     XListPeer xListPeer = new XListPeer(paramList);
/* 1053 */     targetCreatedPeer(paramList, xListPeer);
/* 1054 */     return xListPeer;
/*      */   }
/*      */   
/*      */   public CheckboxPeer createCheckbox(Checkbox paramCheckbox) {
/* 1058 */     XCheckboxPeer xCheckboxPeer = new XCheckboxPeer(paramCheckbox);
/* 1059 */     targetCreatedPeer(paramCheckbox, xCheckboxPeer);
/* 1060 */     return xCheckboxPeer;
/*      */   }
/*      */   
/*      */   public ScrollbarPeer createScrollbar(Scrollbar paramScrollbar) {
/* 1064 */     XScrollbarPeer xScrollbarPeer = new XScrollbarPeer(paramScrollbar);
/* 1065 */     targetCreatedPeer(paramScrollbar, xScrollbarPeer);
/* 1066 */     return xScrollbarPeer;
/*      */   }
/*      */   
/*      */   public ScrollPanePeer createScrollPane(ScrollPane paramScrollPane) {
/* 1070 */     XScrollPanePeer xScrollPanePeer = new XScrollPanePeer(paramScrollPane);
/* 1071 */     targetCreatedPeer(paramScrollPane, xScrollPanePeer);
/* 1072 */     return xScrollPanePeer;
/*      */   }
/*      */   
/*      */   public TextAreaPeer createTextArea(TextArea paramTextArea) {
/* 1076 */     XTextAreaPeer xTextAreaPeer = new XTextAreaPeer(paramTextArea);
/* 1077 */     targetCreatedPeer(paramTextArea, xTextAreaPeer);
/* 1078 */     return xTextAreaPeer;
/*      */   }
/*      */   
/*      */   public ChoicePeer createChoice(Choice paramChoice) {
/* 1082 */     XChoicePeer xChoicePeer = new XChoicePeer(paramChoice);
/* 1083 */     targetCreatedPeer(paramChoice, xChoicePeer);
/* 1084 */     return xChoicePeer;
/*      */   }
/*      */   
/*      */   public CanvasPeer createCanvas(Canvas paramCanvas) {
/* 1088 */     XCanvasPeer xCanvasPeer = isXEmbedServerRequested() ? new XEmbedCanvasPeer(paramCanvas) : new XCanvasPeer(paramCanvas);
/* 1089 */     targetCreatedPeer(paramCanvas, xCanvasPeer);
/* 1090 */     return xCanvasPeer;
/*      */   }
/*      */   
/*      */   public PanelPeer createPanel(Panel paramPanel) {
/* 1094 */     XPanelPeer xPanelPeer = new XPanelPeer(paramPanel);
/* 1095 */     targetCreatedPeer(paramPanel, xPanelPeer);
/* 1096 */     return xPanelPeer;
/*      */   }
/*      */   
/*      */   public WindowPeer createWindow(Window paramWindow) {
/* 1100 */     XWindowPeer xWindowPeer = new XWindowPeer(paramWindow);
/* 1101 */     targetCreatedPeer(paramWindow, xWindowPeer);
/* 1102 */     return xWindowPeer;
/*      */   }
/*      */   
/*      */   public DialogPeer createDialog(Dialog paramDialog) {
/* 1106 */     XDialogPeer xDialogPeer = new XDialogPeer(paramDialog);
/* 1107 */     targetCreatedPeer(paramDialog, xDialogPeer);
/* 1108 */     return xDialogPeer;
/*      */   }
/*      */   
/* 1111 */   private static Boolean sunAwtDisableGtkFileDialogs = null;
/*      */   static ColorModel screenmodel;
/*      */   private static boolean initialized;
/*      */   private static boolean timeStampUpdated;
/*      */   private static long timeStamp;
/*      */   
/*      */   public static synchronized boolean getSunAwtDisableGtkFileDialogs() {
/* 1118 */     if (sunAwtDisableGtkFileDialogs == null) {
/* 1119 */       sunAwtDisableGtkFileDialogs = AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.disableGtkFileDialogs"));
/*      */     }
/*      */     
/* 1122 */     return sunAwtDisableGtkFileDialogs.booleanValue();
/*      */   }
/*      */   public FileDialogPeer createFileDialog(FileDialog paramFileDialog) {
/*      */     XFileDialogPeer xFileDialogPeer;
/* 1126 */     GtkFileDialogPeer gtkFileDialogPeer = null;
/*      */     
/* 1128 */     if (!getSunAwtDisableGtkFileDialogs() && (
/* 1129 */       checkGtkVersion(2, 4, 0) || checkGtkVersion(3, 0, 0))) {
/* 1130 */       gtkFileDialogPeer = new GtkFileDialogPeer(paramFileDialog);
/*      */     } else {
/* 1132 */       xFileDialogPeer = new XFileDialogPeer(paramFileDialog);
/*      */     } 
/* 1134 */     targetCreatedPeer(paramFileDialog, xFileDialogPeer);
/* 1135 */     return xFileDialogPeer;
/*      */   }
/*      */   
/*      */   public MenuBarPeer createMenuBar(MenuBar paramMenuBar) {
/* 1139 */     XMenuBarPeer xMenuBarPeer = new XMenuBarPeer(paramMenuBar);
/* 1140 */     targetCreatedPeer(paramMenuBar, xMenuBarPeer);
/* 1141 */     return xMenuBarPeer;
/*      */   }
/*      */   
/*      */   public MenuPeer createMenu(Menu paramMenu) {
/* 1145 */     return new XMenuPeer(paramMenu);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PopupMenuPeer createPopupMenu(PopupMenu paramPopupMenu) {
/* 1153 */     XPopupMenuPeer xPopupMenuPeer = new XPopupMenuPeer(paramPopupMenu);
/* 1154 */     targetCreatedPeer(paramPopupMenu, xPopupMenuPeer);
/* 1155 */     return xPopupMenuPeer;
/*      */   }
/*      */   
/*      */   public synchronized MouseInfoPeer getMouseInfoPeer() {
/* 1159 */     if (xPeer == null) {
/* 1160 */       xPeer = new XMouseInfoPeer();
/*      */     }
/* 1162 */     return xPeer;
/*      */   }
/*      */ 
/*      */   
/*      */   public XEmbeddedFramePeer createEmbeddedFrame(XEmbeddedFrame paramXEmbeddedFrame) {
/* 1167 */     XEmbeddedFramePeer xEmbeddedFramePeer = new XEmbeddedFramePeer(paramXEmbeddedFrame);
/* 1168 */     targetCreatedPeer(paramXEmbeddedFrame, xEmbeddedFramePeer);
/* 1169 */     return xEmbeddedFramePeer;
/*      */   }
/*      */   
/*      */   XEmbedChildProxyPeer createEmbedProxy(XEmbedChildProxy paramXEmbedChildProxy) {
/* 1173 */     XEmbedChildProxyPeer xEmbedChildProxyPeer = new XEmbedChildProxyPeer(paramXEmbedChildProxy);
/* 1174 */     targetCreatedPeer(paramXEmbedChildProxy, xEmbedChildProxyPeer);
/* 1175 */     return xEmbedChildProxyPeer;
/*      */   }
/*      */   
/*      */   public KeyboardFocusManagerPeer getKeyboardFocusManagerPeer() throws HeadlessException {
/* 1179 */     return XKeyboardFocusManagerPeer.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cursor createCustomCursor(Image paramImage, Point paramPoint, String paramString) throws IndexOutOfBoundsException {
/* 1187 */     return (Cursor)new XCustomCursor(paramImage, paramPoint, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TrayIconPeer createTrayIcon(TrayIcon paramTrayIcon) throws HeadlessException, AWTException {
/* 1193 */     XTrayIconPeer xTrayIconPeer = new XTrayIconPeer(paramTrayIcon);
/* 1194 */     targetCreatedPeer(paramTrayIcon, xTrayIconPeer);
/* 1195 */     return xTrayIconPeer;
/*      */   }
/*      */   
/*      */   public SystemTrayPeer createSystemTray(SystemTray paramSystemTray) throws HeadlessException {
/* 1199 */     return new XSystemTrayPeer(paramSystemTray);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTraySupported() {
/* 1204 */     XSystemTrayPeer xSystemTrayPeer = XSystemTrayPeer.getPeerInstance();
/* 1205 */     if (xSystemTrayPeer != null) {
/* 1206 */       return xSystemTrayPeer.isAvailable();
/*      */     }
/* 1208 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public DataTransferer getDataTransferer() {
/* 1213 */     return XDataTransferer.getInstanceImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getBestCursorSize(int paramInt1, int paramInt2) {
/* 1220 */     return XCustomCursor.getBestCursorSize(
/* 1221 */         Math.max(1, paramInt1), Math.max(1, paramInt2));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaximumCursorColors() {
/* 1226 */     return 2;
/*      */   }
/*      */   
/*      */   public Map mapInputMethodHighlight(InputMethodHighlight paramInputMethodHighlight) {
/* 1230 */     return XInputMethod.mapInputMethodHighlight(paramInputMethodHighlight);
/*      */   }
/*      */   
/*      */   public boolean getLockingKeyState(int paramInt) {
/* 1234 */     if (paramInt != 20 && paramInt != 144 && paramInt != 145 && paramInt != 262)
/*      */     {
/* 1236 */       throw new IllegalArgumentException("invalid key for Toolkit.getLockingKeyState");
/*      */     }
/* 1238 */     awtLock();
/*      */     try {
/* 1240 */       return getModifierState(paramInt);
/*      */     } finally {
/* 1242 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public Clipboard getSystemClipboard() {
/* 1247 */     SecurityManager securityManager = System.getSecurityManager();
/* 1248 */     if (securityManager != null) {
/* 1249 */       securityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/*      */     }
/* 1251 */     synchronized (this) {
/* 1252 */       if (this.clipboard == null) {
/* 1253 */         this.clipboard = new XClipboard("System", "CLIPBOARD");
/*      */       }
/*      */     } 
/* 1256 */     return this.clipboard;
/*      */   }
/*      */   
/*      */   public Clipboard getSystemSelection() {
/* 1260 */     SecurityManager securityManager = System.getSecurityManager();
/* 1261 */     if (securityManager != null) {
/* 1262 */       securityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/*      */     }
/* 1264 */     synchronized (this) {
/* 1265 */       if (this.selection == null) {
/* 1266 */         this.selection = new XClipboard("Selection", "PRIMARY");
/*      */       }
/*      */     } 
/* 1269 */     return this.selection;
/*      */   }
/*      */   
/*      */   public void beep() {
/* 1273 */     awtLock();
/*      */     try {
/* 1275 */       XlibWrapper.XBell(getDisplay(), 0);
/* 1276 */       XlibWrapper.XFlush(getDisplay());
/*      */     } finally {
/* 1278 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintJob getPrintJob(Frame paramFrame, String paramString, Properties paramProperties) {
/* 1285 */     if (paramFrame == null) {
/* 1286 */       throw new NullPointerException("frame must not be null");
/*      */     }
/*      */     
/* 1289 */     PrintJob2D printJob2D = new PrintJob2D(paramFrame, paramString, paramProperties);
/*      */     
/* 1291 */     if (!printJob2D.printDialog()) {
/* 1292 */       printJob2D = null;
/*      */     }
/* 1294 */     return printJob2D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintJob getPrintJob(Frame paramFrame, String paramString, JobAttributes paramJobAttributes, PageAttributes paramPageAttributes) {
/* 1301 */     if (paramFrame == null) {
/* 1302 */       throw new NullPointerException("frame must not be null");
/*      */     }
/*      */     
/* 1305 */     PrintJob2D printJob2D = new PrintJob2D(paramFrame, paramString, paramJobAttributes, paramPageAttributes);
/*      */ 
/*      */     
/* 1308 */     if (!printJob2D.printDialog()) {
/* 1309 */       printJob2D = null;
/*      */     }
/*      */     
/* 1312 */     return printJob2D;
/*      */   }
/*      */   
/*      */   static void XSync() {
/* 1316 */     awtLock();
/*      */     try {
/* 1318 */       XlibWrapper.XSync(getDisplay(), 0);
/*      */     } finally {
/* 1320 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getScreenResolution() {
/* 1325 */     long l = getDisplay();
/* 1326 */     awtLock();
/*      */     try {
/* 1328 */       return 
/*      */         
/* 1330 */         (int)(XlibWrapper.DisplayWidth(l, XlibWrapper.DefaultScreen(l)) * 25.4D / XlibWrapper.DisplayWidthMM(l, 
/* 1331 */           XlibWrapper.DefaultScreen(l)));
/*      */     } finally {
/* 1333 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ColorModel getStaticColorModel() {
/* 1343 */     if (screenmodel == null) {
/* 1344 */       screenmodel = config.getColorModel();
/*      */     }
/* 1346 */     return screenmodel;
/*      */   }
/*      */   
/*      */   public ColorModel getColorModel() {
/* 1350 */     return getStaticColorModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputMethodDescriptor getInputMethodAdapterDescriptor() throws AWTException {
/* 1357 */     return (InputMethodDescriptor)new XInputMethodDescriptor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean enableInputMethodsForTextComponent() {
/* 1366 */     return true;
/*      */   }
/*      */   
/*      */   static int getMultiClickTime() {
/* 1370 */     if (awt_multiclick_time == 0) {
/* 1371 */       initializeMultiClickTime();
/*      */     }
/* 1373 */     return awt_multiclick_time;
/*      */   }
/*      */   static void initializeMultiClickTime() {
/* 1376 */     awtLock();
/*      */     try {
/*      */       try {
/* 1379 */         String str = XlibWrapper.XGetDefault(getDisplay(), "*", "multiClickTime");
/* 1380 */         if (str != null) {
/* 1381 */           awt_multiclick_time = (int)Long.parseLong(str);
/*      */         } else {
/* 1383 */           str = XlibWrapper.XGetDefault(getDisplay(), "OpenWindows", "MultiClickTimeout");
/*      */           
/* 1385 */           if (str != null) {
/*      */ 
/*      */ 
/*      */             
/* 1389 */             awt_multiclick_time = (int)Long.parseLong(str) * 100;
/*      */           } else {
/* 1391 */             awt_multiclick_time = 500;
/*      */           } 
/*      */         } 
/* 1394 */       } catch (NumberFormatException numberFormatException) {
/* 1395 */         awt_multiclick_time = 500;
/* 1396 */       } catch (NullPointerException nullPointerException) {
/* 1397 */         awt_multiclick_time = 500;
/*      */       } 
/*      */     } finally {
/* 1400 */       awtUnlock();
/*      */     } 
/* 1402 */     if (awt_multiclick_time == 0) {
/* 1403 */       awt_multiclick_time = 500;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFrameStateSupported(int paramInt) throws HeadlessException {
/* 1410 */     if (paramInt == 0 || paramInt == 1) {
/* 1411 */       return true;
/*      */     }
/* 1413 */     return XWM.getWM().supportsExtendedState(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   static void dumpPeers() {
/* 1418 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1419 */       log.fine("Mapped windows:");
/* 1420 */       Iterator<Map.Entry> iterator = winMap.entrySet().iterator();
/* 1421 */       while (iterator.hasNext()) {
/* 1422 */         Map.Entry entry = iterator.next();
/* 1423 */         log.fine((new StringBuilder()).append(entry.getKey()).append("->").append(entry.getValue()).toString());
/* 1424 */         if (entry.getValue() instanceof XComponentPeer) {
/* 1425 */           Component component = (Component)((XComponentPeer)entry.getValue()).getTarget();
/* 1426 */           log.fine("\ttarget: " + component);
/*      */         } 
/*      */       } 
/*      */       
/* 1430 */       SunToolkit.dumpPeers(log);
/*      */       
/* 1432 */       log.fine("Mapped special peers:");
/* 1433 */       iterator = specialPeerMap.entrySet().iterator();
/* 1434 */       while (iterator.hasNext()) {
/* 1435 */         Map.Entry entry = iterator.next();
/* 1436 */         log.fine((new StringBuilder()).append(entry.getKey()).append("->").append(entry.getValue()).toString());
/*      */       } 
/*      */       
/* 1439 */       log.fine("Mapped dispatchers:");
/* 1440 */       iterator = winToDispatcher.entrySet().iterator();
/* 1441 */       while (iterator.hasNext()) {
/* 1442 */         Map.Entry entry = iterator.next();
/* 1443 */         log.fine((new StringBuilder()).append(entry.getKey()).append("->").append(entry.getValue()).toString());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1453 */   private static final XEventDispatcher timeFetcher = new XEventDispatcher() {
/*      */       public void dispatchEvent(XEvent param1XEvent) {
/*      */         XPropertyEvent xPropertyEvent;
/* 1456 */         switch (param1XEvent.get_type()) {
/*      */           case 28:
/* 1458 */             xPropertyEvent = param1XEvent.get_xproperty();
/*      */             
/* 1460 */             SunToolkit.awtLock();
/*      */             try {
/* 1462 */               XToolkit.timeStamp = xPropertyEvent.get_time();
/* 1463 */               XToolkit.timeStampUpdated = true;
/* 1464 */               SunToolkit.awtLockNotifyAll();
/*      */             } finally {
/* 1466 */               SunToolkit.awtUnlock();
/*      */             } 
/*      */             break;
/*      */         } 
/*      */       }
/*      */     };
/*      */   private static XAtom _XA_JAVA_TIME_PROPERTY_ATOM; private static final String prefix = "DnD.Cursor."; private static final String postfix = ".32x32"; private static final String dndPrefix = "DnD."; static int altMask; static int metaMask; static int numLockMask; static int modeSwitchMask; static int modLockIsShiftLock; private static SortedMap timeoutTasks; static long reset_time_utc; static final long WRAP_TIME_MILLIS = 4294967295L; private static int backingStoreType; static final int XSUN_KP_BEHAVIOR = 1; static final int XORG_KP_BEHAVIOR = 2; static final int IS_SUN_KEYBOARD = 1; static final int IS_NONSUN_KEYBOARD = 2;
/*      */   static final int IS_KANA_KEYBOARD = 1;
/*      */   static final int IS_NONKANA_KEYBOARD = 2;
/*      */   
/*      */   static long getCurrentServerTime() {
/* 1477 */     awtLock();
/*      */     try {
/*      */       try {
/* 1480 */         if (!initialized) {
/* 1481 */           addEventDispatcher(XBaseWindow.getXAWTRootWindow().getWindow(), timeFetcher);
/*      */           
/* 1483 */           _XA_JAVA_TIME_PROPERTY_ATOM = XAtom.get("_SUNW_JAVA_AWT_TIME");
/* 1484 */           initialized = true;
/*      */         } 
/* 1486 */         timeStampUpdated = false;
/* 1487 */         XlibWrapper.XChangeProperty(getDisplay(), 
/* 1488 */             XBaseWindow.getXAWTRootWindow().getWindow(), _XA_JAVA_TIME_PROPERTY_ATOM
/* 1489 */             .getAtom(), 4L, 32, 2, 0L, 0);
/*      */ 
/*      */         
/* 1492 */         XlibWrapper.XFlush(getDisplay());
/*      */         
/* 1494 */         if (isToolkitThread()) {
/* 1495 */           XEvent xEvent = new XEvent();
/*      */           try {
/* 1497 */             XlibWrapper.XWindowEvent(getDisplay(), 
/* 1498 */                 XBaseWindow.getXAWTRootWindow().getWindow(), 4194304L, xEvent.pData);
/*      */ 
/*      */             
/* 1501 */             timeFetcher.dispatchEvent(xEvent);
/*      */           } finally {
/*      */             
/* 1504 */             xEvent.dispose();
/*      */           } 
/*      */         } else {
/*      */           
/* 1508 */           while (!timeStampUpdated) {
/* 1509 */             awtLockWait();
/*      */           }
/*      */         } 
/* 1512 */       } catch (InterruptedException interruptedException) {
/*      */         
/* 1514 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1515 */           log.fine("Catched exception, timeStamp may not be correct (ie = " + interruptedException + ")");
/*      */         }
/*      */       } 
/*      */     } finally {
/* 1519 */       awtUnlock();
/*      */     } 
/* 1521 */     return timeStamp;
/*      */   }
/*      */   protected void initializeDesktopProperties() {
/* 1524 */     this.desktopProperties.put("DnD.Autoscroll.initialDelay", 
/* 1525 */         Integer.valueOf(50));
/* 1526 */     this.desktopProperties.put("DnD.Autoscroll.interval", 
/* 1527 */         Integer.valueOf(50));
/* 1528 */     this.desktopProperties.put("DnD.Autoscroll.cursorHysteresis", 
/* 1529 */         Integer.valueOf(5));
/* 1530 */     this.desktopProperties.put("Shell.shellFolderManager", "sun.awt.shell.ShellFolderManager");
/*      */ 
/*      */     
/* 1533 */     if (!GraphicsEnvironment.isHeadless()) {
/* 1534 */       this.desktopProperties.put("awt.multiClickInterval", 
/* 1535 */           Integer.valueOf(getMultiClickTime()));
/* 1536 */       this.desktopProperties.put("awt.mouse.numButtons", 
/* 1537 */           Integer.valueOf(getNumberOfButtons()));
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
/*      */   public int getNumberOfButtons() {
/* 1551 */     awtLock();
/*      */     try {
/* 1553 */       if (numberOfButtons == 0) {
/* 1554 */         numberOfButtons = getNumberOfButtonsImpl();
/* 1555 */         numberOfButtons = (numberOfButtons > 20) ? 20 : numberOfButtons;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1560 */         if (numberOfButtons >= 5) {
/* 1561 */           numberOfButtons -= 2;
/* 1562 */         } else if (numberOfButtons == 4 || numberOfButtons == 5) {
/* 1563 */           numberOfButtons = 3;
/*      */         } 
/*      */       } 
/*      */       
/* 1567 */       return numberOfButtons;
/*      */     } finally {
/* 1569 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   static int getNumberOfButtonsForMask() {
/* 1574 */     return Math.min(5, ((SunToolkit)Toolkit.getDefaultToolkit()).getNumberOfButtons());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object lazilyLoadDesktopProperty(String paramString) {
/* 1582 */     if (paramString.startsWith("DnD.Cursor.")) {
/* 1583 */       String str = paramString.substring("DnD.Cursor.".length(), paramString.length()) + ".32x32";
/*      */       
/*      */       try {
/* 1586 */         return Cursor.getSystemCustomCursor(str);
/* 1587 */       } catch (AWTException aWTException) {
/* 1588 */         throw new RuntimeException("cannot load system cursor: " + str, aWTException);
/*      */       } 
/*      */     } 
/*      */     
/* 1592 */     if (paramString.equals("awt.dynamicLayoutSupported")) {
/* 1593 */       return Boolean.valueOf(isDynamicLayoutSupported());
/*      */     }
/*      */     
/* 1596 */     if (initXSettingsIfNeeded(paramString)) {
/* 1597 */       return this.desktopProperties.get(paramString);
/*      */     }
/*      */     
/* 1600 */     return super.lazilyLoadDesktopProperty(paramString);
/*      */   }
/*      */   
/*      */   public synchronized void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 1604 */     if (paramString == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1608 */     initXSettingsIfNeeded(paramString);
/* 1609 */     super.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean initXSettingsIfNeeded(String paramString) {
/* 1619 */     if (!this.loadedXSettings && (paramString
/* 1620 */       .startsWith("gnome.") || paramString
/* 1621 */       .equals("awt.font.desktophints") || paramString
/* 1622 */       .startsWith("DnD."))) {
/*      */       
/* 1624 */       this.loadedXSettings = true;
/* 1625 */       if (!GraphicsEnvironment.isHeadless()) {
/* 1626 */         loadXSettings();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1632 */         if (this.desktopProperties.get("awt.font.desktophints") == null) {
/* 1633 */           if (XWM.isKDE2()) {
/* 1634 */             Object object = FontConfigManager.getFontConfigAAHint();
/* 1635 */             if (object != null)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/* 1640 */               this.desktopProperties.put("fontconfig/Antialias", object);
/*      */             }
/*      */           } 
/*      */           
/* 1644 */           this.desktopProperties.put("awt.font.desktophints", 
/* 1645 */               SunToolkit.getDesktopFontHints());
/*      */         } 
/*      */         
/* 1648 */         return true;
/*      */       } 
/*      */     } 
/* 1651 */     return false;
/*      */   }
/*      */   
/*      */   private void loadXSettings() {
/* 1655 */     this.xs = new XAWTXSettings();
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
/*      */   void parseXSettings(int paramInt, Map paramMap) {
/* 1670 */     if (paramMap == null || paramMap.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1674 */     Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
/* 1675 */     while (iterator.hasNext()) {
/* 1676 */       Map.Entry entry = iterator.next();
/* 1677 */       String str = (String)entry.getKey();
/*      */       
/* 1679 */       str = "gnome." + str;
/* 1680 */       setDesktopProperty(str, entry.getValue());
/* 1681 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1682 */         log.fine("name = " + str + " value = " + entry.getValue());
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
/* 1693 */     setDesktopProperty("awt.font.desktophints", 
/* 1694 */         SunToolkit.getDesktopFontHints());
/*      */     
/* 1696 */     Integer integer = null;
/* 1697 */     synchronized (this) {
/* 1698 */       integer = (Integer)this.desktopProperties.get("gnome.Net/DndDragThreshold");
/*      */     } 
/* 1700 */     if (integer != null) {
/* 1701 */       setDesktopProperty("DnD.gestureMotionThreshold", integer);
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
/*      */   static int keysymToPrimaryKeycode(long paramLong) {
/* 1718 */     awtLock();
/*      */     try {
/* 1720 */       int i = XlibWrapper.XKeysymToKeycode(getDisplay(), paramLong);
/* 1721 */       if (i == 0) {
/* 1722 */         return 0;
/*      */       }
/* 1724 */       long l = XlibWrapper.XKeycodeToKeysym(getDisplay(), i, 0);
/* 1725 */       if (paramLong != l) {
/* 1726 */         return 0;
/*      */       }
/* 1728 */       return i;
/*      */     } finally {
/* 1730 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   static boolean getModifierState(int paramInt) {
/* 1734 */     int i = 0;
/* 1735 */     long l = XKeysym.javaKeycode2Keysym(paramInt);
/* 1736 */     int j = XlibWrapper.XKeysymToKeycode(getDisplay(), l);
/* 1737 */     if (j == 0) {
/* 1738 */       return false;
/*      */     }
/* 1740 */     awtLock();
/*      */     
/*      */     try {
/* 1743 */       XModifierKeymap xModifierKeymap = new XModifierKeymap(XlibWrapper.XGetModifierMapping(getDisplay()));
/*      */       
/* 1745 */       int k = xModifierKeymap.get_max_keypermod();
/*      */       
/* 1747 */       long l1 = xModifierKeymap.get_modifiermap(); byte b;
/* 1748 */       for (b = 0; b < 8; b++) {
/* 1749 */         for (byte b1 = 0; b1 < k; b1++) {
/* 1750 */           short s = Native.getUByte(l1, b * k + b1);
/* 1751 */           if (s != 0)
/*      */           {
/*      */             
/* 1754 */             if (j == s) {
/* 1755 */               i = 1 << b;
/*      */               break;
/*      */             }  } 
/*      */         } 
/* 1759 */         if (i != 0) {
/*      */           break;
/*      */         }
/*      */       } 
/* 1763 */       XlibWrapper.XFreeModifiermap(xModifierKeymap.pData);
/* 1764 */       if (i == 0) {
/* 1765 */         b = 0; return b;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1771 */       long l2 = 0L;
/*      */       
/*      */       try {
/* 1774 */         l2 = ((Long)winMap.firstKey()).longValue();
/* 1775 */       } catch (NoSuchElementException noSuchElementException) {
/*      */         
/* 1777 */         l2 = getDefaultRootWindow();
/*      */       } 
/* 1779 */       boolean bool = XlibWrapper.XQueryPointer(getDisplay(), l2, XlibWrapper.larg1, XlibWrapper.larg2, XlibWrapper.larg3, XlibWrapper.larg4, XlibWrapper.larg5, XlibWrapper.larg6, XlibWrapper.larg7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1787 */       int m = Native.getInt(XlibWrapper.larg7);
/* 1788 */       return ((m & i) != 0);
/*      */     } finally {
/* 1790 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setupModifierMap() {
/* 1798 */     int i = keysymToPrimaryKeycode(65511L);
/* 1799 */     int j = keysymToPrimaryKeycode(65512L);
/* 1800 */     int k = keysymToPrimaryKeycode(65513L);
/* 1801 */     int m = keysymToPrimaryKeycode(65514L);
/* 1802 */     int n = keysymToPrimaryKeycode(65407L);
/* 1803 */     int i1 = keysymToPrimaryKeycode(65406L);
/* 1804 */     int i2 = keysymToPrimaryKeycode(65510L);
/* 1805 */     int i3 = keysymToPrimaryKeycode(65509L);
/*      */     
/* 1807 */     int[] arrayOfInt = { 1, 2, 4, 8, 16, 32, 64, 128 };
/*      */ 
/*      */     
/* 1810 */     log.fine("In setupModifierMap");
/* 1811 */     awtLock();
/*      */     
/*      */     try {
/* 1814 */       XModifierKeymap xModifierKeymap = new XModifierKeymap(XlibWrapper.XGetModifierMapping(getDisplay()));
/*      */       
/* 1816 */       int i4 = xModifierKeymap.get_max_keypermod();
/*      */       
/* 1818 */       long l = xModifierKeymap.get_modifiermap();
/*      */       
/* 1820 */       byte b = 3;
/* 1821 */       for (; b <= 7; 
/* 1822 */         b++) {
/*      */         
/* 1824 */         for (byte b1 = 0; b1 < i4; b1++) {
/*      */           
/* 1826 */           short s = Native.getUByte(l, b * i4 + b1);
/*      */           
/* 1828 */           if (s == 0) {
/*      */             break;
/*      */           }
/* 1831 */           if (metaMask == 0 && (s == i || s == j)) {
/*      */ 
/*      */             
/* 1834 */             metaMask = arrayOfInt[b];
/*      */             break;
/*      */           } 
/* 1837 */           if (altMask == 0 && (s == k || s == m)) {
/* 1838 */             altMask = arrayOfInt[b];
/*      */             break;
/*      */           } 
/* 1841 */           if (numLockMask == 0 && s == n) {
/* 1842 */             numLockMask = arrayOfInt[b];
/*      */             break;
/*      */           } 
/* 1845 */           if (modeSwitchMask == 0 && s == i1) {
/* 1846 */             modeSwitchMask = arrayOfInt[b];
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1852 */       modLockIsShiftLock = 0;
/* 1853 */       for (b = 0; b < i4; b++) {
/* 1854 */         short s = Native.getUByte(l, 1 * i4 + b);
/* 1855 */         if (s == 0) {
/*      */           break;
/*      */         }
/* 1858 */         if (s == i2) {
/* 1859 */           modLockIsShiftLock = 1;
/*      */           break;
/*      */         } 
/* 1862 */         if (s == i3) {
/*      */           break;
/*      */         }
/*      */       } 
/* 1866 */       XlibWrapper.XFreeModifiermap(xModifierKeymap.pData);
/*      */     } finally {
/* 1868 */       awtUnlock();
/*      */     } 
/* 1870 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1871 */       log.fine("metaMask = " + metaMask);
/* 1872 */       log.fine("altMask = " + altMask);
/* 1873 */       log.fine("numLockMask = " + numLockMask);
/* 1874 */       log.fine("modeSwitchMask = " + modeSwitchMask);
/* 1875 */       log.fine("modLockIsShiftLock = " + modLockIsShiftLock);
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
/*      */   static void remove(Runnable paramRunnable) {
/* 1887 */     if (paramRunnable == null) {
/* 1888 */       throw new NullPointerException("task is null");
/*      */     }
/* 1890 */     awtLock();
/*      */     try {
/* 1892 */       if (timeoutTaskLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1893 */         timeoutTaskLog.finer("Removing task " + paramRunnable);
/*      */       }
/* 1895 */       if (timeoutTasks == null) {
/* 1896 */         if (timeoutTaskLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1897 */           timeoutTaskLog.finer("Task is not scheduled");
/*      */         }
/*      */         return;
/*      */       } 
/* 1901 */       Collection collection = timeoutTasks.values();
/* 1902 */       Iterator<List> iterator = collection.iterator();
/* 1903 */       while (iterator.hasNext()) {
/* 1904 */         List list = iterator.next();
/* 1905 */         boolean bool = false;
/* 1906 */         if (list.contains(paramRunnable)) {
/* 1907 */           list.remove(paramRunnable);
/* 1908 */           if (list.isEmpty()) {
/* 1909 */             iterator.remove();
/*      */           }
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 1915 */       awtUnlock();
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
/*      */   static void schedule(Runnable paramRunnable, long paramLong) {
/* 1934 */     if (paramRunnable == null) {
/* 1935 */       throw new NullPointerException("task is null");
/*      */     }
/* 1937 */     if (paramLong <= 0L) {
/* 1938 */       throw new IllegalArgumentException("interval " + paramLong + " is not positive");
/*      */     }
/*      */     
/* 1941 */     awtLock();
/*      */     try {
/* 1943 */       if (timeoutTaskLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1944 */         timeoutTaskLog.finer("XToolkit.schedule(): current time={0};  interval={1};  task being added={2};  tasks before addition={3}", new Object[] {
/*      */ 
/*      */               
/* 1947 */               Long.valueOf(System.currentTimeMillis()), Long.valueOf(paramLong), paramRunnable, timeoutTasks
/*      */             });
/*      */       }
/* 1950 */       if (timeoutTasks == null) {
/* 1951 */         timeoutTasks = new TreeMap<>();
/*      */       }
/*      */       
/* 1954 */       Long long_ = Long.valueOf(System.currentTimeMillis() + paramLong);
/* 1955 */       List<Runnable> list = (List)timeoutTasks.get(long_);
/* 1956 */       if (list == null) {
/* 1957 */         list = new ArrayList(1);
/* 1958 */         timeoutTasks.put(long_, list);
/*      */       } 
/* 1960 */       list.add(paramRunnable);
/*      */ 
/*      */       
/* 1963 */       if (timeoutTasks.get(timeoutTasks.firstKey()) == list && list.size() == 1)
/*      */       {
/*      */         
/* 1966 */         wakeup_poll();
/*      */       }
/*      */     } finally {
/* 1969 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   private long getNextTaskTime() {
/* 1974 */     awtLock();
/*      */     try {
/* 1976 */       if (timeoutTasks == null || timeoutTasks.isEmpty()) {
/* 1977 */         return -1L;
/*      */       }
/* 1979 */       return ((Long)timeoutTasks.firstKey()).longValue();
/*      */     } finally {
/* 1981 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void callTimeoutTasks() {
/* 1990 */     if (timeoutTaskLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1991 */       timeoutTaskLog.finer("XToolkit.callTimeoutTasks(): current time={0};  tasks={1}", new Object[] {
/* 1992 */             Long.valueOf(System.currentTimeMillis()), timeoutTasks
/*      */           });
/*      */     }
/* 1995 */     if (timeoutTasks == null || timeoutTasks.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1999 */     Long long_1 = Long.valueOf(System.currentTimeMillis());
/* 2000 */     Long long_2 = (Long)timeoutTasks.firstKey();
/*      */     
/* 2002 */     while (long_2.compareTo(long_1) <= 0) {
/* 2003 */       List list = (List)timeoutTasks.remove(long_2);
/*      */       
/* 2005 */       for (Runnable runnable : list) {
/*      */ 
/*      */         
/* 2008 */         if (timeoutTaskLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 2009 */           timeoutTaskLog.finer("XToolkit.callTimeoutTasks(): current time={0};  about to run task={1}", new Object[] {
/* 2010 */                 Long.valueOf(long_1.longValue()), runnable
/*      */               });
/*      */         }
/*      */         try {
/* 2014 */           runnable.run();
/* 2015 */         } catch (ThreadDeath threadDeath) {
/* 2016 */           throw threadDeath;
/* 2017 */         } catch (Throwable throwable) {
/* 2018 */           processException(throwable);
/*      */         } 
/*      */       } 
/*      */       
/* 2022 */       if (timeoutTasks.isEmpty()) {
/*      */         break;
/*      */       }
/* 2025 */       long_2 = (Long)timeoutTasks.firstKey();
/*      */     } 
/*      */   }
/*      */   
/*      */   static long getAwtDefaultFg() {
/* 2030 */     return awt_defaultFg;
/*      */   }
/*      */   
/*      */   static boolean isLeftMouseButton(MouseEvent paramMouseEvent) {
/* 2034 */     switch (paramMouseEvent.getID()) {
/*      */       case 501:
/*      */       case 502:
/* 2037 */         return (paramMouseEvent.getButton() == 1);
/*      */       case 500:
/*      */       case 504:
/*      */       case 505:
/*      */       case 506:
/* 2042 */         return ((paramMouseEvent.getModifiersEx() & 0x400) != 0);
/*      */     } 
/* 2044 */     return false;
/*      */   }
/*      */   
/*      */   static boolean isRightMouseButton(MouseEvent paramMouseEvent) {
/* 2048 */     int i = ((Integer)getDefaultToolkit().getDesktopProperty("awt.mouse.numButtons")).intValue();
/* 2049 */     switch (paramMouseEvent.getID()) {
/*      */       case 501:
/*      */       case 502:
/* 2052 */         return ((i == 2 && paramMouseEvent.getButton() == 2) || (i > 2 && paramMouseEvent
/* 2053 */           .getButton() == 3));
/*      */       case 500:
/*      */       case 504:
/*      */       case 505:
/*      */       case 506:
/* 2058 */         return ((i == 2 && (paramMouseEvent.getModifiersEx() & 0x800) != 0) || (i > 2 && (paramMouseEvent
/* 2059 */           .getModifiersEx() & 0x1000) != 0));
/*      */     } 
/* 2061 */     return false;
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
/*      */   static long nowMillisUTC_offset(long paramLong) {
/* 2082 */     long l = System.currentTimeMillis();
/* 2083 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 2084 */       log.finer("reset_time=" + reset_time_utc + ", current_time=" + l + ", server_offset=" + paramLong + ", wrap_time=" + 4294967295L);
/*      */     }
/*      */ 
/*      */     
/* 2088 */     if (l - reset_time_utc > 4294967295L) {
/* 2089 */       reset_time_utc = System.currentTimeMillis() - getCurrentServerTime();
/*      */     }
/*      */     
/* 2092 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 2093 */       log.finer("result = " + (reset_time_utc + paramLong));
/*      */     }
/* 2095 */     return reset_time_utc + paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean needsXEmbedImpl() {
/* 2104 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isModalityTypeSupported(Dialog.ModalityType paramModalityType) {
/* 2108 */     return (paramModalityType == null || paramModalityType == Dialog.ModalityType.MODELESS || paramModalityType == Dialog.ModalityType.DOCUMENT_MODAL || paramModalityType == Dialog.ModalityType.APPLICATION_MODAL || paramModalityType == Dialog.ModalityType.TOOLKIT_MODAL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType paramModalExclusionType) {
/* 2116 */     return (paramModalExclusionType == null || paramModalExclusionType == Dialog.ModalExclusionType.NO_EXCLUDE || paramModalExclusionType == Dialog.ModalExclusionType.APPLICATION_EXCLUDE || paramModalExclusionType == Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static EventQueue getEventQueue(Object paramObject) {
/* 2123 */     AppContext appContext = targetToAppContext(paramObject);
/* 2124 */     if (appContext != null) {
/* 2125 */       return (EventQueue)appContext.get(AppContext.EVENT_QUEUE_KEY);
/*      */     }
/* 2127 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void removeSourceEvents(EventQueue paramEventQueue, Object paramObject, boolean paramBoolean) {
/* 2133 */     AWTAccessor.getEventQueueAccessor()
/* 2134 */       .removeSourceEvents(paramEventQueue, paramObject, paramBoolean);
/*      */   }
/*      */   
/*      */   public boolean isAlwaysOnTopSupported() {
/* 2138 */     for (XLayerProtocol xLayerProtocol : XWM.getWM().<XLayerProtocol>getProtocols(XLayerProtocol.class)) {
/* 2139 */       if (xLayerProtocol.supportsLayer(1)) {
/* 2140 */         return true;
/*      */       }
/*      */     } 
/* 2143 */     return false;
/*      */   }
/*      */   
/*      */   public boolean useBufferPerWindow() {
/* 2147 */     return (getBackingStoreType() == 0);
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
/*      */   static int getBackingStoreType() {
/* 2162 */     return backingStoreType;
/*      */   }
/*      */   
/*      */   private static void setBackingStoreType() {
/* 2166 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.awt.backingStore"));
/*      */ 
/*      */     
/* 2169 */     if (str == null) {
/* 2170 */       backingStoreType = 0;
/* 2171 */       if (backingStoreLog.isLoggable(PlatformLogger.Level.CONFIG)) {
/* 2172 */         backingStoreLog.config("The system property sun.awt.backingStore is not set, by default backingStore=NotUseful");
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2178 */     if (backingStoreLog.isLoggable(PlatformLogger.Level.CONFIG)) {
/* 2179 */       backingStoreLog.config("The system property sun.awt.backingStore is " + str);
/*      */     }
/* 2181 */     str = str.toLowerCase();
/* 2182 */     if (str.equals("always")) {
/* 2183 */       backingStoreType = 2;
/* 2184 */     } else if (str.equals("whenmapped")) {
/* 2185 */       backingStoreType = 1;
/*      */     } else {
/* 2187 */       backingStoreType = 0;
/*      */     } 
/*      */     
/* 2190 */     if (backingStoreLog.isLoggable(PlatformLogger.Level.CONFIG)) {
/* 2191 */       backingStoreLog.config("backingStore(as provided by the system property)=" + ((backingStoreType == 0) ? "NotUseful" : ((backingStoreType == 1) ? "WhenMapped" : "Always")));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2197 */     if (X11SurfaceData.isDgaAvailable()) {
/* 2198 */       backingStoreType = 0;
/*      */       
/* 2200 */       if (backingStoreLog.isLoggable(PlatformLogger.Level.CONFIG)) {
/* 2201 */         backingStoreLog.config("DGA is available, backingStore=NotUseful");
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2207 */     awtLock();
/*      */     try {
/* 2209 */       int i = XlibWrapper.ScreenCount(getDisplay());
/* 2210 */       for (byte b = 0; b < i; b++) {
/* 2211 */         if (XlibWrapper.DoesBackingStore(XlibWrapper.ScreenOfDisplay(getDisplay(), b)) == 0) {
/*      */           
/* 2213 */           backingStoreType = 0;
/*      */           
/* 2215 */           if (backingStoreLog.isLoggable(PlatformLogger.Level.CONFIG)) {
/* 2216 */             backingStoreLog.config("Backing store is not available on the screen " + b + ", backingStore=NotUseful");
/*      */           }
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 2224 */       awtUnlock();
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
/* 2241 */   static int awt_IsXsunKPBehavior = 0;
/*      */   static boolean awt_UseXKB = false;
/*      */   static boolean awt_UseXKB_Calls = false;
/* 2244 */   static int awt_XKBBaseEventCode = 0;
/* 2245 */   static int awt_XKBEffectiveGroup = 0;
/*      */   
/* 2247 */   static long awt_XKBDescPtr = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isXsunKPBehavior() {
/* 2255 */     awtLock();
/*      */     try {
/* 2257 */       if (awt_IsXsunKPBehavior == 0) {
/* 2258 */         if (XlibWrapper.IsXsunKPBehavior(getDisplay())) {
/* 2259 */           awt_IsXsunKPBehavior = 1;
/*      */         } else {
/* 2261 */           awt_IsXsunKPBehavior = 2;
/*      */         } 
/*      */       }
/* 2264 */       return (awt_IsXsunKPBehavior == 1);
/*      */     } finally {
/* 2266 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/* 2270 */   static int sunOrNotKeyboard = 0;
/* 2271 */   static int kanaOrNotKeyboard = 0; private static long eventNumber; private static XEventDispatcher oops_waiter; private static boolean oops_updated;
/*      */   static void resetKeyboardSniffer() {
/* 2273 */     sunOrNotKeyboard = 0;
/* 2274 */     kanaOrNotKeyboard = 0;
/*      */   }
/*      */   static boolean isSunKeyboard() {
/* 2277 */     if (sunOrNotKeyboard == 0) {
/* 2278 */       if (XlibWrapper.IsSunKeyboard(getDisplay())) {
/* 2279 */         sunOrNotKeyboard = 1;
/*      */       } else {
/* 2281 */         sunOrNotKeyboard = 2;
/*      */       } 
/*      */     }
/* 2284 */     return (sunOrNotKeyboard == 1);
/*      */   }
/*      */   static boolean isKanaKeyboard() {
/* 2287 */     if (kanaOrNotKeyboard == 0) {
/* 2288 */       if (XlibWrapper.IsKanaKeyboard(getDisplay())) {
/* 2289 */         kanaOrNotKeyboard = 1;
/*      */       } else {
/* 2291 */         kanaOrNotKeyboard = 2;
/*      */       } 
/*      */     }
/* 2294 */     return (kanaOrNotKeyboard == 1);
/*      */   }
/*      */   static boolean isXKBenabled() {
/* 2297 */     awtLock();
/*      */     try {
/* 2299 */       return awt_UseXKB;
/*      */     } finally {
/* 2301 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean tryXKB() {
/* 2310 */     awtLock();
/*      */     try {
/* 2312 */       String str = "XKEYBOARD";
/*      */       
/* 2314 */       awt_UseXKB = XlibWrapper.XQueryExtension(getDisplay(), str, XlibWrapper.larg1, XlibWrapper.larg2, XlibWrapper.larg3);
/* 2315 */       if (awt_UseXKB) {
/*      */ 
/*      */ 
/*      */         
/* 2319 */         awt_UseXKB_Calls = XlibWrapper.XkbLibraryVersion(XlibWrapper.larg1, XlibWrapper.larg2);
/* 2320 */         if (awt_UseXKB_Calls) {
/* 2321 */           awt_UseXKB_Calls = XlibWrapper.XkbQueryExtension(getDisplay(), XlibWrapper.larg1, XlibWrapper.larg2, XlibWrapper.larg3, XlibWrapper.larg4, XlibWrapper.larg5);
/*      */           
/* 2323 */           if (awt_UseXKB_Calls) {
/* 2324 */             awt_XKBBaseEventCode = Native.getInt(XlibWrapper.larg2);
/* 2325 */             XlibWrapper.XkbSelectEvents(getDisplay(), 256L, 3L, 3L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2334 */             XlibWrapper.XkbSelectEventDetails(getDisplay(), 256L, 2L, 16L, 16L);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2339 */             awt_XKBDescPtr = XlibWrapper.XkbGetMap(getDisplay(), 71L, 256L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2346 */             XlibWrapper.XkbSetDetectableAutoRepeat(getDisplay(), true);
/*      */           } 
/*      */         } 
/*      */       } 
/* 2350 */       return awt_UseXKB;
/*      */     } finally {
/* 2352 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   static boolean canUseXKBCalls() {
/* 2356 */     awtLock();
/*      */     try {
/* 2358 */       return awt_UseXKB_Calls;
/*      */     } finally {
/* 2360 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   static int getXKBEffectiveGroup() {
/* 2364 */     awtLock();
/*      */     try {
/* 2366 */       return awt_XKBEffectiveGroup;
/*      */     } finally {
/* 2368 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   static int getXKBBaseEventCode() {
/* 2372 */     awtLock();
/*      */     try {
/* 2374 */       return awt_XKBBaseEventCode;
/*      */     } finally {
/* 2376 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   static long getXKBKbdDesc() {
/* 2380 */     awtLock();
/*      */     try {
/* 2382 */       return awt_XKBDescPtr;
/*      */     } finally {
/* 2384 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   void freeXKB() {
/* 2388 */     awtLock();
/*      */     try {
/* 2390 */       if (awt_UseXKB_Calls && awt_XKBDescPtr != 0L) {
/* 2391 */         XlibWrapper.XkbFreeKeyboard(awt_XKBDescPtr, 255L, true);
/* 2392 */         awt_XKBDescPtr = 0L;
/*      */       } 
/*      */     } finally {
/* 2395 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void processXkbChanges(XEvent paramXEvent) {
/* 2402 */     XkbEvent xkbEvent = new XkbEvent(paramXEvent.getPData());
/* 2403 */     int i = xkbEvent.get_any().get_xkb_type();
/* 2404 */     switch (i) {
/*      */       case 0:
/* 2406 */         if (awt_XKBDescPtr != 0L) {
/* 2407 */           freeXKB();
/*      */         }
/* 2409 */         awt_XKBDescPtr = XlibWrapper.XkbGetMap(getDisplay(), 71L, 256L);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 2418 */         if (awt_XKBDescPtr != 0L)
/*      */         {
/* 2420 */           XlibWrapper.XkbGetUpdatedMap(getDisplay(), 71L, awt_XKBDescPtr);
/*      */         }
/*      */         break;
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
/*      */   public static long getEventNumber() {
/* 2441 */     awtLock();
/*      */     try {
/* 2443 */       return eventNumber;
/*      */     } finally {
/* 2445 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2451 */   private static int oops_position = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean syncNativeQueue(long paramLong) {
/* 2457 */     XRootWindow xRootWindow = XBaseWindow.getXAWTRootWindow();
/*      */     
/* 2459 */     if (oops_waiter == null) {
/* 2460 */       oops_waiter = new XEventDispatcher() {
/*      */           public void dispatchEvent(XEvent param1XEvent) {
/* 2462 */             if (param1XEvent.get_type() == 22) {
/*      */               
/* 2464 */               XToolkit.oops_updated = true;
/* 2465 */               SunToolkit.awtLockNotifyAll();
/*      */             } 
/*      */           }
/*      */         };
/*      */     }
/*      */     
/* 2471 */     awtLock();
/*      */     try {
/* 2473 */       addEventDispatcher(xRootWindow.getWindow(), oops_waiter);
/*      */       
/* 2475 */       oops_updated = false;
/* 2476 */       long l1 = getEventNumber();
/*      */       
/* 2478 */       XlibWrapper.XMoveWindow(getDisplay(), xRootWindow.getWindow(), ++oops_position, 0);
/*      */       
/* 2480 */       if (oops_position > 50) {
/* 2481 */         oops_position = 0;
/*      */       }
/*      */       
/* 2484 */       XSync();
/*      */       
/* 2486 */       eventLog.finer("Generated OOPS ConfigureNotify event");
/*      */       
/* 2488 */       long l2 = System.currentTimeMillis();
/* 2489 */       while (!oops_updated) {
/*      */         
/*      */         try {
/* 2492 */           awtLockWait(paramLong);
/* 2493 */         } catch (InterruptedException interruptedException) {
/* 2494 */           throw new RuntimeException(interruptedException);
/*      */         } 
/*      */ 
/*      */         
/* 2498 */         if (System.currentTimeMillis() - l2 > paramLong && paramLong >= 0L) {
/* 2499 */           throw new SunToolkit.OperationTimedOut(Long.toString(System.currentTimeMillis() - l2));
/*      */         }
/*      */       } 
/*      */       
/* 2503 */       return (getEventNumber() - l1 > 1L);
/*      */     } finally {
/* 2505 */       removeEventDispatcher(xRootWindow.getWindow(), oops_waiter);
/* 2506 */       eventLog.finer("Exiting syncNativeQueue");
/* 2507 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   public void grab(Window paramWindow) {
/* 2511 */     if (paramWindow.getPeer() != null) {
/* 2512 */       ((XWindowPeer)paramWindow.getPeer()).setGrab(true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void ungrab(Window paramWindow) {
/* 2517 */     if (paramWindow.getPeer() != null) {
/* 2518 */       ((XWindowPeer)paramWindow.getPeer()).setGrab(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDesktopSupported() {
/* 2529 */     return XDesktopPeer.isDesktopSupported();
/*      */   }
/*      */   
/*      */   public DesktopPeer createDesktopPeer(Desktop paramDesktop) {
/* 2533 */     return new XDesktopPeer();
/*      */   }
/*      */   
/*      */   public boolean areExtraMouseButtonsEnabled() throws HeadlessException {
/* 2537 */     return areExtraMouseButtonsEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWindowOpacitySupported() {
/* 2542 */     XNETProtocol xNETProtocol = XWM.getWM().getNETProtocol();
/*      */     
/* 2544 */     if (xNETProtocol == null) {
/* 2545 */       return false;
/*      */     }
/*      */     
/* 2548 */     return xNETProtocol.doOpacityProtocol();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWindowShapingSupported() {
/* 2553 */     return XlibUtil.isShapingSupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWindowTranslucencySupported() {
/* 2561 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTranslucencyCapable(GraphicsConfiguration paramGraphicsConfiguration) {
/* 2566 */     if (!(paramGraphicsConfiguration instanceof X11GraphicsConfig)) {
/* 2567 */       return false;
/*      */     }
/* 2569 */     return ((X11GraphicsConfig)paramGraphicsConfiguration).isTranslucencyCapable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getSunAwtDisableGrab() {
/* 2577 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.disablegrab"))).booleanValue();
/*      */   }
/*      */   
/*      */   static native long getTrayIconDisplayTimeout();
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   static native void waitForEvents(long paramLong);
/*      */   
/*      */   static native void awt_output_flush();
/*      */   
/*      */   private native void nativeLoadSystemColors(int[] paramArrayOfint);
/*      */   
/*      */   static native String getEnv(String paramString);
/*      */   
/*      */   static native void awt_toolkit_init();
/*      */   
/*      */   static native long getDefaultXColormap();
/*      */   
/*      */   static native long getDefaultScreenData();
/*      */   
/*      */   private native int getNumberOfButtonsImpl();
/*      */   
/*      */   static native void wakeup_poll();
/*      */   
/*      */   public static interface XEventListener {
/*      */     void eventProcessed(XEvent param1XEvent);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XToolkit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */