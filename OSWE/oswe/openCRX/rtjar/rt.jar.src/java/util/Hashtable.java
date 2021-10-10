/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
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
/*      */ public class Hashtable<K, V>
/*      */   extends Dictionary<K, V>
/*      */   implements Map<K, V>, Cloneable, Serializable
/*      */ {
/*      */   private transient Entry<?, ?>[] table;
/*      */   private transient int count;
/*      */   private int threshold;
/*      */   private float loadFactor;
/*  167 */   private transient int modCount = 0;
/*      */   
/*      */   private static final long serialVersionUID = 1421746759512286392L;
/*      */   
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   
/*      */   private volatile transient Set<K> keySet;
/*      */   
/*      */   private volatile transient Set<Map.Entry<K, V>> entrySet;
/*      */   private volatile transient Collection<V> values;
/*      */   private static final int KEYS = 0;
/*      */   private static final int VALUES = 1;
/*      */   private static final int ENTRIES = 2;
/*      */   
/*      */   public Hashtable(int paramInt, float paramFloat) {
/*  182 */     if (paramInt < 0) {
/*  183 */       throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
/*      */     }
/*  185 */     if (paramFloat <= 0.0F || Float.isNaN(paramFloat)) {
/*  186 */       throw new IllegalArgumentException("Illegal Load: " + paramFloat);
/*      */     }
/*  188 */     if (paramInt == 0)
/*  189 */       paramInt = 1; 
/*  190 */     this.loadFactor = paramFloat;
/*  191 */     this.table = (Entry<?, ?>[])new Entry[paramInt];
/*  192 */     this.threshold = (int)Math.min(paramInt * paramFloat, 2.14748365E9F);
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
/*      */   public Hashtable(int paramInt) {
/*  204 */     this(paramInt, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hashtable() {
/*  212 */     this(11, 0.75F);
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
/*      */   public Hashtable(Map<? extends K, ? extends V> paramMap) {
/*  225 */     this(Math.max(2 * paramMap.size(), 11), 0.75F);
/*  226 */     putAll(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int size() {
/*  235 */     return this.count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean isEmpty() {
/*  245 */     return (this.count == 0);
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
/*      */   public synchronized Enumeration<K> keys() {
/*  258 */     return getEnumeration(0);
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
/*      */   public synchronized Enumeration<V> elements() {
/*  273 */     return getEnumeration(1);
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
/*      */   public synchronized boolean contains(Object paramObject) {
/*  293 */     if (paramObject == null) {
/*  294 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  297 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  298 */     for (int i = arrayOfEntry.length; i-- > 0;) {
/*  299 */       for (Entry<?, ?> entry = arrayOfEntry[i]; entry != null; entry = entry.next) {
/*  300 */         if (entry.value.equals(paramObject)) {
/*  301 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  305 */     return false;
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
/*      */   public boolean containsValue(Object paramObject) {
/*  321 */     return contains(paramObject);
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
/*      */   public synchronized boolean containsKey(Object paramObject) {
/*  335 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  336 */     int i = paramObject.hashCode();
/*  337 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*  338 */     for (Entry<?, ?> entry = arrayOfEntry[j]; entry != null; entry = entry.next) {
/*  339 */       if (entry.hash == i && entry.key.equals(paramObject)) {
/*  340 */         return true;
/*      */       }
/*      */     } 
/*  343 */     return false;
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
/*      */   public synchronized V get(Object paramObject) {
/*  363 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  364 */     int i = paramObject.hashCode();
/*  365 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*  366 */     for (Entry<?, ?> entry = arrayOfEntry[j]; entry != null; entry = entry.next) {
/*  367 */       if (entry.hash == i && entry.key.equals(paramObject)) {
/*  368 */         return entry.value;
/*      */       }
/*      */     } 
/*  371 */     return null;
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
/*      */   protected void rehash() {
/*  391 */     int i = this.table.length;
/*  392 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*      */ 
/*      */     
/*  395 */     int j = (i << 1) + 1;
/*  396 */     if (j - 2147483639 > 0) {
/*  397 */       if (i == 2147483639) {
/*      */         return;
/*      */       }
/*  400 */       j = 2147483639;
/*      */     } 
/*  402 */     Entry[] arrayOfEntry1 = new Entry[j];
/*      */     
/*  404 */     this.modCount++;
/*  405 */     this.threshold = (int)Math.min(j * this.loadFactor, 2.14748365E9F);
/*  406 */     this.table = (Entry<?, ?>[])arrayOfEntry1;
/*      */     
/*  408 */     for (int k = i; k-- > 0;) {
/*  409 */       for (Entry<?, ?> entry = arrayOfEntry[k]; entry != null; ) {
/*  410 */         Entry<?, ?> entry1 = entry;
/*  411 */         entry = entry.next;
/*      */         
/*  413 */         int m = (entry1.hash & Integer.MAX_VALUE) % j;
/*  414 */         entry1.next = arrayOfEntry1[m];
/*  415 */         arrayOfEntry1[m] = entry1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addEntry(int paramInt1, K paramK, V paramV, int paramInt2) {
/*  421 */     this.modCount++;
/*      */     
/*  423 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  424 */     if (this.count >= this.threshold) {
/*      */       
/*  426 */       rehash();
/*      */       
/*  428 */       arrayOfEntry = this.table;
/*  429 */       paramInt1 = paramK.hashCode();
/*  430 */       paramInt2 = (paramInt1 & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  435 */     Entry<?, ?> entry = arrayOfEntry[paramInt2];
/*  436 */     arrayOfEntry[paramInt2] = new Entry<>(paramInt1, paramK, paramV, entry);
/*  437 */     this.count++;
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
/*      */   public synchronized V put(K paramK, V paramV) {
/*  459 */     if (paramV == null) {
/*  460 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */     
/*  464 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  465 */     int i = paramK.hashCode();
/*  466 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/*  468 */     Entry<?, ?> entry = arrayOfEntry[j];
/*  469 */     for (; entry != null; entry = entry.next) {
/*  470 */       if (entry.hash == i && entry.key.equals(paramK)) {
/*  471 */         V v = entry.value;
/*  472 */         entry.value = paramV;
/*  473 */         return v;
/*      */       } 
/*      */     } 
/*      */     
/*  477 */     addEntry(i, paramK, paramV, j);
/*  478 */     return null;
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
/*      */   public synchronized V remove(Object paramObject) {
/*  491 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  492 */     int i = paramObject.hashCode();
/*  493 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/*  495 */     Entry<?, ?> entry1 = arrayOfEntry[j];
/*  496 */     for (Entry<?, ?> entry2 = null; entry1 != null; entry2 = entry1, entry1 = entry1.next) {
/*  497 */       if (entry1.hash == i && entry1.key.equals(paramObject)) {
/*  498 */         this.modCount++;
/*  499 */         if (entry2 != null) {
/*  500 */           entry2.next = (Entry)entry1.next;
/*      */         } else {
/*  502 */           arrayOfEntry[j] = entry1.next;
/*      */         } 
/*  504 */         this.count--;
/*  505 */         V v = entry1.value;
/*  506 */         entry1.value = null;
/*  507 */         return v;
/*      */       } 
/*      */     } 
/*  510 */     return null;
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
/*      */   public synchronized void putAll(Map<? extends K, ? extends V> paramMap) {
/*  523 */     for (Map.Entry<? extends K, ? extends V> entry : paramMap.entrySet()) {
/*  524 */       put((K)entry.getKey(), (V)entry.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void clear() {
/*  531 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  532 */     this.modCount++;
/*  533 */     for (int i = arrayOfEntry.length; --i >= 0;)
/*  534 */       arrayOfEntry[i] = null; 
/*  535 */     this.count = 0;
/*      */   }
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
/*  547 */       Hashtable hashtable = (Hashtable)super.clone();
/*  548 */       hashtable.table = (Entry<?, ?>[])new Entry[this.table.length];
/*  549 */       for (int i = this.table.length; i-- > 0;) {
/*  550 */         hashtable.table[i] = (this.table[i] != null) ? (Entry<?, ?>)this.table[i]
/*  551 */           .clone() : null;
/*      */       }
/*  553 */       hashtable.keySet = null;
/*  554 */       hashtable.entrySet = null;
/*  555 */       hashtable.values = null;
/*  556 */       hashtable.modCount = 0;
/*  557 */       return hashtable;
/*  558 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/*  560 */       throw new InternalError(cloneNotSupportedException);
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
/*      */   public synchronized String toString() {
/*  575 */     int i = size() - 1;
/*  576 */     if (i == -1) {
/*  577 */       return "{}";
/*      */     }
/*  579 */     StringBuilder stringBuilder = new StringBuilder();
/*  580 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/*      */     
/*  582 */     stringBuilder.append('{');
/*  583 */     for (int j = 0;; j++) {
/*  584 */       Map.Entry entry = iterator.next();
/*  585 */       Object object1 = entry.getKey();
/*  586 */       Object object2 = entry.getValue();
/*  587 */       stringBuilder.append((object1 == this) ? "(this Map)" : object1.toString());
/*  588 */       stringBuilder.append('=');
/*  589 */       stringBuilder.append((object2 == this) ? "(this Map)" : object2.toString());
/*      */       
/*  591 */       if (j == i)
/*  592 */         return stringBuilder.append('}').toString(); 
/*  593 */       stringBuilder.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> Enumeration<T> getEnumeration(int paramInt) {
/*  599 */     if (this.count == 0) {
/*  600 */       return Collections.emptyEnumeration();
/*      */     }
/*  602 */     return new Enumerator<>(paramInt, false);
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> Iterator<T> getIterator(int paramInt) {
/*  607 */     if (this.count == 0) {
/*  608 */       return Collections.emptyIterator();
/*      */     }
/*  610 */     return new Enumerator<>(paramInt, true);
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
/*      */   public Set<K> keySet() {
/*  641 */     if (this.keySet == null)
/*  642 */       this.keySet = Collections.synchronizedSet(new KeySet(), this); 
/*  643 */     return this.keySet;
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractSet<K> {
/*      */     public Iterator<K> iterator() {
/*  648 */       return Hashtable.this.getIterator(0);
/*      */     } private KeySet() {}
/*      */     public int size() {
/*  651 */       return Hashtable.this.count;
/*      */     }
/*      */     public boolean contains(Object param1Object) {
/*  654 */       return Hashtable.this.containsKey(param1Object);
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/*  657 */       return (Hashtable.this.remove(param1Object) != null);
/*      */     }
/*      */     public void clear() {
/*  660 */       Hashtable.this.clear();
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
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/*  681 */     if (this.entrySet == null)
/*  682 */       this.entrySet = Collections.synchronizedSet(new EntrySet(), this); 
/*  683 */     return this.entrySet;
/*      */   }
/*      */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/*  688 */       return (Iterator)Hashtable.this.getIterator(2);
/*      */     }
/*      */     
/*      */     public boolean add(Map.Entry<K, V> param1Entry) {
/*  692 */       return super.add(param1Entry);
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/*  696 */       if (!(param1Object instanceof Map.Entry))
/*  697 */         return false; 
/*  698 */       Map.Entry entry = (Map.Entry)param1Object;
/*  699 */       Object object = entry.getKey();
/*  700 */       Hashtable.Entry[] arrayOfEntry = (Hashtable.Entry[])Hashtable.this.table;
/*  701 */       int i = object.hashCode();
/*  702 */       int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */       
/*  704 */       for (Hashtable.Entry entry1 = arrayOfEntry[j]; entry1 != null; entry1 = entry1.next) {
/*  705 */         if (entry1.hash == i && entry1.equals(entry))
/*  706 */           return true; 
/*  707 */       }  return false;
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object) {
/*  711 */       if (!(param1Object instanceof Map.Entry))
/*  712 */         return false; 
/*  713 */       Map.Entry entry = (Map.Entry)param1Object;
/*  714 */       Object object = entry.getKey();
/*  715 */       Hashtable.Entry[] arrayOfEntry = (Hashtable.Entry[])Hashtable.this.table;
/*  716 */       int i = object.hashCode();
/*  717 */       int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */ 
/*      */       
/*  720 */       Hashtable.Entry entry1 = arrayOfEntry[j];
/*  721 */       for (Hashtable.Entry entry2 = null; entry1 != null; entry2 = entry1, entry1 = entry1.next) {
/*  722 */         if (entry1.hash == i && entry1.equals(entry)) {
/*  723 */           Hashtable.this.modCount++;
/*  724 */           if (entry2 != null) {
/*  725 */             entry2.next = entry1.next;
/*      */           } else {
/*  727 */             arrayOfEntry[j] = entry1.next;
/*      */           } 
/*  729 */           Hashtable.this.count--;
/*  730 */           entry1.value = null;
/*  731 */           return true;
/*      */         } 
/*      */       } 
/*  734 */       return false;
/*      */     }
/*      */     
/*      */     public int size() {
/*  738 */       return Hashtable.this.count;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  742 */       Hashtable.this.clear();
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
/*      */   public Collection<V> values() {
/*  762 */     if (this.values == null) {
/*  763 */       this.values = Collections.synchronizedCollection(new ValueCollection(), this);
/*      */     }
/*  765 */     return this.values;
/*      */   }
/*      */   private class ValueCollection extends AbstractCollection<V> { private ValueCollection() {}
/*      */     
/*      */     public Iterator<V> iterator() {
/*  770 */       return Hashtable.this.getIterator(1);
/*      */     }
/*      */     public int size() {
/*  773 */       return Hashtable.this.count;
/*      */     }
/*      */     public boolean contains(Object param1Object) {
/*  776 */       return Hashtable.this.containsValue(param1Object);
/*      */     }
/*      */     public void clear() {
/*  779 */       Hashtable.this.clear();
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
/*      */   public synchronized boolean equals(Object paramObject) {
/*  795 */     if (paramObject == this) {
/*  796 */       return true;
/*      */     }
/*  798 */     if (!(paramObject instanceof Map))
/*  799 */       return false; 
/*  800 */     Map map = (Map)paramObject;
/*  801 */     if (map.size() != size()) {
/*  802 */       return false;
/*      */     }
/*      */     try {
/*  805 */       Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/*  806 */       while (iterator.hasNext()) {
/*  807 */         Map.Entry entry = iterator.next();
/*  808 */         Object object1 = entry.getKey();
/*  809 */         Object object2 = entry.getValue();
/*  810 */         if (object2 == null) {
/*  811 */           if (map.get(object1) != null || !map.containsKey(object1))
/*  812 */             return false;  continue;
/*      */         } 
/*  814 */         if (!object2.equals(map.get(object1))) {
/*  815 */           return false;
/*      */         }
/*      */       } 
/*  818 */     } catch (ClassCastException classCastException) {
/*  819 */       return false;
/*  820 */     } catch (NullPointerException nullPointerException) {
/*  821 */       return false;
/*      */     } 
/*      */     
/*  824 */     return true;
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
/*      */   public synchronized int hashCode() {
/*  845 */     int i = 0;
/*  846 */     if (this.count == 0 || this.loadFactor < 0.0F) {
/*  847 */       return i;
/*      */     }
/*  849 */     this.loadFactor = -this.loadFactor;
/*  850 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  851 */     for (Entry<?, ?> entry : arrayOfEntry) {
/*  852 */       while (entry != null) {
/*  853 */         i += entry.hashCode();
/*  854 */         entry = entry.next;
/*      */       } 
/*      */     } 
/*      */     
/*  858 */     this.loadFactor = -this.loadFactor;
/*      */     
/*  860 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized V getOrDefault(Object paramObject, V paramV) {
/*  865 */     V v = get(paramObject);
/*  866 */     return (null == v) ? paramV : v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/*  872 */     Objects.requireNonNull(paramBiConsumer);
/*      */     
/*  874 */     int i = this.modCount;
/*      */     
/*  876 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  877 */     for (Entry<?, ?> entry : arrayOfEntry) {
/*  878 */       while (entry != null) {
/*  879 */         paramBiConsumer.accept(entry.key, entry.value);
/*  880 */         entry = entry.next;
/*      */         
/*  882 */         if (i != this.modCount) {
/*  883 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/*  892 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/*  894 */     int i = this.modCount;
/*      */     
/*  896 */     Entry[] arrayOfEntry = (Entry[])this.table;
/*  897 */     for (Entry entry : arrayOfEntry) {
/*  898 */       while (entry != null) {
/*  899 */         entry.value = Objects.requireNonNull(paramBiFunction
/*  900 */             .apply(entry.key, entry.value));
/*  901 */         entry = entry.next;
/*      */         
/*  903 */         if (i != this.modCount) {
/*  904 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized V putIfAbsent(K paramK, V paramV) {
/*  912 */     Objects.requireNonNull(paramV);
/*      */ 
/*      */     
/*  915 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  916 */     int i = paramK.hashCode();
/*  917 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/*  919 */     Entry<?, ?> entry = arrayOfEntry[j];
/*  920 */     for (; entry != null; entry = entry.next) {
/*  921 */       if (entry.hash == i && entry.key.equals(paramK)) {
/*  922 */         V v = entry.value;
/*  923 */         if (v == null) {
/*  924 */           entry.value = paramV;
/*      */         }
/*  926 */         return v;
/*      */       } 
/*      */     } 
/*      */     
/*  930 */     addEntry(i, paramK, paramV, j);
/*  931 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean remove(Object paramObject1, Object paramObject2) {
/*  936 */     Objects.requireNonNull(paramObject2);
/*      */     
/*  938 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  939 */     int i = paramObject1.hashCode();
/*  940 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/*  942 */     Entry<?, ?> entry1 = arrayOfEntry[j];
/*  943 */     for (Entry<?, ?> entry2 = null; entry1 != null; entry2 = entry1, entry1 = entry1.next) {
/*  944 */       if (entry1.hash == i && entry1.key.equals(paramObject1) && entry1.value.equals(paramObject2)) {
/*  945 */         this.modCount++;
/*  946 */         if (entry2 != null) {
/*  947 */           entry2.next = (Entry)entry1.next;
/*      */         } else {
/*  949 */           arrayOfEntry[j] = entry1.next;
/*      */         } 
/*  951 */         this.count--;
/*  952 */         entry1.value = null;
/*  953 */         return true;
/*      */       } 
/*      */     } 
/*  956 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean replace(K paramK, V paramV1, V paramV2) {
/*  961 */     Objects.requireNonNull(paramV1);
/*  962 */     Objects.requireNonNull(paramV2);
/*  963 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  964 */     int i = paramK.hashCode();
/*  965 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/*  967 */     Entry<?, ?> entry = arrayOfEntry[j];
/*  968 */     for (; entry != null; entry = entry.next) {
/*  969 */       if (entry.hash == i && entry.key.equals(paramK)) {
/*  970 */         if (entry.value.equals(paramV1)) {
/*  971 */           entry.value = paramV2;
/*  972 */           return true;
/*      */         } 
/*  974 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/*  978 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized V replace(K paramK, V paramV) {
/*  983 */     Objects.requireNonNull(paramV);
/*  984 */     Entry<?, ?>[] arrayOfEntry = this.table;
/*  985 */     int i = paramK.hashCode();
/*  986 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/*  988 */     Entry<?, ?> entry = arrayOfEntry[j];
/*  989 */     for (; entry != null; entry = entry.next) {
/*  990 */       if (entry.hash == i && entry.key.equals(paramK)) {
/*  991 */         V v = entry.value;
/*  992 */         entry.value = paramV;
/*  993 */         return v;
/*      */       } 
/*      */     } 
/*  996 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction) {
/* 1001 */     Objects.requireNonNull(paramFunction);
/*      */     
/* 1003 */     Entry<?, ?>[] arrayOfEntry = this.table;
/* 1004 */     int i = paramK.hashCode();
/* 1005 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/* 1007 */     Entry<?, ?> entry = arrayOfEntry[j];
/* 1008 */     for (; entry != null; entry = entry.next) {
/* 1009 */       if (entry.hash == i && entry.key.equals(paramK))
/*      */       {
/* 1011 */         return entry.value;
/*      */       }
/*      */     } 
/*      */     
/* 1015 */     V v = paramFunction.apply(paramK);
/* 1016 */     if (v != null) {
/* 1017 */       addEntry(i, paramK, v, j);
/*      */     }
/*      */     
/* 1020 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1025 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/* 1027 */     Entry<?, ?>[] arrayOfEntry = this.table;
/* 1028 */     int i = paramK.hashCode();
/* 1029 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/* 1031 */     Entry<?, ?> entry1 = arrayOfEntry[j];
/* 1032 */     for (Entry<?, ?> entry2 = null; entry1 != null; entry2 = entry1, entry1 = entry1.next) {
/* 1033 */       if (entry1.hash == i && entry1.key.equals(paramK)) {
/* 1034 */         V v = paramBiFunction.apply(paramK, entry1.value);
/* 1035 */         if (v == null) {
/* 1036 */           this.modCount++;
/* 1037 */           if (entry2 != null) {
/* 1038 */             entry2.next = (Entry)entry1.next;
/*      */           } else {
/* 1040 */             arrayOfEntry[j] = entry1.next;
/*      */           } 
/* 1042 */           this.count--;
/*      */         } else {
/* 1044 */           entry1.value = v;
/*      */         } 
/* 1046 */         return v;
/*      */       } 
/*      */     } 
/* 1049 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1054 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/* 1056 */     Entry<?, ?>[] arrayOfEntry = this.table;
/* 1057 */     int i = paramK.hashCode();
/* 1058 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/* 1060 */     Entry<?, ?> entry1 = arrayOfEntry[j]; Entry<?, ?> entry2;
/* 1061 */     for (entry2 = null; entry1 != null; entry2 = entry1, entry1 = entry1.next) {
/* 1062 */       if (entry1.hash == i && Objects.equals(entry1.key, paramK)) {
/* 1063 */         V v = paramBiFunction.apply(paramK, entry1.value);
/* 1064 */         if (v == null) {
/* 1065 */           this.modCount++;
/* 1066 */           if (entry2 != null) {
/* 1067 */             entry2.next = (Entry)entry1.next;
/*      */           } else {
/* 1069 */             arrayOfEntry[j] = entry1.next;
/*      */           } 
/* 1071 */           this.count--;
/*      */         } else {
/* 1073 */           entry1.value = v;
/*      */         } 
/* 1075 */         return v;
/*      */       } 
/*      */     } 
/*      */     
/* 1079 */     entry2 = (Entry<?, ?>)paramBiFunction.apply(paramK, null);
/* 1080 */     if (entry2 != null) {
/* 1081 */       addEntry(i, paramK, (V)entry2, j);
/*      */     }
/*      */     
/* 1084 */     return (V)entry2;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction) {
/* 1089 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/* 1091 */     Entry<?, ?>[] arrayOfEntry = this.table;
/* 1092 */     int i = paramK.hashCode();
/* 1093 */     int j = (i & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */     
/* 1095 */     Entry<?, ?> entry1 = arrayOfEntry[j];
/* 1096 */     for (Entry<?, ?> entry2 = null; entry1 != null; entry2 = entry1, entry1 = entry1.next) {
/* 1097 */       if (entry1.hash == i && entry1.key.equals(paramK)) {
/* 1098 */         V v = paramBiFunction.apply(entry1.value, paramV);
/* 1099 */         if (v == null) {
/* 1100 */           this.modCount++;
/* 1101 */           if (entry2 != null) {
/* 1102 */             entry2.next = (Entry)entry1.next;
/*      */           } else {
/* 1104 */             arrayOfEntry[j] = entry1.next;
/*      */           } 
/* 1106 */           this.count--;
/*      */         } else {
/* 1108 */           entry1.value = v;
/*      */         } 
/* 1110 */         return v;
/*      */       } 
/*      */     } 
/*      */     
/* 1114 */     if (paramV != null) {
/* 1115 */       addEntry(i, paramK, paramV, j);
/*      */     }
/*      */     
/* 1118 */     return paramV;
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
/* 1133 */     Entry<K, V> entry = null;
/*      */     
/* 1135 */     synchronized (this) {
/*      */       
/* 1137 */       paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */       
/* 1140 */       paramObjectOutputStream.writeInt(this.table.length);
/* 1141 */       paramObjectOutputStream.writeInt(this.count);
/*      */ 
/*      */       
/* 1144 */       for (byte b = 0; b < this.table.length; b++) {
/* 1145 */         Entry<?, ?> entry1 = this.table[b];
/*      */         
/* 1147 */         while (entry1 != null) {
/* 1148 */           entry = new Entry<>(0, entry1.key, entry1.value, entry);
/*      */           
/* 1150 */           entry1 = entry1.next;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1156 */     while (entry != null) {
/* 1157 */       paramObjectOutputStream.writeObject(entry.key);
/* 1158 */       paramObjectOutputStream.writeObject(entry.value);
/* 1159 */       entry = entry.next;
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
/* 1170 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/* 1173 */     if (this.loadFactor <= 0.0F || Float.isNaN(this.loadFactor)) {
/* 1174 */       throw new StreamCorruptedException("Illegal Load: " + this.loadFactor);
/*      */     }
/*      */     
/* 1177 */     int i = paramObjectInputStream.readInt();
/* 1178 */     int j = paramObjectInputStream.readInt();
/*      */ 
/*      */     
/* 1181 */     if (j < 0) {
/* 1182 */       throw new StreamCorruptedException("Illegal # of Elements: " + j);
/*      */     }
/*      */ 
/*      */     
/* 1186 */     i = Math.max(i, (int)(j / this.loadFactor) + 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1192 */     int k = (int)((j + j / 20) / this.loadFactor) + 3;
/* 1193 */     if (k > j && (k & 0x1) == 0)
/* 1194 */       k--; 
/* 1195 */     k = Math.min(k, i);
/*      */     
/* 1197 */     if (k < 0) {
/* 1198 */       k = i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1203 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Map.Entry[].class, k);
/* 1204 */     this.table = (Entry<?, ?>[])new Entry[k];
/* 1205 */     this.threshold = (int)Math.min(k * this.loadFactor, 2.14748365E9F);
/* 1206 */     this.count = 0;
/*      */ 
/*      */     
/* 1209 */     for (; j > 0; j--) {
/*      */       
/* 1211 */       Object object1 = paramObjectInputStream.readObject();
/*      */       
/* 1213 */       Object object2 = paramObjectInputStream.readObject();
/*      */       
/* 1215 */       reconstitutionPut(this.table, (K)object1, (V)object2);
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
/*      */   private void reconstitutionPut(Entry<?, ?>[] paramArrayOfEntry, K paramK, V paramV) throws StreamCorruptedException {
/* 1233 */     if (paramV == null) {
/* 1234 */       throw new StreamCorruptedException();
/*      */     }
/*      */ 
/*      */     
/* 1238 */     int i = paramK.hashCode();
/* 1239 */     int j = (i & Integer.MAX_VALUE) % paramArrayOfEntry.length; Entry<?, ?> entry;
/* 1240 */     for (entry = paramArrayOfEntry[j]; entry != null; entry = entry.next) {
/* 1241 */       if (entry.hash == i && entry.key.equals(paramK)) {
/* 1242 */         throw new StreamCorruptedException();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1247 */     entry = paramArrayOfEntry[j];
/* 1248 */     paramArrayOfEntry[j] = new Entry<>(i, paramK, paramV, entry);
/* 1249 */     this.count++;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Entry<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final K key;
/*      */     V value;
/*      */     Entry<K, V> next;
/*      */     
/*      */     protected Entry(int param1Int, K param1K, V param1V, Entry<K, V> param1Entry) {
/* 1262 */       this.hash = param1Int;
/* 1263 */       this.key = param1K;
/* 1264 */       this.value = param1V;
/* 1265 */       this.next = param1Entry;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Object clone() {
/* 1270 */       return new Entry(this.hash, this.key, this.value, (this.next == null) ? null : (Entry<K, V>)this.next
/* 1271 */           .clone());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public K getKey() {
/* 1277 */       return this.key;
/*      */     }
/*      */     
/*      */     public V getValue() {
/* 1281 */       return this.value;
/*      */     }
/*      */     
/*      */     public V setValue(V param1V) {
/* 1285 */       if (param1V == null) {
/* 1286 */         throw new NullPointerException();
/*      */       }
/* 1288 */       V v = this.value;
/* 1289 */       this.value = param1V;
/* 1290 */       return v;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1294 */       if (!(param1Object instanceof Map.Entry))
/* 1295 */         return false; 
/* 1296 */       Map.Entry entry = (Map.Entry)param1Object;
/*      */       
/* 1298 */       if ((this.key == null) ? (entry.getKey() == null) : this.key.equals(entry.getKey())) if ((this.value == null) ? (entry
/* 1299 */           .getValue() == null) : this.value.equals(entry.getValue())); 
/*      */       return false;
/*      */     }
/*      */     public int hashCode() {
/* 1303 */       return this.hash ^ Objects.hashCode(this.value);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1307 */       return this.key.toString() + "=" + this.value.toString();
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
/*      */   private class Enumerator<T>
/*      */     implements Enumeration<T>, Iterator<T>
/*      */   {
/* 1324 */     Hashtable.Entry<?, ?>[] table = Hashtable.this.table;
/* 1325 */     int index = this.table.length;
/*      */ 
/*      */ 
/*      */     
/*      */     Hashtable.Entry<?, ?> entry;
/*      */ 
/*      */     
/*      */     Hashtable.Entry<?, ?> lastReturned;
/*      */ 
/*      */     
/*      */     int type;
/*      */ 
/*      */     
/*      */     boolean iterator;
/*      */ 
/*      */     
/* 1341 */     protected int expectedModCount = Hashtable.this.modCount;
/*      */     
/*      */     Enumerator(int param1Int, boolean param1Boolean) {
/* 1344 */       this.type = param1Int;
/* 1345 */       this.iterator = param1Boolean;
/*      */     }
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1349 */       Hashtable.Entry<?, ?> entry = this.entry;
/* 1350 */       int i = this.index;
/* 1351 */       Hashtable.Entry<?, ?>[] arrayOfEntry = this.table;
/*      */       
/* 1353 */       while (entry == null && i > 0) {
/* 1354 */         entry = arrayOfEntry[--i];
/*      */       }
/* 1356 */       this.entry = entry;
/* 1357 */       this.index = i;
/* 1358 */       return (entry != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public T nextElement() {
/* 1363 */       Hashtable.Entry<?, ?> entry = this.entry;
/* 1364 */       int i = this.index;
/* 1365 */       Hashtable.Entry<?, ?>[] arrayOfEntry = this.table;
/*      */       
/* 1367 */       while (entry == null && i > 0) {
/* 1368 */         entry = arrayOfEntry[--i];
/*      */       }
/* 1370 */       this.entry = entry;
/* 1371 */       this.index = i;
/* 1372 */       if (entry != null) {
/* 1373 */         Hashtable.Entry<?, ?> entry1 = this.lastReturned = this.entry;
/* 1374 */         this.entry = entry1.next;
/* 1375 */         return (this.type == 0) ? (T)entry1.key : ((this.type == 1) ? (T)entry1.value : (T)entry1);
/*      */       } 
/* 1377 */       throw new NoSuchElementException("Hashtable Enumerator");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1382 */       return hasMoreElements();
/*      */     }
/*      */     
/*      */     public T next() {
/* 1386 */       if (Hashtable.this.modCount != this.expectedModCount)
/* 1387 */         throw new ConcurrentModificationException(); 
/* 1388 */       return nextElement();
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1392 */       if (!this.iterator)
/* 1393 */         throw new UnsupportedOperationException(); 
/* 1394 */       if (this.lastReturned == null)
/* 1395 */         throw new IllegalStateException("Hashtable Enumerator"); 
/* 1396 */       if (Hashtable.this.modCount != this.expectedModCount) {
/* 1397 */         throw new ConcurrentModificationException();
/*      */       }
/* 1399 */       synchronized (Hashtable.this) {
/* 1400 */         Hashtable.Entry[] arrayOfEntry = (Hashtable.Entry[])Hashtable.this.table;
/* 1401 */         int i = (this.lastReturned.hash & Integer.MAX_VALUE) % arrayOfEntry.length;
/*      */ 
/*      */         
/* 1404 */         Hashtable.Entry<?, ?> entry1 = arrayOfEntry[i];
/* 1405 */         for (Hashtable.Entry<?, ?> entry2 = null; entry1 != null; entry2 = entry1, entry1 = entry1.next) {
/* 1406 */           if (entry1 == this.lastReturned) {
/* 1407 */             Hashtable.this.modCount++;
/* 1408 */             this.expectedModCount++;
/* 1409 */             if (entry2 == null) {
/* 1410 */               arrayOfEntry[i] = entry1.next;
/*      */             } else {
/* 1412 */               entry2.next = (Hashtable.Entry)entry1.next;
/* 1413 */             }  Hashtable.this.count--;
/* 1414 */             this.lastReturned = null;
/*      */             return;
/*      */           } 
/*      */         } 
/* 1418 */         throw new ConcurrentModificationException();
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Hashtable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */