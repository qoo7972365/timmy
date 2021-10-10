/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayDeque<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Deque<E>, Cloneable, Serializable
/*     */ {
/*     */   transient Object[] elements;
/*     */   transient int head;
/*     */   transient int tail;
/*     */   private static final int MIN_INITIAL_CAPACITY = 8;
/*     */   private static final long serialVersionUID = 2340985798034038923L;
/*     */   
/*     */   private static int calculateSize(int paramInt) {
/* 123 */     int i = 8;
/*     */ 
/*     */     
/* 126 */     if (paramInt >= i) {
/* 127 */       i = paramInt;
/* 128 */       i |= i >>> 1;
/* 129 */       i |= i >>> 2;
/* 130 */       i |= i >>> 4;
/* 131 */       i |= i >>> 8;
/* 132 */       i |= i >>> 16;
/* 133 */       i++;
/*     */       
/* 135 */       if (i < 0)
/* 136 */         i >>>= 1; 
/*     */     } 
/* 138 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void allocateElements(int paramInt) {
/* 147 */     this.elements = new Object[calculateSize(paramInt)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doubleCapacity() {
/* 155 */     assert this.head == this.tail;
/* 156 */     int i = this.head;
/* 157 */     int j = this.elements.length;
/* 158 */     int k = j - i;
/* 159 */     int m = j << 1;
/* 160 */     if (m < 0)
/* 161 */       throw new IllegalStateException("Sorry, deque too big"); 
/* 162 */     Object[] arrayOfObject = new Object[m];
/* 163 */     System.arraycopy(this.elements, i, arrayOfObject, 0, k);
/* 164 */     System.arraycopy(this.elements, 0, arrayOfObject, k, i);
/* 165 */     this.elements = arrayOfObject;
/* 166 */     this.head = 0;
/* 167 */     this.tail = j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> T[] copyElements(T[] paramArrayOfT) {
/* 178 */     if (this.head < this.tail) {
/* 179 */       System.arraycopy(this.elements, this.head, paramArrayOfT, 0, size());
/* 180 */     } else if (this.head > this.tail) {
/* 181 */       int i = this.elements.length - this.head;
/* 182 */       System.arraycopy(this.elements, this.head, paramArrayOfT, 0, i);
/* 183 */       System.arraycopy(this.elements, 0, paramArrayOfT, i, this.tail);
/*     */     } 
/* 185 */     return paramArrayOfT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayDeque() {
/* 193 */     this.elements = new Object[16];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayDeque(int paramInt) {
/* 203 */     allocateElements(paramInt);
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
/*     */   public ArrayDeque(Collection<? extends E> paramCollection) {
/* 217 */     allocateElements(paramCollection.size());
/* 218 */     addAll(paramCollection);
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
/*     */   public void addFirst(E paramE) {
/* 232 */     if (paramE == null)
/* 233 */       throw new NullPointerException(); 
/* 234 */     this.elements[this.head = this.head - 1 & this.elements.length - 1] = paramE;
/* 235 */     if (this.head == this.tail) {
/* 236 */       doubleCapacity();
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
/*     */   public void addLast(E paramE) {
/* 248 */     if (paramE == null)
/* 249 */       throw new NullPointerException(); 
/* 250 */     this.elements[this.tail] = paramE;
/* 251 */     if ((this.tail = this.tail + 1 & this.elements.length - 1) == this.head) {
/* 252 */       doubleCapacity();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offerFirst(E paramE) {
/* 263 */     addFirst(paramE);
/* 264 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offerLast(E paramE) {
/* 275 */     addLast(paramE);
/* 276 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E removeFirst() {
/* 283 */     E e = pollFirst();
/* 284 */     if (e == null)
/* 285 */       throw new NoSuchElementException(); 
/* 286 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E removeLast() {
/* 293 */     E e = pollLast();
/* 294 */     if (e == null)
/* 295 */       throw new NoSuchElementException(); 
/* 296 */     return e;
/*     */   }
/*     */   
/*     */   public E pollFirst() {
/* 300 */     int i = this.head;
/*     */     
/* 302 */     Object object = this.elements[i];
/*     */     
/* 304 */     if (object == null)
/* 305 */       return null; 
/* 306 */     this.elements[i] = null;
/* 307 */     this.head = i + 1 & this.elements.length - 1;
/* 308 */     return (E)object;
/*     */   }
/*     */   
/*     */   public E pollLast() {
/* 312 */     int i = this.tail - 1 & this.elements.length - 1;
/*     */     
/* 314 */     Object object = this.elements[i];
/* 315 */     if (object == null)
/* 316 */       return null; 
/* 317 */     this.elements[i] = null;
/* 318 */     this.tail = i;
/* 319 */     return (E)object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E getFirst() {
/* 327 */     Object object = this.elements[this.head];
/* 328 */     if (object == null)
/* 329 */       throw new NoSuchElementException(); 
/* 330 */     return (E)object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E getLast() {
/* 338 */     Object object = this.elements[this.tail - 1 & this.elements.length - 1];
/* 339 */     if (object == null)
/* 340 */       throw new NoSuchElementException(); 
/* 341 */     return (E)object;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E peekFirst() {
/* 347 */     return (E)this.elements[this.head];
/*     */   }
/*     */ 
/*     */   
/*     */   public E peekLast() {
/* 352 */     return (E)this.elements[this.tail - 1 & this.elements.length - 1];
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
/*     */   public boolean removeFirstOccurrence(Object paramObject) {
/* 368 */     if (paramObject == null)
/* 369 */       return false; 
/* 370 */     int i = this.elements.length - 1;
/* 371 */     int j = this.head;
/*     */     Object object;
/* 373 */     while ((object = this.elements[j]) != null) {
/* 374 */       if (paramObject.equals(object)) {
/* 375 */         delete(j);
/* 376 */         return true;
/*     */       } 
/* 378 */       j = j + 1 & i;
/*     */     } 
/* 380 */     return false;
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
/*     */   public boolean removeLastOccurrence(Object paramObject) {
/* 396 */     if (paramObject == null)
/* 397 */       return false; 
/* 398 */     int i = this.elements.length - 1;
/* 399 */     int j = this.tail - 1 & i;
/*     */     Object object;
/* 401 */     while ((object = this.elements[j]) != null) {
/* 402 */       if (paramObject.equals(object)) {
/* 403 */         delete(j);
/* 404 */         return true;
/*     */       } 
/* 406 */       j = j - 1 & i;
/*     */     } 
/* 408 */     return false;
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
/* 423 */     addLast(paramE);
/* 424 */     return true;
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
/* 437 */     return offerLast(paramE);
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
/*     */   public E remove() {
/* 452 */     return removeFirst();
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
/*     */   public E poll() {
/* 466 */     return pollFirst();
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
/*     */   public E element() {
/* 480 */     return getFirst();
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
/*     */   public E peek() {
/* 493 */     return peekFirst();
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
/*     */   public void push(E paramE) {
/* 508 */     addFirst(paramE);
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
/*     */   public E pop() {
/* 522 */     return removeFirst();
/*     */   }
/*     */   
/*     */   private void checkInvariants() {
/* 526 */     assert this.elements[this.tail] == null; assert false;
/* 527 */     throw new AssertionError();
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
/*     */   private boolean delete(int paramInt) {
/* 544 */     checkInvariants();
/* 545 */     Object[] arrayOfObject = this.elements;
/* 546 */     int i = arrayOfObject.length - 1;
/* 547 */     int j = this.head;
/* 548 */     int k = this.tail;
/* 549 */     int m = paramInt - j & i;
/* 550 */     int n = k - paramInt & i;
/*     */ 
/*     */     
/* 553 */     if (m >= (k - j & i)) {
/* 554 */       throw new ConcurrentModificationException();
/*     */     }
/*     */     
/* 557 */     if (m < n) {
/* 558 */       if (j <= paramInt) {
/* 559 */         System.arraycopy(arrayOfObject, j, arrayOfObject, j + 1, m);
/*     */       } else {
/* 561 */         System.arraycopy(arrayOfObject, 0, arrayOfObject, 1, paramInt);
/* 562 */         arrayOfObject[0] = arrayOfObject[i];
/* 563 */         System.arraycopy(arrayOfObject, j, arrayOfObject, j + 1, i - j);
/*     */       } 
/* 565 */       arrayOfObject[j] = null;
/* 566 */       this.head = j + 1 & i;
/* 567 */       return false;
/*     */     } 
/* 569 */     if (paramInt < k) {
/* 570 */       System.arraycopy(arrayOfObject, paramInt + 1, arrayOfObject, paramInt, n);
/* 571 */       this.tail = k - 1;
/*     */     } else {
/* 573 */       System.arraycopy(arrayOfObject, paramInt + 1, arrayOfObject, paramInt, i - paramInt);
/* 574 */       arrayOfObject[i] = arrayOfObject[0];
/* 575 */       System.arraycopy(arrayOfObject, 1, arrayOfObject, 0, k);
/* 576 */       this.tail = k - 1 & i;
/*     */     } 
/* 578 */     return true;
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
/*     */   public int size() {
/* 590 */     return this.tail - this.head & this.elements.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 599 */     return (this.head == this.tail);
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
/*     */   public Iterator<E> iterator() {
/* 611 */     return new DeqIterator();
/*     */   }
/*     */   
/*     */   public Iterator<E> descendingIterator() {
/* 615 */     return new DescendingIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private class DeqIterator
/*     */     implements Iterator<E>
/*     */   {
/* 622 */     private int cursor = ArrayDeque.this.head;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 628 */     private int fence = ArrayDeque.this.tail;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 634 */     private int lastRet = -1;
/*     */     
/*     */     public boolean hasNext() {
/* 637 */       return (this.cursor != this.fence);
/*     */     }
/*     */     
/*     */     public E next() {
/* 641 */       if (this.cursor == this.fence) {
/* 642 */         throw new NoSuchElementException();
/*     */       }
/* 644 */       Object object = ArrayDeque.this.elements[this.cursor];
/*     */ 
/*     */       
/* 647 */       if (ArrayDeque.this.tail != this.fence || object == null)
/* 648 */         throw new ConcurrentModificationException(); 
/* 649 */       this.lastRet = this.cursor;
/* 650 */       this.cursor = this.cursor + 1 & ArrayDeque.this.elements.length - 1;
/* 651 */       return (E)object;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 655 */       if (this.lastRet < 0)
/* 656 */         throw new IllegalStateException(); 
/* 657 */       if (ArrayDeque.this.delete(this.lastRet)) {
/* 658 */         this.cursor = this.cursor - 1 & ArrayDeque.this.elements.length - 1;
/* 659 */         this.fence = ArrayDeque.this.tail;
/*     */       } 
/* 661 */       this.lastRet = -1;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 665 */       Objects.requireNonNull(param1Consumer);
/* 666 */       Object[] arrayOfObject = ArrayDeque.this.elements;
/* 667 */       int i = arrayOfObject.length - 1, j = this.fence, k = this.cursor;
/* 668 */       this.cursor = j;
/* 669 */       while (k != j) {
/* 670 */         Object object = arrayOfObject[k];
/* 671 */         k = k + 1 & i;
/* 672 */         if (object == null)
/* 673 */           throw new ConcurrentModificationException(); 
/* 674 */         param1Consumer.accept((E)object);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private DeqIterator() {}
/*     */   }
/*     */   
/*     */   private class DescendingIterator
/*     */     implements Iterator<E>
/*     */   {
/* 685 */     private int cursor = ArrayDeque.this.tail;
/* 686 */     private int fence = ArrayDeque.this.head;
/* 687 */     private int lastRet = -1;
/*     */     
/*     */     public boolean hasNext() {
/* 690 */       return (this.cursor != this.fence);
/*     */     }
/*     */     
/*     */     public E next() {
/* 694 */       if (this.cursor == this.fence)
/* 695 */         throw new NoSuchElementException(); 
/* 696 */       this.cursor = this.cursor - 1 & ArrayDeque.this.elements.length - 1;
/*     */       
/* 698 */       Object object = ArrayDeque.this.elements[this.cursor];
/* 699 */       if (ArrayDeque.this.head != this.fence || object == null)
/* 700 */         throw new ConcurrentModificationException(); 
/* 701 */       this.lastRet = this.cursor;
/* 702 */       return (E)object;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 706 */       if (this.lastRet < 0)
/* 707 */         throw new IllegalStateException(); 
/* 708 */       if (!ArrayDeque.this.delete(this.lastRet)) {
/* 709 */         this.cursor = this.cursor + 1 & ArrayDeque.this.elements.length - 1;
/* 710 */         this.fence = ArrayDeque.this.head;
/*     */       } 
/* 712 */       this.lastRet = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private DescendingIterator() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object paramObject) {
/* 725 */     if (paramObject == null)
/* 726 */       return false; 
/* 727 */     int i = this.elements.length - 1;
/* 728 */     int j = this.head;
/*     */     Object object;
/* 730 */     while ((object = this.elements[j]) != null) {
/* 731 */       if (paramObject.equals(object))
/* 732 */         return true; 
/* 733 */       j = j + 1 & i;
/*     */     } 
/* 735 */     return false;
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
/*     */   public boolean remove(Object paramObject) {
/* 752 */     return removeFirstOccurrence(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 760 */     int i = this.head;
/* 761 */     int j = this.tail;
/* 762 */     if (i != j) {
/* 763 */       this.head = this.tail = 0;
/* 764 */       int k = i;
/* 765 */       int m = this.elements.length - 1;
/*     */       do {
/* 767 */         this.elements[k] = null;
/* 768 */         k = k + 1 & m;
/* 769 */       } while (k != j);
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
/*     */   public Object[] toArray() {
/* 787 */     return copyElements(new Object[size()]);
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
/* 828 */     int i = size();
/* 829 */     if (paramArrayOfT.length < i)
/* 830 */       paramArrayOfT = (T[])Array.newInstance(paramArrayOfT
/* 831 */           .getClass().getComponentType(), i); 
/* 832 */     copyElements(paramArrayOfT);
/* 833 */     if (paramArrayOfT.length > i)
/* 834 */       paramArrayOfT[i] = null; 
/* 835 */     return paramArrayOfT;
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
/*     */   public ArrayDeque<E> clone() {
/*     */     try {
/* 848 */       ArrayDeque<E> arrayDeque = (ArrayDeque)super.clone();
/* 849 */       arrayDeque.elements = Arrays.copyOf(this.elements, this.elements.length);
/* 850 */       return arrayDeque;
/* 851 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 852 */       throw new AssertionError();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 867 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 870 */     paramObjectOutputStream.writeInt(size());
/*     */ 
/*     */     
/* 873 */     int i = this.elements.length - 1;
/* 874 */     for (int j = this.head; j != this.tail; j = j + 1 & i) {
/* 875 */       paramObjectOutputStream.writeObject(this.elements[j]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 883 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 886 */     int i = paramObjectInputStream.readInt();
/* 887 */     int j = calculateSize(i);
/* 888 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Object[].class, j);
/* 889 */     allocateElements(i);
/* 890 */     this.head = 0;
/* 891 */     this.tail = i;
/*     */ 
/*     */     
/* 894 */     for (byte b = 0; b < i; b++) {
/* 895 */       this.elements[b] = paramObjectInputStream.readObject();
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
/*     */   public Spliterator<E> spliterator() {
/* 912 */     return new DeqSpliterator<>(this, -1, -1);
/*     */   }
/*     */   
/*     */   static final class DeqSpliterator<E>
/*     */     implements Spliterator<E> {
/*     */     private final ArrayDeque<E> deq;
/*     */     private int fence;
/*     */     private int index;
/*     */     
/*     */     DeqSpliterator(ArrayDeque<E> param1ArrayDeque, int param1Int1, int param1Int2) {
/* 922 */       this.deq = param1ArrayDeque;
/* 923 */       this.index = param1Int1;
/* 924 */       this.fence = param1Int2;
/*     */     }
/*     */     
/*     */     private int getFence() {
/*     */       int i;
/* 929 */       if ((i = this.fence) < 0) {
/* 930 */         i = this.fence = this.deq.tail;
/* 931 */         this.index = this.deq.head;
/*     */       } 
/* 933 */       return i;
/*     */     }
/*     */     
/*     */     public DeqSpliterator<E> trySplit() {
/* 937 */       int i = getFence(), j = this.index, k = this.deq.elements.length;
/* 938 */       if (j != i && (j + 1 & k - 1) != i) {
/* 939 */         if (j > i)
/* 940 */           i += k; 
/* 941 */         int m = j + i >>> 1 & k - 1;
/* 942 */         return new DeqSpliterator(this.deq, j, this.index = m);
/*     */       } 
/* 944 */       return null;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 948 */       if (param1Consumer == null)
/* 949 */         throw new NullPointerException(); 
/* 950 */       Object[] arrayOfObject = this.deq.elements;
/* 951 */       int i = arrayOfObject.length - 1, j = getFence(), k = this.index;
/* 952 */       this.index = j;
/* 953 */       while (k != j) {
/* 954 */         Object object = arrayOfObject[k];
/* 955 */         k = k + 1 & i;
/* 956 */         if (object == null)
/* 957 */           throw new ConcurrentModificationException(); 
/* 958 */         param1Consumer.accept((E)object);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 963 */       if (param1Consumer == null)
/* 964 */         throw new NullPointerException(); 
/* 965 */       Object[] arrayOfObject = this.deq.elements;
/* 966 */       int i = arrayOfObject.length - 1, j = getFence(), k = this.index;
/* 967 */       if (k != this.fence) {
/* 968 */         Object object = arrayOfObject[k];
/* 969 */         this.index = k + 1 & i;
/* 970 */         if (object == null)
/* 971 */           throw new ConcurrentModificationException(); 
/* 972 */         param1Consumer.accept((E)object);
/* 973 */         return true;
/*     */       } 
/* 975 */       return false;
/*     */     }
/*     */     
/*     */     public long estimateSize() {
/* 979 */       int i = getFence() - this.index;
/* 980 */       if (i < 0)
/* 981 */         i += this.deq.elements.length; 
/* 982 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 987 */       return 16720;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ArrayDeque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */