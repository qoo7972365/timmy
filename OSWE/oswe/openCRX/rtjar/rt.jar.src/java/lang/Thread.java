/*      */ package java.lang;
/*      */ 
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.misc.Contended;
/*      */ import sun.misc.VM;
/*      */ import sun.nio.ch.Interruptible;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Thread
/*      */   implements Runnable
/*      */ {
/*      */   private volatile String name;
/*      */   private int priority;
/*      */   private Thread threadQ;
/*      */   private long eetop;
/*      */   private boolean single_step;
/*      */   
/*      */   static {
/*  145 */     registerNatives();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean daemon = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean stillborn = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private Runnable target;
/*      */ 
/*      */ 
/*      */   
/*      */   private ThreadGroup group;
/*      */ 
/*      */   
/*      */   private ClassLoader contextClassLoader;
/*      */ 
/*      */   
/*      */   private AccessControlContext inheritedAccessControlContext;
/*      */ 
/*      */   
/*      */   private static int threadInitNumber;
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized int nextThreadNum() {
/*  177 */     return threadInitNumber++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  182 */   ThreadLocal.ThreadLocalMap threadLocals = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long stackSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long nativeParkEventPointer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long tid;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long threadSeqNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  214 */   private volatile int threadStatus = 0; volatile Object parkBlocker;
/*      */   private volatile Interruptible blocker;
/*      */   
/*      */   private static synchronized long nextThreadID() {
/*  218 */     return ++threadSeqNumber;
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
/*  234 */   private final Object blockerLock = new Object(); public static final int MIN_PRIORITY = 1;
/*      */   public static final int NORM_PRIORITY = 5;
/*      */   public static final int MAX_PRIORITY = 10;
/*      */   
/*      */   void blockedOn(Interruptible paramInterruptible) {
/*  239 */     synchronized (this.blockerLock) {
/*  240 */       this.blocker = paramInterruptible;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sleep(long paramLong, int paramInt) throws InterruptedException {
/*  327 */     if (paramLong < 0L) {
/*  328 */       throw new IllegalArgumentException("timeout value is negative");
/*      */     }
/*      */     
/*  331 */     if (paramInt < 0 || paramInt > 999999) {
/*  332 */       throw new IllegalArgumentException("nanosecond timeout value out of range");
/*      */     }
/*      */ 
/*      */     
/*  336 */     if (paramInt >= 500000 || (paramInt != 0 && paramLong == 0L)) {
/*  337 */       paramLong++;
/*      */     }
/*      */     
/*  340 */     sleep(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong) {
/*  349 */     init(paramThreadGroup, paramRunnable, paramString, paramLong, null, true);
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
/*      */   private void init(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong, AccessControlContext paramAccessControlContext, boolean paramBoolean) {
/*  368 */     if (paramString == null) {
/*  369 */       throw new NullPointerException("name cannot be null");
/*      */     }
/*      */     
/*  372 */     this.name = paramString;
/*      */     
/*  374 */     Thread thread = currentThread();
/*  375 */     SecurityManager securityManager = System.getSecurityManager();
/*  376 */     if (paramThreadGroup == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  381 */       if (securityManager != null) {
/*  382 */         paramThreadGroup = securityManager.getThreadGroup();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  387 */       if (paramThreadGroup == null) {
/*  388 */         paramThreadGroup = thread.getThreadGroup();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  394 */     paramThreadGroup.checkAccess();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  399 */     if (securityManager != null && 
/*  400 */       isCCLOverridden(getClass())) {
/*  401 */       securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */     }
/*      */ 
/*      */     
/*  405 */     paramThreadGroup.addUnstarted();
/*      */     
/*  407 */     this.group = paramThreadGroup;
/*  408 */     this.daemon = thread.isDaemon();
/*  409 */     this.priority = thread.getPriority();
/*  410 */     if (securityManager == null || isCCLOverridden(thread.getClass())) {
/*  411 */       this.contextClassLoader = thread.getContextClassLoader();
/*      */     } else {
/*  413 */       this.contextClassLoader = thread.contextClassLoader;
/*  414 */     }  this
/*  415 */       .inheritedAccessControlContext = (paramAccessControlContext != null) ? paramAccessControlContext : AccessController.getContext();
/*  416 */     this.target = paramRunnable;
/*  417 */     setPriority(this.priority);
/*  418 */     if (paramBoolean && thread.inheritableThreadLocals != null) {
/*  419 */       this
/*  420 */         .inheritableThreadLocals = ThreadLocal.createInheritedMap(thread.inheritableThreadLocals);
/*      */     }
/*  422 */     this.stackSize = paramLong;
/*      */ 
/*      */     
/*  425 */     this.tid = nextThreadID();
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
/*      */   protected Object clone() throws CloneNotSupportedException {
/*  437 */     throw new CloneNotSupportedException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Thread() {
/*  448 */     init(null, null, "Thread-" + nextThreadNum(), 0L);
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
/*      */   public Thread(Runnable paramRunnable) {
/*  464 */     init(null, paramRunnable, "Thread-" + nextThreadNum(), 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Thread(Runnable paramRunnable, AccessControlContext paramAccessControlContext) {
/*  472 */     init(null, paramRunnable, "Thread-" + nextThreadNum(), 0L, paramAccessControlContext, false);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable) {
/*  499 */     init(paramThreadGroup, paramRunnable, "Thread-" + nextThreadNum(), 0L);
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
/*      */   public Thread(String paramString) {
/*  511 */     init(null, null, paramString, 0L);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, String paramString) {
/*  535 */     init(paramThreadGroup, null, paramString, 0L);
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
/*      */   public Thread(Runnable paramRunnable, String paramString) {
/*  551 */     init(null, paramRunnable, paramString, 0L);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString) {
/*  599 */     init(paramThreadGroup, paramRunnable, paramString, 0L);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong) {
/*  678 */     init(paramThreadGroup, paramRunnable, paramString, paramLong);
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
/*      */   public synchronized void start() {
/*  707 */     if (this.threadStatus != 0) {
/*  708 */       throw new IllegalThreadStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  713 */     this.group.add(this);
/*      */     
/*  715 */     boolean bool = false;
/*      */     try {
/*  717 */       start0();
/*  718 */       bool = true;
/*      */     } finally {
/*      */       try {
/*  721 */         if (!bool) {
/*  722 */           this.group.threadStartFailed(this);
/*      */         }
/*  724 */       } catch (Throwable throwable) {}
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
/*      */   public void run() {
/*  747 */     if (this.target != null) {
/*  748 */       this.target.run();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void exit() {
/*  757 */     if (this.group != null) {
/*  758 */       this.group.threadTerminated(this);
/*  759 */       this.group = null;
/*      */     } 
/*      */     
/*  762 */     this.target = null;
/*      */     
/*  764 */     this.threadLocals = null;
/*  765 */     this.inheritableThreadLocals = null;
/*  766 */     this.inheritedAccessControlContext = null;
/*  767 */     this.blocker = null;
/*  768 */     this.uncaughtExceptionHandler = null;
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
/*      */   @Deprecated
/*      */   public final void stop() {
/*  839 */     SecurityManager securityManager = System.getSecurityManager();
/*  840 */     if (securityManager != null) {
/*  841 */       checkAccess();
/*  842 */       if (this != currentThread()) {
/*  843 */         securityManager.checkPermission(SecurityConstants.STOP_THREAD_PERMISSION);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  848 */     if (this.threadStatus != 0) {
/*  849 */       resume();
/*      */     }
/*      */ 
/*      */     
/*  853 */     stop0(new ThreadDeath());
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
/*      */   @Deprecated
/*      */   public final synchronized void stop(Throwable paramThrowable) {
/*  872 */     throw new UnsupportedOperationException();
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
/*      */   public void interrupt() {
/*  915 */     if (this != currentThread()) {
/*  916 */       checkAccess();
/*      */     }
/*  918 */     synchronized (this.blockerLock) {
/*  919 */       Interruptible interruptible = this.blocker;
/*  920 */       if (interruptible != null) {
/*  921 */         interrupt0();
/*  922 */         interruptible.interrupt(this);
/*      */         return;
/*      */       } 
/*      */     } 
/*  926 */     interrupt0();
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
/*      */   public static boolean interrupted() {
/*  947 */     return currentThread().isInterrupted(true);
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
/*      */   public boolean isInterrupted() {
/*  964 */     return isInterrupted(false);
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
/*      */   @Deprecated
/*      */   public void destroy() {
/*  993 */     throw new NoSuchMethodError();
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
/*      */   @Deprecated
/*      */   public final void suspend() {
/* 1031 */     checkAccess();
/* 1032 */     suspend0();
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
/*      */   public final void resume() {
/* 1057 */     checkAccess();
/* 1058 */     resume0();
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
/*      */   public final void setPriority(int paramInt) {
/* 1087 */     checkAccess();
/* 1088 */     if (paramInt > 10 || paramInt < 1)
/* 1089 */       throw new IllegalArgumentException(); 
/*      */     ThreadGroup threadGroup;
/* 1091 */     if ((threadGroup = getThreadGroup()) != null) {
/* 1092 */       if (paramInt > threadGroup.getMaxPriority()) {
/* 1093 */         paramInt = threadGroup.getMaxPriority();
/*      */       }
/* 1095 */       setPriority0(this.priority = paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getPriority() {
/* 1106 */     return this.priority;
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
/*      */   public final synchronized void setName(String paramString) {
/* 1124 */     checkAccess();
/* 1125 */     if (paramString == null) {
/* 1126 */       throw new NullPointerException("name cannot be null");
/*      */     }
/*      */     
/* 1129 */     this.name = paramString;
/* 1130 */     if (this.threadStatus != 0) {
/* 1131 */       setNativeName(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getName() {
/* 1142 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final ThreadGroup getThreadGroup() {
/* 1153 */     return this.group;
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
/*      */   public static int activeCount() {
/* 1173 */     return currentThread().getThreadGroup().activeCount();
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
/*      */   public static int enumerate(Thread[] paramArrayOfThread) {
/* 1203 */     return currentThread().getThreadGroup().enumerate(paramArrayOfThread);
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
/*      */   public final synchronized void join(long paramLong) throws InterruptedException {
/* 1243 */     long l1 = System.currentTimeMillis();
/* 1244 */     long l2 = 0L;
/*      */     
/* 1246 */     if (paramLong < 0L) {
/* 1247 */       throw new IllegalArgumentException("timeout value is negative");
/*      */     }
/*      */     
/* 1250 */     if (paramLong == 0L) {
/* 1251 */       while (isAlive()) {
/* 1252 */         wait(0L);
/*      */       }
/*      */     } else {
/* 1255 */       while (isAlive()) {
/* 1256 */         long l = paramLong - l2;
/* 1257 */         if (l <= 0L) {
/*      */           break;
/*      */         }
/* 1260 */         wait(l);
/* 1261 */         l2 = System.currentTimeMillis() - l1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void join(long paramLong, int paramInt) throws InterruptedException {
/* 1294 */     if (paramLong < 0L) {
/* 1295 */       throw new IllegalArgumentException("timeout value is negative");
/*      */     }
/*      */     
/* 1298 */     if (paramInt < 0 || paramInt > 999999) {
/* 1299 */       throw new IllegalArgumentException("nanosecond timeout value out of range");
/*      */     }
/*      */ 
/*      */     
/* 1303 */     if (paramInt >= 500000 || (paramInt != 0 && paramLong == 0L)) {
/* 1304 */       paramLong++;
/*      */     }
/*      */     
/* 1307 */     join(paramLong);
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
/*      */   public final void join() throws InterruptedException {
/* 1326 */     join(0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dumpStack() {
/* 1336 */     (new Exception("Stack trace")).printStackTrace();
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
/*      */   public final void setDaemon(boolean paramBoolean) {
/* 1357 */     checkAccess();
/* 1358 */     if (isAlive()) {
/* 1359 */       throw new IllegalThreadStateException();
/*      */     }
/* 1361 */     this.daemon = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isDaemon() {
/* 1372 */     return this.daemon;
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
/*      */   public final void checkAccess() {
/* 1388 */     SecurityManager securityManager = System.getSecurityManager();
/* 1389 */     if (securityManager != null) {
/* 1390 */       securityManager.checkAccess(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1401 */     ThreadGroup threadGroup = getThreadGroup();
/* 1402 */     if (threadGroup != null) {
/* 1403 */       return "Thread[" + getName() + "," + getPriority() + "," + threadGroup
/* 1404 */         .getName() + "]";
/*      */     }
/* 1406 */     return "Thread[" + getName() + "," + getPriority() + ",]";
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
/*      */   @CallerSensitive
/*      */   public ClassLoader getContextClassLoader() {
/* 1439 */     if (this.contextClassLoader == null)
/* 1440 */       return null; 
/* 1441 */     SecurityManager securityManager = System.getSecurityManager();
/* 1442 */     if (securityManager != null) {
/* 1443 */       ClassLoader.checkClassLoaderPermission(this.contextClassLoader, 
/* 1444 */           Reflection.getCallerClass());
/*      */     }
/* 1446 */     return this.contextClassLoader;
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
/*      */   public void setContextClassLoader(ClassLoader paramClassLoader) {
/* 1472 */     SecurityManager securityManager = System.getSecurityManager();
/* 1473 */     if (securityManager != null) {
/* 1474 */       securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
/*      */     }
/* 1476 */     this.contextClassLoader = paramClassLoader;
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
/* 1497 */   private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StackTraceElement[] getStackTrace() {
/* 1537 */     if (this != currentThread()) {
/*      */       
/* 1539 */       SecurityManager securityManager = System.getSecurityManager();
/* 1540 */       if (securityManager != null) {
/* 1541 */         securityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1546 */       if (!isAlive()) {
/* 1547 */         return EMPTY_STACK_TRACE;
/*      */       }
/* 1549 */       StackTraceElement[][] arrayOfStackTraceElement = dumpThreads(new Thread[] { this });
/* 1550 */       StackTraceElement[] arrayOfStackTraceElement1 = arrayOfStackTraceElement[0];
/*      */ 
/*      */       
/* 1553 */       if (arrayOfStackTraceElement1 == null) {
/* 1554 */         arrayOfStackTraceElement1 = EMPTY_STACK_TRACE;
/*      */       }
/* 1556 */       return arrayOfStackTraceElement1;
/*      */     } 
/*      */     
/* 1559 */     return (new Exception()).getStackTrace();
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
/*      */   public static Map<Thread, StackTraceElement[]> getAllStackTraces() {
/* 1600 */     SecurityManager securityManager = System.getSecurityManager();
/* 1601 */     if (securityManager != null) {
/* 1602 */       securityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
/*      */       
/* 1604 */       securityManager.checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1609 */     Thread[] arrayOfThread = getThreads();
/* 1610 */     StackTraceElement[][] arrayOfStackTraceElement = dumpThreads(arrayOfThread);
/* 1611 */     HashMap<Object, Object> hashMap = new HashMap<>(arrayOfThread.length);
/* 1612 */     for (byte b = 0; b < arrayOfThread.length; b++) {
/* 1613 */       StackTraceElement[] arrayOfStackTraceElement1 = arrayOfStackTraceElement[b];
/* 1614 */       if (arrayOfStackTraceElement1 != null) {
/* 1615 */         hashMap.put(arrayOfThread[b], arrayOfStackTraceElement1);
/*      */       }
/*      */     } 
/*      */     
/* 1619 */     return (Map)hashMap;
/*      */   }
/*      */   private volatile UncaughtExceptionHandler uncaughtExceptionHandler; private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler; @Contended("tlr")
/*      */   long threadLocalRandomSeed;
/* 1623 */   private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION = new RuntimePermission("enableContextClassLoaderOverride");
/*      */   @Contended("tlr")
/*      */   int threadLocalRandomProbe;
/*      */   @Contended("tlr")
/*      */   int threadLocalRandomSecondarySeed;
/*      */   
/*      */   private static class Caches
/*      */   {
/* 1631 */     static final ConcurrentMap<Thread.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */     
/* 1635 */     static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isCCLOverridden(Class<?> paramClass) {
/* 1646 */     if (paramClass == Thread.class) {
/* 1647 */       return false;
/*      */     }
/* 1649 */     processQueue(Caches.subclassAuditsQueue, (ConcurrentMap)Caches.subclassAudits);
/* 1650 */     WeakClassKey weakClassKey = new WeakClassKey(paramClass, Caches.subclassAuditsQueue);
/* 1651 */     Boolean bool = Caches.subclassAudits.get(weakClassKey);
/* 1652 */     if (bool == null) {
/* 1653 */       bool = Boolean.valueOf(auditSubclass(paramClass));
/* 1654 */       Caches.subclassAudits.putIfAbsent(weakClassKey, bool);
/*      */     } 
/*      */     
/* 1657 */     return bool.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean auditSubclass(final Class<?> subcl) {
/* 1666 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */         {
/*      */           public Boolean run() {
/* 1669 */             Class<Thread> clazz = subcl;
/* 1670 */             for (; clazz != Thread.class; 
/* 1671 */               clazz = (Class)clazz.getSuperclass()) {
/*      */               
/*      */               try {
/* 1674 */                 clazz.getDeclaredMethod("getContextClassLoader", new Class[0]);
/* 1675 */                 return Boolean.TRUE;
/* 1676 */               } catch (NoSuchMethodException noSuchMethodException) {
/*      */                 
/*      */                 try {
/* 1679 */                   Class[] arrayOfClass = { ClassLoader.class };
/* 1680 */                   clazz.getDeclaredMethod("setContextClassLoader", arrayOfClass);
/* 1681 */                   return Boolean.TRUE;
/* 1682 */                 } catch (NoSuchMethodException noSuchMethodException1) {}
/*      */               } 
/*      */             } 
/* 1685 */             return Boolean.FALSE;
/*      */           }
/*      */         });
/*      */     
/* 1689 */     return bool.booleanValue();
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
/*      */   public long getId() {
/* 1705 */     return this.tid;
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
/*      */   public enum State
/*      */   {
/* 1746 */     NEW,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1754 */     RUNNABLE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1763 */     BLOCKED,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1784 */     WAITING,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1798 */     TIMED_WAITING,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1804 */     TERMINATED;
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
/*      */   public State getState() {
/* 1817 */     return VM.toThreadState(this.threadStatus);
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
/*      */   public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler paramUncaughtExceptionHandler) {
/* 1898 */     SecurityManager securityManager = System.getSecurityManager();
/* 1899 */     if (securityManager != null) {
/* 1900 */       securityManager.checkPermission(new RuntimePermission("setDefaultUncaughtExceptionHandler"));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1905 */     defaultUncaughtExceptionHandler = paramUncaughtExceptionHandler;
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
/*      */   public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
/* 1917 */     return defaultUncaughtExceptionHandler;
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
/*      */   public UncaughtExceptionHandler getUncaughtExceptionHandler() {
/* 1930 */     return (this.uncaughtExceptionHandler != null) ? this.uncaughtExceptionHandler : this.group;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void registerNatives();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static native Thread currentThread();
/*      */ 
/*      */ 
/*      */   
/*      */   public static native void yield();
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUncaughtExceptionHandler(UncaughtExceptionHandler paramUncaughtExceptionHandler) {
/* 1950 */     checkAccess();
/* 1951 */     this.uncaughtExceptionHandler = paramUncaughtExceptionHandler;
/*      */   }
/*      */   
/*      */   public static native void sleep(long paramLong) throws InterruptedException;
/*      */   
/*      */   private native void start0();
/*      */   
/*      */   private void dispatchUncaughtException(Throwable paramThrowable) {
/* 1959 */     getUncaughtExceptionHandler().uncaughtException(this, paramThrowable);
/*      */   }
/*      */   private native boolean isInterrupted(boolean paramBoolean);
/*      */   public final native boolean isAlive();
/*      */   
/*      */   @Deprecated
/*      */   public native int countStackFrames();
/*      */   
/*      */   public static native boolean holdsLock(Object paramObject);
/*      */   
/*      */   static void processQueue(ReferenceQueue<Class<?>> paramReferenceQueue, ConcurrentMap<? extends WeakReference<Class<?>>, ?> paramConcurrentMap) {
/*      */     Reference<? extends Class<?>> reference;
/* 1971 */     while ((reference = paramReferenceQueue.poll()) != null)
/* 1972 */       paramConcurrentMap.remove(reference); 
/*      */   }
/*      */   private static native StackTraceElement[][] dumpThreads(Thread[] paramArrayOfThread);
/*      */   
/*      */   private static native Thread[] getThreads();
/*      */   
/*      */   private native void setPriority0(int paramInt);
/*      */   
/*      */   private native void stop0(Object paramObject);
/*      */   
/*      */   private native void suspend0();
/*      */   
/*      */   private native void resume0();
/*      */   
/*      */   private native void interrupt0();
/*      */   
/*      */   private native void setNativeName(String paramString);
/*      */   
/*      */   static class WeakClassKey extends WeakReference<Class<?>> { WeakClassKey(Class<?> param1Class, ReferenceQueue<Class<?>> param1ReferenceQueue) {
/* 1991 */       super(param1Class, param1ReferenceQueue);
/* 1992 */       this.hash = System.identityHashCode(param1Class);
/*      */     }
/*      */ 
/*      */     
/*      */     private final int hash;
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2000 */       return this.hash;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2011 */       if (param1Object == this) {
/* 2012 */         return true;
/*      */       }
/* 2014 */       if (param1Object instanceof WeakClassKey) {
/* 2015 */         Class<?> clazz = get();
/* 2016 */         return (clazz != null && clazz == ((WeakClassKey)param1Object)
/* 2017 */           .get());
/*      */       } 
/* 2019 */       return false;
/*      */     } }
/*      */ 
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface UncaughtExceptionHandler {
/*      */     void uncaughtException(Thread param1Thread, Throwable param1Throwable);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Thread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */