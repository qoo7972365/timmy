/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayBlockingQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -817911632652898426L;
/*      */   final Object[] items;
/*      */   int takeIndex;
/*      */   int putIndex;
/*      */   int count;
/*      */   final ReentrantLock lock;
/*      */   private final Condition notEmpty;
/*      */   private final Condition notFull;
/*  124 */   transient Itrs itrs = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int dec(int paramInt) {
/*  132 */     return ((paramInt == 0) ? this.items.length : paramInt) - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final E itemAt(int paramInt) {
/*  140 */     return (E)this.items[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkNotNull(Object paramObject) {
/*  149 */     if (paramObject == null) {
/*  150 */       throw new NullPointerException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void enqueue(E paramE) {
/*  160 */     Object[] arrayOfObject = this.items;
/*  161 */     arrayOfObject[this.putIndex] = paramE;
/*  162 */     if (++this.putIndex == arrayOfObject.length)
/*  163 */       this.putIndex = 0; 
/*  164 */     this.count++;
/*  165 */     this.notEmpty.signal();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E dequeue() {
/*  175 */     Object[] arrayOfObject = this.items;
/*      */     
/*  177 */     Object object = arrayOfObject[this.takeIndex];
/*  178 */     arrayOfObject[this.takeIndex] = null;
/*  179 */     if (++this.takeIndex == arrayOfObject.length)
/*  180 */       this.takeIndex = 0; 
/*  181 */     this.count--;
/*  182 */     if (this.itrs != null)
/*  183 */       this.itrs.elementDequeued(); 
/*  184 */     this.notFull.signal();
/*  185 */     return (E)object;
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
/*      */   void removeAt(int paramInt) {
/*  197 */     Object[] arrayOfObject = this.items;
/*  198 */     if (paramInt == this.takeIndex) {
/*      */       
/*  200 */       arrayOfObject[this.takeIndex] = null;
/*  201 */       if (++this.takeIndex == arrayOfObject.length)
/*  202 */         this.takeIndex = 0; 
/*  203 */       this.count--;
/*  204 */       if (this.itrs != null) {
/*  205 */         this.itrs.elementDequeued();
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  210 */       int i = this.putIndex;
/*  211 */       int j = paramInt; while (true) {
/*  212 */         int k = j + 1;
/*  213 */         if (k == arrayOfObject.length)
/*  214 */           k = 0; 
/*  215 */         if (k != i) {
/*  216 */           arrayOfObject[j] = arrayOfObject[k];
/*  217 */           j = k; continue;
/*      */         }  break;
/*  219 */       }  arrayOfObject[j] = null;
/*  220 */       this.putIndex = j;
/*      */ 
/*      */ 
/*      */       
/*  224 */       this.count--;
/*  225 */       if (this.itrs != null)
/*  226 */         this.itrs.removedAt(paramInt); 
/*      */     } 
/*  228 */     this.notFull.signal();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayBlockingQueue(int paramInt) {
/*  239 */     this(paramInt, false);
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
/*      */   public ArrayBlockingQueue(int paramInt, boolean paramBoolean) {
/*  253 */     if (paramInt <= 0)
/*  254 */       throw new IllegalArgumentException(); 
/*  255 */     this.items = new Object[paramInt];
/*  256 */     this.lock = new ReentrantLock(paramBoolean);
/*  257 */     this.notEmpty = this.lock.newCondition();
/*  258 */     this.notFull = this.lock.newCondition();
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
/*      */   public ArrayBlockingQueue(int paramInt, boolean paramBoolean, Collection<? extends E> paramCollection) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: iload_1
/*      */     //   2: iload_2
/*      */     //   3: invokespecial <init> : (IZ)V
/*      */     //   6: aload_0
/*      */     //   7: getfield lock : Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   10: astore #4
/*      */     //   12: aload #4
/*      */     //   14: invokevirtual lock : ()V
/*      */     //   17: iconst_0
/*      */     //   18: istore #5
/*      */     //   20: aload_3
/*      */     //   21: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   26: astore #6
/*      */     //   28: aload #6
/*      */     //   30: invokeinterface hasNext : ()Z
/*      */     //   35: ifeq -> 67
/*      */     //   38: aload #6
/*      */     //   40: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   45: astore #7
/*      */     //   47: aload #7
/*      */     //   49: invokestatic checkNotNull : (Ljava/lang/Object;)V
/*      */     //   52: aload_0
/*      */     //   53: getfield items : [Ljava/lang/Object;
/*      */     //   56: iload #5
/*      */     //   58: iinc #5, 1
/*      */     //   61: aload #7
/*      */     //   63: aastore
/*      */     //   64: goto -> 28
/*      */     //   67: goto -> 80
/*      */     //   70: astore #6
/*      */     //   72: new java/lang/IllegalArgumentException
/*      */     //   75: dup
/*      */     //   76: invokespecial <init> : ()V
/*      */     //   79: athrow
/*      */     //   80: aload_0
/*      */     //   81: iload #5
/*      */     //   83: putfield count : I
/*      */     //   86: aload_0
/*      */     //   87: iload #5
/*      */     //   89: iload_1
/*      */     //   90: if_icmpne -> 97
/*      */     //   93: iconst_0
/*      */     //   94: goto -> 99
/*      */     //   97: iload #5
/*      */     //   99: putfield putIndex : I
/*      */     //   102: aload #4
/*      */     //   104: invokevirtual unlock : ()V
/*      */     //   107: goto -> 120
/*      */     //   110: astore #8
/*      */     //   112: aload #4
/*      */     //   114: invokevirtual unlock : ()V
/*      */     //   117: aload #8
/*      */     //   119: athrow
/*      */     //   120: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #279	-> 0
/*      */     //   #281	-> 6
/*      */     //   #282	-> 12
/*      */     //   #284	-> 17
/*      */     //   #286	-> 20
/*      */     //   #287	-> 47
/*      */     //   #288	-> 52
/*      */     //   #289	-> 64
/*      */     //   #292	-> 67
/*      */     //   #290	-> 70
/*      */     //   #291	-> 72
/*      */     //   #293	-> 80
/*      */     //   #294	-> 86
/*      */     //   #296	-> 102
/*      */     //   #297	-> 107
/*      */     //   #296	-> 110
/*      */     //   #297	-> 117
/*      */     //   #298	-> 120
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   17	102	110	finally
/*      */     //   20	67	70	java/lang/ArrayIndexOutOfBoundsException
/*      */     //   110	112	110	finally
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
/*      */   public boolean add(E paramE) {
/*  312 */     return super.add(paramE);
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
/*  325 */     checkNotNull(paramE);
/*  326 */     ReentrantLock reentrantLock = this.lock;
/*  327 */     reentrantLock.lock();
/*      */     try {
/*  329 */       if (this.count == this.items.length) {
/*  330 */         return false;
/*      */       }
/*  332 */       enqueue(paramE);
/*  333 */       return true;
/*      */     } finally {
/*      */       
/*  336 */       reentrantLock.unlock();
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
/*      */   public void put(E paramE) throws InterruptedException {
/*  348 */     checkNotNull(paramE);
/*  349 */     ReentrantLock reentrantLock = this.lock;
/*  350 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  352 */       while (this.count == this.items.length)
/*  353 */         this.notFull.await(); 
/*  354 */       enqueue(paramE);
/*      */     } finally {
/*  356 */       reentrantLock.unlock();
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
/*  371 */     checkNotNull(paramE);
/*  372 */     long l = paramTimeUnit.toNanos(paramLong);
/*  373 */     ReentrantLock reentrantLock = this.lock;
/*  374 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  376 */       while (this.count == this.items.length) {
/*  377 */         if (l <= 0L)
/*  378 */           return false; 
/*  379 */         l = this.notFull.awaitNanos(l);
/*      */       } 
/*  381 */       enqueue(paramE);
/*  382 */       return true;
/*      */     } finally {
/*  384 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E poll() {
/*  389 */     ReentrantLock reentrantLock = this.lock;
/*  390 */     reentrantLock.lock();
/*      */     try {
/*  392 */       return (this.count == 0) ? null : dequeue();
/*      */     } finally {
/*  394 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException {
/*  399 */     ReentrantLock reentrantLock = this.lock;
/*  400 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  402 */       while (this.count == 0)
/*  403 */         this.notEmpty.await(); 
/*  404 */       return dequeue();
/*      */     } finally {
/*  406 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  411 */     long l = paramTimeUnit.toNanos(paramLong);
/*  412 */     ReentrantLock reentrantLock = this.lock;
/*  413 */     reentrantLock.lockInterruptibly();
/*      */     try {
/*  415 */       while (this.count == 0) {
/*  416 */         if (l <= 0L)
/*  417 */           return null; 
/*  418 */         l = this.notEmpty.awaitNanos(l);
/*      */       } 
/*  420 */       return dequeue();
/*      */     } finally {
/*  422 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public E peek() {
/*  427 */     ReentrantLock reentrantLock = this.lock;
/*  428 */     reentrantLock.lock();
/*      */     try {
/*  430 */       return itemAt(this.takeIndex);
/*      */     } finally {
/*  432 */       reentrantLock.unlock();
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
/*      */   public int size() {
/*  444 */     ReentrantLock reentrantLock = this.lock;
/*  445 */     reentrantLock.lock();
/*      */     try {
/*  447 */       return this.count;
/*      */     } finally {
/*  449 */       reentrantLock.unlock();
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
/*      */   public int remainingCapacity() {
/*  467 */     ReentrantLock reentrantLock = this.lock;
/*  468 */     reentrantLock.lock();
/*      */     try {
/*  470 */       return this.items.length - this.count;
/*      */     } finally {
/*  472 */       reentrantLock.unlock();
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
/*      */   public boolean remove(Object paramObject) {
/*  494 */     if (paramObject == null) return false; 
/*  495 */     Object[] arrayOfObject = this.items;
/*  496 */     ReentrantLock reentrantLock = this.lock;
/*  497 */     reentrantLock.lock();
/*      */     try {
/*  499 */       if (this.count > 0) {
/*  500 */         int i = this.putIndex;
/*  501 */         int j = this.takeIndex;
/*      */         do {
/*  503 */           if (paramObject.equals(arrayOfObject[j])) {
/*  504 */             removeAt(j);
/*  505 */             return true;
/*      */           } 
/*  507 */           if (++j != arrayOfObject.length)
/*  508 */             continue;  j = 0;
/*  509 */         } while (j != i);
/*      */       } 
/*  511 */       return false;
/*      */     } finally {
/*  513 */       reentrantLock.unlock();
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
/*  526 */     if (paramObject == null) return false; 
/*  527 */     Object[] arrayOfObject = this.items;
/*  528 */     ReentrantLock reentrantLock = this.lock;
/*  529 */     reentrantLock.lock();
/*      */     try {
/*  531 */       if (this.count > 0) {
/*  532 */         int i = this.putIndex;
/*  533 */         int j = this.takeIndex;
/*      */         do {
/*  535 */           if (paramObject.equals(arrayOfObject[j]))
/*  536 */             return true; 
/*  537 */           if (++j != arrayOfObject.length)
/*  538 */             continue;  j = 0;
/*  539 */         } while (j != i);
/*      */       } 
/*  541 */       return false;
/*      */     } finally {
/*  543 */       reentrantLock.unlock();
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
/*      */     Object[] arrayOfObject;
/*  562 */     ReentrantLock reentrantLock = this.lock;
/*  563 */     reentrantLock.lock();
/*      */     try {
/*  565 */       int i = this.count;
/*  566 */       arrayOfObject = new Object[i];
/*  567 */       int j = this.items.length - this.takeIndex;
/*  568 */       if (i <= j) {
/*  569 */         System.arraycopy(this.items, this.takeIndex, arrayOfObject, 0, i);
/*      */       } else {
/*  571 */         System.arraycopy(this.items, this.takeIndex, arrayOfObject, 0, j);
/*  572 */         System.arraycopy(this.items, 0, arrayOfObject, j, i - j);
/*      */       } 
/*      */     } finally {
/*  575 */       reentrantLock.unlock();
/*      */     } 
/*  577 */     return arrayOfObject;
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
/*  617 */     Object[] arrayOfObject = this.items;
/*  618 */     ReentrantLock reentrantLock = this.lock;
/*  619 */     reentrantLock.lock();
/*      */     try {
/*  621 */       int i = this.count;
/*  622 */       int j = paramArrayOfT.length;
/*  623 */       if (j < i)
/*  624 */         paramArrayOfT = (T[])Array.newInstance(paramArrayOfT
/*  625 */             .getClass().getComponentType(), i); 
/*  626 */       int k = arrayOfObject.length - this.takeIndex;
/*  627 */       if (i <= k) {
/*  628 */         System.arraycopy(arrayOfObject, this.takeIndex, paramArrayOfT, 0, i);
/*      */       } else {
/*  630 */         System.arraycopy(arrayOfObject, this.takeIndex, paramArrayOfT, 0, k);
/*  631 */         System.arraycopy(arrayOfObject, 0, paramArrayOfT, k, i - k);
/*      */       } 
/*  633 */       if (j > i)
/*  634 */         paramArrayOfT[i] = null; 
/*      */     } finally {
/*  636 */       reentrantLock.unlock();
/*      */     } 
/*  638 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */   public String toString() {
/*  642 */     ReentrantLock reentrantLock = this.lock;
/*  643 */     reentrantLock.lock();
/*      */     try {
/*  645 */       int i = this.count;
/*  646 */       if (i == 0) {
/*  647 */         return "[]";
/*      */       }
/*  649 */       Object[] arrayOfObject = this.items;
/*  650 */       StringBuilder stringBuilder = new StringBuilder();
/*  651 */       stringBuilder.append('[');
/*  652 */       int j = this.takeIndex; while (true) {
/*  653 */         Object object = arrayOfObject[j];
/*  654 */         stringBuilder.append((object == this) ? "(this Collection)" : object);
/*  655 */         if (--i == 0)
/*  656 */           return stringBuilder.append(']').toString(); 
/*  657 */         stringBuilder.append(',').append(' ');
/*  658 */         if (++j == arrayOfObject.length)
/*  659 */           j = 0; 
/*      */       } 
/*      */     } finally {
/*  662 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  671 */     Object[] arrayOfObject = this.items;
/*  672 */     ReentrantLock reentrantLock = this.lock;
/*  673 */     reentrantLock.lock();
/*      */     
/*  675 */     try { int i = this.count;
/*  676 */       if (i > 0) {
/*  677 */         int j = this.putIndex;
/*  678 */         int k = this.takeIndex;
/*      */         while (true) {
/*  680 */           arrayOfObject[k] = null;
/*  681 */           if (++k == arrayOfObject.length)
/*  682 */             k = 0; 
/*  683 */           if (k == j)
/*  684 */           { this.takeIndex = j;
/*  685 */             this.count = 0;
/*  686 */             if (this.itrs != null)
/*  687 */               this.itrs.queueIsEmpty(); 
/*  688 */             for (; i > 0 && reentrantLock.hasWaiters(this.notFull); i--)
/*  689 */               this.notFull.signal();  break; } 
/*      */         } 
/*      */       }  }
/*  692 */     finally { reentrantLock.unlock(); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection) {
/*  703 */     return drainTo(paramCollection, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt) {
/*  713 */     checkNotNull(paramCollection);
/*  714 */     if (paramCollection == this)
/*  715 */       throw new IllegalArgumentException(); 
/*  716 */     if (paramInt <= 0)
/*  717 */       return 0; 
/*  718 */     Object[] arrayOfObject = this.items;
/*  719 */     ReentrantLock reentrantLock = this.lock;
/*  720 */     reentrantLock.lock();
/*      */     try {
/*  722 */       int i = Math.min(paramInt, this.count);
/*  723 */       int j = this.takeIndex;
/*  724 */       byte b = 0;
/*      */ 
/*      */ 
/*      */ 
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  752 */       reentrantLock.unlock();
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
/*  766 */     return new Itr();
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
/*      */   class Itrs
/*      */   {
/*      */     private class Node
/*      */       extends WeakReference<ArrayBlockingQueue<E>.Itr>
/*      */     {
/*      */       Node next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       Node(ArrayBlockingQueue<E>.Itr param2Itr, Node param2Node) {
/*  829 */         super(param2Itr);
/*  830 */         this.next = param2Node;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  835 */     int cycles = 0;
/*      */ 
/*      */     
/*      */     private Node head;
/*      */ 
/*      */     
/*  841 */     private Node sweeper = null;
/*      */     
/*      */     private static final int SHORT_SWEEP_PROBES = 4;
/*      */     private static final int LONG_SWEEP_PROBES = 16;
/*      */     
/*      */     Itrs(ArrayBlockingQueue<E>.Itr param1Itr) {
/*  847 */       register(param1Itr);
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
/*      */     void doSomeSweeping(boolean param1Boolean) {
/*      */       Node node1, node2;
/*      */       boolean bool;
/*  861 */       byte b = param1Boolean ? 16 : 4;
/*      */       
/*  863 */       Node node3 = this.sweeper;
/*      */ 
/*      */       
/*  866 */       if (node3 == null) {
/*  867 */         node1 = null;
/*  868 */         node2 = this.head;
/*  869 */         bool = true;
/*      */       } else {
/*  871 */         node1 = node3;
/*  872 */         node2 = node1.next;
/*  873 */         bool = false;
/*      */       } 
/*      */       
/*  876 */       for (; b > 0; b--) {
/*  877 */         if (node2 == null) {
/*  878 */           if (bool)
/*      */             break; 
/*  880 */           node1 = null;
/*  881 */           node2 = this.head;
/*  882 */           bool = true;
/*      */         } 
/*  884 */         ArrayBlockingQueue<E>.Itr itr = node2.get();
/*  885 */         Node node = node2.next;
/*  886 */         if (itr == null || itr.isDetached())
/*      */         
/*  888 */         { b = 16;
/*      */           
/*  890 */           node2.clear();
/*  891 */           node2.next = null;
/*  892 */           if (node1 == null) {
/*  893 */             this.head = node;
/*  894 */             if (node == null) {
/*      */               
/*  896 */               ArrayBlockingQueue.this.itrs = null;
/*      */               
/*      */               return;
/*      */             } 
/*      */           } else {
/*  901 */             node1.next = node;
/*      */           }  }
/*  903 */         else { node1 = node2; }
/*      */         
/*  905 */         node2 = node;
/*      */       } 
/*      */       
/*  908 */       this.sweeper = (node2 == null) ? null : node1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void register(ArrayBlockingQueue<E>.Itr param1Itr) {
/*  916 */       this.head = new Node(param1Itr, this.head);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void takeIndexWrapped() {
/*  926 */       this.cycles++;
/*  927 */       for (Node node1 = null, node2 = this.head; node2 != null; ) {
/*  928 */         ArrayBlockingQueue<E>.Itr itr = node2.get();
/*  929 */         Node node = node2.next;
/*  930 */         if (itr == null || itr.takeIndexWrapped())
/*      */         
/*      */         { 
/*  933 */           node2.clear();
/*  934 */           node2.next = null;
/*  935 */           if (node1 == null) {
/*  936 */             this.head = node;
/*      */           } else {
/*  938 */             node1.next = node;
/*      */           }  }
/*  940 */         else { node1 = node2; }
/*      */         
/*  942 */         node2 = node;
/*      */       } 
/*  944 */       if (this.head == null) {
/*  945 */         ArrayBlockingQueue.this.itrs = null;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void removedAt(int param1Int) {
/*  954 */       for (Node node1 = null, node2 = this.head; node2 != null; ) {
/*  955 */         ArrayBlockingQueue<E>.Itr itr = node2.get();
/*  956 */         Node node = node2.next;
/*  957 */         if (itr == null || itr.removedAt(param1Int))
/*      */         
/*      */         { 
/*  960 */           node2.clear();
/*  961 */           node2.next = null;
/*  962 */           if (node1 == null) {
/*  963 */             this.head = node;
/*      */           } else {
/*  965 */             node1.next = node;
/*      */           }  }
/*  967 */         else { node1 = node2; }
/*      */         
/*  969 */         node2 = node;
/*      */       } 
/*  971 */       if (this.head == null) {
/*  972 */         ArrayBlockingQueue.this.itrs = null;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void queueIsEmpty() {
/*  983 */       for (Node node = this.head; node != null; node = node.next) {
/*  984 */         ArrayBlockingQueue<E>.Itr itr = node.get();
/*  985 */         if (itr != null) {
/*  986 */           node.clear();
/*  987 */           itr.shutdown();
/*      */         } 
/*      */       } 
/*  990 */       this.head = null;
/*  991 */       ArrayBlockingQueue.this.itrs = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void elementDequeued() {
/*  999 */       if (ArrayBlockingQueue.this.count == 0) {
/* 1000 */         queueIsEmpty();
/* 1001 */       } else if (ArrayBlockingQueue.this.takeIndex == 0) {
/* 1002 */         takeIndexWrapped();
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
/*      */   private class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private int cursor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private E nextItem;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int nextIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private E lastItem;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1060 */     private int lastRet = -1; private int prevTakeIndex; private int prevCycles; Itr() {
/* 1061 */       ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
/* 1062 */       reentrantLock.lock();
/*      */       try {
/* 1064 */         if (ArrayBlockingQueue.this.count == 0) {
/*      */           
/* 1066 */           this.cursor = -1;
/* 1067 */           this.nextIndex = -1;
/* 1068 */           this.prevTakeIndex = -3;
/*      */         } else {
/* 1070 */           int i = ArrayBlockingQueue.this.takeIndex;
/* 1071 */           this.prevTakeIndex = i;
/* 1072 */           this.nextItem = ArrayBlockingQueue.this.itemAt(this.nextIndex = i);
/* 1073 */           this.cursor = incCursor(i);
/* 1074 */           if (ArrayBlockingQueue.this.itrs == null) {
/* 1075 */             ArrayBlockingQueue.this.itrs = new ArrayBlockingQueue.Itrs(this);
/*      */           } else {
/* 1077 */             ArrayBlockingQueue.this.itrs.register(this);
/* 1078 */             ArrayBlockingQueue.this.itrs.doSomeSweeping(false);
/*      */           } 
/* 1080 */           this.prevCycles = ArrayBlockingQueue.this.itrs.cycles;
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 1087 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     private static final int NONE = -1; private static final int REMOVED = -2; private static final int DETACHED = -3;
/*      */     
/*      */     boolean isDetached() {
/* 1093 */       return (this.prevTakeIndex < 0);
/*      */     }
/*      */ 
/*      */     
/*      */     private int incCursor(int param1Int) {
/* 1098 */       if (++param1Int == ArrayBlockingQueue.this.items.length)
/* 1099 */         param1Int = 0; 
/* 1100 */       if (param1Int == ArrayBlockingQueue.this.putIndex)
/* 1101 */         param1Int = -1; 
/* 1102 */       return param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean invalidated(int param1Int1, int param1Int2, long param1Long, int param1Int3) {
/* 1111 */       if (param1Int1 < 0)
/* 1112 */         return false; 
/* 1113 */       int i = param1Int1 - param1Int2;
/* 1114 */       if (i < 0)
/* 1115 */         i += param1Int3; 
/* 1116 */       return (param1Long > i);
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
/*      */     private void incorporateDequeues() {
/* 1129 */       int i = ArrayBlockingQueue.this.itrs.cycles;
/* 1130 */       int j = ArrayBlockingQueue.this.takeIndex;
/* 1131 */       int k = this.prevCycles;
/* 1132 */       int m = this.prevTakeIndex;
/*      */       
/* 1134 */       if (i != k || j != m) {
/* 1135 */         int n = ArrayBlockingQueue.this.items.length;
/*      */ 
/*      */         
/* 1138 */         long l = ((i - k) * n + j - m);
/*      */ 
/*      */ 
/*      */         
/* 1142 */         if (invalidated(this.lastRet, m, l, n))
/* 1143 */           this.lastRet = -2; 
/* 1144 */         if (invalidated(this.nextIndex, m, l, n))
/* 1145 */           this.nextIndex = -2; 
/* 1146 */         if (invalidated(this.cursor, m, l, n)) {
/* 1147 */           this.cursor = j;
/*      */         }
/* 1149 */         if (this.cursor < 0 && this.nextIndex < 0 && this.lastRet < 0) {
/* 1150 */           detach();
/*      */         } else {
/* 1152 */           this.prevCycles = i;
/* 1153 */           this.prevTakeIndex = j;
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
/*      */ 
/*      */     
/*      */     private void detach() {
/* 1172 */       if (this.prevTakeIndex >= 0) {
/*      */         
/* 1174 */         this.prevTakeIndex = -3;
/*      */         
/* 1176 */         ArrayBlockingQueue.this.itrs.doSomeSweeping(true);
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
/*      */     public boolean hasNext() {
/* 1188 */       if (this.nextItem != null)
/* 1189 */         return true; 
/* 1190 */       noNext();
/* 1191 */       return false;
/*      */     }
/*      */     
/*      */     private void noNext() {
/* 1195 */       ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
/* 1196 */       reentrantLock.lock();
/*      */ 
/*      */       
/*      */       try {
/* 1200 */         if (!isDetached()) {
/*      */           
/* 1202 */           incorporateDequeues();
/* 1203 */           if (this.lastRet >= 0) {
/* 1204 */             this.lastItem = ArrayBlockingQueue.this.itemAt(this.lastRet);
/*      */             
/* 1206 */             detach();
/*      */           }
/*      */         
/*      */         } 
/*      */       } finally {
/*      */         
/* 1212 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public E next() {
/* 1218 */       E e = this.nextItem;
/* 1219 */       if (e == null)
/* 1220 */         throw new NoSuchElementException(); 
/* 1221 */       ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
/* 1222 */       reentrantLock.lock();
/*      */       try {
/* 1224 */         if (!isDetached()) {
/* 1225 */           incorporateDequeues();
/*      */         }
/*      */         
/* 1228 */         this.lastRet = this.nextIndex;
/* 1229 */         int i = this.cursor;
/* 1230 */         if (i >= 0) {
/* 1231 */           this.nextItem = ArrayBlockingQueue.this.itemAt(this.nextIndex = i);
/*      */           
/* 1233 */           this.cursor = incCursor(i);
/*      */         } else {
/* 1235 */           this.nextIndex = -1;
/* 1236 */           this.nextItem = null;
/*      */         } 
/*      */       } finally {
/* 1239 */         reentrantLock.unlock();
/*      */       } 
/* 1241 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1246 */       ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
/* 1247 */       reentrantLock.lock();
/*      */       try {
/* 1249 */         if (!isDetached())
/* 1250 */           incorporateDequeues(); 
/* 1251 */         int i = this.lastRet;
/* 1252 */         this.lastRet = -1;
/* 1253 */         if (i >= 0) {
/* 1254 */           if (!isDetached()) {
/* 1255 */             ArrayBlockingQueue.this.removeAt(i);
/*      */           } else {
/* 1257 */             E e = this.lastItem;
/*      */             
/* 1259 */             this.lastItem = null;
/* 1260 */             if (ArrayBlockingQueue.this.itemAt(i) == e)
/* 1261 */               ArrayBlockingQueue.this.removeAt(i); 
/*      */           } 
/* 1263 */         } else if (i == -1) {
/* 1264 */           throw new IllegalStateException();
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1269 */         if (this.cursor < 0 && this.nextIndex < 0)
/* 1270 */           detach(); 
/*      */       } finally {
/* 1272 */         reentrantLock.unlock();
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
/*      */     void shutdown() {
/* 1286 */       this.cursor = -1;
/* 1287 */       if (this.nextIndex >= 0)
/* 1288 */         this.nextIndex = -2; 
/* 1289 */       if (this.lastRet >= 0) {
/* 1290 */         this.lastRet = -2;
/* 1291 */         this.lastItem = null;
/*      */       } 
/* 1293 */       this.prevTakeIndex = -3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int distance(int param1Int1, int param1Int2, int param1Int3) {
/* 1301 */       int i = param1Int1 - param1Int2;
/* 1302 */       if (i < 0)
/* 1303 */         i += param1Int3; 
/* 1304 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean removedAt(int param1Int) {
/* 1314 */       if (isDetached()) {
/* 1315 */         return true;
/*      */       }
/* 1317 */       int i = ArrayBlockingQueue.this.itrs.cycles;
/* 1318 */       int j = ArrayBlockingQueue.this.takeIndex;
/* 1319 */       int k = this.prevCycles;
/* 1320 */       int m = this.prevTakeIndex;
/* 1321 */       int n = ArrayBlockingQueue.this.items.length;
/* 1322 */       int i1 = i - k;
/* 1323 */       if (param1Int < j)
/* 1324 */         i1++; 
/* 1325 */       int i2 = i1 * n + param1Int - m;
/*      */ 
/*      */       
/* 1328 */       int i3 = this.cursor;
/* 1329 */       if (i3 >= 0) {
/* 1330 */         int i6 = distance(i3, m, n);
/* 1331 */         if (i6 == i2) {
/* 1332 */           if (i3 == ArrayBlockingQueue.this.putIndex) {
/* 1333 */             this.cursor = i3 = -1;
/*      */           }
/* 1335 */         } else if (i6 > i2) {
/*      */           
/* 1337 */           this.cursor = i3 = ArrayBlockingQueue.this.dec(i3);
/*      */         } 
/*      */       } 
/* 1340 */       int i4 = this.lastRet;
/* 1341 */       if (i4 >= 0) {
/* 1342 */         int i6 = distance(i4, m, n);
/* 1343 */         if (i6 == i2) {
/* 1344 */           this.lastRet = i4 = -2;
/* 1345 */         } else if (i6 > i2) {
/* 1346 */           this.lastRet = i4 = ArrayBlockingQueue.this.dec(i4);
/*      */         } 
/* 1348 */       }  int i5 = this.nextIndex;
/* 1349 */       if (i5 >= 0) {
/* 1350 */         int i6 = distance(i5, m, n);
/* 1351 */         if (i6 == i2) {
/* 1352 */           this.nextIndex = i5 = -2;
/* 1353 */         } else if (i6 > i2) {
/* 1354 */           this.nextIndex = i5 = ArrayBlockingQueue.this.dec(i5);
/*      */         } 
/* 1356 */       } else if (i3 < 0 && i5 < 0 && i4 < 0) {
/* 1357 */         this.prevTakeIndex = -3;
/* 1358 */         return true;
/*      */       } 
/* 1360 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean takeIndexWrapped() {
/* 1370 */       if (isDetached())
/* 1371 */         return true; 
/* 1372 */       if (ArrayBlockingQueue.this.itrs.cycles - this.prevCycles > 1) {
/*      */ 
/*      */         
/* 1375 */         shutdown();
/* 1376 */         return true;
/*      */       } 
/* 1378 */       return false;
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
/*      */   public Spliterator<E> spliterator() {
/* 1412 */     return 
/* 1413 */       Spliterators.spliterator(this, 4368);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1430 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */ 
/*      */     
/* 1434 */     if (this.items.length == 0 || this.takeIndex < 0 || this.takeIndex >= this.items.length || this.putIndex < 0 || this.putIndex >= this.items.length || this.count < 0 || this.count > this.items.length || 
/*      */ 
/*      */ 
/*      */       
/* 1438 */       Math.floorMod(this.putIndex - this.takeIndex, this.items.length) != 
/* 1439 */       Math.floorMod(this.count, this.items.length))
/* 1440 */       throw new InvalidObjectException("invariants violated"); 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ArrayBlockingQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */