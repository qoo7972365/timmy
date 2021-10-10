/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.locks.LockSupport;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FutureTask<V>
/*     */   implements RunnableFuture<V>
/*     */ {
/*     */   private volatile int state;
/*     */   private static final int NEW = 0;
/*     */   private static final int COMPLETING = 1;
/*     */   private static final int NORMAL = 2;
/*     */   private static final int EXCEPTIONAL = 3;
/*     */   private static final int CANCELLED = 4;
/*     */   private static final int INTERRUPTING = 5;
/*     */   private static final int INTERRUPTED = 6;
/*     */   private Callable<V> callable;
/*     */   private Object outcome;
/*     */   private volatile Thread runner;
/*     */   private volatile WaitNode waiters;
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final long stateOffset;
/*     */   private static final long runnerOffset;
/*     */   private static final long waitersOffset;
/*     */   
/*     */   private V report(int paramInt) throws ExecutionException {
/* 117 */     Object object = this.outcome;
/* 118 */     if (paramInt == 2)
/* 119 */       return (V)object; 
/* 120 */     if (paramInt >= 4)
/* 121 */       throw new CancellationException(); 
/* 122 */     throw new ExecutionException((Throwable)object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FutureTask(Callable<V> paramCallable) {
/* 133 */     if (paramCallable == null)
/* 134 */       throw new NullPointerException(); 
/* 135 */     this.callable = paramCallable;
/* 136 */     this.state = 0;
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
/*     */   public FutureTask(Runnable paramRunnable, V paramV) {
/* 152 */     this.callable = Executors.callable(paramRunnable, paramV);
/* 153 */     this.state = 0;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 157 */     return (this.state >= 4);
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/* 161 */     return (this.state != 0);
/*     */   }
/*     */   
/*     */   public boolean cancel(boolean paramBoolean) {
/* 165 */     if (this.state != 0 || 
/* 166 */       !UNSAFE.compareAndSwapInt(this, stateOffset, 0, paramBoolean ? 5 : 4))
/*     */     {
/* 168 */       return false; } 
/*     */     try {
/* 170 */       if (paramBoolean) {
/*     */         try {
/* 172 */           Thread thread = this.runner;
/* 173 */           if (thread != null)
/* 174 */             thread.interrupt(); 
/*     */         } finally {
/* 176 */           UNSAFE.putOrderedInt(this, stateOffset, 6);
/*     */         } 
/*     */       }
/*     */     } finally {
/* 180 */       finishCompletion();
/*     */     } 
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V get() throws InterruptedException, ExecutionException {
/* 189 */     int i = this.state;
/* 190 */     if (i <= 1)
/* 191 */       i = awaitDone(false, 0L); 
/* 192 */     return report(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/* 200 */     if (paramTimeUnit == null)
/* 201 */       throw new NullPointerException(); 
/* 202 */     int i = this.state;
/* 203 */     if (i <= 1 && (
/* 204 */       i = awaitDone(true, paramTimeUnit.toNanos(paramLong))) <= 1)
/* 205 */       throw new TimeoutException(); 
/* 206 */     return report(i);
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
/*     */   protected void done() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void set(V paramV) {
/* 230 */     if (UNSAFE.compareAndSwapInt(this, stateOffset, 0, 1)) {
/* 231 */       this.outcome = paramV;
/* 232 */       UNSAFE.putOrderedInt(this, stateOffset, 2);
/* 233 */       finishCompletion();
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
/*     */   protected void setException(Throwable paramThrowable) {
/* 248 */     if (UNSAFE.compareAndSwapInt(this, stateOffset, 0, 1)) {
/* 249 */       this.outcome = paramThrowable;
/* 250 */       UNSAFE.putOrderedInt(this, stateOffset, 3);
/* 251 */       finishCompletion();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void run() {
/* 256 */     if (this.state != 0 || 
/* 257 */       !UNSAFE.compareAndSwapObject(this, runnerOffset, null, 
/* 258 */         Thread.currentThread()))
/*     */       return; 
/*     */     try {
/* 261 */       Callable<V> callable = this.callable;
/* 262 */       if (callable != null && this.state == 0) {
/*     */         V v;
/*     */         boolean bool;
/*     */         try {
/* 266 */           V v1 = callable.call();
/* 267 */           bool = true;
/* 268 */         } catch (Throwable throwable) {
/* 269 */           v = null;
/* 270 */           bool = false;
/* 271 */           setException(throwable);
/*     */         } 
/* 273 */         if (bool) {
/* 274 */           set(v);
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/* 279 */       this.runner = null;
/*     */ 
/*     */       
/* 282 */       int i = this.state;
/* 283 */       if (i >= 5) {
/* 284 */         handlePossibleCancellationInterrupt(i);
/*     */       }
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
/*     */   protected boolean runAndReset() {
/* 298 */     if (this.state != 0 || 
/* 299 */       !UNSAFE.compareAndSwapObject(this, runnerOffset, null, 
/* 300 */         Thread.currentThread()))
/* 301 */       return false; 
/* 302 */     boolean bool = false;
/* 303 */     int i = this.state;
/*     */     try {
/* 305 */       Callable<V> callable = this.callable;
/* 306 */       if (callable != null && i == 0) {
/*     */         try {
/* 308 */           callable.call();
/* 309 */           bool = true;
/* 310 */         } catch (Throwable throwable) {
/* 311 */           setException(throwable);
/*     */         }
/*     */       
/*     */       }
/*     */     } finally {
/*     */       
/* 317 */       this.runner = null;
/*     */ 
/*     */       
/* 320 */       i = this.state;
/* 321 */       if (i >= 5)
/* 322 */         handlePossibleCancellationInterrupt(i); 
/*     */     } 
/* 324 */     return (bool && i == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handlePossibleCancellationInterrupt(int paramInt) {
/* 334 */     if (paramInt == 5) {
/* 335 */       while (this.state == 5) {
/* 336 */         Thread.yield();
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class WaitNode
/*     */   {
/* 357 */     volatile Thread thread = Thread.currentThread();
/*     */ 
/*     */     
/*     */     volatile WaitNode next;
/*     */   }
/*     */ 
/*     */   
/*     */   private void finishCompletion() {
/*     */     WaitNode waitNode;
/* 366 */     while ((waitNode = this.waiters) != null) {
/* 367 */       if (UNSAFE.compareAndSwapObject(this, waitersOffset, waitNode, null)) {
/*     */         while (true) {
/* 369 */           Thread thread = waitNode.thread;
/* 370 */           if (thread != null) {
/* 371 */             waitNode.thread = null;
/* 372 */             LockSupport.unpark(thread);
/*     */           } 
/* 374 */           WaitNode waitNode1 = waitNode.next;
/* 375 */           if (waitNode1 == null)
/*     */             break; 
/* 377 */           waitNode.next = null;
/* 378 */           waitNode = waitNode1;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 384 */     done();
/*     */     
/* 386 */     this.callable = null;
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
/*     */   private int awaitDone(boolean paramBoolean, long paramLong) throws InterruptedException {
/* 398 */     long l = paramBoolean ? (System.nanoTime() + paramLong) : 0L;
/* 399 */     WaitNode waitNode = null;
/* 400 */     boolean bool = false;
/*     */     while (true) {
/* 402 */       if (Thread.interrupted()) {
/* 403 */         removeWaiter(waitNode);
/* 404 */         throw new InterruptedException();
/*     */       } 
/*     */       
/* 407 */       int i = this.state;
/* 408 */       if (i > 1) {
/* 409 */         if (waitNode != null)
/* 410 */           waitNode.thread = null; 
/* 411 */         return i;
/*     */       } 
/* 413 */       if (i == 1) {
/* 414 */         Thread.yield(); continue;
/* 415 */       }  if (waitNode == null) {
/* 416 */         waitNode = new WaitNode(); continue;
/* 417 */       }  if (!bool) {
/* 418 */         bool = UNSAFE.compareAndSwapObject(this, waitersOffset, waitNode.next = this.waiters, waitNode); continue;
/*     */       } 
/* 420 */       if (paramBoolean) {
/* 421 */         paramLong = l - System.nanoTime();
/* 422 */         if (paramLong <= 0L) {
/* 423 */           removeWaiter(waitNode);
/* 424 */           return this.state;
/*     */         } 
/* 426 */         LockSupport.parkNanos(this, paramLong);
/*     */         continue;
/*     */       } 
/* 429 */       LockSupport.park(this);
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
/*     */   private void removeWaiter(WaitNode paramWaitNode) {
/* 444 */     if (paramWaitNode != null) {
/* 445 */       paramWaitNode.thread = null;
/*     */       
/*     */       label22: while (true) {
/* 448 */         for (WaitNode waitNode1 = null, waitNode2 = this.waiters; waitNode2 != null; waitNode2 = waitNode) {
/* 449 */           WaitNode waitNode = waitNode2.next;
/* 450 */           if (waitNode2.thread != null) {
/* 451 */             waitNode1 = waitNode2;
/* 452 */           } else if (waitNode1 != null) {
/* 453 */             waitNode1.next = waitNode;
/* 454 */             if (waitNode1.thread == null) {
/*     */               continue label22;
/*     */             }
/* 457 */           } else if (!UNSAFE.compareAndSwapObject(this, waitersOffset, waitNode2, waitNode)) {
/*     */             continue label22;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 473 */       UNSAFE = Unsafe.getUnsafe();
/* 474 */       Class<FutureTask> clazz = FutureTask.class;
/*     */       
/* 476 */       stateOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("state"));
/*     */       
/* 478 */       runnerOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("runner"));
/*     */       
/* 480 */       waitersOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("waiters"));
/* 481 */     } catch (Exception exception) {
/* 482 */       throw new Error(exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/FutureTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */