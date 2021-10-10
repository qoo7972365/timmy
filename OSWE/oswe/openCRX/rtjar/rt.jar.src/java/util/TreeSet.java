/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TreeSet<E>
/*     */   extends AbstractSet<E>
/*     */   implements NavigableSet<E>, Cloneable, Serializable
/*     */ {
/*     */   private transient NavigableMap<E, Object> m;
/* 101 */   private static final Object PRESENT = new Object();
/*     */   
/*     */   private static final long serialVersionUID = -2479143000061671589L;
/*     */ 
/*     */   
/*     */   TreeSet(NavigableMap<E, Object> paramNavigableMap) {
/* 107 */     this.m = paramNavigableMap;
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
/*     */   public TreeSet() {
/* 124 */     this(new TreeMap<>());
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
/*     */   public TreeSet(Comparator<? super E> paramComparator) {
/* 141 */     this(new TreeMap<>(paramComparator));
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
/*     */   public TreeSet(Collection<? extends E> paramCollection) {
/* 159 */     this();
/* 160 */     addAll(paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeSet(SortedSet<E> paramSortedSet) {
/* 171 */     this(paramSortedSet.comparator());
/* 172 */     addAll(paramSortedSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 181 */     return this.m.navigableKeySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> descendingIterator() {
/* 191 */     return this.m.descendingKeySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NavigableSet<E> descendingSet() {
/* 198 */     return new TreeSet(this.m.descendingMap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 207 */     return this.m.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 216 */     return this.m.isEmpty();
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
/*     */   public boolean contains(Object paramObject) {
/* 234 */     return this.m.containsKey(paramObject);
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
/*     */   public boolean add(E paramE) {
/* 255 */     return (this.m.put(paramE, PRESENT) == null);
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
/*     */   public boolean remove(Object paramObject) {
/* 276 */     return (this.m.remove(paramObject) == PRESENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 284 */     this.m.clear();
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
/* 300 */     if (this.m.size() == 0 && paramCollection.size() > 0 && paramCollection instanceof SortedSet && this.m instanceof TreeMap) {
/*     */ 
/*     */       
/* 303 */       SortedSet sortedSet = (SortedSet)paramCollection;
/* 304 */       TreeMap treeMap = (TreeMap)this.m;
/* 305 */       Comparator comparator1 = sortedSet.comparator();
/* 306 */       Comparator comparator2 = treeMap.comparator();
/* 307 */       if (comparator1 == comparator2 || (comparator1 != null && comparator1.equals(comparator2))) {
/* 308 */         treeMap.addAllForTreeSet(sortedSet, PRESENT);
/* 309 */         return true;
/*     */       } 
/*     */     } 
/* 312 */     return super.addAll(paramCollection);
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
/*     */   public NavigableSet<E> subSet(E paramE1, boolean paramBoolean1, E paramE2, boolean paramBoolean2) {
/* 325 */     return new TreeSet(this.m.subMap(paramE1, paramBoolean1, paramE2, paramBoolean2));
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
/*     */   public NavigableSet<E> headSet(E paramE, boolean paramBoolean) {
/* 338 */     return new TreeSet(this.m.headMap(paramE, paramBoolean));
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
/*     */   public NavigableSet<E> tailSet(E paramE, boolean paramBoolean) {
/* 350 */     return new TreeSet(this.m.tailMap(paramE, paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedSet<E> subSet(E paramE1, E paramE2) {
/* 361 */     return subSet(paramE1, true, paramE2, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedSet<E> headSet(E paramE) {
/* 372 */     return headSet(paramE, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedSet<E> tailSet(E paramE) {
/* 383 */     return tailSet(paramE, true);
/*     */   }
/*     */   
/*     */   public Comparator<? super E> comparator() {
/* 387 */     return this.m.comparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E first() {
/* 394 */     return this.m.firstKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E last() {
/* 401 */     return this.m.lastKey();
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
/*     */   public E lower(E paramE) {
/* 414 */     return this.m.lowerKey(paramE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E floor(E paramE) {
/* 425 */     return this.m.floorKey(paramE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E ceiling(E paramE) {
/* 436 */     return this.m.ceilingKey(paramE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E higher(E paramE) {
/* 447 */     return this.m.higherKey(paramE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pollFirst() {
/* 454 */     Map.Entry<E, Object> entry = this.m.pollFirstEntry();
/* 455 */     return (entry == null) ? null : entry.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pollLast() {
/* 462 */     Map.Entry<E, Object> entry = this.m.pollLastEntry();
/* 463 */     return (entry == null) ? null : entry.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     TreeSet treeSet;
/*     */     try {
/* 476 */       treeSet = (TreeSet)super.clone();
/* 477 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 478 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */     
/* 481 */     treeSet.m = new TreeMap<>(this.m);
/* 482 */     return treeSet;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 500 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 503 */     paramObjectOutputStream.writeObject(this.m.comparator());
/*     */ 
/*     */     
/* 506 */     paramObjectOutputStream.writeInt(this.m.size());
/*     */ 
/*     */     
/* 509 */     for (E e : this.m.keySet()) {
/* 510 */       paramObjectOutputStream.writeObject(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 520 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */ 
/*     */     
/* 524 */     Comparator<?> comparator = (Comparator)paramObjectInputStream.readObject();
/*     */ 
/*     */     
/* 527 */     TreeMap<Object, Object> treeMap = new TreeMap<>(comparator);
/* 528 */     this.m = (NavigableMap)treeMap;
/*     */ 
/*     */     
/* 531 */     int i = paramObjectInputStream.readInt();
/*     */     
/* 533 */     treeMap.readTreeSet(i, paramObjectInputStream, PRESENT);
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
/*     */   public Spliterator<E> spliterator() {
/* 556 */     return TreeMap.keySpliteratorFor(this.m);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/TreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */