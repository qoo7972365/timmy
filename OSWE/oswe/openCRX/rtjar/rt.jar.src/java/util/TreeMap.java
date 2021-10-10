/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.invoke.SerializedLambda;
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
/*      */ public class TreeMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements NavigableMap<K, V>, Cloneable, Serializable
/*      */ {
/*      */   private final Comparator<? super K> comparator;
/*      */   private transient Entry<K, V> root;
/*  128 */   private transient int size = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   private transient int modCount = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient EntrySet entrySet;
/*      */ 
/*      */   
/*      */   private transient KeySet<K> navigableKeySet;
/*      */ 
/*      */   
/*      */   private transient NavigableMap<K, V> descendingMap;
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeMap() {
/*  148 */     this.comparator = null;
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
/*      */   public TreeMap(Comparator<? super K> paramComparator) {
/*  166 */     this.comparator = paramComparator;
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
/*      */   public TreeMap(Map<? extends K, ? extends V> paramMap) {
/*  184 */     this.comparator = null;
/*  185 */     putAll(paramMap);
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
/*      */   public TreeMap(SortedMap<K, ? extends V> paramSortedMap) {
/*  198 */     this.comparator = paramSortedMap.comparator();
/*      */     
/*  200 */     try { buildFromSorted(paramSortedMap.size(), paramSortedMap.entrySet().iterator(), (ObjectInputStream)null, (V)null); }
/*  201 */     catch (IOException iOException) {  }
/*  202 */     catch (ClassNotFoundException classNotFoundException) {}
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
/*      */   public int size() {
/*  215 */     return this.size;
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
/*      */   public boolean containsKey(Object paramObject) {
/*  232 */     return (getEntry(paramObject) != null);
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
/*      */   public boolean containsValue(Object paramObject) {
/*  249 */     for (Entry<K, V> entry = getFirstEntry(); entry != null; entry = successor(entry)) {
/*  250 */       if (valEquals(paramObject, entry.value))
/*  251 */         return true; 
/*  252 */     }  return false;
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
/*      */   public V get(Object paramObject) {
/*  278 */     Entry<K, V> entry = getEntry(paramObject);
/*  279 */     return (entry == null) ? null : entry.value;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  283 */     return this.comparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  290 */     return key(getFirstEntry());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  297 */     return key(getLastEntry());
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
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap) {
/*  313 */     int i = paramMap.size();
/*  314 */     if (this.size == 0 && i != 0 && paramMap instanceof SortedMap) {
/*  315 */       Comparator<? super K> comparator = ((SortedMap)paramMap).comparator();
/*  316 */       if (comparator == this.comparator || (comparator != null && comparator.equals(this.comparator))) {
/*  317 */         this.modCount++;
/*      */         
/*  319 */         try { buildFromSorted(i, paramMap.entrySet().iterator(), (ObjectInputStream)null, (V)null); }
/*      */         
/*  321 */         catch (IOException iOException) {  }
/*  322 */         catch (ClassNotFoundException classNotFoundException) {}
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  327 */     super.putAll(paramMap);
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
/*      */   final Entry<K, V> getEntry(Object paramObject) {
/*  344 */     if (this.comparator != null)
/*  345 */       return getEntryUsingComparator(paramObject); 
/*  346 */     if (paramObject == null) {
/*  347 */       throw new NullPointerException();
/*      */     }
/*  349 */     Comparable<K> comparable = (Comparable)paramObject;
/*  350 */     Entry<K, V> entry = this.root;
/*  351 */     while (entry != null) {
/*  352 */       int i = comparable.compareTo(entry.key);
/*  353 */       if (i < 0) {
/*  354 */         entry = entry.left; continue;
/*  355 */       }  if (i > 0) {
/*  356 */         entry = entry.right; continue;
/*      */       } 
/*  358 */       return entry;
/*      */     } 
/*  360 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> getEntryUsingComparator(Object paramObject) {
/*  371 */     Object object = paramObject;
/*  372 */     Comparator<? super K> comparator = this.comparator;
/*  373 */     if (comparator != null) {
/*  374 */       Entry<K, V> entry = this.root;
/*  375 */       while (entry != null) {
/*  376 */         int i = comparator.compare((K)object, entry.key);
/*  377 */         if (i < 0) {
/*  378 */           entry = entry.left; continue;
/*  379 */         }  if (i > 0) {
/*  380 */           entry = entry.right; continue;
/*      */         } 
/*  382 */         return entry;
/*      */       } 
/*      */     } 
/*  385 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> getCeilingEntry(K paramK) {
/*  395 */     Entry<K, V> entry = this.root;
/*  396 */     while (entry != null) {
/*  397 */       int i = compare(paramK, entry.key);
/*  398 */       if (i < 0) {
/*  399 */         if (entry.left != null) {
/*  400 */           entry = entry.left; continue;
/*      */         } 
/*  402 */         return entry;
/*  403 */       }  if (i > 0) {
/*  404 */         if (entry.right != null) {
/*  405 */           entry = entry.right; continue;
/*      */         } 
/*  407 */         Entry<K, V> entry1 = entry.parent;
/*  408 */         Entry<K, V> entry2 = entry;
/*  409 */         while (entry1 != null && entry2 == entry1.right) {
/*  410 */           entry2 = entry1;
/*  411 */           entry1 = entry1.parent;
/*      */         } 
/*  413 */         return entry1;
/*      */       } 
/*      */       
/*  416 */       return entry;
/*      */     } 
/*  418 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> getFloorEntry(K paramK) {
/*  427 */     Entry<K, V> entry = this.root;
/*  428 */     while (entry != null) {
/*  429 */       int i = compare(paramK, entry.key);
/*  430 */       if (i > 0) {
/*  431 */         if (entry.right != null) {
/*  432 */           entry = entry.right; continue;
/*      */         } 
/*  434 */         return entry;
/*  435 */       }  if (i < 0) {
/*  436 */         if (entry.left != null) {
/*  437 */           entry = entry.left; continue;
/*      */         } 
/*  439 */         Entry<K, V> entry1 = entry.parent;
/*  440 */         Entry<K, V> entry2 = entry;
/*  441 */         while (entry1 != null && entry2 == entry1.left) {
/*  442 */           entry2 = entry1;
/*  443 */           entry1 = entry1.parent;
/*      */         } 
/*  445 */         return entry1;
/*      */       } 
/*      */       
/*  448 */       return entry;
/*      */     } 
/*      */     
/*  451 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> getHigherEntry(K paramK) {
/*  461 */     Entry<K, V> entry = this.root;
/*  462 */     while (entry != null) {
/*  463 */       int i = compare(paramK, entry.key);
/*  464 */       if (i < 0) {
/*  465 */         if (entry.left != null) {
/*  466 */           entry = entry.left; continue;
/*      */         } 
/*  468 */         return entry;
/*      */       } 
/*  470 */       if (entry.right != null) {
/*  471 */         entry = entry.right; continue;
/*      */       } 
/*  473 */       Entry<K, V> entry1 = entry.parent;
/*  474 */       Entry<K, V> entry2 = entry;
/*  475 */       while (entry1 != null && entry2 == entry1.right) {
/*  476 */         entry2 = entry1;
/*  477 */         entry1 = entry1.parent;
/*      */       } 
/*  479 */       return entry1;
/*      */     } 
/*      */ 
/*      */     
/*  483 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> getLowerEntry(K paramK) {
/*  492 */     Entry<K, V> entry = this.root;
/*  493 */     while (entry != null) {
/*  494 */       int i = compare(paramK, entry.key);
/*  495 */       if (i > 0) {
/*  496 */         if (entry.right != null) {
/*  497 */           entry = entry.right; continue;
/*      */         } 
/*  499 */         return entry;
/*      */       } 
/*  501 */       if (entry.left != null) {
/*  502 */         entry = entry.left; continue;
/*      */       } 
/*  504 */       Entry<K, V> entry1 = entry.parent;
/*  505 */       Entry<K, V> entry2 = entry;
/*  506 */       while (entry1 != null && entry2 == entry1.left) {
/*  507 */         entry2 = entry1;
/*  508 */         entry1 = entry1.parent;
/*      */       } 
/*  510 */       return entry1;
/*      */     } 
/*      */ 
/*      */     
/*  514 */     return null;
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
/*      */   public V put(K paramK, V paramV) {
/*      */     int i;
/*  536 */     Entry<K, V> entry2, entry1 = this.root;
/*  537 */     if (entry1 == null) {
/*  538 */       compare(paramK, paramK);
/*      */       
/*  540 */       this.root = new Entry<>(paramK, paramV, null);
/*  541 */       this.size = 1;
/*  542 */       this.modCount++;
/*  543 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  548 */     Comparator<? super K> comparator = this.comparator;
/*  549 */     if (comparator != null) {
/*      */       do {
/*  551 */         entry2 = entry1;
/*  552 */         i = comparator.compare(paramK, entry1.key);
/*  553 */         if (i < 0)
/*  554 */         { entry1 = entry1.left; }
/*  555 */         else if (i > 0)
/*  556 */         { entry1 = entry1.right; }
/*      */         else
/*  558 */         { return entry1.setValue(paramV); } 
/*  559 */       } while (entry1 != null);
/*      */     } else {
/*      */       
/*  562 */       if (paramK == null) {
/*  563 */         throw new NullPointerException();
/*      */       }
/*  565 */       Comparable<K> comparable = (Comparable)paramK;
/*      */       do {
/*  567 */         entry2 = entry1;
/*  568 */         i = comparable.compareTo(entry1.key);
/*  569 */         if (i < 0)
/*  570 */         { entry1 = entry1.left; }
/*  571 */         else if (i > 0)
/*  572 */         { entry1 = entry1.right; }
/*      */         else
/*  574 */         { return entry1.setValue(paramV); } 
/*  575 */       } while (entry1 != null);
/*      */     } 
/*  577 */     Entry<K, V> entry3 = new Entry<>(paramK, paramV, entry2);
/*  578 */     if (i < 0) {
/*  579 */       entry2.left = entry3;
/*      */     } else {
/*  581 */       entry2.right = entry3;
/*  582 */     }  fixAfterInsertion(entry3);
/*  583 */     this.size++;
/*  584 */     this.modCount++;
/*  585 */     return null;
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
/*      */   public V remove(Object paramObject) {
/*  603 */     Entry<K, V> entry = getEntry(paramObject);
/*  604 */     if (entry == null) {
/*  605 */       return null;
/*      */     }
/*  607 */     V v = entry.value;
/*  608 */     deleteEntry(entry);
/*  609 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  617 */     this.modCount++;
/*  618 */     this.size = 0;
/*  619 */     this.root = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     TreeMap treeMap;
/*      */     try {
/*  631 */       treeMap = (TreeMap)super.clone();
/*  632 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  633 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */ 
/*      */     
/*  637 */     treeMap.root = null;
/*  638 */     treeMap.size = 0;
/*  639 */     treeMap.modCount = 0;
/*  640 */     treeMap.entrySet = null;
/*  641 */     treeMap.navigableKeySet = null;
/*  642 */     treeMap.descendingMap = null;
/*      */ 
/*      */ 
/*      */     
/*  646 */     try { treeMap.buildFromSorted(this.size, entrySet().iterator(), (ObjectInputStream)null, (Object)null); }
/*  647 */     catch (IOException iOException) {  }
/*  648 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */ 
/*      */     
/*  651 */     return treeMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> firstEntry() {
/*  660 */     return exportEntry(getFirstEntry());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> lastEntry() {
/*  667 */     return exportEntry(getLastEntry());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> pollFirstEntry() {
/*  674 */     Entry<K, V> entry = getFirstEntry();
/*  675 */     Map.Entry<K, V> entry1 = exportEntry(entry);
/*  676 */     if (entry != null)
/*  677 */       deleteEntry(entry); 
/*  678 */     return entry1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> pollLastEntry() {
/*  685 */     Entry<K, V> entry = getLastEntry();
/*  686 */     Map.Entry<K, V> entry1 = exportEntry(entry);
/*  687 */     if (entry != null)
/*  688 */       deleteEntry(entry); 
/*  689 */     return entry1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> lowerEntry(K paramK) {
/*  700 */     return exportEntry(getLowerEntry(paramK));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lowerKey(K paramK) {
/*  711 */     return keyOrNull(getLowerEntry(paramK));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> floorEntry(K paramK) {
/*  722 */     return exportEntry(getFloorEntry(paramK));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K floorKey(K paramK) {
/*  733 */     return keyOrNull(getFloorEntry(paramK));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> ceilingEntry(K paramK) {
/*  744 */     return exportEntry(getCeilingEntry(paramK));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K ceilingKey(K paramK) {
/*  755 */     return keyOrNull(getCeilingEntry(paramK));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> higherEntry(K paramK) {
/*  766 */     return exportEntry(getHigherEntry(paramK));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K higherKey(K paramK) {
/*  777 */     return keyOrNull(getHigherEntry(paramK));
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
/*      */   public Set<K> keySet() {
/*  817 */     return navigableKeySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NavigableSet<K> navigableKeySet() {
/*  824 */     KeySet<K> keySet = this.navigableKeySet;
/*  825 */     return (keySet != null) ? keySet : (this.navigableKeySet = new KeySet<>(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NavigableSet<K> descendingKeySet() {
/*  832 */     return descendingMap().navigableKeySet();
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
/*      */   public Collection<V> values() {
/*  857 */     Collection<V> collection = this.values;
/*  858 */     if (collection == null) {
/*  859 */       collection = new Values();
/*  860 */       this.values = collection;
/*      */     } 
/*  862 */     return collection;
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
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/*  888 */     EntrySet entrySet = this.entrySet;
/*  889 */     return (entrySet != null) ? entrySet : (this.entrySet = new EntrySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NavigableMap<K, V> descendingMap() {
/*  896 */     NavigableMap<K, V> navigableMap = this.descendingMap;
/*  897 */     return (navigableMap != null) ? navigableMap : (this.descendingMap = new DescendingSubMap<>(this, true, null, true, true, null, true));
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
/*      */   public NavigableMap<K, V> subMap(K paramK1, boolean paramBoolean1, K paramK2, boolean paramBoolean2) {
/*  913 */     return new AscendingSubMap<>(this, false, paramK1, paramBoolean1, false, paramK2, paramBoolean2);
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
/*      */   public NavigableMap<K, V> headMap(K paramK, boolean paramBoolean) {
/*  927 */     return new AscendingSubMap<>(this, true, null, true, false, paramK, paramBoolean);
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
/*      */   public NavigableMap<K, V> tailMap(K paramK, boolean paramBoolean) {
/*  941 */     return new AscendingSubMap<>(this, false, paramK, paramBoolean, true, null, true);
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
/*      */   public SortedMap<K, V> subMap(K paramK1, K paramK2) {
/*  954 */     return subMap(paramK1, true, paramK2, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SortedMap<K, V> headMap(K paramK) {
/*  965 */     return headMap(paramK, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SortedMap<K, V> tailMap(K paramK) {
/*  976 */     return tailMap(paramK, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K paramK, V paramV1, V paramV2) {
/*  981 */     Entry<K, V> entry = getEntry(paramK);
/*  982 */     if (entry != null && Objects.equals(paramV1, entry.value)) {
/*  983 */       entry.value = paramV2;
/*  984 */       return true;
/*      */     } 
/*  986 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(K paramK, V paramV) {
/*  991 */     Entry<K, V> entry = getEntry(paramK);
/*  992 */     if (entry != null) {
/*  993 */       V v = entry.value;
/*  994 */       entry.value = paramV;
/*  995 */       return v;
/*      */     } 
/*  997 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 1002 */     Objects.requireNonNull(paramBiConsumer);
/* 1003 */     int i = this.modCount;
/* 1004 */     for (Entry<K, V> entry = getFirstEntry(); entry != null; entry = successor(entry)) {
/* 1005 */       paramBiConsumer.accept(entry.key, entry.value);
/*      */       
/* 1007 */       if (i != this.modCount) {
/* 1008 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1015 */     Objects.requireNonNull(paramBiFunction);
/* 1016 */     int i = this.modCount;
/*      */     
/* 1018 */     for (Entry<K, V> entry = getFirstEntry(); entry != null; entry = successor(entry)) {
/* 1019 */       entry.value = paramBiFunction.apply(entry.key, entry.value);
/*      */       
/* 1021 */       if (i != this.modCount) {
/* 1022 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   class Values
/*      */     extends AbstractCollection<V>
/*      */   {
/*      */     public Iterator<V> iterator() {
/* 1031 */       return new TreeMap.ValueIterator(TreeMap.this.getFirstEntry());
/*      */     }
/*      */     
/*      */     public int size() {
/* 1035 */       return TreeMap.this.size();
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 1039 */       return TreeMap.this.containsValue(param1Object);
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object) {
/* 1043 */       for (TreeMap.Entry<?, ?> entry = TreeMap.this.getFirstEntry(); entry != null; entry = TreeMap.successor(entry)) {
/* 1044 */         if (TreeMap.valEquals(entry.getValue(), param1Object)) {
/* 1045 */           TreeMap.this.deleteEntry((TreeMap.Entry)entry);
/* 1046 */           return true;
/*      */         } 
/*      */       } 
/* 1049 */       return false;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1053 */       TreeMap.this.clear();
/*      */     }
/*      */     
/*      */     public Spliterator<V> spliterator() {
/* 1057 */       return new TreeMap.ValueSpliterator<>(TreeMap.this, null, null, 0, -1, 0);
/*      */     }
/*      */   }
/*      */   
/*      */   class EntrySet extends AbstractSet<Map.Entry<K, V>> {
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 1063 */       return new TreeMap.EntryIterator(TreeMap.this.getFirstEntry());
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 1067 */       if (!(param1Object instanceof Map.Entry))
/* 1068 */         return false; 
/* 1069 */       Map.Entry entry = (Map.Entry)param1Object;
/* 1070 */       Object object = entry.getValue();
/* 1071 */       TreeMap.Entry entry1 = TreeMap.this.getEntry(entry.getKey());
/* 1072 */       return (entry1 != null && TreeMap.valEquals(entry1.getValue(), object));
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object) {
/* 1076 */       if (!(param1Object instanceof Map.Entry))
/* 1077 */         return false; 
/* 1078 */       Map.Entry entry = (Map.Entry)param1Object;
/* 1079 */       Object object = entry.getValue();
/* 1080 */       TreeMap.Entry entry1 = TreeMap.this.getEntry(entry.getKey());
/* 1081 */       if (entry1 != null && TreeMap.valEquals(entry1.getValue(), object)) {
/* 1082 */         TreeMap.this.deleteEntry(entry1);
/* 1083 */         return true;
/*      */       } 
/* 1085 */       return false;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1089 */       return TreeMap.this.size();
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1093 */       TreeMap.this.clear();
/*      */     }
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> spliterator() {
/* 1097 */       return new TreeMap.EntrySpliterator<>(TreeMap.this, null, null, 0, -1, 0);
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
/*      */   Iterator<K> keyIterator() {
/* 1110 */     return new KeyIterator(getFirstEntry());
/*      */   }
/*      */   
/*      */   Iterator<K> descendingKeyIterator() {
/* 1114 */     return new DescendingKeyIterator(getLastEntry());
/*      */   }
/*      */   static final class KeySet<E> extends AbstractSet<E> implements NavigableSet<E> { private final NavigableMap<E, ?> m;
/*      */     
/*      */     KeySet(NavigableMap<E, ?> param1NavigableMap) {
/* 1119 */       this.m = param1NavigableMap;
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 1122 */       if (this.m instanceof TreeMap) {
/* 1123 */         return ((TreeMap)this.m).keyIterator();
/*      */       }
/* 1125 */       return ((TreeMap.NavigableSubMap)this.m).keyIterator();
/*      */     }
/*      */     
/*      */     public Iterator<E> descendingIterator() {
/* 1129 */       if (this.m instanceof TreeMap) {
/* 1130 */         return ((TreeMap)this.m).descendingKeyIterator();
/*      */       }
/* 1132 */       return ((TreeMap.NavigableSubMap)this.m).descendingKeyIterator();
/*      */     }
/*      */     
/* 1135 */     public int size() { return this.m.size(); }
/* 1136 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 1137 */     public boolean contains(Object param1Object) { return this.m.containsKey(param1Object); }
/* 1138 */     public void clear() { this.m.clear(); }
/* 1139 */     public E lower(E param1E) { return this.m.lowerKey(param1E); }
/* 1140 */     public E floor(E param1E) { return this.m.floorKey(param1E); }
/* 1141 */     public E ceiling(E param1E) { return this.m.ceilingKey(param1E); }
/* 1142 */     public E higher(E param1E) { return this.m.higherKey(param1E); }
/* 1143 */     public E first() { return this.m.firstKey(); }
/* 1144 */     public E last() { return this.m.lastKey(); } public Comparator<? super E> comparator() {
/* 1145 */       return this.m.comparator();
/*      */     } public E pollFirst() {
/* 1147 */       Map.Entry<E, ?> entry = this.m.pollFirstEntry();
/* 1148 */       return (entry == null) ? null : entry.getKey();
/*      */     }
/*      */     public E pollLast() {
/* 1151 */       Map.Entry<E, ?> entry = this.m.pollLastEntry();
/* 1152 */       return (entry == null) ? null : entry.getKey();
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/* 1155 */       int i = size();
/* 1156 */       this.m.remove(param1Object);
/* 1157 */       return (size() != i);
/*      */     }
/*      */     
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 1161 */       return new KeySet(this.m.subMap(param1E1, param1Boolean1, param1E2, param1Boolean2));
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 1165 */       return new KeySet(this.m.headMap(param1E, param1Boolean));
/*      */     }
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 1168 */       return new KeySet(this.m.tailMap(param1E, param1Boolean));
/*      */     }
/*      */     public SortedSet<E> subSet(E param1E1, E param1E2) {
/* 1171 */       return subSet(param1E1, true, param1E2, false);
/*      */     }
/*      */     public SortedSet<E> headSet(E param1E) {
/* 1174 */       return headSet(param1E, false);
/*      */     }
/*      */     public SortedSet<E> tailSet(E param1E) {
/* 1177 */       return tailSet(param1E, true);
/*      */     }
/*      */     public NavigableSet<E> descendingSet() {
/* 1180 */       return new KeySet(this.m.descendingMap());
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 1184 */       return TreeMap.keySpliteratorFor(this.m);
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   abstract class PrivateEntryIterator<T>
/*      */     implements Iterator<T>
/*      */   {
/*      */     TreeMap.Entry<K, V> next;
/*      */     TreeMap.Entry<K, V> lastReturned;
/*      */     int expectedModCount;
/*      */     
/*      */     PrivateEntryIterator(TreeMap.Entry<K, V> param1Entry) {
/* 1197 */       this.expectedModCount = TreeMap.this.modCount;
/* 1198 */       this.lastReturned = null;
/* 1199 */       this.next = param1Entry;
/*      */     }
/*      */     
/*      */     public final boolean hasNext() {
/* 1203 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     final TreeMap.Entry<K, V> nextEntry() {
/* 1207 */       TreeMap.Entry<K, V> entry = this.next;
/* 1208 */       if (entry == null)
/* 1209 */         throw new NoSuchElementException(); 
/* 1210 */       if (TreeMap.this.modCount != this.expectedModCount)
/* 1211 */         throw new ConcurrentModificationException(); 
/* 1212 */       this.next = TreeMap.successor(entry);
/* 1213 */       this.lastReturned = entry;
/* 1214 */       return entry;
/*      */     }
/*      */     
/*      */     final TreeMap.Entry<K, V> prevEntry() {
/* 1218 */       TreeMap.Entry<K, V> entry = this.next;
/* 1219 */       if (entry == null)
/* 1220 */         throw new NoSuchElementException(); 
/* 1221 */       if (TreeMap.this.modCount != this.expectedModCount)
/* 1222 */         throw new ConcurrentModificationException(); 
/* 1223 */       this.next = TreeMap.predecessor(entry);
/* 1224 */       this.lastReturned = entry;
/* 1225 */       return entry;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1229 */       if (this.lastReturned == null)
/* 1230 */         throw new IllegalStateException(); 
/* 1231 */       if (TreeMap.this.modCount != this.expectedModCount) {
/* 1232 */         throw new ConcurrentModificationException();
/*      */       }
/* 1234 */       if (this.lastReturned.left != null && this.lastReturned.right != null)
/* 1235 */         this.next = this.lastReturned; 
/* 1236 */       TreeMap.this.deleteEntry(this.lastReturned);
/* 1237 */       this.expectedModCount = TreeMap.this.modCount;
/* 1238 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntryIterator extends PrivateEntryIterator<Map.Entry<K, V>> {
/*      */     EntryIterator(TreeMap.Entry<K, V> param1Entry) {
/* 1244 */       super(param1Entry);
/*      */     }
/*      */     public Map.Entry<K, V> next() {
/* 1247 */       return nextEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   final class ValueIterator extends PrivateEntryIterator<V> {
/*      */     ValueIterator(TreeMap.Entry<K, V> param1Entry) {
/* 1253 */       super(param1Entry);
/*      */     }
/*      */     public V next() {
/* 1256 */       return (nextEntry()).value;
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeyIterator extends PrivateEntryIterator<K> {
/*      */     KeyIterator(TreeMap.Entry<K, V> param1Entry) {
/* 1262 */       super(param1Entry);
/*      */     }
/*      */     public K next() {
/* 1265 */       return (nextEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   final class DescendingKeyIterator extends PrivateEntryIterator<K> {
/*      */     DescendingKeyIterator(TreeMap.Entry<K, V> param1Entry) {
/* 1271 */       super(param1Entry);
/*      */     }
/*      */     public K next() {
/* 1274 */       return (prevEntry()).key;
/*      */     }
/*      */     public void remove() {
/* 1277 */       if (this.lastReturned == null)
/* 1278 */         throw new IllegalStateException(); 
/* 1279 */       if (TreeMap.this.modCount != this.expectedModCount)
/* 1280 */         throw new ConcurrentModificationException(); 
/* 1281 */       TreeMap.this.deleteEntry(this.lastReturned);
/* 1282 */       this.lastReturned = null;
/* 1283 */       this.expectedModCount = TreeMap.this.modCount;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compare(Object paramObject1, Object paramObject2) {
/* 1294 */     return (this.comparator == null) ? ((Comparable<Object>)paramObject1).compareTo(paramObject2) : this.comparator
/* 1295 */       .compare((K)paramObject1, (K)paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean valEquals(Object paramObject1, Object paramObject2) {
/* 1303 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> Map.Entry<K, V> exportEntry(Entry<K, V> paramEntry) {
/* 1310 */     return (paramEntry == null) ? null : new AbstractMap.SimpleImmutableEntry<>(paramEntry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> K keyOrNull(Entry<K, V> paramEntry) {
/* 1318 */     return (paramEntry == null) ? null : paramEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K> K key(Entry<K, ?> paramEntry) {
/* 1326 */     if (paramEntry == null)
/* 1327 */       throw new NoSuchElementException(); 
/* 1328 */     return paramEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1338 */   private static final Object UNBOUNDED = new Object();
/*      */   
/*      */   private static final boolean RED = false;
/*      */   
/*      */   private static final boolean BLACK = true;
/*      */   
/*      */   private static final long serialVersionUID = 919286545866124006L;
/*      */ 
/*      */   
/*      */   static abstract class NavigableSubMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */     implements NavigableMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -2102997345730753016L;
/*      */     
/*      */     final TreeMap<K, V> m;
/*      */     
/*      */     final K lo;
/*      */     final K hi;
/*      */     final boolean fromStart;
/*      */     final boolean toEnd;
/*      */     final boolean loInclusive;
/*      */     final boolean hiInclusive;
/*      */     transient NavigableMap<K, V> descendingMapView;
/*      */     transient EntrySetView entrySetView;
/*      */     transient TreeMap.KeySet<K> navigableKeySetView;
/*      */     
/*      */     NavigableSubMap(TreeMap<K, V> param1TreeMap, boolean param1Boolean1, K param1K1, boolean param1Boolean2, boolean param1Boolean3, K param1K2, boolean param1Boolean4) {
/* 1366 */       if (!param1Boolean1 && !param1Boolean3) {
/* 1367 */         if (param1TreeMap.compare(param1K1, param1K2) > 0)
/* 1368 */           throw new IllegalArgumentException("fromKey > toKey"); 
/*      */       } else {
/* 1370 */         if (!param1Boolean1)
/* 1371 */           param1TreeMap.compare(param1K1, param1K1); 
/* 1372 */         if (!param1Boolean3) {
/* 1373 */           param1TreeMap.compare(param1K2, param1K2);
/*      */         }
/*      */       } 
/* 1376 */       this.m = param1TreeMap;
/* 1377 */       this.fromStart = param1Boolean1;
/* 1378 */       this.lo = param1K1;
/* 1379 */       this.loInclusive = param1Boolean2;
/* 1380 */       this.toEnd = param1Boolean3;
/* 1381 */       this.hi = param1K2;
/* 1382 */       this.hiInclusive = param1Boolean4;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean tooLow(Object param1Object) {
/* 1388 */       if (!this.fromStart) {
/* 1389 */         int i = this.m.compare(param1Object, this.lo);
/* 1390 */         if (i < 0 || (i == 0 && !this.loInclusive))
/* 1391 */           return true; 
/*      */       } 
/* 1393 */       return false;
/*      */     }
/*      */     
/*      */     final boolean tooHigh(Object param1Object) {
/* 1397 */       if (!this.toEnd) {
/* 1398 */         int i = this.m.compare(param1Object, this.hi);
/* 1399 */         if (i > 0 || (i == 0 && !this.hiInclusive))
/* 1400 */           return true; 
/*      */       } 
/* 1402 */       return false;
/*      */     }
/*      */     
/*      */     final boolean inRange(Object param1Object) {
/* 1406 */       return (!tooLow(param1Object) && !tooHigh(param1Object));
/*      */     }
/*      */     
/*      */     final boolean inClosedRange(Object param1Object) {
/* 1410 */       return ((this.fromStart || this.m.compare(param1Object, this.lo) >= 0) && (this.toEnd || this.m
/* 1411 */         .compare(this.hi, param1Object) >= 0));
/*      */     }
/*      */     
/*      */     final boolean inRange(Object param1Object, boolean param1Boolean) {
/* 1415 */       return param1Boolean ? inRange(param1Object) : inClosedRange(param1Object);
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
/*      */     
/*      */     final TreeMap.Entry<K, V> absLowest() {
/* 1428 */       TreeMap.Entry<K, V> entry = this.fromStart ? this.m.getFirstEntry() : (this.loInclusive ? this.m.getCeilingEntry(this.lo) : this.m.getHigherEntry(this.lo));
/* 1429 */       return (entry == null || tooHigh(entry.key)) ? null : entry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final TreeMap.Entry<K, V> absHighest() {
/* 1436 */       TreeMap.Entry<K, V> entry = this.toEnd ? this.m.getLastEntry() : (this.hiInclusive ? this.m.getFloorEntry(this.hi) : this.m.getLowerEntry(this.hi));
/* 1437 */       return (entry == null || tooLow(entry.key)) ? null : entry;
/*      */     }
/*      */     
/*      */     final TreeMap.Entry<K, V> absCeiling(K param1K) {
/* 1441 */       if (tooLow(param1K))
/* 1442 */         return absLowest(); 
/* 1443 */       TreeMap.Entry<K, V> entry = this.m.getCeilingEntry(param1K);
/* 1444 */       return (entry == null || tooHigh(entry.key)) ? null : entry;
/*      */     }
/*      */     
/*      */     final TreeMap.Entry<K, V> absHigher(K param1K) {
/* 1448 */       if (tooLow(param1K))
/* 1449 */         return absLowest(); 
/* 1450 */       TreeMap.Entry<K, V> entry = this.m.getHigherEntry(param1K);
/* 1451 */       return (entry == null || tooHigh(entry.key)) ? null : entry;
/*      */     }
/*      */     
/*      */     final TreeMap.Entry<K, V> absFloor(K param1K) {
/* 1455 */       if (tooHigh(param1K))
/* 1456 */         return absHighest(); 
/* 1457 */       TreeMap.Entry<K, V> entry = this.m.getFloorEntry(param1K);
/* 1458 */       return (entry == null || tooLow(entry.key)) ? null : entry;
/*      */     }
/*      */     
/*      */     final TreeMap.Entry<K, V> absLower(K param1K) {
/* 1462 */       if (tooHigh(param1K))
/* 1463 */         return absHighest(); 
/* 1464 */       TreeMap.Entry<K, V> entry = this.m.getLowerEntry(param1K);
/* 1465 */       return (entry == null || tooLow(entry.key)) ? null : entry;
/*      */     }
/*      */ 
/*      */     
/*      */     final TreeMap.Entry<K, V> absHighFence() {
/* 1470 */       return this.toEnd ? null : (this.hiInclusive ? this.m
/* 1471 */         .getHigherEntry(this.hi) : this.m
/* 1472 */         .getCeilingEntry(this.hi));
/*      */     }
/*      */ 
/*      */     
/*      */     final TreeMap.Entry<K, V> absLowFence() {
/* 1477 */       return this.fromStart ? null : (this.loInclusive ? this.m
/* 1478 */         .getLowerEntry(this.lo) : this.m
/* 1479 */         .getFloorEntry(this.lo));
/*      */     }
/*      */ 
/*      */     
/*      */     abstract TreeMap.Entry<K, V> subLowest();
/*      */ 
/*      */     
/*      */     abstract TreeMap.Entry<K, V> subHighest();
/*      */     
/*      */     abstract TreeMap.Entry<K, V> subCeiling(K param1K);
/*      */     
/*      */     abstract TreeMap.Entry<K, V> subHigher(K param1K);
/*      */     
/*      */     abstract TreeMap.Entry<K, V> subFloor(K param1K);
/*      */     
/*      */     abstract TreeMap.Entry<K, V> subLower(K param1K);
/*      */     
/*      */     abstract Iterator<K> keyIterator();
/*      */     
/*      */     abstract Spliterator<K> keySpliterator();
/*      */     
/*      */     abstract Iterator<K> descendingKeyIterator();
/*      */     
/*      */     public boolean isEmpty() {
/* 1503 */       return (this.fromStart && this.toEnd) ? this.m.isEmpty() : entrySet().isEmpty();
/*      */     }
/*      */     
/*      */     public int size() {
/* 1507 */       return (this.fromStart && this.toEnd) ? this.m.size() : entrySet().size();
/*      */     }
/*      */     
/*      */     public final boolean containsKey(Object param1Object) {
/* 1511 */       return (inRange(param1Object) && this.m.containsKey(param1Object));
/*      */     }
/*      */     
/*      */     public final V put(K param1K, V param1V) {
/* 1515 */       if (!inRange(param1K))
/* 1516 */         throw new IllegalArgumentException("key out of range"); 
/* 1517 */       return this.m.put(param1K, param1V);
/*      */     }
/*      */     
/*      */     public final V get(Object param1Object) {
/* 1521 */       return !inRange(param1Object) ? null : this.m.get(param1Object);
/*      */     }
/*      */     
/*      */     public final V remove(Object param1Object) {
/* 1525 */       return !inRange(param1Object) ? null : this.m.remove(param1Object);
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> ceilingEntry(K param1K) {
/* 1529 */       return TreeMap.exportEntry(subCeiling(param1K));
/*      */     }
/*      */     
/*      */     public final K ceilingKey(K param1K) {
/* 1533 */       return TreeMap.keyOrNull(subCeiling(param1K));
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> higherEntry(K param1K) {
/* 1537 */       return TreeMap.exportEntry(subHigher(param1K));
/*      */     }
/*      */     
/*      */     public final K higherKey(K param1K) {
/* 1541 */       return TreeMap.keyOrNull(subHigher(param1K));
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> floorEntry(K param1K) {
/* 1545 */       return TreeMap.exportEntry(subFloor(param1K));
/*      */     }
/*      */     
/*      */     public final K floorKey(K param1K) {
/* 1549 */       return TreeMap.keyOrNull(subFloor(param1K));
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> lowerEntry(K param1K) {
/* 1553 */       return TreeMap.exportEntry(subLower(param1K));
/*      */     }
/*      */     
/*      */     public final K lowerKey(K param1K) {
/* 1557 */       return TreeMap.keyOrNull(subLower(param1K));
/*      */     }
/*      */     
/*      */     public final K firstKey() {
/* 1561 */       return TreeMap.key(subLowest());
/*      */     }
/*      */     
/*      */     public final K lastKey() {
/* 1565 */       return TreeMap.key(subHighest());
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> firstEntry() {
/* 1569 */       return TreeMap.exportEntry(subLowest());
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> lastEntry() {
/* 1573 */       return TreeMap.exportEntry(subHighest());
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> pollFirstEntry() {
/* 1577 */       TreeMap.Entry<K, V> entry = subLowest();
/* 1578 */       Map.Entry<K, V> entry1 = TreeMap.exportEntry(entry);
/* 1579 */       if (entry != null)
/* 1580 */         this.m.deleteEntry(entry); 
/* 1581 */       return entry1;
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> pollLastEntry() {
/* 1585 */       TreeMap.Entry<K, V> entry = subHighest();
/* 1586 */       Map.Entry<K, V> entry1 = TreeMap.exportEntry(entry);
/* 1587 */       if (entry != null)
/* 1588 */         this.m.deleteEntry(entry); 
/* 1589 */       return entry1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final NavigableSet<K> navigableKeySet() {
/* 1598 */       TreeMap.KeySet<K> keySet = this.navigableKeySetView;
/* 1599 */       return (keySet != null) ? keySet : (this.navigableKeySetView = new TreeMap.KeySet<>(this));
/*      */     }
/*      */ 
/*      */     
/*      */     public final Set<K> keySet() {
/* 1604 */       return navigableKeySet();
/*      */     }
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 1608 */       return descendingMap().navigableKeySet();
/*      */     }
/*      */     
/*      */     public final SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 1612 */       return subMap(param1K1, true, param1K2, false);
/*      */     }
/*      */     
/*      */     public final SortedMap<K, V> headMap(K param1K) {
/* 1616 */       return headMap(param1K, false);
/*      */     }
/*      */     
/*      */     public final SortedMap<K, V> tailMap(K param1K) {
/* 1620 */       return tailMap(param1K, true);
/*      */     }
/*      */     
/*      */     abstract class EntrySetView
/*      */       extends AbstractSet<Map.Entry<K, V>>
/*      */     {
/* 1626 */       private transient int size = -1; private transient int sizeModCount;
/*      */       
/*      */       public int size() {
/* 1629 */         if (TreeMap.NavigableSubMap.this.fromStart && TreeMap.NavigableSubMap.this.toEnd)
/* 1630 */           return TreeMap.NavigableSubMap.this.m.size(); 
/* 1631 */         if (this.size == -1 || this.sizeModCount != TreeMap.NavigableSubMap.this.m.modCount) {
/* 1632 */           this.sizeModCount = TreeMap.NavigableSubMap.this.m.modCount;
/* 1633 */           this.size = 0;
/* 1634 */           Iterator<Map.Entry<K, V>> iterator = iterator();
/* 1635 */           while (iterator.hasNext()) {
/* 1636 */             this.size++;
/* 1637 */             iterator.next();
/*      */           } 
/*      */         } 
/* 1640 */         return this.size;
/*      */       }
/*      */       
/*      */       public boolean isEmpty() {
/* 1644 */         TreeMap.Entry entry = TreeMap.NavigableSubMap.this.absLowest();
/* 1645 */         return (entry == null || TreeMap.NavigableSubMap.this.tooHigh(entry.key));
/*      */       }
/*      */       
/*      */       public boolean contains(Object param2Object) {
/* 1649 */         if (!(param2Object instanceof Map.Entry))
/* 1650 */           return false; 
/* 1651 */         Map.Entry entry = (Map.Entry)param2Object;
/* 1652 */         Object object = entry.getKey();
/* 1653 */         if (!TreeMap.NavigableSubMap.this.inRange(object))
/* 1654 */           return false; 
/* 1655 */         TreeMap.Entry entry1 = TreeMap.NavigableSubMap.this.m.getEntry(object);
/* 1656 */         return (entry1 != null && 
/* 1657 */           TreeMap.valEquals(entry1.getValue(), entry.getValue()));
/*      */       }
/*      */       
/*      */       public boolean remove(Object param2Object) {
/* 1661 */         if (!(param2Object instanceof Map.Entry))
/* 1662 */           return false; 
/* 1663 */         Map.Entry entry = (Map.Entry)param2Object;
/* 1664 */         Object object = entry.getKey();
/* 1665 */         if (!TreeMap.NavigableSubMap.this.inRange(object))
/* 1666 */           return false; 
/* 1667 */         TreeMap.Entry entry1 = TreeMap.NavigableSubMap.this.m.getEntry(object);
/* 1668 */         if (entry1 != null && TreeMap.valEquals(entry1.getValue(), entry
/* 1669 */             .getValue())) {
/* 1670 */           TreeMap.NavigableSubMap.this.m.deleteEntry(entry1);
/* 1671 */           return true;
/*      */         } 
/* 1673 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     abstract class SubMapIterator<T>
/*      */       implements Iterator<T>
/*      */     {
/*      */       TreeMap.Entry<K, V> lastReturned;
/*      */       
/*      */       TreeMap.Entry<K, V> next;
/*      */       final Object fenceKey;
/*      */       int expectedModCount;
/*      */       
/*      */       SubMapIterator(TreeMap.Entry<K, V> param2Entry1, TreeMap.Entry<K, V> param2Entry2) {
/* 1688 */         this.expectedModCount = TreeMap.NavigableSubMap.this.m.modCount;
/* 1689 */         this.lastReturned = null;
/* 1690 */         this.next = param2Entry1;
/* 1691 */         this.fenceKey = (param2Entry2 == null) ? TreeMap.UNBOUNDED : param2Entry2.key;
/*      */       }
/*      */       
/*      */       public final boolean hasNext() {
/* 1695 */         return (this.next != null && this.next.key != this.fenceKey);
/*      */       }
/*      */       
/*      */       final TreeMap.Entry<K, V> nextEntry() {
/* 1699 */         TreeMap.Entry<K, V> entry = this.next;
/* 1700 */         if (entry == null || entry.key == this.fenceKey)
/* 1701 */           throw new NoSuchElementException(); 
/* 1702 */         if (TreeMap.NavigableSubMap.this.m.modCount != this.expectedModCount)
/* 1703 */           throw new ConcurrentModificationException(); 
/* 1704 */         this.next = TreeMap.successor(entry);
/* 1705 */         this.lastReturned = entry;
/* 1706 */         return entry;
/*      */       }
/*      */       
/*      */       final TreeMap.Entry<K, V> prevEntry() {
/* 1710 */         TreeMap.Entry<K, V> entry = this.next;
/* 1711 */         if (entry == null || entry.key == this.fenceKey)
/* 1712 */           throw new NoSuchElementException(); 
/* 1713 */         if (TreeMap.NavigableSubMap.this.m.modCount != this.expectedModCount)
/* 1714 */           throw new ConcurrentModificationException(); 
/* 1715 */         this.next = TreeMap.predecessor(entry);
/* 1716 */         this.lastReturned = entry;
/* 1717 */         return entry;
/*      */       }
/*      */       
/*      */       final void removeAscending() {
/* 1721 */         if (this.lastReturned == null)
/* 1722 */           throw new IllegalStateException(); 
/* 1723 */         if (TreeMap.NavigableSubMap.this.m.modCount != this.expectedModCount) {
/* 1724 */           throw new ConcurrentModificationException();
/*      */         }
/* 1726 */         if (this.lastReturned.left != null && this.lastReturned.right != null)
/* 1727 */           this.next = this.lastReturned; 
/* 1728 */         TreeMap.NavigableSubMap.this.m.deleteEntry(this.lastReturned);
/* 1729 */         this.lastReturned = null;
/* 1730 */         this.expectedModCount = TreeMap.NavigableSubMap.this.m.modCount;
/*      */       }
/*      */       
/*      */       final void removeDescending() {
/* 1734 */         if (this.lastReturned == null)
/* 1735 */           throw new IllegalStateException(); 
/* 1736 */         if (TreeMap.NavigableSubMap.this.m.modCount != this.expectedModCount)
/* 1737 */           throw new ConcurrentModificationException(); 
/* 1738 */         TreeMap.NavigableSubMap.this.m.deleteEntry(this.lastReturned);
/* 1739 */         this.lastReturned = null;
/* 1740 */         this.expectedModCount = TreeMap.NavigableSubMap.this.m.modCount;
/*      */       }
/*      */     }
/*      */     
/*      */     final class SubMapEntryIterator
/*      */       extends SubMapIterator<Map.Entry<K, V>>
/*      */     {
/*      */       SubMapEntryIterator(TreeMap.Entry<K, V> param2Entry1, TreeMap.Entry<K, V> param2Entry2) {
/* 1748 */         super(param2Entry1, param2Entry2);
/*      */       }
/*      */       public Map.Entry<K, V> next() {
/* 1751 */         return nextEntry();
/*      */       }
/*      */       public void remove() {
/* 1754 */         removeAscending();
/*      */       }
/*      */     }
/*      */     
/*      */     final class DescendingSubMapEntryIterator
/*      */       extends SubMapIterator<Map.Entry<K, V>> {
/*      */       DescendingSubMapEntryIterator(TreeMap.Entry<K, V> param2Entry1, TreeMap.Entry<K, V> param2Entry2) {
/* 1761 */         super(param2Entry1, param2Entry2);
/*      */       }
/*      */       
/*      */       public Map.Entry<K, V> next() {
/* 1765 */         return prevEntry();
/*      */       }
/*      */       public void remove() {
/* 1768 */         removeDescending();
/*      */       }
/*      */     }
/*      */     
/*      */     final class SubMapKeyIterator
/*      */       extends SubMapIterator<K>
/*      */       implements Spliterator<K>
/*      */     {
/*      */       SubMapKeyIterator(TreeMap.Entry<K, V> param2Entry1, TreeMap.Entry<K, V> param2Entry2) {
/* 1777 */         super(param2Entry1, param2Entry2);
/*      */       }
/*      */       public K next() {
/* 1780 */         return (nextEntry()).key;
/*      */       }
/*      */       public void remove() {
/* 1783 */         removeAscending();
/*      */       }
/*      */       public Spliterator<K> trySplit() {
/* 1786 */         return null;
/*      */       }
/*      */       public void forEachRemaining(Consumer<? super K> param2Consumer) {
/* 1789 */         while (hasNext())
/* 1790 */           param2Consumer.accept(next()); 
/*      */       }
/*      */       public boolean tryAdvance(Consumer<? super K> param2Consumer) {
/* 1793 */         if (hasNext()) {
/* 1794 */           param2Consumer.accept(next());
/* 1795 */           return true;
/*      */         } 
/* 1797 */         return false;
/*      */       }
/*      */       public long estimateSize() {
/* 1800 */         return Long.MAX_VALUE;
/*      */       }
/*      */       public int characteristics() {
/* 1803 */         return 21;
/*      */       }
/*      */       
/*      */       public final Comparator<? super K> getComparator() {
/* 1807 */         return TreeMap.NavigableSubMap.this.comparator();
/*      */       }
/*      */     }
/*      */     
/*      */     final class DescendingSubMapKeyIterator
/*      */       extends SubMapIterator<K>
/*      */       implements Spliterator<K> {
/*      */       DescendingSubMapKeyIterator(TreeMap.Entry<K, V> param2Entry1, TreeMap.Entry<K, V> param2Entry2) {
/* 1815 */         super(param2Entry1, param2Entry2);
/*      */       }
/*      */       public K next() {
/* 1818 */         return (prevEntry()).key;
/*      */       }
/*      */       public void remove() {
/* 1821 */         removeDescending();
/*      */       }
/*      */       public Spliterator<K> trySplit() {
/* 1824 */         return null;
/*      */       }
/*      */       public void forEachRemaining(Consumer<? super K> param2Consumer) {
/* 1827 */         while (hasNext())
/* 1828 */           param2Consumer.accept(next()); 
/*      */       }
/*      */       public boolean tryAdvance(Consumer<? super K> param2Consumer) {
/* 1831 */         if (hasNext()) {
/* 1832 */           param2Consumer.accept(next());
/* 1833 */           return true;
/*      */         } 
/* 1835 */         return false;
/*      */       }
/*      */       public long estimateSize() {
/* 1838 */         return Long.MAX_VALUE;
/*      */       }
/*      */       public int characteristics() {
/* 1841 */         return 17;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class AscendingSubMap<K, V>
/*      */     extends NavigableSubMap<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = 912986545866124060L;
/*      */ 
/*      */     
/*      */     AscendingSubMap(TreeMap<K, V> param1TreeMap, boolean param1Boolean1, K param1K1, boolean param1Boolean2, boolean param1Boolean3, K param1K2, boolean param1Boolean4) {
/* 1855 */       super(param1TreeMap, param1Boolean1, param1K1, param1Boolean2, param1Boolean3, param1K2, param1Boolean4);
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1859 */       return this.m.comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 1864 */       if (!inRange(param1K1, param1Boolean1))
/* 1865 */         throw new IllegalArgumentException("fromKey out of range"); 
/* 1866 */       if (!inRange(param1K2, param1Boolean2))
/* 1867 */         throw new IllegalArgumentException("toKey out of range"); 
/* 1868 */       return new AscendingSubMap(this.m, false, param1K1, param1Boolean1, false, param1K2, param1Boolean2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 1874 */       if (!inRange(param1K, param1Boolean))
/* 1875 */         throw new IllegalArgumentException("toKey out of range"); 
/* 1876 */       return new AscendingSubMap(this.m, this.fromStart, this.lo, this.loInclusive, false, param1K, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 1882 */       if (!inRange(param1K, param1Boolean))
/* 1883 */         throw new IllegalArgumentException("fromKey out of range"); 
/* 1884 */       return new AscendingSubMap(this.m, false, param1K, param1Boolean, this.toEnd, this.hi, this.hiInclusive);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/* 1890 */       NavigableMap<K, V> navigableMap = this.descendingMapView;
/* 1891 */       return (navigableMap != null) ? navigableMap : (this.descendingMapView = new TreeMap.DescendingSubMap<>(this.m, this.fromStart, this.lo, this.loInclusive, this.toEnd, this.hi, this.hiInclusive));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Iterator<K> keyIterator() {
/* 1899 */       return new TreeMap.NavigableSubMap.SubMapKeyIterator(this, absLowest(), absHighFence());
/*      */     }
/*      */     
/*      */     Spliterator<K> keySpliterator() {
/* 1903 */       return new TreeMap.NavigableSubMap.SubMapKeyIterator(this, absLowest(), absHighFence());
/*      */     }
/*      */     
/*      */     Iterator<K> descendingKeyIterator() {
/* 1907 */       return new TreeMap.NavigableSubMap.DescendingSubMapKeyIterator(this, absHighest(), absLowFence());
/*      */     }
/*      */     
/*      */     final class AscendingEntrySetView extends TreeMap.NavigableSubMap<K, V>.EntrySetView {
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 1912 */         return new TreeMap.NavigableSubMap.SubMapEntryIterator(TreeMap.AscendingSubMap.this, TreeMap.AscendingSubMap.this.absLowest(), TreeMap.AscendingSubMap.this.absHighFence());
/*      */       }
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1917 */       TreeMap.NavigableSubMap<K, V>.EntrySetView entrySetView = this.entrySetView;
/* 1918 */       return (entrySetView != null) ? entrySetView : (this.entrySetView = new AscendingEntrySetView());
/*      */     }
/*      */     
/* 1921 */     TreeMap.Entry<K, V> subLowest() { return absLowest(); }
/* 1922 */     TreeMap.Entry<K, V> subHighest() { return absHighest(); }
/* 1923 */     TreeMap.Entry<K, V> subCeiling(K param1K) { return absCeiling(param1K); }
/* 1924 */     TreeMap.Entry<K, V> subHigher(K param1K) { return absHigher(param1K); }
/* 1925 */     TreeMap.Entry<K, V> subFloor(K param1K) { return absFloor(param1K); } TreeMap.Entry<K, V> subLower(K param1K) {
/* 1926 */       return absLower(param1K);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class DescendingSubMap<K, V>
/*      */     extends NavigableSubMap<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = 912986545866120460L;
/*      */     private final Comparator<? super K> reverseComparator;
/*      */     
/*      */     DescendingSubMap(TreeMap<K, V> param1TreeMap, boolean param1Boolean1, K param1K1, boolean param1Boolean2, boolean param1Boolean3, K param1K2, boolean param1Boolean4) {
/* 1937 */       super(param1TreeMap, param1Boolean1, param1K1, param1Boolean2, param1Boolean3, param1K2, param1Boolean4);
/*      */ 
/*      */       
/* 1940 */       this
/* 1941 */         .reverseComparator = Collections.reverseOrder(this.m.comparator);
/*      */     }
/*      */     public Comparator<? super K> comparator() {
/* 1944 */       return this.reverseComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 1949 */       if (!inRange(param1K1, param1Boolean1))
/* 1950 */         throw new IllegalArgumentException("fromKey out of range"); 
/* 1951 */       if (!inRange(param1K2, param1Boolean2))
/* 1952 */         throw new IllegalArgumentException("toKey out of range"); 
/* 1953 */       return new DescendingSubMap(this.m, false, param1K2, param1Boolean2, false, param1K1, param1Boolean1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 1959 */       if (!inRange(param1K, param1Boolean))
/* 1960 */         throw new IllegalArgumentException("toKey out of range"); 
/* 1961 */       return new DescendingSubMap(this.m, false, param1K, param1Boolean, this.toEnd, this.hi, this.hiInclusive);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 1967 */       if (!inRange(param1K, param1Boolean))
/* 1968 */         throw new IllegalArgumentException("fromKey out of range"); 
/* 1969 */       return new DescendingSubMap(this.m, this.fromStart, this.lo, this.loInclusive, false, param1K, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/* 1975 */       NavigableMap<K, V> navigableMap = this.descendingMapView;
/* 1976 */       return (navigableMap != null) ? navigableMap : (this.descendingMapView = new TreeMap.AscendingSubMap<>(this.m, this.fromStart, this.lo, this.loInclusive, this.toEnd, this.hi, this.hiInclusive));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Iterator<K> keyIterator() {
/* 1984 */       return new TreeMap.NavigableSubMap.DescendingSubMapKeyIterator(this, absHighest(), absLowFence());
/*      */     }
/*      */     
/*      */     Spliterator<K> keySpliterator() {
/* 1988 */       return new TreeMap.NavigableSubMap.DescendingSubMapKeyIterator(this, absHighest(), absLowFence());
/*      */     }
/*      */     
/*      */     Iterator<K> descendingKeyIterator() {
/* 1992 */       return new TreeMap.NavigableSubMap.SubMapKeyIterator(this, absLowest(), absHighFence());
/*      */     }
/*      */     
/*      */     final class DescendingEntrySetView extends TreeMap.NavigableSubMap<K, V>.EntrySetView {
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 1997 */         return new TreeMap.NavigableSubMap.DescendingSubMapEntryIterator(TreeMap.DescendingSubMap.this, TreeMap.DescendingSubMap.this.absHighest(), TreeMap.DescendingSubMap.this.absLowFence());
/*      */       }
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 2002 */       TreeMap.NavigableSubMap<K, V>.EntrySetView entrySetView = this.entrySetView;
/* 2003 */       return (entrySetView != null) ? entrySetView : (this.entrySetView = new DescendingEntrySetView());
/*      */     }
/*      */     
/* 2006 */     TreeMap.Entry<K, V> subLowest() { return absHighest(); }
/* 2007 */     TreeMap.Entry<K, V> subHighest() { return absLowest(); }
/* 2008 */     TreeMap.Entry<K, V> subCeiling(K param1K) { return absFloor(param1K); }
/* 2009 */     TreeMap.Entry<K, V> subHigher(K param1K) { return absLower(param1K); }
/* 2010 */     TreeMap.Entry<K, V> subFloor(K param1K) { return absCeiling(param1K); } TreeMap.Entry<K, V> subLower(K param1K) {
/* 2011 */       return absHigher(param1K);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class SubMap
/*      */     extends AbstractMap<K, V>
/*      */     implements SortedMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -6520786458950516097L;
/*      */     
/*      */     private boolean fromStart = false;
/*      */     
/*      */     private boolean toEnd = false;
/*      */     private K fromKey;
/*      */     private K toKey;
/*      */     
/*      */     private Object readResolve() {
/* 2029 */       return new TreeMap.AscendingSubMap<>(TreeMap.this, this.fromStart, this.fromKey, true, this.toEnd, this.toKey, false);
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 2033 */       throw new InternalError();
/* 2034 */     } public K lastKey() { throw new InternalError(); }
/* 2035 */     public K firstKey() { throw new InternalError(); }
/* 2036 */     public SortedMap<K, V> subMap(K param1K1, K param1K2) { throw new InternalError(); }
/* 2037 */     public SortedMap<K, V> headMap(K param1K) { throw new InternalError(); }
/* 2038 */     public SortedMap<K, V> tailMap(K param1K) { throw new InternalError(); } public Comparator<? super K> comparator() {
/* 2039 */       throw new InternalError();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Entry<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     K key;
/*      */ 
/*      */     
/*      */     V value;
/*      */ 
/*      */     
/*      */     Entry<K, V> left;
/*      */ 
/*      */     
/*      */     Entry<K, V> right;
/*      */ 
/*      */     
/*      */     Entry<K, V> parent;
/*      */     
/*      */     boolean color = true;
/*      */ 
/*      */     
/*      */     Entry(K param1K, V param1V, Entry<K, V> param1Entry) {
/* 2066 */       this.key = param1K;
/* 2067 */       this.value = param1V;
/* 2068 */       this.parent = param1Entry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public K getKey() {
/* 2077 */       return this.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V getValue() {
/* 2086 */       return this.value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V setValue(V param1V) {
/* 2097 */       V v = this.value;
/* 2098 */       this.value = param1V;
/* 2099 */       return v;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2103 */       if (!(param1Object instanceof Map.Entry))
/* 2104 */         return false; 
/* 2105 */       Map.Entry entry = (Map.Entry)param1Object;
/*      */       
/* 2107 */       return (TreeMap.valEquals(this.key, entry.getKey()) && TreeMap.valEquals(this.value, entry.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 2111 */       boolean bool1 = (this.key == null) ? false : this.key.hashCode();
/* 2112 */       boolean bool2 = (this.value == null) ? false : this.value.hashCode();
/* 2113 */       return bool1 ^ bool2;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2117 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> getFirstEntry() {
/* 2126 */     Entry<K, V> entry = this.root;
/* 2127 */     if (entry != null)
/* 2128 */       while (entry.left != null)
/* 2129 */         entry = entry.left;  
/* 2130 */     return entry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> getLastEntry() {
/* 2138 */     Entry<K, V> entry = this.root;
/* 2139 */     if (entry != null)
/* 2140 */       while (entry.right != null)
/* 2141 */         entry = entry.right;  
/* 2142 */     return entry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> Entry<K, V> successor(Entry<K, V> paramEntry) {
/* 2149 */     if (paramEntry == null)
/* 2150 */       return null; 
/* 2151 */     if (paramEntry.right != null) {
/* 2152 */       Entry<K, V> entry = paramEntry.right;
/* 2153 */       while (entry.left != null)
/* 2154 */         entry = entry.left; 
/* 2155 */       return entry;
/*      */     } 
/* 2157 */     Entry<K, V> entry1 = paramEntry.parent;
/* 2158 */     Entry<K, V> entry2 = paramEntry;
/* 2159 */     while (entry1 != null && entry2 == entry1.right) {
/* 2160 */       entry2 = entry1;
/* 2161 */       entry1 = entry1.parent;
/*      */     } 
/* 2163 */     return entry1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> Entry<K, V> predecessor(Entry<K, V> paramEntry) {
/* 2171 */     if (paramEntry == null)
/* 2172 */       return null; 
/* 2173 */     if (paramEntry.left != null) {
/* 2174 */       Entry<K, V> entry = paramEntry.left;
/* 2175 */       while (entry.right != null)
/* 2176 */         entry = entry.right; 
/* 2177 */       return entry;
/*      */     } 
/* 2179 */     Entry<K, V> entry1 = paramEntry.parent;
/* 2180 */     Entry<K, V> entry2 = paramEntry;
/* 2181 */     while (entry1 != null && entry2 == entry1.left) {
/* 2182 */       entry2 = entry1;
/* 2183 */       entry1 = entry1.parent;
/*      */     } 
/* 2185 */     return entry1;
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
/*      */   private static <K, V> boolean colorOf(Entry<K, V> paramEntry) {
/* 2200 */     return (paramEntry == null) ? true : paramEntry.color;
/*      */   }
/*      */   
/*      */   private static <K, V> Entry<K, V> parentOf(Entry<K, V> paramEntry) {
/* 2204 */     return (paramEntry == null) ? null : paramEntry.parent;
/*      */   }
/*      */   
/*      */   private static <K, V> void setColor(Entry<K, V> paramEntry, boolean paramBoolean) {
/* 2208 */     if (paramEntry != null)
/* 2209 */       paramEntry.color = paramBoolean; 
/*      */   }
/*      */   
/*      */   private static <K, V> Entry<K, V> leftOf(Entry<K, V> paramEntry) {
/* 2213 */     return (paramEntry == null) ? null : paramEntry.left;
/*      */   }
/*      */   
/*      */   private static <K, V> Entry<K, V> rightOf(Entry<K, V> paramEntry) {
/* 2217 */     return (paramEntry == null) ? null : paramEntry.right;
/*      */   }
/*      */ 
/*      */   
/*      */   private void rotateLeft(Entry<K, V> paramEntry) {
/* 2222 */     if (paramEntry != null) {
/* 2223 */       Entry<K, V> entry = paramEntry.right;
/* 2224 */       paramEntry.right = entry.left;
/* 2225 */       if (entry.left != null)
/* 2226 */         entry.left.parent = paramEntry; 
/* 2227 */       entry.parent = paramEntry.parent;
/* 2228 */       if (paramEntry.parent == null) {
/* 2229 */         this.root = entry;
/* 2230 */       } else if (paramEntry.parent.left == paramEntry) {
/* 2231 */         paramEntry.parent.left = entry;
/*      */       } else {
/* 2233 */         paramEntry.parent.right = entry;
/* 2234 */       }  entry.left = paramEntry;
/* 2235 */       paramEntry.parent = entry;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void rotateRight(Entry<K, V> paramEntry) {
/* 2241 */     if (paramEntry != null) {
/* 2242 */       Entry<K, V> entry = paramEntry.left;
/* 2243 */       paramEntry.left = entry.right;
/* 2244 */       if (entry.right != null) entry.right.parent = paramEntry; 
/* 2245 */       entry.parent = paramEntry.parent;
/* 2246 */       if (paramEntry.parent == null)
/* 2247 */       { this.root = entry; }
/* 2248 */       else if (paramEntry.parent.right == paramEntry)
/* 2249 */       { paramEntry.parent.right = entry; }
/* 2250 */       else { paramEntry.parent.left = entry; }
/* 2251 */        entry.right = paramEntry;
/* 2252 */       paramEntry.parent = entry;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void fixAfterInsertion(Entry<K, V> paramEntry) {
/* 2258 */     paramEntry.color = false;
/*      */     
/* 2260 */     while (paramEntry != null && paramEntry != this.root && !paramEntry.parent.color) {
/* 2261 */       if (parentOf(paramEntry) == leftOf(parentOf(parentOf(paramEntry)))) {
/* 2262 */         Entry<?, ?> entry1 = rightOf(parentOf(parentOf(paramEntry)));
/* 2263 */         if (!colorOf(entry1)) {
/* 2264 */           setColor(parentOf(paramEntry), true);
/* 2265 */           setColor(entry1, true);
/* 2266 */           setColor(parentOf(parentOf(paramEntry)), false);
/* 2267 */           paramEntry = parentOf(parentOf(paramEntry)); continue;
/*      */         } 
/* 2269 */         if (paramEntry == rightOf(parentOf(paramEntry))) {
/* 2270 */           paramEntry = parentOf(paramEntry);
/* 2271 */           rotateLeft(paramEntry);
/*      */         } 
/* 2273 */         setColor(parentOf(paramEntry), true);
/* 2274 */         setColor(parentOf(parentOf(paramEntry)), false);
/* 2275 */         rotateRight(parentOf(parentOf(paramEntry)));
/*      */         continue;
/*      */       } 
/* 2278 */       Entry<?, ?> entry = leftOf(parentOf(parentOf(paramEntry)));
/* 2279 */       if (!colorOf(entry)) {
/* 2280 */         setColor(parentOf(paramEntry), true);
/* 2281 */         setColor(entry, true);
/* 2282 */         setColor(parentOf(parentOf(paramEntry)), false);
/* 2283 */         paramEntry = parentOf(parentOf(paramEntry)); continue;
/*      */       } 
/* 2285 */       if (paramEntry == leftOf(parentOf(paramEntry))) {
/* 2286 */         paramEntry = parentOf(paramEntry);
/* 2287 */         rotateRight(paramEntry);
/*      */       } 
/* 2289 */       setColor(parentOf(paramEntry), true);
/* 2290 */       setColor(parentOf(parentOf(paramEntry)), false);
/* 2291 */       rotateLeft(parentOf(parentOf(paramEntry)));
/*      */     } 
/*      */ 
/*      */     
/* 2295 */     this.root.color = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteEntry(Entry<K, V> paramEntry) {
/* 2302 */     this.modCount++;
/* 2303 */     this.size--;
/*      */ 
/*      */ 
/*      */     
/* 2307 */     if (paramEntry.left != null && paramEntry.right != null) {
/* 2308 */       Entry<K, V> entry1 = successor(paramEntry);
/* 2309 */       paramEntry.key = entry1.key;
/* 2310 */       paramEntry.value = entry1.value;
/* 2311 */       paramEntry = entry1;
/*      */     } 
/*      */ 
/*      */     
/* 2315 */     Entry<K, V> entry = (paramEntry.left != null) ? paramEntry.left : paramEntry.right;
/*      */     
/* 2317 */     if (entry != null) {
/*      */       
/* 2319 */       entry.parent = paramEntry.parent;
/* 2320 */       if (paramEntry.parent == null) {
/* 2321 */         this.root = entry;
/* 2322 */       } else if (paramEntry == paramEntry.parent.left) {
/* 2323 */         paramEntry.parent.left = entry;
/*      */       } else {
/* 2325 */         paramEntry.parent.right = entry;
/*      */       } 
/*      */       
/* 2328 */       paramEntry.left = paramEntry.right = paramEntry.parent = null;
/*      */ 
/*      */       
/* 2331 */       if (paramEntry.color == true)
/* 2332 */         fixAfterDeletion(entry); 
/* 2333 */     } else if (paramEntry.parent == null) {
/* 2334 */       this.root = null;
/*      */     } else {
/* 2336 */       if (paramEntry.color == true) {
/* 2337 */         fixAfterDeletion(paramEntry);
/*      */       }
/* 2339 */       if (paramEntry.parent != null) {
/* 2340 */         if (paramEntry == paramEntry.parent.left) {
/* 2341 */           paramEntry.parent.left = null;
/* 2342 */         } else if (paramEntry == paramEntry.parent.right) {
/* 2343 */           paramEntry.parent.right = null;
/* 2344 */         }  paramEntry.parent = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void fixAfterDeletion(Entry<K, V> paramEntry) {
/* 2351 */     while (paramEntry != this.root && colorOf(paramEntry) == true) {
/* 2352 */       if (paramEntry == leftOf(parentOf(paramEntry))) {
/* 2353 */         Entry<?, ?> entry1 = rightOf(parentOf(paramEntry));
/*      */         
/* 2355 */         if (!colorOf(entry1)) {
/* 2356 */           setColor(entry1, true);
/* 2357 */           setColor(parentOf(paramEntry), false);
/* 2358 */           rotateLeft(parentOf(paramEntry));
/* 2359 */           entry1 = rightOf(parentOf(paramEntry));
/*      */         } 
/*      */         
/* 2362 */         if (colorOf(leftOf(entry1)) == true && 
/* 2363 */           colorOf(rightOf(entry1)) == true) {
/* 2364 */           setColor(entry1, false);
/* 2365 */           paramEntry = parentOf(paramEntry); continue;
/*      */         } 
/* 2367 */         if (colorOf(rightOf(entry1)) == true) {
/* 2368 */           setColor(leftOf(entry1), true);
/* 2369 */           setColor(entry1, false);
/* 2370 */           rotateRight((Entry)entry1);
/* 2371 */           entry1 = rightOf(parentOf(paramEntry));
/*      */         } 
/* 2373 */         setColor(entry1, colorOf(parentOf(paramEntry)));
/* 2374 */         setColor(parentOf(paramEntry), true);
/* 2375 */         setColor(rightOf(entry1), true);
/* 2376 */         rotateLeft(parentOf(paramEntry));
/* 2377 */         paramEntry = this.root;
/*      */         continue;
/*      */       } 
/* 2380 */       Entry<?, ?> entry = leftOf(parentOf(paramEntry));
/*      */       
/* 2382 */       if (!colorOf(entry)) {
/* 2383 */         setColor(entry, true);
/* 2384 */         setColor(parentOf(paramEntry), false);
/* 2385 */         rotateRight(parentOf(paramEntry));
/* 2386 */         entry = leftOf(parentOf(paramEntry));
/*      */       } 
/*      */       
/* 2389 */       if (colorOf(rightOf(entry)) == true && 
/* 2390 */         colorOf(leftOf(entry)) == true) {
/* 2391 */         setColor(entry, false);
/* 2392 */         paramEntry = parentOf(paramEntry); continue;
/*      */       } 
/* 2394 */       if (colorOf(leftOf(entry)) == true) {
/* 2395 */         setColor(rightOf(entry), true);
/* 2396 */         setColor(entry, false);
/* 2397 */         rotateLeft((Entry)entry);
/* 2398 */         entry = leftOf(parentOf(paramEntry));
/*      */       } 
/* 2400 */       setColor(entry, colorOf(parentOf(paramEntry)));
/* 2401 */       setColor(parentOf(paramEntry), true);
/* 2402 */       setColor(leftOf(entry), true);
/* 2403 */       rotateRight(parentOf(paramEntry));
/* 2404 */       paramEntry = this.root;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2409 */     setColor(paramEntry, true);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 2429 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */     
/* 2432 */     paramObjectOutputStream.writeInt(this.size);
/*      */ 
/*      */     
/* 2435 */     for (Map.Entry<K, V> entry : entrySet()) {
/*      */       
/* 2437 */       paramObjectOutputStream.writeObject(entry.getKey());
/* 2438 */       paramObjectOutputStream.writeObject(entry.getValue());
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
/* 2449 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/* 2452 */     int i = paramObjectInputStream.readInt();
/*      */     
/* 2454 */     buildFromSorted(i, (Iterator<?>)null, paramObjectInputStream, (V)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void readTreeSet(int paramInt, ObjectInputStream paramObjectInputStream, V paramV) throws IOException, ClassNotFoundException {
/* 2460 */     buildFromSorted(paramInt, (Iterator<?>)null, paramObjectInputStream, paramV);
/*      */   }
/*      */ 
/*      */   
/*      */   void addAllForTreeSet(SortedSet<? extends K> paramSortedSet, V paramV) {
/*      */     
/* 2466 */     try { buildFromSorted(paramSortedSet.size(), paramSortedSet.iterator(), (ObjectInputStream)null, paramV); }
/* 2467 */     catch (IOException iOException) {  }
/* 2468 */     catch (ClassNotFoundException classNotFoundException) {}
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
/*      */   private void buildFromSorted(int paramInt, Iterator<?> paramIterator, ObjectInputStream paramObjectInputStream, V paramV) throws IOException, ClassNotFoundException {
/* 2507 */     this.size = paramInt;
/* 2508 */     this.root = buildFromSorted(0, 0, paramInt - 1, computeRedLevel(paramInt), paramIterator, paramObjectInputStream, paramV);
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
/*      */   private final Entry<K, V> buildFromSorted(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Iterator<?> paramIterator, ObjectInputStream paramObjectInputStream, V paramV) throws IOException, ClassNotFoundException {
/*      */     Object object;
/*      */     Object object1;
/* 2545 */     if (paramInt3 < paramInt2) return null;
/*      */     
/* 2547 */     int i = paramInt2 + paramInt3 >>> 1;
/*      */     
/* 2549 */     Entry<K, V> entry = null;
/* 2550 */     if (paramInt2 < i) {
/* 2551 */       entry = buildFromSorted(paramInt1 + 1, paramInt2, i - 1, paramInt4, paramIterator, paramObjectInputStream, paramV);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2557 */     if (paramIterator != null) {
/* 2558 */       if (paramV == null) {
/* 2559 */         Map.Entry entry2 = (Map.Entry)paramIterator.next();
/* 2560 */         object = entry2.getKey();
/* 2561 */         object1 = entry2.getValue();
/*      */       } else {
/* 2563 */         object = paramIterator.next();
/* 2564 */         V v = paramV;
/*      */       } 
/*      */     } else {
/* 2567 */       object = paramObjectInputStream.readObject();
/* 2568 */       object1 = (paramV != null) ? (Object)paramV : paramObjectInputStream.readObject();
/*      */     } 
/*      */     
/* 2571 */     Entry<Object, Object> entry1 = new Entry<>(object, object1, null);
/*      */ 
/*      */     
/* 2574 */     if (paramInt1 == paramInt4) {
/* 2575 */       entry1.color = false;
/*      */     }
/* 2577 */     if (entry != null) {
/* 2578 */       entry1.left = (Entry)entry;
/* 2579 */       entry.parent = (Entry)entry1;
/*      */     } 
/*      */     
/* 2582 */     if (i < paramInt3) {
/* 2583 */       Entry<K, V> entry2 = buildFromSorted(paramInt1 + 1, i + 1, paramInt3, paramInt4, paramIterator, paramObjectInputStream, paramV);
/*      */       
/* 2585 */       entry1.right = (Entry)entry2;
/* 2586 */       entry2.parent = (Entry)entry1;
/*      */     } 
/*      */     
/* 2589 */     return (Entry)entry1;
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
/*      */   private static int computeRedLevel(int paramInt) {
/* 2602 */     byte b = 0;
/* 2603 */     for (int i = paramInt - 1; i >= 0; i = i / 2 - 1)
/* 2604 */       b++; 
/* 2605 */     return b;
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
/*      */   static <K> Spliterator<K> keySpliteratorFor(NavigableMap<K, ?> paramNavigableMap) {
/* 2618 */     if (paramNavigableMap instanceof TreeMap) {
/* 2619 */       TreeMap treeMap = (TreeMap)paramNavigableMap;
/*      */       
/* 2621 */       return treeMap.keySpliterator();
/*      */     } 
/* 2623 */     if (paramNavigableMap instanceof DescendingSubMap) {
/* 2624 */       DescendingSubMap descendingSubMap = (DescendingSubMap)paramNavigableMap;
/*      */       
/* 2626 */       TreeMap treeMap = descendingSubMap.m;
/* 2627 */       if (descendingSubMap == treeMap.descendingMap) {
/* 2628 */         TreeMap treeMap1 = treeMap;
/*      */         
/* 2630 */         return treeMap1.descendingKeySpliterator();
/*      */       } 
/*      */     } 
/* 2633 */     NavigableSubMap navigableSubMap = (NavigableSubMap)paramNavigableMap;
/*      */     
/* 2635 */     return navigableSubMap.keySpliterator();
/*      */   }
/*      */   
/*      */   final Spliterator<K> keySpliterator() {
/* 2639 */     return new KeySpliterator<>(this, null, null, 0, -1, 0);
/*      */   }
/*      */   
/*      */   final Spliterator<K> descendingKeySpliterator() {
/* 2643 */     return new DescendingKeySpliterator<>(this, null, null, 0, -2, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TreeMapSpliterator<K, V>
/*      */   {
/*      */     final TreeMap<K, V> tree;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TreeMap.Entry<K, V> current;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TreeMap.Entry<K, V> fence;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int side;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int est;
/*      */ 
/*      */ 
/*      */     
/*      */     int expectedModCount;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TreeMapSpliterator(TreeMap<K, V> param1TreeMap, TreeMap.Entry<K, V> param1Entry1, TreeMap.Entry<K, V> param1Entry2, int param1Int1, int param1Int2, int param1Int3) {
/* 2682 */       this.tree = param1TreeMap;
/* 2683 */       this.current = param1Entry1;
/* 2684 */       this.fence = param1Entry2;
/* 2685 */       this.side = param1Int1;
/* 2686 */       this.est = param1Int2;
/* 2687 */       this.expectedModCount = param1Int3;
/*      */     }
/*      */     
/*      */     final int getEstimate() {
/*      */       int i;
/* 2692 */       if ((i = this.est) < 0) {
/* 2693 */         TreeMap<K, V> treeMap; if ((treeMap = this.tree) != null) {
/* 2694 */           this.current = (i == -1) ? treeMap.getFirstEntry() : treeMap.getLastEntry();
/* 2695 */           i = this.est = treeMap.size;
/* 2696 */           this.expectedModCount = treeMap.modCount;
/*      */         } else {
/*      */           
/* 2699 */           i = this.est = 0;
/*      */         } 
/* 2701 */       }  return i;
/*      */     }
/*      */     
/*      */     public final long estimateSize() {
/* 2705 */       return getEstimate();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends TreeMapSpliterator<K, V>
/*      */     implements Spliterator<K>
/*      */   {
/*      */     KeySpliterator(TreeMap<K, V> param1TreeMap, TreeMap.Entry<K, V> param1Entry1, TreeMap.Entry<K, V> param1Entry2, int param1Int1, int param1Int2, int param1Int3) {
/* 2715 */       super(param1TreeMap, param1Entry1, param1Entry2, param1Int1, param1Int2, param1Int3);
/*      */     }
/*      */     
/*      */     public KeySpliterator<K, V> trySplit() {
/* 2719 */       if (this.est < 0)
/* 2720 */         getEstimate(); 
/* 2721 */       int i = this.side;
/* 2722 */       TreeMap.Entry<K, V> entry1 = this.current, entry2 = this.fence;
/*      */       
/* 2724 */       TreeMap.Entry<K, V> entry3 = (entry1 == null || entry1 == entry2) ? null : ((i == 0) ? this.tree.root : ((i > 0) ? entry1.right : ((i < 0 && entry2 != null) ? entry2.left : null)));
/*      */ 
/*      */ 
/*      */       
/* 2728 */       if (entry3 != null && entry3 != entry1 && entry3 != entry2 && this.tree
/* 2729 */         .compare(entry1.key, entry3.key) < 0) {
/* 2730 */         this.side = 1;
/* 2731 */         return new KeySpliterator(this.tree, entry1, this.current = entry3, -1, this.est >>>= 1, this.expectedModCount);
/*      */       } 
/*      */       
/* 2734 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> param1Consumer) {
/* 2738 */       if (param1Consumer == null)
/* 2739 */         throw new NullPointerException(); 
/* 2740 */       if (this.est < 0)
/* 2741 */         getEstimate(); 
/* 2742 */       TreeMap.Entry<K, V> entry1 = this.fence; TreeMap.Entry<K, V> entry2;
/* 2743 */       if ((entry2 = this.current) != null && entry2 != entry1) {
/* 2744 */         TreeMap.Entry<K, V> entry; this.current = entry1;
/*      */         do {
/* 2746 */           param1Consumer.accept(entry2.key);
/* 2747 */           if ((entry = entry2.right) != null) {
/* 2748 */             TreeMap.Entry<K, V> entry3; while ((entry3 = entry.left) != null) {
/* 2749 */               entry = entry3;
/*      */             }
/*      */           } else {
/* 2752 */             while ((entry = entry2.parent) != null && entry2 == entry.right)
/* 2753 */               entry2 = entry; 
/*      */           } 
/* 2755 */         } while ((entry2 = entry) != null && entry2 != entry1);
/* 2756 */         if (this.tree.modCount != this.expectedModCount) {
/* 2757 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> param1Consumer) {
/* 2763 */       if (param1Consumer == null)
/* 2764 */         throw new NullPointerException(); 
/* 2765 */       if (this.est < 0)
/* 2766 */         getEstimate();  TreeMap.Entry<K, V> entry;
/* 2767 */       if ((entry = this.current) == null || entry == this.fence)
/* 2768 */         return false; 
/* 2769 */       this.current = TreeMap.successor(entry);
/* 2770 */       param1Consumer.accept(entry.key);
/* 2771 */       if (this.tree.modCount != this.expectedModCount)
/* 2772 */         throw new ConcurrentModificationException(); 
/* 2773 */       return true;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 2777 */       return ((this.side == 0) ? 64 : 0) | 0x1 | 0x4 | 0x10;
/*      */     }
/*      */ 
/*      */     
/*      */     public final Comparator<? super K> getComparator() {
/* 2782 */       return this.tree.comparator;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class DescendingKeySpliterator<K, V>
/*      */     extends TreeMapSpliterator<K, V>
/*      */     implements Spliterator<K>
/*      */   {
/*      */     DescendingKeySpliterator(TreeMap<K, V> param1TreeMap, TreeMap.Entry<K, V> param1Entry1, TreeMap.Entry<K, V> param1Entry2, int param1Int1, int param1Int2, int param1Int3) {
/* 2793 */       super(param1TreeMap, param1Entry1, param1Entry2, param1Int1, param1Int2, param1Int3);
/*      */     }
/*      */     
/*      */     public DescendingKeySpliterator<K, V> trySplit() {
/* 2797 */       if (this.est < 0)
/* 2798 */         getEstimate(); 
/* 2799 */       int i = this.side;
/* 2800 */       TreeMap.Entry<K, V> entry1 = this.current, entry2 = this.fence;
/*      */       
/* 2802 */       TreeMap.Entry<K, V> entry3 = (entry1 == null || entry1 == entry2) ? null : ((i == 0) ? this.tree.root : ((i < 0) ? entry1.left : ((i > 0 && entry2 != null) ? entry2.right : null)));
/*      */ 
/*      */ 
/*      */       
/* 2806 */       if (entry3 != null && entry3 != entry1 && entry3 != entry2 && this.tree
/* 2807 */         .compare(entry1.key, entry3.key) > 0) {
/* 2808 */         this.side = 1;
/* 2809 */         return new DescendingKeySpliterator(this.tree, entry1, this.current = entry3, -1, this.est >>>= 1, this.expectedModCount);
/*      */       } 
/*      */       
/* 2812 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> param1Consumer) {
/* 2816 */       if (param1Consumer == null)
/* 2817 */         throw new NullPointerException(); 
/* 2818 */       if (this.est < 0)
/* 2819 */         getEstimate(); 
/* 2820 */       TreeMap.Entry<K, V> entry1 = this.fence; TreeMap.Entry<K, V> entry2;
/* 2821 */       if ((entry2 = this.current) != null && entry2 != entry1) {
/* 2822 */         TreeMap.Entry<K, V> entry; this.current = entry1;
/*      */         do {
/* 2824 */           param1Consumer.accept(entry2.key);
/* 2825 */           if ((entry = entry2.left) != null) {
/* 2826 */             TreeMap.Entry<K, V> entry3; while ((entry3 = entry.right) != null) {
/* 2827 */               entry = entry3;
/*      */             }
/*      */           } else {
/* 2830 */             while ((entry = entry2.parent) != null && entry2 == entry.left)
/* 2831 */               entry2 = entry; 
/*      */           } 
/* 2833 */         } while ((entry2 = entry) != null && entry2 != entry1);
/* 2834 */         if (this.tree.modCount != this.expectedModCount) {
/* 2835 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> param1Consumer) {
/* 2841 */       if (param1Consumer == null)
/* 2842 */         throw new NullPointerException(); 
/* 2843 */       if (this.est < 0)
/* 2844 */         getEstimate();  TreeMap.Entry<K, V> entry;
/* 2845 */       if ((entry = this.current) == null || entry == this.fence)
/* 2846 */         return false; 
/* 2847 */       this.current = TreeMap.predecessor(entry);
/* 2848 */       param1Consumer.accept(entry.key);
/* 2849 */       if (this.tree.modCount != this.expectedModCount)
/* 2850 */         throw new ConcurrentModificationException(); 
/* 2851 */       return true;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 2855 */       return ((this.side == 0) ? 64 : 0) | 0x1 | 0x10;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends TreeMapSpliterator<K, V>
/*      */     implements Spliterator<V>
/*      */   {
/*      */     ValueSpliterator(TreeMap<K, V> param1TreeMap, TreeMap.Entry<K, V> param1Entry1, TreeMap.Entry<K, V> param1Entry2, int param1Int1, int param1Int2, int param1Int3) {
/* 2866 */       super(param1TreeMap, param1Entry1, param1Entry2, param1Int1, param1Int2, param1Int3);
/*      */     }
/*      */     
/*      */     public ValueSpliterator<K, V> trySplit() {
/* 2870 */       if (this.est < 0)
/* 2871 */         getEstimate(); 
/* 2872 */       int i = this.side;
/* 2873 */       TreeMap.Entry<K, V> entry1 = this.current, entry2 = this.fence;
/*      */       
/* 2875 */       TreeMap.Entry<K, V> entry3 = (entry1 == null || entry1 == entry2) ? null : ((i == 0) ? this.tree.root : ((i > 0) ? entry1.right : ((i < 0 && entry2 != null) ? entry2.left : null)));
/*      */ 
/*      */ 
/*      */       
/* 2879 */       if (entry3 != null && entry3 != entry1 && entry3 != entry2 && this.tree
/* 2880 */         .compare(entry1.key, entry3.key) < 0) {
/* 2881 */         this.side = 1;
/* 2882 */         return new ValueSpliterator(this.tree, entry1, this.current = entry3, -1, this.est >>>= 1, this.expectedModCount);
/*      */       } 
/*      */       
/* 2885 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super V> param1Consumer) {
/* 2889 */       if (param1Consumer == null)
/* 2890 */         throw new NullPointerException(); 
/* 2891 */       if (this.est < 0)
/* 2892 */         getEstimate(); 
/* 2893 */       TreeMap.Entry<K, V> entry1 = this.fence; TreeMap.Entry<K, V> entry2;
/* 2894 */       if ((entry2 = this.current) != null && entry2 != entry1) {
/* 2895 */         TreeMap.Entry<K, V> entry; this.current = entry1;
/*      */         do {
/* 2897 */           param1Consumer.accept(entry2.value);
/* 2898 */           if ((entry = entry2.right) != null) {
/* 2899 */             TreeMap.Entry<K, V> entry3; while ((entry3 = entry.left) != null) {
/* 2900 */               entry = entry3;
/*      */             }
/*      */           } else {
/* 2903 */             while ((entry = entry2.parent) != null && entry2 == entry.right)
/* 2904 */               entry2 = entry; 
/*      */           } 
/* 2906 */         } while ((entry2 = entry) != null && entry2 != entry1);
/* 2907 */         if (this.tree.modCount != this.expectedModCount) {
/* 2908 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> param1Consumer) {
/* 2914 */       if (param1Consumer == null)
/* 2915 */         throw new NullPointerException(); 
/* 2916 */       if (this.est < 0)
/* 2917 */         getEstimate();  TreeMap.Entry<K, V> entry;
/* 2918 */       if ((entry = this.current) == null || entry == this.fence)
/* 2919 */         return false; 
/* 2920 */       this.current = TreeMap.successor(entry);
/* 2921 */       param1Consumer.accept(entry.value);
/* 2922 */       if (this.tree.modCount != this.expectedModCount)
/* 2923 */         throw new ConcurrentModificationException(); 
/* 2924 */       return true;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 2928 */       return ((this.side == 0) ? 64 : 0) | 0x10;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends TreeMapSpliterator<K, V>
/*      */     implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySpliterator(TreeMap<K, V> param1TreeMap, TreeMap.Entry<K, V> param1Entry1, TreeMap.Entry<K, V> param1Entry2, int param1Int1, int param1Int2, int param1Int3) {
/* 2938 */       super(param1TreeMap, param1Entry1, param1Entry2, param1Int1, param1Int2, param1Int3);
/*      */     }
/*      */     
/*      */     public EntrySpliterator<K, V> trySplit() {
/* 2942 */       if (this.est < 0)
/* 2943 */         getEstimate(); 
/* 2944 */       int i = this.side;
/* 2945 */       TreeMap.Entry<K, V> entry1 = this.current, entry2 = this.fence;
/*      */       
/* 2947 */       TreeMap.Entry<K, V> entry3 = (entry1 == null || entry1 == entry2) ? null : ((i == 0) ? this.tree.root : ((i > 0) ? entry1.right : ((i < 0 && entry2 != null) ? entry2.left : null)));
/*      */ 
/*      */ 
/*      */       
/* 2951 */       if (entry3 != null && entry3 != entry1 && entry3 != entry2 && this.tree
/* 2952 */         .compare(entry1.key, entry3.key) < 0) {
/* 2953 */         this.side = 1;
/* 2954 */         return new EntrySpliterator(this.tree, entry1, this.current = entry3, -1, this.est >>>= 1, this.expectedModCount);
/*      */       } 
/*      */       
/* 2957 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 2961 */       if (param1Consumer == null)
/* 2962 */         throw new NullPointerException(); 
/* 2963 */       if (this.est < 0)
/* 2964 */         getEstimate(); 
/* 2965 */       TreeMap.Entry<K, V> entry1 = this.fence; TreeMap.Entry<K, V> entry2;
/* 2966 */       if ((entry2 = this.current) != null && entry2 != entry1) {
/* 2967 */         TreeMap.Entry<K, V> entry; this.current = entry1;
/*      */         do {
/* 2969 */           param1Consumer.accept(entry2);
/* 2970 */           if ((entry = entry2.right) != null) {
/* 2971 */             TreeMap.Entry<K, V> entry3; while ((entry3 = entry.left) != null) {
/* 2972 */               entry = entry3;
/*      */             }
/*      */           } else {
/* 2975 */             while ((entry = entry2.parent) != null && entry2 == entry.right)
/* 2976 */               entry2 = entry; 
/*      */           } 
/* 2978 */         } while ((entry2 = entry) != null && entry2 != entry1);
/* 2979 */         if (this.tree.modCount != this.expectedModCount) {
/* 2980 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 2986 */       if (param1Consumer == null)
/* 2987 */         throw new NullPointerException(); 
/* 2988 */       if (this.est < 0)
/* 2989 */         getEstimate();  TreeMap.Entry<K, V> entry;
/* 2990 */       if ((entry = this.current) == null || entry == this.fence)
/* 2991 */         return false; 
/* 2992 */       this.current = TreeMap.successor(entry);
/* 2993 */       param1Consumer.accept(entry);
/* 2994 */       if (this.tree.modCount != this.expectedModCount)
/* 2995 */         throw new ConcurrentModificationException(); 
/* 2996 */       return true;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 3000 */       return ((this.side == 0) ? 64 : 0) | 0x1 | 0x4 | 0x10;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Comparator<Map.Entry<K, V>> getComparator() {
/* 3007 */       if (this.tree.comparator != null) {
/* 3008 */         return Map.Entry.comparingByKey(this.tree.comparator);
/*      */       }
/*      */       
/* 3011 */       return (param1Entry1, param1Entry2) -> {
/*      */           Comparable comparable = (Comparable)param1Entry1.getKey();
/*      */           return comparable.compareTo(param1Entry2.getKey());
/*      */         };
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/TreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */