/*     */ package sun.awt.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.AbstractSequentialList;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IdentityLinkedList<E>
/*     */   extends AbstractSequentialList<E>
/*     */   implements List<E>, Deque<E>
/*     */ {
/*  93 */   private transient Entry<E> header = new Entry<>(null, null, null);
/*  94 */   private transient int size = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdentityLinkedList() {
/* 100 */     this.header.next = this.header.previous = this.header;
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
/*     */   public IdentityLinkedList(Collection<? extends E> paramCollection) {
/* 112 */     this();
/* 113 */     addAll(paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E getFirst() {
/* 123 */     if (this.size == 0) {
/* 124 */       throw new NoSuchElementException();
/*     */     }
/* 126 */     return this.header.next.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E getLast() {
/* 136 */     if (this.size == 0) {
/* 137 */       throw new NoSuchElementException();
/*     */     }
/* 139 */     return this.header.previous.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E removeFirst() {
/* 149 */     return remove(this.header.next);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E removeLast() {
/* 159 */     return remove(this.header.previous);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFirst(E paramE) {
/* 168 */     addBefore(paramE, this.header.next);
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
/* 179 */     addBefore(paramE, this.header);
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
/*     */   public boolean contains(Object paramObject) {
/* 192 */     return (indexOf(paramObject) != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 201 */     return this.size;
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
/*     */   public boolean add(E paramE) {
/* 213 */     addBefore(paramE, this.header);
/* 214 */     return true;
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
/*     */   public boolean remove(Object paramObject) {
/* 230 */     for (Entry<E> entry = this.header.next; entry != this.header; entry = entry.next) {
/* 231 */       if (paramObject == entry.element) {
/* 232 */         remove(entry);
/* 233 */         return true;
/*     */       } 
/*     */     } 
/* 236 */     return false;
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
/*     */   public boolean addAll(Collection<? extends E> paramCollection) {
/* 252 */     return addAll(this.size, paramCollection);
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
/*     */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/* 271 */     if (paramInt < 0 || paramInt > this.size) {
/* 272 */       throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
/*     */     }
/* 274 */     Object[] arrayOfObject = paramCollection.toArray();
/* 275 */     int i = arrayOfObject.length;
/* 276 */     if (i == 0)
/* 277 */       return false; 
/* 278 */     this.modCount++;
/*     */     
/* 280 */     Entry<E> entry1 = (paramInt == this.size) ? this.header : entry(paramInt);
/* 281 */     Entry<E> entry2 = entry1.previous;
/* 282 */     for (byte b = 0; b < i; b++) {
/* 283 */       Entry<E> entry = new Entry(arrayOfObject[b], entry1, entry2);
/* 284 */       entry2.next = entry;
/* 285 */       entry2 = entry;
/*     */     } 
/* 287 */     entry1.previous = entry2;
/*     */     
/* 289 */     this.size += i;
/* 290 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 297 */     Entry<E> entry = this.header.next;
/* 298 */     while (entry != this.header) {
/* 299 */       Entry<E> entry1 = entry.next;
/* 300 */       entry.next = entry.previous = null;
/* 301 */       entry.element = null;
/* 302 */       entry = entry1;
/*     */     } 
/* 304 */     this.header.next = this.header.previous = this.header;
/* 305 */     this.size = 0;
/* 306 */     this.modCount++;
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
/*     */   public E get(int paramInt) {
/* 320 */     return (entry(paramInt)).element;
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
/*     */   public E set(int paramInt, E paramE) {
/* 333 */     Entry<E> entry = entry(paramInt);
/* 334 */     E e = entry.element;
/* 335 */     entry.element = paramE;
/* 336 */     return e;
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
/*     */   public void add(int paramInt, E paramE) {
/* 349 */     addBefore(paramE, (paramInt == this.size) ? this.header : entry(paramInt));
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
/*     */   public E remove(int paramInt) {
/* 362 */     return remove(entry(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Entry<E> entry(int paramInt) {
/* 369 */     if (paramInt < 0 || paramInt >= this.size) {
/* 370 */       throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
/*     */     }
/* 372 */     Entry<E> entry = this.header;
/* 373 */     if (paramInt < this.size >> 1) {
/* 374 */       for (byte b = 0; b <= paramInt; b++)
/* 375 */         entry = entry.next; 
/*     */     } else {
/* 377 */       for (int i = this.size; i > paramInt; i--)
/* 378 */         entry = entry.previous; 
/*     */     } 
/* 380 */     return entry;
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
/*     */   public int indexOf(Object paramObject) {
/* 398 */     byte b = 0;
/* 399 */     for (Entry<E> entry = this.header.next; entry != this.header; entry = entry.next) {
/* 400 */       if (paramObject == entry.element) {
/* 401 */         return b;
/*     */       }
/* 403 */       b++;
/*     */     } 
/* 405 */     return -1;
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
/*     */   public int lastIndexOf(Object paramObject) {
/* 420 */     int i = this.size;
/* 421 */     for (Entry<E> entry = this.header.previous; entry != this.header; entry = entry.previous) {
/* 422 */       i--;
/* 423 */       if (paramObject == entry.element) {
/* 424 */         return i;
/*     */       }
/*     */     } 
/* 427 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peek() {
/* 438 */     if (this.size == 0)
/* 439 */       return null; 
/* 440 */     return getFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E element() {
/* 450 */     return getFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E poll() {
/* 459 */     if (this.size == 0)
/* 460 */       return null; 
/* 461 */     return removeFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E remove() {
/* 472 */     return removeFirst();
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
/* 483 */     return add(paramE);
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
/*     */   public boolean offerFirst(E paramE) {
/* 495 */     addFirst(paramE);
/* 496 */     return true;
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
/* 507 */     addLast(paramE);
/* 508 */     return true;
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
/*     */   public E peekFirst() {
/* 520 */     if (this.size == 0)
/* 521 */       return null; 
/* 522 */     return getFirst();
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
/*     */   public E peekLast() {
/* 534 */     if (this.size == 0)
/* 535 */       return null; 
/* 536 */     return getLast();
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
/*     */   public E pollFirst() {
/* 548 */     if (this.size == 0)
/* 549 */       return null; 
/* 550 */     return removeFirst();
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
/*     */   public E pollLast() {
/* 562 */     if (this.size == 0)
/* 563 */       return null; 
/* 564 */     return removeLast();
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
/*     */   public void push(E paramE) {
/* 577 */     addFirst(paramE);
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
/*     */   public E pop() {
/* 592 */     return removeFirst();
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
/*     */   public boolean removeFirstOccurrence(Object paramObject) {
/* 605 */     return remove(paramObject);
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
/*     */   public boolean removeLastOccurrence(Object paramObject) {
/* 618 */     for (Entry<E> entry = this.header.previous; entry != this.header; entry = entry.previous) {
/* 619 */       if (paramObject == entry.element) {
/* 620 */         remove(entry);
/* 621 */         return true;
/*     */       } 
/*     */     } 
/* 624 */     return false;
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
/*     */   public ListIterator<E> listIterator(int paramInt) {
/* 649 */     return new ListItr(paramInt);
/*     */   }
/*     */   
/*     */   private class ListItr implements ListIterator<E> {
/* 653 */     private IdentityLinkedList.Entry<E> lastReturned = IdentityLinkedList.this.header;
/*     */     private IdentityLinkedList.Entry<E> next;
/*     */     private int nextIndex;
/* 656 */     private int expectedModCount = IdentityLinkedList.this.modCount;
/*     */     
/*     */     ListItr(int param1Int) {
/* 659 */       if (param1Int < 0 || param1Int > IdentityLinkedList.this.size)
/* 660 */         throw new IndexOutOfBoundsException("Index: " + param1Int + ", Size: " + IdentityLinkedList.this
/* 661 */             .size); 
/* 662 */       if (param1Int < IdentityLinkedList.this.size >> 1) {
/* 663 */         this.next = IdentityLinkedList.this.header.next;
/* 664 */         for (this.nextIndex = 0; this.nextIndex < param1Int; this.nextIndex++)
/* 665 */           this.next = this.next.next; 
/*     */       } else {
/* 667 */         this.next = IdentityLinkedList.this.header;
/* 668 */         for (this.nextIndex = IdentityLinkedList.this.size; this.nextIndex > param1Int; this.nextIndex--)
/* 669 */           this.next = this.next.previous; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 674 */       return (this.nextIndex != IdentityLinkedList.this.size);
/*     */     }
/*     */     
/*     */     public E next() {
/* 678 */       checkForComodification();
/* 679 */       if (this.nextIndex == IdentityLinkedList.this.size) {
/* 680 */         throw new NoSuchElementException();
/*     */       }
/* 682 */       this.lastReturned = this.next;
/* 683 */       this.next = this.next.next;
/* 684 */       this.nextIndex++;
/* 685 */       return this.lastReturned.element;
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 689 */       return (this.nextIndex != 0);
/*     */     }
/*     */     
/*     */     public E previous() {
/* 693 */       if (this.nextIndex == 0) {
/* 694 */         throw new NoSuchElementException();
/*     */       }
/* 696 */       this.lastReturned = this.next = this.next.previous;
/* 697 */       this.nextIndex--;
/* 698 */       checkForComodification();
/* 699 */       return this.lastReturned.element;
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 703 */       return this.nextIndex;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 707 */       return this.nextIndex - 1;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 711 */       checkForComodification();
/* 712 */       IdentityLinkedList.Entry<E> entry = this.lastReturned.next;
/*     */       try {
/* 714 */         IdentityLinkedList.this.remove(this.lastReturned);
/* 715 */       } catch (NoSuchElementException noSuchElementException) {
/* 716 */         throw new IllegalStateException();
/*     */       } 
/* 718 */       if (this.next == this.lastReturned) {
/* 719 */         this.next = entry;
/*     */       } else {
/* 721 */         this.nextIndex--;
/* 722 */       }  this.lastReturned = IdentityLinkedList.this.header;
/* 723 */       this.expectedModCount++;
/*     */     }
/*     */     
/*     */     public void set(E param1E) {
/* 727 */       if (this.lastReturned == IdentityLinkedList.this.header)
/* 728 */         throw new IllegalStateException(); 
/* 729 */       checkForComodification();
/* 730 */       this.lastReturned.element = param1E;
/*     */     }
/*     */     
/*     */     public void add(E param1E) {
/* 734 */       checkForComodification();
/* 735 */       this.lastReturned = IdentityLinkedList.this.header;
/* 736 */       IdentityLinkedList.this.addBefore(param1E, this.next);
/* 737 */       this.nextIndex++;
/* 738 */       this.expectedModCount++;
/*     */     }
/*     */     
/*     */     final void checkForComodification() {
/* 742 */       if (IdentityLinkedList.this.modCount != this.expectedModCount)
/* 743 */         throw new ConcurrentModificationException(); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Entry<E> {
/*     */     E element;
/*     */     Entry<E> next;
/*     */     Entry<E> previous;
/*     */     
/*     */     Entry(E param1E, Entry<E> param1Entry1, Entry<E> param1Entry2) {
/* 753 */       this.element = param1E;
/* 754 */       this.next = param1Entry1;
/* 755 */       this.previous = param1Entry2;
/*     */     }
/*     */   }
/*     */   
/*     */   private Entry<E> addBefore(E paramE, Entry<E> paramEntry) {
/* 760 */     Entry<E> entry = new Entry<>(paramE, paramEntry, paramEntry.previous);
/* 761 */     entry.previous.next = entry;
/* 762 */     entry.next.previous = entry;
/* 763 */     this.size++;
/* 764 */     this.modCount++;
/* 765 */     return entry;
/*     */   }
/*     */   
/*     */   private E remove(Entry<E> paramEntry) {
/* 769 */     if (paramEntry == this.header) {
/* 770 */       throw new NoSuchElementException();
/*     */     }
/* 772 */     E e = paramEntry.element;
/* 773 */     paramEntry.previous.next = paramEntry.next;
/* 774 */     paramEntry.next.previous = paramEntry.previous;
/* 775 */     paramEntry.next = paramEntry.previous = null;
/* 776 */     paramEntry.element = null;
/* 777 */     this.size--;
/* 778 */     this.modCount++;
/* 779 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> descendingIterator() {
/* 786 */     return new DescendingIterator();
/*     */   }
/*     */   
/*     */   private class DescendingIterator
/*     */     implements Iterator {
/* 791 */     final IdentityLinkedList<E>.ListItr itr = new IdentityLinkedList.ListItr(IdentityLinkedList.this.size());
/*     */     public boolean hasNext() {
/* 793 */       return this.itr.hasPrevious();
/*     */     }
/*     */     public E next() {
/* 796 */       return this.itr.previous();
/*     */     }
/*     */     public void remove() {
/* 799 */       this.itr.remove();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 818 */     Object[] arrayOfObject = new Object[this.size];
/* 819 */     byte b = 0;
/* 820 */     for (Entry<E> entry = this.header.next; entry != this.header; entry = entry.next)
/* 821 */       arrayOfObject[b++] = entry.element; 
/* 822 */     return arrayOfObject;
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
/*     */   
/*     */   public <T> T[] toArray(T[] paramArrayOfT) {
/* 864 */     if (paramArrayOfT.length < this.size)
/* 865 */       paramArrayOfT = (T[])Array.newInstance(paramArrayOfT
/* 866 */           .getClass().getComponentType(), this.size); 
/* 867 */     byte b = 0;
/* 868 */     T[] arrayOfT = paramArrayOfT;
/* 869 */     for (Entry<E> entry = this.header.next; entry != this.header; entry = entry.next) {
/* 870 */       arrayOfT[b++] = (T)entry.element;
/*     */     }
/* 872 */     if (paramArrayOfT.length > this.size) {
/* 873 */       paramArrayOfT[this.size] = null;
/*     */     }
/* 875 */     return paramArrayOfT;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/util/IdentityLinkedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */