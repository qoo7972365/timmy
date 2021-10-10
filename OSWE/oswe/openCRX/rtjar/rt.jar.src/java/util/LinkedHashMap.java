/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinkedHashMap<K, V>
/*     */   extends HashMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = 3801124242820219131L;
/*     */   transient Entry<K, V> head;
/*     */   transient Entry<K, V> tail;
/*     */   final boolean accessOrder;
/*     */   
/*     */   static class Entry<K, V>
/*     */     extends HashMap.Node<K, V>
/*     */   {
/*     */     Entry<K, V> before;
/*     */     Entry<K, V> after;
/*     */     
/*     */     Entry(int param1Int, K param1K, V param1V, HashMap.Node<K, V> param1Node) {
/* 195 */       super(param1Int, param1K, param1V, param1Node);
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
/*     */   private void linkNodeLast(Entry<K, V> paramEntry) {
/* 223 */     Entry<K, V> entry = this.tail;
/* 224 */     this.tail = paramEntry;
/* 225 */     if (entry == null) {
/* 226 */       this.head = paramEntry;
/*     */     } else {
/* 228 */       paramEntry.before = entry;
/* 229 */       entry.after = paramEntry;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void transferLinks(Entry<K, V> paramEntry1, Entry<K, V> paramEntry2) {
/* 236 */     Entry<K, V> entry1 = paramEntry2.before = paramEntry1.before;
/* 237 */     Entry<K, V> entry2 = paramEntry2.after = paramEntry1.after;
/* 238 */     if (entry1 == null) {
/* 239 */       this.head = paramEntry2;
/*     */     } else {
/* 241 */       entry1.after = paramEntry2;
/* 242 */     }  if (entry2 == null) {
/* 243 */       this.tail = paramEntry2;
/*     */     } else {
/* 245 */       entry2.before = paramEntry2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void reinitialize() {
/* 251 */     super.reinitialize();
/* 252 */     this.head = this.tail = null;
/*     */   }
/*     */   
/*     */   HashMap.Node<K, V> newNode(int paramInt, K paramK, V paramV, HashMap.Node<K, V> paramNode) {
/* 256 */     Entry<K, V> entry = new Entry<>(paramInt, paramK, paramV, paramNode);
/*     */     
/* 258 */     linkNodeLast(entry);
/* 259 */     return entry;
/*     */   }
/*     */   
/*     */   HashMap.Node<K, V> replacementNode(HashMap.Node<K, V> paramNode1, HashMap.Node<K, V> paramNode2) {
/* 263 */     Entry<K, V> entry1 = (Entry)paramNode1;
/* 264 */     Entry<K, V> entry2 = new Entry<>(entry1.hash, entry1.key, entry1.value, paramNode2);
/*     */     
/* 266 */     transferLinks(entry1, entry2);
/* 267 */     return entry2;
/*     */   }
/*     */   
/*     */   HashMap.TreeNode<K, V> newTreeNode(int paramInt, K paramK, V paramV, HashMap.Node<K, V> paramNode) {
/* 271 */     HashMap.TreeNode<K, V> treeNode = new HashMap.TreeNode<>(paramInt, paramK, paramV, paramNode);
/* 272 */     linkNodeLast(treeNode);
/* 273 */     return treeNode;
/*     */   }
/*     */   
/*     */   HashMap.TreeNode<K, V> replacementTreeNode(HashMap.Node<K, V> paramNode1, HashMap.Node<K, V> paramNode2) {
/* 277 */     Entry<K, V> entry = (Entry)paramNode1;
/* 278 */     HashMap.TreeNode<K, V> treeNode = new HashMap.TreeNode<>(entry.hash, entry.key, entry.value, paramNode2);
/* 279 */     transferLinks(entry, treeNode);
/* 280 */     return treeNode;
/*     */   }
/*     */   
/*     */   void afterNodeRemoval(HashMap.Node<K, V> paramNode) {
/* 284 */     Entry entry = (Entry)paramNode;
/* 285 */     Entry<K, V> entry1 = entry.before, entry2 = entry.after;
/* 286 */     entry.before = entry.after = null;
/* 287 */     if (entry1 == null) {
/* 288 */       this.head = entry2;
/*     */     } else {
/* 290 */       entry1.after = entry2;
/* 291 */     }  if (entry2 == null) {
/* 292 */       this.tail = entry1;
/*     */     } else {
/* 294 */       entry2.before = entry1;
/*     */     } 
/*     */   }
/*     */   void afterNodeInsertion(boolean paramBoolean) {
/*     */     Entry<K, V> entry;
/* 299 */     if (paramBoolean && (entry = this.head) != null && removeEldestEntry(entry)) {
/* 300 */       K k = entry.key;
/* 301 */       removeNode(hash(k), k, (Object)null, false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   void afterNodeAccess(HashMap.Node<K, V> paramNode) {
/*     */     Entry<K, V> entry;
/* 307 */     if (this.accessOrder && (entry = this.tail) != paramNode) {
/* 308 */       Entry<K, V> entry1 = (Entry)paramNode;
/* 309 */       Entry<K, V> entry2 = entry1.before, entry3 = entry1.after;
/* 310 */       entry1.after = null;
/* 311 */       if (entry2 == null) {
/* 312 */         this.head = entry3;
/*     */       } else {
/* 314 */         entry2.after = entry3;
/* 315 */       }  if (entry3 != null) {
/* 316 */         entry3.before = entry2;
/*     */       } else {
/* 318 */         entry = entry2;
/* 319 */       }  if (entry == null) {
/* 320 */         this.head = entry1;
/*     */       } else {
/* 322 */         entry1.before = entry;
/* 323 */         entry.after = entry1;
/*     */       } 
/* 325 */       this.tail = entry1;
/* 326 */       this.modCount++;
/*     */     } 
/*     */   }
/*     */   
/*     */   void internalWriteEntries(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 331 */     for (Entry<K, V> entry = this.head; entry != null; entry = entry.after) {
/* 332 */       paramObjectOutputStream.writeObject(entry.key);
/* 333 */       paramObjectOutputStream.writeObject(entry.value);
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
/*     */   public LinkedHashMap(int paramInt, float paramFloat) {
/* 347 */     super(paramInt, paramFloat);
/* 348 */     this.accessOrder = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedHashMap(int paramInt) {
/* 359 */     super(paramInt);
/* 360 */     this.accessOrder = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedHashMap() {
/* 369 */     this.accessOrder = false;
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
/*     */   public LinkedHashMap(Map<? extends K, ? extends V> paramMap) {
/* 383 */     this.accessOrder = false;
/* 384 */     putMapEntries(paramMap, false);
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
/*     */   public LinkedHashMap(int paramInt, float paramFloat, boolean paramBoolean) {
/* 401 */     super(paramInt, paramFloat);
/* 402 */     this.accessOrder = paramBoolean;
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
/*     */   public boolean containsValue(Object paramObject) {
/* 415 */     for (Entry<K, V> entry = this.head; entry != null; entry = entry.after) {
/* 416 */       V v = entry.value;
/* 417 */       if (v == paramObject || (paramObject != null && paramObject.equals(v)))
/* 418 */         return true; 
/*     */     } 
/* 420 */     return false;
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
/*     */   public V get(Object paramObject) {
/*     */     HashMap.Node<K, V> node;
/* 440 */     if ((node = getNode(hash(paramObject), paramObject)) == null)
/* 441 */       return null; 
/* 442 */     if (this.accessOrder)
/* 443 */       afterNodeAccess(node); 
/* 444 */     return node.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getOrDefault(Object paramObject, V paramV) {
/*     */     HashMap.Node<K, V> node;
/* 452 */     if ((node = getNode(hash(paramObject), paramObject)) == null)
/* 453 */       return paramV; 
/* 454 */     if (this.accessOrder)
/* 455 */       afterNodeAccess(node); 
/* 456 */     return node.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 463 */     super.clear();
/* 464 */     this.head = this.tail = null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean removeEldestEntry(Map.Entry<K, V> paramEntry) {
/* 509 */     return false;
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
/*     */   public Set<K> keySet() {
/* 531 */     Set<K> set = this.keySet;
/* 532 */     if (set == null) {
/* 533 */       set = new LinkedKeySet();
/* 534 */       this.keySet = set;
/*     */     } 
/* 536 */     return set;
/*     */   }
/*     */   
/*     */   final class LinkedKeySet extends AbstractSet<K> {
/* 540 */     public final int size() { return LinkedHashMap.this.size; } public final void clear() {
/* 541 */       LinkedHashMap.this.clear();
/*     */     } public final Iterator<K> iterator() {
/* 543 */       return new LinkedHashMap.LinkedKeyIterator();
/*     */     } public final boolean contains(Object param1Object) {
/* 545 */       return LinkedHashMap.this.containsKey(param1Object);
/*     */     } public final boolean remove(Object param1Object) {
/* 547 */       return (LinkedHashMap.this.removeNode(HashMap.hash(param1Object), param1Object, (Object)null, false, true) != null);
/*     */     }
/*     */     public final Spliterator<K> spliterator() {
/* 550 */       return Spliterators.spliterator(this, 81);
/*     */     }
/*     */ 
/*     */     
/*     */     public final void forEach(Consumer<? super K> param1Consumer) {
/* 555 */       if (param1Consumer == null)
/* 556 */         throw new NullPointerException(); 
/* 557 */       int i = LinkedHashMap.this.modCount;
/* 558 */       for (LinkedHashMap.Entry entry = LinkedHashMap.this.head; entry != null; entry = entry.after)
/* 559 */         param1Consumer.accept(entry.key); 
/* 560 */       if (LinkedHashMap.this.modCount != i) {
/* 561 */         throw new ConcurrentModificationException();
/*     */       }
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
/*     */   public Collection<V> values() {
/* 584 */     Collection<V> collection = this.values;
/* 585 */     if (collection == null) {
/* 586 */       collection = new LinkedValues();
/* 587 */       this.values = collection;
/*     */     } 
/* 589 */     return collection;
/*     */   }
/*     */   
/*     */   final class LinkedValues extends AbstractCollection<V> {
/* 593 */     public final int size() { return LinkedHashMap.this.size; } public final void clear() {
/* 594 */       LinkedHashMap.this.clear();
/*     */     } public final Iterator<V> iterator() {
/* 596 */       return new LinkedHashMap.LinkedValueIterator();
/*     */     } public final boolean contains(Object param1Object) {
/* 598 */       return LinkedHashMap.this.containsValue(param1Object);
/*     */     } public final Spliterator<V> spliterator() {
/* 600 */       return Spliterators.spliterator(this, 80);
/*     */     }
/*     */     
/*     */     public final void forEach(Consumer<? super V> param1Consumer) {
/* 604 */       if (param1Consumer == null)
/* 605 */         throw new NullPointerException(); 
/* 606 */       int i = LinkedHashMap.this.modCount;
/* 607 */       for (LinkedHashMap.Entry entry = LinkedHashMap.this.head; entry != null; entry = entry.after)
/* 608 */         param1Consumer.accept(entry.value); 
/* 609 */       if (LinkedHashMap.this.modCount != i) {
/* 610 */         throw new ConcurrentModificationException();
/*     */       }
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
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/*     */     Set<Map.Entry<K, V>> set;
/* 635 */     return ((set = this.entrySet) == null) ? (this.entrySet = new LinkedEntrySet()) : set;
/*     */   }
/*     */   
/*     */   final class LinkedEntrySet extends AbstractSet<Map.Entry<K, V>> {
/* 639 */     public final int size() { return LinkedHashMap.this.size; } public final void clear() {
/* 640 */       LinkedHashMap.this.clear();
/*     */     } public final Iterator<Map.Entry<K, V>> iterator() {
/* 642 */       return new LinkedHashMap.LinkedEntryIterator();
/*     */     }
/*     */     public final boolean contains(Object param1Object) {
/* 645 */       if (!(param1Object instanceof Map.Entry))
/* 646 */         return false; 
/* 647 */       Map.Entry entry = (Map.Entry)param1Object;
/* 648 */       Object object = entry.getKey();
/* 649 */       HashMap.Node node = LinkedHashMap.this.getNode(HashMap.hash(object), object);
/* 650 */       return (node != null && node.equals(entry));
/*     */     }
/*     */     public final boolean remove(Object param1Object) {
/* 653 */       if (param1Object instanceof Map.Entry) {
/* 654 */         Map.Entry entry = (Map.Entry)param1Object;
/* 655 */         Object object1 = entry.getKey();
/* 656 */         Object object2 = entry.getValue();
/* 657 */         return (LinkedHashMap.this.removeNode(HashMap.hash(object1), object1, object2, true, true) != null);
/*     */       } 
/* 659 */       return false;
/*     */     }
/*     */     public final Spliterator<Map.Entry<K, V>> spliterator() {
/* 662 */       return Spliterators.spliterator(this, 81);
/*     */     }
/*     */ 
/*     */     
/*     */     public final void forEach(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 667 */       if (param1Consumer == null)
/* 668 */         throw new NullPointerException(); 
/* 669 */       int i = LinkedHashMap.this.modCount;
/* 670 */       for (LinkedHashMap.Entry<K, V> entry = LinkedHashMap.this.head; entry != null; entry = entry.after)
/* 671 */         param1Consumer.accept(entry); 
/* 672 */       if (LinkedHashMap.this.modCount != i) {
/* 673 */         throw new ConcurrentModificationException();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 680 */     if (paramBiConsumer == null)
/* 681 */       throw new NullPointerException(); 
/* 682 */     int i = this.modCount;
/* 683 */     for (Entry<K, V> entry = this.head; entry != null; entry = entry.after)
/* 684 */       paramBiConsumer.accept(entry.key, entry.value); 
/* 685 */     if (this.modCount != i)
/* 686 */       throw new ConcurrentModificationException(); 
/*     */   }
/*     */   
/*     */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 690 */     if (paramBiFunction == null)
/* 691 */       throw new NullPointerException(); 
/* 692 */     int i = this.modCount;
/* 693 */     for (Entry<K, V> entry = this.head; entry != null; entry = entry.after)
/* 694 */       entry.value = paramBiFunction.apply(entry.key, entry.value); 
/* 695 */     if (this.modCount != i) {
/* 696 */       throw new ConcurrentModificationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract class LinkedHashIterator
/*     */   {
/* 707 */     LinkedHashMap.Entry<K, V> next = LinkedHashMap.this.head;
/* 708 */     int expectedModCount = LinkedHashMap.this.modCount;
/* 709 */     LinkedHashMap.Entry<K, V> current = null;
/*     */ 
/*     */     
/*     */     public final boolean hasNext() {
/* 713 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     final LinkedHashMap.Entry<K, V> nextNode() {
/* 717 */       LinkedHashMap.Entry<K, V> entry = this.next;
/* 718 */       if (LinkedHashMap.this.modCount != this.expectedModCount)
/* 719 */         throw new ConcurrentModificationException(); 
/* 720 */       if (entry == null)
/* 721 */         throw new NoSuchElementException(); 
/* 722 */       this.current = entry;
/* 723 */       this.next = entry.after;
/* 724 */       return entry;
/*     */     }
/*     */     
/*     */     public final void remove() {
/* 728 */       LinkedHashMap.Entry<K, V> entry = this.current;
/* 729 */       if (entry == null)
/* 730 */         throw new IllegalStateException(); 
/* 731 */       if (LinkedHashMap.this.modCount != this.expectedModCount)
/* 732 */         throw new ConcurrentModificationException(); 
/* 733 */       this.current = null;
/* 734 */       K k = entry.key;
/* 735 */       LinkedHashMap.this.removeNode(HashMap.hash(k), k, (Object)null, false, false);
/* 736 */       this.expectedModCount = LinkedHashMap.this.modCount;
/*     */     }
/*     */   }
/*     */   
/*     */   final class LinkedKeyIterator extends LinkedHashIterator implements Iterator<K> {
/*     */     public final K next() {
/* 742 */       return nextNode().getKey();
/*     */     }
/*     */   }
/*     */   
/*     */   final class LinkedValueIterator extends LinkedHashIterator implements Iterator<V> { public final V next() {
/* 747 */       return (nextNode()).value;
/*     */     } }
/*     */   
/*     */   final class LinkedEntryIterator extends LinkedHashIterator implements Iterator<Map.Entry<K, V>> {
/*     */     public final Map.Entry<K, V> next() {
/* 752 */       return nextNode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/LinkedHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */