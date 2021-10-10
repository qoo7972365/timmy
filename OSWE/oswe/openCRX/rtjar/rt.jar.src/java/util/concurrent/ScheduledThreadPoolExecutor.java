/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.concurrent.atomic.AtomicLong;
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
/*      */ public class ScheduledThreadPoolExecutor
/*      */   extends ThreadPoolExecutor
/*      */   implements ScheduledExecutorService
/*      */ {
/*      */   private volatile boolean continueExistingPeriodicTasksAfterShutdown;
/*      */   private volatile boolean executeExistingDelayedTasksAfterShutdown = true;
/*      */   private volatile boolean removeOnCancel = false;
/*  171 */   private static final AtomicLong sequencer = new AtomicLong();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final long now() {
/*  177 */     return System.nanoTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class ScheduledFutureTask<V>
/*      */     extends FutureTask<V>
/*      */     implements RunnableScheduledFuture<V>
/*      */   {
/*      */     private final long sequenceNumber;
/*      */ 
/*      */ 
/*      */     
/*      */     private long time;
/*      */ 
/*      */ 
/*      */     
/*      */     private final long period;
/*      */ 
/*      */     
/*  198 */     RunnableScheduledFuture<V> outerTask = this;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int heapIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ScheduledFutureTask(Runnable param1Runnable, V param1V, long param1Long) {
/*  209 */       super(param1Runnable, param1V);
/*  210 */       this.time = param1Long;
/*  211 */       this.period = 0L;
/*  212 */       this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ScheduledFutureTask(Runnable param1Runnable, V param1V, long param1Long1, long param1Long2) {
/*  219 */       super(param1Runnable, param1V);
/*  220 */       this.time = param1Long1;
/*  221 */       this.period = param1Long2;
/*  222 */       this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ScheduledFutureTask(Callable<V> param1Callable, long param1Long) {
/*  229 */       super(param1Callable);
/*  230 */       this.time = param1Long;
/*  231 */       this.period = 0L;
/*  232 */       this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
/*      */     }
/*      */     
/*      */     public long getDelay(TimeUnit param1TimeUnit) {
/*  236 */       return param1TimeUnit.convert(this.time - ScheduledThreadPoolExecutor.this.now(), TimeUnit.NANOSECONDS);
/*      */     }
/*      */     
/*      */     public int compareTo(Delayed param1Delayed) {
/*  240 */       if (param1Delayed == this)
/*  241 */         return 0; 
/*  242 */       if (param1Delayed instanceof ScheduledFutureTask) {
/*  243 */         ScheduledFutureTask scheduledFutureTask = (ScheduledFutureTask)param1Delayed;
/*  244 */         long l1 = this.time - scheduledFutureTask.time;
/*  245 */         if (l1 < 0L)
/*  246 */           return -1; 
/*  247 */         if (l1 > 0L)
/*  248 */           return 1; 
/*  249 */         if (this.sequenceNumber < scheduledFutureTask.sequenceNumber) {
/*  250 */           return -1;
/*      */         }
/*  252 */         return 1;
/*      */       } 
/*  254 */       long l = getDelay(TimeUnit.NANOSECONDS) - param1Delayed.getDelay(TimeUnit.NANOSECONDS);
/*  255 */       return (l < 0L) ? -1 : ((l > 0L) ? 1 : 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isPeriodic() {
/*  264 */       return (this.period != 0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setNextRunTime() {
/*  271 */       long l = this.period;
/*  272 */       if (l > 0L) {
/*  273 */         this.time += l;
/*      */       } else {
/*  275 */         this.time = ScheduledThreadPoolExecutor.this.triggerTime(-l);
/*      */       } 
/*      */     }
/*      */     public boolean cancel(boolean param1Boolean) {
/*  279 */       boolean bool = super.cancel(param1Boolean);
/*  280 */       if (bool && ScheduledThreadPoolExecutor.this.removeOnCancel && this.heapIndex >= 0)
/*  281 */         ScheduledThreadPoolExecutor.this.remove(this); 
/*  282 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/*  289 */       boolean bool = isPeriodic();
/*  290 */       if (!ScheduledThreadPoolExecutor.this.canRunInCurrentRunState(bool)) {
/*  291 */         cancel(false);
/*  292 */       } else if (!bool) {
/*  293 */         run();
/*  294 */       } else if (runAndReset()) {
/*  295 */         setNextRunTime();
/*  296 */         ScheduledThreadPoolExecutor.this.reExecutePeriodic(this.outerTask);
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
/*      */   boolean canRunInCurrentRunState(boolean paramBoolean) {
/*  308 */     return isRunningOrShutdown(paramBoolean ? this.continueExistingPeriodicTasksAfterShutdown : this.executeExistingDelayedTasksAfterShutdown);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void delayedExecute(RunnableScheduledFuture<?> paramRunnableScheduledFuture) {
/*  325 */     if (isShutdown()) {
/*  326 */       reject(paramRunnableScheduledFuture);
/*      */     } else {
/*  328 */       super.getQueue().add(paramRunnableScheduledFuture);
/*  329 */       if (isShutdown() && 
/*  330 */         !canRunInCurrentRunState(paramRunnableScheduledFuture.isPeriodic()) && 
/*  331 */         remove(paramRunnableScheduledFuture)) {
/*  332 */         paramRunnableScheduledFuture.cancel(false);
/*      */       } else {
/*  334 */         ensurePrestart();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void reExecutePeriodic(RunnableScheduledFuture<?> paramRunnableScheduledFuture) {
/*  345 */     if (canRunInCurrentRunState(true)) {
/*  346 */       super.getQueue().add(paramRunnableScheduledFuture);
/*  347 */       if (!canRunInCurrentRunState(true) && remove(paramRunnableScheduledFuture)) {
/*  348 */         paramRunnableScheduledFuture.cancel(false);
/*      */       } else {
/*  350 */         ensurePrestart();
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
/*      */   void onShutdown() {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokespecial getQueue : ()Ljava/util/concurrent/BlockingQueue;
/*      */     //   4: astore_1
/*      */     //   5: aload_0
/*      */     //   6: invokevirtual getExecuteExistingDelayedTasksAfterShutdownPolicy : ()Z
/*      */     //   9: istore_2
/*      */     //   10: aload_0
/*      */     //   11: invokevirtual getContinueExistingPeriodicTasksAfterShutdownPolicy : ()Z
/*      */     //   14: istore_3
/*      */     //   15: iload_2
/*      */     //   16: ifne -> 88
/*      */     //   19: iload_3
/*      */     //   20: ifne -> 88
/*      */     //   23: aload_1
/*      */     //   24: invokeinterface toArray : ()[Ljava/lang/Object;
/*      */     //   29: astore #4
/*      */     //   31: aload #4
/*      */     //   33: arraylength
/*      */     //   34: istore #5
/*      */     //   36: iconst_0
/*      */     //   37: istore #6
/*      */     //   39: iload #6
/*      */     //   41: iload #5
/*      */     //   43: if_icmpge -> 79
/*      */     //   46: aload #4
/*      */     //   48: iload #6
/*      */     //   50: aaload
/*      */     //   51: astore #7
/*      */     //   53: aload #7
/*      */     //   55: instanceof java/util/concurrent/RunnableScheduledFuture
/*      */     //   58: ifeq -> 73
/*      */     //   61: aload #7
/*      */     //   63: checkcast java/util/concurrent/RunnableScheduledFuture
/*      */     //   66: iconst_0
/*      */     //   67: invokeinterface cancel : (Z)Z
/*      */     //   72: pop
/*      */     //   73: iinc #6, 1
/*      */     //   76: goto -> 39
/*      */     //   79: aload_1
/*      */     //   80: invokeinterface clear : ()V
/*      */     //   85: goto -> 190
/*      */     //   88: aload_1
/*      */     //   89: invokeinterface toArray : ()[Ljava/lang/Object;
/*      */     //   94: astore #4
/*      */     //   96: aload #4
/*      */     //   98: arraylength
/*      */     //   99: istore #5
/*      */     //   101: iconst_0
/*      */     //   102: istore #6
/*      */     //   104: iload #6
/*      */     //   106: iload #5
/*      */     //   108: if_icmpge -> 190
/*      */     //   111: aload #4
/*      */     //   113: iload #6
/*      */     //   115: aaload
/*      */     //   116: astore #7
/*      */     //   118: aload #7
/*      */     //   120: instanceof java/util/concurrent/RunnableScheduledFuture
/*      */     //   123: ifeq -> 184
/*      */     //   126: aload #7
/*      */     //   128: checkcast java/util/concurrent/RunnableScheduledFuture
/*      */     //   131: astore #8
/*      */     //   133: aload #8
/*      */     //   135: invokeinterface isPeriodic : ()Z
/*      */     //   140: ifeq -> 150
/*      */     //   143: iload_3
/*      */     //   144: ifne -> 154
/*      */     //   147: goto -> 164
/*      */     //   150: iload_2
/*      */     //   151: ifeq -> 164
/*      */     //   154: aload #8
/*      */     //   156: invokeinterface isCancelled : ()Z
/*      */     //   161: ifeq -> 184
/*      */     //   164: aload_1
/*      */     //   165: aload #8
/*      */     //   167: invokeinterface remove : (Ljava/lang/Object;)Z
/*      */     //   172: ifeq -> 184
/*      */     //   175: aload #8
/*      */     //   177: iconst_0
/*      */     //   178: invokeinterface cancel : (Z)Z
/*      */     //   183: pop
/*      */     //   184: iinc #6, 1
/*      */     //   187: goto -> 104
/*      */     //   190: aload_0
/*      */     //   191: invokevirtual tryTerminate : ()V
/*      */     //   194: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #359	-> 0
/*      */     //   #360	-> 5
/*      */     //   #361	-> 6
/*      */     //   #362	-> 10
/*      */     //   #363	-> 11
/*      */     //   #364	-> 15
/*      */     //   #365	-> 23
/*      */     //   #366	-> 53
/*      */     //   #367	-> 61
/*      */     //   #365	-> 73
/*      */     //   #368	-> 79
/*      */     //   #372	-> 88
/*      */     //   #373	-> 118
/*      */     //   #374	-> 126
/*      */     //   #376	-> 133
/*      */     //   #377	-> 156
/*      */     //   #378	-> 164
/*      */     //   #379	-> 175
/*      */     //   #372	-> 184
/*      */     //   #384	-> 190
/*      */     //   #385	-> 194
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <V> RunnableScheduledFuture<V> decorateTask(Runnable paramRunnable, RunnableScheduledFuture<V> paramRunnableScheduledFuture) {
/*  401 */     return paramRunnableScheduledFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <V> RunnableScheduledFuture<V> decorateTask(Callable<V> paramCallable, RunnableScheduledFuture<V> paramRunnableScheduledFuture) {
/*  418 */     return paramRunnableScheduledFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledThreadPoolExecutor(int paramInt) {
/*  430 */     super(paramInt, 2147483647, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledThreadPoolExecutor(int paramInt, ThreadFactory paramThreadFactory) {
/*  447 */     super(paramInt, 2147483647, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), paramThreadFactory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledThreadPoolExecutor(int paramInt, RejectedExecutionHandler paramRejectedExecutionHandler) {
/*  464 */     super(paramInt, 2147483647, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), paramRejectedExecutionHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledThreadPoolExecutor(int paramInt, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler) {
/*  485 */     super(paramInt, 2147483647, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), paramThreadFactory, paramRejectedExecutionHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long triggerTime(long paramLong, TimeUnit paramTimeUnit) {
/*  493 */     return triggerTime(paramTimeUnit.toNanos((paramLong < 0L) ? 0L : paramLong));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long triggerTime(long paramLong) {
/*  500 */     return now() + ((paramLong < 4611686018427387903L) ? paramLong : 
/*  501 */       overflowFree(paramLong));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long overflowFree(long paramLong) {
/*  512 */     Delayed delayed = (Delayed)super.getQueue().peek();
/*  513 */     if (delayed != null) {
/*  514 */       long l = delayed.getDelay(TimeUnit.NANOSECONDS);
/*  515 */       if (l < 0L && paramLong - l < 0L)
/*  516 */         paramLong = Long.MAX_VALUE + l; 
/*      */     } 
/*  518 */     return paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/*  528 */     if (paramRunnable == null || paramTimeUnit == null)
/*  529 */       throw new NullPointerException(); 
/*  530 */     RunnableScheduledFuture<?> runnableScheduledFuture = decorateTask(paramRunnable, new ScheduledFutureTask(paramRunnable, null, 
/*      */           
/*  532 */           triggerTime(paramLong, paramTimeUnit)));
/*  533 */     delayedExecute(runnableScheduledFuture);
/*  534 */     return runnableScheduledFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit) {
/*  544 */     if (paramCallable == null || paramTimeUnit == null)
/*  545 */       throw new NullPointerException(); 
/*  546 */     RunnableScheduledFuture<V> runnableScheduledFuture = decorateTask(paramCallable, new ScheduledFutureTask<>(paramCallable, 
/*      */           
/*  548 */           triggerTime(paramLong, paramTimeUnit)));
/*  549 */     delayedExecute(runnableScheduledFuture);
/*  550 */     return runnableScheduledFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/*  562 */     if (paramRunnable == null || paramTimeUnit == null)
/*  563 */       throw new NullPointerException(); 
/*  564 */     if (paramLong2 <= 0L) {
/*  565 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  570 */     ScheduledFutureTask<?> scheduledFutureTask = new ScheduledFutureTask(paramRunnable, null, triggerTime(paramLong1, paramTimeUnit), paramTimeUnit.toNanos(paramLong2));
/*  571 */     RunnableScheduledFuture<?> runnableScheduledFuture = decorateTask(paramRunnable, scheduledFutureTask);
/*  572 */     scheduledFutureTask.outerTask = runnableScheduledFuture;
/*  573 */     delayedExecute(runnableScheduledFuture);
/*  574 */     return runnableScheduledFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/*  586 */     if (paramRunnable == null || paramTimeUnit == null)
/*  587 */       throw new NullPointerException(); 
/*  588 */     if (paramLong2 <= 0L) {
/*  589 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  594 */     ScheduledFutureTask<?> scheduledFutureTask = new ScheduledFutureTask(paramRunnable, null, triggerTime(paramLong1, paramTimeUnit), paramTimeUnit.toNanos(-paramLong2));
/*  595 */     RunnableScheduledFuture<?> runnableScheduledFuture = decorateTask(paramRunnable, scheduledFutureTask);
/*  596 */     scheduledFutureTask.outerTask = runnableScheduledFuture;
/*  597 */     delayedExecute(runnableScheduledFuture);
/*  598 */     return runnableScheduledFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  622 */     schedule(paramRunnable, 0L, TimeUnit.NANOSECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<?> submit(Runnable paramRunnable) {
/*  632 */     return schedule(paramRunnable, 0L, TimeUnit.NANOSECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Future<T> submit(Runnable paramRunnable, T paramT) {
/*  640 */     return schedule(Executors.callable(paramRunnable, paramT), 0L, TimeUnit.NANOSECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Future<T> submit(Callable<T> paramCallable) {
/*  648 */     return schedule(paramCallable, 0L, TimeUnit.NANOSECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContinueExistingPeriodicTasksAfterShutdownPolicy(boolean paramBoolean) {
/*  663 */     this.continueExistingPeriodicTasksAfterShutdown = paramBoolean;
/*  664 */     if (!paramBoolean && isShutdown()) {
/*  665 */       onShutdown();
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
/*      */   public boolean getContinueExistingPeriodicTasksAfterShutdownPolicy() {
/*  680 */     return this.continueExistingPeriodicTasksAfterShutdown;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExecuteExistingDelayedTasksAfterShutdownPolicy(boolean paramBoolean) {
/*  695 */     this.executeExistingDelayedTasksAfterShutdown = paramBoolean;
/*  696 */     if (!paramBoolean && isShutdown()) {
/*  697 */       onShutdown();
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
/*      */   public boolean getExecuteExistingDelayedTasksAfterShutdownPolicy() {
/*  712 */     return this.executeExistingDelayedTasksAfterShutdown;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRemoveOnCancelPolicy(boolean paramBoolean) {
/*  725 */     this.removeOnCancel = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRemoveOnCancelPolicy() {
/*  739 */     return this.removeOnCancel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  761 */     super.shutdown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  786 */     return super.shutdownNow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  801 */     return super.getQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DelayedWorkQueue
/*      */     extends AbstractQueue<Runnable>
/*      */     implements BlockingQueue<Runnable>
/*      */   {
/*      */     private static final int INITIAL_CAPACITY = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  836 */     private RunnableScheduledFuture<?>[] queue = (RunnableScheduledFuture<?>[])new RunnableScheduledFuture[16];
/*      */     
/*  838 */     private final ReentrantLock lock = new ReentrantLock();
/*  839 */     private int size = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  857 */     private Thread leader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  863 */     private final Condition available = this.lock.newCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setIndex(RunnableScheduledFuture<?> param1RunnableScheduledFuture, int param1Int) {
/*  869 */       if (param1RunnableScheduledFuture instanceof ScheduledThreadPoolExecutor.ScheduledFutureTask) {
/*  870 */         ((ScheduledThreadPoolExecutor.ScheduledFutureTask)param1RunnableScheduledFuture).heapIndex = param1Int;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void siftUp(int param1Int, RunnableScheduledFuture<?> param1RunnableScheduledFuture) {
/*  878 */       while (param1Int > 0) {
/*  879 */         int i = param1Int - 1 >>> 1;
/*  880 */         RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[i];
/*  881 */         if (param1RunnableScheduledFuture.compareTo(runnableScheduledFuture) >= 0)
/*      */           break; 
/*  883 */         this.queue[param1Int] = runnableScheduledFuture;
/*  884 */         setIndex(runnableScheduledFuture, param1Int);
/*  885 */         param1Int = i;
/*      */       } 
/*  887 */       this.queue[param1Int] = param1RunnableScheduledFuture;
/*  888 */       setIndex(param1RunnableScheduledFuture, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void siftDown(int param1Int, RunnableScheduledFuture<?> param1RunnableScheduledFuture) {
/*  896 */       int i = this.size >>> 1;
/*  897 */       while (param1Int < i) {
/*  898 */         int j = (param1Int << 1) + 1;
/*  899 */         RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[j];
/*  900 */         int k = j + 1;
/*  901 */         if (k < this.size && runnableScheduledFuture.compareTo(this.queue[k]) > 0)
/*  902 */           runnableScheduledFuture = this.queue[j = k]; 
/*  903 */         if (param1RunnableScheduledFuture.compareTo(runnableScheduledFuture) <= 0)
/*      */           break; 
/*  905 */         this.queue[param1Int] = runnableScheduledFuture;
/*  906 */         setIndex(runnableScheduledFuture, param1Int);
/*  907 */         param1Int = j;
/*      */       } 
/*  909 */       this.queue[param1Int] = param1RunnableScheduledFuture;
/*  910 */       setIndex(param1RunnableScheduledFuture, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void grow() {
/*  917 */       int i = this.queue.length;
/*  918 */       int j = i + (i >> 1);
/*  919 */       if (j < 0)
/*  920 */         j = Integer.MAX_VALUE; 
/*  921 */       this.queue = (RunnableScheduledFuture<?>[])Arrays.<RunnableScheduledFuture>copyOf((RunnableScheduledFuture[])this.queue, j);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int indexOf(Object param1Object) {
/*  928 */       if (param1Object != null)
/*  929 */         if (param1Object instanceof ScheduledThreadPoolExecutor.ScheduledFutureTask) {
/*  930 */           int i = ((ScheduledThreadPoolExecutor.ScheduledFutureTask)param1Object).heapIndex;
/*      */ 
/*      */           
/*  933 */           if (i >= 0 && i < this.size && this.queue[i] == param1Object)
/*  934 */             return i; 
/*      */         } else {
/*  936 */           for (byte b = 0; b < this.size; b++) {
/*  937 */             if (param1Object.equals(this.queue[b]))
/*  938 */               return b; 
/*      */           } 
/*      */         }  
/*  941 */       return -1;
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/*  945 */       ReentrantLock reentrantLock = this.lock;
/*  946 */       reentrantLock.lock();
/*      */       try {
/*  948 */         return (indexOf(param1Object) != -1);
/*      */       } finally {
/*  950 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object) {
/*  955 */       ReentrantLock reentrantLock = this.lock;
/*  956 */       reentrantLock.lock();
/*      */       try {
/*  958 */         int i = indexOf(param1Object);
/*  959 */         if (i < 0) {
/*  960 */           return false;
/*      */         }
/*  962 */         setIndex(this.queue[i], -1);
/*  963 */         int j = --this.size;
/*  964 */         RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[j];
/*  965 */         this.queue[j] = null;
/*  966 */         if (j != i) {
/*  967 */           siftDown(i, runnableScheduledFuture);
/*  968 */           if (this.queue[i] == runnableScheduledFuture)
/*  969 */             siftUp(i, runnableScheduledFuture); 
/*      */         } 
/*  971 */         return true;
/*      */       } finally {
/*  973 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  978 */       ReentrantLock reentrantLock = this.lock;
/*  979 */       reentrantLock.lock();
/*      */       try {
/*  981 */         return this.size;
/*      */       } finally {
/*  983 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  988 */       return (size() == 0);
/*      */     }
/*      */     
/*      */     public int remainingCapacity() {
/*  992 */       return Integer.MAX_VALUE;
/*      */     }
/*      */     
/*      */     public RunnableScheduledFuture<?> peek() {
/*  996 */       ReentrantLock reentrantLock = this.lock;
/*  997 */       reentrantLock.lock();
/*      */       try {
/*  999 */         return this.queue[0];
/*      */       } finally {
/* 1001 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean offer(Runnable param1Runnable) {
/* 1006 */       if (param1Runnable == null)
/* 1007 */         throw new NullPointerException(); 
/* 1008 */       RunnableScheduledFuture<?> runnableScheduledFuture = (RunnableScheduledFuture)param1Runnable;
/* 1009 */       ReentrantLock reentrantLock = this.lock;
/* 1010 */       reentrantLock.lock();
/*      */       try {
/* 1012 */         int i = this.size;
/* 1013 */         if (i >= this.queue.length)
/* 1014 */           grow(); 
/* 1015 */         this.size = i + 1;
/* 1016 */         if (i == 0) {
/* 1017 */           this.queue[0] = runnableScheduledFuture;
/* 1018 */           setIndex(runnableScheduledFuture, 0);
/*      */         } else {
/* 1020 */           siftUp(i, runnableScheduledFuture);
/*      */         } 
/* 1022 */         if (this.queue[0] == runnableScheduledFuture) {
/* 1023 */           this.leader = null;
/* 1024 */           this.available.signal();
/*      */         } 
/*      */       } finally {
/* 1027 */         reentrantLock.unlock();
/*      */       } 
/* 1029 */       return true;
/*      */     }
/*      */     
/*      */     public void put(Runnable param1Runnable) {
/* 1033 */       offer(param1Runnable);
/*      */     }
/*      */     
/*      */     public boolean add(Runnable param1Runnable) {
/* 1037 */       return offer(param1Runnable);
/*      */     }
/*      */     
/*      */     public boolean offer(Runnable param1Runnable, long param1Long, TimeUnit param1TimeUnit) {
/* 1041 */       return offer(param1Runnable);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private RunnableScheduledFuture<?> finishPoll(RunnableScheduledFuture<?> param1RunnableScheduledFuture) {
/* 1051 */       int i = --this.size;
/* 1052 */       RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[i];
/* 1053 */       this.queue[i] = null;
/* 1054 */       if (i != 0)
/* 1055 */         siftDown(0, runnableScheduledFuture); 
/* 1056 */       setIndex(param1RunnableScheduledFuture, -1);
/* 1057 */       return param1RunnableScheduledFuture;
/*      */     }
/*      */     
/*      */     public RunnableScheduledFuture<?> poll() {
/* 1061 */       ReentrantLock reentrantLock = this.lock;
/* 1062 */       reentrantLock.lock();
/*      */       try {
/* 1064 */         RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
/* 1065 */         if (runnableScheduledFuture == null || runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L) {
/* 1066 */           return null;
/*      */         }
/* 1068 */         return finishPoll(runnableScheduledFuture);
/*      */       } finally {
/* 1070 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public RunnableScheduledFuture<?> take() throws InterruptedException {
/* 1075 */       ReentrantLock reentrantLock = this.lock;
/* 1076 */       reentrantLock.lockInterruptibly();
/*      */       try {
/*      */         while (true) {
/* 1079 */           RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
/* 1080 */           if (runnableScheduledFuture == null) {
/* 1081 */             this.available.await(); continue;
/*      */           } 
/* 1083 */           long l = runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS);
/* 1084 */           if (l <= 0L)
/* 1085 */             return finishPoll(runnableScheduledFuture); 
/* 1086 */           runnableScheduledFuture = null;
/* 1087 */           if (this.leader != null) {
/* 1088 */             this.available.await(); continue;
/*      */           } 
/* 1090 */           Thread thread = Thread.currentThread();
/* 1091 */           this.leader = thread;
/*      */           try {
/* 1093 */             this.available.awaitNanos(l);
/*      */           } finally {
/* 1095 */             if (this.leader == thread) {
/* 1096 */               this.leader = null;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } finally {
/*      */         
/* 1102 */         if (this.leader == null && this.queue[0] != null)
/* 1103 */           this.available.signal(); 
/* 1104 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public RunnableScheduledFuture<?> poll(long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/* 1110 */       long l = param1TimeUnit.toNanos(param1Long);
/* 1111 */       ReentrantLock reentrantLock = this.lock;
/* 1112 */       reentrantLock.lockInterruptibly();
/*      */       try {
/*      */         while (true) {
/* 1115 */           RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
/* 1116 */           if (runnableScheduledFuture == null) {
/* 1117 */             if (l <= 0L) {
/* 1118 */               return null;
/*      */             }
/* 1120 */             l = this.available.awaitNanos(l); continue;
/*      */           } 
/* 1122 */           long l1 = runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS);
/* 1123 */           if (l1 <= 0L)
/* 1124 */             return finishPoll(runnableScheduledFuture); 
/* 1125 */           if (l <= 0L)
/* 1126 */             return null; 
/* 1127 */           runnableScheduledFuture = null;
/* 1128 */           if (l < l1 || this.leader != null) {
/* 1129 */             l = this.available.awaitNanos(l); continue;
/*      */           } 
/* 1131 */           Thread thread = Thread.currentThread();
/* 1132 */           this.leader = thread;
/*      */           try {
/* 1134 */             long l2 = this.available.awaitNanos(l1);
/* 1135 */             l -= l1 - l2;
/*      */           } finally {
/* 1137 */             if (this.leader == thread) {
/* 1138 */               this.leader = null;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } finally {
/*      */         
/* 1144 */         if (this.leader == null && this.queue[0] != null)
/* 1145 */           this.available.signal(); 
/* 1146 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1151 */       ReentrantLock reentrantLock = this.lock;
/* 1152 */       reentrantLock.lock();
/*      */       try {
/* 1154 */         for (byte b = 0; b < this.size; b++) {
/* 1155 */           RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[b];
/* 1156 */           if (runnableScheduledFuture != null) {
/* 1157 */             this.queue[b] = null;
/* 1158 */             setIndex(runnableScheduledFuture, -1);
/*      */           } 
/*      */         } 
/* 1161 */         this.size = 0;
/*      */       } finally {
/* 1163 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private RunnableScheduledFuture<?> peekExpired() {
/* 1173 */       RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
/* 1174 */       return (runnableScheduledFuture == null || runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L) ? null : runnableScheduledFuture;
/*      */     }
/*      */ 
/*      */     
/*      */     public int drainTo(Collection<? super Runnable> param1Collection) {
/* 1179 */       if (param1Collection == null)
/* 1180 */         throw new NullPointerException(); 
/* 1181 */       if (param1Collection == this)
/* 1182 */         throw new IllegalArgumentException(); 
/* 1183 */       ReentrantLock reentrantLock = this.lock;
/* 1184 */       reentrantLock.lock();
/*      */       
/*      */       try {
/* 1187 */         byte b = 0; RunnableScheduledFuture<?> runnableScheduledFuture;
/* 1188 */         while ((runnableScheduledFuture = peekExpired()) != null) {
/* 1189 */           param1Collection.add(runnableScheduledFuture);
/* 1190 */           finishPoll(runnableScheduledFuture);
/* 1191 */           b++;
/*      */         } 
/* 1193 */         return b;
/*      */       } finally {
/* 1195 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public int drainTo(Collection<? super Runnable> param1Collection, int param1Int) {
/* 1200 */       if (param1Collection == null)
/* 1201 */         throw new NullPointerException(); 
/* 1202 */       if (param1Collection == this)
/* 1203 */         throw new IllegalArgumentException(); 
/* 1204 */       if (param1Int <= 0)
/* 1205 */         return 0; 
/* 1206 */       ReentrantLock reentrantLock = this.lock;
/* 1207 */       reentrantLock.lock();
/*      */       
/*      */       try {
/* 1210 */         byte b = 0; RunnableScheduledFuture<?> runnableScheduledFuture;
/* 1211 */         while (b < param1Int && (runnableScheduledFuture = peekExpired()) != null) {
/* 1212 */           param1Collection.add(runnableScheduledFuture);
/* 1213 */           finishPoll(runnableScheduledFuture);
/* 1214 */           b++;
/*      */         } 
/* 1216 */         return b;
/*      */       } finally {
/* 1218 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 1223 */       ReentrantLock reentrantLock = this.lock;
/* 1224 */       reentrantLock.lock();
/*      */       try {
/* 1226 */         return Arrays.copyOf(this.queue, this.size, Object[].class);
/*      */       } finally {
/* 1228 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 1234 */       ReentrantLock reentrantLock = this.lock;
/* 1235 */       reentrantLock.lock();
/*      */       try {
/* 1237 */         if (param1ArrayOfT.length < this.size)
/* 1238 */           return (T[])Arrays.<Object, RunnableScheduledFuture<?>>copyOf(this.queue, this.size, (Class)param1ArrayOfT.getClass()); 
/* 1239 */         System.arraycopy(this.queue, 0, param1ArrayOfT, 0, this.size);
/* 1240 */         if (param1ArrayOfT.length > this.size)
/* 1241 */           param1ArrayOfT[this.size] = null; 
/* 1242 */         return param1ArrayOfT;
/*      */       } finally {
/* 1244 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public Iterator<Runnable> iterator() {
/* 1249 */       return new Itr((RunnableScheduledFuture<?>[])Arrays.<RunnableScheduledFuture>copyOf((RunnableScheduledFuture[])this.queue, this.size));
/*      */     }
/*      */ 
/*      */     
/*      */     private class Itr
/*      */       implements Iterator<Runnable>
/*      */     {
/*      */       final RunnableScheduledFuture<?>[] array;
/* 1257 */       int cursor = 0;
/* 1258 */       int lastRet = -1;
/*      */       
/*      */       Itr(RunnableScheduledFuture<?>[] param2ArrayOfRunnableScheduledFuture) {
/* 1261 */         this.array = param2ArrayOfRunnableScheduledFuture;
/*      */       }
/*      */       
/*      */       public boolean hasNext() {
/* 1265 */         return (this.cursor < this.array.length);
/*      */       }
/*      */       
/*      */       public Runnable next() {
/* 1269 */         if (this.cursor >= this.array.length)
/* 1270 */           throw new NoSuchElementException(); 
/* 1271 */         this.lastRet = this.cursor;
/* 1272 */         return this.array[this.cursor++];
/*      */       }
/*      */       
/*      */       public void remove() {
/* 1276 */         if (this.lastRet < 0)
/* 1277 */           throw new IllegalStateException(); 
/* 1278 */         ScheduledThreadPoolExecutor.DelayedWorkQueue.this.remove(this.array[this.lastRet]);
/* 1279 */         this.lastRet = -1;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ScheduledThreadPoolExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */