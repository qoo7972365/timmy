/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.SystemTray;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.TrayIcon;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.InvocationEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.function.Supplier;
/*     */ import sun.misc.JavaAWTAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.util.logging.PlatformLogger;
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
/*     */ public final class AppContext
/*     */ {
/* 136 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.AppContext");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static final Object EVENT_QUEUE_KEY = new StringBuffer("EventQueue");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static final Object EVENT_QUEUE_LOCK_KEY = new StringBuilder("EventQueue.Lock");
/* 150 */   public static final Object EVENT_QUEUE_COND_KEY = new StringBuilder("EventQueue.Condition");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   private static final Map<ThreadGroup, AppContext> threadGroup2appContext = Collections.synchronizedMap(new IdentityHashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<AppContext> getAppContexts() {
/* 161 */     synchronized (threadGroup2appContext) {
/* 162 */       return new HashSet<>(threadGroup2appContext.values());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   private static volatile AppContext mainAppContext = null;
/*     */   private static class GetAppContextLock {
/*     */     private GetAppContextLock() {} }
/* 173 */   private static final Object getAppContextLock = new GetAppContextLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   private final Map<Object, Object> table = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ThreadGroup threadGroup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   private PropertyChangeSupport changeSupport = null;
/*     */   public static final String DISPOSED_PROPERTY_NAME = "disposed";
/*     */   public static final String GUI_DISPOSED = "guidisposed";
/*     */   
/*     */   private enum State
/*     */   {
/* 199 */     VALID,
/* 200 */     BEING_DISPOSED,
/* 201 */     DISPOSED;
/*     */   }
/*     */   
/* 204 */   private volatile State state = State.VALID;
/*     */   
/*     */   public boolean isDisposed() {
/* 207 */     return (this.state == State.DISPOSED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   private static final AtomicInteger numAppContexts = new AtomicInteger(0);
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
/*     */   private final ClassLoader contextClassLoader;
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
/* 259 */   private static final ThreadLocal<AppContext> threadAppContext = new ThreadLocal<>();
/*     */   private long DISPOSAL_TIMEOUT;
/*     */   private long THREAD_INTERRUPT_TIMEOUT;
/*     */   private MostRecentKeyValue mostRecentKeyValue;
/*     */   private MostRecentKeyValue shadowMostRecentKeyValue;
/*     */   
/*     */   private static final void initMainAppContext() {
/* 266 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 269 */             ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 270 */             ThreadGroup threadGroup2 = threadGroup1.getParent();
/* 271 */             while (threadGroup2 != null) {
/*     */               
/* 273 */               threadGroup1 = threadGroup2;
/* 274 */               threadGroup2 = threadGroup1.getParent();
/*     */             } 
/*     */             
/* 277 */             AppContext.mainAppContext = SunToolkit.createNewAppContext(threadGroup1);
/* 278 */             return null;
/*     */           }
/*     */         });
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
/*     */   
/*     */   public static final AppContext getAppContext() {
/* 296 */     if (numAppContexts.get() == 1 && mainAppContext != null) {
/* 297 */       return mainAppContext;
/*     */     }
/*     */     
/* 300 */     AppContext appContext = threadAppContext.get();
/*     */     
/* 302 */     if (null == appContext) {
/* 303 */       appContext = AccessController.<AppContext>doPrivileged(new PrivilegedAction<AppContext>()
/*     */           {
/*     */ 
/*     */ 
/*     */             
/*     */             public AppContext run()
/*     */             {
/* 310 */               ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 311 */               ThreadGroup threadGroup2 = threadGroup1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 317 */               synchronized (AppContext.getAppContextLock) {
/* 318 */                 if (AppContext.numAppContexts.get() == 0) {
/* 319 */                   if (System.getProperty("javaplugin.version") == null && 
/* 320 */                     System.getProperty("javawebstart.version") == null) {
/* 321 */                     AppContext.initMainAppContext();
/* 322 */                   } else if (System.getProperty("javafx.version") != null && threadGroup2
/* 323 */                     .getParent() != null) {
/*     */                     
/* 325 */                     SunToolkit.createNewAppContext();
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */               
/* 330 */               AppContext appContext = (AppContext)AppContext.threadGroup2appContext.get(threadGroup2);
/* 331 */               while (appContext == null) {
/* 332 */                 threadGroup2 = threadGroup2.getParent();
/* 333 */                 if (threadGroup2 == null) {
/*     */ 
/*     */                   
/* 336 */                   SecurityManager securityManager = System.getSecurityManager();
/* 337 */                   if (securityManager != null) {
/* 338 */                     ThreadGroup threadGroup = securityManager.getThreadGroup();
/* 339 */                     if (threadGroup != null)
/*     */                     {
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 345 */                       return (AppContext)AppContext.threadGroup2appContext.get(threadGroup);
/*     */                     }
/*     */                   } 
/* 348 */                   return null;
/*     */                 } 
/* 350 */                 appContext = (AppContext)AppContext.threadGroup2appContext.get(threadGroup2);
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 356 */               for (ThreadGroup threadGroup3 = threadGroup1; threadGroup3 != threadGroup2; threadGroup3 = threadGroup3.getParent()) {
/* 357 */                 AppContext.threadGroup2appContext.put(threadGroup3, appContext);
/*     */               }
/*     */ 
/*     */               
/* 361 */               AppContext.threadAppContext.set(appContext);
/*     */               
/* 363 */               return appContext;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 368 */     return appContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean isMainContext(AppContext paramAppContext) {
/* 379 */     return (paramAppContext != null && paramAppContext == mainAppContext);
/*     */   }
/*     */   
/*     */   private static final AppContext getExecutionAppContext() {
/* 383 */     SecurityManager securityManager = System.getSecurityManager();
/* 384 */     if (securityManager != null && securityManager instanceof AWTSecurityManager) {
/*     */ 
/*     */       
/* 387 */       AWTSecurityManager aWTSecurityManager = (AWTSecurityManager)securityManager;
/* 388 */       return aWTSecurityManager.getAppContext();
/*     */     } 
/*     */     
/* 391 */     return null;
/*     */   } public void dispose() throws IllegalThreadStateException { if (this.threadGroup.parentOf(Thread.currentThread().getThreadGroup())) throw new IllegalThreadStateException("Current Thread is contained within AppContext to be disposed.");  synchronized (this) { if (this.state != State.VALID) return;  this.state = State.BEING_DISPOSED; }  final PropertyChangeSupport changeSupport = this.changeSupport; if (propertyChangeSupport != null) propertyChangeSupport.firePropertyChange("disposed", false, true);  final Object notificationLock = new Object(); Runnable runnable = new Runnable() { public void run() { Window[] arrayOfWindow = Window.getOwnerlessWindows(); for (Window window : arrayOfWindow) { try { window.dispose(); } catch (Throwable throwable) { AppContext.log.finer("exception occurred while disposing app context", throwable); }  }  AccessController.doPrivileged(new PrivilegedAction<Void>() { public Void run() { if (!GraphicsEnvironment.isHeadless() && SystemTray.isSupported()) { SystemTray systemTray = SystemTray.getSystemTray(); TrayIcon[] arrayOfTrayIcon = systemTray.getTrayIcons(); for (TrayIcon trayIcon : arrayOfTrayIcon) systemTray.remove(trayIcon);  }  return null; } }
/*     */             ); if (changeSupport != null) changeSupport.firePropertyChange("guidisposed", false, true);  synchronized (notificationLock) { notificationLock.notifyAll(); }  } }
/* 394 */       ; synchronized (object) { SunToolkit.postEvent(this, new InvocationEvent(Toolkit.getDefaultToolkit(), runnable)); try { object.wait(this.DISPOSAL_TIMEOUT); } catch (InterruptedException interruptedException) {} }  runnable = new Runnable() { public void run() { synchronized (notificationLock) { notificationLock.notifyAll(); }  } }; synchronized (object) { SunToolkit.postEvent(this, new InvocationEvent(Toolkit.getDefaultToolkit(), runnable)); try { object.wait(this.DISPOSAL_TIMEOUT); } catch (InterruptedException interruptedException) {} }  synchronized (this) { this.state = State.DISPOSED; }  this.threadGroup.interrupt(); long l1 = System.currentTimeMillis(); long l2 = l1 + this.THREAD_INTERRUPT_TIMEOUT; while (this.threadGroup.activeCount() > 0 && System.currentTimeMillis() < l2) { try { Thread.sleep(10L); } catch (InterruptedException interruptedException) {} }  this.threadGroup.stop(); l1 = System.currentTimeMillis(); l2 = l1 + this.THREAD_INTERRUPT_TIMEOUT; while (this.threadGroup.activeCount() > 0 && System.currentTimeMillis() < l2) { try { Thread.sleep(10L); } catch (InterruptedException interruptedException) {} }  int i = this.threadGroup.activeGroupCount(); if (i > 0) { ThreadGroup[] arrayOfThreadGroup = new ThreadGroup[i]; i = this.threadGroup.enumerate(arrayOfThreadGroup); for (byte b = 0; b < i; b++) threadGroup2appContext.remove(arrayOfThreadGroup[b]);  }  threadGroup2appContext.remove(this.threadGroup); threadAppContext.set(null); try { this.threadGroup.destroy(); } catch (IllegalThreadStateException illegalThreadStateException) {} synchronized (this.table) { this.table.clear(); }  numAppContexts.decrementAndGet(); this.mostRecentKeyValue = null; } AppContext(ThreadGroup paramThreadGroup) { this.DISPOSAL_TIMEOUT = 5000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     this.THREAD_INTERRUPT_TIMEOUT = 1000L;
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
/* 619 */     this.mostRecentKeyValue = null;
/* 620 */     this.shadowMostRecentKeyValue = null;
/*     */     numAppContexts.incrementAndGet();
/*     */     this.threadGroup = paramThreadGroup;
/*     */     threadGroup2appContext.put(paramThreadGroup, this);
/*     */     this.contextClassLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public ClassLoader run() { return Thread.currentThread().getContextClassLoader(); }
/*     */         });
/*     */     ReentrantLock reentrantLock = new ReentrantLock();
/*     */     put(EVENT_QUEUE_LOCK_KEY, reentrantLock);
/*     */     Condition condition = reentrantLock.newCondition();
/*     */     put(EVENT_QUEUE_COND_KEY, condition); } static final class PostShutdownEventRunnable implements Runnable {
/*     */     private final AppContext appContext; public PostShutdownEventRunnable(AppContext param1AppContext) { this.appContext = param1AppContext; } public void run() {
/*     */       EventQueue eventQueue = (EventQueue)this.appContext.get(AppContext.EVENT_QUEUE_KEY);
/*     */       if (eventQueue != null)
/*     */         eventQueue.postEvent(AWTAutoShutdown.getShutdownEvent()); 
/*     */     }
/*     */   } public Object get(Object paramObject) {
/* 637 */     synchronized (this.table) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 644 */       MostRecentKeyValue mostRecentKeyValue = this.mostRecentKeyValue;
/* 645 */       if (mostRecentKeyValue != null && mostRecentKeyValue.key == paramObject) {
/* 646 */         return mostRecentKeyValue.value;
/*     */       }
/*     */       
/* 649 */       Object object = this.table.get(paramObject);
/* 650 */       if (this.mostRecentKeyValue == null) {
/* 651 */         this.mostRecentKeyValue = new MostRecentKeyValue(paramObject, object);
/* 652 */         this.shadowMostRecentKeyValue = new MostRecentKeyValue(paramObject, object);
/*     */       } else {
/* 654 */         MostRecentKeyValue mostRecentKeyValue1 = this.mostRecentKeyValue;
/* 655 */         this.shadowMostRecentKeyValue.setPair(paramObject, object);
/* 656 */         this.mostRecentKeyValue = this.shadowMostRecentKeyValue;
/* 657 */         this.shadowMostRecentKeyValue = mostRecentKeyValue1;
/*     */       } 
/* 659 */       return object;
/*     */     }  } static final class CreateThreadAction implements PrivilegedAction<Thread> {
/*     */     private final AppContext appContext; private final Runnable runnable; public CreateThreadAction(AppContext param1AppContext, Runnable param1Runnable) { this.appContext = param1AppContext;
/*     */       this.runnable = param1Runnable; }
/*     */     public Thread run() { Thread thread = new Thread(this.appContext.getThreadGroup(), this.runnable);
/*     */       thread.setContextClassLoader(this.appContext.getContextClassLoader());
/*     */       thread.setPriority(6);
/*     */       thread.setDaemon(true);
/*     */       return thread; } }
/*     */   static void stopEventDispatchThreads() { for (AppContext appContext : getAppContexts()) {
/*     */       if (appContext.isDisposed())
/*     */         continue; 
/*     */       PostShutdownEventRunnable postShutdownEventRunnable = new PostShutdownEventRunnable(appContext);
/*     */       if (appContext != getAppContext()) {
/*     */         CreateThreadAction createThreadAction = new CreateThreadAction(appContext, postShutdownEventRunnable);
/*     */         Thread thread = AccessController.<Thread>doPrivileged(createThreadAction);
/*     */         thread.start();
/*     */         continue;
/*     */       } 
/*     */       postShutdownEventRunnable.run();
/*     */     }  }
/*     */   public Object put(Object paramObject1, Object paramObject2) {
/* 681 */     synchronized (this.table) {
/* 682 */       MostRecentKeyValue mostRecentKeyValue = this.mostRecentKeyValue;
/* 683 */       if (mostRecentKeyValue != null && mostRecentKeyValue.key == paramObject1)
/* 684 */         mostRecentKeyValue.value = paramObject2; 
/* 685 */       return this.table.put(paramObject1, paramObject2);
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
/*     */ 
/*     */   
/*     */   public Object remove(Object paramObject) {
/* 700 */     synchronized (this.table) {
/* 701 */       MostRecentKeyValue mostRecentKeyValue = this.mostRecentKeyValue;
/* 702 */       if (mostRecentKeyValue != null && mostRecentKeyValue.key == paramObject)
/* 703 */         mostRecentKeyValue.value = null; 
/* 704 */       return this.table.remove(paramObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadGroup getThreadGroup() {
/* 714 */     return this.threadGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getContextClassLoader() {
/* 724 */     return this.contextClassLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 733 */     return getClass().getName() + "[threadGroup=" + this.threadGroup.getName() + "]";
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
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
/* 751 */     if (this.changeSupport == null) {
/* 752 */       return new PropertyChangeListener[0];
/*     */     }
/* 754 */     return this.changeSupport.getPropertyChangeListeners();
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
/*     */   public synchronized void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 783 */     if (paramPropertyChangeListener == null) {
/*     */       return;
/*     */     }
/* 786 */     if (this.changeSupport == null) {
/* 787 */       this.changeSupport = new PropertyChangeSupport(this);
/*     */     }
/* 789 */     this.changeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 809 */     if (paramPropertyChangeListener == null || this.changeSupport == null) {
/*     */       return;
/*     */     }
/* 812 */     this.changeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized PropertyChangeListener[] getPropertyChangeListeners(String paramString) {
/* 830 */     if (this.changeSupport == null) {
/* 831 */       return new PropertyChangeListener[0];
/*     */     }
/* 833 */     return this.changeSupport.getPropertyChangeListeners(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 838 */     SharedSecrets.setJavaAWTAccess(new JavaAWTAccess() {
/*     */           private boolean hasRootThreadGroup(final AppContext ecx) {
/* 840 */             return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */                 {
/*     */                   public Boolean run() {
/* 843 */                     return Boolean.valueOf((ecx.threadGroup.getParent() == null));
/*     */                   }
/*     */                 })).booleanValue();
/*     */           }
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
/*     */           public Object getAppletContext() {
/* 863 */             if (AppContext.numAppContexts.get() == 0) return null;
/*     */ 
/*     */             
/* 866 */             AppContext appContext = AppContext.getExecutionAppContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 876 */             if (AppContext.numAppContexts.get() > 0)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 883 */               appContext = (appContext != null) ? appContext : AppContext.getAppContext();
/*     */             }
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
/* 895 */             boolean bool = (appContext == null || AppContext.mainAppContext == appContext || (AppContext.mainAppContext == null && hasRootThreadGroup(appContext))) ? true : false;
/*     */             
/* 897 */             return bool ? null : appContext;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T getSoftReferenceValue(Object paramObject, Supplier<T> paramSupplier) {
/* 906 */     AppContext appContext = getAppContext();
/* 907 */     SoftReference<Object> softReference = (SoftReference)appContext.get(paramObject);
/* 908 */     if (softReference != null) {
/* 909 */       T t1 = (T)softReference.get();
/* 910 */       if (t1 != null) {
/* 911 */         return t1;
/*     */       }
/*     */     } 
/* 914 */     T t = paramSupplier.get();
/* 915 */     softReference = new SoftReference<>((Object)t);
/* 916 */     appContext.put(paramObject, softReference);
/* 917 */     return t;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/AppContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */