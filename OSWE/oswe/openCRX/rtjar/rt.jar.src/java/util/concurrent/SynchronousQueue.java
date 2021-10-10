/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.concurrent.locks.ReentrantLock;
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
/*      */ public class SynchronousQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3223113410248163686L;
/*      */   
/*      */   static abstract class Transferer<E>
/*      */   {
/*      */     abstract E transfer(E param1E, boolean param1Boolean, long param1Long);
/*      */   }
/*      */   
/*  186 */   static final int NCPUS = Runtime.getRuntime().availableProcessors();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  195 */   static final int maxTimedSpins = (NCPUS < 2) ? 0 : 32;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   static final int maxUntimedSpins = maxTimedSpins * 16;
/*      */   
/*      */   static final long spinForTimeoutThreshold = 1000L;
/*      */   
/*      */   private volatile transient Transferer<E> transferer;
/*      */   
/*      */   private ReentrantLock qlock;
/*      */   
/*      */   private WaitQueue waitingProducers;
/*      */   
/*      */   private WaitQueue waitingConsumers;
/*      */ 
/*      */   
/*      */   static final class TransferStack<E>
/*      */     extends Transferer<E>
/*      */   {
/*      */     static final int REQUEST = 0;
/*      */     
/*      */     static final int DATA = 1;
/*      */     
/*      */     static final int FULFILLING = 2;
/*      */     
/*      */     volatile SNode head;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long headOffset;
/*      */     
/*      */     static boolean isFulfilling(int param1Int) {
/*  229 */       return ((param1Int & 0x2) != 0);
/*      */     }
/*      */     
/*      */     static final class SNode {
/*      */       volatile SNode next;
/*      */       volatile SNode match;
/*      */       volatile Thread waiter;
/*      */       Object item;
/*      */       int mode;
/*      */       private static final Unsafe UNSAFE;
/*      */       private static final long matchOffset;
/*      */       private static final long nextOffset;
/*      */       
/*      */       SNode(Object param2Object) {
/*  243 */         this.item = param2Object;
/*      */       }
/*      */       
/*      */       boolean casNext(SNode param2SNode1, SNode param2SNode2) {
/*  247 */         return (param2SNode1 == this.next && UNSAFE
/*  248 */           .compareAndSwapObject(this, nextOffset, param2SNode1, param2SNode2));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       boolean tryMatch(SNode param2SNode) {
/*  260 */         if (this.match == null && UNSAFE
/*  261 */           .compareAndSwapObject(this, matchOffset, null, param2SNode)) {
/*  262 */           Thread thread = this.waiter;
/*  263 */           if (thread != null) {
/*  264 */             this.waiter = null;
/*  265 */             LockSupport.unpark(thread);
/*      */           } 
/*  267 */           return true;
/*      */         } 
/*  269 */         return (this.match == param2SNode);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void tryCancel() {
/*  276 */         UNSAFE.compareAndSwapObject(this, matchOffset, null, this);
/*      */       }
/*      */       
/*      */       boolean isCancelled() {
/*  280 */         return (this.match == this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       static {
/*      */         try {
/*  290 */           UNSAFE = Unsafe.getUnsafe();
/*  291 */           Class<SNode> clazz = SNode.class;
/*      */           
/*  293 */           matchOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("match"));
/*      */           
/*  295 */           nextOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
/*  296 */         } catch (Exception exception) {
/*  297 */           throw new Error(exception);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean casHead(SNode param1SNode1, SNode param1SNode2) {
/*  306 */       return (param1SNode1 == this.head && UNSAFE
/*  307 */         .compareAndSwapObject(this, headOffset, param1SNode1, param1SNode2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static SNode snode(SNode param1SNode1, Object param1Object, SNode param1SNode2, int param1Int) {
/*  318 */       if (param1SNode1 == null) param1SNode1 = new SNode(param1Object); 
/*  319 */       param1SNode1.mode = param1Int;
/*  320 */       param1SNode1.next = param1SNode2;
/*  321 */       return param1SNode1;
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
/*      */     E transfer(E param1E, boolean param1Boolean, long param1Long) {
/*  350 */       SNode sNode = null;
/*  351 */       boolean bool = (param1E == null) ? false : true;
/*      */       
/*      */       label60: while (true) {
/*  354 */         SNode sNode1 = this.head;
/*  355 */         if (sNode1 == null || sNode1.mode == bool) {
/*  356 */           if (param1Boolean && param1Long <= 0L) {
/*  357 */             if (sNode1 != null && sNode1.isCancelled()) {
/*  358 */               casHead(sNode1, sNode1.next); continue;
/*      */             } 
/*  360 */             return null;
/*  361 */           }  if (casHead(sNode1, sNode = snode(sNode, param1E, sNode1, bool))) {
/*  362 */             SNode sNode4 = awaitFulfill(sNode, param1Boolean, param1Long);
/*  363 */             if (sNode4 == sNode) {
/*  364 */               clean(sNode);
/*  365 */               return null;
/*      */             } 
/*  367 */             if ((sNode1 = this.head) != null && sNode1.next == sNode)
/*  368 */               casHead(sNode1, sNode.next); 
/*  369 */             return !bool ? (E)sNode4.item : (E)sNode.item;
/*      */           }  continue;
/*  371 */         }  if (!isFulfilling(sNode1.mode)) {
/*  372 */           if (sNode1.isCancelled()) {
/*  373 */             casHead(sNode1, sNode1.next); continue;
/*  374 */           }  if (casHead(sNode1, sNode = snode(sNode, param1E, sNode1, 0x2 | bool))) {
/*      */             while (true) {
/*  376 */               SNode sNode4 = sNode.next;
/*  377 */               if (sNode4 == null) {
/*  378 */                 casHead(sNode, null);
/*  379 */                 sNode = null;
/*      */                 continue label60;
/*      */               } 
/*  382 */               SNode sNode5 = sNode4.next;
/*  383 */               if (sNode4.tryMatch(sNode)) {
/*  384 */                 casHead(sNode, sNode5);
/*  385 */                 return !bool ? (E)sNode4.item : (E)sNode.item;
/*      */               } 
/*  387 */               sNode.casNext(sNode4, sNode5);
/*      */             }  break;
/*      */           }  continue;
/*      */         } 
/*  391 */         SNode sNode2 = sNode1.next;
/*  392 */         if (sNode2 == null) {
/*  393 */           casHead(sNode1, null); continue;
/*      */         } 
/*  395 */         SNode sNode3 = sNode2.next;
/*  396 */         if (sNode2.tryMatch(sNode1)) {
/*  397 */           casHead(sNode1, sNode3); continue;
/*      */         } 
/*  399 */         sNode1.casNext(sNode2, sNode3);
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
/*      */     SNode awaitFulfill(SNode param1SNode, boolean param1Boolean, long param1Long) {
/*  436 */       long l = param1Boolean ? (System.nanoTime() + param1Long) : 0L;
/*  437 */       Thread thread = Thread.currentThread();
/*  438 */       byte b = shouldSpin(param1SNode) ? (param1Boolean ? SynchronousQueue.maxTimedSpins : SynchronousQueue.maxUntimedSpins) : 0;
/*      */       
/*      */       while (true) {
/*  441 */         if (thread.isInterrupted())
/*  442 */           param1SNode.tryCancel(); 
/*  443 */         SNode sNode = param1SNode.match;
/*  444 */         if (sNode != null)
/*  445 */           return sNode; 
/*  446 */         if (param1Boolean) {
/*  447 */           param1Long = l - System.nanoTime();
/*  448 */           if (param1Long <= 0L) {
/*  449 */             param1SNode.tryCancel();
/*      */             continue;
/*      */           } 
/*      */         } 
/*  453 */         if (b) {
/*  454 */           b = shouldSpin(param1SNode) ? (b - 1) : 0; continue;
/*  455 */         }  if (param1SNode.waiter == null) {
/*  456 */           param1SNode.waiter = thread; continue;
/*  457 */         }  if (!param1Boolean) {
/*  458 */           LockSupport.park(this); continue;
/*  459 */         }  if (param1Long > 1000L) {
/*  460 */           LockSupport.parkNanos(this, param1Long);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean shouldSpin(SNode param1SNode) {
/*  469 */       SNode sNode = this.head;
/*  470 */       return (sNode == param1SNode || sNode == null || isFulfilling(sNode.mode));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clean(SNode param1SNode) {
/*  477 */       param1SNode.item = null;
/*  478 */       param1SNode.waiter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  491 */       SNode sNode1 = param1SNode.next;
/*  492 */       if (sNode1 != null && sNode1.isCancelled()) {
/*  493 */         sNode1 = sNode1.next;
/*      */       }
/*      */       
/*      */       SNode sNode2;
/*  497 */       while ((sNode2 = this.head) != null && sNode2 != sNode1 && sNode2.isCancelled()) {
/*  498 */         casHead(sNode2, sNode2.next);
/*      */       }
/*      */       
/*  501 */       while (sNode2 != null && sNode2 != sNode1) {
/*  502 */         SNode sNode = sNode2.next;
/*  503 */         if (sNode != null && sNode.isCancelled()) {
/*  504 */           sNode2.casNext(sNode, sNode.next); continue;
/*      */         } 
/*  506 */         sNode2 = sNode;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/*  515 */         UNSAFE = Unsafe.getUnsafe();
/*  516 */         Class<TransferStack> clazz = TransferStack.class;
/*      */         
/*  518 */         headOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
/*  519 */       } catch (Exception exception) {
/*  520 */         throw new Error(exception);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class TransferQueue<E> extends Transferer<E> {
/*      */     volatile transient QNode head;
/*      */     volatile transient QNode tail;
/*      */     volatile transient QNode cleanMe;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long headOffset;
/*      */     private static final long tailOffset;
/*      */     private static final long cleanMeOffset;
/*      */     
/*      */     static final class QNode {
/*      */       volatile QNode next;
/*      */       volatile Object item;
/*      */       volatile Thread waiter;
/*      */       final boolean isData;
/*      */       private static final Unsafe UNSAFE;
/*      */       private static final long itemOffset;
/*      */       private static final long nextOffset;
/*      */       
/*      */       QNode(Object param2Object, boolean param2Boolean) {
/*  544 */         this.item = param2Object;
/*  545 */         this.isData = param2Boolean;
/*      */       }
/*      */       
/*      */       boolean casNext(QNode param2QNode1, QNode param2QNode2) {
/*  549 */         return (this.next == param2QNode1 && UNSAFE
/*  550 */           .compareAndSwapObject(this, nextOffset, param2QNode1, param2QNode2));
/*      */       }
/*      */       
/*      */       boolean casItem(Object param2Object1, Object param2Object2) {
/*  554 */         return (this.item == param2Object1 && UNSAFE
/*  555 */           .compareAndSwapObject(this, itemOffset, param2Object1, param2Object2));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void tryCancel(Object param2Object) {
/*  562 */         UNSAFE.compareAndSwapObject(this, itemOffset, param2Object, this);
/*      */       }
/*      */       
/*      */       boolean isCancelled() {
/*  566 */         return (this.item == this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       boolean isOffList() {
/*  575 */         return (this.next == this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       static {
/*      */         try {
/*  585 */           UNSAFE = Unsafe.getUnsafe();
/*  586 */           Class<QNode> clazz = QNode.class;
/*      */           
/*  588 */           itemOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
/*      */           
/*  590 */           nextOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
/*  591 */         } catch (Exception exception) {
/*  592 */           throw new Error(exception);
/*      */         } 
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
/*      */     TransferQueue() {
/*  609 */       QNode qNode = new QNode(null, false);
/*  610 */       this.head = qNode;
/*  611 */       this.tail = qNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void advanceHead(QNode param1QNode1, QNode param1QNode2) {
/*  619 */       if (param1QNode1 == this.head && UNSAFE
/*  620 */         .compareAndSwapObject(this, headOffset, param1QNode1, param1QNode2)) {
/*  621 */         param1QNode1.next = param1QNode1;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void advanceTail(QNode param1QNode1, QNode param1QNode2) {
/*  628 */       if (this.tail == param1QNode1) {
/*  629 */         UNSAFE.compareAndSwapObject(this, tailOffset, param1QNode1, param1QNode2);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean casCleanMe(QNode param1QNode1, QNode param1QNode2) {
/*  636 */       return (this.cleanMe == param1QNode1 && UNSAFE
/*  637 */         .compareAndSwapObject(this, cleanMeOffset, param1QNode1, param1QNode2));
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
/*      */     E transfer(E param1E, boolean param1Boolean, long param1Long) {
/*      */       QNode qNode2, qNode3;
/*      */       Object object;
/*  670 */       QNode qNode1 = null;
/*  671 */       boolean bool = (param1E != null);
/*      */       
/*      */       while (true) {
/*  674 */         QNode qNode = this.tail;
/*  675 */         qNode2 = this.head;
/*  676 */         if (qNode == null || qNode2 == null) {
/*      */           continue;
/*      */         }
/*  679 */         if (qNode2 == qNode || qNode.isData == bool) {
/*  680 */           QNode qNode4 = qNode.next;
/*  681 */           if (qNode != this.tail)
/*      */             continue; 
/*  683 */           if (qNode4 != null) {
/*  684 */             advanceTail(qNode, qNode4);
/*      */             continue;
/*      */           } 
/*  687 */           if (param1Boolean && param1Long <= 0L)
/*  688 */             return null; 
/*  689 */           if (qNode1 == null)
/*  690 */             qNode1 = new QNode(param1E, bool); 
/*  691 */           if (!qNode.casNext(null, qNode1)) {
/*      */             continue;
/*      */           }
/*  694 */           advanceTail(qNode, qNode1);
/*  695 */           Object object1 = awaitFulfill(qNode1, param1E, param1Boolean, param1Long);
/*  696 */           if (object1 == qNode1) {
/*  697 */             clean(qNode, qNode1);
/*  698 */             return null;
/*      */           } 
/*      */           
/*  701 */           if (!qNode1.isOffList()) {
/*  702 */             advanceHead(qNode, qNode1);
/*  703 */             if (object1 != null)
/*  704 */               qNode1.item = qNode1; 
/*  705 */             qNode1.waiter = null;
/*      */           } 
/*  707 */           return (object1 != null) ? (E)object1 : param1E;
/*      */         } 
/*      */         
/*  710 */         qNode3 = qNode2.next;
/*  711 */         if (qNode != this.tail || qNode3 == null || qNode2 != this.head) {
/*      */           continue;
/*      */         }
/*  714 */         object = qNode3.item;
/*  715 */         if (bool == ((object != null)) || object == qNode3 || 
/*      */           
/*  717 */           !qNode3.casItem(object, param1E)) {
/*  718 */           advanceHead(qNode2, qNode3); continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*  722 */       advanceHead(qNode2, qNode3);
/*  723 */       LockSupport.unpark(qNode3.waiter);
/*  724 */       return (object != null) ? (E)object : param1E;
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
/*      */     Object awaitFulfill(QNode param1QNode, E param1E, boolean param1Boolean, long param1Long) {
/*  740 */       long l = param1Boolean ? (System.nanoTime() + param1Long) : 0L;
/*  741 */       Thread thread = Thread.currentThread();
/*  742 */       byte b = (this.head.next == param1QNode) ? (param1Boolean ? SynchronousQueue.maxTimedSpins : SynchronousQueue.maxUntimedSpins) : 0;
/*      */       
/*      */       while (true) {
/*  745 */         if (thread.isInterrupted())
/*  746 */           param1QNode.tryCancel(param1E); 
/*  747 */         Object object = param1QNode.item;
/*  748 */         if (object != param1E)
/*  749 */           return object; 
/*  750 */         if (param1Boolean) {
/*  751 */           param1Long = l - System.nanoTime();
/*  752 */           if (param1Long <= 0L) {
/*  753 */             param1QNode.tryCancel(param1E);
/*      */             continue;
/*      */           } 
/*      */         } 
/*  757 */         if (b) {
/*  758 */           b--; continue;
/*  759 */         }  if (param1QNode.waiter == null) {
/*  760 */           param1QNode.waiter = thread; continue;
/*  761 */         }  if (!param1Boolean) {
/*  762 */           LockSupport.park(this); continue;
/*  763 */         }  if (param1Long > 1000L) {
/*  764 */           LockSupport.parkNanos(this, param1Long);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void clean(QNode param1QNode1, QNode param1QNode2) {
/*  772 */       param1QNode2.waiter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  781 */       while (param1QNode1.next == param1QNode2) {
/*  782 */         QNode qNode1 = this.head;
/*  783 */         QNode qNode2 = qNode1.next;
/*  784 */         if (qNode2 != null && qNode2.isCancelled()) {
/*  785 */           advanceHead(qNode1, qNode2);
/*      */           continue;
/*      */         } 
/*  788 */         QNode qNode3 = this.tail;
/*  789 */         if (qNode3 == qNode1)
/*      */           return; 
/*  791 */         QNode qNode4 = qNode3.next;
/*  792 */         if (qNode3 != this.tail)
/*      */           continue; 
/*  794 */         if (qNode4 != null) {
/*  795 */           advanceTail(qNode3, qNode4);
/*      */           continue;
/*      */         } 
/*  798 */         if (param1QNode2 != qNode3) {
/*  799 */           QNode qNode = param1QNode2.next;
/*  800 */           if (qNode == param1QNode2 || param1QNode1.casNext(param1QNode2, qNode))
/*      */             return; 
/*      */         } 
/*  803 */         QNode qNode5 = this.cleanMe;
/*  804 */         if (qNode5 != null) {
/*  805 */           QNode qNode6 = qNode5.next;
/*      */           QNode qNode7;
/*  807 */           if (qNode6 == null || qNode6 == qNode5 || 
/*      */             
/*  809 */             !qNode6.isCancelled() || (qNode6 != qNode3 && (qNode7 = qNode6.next) != null && qNode7 != qNode6 && qNode5
/*      */ 
/*      */ 
/*      */             
/*  813 */             .casNext(qNode6, qNode7)))
/*  814 */             casCleanMe(qNode5, null); 
/*  815 */           if (qNode5 == param1QNode1)
/*      */             return;  continue;
/*  817 */         }  if (casCleanMe(null, param1QNode1)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/*  828 */         UNSAFE = Unsafe.getUnsafe();
/*  829 */         Class<TransferQueue> clazz = TransferQueue.class;
/*      */         
/*  831 */         headOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
/*      */         
/*  833 */         tailOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
/*      */         
/*  835 */         cleanMeOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("cleanMe"));
/*  836 */       } catch (Exception exception) {
/*  837 */         throw new Error(exception);
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
/*      */   public SynchronousQueue() {
/*  855 */     this(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SynchronousQueue(boolean paramBoolean) {
/*  865 */     this.transferer = paramBoolean ? new TransferQueue<>() : new TransferStack<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void put(E paramE) throws InterruptedException {
/*  876 */     if (paramE == null) throw new NullPointerException(); 
/*  877 */     if (this.transferer.transfer(paramE, false, 0L) == null) {
/*  878 */       Thread.interrupted();
/*  879 */       throw new InterruptedException();
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
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  894 */     if (paramE == null) throw new NullPointerException(); 
/*  895 */     if (this.transferer.transfer(paramE, true, paramTimeUnit.toNanos(paramLong)) != null)
/*  896 */       return true; 
/*  897 */     if (!Thread.interrupted())
/*  898 */       return false; 
/*  899 */     throw new InterruptedException();
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
/*      */   public boolean offer(E paramE) {
/*  912 */     if (paramE == null) throw new NullPointerException(); 
/*  913 */     return (this.transferer.transfer(paramE, true, 0L) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E take() throws InterruptedException {
/*  924 */     E e = this.transferer.transfer(null, false, 0L);
/*  925 */     if (e != null)
/*  926 */       return e; 
/*  927 */     Thread.interrupted();
/*  928 */     throw new InterruptedException();
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
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  941 */     E e = this.transferer.transfer(null, true, paramTimeUnit.toNanos(paramLong));
/*  942 */     if (e != null || !Thread.interrupted())
/*  943 */       return e; 
/*  944 */     throw new InterruptedException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E poll() {
/*  955 */     return this.transferer.transfer(null, true, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  965 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  975 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int remainingCapacity() {
/*  985 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(Object paramObject) {
/* 1003 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object paramObject) {
/* 1014 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsAll(Collection<?> paramCollection) {
/* 1025 */     return paramCollection.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeAll(Collection<?> paramCollection) {
/* 1036 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean retainAll(Collection<?> paramCollection) {
/* 1047 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E peek() {
/* 1058 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<E> iterator() {
/* 1068 */     return Collections.emptyIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Spliterator<E> spliterator() {
/* 1079 */     return Spliterators.emptySpliterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] toArray() {
/* 1087 */     return new Object[0];
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT) {
/* 1099 */     if (paramArrayOfT.length > 0)
/* 1100 */       paramArrayOfT[0] = null; 
/* 1101 */     return paramArrayOfT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection) {
/* 1111 */     if (paramCollection == null)
/* 1112 */       throw new NullPointerException(); 
/* 1113 */     if (paramCollection == this)
/* 1114 */       throw new IllegalArgumentException(); 
/* 1115 */     byte b = 0; E e;
/* 1116 */     while ((e = poll()) != null) {
/* 1117 */       paramCollection.add(e);
/* 1118 */       b++;
/*      */     } 
/* 1120 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt) {
/* 1130 */     if (paramCollection == null)
/* 1131 */       throw new NullPointerException(); 
/* 1132 */     if (paramCollection == this)
/* 1133 */       throw new IllegalArgumentException(); 
/* 1134 */     byte b = 0; E e;
/* 1135 */     while (b < paramInt && (e = poll()) != null) {
/* 1136 */       paramCollection.add(e);
/* 1137 */       b++;
/*      */     } 
/* 1139 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class WaitQueue
/*      */     implements Serializable {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class LifoWaitQueue
/*      */     extends WaitQueue
/*      */   {
/*      */     private static final long serialVersionUID = -3633113410248163686L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class FifoWaitQueue
/*      */     extends WaitQueue
/*      */   {
/*      */     private static final long serialVersionUID = -3623113410248163686L;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1169 */     boolean bool = this.transferer instanceof TransferQueue;
/* 1170 */     if (bool) {
/* 1171 */       this.qlock = new ReentrantLock(true);
/* 1172 */       this.waitingProducers = new FifoWaitQueue();
/* 1173 */       this.waitingConsumers = new FifoWaitQueue();
/*      */     } else {
/*      */       
/* 1176 */       this.qlock = new ReentrantLock();
/* 1177 */       this.waitingProducers = new LifoWaitQueue();
/* 1178 */       this.waitingConsumers = new LifoWaitQueue();
/*      */     } 
/* 1180 */     paramObjectOutputStream.defaultWriteObject();
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
/* 1192 */     paramObjectInputStream.defaultReadObject();
/* 1193 */     if (this.waitingProducers instanceof FifoWaitQueue) {
/* 1194 */       this.transferer = new TransferQueue<>();
/*      */     } else {
/* 1196 */       this.transferer = new TransferStack<>();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static long objectFieldOffset(Unsafe paramUnsafe, String paramString, Class<?> paramClass) {
/*      */     try {
/* 1203 */       return paramUnsafe.objectFieldOffset(paramClass.getDeclaredField(paramString));
/* 1204 */     } catch (NoSuchFieldException noSuchFieldException) {
/*      */       
/* 1206 */       NoSuchFieldError noSuchFieldError = new NoSuchFieldError(paramString);
/* 1207 */       noSuchFieldError.initCause(noSuchFieldException);
/* 1208 */       throw noSuchFieldError;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/SynchronousQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */