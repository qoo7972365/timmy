/*      */ package sun.applet;
/*      */ 
/*      */ import java.applet.Applet;
/*      */ import java.applet.AppletStub;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Panel;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.JarURLConnection;
/*      */ import java.net.SocketPermission;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessControlException;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSource;
/*      */ import java.security.Permission;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.Permissions;
/*      */ import java.security.Policy;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.EmbeddedFrame;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.misc.MessageUtils;
/*      */ import sun.misc.PerformanceLogger;
/*      */ import sun.misc.Queue;
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
/*      */ public abstract class AppletPanel
/*      */   extends Panel
/*      */   implements AppletStub, Runnable
/*      */ {
/*      */   protected Applet applet;
/*      */   protected boolean doInit = true;
/*      */   protected AppletClassLoader loader;
/*      */   public static final int APPLET_DISPOSE = 0;
/*      */   public static final int APPLET_LOAD = 1;
/*      */   public static final int APPLET_INIT = 2;
/*      */   public static final int APPLET_START = 3;
/*      */   public static final int APPLET_STOP = 4;
/*      */   public static final int APPLET_DESTROY = 5;
/*      */   public static final int APPLET_QUIT = 6;
/*      */   public static final int APPLET_ERROR = 7;
/*      */   public static final int APPLET_RESIZE = 51234;
/*      */   public static final int APPLET_LOADING = 51235;
/*      */   public static final int APPLET_LOADING_COMPLETED = 51236;
/*      */   protected int status;
/*      */   protected Thread handler;
/*  119 */   Dimension defaultAppletSize = new Dimension(10, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   Dimension currentAppletSize = new Dimension(10, 10);
/*      */   
/*  126 */   MessageUtils mu = new MessageUtils();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   Thread loaderThread = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean loadAbortRequest = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  150 */   private static int threadGroupNumber = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private AppletListener listeners;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setupAppletAppContext() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void createAppletThread() {
/*  164 */     String str1 = "applet-" + getCode();
/*  165 */     this.loader = getClassLoader(getCodeBase(), getClassLoaderCacheKey());
/*  166 */     this.loader.grab();
/*      */ 
/*      */ 
/*      */     
/*  170 */     String str2 = getParameter("codebase_lookup");
/*      */     
/*  172 */     if (str2 != null && str2.equals("false")) {
/*  173 */       this.loader.setCodebaseLookup(false);
/*      */     } else {
/*  175 */       this.loader.setCodebaseLookup(true);
/*      */     } 
/*      */     
/*  178 */     ThreadGroup threadGroup = this.loader.getThreadGroup();
/*      */     
/*  180 */     this.handler = new Thread(threadGroup, this, "thread " + str1);
/*      */     
/*  182 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*  185 */             AppletPanel.this.handler.setContextClassLoader(AppletPanel.this.loader);
/*  186 */             return null;
/*      */           }
/*      */         });
/*  189 */     this.handler.start();
/*      */   }
/*      */   
/*      */   void joinAppletThread() throws InterruptedException {
/*  193 */     if (this.handler != null) {
/*  194 */       this.handler.join();
/*  195 */       this.handler = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   void release() {
/*  200 */     if (this.loader != null) {
/*  201 */       this.loader.release();
/*  202 */       this.loader = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init() {
/*      */     try {
/*  212 */       this.defaultAppletSize.width = getWidth();
/*  213 */       this.currentAppletSize.width = this.defaultAppletSize.width;
/*      */ 
/*      */       
/*  216 */       this.defaultAppletSize.height = getHeight();
/*  217 */       this.currentAppletSize.height = this.defaultAppletSize.height;
/*      */     }
/*  219 */     catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */       
/*  222 */       this.status = 7;
/*  223 */       showAppletStatus("badattribute.exception");
/*  224 */       showAppletLog("badattribute.exception");
/*  225 */       showAppletException(numberFormatException);
/*      */     } 
/*      */     
/*  228 */     setLayout(new BorderLayout());
/*      */     
/*  230 */     createAppletThread();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension minimumSize() {
/*  238 */     return new Dimension(this.defaultAppletSize.width, this.defaultAppletSize.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension preferredSize() {
/*  247 */     return new Dimension(this.currentAppletSize.width, this.currentAppletSize.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  256 */   private Queue queue = null;
/*      */ 
/*      */   
/*      */   public synchronized void addAppletListener(AppletListener paramAppletListener) {
/*  260 */     this.listeners = AppletEventMulticaster.add(this.listeners, paramAppletListener);
/*      */   }
/*      */   
/*      */   public synchronized void removeAppletListener(AppletListener paramAppletListener) {
/*  264 */     this.listeners = AppletEventMulticaster.remove(this.listeners, paramAppletListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchAppletEvent(int paramInt, Object paramObject) {
/*  272 */     if (this.listeners != null) {
/*  273 */       AppletEvent appletEvent = new AppletEvent(this, paramInt, paramObject);
/*  274 */       this.listeners.appletStateChanged(appletEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEvent(int paramInt) {
/*  282 */     synchronized (this) {
/*  283 */       if (this.queue == null)
/*      */       {
/*  285 */         this.queue = new Queue();
/*      */       }
/*  287 */       Integer integer = Integer.valueOf(paramInt);
/*  288 */       this.queue.enqueue(integer);
/*  289 */       notifyAll();
/*      */     } 
/*  291 */     if (paramInt == 6) {
/*      */       try {
/*  293 */         joinAppletThread();
/*  294 */       } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  299 */       if (this.loader == null)
/*  300 */         this.loader = getClassLoader(getCodeBase(), getClassLoaderCacheKey()); 
/*  301 */       release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized AppletEvent getNextEvent() throws InterruptedException {
/*  309 */     while (this.queue == null || this.queue.isEmpty()) {
/*  310 */       wait();
/*      */     }
/*  312 */     Integer integer = (Integer)this.queue.dequeue();
/*  313 */     return new AppletEvent(this, integer.intValue(), null);
/*      */   }
/*      */   
/*      */   boolean emptyEventQueue() {
/*  317 */     if (this.queue == null || this.queue.isEmpty()) {
/*  318 */       return true;
/*      */     }
/*  320 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setExceptionStatus(AccessControlException paramAccessControlException) {
/*  331 */     Permission permission = paramAccessControlException.getPermission();
/*  332 */     if (permission instanceof RuntimePermission && 
/*  333 */       permission.getName().startsWith("modifyThread")) {
/*  334 */       if (this.loader == null)
/*  335 */         this.loader = getClassLoader(getCodeBase(), getClassLoaderCacheKey()); 
/*  336 */       this.loader.setExceptionStatus();
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
/*      */   public void run() {
/*  373 */     Thread thread = Thread.currentThread();
/*  374 */     if (thread == this.loaderThread) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  379 */       runLoader();
/*      */       
/*      */       return;
/*      */     } 
/*  383 */     boolean bool = false;
/*  384 */     while (!bool && !thread.isInterrupted()) {
/*      */       AppletEvent appletEvent;
/*      */       try {
/*  387 */         appletEvent = getNextEvent();
/*  388 */       } catch (InterruptedException interruptedException) {
/*  389 */         showAppletStatus("bail");
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*      */         Font font;
/*  395 */         switch (appletEvent.getID()) {
/*      */           case 1:
/*  397 */             if (!okToLoad()) {
/*      */               break;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  408 */             if (this.loaderThread == null) {
/*      */ 
/*      */               
/*  411 */               setLoaderThread(new Thread(this));
/*  412 */               this.loaderThread.start();
/*      */               
/*  414 */               this.loaderThread.join();
/*  415 */               setLoaderThread((Thread)null);
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/*  425 */             if (this.status != 1 && this.status != 5) {
/*  426 */               showAppletStatus("notloaded");
/*      */               break;
/*      */             } 
/*  429 */             this.applet.resize(this.defaultAppletSize);
/*  430 */             if (this.doInit) {
/*  431 */               if (PerformanceLogger.loggingEnabled()) {
/*  432 */                 PerformanceLogger.setTime("Applet Init");
/*  433 */                 PerformanceLogger.outputLog();
/*      */               } 
/*  435 */               this.applet.init();
/*      */             } 
/*      */ 
/*      */             
/*  439 */             font = getFont();
/*  440 */             if (font == null || ("dialog"
/*  441 */               .equals(font.getFamily().toLowerCase(Locale.ENGLISH)) && font
/*  442 */               .getSize() == 12 && font.getStyle() == 0)) {
/*  443 */               setFont(new Font("Dialog", 0, 12));
/*      */             }
/*      */             
/*  446 */             this.doInit = true;
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  451 */               final AppletPanel p = this;
/*  452 */               Runnable runnable = new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  455 */                     p.validate();
/*      */                   }
/*      */                 };
/*  458 */               AWTAccessor.getEventQueueAccessor().invokeAndWait(this.applet, runnable);
/*      */             }
/*  460 */             catch (InterruptedException interruptedException) {
/*      */             
/*  462 */             } catch (InvocationTargetException invocationTargetException) {}
/*      */ 
/*      */             
/*  465 */             this.status = 2;
/*  466 */             showAppletStatus("inited");
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  471 */             if (this.status != 2 && this.status != 4) {
/*  472 */               showAppletStatus("notinited");
/*      */               break;
/*      */             } 
/*  475 */             this.applet.resize(this.currentAppletSize);
/*  476 */             this.applet.start();
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  481 */               final AppletPanel p = this;
/*  482 */               final Applet a = this.applet;
/*  483 */               Runnable runnable = new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  486 */                     p.validate();
/*  487 */                     a.setVisible(true);
/*      */ 
/*      */ 
/*      */                     
/*  491 */                     if (AppletPanel.this.hasInitialFocus()) {
/*  492 */                       AppletPanel.this.setDefaultFocus();
/*      */                     }
/*      */                   }
/*      */                 };
/*  496 */               AWTAccessor.getEventQueueAccessor().invokeAndWait(this.applet, runnable);
/*      */             }
/*  498 */             catch (InterruptedException interruptedException) {
/*      */             
/*  500 */             } catch (InvocationTargetException invocationTargetException) {}
/*      */ 
/*      */             
/*  503 */             this.status = 3;
/*  504 */             showAppletStatus("started");
/*      */             break;
/*      */ 
/*      */           
/*      */           case 4:
/*  509 */             if (this.status != 3) {
/*  510 */               showAppletStatus("notstarted");
/*      */               break;
/*      */             } 
/*  513 */             this.status = 4;
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  518 */               final Applet a = this.applet;
/*  519 */               Runnable runnable = new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  522 */                     a.setVisible(false);
/*      */                   }
/*      */                 };
/*  525 */               AWTAccessor.getEventQueueAccessor().invokeAndWait(this.applet, runnable);
/*      */             }
/*  527 */             catch (InterruptedException interruptedException) {
/*      */             
/*  529 */             } catch (InvocationTargetException invocationTargetException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  539 */               this.applet.stop();
/*  540 */             } catch (AccessControlException accessControlException) {
/*  541 */               setExceptionStatus(accessControlException);
/*      */               
/*  543 */               throw accessControlException;
/*      */             } 
/*  545 */             showAppletStatus("stopped");
/*      */             break;
/*      */           
/*      */           case 5:
/*  549 */             if (this.status != 4 && this.status != 2) {
/*  550 */               showAppletStatus("notstopped");
/*      */               break;
/*      */             } 
/*  553 */             this.status = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  561 */               this.applet.destroy();
/*  562 */             } catch (AccessControlException accessControlException) {
/*  563 */               setExceptionStatus(accessControlException);
/*      */               
/*  565 */               throw accessControlException;
/*      */             } 
/*  567 */             showAppletStatus("destroyed");
/*      */             break;
/*      */           
/*      */           case 0:
/*  571 */             if (this.status != 5 && this.status != 1) {
/*  572 */               showAppletStatus("notdestroyed");
/*      */               break;
/*      */             } 
/*  575 */             this.status = 0;
/*      */             
/*      */             try {
/*  578 */               final Applet a = this.applet;
/*  579 */               Runnable runnable = new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  582 */                     AppletPanel.this.remove(a);
/*      */                   }
/*      */                 };
/*  585 */               AWTAccessor.getEventQueueAccessor().invokeAndWait(this.applet, runnable);
/*      */             }
/*  587 */             catch (InterruptedException interruptedException) {
/*      */ 
/*      */             
/*  590 */             } catch (InvocationTargetException invocationTargetException) {}
/*      */ 
/*      */             
/*  593 */             this.applet = null;
/*  594 */             showAppletStatus("disposed");
/*  595 */             bool = true;
/*      */             break;
/*      */           
/*      */           case 6:
/*      */             return;
/*      */         } 
/*  601 */       } catch (Exception exception) {
/*  602 */         this.status = 7;
/*  603 */         if (exception.getMessage() != null) {
/*  604 */           showAppletStatus("exception2", exception.getClass().getName(), exception
/*  605 */               .getMessage());
/*      */         } else {
/*  607 */           showAppletStatus("exception", exception.getClass().getName());
/*      */         } 
/*  609 */         showAppletException(exception);
/*  610 */       } catch (ThreadDeath threadDeath) {
/*  611 */         showAppletStatus("death");
/*      */         return;
/*  613 */       } catch (Error error) {
/*  614 */         this.status = 7;
/*  615 */         if (error.getMessage() != null) {
/*  616 */           showAppletStatus("error2", error.getClass().getName(), error
/*  617 */               .getMessage());
/*      */         } else {
/*  619 */           showAppletStatus("error", error.getClass().getName());
/*      */         } 
/*  621 */         showAppletException(error);
/*      */       } 
/*  623 */       clearLoadAbortRequest();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Component getMostRecentFocusOwnerForWindow(Window paramWindow) {
/*  634 */     Method method = AccessController.<Method>doPrivileged(new PrivilegedAction<Method>()
/*      */         {
/*      */           public Object run() {
/*  637 */             Method method = null;
/*      */             try {
/*  639 */               method = KeyboardFocusManager.class.getDeclaredMethod("getMostRecentFocusOwner", new Class[] { Window.class });
/*      */ 
/*      */               
/*  642 */               method.setAccessible(true);
/*  643 */             } catch (Exception exception) {
/*      */               
/*  645 */               exception.printStackTrace();
/*      */             } 
/*  647 */             return method;
/*      */           }
/*      */         });
/*  650 */     if (method != null) {
/*      */       
/*      */       try {
/*  653 */         return (Component)method.invoke(null, new Object[] { paramWindow });
/*  654 */       } catch (Exception exception) {
/*      */         
/*  656 */         exception.printStackTrace();
/*      */       } 
/*      */     }
/*      */     
/*  660 */     return paramWindow.getMostRecentFocusOwner();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDefaultFocus() {
/*  668 */     Component component = null;
/*  669 */     Container container = getParent();
/*      */     
/*  671 */     if (container != null) {
/*  672 */       if (container instanceof Window) {
/*  673 */         component = getMostRecentFocusOwnerForWindow((Window)container);
/*  674 */         if (component == container || component == null)
/*      */         {
/*  676 */           component = container.getFocusTraversalPolicy().getInitialComponent((Window)container);
/*      */         }
/*  678 */       } else if (container.isFocusCycleRoot()) {
/*      */         
/*  680 */         component = container.getFocusTraversalPolicy().getDefaultComponent(container);
/*      */       } 
/*      */     }
/*      */     
/*  684 */     if (component != null) {
/*  685 */       if (container instanceof EmbeddedFrame) {
/*  686 */         ((EmbeddedFrame)container).synthesizeWindowActivation(true);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  692 */       component.requestFocusInWindow();
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
/*      */   protected void runLoader() {
/*  704 */     if (this.status != 0) {
/*  705 */       showAppletStatus("notdisposed");
/*      */       
/*      */       return;
/*      */     } 
/*  709 */     dispatchAppletEvent(51235, (Object)null);
/*      */ 
/*      */ 
/*      */     
/*  713 */     this.status = 1;
/*      */ 
/*      */     
/*  716 */     this.loader = getClassLoader(getCodeBase(), getClassLoaderCacheKey());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  722 */     String str = getCode();
/*      */ 
/*      */ 
/*      */     
/*  726 */     setupAppletAppContext();
/*      */     
/*      */     try {
/*  729 */       loadJarFiles(this.loader);
/*  730 */       this.applet = createApplet(this.loader);
/*  731 */     } catch (ClassNotFoundException classNotFoundException) {
/*  732 */       this.status = 7;
/*  733 */       showAppletStatus("notfound", str);
/*  734 */       showAppletLog("notfound", str);
/*  735 */       showAppletException(classNotFoundException);
/*      */       return;
/*  737 */     } catch (InstantiationException instantiationException) {
/*  738 */       this.status = 7;
/*  739 */       showAppletStatus("nocreate", str);
/*  740 */       showAppletLog("nocreate", str);
/*  741 */       showAppletException(instantiationException);
/*      */       return;
/*  743 */     } catch (IllegalAccessException illegalAccessException) {
/*  744 */       this.status = 7;
/*  745 */       showAppletStatus("noconstruct", str);
/*  746 */       showAppletLog("noconstruct", str);
/*  747 */       showAppletException(illegalAccessException);
/*      */       
/*      */       return;
/*  750 */     } catch (Exception exception) {
/*  751 */       this.status = 7;
/*  752 */       showAppletStatus("exception", exception.getMessage());
/*  753 */       showAppletException(exception);
/*      */       return;
/*  755 */     } catch (ThreadDeath threadDeath) {
/*  756 */       this.status = 7;
/*  757 */       showAppletStatus("death");
/*      */       return;
/*  759 */     } catch (Error error) {
/*  760 */       this.status = 7;
/*  761 */       showAppletStatus("error", error.getMessage());
/*  762 */       showAppletException(error);
/*      */       
/*      */       return;
/*      */     } finally {
/*  766 */       dispatchAppletEvent(51236, (Object)null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  772 */     if (this.applet != null) {
/*      */ 
/*      */       
/*  775 */       this.applet.setStub(this);
/*  776 */       this.applet.hide();
/*  777 */       add("Center", this.applet);
/*  778 */       showAppletStatus("loaded");
/*  779 */       validate();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Applet createApplet(AppletClassLoader paramAppletClassLoader) throws ClassNotFoundException, IllegalAccessException, IOException, InstantiationException, InterruptedException {
/*  785 */     String str1 = getSerializedObject();
/*  786 */     String str2 = getCode();
/*      */     
/*  788 */     if (str2 != null && str1 != null) {
/*  789 */       System.err.println(amh.getMessage("runloader.err"));
/*      */       
/*  791 */       throw new InstantiationException("Either \"code\" or \"object\" should be specified, but not both.");
/*      */     } 
/*  793 */     if (str2 == null && str1 == null) {
/*  794 */       String str = "nocode";
/*  795 */       this.status = 7;
/*  796 */       showAppletStatus(str);
/*  797 */       showAppletLog(str);
/*  798 */       repaint();
/*      */     } 
/*  800 */     if (str2 != null) {
/*  801 */       this.applet = paramAppletClassLoader.loadCode(str2).newInstance();
/*  802 */       this.doInit = true;
/*      */     } else {
/*      */       
/*  805 */       try(InputStream null = (InputStream)AccessController.<InputStream>doPrivileged(() -> paramAppletClassLoader.getResourceAsStream(paramString)); 
/*      */           
/*  807 */           AppletObjectInputStream null = new AppletObjectInputStream(inputStream, paramAppletClassLoader)) {
/*      */         
/*  809 */         this.applet = (Applet)appletObjectInputStream.readObject();
/*  810 */         this.doInit = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  818 */     findAppletJDKLevel(this.applet);
/*      */     
/*  820 */     if (Thread.interrupted()) {
/*      */       try {
/*  822 */         this.status = 0;
/*  823 */         this.applet = null;
/*      */ 
/*      */ 
/*      */         
/*  827 */         showAppletStatus("death");
/*      */       } finally {
/*  829 */         Thread.currentThread().interrupt();
/*      */       } 
/*  831 */       return null;
/*      */     } 
/*  833 */     return this.applet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadJarFiles(AppletClassLoader paramAppletClassLoader) throws IOException, InterruptedException {
/*  841 */     String str = getJarFiles();
/*      */     
/*  843 */     if (str != null) {
/*  844 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",", false);
/*  845 */       while (stringTokenizer.hasMoreTokens()) {
/*  846 */         String str1 = stringTokenizer.nextToken().trim();
/*      */         try {
/*  848 */           paramAppletClassLoader.addJar(str1);
/*  849 */         } catch (IllegalArgumentException illegalArgumentException) {}
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
/*      */   protected synchronized void stopLoading() {
/*  862 */     if (this.loaderThread != null) {
/*      */       
/*  864 */       this.loaderThread.interrupt();
/*      */     } else {
/*  866 */       setLoadAbortRequest();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected synchronized boolean okToLoad() {
/*  872 */     return !this.loadAbortRequest;
/*      */   }
/*      */   
/*      */   protected synchronized void clearLoadAbortRequest() {
/*  876 */     this.loadAbortRequest = false;
/*      */   }
/*      */   
/*      */   protected synchronized void setLoadAbortRequest() {
/*  880 */     this.loadAbortRequest = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized void setLoaderThread(Thread paramThread) {
/*  885 */     this.loaderThread = paramThread;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isActive() {
/*  893 */     return (this.status == 3);
/*      */   }
/*      */ 
/*      */   
/*  897 */   private EventQueue appEvtQ = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appletResize(int paramInt1, int paramInt2) {
/*  903 */     this.currentAppletSize.width = paramInt1;
/*  904 */     this.currentAppletSize.height = paramInt2;
/*  905 */     final Dimension currentSize = new Dimension(this.currentAppletSize.width, this.currentAppletSize.height);
/*      */ 
/*      */     
/*  908 */     if (this.loader != null) {
/*  909 */       AppContext appContext = this.loader.getAppContext();
/*  910 */       if (appContext != null) {
/*  911 */         this.appEvtQ = (EventQueue)appContext.get(AppContext.EVENT_QUEUE_KEY);
/*      */       }
/*      */     } 
/*  914 */     final AppletPanel ap = this;
/*  915 */     if (this.appEvtQ != null) {
/*  916 */       this.appEvtQ.postEvent(new InvocationEvent(Toolkit.getDefaultToolkit(), new Runnable()
/*      */             {
/*      */               public void run()
/*      */               {
/*  920 */                 if (ap != null) {
/*  921 */                   ap.dispatchAppletEvent(51234, currentSize);
/*      */                 }
/*      */               }
/*      */             }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  932 */     super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*  933 */     this.currentAppletSize.width = paramInt3;
/*  934 */     this.currentAppletSize.height = paramInt4;
/*      */   }
/*      */   
/*      */   public Applet getApplet() {
/*  938 */     return this.applet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void showAppletStatus(String paramString) {
/*  946 */     getAppletContext().showStatus(amh.getMessage(paramString));
/*      */   }
/*      */   
/*      */   protected void showAppletStatus(String paramString, Object paramObject) {
/*  950 */     getAppletContext().showStatus(amh.getMessage(paramString, paramObject));
/*      */   }
/*      */   protected void showAppletStatus(String paramString, Object paramObject1, Object paramObject2) {
/*  953 */     getAppletContext().showStatus(amh.getMessage(paramString, paramObject1, paramObject2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void showAppletLog(String paramString) {
/*  960 */     System.out.println(amh.getMessage(paramString));
/*      */   }
/*      */   
/*      */   protected void showAppletLog(String paramString, Object paramObject) {
/*  964 */     System.out.println(amh.getMessage(paramString, paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void showAppletException(Throwable paramThrowable) {
/*  972 */     paramThrowable.printStackTrace();
/*  973 */     repaint();
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
/*      */   public String getClassLoaderCacheKey() {
/*  986 */     return getCodeBase().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  992 */   private static HashMap classloaders = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void flushClassLoader(String paramString) {
/*  998 */     classloaders.remove(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void flushClassLoaders() {
/* 1005 */     classloaders = new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AppletClassLoader createClassLoader(URL paramURL) {
/* 1015 */     return new AppletClassLoader(paramURL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized AppletClassLoader getClassLoader(final URL codebase, final String key) {
/* 1022 */     AppletClassLoader appletClassLoader = (AppletClassLoader)classloaders.get(key);
/* 1023 */     if (appletClassLoader == null) {
/*      */       
/* 1025 */       AccessControlContext accessControlContext = getAccessControlContext(codebase);
/*      */       
/* 1027 */       appletClassLoader = AccessController.<AppletClassLoader>doPrivileged(new PrivilegedAction<AppletClassLoader>()
/*      */           {
/*      */             public Object run() {
/* 1030 */               AppletClassLoader appletClassLoader = AppletPanel.this.createClassLoader(codebase);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1046 */               synchronized (getClass()) {
/*      */                 
/* 1048 */                 AppletClassLoader appletClassLoader1 = (AppletClassLoader)AppletPanel.classloaders.get(key);
/* 1049 */                 if (appletClassLoader1 == null) {
/* 1050 */                   AppletPanel.classloaders.put(key, appletClassLoader);
/* 1051 */                   return appletClassLoader;
/*      */                 } 
/* 1053 */                 return appletClassLoader1;
/*      */               } 
/*      */             }
/*      */           }accessControlContext);
/*      */     } 
/*      */     
/* 1059 */     return appletClassLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AccessControlContext getAccessControlContext(URL paramURL) {
/*      */     Permission permission;
/* 1071 */     PermissionCollection permissionCollection = AccessController.<PermissionCollection>doPrivileged(new PrivilegedAction<PermissionCollection>()
/*      */         {
/*      */           public Object run() {
/* 1074 */             Policy policy = Policy.getPolicy();
/* 1075 */             if (policy != null) {
/* 1076 */               return policy.getPermissions(new CodeSource(null, (Certificate[])null));
/*      */             }
/*      */             
/* 1079 */             return null;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 1084 */     if (permissionCollection == null) {
/* 1085 */       permissionCollection = new Permissions();
/*      */     }
/*      */ 
/*      */     
/* 1089 */     permissionCollection.add(SecurityConstants.CREATE_CLASSLOADER_PERMISSION);
/*      */ 
/*      */     
/* 1092 */     URLConnection uRLConnection = null;
/*      */     try {
/* 1094 */       uRLConnection = paramURL.openConnection();
/* 1095 */       permission = uRLConnection.getPermission();
/* 1096 */     } catch (IOException iOException) {
/* 1097 */       permission = null;
/*      */     } 
/*      */     
/* 1100 */     if (permission != null) {
/* 1101 */       permissionCollection.add(permission);
/*      */     }
/* 1103 */     if (permission instanceof FilePermission) {
/*      */       
/* 1105 */       String str = permission.getName();
/*      */       
/* 1107 */       int i = str.lastIndexOf(File.separatorChar);
/*      */       
/* 1109 */       if (i != -1) {
/* 1110 */         str = str.substring(0, i + 1);
/*      */         
/* 1112 */         if (str.endsWith(File.separator)) {
/* 1113 */           str = str + "-";
/*      */         }
/* 1115 */         permissionCollection.add(new FilePermission(str, "read"));
/*      */       } 
/*      */     } else {
/*      */       
/* 1119 */       URL uRL = paramURL;
/* 1120 */       if (uRLConnection instanceof JarURLConnection) {
/* 1121 */         uRL = ((JarURLConnection)uRLConnection).getJarFileURL();
/*      */       }
/* 1123 */       String str = uRL.getHost();
/* 1124 */       if (str != null && str.length() > 0) {
/* 1125 */         permissionCollection.add(new SocketPermission(str, "connect,accept"));
/*      */       }
/*      */     } 
/*      */     
/* 1129 */     ProtectionDomain protectionDomain = new ProtectionDomain(new CodeSource(paramURL, (Certificate[])null), permissionCollection);
/*      */ 
/*      */     
/* 1132 */     return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Thread getAppletHandlerThread() {
/* 1139 */     return this.handler;
/*      */   }
/*      */   
/*      */   public int getAppletWidth() {
/* 1143 */     return this.currentAppletSize.width;
/*      */   }
/*      */   
/*      */   public int getAppletHeight() {
/* 1147 */     return this.currentAppletSize.height;
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
/*      */   public static void changeFrameAppContext(Frame paramFrame, AppContext paramAppContext) {
/* 1167 */     AppContext appContext = SunToolkit.targetToAppContext(paramFrame);
/*      */     
/* 1169 */     if (appContext == paramAppContext) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1174 */     synchronized (Window.class) {
/*      */       
/* 1176 */       WeakReference<Frame> weakReference = null;
/*      */ 
/*      */ 
/*      */       
/* 1180 */       Vector<WeakReference<Frame>> vector = (Vector)appContext.get(Window.class);
/* 1181 */       if (vector != null) {
/* 1182 */         for (WeakReference<Frame> weakReference1 : (Iterable<WeakReference<Frame>>)vector) {
/* 1183 */           if (weakReference1.get() == paramFrame) {
/* 1184 */             weakReference = weakReference1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1189 */         if (weakReference != null) {
/* 1190 */           vector.remove(weakReference);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1195 */       SunToolkit.insertTargetMapping(paramFrame, paramAppContext);
/*      */ 
/*      */ 
/*      */       
/* 1199 */       vector = (Vector)paramAppContext.get(Window.class);
/* 1200 */       if (vector == null) {
/* 1201 */         vector = new Vector();
/* 1202 */         paramAppContext.put(Window.class, vector);
/*      */       } 
/*      */       
/* 1205 */       vector.add(weakReference);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean jdk11Applet = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean jdk12Applet = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void findAppletJDKLevel(Applet paramApplet) {
/* 1228 */     Class<?> clazz = paramApplet.getClass();
/*      */     
/* 1230 */     synchronized (clazz) {
/*      */ 
/*      */       
/* 1233 */       Boolean bool1 = this.loader.isJDK11Target(clazz);
/* 1234 */       Boolean bool2 = this.loader.isJDK12Target(clazz);
/*      */ 
/*      */ 
/*      */       
/* 1238 */       if (bool1 != null || bool2 != null) {
/* 1239 */         this.jdk11Applet = (bool1 == null) ? false : bool1.booleanValue();
/* 1240 */         this.jdk12Applet = (bool2 == null) ? false : bool2.booleanValue();
/*      */         
/*      */         return;
/*      */       } 
/* 1244 */       String str1 = clazz.getName();
/*      */ 
/*      */       
/* 1247 */       str1 = str1.replace('.', '/');
/*      */ 
/*      */       
/* 1250 */       String str2 = str1 + ".class";
/*      */       
/* 1252 */       byte[] arrayOfByte = new byte[8];
/*      */       
/* 1254 */       try (InputStream null = (InputStream)AccessController.<InputStream>doPrivileged(() -> this.loader.getResourceAsStream(paramString))) {
/*      */ 
/*      */ 
/*      */         
/* 1258 */         int j = inputStream.read(arrayOfByte, 0, 8);
/*      */ 
/*      */ 
/*      */         
/* 1262 */         if (j != 8) {
/*      */           return;
/*      */         }
/* 1265 */       } catch (IOException iOException) {
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/* 1270 */       int i = readShort(arrayOfByte, 6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1278 */       if (i < 46) {
/* 1279 */         this.jdk11Applet = true;
/* 1280 */       } else if (i == 46) {
/* 1281 */         this.jdk12Applet = true;
/*      */       } 
/*      */ 
/*      */       
/* 1285 */       this.loader.setJDK11Target(clazz, this.jdk11Applet);
/* 1286 */       this.loader.setJDK12Target(clazz, this.jdk12Applet);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isJDK11Applet() {
/* 1294 */     return this.jdk11Applet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isJDK12Applet() {
/* 1301 */     return this.jdk12Applet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readShort(byte[] paramArrayOfbyte, int paramInt) {
/* 1308 */     int i = readByte(paramArrayOfbyte[paramInt]);
/* 1309 */     int j = readByte(paramArrayOfbyte[paramInt + 1]);
/* 1310 */     return i << 8 | j;
/*      */   }
/*      */   
/*      */   private int readByte(byte paramByte) {
/* 1314 */     return paramByte & 0xFF;
/*      */   }
/*      */ 
/*      */   
/* 1318 */   private static AppletMessageHandler amh = new AppletMessageHandler("appletpanel");
/*      */   
/*      */   protected abstract String getCode();
/*      */   
/*      */   protected abstract String getJarFiles();
/*      */   
/*      */   protected abstract String getSerializedObject();
/*      */   
/*      */   public abstract int getWidth();
/*      */   
/*      */   public abstract int getHeight();
/*      */   
/*      */   public abstract boolean hasInitialFocus();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */