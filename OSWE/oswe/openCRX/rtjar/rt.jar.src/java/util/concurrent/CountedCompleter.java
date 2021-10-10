/*     */ package java.util.concurrent;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CountedCompleter<T>
/*     */   extends ForkJoinTask<T>
/*     */ {
/*     */   private static final long serialVersionUID = 5232453752276485070L;
/*     */   final CountedCompleter<?> completer;
/*     */   volatile int pending;
/*     */   private static final Unsafe U;
/*     */   private static final long PENDING;
/*     */   
/*     */   protected CountedCompleter(CountedCompleter<?> paramCountedCompleter, int paramInt) {
/* 428 */     this.completer = paramCountedCompleter;
/* 429 */     this.pending = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CountedCompleter(CountedCompleter<?> paramCountedCompleter) {
/* 439 */     this.completer = paramCountedCompleter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CountedCompleter() {
/* 447 */     this.completer = null;
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
/*     */   public abstract void compute();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCompletion(CountedCompleter<?> paramCountedCompleter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onExceptionalCompletion(Throwable paramThrowable, CountedCompleter<?> paramCountedCompleter) {
/* 489 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CountedCompleter<?> getCompleter() {
/* 499 */     return this.completer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getPendingCount() {
/* 508 */     return this.pending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setPendingCount(int paramInt) {
/* 517 */     this.pending = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addToPendingCount(int paramInt) {
/* 526 */     U.getAndAddInt(this, PENDING, paramInt);
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
/*     */   public final boolean compareAndSetPendingCount(int paramInt1, int paramInt2) {
/* 538 */     return U.compareAndSwapInt(this, PENDING, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int decrementPendingCountUnlessZero() {
/*     */     int i;
/*     */     do {
/*     */     
/* 549 */     } while ((i = this.pending) != 0 && 
/* 550 */       !U.compareAndSwapInt(this, PENDING, i, i - 1));
/* 551 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CountedCompleter<?> getRoot() {
/* 561 */     CountedCompleter<?> countedCompleter1 = this; CountedCompleter<?> countedCompleter2;
/* 562 */     while ((countedCompleter2 = countedCompleter1.completer) != null)
/* 563 */       countedCompleter1 = countedCompleter2; 
/* 564 */     return countedCompleter1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void tryComplete() {
/*     */     int i;
/* 574 */     CountedCompleter<?> countedCompleter1 = this, countedCompleter2 = countedCompleter1;
/*     */     do {
/* 576 */       while ((i = countedCompleter1.pending) == 0) {
/* 577 */         countedCompleter1.onCompletion(countedCompleter2);
/* 578 */         if ((countedCompleter1 = (countedCompleter2 = countedCompleter1).completer) == null) {
/* 579 */           countedCompleter2.quietlyComplete();
/*     */           return;
/*     */         } 
/*     */       } 
/* 583 */     } while (!U.compareAndSwapInt(countedCompleter1, PENDING, i, i - 1));
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
/*     */   public final void propagateCompletion() {
/*     */     int i;
/* 598 */     CountedCompleter<?> countedCompleter1 = this, countedCompleter2 = countedCompleter1;
/*     */     do {
/* 600 */       while ((i = countedCompleter1.pending) == 0) {
/* 601 */         if ((countedCompleter1 = (countedCompleter2 = countedCompleter1).completer) == null) {
/* 602 */           countedCompleter2.quietlyComplete();
/*     */           return;
/*     */         } 
/*     */       } 
/* 606 */     } while (!U.compareAndSwapInt(countedCompleter1, PENDING, i, i - 1));
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
/*     */   public void complete(T paramT) {
/* 632 */     setRawResult(paramT);
/* 633 */     onCompletion(this);
/* 634 */     quietlyComplete(); CountedCompleter<?> countedCompleter;
/* 635 */     if ((countedCompleter = this.completer) != null) {
/* 636 */       countedCompleter.tryComplete();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CountedCompleter<?> firstComplete() {
/*     */     while (true) {
/*     */       int i;
/* 649 */       if ((i = this.pending) == 0)
/* 650 */         return this; 
/* 651 */       if (U.compareAndSwapInt(this, PENDING, i, i - 1)) {
/* 652 */         return null;
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
/*     */ 
/*     */   
/*     */   public final CountedCompleter<?> nextComplete() {
/*     */     CountedCompleter<?> countedCompleter;
/* 675 */     if ((countedCompleter = this.completer) != null) {
/* 676 */       return countedCompleter.firstComplete();
/*     */     }
/* 678 */     quietlyComplete();
/* 679 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void quietlyCompleteRoot() {
/* 687 */     CountedCompleter<?> countedCompleter = this; while (true) {
/* 688 */       CountedCompleter<?> countedCompleter1; if ((countedCompleter1 = countedCompleter.completer) == null) {
/* 689 */         countedCompleter.quietlyComplete();
/*     */         return;
/*     */       } 
/* 692 */       countedCompleter = countedCompleter1;
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
/*     */   public final void helpComplete(int paramInt) {
/* 707 */     if (paramInt > 0 && this.status >= 0) {
/* 708 */       Thread thread; if (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) {
/* 709 */         ForkJoinWorkerThread forkJoinWorkerThread; (forkJoinWorkerThread = (ForkJoinWorkerThread)thread).pool
/* 710 */           .helpComplete(forkJoinWorkerThread.workQueue, this, paramInt);
/*     */       } else {
/* 712 */         ForkJoinPool.common.externalHelpComplete(this, paramInt);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void internalPropagateException(Throwable paramThrowable) {
/* 720 */     CountedCompleter<?> countedCompleter1 = this, countedCompleter2 = countedCompleter1;
/* 721 */     while (countedCompleter1.onExceptionalCompletion(paramThrowable, countedCompleter2) && (countedCompleter1 = (countedCompleter2 = countedCompleter1).completer) != null && countedCompleter1.status >= 0 && countedCompleter1
/*     */       
/* 723 */       .recordExceptionalCompletion(paramThrowable) == Integer.MIN_VALUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean exec() {
/* 731 */     compute();
/* 732 */     return false;
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
/*     */   public T getRawResult() {
/* 744 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setRawResult(T paramT) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 760 */       U = Unsafe.getUnsafe();
/*     */       
/* 762 */       PENDING = U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
/* 763 */     } catch (Exception exception) {
/* 764 */       throw new Error(exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/CountedCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */