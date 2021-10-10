/*      */ package java.util.concurrent.locks;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
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
/*      */ public abstract class AbstractQueuedLongSynchronizer
/*      */   extends AbstractOwnableSynchronizer
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7373984972572414692L;
/*      */   private volatile transient Node head;
/*      */   private volatile transient Node tail;
/*      */   private volatile long state;
/*      */   static final long spinForTimeoutThreshold = 1000L;
/*      */   
/*      */   static final class Node
/*      */   {
/*  160 */     static final Node SHARED = new Node();
/*      */     
/*  162 */     static final Node EXCLUSIVE = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int CANCELLED = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int SIGNAL = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int CONDITION = -2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int PROPAGATE = -3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile int waitStatus;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile Node prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile Node next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile Thread thread;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Node nextWaiter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean isShared() {
/*  262 */       return (this.nextWaiter == SHARED);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Node predecessor() throws NullPointerException {
/*  273 */       Node node = this.prev;
/*  274 */       if (node == null) {
/*  275 */         throw new NullPointerException();
/*      */       }
/*  277 */       return node;
/*      */     }
/*      */ 
/*      */     
/*      */     Node() {}
/*      */     
/*      */     Node(Thread param1Thread, Node param1Node) {
/*  284 */       this.nextWaiter = param1Node;
/*  285 */       this.thread = param1Thread;
/*      */     }
/*      */     
/*      */     Node(Thread param1Thread, int param1Int) {
/*  289 */       this.waitStatus = param1Int;
/*  290 */       this.thread = param1Thread;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final long getState() {
/*  319 */     return this.state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setState(long paramLong) {
/*  328 */     this.state = paramLong;
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
/*      */   protected final boolean compareAndSetState(long paramLong1, long paramLong2) {
/*  344 */     return unsafe.compareAndSwapLong(this, stateOffset, paramLong1, paramLong2);
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
/*      */   private Node enq(Node paramNode) {
/*      */     while (true) {
/*  363 */       Node node = this.tail;
/*  364 */       if (node == null) {
/*  365 */         if (compareAndSetHead(new Node()))
/*  366 */           this.tail = this.head;  continue;
/*      */       } 
/*  368 */       paramNode.prev = node;
/*  369 */       if (compareAndSetTail(node, paramNode)) {
/*  370 */         node.next = paramNode;
/*  371 */         return node;
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
/*      */   private Node addWaiter(Node paramNode) {
/*  384 */     Node node1 = new Node(Thread.currentThread(), paramNode);
/*      */     
/*  386 */     Node node2 = this.tail;
/*  387 */     if (node2 != null) {
/*  388 */       node1.prev = node2;
/*  389 */       if (compareAndSetTail(node2, node1)) {
/*  390 */         node2.next = node1;
/*  391 */         return node1;
/*      */       } 
/*      */     } 
/*  394 */     enq(node1);
/*  395 */     return node1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHead(Node paramNode) {
/*  406 */     this.head = paramNode;
/*  407 */     paramNode.thread = null;
/*  408 */     paramNode.prev = null;
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
/*      */   private void unparkSuccessor(Node paramNode) {
/*  422 */     int i = paramNode.waitStatus;
/*  423 */     if (i < 0) {
/*  424 */       compareAndSetWaitStatus(paramNode, i, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  432 */     Node node = paramNode.next;
/*  433 */     if (node == null || node.waitStatus > 0) {
/*  434 */       node = null;
/*  435 */       for (Node node1 = this.tail; node1 != null && node1 != paramNode; node1 = node1.prev) {
/*  436 */         if (node1.waitStatus <= 0)
/*  437 */           node = node1; 
/*      */       } 
/*  439 */     }  if (node != null) {
/*  440 */       LockSupport.unpark(node.thread);
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
/*      */   private void doReleaseShared() {
/*      */     while (true) {
/*  461 */       Node node = this.head;
/*  462 */       if (node != null && node != this.tail) {
/*  463 */         int i = node.waitStatus;
/*  464 */         if (i == -1) {
/*  465 */           if (!compareAndSetWaitStatus(node, -1, 0))
/*      */             continue; 
/*  467 */           unparkSuccessor(node);
/*      */         }
/*  469 */         else if (i == 0 && 
/*  470 */           !compareAndSetWaitStatus(node, 0, -3)) {
/*      */           continue;
/*      */         } 
/*  473 */       }  if (node == this.head) {
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
/*      */   private void setHeadAndPropagate(Node paramNode, long paramLong) {
/*  487 */     Node node = this.head;
/*  488 */     setHead(paramNode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  505 */     if (paramLong > 0L || node == null || node.waitStatus < 0 || (node = this.head) == null || node.waitStatus < 0) {
/*      */       
/*  507 */       Node node1 = paramNode.next;
/*  508 */       if (node1 == null || node1.isShared()) {
/*  509 */         doReleaseShared();
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
/*      */   private void cancelAcquire(Node paramNode) {
/*  522 */     if (paramNode == null) {
/*      */       return;
/*      */     }
/*  525 */     paramNode.thread = null;
/*      */ 
/*      */     
/*  528 */     Node node1 = paramNode.prev;
/*  529 */     while (node1.waitStatus > 0) {
/*  530 */       paramNode.prev = node1 = node1.prev;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  535 */     Node node2 = node1.next;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  540 */     paramNode.waitStatus = 1;
/*      */ 
/*      */     
/*  543 */     if (paramNode == this.tail && compareAndSetTail(paramNode, node1)) {
/*  544 */       compareAndSetNext(node1, node2, null);
/*      */     } else {
/*      */       int i;
/*      */ 
/*      */       
/*  549 */       if (node1 != this.head && ((i = node1.waitStatus) == -1 || (i <= 0 && 
/*      */         
/*  551 */         compareAndSetWaitStatus(node1, i, -1))) && node1.thread != null) {
/*      */         
/*  553 */         Node node = paramNode.next;
/*  554 */         if (node != null && node.waitStatus <= 0)
/*  555 */           compareAndSetNext(node1, node2, node); 
/*      */       } else {
/*  557 */         unparkSuccessor(paramNode);
/*      */       } 
/*      */       
/*  560 */       paramNode.next = paramNode;
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
/*      */   private static boolean shouldParkAfterFailedAcquire(Node paramNode1, Node paramNode2) {
/*  574 */     int i = paramNode1.waitStatus;
/*  575 */     if (i == -1)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  580 */       return true; } 
/*  581 */     if (i > 0)
/*      */     {
/*      */       while (true)
/*      */       
/*      */       { 
/*      */         
/*  587 */         paramNode2.prev = paramNode1 = paramNode1.prev;
/*  588 */         if (paramNode1.waitStatus <= 0)
/*  589 */         { paramNode1.next = paramNode2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  598 */           return false; }  }  }  compareAndSetWaitStatus(paramNode1, i, -1); return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void selfInterrupt() {
/*  605 */     Thread.currentThread().interrupt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean parkAndCheckInterrupt() {
/*  614 */     LockSupport.park(this);
/*  615 */     return Thread.interrupted();
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
/*      */   final boolean acquireQueued(Node paramNode, long paramLong) {
/*  636 */     boolean bool = true;
/*      */     try {
/*  638 */       boolean bool1 = false;
/*      */       while (true) {
/*  640 */         Node node = paramNode.predecessor();
/*  641 */         if (node == this.head && tryAcquire(paramLong)) {
/*  642 */           setHead(paramNode);
/*  643 */           node.next = null;
/*  644 */           bool = false;
/*  645 */           return bool1;
/*      */         } 
/*  647 */         if (shouldParkAfterFailedAcquire(node, paramNode) && 
/*  648 */           parkAndCheckInterrupt())
/*  649 */           bool1 = true; 
/*      */       } 
/*      */     } finally {
/*  652 */       if (bool) {
/*  653 */         cancelAcquire(paramNode);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doAcquireInterruptibly(long paramLong) throws InterruptedException {
/*  663 */     Node node = addWaiter(Node.EXCLUSIVE);
/*  664 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/*  667 */         Node node1 = node.predecessor();
/*  668 */         if (node1 == this.head && tryAcquire(paramLong)) {
/*  669 */           setHead(node);
/*  670 */           node1.next = null;
/*  671 */           bool = false;
/*      */           return;
/*      */         } 
/*  674 */         if (shouldParkAfterFailedAcquire(node1, node) && 
/*  675 */           parkAndCheckInterrupt())
/*  676 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/*  679 */       if (bool) {
/*  680 */         cancelAcquire(node);
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
/*      */   private boolean doAcquireNanos(long paramLong1, long paramLong2) throws InterruptedException {
/*  693 */     if (paramLong2 <= 0L)
/*  694 */       return false; 
/*  695 */     long l = System.nanoTime() + paramLong2;
/*  696 */     Node node = addWaiter(Node.EXCLUSIVE);
/*  697 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/*  700 */         Node node1 = node.predecessor();
/*  701 */         if (node1 == this.head && tryAcquire(paramLong1)) {
/*  702 */           setHead(node);
/*  703 */           node1.next = null;
/*  704 */           bool = false;
/*  705 */           return true;
/*      */         } 
/*  707 */         paramLong2 = l - System.nanoTime();
/*  708 */         if (paramLong2 <= 0L)
/*  709 */           return false; 
/*  710 */         if (shouldParkAfterFailedAcquire(node1, node) && paramLong2 > 1000L)
/*      */         {
/*  712 */           LockSupport.parkNanos(this, paramLong2); } 
/*  713 */         if (Thread.interrupted())
/*  714 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/*  717 */       if (bool) {
/*  718 */         cancelAcquire(node);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doAcquireShared(long paramLong) {
/*  727 */     Node node = addWaiter(Node.SHARED);
/*  728 */     boolean bool = true;
/*      */     try {
/*  730 */       boolean bool1 = false;
/*      */       while (true) {
/*  732 */         Node node1 = node.predecessor();
/*  733 */         if (node1 == this.head) {
/*  734 */           long l = tryAcquireShared(paramLong);
/*  735 */           if (l >= 0L) {
/*  736 */             setHeadAndPropagate(node, l);
/*  737 */             node1.next = null;
/*  738 */             if (bool1)
/*  739 */               selfInterrupt(); 
/*  740 */             bool = false;
/*      */             return;
/*      */           } 
/*      */         } 
/*  744 */         if (shouldParkAfterFailedAcquire(node1, node) && 
/*  745 */           parkAndCheckInterrupt())
/*  746 */           bool1 = true; 
/*      */       } 
/*      */     } finally {
/*  749 */       if (bool) {
/*  750 */         cancelAcquire(node);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doAcquireSharedInterruptibly(long paramLong) throws InterruptedException {
/*  760 */     Node node = addWaiter(Node.SHARED);
/*  761 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/*  764 */         Node node1 = node.predecessor();
/*  765 */         if (node1 == this.head) {
/*  766 */           long l = tryAcquireShared(paramLong);
/*  767 */           if (l >= 0L) {
/*  768 */             setHeadAndPropagate(node, l);
/*  769 */             node1.next = null;
/*  770 */             bool = false;
/*      */             return;
/*      */           } 
/*      */         } 
/*  774 */         if (shouldParkAfterFailedAcquire(node1, node) && 
/*  775 */           parkAndCheckInterrupt())
/*  776 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/*  779 */       if (bool) {
/*  780 */         cancelAcquire(node);
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
/*      */   private boolean doAcquireSharedNanos(long paramLong1, long paramLong2) throws InterruptedException {
/*  793 */     if (paramLong2 <= 0L)
/*  794 */       return false; 
/*  795 */     long l = System.nanoTime() + paramLong2;
/*  796 */     Node node = addWaiter(Node.SHARED);
/*  797 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/*  800 */         Node node1 = node.predecessor();
/*  801 */         if (node1 == this.head) {
/*  802 */           long l1 = tryAcquireShared(paramLong1);
/*  803 */           if (l1 >= 0L) {
/*  804 */             setHeadAndPropagate(node, l1);
/*  805 */             node1.next = null;
/*  806 */             bool = false;
/*  807 */             return true;
/*      */           } 
/*      */         } 
/*  810 */         paramLong2 = l - System.nanoTime();
/*  811 */         if (paramLong2 <= 0L)
/*  812 */           return false; 
/*  813 */         if (shouldParkAfterFailedAcquire(node1, node) && paramLong2 > 1000L)
/*      */         {
/*  815 */           LockSupport.parkNanos(this, paramLong2); } 
/*  816 */         if (Thread.interrupted())
/*  817 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/*  820 */       if (bool) {
/*  821 */         cancelAcquire(node);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean tryAcquire(long paramLong) {
/*  854 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryRelease(long paramLong) {
/*  880 */     throw new UnsupportedOperationException();
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
/*      */   protected long tryAcquireShared(long paramLong) {
/*  916 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryReleaseShared(long paramLong) {
/*  941 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean isHeldExclusively() {
/*  960 */     throw new UnsupportedOperationException();
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
/*      */   public final void acquire(long paramLong) {
/*  976 */     if (!tryAcquire(paramLong) && 
/*  977 */       acquireQueued(addWaiter(Node.EXCLUSIVE), paramLong)) {
/*  978 */       selfInterrupt();
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
/*      */   public final void acquireInterruptibly(long paramLong) throws InterruptedException {
/*  997 */     if (Thread.interrupted())
/*  998 */       throw new InterruptedException(); 
/*  999 */     if (!tryAcquire(paramLong)) {
/* 1000 */       doAcquireInterruptibly(paramLong);
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
/*      */   public final boolean tryAcquireNanos(long paramLong1, long paramLong2) throws InterruptedException {
/* 1022 */     if (Thread.interrupted())
/* 1023 */       throw new InterruptedException(); 
/* 1024 */     return (tryAcquire(paramLong1) || 
/* 1025 */       doAcquireNanos(paramLong1, paramLong2));
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
/*      */   public final boolean release(long paramLong) {
/* 1039 */     if (tryRelease(paramLong)) {
/* 1040 */       Node node = this.head;
/* 1041 */       if (node != null && node.waitStatus != 0)
/* 1042 */         unparkSuccessor(node); 
/* 1043 */       return true;
/*      */     } 
/* 1045 */     return false;
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
/*      */   public final void acquireShared(long paramLong) {
/* 1060 */     if (tryAcquireShared(paramLong) < 0L) {
/* 1061 */       doAcquireShared(paramLong);
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
/*      */   public final void acquireSharedInterruptibly(long paramLong) throws InterruptedException {
/* 1079 */     if (Thread.interrupted())
/* 1080 */       throw new InterruptedException(); 
/* 1081 */     if (tryAcquireShared(paramLong) < 0L) {
/* 1082 */       doAcquireSharedInterruptibly(paramLong);
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
/*      */   public final boolean tryAcquireSharedNanos(long paramLong1, long paramLong2) throws InterruptedException {
/* 1103 */     if (Thread.interrupted())
/* 1104 */       throw new InterruptedException(); 
/* 1105 */     return (tryAcquireShared(paramLong1) >= 0L || 
/* 1106 */       doAcquireSharedNanos(paramLong1, paramLong2));
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
/*      */   public final boolean releaseShared(long paramLong) {
/* 1119 */     if (tryReleaseShared(paramLong)) {
/* 1120 */       doReleaseShared();
/* 1121 */       return true;
/*      */     } 
/* 1123 */     return false;
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
/*      */   public final boolean hasQueuedThreads() {
/* 1140 */     return (this.head != this.tail);
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
/*      */   public final boolean hasContended() {
/* 1153 */     return (this.head != null);
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
/*      */   public final Thread getFirstQueuedThread() {
/* 1169 */     return (this.head == this.tail) ? null : fullGetFirstQueuedThread();
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
/*      */   private Thread fullGetFirstQueuedThread() {
/*      */     Node node1, node2;
/*      */     Thread thread1;
/* 1186 */     if (((node1 = this.head) != null && (node2 = node1.next) != null && node2.prev == this.head && (thread1 = node2.thread) != null) || ((node1 = this.head) != null && (node2 = node1.next) != null && node2.prev == this.head && (thread1 = node2.thread) != null))
/*      */     {
/*      */ 
/*      */       
/* 1190 */       return thread1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1200 */     Node node3 = this.tail;
/* 1201 */     Thread thread2 = null;
/* 1202 */     while (node3 != null && node3 != this.head) {
/* 1203 */       Thread thread = node3.thread;
/* 1204 */       if (thread != null)
/* 1205 */         thread2 = thread; 
/* 1206 */       node3 = node3.prev;
/*      */     } 
/* 1208 */     return thread2;
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
/*      */   public final boolean isQueued(Thread paramThread) {
/* 1222 */     if (paramThread == null)
/* 1223 */       throw new NullPointerException(); 
/* 1224 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1225 */       if (node.thread == paramThread)
/* 1226 */         return true; 
/* 1227 */     }  return false;
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
/*      */   final boolean apparentlyFirstQueuedIsExclusive() {
/*      */     Node node1;
/*      */     Node node2;
/* 1241 */     return ((node1 = this.head) != null && (node2 = node1.next) != null && 
/*      */       
/* 1243 */       !node2.isShared() && node2.thread != null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean hasQueuedPredecessors() {
/* 1294 */     Node node1 = this.tail;
/* 1295 */     Node node2 = this.head;
/*      */     Node node3;
/* 1297 */     return (node2 != node1 && ((node3 = node2.next) == null || node3.thread != 
/* 1298 */       Thread.currentThread()));
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
/*      */   public final int getQueueLength() {
/* 1315 */     byte b = 0;
/* 1316 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1317 */       if (node.thread != null)
/* 1318 */         b++; 
/*      */     } 
/* 1320 */     return b;
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
/*      */   public final Collection<Thread> getQueuedThreads() {
/* 1335 */     ArrayList<Thread> arrayList = new ArrayList();
/* 1336 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1337 */       Thread thread = node.thread;
/* 1338 */       if (thread != null)
/* 1339 */         arrayList.add(thread); 
/*      */     } 
/* 1341 */     return arrayList;
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
/*      */   public final Collection<Thread> getExclusiveQueuedThreads() {
/* 1353 */     ArrayList<Thread> arrayList = new ArrayList();
/* 1354 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1355 */       if (!node.isShared()) {
/* 1356 */         Thread thread = node.thread;
/* 1357 */         if (thread != null)
/* 1358 */           arrayList.add(thread); 
/*      */       } 
/*      */     } 
/* 1361 */     return arrayList;
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
/*      */   public final Collection<Thread> getSharedQueuedThreads() {
/* 1373 */     ArrayList<Thread> arrayList = new ArrayList();
/* 1374 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1375 */       if (node.isShared()) {
/* 1376 */         Thread thread = node.thread;
/* 1377 */         if (thread != null)
/* 1378 */           arrayList.add(thread); 
/*      */       } 
/*      */     } 
/* 1381 */     return arrayList;
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
/* 1394 */     long l = getState();
/* 1395 */     String str = hasQueuedThreads() ? "non" : "";
/* 1396 */     return super.toString() + "[State = " + l + ", " + str + "empty queue]";
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
/*      */   final boolean isOnSyncQueue(Node paramNode) {
/* 1410 */     if (paramNode.waitStatus == -2 || paramNode.prev == null)
/* 1411 */       return false; 
/* 1412 */     if (paramNode.next != null) {
/* 1413 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1422 */     return findNodeFromTail(paramNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findNodeFromTail(Node paramNode) {
/* 1431 */     Node node = this.tail;
/*      */     while (true) {
/* 1433 */       if (node == paramNode)
/* 1434 */         return true; 
/* 1435 */       if (node == null)
/* 1436 */         return false; 
/* 1437 */       node = node.prev;
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
/*      */   final boolean transferForSignal(Node paramNode) {
/* 1452 */     if (!compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1453 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1461 */     Node node = enq(paramNode);
/* 1462 */     int i = node.waitStatus;
/* 1463 */     if (i > 0 || !compareAndSetWaitStatus(node, i, -1))
/* 1464 */       LockSupport.unpark(paramNode.thread); 
/* 1465 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean transferAfterCancelledWait(Node paramNode) {
/* 1476 */     if (compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1477 */       enq(paramNode);
/* 1478 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1486 */     while (!isOnSyncQueue(paramNode))
/* 1487 */       Thread.yield(); 
/* 1488 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final long fullyRelease(Node paramNode) {
/* 1498 */     boolean bool = true;
/*      */     try {
/* 1500 */       long l = getState();
/* 1501 */       if (release(l)) {
/* 1502 */         bool = false;
/* 1503 */         return l;
/*      */       } 
/* 1505 */       throw new IllegalMonitorStateException();
/*      */     } finally {
/*      */       
/* 1508 */       if (bool) {
/* 1509 */         paramNode.waitStatus = 1;
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
/*      */   public final boolean owns(ConditionObject paramConditionObject) {
/* 1524 */     return paramConditionObject.isOwnedBy(this);
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
/*      */   public final boolean hasWaiters(ConditionObject paramConditionObject) {
/* 1544 */     if (!owns(paramConditionObject))
/* 1545 */       throw new IllegalArgumentException("Not owner"); 
/* 1546 */     return paramConditionObject.hasWaiters();
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
/*      */   public final int getWaitQueueLength(ConditionObject paramConditionObject) {
/* 1566 */     if (!owns(paramConditionObject))
/* 1567 */       throw new IllegalArgumentException("Not owner"); 
/* 1568 */     return paramConditionObject.getWaitQueueLength();
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
/*      */   public final Collection<Thread> getWaitingThreads(ConditionObject paramConditionObject) {
/* 1588 */     if (!owns(paramConditionObject))
/* 1589 */       throw new IllegalArgumentException("Not owner"); 
/* 1590 */     return paramConditionObject.getWaitingThreads();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class ConditionObject
/*      */     implements Condition, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1173984872572414699L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private transient AbstractQueuedLongSynchronizer.Node firstWaiter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private transient AbstractQueuedLongSynchronizer.Node lastWaiter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int REINTERRUPT = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int THROW_IE = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AbstractQueuedLongSynchronizer.Node addConditionWaiter() {
/* 1629 */       AbstractQueuedLongSynchronizer.Node node1 = this.lastWaiter;
/*      */       
/* 1631 */       if (node1 != null && node1.waitStatus != -2) {
/* 1632 */         unlinkCancelledWaiters();
/* 1633 */         node1 = this.lastWaiter;
/*      */       } 
/* 1635 */       AbstractQueuedLongSynchronizer.Node node2 = new AbstractQueuedLongSynchronizer.Node(Thread.currentThread(), -2);
/* 1636 */       if (node1 == null) {
/* 1637 */         this.firstWaiter = node2;
/*      */       } else {
/* 1639 */         node1.nextWaiter = node2;
/* 1640 */       }  this.lastWaiter = node2;
/* 1641 */       return node2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doSignal(AbstractQueuedLongSynchronizer.Node param1Node) {
/*      */       do {
/* 1652 */         if ((this.firstWaiter = param1Node.nextWaiter) == null)
/* 1653 */           this.lastWaiter = null; 
/* 1654 */         param1Node.nextWaiter = null;
/* 1655 */       } while (!AbstractQueuedLongSynchronizer.this.transferForSignal(param1Node) && (param1Node = this.firstWaiter) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doSignalAll(AbstractQueuedLongSynchronizer.Node param1Node) {
/* 1664 */       this.lastWaiter = this.firstWaiter = null;
/*      */       do {
/* 1666 */         AbstractQueuedLongSynchronizer.Node node = param1Node.nextWaiter;
/* 1667 */         param1Node.nextWaiter = null;
/* 1668 */         AbstractQueuedLongSynchronizer.this.transferForSignal(param1Node);
/* 1669 */         param1Node = node;
/* 1670 */       } while (param1Node != null);
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
/*      */     private void unlinkCancelledWaiters() {
/* 1688 */       AbstractQueuedLongSynchronizer.Node node1 = this.firstWaiter;
/* 1689 */       AbstractQueuedLongSynchronizer.Node node2 = null;
/* 1690 */       while (node1 != null) {
/* 1691 */         AbstractQueuedLongSynchronizer.Node node = node1.nextWaiter;
/* 1692 */         if (node1.waitStatus != -2) {
/* 1693 */           node1.nextWaiter = null;
/* 1694 */           if (node2 == null) {
/* 1695 */             this.firstWaiter = node;
/*      */           } else {
/* 1697 */             node2.nextWaiter = node;
/* 1698 */           }  if (node == null) {
/* 1699 */             this.lastWaiter = node2;
/*      */           }
/*      */         } else {
/* 1702 */           node2 = node1;
/* 1703 */         }  node1 = node;
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
/*      */     public final void signal() {
/* 1718 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1719 */         throw new IllegalMonitorStateException(); 
/* 1720 */       AbstractQueuedLongSynchronizer.Node node = this.firstWaiter;
/* 1721 */       if (node != null) {
/* 1722 */         doSignal(node);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void signalAll() {
/* 1733 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1734 */         throw new IllegalMonitorStateException(); 
/* 1735 */       AbstractQueuedLongSynchronizer.Node node = this.firstWaiter;
/* 1736 */       if (node != null) {
/* 1737 */         doSignalAll(node);
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
/*      */     public final void awaitUninterruptibly() {
/* 1752 */       AbstractQueuedLongSynchronizer.Node node = addConditionWaiter();
/* 1753 */       long l = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
/* 1754 */       boolean bool = false;
/* 1755 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node)) {
/* 1756 */         LockSupport.park(this);
/* 1757 */         if (Thread.interrupted())
/* 1758 */           bool = true; 
/*      */       } 
/* 1760 */       if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l) || bool) {
/* 1761 */         AbstractQueuedLongSynchronizer.selfInterrupt();
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
/*      */     private int checkInterruptWhileWaiting(AbstractQueuedLongSynchronizer.Node param1Node) {
/* 1782 */       return Thread.interrupted() ? (
/* 1783 */         AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(param1Node) ? -1 : 1) : 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void reportInterruptAfterWait(int param1Int) throws InterruptedException {
/* 1793 */       if (param1Int == -1)
/* 1794 */         throw new InterruptedException(); 
/* 1795 */       if (param1Int == 1) {
/* 1796 */         AbstractQueuedLongSynchronizer.selfInterrupt();
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
/*      */     public final void await() throws InterruptedException {
/* 1813 */       if (Thread.interrupted())
/* 1814 */         throw new InterruptedException(); 
/* 1815 */       AbstractQueuedLongSynchronizer.Node node = addConditionWaiter();
/* 1816 */       long l = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
/* 1817 */       int i = 0;
/*      */       do {
/* 1819 */         LockSupport.park(this);
/* 1820 */       } while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node) && (i = checkInterruptWhileWaiting(node)) == 0);
/*      */ 
/*      */       
/* 1823 */       if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l) && i != -1)
/* 1824 */         i = 1; 
/* 1825 */       if (node.nextWaiter != null)
/* 1826 */         unlinkCancelledWaiters(); 
/* 1827 */       if (i != 0) {
/* 1828 */         reportInterruptAfterWait(i);
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
/*      */     public final long awaitNanos(long param1Long) throws InterruptedException {
/* 1846 */       if (Thread.interrupted())
/* 1847 */         throw new InterruptedException(); 
/* 1848 */       AbstractQueuedLongSynchronizer.Node node = addConditionWaiter();
/* 1849 */       long l1 = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
/* 1850 */       long l2 = System.nanoTime() + param1Long;
/* 1851 */       int i = 0;
/* 1852 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node)) {
/* 1853 */         if (param1Long <= 0L) {
/* 1854 */           AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(node);
/*      */           break;
/*      */         } 
/* 1857 */         if (param1Long >= 1000L)
/* 1858 */           LockSupport.parkNanos(this, param1Long); 
/* 1859 */         if ((i = checkInterruptWhileWaiting(node)) != 0)
/*      */           break; 
/* 1861 */         param1Long = l2 - System.nanoTime();
/*      */       } 
/* 1863 */       if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l1) && i != -1)
/* 1864 */         i = 1; 
/* 1865 */       if (node.nextWaiter != null)
/* 1866 */         unlinkCancelledWaiters(); 
/* 1867 */       if (i != 0)
/* 1868 */         reportInterruptAfterWait(i); 
/* 1869 */       return l2 - System.nanoTime();
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
/*      */     public final boolean awaitUntil(Date param1Date) throws InterruptedException {
/* 1888 */       long l1 = param1Date.getTime();
/* 1889 */       if (Thread.interrupted())
/* 1890 */         throw new InterruptedException(); 
/* 1891 */       AbstractQueuedLongSynchronizer.Node node = addConditionWaiter();
/* 1892 */       long l2 = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
/* 1893 */       boolean bool = false;
/* 1894 */       int i = 0;
/* 1895 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node)) {
/* 1896 */         if (System.currentTimeMillis() > l1) {
/* 1897 */           bool = AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(node);
/*      */           break;
/*      */         } 
/* 1900 */         LockSupport.parkUntil(this, l1);
/* 1901 */         if ((i = checkInterruptWhileWaiting(node)) != 0)
/*      */           break; 
/*      */       } 
/* 1904 */       if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l2) && i != -1)
/* 1905 */         i = 1; 
/* 1906 */       if (node.nextWaiter != null)
/* 1907 */         unlinkCancelledWaiters(); 
/* 1908 */       if (i != 0)
/* 1909 */         reportInterruptAfterWait(i); 
/* 1910 */       return !bool;
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
/*      */     public final boolean await(long param1Long, TimeUnit param1TimeUnit) throws InterruptedException {
/* 1929 */       long l1 = param1TimeUnit.toNanos(param1Long);
/* 1930 */       if (Thread.interrupted())
/* 1931 */         throw new InterruptedException(); 
/* 1932 */       AbstractQueuedLongSynchronizer.Node node = addConditionWaiter();
/* 1933 */       long l2 = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
/* 1934 */       long l3 = System.nanoTime() + l1;
/* 1935 */       boolean bool = false;
/* 1936 */       int i = 0;
/* 1937 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node)) {
/* 1938 */         if (l1 <= 0L) {
/* 1939 */           bool = AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(node);
/*      */           break;
/*      */         } 
/* 1942 */         if (l1 >= 1000L)
/* 1943 */           LockSupport.parkNanos(this, l1); 
/* 1944 */         if ((i = checkInterruptWhileWaiting(node)) != 0)
/*      */           break; 
/* 1946 */         l1 = l3 - System.nanoTime();
/*      */       } 
/* 1948 */       if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l2) && i != -1)
/* 1949 */         i = 1; 
/* 1950 */       if (node.nextWaiter != null)
/* 1951 */         unlinkCancelledWaiters(); 
/* 1952 */       if (i != 0)
/* 1953 */         reportInterruptAfterWait(i); 
/* 1954 */       return !bool;
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
/*      */     final boolean isOwnedBy(AbstractQueuedLongSynchronizer param1AbstractQueuedLongSynchronizer) {
/* 1966 */       return (param1AbstractQueuedLongSynchronizer == AbstractQueuedLongSynchronizer.this);
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
/*      */     protected final boolean hasWaiters() {
/* 1978 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1979 */         throw new IllegalMonitorStateException(); 
/* 1980 */       for (AbstractQueuedLongSynchronizer.Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
/* 1981 */         if (node.waitStatus == -2)
/* 1982 */           return true; 
/*      */       } 
/* 1984 */       return false;
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
/*      */     protected final int getWaitQueueLength() {
/* 1997 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1998 */         throw new IllegalMonitorStateException(); 
/* 1999 */       byte b = 0;
/* 2000 */       for (AbstractQueuedLongSynchronizer.Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
/* 2001 */         if (node.waitStatus == -2)
/* 2002 */           b++; 
/*      */       } 
/* 2004 */       return b;
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
/*      */     protected final Collection<Thread> getWaitingThreads() {
/* 2017 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 2018 */         throw new IllegalMonitorStateException(); 
/* 2019 */       ArrayList<Thread> arrayList = new ArrayList();
/* 2020 */       for (AbstractQueuedLongSynchronizer.Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
/* 2021 */         if (node.waitStatus == -2) {
/* 2022 */           Thread thread = node.thread;
/* 2023 */           if (thread != null)
/* 2024 */             arrayList.add(thread); 
/*      */         } 
/*      */       } 
/* 2027 */       return arrayList;
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
/* 2040 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */   
/*      */   private static final long stateOffset;
/*      */   private static final long headOffset;
/*      */   private static final long tailOffset;
/*      */   private static final long waitStatusOffset;
/*      */   private static final long nextOffset;
/*      */   
/*      */   static {
/*      */     try {
/* 2050 */       stateOffset = unsafe.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("state"));
/*      */       
/* 2052 */       headOffset = unsafe.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("head"));
/*      */       
/* 2054 */       tailOffset = unsafe.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("tail"));
/*      */       
/* 2056 */       waitStatusOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
/*      */       
/* 2058 */       nextOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
/*      */     } catch (Exception exception) {
/* 2060 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean compareAndSetHead(Node paramNode) {
/* 2067 */     return unsafe.compareAndSwapObject(this, headOffset, null, paramNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean compareAndSetTail(Node paramNode1, Node paramNode2) {
/* 2074 */     return unsafe.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean compareAndSetWaitStatus(Node paramNode, int paramInt1, int paramInt2) {
/* 2083 */     return unsafe.compareAndSwapInt(paramNode, waitStatusOffset, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean compareAndSetNext(Node paramNode1, Node paramNode2, Node paramNode3) {
/* 2093 */     return unsafe.compareAndSwapObject(paramNode1, nextOffset, paramNode2, paramNode3);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/locks/AbstractQueuedLongSynchronizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */