/*     */ package java.util.concurrent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExecutorCompletionService<V>
/*     */   implements CompletionService<V>
/*     */ {
/*     */   private final Executor executor;
/*     */   private final AbstractExecutorService aes;
/*     */   private final BlockingQueue<Future<V>> completionQueue;
/*     */   
/*     */   private class QueueingFuture
/*     */     extends FutureTask<Void>
/*     */   {
/*     */     private final Future<V> task;
/*     */     
/*     */     QueueingFuture(RunnableFuture<V> param1RunnableFuture) {
/* 117 */       super(param1RunnableFuture, null);
/* 118 */       this.task = param1RunnableFuture;
/*     */     } protected void done() {
/* 120 */       ExecutorCompletionService.this.completionQueue.add(this.task);
/*     */     }
/*     */   }
/*     */   
/*     */   private RunnableFuture<V> newTaskFor(Callable<V> paramCallable) {
/* 125 */     if (this.aes == null) {
/* 126 */       return new FutureTask<>(paramCallable);
/*     */     }
/* 128 */     return this.aes.newTaskFor(paramCallable);
/*     */   }
/*     */   
/*     */   private RunnableFuture<V> newTaskFor(Runnable paramRunnable, V paramV) {
/* 132 */     if (this.aes == null) {
/* 133 */       return new FutureTask<>(paramRunnable, paramV);
/*     */     }
/* 135 */     return this.aes.newTaskFor(paramRunnable, paramV);
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
/*     */   public ExecutorCompletionService(Executor paramExecutor) {
/* 147 */     if (paramExecutor == null)
/* 148 */       throw new NullPointerException(); 
/* 149 */     this.executor = paramExecutor;
/* 150 */     this.aes = (paramExecutor instanceof AbstractExecutorService) ? (AbstractExecutorService)paramExecutor : null;
/*     */     
/* 152 */     this.completionQueue = new LinkedBlockingQueue<>();
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
/*     */   public ExecutorCompletionService(Executor paramExecutor, BlockingQueue<Future<V>> paramBlockingQueue) {
/* 170 */     if (paramExecutor == null || paramBlockingQueue == null)
/* 171 */       throw new NullPointerException(); 
/* 172 */     this.executor = paramExecutor;
/* 173 */     this.aes = (paramExecutor instanceof AbstractExecutorService) ? (AbstractExecutorService)paramExecutor : null;
/*     */     
/* 175 */     this.completionQueue = paramBlockingQueue;
/*     */   }
/*     */   
/*     */   public Future<V> submit(Callable<V> paramCallable) {
/* 179 */     if (paramCallable == null) throw new NullPointerException(); 
/* 180 */     RunnableFuture<V> runnableFuture = newTaskFor(paramCallable);
/* 181 */     this.executor.execute(new QueueingFuture(runnableFuture));
/* 182 */     return runnableFuture;
/*     */   }
/*     */   
/*     */   public Future<V> submit(Runnable paramRunnable, V paramV) {
/* 186 */     if (paramRunnable == null) throw new NullPointerException(); 
/* 187 */     RunnableFuture<V> runnableFuture = newTaskFor(paramRunnable, paramV);
/* 188 */     this.executor.execute(new QueueingFuture(runnableFuture));
/* 189 */     return runnableFuture;
/*     */   }
/*     */   
/*     */   public Future<V> take() throws InterruptedException {
/* 193 */     return this.completionQueue.take();
/*     */   }
/*     */   
/*     */   public Future<V> poll() {
/* 197 */     return this.completionQueue.poll();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<V> poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 202 */     return this.completionQueue.poll(paramLong, paramTimeUnit);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ExecutorCompletionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */