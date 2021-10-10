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
/*      */ import java.util.concurrent.atomic.AtomicInteger;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LinkedBlockingQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -6903933977591709194L;
/*      */   private final int capacity;
/*      */   
/*      */   static class Node<E>
/*      */   {
/*      */     E item;
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
/*  140 */   private final AtomicInteger count = new AtomicInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient Node<E> head;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Node<E> last;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   private final ReentrantLock takeLock = new ReentrantLock();
/*      */ 
/*      */   
/*  158 */   private final Condition notEmpty = this.takeLock.newCondition();
/*      */ 
/*      */   
/*  161 */   private final ReentrantLock putLock = new ReentrantLock();
/*      */ 
/*      */   
/*  164 */   private final Condition notFull = this.putLock.newCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void signalNotEmpty() {
/*  171 */     ReentrantLock reentrantLock = this.takeLock;
/*  172 */     reentrantLock.lock();
/*      */     try {
/*  174 */       this.notEmpty.signal();
/*      */     } finally {
/*  176 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void signalNotFull() {
/*  184 */     ReentrantLock reentrantLock = this.putLock;
/*  185 */     reentrantLock.lock();
/*      */     try {
/*  187 */       this.notFull.signal();
/*      */     } finally {
/*  189 */       reentrantLock.unlock();
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
/*      */   private void enqueue(Node<E> paramNode) {
/*  201 */     this.last = this.last.next = paramNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E dequeue() {
/*  212 */     Node<E> node1 = this.head;
/*  213 */     Node<E> node2 = node1.next;
/*  214 */     node1.next = node1;
/*  215 */     this.head = node2;
/*  216 */     E e = node2.item;
/*  217 */     node2.item = null;
/*  218 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void fullyLock() {
/*  225 */     this.putLock.lock();
/*  226 */     this.takeLock.lock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void fullyUnlock() {
/*  233 */     this.takeLock.unlock();
/*  234 */     this.putLock.unlock();
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
/*      */   public LinkedBlockingQueue() {
/*  250 */     this(2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedBlockingQueue(int paramInt) {
/*  261 */     if (paramInt <= 0) throw new IllegalArgumentException(); 
/*  262 */     this.capacity = paramInt;
/*  263 */     this.last = this.head = new Node<>(null);
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
/*      */   public LinkedBlockingQueue(Collection<? extends E> paramCollection) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: ldc 2147483647
/*      */     //   3: invokespecial <init> : (I)V
/*      */     //   6: aload_0
/*      */     //   7: getfield putLock : Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   10: astore_2
/*      */     //   11: aload_2
/*      */     //   12: invokevirtual lock : ()V
/*      */     //   15: iconst_0
/*      */     //   16: istore_3
/*      */     //   17: aload_1
/*      */     //   18: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   23: astore #4
/*      */     //   25: aload #4
/*      */     //   27: invokeinterface hasNext : ()Z
/*      */     //   32: ifeq -> 94
/*      */     //   35: aload #4
/*      */     //   37: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   42: astore #5
/*      */     //   44: aload #5
/*      */     //   46: ifnonnull -> 57
/*      */     //   49: new java/lang/NullPointerException
/*      */     //   52: dup
/*      */     //   53: invokespecial <init> : ()V
/*      */     //   56: athrow
/*      */     //   57: iload_3
/*      */     //   58: aload_0
/*      */     //   59: getfield capacity : I
/*      */     //   62: if_icmpne -> 75
/*      */     //   65: new java/lang/IllegalStateException
/*      */     //   68: dup
/*      */     //   69: ldc 'Queue full'
/*      */     //   71: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   74: athrow
/*      */     //   75: aload_0
/*      */     //   76: new java/util/concurrent/LinkedBlockingQueue$Node
/*      */     //   79: dup
/*      */     //   80: aload #5
/*      */     //   82: invokespecial <init> : (Ljava/lang/Object;)V
/*      */     //   85: invokespecial enqueue : (Ljava/util/concurrent/LinkedBlockingQueue$Node;)V
/*      */     //   88: iinc #3, 1
/*      */     //   91: goto -> 25
/*      */     //   94: aload_0
/*      */     //   95: getfield count : Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   98: iload_3
/*      */     //   99: invokevirtual set : (I)V
/*      */     //   102: aload_2
/*      */     //   103: invokevirtual unlock : ()V
/*      */     //   106: goto -> 118
/*      */     //   109: astore #6
/*      */     //   111: aload_2
/*      */     //   112: invokevirtual unlock : ()V
/*      */     //   115: aload #6
/*      */     //   117: athrow
/*      */     //   118: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #277	-> 0
/*      */     //   #278	-> 6
/*      */     //   #279	-> 11
/*      */     //   #281	-> 15
/*      */     //   #282	-> 17
/*      */     //   #283	-> 44
/*      */     //   #284	-> 49
/*      */     //   #285	-> 57
/*      */     //   #286	-> 65
/*      */     //   #287	-> 75
/*      */     //   #288	-> 88
/*      */     //   #289	-> 91
/*      */     //   #290	-> 94
/*      */     //   #292	-> 102
/*      */     //   #293	-> 106
/*      */     //   #292	-> 109
/*      */     //   #293	-> 115
/*      */     //   #294	-> 118
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   15	102	109	finally
/*      */     //   109	111	109	finally
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
/*  304 */     return this.count.get();
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
/*      */   public int remainingCapacity() {
/*  321 */     return this.capacity - this.count.get();
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
/*  332 */     if (paramE == null) throw new NullPointerException();
/*      */ 
/*      */     
/*  335 */     int i = -1;
/*  336 */     Node<E> node = new Node<>(paramE);
/*  337 */     ReentrantLock reentrantLock = this.putLock;
/*  338 */     AtomicInteger atomicInteger = this.count;
/*  339 */     reentrantLock.lockInterruptibly();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  349 */       while (atomicInteger.get() == this.capacity) {
/*  350 */         this.notFull.await();
/*      */       }
/*  352 */       enqueue(node);
/*  353 */       i = atomicInteger.getAndIncrement();
/*  354 */       if (i + 1 < this.capacity)
/*  355 */         this.notFull.signal(); 
/*      */     } finally {
/*  357 */       reentrantLock.unlock();
/*      */     } 
/*  359 */     if (i == 0) {
/*  360 */       signalNotEmpty();
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
/*  375 */     if (paramE == null) throw new NullPointerException(); 
/*  376 */     long l = paramTimeUnit.toNanos(paramLong);
/*  377 */     int i = -1;
/*  378 */     ReentrantLock reentrantLock = this.putLock;
/*  379 */     AtomicInteger atomicInteger = this.count;
/*  380 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  382 */       while (atomicInteger.get() == this.capacity) {
/*  383 */         if (l <= 0L)
/*  384 */           return false; 
/*  385 */         l = this.notFull.awaitNanos(l);
/*      */       } 
/*  387 */       enqueue(new Node<>(paramE));
/*  388 */       i = atomicInteger.getAndIncrement();
/*  389 */       if (i + 1 < this.capacity)
/*  390 */         this.notFull.signal(); 
/*      */     } finally {
/*  392 */       reentrantLock.unlock();
/*      */     } 
/*  394 */     if (i == 0)
/*  395 */       signalNotEmpty(); 
/*  396 */     return true;
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
/*      */   public boolean offer(E paramE) {
/*  411 */     if (paramE == null) throw new NullPointerException(); 
/*  412 */     AtomicInteger atomicInteger = this.count;
/*  413 */     if (atomicInteger.get() == this.capacity)
/*  414 */       return false; 
/*  415 */     int i = -1;
/*  416 */     Node<E> node = new Node<>(paramE);
/*  417 */     ReentrantLock reentrantLock = this.putLock;
/*  418 */     reentrantLock.lock();
/*      */     try {
/*  420 */       if (atomicInteger.get() < this.capacity) {
/*  421 */         enqueue(node);
/*  422 */         i = atomicInteger.getAndIncrement();
/*  423 */         if (i + 1 < this.capacity)
/*  424 */           this.notFull.signal(); 
/*      */       } 
/*      */     } finally {
/*  427 */       reentrantLock.unlock();
/*      */     } 
/*  429 */     if (i == 0)
/*  430 */       signalNotEmpty(); 
/*  431 */     return (i >= 0);
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException {
/*      */     E e;
/*  436 */     int i = -1;
/*  437 */     AtomicInteger atomicInteger = this.count;
/*  438 */     ReentrantLock reentrantLock = this.takeLock;
/*  439 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  441 */       while (atomicInteger.get() == 0) {
/*  442 */         this.notEmpty.await();
/*      */       }
/*  444 */       e = dequeue();
/*  445 */       i = atomicInteger.getAndDecrement();
/*  446 */       if (i > 1)
/*  447 */         this.notEmpty.signal(); 
/*      */     } finally {
/*  449 */       reentrantLock.unlock();
/*      */     } 
/*  451 */     if (i == this.capacity)
/*  452 */       signalNotFull(); 
/*  453 */     return e;
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  457 */     E e = null;
/*  458 */     int i = -1;
/*  459 */     long l = paramTimeUnit.toNanos(paramLong);
/*  460 */     AtomicInteger atomicInteger = this.count;
/*  461 */     ReentrantLock reentrantLock = this.takeLock;
/*  462 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  464 */       while (atomicInteger.get() == 0) {
/*  465 */         if (l <= 0L)
/*  466 */           return null; 
/*  467 */         l = this.notEmpty.awaitNanos(l);
/*      */       } 
/*  469 */       e = dequeue();
/*  470 */       i = atomicInteger.getAndDecrement();
/*  471 */       if (i > 1)
/*  472 */         this.notEmpty.signal(); 
/*      */     } finally {
/*  474 */       reentrantLock.unlock();
/*      */     } 
/*  476 */     if (i == this.capacity)
/*  477 */       signalNotFull(); 
/*  478 */     return e;
/*      */   }
/*      */   
/*      */   public E poll() {
/*  482 */     AtomicInteger atomicInteger = this.count;
/*  483 */     if (atomicInteger.get() == 0)
/*  484 */       return null; 
/*  485 */     E e = null;
/*  486 */     int i = -1;
/*  487 */     ReentrantLock reentrantLock = this.takeLock;
/*  488 */     reentrantLock.lock();
/*      */     try {
/*  490 */       if (atomicInteger.get() > 0) {
/*  491 */         e = dequeue();
/*  492 */         i = atomicInteger.getAndDecrement();
/*  493 */         if (i > 1)
/*  494 */           this.notEmpty.signal(); 
/*      */       } 
/*      */     } finally {
/*  497 */       reentrantLock.unlock();
/*      */     } 
/*  499 */     if (i == this.capacity)
/*  500 */       signalNotFull(); 
/*  501 */     return e;
/*      */   }
/*      */   
/*      */   public E peek() {
/*  505 */     if (this.count.get() == 0)
/*  506 */       return null; 
/*  507 */     ReentrantLock reentrantLock = this.takeLock;
/*  508 */     reentrantLock.lock();
/*      */     try {
/*  510 */       Node<E> node = this.head.next;
/*  511 */       if (node == null) {
/*  512 */         return null;
/*      */       }
/*  514 */       return node.item;
/*      */     } finally {
/*  516 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void unlink(Node<E> paramNode1, Node<E> paramNode2) {
/*  527 */     paramNode1.item = null;
/*  528 */     paramNode2.next = paramNode1.next;
/*  529 */     if (this.last == paramNode1)
/*  530 */       this.last = paramNode2; 
/*  531 */     if (this.count.getAndDecrement() == this.capacity) {
/*  532 */       this.notFull.signal();
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
/*      */   public boolean remove(Object paramObject) {
/*  547 */     if (paramObject == null) return false; 
/*  548 */     fullyLock();
/*      */     try {
/*  550 */       Node<E> node1 = this.head, node2 = node1.next;
/*  551 */       for (; node2 != null; 
/*  552 */         node1 = node2, node2 = node2.next) {
/*  553 */         if (paramObject.equals(node2.item)) {
/*  554 */           unlink(node2, node1);
/*  555 */           return true;
/*      */         } 
/*      */       } 
/*  558 */       return false;
/*      */     } finally {
/*  560 */       fullyUnlock();
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
/*  573 */     if (paramObject == null) return false; 
/*  574 */     fullyLock();
/*      */     try {
/*  576 */       for (Node<E> node = this.head.next; node != null; node = node.next) {
/*  577 */         if (paramObject.equals(node.item))
/*  578 */           return true; 
/*  579 */       }  return false;
/*      */     } finally {
/*  581 */       fullyUnlock();
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
/*      */   public Object[] toArray() {
/*  599 */     fullyLock();
/*      */     try {
/*  601 */       int i = this.count.get();
/*  602 */       Object[] arrayOfObject = new Object[i];
/*  603 */       byte b = 0;
/*  604 */       for (Node<E> node = this.head.next; node != null; node = node.next)
/*  605 */         arrayOfObject[b++] = node.item; 
/*  606 */       return arrayOfObject;
/*      */     } finally {
/*  608 */       fullyUnlock();
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
/*  649 */     fullyLock();
/*      */     try {
/*  651 */       int i = this.count.get();
/*  652 */       if (paramArrayOfT.length < i)
/*      */       {
/*  654 */         paramArrayOfT = (T[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
/*      */       }
/*  656 */       byte b = 0;
/*  657 */       for (Node<E> node = this.head.next; node != null; node = node.next)
/*  658 */         paramArrayOfT[b++] = (T)node.item; 
/*  659 */       if (paramArrayOfT.length > b)
/*  660 */         paramArrayOfT[b] = null; 
/*  661 */       return paramArrayOfT;
/*      */     } finally {
/*  663 */       fullyUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public String toString() {
/*  668 */     fullyLock();
/*      */     try {
/*  670 */       Node<E> node = this.head.next;
/*  671 */       if (node == null) {
/*  672 */         return "[]";
/*      */       }
/*  674 */       StringBuilder stringBuilder = new StringBuilder();
/*  675 */       stringBuilder.append('[');
/*      */       while (true) {
/*  677 */         E e = node.item;
/*  678 */         stringBuilder.append((e == this) ? "(this Collection)" : e);
/*  679 */         node = node.next;
/*  680 */         if (node == null)
/*  681 */           return stringBuilder.append(']').toString(); 
/*  682 */         stringBuilder.append(',').append(' ');
/*      */       } 
/*      */     } finally {
/*  685 */       fullyUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  694 */     fullyLock(); try {
/*      */       Node<E> node1;
/*  696 */       for (Node<E> node2 = this.head; (node1 = node2.next) != null; node2 = node1) {
/*  697 */         node2.next = node2;
/*  698 */         node1.item = null;
/*      */       } 
/*  700 */       this.head = this.last;
/*      */       
/*  702 */       if (this.count.getAndSet(0) == this.capacity)
/*  703 */         this.notFull.signal(); 
/*      */     } finally {
/*  705 */       fullyUnlock();
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
/*  716 */     return drainTo(paramCollection, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt) {
/*  726 */     if (paramCollection == null)
/*  727 */       throw new NullPointerException(); 
/*  728 */     if (paramCollection == this)
/*  729 */       throw new IllegalArgumentException(); 
/*  730 */     if (paramInt <= 0)
/*  731 */       return 0; 
/*  732 */     boolean bool = false;
/*  733 */     ReentrantLock reentrantLock = this.takeLock;
/*  734 */     reentrantLock.lock();
/*      */     try {
/*  736 */       int i = Math.min(paramInt, this.count.get());
/*      */       
/*  738 */       Node<E> node = this.head;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  759 */       reentrantLock.unlock();
/*  760 */       if (bool) {
/*  761 */         signalNotFull();
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
/*      */   public Iterator<E> iterator() {
/*  775 */     return new Itr();
/*      */   }
/*      */ 
/*      */   
/*      */   private class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private LinkedBlockingQueue.Node<E> current;
/*      */     
/*      */     private LinkedBlockingQueue.Node<E> lastRet;
/*      */     
/*      */     private E currentElement;
/*      */ 
/*      */     
/*      */     Itr() {
/*  790 */       LinkedBlockingQueue.this.fullyLock();
/*      */       try {
/*  792 */         this.current = LinkedBlockingQueue.this.head.next;
/*  793 */         if (this.current != null)
/*  794 */           this.currentElement = this.current.item; 
/*      */       } finally {
/*  796 */         LinkedBlockingQueue.this.fullyUnlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  801 */       return (this.current != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private LinkedBlockingQueue.Node<E> nextNode(LinkedBlockingQueue.Node<E> param1Node) {
/*      */       while (true) {
/*  813 */         LinkedBlockingQueue.Node<E> node = param1Node.next;
/*  814 */         if (node == param1Node)
/*  815 */           return LinkedBlockingQueue.this.head.next; 
/*  816 */         if (node == null || node.item != null)
/*  817 */           return node; 
/*  818 */         param1Node = node;
/*      */       } 
/*      */     }
/*      */     
/*      */     public E next() {
/*  823 */       LinkedBlockingQueue.this.fullyLock();
/*      */       try {
/*  825 */         if (this.current == null)
/*  826 */           throw new NoSuchElementException(); 
/*  827 */         E e = this.currentElement;
/*  828 */         this.lastRet = this.current;
/*  829 */         this.current = nextNode(this.current);
/*  830 */         this.currentElement = (this.current == null) ? null : this.current.item;
/*  831 */         return e;
/*      */       } finally {
/*  833 */         LinkedBlockingQueue.this.fullyUnlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  838 */       if (this.lastRet == null)
/*  839 */         throw new IllegalStateException(); 
/*  840 */       LinkedBlockingQueue.this.fullyLock();
/*      */       try {
/*  842 */         LinkedBlockingQueue.Node<E> node1 = this.lastRet;
/*  843 */         this.lastRet = null;
/*  844 */         LinkedBlockingQueue.Node<E> node2 = LinkedBlockingQueue.this.head, node3 = node2.next;
/*  845 */         for (; node3 != null; 
/*  846 */           node2 = node3, node3 = node3.next) {
/*  847 */           if (node3 == node1) {
/*  848 */             LinkedBlockingQueue.this.unlink(node3, node2);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } finally {
/*  853 */         LinkedBlockingQueue.this.fullyUnlock();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class LBQSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedBlockingQueue<E> queue;
/*      */     LinkedBlockingQueue.Node<E> current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     long est;
/*      */     
/*      */     LBQSpliterator(LinkedBlockingQueue<E> param1LinkedBlockingQueue) {
/*  867 */       this.queue = param1LinkedBlockingQueue;
/*  868 */       this.est = param1LinkedBlockingQueue.size();
/*      */     }
/*      */     public long estimateSize() {
/*  871 */       return this.est;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit() {
/*  875 */       LinkedBlockingQueue<E> linkedBlockingQueue = this.queue;
/*  876 */       int i = this.batch;
/*  877 */       byte b = (i <= 0) ? 1 : ((i >= 33554432) ? 33554432 : (i + 1)); LinkedBlockingQueue.Node<E> node;
/*  878 */       if (!this.exhausted && ((node = this.current) != null || (node = linkedBlockingQueue.head.next) != null) && node.next != null) {
/*      */ 
/*      */         
/*  881 */         Object[] arrayOfObject = new Object[b];
/*  882 */         byte b1 = 0;
/*  883 */         LinkedBlockingQueue.Node<E> node1 = this.current;
/*  884 */         linkedBlockingQueue.fullyLock();
/*      */         try {
/*  886 */           if (node1 != null || (node1 = linkedBlockingQueue.head.next) != null) {
/*      */             do {
/*  888 */               arrayOfObject[b1] = node1.item; if (node1.item == null)
/*  889 */                 continue;  b1++;
/*  890 */             } while ((node1 = node1.next) != null && b1 < b);
/*      */           }
/*      */         } finally {
/*  893 */           linkedBlockingQueue.fullyUnlock();
/*      */         } 
/*  895 */         if ((this.current = node1) == null) {
/*  896 */           this.est = 0L;
/*  897 */           this.exhausted = true;
/*      */         }
/*  899 */         else if ((this.est -= b1) < 0L) {
/*  900 */           this.est = 0L;
/*  901 */         }  if (b1 > 0) {
/*  902 */           this.batch = b1;
/*  903 */           return 
/*  904 */             Spliterators.spliterator(arrayOfObject, 0, b1, 4368);
/*      */         } 
/*      */       } 
/*      */       
/*  908 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/*  912 */       if (param1Consumer == null) throw new NullPointerException(); 
/*  913 */       LinkedBlockingQueue<E> linkedBlockingQueue = this.queue;
/*  914 */       if (!this.exhausted) {
/*  915 */         this.exhausted = true;
/*  916 */         LinkedBlockingQueue.Node<E> node = this.current; do {
/*      */           E e;
/*  918 */           Object object = null;
/*  919 */           linkedBlockingQueue.fullyLock();
/*      */           try {
/*  921 */             if (node == null)
/*  922 */               node = linkedBlockingQueue.head.next; 
/*  923 */             while (node != null) {
/*  924 */               e = node.item;
/*  925 */               node = node.next;
/*  926 */               if (e != null)
/*      */                 break; 
/*      */             } 
/*      */           } finally {
/*  930 */             linkedBlockingQueue.fullyUnlock();
/*      */           } 
/*  932 */           if (e == null)
/*  933 */             continue;  param1Consumer.accept(e);
/*  934 */         } while (node != null);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/*  939 */       if (param1Consumer == null) throw new NullPointerException(); 
/*  940 */       LinkedBlockingQueue<E> linkedBlockingQueue = this.queue;
/*  941 */       if (!this.exhausted) {
/*  942 */         E e; Object object = null;
/*  943 */         linkedBlockingQueue.fullyLock();
/*      */         try {
/*  945 */           if (this.current == null)
/*  946 */             this.current = linkedBlockingQueue.head.next; 
/*  947 */           while (this.current != null) {
/*  948 */             e = this.current.item;
/*  949 */             this.current = this.current.next;
/*  950 */             if (e != null)
/*      */               break; 
/*      */           } 
/*      */         } finally {
/*  954 */           linkedBlockingQueue.fullyUnlock();
/*      */         } 
/*  956 */         if (this.current == null)
/*  957 */           this.exhausted = true; 
/*  958 */         if (e != null) {
/*  959 */           param1Consumer.accept(e);
/*  960 */           return true;
/*      */         } 
/*      */       } 
/*  963 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  967 */       return 4368;
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
/*  989 */     return new LBQSpliterator<>(this);
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
/* 1004 */     fullyLock();
/*      */     
/*      */     try {
/* 1007 */       paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */       
/* 1010 */       for (Node<E> node = this.head.next; node != null; node = node.next) {
/* 1011 */         paramObjectOutputStream.writeObject(node.item);
/*      */       }
/*      */       
/* 1014 */       paramObjectOutputStream.writeObject(null);
/*      */     } finally {
/* 1016 */       fullyUnlock();
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1030 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1032 */     this.count.set(0);
/* 1033 */     this.last = this.head = new Node<>(null);
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1038 */       Object object = paramObjectInputStream.readObject();
/* 1039 */       if (object == null)
/*      */         break; 
/* 1041 */       add((E)object);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/LinkedBlockingQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */