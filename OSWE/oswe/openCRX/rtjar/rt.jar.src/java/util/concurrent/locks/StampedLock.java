/*      */ package java.util.concurrent.locks;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StampedLock
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -6001602636862214147L;
/*  272 */   private static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */ 
/*      */   
/*  275 */   private static final int SPINS = (NCPU > 1) ? 64 : 0;
/*      */ 
/*      */   
/*  278 */   private static final int HEAD_SPINS = (NCPU > 1) ? 1024 : 0;
/*      */ 
/*      */   
/*  281 */   private static final int MAX_HEAD_SPINS = (NCPU > 1) ? 65536 : 0;
/*      */   
/*      */   private static final int OVERFLOW_YIELD_RATE = 7;
/*      */   
/*      */   private static final int LG_READERS = 7;
/*      */   
/*      */   private static final long RUNIT = 1L;
/*      */   
/*      */   private static final long WBIT = 128L;
/*      */   
/*      */   private static final long RBITS = 127L;
/*      */   
/*      */   private static final long RFULL = 126L;
/*      */   
/*      */   private static final long ABITS = 255L;
/*      */   private static final long SBITS = -128L;
/*      */   private static final long ORIGIN = 256L;
/*      */   private static final long INTERRUPTED = 1L;
/*      */   private static final int WAITING = -1;
/*      */   private static final int CANCELLED = 1;
/*      */   private static final int RMODE = 0;
/*      */   private static final int WMODE = 1;
/*      */   private volatile transient WNode whead;
/*      */   private volatile transient WNode wtail;
/*      */   transient ReadLockView readLockView;
/*      */   transient WriteLockView writeLockView;
/*      */   transient ReadWriteLockView readWriteLockView;
/*      */   
/*      */   static final class WNode
/*      */   {
/*      */     volatile WNode prev;
/*      */     volatile WNode next;
/*      */     volatile WNode cowait;
/*      */     volatile Thread thread;
/*      */     volatile int status;
/*      */     final int mode;
/*      */     
/*      */     WNode(int param1Int, WNode param1WNode) {
/*  319 */       this.mode = param1Int; this.prev = param1WNode;
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
/*  341 */   private volatile transient long state = 256L;
/*      */   
/*      */   private transient int readerOverflow;
/*      */   
/*      */   private static final Unsafe U;
/*      */   private static final long STATE;
/*      */   private static final long WHEAD;
/*      */   
/*      */   public long writeLock() {
/*      */     long l1;
/*      */     long l2;
/*  352 */     return (((l1 = this.state) & 0xFFL) == 0L && U
/*  353 */       .compareAndSwapLong(this, STATE, l1, l2 = l1 + 128L)) ? l2 : 
/*  354 */       acquireWrite(false, 0L);
/*      */   }
/*      */   private static final long WTAIL;
/*      */   private static final long WNEXT;
/*      */   private static final long WSTATUS;
/*      */   private static final long WCOWAIT;
/*      */   private static final long PARKBLOCKER;
/*      */   
/*      */   public long tryWriteLock() {
/*      */     long l1;
/*      */     long l2;
/*  365 */     return (((l1 = this.state) & 0xFFL) == 0L && U
/*  366 */       .compareAndSwapLong(this, STATE, l1, l2 = l1 + 128L)) ? l2 : 0L;
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
/*      */   public long tryWriteLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  385 */     long l = paramTimeUnit.toNanos(paramLong);
/*  386 */     if (!Thread.interrupted()) {
/*      */       long l1;
/*  388 */       if ((l1 = tryWriteLock()) != 0L)
/*  389 */         return l1; 
/*  390 */       if (l <= 0L)
/*  391 */         return 0L;  long l2;
/*  392 */       if ((l2 = System.nanoTime() + l) == 0L)
/*  393 */         l2 = 1L; 
/*  394 */       if ((l1 = acquireWrite(true, l2)) != 1L)
/*  395 */         return l1; 
/*      */     } 
/*  397 */     throw new InterruptedException();
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
/*      */   public long writeLockInterruptibly() throws InterruptedException {
/*      */     long l;
/*  412 */     if (!Thread.interrupted() && (
/*  413 */       l = acquireWrite(true, 0L)) != 1L)
/*  414 */       return l; 
/*  415 */     throw new InterruptedException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long readLock() {
/*  425 */     long l1 = this.state; long l2;
/*  426 */     return (this.whead == this.wtail && (l1 & 0xFFL) < 126L && U
/*  427 */       .compareAndSwapLong(this, STATE, l1, l2 = l1 + 1L)) ? l2 : 
/*  428 */       acquireRead(false, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long tryReadLock() {
/*      */     while (true) {
/*      */       long l1;
/*      */       long l2;
/*  440 */       if ((l2 = (l1 = this.state) & 0xFFL) == 128L)
/*  441 */         return 0L; 
/*  442 */       if (l2 < 126L) {
/*  443 */         long l; if (U.compareAndSwapLong(this, STATE, l1, l = l1 + 1L))
/*  444 */           return l;  continue;
/*      */       }  long l3;
/*  446 */       if ((l3 = tryIncReaderOverflow(l1)) != 0L) {
/*  447 */         return l3;
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
/*      */   public long tryReadLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  467 */     long l = paramTimeUnit.toNanos(paramLong);
/*  468 */     if (!Thread.interrupted()) {
/*  469 */       long l1; long l2; if ((l2 = (l1 = this.state) & 0xFFL) != 128L)
/*  470 */         if (l2 < 126L) {
/*  471 */           long l5; if (U.compareAndSwapLong(this, STATE, l1, l5 = l1 + 1L))
/*  472 */             return l5; 
/*      */         } else {
/*  474 */           long l5; if ((l5 = tryIncReaderOverflow(l1)) != 0L)
/*  475 */             return l5; 
/*      */         }  
/*  477 */       if (l <= 0L)
/*  478 */         return 0L;  long l4;
/*  479 */       if ((l4 = System.nanoTime() + l) == 0L)
/*  480 */         l4 = 1L;  long l3;
/*  481 */       if ((l3 = acquireRead(true, l4)) != 1L)
/*  482 */         return l3; 
/*      */     } 
/*  484 */     throw new InterruptedException();
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
/*      */   public long readLockInterruptibly() throws InterruptedException {
/*      */     long l;
/*  499 */     if (!Thread.interrupted() && (
/*  500 */       l = acquireRead(true, 0L)) != 1L)
/*  501 */       return l; 
/*  502 */     throw new InterruptedException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long tryOptimisticRead() {
/*      */     long l;
/*  513 */     return (((l = this.state) & 0x80L) == 0L) ? (l & 0xFFFFFFFFFFFFFF80L) : 0L;
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
/*      */   public boolean validate(long paramLong) {
/*  529 */     U.loadFence();
/*  530 */     return ((paramLong & 0xFFFFFFFFFFFFFF80L) == (this.state & 0xFFFFFFFFFFFFFF80L));
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
/*      */   public void unlockWrite(long paramLong) {
/*  543 */     if (this.state != paramLong || (paramLong & 0x80L) == 0L)
/*  544 */       throw new IllegalMonitorStateException(); 
/*  545 */     this.state = ((paramLong += 128L) == 0L) ? 256L : paramLong; WNode wNode;
/*  546 */     if ((wNode = this.whead) != null && wNode.status != 0) {
/*  547 */       release(wNode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unlockRead(long paramLong) {
/*      */     while (true) {
/*      */       long l1;
/*      */       long l2;
/*  561 */       if (((l1 = this.state) & 0xFFFFFFFFFFFFFF80L) != (paramLong & 0xFFFFFFFFFFFFFF80L) || (paramLong & 0xFFL) == 0L || (l2 = l1 & 0xFFL) == 0L || l2 == 128L)
/*      */       {
/*  563 */         throw new IllegalMonitorStateException(); } 
/*  564 */       if (l2 < 126L) {
/*  565 */         if (U.compareAndSwapLong(this, STATE, l1, l1 - 1L)) {
/*  566 */           WNode wNode; if (l2 == 1L && (wNode = this.whead) != null && wNode.status != 0)
/*  567 */             release(wNode);  break;
/*      */         } 
/*      */         continue;
/*      */       } 
/*  571 */       if (tryDecReaderOverflow(l1) != 0L) {
/*      */         break;
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
/*      */   public void unlock(long paramLong) {
/*  585 */     long l1 = paramLong & 0xFFL; long l2, l3;
/*  586 */     while (((l3 = this.state) & 0xFFFFFFFFFFFFFF80L) == (paramLong & 0xFFFFFFFFFFFFFF80L) && (
/*  587 */       l2 = l3 & 0xFFL) != 0L) {
/*      */       
/*  589 */       if (l2 == 128L) {
/*  590 */         if (l1 != l2)
/*      */           break; 
/*  592 */         this.state = ((l3 += 128L) == 0L) ? 256L : l3; WNode wNode;
/*  593 */         if ((wNode = this.whead) != null && wNode.status != 0)
/*  594 */           release(wNode); 
/*      */         return;
/*      */       } 
/*  597 */       if (l1 == 0L || l1 >= 128L)
/*      */         break; 
/*  599 */       if (l2 < 126L) {
/*  600 */         if (U.compareAndSwapLong(this, STATE, l3, l3 - 1L)) {
/*  601 */           WNode wNode; if (l2 == 1L && (wNode = this.whead) != null && wNode.status != 0)
/*  602 */             release(wNode);  return;
/*      */         } 
/*      */         continue;
/*      */       } 
/*  606 */       if (tryDecReaderOverflow(l3) != 0L)
/*      */         return; 
/*      */     } 
/*  609 */     throw new IllegalMonitorStateException();
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
/*      */   public long tryConvertToWriteLock(long paramLong) {
/*  625 */     long l1 = paramLong & 0xFFL; long l2;
/*  626 */     while (((l2 = this.state) & 0xFFFFFFFFFFFFFF80L) == (paramLong & 0xFFFFFFFFFFFFFF80L)) {
/*  627 */       long l; if ((l = l2 & 0xFFL) == 0L) {
/*  628 */         if (l1 != 0L)
/*      */           break;  long l3;
/*  630 */         if (U.compareAndSwapLong(this, STATE, l2, l3 = l2 + 128L))
/*  631 */           return l3;  continue;
/*      */       } 
/*  633 */       if (l == 128L) {
/*  634 */         if (l1 != l)
/*      */           break; 
/*  636 */         return paramLong;
/*      */       } 
/*  638 */       if (l == 1L && l1 != 0L) {
/*  639 */         long l3; if (U.compareAndSwapLong(this, STATE, l2, l3 = l2 - 1L + 128L))
/*      */         {
/*  641 */           return l3;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  646 */     return 0L;
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
/*      */   public long tryConvertToReadLock(long paramLong) {
/*  661 */     long l1 = paramLong & 0xFFL; long l2;
/*  662 */     while (((l2 = this.state) & 0xFFFFFFFFFFFFFF80L) == (paramLong & 0xFFFFFFFFFFFFFF80L)) {
/*  663 */       long l; if ((l = l2 & 0xFFL) == 0L) {
/*  664 */         if (l1 != 0L)
/*      */           break; 
/*  666 */         if (l < 126L) {
/*  667 */           long l4; if (U.compareAndSwapLong(this, STATE, l2, l4 = l2 + 1L))
/*  668 */             return l4;  continue;
/*      */         }  long l3;
/*  670 */         if ((l3 = tryIncReaderOverflow(l2)) != 0L)
/*  671 */           return l3;  continue;
/*      */       } 
/*  673 */       if (l == 128L) {
/*  674 */         if (l1 != l)
/*      */           break; 
/*  676 */         long l3 = l2 + 129L; WNode wNode;
/*  677 */         if ((wNode = this.whead) != null && wNode.status != 0)
/*  678 */           release(wNode); 
/*  679 */         return l3;
/*      */       } 
/*  681 */       if (l1 != 0L && l1 < 128L) {
/*  682 */         return paramLong;
/*      */       }
/*      */     } 
/*      */     
/*  686 */     return 0L;
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
/*      */   public long tryConvertToOptimisticRead(long paramLong) {
/*  700 */     long l1 = paramLong & 0xFFL;
/*  701 */     U.loadFence();
/*      */     long l2;
/*  703 */     while (((l2 = this.state) & 0xFFFFFFFFFFFFFF80L) == (paramLong & 0xFFFFFFFFFFFFFF80L)) {
/*      */       long l3;
/*  705 */       if ((l3 = l2 & 0xFFL) == 0L) {
/*  706 */         if (l1 != 0L)
/*      */           break; 
/*  708 */         return l2;
/*      */       } 
/*  710 */       if (l3 == 128L) {
/*  711 */         if (l1 != l3)
/*      */           break; 
/*  713 */         long l = ((l2 += 128L) == 0L) ? 256L : l2; WNode wNode;
/*  714 */         if ((wNode = this.whead) != null && wNode.status != 0)
/*  715 */           release(wNode); 
/*  716 */         return l;
/*      */       } 
/*  718 */       if (l1 == 0L || l1 >= 128L)
/*      */         break; 
/*  720 */       if (l3 < 126L) {
/*  721 */         long l; if (U.compareAndSwapLong(this, STATE, l2, l = l2 - 1L)) {
/*  722 */           WNode wNode; if (l3 == 1L && (wNode = this.whead) != null && wNode.status != 0)
/*  723 */             release(wNode); 
/*  724 */           return l & 0xFFFFFFFFFFFFFF80L;
/*      */         }  continue;
/*      */       }  long l4;
/*  727 */       if ((l4 = tryDecReaderOverflow(l2)) != 0L)
/*  728 */         return l4 & 0xFFFFFFFFFFFFFF80L; 
/*      */     } 
/*  730 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryUnlockWrite() {
/*      */     long l;
/*  742 */     if (((l = this.state) & 0x80L) != 0L) {
/*  743 */       this.state = ((l += 128L) == 0L) ? 256L : l; WNode wNode;
/*  744 */       if ((wNode = this.whead) != null && wNode.status != 0)
/*  745 */         release(wNode); 
/*  746 */       return true;
/*      */     } 
/*  748 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryUnlockRead() {
/*      */     long l1;
/*      */     long l2;
/*  760 */     while ((l2 = (l1 = this.state) & 0xFFL) != 0L && l2 < 128L) {
/*  761 */       if (l2 < 126L) {
/*  762 */         if (U.compareAndSwapLong(this, STATE, l1, l1 - 1L)) {
/*  763 */           WNode wNode; if (l2 == 1L && (wNode = this.whead) != null && wNode.status != 0)
/*  764 */             release(wNode); 
/*  765 */           return true;
/*      */         }  continue;
/*      */       } 
/*  768 */       if (tryDecReaderOverflow(l1) != 0L)
/*  769 */         return true; 
/*      */     } 
/*  771 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getReadLockCount(long paramLong) {
/*      */     long l;
/*  782 */     if ((l = paramLong & 0x7FL) >= 126L)
/*  783 */       l = 126L + this.readerOverflow; 
/*  784 */     return (int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWriteLocked() {
/*  793 */     return ((this.state & 0x80L) != 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadLocked() {
/*  802 */     return ((this.state & 0x7FL) != 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReadLockCount() {
/*  812 */     return getReadLockCount(this.state);
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
/*  825 */     long l = this.state;
/*  826 */     return super.toString() + (((l & 0xFFL) == 0L) ? "[Unlocked]" : (((l & 0x80L) != 0L) ? "[Write-locked]" : ("[Read-locks:" + 
/*      */ 
/*      */       
/*  829 */       getReadLockCount(l) + "]")));
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
/*      */   public Lock asReadLock() {
/*      */     ReadLockView readLockView;
/*  846 */     return ((readLockView = this.readLockView) != null) ? readLockView : (this.readLockView = new ReadLockView());
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
/*      */   public Lock asWriteLock() {
/*      */     WriteLockView writeLockView;
/*  862 */     return ((writeLockView = this.writeLockView) != null) ? writeLockView : (this.writeLockView = new WriteLockView());
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
/*      */   public ReadWriteLock asReadWriteLock() {
/*      */     ReadWriteLockView readWriteLockView;
/*  876 */     return ((readWriteLockView = this.readWriteLockView) != null) ? readWriteLockView : (this.readWriteLockView = new ReadWriteLockView());
/*      */   }
/*      */   
/*      */   final class ReadLockView
/*      */     implements Lock
/*      */   {
/*      */     public void lock() {
/*  883 */       StampedLock.this.readLock();
/*      */     } public void lockInterruptibly() throws InterruptedException {
/*  885 */       StampedLock.this.readLockInterruptibly();
/*      */     } public boolean tryLock() {
/*  887 */       return (StampedLock.this.tryReadLock() != 0L);
/*      */     }
/*      */     public boolean tryLock(long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/*  890 */       return (StampedLock.this.tryReadLock(param1Long, param1TimeUnit) != 0L);
/*      */     } public void unlock() {
/*  892 */       StampedLock.this.unstampedUnlockRead();
/*      */     } public Condition newCondition() {
/*  894 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   final class WriteLockView implements Lock {
/*      */     public void lock() {
/*  899 */       StampedLock.this.writeLock();
/*      */     } public void lockInterruptibly() throws InterruptedException {
/*  901 */       StampedLock.this.writeLockInterruptibly();
/*      */     } public boolean tryLock() {
/*  903 */       return (StampedLock.this.tryWriteLock() != 0L);
/*      */     }
/*      */     public boolean tryLock(long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/*  906 */       return (StampedLock.this.tryWriteLock(param1Long, param1TimeUnit) != 0L);
/*      */     } public void unlock() {
/*  908 */       StampedLock.this.unstampedUnlockWrite();
/*      */     } public Condition newCondition() {
/*  910 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   final class ReadWriteLockView implements ReadWriteLock {
/*  915 */     public Lock readLock() { return StampedLock.this.asReadLock(); } public Lock writeLock() {
/*  916 */       return StampedLock.this.asWriteLock();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final void unstampedUnlockWrite() {
/*      */     long l;
/*  924 */     if (((l = this.state) & 0x80L) == 0L)
/*  925 */       throw new IllegalMonitorStateException(); 
/*  926 */     this.state = ((l += 128L) == 0L) ? 256L : l; WNode wNode;
/*  927 */     if ((wNode = this.whead) != null && wNode.status != 0)
/*  928 */       release(wNode); 
/*      */   }
/*      */   final void unstampedUnlockRead() {
/*      */     while (true) {
/*      */       long l1;
/*      */       long l2;
/*  934 */       if ((l2 = (l1 = this.state) & 0xFFL) == 0L || l2 >= 128L)
/*  935 */         throw new IllegalMonitorStateException(); 
/*  936 */       if (l2 < 126L) {
/*  937 */         if (U.compareAndSwapLong(this, STATE, l1, l1 - 1L)) {
/*  938 */           WNode wNode; if (l2 == 1L && (wNode = this.whead) != null && wNode.status != 0)
/*  939 */             release(wNode);  break;
/*      */         } 
/*      */         continue;
/*      */       } 
/*  943 */       if (tryDecReaderOverflow(l1) != 0L) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  950 */     paramObjectInputStream.defaultReadObject();
/*  951 */     this.state = 256L;
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
/*      */   private long tryIncReaderOverflow(long paramLong) {
/*  966 */     if ((paramLong & 0xFFL) == 126L) {
/*  967 */       if (U.compareAndSwapLong(this, STATE, paramLong, paramLong | 0x7FL)) {
/*  968 */         this.readerOverflow++;
/*  969 */         this.state = paramLong;
/*  970 */         return paramLong;
/*      */       }
/*      */     
/*  973 */     } else if ((LockSupport.nextSecondarySeed() & 0x7) == 0) {
/*      */       
/*  975 */       Thread.yield();
/*  976 */     }  return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long tryDecReaderOverflow(long paramLong) {
/*  987 */     if ((paramLong & 0xFFL) == 126L) {
/*  988 */       if (U.compareAndSwapLong(this, STATE, paramLong, paramLong | 0x7FL)) {
/*      */         long l; int i;
/*  990 */         if ((i = this.readerOverflow) > 0) {
/*  991 */           this.readerOverflow = i - 1;
/*  992 */           l = paramLong;
/*      */         } else {
/*      */           
/*  995 */           l = paramLong - 1L;
/*  996 */         }  this.state = l;
/*  997 */         return l;
/*      */       }
/*      */     
/* 1000 */     } else if ((LockSupport.nextSecondarySeed() & 0x7) == 0) {
/*      */       
/* 1002 */       Thread.yield();
/* 1003 */     }  return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void release(WNode paramWNode) {
/* 1014 */     if (paramWNode != null) {
/*      */       
/* 1016 */       U.compareAndSwapInt(paramWNode, WSTATUS, -1, 0); WNode wNode;
/* 1017 */       if ((wNode = paramWNode.next) == null || wNode.status == 1)
/* 1018 */         for (WNode wNode1 = this.wtail; wNode1 != null && wNode1 != paramWNode; wNode1 = wNode1.prev) {
/* 1019 */           if (wNode1.status <= 0)
/* 1020 */             wNode = wNode1; 
/*      */         }   Thread thread;
/* 1022 */       if (wNode != null && (thread = wNode.thread) != null) {
/* 1023 */         U.unpark(thread);
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
/*      */   private long acquireWrite(boolean paramBoolean, long paramLong) {
/* 1037 */     WNode wNode = null;
/* 1038 */     int i = -1; while (true) {
/*      */       long l1; long l2;
/* 1040 */       while ((l1 = (l2 = this.state) & 0xFFL) == 0L) {
/* 1041 */         long l; if (U.compareAndSwapLong(this, STATE, l2, l = l2 + 128L))
/* 1042 */           return l; 
/*      */       } 
/* 1044 */       if (i < 0) {
/* 1045 */         i = (l1 == 128L && this.wtail == this.whead) ? SPINS : 0; continue;
/* 1046 */       }  if (i > 0) {
/* 1047 */         if (LockSupport.nextSecondarySeed() >= 0)
/* 1048 */           i--;  continue;
/*      */       }  WNode wNode1;
/* 1050 */       if ((wNode1 = this.wtail) == null) {
/* 1051 */         WNode wNode2 = new WNode(1, null);
/* 1052 */         if (U.compareAndSwapObject(this, WHEAD, null, wNode2))
/* 1053 */           this.wtail = wNode2;  continue;
/*      */       } 
/* 1055 */       if (wNode == null) {
/* 1056 */         wNode = new WNode(1, wNode1); continue;
/* 1057 */       }  if (wNode.prev != wNode1) {
/* 1058 */         wNode.prev = wNode1; continue;
/* 1059 */       }  if (U.compareAndSwapObject(this, WTAIL, wNode1, wNode)) {
/* 1060 */         wNode1.next = wNode;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1065 */         i = -1; while (true) {
/*      */           WNode wNode2;
/* 1067 */           if ((wNode2 = this.whead) == wNode1) {
/* 1068 */             if (i < 0) {
/* 1069 */               i = HEAD_SPINS;
/* 1070 */             } else if (i < MAX_HEAD_SPINS) {
/* 1071 */               i <<= 1;
/* 1072 */             }  int j = i; do {
/*      */               long l;
/* 1074 */               while (((l = this.state) & 0xFFL) == 0L) {
/* 1075 */                 long l3; if (U.compareAndSwapLong(this, STATE, l, l3 = l + 128L)) {
/*      */                   
/* 1077 */                   this.whead = wNode;
/* 1078 */                   wNode.prev = null;
/* 1079 */                   return l3;
/*      */                 } 
/*      */               } 
/* 1082 */             } while (LockSupport.nextSecondarySeed() < 0 || --j > 0);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1087 */           else if (wNode2 != null) {
/*      */             WNode wNode3;
/* 1089 */             while ((wNode3 = wNode2.cowait) != null) {
/* 1090 */               Thread thread; if (U.compareAndSwapObject(wNode2, WCOWAIT, wNode3, wNode3.cowait) && (thread = wNode3.thread) != null)
/*      */               {
/* 1092 */                 U.unpark(thread); } 
/*      */             } 
/*      */           } 
/* 1095 */           if (this.whead == wNode2) {
/* 1096 */             long l; WNode wNode3; if ((wNode3 = wNode.prev) != wNode1) {
/* 1097 */               if (wNode3 != null)
/* 1098 */                 (wNode1 = wNode3).next = wNode;  continue;
/*      */             }  int j;
/* 1100 */             if ((j = wNode1.status) == 0) {
/* 1101 */               U.compareAndSwapInt(wNode1, WSTATUS, 0, -1); continue;
/* 1102 */             }  if (j == 1) {
/* 1103 */               WNode wNode4; if ((wNode4 = wNode1.prev) != null) {
/* 1104 */                 wNode.prev = wNode4;
/* 1105 */                 wNode4.next = wNode;
/*      */               } 
/*      */               
/*      */               continue;
/*      */             } 
/* 1110 */             if (paramLong == 0L) {
/* 1111 */               l = 0L;
/* 1112 */             } else if ((l = paramLong - System.nanoTime()) <= 0L) {
/* 1113 */               return cancelWaiter(wNode, wNode, false);
/* 1114 */             }  Thread thread = Thread.currentThread();
/* 1115 */             U.putObject(thread, PARKBLOCKER, this);
/* 1116 */             wNode.thread = thread;
/* 1117 */             if (wNode1.status < 0 && (wNode1 != wNode2 || (this.state & 0xFFL) != 0L) && this.whead == wNode2 && wNode.prev == wNode1)
/*      */             {
/* 1119 */               U.park(false, l); } 
/* 1120 */             wNode.thread = null;
/* 1121 */             U.putObject(thread, PARKBLOCKER, null);
/* 1122 */             if (paramBoolean && Thread.interrupted()) {
/* 1123 */               return cancelWaiter(wNode, wNode, true);
/*      */             }
/*      */           } 
/*      */         } 
/*      */         break;
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
/*      */   private long acquireRead(boolean paramBoolean, long paramLong) {
/* 1139 */     WNode wNode2, wNode1 = null;
/* 1140 */     int i = -1; label175: while (true) {
/*      */       WNode wNode;
/* 1142 */       if ((wNode = this.whead) == (wNode2 = this.wtail)) {
/*      */         while (true) {
/* 1144 */           long l1; long l2; long l3; if (((l1 = (l2 = this.state) & 0xFFL) < 126L) ? U
/* 1145 */             .compareAndSwapLong(this, STATE, l2, l3 = l2 + 1L) : (l1 < 128L && (
/* 1146 */             l3 = tryIncReaderOverflow(l2)) != 0L))
/* 1147 */             return l3; 
/* 1148 */           if (l1 >= 128L) {
/* 1149 */             if (i > 0) {
/* 1150 */               if (LockSupport.nextSecondarySeed() >= 0)
/* 1151 */                 i--; 
/*      */               continue;
/*      */             } 
/* 1154 */             if (i == 0) {
/* 1155 */               WNode wNode3 = this.whead, wNode4 = this.wtail;
/* 1156 */               if ((wNode3 == wNode && wNode4 == wNode2) || (wNode = wNode3) != (wNode2 = wNode4))
/*      */                 break; 
/*      */             } 
/* 1159 */             i = SPINS;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1164 */       if (wNode2 == null) {
/* 1165 */         WNode wNode3 = new WNode(1, null);
/* 1166 */         if (U.compareAndSwapObject(this, WHEAD, null, wNode3))
/* 1167 */           this.wtail = wNode3;  continue;
/*      */       } 
/* 1169 */       if (wNode1 == null) {
/* 1170 */         wNode1 = new WNode(0, wNode2); continue;
/* 1171 */       }  if (wNode == wNode2 || wNode2.mode != 0) {
/* 1172 */         if (wNode1.prev != wNode2) {
/* 1173 */           wNode1.prev = wNode2; continue;
/* 1174 */         }  if (U.compareAndSwapObject(this, WTAIL, wNode2, wNode1)) {
/* 1175 */           wNode2.next = wNode1; break;
/*      */         } 
/*      */         continue;
/*      */       } 
/* 1179 */       if (!U.compareAndSwapObject(wNode2, WCOWAIT, wNode1.cowait = wNode2.cowait, wNode1)) {
/*      */         
/* 1181 */         wNode1.cowait = null; continue;
/*      */       }  while (true) {
/*      */         WNode wNode4;
/*      */         Thread thread;
/* 1185 */         if ((wNode = this.whead) != null && (wNode4 = wNode.cowait) != null && U
/* 1186 */           .compareAndSwapObject(wNode, WCOWAIT, wNode4, wNode4.cowait) && (thread = wNode4.thread) != null)
/*      */         {
/* 1188 */           U.unpark(thread); }  WNode wNode3;
/* 1189 */         if (wNode == (wNode3 = wNode2.prev) || wNode == wNode2 || wNode3 == null) {
/*      */           long l; do {
/*      */             long l1; long l2;
/* 1192 */             if (((l = (l1 = this.state) & 0xFFL) < 126L) ? U
/* 1193 */               .compareAndSwapLong(this, STATE, l1, l2 = l1 + 1L) : (l < 128L && (
/*      */ 
/*      */               
/* 1196 */               l2 = tryIncReaderOverflow(l1)) != 0L))
/* 1197 */               return l2; 
/* 1198 */           } while (l < 128L);
/*      */         } 
/* 1200 */         if (this.whead == wNode && wNode2.prev == wNode3) {
/*      */           long l;
/* 1202 */           if (wNode3 == null || wNode == wNode2 || wNode2.status > 0) {
/* 1203 */             wNode1 = null;
/*      */             continue label175;
/*      */           } 
/* 1206 */           if (paramLong == 0L) {
/* 1207 */             l = 0L;
/* 1208 */           } else if ((l = paramLong - System.nanoTime()) <= 0L) {
/* 1209 */             return cancelWaiter(wNode1, wNode2, false);
/* 1210 */           }  Thread thread1 = Thread.currentThread();
/* 1211 */           U.putObject(thread1, PARKBLOCKER, this);
/* 1212 */           wNode1.thread = thread1;
/* 1213 */           if ((wNode != wNode3 || (this.state & 0xFFL) == 128L) && this.whead == wNode && wNode2.prev == wNode3)
/*      */           {
/* 1215 */             U.park(false, l); } 
/* 1216 */           wNode1.thread = null;
/* 1217 */           U.putObject(thread1, PARKBLOCKER, null);
/* 1218 */           if (paramBoolean && Thread.interrupted()) {
/* 1219 */             return cancelWaiter(wNode1, wNode2, true);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1225 */     i = -1; while (true) {
/*      */       WNode wNode;
/* 1227 */       if ((wNode = this.whead) == wNode2) {
/* 1228 */         long l; if (i < 0) {
/* 1229 */           i = HEAD_SPINS;
/* 1230 */         } else if (i < MAX_HEAD_SPINS) {
/* 1231 */           i <<= 1;
/* 1232 */         }  int j = i; do {
/*      */           long l1; long l2;
/* 1234 */           if (((l = (l1 = this.state) & 0xFFL) < 126L) ? U
/* 1235 */             .compareAndSwapLong(this, STATE, l1, l2 = l1 + 1L) : (l < 128L && (
/* 1236 */             l2 = tryIncReaderOverflow(l1)) != 0L)) {
/*      */             
/* 1238 */             this.whead = wNode1;
/* 1239 */             wNode1.prev = null; WNode wNode3;
/* 1240 */             while ((wNode3 = wNode1.cowait) != null) {
/* 1241 */               Thread thread; if (U.compareAndSwapObject(wNode1, WCOWAIT, wNode3, wNode3.cowait) && (thread = wNode3.thread) != null)
/*      */               {
/*      */                 
/* 1244 */                 U.unpark(thread); } 
/*      */             } 
/* 1246 */             return l2;
/*      */           } 
/* 1248 */         } while (l < 128L || 
/* 1249 */           LockSupport.nextSecondarySeed() < 0 || --j > 0);
/*      */ 
/*      */       
/*      */       }
/* 1253 */       else if (wNode != null) {
/*      */         WNode wNode3;
/* 1255 */         while ((wNode3 = wNode.cowait) != null) {
/* 1256 */           Thread thread; if (U.compareAndSwapObject(wNode, WCOWAIT, wNode3, wNode3.cowait) && (thread = wNode3.thread) != null)
/*      */           {
/* 1258 */             U.unpark(thread); } 
/*      */         } 
/*      */       } 
/* 1261 */       if (this.whead == wNode) {
/* 1262 */         long l; WNode wNode3; if ((wNode3 = wNode1.prev) != wNode2) {
/* 1263 */           if (wNode3 != null)
/* 1264 */             (wNode2 = wNode3).next = wNode1;  continue;
/*      */         }  int j;
/* 1266 */         if ((j = wNode2.status) == 0) {
/* 1267 */           U.compareAndSwapInt(wNode2, WSTATUS, 0, -1); continue;
/* 1268 */         }  if (j == 1) {
/* 1269 */           WNode wNode4; if ((wNode4 = wNode2.prev) != null) {
/* 1270 */             wNode1.prev = wNode4;
/* 1271 */             wNode4.next = wNode1;
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/* 1276 */         if (paramLong == 0L) {
/* 1277 */           l = 0L;
/* 1278 */         } else if ((l = paramLong - System.nanoTime()) <= 0L) {
/* 1279 */           return cancelWaiter(wNode1, wNode1, false);
/* 1280 */         }  Thread thread = Thread.currentThread();
/* 1281 */         U.putObject(thread, PARKBLOCKER, this);
/* 1282 */         wNode1.thread = thread;
/* 1283 */         if (wNode2.status < 0 && (wNode2 != wNode || (this.state & 0xFFL) == 128L) && this.whead == wNode && wNode1.prev == wNode2)
/*      */         {
/*      */           
/* 1286 */           U.park(false, l); } 
/* 1287 */         wNode1.thread = null;
/* 1288 */         U.putObject(thread, PARKBLOCKER, null);
/* 1289 */         if (paramBoolean && Thread.interrupted()) {
/* 1290 */           return cancelWaiter(wNode1, wNode1, true);
/*      */         }
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
/*      */   private long cancelWaiter(WNode paramWNode1, WNode paramWNode2, boolean paramBoolean) {
/* 1313 */     if (paramWNode1 != null && paramWNode2 != null) {
/*      */       
/* 1315 */       paramWNode1.status = 1;
/*      */       WNode wNode1, wNode2;
/* 1317 */       for (wNode1 = paramWNode2; (wNode2 = wNode1.cowait) != null; ) {
/* 1318 */         if (wNode2.status == 1) {
/* 1319 */           U.compareAndSwapObject(wNode1, WCOWAIT, wNode2, wNode2.cowait);
/* 1320 */           wNode1 = paramWNode2;
/*      */           continue;
/*      */         } 
/* 1323 */         wNode1 = wNode2;
/*      */       } 
/* 1325 */       if (paramWNode2 == paramWNode1) {
/* 1326 */         for (wNode1 = paramWNode2.cowait; wNode1 != null; wNode1 = wNode1.cowait) {
/* 1327 */           Thread thread; if ((thread = wNode1.thread) != null)
/* 1328 */             U.unpark(thread); 
/*      */         } 
/* 1330 */         for (wNode1 = paramWNode1.prev; wNode1 != null; ) {
/*      */           
/* 1332 */           while ((wNode2 = paramWNode1.next) == null || wNode2.status == 1) {
/*      */             
/* 1334 */             WNode wNode4 = null;
/* 1335 */             for (WNode wNode5 = this.wtail; wNode5 != null && wNode5 != paramWNode1; wNode5 = wNode5.prev) {
/* 1336 */               if (wNode5.status != 1)
/* 1337 */                 wNode4 = wNode5; 
/* 1338 */             }  if (wNode2 == wNode4 || U
/* 1339 */               .compareAndSwapObject(paramWNode1, WNEXT, wNode2, wNode2 = wNode4)) {
/*      */               
/* 1341 */               if (wNode2 == null && paramWNode1 == this.wtail)
/* 1342 */                 U.compareAndSwapObject(this, WTAIL, paramWNode1, wNode1); 
/*      */               break;
/*      */             } 
/*      */           } 
/* 1346 */           if (wNode1.next == paramWNode1)
/* 1347 */             U.compareAndSwapObject(wNode1, WNEXT, paramWNode1, wNode2);  Thread thread;
/* 1348 */           if (wNode2 != null && (thread = wNode2.thread) != null) {
/* 1349 */             wNode2.thread = null;
/* 1350 */             U.unpark(thread);
/*      */           }  WNode wNode3;
/* 1352 */           if (wNode1.status != 1 || (wNode3 = wNode1.prev) == null)
/*      */             break; 
/* 1354 */           paramWNode1.prev = wNode3;
/* 1355 */           U.compareAndSwapObject(wNode3, WNEXT, wNode1, wNode2);
/* 1356 */           wNode1 = wNode3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     WNode wNode;
/* 1361 */     while ((wNode = this.whead) != null) {
/*      */       WNode wNode1;
/* 1363 */       if ((wNode1 = wNode.next) == null || wNode1.status == 1)
/* 1364 */         for (WNode wNode2 = this.wtail; wNode2 != null && wNode2 != wNode; wNode2 = wNode2.prev) {
/* 1365 */           if (wNode2.status <= 0)
/* 1366 */             wNode1 = wNode2; 
/*      */         }  
/* 1368 */       if (wNode == this.whead) {
/* 1369 */         long l; if (wNode1 != null && wNode.status == 0 && ((l = this.state) & 0xFFL) != 128L && (l == 0L || wNode1.mode == 0))
/*      */         {
/*      */           
/* 1372 */           release(wNode); } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1376 */     return (paramBoolean || Thread.interrupted()) ? 1L : 0L;
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
/*      */   static {
/*      */     try {
/* 1391 */       U = Unsafe.getUnsafe();
/* 1392 */       Class<StampedLock> clazz = StampedLock.class;
/* 1393 */       Class<WNode> clazz1 = WNode.class;
/*      */       
/* 1395 */       STATE = U.objectFieldOffset(clazz.getDeclaredField("state"));
/*      */       
/* 1397 */       WHEAD = U.objectFieldOffset(clazz.getDeclaredField("whead"));
/*      */       
/* 1399 */       WTAIL = U.objectFieldOffset(clazz.getDeclaredField("wtail"));
/*      */       
/* 1401 */       WSTATUS = U.objectFieldOffset(clazz1.getDeclaredField("status"));
/*      */       
/* 1403 */       WNEXT = U.objectFieldOffset(clazz1.getDeclaredField("next"));
/*      */       
/* 1405 */       WCOWAIT = U.objectFieldOffset(clazz1.getDeclaredField("cowait"));
/* 1406 */       Class<Thread> clazz2 = Thread.class;
/*      */       
/* 1408 */       PARKBLOCKER = U.objectFieldOffset(clazz2.getDeclaredField("parkBlocker"));
/*      */     }
/* 1410 */     catch (Exception exception) {
/* 1411 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/locks/StampedLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */