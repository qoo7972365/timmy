/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IdentityHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements Map<K, V>, Serializable, Cloneable
/*      */ {
/*      */   private static final int DEFAULT_CAPACITY = 32;
/*      */   private static final int MINIMUM_CAPACITY = 4;
/*      */   private static final int MAXIMUM_CAPACITY = 536870912;
/*      */   transient Object[] table;
/*      */   int size;
/*      */   transient int modCount;
/*  190 */   static final Object NULL_KEY = new Object();
/*      */   
/*      */   private transient Set<Map.Entry<K, V>> entrySet;
/*      */   private static final long serialVersionUID = 8188218128353913216L;
/*      */   
/*      */   private static Object maskNull(Object paramObject) {
/*  196 */     return (paramObject == null) ? NULL_KEY : paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final Object unmaskNull(Object paramObject) {
/*  203 */     return (paramObject == NULL_KEY) ? null : paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdentityHashMap() {
/*  211 */     init(32);
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
/*      */   public IdentityHashMap(int paramInt) {
/*  224 */     if (paramInt < 0) {
/*  225 */       throw new IllegalArgumentException("expectedMaxSize is negative: " + paramInt);
/*      */     }
/*  227 */     init(capacity(paramInt));
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
/*      */   private static int capacity(int paramInt) {
/*  239 */     return (paramInt > 178956970) ? 536870912 : ((paramInt <= 2) ? 4 : 
/*      */ 
/*      */       
/*  242 */       Integer.highestOneBit(paramInt + (paramInt << 1)));
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
/*      */   private void init(int paramInt) {
/*  255 */     this.table = new Object[2 * paramInt];
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
/*      */   public IdentityHashMap(Map<? extends K, ? extends V> paramMap) {
/*  267 */     this((int)((1 + paramMap.size()) * 1.1D));
/*  268 */     putAll(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  277 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  288 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int hash(Object paramObject, int paramInt) {
/*  295 */     int i = System.identityHashCode(paramObject);
/*      */     
/*  297 */     return (i << 1) - (i << 8) & paramInt - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int nextKeyIndex(int paramInt1, int paramInt2) {
/*  304 */     return (paramInt1 + 2 < paramInt2) ? (paramInt1 + 2) : 0;
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
/*      */   public V get(Object paramObject) {
/*  326 */     Object object = maskNull(paramObject);
/*  327 */     Object[] arrayOfObject = this.table;
/*  328 */     int i = arrayOfObject.length;
/*  329 */     int j = hash(object, i);
/*      */     while (true) {
/*  331 */       Object object1 = arrayOfObject[j];
/*  332 */       if (object1 == object)
/*  333 */         return (V)arrayOfObject[j + 1]; 
/*  334 */       if (object1 == null)
/*  335 */         return null; 
/*  336 */       j = nextKeyIndex(j, i);
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
/*      */   public boolean containsKey(Object paramObject) {
/*  350 */     Object object = maskNull(paramObject);
/*  351 */     Object[] arrayOfObject = this.table;
/*  352 */     int i = arrayOfObject.length;
/*  353 */     int j = hash(object, i);
/*      */     while (true) {
/*  355 */       Object object1 = arrayOfObject[j];
/*  356 */       if (object1 == object)
/*  357 */         return true; 
/*  358 */       if (object1 == null)
/*  359 */         return false; 
/*  360 */       j = nextKeyIndex(j, i);
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
/*      */   public boolean containsValue(Object paramObject) {
/*  374 */     Object[] arrayOfObject = this.table;
/*  375 */     for (byte b = 1; b < arrayOfObject.length; b += 2) {
/*  376 */       if (arrayOfObject[b] == paramObject && arrayOfObject[b - 1] != null)
/*  377 */         return true; 
/*      */     } 
/*  379 */     return false;
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
/*      */   private boolean containsMapping(Object paramObject1, Object paramObject2) {
/*  391 */     Object object = maskNull(paramObject1);
/*  392 */     Object[] arrayOfObject = this.table;
/*  393 */     int i = arrayOfObject.length;
/*  394 */     int j = hash(object, i);
/*      */     while (true) {
/*  396 */       Object object1 = arrayOfObject[j];
/*  397 */       if (object1 == object)
/*  398 */         return (arrayOfObject[j + 1] == paramObject2); 
/*  399 */       if (object1 == null)
/*  400 */         return false; 
/*  401 */       j = nextKeyIndex(j, i);
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
/*      */   public V put(K paramK, V paramV) {
/*      */     Object[] arrayOfObject;
/*      */     int i, j;
/*  421 */     Object object = maskNull(paramK);
/*      */     
/*      */     while (true) {
/*  424 */       arrayOfObject = this.table;
/*  425 */       int k = arrayOfObject.length;
/*  426 */       i = hash(object, k);
/*      */       Object object1;
/*  428 */       for (; (object1 = arrayOfObject[i]) != null; 
/*  429 */         i = nextKeyIndex(i, k)) {
/*  430 */         if (object1 == object) {
/*      */           
/*  432 */           Object object2 = arrayOfObject[i + 1];
/*  433 */           arrayOfObject[i + 1] = paramV;
/*  434 */           return (V)object2;
/*      */         } 
/*      */       } 
/*      */       
/*  438 */       j = this.size + 1;
/*      */ 
/*      */       
/*  441 */       if (j + (j << 1) > k && resize(k))
/*      */         continue;  break;
/*      */     } 
/*  444 */     this.modCount++;
/*  445 */     arrayOfObject[i] = object;
/*  446 */     arrayOfObject[i + 1] = paramV;
/*  447 */     this.size = j;
/*  448 */     return null;
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
/*      */   private boolean resize(int paramInt) {
/*  460 */     int i = paramInt * 2;
/*      */     
/*  462 */     Object[] arrayOfObject1 = this.table;
/*  463 */     int j = arrayOfObject1.length;
/*  464 */     if (j == 1073741824) {
/*  465 */       if (this.size == 536870911)
/*  466 */         throw new IllegalStateException("Capacity exhausted."); 
/*  467 */       return false;
/*      */     } 
/*  469 */     if (j >= i) {
/*  470 */       return false;
/*      */     }
/*  472 */     Object[] arrayOfObject2 = new Object[i];
/*      */     
/*  474 */     for (byte b = 0; b < j; b += 2) {
/*  475 */       Object object = arrayOfObject1[b];
/*  476 */       if (object != null) {
/*  477 */         Object object1 = arrayOfObject1[b + 1];
/*  478 */         arrayOfObject1[b] = null;
/*  479 */         arrayOfObject1[b + 1] = null;
/*  480 */         int k = hash(object, i);
/*  481 */         while (arrayOfObject2[k] != null)
/*  482 */           k = nextKeyIndex(k, i); 
/*  483 */         arrayOfObject2[k] = object;
/*  484 */         arrayOfObject2[k + 1] = object1;
/*      */       } 
/*      */     } 
/*  487 */     this.table = arrayOfObject2;
/*  488 */     return true;
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
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap) {
/*  500 */     int i = paramMap.size();
/*  501 */     if (i == 0)
/*      */       return; 
/*  503 */     if (i > this.size) {
/*  504 */       resize(capacity(i));
/*      */     }
/*  506 */     for (Map.Entry<? extends K, ? extends V> entry : paramMap.entrySet()) {
/*  507 */       put((K)entry.getKey(), (V)entry.getValue());
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
/*      */   public V remove(Object paramObject) {
/*  520 */     Object object = maskNull(paramObject);
/*  521 */     Object[] arrayOfObject = this.table;
/*  522 */     int i = arrayOfObject.length;
/*  523 */     int j = hash(object, i);
/*      */     
/*      */     while (true) {
/*  526 */       Object object1 = arrayOfObject[j];
/*  527 */       if (object1 == object) {
/*  528 */         this.modCount++;
/*  529 */         this.size--;
/*      */         
/*  531 */         Object object2 = arrayOfObject[j + 1];
/*  532 */         arrayOfObject[j + 1] = null;
/*  533 */         arrayOfObject[j] = null;
/*  534 */         closeDeletion(j);
/*  535 */         return (V)object2;
/*      */       } 
/*  537 */       if (object1 == null)
/*  538 */         return null; 
/*  539 */       j = nextKeyIndex(j, i);
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
/*      */   private boolean removeMapping(Object paramObject1, Object paramObject2) {
/*  552 */     Object object = maskNull(paramObject1);
/*  553 */     Object[] arrayOfObject = this.table;
/*  554 */     int i = arrayOfObject.length;
/*  555 */     int j = hash(object, i);
/*      */     
/*      */     while (true) {
/*  558 */       Object object1 = arrayOfObject[j];
/*  559 */       if (object1 == object) {
/*  560 */         if (arrayOfObject[j + 1] != paramObject2)
/*  561 */           return false; 
/*  562 */         this.modCount++;
/*  563 */         this.size--;
/*  564 */         arrayOfObject[j] = null;
/*  565 */         arrayOfObject[j + 1] = null;
/*  566 */         closeDeletion(j);
/*  567 */         return true;
/*      */       } 
/*  569 */       if (object1 == null)
/*  570 */         return false; 
/*  571 */       j = nextKeyIndex(j, i);
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
/*      */   private void closeDeletion(int paramInt) {
/*  584 */     Object[] arrayOfObject = this.table;
/*  585 */     int i = arrayOfObject.length;
/*      */ 
/*      */     
/*      */     Object object;
/*      */     
/*      */     int j;
/*      */     
/*  592 */     for (j = nextKeyIndex(paramInt, i); (object = arrayOfObject[j]) != null; 
/*  593 */       j = nextKeyIndex(j, i)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  600 */       int k = hash(object, i);
/*  601 */       if ((j < k && (k <= paramInt || paramInt <= j)) || (k <= paramInt && paramInt <= j)) {
/*  602 */         arrayOfObject[paramInt] = object;
/*  603 */         arrayOfObject[paramInt + 1] = arrayOfObject[j + 1];
/*  604 */         arrayOfObject[j] = null;
/*  605 */         arrayOfObject[j + 1] = null;
/*  606 */         paramInt = j;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  616 */     this.modCount++;
/*  617 */     Object[] arrayOfObject = this.table;
/*  618 */     for (byte b = 0; b < arrayOfObject.length; b++)
/*  619 */       arrayOfObject[b] = null; 
/*  620 */     this.size = 0;
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
/*      */   public boolean equals(Object paramObject) {
/*  641 */     if (paramObject == this)
/*  642 */       return true; 
/*  643 */     if (paramObject instanceof IdentityHashMap) {
/*  644 */       IdentityHashMap identityHashMap = (IdentityHashMap)paramObject;
/*  645 */       if (identityHashMap.size() != this.size) {
/*  646 */         return false;
/*      */       }
/*  648 */       Object[] arrayOfObject = identityHashMap.table;
/*  649 */       for (byte b = 0; b < arrayOfObject.length; b += 2) {
/*  650 */         Object object = arrayOfObject[b];
/*  651 */         if (object != null && !containsMapping(object, arrayOfObject[b + 1]))
/*  652 */           return false; 
/*      */       } 
/*  654 */       return true;
/*  655 */     }  if (paramObject instanceof Map) {
/*  656 */       Map map = (Map)paramObject;
/*  657 */       return entrySet().equals(map.entrySet());
/*      */     } 
/*  659 */     return false;
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
/*      */   public int hashCode() {
/*  683 */     int i = 0;
/*  684 */     Object[] arrayOfObject = this.table;
/*  685 */     for (byte b = 0; b < arrayOfObject.length; b += 2) {
/*  686 */       Object object = arrayOfObject[b];
/*  687 */       if (object != null) {
/*  688 */         Object object1 = unmaskNull(object);
/*  689 */         i += System.identityHashCode(object1) ^ 
/*  690 */           System.identityHashCode(arrayOfObject[b + 1]);
/*      */       } 
/*      */     } 
/*  693 */     return i;
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
/*  704 */       IdentityHashMap identityHashMap = (IdentityHashMap)super.clone();
/*  705 */       identityHashMap.entrySet = null;
/*  706 */       identityHashMap.table = (Object[])this.table.clone();
/*  707 */       return identityHashMap;
/*  708 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  709 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */   
/*      */   private abstract class IdentityHashMapIterator<T> implements Iterator<T> {
/*  714 */     int index = (IdentityHashMap.this.size != 0) ? 0 : IdentityHashMap.this.table.length;
/*  715 */     int expectedModCount = IdentityHashMap.this.modCount;
/*  716 */     int lastReturnedIndex = -1;
/*      */     boolean indexValid;
/*  718 */     Object[] traversalTable = IdentityHashMap.this.table;
/*      */     
/*      */     public boolean hasNext() {
/*  721 */       Object[] arrayOfObject = this.traversalTable;
/*  722 */       for (int i = this.index; i < arrayOfObject.length; i += 2) {
/*  723 */         Object object = arrayOfObject[i];
/*  724 */         if (object != null) {
/*  725 */           this.index = i;
/*  726 */           return this.indexValid = true;
/*      */         } 
/*      */       } 
/*  729 */       this.index = arrayOfObject.length;
/*  730 */       return false;
/*      */     }
/*      */     
/*      */     protected int nextIndex() {
/*  734 */       if (IdentityHashMap.this.modCount != this.expectedModCount)
/*  735 */         throw new ConcurrentModificationException(); 
/*  736 */       if (!this.indexValid && !hasNext()) {
/*  737 */         throw new NoSuchElementException();
/*      */       }
/*  739 */       this.indexValid = false;
/*  740 */       this.lastReturnedIndex = this.index;
/*  741 */       this.index += 2;
/*  742 */       return this.lastReturnedIndex;
/*      */     }
/*      */     private IdentityHashMapIterator() {}
/*      */     public void remove() {
/*  746 */       if (this.lastReturnedIndex == -1)
/*  747 */         throw new IllegalStateException(); 
/*  748 */       if (IdentityHashMap.this.modCount != this.expectedModCount) {
/*  749 */         throw new ConcurrentModificationException();
/*      */       }
/*  751 */       this.expectedModCount = ++IdentityHashMap.this.modCount;
/*  752 */       int i = this.lastReturnedIndex;
/*  753 */       this.lastReturnedIndex = -1;
/*      */       
/*  755 */       this.index = i;
/*  756 */       this.indexValid = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  770 */       Object[] arrayOfObject = this.traversalTable;
/*  771 */       int j = arrayOfObject.length;
/*      */       
/*  773 */       int k = i;
/*  774 */       Object object1 = arrayOfObject[k];
/*  775 */       arrayOfObject[k] = null;
/*  776 */       arrayOfObject[k + 1] = null;
/*      */ 
/*      */ 
/*      */       
/*  780 */       if (arrayOfObject != IdentityHashMap.this.table) {
/*  781 */         IdentityHashMap.this.remove(object1);
/*  782 */         this.expectedModCount = IdentityHashMap.this.modCount;
/*      */         
/*      */         return;
/*      */       } 
/*  786 */       IdentityHashMap.this.size--;
/*      */       Object object2;
/*      */       int m;
/*  789 */       for (m = IdentityHashMap.nextKeyIndex(k, j); (object2 = arrayOfObject[m]) != null; 
/*  790 */         m = IdentityHashMap.nextKeyIndex(m, j)) {
/*  791 */         int n = IdentityHashMap.hash(object2, j);
/*      */         
/*  793 */         if ((m < n && (n <= k || k <= m)) || (n <= k && k <= m)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  803 */           if (m < i && k >= i && this.traversalTable == IdentityHashMap.this.table) {
/*      */             
/*  805 */             int i1 = j - i;
/*  806 */             Object[] arrayOfObject1 = new Object[i1];
/*  807 */             System.arraycopy(arrayOfObject, i, arrayOfObject1, 0, i1);
/*      */             
/*  809 */             this.traversalTable = arrayOfObject1;
/*  810 */             this.index = 0;
/*      */           } 
/*      */           
/*  813 */           arrayOfObject[k] = object2;
/*  814 */           arrayOfObject[k + 1] = arrayOfObject[m + 1];
/*  815 */           arrayOfObject[m] = null;
/*  816 */           arrayOfObject[m + 1] = null;
/*  817 */           k = m;
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class KeyIterator extends IdentityHashMapIterator<K> {
/*      */     private KeyIterator() {}
/*      */     
/*      */     public K next() {
/*  826 */       return (K)IdentityHashMap.unmaskNull(this.traversalTable[nextIndex()]);
/*      */     } }
/*      */   
/*      */   private class ValueIterator extends IdentityHashMapIterator<V> {
/*      */     private ValueIterator() {}
/*      */     
/*      */     public V next() {
/*  833 */       return (V)this.traversalTable[nextIndex() + 1];
/*      */     }
/*      */   }
/*      */   
/*      */   private class EntryIterator extends IdentityHashMapIterator<Map.Entry<K, V>> {
/*      */     private Entry lastReturnedEntry;
/*      */     
/*      */     private EntryIterator() {}
/*      */     
/*      */     public Map.Entry<K, V> next() {
/*  843 */       this.lastReturnedEntry = new Entry(nextIndex());
/*  844 */       return this.lastReturnedEntry;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  848 */       this
/*  849 */         .lastReturnedIndex = (null == this.lastReturnedEntry) ? -1 : this.lastReturnedEntry.index;
/*  850 */       super.remove();
/*  851 */       this.lastReturnedEntry.index = this.lastReturnedIndex;
/*  852 */       this.lastReturnedEntry = null;
/*      */     }
/*      */     
/*      */     private class Entry implements Map.Entry<K, V> {
/*      */       private int index;
/*      */       
/*      */       private Entry(int param2Int) {
/*  859 */         this.index = param2Int;
/*      */       }
/*      */ 
/*      */       
/*      */       public K getKey() {
/*  864 */         checkIndexForEntryUse();
/*  865 */         return (K)IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index]);
/*      */       }
/*      */ 
/*      */       
/*      */       public V getValue() {
/*  870 */         checkIndexForEntryUse();
/*  871 */         return (V)IdentityHashMap.EntryIterator.this.traversalTable[this.index + 1];
/*      */       }
/*      */ 
/*      */       
/*      */       public V setValue(V param2V) {
/*  876 */         checkIndexForEntryUse();
/*  877 */         Object object = IdentityHashMap.EntryIterator.this.traversalTable[this.index + 1];
/*  878 */         IdentityHashMap.EntryIterator.this.traversalTable[this.index + 1] = param2V;
/*      */         
/*  880 */         if (IdentityHashMap.EntryIterator.this.traversalTable != IdentityHashMap.this.table)
/*  881 */           IdentityHashMap.this.put(IdentityHashMap.EntryIterator.this.traversalTable[this.index], param2V); 
/*  882 */         return (V)object;
/*      */       }
/*      */       
/*      */       public boolean equals(Object param2Object) {
/*  886 */         if (this.index < 0) {
/*  887 */           return super.equals(param2Object);
/*      */         }
/*  889 */         if (!(param2Object instanceof Map.Entry))
/*  890 */           return false; 
/*  891 */         Map.Entry entry = (Map.Entry)param2Object;
/*  892 */         return (entry.getKey() == IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index]) && entry
/*  893 */           .getValue() == IdentityHashMap.EntryIterator.this.traversalTable[this.index + 1]);
/*      */       }
/*      */       
/*      */       public int hashCode() {
/*  897 */         if (IdentityHashMap.EntryIterator.this.lastReturnedIndex < 0) {
/*  898 */           return super.hashCode();
/*      */         }
/*  900 */         return System.identityHashCode(IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index])) ^ 
/*  901 */           System.identityHashCode(IdentityHashMap.EntryIterator.this.traversalTable[this.index + 1]);
/*      */       }
/*      */       
/*      */       public String toString() {
/*  905 */         if (this.index < 0) {
/*  906 */           return super.toString();
/*      */         }
/*  908 */         return IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index]) + "=" + IdentityHashMap.EntryIterator.this.traversalTable[this.index + 1];
/*      */       }
/*      */ 
/*      */       
/*      */       private void checkIndexForEntryUse() {
/*  913 */         if (this.index < 0) {
/*  914 */           throw new IllegalStateException("Entry was removed");
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/*  967 */     Set<K> set = this.keySet;
/*  968 */     if (set == null) {
/*  969 */       set = new KeySet();
/*  970 */       this.keySet = set;
/*      */     } 
/*  972 */     return set;
/*      */   }
/*      */   private class KeySet extends AbstractSet<K> { private KeySet() {}
/*      */     
/*      */     public Iterator<K> iterator() {
/*  977 */       return new IdentityHashMap.KeyIterator();
/*      */     }
/*      */     public int size() {
/*  980 */       return IdentityHashMap.this.size;
/*      */     }
/*      */     public boolean contains(Object param1Object) {
/*  983 */       return IdentityHashMap.this.containsKey(param1Object);
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/*  986 */       int i = IdentityHashMap.this.size;
/*  987 */       IdentityHashMap.this.remove(param1Object);
/*  988 */       return (IdentityHashMap.this.size != i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/*  996 */       Objects.requireNonNull(param1Collection);
/*  997 */       boolean bool = false;
/*  998 */       for (Iterator<K> iterator = iterator(); iterator.hasNext();) {
/*  999 */         if (param1Collection.contains(iterator.next())) {
/* 1000 */           iterator.remove();
/* 1001 */           bool = true;
/*      */         } 
/*      */       } 
/* 1004 */       return bool;
/*      */     }
/*      */     public void clear() {
/* 1007 */       IdentityHashMap.this.clear();
/*      */     }
/*      */     public int hashCode() {
/* 1010 */       int i = 0;
/* 1011 */       for (K k : this)
/* 1012 */         i += System.identityHashCode(k); 
/* 1013 */       return i;
/*      */     }
/*      */     public Object[] toArray() {
/* 1016 */       return toArray(new Object[0]);
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 1020 */       int i = IdentityHashMap.this.modCount;
/* 1021 */       int j = size();
/* 1022 */       if (param1ArrayOfT.length < j)
/* 1023 */         param1ArrayOfT = (T[])Array.newInstance(param1ArrayOfT.getClass().getComponentType(), j); 
/* 1024 */       Object[] arrayOfObject = IdentityHashMap.this.table;
/* 1025 */       byte b1 = 0;
/* 1026 */       for (byte b2 = 0; b2 < arrayOfObject.length; b2 += 2) {
/*      */         Object object;
/* 1028 */         if ((object = arrayOfObject[b2]) != null) {
/*      */           
/* 1030 */           if (b1 >= j) {
/* 1031 */             throw new ConcurrentModificationException();
/*      */           }
/* 1033 */           param1ArrayOfT[b1++] = (T)IdentityHashMap.unmaskNull(object);
/*      */         } 
/*      */       } 
/*      */       
/* 1037 */       if (b1 < j || i != IdentityHashMap.this.modCount) {
/* 1038 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/* 1041 */       if (b1 < param1ArrayOfT.length) {
/* 1042 */         param1ArrayOfT[b1] = null;
/*      */       }
/* 1044 */       return param1ArrayOfT;
/*      */     }
/*      */     
/*      */     public Spliterator<K> spliterator() {
/* 1048 */       return new IdentityHashMap.KeySpliterator<>(IdentityHashMap.this, 0, -1, 0, 0);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> values() {
/* 1073 */     Collection<V> collection = this.values;
/* 1074 */     if (collection == null) {
/* 1075 */       collection = new Values();
/* 1076 */       this.values = collection;
/*      */     } 
/* 1078 */     return collection;
/*      */   }
/*      */   private class Values extends AbstractCollection<V> { private Values() {}
/*      */     
/*      */     public Iterator<V> iterator() {
/* 1083 */       return new IdentityHashMap.ValueIterator();
/*      */     }
/*      */     public int size() {
/* 1086 */       return IdentityHashMap.this.size;
/*      */     }
/*      */     public boolean contains(Object param1Object) {
/* 1089 */       return IdentityHashMap.this.containsValue(param1Object);
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/* 1092 */       for (Iterator<V> iterator = iterator(); iterator.hasNext();) {
/* 1093 */         if (iterator.next() == param1Object) {
/* 1094 */           iterator.remove();
/* 1095 */           return true;
/*      */         } 
/*      */       } 
/* 1098 */       return false;
/*      */     }
/*      */     public void clear() {
/* 1101 */       IdentityHashMap.this.clear();
/*      */     }
/*      */     public Object[] toArray() {
/* 1104 */       return toArray(new Object[0]);
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 1108 */       int i = IdentityHashMap.this.modCount;
/* 1109 */       int j = size();
/* 1110 */       if (param1ArrayOfT.length < j)
/* 1111 */         param1ArrayOfT = (T[])Array.newInstance(param1ArrayOfT.getClass().getComponentType(), j); 
/* 1112 */       Object[] arrayOfObject = IdentityHashMap.this.table;
/* 1113 */       byte b1 = 0;
/* 1114 */       for (byte b2 = 0; b2 < arrayOfObject.length; b2 += 2) {
/* 1115 */         if (arrayOfObject[b2] != null) {
/*      */           
/* 1117 */           if (b1 >= j) {
/* 1118 */             throw new ConcurrentModificationException();
/*      */           }
/* 1120 */           param1ArrayOfT[b1++] = (T)arrayOfObject[b2 + 1];
/*      */         } 
/*      */       } 
/*      */       
/* 1124 */       if (b1 < j || i != IdentityHashMap.this.modCount) {
/* 1125 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/* 1128 */       if (b1 < param1ArrayOfT.length) {
/* 1129 */         param1ArrayOfT[b1] = null;
/*      */       }
/* 1131 */       return param1ArrayOfT;
/*      */     }
/*      */     
/*      */     public Spliterator<V> spliterator() {
/* 1135 */       return new IdentityHashMap.ValueSpliterator<>(IdentityHashMap.this, 0, -1, 0, 0);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/* 1178 */     Set<Map.Entry<K, V>> set = this.entrySet;
/* 1179 */     if (set != null) {
/* 1180 */       return set;
/*      */     }
/* 1182 */     return this.entrySet = new EntrySet();
/*      */   }
/*      */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 1187 */       return new IdentityHashMap.EntryIterator();
/*      */     }
/*      */     public boolean contains(Object param1Object) {
/* 1190 */       if (!(param1Object instanceof Map.Entry))
/* 1191 */         return false; 
/* 1192 */       Map.Entry entry = (Map.Entry)param1Object;
/* 1193 */       return IdentityHashMap.this.containsMapping(entry.getKey(), entry.getValue());
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/* 1196 */       if (!(param1Object instanceof Map.Entry))
/* 1197 */         return false; 
/* 1198 */       Map.Entry entry = (Map.Entry)param1Object;
/* 1199 */       return IdentityHashMap.this.removeMapping(entry.getKey(), entry.getValue());
/*      */     }
/*      */     public int size() {
/* 1202 */       return IdentityHashMap.this.size;
/*      */     }
/*      */     public void clear() {
/* 1205 */       IdentityHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/* 1213 */       Objects.requireNonNull(param1Collection);
/* 1214 */       boolean bool = false;
/* 1215 */       for (Iterator<Map.Entry<K, V>> iterator = iterator(); iterator.hasNext();) {
/* 1216 */         if (param1Collection.contains(iterator.next())) {
/* 1217 */           iterator.remove();
/* 1218 */           bool = true;
/*      */         } 
/*      */       } 
/* 1221 */       return bool;
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 1225 */       return toArray(new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 1230 */       int i = IdentityHashMap.this.modCount;
/* 1231 */       int j = size();
/* 1232 */       if (param1ArrayOfT.length < j)
/* 1233 */         param1ArrayOfT = (T[])Array.newInstance(param1ArrayOfT.getClass().getComponentType(), j); 
/* 1234 */       Object[] arrayOfObject = IdentityHashMap.this.table;
/* 1235 */       byte b1 = 0;
/* 1236 */       for (byte b2 = 0; b2 < arrayOfObject.length; b2 += 2) {
/*      */         Object object;
/* 1238 */         if ((object = arrayOfObject[b2]) != null) {
/*      */           
/* 1240 */           if (b1 >= j) {
/* 1241 */             throw new ConcurrentModificationException();
/*      */           }
/* 1243 */           param1ArrayOfT[b1++] = (T)new AbstractMap.SimpleEntry<>(IdentityHashMap.unmaskNull(object), arrayOfObject[b2 + 1]);
/*      */         } 
/*      */       } 
/*      */       
/* 1247 */       if (b1 < j || i != IdentityHashMap.this.modCount) {
/* 1248 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/* 1251 */       if (b1 < param1ArrayOfT.length) {
/* 1252 */         param1ArrayOfT[b1] = null;
/*      */       }
/* 1254 */       return param1ArrayOfT;
/*      */     }
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> spliterator() {
/* 1258 */       return new IdentityHashMap.EntrySpliterator<>(IdentityHashMap.this, 0, -1, 0, 0);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1278 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */     
/* 1281 */     paramObjectOutputStream.writeInt(this.size);
/*      */ 
/*      */     
/* 1284 */     Object[] arrayOfObject = this.table;
/* 1285 */     for (byte b = 0; b < arrayOfObject.length; b += 2) {
/* 1286 */       Object object = arrayOfObject[b];
/* 1287 */       if (object != null) {
/* 1288 */         paramObjectOutputStream.writeObject(unmaskNull(object));
/* 1289 */         paramObjectOutputStream.writeObject(arrayOfObject[b + 1]);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1301 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/* 1304 */     int i = paramObjectInputStream.readInt();
/* 1305 */     if (i < 0) {
/* 1306 */       throw new StreamCorruptedException("Illegal mappings count: " + i);
/*      */     }
/* 1308 */     int j = capacity(i);
/* 1309 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Object[].class, j);
/* 1310 */     init(j);
/*      */ 
/*      */     
/* 1313 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1315 */       Object object1 = paramObjectInputStream.readObject();
/*      */       
/* 1317 */       Object object2 = paramObjectInputStream.readObject();
/* 1318 */       putForCreate((K)object1, (V)object2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void putForCreate(K paramK, V paramV) throws StreamCorruptedException {
/* 1329 */     Object object1 = maskNull(paramK);
/* 1330 */     Object[] arrayOfObject = this.table;
/* 1331 */     int i = arrayOfObject.length;
/* 1332 */     int j = hash(object1, i);
/*      */     
/*      */     Object object2;
/* 1335 */     while ((object2 = arrayOfObject[j]) != null) {
/* 1336 */       if (object2 == object1)
/* 1337 */         throw new StreamCorruptedException(); 
/* 1338 */       j = nextKeyIndex(j, i);
/*      */     } 
/* 1340 */     arrayOfObject[j] = object1;
/* 1341 */     arrayOfObject[j + 1] = paramV;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 1347 */     Objects.requireNonNull(paramBiConsumer);
/* 1348 */     int i = this.modCount;
/*      */     
/* 1350 */     Object[] arrayOfObject = this.table;
/* 1351 */     for (byte b = 0; b < arrayOfObject.length; b += 2) {
/* 1352 */       Object object = arrayOfObject[b];
/* 1353 */       if (object != null) {
/* 1354 */         paramBiConsumer.accept((K)unmaskNull(object), (V)arrayOfObject[b + 1]);
/*      */       }
/*      */       
/* 1357 */       if (this.modCount != i) {
/* 1358 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1366 */     Objects.requireNonNull(paramBiFunction);
/* 1367 */     int i = this.modCount;
/*      */     
/* 1369 */     Object[] arrayOfObject = this.table;
/* 1370 */     for (byte b = 0; b < arrayOfObject.length; b += 2) {
/* 1371 */       Object object = arrayOfObject[b];
/* 1372 */       if (object != null) {
/* 1373 */         arrayOfObject[b + 1] = paramBiFunction.apply((K)unmaskNull(object), (V)arrayOfObject[b + 1]);
/*      */       }
/*      */       
/* 1376 */       if (this.modCount != i) {
/* 1377 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static class IdentityHashMapSpliterator<K, V>
/*      */   {
/*      */     final IdentityHashMap<K, V> map;
/*      */     
/*      */     int index;
/*      */     
/*      */     int fence;
/*      */     
/*      */     int est;
/*      */     int expectedModCount;
/*      */     
/*      */     IdentityHashMapSpliterator(IdentityHashMap<K, V> param1IdentityHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1395 */       this.map = param1IdentityHashMap;
/* 1396 */       this.index = param1Int1;
/* 1397 */       this.fence = param1Int2;
/* 1398 */       this.est = param1Int3;
/* 1399 */       this.expectedModCount = param1Int4;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/* 1404 */       if ((i = this.fence) < 0) {
/* 1405 */         this.est = this.map.size;
/* 1406 */         this.expectedModCount = this.map.modCount;
/* 1407 */         i = this.fence = this.map.table.length;
/*      */       } 
/* 1409 */       return i;
/*      */     }
/*      */     
/*      */     public final long estimateSize() {
/* 1413 */       getFence();
/* 1414 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends IdentityHashMapSpliterator<K, V>
/*      */     implements Spliterator<K>
/*      */   {
/*      */     KeySpliterator(IdentityHashMap<K, V> param1IdentityHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1423 */       super(param1IdentityHashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public KeySpliterator<K, V> trySplit() {
/* 1427 */       int i = getFence(), j = this.index, k = j + i >>> 1 & 0xFFFFFFFE;
/* 1428 */       return (j >= k) ? null : new KeySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> param1Consumer) {
/* 1435 */       if (param1Consumer == null)
/* 1436 */         throw new NullPointerException();  int i; int j;
/*      */       IdentityHashMap<K, V> identityHashMap;
/*      */       Object[] arrayOfObject;
/* 1439 */       if ((identityHashMap = this.map) != null && (arrayOfObject = identityHashMap.table) != null && (i = this.index) >= 0 && (this
/* 1440 */         .index = j = getFence()) <= arrayOfObject.length) {
/* 1441 */         for (; i < j; i += 2) {
/* 1442 */           Object object; if ((object = arrayOfObject[i]) != null)
/* 1443 */             param1Consumer.accept((K)IdentityHashMap.unmaskNull(object)); 
/*      */         } 
/* 1445 */         if (identityHashMap.modCount == this.expectedModCount)
/*      */           return; 
/*      */       } 
/* 1448 */       throw new ConcurrentModificationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> param1Consumer) {
/* 1453 */       if (param1Consumer == null)
/* 1454 */         throw new NullPointerException(); 
/* 1455 */       Object[] arrayOfObject = this.map.table;
/* 1456 */       int i = getFence();
/* 1457 */       while (this.index < i) {
/* 1458 */         Object object = arrayOfObject[this.index];
/* 1459 */         this.index += 2;
/* 1460 */         if (object != null) {
/* 1461 */           param1Consumer.accept((K)IdentityHashMap.unmaskNull(object));
/* 1462 */           if (this.map.modCount != this.expectedModCount)
/* 1463 */             throw new ConcurrentModificationException(); 
/* 1464 */           return true;
/*      */         } 
/*      */       } 
/* 1467 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1471 */       return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends IdentityHashMapSpliterator<K, V>
/*      */     implements Spliterator<V>
/*      */   {
/*      */     ValueSpliterator(IdentityHashMap<K, V> param1IdentityHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1480 */       super(param1IdentityHashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public ValueSpliterator<K, V> trySplit() {
/* 1484 */       int i = getFence(), j = this.index, k = j + i >>> 1 & 0xFFFFFFFE;
/* 1485 */       return (j >= k) ? null : new ValueSpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super V> param1Consumer) {
/* 1491 */       if (param1Consumer == null)
/* 1492 */         throw new NullPointerException();  int i; int j;
/*      */       IdentityHashMap<K, V> identityHashMap;
/*      */       Object[] arrayOfObject;
/* 1495 */       if ((identityHashMap = this.map) != null && (arrayOfObject = identityHashMap.table) != null && (i = this.index) >= 0 && (this
/* 1496 */         .index = j = getFence()) <= arrayOfObject.length) {
/* 1497 */         for (; i < j; i += 2) {
/* 1498 */           if (arrayOfObject[i] != null) {
/* 1499 */             Object object = arrayOfObject[i + 1];
/* 1500 */             param1Consumer.accept((V)object);
/*      */           } 
/*      */         } 
/* 1503 */         if (identityHashMap.modCount == this.expectedModCount)
/*      */           return; 
/*      */       } 
/* 1506 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> param1Consumer) {
/* 1510 */       if (param1Consumer == null)
/* 1511 */         throw new NullPointerException(); 
/* 1512 */       Object[] arrayOfObject = this.map.table;
/* 1513 */       int i = getFence();
/* 1514 */       while (this.index < i) {
/* 1515 */         Object object1 = arrayOfObject[this.index];
/* 1516 */         Object object2 = arrayOfObject[this.index + 1];
/* 1517 */         this.index += 2;
/* 1518 */         if (object1 != null) {
/* 1519 */           param1Consumer.accept((V)object2);
/* 1520 */           if (this.map.modCount != this.expectedModCount)
/* 1521 */             throw new ConcurrentModificationException(); 
/* 1522 */           return true;
/*      */         } 
/*      */       } 
/* 1525 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1529 */       return (this.fence < 0 || this.est == this.map.size) ? 64 : 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends IdentityHashMapSpliterator<K, V>
/*      */     implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySpliterator(IdentityHashMap<K, V> param1IdentityHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1539 */       super(param1IdentityHashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public EntrySpliterator<K, V> trySplit() {
/* 1543 */       int i = getFence(), j = this.index, k = j + i >>> 1 & 0xFFFFFFFE;
/* 1544 */       return (j >= k) ? null : new EntrySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 1550 */       if (param1Consumer == null)
/* 1551 */         throw new NullPointerException();  int i; int j;
/*      */       IdentityHashMap<K, V> identityHashMap;
/*      */       Object[] arrayOfObject;
/* 1554 */       if ((identityHashMap = this.map) != null && (arrayOfObject = identityHashMap.table) != null && (i = this.index) >= 0 && (this
/* 1555 */         .index = j = getFence()) <= arrayOfObject.length) {
/* 1556 */         for (; i < j; i += 2) {
/* 1557 */           Object object = arrayOfObject[i];
/* 1558 */           if (object != null) {
/*      */             
/* 1560 */             Object object1 = IdentityHashMap.unmaskNull(object);
/* 1561 */             Object object2 = arrayOfObject[i + 1];
/* 1562 */             param1Consumer
/* 1563 */               .accept(new AbstractMap.SimpleImmutableEntry<>((K)object1, (V)object2));
/*      */           } 
/*      */         } 
/*      */         
/* 1567 */         if (identityHashMap.modCount == this.expectedModCount)
/*      */           return; 
/*      */       } 
/* 1570 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 1574 */       if (param1Consumer == null)
/* 1575 */         throw new NullPointerException(); 
/* 1576 */       Object[] arrayOfObject = this.map.table;
/* 1577 */       int i = getFence();
/* 1578 */       while (this.index < i) {
/* 1579 */         Object object1 = arrayOfObject[this.index];
/* 1580 */         Object object2 = arrayOfObject[this.index + 1];
/* 1581 */         this.index += 2;
/* 1582 */         if (object1 != null) {
/*      */           
/* 1584 */           Object object = IdentityHashMap.unmaskNull(object1);
/* 1585 */           param1Consumer
/* 1586 */             .accept(new AbstractMap.SimpleImmutableEntry<>((K)object, (V)object2));
/* 1587 */           if (this.map.modCount != this.expectedModCount)
/* 1588 */             throw new ConcurrentModificationException(); 
/* 1589 */           return true;
/*      */         } 
/*      */       } 
/* 1592 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1596 */       return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/IdentityHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */