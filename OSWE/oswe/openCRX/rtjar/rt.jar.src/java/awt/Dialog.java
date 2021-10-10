/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.peer.DialogPeer;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessControlException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.util.IdentityArrayList;
/*      */ import sun.awt.util.IdentityLinkedList;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Dialog
/*      */   extends Window
/*      */ {
/*      */   static {
/*  101 */     Toolkit.loadLibraries();
/*  102 */     if (!GraphicsEnvironment.isHeadless()) {
/*  103 */       initIDs();
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
/*      */   boolean resizable = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean undecorated = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean initialized = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum ModalityType
/*      */   {
/*  151 */     MODELESS,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     DOCUMENT_MODAL,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  168 */     APPLICATION_MODAL,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  184 */     TOOLKIT_MODAL;
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
/*  197 */   public static final ModalityType DEFAULT_MODALITY_TYPE = ModalityType.APPLICATION_MODAL;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean modal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ModalityType modalityType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum ModalExclusionType
/*      */   {
/*  247 */     NO_EXCLUDE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  253 */     APPLICATION_EXCLUDE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  265 */     TOOLKIT_EXCLUDE;
/*      */   }
/*      */ 
/*      */   
/*  269 */   static transient IdentityArrayList<Dialog> modalDialogs = new IdentityArrayList<>();
/*      */   
/*  271 */   transient IdentityArrayList<Window> blockedWindows = new IdentityArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String title;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient ModalEventFilter modalFilter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile transient SecondaryLoop secondaryLoop;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   volatile transient boolean isInHide = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   volatile transient boolean isInDispose = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String base = "dialog";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  309 */   private static int nameCounter = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 5920926903803293709L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dialog(Frame paramFrame) {
/*  332 */     this(paramFrame, "", false);
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
/*      */   public Dialog(Frame paramFrame, boolean paramBoolean) {
/*  358 */     this(paramFrame, "", paramBoolean);
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
/*      */   public Dialog(Frame paramFrame, String paramString) {
/*  379 */     this(paramFrame, paramString, false);
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
/*      */   public Dialog(Frame paramFrame, String paramString, boolean paramBoolean) {
/*  409 */     this(paramFrame, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
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
/*      */   public Dialog(Frame paramFrame, String paramString, boolean paramBoolean, GraphicsConfiguration paramGraphicsConfiguration) {
/*  443 */     this(paramFrame, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, paramGraphicsConfiguration);
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
/*      */   public Dialog(Dialog paramDialog) {
/*  460 */     this(paramDialog, "", false);
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
/*      */   public Dialog(Dialog paramDialog, String paramString) {
/*  480 */     this(paramDialog, paramString, false);
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
/*      */   public Dialog(Dialog paramDialog, String paramString, boolean paramBoolean) {
/*  510 */     this(paramDialog, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
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
/*      */   public Dialog(Dialog paramDialog, String paramString, boolean paramBoolean, GraphicsConfiguration paramGraphicsConfiguration) {
/*  547 */     this(paramDialog, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, paramGraphicsConfiguration);
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
/*      */   public Dialog(Window paramWindow) {
/*  571 */     this(paramWindow, "", ModalityType.MODELESS);
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
/*      */   public Dialog(Window paramWindow, String paramString) {
/*  597 */     this(paramWindow, paramString, ModalityType.MODELESS);
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
/*      */   public Dialog(Window paramWindow, ModalityType paramModalityType) {
/*  630 */     this(paramWindow, "", paramModalityType);
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
/*      */   public Dialog(Window paramWindow, String paramString, ModalityType paramModalityType) {
/*  665 */     super(paramWindow);
/*      */     
/*  667 */     if (paramWindow != null && !(paramWindow instanceof Frame) && !(paramWindow instanceof Dialog))
/*      */     {
/*      */ 
/*      */       
/*  671 */       throw new IllegalArgumentException("Wrong parent window");
/*      */     }
/*      */     
/*  674 */     this.title = paramString;
/*  675 */     setModalityType(paramModalityType);
/*  676 */     SunToolkit.checkAndSetPolicy(this);
/*  677 */     this.initialized = true;
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
/*      */   public Dialog(Window paramWindow, String paramString, ModalityType paramModalityType, GraphicsConfiguration paramGraphicsConfiguration) {
/*  717 */     super(paramWindow, paramGraphicsConfiguration);
/*      */     
/*  719 */     if (paramWindow != null && !(paramWindow instanceof Frame) && !(paramWindow instanceof Dialog))
/*      */     {
/*      */ 
/*      */       
/*  723 */       throw new IllegalArgumentException("wrong owner window");
/*      */     }
/*      */     
/*  726 */     this.title = paramString;
/*  727 */     setModalityType(paramModalityType);
/*  728 */     SunToolkit.checkAndSetPolicy(this);
/*  729 */     this.initialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String constructComponentName() {
/*  737 */     synchronized (Dialog.class) {
/*  738 */       return "dialog" + nameCounter++;
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
/*      */   public void addNotify() {
/*  752 */     synchronized (getTreeLock()) {
/*  753 */       if (this.parent != null && this.parent.getPeer() == null) {
/*  754 */         this.parent.addNotify();
/*      */       }
/*      */       
/*  757 */       if (this.peer == null) {
/*  758 */         this.peer = getToolkit().createDialog(this);
/*      */       }
/*  760 */       super.addNotify();
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
/*      */   public boolean isModal() {
/*  780 */     return isModal_NoClientCode();
/*      */   }
/*      */   final boolean isModal_NoClientCode() {
/*  783 */     return (this.modalityType != ModalityType.MODELESS);
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
/*      */   public void setModal(boolean paramBoolean) {
/*  810 */     this.modal = paramBoolean;
/*  811 */     setModalityType(paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
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
/*      */   public ModalityType getModalityType() {
/*  824 */     return this.modalityType;
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
/*      */   public void setModalityType(ModalityType paramModalityType) {
/*  850 */     if (paramModalityType == null) {
/*  851 */       paramModalityType = ModalityType.MODELESS;
/*      */     }
/*  853 */     if (!Toolkit.getDefaultToolkit().isModalityTypeSupported(paramModalityType)) {
/*  854 */       paramModalityType = ModalityType.MODELESS;
/*      */     }
/*  856 */     if (this.modalityType == paramModalityType) {
/*      */       return;
/*      */     }
/*      */     
/*  860 */     checkModalityPermission(paramModalityType);
/*      */     
/*  862 */     this.modalityType = paramModalityType;
/*  863 */     this.modal = (this.modalityType != ModalityType.MODELESS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTitle() {
/*  874 */     return this.title;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTitle(String paramString) {
/*  884 */     String str = this.title;
/*      */     
/*  886 */     synchronized (this) {
/*  887 */       this.title = paramString;
/*  888 */       DialogPeer dialogPeer = (DialogPeer)this.peer;
/*  889 */       if (dialogPeer != null) {
/*  890 */         dialogPeer.setTitle(paramString);
/*      */       }
/*      */     } 
/*  893 */     firePropertyChange("title", str, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean conditionalShow(Component paramComponent, AtomicLong paramAtomicLong) {
/*      */     boolean bool;
/*  902 */     closeSplashScreen();
/*      */     
/*  904 */     synchronized (getTreeLock()) {
/*  905 */       if (this.peer == null) {
/*  906 */         addNotify();
/*      */       }
/*  908 */       validateUnconditionally();
/*  909 */       if (this.visible) {
/*  910 */         toFront();
/*  911 */         bool = false;
/*      */       } else {
/*  913 */         this.visible = bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  918 */         if (!isModal()) {
/*  919 */           checkShouldBeBlocked(this);
/*      */         } else {
/*  921 */           modalDialogs.add(this);
/*  922 */           modalShow();
/*      */         } 
/*      */         
/*  925 */         if (paramComponent != null && paramAtomicLong != null && isFocusable() && 
/*  926 */           isEnabled() && !isModalBlocked()) {
/*      */ 
/*      */           
/*  929 */           paramAtomicLong.set(Toolkit.getEventQueue().getMostRecentKeyEventTime());
/*  930 */           KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  931 */             .enqueueKeyEvents(paramAtomicLong.get(), paramComponent);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  936 */         mixOnShowing();
/*      */         
/*  938 */         this.peer.setVisible(true);
/*  939 */         if (isModalBlocked()) {
/*  940 */           this.modalBlocker.toFront();
/*      */         }
/*      */         
/*  943 */         setLocationByPlatform(false);
/*  944 */         for (byte b = 0; b < this.ownedWindowList.size(); b++) {
/*  945 */           Window window = ((WeakReference<Window>)this.ownedWindowList.elementAt(b)).get();
/*  946 */           if (window != null && window.showWithParent) {
/*  947 */             window.show();
/*  948 */             window.showWithParent = false;
/*      */           } 
/*      */         } 
/*  951 */         Window.updateChildFocusableWindowState(this);
/*      */         
/*  953 */         createHierarchyEvents(1400, this, this.parent, 4L, 
/*      */ 
/*      */             
/*  956 */             Toolkit.enabledOnToolkit(32768L));
/*  957 */         if (this.componentListener != null || (this.eventMask & 0x1L) != 0L || 
/*      */           
/*  959 */           Toolkit.enabledOnToolkit(1L)) {
/*  960 */           ComponentEvent componentEvent = new ComponentEvent(this, 102);
/*      */           
/*  962 */           Toolkit.getEventQueue().postEvent(componentEvent);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  967 */     if (bool && (this.state & 0x1) == 0) {
/*  968 */       postWindowEvent(200);
/*  969 */       this.state |= 0x1;
/*      */     } 
/*      */     
/*  972 */     return bool;
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
/*      */   public void setVisible(boolean paramBoolean) {
/* 1005 */     super.setVisible(paramBoolean);
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
/*      */   @Deprecated
/*      */   public void show() {
/* 1030 */     if (!this.initialized) {
/* 1031 */       throw new IllegalStateException("The dialog component has not been initialized properly");
/*      */     }
/*      */ 
/*      */     
/* 1035 */     this.beforeFirstShow = false;
/* 1036 */     if (!isModal()) {
/* 1037 */       conditionalShow((Component)null, (AtomicLong)null);
/*      */     } else {
/* 1039 */       AppContext appContext = AppContext.getAppContext();
/*      */       
/* 1041 */       AtomicLong atomicLong = new AtomicLong();
/* 1042 */       Component component = null;
/*      */       try {
/* 1044 */         component = getMostRecentFocusOwner();
/* 1045 */         if (conditionalShow(component, atomicLong)) {
/* 1046 */           this.modalFilter = ModalEventFilter.createFilterForDialog(this);
/* 1047 */           Conditional conditional = new Conditional()
/*      */             {
/*      */               public boolean evaluate() {
/* 1050 */                 return (Dialog.this.windowClosingException == null);
/*      */               }
/*      */             };
/*      */ 
/*      */ 
/*      */           
/* 1056 */           if (this.modalityType == ModalityType.TOOLKIT_MODAL) {
/* 1057 */             Iterator<AppContext> iterator = AppContext.getAppContexts().iterator();
/* 1058 */             while (iterator.hasNext()) {
/* 1059 */               AppContext appContext1 = iterator.next();
/* 1060 */               if (appContext1 == appContext) {
/*      */                 continue;
/*      */               }
/* 1063 */               EventQueue eventQueue = (EventQueue)appContext1.get(AppContext.EVENT_QUEUE_KEY);
/*      */ 
/*      */               
/* 1066 */               Runnable runnable = new Runnable() {
/*      */                   public void run() {}
/*      */                 };
/* 1069 */               eventQueue.postEvent(new InvocationEvent(this, runnable));
/* 1070 */               EventDispatchThread eventDispatchThread = eventQueue.getDispatchThread();
/* 1071 */               eventDispatchThread.addEventFilter(this.modalFilter);
/*      */             } 
/*      */           } 
/*      */           
/* 1075 */           modalityPushed();
/*      */           try {
/* 1077 */             EventQueue eventQueue = AccessController.<EventQueue>doPrivileged(new PrivilegedAction<EventQueue>()
/*      */                 {
/*      */                   public EventQueue run() {
/* 1080 */                     return Toolkit.getDefaultToolkit().getSystemEventQueue();
/*      */                   }
/*      */                 });
/* 1083 */             this.secondaryLoop = eventQueue.createSecondaryLoop(conditional, this.modalFilter, 0L);
/* 1084 */             if (!this.secondaryLoop.enter()) {
/* 1085 */               this.secondaryLoop = null;
/*      */             }
/*      */           } finally {
/* 1088 */             modalityPopped();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1093 */           if (this.modalityType == ModalityType.TOOLKIT_MODAL) {
/* 1094 */             Iterator<AppContext> iterator = AppContext.getAppContexts().iterator();
/* 1095 */             while (iterator.hasNext()) {
/* 1096 */               AppContext appContext1 = iterator.next();
/* 1097 */               if (appContext1 == appContext) {
/*      */                 continue;
/*      */               }
/* 1100 */               EventQueue eventQueue = (EventQueue)appContext1.get(AppContext.EVENT_QUEUE_KEY);
/* 1101 */               EventDispatchThread eventDispatchThread = eventQueue.getDispatchThread();
/* 1102 */               eventDispatchThread.removeEventFilter(this.modalFilter);
/*      */             } 
/*      */           } 
/*      */           
/* 1106 */           if (this.windowClosingException != null) {
/* 1107 */             this.windowClosingException.fillInStackTrace();
/* 1108 */             throw this.windowClosingException;
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1112 */         if (component != null)
/*      */         {
/* 1114 */           KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 1115 */             .dequeueKeyEvents(atomicLong.get(), component);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   final void modalityPushed() {
/* 1122 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 1123 */     if (toolkit instanceof SunToolkit) {
/* 1124 */       SunToolkit sunToolkit = (SunToolkit)toolkit;
/* 1125 */       sunToolkit.notifyModalityPushed(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   final void modalityPopped() {
/* 1130 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 1131 */     if (toolkit instanceof SunToolkit) {
/* 1132 */       SunToolkit sunToolkit = (SunToolkit)toolkit;
/* 1133 */       sunToolkit.notifyModalityPopped(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   void interruptBlocking() {
/* 1138 */     if (isModal()) {
/* 1139 */       disposeImpl();
/* 1140 */     } else if (this.windowClosingException != null) {
/* 1141 */       this.windowClosingException.fillInStackTrace();
/* 1142 */       this.windowClosingException.printStackTrace();
/* 1143 */       this.windowClosingException = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void hideAndDisposePreHandler() {
/* 1148 */     this.isInHide = true;
/* 1149 */     synchronized (getTreeLock()) {
/* 1150 */       if (this.secondaryLoop != null) {
/* 1151 */         modalHide();
/*      */ 
/*      */         
/* 1154 */         if (this.modalFilter != null) {
/* 1155 */           this.modalFilter.disable();
/*      */         }
/* 1157 */         modalDialogs.remove(this);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void hideAndDisposeHandler() {
/* 1162 */     if (this.secondaryLoop != null) {
/* 1163 */       this.secondaryLoop.exit();
/* 1164 */       this.secondaryLoop = null;
/*      */     } 
/* 1166 */     this.isInHide = false;
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
/*      */   @Deprecated
/*      */   public void hide() {
/* 1180 */     hideAndDisposePreHandler();
/* 1181 */     super.hide();
/*      */ 
/*      */ 
/*      */     
/* 1185 */     if (!this.isInDispose) {
/* 1186 */       hideAndDisposeHandler();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doDispose() {
/* 1197 */     this.isInDispose = true;
/* 1198 */     super.doDispose();
/* 1199 */     hideAndDisposeHandler();
/* 1200 */     this.isInDispose = false;
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
/*      */   public void toBack() {
/* 1212 */     super.toBack();
/* 1213 */     if (this.visible) {
/* 1214 */       synchronized (getTreeLock()) {
/* 1215 */         for (Window window : this.blockedWindows) {
/* 1216 */           window.toBack_NoClientCode();
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
/*      */   public boolean isResizable() {
/* 1230 */     return this.resizable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResizable(boolean paramBoolean) {
/* 1240 */     boolean bool = false;
/*      */     
/* 1242 */     synchronized (this) {
/* 1243 */       this.resizable = paramBoolean;
/* 1244 */       DialogPeer dialogPeer = (DialogPeer)this.peer;
/* 1245 */       if (dialogPeer != null) {
/* 1246 */         dialogPeer.setResizable(paramBoolean);
/* 1247 */         bool = true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1255 */     if (bool) {
/* 1256 */       invalidateIfValid();
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
/*      */   public void setUndecorated(boolean paramBoolean) {
/* 1292 */     synchronized (getTreeLock()) {
/* 1293 */       if (isDisplayable()) {
/* 1294 */         throw new IllegalComponentStateException("The dialog is displayable.");
/*      */       }
/* 1296 */       if (!paramBoolean) {
/* 1297 */         if (getOpacity() < 1.0F) {
/* 1298 */           throw new IllegalComponentStateException("The dialog is not opaque");
/*      */         }
/* 1300 */         if (getShape() != null) {
/* 1301 */           throw new IllegalComponentStateException("The dialog does not have a default shape");
/*      */         }
/* 1303 */         Color color = getBackground();
/* 1304 */         if (color != null && color.getAlpha() < 255) {
/* 1305 */           throw new IllegalComponentStateException("The dialog background color is not opaque");
/*      */         }
/*      */       } 
/* 1308 */       this.undecorated = paramBoolean;
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
/*      */   public boolean isUndecorated() {
/* 1321 */     return this.undecorated;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOpacity(float paramFloat) {
/* 1329 */     synchronized (getTreeLock()) {
/* 1330 */       if (paramFloat < 1.0F && !isUndecorated()) {
/* 1331 */         throw new IllegalComponentStateException("The dialog is decorated");
/*      */       }
/* 1333 */       super.setOpacity(paramFloat);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShape(Shape paramShape) {
/* 1342 */     synchronized (getTreeLock()) {
/* 1343 */       if (paramShape != null && !isUndecorated()) {
/* 1344 */         throw new IllegalComponentStateException("The dialog is decorated");
/*      */       }
/* 1346 */       super.setShape(paramShape);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackground(Color paramColor) {
/* 1355 */     synchronized (getTreeLock()) {
/* 1356 */       if (paramColor != null && paramColor.getAlpha() < 255 && !isUndecorated()) {
/* 1357 */         throw new IllegalComponentStateException("The dialog is decorated");
/*      */       }
/* 1359 */       super.setBackground(paramColor);
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
/*      */   protected String paramString() {
/* 1373 */     String str = super.paramString() + "," + this.modalityType;
/* 1374 */     if (this.title != null) {
/* 1375 */       str = str + ",title=" + this.title;
/*      */     }
/* 1377 */     return str;
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
/*      */   void modalShow() {
/* 1401 */     IdentityArrayList<Dialog> identityArrayList1 = new IdentityArrayList();
/* 1402 */     for (Dialog dialog : modalDialogs) {
/* 1403 */       if (dialog.shouldBlock(this)) {
/* 1404 */         Window window = dialog;
/* 1405 */         while (window != null && window != this) {
/* 1406 */           window = window.getOwner_NoClientCode();
/*      */         }
/* 1408 */         if (window == this || !shouldBlock(dialog) || this.modalityType.compareTo(dialog.getModalityType()) < 0) {
/* 1409 */           identityArrayList1.add(dialog);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1415 */     for (byte b1 = 0; b1 < identityArrayList1.size(); b1++) {
/* 1416 */       Dialog dialog = identityArrayList1.get(b1);
/* 1417 */       if (dialog.isModalBlocked()) {
/* 1418 */         Dialog dialog1 = dialog.getModalBlocker();
/* 1419 */         if (!identityArrayList1.contains(dialog1)) {
/* 1420 */           identityArrayList1.add(b1 + 1, dialog1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1425 */     if (identityArrayList1.size() > 0) {
/* 1426 */       ((Dialog)identityArrayList1.get(0)).blockWindow(this);
/*      */     }
/*      */ 
/*      */     
/* 1430 */     IdentityArrayList<Dialog> identityArrayList2 = new IdentityArrayList<>(identityArrayList1);
/* 1431 */     byte b2 = 0;
/* 1432 */     while (b2 < identityArrayList2.size()) {
/* 1433 */       Window window = identityArrayList2.get(b2);
/* 1434 */       Window[] arrayOfWindow = window.getOwnedWindows_NoClientCode();
/* 1435 */       for (Window window1 : arrayOfWindow) {
/* 1436 */         identityArrayList2.add(window1);
/*      */       }
/* 1438 */       b2++;
/*      */     } 
/*      */     
/* 1441 */     IdentityLinkedList<Window> identityLinkedList = new IdentityLinkedList();
/*      */     
/* 1443 */     IdentityArrayList<Window> identityArrayList = Window.getAllUnblockedWindows();
/* 1444 */     for (Window window : identityArrayList) {
/* 1445 */       if (shouldBlock(window) && !identityArrayList2.contains(window)) {
/* 1446 */         if (window instanceof Dialog && ((Dialog)window).isModal_NoClientCode()) {
/* 1447 */           Dialog dialog = (Dialog)window;
/* 1448 */           if (dialog.shouldBlock(this) && modalDialogs.indexOf(dialog) > modalDialogs.indexOf(this)) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 1452 */         identityLinkedList.add(window);
/*      */       } 
/*      */     } 
/* 1455 */     blockWindows(identityLinkedList);
/*      */     
/* 1457 */     if (!isModalBlocked()) {
/* 1458 */       updateChildrenBlocking();
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
/*      */   void modalHide() {
/* 1471 */     IdentityArrayList<Window> identityArrayList = new IdentityArrayList();
/* 1472 */     int i = this.blockedWindows.size(); byte b;
/* 1473 */     for (b = 0; b < i; b++) {
/* 1474 */       Window window = this.blockedWindows.get(0);
/* 1475 */       identityArrayList.add(window);
/* 1476 */       unblockWindow(window);
/*      */     } 
/*      */ 
/*      */     
/* 1480 */     for (b = 0; b < i; b++) {
/* 1481 */       Window window = identityArrayList.get(b);
/* 1482 */       if (window instanceof Dialog && ((Dialog)window).isModal_NoClientCode()) {
/* 1483 */         Dialog dialog = (Dialog)window;
/* 1484 */         dialog.modalShow();
/*      */       } else {
/* 1486 */         checkShouldBeBlocked(window);
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
/*      */   boolean shouldBlock(Window paramWindow) {
/* 1501 */     if (!isVisible_NoClientCode() || (
/* 1502 */       !paramWindow.isVisible_NoClientCode() && !paramWindow.isInShow) || this.isInHide || paramWindow == this || 
/*      */ 
/*      */       
/* 1505 */       !isModal_NoClientCode())
/*      */     {
/* 1507 */       return false;
/*      */     }
/* 1509 */     if (paramWindow instanceof Dialog && ((Dialog)paramWindow).isInHide) {
/* 1510 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1515 */     Dialog dialog = this;
/* 1516 */     while (dialog != null) {
/* 1517 */       Container container = paramWindow;
/* 1518 */       while (container != null && container != dialog) {
/* 1519 */         container = container.getParent_NoClientCode();
/*      */       }
/* 1521 */       if (container == dialog) {
/* 1522 */         return false;
/*      */       }
/* 1524 */       dialog = dialog.getModalBlocker();
/*      */     } 
/* 1526 */     switch (this.modalityType) {
/*      */       case MODELESS:
/* 1528 */         return false;
/*      */       case DOCUMENT_MODAL:
/* 1530 */         if (paramWindow.isModalExcluded(ModalExclusionType.APPLICATION_EXCLUDE)) {
/*      */ 
/*      */           
/* 1533 */           Container container = this;
/* 1534 */           while (container != null && container != paramWindow) {
/* 1535 */             container = container.getParent_NoClientCode();
/*      */           }
/* 1537 */           return (container == paramWindow);
/*      */         } 
/* 1539 */         return (getDocumentRoot() == paramWindow.getDocumentRoot());
/*      */       
/*      */       case APPLICATION_MODAL:
/* 1542 */         return (!paramWindow.isModalExcluded(ModalExclusionType.APPLICATION_EXCLUDE) && this.appContext == paramWindow.appContext);
/*      */       
/*      */       case TOOLKIT_MODAL:
/* 1545 */         return !paramWindow.isModalExcluded(ModalExclusionType.TOOLKIT_EXCLUDE);
/*      */     } 
/*      */     
/* 1548 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void blockWindow(Window paramWindow) {
/* 1558 */     if (!paramWindow.isModalBlocked()) {
/* 1559 */       paramWindow.setModalBlocked(this, true, true);
/* 1560 */       this.blockedWindows.add(paramWindow);
/*      */     } 
/*      */   }
/*      */   
/*      */   void blockWindows(List<Window> paramList) {
/* 1565 */     DialogPeer dialogPeer = (DialogPeer)this.peer;
/* 1566 */     if (dialogPeer == null) {
/*      */       return;
/*      */     }
/* 1569 */     Iterator<Window> iterator = paramList.iterator();
/* 1570 */     while (iterator.hasNext()) {
/* 1571 */       Window window = iterator.next();
/* 1572 */       if (!window.isModalBlocked()) {
/* 1573 */         window.setModalBlocked(this, true, false); continue;
/*      */       } 
/* 1575 */       iterator.remove();
/*      */     } 
/*      */     
/* 1578 */     dialogPeer.blockWindows(paramList);
/* 1579 */     this.blockedWindows.addAll(paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void unblockWindow(Window paramWindow) {
/* 1588 */     if (paramWindow.isModalBlocked() && this.blockedWindows.contains(paramWindow)) {
/* 1589 */       this.blockedWindows.remove(paramWindow);
/* 1590 */       paramWindow.setModalBlocked(this, false, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkShouldBeBlocked(Window paramWindow) {
/* 1599 */     synchronized (paramWindow.getTreeLock()) {
/* 1600 */       for (byte b = 0; b < modalDialogs.size(); b++) {
/* 1601 */         Dialog dialog = modalDialogs.get(b);
/* 1602 */         if (dialog.shouldBlock(paramWindow)) {
/* 1603 */           dialog.blockWindow(paramWindow);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkModalityPermission(ModalityType paramModalityType) {
/* 1611 */     if (paramModalityType == ModalityType.TOOLKIT_MODAL) {
/* 1612 */       SecurityManager securityManager = System.getSecurityManager();
/* 1613 */       if (securityManager != null) {
/* 1614 */         securityManager.checkPermission(SecurityConstants.AWT.TOOLKIT_MODALITY_PERMISSION);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/* 1624 */     GraphicsEnvironment.checkHeadless();
/*      */ 
/*      */     
/* 1627 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*      */     
/* 1629 */     ModalityType modalityType = (ModalityType)getField.get("modalityType", (Object)null);
/*      */     
/*      */     try {
/* 1632 */       checkModalityPermission(modalityType);
/* 1633 */     } catch (AccessControlException accessControlException) {
/* 1634 */       modalityType = DEFAULT_MODALITY_TYPE;
/*      */     } 
/*      */ 
/*      */     
/* 1638 */     if (modalityType == null) {
/* 1639 */       this.modal = getField.get("modal", false);
/* 1640 */       setModal(this.modal);
/*      */     } else {
/* 1642 */       this.modalityType = modalityType;
/*      */     } 
/*      */     
/* 1645 */     this.resizable = getField.get("resizable", true);
/* 1646 */     this.undecorated = getField.get("undecorated", false);
/* 1647 */     this.title = (String)getField.get("title", "");
/*      */     
/* 1649 */     this.blockedWindows = new IdentityArrayList<>();
/*      */     
/* 1651 */     SunToolkit.checkAndSetPolicy(this);
/*      */     
/* 1653 */     this.initialized = true;
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 1673 */     if (this.accessibleContext == null) {
/* 1674 */       this.accessibleContext = new AccessibleAWTDialog();
/*      */     }
/* 1676 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void initIDs();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleAWTDialog
/*      */     extends Window.AccessibleAWTWindow
/*      */   {
/*      */     private static final long serialVersionUID = 4837230331833941201L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/* 1700 */       return AccessibleRole.DIALOG;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/* 1711 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 1712 */       if (Dialog.this.getFocusOwner() != null) {
/* 1713 */         accessibleStateSet.add(AccessibleState.ACTIVE);
/*      */       }
/* 1715 */       if (Dialog.this.isModal()) {
/* 1716 */         accessibleStateSet.add(AccessibleState.MODAL);
/*      */       }
/* 1718 */       if (Dialog.this.isResizable()) {
/* 1719 */         accessibleStateSet.add(AccessibleState.RESIZABLE);
/*      */       }
/* 1721 */       return accessibleStateSet;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Dialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */