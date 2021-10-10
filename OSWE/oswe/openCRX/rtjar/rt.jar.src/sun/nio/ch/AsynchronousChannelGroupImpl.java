/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.AsynchronousChannelGroup;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.spi.AsynchronousChannelProvider;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AsynchronousChannelGroupImpl
/*     */   extends AsynchronousChannelGroup
/*     */   implements Executor
/*     */ {
/*  51 */   private static final int internalThreadCount = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.nio.ch.internalThreadPoolSize", 1))).intValue();
/*     */ 
/*     */ 
/*     */   
/*     */   private final ThreadPool pool;
/*     */ 
/*     */   
/*  58 */   private final AtomicInteger threadCount = new AtomicInteger();
/*     */ 
/*     */ 
/*     */   
/*     */   private ScheduledThreadPoolExecutor timeoutExecutor;
/*     */ 
/*     */   
/*     */   private final Queue<Runnable> taskQueue;
/*     */ 
/*     */   
/*  68 */   private final AtomicBoolean shutdown = new AtomicBoolean();
/*  69 */   private final Object shutdownNowLock = new Object();
/*     */   
/*     */   private volatile boolean terminateInitiated;
/*     */ 
/*     */   
/*     */   AsynchronousChannelGroupImpl(AsynchronousChannelProvider paramAsynchronousChannelProvider, ThreadPool paramThreadPool) {
/*  75 */     super(paramAsynchronousChannelProvider);
/*  76 */     this.pool = paramThreadPool;
/*     */     
/*  78 */     if (paramThreadPool.isFixedThreadPool()) {
/*  79 */       this.taskQueue = new ConcurrentLinkedQueue<>();
/*     */     } else {
/*  81 */       this.taskQueue = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this
/*  87 */       .timeoutExecutor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1, ThreadPool.defaultThreadFactory());
/*  88 */     this.timeoutExecutor.setRemoveOnCancelPolicy(true);
/*     */   }
/*     */   
/*     */   final ExecutorService executor() {
/*  92 */     return this.pool.executor();
/*     */   }
/*     */   
/*     */   final boolean isFixedThreadPool() {
/*  96 */     return this.pool.isFixedThreadPool();
/*     */   }
/*     */   
/*     */   final int fixedThreadCount() {
/* 100 */     if (isFixedThreadPool()) {
/* 101 */       return this.pool.poolSize();
/*     */     }
/* 103 */     return this.pool.poolSize() + internalThreadCount;
/*     */   }
/*     */ 
/*     */   
/*     */   private Runnable bindToGroup(final Runnable task) {
/* 108 */     final AsynchronousChannelGroupImpl thisGroup = this;
/* 109 */     return new Runnable() {
/*     */         public void run() {
/* 111 */           Invoker.bindToGroup(thisGroup);
/* 112 */           task.run();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private void startInternalThread(final Runnable task) {
/* 118 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           
/*     */           public Void run()
/*     */           {
/* 123 */             ThreadPool.defaultThreadFactory().newThread(task).start();
/* 124 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected final void startThreads(Runnable paramRunnable) {
/* 130 */     if (!isFixedThreadPool()) {
/* 131 */       for (byte b = 0; b < internalThreadCount; b++) {
/* 132 */         startInternalThread(paramRunnable);
/* 133 */         this.threadCount.incrementAndGet();
/*     */       } 
/*     */     }
/* 136 */     if (this.pool.poolSize() > 0) {
/* 137 */       paramRunnable = bindToGroup(paramRunnable);
/*     */       try {
/* 139 */         for (byte b = 0; b < this.pool.poolSize(); b++) {
/* 140 */           this.pool.executor().execute(paramRunnable);
/* 141 */           this.threadCount.incrementAndGet();
/*     */         } 
/* 143 */       } catch (RejectedExecutionException rejectedExecutionException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final int threadCount() {
/* 150 */     return this.threadCount.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int threadExit(Runnable paramRunnable, boolean paramBoolean) {
/* 157 */     if (paramBoolean) {
/*     */       try {
/* 159 */         if (Invoker.isBoundToAnyGroup()) {
/*     */           
/* 161 */           this.pool.executor().execute(bindToGroup(paramRunnable));
/*     */         } else {
/*     */           
/* 164 */           startInternalThread(paramRunnable);
/*     */         } 
/* 166 */         return this.threadCount.get();
/* 167 */       } catch (RejectedExecutionException rejectedExecutionException) {}
/*     */     }
/*     */ 
/*     */     
/* 171 */     return this.threadCount.decrementAndGet();
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
/*     */   final void executeOnPooledThread(Runnable paramRunnable) {
/* 185 */     if (isFixedThreadPool()) {
/* 186 */       executeOnHandlerTask(paramRunnable);
/*     */     } else {
/* 188 */       this.pool.executor().execute(bindToGroup(paramRunnable));
/*     */     } 
/*     */   }
/*     */   
/*     */   final void offerTask(Runnable paramRunnable) {
/* 193 */     this.taskQueue.offer(paramRunnable);
/*     */   }
/*     */   
/*     */   final Runnable pollTask() {
/* 197 */     return (this.taskQueue == null) ? null : this.taskQueue.poll();
/*     */   }
/*     */   
/*     */   final Future<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/*     */     try {
/* 202 */       return this.timeoutExecutor.schedule(paramRunnable, paramLong, paramTimeUnit);
/* 203 */     } catch (RejectedExecutionException rejectedExecutionException) {
/* 204 */       if (this.terminateInitiated)
/*     */       {
/* 206 */         return null;
/*     */       }
/* 208 */       throw new AssertionError(rejectedExecutionException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isShutdown() {
/* 214 */     return this.shutdown.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isTerminated() {
/* 219 */     return this.pool.executor().isTerminated();
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
/*     */   
/*     */   private void shutdownExecutors() {
/* 249 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 252 */             AsynchronousChannelGroupImpl.this.pool.executor().shutdown();
/* 253 */             AsynchronousChannelGroupImpl.this.timeoutExecutor.shutdown();
/* 254 */             return null;
/*     */           }
/*     */         },  (AccessControlContext)null, new Permission[] { new RuntimePermission("modifyThread") });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void shutdown() {
/* 263 */     if (this.shutdown.getAndSet(true)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 269 */     if (!isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 274 */     synchronized (this.shutdownNowLock) {
/* 275 */       if (!this.terminateInitiated) {
/* 276 */         this.terminateInitiated = true;
/* 277 */         shutdownHandlerTasks();
/* 278 */         shutdownExecutors();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void shutdownNow() throws IOException {
/* 285 */     this.shutdown.set(true);
/* 286 */     synchronized (this.shutdownNowLock) {
/* 287 */       if (!this.terminateInitiated) {
/* 288 */         this.terminateInitiated = true;
/* 289 */         closeAllChannels();
/* 290 */         shutdownHandlerTasks();
/* 291 */         shutdownExecutors();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void detachFromThreadPool() {
/* 301 */     if (this.shutdown.getAndSet(true))
/* 302 */       throw new AssertionError("Already shutdown"); 
/* 303 */     if (!isEmpty())
/* 304 */       throw new AssertionError("Group not empty"); 
/* 305 */     shutdownHandlerTasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 312 */     return this.pool.executor().awaitTermination(paramLong, paramTimeUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void execute(Runnable paramRunnable) {
/* 320 */     SecurityManager securityManager = System.getSecurityManager();
/* 321 */     if (securityManager != null) {
/*     */ 
/*     */       
/* 324 */       final AccessControlContext acc = AccessController.getContext();
/* 325 */       final Runnable delegate = paramRunnable;
/* 326 */       paramRunnable = new Runnable()
/*     */         {
/*     */           public void run() {
/* 329 */             AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */                 {
/*     */                   public Void run() {
/* 332 */                     delegate.run();
/* 333 */                     return null;
/*     */                   }
/*     */                 },  acc);
/*     */           }
/*     */         };
/*     */     } 
/* 339 */     executeOnPooledThread(paramRunnable);
/*     */   }
/*     */   
/*     */   abstract void executeOnHandlerTask(Runnable paramRunnable);
/*     */   
/*     */   abstract boolean isEmpty();
/*     */   
/*     */   abstract Object attachForeignChannel(Channel paramChannel, FileDescriptor paramFileDescriptor) throws IOException;
/*     */   
/*     */   abstract void detachForeignChannel(Object paramObject);
/*     */   
/*     */   abstract void closeAllChannels() throws IOException;
/*     */   
/*     */   abstract void shutdownHandlerTasks();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/AsynchronousChannelGroupImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */