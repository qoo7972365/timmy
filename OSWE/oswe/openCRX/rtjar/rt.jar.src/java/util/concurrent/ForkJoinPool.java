/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permissions;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import sun.misc.Contended;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Contended
/*      */ public class ForkJoinPool
/*      */   extends AbstractExecutorService
/*      */ {
/*      */   static final int SMASK = 65535;
/*      */   static final int MAX_CAP = 32767;
/*      */   static final int EVENMASK = 65534;
/*      */   static final int SQMASK = 126;
/*      */   static final int SCANNING = 1;
/*      */   static final int INACTIVE = -2147483648;
/*      */   static final int SS_SEQ = 65536;
/*      */   static final int MODE_MASK = -65536;
/*      */   static final int LIFO_QUEUE = 0;
/*      */   static final int FIFO_QUEUE = 65536;
/*      */   static final int SHARED_QUEUE = -2147483648;
/*      */   public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
/*      */   private static final RuntimePermission modifyThreadPermission;
/*      */   static final ForkJoinPool common;
/*      */   static final int commonParallelism;
/*      */   
/*      */   private static void checkPermission() {
/*  700 */     SecurityManager securityManager = System.getSecurityManager();
/*  701 */     if (securityManager != null) {
/*  702 */       securityManager.checkPermission(modifyThreadPermission);
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
/*      */   static final class DefaultForkJoinWorkerThreadFactory
/*      */     implements ForkJoinWorkerThreadFactory
/*      */   {
/*      */     public final ForkJoinWorkerThread newThread(ForkJoinPool param1ForkJoinPool) {
/*  731 */       return new ForkJoinWorkerThread(param1ForkJoinPool);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class EmptyTask
/*      */     extends ForkJoinTask<Void>
/*      */   {
/*      */     private static final long serialVersionUID = -7721805057305804111L;
/*      */ 
/*      */     
/*      */     public final Void getRawResult() {
/*  744 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {} public final boolean exec() {
/*  746 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Contended
/*      */   static final class WorkQueue
/*      */   {
/*      */     static final int INITIAL_QUEUE_CAPACITY = 8192;
/*      */ 
/*      */     
/*      */     static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
/*      */ 
/*      */     
/*      */     volatile int scanState;
/*      */ 
/*      */     
/*      */     int stackPred;
/*      */ 
/*      */     
/*      */     int nsteals;
/*      */ 
/*      */     
/*      */     int hint;
/*      */ 
/*      */     
/*      */     int config;
/*      */ 
/*      */     
/*      */     volatile int qlock;
/*      */ 
/*      */     
/*      */     volatile int base;
/*      */ 
/*      */     
/*      */     int top;
/*      */ 
/*      */     
/*      */     ForkJoinTask<?>[] array;
/*      */ 
/*      */     
/*      */     final ForkJoinPool pool;
/*      */ 
/*      */     
/*      */     final ForkJoinWorkerThread owner;
/*      */ 
/*      */     
/*      */     volatile Thread parker;
/*      */ 
/*      */     
/*      */     volatile ForkJoinTask<?> currentJoin;
/*      */ 
/*      */     
/*      */     volatile ForkJoinTask<?> currentSteal;
/*      */ 
/*      */     
/*      */     private static final Unsafe U;
/*      */     
/*      */     private static final int ABASE;
/*      */     
/*      */     private static final int ASHIFT;
/*      */     
/*      */     private static final long QTOP;
/*      */     
/*      */     private static final long QLOCK;
/*      */     
/*      */     private static final long QCURRENTSTEAL;
/*      */ 
/*      */     
/*      */     WorkQueue(ForkJoinPool param1ForkJoinPool, ForkJoinWorkerThread param1ForkJoinWorkerThread) {
/*  817 */       this.pool = param1ForkJoinPool;
/*  818 */       this.owner = param1ForkJoinWorkerThread;
/*      */       
/*  820 */       this.base = this.top = 4096;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int getPoolIndex() {
/*  827 */       return (this.config & 0xFFFF) >>> 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int queueSize() {
/*  834 */       int i = this.base - this.top;
/*  835 */       return (i >= 0) ? 0 : -i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean isEmpty() {
/*      */       ForkJoinTask<?>[] arrayOfForkJoinTask;
/*      */       int i;
/*      */       int j;
/*      */       int k;
/*  845 */       return ((i = this.base - (k = this.top)) >= 0 || (i == -1 && ((arrayOfForkJoinTask = this.array) == null || (j = arrayOfForkJoinTask.length - 1) < 0 || U
/*      */ 
/*      */ 
/*      */         
/*  849 */         .getObject(arrayOfForkJoinTask, ((j & k - 1) << ASHIFT) + ABASE) == null)));
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
/*      */     final void push(ForkJoinTask<?> param1ForkJoinTask) {
/*  861 */       int i = this.base, j = this.top; ForkJoinTask<?>[] arrayOfForkJoinTask;
/*  862 */       if ((arrayOfForkJoinTask = this.array) != null) {
/*  863 */         int m = arrayOfForkJoinTask.length - 1;
/*  864 */         U.putOrderedObject(arrayOfForkJoinTask, (((m & j) << ASHIFT) + ABASE), param1ForkJoinTask);
/*  865 */         U.putOrderedInt(this, QTOP, j + 1); int k;
/*  866 */         if ((k = j - i) <= 1) {
/*  867 */           ForkJoinPool forkJoinPool; if ((forkJoinPool = this.pool) != null) {
/*  868 */             forkJoinPool.signalWork(forkJoinPool.workQueues, this);
/*      */           }
/*  870 */         } else if (k >= m) {
/*  871 */           growArray();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ForkJoinTask<?>[] growArray() {
/*  881 */       ForkJoinTask<?>[] arrayOfForkJoinTask1 = this.array;
/*  882 */       byte b = (arrayOfForkJoinTask1 != null) ? (arrayOfForkJoinTask1.length << 1) : 8192;
/*  883 */       if (b > 67108864) {
/*  884 */         throw new RejectedExecutionException("Queue capacity exceeded");
/*      */       }
/*  886 */       ForkJoinTask<?>[] arrayOfForkJoinTask2 = this.array = (ForkJoinTask<?>[])new ForkJoinTask[b]; int i, j, k;
/*  887 */       if (arrayOfForkJoinTask1 != null && (i = arrayOfForkJoinTask1.length - 1) >= 0 && (j = this.top) - (k = this.base) > 0) {
/*      */         
/*  889 */         int m = b - 1;
/*      */         
/*      */         do {
/*  892 */           int n = ((k & i) << ASHIFT) + ABASE;
/*  893 */           int i1 = ((k & m) << ASHIFT) + ABASE;
/*  894 */           ForkJoinTask forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask1, n);
/*  895 */           if (forkJoinTask == null || 
/*  896 */             !U.compareAndSwapObject(arrayOfForkJoinTask1, n, forkJoinTask, null))
/*  897 */             continue;  U.putObjectVolatile(arrayOfForkJoinTask2, i1, forkJoinTask);
/*  898 */         } while (++k != j);
/*      */       } 
/*  900 */       return arrayOfForkJoinTask2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ForkJoinTask<?> pop() {
/*      */       ForkJoinTask<?>[] arrayOfForkJoinTask;
/*      */       int i;
/*  909 */       if ((arrayOfForkJoinTask = this.array) != null && (i = arrayOfForkJoinTask.length - 1) >= 0) {
/*      */         
/*  911 */         long l = (((i & j) << ASHIFT) + ABASE); ForkJoinTask<?> forkJoinTask; int j;
/*  912 */         while ((j = this.top - 1) - this.base >= 0 && (forkJoinTask = (ForkJoinTask)U.getObject(arrayOfForkJoinTask, l)) != null) {
/*      */           
/*  914 */           if (U.compareAndSwapObject(arrayOfForkJoinTask, l, forkJoinTask, null)) {
/*  915 */             U.putOrderedInt(this, QTOP, j);
/*  916 */             return forkJoinTask;
/*      */           } 
/*      */         } 
/*      */       } 
/*  920 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ForkJoinTask<?> pollAt(int param1Int) {
/*  931 */       int i = ((arrayOfForkJoinTask.length - 1 & param1Int) << ASHIFT) + ABASE; ForkJoinTask<?> forkJoinTask, arrayOfForkJoinTask[];
/*  932 */       if ((arrayOfForkJoinTask = this.array) != null && (forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, i)) != null && this.base == param1Int && U
/*  933 */         .compareAndSwapObject(arrayOfForkJoinTask, i, forkJoinTask, null)) {
/*  934 */         this.base = param1Int + 1;
/*  935 */         return forkJoinTask;
/*      */       } 
/*      */       
/*  938 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final ForkJoinTask<?> poll() {
/*      */       ForkJoinTask<?>[] arrayOfForkJoinTask;
/*      */       int i;
/*  946 */       while ((i = this.base) - this.top < 0 && (arrayOfForkJoinTask = this.array) != null) {
/*  947 */         int j = ((arrayOfForkJoinTask.length - 1 & i) << ASHIFT) + ABASE;
/*  948 */         ForkJoinTask<?> forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, j);
/*  949 */         if (this.base == i) {
/*  950 */           if (forkJoinTask != null) {
/*  951 */             if (U.compareAndSwapObject(arrayOfForkJoinTask, j, forkJoinTask, null)) {
/*  952 */               this.base = i + 1;
/*  953 */               return forkJoinTask;
/*      */             }  continue;
/*      */           } 
/*  956 */           if (i + 1 == this.top)
/*      */             break; 
/*      */         } 
/*      */       } 
/*  960 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ForkJoinTask<?> nextLocalTask() {
/*  967 */       return ((this.config & 0x10000) == 0) ? pop() : poll();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ForkJoinTask<?> peek() {
/*  974 */       ForkJoinTask<?>[] arrayOfForkJoinTask = this.array; int i;
/*  975 */       if (arrayOfForkJoinTask == null || (i = arrayOfForkJoinTask.length - 1) < 0)
/*  976 */         return null; 
/*  977 */       int j = ((this.config & 0x10000) == 0) ? (this.top - 1) : this.base;
/*  978 */       int k = ((j & i) << ASHIFT) + ABASE;
/*  979 */       return (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, k);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean tryUnpush(ForkJoinTask<?> param1ForkJoinTask) {
/*      */       ForkJoinTask<?>[] arrayOfForkJoinTask;
/*      */       int i;
/*  988 */       if ((arrayOfForkJoinTask = this.array) != null && (i = this.top) != this.base && U
/*      */         
/*  990 */         .compareAndSwapObject(arrayOfForkJoinTask, (((arrayOfForkJoinTask.length - 1 & --i) << ASHIFT) + ABASE), param1ForkJoinTask, null)) {
/*  991 */         U.putOrderedInt(this, QTOP, i);
/*  992 */         return true;
/*      */       } 
/*  994 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void cancelAll() {
/*      */       ForkJoinTask<?> forkJoinTask;
/* 1002 */       if ((forkJoinTask = this.currentJoin) != null) {
/* 1003 */         this.currentJoin = null;
/* 1004 */         ForkJoinTask.cancelIgnoringExceptions(forkJoinTask);
/*      */       } 
/* 1006 */       if ((forkJoinTask = this.currentSteal) != null) {
/* 1007 */         this.currentSteal = null;
/* 1008 */         ForkJoinTask.cancelIgnoringExceptions(forkJoinTask);
/*      */       } 
/* 1010 */       while ((forkJoinTask = poll()) != null) {
/* 1011 */         ForkJoinTask.cancelIgnoringExceptions(forkJoinTask);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void pollAndExecAll() {
/*      */       ForkJoinTask<?> forkJoinTask;
/* 1020 */       while ((forkJoinTask = poll()) != null) {
/* 1021 */         forkJoinTask.doExec();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void execLocalTasks() {
/* 1030 */       int i = this.base;
/* 1031 */       ForkJoinTask<?>[] arrayOfForkJoinTask = this.array; int j, k;
/* 1032 */       if (i - (k = this.top - 1) <= 0 && arrayOfForkJoinTask != null && (j = arrayOfForkJoinTask.length - 1) >= 0)
/*      */       {
/* 1034 */         if ((this.config & 0x10000) == 0) {
/*      */           ForkJoinTask forkJoinTask;
/*      */           
/* 1037 */           while ((forkJoinTask = (ForkJoinTask)U.getAndSetObject(arrayOfForkJoinTask, (((j & k) << ASHIFT) + ABASE), null)) != null) {
/*      */             
/* 1039 */             U.putOrderedInt(this, QTOP, k);
/* 1040 */             forkJoinTask.doExec();
/* 1041 */             if (this.base - (k = this.top - 1) > 0) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */         } else {
/* 1046 */           pollAndExecAll();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void runTask(ForkJoinTask<?> param1ForkJoinTask) {
/* 1054 */       if (param1ForkJoinTask != null) {
/* 1055 */         this.scanState &= 0xFFFFFFFE;
/* 1056 */         (this.currentSteal = param1ForkJoinTask).doExec();
/* 1057 */         U.putOrderedObject(this, QCURRENTSTEAL, null);
/* 1058 */         execLocalTasks();
/* 1059 */         ForkJoinWorkerThread forkJoinWorkerThread = this.owner;
/* 1060 */         if (++this.nsteals < 0)
/* 1061 */           transferStealCount(this.pool); 
/* 1062 */         this.scanState |= 0x1;
/* 1063 */         if (forkJoinWorkerThread != null) {
/* 1064 */           forkJoinWorkerThread.afterTopLevelExec();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void transferStealCount(ForkJoinPool param1ForkJoinPool) {
/*      */       AtomicLong atomicLong;
/* 1073 */       if (param1ForkJoinPool != null && (atomicLong = param1ForkJoinPool.stealCounter) != null) {
/* 1074 */         int i = this.nsteals;
/* 1075 */         this.nsteals = 0;
/* 1076 */         atomicLong.getAndAdd(((i < 0) ? 2147483647L : i));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean tryRemoveAndExec(ForkJoinTask<?> param1ForkJoinTask) {
/*      */       ForkJoinTask<?>[] arrayOfForkJoinTask;
/*      */       int i;
/* 1088 */       if ((arrayOfForkJoinTask = this.array) != null && (i = arrayOfForkJoinTask.length - 1) >= 0 && param1ForkJoinTask != null) {
/*      */         int j; int k; int m;
/* 1090 */         while ((m = (j = this.top) - (k = this.base)) > 0) {
/*      */           while (true) {
/* 1092 */             long l = (((--j & i) << ASHIFT) + ABASE); ForkJoinTask<?> forkJoinTask;
/* 1093 */             if ((forkJoinTask = (ForkJoinTask)U.getObject(arrayOfForkJoinTask, l)) == null)
/* 1094 */               return (j + 1 == this.top); 
/* 1095 */             if (forkJoinTask == param1ForkJoinTask) {
/* 1096 */               boolean bool = false;
/* 1097 */               if (j + 1 == this.top) {
/* 1098 */                 if (U.compareAndSwapObject(arrayOfForkJoinTask, l, param1ForkJoinTask, null)) {
/* 1099 */                   U.putOrderedInt(this, QTOP, j);
/* 1100 */                   bool = true;
/*      */                 }
/*      */               
/* 1103 */               } else if (this.base == k) {
/* 1104 */                 bool = U.compareAndSwapObject(arrayOfForkJoinTask, l, param1ForkJoinTask, new ForkJoinPool.EmptyTask());
/*      */               } 
/* 1106 */               if (bool)
/* 1107 */                 param1ForkJoinTask.doExec(); 
/*      */               break;
/*      */             } 
/* 1110 */             if (forkJoinTask.status < 0 && j + 1 == this.top) {
/* 1111 */               if (U.compareAndSwapObject(arrayOfForkJoinTask, l, forkJoinTask, null))
/* 1112 */                 U.putOrderedInt(this, QTOP, j); 
/*      */               break;
/*      */             } 
/* 1115 */             if (--m == 0)
/* 1116 */               return false; 
/*      */           } 
/* 1118 */           if (param1ForkJoinTask.status < 0)
/* 1119 */             return false; 
/*      */         } 
/*      */       } 
/* 1122 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final CountedCompleter<?> popCC(CountedCompleter<?> param1CountedCompleter, int param1Int) {
/* 1132 */       long l = (((arrayOfForkJoinTask.length - 1 & i - 1) << ASHIFT) + ABASE); int i; ForkJoinTask<?>[] arrayOfForkJoinTask; Object object;
/* 1133 */       if (this.base - (i = this.top) < 0 && (arrayOfForkJoinTask = this.array) != null && (object = U.getObjectVolatile(arrayOfForkJoinTask, l)) != null && object instanceof CountedCompleter) {
/*      */         
/* 1135 */         CountedCompleter<?> countedCompleter1 = (CountedCompleter)object;
/* 1136 */         CountedCompleter<?> countedCompleter2 = countedCompleter1; do {
/* 1137 */           if (countedCompleter2 == param1CountedCompleter) {
/* 1138 */             if (param1Int < 0) {
/* 1139 */               if (U.compareAndSwapInt(this, QLOCK, 0, 1)) {
/* 1140 */                 if (this.top == i && this.array == arrayOfForkJoinTask && U
/* 1141 */                   .compareAndSwapObject(arrayOfForkJoinTask, l, countedCompleter1, null)) {
/* 1142 */                   U.putOrderedInt(this, QTOP, i - 1);
/* 1143 */                   U.putOrderedInt(this, QLOCK, 0);
/* 1144 */                   return countedCompleter1;
/*      */                 } 
/* 1146 */                 U.compareAndSwapInt(this, QLOCK, 1, 0);
/*      */               }  break;
/*      */             } 
/* 1149 */             if (U.compareAndSwapObject(arrayOfForkJoinTask, l, countedCompleter1, null)) {
/* 1150 */               U.putOrderedInt(this, QTOP, i - 1);
/* 1151 */               return countedCompleter1;
/*      */             } 
/*      */             break;
/*      */           } 
/* 1155 */         } while ((countedCompleter2 = countedCompleter2.completer) != null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1160 */       return null;
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
/*      */     final int pollAndExecCC(CountedCompleter<?> param1CountedCompleter) {
/*      */       byte b;
/*      */       int i;
/*      */       ForkJoinTask<?>[] arrayOfForkJoinTask;
/* 1175 */       if ((i = this.base) - this.top >= 0 || (arrayOfForkJoinTask = this.array) == null) {
/* 1176 */         b = i | Integer.MIN_VALUE;
/*      */       } else {
/* 1178 */         long l = (((arrayOfForkJoinTask.length - 1 & i) << ASHIFT) + ABASE); Object object;
/* 1179 */         if ((object = U.getObjectVolatile(arrayOfForkJoinTask, l)) == null) {
/* 1180 */           b = 2;
/* 1181 */         } else if (!(object instanceof CountedCompleter)) {
/* 1182 */           b = -1;
/*      */         } else {
/* 1184 */           CountedCompleter<?> countedCompleter1 = (CountedCompleter)object;
/* 1185 */           CountedCompleter<?> countedCompleter2 = countedCompleter1; while (true) {
/* 1186 */             if (countedCompleter2 == param1CountedCompleter) {
/* 1187 */               if (this.base == i && U
/* 1188 */                 .compareAndSwapObject(arrayOfForkJoinTask, l, countedCompleter1, null)) {
/* 1189 */                 this.base = i + 1;
/* 1190 */                 countedCompleter1.doExec();
/* 1191 */                 boolean bool = true;
/*      */                 break;
/*      */               } 
/* 1194 */               b = 2;
/*      */               break;
/*      */             } 
/* 1197 */             if ((countedCompleter2 = countedCompleter2.completer) == null) {
/* 1198 */               b = -1;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1204 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean isApparentlyUnblocked() {
/*      */       ForkJoinWorkerThread forkJoinWorkerThread;
/*      */       Thread.State state;
/* 1212 */       return (this.scanState >= 0 && (forkJoinWorkerThread = this.owner) != null && (
/*      */         
/* 1214 */         state = forkJoinWorkerThread.getState()) != Thread.State.BLOCKED && state != Thread.State.WAITING && state != Thread.State.TIMED_WAITING);
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
/*      */     static {
/*      */       try {
/* 1228 */         U = Unsafe.getUnsafe();
/* 1229 */         Class<WorkQueue> clazz = WorkQueue.class;
/* 1230 */         Class<ForkJoinTask[]> clazz1 = ForkJoinTask[].class;
/*      */         
/* 1232 */         QTOP = U.objectFieldOffset(clazz.getDeclaredField("top"));
/*      */         
/* 1234 */         QLOCK = U.objectFieldOffset(clazz.getDeclaredField("qlock"));
/*      */         
/* 1236 */         QCURRENTSTEAL = U.objectFieldOffset(clazz.getDeclaredField("currentSteal"));
/* 1237 */         ABASE = U.arrayBaseOffset(clazz1);
/* 1238 */         int i = U.arrayIndexScale(clazz1);
/* 1239 */         if ((i & i - 1) != 0)
/* 1240 */           throw new Error("data type scale not a power of two"); 
/* 1241 */         ASHIFT = 31 - Integer.numberOfLeadingZeros(i);
/* 1242 */       } catch (Exception exception) {
/* 1243 */         throw new Error(exception);
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
/*      */ 
/*      */   
/*      */   private static final synchronized int nextPoolId() {
/* 1294 */     return ++poolNumberSequence;
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
/*      */   private int lockRunState() {
/*      */     int i;
/* 1405 */     return (((i = this.runState) & 0x1) != 0 || 
/* 1406 */       !U.compareAndSwapInt(this, RUNSTATE, i, i |= 0x1)) ? 
/* 1407 */       awaitRunStateLock() : i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int awaitRunStateLock() {
/* 1416 */     boolean bool = false;
/* 1417 */     byte b = 0; int i = 0; while (true) {
/* 1418 */       int j; while (((j = this.runState) & 0x1) == 0) {
/* 1419 */         int k; if (U.compareAndSwapInt(this, RUNSTATE, j, k = j | 0x1)) {
/* 1420 */           if (bool)
/*      */             try {
/* 1422 */               Thread.currentThread().interrupt();
/* 1423 */             } catch (SecurityException securityException) {
/*      */               continue;
/*      */             }  
/* 1426 */           return k;
/*      */         } 
/*      */       } 
/* 1429 */       if (!i) {
/* 1430 */         i = ThreadLocalRandom.nextSecondarySeed(); continue;
/* 1431 */       }  if (b) {
/* 1432 */         i ^= i << 6; i ^= i >>> 21; i ^= i << 7;
/* 1433 */         if (i >= 0)
/* 1434 */           b--;  continue;
/*      */       }  AtomicLong atomicLong;
/* 1436 */       if ((j & 0x4) == 0 || (atomicLong = this.stealCounter) == null) {
/* 1437 */         Thread.yield(); continue;
/* 1438 */       }  if (U.compareAndSwapInt(this, RUNSTATE, j, j | 0x2)) {
/* 1439 */         synchronized (atomicLong) {
/* 1440 */           if ((this.runState & 0x2) != 0) {
/*      */             try {
/* 1442 */               atomicLong.wait();
/* 1443 */             } catch (InterruptedException interruptedException) {
/* 1444 */               if (!(Thread.currentThread() instanceof ForkJoinWorkerThread))
/*      */               {
/* 1446 */                 bool = true;
/*      */               }
/*      */             } 
/*      */           } else {
/* 1450 */             atomicLong.notifyAll();
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
/*      */   
/*      */   private void unlockRunState(int paramInt1, int paramInt2) {
/* 1463 */     if (!U.compareAndSwapInt(this, RUNSTATE, paramInt1, paramInt2)) {
/* 1464 */       AtomicLong atomicLong = this.stealCounter;
/* 1465 */       this.runState = paramInt2;
/* 1466 */       if (atomicLong != null) {
/* 1467 */         synchronized (atomicLong) { atomicLong.notifyAll(); }
/*      */       
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
/*      */   private boolean createWorker() {
/* 1481 */     ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = this.factory;
/* 1482 */     Throwable throwable = null;
/* 1483 */     ForkJoinWorkerThread forkJoinWorkerThread = null;
/*      */     try {
/* 1485 */       if (forkJoinWorkerThreadFactory != null && (forkJoinWorkerThread = forkJoinWorkerThreadFactory.newThread(this)) != null) {
/* 1486 */         forkJoinWorkerThread.start();
/* 1487 */         return true;
/*      */       } 
/* 1489 */     } catch (Throwable throwable1) {
/* 1490 */       throwable = throwable1;
/*      */     } 
/* 1492 */     deregisterWorker(forkJoinWorkerThread, throwable);
/* 1493 */     return false;
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
/*      */   private void tryAddWorker(long paramLong) {
/* 1505 */     boolean bool = false;
/*      */     do {
/* 1507 */       long l = 0xFFFF000000000000L & paramLong + 281474976710656L | 0xFFFF00000000L & paramLong + 4294967296L;
/*      */       
/* 1509 */       if (this.ctl != paramLong)
/*      */         continue;  int i, j;
/* 1511 */       if ((j = (i = lockRunState()) & 0x20000000) == 0)
/* 1512 */         bool = U.compareAndSwapLong(this, CTL, paramLong, l); 
/* 1513 */       unlockRunState(i, i & 0xFFFFFFFE);
/* 1514 */       if (j != 0)
/*      */         break; 
/* 1516 */       if (bool) {
/* 1517 */         createWorker();
/*      */         
/*      */         break;
/*      */       } 
/* 1521 */     } while (((paramLong = this.ctl) & 0x800000000000L) != 0L && (int)paramLong == 0);
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
/*      */   final WorkQueue registerWorker(ForkJoinWorkerThread paramForkJoinWorkerThread) {
/* 1533 */     paramForkJoinWorkerThread.setDaemon(true); Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
/* 1534 */     if ((uncaughtExceptionHandler = this.ueh) != null)
/* 1535 */       paramForkJoinWorkerThread.setUncaughtExceptionHandler(uncaughtExceptionHandler); 
/* 1536 */     WorkQueue workQueue = new WorkQueue(this, paramForkJoinWorkerThread);
/* 1537 */     int i = 0;
/* 1538 */     int j = this.config & 0xFFFF0000;
/* 1539 */     int k = lockRunState(); try {
/*      */       WorkQueue[] arrayOfWorkQueue;
/*      */       int m;
/* 1542 */       if ((arrayOfWorkQueue = this.workQueues) != null && (m = arrayOfWorkQueue.length) > 0) {
/* 1543 */         int n = this.indexSeed += -1640531527;
/* 1544 */         int i1 = m - 1;
/* 1545 */         i = (n << 1 | 0x1) & i1;
/* 1546 */         if (arrayOfWorkQueue[i] != null) {
/* 1547 */           byte b1 = 0;
/* 1548 */           byte b2 = (m <= 4) ? 2 : ((m >>> 1 & 0xFFFE) + 2);
/* 1549 */           while (arrayOfWorkQueue[i = i + b2 & i1] != null) {
/* 1550 */             if (++b1 >= m) {
/* 1551 */               this.workQueues = arrayOfWorkQueue = Arrays.copyOf(arrayOfWorkQueue, m <<= 1);
/* 1552 */               i1 = m - 1;
/* 1553 */               b1 = 0;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1557 */         workQueue.hint = n;
/* 1558 */         workQueue.config = i | j;
/* 1559 */         workQueue.scanState = i;
/* 1560 */         arrayOfWorkQueue[i] = workQueue;
/*      */       } 
/*      */     } finally {
/* 1563 */       unlockRunState(k, k & 0xFFFFFFFE);
/*      */     } 
/* 1565 */     paramForkJoinWorkerThread.setName(this.workerNamePrefix.concat(Integer.toString(i >>> 1)));
/* 1566 */     return workQueue;
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
/*      */   final void deregisterWorker(ForkJoinWorkerThread paramForkJoinWorkerThread, Throwable paramThrowable) {
/* 1579 */     WorkQueue workQueue = null;
/* 1580 */     if (paramForkJoinWorkerThread != null && (workQueue = paramForkJoinWorkerThread.workQueue) != null) {
/*      */       
/* 1582 */       int j = workQueue.config & 0xFFFF;
/* 1583 */       int k = lockRunState(); WorkQueue[] arrayOfWorkQueue1;
/* 1584 */       if ((arrayOfWorkQueue1 = this.workQueues) != null && arrayOfWorkQueue1.length > j && arrayOfWorkQueue1[j] == workQueue)
/* 1585 */         arrayOfWorkQueue1[j] = null; 
/* 1586 */       unlockRunState(k, k & 0xFFFFFFFE);
/*      */     }  long l;
/*      */     do {
/*      */     
/* 1590 */     } while (!U.compareAndSwapLong(this, CTL, l = this.ctl, 0xFFFF000000000000L & l - 281474976710656L | 0xFFFF00000000L & l - 4294967296L | 0xFFFFFFFFL & l));
/*      */ 
/*      */     
/* 1593 */     if (workQueue != null) {
/* 1594 */       workQueue.qlock = -1;
/* 1595 */       workQueue.transferStealCount(this);
/* 1596 */       workQueue.cancelAll();
/*      */     } 
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     int i;
/* 1600 */     while (!tryTerminate(false, false) && workQueue != null && workQueue.array != null && (this.runState & 0x20000000) == 0 && (arrayOfWorkQueue = this.workQueues) != null && (i = arrayOfWorkQueue.length - 1) >= 0) {
/*      */       int j;
/*      */ 
/*      */       
/* 1604 */       if ((j = (int)(l = this.ctl)) != 0) {
/* 1605 */         if (tryRelease(l, arrayOfWorkQueue[j & i], 281474976710656L))
/*      */           break;  continue;
/*      */       } 
/* 1608 */       if (paramThrowable != null && (l & 0x800000000000L) != 0L) {
/* 1609 */         tryAddWorker(l);
/*      */       }
/*      */       
/*      */       break;
/*      */     } 
/*      */     
/* 1615 */     if (paramThrowable == null) {
/* 1616 */       ForkJoinTask.helpExpungeStaleExceptions();
/*      */     } else {
/* 1618 */       ForkJoinTask.rethrow(paramThrowable);
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
/*      */   final void signalWork(WorkQueue[] paramArrayOfWorkQueue, WorkQueue paramWorkQueue) {
/*      */     long l;
/* 1631 */     while ((l = this.ctl) < 0L) {
/* 1632 */       int i; if ((i = (int)l) == 0) {
/* 1633 */         if ((l & 0x800000000000L) != 0L)
/* 1634 */           tryAddWorker(l); 
/*      */         break;
/*      */       } 
/* 1637 */       if (paramArrayOfWorkQueue == null)
/*      */         break;  int j;
/* 1639 */       if (paramArrayOfWorkQueue.length <= (j = i & 0xFFFF))
/*      */         break;  WorkQueue workQueue;
/* 1641 */       if ((workQueue = paramArrayOfWorkQueue[j]) == null)
/*      */         break; 
/* 1643 */       int k = i + 65536 & Integer.MAX_VALUE;
/* 1644 */       int m = i - workQueue.scanState;
/* 1645 */       long l1 = 0xFFFFFFFF00000000L & l + 281474976710656L | 0xFFFFFFFFL & workQueue.stackPred;
/* 1646 */       if (m == 0 && U.compareAndSwapLong(this, CTL, l, l1)) {
/* 1647 */         workQueue.scanState = k; Thread thread;
/* 1648 */         if ((thread = workQueue.parker) != null)
/* 1649 */           U.unpark(thread); 
/*      */         break;
/*      */       } 
/* 1652 */       if (paramWorkQueue != null && paramWorkQueue.base == paramWorkQueue.top) {
/*      */         break;
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
/*      */   private boolean tryRelease(long paramLong1, WorkQueue paramWorkQueue, long paramLong2) {
/* 1668 */     int i = (int)paramLong1, j = i + 65536 & Integer.MAX_VALUE;
/* 1669 */     if (paramWorkQueue != null && paramWorkQueue.scanState == i) {
/* 1670 */       long l = 0xFFFFFFFF00000000L & paramLong1 + paramLong2 | 0xFFFFFFFFL & paramWorkQueue.stackPred;
/* 1671 */       if (U.compareAndSwapLong(this, CTL, paramLong1, l)) {
/* 1672 */         paramWorkQueue.scanState = j; Thread thread;
/* 1673 */         if ((thread = paramWorkQueue.parker) != null)
/* 1674 */           U.unpark(thread); 
/* 1675 */         return true;
/*      */       } 
/*      */     } 
/* 1678 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void runWorker(WorkQueue paramWorkQueue) {
/* 1687 */     paramWorkQueue.growArray();
/* 1688 */     int i = paramWorkQueue.hint;
/* 1689 */     int j = (i == 0) ? 1 : i; while (true) {
/*      */       ForkJoinTask<?> forkJoinTask;
/* 1691 */       if ((forkJoinTask = scan(paramWorkQueue, j)) != null) {
/* 1692 */         paramWorkQueue.runTask(forkJoinTask);
/* 1693 */       } else if (!awaitWork(paramWorkQueue, j)) {
/*      */         break;
/* 1695 */       }  j ^= j << 13; j ^= j >>> 17; j ^= j << 5;
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
/*      */   private ForkJoinTask<?> scan(WorkQueue paramWorkQueue, int paramInt) {
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     int i;
/* 1717 */     if ((arrayOfWorkQueue = this.workQueues) != null && (i = arrayOfWorkQueue.length - 1) > 0 && paramWorkQueue != null) {
/* 1718 */       int j = paramWorkQueue.scanState;
/* 1719 */       int k = paramInt & i, m = k, n = 0, i1 = 0;
/*      */       while (true) {
/*      */         WorkQueue workQueue;
/* 1722 */         if ((workQueue = arrayOfWorkQueue[m]) != null) {
/* 1723 */           ForkJoinTask<?>[] arrayOfForkJoinTask; int i2; int i3; if ((i3 = (i2 = workQueue.base) - workQueue.top) < 0 && (arrayOfForkJoinTask = workQueue.array) != null) {
/*      */             
/* 1725 */             long l = (((arrayOfForkJoinTask.length - 1 & i2) << ASHIFT) + ABASE);
/*      */             ForkJoinTask<?> forkJoinTask;
/* 1727 */             if ((forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, l)) != null && workQueue.base == i2)
/*      */             {
/* 1729 */               if (j >= 0) {
/* 1730 */                 if (U.compareAndSwapObject(arrayOfForkJoinTask, l, forkJoinTask, null)) {
/* 1731 */                   workQueue.base = i2 + 1;
/* 1732 */                   if (i3 < -1)
/* 1733 */                     signalWork(arrayOfWorkQueue, workQueue); 
/* 1734 */                   return forkJoinTask;
/*      */                 }
/*      */               
/* 1737 */               } else if (!n && paramWorkQueue.scanState < 0) {
/*      */                 long l1;
/* 1739 */                 tryRelease(l1 = this.ctl, arrayOfWorkQueue[i & (int)l1], 281474976710656L);
/*      */               }  } 
/* 1741 */             if (j < 0)
/* 1742 */               j = paramWorkQueue.scanState; 
/* 1743 */             paramInt ^= paramInt << 1; paramInt ^= paramInt >>> 3; paramInt ^= paramInt << 10;
/* 1744 */             k = m = paramInt & i;
/* 1745 */             n = i1 = 0;
/*      */             continue;
/*      */           } 
/* 1748 */           i1 += i2;
/*      */         } 
/* 1750 */         if ((m = m + 1 & i) == k) {
/* 1751 */           if ((j >= 0 || j == (j = paramWorkQueue.scanState)) && n == (n = i1)) {
/*      */             
/* 1753 */             if (j < 0 || paramWorkQueue.qlock < 0)
/*      */               break; 
/* 1755 */             int i2 = j | Integer.MIN_VALUE;
/* 1756 */             long l1, l2 = 0xFFFFFFFFL & i2 | 0xFFFFFFFF00000000L & (l1 = this.ctl) - 281474976710656L;
/*      */             
/* 1758 */             paramWorkQueue.stackPred = (int)l1;
/* 1759 */             U.putInt(paramWorkQueue, QSCANSTATE, i2);
/* 1760 */             if (U.compareAndSwapLong(this, CTL, l1, l2)) {
/* 1761 */               j = i2;
/*      */             } else {
/* 1763 */               paramWorkQueue.scanState = j;
/*      */             } 
/* 1765 */           }  i1 = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1769 */     return null;
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
/*      */   private boolean awaitWork(WorkQueue paramWorkQueue, int paramInt) {
/* 1786 */     if (paramWorkQueue == null || paramWorkQueue.qlock < 0)
/* 1787 */       return false; 
/* 1788 */     int i = paramWorkQueue.stackPred; byte b = 0; int j;
/* 1789 */     while ((j = paramWorkQueue.scanState) < 0) {
/*      */       
/* 1791 */       if (b) {
/* 1792 */         paramInt ^= paramInt << 6; paramInt ^= paramInt >>> 21; paramInt ^= paramInt << 7;
/* 1793 */         if (paramInt >= 0 && --b == 0) {
/*      */           WorkQueue workQueue; WorkQueue[] arrayOfWorkQueue; int k;
/* 1795 */           if (i != 0 && (arrayOfWorkQueue = this.workQueues) != null && (k = i & 0xFFFF) < arrayOfWorkQueue.length && (workQueue = arrayOfWorkQueue[k]) != null && (workQueue.parker == null || workQueue.scanState >= 0))
/*      */           {
/*      */ 
/*      */             
/* 1799 */             b = 0; } 
/*      */         }  continue;
/*      */       } 
/* 1802 */       if (paramWorkQueue.qlock < 0)
/* 1803 */         return false; 
/* 1804 */       if (!Thread.interrupted()) {
/*      */         long l2, l3, l4, l1;
/* 1806 */         int k = (int)((l1 = this.ctl) >> 48L) + (this.config & 0xFFFF);
/* 1807 */         if ((k <= 0 && tryTerminate(false, false)) || (this.runState & 0x20000000) != 0)
/*      */         {
/* 1809 */           return false; } 
/* 1810 */         if (k <= 0 && j == (int)l1) {
/* 1811 */           l2 = 0xFFFFFFFF00000000L & l1 + 281474976710656L | 0xFFFFFFFFL & i;
/* 1812 */           short s = (short)(int)(l1 >>> 32L);
/* 1813 */           if (s > 2 && U.compareAndSwapLong(this, CTL, l1, l2))
/* 1814 */             return false; 
/* 1815 */           l3 = 2000000000L * ((s >= 0) ? 1L : (1 - s));
/* 1816 */           l4 = System.nanoTime() + l3 - 20000000L;
/*      */         } else {
/*      */           
/* 1819 */           l2 = l3 = l4 = 0L;
/* 1820 */         }  Thread thread = Thread.currentThread();
/* 1821 */         U.putObject(thread, PARKBLOCKER, this);
/* 1822 */         paramWorkQueue.parker = thread;
/* 1823 */         if (paramWorkQueue.scanState < 0 && this.ctl == l1)
/* 1824 */           U.park(false, l3); 
/* 1825 */         U.putOrderedObject(paramWorkQueue, QPARKER, null);
/* 1826 */         U.putObject(thread, PARKBLOCKER, null);
/* 1827 */         if (paramWorkQueue.scanState >= 0)
/*      */           break; 
/* 1829 */         if (l3 != 0L && this.ctl == l1 && l4 - 
/* 1830 */           System.nanoTime() <= 0L && U
/* 1831 */           .compareAndSwapLong(this, CTL, l1, l2))
/* 1832 */           return false; 
/*      */       } 
/*      */     } 
/* 1835 */     return true;
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
/*      */   final int helpComplete(WorkQueue paramWorkQueue, CountedCompleter<?> paramCountedCompleter, int paramInt) {
/* 1858 */     int i = 0; WorkQueue[] arrayOfWorkQueue; int j;
/* 1859 */     if ((arrayOfWorkQueue = this.workQueues) != null && (j = arrayOfWorkQueue.length - 1) >= 0 && paramCountedCompleter != null && paramWorkQueue != null) {
/*      */       
/* 1861 */       int k = paramWorkQueue.config;
/* 1862 */       int m = paramWorkQueue.hint ^ paramWorkQueue.top;
/* 1863 */       int n = m & j;
/* 1864 */       int i1 = 1;
/* 1865 */       int i2 = n, i3 = 0, i4 = 0;
/*      */       
/* 1867 */       while ((i = paramCountedCompleter.status) >= 0) {
/*      */         CountedCompleter<?> countedCompleter;
/* 1869 */         if (i1 == 1 && (countedCompleter = paramWorkQueue.popCC(paramCountedCompleter, k)) != null) {
/* 1870 */           countedCompleter.doExec();
/* 1871 */           if (paramInt != 0 && --paramInt == 0)
/*      */             break; 
/* 1873 */           n = i2;
/* 1874 */           i3 = i4 = 0; continue;
/*      */         } 
/*      */         WorkQueue workQueue;
/* 1877 */         if ((workQueue = arrayOfWorkQueue[i2]) == null) {
/* 1878 */           i1 = 0;
/* 1879 */         } else if ((i1 = workQueue.pollAndExecCC(paramCountedCompleter)) < 0) {
/* 1880 */           i4 += i1;
/* 1881 */         }  if (i1 > 0) {
/* 1882 */           if (i1 == 1 && paramInt != 0 && --paramInt == 0)
/*      */             break; 
/* 1884 */           m ^= m << 13; m ^= m >>> 17; m ^= m << 5;
/* 1885 */           n = i2 = m & j;
/* 1886 */           i3 = i4 = 0; continue;
/*      */         } 
/* 1888 */         if ((i2 = i2 + 1 & j) == n) {
/* 1889 */           if (i3 == (i3 = i4))
/*      */             break; 
/* 1891 */           i4 = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1896 */     return i;
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
/*      */   private void helpStealer(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask) {
/* 1913 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 1914 */     int i = 0; int j;
/* 1915 */     if (arrayOfWorkQueue != null && (j = arrayOfWorkQueue.length - 1) >= 0 && paramWorkQueue != null && paramForkJoinTask != null) {
/*      */       int k;
/*      */       do {
/* 1918 */         k = 0;
/*      */         
/* 1920 */         WorkQueue workQueue = paramWorkQueue; ForkJoinTask<?> forkJoinTask;
/* 1921 */         label57: for (forkJoinTask = paramForkJoinTask; forkJoinTask.status >= 0; ) {
/* 1922 */           int m = workQueue.hint | 0x1; byte b = 0;
/* 1923 */           for (; b <= j; b += 2) {
/*      */             WorkQueue workQueue1; int n;
/* 1925 */             if ((workQueue1 = arrayOfWorkQueue[n = m + b & j]) != null) {
/* 1926 */               if (workQueue1.currentSteal == forkJoinTask) {
/* 1927 */                 workQueue.hint = n;
/*      */                 continue label57;
/*      */               } 
/* 1930 */               k += workQueue1.base;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1969 */       while (paramForkJoinTask.status >= 0 && i != (i = k));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean tryCompensate(WorkQueue paramWorkQueue) {
/*      */     boolean bool;
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     int i;
/*      */     int j;
/* 1984 */     if (paramWorkQueue == null || paramWorkQueue.qlock < 0 || (arrayOfWorkQueue = this.workQueues) == null || (i = arrayOfWorkQueue.length - 1) <= 0 || (j = this.config & 0xFFFF) == 0)
/*      */     
/*      */     { 
/* 1987 */       bool = false; }
/* 1988 */     else { long l; int k; if ((k = (int)(l = this.ctl)) != 0) {
/* 1989 */         bool = tryRelease(l, arrayOfWorkQueue[k & i], 0L);
/*      */       } else {
/* 1991 */         int m = (int)(l >> 48L) + j;
/* 1992 */         int n = (short)(int)(l >> 32L) + j;
/* 1993 */         byte b1 = 0; byte b2;
/* 1994 */         for (b2 = 0; b2 <= i; b2++) {
/*      */           WorkQueue workQueue;
/* 1996 */           if ((workQueue = arrayOfWorkQueue[(b2 << 1 | 0x1) & i]) != null) {
/* 1997 */             if ((workQueue.scanState & 0x1) != 0)
/*      */               break; 
/* 1999 */             b1++;
/*      */           } 
/*      */         } 
/* 2002 */         if (b1 != n << 1 || this.ctl != l) {
/* 2003 */           bool = false;
/* 2004 */         } else if (n >= j && m > 1 && paramWorkQueue.isEmpty()) {
/* 2005 */           long l1 = 0xFFFF000000000000L & l - 281474976710656L | 0xFFFFFFFFFFFFL & l;
/*      */           
/* 2007 */           bool = U.compareAndSwapLong(this, CTL, l, l1);
/*      */         } else {
/* 2009 */           boolean bool1; if (n >= 32767 || (this == common && n >= j + commonMaxSpares))
/*      */           {
/* 2011 */             throw new RejectedExecutionException("Thread limit exceeded replacing blocked worker");
/*      */           }
/*      */           
/* 2014 */           b2 = 0;
/* 2015 */           long l1 = 0xFFFF000000000000L & l | 0xFFFF00000000L & l + 4294967296L;
/*      */           int i1;
/* 2017 */           if (((i1 = lockRunState()) & 0x20000000) == 0)
/* 2018 */             bool1 = U.compareAndSwapLong(this, CTL, l, l1); 
/* 2019 */           unlockRunState(i1, i1 & 0xFFFFFFFE);
/* 2020 */           bool = (bool1 && createWorker()) ? true : false;
/*      */         } 
/*      */       }  }
/* 2023 */      return bool;
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
/*      */   final int awaitJoin(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask, long paramLong) {
/* 2035 */     int i = 0;
/* 2036 */     if (paramForkJoinTask != null && paramWorkQueue != null) {
/* 2037 */       ForkJoinTask<?> forkJoinTask = paramWorkQueue.currentJoin;
/* 2038 */       U.putOrderedObject(paramWorkQueue, QCURRENTJOIN, paramForkJoinTask);
/* 2039 */       CountedCompleter<?> countedCompleter = (paramForkJoinTask instanceof CountedCompleter) ? (CountedCompleter)paramForkJoinTask : null;
/*      */ 
/*      */       
/* 2042 */       while ((i = paramForkJoinTask.status) >= 0) {
/*      */         long l;
/* 2044 */         if (countedCompleter != null) {
/* 2045 */           helpComplete(paramWorkQueue, countedCompleter, 0);
/* 2046 */         } else if (paramWorkQueue.base == paramWorkQueue.top || paramWorkQueue.tryRemoveAndExec(paramForkJoinTask)) {
/* 2047 */           helpStealer(paramWorkQueue, paramForkJoinTask);
/* 2048 */         }  if ((i = paramForkJoinTask.status) < 0) {
/*      */           break;
/*      */         }
/* 2051 */         if (paramLong == 0L)
/* 2052 */         { l = 0L; }
/* 2053 */         else { long l1; if ((l1 = paramLong - System.nanoTime()) <= 0L)
/*      */             break; 
/* 2055 */           if ((l = TimeUnit.NANOSECONDS.toMillis(l1)) <= 0L)
/* 2056 */             l = 1L;  }
/* 2057 */          if (tryCompensate(paramWorkQueue)) {
/* 2058 */           paramForkJoinTask.internalWait(l);
/* 2059 */           U.getAndAddLong(this, CTL, 281474976710656L);
/*      */         } 
/*      */       } 
/* 2062 */       U.putOrderedObject(paramWorkQueue, QCURRENTJOIN, forkJoinTask);
/*      */     } 
/* 2064 */     return i;
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
/*      */   private WorkQueue findNonEmptyStealQueue() {
/* 2076 */     int j = ThreadLocalRandom.nextSecondarySeed(); WorkQueue[] arrayOfWorkQueue; int i;
/* 2077 */     if ((arrayOfWorkQueue = this.workQueues) != null && (i = arrayOfWorkQueue.length - 1) >= 0) {
/* 2078 */       int k = j & i, m = k, n = 0, i1 = 0; while (true) {
/*      */         WorkQueue workQueue;
/* 2080 */         if ((workQueue = arrayOfWorkQueue[m]) != null) {
/* 2081 */           int i2; if ((i2 = workQueue.base) - workQueue.top < 0)
/* 2082 */             return workQueue; 
/* 2083 */           i1 += i2;
/*      */         } 
/* 2085 */         if ((m = m + 1 & i) == k) {
/* 2086 */           if (n == (n = i1))
/*      */             break; 
/* 2088 */           i1 = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2092 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void helpQuiescePool(WorkQueue paramWorkQueue) {
/* 2102 */     ForkJoinTask<?> forkJoinTask = paramWorkQueue.currentSteal;
/* 2103 */     boolean bool = true;
/*      */     while (true) {
/* 2105 */       paramWorkQueue.execLocalTasks(); WorkQueue workQueue;
/* 2106 */       while ((workQueue = findNonEmptyStealQueue()) != null) {
/* 2107 */         if (!bool) {
/* 2108 */           bool = true;
/* 2109 */           U.getAndAddLong(this, CTL, 281474976710656L);
/*      */         } 
/*      */         
/* 2112 */         U.putOrderedObject(paramWorkQueue, QCURRENTSTEAL, forkJoinTask1);
/* 2113 */         forkJoinTask1.doExec(); ForkJoinTask<?> forkJoinTask1; int i;
/* 2114 */         if ((i = workQueue.base) - workQueue.top < 0 && (forkJoinTask1 = workQueue.pollAt(i)) != null && ++paramWorkQueue.nsteals < 0) {
/* 2115 */           paramWorkQueue.transferStealCount(this);
/*      */         }
/*      */       } 
/* 2118 */       if (bool) {
/* 2119 */         long l1, l2 = 0xFFFF000000000000L & (l1 = this.ctl) - 281474976710656L | 0xFFFFFFFFFFFFL & l1;
/* 2120 */         if ((int)(l2 >> 48L) + (this.config & 0xFFFF) <= 0)
/*      */           break; 
/* 2122 */         if (U.compareAndSwapLong(this, CTL, l1, l2))
/* 2123 */           bool = false;  continue;
/*      */       }  long l;
/* 2125 */       if ((int)((l = this.ctl) >> 48L) + (this.config & 0xFFFF) <= 0 && U
/* 2126 */         .compareAndSwapLong(this, CTL, l, l + 281474976710656L))
/*      */         break; 
/*      */     } 
/* 2129 */     U.putOrderedObject(paramWorkQueue, QCURRENTSTEAL, forkJoinTask);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final ForkJoinTask<?> nextTaskFor(WorkQueue paramWorkQueue) {
/*      */     while (true) {
/*      */       ForkJoinTask<?> forkJoinTask;
/* 2140 */       if ((forkJoinTask = paramWorkQueue.nextLocalTask()) != null)
/* 2141 */         return forkJoinTask;  WorkQueue workQueue;
/* 2142 */       if ((workQueue = findNonEmptyStealQueue()) == null)
/* 2143 */         return null;  int i;
/* 2144 */       if ((i = workQueue.base) - workQueue.top < 0 && (forkJoinTask = workQueue.pollAt(i)) != null) {
/* 2145 */         return forkJoinTask;
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
/*      */   static int getSurplusQueuedTaskCount() {
/*      */     Thread thread;
/* 2193 */     if (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) {
/* 2194 */       ForkJoinWorkerThread forkJoinWorkerThread; ForkJoinPool forkJoinPool; int i = (forkJoinPool = (forkJoinWorkerThread = (ForkJoinWorkerThread)thread).pool).config & 0xFFFF;
/*      */       WorkQueue workQueue;
/* 2196 */       int j = (workQueue = forkJoinWorkerThread.workQueue).top - workQueue.base;
/* 2197 */       int k = (int)(forkJoinPool.ctl >> 48L) + i;
/* 2198 */       return j - ((k > (i >>>= 1)) ? 0 : ((k > (i >>>= 1)) ? 1 : ((k > (i >>>= 1)) ? 2 : ((k > (i >>>= 1)) ? 4 : 8))));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2204 */     return 0;
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
/*      */   private boolean tryTerminate(boolean paramBoolean1, boolean paramBoolean2) {
/* 2219 */     if (this == common)
/* 2220 */       return false;  int i;
/* 2221 */     if ((i = this.runState) >= 0) {
/* 2222 */       if (!paramBoolean2)
/* 2223 */         return false; 
/* 2224 */       i = lockRunState();
/* 2225 */       unlockRunState(i, i & 0xFFFFFFFE | Integer.MIN_VALUE);
/*      */     } 
/*      */     
/* 2228 */     if ((i & 0x20000000) == 0) {
/* 2229 */       if (!paramBoolean1) {
/* 2230 */         long l2, l1 = 0L;
/*      */         do {
/* 2232 */           l2 = this.ctl;
/* 2233 */           if ((int)(l2 >> 48L) + (this.config & 0xFFFF) > 0)
/* 2234 */             return false;  WorkQueue[] arrayOfWorkQueue; int j;
/* 2235 */           if ((arrayOfWorkQueue = this.workQueues) == null || (j = arrayOfWorkQueue.length - 1) <= 0)
/*      */             break; 
/* 2237 */           for (byte b1 = 0; b1 <= j; b1++) {
/* 2238 */             WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b1]) != null) {
/* 2239 */               int k; if ((k = workQueue.base) != workQueue.top || workQueue.scanState >= 0 || workQueue.currentSteal != null) {
/*      */                 long l3;
/* 2241 */                 tryRelease(l3 = this.ctl, arrayOfWorkQueue[j & (int)l3], 281474976710656L);
/* 2242 */                 return false;
/*      */               } 
/* 2244 */               l2 += k;
/* 2245 */               if ((b1 & 0x1) == 0)
/* 2246 */                 workQueue.qlock = -1; 
/*      */             } 
/*      */           } 
/* 2249 */         } while (l1 != (l1 = l2));
/*      */       } 
/*      */ 
/*      */       
/* 2253 */       if ((this.runState & 0x20000000) == 0) {
/* 2254 */         i = lockRunState();
/* 2255 */         unlockRunState(i, i & 0xFFFFFFFE | 0x20000000);
/*      */       } 
/*      */     } 
/*      */     
/* 2259 */     byte b = 0;
/* 2260 */     long l = 0L;
/*      */     while (true) {
/* 2262 */       long l1 = this.ctl; WorkQueue[] arrayOfWorkQueue; int j;
/* 2263 */       if ((short)(int)(l1 >>> 32L) + (this.config & 0xFFFF) <= 0 || (arrayOfWorkQueue = this.workQueues) == null || (j = arrayOfWorkQueue.length - 1) <= 0) {
/*      */         
/* 2265 */         if ((this.runState & 0x40000000) == 0) {
/* 2266 */           i = lockRunState();
/* 2267 */           unlockRunState(i, i & 0xFFFFFFFE | 0x40000000);
/* 2268 */           synchronized (this) { notifyAll(); }
/*      */         
/*      */         }  break;
/*      */       } 
/* 2272 */       for (byte b1 = 0; b1 <= j; b1++) {
/* 2273 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b1]) != null) {
/* 2274 */           l1 += workQueue.base;
/* 2275 */           workQueue.qlock = -1;
/* 2276 */           if (b) {
/* 2277 */             workQueue.cancelAll(); ForkJoinWorkerThread forkJoinWorkerThread;
/* 2278 */             if (b > 1 && (forkJoinWorkerThread = workQueue.owner) != null) {
/* 2279 */               if (!forkJoinWorkerThread.isInterrupted()) {
/*      */                 try {
/* 2281 */                   forkJoinWorkerThread.interrupt();
/* 2282 */                 } catch (Throwable throwable) {}
/*      */               }
/*      */               
/* 2285 */               if (workQueue.scanState < 0)
/* 2286 */                 U.unpark(forkJoinWorkerThread); 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2291 */       if (l1 != l) {
/* 2292 */         l = l1;
/* 2293 */         b = 0; continue;
/*      */       } 
/* 2295 */       if (b > 3 && b > j)
/*      */         break; 
/* 2297 */       if (++b > 1) {
/* 2298 */         byte b2 = 0; long l2; int k;
/* 2299 */         while (b2++ <= j && (k = (int)(l2 = this.ctl)) != 0)
/* 2300 */           tryRelease(l2, arrayOfWorkQueue[k & j], 281474976710656L); 
/*      */       } 
/*      */     } 
/* 2303 */     return true;
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
/*      */   private void externalSubmit(ForkJoinTask<?> paramForkJoinTask) {
/*      */     int i;
/* 2319 */     if ((i = ThreadLocalRandom.getProbe()) == 0) {
/* 2320 */       ThreadLocalRandom.localInit();
/* 2321 */       i = ThreadLocalRandom.getProbe();
/*      */     } 
/*      */     
/*      */     while (true) {
/* 2325 */       boolean bool = false; int j;
/* 2326 */       if ((j = this.runState) < 0) {
/* 2327 */         tryTerminate(false, false);
/* 2328 */         throw new RejectedExecutionException();
/*      */       }  WorkQueue[] arrayOfWorkQueue; int k;
/* 2330 */       if ((j & 0x4) == 0 || (arrayOfWorkQueue = this.workQueues) == null || (k = arrayOfWorkQueue.length - 1) < 0) {
/*      */         
/* 2332 */         byte b = 0;
/* 2333 */         j = lockRunState();
/*      */         try {
/* 2335 */           if ((j & 0x4) == 0) {
/* 2336 */             U.compareAndSwapObject(this, STEALCOUNTER, null, new AtomicLong());
/*      */ 
/*      */             
/* 2339 */             int m = this.config & 0xFFFF;
/* 2340 */             int n = (m > 1) ? (m - 1) : 1;
/* 2341 */             n |= n >>> 1; n |= n >>> 2; n |= n >>> 4;
/* 2342 */             n |= n >>> 8; n |= n >>> 16; n = n + 1 << 1;
/* 2343 */             this.workQueues = new WorkQueue[n];
/* 2344 */             b = 4;
/*      */           } 
/*      */         } finally {
/* 2347 */           unlockRunState(j, j & 0xFFFFFFFE | b);
/*      */         } 
/*      */       } else {
/* 2350 */         WorkQueue workQueue; int m; if ((workQueue = arrayOfWorkQueue[m = i & k & 0x7E]) != null)
/* 2351 */         { if (workQueue.qlock == 0 && U.compareAndSwapInt(workQueue, QLOCK, 0, 1)) {
/* 2352 */             ForkJoinTask<?>[] arrayOfForkJoinTask = workQueue.array;
/* 2353 */             int n = workQueue.top;
/* 2354 */             boolean bool1 = false; try {
/*      */               ForkJoinTask[] arrayOfForkJoinTask1;
/* 2356 */               if ((arrayOfForkJoinTask != null && arrayOfForkJoinTask.length > n + 1 - workQueue.base) || (
/* 2357 */                 arrayOfForkJoinTask1 = (ForkJoinTask[])workQueue.growArray()) != null) {
/* 2358 */                 int i1 = ((arrayOfForkJoinTask1.length - 1 & n) << ASHIFT) + ABASE;
/* 2359 */                 U.putOrderedObject(arrayOfForkJoinTask1, i1, paramForkJoinTask);
/* 2360 */                 U.putOrderedInt(workQueue, QTOP, n + 1);
/* 2361 */                 bool1 = true;
/*      */               } 
/*      */             } finally {
/* 2364 */               U.compareAndSwapInt(workQueue, QLOCK, 1, 0);
/*      */             } 
/* 2366 */             if (bool1) {
/* 2367 */               signalWork(arrayOfWorkQueue, workQueue);
/*      */               return;
/*      */             } 
/*      */           } 
/* 2371 */           bool = true; }
/*      */         
/* 2373 */         else if (((j = this.runState) & 0x1) == 0)
/* 2374 */         { workQueue = new WorkQueue(this, null);
/* 2375 */           workQueue.hint = i;
/* 2376 */           workQueue.config = m | Integer.MIN_VALUE;
/* 2377 */           workQueue.scanState = Integer.MIN_VALUE;
/* 2378 */           j = lockRunState();
/* 2379 */           if (j > 0 && (arrayOfWorkQueue = this.workQueues) != null && m < arrayOfWorkQueue.length && arrayOfWorkQueue[m] == null)
/*      */           {
/* 2381 */             arrayOfWorkQueue[m] = workQueue; } 
/* 2382 */           unlockRunState(j, j & 0xFFFFFFFE); }
/*      */         else
/*      */         
/* 2385 */         { bool = true; } 
/* 2386 */       }  if (bool) {
/* 2387 */         i = ThreadLocalRandom.advanceProbe(i);
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
/*      */   final void externalPush(ForkJoinTask<?> paramForkJoinTask) {
/* 2401 */     int j = ThreadLocalRandom.getProbe();
/* 2402 */     int k = this.runState; WorkQueue arrayOfWorkQueue[], workQueue; int i;
/* 2403 */     if ((arrayOfWorkQueue = this.workQueues) != null && (i = arrayOfWorkQueue.length - 1) >= 0 && (workQueue = arrayOfWorkQueue[i & j & 0x7E]) != null && j != 0 && k > 0 && U
/*      */       
/* 2405 */       .compareAndSwapInt(workQueue, QLOCK, 0, 1)) {
/*      */       ForkJoinTask<?>[] arrayOfForkJoinTask; int m; int n; int i1;
/* 2407 */       if ((arrayOfForkJoinTask = workQueue.array) != null && (m = arrayOfForkJoinTask.length - 1) > (n = (i1 = workQueue.top) - workQueue.base)) {
/*      */         
/* 2409 */         int i2 = ((m & i1) << ASHIFT) + ABASE;
/* 2410 */         U.putOrderedObject(arrayOfForkJoinTask, i2, paramForkJoinTask);
/* 2411 */         U.putOrderedInt(workQueue, QTOP, i1 + 1);
/* 2412 */         U.putIntVolatile(workQueue, QLOCK, 0);
/* 2413 */         if (n <= 1)
/* 2414 */           signalWork(arrayOfWorkQueue, workQueue); 
/*      */         return;
/*      */       } 
/* 2417 */       U.compareAndSwapInt(workQueue, QLOCK, 1, 0);
/*      */     } 
/* 2419 */     externalSubmit(paramForkJoinTask);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static WorkQueue commonSubmitterQueue() {
/* 2426 */     ForkJoinPool forkJoinPool = common;
/* 2427 */     int i = ThreadLocalRandom.getProbe(); WorkQueue[] arrayOfWorkQueue;
/*      */     int j;
/* 2429 */     return (forkJoinPool != null && (arrayOfWorkQueue = forkJoinPool.workQueues) != null && (j = arrayOfWorkQueue.length - 1) >= 0) ? arrayOfWorkQueue[j & i & 0x7E] : null;
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
/*      */   final boolean tryExternalUnpush(ForkJoinTask<?> paramForkJoinTask) {
/* 2441 */     int k = ThreadLocalRandom.getProbe(); WorkQueue arrayOfWorkQueue[], workQueue; ForkJoinTask<?>[] arrayOfForkJoinTask; int i, j;
/* 2442 */     if ((arrayOfWorkQueue = this.workQueues) != null && (i = arrayOfWorkQueue.length - 1) >= 0 && (workQueue = arrayOfWorkQueue[i & k & 0x7E]) != null && (arrayOfForkJoinTask = workQueue.array) != null && (j = workQueue.top) != workQueue.base) {
/*      */ 
/*      */       
/* 2445 */       long l = (((arrayOfForkJoinTask.length - 1 & j - 1) << ASHIFT) + ABASE);
/* 2446 */       if (U.compareAndSwapInt(workQueue, QLOCK, 0, 1)) {
/* 2447 */         if (workQueue.top == j && workQueue.array == arrayOfForkJoinTask && U
/* 2448 */           .getObject(arrayOfForkJoinTask, l) == paramForkJoinTask && U
/* 2449 */           .compareAndSwapObject(arrayOfForkJoinTask, l, paramForkJoinTask, null)) {
/* 2450 */           U.putOrderedInt(workQueue, QTOP, j - 1);
/* 2451 */           U.putOrderedInt(workQueue, QLOCK, 0);
/* 2452 */           return true;
/*      */         } 
/* 2454 */         U.compareAndSwapInt(workQueue, QLOCK, 1, 0);
/*      */       } 
/*      */     } 
/* 2457 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int externalHelpComplete(CountedCompleter<?> paramCountedCompleter, int paramInt) {
/* 2465 */     int j = ThreadLocalRandom.getProbe(); WorkQueue[] arrayOfWorkQueue; int i; return ((
/* 2466 */       arrayOfWorkQueue = this.workQueues) == null || (i = arrayOfWorkQueue.length) == 0) ? 0 : 
/* 2467 */       helpComplete(arrayOfWorkQueue[i - 1 & j & 0x7E], paramCountedCompleter, paramInt);
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
/*      */   public ForkJoinPool() {
/* 2486 */     this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, null, false);
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
/*      */   public ForkJoinPool(int paramInt) {
/* 2505 */     this(paramInt, defaultForkJoinWorkerThreadFactory, null, false);
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
/*      */   public ForkJoinPool(int paramInt, ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, boolean paramBoolean) {
/* 2536 */     this(checkParallelism(paramInt), 
/* 2537 */         checkFactory(paramForkJoinWorkerThreadFactory), paramUncaughtExceptionHandler, paramBoolean ? 65536 : 0, "ForkJoinPool-" + 
/*      */ 
/*      */         
/* 2540 */         nextPoolId() + "-worker-");
/* 2541 */     checkPermission();
/*      */   }
/*      */   
/*      */   private static int checkParallelism(int paramInt) {
/* 2545 */     if (paramInt <= 0 || paramInt > 32767)
/* 2546 */       throw new IllegalArgumentException(); 
/* 2547 */     return paramInt;
/*      */   }
/*      */ 
/*      */   
/*      */   private static ForkJoinWorkerThreadFactory checkFactory(ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory) {
/* 2552 */     if (paramForkJoinWorkerThreadFactory == null)
/* 2553 */       throw new NullPointerException(); 
/* 2554 */     return paramForkJoinWorkerThreadFactory;
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
/*      */   private ForkJoinPool(int paramInt1, ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, int paramInt2, String paramString) {
/* 2567 */     this.workerNamePrefix = paramString;
/* 2568 */     this.factory = paramForkJoinWorkerThreadFactory;
/* 2569 */     this.ueh = paramUncaughtExceptionHandler;
/* 2570 */     this.config = paramInt1 & 0xFFFF | paramInt2;
/* 2571 */     long l = -paramInt1;
/* 2572 */     this.ctl = l << 48L & 0xFFFF000000000000L | l << 32L & 0xFFFF00000000L;
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
/*      */   public static ForkJoinPool commonPool() {
/* 2590 */     return common;
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
/*      */   public <T> T invoke(ForkJoinTask<T> paramForkJoinTask) {
/* 2613 */     if (paramForkJoinTask == null)
/* 2614 */       throw new NullPointerException(); 
/* 2615 */     externalPush(paramForkJoinTask);
/* 2616 */     return paramForkJoinTask.join();
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
/*      */   public void execute(ForkJoinTask<?> paramForkJoinTask) {
/* 2628 */     if (paramForkJoinTask == null)
/* 2629 */       throw new NullPointerException(); 
/* 2630 */     externalPush(paramForkJoinTask);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute(Runnable paramRunnable) {
/*      */     ForkJoinTask<?> forkJoinTask;
/* 2641 */     if (paramRunnable == null) {
/* 2642 */       throw new NullPointerException();
/*      */     }
/* 2644 */     if (paramRunnable instanceof ForkJoinTask) {
/* 2645 */       forkJoinTask = (ForkJoinTask)paramRunnable;
/*      */     } else {
/* 2647 */       forkJoinTask = new ForkJoinTask.RunnableExecuteAction(paramRunnable);
/* 2648 */     }  externalPush(forkJoinTask);
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
/*      */   public <T> ForkJoinTask<T> submit(ForkJoinTask<T> paramForkJoinTask) {
/* 2662 */     if (paramForkJoinTask == null)
/* 2663 */       throw new NullPointerException(); 
/* 2664 */     externalPush(paramForkJoinTask);
/* 2665 */     return paramForkJoinTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> ForkJoinTask<T> submit(Callable<T> paramCallable) {
/* 2674 */     ForkJoinTask.AdaptedCallable<T> adaptedCallable = new ForkJoinTask.AdaptedCallable<>(paramCallable);
/* 2675 */     externalPush(adaptedCallable);
/* 2676 */     return adaptedCallable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> ForkJoinTask<T> submit(Runnable paramRunnable, T paramT) {
/* 2685 */     ForkJoinTask.AdaptedRunnable<T> adaptedRunnable = new ForkJoinTask.AdaptedRunnable<>(paramRunnable, paramT);
/* 2686 */     externalPush(adaptedRunnable);
/* 2687 */     return adaptedRunnable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ForkJoinTask<?> submit(Runnable paramRunnable) {
/*      */     ForkJoinTask<?> forkJoinTask;
/* 2696 */     if (paramRunnable == null) {
/* 2697 */       throw new NullPointerException();
/*      */     }
/* 2699 */     if (paramRunnable instanceof ForkJoinTask) {
/* 2700 */       forkJoinTask = (ForkJoinTask)paramRunnable;
/*      */     } else {
/* 2702 */       forkJoinTask = new ForkJoinTask.AdaptedRunnableAction(paramRunnable);
/* 2703 */     }  externalPush(forkJoinTask);
/* 2704 */     return forkJoinTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection) {
/* 2715 */     ArrayList<ForkJoinTask.AdaptedCallable> arrayList = new ArrayList(paramCollection.size());
/*      */     
/* 2717 */     boolean bool = false;
/*      */     try {
/* 2719 */       for (Callable<?> callable : paramCollection) {
/* 2720 */         ForkJoinTask.AdaptedCallable<?> adaptedCallable = new ForkJoinTask.AdaptedCallable(callable);
/* 2721 */         arrayList.add(adaptedCallable);
/* 2722 */         externalPush(adaptedCallable);
/*      */       }  int i;
/* 2724 */       for (byte b = 0; b < i; b++)
/* 2725 */         ((ForkJoinTask)arrayList.get(b)).quietlyJoin(); 
/* 2726 */       bool = true;
/* 2727 */       return (List)arrayList;
/*      */     } finally {
/* 2729 */       if (!bool) {
/* 2730 */         byte b; int i; for (b = 0, i = arrayList.size(); b < i; b++) {
/* 2731 */           ((Future)arrayList.get(b)).cancel(false);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ForkJoinWorkerThreadFactory getFactory() {
/* 2741 */     return this.factory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
/* 2751 */     return this.ueh;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getParallelism() {
/*      */     int i;
/* 2761 */     return ((i = this.config & 0xFFFF) > 0) ? i : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getCommonPoolParallelism() {
/* 2771 */     return commonParallelism;
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
/*      */   public int getPoolSize() {
/* 2783 */     return (this.config & 0xFFFF) + (short)(int)(this.ctl >>> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAsyncMode() {
/* 2793 */     return ((this.config & 0x10000) != 0);
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
/*      */   public int getRunningThreadCount() {
/* 2805 */     byte b = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2807 */     if ((arrayOfWorkQueue = this.workQueues) != null)
/* 2808 */       for (byte b1 = 1; b1 < arrayOfWorkQueue.length; b1 += 2) {
/* 2809 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b1]) != null && workQueue.isApparentlyUnblocked()) {
/* 2810 */           b++;
/*      */         }
/*      */       }  
/* 2813 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActiveThreadCount() {
/* 2824 */     int i = (this.config & 0xFFFF) + (int)(this.ctl >> 48L);
/* 2825 */     return (i <= 0) ? 0 : i;
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
/*      */   public boolean isQuiescent() {
/* 2840 */     return ((this.config & 0xFFFF) + (int)(this.ctl >> 48L) <= 0);
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
/*      */   public long getStealCount() {
/* 2855 */     AtomicLong atomicLong = this.stealCounter;
/* 2856 */     long l = (atomicLong == null) ? 0L : atomicLong.get();
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2858 */     if ((arrayOfWorkQueue = this.workQueues) != null)
/* 2859 */       for (byte b = 1; b < arrayOfWorkQueue.length; b += 2) {
/* 2860 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b]) != null) {
/* 2861 */           l += workQueue.nsteals;
/*      */         }
/*      */       }  
/* 2864 */     return l;
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
/*      */   public long getQueuedTaskCount() {
/* 2878 */     long l = 0L;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2880 */     if ((arrayOfWorkQueue = this.workQueues) != null)
/* 2881 */       for (byte b = 1; b < arrayOfWorkQueue.length; b += 2) {
/* 2882 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b]) != null) {
/* 2883 */           l += workQueue.queueSize();
/*      */         }
/*      */       }  
/* 2886 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getQueuedSubmissionCount() {
/* 2897 */     int i = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2899 */     if ((arrayOfWorkQueue = this.workQueues) != null)
/* 2900 */       for (byte b = 0; b < arrayOfWorkQueue.length; b += 2) {
/* 2901 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b]) != null) {
/* 2902 */           i += workQueue.queueSize();
/*      */         }
/*      */       }  
/* 2905 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasQueuedSubmissions() {
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2916 */     if ((arrayOfWorkQueue = this.workQueues) != null)
/* 2917 */       for (byte b = 0; b < arrayOfWorkQueue.length; b += 2) {
/* 2918 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b]) != null && !workQueue.isEmpty()) {
/* 2919 */           return true;
/*      */         }
/*      */       }  
/* 2922 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ForkJoinTask<?> pollSubmission() {
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2934 */     if ((arrayOfWorkQueue = this.workQueues) != null)
/* 2935 */       for (byte b = 0; b < arrayOfWorkQueue.length; b += 2) {
/* 2936 */         WorkQueue workQueue; ForkJoinTask<?> forkJoinTask; if ((workQueue = arrayOfWorkQueue[b]) != null && (forkJoinTask = workQueue.poll()) != null) {
/* 2937 */           return forkJoinTask;
/*      */         }
/*      */       }  
/* 2940 */     return null;
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
/*      */   protected int drainTasksTo(Collection<? super ForkJoinTask<?>> paramCollection) {
/* 2961 */     byte b = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2963 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2964 */       for (byte b1 = 0; b1 < arrayOfWorkQueue.length; b1++) {
/* 2965 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b1]) != null) {
/* 2966 */           ForkJoinTask<?> forkJoinTask; while ((forkJoinTask = workQueue.poll()) != null) {
/* 2967 */             paramCollection.add(forkJoinTask);
/* 2968 */             b++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/* 2973 */     return b;
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
/*      */   public String toString() {
/* 2985 */     long l1 = 0L, l2 = 0L; byte b = 0;
/* 2986 */     AtomicLong atomicLong = this.stealCounter;
/* 2987 */     long l3 = (atomicLong == null) ? 0L : atomicLong.get();
/* 2988 */     long l4 = this.ctl;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2990 */     if ((arrayOfWorkQueue = this.workQueues) != null)
/* 2991 */       for (byte b1 = 0; b1 < arrayOfWorkQueue.length; b1++) {
/* 2992 */         WorkQueue workQueue; if ((workQueue = arrayOfWorkQueue[b1]) != null) {
/* 2993 */           int n = workQueue.queueSize();
/* 2994 */           if ((b1 & 0x1) == 0) {
/* 2995 */             l2 += n;
/*      */           } else {
/* 2997 */             l1 += n;
/* 2998 */             l3 += workQueue.nsteals;
/* 2999 */             if (workQueue.isApparentlyUnblocked()) {
/* 3000 */               b++;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }  
/* 3005 */     int i = this.config & 0xFFFF;
/* 3006 */     int j = i + (short)(int)(l4 >>> 32L);
/* 3007 */     int k = i + (int)(l4 >> 48L);
/* 3008 */     if (k < 0)
/* 3009 */       k = 0; 
/* 3010 */     int m = this.runState;
/* 3011 */     String str = ((m & 0x40000000) != 0) ? "Terminated" : (((m & 0x20000000) != 0) ? "Terminating" : (((m & Integer.MIN_VALUE) != 0) ? "Shutting down" : "Running"));
/*      */ 
/*      */ 
/*      */     
/* 3015 */     return super.toString() + "[" + str + ", parallelism = " + i + ", size = " + j + ", active = " + k + ", running = " + b + ", steals = " + l3 + ", tasks = " + l1 + ", submissions = " + l2 + "]";
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
/*      */   public void shutdown() {
/* 3042 */     checkPermission();
/* 3043 */     tryTerminate(false, true);
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
/*      */   public List<Runnable> shutdownNow() {
/* 3065 */     checkPermission();
/* 3066 */     tryTerminate(true, true);
/* 3067 */     return Collections.emptyList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTerminated() {
/* 3076 */     return ((this.runState & 0x40000000) != 0);
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
/*      */   public boolean isTerminating() {
/* 3093 */     int i = this.runState;
/* 3094 */     return ((i & 0x20000000) != 0 && (i & 0x40000000) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShutdown() {
/* 3103 */     return ((this.runState & Integer.MIN_VALUE) != 0);
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
/*      */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 3122 */     if (Thread.interrupted())
/* 3123 */       throw new InterruptedException(); 
/* 3124 */     if (this == common) {
/* 3125 */       awaitQuiescence(paramLong, paramTimeUnit);
/* 3126 */       return false;
/*      */     } 
/* 3128 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 3129 */     if (isTerminated())
/* 3130 */       return true; 
/* 3131 */     if (l1 <= 0L)
/* 3132 */       return false; 
/* 3133 */     long l2 = System.nanoTime() + l1;
/* 3134 */     synchronized (this) {
/*      */       while (true) {
/* 3136 */         if (isTerminated())
/* 3137 */           return true; 
/* 3138 */         if (l1 <= 0L)
/* 3139 */           return false; 
/* 3140 */         long l = TimeUnit.NANOSECONDS.toMillis(l1);
/* 3141 */         wait((l > 0L) ? l : 1L);
/* 3142 */         l1 = l2 - System.nanoTime();
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
/*      */   public boolean awaitQuiescence(long paramLong, TimeUnit paramTimeUnit) {
/* 3159 */     long l1 = paramTimeUnit.toNanos(paramLong);
/*      */     
/* 3161 */     Thread thread = Thread.currentThread(); ForkJoinWorkerThread forkJoinWorkerThread;
/* 3162 */     if (thread instanceof ForkJoinWorkerThread && (forkJoinWorkerThread = (ForkJoinWorkerThread)thread).pool == this) {
/*      */       
/* 3164 */       helpQuiescePool(forkJoinWorkerThread.workQueue);
/* 3165 */       return true;
/*      */     } 
/* 3167 */     long l2 = System.nanoTime();
/*      */     
/* 3169 */     byte b = 0;
/* 3170 */     boolean bool = true; WorkQueue[] arrayOfWorkQueue; int i;
/* 3171 */     while (!isQuiescent() && (arrayOfWorkQueue = this.workQueues) != null && (i = arrayOfWorkQueue.length - 1) >= 0) {
/*      */       
/* 3173 */       if (!bool) {
/* 3174 */         if (System.nanoTime() - l2 > l1)
/* 3175 */           return false; 
/* 3176 */         Thread.yield();
/*      */       } 
/* 3178 */       bool = false;
/* 3179 */       for (int j = i + 1 << 2; j >= 0; j--) {
/*      */         WorkQueue workQueue; int k; int m;
/* 3181 */         if ((m = b++ & i) <= i && m >= 0 && (workQueue = arrayOfWorkQueue[m]) != null && (k = workQueue.base) - workQueue.top < 0) {
/*      */           
/* 3183 */           bool = true; ForkJoinTask<?> forkJoinTask;
/* 3184 */           if ((forkJoinTask = workQueue.pollAt(k)) != null)
/* 3185 */             forkJoinTask.doExec(); 
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3190 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void quiesceCommonPool() {
/* 3198 */     common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
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
/*      */   public static void managedBlock(ManagedBlocker paramManagedBlocker) throws InterruptedException {
/* 3305 */     Thread thread = Thread.currentThread(); ForkJoinPool forkJoinPool; ForkJoinWorkerThread forkJoinWorkerThread;
/* 3306 */     if (thread instanceof ForkJoinWorkerThread && (forkJoinPool = (forkJoinWorkerThread = (ForkJoinWorkerThread)thread).pool) != null) {
/*      */       
/* 3308 */       WorkQueue workQueue = forkJoinWorkerThread.workQueue;
/* 3309 */       while (!paramManagedBlocker.isReleasable()) {
/* 3310 */         if (forkJoinPool.tryCompensate(workQueue)) { try { do {
/*      */             
/* 3312 */             } while (!paramManagedBlocker.isReleasable() && 
/* 3313 */               !paramManagedBlocker.block()); }
/*      */           finally
/* 3315 */           { U.getAndAddLong(forkJoinPool, CTL, 281474976710656L); }
/*      */            break; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       do {
/*      */       
/* 3322 */       } while (!paramManagedBlocker.isReleasable() && 
/* 3323 */         !paramManagedBlocker.block());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT) {
/* 3332 */     return new ForkJoinTask.AdaptedRunnable<>(paramRunnable, paramT);
/*      */   }
/*      */   
/*      */   protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable) {
/* 3336 */     return new ForkJoinTask.AdaptedCallable<>(paramCallable);
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
/*      */   static {
/*      */     try {
/* 3357 */       U = Unsafe.getUnsafe();
/* 3358 */       Class<ForkJoinPool> clazz = ForkJoinPool.class;
/*      */       
/* 3360 */       CTL = U.objectFieldOffset(clazz.getDeclaredField("ctl"));
/*      */       
/* 3362 */       RUNSTATE = U.objectFieldOffset(clazz.getDeclaredField("runState"));
/*      */       
/* 3364 */       STEALCOUNTER = U.objectFieldOffset(clazz.getDeclaredField("stealCounter"));
/* 3365 */       Class<Thread> clazz1 = Thread.class;
/*      */       
/* 3367 */       PARKBLOCKER = U.objectFieldOffset(clazz1.getDeclaredField("parkBlocker"));
/* 3368 */       Class<WorkQueue> clazz2 = WorkQueue.class;
/*      */       
/* 3370 */       QTOP = U.objectFieldOffset(clazz2.getDeclaredField("top"));
/*      */       
/* 3372 */       QLOCK = U.objectFieldOffset(clazz2.getDeclaredField("qlock"));
/*      */       
/* 3374 */       QSCANSTATE = U.objectFieldOffset(clazz2.getDeclaredField("scanState"));
/*      */       
/* 3376 */       QPARKER = U.objectFieldOffset(clazz2.getDeclaredField("parker"));
/*      */       
/* 3378 */       QCURRENTSTEAL = U.objectFieldOffset(clazz2.getDeclaredField("currentSteal"));
/*      */       
/* 3380 */       QCURRENTJOIN = U.objectFieldOffset(clazz2.getDeclaredField("currentJoin"));
/* 3381 */       Class<ForkJoinTask[]> clazz3 = ForkJoinTask[].class;
/* 3382 */       ABASE = U.arrayBaseOffset(clazz3);
/* 3383 */       int j = U.arrayIndexScale(clazz3);
/* 3384 */       if ((j & j - 1) != 0)
/* 3385 */         throw new Error("data type scale not a power of two"); 
/* 3386 */       ASHIFT = 31 - Integer.numberOfLeadingZeros(j);
/* 3387 */     } catch (Exception exception) {
/* 3388 */       throw new Error(exception);
/*      */     } 
/*      */   }
/* 3391 */   private static int poolNumberSequence; private static final long IDLE_TIMEOUT = 2000000000L; private static final long TIMEOUT_SLOP = 20000000L; private static final int DEFAULT_COMMON_MAX_SPARES = 256; private static final int SPINS = 0; private static final int SEED_INCREMENT = -1640531527; private static final long SP_MASK = 4294967295L; private static final long UC_MASK = -4294967296L; private static final int AC_SHIFT = 48; private static final long AC_UNIT = 281474976710656L; private static final long AC_MASK = -281474976710656L; private static int commonMaxSpares = 256; private static final int TC_SHIFT = 32; private static final long TC_UNIT = 4294967296L; private static final long TC_MASK = 281470681743360L; private static final long ADD_WORKER = 140737488355328L; private static final int RSLOCK = 1; private static final int RSIGNAL = 2; private static final int STARTED = 4; private static final int STOP = 536870912; private static final int TERMINATED = 1073741824; private static final int SHUTDOWN = -2147483648; static {
/* 3392 */     defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory();
/*      */     
/* 3394 */     modifyThreadPermission = new RuntimePermission("modifyThread");
/*      */ 
/*      */     
/* 3397 */     common = AccessController.<ForkJoinPool>doPrivileged(new PrivilegedAction<ForkJoinPool>() {
/* 3398 */           public ForkJoinPool run() { return ForkJoinPool.makeCommonPool(); } });
/* 3399 */     int i = common.config & 0xFFFF;
/* 3400 */     commonParallelism = (i > 0) ? i : 1;
/*      */   }
/*      */   volatile long ctl; volatile int runState; final int config; int indexSeed; volatile WorkQueue[] workQueues; final ForkJoinWorkerThreadFactory factory; final Thread.UncaughtExceptionHandler ueh; final String workerNamePrefix; volatile AtomicLong stealCounter; private static final Unsafe U; private static final int ABASE; private static final int ASHIFT; private static final long CTL; private static final long RUNSTATE; private static final long STEALCOUNTER; private static final long PARKBLOCKER; private static final long QTOP; private static final long QLOCK; private static final long QSCANSTATE;
/*      */   private static final long QPARKER;
/*      */   private static final long QCURRENTSTEAL;
/*      */   private static final long QCURRENTJOIN;
/*      */   
/*      */   private static ForkJoinPool makeCommonPool() {
/* 3408 */     int i = -1;
/* 3409 */     ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = null;
/* 3410 */     Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
/*      */     
/*      */     try {
/* 3413 */       String str1 = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
/*      */       
/* 3415 */       String str2 = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
/*      */       
/* 3417 */       String str3 = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
/* 3418 */       if (str1 != null)
/* 3419 */         i = Integer.parseInt(str1); 
/* 3420 */       if (str2 != null)
/*      */       {
/* 3422 */         forkJoinWorkerThreadFactory = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(str2).newInstance(); } 
/* 3423 */       if (str3 != null)
/*      */       {
/* 3425 */         uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(str3).newInstance(); } 
/* 3426 */     } catch (Exception exception) {}
/*      */     
/* 3428 */     if (forkJoinWorkerThreadFactory == null)
/* 3429 */       if (System.getSecurityManager() == null) {
/* 3430 */         forkJoinWorkerThreadFactory = defaultForkJoinWorkerThreadFactory;
/*      */       } else {
/* 3432 */         forkJoinWorkerThreadFactory = new InnocuousForkJoinWorkerThreadFactory();
/*      */       }  
/* 3434 */     if (i < 0 && (
/* 3435 */       i = Runtime.getRuntime().availableProcessors() - 1) <= 0)
/* 3436 */       i = 1; 
/* 3437 */     if (i > 32767)
/* 3438 */       i = 32767; 
/* 3439 */     return new ForkJoinPool(i, forkJoinWorkerThreadFactory, uncaughtExceptionHandler, 0, "ForkJoinPool.commonPool-worker-");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class InnocuousForkJoinWorkerThreadFactory
/*      */     implements ForkJoinWorkerThreadFactory
/*      */   {
/*      */     private static final AccessControlContext innocuousAcc;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 3455 */       Permissions permissions = new Permissions();
/* 3456 */       permissions.add(ForkJoinPool.modifyThreadPermission);
/* 3457 */       permissions.add(new RuntimePermission("enableContextClassLoaderOverride"));
/*      */       
/* 3459 */       permissions.add(new RuntimePermission("modifyThreadGroup"));
/*      */       
/* 3461 */       innocuousAcc = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, permissions) });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final ForkJoinWorkerThread newThread(final ForkJoinPool pool) {
/* 3467 */       return 
/* 3468 */         AccessController.<ForkJoinWorkerThread.InnocuousForkJoinWorkerThread>doPrivileged((PrivilegedAction)new PrivilegedAction<ForkJoinWorkerThread>()
/*      */           {
/*      */             public ForkJoinWorkerThread run() {
/* 3471 */               return new ForkJoinWorkerThread.InnocuousForkJoinWorkerThread(pool);
/*      */             }
/*      */           },  innocuousAcc);
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface ManagedBlocker {
/*      */     boolean block() throws InterruptedException;
/*      */     
/*      */     boolean isReleasable();
/*      */   }
/*      */   
/*      */   public static interface ForkJoinWorkerThreadFactory {
/*      */     ForkJoinWorkerThread newThread(ForkJoinPool param1ForkJoinPool);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ForkJoinPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */