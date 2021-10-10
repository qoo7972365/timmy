/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.invoke.SerializedLambda;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NavigableMap;
/*      */ import java.util.NavigableSet;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConcurrentSkipListMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements ConcurrentNavigableMap<K, V>, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -8627078645895051609L;
/*  363 */   private static final Object BASE_HEADER = new Object();
/*      */ 
/*      */   
/*      */   private volatile transient HeadIndex<K, V> head;
/*      */ 
/*      */   
/*      */   final Comparator<? super K> comparator;
/*      */   
/*      */   private transient KeySet<K> keySet;
/*      */   
/*      */   private transient EntrySet<K, V> entrySet;
/*      */   
/*      */   private transient Values<V> values;
/*      */   
/*      */   private transient ConcurrentNavigableMap<K, V> descendingMap;
/*      */   
/*      */   private static final int EQ = 1;
/*      */   
/*      */   private static final int LT = 2;
/*      */   
/*      */   private static final int GT = 0;
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */   private static final long headOffset;
/*      */   
/*      */   private static final long SECONDARY;
/*      */ 
/*      */   
/*      */   private void initialize() {
/*  393 */     this.keySet = null;
/*  394 */     this.entrySet = null;
/*  395 */     this.values = null;
/*  396 */     this.descendingMap = null;
/*  397 */     this.head = new HeadIndex<>(new Node<>(null, BASE_HEADER, null), null, null, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean casHead(HeadIndex<K, V> paramHeadIndex1, HeadIndex<K, V> paramHeadIndex2) {
/*  405 */     return UNSAFE.compareAndSwapObject(this, headOffset, paramHeadIndex1, paramHeadIndex2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Node<K, V>
/*      */   {
/*      */     final K key;
/*      */     
/*      */     volatile Object value;
/*      */     
/*      */     volatile Node<K, V> next;
/*      */     
/*      */     private static final Unsafe UNSAFE;
/*      */     
/*      */     private static final long valueOffset;
/*      */     
/*      */     private static final long nextOffset;
/*      */ 
/*      */     
/*      */     Node(K param1K, Object param1Object, Node<K, V> param1Node) {
/*  426 */       this.key = param1K;
/*  427 */       this.value = param1Object;
/*  428 */       this.next = param1Node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Node(Node<K, V> param1Node) {
/*  439 */       this.key = null;
/*  440 */       this.value = this;
/*  441 */       this.next = param1Node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean casValue(Object param1Object1, Object param1Object2) {
/*  448 */       return UNSAFE.compareAndSwapObject(this, valueOffset, param1Object1, param1Object2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean casNext(Node<K, V> param1Node1, Node<K, V> param1Node2) {
/*  455 */       return UNSAFE.compareAndSwapObject(this, nextOffset, param1Node1, param1Node2);
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
/*      */     boolean isMarker() {
/*  468 */       return (this.value == this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isBaseHeader() {
/*  476 */       return (this.value == ConcurrentSkipListMap.BASE_HEADER);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean appendMarker(Node<K, V> param1Node) {
/*  485 */       return casNext(param1Node, new Node(param1Node));
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
/*      */ 
/*      */ 
/*      */     
/*      */     void helpDelete(Node<K, V> param1Node1, Node<K, V> param1Node2) {
/*  501 */       if (param1Node2 == this.next && this == param1Node1.next) {
/*  502 */         if (param1Node2 == null || param1Node2.value != param1Node2) {
/*  503 */           casNext(param1Node2, new Node(param1Node2));
/*      */         } else {
/*  505 */           param1Node1.casNext(this, param1Node2.next);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     V getValidValue() {
/*  516 */       Object object = this.value;
/*  517 */       if (object == this || object == ConcurrentSkipListMap.BASE_HEADER)
/*  518 */         return null; 
/*  519 */       return (V)object;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AbstractMap.SimpleImmutableEntry<K, V> createSnapshot() {
/*  529 */       Object object1 = this.value;
/*  530 */       if (object1 == null || object1 == this || object1 == ConcurrentSkipListMap.BASE_HEADER)
/*  531 */         return null; 
/*  532 */       Object object2 = object1;
/*  533 */       return new AbstractMap.SimpleImmutableEntry<>(this.key, (V)object2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/*  544 */         UNSAFE = Unsafe.getUnsafe();
/*  545 */         Class<Node> clazz = Node.class;
/*      */         
/*  547 */         valueOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("value"));
/*      */         
/*  549 */         nextOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
/*  550 */       } catch (Exception exception) {
/*  551 */         throw new Error(exception);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Index<K, V>
/*      */   {
/*      */     final ConcurrentSkipListMap.Node<K, V> node;
/*      */ 
/*      */     
/*      */     final Index<K, V> down;
/*      */ 
/*      */     
/*      */     volatile Index<K, V> right;
/*      */     
/*      */     private static final Unsafe UNSAFE;
/*      */     
/*      */     private static final long rightOffset;
/*      */ 
/*      */     
/*      */     Index(ConcurrentSkipListMap.Node<K, V> param1Node, Index<K, V> param1Index1, Index<K, V> param1Index2) {
/*  574 */       this.node = param1Node;
/*  575 */       this.down = param1Index1;
/*  576 */       this.right = param1Index2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean casRight(Index<K, V> param1Index1, Index<K, V> param1Index2) {
/*  583 */       return UNSAFE.compareAndSwapObject(this, rightOffset, param1Index1, param1Index2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean indexesDeletedNode() {
/*  591 */       return (this.node.value == null);
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
/*      */     final boolean link(Index<K, V> param1Index1, Index<K, V> param1Index2) {
/*  603 */       ConcurrentSkipListMap.Node<K, V> node = this.node;
/*  604 */       param1Index2.right = param1Index1;
/*  605 */       return (node.value != null && casRight(param1Index1, param1Index2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean unlink(Index<K, V> param1Index) {
/*  616 */       return (this.node.value != null && casRight(param1Index, param1Index.right));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/*  624 */         UNSAFE = Unsafe.getUnsafe();
/*  625 */         Class<Index> clazz = Index.class;
/*      */         
/*  627 */         rightOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("right"));
/*  628 */       } catch (Exception exception) {
/*  629 */         throw new Error(exception);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class HeadIndex<K, V>
/*      */     extends Index<K, V>
/*      */   {
/*      */     final int level;
/*      */ 
/*      */     
/*      */     HeadIndex(ConcurrentSkipListMap.Node<K, V> param1Node, ConcurrentSkipListMap.Index<K, V> param1Index1, ConcurrentSkipListMap.Index<K, V> param1Index2, int param1Int) {
/*  642 */       super(param1Node, param1Index1, param1Index2);
/*  643 */       this.level = param1Int;
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
/*      */   static final int cpr(Comparator<Object> paramComparator, Object paramObject1, Object paramObject2) {
/*  655 */     return (paramComparator != null) ? paramComparator.compare(paramObject1, paramObject2) : ((Comparable<Object>)paramObject1).compareTo(paramObject2);
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
/*      */   private Node<K, V> findPredecessor(Object paramObject, Comparator<? super K> paramComparator) {
/*  669 */     if (paramObject == null)
/*  670 */       throw new NullPointerException(); 
/*      */     label22: while (true) {
/*  672 */       HeadIndex<K, V> headIndex = this.head; Index<K, V> index = headIndex.right; while (true) {
/*  673 */         if (index != null) {
/*  674 */           Node<K, V> node = index.node;
/*  675 */           K k = node.key;
/*  676 */           if (node.value == null) {
/*  677 */             if (!headIndex.unlink(index))
/*      */               continue label22; 
/*  679 */             index = headIndex.right;
/*      */             continue;
/*      */           } 
/*  682 */           if (cpr(paramComparator, paramObject, k) > 0) {
/*  683 */             index1 = index;
/*  684 */             index = index.right; continue;
/*      */           } 
/*      */         } 
/*      */         Index<K, V> index2;
/*  688 */         if ((index2 = index1.down) == null)
/*  689 */           return index1.node; 
/*  690 */         Index<K, V> index1 = index2;
/*  691 */         index = index2.right;
/*      */       } 
/*      */       break;
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
/*      */   private Node<K, V> findNode(Object paramObject) {
/*  741 */     if (paramObject == null)
/*  742 */       throw new NullPointerException(); 
/*  743 */     Comparator<? super K> comparator = this.comparator;
/*      */     label31: while (true) {
/*  745 */       Node<K, V> node1 = findPredecessor(paramObject, comparator), node2 = node1.next;
/*      */       
/*  747 */       while (node2 != null) {
/*      */         
/*  749 */         Node<K, V> node = node2.next;
/*  750 */         if (node2 != node1.next)
/*      */           continue label31;  Object object;
/*  752 */         if ((object = node2.value) == null) {
/*  753 */           node2.helpDelete(node1, node);
/*      */           continue label31;
/*      */         } 
/*  756 */         if (node1.value != null) { if (object == node2)
/*      */             continue label31;  int i;
/*  758 */           if ((i = cpr(comparator, paramObject, node2.key)) == 0)
/*  759 */             return node2; 
/*  760 */           if (i < 0)
/*      */             break; 
/*  762 */           node1 = node2;
/*  763 */           node2 = node; continue; }
/*      */          continue label31;
/*      */       }  break;
/*  766 */     }  return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private V doGet(Object paramObject) {
/*  777 */     if (paramObject == null)
/*  778 */       throw new NullPointerException(); 
/*  779 */     Comparator<? super K> comparator = this.comparator;
/*      */     label31: while (true) {
/*  781 */       Node<K, V> node1 = findPredecessor(paramObject, comparator), node2 = node1.next;
/*      */       
/*  783 */       while (node2 != null) {
/*      */         
/*  785 */         Node<K, V> node = node2.next;
/*  786 */         if (node2 != node1.next)
/*      */           continue label31;  Object object;
/*  788 */         if ((object = node2.value) == null) {
/*  789 */           node2.helpDelete(node1, node);
/*      */           continue label31;
/*      */         } 
/*  792 */         if (node1.value != null) { if (object == node2)
/*      */             continue label31;  int i;
/*  794 */           if ((i = cpr(comparator, paramObject, node2.key)) == 0) {
/*  795 */             return (V)object;
/*      */           }
/*      */           
/*  798 */           if (i < 0)
/*      */             break; 
/*  800 */           node1 = node2;
/*  801 */           node2 = node; continue; }
/*      */          continue label31;
/*      */       }  break;
/*  804 */     }  return null;
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
/*      */   private V doPut(K paramK, V paramV, boolean paramBoolean) {
/*      */     Node<K, V> node;
/*  819 */     if (paramK == null)
/*  820 */       throw new NullPointerException(); 
/*  821 */     Comparator<? super K> comparator = this.comparator;
/*      */     label101: while (true) {
/*  823 */       Node<K, V> node1 = findPredecessor(paramK, comparator), node2 = node1.next;
/*  824 */       while (node2 != null) {
/*      */         
/*  826 */         Node<K, V> node3 = node2.next;
/*  827 */         if (node2 != node1.next)
/*      */           continue label101;  Object object;
/*  829 */         if ((object = node2.value) == null) {
/*  830 */           node2.helpDelete(node1, node3);
/*      */           continue label101;
/*      */         } 
/*  833 */         if (node1.value != null) { if (object == node2)
/*      */             continue label101;  int j;
/*  835 */           if ((j = cpr(comparator, paramK, node2.key)) > 0) {
/*  836 */             node1 = node2;
/*  837 */             node2 = node3;
/*      */             continue;
/*      */           } 
/*  840 */           if (j == 0) {
/*  841 */             if (!paramBoolean) { if (node2.casValue(object, paramV))
/*  842 */                 return (V)object;  continue; }
/*      */             
/*      */             continue;
/*      */           } 
/*      */           break; }
/*      */         
/*      */         continue label101;
/*      */       } 
/*  850 */       node = new Node<>(paramK, paramV, node2);
/*  851 */       if (!node1.casNext(node2, node)) {
/*      */         continue;
/*      */       }
/*      */       
/*      */       break;
/*      */     } 
/*  857 */     int i = ThreadLocalRandom.nextSecondarySeed();
/*  858 */     if ((i & 0x80000001) == 0) {
/*  859 */       int j = 1;
/*  860 */       while (((i >>>= 1) & 0x1) != 0)
/*  861 */         j++; 
/*  862 */       Index<K, V> index = null;
/*  863 */       HeadIndex<K, V> headIndex = this.head; int k;
/*  864 */       if (j <= (k = headIndex.level)) {
/*  865 */         for (byte b = 1; b <= j; b++) {
/*  866 */           index = new Index<>(node, index, null);
/*      */         }
/*      */       } else {
/*  869 */         j = k + 1;
/*  870 */         Index[] arrayOfIndex = new Index[j + 1];
/*      */         int n;
/*  872 */         for (n = 1; n <= j; n++)
/*  873 */           arrayOfIndex[n] = index = new Index<>(node, index, null); 
/*      */         while (true) {
/*  875 */           headIndex = this.head;
/*  876 */           n = headIndex.level;
/*  877 */           if (j <= n)
/*      */             break; 
/*  879 */           HeadIndex<K, V> headIndex1 = headIndex;
/*  880 */           Node<K, V> node1 = headIndex.node;
/*  881 */           for (int i1 = n + 1; i1 <= j; i1++)
/*  882 */             headIndex1 = new HeadIndex<>(node1, headIndex1, arrayOfIndex[i1], i1); 
/*  883 */           if (casHead(headIndex, headIndex1)) {
/*  884 */             headIndex = headIndex1;
/*  885 */             index = arrayOfIndex[j = n];
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  891 */       int m = j; label105: while (true) {
/*  892 */         int n = headIndex.level;
/*  893 */         HeadIndex<K, V> headIndex1 = headIndex; Index<K, V> index1 = headIndex1.right, index2 = index;
/*  894 */         while (headIndex1 != null && index2 != null) {
/*      */           
/*  896 */           if (index1 != null) {
/*  897 */             Node<K, V> node1 = index1.node;
/*      */             
/*  899 */             int i1 = cpr(comparator, paramK, node1.key);
/*  900 */             if (node1.value == null) {
/*  901 */               if (!headIndex1.unlink(index1))
/*      */                 continue label105; 
/*  903 */               index1 = headIndex1.right;
/*      */               continue;
/*      */             } 
/*  906 */             if (i1 > 0) {
/*  907 */               index3 = index1;
/*  908 */               index1 = index1.right;
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*  913 */           if (n == m) {
/*  914 */             if (!index3.link(index1, index2))
/*      */               continue label105; 
/*  916 */             if (index2.node.value == null) {
/*  917 */               findNode(paramK);
/*      */               break;
/*      */             } 
/*  920 */             if (--m == 0) {
/*      */               break;
/*      */             }
/*      */           } 
/*  924 */           if (--n >= m && n < j)
/*  925 */             index2 = index2.down; 
/*  926 */           Index<K, V> index3 = index3.down;
/*  927 */           index1 = index3.right;
/*      */         }  break;
/*      */       } 
/*      */     } 
/*  931 */     return null;
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
/*      */   final V doRemove(Object paramObject1, Object paramObject2) {
/*  956 */     if (paramObject1 == null)
/*  957 */       throw new NullPointerException(); 
/*  958 */     Comparator<? super K> comparator = this.comparator;
/*      */     label44: while (true) {
/*  960 */       Node<K, V> node1 = findPredecessor(paramObject1, comparator), node2 = node1.next;
/*      */       
/*  962 */       while (node2 != null) {
/*      */         
/*  964 */         Node<K, V> node = node2.next;
/*  965 */         if (node2 != node1.next)
/*      */           continue label44;  Object object;
/*  967 */         if ((object = node2.value) == null) {
/*  968 */           node2.helpDelete(node1, node);
/*      */           continue label44;
/*      */         } 
/*  971 */         if (node1.value != null) { if (object == node2)
/*      */             continue label44;  int i;
/*  973 */           if ((i = cpr(comparator, paramObject1, node2.key)) < 0)
/*      */             break; 
/*  975 */           if (i > 0) {
/*  976 */             node1 = node2;
/*  977 */             node2 = node;
/*      */             continue;
/*      */           } 
/*  980 */           if (paramObject2 != null) { if (!paramObject2.equals(object))
/*      */               break; 
/*  982 */             if (!node2.casValue(object, null))
/*      */               continue; 
/*  984 */             if (!node2.appendMarker(node) || !node1.casNext(node2, node)) {
/*  985 */               findNode(paramObject1);
/*      */             } else {
/*  987 */               findPredecessor(paramObject1, comparator);
/*  988 */               if (this.head.right == null)
/*  989 */                 tryReduceLevel(); 
/*      */             } 
/*  991 */             return (V)object; }
/*      */            continue label44; }
/*      */          continue label44;
/*      */       }  break;
/*  995 */     }  return null;
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
/*      */   private void tryReduceLevel() {
/* 1019 */     HeadIndex<K, V> headIndex1 = this.head;
/*      */     HeadIndex<K, V> headIndex2;
/*      */     HeadIndex headIndex;
/* 1022 */     if (headIndex1.level > 3 && (headIndex2 = (HeadIndex)headIndex1.down) != null && (headIndex = (HeadIndex)headIndex2.down) != null && headIndex.right == null && headIndex2.right == null && headIndex1.right == null && 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1028 */       casHead(headIndex1, headIndex2) && headIndex1.right != null)
/*      */     {
/* 1030 */       casHead(headIndex2, headIndex1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node<K, V> findFirst() {
/*      */     while (true) {
/*      */       Node<K, V> node1;
/*      */       Node<K, V> node2;
/* 1041 */       if ((node2 = (node1 = this.head.node).next) == null)
/* 1042 */         return null; 
/* 1043 */       if (node2.value != null)
/* 1044 */         return node2; 
/* 1045 */       node2.helpDelete(node1, node2.next);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Map.Entry<K, V> doRemoveFirstEntry() {
/*      */     Node<K, V> node1, node2, node3;
/*      */     Object object1;
/*      */     while (true) {
/* 1055 */       if ((node2 = (node1 = this.head.node).next) == null)
/* 1056 */         return null; 
/* 1057 */       node3 = node2.next;
/* 1058 */       if (node2 != node1.next)
/*      */         continue; 
/* 1060 */       object1 = node2.value;
/* 1061 */       if (object1 == null) {
/* 1062 */         node2.helpDelete(node1, node3);
/*      */         continue;
/*      */       } 
/* 1065 */       if (!node2.casValue(object1, null))
/*      */         continue;  break;
/* 1067 */     }  if (!node2.appendMarker(node3) || !node1.casNext(node2, node3))
/* 1068 */       findFirst(); 
/* 1069 */     clearIndexToFirst();
/* 1070 */     Object object2 = object1;
/* 1071 */     return new AbstractMap.SimpleImmutableEntry<>(node2.key, (V)object2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void clearIndexToFirst() {
/*      */     label16: while (true) {
/* 1080 */       HeadIndex<K, V> headIndex = this.head; while (true) {
/* 1081 */         Index<K, V> index2 = headIndex.right;
/* 1082 */         if (index2 != null && index2.indexesDeletedNode() && !headIndex.unlink(index2))
/*      */           continue label16;  Index<K, V> index1;
/* 1084 */         if ((index1 = headIndex.down) == null) {
/* 1085 */           if (this.head.right == null) {
/* 1086 */             tryReduceLevel();
/*      */           }
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Map.Entry<K, V> doRemoveLastEntry() {
/*      */     Node<K, V> node1, node2, node3;
/*      */     Object object1;
/*      */     label35: while (true) {
/* 1100 */       node1 = findPredecessorOfLast();
/* 1101 */       node2 = node1.next;
/* 1102 */       if (node2 == null) {
/* 1103 */         if (node1.isBaseHeader()) {
/* 1104 */           return null;
/*      */         }
/*      */         continue;
/*      */       } 
/*      */       while (true) {
/* 1109 */         node3 = node2.next;
/* 1110 */         if (node2 != node1.next)
/*      */           continue label35; 
/* 1112 */         object1 = node2.value;
/* 1113 */         if (object1 == null) {
/* 1114 */           node2.helpDelete(node1, node3);
/*      */           continue label35;
/*      */         } 
/* 1117 */         if (node1.value != null) { if (object1 == node2)
/*      */             continue label35; 
/* 1119 */           if (node3 != null) {
/* 1120 */             node1 = node2;
/* 1121 */             node2 = node3; continue;
/*      */           }  break; }
/*      */          continue label35;
/* 1124 */       }  if (!node2.casValue(object1, null))
/*      */         continue;  break;
/* 1126 */     }  K k = node2.key;
/* 1127 */     if (!node2.appendMarker(node3) || !node1.casNext(node2, node3)) {
/* 1128 */       findNode(k);
/*      */     } else {
/* 1130 */       findPredecessor(k, this.comparator);
/* 1131 */       if (this.head.right == null)
/* 1132 */         tryReduceLevel(); 
/*      */     } 
/* 1134 */     Object object2 = object1;
/* 1135 */     return new AbstractMap.SimpleImmutableEntry<>(k, (V)object2);
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
/*      */   final Node<K, V> findLast() {
/* 1152 */     HeadIndex<K, V> headIndex = this.head;
/*      */     while (true) {
/*      */       Index<K, V> index3;
/* 1155 */       while ((index3 = headIndex.right) != null) {
/* 1156 */         if (index3.indexesDeletedNode()) {
/* 1157 */           headIndex.unlink(index3);
/* 1158 */           headIndex = this.head;
/*      */           continue;
/*      */         } 
/* 1161 */         index1 = index3;
/* 1162 */       }  Index<K, V> index2; if ((index2 = index1.down) != null) {
/* 1163 */         index1 = index2; continue;
/*      */       } 
/* 1165 */       Node<K, V> node1 = index1.node, node2 = node1.next; while (true) {
/* 1166 */         if (node2 == null)
/* 1167 */           return node1.isBaseHeader() ? null : node1; 
/* 1168 */         Node<K, V> node = node2.next;
/* 1169 */         if (node2 != node1.next)
/*      */           break; 
/* 1171 */         Object object = node2.value;
/* 1172 */         if (object == null) {
/* 1173 */           node2.helpDelete(node1, node);
/*      */           break;
/*      */         } 
/* 1176 */         if (node1.value == null || object == node2)
/*      */           break; 
/* 1178 */         node1 = node2;
/* 1179 */         node2 = node;
/*      */       } 
/* 1181 */       Index<K, V> index1 = this.head;
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
/*      */   private Node<K, V> findPredecessorOfLast() {
/*      */     Index<K, V> index;
/*      */     label19: while (true) {
/* 1195 */       index = this.head; while (true) {
/*      */         Index<K, V> index2;
/* 1197 */         if ((index2 = index.right) != null) {
/* 1198 */           if (index2.indexesDeletedNode()) {
/* 1199 */             index.unlink(index2);
/*      */             
/*      */             continue label19;
/*      */           } 
/* 1203 */           if (index2.node.next != null) {
/* 1204 */             index = index2; continue;
/*      */           } 
/*      */         } 
/*      */         Index<K, V> index1;
/* 1208 */         if ((index1 = index.down) != null)
/* 1209 */         { index = index1; continue; }  break;
/*      */       }  break;
/* 1211 */     }  return index.node;
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
/*      */   final Node<K, V> findNear(K paramK, int paramInt, Comparator<? super K> paramComparator) {
/* 1231 */     if (paramK == null)
/* 1232 */       throw new NullPointerException(); 
/*      */     label40: while (true) {
/* 1234 */       Node<K, V> node1 = findPredecessor(paramK, paramComparator), node2 = node1.next;
/*      */       while (true) {
/* 1236 */         if (node2 == null)
/* 1237 */           return ((paramInt & 0x2) == 0 || node1.isBaseHeader()) ? null : node1; 
/* 1238 */         Node<K, V> node = node2.next;
/* 1239 */         if (node2 != node1.next)
/*      */           continue label40;  Object object;
/* 1241 */         if ((object = node2.value) == null) {
/* 1242 */           node2.helpDelete(node1, node);
/*      */           continue label40;
/*      */         } 
/* 1245 */         if (node1.value != null) { if (object == node2)
/*      */             continue label40; 
/* 1247 */           int i = cpr(paramComparator, paramK, node2.key);
/* 1248 */           if ((i == 0 && (paramInt & 0x1) != 0) || (i < 0 && (paramInt & 0x2) == 0))
/*      */           {
/* 1250 */             return node2; } 
/* 1251 */           if (i <= 0 && (paramInt & 0x2) != 0)
/* 1252 */             return node1.isBaseHeader() ? null : node1; 
/* 1253 */           node1 = node2;
/* 1254 */           node2 = node;
/*      */           continue; }
/*      */         
/*      */         continue label40;
/*      */       } 
/*      */       break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final AbstractMap.SimpleImmutableEntry<K, V> getNear(K paramK, int paramInt) {
/* 1266 */     Comparator<? super K> comparator = this.comparator;
/*      */     while (true) {
/* 1268 */       Node<K, V> node = findNear(paramK, paramInt, comparator);
/* 1269 */       if (node == null)
/* 1270 */         return null; 
/* 1271 */       AbstractMap.SimpleImmutableEntry<K, V> simpleImmutableEntry = node.createSnapshot();
/* 1272 */       if (simpleImmutableEntry != null) {
/* 1273 */         return simpleImmutableEntry;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentSkipListMap() {
/* 1284 */     this.comparator = null;
/* 1285 */     initialize();
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
/*      */   public ConcurrentSkipListMap(Comparator<? super K> paramComparator) {
/* 1297 */     this.comparator = paramComparator;
/* 1298 */     initialize();
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
/*      */   public ConcurrentSkipListMap(Map<? extends K, ? extends V> paramMap) {
/* 1313 */     this.comparator = null;
/* 1314 */     initialize();
/* 1315 */     putAll(paramMap);
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
/*      */   public ConcurrentSkipListMap(SortedMap<K, ? extends V> paramSortedMap) {
/* 1328 */     this.comparator = paramSortedMap.comparator();
/* 1329 */     initialize();
/* 1330 */     buildFromSorted(paramSortedMap);
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
/*      */   public ConcurrentSkipListMap<K, V> clone() {
/*      */     try {
/* 1343 */       ConcurrentSkipListMap<K, V> concurrentSkipListMap = (ConcurrentSkipListMap)super.clone();
/* 1344 */       concurrentSkipListMap.initialize();
/* 1345 */       concurrentSkipListMap.buildFromSorted(this);
/* 1346 */       return concurrentSkipListMap;
/* 1347 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 1348 */       throw new InternalError();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildFromSorted(SortedMap<K, ? extends V> paramSortedMap) {
/* 1358 */     if (paramSortedMap == null) {
/* 1359 */       throw new NullPointerException();
/*      */     }
/* 1361 */     HeadIndex<K, V> headIndex1 = this.head;
/* 1362 */     Node<K, V> node = headIndex1.node;
/*      */ 
/*      */ 
/*      */     
/* 1366 */     ArrayList<HeadIndex<K, V>> arrayList = new ArrayList();
/*      */ 
/*      */     
/* 1369 */     for (byte b = 0; b <= headIndex1.level; b++)
/* 1370 */       arrayList.add(null); 
/* 1371 */     HeadIndex<K, V> headIndex2 = headIndex1;
/* 1372 */     for (int i = headIndex1.level; i > 0; i--) {
/* 1373 */       arrayList.set(i, headIndex2);
/* 1374 */       Index<K, V> index = headIndex2.down;
/*      */     } 
/*      */ 
/*      */     
/* 1378 */     Iterator<Map.Entry> iterator = paramSortedMap.entrySet().iterator();
/* 1379 */     while (iterator.hasNext()) {
/* 1380 */       Map.Entry entry = iterator.next();
/* 1381 */       int j = ThreadLocalRandom.current().nextInt();
/* 1382 */       int k = 0;
/* 1383 */       if ((j & 0x80000001) == 0) {
/*      */         do {
/* 1385 */           k++;
/* 1386 */         } while (((j >>>= 1) & 0x1) != 0);
/* 1387 */         if (k > headIndex1.level) k = headIndex1.level + 1; 
/*      */       } 
/* 1389 */       Object object1 = entry.getKey();
/* 1390 */       Object object2 = entry.getValue();
/* 1391 */       if (object1 == null || object2 == null)
/* 1392 */         throw new NullPointerException(); 
/* 1393 */       Node<Object, Object> node2 = new Node<>(object1, object2, null);
/* 1394 */       node.next = (Node)node2;
/* 1395 */       Node<Object, Object> node1 = node2;
/* 1396 */       if (k > 0) {
/* 1397 */         Index<Object, Object> index = null;
/* 1398 */         for (byte b1 = 1; b1 <= k; b1++) {
/* 1399 */           index = new Index<>(node2, index, null);
/* 1400 */           if (b1 > headIndex1.level) {
/* 1401 */             headIndex1 = new HeadIndex<>(headIndex1.node, headIndex1, (Index)index, b1);
/*      */           }
/* 1403 */           if (b1 < arrayList.size()) {
/* 1404 */             ((Index)arrayList.get(b1)).right = index;
/* 1405 */             arrayList.set(b1, index);
/*      */           } else {
/* 1407 */             arrayList.add(index);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1411 */     }  this.head = headIndex1;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1430 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */     
/* 1433 */     for (Node<K, V> node = findFirst(); node != null; node = node.next) {
/* 1434 */       V v = node.getValidValue();
/* 1435 */       if (v != null) {
/* 1436 */         paramObjectOutputStream.writeObject(node.key);
/* 1437 */         paramObjectOutputStream.writeObject(v);
/*      */       } 
/*      */     } 
/* 1440 */     paramObjectOutputStream.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1454 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1456 */     initialize();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1466 */     HeadIndex<K, V> headIndex1 = this.head;
/* 1467 */     Node<K, V> node = headIndex1.node;
/* 1468 */     ArrayList<HeadIndex<K, V>> arrayList = new ArrayList();
/* 1469 */     for (byte b = 0; b <= headIndex1.level; b++)
/* 1470 */       arrayList.add(null); 
/* 1471 */     HeadIndex<K, V> headIndex2 = headIndex1;
/* 1472 */     for (int i = headIndex1.level; i > 0; i--) {
/* 1473 */       arrayList.set(i, headIndex2);
/* 1474 */       Index<K, V> index = headIndex2.down;
/*      */     } 
/*      */     
/*      */     while (true) {
/* 1478 */       Object object1 = paramObjectInputStream.readObject();
/* 1479 */       if (object1 == null)
/*      */         break; 
/* 1481 */       Object object2 = paramObjectInputStream.readObject();
/* 1482 */       if (object2 == null)
/* 1483 */         throw new NullPointerException(); 
/* 1484 */       Object object3 = object1;
/* 1485 */       Object object4 = object2;
/* 1486 */       int j = ThreadLocalRandom.current().nextInt();
/* 1487 */       int k = 0;
/* 1488 */       if ((j & 0x80000001) == 0) {
/*      */         do {
/* 1490 */           k++;
/* 1491 */         } while (((j >>>= 1) & 0x1) != 0);
/* 1492 */         if (k > headIndex1.level) k = headIndex1.level + 1; 
/*      */       } 
/* 1494 */       Node<Object, Object> node2 = new Node<>(object3, object4, null);
/* 1495 */       node.next = (Node)node2;
/* 1496 */       Node<Object, Object> node1 = node2;
/* 1497 */       if (k > 0) {
/* 1498 */         Index<Object, Object> index = null;
/* 1499 */         for (byte b1 = 1; b1 <= k; b1++) {
/* 1500 */           index = new Index<>(node2, index, null);
/* 1501 */           if (b1 > headIndex1.level) {
/* 1502 */             headIndex1 = new HeadIndex<>(headIndex1.node, headIndex1, (Index)index, b1);
/*      */           }
/* 1504 */           if (b1 < arrayList.size()) {
/* 1505 */             ((Index)arrayList.get(b1)).right = index;
/* 1506 */             arrayList.set(b1, index);
/*      */           } else {
/* 1508 */             arrayList.add(index);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1512 */     }  this.head = headIndex1;
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
/*      */   public boolean containsKey(Object paramObject) {
/* 1528 */     return (doGet(paramObject) != null);
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
/*      */   public V get(Object paramObject) {
/* 1546 */     return doGet(paramObject);
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
/*      */   public V getOrDefault(Object paramObject, V paramV) {
/*      */     V v;
/* 1562 */     return ((v = doGet(paramObject)) == null) ? paramV : v;
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
/* 1579 */     if (paramV == null)
/* 1580 */       throw new NullPointerException(); 
/* 1581 */     return doPut(paramK, paramV, false);
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
/*      */   public V remove(Object paramObject) {
/* 1595 */     return doRemove(paramObject, (Object)null);
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
/* 1611 */     if (paramObject == null)
/* 1612 */       throw new NullPointerException(); 
/* 1613 */     for (Node<K, V> node = findFirst(); node != null; node = node.next) {
/* 1614 */       V v = node.getValidValue();
/* 1615 */       if (v != null && paramObject.equals(v))
/* 1616 */         return true; 
/*      */     } 
/* 1618 */     return false;
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
/*      */   public int size() {
/* 1638 */     long l = 0L;
/* 1639 */     for (Node<K, V> node = findFirst(); node != null; node = node.next) {
/* 1640 */       if (node.getValidValue() != null)
/* 1641 */         l++; 
/*      */     } 
/* 1643 */     return (l >= 2147483647L) ? Integer.MAX_VALUE : (int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1651 */     return (findFirst() == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*      */     while (true) {
/* 1660 */       HeadIndex<K, V> headIndex1 = this.head, headIndex2 = (HeadIndex)headIndex1.down;
/* 1661 */       if (headIndex2 != null) {
/* 1662 */         casHead(headIndex1, headIndex2); continue;
/* 1663 */       }  Node<K, V> node1, node2; if ((node1 = headIndex1.node) != null && (node2 = node1.next) != null) {
/* 1664 */         Node<K, V> node = node2.next;
/* 1665 */         if (node2 == node1.next) {
/* 1666 */           Object object = node2.value;
/* 1667 */           if (object == null) {
/* 1668 */             node2.helpDelete(node1, node); continue;
/* 1669 */           }  if (node2.casValue(object, null) && node2.appendMarker(node)) {
/* 1670 */             node1.casNext(node2, node);
/*      */           }
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */       break;
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
/*      */   public V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction) {
/* 1695 */     if (paramK == null || paramFunction == null)
/* 1696 */       throw new NullPointerException();  V v1;
/*      */     V v2;
/* 1698 */     if ((v1 = doGet(paramK)) == null && (
/* 1699 */       v2 = paramFunction.apply(paramK)) != null) {
/* 1700 */       V v; v1 = ((v = doPut(paramK, v2, true)) == null) ? v2 : v;
/* 1701 */     }  return v1;
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
/*      */   public V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1719 */     if (paramK == null || paramBiFunction == null)
/* 1720 */       throw new NullPointerException(); 
/*      */     Node<K, V> node;
/* 1722 */     while ((node = findNode(paramK)) != null) {
/* 1723 */       Object object; if ((object = node.value) != null) {
/* 1724 */         Object object1 = object;
/* 1725 */         V v = paramBiFunction.apply(paramK, (V)object1);
/* 1726 */         if (v != null) {
/* 1727 */           if (node.casValue(object1, v))
/* 1728 */             return v;  continue;
/*      */         } 
/* 1730 */         if (doRemove(paramK, object1) != null)
/*      */           break; 
/*      */       } 
/*      */     } 
/* 1734 */     return null;
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
/*      */   public V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1752 */     if (paramK == null || paramBiFunction == null)
/* 1753 */       throw new NullPointerException(); 
/*      */     label23: while (true) {
/*      */       Node<K, V> node;
/* 1756 */       while ((node = findNode(paramK)) == null) {
/* 1757 */         V v; if ((v = paramBiFunction.apply(paramK, null)) == null)
/*      */           break label23; 
/* 1759 */         if (doPut(paramK, v, true) == null)
/* 1760 */           return v; 
/*      */       }  Object object;
/* 1762 */       if ((object = node.value) != null) {
/* 1763 */         Object object1 = object; V v;
/* 1764 */         if ((v = paramBiFunction.apply(paramK, (V)object1)) != null) {
/* 1765 */           if (node.casValue(object1, v))
/* 1766 */             return v;  continue;
/*      */         } 
/* 1768 */         if (doRemove(paramK, object1) != null)
/*      */           break; 
/*      */       } 
/*      */     } 
/* 1772 */     return null;
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
/*      */   public V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction) {
/* 1792 */     if (paramK == null || paramV == null || paramBiFunction == null)
/* 1793 */       throw new NullPointerException(); 
/*      */     while (true) {
/*      */       Node<K, V> node;
/* 1796 */       while ((node = findNode(paramK)) == null) {
/* 1797 */         if (doPut(paramK, paramV, true) == null)
/* 1798 */           return paramV; 
/*      */       }  Object object;
/* 1800 */       if ((object = node.value) != null) {
/* 1801 */         Object object1 = object; V v;
/* 1802 */         if ((v = paramBiFunction.apply((V)object1, paramV)) != null) {
/* 1803 */           if (node.casValue(object1, v))
/* 1804 */             return v;  continue;
/*      */         } 
/* 1806 */         if (doRemove(paramK, object1) != null) {
/* 1807 */           return null;
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
/*      */   public NavigableSet<K> keySet() {
/* 1852 */     KeySet<K> keySet = this.keySet;
/* 1853 */     return (keySet != null) ? keySet : (this.keySet = new KeySet<>(this));
/*      */   }
/*      */   
/*      */   public NavigableSet<K> navigableKeySet() {
/* 1857 */     KeySet<K> keySet = this.keySet;
/* 1858 */     return (keySet != null) ? keySet : (this.keySet = new KeySet<>(this));
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
/*      */   public Collection<V> values() {
/* 1881 */     Values<V> values = this.values;
/* 1882 */     return (values != null) ? values : (this.values = new Values<>(this));
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
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/* 1913 */     EntrySet<K, V> entrySet = this.entrySet;
/* 1914 */     return (entrySet != null) ? entrySet : (this.entrySet = new EntrySet<>(this));
/*      */   }
/*      */   
/*      */   public ConcurrentNavigableMap<K, V> descendingMap() {
/* 1918 */     ConcurrentNavigableMap<K, V> concurrentNavigableMap = this.descendingMap;
/* 1919 */     return (concurrentNavigableMap != null) ? concurrentNavigableMap : (this.descendingMap = new SubMap<>(this, null, false, null, false, true));
/*      */   }
/*      */ 
/*      */   
/*      */   public NavigableSet<K> descendingKeySet() {
/* 1924 */     return descendingMap().navigableKeySet();
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
/*      */   public boolean equals(Object paramObject) {
/* 1942 */     if (paramObject == this)
/* 1943 */       return true; 
/* 1944 */     if (!(paramObject instanceof Map))
/* 1945 */       return false; 
/* 1946 */     Map map = (Map)paramObject;
/*      */     try {
/* 1948 */       for (Map.Entry<K, V> entry : entrySet()) {
/* 1949 */         if (!entry.getValue().equals(map.get(entry.getKey())))
/* 1950 */           return false; 
/* 1951 */       }  for (Map.Entry entry : map.entrySet()) {
/* 1952 */         Object object1 = entry.getKey();
/* 1953 */         Object object2 = entry.getValue();
/* 1954 */         if (object1 == null || object2 == null || !object2.equals(get(object1)))
/* 1955 */           return false; 
/*      */       } 
/* 1957 */       return true;
/* 1958 */     } catch (ClassCastException classCastException) {
/* 1959 */       return false;
/* 1960 */     } catch (NullPointerException nullPointerException) {
/* 1961 */       return false;
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
/*      */   public V putIfAbsent(K paramK, V paramV) {
/* 1977 */     if (paramV == null)
/* 1978 */       throw new NullPointerException(); 
/* 1979 */     return doPut(paramK, paramV, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object paramObject1, Object paramObject2) {
/* 1990 */     if (paramObject1 == null)
/* 1991 */       throw new NullPointerException(); 
/* 1992 */     return (paramObject2 != null && doRemove(paramObject1, paramObject2) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K paramK, V paramV1, V paramV2) {
/* 2003 */     if (paramK == null || paramV1 == null || paramV2 == null)
/* 2004 */       throw new NullPointerException(); 
/*      */     while (true) {
/*      */       Node<K, V> node;
/* 2007 */       if ((node = findNode(paramK)) == null)
/* 2008 */         return false;  Object object;
/* 2009 */       if ((object = node.value) != null) {
/* 2010 */         if (!paramV1.equals(object))
/* 2011 */           return false; 
/* 2012 */         if (node.casValue(object, paramV2)) {
/* 2013 */           return true;
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
/*      */   public V replace(K paramK, V paramV) {
/* 2028 */     if (paramK == null || paramV == null)
/* 2029 */       throw new NullPointerException(); 
/*      */     while (true) {
/*      */       Node<K, V> node;
/* 2032 */       if ((node = findNode(paramK)) == null)
/* 2033 */         return null;  Object object;
/* 2034 */       if ((object = node.value) != null && node.casValue(object, paramV)) {
/* 2035 */         return (V)object;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 2044 */     return this.comparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 2051 */     Node<K, V> node = findFirst();
/* 2052 */     if (node == null)
/* 2053 */       throw new NoSuchElementException(); 
/* 2054 */     return node.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 2061 */     Node<K, V> node = findLast();
/* 2062 */     if (node == null)
/* 2063 */       throw new NoSuchElementException(); 
/* 2064 */     return node.key;
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
/*      */   public ConcurrentNavigableMap<K, V> subMap(K paramK1, boolean paramBoolean1, K paramK2, boolean paramBoolean2) {
/* 2076 */     if (paramK1 == null || paramK2 == null)
/* 2077 */       throw new NullPointerException(); 
/* 2078 */     return new SubMap<>(this, paramK1, paramBoolean1, paramK2, paramBoolean2, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentNavigableMap<K, V> headMap(K paramK, boolean paramBoolean) {
/* 2089 */     if (paramK == null)
/* 2090 */       throw new NullPointerException(); 
/* 2091 */     return new SubMap<>(this, null, false, paramK, paramBoolean, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentNavigableMap<K, V> tailMap(K paramK, boolean paramBoolean) {
/* 2102 */     if (paramK == null)
/* 2103 */       throw new NullPointerException(); 
/* 2104 */     return new SubMap<>(this, paramK, paramBoolean, null, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentNavigableMap<K, V> subMap(K paramK1, K paramK2) {
/* 2114 */     return subMap(paramK1, true, paramK2, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentNavigableMap<K, V> headMap(K paramK) {
/* 2123 */     return headMap(paramK, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentNavigableMap<K, V> tailMap(K paramK) {
/* 2132 */     return tailMap(paramK, true);
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
/*      */   public Map.Entry<K, V> lowerEntry(K paramK) {
/* 2147 */     return getNear(paramK, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lowerKey(K paramK) {
/* 2155 */     Node<K, V> node = findNear(paramK, 2, this.comparator);
/* 2156 */     return (node == null) ? null : node.key;
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
/*      */   public Map.Entry<K, V> floorEntry(K paramK) {
/* 2170 */     return getNear(paramK, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K floorKey(K paramK) {
/* 2179 */     Node<K, V> node = findNear(paramK, 3, this.comparator);
/* 2180 */     return (node == null) ? null : node.key;
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
/*      */   public Map.Entry<K, V> ceilingEntry(K paramK) {
/* 2193 */     return getNear(paramK, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K ceilingKey(K paramK) {
/* 2201 */     Node<K, V> node = findNear(paramK, 1, this.comparator);
/* 2202 */     return (node == null) ? null : node.key;
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
/*      */   public Map.Entry<K, V> higherEntry(K paramK) {
/* 2216 */     return getNear(paramK, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K higherKey(K paramK) {
/* 2225 */     Node<K, V> node = findNear(paramK, 0, this.comparator);
/* 2226 */     return (node == null) ? null : node.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> firstEntry() {
/*      */     while (true) {
/* 2237 */       Node<K, V> node = findFirst();
/* 2238 */       if (node == null)
/* 2239 */         return null; 
/* 2240 */       AbstractMap.SimpleImmutableEntry<K, V> simpleImmutableEntry = node.createSnapshot();
/* 2241 */       if (simpleImmutableEntry != null) {
/* 2242 */         return simpleImmutableEntry;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> lastEntry() {
/*      */     while (true) {
/* 2254 */       Node<K, V> node = findLast();
/* 2255 */       if (node == null)
/* 2256 */         return null; 
/* 2257 */       AbstractMap.SimpleImmutableEntry<K, V> simpleImmutableEntry = node.createSnapshot();
/* 2258 */       if (simpleImmutableEntry != null) {
/* 2259 */         return simpleImmutableEntry;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> pollFirstEntry() {
/* 2270 */     return doRemoveFirstEntry();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map.Entry<K, V> pollLastEntry() {
/* 2280 */     return doRemoveLastEntry();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   abstract class Iter<T>
/*      */     implements Iterator<T>
/*      */   {
/*      */     ConcurrentSkipListMap.Node<K, V> lastReturned;
/*      */ 
/*      */     
/*      */     ConcurrentSkipListMap.Node<K, V> next;
/*      */ 
/*      */     
/*      */     V nextValue;
/*      */ 
/*      */ 
/*      */     
/*      */     Iter() {
/* 2299 */       while ((this.next = ConcurrentSkipListMap.this.findFirst()) != null) {
/* 2300 */         Object object = this.next.value;
/* 2301 */         if (object != null && object != this.next) {
/* 2302 */           Object object1 = object;
/* 2303 */           this.nextValue = (V)object1;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public final boolean hasNext() {
/* 2310 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final void advance() {
/* 2315 */       if (this.next == null)
/* 2316 */         throw new NoSuchElementException(); 
/* 2317 */       this.lastReturned = this.next;
/* 2318 */       while ((this.next = this.next.next) != null) {
/* 2319 */         Object object = this.next.value;
/* 2320 */         if (object != null && object != this.next) {
/* 2321 */           Object object1 = object;
/* 2322 */           this.nextValue = (V)object1;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 2329 */       ConcurrentSkipListMap.Node<K, V> node = this.lastReturned;
/* 2330 */       if (node == null) {
/* 2331 */         throw new IllegalStateException();
/*      */       }
/*      */       
/* 2334 */       ConcurrentSkipListMap.this.remove(node.key);
/* 2335 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */   
/*      */   final class ValueIterator
/*      */     extends Iter<V> {
/*      */     public V next() {
/* 2342 */       Object object = this.nextValue;
/* 2343 */       advance();
/* 2344 */       return (V)object;
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeyIterator extends Iter<K> {
/*      */     public K next() {
/* 2350 */       ConcurrentSkipListMap.Node node = this.next;
/* 2351 */       advance();
/* 2352 */       return node.key;
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntryIterator extends Iter<Map.Entry<K, V>> {
/*      */     public Map.Entry<K, V> next() {
/* 2358 */       ConcurrentSkipListMap.Node node = this.next;
/* 2359 */       Object object = this.nextValue;
/* 2360 */       advance();
/* 2361 */       return new AbstractMap.SimpleImmutableEntry<>(node.key, (V)object);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Iterator<K> keyIterator() {
/* 2368 */     return new KeyIterator();
/*      */   }
/*      */   
/*      */   Iterator<V> valueIterator() {
/* 2372 */     return new ValueIterator();
/*      */   }
/*      */   
/*      */   Iterator<Map.Entry<K, V>> entryIterator() {
/* 2376 */     return new EntryIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final <E> List<E> toList(Collection<E> paramCollection) {
/*      */     // Byte code:
/*      */     //   0: new java/util/ArrayList
/*      */     //   3: dup
/*      */     //   4: invokespecial <init> : ()V
/*      */     //   7: astore_1
/*      */     //   8: aload_0
/*      */     //   9: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   14: astore_2
/*      */     //   15: aload_2
/*      */     //   16: invokeinterface hasNext : ()Z
/*      */     //   21: ifeq -> 40
/*      */     //   24: aload_2
/*      */     //   25: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   30: astore_3
/*      */     //   31: aload_1
/*      */     //   32: aload_3
/*      */     //   33: invokevirtual add : (Ljava/lang/Object;)Z
/*      */     //   36: pop
/*      */     //   37: goto -> 15
/*      */     //   40: aload_1
/*      */     //   41: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #2389	-> 0
/*      */     //   #2390	-> 8
/*      */     //   #2391	-> 31
/*      */     //   #2392	-> 40
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class KeySet<E>
/*      */     extends AbstractSet<E>
/*      */     implements NavigableSet<E>
/*      */   {
/*      */     final ConcurrentNavigableMap<E, ?> m;
/*      */ 
/*      */ 
/*      */     
/*      */     KeySet(ConcurrentNavigableMap<E, ?> param1ConcurrentNavigableMap) {
/* 2398 */       this.m = param1ConcurrentNavigableMap;
/* 2399 */     } public int size() { return this.m.size(); }
/* 2400 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 2401 */     public boolean contains(Object param1Object) { return this.m.containsKey(param1Object); }
/* 2402 */     public boolean remove(Object param1Object) { return (this.m.remove(param1Object) != null); }
/* 2403 */     public void clear() { this.m.clear(); }
/* 2404 */     public E lower(E param1E) { return this.m.lowerKey(param1E); }
/* 2405 */     public E floor(E param1E) { return this.m.floorKey(param1E); }
/* 2406 */     public E ceiling(E param1E) { return this.m.ceilingKey(param1E); }
/* 2407 */     public E higher(E param1E) { return this.m.higherKey(param1E); }
/* 2408 */     public Comparator<? super E> comparator() { return this.m.comparator(); }
/* 2409 */     public E first() { return this.m.firstKey(); } public E last() {
/* 2410 */       return this.m.lastKey();
/*      */     } public E pollFirst() {
/* 2412 */       Map.Entry<E, ?> entry = this.m.pollFirstEntry();
/* 2413 */       return (entry == null) ? null : entry.getKey();
/*      */     }
/*      */     public E pollLast() {
/* 2416 */       Map.Entry<E, ?> entry = this.m.pollLastEntry();
/* 2417 */       return (entry == null) ? null : entry.getKey();
/*      */     }
/*      */     
/*      */     public Iterator<E> iterator() {
/* 2421 */       if (this.m instanceof ConcurrentSkipListMap) {
/* 2422 */         return ((ConcurrentSkipListMap)this.m).keyIterator();
/*      */       }
/* 2424 */       return ((ConcurrentSkipListMap.SubMap)this.m).keyIterator();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/* 2427 */       if (param1Object == this)
/* 2428 */         return true; 
/* 2429 */       if (!(param1Object instanceof Set))
/* 2430 */         return false; 
/* 2431 */       Collection<?> collection = (Collection)param1Object;
/*      */       try {
/* 2433 */         return (containsAll(collection) && collection.containsAll(this));
/* 2434 */       } catch (ClassCastException classCastException) {
/* 2435 */         return false;
/* 2436 */       } catch (NullPointerException nullPointerException) {
/* 2437 */         return false;
/*      */       } 
/*      */     }
/* 2440 */     public Object[] toArray() { return ConcurrentSkipListMap.toList(this).toArray(); } public <T> T[] toArray(T[] param1ArrayOfT) {
/* 2441 */       return (T[])ConcurrentSkipListMap.toList(this).toArray((Object[])param1ArrayOfT);
/*      */     } public Iterator<E> descendingIterator() {
/* 2443 */       return descendingSet().iterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 2449 */       return new KeySet(this.m.subMap(param1E1, param1Boolean1, param1E2, param1Boolean2));
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 2453 */       return new KeySet(this.m.headMap(param1E, param1Boolean));
/*      */     }
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 2456 */       return new KeySet(this.m.tailMap(param1E, param1Boolean));
/*      */     }
/*      */     public NavigableSet<E> subSet(E param1E1, E param1E2) {
/* 2459 */       return subSet(param1E1, true, param1E2, false);
/*      */     }
/*      */     public NavigableSet<E> headSet(E param1E) {
/* 2462 */       return headSet(param1E, false);
/*      */     }
/*      */     public NavigableSet<E> tailSet(E param1E) {
/* 2465 */       return tailSet(param1E, true);
/*      */     }
/*      */     public NavigableSet<E> descendingSet() {
/* 2468 */       return new KeySet(this.m.descendingMap());
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 2472 */       if (this.m instanceof ConcurrentSkipListMap) {
/* 2473 */         return ((ConcurrentSkipListMap)this.m).keySpliterator();
/*      */       }
/* 2475 */       return (Spliterator<E>)((ConcurrentSkipListMap.SubMap)this.m).keyIterator();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Values<E> extends AbstractCollection<E> { final ConcurrentNavigableMap<?, E> m;
/*      */     
/*      */     Values(ConcurrentNavigableMap<?, E> param1ConcurrentNavigableMap) {
/* 2482 */       this.m = param1ConcurrentNavigableMap;
/*      */     }
/*      */     
/*      */     public Iterator<E> iterator() {
/* 2486 */       if (this.m instanceof ConcurrentSkipListMap) {
/* 2487 */         return ((ConcurrentSkipListMap)this.m).valueIterator();
/*      */       }
/* 2489 */       return ((ConcurrentSkipListMap.SubMap)this.m).valueIterator();
/*      */     }
/*      */     public boolean isEmpty() {
/* 2492 */       return this.m.isEmpty();
/*      */     }
/*      */     public int size() {
/* 2495 */       return this.m.size();
/*      */     }
/*      */     public boolean contains(Object param1Object) {
/* 2498 */       return this.m.containsValue(param1Object);
/*      */     }
/*      */     public void clear() {
/* 2501 */       this.m.clear();
/*      */     }
/* 2503 */     public Object[] toArray() { return ConcurrentSkipListMap.toList(this).toArray(); } public <T> T[] toArray(T[] param1ArrayOfT) {
/* 2504 */       return (T[])ConcurrentSkipListMap.toList(this).toArray((Object[])param1ArrayOfT);
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 2507 */       if (this.m instanceof ConcurrentSkipListMap) {
/* 2508 */         return ((ConcurrentSkipListMap)this.m).valueSpliterator();
/*      */       }
/* 2510 */       return (Spliterator<E>)((ConcurrentSkipListMap.SubMap)this.m).valueIterator();
/*      */     } }
/*      */   
/*      */   static final class EntrySet<K1, V1> extends AbstractSet<Map.Entry<K1, V1>> {
/*      */     final ConcurrentNavigableMap<K1, V1> m;
/*      */     
/*      */     EntrySet(ConcurrentNavigableMap<K1, V1> param1ConcurrentNavigableMap) {
/* 2517 */       this.m = param1ConcurrentNavigableMap;
/*      */     }
/*      */     
/*      */     public Iterator<Map.Entry<K1, V1>> iterator() {
/* 2521 */       if (this.m instanceof ConcurrentSkipListMap) {
/* 2522 */         return ((ConcurrentSkipListMap<K1, V1>)this.m).entryIterator();
/*      */       }
/* 2524 */       return ((ConcurrentSkipListMap.SubMap<K1, V1>)this.m).entryIterator();
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 2528 */       if (!(param1Object instanceof Map.Entry))
/* 2529 */         return false; 
/* 2530 */       Map.Entry entry = (Map.Entry)param1Object;
/* 2531 */       V1 v1 = this.m.get(entry.getKey());
/* 2532 */       return (v1 != null && v1.equals(entry.getValue()));
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/* 2535 */       if (!(param1Object instanceof Map.Entry))
/* 2536 */         return false; 
/* 2537 */       Map.Entry entry = (Map.Entry)param1Object;
/* 2538 */       return this.m.remove(entry.getKey(), entry
/* 2539 */           .getValue());
/*      */     }
/*      */     public boolean isEmpty() {
/* 2542 */       return this.m.isEmpty();
/*      */     }
/*      */     public int size() {
/* 2545 */       return this.m.size();
/*      */     }
/*      */     public void clear() {
/* 2548 */       this.m.clear();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/* 2551 */       if (param1Object == this)
/* 2552 */         return true; 
/* 2553 */       if (!(param1Object instanceof Set))
/* 2554 */         return false; 
/* 2555 */       Collection<?> collection = (Collection)param1Object;
/*      */       try {
/* 2557 */         return (containsAll(collection) && collection.containsAll(this));
/* 2558 */       } catch (ClassCastException classCastException) {
/* 2559 */         return false;
/* 2560 */       } catch (NullPointerException nullPointerException) {
/* 2561 */         return false;
/*      */       } 
/*      */     }
/* 2564 */     public Object[] toArray() { return ConcurrentSkipListMap.toList(this).toArray(); } public <T> T[] toArray(T[] param1ArrayOfT) {
/* 2565 */       return (T[])ConcurrentSkipListMap.toList(this).toArray((Object[])param1ArrayOfT);
/*      */     }
/*      */     public Spliterator<Map.Entry<K1, V1>> spliterator() {
/* 2568 */       if (this.m instanceof ConcurrentSkipListMap) {
/* 2569 */         return ((ConcurrentSkipListMap<K1, V1>)this.m).entrySpliterator();
/*      */       }
/* 2571 */       return (Spliterator<Map.Entry<K1, V1>>)((ConcurrentSkipListMap.SubMap)this.m)
/* 2572 */         .entryIterator();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SubMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */     implements ConcurrentNavigableMap<K, V>, Cloneable, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7647078645895051609L;
/*      */ 
/*      */ 
/*      */     
/*      */     private final ConcurrentSkipListMap<K, V> m;
/*      */ 
/*      */ 
/*      */     
/*      */     private final K lo;
/*      */ 
/*      */     
/*      */     private final K hi;
/*      */ 
/*      */     
/*      */     private final boolean loInclusive;
/*      */ 
/*      */     
/*      */     private final boolean hiInclusive;
/*      */ 
/*      */     
/*      */     private final boolean isDescending;
/*      */ 
/*      */     
/*      */     private transient ConcurrentSkipListMap.KeySet<K> keySetView;
/*      */ 
/*      */     
/*      */     private transient Set<Map.Entry<K, V>> entrySetView;
/*      */ 
/*      */     
/*      */     private transient Collection<V> valuesView;
/*      */ 
/*      */ 
/*      */     
/*      */     SubMap(ConcurrentSkipListMap<K, V> param1ConcurrentSkipListMap, K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2, boolean param1Boolean3) {
/* 2617 */       Comparator<? super K> comparator = param1ConcurrentSkipListMap.comparator;
/* 2618 */       if (param1K1 != null && param1K2 != null && 
/* 2619 */         ConcurrentSkipListMap.cpr(comparator, param1K1, param1K2) > 0)
/* 2620 */         throw new IllegalArgumentException("inconsistent range"); 
/* 2621 */       this.m = param1ConcurrentSkipListMap;
/* 2622 */       this.lo = param1K1;
/* 2623 */       this.hi = param1K2;
/* 2624 */       this.loInclusive = param1Boolean1;
/* 2625 */       this.hiInclusive = param1Boolean2;
/* 2626 */       this.isDescending = param1Boolean3;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean tooLow(Object param1Object, Comparator<? super K> param1Comparator) {
/*      */       int i;
/* 2633 */       return (this.lo != null && ((i = ConcurrentSkipListMap.cpr(param1Comparator, param1Object, this.lo)) < 0 || (i == 0 && !this.loInclusive)));
/*      */     }
/*      */ 
/*      */     
/*      */     boolean tooHigh(Object param1Object, Comparator<? super K> param1Comparator) {
/*      */       int i;
/* 2639 */       return (this.hi != null && ((i = ConcurrentSkipListMap.cpr(param1Comparator, param1Object, this.hi)) > 0 || (i == 0 && !this.hiInclusive)));
/*      */     }
/*      */ 
/*      */     
/*      */     boolean inBounds(Object param1Object, Comparator<? super K> param1Comparator) {
/* 2644 */       return (!tooLow(param1Object, param1Comparator) && !tooHigh(param1Object, param1Comparator));
/*      */     }
/*      */     
/*      */     void checkKeyBounds(K param1K, Comparator<? super K> param1Comparator) {
/* 2648 */       if (param1K == null)
/* 2649 */         throw new NullPointerException(); 
/* 2650 */       if (!inBounds(param1K, param1Comparator)) {
/* 2651 */         throw new IllegalArgumentException("key out of range");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isBeforeEnd(ConcurrentSkipListMap.Node<K, V> param1Node, Comparator<? super K> param1Comparator) {
/* 2659 */       if (param1Node == null)
/* 2660 */         return false; 
/* 2661 */       if (this.hi == null)
/* 2662 */         return true; 
/* 2663 */       K k = param1Node.key;
/* 2664 */       if (k == null)
/* 2665 */         return true; 
/* 2666 */       int i = ConcurrentSkipListMap.cpr(param1Comparator, k, this.hi);
/* 2667 */       if (i > 0 || (i == 0 && !this.hiInclusive))
/* 2668 */         return false; 
/* 2669 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ConcurrentSkipListMap.Node<K, V> loNode(Comparator<? super K> param1Comparator) {
/* 2677 */       if (this.lo == null)
/* 2678 */         return this.m.findFirst(); 
/* 2679 */       if (this.loInclusive) {
/* 2680 */         return this.m.findNear(this.lo, 1, param1Comparator);
/*      */       }
/* 2682 */       return this.m.findNear(this.lo, 0, param1Comparator);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ConcurrentSkipListMap.Node<K, V> hiNode(Comparator<? super K> param1Comparator) {
/* 2690 */       if (this.hi == null)
/* 2691 */         return this.m.findLast(); 
/* 2692 */       if (this.hiInclusive) {
/* 2693 */         return this.m.findNear(this.hi, 3, param1Comparator);
/*      */       }
/* 2695 */       return this.m.findNear(this.hi, 2, param1Comparator);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     K lowestKey() {
/* 2702 */       Comparator<? super K> comparator = this.m.comparator;
/* 2703 */       ConcurrentSkipListMap.Node<K, V> node = loNode(comparator);
/* 2704 */       if (isBeforeEnd(node, comparator)) {
/* 2705 */         return node.key;
/*      */       }
/* 2707 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     K highestKey() {
/* 2714 */       Comparator<? super K> comparator = this.m.comparator;
/* 2715 */       ConcurrentSkipListMap.Node<K, V> node = hiNode(comparator);
/* 2716 */       if (node != null) {
/* 2717 */         K k = node.key;
/* 2718 */         if (inBounds(k, comparator))
/* 2719 */           return k; 
/*      */       } 
/* 2721 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     Map.Entry<K, V> lowestEntry() {
/* 2725 */       Comparator<? super K> comparator = this.m.comparator;
/*      */       while (true) {
/* 2727 */         ConcurrentSkipListMap.Node<K, V> node = loNode(comparator);
/* 2728 */         if (!isBeforeEnd(node, comparator))
/* 2729 */           return null; 
/* 2730 */         AbstractMap.SimpleImmutableEntry<K, V> simpleImmutableEntry = node.createSnapshot();
/* 2731 */         if (simpleImmutableEntry != null)
/* 2732 */           return simpleImmutableEntry; 
/*      */       } 
/*      */     }
/*      */     
/*      */     Map.Entry<K, V> highestEntry() {
/* 2737 */       Comparator<? super K> comparator = this.m.comparator;
/*      */       while (true) {
/* 2739 */         ConcurrentSkipListMap.Node<K, V> node = hiNode(comparator);
/* 2740 */         if (node == null || !inBounds(node.key, comparator))
/* 2741 */           return null; 
/* 2742 */         AbstractMap.SimpleImmutableEntry<K, V> simpleImmutableEntry = node.createSnapshot();
/* 2743 */         if (simpleImmutableEntry != null)
/* 2744 */           return simpleImmutableEntry; 
/*      */       } 
/*      */     }
/*      */     
/*      */     Map.Entry<K, V> removeLowest() {
/* 2749 */       Comparator<? super K> comparator = this.m.comparator;
/*      */       while (true) {
/* 2751 */         ConcurrentSkipListMap.Node<K, V> node = loNode(comparator);
/* 2752 */         if (node == null)
/* 2753 */           return null; 
/* 2754 */         K k = node.key;
/* 2755 */         if (!inBounds(k, comparator))
/* 2756 */           return null; 
/* 2757 */         V v = this.m.doRemove(k, (Object)null);
/* 2758 */         if (v != null)
/* 2759 */           return new AbstractMap.SimpleImmutableEntry<>(k, v); 
/*      */       } 
/*      */     }
/*      */     
/*      */     Map.Entry<K, V> removeHighest() {
/* 2764 */       Comparator<? super K> comparator = this.m.comparator;
/*      */       while (true) {
/* 2766 */         ConcurrentSkipListMap.Node<K, V> node = hiNode(comparator);
/* 2767 */         if (node == null)
/* 2768 */           return null; 
/* 2769 */         K k = node.key;
/* 2770 */         if (!inBounds(k, comparator))
/* 2771 */           return null; 
/* 2772 */         V v = this.m.doRemove(k, (Object)null);
/* 2773 */         if (v != null) {
/* 2774 */           return new AbstractMap.SimpleImmutableEntry<>(k, v);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     Map.Entry<K, V> getNearEntry(K param1K, int param1Int) {
/* 2782 */       Comparator<? super K> comparator = this.m.comparator;
/* 2783 */       if (this.isDescending)
/* 2784 */         if ((param1Int & 0x2) == 0) {
/* 2785 */           param1Int |= 0x2;
/*      */         } else {
/* 2787 */           param1Int &= 0xFFFFFFFD;
/*      */         }  
/* 2789 */       if (tooLow(param1K, comparator))
/* 2790 */         return ((param1Int & 0x2) != 0) ? null : lowestEntry(); 
/* 2791 */       if (tooHigh(param1K, comparator))
/* 2792 */         return ((param1Int & 0x2) != 0) ? highestEntry() : null; 
/*      */       while (true) {
/* 2794 */         ConcurrentSkipListMap.Node<K, V> node = this.m.findNear(param1K, param1Int, comparator);
/* 2795 */         if (node == null || !inBounds(node.key, comparator))
/* 2796 */           return null; 
/* 2797 */         K k = node.key;
/* 2798 */         V v = node.getValidValue();
/* 2799 */         if (v != null) {
/* 2800 */           return new AbstractMap.SimpleImmutableEntry<>(k, v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     K getNearKey(K param1K, int param1Int) {
/* 2806 */       Comparator<? super K> comparator = this.m.comparator;
/* 2807 */       if (this.isDescending)
/* 2808 */         if ((param1Int & 0x2) == 0) {
/* 2809 */           param1Int |= 0x2;
/*      */         } else {
/* 2811 */           param1Int &= 0xFFFFFFFD;
/*      */         }  
/* 2813 */       if (tooLow(param1K, comparator)) {
/* 2814 */         if ((param1Int & 0x2) == 0) {
/* 2815 */           ConcurrentSkipListMap.Node<K, V> node = loNode(comparator);
/* 2816 */           if (isBeforeEnd(node, comparator))
/* 2817 */             return node.key; 
/*      */         } 
/* 2819 */         return null;
/*      */       } 
/* 2821 */       if (tooHigh(param1K, comparator)) {
/* 2822 */         if ((param1Int & 0x2) != 0) {
/* 2823 */           ConcurrentSkipListMap.Node<K, V> node = hiNode(comparator);
/* 2824 */           if (node != null) {
/* 2825 */             K k = node.key;
/* 2826 */             if (inBounds(k, comparator))
/* 2827 */               return k; 
/*      */           } 
/*      */         } 
/* 2830 */         return null;
/*      */       } 
/*      */       while (true) {
/* 2833 */         ConcurrentSkipListMap.Node<K, V> node = this.m.findNear(param1K, param1Int, comparator);
/* 2834 */         if (node == null || !inBounds(node.key, comparator))
/* 2835 */           return null; 
/* 2836 */         K k = node.key;
/* 2837 */         V v = node.getValidValue();
/* 2838 */         if (v != null) {
/* 2839 */           return k;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object param1Object) {
/* 2846 */       if (param1Object == null) throw new NullPointerException(); 
/* 2847 */       return (inBounds(param1Object, this.m.comparator) && this.m.containsKey(param1Object));
/*      */     }
/*      */     
/*      */     public V get(Object param1Object) {
/* 2851 */       if (param1Object == null) throw new NullPointerException(); 
/* 2852 */       return !inBounds(param1Object, this.m.comparator) ? null : this.m.get(param1Object);
/*      */     }
/*      */     
/*      */     public V put(K param1K, V param1V) {
/* 2856 */       checkKeyBounds(param1K, this.m.comparator);
/* 2857 */       return this.m.put(param1K, param1V);
/*      */     }
/*      */     
/*      */     public V remove(Object param1Object) {
/* 2861 */       return !inBounds(param1Object, this.m.comparator) ? null : this.m.remove(param1Object);
/*      */     }
/*      */     
/*      */     public int size() {
/* 2865 */       Comparator<? super K> comparator = this.m.comparator;
/* 2866 */       long l = 0L;
/* 2867 */       ConcurrentSkipListMap.Node<K, V> node = loNode(comparator);
/* 2868 */       for (; isBeforeEnd(node, comparator); 
/* 2869 */         node = node.next) {
/* 2870 */         if (node.getValidValue() != null)
/* 2871 */           l++; 
/*      */       } 
/* 2873 */       return (l >= 2147483647L) ? Integer.MAX_VALUE : (int)l;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 2877 */       Comparator<? super K> comparator = this.m.comparator;
/* 2878 */       return !isBeforeEnd(loNode(comparator), comparator);
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object param1Object) {
/* 2882 */       if (param1Object == null)
/* 2883 */         throw new NullPointerException(); 
/* 2884 */       Comparator<? super K> comparator = this.m.comparator;
/* 2885 */       ConcurrentSkipListMap.Node<K, V> node = loNode(comparator);
/* 2886 */       for (; isBeforeEnd(node, comparator); 
/* 2887 */         node = node.next) {
/* 2888 */         V v = node.getValidValue();
/* 2889 */         if (v != null && param1Object.equals(v))
/* 2890 */           return true; 
/*      */       } 
/* 2892 */       return false;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 2896 */       Comparator<? super K> comparator = this.m.comparator;
/* 2897 */       ConcurrentSkipListMap.Node<K, V> node = loNode(comparator);
/* 2898 */       for (; isBeforeEnd(node, comparator); 
/* 2899 */         node = node.next) {
/* 2900 */         if (node.getValidValue() != null) {
/* 2901 */           this.m.remove(node.key);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 2908 */       checkKeyBounds(param1K, this.m.comparator);
/* 2909 */       return this.m.putIfAbsent(param1K, param1V);
/*      */     }
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 2913 */       return (inBounds(param1Object1, this.m.comparator) && this.m.remove(param1Object1, param1Object2));
/*      */     }
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 2917 */       checkKeyBounds(param1K, this.m.comparator);
/* 2918 */       return this.m.replace(param1K, param1V1, param1V2);
/*      */     }
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 2922 */       checkKeyBounds(param1K, this.m.comparator);
/* 2923 */       return this.m.replace(param1K, param1V);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 2929 */       Comparator<? super K> comparator = this.m.comparator();
/* 2930 */       if (this.isDescending) {
/* 2931 */         return Collections.reverseOrder(comparator);
/*      */       }
/* 2933 */       return comparator;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SubMap<K, V> newSubMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 2942 */       Comparator<? super K> comparator = this.m.comparator;
/* 2943 */       if (this.isDescending) {
/* 2944 */         K k = param1K1;
/* 2945 */         param1K1 = param1K2;
/* 2946 */         param1K2 = k;
/* 2947 */         boolean bool = param1Boolean1;
/* 2948 */         param1Boolean1 = param1Boolean2;
/* 2949 */         param1Boolean2 = bool;
/*      */       } 
/* 2951 */       if (this.lo != null)
/* 2952 */         if (param1K1 == null) {
/* 2953 */           param1K1 = this.lo;
/* 2954 */           param1Boolean1 = this.loInclusive;
/*      */         } else {
/*      */           
/* 2957 */           int i = ConcurrentSkipListMap.cpr(comparator, param1K1, this.lo);
/* 2958 */           if (i < 0 || (i == 0 && !this.loInclusive && param1Boolean1)) {
/* 2959 */             throw new IllegalArgumentException("key out of range");
/*      */           }
/*      */         }  
/* 2962 */       if (this.hi != null)
/* 2963 */         if (param1K2 == null) {
/* 2964 */           param1K2 = this.hi;
/* 2965 */           param1Boolean2 = this.hiInclusive;
/*      */         } else {
/*      */           
/* 2968 */           int i = ConcurrentSkipListMap.cpr(comparator, param1K2, this.hi);
/* 2969 */           if (i > 0 || (i == 0 && !this.hiInclusive && param1Boolean2)) {
/* 2970 */             throw new IllegalArgumentException("key out of range");
/*      */           }
/*      */         }  
/* 2973 */       return new SubMap(this.m, param1K1, param1Boolean1, param1K2, param1Boolean2, this.isDescending);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public SubMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 2979 */       if (param1K1 == null || param1K2 == null)
/* 2980 */         throw new NullPointerException(); 
/* 2981 */       return newSubMap(param1K1, param1Boolean1, param1K2, param1Boolean2);
/*      */     }
/*      */     
/*      */     public SubMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 2985 */       if (param1K == null)
/* 2986 */         throw new NullPointerException(); 
/* 2987 */       return newSubMap(null, false, param1K, param1Boolean);
/*      */     }
/*      */     
/*      */     public SubMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 2991 */       if (param1K == null)
/* 2992 */         throw new NullPointerException(); 
/* 2993 */       return newSubMap(param1K, param1Boolean, null, false);
/*      */     }
/*      */     
/*      */     public SubMap<K, V> subMap(K param1K1, K param1K2) {
/* 2997 */       return subMap(param1K1, true, param1K2, false);
/*      */     }
/*      */     
/*      */     public SubMap<K, V> headMap(K param1K) {
/* 3001 */       return headMap(param1K, false);
/*      */     }
/*      */     
/*      */     public SubMap<K, V> tailMap(K param1K) {
/* 3005 */       return tailMap(param1K, true);
/*      */     }
/*      */     
/*      */     public SubMap<K, V> descendingMap() {
/* 3009 */       return new SubMap(this.m, this.lo, this.loInclusive, this.hi, this.hiInclusive, !this.isDescending);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> ceilingEntry(K param1K) {
/* 3016 */       return getNearEntry(param1K, 1);
/*      */     }
/*      */     
/*      */     public K ceilingKey(K param1K) {
/* 3020 */       return getNearKey(param1K, 1);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> lowerEntry(K param1K) {
/* 3024 */       return getNearEntry(param1K, 2);
/*      */     }
/*      */     
/*      */     public K lowerKey(K param1K) {
/* 3028 */       return getNearKey(param1K, 2);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> floorEntry(K param1K) {
/* 3032 */       return getNearEntry(param1K, 3);
/*      */     }
/*      */     
/*      */     public K floorKey(K param1K) {
/* 3036 */       return getNearKey(param1K, 3);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> higherEntry(K param1K) {
/* 3040 */       return getNearEntry(param1K, 0);
/*      */     }
/*      */     
/*      */     public K higherKey(K param1K) {
/* 3044 */       return getNearKey(param1K, 0);
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 3048 */       return this.isDescending ? highestKey() : lowestKey();
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 3052 */       return this.isDescending ? lowestKey() : highestKey();
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> firstEntry() {
/* 3056 */       return this.isDescending ? highestEntry() : lowestEntry();
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> lastEntry() {
/* 3060 */       return this.isDescending ? lowestEntry() : highestEntry();
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> pollFirstEntry() {
/* 3064 */       return this.isDescending ? removeHighest() : removeLowest();
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> pollLastEntry() {
/* 3068 */       return this.isDescending ? removeLowest() : removeHighest();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableSet<K> keySet() {
/* 3074 */       ConcurrentSkipListMap.KeySet<K> keySet = this.keySetView;
/* 3075 */       return (keySet != null) ? keySet : (this.keySetView = new ConcurrentSkipListMap.KeySet<>(this));
/*      */     }
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 3079 */       ConcurrentSkipListMap.KeySet<K> keySet = this.keySetView;
/* 3080 */       return (keySet != null) ? keySet : (this.keySetView = new ConcurrentSkipListMap.KeySet<>(this));
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/* 3084 */       Collection<V> collection = this.valuesView;
/* 3085 */       return (collection != null) ? collection : (this.valuesView = new ConcurrentSkipListMap.Values<>(this));
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 3089 */       Set<Map.Entry<K, V>> set = this.entrySetView;
/* 3090 */       return (set != null) ? set : (this.entrySetView = new ConcurrentSkipListMap.EntrySet<>(this));
/*      */     }
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 3094 */       return descendingMap().navigableKeySet();
/*      */     }
/*      */     
/*      */     Iterator<K> keyIterator() {
/* 3098 */       return new SubMapKeyIterator();
/*      */     }
/*      */     
/*      */     Iterator<V> valueIterator() {
/* 3102 */       return new SubMapValueIterator();
/*      */     }
/*      */     
/*      */     Iterator<Map.Entry<K, V>> entryIterator() {
/* 3106 */       return new SubMapEntryIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract class SubMapIter<T>
/*      */       implements Iterator<T>, Spliterator<T>
/*      */     {
/*      */       ConcurrentSkipListMap.Node<K, V> lastReturned;
/*      */       
/*      */       ConcurrentSkipListMap.Node<K, V> next;
/*      */       
/*      */       V nextValue;
/*      */ 
/*      */       
/*      */       SubMapIter() {
/* 3122 */         Comparator<? super K> comparator = ConcurrentSkipListMap.SubMap.this.m.comparator;
/*      */         while (true) {
/* 3124 */           this.next = ConcurrentSkipListMap.SubMap.this.isDescending ? ConcurrentSkipListMap.SubMap.this.hiNode(comparator) : ConcurrentSkipListMap.SubMap.this.loNode(comparator);
/* 3125 */           if (this.next == null)
/*      */             break; 
/* 3127 */           Object object = this.next.value;
/* 3128 */           if (object != null && object != this.next) {
/* 3129 */             if (!ConcurrentSkipListMap.SubMap.this.inBounds(this.next.key, comparator)) {
/* 3130 */               this.next = null; break;
/*      */             } 
/* 3132 */             Object object1 = object;
/* 3133 */             this.nextValue = (V)object1;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       public final boolean hasNext() {
/* 3141 */         return (this.next != null);
/*      */       }
/*      */       
/*      */       final void advance() {
/* 3145 */         if (this.next == null)
/* 3146 */           throw new NoSuchElementException(); 
/* 3147 */         this.lastReturned = this.next;
/* 3148 */         if (ConcurrentSkipListMap.SubMap.this.isDescending) {
/* 3149 */           descend();
/*      */         } else {
/* 3151 */           ascend();
/*      */         } 
/*      */       }
/*      */       private void ascend() {
/* 3155 */         Comparator comparator = ConcurrentSkipListMap.SubMap.this.m.comparator;
/*      */         while (true) {
/* 3157 */           this.next = this.next.next;
/* 3158 */           if (this.next == null)
/*      */             break; 
/* 3160 */           Object object = this.next.value;
/* 3161 */           if (object != null && object != this.next) {
/* 3162 */             if (ConcurrentSkipListMap.SubMap.this.tooHigh(this.next.key, comparator)) {
/* 3163 */               this.next = null; break;
/*      */             } 
/* 3165 */             Object object1 = object;
/* 3166 */             this.nextValue = (V)object1;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       private void descend() {
/* 3174 */         Comparator<? super K> comparator = ConcurrentSkipListMap.SubMap.this.m.comparator;
/*      */         while (true) {
/* 3176 */           this.next = ConcurrentSkipListMap.SubMap.this.m.findNear(this.lastReturned.key, 2, comparator);
/* 3177 */           if (this.next == null)
/*      */             break; 
/* 3179 */           Object object = this.next.value;
/* 3180 */           if (object != null && object != this.next) {
/* 3181 */             if (ConcurrentSkipListMap.SubMap.this.tooLow(this.next.key, comparator)) {
/* 3182 */               this.next = null; break;
/*      */             } 
/* 3184 */             Object object1 = object;
/* 3185 */             this.nextValue = (V)object1;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       public void remove() {
/* 3193 */         ConcurrentSkipListMap.Node<K, V> node = this.lastReturned;
/* 3194 */         if (node == null)
/* 3195 */           throw new IllegalStateException(); 
/* 3196 */         ConcurrentSkipListMap.SubMap.this.m.remove(node.key);
/* 3197 */         this.lastReturned = null;
/*      */       }
/*      */       
/*      */       public Spliterator<T> trySplit() {
/* 3201 */         return null;
/*      */       }
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/* 3205 */         if (hasNext()) {
/* 3206 */           param2Consumer.accept(next());
/* 3207 */           return true;
/*      */         } 
/* 3209 */         return false;
/*      */       }
/*      */       
/*      */       public void forEachRemaining(Consumer<? super T> param2Consumer) {
/* 3213 */         while (hasNext())
/* 3214 */           param2Consumer.accept(next()); 
/*      */       }
/*      */       
/*      */       public long estimateSize() {
/* 3218 */         return Long.MAX_VALUE;
/*      */       }
/*      */     }
/*      */     
/*      */     final class SubMapValueIterator
/*      */       extends SubMapIter<V> {
/*      */       public V next() {
/* 3225 */         Object object = this.nextValue;
/* 3226 */         advance();
/* 3227 */         return (V)object;
/*      */       }
/*      */       public int characteristics() {
/* 3230 */         return 0;
/*      */       }
/*      */     }
/*      */     
/*      */     final class SubMapKeyIterator extends SubMapIter<K> {
/*      */       public K next() {
/* 3236 */         ConcurrentSkipListMap.Node node = this.next;
/* 3237 */         advance();
/* 3238 */         return node.key;
/*      */       }
/*      */       public int characteristics() {
/* 3241 */         return 21;
/*      */       }
/*      */       
/*      */       public final Comparator<? super K> getComparator() {
/* 3245 */         return ConcurrentSkipListMap.SubMap.this.comparator();
/*      */       }
/*      */     }
/*      */     
/*      */     final class SubMapEntryIterator extends SubMapIter<Map.Entry<K, V>> {
/*      */       public Map.Entry<K, V> next() {
/* 3251 */         ConcurrentSkipListMap.Node node = this.next;
/* 3252 */         Object object = this.nextValue;
/* 3253 */         advance();
/* 3254 */         return new AbstractMap.SimpleImmutableEntry<>(node.key, (V)object);
/*      */       }
/*      */       public int characteristics() {
/* 3257 */         return 1;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 3265 */     if (paramBiConsumer == null) throw new NullPointerException();
/*      */     
/* 3267 */     for (Node<K, V> node = findFirst(); node != null; node = node.next) {
/* 3268 */       V v; if ((v = node.getValidValue()) != null)
/* 3269 */         paramBiConsumer.accept(node.key, v); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 3274 */     if (paramBiFunction == null) throw new NullPointerException();
/*      */     
/* 3276 */     for (Node<K, V> node = findFirst(); node != null; node = node.next) {
/* 3277 */       V v; while ((v = node.getValidValue()) != null) {
/* 3278 */         V v1 = paramBiFunction.apply(node.key, v);
/* 3279 */         if (v1 == null) throw new NullPointerException(); 
/* 3280 */         if (node.casValue(v, v1)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class CSLMSpliterator<K, V>
/*      */   {
/*      */     final Comparator<? super K> comparator;
/*      */ 
/*      */ 
/*      */     
/*      */     final K fence;
/*      */ 
/*      */ 
/*      */     
/*      */     ConcurrentSkipListMap.Index<K, V> row;
/*      */ 
/*      */     
/*      */     ConcurrentSkipListMap.Node<K, V> current;
/*      */ 
/*      */     
/*      */     int est;
/*      */ 
/*      */ 
/*      */     
/*      */     CSLMSpliterator(Comparator<? super K> param1Comparator, ConcurrentSkipListMap.Index<K, V> param1Index, ConcurrentSkipListMap.Node<K, V> param1Node, K param1K, int param1Int) {
/* 3311 */       this.comparator = param1Comparator; this.row = param1Index;
/* 3312 */       this.current = param1Node; this.fence = param1K; this.est = param1Int;
/*      */     }
/*      */     public final long estimateSize() {
/* 3315 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends CSLMSpliterator<K, V> implements Spliterator<K> {
/*      */     KeySpliterator(Comparator<? super K> param1Comparator, ConcurrentSkipListMap.Index<K, V> param1Index, ConcurrentSkipListMap.Node<K, V> param1Node, K param1K, int param1Int) {
/* 3322 */       super(param1Comparator, param1Index, param1Node, param1K, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<K> trySplit() {
/* 3327 */       Comparator<? super K> comparator = this.comparator;
/* 3328 */       K k2 = this.fence; ConcurrentSkipListMap.Node<K, V> node; K k1;
/* 3329 */       if ((node = this.current) != null && (k1 = node.key) != null) {
/* 3330 */         for (ConcurrentSkipListMap.Index<K, V> index = this.row; index != null; index = this.row = index.down) {
/*      */           ConcurrentSkipListMap.Index<K, V> index1; ConcurrentSkipListMap.Node<K, V> node1; ConcurrentSkipListMap.Node<K, V> node2; K k;
/* 3332 */           if ((index1 = index.right) != null && (node1 = index1.node) != null && (node2 = node1.next) != null && node2.value != null && (k = node2.key) != null && 
/*      */             
/* 3334 */             ConcurrentSkipListMap.cpr(comparator, k, k1) > 0 && (k2 == null || 
/* 3335 */             ConcurrentSkipListMap.cpr(comparator, k, k2) < 0)) {
/* 3336 */             this.current = node2;
/* 3337 */             ConcurrentSkipListMap.Index<K, V> index2 = index.down;
/* 3338 */             this.row = (index1.right != null) ? index1 : index1.down;
/* 3339 */             this.est -= this.est >>> 2;
/* 3340 */             return new KeySpliterator(comparator, index2, node, k, this.est);
/*      */           } 
/*      */         } 
/*      */       }
/* 3344 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> param1Consumer) {
/* 3348 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 3349 */       Comparator<? super K> comparator = this.comparator;
/* 3350 */       K k = this.fence;
/* 3351 */       ConcurrentSkipListMap.Node<K, V> node = this.current;
/* 3352 */       this.current = null;
/* 3353 */       for (; node != null; node = node.next) {
/*      */         K k1;
/* 3355 */         if ((k1 = node.key) != null && k != null && ConcurrentSkipListMap.cpr(comparator, k, k1) <= 0)
/*      */           break;  Object object;
/* 3357 */         if ((object = node.value) != null && object != node)
/* 3358 */           param1Consumer.accept(k1); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> param1Consumer) {
/* 3363 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 3364 */       Comparator<? super K> comparator = this.comparator;
/* 3365 */       K k = this.fence;
/* 3366 */       ConcurrentSkipListMap.Node<K, V> node = this.current;
/* 3367 */       for (; node != null; node = node.next) {
/*      */         K k1;
/* 3369 */         if ((k1 = node.key) != null && k != null && ConcurrentSkipListMap.cpr(comparator, k, k1) <= 0) {
/* 3370 */           node = null; break;
/*      */         } 
/*      */         Object object;
/* 3373 */         if ((object = node.value) != null && object != node) {
/* 3374 */           this.current = node.next;
/* 3375 */           param1Consumer.accept(k1);
/* 3376 */           return true;
/*      */         } 
/*      */       } 
/* 3379 */       this.current = node;
/* 3380 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 3384 */       return 4373;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final Comparator<? super K> getComparator() {
/* 3390 */       return this.comparator;
/*      */     }
/*      */   }
/*      */   
/*      */   final KeySpliterator<K, V> keySpliterator() {
/* 3395 */     Comparator<? super K> comparator = this.comparator;
/*      */     while (true) {
/*      */       HeadIndex<K, V> headIndex;
/* 3398 */       Node<K, V> node2 = (headIndex = this.head).node; Node<K, V> node1;
/* 3399 */       if ((node1 = node2.next) == null || node1.value != null) {
/* 3400 */         return new KeySpliterator<>(comparator, headIndex, node1, null, (node1 == null) ? 0 : Integer.MAX_VALUE);
/*      */       }
/* 3402 */       node1.helpDelete(node2, node1.next);
/*      */     } 
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends CSLMSpliterator<K, V>
/*      */     implements Spliterator<V> {
/*      */     ValueSpliterator(Comparator<? super K> param1Comparator, ConcurrentSkipListMap.Index<K, V> param1Index, ConcurrentSkipListMap.Node<K, V> param1Node, K param1K, int param1Int) {
/* 3410 */       super(param1Comparator, param1Index, param1Node, param1K, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<V> trySplit() {
/* 3415 */       Comparator<? super K> comparator = this.comparator;
/* 3416 */       K k2 = this.fence; ConcurrentSkipListMap.Node<K, V> node; K k1;
/* 3417 */       if ((node = this.current) != null && (k1 = node.key) != null) {
/* 3418 */         for (ConcurrentSkipListMap.Index<K, V> index = this.row; index != null; index = this.row = index.down) {
/*      */           ConcurrentSkipListMap.Index<K, V> index1; ConcurrentSkipListMap.Node<K, V> node1; ConcurrentSkipListMap.Node<K, V> node2; K k;
/* 3420 */           if ((index1 = index.right) != null && (node1 = index1.node) != null && (node2 = node1.next) != null && node2.value != null && (k = node2.key) != null && 
/*      */             
/* 3422 */             ConcurrentSkipListMap.cpr(comparator, k, k1) > 0 && (k2 == null || 
/* 3423 */             ConcurrentSkipListMap.cpr(comparator, k, k2) < 0)) {
/* 3424 */             this.current = node2;
/* 3425 */             ConcurrentSkipListMap.Index<K, V> index2 = index.down;
/* 3426 */             this.row = (index1.right != null) ? index1 : index1.down;
/* 3427 */             this.est -= this.est >>> 2;
/* 3428 */             return new ValueSpliterator(comparator, index2, node, k, this.est);
/*      */           } 
/*      */         } 
/*      */       }
/* 3432 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super V> param1Consumer) {
/* 3436 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 3437 */       Comparator<? super K> comparator = this.comparator;
/* 3438 */       K k = this.fence;
/* 3439 */       ConcurrentSkipListMap.Node<K, V> node = this.current;
/* 3440 */       this.current = null;
/* 3441 */       for (; node != null; node = node.next) {
/*      */         K k1;
/* 3443 */         if ((k1 = node.key) != null && k != null && ConcurrentSkipListMap.cpr(comparator, k, k1) <= 0)
/*      */           break;  Object object;
/* 3445 */         if ((object = node.value) != null && object != node) {
/* 3446 */           Object object1 = object;
/* 3447 */           param1Consumer.accept((V)object1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> param1Consumer) {
/* 3453 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 3454 */       Comparator<? super K> comparator = this.comparator;
/* 3455 */       K k = this.fence;
/* 3456 */       ConcurrentSkipListMap.Node<K, V> node = this.current;
/* 3457 */       for (; node != null; node = node.next) {
/*      */         K k1;
/* 3459 */         if ((k1 = node.key) != null && k != null && ConcurrentSkipListMap.cpr(comparator, k, k1) <= 0) {
/* 3460 */           node = null; break;
/*      */         } 
/*      */         Object object;
/* 3463 */         if ((object = node.value) != null && object != node) {
/* 3464 */           this.current = node.next;
/* 3465 */           Object object1 = object;
/* 3466 */           param1Consumer.accept((V)object1);
/* 3467 */           return true;
/*      */         } 
/*      */       } 
/* 3470 */       this.current = node;
/* 3471 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 3475 */       return 4368;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final ValueSpliterator<K, V> valueSpliterator() {
/* 3482 */     Comparator<? super K> comparator = this.comparator;
/*      */     while (true) {
/*      */       HeadIndex<K, V> headIndex;
/* 3485 */       Node<K, V> node2 = (headIndex = this.head).node; Node<K, V> node1;
/* 3486 */       if ((node1 = node2.next) == null || node1.value != null) {
/* 3487 */         return new ValueSpliterator<>(comparator, headIndex, node1, null, (node1 == null) ? 0 : Integer.MAX_VALUE);
/*      */       }
/* 3489 */       node1.helpDelete(node2, node1.next);
/*      */     } 
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends CSLMSpliterator<K, V>
/*      */     implements Spliterator<Map.Entry<K, V>> {
/*      */     EntrySpliterator(Comparator<? super K> param1Comparator, ConcurrentSkipListMap.Index<K, V> param1Index, ConcurrentSkipListMap.Node<K, V> param1Node, K param1K, int param1Int) {
/* 3497 */       super(param1Comparator, param1Index, param1Node, param1K, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> trySplit() {
/* 3502 */       Comparator<? super K> comparator = this.comparator;
/* 3503 */       K k2 = this.fence; ConcurrentSkipListMap.Node<K, V> node; K k1;
/* 3504 */       if ((node = this.current) != null && (k1 = node.key) != null) {
/* 3505 */         for (ConcurrentSkipListMap.Index<K, V> index = this.row; index != null; index = this.row = index.down) {
/*      */           ConcurrentSkipListMap.Index<K, V> index1; ConcurrentSkipListMap.Node<K, V> node1; ConcurrentSkipListMap.Node<K, V> node2; K k;
/* 3507 */           if ((index1 = index.right) != null && (node1 = index1.node) != null && (node2 = node1.next) != null && node2.value != null && (k = node2.key) != null && 
/*      */             
/* 3509 */             ConcurrentSkipListMap.cpr(comparator, k, k1) > 0 && (k2 == null || 
/* 3510 */             ConcurrentSkipListMap.cpr(comparator, k, k2) < 0)) {
/* 3511 */             this.current = node2;
/* 3512 */             ConcurrentSkipListMap.Index<K, V> index2 = index.down;
/* 3513 */             this.row = (index1.right != null) ? index1 : index1.down;
/* 3514 */             this.est -= this.est >>> 2;
/* 3515 */             return new EntrySpliterator(comparator, index2, node, k, this.est);
/*      */           } 
/*      */         } 
/*      */       }
/* 3519 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 3523 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 3524 */       Comparator<? super K> comparator = this.comparator;
/* 3525 */       K k = this.fence;
/* 3526 */       ConcurrentSkipListMap.Node<K, V> node = this.current;
/* 3527 */       this.current = null;
/* 3528 */       for (; node != null; node = node.next) {
/*      */         K k1;
/* 3530 */         if ((k1 = node.key) != null && k != null && ConcurrentSkipListMap.cpr(comparator, k, k1) <= 0)
/*      */           break;  Object object;
/* 3532 */         if ((object = node.value) != null && object != node) {
/* 3533 */           Object object1 = object;
/* 3534 */           param1Consumer
/* 3535 */             .accept(new AbstractMap.SimpleImmutableEntry<>(k1, (V)object1));
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 3541 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 3542 */       Comparator<? super K> comparator = this.comparator;
/* 3543 */       K k = this.fence;
/* 3544 */       ConcurrentSkipListMap.Node<K, V> node = this.current;
/* 3545 */       for (; node != null; node = node.next) {
/*      */         K k1;
/* 3547 */         if ((k1 = node.key) != null && k != null && ConcurrentSkipListMap.cpr(comparator, k, k1) <= 0) {
/* 3548 */           node = null; break;
/*      */         } 
/*      */         Object object;
/* 3551 */         if ((object = node.value) != null && object != node) {
/* 3552 */           this.current = node.next;
/* 3553 */           Object object1 = object;
/* 3554 */           param1Consumer
/* 3555 */             .accept(new AbstractMap.SimpleImmutableEntry<>(k1, (V)object1));
/* 3556 */           return true;
/*      */         } 
/*      */       } 
/* 3559 */       this.current = node;
/* 3560 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 3564 */       return 4373;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Comparator<Map.Entry<K, V>> getComparator() {
/* 3571 */       if (this.comparator != null) {
/* 3572 */         return Map.Entry.comparingByKey(this.comparator);
/*      */       }
/*      */       
/* 3575 */       return (param1Entry1, param1Entry2) -> {
/*      */           Comparable comparable = (Comparable)param1Entry1.getKey();
/*      */           return comparable.compareTo(param1Entry2.getKey());
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final EntrySpliterator<K, V> entrySpliterator() {
/* 3586 */     Comparator<? super K> comparator = this.comparator;
/*      */     while (true) {
/*      */       HeadIndex<K, V> headIndex;
/* 3589 */       Node<K, V> node2 = (headIndex = this.head).node; Node<K, V> node1;
/* 3590 */       if ((node1 = node2.next) == null || node1.value != null) {
/* 3591 */         return new EntrySpliterator<>(comparator, headIndex, node1, null, (node1 == null) ? 0 : Integer.MAX_VALUE);
/*      */       }
/* 3593 */       node1.helpDelete(node2, node1.next);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 3603 */       UNSAFE = Unsafe.getUnsafe();
/* 3604 */       Class<ConcurrentSkipListMap> clazz = ConcurrentSkipListMap.class;
/*      */       
/* 3606 */       headOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
/* 3607 */       Class<Thread> clazz1 = Thread.class;
/*      */       
/* 3609 */       SECONDARY = UNSAFE.objectFieldOffset(clazz1.getDeclaredField("threadLocalRandomSecondarySeed"));
/*      */     }
/* 3611 */     catch (Exception exception) {
/* 3612 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ConcurrentSkipListMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */