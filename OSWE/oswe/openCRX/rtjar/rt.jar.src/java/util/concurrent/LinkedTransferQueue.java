/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.function.Consumer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LinkedTransferQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements TransferQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3223113410248163686L;
/*  415 */   private static final boolean MP = (Runtime.getRuntime().availableProcessors() > 1);
/*      */   
/*      */   private static final int FRONT_SPINS = 128;
/*      */   
/*      */   private static final int CHAINED_SPINS = 64;
/*      */   
/*      */   static final int SWEEP_THRESHOLD = 32;
/*      */   
/*      */   volatile transient Node head;
/*      */   
/*      */   private volatile transient Node tail;
/*      */   
/*      */   private volatile transient int sweepVotes;
/*      */   
/*      */   private static final int NOW = 0;
/*      */   
/*      */   private static final int ASYNC = 1;
/*      */   
/*      */   private static final int SYNC = 2;
/*      */   
/*      */   private static final int TIMED = 3;
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */   private static final long headOffset;
/*      */   
/*      */   private static final long tailOffset;
/*      */   
/*      */   private static final long sweepVotesOffset;
/*      */ 
/*      */   
/*      */   static final class Node
/*      */   {
/*      */     final boolean isData;
/*      */     volatile Object item;
/*      */     volatile Node next;
/*      */     volatile Thread waiter;
/*      */     private static final long serialVersionUID = -3375979862319811754L;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long itemOffset;
/*      */     private static final long nextOffset;
/*      */     private static final long waiterOffset;
/*      */     
/*      */     final boolean casNext(Node param1Node1, Node param1Node2) {
/*  459 */       return UNSAFE.compareAndSwapObject(this, nextOffset, param1Node1, param1Node2);
/*      */     }
/*      */ 
/*      */     
/*      */     final boolean casItem(Object param1Object1, Object param1Object2) {
/*  464 */       return UNSAFE.compareAndSwapObject(this, itemOffset, param1Object1, param1Object2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Node(Object param1Object, boolean param1Boolean) {
/*  472 */       UNSAFE.putObject(this, itemOffset, param1Object);
/*  473 */       this.isData = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void forgetNext() {
/*  481 */       UNSAFE.putObject(this, nextOffset, this);
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
/*      */     final void forgetContents() {
/*  494 */       UNSAFE.putObject(this, itemOffset, this);
/*  495 */       UNSAFE.putObject(this, waiterOffset, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean isMatched() {
/*  503 */       Object object = this.item;
/*  504 */       return (object == this || ((object == null)) == this.isData);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean isUnmatchedRequest() {
/*  511 */       return (!this.isData && this.item == null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean cannotPrecede(boolean param1Boolean) {
/*  520 */       boolean bool = this.isData;
/*      */       Object object;
/*  522 */       return (bool != param1Boolean && (object = this.item) != this && ((object != null)) == bool);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean tryMatchData() {
/*  530 */       Object object = this.item;
/*  531 */       if (object != null && object != this && casItem(object, null)) {
/*  532 */         LockSupport.unpark(this.waiter);
/*  533 */         return true;
/*      */       } 
/*  535 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/*  547 */         UNSAFE = Unsafe.getUnsafe();
/*  548 */         Class<Node> clazz = Node.class;
/*      */         
/*  550 */         itemOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
/*      */         
/*  552 */         nextOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
/*      */         
/*  554 */         waiterOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("waiter"));
/*  555 */       } catch (Exception exception) {
/*  556 */         throw new Error(exception);
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
/*      */   private boolean casTail(Node paramNode1, Node paramNode2) {
/*  572 */     return UNSAFE.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   private boolean casHead(Node paramNode1, Node paramNode2) {
/*  576 */     return UNSAFE.compareAndSwapObject(this, headOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   private boolean casSweepVotes(int paramInt1, int paramInt2) {
/*  580 */     return UNSAFE.compareAndSwapInt(this, sweepVotesOffset, paramInt1, paramInt2);
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
/*      */   static <E> E cast(Object paramObject) {
/*  594 */     return (E)paramObject;
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
/*      */   private E xfer(E paramE, boolean paramBoolean, int paramInt, long paramLong) {
/*  608 */     if (paramBoolean && paramE == null)
/*  609 */       throw new NullPointerException(); 
/*  610 */     Node node = null;
/*      */     
/*      */     while (true) {
/*      */       Node node1;
/*      */       
/*  615 */       for (Node node2 = node1 = this.head; node2 != null; ) {
/*  616 */         boolean bool = node2.isData;
/*  617 */         Object object = node2.item;
/*  618 */         if (object != node2 && ((object != null)) == bool) {
/*  619 */           if (bool == paramBoolean)
/*      */             break; 
/*  621 */           if (node2.casItem(object, paramE)) {
/*  622 */             for (Node node4 = node2; node4 != node1; ) {
/*  623 */               Node node5 = node4.next;
/*  624 */               if (this.head == node1 && casHead(node1, (node5 == null) ? node4 : node5)) {
/*  625 */                 node1.forgetNext();
/*      */                 break;
/*      */               } 
/*  628 */               if ((node1 = this.head) == null || (node4 = node1.next) == null || 
/*  629 */                 !node4.isMatched())
/*      */                 break; 
/*      */             } 
/*  632 */             LockSupport.unpark(node2.waiter);
/*  633 */             return cast(object);
/*      */           } 
/*      */         } 
/*  636 */         Node node3 = node2.next;
/*  637 */         node2 = (node2 != node3) ? node3 : (node1 = this.head);
/*      */       } 
/*      */       
/*  640 */       if (paramInt != 0) {
/*  641 */         if (node == null)
/*  642 */           node = new Node(paramE, paramBoolean); 
/*  643 */         node1 = tryAppend(node, paramBoolean);
/*  644 */         if (node1 == null)
/*      */           continue; 
/*  646 */         if (paramInt != 1)
/*  647 */           return awaitMatch(node, node1, paramE, (paramInt == 3), paramLong); 
/*      */       }  break;
/*  649 */     }  return paramE;
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
/*      */   private Node tryAppend(Node paramNode, boolean paramBoolean) {
/*  663 */     Node node1 = this.tail, node2 = node1;
/*      */     while (true) {
/*  665 */       if (node2 == null && (node2 = this.head) == null) {
/*  666 */         if (casHead(null, paramNode))
/*  667 */           return paramNode;  continue;
/*      */       } 
/*  669 */       if (node2.cannotPrecede(paramBoolean))
/*  670 */         return null;  Node node;
/*  671 */       if ((node = node2.next) != null) {
/*  672 */         Node node3; node2 = (node2 != node1 && node1 != (node3 = this.tail)) ? (node1 = node3) : ((node2 != node) ? node : null); continue;
/*      */       } 
/*  674 */       if (!node2.casNext(null, paramNode)) {
/*  675 */         node2 = node2.next; continue;
/*      */       }  break;
/*  677 */     }  if (node2 != node1) {
/*  678 */       while ((this.tail != node1 || !casTail(node1, paramNode)) && (node1 = this.tail) != null && (paramNode = node1.next) != null && (paramNode = paramNode.next) != null && paramNode != node1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  683 */     return node2;
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
/*      */   private E awaitMatch(Node paramNode1, Node paramNode2, E paramE, boolean paramBoolean, long paramLong) {
/*  701 */     long l = paramBoolean ? (System.nanoTime() + paramLong) : 0L;
/*  702 */     Thread thread = Thread.currentThread();
/*  703 */     int i = -1;
/*  704 */     ThreadLocalRandom threadLocalRandom = null;
/*      */     
/*      */     while (true) {
/*  707 */       Object object = paramNode1.item;
/*  708 */       if (object != paramE) {
/*      */         
/*  710 */         paramNode1.forgetContents();
/*  711 */         return cast(object);
/*      */       } 
/*  713 */       if ((thread.isInterrupted() || (paramBoolean && paramLong <= 0L)) && paramNode1
/*  714 */         .casItem(paramE, paramNode1)) {
/*  715 */         unsplice(paramNode2, paramNode1);
/*  716 */         return paramE;
/*      */       } 
/*      */       
/*  719 */       if (i < 0) {
/*  720 */         if ((i = spinsFor(paramNode2, paramNode1.isData)) > 0)
/*  721 */           threadLocalRandom = ThreadLocalRandom.current();  continue;
/*      */       } 
/*  723 */       if (i > 0) {
/*  724 */         i--;
/*  725 */         if (threadLocalRandom.nextInt(64) == 0)
/*  726 */           Thread.yield();  continue;
/*      */       } 
/*  728 */       if (paramNode1.waiter == null) {
/*  729 */         paramNode1.waiter = thread; continue;
/*      */       } 
/*  731 */       if (paramBoolean) {
/*  732 */         paramLong = l - System.nanoTime();
/*  733 */         if (paramLong > 0L)
/*  734 */           LockSupport.parkNanos(this, paramLong); 
/*      */         continue;
/*      */       } 
/*  737 */       LockSupport.park(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int spinsFor(Node paramNode, boolean paramBoolean) {
/*  747 */     if (MP && paramNode != null) {
/*  748 */       if (paramNode.isData != paramBoolean)
/*  749 */         return 192; 
/*  750 */       if (paramNode.isMatched())
/*  751 */         return 128; 
/*  752 */       if (paramNode.waiter == null)
/*  753 */         return 64; 
/*      */     } 
/*  755 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node succ(Node paramNode) {
/*  766 */     Node node = paramNode.next;
/*  767 */     return (paramNode == node) ? this.head : node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node firstOfMode(boolean paramBoolean) {
/*  775 */     for (Node node = this.head; node != null; node = succ(node)) {
/*  776 */       if (!node.isMatched())
/*  777 */         return (node.isData == paramBoolean) ? node : null; 
/*      */     } 
/*  779 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node firstDataNode() {
/*  788 */     for (Node node = this.head; node != null; ) {
/*  789 */       Object object = node.item;
/*  790 */       if (node.isData) {
/*  791 */         if (object != null && object != node) {
/*  792 */           return node;
/*      */         }
/*  794 */       } else if (object == null) {
/*      */         break;
/*  796 */       }  if (node == (node = node.next))
/*  797 */         node = this.head; 
/*      */     } 
/*  799 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E firstDataItem() {
/*  807 */     for (Node node = this.head; node != null; node = succ(node)) {
/*  808 */       Object object = node.item;
/*  809 */       if (node.isData) {
/*  810 */         if (object != null && object != node) {
/*  811 */           return cast(object);
/*      */         }
/*  813 */       } else if (object == null) {
/*  814 */         return null;
/*      */       } 
/*  816 */     }  return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int countOfMode(boolean paramBoolean) {
/*  824 */     byte b = 0;
/*  825 */     for (Node node = this.head; node != null; ) {
/*  826 */       if (!node.isMatched()) {
/*  827 */         if (node.isData != paramBoolean)
/*  828 */           return 0; 
/*  829 */         if (++b == Integer.MAX_VALUE)
/*      */           break; 
/*      */       } 
/*  832 */       Node node1 = node.next;
/*  833 */       if (node1 != node) {
/*  834 */         node = node1; continue;
/*      */       } 
/*  836 */       b = 0;
/*  837 */       node = this.head;
/*      */     } 
/*      */     
/*  840 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private LinkedTransferQueue.Node nextNode;
/*      */ 
/*      */     
/*      */     private E nextItem;
/*      */ 
/*      */     
/*      */     private LinkedTransferQueue.Node lastRet;
/*      */ 
/*      */     
/*      */     private LinkedTransferQueue.Node lastPred;
/*      */ 
/*      */ 
/*      */     
/*      */     private void advance(LinkedTransferQueue.Node param1Node) {
/*      */       LinkedTransferQueue.Node node1;
/*  864 */       if ((node1 = this.lastRet) != null && !node1.isMatched())
/*  865 */       { this.lastPred = node1; }
/*  866 */       else { LinkedTransferQueue.Node node; if ((node = this.lastPred) == null || node.isMatched()) {
/*  867 */           this.lastPred = null;
/*      */         } else {
/*      */           LinkedTransferQueue.Node node3; LinkedTransferQueue.Node node4;
/*  870 */           while ((node3 = node.next) != null && node3 != node && node3
/*  871 */             .isMatched() && (node4 = node3.next) != null && node4 != node3)
/*      */           {
/*  873 */             node.casNext(node3, node4); } 
/*      */         }  }
/*      */       
/*  876 */       this.lastRet = param1Node;
/*      */       
/*  878 */       LinkedTransferQueue.Node node2 = param1Node; while (true) {
/*  879 */         LinkedTransferQueue.Node node3 = (node2 == null) ? LinkedTransferQueue.this.head : node2.next;
/*  880 */         if (node3 == null)
/*      */           break; 
/*  882 */         if (node3 == node2) {
/*  883 */           node2 = null;
/*      */           continue;
/*      */         } 
/*  886 */         Object object = node3.item;
/*  887 */         if (node3.isData) {
/*  888 */           if (object != null && object != node3) {
/*  889 */             this.nextItem = LinkedTransferQueue.cast(object);
/*  890 */             this.nextNode = node3;
/*      */             
/*      */             return;
/*      */           } 
/*  894 */         } else if (object == null) {
/*      */           break;
/*      */         } 
/*  897 */         if (node2 == null) {
/*  898 */           node2 = node3; continue;
/*  899 */         }  LinkedTransferQueue.Node node4; if ((node4 = node3.next) == null)
/*      */           break; 
/*  901 */         if (node3 == node4) {
/*  902 */           node2 = null; continue;
/*      */         } 
/*  904 */         node2.casNext(node3, node4);
/*      */       } 
/*  906 */       this.nextNode = null;
/*  907 */       this.nextItem = null;
/*      */     }
/*      */     
/*      */     Itr() {
/*  911 */       advance(null);
/*      */     }
/*      */     
/*      */     public final boolean hasNext() {
/*  915 */       return (this.nextNode != null);
/*      */     }
/*      */     
/*      */     public final E next() {
/*  919 */       LinkedTransferQueue.Node node = this.nextNode;
/*  920 */       if (node == null) throw new NoSuchElementException(); 
/*  921 */       E e = this.nextItem;
/*  922 */       advance(node);
/*  923 */       return e;
/*      */     }
/*      */     
/*      */     public final void remove() {
/*  927 */       LinkedTransferQueue.Node node = this.lastRet;
/*  928 */       if (node == null)
/*  929 */         throw new IllegalStateException(); 
/*  930 */       this.lastRet = null;
/*  931 */       if (node.tryMatchData())
/*  932 */         LinkedTransferQueue.this.unsplice(this.lastPred, node); 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class LTQSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedTransferQueue<E> queue;
/*      */     LinkedTransferQueue.Node current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     
/*      */     LTQSpliterator(LinkedTransferQueue<E> param1LinkedTransferQueue) {
/*  944 */       this.queue = param1LinkedTransferQueue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<E> trySplit() {
/*  949 */       LinkedTransferQueue<E> linkedTransferQueue = this.queue;
/*  950 */       int i = this.batch;
/*  951 */       byte b = (i <= 0) ? 1 : ((i >= 33554432) ? 33554432 : (i + 1)); LinkedTransferQueue.Node node;
/*  952 */       if (!this.exhausted && ((node = this.current) != null || (
/*  953 */         node = linkedTransferQueue.firstDataNode()) != null) && node.next != null) {
/*      */         
/*  955 */         Object[] arrayOfObject = new Object[b];
/*  956 */         byte b1 = 0;
/*      */         do {
/*  958 */           Object object = node.item;
/*  959 */           arrayOfObject[b1] = object; if (object != node && object != null)
/*  960 */             b1++; 
/*  961 */           if (node != (node = node.next))
/*  962 */             continue;  node = linkedTransferQueue.firstDataNode();
/*  963 */         } while (node != null && b1 < b && node.isData);
/*  964 */         if ((this.current = node) == null)
/*  965 */           this.exhausted = true; 
/*  966 */         if (b1 > 0) {
/*  967 */           this.batch = b1;
/*  968 */           return 
/*  969 */             Spliterators.spliterator(arrayOfObject, 0, b1, 4368);
/*      */         } 
/*      */       } 
/*      */       
/*  973 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/*  979 */       if (param1Consumer == null) throw new NullPointerException(); 
/*  980 */       LinkedTransferQueue<E> linkedTransferQueue = this.queue; LinkedTransferQueue.Node node;
/*  981 */       if (!this.exhausted && ((node = this.current) != null || (
/*  982 */         node = linkedTransferQueue.firstDataNode()) != null)) {
/*  983 */         this.exhausted = true;
/*      */         do {
/*  985 */           Object object = node.item;
/*  986 */           if (object != null && object != node)
/*  987 */             param1Consumer.accept((E)object); 
/*  988 */           if (node != (node = node.next))
/*  989 */             continue;  node = linkedTransferQueue.firstDataNode();
/*  990 */         } while (node != null && node.isData);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/*  997 */       if (param1Consumer == null) throw new NullPointerException(); 
/*  998 */       LinkedTransferQueue<E> linkedTransferQueue = this.queue; LinkedTransferQueue.Node node;
/*  999 */       if (!this.exhausted && ((node = this.current) != null || (
/* 1000 */         node = linkedTransferQueue.firstDataNode()) != null)) {
/*      */         Object object;
/*      */         do {
/* 1003 */           if ((object = node.item) == node)
/* 1004 */             object = null; 
/* 1005 */           if (node != (node = node.next))
/* 1006 */             continue;  node = linkedTransferQueue.firstDataNode();
/* 1007 */         } while (object == null && node != null && node.isData);
/* 1008 */         if ((this.current = node) == null)
/* 1009 */           this.exhausted = true; 
/* 1010 */         if (object != null) {
/* 1011 */           param1Consumer.accept((E)object);
/* 1012 */           return true;
/*      */         } 
/*      */       } 
/* 1015 */       return false;
/*      */     }
/*      */     public long estimateSize() {
/* 1018 */       return Long.MAX_VALUE;
/*      */     }
/*      */     public int characteristics() {
/* 1021 */       return 4368;
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
/*      */   public Spliterator<E> spliterator() {
/* 1043 */     return new LTQSpliterator<>(this);
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
/*      */   final void unsplice(Node paramNode1, Node paramNode2) {
/* 1057 */     paramNode2.forgetContents();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1065 */     if (paramNode1 != null && paramNode1 != paramNode2 && paramNode1.next == paramNode2) {
/* 1066 */       Node node = paramNode2.next;
/* 1067 */       if (node == null || (node != paramNode2 && paramNode1
/* 1068 */         .casNext(paramNode2, node) && paramNode1.isMatched())) {
/*      */         while (true) {
/* 1070 */           Node node1 = this.head;
/* 1071 */           if (node1 == paramNode1 || node1 == paramNode2 || node1 == null)
/*      */             return; 
/* 1073 */           if (!node1.isMatched())
/*      */             break; 
/* 1075 */           Node node2 = node1.next;
/* 1076 */           if (node2 == null)
/*      */             return; 
/* 1078 */           if (node2 != node1 && casHead(node1, node2))
/* 1079 */             node1.forgetNext(); 
/*      */         } 
/* 1081 */         if (paramNode1.next != paramNode1 && paramNode2.next != paramNode2) {
/*      */           while (true) {
/* 1083 */             int i = this.sweepVotes;
/* 1084 */             if (i < 32) {
/* 1085 */               if (casSweepVotes(i, i + 1))
/*      */                 break;  continue;
/*      */             } 
/* 1088 */             if (casSweepVotes(i, 0)) {
/* 1089 */               sweep();
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sweep() {
/*      */     Node node2;
/* 1103 */     for (Node node1 = this.head; node1 != null && (node2 = node1.next) != null; ) {
/* 1104 */       if (!node2.isMatched()) {
/*      */         
/* 1106 */         node1 = node2; continue;
/* 1107 */       }  Node node; if ((node = node2.next) == null)
/*      */         break; 
/* 1109 */       if (node2 == node) {
/*      */         
/* 1111 */         node1 = this.head; continue;
/*      */       } 
/* 1113 */       node1.casNext(node2, node);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findAndRemove(Object paramObject) {
/* 1121 */     if (paramObject != null) {
/* 1122 */       for (Node node1 = null, node2 = this.head; node2 != null; ) {
/* 1123 */         Object object = node2.item;
/* 1124 */         if (node2.isData) {
/* 1125 */           if (object != null && object != node2 && paramObject.equals(object) && node2
/* 1126 */             .tryMatchData()) {
/* 1127 */             unsplice(node1, node2);
/* 1128 */             return true;
/*      */           }
/*      */         
/* 1131 */         } else if (object == null) {
/*      */           break;
/* 1133 */         }  node1 = node2;
/* 1134 */         if ((node2 = node2.next) == node1) {
/* 1135 */           node1 = null;
/* 1136 */           node2 = this.head;
/*      */         } 
/*      */       } 
/*      */     }
/* 1140 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedTransferQueue() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedTransferQueue(Collection<? extends E> paramCollection) {
/* 1159 */     this();
/* 1160 */     addAll(paramCollection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void put(E paramE) {
/* 1170 */     xfer(paramE, true, 1, 0L);
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
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit) {
/* 1184 */     xfer(paramE, true, 1, 0L);
/* 1185 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offer(E paramE) {
/* 1196 */     xfer(paramE, true, 1, 0L);
/* 1197 */     return true;
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
/*      */   public boolean add(E paramE) {
/* 1209 */     xfer(paramE, true, 1, 0L);
/* 1210 */     return true;
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
/*      */   public boolean tryTransfer(E paramE) {
/* 1224 */     return (xfer(paramE, true, 0, 0L) == null);
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
/*      */   public void transfer(E paramE) throws InterruptedException {
/* 1239 */     if (xfer(paramE, true, 2, 0L) != null) {
/* 1240 */       Thread.interrupted();
/* 1241 */       throw new InterruptedException();
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
/*      */   public boolean tryTransfer(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 1261 */     if (xfer(paramE, true, 3, paramTimeUnit.toNanos(paramLong)) == null)
/* 1262 */       return true; 
/* 1263 */     if (!Thread.interrupted())
/* 1264 */       return false; 
/* 1265 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException {
/* 1269 */     E e = xfer((E)null, false, 2, 0L);
/* 1270 */     if (e != null)
/* 1271 */       return e; 
/* 1272 */     Thread.interrupted();
/* 1273 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 1277 */     E e = xfer((E)null, false, 3, paramTimeUnit.toNanos(paramLong));
/* 1278 */     if (e != null || !Thread.interrupted())
/* 1279 */       return e; 
/* 1280 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */   public E poll() {
/* 1284 */     return xfer((E)null, false, 0, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection) {
/* 1292 */     if (paramCollection == null)
/* 1293 */       throw new NullPointerException(); 
/* 1294 */     if (paramCollection == this)
/* 1295 */       throw new IllegalArgumentException(); 
/* 1296 */     byte b = 0; E e;
/* 1297 */     while ((e = poll()) != null) {
/* 1298 */       paramCollection.add(e);
/* 1299 */       b++;
/*      */     } 
/* 1301 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt) {
/* 1309 */     if (paramCollection == null)
/* 1310 */       throw new NullPointerException(); 
/* 1311 */     if (paramCollection == this)
/* 1312 */       throw new IllegalArgumentException(); 
/* 1313 */     byte b = 0; E e;
/* 1314 */     while (b < paramInt && (e = poll()) != null) {
/* 1315 */       paramCollection.add(e);
/* 1316 */       b++;
/*      */     } 
/* 1318 */     return b;
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
/*      */   public Iterator<E> iterator() {
/* 1331 */     return new Itr();
/*      */   }
/*      */   
/*      */   public E peek() {
/* 1335 */     return firstDataItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1344 */     for (Node node = this.head; node != null; node = succ(node)) {
/* 1345 */       if (!node.isMatched())
/* 1346 */         return !node.isData; 
/*      */     } 
/* 1348 */     return true;
/*      */   }
/*      */   
/*      */   public boolean hasWaitingConsumer() {
/* 1352 */     return (firstOfMode(false) != null);
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
/*      */   public int size() {
/* 1368 */     return countOfMode(true);
/*      */   }
/*      */   
/*      */   public int getWaitingConsumerCount() {
/* 1372 */     return countOfMode(false);
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
/*      */   public boolean remove(Object paramObject) {
/* 1387 */     return findAndRemove(paramObject);
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
/*      */   public boolean contains(Object paramObject) {
/* 1399 */     if (paramObject == null) return false; 
/* 1400 */     for (Node node = this.head; node != null; node = succ(node)) {
/* 1401 */       Object object = node.item;
/* 1402 */       if (node.isData) {
/* 1403 */         if (object != null && object != node && paramObject.equals(object)) {
/* 1404 */           return true;
/*      */         }
/* 1406 */       } else if (object == null) {
/*      */         break;
/*      */       } 
/* 1409 */     }  return false;
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
/*      */   public int remainingCapacity() {
/* 1421 */     return Integer.MAX_VALUE;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1434 */     paramObjectOutputStream.defaultWriteObject();
/* 1435 */     for (E e : this) {
/* 1436 */       paramObjectOutputStream.writeObject(e);
/*      */     }
/* 1438 */     paramObjectOutputStream.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1450 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */     while (true) {
/* 1453 */       Object object = paramObjectInputStream.readObject();
/* 1454 */       if (object == null) {
/*      */         break;
/*      */       }
/* 1457 */       offer((E)object);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1469 */       UNSAFE = Unsafe.getUnsafe();
/* 1470 */       Class<LinkedTransferQueue> clazz = LinkedTransferQueue.class;
/*      */       
/* 1472 */       headOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
/*      */       
/* 1474 */       tailOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
/*      */       
/* 1476 */       sweepVotesOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("sweepVotes"));
/* 1477 */     } catch (Exception exception) {
/* 1478 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/LinkedTransferQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */