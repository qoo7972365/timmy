/*      */ package java.util;
/*      */ 
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WeakHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements Map<K, V>
/*      */ {
/*      */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*      */   Entry<K, V>[] table;
/*      */   private int size;
/*      */   private int threshold;
/*      */   private final float loadFactor;
/*  180 */   private final ReferenceQueue<Object> queue = new ReferenceQueue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int modCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K, V>[] newTable(int paramInt) {
/*  195 */     return (Entry<K, V>[])new Entry[paramInt];
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
/*      */   public WeakHashMap(int paramInt, float paramFloat) {
/*  208 */     if (paramInt < 0) {
/*  209 */       throw new IllegalArgumentException("Illegal Initial Capacity: " + paramInt);
/*      */     }
/*  211 */     if (paramInt > 1073741824) {
/*  212 */       paramInt = 1073741824;
/*      */     }
/*  214 */     if (paramFloat <= 0.0F || Float.isNaN(paramFloat)) {
/*  215 */       throw new IllegalArgumentException("Illegal Load factor: " + paramFloat);
/*      */     }
/*  217 */     int i = 1;
/*  218 */     while (i < paramInt)
/*  219 */       i <<= 1; 
/*  220 */     this.table = newTable(i);
/*  221 */     this.loadFactor = paramFloat;
/*  222 */     this.threshold = (int)(i * paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WeakHashMap(int paramInt) {
/*  233 */     this(paramInt, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WeakHashMap() {
/*  241 */     this(16, 0.75F);
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
/*      */   public WeakHashMap(Map<? extends K, ? extends V> paramMap) {
/*  255 */     this(Math.max((int)(paramMap.size() / 0.75F) + 1, 16), 0.75F);
/*      */ 
/*      */     
/*  258 */     putAll(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  266 */   private static final Object NULL_KEY = new Object();
/*      */   
/*      */   private transient Set<Map.Entry<K, V>> entrySet;
/*      */ 
/*      */   
/*      */   private static Object maskNull(Object paramObject) {
/*  272 */     return (paramObject == null) ? NULL_KEY : paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object unmaskNull(Object paramObject) {
/*  279 */     return (paramObject == NULL_KEY) ? null : paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean eq(Object paramObject1, Object paramObject2) {
/*  287 */     return (paramObject1 == paramObject2 || paramObject1.equals(paramObject2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int hash(Object paramObject) {
/*  298 */     int i = paramObject.hashCode();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  303 */     i ^= i >>> 20 ^ i >>> 12;
/*  304 */     return i ^ i >>> 7 ^ i >>> 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int indexFor(int paramInt1, int paramInt2) {
/*  311 */     return paramInt1 & paramInt2 - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void expungeStaleEntries() {
/*      */     Reference<?> reference;
/*  318 */     while ((reference = this.queue.poll()) != null) {
/*  319 */       synchronized (this.queue) {
/*      */         
/*  321 */         Entry<K, V> entry1 = (Entry)reference;
/*  322 */         int i = indexFor(entry1.hash, this.table.length);
/*      */         
/*  324 */         Entry<K, V> entry2 = this.table[i];
/*  325 */         Entry<K, V> entry3 = entry2;
/*  326 */         while (entry3 != null) {
/*  327 */           Entry<K, V> entry = entry3.next;
/*  328 */           if (entry3 == entry1) {
/*  329 */             if (entry2 == entry1) {
/*  330 */               this.table[i] = entry;
/*      */             } else {
/*  332 */               entry2.next = entry;
/*      */             } 
/*      */             
/*  335 */             entry1.value = null;
/*  336 */             this.size--;
/*      */             break;
/*      */           } 
/*  339 */           entry2 = entry3;
/*  340 */           entry3 = entry;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K, V>[] getTable() {
/*  350 */     expungeStaleEntries();
/*  351 */     return this.table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  361 */     if (this.size == 0)
/*  362 */       return 0; 
/*  363 */     expungeStaleEntries();
/*  364 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  374 */     return (size() == 0);
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
/*      */   public V get(Object paramObject) {
/*  395 */     Object object = maskNull(paramObject);
/*  396 */     int i = hash(object);
/*  397 */     Entry[] arrayOfEntry = (Entry[])getTable();
/*  398 */     int j = indexFor(i, arrayOfEntry.length);
/*  399 */     Entry entry = arrayOfEntry[j];
/*  400 */     while (entry != null) {
/*  401 */       if (entry.hash == i && eq(object, entry.get()))
/*  402 */         return entry.value; 
/*  403 */       entry = entry.next;
/*      */     } 
/*  405 */     return null;
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
/*      */   public boolean containsKey(Object paramObject) {
/*  417 */     return (getEntry(paramObject) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Entry<K, V> getEntry(Object paramObject) {
/*  425 */     Object object = maskNull(paramObject);
/*  426 */     int i = hash(object);
/*  427 */     Entry[] arrayOfEntry = (Entry[])getTable();
/*  428 */     int j = indexFor(i, arrayOfEntry.length);
/*  429 */     Entry<K, V> entry = arrayOfEntry[j];
/*  430 */     while (entry != null && (entry.hash != i || !eq(object, entry.get())))
/*  431 */       entry = entry.next; 
/*  432 */     return entry;
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
/*      */   public V put(K paramK, V paramV) {
/*  448 */     Object object = maskNull(paramK);
/*  449 */     int i = hash(object);
/*  450 */     Entry[] arrayOfEntry = (Entry[])getTable();
/*  451 */     int j = indexFor(i, arrayOfEntry.length);
/*      */     Entry<?, V> entry;
/*  453 */     for (entry = arrayOfEntry[j]; entry != null; entry = entry.next) {
/*  454 */       if (i == entry.hash && eq(object, entry.get())) {
/*  455 */         V v = entry.value;
/*  456 */         if (paramV != v)
/*  457 */           entry.value = paramV; 
/*  458 */         return v;
/*      */       } 
/*      */     } 
/*      */     
/*  462 */     this.modCount++;
/*  463 */     entry = arrayOfEntry[j];
/*  464 */     arrayOfEntry[j] = new Entry<>(object, paramV, this.queue, i, entry);
/*  465 */     if (++this.size >= this.threshold)
/*  466 */       resize(arrayOfEntry.length * 2); 
/*  467 */     return null;
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
/*      */   void resize(int paramInt) {
/*  485 */     Entry[] arrayOfEntry1 = (Entry[])getTable();
/*  486 */     int i = arrayOfEntry1.length;
/*  487 */     if (i == 1073741824) {
/*  488 */       this.threshold = Integer.MAX_VALUE;
/*      */       
/*      */       return;
/*      */     } 
/*  492 */     Entry[] arrayOfEntry2 = (Entry[])newTable(paramInt);
/*  493 */     transfer((Entry<K, V>[])arrayOfEntry1, (Entry<K, V>[])arrayOfEntry2);
/*  494 */     this.table = (Entry<K, V>[])arrayOfEntry2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  501 */     if (this.size >= this.threshold / 2) {
/*  502 */       this.threshold = (int)(paramInt * this.loadFactor);
/*      */     } else {
/*  504 */       expungeStaleEntries();
/*  505 */       transfer((Entry<K, V>[])arrayOfEntry2, (Entry<K, V>[])arrayOfEntry1);
/*  506 */       this.table = (Entry<K, V>[])arrayOfEntry1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void transfer(Entry<K, V>[] paramArrayOfEntry1, Entry<K, V>[] paramArrayOfEntry2) {
/*  512 */     for (byte b = 0; b < paramArrayOfEntry1.length; b++) {
/*  513 */       Entry<K, V> entry = paramArrayOfEntry1[b];
/*  514 */       paramArrayOfEntry1[b] = null;
/*  515 */       while (entry != null) {
/*  516 */         Entry<K, V> entry1 = entry.next;
/*  517 */         Object object = entry.get();
/*  518 */         if (object == null) {
/*  519 */           entry.next = null;
/*  520 */           entry.value = null;
/*  521 */           this.size--;
/*      */         } else {
/*  523 */           int i = indexFor(entry.hash, paramArrayOfEntry2.length);
/*  524 */           entry.next = paramArrayOfEntry2[i];
/*  525 */           paramArrayOfEntry2[i] = entry;
/*      */         } 
/*  527 */         entry = entry1;
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
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap) {
/*  541 */     int i = paramMap.size();
/*  542 */     if (i == 0) {
/*      */       return;
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
/*  554 */     if (i > this.threshold) {
/*  555 */       int j = (int)(i / this.loadFactor + 1.0F);
/*  556 */       if (j > 1073741824)
/*  557 */         j = 1073741824; 
/*  558 */       int k = this.table.length;
/*  559 */       while (k < j)
/*  560 */         k <<= 1; 
/*  561 */       if (k > this.table.length) {
/*  562 */         resize(k);
/*      */       }
/*      */     } 
/*  565 */     for (Map.Entry<? extends K, ? extends V> entry : paramMap.entrySet()) {
/*  566 */       put((K)entry.getKey(), (V)entry.getValue());
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
/*      */   public V remove(Object paramObject) {
/*  590 */     Object object = maskNull(paramObject);
/*  591 */     int i = hash(object);
/*  592 */     Entry[] arrayOfEntry = (Entry[])getTable();
/*  593 */     int j = indexFor(i, arrayOfEntry.length);
/*  594 */     Entry entry1 = arrayOfEntry[j];
/*  595 */     Entry entry2 = entry1;
/*      */     
/*  597 */     while (entry2 != null) {
/*  598 */       Entry entry = entry2.next;
/*  599 */       if (i == entry2.hash && eq(object, entry2.get())) {
/*  600 */         this.modCount++;
/*  601 */         this.size--;
/*  602 */         if (entry1 == entry2) {
/*  603 */           arrayOfEntry[j] = entry;
/*      */         } else {
/*  605 */           entry1.next = entry;
/*  606 */         }  return entry2.value;
/*      */       } 
/*  608 */       entry1 = entry2;
/*  609 */       entry2 = entry;
/*      */     } 
/*      */     
/*  612 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean removeMapping(Object paramObject) {
/*  617 */     if (!(paramObject instanceof Map.Entry))
/*  618 */       return false; 
/*  619 */     Entry[] arrayOfEntry = (Entry[])getTable();
/*  620 */     Map.Entry entry = (Map.Entry)paramObject;
/*  621 */     Object object = maskNull(entry.getKey());
/*  622 */     int i = hash(object);
/*  623 */     int j = indexFor(i, arrayOfEntry.length);
/*  624 */     Entry entry1 = arrayOfEntry[j];
/*  625 */     Entry entry2 = entry1;
/*      */     
/*  627 */     while (entry2 != null) {
/*  628 */       Entry entry3 = entry2.next;
/*  629 */       if (i == entry2.hash && entry2.equals(entry)) {
/*  630 */         this.modCount++;
/*  631 */         this.size--;
/*  632 */         if (entry1 == entry2) {
/*  633 */           arrayOfEntry[j] = entry3;
/*      */         } else {
/*  635 */           entry1.next = entry3;
/*  636 */         }  return true;
/*      */       } 
/*  638 */       entry1 = entry2;
/*  639 */       entry2 = entry3;
/*      */     } 
/*      */     
/*  642 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  652 */     while (this.queue.poll() != null);
/*      */ 
/*      */     
/*  655 */     this.modCount++;
/*  656 */     Arrays.fill((Object[])this.table, (Object)null);
/*  657 */     this.size = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  662 */     while (this.queue.poll() != null);
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
/*  675 */     if (paramObject == null) {
/*  676 */       return containsNullValue();
/*      */     }
/*  678 */     Entry[] arrayOfEntry = (Entry[])getTable();
/*  679 */     for (int i = arrayOfEntry.length; i-- > 0;) {
/*  680 */       for (Entry entry = arrayOfEntry[i]; entry != null; entry = entry.next)
/*  681 */       { if (paramObject.equals(entry.value))
/*  682 */           return true;  } 
/*  683 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean containsNullValue() {
/*  690 */     Entry[] arrayOfEntry = (Entry[])getTable();
/*  691 */     for (int i = arrayOfEntry.length; i-- > 0;) {
/*  692 */       for (Entry entry = arrayOfEntry[i]; entry != null; entry = entry.next)
/*  693 */       { if (entry.value == null)
/*  694 */           return true;  } 
/*  695 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Entry<K, V>
/*      */     extends WeakReference<Object>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     V value;
/*      */ 
/*      */     
/*      */     final int hash;
/*      */     
/*      */     Entry<K, V> next;
/*      */ 
/*      */     
/*      */     Entry(Object param1Object, V param1V, ReferenceQueue<Object> param1ReferenceQueue, int param1Int, Entry<K, V> param1Entry) {
/*  713 */       super(param1Object, param1ReferenceQueue);
/*  714 */       this.value = param1V;
/*  715 */       this.hash = param1Int;
/*  716 */       this.next = param1Entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  721 */       return (K)WeakHashMap.unmaskNull(get());
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  725 */       return this.value;
/*      */     }
/*      */     
/*      */     public V setValue(V param1V) {
/*  729 */       V v = this.value;
/*  730 */       this.value = param1V;
/*  731 */       return v;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  735 */       if (!(param1Object instanceof Map.Entry))
/*  736 */         return false; 
/*  737 */       Map.Entry entry = (Map.Entry)param1Object;
/*  738 */       K k = getKey();
/*  739 */       Object object = entry.getKey();
/*  740 */       if (k == object || (k != null && k.equals(object))) {
/*  741 */         V v = getValue();
/*  742 */         Object object1 = entry.getValue();
/*  743 */         if (v == object1 || (v != null && v.equals(object1)))
/*  744 */           return true; 
/*      */       } 
/*  746 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  750 */       K k = getKey();
/*  751 */       V v = getValue();
/*  752 */       return Objects.hashCode(k) ^ Objects.hashCode(v);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  756 */       return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
/*      */     }
/*      */   }
/*      */   
/*      */   private abstract class HashIterator<T> implements Iterator<T> {
/*      */     private int index;
/*      */     private WeakHashMap.Entry<K, V> entry;
/*      */     private WeakHashMap.Entry<K, V> lastReturned;
/*  764 */     private int expectedModCount = WeakHashMap.this.modCount;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object nextKey;
/*      */ 
/*      */ 
/*      */     
/*      */     private Object currentKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     HashIterator() {
/*  779 */       this.index = WeakHashMap.this.isEmpty() ? 0 : WeakHashMap.this.table.length;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  783 */       WeakHashMap.Entry[] arrayOfEntry = WeakHashMap.this.table;
/*      */       
/*  785 */       while (this.nextKey == null) {
/*  786 */         WeakHashMap.Entry<K, V> entry = this.entry;
/*  787 */         int i = this.index;
/*  788 */         while (entry == null && i > 0)
/*  789 */           entry = arrayOfEntry[--i]; 
/*  790 */         this.entry = entry;
/*  791 */         this.index = i;
/*  792 */         if (entry == null) {
/*  793 */           this.currentKey = null;
/*  794 */           return false;
/*      */         } 
/*  796 */         this.nextKey = entry.get();
/*  797 */         if (this.nextKey == null)
/*  798 */           this.entry = this.entry.next; 
/*      */       } 
/*  800 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected WeakHashMap.Entry<K, V> nextEntry() {
/*  805 */       if (WeakHashMap.this.modCount != this.expectedModCount)
/*  806 */         throw new ConcurrentModificationException(); 
/*  807 */       if (this.nextKey == null && !hasNext()) {
/*  808 */         throw new NoSuchElementException();
/*      */       }
/*  810 */       this.lastReturned = this.entry;
/*  811 */       this.entry = this.entry.next;
/*  812 */       this.currentKey = this.nextKey;
/*  813 */       this.nextKey = null;
/*  814 */       return this.lastReturned;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  818 */       if (this.lastReturned == null)
/*  819 */         throw new IllegalStateException(); 
/*  820 */       if (WeakHashMap.this.modCount != this.expectedModCount) {
/*  821 */         throw new ConcurrentModificationException();
/*      */       }
/*  823 */       WeakHashMap.this.remove(this.currentKey);
/*  824 */       this.expectedModCount = WeakHashMap.this.modCount;
/*  825 */       this.lastReturned = null;
/*  826 */       this.currentKey = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private class ValueIterator extends HashIterator<V> { private ValueIterator() {}
/*      */     
/*      */     public V next() {
/*  833 */       return (nextEntry()).value;
/*      */     } }
/*      */   
/*      */   private class KeyIterator extends HashIterator<K> { private KeyIterator() {}
/*      */     
/*      */     public K next() {
/*  839 */       return (K)nextEntry().getKey();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends HashIterator<Map.Entry<K, V>> { private EntryIterator() {}
/*      */     
/*      */     public Map.Entry<K, V> next() {
/*  845 */       return nextEntry();
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
/*      */   public Set<K> keySet() {
/*  867 */     Set<K> set = this.keySet;
/*  868 */     if (set == null) {
/*  869 */       set = new KeySet();
/*  870 */       this.keySet = set;
/*      */     } 
/*  872 */     return set;
/*      */   }
/*      */   private class KeySet extends AbstractSet<K> { private KeySet() {}
/*      */     
/*      */     public Iterator<K> iterator() {
/*  877 */       return new WeakHashMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public int size() {
/*  881 */       return WeakHashMap.this.size();
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/*  885 */       return WeakHashMap.this.containsKey(param1Object);
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object) {
/*  889 */       if (WeakHashMap.this.containsKey(param1Object)) {
/*  890 */         WeakHashMap.this.remove(param1Object);
/*  891 */         return true;
/*      */       } 
/*      */       
/*  894 */       return false;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  898 */       WeakHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public Spliterator<K> spliterator() {
/*  902 */       return new WeakHashMap.KeySpliterator<>(WeakHashMap.this, 0, -1, 0, 0);
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
/*      */   public Collection<V> values() {
/*  920 */     Collection<V> collection = this.values;
/*  921 */     if (collection == null) {
/*  922 */       collection = new Values();
/*  923 */       this.values = collection;
/*      */     } 
/*  925 */     return collection;
/*      */   }
/*      */   private class Values extends AbstractCollection<V> { private Values() {}
/*      */     
/*      */     public Iterator<V> iterator() {
/*  930 */       return new WeakHashMap.ValueIterator();
/*      */     }
/*      */     
/*      */     public int size() {
/*  934 */       return WeakHashMap.this.size();
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/*  938 */       return WeakHashMap.this.containsValue(param1Object);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  942 */       WeakHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public Spliterator<V> spliterator() {
/*  946 */       return new WeakHashMap.ValueSpliterator<>(WeakHashMap.this, 0, -1, 0, 0);
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
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/*  965 */     Set<Map.Entry<K, V>> set = this.entrySet;
/*  966 */     return (set != null) ? set : (this.entrySet = new EntrySet());
/*      */   }
/*      */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/*  971 */       return new WeakHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/*  975 */       if (!(param1Object instanceof Map.Entry))
/*  976 */         return false; 
/*  977 */       Map.Entry entry = (Map.Entry)param1Object;
/*  978 */       WeakHashMap.Entry entry1 = WeakHashMap.this.getEntry(entry.getKey());
/*  979 */       return (entry1 != null && entry1.equals(entry));
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object) {
/*  983 */       return WeakHashMap.this.removeMapping(param1Object);
/*      */     }
/*      */     
/*      */     public int size() {
/*  987 */       return WeakHashMap.this.size();
/*      */     }
/*      */     
/*      */     public void clear() {
/*  991 */       WeakHashMap.this.clear();
/*      */     }
/*      */     
/*      */     private List<Map.Entry<K, V>> deepCopy() {
/*  995 */       ArrayList<Map.Entry<K, V>> arrayList = new ArrayList(size());
/*  996 */       for (Map.Entry<?, ?> entry : (Iterable<Map.Entry<?, ?>>)this)
/*  997 */         arrayList.add(new AbstractMap.SimpleEntry<>(entry)); 
/*  998 */       return arrayList;
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 1002 */       return deepCopy().toArray();
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 1006 */       return deepCopy().toArray(param1ArrayOfT);
/*      */     }
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> spliterator() {
/* 1010 */       return new WeakHashMap.EntrySpliterator<>(WeakHashMap.this, 0, -1, 0, 0);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 1017 */     Objects.requireNonNull(paramBiConsumer);
/* 1018 */     int i = this.modCount;
/*      */     
/* 1020 */     Entry[] arrayOfEntry = (Entry[])getTable();
/* 1021 */     for (Entry entry : arrayOfEntry) {
/* 1022 */       while (entry != null) {
/* 1023 */         Object object = entry.get();
/* 1024 */         if (object != null) {
/* 1025 */           paramBiConsumer.accept((K)unmaskNull(object), entry.value);
/*      */         }
/* 1027 */         entry = entry.next;
/*      */         
/* 1029 */         if (i != this.modCount) {
/* 1030 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1039 */     Objects.requireNonNull(paramBiFunction);
/* 1040 */     int i = this.modCount;
/*      */     
/* 1042 */     Entry[] arrayOfEntry = (Entry[])getTable();
/* 1043 */     for (Entry entry : arrayOfEntry) {
/* 1044 */       while (entry != null) {
/* 1045 */         Object object = entry.get();
/* 1046 */         if (object != null) {
/* 1047 */           entry.value = paramBiFunction.apply((K)unmaskNull(object), entry.value);
/*      */         }
/* 1049 */         entry = entry.next;
/*      */         
/* 1051 */         if (i != this.modCount) {
/* 1052 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static class WeakHashMapSpliterator<K, V>
/*      */   {
/*      */     final WeakHashMap<K, V> map;
/*      */     
/*      */     WeakHashMap.Entry<K, V> current;
/*      */     
/*      */     int index;
/*      */     
/*      */     int fence;
/*      */     
/*      */     int est;
/*      */     int expectedModCount;
/*      */     
/*      */     WeakHashMapSpliterator(WeakHashMap<K, V> param1WeakHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1073 */       this.map = param1WeakHashMap;
/* 1074 */       this.index = param1Int1;
/* 1075 */       this.fence = param1Int2;
/* 1076 */       this.est = param1Int3;
/* 1077 */       this.expectedModCount = param1Int4;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/* 1082 */       if ((i = this.fence) < 0) {
/* 1083 */         WeakHashMap<K, V> weakHashMap = this.map;
/* 1084 */         this.est = weakHashMap.size();
/* 1085 */         this.expectedModCount = weakHashMap.modCount;
/* 1086 */         i = this.fence = weakHashMap.table.length;
/*      */       } 
/* 1088 */       return i;
/*      */     }
/*      */     
/*      */     public final long estimateSize() {
/* 1092 */       getFence();
/* 1093 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends WeakHashMapSpliterator<K, V>
/*      */     implements Spliterator<K>
/*      */   {
/*      */     KeySpliterator(WeakHashMap<K, V> param1WeakHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1102 */       super(param1WeakHashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public KeySpliterator<K, V> trySplit() {
/* 1106 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1107 */       return (j >= k) ? null : new KeySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> param1Consumer) {
/*      */       int k;
/* 1114 */       if (param1Consumer == null)
/* 1115 */         throw new NullPointerException(); 
/* 1116 */       WeakHashMap<K, V> weakHashMap = this.map;
/* 1117 */       WeakHashMap.Entry<K, V>[] arrayOfEntry = weakHashMap.table; int j;
/* 1118 */       if ((j = this.fence) < 0) {
/* 1119 */         k = this.expectedModCount = weakHashMap.modCount;
/* 1120 */         j = this.fence = arrayOfEntry.length;
/*      */       } else {
/*      */         
/* 1123 */         k = this.expectedModCount;
/* 1124 */       }  int i; if (arrayOfEntry.length >= j && (i = this.index) >= 0 && (i < (this.index = j) || this.current != null)) {
/*      */         
/* 1126 */         WeakHashMap.Entry<K, V> entry = this.current;
/* 1127 */         this.current = null;
/*      */         do {
/* 1129 */           if (entry == null) {
/* 1130 */             entry = arrayOfEntry[i++];
/*      */           } else {
/* 1132 */             Object object = entry.get();
/* 1133 */             entry = entry.next;
/* 1134 */             if (object != null) {
/*      */               
/* 1136 */               Object object1 = WeakHashMap.unmaskNull(object);
/* 1137 */               param1Consumer.accept((K)object1);
/*      */             } 
/*      */           } 
/* 1140 */         } while (entry != null || i < j);
/*      */       } 
/* 1142 */       if (weakHashMap.modCount != k) {
/* 1143 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> param1Consumer) {
/* 1148 */       if (param1Consumer == null)
/* 1149 */         throw new NullPointerException(); 
/* 1150 */       WeakHashMap.Entry<K, V>[] arrayOfEntry = this.map.table; int i;
/* 1151 */       if (arrayOfEntry.length >= (i = getFence()) && this.index >= 0) {
/* 1152 */         while (this.current != null || this.index < i) {
/* 1153 */           if (this.current == null) {
/* 1154 */             this.current = arrayOfEntry[this.index++]; continue;
/*      */           } 
/* 1156 */           Object object = this.current.get();
/* 1157 */           this.current = this.current.next;
/* 1158 */           if (object != null) {
/*      */             
/* 1160 */             Object object1 = WeakHashMap.unmaskNull(object);
/* 1161 */             param1Consumer.accept((K)object1);
/* 1162 */             if (this.map.modCount != this.expectedModCount)
/* 1163 */               throw new ConcurrentModificationException(); 
/* 1164 */             return true;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1169 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1173 */       return 1;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends WeakHashMapSpliterator<K, V>
/*      */     implements Spliterator<V>
/*      */   {
/*      */     ValueSpliterator(WeakHashMap<K, V> param1WeakHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1182 */       super(param1WeakHashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public ValueSpliterator<K, V> trySplit() {
/* 1186 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1187 */       return (j >= k) ? null : new ValueSpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super V> param1Consumer) {
/*      */       int k;
/* 1194 */       if (param1Consumer == null)
/* 1195 */         throw new NullPointerException(); 
/* 1196 */       WeakHashMap<K, V> weakHashMap = this.map;
/* 1197 */       WeakHashMap.Entry<K, V>[] arrayOfEntry = weakHashMap.table; int j;
/* 1198 */       if ((j = this.fence) < 0) {
/* 1199 */         k = this.expectedModCount = weakHashMap.modCount;
/* 1200 */         j = this.fence = arrayOfEntry.length;
/*      */       } else {
/*      */         
/* 1203 */         k = this.expectedModCount;
/* 1204 */       }  int i; if (arrayOfEntry.length >= j && (i = this.index) >= 0 && (i < (this.index = j) || this.current != null)) {
/*      */         
/* 1206 */         WeakHashMap.Entry<K, V> entry = this.current;
/* 1207 */         this.current = null;
/*      */         do {
/* 1209 */           if (entry == null) {
/* 1210 */             entry = arrayOfEntry[i++];
/*      */           } else {
/* 1212 */             Object object = entry.get();
/* 1213 */             V v = entry.value;
/* 1214 */             entry = entry.next;
/* 1215 */             if (object != null)
/* 1216 */               param1Consumer.accept(v); 
/*      */           } 
/* 1218 */         } while (entry != null || i < j);
/*      */       } 
/* 1220 */       if (weakHashMap.modCount != k) {
/* 1221 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> param1Consumer) {
/* 1226 */       if (param1Consumer == null)
/* 1227 */         throw new NullPointerException(); 
/* 1228 */       WeakHashMap.Entry<K, V>[] arrayOfEntry = this.map.table; int i;
/* 1229 */       if (arrayOfEntry.length >= (i = getFence()) && this.index >= 0) {
/* 1230 */         while (this.current != null || this.index < i) {
/* 1231 */           if (this.current == null) {
/* 1232 */             this.current = arrayOfEntry[this.index++]; continue;
/*      */           } 
/* 1234 */           Object object = this.current.get();
/* 1235 */           V v = this.current.value;
/* 1236 */           this.current = this.current.next;
/* 1237 */           if (object != null) {
/* 1238 */             param1Consumer.accept(v);
/* 1239 */             if (this.map.modCount != this.expectedModCount)
/* 1240 */               throw new ConcurrentModificationException(); 
/* 1241 */             return true;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1246 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1250 */       return 0;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends WeakHashMapSpliterator<K, V>
/*      */     implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySpliterator(WeakHashMap<K, V> param1WeakHashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1259 */       super(param1WeakHashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public EntrySpliterator<K, V> trySplit() {
/* 1263 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1264 */       return (j >= k) ? null : new EntrySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/*      */       int k;
/* 1272 */       if (param1Consumer == null)
/* 1273 */         throw new NullPointerException(); 
/* 1274 */       WeakHashMap<K, V> weakHashMap = this.map;
/* 1275 */       WeakHashMap.Entry<K, V>[] arrayOfEntry = weakHashMap.table; int j;
/* 1276 */       if ((j = this.fence) < 0) {
/* 1277 */         k = this.expectedModCount = weakHashMap.modCount;
/* 1278 */         j = this.fence = arrayOfEntry.length;
/*      */       } else {
/*      */         
/* 1281 */         k = this.expectedModCount;
/* 1282 */       }  int i; if (arrayOfEntry.length >= j && (i = this.index) >= 0 && (i < (this.index = j) || this.current != null)) {
/*      */         
/* 1284 */         WeakHashMap.Entry<K, V> entry = this.current;
/* 1285 */         this.current = null;
/*      */         do {
/* 1287 */           if (entry == null) {
/* 1288 */             entry = arrayOfEntry[i++];
/*      */           } else {
/* 1290 */             Object object = entry.get();
/* 1291 */             V v = entry.value;
/* 1292 */             entry = entry.next;
/* 1293 */             if (object != null) {
/*      */               
/* 1295 */               Object object1 = WeakHashMap.unmaskNull(object);
/* 1296 */               param1Consumer
/* 1297 */                 .accept(new AbstractMap.SimpleImmutableEntry<>((K)object1, v));
/*      */             } 
/*      */           } 
/* 1300 */         } while (entry != null || i < j);
/*      */       } 
/* 1302 */       if (weakHashMap.modCount != k) {
/* 1303 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 1308 */       if (param1Consumer == null)
/* 1309 */         throw new NullPointerException(); 
/* 1310 */       WeakHashMap.Entry<K, V>[] arrayOfEntry = this.map.table; int i;
/* 1311 */       if (arrayOfEntry.length >= (i = getFence()) && this.index >= 0) {
/* 1312 */         while (this.current != null || this.index < i) {
/* 1313 */           if (this.current == null) {
/* 1314 */             this.current = arrayOfEntry[this.index++]; continue;
/*      */           } 
/* 1316 */           Object object = this.current.get();
/* 1317 */           V v = this.current.value;
/* 1318 */           this.current = this.current.next;
/* 1319 */           if (object != null) {
/*      */             
/* 1321 */             Object object1 = WeakHashMap.unmaskNull(object);
/* 1322 */             param1Consumer
/* 1323 */               .accept(new AbstractMap.SimpleImmutableEntry<>((K)object1, v));
/* 1324 */             if (this.map.modCount != this.expectedModCount)
/* 1325 */               throw new ConcurrentModificationException(); 
/* 1326 */             return true;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1331 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1335 */       return 1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/WeakHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */