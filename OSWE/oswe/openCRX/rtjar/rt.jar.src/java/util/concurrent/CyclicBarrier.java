/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CyclicBarrier
/*     */ {
/*     */   private static class Generation
/*     */   {
/*     */     private Generation() {}
/*     */     
/*     */     boolean broken = false;
/*     */   }
/*     */   
/* 156 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   
/* 158 */   private final Condition trip = this.lock.newCondition();
/*     */   
/*     */   private final int parties;
/*     */   
/*     */   private final Runnable barrierCommand;
/*     */   
/* 164 */   private Generation generation = new Generation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int count;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextGeneration() {
/* 179 */     this.trip.signalAll();
/*     */     
/* 181 */     this.count = this.parties;
/* 182 */     this.generation = new Generation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void breakBarrier() {
/* 190 */     this.generation.broken = true;
/* 191 */     this.count = this.parties;
/* 192 */     this.trip.signalAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int dowait(boolean paramBoolean, long paramLong) throws InterruptedException, BrokenBarrierException, TimeoutException {
/* 201 */     ReentrantLock reentrantLock = this.lock;
/* 202 */     reentrantLock.lock();
/*     */     try {
/* 204 */       Generation generation = this.generation;
/*     */       
/* 206 */       if (generation.broken) {
/* 207 */         throw new BrokenBarrierException();
/*     */       }
/* 209 */       if (Thread.interrupted()) {
/* 210 */         breakBarrier();
/* 211 */         throw new InterruptedException();
/*     */       } 
/*     */       
/* 214 */       int i = --this.count;
/* 215 */       if (i == 0) {
/* 216 */         boolean bool = false;
/*     */         try {
/* 218 */           Runnable runnable = this.barrierCommand;
/* 219 */           if (runnable != null)
/* 220 */             runnable.run(); 
/* 221 */           bool = true;
/* 222 */           nextGeneration();
/* 223 */           return 0;
/*     */         } finally {
/* 225 */           if (!bool) {
/* 226 */             breakBarrier();
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*     */       while (true) {
/*     */         try {
/* 233 */           if (!paramBoolean)
/* 234 */           { this.trip.await(); }
/* 235 */           else if (paramLong > 0L)
/* 236 */           { paramLong = this.trip.awaitNanos(paramLong); } 
/* 237 */         } catch (InterruptedException interruptedException) {
/* 238 */           if (generation == this.generation && !generation.broken) {
/* 239 */             breakBarrier();
/* 240 */             throw interruptedException;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 245 */           Thread.currentThread().interrupt();
/*     */         } 
/*     */ 
/*     */         
/* 249 */         if (generation.broken) {
/* 250 */           throw new BrokenBarrierException();
/*     */         }
/* 252 */         if (generation != this.generation) {
/* 253 */           return i;
/*     */         }
/* 255 */         if (paramBoolean && paramLong <= 0L) {
/* 256 */           breakBarrier();
/* 257 */           throw new TimeoutException();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 261 */       reentrantLock.unlock();
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
/*     */   public CyclicBarrier(int paramInt, Runnable paramRunnable) {
/* 278 */     if (paramInt <= 0) throw new IllegalArgumentException(); 
/* 279 */     this.parties = paramInt;
/* 280 */     this.count = paramInt;
/* 281 */     this.barrierCommand = paramRunnable;
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
/*     */   public CyclicBarrier(int paramInt) {
/* 294 */     this(paramInt, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParties() {
/* 303 */     return this.parties;
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
/*     */   public int await() throws InterruptedException, BrokenBarrierException {
/*     */     try {
/* 362 */       return dowait(false, 0L);
/* 363 */     } catch (TimeoutException timeoutException) {
/* 364 */       throw new Error(timeoutException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int await(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, BrokenBarrierException, TimeoutException {
/* 435 */     return dowait(true, paramTimeUnit.toNanos(paramLong));
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
/*     */   public boolean isBroken() {
/* 447 */     ReentrantLock reentrantLock = this.lock;
/* 448 */     reentrantLock.lock();
/*     */     try {
/* 450 */       return this.generation.broken;
/*     */     } finally {
/* 452 */       reentrantLock.unlock();
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
/*     */   public void reset() {
/* 466 */     ReentrantLock reentrantLock = this.lock;
/* 467 */     reentrantLock.lock();
/*     */     try {
/* 469 */       breakBarrier();
/* 470 */       nextGeneration();
/*     */     } finally {
/* 472 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberWaiting() {
/* 483 */     ReentrantLock reentrantLock = this.lock;
/* 484 */     reentrantLock.lock();
/*     */     try {
/* 486 */       return this.parties - this.count;
/*     */     } finally {
/* 488 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/CyclicBarrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */