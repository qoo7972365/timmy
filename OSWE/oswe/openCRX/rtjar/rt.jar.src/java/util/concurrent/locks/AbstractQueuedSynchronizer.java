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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractQueuedSynchronizer
/*      */   extends AbstractOwnableSynchronizer
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7373984972572414691L;
/*      */   private volatile transient Node head;
/*      */   private volatile transient Node tail;
/*      */   private volatile int state;
/*      */   static final long spinForTimeoutThreshold = 1000L;
/*      */   
/*      */   static final class Node
/*      */   {
/*  382 */     static final Node SHARED = new Node();
/*      */     
/*  384 */     static final Node EXCLUSIVE = null;
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
/*  484 */       return (this.nextWaiter == SHARED);
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
/*  495 */       Node node = this.prev;
/*  496 */       if (node == null) {
/*  497 */         throw new NullPointerException();
/*      */       }
/*  499 */       return node;
/*      */     }
/*      */ 
/*      */     
/*      */     Node() {}
/*      */     
/*      */     Node(Thread param1Thread, Node param1Node) {
/*  506 */       this.nextWaiter = param1Node;
/*  507 */       this.thread = param1Thread;
/*      */     }
/*      */     
/*      */     Node(Thread param1Thread, int param1Int) {
/*  511 */       this.waitStatus = param1Int;
/*  512 */       this.thread = param1Thread;
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
/*      */   protected final int getState() {
/*  541 */     return this.state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setState(int paramInt) {
/*  550 */     this.state = paramInt;
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
/*      */   protected final boolean compareAndSetState(int paramInt1, int paramInt2) {
/*  566 */     return unsafe.compareAndSwapInt(this, stateOffset, paramInt1, paramInt2);
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
/*  585 */       Node node = this.tail;
/*  586 */       if (node == null) {
/*  587 */         if (compareAndSetHead(new Node()))
/*  588 */           this.tail = this.head;  continue;
/*      */       } 
/*  590 */       paramNode.prev = node;
/*  591 */       if (compareAndSetTail(node, paramNode)) {
/*  592 */         node.next = paramNode;
/*  593 */         return node;
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
/*  606 */     Node node1 = new Node(Thread.currentThread(), paramNode);
/*      */     
/*  608 */     Node node2 = this.tail;
/*  609 */     if (node2 != null) {
/*  610 */       node1.prev = node2;
/*  611 */       if (compareAndSetTail(node2, node1)) {
/*  612 */         node2.next = node1;
/*  613 */         return node1;
/*      */       } 
/*      */     } 
/*  616 */     enq(node1);
/*  617 */     return node1;
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
/*  628 */     this.head = paramNode;
/*  629 */     paramNode.thread = null;
/*  630 */     paramNode.prev = null;
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
/*  644 */     int i = paramNode.waitStatus;
/*  645 */     if (i < 0) {
/*  646 */       compareAndSetWaitStatus(paramNode, i, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  654 */     Node node = paramNode.next;
/*  655 */     if (node == null || node.waitStatus > 0) {
/*  656 */       node = null;
/*  657 */       for (Node node1 = this.tail; node1 != null && node1 != paramNode; node1 = node1.prev) {
/*  658 */         if (node1.waitStatus <= 0)
/*  659 */           node = node1; 
/*      */       } 
/*  661 */     }  if (node != null) {
/*  662 */       LockSupport.unpark(node.thread);
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
/*  683 */       Node node = this.head;
/*  684 */       if (node != null && node != this.tail) {
/*  685 */         int i = node.waitStatus;
/*  686 */         if (i == -1) {
/*  687 */           if (!compareAndSetWaitStatus(node, -1, 0))
/*      */             continue; 
/*  689 */           unparkSuccessor(node);
/*      */         }
/*  691 */         else if (i == 0 && 
/*  692 */           !compareAndSetWaitStatus(node, 0, -3)) {
/*      */           continue;
/*      */         } 
/*  695 */       }  if (node == this.head) {
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
/*      */   private void setHeadAndPropagate(Node paramNode, int paramInt) {
/*  709 */     Node node = this.head;
/*  710 */     setHead(paramNode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  727 */     if (paramInt > 0 || node == null || node.waitStatus < 0 || (node = this.head) == null || node.waitStatus < 0) {
/*      */       
/*  729 */       Node node1 = paramNode.next;
/*  730 */       if (node1 == null || node1.isShared()) {
/*  731 */         doReleaseShared();
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
/*  744 */     if (paramNode == null) {
/*      */       return;
/*      */     }
/*  747 */     paramNode.thread = null;
/*      */ 
/*      */     
/*  750 */     Node node1 = paramNode.prev;
/*  751 */     while (node1.waitStatus > 0) {
/*  752 */       paramNode.prev = node1 = node1.prev;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  757 */     Node node2 = node1.next;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  762 */     paramNode.waitStatus = 1;
/*      */ 
/*      */     
/*  765 */     if (paramNode == this.tail && compareAndSetTail(paramNode, node1)) {
/*  766 */       compareAndSetNext(node1, node2, null);
/*      */     } else {
/*      */       int i;
/*      */ 
/*      */       
/*  771 */       if (node1 != this.head && ((i = node1.waitStatus) == -1 || (i <= 0 && 
/*      */         
/*  773 */         compareAndSetWaitStatus(node1, i, -1))) && node1.thread != null) {
/*      */         
/*  775 */         Node node = paramNode.next;
/*  776 */         if (node != null && node.waitStatus <= 0)
/*  777 */           compareAndSetNext(node1, node2, node); 
/*      */       } else {
/*  779 */         unparkSuccessor(paramNode);
/*      */       } 
/*      */       
/*  782 */       paramNode.next = paramNode;
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
/*  796 */     int i = paramNode1.waitStatus;
/*  797 */     if (i == -1)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  802 */       return true; } 
/*  803 */     if (i > 0)
/*      */     {
/*      */       while (true)
/*      */       
/*      */       { 
/*      */         
/*  809 */         paramNode2.prev = paramNode1 = paramNode1.prev;
/*  810 */         if (paramNode1.waitStatus <= 0)
/*  811 */         { paramNode1.next = paramNode2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  820 */           return false; }  }  }  compareAndSetWaitStatus(paramNode1, i, -1); return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void selfInterrupt() {
/*  827 */     Thread.currentThread().interrupt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean parkAndCheckInterrupt() {
/*  836 */     LockSupport.park(this);
/*  837 */     return Thread.interrupted();
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
/*      */   final boolean acquireQueued(Node paramNode, int paramInt) {
/*  858 */     boolean bool = true;
/*      */     try {
/*  860 */       boolean bool1 = false;
/*      */       while (true) {
/*  862 */         Node node = paramNode.predecessor();
/*  863 */         if (node == this.head && tryAcquire(paramInt)) {
/*  864 */           setHead(paramNode);
/*  865 */           node.next = null;
/*  866 */           bool = false;
/*  867 */           return bool1;
/*      */         } 
/*  869 */         if (shouldParkAfterFailedAcquire(node, paramNode) && 
/*  870 */           parkAndCheckInterrupt())
/*  871 */           bool1 = true; 
/*      */       } 
/*      */     } finally {
/*  874 */       if (bool) {
/*  875 */         cancelAcquire(paramNode);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doAcquireInterruptibly(int paramInt) throws InterruptedException {
/*  885 */     Node node = addWaiter(Node.EXCLUSIVE);
/*  886 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/*  889 */         Node node1 = node.predecessor();
/*  890 */         if (node1 == this.head && tryAcquire(paramInt)) {
/*  891 */           setHead(node);
/*  892 */           node1.next = null;
/*  893 */           bool = false;
/*      */           return;
/*      */         } 
/*  896 */         if (shouldParkAfterFailedAcquire(node1, node) && 
/*  897 */           parkAndCheckInterrupt())
/*  898 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/*  901 */       if (bool) {
/*  902 */         cancelAcquire(node);
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
/*      */   private boolean doAcquireNanos(int paramInt, long paramLong) throws InterruptedException {
/*  915 */     if (paramLong <= 0L)
/*  916 */       return false; 
/*  917 */     long l = System.nanoTime() + paramLong;
/*  918 */     Node node = addWaiter(Node.EXCLUSIVE);
/*  919 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/*  922 */         Node node1 = node.predecessor();
/*  923 */         if (node1 == this.head && tryAcquire(paramInt)) {
/*  924 */           setHead(node);
/*  925 */           node1.next = null;
/*  926 */           bool = false;
/*  927 */           return true;
/*      */         } 
/*  929 */         paramLong = l - System.nanoTime();
/*  930 */         if (paramLong <= 0L)
/*  931 */           return false; 
/*  932 */         if (shouldParkAfterFailedAcquire(node1, node) && paramLong > 1000L)
/*      */         {
/*  934 */           LockSupport.parkNanos(this, paramLong); } 
/*  935 */         if (Thread.interrupted())
/*  936 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/*  939 */       if (bool) {
/*  940 */         cancelAcquire(node);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doAcquireShared(int paramInt) {
/*  949 */     Node node = addWaiter(Node.SHARED);
/*  950 */     boolean bool = true;
/*      */     try {
/*  952 */       boolean bool1 = false;
/*      */       while (true) {
/*  954 */         Node node1 = node.predecessor();
/*  955 */         if (node1 == this.head) {
/*  956 */           int i = tryAcquireShared(paramInt);
/*  957 */           if (i >= 0) {
/*  958 */             setHeadAndPropagate(node, i);
/*  959 */             node1.next = null;
/*  960 */             if (bool1)
/*  961 */               selfInterrupt(); 
/*  962 */             bool = false;
/*      */             return;
/*      */           } 
/*      */         } 
/*  966 */         if (shouldParkAfterFailedAcquire(node1, node) && 
/*  967 */           parkAndCheckInterrupt())
/*  968 */           bool1 = true; 
/*      */       } 
/*      */     } finally {
/*  971 */       if (bool) {
/*  972 */         cancelAcquire(node);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doAcquireSharedInterruptibly(int paramInt) throws InterruptedException {
/*  982 */     Node node = addWaiter(Node.SHARED);
/*  983 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/*  986 */         Node node1 = node.predecessor();
/*  987 */         if (node1 == this.head) {
/*  988 */           int i = tryAcquireShared(paramInt);
/*  989 */           if (i >= 0) {
/*  990 */             setHeadAndPropagate(node, i);
/*  991 */             node1.next = null;
/*  992 */             bool = false;
/*      */             return;
/*      */           } 
/*      */         } 
/*  996 */         if (shouldParkAfterFailedAcquire(node1, node) && 
/*  997 */           parkAndCheckInterrupt())
/*  998 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/* 1001 */       if (bool) {
/* 1002 */         cancelAcquire(node);
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
/*      */   private boolean doAcquireSharedNanos(int paramInt, long paramLong) throws InterruptedException {
/* 1015 */     if (paramLong <= 0L)
/* 1016 */       return false; 
/* 1017 */     long l = System.nanoTime() + paramLong;
/* 1018 */     Node node = addWaiter(Node.SHARED);
/* 1019 */     boolean bool = true;
/*      */     try {
/*      */       while (true) {
/* 1022 */         Node node1 = node.predecessor();
/* 1023 */         if (node1 == this.head) {
/* 1024 */           int i = tryAcquireShared(paramInt);
/* 1025 */           if (i >= 0) {
/* 1026 */             setHeadAndPropagate(node, i);
/* 1027 */             node1.next = null;
/* 1028 */             bool = false;
/* 1029 */             return true;
/*      */           } 
/*      */         } 
/* 1032 */         paramLong = l - System.nanoTime();
/* 1033 */         if (paramLong <= 0L)
/* 1034 */           return false; 
/* 1035 */         if (shouldParkAfterFailedAcquire(node1, node) && paramLong > 1000L)
/*      */         {
/* 1037 */           LockSupport.parkNanos(this, paramLong); } 
/* 1038 */         if (Thread.interrupted())
/* 1039 */           throw new InterruptedException(); 
/*      */       } 
/*      */     } finally {
/* 1042 */       if (bool) {
/* 1043 */         cancelAcquire(node);
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
/*      */   protected boolean tryAcquire(int paramInt) {
/* 1076 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryRelease(int paramInt) {
/* 1102 */     throw new UnsupportedOperationException();
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
/*      */   protected int tryAcquireShared(int paramInt) {
/* 1138 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryReleaseShared(int paramInt) {
/* 1163 */     throw new UnsupportedOperationException();
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
/* 1182 */     throw new UnsupportedOperationException();
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
/*      */   public final void acquire(int paramInt) {
/* 1198 */     if (!tryAcquire(paramInt) && 
/* 1199 */       acquireQueued(addWaiter(Node.EXCLUSIVE), paramInt)) {
/* 1200 */       selfInterrupt();
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
/*      */   public final void acquireInterruptibly(int paramInt) throws InterruptedException {
/* 1219 */     if (Thread.interrupted())
/* 1220 */       throw new InterruptedException(); 
/* 1221 */     if (!tryAcquire(paramInt)) {
/* 1222 */       doAcquireInterruptibly(paramInt);
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
/*      */   public final boolean tryAcquireNanos(int paramInt, long paramLong) throws InterruptedException {
/* 1244 */     if (Thread.interrupted())
/* 1245 */       throw new InterruptedException(); 
/* 1246 */     return (tryAcquire(paramInt) || 
/* 1247 */       doAcquireNanos(paramInt, paramLong));
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
/*      */   public final boolean release(int paramInt) {
/* 1261 */     if (tryRelease(paramInt)) {
/* 1262 */       Node node = this.head;
/* 1263 */       if (node != null && node.waitStatus != 0)
/* 1264 */         unparkSuccessor(node); 
/* 1265 */       return true;
/*      */     } 
/* 1267 */     return false;
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
/*      */   public final void acquireShared(int paramInt) {
/* 1282 */     if (tryAcquireShared(paramInt) < 0) {
/* 1283 */       doAcquireShared(paramInt);
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
/*      */   public final void acquireSharedInterruptibly(int paramInt) throws InterruptedException {
/* 1301 */     if (Thread.interrupted())
/* 1302 */       throw new InterruptedException(); 
/* 1303 */     if (tryAcquireShared(paramInt) < 0) {
/* 1304 */       doAcquireSharedInterruptibly(paramInt);
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
/*      */   public final boolean tryAcquireSharedNanos(int paramInt, long paramLong) throws InterruptedException {
/* 1325 */     if (Thread.interrupted())
/* 1326 */       throw new InterruptedException(); 
/* 1327 */     return (tryAcquireShared(paramInt) >= 0 || 
/* 1328 */       doAcquireSharedNanos(paramInt, paramLong));
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
/*      */   public final boolean releaseShared(int paramInt) {
/* 1341 */     if (tryReleaseShared(paramInt)) {
/* 1342 */       doReleaseShared();
/* 1343 */       return true;
/*      */     } 
/* 1345 */     return false;
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
/* 1362 */     return (this.head != this.tail);
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
/* 1375 */     return (this.head != null);
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
/* 1391 */     return (this.head == this.tail) ? null : fullGetFirstQueuedThread();
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
/* 1408 */     if (((node1 = this.head) != null && (node2 = node1.next) != null && node2.prev == this.head && (thread1 = node2.thread) != null) || ((node1 = this.head) != null && (node2 = node1.next) != null && node2.prev == this.head && (thread1 = node2.thread) != null))
/*      */     {
/*      */ 
/*      */       
/* 1412 */       return thread1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1422 */     Node node3 = this.tail;
/* 1423 */     Thread thread2 = null;
/* 1424 */     while (node3 != null && node3 != this.head) {
/* 1425 */       Thread thread = node3.thread;
/* 1426 */       if (thread != null)
/* 1427 */         thread2 = thread; 
/* 1428 */       node3 = node3.prev;
/*      */     } 
/* 1430 */     return thread2;
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
/* 1444 */     if (paramThread == null)
/* 1445 */       throw new NullPointerException(); 
/* 1446 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1447 */       if (node.thread == paramThread)
/* 1448 */         return true; 
/* 1449 */     }  return false;
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
/* 1463 */     return ((node1 = this.head) != null && (node2 = node1.next) != null && 
/*      */       
/* 1465 */       !node2.isShared() && node2.thread != null);
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
/* 1516 */     Node node1 = this.tail;
/* 1517 */     Node node2 = this.head;
/*      */     Node node3;
/* 1519 */     return (node2 != node1 && ((node3 = node2.next) == null || node3.thread != 
/* 1520 */       Thread.currentThread()));
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
/* 1537 */     byte b = 0;
/* 1538 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1539 */       if (node.thread != null)
/* 1540 */         b++; 
/*      */     } 
/* 1542 */     return b;
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
/* 1557 */     ArrayList<Thread> arrayList = new ArrayList();
/* 1558 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1559 */       Thread thread = node.thread;
/* 1560 */       if (thread != null)
/* 1561 */         arrayList.add(thread); 
/*      */     } 
/* 1563 */     return arrayList;
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
/* 1575 */     ArrayList<Thread> arrayList = new ArrayList();
/* 1576 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1577 */       if (!node.isShared()) {
/* 1578 */         Thread thread = node.thread;
/* 1579 */         if (thread != null)
/* 1580 */           arrayList.add(thread); 
/*      */       } 
/*      */     } 
/* 1583 */     return arrayList;
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
/* 1595 */     ArrayList<Thread> arrayList = new ArrayList();
/* 1596 */     for (Node node = this.tail; node != null; node = node.prev) {
/* 1597 */       if (node.isShared()) {
/* 1598 */         Thread thread = node.thread;
/* 1599 */         if (thread != null)
/* 1600 */           arrayList.add(thread); 
/*      */       } 
/*      */     } 
/* 1603 */     return arrayList;
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
/* 1616 */     int i = getState();
/* 1617 */     String str = hasQueuedThreads() ? "non" : "";
/* 1618 */     return super.toString() + "[State = " + i + ", " + str + "empty queue]";
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
/* 1632 */     if (paramNode.waitStatus == -2 || paramNode.prev == null)
/* 1633 */       return false; 
/* 1634 */     if (paramNode.next != null) {
/* 1635 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1644 */     return findNodeFromTail(paramNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findNodeFromTail(Node paramNode) {
/* 1653 */     Node node = this.tail;
/*      */     while (true) {
/* 1655 */       if (node == paramNode)
/* 1656 */         return true; 
/* 1657 */       if (node == null)
/* 1658 */         return false; 
/* 1659 */       node = node.prev;
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
/* 1674 */     if (!compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1675 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1683 */     Node node = enq(paramNode);
/* 1684 */     int i = node.waitStatus;
/* 1685 */     if (i > 0 || !compareAndSetWaitStatus(node, i, -1))
/* 1686 */       LockSupport.unpark(paramNode.thread); 
/* 1687 */     return true;
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
/* 1698 */     if (compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1699 */       enq(paramNode);
/* 1700 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1708 */     while (!isOnSyncQueue(paramNode))
/* 1709 */       Thread.yield(); 
/* 1710 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int fullyRelease(Node paramNode) {
/* 1720 */     boolean bool = true;
/*      */     try {
/* 1722 */       int i = getState();
/* 1723 */       if (release(i)) {
/* 1724 */         bool = false;
/* 1725 */         return i;
/*      */       } 
/* 1727 */       throw new IllegalMonitorStateException();
/*      */     } finally {
/*      */       
/* 1730 */       if (bool) {
/* 1731 */         paramNode.waitStatus = 1;
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
/* 1746 */     return paramConditionObject.isOwnedBy(this);
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
/* 1766 */     if (!owns(paramConditionObject))
/* 1767 */       throw new IllegalArgumentException("Not owner"); 
/* 1768 */     return paramConditionObject.hasWaiters();
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
/* 1788 */     if (!owns(paramConditionObject))
/* 1789 */       throw new IllegalArgumentException("Not owner"); 
/* 1790 */     return paramConditionObject.getWaitQueueLength();
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
/* 1810 */     if (!owns(paramConditionObject))
/* 1811 */       throw new IllegalArgumentException("Not owner"); 
/* 1812 */     return paramConditionObject.getWaitingThreads();
/*      */   }
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
/*      */     private transient AbstractQueuedSynchronizer.Node firstWaiter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private transient AbstractQueuedSynchronizer.Node lastWaiter;
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
/*      */     private AbstractQueuedSynchronizer.Node addConditionWaiter() {
/* 1849 */       AbstractQueuedSynchronizer.Node node1 = this.lastWaiter;
/*      */       
/* 1851 */       if (node1 != null && node1.waitStatus != -2) {
/* 1852 */         unlinkCancelledWaiters();
/* 1853 */         node1 = this.lastWaiter;
/*      */       } 
/* 1855 */       AbstractQueuedSynchronizer.Node node2 = new AbstractQueuedSynchronizer.Node(Thread.currentThread(), -2);
/* 1856 */       if (node1 == null) {
/* 1857 */         this.firstWaiter = node2;
/*      */       } else {
/* 1859 */         node1.nextWaiter = node2;
/* 1860 */       }  this.lastWaiter = node2;
/* 1861 */       return node2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doSignal(AbstractQueuedSynchronizer.Node param1Node) {
/*      */       do {
/* 1872 */         if ((this.firstWaiter = param1Node.nextWaiter) == null)
/* 1873 */           this.lastWaiter = null; 
/* 1874 */         param1Node.nextWaiter = null;
/* 1875 */       } while (!AbstractQueuedSynchronizer.this.transferForSignal(param1Node) && (param1Node = this.firstWaiter) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doSignalAll(AbstractQueuedSynchronizer.Node param1Node) {
/* 1884 */       this.lastWaiter = this.firstWaiter = null;
/*      */       do {
/* 1886 */         AbstractQueuedSynchronizer.Node node = param1Node.nextWaiter;
/* 1887 */         param1Node.nextWaiter = null;
/* 1888 */         AbstractQueuedSynchronizer.this.transferForSignal(param1Node);
/* 1889 */         param1Node = node;
/* 1890 */       } while (param1Node != null);
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
/* 1908 */       AbstractQueuedSynchronizer.Node node1 = this.firstWaiter;
/* 1909 */       AbstractQueuedSynchronizer.Node node2 = null;
/* 1910 */       while (node1 != null) {
/* 1911 */         AbstractQueuedSynchronizer.Node node = node1.nextWaiter;
/* 1912 */         if (node1.waitStatus != -2) {
/* 1913 */           node1.nextWaiter = null;
/* 1914 */           if (node2 == null) {
/* 1915 */             this.firstWaiter = node;
/*      */           } else {
/* 1917 */             node2.nextWaiter = node;
/* 1918 */           }  if (node == null) {
/* 1919 */             this.lastWaiter = node2;
/*      */           }
/*      */         } else {
/* 1922 */           node2 = node1;
/* 1923 */         }  node1 = node;
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
/* 1938 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 1939 */         throw new IllegalMonitorStateException(); 
/* 1940 */       AbstractQueuedSynchronizer.Node node = this.firstWaiter;
/* 1941 */       if (node != null) {
/* 1942 */         doSignal(node);
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
/* 1953 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 1954 */         throw new IllegalMonitorStateException(); 
/* 1955 */       AbstractQueuedSynchronizer.Node node = this.firstWaiter;
/* 1956 */       if (node != null) {
/* 1957 */         doSignalAll(node);
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
/* 1972 */       AbstractQueuedSynchronizer.Node node = addConditionWaiter();
/* 1973 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(node);
/* 1974 */       boolean bool = false;
/* 1975 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(node)) {
/* 1976 */         LockSupport.park(this);
/* 1977 */         if (Thread.interrupted())
/* 1978 */           bool = true; 
/*      */       } 
/* 1980 */       if (AbstractQueuedSynchronizer.this.acquireQueued(node, i) || bool) {
/* 1981 */         AbstractQueuedSynchronizer.selfInterrupt();
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
/*      */     private int checkInterruptWhileWaiting(AbstractQueuedSynchronizer.Node param1Node) {
/* 2002 */       return Thread.interrupted() ? (
/* 2003 */         AbstractQueuedSynchronizer.this.transferAfterCancelledWait(param1Node) ? -1 : 1) : 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void reportInterruptAfterWait(int param1Int) throws InterruptedException {
/* 2013 */       if (param1Int == -1)
/* 2014 */         throw new InterruptedException(); 
/* 2015 */       if (param1Int == 1) {
/* 2016 */         AbstractQueuedSynchronizer.selfInterrupt();
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
/* 2033 */       if (Thread.interrupted())
/* 2034 */         throw new InterruptedException(); 
/* 2035 */       AbstractQueuedSynchronizer.Node node = addConditionWaiter();
/* 2036 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(node);
/* 2037 */       int j = 0;
/*      */       do {
/* 2039 */         LockSupport.park(this);
/* 2040 */       } while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(node) && (j = checkInterruptWhileWaiting(node)) == 0);
/*      */ 
/*      */       
/* 2043 */       if (AbstractQueuedSynchronizer.this.acquireQueued(node, i) && j != -1)
/* 2044 */         j = 1; 
/* 2045 */       if (node.nextWaiter != null)
/* 2046 */         unlinkCancelledWaiters(); 
/* 2047 */       if (j != 0) {
/* 2048 */         reportInterruptAfterWait(j);
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
/* 2066 */       if (Thread.interrupted())
/* 2067 */         throw new InterruptedException(); 
/* 2068 */       AbstractQueuedSynchronizer.Node node = addConditionWaiter();
/* 2069 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(node);
/* 2070 */       long l = System.nanoTime() + param1Long;
/* 2071 */       int j = 0;
/* 2072 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(node)) {
/* 2073 */         if (param1Long <= 0L) {
/* 2074 */           AbstractQueuedSynchronizer.this.transferAfterCancelledWait(node);
/*      */           break;
/*      */         } 
/* 2077 */         if (param1Long >= 1000L)
/* 2078 */           LockSupport.parkNanos(this, param1Long); 
/* 2079 */         if ((j = checkInterruptWhileWaiting(node)) != 0)
/*      */           break; 
/* 2081 */         param1Long = l - System.nanoTime();
/*      */       } 
/* 2083 */       if (AbstractQueuedSynchronizer.this.acquireQueued(node, i) && j != -1)
/* 2084 */         j = 1; 
/* 2085 */       if (node.nextWaiter != null)
/* 2086 */         unlinkCancelledWaiters(); 
/* 2087 */       if (j != 0)
/* 2088 */         reportInterruptAfterWait(j); 
/* 2089 */       return l - System.nanoTime();
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
/* 2108 */       long l = param1Date.getTime();
/* 2109 */       if (Thread.interrupted())
/* 2110 */         throw new InterruptedException(); 
/* 2111 */       AbstractQueuedSynchronizer.Node node = addConditionWaiter();
/* 2112 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(node);
/* 2113 */       boolean bool = false;
/* 2114 */       int j = 0;
/* 2115 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(node)) {
/* 2116 */         if (System.currentTimeMillis() > l) {
/* 2117 */           bool = AbstractQueuedSynchronizer.this.transferAfterCancelledWait(node);
/*      */           break;
/*      */         } 
/* 2120 */         LockSupport.parkUntil(this, l);
/* 2121 */         if ((j = checkInterruptWhileWaiting(node)) != 0)
/*      */           break; 
/*      */       } 
/* 2124 */       if (AbstractQueuedSynchronizer.this.acquireQueued(node, i) && j != -1)
/* 2125 */         j = 1; 
/* 2126 */       if (node.nextWaiter != null)
/* 2127 */         unlinkCancelledWaiters(); 
/* 2128 */       if (j != 0)
/* 2129 */         reportInterruptAfterWait(j); 
/* 2130 */       return !bool;
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
/* 2149 */       long l1 = param1TimeUnit.toNanos(param1Long);
/* 2150 */       if (Thread.interrupted())
/* 2151 */         throw new InterruptedException(); 
/* 2152 */       AbstractQueuedSynchronizer.Node node = addConditionWaiter();
/* 2153 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(node);
/* 2154 */       long l2 = System.nanoTime() + l1;
/* 2155 */       boolean bool = false;
/* 2156 */       int j = 0;
/* 2157 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(node)) {
/* 2158 */         if (l1 <= 0L) {
/* 2159 */           bool = AbstractQueuedSynchronizer.this.transferAfterCancelledWait(node);
/*      */           break;
/*      */         } 
/* 2162 */         if (l1 >= 1000L)
/* 2163 */           LockSupport.parkNanos(this, l1); 
/* 2164 */         if ((j = checkInterruptWhileWaiting(node)) != 0)
/*      */           break; 
/* 2166 */         l1 = l2 - System.nanoTime();
/*      */       } 
/* 2168 */       if (AbstractQueuedSynchronizer.this.acquireQueued(node, i) && j != -1)
/* 2169 */         j = 1; 
/* 2170 */       if (node.nextWaiter != null)
/* 2171 */         unlinkCancelledWaiters(); 
/* 2172 */       if (j != 0)
/* 2173 */         reportInterruptAfterWait(j); 
/* 2174 */       return !bool;
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
/*      */     final boolean isOwnedBy(AbstractQueuedSynchronizer param1AbstractQueuedSynchronizer) {
/* 2186 */       return (param1AbstractQueuedSynchronizer == AbstractQueuedSynchronizer.this);
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
/* 2198 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 2199 */         throw new IllegalMonitorStateException(); 
/* 2200 */       for (AbstractQueuedSynchronizer.Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
/* 2201 */         if (node.waitStatus == -2)
/* 2202 */           return true; 
/*      */       } 
/* 2204 */       return false;
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
/* 2217 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 2218 */         throw new IllegalMonitorStateException(); 
/* 2219 */       byte b = 0;
/* 2220 */       for (AbstractQueuedSynchronizer.Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
/* 2221 */         if (node.waitStatus == -2)
/* 2222 */           b++; 
/*      */       } 
/* 2224 */       return b;
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
/* 2237 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 2238 */         throw new IllegalMonitorStateException(); 
/* 2239 */       ArrayList<Thread> arrayList = new ArrayList();
/* 2240 */       for (AbstractQueuedSynchronizer.Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
/* 2241 */         if (node.waitStatus == -2) {
/* 2242 */           Thread thread = node.thread;
/* 2243 */           if (thread != null)
/* 2244 */             arrayList.add(thread); 
/*      */         } 
/*      */       } 
/* 2247 */       return arrayList;
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
/* 2260 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */   
/*      */   private static final long stateOffset;
/*      */   private static final long headOffset;
/*      */   private static final long tailOffset;
/*      */   private static final long waitStatusOffset;
/*      */   private static final long nextOffset;
/*      */   
/*      */   static {
/*      */     try {
/* 2270 */       stateOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("state"));
/*      */       
/* 2272 */       headOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("head"));
/*      */       
/* 2274 */       tailOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
/*      */       
/* 2276 */       waitStatusOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
/*      */       
/* 2278 */       nextOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
/*      */     } catch (Exception exception) {
/* 2280 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean compareAndSetHead(Node paramNode) {
/* 2287 */     return unsafe.compareAndSwapObject(this, headOffset, null, paramNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean compareAndSetTail(Node paramNode1, Node paramNode2) {
/* 2294 */     return unsafe.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean compareAndSetWaitStatus(Node paramNode, int paramInt1, int paramInt2) {
/* 2303 */     return unsafe.compareAndSwapInt(paramNode, waitStatusOffset, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean compareAndSetNext(Node paramNode1, Node paramNode2, Node paramNode3) {
/* 2313 */     return unsafe.compareAndSwapObject(paramNode1, nextOffset, paramNode2, paramNode3);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/locks/AbstractQueuedSynchronizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */