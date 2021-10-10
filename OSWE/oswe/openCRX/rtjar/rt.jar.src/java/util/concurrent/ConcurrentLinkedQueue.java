/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.Consumer;
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
/*     */ public class ConcurrentLinkedQueue<E>
/*     */   extends AbstractQueue<E>
/*     */   implements Queue<E>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 196745693267521676L;
/*     */   private volatile transient Node<E> head;
/*     */   private volatile transient Node<E> tail;
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final long headOffset;
/*     */   private static final long tailOffset;
/*     */   
/*     */   private static class Node<E>
/*     */   {
/*     */     volatile E item;
/*     */     volatile Node<E> next;
/*     */     private static final Unsafe UNSAFE;
/*     */     private static final long itemOffset;
/*     */     private static final long nextOffset;
/*     */     
/*     */     Node(E param1E) {
/* 189 */       UNSAFE.putObject(this, itemOffset, param1E);
/*     */     }
/*     */     
/*     */     boolean casItem(E param1E1, E param1E2) {
/* 193 */       return UNSAFE.compareAndSwapObject(this, itemOffset, param1E1, param1E2);
/*     */     }
/*     */     
/*     */     void lazySetNext(Node<E> param1Node) {
/* 197 */       UNSAFE.putOrderedObject(this, nextOffset, param1Node);
/*     */     }
/*     */     
/*     */     boolean casNext(Node<E> param1Node1, Node<E> param1Node2) {
/* 201 */       return UNSAFE.compareAndSwapObject(this, nextOffset, param1Node1, param1Node2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 212 */         UNSAFE = Unsafe.getUnsafe();
/* 213 */         Class<Node> clazz = Node.class;
/*     */         
/* 215 */         itemOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
/*     */         
/* 217 */         nextOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
/* 218 */       } catch (Exception exception) {
/* 219 */         throw new Error(exception);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConcurrentLinkedQueue() {
/* 256 */     this.head = this.tail = new Node<>(null);
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
/*     */   public ConcurrentLinkedQueue(Collection<? extends E> paramCollection) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokespecial <init> : ()V
/*     */     //   4: aconst_null
/*     */     //   5: astore_2
/*     */     //   6: aconst_null
/*     */     //   7: astore_3
/*     */     //   8: aload_1
/*     */     //   9: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   14: astore #4
/*     */     //   16: aload #4
/*     */     //   18: invokeinterface hasNext : ()Z
/*     */     //   23: ifeq -> 75
/*     */     //   26: aload #4
/*     */     //   28: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   33: astore #5
/*     */     //   35: aload #5
/*     */     //   37: invokestatic checkNotNull : (Ljava/lang/Object;)V
/*     */     //   40: new java/util/concurrent/ConcurrentLinkedQueue$Node
/*     */     //   43: dup
/*     */     //   44: aload #5
/*     */     //   46: invokespecial <init> : (Ljava/lang/Object;)V
/*     */     //   49: astore #6
/*     */     //   51: aload_2
/*     */     //   52: ifnonnull -> 63
/*     */     //   55: aload #6
/*     */     //   57: dup
/*     */     //   58: astore_3
/*     */     //   59: astore_2
/*     */     //   60: goto -> 72
/*     */     //   63: aload_3
/*     */     //   64: aload #6
/*     */     //   66: invokevirtual lazySetNext : (Ljava/util/concurrent/ConcurrentLinkedQueue$Node;)V
/*     */     //   69: aload #6
/*     */     //   71: astore_3
/*     */     //   72: goto -> 16
/*     */     //   75: aload_2
/*     */     //   76: ifnonnull -> 90
/*     */     //   79: new java/util/concurrent/ConcurrentLinkedQueue$Node
/*     */     //   82: dup
/*     */     //   83: aconst_null
/*     */     //   84: invokespecial <init> : (Ljava/lang/Object;)V
/*     */     //   87: dup
/*     */     //   88: astore_3
/*     */     //   89: astore_2
/*     */     //   90: aload_0
/*     */     //   91: aload_2
/*     */     //   92: putfield head : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   95: aload_0
/*     */     //   96: aload_3
/*     */     //   97: putfield tail : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   100: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #268	-> 0
/*     */     //   #269	-> 4
/*     */     //   #270	-> 8
/*     */     //   #271	-> 35
/*     */     //   #272	-> 40
/*     */     //   #273	-> 51
/*     */     //   #274	-> 55
/*     */     //   #276	-> 63
/*     */     //   #277	-> 69
/*     */     //   #279	-> 72
/*     */     //   #280	-> 75
/*     */     //   #281	-> 79
/*     */     //   #282	-> 90
/*     */     //   #283	-> 95
/*     */     //   #284	-> 100
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
/*     */   public boolean add(E paramE) {
/* 297 */     return offer(paramE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void updateHead(Node<E> paramNode1, Node<E> paramNode2) {
/* 305 */     if (paramNode1 != paramNode2 && casHead(paramNode1, paramNode2)) {
/* 306 */       paramNode1.lazySetNext(paramNode1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Node<E> succ(Node<E> paramNode) {
/* 315 */     Node<E> node = paramNode.next;
/* 316 */     return (paramNode == node) ? this.head : node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offer(E paramE) {
/* 327 */     checkNotNull(paramE);
/* 328 */     Node<E> node1 = new Node<>(paramE);
/*     */     
/* 330 */     Node<E> node2 = this.tail, node3 = node2; while (true) {
/* 331 */       Node<E> node = node3.next;
/* 332 */       if (node == null) {
/*     */         
/* 334 */         if (node3.casNext(null, node1)) {
/*     */ 
/*     */ 
/*     */           
/* 338 */           if (node3 != node2)
/* 339 */             casTail(node2, node1); 
/* 340 */           return true;
/*     */         } 
/*     */         continue;
/*     */       } 
/* 344 */       if (node3 == node) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 349 */         node3 = (node2 != (node2 = this.tail)) ? node2 : this.head;
/*     */         continue;
/*     */       } 
/* 352 */       node3 = (node3 != node2 && node2 != (node2 = this.tail)) ? node2 : node;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public E poll() {
/*     */     label21: while (true) {
/* 359 */       Node<E> node1 = this.head, node2 = node1; while (true) {
/* 360 */         E e = node2.item;
/*     */         
/* 362 */         if (e != null && node2.casItem(e, null)) {
/*     */ 
/*     */           
/* 365 */           if (node2 != node1) {
/* 366 */             Node<E> node3; updateHead(node1, ((node3 = node2.next) != null) ? node3 : node2);
/* 367 */           }  return e;
/*     */         }  Node<E> node;
/* 369 */         if ((node = node2.next) == null) {
/* 370 */           updateHead(node1, node2);
/* 371 */           return null;
/*     */         } 
/* 373 */         if (node2 == node) {
/*     */           continue label21;
/*     */         }
/* 376 */         node2 = node;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public E peek() {
/*     */     label13: while (true) {
/* 384 */       Node<E> node1 = this.head, node2 = node1; while (true) {
/* 385 */         E e = node2.item; Node<E> node;
/* 386 */         if (e != null || (node = node2.next) == null) {
/* 387 */           updateHead(node1, node2);
/* 388 */           return e;
/*     */         } 
/* 390 */         if (node2 == node) {
/*     */           continue label13;
/*     */         }
/* 393 */         node2 = node;
/*     */       } 
/*     */       break;
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
/*     */   Node<E> first() {
/*     */     label20: while (true) {
/* 409 */       Node<E> node1 = this.head, node2 = node1; while (true) {
/* 410 */         boolean bool = (node2.item != null) ? true : false; Node<E> node;
/* 411 */         if (bool || (node = node2.next) == null) {
/* 412 */           updateHead(node1, node2);
/* 413 */           return bool ? node2 : null;
/*     */         } 
/* 415 */         if (node2 == node) {
/*     */           continue label20;
/*     */         }
/* 418 */         node2 = node;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 429 */     return (first() == null);
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
/*     */   public int size() {
/* 449 */     byte b = 0;
/* 450 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 451 */       if (node.item != null)
/*     */       {
/* 453 */         if (++b == Integer.MAX_VALUE)
/*     */           break;  } 
/* 455 */     }  return b;
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
/*     */   public boolean contains(Object paramObject) {
/* 467 */     if (paramObject == null) return false; 
/* 468 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 469 */       E e = node.item;
/* 470 */       if (e != null && paramObject.equals(e))
/* 471 */         return true; 
/*     */     } 
/* 473 */     return false;
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
/*     */   public boolean remove(Object paramObject) {
/* 488 */     if (paramObject != null) {
/* 489 */       Node<E> node = null;
/* 490 */       Object object = first(); while (true) { if (object != null)
/* 491 */         { boolean bool = false;
/* 492 */           E e = ((Node)object).item;
/* 493 */           if (e != null)
/* 494 */           { if (!paramObject.equals(e))
/* 495 */             { Node<E> node2 = succ((Node<E>)object); }
/*     */             else
/*     */             
/* 498 */             { bool = object.casItem(e, null);
/*     */ 
/*     */               
/* 501 */               Node<E> node2 = succ((Node<E>)object); }  continue; }  } else { break; }  Node<E> node1 = succ((Node<E>)object);
/*     */         
/*     */         node = (Node<E>)object;
/*     */         
/*     */         object = SYNTHETIC_LOCAL_VARIABLE_2; }
/*     */     
/*     */     } 
/* 508 */     return false;
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
/*     */   public boolean addAll(Collection<? extends E> paramCollection) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: aload_0
/*     */     //   2: if_acmpne -> 13
/*     */     //   5: new java/lang/IllegalArgumentException
/*     */     //   8: dup
/*     */     //   9: invokespecial <init> : ()V
/*     */     //   12: athrow
/*     */     //   13: aconst_null
/*     */     //   14: astore_2
/*     */     //   15: aconst_null
/*     */     //   16: astore_3
/*     */     //   17: aload_1
/*     */     //   18: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   23: astore #4
/*     */     //   25: aload #4
/*     */     //   27: invokeinterface hasNext : ()Z
/*     */     //   32: ifeq -> 84
/*     */     //   35: aload #4
/*     */     //   37: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   42: astore #5
/*     */     //   44: aload #5
/*     */     //   46: invokestatic checkNotNull : (Ljava/lang/Object;)V
/*     */     //   49: new java/util/concurrent/ConcurrentLinkedQueue$Node
/*     */     //   52: dup
/*     */     //   53: aload #5
/*     */     //   55: invokespecial <init> : (Ljava/lang/Object;)V
/*     */     //   58: astore #6
/*     */     //   60: aload_2
/*     */     //   61: ifnonnull -> 72
/*     */     //   64: aload #6
/*     */     //   66: dup
/*     */     //   67: astore_3
/*     */     //   68: astore_2
/*     */     //   69: goto -> 81
/*     */     //   72: aload_3
/*     */     //   73: aload #6
/*     */     //   75: invokevirtual lazySetNext : (Ljava/util/concurrent/ConcurrentLinkedQueue$Node;)V
/*     */     //   78: aload #6
/*     */     //   80: astore_3
/*     */     //   81: goto -> 25
/*     */     //   84: aload_2
/*     */     //   85: ifnonnull -> 90
/*     */     //   88: iconst_0
/*     */     //   89: ireturn
/*     */     //   90: aload_0
/*     */     //   91: getfield tail : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   94: astore #4
/*     */     //   96: aload #4
/*     */     //   98: astore #5
/*     */     //   100: aload #5
/*     */     //   102: getfield next : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   105: astore #6
/*     */     //   107: aload #6
/*     */     //   109: ifnonnull -> 155
/*     */     //   112: aload #5
/*     */     //   114: aconst_null
/*     */     //   115: aload_2
/*     */     //   116: invokevirtual casNext : (Ljava/util/concurrent/ConcurrentLinkedQueue$Node;Ljava/util/concurrent/ConcurrentLinkedQueue$Node;)Z
/*     */     //   119: ifeq -> 216
/*     */     //   122: aload_0
/*     */     //   123: aload #4
/*     */     //   125: aload_3
/*     */     //   126: invokespecial casTail : (Ljava/util/concurrent/ConcurrentLinkedQueue$Node;Ljava/util/concurrent/ConcurrentLinkedQueue$Node;)Z
/*     */     //   129: ifne -> 153
/*     */     //   132: aload_0
/*     */     //   133: getfield tail : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   136: astore #4
/*     */     //   138: aload_3
/*     */     //   139: getfield next : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   142: ifnonnull -> 153
/*     */     //   145: aload_0
/*     */     //   146: aload #4
/*     */     //   148: aload_3
/*     */     //   149: invokespecial casTail : (Ljava/util/concurrent/ConcurrentLinkedQueue$Node;Ljava/util/concurrent/ConcurrentLinkedQueue$Node;)Z
/*     */     //   152: pop
/*     */     //   153: iconst_1
/*     */     //   154: ireturn
/*     */     //   155: aload #5
/*     */     //   157: aload #6
/*     */     //   159: if_acmpne -> 188
/*     */     //   162: aload #4
/*     */     //   164: aload_0
/*     */     //   165: getfield tail : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   168: dup
/*     */     //   169: astore #4
/*     */     //   171: if_acmpeq -> 179
/*     */     //   174: aload #4
/*     */     //   176: goto -> 183
/*     */     //   179: aload_0
/*     */     //   180: getfield head : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   183: astore #5
/*     */     //   185: goto -> 216
/*     */     //   188: aload #5
/*     */     //   190: aload #4
/*     */     //   192: if_acmpeq -> 212
/*     */     //   195: aload #4
/*     */     //   197: aload_0
/*     */     //   198: getfield tail : Ljava/util/concurrent/ConcurrentLinkedQueue$Node;
/*     */     //   201: dup
/*     */     //   202: astore #4
/*     */     //   204: if_acmpeq -> 212
/*     */     //   207: aload #4
/*     */     //   209: goto -> 214
/*     */     //   212: aload #6
/*     */     //   214: astore #5
/*     */     //   216: goto -> 100
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #524	-> 0
/*     */     //   #526	-> 5
/*     */     //   #529	-> 13
/*     */     //   #530	-> 17
/*     */     //   #531	-> 44
/*     */     //   #532	-> 49
/*     */     //   #533	-> 60
/*     */     //   #534	-> 64
/*     */     //   #536	-> 72
/*     */     //   #537	-> 78
/*     */     //   #539	-> 81
/*     */     //   #540	-> 84
/*     */     //   #541	-> 88
/*     */     //   #544	-> 90
/*     */     //   #545	-> 100
/*     */     //   #546	-> 107
/*     */     //   #548	-> 112
/*     */     //   #551	-> 122
/*     */     //   #554	-> 132
/*     */     //   #555	-> 138
/*     */     //   #556	-> 145
/*     */     //   #558	-> 153
/*     */     //   #562	-> 155
/*     */     //   #567	-> 162
/*     */     //   #570	-> 188
/*     */     //   #571	-> 216
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
/*     */   public Object[] toArray() {
/* 589 */     ArrayList<E> arrayList = new ArrayList();
/* 590 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 591 */       E e = node.item;
/* 592 */       if (e != null)
/* 593 */         arrayList.add(e); 
/*     */     } 
/* 595 */     return arrayList.toArray();
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
/*     */   public <T> T[] toArray(T[] paramArrayOfT) {
/* 636 */     byte b = 0;
/*     */     Node<E> node1;
/* 638 */     for (node1 = first(); node1 != null && b < paramArrayOfT.length; node1 = succ(node1)) {
/* 639 */       E e = node1.item;
/* 640 */       if (e != null)
/* 641 */         paramArrayOfT[b++] = (T)e; 
/*     */     } 
/* 643 */     if (node1 == null) {
/* 644 */       if (b < paramArrayOfT.length)
/* 645 */         paramArrayOfT[b] = null; 
/* 646 */       return paramArrayOfT;
/*     */     } 
/*     */ 
/*     */     
/* 650 */     ArrayList<E> arrayList = new ArrayList();
/* 651 */     for (Node<E> node2 = first(); node2 != null; node2 = succ(node2)) {
/* 652 */       E e = node2.item;
/* 653 */       if (e != null)
/* 654 */         arrayList.add(e); 
/*     */     } 
/* 656 */     return arrayList.toArray(paramArrayOfT);
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
/*     */   public Iterator<E> iterator() {
/* 669 */     return new Itr();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Itr
/*     */     implements Iterator<E>
/*     */   {
/*     */     private ConcurrentLinkedQueue.Node<E> nextNode;
/*     */ 
/*     */ 
/*     */     
/*     */     private E nextItem;
/*     */ 
/*     */ 
/*     */     
/*     */     private ConcurrentLinkedQueue.Node<E> lastRet;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Itr() {
/* 692 */       advance();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private E advance() {
/*     */       ConcurrentLinkedQueue.Node<E> node1, node2;
/* 700 */       this.lastRet = this.nextNode;
/* 701 */       E e = this.nextItem;
/*     */ 
/*     */       
/* 704 */       if (this.nextNode == null) {
/* 705 */         node2 = ConcurrentLinkedQueue.this.first();
/* 706 */         node1 = null;
/*     */       } else {
/* 708 */         node1 = this.nextNode;
/* 709 */         node2 = ConcurrentLinkedQueue.this.succ(this.nextNode);
/*     */       } 
/*     */       
/*     */       while (true) {
/* 713 */         if (node2 == null) {
/* 714 */           this.nextNode = null;
/* 715 */           this.nextItem = null;
/* 716 */           return e;
/*     */         } 
/* 718 */         E e1 = node2.item;
/* 719 */         if (e1 != null) {
/* 720 */           this.nextNode = node2;
/* 721 */           this.nextItem = e1;
/* 722 */           return e;
/*     */         } 
/*     */         
/* 725 */         ConcurrentLinkedQueue.Node<E> node = ConcurrentLinkedQueue.this.succ(node2);
/* 726 */         if (node1 != null && node != null)
/* 727 */           node1.casNext(node2, node); 
/* 728 */         node2 = node;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 734 */       return (this.nextNode != null);
/*     */     }
/*     */     
/*     */     public E next() {
/* 738 */       if (this.nextNode == null) throw new NoSuchElementException(); 
/* 739 */       return advance();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 743 */       ConcurrentLinkedQueue.Node<E> node = this.lastRet;
/* 744 */       if (node == null) throw new IllegalStateException();
/*     */       
/* 746 */       node.item = null;
/* 747 */       this.lastRet = null;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 763 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 766 */     for (Node<E> node = first(); node != null; node = succ(node)) {
/* 767 */       E e = node.item;
/* 768 */       if (e != null) {
/* 769 */         paramObjectOutputStream.writeObject(e);
/*     */       }
/*     */     } 
/*     */     
/* 773 */     paramObjectOutputStream.writeObject(null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 785 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 788 */     Node<E> node1 = null, node2 = null;
/*     */     Object object;
/* 790 */     while ((object = paramObjectInputStream.readObject()) != null) {
/*     */       
/* 792 */       Node<E> node = new Node(object);
/* 793 */       if (node1 == null) {
/* 794 */         node1 = node2 = node; continue;
/*     */       } 
/* 796 */       node2.lazySetNext(node);
/* 797 */       node2 = node;
/*     */     } 
/*     */     
/* 800 */     if (node1 == null)
/* 801 */       node1 = node2 = new Node(null); 
/* 802 */     this.head = node1;
/* 803 */     this.tail = node2;
/*     */   }
/*     */   
/*     */   static final class CLQSpliterator<E> implements Spliterator<E> {
/*     */     static final int MAX_BATCH = 33554432;
/*     */     final ConcurrentLinkedQueue<E> queue;
/*     */     ConcurrentLinkedQueue.Node<E> current;
/*     */     int batch;
/*     */     boolean exhausted;
/*     */     
/*     */     CLQSpliterator(ConcurrentLinkedQueue<E> param1ConcurrentLinkedQueue) {
/* 814 */       this.queue = param1ConcurrentLinkedQueue;
/*     */     }
/*     */ 
/*     */     
/*     */     public Spliterator<E> trySplit() {
/* 819 */       ConcurrentLinkedQueue<E> concurrentLinkedQueue = this.queue;
/* 820 */       int i = this.batch;
/* 821 */       byte b = (i <= 0) ? 1 : ((i >= 33554432) ? 33554432 : (i + 1)); ConcurrentLinkedQueue.Node<E> node;
/* 822 */       if (!this.exhausted && ((node = this.current) != null || (
/* 823 */         node = concurrentLinkedQueue.first()) != null) && node.next != null) {
/*     */         
/* 825 */         Object[] arrayOfObject = new Object[b];
/* 826 */         byte b1 = 0;
/*     */         do {
/* 828 */           arrayOfObject[b1] = node.item; if (node.item != null)
/* 829 */             b1++; 
/* 830 */           if (node != (node = node.next))
/* 831 */             continue;  node = concurrentLinkedQueue.first();
/* 832 */         } while (node != null && b1 < b);
/* 833 */         if ((this.current = node) == null)
/* 834 */           this.exhausted = true; 
/* 835 */         if (b1 > 0) {
/* 836 */           this.batch = b1;
/* 837 */           return 
/* 838 */             Spliterators.spliterator(arrayOfObject, 0, b1, 4368);
/*     */         } 
/*     */       } 
/*     */       
/* 842 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 847 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 848 */       ConcurrentLinkedQueue<E> concurrentLinkedQueue = this.queue; ConcurrentLinkedQueue.Node<E> node;
/* 849 */       if (!this.exhausted && ((node = this.current) != null || (
/* 850 */         node = concurrentLinkedQueue.first()) != null)) {
/* 851 */         this.exhausted = true;
/*     */         do {
/* 853 */           E e = node.item;
/* 854 */           if (node == (node = node.next))
/* 855 */             node = concurrentLinkedQueue.first(); 
/* 856 */           if (e == null)
/* 857 */             continue;  param1Consumer.accept(e);
/* 858 */         } while (node != null);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 864 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 865 */       ConcurrentLinkedQueue<E> concurrentLinkedQueue = this.queue; ConcurrentLinkedQueue.Node<E> node;
/* 866 */       if (!this.exhausted && ((node = this.current) != null || (
/* 867 */         node = concurrentLinkedQueue.first()) != null)) {
/*     */         E e;
/*     */         do {
/* 870 */           e = node.item;
/* 871 */           if (node != (node = node.next))
/* 872 */             continue;  node = concurrentLinkedQueue.first();
/* 873 */         } while (e == null && node != null);
/* 874 */         if ((this.current = node) == null)
/* 875 */           this.exhausted = true; 
/* 876 */         if (e != null) {
/* 877 */           param1Consumer.accept(e);
/* 878 */           return true;
/*     */         } 
/*     */       } 
/* 881 */       return false;
/*     */     }
/*     */     public long estimateSize() {
/* 884 */       return Long.MAX_VALUE;
/*     */     }
/*     */     public int characteristics() {
/* 887 */       return 4368;
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
/*     */ 
/*     */   
/*     */   public Spliterator<E> spliterator() {
/* 910 */     return new CLQSpliterator<>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNotNull(Object paramObject) {
/* 919 */     if (paramObject == null)
/* 920 */       throw new NullPointerException(); 
/*     */   }
/*     */   
/*     */   private boolean casTail(Node<E> paramNode1, Node<E> paramNode2) {
/* 924 */     return UNSAFE.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*     */   }
/*     */   
/*     */   private boolean casHead(Node<E> paramNode1, Node<E> paramNode2) {
/* 928 */     return UNSAFE.compareAndSwapObject(this, headOffset, paramNode1, paramNode2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 938 */       UNSAFE = Unsafe.getUnsafe();
/* 939 */       Class<ConcurrentLinkedQueue> clazz = ConcurrentLinkedQueue.class;
/*     */       
/* 941 */       headOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
/*     */       
/* 943 */       tailOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
/* 944 */     } catch (Exception exception) {
/* 945 */       throw new Error(exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ConcurrentLinkedQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */