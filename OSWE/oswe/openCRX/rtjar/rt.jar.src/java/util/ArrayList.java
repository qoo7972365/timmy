/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ import sun.misc.SharedSecrets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayList<E>
/*      */   extends AbstractList<E>
/*      */   implements List<E>, RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 8683452581122892189L;
/*      */   private static final int DEFAULT_CAPACITY = 10;
/*  120 */   private static final Object[] EMPTY_ELEMENTDATA = new Object[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  127 */   private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient Object[] elementData;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int size;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList(int paramInt) {
/*  152 */     if (paramInt > 0) {
/*  153 */       this.elementData = new Object[paramInt];
/*  154 */     } else if (paramInt == 0) {
/*  155 */       this.elementData = EMPTY_ELEMENTDATA;
/*      */     } else {
/*  157 */       throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList() {
/*  166 */     this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
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
/*      */   public ArrayList(Collection<? extends E> paramCollection) {
/*  178 */     this.elementData = paramCollection.toArray();
/*  179 */     if ((this.size = this.elementData.length) != 0) {
/*      */       
/*  181 */       if (this.elementData.getClass() != Object[].class) {
/*  182 */         this.elementData = Arrays.copyOf(this.elementData, this.size, Object[].class);
/*      */       }
/*      */     } else {
/*  185 */       this.elementData = EMPTY_ELEMENTDATA;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trimToSize() {
/*  195 */     this.modCount++;
/*  196 */     if (this.size < this.elementData.length) {
/*  197 */       this
/*      */         
/*  199 */         .elementData = (this.size == 0) ? EMPTY_ELEMENTDATA : Arrays.<Object>copyOf(this.elementData, this.size);
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
/*      */   public void ensureCapacity(int paramInt) {
/*  211 */     byte b = (this.elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : 10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  218 */     if (paramInt > b) {
/*  219 */       ensureExplicitCapacity(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */   private static int calculateCapacity(Object[] paramArrayOfObject, int paramInt) {
/*  224 */     if (paramArrayOfObject == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
/*  225 */       return Math.max(10, paramInt);
/*      */     }
/*  227 */     return paramInt;
/*      */   }
/*      */   
/*      */   private void ensureCapacityInternal(int paramInt) {
/*  231 */     ensureExplicitCapacity(calculateCapacity(this.elementData, paramInt));
/*      */   }
/*      */   
/*      */   private void ensureExplicitCapacity(int paramInt) {
/*  235 */     this.modCount++;
/*      */ 
/*      */     
/*  238 */     if (paramInt - this.elementData.length > 0) {
/*  239 */       grow(paramInt);
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
/*      */   private void grow(int paramInt) {
/*  258 */     int i = this.elementData.length;
/*  259 */     int j = i + (i >> 1);
/*  260 */     if (j - paramInt < 0)
/*  261 */       j = paramInt; 
/*  262 */     if (j - 2147483639 > 0) {
/*  263 */       j = hugeCapacity(paramInt);
/*      */     }
/*  265 */     this.elementData = Arrays.copyOf(this.elementData, j);
/*      */   }
/*      */   
/*      */   private static int hugeCapacity(int paramInt) {
/*  269 */     if (paramInt < 0)
/*  270 */       throw new OutOfMemoryError(); 
/*  271 */     return (paramInt > 2147483639) ? Integer.MAX_VALUE : 2147483639;
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
/*  282 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  291 */     return (this.size == 0);
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
/*      */   public boolean contains(Object paramObject) {
/*  304 */     return (indexOf(paramObject) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(Object paramObject) {
/*  315 */     if (paramObject == null)
/*  316 */     { for (byte b = 0; b < this.size; b++) {
/*  317 */         if (this.elementData[b] == null)
/*  318 */           return b; 
/*      */       }  }
/*  320 */     else { for (byte b = 0; b < this.size; b++) {
/*  321 */         if (paramObject.equals(this.elementData[b]))
/*  322 */           return b; 
/*      */       }  }
/*  324 */      return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(Object paramObject) {
/*  335 */     if (paramObject == null)
/*  336 */     { for (int i = this.size - 1; i >= 0; i--) {
/*  337 */         if (this.elementData[i] == null)
/*  338 */           return i; 
/*      */       }  }
/*  340 */     else { for (int i = this.size - 1; i >= 0; i--) {
/*  341 */         if (paramObject.equals(this.elementData[i]))
/*  342 */           return i; 
/*      */       }  }
/*  344 */      return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/*  355 */       ArrayList arrayList = (ArrayList)super.clone();
/*  356 */       arrayList.elementData = Arrays.copyOf(this.elementData, this.size);
/*  357 */       arrayList.modCount = 0;
/*  358 */       return arrayList;
/*  359 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/*  361 */       throw new InternalError(cloneNotSupportedException);
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
/*      */   public Object[] toArray() {
/*  380 */     return Arrays.copyOf(this.elementData, this.size);
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT) {
/*  409 */     if (paramArrayOfT.length < this.size)
/*      */     {
/*  411 */       return (T[])Arrays.<Object, Object>copyOf(this.elementData, this.size, (Class)paramArrayOfT.getClass()); } 
/*  412 */     System.arraycopy(this.elementData, 0, paramArrayOfT, 0, this.size);
/*  413 */     if (paramArrayOfT.length > this.size)
/*  414 */       paramArrayOfT[this.size] = null; 
/*  415 */     return paramArrayOfT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   E elementData(int paramInt) {
/*  422 */     return (E)this.elementData[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E get(int paramInt) {
/*  433 */     rangeCheck(paramInt);
/*      */     
/*  435 */     return elementData(paramInt);
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
/*      */   public E set(int paramInt, E paramE) {
/*  448 */     rangeCheck(paramInt);
/*      */     
/*  450 */     E e = elementData(paramInt);
/*  451 */     this.elementData[paramInt] = paramE;
/*  452 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean add(E paramE) {
/*  462 */     ensureCapacityInternal(this.size + 1);
/*  463 */     this.elementData[this.size++] = paramE;
/*  464 */     return true;
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
/*      */   public void add(int paramInt, E paramE) {
/*  477 */     rangeCheckForAdd(paramInt);
/*      */     
/*  479 */     ensureCapacityInternal(this.size + 1);
/*  480 */     System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.size - paramInt);
/*      */     
/*  482 */     this.elementData[paramInt] = paramE;
/*  483 */     this.size++;
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
/*      */   public E remove(int paramInt) {
/*  496 */     rangeCheck(paramInt);
/*      */     
/*  498 */     this.modCount++;
/*  499 */     E e = elementData(paramInt);
/*      */     
/*  501 */     int i = this.size - paramInt - 1;
/*  502 */     if (i > 0) {
/*  503 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  505 */     this.elementData[--this.size] = null;
/*      */     
/*  507 */     return e;
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
/*      */   public boolean remove(Object paramObject) {
/*  524 */     if (paramObject == null)
/*  525 */     { for (byte b = 0; b < this.size; b++) {
/*  526 */         if (this.elementData[b] == null) {
/*  527 */           fastRemove(b);
/*  528 */           return true;
/*      */         } 
/*      */       }  }
/*  531 */     else { for (byte b = 0; b < this.size; b++) {
/*  532 */         if (paramObject.equals(this.elementData[b])) {
/*  533 */           fastRemove(b);
/*  534 */           return true;
/*      */         } 
/*      */       }  }
/*  537 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fastRemove(int paramInt) {
/*  545 */     this.modCount++;
/*  546 */     int i = this.size - paramInt - 1;
/*  547 */     if (i > 0) {
/*  548 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  550 */     this.elementData[--this.size] = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  558 */     this.modCount++;
/*      */ 
/*      */     
/*  561 */     for (byte b = 0; b < this.size; b++) {
/*  562 */       this.elementData[b] = null;
/*      */     }
/*  564 */     this.size = 0;
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
/*      */   public boolean addAll(Collection<? extends E> paramCollection) {
/*  581 */     Object[] arrayOfObject = paramCollection.toArray();
/*  582 */     int i = arrayOfObject.length;
/*  583 */     ensureCapacityInternal(this.size + i);
/*  584 */     System.arraycopy(arrayOfObject, 0, this.elementData, this.size, i);
/*  585 */     this.size += i;
/*  586 */     return (i != 0);
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
/*      */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/*  605 */     rangeCheckForAdd(paramInt);
/*      */     
/*  607 */     Object[] arrayOfObject = paramCollection.toArray();
/*  608 */     int i = arrayOfObject.length;
/*  609 */     ensureCapacityInternal(this.size + i);
/*      */     
/*  611 */     int j = this.size - paramInt;
/*  612 */     if (j > 0) {
/*  613 */       System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + i, j);
/*      */     }
/*      */     
/*  616 */     System.arraycopy(arrayOfObject, 0, this.elementData, paramInt, i);
/*  617 */     this.size += i;
/*  618 */     return (i != 0);
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
/*      */   protected void removeRange(int paramInt1, int paramInt2) {
/*  636 */     this.modCount++;
/*  637 */     int i = this.size - paramInt2;
/*  638 */     System.arraycopy(this.elementData, paramInt2, this.elementData, paramInt1, i);
/*      */ 
/*      */ 
/*      */     
/*  642 */     int j = this.size - paramInt2 - paramInt1;
/*  643 */     for (int k = j; k < this.size; k++) {
/*  644 */       this.elementData[k] = null;
/*      */     }
/*  646 */     this.size = j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rangeCheck(int paramInt) {
/*  656 */     if (paramInt >= this.size) {
/*  657 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void rangeCheckForAdd(int paramInt) {
/*  664 */     if (paramInt > this.size || paramInt < 0) {
/*  665 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String outOfBoundsMsg(int paramInt) {
/*  674 */     return "Index: " + paramInt + ", Size: " + this.size;
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
/*      */   public boolean removeAll(Collection<?> paramCollection) {
/*  693 */     Objects.requireNonNull(paramCollection);
/*  694 */     return batchRemove(paramCollection, false);
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
/*      */   public boolean retainAll(Collection<?> paramCollection) {
/*  714 */     Objects.requireNonNull(paramCollection);
/*  715 */     return batchRemove(paramCollection, true);
/*      */   }
/*      */   
/*      */   private boolean batchRemove(Collection<?> paramCollection, boolean paramBoolean) {
/*  719 */     Object[] arrayOfObject = this.elementData;
/*  720 */     byte b = 0; int i = 0;
/*  721 */     boolean bool = false;
/*      */     try {
/*  723 */       for (; b < this.size; b++) {
/*  724 */         if (paramCollection.contains(arrayOfObject[b]) == paramBoolean) {
/*  725 */           arrayOfObject[i++] = arrayOfObject[b];
/*      */         }
/*      */       } 
/*      */     } finally {
/*  729 */       if (b != this.size) {
/*  730 */         System.arraycopy(arrayOfObject, b, arrayOfObject, i, this.size - b);
/*      */ 
/*      */         
/*  733 */         i += this.size - b;
/*      */       } 
/*  735 */       if (i != this.size) {
/*      */         
/*  737 */         for (int j = i; j < this.size; j++)
/*  738 */           arrayOfObject[j] = null; 
/*  739 */         this.modCount += this.size - i;
/*  740 */         this.size = i;
/*  741 */         bool = true;
/*      */       } 
/*      */     } 
/*  744 */     return bool;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  758 */     int i = this.modCount;
/*  759 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */     
/*  762 */     paramObjectOutputStream.writeInt(this.size);
/*      */ 
/*      */     
/*  765 */     for (byte b = 0; b < this.size; b++) {
/*  766 */       paramObjectOutputStream.writeObject(this.elementData[b]);
/*      */     }
/*      */     
/*  769 */     if (this.modCount != i) {
/*  770 */       throw new ConcurrentModificationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  780 */     this.elementData = EMPTY_ELEMENTDATA;
/*      */ 
/*      */     
/*  783 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/*  786 */     paramObjectInputStream.readInt();
/*      */     
/*  788 */     if (this.size > 0) {
/*      */       
/*  790 */       int i = calculateCapacity(this.elementData, this.size);
/*  791 */       SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Object[].class, i);
/*  792 */       ensureCapacityInternal(this.size);
/*      */       
/*  794 */       Object[] arrayOfObject = this.elementData;
/*      */       
/*  796 */       for (byte b = 0; b < this.size; b++) {
/*  797 */         arrayOfObject[b] = paramObjectInputStream.readObject();
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
/*      */ 
/*      */   
/*      */   public ListIterator<E> listIterator(int paramInt) {
/*  815 */     if (paramInt < 0 || paramInt > this.size)
/*  816 */       throw new IndexOutOfBoundsException("Index: " + paramInt); 
/*  817 */     return new ListItr(paramInt);
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
/*      */   public ListIterator<E> listIterator() {
/*  829 */     return new ListItr(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<E> iterator() {
/*  840 */     return new Itr();
/*      */   }
/*      */ 
/*      */   
/*      */   private class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     int cursor;
/*  848 */     int lastRet = -1;
/*  849 */     int expectedModCount = ArrayList.this.modCount;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  854 */       return (this.cursor != ArrayList.this.size);
/*      */     }
/*      */ 
/*      */     
/*      */     public E next() {
/*  859 */       checkForComodification();
/*  860 */       int i = this.cursor;
/*  861 */       if (i >= ArrayList.this.size)
/*  862 */         throw new NoSuchElementException(); 
/*  863 */       Object[] arrayOfObject = ArrayList.this.elementData;
/*  864 */       if (i >= arrayOfObject.length)
/*  865 */         throw new ConcurrentModificationException(); 
/*  866 */       this.cursor = i + 1;
/*  867 */       return (E)arrayOfObject[this.lastRet = i];
/*      */     }
/*      */     
/*      */     public void remove() {
/*  871 */       if (this.lastRet < 0)
/*  872 */         throw new IllegalStateException(); 
/*  873 */       checkForComodification();
/*      */       
/*      */       try {
/*  876 */         ArrayList.this.remove(this.lastRet);
/*  877 */         this.cursor = this.lastRet;
/*  878 */         this.lastRet = -1;
/*  879 */         this.expectedModCount = ArrayList.this.modCount;
/*  880 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*  881 */         throw new ConcurrentModificationException();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/*  888 */       Objects.requireNonNull(param1Consumer);
/*  889 */       int i = ArrayList.this.size;
/*  890 */       int j = this.cursor;
/*  891 */       if (j >= i) {
/*      */         return;
/*      */       }
/*  894 */       Object[] arrayOfObject = ArrayList.this.elementData;
/*  895 */       if (j >= arrayOfObject.length) {
/*  896 */         throw new ConcurrentModificationException();
/*      */       }
/*  898 */       while (j != i && ArrayList.this.modCount == this.expectedModCount) {
/*  899 */         param1Consumer.accept((E)arrayOfObject[j++]);
/*      */       }
/*      */       
/*  902 */       this.cursor = j;
/*  903 */       this.lastRet = j - 1;
/*  904 */       checkForComodification();
/*      */     }
/*      */     
/*      */     final void checkForComodification() {
/*  908 */       if (ArrayList.this.modCount != this.expectedModCount) {
/*  909 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ListItr
/*      */     extends Itr
/*      */     implements ListIterator<E>
/*      */   {
/*      */     ListItr(int param1Int) {
/*  919 */       this.cursor = param1Int;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  923 */       return (this.cursor != 0);
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  927 */       return this.cursor;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  931 */       return this.cursor - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public E previous() {
/*  936 */       checkForComodification();
/*  937 */       int i = this.cursor - 1;
/*  938 */       if (i < 0)
/*  939 */         throw new NoSuchElementException(); 
/*  940 */       Object[] arrayOfObject = ArrayList.this.elementData;
/*  941 */       if (i >= arrayOfObject.length)
/*  942 */         throw new ConcurrentModificationException(); 
/*  943 */       this.cursor = i;
/*  944 */       return (E)arrayOfObject[this.lastRet = i];
/*      */     }
/*      */     
/*      */     public void set(E param1E) {
/*  948 */       if (this.lastRet < 0)
/*  949 */         throw new IllegalStateException(); 
/*  950 */       checkForComodification();
/*      */       
/*      */       try {
/*  953 */         ArrayList.this.set(this.lastRet, param1E);
/*  954 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*  955 */         throw new ConcurrentModificationException();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(E param1E) {
/*  960 */       checkForComodification();
/*      */       
/*      */       try {
/*  963 */         int i = this.cursor;
/*  964 */         ArrayList.this.add(i, param1E);
/*  965 */         this.cursor = i + 1;
/*  966 */         this.lastRet = -1;
/*  967 */         this.expectedModCount = ArrayList.this.modCount;
/*  968 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*  969 */         throw new ConcurrentModificationException();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<E> subList(int paramInt1, int paramInt2) {
/* 1004 */     subListRangeCheck(paramInt1, paramInt2, this.size);
/* 1005 */     return new SubList(this, 0, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   static void subListRangeCheck(int paramInt1, int paramInt2, int paramInt3) {
/* 1009 */     if (paramInt1 < 0)
/* 1010 */       throw new IndexOutOfBoundsException("fromIndex = " + paramInt1); 
/* 1011 */     if (paramInt2 > paramInt3)
/* 1012 */       throw new IndexOutOfBoundsException("toIndex = " + paramInt2); 
/* 1013 */     if (paramInt1 > paramInt2)
/* 1014 */       throw new IllegalArgumentException("fromIndex(" + paramInt1 + ") > toIndex(" + paramInt2 + ")"); 
/*      */   }
/*      */   
/*      */   private class SubList
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess {
/*      */     private final AbstractList<E> parent;
/*      */     private final int parentOffset;
/*      */     private final int offset;
/*      */     int size;
/*      */     
/*      */     SubList(AbstractList<E> param1AbstractList, int param1Int1, int param1Int2, int param1Int3) {
/* 1026 */       this.parent = param1AbstractList;
/* 1027 */       this.parentOffset = param1Int2;
/* 1028 */       this.offset = param1Int1 + param1Int2;
/* 1029 */       this.size = param1Int3 - param1Int2;
/* 1030 */       this.modCount = ArrayList.this.modCount;
/*      */     }
/*      */     
/*      */     public E set(int param1Int, E param1E) {
/* 1034 */       rangeCheck(param1Int);
/* 1035 */       checkForComodification();
/* 1036 */       E e = (E)ArrayList.this.elementData(this.offset + param1Int);
/* 1037 */       ArrayList.this.elementData[this.offset + param1Int] = param1E;
/* 1038 */       return e;
/*      */     }
/*      */     
/*      */     public E get(int param1Int) {
/* 1042 */       rangeCheck(param1Int);
/* 1043 */       checkForComodification();
/* 1044 */       return ArrayList.this.elementData(this.offset + param1Int);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1048 */       checkForComodification();
/* 1049 */       return this.size;
/*      */     }
/*      */     
/*      */     public void add(int param1Int, E param1E) {
/* 1053 */       rangeCheckForAdd(param1Int);
/* 1054 */       checkForComodification();
/* 1055 */       this.parent.add(this.parentOffset + param1Int, param1E);
/* 1056 */       this.modCount = this.parent.modCount;
/* 1057 */       this.size++;
/*      */     }
/*      */     
/*      */     public E remove(int param1Int) {
/* 1061 */       rangeCheck(param1Int);
/* 1062 */       checkForComodification();
/* 1063 */       E e = this.parent.remove(this.parentOffset + param1Int);
/* 1064 */       this.modCount = this.parent.modCount;
/* 1065 */       this.size--;
/* 1066 */       return e;
/*      */     }
/*      */     
/*      */     protected void removeRange(int param1Int1, int param1Int2) {
/* 1070 */       checkForComodification();
/* 1071 */       this.parent.removeRange(this.parentOffset + param1Int1, this.parentOffset + param1Int2);
/*      */       
/* 1073 */       this.modCount = this.parent.modCount;
/* 1074 */       this.size -= param1Int2 - param1Int1;
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends E> param1Collection) {
/* 1078 */       return addAll(this.size, param1Collection);
/*      */     }
/*      */     
/*      */     public boolean addAll(int param1Int, Collection<? extends E> param1Collection) {
/* 1082 */       rangeCheckForAdd(param1Int);
/* 1083 */       int i = param1Collection.size();
/* 1084 */       if (i == 0) {
/* 1085 */         return false;
/*      */       }
/* 1087 */       checkForComodification();
/* 1088 */       this.parent.addAll(this.parentOffset + param1Int, param1Collection);
/* 1089 */       this.modCount = this.parent.modCount;
/* 1090 */       this.size += i;
/* 1091 */       return true;
/*      */     }
/*      */     
/*      */     public Iterator<E> iterator() {
/* 1095 */       return listIterator();
/*      */     }
/*      */     
/*      */     public ListIterator<E> listIterator(final int index) {
/* 1099 */       checkForComodification();
/* 1100 */       rangeCheckForAdd(index);
/* 1101 */       final int offset = this.offset;
/*      */       
/* 1103 */       return new ListIterator<E>() {
/* 1104 */           int cursor = index;
/* 1105 */           int lastRet = -1;
/* 1106 */           int expectedModCount = ArrayList.this.modCount;
/*      */           
/*      */           public boolean hasNext() {
/* 1109 */             return (this.cursor != ArrayList.SubList.this.size);
/*      */           }
/*      */ 
/*      */           
/*      */           public E next() {
/* 1114 */             checkForComodification();
/* 1115 */             int i = this.cursor;
/* 1116 */             if (i >= ArrayList.SubList.this.size)
/* 1117 */               throw new NoSuchElementException(); 
/* 1118 */             Object[] arrayOfObject = ArrayList.this.elementData;
/* 1119 */             if (offset + i >= arrayOfObject.length)
/* 1120 */               throw new ConcurrentModificationException(); 
/* 1121 */             this.cursor = i + 1;
/* 1122 */             return (E)arrayOfObject[offset + (this.lastRet = i)];
/*      */           }
/*      */           
/*      */           public boolean hasPrevious() {
/* 1126 */             return (this.cursor != 0);
/*      */           }
/*      */ 
/*      */           
/*      */           public E previous() {
/* 1131 */             checkForComodification();
/* 1132 */             int i = this.cursor - 1;
/* 1133 */             if (i < 0)
/* 1134 */               throw new NoSuchElementException(); 
/* 1135 */             Object[] arrayOfObject = ArrayList.this.elementData;
/* 1136 */             if (offset + i >= arrayOfObject.length)
/* 1137 */               throw new ConcurrentModificationException(); 
/* 1138 */             this.cursor = i;
/* 1139 */             return (E)arrayOfObject[offset + (this.lastRet = i)];
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEachRemaining(Consumer<? super E> param2Consumer) {
/* 1144 */             Objects.requireNonNull(param2Consumer);
/* 1145 */             int i = ArrayList.SubList.this.size;
/* 1146 */             int j = this.cursor;
/* 1147 */             if (j >= i) {
/*      */               return;
/*      */             }
/* 1150 */             Object[] arrayOfObject = ArrayList.this.elementData;
/* 1151 */             if (offset + j >= arrayOfObject.length) {
/* 1152 */               throw new ConcurrentModificationException();
/*      */             }
/* 1154 */             while (j != i && ArrayList.SubList.this.modCount == this.expectedModCount) {
/* 1155 */               param2Consumer.accept((E)arrayOfObject[offset + j++]);
/*      */             }
/*      */             
/* 1158 */             this.lastRet = this.cursor = j;
/* 1159 */             checkForComodification();
/*      */           }
/*      */           
/*      */           public int nextIndex() {
/* 1163 */             return this.cursor;
/*      */           }
/*      */           
/*      */           public int previousIndex() {
/* 1167 */             return this.cursor - 1;
/*      */           }
/*      */           
/*      */           public void remove() {
/* 1171 */             if (this.lastRet < 0)
/* 1172 */               throw new IllegalStateException(); 
/* 1173 */             checkForComodification();
/*      */             
/*      */             try {
/* 1176 */               ArrayList.SubList.this.remove(this.lastRet);
/* 1177 */               this.cursor = this.lastRet;
/* 1178 */               this.lastRet = -1;
/* 1179 */               this.expectedModCount = ArrayList.this.modCount;
/* 1180 */             } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 1181 */               throw new ConcurrentModificationException();
/*      */             } 
/*      */           }
/*      */           
/*      */           public void set(E param2E) {
/* 1186 */             if (this.lastRet < 0)
/* 1187 */               throw new IllegalStateException(); 
/* 1188 */             checkForComodification();
/*      */             
/*      */             try {
/* 1191 */               ArrayList.this.set(offset + this.lastRet, param2E);
/* 1192 */             } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 1193 */               throw new ConcurrentModificationException();
/*      */             } 
/*      */           }
/*      */           
/*      */           public void add(E param2E) {
/* 1198 */             checkForComodification();
/*      */             
/*      */             try {
/* 1201 */               int i = this.cursor;
/* 1202 */               ArrayList.SubList.this.add(i, param2E);
/* 1203 */               this.cursor = i + 1;
/* 1204 */               this.lastRet = -1;
/* 1205 */               this.expectedModCount = ArrayList.this.modCount;
/* 1206 */             } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 1207 */               throw new ConcurrentModificationException();
/*      */             } 
/*      */           }
/*      */           
/*      */           final void checkForComodification() {
/* 1212 */             if (this.expectedModCount != ArrayList.this.modCount)
/* 1213 */               throw new ConcurrentModificationException(); 
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 1219 */       ArrayList.subListRangeCheck(param1Int1, param1Int2, this.size);
/* 1220 */       return new SubList(this, this.offset, param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     private void rangeCheck(int param1Int) {
/* 1224 */       if (param1Int < 0 || param1Int >= this.size)
/* 1225 */         throw new IndexOutOfBoundsException(outOfBoundsMsg(param1Int)); 
/*      */     }
/*      */     
/*      */     private void rangeCheckForAdd(int param1Int) {
/* 1229 */       if (param1Int < 0 || param1Int > this.size)
/* 1230 */         throw new IndexOutOfBoundsException(outOfBoundsMsg(param1Int)); 
/*      */     }
/*      */     
/*      */     private String outOfBoundsMsg(int param1Int) {
/* 1234 */       return "Index: " + param1Int + ", Size: " + this.size;
/*      */     }
/*      */     
/*      */     private void checkForComodification() {
/* 1238 */       if (ArrayList.this.modCount != this.modCount)
/* 1239 */         throw new ConcurrentModificationException(); 
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 1243 */       checkForComodification();
/* 1244 */       return new ArrayList.ArrayListSpliterator<>(ArrayList.this, this.offset, this.offset + this.size, this.modCount);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(Consumer<? super E> paramConsumer) {
/* 1251 */     Objects.requireNonNull(paramConsumer);
/* 1252 */     int i = this.modCount;
/*      */     
/* 1254 */     Object[] arrayOfObject = this.elementData;
/* 1255 */     int j = this.size;
/* 1256 */     for (byte b = 0; this.modCount == i && b < j; b++) {
/* 1257 */       paramConsumer.accept((E)arrayOfObject[b]);
/*      */     }
/* 1259 */     if (this.modCount != i) {
/* 1260 */       throw new ConcurrentModificationException();
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
/*      */   public Spliterator<E> spliterator() {
/* 1279 */     return new ArrayListSpliterator<>(this, 0, -1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ArrayListSpliterator<E>
/*      */     implements Spliterator<E>
/*      */   {
/*      */     private final ArrayList<E> list;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int index;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int fence;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int expectedModCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ArrayListSpliterator(ArrayList<E> param1ArrayList, int param1Int1, int param1Int2, int param1Int3) {
/* 1325 */       this.list = param1ArrayList;
/* 1326 */       this.index = param1Int1;
/* 1327 */       this.fence = param1Int2;
/* 1328 */       this.expectedModCount = param1Int3;
/*      */     }
/*      */ 
/*      */     
/*      */     private int getFence() {
/*      */       int i;
/* 1334 */       if ((i = this.fence) < 0) {
/* 1335 */         ArrayList<E> arrayList; if ((arrayList = this.list) == null) {
/* 1336 */           i = this.fence = 0;
/*      */         } else {
/* 1338 */           this.expectedModCount = arrayList.modCount;
/* 1339 */           i = this.fence = arrayList.size;
/*      */         } 
/*      */       } 
/* 1342 */       return i;
/*      */     }
/*      */     
/*      */     public ArrayListSpliterator<E> trySplit() {
/* 1346 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1347 */       return (j >= k) ? null : new ArrayListSpliterator(this.list, j, this.index = k, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 1353 */       if (param1Consumer == null)
/* 1354 */         throw new NullPointerException(); 
/* 1355 */       int i = getFence(), j = this.index;
/* 1356 */       if (j < i) {
/* 1357 */         this.index = j + 1;
/* 1358 */         Object object = this.list.elementData[j];
/* 1359 */         param1Consumer.accept((E)object);
/* 1360 */         if (this.list.modCount != this.expectedModCount)
/* 1361 */           throw new ConcurrentModificationException(); 
/* 1362 */         return true;
/*      */       } 
/* 1364 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1370 */       if (param1Consumer == null)
/* 1371 */         throw new NullPointerException();  ArrayList<E> arrayList; Object[] arrayOfObject;
/* 1372 */       if ((arrayList = this.list) != null && (arrayOfObject = arrayList.elementData) != null) {
/* 1373 */         int k; int j; if ((j = this.fence) < 0) {
/* 1374 */           k = arrayList.modCount;
/* 1375 */           j = arrayList.size;
/*      */         } else {
/*      */           
/* 1378 */           k = this.expectedModCount;
/* 1379 */         }  int i; if ((i = this.index) >= 0 && (this.index = j) <= arrayOfObject.length) {
/* 1380 */           for (; i < j; i++) {
/* 1381 */             Object object = arrayOfObject[i];
/* 1382 */             param1Consumer.accept((E)object);
/*      */           } 
/* 1384 */           if (arrayList.modCount == k)
/*      */             return; 
/*      */         } 
/*      */       } 
/* 1388 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1392 */       return (getFence() - this.index);
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1396 */       return 16464;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeIf(Predicate<? super E> paramPredicate) {
/* 1402 */     Objects.requireNonNull(paramPredicate);
/*      */ 
/*      */ 
/*      */     
/* 1406 */     byte b1 = 0;
/* 1407 */     BitSet bitSet = new BitSet(this.size);
/* 1408 */     int i = this.modCount;
/* 1409 */     int j = this.size; byte b2;
/* 1410 */     for (b2 = 0; this.modCount == i && b2 < j; b2++) {
/*      */       
/* 1412 */       Object object = this.elementData[b2];
/* 1413 */       if (paramPredicate.test((E)object)) {
/* 1414 */         bitSet.set(b2);
/* 1415 */         b1++;
/*      */       } 
/*      */     } 
/* 1418 */     if (this.modCount != i) {
/* 1419 */       throw new ConcurrentModificationException();
/*      */     }
/*      */ 
/*      */     
/* 1423 */     b2 = (b1 > 0) ? 1 : 0;
/* 1424 */     if (b2 != 0) {
/* 1425 */       int k = j - b1; int m; byte b;
/* 1426 */       for (m = 0, b = 0; m < j && b < k; m++, b++) {
/* 1427 */         m = bitSet.nextClearBit(m);
/* 1428 */         this.elementData[b] = this.elementData[m];
/*      */       } 
/* 1430 */       for (m = k; m < j; m++) {
/* 1431 */         this.elementData[m] = null;
/*      */       }
/* 1433 */       this.size = k;
/* 1434 */       if (this.modCount != i) {
/* 1435 */         throw new ConcurrentModificationException();
/*      */       }
/* 1437 */       this.modCount++;
/*      */     } 
/*      */     
/* 1440 */     return b2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceAll(UnaryOperator<E> paramUnaryOperator) {
/* 1446 */     Objects.requireNonNull(paramUnaryOperator);
/* 1447 */     int i = this.modCount;
/* 1448 */     int j = this.size;
/* 1449 */     for (byte b = 0; this.modCount == i && b < j; b++) {
/* 1450 */       this.elementData[b] = paramUnaryOperator.apply((E)this.elementData[b]);
/*      */     }
/* 1452 */     if (this.modCount != i) {
/* 1453 */       throw new ConcurrentModificationException();
/*      */     }
/* 1455 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sort(Comparator<? super E> paramComparator) {
/* 1461 */     int i = this.modCount;
/* 1462 */     Arrays.sort(this.elementData, 0, this.size, (Comparator)paramComparator);
/* 1463 */     if (this.modCount != i) {
/* 1464 */       throw new ConcurrentModificationException();
/*      */     }
/* 1466 */     this.modCount++;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */