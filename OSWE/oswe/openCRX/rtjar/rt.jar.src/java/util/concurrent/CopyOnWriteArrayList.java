/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
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
/*      */ public class CopyOnWriteArrayList<E>
/*      */   implements List<E>, RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 8673264195747942595L;
/*      */   final transient ReentrantLock lock;
/*      */   private volatile transient Object[] array;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long lockOffset;
/*      */   
/*      */   final Object[] getArray() {
/*  107 */     return this.array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setArray(Object[] paramArrayOfObject) {
/*  114 */     this.array = paramArrayOfObject;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CopyOnWriteArrayList() {
/*      */     this.lock = new ReentrantLock();
/*  121 */     setArray(new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CopyOnWriteArrayList(Collection<? extends E> paramCollection) {
/*      */     Object[] arrayOfObject;
/*      */     this.lock = new ReentrantLock();
/*  134 */     if (paramCollection.getClass() == CopyOnWriteArrayList.class) {
/*  135 */       arrayOfObject = ((CopyOnWriteArrayList)paramCollection).getArray();
/*      */     } else {
/*  137 */       arrayOfObject = paramCollection.toArray();
/*      */       
/*  139 */       if (arrayOfObject.getClass() != Object[].class)
/*  140 */         arrayOfObject = Arrays.copyOf(arrayOfObject, arrayOfObject.length, Object[].class); 
/*      */     } 
/*  142 */     setArray(arrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CopyOnWriteArrayList(E[] paramArrayOfE) {
/*      */     this.lock = new ReentrantLock();
/*  153 */     setArray(Arrays.copyOf(paramArrayOfE, paramArrayOfE.length, Object[].class));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  162 */     return (getArray()).length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  171 */     return (size() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean eq(Object paramObject1, Object paramObject2) {
/*  178 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
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
/*      */   private static int indexOf(Object paramObject, Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/*  192 */     if (paramObject == null)
/*  193 */     { for (int i = paramInt1; i < paramInt2; i++) {
/*  194 */         if (paramArrayOfObject[i] == null)
/*  195 */           return i; 
/*      */       }  }
/*  197 */     else { for (int i = paramInt1; i < paramInt2; i++) {
/*  198 */         if (paramObject.equals(paramArrayOfObject[i]))
/*  199 */           return i; 
/*      */       }  }
/*  201 */      return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int lastIndexOf(Object paramObject, Object[] paramArrayOfObject, int paramInt) {
/*  212 */     if (paramObject == null)
/*  213 */     { for (int i = paramInt; i >= 0; i--) {
/*  214 */         if (paramArrayOfObject[i] == null)
/*  215 */           return i; 
/*      */       }  }
/*  217 */     else { for (int i = paramInt; i >= 0; i--) {
/*  218 */         if (paramObject.equals(paramArrayOfObject[i]))
/*  219 */           return i; 
/*      */       }  }
/*  221 */      return -1;
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
/*  234 */     Object[] arrayOfObject = getArray();
/*  235 */     return (indexOf(paramObject, arrayOfObject, 0, arrayOfObject.length) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(Object paramObject) {
/*  242 */     Object[] arrayOfObject = getArray();
/*  243 */     return indexOf(paramObject, arrayOfObject, 0, arrayOfObject.length);
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
/*      */   public int indexOf(E paramE, int paramInt) {
/*  262 */     Object[] arrayOfObject = getArray();
/*  263 */     return indexOf(paramE, arrayOfObject, paramInt, arrayOfObject.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(Object paramObject) {
/*  270 */     Object[] arrayOfObject = getArray();
/*  271 */     return lastIndexOf(paramObject, arrayOfObject, arrayOfObject.length - 1);
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
/*      */   public int lastIndexOf(E paramE, int paramInt) {
/*  291 */     Object[] arrayOfObject = getArray();
/*  292 */     return lastIndexOf(paramE, arrayOfObject, paramInt);
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
/*      */   public Object clone() {
/*      */     try {
/*  305 */       CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList)super.clone();
/*  306 */       copyOnWriteArrayList.resetLock();
/*  307 */       return copyOnWriteArrayList;
/*  308 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/*  310 */       throw new InternalError();
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
/*  328 */     Object[] arrayOfObject = getArray();
/*  329 */     return Arrays.copyOf(arrayOfObject, arrayOfObject.length);
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT) {
/*  372 */     Object[] arrayOfObject = getArray();
/*  373 */     int i = arrayOfObject.length;
/*  374 */     if (paramArrayOfT.length < i) {
/*  375 */       return (T[])Arrays.<Object, Object>copyOf(arrayOfObject, i, (Class)paramArrayOfT.getClass());
/*      */     }
/*  377 */     System.arraycopy(arrayOfObject, 0, paramArrayOfT, 0, i);
/*  378 */     if (paramArrayOfT.length > i)
/*  379 */       paramArrayOfT[i] = null; 
/*  380 */     return paramArrayOfT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E get(Object[] paramArrayOfObject, int paramInt) {
/*  388 */     return (E)paramArrayOfObject[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E get(int paramInt) {
/*  397 */     return get(getArray(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E set(int paramInt, E paramE) {
/*  407 */     ReentrantLock reentrantLock = this.lock;
/*  408 */     reentrantLock.lock();
/*      */     try {
/*  410 */       Object[] arrayOfObject = getArray();
/*  411 */       E e = get(arrayOfObject, paramInt);
/*      */       
/*  413 */       if (e != paramE) {
/*  414 */         int i = arrayOfObject.length;
/*  415 */         Object[] arrayOfObject1 = Arrays.copyOf(arrayOfObject, i);
/*  416 */         arrayOfObject1[paramInt] = paramE;
/*  417 */         setArray(arrayOfObject1);
/*      */       } else {
/*      */         
/*  420 */         setArray(arrayOfObject);
/*      */       } 
/*  422 */       return e;
/*      */     } finally {
/*  424 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean add(E paramE) {
/*  435 */     ReentrantLock reentrantLock = this.lock;
/*  436 */     reentrantLock.lock();
/*      */     try {
/*  438 */       Object[] arrayOfObject1 = getArray();
/*  439 */       int i = arrayOfObject1.length;
/*  440 */       Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + 1);
/*  441 */       arrayOfObject2[i] = paramE;
/*  442 */       setArray(arrayOfObject2);
/*  443 */       return true;
/*      */     } finally {
/*  445 */       reentrantLock.unlock();
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
/*      */   public void add(int paramInt, E paramE) {
/*  457 */     ReentrantLock reentrantLock = this.lock;
/*  458 */     reentrantLock.lock();
/*      */     try {
/*  460 */       Object[] arrayOfObject2, arrayOfObject1 = getArray();
/*  461 */       int i = arrayOfObject1.length;
/*  462 */       if (paramInt > i || paramInt < 0) {
/*  463 */         throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + i);
/*      */       }
/*      */       
/*  466 */       int j = i - paramInt;
/*  467 */       if (j == 0) {
/*  468 */         arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + 1);
/*      */       } else {
/*  470 */         arrayOfObject2 = new Object[i + 1];
/*  471 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, paramInt);
/*  472 */         System.arraycopy(arrayOfObject1, paramInt, arrayOfObject2, paramInt + 1, j);
/*      */       } 
/*      */       
/*  475 */       arrayOfObject2[paramInt] = paramE;
/*  476 */       setArray(arrayOfObject2);
/*      */     } finally {
/*  478 */       reentrantLock.unlock();
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
/*      */   public E remove(int paramInt) {
/*  490 */     ReentrantLock reentrantLock = this.lock;
/*  491 */     reentrantLock.lock();
/*      */     try {
/*  493 */       Object[] arrayOfObject = getArray();
/*  494 */       int i = arrayOfObject.length;
/*  495 */       E e = get(arrayOfObject, paramInt);
/*  496 */       int j = i - paramInt - 1;
/*  497 */       if (j == 0) {
/*  498 */         setArray(Arrays.copyOf(arrayOfObject, i - 1));
/*      */       } else {
/*  500 */         Object[] arrayOfObject1 = new Object[i - 1];
/*  501 */         System.arraycopy(arrayOfObject, 0, arrayOfObject1, 0, paramInt);
/*  502 */         System.arraycopy(arrayOfObject, paramInt + 1, arrayOfObject1, paramInt, j);
/*      */         
/*  504 */         setArray(arrayOfObject1);
/*      */       } 
/*  506 */       return e;
/*      */     } finally {
/*  508 */       reentrantLock.unlock();
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
/*      */   public boolean remove(Object paramObject) {
/*  526 */     Object[] arrayOfObject = getArray();
/*  527 */     int i = indexOf(paramObject, arrayOfObject, 0, arrayOfObject.length);
/*  528 */     return (i < 0) ? false : remove(paramObject, arrayOfObject, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean remove(Object paramObject, Object[] paramArrayOfObject, int paramInt) {
/*  536 */     ReentrantLock reentrantLock = this.lock;
/*  537 */     reentrantLock.lock();
/*      */     try {
/*  539 */       Object[] arrayOfObject1 = getArray();
/*  540 */       int i = arrayOfObject1.length;
/*  541 */       if (paramArrayOfObject != arrayOfObject1) {
/*  542 */         int j = Math.min(paramInt, i); byte b;
/*  543 */         for (b = 0; b < j; b++) {
/*  544 */           if (arrayOfObject1[b] != paramArrayOfObject[b] && eq(paramObject, arrayOfObject1[b])) {
/*  545 */             paramInt = b;
/*      */             // Byte code: goto -> 135
/*      */           } 
/*      */         } 
/*  549 */         if (paramInt >= i) {
/*  550 */           b = 0; return b;
/*  551 */         }  if (arrayOfObject1[paramInt] != paramObject) {
/*      */           
/*  553 */           paramInt = indexOf(paramObject, arrayOfObject1, paramInt, i);
/*  554 */           if (paramInt < 0)
/*  555 */           { b = 0; return b; } 
/*      */         } 
/*  557 */       }  Object[] arrayOfObject2 = new Object[i - 1];
/*  558 */       System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, paramInt);
/*  559 */       System.arraycopy(arrayOfObject1, paramInt + 1, arrayOfObject2, paramInt, i - paramInt - 1);
/*      */ 
/*      */       
/*  562 */       setArray(arrayOfObject2);
/*  563 */       return true;
/*      */     } finally {
/*  565 */       reentrantLock.unlock();
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
/*      */   void removeRange(int paramInt1, int paramInt2) {
/*  582 */     ReentrantLock reentrantLock = this.lock;
/*  583 */     reentrantLock.lock();
/*      */     try {
/*  585 */       Object[] arrayOfObject = getArray();
/*  586 */       int i = arrayOfObject.length;
/*      */       
/*  588 */       if (paramInt1 < 0 || paramInt2 > i || paramInt2 < paramInt1)
/*  589 */         throw new IndexOutOfBoundsException(); 
/*  590 */       int j = i - paramInt2 - paramInt1;
/*  591 */       int k = i - paramInt2;
/*  592 */       if (k == 0) {
/*  593 */         setArray(Arrays.copyOf(arrayOfObject, j));
/*      */       } else {
/*  595 */         Object[] arrayOfObject1 = new Object[j];
/*  596 */         System.arraycopy(arrayOfObject, 0, arrayOfObject1, 0, paramInt1);
/*  597 */         System.arraycopy(arrayOfObject, paramInt2, arrayOfObject1, paramInt1, k);
/*      */         
/*  599 */         setArray(arrayOfObject1);
/*      */       } 
/*      */     } finally {
/*  602 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addIfAbsent(E paramE) {
/*  613 */     Object[] arrayOfObject = getArray();
/*  614 */     return (indexOf(paramE, arrayOfObject, 0, arrayOfObject.length) >= 0) ? false : 
/*  615 */       addIfAbsent(paramE, arrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addIfAbsent(E paramE, Object[] paramArrayOfObject) {
/*  623 */     ReentrantLock reentrantLock = this.lock;
/*  624 */     reentrantLock.lock();
/*      */     try {
/*  626 */       Object[] arrayOfObject1 = getArray();
/*  627 */       int i = arrayOfObject1.length;
/*  628 */       if (paramArrayOfObject != arrayOfObject1) {
/*      */         
/*  630 */         int j = Math.min(paramArrayOfObject.length, i); byte b;
/*  631 */         for (b = 0; b < j; b++) {
/*  632 */           if (arrayOfObject1[b] != paramArrayOfObject[b] && eq(paramE, arrayOfObject1[b]))
/*  633 */             return false; 
/*  634 */         }  if (indexOf(paramE, arrayOfObject1, j, i) >= 0) {
/*  635 */           b = 0; return b;
/*      */         } 
/*  637 */       }  Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + 1);
/*  638 */       arrayOfObject2[i] = paramE;
/*  639 */       setArray(arrayOfObject2);
/*  640 */       return true;
/*      */     } finally {
/*  642 */       reentrantLock.unlock();
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
/*      */   public boolean containsAll(Collection<?> paramCollection) {
/*  657 */     Object[] arrayOfObject = getArray();
/*  658 */     int i = arrayOfObject.length;
/*  659 */     for (Object object : paramCollection) {
/*  660 */       if (indexOf(object, arrayOfObject, 0, i) < 0)
/*  661 */         return false; 
/*      */     } 
/*  663 */     return true;
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
/*      */   public boolean removeAll(Collection<?> paramCollection) {
/*  683 */     if (paramCollection == null) throw new NullPointerException(); 
/*  684 */     ReentrantLock reentrantLock = this.lock;
/*  685 */     reentrantLock.lock();
/*      */     try {
/*  687 */       Object[] arrayOfObject = getArray();
/*  688 */       int i = arrayOfObject.length;
/*  689 */       if (i != 0) {
/*      */         
/*  691 */         byte b1 = 0;
/*  692 */         Object[] arrayOfObject1 = new Object[i]; byte b2;
/*  693 */         for (b2 = 0; b2 < i; b2++) {
/*  694 */           Object object = arrayOfObject[b2];
/*  695 */           if (!paramCollection.contains(object))
/*  696 */             arrayOfObject1[b1++] = object; 
/*      */         } 
/*  698 */         if (b1 != i) {
/*  699 */           setArray(Arrays.copyOf(arrayOfObject1, b1));
/*  700 */           b2 = 1; return b2;
/*      */         } 
/*      */       } 
/*  703 */       return false;
/*      */     } finally {
/*  705 */       reentrantLock.unlock();
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
/*      */   public boolean retainAll(Collection<?> paramCollection) {
/*  726 */     if (paramCollection == null) throw new NullPointerException(); 
/*  727 */     ReentrantLock reentrantLock = this.lock;
/*  728 */     reentrantLock.lock();
/*      */     try {
/*  730 */       Object[] arrayOfObject = getArray();
/*  731 */       int i = arrayOfObject.length;
/*  732 */       if (i != 0) {
/*      */         
/*  734 */         byte b1 = 0;
/*  735 */         Object[] arrayOfObject1 = new Object[i]; byte b2;
/*  736 */         for (b2 = 0; b2 < i; b2++) {
/*  737 */           Object object = arrayOfObject[b2];
/*  738 */           if (paramCollection.contains(object))
/*  739 */             arrayOfObject1[b1++] = object; 
/*      */         } 
/*  741 */         if (b1 != i) {
/*  742 */           setArray(Arrays.copyOf(arrayOfObject1, b1));
/*  743 */           b2 = 1; return b2;
/*      */         } 
/*      */       } 
/*  746 */       return false;
/*      */     } finally {
/*  748 */       reentrantLock.unlock();
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
/*      */   public int addAllAbsent(Collection<? extends E> paramCollection) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: invokeinterface toArray : ()[Ljava/lang/Object;
/*      */     //   6: astore_2
/*      */     //   7: aload_2
/*      */     //   8: arraylength
/*      */     //   9: ifne -> 14
/*      */     //   12: iconst_0
/*      */     //   13: ireturn
/*      */     //   14: aload_0
/*      */     //   15: getfield lock : Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   18: astore_3
/*      */     //   19: aload_3
/*      */     //   20: invokevirtual lock : ()V
/*      */     //   23: aload_0
/*      */     //   24: invokevirtual getArray : ()[Ljava/lang/Object;
/*      */     //   27: astore #4
/*      */     //   29: aload #4
/*      */     //   31: arraylength
/*      */     //   32: istore #5
/*      */     //   34: iconst_0
/*      */     //   35: istore #6
/*      */     //   37: iconst_0
/*      */     //   38: istore #7
/*      */     //   40: iload #7
/*      */     //   42: aload_2
/*      */     //   43: arraylength
/*      */     //   44: if_icmpge -> 93
/*      */     //   47: aload_2
/*      */     //   48: iload #7
/*      */     //   50: aaload
/*      */     //   51: astore #8
/*      */     //   53: aload #8
/*      */     //   55: aload #4
/*      */     //   57: iconst_0
/*      */     //   58: iload #5
/*      */     //   60: invokestatic indexOf : (Ljava/lang/Object;[Ljava/lang/Object;II)I
/*      */     //   63: ifge -> 87
/*      */     //   66: aload #8
/*      */     //   68: aload_2
/*      */     //   69: iconst_0
/*      */     //   70: iload #6
/*      */     //   72: invokestatic indexOf : (Ljava/lang/Object;[Ljava/lang/Object;II)I
/*      */     //   75: ifge -> 87
/*      */     //   78: aload_2
/*      */     //   79: iload #6
/*      */     //   81: iinc #6, 1
/*      */     //   84: aload #8
/*      */     //   86: aastore
/*      */     //   87: iinc #7, 1
/*      */     //   90: goto -> 40
/*      */     //   93: iload #6
/*      */     //   95: ifle -> 127
/*      */     //   98: aload #4
/*      */     //   100: iload #5
/*      */     //   102: iload #6
/*      */     //   104: iadd
/*      */     //   105: invokestatic copyOf : ([Ljava/lang/Object;I)[Ljava/lang/Object;
/*      */     //   108: astore #7
/*      */     //   110: aload_2
/*      */     //   111: iconst_0
/*      */     //   112: aload #7
/*      */     //   114: iload #5
/*      */     //   116: iload #6
/*      */     //   118: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*      */     //   121: aload_0
/*      */     //   122: aload #7
/*      */     //   124: invokevirtual setArray : ([Ljava/lang/Object;)V
/*      */     //   127: iload #6
/*      */     //   129: istore #7
/*      */     //   131: aload_3
/*      */     //   132: invokevirtual unlock : ()V
/*      */     //   135: iload #7
/*      */     //   137: ireturn
/*      */     //   138: astore #9
/*      */     //   140: aload_3
/*      */     //   141: invokevirtual unlock : ()V
/*      */     //   144: aload #9
/*      */     //   146: athrow
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #764	-> 0
/*      */     //   #765	-> 7
/*      */     //   #766	-> 12
/*      */     //   #767	-> 14
/*      */     //   #768	-> 19
/*      */     //   #770	-> 23
/*      */     //   #771	-> 29
/*      */     //   #772	-> 34
/*      */     //   #774	-> 37
/*      */     //   #775	-> 47
/*      */     //   #776	-> 53
/*      */     //   #777	-> 72
/*      */     //   #778	-> 78
/*      */     //   #774	-> 87
/*      */     //   #780	-> 93
/*      */     //   #781	-> 98
/*      */     //   #782	-> 110
/*      */     //   #783	-> 121
/*      */     //   #785	-> 127
/*      */     //   #787	-> 131
/*      */     //   #785	-> 135
/*      */     //   #787	-> 138
/*      */     //   #788	-> 144
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   23	131	138	finally
/*      */     //   138	140	138	finally
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
/*      */   public void clear() {
/*  796 */     ReentrantLock reentrantLock = this.lock;
/*  797 */     reentrantLock.lock();
/*      */     try {
/*  799 */       setArray(new Object[0]);
/*      */     } finally {
/*  801 */       reentrantLock.unlock();
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
/*      */   public boolean addAll(Collection<? extends E> paramCollection) {
/*  817 */     Object[] arrayOfObject = (paramCollection.getClass() == CopyOnWriteArrayList.class) ? ((CopyOnWriteArrayList)paramCollection).getArray() : paramCollection.toArray();
/*  818 */     if (arrayOfObject.length == 0)
/*  819 */       return false; 
/*  820 */     ReentrantLock reentrantLock = this.lock;
/*  821 */     reentrantLock.lock();
/*      */     try {
/*  823 */       Object[] arrayOfObject1 = getArray();
/*  824 */       int i = arrayOfObject1.length;
/*  825 */       if (i == 0 && arrayOfObject.getClass() == Object[].class) {
/*  826 */         setArray(arrayOfObject);
/*      */       } else {
/*  828 */         Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + arrayOfObject.length);
/*  829 */         System.arraycopy(arrayOfObject, 0, arrayOfObject2, i, arrayOfObject.length);
/*  830 */         setArray(arrayOfObject2);
/*      */       } 
/*  832 */       return true;
/*      */     } finally {
/*  834 */       reentrantLock.unlock();
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
/*      */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/*  855 */     Object[] arrayOfObject = paramCollection.toArray();
/*  856 */     ReentrantLock reentrantLock = this.lock;
/*  857 */     reentrantLock.lock();
/*      */     try {
/*  859 */       Object[] arrayOfObject2, arrayOfObject1 = getArray();
/*  860 */       int i = arrayOfObject1.length;
/*  861 */       if (paramInt > i || paramInt < 0) {
/*  862 */         throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + i);
/*      */       }
/*  864 */       if (arrayOfObject.length == 0)
/*  865 */         return false; 
/*  866 */       int j = i - paramInt;
/*      */       
/*  868 */       if (j == 0) {
/*  869 */         arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + arrayOfObject.length);
/*      */       } else {
/*  871 */         arrayOfObject2 = new Object[i + arrayOfObject.length];
/*  872 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, paramInt);
/*  873 */         System.arraycopy(arrayOfObject1, paramInt, arrayOfObject2, paramInt + arrayOfObject.length, j);
/*      */       } 
/*      */ 
/*      */       
/*  877 */       System.arraycopy(arrayOfObject, 0, arrayOfObject2, paramInt, arrayOfObject.length);
/*  878 */       setArray(arrayOfObject2);
/*  879 */       return true;
/*      */     } finally {
/*  881 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void forEach(Consumer<? super E> paramConsumer) {
/*  886 */     if (paramConsumer == null) throw new NullPointerException(); 
/*  887 */     Object[] arrayOfObject = getArray();
/*  888 */     int i = arrayOfObject.length;
/*  889 */     for (byte b = 0; b < i; b++) {
/*  890 */       Object object = arrayOfObject[b];
/*  891 */       paramConsumer.accept((E)object);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean removeIf(Predicate<? super E> paramPredicate) {
/*  896 */     if (paramPredicate == null) throw new NullPointerException(); 
/*  897 */     ReentrantLock reentrantLock = this.lock;
/*  898 */     reentrantLock.lock();
/*      */     try {
/*  900 */       Object[] arrayOfObject = getArray();
/*  901 */       int i = arrayOfObject.length;
/*  902 */       if (i != 0) {
/*  903 */         byte b1 = 0;
/*  904 */         Object[] arrayOfObject1 = new Object[i]; byte b2;
/*  905 */         for (b2 = 0; b2 < i; b2++) {
/*  906 */           Object object = arrayOfObject[b2];
/*  907 */           if (!paramPredicate.test((E)object))
/*  908 */             arrayOfObject1[b1++] = object; 
/*      */         } 
/*  910 */         if (b1 != i) {
/*  911 */           setArray(Arrays.copyOf(arrayOfObject1, b1));
/*  912 */           b2 = 1; return b2;
/*      */         } 
/*      */       } 
/*  915 */       return false;
/*      */     } finally {
/*  917 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void replaceAll(UnaryOperator<E> paramUnaryOperator) {
/*  922 */     if (paramUnaryOperator == null) throw new NullPointerException(); 
/*  923 */     ReentrantLock reentrantLock = this.lock;
/*  924 */     reentrantLock.lock();
/*      */     try {
/*  926 */       Object[] arrayOfObject1 = getArray();
/*  927 */       int i = arrayOfObject1.length;
/*  928 */       Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i);
/*  929 */       for (byte b = 0; b < i; b++) {
/*  930 */         Object object = arrayOfObject1[b];
/*  931 */         arrayOfObject2[b] = paramUnaryOperator.apply((E)object);
/*      */       } 
/*  933 */       setArray(arrayOfObject2);
/*      */     } finally {
/*  935 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void sort(Comparator<? super E> paramComparator) {
/*  940 */     ReentrantLock reentrantLock = this.lock;
/*  941 */     reentrantLock.lock();
/*      */     try {
/*  943 */       Object[] arrayOfObject1 = getArray();
/*  944 */       Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, arrayOfObject1.length);
/*  945 */       Object[] arrayOfObject3 = arrayOfObject2;
/*  946 */       Arrays.sort(arrayOfObject3, (Comparator)paramComparator);
/*  947 */       setArray(arrayOfObject2);
/*      */     } finally {
/*  949 */       reentrantLock.unlock();
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
/*  965 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*  967 */     Object[] arrayOfObject = getArray();
/*      */     
/*  969 */     paramObjectOutputStream.writeInt(arrayOfObject.length);
/*      */ 
/*      */     
/*  972 */     for (Object object : arrayOfObject) {
/*  973 */       paramObjectOutputStream.writeObject(object);
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
/*  986 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/*  989 */     resetLock();
/*      */ 
/*      */     
/*  992 */     int i = paramObjectInputStream.readInt();
/*  993 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Object[].class, i);
/*  994 */     Object[] arrayOfObject = new Object[i];
/*      */ 
/*      */     
/*  997 */     for (byte b = 0; b < i; b++)
/*  998 */       arrayOfObject[b] = paramObjectInputStream.readObject(); 
/*  999 */     setArray(arrayOfObject);
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
/*      */   public String toString() {
/* 1013 */     return Arrays.toString(getArray());
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
/*      */   public boolean equals(Object paramObject) {
/* 1032 */     if (paramObject == this)
/* 1033 */       return true; 
/* 1034 */     if (!(paramObject instanceof List)) {
/* 1035 */       return false;
/*      */     }
/* 1037 */     List list = (List)paramObject;
/* 1038 */     Iterator iterator = list.iterator();
/* 1039 */     Object[] arrayOfObject = getArray();
/* 1040 */     int i = arrayOfObject.length;
/* 1041 */     for (byte b = 0; b < i; b++) {
/* 1042 */       if (!iterator.hasNext() || !eq(arrayOfObject[b], iterator.next()))
/* 1043 */         return false; 
/* 1044 */     }  if (iterator.hasNext())
/* 1045 */       return false; 
/* 1046 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1057 */     int i = 1;
/* 1058 */     Object[] arrayOfObject = getArray();
/* 1059 */     int j = arrayOfObject.length;
/* 1060 */     for (byte b = 0; b < j; b++) {
/* 1061 */       Object object = arrayOfObject[b];
/* 1062 */       i = 31 * i + ((object == null) ? 0 : object.hashCode());
/*      */     } 
/* 1064 */     return i;
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
/*      */   public Iterator<E> iterator() {
/* 1078 */     return new COWIterator<>(getArray(), 0);
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
/* 1090 */     return new COWIterator<>(getArray(), 0);
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
/*      */   public ListIterator<E> listIterator(int paramInt) {
/* 1104 */     Object[] arrayOfObject = getArray();
/* 1105 */     int i = arrayOfObject.length;
/* 1106 */     if (paramInt < 0 || paramInt > i) {
/* 1107 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
/*      */     }
/* 1109 */     return new COWIterator<>(arrayOfObject, paramInt);
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
/* 1127 */     return 
/* 1128 */       Spliterators.spliterator(getArray(), 1040);
/*      */   }
/*      */   
/*      */   static final class COWIterator<E>
/*      */     implements ListIterator<E>
/*      */   {
/*      */     private final Object[] snapshot;
/*      */     private int cursor;
/*      */     
/*      */     private COWIterator(Object[] param1ArrayOfObject, int param1Int) {
/* 1138 */       this.cursor = param1Int;
/* 1139 */       this.snapshot = param1ArrayOfObject;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1143 */       return (this.cursor < this.snapshot.length);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1147 */       return (this.cursor > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public E next() {
/* 1152 */       if (!hasNext())
/* 1153 */         throw new NoSuchElementException(); 
/* 1154 */       return (E)this.snapshot[this.cursor++];
/*      */     }
/*      */ 
/*      */     
/*      */     public E previous() {
/* 1159 */       if (!hasPrevious())
/* 1160 */         throw new NoSuchElementException(); 
/* 1161 */       return (E)this.snapshot[--this.cursor];
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1165 */       return this.cursor;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1169 */       return this.cursor - 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1178 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void set(E param1E) {
/* 1187 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void add(E param1E) {
/* 1196 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1201 */       Objects.requireNonNull(param1Consumer);
/* 1202 */       Object[] arrayOfObject = this.snapshot;
/* 1203 */       int i = arrayOfObject.length;
/* 1204 */       for (int j = this.cursor; j < i; j++) {
/* 1205 */         Object object = arrayOfObject[j];
/* 1206 */         param1Consumer.accept((E)object);
/*      */       } 
/* 1208 */       this.cursor = i;
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
/*      */   public List<E> subList(int paramInt1, int paramInt2) {
/* 1228 */     ReentrantLock reentrantLock = this.lock;
/* 1229 */     reentrantLock.lock();
/*      */     try {
/* 1231 */       Object[] arrayOfObject = getArray();
/* 1232 */       int i = arrayOfObject.length;
/* 1233 */       if (paramInt1 < 0 || paramInt2 > i || paramInt1 > paramInt2)
/* 1234 */         throw new IndexOutOfBoundsException(); 
/* 1235 */       return new COWSubList(this, paramInt1, paramInt2);
/*      */     } finally {
/* 1237 */       reentrantLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class COWSubList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private final CopyOnWriteArrayList<E> l;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int offset;
/*      */ 
/*      */ 
/*      */     
/*      */     private int size;
/*      */ 
/*      */ 
/*      */     
/*      */     private Object[] expectedArray;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     COWSubList(CopyOnWriteArrayList<E> param1CopyOnWriteArrayList, int param1Int1, int param1Int2) {
/* 1268 */       this.l = param1CopyOnWriteArrayList;
/* 1269 */       this.expectedArray = this.l.getArray();
/* 1270 */       this.offset = param1Int1;
/* 1271 */       this.size = param1Int2 - param1Int1;
/*      */     }
/*      */ 
/*      */     
/*      */     private void checkForComodification() {
/* 1276 */       if (this.l.getArray() != this.expectedArray) {
/* 1277 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     private void rangeCheck(int param1Int) {
/* 1282 */       if (param1Int < 0 || param1Int >= this.size) {
/* 1283 */         throw new IndexOutOfBoundsException("Index: " + param1Int + ",Size: " + this.size);
/*      */       }
/*      */     }
/*      */     
/*      */     public E set(int param1Int, E param1E) {
/* 1288 */       ReentrantLock reentrantLock = this.l.lock;
/* 1289 */       reentrantLock.lock();
/*      */       try {
/* 1291 */         rangeCheck(param1Int);
/* 1292 */         checkForComodification();
/* 1293 */         E e = this.l.set(param1Int + this.offset, param1E);
/* 1294 */         this.expectedArray = this.l.getArray();
/* 1295 */         return e;
/*      */       } finally {
/* 1297 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public E get(int param1Int) {
/* 1302 */       ReentrantLock reentrantLock = this.l.lock;
/* 1303 */       reentrantLock.lock();
/*      */       try {
/* 1305 */         rangeCheck(param1Int);
/* 1306 */         checkForComodification();
/* 1307 */         return this.l.get(param1Int + this.offset);
/*      */       } finally {
/* 1309 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1314 */       ReentrantLock reentrantLock = this.l.lock;
/* 1315 */       reentrantLock.lock();
/*      */       try {
/* 1317 */         checkForComodification();
/* 1318 */         return this.size;
/*      */       } finally {
/* 1320 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(int param1Int, E param1E) {
/* 1325 */       ReentrantLock reentrantLock = this.l.lock;
/* 1326 */       reentrantLock.lock();
/*      */       try {
/* 1328 */         checkForComodification();
/* 1329 */         if (param1Int < 0 || param1Int > this.size)
/* 1330 */           throw new IndexOutOfBoundsException(); 
/* 1331 */         this.l.add(param1Int + this.offset, param1E);
/* 1332 */         this.expectedArray = this.l.getArray();
/* 1333 */         this.size++;
/*      */       } finally {
/* 1335 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1340 */       ReentrantLock reentrantLock = this.l.lock;
/* 1341 */       reentrantLock.lock();
/*      */       try {
/* 1343 */         checkForComodification();
/* 1344 */         this.l.removeRange(this.offset, this.offset + this.size);
/* 1345 */         this.expectedArray = this.l.getArray();
/* 1346 */         this.size = 0;
/*      */       } finally {
/* 1348 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public E remove(int param1Int) {
/* 1353 */       ReentrantLock reentrantLock = this.l.lock;
/* 1354 */       reentrantLock.lock();
/*      */       try {
/* 1356 */         rangeCheck(param1Int);
/* 1357 */         checkForComodification();
/* 1358 */         E e = this.l.remove(param1Int + this.offset);
/* 1359 */         this.expectedArray = this.l.getArray();
/* 1360 */         this.size--;
/* 1361 */         return e;
/*      */       } finally {
/* 1363 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object) {
/* 1368 */       int i = indexOf(param1Object);
/* 1369 */       if (i == -1)
/* 1370 */         return false; 
/* 1371 */       remove(i);
/* 1372 */       return true;
/*      */     }
/*      */     
/*      */     public Iterator<E> iterator() {
/* 1376 */       ReentrantLock reentrantLock = this.l.lock;
/* 1377 */       reentrantLock.lock();
/*      */       try {
/* 1379 */         checkForComodification();
/* 1380 */         return new CopyOnWriteArrayList.COWSubListIterator<>(this.l, 0, this.offset, this.size);
/*      */       } finally {
/* 1382 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public ListIterator<E> listIterator(int param1Int) {
/* 1387 */       ReentrantLock reentrantLock = this.l.lock;
/* 1388 */       reentrantLock.lock();
/*      */       try {
/* 1390 */         checkForComodification();
/* 1391 */         if (param1Int < 0 || param1Int > this.size) {
/* 1392 */           throw new IndexOutOfBoundsException("Index: " + param1Int + ", Size: " + this.size);
/*      */         }
/* 1394 */         return new CopyOnWriteArrayList.COWSubListIterator<>(this.l, param1Int, this.offset, this.size);
/*      */       } finally {
/* 1396 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 1401 */       ReentrantLock reentrantLock = this.l.lock;
/* 1402 */       reentrantLock.lock();
/*      */       try {
/* 1404 */         checkForComodification();
/* 1405 */         if (param1Int1 < 0 || param1Int2 > this.size || param1Int1 > param1Int2)
/* 1406 */           throw new IndexOutOfBoundsException(); 
/* 1407 */         return new COWSubList(this.l, param1Int1 + this.offset, param1Int2 + this.offset);
/*      */       } finally {
/*      */         
/* 1410 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 1415 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 1416 */       int i = this.offset;
/* 1417 */       int j = this.offset + this.size;
/* 1418 */       Object[] arrayOfObject = this.expectedArray;
/* 1419 */       if (this.l.getArray() != arrayOfObject)
/* 1420 */         throw new ConcurrentModificationException(); 
/* 1421 */       if (i < 0 || j > arrayOfObject.length)
/* 1422 */         throw new IndexOutOfBoundsException(); 
/* 1423 */       for (int k = i; k < j; k++) {
/* 1424 */         Object object = arrayOfObject[k];
/* 1425 */         param1Consumer.accept((E)object);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 1430 */       if (param1UnaryOperator == null) throw new NullPointerException(); 
/* 1431 */       ReentrantLock reentrantLock = this.l.lock;
/* 1432 */       reentrantLock.lock();
/*      */       try {
/* 1434 */         int i = this.offset;
/* 1435 */         int j = this.offset + this.size;
/* 1436 */         Object[] arrayOfObject1 = this.expectedArray;
/* 1437 */         if (this.l.getArray() != arrayOfObject1)
/* 1438 */           throw new ConcurrentModificationException(); 
/* 1439 */         int k = arrayOfObject1.length;
/* 1440 */         if (i < 0 || j > k)
/* 1441 */           throw new IndexOutOfBoundsException(); 
/* 1442 */         Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, k);
/* 1443 */         for (int m = i; m < j; m++) {
/* 1444 */           Object object = arrayOfObject1[m];
/* 1445 */           arrayOfObject2[m] = param1UnaryOperator.apply((E)object);
/*      */         } 
/* 1447 */         this.l.setArray(this.expectedArray = arrayOfObject2);
/*      */       } finally {
/* 1449 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 1454 */       ReentrantLock reentrantLock = this.l.lock;
/* 1455 */       reentrantLock.lock();
/*      */       try {
/* 1457 */         int i = this.offset;
/* 1458 */         int j = this.offset + this.size;
/* 1459 */         Object[] arrayOfObject1 = this.expectedArray;
/* 1460 */         if (this.l.getArray() != arrayOfObject1)
/* 1461 */           throw new ConcurrentModificationException(); 
/* 1462 */         int k = arrayOfObject1.length;
/* 1463 */         if (i < 0 || j > k)
/* 1464 */           throw new IndexOutOfBoundsException(); 
/* 1465 */         Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, k);
/* 1466 */         Object[] arrayOfObject3 = arrayOfObject2;
/* 1467 */         Arrays.sort(arrayOfObject3, i, j, (Comparator)param1Comparator);
/* 1468 */         this.l.setArray(this.expectedArray = arrayOfObject2);
/*      */       } finally {
/* 1470 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/* 1475 */       if (param1Collection == null) throw new NullPointerException(); 
/* 1476 */       boolean bool = false;
/* 1477 */       ReentrantLock reentrantLock = this.l.lock;
/* 1478 */       reentrantLock.lock();
/*      */       try {
/* 1480 */         int i = this.size;
/* 1481 */         if (i > 0) {
/* 1482 */           int j = this.offset;
/* 1483 */           int k = this.offset + i;
/* 1484 */           Object[] arrayOfObject1 = this.expectedArray;
/* 1485 */           if (this.l.getArray() != arrayOfObject1)
/* 1486 */             throw new ConcurrentModificationException(); 
/* 1487 */           int m = arrayOfObject1.length;
/* 1488 */           if (j < 0 || k > m)
/* 1489 */             throw new IndexOutOfBoundsException(); 
/* 1490 */           byte b = 0;
/* 1491 */           Object[] arrayOfObject2 = new Object[i];
/* 1492 */           for (int n = j; n < k; n++) {
/* 1493 */             Object object = arrayOfObject1[n];
/* 1494 */             if (!param1Collection.contains(object))
/* 1495 */               arrayOfObject2[b++] = object; 
/*      */           } 
/* 1497 */           if (b != i) {
/* 1498 */             Object[] arrayOfObject = new Object[m - i + b];
/* 1499 */             System.arraycopy(arrayOfObject1, 0, arrayOfObject, 0, j);
/* 1500 */             System.arraycopy(arrayOfObject2, 0, arrayOfObject, j, b);
/* 1501 */             System.arraycopy(arrayOfObject1, k, arrayOfObject, j + b, m - k);
/*      */             
/* 1503 */             this.size = b;
/* 1504 */             bool = true;
/* 1505 */             this.l.setArray(this.expectedArray = arrayOfObject);
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1509 */         reentrantLock.unlock();
/*      */       } 
/* 1511 */       return bool;
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> param1Collection) {
/* 1515 */       if (param1Collection == null) throw new NullPointerException(); 
/* 1516 */       boolean bool = false;
/* 1517 */       ReentrantLock reentrantLock = this.l.lock;
/* 1518 */       reentrantLock.lock();
/*      */       try {
/* 1520 */         int i = this.size;
/* 1521 */         if (i > 0) {
/* 1522 */           int j = this.offset;
/* 1523 */           int k = this.offset + i;
/* 1524 */           Object[] arrayOfObject1 = this.expectedArray;
/* 1525 */           if (this.l.getArray() != arrayOfObject1)
/* 1526 */             throw new ConcurrentModificationException(); 
/* 1527 */           int m = arrayOfObject1.length;
/* 1528 */           if (j < 0 || k > m)
/* 1529 */             throw new IndexOutOfBoundsException(); 
/* 1530 */           byte b = 0;
/* 1531 */           Object[] arrayOfObject2 = new Object[i];
/* 1532 */           for (int n = j; n < k; n++) {
/* 1533 */             Object object = arrayOfObject1[n];
/* 1534 */             if (param1Collection.contains(object))
/* 1535 */               arrayOfObject2[b++] = object; 
/*      */           } 
/* 1537 */           if (b != i) {
/* 1538 */             Object[] arrayOfObject = new Object[m - i + b];
/* 1539 */             System.arraycopy(arrayOfObject1, 0, arrayOfObject, 0, j);
/* 1540 */             System.arraycopy(arrayOfObject2, 0, arrayOfObject, j, b);
/* 1541 */             System.arraycopy(arrayOfObject1, k, arrayOfObject, j + b, m - k);
/*      */             
/* 1543 */             this.size = b;
/* 1544 */             bool = true;
/* 1545 */             this.l.setArray(this.expectedArray = arrayOfObject);
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1549 */         reentrantLock.unlock();
/*      */       } 
/* 1551 */       return bool;
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 1555 */       if (param1Predicate == null) throw new NullPointerException(); 
/* 1556 */       boolean bool = false;
/* 1557 */       ReentrantLock reentrantLock = this.l.lock;
/* 1558 */       reentrantLock.lock();
/*      */       try {
/* 1560 */         int i = this.size;
/* 1561 */         if (i > 0) {
/* 1562 */           int j = this.offset;
/* 1563 */           int k = this.offset + i;
/* 1564 */           Object[] arrayOfObject1 = this.expectedArray;
/* 1565 */           if (this.l.getArray() != arrayOfObject1)
/* 1566 */             throw new ConcurrentModificationException(); 
/* 1567 */           int m = arrayOfObject1.length;
/* 1568 */           if (j < 0 || k > m)
/* 1569 */             throw new IndexOutOfBoundsException(); 
/* 1570 */           byte b = 0;
/* 1571 */           Object[] arrayOfObject2 = new Object[i];
/* 1572 */           for (int n = j; n < k; n++) {
/* 1573 */             Object object = arrayOfObject1[n];
/* 1574 */             if (!param1Predicate.test((E)object))
/* 1575 */               arrayOfObject2[b++] = object; 
/*      */           } 
/* 1577 */           if (b != i) {
/* 1578 */             Object[] arrayOfObject = new Object[m - i + b];
/* 1579 */             System.arraycopy(arrayOfObject1, 0, arrayOfObject, 0, j);
/* 1580 */             System.arraycopy(arrayOfObject2, 0, arrayOfObject, j, b);
/* 1581 */             System.arraycopy(arrayOfObject1, k, arrayOfObject, j + b, m - k);
/*      */             
/* 1583 */             this.size = b;
/* 1584 */             bool = true;
/* 1585 */             this.l.setArray(this.expectedArray = arrayOfObject);
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1589 */         reentrantLock.unlock();
/*      */       } 
/* 1591 */       return bool;
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 1595 */       int i = this.offset;
/* 1596 */       int j = this.offset + this.size;
/* 1597 */       Object[] arrayOfObject = this.expectedArray;
/* 1598 */       if (this.l.getArray() != arrayOfObject)
/* 1599 */         throw new ConcurrentModificationException(); 
/* 1600 */       if (i < 0 || j > arrayOfObject.length)
/* 1601 */         throw new IndexOutOfBoundsException(); 
/* 1602 */       return 
/* 1603 */         Spliterators.spliterator(arrayOfObject, i, j, 1040);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class COWSubListIterator<E>
/*      */     implements ListIterator<E> {
/*      */     private final ListIterator<E> it;
/*      */     private final int offset;
/*      */     private final int size;
/*      */     
/*      */     COWSubListIterator(List<E> param1List, int param1Int1, int param1Int2, int param1Int3) {
/* 1614 */       this.offset = param1Int2;
/* 1615 */       this.size = param1Int3;
/* 1616 */       this.it = param1List.listIterator(param1Int1 + param1Int2);
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1620 */       return (nextIndex() < this.size);
/*      */     }
/*      */     
/*      */     public E next() {
/* 1624 */       if (hasNext()) {
/* 1625 */         return this.it.next();
/*      */       }
/* 1627 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1631 */       return (previousIndex() >= 0);
/*      */     }
/*      */     
/*      */     public E previous() {
/* 1635 */       if (hasPrevious()) {
/* 1636 */         return this.it.previous();
/*      */       }
/* 1638 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1642 */       return this.it.nextIndex() - this.offset;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1646 */       return this.it.previousIndex() - this.offset;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1650 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void set(E param1E) {
/* 1654 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(E param1E) {
/* 1658 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1663 */       Objects.requireNonNull(param1Consumer);
/* 1664 */       int i = this.size;
/* 1665 */       ListIterator<E> listIterator = this.it;
/* 1666 */       while (nextIndex() < i) {
/* 1667 */         param1Consumer.accept(listIterator.next());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void resetLock() {
/* 1674 */     UNSAFE.putObjectVolatile(this, lockOffset, new ReentrantLock());
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1680 */       UNSAFE = Unsafe.getUnsafe();
/* 1681 */       Class<CopyOnWriteArrayList> clazz = CopyOnWriteArrayList.class;
/*      */       
/* 1683 */       lockOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("lock"));
/* 1684 */     } catch (Exception exception) {
/* 1685 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/CopyOnWriteArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */