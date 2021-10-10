/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractList<E>
/*     */   extends AbstractCollection<E>
/*     */   implements List<E>
/*     */ {
/*     */   public boolean add(E paramE) {
/* 108 */     add(size(), paramE);
/* 109 */     return true;
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
/*     */   public abstract E get(int paramInt);
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
/* 132 */     throw new UnsupportedOperationException();
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
/*     */   public void add(int paramInt, E paramE) {
/* 148 */     throw new UnsupportedOperationException();
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
/* 161 */     throw new UnsupportedOperationException();
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
/*     */   public int indexOf(Object paramObject) {
/* 178 */     ListIterator<E> listIterator = listIterator();
/* 179 */     if (paramObject == null)
/* 180 */     { while (listIterator.hasNext()) {
/* 181 */         if (listIterator.next() == null)
/* 182 */           return listIterator.previousIndex(); 
/*     */       }  }
/* 184 */     else { while (listIterator.hasNext()) {
/* 185 */         if (paramObject.equals(listIterator.next()))
/* 186 */           return listIterator.previousIndex(); 
/*     */       }  }
/* 188 */      return -1;
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
/* 203 */     ListIterator<E> listIterator = listIterator(size());
/* 204 */     if (paramObject == null)
/* 205 */     { while (listIterator.hasPrevious()) {
/* 206 */         if (listIterator.previous() == null)
/* 207 */           return listIterator.nextIndex(); 
/*     */       }  }
/* 209 */     else { while (listIterator.hasPrevious()) {
/* 210 */         if (paramObject.equals(listIterator.previous()))
/* 211 */           return listIterator.nextIndex(); 
/*     */       }  }
/* 213 */      return -1;
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
/*     */   public void clear() {
/* 234 */     removeRange(0, size());
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
/*     */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: iload_1
/*     */     //   2: invokespecial rangeCheckForAdd : (I)V
/*     */     //   5: iconst_0
/*     */     //   6: istore_3
/*     */     //   7: aload_2
/*     */     //   8: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   13: astore #4
/*     */     //   15: aload #4
/*     */     //   17: invokeinterface hasNext : ()Z
/*     */     //   22: ifeq -> 49
/*     */     //   25: aload #4
/*     */     //   27: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   32: astore #5
/*     */     //   34: aload_0
/*     */     //   35: iload_1
/*     */     //   36: iinc #1, 1
/*     */     //   39: aload #5
/*     */     //   41: invokevirtual add : (ILjava/lang/Object;)V
/*     */     //   44: iconst_1
/*     */     //   45: istore_3
/*     */     //   46: goto -> 15
/*     */     //   49: iload_3
/*     */     //   50: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #257	-> 0
/*     */     //   #258	-> 5
/*     */     //   #259	-> 7
/*     */     //   #260	-> 34
/*     */     //   #261	-> 44
/*     */     //   #262	-> 46
/*     */     //   #263	-> 49
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
/*     */   public Iterator<E> iterator() {
/* 288 */     return new Itr();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListIterator<E> listIterator() {
/* 299 */     return listIterator(0);
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
/*     */   public ListIterator<E> listIterator(int paramInt) {
/* 325 */     rangeCheckForAdd(paramInt);
/*     */     
/* 327 */     return new ListItr(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private class Itr
/*     */     implements Iterator<E>
/*     */   {
/* 334 */     int cursor = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 341 */     int lastRet = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     int expectedModCount = AbstractList.this.modCount;
/*     */     
/*     */     public boolean hasNext() {
/* 351 */       return (this.cursor != AbstractList.this.size());
/*     */     }
/*     */     
/*     */     public E next() {
/* 355 */       checkForComodification();
/*     */       try {
/* 357 */         int i = this.cursor;
/* 358 */         E e = (E)AbstractList.this.get(i);
/* 359 */         this.lastRet = i;
/* 360 */         this.cursor = i + 1;
/* 361 */         return e;
/* 362 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 363 */         checkForComodification();
/* 364 */         throw new NoSuchElementException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 369 */       if (this.lastRet < 0)
/* 370 */         throw new IllegalStateException(); 
/* 371 */       checkForComodification();
/*     */       
/*     */       try {
/* 374 */         AbstractList.this.remove(this.lastRet);
/* 375 */         if (this.lastRet < this.cursor)
/* 376 */           this.cursor--; 
/* 377 */         this.lastRet = -1;
/* 378 */         this.expectedModCount = AbstractList.this.modCount;
/* 379 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 380 */         throw new ConcurrentModificationException();
/*     */       } 
/*     */     }
/*     */     
/*     */     final void checkForComodification() {
/* 385 */       if (AbstractList.this.modCount != this.expectedModCount)
/* 386 */         throw new ConcurrentModificationException(); 
/*     */     }
/*     */     
/*     */     private Itr() {} }
/*     */   
/*     */   private class ListItr extends Itr implements ListIterator<E> { ListItr(int param1Int) {
/* 392 */       this.cursor = param1Int;
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 396 */       return (this.cursor != 0);
/*     */     }
/*     */     
/*     */     public E previous() {
/* 400 */       checkForComodification();
/*     */       try {
/* 402 */         int i = this.cursor - 1;
/* 403 */         E e = (E)AbstractList.this.get(i);
/* 404 */         this.lastRet = this.cursor = i;
/* 405 */         return e;
/* 406 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 407 */         checkForComodification();
/* 408 */         throw new NoSuchElementException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 413 */       return this.cursor;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 417 */       return this.cursor - 1;
/*     */     }
/*     */     
/*     */     public void set(E param1E) {
/* 421 */       if (this.lastRet < 0)
/* 422 */         throw new IllegalStateException(); 
/* 423 */       checkForComodification();
/*     */       
/*     */       try {
/* 426 */         AbstractList.this.set(this.lastRet, param1E);
/* 427 */         this.expectedModCount = AbstractList.this.modCount;
/* 428 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 429 */         throw new ConcurrentModificationException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void add(E param1E) {
/* 434 */       checkForComodification();
/*     */       
/*     */       try {
/* 437 */         int i = this.cursor;
/* 438 */         AbstractList.this.add(i, param1E);
/* 439 */         this.lastRet = -1;
/* 440 */         this.cursor = i + 1;
/* 441 */         this.expectedModCount = AbstractList.this.modCount;
/* 442 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 443 */         throw new ConcurrentModificationException();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<E> subList(int paramInt1, int paramInt2) {
/* 484 */     return (this instanceof RandomAccess) ? new RandomAccessSubList<>(this, paramInt1, paramInt2) : new SubList<>(this, paramInt1, paramInt2);
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
/*     */   public boolean equals(Object paramObject) {
/* 513 */     if (paramObject == this)
/* 514 */       return true; 
/* 515 */     if (!(paramObject instanceof List)) {
/* 516 */       return false;
/*     */     }
/* 518 */     ListIterator<E> listIterator = listIterator();
/* 519 */     ListIterator<Object> listIterator1 = ((List)paramObject).listIterator();
/* 520 */     while (listIterator.hasNext() && listIterator1.hasNext()) {
/* 521 */       E e = listIterator.next();
/* 522 */       Object object = listIterator1.next();
/* 523 */       if ((e == null) ? (object == null) : e.equals(object))
/* 524 */         continue;  return false;
/*     */     } 
/* 526 */     return (!listIterator.hasNext() && !listIterator1.hasNext());
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
/*     */   public int hashCode() {
/* 539 */     int i = 1;
/* 540 */     for (E e : this)
/* 541 */       i = 31 * i + ((e == null) ? 0 : e.hashCode()); 
/* 542 */     return i;
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
/*     */   protected void removeRange(int paramInt1, int paramInt2) {
/* 568 */     ListIterator<E> listIterator = listIterator(paramInt1); byte b; int i;
/* 569 */     for (b = 0, i = paramInt2 - paramInt1; b < i; b++) {
/* 570 */       listIterator.next();
/* 571 */       listIterator.remove();
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
/* 601 */   protected transient int modCount = 0;
/*     */   
/*     */   private void rangeCheckForAdd(int paramInt) {
/* 604 */     if (paramInt < 0 || paramInt > size())
/* 605 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt)); 
/*     */   }
/*     */   
/*     */   private String outOfBoundsMsg(int paramInt) {
/* 609 */     return "Index: " + paramInt + ", Size: " + size();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/AbstractList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */