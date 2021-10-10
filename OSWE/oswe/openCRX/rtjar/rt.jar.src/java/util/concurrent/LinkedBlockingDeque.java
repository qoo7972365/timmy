/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LinkedBlockingDeque<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingDeque<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -387911632671998426L;
/*      */   transient Node<E> first;
/*      */   transient Node<E> last;
/*      */   private transient int count;
/*      */   private final int capacity;
/*      */   
/*      */   static final class Node<E>
/*      */   {
/*      */     E item;
/*      */     Node<E> prev;
/*      */     Node<E> next;
/*      */     
/*      */     Node(E param1E) {
/*  133 */       this.item = param1E;
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
/*  158 */   final ReentrantLock lock = new ReentrantLock();
/*      */ 
/*      */   
/*  161 */   private final Condition notEmpty = this.lock.newCondition();
/*      */ 
/*      */   
/*  164 */   private final Condition notFull = this.lock.newCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedBlockingDeque() {
/*  171 */     this(2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedBlockingDeque(int paramInt) {
/*  181 */     if (paramInt <= 0) throw new IllegalArgumentException(); 
/*  182 */     this.capacity = paramInt;
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
/*      */   public LinkedBlockingDeque(Collection<? extends E> paramCollection) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: ldc 2147483647
/*      */     //   3: invokespecial <init> : (I)V
/*      */     //   6: aload_0
/*      */     //   7: getfield lock : Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   10: astore_2
/*      */     //   11: aload_2
/*      */     //   12: invokevirtual lock : ()V
/*      */     //   15: aload_1
/*      */     //   16: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   21: astore_3
/*      */     //   22: aload_3
/*      */     //   23: invokeinterface hasNext : ()Z
/*      */     //   28: ifeq -> 81
/*      */     //   31: aload_3
/*      */     //   32: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   37: astore #4
/*      */     //   39: aload #4
/*      */     //   41: ifnonnull -> 52
/*      */     //   44: new java/lang/NullPointerException
/*      */     //   47: dup
/*      */     //   48: invokespecial <init> : ()V
/*      */     //   51: athrow
/*      */     //   52: aload_0
/*      */     //   53: new java/util/concurrent/LinkedBlockingDeque$Node
/*      */     //   56: dup
/*      */     //   57: aload #4
/*      */     //   59: invokespecial <init> : (Ljava/lang/Object;)V
/*      */     //   62: invokespecial linkLast : (Ljava/util/concurrent/LinkedBlockingDeque$Node;)Z
/*      */     //   65: ifne -> 78
/*      */     //   68: new java/lang/IllegalStateException
/*      */     //   71: dup
/*      */     //   72: ldc 'Deque full'
/*      */     //   74: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   77: athrow
/*      */     //   78: goto -> 22
/*      */     //   81: aload_2
/*      */     //   82: invokevirtual unlock : ()V
/*      */     //   85: goto -> 97
/*      */     //   88: astore #5
/*      */     //   90: aload_2
/*      */     //   91: invokevirtual unlock : ()V
/*      */     //   94: aload #5
/*      */     //   96: athrow
/*      */     //   97: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #196	-> 0
/*      */     //   #197	-> 6
/*      */     //   #198	-> 11
/*      */     //   #200	-> 15
/*      */     //   #201	-> 39
/*      */     //   #202	-> 44
/*      */     //   #203	-> 52
/*      */     //   #204	-> 68
/*      */     //   #205	-> 78
/*      */     //   #207	-> 81
/*      */     //   #208	-> 85
/*      */     //   #207	-> 88
/*      */     //   #208	-> 94
/*      */     //   #209	-> 97
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   15	81	88	finally
/*      */     //   88	90	88	finally
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
/*      */   private boolean linkFirst(Node<E> paramNode) {
/*  219 */     if (this.count >= this.capacity)
/*  220 */       return false; 
/*  221 */     Node<E> node = this.first;
/*  222 */     paramNode.next = node;
/*  223 */     this.first = paramNode;
/*  224 */     if (this.last == null) {
/*  225 */       this.last = paramNode;
/*      */     } else {
/*  227 */       node.prev = paramNode;
/*  228 */     }  this.count++;
/*  229 */     this.notEmpty.signal();
/*  230 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean linkLast(Node<E> paramNode) {
/*  238 */     if (this.count >= this.capacity)
/*  239 */       return false; 
/*  240 */     Node<E> node = this.last;
/*  241 */     paramNode.prev = node;
/*  242 */     this.last = paramNode;
/*  243 */     if (this.first == null) {
/*  244 */       this.first = paramNode;
/*      */     } else {
/*  246 */       node.next = paramNode;
/*  247 */     }  this.count++;
/*  248 */     this.notEmpty.signal();
/*  249 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E unlinkFirst() {
/*  257 */     Node<E> node1 = this.first;
/*  258 */     if (node1 == null)
/*  259 */       return null; 
/*  260 */     Node<E> node2 = node1.next;
/*  261 */     E e = node1.item;
/*  262 */     node1.item = null;
/*  263 */     node1.next = node1;
/*  264 */     this.first = node2;
/*  265 */     if (node2 == null) {
/*  266 */       this.last = null;
/*      */     } else {
/*  268 */       node2.prev = null;
/*  269 */     }  this.count--;
/*  270 */     this.notFull.signal();
/*  271 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E unlinkLast() {
/*  279 */     Node<E> node1 = this.last;
/*  280 */     if (node1 == null)
/*  281 */       return null; 
/*  282 */     Node<E> node2 = node1.prev;
/*  283 */     E e = node1.item;
/*  284 */     node1.item = null;
/*  285 */     node1.prev = node1;
/*  286 */     this.last = node2;
/*  287 */     if (node2 == null) {
/*  288 */       this.first = null;
/*      */     } else {
/*  290 */       node2.next = null;
/*  291 */     }  this.count--;
/*  292 */     this.notFull.signal();
/*  293 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void unlink(Node<E> paramNode) {
/*  301 */     Node<E> node1 = paramNode.prev;
/*  302 */     Node<E> node2 = paramNode.next;
/*  303 */     if (node1 == null) {
/*  304 */       unlinkFirst();
/*  305 */     } else if (node2 == null) {
/*  306 */       unlinkLast();
/*      */     } else {
/*  308 */       node1.next = node2;
/*  309 */       node2.prev = node1;
/*  310 */       paramNode.item = null;
/*      */ 
/*      */       
/*  313 */       this.count--;
/*  314 */       this.notFull.signal();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFirst(E paramE) {
/*  325 */     if (!offerFirst(paramE)) {
/*  326 */       throw new IllegalStateException("Deque full");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLast(E paramE) {
/*  334 */     if (!offerLast(paramE)) {
/*  335 */       throw new IllegalStateException("Deque full");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offerFirst(E paramE) {
/*  342 */     if (paramE == null) throw new NullPointerException(); 
/*  343 */     Node<E> node = new Node<>(paramE);
/*  344 */     ReentrantLock reentrantLock = this.lock;
/*  345 */     reentrantLock.lock();
/*      */     try {
/*  347 */       return linkFirst(node);
/*      */     } finally {
/*  349 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offerLast(E paramE) {
/*  357 */     if (paramE == null) throw new NullPointerException(); 
/*  358 */     Node<E> node = new Node<>(paramE);
/*  359 */     ReentrantLock reentrantLock = this.lock;
/*  360 */     reentrantLock.lock();
/*      */     try {
/*  362 */       return linkLast(node);
/*      */     } finally {
/*  364 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putFirst(E paramE) throws InterruptedException {
/*  373 */     if (paramE == null) throw new NullPointerException(); 
/*  374 */     Node<E> node = new Node<>(paramE);
/*  375 */     ReentrantLock reentrantLock = this.lock;
/*  376 */     reentrantLock.lock();
/*      */     try {
/*  378 */       while (!linkFirst(node))
/*  379 */         this.notFull.await(); 
/*      */     } finally {
/*  381 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putLast(E paramE) throws InterruptedException {
/*  390 */     if (paramE == null) throw new NullPointerException(); 
/*  391 */     Node<E> node = new Node<>(paramE);
/*  392 */     ReentrantLock reentrantLock = this.lock;
/*  393 */     reentrantLock.lock();
/*      */     try {
/*  395 */       while (!linkLast(node))
/*  396 */         this.notFull.await(); 
/*      */     } finally {
/*  398 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offerFirst(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  408 */     if (paramE == null) throw new NullPointerException(); 
/*  409 */     Node<E> node = new Node<>(paramE);
/*  410 */     long l = paramTimeUnit.toNanos(paramLong);
/*  411 */     ReentrantLock reentrantLock = this.lock;
/*  412 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  414 */       while (!linkFirst(node)) {
/*  415 */         if (l <= 0L)
/*  416 */           return false; 
/*  417 */         l = this.notFull.awaitNanos(l);
/*      */       } 
/*  419 */       return true;
/*      */     } finally {
/*  421 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offerLast(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  431 */     if (paramE == null) throw new NullPointerException(); 
/*  432 */     Node<E> node = new Node<>(paramE);
/*  433 */     long l = paramTimeUnit.toNanos(paramLong);
/*  434 */     ReentrantLock reentrantLock = this.lock;
/*  435 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  437 */       while (!linkLast(node)) {
/*  438 */         if (l <= 0L)
/*  439 */           return false; 
/*  440 */         l = this.notFull.awaitNanos(l);
/*      */       } 
/*  442 */       return true;
/*      */     } finally {
/*  444 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E removeFirst() {
/*  452 */     E e = pollFirst();
/*  453 */     if (e == null) throw new NoSuchElementException(); 
/*  454 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E removeLast() {
/*  461 */     E e = pollLast();
/*  462 */     if (e == null) throw new NoSuchElementException(); 
/*  463 */     return e;
/*      */   }
/*      */   
/*      */   public E pollFirst() {
/*  467 */     ReentrantLock reentrantLock = this.lock;
/*  468 */     reentrantLock.lock();
/*      */     try {
/*  470 */       return unlinkFirst();
/*      */     } finally {
/*  472 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E pollLast() {
/*  477 */     ReentrantLock reentrantLock = this.lock;
/*  478 */     reentrantLock.lock();
/*      */     try {
/*  480 */       return unlinkLast();
/*      */     } finally {
/*  482 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E takeFirst() throws InterruptedException {
/*  487 */     ReentrantLock reentrantLock = this.lock;
/*  488 */     reentrantLock.lock();
/*      */     try {
/*      */       E e;
/*  491 */       while ((e = unlinkFirst()) == null)
/*  492 */         this.notEmpty.await(); 
/*  493 */       return e;
/*      */     } finally {
/*  495 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E takeLast() throws InterruptedException {
/*  500 */     ReentrantLock reentrantLock = this.lock;
/*  501 */     reentrantLock.lock();
/*      */     try {
/*      */       E e;
/*  504 */       while ((e = unlinkLast()) == null)
/*  505 */         this.notEmpty.await(); 
/*  506 */       return e;
/*      */     } finally {
/*  508 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public E pollFirst(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  514 */     long l = paramTimeUnit.toNanos(paramLong);
/*  515 */     ReentrantLock reentrantLock = this.lock;
/*  516 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*      */       E e;
/*  519 */       while ((e = unlinkFirst()) == null) {
/*  520 */         if (l <= 0L)
/*  521 */           return null; 
/*  522 */         l = this.notEmpty.awaitNanos(l);
/*      */       } 
/*  524 */       return e;
/*      */     } finally {
/*  526 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public E pollLast(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  532 */     long l = paramTimeUnit.toNanos(paramLong);
/*  533 */     ReentrantLock reentrantLock = this.lock;
/*  534 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*      */       E e;
/*  537 */       while ((e = unlinkLast()) == null) {
/*  538 */         if (l <= 0L)
/*  539 */           return null; 
/*  540 */         l = this.notEmpty.awaitNanos(l);
/*      */       } 
/*  542 */       return e;
/*      */     } finally {
/*  544 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E getFirst() {
/*  552 */     E e = peekFirst();
/*  553 */     if (e == null) throw new NoSuchElementException(); 
/*  554 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E getLast() {
/*  561 */     E e = peekLast();
/*  562 */     if (e == null) throw new NoSuchElementException(); 
/*  563 */     return e;
/*      */   }
/*      */   
/*      */   public E peekFirst() {
/*  567 */     ReentrantLock reentrantLock = this.lock;
/*  568 */     reentrantLock.lock();
/*      */     try {
/*  570 */       return (this.first == null) ? null : this.first.item;
/*      */     } finally {
/*  572 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E peekLast() {
/*  577 */     ReentrantLock reentrantLock = this.lock;
/*  578 */     reentrantLock.lock();
/*      */     try {
/*  580 */       return (this.last == null) ? null : this.last.item;
/*      */     } finally {
/*  582 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean removeFirstOccurrence(Object paramObject) {
/*  587 */     if (paramObject == null) return false; 
/*  588 */     ReentrantLock reentrantLock = this.lock;
/*  589 */     reentrantLock.lock();
/*      */     try {
/*  591 */       for (Node<E> node = this.first; node != null; node = node.next) {
/*  592 */         if (paramObject.equals(node.item)) {
/*  593 */           unlink(node);
/*  594 */           return true;
/*      */         } 
/*      */       } 
/*  597 */       return false;
/*      */     } finally {
/*  599 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean removeLastOccurrence(Object paramObject) {
/*  604 */     if (paramObject == null) return false; 
/*  605 */     ReentrantLock reentrantLock = this.lock;
/*  606 */     reentrantLock.lock();
/*      */     try {
/*  608 */       for (Node<E> node = this.last; node != null; node = node.prev) {
/*  609 */         if (paramObject.equals(node.item)) {
/*  610 */           unlink(node);
/*  611 */           return true;
/*      */         } 
/*      */       } 
/*  614 */       return false;
/*      */     } finally {
/*  616 */       reentrantLock.unlock();
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
/*      */   public boolean add(E paramE) {
/*  633 */     addLast(paramE);
/*  634 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offer(E paramE) {
/*  641 */     return offerLast(paramE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void put(E paramE) throws InterruptedException {
/*  649 */     putLast(paramE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  658 */     return offerLast(paramE, paramLong, paramTimeUnit);
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
/*      */   public E remove() {
/*  672 */     return removeFirst();
/*      */   }
/*      */   
/*      */   public E poll() {
/*  676 */     return pollFirst();
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException {
/*  680 */     return takeFirst();
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  684 */     return pollFirst(paramLong, paramTimeUnit);
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
/*      */   public E element() {
/*  698 */     return getFirst();
/*      */   }
/*      */   
/*      */   public E peek() {
/*  702 */     return peekFirst();
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
/*      */   public int remainingCapacity() {
/*  717 */     ReentrantLock reentrantLock = this.lock;
/*  718 */     reentrantLock.lock();
/*      */     try {
/*  720 */       return this.capacity - this.count;
/*      */     } finally {
/*  722 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection) {
/*  733 */     return drainTo(paramCollection, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt) {
/*  743 */     if (paramCollection == null)
/*  744 */       throw new NullPointerException(); 
/*  745 */     if (paramCollection == this)
/*  746 */       throw new IllegalArgumentException(); 
/*  747 */     if (paramInt <= 0)
/*  748 */       return 0; 
/*  749 */     ReentrantLock reentrantLock = this.lock;
/*  750 */     reentrantLock.lock();
/*      */     try {
/*  752 */       int i = Math.min(paramInt, this.count); int j;
/*  753 */       for (j = 0; j < i; j++) {
/*  754 */         paramCollection.add(this.first.item);
/*  755 */         unlinkFirst();
/*      */       } 
/*  757 */       j = i; return j;
/*      */     } finally {
/*  759 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(E paramE) {
/*  770 */     addFirst(paramE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E pop() {
/*  777 */     return removeFirst();
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
/*      */   public boolean remove(Object paramObject) {
/*  797 */     return removeFirstOccurrence(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  806 */     ReentrantLock reentrantLock = this.lock;
/*  807 */     reentrantLock.lock();
/*      */     try {
/*  809 */       return this.count;
/*      */     } finally {
/*  811 */       reentrantLock.unlock();
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
/*      */   public boolean contains(Object paramObject) {
/*  824 */     if (paramObject == null) return false; 
/*  825 */     ReentrantLock reentrantLock = this.lock;
/*  826 */     reentrantLock.lock();
/*      */     try {
/*  828 */       for (Node<E> node = this.first; node != null; node = node.next) {
/*  829 */         if (paramObject.equals(node.item))
/*  830 */           return true; 
/*  831 */       }  return false;
/*      */     } finally {
/*  833 */       reentrantLock.unlock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  893 */     ReentrantLock reentrantLock = this.lock;
/*  894 */     reentrantLock.lock();
/*      */     try {
/*  896 */       Object[] arrayOfObject = new Object[this.count];
/*  897 */       byte b = 0;
/*  898 */       for (Node<E> node = this.first; node != null; node = node.next)
/*  899 */         arrayOfObject[b++] = node.item; 
/*  900 */       return arrayOfObject;
/*      */     } finally {
/*  902 */       reentrantLock.unlock();
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
/*  943 */     ReentrantLock reentrantLock = this.lock;
/*  944 */     reentrantLock.lock();
/*      */     try {
/*  946 */       if (paramArrayOfT.length < this.count)
/*      */       {
/*  948 */         paramArrayOfT = (T[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.count);
/*      */       }
/*  950 */       byte b = 0;
/*  951 */       for (Node<E> node = this.first; node != null; node = node.next)
/*  952 */         paramArrayOfT[b++] = (T)node.item; 
/*  953 */       if (paramArrayOfT.length > b)
/*  954 */         paramArrayOfT[b] = null; 
/*  955 */       return paramArrayOfT;
/*      */     } finally {
/*  957 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public String toString() {
/*  962 */     ReentrantLock reentrantLock = this.lock;
/*  963 */     reentrantLock.lock();
/*      */     try {
/*  965 */       Node<E> node = this.first;
/*  966 */       if (node == null) {
/*  967 */         return "[]";
/*      */       }
/*  969 */       StringBuilder stringBuilder = new StringBuilder();
/*  970 */       stringBuilder.append('[');
/*      */       while (true) {
/*  972 */         E e = node.item;
/*  973 */         stringBuilder.append((e == this) ? "(this Collection)" : e);
/*  974 */         node = node.next;
/*  975 */         if (node == null)
/*  976 */           return stringBuilder.append(']').toString(); 
/*  977 */         stringBuilder.append(',').append(' ');
/*      */       } 
/*      */     } finally {
/*  980 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  989 */     ReentrantLock reentrantLock = this.lock;
/*  990 */     reentrantLock.lock();
/*      */     try {
/*  992 */       for (Node<E> node = this.first; node != null; ) {
/*  993 */         node.item = null;
/*  994 */         Node<E> node1 = node.next;
/*  995 */         node.prev = null;
/*  996 */         node.next = null;
/*  997 */         node = node1;
/*      */       } 
/*  999 */       this.first = this.last = null;
/* 1000 */       this.count = 0;
/* 1001 */       this.notFull.signalAll();
/*      */     } finally {
/* 1003 */       reentrantLock.unlock();
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
/*      */   public Iterator<E> iterator() {
/* 1017 */     return new Itr();
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
/* 1031 */     return new DescendingItr();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class AbstractItr
/*      */     implements Iterator<E>
/*      */   {
/*      */     LinkedBlockingDeque.Node<E> next;
/*      */ 
/*      */ 
/*      */     
/*      */     E nextItem;
/*      */ 
/*      */ 
/*      */     
/*      */     private LinkedBlockingDeque.Node<E> lastRet;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract LinkedBlockingDeque.Node<E> firstNode();
/*      */ 
/*      */ 
/*      */     
/*      */     abstract LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> param1Node);
/*      */ 
/*      */ 
/*      */     
/*      */     AbstractItr() {
/* 1062 */       ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
/* 1063 */       reentrantLock.lock();
/*      */       try {
/* 1065 */         this.next = firstNode();
/* 1066 */         this.nextItem = (this.next == null) ? null : this.next.item;
/*      */       } finally {
/* 1068 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private LinkedBlockingDeque.Node<E> succ(LinkedBlockingDeque.Node<E> param1Node) {
/*      */       while (true) {
/* 1080 */         LinkedBlockingDeque.Node<E> node = nextNode(param1Node);
/* 1081 */         if (node == null)
/* 1082 */           return null; 
/* 1083 */         if (node.item != null)
/* 1084 */           return node; 
/* 1085 */         if (node == param1Node) {
/* 1086 */           return firstNode();
/*      */         }
/* 1088 */         param1Node = node;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void advance() {
/* 1096 */       ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
/* 1097 */       reentrantLock.lock();
/*      */       
/*      */       try {
/* 1100 */         this.next = succ(this.next);
/* 1101 */         this.nextItem = (this.next == null) ? null : this.next.item;
/*      */       } finally {
/* 1103 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1108 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public E next() {
/* 1112 */       if (this.next == null)
/* 1113 */         throw new NoSuchElementException(); 
/* 1114 */       this.lastRet = this.next;
/* 1115 */       E e = this.nextItem;
/* 1116 */       advance();
/* 1117 */       return e;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1121 */       LinkedBlockingDeque.Node<E> node = this.lastRet;
/* 1122 */       if (node == null)
/* 1123 */         throw new IllegalStateException(); 
/* 1124 */       this.lastRet = null;
/* 1125 */       ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
/* 1126 */       reentrantLock.lock();
/*      */       try {
/* 1128 */         if (node.item != null)
/* 1129 */           LinkedBlockingDeque.this.unlink(node); 
/*      */       } finally {
/* 1131 */         reentrantLock.unlock();
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class Itr extends AbstractItr {
/*      */     private Itr() {}
/*      */     
/* 1138 */     LinkedBlockingDeque.Node<E> firstNode() { return LinkedBlockingDeque.this.first; } LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> param1Node) {
/* 1139 */       return param1Node.next;
/*      */     }
/*      */   }
/*      */   private class DescendingItr extends AbstractItr { private DescendingItr() {}
/*      */     
/* 1144 */     LinkedBlockingDeque.Node<E> firstNode() { return LinkedBlockingDeque.this.last; } LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> param1Node) {
/* 1145 */       return param1Node.prev;
/*      */     } }
/*      */   
/*      */   static final class LBDSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedBlockingDeque<E> queue;
/*      */     LinkedBlockingDeque.Node<E> current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     long est;
/*      */     
/*      */     LBDSpliterator(LinkedBlockingDeque<E> param1LinkedBlockingDeque) {
/* 1157 */       this.queue = param1LinkedBlockingDeque;
/* 1158 */       this.est = param1LinkedBlockingDeque.size();
/*      */     }
/*      */     public long estimateSize() {
/* 1161 */       return this.est;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit() {
/* 1165 */       LinkedBlockingDeque<E> linkedBlockingDeque = this.queue;
/* 1166 */       int i = this.batch;
/* 1167 */       byte b = (i <= 0) ? 1 : ((i >= 33554432) ? 33554432 : (i + 1)); LinkedBlockingDeque.Node<E> node;
/* 1168 */       if (!this.exhausted && ((node = this.current) != null || (node = linkedBlockingDeque.first) != null) && node.next != null) {
/*      */ 
/*      */         
/* 1171 */         Object[] arrayOfObject = new Object[b];
/* 1172 */         ReentrantLock reentrantLock = linkedBlockingDeque.lock;
/* 1173 */         byte b1 = 0;
/* 1174 */         LinkedBlockingDeque.Node<E> node1 = this.current;
/* 1175 */         reentrantLock.lock();
/*      */         try {
/* 1177 */           if (node1 != null || (node1 = linkedBlockingDeque.first) != null) {
/*      */             do {
/* 1179 */               arrayOfObject[b1] = node1.item; if (node1.item == null)
/* 1180 */                 continue;  b1++;
/* 1181 */             } while ((node1 = node1.next) != null && b1 < b);
/*      */           }
/*      */         } finally {
/* 1184 */           reentrantLock.unlock();
/*      */         } 
/* 1186 */         if ((this.current = node1) == null) {
/* 1187 */           this.est = 0L;
/* 1188 */           this.exhausted = true;
/*      */         }
/* 1190 */         else if ((this.est -= b1) < 0L) {
/* 1191 */           this.est = 0L;
/* 1192 */         }  if (b1 > 0) {
/* 1193 */           this.batch = b1;
/* 1194 */           return 
/* 1195 */             Spliterators.spliterator(arrayOfObject, 0, b1, 4368);
/*      */         } 
/*      */       } 
/*      */       
/* 1199 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1203 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 1204 */       LinkedBlockingDeque<E> linkedBlockingDeque = this.queue;
/* 1205 */       ReentrantLock reentrantLock = linkedBlockingDeque.lock;
/* 1206 */       if (!this.exhausted) {
/* 1207 */         this.exhausted = true;
/* 1208 */         LinkedBlockingDeque.Node<E> node = this.current; do {
/*      */           E e;
/* 1210 */           Object object = null;
/* 1211 */           reentrantLock.lock();
/*      */           try {
/* 1213 */             if (node == null)
/* 1214 */               node = linkedBlockingDeque.first; 
/* 1215 */             while (node != null) {
/* 1216 */               e = node.item;
/* 1217 */               node = node.next;
/* 1218 */               if (e != null)
/*      */                 break; 
/*      */             } 
/*      */           } finally {
/* 1222 */             reentrantLock.unlock();
/*      */           } 
/* 1224 */           if (e == null)
/* 1225 */             continue;  param1Consumer.accept(e);
/* 1226 */         } while (node != null);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 1231 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 1232 */       LinkedBlockingDeque<E> linkedBlockingDeque = this.queue;
/* 1233 */       ReentrantLock reentrantLock = linkedBlockingDeque.lock;
/* 1234 */       if (!this.exhausted) {
/* 1235 */         E e; Object object = null;
/* 1236 */         reentrantLock.lock();
/*      */         try {
/* 1238 */           if (this.current == null)
/* 1239 */             this.current = linkedBlockingDeque.first; 
/* 1240 */           while (this.current != null) {
/* 1241 */             e = this.current.item;
/* 1242 */             this.current = this.current.next;
/* 1243 */             if (e != null)
/*      */               break; 
/*      */           } 
/*      */         } finally {
/* 1247 */           reentrantLock.unlock();
/*      */         } 
/* 1249 */         if (this.current == null)
/* 1250 */           this.exhausted = true; 
/* 1251 */         if (e != null) {
/* 1252 */           param1Consumer.accept(e);
/* 1253 */           return true;
/*      */         } 
/*      */       } 
/* 1256 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1260 */       return 4368;
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
/* 1282 */     return new LBDSpliterator<>(this);
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
/* 1295 */     ReentrantLock reentrantLock = this.lock;
/* 1296 */     reentrantLock.lock();
/*      */     
/*      */     try {
/* 1299 */       paramObjectOutputStream.defaultWriteObject();
/*      */       
/* 1301 */       for (Node<E> node = this.first; node != null; node = node.next) {
/* 1302 */         paramObjectOutputStream.writeObject(node.item);
/*      */       }
/* 1304 */       paramObjectOutputStream.writeObject(null);
/*      */     } finally {
/* 1306 */       reentrantLock.unlock();
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1319 */     paramObjectInputStream.defaultReadObject();
/* 1320 */     this.count = 0;
/* 1321 */     this.first = null;
/* 1322 */     this.last = null;
/*      */ 
/*      */     
/*      */     while (true) {
/* 1326 */       Object object = paramObjectInputStream.readObject();
/* 1327 */       if (object == null)
/*      */         break; 
/* 1329 */       add((E)object);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/LinkedBlockingDeque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */