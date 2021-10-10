/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements Map<K, V>, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 362498820763181265L;
/*      */   static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*      */   static final int TREEIFY_THRESHOLD = 8;
/*      */   static final int UNTREEIFY_THRESHOLD = 6;
/*      */   static final int MIN_TREEIFY_CAPACITY = 64;
/*      */   transient Node<K, V>[] table;
/*      */   transient Set<Map.Entry<K, V>> entrySet;
/*      */   transient int size;
/*      */   transient int modCount;
/*      */   int threshold;
/*      */   final float loadFactor;
/*      */   
/*      */   static class Node<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final K key;
/*      */     V value;
/*      */     Node<K, V> next;
/*      */     
/*      */     Node(int param1Int, K param1K, V param1V, Node<K, V> param1Node) {
/*  286 */       this.hash = param1Int;
/*  287 */       this.key = param1K;
/*  288 */       this.value = param1V;
/*  289 */       this.next = param1Node;
/*      */     }
/*      */     
/*  292 */     public final K getKey() { return this.key; }
/*  293 */     public final V getValue() { return this.value; } public final String toString() {
/*  294 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*      */     }
/*      */     public final int hashCode() {
/*  297 */       return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
/*      */     }
/*      */     
/*      */     public final V setValue(V param1V) {
/*  301 */       V v = this.value;
/*  302 */       this.value = param1V;
/*  303 */       return v;
/*      */     }
/*      */     
/*      */     public final boolean equals(Object param1Object) {
/*  307 */       if (param1Object == this)
/*  308 */         return true; 
/*  309 */       if (param1Object instanceof Map.Entry) {
/*  310 */         Map.Entry entry = (Map.Entry)param1Object;
/*  311 */         if (Objects.equals(this.key, entry.getKey()) && 
/*  312 */           Objects.equals(this.value, entry.getValue()))
/*  313 */           return true; 
/*      */       } 
/*  315 */       return false;
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
/*      */   static final int hash(Object paramObject) {
/*      */     int i;
/*  339 */     return (paramObject == null) ? 0 : ((i = paramObject.hashCode()) ^ i >>> 16);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Class<?> comparableClassFor(Object paramObject) {
/*  347 */     if (paramObject instanceof Comparable) {
/*      */       Class<?> clazz;
/*  349 */       if ((clazz = paramObject.getClass()) == String.class)
/*  350 */         return clazz;  Type[] arrayOfType;
/*  351 */       if ((arrayOfType = clazz.getGenericInterfaces()) != null)
/*  352 */         for (byte b = 0; b < arrayOfType.length; b++) {
/*  353 */           Type[] arrayOfType1; Type type; ParameterizedType parameterizedType; if (type = arrayOfType[b] instanceof ParameterizedType && (parameterizedType = (ParameterizedType)type)
/*  354 */             .getRawType() == Comparable.class && (
/*      */             
/*  356 */             arrayOfType1 = parameterizedType.getActualTypeArguments()) != null && arrayOfType1.length == 1 && arrayOfType1[0] == clazz)
/*      */           {
/*  358 */             return clazz;
/*      */           }
/*      */         }  
/*      */     } 
/*  362 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compareComparables(Class<?> paramClass, Object paramObject1, Object paramObject2) {
/*  371 */     return (paramObject2 == null || paramObject2.getClass() != paramClass) ? 0 : ((Comparable<Object>)paramObject1)
/*  372 */       .compareTo(paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int tableSizeFor(int paramInt) {
/*  379 */     int i = paramInt - 1;
/*  380 */     i |= i >>> 1;
/*  381 */     i |= i >>> 2;
/*  382 */     i |= i >>> 4;
/*  383 */     i |= i >>> 8;
/*  384 */     i |= i >>> 16;
/*  385 */     return (i < 0) ? 1 : ((i >= 1073741824) ? 1073741824 : (i + 1));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashMap(int paramInt, float paramFloat) {
/*  448 */     if (paramInt < 0) {
/*  449 */       throw new IllegalArgumentException("Illegal initial capacity: " + paramInt);
/*      */     }
/*  451 */     if (paramInt > 1073741824)
/*  452 */       paramInt = 1073741824; 
/*  453 */     if (paramFloat <= 0.0F || Float.isNaN(paramFloat)) {
/*  454 */       throw new IllegalArgumentException("Illegal load factor: " + paramFloat);
/*      */     }
/*  456 */     this.loadFactor = paramFloat;
/*  457 */     this.threshold = tableSizeFor(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashMap(int paramInt) {
/*  468 */     this(paramInt, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashMap() {
/*  476 */     this.loadFactor = 0.75F;
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
/*      */   public HashMap(Map<? extends K, ? extends V> paramMap) {
/*  489 */     this.loadFactor = 0.75F;
/*  490 */     putMapEntries(paramMap, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void putMapEntries(Map<? extends K, ? extends V> paramMap, boolean paramBoolean) {
/*  501 */     int i = paramMap.size();
/*  502 */     if (i > 0) {
/*  503 */       if (this.table == null) {
/*  504 */         float f = i / this.loadFactor + 1.0F;
/*  505 */         int j = (f < 1.07374182E9F) ? (int)f : 1073741824;
/*      */         
/*  507 */         if (j > this.threshold) {
/*  508 */           this.threshold = tableSizeFor(j);
/*      */         }
/*  510 */       } else if (i > this.threshold) {
/*  511 */         resize();
/*  512 */       }  for (Map.Entry<? extends K, ? extends V> entry : paramMap.entrySet()) {
/*  513 */         Object object1 = entry.getKey();
/*  514 */         Object object2 = entry.getValue();
/*  515 */         putVal(hash(object1), (K)object1, (V)object2, false, paramBoolean);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  526 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  535 */     return (this.size == 0);
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
/*      */     Node<K, V> node;
/*  557 */     return ((node = getNode(hash(paramObject), paramObject)) == null) ? null : node.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node<K, V> getNode(int paramInt, Object paramObject) {
/*      */     Node<K, V>[] arrayOfNode;
/*      */     Node<K, V> node;
/*      */     int i;
/*  569 */     if ((arrayOfNode = this.table) != null && (i = arrayOfNode.length) > 0 && (node = arrayOfNode[i - 1 & paramInt]) != null) {
/*      */       K k;
/*  571 */       if (node.hash == paramInt && ((k = node.key) == paramObject || (paramObject != null && paramObject
/*  572 */         .equals(k))))
/*  573 */         return node;  Node<K, V> node1;
/*  574 */       if ((node1 = node.next) != null) {
/*  575 */         if (node instanceof TreeNode)
/*  576 */           return ((TreeNode<K, V>)node).getTreeNode(paramInt, paramObject); 
/*      */         do {
/*  578 */           if (node1.hash == paramInt && ((k = node1.key) == paramObject || (paramObject != null && paramObject
/*  579 */             .equals(k))))
/*  580 */             return node1; 
/*  581 */         } while ((node1 = node1.next) != null);
/*      */       } 
/*      */     } 
/*  584 */     return null;
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
/*  596 */     return (getNode(hash(paramObject), paramObject) != null);
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
/*  612 */     return putVal(hash(paramK), paramK, paramV, false, true);
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
/*      */   final V putVal(int paramInt, K paramK, V paramV, boolean paramBoolean1, boolean paramBoolean2) {
/*      */     Node[] arrayOfNode;
/*      */     Node<K, V>[] arrayOfNode1;
/*      */     int i;
/*  628 */     if ((arrayOfNode1 = this.table) == null || (i = arrayOfNode1.length) == 0)
/*  629 */       i = (arrayOfNode = (Node[])resize()).length;  Node<K, V> node; int j;
/*  630 */     if ((node = arrayOfNode[j = i - 1 & paramInt]) == null) {
/*  631 */       arrayOfNode[j] = newNode(paramInt, paramK, paramV, (Node<K, V>)null);
/*      */     } else {
/*      */       Node<K, V> node1; K k;
/*  634 */       if (node.hash == paramInt && ((k = node.key) == paramK || (paramK != null && paramK
/*  635 */         .equals(k)))) {
/*  636 */         node1 = node;
/*  637 */       } else if (node instanceof TreeNode) {
/*  638 */         node1 = ((TreeNode<K, V>)node).putTreeVal(this, (Node<K, V>[])arrayOfNode, paramInt, paramK, paramV);
/*      */       } else {
/*  640 */         for (byte b = 0;; b++) {
/*  641 */           if ((node1 = node.next) == null) {
/*  642 */             node.next = newNode(paramInt, paramK, paramV, (Node<K, V>)null);
/*  643 */             if (b >= 7)
/*  644 */               treeifyBin((Node<K, V>[])arrayOfNode, paramInt); 
/*      */             break;
/*      */           } 
/*  647 */           if (node1.hash == paramInt && ((k = node1.key) == paramK || (paramK != null && paramK
/*  648 */             .equals(k))))
/*      */             break; 
/*  650 */           node = node1;
/*      */         } 
/*      */       } 
/*  653 */       if (node1 != null) {
/*  654 */         V v = node1.value;
/*  655 */         if (!paramBoolean1 || v == null)
/*  656 */           node1.value = paramV; 
/*  657 */         afterNodeAccess(node1);
/*  658 */         return v;
/*      */       } 
/*      */     } 
/*  661 */     this.modCount++;
/*  662 */     if (++this.size > this.threshold)
/*  663 */       resize(); 
/*  664 */     afterNodeInsertion(paramBoolean2);
/*  665 */     return null;
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
/*      */   final Node<K, V>[] resize() {
/*      */     byte b2;
/*  678 */     Node<K, V>[] arrayOfNode = this.table;
/*  679 */     byte b1 = (arrayOfNode == null) ? 0 : arrayOfNode.length;
/*  680 */     int i = this.threshold;
/*  681 */     int j = 0;
/*  682 */     if (b1) {
/*  683 */       if (b1 >= 1073741824) {
/*  684 */         this.threshold = Integer.MAX_VALUE;
/*  685 */         return arrayOfNode;
/*      */       } 
/*  687 */       if ((b2 = b1 << 1) < 1073741824 && b1 >= 16)
/*      */       {
/*  689 */         j = i << 1;
/*      */       }
/*  691 */     } else if (i > 0) {
/*  692 */       b2 = i;
/*      */     } else {
/*  694 */       b2 = 16;
/*  695 */       j = 12;
/*      */     } 
/*  697 */     if (j == 0) {
/*  698 */       float f = b2 * this.loadFactor;
/*  699 */       j = (b2 < 1073741824 && f < 1.07374182E9F) ? (int)f : Integer.MAX_VALUE;
/*      */     } 
/*      */     
/*  702 */     this.threshold = j;
/*      */     
/*  704 */     Node[] arrayOfNode1 = new Node[b2];
/*  705 */     this.table = (Node<K, V>[])arrayOfNode1;
/*  706 */     if (arrayOfNode != null)
/*  707 */       for (byte b = 0; b < b1; b++) {
/*      */         Node<K, V> node;
/*  709 */         if ((node = arrayOfNode[b]) != null) {
/*  710 */           arrayOfNode[b] = null;
/*  711 */           if (node.next == null) {
/*  712 */             arrayOfNode1[node.hash & b2 - 1] = node;
/*  713 */           } else if (node instanceof TreeNode) {
/*  714 */             ((TreeNode)node).split(this, arrayOfNode1, b, b1);
/*      */           } else {
/*  716 */             Node<K, V> node1 = null, node2 = null;
/*  717 */             Node<K, V> node3 = null, node4 = null;
/*      */             
/*      */             while (true) {
/*  720 */               Node<K, V> node5 = node.next;
/*  721 */               if ((node.hash & b1) == 0) {
/*  722 */                 if (node2 == null) {
/*  723 */                   node1 = node;
/*      */                 } else {
/*  725 */                   node2.next = node;
/*  726 */                 }  node2 = node;
/*      */               } else {
/*      */                 
/*  729 */                 if (node4 == null) {
/*  730 */                   node3 = node;
/*      */                 } else {
/*  732 */                   node4.next = node;
/*  733 */                 }  node4 = node;
/*      */               } 
/*  735 */               if ((node = node5) == null) {
/*  736 */                 if (node2 != null) {
/*  737 */                   node2.next = null;
/*  738 */                   arrayOfNode1[b] = node1;
/*      */                 } 
/*  740 */                 if (node4 != null) {
/*  741 */                   node4.next = null;
/*  742 */                   arrayOfNode1[b + b1] = node3;
/*      */                 }  break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  748 */       }   return (Node<K, V>[])arrayOfNode1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void treeifyBin(Node<K, V>[] paramArrayOfNode, int paramInt) {
/*      */     int i;
/*  757 */     if (paramArrayOfNode == null || (i = paramArrayOfNode.length) < 64)
/*  758 */     { resize(); }
/*  759 */     else { int j; Node<K, V> node; if ((node = paramArrayOfNode[j = i - 1 & paramInt]) != null) {
/*  760 */         TreeNode<K, V> treeNode1 = null, treeNode2 = null;
/*      */         while (true) {
/*  762 */           TreeNode<K, V> treeNode = replacementTreeNode(node, (Node<K, V>)null);
/*  763 */           if (treeNode2 == null) {
/*  764 */             treeNode1 = treeNode;
/*      */           } else {
/*  766 */             treeNode.prev = treeNode2;
/*  767 */             treeNode2.next = treeNode;
/*      */           } 
/*  769 */           treeNode2 = treeNode;
/*  770 */           if ((node = node.next) == null) {
/*  771 */             paramArrayOfNode[j] = treeNode1; if (treeNode1 != null) {
/*  772 */               treeNode1.treeify(paramArrayOfNode);
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }  }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap) {
/*  785 */     putMapEntries(paramMap, true);
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
/*      */   public V remove(Object paramObject) {
/*      */     Node<K, V> node;
/*  799 */     return ((node = removeNode(hash(paramObject), paramObject, (Object)null, false, true)) == null) ? null : node.value;
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
/*      */   final Node<K, V> removeNode(int paramInt, Object paramObject1, Object paramObject2, boolean paramBoolean1, boolean paramBoolean2) {
/*      */     Node<K, V>[] arrayOfNode;
/*      */     Node<K, V> node;
/*      */     int i;
/*      */     int j;
/*  816 */     if ((arrayOfNode = this.table) != null && (i = arrayOfNode.length) > 0 && (node = arrayOfNode[j = i - 1 & paramInt]) != null) {
/*      */       
/*  818 */       Node<K, V> node1 = null; K k;
/*  819 */       if (node.hash == paramInt && ((k = node.key) == paramObject1 || (paramObject1 != null && paramObject1
/*  820 */         .equals(k))))
/*  821 */       { node1 = node; }
/*  822 */       else { Node<K, V> node2; if ((node2 = node.next) != null)
/*  823 */           if (node instanceof TreeNode) {
/*  824 */             node1 = ((TreeNode<K, V>)node).getTreeNode(paramInt, paramObject1);
/*      */           } else {
/*      */             do {
/*  827 */               if (node2.hash == paramInt && ((k = node2.key) == paramObject1 || (paramObject1 != null && paramObject1
/*      */                 
/*  829 */                 .equals(k)))) {
/*  830 */                 node1 = node2;
/*      */                 break;
/*      */               } 
/*  833 */               node = node2;
/*  834 */             } while ((node2 = node2.next) != null);
/*      */           }   }
/*      */        V v;
/*  837 */       if (node1 != null && (!paramBoolean1 || (v = node1.value) == paramObject2 || (paramObject2 != null && paramObject2
/*  838 */         .equals(v)))) {
/*  839 */         if (node1 instanceof TreeNode) {
/*  840 */           ((TreeNode<K, V>)node1).removeTreeNode(this, arrayOfNode, paramBoolean2);
/*  841 */         } else if (node1 == node) {
/*  842 */           arrayOfNode[j] = node1.next;
/*      */         } else {
/*  844 */           node.next = node1.next;
/*  845 */         }  this.modCount++;
/*  846 */         this.size--;
/*  847 */         afterNodeRemoval(node1);
/*  848 */         return node1;
/*      */       } 
/*      */     } 
/*  851 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  860 */     this.modCount++; Node<K, V>[] arrayOfNode;
/*  861 */     if ((arrayOfNode = this.table) != null && this.size > 0) {
/*  862 */       this.size = 0;
/*  863 */       for (byte b = 0; b < arrayOfNode.length; b++) {
/*  864 */         arrayOfNode[b] = null;
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
/*      */   public boolean containsValue(Object paramObject) {
/*      */     Node<K, V>[] arrayOfNode;
/*  878 */     if ((arrayOfNode = this.table) != null && this.size > 0)
/*  879 */       for (byte b = 0; b < arrayOfNode.length; b++) {
/*  880 */         for (Node<K, V> node = arrayOfNode[b]; node != null; node = node.next) {
/*  881 */           V v; if ((v = node.value) == paramObject || (paramObject != null && paramObject
/*  882 */             .equals(v))) {
/*  883 */             return true;
/*      */           }
/*      */         } 
/*      */       }  
/*  887 */     return false;
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
/*      */   public Set<K> keySet() {
/*  906 */     Set<K> set = this.keySet;
/*  907 */     if (set == null) {
/*  908 */       set = new KeySet();
/*  909 */       this.keySet = set;
/*      */     } 
/*  911 */     return set;
/*      */   }
/*      */   
/*      */   final class KeySet extends AbstractSet<K> {
/*  915 */     public final int size() { return HashMap.this.size; }
/*  916 */     public final void clear() { HashMap.this.clear(); }
/*  917 */     public final Iterator<K> iterator() { return new HashMap.KeyIterator(); } public final boolean contains(Object param1Object) {
/*  918 */       return HashMap.this.containsKey(param1Object);
/*      */     } public final boolean remove(Object param1Object) {
/*  920 */       return (HashMap.this.removeNode(HashMap.hash(param1Object), param1Object, (Object)null, false, true) != null);
/*      */     }
/*      */     public final Spliterator<K> spliterator() {
/*  923 */       return new HashMap.KeySpliterator<>(HashMap.this, 0, -1, 0, 0);
/*      */     }
/*      */     
/*      */     public final void forEach(Consumer<? super K> param1Consumer) {
/*  927 */       if (param1Consumer == null)
/*  928 */         throw new NullPointerException();  HashMap.Node[] arrayOfNode;
/*  929 */       if (HashMap.this.size > 0 && (arrayOfNode = HashMap.this.table) != null) {
/*  930 */         int i = HashMap.this.modCount;
/*  931 */         for (byte b = 0; b < arrayOfNode.length; b++) {
/*  932 */           for (HashMap.Node node = arrayOfNode[b]; node != null; node = node.next)
/*  933 */             param1Consumer.accept(node.key); 
/*      */         } 
/*  935 */         if (HashMap.this.modCount != i) {
/*  936 */           throw new ConcurrentModificationException();
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
/*      */   public Collection<V> values() {
/*  957 */     Collection<V> collection = this.values;
/*  958 */     if (collection == null) {
/*  959 */       collection = new Values();
/*  960 */       this.values = collection;
/*      */     } 
/*  962 */     return collection;
/*      */   }
/*      */   
/*      */   final class Values extends AbstractCollection<V> {
/*  966 */     public final int size() { return HashMap.this.size; }
/*  967 */     public final void clear() { HashMap.this.clear(); }
/*  968 */     public final Iterator<V> iterator() { return new HashMap.ValueIterator(); } public final boolean contains(Object param1Object) {
/*  969 */       return HashMap.this.containsValue(param1Object);
/*      */     } public final Spliterator<V> spliterator() {
/*  971 */       return new HashMap.ValueSpliterator<>(HashMap.this, 0, -1, 0, 0);
/*      */     }
/*      */     
/*      */     public final void forEach(Consumer<? super V> param1Consumer) {
/*  975 */       if (param1Consumer == null)
/*  976 */         throw new NullPointerException();  HashMap.Node[] arrayOfNode;
/*  977 */       if (HashMap.this.size > 0 && (arrayOfNode = HashMap.this.table) != null) {
/*  978 */         int i = HashMap.this.modCount;
/*  979 */         for (byte b = 0; b < arrayOfNode.length; b++) {
/*  980 */           for (HashMap.Node node = arrayOfNode[b]; node != null; node = node.next)
/*  981 */             param1Consumer.accept(node.value); 
/*      */         } 
/*  983 */         if (HashMap.this.modCount != i) {
/*  984 */           throw new ConcurrentModificationException();
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
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/*      */     Set<Map.Entry<K, V>> set;
/* 1007 */     return ((set = this.entrySet) == null) ? (this.entrySet = new EntrySet()) : set;
/*      */   }
/*      */   
/*      */   final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
/* 1011 */     public final int size() { return HashMap.this.size; } public final void clear() {
/* 1012 */       HashMap.this.clear();
/*      */     } public final Iterator<Map.Entry<K, V>> iterator() {
/* 1014 */       return new HashMap.EntryIterator();
/*      */     }
/*      */     public final boolean contains(Object param1Object) {
/* 1017 */       if (!(param1Object instanceof Map.Entry))
/* 1018 */         return false; 
/* 1019 */       Map.Entry entry = (Map.Entry)param1Object;
/* 1020 */       Object object = entry.getKey();
/* 1021 */       HashMap.Node node = HashMap.this.getNode(HashMap.hash(object), object);
/* 1022 */       return (node != null && node.equals(entry));
/*      */     }
/*      */     public final boolean remove(Object param1Object) {
/* 1025 */       if (param1Object instanceof Map.Entry) {
/* 1026 */         Map.Entry entry = (Map.Entry)param1Object;
/* 1027 */         Object object1 = entry.getKey();
/* 1028 */         Object object2 = entry.getValue();
/* 1029 */         return (HashMap.this.removeNode(HashMap.hash(object1), object1, object2, true, true) != null);
/*      */       } 
/* 1031 */       return false;
/*      */     }
/*      */     public final Spliterator<Map.Entry<K, V>> spliterator() {
/* 1034 */       return new HashMap.EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
/*      */     }
/*      */     
/*      */     public final void forEach(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 1038 */       if (param1Consumer == null)
/* 1039 */         throw new NullPointerException();  HashMap.Node[] arrayOfNode;
/* 1040 */       if (HashMap.this.size > 0 && (arrayOfNode = HashMap.this.table) != null) {
/* 1041 */         int i = HashMap.this.modCount;
/* 1042 */         for (byte b = 0; b < arrayOfNode.length; b++) {
/* 1043 */           for (HashMap.Node<K, V> node = arrayOfNode[b]; node != null; node = node.next)
/* 1044 */             param1Consumer.accept(node); 
/*      */         } 
/* 1046 */         if (HashMap.this.modCount != i) {
/* 1047 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(Object paramObject, V paramV) {
/*      */     Node<K, V> node;
/* 1057 */     return ((node = getNode(hash(paramObject), paramObject)) == null) ? paramV : node.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K paramK, V paramV) {
/* 1062 */     return putVal(hash(paramK), paramK, paramV, true, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object paramObject1, Object paramObject2) {
/* 1067 */     return (removeNode(hash(paramObject1), paramObject1, paramObject2, true, true) != null);
/*      */   }
/*      */   
/*      */   public boolean replace(K paramK, V paramV1, V paramV2) {
/*      */     Node<K, V> node;
/*      */     V v;
/* 1073 */     if ((node = getNode(hash(paramK), paramK)) != null && ((v = node.value) == paramV1 || (v != null && v
/* 1074 */       .equals(paramV1)))) {
/* 1075 */       node.value = paramV2;
/* 1076 */       afterNodeAccess(node);
/* 1077 */       return true;
/*      */     } 
/* 1079 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(K paramK, V paramV) {
/*      */     Node<K, V> node;
/* 1085 */     if ((node = getNode(hash(paramK), paramK)) != null) {
/* 1086 */       V v = node.value;
/* 1087 */       node.value = paramV;
/* 1088 */       afterNodeAccess(node);
/* 1089 */       return v;
/*      */     } 
/* 1091 */     return null;
/*      */   }
/*      */   
/*      */   public V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction) {
/*      */     Node[] arrayOfNode;
/*      */     Node<K, V> node2;
/* 1097 */     if (paramFunction == null)
/* 1098 */       throw new NullPointerException(); 
/* 1099 */     int i = hash(paramK);
/*      */     
/* 1101 */     byte b = 0;
/* 1102 */     TreeNode<?, ?> treeNode1 = null;
/* 1103 */     TreeNode<?, ?> treeNode2 = null; Node<K, V>[] arrayOfNode1; int j;
/* 1104 */     if (this.size > this.threshold || (arrayOfNode1 = this.table) == null || (j = arrayOfNode1.length) == 0)
/*      */     {
/* 1106 */       j = (arrayOfNode = (Node[])resize()).length; }  Node<K, V> node1; int k;
/* 1107 */     if ((node1 = arrayOfNode[k = j - 1 & i]) != null) {
/* 1108 */       if (node1 instanceof TreeNode) {
/* 1109 */         treeNode2 = (treeNode1 = (TreeNode<?, ?>)node1).getTreeNode(i, paramK);
/*      */       } else {
/* 1111 */         Node<K, V> node = node1; do {
/*      */           K k1;
/* 1113 */           if (node.hash == i && ((k1 = node.key) == paramK || (paramK != null && paramK
/* 1114 */             .equals(k1)))) {
/* 1115 */             node2 = node;
/*      */             break;
/*      */           } 
/* 1118 */           ++b;
/* 1119 */         } while ((node = node.next) != null);
/*      */       } 
/*      */       
/* 1122 */       if (node2 != null && (v = node2.value) != null) {
/* 1123 */         afterNodeAccess(node2);
/* 1124 */         return v;
/*      */       } 
/*      */     } 
/* 1127 */     V v = paramFunction.apply(paramK);
/* 1128 */     if (v == null)
/* 1129 */       return null; 
/* 1130 */     if (node2 != null) {
/* 1131 */       node2.value = v;
/* 1132 */       afterNodeAccess(node2);
/* 1133 */       return v;
/*      */     } 
/* 1135 */     if (treeNode1 != null) {
/* 1136 */       treeNode1.putTreeVal(this, (Node<?, ?>[])arrayOfNode, i, paramK, v);
/*      */     } else {
/* 1138 */       arrayOfNode[k] = newNode(i, paramK, v, node1);
/* 1139 */       if (b >= 7)
/* 1140 */         treeifyBin((Node<K, V>[])arrayOfNode, i); 
/*      */     } 
/* 1142 */     this.modCount++;
/* 1143 */     this.size++;
/* 1144 */     afterNodeInsertion(true);
/* 1145 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1150 */     if (paramBiFunction == null) {
/* 1151 */       throw new NullPointerException();
/*      */     }
/* 1153 */     int i = hash(paramK); Node<K, V> node; V v;
/* 1154 */     if ((node = getNode(i, paramK)) != null && (v = node.value) != null) {
/*      */       
/* 1156 */       V v1 = paramBiFunction.apply(paramK, v);
/* 1157 */       if (v1 != null) {
/* 1158 */         node.value = v1;
/* 1159 */         afterNodeAccess(node);
/* 1160 */         return v1;
/*      */       } 
/*      */       
/* 1163 */       removeNode(i, paramK, (Object)null, false, true);
/*      */     } 
/* 1165 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/*      */     Node[] arrayOfNode;
/* 1171 */     if (paramBiFunction == null)
/* 1172 */       throw new NullPointerException(); 
/* 1173 */     int i = hash(paramK);
/*      */     
/* 1175 */     byte b = 0;
/* 1176 */     TreeNode<?, ?> treeNode = null;
/* 1177 */     Node<K, V> node2 = null; Node<K, V>[] arrayOfNode1; int j;
/* 1178 */     if (this.size > this.threshold || (arrayOfNode1 = this.table) == null || (j = arrayOfNode1.length) == 0)
/*      */     {
/* 1180 */       j = (arrayOfNode = (Node[])resize()).length; }  Node<K, V> node1; int k;
/* 1181 */     if ((node1 = arrayOfNode[k = j - 1 & i]) != null) {
/* 1182 */       if (node1 instanceof TreeNode) {
/* 1183 */         node2 = (Node)(treeNode = (TreeNode<?, ?>)node1).getTreeNode(i, paramK);
/*      */       } else {
/* 1185 */         Node<K, V> node = node1; do {
/*      */           K k1;
/* 1187 */           if (node.hash == i && ((k1 = node.key) == paramK || (paramK != null && paramK
/* 1188 */             .equals(k1)))) {
/* 1189 */             node2 = node;
/*      */             break;
/*      */           } 
/* 1192 */           ++b;
/* 1193 */         } while ((node = node.next) != null);
/*      */       } 
/*      */     }
/* 1196 */     V v1 = (node2 == null) ? null : node2.value;
/* 1197 */     V v2 = paramBiFunction.apply(paramK, v1);
/* 1198 */     if (node2 != null) {
/* 1199 */       if (v2 != null) {
/* 1200 */         node2.value = v2;
/* 1201 */         afterNodeAccess(node2);
/*      */       } else {
/*      */         
/* 1204 */         removeNode(i, paramK, (Object)null, false, true);
/*      */       } 
/* 1206 */     } else if (v2 != null) {
/* 1207 */       if (treeNode != null) {
/* 1208 */         treeNode.putTreeVal(this, (Node<?, ?>[])arrayOfNode, i, paramK, v2);
/*      */       } else {
/* 1210 */         arrayOfNode[k] = newNode(i, paramK, v2, node1);
/* 1211 */         if (b >= 7)
/* 1212 */           treeifyBin((Node<K, V>[])arrayOfNode, i); 
/*      */       } 
/* 1214 */       this.modCount++;
/* 1215 */       this.size++;
/* 1216 */       afterNodeInsertion(true);
/*      */     } 
/* 1218 */     return v2;
/*      */   }
/*      */ 
/*      */   
/*      */   public V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction) {
/*      */     Node[] arrayOfNode;
/* 1224 */     if (paramV == null)
/* 1225 */       throw new NullPointerException(); 
/* 1226 */     if (paramBiFunction == null)
/* 1227 */       throw new NullPointerException(); 
/* 1228 */     int i = hash(paramK);
/*      */     
/* 1230 */     byte b = 0;
/* 1231 */     TreeNode<?, ?> treeNode = null;
/* 1232 */     Node<K, V> node2 = null; Node<K, V>[] arrayOfNode1; int j;
/* 1233 */     if (this.size > this.threshold || (arrayOfNode1 = this.table) == null || (j = arrayOfNode1.length) == 0)
/*      */     {
/* 1235 */       j = (arrayOfNode = (Node[])resize()).length; }  Node<K, V> node1; int k;
/* 1236 */     if ((node1 = arrayOfNode[k = j - 1 & i]) != null) {
/* 1237 */       if (node1 instanceof TreeNode) {
/* 1238 */         node2 = (Node)(treeNode = (TreeNode<?, ?>)node1).getTreeNode(i, paramK);
/*      */       } else {
/* 1240 */         Node<K, V> node = node1; do {
/*      */           K k1;
/* 1242 */           if (node.hash == i && ((k1 = node.key) == paramK || (paramK != null && paramK
/* 1243 */             .equals(k1)))) {
/* 1244 */             node2 = node;
/*      */             break;
/*      */           } 
/* 1247 */           ++b;
/* 1248 */         } while ((node = node.next) != null);
/*      */       } 
/*      */     }
/* 1251 */     if (node2 != null) {
/*      */       V v;
/* 1253 */       if (node2.value != null) {
/* 1254 */         v = paramBiFunction.apply(node2.value, paramV);
/*      */       } else {
/* 1256 */         v = paramV;
/* 1257 */       }  if (v != null) {
/* 1258 */         node2.value = v;
/* 1259 */         afterNodeAccess(node2);
/*      */       } else {
/*      */         
/* 1262 */         removeNode(i, paramK, (Object)null, false, true);
/* 1263 */       }  return v;
/*      */     } 
/* 1265 */     if (paramV != null) {
/* 1266 */       if (treeNode != null) {
/* 1267 */         treeNode.putTreeVal(this, (Node<?, ?>[])arrayOfNode, i, paramK, paramV);
/*      */       } else {
/* 1269 */         arrayOfNode[k] = newNode(i, paramK, paramV, node1);
/* 1270 */         if (b >= 7)
/* 1271 */           treeifyBin((Node<K, V>[])arrayOfNode, i); 
/*      */       } 
/* 1273 */       this.modCount++;
/* 1274 */       this.size++;
/* 1275 */       afterNodeInsertion(true);
/*      */     } 
/* 1277 */     return paramV;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 1283 */     if (paramBiConsumer == null)
/* 1284 */       throw new NullPointerException();  Node<K, V>[] arrayOfNode;
/* 1285 */     if (this.size > 0 && (arrayOfNode = this.table) != null) {
/* 1286 */       int i = this.modCount;
/* 1287 */       for (byte b = 0; b < arrayOfNode.length; b++) {
/* 1288 */         for (Node<K, V> node = arrayOfNode[b]; node != null; node = node.next)
/* 1289 */           paramBiConsumer.accept(node.key, node.value); 
/*      */       } 
/* 1291 */       if (this.modCount != i) {
/* 1292 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1299 */     if (paramBiFunction == null)
/* 1300 */       throw new NullPointerException();  Node<K, V>[] arrayOfNode;
/* 1301 */     if (this.size > 0 && (arrayOfNode = this.table) != null) {
/* 1302 */       int i = this.modCount;
/* 1303 */       for (byte b = 0; b < arrayOfNode.length; b++) {
/* 1304 */         for (Node<K, V> node = arrayOfNode[b]; node != null; node = node.next) {
/* 1305 */           node.value = paramBiFunction.apply(node.key, node.value);
/*      */         }
/*      */       } 
/* 1308 */       if (this.modCount != i) {
/* 1309 */         throw new ConcurrentModificationException();
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
/*      */   public Object clone() {
/*      */     HashMap hashMap;
/*      */     try {
/* 1327 */       hashMap = (HashMap)super.clone();
/* 1328 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 1330 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/* 1332 */     hashMap.reinitialize();
/* 1333 */     hashMap.putMapEntries(this, false);
/* 1334 */     return hashMap;
/*      */   }
/*      */   
/*      */   final float loadFactor() {
/* 1338 */     return this.loadFactor;
/*      */   } final int capacity() {
/* 1340 */     return (this.table != null) ? this.table.length : ((this.threshold > 0) ? this.threshold : 16);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1358 */     int i = capacity();
/*      */     
/* 1360 */     paramObjectOutputStream.defaultWriteObject();
/* 1361 */     paramObjectOutputStream.writeInt(i);
/* 1362 */     paramObjectOutputStream.writeInt(this.size);
/* 1363 */     internalWriteEntries(paramObjectOutputStream);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1376 */     paramObjectInputStream.defaultReadObject();
/* 1377 */     reinitialize();
/* 1378 */     if (this.loadFactor <= 0.0F || Float.isNaN(this.loadFactor)) {
/* 1379 */       throw new InvalidObjectException("Illegal load factor: " + this.loadFactor);
/*      */     }
/* 1381 */     paramObjectInputStream.readInt();
/* 1382 */     int i = paramObjectInputStream.readInt();
/* 1383 */     if (i < 0) {
/* 1384 */       throw new InvalidObjectException("Illegal mappings count: " + i);
/*      */     }
/* 1386 */     if (i > 0) {
/*      */ 
/*      */       
/* 1389 */       float f1 = Math.min(Math.max(0.25F, this.loadFactor), 4.0F);
/* 1390 */       float f2 = i / f1 + 1.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1395 */       byte b1 = (f2 < 16.0F) ? 16 : ((f2 >= 1.07374182E9F) ? 1073741824 : tableSizeFor((int)f2));
/* 1396 */       float f3 = b1 * f1;
/* 1397 */       this.threshold = (b1 < 1073741824 && f3 < 1.07374182E9F) ? (int)f3 : Integer.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1402 */       SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, Map.Entry[].class, b1);
/*      */       
/* 1404 */       Node[] arrayOfNode = new Node[b1];
/* 1405 */       this.table = (Node<K, V>[])arrayOfNode;
/*      */ 
/*      */       
/* 1408 */       for (byte b2 = 0; b2 < i; b2++) {
/*      */         
/* 1410 */         Object object1 = paramObjectInputStream.readObject();
/*      */         
/* 1412 */         Object object2 = paramObjectInputStream.readObject();
/* 1413 */         putVal(hash(object1), (K)object1, (V)object2, false, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   abstract class HashIterator
/*      */   {
/*      */     HashMap.Node<K, V> next;
/*      */ 
/*      */     
/*      */     HashMap.Node<K, V> current;
/*      */ 
/*      */     
/* 1428 */     int expectedModCount = HashMap.this.modCount; int index; HashIterator() {
/* 1429 */       HashMap.Node[] arrayOfNode = HashMap.this.table;
/* 1430 */       this.current = this.next = null;
/* 1431 */       this.index = 0;
/* 1432 */       if (arrayOfNode != null && HashMap.this.size > 0) {
/* 1433 */         do {  } while (this.index < arrayOfNode.length && (this.next = arrayOfNode[this.index++]) == null);
/*      */       }
/*      */     }
/*      */     
/*      */     public final boolean hasNext() {
/* 1438 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final HashMap.Node<K, V> nextNode() {
/* 1443 */       HashMap.Node<K, V> node = this.next;
/* 1444 */       if (HashMap.this.modCount != this.expectedModCount)
/* 1445 */         throw new ConcurrentModificationException(); 
/* 1446 */       if (node == null)
/* 1447 */         throw new NoSuchElementException();  HashMap.Node[] arrayOfNode;
/* 1448 */       if ((this.next = (this.current = node).next) == null && (arrayOfNode = HashMap.this.table) != null) {
/* 1449 */         do {  } while (this.index < arrayOfNode.length && (this.next = arrayOfNode[this.index++]) == null);
/*      */       }
/* 1451 */       return node;
/*      */     }
/*      */     
/*      */     public final void remove() {
/* 1455 */       HashMap.Node<K, V> node = this.current;
/* 1456 */       if (node == null)
/* 1457 */         throw new IllegalStateException(); 
/* 1458 */       if (HashMap.this.modCount != this.expectedModCount)
/* 1459 */         throw new ConcurrentModificationException(); 
/* 1460 */       this.current = null;
/* 1461 */       K k = node.key;
/* 1462 */       HashMap.this.removeNode(HashMap.hash(k), k, (Object)null, false, false);
/* 1463 */       this.expectedModCount = HashMap.this.modCount;
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeyIterator extends HashIterator implements Iterator<K> {
/*      */     public final K next() {
/* 1469 */       return (nextNode()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   final class ValueIterator extends HashIterator implements Iterator<V> { public final V next() {
/* 1474 */       return (nextNode()).value;
/*      */     } }
/*      */   
/*      */   final class EntryIterator extends HashIterator implements Iterator<Map.Entry<K, V>> {
/*      */     public final Map.Entry<K, V> next() {
/* 1479 */       return nextNode();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class HashMapSpliterator<K, V>
/*      */   {
/*      */     final HashMap<K, V> map;
/*      */     
/*      */     HashMap.Node<K, V> current;
/*      */     
/*      */     int index;
/*      */     int fence;
/*      */     int est;
/*      */     int expectedModCount;
/*      */     
/*      */     HashMapSpliterator(HashMap<K, V> param1HashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1496 */       this.map = param1HashMap;
/* 1497 */       this.index = param1Int1;
/* 1498 */       this.fence = param1Int2;
/* 1499 */       this.est = param1Int3;
/* 1500 */       this.expectedModCount = param1Int4;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/* 1505 */       if ((i = this.fence) < 0) {
/* 1506 */         HashMap<K, V> hashMap = this.map;
/* 1507 */         this.est = hashMap.size;
/* 1508 */         this.expectedModCount = hashMap.modCount;
/* 1509 */         HashMap.Node<K, V>[] arrayOfNode = hashMap.table;
/* 1510 */         i = this.fence = (arrayOfNode == null) ? 0 : arrayOfNode.length;
/*      */       } 
/* 1512 */       return i;
/*      */     }
/*      */     
/*      */     public final long estimateSize() {
/* 1516 */       getFence();
/* 1517 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends HashMapSpliterator<K, V>
/*      */     implements Spliterator<K>
/*      */   {
/*      */     KeySpliterator(HashMap<K, V> param1HashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1526 */       super(param1HashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public KeySpliterator<K, V> trySplit() {
/* 1530 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1531 */       return (j >= k || this.current != null) ? null : new KeySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> param1Consumer) {
/*      */       int k;
/* 1538 */       if (param1Consumer == null)
/* 1539 */         throw new NullPointerException(); 
/* 1540 */       HashMap<K, V> hashMap = this.map;
/* 1541 */       HashMap.Node<K, V>[] arrayOfNode = hashMap.table; int j;
/* 1542 */       if ((j = this.fence) < 0) {
/* 1543 */         k = this.expectedModCount = hashMap.modCount;
/* 1544 */         j = this.fence = (arrayOfNode == null) ? 0 : arrayOfNode.length;
/*      */       } else {
/*      */         
/* 1547 */         k = this.expectedModCount;
/* 1548 */       }  int i; if (arrayOfNode != null && arrayOfNode.length >= j && (i = this.index) >= 0 && (i < (this.index = j) || this.current != null)) {
/*      */         
/* 1550 */         HashMap.Node<K, V> node = this.current;
/* 1551 */         this.current = null;
/*      */         while (true) {
/* 1553 */           if (node == null) {
/* 1554 */             node = arrayOfNode[i++];
/*      */           } else {
/* 1556 */             param1Consumer.accept(node.key);
/* 1557 */             node = node.next;
/*      */           } 
/* 1559 */           if (node == null && i >= j) {
/* 1560 */             if (hashMap.modCount != k)
/* 1561 */               throw new ConcurrentModificationException(); 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } public boolean tryAdvance(Consumer<? super K> param1Consumer) {
/* 1567 */       if (param1Consumer == null)
/* 1568 */         throw new NullPointerException(); 
/* 1569 */       HashMap.Node<K, V>[] arrayOfNode = this.map.table; int i;
/* 1570 */       if (arrayOfNode != null && arrayOfNode.length >= (i = getFence()) && this.index >= 0) {
/* 1571 */         while (this.current != null || this.index < i) {
/* 1572 */           if (this.current == null) {
/* 1573 */             this.current = arrayOfNode[this.index++]; continue;
/*      */           } 
/* 1575 */           K k = this.current.key;
/* 1576 */           this.current = this.current.next;
/* 1577 */           param1Consumer.accept(k);
/* 1578 */           if (this.map.modCount != this.expectedModCount)
/* 1579 */             throw new ConcurrentModificationException(); 
/* 1580 */           return true;
/*      */         } 
/*      */       }
/*      */       
/* 1584 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1588 */       return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends HashMapSpliterator<K, V>
/*      */     implements Spliterator<V>
/*      */   {
/*      */     ValueSpliterator(HashMap<K, V> param1HashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1598 */       super(param1HashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public ValueSpliterator<K, V> trySplit() {
/* 1602 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1603 */       return (j >= k || this.current != null) ? null : new ValueSpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super V> param1Consumer) {
/*      */       int k;
/* 1610 */       if (param1Consumer == null)
/* 1611 */         throw new NullPointerException(); 
/* 1612 */       HashMap<K, V> hashMap = this.map;
/* 1613 */       HashMap.Node<K, V>[] arrayOfNode = hashMap.table; int j;
/* 1614 */       if ((j = this.fence) < 0) {
/* 1615 */         k = this.expectedModCount = hashMap.modCount;
/* 1616 */         j = this.fence = (arrayOfNode == null) ? 0 : arrayOfNode.length;
/*      */       } else {
/*      */         
/* 1619 */         k = this.expectedModCount;
/* 1620 */       }  int i; if (arrayOfNode != null && arrayOfNode.length >= j && (i = this.index) >= 0 && (i < (this.index = j) || this.current != null)) {
/*      */         
/* 1622 */         HashMap.Node<K, V> node = this.current;
/* 1623 */         this.current = null;
/*      */         while (true) {
/* 1625 */           if (node == null) {
/* 1626 */             node = arrayOfNode[i++];
/*      */           } else {
/* 1628 */             param1Consumer.accept(node.value);
/* 1629 */             node = node.next;
/*      */           } 
/* 1631 */           if (node == null && i >= j) {
/* 1632 */             if (hashMap.modCount != k)
/* 1633 */               throw new ConcurrentModificationException(); 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } public boolean tryAdvance(Consumer<? super V> param1Consumer) {
/* 1639 */       if (param1Consumer == null)
/* 1640 */         throw new NullPointerException(); 
/* 1641 */       HashMap.Node<K, V>[] arrayOfNode = this.map.table; int i;
/* 1642 */       if (arrayOfNode != null && arrayOfNode.length >= (i = getFence()) && this.index >= 0) {
/* 1643 */         while (this.current != null || this.index < i) {
/* 1644 */           if (this.current == null) {
/* 1645 */             this.current = arrayOfNode[this.index++]; continue;
/*      */           } 
/* 1647 */           V v = this.current.value;
/* 1648 */           this.current = this.current.next;
/* 1649 */           param1Consumer.accept(v);
/* 1650 */           if (this.map.modCount != this.expectedModCount)
/* 1651 */             throw new ConcurrentModificationException(); 
/* 1652 */           return true;
/*      */         } 
/*      */       }
/*      */       
/* 1656 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1660 */       return (this.fence < 0 || this.est == this.map.size) ? 64 : 0;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends HashMapSpliterator<K, V>
/*      */     implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySpliterator(HashMap<K, V> param1HashMap, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1669 */       super(param1HashMap, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public EntrySpliterator<K, V> trySplit() {
/* 1673 */       int i = getFence(), j = this.index, k = j + i >>> 1;
/* 1674 */       return (j >= k || this.current != null) ? null : new EntrySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/*      */       int k;
/* 1681 */       if (param1Consumer == null)
/* 1682 */         throw new NullPointerException(); 
/* 1683 */       HashMap<K, V> hashMap = this.map;
/* 1684 */       HashMap.Node<K, V>[] arrayOfNode = hashMap.table; int j;
/* 1685 */       if ((j = this.fence) < 0) {
/* 1686 */         k = this.expectedModCount = hashMap.modCount;
/* 1687 */         j = this.fence = (arrayOfNode == null) ? 0 : arrayOfNode.length;
/*      */       } else {
/*      */         
/* 1690 */         k = this.expectedModCount;
/* 1691 */       }  int i; if (arrayOfNode != null && arrayOfNode.length >= j && (i = this.index) >= 0 && (i < (this.index = j) || this.current != null)) {
/*      */         
/* 1693 */         HashMap.Node<K, V> node = this.current;
/* 1694 */         this.current = null;
/*      */         while (true) {
/* 1696 */           if (node == null) {
/* 1697 */             node = arrayOfNode[i++];
/*      */           } else {
/* 1699 */             param1Consumer.accept(node);
/* 1700 */             node = node.next;
/*      */           } 
/* 1702 */           if (node == null && i >= j) {
/* 1703 */             if (hashMap.modCount != k)
/* 1704 */               throw new ConcurrentModificationException(); 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 1710 */       if (param1Consumer == null)
/* 1711 */         throw new NullPointerException(); 
/* 1712 */       HashMap.Node<K, V>[] arrayOfNode = this.map.table; int i;
/* 1713 */       if (arrayOfNode != null && arrayOfNode.length >= (i = getFence()) && this.index >= 0) {
/* 1714 */         while (this.current != null || this.index < i) {
/* 1715 */           if (this.current == null) {
/* 1716 */             this.current = arrayOfNode[this.index++]; continue;
/*      */           } 
/* 1718 */           HashMap.Node<K, V> node = this.current;
/* 1719 */           this.current = this.current.next;
/* 1720 */           param1Consumer.accept(node);
/* 1721 */           if (this.map.modCount != this.expectedModCount)
/* 1722 */             throw new ConcurrentModificationException(); 
/* 1723 */           return true;
/*      */         } 
/*      */       }
/*      */       
/* 1727 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1731 */       return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
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
/*      */   Node<K, V> newNode(int paramInt, K paramK, V paramV, Node<K, V> paramNode) {
/* 1750 */     return new Node<>(paramInt, paramK, paramV, paramNode);
/*      */   }
/*      */ 
/*      */   
/*      */   Node<K, V> replacementNode(Node<K, V> paramNode1, Node<K, V> paramNode2) {
/* 1755 */     return new Node<>(paramNode1.hash, paramNode1.key, paramNode1.value, paramNode2);
/*      */   }
/*      */ 
/*      */   
/*      */   TreeNode<K, V> newTreeNode(int paramInt, K paramK, V paramV, Node<K, V> paramNode) {
/* 1760 */     return new TreeNode<>(paramInt, paramK, paramV, paramNode);
/*      */   }
/*      */ 
/*      */   
/*      */   TreeNode<K, V> replacementTreeNode(Node<K, V> paramNode1, Node<K, V> paramNode2) {
/* 1765 */     return new TreeNode<>(paramNode1.hash, paramNode1.key, paramNode1.value, paramNode2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void reinitialize() {
/* 1772 */     this.table = null;
/* 1773 */     this.entrySet = null;
/* 1774 */     this.keySet = null;
/* 1775 */     this.values = null;
/* 1776 */     this.modCount = 0;
/* 1777 */     this.threshold = 0;
/* 1778 */     this.size = 0;
/*      */   }
/*      */   
/*      */   void afterNodeAccess(Node<K, V> paramNode) {}
/*      */   
/*      */   void afterNodeInsertion(boolean paramBoolean) {}
/*      */   
/*      */   void afterNodeRemoval(Node<K, V> paramNode) {}
/*      */   
/*      */   void internalWriteEntries(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     Node<K, V>[] arrayOfNode;
/* 1789 */     if (this.size > 0 && (arrayOfNode = this.table) != null) {
/* 1790 */       for (byte b = 0; b < arrayOfNode.length; b++) {
/* 1791 */         for (Node<K, V> node = arrayOfNode[b]; node != null; node = node.next) {
/* 1792 */           paramObjectOutputStream.writeObject(node.key);
/* 1793 */           paramObjectOutputStream.writeObject(node.value);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class TreeNode<K, V>
/*      */     extends LinkedHashMap.Entry<K, V>
/*      */   {
/*      */     TreeNode<K, V> parent;
/*      */     
/*      */     TreeNode<K, V> left;
/*      */     
/*      */     TreeNode<K, V> right;
/*      */     
/*      */     TreeNode<K, V> prev;
/*      */     
/*      */     boolean red;
/*      */     
/*      */     TreeNode(int param1Int, K param1K, V param1V, HashMap.Node<K, V> param1Node) {
/* 1814 */       super(param1Int, param1K, param1V, param1Node);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final TreeNode<K, V> root() {
/* 1821 */       TreeNode<K, V> treeNode = this; while (true) {
/* 1822 */         TreeNode<K, V> treeNode1; if ((treeNode1 = treeNode.parent) == null)
/* 1823 */           return treeNode; 
/* 1824 */         treeNode = treeNode1;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> void moveRootToFront(HashMap.Node<K, V>[] param1ArrayOfNode, TreeNode<K, V> param1TreeNode) {
/*      */       int i;
/* 1833 */       if (param1TreeNode != null && param1ArrayOfNode != null && (i = param1ArrayOfNode.length) > 0) {
/* 1834 */         int j = i - 1 & param1TreeNode.hash;
/* 1835 */         TreeNode<K, V> treeNode = (TreeNode)param1ArrayOfNode[j];
/* 1836 */         if (param1TreeNode != treeNode) {
/*      */           
/* 1838 */           param1ArrayOfNode[j] = param1TreeNode;
/* 1839 */           TreeNode<K, V> treeNode1 = param1TreeNode.prev; HashMap.Node<K, V> node;
/* 1840 */           if ((node = param1TreeNode.next) != null)
/* 1841 */             ((TreeNode)node).prev = treeNode1; 
/* 1842 */           if (treeNode1 != null)
/* 1843 */             treeNode1.next = node; 
/* 1844 */           if (treeNode != null)
/* 1845 */             treeNode.prev = param1TreeNode; 
/* 1846 */           param1TreeNode.next = treeNode;
/* 1847 */           param1TreeNode.prev = null;
/*      */         } 
/* 1849 */         assert checkInvariants(param1TreeNode);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final TreeNode<K, V> find(int param1Int, Object param1Object, Class<?> param1Class) {
/* 1859 */       TreeNode<K, V> treeNode = this;
/*      */       
/*      */       while (true) {
/* 1862 */         TreeNode<K, V> treeNode1 = treeNode.left, treeNode2 = treeNode.right; int i;
/* 1863 */         if ((i = treeNode.hash) > param1Int)
/* 1864 */         { treeNode = treeNode1; }
/* 1865 */         else if (i < param1Int)
/* 1866 */         { treeNode = treeNode2; }
/* 1867 */         else { K k; if ((k = treeNode.key) == param1Object || (param1Object != null && param1Object.equals(k)))
/* 1868 */             return treeNode; 
/* 1869 */           if (treeNode1 == null)
/* 1870 */           { treeNode = treeNode2; }
/* 1871 */           else if (treeNode2 == null)
/* 1872 */           { treeNode = treeNode1; }
/* 1873 */           else { int j; if ((param1Class != null || (
/* 1874 */               param1Class = HashMap.comparableClassFor(param1Object)) != null) && (
/* 1875 */               j = HashMap.compareComparables(param1Class, param1Object, k)) != 0)
/* 1876 */             { treeNode = (j < 0) ? treeNode1 : treeNode2; }
/* 1877 */             else { TreeNode<K, V> treeNode3; if ((treeNode3 = treeNode2.find(param1Int, param1Object, param1Class)) != null) {
/* 1878 */                 return treeNode3;
/*      */               }
/* 1880 */               treeNode = treeNode1; }  }  }
/* 1881 */          if (treeNode == null) {
/* 1882 */           return null;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     final TreeNode<K, V> getTreeNode(int param1Int, Object param1Object) {
/* 1889 */       return ((this.parent != null) ? root() : this).find(param1Int, param1Object, (Class<?>)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static int tieBreakOrder(Object param1Object1, Object param1Object2) {
/*      */       int i;
/* 1901 */       if (param1Object1 == null || param1Object2 == null || (
/*      */         
/* 1903 */         i = param1Object1.getClass().getName().compareTo(param1Object2.getClass().getName())) == 0) {
/* 1904 */         i = (System.identityHashCode(param1Object1) <= System.identityHashCode(param1Object2)) ? -1 : 1;
/*      */       }
/* 1906 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void treeify(HashMap.Node<K, V>[] param1ArrayOfNode) {
/* 1913 */       TreeNode<K, V> treeNode1 = null;
/* 1914 */       for (TreeNode<K, V> treeNode2 = this; treeNode2 != null; treeNode2 = treeNode) {
/* 1915 */         TreeNode<K, V> treeNode = (TreeNode)treeNode2.next;
/* 1916 */         treeNode2.left = treeNode2.right = null;
/* 1917 */         if (treeNode1 == null) {
/* 1918 */           treeNode2.parent = null;
/* 1919 */           treeNode2.red = false;
/* 1920 */           treeNode1 = treeNode2;
/*      */         } else {
/*      */           
/* 1923 */           K k = treeNode2.key;
/* 1924 */           int i = treeNode2.hash;
/* 1925 */           Class<?> clazz = null;
/* 1926 */           TreeNode<K, V> treeNode3 = treeNode1; while (true) {
/*      */             int j;
/* 1928 */             K k1 = treeNode3.key; int m;
/* 1929 */             if ((m = treeNode3.hash) > i) {
/* 1930 */               j = -1;
/* 1931 */             } else if (m < i) {
/* 1932 */               j = 1;
/* 1933 */             } else if ((clazz == null && (
/* 1934 */               clazz = HashMap.comparableClassFor(k)) == null) || (
/* 1935 */               j = HashMap.compareComparables(clazz, k, k1)) == 0) {
/* 1936 */               j = tieBreakOrder(k, k1);
/*      */             } 
/* 1938 */             TreeNode<K, V> treeNode4 = treeNode3;
/* 1939 */             if ((treeNode3 = (TreeNode<K, V>)((j <= 0) ? treeNode3.left : treeNode3.right)) == null) {
/* 1940 */               treeNode2.parent = treeNode4;
/* 1941 */               if (j <= 0) {
/* 1942 */                 treeNode4.left = treeNode2;
/*      */               } else {
/* 1944 */                 treeNode4.right = treeNode2;
/* 1945 */               }  treeNode1 = balanceInsertion(treeNode1, treeNode2);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1951 */       moveRootToFront(param1ArrayOfNode, treeNode1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final HashMap.Node<K, V> untreeify(HashMap<K, V> param1HashMap) {
/* 1959 */       HashMap.Node<K, V> node1 = null, node2 = null;
/* 1960 */       for (TreeNode<K, V> treeNode = this; treeNode != null; node3 = treeNode.next) {
/* 1961 */         HashMap.Node<K, V> node3, node4 = param1HashMap.replacementNode(treeNode, (HashMap.Node<K, V>)null);
/* 1962 */         if (node2 == null) {
/* 1963 */           node1 = node4;
/*      */         } else {
/* 1965 */           node2.next = node4;
/* 1966 */         }  node2 = node4;
/*      */       } 
/* 1968 */       return node1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final TreeNode<K, V> putTreeVal(HashMap<K, V> param1HashMap, HashMap.Node<K, V>[] param1ArrayOfNode, int param1Int, K param1K, V param1V) {
/* 1976 */       Class<?> clazz = null;
/* 1977 */       boolean bool = false;
/* 1978 */       TreeNode<K, V> treeNode1 = (this.parent != null) ? root() : this;
/* 1979 */       TreeNode<K, V> treeNode2 = treeNode1; while (true) {
/*      */         int i, j;
/* 1981 */         if ((j = treeNode2.hash) > param1Int)
/* 1982 */         { i = -1; }
/* 1983 */         else if (j < param1Int)
/* 1984 */         { i = 1; }
/* 1985 */         else { K k; if ((k = treeNode2.key) == param1K || (param1K != null && param1K.equals(k)))
/* 1986 */             return treeNode2; 
/* 1987 */           if ((clazz == null && (
/* 1988 */             clazz = HashMap.comparableClassFor(param1K)) == null) || (
/* 1989 */             i = HashMap.compareComparables(clazz, param1K, k)) == 0) {
/* 1990 */             if (!bool) {
/*      */               
/* 1992 */               bool = true; TreeNode<K, V> treeNode3, treeNode4;
/* 1993 */               if (((treeNode4 = treeNode2.left) != null && (
/* 1994 */                 treeNode3 = treeNode4.find(param1Int, param1K, clazz)) != null) || ((treeNode4 = treeNode2.right) != null && (
/*      */                 
/* 1996 */                 treeNode3 = treeNode4.find(param1Int, param1K, clazz)) != null))
/* 1997 */                 return treeNode3; 
/*      */             } 
/* 1999 */             i = tieBreakOrder(param1K, k);
/*      */           }  }
/*      */         
/* 2002 */         TreeNode<K, V> treeNode = treeNode2;
/* 2003 */         if ((treeNode2 = (TreeNode<K, V>)((i <= 0) ? treeNode2.left : treeNode2.right)) == null) {
/* 2004 */           HashMap.Node<K, V> node = treeNode.next;
/* 2005 */           TreeNode<K, V> treeNode3 = param1HashMap.newTreeNode(param1Int, param1K, param1V, node);
/* 2006 */           if (i <= 0) {
/* 2007 */             treeNode.left = treeNode3;
/*      */           } else {
/* 2009 */             treeNode.right = treeNode3;
/* 2010 */           }  treeNode.next = treeNode3;
/* 2011 */           treeNode3.parent = treeNode3.prev = treeNode;
/* 2012 */           if (node != null)
/* 2013 */             ((TreeNode)node).prev = treeNode3; 
/* 2014 */           moveRootToFront(param1ArrayOfNode, balanceInsertion(treeNode1, treeNode3));
/* 2015 */           return null;
/*      */         } 
/*      */       } 
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
/*      */     final void removeTreeNode(HashMap<K, V> param1HashMap, HashMap.Node<K, V>[] param1ArrayOfNode, boolean param1Boolean) {
/*      */       TreeNode<K, V> treeNode9;
/*      */       int i;
/* 2033 */       if (param1ArrayOfNode == null || (i = param1ArrayOfNode.length) == 0)
/*      */         return; 
/* 2035 */       int j = i - 1 & this.hash;
/* 2036 */       TreeNode<K, V> treeNode1 = (TreeNode)param1ArrayOfNode[j], treeNode2 = treeNode1;
/* 2037 */       TreeNode<K, V> treeNode4 = (TreeNode)this.next, treeNode5 = this.prev;
/* 2038 */       if (treeNode5 == null) {
/* 2039 */         param1ArrayOfNode[j] = treeNode1 = treeNode4;
/*      */       } else {
/* 2041 */         treeNode5.next = treeNode4;
/* 2042 */       }  if (treeNode4 != null)
/* 2043 */         treeNode4.prev = treeNode5; 
/* 2044 */       if (treeNode1 == null)
/*      */         return; 
/* 2046 */       if (treeNode2.parent != null)
/* 2047 */         treeNode2 = treeNode2.root();  TreeNode<K, V> treeNode3;
/* 2048 */       if (treeNode2 == null || (param1Boolean && (treeNode2.right == null || (treeNode3 = treeNode2.left) == null || treeNode3.left == null))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2053 */         param1ArrayOfNode[j] = treeNode1.untreeify(param1HashMap);
/*      */         return;
/*      */       } 
/* 2056 */       TreeNode<K, V> treeNode6 = this, treeNode7 = this.left, treeNode8 = this.right;
/* 2057 */       if (treeNode7 != null && treeNode8 != null) {
/* 2058 */         TreeNode<K, V> treeNode11 = treeNode8; TreeNode<K, V> treeNode12;
/* 2059 */         while ((treeNode12 = treeNode11.left) != null)
/* 2060 */           treeNode11 = treeNode12; 
/* 2061 */         boolean bool = treeNode11.red; treeNode11.red = treeNode6.red; treeNode6.red = bool;
/* 2062 */         TreeNode<K, V> treeNode13 = treeNode11.right;
/* 2063 */         TreeNode<K, V> treeNode14 = treeNode6.parent;
/* 2064 */         if (treeNode11 == treeNode8) {
/* 2065 */           treeNode6.parent = treeNode11;
/* 2066 */           treeNode11.right = treeNode6;
/*      */         } else {
/*      */           
/* 2069 */           TreeNode<K, V> treeNode = treeNode11.parent;
/* 2070 */           if ((treeNode6.parent = treeNode) != null)
/* 2071 */             if (treeNode11 == treeNode.left) {
/* 2072 */               treeNode.left = treeNode6;
/*      */             } else {
/* 2074 */               treeNode.right = treeNode6;
/*      */             }  
/* 2076 */           if ((treeNode11.right = treeNode8) != null)
/* 2077 */             treeNode8.parent = treeNode11; 
/*      */         } 
/* 2079 */         treeNode6.left = null;
/* 2080 */         if ((treeNode6.right = treeNode13) != null)
/* 2081 */           treeNode13.parent = treeNode6; 
/* 2082 */         if ((treeNode11.left = treeNode7) != null)
/* 2083 */           treeNode7.parent = treeNode11; 
/* 2084 */         if ((treeNode11.parent = treeNode14) == null) {
/* 2085 */           treeNode2 = treeNode11;
/* 2086 */         } else if (treeNode6 == treeNode14.left) {
/* 2087 */           treeNode14.left = treeNode11;
/*      */         } else {
/* 2089 */           treeNode14.right = treeNode11;
/* 2090 */         }  if (treeNode13 != null) {
/* 2091 */           treeNode9 = treeNode13;
/*      */         } else {
/* 2093 */           treeNode9 = treeNode6;
/*      */         } 
/* 2095 */       } else if (treeNode7 != null) {
/* 2096 */         treeNode9 = treeNode7;
/* 2097 */       } else if (treeNode8 != null) {
/* 2098 */         treeNode9 = treeNode8;
/*      */       } else {
/* 2100 */         treeNode9 = treeNode6;
/* 2101 */       }  if (treeNode9 != treeNode6) {
/* 2102 */         TreeNode<K, V> treeNode = treeNode9.parent = treeNode6.parent;
/* 2103 */         if (treeNode == null) {
/* 2104 */           treeNode2 = treeNode9;
/* 2105 */         } else if (treeNode6 == treeNode.left) {
/* 2106 */           treeNode.left = treeNode9;
/*      */         } else {
/* 2108 */           treeNode.right = treeNode9;
/* 2109 */         }  treeNode6.left = treeNode6.right = treeNode6.parent = null;
/*      */       } 
/*      */       
/* 2112 */       TreeNode<K, V> treeNode10 = treeNode6.red ? treeNode2 : balanceDeletion(treeNode2, treeNode9);
/*      */       
/* 2114 */       if (treeNode9 == treeNode6) {
/* 2115 */         TreeNode<K, V> treeNode = treeNode6.parent;
/* 2116 */         treeNode6.parent = null;
/* 2117 */         if (treeNode != null)
/* 2118 */           if (treeNode6 == treeNode.left) {
/* 2119 */             treeNode.left = null;
/* 2120 */           } else if (treeNode6 == treeNode.right) {
/* 2121 */             treeNode.right = null;
/*      */           }  
/*      */       } 
/* 2124 */       if (param1Boolean) {
/* 2125 */         moveRootToFront(param1ArrayOfNode, treeNode10);
/*      */       }
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
/*      */     final void split(HashMap<K, V> param1HashMap, HashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2) {
/* 2139 */       TreeNode<K, V> treeNode1 = this;
/*      */       
/* 2141 */       TreeNode<K, V> treeNode2 = null, treeNode3 = null;
/* 2142 */       TreeNode<K, V> treeNode4 = null, treeNode5 = null;
/* 2143 */       byte b1 = 0, b2 = 0;
/* 2144 */       for (TreeNode<K, V> treeNode6 = treeNode1; treeNode6 != null; treeNode6 = treeNode) {
/* 2145 */         TreeNode<K, V> treeNode = (TreeNode)treeNode6.next;
/* 2146 */         treeNode6.next = null;
/* 2147 */         if ((treeNode6.hash & param1Int2) == 0) {
/* 2148 */           if ((treeNode6.prev = treeNode3) == null) {
/* 2149 */             treeNode2 = treeNode6;
/*      */           } else {
/* 2151 */             treeNode3.next = treeNode6;
/* 2152 */           }  treeNode3 = treeNode6;
/* 2153 */           b1++;
/*      */         } else {
/*      */           
/* 2156 */           if ((treeNode6.prev = treeNode5) == null) {
/* 2157 */             treeNode4 = treeNode6;
/*      */           } else {
/* 2159 */             treeNode5.next = treeNode6;
/* 2160 */           }  treeNode5 = treeNode6;
/* 2161 */           b2++;
/*      */         } 
/*      */       } 
/*      */       
/* 2165 */       if (treeNode2 != null)
/* 2166 */         if (b1 <= 6) {
/* 2167 */           param1ArrayOfNode[param1Int1] = treeNode2.untreeify(param1HashMap);
/*      */         } else {
/* 2169 */           param1ArrayOfNode[param1Int1] = treeNode2;
/* 2170 */           if (treeNode4 != null) {
/* 2171 */             treeNode2.treeify(param1ArrayOfNode);
/*      */           }
/*      */         }  
/* 2174 */       if (treeNode4 != null) {
/* 2175 */         if (b2 <= 6) {
/* 2176 */           param1ArrayOfNode[param1Int1 + param1Int2] = treeNode4.untreeify(param1HashMap);
/*      */         } else {
/* 2178 */           param1ArrayOfNode[param1Int1 + param1Int2] = treeNode4;
/* 2179 */           if (treeNode2 != null) {
/* 2180 */             treeNode4.treeify(param1ArrayOfNode);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> param1TreeNode1, TreeNode<K, V> param1TreeNode2) {
/*      */       TreeNode<K, V> treeNode;
/* 2191 */       if (param1TreeNode2 != null && (treeNode = param1TreeNode2.right) != null) {
/* 2192 */         TreeNode<K, V> treeNode2; if ((treeNode2 = param1TreeNode2.right = treeNode.left) != null)
/* 2193 */           treeNode2.parent = param1TreeNode2;  TreeNode<K, V> treeNode1;
/* 2194 */         if ((treeNode1 = treeNode.parent = param1TreeNode2.parent) == null) {
/* 2195 */           (param1TreeNode1 = treeNode).red = false;
/* 2196 */         } else if (treeNode1.left == param1TreeNode2) {
/* 2197 */           treeNode1.left = treeNode;
/*      */         } else {
/* 2199 */           treeNode1.right = treeNode;
/* 2200 */         }  treeNode.left = param1TreeNode2;
/* 2201 */         param1TreeNode2.parent = treeNode;
/*      */       } 
/* 2203 */       return param1TreeNode1;
/*      */     }
/*      */ 
/*      */     
/*      */     static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> param1TreeNode1, TreeNode<K, V> param1TreeNode2) {
/*      */       TreeNode<K, V> treeNode;
/* 2209 */       if (param1TreeNode2 != null && (treeNode = param1TreeNode2.left) != null) {
/* 2210 */         TreeNode<K, V> treeNode2; if ((treeNode2 = param1TreeNode2.left = treeNode.right) != null)
/* 2211 */           treeNode2.parent = param1TreeNode2;  TreeNode<K, V> treeNode1;
/* 2212 */         if ((treeNode1 = treeNode.parent = param1TreeNode2.parent) == null) {
/* 2213 */           (param1TreeNode1 = treeNode).red = false;
/* 2214 */         } else if (treeNode1.right == param1TreeNode2) {
/* 2215 */           treeNode1.right = treeNode;
/*      */         } else {
/* 2217 */           treeNode1.left = treeNode;
/* 2218 */         }  treeNode.right = param1TreeNode2;
/* 2219 */         param1TreeNode2.parent = treeNode;
/*      */       } 
/* 2221 */       return param1TreeNode1;
/*      */     }
/*      */ 
/*      */     
/*      */     static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> param1TreeNode1, TreeNode<K, V> param1TreeNode2) {
/* 2226 */       param1TreeNode2.red = true; while (true) {
/*      */         TreeNode<K, V> treeNode1;
/* 2228 */         if ((treeNode1 = param1TreeNode2.parent) == null) {
/* 2229 */           param1TreeNode2.red = false;
/* 2230 */           return param1TreeNode2;
/*      */         }  TreeNode<K, V> treeNode2;
/* 2232 */         if (!treeNode1.red || (treeNode2 = treeNode1.parent) == null)
/* 2233 */           return param1TreeNode1;  TreeNode<K, V> treeNode3;
/* 2234 */         if (treeNode1 == (treeNode3 = treeNode2.left)) {
/* 2235 */           TreeNode<K, V> treeNode; if ((treeNode = treeNode2.right) != null && treeNode.red) {
/* 2236 */             treeNode.red = false;
/* 2237 */             treeNode1.red = false;
/* 2238 */             treeNode2.red = true;
/* 2239 */             param1TreeNode2 = treeNode2;
/*      */             continue;
/*      */           } 
/* 2242 */           if (param1TreeNode2 == treeNode1.right) {
/* 2243 */             param1TreeNode1 = rotateLeft(param1TreeNode1, param1TreeNode2 = treeNode1);
/* 2244 */             treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.parent;
/*      */           } 
/* 2246 */           if (treeNode1 != null) {
/* 2247 */             treeNode1.red = false;
/* 2248 */             if (treeNode2 != null) {
/* 2249 */               treeNode2.red = true;
/* 2250 */               param1TreeNode1 = rotateRight(param1TreeNode1, treeNode2);
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/* 2256 */         if (treeNode3 != null && treeNode3.red) {
/* 2257 */           treeNode3.red = false;
/* 2258 */           treeNode1.red = false;
/* 2259 */           treeNode2.red = true;
/* 2260 */           param1TreeNode2 = treeNode2;
/*      */           continue;
/*      */         } 
/* 2263 */         if (param1TreeNode2 == treeNode1.left) {
/* 2264 */           param1TreeNode1 = rotateRight(param1TreeNode1, param1TreeNode2 = treeNode1);
/* 2265 */           treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.parent;
/*      */         } 
/* 2267 */         if (treeNode1 != null) {
/* 2268 */           treeNode1.red = false;
/* 2269 */           if (treeNode2 != null) {
/* 2270 */             treeNode2.red = true;
/* 2271 */             param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> param1TreeNode1, TreeNode<K, V> param1TreeNode2) {
/*      */       while (true) {
/* 2282 */         if (param1TreeNode2 == null || param1TreeNode2 == param1TreeNode1)
/* 2283 */           return param1TreeNode1;  TreeNode<K, V> treeNode1;
/* 2284 */         if ((treeNode1 = param1TreeNode2.parent) == null) {
/* 2285 */           param1TreeNode2.red = false;
/* 2286 */           return param1TreeNode2;
/*      */         } 
/* 2288 */         if (param1TreeNode2.red) {
/* 2289 */           param1TreeNode2.red = false;
/* 2290 */           return param1TreeNode1;
/*      */         }  TreeNode<K, V> treeNode2;
/* 2292 */         if ((treeNode2 = treeNode1.left) == param1TreeNode2) {
/* 2293 */           TreeNode<K, V> treeNode5; if ((treeNode5 = treeNode1.right) != null && treeNode5.red) {
/* 2294 */             treeNode5.red = false;
/* 2295 */             treeNode1.red = true;
/* 2296 */             param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode1);
/* 2297 */             treeNode5 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.right;
/*      */           } 
/* 2299 */           if (treeNode5 == null) {
/* 2300 */             param1TreeNode2 = treeNode1; continue;
/*      */           } 
/* 2302 */           TreeNode<K, V> treeNode6 = treeNode5.left, treeNode7 = treeNode5.right;
/* 2303 */           if ((treeNode7 == null || !treeNode7.red) && (treeNode6 == null || !treeNode6.red)) {
/*      */             
/* 2305 */             treeNode5.red = true;
/* 2306 */             param1TreeNode2 = treeNode1;
/*      */             continue;
/*      */           } 
/* 2309 */           if (treeNode7 == null || !treeNode7.red) {
/* 2310 */             if (treeNode6 != null)
/* 2311 */               treeNode6.red = false; 
/* 2312 */             treeNode5.red = true;
/* 2313 */             param1TreeNode1 = rotateRight(param1TreeNode1, treeNode5);
/* 2314 */             treeNode5 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.right;
/*      */           } 
/*      */           
/* 2317 */           if (treeNode5 != null) {
/* 2318 */             treeNode5.red = (treeNode1 == null) ? false : treeNode1.red;
/* 2319 */             if ((treeNode7 = treeNode5.right) != null)
/* 2320 */               treeNode7.red = false; 
/*      */           } 
/* 2322 */           if (treeNode1 != null) {
/* 2323 */             treeNode1.red = false;
/* 2324 */             param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode1);
/*      */           } 
/* 2326 */           param1TreeNode2 = param1TreeNode1;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 2331 */         if (treeNode2 != null && treeNode2.red) {
/* 2332 */           treeNode2.red = false;
/* 2333 */           treeNode1.red = true;
/* 2334 */           param1TreeNode1 = rotateRight(param1TreeNode1, treeNode1);
/* 2335 */           treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.left;
/*      */         } 
/* 2337 */         if (treeNode2 == null) {
/* 2338 */           param1TreeNode2 = treeNode1; continue;
/*      */         } 
/* 2340 */         TreeNode<K, V> treeNode3 = treeNode2.left, treeNode4 = treeNode2.right;
/* 2341 */         if ((treeNode3 == null || !treeNode3.red) && (treeNode4 == null || !treeNode4.red)) {
/*      */           
/* 2343 */           treeNode2.red = true;
/* 2344 */           param1TreeNode2 = treeNode1;
/*      */           continue;
/*      */         } 
/* 2347 */         if (treeNode3 == null || !treeNode3.red) {
/* 2348 */           if (treeNode4 != null)
/* 2349 */             treeNode4.red = false; 
/* 2350 */           treeNode2.red = true;
/* 2351 */           param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode2);
/* 2352 */           treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.left;
/*      */         } 
/*      */         
/* 2355 */         if (treeNode2 != null) {
/* 2356 */           treeNode2.red = (treeNode1 == null) ? false : treeNode1.red;
/* 2357 */           if ((treeNode3 = treeNode2.left) != null)
/* 2358 */             treeNode3.red = false; 
/*      */         } 
/* 2360 */         if (treeNode1 != null) {
/* 2361 */           treeNode1.red = false;
/* 2362 */           param1TreeNode1 = rotateRight(param1TreeNode1, treeNode1);
/*      */         } 
/* 2364 */         param1TreeNode2 = param1TreeNode1;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> boolean checkInvariants(TreeNode<K, V> param1TreeNode) {
/* 2375 */       TreeNode<K, V> treeNode1 = param1TreeNode.parent, treeNode2 = param1TreeNode.left, treeNode3 = param1TreeNode.right;
/* 2376 */       TreeNode<K, V> treeNode4 = param1TreeNode.prev; TreeNode treeNode = (TreeNode)param1TreeNode.next;
/* 2377 */       if (treeNode4 != null && treeNode4.next != param1TreeNode)
/* 2378 */         return false; 
/* 2379 */       if (treeNode != null && treeNode.prev != param1TreeNode)
/* 2380 */         return false; 
/* 2381 */       if (treeNode1 != null && param1TreeNode != treeNode1.left && param1TreeNode != treeNode1.right)
/* 2382 */         return false; 
/* 2383 */       if (treeNode2 != null && (treeNode2.parent != param1TreeNode || treeNode2.hash > param1TreeNode.hash))
/* 2384 */         return false; 
/* 2385 */       if (treeNode3 != null && (treeNode3.parent != param1TreeNode || treeNode3.hash < param1TreeNode.hash))
/* 2386 */         return false; 
/* 2387 */       if (param1TreeNode.red && treeNode2 != null && treeNode2.red && treeNode3 != null && treeNode3.red)
/* 2388 */         return false; 
/* 2389 */       if (treeNode2 != null && !checkInvariants(treeNode2))
/* 2390 */         return false; 
/* 2391 */       if (treeNode3 != null && !checkInvariants(treeNode3))
/* 2392 */         return false; 
/* 2393 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/HashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */