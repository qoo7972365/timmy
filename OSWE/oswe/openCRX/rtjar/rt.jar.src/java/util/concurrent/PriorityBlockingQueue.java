/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.PriorityQueue;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.Consumer;
/*      */ import sun.misc.SharedSecrets;
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
/*      */ public class PriorityBlockingQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 5595510919245408276L;
/*      */   private static final int DEFAULT_INITIAL_CAPACITY = 11;
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   private transient Object[] queue;
/*      */   private transient int size;
/*      */   private transient Comparator<? super E> comparator;
/*      */   private final ReentrantLock lock;
/*      */   private final Condition notEmpty;
/*      */   private volatile transient int allocationSpinLock;
/*      */   private PriorityQueue<E> q;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long allocationSpinLockOffset;
/*      */   
/*      */   public PriorityBlockingQueue() {
/*  192 */     this(11, null);
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
/*      */   public PriorityBlockingQueue(int paramInt) {
/*  205 */     this(paramInt, null);
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
/*      */   public PriorityBlockingQueue(int paramInt, Comparator<? super E> paramComparator) {
/*  222 */     if (paramInt < 1)
/*  223 */       throw new IllegalArgumentException(); 
/*  224 */     this.lock = new ReentrantLock();
/*  225 */     this.notEmpty = this.lock.newCondition();
/*  226 */     this.comparator = paramComparator;
/*  227 */     this.queue = new Object[paramInt];
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
/*      */   public PriorityBlockingQueue(Collection<? extends E> paramCollection) {
/*  247 */     this.lock = new ReentrantLock();
/*  248 */     this.notEmpty = this.lock.newCondition();
/*  249 */     boolean bool1 = true;
/*  250 */     boolean bool2 = true;
/*  251 */     if (paramCollection instanceof SortedSet) {
/*  252 */       SortedSet<E> sortedSet = (SortedSet)paramCollection;
/*  253 */       this.comparator = sortedSet.comparator();
/*  254 */       bool1 = false;
/*      */     }
/*  256 */     else if (paramCollection instanceof PriorityBlockingQueue) {
/*  257 */       PriorityBlockingQueue<E> priorityBlockingQueue = (PriorityBlockingQueue)paramCollection;
/*      */       
/*  259 */       this.comparator = priorityBlockingQueue.comparator();
/*  260 */       bool2 = false;
/*  261 */       if (priorityBlockingQueue.getClass() == PriorityBlockingQueue.class)
/*  262 */         bool1 = false; 
/*      */     } 
/*  264 */     Object[] arrayOfObject = paramCollection.toArray();
/*  265 */     int i = arrayOfObject.length;
/*      */     
/*  267 */     if (arrayOfObject.getClass() != Object[].class)
/*  268 */       arrayOfObject = Arrays.copyOf(arrayOfObject, i, Object[].class); 
/*  269 */     if (bool2 && (i == 1 || this.comparator != null))
/*  270 */       for (byte b = 0; b < i; b++) {
/*  271 */         if (arrayOfObject[b] == null)
/*  272 */           throw new NullPointerException(); 
/*      */       }  
/*  274 */     this.queue = arrayOfObject;
/*  275 */     this.size = i;
/*  276 */     if (bool1) {
/*  277 */       heapify();
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
/*      */   private void tryGrow(Object[] paramArrayOfObject, int paramInt) {
/*  290 */     this.lock.unlock();
/*  291 */     Object[] arrayOfObject = null;
/*  292 */     if (this.allocationSpinLock == 0 && UNSAFE
/*  293 */       .compareAndSwapInt(this, allocationSpinLockOffset, 0, 1)) {
/*      */       
/*      */       try {
/*  296 */         int i = paramInt + ((paramInt < 64) ? (paramInt + 2) : (paramInt >> 1));
/*      */ 
/*      */         
/*  299 */         if (i - 2147483639 > 0) {
/*  300 */           int j = paramInt + 1;
/*  301 */           if (j < 0 || j > 2147483639)
/*  302 */             throw new OutOfMemoryError(); 
/*  303 */           i = 2147483639;
/*      */         } 
/*  305 */         if (i > paramInt && this.queue == paramArrayOfObject)
/*  306 */           arrayOfObject = new Object[i]; 
/*      */       } finally {
/*  308 */         this.allocationSpinLock = 0;
/*      */       } 
/*      */     }
/*  311 */     if (arrayOfObject == null)
/*  312 */       Thread.yield(); 
/*  313 */     this.lock.lock();
/*  314 */     if (arrayOfObject != null && this.queue == paramArrayOfObject) {
/*  315 */       this.queue = arrayOfObject;
/*  316 */       System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E dequeue() {
/*  324 */     int i = this.size - 1;
/*  325 */     if (i < 0) {
/*  326 */       return null;
/*      */     }
/*  328 */     Object[] arrayOfObject = this.queue;
/*  329 */     Object object1 = arrayOfObject[0];
/*  330 */     Object object2 = arrayOfObject[i];
/*  331 */     arrayOfObject[i] = null;
/*  332 */     Comparator<? super E> comparator = this.comparator;
/*  333 */     if (comparator == null) {
/*  334 */       siftDownComparable(0, object2, arrayOfObject, i);
/*      */     } else {
/*  336 */       siftDownUsingComparator(0, object2, arrayOfObject, i, (Comparator)comparator);
/*  337 */     }  this.size = i;
/*  338 */     return (E)object1;
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
/*      */   private static <T> void siftUpComparable(int paramInt, T paramT, Object[] paramArrayOfObject) {
/*  358 */     Comparable<Object> comparable = (Comparable)paramT;
/*  359 */     while (paramInt > 0) {
/*  360 */       int i = paramInt - 1 >>> 1;
/*  361 */       Object object = paramArrayOfObject[i];
/*  362 */       if (comparable.compareTo(object) >= 0)
/*      */         break; 
/*  364 */       paramArrayOfObject[paramInt] = object;
/*  365 */       paramInt = i;
/*      */     } 
/*  367 */     paramArrayOfObject[paramInt] = comparable;
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> void siftUpUsingComparator(int paramInt, T paramT, Object[] paramArrayOfObject, Comparator<? super T> paramComparator) {
/*  372 */     while (paramInt > 0) {
/*  373 */       int i = paramInt - 1 >>> 1;
/*  374 */       Object object = paramArrayOfObject[i];
/*  375 */       if (paramComparator.compare(paramT, (T)object) >= 0)
/*      */         break; 
/*  377 */       paramArrayOfObject[paramInt] = object;
/*  378 */       paramInt = i;
/*      */     } 
/*  380 */     paramArrayOfObject[paramInt] = paramT;
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
/*      */   private static <T> void siftDownComparable(int paramInt1, T paramT, Object[] paramArrayOfObject, int paramInt2) {
/*  395 */     if (paramInt2 > 0) {
/*  396 */       Comparable<Object> comparable = (Comparable)paramT;
/*  397 */       int i = paramInt2 >>> 1;
/*  398 */       while (paramInt1 < i) {
/*  399 */         int j = (paramInt1 << 1) + 1;
/*  400 */         Object object = paramArrayOfObject[j];
/*  401 */         int k = j + 1;
/*  402 */         if (k < paramInt2 && ((Comparable<Object>)object)
/*  403 */           .compareTo(paramArrayOfObject[k]) > 0)
/*  404 */           object = paramArrayOfObject[j = k]; 
/*  405 */         if (comparable.compareTo(object) <= 0)
/*      */           break; 
/*  407 */         paramArrayOfObject[paramInt1] = object;
/*  408 */         paramInt1 = j;
/*      */       } 
/*  410 */       paramArrayOfObject[paramInt1] = comparable;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> void siftDownUsingComparator(int paramInt1, T paramT, Object[] paramArrayOfObject, int paramInt2, Comparator<? super T> paramComparator) {
/*  417 */     if (paramInt2 > 0) {
/*  418 */       int i = paramInt2 >>> 1;
/*  419 */       while (paramInt1 < i) {
/*  420 */         int j = (paramInt1 << 1) + 1;
/*  421 */         Object object = paramArrayOfObject[j];
/*  422 */         int k = j + 1;
/*  423 */         if (k < paramInt2 && paramComparator.compare((T)object, (T)paramArrayOfObject[k]) > 0)
/*  424 */           object = paramArrayOfObject[j = k]; 
/*  425 */         if (paramComparator.compare(paramT, (T)object) <= 0)
/*      */           break; 
/*  427 */         paramArrayOfObject[paramInt1] = object;
/*  428 */         paramInt1 = j;
/*      */       } 
/*  430 */       paramArrayOfObject[paramInt1] = paramT;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void heapify() {
/*  439 */     Object[] arrayOfObject = this.queue;
/*  440 */     int i = this.size;
/*  441 */     int j = (i >>> 1) - 1;
/*  442 */     Comparator<? super E> comparator = this.comparator;
/*  443 */     if (comparator == null) {
/*  444 */       for (int k = j; k >= 0; k--) {
/*  445 */         siftDownComparable(k, arrayOfObject[k], arrayOfObject, i);
/*      */       }
/*      */     } else {
/*  448 */       for (int k = j; k >= 0; k--) {
/*  449 */         siftDownUsingComparator(k, arrayOfObject[k], arrayOfObject, i, (Comparator)comparator);
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
/*      */   public boolean add(E paramE) {
/*  464 */     return offer(paramE);
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
/*  479 */     if (paramE == null)
/*  480 */       throw new NullPointerException(); 
/*  481 */     ReentrantLock reentrantLock = this.lock;
/*  482 */     reentrantLock.lock();
/*      */     int i, j;
/*      */     Object[] arrayOfObject;
/*  485 */     while ((i = this.size) >= (j = (arrayOfObject = this.queue).length))
/*  486 */       tryGrow(arrayOfObject, j); 
/*      */     try {
/*  488 */       Comparator<? super E> comparator = this.comparator;
/*  489 */       if (comparator == null) {
/*  490 */         siftUpComparable(i, paramE, arrayOfObject);
/*      */       } else {
/*  492 */         siftUpUsingComparator(i, paramE, arrayOfObject, comparator);
/*  493 */       }  this.size = i + 1;
/*  494 */       this.notEmpty.signal();
/*      */     } finally {
/*  496 */       reentrantLock.unlock();
/*      */     } 
/*  498 */     return true;
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
/*      */   public void put(E paramE) {
/*  512 */     offer(paramE);
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
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit) {
/*  531 */     return offer(paramE);
/*      */   }
/*      */   
/*      */   public E poll() {
/*  535 */     ReentrantLock reentrantLock = this.lock;
/*  536 */     reentrantLock.lock();
/*      */     try {
/*  538 */       return dequeue();
/*      */     } finally {
/*  540 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   public E take() throws InterruptedException {
/*      */     E e;
/*  545 */     ReentrantLock reentrantLock = this.lock;
/*  546 */     reentrantLock.lockInterruptibly();
/*      */     
/*      */     try {
/*  549 */       while ((e = dequeue()) == null)
/*  550 */         this.notEmpty.await(); 
/*      */     } finally {
/*  552 */       reentrantLock.unlock();
/*      */     } 
/*  554 */     return e;
/*      */   }
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*      */     E e;
/*  558 */     long l = paramTimeUnit.toNanos(paramLong);
/*  559 */     ReentrantLock reentrantLock = this.lock;
/*  560 */     reentrantLock.lockInterruptibly();
/*      */     
/*      */     try {
/*  563 */       while ((e = dequeue()) == null && l > 0L)
/*  564 */         l = this.notEmpty.awaitNanos(l); 
/*      */     } finally {
/*  566 */       reentrantLock.unlock();
/*      */     } 
/*  568 */     return e;
/*      */   }
/*      */   
/*      */   public E peek() {
/*  572 */     ReentrantLock reentrantLock = this.lock;
/*  573 */     reentrantLock.lock();
/*      */     try {
/*  575 */       return (E)((this.size == 0) ? null : this.queue[0]);
/*      */     } finally {
/*  577 */       reentrantLock.unlock();
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
/*      */   public Comparator<? super E> comparator() {
/*  591 */     return this.comparator;
/*      */   }
/*      */   
/*      */   public int size() {
/*  595 */     ReentrantLock reentrantLock = this.lock;
/*  596 */     reentrantLock.lock();
/*      */     try {
/*  598 */       return this.size;
/*      */     } finally {
/*  600 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int remainingCapacity() {
/*  610 */     return Integer.MAX_VALUE;
/*      */   }
/*      */   
/*      */   private int indexOf(Object paramObject) {
/*  614 */     if (paramObject != null) {
/*  615 */       Object[] arrayOfObject = this.queue;
/*  616 */       int i = this.size;
/*  617 */       for (byte b = 0; b < i; b++) {
/*  618 */         if (paramObject.equals(arrayOfObject[b]))
/*  619 */           return b; 
/*      */       } 
/*  621 */     }  return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeAt(int paramInt) {
/*  628 */     Object[] arrayOfObject = this.queue;
/*  629 */     int i = this.size - 1;
/*  630 */     if (i == paramInt) {
/*  631 */       arrayOfObject[paramInt] = null;
/*      */     } else {
/*  633 */       Object object = arrayOfObject[i];
/*  634 */       arrayOfObject[i] = null;
/*  635 */       Comparator<? super E> comparator = this.comparator;
/*  636 */       if (comparator == null) {
/*  637 */         siftDownComparable(paramInt, object, arrayOfObject, i);
/*      */       } else {
/*  639 */         siftDownUsingComparator(paramInt, object, arrayOfObject, i, (Comparator)comparator);
/*  640 */       }  if (arrayOfObject[paramInt] == object)
/*  641 */         if (comparator == null) {
/*  642 */           siftUpComparable(paramInt, object, arrayOfObject);
/*      */         } else {
/*  644 */           siftUpUsingComparator(paramInt, object, arrayOfObject, (Comparator)comparator);
/*      */         }  
/*      */     } 
/*  647 */     this.size = i;
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
/*  662 */     ReentrantLock reentrantLock = this.lock;
/*  663 */     reentrantLock.lock();
/*      */     try {
/*  665 */       int i = indexOf(paramObject);
/*  666 */       if (i == -1)
/*  667 */         return false; 
/*  668 */       removeAt(i);
/*  669 */       return true;
/*      */     } finally {
/*  671 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removeEQ(Object paramObject) {
/*  679 */     ReentrantLock reentrantLock = this.lock;
/*  680 */     reentrantLock.lock();
/*      */     try {
/*  682 */       Object[] arrayOfObject = this.queue; byte b; int i;
/*  683 */       for (b = 0, i = this.size; b < i; b++) {
/*  684 */         if (paramObject == arrayOfObject[b]) {
/*  685 */           removeAt(b);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  690 */       reentrantLock.unlock();
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
/*  703 */     ReentrantLock reentrantLock = this.lock;
/*  704 */     reentrantLock.lock();
/*      */     try {
/*  706 */       return (indexOf(paramObject) != -1);
/*      */     } finally {
/*  708 */       reentrantLock.unlock();
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
/*  726 */     ReentrantLock reentrantLock = this.lock;
/*  727 */     reentrantLock.lock();
/*      */     try {
/*  729 */       return Arrays.copyOf(this.queue, this.size);
/*      */     } finally {
/*  731 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public String toString() {
/*  736 */     ReentrantLock reentrantLock = this.lock;
/*  737 */     reentrantLock.lock();
/*      */     try {
/*  739 */       int i = this.size;
/*  740 */       if (i == 0)
/*  741 */         return "[]"; 
/*  742 */       StringBuilder stringBuilder = new StringBuilder();
/*  743 */       stringBuilder.append('[');
/*  744 */       for (byte b = 0; b < i; b++) {
/*  745 */         Object object = this.queue[b];
/*  746 */         stringBuilder.append((object == this) ? "(this Collection)" : object);
/*  747 */         if (b != i - 1)
/*  748 */           stringBuilder.append(',').append(' '); 
/*      */       } 
/*  750 */       return stringBuilder.append(']').toString();
/*      */     } finally {
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
/*      */   public int drainTo(Collection<? super E> paramCollection) {
/*  763 */     return drainTo(paramCollection, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt) {
/*  773 */     if (paramCollection == null)
/*  774 */       throw new NullPointerException(); 
/*  775 */     if (paramCollection == this)
/*  776 */       throw new IllegalArgumentException(); 
/*  777 */     if (paramInt <= 0)
/*  778 */       return 0; 
/*  779 */     ReentrantLock reentrantLock = this.lock;
/*  780 */     reentrantLock.lock();
/*      */     try {
/*  782 */       int i = Math.min(this.size, paramInt); int j;
/*  783 */       for (j = 0; j < i; j++) {
/*  784 */         paramCollection.add((E)this.queue[0]);
/*  785 */         dequeue();
/*      */       } 
/*  787 */       j = i; return j;
/*      */     } finally {
/*  789 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  798 */     ReentrantLock reentrantLock = this.lock;
/*  799 */     reentrantLock.lock();
/*      */     try {
/*  801 */       Object[] arrayOfObject = this.queue;
/*  802 */       int i = this.size;
/*  803 */       this.size = 0;
/*  804 */       for (byte b = 0; b < i; b++)
/*  805 */         arrayOfObject[b] = null; 
/*      */     } finally {
/*  807 */       reentrantLock.unlock();
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
/*  848 */     ReentrantLock reentrantLock = this.lock;
/*  849 */     reentrantLock.lock();
/*      */     try {
/*  851 */       int i = this.size;
/*  852 */       if (paramArrayOfT.length < i)
/*      */       {
/*  854 */         return (T[])Arrays.<Object, Object>copyOf(this.queue, this.size, (Class)paramArrayOfT.getClass()); } 
/*  855 */       System.arraycopy(this.queue, 0, paramArrayOfT, 0, i);
/*  856 */       if (paramArrayOfT.length > i)
/*  857 */         paramArrayOfT[i] = null; 
/*  858 */       return paramArrayOfT;
/*      */     } finally {
/*  860 */       reentrantLock.unlock();
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
/*  874 */     return new Itr(toArray());
/*      */   }
/*      */ 
/*      */   
/*      */   final class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     final Object[] array;
/*      */     int cursor;
/*      */     int lastRet;
/*      */     
/*      */     Itr(Object[] param1ArrayOfObject) {
/*  886 */       this.lastRet = -1;
/*  887 */       this.array = param1ArrayOfObject;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  891 */       return (this.cursor < this.array.length);
/*      */     }
/*      */     
/*      */     public E next() {
/*  895 */       if (this.cursor >= this.array.length)
/*  896 */         throw new NoSuchElementException(); 
/*  897 */       this.lastRet = this.cursor;
/*  898 */       return (E)this.array[this.cursor++];
/*      */     }
/*      */     
/*      */     public void remove() {
/*  902 */       if (this.lastRet < 0)
/*  903 */         throw new IllegalStateException(); 
/*  904 */       PriorityBlockingQueue.this.removeEQ(this.array[this.lastRet]);
/*  905 */       this.lastRet = -1;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  921 */     this.lock.lock();
/*      */     
/*      */     try {
/*  924 */       this.q = new PriorityQueue<>(Math.max(this.size, 1), this.comparator);
/*  925 */       this.q.addAll(this);
/*  926 */       paramObjectOutputStream.defaultWriteObject();
/*      */     } finally {
/*  928 */       this.q = null;
/*  929 */       this.lock.unlock();
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
/*      */     try {
/*  943 */       paramObjectInputStream.defaultReadObject();
/*  944 */       int i = this.q.size();
/*  945 */       SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Object[].class, i);
/*  946 */       this.queue = new Object[i];
/*  947 */       this.comparator = this.q.comparator();
/*  948 */       addAll(this.q);
/*      */     } finally {
/*  950 */       this.q = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static final class PBQSpliterator<E>
/*      */     implements Spliterator<E>
/*      */   {
/*      */     final PriorityBlockingQueue<E> queue;
/*      */     Object[] array;
/*      */     int index;
/*      */     int fence;
/*      */     
/*      */     PBQSpliterator(PriorityBlockingQueue<E> param1PriorityBlockingQueue, Object[] param1ArrayOfObject, int param1Int1, int param1Int2) {
/*  964 */       this.queue = param1PriorityBlockingQueue;
/*  965 */       this.array = param1ArrayOfObject;
/*  966 */       this.index = param1Int1;
/*  967 */       this.fence = param1Int2;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/*  972 */       if ((i = this.fence) < 0)
/*  973 */         i = this.fence = (this.array = this.queue.toArray()).length; 
/*  974 */       return i;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit() {
/*  978 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/*  979 */       return (j >= k) ? null : new PBQSpliterator(this.queue, this.array, j, this.index = k);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/*  986 */       if (param1Consumer == null)
/*  987 */         throw new NullPointerException();  Object[] arrayOfObject;
/*  988 */       if ((arrayOfObject = this.array) == null)
/*  989 */         this.fence = (arrayOfObject = this.queue.toArray()).length;  int i; int j;
/*  990 */       if ((j = this.fence) <= arrayOfObject.length && (i = this.index) >= 0 && i < (this.index = j)) {
/*      */         
/*  992 */         do { param1Consumer.accept((E)arrayOfObject[i]); } while (++i < j);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/*  997 */       if (param1Consumer == null)
/*  998 */         throw new NullPointerException(); 
/*  999 */       if (getFence() > this.index && this.index >= 0) {
/* 1000 */         Object object = this.array[this.index++];
/* 1001 */         param1Consumer.accept((E)object);
/* 1002 */         return true;
/*      */       } 
/* 1004 */       return false;
/*      */     }
/*      */     public long estimateSize() {
/* 1007 */       return (getFence() - this.index);
/*      */     }
/*      */     public int characteristics() {
/* 1010 */       return 16704;
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
/*      */   public Spliterator<E> spliterator() {
/* 1030 */     return new PBQSpliterator<>(this, null, 0, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1038 */       UNSAFE = Unsafe.getUnsafe();
/* 1039 */       Class<PriorityBlockingQueue> clazz = PriorityBlockingQueue.class;
/*      */       
/* 1041 */       allocationSpinLockOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("allocationSpinLock"));
/* 1042 */     } catch (Exception exception) {
/* 1043 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/PriorityBlockingQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */