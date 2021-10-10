/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
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
/*      */ public class ConcurrentLinkedDeque<E>
/*      */   extends AbstractCollection<E>
/*      */   implements Deque<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 876323262645176354L;
/*      */   private volatile transient Node<E> head;
/*      */   private volatile transient Node<E> tail;
/*      */   
/*      */   Node<E> prevTerminator() {
/*  282 */     return (Node)PREV_TERMINATOR;
/*      */   }
/*      */ 
/*      */   
/*      */   Node<E> nextTerminator() {
/*  287 */     return (Node)NEXT_TERMINATOR;
/*      */   }
/*      */   
/*      */   static final class Node<E>
/*      */   {
/*      */     volatile Node<E> prev;
/*      */     volatile E item;
/*      */     volatile Node<E> next;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long prevOffset;
/*      */     private static final long itemOffset;
/*      */     private static final long nextOffset;
/*      */     
/*      */     Node() {}
/*      */     
/*      */     Node(E param1E) {
/*  303 */       UNSAFE.putObject(this, itemOffset, param1E);
/*      */     }
/*      */     
/*      */     boolean casItem(E param1E1, E param1E2) {
/*  307 */       return UNSAFE.compareAndSwapObject(this, itemOffset, param1E1, param1E2);
/*      */     }
/*      */     
/*      */     void lazySetNext(Node<E> param1Node) {
/*  311 */       UNSAFE.putOrderedObject(this, nextOffset, param1Node);
/*      */     }
/*      */     
/*      */     boolean casNext(Node<E> param1Node1, Node<E> param1Node2) {
/*  315 */       return UNSAFE.compareAndSwapObject(this, nextOffset, param1Node1, param1Node2);
/*      */     }
/*      */     
/*      */     void lazySetPrev(Node<E> param1Node) {
/*  319 */       UNSAFE.putOrderedObject(this, prevOffset, param1Node);
/*      */     }
/*      */     
/*      */     boolean casPrev(Node<E> param1Node1, Node<E> param1Node2) {
/*  323 */       return UNSAFE.compareAndSwapObject(this, prevOffset, param1Node1, param1Node2);
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
/*  335 */         UNSAFE = Unsafe.getUnsafe();
/*  336 */         Class<Node> clazz = Node.class;
/*      */         
/*  338 */         prevOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("prev"));
/*      */         
/*  340 */         itemOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
/*      */         
/*  342 */         nextOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
/*  343 */       } catch (Exception exception) {
/*  344 */         throw new Error(exception);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void linkFirst(E paramE) {
/*      */     Node<E> node2, node3;
/*  353 */     checkNotNull(paramE);
/*  354 */     Node<E> node1 = new Node<>(paramE);
/*      */ 
/*      */     
/*      */     label22: while (true) {
/*  358 */       node3 = node2 = this.head; while (true) {
/*  359 */         Node<E> node; if ((node = node3.prev) != null && (node = (node3 = node).prev) != null) {
/*      */ 
/*      */ 
/*      */           
/*  363 */           node3 = (node2 != (node2 = this.head)) ? node2 : node; continue;
/*  364 */         }  if (node3.next == node3) {
/*      */           continue label22;
/*      */         }
/*      */         
/*  368 */         node1.lazySetNext(node3);
/*  369 */         if (node3.casPrev(null, node1))
/*      */           break; 
/*      */       }  break;
/*      */     } 
/*  373 */     if (node3 != node2) {
/*  374 */       casHead(node2, node1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void linkLast(E paramE) {
/*      */     Node<E> node2, node3;
/*  386 */     checkNotNull(paramE);
/*  387 */     Node<E> node1 = new Node<>(paramE);
/*      */ 
/*      */     
/*      */     label22: while (true) {
/*  391 */       node3 = node2 = this.tail; while (true) {
/*  392 */         Node<E> node; if ((node = node3.next) != null && (node = (node3 = node).next) != null) {
/*      */ 
/*      */ 
/*      */           
/*  396 */           node3 = (node2 != (node2 = this.tail)) ? node2 : node; continue;
/*  397 */         }  if (node3.prev == node3) {
/*      */           continue label22;
/*      */         }
/*      */         
/*  401 */         node1.lazySetPrev(node3);
/*  402 */         if (node3.casNext(null, node1))
/*      */           break; 
/*      */       }  break;
/*      */     } 
/*  406 */     if (node3 != node2) {
/*  407 */       casTail(node2, node1);
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
/*      */   void unlink(Node<E> paramNode) {
/*  426 */     Node<E> node1 = paramNode.prev;
/*  427 */     Node<E> node2 = paramNode.next;
/*  428 */     if (node1 == null) {
/*  429 */       unlinkFirst(paramNode, node2);
/*  430 */     } else if (node2 == null) {
/*  431 */       unlinkLast(paramNode, node1);
/*      */     } else {
/*      */       Node<E> node3, node4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       boolean bool1, bool2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  454 */       byte b = 1;
/*      */       
/*      */       Node<E> node5;
/*  457 */       for (node5 = node1;; b++) {
/*  458 */         if (node5.item != null) {
/*  459 */           node3 = node5;
/*  460 */           bool1 = false;
/*      */           break;
/*      */         } 
/*  463 */         Node<E> node = node5.prev;
/*  464 */         if (node == null) {
/*  465 */           if (node5.next == node5)
/*      */             return; 
/*  467 */           node3 = node5;
/*  468 */           bool1 = true;
/*      */           break;
/*      */         } 
/*  471 */         if (node5 == node) {
/*      */           return;
/*      */         }
/*  474 */         node5 = node;
/*      */       } 
/*      */ 
/*      */       
/*  478 */       for (node5 = node2;; b++) {
/*  479 */         if (node5.item != null) {
/*  480 */           node4 = node5;
/*  481 */           bool2 = false;
/*      */           break;
/*      */         } 
/*  484 */         Node<E> node = node5.next;
/*  485 */         if (node == null) {
/*  486 */           if (node5.prev == node5)
/*      */             return; 
/*  488 */           node4 = node5;
/*  489 */           bool2 = true;
/*      */           break;
/*      */         } 
/*  492 */         if (node5 == node) {
/*      */           return;
/*      */         }
/*  495 */         node5 = node;
/*      */       } 
/*      */ 
/*      */       
/*  499 */       if (b < 2 && (bool1 | bool2) != 0) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  506 */       skipDeletedSuccessors(node3);
/*  507 */       skipDeletedPredecessors(node4);
/*      */ 
/*      */       
/*  510 */       if ((bool1 | bool2) != 0 && node3.next == node4 && node4.prev == node3 && (bool1 ? (node3.prev == null) : (node3.item != null)) && (bool2 ? (node4.next == null) : (node4.item != null))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  518 */         updateHead();
/*  519 */         updateTail();
/*      */ 
/*      */         
/*  522 */         paramNode.lazySetPrev(bool1 ? prevTerminator() : paramNode);
/*  523 */         paramNode.lazySetNext(bool2 ? nextTerminator() : paramNode);
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
/*      */   private void unlinkFirst(Node<E> paramNode1, Node<E> paramNode2) {
/*  535 */     Node<E> node1 = null, node2 = paramNode2; while (true) {
/*  536 */       Node<E> node; if (node2.item != null || (node = node2.next) == null) {
/*  537 */         if (node1 != null && node2.prev != node2 && paramNode1.casNext(paramNode2, node2)) {
/*  538 */           skipDeletedPredecessors(node2);
/*  539 */           if (paramNode1.prev == null && (node2.next == null || node2.item != null) && node2.prev == paramNode1) {
/*      */ 
/*      */ 
/*      */             
/*  543 */             updateHead();
/*  544 */             updateTail();
/*      */ 
/*      */             
/*  547 */             node1.lazySetNext(node1);
/*  548 */             node1.lazySetPrev(prevTerminator());
/*      */           } 
/*      */         } 
/*      */         return;
/*      */       } 
/*  553 */       if (node2 == node) {
/*      */         return;
/*      */       }
/*  556 */       node1 = node2;
/*  557 */       node2 = node;
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
/*      */   private void unlinkLast(Node<E> paramNode1, Node<E> paramNode2) {
/*  569 */     Node<E> node1 = null, node2 = paramNode2; while (true) {
/*  570 */       Node<E> node; if (node2.item != null || (node = node2.prev) == null) {
/*  571 */         if (node1 != null && node2.next != node2 && paramNode1.casPrev(paramNode2, node2)) {
/*  572 */           skipDeletedSuccessors(node2);
/*  573 */           if (paramNode1.next == null && (node2.prev == null || node2.item != null) && node2.next == paramNode1) {
/*      */ 
/*      */ 
/*      */             
/*  577 */             updateHead();
/*  578 */             updateTail();
/*      */ 
/*      */             
/*  581 */             node1.lazySetPrev(node1);
/*  582 */             node1.lazySetNext(nextTerminator());
/*      */           } 
/*      */         } 
/*      */         return;
/*      */       } 
/*  587 */       if (node2 == node) {
/*      */         return;
/*      */       }
/*  590 */       node1 = node2;
/*  591 */       node2 = node;
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
/*      */   private final void updateHead() {
/*      */     Node<E> node1;
/*      */     Node<E> node2;
/*  607 */     label19: while ((node1 = this.head).item == null && (node2 = node1.prev) != null) {
/*      */       while (true) {
/*  609 */         Node<E> node; if ((node = node2.prev) == null || (node = (node2 = node).prev) == null) {
/*      */ 
/*      */ 
/*      */           
/*  613 */           if (casHead(node1, node2)) {
/*      */             return;
/*      */           }
/*      */           continue label19;
/*      */         } 
/*  618 */         if (node1 != this.head) {
/*      */           continue label19;
/*      */         }
/*  621 */         node2 = node;
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
/*      */   private final void updateTail() {
/*      */     Node<E> node1;
/*      */     Node<E> node2;
/*  637 */     label19: while ((node1 = this.tail).item == null && (node2 = node1.next) != null) {
/*      */       while (true) {
/*  639 */         Node<E> node; if ((node = node2.next) == null || (node = (node2 = node).next) == null) {
/*      */ 
/*      */ 
/*      */           
/*  643 */           if (casTail(node1, node2)) {
/*      */             return;
/*      */           }
/*      */           continue label19;
/*      */         } 
/*  648 */         if (node1 != this.tail) {
/*      */           continue label19;
/*      */         }
/*  651 */         node2 = node;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void skipDeletedPredecessors(Node<E> paramNode) {
/*      */     label21: do {
/*  659 */       Node<E> node1 = paramNode.prev;
/*      */ 
/*      */ 
/*      */       
/*  663 */       Node<E> node2 = node1;
/*      */ 
/*      */       
/*  666 */       while (node2.item == null) {
/*      */         
/*  668 */         Node<E> node = node2.prev;
/*  669 */         if (node == null) {
/*  670 */           if (node2.next == node2)
/*      */             continue label21; 
/*      */           break;
/*      */         } 
/*  674 */         if (node2 == node) {
/*      */           continue label21;
/*      */         }
/*  677 */         node2 = node;
/*      */       } 
/*      */ 
/*      */       
/*  681 */       if (node1 == node2 || paramNode.casPrev(node1, node2)) {
/*      */         return;
/*      */       }
/*  684 */     } while (paramNode.item != null || paramNode.next == null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void skipDeletedSuccessors(Node<E> paramNode) {
/*      */     label21: do {
/*  690 */       Node<E> node1 = paramNode.next;
/*      */ 
/*      */ 
/*      */       
/*  694 */       Node<E> node2 = node1;
/*      */ 
/*      */       
/*  697 */       while (node2.item == null) {
/*      */         
/*  699 */         Node<E> node = node2.next;
/*  700 */         if (node == null) {
/*  701 */           if (node2.prev == node2)
/*      */             continue label21; 
/*      */           break;
/*      */         } 
/*  705 */         if (node2 == node) {
/*      */           continue label21;
/*      */         }
/*  708 */         node2 = node;
/*      */       } 
/*      */ 
/*      */       
/*  712 */       if (node1 == node2 || paramNode.casNext(node1, node2)) {
/*      */         return;
/*      */       }
/*  715 */     } while (paramNode.item != null || paramNode.prev == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node<E> succ(Node<E> paramNode) {
/*  725 */     Node<E> node = paramNode.next;
/*  726 */     return (paramNode == node) ? first() : node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node<E> pred(Node<E> paramNode) {
/*  735 */     Node<E> node = paramNode.prev;
/*  736 */     return (paramNode == node) ? last() : node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Node<E> first() {
/*      */     Node<E> node1;
/*      */     Node<E> node2;
/*      */     do {
/*  748 */       node2 = node1 = this.head; Node<E> node;
/*  749 */       while ((node = node2.prev) != null && (node = (node2 = node).prev) != null)
/*      */       {
/*      */ 
/*      */         
/*  753 */         node2 = (node1 != (node1 = this.head)) ? node1 : node; } 
/*  754 */     } while (node2 != node1 && 
/*      */ 
/*      */       
/*  757 */       !casHead(node1, node2));
/*  758 */     return node2;
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
/*      */   Node<E> last() {
/*      */     Node<E> node1;
/*      */     Node<E> node2;
/*      */     do {
/*  773 */       node2 = node1 = this.tail; Node<E> node;
/*  774 */       while ((node = node2.next) != null && (node = (node2 = node).next) != null)
/*      */       {
/*      */ 
/*      */         
/*  778 */         node2 = (node1 != (node1 = this.tail)) ? node1 : node; } 
/*  779 */     } while (node2 != node1 && 
/*      */ 
/*      */       
/*  782 */       !casTail(node1, node2));
/*  783 */     return node2;
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
/*      */   private static void checkNotNull(Object paramObject) {
/*  797 */     if (paramObject == null) {
/*  798 */       throw new NullPointerException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E screenNullResult(E paramE) {
/*  809 */     if (paramE == null)
/*  810 */       throw new NoSuchElementException(); 
/*  811 */     return paramE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList<E> toArrayList() {
/*  821 */     ArrayList<E> arrayList = new ArrayList();
/*  822 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/*  823 */       E e = node.item;
/*  824 */       if (e != null)
/*  825 */         arrayList.add(e); 
/*      */     } 
/*  827 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentLinkedDeque() {
/*  834 */     this.head = this.tail = new Node<>(null);
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
/*      */   public ConcurrentLinkedDeque(Collection<? extends E> paramCollection) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokespecial <init> : ()V
/*      */     //   4: aconst_null
/*      */     //   5: astore_2
/*      */     //   6: aconst_null
/*      */     //   7: astore_3
/*      */     //   8: aload_1
/*      */     //   9: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   14: astore #4
/*      */     //   16: aload #4
/*      */     //   18: invokeinterface hasNext : ()Z
/*      */     //   23: ifeq -> 81
/*      */     //   26: aload #4
/*      */     //   28: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   33: astore #5
/*      */     //   35: aload #5
/*      */     //   37: invokestatic checkNotNull : (Ljava/lang/Object;)V
/*      */     //   40: new java/util/concurrent/ConcurrentLinkedDeque$Node
/*      */     //   43: dup
/*      */     //   44: aload #5
/*      */     //   46: invokespecial <init> : (Ljava/lang/Object;)V
/*      */     //   49: astore #6
/*      */     //   51: aload_2
/*      */     //   52: ifnonnull -> 63
/*      */     //   55: aload #6
/*      */     //   57: dup
/*      */     //   58: astore_3
/*      */     //   59: astore_2
/*      */     //   60: goto -> 78
/*      */     //   63: aload_3
/*      */     //   64: aload #6
/*      */     //   66: invokevirtual lazySetNext : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)V
/*      */     //   69: aload #6
/*      */     //   71: aload_3
/*      */     //   72: invokevirtual lazySetPrev : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)V
/*      */     //   75: aload #6
/*      */     //   77: astore_3
/*      */     //   78: goto -> 16
/*      */     //   81: aload_0
/*      */     //   82: aload_2
/*      */     //   83: aload_3
/*      */     //   84: invokespecial initHeadTail : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)V
/*      */     //   87: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #846	-> 0
/*      */     //   #848	-> 4
/*      */     //   #849	-> 8
/*      */     //   #850	-> 35
/*      */     //   #851	-> 40
/*      */     //   #852	-> 51
/*      */     //   #853	-> 55
/*      */     //   #855	-> 63
/*      */     //   #856	-> 69
/*      */     //   #857	-> 75
/*      */     //   #859	-> 78
/*      */     //   #860	-> 81
/*      */     //   #861	-> 87
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
/*      */   private void initHeadTail(Node<E> paramNode1, Node<E> paramNode2) {
/*  867 */     if (paramNode1 == paramNode2) {
/*  868 */       if (paramNode1 == null) {
/*  869 */         paramNode1 = paramNode2 = new Node<>(null);
/*      */       } else {
/*      */         
/*  872 */         Node<E> node = new Node(null);
/*  873 */         paramNode2.lazySetNext(node);
/*  874 */         node.lazySetPrev(paramNode2);
/*  875 */         paramNode2 = node;
/*      */       } 
/*      */     }
/*  878 */     this.head = paramNode1;
/*  879 */     this.tail = paramNode2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFirst(E paramE) {
/*  890 */     linkFirst(paramE);
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
/*      */   public void addLast(E paramE) {
/*  903 */     linkLast(paramE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offerFirst(E paramE) {
/*  914 */     linkFirst(paramE);
/*  915 */     return true;
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
/*      */   public boolean offerLast(E paramE) {
/*  928 */     linkLast(paramE);
/*  929 */     return true;
/*      */   }
/*      */   
/*      */   public E peekFirst() {
/*  933 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/*  934 */       E e = node.item;
/*  935 */       if (e != null)
/*  936 */         return e; 
/*      */     } 
/*  938 */     return null;
/*      */   }
/*      */   
/*      */   public E peekLast() {
/*  942 */     for (Node<E> node = last(); node != null; node = pred(node)) {
/*  943 */       E e = node.item;
/*  944 */       if (e != null)
/*  945 */         return e; 
/*      */     } 
/*  947 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E getFirst() {
/*  954 */     return screenNullResult(peekFirst());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E getLast() {
/*  961 */     return screenNullResult(peekLast());
/*      */   }
/*      */   
/*      */   public E pollFirst() {
/*  965 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/*  966 */       E e = node.item;
/*  967 */       if (e != null && node.casItem(e, null)) {
/*  968 */         unlink(node);
/*  969 */         return e;
/*      */       } 
/*      */     } 
/*  972 */     return null;
/*      */   }
/*      */   
/*      */   public E pollLast() {
/*  976 */     for (Node<E> node = last(); node != null; node = pred(node)) {
/*  977 */       E e = node.item;
/*  978 */       if (e != null && node.casItem(e, null)) {
/*  979 */         unlink(node);
/*  980 */         return e;
/*      */       } 
/*      */     } 
/*  983 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E removeFirst() {
/*  990 */     return screenNullResult(pollFirst());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E removeLast() {
/*  997 */     return screenNullResult(pollLast());
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
/* 1010 */     return offerLast(paramE);
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
/* 1022 */     return offerLast(paramE);
/*      */   }
/*      */   
/* 1025 */   public E poll() { return pollFirst(); } public E peek() {
/* 1026 */     return peekFirst();
/*      */   }
/*      */ 
/*      */   
/*      */   public E remove() {
/* 1031 */     return removeFirst();
/*      */   }
/*      */ 
/*      */   
/*      */   public E pop() {
/* 1036 */     return removeFirst();
/*      */   }
/*      */ 
/*      */   
/*      */   public E element() {
/* 1041 */     return getFirst();
/*      */   }
/*      */ 
/*      */   
/*      */   public void push(E paramE) {
/* 1046 */     addFirst(paramE);
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
/*      */   public boolean removeFirstOccurrence(Object paramObject) {
/* 1058 */     checkNotNull(paramObject);
/* 1059 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 1060 */       E e = node.item;
/* 1061 */       if (e != null && paramObject.equals(e) && node.casItem(e, null)) {
/* 1062 */         unlink(node);
/* 1063 */         return true;
/*      */       } 
/*      */     } 
/* 1066 */     return false;
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
/*      */   public boolean removeLastOccurrence(Object paramObject) {
/* 1079 */     checkNotNull(paramObject);
/* 1080 */     for (Node<E> node = last(); node != null; node = pred(node)) {
/* 1081 */       E e = node.item;
/* 1082 */       if (e != null && paramObject.equals(e) && node.casItem(e, null)) {
/* 1083 */         unlink(node);
/* 1084 */         return true;
/*      */       } 
/*      */     } 
/* 1087 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(Object paramObject) {
/* 1098 */     if (paramObject == null) return false; 
/* 1099 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 1100 */       E e = node.item;
/* 1101 */       if (e != null && paramObject.equals(e))
/* 1102 */         return true; 
/*      */     } 
/* 1104 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1113 */     return (peekFirst() == null);
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
/*      */   public int size() {
/* 1133 */     byte b = 0;
/* 1134 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 1135 */       if (node.item != null)
/*      */       {
/* 1137 */         if (++b == Integer.MAX_VALUE)
/*      */           break;  } 
/* 1139 */     }  return b;
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
/*      */   public boolean remove(Object paramObject) {
/* 1152 */     return removeFirstOccurrence(paramObject);
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
/*      */   public boolean addAll(Collection<? extends E> paramCollection) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: aload_0
/*      */     //   2: if_acmpne -> 13
/*      */     //   5: new java/lang/IllegalArgumentException
/*      */     //   8: dup
/*      */     //   9: invokespecial <init> : ()V
/*      */     //   12: athrow
/*      */     //   13: aconst_null
/*      */     //   14: astore_2
/*      */     //   15: aconst_null
/*      */     //   16: astore_3
/*      */     //   17: aload_1
/*      */     //   18: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   23: astore #4
/*      */     //   25: aload #4
/*      */     //   27: invokeinterface hasNext : ()Z
/*      */     //   32: ifeq -> 90
/*      */     //   35: aload #4
/*      */     //   37: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   42: astore #5
/*      */     //   44: aload #5
/*      */     //   46: invokestatic checkNotNull : (Ljava/lang/Object;)V
/*      */     //   49: new java/util/concurrent/ConcurrentLinkedDeque$Node
/*      */     //   52: dup
/*      */     //   53: aload #5
/*      */     //   55: invokespecial <init> : (Ljava/lang/Object;)V
/*      */     //   58: astore #6
/*      */     //   60: aload_2
/*      */     //   61: ifnonnull -> 72
/*      */     //   64: aload #6
/*      */     //   66: dup
/*      */     //   67: astore_3
/*      */     //   68: astore_2
/*      */     //   69: goto -> 87
/*      */     //   72: aload_3
/*      */     //   73: aload #6
/*      */     //   75: invokevirtual lazySetNext : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)V
/*      */     //   78: aload #6
/*      */     //   80: aload_3
/*      */     //   81: invokevirtual lazySetPrev : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)V
/*      */     //   84: aload #6
/*      */     //   86: astore_3
/*      */     //   87: goto -> 25
/*      */     //   90: aload_2
/*      */     //   91: ifnonnull -> 96
/*      */     //   94: iconst_0
/*      */     //   95: ireturn
/*      */     //   96: aload_0
/*      */     //   97: getfield tail : Ljava/util/concurrent/ConcurrentLinkedDeque$Node;
/*      */     //   100: astore #4
/*      */     //   102: aload #4
/*      */     //   104: astore #5
/*      */     //   106: aload #5
/*      */     //   108: getfield next : Ljava/util/concurrent/ConcurrentLinkedDeque$Node;
/*      */     //   111: dup
/*      */     //   112: astore #6
/*      */     //   114: ifnull -> 155
/*      */     //   117: aload #6
/*      */     //   119: dup
/*      */     //   120: astore #5
/*      */     //   122: getfield next : Ljava/util/concurrent/ConcurrentLinkedDeque$Node;
/*      */     //   125: dup
/*      */     //   126: astore #6
/*      */     //   128: ifnull -> 155
/*      */     //   131: aload #4
/*      */     //   133: aload_0
/*      */     //   134: getfield tail : Ljava/util/concurrent/ConcurrentLinkedDeque$Node;
/*      */     //   137: dup
/*      */     //   138: astore #4
/*      */     //   140: if_acmpeq -> 148
/*      */     //   143: aload #4
/*      */     //   145: goto -> 150
/*      */     //   148: aload #6
/*      */     //   150: astore #5
/*      */     //   152: goto -> 106
/*      */     //   155: aload #5
/*      */     //   157: getfield prev : Ljava/util/concurrent/ConcurrentLinkedDeque$Node;
/*      */     //   160: aload #5
/*      */     //   162: if_acmpne -> 168
/*      */     //   165: goto -> 96
/*      */     //   168: aload_2
/*      */     //   169: aload #5
/*      */     //   171: invokevirtual lazySetPrev : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)V
/*      */     //   174: aload #5
/*      */     //   176: aconst_null
/*      */     //   177: aload_2
/*      */     //   178: invokevirtual casNext : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)Z
/*      */     //   181: ifeq -> 106
/*      */     //   184: aload_0
/*      */     //   185: aload #4
/*      */     //   187: aload_3
/*      */     //   188: invokespecial casTail : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)Z
/*      */     //   191: ifne -> 215
/*      */     //   194: aload_0
/*      */     //   195: getfield tail : Ljava/util/concurrent/ConcurrentLinkedDeque$Node;
/*      */     //   198: astore #4
/*      */     //   200: aload_3
/*      */     //   201: getfield next : Ljava/util/concurrent/ConcurrentLinkedDeque$Node;
/*      */     //   204: ifnonnull -> 215
/*      */     //   207: aload_0
/*      */     //   208: aload #4
/*      */     //   210: aload_3
/*      */     //   211: invokespecial casTail : (Ljava/util/concurrent/ConcurrentLinkedDeque$Node;Ljava/util/concurrent/ConcurrentLinkedDeque$Node;)Z
/*      */     //   214: pop
/*      */     //   215: iconst_1
/*      */     //   216: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1168	-> 0
/*      */     //   #1170	-> 5
/*      */     //   #1173	-> 13
/*      */     //   #1174	-> 17
/*      */     //   #1175	-> 44
/*      */     //   #1176	-> 49
/*      */     //   #1177	-> 60
/*      */     //   #1178	-> 64
/*      */     //   #1180	-> 72
/*      */     //   #1181	-> 78
/*      */     //   #1182	-> 84
/*      */     //   #1184	-> 87
/*      */     //   #1185	-> 90
/*      */     //   #1186	-> 94
/*      */     //   #1191	-> 96
/*      */     //   #1192	-> 106
/*      */     //   #1196	-> 131
/*      */     //   #1197	-> 155
/*      */     //   #1198	-> 165
/*      */     //   #1201	-> 168
/*      */     //   #1202	-> 174
/*      */     //   #1205	-> 184
/*      */     //   #1208	-> 194
/*      */     //   #1209	-> 200
/*      */     //   #1210	-> 207
/*      */     //   #1212	-> 215
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
/*      */   public void clear() {
/* 1223 */     while (pollFirst() != null);
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
/*      */   public Object[] toArray() {
/* 1241 */     return toArrayList().toArray();
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT) {
/* 1282 */     return toArrayList().toArray(paramArrayOfT);
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
/* 1295 */     return new Itr();
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
/*      */   public Iterator<E> descendingIterator() {
/* 1309 */     return new DescendingItr();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class AbstractItr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private ConcurrentLinkedDeque.Node<E> nextNode;
/*      */ 
/*      */     
/*      */     private E nextItem;
/*      */ 
/*      */     
/*      */     private ConcurrentLinkedDeque.Node<E> lastRet;
/*      */ 
/*      */ 
/*      */     
/*      */     abstract ConcurrentLinkedDeque.Node<E> startNode();
/*      */ 
/*      */ 
/*      */     
/*      */     abstract ConcurrentLinkedDeque.Node<E> nextNode(ConcurrentLinkedDeque.Node<E> param1Node);
/*      */ 
/*      */ 
/*      */     
/*      */     AbstractItr() {
/* 1336 */       advance();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void advance() {
/* 1344 */       this.lastRet = this.nextNode;
/*      */       
/* 1346 */       ConcurrentLinkedDeque.Node<E> node = (this.nextNode == null) ? startNode() : nextNode(this.nextNode);
/* 1347 */       for (;; node = nextNode(node)) {
/* 1348 */         if (node == null) {
/*      */           
/* 1350 */           this.nextNode = null;
/* 1351 */           this.nextItem = null;
/*      */           break;
/*      */         } 
/* 1354 */         E e = node.item;
/* 1355 */         if (e != null) {
/* 1356 */           this.nextNode = node;
/* 1357 */           this.nextItem = e;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1364 */       return (this.nextItem != null);
/*      */     }
/*      */     
/*      */     public E next() {
/* 1368 */       E e = this.nextItem;
/* 1369 */       if (e == null) throw new NoSuchElementException(); 
/* 1370 */       advance();
/* 1371 */       return e;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1375 */       ConcurrentLinkedDeque.Node<E> node = this.lastRet;
/* 1376 */       if (node == null) throw new IllegalStateException(); 
/* 1377 */       node.item = null;
/* 1378 */       ConcurrentLinkedDeque.this.unlink(node);
/* 1379 */       this.lastRet = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private class Itr extends AbstractItr { private Itr() {}
/*      */     
/* 1385 */     ConcurrentLinkedDeque.Node<E> startNode() { return ConcurrentLinkedDeque.this.first(); } ConcurrentLinkedDeque.Node<E> nextNode(ConcurrentLinkedDeque.Node<E> param1Node) {
/* 1386 */       return ConcurrentLinkedDeque.this.succ(param1Node);
/*      */     } }
/*      */   
/*      */   private class DescendingItr extends AbstractItr { private DescendingItr() {}
/*      */     
/* 1391 */     ConcurrentLinkedDeque.Node<E> startNode() { return ConcurrentLinkedDeque.this.last(); } ConcurrentLinkedDeque.Node<E> nextNode(ConcurrentLinkedDeque.Node<E> param1Node) {
/* 1392 */       return ConcurrentLinkedDeque.this.pred(param1Node);
/*      */     } }
/*      */   
/*      */   static final class CLDSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final ConcurrentLinkedDeque<E> queue;
/*      */     ConcurrentLinkedDeque.Node<E> current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     
/*      */     CLDSpliterator(ConcurrentLinkedDeque<E> param1ConcurrentLinkedDeque) {
/* 1403 */       this.queue = param1ConcurrentLinkedDeque;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<E> trySplit() {
/* 1408 */       ConcurrentLinkedDeque<E> concurrentLinkedDeque = this.queue;
/* 1409 */       int i = this.batch;
/* 1410 */       byte b = (i <= 0) ? 1 : ((i >= 33554432) ? 33554432 : (i + 1)); ConcurrentLinkedDeque.Node<E> node;
/* 1411 */       if (!this.exhausted && ((node = this.current) != null || (
/* 1412 */         node = concurrentLinkedDeque.first()) != null)) {
/* 1413 */         if (node.item == null && node == (node = node.next))
/* 1414 */           this.current = node = concurrentLinkedDeque.first(); 
/* 1415 */         if (node != null && node.next != null) {
/* 1416 */           Object[] arrayOfObject = new Object[b];
/* 1417 */           byte b1 = 0;
/*      */           do {
/* 1419 */             arrayOfObject[b1] = node.item; if (node.item != null)
/* 1420 */               b1++; 
/* 1421 */             if (node != (node = node.next))
/* 1422 */               continue;  node = concurrentLinkedDeque.first();
/* 1423 */           } while (node != null && b1 < b);
/* 1424 */           if ((this.current = node) == null)
/* 1425 */             this.exhausted = true; 
/* 1426 */           if (b1 > 0) {
/* 1427 */             this.batch = b1;
/* 1428 */             return 
/* 1429 */               Spliterators.spliterator(arrayOfObject, 0, b1, 4368);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1434 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1439 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 1440 */       ConcurrentLinkedDeque<E> concurrentLinkedDeque = this.queue; ConcurrentLinkedDeque.Node<E> node;
/* 1441 */       if (!this.exhausted && ((node = this.current) != null || (
/* 1442 */         node = concurrentLinkedDeque.first()) != null)) {
/* 1443 */         this.exhausted = true;
/*      */         do {
/* 1445 */           E e = node.item;
/* 1446 */           if (node == (node = node.next))
/* 1447 */             node = concurrentLinkedDeque.first(); 
/* 1448 */           if (e == null)
/* 1449 */             continue;  param1Consumer.accept(e);
/* 1450 */         } while (node != null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 1456 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 1457 */       ConcurrentLinkedDeque<E> concurrentLinkedDeque = this.queue; ConcurrentLinkedDeque.Node<E> node;
/* 1458 */       if (!this.exhausted && ((node = this.current) != null || (
/* 1459 */         node = concurrentLinkedDeque.first()) != null)) {
/*      */         E e;
/*      */         do {
/* 1462 */           e = node.item;
/* 1463 */           if (node != (node = node.next))
/* 1464 */             continue;  node = concurrentLinkedDeque.first();
/* 1465 */         } while (e == null && node != null);
/* 1466 */         if ((this.current = node) == null)
/* 1467 */           this.exhausted = true; 
/* 1468 */         if (e != null) {
/* 1469 */           param1Consumer.accept(e);
/* 1470 */           return true;
/*      */         } 
/*      */       } 
/* 1473 */       return false;
/*      */     }
/*      */     public long estimateSize() {
/* 1476 */       return Long.MAX_VALUE;
/*      */     }
/*      */     public int characteristics() {
/* 1479 */       return 4368;
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
/* 1501 */     return new CLDSpliterator<>(this);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1516 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */     
/* 1519 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 1520 */       E e = node.item;
/* 1521 */       if (e != null) {
/* 1522 */         paramObjectOutputStream.writeObject(e);
/*      */       }
/*      */     } 
/*      */     
/* 1526 */     paramObjectOutputStream.writeObject(null);
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
/* 1538 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/* 1541 */     Node<E> node1 = null, node2 = null;
/*      */     Object object;
/* 1543 */     while ((object = paramObjectInputStream.readObject()) != null) {
/*      */       
/* 1545 */       Node<E> node = new Node(object);
/* 1546 */       if (node1 == null) {
/* 1547 */         node1 = node2 = node; continue;
/*      */       } 
/* 1549 */       node2.lazySetNext(node);
/* 1550 */       node.lazySetPrev(node2);
/* 1551 */       node2 = node;
/*      */     } 
/*      */     
/* 1554 */     initHeadTail(node1, node2);
/*      */   }
/*      */   
/*      */   private boolean casHead(Node<E> paramNode1, Node<E> paramNode2) {
/* 1558 */     return UNSAFE.compareAndSwapObject(this, headOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   private boolean casTail(Node<E> paramNode1, Node<E> paramNode2) {
/* 1562 */     return UNSAFE.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1571 */   private static final Node<Object> PREV_TERMINATOR = new Node(); static {
/* 1572 */     PREV_TERMINATOR.next = PREV_TERMINATOR;
/* 1573 */   } private static final Node<Object> NEXT_TERMINATOR = new Node(); private static final int HOPS = 2; static {
/* 1574 */     NEXT_TERMINATOR.prev = NEXT_TERMINATOR;
/*      */     try {
/* 1576 */       UNSAFE = Unsafe.getUnsafe();
/* 1577 */       Class<ConcurrentLinkedDeque> clazz = ConcurrentLinkedDeque.class;
/*      */       
/* 1579 */       headOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
/*      */       
/* 1581 */       tailOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
/* 1582 */     } catch (Exception exception) {
/* 1583 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long headOffset;
/*      */   private static final long tailOffset;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ConcurrentLinkedDeque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */