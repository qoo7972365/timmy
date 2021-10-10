/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Vector<E>
/*      */   extends AbstractList<E>
/*      */   implements List<E>, RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   protected Object[] elementData;
/*      */   protected int elementCount;
/*      */   protected int capacityIncrement;
/*      */   private static final long serialVersionUID = -2767605614048989439L;
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   
/*      */   public Vector(int paramInt1, int paramInt2) {
/*  135 */     if (paramInt1 < 0) {
/*  136 */       throw new IllegalArgumentException("Illegal Capacity: " + paramInt1);
/*      */     }
/*  138 */     this.elementData = new Object[paramInt1];
/*  139 */     this.capacityIncrement = paramInt2;
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
/*      */   public Vector(int paramInt) {
/*  151 */     this(paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector() {
/*  160 */     this(10);
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
/*      */   public Vector(Collection<? extends E> paramCollection) {
/*  174 */     this.elementData = paramCollection.toArray();
/*  175 */     this.elementCount = this.elementData.length;
/*      */     
/*  177 */     if (this.elementData.getClass() != Object[].class) {
/*  178 */       this.elementData = Arrays.copyOf(this.elementData, this.elementCount, Object[].class);
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
/*      */   public synchronized void copyInto(Object[] paramArrayOfObject) {
/*  195 */     System.arraycopy(this.elementData, 0, paramArrayOfObject, 0, this.elementCount);
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
/*      */   public synchronized void trimToSize() {
/*  207 */     this.modCount++;
/*  208 */     int i = this.elementData.length;
/*  209 */     if (this.elementCount < i) {
/*  210 */       this.elementData = Arrays.copyOf(this.elementData, this.elementCount);
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
/*      */   public synchronized void ensureCapacity(int paramInt) {
/*  232 */     if (paramInt > 0) {
/*  233 */       this.modCount++;
/*  234 */       ensureCapacityHelper(paramInt);
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
/*      */   private void ensureCapacityHelper(int paramInt) {
/*  248 */     if (paramInt - this.elementData.length > 0) {
/*  249 */       grow(paramInt);
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
/*      */   private void grow(int paramInt) {
/*  262 */     int i = this.elementData.length;
/*  263 */     int j = i + ((this.capacityIncrement > 0) ? this.capacityIncrement : i);
/*      */     
/*  265 */     if (j - paramInt < 0)
/*  266 */       j = paramInt; 
/*  267 */     if (j - 2147483639 > 0)
/*  268 */       j = hugeCapacity(paramInt); 
/*  269 */     this.elementData = Arrays.copyOf(this.elementData, j);
/*      */   }
/*      */   
/*      */   private static int hugeCapacity(int paramInt) {
/*  273 */     if (paramInt < 0)
/*  274 */       throw new OutOfMemoryError(); 
/*  275 */     return (paramInt > 2147483639) ? Integer.MAX_VALUE : 2147483639;
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
/*      */   public synchronized void setSize(int paramInt) {
/*  290 */     this.modCount++;
/*  291 */     if (paramInt > this.elementCount) {
/*  292 */       ensureCapacityHelper(paramInt);
/*      */     } else {
/*  294 */       for (int i = paramInt; i < this.elementCount; i++) {
/*  295 */         this.elementData[i] = null;
/*      */       }
/*      */     } 
/*  298 */     this.elementCount = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int capacity() {
/*  309 */     return this.elementData.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int size() {
/*  318 */     return this.elementCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean isEmpty() {
/*  329 */     return (this.elementCount == 0);
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
/*      */   public Enumeration<E> elements() {
/*  342 */     return new Enumeration<E>() {
/*  343 */         int count = 0;
/*      */         
/*      */         public boolean hasMoreElements() {
/*  346 */           return (this.count < Vector.this.elementCount);
/*      */         }
/*      */         
/*      */         public E nextElement() {
/*  350 */           synchronized (Vector.this) {
/*  351 */             if (this.count < Vector.this.elementCount) {
/*  352 */               return Vector.this.elementData(this.count++);
/*      */             }
/*      */           } 
/*  355 */           throw new NoSuchElementException("Vector Enumeration");
/*      */         }
/*      */       };
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
/*  370 */     return (indexOf(paramObject, 0) >= 0);
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
/*      */   public int indexOf(Object paramObject) {
/*  385 */     return indexOf(paramObject, 0);
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
/*      */   public synchronized int indexOf(Object paramObject, int paramInt) {
/*  405 */     if (paramObject == null)
/*  406 */     { for (int i = paramInt; i < this.elementCount; i++) {
/*  407 */         if (this.elementData[i] == null)
/*  408 */           return i; 
/*      */       }  }
/*  410 */     else { for (int i = paramInt; i < this.elementCount; i++) {
/*  411 */         if (paramObject.equals(this.elementData[i]))
/*  412 */           return i; 
/*      */       }  }
/*  414 */      return -1;
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
/*      */   public synchronized int lastIndexOf(Object paramObject) {
/*  429 */     return lastIndexOf(paramObject, this.elementCount - 1);
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
/*      */   public synchronized int lastIndexOf(Object paramObject, int paramInt) {
/*  449 */     if (paramInt >= this.elementCount) {
/*  450 */       throw new IndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     }
/*  452 */     if (paramObject == null)
/*  453 */     { for (int i = paramInt; i >= 0; i--) {
/*  454 */         if (this.elementData[i] == null)
/*  455 */           return i; 
/*      */       }  }
/*  457 */     else { for (int i = paramInt; i >= 0; i--) {
/*  458 */         if (paramObject.equals(this.elementData[i]))
/*  459 */           return i; 
/*      */       }  }
/*  461 */      return -1;
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
/*      */   public synchronized E elementAt(int paramInt) {
/*  476 */     if (paramInt >= this.elementCount) {
/*  477 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     }
/*      */     
/*  480 */     return elementData(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized E firstElement() {
/*  491 */     if (this.elementCount == 0) {
/*  492 */       throw new NoSuchElementException();
/*      */     }
/*  494 */     return elementData(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized E lastElement() {
/*  505 */     if (this.elementCount == 0) {
/*  506 */       throw new NoSuchElementException();
/*      */     }
/*  508 */     return elementData(this.elementCount - 1);
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
/*      */   public synchronized void setElementAt(E paramE, int paramInt) {
/*  532 */     if (paramInt >= this.elementCount) {
/*  533 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     }
/*      */     
/*  536 */     this.elementData[paramInt] = paramE;
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
/*      */   public synchronized void removeElementAt(int paramInt) {
/*  559 */     this.modCount++;
/*  560 */     if (paramInt >= this.elementCount) {
/*  561 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     }
/*      */     
/*  564 */     if (paramInt < 0) {
/*  565 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  567 */     int i = this.elementCount - paramInt - 1;
/*  568 */     if (i > 0) {
/*  569 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  571 */     this.elementCount--;
/*  572 */     this.elementData[this.elementCount] = null;
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
/*      */   public synchronized void insertElementAt(E paramE, int paramInt) {
/*  599 */     this.modCount++;
/*  600 */     if (paramInt > this.elementCount) {
/*  601 */       throw new ArrayIndexOutOfBoundsException(paramInt + " > " + this.elementCount);
/*      */     }
/*      */     
/*  604 */     ensureCapacityHelper(this.elementCount + 1);
/*  605 */     System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.elementCount - paramInt);
/*  606 */     this.elementData[paramInt] = paramE;
/*  607 */     this.elementCount++;
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
/*      */   public synchronized void addElement(E paramE) {
/*  622 */     this.modCount++;
/*  623 */     ensureCapacityHelper(this.elementCount + 1);
/*  624 */     this.elementData[this.elementCount++] = paramE;
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
/*      */   public synchronized boolean removeElement(Object paramObject) {
/*  643 */     this.modCount++;
/*  644 */     int i = indexOf(paramObject);
/*  645 */     if (i >= 0) {
/*  646 */       removeElementAt(i);
/*  647 */       return true;
/*      */     } 
/*  649 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeAllElements() {
/*  659 */     this.modCount++;
/*      */     
/*  661 */     for (byte b = 0; b < this.elementCount; b++) {
/*  662 */       this.elementData[b] = null;
/*      */     }
/*  664 */     this.elementCount = 0;
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
/*      */   public synchronized Object clone() {
/*      */     try {
/*  677 */       Vector vector = (Vector)super.clone();
/*  678 */       vector.elementData = Arrays.copyOf(this.elementData, this.elementCount);
/*  679 */       vector.modCount = 0;
/*  680 */       return vector;
/*  681 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/*  683 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object[] toArray() {
/*  694 */     return Arrays.copyOf(this.elementData, this.elementCount);
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
/*      */   public synchronized <T> T[] toArray(T[] paramArrayOfT) {
/*  722 */     if (paramArrayOfT.length < this.elementCount) {
/*  723 */       return (T[])Arrays.<Object, Object>copyOf(this.elementData, this.elementCount, (Class)paramArrayOfT.getClass());
/*      */     }
/*  725 */     System.arraycopy(this.elementData, 0, paramArrayOfT, 0, this.elementCount);
/*      */     
/*  727 */     if (paramArrayOfT.length > this.elementCount) {
/*  728 */       paramArrayOfT[this.elementCount] = null;
/*      */     }
/*  730 */     return paramArrayOfT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   E elementData(int paramInt) {
/*  737 */     return (E)this.elementData[paramInt];
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
/*      */   public synchronized E get(int paramInt) {
/*  750 */     if (paramInt >= this.elementCount) {
/*  751 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  753 */     return elementData(paramInt);
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
/*      */   public synchronized E set(int paramInt, E paramE) {
/*  768 */     if (paramInt >= this.elementCount) {
/*  769 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  771 */     E e = elementData(paramInt);
/*  772 */     this.elementData[paramInt] = paramE;
/*  773 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean add(E paramE) {
/*  784 */     this.modCount++;
/*  785 */     ensureCapacityHelper(this.elementCount + 1);
/*  786 */     this.elementData[this.elementCount++] = paramE;
/*  787 */     return true;
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
/*  802 */     return removeElement(paramObject);
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
/*      */   public void add(int paramInt, E paramE) {
/*  817 */     insertElementAt(paramE, paramInt);
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
/*      */   public synchronized E remove(int paramInt) {
/*  832 */     this.modCount++;
/*  833 */     if (paramInt >= this.elementCount)
/*  834 */       throw new ArrayIndexOutOfBoundsException(paramInt); 
/*  835 */     E e = elementData(paramInt);
/*      */     
/*  837 */     int i = this.elementCount - paramInt - 1;
/*  838 */     if (i > 0) {
/*  839 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  841 */     this.elementData[--this.elementCount] = null;
/*      */     
/*  843 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  853 */     removeAllElements();
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
/*      */   public synchronized boolean containsAll(Collection<?> paramCollection) {
/*  869 */     return super.containsAll(paramCollection);
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
/*      */   public synchronized boolean addAll(Collection<? extends E> paramCollection) {
/*  886 */     this.modCount++;
/*  887 */     Object[] arrayOfObject = paramCollection.toArray();
/*  888 */     int i = arrayOfObject.length;
/*  889 */     ensureCapacityHelper(this.elementCount + i);
/*  890 */     System.arraycopy(arrayOfObject, 0, this.elementData, this.elementCount, i);
/*  891 */     this.elementCount += i;
/*  892 */     return (i != 0);
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
/*      */   public synchronized boolean removeAll(Collection<?> paramCollection) {
/*  913 */     return super.removeAll(paramCollection);
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
/*      */   public synchronized boolean retainAll(Collection<?> paramCollection) {
/*  936 */     return super.retainAll(paramCollection);
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
/*      */   public synchronized boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/*  957 */     this.modCount++;
/*  958 */     if (paramInt < 0 || paramInt > this.elementCount) {
/*  959 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  961 */     Object[] arrayOfObject = paramCollection.toArray();
/*  962 */     int i = arrayOfObject.length;
/*  963 */     ensureCapacityHelper(this.elementCount + i);
/*      */     
/*  965 */     int j = this.elementCount - paramInt;
/*  966 */     if (j > 0) {
/*  967 */       System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + i, j);
/*      */     }
/*      */     
/*  970 */     System.arraycopy(arrayOfObject, 0, this.elementData, paramInt, i);
/*  971 */     this.elementCount += i;
/*  972 */     return (i != 0);
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
/*      */   public synchronized boolean equals(Object paramObject) {
/*  988 */     return super.equals(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int hashCode() {
/*  995 */     return super.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String toString() {
/* 1003 */     return super.toString();
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
/*      */   public synchronized List<E> subList(int paramInt1, int paramInt2) {
/* 1041 */     return Collections.synchronizedList(super.subList(paramInt1, paramInt2), this);
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
/*      */   protected synchronized void removeRange(int paramInt1, int paramInt2) {
/* 1053 */     this.modCount++;
/* 1054 */     int i = this.elementCount - paramInt2;
/* 1055 */     System.arraycopy(this.elementData, paramInt2, this.elementData, paramInt1, i);
/*      */ 
/*      */ 
/*      */     
/* 1059 */     int j = this.elementCount - paramInt2 - paramInt1;
/* 1060 */     while (this.elementCount != j) {
/* 1061 */       this.elementData[--this.elementCount] = null;
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1077 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 1078 */     int i = getField.get("elementCount", 0);
/* 1079 */     Object[] arrayOfObject = (Object[])getField.get("elementData", (Object)null);
/* 1080 */     if (i < 0 || arrayOfObject == null || i > arrayOfObject.length) {
/* 1081 */       throw new StreamCorruptedException("Inconsistent vector internals");
/*      */     }
/* 1083 */     this.elementCount = i;
/* 1084 */     this.elementData = (Object[])arrayOfObject.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     Object[] arrayOfObject;
/* 1095 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/*      */     
/* 1097 */     synchronized (this) {
/* 1098 */       putField.put("capacityIncrement", this.capacityIncrement);
/* 1099 */       putField.put("elementCount", this.elementCount);
/* 1100 */       arrayOfObject = (Object[])this.elementData.clone();
/*      */     } 
/* 1102 */     putField.put("elementData", arrayOfObject);
/* 1103 */     paramObjectOutputStream.writeFields();
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
/*      */   public synchronized ListIterator<E> listIterator(int paramInt) {
/* 1119 */     if (paramInt < 0 || paramInt > this.elementCount)
/* 1120 */       throw new IndexOutOfBoundsException("Index: " + paramInt); 
/* 1121 */     return new ListItr(paramInt);
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
/*      */   public synchronized ListIterator<E> listIterator() {
/* 1133 */     return new ListItr(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Iterator<E> iterator() {
/* 1144 */     return new Itr();
/*      */   }
/*      */ 
/*      */   
/*      */   private class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     int cursor;
/* 1152 */     int lastRet = -1;
/* 1153 */     int expectedModCount = Vector.this.modCount;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1158 */       return (this.cursor != Vector.this.elementCount);
/*      */     }
/*      */     
/*      */     public E next() {
/* 1162 */       synchronized (Vector.this) {
/* 1163 */         checkForComodification();
/* 1164 */         int i = this.cursor;
/* 1165 */         if (i >= Vector.this.elementCount)
/* 1166 */           throw new NoSuchElementException(); 
/* 1167 */         this.cursor = i + 1;
/* 1168 */         return Vector.this.elementData(this.lastRet = i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1173 */       if (this.lastRet == -1)
/* 1174 */         throw new IllegalStateException(); 
/* 1175 */       synchronized (Vector.this) {
/* 1176 */         checkForComodification();
/* 1177 */         Vector.this.remove(this.lastRet);
/* 1178 */         this.expectedModCount = Vector.this.modCount;
/*      */       } 
/* 1180 */       this.cursor = this.lastRet;
/* 1181 */       this.lastRet = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1186 */       Objects.requireNonNull(param1Consumer);
/* 1187 */       synchronized (Vector.this) {
/* 1188 */         int i = Vector.this.elementCount;
/* 1189 */         int j = this.cursor;
/* 1190 */         if (j >= i) {
/*      */           return;
/*      */         }
/*      */         
/* 1194 */         Object[] arrayOfObject = Vector.this.elementData;
/* 1195 */         if (j >= arrayOfObject.length) {
/* 1196 */           throw new ConcurrentModificationException();
/*      */         }
/* 1198 */         while (j != i && Vector.this.modCount == this.expectedModCount) {
/* 1199 */           param1Consumer.accept((E)arrayOfObject[j++]);
/*      */         }
/*      */         
/* 1202 */         this.cursor = j;
/* 1203 */         this.lastRet = j - 1;
/* 1204 */         checkForComodification();
/*      */       } 
/*      */     }
/*      */     
/*      */     final void checkForComodification() {
/* 1209 */       if (Vector.this.modCount != this.expectedModCount)
/* 1210 */         throw new ConcurrentModificationException(); 
/*      */     }
/*      */     
/*      */     private Itr() {}
/*      */   }
/*      */   
/*      */   final class ListItr
/*      */     extends Itr
/*      */     implements ListIterator<E> {
/*      */     ListItr(int param1Int) {
/* 1220 */       this.cursor = param1Int;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1224 */       return (this.cursor != 0);
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1228 */       return this.cursor;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1232 */       return this.cursor - 1;
/*      */     }
/*      */     
/*      */     public E previous() {
/* 1236 */       synchronized (Vector.this) {
/* 1237 */         checkForComodification();
/* 1238 */         int i = this.cursor - 1;
/* 1239 */         if (i < 0)
/* 1240 */           throw new NoSuchElementException(); 
/* 1241 */         this.cursor = i;
/* 1242 */         return Vector.this.elementData(this.lastRet = i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void set(E param1E) {
/* 1247 */       if (this.lastRet == -1)
/* 1248 */         throw new IllegalStateException(); 
/* 1249 */       synchronized (Vector.this) {
/* 1250 */         checkForComodification();
/* 1251 */         Vector.this.set(this.lastRet, param1E);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(E param1E) {
/* 1256 */       int i = this.cursor;
/* 1257 */       synchronized (Vector.this) {
/* 1258 */         checkForComodification();
/* 1259 */         Vector.this.add(i, param1E);
/* 1260 */         this.expectedModCount = Vector.this.modCount;
/*      */       } 
/* 1262 */       this.cursor = i + 1;
/* 1263 */       this.lastRet = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void forEach(Consumer<? super E> paramConsumer) {
/* 1269 */     Objects.requireNonNull(paramConsumer);
/* 1270 */     int i = this.modCount;
/*      */     
/* 1272 */     Object[] arrayOfObject = this.elementData;
/* 1273 */     int j = this.elementCount;
/* 1274 */     for (byte b = 0; this.modCount == i && b < j; b++) {
/* 1275 */       paramConsumer.accept((E)arrayOfObject[b]);
/*      */     }
/* 1277 */     if (this.modCount != i) {
/* 1278 */       throw new ConcurrentModificationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean removeIf(Predicate<? super E> paramPredicate) {
/* 1285 */     Objects.requireNonNull(paramPredicate);
/*      */ 
/*      */ 
/*      */     
/* 1289 */     byte b1 = 0;
/* 1290 */     int i = this.elementCount;
/* 1291 */     BitSet bitSet = new BitSet(i);
/* 1292 */     int j = this.modCount; byte b2;
/* 1293 */     for (b2 = 0; this.modCount == j && b2 < i; b2++) {
/*      */       
/* 1295 */       Object object = this.elementData[b2];
/* 1296 */       if (paramPredicate.test((E)object)) {
/* 1297 */         bitSet.set(b2);
/* 1298 */         b1++;
/*      */       } 
/*      */     } 
/* 1301 */     if (this.modCount != j) {
/* 1302 */       throw new ConcurrentModificationException();
/*      */     }
/*      */ 
/*      */     
/* 1306 */     b2 = (b1 > 0) ? 1 : 0;
/* 1307 */     if (b2 != 0) {
/* 1308 */       int k = i - b1; int m; byte b;
/* 1309 */       for (m = 0, b = 0; m < i && b < k; m++, b++) {
/* 1310 */         m = bitSet.nextClearBit(m);
/* 1311 */         this.elementData[b] = this.elementData[m];
/*      */       } 
/* 1313 */       for (m = k; m < i; m++) {
/* 1314 */         this.elementData[m] = null;
/*      */       }
/* 1316 */       this.elementCount = k;
/* 1317 */       if (this.modCount != j) {
/* 1318 */         throw new ConcurrentModificationException();
/*      */       }
/* 1320 */       this.modCount++;
/*      */     } 
/*      */     
/* 1323 */     return b2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void replaceAll(UnaryOperator<E> paramUnaryOperator) {
/* 1329 */     Objects.requireNonNull(paramUnaryOperator);
/* 1330 */     int i = this.modCount;
/* 1331 */     int j = this.elementCount;
/* 1332 */     for (byte b = 0; this.modCount == i && b < j; b++) {
/* 1333 */       this.elementData[b] = paramUnaryOperator.apply((E)this.elementData[b]);
/*      */     }
/* 1335 */     if (this.modCount != i) {
/* 1336 */       throw new ConcurrentModificationException();
/*      */     }
/* 1338 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void sort(Comparator<? super E> paramComparator) {
/* 1344 */     int i = this.modCount;
/* 1345 */     Arrays.sort(this.elementData, 0, this.elementCount, (Comparator)paramComparator);
/* 1346 */     if (this.modCount != i) {
/* 1347 */       throw new ConcurrentModificationException();
/*      */     }
/* 1349 */     this.modCount++;
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
/* 1367 */     return new VectorSpliterator<>(this, null, 0, -1, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   static final class VectorSpliterator<E>
/*      */     implements Spliterator<E>
/*      */   {
/*      */     private final Vector<E> list;
/*      */     private Object[] array;
/*      */     private int index;
/*      */     private int fence;
/*      */     private int expectedModCount;
/*      */     
/*      */     VectorSpliterator(Vector<E> param1Vector, Object[] param1ArrayOfObject, int param1Int1, int param1Int2, int param1Int3) {
/* 1381 */       this.list = param1Vector;
/* 1382 */       this.array = param1ArrayOfObject;
/* 1383 */       this.index = param1Int1;
/* 1384 */       this.fence = param1Int2;
/* 1385 */       this.expectedModCount = param1Int3;
/*      */     }
/*      */     
/*      */     private int getFence() {
/*      */       int i;
/* 1390 */       if ((i = this.fence) < 0) {
/* 1391 */         synchronized (this.list) {
/* 1392 */           this.array = this.list.elementData;
/* 1393 */           this.expectedModCount = this.list.modCount;
/* 1394 */           i = this.fence = this.list.elementCount;
/*      */         } 
/*      */       }
/* 1397 */       return i;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit() {
/* 1401 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1402 */       return (j >= k) ? null : new VectorSpliterator(this.list, this.array, j, this.index = k, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 1410 */       if (param1Consumer == null)
/* 1411 */         throw new NullPointerException();  int i;
/* 1412 */       if (getFence() > (i = this.index)) {
/* 1413 */         this.index = i + 1;
/* 1414 */         param1Consumer.accept((E)this.array[i]);
/* 1415 */         if (this.list.modCount != this.expectedModCount)
/* 1416 */           throw new ConcurrentModificationException(); 
/* 1417 */         return true;
/*      */       } 
/* 1419 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1426 */       if (param1Consumer == null)
/* 1427 */         throw new NullPointerException();  Vector<E> vector;
/* 1428 */       if ((vector = this.list) != null) {
/* 1429 */         Object[] arrayOfObject; int j; if ((j = this.fence) < 0) {
/* 1430 */           synchronized (vector) {
/* 1431 */             this.expectedModCount = vector.modCount;
/* 1432 */             arrayOfObject = this.array = vector.elementData;
/* 1433 */             j = this.fence = vector.elementCount;
/*      */           } 
/*      */         } else {
/*      */           
/* 1437 */           arrayOfObject = this.array;
/* 1438 */         }  int i; if (arrayOfObject != null && (i = this.index) >= 0 && (this.index = j) <= arrayOfObject.length) {
/* 1439 */           while (i < j)
/* 1440 */             param1Consumer.accept((E)arrayOfObject[i++]); 
/* 1441 */           if (vector.modCount == this.expectedModCount)
/*      */             return; 
/*      */         } 
/*      */       } 
/* 1445 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1449 */       return (getFence() - this.index);
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1453 */       return 16464;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Vector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */