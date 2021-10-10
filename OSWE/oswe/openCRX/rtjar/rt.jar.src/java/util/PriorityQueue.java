/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.function.Consumer;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PriorityQueue<E>
/*     */   extends AbstractQueue<E>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7720805057305804111L;
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 11;
/*     */   transient Object[] queue;
/* 103 */   private int size = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Comparator<? super E> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   transient int modCount = 0;
/*     */ 
/*     */   
/*     */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*     */ 
/*     */ 
/*     */   
/*     */   public PriorityQueue() {
/* 123 */     this(11, null);
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
/*     */   public PriorityQueue(int paramInt) {
/* 136 */     this(paramInt, null);
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
/*     */   public PriorityQueue(Comparator<? super E> paramComparator) {
/* 149 */     this(11, paramComparator);
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
/*     */   public PriorityQueue(int paramInt, Comparator<? super E> paramComparator) {
/* 167 */     if (paramInt < 1)
/* 168 */       throw new IllegalArgumentException(); 
/* 169 */     this.queue = new Object[paramInt];
/* 170 */     this.comparator = paramComparator;
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
/*     */   public PriorityQueue(Collection<? extends E> paramCollection) {
/* 191 */     if (paramCollection instanceof SortedSet) {
/* 192 */       SortedSet<E> sortedSet = (SortedSet)paramCollection;
/* 193 */       this.comparator = sortedSet.comparator();
/* 194 */       initElementsFromCollection(sortedSet);
/*     */     }
/* 196 */     else if (paramCollection instanceof PriorityQueue) {
/* 197 */       PriorityQueue<E> priorityQueue = (PriorityQueue)paramCollection;
/* 198 */       this.comparator = priorityQueue.comparator();
/* 199 */       initFromPriorityQueue(priorityQueue);
/*     */     } else {
/*     */       
/* 202 */       this.comparator = null;
/* 203 */       initFromCollection(paramCollection);
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
/*     */   public PriorityQueue(PriorityQueue<? extends E> paramPriorityQueue) {
/* 223 */     this.comparator = paramPriorityQueue.comparator();
/* 224 */     initFromPriorityQueue(paramPriorityQueue);
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
/*     */   public PriorityQueue(SortedSet<? extends E> paramSortedSet) {
/* 242 */     this.comparator = paramSortedSet.comparator();
/* 243 */     initElementsFromCollection(paramSortedSet);
/*     */   }
/*     */   
/*     */   private void initFromPriorityQueue(PriorityQueue<? extends E> paramPriorityQueue) {
/* 247 */     if (paramPriorityQueue.getClass() == PriorityQueue.class) {
/* 248 */       this.queue = paramPriorityQueue.toArray();
/* 249 */       this.size = paramPriorityQueue.size();
/*     */     } else {
/* 251 */       initFromCollection(paramPriorityQueue);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initElementsFromCollection(Collection<? extends E> paramCollection) {
/* 256 */     Object[] arrayOfObject = paramCollection.toArray();
/*     */     
/* 258 */     if (arrayOfObject.getClass() != Object[].class)
/* 259 */       arrayOfObject = Arrays.copyOf(arrayOfObject, arrayOfObject.length, Object[].class); 
/* 260 */     int i = arrayOfObject.length;
/* 261 */     if (i == 1 || this.comparator != null)
/* 262 */       for (byte b = 0; b < i; b++) {
/* 263 */         if (arrayOfObject[b] == null)
/* 264 */           throw new NullPointerException(); 
/* 265 */       }   this.queue = arrayOfObject;
/* 266 */     this.size = arrayOfObject.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initFromCollection(Collection<? extends E> paramCollection) {
/* 275 */     initElementsFromCollection(paramCollection);
/* 276 */     heapify();
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
/*     */   private void grow(int paramInt) {
/* 293 */     int i = this.queue.length;
/*     */     
/* 295 */     int j = i + ((i < 64) ? (i + 2) : (i >> 1));
/*     */ 
/*     */ 
/*     */     
/* 299 */     if (j - 2147483639 > 0)
/* 300 */       j = hugeCapacity(paramInt); 
/* 301 */     this.queue = Arrays.copyOf(this.queue, j);
/*     */   }
/*     */   
/*     */   private static int hugeCapacity(int paramInt) {
/* 305 */     if (paramInt < 0)
/* 306 */       throw new OutOfMemoryError(); 
/* 307 */     return (paramInt > 2147483639) ? Integer.MAX_VALUE : 2147483639;
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
/*     */   public boolean add(E paramE) {
/* 322 */     return offer(paramE);
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
/*     */   public boolean offer(E paramE) {
/* 335 */     if (paramE == null)
/* 336 */       throw new NullPointerException(); 
/* 337 */     this.modCount++;
/* 338 */     int i = this.size;
/* 339 */     if (i >= this.queue.length)
/* 340 */       grow(i + 1); 
/* 341 */     this.size = i + 1;
/* 342 */     if (i == 0) {
/* 343 */       this.queue[0] = paramE;
/*     */     } else {
/* 345 */       siftUp(i, paramE);
/* 346 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public E peek() {
/* 351 */     return (this.size == 0) ? null : (E)this.queue[0];
/*     */   }
/*     */   
/*     */   private int indexOf(Object paramObject) {
/* 355 */     if (paramObject != null)
/* 356 */       for (byte b = 0; b < this.size; b++) {
/* 357 */         if (paramObject.equals(this.queue[b]))
/* 358 */           return b; 
/*     */       }  
/* 360 */     return -1;
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
/* 375 */     int i = indexOf(paramObject);
/* 376 */     if (i == -1) {
/* 377 */       return false;
/*     */     }
/* 379 */     removeAt(i);
/* 380 */     return true;
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
/*     */   boolean removeEq(Object paramObject) {
/* 392 */     for (byte b = 0; b < this.size; b++) {
/* 393 */       if (paramObject == this.queue[b]) {
/* 394 */         removeAt(b);
/* 395 */         return true;
/*     */       } 
/*     */     } 
/* 398 */     return false;
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
/* 410 */     return (indexOf(paramObject) != -1);
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
/*     */   public Object[] toArray() {
/* 427 */     return Arrays.copyOf(this.queue, this.size);
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
/* 468 */     int i = this.size;
/* 469 */     if (paramArrayOfT.length < i)
/*     */     {
/* 471 */       return (T[])Arrays.<Object, Object>copyOf(this.queue, i, (Class)paramArrayOfT.getClass()); } 
/* 472 */     System.arraycopy(this.queue, 0, paramArrayOfT, 0, i);
/* 473 */     if (paramArrayOfT.length > i)
/* 474 */       paramArrayOfT[i] = null; 
/* 475 */     return paramArrayOfT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 485 */     return new Itr();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final class Itr
/*     */     implements Iterator<E>
/*     */   {
/* 493 */     private int cursor = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 500 */     private int lastRet = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 513 */     private ArrayDeque<E> forgetMeNot = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 519 */     private E lastRetElt = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 526 */     private int expectedModCount = PriorityQueue.this.modCount;
/*     */     
/*     */     public boolean hasNext() {
/* 529 */       return (this.cursor < PriorityQueue.this.size || (this.forgetMeNot != null && 
/* 530 */         !this.forgetMeNot.isEmpty()));
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 535 */       if (this.expectedModCount != PriorityQueue.this.modCount)
/* 536 */         throw new ConcurrentModificationException(); 
/* 537 */       if (this.cursor < PriorityQueue.this.size)
/* 538 */         return (E)PriorityQueue.this.queue[this.lastRet = this.cursor++]; 
/* 539 */       if (this.forgetMeNot != null) {
/* 540 */         this.lastRet = -1;
/* 541 */         this.lastRetElt = this.forgetMeNot.poll();
/* 542 */         if (this.lastRetElt != null)
/* 543 */           return this.lastRetElt; 
/*     */       } 
/* 545 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 549 */       if (this.expectedModCount != PriorityQueue.this.modCount)
/* 550 */         throw new ConcurrentModificationException(); 
/* 551 */       if (this.lastRet != -1) {
/* 552 */         Object object = PriorityQueue.this.removeAt(this.lastRet);
/* 553 */         this.lastRet = -1;
/* 554 */         if (object == null) {
/* 555 */           this.cursor--;
/*     */         } else {
/* 557 */           if (this.forgetMeNot == null)
/* 558 */             this.forgetMeNot = new ArrayDeque<>(); 
/* 559 */           this.forgetMeNot.add((E)object);
/*     */         } 
/* 561 */       } else if (this.lastRetElt != null) {
/* 562 */         PriorityQueue.this.removeEq(this.lastRetElt);
/* 563 */         this.lastRetElt = null;
/*     */       } else {
/* 565 */         throw new IllegalStateException();
/*     */       } 
/* 567 */       this.expectedModCount = PriorityQueue.this.modCount;
/*     */     }
/*     */     private Itr() {} }
/*     */   
/*     */   public int size() {
/* 572 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 580 */     this.modCount++;
/* 581 */     for (byte b = 0; b < this.size; b++)
/* 582 */       this.queue[b] = null; 
/* 583 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public E poll() {
/* 588 */     if (this.size == 0)
/* 589 */       return null; 
/* 590 */     int i = --this.size;
/* 591 */     this.modCount++;
/* 592 */     Object object1 = this.queue[0];
/* 593 */     Object object2 = this.queue[i];
/* 594 */     this.queue[i] = null;
/* 595 */     if (i != 0)
/* 596 */       siftDown(0, (E)object2); 
/* 597 */     return (E)object1;
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
/*     */   private E removeAt(int paramInt) {
/* 615 */     this.modCount++;
/* 616 */     int i = --this.size;
/* 617 */     if (i == paramInt) {
/* 618 */       this.queue[paramInt] = null;
/*     */     } else {
/* 620 */       Object object = this.queue[i];
/* 621 */       this.queue[i] = null;
/* 622 */       siftDown(paramInt, (E)object);
/* 623 */       if (this.queue[paramInt] == object) {
/* 624 */         siftUp(paramInt, (E)object);
/* 625 */         if (this.queue[paramInt] != object)
/* 626 */           return (E)object; 
/*     */       } 
/*     */     } 
/* 629 */     return null;
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
/*     */   private void siftUp(int paramInt, E paramE) {
/* 645 */     if (this.comparator != null) {
/* 646 */       siftUpUsingComparator(paramInt, paramE);
/*     */     } else {
/* 648 */       siftUpComparable(paramInt, paramE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void siftUpComparable(int paramInt, E paramE) {
/* 653 */     Comparable<Object> comparable = (Comparable)paramE;
/* 654 */     while (paramInt > 0) {
/* 655 */       int i = paramInt - 1 >>> 1;
/* 656 */       Object object = this.queue[i];
/* 657 */       if (comparable.compareTo(object) >= 0)
/*     */         break; 
/* 659 */       this.queue[paramInt] = object;
/* 660 */       paramInt = i;
/*     */     } 
/* 662 */     this.queue[paramInt] = comparable;
/*     */   }
/*     */ 
/*     */   
/*     */   private void siftUpUsingComparator(int paramInt, E paramE) {
/* 667 */     while (paramInt > 0) {
/* 668 */       int i = paramInt - 1 >>> 1;
/* 669 */       Object object = this.queue[i];
/* 670 */       if (this.comparator.compare(paramE, (E)object) >= 0)
/*     */         break; 
/* 672 */       this.queue[paramInt] = object;
/* 673 */       paramInt = i;
/*     */     } 
/* 675 */     this.queue[paramInt] = paramE;
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
/*     */   private void siftDown(int paramInt, E paramE) {
/* 687 */     if (this.comparator != null) {
/* 688 */       siftDownUsingComparator(paramInt, paramE);
/*     */     } else {
/* 690 */       siftDownComparable(paramInt, paramE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void siftDownComparable(int paramInt, E paramE) {
/* 695 */     Comparable<Object> comparable = (Comparable)paramE;
/* 696 */     int i = this.size >>> 1;
/* 697 */     while (paramInt < i) {
/* 698 */       int j = (paramInt << 1) + 1;
/* 699 */       Object object = this.queue[j];
/* 700 */       int k = j + 1;
/* 701 */       if (k < this.size && ((Comparable<Object>)object)
/* 702 */         .compareTo(this.queue[k]) > 0)
/* 703 */         object = this.queue[j = k]; 
/* 704 */       if (comparable.compareTo(object) <= 0)
/*     */         break; 
/* 706 */       this.queue[paramInt] = object;
/* 707 */       paramInt = j;
/*     */     } 
/* 709 */     this.queue[paramInt] = comparable;
/*     */   }
/*     */ 
/*     */   
/*     */   private void siftDownUsingComparator(int paramInt, E paramE) {
/* 714 */     int i = this.size >>> 1;
/* 715 */     while (paramInt < i) {
/* 716 */       int j = (paramInt << 1) + 1;
/* 717 */       Object object = this.queue[j];
/* 718 */       int k = j + 1;
/* 719 */       if (k < this.size && this.comparator
/* 720 */         .compare((E)object, (E)this.queue[k]) > 0)
/* 721 */         object = this.queue[j = k]; 
/* 722 */       if (this.comparator.compare(paramE, (E)object) <= 0)
/*     */         break; 
/* 724 */       this.queue[paramInt] = object;
/* 725 */       paramInt = j;
/*     */     } 
/* 727 */     this.queue[paramInt] = paramE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void heapify() {
/* 736 */     for (int i = (this.size >>> 1) - 1; i >= 0; i--) {
/* 737 */       siftDown(i, (E)this.queue[i]);
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
/*     */   public Comparator<? super E> comparator() {
/* 750 */     return this.comparator;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 764 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 767 */     paramObjectOutputStream.writeInt(Math.max(2, this.size + 1));
/*     */ 
/*     */     
/* 770 */     for (byte b = 0; b < this.size; b++) {
/* 771 */       paramObjectOutputStream.writeObject(this.queue[b]);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 783 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 786 */     paramObjectInputStream.readInt();
/*     */     
/* 788 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Object[].class, this.size);
/* 789 */     this.queue = new Object[this.size];
/*     */ 
/*     */     
/* 792 */     for (byte b = 0; b < this.size; b++) {
/* 793 */       this.queue[b] = paramObjectInputStream.readObject();
/*     */     }
/*     */ 
/*     */     
/* 797 */     heapify();
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
/*     */   public final Spliterator<E> spliterator() {
/* 814 */     return new PriorityQueueSpliterator<>(this, 0, -1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   static final class PriorityQueueSpliterator<E>
/*     */     implements Spliterator<E>
/*     */   {
/*     */     private final PriorityQueue<E> pq;
/*     */     
/*     */     private int index;
/*     */     
/*     */     private int fence;
/*     */     
/*     */     private int expectedModCount;
/*     */     
/*     */     PriorityQueueSpliterator(PriorityQueue<E> param1PriorityQueue, int param1Int1, int param1Int2, int param1Int3) {
/* 830 */       this.pq = param1PriorityQueue;
/* 831 */       this.index = param1Int1;
/* 832 */       this.fence = param1Int2;
/* 833 */       this.expectedModCount = param1Int3;
/*     */     }
/*     */     
/*     */     private int getFence() {
/*     */       int i;
/* 838 */       if ((i = this.fence) < 0) {
/* 839 */         this.expectedModCount = this.pq.modCount;
/* 840 */         i = this.fence = this.pq.size;
/*     */       } 
/* 842 */       return i;
/*     */     }
/*     */     
/*     */     public PriorityQueueSpliterator<E> trySplit() {
/* 846 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 847 */       return (j >= k) ? null : new PriorityQueueSpliterator(this.pq, j, this.index = k, this.expectedModCount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 856 */       if (param1Consumer == null)
/* 857 */         throw new NullPointerException();  PriorityQueue<E> priorityQueue; Object[] arrayOfObject;
/* 858 */       if ((priorityQueue = this.pq) != null && (arrayOfObject = priorityQueue.queue) != null) {
/* 859 */         int k; int j; if ((j = this.fence) < 0) {
/* 860 */           k = priorityQueue.modCount;
/* 861 */           j = priorityQueue.size;
/*     */         } else {
/*     */           
/* 864 */           k = this.expectedModCount;
/* 865 */         }  int i; if ((i = this.index) >= 0 && (this.index = j) <= arrayOfObject.length)
/* 866 */           for (;; i++) {
/* 867 */             if (i < j) {
/* 868 */               Object object; if ((object = arrayOfObject[i]) == null)
/*     */                 break; 
/* 870 */               param1Consumer.accept((E)object);
/*     */             } else {
/* 872 */               if (priorityQueue.modCount != k) {
/*     */                 break;
/*     */               }
/*     */               return;
/*     */             } 
/*     */           }  
/*     */       } 
/* 879 */       throw new ConcurrentModificationException();
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 883 */       if (param1Consumer == null)
/* 884 */         throw new NullPointerException(); 
/* 885 */       int i = getFence(), j = this.index;
/* 886 */       if (j >= 0 && j < i) {
/* 887 */         this.index = j + 1;
/* 888 */         Object object = this.pq.queue[j];
/* 889 */         if (object == null)
/* 890 */           throw new ConcurrentModificationException(); 
/* 891 */         param1Consumer.accept((E)object);
/* 892 */         if (this.pq.modCount != this.expectedModCount)
/* 893 */           throw new ConcurrentModificationException(); 
/* 894 */         return true;
/*     */       } 
/* 896 */       return false;
/*     */     }
/*     */     
/*     */     public long estimateSize() {
/* 900 */       return (getFence() - this.index);
/*     */     }
/*     */     
/*     */     public int characteristics() {
/* 904 */       return 16704;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/PriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */