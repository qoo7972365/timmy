/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.concurrent.locks.LockSupport;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Phaser
/*      */ {
/*      */   private volatile long state;
/*      */   private static final int MAX_PARTIES = 65535;
/*      */   private static final int MAX_PHASE = 2147483647;
/*      */   private static final int PARTIES_SHIFT = 16;
/*      */   private static final int PHASE_SHIFT = 32;
/*      */   private static final int UNARRIVED_MASK = 65535;
/*      */   private static final long PARTIES_MASK = 4294901760L;
/*      */   private static final long COUNTS_MASK = 4294967295L;
/*      */   private static final long TERMINATION_BIT = -9223372036854775808L;
/*      */   private static final int ONE_ARRIVAL = 1;
/*      */   private static final int ONE_PARTY = 65536;
/*      */   private static final int ONE_DEREGISTER = 65537;
/*      */   private static final int EMPTY = 1;
/*      */   private final Phaser parent;
/*      */   private final Phaser root;
/*      */   private final AtomicReference<QNode> evenQ;
/*      */   private final AtomicReference<QNode> oddQ;
/*      */   
/*      */   private static int unarrivedOf(long paramLong) {
/*  315 */     int i = (int)paramLong;
/*  316 */     return (i == 1) ? 0 : (i & 0xFFFF);
/*      */   }
/*      */   
/*      */   private static int partiesOf(long paramLong) {
/*  320 */     return (int)paramLong >>> 16;
/*      */   }
/*      */   
/*      */   private static int phaseOf(long paramLong) {
/*  324 */     return (int)(paramLong >>> 32L);
/*      */   }
/*      */   
/*      */   private static int arrivedOf(long paramLong) {
/*  328 */     int i = (int)paramLong;
/*  329 */     return (i == 1) ? 0 : ((i >>> 16) - (i & 0xFFFF));
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
/*      */ 
/*      */   
/*      */   private AtomicReference<QNode> queueFor(int paramInt) {
/*  353 */     return ((paramInt & 0x1) == 0) ? this.evenQ : this.oddQ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String badArrive(long paramLong) {
/*  360 */     return "Attempted arrival of unregistered party for " + 
/*  361 */       stateToString(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String badRegister(long paramLong) {
/*  368 */     return "Attempt to register more than 65535 parties for " + 
/*  369 */       stateToString(paramLong);
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
/*      */   private int doArrive(int paramInt) {
/*  382 */     Phaser phaser = this.root;
/*      */     while (true) {
/*  384 */       long l = (phaser == this) ? this.state : reconcileState();
/*  385 */       int i = (int)(l >>> 32L);
/*  386 */       if (i < 0)
/*  387 */         return i; 
/*  388 */       int j = (int)l;
/*  389 */       boolean bool = (j == 1) ? false : (j & 0xFFFF);
/*  390 */       if (bool)
/*  391 */         throw new IllegalStateException(badArrive(l)); 
/*  392 */       if (UNSAFE.compareAndSwapLong(this, stateOffset, l, l -= paramInt)) {
/*  393 */         if (bool == true) {
/*  394 */           long l1 = l & 0xFFFF0000L;
/*  395 */           int k = (int)l1 >>> 16;
/*  396 */           if (phaser == this) {
/*  397 */             if (onAdvance(i, k)) {
/*  398 */               l1 |= Long.MIN_VALUE;
/*  399 */             } else if (k == 0) {
/*  400 */               l1 |= 0x1L;
/*      */             } else {
/*  402 */               l1 |= k;
/*  403 */             }  int m = i + 1 & Integer.MAX_VALUE;
/*  404 */             l1 |= m << 32L;
/*  405 */             UNSAFE.compareAndSwapLong(this, stateOffset, l, l1);
/*  406 */             releaseWaiters(i);
/*      */           }
/*  408 */           else if (k == 0) {
/*  409 */             i = this.parent.doArrive(65537);
/*  410 */             UNSAFE.compareAndSwapLong(this, stateOffset, l, l | 0x1L);
/*      */           }
/*      */           else {
/*      */             
/*  414 */             i = this.parent.doArrive(1);
/*      */           } 
/*  416 */         }  return i;
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
/*      */   private int doRegister(int paramInt) {
/*      */     int i;
/*  429 */     long l = paramInt << 16L | paramInt;
/*  430 */     Phaser phaser = this.parent;
/*      */     
/*      */     while (true) {
/*  433 */       long l1 = (phaser == null) ? this.state : reconcileState();
/*  434 */       int j = (int)l1;
/*  435 */       int k = j >>> 16;
/*  436 */       int m = j & 0xFFFF;
/*  437 */       if (paramInt > 65535 - k)
/*  438 */         throw new IllegalStateException(badRegister(l1)); 
/*  439 */       i = (int)(l1 >>> 32L);
/*  440 */       if (i < 0)
/*      */         break; 
/*  442 */       if (j != 1) {
/*  443 */         if (phaser == null || reconcileState() == l1) {
/*  444 */           if (m == 0) {
/*  445 */             this.root.internalAwaitAdvance(i, null); continue;
/*  446 */           }  if (UNSAFE.compareAndSwapLong(this, stateOffset, l1, l1 + l))
/*      */             break; 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  451 */       if (phaser == null) {
/*  452 */         long l2 = i << 32L | l;
/*  453 */         if (UNSAFE.compareAndSwapLong(this, stateOffset, l1, l2))
/*      */           break; 
/*      */         continue;
/*      */       } 
/*  457 */       synchronized (this) {
/*  458 */         if (this.state == l1) {
/*  459 */           i = phaser.doRegister(1);
/*  460 */           if (i < 0) {
/*      */             break;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  466 */           while (!UNSAFE.compareAndSwapLong(this, stateOffset, l1, i << 32L | l)) {
/*      */             
/*  468 */             l1 = this.state;
/*  469 */             i = (int)(this.root.state >>> 32L);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  477 */     return i;
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
/*      */   private long reconcileState() {
/*  490 */     Phaser phaser = this.root;
/*  491 */     long l = this.state;
/*  492 */     if (phaser != this) {
/*      */       int i;
/*      */       
/*  495 */       while ((i = (int)(phaser.state >>> 32L)) != (int)(l >>> 32L)) {
/*      */         int j;
/*      */         
/*  498 */         if (!UNSAFE.compareAndSwapLong(this, stateOffset, l, l = i << 32L | ((i < 0) ? (l & 0xFFFFFFFFL) : (((j = (int)l >>> 16) == 0) ? 1L : (l & 0xFFFF0000L | j)))))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  503 */           l = this.state; } 
/*      */       } 
/*  505 */     }  return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phaser() {
/*  514 */     this(null, 0);
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
/*      */   public Phaser(int paramInt) {
/*  527 */     this(null, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phaser(Phaser paramPhaser) {
/*  536 */     this(paramPhaser, 0);
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
/*      */   public Phaser(Phaser paramPhaser, int paramInt) {
/*  552 */     if (paramInt >>> 16 != 0)
/*  553 */       throw new IllegalArgumentException("Illegal number of parties"); 
/*  554 */     int i = 0;
/*  555 */     this.parent = paramPhaser;
/*  556 */     if (paramPhaser != null) {
/*  557 */       Phaser phaser = paramPhaser.root;
/*  558 */       this.root = phaser;
/*  559 */       this.evenQ = phaser.evenQ;
/*  560 */       this.oddQ = phaser.oddQ;
/*  561 */       if (paramInt != 0) {
/*  562 */         i = paramPhaser.doRegister(1);
/*      */       }
/*      */     } else {
/*  565 */       this.root = this;
/*  566 */       this.evenQ = new AtomicReference<>();
/*  567 */       this.oddQ = new AtomicReference<>();
/*      */     } 
/*  569 */     this.state = (paramInt == 0) ? 1L : (i << 32L | paramInt << 16L | paramInt);
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
/*      */   public int register() {
/*  591 */     return doRegister(1);
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
/*      */   
/*      */   public int bulkRegister(int paramInt) {
/*  614 */     if (paramInt < 0)
/*  615 */       throw new IllegalArgumentException(); 
/*  616 */     if (paramInt == 0)
/*  617 */       return getPhase(); 
/*  618 */     return doRegister(paramInt);
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
/*      */   public int arrive() {
/*  634 */     return doArrive(1);
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
/*      */   public int arriveAndDeregister() {
/*  654 */     return doArrive(65537);
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
/*      */   public int arriveAndAwaitAdvance() {
/*      */     long l1;
/*      */     int i;
/*      */     byte b;
/*  677 */     Phaser phaser = this.root;
/*      */     do {
/*  679 */       l1 = (phaser == this) ? this.state : reconcileState();
/*  680 */       i = (int)(l1 >>> 32L);
/*  681 */       if (i < 0)
/*  682 */         return i; 
/*  683 */       int m = (int)l1;
/*  684 */       b = (m == 1) ? 0 : (m & 0xFFFF);
/*  685 */       if (b)
/*  686 */         throw new IllegalStateException(badArrive(l1)); 
/*  687 */     } while (!UNSAFE.compareAndSwapLong(this, stateOffset, l1, --l1));
/*      */     
/*  689 */     if (b > 1)
/*  690 */       return phaser.internalAwaitAdvance(i, null); 
/*  691 */     if (phaser != this)
/*  692 */       return this.parent.arriveAndAwaitAdvance(); 
/*  693 */     long l2 = l1 & 0xFFFF0000L;
/*  694 */     int j = (int)l2 >>> 16;
/*  695 */     if (onAdvance(i, j)) {
/*  696 */       l2 |= Long.MIN_VALUE;
/*  697 */     } else if (j == 0) {
/*  698 */       l2 |= 0x1L;
/*      */     } else {
/*  700 */       l2 |= j;
/*  701 */     }  int k = i + 1 & Integer.MAX_VALUE;
/*  702 */     l2 |= k << 32L;
/*  703 */     if (!UNSAFE.compareAndSwapLong(this, stateOffset, l1, l2))
/*  704 */       return (int)(this.state >>> 32L); 
/*  705 */     releaseWaiters(i);
/*  706 */     return k;
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
/*      */   public int awaitAdvance(int paramInt) {
/*  724 */     Phaser phaser = this.root;
/*  725 */     long l = (phaser == this) ? this.state : reconcileState();
/*  726 */     int i = (int)(l >>> 32L);
/*  727 */     if (paramInt < 0)
/*  728 */       return paramInt; 
/*  729 */     if (i == paramInt)
/*  730 */       return phaser.internalAwaitAdvance(paramInt, null); 
/*  731 */     return i;
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
/*      */   public int awaitAdvanceInterruptibly(int paramInt) throws InterruptedException {
/*  751 */     Phaser phaser = this.root;
/*  752 */     long l = (phaser == this) ? this.state : reconcileState();
/*  753 */     int i = (int)(l >>> 32L);
/*  754 */     if (paramInt < 0)
/*  755 */       return paramInt; 
/*  756 */     if (i == paramInt) {
/*  757 */       QNode qNode = new QNode(this, paramInt, true, false, 0L);
/*  758 */       i = phaser.internalAwaitAdvance(paramInt, qNode);
/*  759 */       if (qNode.wasInterrupted)
/*  760 */         throw new InterruptedException(); 
/*      */     } 
/*  762 */     return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int awaitAdvanceInterruptibly(int paramInt, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, TimeoutException {
/*  788 */     long l1 = paramTimeUnit.toNanos(paramLong);
/*  789 */     Phaser phaser = this.root;
/*  790 */     long l2 = (phaser == this) ? this.state : reconcileState();
/*  791 */     int i = (int)(l2 >>> 32L);
/*  792 */     if (paramInt < 0)
/*  793 */       return paramInt; 
/*  794 */     if (i == paramInt) {
/*  795 */       QNode qNode = new QNode(this, paramInt, true, true, l1);
/*  796 */       i = phaser.internalAwaitAdvance(paramInt, qNode);
/*  797 */       if (qNode.wasInterrupted)
/*  798 */         throw new InterruptedException(); 
/*  799 */       if (i == paramInt)
/*  800 */         throw new TimeoutException(); 
/*      */     } 
/*  802 */     return i;
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
/*      */   public void forceTermination() {
/*  816 */     Phaser phaser = this.root;
/*      */     long l;
/*  818 */     while ((l = phaser.state) >= 0L) {
/*  819 */       if (UNSAFE.compareAndSwapLong(phaser, stateOffset, l, l | Long.MIN_VALUE)) {
/*      */ 
/*      */         
/*  822 */         releaseWaiters(0);
/*  823 */         releaseWaiters(1);
/*      */         return;
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
/*      */   public final int getPhase() {
/*  839 */     return (int)(this.root.state >>> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRegisteredParties() {
/*  848 */     return partiesOf(this.state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArrivedParties() {
/*  859 */     return arrivedOf(reconcileState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUnarrivedParties() {
/*  870 */     return unarrivedOf(reconcileState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phaser getParent() {
/*  879 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phaser getRoot() {
/*  889 */     return this.root;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTerminated() {
/*  898 */     return (this.root.state < 0L);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean onAdvance(int paramInt1, int paramInt2) {
/*  942 */     return (paramInt2 == 0);
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
/*  955 */     return stateToString(reconcileState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String stateToString(long paramLong) {
/*  962 */     return super.toString() + "[phase = " + 
/*  963 */       phaseOf(paramLong) + " parties = " + 
/*  964 */       partiesOf(paramLong) + " arrived = " + 
/*  965 */       arrivedOf(paramLong) + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void releaseWaiters(int paramInt) {
/*  976 */     AtomicReference<QNode> atomicReference = ((paramInt & 0x1) == 0) ? this.evenQ : this.oddQ; QNode qNode;
/*  977 */     while ((qNode = atomicReference.get()) != null && qNode.phase != (int)(this.root.state >>> 32L)) {
/*      */       Thread thread;
/*  979 */       if (atomicReference.compareAndSet(qNode, qNode.next) && (thread = qNode.thread) != null) {
/*      */         
/*  981 */         qNode.thread = null;
/*  982 */         LockSupport.unpark(thread);
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
/*      */   private int abortWait(int paramInt) {
/*  997 */     AtomicReference<QNode> atomicReference = ((paramInt & 0x1) == 0) ? this.evenQ : this.oddQ;
/*      */     
/*      */     while (true) {
/* 1000 */       QNode qNode = atomicReference.get();
/* 1001 */       int i = (int)(this.root.state >>> 32L); Thread thread;
/* 1002 */       if (qNode == null || ((thread = qNode.thread) != null && qNode.phase == i))
/* 1003 */         return i; 
/* 1004 */       if (atomicReference.compareAndSet(qNode, qNode.next) && thread != null) {
/* 1005 */         qNode.thread = null;
/* 1006 */         LockSupport.unpark(thread);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1012 */   private static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1025 */   static final int SPINS_PER_ARRIVAL = (NCPU < 2) ? 1 : 256;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long stateOffset;
/*      */ 
/*      */ 
/*      */   
/*      */   private int internalAwaitAdvance(int paramInt, QNode paramQNode) {
/* 1038 */     releaseWaiters(paramInt - 1);
/* 1039 */     boolean bool = false;
/* 1040 */     int i = 0;
/* 1041 */     int j = SPINS_PER_ARRIVAL;
/*      */     long l;
/*      */     int k;
/* 1044 */     while ((k = (int)((l = this.state) >>> 32L)) == paramInt) {
/* 1045 */       if (paramQNode == null) {
/* 1046 */         int m = (int)l & 0xFFFF;
/* 1047 */         if (m != i && (i = m) < NCPU)
/*      */         {
/* 1049 */           j += SPINS_PER_ARRIVAL; } 
/* 1050 */         boolean bool1 = Thread.interrupted();
/* 1051 */         if (bool1 || --j < 0) {
/* 1052 */           paramQNode = new QNode(this, paramInt, false, false, 0L);
/* 1053 */           paramQNode.wasInterrupted = bool1;
/*      */         }  continue;
/*      */       } 
/* 1056 */       if (paramQNode.isReleasable())
/*      */         break; 
/* 1058 */       if (!bool) {
/* 1059 */         AtomicReference<QNode> atomicReference = ((paramInt & 0x1) == 0) ? this.evenQ : this.oddQ;
/* 1060 */         QNode qNode = paramQNode.next = atomicReference.get();
/* 1061 */         if ((qNode == null || qNode.phase == paramInt) && (int)(this.state >>> 32L) == paramInt)
/*      */         {
/* 1063 */           bool = atomicReference.compareAndSet(qNode, paramQNode); } 
/*      */         continue;
/*      */       } 
/*      */       try {
/* 1067 */         ForkJoinPool.managedBlock(paramQNode);
/* 1068 */       } catch (InterruptedException interruptedException) {
/* 1069 */         paramQNode.wasInterrupted = true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1074 */     if (paramQNode != null) {
/* 1075 */       if (paramQNode.thread != null)
/* 1076 */         paramQNode.thread = null; 
/* 1077 */       if (paramQNode.wasInterrupted && !paramQNode.interruptible)
/* 1078 */         Thread.currentThread().interrupt(); 
/* 1079 */       if (k == paramInt && (k = (int)(this.state >>> 32L)) == paramInt)
/* 1080 */         return abortWait(paramInt); 
/*      */     } 
/* 1082 */     releaseWaiters(paramInt);
/* 1083 */     return k;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class QNode
/*      */     implements ForkJoinPool.ManagedBlocker
/*      */   {
/*      */     final Phaser phaser;
/*      */     
/*      */     final int phase;
/*      */     final boolean interruptible;
/*      */     final boolean timed;
/*      */     boolean wasInterrupted;
/*      */     long nanos;
/*      */     final long deadline;
/*      */     volatile Thread thread;
/*      */     QNode next;
/*      */     
/*      */     QNode(Phaser param1Phaser, int param1Int, boolean param1Boolean1, boolean param1Boolean2, long param1Long) {
/* 1102 */       this.phaser = param1Phaser;
/* 1103 */       this.phase = param1Int;
/* 1104 */       this.interruptible = param1Boolean1;
/* 1105 */       this.nanos = param1Long;
/* 1106 */       this.timed = param1Boolean2;
/* 1107 */       this.deadline = param1Boolean2 ? (System.nanoTime() + param1Long) : 0L;
/* 1108 */       this.thread = Thread.currentThread();
/*      */     }
/*      */     
/*      */     public boolean isReleasable() {
/* 1112 */       if (this.thread == null)
/* 1113 */         return true; 
/* 1114 */       if (this.phaser.getPhase() != this.phase) {
/* 1115 */         this.thread = null;
/* 1116 */         return true;
/*      */       } 
/* 1118 */       if (Thread.interrupted())
/* 1119 */         this.wasInterrupted = true; 
/* 1120 */       if (this.wasInterrupted && this.interruptible) {
/* 1121 */         this.thread = null;
/* 1122 */         return true;
/*      */       } 
/* 1124 */       if (this.timed) {
/* 1125 */         if (this.nanos > 0L) {
/* 1126 */           this.nanos = this.deadline - System.nanoTime();
/*      */         }
/* 1128 */         if (this.nanos <= 0L) {
/* 1129 */           this.thread = null;
/* 1130 */           return true;
/*      */         } 
/*      */       } 
/* 1133 */       return false;
/*      */     }
/*      */     
/*      */     public boolean block() {
/* 1137 */       if (isReleasable())
/* 1138 */         return true; 
/* 1139 */       if (!this.timed) {
/* 1140 */         LockSupport.park(this);
/* 1141 */       } else if (this.nanos > 0L) {
/* 1142 */         LockSupport.parkNanos(this, this.nanos);
/* 1143 */       }  return isReleasable();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1153 */       UNSAFE = Unsafe.getUnsafe();
/* 1154 */       Class<Phaser> clazz = Phaser.class;
/*      */       
/* 1156 */       stateOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("state"));
/* 1157 */     } catch (Exception exception) {
/* 1158 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/Phaser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */