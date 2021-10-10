/*      */ package java.util.concurrent.locks;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.concurrent.TimeUnit;
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
/*      */ public class ReentrantReadWriteLock
/*      */   implements ReadWriteLock, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -6992448646407690164L;
/*      */   private final ReadLock readerLock;
/*      */   private final WriteLock writerLock;
/*      */   final Sync sync;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long TID_OFFSET;
/*      */   
/*      */   public ReentrantReadWriteLock() {
/*  230 */     this(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReentrantReadWriteLock(boolean paramBoolean) {
/*  240 */     this.sync = paramBoolean ? new FairSync() : new NonfairSync();
/*  241 */     this.readerLock = new ReadLock(this);
/*  242 */     this.writerLock = new WriteLock(this);
/*      */   }
/*      */   
/*  245 */   public WriteLock writeLock() { return this.writerLock; } public ReadLock readLock() {
/*  246 */     return this.readerLock;
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class Sync
/*      */     extends AbstractQueuedSynchronizer
/*      */   {
/*      */     private static final long serialVersionUID = 6317671515068378041L;
/*      */     
/*      */     static final int SHARED_SHIFT = 16;
/*      */     
/*      */     static final int SHARED_UNIT = 65536;
/*      */     
/*      */     static final int MAX_COUNT = 65535;
/*      */     
/*      */     static final int EXCLUSIVE_MASK = 65535;
/*      */     
/*      */     private transient ThreadLocalHoldCounter readHolds;
/*      */     
/*      */     private transient HoldCounter cachedHoldCounter;
/*      */     
/*      */     static int sharedCount(int param1Int) {
/*  268 */       return param1Int >>> 16;
/*      */     } static int exclusiveCount(int param1Int) {
/*  270 */       return param1Int & 0xFFFF;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static final class HoldCounter
/*      */     {
/*  277 */       int count = 0;
/*      */       
/*  279 */       final long tid = ReentrantReadWriteLock.getThreadId(Thread.currentThread());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final class ThreadLocalHoldCounter
/*      */       extends ThreadLocal<HoldCounter>
/*      */     {
/*      */       public ReentrantReadWriteLock.Sync.HoldCounter initialValue() {
/*  289 */         return new ReentrantReadWriteLock.Sync.HoldCounter();
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  334 */     private transient Thread firstReader = null;
/*      */     private transient int firstReaderHoldCount;
/*      */     
/*      */     Sync() {
/*  338 */       this.readHolds = new ThreadLocalHoldCounter();
/*  339 */       setState(getState());
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
/*      */     abstract boolean readerShouldBlock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract boolean writerShouldBlock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final boolean tryRelease(int param1Int) {
/*  370 */       if (!isHeldExclusively())
/*  371 */         throw new IllegalMonitorStateException(); 
/*  372 */       int i = getState() - param1Int;
/*  373 */       boolean bool = (exclusiveCount(i) == 0) ? true : false;
/*  374 */       if (bool)
/*  375 */         setExclusiveOwnerThread(null); 
/*  376 */       setState(i);
/*  377 */       return bool;
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
/*      */     protected final boolean tryAcquire(int param1Int) {
/*  392 */       Thread thread = Thread.currentThread();
/*  393 */       int i = getState();
/*  394 */       int j = exclusiveCount(i);
/*  395 */       if (i != 0) {
/*      */         
/*  397 */         if (j == 0 || thread != getExclusiveOwnerThread())
/*  398 */           return false; 
/*  399 */         if (j + exclusiveCount(param1Int) > 65535) {
/*  400 */           throw new Error("Maximum lock count exceeded");
/*      */         }
/*  402 */         setState(i + param1Int);
/*  403 */         return true;
/*      */       } 
/*  405 */       if (writerShouldBlock() || 
/*  406 */         !compareAndSetState(i, i + param1Int))
/*  407 */         return false; 
/*  408 */       setExclusiveOwnerThread(thread);
/*  409 */       return true;
/*      */     }
/*      */     protected final boolean tryReleaseShared(int param1Int) {
/*      */       int i, j;
/*  413 */       Thread thread = Thread.currentThread();
/*  414 */       if (this.firstReader == thread)
/*      */       
/*  416 */       { if (this.firstReaderHoldCount == 1) {
/*  417 */           this.firstReader = null;
/*      */         } else {
/*  419 */           this.firstReaderHoldCount--;
/*      */         }  }
/*  421 */       else { HoldCounter holdCounter = this.cachedHoldCounter;
/*  422 */         if (holdCounter == null || holdCounter.tid != ReentrantReadWriteLock.getThreadId(thread))
/*  423 */           holdCounter = this.readHolds.get(); 
/*  424 */         j = holdCounter.count;
/*  425 */         if (j <= 1) {
/*  426 */           this.readHolds.remove();
/*  427 */           if (j <= 0)
/*  428 */             throw unmatchedUnlockException(); 
/*      */         } 
/*  430 */         holdCounter.count--; }
/*      */       
/*      */       do {
/*  433 */         i = getState();
/*  434 */         j = i - 65536;
/*  435 */       } while (!compareAndSetState(i, j));
/*      */ 
/*      */ 
/*      */       
/*  439 */       return (j == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     private IllegalMonitorStateException unmatchedUnlockException() {
/*  444 */       return new IllegalMonitorStateException("attempt to unlock read lock, not locked by current thread");
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
/*      */     protected final int tryAcquireShared(int param1Int) {
/*  464 */       Thread thread = Thread.currentThread();
/*  465 */       int i = getState();
/*  466 */       if (exclusiveCount(i) != 0 && 
/*  467 */         getExclusiveOwnerThread() != thread)
/*  468 */         return -1; 
/*  469 */       int j = sharedCount(i);
/*  470 */       if (!readerShouldBlock() && j < 65535 && 
/*      */         
/*  472 */         compareAndSetState(i, i + 65536)) {
/*  473 */         if (j == 0) {
/*  474 */           this.firstReader = thread;
/*  475 */           this.firstReaderHoldCount = 1;
/*  476 */         } else if (this.firstReader == thread) {
/*  477 */           this.firstReaderHoldCount++;
/*      */         } else {
/*  479 */           HoldCounter holdCounter = this.cachedHoldCounter;
/*  480 */           if (holdCounter == null || holdCounter.tid != ReentrantReadWriteLock.getThreadId(thread)) {
/*  481 */             this.cachedHoldCounter = holdCounter = this.readHolds.get();
/*  482 */           } else if (holdCounter.count == 0) {
/*  483 */             this.readHolds.set(holdCounter);
/*  484 */           }  holdCounter.count++;
/*      */         } 
/*  486 */         return 1;
/*      */       } 
/*  488 */       return fullTryAcquireShared(thread);
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
/*      */     final int fullTryAcquireShared(Thread param1Thread) {
/*  502 */       HoldCounter holdCounter = null;
/*      */       while (true) {
/*  504 */         int i = getState();
/*  505 */         if (exclusiveCount(i) != 0) {
/*  506 */           if (getExclusiveOwnerThread() != param1Thread) {
/*  507 */             return -1;
/*      */           }
/*      */         }
/*  510 */         else if (readerShouldBlock()) {
/*      */           
/*  512 */           if (this.firstReader != param1Thread) {
/*      */ 
/*      */             
/*  515 */             if (holdCounter == null) {
/*  516 */               holdCounter = this.cachedHoldCounter;
/*  517 */               if (holdCounter == null || holdCounter.tid != ReentrantReadWriteLock.getThreadId(param1Thread)) {
/*  518 */                 holdCounter = this.readHolds.get();
/*  519 */                 if (holdCounter.count == 0)
/*  520 */                   this.readHolds.remove(); 
/*      */               } 
/*      */             } 
/*  523 */             if (holdCounter.count == 0)
/*  524 */               return -1; 
/*      */           } 
/*      */         } 
/*  527 */         if (sharedCount(i) == 65535)
/*  528 */           throw new Error("Maximum lock count exceeded"); 
/*  529 */         if (compareAndSetState(i, i + 65536)) {
/*  530 */           if (sharedCount(i) == 0) {
/*  531 */             this.firstReader = param1Thread;
/*  532 */             this.firstReaderHoldCount = 1;
/*  533 */           } else if (this.firstReader == param1Thread) {
/*  534 */             this.firstReaderHoldCount++;
/*      */           } else {
/*  536 */             if (holdCounter == null)
/*  537 */               holdCounter = this.cachedHoldCounter; 
/*  538 */             if (holdCounter == null || holdCounter.tid != ReentrantReadWriteLock.getThreadId(param1Thread)) {
/*  539 */               holdCounter = this.readHolds.get();
/*  540 */             } else if (holdCounter.count == 0) {
/*  541 */               this.readHolds.set(holdCounter);
/*  542 */             }  holdCounter.count++;
/*  543 */             this.cachedHoldCounter = holdCounter;
/*      */           } 
/*  545 */           return 1;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean tryWriteLock() {
/*  556 */       Thread thread = Thread.currentThread();
/*  557 */       int i = getState();
/*  558 */       if (i != 0) {
/*  559 */         int j = exclusiveCount(i);
/*  560 */         if (j == 0 || thread != getExclusiveOwnerThread())
/*  561 */           return false; 
/*  562 */         if (j == 65535)
/*  563 */           throw new Error("Maximum lock count exceeded"); 
/*      */       } 
/*  565 */       if (!compareAndSetState(i, i + 1))
/*  566 */         return false; 
/*  567 */       setExclusiveOwnerThread(thread);
/*  568 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean tryReadLock() {
/*  577 */       Thread thread = Thread.currentThread();
/*      */       while (true) {
/*  579 */         int i = getState();
/*  580 */         if (exclusiveCount(i) != 0 && 
/*  581 */           getExclusiveOwnerThread() != thread)
/*  582 */           return false; 
/*  583 */         int j = sharedCount(i);
/*  584 */         if (j == 65535)
/*  585 */           throw new Error("Maximum lock count exceeded"); 
/*  586 */         if (compareAndSetState(i, i + 65536)) {
/*  587 */           if (j == 0) {
/*  588 */             this.firstReader = thread;
/*  589 */             this.firstReaderHoldCount = 1;
/*  590 */           } else if (this.firstReader == thread) {
/*  591 */             this.firstReaderHoldCount++;
/*      */           } else {
/*  593 */             HoldCounter holdCounter = this.cachedHoldCounter;
/*  594 */             if (holdCounter == null || holdCounter.tid != ReentrantReadWriteLock.getThreadId(thread)) {
/*  595 */               this.cachedHoldCounter = holdCounter = this.readHolds.get();
/*  596 */             } else if (holdCounter.count == 0) {
/*  597 */               this.readHolds.set(holdCounter);
/*  598 */             }  holdCounter.count++;
/*      */           } 
/*  600 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected final boolean isHeldExclusively() {
/*  608 */       return (getExclusiveOwnerThread() == Thread.currentThread());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final AbstractQueuedSynchronizer.ConditionObject newCondition() {
/*  614 */       return new AbstractQueuedSynchronizer.ConditionObject(this);
/*      */     }
/*      */ 
/*      */     
/*      */     final Thread getOwner() {
/*  619 */       return (exclusiveCount(getState()) == 0) ? null : 
/*      */         
/*  621 */         getExclusiveOwnerThread();
/*      */     }
/*      */     
/*      */     final int getReadLockCount() {
/*  625 */       return sharedCount(getState());
/*      */     }
/*      */     
/*      */     final boolean isWriteLocked() {
/*  629 */       return (exclusiveCount(getState()) != 0);
/*      */     }
/*      */     
/*      */     final int getWriteHoldCount() {
/*  633 */       return isHeldExclusively() ? exclusiveCount(getState()) : 0;
/*      */     }
/*      */     
/*      */     final int getReadHoldCount() {
/*  637 */       if (getReadLockCount() == 0) {
/*  638 */         return 0;
/*      */       }
/*  640 */       Thread thread = Thread.currentThread();
/*  641 */       if (this.firstReader == thread) {
/*  642 */         return this.firstReaderHoldCount;
/*      */       }
/*  644 */       HoldCounter holdCounter = this.cachedHoldCounter;
/*  645 */       if (holdCounter != null && holdCounter.tid == ReentrantReadWriteLock.getThreadId(thread)) {
/*  646 */         return holdCounter.count;
/*      */       }
/*  648 */       int i = (this.readHolds.get()).count;
/*  649 */       if (i == 0) this.readHolds.remove(); 
/*  650 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/*  658 */       param1ObjectInputStream.defaultReadObject();
/*  659 */       this.readHolds = new ThreadLocalHoldCounter();
/*  660 */       setState(0);
/*      */     }
/*      */     final int getCount() {
/*  663 */       return getState();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class NonfairSync
/*      */     extends Sync {
/*      */     private static final long serialVersionUID = -8159625535654395037L;
/*      */     
/*      */     final boolean writerShouldBlock() {
/*  672 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean readerShouldBlock() {
/*  682 */       return apparentlyFirstQueuedIsExclusive();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class FairSync
/*      */     extends Sync
/*      */   {
/*      */     private static final long serialVersionUID = -2274990926593161451L;
/*      */     
/*      */     final boolean writerShouldBlock() {
/*  692 */       return hasQueuedPredecessors();
/*      */     }
/*      */     final boolean readerShouldBlock() {
/*  695 */       return hasQueuedPredecessors();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ReadLock
/*      */     implements Lock, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -5992448646407690164L;
/*      */ 
/*      */     
/*      */     private final ReentrantReadWriteLock.Sync sync;
/*      */ 
/*      */ 
/*      */     
/*      */     protected ReadLock(ReentrantReadWriteLock param1ReentrantReadWriteLock) {
/*  713 */       this.sync = param1ReentrantReadWriteLock.sync;
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
/*      */     public void lock() {
/*  727 */       this.sync.acquireShared(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void lockInterruptibly() throws InterruptedException {
/*  772 */       this.sync.acquireSharedInterruptibly(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryLock() {
/*  799 */       return this.sync.tryReadLock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryLock(long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/*  871 */       return this.sync.tryAcquireSharedNanos(1, param1TimeUnit.toNanos(param1Long));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unlock() {
/*  881 */       this.sync.releaseShared(1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Condition newCondition() {
/*  891 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  902 */       int i = this.sync.getReadLockCount();
/*  903 */       return super.toString() + "[Read locks = " + i + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class WriteLock
/*      */     implements Lock, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4992448646407690164L;
/*      */ 
/*      */ 
/*      */     
/*      */     private final ReentrantReadWriteLock.Sync sync;
/*      */ 
/*      */ 
/*      */     
/*      */     protected WriteLock(ReentrantReadWriteLock param1ReentrantReadWriteLock) {
/*  922 */       this.sync = param1ReentrantReadWriteLock.sync;
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
/*      */     public void lock() {
/*  943 */       this.sync.acquire(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void lockInterruptibly() throws InterruptedException {
/*  998 */       this.sync.acquireInterruptibly(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryLock() {
/* 1031 */       return this.sync.tryWriteLock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryLock(long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/* 1115 */       return this.sync.tryAcquireNanos(1, param1TimeUnit.toNanos(param1Long));
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
/*      */     public void unlock() {
/* 1131 */       this.sync.release(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Condition newCondition() {
/* 1178 */       return this.sync.newCondition();
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
/*      */     public String toString() {
/* 1190 */       Thread thread = this.sync.getOwner();
/* 1191 */       return super.toString() + ((thread == null) ? "[Unlocked]" : ("[Locked by thread " + thread
/*      */         
/* 1193 */         .getName() + "]"));
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
/*      */     public boolean isHeldByCurrentThread() {
/* 1206 */       return this.sync.isHeldExclusively();
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
/*      */     public int getHoldCount() {
/* 1220 */       return this.sync.getWriteHoldCount();
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
/*      */   public final boolean isFair() {
/* 1232 */     return this.sync instanceof FairSync;
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
/*      */   protected Thread getOwner() {
/* 1249 */     return this.sync.getOwner();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReadLockCount() {
/* 1259 */     return this.sync.getReadLockCount();
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
/*      */   public boolean isWriteLocked() {
/* 1271 */     return this.sync.isWriteLocked();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWriteLockedByCurrentThread() {
/* 1281 */     return this.sync.isHeldExclusively();
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
/*      */   public int getWriteHoldCount() {
/* 1293 */     return this.sync.getWriteHoldCount();
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
/*      */   public int getReadHoldCount() {
/* 1306 */     return this.sync.getReadHoldCount();
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
/*      */   protected Collection<Thread> getQueuedWriterThreads() {
/* 1321 */     return this.sync.getExclusiveQueuedThreads();
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
/*      */   protected Collection<Thread> getQueuedReaderThreads() {
/* 1336 */     return this.sync.getSharedQueuedThreads();
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
/*      */   public final boolean hasQueuedThreads() {
/* 1350 */     return this.sync.hasQueuedThreads();
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
/*      */   public final boolean hasQueuedThread(Thread paramThread) {
/* 1365 */     return this.sync.isQueued(paramThread);
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
/*      */   public final int getQueueLength() {
/* 1379 */     return this.sync.getQueueLength();
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
/*      */   protected Collection<Thread> getQueuedThreads() {
/* 1394 */     return this.sync.getQueuedThreads();
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
/*      */   public boolean hasWaiters(Condition paramCondition) {
/* 1413 */     if (paramCondition == null)
/* 1414 */       throw new NullPointerException(); 
/* 1415 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 1416 */       throw new IllegalArgumentException("not owner"); 
/* 1417 */     return this.sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*      */   public int getWaitQueueLength(Condition paramCondition) {
/* 1436 */     if (paramCondition == null)
/* 1437 */       throw new NullPointerException(); 
/* 1438 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 1439 */       throw new IllegalArgumentException("not owner"); 
/* 1440 */     return this.sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*      */   protected Collection<Thread> getWaitingThreads(Condition paramCondition) {
/* 1461 */     if (paramCondition == null)
/* 1462 */       throw new NullPointerException(); 
/* 1463 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 1464 */       throw new IllegalArgumentException("not owner"); 
/* 1465 */     return this.sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*      */   public String toString() {
/* 1478 */     int i = this.sync.getCount();
/* 1479 */     int j = Sync.exclusiveCount(i);
/* 1480 */     int k = Sync.sharedCount(i);
/*      */     
/* 1482 */     return super.toString() + "[Write locks = " + j + ", Read locks = " + k + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long getThreadId(Thread paramThread) {
/* 1493 */     return UNSAFE.getLongVolatile(paramThread, TID_OFFSET);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1501 */       UNSAFE = Unsafe.getUnsafe();
/* 1502 */       Class<Thread> clazz = Thread.class;
/*      */       
/* 1504 */       TID_OFFSET = UNSAFE.objectFieldOffset(clazz.getDeclaredField("tid"));
/* 1505 */     } catch (Exception exception) {
/* 1506 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/locks/ReentrantReadWriteLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */