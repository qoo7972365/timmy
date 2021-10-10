/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractExecutorService
/*     */   implements ExecutorService
/*     */ {
/*     */   protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT) {
/*  87 */     return new FutureTask<>(paramRunnable, paramT);
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
/*     */   protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable) {
/* 102 */     return new FutureTask<>(paramCallable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> submit(Runnable paramRunnable) {
/* 110 */     if (paramRunnable == null) throw new NullPointerException(); 
/* 111 */     RunnableFuture<?> runnableFuture = newTaskFor(paramRunnable, null);
/* 112 */     execute(runnableFuture);
/* 113 */     return runnableFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Runnable paramRunnable, T paramT) {
/* 121 */     if (paramRunnable == null) throw new NullPointerException(); 
/* 122 */     RunnableFuture<T> runnableFuture = newTaskFor(paramRunnable, paramT);
/* 123 */     execute(runnableFuture);
/* 124 */     return runnableFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> paramCallable) {
/* 132 */     if (paramCallable == null) throw new NullPointerException(); 
/* 133 */     RunnableFuture<T> runnableFuture = newTaskFor(paramCallable);
/* 134 */     execute(runnableFuture);
/* 135 */     return runnableFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> T doInvokeAny(Collection<? extends Callable<T>> paramCollection, boolean paramBoolean, long paramLong) throws InterruptedException, ExecutionException, TimeoutException {
/* 144 */     if (paramCollection == null)
/* 145 */       throw new NullPointerException(); 
/* 146 */     int i = paramCollection.size();
/* 147 */     if (i == 0)
/* 148 */       throw new IllegalArgumentException(); 
/* 149 */     ArrayList<Future> arrayList = new ArrayList(i);
/* 150 */     ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection) throws InterruptedException, ExecutionException {
/*     */     try {
/* 215 */       return doInvokeAny(paramCollection, false, 0L);
/* 216 */     } catch (TimeoutException timeoutException) {
/*     */       assert false;
/* 218 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/* 225 */     return doInvokeAny(paramCollection, true, paramTimeUnit.toNanos(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection) throws InterruptedException {
/* 230 */     if (paramCollection == null)
/* 231 */       throw new NullPointerException(); 
/* 232 */     ArrayList<RunnableFuture<?>> arrayList = new ArrayList(paramCollection.size());
/* 233 */     boolean bool = false;
/*     */     try {
/* 235 */       for (Callable<?> callable : paramCollection) {
/* 236 */         RunnableFuture<?> runnableFuture = newTaskFor(callable);
/* 237 */         arrayList.add(runnableFuture);
/* 238 */         execute(runnableFuture);
/*     */       }  int i;
/* 240 */       for (byte b = 0; b < i; b++) {
/* 241 */         Future future = arrayList.get(b);
/* 242 */         if (!future.isDone()) {
/*     */           
/* 244 */           try { future.get(); }
/* 245 */           catch (CancellationException cancellationException) {  }
/* 246 */           catch (ExecutionException executionException) {}
/*     */         }
/*     */       } 
/*     */       
/* 250 */       bool = true;
/* 251 */       return (List)arrayList;
/*     */     } finally {
/* 253 */       if (!bool) {
/* 254 */         byte b; int i; for (b = 0, i = arrayList.size(); b < i; b++) {
/* 255 */           ((Future)arrayList.get(b)).cancel(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 262 */     if (paramCollection == null)
/* 263 */       throw new NullPointerException(); 
/* 264 */     long l = paramTimeUnit.toNanos(paramLong);
/* 265 */     ArrayList<Runnable> arrayList = new ArrayList(paramCollection.size());
/* 266 */     boolean bool = false;
/*     */     try {
/* 268 */       for (Callable<?> callable : paramCollection) {
/* 269 */         arrayList.add(newTaskFor(callable));
/*     */       }
/* 271 */       long l1 = System.nanoTime() + l;
/* 272 */       int i = arrayList.size();
/*     */       
/*     */       byte b;
/*     */       
/* 276 */       for (b = 0; b < i; b++) {
/* 277 */         execute(arrayList.get(b));
/* 278 */         l = l1 - System.nanoTime();
/* 279 */         if (l <= 0L) {
/* 280 */           return (List)arrayList;
/*     */         }
/*     */       } 
/* 283 */       for (b = 0; b < i; b++) {
/* 284 */         Future future = (Future)arrayList.get(b);
/* 285 */         if (!future.isDone()) {
/* 286 */           if (l <= 0L)
/* 287 */             return (List)arrayList; 
/*     */           
/* 289 */           try { future.get(l, TimeUnit.NANOSECONDS); }
/* 290 */           catch (CancellationException cancellationException) {  }
/* 291 */           catch (ExecutionException executionException) {  }
/* 292 */           catch (TimeoutException timeoutException)
/* 293 */           { return (List)arrayList; }
/*     */           
/* 295 */           l = l1 - System.nanoTime();
/*     */         } 
/*     */       } 
/* 298 */       bool = true;
/* 299 */       return (List)arrayList;
/*     */     } finally {
/* 301 */       if (!bool) {
/* 302 */         byte b; int i; for (b = 0, i = arrayList.size(); b < i; b++)
/* 303 */           ((Future)arrayList.get(b)).cancel(true); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/AbstractExecutorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */