/*     */ package java.util.concurrent.locks;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReentrantLock
/*     */   implements Lock, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7373984872572414699L;
/*     */   private final Sync sync;
/*     */   
/*     */   static abstract class Sync
/*     */     extends AbstractQueuedSynchronizer
/*     */   {
/*     */     private static final long serialVersionUID = -5179523762034025860L;
/*     */     
/*     */     abstract void lock();
/*     */     
/*     */     final boolean nonfairTryAcquire(int param1Int) {
/* 130 */       Thread thread = Thread.currentThread();
/* 131 */       int i = getState();
/* 132 */       if (i == 0) {
/* 133 */         if (compareAndSetState(0, param1Int)) {
/* 134 */           setExclusiveOwnerThread(thread);
/* 135 */           return true;
/*     */         }
/*     */       
/* 138 */       } else if (thread == getExclusiveOwnerThread()) {
/* 139 */         int j = i + param1Int;
/* 140 */         if (j < 0)
/* 141 */           throw new Error("Maximum lock count exceeded"); 
/* 142 */         setState(j);
/* 143 */         return true;
/*     */       } 
/* 145 */       return false;
/*     */     }
/*     */     
/*     */     protected final boolean tryRelease(int param1Int) {
/* 149 */       int i = getState() - param1Int;
/* 150 */       if (Thread.currentThread() != getExclusiveOwnerThread())
/* 151 */         throw new IllegalMonitorStateException(); 
/* 152 */       boolean bool = false;
/* 153 */       if (i == 0) {
/* 154 */         bool = true;
/* 155 */         setExclusiveOwnerThread(null);
/*     */       } 
/* 157 */       setState(i);
/* 158 */       return bool;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected final boolean isHeldExclusively() {
/* 164 */       return (getExclusiveOwnerThread() == Thread.currentThread());
/*     */     }
/*     */     
/*     */     final AbstractQueuedSynchronizer.ConditionObject newCondition() {
/* 168 */       return new AbstractQueuedSynchronizer.ConditionObject(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     final Thread getOwner() {
/* 174 */       return (getState() == 0) ? null : getExclusiveOwnerThread();
/*     */     }
/*     */     
/*     */     final int getHoldCount() {
/* 178 */       return isHeldExclusively() ? getState() : 0;
/*     */     }
/*     */     
/*     */     final boolean isLocked() {
/* 182 */       return (getState() != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 190 */       param1ObjectInputStream.defaultReadObject();
/* 191 */       setState(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class NonfairSync
/*     */     extends Sync
/*     */   {
/*     */     private static final long serialVersionUID = 7316153563782823691L;
/*     */ 
/*     */ 
/*     */     
/*     */     final void lock() {
/* 206 */       if (compareAndSetState(0, 1)) {
/* 207 */         setExclusiveOwnerThread(Thread.currentThread());
/*     */       } else {
/* 209 */         acquire(1);
/*     */       } 
/*     */     }
/*     */     protected final boolean tryAcquire(int param1Int) {
/* 213 */       return nonfairTryAcquire(param1Int);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class FairSync
/*     */     extends Sync
/*     */   {
/*     */     private static final long serialVersionUID = -3000897897090466540L;
/*     */     
/*     */     final void lock() {
/* 224 */       acquire(1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final boolean tryAcquire(int param1Int) {
/* 232 */       Thread thread = Thread.currentThread();
/* 233 */       int i = getState();
/* 234 */       if (i == 0) {
/* 235 */         if (!hasQueuedPredecessors() && 
/* 236 */           compareAndSetState(0, param1Int)) {
/* 237 */           setExclusiveOwnerThread(thread);
/* 238 */           return true;
/*     */         }
/*     */       
/* 241 */       } else if (thread == getExclusiveOwnerThread()) {
/* 242 */         int j = i + param1Int;
/* 243 */         if (j < 0)
/* 244 */           throw new Error("Maximum lock count exceeded"); 
/* 245 */         setState(j);
/* 246 */         return true;
/*     */       } 
/* 248 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReentrantLock() {
/* 257 */     this.sync = new NonfairSync();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReentrantLock(boolean paramBoolean) {
/* 267 */     this.sync = paramBoolean ? new FairSync() : new NonfairSync();
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
/*     */   public void lock() {
/* 285 */     this.sync.lock();
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
/*     */   public void lockInterruptibly() throws InterruptedException {
/* 335 */     this.sync.acquireInterruptibly(1);
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
/*     */   public boolean tryLock() {
/* 365 */     return this.sync.nonfairTryAcquire(1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 442 */     return this.sync.tryAcquireNanos(1, paramTimeUnit.toNanos(paramLong));
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
/*     */   public void unlock() {
/* 457 */     this.sync.release(1);
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
/*     */   public Condition newCondition() {
/* 500 */     return this.sync.newCondition();
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
/*     */   public int getHoldCount() {
/* 533 */     return this.sync.getHoldCount();
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
/*     */   public boolean isHeldByCurrentThread() {
/* 578 */     return this.sync.isHeldExclusively();
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
/*     */   public boolean isLocked() {
/* 590 */     return this.sync.isLocked();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isFair() {
/* 599 */     return this.sync instanceof FairSync;
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
/*     */   protected Thread getOwner() {
/* 616 */     return this.sync.getOwner();
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
/*     */   public final boolean hasQueuedThreads() {
/* 630 */     return this.sync.hasQueuedThreads();
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
/*     */   public final boolean hasQueuedThread(Thread paramThread) {
/* 645 */     return this.sync.isQueued(paramThread);
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
/*     */   public final int getQueueLength() {
/* 659 */     return this.sync.getQueueLength();
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
/*     */   protected Collection<Thread> getQueuedThreads() {
/* 674 */     return this.sync.getQueuedThreads();
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
/*     */   public boolean hasWaiters(Condition paramCondition) {
/* 693 */     if (paramCondition == null)
/* 694 */       throw new NullPointerException(); 
/* 695 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 696 */       throw new IllegalArgumentException("not owner"); 
/* 697 */     return this.sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*     */   public int getWaitQueueLength(Condition paramCondition) {
/* 716 */     if (paramCondition == null)
/* 717 */       throw new NullPointerException(); 
/* 718 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 719 */       throw new IllegalArgumentException("not owner"); 
/* 720 */     return this.sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*     */   protected Collection<Thread> getWaitingThreads(Condition paramCondition) {
/* 741 */     if (paramCondition == null)
/* 742 */       throw new NullPointerException(); 
/* 743 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 744 */       throw new IllegalArgumentException("not owner"); 
/* 745 */     return this.sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*     */   public String toString() {
/* 757 */     Thread thread = this.sync.getOwner();
/* 758 */     return super.toString() + ((thread == null) ? "[Unlocked]" : ("[Locked by thread " + thread
/*     */       
/* 760 */       .getName() + "]"));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/locks/ReentrantLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */