/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Executors
/*     */ {
/*     */   public static ExecutorService newFixedThreadPool(int paramInt) {
/*  89 */     return new ThreadPoolExecutor(paramInt, paramInt, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
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
/*     */   public static ExecutorService newWorkStealingPool(int paramInt) {
/* 110 */     return new ForkJoinPool(paramInt, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
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
/*     */   public static ExecutorService newWorkStealingPool() {
/* 125 */     return new ForkJoinPool(
/* 126 */         Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
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
/*     */   public static ExecutorService newFixedThreadPool(int paramInt, ThreadFactory paramThreadFactory) {
/* 151 */     return new ThreadPoolExecutor(paramInt, paramInt, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), paramThreadFactory);
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
/*     */   public static ExecutorService newSingleThreadExecutor() {
/* 171 */     return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()));
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
/*     */   public static ExecutorService newSingleThreadExecutor(ThreadFactory paramThreadFactory) {
/* 192 */     return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), paramThreadFactory));
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
/*     */   public static ExecutorService newCachedThreadPool() {
/* 216 */     return new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
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
/*     */   public static ExecutorService newCachedThreadPool(ThreadFactory paramThreadFactory) {
/* 231 */     return new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), paramThreadFactory);
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
/*     */   public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
/* 251 */     return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1));
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
/*     */   public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory paramThreadFactory) {
/* 272 */     return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1, paramThreadFactory));
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
/*     */   public static ScheduledExecutorService newScheduledThreadPool(int paramInt) {
/* 285 */     return new ScheduledThreadPoolExecutor(paramInt);
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
/*     */   public static ScheduledExecutorService newScheduledThreadPool(int paramInt, ThreadFactory paramThreadFactory) {
/* 301 */     return new ScheduledThreadPoolExecutor(paramInt, paramThreadFactory);
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
/*     */   public static ExecutorService unconfigurableExecutorService(ExecutorService paramExecutorService) {
/* 315 */     if (paramExecutorService == null)
/* 316 */       throw new NullPointerException(); 
/* 317 */     return new DelegatedExecutorService(paramExecutorService);
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
/*     */   public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService paramScheduledExecutorService) {
/* 331 */     if (paramScheduledExecutorService == null)
/* 332 */       throw new NullPointerException(); 
/* 333 */     return new DelegatedScheduledExecutorService(paramScheduledExecutorService);
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
/*     */   public static ThreadFactory defaultThreadFactory() {
/* 353 */     return new DefaultThreadFactory();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadFactory privilegedThreadFactory() {
/* 390 */     return new PrivilegedThreadFactory();
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
/*     */   public static <T> Callable<T> callable(Runnable paramRunnable, T paramT) {
/* 405 */     if (paramRunnable == null)
/* 406 */       throw new NullPointerException(); 
/* 407 */     return new RunnableAdapter<>(paramRunnable, paramT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Callable<Object> callable(Runnable paramRunnable) {
/* 418 */     if (paramRunnable == null)
/* 419 */       throw new NullPointerException(); 
/* 420 */     return new RunnableAdapter(paramRunnable, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Callable<Object> callable(final PrivilegedAction<?> action) {
/* 431 */     if (action == null)
/* 432 */       throw new NullPointerException(); 
/* 433 */     return new Callable() { public Object call() {
/* 434 */           return action.run();
/*     */         } }
/*     */       ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Callable<Object> callable(final PrivilegedExceptionAction<?> action) {
/* 446 */     if (action == null)
/* 447 */       throw new NullPointerException(); 
/* 448 */     return new Callable() { public Object call() throws Exception {
/* 449 */           return action.run();
/*     */         } }
/*     */       ;
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
/*     */   public static <T> Callable<T> privilegedCallable(Callable<T> paramCallable) {
/* 467 */     if (paramCallable == null)
/* 468 */       throw new NullPointerException(); 
/* 469 */     return new PrivilegedCallable<>(paramCallable);
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
/*     */   public static <T> Callable<T> privilegedCallableUsingCurrentClassLoader(Callable<T> paramCallable) {
/* 493 */     if (paramCallable == null)
/* 494 */       throw new NullPointerException(); 
/* 495 */     return new PrivilegedCallableUsingCurrentClassLoader<>(paramCallable);
/*     */   }
/*     */ 
/*     */   
/*     */   static final class RunnableAdapter<T>
/*     */     implements Callable<T>
/*     */   {
/*     */     final Runnable task;
/*     */     
/*     */     final T result;
/*     */     
/*     */     RunnableAdapter(Runnable param1Runnable, T param1T) {
/* 507 */       this.task = param1Runnable;
/* 508 */       this.result = param1T;
/*     */     }
/*     */     public T call() {
/* 511 */       this.task.run();
/* 512 */       return this.result;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class PrivilegedCallable<T>
/*     */     implements Callable<T>
/*     */   {
/*     */     private final Callable<T> task;
/*     */     private final AccessControlContext acc;
/*     */     
/*     */     PrivilegedCallable(Callable<T> param1Callable) {
/* 524 */       this.task = param1Callable;
/* 525 */       this.acc = AccessController.getContext();
/*     */     }
/*     */     
/*     */     public T call() throws Exception {
/*     */       try {
/* 530 */         return AccessController.doPrivileged(new PrivilegedExceptionAction<T>()
/*     */             {
/*     */               public T run() throws Exception {
/* 533 */                 return (T)Executors.PrivilegedCallable.this.task.call();
/*     */               }
/*     */             },  this.acc);
/* 536 */       } catch (PrivilegedActionException privilegedActionException) {
/* 537 */         throw privilegedActionException.getException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class PrivilegedCallableUsingCurrentClassLoader<T>
/*     */     implements Callable<T>
/*     */   {
/*     */     private final Callable<T> task;
/*     */     
/*     */     private final AccessControlContext acc;
/*     */     private final ClassLoader ccl;
/*     */     
/*     */     PrivilegedCallableUsingCurrentClassLoader(Callable<T> param1Callable) {
/* 552 */       SecurityManager securityManager = System.getSecurityManager();
/* 553 */       if (securityManager != null) {
/*     */ 
/*     */ 
/*     */         
/* 557 */         securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*     */ 
/*     */ 
/*     */         
/* 561 */         securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
/*     */       } 
/* 563 */       this.task = param1Callable;
/* 564 */       this.acc = AccessController.getContext();
/* 565 */       this.ccl = Thread.currentThread().getContextClassLoader();
/*     */     }
/*     */     
/*     */     public T call() throws Exception {
/*     */       try {
/* 570 */         return AccessController.doPrivileged(new PrivilegedExceptionAction<T>()
/*     */             {
/*     */               public T run() throws Exception {
/* 573 */                 Thread thread = Thread.currentThread();
/* 574 */                 ClassLoader classLoader = thread.getContextClassLoader();
/* 575 */                 if (Executors.PrivilegedCallableUsingCurrentClassLoader.this.ccl == classLoader) {
/* 576 */                   return (T)Executors.PrivilegedCallableUsingCurrentClassLoader.this.task.call();
/*     */                 }
/* 578 */                 thread.setContextClassLoader(Executors.PrivilegedCallableUsingCurrentClassLoader.this.ccl);
/*     */                 try {
/* 580 */                   return (T)Executors.PrivilegedCallableUsingCurrentClassLoader.this.task.call();
/*     */                 } finally {
/* 582 */                   thread.setContextClassLoader(classLoader);
/*     */                 } 
/*     */               }
/*     */             }, 
/*     */             this.acc);
/* 587 */       } catch (PrivilegedActionException privilegedActionException) {
/* 588 */         throw privilegedActionException.getException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class DefaultThreadFactory
/*     */     implements ThreadFactory
/*     */   {
/* 597 */     private static final AtomicInteger poolNumber = new AtomicInteger(1);
/*     */     private final ThreadGroup group;
/* 599 */     private final AtomicInteger threadNumber = new AtomicInteger(1);
/*     */     private final String namePrefix;
/*     */     
/*     */     DefaultThreadFactory() {
/* 603 */       SecurityManager securityManager = System.getSecurityManager();
/* 604 */       this
/* 605 */         .group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
/* 606 */       this
/* 607 */         .namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Thread newThread(Runnable param1Runnable) {
/* 613 */       Thread thread = new Thread(this.group, param1Runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
/*     */       
/* 615 */       if (thread.isDaemon())
/* 616 */         thread.setDaemon(false); 
/* 617 */       if (thread.getPriority() != 5)
/* 618 */         thread.setPriority(5); 
/* 619 */       return thread;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class PrivilegedThreadFactory
/*     */     extends DefaultThreadFactory
/*     */   {
/*     */     private final AccessControlContext acc;
/*     */     
/*     */     private final ClassLoader ccl;
/*     */     
/*     */     PrivilegedThreadFactory() {
/* 632 */       SecurityManager securityManager = System.getSecurityManager();
/* 633 */       if (securityManager != null) {
/*     */ 
/*     */ 
/*     */         
/* 637 */         securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*     */ 
/*     */         
/* 640 */         securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
/*     */       } 
/* 642 */       this.acc = AccessController.getContext();
/* 643 */       this.ccl = Thread.currentThread().getContextClassLoader();
/*     */     }
/*     */     
/*     */     public Thread newThread(final Runnable r) {
/* 647 */       return super.newThread(new Runnable() {
/*     */             public void run() {
/* 649 */               AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */                     public Void run() {
/* 651 */                       Thread.currentThread().setContextClassLoader(Executors.PrivilegedThreadFactory.this.ccl);
/* 652 */                       r.run();
/* 653 */                       return null;
/*     */                     }
/* 655 */                   },  Executors.PrivilegedThreadFactory.this.acc);
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   static class DelegatedExecutorService
/*     */     extends AbstractExecutorService
/*     */   {
/*     */     private final ExecutorService e;
/*     */     
/*     */     DelegatedExecutorService(ExecutorService param1ExecutorService) {
/* 667 */       this.e = param1ExecutorService;
/* 668 */     } public void execute(Runnable param1Runnable) { this.e.execute(param1Runnable); }
/* 669 */     public void shutdown() { this.e.shutdown(); }
/* 670 */     public List<Runnable> shutdownNow() { return this.e.shutdownNow(); }
/* 671 */     public boolean isShutdown() { return this.e.isShutdown(); } public boolean isTerminated() {
/* 672 */       return this.e.isTerminated();
/*     */     }
/*     */     public boolean awaitTermination(long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/* 675 */       return this.e.awaitTermination(param1Long, param1TimeUnit);
/*     */     }
/*     */     public Future<?> submit(Runnable param1Runnable) {
/* 678 */       return this.e.submit(param1Runnable);
/*     */     }
/*     */     public <T> Future<T> submit(Callable<T> param1Callable) {
/* 681 */       return this.e.submit(param1Callable);
/*     */     }
/*     */     public <T> Future<T> submit(Runnable param1Runnable, T param1T) {
/* 684 */       return this.e.submit(param1Runnable, param1T);
/*     */     }
/*     */     
/*     */     public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> param1Collection) throws InterruptedException {
/* 688 */       return this.e.invokeAll(param1Collection);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> param1Collection, long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/* 693 */       return this.e.invokeAll(param1Collection, param1Long, param1TimeUnit);
/*     */     }
/*     */     
/*     */     public <T> T invokeAny(Collection<? extends Callable<T>> param1Collection) throws InterruptedException, ExecutionException {
/* 697 */       return this.e.invokeAny(param1Collection);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T invokeAny(Collection<? extends Callable<T>> param1Collection, long param1Long, TimeUnit param1TimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/* 702 */       return this.e.invokeAny(param1Collection, param1Long, param1TimeUnit);
/*     */     }
/*     */   }
/*     */   
/*     */   static class FinalizableDelegatedExecutorService
/*     */     extends DelegatedExecutorService {
/*     */     FinalizableDelegatedExecutorService(ExecutorService param1ExecutorService) {
/* 709 */       super(param1ExecutorService);
/*     */     }
/*     */     protected void finalize() {
/* 712 */       shutdown();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class DelegatedScheduledExecutorService
/*     */     extends DelegatedExecutorService
/*     */     implements ScheduledExecutorService
/*     */   {
/*     */     private final ScheduledExecutorService e;
/*     */ 
/*     */     
/*     */     DelegatedScheduledExecutorService(ScheduledExecutorService param1ScheduledExecutorService) {
/* 725 */       super(param1ScheduledExecutorService);
/* 726 */       this.e = param1ScheduledExecutorService;
/*     */     }
/*     */     public ScheduledFuture<?> schedule(Runnable param1Runnable, long param1Long, TimeUnit param1TimeUnit) {
/* 729 */       return this.e.schedule(param1Runnable, param1Long, param1TimeUnit);
/*     */     }
/*     */     public <V> ScheduledFuture<V> schedule(Callable<V> param1Callable, long param1Long, TimeUnit param1TimeUnit) {
/* 732 */       return this.e.schedule(param1Callable, param1Long, param1TimeUnit);
/*     */     }
/*     */     public ScheduledFuture<?> scheduleAtFixedRate(Runnable param1Runnable, long param1Long1, long param1Long2, TimeUnit param1TimeUnit) {
/* 735 */       return this.e.scheduleAtFixedRate(param1Runnable, param1Long1, param1Long2, param1TimeUnit);
/*     */     }
/*     */     public ScheduledFuture<?> scheduleWithFixedDelay(Runnable param1Runnable, long param1Long1, long param1Long2, TimeUnit param1TimeUnit) {
/* 738 */       return this.e.scheduleWithFixedDelay(param1Runnable, param1Long1, param1Long2, param1TimeUnit);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/Executors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */