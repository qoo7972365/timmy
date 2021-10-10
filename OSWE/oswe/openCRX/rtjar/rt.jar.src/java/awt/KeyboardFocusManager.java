/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.peer.KeyboardFocusManagerPeer;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyChangeSupport;
/*      */ import java.beans.PropertyVetoException;
/*      */ import java.beans.VetoableChangeListener;
/*      */ import java.beans.VetoableChangeSupport;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Field;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.KeyboardFocusManagerPeerProvider;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class KeyboardFocusManager
/*      */   implements KeyEventDispatcher, KeyEventPostProcessor
/*      */ {
/*  112 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("java.awt.focus.KeyboardFocusManager");
/*      */   transient KeyboardFocusManagerPeer peer;
/*      */   
/*      */   static {
/*  116 */     Toolkit.loadLibraries();
/*  117 */     if (!GraphicsEnvironment.isHeadless()) {
/*  118 */       initIDs();
/*      */     }
/*  120 */     AWTAccessor.setKeyboardFocusManagerAccessor(new AWTAccessor.KeyboardFocusManagerAccessor()
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public int shouldNativelyFocusHeavyweight(Component param1Component1, Component param1Component2, boolean param1Boolean1, boolean param1Boolean2, long param1Long, CausedFocusEvent.Cause param1Cause)
/*      */           {
/*  129 */             return KeyboardFocusManager.shouldNativelyFocusHeavyweight(param1Component1, param1Component2, param1Boolean1, param1Boolean2, param1Long, param1Cause);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean processSynchronousLightweightTransfer(Component param1Component1, Component param1Component2, boolean param1Boolean1, boolean param1Boolean2, long param1Long) {
/*  138 */             return KeyboardFocusManager.processSynchronousLightweightTransfer(param1Component1, param1Component2, param1Boolean1, param1Boolean2, param1Long);
/*      */           }
/*      */           
/*      */           public void removeLastFocusRequest(Component param1Component) {
/*  142 */             KeyboardFocusManager.removeLastFocusRequest(param1Component);
/*      */           }
/*      */           public void setMostRecentFocusOwner(Window param1Window, Component param1Component) {
/*  145 */             KeyboardFocusManager.setMostRecentFocusOwner(param1Window, param1Component);
/*      */           }
/*      */           public KeyboardFocusManager getCurrentKeyboardFocusManager(AppContext param1AppContext) {
/*  148 */             return KeyboardFocusManager.getCurrentKeyboardFocusManager(param1AppContext);
/*      */           }
/*      */           public Container getCurrentFocusCycleRoot() {
/*  151 */             return KeyboardFocusManager.currentFocusCycleRoot;
/*      */           }
/*      */         });
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
/*  164 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.KeyboardFocusManager");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int FORWARD_TRAVERSAL_KEYS = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int BACKWARD_TRAVERSAL_KEYS = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int UP_CYCLE_TRAVERSAL_KEYS = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DOWN_CYCLE_TRAVERSAL_KEYS = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int TRAVERSAL_KEY_LENGTH = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Component focusOwner;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Component permanentFocusOwner;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Window focusedWindow;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Window activeWindow;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyboardFocusManager getCurrentKeyboardFocusManager() {
/*  216 */     return getCurrentKeyboardFocusManager(AppContext.getAppContext());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static synchronized KeyboardFocusManager getCurrentKeyboardFocusManager(AppContext paramAppContext) {
/*  223 */     KeyboardFocusManager keyboardFocusManager = (KeyboardFocusManager)paramAppContext.get(KeyboardFocusManager.class);
/*  224 */     if (keyboardFocusManager == null) {
/*  225 */       keyboardFocusManager = new DefaultKeyboardFocusManager();
/*  226 */       paramAppContext.put(KeyboardFocusManager.class, keyboardFocusManager);
/*      */     } 
/*  228 */     return keyboardFocusManager;
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
/*      */   public static void setCurrentKeyboardFocusManager(KeyboardFocusManager paramKeyboardFocusManager) throws SecurityException {
/*  251 */     checkReplaceKFMPermission();
/*      */     
/*  253 */     KeyboardFocusManager keyboardFocusManager = null;
/*      */     
/*  255 */     synchronized (KeyboardFocusManager.class) {
/*  256 */       AppContext appContext = AppContext.getAppContext();
/*      */       
/*  258 */       if (paramKeyboardFocusManager != null) {
/*  259 */         keyboardFocusManager = getCurrentKeyboardFocusManager(appContext);
/*      */         
/*  261 */         appContext.put(KeyboardFocusManager.class, paramKeyboardFocusManager);
/*      */       } else {
/*  263 */         keyboardFocusManager = getCurrentKeyboardFocusManager(appContext);
/*  264 */         appContext.remove(KeyboardFocusManager.class);
/*      */       } 
/*      */     } 
/*      */     
/*  268 */     if (keyboardFocusManager != null) {
/*  269 */       keyboardFocusManager.firePropertyChange("managingFocus", Boolean.TRUE, Boolean.FALSE);
/*      */     }
/*      */ 
/*      */     
/*  273 */     if (paramKeyboardFocusManager != null) {
/*  274 */       paramKeyboardFocusManager.firePropertyChange("managingFocus", Boolean.FALSE, Boolean.TRUE);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  314 */   private FocusTraversalPolicy defaultPolicy = new DefaultFocusTraversalPolicy();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  320 */   private static final String[] defaultFocusTraversalKeyPropertyNames = new String[] { "forwardDefaultFocusTraversalKeys", "backwardDefaultFocusTraversalKeys", "upCycleDefaultFocusTraversalKeys", "downCycleDefaultFocusTraversalKeys" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  330 */   private static final AWTKeyStroke[][] defaultFocusTraversalKeyStrokes = new AWTKeyStroke[][] {
/*      */       {
/*  332 */         AWTKeyStroke.getAWTKeyStroke(9, 0, false), 
/*  333 */         AWTKeyStroke.getAWTKeyStroke(9, 130, false)
/*      */       
/*      */       },
/*  336 */       { AWTKeyStroke.getAWTKeyStroke(9, 65, false), 
/*  337 */         AWTKeyStroke.getAWTKeyStroke(9, 195, false) }, {}, {}
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
/*  351 */   private Set<AWTKeyStroke>[] defaultFocusTraversalKeys = (Set<AWTKeyStroke>[])new Set[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Container currentFocusCycleRoot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private VetoableChangeSupport vetoableSupport;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeSupport changeSupport;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LinkedList<KeyEventDispatcher> keyEventDispatchers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LinkedList<KeyEventPostProcessor> keyEventPostProcessors;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  393 */   private static Map<Window, WeakReference<Component>> mostRecentFocusOwners = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AWTPermission replaceKeyboardFocusManagerPermission;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  404 */   transient SequencedEvent currentSequencedEvent = null;
/*      */   
/*      */   final void setCurrentSequencedEvent(SequencedEvent paramSequencedEvent) {
/*  407 */     synchronized (SequencedEvent.class) {
/*  408 */       assert paramSequencedEvent == null || this.currentSequencedEvent == null;
/*  409 */       this.currentSequencedEvent = paramSequencedEvent;
/*      */     } 
/*      */   }
/*      */   
/*      */   final SequencedEvent getCurrentSequencedEvent() {
/*  414 */     synchronized (SequencedEvent.class) {
/*  415 */       return this.currentSequencedEvent;
/*      */     } 
/*      */   }
/*      */   
/*      */   static Set<AWTKeyStroke> initFocusTraversalKeysSet(String paramString, Set<AWTKeyStroke> paramSet) {
/*  420 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/*  421 */     while (stringTokenizer.hasMoreTokens()) {
/*  422 */       paramSet.add(AWTKeyStroke.getAWTKeyStroke(stringTokenizer.nextToken()));
/*      */     }
/*  424 */     return paramSet.isEmpty() ? Collections.EMPTY_SET : 
/*      */       
/*  426 */       Collections.<AWTKeyStroke>unmodifiableSet(paramSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyboardFocusManager() {
/*  433 */     for (byte b = 0; b < 4; b++) {
/*  434 */       HashSet<AWTKeyStroke> hashSet = new HashSet();
/*  435 */       for (byte b1 = 0; b1 < (defaultFocusTraversalKeyStrokes[b]).length; b1++) {
/*  436 */         hashSet.add(defaultFocusTraversalKeyStrokes[b][b1]);
/*      */       }
/*  438 */       this.defaultFocusTraversalKeys[b] = hashSet.isEmpty() ? Collections.EMPTY_SET : 
/*      */         
/*  440 */         Collections.<AWTKeyStroke>unmodifiableSet(hashSet);
/*      */     } 
/*  442 */     initPeer();
/*      */   }
/*      */   
/*      */   private void initPeer() {
/*  446 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  447 */     KeyboardFocusManagerPeerProvider keyboardFocusManagerPeerProvider = (KeyboardFocusManagerPeerProvider)toolkit;
/*  448 */     this.peer = keyboardFocusManagerPeerProvider.getKeyboardFocusManagerPeer();
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
/*      */   public Component getFocusOwner() {
/*  466 */     synchronized (KeyboardFocusManager.class) {
/*  467 */       if (focusOwner == null) {
/*  468 */         return null;
/*      */       }
/*      */       
/*  471 */       return (focusOwner.appContext == AppContext.getAppContext()) ? focusOwner : null;
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
/*      */   protected Component getGlobalFocusOwner() throws SecurityException {
/*  499 */     synchronized (KeyboardFocusManager.class) {
/*  500 */       checkKFMSecurity();
/*  501 */       return focusOwner;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setGlobalFocusOwner(Component paramComponent) throws SecurityException {
/*  536 */     Component component = null;
/*  537 */     boolean bool = false;
/*      */     
/*  539 */     if (paramComponent == null || paramComponent.isFocusable()) {
/*  540 */       synchronized (KeyboardFocusManager.class) {
/*  541 */         checkKFMSecurity();
/*      */         
/*  543 */         component = getFocusOwner();
/*      */         
/*      */         try {
/*  546 */           fireVetoableChange("focusOwner", component, paramComponent);
/*      */         }
/*  548 */         catch (PropertyVetoException propertyVetoException) {
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  553 */         focusOwner = paramComponent;
/*      */         
/*  555 */         if (paramComponent != null && (
/*  556 */           getCurrentFocusCycleRoot() == null || 
/*  557 */           !paramComponent.isFocusCycleRoot(getCurrentFocusCycleRoot()))) {
/*      */ 
/*      */           
/*  560 */           Container container = paramComponent.getFocusCycleRootAncestor();
/*  561 */           if (container == null && paramComponent instanceof Window)
/*      */           {
/*  563 */             container = (Container)paramComponent;
/*      */           }
/*  565 */           if (container != null) {
/*  566 */             setGlobalCurrentFocusCycleRootPriv(container);
/*      */           }
/*      */         } 
/*      */         
/*  570 */         bool = true;
/*      */       } 
/*      */     }
/*      */     
/*  574 */     if (bool) {
/*  575 */       firePropertyChange("focusOwner", component, paramComponent);
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
/*      */   public void clearFocusOwner() {
/*  596 */     if (getFocusOwner() != null) {
/*  597 */       clearGlobalFocusOwner();
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
/*      */ 
/*      */   
/*      */   public void clearGlobalFocusOwner() throws SecurityException {
/*  628 */     checkReplaceKFMPermission();
/*  629 */     if (!GraphicsEnvironment.isHeadless()) {
/*      */ 
/*      */       
/*  632 */       Toolkit.getDefaultToolkit();
/*      */       
/*  634 */       _clearGlobalFocusOwner();
/*      */     } 
/*      */   }
/*      */   private void _clearGlobalFocusOwner() {
/*  638 */     Window window = markClearGlobalFocusOwner();
/*  639 */     this.peer.clearGlobalFocusOwner(window);
/*      */   }
/*      */   
/*      */   void clearGlobalFocusOwnerPriv() {
/*  643 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*  645 */             KeyboardFocusManager.this.clearGlobalFocusOwner();
/*  646 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   Component getNativeFocusOwner() {
/*  652 */     return this.peer.getCurrentFocusOwner();
/*      */   }
/*      */   
/*      */   void setNativeFocusOwner(Component paramComponent) {
/*  656 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST))
/*  657 */       focusLog.finest("Calling peer {0} setCurrentFocusOwner for {1}", new Object[] {
/*  658 */             String.valueOf(this.peer), String.valueOf(paramComponent)
/*      */           }); 
/*  660 */     this.peer.setCurrentFocusOwner(paramComponent);
/*      */   }
/*      */   
/*      */   Window getNativeFocusedWindow() {
/*  664 */     return this.peer.getCurrentFocusedWindow();
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
/*      */   public Component getPermanentFocusOwner() {
/*  682 */     synchronized (KeyboardFocusManager.class) {
/*  683 */       if (permanentFocusOwner == null) {
/*  684 */         return null;
/*      */       }
/*      */       
/*  687 */       return 
/*  688 */         (permanentFocusOwner.appContext == AppContext.getAppContext()) ? permanentFocusOwner : null;
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
/*      */   protected Component getGlobalPermanentFocusOwner() throws SecurityException {
/*  714 */     synchronized (KeyboardFocusManager.class) {
/*  715 */       checkKFMSecurity();
/*  716 */       return permanentFocusOwner;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setGlobalPermanentFocusOwner(Component paramComponent) throws SecurityException {
/*  752 */     Component component = null;
/*  753 */     boolean bool = false;
/*      */     
/*  755 */     if (paramComponent == null || paramComponent.isFocusable()) {
/*  756 */       synchronized (KeyboardFocusManager.class) {
/*  757 */         checkKFMSecurity();
/*      */         
/*  759 */         component = getPermanentFocusOwner();
/*      */         
/*      */         try {
/*  762 */           fireVetoableChange("permanentFocusOwner", component, paramComponent);
/*      */         
/*      */         }
/*  765 */         catch (PropertyVetoException propertyVetoException) {
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  770 */         permanentFocusOwner = paramComponent;
/*      */ 
/*      */         
/*  773 */         setMostRecentFocusOwner(paramComponent);
/*      */         
/*  775 */         bool = true;
/*      */       } 
/*      */     }
/*      */     
/*  779 */     if (bool) {
/*  780 */       firePropertyChange("permanentFocusOwner", component, paramComponent);
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
/*      */   public Window getFocusedWindow() {
/*  796 */     synchronized (KeyboardFocusManager.class) {
/*  797 */       if (focusedWindow == null) {
/*  798 */         return null;
/*      */       }
/*      */       
/*  801 */       return (focusedWindow.appContext == AppContext.getAppContext()) ? focusedWindow : null;
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
/*      */   protected Window getGlobalFocusedWindow() throws SecurityException {
/*  821 */     synchronized (KeyboardFocusManager.class) {
/*  822 */       checkKFMSecurity();
/*  823 */       return focusedWindow;
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setGlobalFocusedWindow(Window paramWindow) throws SecurityException {
/*  855 */     Window window = null;
/*  856 */     boolean bool = false;
/*      */     
/*  858 */     if (paramWindow == null || paramWindow.isFocusableWindow()) {
/*  859 */       synchronized (KeyboardFocusManager.class) {
/*  860 */         checkKFMSecurity();
/*      */         
/*  862 */         window = getFocusedWindow();
/*      */         
/*      */         try {
/*  865 */           fireVetoableChange("focusedWindow", window, paramWindow);
/*      */         }
/*  867 */         catch (PropertyVetoException propertyVetoException) {
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  872 */         focusedWindow = paramWindow;
/*  873 */         bool = true;
/*      */       } 
/*      */     }
/*      */     
/*  877 */     if (bool) {
/*  878 */       firePropertyChange("focusedWindow", window, paramWindow);
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
/*      */   public Window getActiveWindow() {
/*  897 */     synchronized (KeyboardFocusManager.class) {
/*  898 */       if (activeWindow == null) {
/*  899 */         return null;
/*      */       }
/*      */       
/*  902 */       return (activeWindow.appContext == AppContext.getAppContext()) ? activeWindow : null;
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
/*      */   protected Window getGlobalActiveWindow() throws SecurityException {
/*  925 */     synchronized (KeyboardFocusManager.class) {
/*  926 */       checkKFMSecurity();
/*  927 */       return activeWindow;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setGlobalActiveWindow(Window paramWindow) throws SecurityException {
/*      */     Window window;
/*  961 */     synchronized (KeyboardFocusManager.class) {
/*  962 */       checkKFMSecurity();
/*      */       
/*  964 */       window = getActiveWindow();
/*  965 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  966 */         focusLog.finer("Setting global active window to " + paramWindow + ", old active " + window);
/*      */       }
/*      */       
/*      */       try {
/*  970 */         fireVetoableChange("activeWindow", window, paramWindow);
/*      */       }
/*  972 */       catch (PropertyVetoException propertyVetoException) {
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  977 */       activeWindow = paramWindow;
/*      */     } 
/*      */     
/*  980 */     firePropertyChange("activeWindow", window, paramWindow);
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
/*      */   public synchronized FocusTraversalPolicy getDefaultFocusTraversalPolicy() {
/*  994 */     return this.defaultPolicy;
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
/*      */   public void setDefaultFocusTraversalPolicy(FocusTraversalPolicy paramFocusTraversalPolicy) {
/*      */     FocusTraversalPolicy focusTraversalPolicy;
/* 1015 */     if (paramFocusTraversalPolicy == null) {
/* 1016 */       throw new IllegalArgumentException("default focus traversal policy cannot be null");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1021 */     synchronized (this) {
/* 1022 */       focusTraversalPolicy = this.defaultPolicy;
/* 1023 */       this.defaultPolicy = paramFocusTraversalPolicy;
/*      */     } 
/*      */     
/* 1026 */     firePropertyChange("defaultFocusTraversalPolicy", focusTraversalPolicy, paramFocusTraversalPolicy);
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
/*      */   public void setDefaultFocusTraversalKeys(int paramInt, Set<? extends AWTKeyStroke> paramSet) {
/*      */     Set<AWTKeyStroke> set;
/* 1122 */     if (paramInt < 0 || paramInt >= 4) {
/* 1123 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/* 1125 */     if (paramSet == null) {
/* 1126 */       throw new IllegalArgumentException("cannot set null Set of default focus traversal keys");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1131 */     synchronized (this) {
/* 1132 */       for (AWTKeyStroke aWTKeyStroke : paramSet) {
/*      */         
/* 1134 */         if (aWTKeyStroke == null) {
/* 1135 */           throw new IllegalArgumentException("cannot set null focus traversal key");
/*      */         }
/*      */         
/* 1138 */         if (aWTKeyStroke.getKeyChar() != Character.MAX_VALUE) {
/* 1139 */           throw new IllegalArgumentException("focus traversal keys cannot map to KEY_TYPED events");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1144 */         for (byte b = 0; b < 4; b++) {
/* 1145 */           if (b != paramInt)
/*      */           {
/*      */ 
/*      */             
/* 1149 */             if (this.defaultFocusTraversalKeys[b].contains(aWTKeyStroke)) {
/* 1150 */               throw new IllegalArgumentException("focus traversal keys must be unique for a Component");
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/* 1155 */       set = this.defaultFocusTraversalKeys[paramInt];
/* 1156 */       this.defaultFocusTraversalKeys[paramInt] = 
/* 1157 */         Collections.unmodifiableSet(new HashSet<>(paramSet));
/*      */     } 
/*      */     
/* 1160 */     firePropertyChange(defaultFocusTraversalKeyPropertyNames[paramInt], set, paramSet);
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
/*      */   public Set<AWTKeyStroke> getDefaultFocusTraversalKeys(int paramInt) {
/* 1191 */     if (paramInt < 0 || paramInt >= 4) {
/* 1192 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */ 
/*      */     
/* 1196 */     return this.defaultFocusTraversalKeys[paramInt];
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
/*      */   public Container getCurrentFocusCycleRoot() {
/* 1216 */     synchronized (KeyboardFocusManager.class) {
/* 1217 */       if (currentFocusCycleRoot == null) {
/* 1218 */         return null;
/*      */       }
/*      */       
/* 1221 */       return 
/* 1222 */         (currentFocusCycleRoot.appContext == AppContext.getAppContext()) ? currentFocusCycleRoot : null;
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
/*      */   protected Container getGlobalCurrentFocusCycleRoot() throws SecurityException {
/* 1248 */     synchronized (KeyboardFocusManager.class) {
/* 1249 */       checkKFMSecurity();
/* 1250 */       return currentFocusCycleRoot;
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
/*      */   public void setGlobalCurrentFocusCycleRoot(Container paramContainer) throws SecurityException {
/*      */     Container container;
/* 1280 */     checkReplaceKFMPermission();
/*      */ 
/*      */ 
/*      */     
/* 1284 */     synchronized (KeyboardFocusManager.class) {
/* 1285 */       container = getCurrentFocusCycleRoot();
/* 1286 */       currentFocusCycleRoot = paramContainer;
/*      */     } 
/*      */     
/* 1289 */     firePropertyChange("currentFocusCycleRoot", container, paramContainer);
/*      */   }
/*      */ 
/*      */   
/*      */   void setGlobalCurrentFocusCycleRootPriv(final Container newFocusCycleRoot) {
/* 1294 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/* 1296 */             KeyboardFocusManager.this.setGlobalCurrentFocusCycleRoot(newFocusCycleRoot);
/* 1297 */             return null;
/*      */           }
/*      */         });
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
/*      */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 1334 */     if (paramPropertyChangeListener != null) {
/* 1335 */       synchronized (this) {
/* 1336 */         if (this.changeSupport == null) {
/* 1337 */           this.changeSupport = new PropertyChangeSupport(this);
/*      */         }
/* 1339 */         this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 1357 */     if (paramPropertyChangeListener != null) {
/* 1358 */       synchronized (this) {
/* 1359 */         if (this.changeSupport != null) {
/* 1360 */           this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
/* 1381 */     if (this.changeSupport == null) {
/* 1382 */       this.changeSupport = new PropertyChangeSupport(this);
/*      */     }
/* 1384 */     return this.changeSupport.getPropertyChangeListeners();
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
/*      */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 1421 */     if (paramPropertyChangeListener != null) {
/* 1422 */       synchronized (this) {
/* 1423 */         if (this.changeSupport == null) {
/* 1424 */           this.changeSupport = new PropertyChangeSupport(this);
/*      */         }
/* 1426 */         this.changeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*      */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 1447 */     if (paramPropertyChangeListener != null) {
/* 1448 */       synchronized (this) {
/* 1449 */         if (this.changeSupport != null) {
/* 1450 */           this.changeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized PropertyChangeListener[] getPropertyChangeListeners(String paramString) {
/* 1470 */     if (this.changeSupport == null) {
/* 1471 */       this.changeSupport = new PropertyChangeSupport(this);
/*      */     }
/* 1473 */     return this.changeSupport.getPropertyChangeListeners(paramString);
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
/*      */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 1488 */     if (paramObject1 == paramObject2) {
/*      */       return;
/*      */     }
/* 1491 */     PropertyChangeSupport propertyChangeSupport = this.changeSupport;
/* 1492 */     if (propertyChangeSupport != null) {
/* 1493 */       propertyChangeSupport.firePropertyChange(paramString, paramObject1, paramObject2);
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
/*      */   public void addVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener) {
/* 1515 */     if (paramVetoableChangeListener != null) {
/* 1516 */       synchronized (this) {
/* 1517 */         if (this.vetoableSupport == null) {
/* 1518 */           this.vetoableSupport = new VetoableChangeSupport(this);
/*      */         }
/*      */         
/* 1521 */         this.vetoableSupport.addVetoableChangeListener(paramVetoableChangeListener);
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
/*      */   public void removeVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener) {
/* 1539 */     if (paramVetoableChangeListener != null) {
/* 1540 */       synchronized (this) {
/* 1541 */         if (this.vetoableSupport != null) {
/* 1542 */           this.vetoableSupport.removeVetoableChangeListener(paramVetoableChangeListener);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized VetoableChangeListener[] getVetoableChangeListeners() {
/* 1563 */     if (this.vetoableSupport == null) {
/* 1564 */       this.vetoableSupport = new VetoableChangeSupport(this);
/*      */     }
/* 1566 */     return this.vetoableSupport.getVetoableChangeListeners();
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
/*      */   public void addVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener) {
/* 1589 */     if (paramVetoableChangeListener != null) {
/* 1590 */       synchronized (this) {
/* 1591 */         if (this.vetoableSupport == null) {
/* 1592 */           this.vetoableSupport = new VetoableChangeSupport(this);
/*      */         }
/*      */         
/* 1595 */         this.vetoableSupport.addVetoableChangeListener(paramString, paramVetoableChangeListener);
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
/*      */   public void removeVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener) {
/* 1616 */     if (paramVetoableChangeListener != null) {
/* 1617 */       synchronized (this) {
/* 1618 */         if (this.vetoableSupport != null) {
/* 1619 */           this.vetoableSupport.removeVetoableChangeListener(paramString, paramVetoableChangeListener);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized VetoableChangeListener[] getVetoableChangeListeners(String paramString) {
/* 1640 */     if (this.vetoableSupport == null) {
/* 1641 */       this.vetoableSupport = new VetoableChangeSupport(this);
/*      */     }
/* 1643 */     return this.vetoableSupport.getVetoableChangeListeners(paramString);
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
/*      */   protected void fireVetoableChange(String paramString, Object paramObject1, Object paramObject2) throws PropertyVetoException {
/* 1666 */     if (paramObject1 == paramObject2) {
/*      */       return;
/*      */     }
/* 1669 */     VetoableChangeSupport vetoableChangeSupport = this.vetoableSupport;
/*      */     
/* 1671 */     if (vetoableChangeSupport != null) {
/* 1672 */       vetoableChangeSupport.fireVetoableChange(paramString, paramObject1, paramObject2);
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
/*      */   public void addKeyEventDispatcher(KeyEventDispatcher paramKeyEventDispatcher) {
/* 1700 */     if (paramKeyEventDispatcher != null) {
/* 1701 */       synchronized (this) {
/* 1702 */         if (this.keyEventDispatchers == null) {
/* 1703 */           this.keyEventDispatchers = new LinkedList<>();
/*      */         }
/* 1705 */         this.keyEventDispatchers.add(paramKeyEventDispatcher);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeKeyEventDispatcher(KeyEventDispatcher paramKeyEventDispatcher) {
/* 1731 */     if (paramKeyEventDispatcher != null) {
/* 1732 */       synchronized (this) {
/* 1733 */         if (this.keyEventDispatchers != null) {
/* 1734 */           this.keyEventDispatchers.remove(paramKeyEventDispatcher);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized List<KeyEventDispatcher> getKeyEventDispatchers() {
/* 1756 */     return (this.keyEventDispatchers != null) ? (List<KeyEventDispatcher>)this.keyEventDispatchers
/* 1757 */       .clone() : null;
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
/*      */   public void addKeyEventPostProcessor(KeyEventPostProcessor paramKeyEventPostProcessor) {
/* 1788 */     if (paramKeyEventPostProcessor != null) {
/* 1789 */       synchronized (this) {
/* 1790 */         if (this.keyEventPostProcessors == null) {
/* 1791 */           this.keyEventPostProcessors = new LinkedList<>();
/*      */         }
/* 1793 */         this.keyEventPostProcessors.add(paramKeyEventPostProcessor);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeKeyEventPostProcessor(KeyEventPostProcessor paramKeyEventPostProcessor) {
/* 1821 */     if (paramKeyEventPostProcessor != null) {
/* 1822 */       synchronized (this) {
/* 1823 */         if (this.keyEventPostProcessors != null) {
/* 1824 */           this.keyEventPostProcessors.remove(paramKeyEventPostProcessor);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<KeyEventPostProcessor> getKeyEventPostProcessors() {
/* 1847 */     return (this.keyEventPostProcessors != null) ? (List<KeyEventPostProcessor>)this.keyEventPostProcessors
/* 1848 */       .clone() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setMostRecentFocusOwner(Component paramComponent) {
/* 1855 */     Component component = paramComponent;
/* 1856 */     while (component != null && !(component instanceof Window)) {
/* 1857 */       component = component.parent;
/*      */     }
/* 1859 */     if (component != null) {
/* 1860 */       setMostRecentFocusOwner((Window)component, paramComponent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static synchronized void setMostRecentFocusOwner(Window paramWindow, Component paramComponent) {
/* 1869 */     WeakReference<Component> weakReference = null;
/* 1870 */     if (paramComponent != null) {
/* 1871 */       weakReference = new WeakReference<>(paramComponent);
/*      */     }
/* 1873 */     mostRecentFocusOwners.put(paramWindow, weakReference);
/*      */   }
/*      */   
/*      */   static void clearMostRecentFocusOwner(Component paramComponent) {
/*      */     Container container;
/* 1878 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1882 */     synchronized (paramComponent.getTreeLock()) {
/* 1883 */       container = paramComponent.getParent();
/* 1884 */       while (container != null && !(container instanceof Window)) {
/* 1885 */         container = container.getParent();
/*      */       }
/*      */     } 
/*      */     
/* 1889 */     synchronized (KeyboardFocusManager.class) {
/* 1890 */       if (container != null && 
/* 1891 */         getMostRecentFocusOwner((Window)container) == paramComponent)
/*      */       {
/* 1893 */         setMostRecentFocusOwner((Window)container, null);
/*      */       }
/*      */       
/* 1896 */       if (container != null) {
/* 1897 */         Window window = (Window)container;
/* 1898 */         if (window.getTemporaryLostComponent() == paramComponent) {
/* 1899 */           window.setTemporaryLostComponent(null);
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
/*      */   static synchronized Component getMostRecentFocusOwner(Window paramWindow) {
/* 1911 */     WeakReference<Component> weakReference = mostRecentFocusOwners.get(paramWindow);
/* 1912 */     return (weakReference == null) ? null : weakReference.get();
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
/*      */   public final void redispatchEvent(Component paramComponent, AWTEvent paramAWTEvent) {
/* 1953 */     paramAWTEvent.focusManagerIsDispatching = true;
/* 1954 */     paramComponent.dispatchEvent(paramAWTEvent);
/* 1955 */     paramAWTEvent.focusManagerIsDispatching = false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void focusNextComponent() {
/* 2109 */     Component component = getFocusOwner();
/* 2110 */     if (component != null) {
/* 2111 */       focusNextComponent(component);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void focusPreviousComponent() {
/* 2119 */     Component component = getFocusOwner();
/* 2120 */     if (component != null) {
/* 2121 */       focusPreviousComponent(component);
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
/*      */   public final void upFocusCycle() {
/* 2135 */     Component component = getFocusOwner();
/* 2136 */     if (component != null) {
/* 2137 */       upFocusCycle(component);
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
/*      */   public final void downFocusCycle() {
/* 2151 */     Component component = getFocusOwner();
/* 2152 */     if (component instanceof Container) {
/* 2153 */       downFocusCycle((Container)component);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void dumpRequests() {
/* 2161 */     System.err.println(">>> Requests dump, time: " + System.currentTimeMillis());
/* 2162 */     synchronized (heavyweightRequests) {
/* 2163 */       for (HeavyweightFocusRequest heavyweightFocusRequest : heavyweightRequests) {
/* 2164 */         System.err.println(">>> Req: " + heavyweightFocusRequest);
/*      */       }
/*      */     } 
/* 2167 */     System.err.println("");
/*      */   }
/*      */   
/*      */   private static final class LightweightFocusRequest {
/*      */     final Component component;
/*      */     final boolean temporary;
/*      */     final CausedFocusEvent.Cause cause;
/*      */     
/*      */     LightweightFocusRequest(Component param1Component, boolean param1Boolean, CausedFocusEvent.Cause param1Cause) {
/* 2176 */       this.component = param1Component;
/* 2177 */       this.temporary = param1Boolean;
/* 2178 */       this.cause = param1Cause;
/*      */     }
/*      */     public String toString() {
/* 2181 */       return "LightweightFocusRequest[component=" + this.component + ",temporary=" + this.temporary + ", cause=" + this.cause + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class HeavyweightFocusRequest
/*      */   {
/*      */     final Component heavyweight;
/*      */     final LinkedList<KeyboardFocusManager.LightweightFocusRequest> lightweightRequests;
/* 2190 */     static final HeavyweightFocusRequest CLEAR_GLOBAL_FOCUS_OWNER = new HeavyweightFocusRequest();
/*      */ 
/*      */     
/*      */     private HeavyweightFocusRequest() {
/* 2194 */       this.heavyweight = null;
/* 2195 */       this.lightweightRequests = null;
/*      */     }
/*      */ 
/*      */     
/*      */     HeavyweightFocusRequest(Component param1Component1, Component param1Component2, boolean param1Boolean, CausedFocusEvent.Cause param1Cause) {
/* 2200 */       if (KeyboardFocusManager.log.isLoggable(PlatformLogger.Level.FINE) && 
/* 2201 */         param1Component1 == null) {
/* 2202 */         KeyboardFocusManager.log.fine("Assertion (heavyweight != null) failed");
/*      */       }
/*      */ 
/*      */       
/* 2206 */       this.heavyweight = param1Component1;
/* 2207 */       this.lightweightRequests = new LinkedList<>();
/* 2208 */       addLightweightRequest(param1Component2, param1Boolean, param1Cause);
/*      */     }
/*      */     
/*      */     boolean addLightweightRequest(Component param1Component, boolean param1Boolean, CausedFocusEvent.Cause param1Cause) {
/* 2212 */       if (KeyboardFocusManager.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 2213 */         if (this == CLEAR_GLOBAL_FOCUS_OWNER) {
/* 2214 */           KeyboardFocusManager.log.fine("Assertion (this != HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER) failed");
/*      */         }
/* 2216 */         if (param1Component == null) {
/* 2217 */           KeyboardFocusManager.log.fine("Assertion (descendant != null) failed");
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2222 */       Component component = (this.lightweightRequests.size() > 0) ? ((KeyboardFocusManager.LightweightFocusRequest)this.lightweightRequests.getLast()).component : null;
/*      */ 
/*      */       
/* 2225 */       if (param1Component != component) {
/*      */         
/* 2227 */         this.lightweightRequests
/* 2228 */           .add(new KeyboardFocusManager.LightweightFocusRequest(param1Component, param1Boolean, param1Cause));
/* 2229 */         return true;
/*      */       } 
/* 2231 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     KeyboardFocusManager.LightweightFocusRequest getFirstLightweightRequest() {
/* 2236 */       if (this == CLEAR_GLOBAL_FOCUS_OWNER) {
/* 2237 */         return null;
/*      */       }
/* 2239 */       return this.lightweightRequests.getFirst();
/*      */     }
/*      */     public String toString() {
/* 2242 */       boolean bool = true;
/* 2243 */       String str = "HeavyweightFocusRequest[heavweight=" + this.heavyweight + ",lightweightRequests=";
/*      */       
/* 2245 */       if (this.lightweightRequests == null) {
/* 2246 */         str = str + null;
/*      */       } else {
/* 2248 */         str = str + "[";
/*      */         
/* 2250 */         for (KeyboardFocusManager.LightweightFocusRequest lightweightFocusRequest : this.lightweightRequests) {
/* 2251 */           if (bool) {
/* 2252 */             bool = false;
/*      */           } else {
/* 2254 */             str = str + ",";
/*      */           } 
/* 2256 */           str = str + lightweightFocusRequest;
/*      */         } 
/* 2258 */         str = str + "]";
/*      */       } 
/* 2260 */       str = str + "]";
/* 2261 */       return str;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2270 */   private static LinkedList<HeavyweightFocusRequest> heavyweightRequests = new LinkedList<>();
/*      */   
/*      */   private static LinkedList<LightweightFocusRequest> currentLightweightRequests;
/*      */   private static boolean clearingCurrentLightweightRequests;
/*      */   private static boolean allowSyncFocusRequests = true;
/* 2275 */   private static Component newFocusOwner = null;
/*      */   
/*      */   private static volatile boolean disableRestoreFocus;
/*      */   
/*      */   static final int SNFH_FAILURE = 0;
/*      */   
/*      */   static final int SNFH_SUCCESS_HANDLED = 1;
/*      */   static final int SNFH_SUCCESS_PROCEED = 2;
/*      */   static Field proxyActive;
/*      */   
/*      */   static boolean processSynchronousLightweightTransfer(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong) {
/* 2286 */     Window window = SunToolkit.getContainingWindow(paramComponent1);
/* 2287 */     if (window == null || !window.syncLWRequests) {
/* 2288 */       return false;
/*      */     }
/* 2290 */     if (paramComponent2 == null)
/*      */     {
/*      */ 
/*      */       
/* 2294 */       paramComponent2 = paramComponent1;
/*      */     }
/*      */     
/* 2297 */     KeyboardFocusManager keyboardFocusManager = getCurrentKeyboardFocusManager(SunToolkit.targetToAppContext(paramComponent2));
/*      */     
/* 2299 */     FocusEvent focusEvent1 = null;
/* 2300 */     FocusEvent focusEvent2 = null;
/* 2301 */     Component component = keyboardFocusManager.getGlobalFocusOwner();
/*      */     
/* 2303 */     synchronized (heavyweightRequests) {
/* 2304 */       HeavyweightFocusRequest heavyweightFocusRequest = getLastHWRequest();
/* 2305 */       if (heavyweightFocusRequest == null && paramComponent1 == keyboardFocusManager
/* 2306 */         .getNativeFocusOwner() && allowSyncFocusRequests) {
/*      */ 
/*      */ 
/*      */         
/* 2310 */         if (paramComponent2 == component)
/*      */         {
/* 2312 */           return true;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2319 */         keyboardFocusManager.enqueueKeyEvents(paramLong, paramComponent2);
/*      */         
/* 2321 */         heavyweightFocusRequest = new HeavyweightFocusRequest(paramComponent1, paramComponent2, paramBoolean1, CausedFocusEvent.Cause.UNKNOWN);
/*      */ 
/*      */         
/* 2324 */         heavyweightRequests.add(heavyweightFocusRequest);
/*      */         
/* 2326 */         if (component != null) {
/* 2327 */           focusEvent1 = new FocusEvent(component, 1005, paramBoolean1, paramComponent2);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2332 */         focusEvent2 = new FocusEvent(paramComponent2, 1004, paramBoolean1, component);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2337 */     boolean bool = false;
/* 2338 */     boolean bool1 = clearingCurrentLightweightRequests;
/*      */     
/* 2340 */     Throwable throwable = null;
/*      */     try {
/* 2342 */       clearingCurrentLightweightRequests = false;
/* 2343 */       synchronized (Component.LOCK) {
/*      */         
/* 2345 */         if (focusEvent1 != null && component != null) {
/* 2346 */           focusEvent1.isPosted = true;
/* 2347 */           throwable = dispatchAndCatchException(throwable, component, focusEvent1);
/* 2348 */           bool = true;
/*      */         } 
/*      */         
/* 2351 */         if (focusEvent2 != null && paramComponent2 != null) {
/* 2352 */           focusEvent2.isPosted = true;
/* 2353 */           throwable = dispatchAndCatchException(throwable, paramComponent2, focusEvent2);
/* 2354 */           bool = true;
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 2358 */       clearingCurrentLightweightRequests = bool1;
/*      */     } 
/* 2360 */     if (throwable instanceof RuntimeException)
/* 2361 */       throw (RuntimeException)throwable; 
/* 2362 */     if (throwable instanceof Error) {
/* 2363 */       throw (Error)throwable;
/*      */     }
/* 2365 */     return bool;
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
/*      */   static int shouldNativelyFocusHeavyweight(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/* 2390 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 2391 */       if (paramComponent1 == null) {
/* 2392 */         log.fine("Assertion (heavyweight != null) failed");
/*      */       }
/* 2394 */       if (paramLong == 0L) {
/* 2395 */         log.fine("Assertion (time != 0) failed");
/*      */       }
/*      */     } 
/*      */     
/* 2399 */     if (paramComponent2 == null)
/*      */     {
/*      */ 
/*      */       
/* 2403 */       paramComponent2 = paramComponent1;
/*      */     }
/*      */ 
/*      */     
/* 2407 */     KeyboardFocusManager keyboardFocusManager1 = getCurrentKeyboardFocusManager(SunToolkit.targetToAppContext(paramComponent2));
/* 2408 */     KeyboardFocusManager keyboardFocusManager2 = getCurrentKeyboardFocusManager();
/* 2409 */     Component component1 = keyboardFocusManager2.getGlobalFocusOwner();
/* 2410 */     Component component2 = keyboardFocusManager2.getNativeFocusOwner();
/* 2411 */     Window window = keyboardFocusManager2.getNativeFocusedWindow();
/* 2412 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER))
/* 2413 */       focusLog.finer("SNFH for {0} in {1}", new Object[] {
/* 2414 */             String.valueOf(paramComponent2), String.valueOf(paramComponent1)
/*      */           }); 
/* 2416 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2417 */       focusLog.finest("0. Current focus owner {0}", new Object[] {
/* 2418 */             String.valueOf(component1) });
/* 2419 */       focusLog.finest("0. Native focus owner {0}", new Object[] {
/* 2420 */             String.valueOf(component2) });
/* 2421 */       focusLog.finest("0. Native focused window {0}", new Object[] {
/* 2422 */             String.valueOf(window) });
/*      */     } 
/* 2424 */     synchronized (heavyweightRequests) {
/* 2425 */       HeavyweightFocusRequest heavyweightFocusRequest = getLastHWRequest();
/* 2426 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2427 */         focusLog.finest("Request {0}", new Object[] { String.valueOf(heavyweightFocusRequest) });
/*      */       }
/* 2429 */       if (heavyweightFocusRequest == null && paramComponent1 == component2 && paramComponent1
/*      */         
/* 2431 */         .getContainingWindow() == window) {
/*      */         
/* 2433 */         if (paramComponent2 == component1) {
/*      */           
/* 2435 */           if (focusLog.isLoggable(PlatformLogger.Level.FINEST))
/* 2436 */             focusLog.finest("1. SNFH_FAILURE for {0}", new Object[] {
/* 2437 */                   String.valueOf(paramComponent2) }); 
/* 2438 */           return 0;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2445 */         keyboardFocusManager1.enqueueKeyEvents(paramLong, paramComponent2);
/*      */         
/* 2447 */         heavyweightFocusRequest = new HeavyweightFocusRequest(paramComponent1, paramComponent2, paramBoolean1, paramCause);
/*      */ 
/*      */         
/* 2450 */         heavyweightRequests.add(heavyweightFocusRequest);
/*      */         
/* 2452 */         if (component1 != null) {
/* 2453 */           CausedFocusEvent causedFocusEvent1 = new CausedFocusEvent(component1, 1005, paramBoolean1, paramComponent2, paramCause);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2459 */           SunToolkit.postEvent(component1.appContext, causedFocusEvent1);
/*      */         } 
/*      */         
/* 2462 */         CausedFocusEvent causedFocusEvent = new CausedFocusEvent(paramComponent2, 1004, paramBoolean1, component1, paramCause);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2467 */         SunToolkit.postEvent(paramComponent2.appContext, causedFocusEvent);
/*      */         
/* 2469 */         if (focusLog.isLoggable(PlatformLogger.Level.FINEST))
/* 2470 */           focusLog.finest("2. SNFH_HANDLED for {0}", new Object[] { String.valueOf(paramComponent2) }); 
/* 2471 */         return 1;
/* 2472 */       }  if (heavyweightFocusRequest != null && heavyweightFocusRequest.heavyweight == paramComponent1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2478 */         if (heavyweightFocusRequest.addLightweightRequest(paramComponent2, paramBoolean1, paramCause))
/*      */         {
/* 2480 */           keyboardFocusManager1.enqueueKeyEvents(paramLong, paramComponent2);
/*      */         }
/*      */         
/* 2483 */         if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2484 */           focusLog.finest("3. SNFH_HANDLED for lightweight" + paramComponent2 + " in " + paramComponent1);
/*      */         }
/*      */         
/* 2487 */         return 1;
/*      */       } 
/* 2489 */       if (!paramBoolean2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2495 */         if (heavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER) {
/*      */ 
/*      */           
/* 2498 */           int i = heavyweightRequests.size();
/*      */           
/* 2500 */           heavyweightFocusRequest = (i >= 2) ? heavyweightRequests.get(i - 2) : null;
/*      */         } 
/*      */         
/* 2503 */         if (focusedWindowChanged(paramComponent1, (heavyweightFocusRequest != null) ? heavyweightFocusRequest.heavyweight : window)) {
/*      */ 
/*      */ 
/*      */           
/* 2507 */           if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2508 */             focusLog.finest("4. SNFH_FAILURE for " + paramComponent2);
/*      */           }
/* 2510 */           return 0;
/*      */         } 
/*      */       } 
/*      */       
/* 2514 */       keyboardFocusManager1.enqueueKeyEvents(paramLong, paramComponent2);
/* 2515 */       heavyweightRequests
/* 2516 */         .add(new HeavyweightFocusRequest(paramComponent1, paramComponent2, paramBoolean1, paramCause));
/*      */       
/* 2518 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2519 */         focusLog.finest("5. SNFH_PROCEED for " + paramComponent2);
/*      */       }
/* 2521 */       return 2;
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
/*      */   static Window markClearGlobalFocusOwner() {
/* 2537 */     Window window = getCurrentKeyboardFocusManager().getNativeFocusedWindow();
/*      */     
/* 2539 */     synchronized (heavyweightRequests) {
/* 2540 */       HeavyweightFocusRequest heavyweightFocusRequest = getLastHWRequest();
/* 2541 */       if (heavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER)
/*      */       {
/*      */ 
/*      */         
/* 2545 */         return null;
/*      */       }
/*      */       
/* 2548 */       heavyweightRequests
/* 2549 */         .add(HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER);
/*      */ 
/*      */       
/* 2552 */       Container container = (heavyweightFocusRequest != null) ? SunToolkit.getContainingWindow(heavyweightFocusRequest.heavyweight) : window;
/*      */       
/* 2554 */       while (container != null && !(container instanceof Frame) && !(container instanceof Dialog))
/*      */       {
/*      */ 
/*      */         
/* 2558 */         container = container.getParent_NoClientCode();
/*      */       }
/*      */       
/* 2561 */       return (Window)container;
/*      */     } 
/*      */   }
/*      */   Component getCurrentWaitingRequest(Component paramComponent) {
/* 2565 */     synchronized (heavyweightRequests) {
/* 2566 */       HeavyweightFocusRequest heavyweightFocusRequest = getFirstHWRequest();
/* 2567 */       if (heavyweightFocusRequest != null && 
/* 2568 */         heavyweightFocusRequest.heavyweight == paramComponent) {
/*      */         
/* 2570 */         LightweightFocusRequest lightweightFocusRequest = heavyweightFocusRequest.lightweightRequests.getFirst();
/* 2571 */         if (lightweightFocusRequest != null) {
/* 2572 */           return lightweightFocusRequest.component;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2577 */     return null;
/*      */   }
/*      */   
/*      */   static boolean isAutoFocusTransferEnabled() {
/* 2581 */     synchronized (heavyweightRequests) {
/* 2582 */       return (heavyweightRequests.size() == 0 && !disableRestoreFocus && null == currentLightweightRequests);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isAutoFocusTransferEnabledFor(Component paramComponent) {
/* 2589 */     return (isAutoFocusTransferEnabled() && paramComponent.isAutoFocusTransferOnDisposal());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Throwable dispatchAndCatchException(Throwable paramThrowable, Component paramComponent, FocusEvent paramFocusEvent) {
/*      */     Error error;
/* 2599 */     RuntimeException runtimeException = null;
/*      */     try {
/* 2601 */       paramComponent.dispatchEvent(paramFocusEvent);
/* 2602 */     } catch (RuntimeException runtimeException1) {
/* 2603 */       runtimeException = runtimeException1;
/* 2604 */     } catch (Error error1) {
/* 2605 */       error = error1;
/*      */     } 
/* 2607 */     if (error != null) {
/* 2608 */       if (paramThrowable != null) {
/* 2609 */         handleException(paramThrowable);
/*      */       }
/* 2611 */       return error;
/*      */     } 
/* 2613 */     return paramThrowable;
/*      */   }
/*      */   
/*      */   private static void handleException(Throwable paramThrowable) {
/* 2617 */     paramThrowable.printStackTrace();
/*      */   }
/*      */   
/*      */   static void processCurrentLightweightRequests() {
/* 2621 */     KeyboardFocusManager keyboardFocusManager = getCurrentKeyboardFocusManager();
/* 2622 */     LinkedList<LightweightFocusRequest> linkedList = null;
/*      */     
/* 2624 */     Component component = keyboardFocusManager.getGlobalFocusOwner();
/* 2625 */     if (component != null && component.appContext != 
/* 2626 */       AppContext.getAppContext()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2634 */     synchronized (heavyweightRequests) {
/* 2635 */       if (currentLightweightRequests != null) {
/* 2636 */         clearingCurrentLightweightRequests = true;
/* 2637 */         disableRestoreFocus = true;
/* 2638 */         linkedList = currentLightweightRequests;
/* 2639 */         allowSyncFocusRequests = (linkedList.size() < 2);
/* 2640 */         currentLightweightRequests = null;
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2647 */     Throwable throwable = null;
/*      */     try {
/* 2649 */       if (linkedList != null) {
/* 2650 */         Component component1 = null;
/* 2651 */         Component component2 = null;
/*      */         
/* 2653 */         for (Iterator<LightweightFocusRequest> iterator = linkedList.iterator(); iterator.hasNext(); ) {
/*      */           
/* 2655 */           component2 = keyboardFocusManager.getGlobalFocusOwner();
/*      */           
/* 2657 */           LightweightFocusRequest lightweightFocusRequest = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2668 */           if (!iterator.hasNext()) {
/* 2669 */             disableRestoreFocus = false;
/*      */           }
/*      */           
/* 2672 */           CausedFocusEvent causedFocusEvent1 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2678 */           if (component2 != null) {
/* 2679 */             causedFocusEvent1 = new CausedFocusEvent(component2, 1005, lightweightFocusRequest.temporary, lightweightFocusRequest.component, lightweightFocusRequest.cause);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 2684 */           CausedFocusEvent causedFocusEvent2 = new CausedFocusEvent(lightweightFocusRequest.component, 1004, lightweightFocusRequest.temporary, (component2 == null) ? component1 : component2, lightweightFocusRequest.cause);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2691 */           if (component2 != null) {
/* 2692 */             causedFocusEvent1.isPosted = true;
/* 2693 */             throwable = dispatchAndCatchException(throwable, component2, causedFocusEvent1);
/*      */           } 
/*      */           
/* 2696 */           causedFocusEvent2.isPosted = true;
/* 2697 */           throwable = dispatchAndCatchException(throwable, lightweightFocusRequest.component, causedFocusEvent2);
/*      */           
/* 2699 */           if (keyboardFocusManager.getGlobalFocusOwner() == lightweightFocusRequest.component) {
/* 2700 */             component1 = lightweightFocusRequest.component;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 2705 */       clearingCurrentLightweightRequests = false;
/* 2706 */       disableRestoreFocus = false;
/* 2707 */       linkedList = null;
/* 2708 */       allowSyncFocusRequests = true;
/*      */     } 
/* 2710 */     if (throwable instanceof RuntimeException)
/* 2711 */       throw (RuntimeException)throwable; 
/* 2712 */     if (throwable instanceof Error) {
/* 2713 */       throw (Error)throwable;
/*      */     }
/*      */   }
/*      */   
/*      */   static FocusEvent retargetUnexpectedFocusEvent(FocusEvent paramFocusEvent) {
/* 2718 */     synchronized (heavyweightRequests) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2723 */       if (removeFirstRequest()) {
/* 2724 */         return (FocusEvent)retargetFocusEvent(paramFocusEvent);
/*      */       }
/*      */       
/* 2727 */       Component component1 = paramFocusEvent.getComponent();
/* 2728 */       Component component2 = paramFocusEvent.getOppositeComponent();
/* 2729 */       boolean bool = false;
/* 2730 */       if (paramFocusEvent.getID() == 1005 && (component2 == null || 
/* 2731 */         isTemporary(component2, component1)))
/*      */       {
/* 2733 */         bool = true;
/*      */       }
/* 2735 */       return new CausedFocusEvent(component1, paramFocusEvent.getID(), bool, component2, CausedFocusEvent.Cause.NATIVE_SYSTEM);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static FocusEvent retargetFocusGained(FocusEvent paramFocusEvent) {
/* 2741 */     assert paramFocusEvent.getID() == 1004;
/*      */ 
/*      */     
/* 2744 */     Component component1 = getCurrentKeyboardFocusManager().getGlobalFocusOwner();
/* 2745 */     Component component2 = paramFocusEvent.getComponent();
/* 2746 */     Component component3 = paramFocusEvent.getOppositeComponent();
/* 2747 */     Component component4 = getHeavyweight(component2);
/*      */     
/* 2749 */     synchronized (heavyweightRequests) {
/* 2750 */       HeavyweightFocusRequest heavyweightFocusRequest = getFirstHWRequest();
/*      */       
/* 2752 */       if (heavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER)
/*      */       {
/* 2754 */         return retargetUnexpectedFocusEvent(paramFocusEvent);
/*      */       }
/*      */       
/* 2757 */       if (component2 != null && component4 == null && heavyweightFocusRequest != null)
/*      */       {
/*      */ 
/*      */         
/* 2761 */         if (component2 == (heavyweightFocusRequest.getFirstLightweightRequest()).component) {
/*      */           
/* 2763 */           component2 = heavyweightFocusRequest.heavyweight;
/* 2764 */           component4 = component2;
/*      */         } 
/*      */       }
/* 2767 */       if (heavyweightFocusRequest != null && component4 == heavyweightFocusRequest.heavyweight) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2773 */         heavyweightRequests.removeFirst();
/*      */ 
/*      */         
/* 2776 */         LightweightFocusRequest lightweightFocusRequest = heavyweightFocusRequest.lightweightRequests.removeFirst();
/*      */         
/* 2778 */         Component component = lightweightFocusRequest.component;
/* 2779 */         if (component1 != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2791 */           newFocusOwner = component;
/*      */         }
/*      */ 
/*      */         
/* 2795 */         boolean bool = (component3 == null || isTemporary(component, component3)) ? false : lightweightFocusRequest.temporary;
/*      */ 
/*      */ 
/*      */         
/* 2799 */         if (heavyweightFocusRequest.lightweightRequests.size() > 0) {
/* 2800 */           currentLightweightRequests = heavyweightFocusRequest.lightweightRequests;
/*      */           
/* 2802 */           EventQueue.invokeLater(new Runnable() {
/*      */                 public void run() {
/* 2804 */                   KeyboardFocusManager.processCurrentLightweightRequests();
/*      */                 }
/*      */               });
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2811 */         return new CausedFocusEvent(component, 1004, bool, component3, lightweightFocusRequest.cause);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2816 */       if (component1 != null && component1
/* 2817 */         .getContainingWindow() == component2 && (heavyweightFocusRequest == null || component2 != heavyweightFocusRequest.heavyweight))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2824 */         return new CausedFocusEvent(component1, 1004, false, null, CausedFocusEvent.Cause.ACTIVATION);
/*      */       }
/*      */ 
/*      */       
/* 2828 */       return retargetUnexpectedFocusEvent(paramFocusEvent);
/*      */     } 
/*      */   }
/*      */   
/*      */   static FocusEvent retargetFocusLost(FocusEvent paramFocusEvent) {
/* 2833 */     assert paramFocusEvent.getID() == 1005;
/*      */ 
/*      */     
/* 2836 */     Component component1 = getCurrentKeyboardFocusManager().getGlobalFocusOwner();
/* 2837 */     Component component2 = paramFocusEvent.getOppositeComponent();
/* 2838 */     Component component3 = getHeavyweight(component2);
/*      */     
/* 2840 */     synchronized (heavyweightRequests) {
/* 2841 */       HeavyweightFocusRequest heavyweightFocusRequest = getFirstHWRequest();
/*      */       
/* 2843 */       if (heavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER) {
/*      */         
/* 2845 */         if (component1 != null)
/*      */         {
/* 2847 */           heavyweightRequests.removeFirst();
/* 2848 */           return new CausedFocusEvent(component1, 1005, false, null, CausedFocusEvent.Cause.CLEAR_GLOBAL_FOCUS_OWNER);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2855 */         if (component2 == null) {
/*      */ 
/*      */           
/* 2858 */           if (component1 != null) {
/* 2859 */             return new CausedFocusEvent(component1, 1005, true, null, CausedFocusEvent.Cause.ACTIVATION);
/*      */           }
/*      */ 
/*      */           
/* 2863 */           return paramFocusEvent;
/*      */         } 
/* 2865 */         if (heavyweightFocusRequest != null && (component3 == heavyweightFocusRequest.heavyweight || (component3 == null && component2 == 
/*      */ 
/*      */           
/* 2868 */           (heavyweightFocusRequest.getFirstLightweightRequest()).component))) {
/*      */           
/* 2870 */           if (component1 == null) {
/* 2871 */             return paramFocusEvent;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2881 */           LightweightFocusRequest lightweightFocusRequest = heavyweightFocusRequest.lightweightRequests.getFirst();
/*      */           
/* 2883 */           boolean bool = isTemporary(component2, component1) ? true : lightweightFocusRequest.temporary;
/*      */ 
/*      */ 
/*      */           
/* 2887 */           return new CausedFocusEvent(component1, 1005, bool, lightweightFocusRequest.component, lightweightFocusRequest.cause);
/*      */         } 
/* 2889 */         if (focusedWindowChanged(component2, component1)) {
/*      */ 
/*      */           
/* 2892 */           if (!paramFocusEvent.isTemporary() && component1 != null)
/*      */           {
/* 2894 */             paramFocusEvent = new CausedFocusEvent(component1, 1005, true, component2, CausedFocusEvent.Cause.ACTIVATION);
/*      */           }
/*      */           
/* 2897 */           return paramFocusEvent;
/*      */         } 
/*      */       } 
/* 2900 */       return retargetUnexpectedFocusEvent(paramFocusEvent);
/*      */     } 
/*      */   }
/*      */   
/*      */   static AWTEvent retargetFocusEvent(AWTEvent paramAWTEvent) {
/* 2905 */     if (clearingCurrentLightweightRequests) {
/* 2906 */       return paramAWTEvent;
/*      */     }
/*      */     
/* 2909 */     KeyboardFocusManager keyboardFocusManager = getCurrentKeyboardFocusManager();
/* 2910 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 2911 */       if (paramAWTEvent instanceof FocusEvent || paramAWTEvent instanceof java.awt.event.WindowEvent) {
/* 2912 */         focusLog.finer(">>> {0}", new Object[] { String.valueOf(paramAWTEvent) });
/*      */       }
/* 2914 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER) && paramAWTEvent instanceof KeyEvent) {
/* 2915 */         focusLog.finer("    focus owner is {0}", new Object[] {
/* 2916 */               String.valueOf(keyboardFocusManager.getGlobalFocusOwner()) });
/* 2917 */         focusLog.finer(">>> {0}", new Object[] { String.valueOf(paramAWTEvent) });
/*      */       } 
/*      */     } 
/*      */     
/* 2921 */     synchronized (heavyweightRequests) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2932 */       if (newFocusOwner != null && paramAWTEvent
/* 2933 */         .getID() == 1005) {
/*      */         
/* 2935 */         FocusEvent focusEvent = (FocusEvent)paramAWTEvent;
/*      */         
/* 2937 */         if (keyboardFocusManager.getGlobalFocusOwner() == focusEvent.getComponent() && focusEvent
/* 2938 */           .getOppositeComponent() == newFocusOwner) {
/*      */           
/* 2940 */           newFocusOwner = null;
/* 2941 */           return paramAWTEvent;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2946 */     processCurrentLightweightRequests();
/*      */     
/* 2948 */     switch (paramAWTEvent.getID()) {
/*      */       case 1004:
/* 2950 */         paramAWTEvent = retargetFocusGained((FocusEvent)paramAWTEvent);
/*      */         break;
/*      */       
/*      */       case 1005:
/* 2954 */         paramAWTEvent = retargetFocusLost((FocusEvent)paramAWTEvent);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2960 */     return paramAWTEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void clearMarkers() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean removeFirstRequest() {
/* 2974 */     KeyboardFocusManager keyboardFocusManager = getCurrentKeyboardFocusManager();
/*      */     
/* 2976 */     synchronized (heavyweightRequests) {
/* 2977 */       HeavyweightFocusRequest heavyweightFocusRequest = getFirstHWRequest();
/*      */       
/* 2979 */       if (heavyweightFocusRequest != null) {
/* 2980 */         heavyweightRequests.removeFirst();
/* 2981 */         if (heavyweightFocusRequest.lightweightRequests != null) {
/*      */           
/* 2983 */           Iterator<LightweightFocusRequest> iterator = heavyweightFocusRequest.lightweightRequests.iterator();
/* 2984 */           while (iterator.hasNext())
/*      */           {
/* 2986 */             keyboardFocusManager
/* 2987 */               .dequeueKeyEvents(-1L, ((LightweightFocusRequest)iterator.next()).component);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2994 */       if (heavyweightRequests.size() == 0) {
/* 2995 */         keyboardFocusManager.clearMarkers();
/*      */       }
/* 2997 */       return (heavyweightRequests.size() > 0);
/*      */     } 
/*      */   }
/*      */   static void removeLastFocusRequest(Component paramComponent) {
/* 3001 */     if (log.isLoggable(PlatformLogger.Level.FINE) && 
/* 3002 */       paramComponent == null) {
/* 3003 */       log.fine("Assertion (heavyweight != null) failed");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3008 */     KeyboardFocusManager keyboardFocusManager = getCurrentKeyboardFocusManager();
/* 3009 */     synchronized (heavyweightRequests) {
/* 3010 */       HeavyweightFocusRequest heavyweightFocusRequest = getLastHWRequest();
/* 3011 */       if (heavyweightFocusRequest != null && heavyweightFocusRequest.heavyweight == paramComponent)
/*      */       {
/* 3013 */         heavyweightRequests.removeLast();
/*      */       }
/*      */ 
/*      */       
/* 3017 */       if (heavyweightRequests.size() == 0) {
/* 3018 */         keyboardFocusManager.clearMarkers();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean focusedWindowChanged(Component paramComponent1, Component paramComponent2) {
/* 3024 */     Window window1 = SunToolkit.getContainingWindow(paramComponent1);
/* 3025 */     Window window2 = SunToolkit.getContainingWindow(paramComponent2);
/* 3026 */     if (window1 == null && window2 == null) {
/* 3027 */       return true;
/*      */     }
/* 3029 */     if (window1 == null) {
/* 3030 */       return true;
/*      */     }
/* 3032 */     if (window2 == null) {
/* 3033 */       return true;
/*      */     }
/* 3035 */     return (window1 != window2);
/*      */   }
/*      */   
/*      */   private static boolean isTemporary(Component paramComponent1, Component paramComponent2) {
/* 3039 */     Window window1 = SunToolkit.getContainingWindow(paramComponent1);
/* 3040 */     Window window2 = SunToolkit.getContainingWindow(paramComponent2);
/* 3041 */     if (window1 == null && window2 == null) {
/* 3042 */       return false;
/*      */     }
/* 3044 */     if (window1 == null) {
/* 3045 */       return true;
/*      */     }
/* 3047 */     if (window2 == null) {
/* 3048 */       return false;
/*      */     }
/* 3050 */     return (window1 != window2);
/*      */   }
/*      */   
/*      */   static Component getHeavyweight(Component paramComponent) {
/* 3054 */     if (paramComponent == null || paramComponent.getPeer() == null)
/* 3055 */       return null; 
/* 3056 */     if (paramComponent.getPeer() instanceof java.awt.peer.LightweightPeer) {
/* 3057 */       return paramComponent.getNativeContainer();
/*      */     }
/* 3059 */     return paramComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isProxyActiveImpl(KeyEvent paramKeyEvent) {
/* 3066 */     if (proxyActive == null) {
/* 3067 */       proxyActive = AccessController.<Field>doPrivileged(new PrivilegedAction<Field>() {
/*      */             public Field run() {
/* 3069 */               Field field = null;
/*      */               try {
/* 3071 */                 field = KeyEvent.class.getDeclaredField("isProxyActive");
/* 3072 */                 if (field != null) {
/* 3073 */                   field.setAccessible(true);
/*      */                 }
/* 3075 */               } catch (NoSuchFieldException noSuchFieldException) {
/*      */                 assert false;
/*      */               } 
/* 3078 */               return field;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     try {
/* 3084 */       return proxyActive.getBoolean(paramKeyEvent);
/* 3085 */     } catch (IllegalAccessException illegalAccessException) {
/*      */       assert false;
/*      */       
/* 3088 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   static boolean isProxyActive(KeyEvent paramKeyEvent) {
/* 3093 */     if (!GraphicsEnvironment.isHeadless()) {
/* 3094 */       return isProxyActiveImpl(paramKeyEvent);
/*      */     }
/* 3096 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static HeavyweightFocusRequest getLastHWRequest() {
/* 3101 */     synchronized (heavyweightRequests) {
/* 3102 */       return (heavyweightRequests.size() > 0) ? heavyweightRequests
/* 3103 */         .getLast() : null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static HeavyweightFocusRequest getFirstHWRequest() {
/* 3109 */     synchronized (heavyweightRequests) {
/* 3110 */       return (heavyweightRequests.size() > 0) ? heavyweightRequests
/* 3111 */         .getFirst() : null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkReplaceKFMPermission() throws SecurityException {
/* 3119 */     SecurityManager securityManager = System.getSecurityManager();
/* 3120 */     if (securityManager != null) {
/* 3121 */       if (replaceKeyboardFocusManagerPermission == null) {
/* 3122 */         replaceKeyboardFocusManagerPermission = new AWTPermission("replaceKeyboardFocusManager");
/*      */       }
/*      */       
/* 3125 */       securityManager
/* 3126 */         .checkPermission(replaceKeyboardFocusManagerPermission);
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
/*      */   private void checkKFMSecurity() throws SecurityException {
/* 3146 */     if (this != getCurrentKeyboardFocusManager())
/* 3147 */       checkReplaceKFMPermission(); 
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   public abstract boolean dispatchEvent(AWTEvent paramAWTEvent);
/*      */   
/*      */   public abstract boolean dispatchKeyEvent(KeyEvent paramKeyEvent);
/*      */   
/*      */   public abstract boolean postProcessKeyEvent(KeyEvent paramKeyEvent);
/*      */   
/*      */   public abstract void processKeyEvent(Component paramComponent, KeyEvent paramKeyEvent);
/*      */   
/*      */   protected abstract void enqueueKeyEvents(long paramLong, Component paramComponent);
/*      */   
/*      */   protected abstract void dequeueKeyEvents(long paramLong, Component paramComponent);
/*      */   
/*      */   protected abstract void discardKeyEvents(Component paramComponent);
/*      */   
/*      */   public abstract void focusNextComponent(Component paramComponent);
/*      */   
/*      */   public abstract void focusPreviousComponent(Component paramComponent);
/*      */   
/*      */   public abstract void upFocusCycle(Component paramComponent);
/*      */   
/*      */   public abstract void downFocusCycle(Container paramContainer);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/KeyboardFocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */