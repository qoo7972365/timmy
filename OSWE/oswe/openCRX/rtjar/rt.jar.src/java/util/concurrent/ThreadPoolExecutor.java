/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ThreadPoolExecutor
/*      */   extends AbstractExecutorService
/*      */ {
/*  381 */   private final AtomicInteger ctl = new AtomicInteger(ctlOf(-536870912, 0));
/*      */   
/*      */   private static final int COUNT_BITS = 29;
/*      */   private static final int CAPACITY = 536870911;
/*      */   private static final int RUNNING = -536870912;
/*      */   private static final int SHUTDOWN = 0;
/*      */   private static final int STOP = 536870912;
/*      */   private static final int TIDYING = 1073741824;
/*      */   private static final int TERMINATED = 1610612736;
/*      */   private final BlockingQueue<Runnable> workQueue;
/*      */   
/*      */   private static int runStateOf(int paramInt) {
/*  393 */     return paramInt & 0xE0000000; }
/*  394 */   private static int workerCountOf(int paramInt) { return paramInt & 0x1FFFFFFF; } private static int ctlOf(int paramInt1, int paramInt2) {
/*  395 */     return paramInt1 | paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean runStateLessThan(int paramInt1, int paramInt2) {
/*  403 */     return (paramInt1 < paramInt2);
/*      */   }
/*      */   
/*      */   private static boolean runStateAtLeast(int paramInt1, int paramInt2) {
/*  407 */     return (paramInt1 >= paramInt2);
/*      */   }
/*      */   
/*      */   private static boolean isRunning(int paramInt) {
/*  411 */     return (paramInt < 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean compareAndIncrementWorkerCount(int paramInt) {
/*  418 */     return this.ctl.compareAndSet(paramInt, paramInt + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean compareAndDecrementWorkerCount(int paramInt) {
/*  425 */     return this.ctl.compareAndSet(paramInt, paramInt - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void decrementWorkerCount() {
/*      */     do {
/*      */     
/*  434 */     } while (!compareAndDecrementWorkerCount(this.ctl.get()));
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
/*  463 */   private final ReentrantLock mainLock = new ReentrantLock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  469 */   private final HashSet<Worker> workers = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  474 */   private final Condition termination = this.mainLock.newCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int largestPoolSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long completedTaskCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile ThreadFactory threadFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile RejectedExecutionHandler handler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile long keepAliveTime;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile boolean allowCoreThreadTimeOut;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile int corePoolSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile int maximumPoolSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  550 */   private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  573 */   private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final AccessControlContext acc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean ONLY_ONE = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Worker
/*      */     extends AbstractQueuedSynchronizer
/*      */     implements Runnable
/*      */   {
/*      */     private static final long serialVersionUID = 6138294804551838833L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Thread thread;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Runnable firstTask;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile long completedTasks;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Worker(Runnable param1Runnable) {
/*  617 */       setState(-1);
/*  618 */       this.firstTask = param1Runnable;
/*  619 */       this.thread = ThreadPoolExecutor.this.getThreadFactory().newThread(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*  624 */       ThreadPoolExecutor.this.runWorker(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isHeldExclusively() {
/*  633 */       return (getState() != 0);
/*      */     }
/*      */     
/*      */     protected boolean tryAcquire(int param1Int) {
/*  637 */       if (compareAndSetState(0, 1)) {
/*  638 */         setExclusiveOwnerThread(Thread.currentThread());
/*  639 */         return true;
/*      */       } 
/*  641 */       return false;
/*      */     }
/*      */     
/*      */     protected boolean tryRelease(int param1Int) {
/*  645 */       setExclusiveOwnerThread(null);
/*  646 */       setState(0);
/*  647 */       return true;
/*      */     }
/*      */     
/*  650 */     public void lock() { acquire(1); }
/*  651 */     public boolean tryLock() { return tryAcquire(1); }
/*  652 */     public void unlock() { release(1); } public boolean isLocked() {
/*  653 */       return isHeldExclusively();
/*      */     }
/*      */     void interruptIfStarted() {
/*      */       Thread thread;
/*  657 */       if (getState() >= 0 && (thread = this.thread) != null && !thread.isInterrupted()) {
/*      */         try {
/*  659 */           thread.interrupt();
/*  660 */         } catch (SecurityException securityException) {}
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
/*      */   private void advanceRunState(int paramInt) {
/*      */     int i;
/*      */     do {
/*  679 */       i = this.ctl.get();
/*  680 */     } while (!runStateAtLeast(i, paramInt) && 
/*  681 */       !this.ctl.compareAndSet(i, ctlOf(paramInt, workerCountOf(i))));
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
/*      */   final void tryTerminate() {
/*      */     while (true) {
/*  698 */       int i = this.ctl.get();
/*  699 */       if (isRunning(i) || 
/*  700 */         runStateAtLeast(i, 1073741824) || (
/*  701 */         runStateOf(i) == 0 && !this.workQueue.isEmpty()))
/*      */         return; 
/*  703 */       if (workerCountOf(i) != 0) {
/*  704 */         interruptIdleWorkers(true);
/*      */         
/*      */         return;
/*      */       } 
/*  708 */       ReentrantLock reentrantLock = this.mainLock;
/*  709 */       reentrantLock.lock();
/*      */       try {
/*  711 */         if (this.ctl.compareAndSet(i, ctlOf(1073741824, 0))) {
/*      */           try {
/*  713 */             terminated();
/*      */           } finally {
/*  715 */             this.ctl.set(ctlOf(1610612736, 0));
/*  716 */             this.termination.signalAll();
/*      */           } 
/*      */           return;
/*      */         } 
/*      */       } finally {
/*  721 */         reentrantLock.unlock();
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
/*      */   private void checkShutdownAccess() {
/*  740 */     SecurityManager securityManager = System.getSecurityManager();
/*  741 */     if (securityManager != null) {
/*  742 */       securityManager.checkPermission(shutdownPerm);
/*  743 */       ReentrantLock reentrantLock = this.mainLock;
/*  744 */       reentrantLock.lock();
/*      */       try {
/*  746 */         for (Worker worker : this.workers)
/*  747 */           securityManager.checkAccess(worker.thread); 
/*      */       } finally {
/*  749 */         reentrantLock.unlock();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void interruptWorkers() {
/*  759 */     ReentrantLock reentrantLock = this.mainLock;
/*  760 */     reentrantLock.lock();
/*      */     try {
/*  762 */       for (Worker worker : this.workers)
/*  763 */         worker.interruptIfStarted(); 
/*      */     } finally {
/*  765 */       reentrantLock.unlock();
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
/*      */   private void interruptIdleWorkers(boolean paramBoolean) {
/*  789 */     ReentrantLock reentrantLock = this.mainLock;
/*  790 */     reentrantLock.lock();
/*      */     try {
/*  792 */       for (Worker worker : this.workers) {
/*  793 */         Thread thread = worker.thread;
/*  794 */         if (!thread.isInterrupted() && worker.tryLock()) {
/*      */           
/*  796 */           try { thread.interrupt(); }
/*  797 */           catch (SecurityException securityException) {  }
/*      */           finally
/*  799 */           { worker.unlock(); }
/*      */         
/*      */         }
/*  802 */         if (paramBoolean)
/*      */           break; 
/*      */       } 
/*      */     } finally {
/*  806 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void interruptIdleWorkers() {
/*  815 */     interruptIdleWorkers(false);
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
/*      */   final void reject(Runnable paramRunnable) {
/*  830 */     this.handler.rejectedExecution(paramRunnable, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void onShutdown() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isRunningOrShutdown(boolean paramBoolean) {
/*  848 */     int i = runStateOf(this.ctl.get());
/*  849 */     return (i == -536870912 || (i == 0 && paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Runnable> drainQueue() {
/*  859 */     BlockingQueue<Runnable> blockingQueue = this.workQueue;
/*  860 */     ArrayList<? super Runnable> arrayList = new ArrayList();
/*  861 */     blockingQueue.drainTo(arrayList);
/*  862 */     if (!blockingQueue.isEmpty())
/*  863 */       for (Runnable runnable : (Runnable[])blockingQueue.toArray((Object[])new Runnable[0])) {
/*  864 */         if (blockingQueue.remove(runnable)) {
/*  865 */           arrayList.add(runnable);
/*      */         }
/*      */       }  
/*  868 */     return (List)arrayList;
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
/*      */   private boolean addWorker(Runnable paramRunnable, boolean paramBoolean) {
/*      */     label51: while (true) {
/*  904 */       int i = this.ctl.get();
/*  905 */       int j = runStateOf(i);
/*      */ 
/*      */       
/*  908 */       if (j >= 0 && (j != 0 || paramRunnable != null || this.workQueue
/*      */ 
/*      */         
/*  911 */         .isEmpty())) {
/*  912 */         return false;
/*      */       }
/*      */       while (true) {
/*  915 */         int k = workerCountOf(i);
/*  916 */         if (k >= 536870911 || k >= (paramBoolean ? this.corePoolSize : this.maximumPoolSize))
/*      */         {
/*  918 */           return false; } 
/*  919 */         if (compareAndIncrementWorkerCount(i))
/*      */           break; 
/*  921 */         i = this.ctl.get();
/*  922 */         if (runStateOf(i) != j) {
/*      */           continue label51;
/*      */         }
/*      */       } 
/*      */       break;
/*      */     } 
/*  928 */     boolean bool1 = false;
/*  929 */     boolean bool2 = false;
/*  930 */     Worker worker = null;
/*      */     try {
/*  932 */       worker = new Worker(paramRunnable);
/*  933 */       Thread thread = worker.thread;
/*  934 */       if (thread != null) {
/*  935 */         ReentrantLock reentrantLock = this.mainLock;
/*  936 */         reentrantLock.lock();
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  941 */           int i = runStateOf(this.ctl.get());
/*      */           
/*  943 */           if (i < 0 || (i == 0 && paramRunnable == null)) {
/*      */             
/*  945 */             if (thread.isAlive())
/*  946 */               throw new IllegalThreadStateException(); 
/*  947 */             this.workers.add(worker);
/*  948 */             int j = this.workers.size();
/*  949 */             if (j > this.largestPoolSize)
/*  950 */               this.largestPoolSize = j; 
/*  951 */             bool2 = true;
/*      */           } 
/*      */         } finally {
/*  954 */           reentrantLock.unlock();
/*      */         } 
/*  956 */         if (bool2) {
/*  957 */           thread.start();
/*  958 */           bool1 = true;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  962 */       if (!bool1)
/*  963 */         addWorkerFailed(worker); 
/*      */     } 
/*  965 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addWorkerFailed(Worker paramWorker) {
/*  976 */     ReentrantLock reentrantLock = this.mainLock;
/*  977 */     reentrantLock.lock();
/*      */     try {
/*  979 */       if (paramWorker != null)
/*  980 */         this.workers.remove(paramWorker); 
/*  981 */       decrementWorkerCount();
/*  982 */       tryTerminate();
/*      */     } finally {
/*  984 */       reentrantLock.unlock();
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
/*      */   private void processWorkerExit(Worker paramWorker, boolean paramBoolean) {
/* 1002 */     if (paramBoolean) {
/* 1003 */       decrementWorkerCount();
/*      */     }
/* 1005 */     ReentrantLock reentrantLock = this.mainLock;
/* 1006 */     reentrantLock.lock();
/*      */     try {
/* 1008 */       this.completedTaskCount += paramWorker.completedTasks;
/* 1009 */       this.workers.remove(paramWorker);
/*      */     } finally {
/* 1011 */       reentrantLock.unlock();
/*      */     } 
/*      */     
/* 1014 */     tryTerminate();
/*      */     
/* 1016 */     int i = this.ctl.get();
/* 1017 */     if (runStateLessThan(i, 536870912)) {
/* 1018 */       if (!paramBoolean) {
/* 1019 */         byte b = this.allowCoreThreadTimeOut ? 0 : this.corePoolSize;
/* 1020 */         if (!b && !this.workQueue.isEmpty())
/* 1021 */           b = 1; 
/* 1022 */         if (workerCountOf(i) >= b)
/*      */           return; 
/*      */       } 
/* 1025 */       addWorker(null, false);
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
/*      */   private Runnable getTask() {
/* 1047 */     boolean bool = false;
/*      */     
/*      */     while (true) {
/* 1050 */       int i = this.ctl.get();
/* 1051 */       int j = runStateOf(i);
/*      */ 
/*      */       
/* 1054 */       if (j >= 0 && (j >= 536870912 || this.workQueue.isEmpty())) {
/* 1055 */         decrementWorkerCount();
/* 1056 */         return null;
/*      */       } 
/*      */       
/* 1059 */       int k = workerCountOf(i);
/*      */ 
/*      */       
/* 1062 */       boolean bool1 = (this.allowCoreThreadTimeOut || k > this.corePoolSize) ? true : false;
/*      */       
/* 1064 */       if ((k > this.maximumPoolSize || (bool1 && bool)) && (k > 1 || this.workQueue
/* 1065 */         .isEmpty())) {
/* 1066 */         if (compareAndDecrementWorkerCount(i)) {
/* 1067 */           return null;
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*      */       try {
/* 1074 */         Runnable runnable = bool1 ? this.workQueue.poll(this.keepAliveTime, TimeUnit.NANOSECONDS) : this.workQueue.take();
/* 1075 */         if (runnable != null)
/* 1076 */           return runnable; 
/* 1077 */         bool = true;
/* 1078 */       } catch (InterruptedException interruptedException) {
/* 1079 */         bool = false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void runWorker(Worker paramWorker) {
/* 1128 */     Thread thread = Thread.currentThread();
/* 1129 */     Runnable runnable = paramWorker.firstTask;
/* 1130 */     paramWorker.firstTask = null;
/* 1131 */     paramWorker.unlock();
/* 1132 */     boolean bool = true;
/*      */     try {
/* 1134 */       while (runnable != null || (runnable = getTask()) != null) {
/* 1135 */         paramWorker.lock();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1140 */         if ((runStateAtLeast(this.ctl.get(), 536870912) || (
/* 1141 */           Thread.interrupted() && 
/* 1142 */           runStateAtLeast(this.ctl.get(), 536870912))) && 
/* 1143 */           !thread.isInterrupted())
/* 1144 */           thread.interrupt(); 
/*      */         try {
/* 1146 */           beforeExecute(thread, runnable);
/* 1147 */           Throwable throwable = null;
/*      */           try {
/* 1149 */             runnable.run();
/* 1150 */           } catch (RuntimeException runtimeException) {
/* 1151 */             throwable = runtimeException; throw runtimeException;
/* 1152 */           } catch (Error error) {
/* 1153 */             throwable = error; throw error;
/* 1154 */           } catch (Throwable throwable1) {
/* 1155 */             throwable = throwable1; throw new Error(throwable1);
/*      */           } finally {
/* 1157 */             afterExecute(runnable, throwable);
/*      */           } 
/*      */         } finally {
/* 1160 */           runnable = null;
/* 1161 */           paramWorker.completedTasks++;
/* 1162 */           paramWorker.unlock();
/*      */         } 
/*      */       } 
/* 1165 */       bool = false;
/*      */     } finally {
/* 1167 */       processWorkerExit(paramWorker, bool);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue) {
/* 1202 */     this(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, 
/* 1203 */         Executors.defaultThreadFactory(), defaultHandler);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory) {
/* 1237 */     this(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory, defaultHandler);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, RejectedExecutionHandler paramRejectedExecutionHandler) {
/* 1272 */     this(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, 
/* 1273 */         Executors.defaultThreadFactory(), paramRejectedExecutionHandler);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler) {
/* 1310 */     if (paramInt1 < 0 || paramInt2 <= 0 || paramInt2 < paramInt1 || paramLong < 0L)
/*      */     {
/*      */ 
/*      */       
/* 1314 */       throw new IllegalArgumentException(); } 
/* 1315 */     if (paramBlockingQueue == null || paramThreadFactory == null || paramRejectedExecutionHandler == null)
/* 1316 */       throw new NullPointerException(); 
/* 1317 */     this
/*      */       
/* 1319 */       .acc = (System.getSecurityManager() == null) ? null : AccessController.getContext();
/* 1320 */     this.corePoolSize = paramInt1;
/* 1321 */     this.maximumPoolSize = paramInt2;
/* 1322 */     this.workQueue = paramBlockingQueue;
/* 1323 */     this.keepAliveTime = paramTimeUnit.toNanos(paramLong);
/* 1324 */     this.threadFactory = paramThreadFactory;
/* 1325 */     this.handler = paramRejectedExecutionHandler;
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
/*      */   public void execute(Runnable paramRunnable) {
/* 1343 */     if (paramRunnable == null) {
/* 1344 */       throw new NullPointerException();
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
/* 1365 */     int i = this.ctl.get();
/* 1366 */     if (workerCountOf(i) < this.corePoolSize) {
/* 1367 */       if (addWorker(paramRunnable, true))
/*      */         return; 
/* 1369 */       i = this.ctl.get();
/*      */     } 
/* 1371 */     if (isRunning(i) && this.workQueue.offer(paramRunnable)) {
/* 1372 */       int j = this.ctl.get();
/* 1373 */       if (!isRunning(j) && remove(paramRunnable)) {
/* 1374 */         reject(paramRunnable);
/* 1375 */       } else if (workerCountOf(j) == 0) {
/* 1376 */         addWorker(null, false);
/*      */       } 
/* 1378 */     } else if (!addWorker(paramRunnable, false)) {
/* 1379 */       reject(paramRunnable);
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
/*      */   public void shutdown() {
/* 1394 */     ReentrantLock reentrantLock = this.mainLock;
/* 1395 */     reentrantLock.lock();
/*      */     try {
/* 1397 */       checkShutdownAccess();
/* 1398 */       advanceRunState(0);
/* 1399 */       interruptIdleWorkers();
/* 1400 */       onShutdown();
/*      */     } finally {
/* 1402 */       reentrantLock.unlock();
/*      */     } 
/* 1404 */     tryTerminate();
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
/*      */   public List<Runnable> shutdownNow() {
/*      */     List<Runnable> list;
/* 1426 */     ReentrantLock reentrantLock = this.mainLock;
/* 1427 */     reentrantLock.lock();
/*      */     try {
/* 1429 */       checkShutdownAccess();
/* 1430 */       advanceRunState(536870912);
/* 1431 */       interruptWorkers();
/* 1432 */       list = drainQueue();
/*      */     } finally {
/* 1434 */       reentrantLock.unlock();
/*      */     } 
/* 1436 */     tryTerminate();
/* 1437 */     return list;
/*      */   }
/*      */   
/*      */   public boolean isShutdown() {
/* 1441 */     return !isRunning(this.ctl.get());
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
/*      */   public boolean isTerminating() {
/* 1456 */     int i = this.ctl.get();
/* 1457 */     return (!isRunning(i) && runStateLessThan(i, 1610612736));
/*      */   }
/*      */   
/*      */   public boolean isTerminated() {
/* 1461 */     return runStateAtLeast(this.ctl.get(), 1610612736);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 1466 */     long l = paramTimeUnit.toNanos(paramLong);
/* 1467 */     ReentrantLock reentrantLock = this.mainLock;
/* 1468 */     reentrantLock.lock();
/*      */     try {
/*      */       while (true) {
/* 1471 */         if (runStateAtLeast(this.ctl.get(), 1610612736))
/* 1472 */           return true; 
/* 1473 */         if (l <= 0L)
/* 1474 */           return false; 
/* 1475 */         l = this.termination.awaitNanos(l);
/*      */       } 
/*      */     } finally {
/* 1478 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() {
/* 1487 */     SecurityManager securityManager = System.getSecurityManager();
/* 1488 */     if (securityManager == null || this.acc == null) {
/* 1489 */       shutdown();
/*      */     } else {
/* 1491 */       PrivilegedAction<?> privilegedAction = () -> { shutdown(); return null;
/* 1492 */         }; AccessController.doPrivileged(privilegedAction, this.acc);
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
/*      */   public void setThreadFactory(ThreadFactory paramThreadFactory) {
/* 1504 */     if (paramThreadFactory == null)
/* 1505 */       throw new NullPointerException(); 
/* 1506 */     this.threadFactory = paramThreadFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThreadFactory getThreadFactory() {
/* 1516 */     return this.threadFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRejectedExecutionHandler(RejectedExecutionHandler paramRejectedExecutionHandler) {
/* 1527 */     if (paramRejectedExecutionHandler == null)
/* 1528 */       throw new NullPointerException(); 
/* 1529 */     this.handler = paramRejectedExecutionHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RejectedExecutionHandler getRejectedExecutionHandler() {
/* 1539 */     return this.handler;
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
/*      */   public void setCorePoolSize(int paramInt) {
/* 1554 */     if (paramInt < 0)
/* 1555 */       throw new IllegalArgumentException(); 
/* 1556 */     int i = paramInt - this.corePoolSize;
/* 1557 */     this.corePoolSize = paramInt;
/* 1558 */     if (workerCountOf(this.ctl.get()) > paramInt) {
/* 1559 */       interruptIdleWorkers();
/* 1560 */     } else if (i > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1565 */       int j = Math.min(i, this.workQueue.size()); do {  }
/* 1566 */       while (j-- > 0 && addWorker(null, true) && 
/* 1567 */         !this.workQueue.isEmpty());
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
/*      */   public int getCorePoolSize() {
/* 1580 */     return this.corePoolSize;
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
/*      */   public boolean prestartCoreThread() {
/* 1592 */     return (workerCountOf(this.ctl.get()) < this.corePoolSize && 
/* 1593 */       addWorker(null, true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void ensurePrestart() {
/* 1601 */     int i = workerCountOf(this.ctl.get());
/* 1602 */     if (i < this.corePoolSize) {
/* 1603 */       addWorker(null, true);
/* 1604 */     } else if (i == 0) {
/* 1605 */       addWorker(null, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int prestartAllCoreThreads() {
/* 1616 */     byte b = 0;
/* 1617 */     while (addWorker(null, true))
/* 1618 */       b++; 
/* 1619 */     return b;
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
/*      */   public boolean allowsCoreThreadTimeOut() {
/* 1636 */     return this.allowCoreThreadTimeOut;
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
/*      */   public void allowCoreThreadTimeOut(boolean paramBoolean) {
/* 1657 */     if (paramBoolean && this.keepAliveTime <= 0L)
/* 1658 */       throw new IllegalArgumentException("Core threads must have nonzero keep alive times"); 
/* 1659 */     if (paramBoolean != this.allowCoreThreadTimeOut) {
/* 1660 */       this.allowCoreThreadTimeOut = paramBoolean;
/* 1661 */       if (paramBoolean) {
/* 1662 */         interruptIdleWorkers();
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
/*      */   public void setMaximumPoolSize(int paramInt) {
/* 1679 */     if (paramInt <= 0 || paramInt < this.corePoolSize)
/* 1680 */       throw new IllegalArgumentException(); 
/* 1681 */     this.maximumPoolSize = paramInt;
/* 1682 */     if (workerCountOf(this.ctl.get()) > paramInt) {
/* 1683 */       interruptIdleWorkers();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumPoolSize() {
/* 1693 */     return this.maximumPoolSize;
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
/*      */   public void setKeepAliveTime(long paramLong, TimeUnit paramTimeUnit) {
/* 1711 */     if (paramLong < 0L)
/* 1712 */       throw new IllegalArgumentException(); 
/* 1713 */     if (paramLong == 0L && allowsCoreThreadTimeOut())
/* 1714 */       throw new IllegalArgumentException("Core threads must have nonzero keep alive times"); 
/* 1715 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 1716 */     long l2 = l1 - this.keepAliveTime;
/* 1717 */     this.keepAliveTime = l1;
/* 1718 */     if (l2 < 0L) {
/* 1719 */       interruptIdleWorkers();
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
/*      */   public long getKeepAliveTime(TimeUnit paramTimeUnit) {
/* 1732 */     return paramTimeUnit.convert(this.keepAliveTime, TimeUnit.NANOSECONDS);
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
/*      */   public BlockingQueue<Runnable> getQueue() {
/* 1746 */     return this.workQueue;
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
/*      */   public boolean remove(Runnable paramRunnable) {
/* 1766 */     boolean bool = this.workQueue.remove(paramRunnable);
/* 1767 */     tryTerminate();
/* 1768 */     return bool;
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
/*      */   public void purge() {
/* 1782 */     BlockingQueue<Runnable> blockingQueue = this.workQueue;
/*      */     try {
/* 1784 */       Iterator<Runnable> iterator = blockingQueue.iterator();
/* 1785 */       while (iterator.hasNext()) {
/* 1786 */         Runnable runnable = iterator.next();
/* 1787 */         if (runnable instanceof Future && ((Future)runnable).isCancelled())
/* 1788 */           iterator.remove(); 
/*      */       } 
/* 1790 */     } catch (ConcurrentModificationException concurrentModificationException) {
/*      */ 
/*      */ 
/*      */       
/* 1794 */       for (Object object : blockingQueue.toArray()) {
/* 1795 */         if (object instanceof Future && ((Future)object).isCancelled())
/* 1796 */           blockingQueue.remove(object); 
/*      */       } 
/*      */     } 
/* 1799 */     tryTerminate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPoolSize() {
/* 1810 */     ReentrantLock reentrantLock = this.mainLock;
/* 1811 */     reentrantLock.lock();
/*      */ 
/*      */     
/*      */     try {
/* 1815 */       return runStateAtLeast(this.ctl.get(), 1073741824) ? 0 : this.workers
/* 1816 */         .size();
/*      */     } finally {
/* 1818 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActiveCount() {
/* 1829 */     ReentrantLock reentrantLock = this.mainLock;
/* 1830 */     reentrantLock.lock();
/*      */     try {
/* 1832 */       byte b = 0;
/* 1833 */       for (Worker worker : this.workers) {
/* 1834 */         if (worker.isLocked())
/* 1835 */           b++; 
/* 1836 */       }  return b;
/*      */     } finally {
/* 1838 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLargestPoolSize() {
/* 1849 */     ReentrantLock reentrantLock = this.mainLock;
/* 1850 */     reentrantLock.lock();
/*      */     try {
/* 1852 */       return this.largestPoolSize;
/*      */     } finally {
/* 1854 */       reentrantLock.unlock();
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
/*      */   public long getTaskCount() {
/* 1867 */     ReentrantLock reentrantLock = this.mainLock;
/* 1868 */     reentrantLock.lock();
/*      */     try {
/* 1870 */       long l = this.completedTaskCount;
/* 1871 */       for (Worker worker : this.workers) {
/* 1872 */         l += worker.completedTasks;
/* 1873 */         if (worker.isLocked())
/* 1874 */           l++; 
/*      */       } 
/* 1876 */       return l + this.workQueue.size();
/*      */     } finally {
/* 1878 */       reentrantLock.unlock();
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
/*      */   public long getCompletedTaskCount() {
/* 1892 */     ReentrantLock reentrantLock = this.mainLock;
/* 1893 */     reentrantLock.lock();
/*      */     try {
/* 1895 */       long l = this.completedTaskCount;
/* 1896 */       for (Worker worker : this.workers)
/* 1897 */         l += worker.completedTasks; 
/* 1898 */       return l;
/*      */     } finally {
/* 1900 */       reentrantLock.unlock();
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
/*      */     long l;
/*      */     int i;
/*      */     byte b;
/* 1914 */     ReentrantLock reentrantLock = this.mainLock;
/* 1915 */     reentrantLock.lock();
/*      */     try {
/* 1917 */       l = this.completedTaskCount;
/* 1918 */       b = 0;
/* 1919 */       i = this.workers.size();
/* 1920 */       for (Worker worker : this.workers) {
/* 1921 */         l += worker.completedTasks;
/* 1922 */         if (worker.isLocked())
/* 1923 */           b++; 
/*      */       } 
/*      */     } finally {
/* 1926 */       reentrantLock.unlock();
/*      */     } 
/* 1928 */     int j = this.ctl.get();
/*      */     
/* 1930 */     String str = runStateLessThan(j, 0) ? "Running" : (runStateAtLeast(j, 1610612736) ? "Terminated" : "Shutting down");
/*      */     
/* 1932 */     return super.toString() + "[" + str + ", pool size = " + i + ", active threads = " + b + ", queued tasks = " + this.workQueue
/*      */ 
/*      */ 
/*      */       
/* 1936 */       .size() + ", completed tasks = " + l + "]";
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
/*      */   protected void beforeExecute(Thread paramThread, Runnable paramRunnable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void afterExecute(Runnable paramRunnable, Throwable paramThrowable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void terminated() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CallerRunsPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable param1Runnable, ThreadPoolExecutor param1ThreadPoolExecutor) {
/* 2037 */       if (!param1ThreadPoolExecutor.isShutdown()) {
/* 2038 */         param1Runnable.run();
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
/*      */   public static class AbortPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable param1Runnable, ThreadPoolExecutor param1ThreadPoolExecutor) {
/* 2061 */       throw new RejectedExecutionException("Task " + param1Runnable.toString() + " rejected from " + param1ThreadPoolExecutor
/*      */           
/* 2063 */           .toString());
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
/*      */   public static class DiscardPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable param1Runnable, ThreadPoolExecutor param1ThreadPoolExecutor) {}
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
/*      */   public static class DiscardOldestPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable param1Runnable, ThreadPoolExecutor param1ThreadPoolExecutor) {
/* 2108 */       if (!param1ThreadPoolExecutor.isShutdown()) {
/* 2109 */         param1ThreadPoolExecutor.getQueue().poll();
/* 2110 */         param1ThreadPoolExecutor.execute(param1Runnable);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ThreadPoolExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */