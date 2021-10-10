/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.Spliterator;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleBinaryOperator;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.IntBinaryOperator;
/*      */ import java.util.function.LongBinaryOperator;
/*      */ import java.util.function.ToDoubleBiFunction;
/*      */ import java.util.function.ToDoubleFunction;
/*      */ import java.util.function.ToIntBiFunction;
/*      */ import java.util.function.ToIntFunction;
/*      */ import java.util.function.ToLongBiFunction;
/*      */ import java.util.function.ToLongFunction;
/*      */ import sun.misc.Contended;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConcurrentHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements ConcurrentMap<K, V>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7249069246763182397L;
/*      */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   private static final int DEFAULT_CAPACITY = 16;
/*      */   static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
/*      */   private static final float LOAD_FACTOR = 0.75F;
/*      */   static final int TREEIFY_THRESHOLD = 8;
/*      */   static final int UNTREEIFY_THRESHOLD = 6;
/*      */   static final int MIN_TREEIFY_CAPACITY = 64;
/*      */   private static final int MIN_TRANSFER_STRIDE = 16;
/*  578 */   private static int RESIZE_STAMP_BITS = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  584 */   private static final int MAX_RESIZERS = (1 << 32 - RESIZE_STAMP_BITS) - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  589 */   private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
/*      */ 
/*      */   
/*      */   static final int MOVED = -1;
/*      */   
/*      */   static final int TREEBIN = -2;
/*      */   
/*      */   static final int RESERVED = -3;
/*      */   
/*      */   static final int HASH_BITS = 2147483647;
/*      */   
/*  600 */   static final int NCPU = Runtime.getRuntime().availableProcessors(); volatile transient Node<K, V>[] table; private volatile transient Node<K, V>[] nextTable; private volatile transient long baseCount; private volatile transient int sizeCtl; private volatile transient int transferIndex; private volatile transient int cellsBusy; private volatile transient CounterCell[] counterCells;
/*      */   private transient KeySetView<K, V> keySet;
/*      */   private transient ValuesView<K, V> values;
/*  603 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", int.class), new ObjectStreamField("segmentShift", int.class) };
/*      */   
/*      */   private transient EntrySetView<K, V> entrySet;
/*      */   
/*      */   private static final Unsafe U;
/*      */   
/*      */   private static final long SIZECTL;
/*      */   private static final long TRANSFERINDEX;
/*      */   private static final long BASECOUNT;
/*      */   private static final long CELLSBUSY;
/*      */   private static final long CELLVALUE;
/*      */   private static final long ABASE;
/*      */   private static final int ASHIFT;
/*      */   
/*      */   static class Node<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final K key;
/*      */     volatile V val;
/*      */     volatile Node<K, V> next;
/*      */     
/*      */     Node(int param1Int, K param1K, V param1V, Node<K, V> param1Node) {
/*  626 */       this.hash = param1Int;
/*  627 */       this.key = param1K;
/*  628 */       this.val = param1V;
/*  629 */       this.next = param1Node;
/*      */     }
/*      */     
/*  632 */     public final K getKey() { return this.key; }
/*  633 */     public final V getValue() { return this.val; }
/*  634 */     public final int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); } public final String toString() {
/*  635 */       return (new StringBuilder()).append(this.key).append("=").append(this.val).toString();
/*      */     }
/*  637 */     public final V setValue(V param1V) { throw new UnsupportedOperationException(); } public final boolean equals(Object param1Object) {
/*      */       K k;
/*      */       Object object;
/*      */       V v;
/*      */       Map.Entry<Object, ?> entry;
/*  642 */       return (param1Object instanceof Map.Entry && (
/*  643 */         k = (K)(entry = (Map.Entry)param1Object).getKey()) != null && (
/*  644 */         object = entry.getValue()) != null && (k == this.key || k
/*  645 */         .equals(this.key)) && (object == (v = this.val) || object
/*  646 */         .equals(v)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Node<K, V> find(int param1Int, Object param1Object) {
/*  653 */       Node<K, V> node = this;
/*  654 */       if (param1Object != null) {
/*      */         do {
/*      */           K k;
/*  657 */           if (node.hash == param1Int && ((k = node.key) == param1Object || (k != null && param1Object
/*  658 */             .equals(k))))
/*  659 */             return node; 
/*  660 */         } while ((node = node.next) != null);
/*      */       }
/*  662 */       return null;
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
/*      */   static final int spread(int paramInt) {
/*  685 */     return (paramInt ^ paramInt >>> 16) & Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int tableSizeFor(int paramInt) {
/*  693 */     int i = paramInt - 1;
/*  694 */     i |= i >>> 1;
/*  695 */     i |= i >>> 2;
/*  696 */     i |= i >>> 4;
/*  697 */     i |= i >>> 8;
/*  698 */     i |= i >>> 16;
/*  699 */     return (i < 0) ? 1 : ((i >= 1073741824) ? 1073741824 : (i + 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Class<?> comparableClassFor(Object paramObject) {
/*  707 */     if (paramObject instanceof Comparable) {
/*      */       Class<?> clazz;
/*  709 */       if ((clazz = paramObject.getClass()) == String.class)
/*  710 */         return clazz;  Type[] arrayOfType;
/*  711 */       if ((arrayOfType = clazz.getGenericInterfaces()) != null)
/*  712 */         for (byte b = 0; b < arrayOfType.length; b++) {
/*  713 */           Type[] arrayOfType1; Type type; ParameterizedType parameterizedType; if (type = arrayOfType[b] instanceof ParameterizedType && (parameterizedType = (ParameterizedType)type)
/*  714 */             .getRawType() == Comparable.class && (
/*      */             
/*  716 */             arrayOfType1 = parameterizedType.getActualTypeArguments()) != null && arrayOfType1.length == 1 && arrayOfType1[0] == clazz)
/*      */           {
/*  718 */             return clazz;
/*      */           }
/*      */         }  
/*      */     } 
/*  722 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compareComparables(Class<?> paramClass, Object paramObject1, Object paramObject2) {
/*  731 */     return (paramObject2 == null || paramObject2.getClass() != paramClass) ? 0 : ((Comparable<Object>)paramObject1)
/*  732 */       .compareTo(paramObject2);
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
/*      */   static final <K, V> Node<K, V> tabAt(Node<K, V>[] paramArrayOfNode, int paramInt) {
/*  755 */     return (Node<K, V>)U.getObjectVolatile(paramArrayOfNode, (paramInt << ASHIFT) + ABASE);
/*      */   }
/*      */ 
/*      */   
/*      */   static final <K, V> boolean casTabAt(Node<K, V>[] paramArrayOfNode, int paramInt, Node<K, V> paramNode1, Node<K, V> paramNode2) {
/*  760 */     return U.compareAndSwapObject(paramArrayOfNode, (paramInt << ASHIFT) + ABASE, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   static final <K, V> void setTabAt(Node<K, V>[] paramArrayOfNode, int paramInt, Node<K, V> paramNode) {
/*  764 */     U.putObjectVolatile(paramArrayOfNode, (paramInt << ASHIFT) + ABASE, paramNode);
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
/*      */   public ConcurrentHashMap() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentHashMap(int paramInt) {
/*  837 */     if (paramInt < 0) {
/*  838 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  841 */     int i = (paramInt >= 536870912) ? 1073741824 : tableSizeFor(paramInt + (paramInt >>> 1) + 1);
/*  842 */     this.sizeCtl = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentHashMap(Map<? extends K, ? extends V> paramMap) {
/*  851 */     this.sizeCtl = 16;
/*  852 */     putAll(paramMap);
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
/*      */   public ConcurrentHashMap(int paramInt, float paramFloat) {
/*  871 */     this(paramInt, paramFloat, 1);
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
/*      */   public ConcurrentHashMap(int paramInt1, float paramFloat, int paramInt2) {
/*  894 */     if (paramFloat <= 0.0F || paramInt1 < 0 || paramInt2 <= 0)
/*  895 */       throw new IllegalArgumentException(); 
/*  896 */     if (paramInt1 < paramInt2)
/*  897 */       paramInt1 = paramInt2; 
/*  898 */     long l = (long)(1.0D + ((float)paramInt1 / paramFloat));
/*      */     
/*  900 */     int i = (l >= 1073741824L) ? 1073741824 : tableSizeFor((int)l);
/*  901 */     this.sizeCtl = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  910 */     long l = sumCount();
/*  911 */     return (l < 0L) ? 0 : ((l > 2147483647L) ? Integer.MAX_VALUE : (int)l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  920 */     return (sumCount() <= 0L);
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
/*      */   public V get(Object paramObject) {
/*  936 */     int j = spread(paramObject.hashCode()); Node<K, V> arrayOfNode[], node; int i;
/*  937 */     if ((arrayOfNode = this.table) != null && (i = arrayOfNode.length) > 0 && (
/*  938 */       node = tabAt(arrayOfNode, i - 1 & j)) != null) {
/*  939 */       int k; if ((k = node.hash) == j) {
/*  940 */         K k1; if ((k1 = node.key) == paramObject || (k1 != null && paramObject.equals(k1))) {
/*  941 */           return node.val;
/*      */         }
/*  943 */       } else if (k < 0) {
/*  944 */         Node<K, V> node1; return ((node1 = node.find(j, paramObject)) != null) ? node1.val : null;
/*  945 */       }  while ((node = node.next) != null) {
/*  946 */         K k1; if (node.hash == j && ((k1 = node.key) == paramObject || (k1 != null && paramObject
/*  947 */           .equals(k1))))
/*  948 */           return node.val; 
/*      */       } 
/*      */     } 
/*  951 */     return null;
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
/*  964 */     return (get(paramObject) != null);
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
/*      */   public boolean containsValue(Object paramObject) {
/*  978 */     if (paramObject == null)
/*  979 */       throw new NullPointerException(); 
/*      */     Node<K, V>[] arrayOfNode;
/*  981 */     if ((arrayOfNode = this.table) != null) {
/*  982 */       Traverser<K, V> traverser = new Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); Node<K, V> node;
/*  983 */       while ((node = traverser.advance()) != null) {
/*      */         V v;
/*  985 */         if ((v = node.val) == paramObject || (v != null && paramObject.equals(v)))
/*  986 */           return true; 
/*      */       } 
/*      */     } 
/*  989 */     return false;
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
/* 1006 */     return putVal(paramK, paramV, false);
/*      */   }
/*      */ 
/*      */   
/*      */   final V putVal(K paramK, V paramV, boolean paramBoolean) {
/* 1011 */     if (paramK == null || paramV == null) throw new NullPointerException(); 
/* 1012 */     int i = spread(paramK.hashCode());
/* 1013 */     byte b = 0;
/* 1014 */     Node<K, V>[] arrayOfNode = this.table; while (true) {
/*      */       Node[] arrayOfNode1; V v; int j;
/* 1016 */       if (arrayOfNode == null || (j = arrayOfNode.length) == 0) {
/* 1017 */         arrayOfNode1 = (Node[])initTable(); continue;
/* 1018 */       }  Node<?, ?> node; int k; if ((node = tabAt((Node<?, ?>[])arrayOfNode1, k = j - 1 & i)) == null) {
/* 1019 */         if (casTabAt((Node<?, ?>[])arrayOfNode1, k, null, new Node<>(i, paramK, paramV, null)))
/*      */           break;  continue;
/*      */       } 
/*      */       int m;
/* 1023 */       if ((m = node.hash) == -1) {
/* 1024 */         arrayOfNode1 = (Node[])helpTransfer((Node<K, V>[])arrayOfNode1, (Node)node); continue;
/*      */       } 
/* 1026 */       Object object = null;
/* 1027 */       synchronized (node) {
/* 1028 */         if (tabAt((Node<?, ?>[])arrayOfNode1, k) == node)
/* 1029 */           if (m >= 0) {
/* 1030 */             b = 1;
/* 1031 */             for (Node<?, ?> node1 = node;; b++) {
/*      */               K k1;
/* 1033 */               if (node1.hash == i && ((k1 = node1.key) == paramK || (k1 != null && paramK
/*      */                 
/* 1035 */                 .equals(k1)))) {
/* 1036 */                 v = node1.val;
/* 1037 */                 if (!paramBoolean)
/* 1038 */                   node1.val = paramV; 
/*      */                 break;
/*      */               } 
/* 1041 */               Node<?, ?> node2 = node1;
/* 1042 */               if ((node1 = node1.next) == null) {
/* 1043 */                 node2.next = new Node<>(i, paramK, paramV, null);
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1049 */           } else if (node instanceof TreeBin) {
/*      */             
/* 1051 */             b = 2; TreeNode<K, V> treeNode;
/* 1052 */             if ((treeNode = ((TreeBin)node).putTreeVal(i, paramK, paramV)) != null) {
/*      */               
/* 1054 */               v = treeNode.val;
/* 1055 */               if (!paramBoolean) {
/* 1056 */                 treeNode.val = paramV;
/*      */               }
/*      */             } 
/*      */           }  
/*      */       } 
/* 1061 */       if (b != 0) {
/* 1062 */         if (b >= 8)
/* 1063 */           treeifyBin((Node<K, V>[])arrayOfNode1, k); 
/* 1064 */         if (v != null) {
/* 1065 */           return v;
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 1070 */     addCount(1L, b);
/* 1071 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap) {
/* 1082 */     tryPresize(paramMap.size());
/* 1083 */     for (Map.Entry<? extends K, ? extends V> entry : paramMap.entrySet()) {
/* 1084 */       putVal((K)entry.getKey(), (V)entry.getValue(), false);
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
/* 1097 */     return replaceNode(paramObject, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final V replaceNode(Object paramObject1, V paramV, Object paramObject2) {
/* 1106 */     int i = spread(paramObject1.hashCode());
/* 1107 */     Node<K, V>[] arrayOfNode = this.table; Node<K, V> node;
/*      */     int j, k;
/* 1109 */     while (arrayOfNode != null && (j = arrayOfNode.length) != 0 && (
/* 1110 */       node = tabAt(arrayOfNode, k = j - 1 & i)) != null) {
/*      */       Node[] arrayOfNode1; V v; int m;
/* 1112 */       if ((m = node.hash) == -1) {
/* 1113 */         arrayOfNode1 = (Node[])helpTransfer(arrayOfNode, node); continue;
/*      */       } 
/* 1115 */       Object object = null;
/* 1116 */       boolean bool = false;
/* 1117 */       synchronized (node) {
/* 1118 */         if (tabAt((Node<K, V>[])arrayOfNode1, k) == node)
/* 1119 */           if (m >= 0) {
/* 1120 */             bool = true;
/* 1121 */             Node<K, V> node1 = node, node2 = null; do {
/*      */               K k1;
/* 1123 */               if (node1.hash == i && ((k1 = node1.key) == paramObject1 || (k1 != null && paramObject1
/*      */                 
/* 1125 */                 .equals(k1)))) {
/* 1126 */                 V v1 = node1.val;
/* 1127 */                 if (paramObject2 == null || paramObject2 == v1 || (v1 != null && paramObject2
/* 1128 */                   .equals(v1))) {
/* 1129 */                   v = v1;
/* 1130 */                   if (paramV != null) {
/* 1131 */                     node1.val = paramV; break;
/* 1132 */                   }  if (node2 != null) {
/* 1133 */                     node2.next = node1.next; break;
/*      */                   } 
/* 1135 */                   setTabAt((Node<K, V>[])arrayOfNode1, k, node1.next);
/*      */                 } 
/*      */                 break;
/*      */               } 
/* 1139 */               node2 = node1;
/* 1140 */             } while ((node1 = node1.next) != null);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1145 */             bool = true;
/* 1146 */             TreeBin treeBin = (TreeBin)node;
/*      */             TreeNode treeNode1, treeNode2;
/* 1148 */             if (node instanceof TreeBin && (treeNode1 = treeBin.root) != null && (
/* 1149 */               treeNode2 = treeNode1.findTreeNode(i, paramObject1, (Class<?>)null)) != null) {
/* 1150 */               V v1 = treeNode2.val;
/* 1151 */               if (paramObject2 == null || paramObject2 == v1 || (v1 != null && paramObject2
/* 1152 */                 .equals(v1))) {
/* 1153 */                 v = v1;
/* 1154 */                 if (paramV != null) {
/* 1155 */                   treeNode2.val = paramV;
/* 1156 */                 } else if (treeBin.removeTreeNode(treeNode2)) {
/* 1157 */                   setTabAt((Node<?, ?>[])arrayOfNode1, k, untreeify(treeBin.first));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }  
/*      */       } 
/* 1163 */       if (bool) {
/* 1164 */         if (v != null) {
/* 1165 */           if (paramV == null)
/* 1166 */             addCount(-1L, -1); 
/* 1167 */           return v;
/*      */         } 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1173 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/* 1180 */     long l = 0L;
/* 1181 */     byte b = 0;
/* 1182 */     Node<K, V>[] arrayOfNode = this.table;
/* 1183 */     while (arrayOfNode != null && b < arrayOfNode.length) {
/*      */       Node[] arrayOfNode1;
/* 1185 */       Node<K, V> node = tabAt(arrayOfNode, b);
/* 1186 */       if (node == null) {
/* 1187 */         b++; continue;
/* 1188 */       }  int i; if ((i = node.hash) == -1) {
/* 1189 */         arrayOfNode1 = (Node[])helpTransfer(arrayOfNode, node);
/* 1190 */         b = 0;
/*      */         continue;
/*      */       } 
/* 1193 */       synchronized (node) {
/* 1194 */         if (tabAt((Node<K, V>[])arrayOfNode1, b) == node) {
/* 1195 */           Node node1 = (i >= 0) ? node : ((node instanceof TreeBin) ? ((TreeBin)node).first : null);
/*      */ 
/*      */           
/* 1198 */           while (node1 != null) {
/* 1199 */             l--;
/* 1200 */             node1 = node1.next;
/*      */           } 
/* 1202 */           setTabAt((Node<?, ?>[])arrayOfNode1, b++, null);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1207 */     if (l != 0L) {
/* 1208 */       addCount(l, -1);
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
/*      */   public KeySetView<K, V> keySet() {
/*      */     KeySetView<K, V> keySetView;
/* 1231 */     return ((keySetView = this.keySet) != null) ? keySetView : (this.keySet = new KeySetView<>(this, null));
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
/*      */   public Collection<V> values() {
/*      */     ValuesView<K, V> valuesView;
/* 1254 */     return ((valuesView = this.values) != null) ? valuesView : (this.values = new ValuesView<>(this));
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
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/*      */     EntrySetView<K, V> entrySetView;
/* 1276 */     return ((entrySetView = this.entrySet) != null) ? entrySetView : (this.entrySet = new EntrySetView<>(this));
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
/* 1287 */     int i = 0;
/*      */     Node<K, V>[] arrayOfNode;
/* 1289 */     if ((arrayOfNode = this.table) != null) {
/* 1290 */       Traverser<K, V> traverser = new Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); Node<K, V> node;
/* 1291 */       while ((node = traverser.advance()) != null)
/* 1292 */         i += node.key.hashCode() ^ node.val.hashCode(); 
/*      */     } 
/* 1294 */     return i;
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
/*      */   public String toString() {
/*      */     Node<K, V>[] arrayOfNode;
/* 1310 */     boolean bool = ((arrayOfNode = this.table) == null) ? false : arrayOfNode.length;
/* 1311 */     Traverser<K, V> traverser = new Traverser<>(arrayOfNode, bool, 0, bool);
/* 1312 */     StringBuilder stringBuilder = new StringBuilder();
/* 1313 */     stringBuilder.append('{');
/*      */     Node<K, V> node;
/* 1315 */     if ((node = traverser.advance()) != null) {
/*      */       while (true) {
/* 1317 */         K k = node.key;
/* 1318 */         V v = node.val;
/* 1319 */         stringBuilder.append((k == this) ? "(this Map)" : k);
/* 1320 */         stringBuilder.append('=');
/* 1321 */         stringBuilder.append((v == this) ? "(this Map)" : v);
/* 1322 */         if ((node = traverser.advance()) == null)
/*      */           break; 
/* 1324 */         stringBuilder.append(',').append(' ');
/*      */       } 
/*      */     }
/* 1327 */     return stringBuilder.append('}').toString();
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
/*      */   public boolean equals(Object paramObject) {
/* 1341 */     if (paramObject != this) {
/* 1342 */       if (!(paramObject instanceof Map))
/* 1343 */         return false; 
/* 1344 */       Map map = (Map)paramObject;
/*      */       Node<K, V>[] arrayOfNode;
/* 1346 */       boolean bool = ((arrayOfNode = this.table) == null) ? false : arrayOfNode.length;
/* 1347 */       Traverser<K, V> traverser = new Traverser<>(arrayOfNode, bool, 0, bool); Node<K, V> node;
/* 1348 */       while ((node = traverser.advance()) != null) {
/* 1349 */         V v = node.val;
/* 1350 */         Object object = map.get(node.key);
/* 1351 */         if (object == null || (object != v && !object.equals(v)))
/* 1352 */           return false; 
/*      */       } 
/* 1354 */       for (Map.Entry entry : map.entrySet()) {
/*      */         Object object1; Object object2; V v;
/* 1356 */         if ((object1 = entry.getKey()) == null || (
/* 1357 */           object2 = entry.getValue()) == null || (
/* 1358 */           v = get(object1)) == null || (object2 != v && 
/* 1359 */           !object2.equals(v)))
/* 1360 */           return false; 
/*      */       } 
/*      */     } 
/* 1363 */     return true;
/*      */   }
/*      */   
/*      */   static class Segment<K, V>
/*      */     extends ReentrantLock
/*      */     implements Serializable {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     final float loadFactor;
/*      */     
/*      */     Segment(float param1Float) {
/* 1373 */       this.loadFactor = param1Float;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1390 */     byte b1 = 0;
/* 1391 */     int i = 1;
/* 1392 */     while (i < 16) {
/* 1393 */       b1++;
/* 1394 */       i <<= 1;
/*      */     } 
/* 1396 */     int j = 32 - b1;
/* 1397 */     int k = i - 1;
/*      */     
/* 1399 */     Segment[] arrayOfSegment = new Segment[16];
/*      */     
/* 1401 */     for (byte b2 = 0; b2 < arrayOfSegment.length; b2++)
/* 1402 */       arrayOfSegment[b2] = new Segment<>(0.75F); 
/* 1403 */     paramObjectOutputStream.putFields().put("segments", arrayOfSegment);
/* 1404 */     paramObjectOutputStream.putFields().put("segmentShift", j);
/* 1405 */     paramObjectOutputStream.putFields().put("segmentMask", k);
/* 1406 */     paramObjectOutputStream.writeFields();
/*      */     
/*      */     Node<K, V>[] arrayOfNode;
/* 1409 */     if ((arrayOfNode = this.table) != null) {
/* 1410 */       Traverser<K, V> traverser = new Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); Node<K, V> node;
/* 1411 */       while ((node = traverser.advance()) != null) {
/* 1412 */         paramObjectOutputStream.writeObject(node.key);
/* 1413 */         paramObjectOutputStream.writeObject(node.val);
/*      */       } 
/*      */     } 
/* 1416 */     paramObjectOutputStream.writeObject(null);
/* 1417 */     paramObjectOutputStream.writeObject(null);
/* 1418 */     arrayOfSegment = null;
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1437 */     this.sizeCtl = -1;
/* 1438 */     paramObjectInputStream.defaultReadObject();
/* 1439 */     long l = 0L;
/* 1440 */     Node<Object, Object> node = null;
/*      */     
/*      */     while (true) {
/* 1443 */       Object object1 = paramObjectInputStream.readObject();
/*      */       
/* 1445 */       Object object2 = paramObjectInputStream.readObject();
/* 1446 */       if (object1 != null && object2 != null) {
/* 1447 */         node = new Node<>(spread(object1.hashCode()), object1, object2, node);
/* 1448 */         l++;
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/* 1453 */     if (l == 0L) {
/* 1454 */       this.sizeCtl = 0;
/*      */     } else {
/*      */       int i;
/* 1457 */       if (l >= 536870912L) {
/* 1458 */         i = 1073741824;
/*      */       } else {
/* 1460 */         int k = (int)l;
/* 1461 */         i = tableSizeFor(k + (k >>> 1) + 1);
/*      */       } 
/*      */       
/* 1464 */       Node[] arrayOfNode = new Node[i];
/* 1465 */       int j = i - 1;
/* 1466 */       long l1 = 0L;
/* 1467 */       while (node != null) {
/*      */         boolean bool;
/* 1469 */         Node<Object, Object> node1 = node.next;
/* 1470 */         int k = node.hash, m = k & j; Node<?, ?> node2;
/* 1471 */         if ((node2 = tabAt((Node<?, ?>[])arrayOfNode, m)) == null) {
/* 1472 */           bool = true;
/*      */         } else {
/* 1474 */           K k1 = node.key;
/* 1475 */           if (node2.hash < 0) {
/* 1476 */             TreeBin<K, V> treeBin = (TreeBin)node2;
/* 1477 */             if (treeBin.putTreeVal(k, k1, node.val) == null)
/* 1478 */               l1++; 
/* 1479 */             bool = false;
/*      */           } else {
/*      */             
/* 1482 */             byte b = 0;
/* 1483 */             bool = true;
/*      */             Node<?, ?> node3;
/* 1485 */             for (node3 = node2; node3 != null; node3 = node3.next) {
/* 1486 */               K k2; if (node3.hash == k && ((k2 = node3.key) == k1 || (k2 != null && k1
/*      */                 
/* 1488 */                 .equals(k2)))) {
/* 1489 */                 bool = false;
/*      */                 break;
/*      */               } 
/* 1492 */               b++;
/*      */             } 
/* 1494 */             if (bool && b >= 8) {
/* 1495 */               bool = false;
/* 1496 */               l1++;
/* 1497 */               node.next = (Node)node2;
/* 1498 */               TreeNode<K, V> treeNode1 = null, treeNode2 = null;
/* 1499 */               for (node3 = node; node3 != null; node3 = node3.next) {
/* 1500 */                 TreeNode<K, V> treeNode = new TreeNode<>(node3.hash, node3.key, node3.val, null, null);
/*      */                 
/* 1502 */                 if ((treeNode.prev = treeNode2) == null) {
/* 1503 */                   treeNode1 = treeNode;
/*      */                 } else {
/* 1505 */                   treeNode2.next = treeNode;
/* 1506 */                 }  treeNode2 = treeNode;
/*      */               } 
/* 1508 */               setTabAt((Node<?, ?>[])arrayOfNode, m, new TreeBin<>(treeNode1));
/*      */             } 
/*      */           } 
/*      */         } 
/* 1512 */         if (bool) {
/* 1513 */           l1++;
/* 1514 */           node.next = (Node)node2;
/* 1515 */           setTabAt((Node<Object, Object>[])arrayOfNode, m, node);
/*      */         } 
/* 1517 */         node = node1;
/*      */       } 
/* 1519 */       this.table = (Node<K, V>[])arrayOfNode;
/* 1520 */       this.sizeCtl = i - (i >>> 2);
/* 1521 */       this.baseCount = l1;
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
/*      */   public V putIfAbsent(K paramK, V paramV) {
/* 1535 */     return putVal(paramK, paramV, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object paramObject1, Object paramObject2) {
/* 1544 */     if (paramObject1 == null)
/* 1545 */       throw new NullPointerException(); 
/* 1546 */     return (paramObject2 != null && replaceNode(paramObject1, null, paramObject2) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K paramK, V paramV1, V paramV2) {
/* 1555 */     if (paramK == null || paramV1 == null || paramV2 == null)
/* 1556 */       throw new NullPointerException(); 
/* 1557 */     return (replaceNode(paramK, paramV2, paramV1) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(K paramK, V paramV) {
/* 1568 */     if (paramK == null || paramV == null)
/* 1569 */       throw new NullPointerException(); 
/* 1570 */     return replaceNode(paramK, paramV, null);
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
/*      */   public V getOrDefault(Object paramObject, V paramV) {
/*      */     V v;
/* 1588 */     return ((v = get(paramObject)) == null) ? paramV : v;
/*      */   }
/*      */   
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 1592 */     if (paramBiConsumer == null) throw new NullPointerException(); 
/*      */     Node<K, V>[] arrayOfNode;
/* 1594 */     if ((arrayOfNode = this.table) != null) {
/* 1595 */       Traverser<K, V> traverser = new Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); Node<K, V> node;
/* 1596 */       while ((node = traverser.advance()) != null) {
/* 1597 */         paramBiConsumer.accept(node.key, node.val);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1603 */     if (paramBiFunction == null) throw new NullPointerException(); 
/*      */     Node<K, V>[] arrayOfNode;
/* 1605 */     if ((arrayOfNode = this.table) != null) {
/* 1606 */       Traverser<K, V> traverser = new Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); Node<K, V> node;
/* 1607 */       label22: while ((node = traverser.advance()) != null) {
/* 1608 */         V v = node.val;
/* 1609 */         K k = node.key; while (true) {
/* 1610 */           V v1 = paramBiFunction.apply(k, v);
/* 1611 */           if (v1 == null)
/* 1612 */             throw new NullPointerException(); 
/* 1613 */           if (replaceNode(k, v1, v) == null) {
/* 1614 */             if ((v = get(k)) == null) {
/*      */               continue label22;
/*      */             }
/*      */             continue;
/*      */           } 
/*      */           continue label22;
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
/*      */   public V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction) {
/* 1644 */     if (paramK == null || paramFunction == null)
/* 1645 */       throw new NullPointerException(); 
/* 1646 */     int i = spread(paramK.hashCode());
/* 1647 */     V v = null;
/* 1648 */     byte b = 0;
/* 1649 */     Node<K, V>[] arrayOfNode = this.table; while (true) {
/*      */       Node[] arrayOfNode1; int j;
/* 1651 */       if (arrayOfNode == null || (j = arrayOfNode.length) == 0) {
/* 1652 */         arrayOfNode1 = (Node[])initTable(); continue;
/* 1653 */       }  Node<?, ?> node; int k; if ((node = tabAt((Node<?, ?>[])arrayOfNode1, k = j - 1 & i)) == null) {
/* 1654 */         ReservationNode<Object, Object> reservationNode = new ReservationNode<>();
/* 1655 */         synchronized (reservationNode) {
/* 1656 */           if (casTabAt((Node<Object, Object>[])arrayOfNode1, k, null, reservationNode)) {
/* 1657 */             b = 1;
/* 1658 */             Node<K, V> node1 = null;
/*      */             try {
/* 1660 */               if ((v = paramFunction.apply(paramK)) != null)
/* 1661 */                 node1 = new Node<>(i, paramK, v, null); 
/*      */             } finally {
/* 1663 */               setTabAt((Node<K, V>[])arrayOfNode1, k, node1);
/*      */             } 
/*      */           } 
/*      */         } 
/* 1667 */         if (b)
/*      */           break;  continue;
/*      */       }  int m;
/* 1670 */       if ((m = node.hash) == -1) {
/* 1671 */         arrayOfNode1 = (Node[])helpTransfer((Node<K, V>[])arrayOfNode1, (Node)node); continue;
/*      */       } 
/* 1673 */       boolean bool = false;
/* 1674 */       synchronized (node) {
/* 1675 */         if (tabAt((Node<?, ?>[])arrayOfNode1, k) == node) {
/* 1676 */           if (m >= 0) {
/* 1677 */             b = 1;
/* 1678 */             for (Node<?, ?> node1 = node;; b++) {
/*      */               K k1;
/* 1680 */               if (node1.hash == i && ((k1 = node1.key) == paramK || (k1 != null && paramK
/*      */                 
/* 1682 */                 .equals(k1)))) {
/* 1683 */                 v = node1.val;
/*      */                 break;
/*      */               } 
/* 1686 */               Node<?, ?> node2 = node1;
/* 1687 */               if ((node1 = node1.next) == null) {
/* 1688 */                 if ((v = paramFunction.apply(paramK)) != null) {
/* 1689 */                   bool = true;
/* 1690 */                   node2.next = new Node<>(i, paramK, v, null);
/*      */                 } 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1696 */           } else if (node instanceof TreeBin) {
/* 1697 */             b = 2;
/* 1698 */             TreeBin<K, V> treeBin = (TreeBin)node;
/*      */             TreeNode treeNode1, treeNode2;
/* 1700 */             if ((treeNode1 = treeBin.root) != null && (
/* 1701 */               treeNode2 = treeNode1.findTreeNode(i, paramK, (Class<?>)null)) != null) {
/* 1702 */               v = treeNode2.val;
/* 1703 */             } else if ((v = paramFunction.apply(paramK)) != null) {
/* 1704 */               bool = true;
/* 1705 */               treeBin.putTreeVal(i, paramK, v);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/* 1710 */       if (b != 0) {
/* 1711 */         if (b >= 8)
/* 1712 */           treeifyBin((Node<K, V>[])arrayOfNode1, k); 
/* 1713 */         if (!bool) {
/* 1714 */           return v;
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 1719 */     if (v != null)
/* 1720 */       addCount(1L, b); 
/* 1721 */     return v;
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
/*      */   public V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1745 */     if (paramK == null || paramBiFunction == null)
/* 1746 */       throw new NullPointerException(); 
/* 1747 */     int i = spread(paramK.hashCode());
/* 1748 */     V v = null;
/* 1749 */     byte b = 0;
/* 1750 */     byte b1 = 0;
/* 1751 */     Node<K, V>[] arrayOfNode = this.table; while (true) {
/*      */       Node[] arrayOfNode1; int j;
/* 1753 */       if (arrayOfNode == null || (j = arrayOfNode.length) == 0) {
/* 1754 */         arrayOfNode1 = (Node[])initTable(); continue;
/* 1755 */       }  Node<?, ?> node; int k; if ((node = tabAt((Node<?, ?>[])arrayOfNode1, k = j - 1 & i)) == null)
/*      */         break;  int m;
/* 1757 */       if ((m = node.hash) == -1) {
/* 1758 */         arrayOfNode1 = (Node[])helpTransfer((Node<K, V>[])arrayOfNode1, (Node)node); continue;
/*      */       } 
/* 1760 */       synchronized (node) {
/* 1761 */         if (tabAt((Node<?, ?>[])arrayOfNode1, k) == node)
/* 1762 */           if (m >= 0) {
/* 1763 */             b1 = 1;
/* 1764 */             for (Node<?, ?> node1 = node, node2 = null;; b1++) {
/*      */               K k1;
/* 1766 */               if (node1.hash == i && ((k1 = node1.key) == paramK || (k1 != null && paramK
/*      */                 
/* 1768 */                 .equals(k1)))) {
/* 1769 */                 v = paramBiFunction.apply(paramK, node1.val);
/* 1770 */                 if (v != null) {
/* 1771 */                   node1.val = v; break;
/*      */                 } 
/* 1773 */                 b = -1;
/* 1774 */                 Node<?, ?> node3 = node1.next;
/* 1775 */                 if (node2 != null) {
/* 1776 */                   node2.next = (Node)node3; break;
/*      */                 } 
/* 1778 */                 setTabAt((Node<?, ?>[])arrayOfNode1, k, node3);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1782 */               node2 = node1;
/* 1783 */               if ((node1 = node1.next) == null) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } else {
/* 1788 */             b1 = 2;
/* 1789 */             TreeBin treeBin = (TreeBin)node;
/*      */             TreeNode treeNode1, treeNode2;
/* 1791 */             if (node instanceof TreeBin && (treeNode1 = treeBin.root) != null && (
/* 1792 */               treeNode2 = treeNode1.findTreeNode(i, paramK, (Class<?>)null)) != null) {
/* 1793 */               v = paramBiFunction.apply(paramK, treeNode2.val);
/* 1794 */               if (v != null) {
/* 1795 */                 treeNode2.val = v;
/*      */               } else {
/* 1797 */                 b = -1;
/* 1798 */                 if (treeBin.removeTreeNode(treeNode2)) {
/* 1799 */                   setTabAt((Node<?, ?>[])arrayOfNode1, k, untreeify(treeBin.first));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }  
/*      */       } 
/* 1805 */       if (b1 != 0) {
/*      */         break;
/*      */       }
/*      */     } 
/* 1809 */     if (b != 0)
/* 1810 */       addCount(b, b1); 
/* 1811 */     return v;
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
/*      */   public V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1836 */     if (paramK == null || paramBiFunction == null)
/* 1837 */       throw new NullPointerException(); 
/* 1838 */     int i = spread(paramK.hashCode());
/* 1839 */     V v = null;
/* 1840 */     byte b = 0;
/* 1841 */     byte b1 = 0;
/* 1842 */     Node<K, V>[] arrayOfNode = this.table; while (true) {
/*      */       Node[] arrayOfNode1; int j;
/* 1844 */       if (arrayOfNode == null || (j = arrayOfNode.length) == 0) {
/* 1845 */         arrayOfNode1 = (Node[])initTable(); continue;
/* 1846 */       }  Node<?, ?> node; int k; if ((node = tabAt((Node<?, ?>[])arrayOfNode1, k = j - 1 & i)) == null) {
/* 1847 */         ReservationNode<Object, Object> reservationNode = new ReservationNode<>();
/* 1848 */         synchronized (reservationNode) {
/* 1849 */           if (casTabAt((Node<Object, Object>[])arrayOfNode1, k, null, reservationNode)) {
/* 1850 */             b1 = 1;
/* 1851 */             Node<K, V> node1 = null;
/*      */             try {
/* 1853 */               if ((v = paramBiFunction.apply(paramK, null)) != null) {
/* 1854 */                 b = 1;
/* 1855 */                 node1 = new Node<>(i, paramK, v, null);
/*      */               } 
/*      */             } finally {
/* 1858 */               setTabAt((Node<K, V>[])arrayOfNode1, k, node1);
/*      */             } 
/*      */           } 
/*      */         } 
/* 1862 */         if (b1)
/*      */           break;  continue;
/*      */       }  int m;
/* 1865 */       if ((m = node.hash) == -1) {
/* 1866 */         arrayOfNode1 = (Node[])helpTransfer((Node<K, V>[])arrayOfNode1, (Node)node); continue;
/*      */       } 
/* 1868 */       synchronized (node) {
/* 1869 */         if (tabAt((Node<?, ?>[])arrayOfNode1, k) == node)
/* 1870 */           if (m >= 0) {
/* 1871 */             b1 = 1;
/* 1872 */             for (Node<?, ?> node1 = node, node2 = null;; b1++) {
/*      */               K k1;
/* 1874 */               if (node1.hash == i && ((k1 = node1.key) == paramK || (k1 != null && paramK
/*      */                 
/* 1876 */                 .equals(k1)))) {
/* 1877 */                 v = paramBiFunction.apply(paramK, node1.val);
/* 1878 */                 if (v != null) {
/* 1879 */                   node1.val = v; break;
/*      */                 } 
/* 1881 */                 b = -1;
/* 1882 */                 Node<?, ?> node3 = node1.next;
/* 1883 */                 if (node2 != null) {
/* 1884 */                   node2.next = (Node)node3; break;
/*      */                 } 
/* 1886 */                 setTabAt((Node<?, ?>[])arrayOfNode1, k, node3);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1890 */               node2 = node1;
/* 1891 */               if ((node1 = node1.next) == null) {
/* 1892 */                 v = paramBiFunction.apply(paramK, null);
/* 1893 */                 if (v != null) {
/* 1894 */                   b = 1;
/* 1895 */                   node2.next = new Node<>(i, paramK, v, null);
/*      */                 } 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1902 */           } else if (node instanceof TreeBin) {
/* 1903 */             TreeNode treeNode2; b1 = 1;
/* 1904 */             TreeBin<K, V> treeBin = (TreeBin)node;
/*      */             TreeNode treeNode1;
/* 1906 */             if ((treeNode1 = treeBin.root) != null) {
/* 1907 */               treeNode2 = treeNode1.findTreeNode(i, paramK, (Class<?>)null);
/*      */             } else {
/* 1909 */               treeNode2 = null;
/* 1910 */             }  V v1 = (treeNode2 == null) ? null : treeNode2.val;
/* 1911 */             v = paramBiFunction.apply(paramK, v1);
/* 1912 */             if (v != null) {
/* 1913 */               if (treeNode2 != null) {
/* 1914 */                 treeNode2.val = v;
/*      */               } else {
/* 1916 */                 b = 1;
/* 1917 */                 treeBin.putTreeVal(i, paramK, v);
/*      */               }
/*      */             
/* 1920 */             } else if (treeNode2 != null) {
/* 1921 */               b = -1;
/* 1922 */               if (treeBin.removeTreeNode(treeNode2)) {
/* 1923 */                 setTabAt((Node<?, ?>[])arrayOfNode1, k, untreeify(treeBin.first));
/*      */               }
/*      */             } 
/*      */           }  
/*      */       } 
/* 1928 */       if (b1 != 0) {
/* 1929 */         if (b1 >= 8) {
/* 1930 */           treeifyBin((Node<K, V>[])arrayOfNode1, k);
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 1935 */     if (b != 0)
/* 1936 */       addCount(b, b1); 
/* 1937 */     return v;
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
/*      */   public V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction) {
/* 1961 */     if (paramK == null || paramV == null || paramBiFunction == null)
/* 1962 */       throw new NullPointerException(); 
/* 1963 */     int i = spread(paramK.hashCode());
/* 1964 */     V v = null;
/* 1965 */     byte b = 0;
/* 1966 */     byte b1 = 0;
/* 1967 */     Node<K, V>[] arrayOfNode = this.table; while (true) {
/*      */       Node[] arrayOfNode1; int j;
/* 1969 */       if (arrayOfNode == null || (j = arrayOfNode.length) == 0) {
/* 1970 */         arrayOfNode1 = (Node[])initTable(); continue;
/* 1971 */       }  Node<?, ?> node; int k; if ((node = tabAt((Node<?, ?>[])arrayOfNode1, k = j - 1 & i)) == null) {
/* 1972 */         if (casTabAt((Node<?, ?>[])arrayOfNode1, k, null, new Node<>(i, paramK, paramV, null))) {
/* 1973 */           b = 1;
/* 1974 */           v = paramV; break;
/*      */         }  continue;
/*      */       } 
/*      */       int m;
/* 1978 */       if ((m = node.hash) == -1) {
/* 1979 */         arrayOfNode1 = (Node[])helpTransfer((Node<K, V>[])arrayOfNode1, (Node)node); continue;
/*      */       } 
/* 1981 */       synchronized (node) {
/* 1982 */         if (tabAt((Node<?, ?>[])arrayOfNode1, k) == node)
/* 1983 */           if (m >= 0) {
/* 1984 */             b1 = 1;
/* 1985 */             for (Node<?, ?> node1 = node, node2 = null;; b1++) {
/*      */               K k1;
/* 1987 */               if (node1.hash == i && ((k1 = node1.key) == paramK || (k1 != null && paramK
/*      */                 
/* 1989 */                 .equals(k1)))) {
/* 1990 */                 v = paramBiFunction.apply(node1.val, paramV);
/* 1991 */                 if (v != null) {
/* 1992 */                   node1.val = v; break;
/*      */                 } 
/* 1994 */                 b = -1;
/* 1995 */                 Node<?, ?> node3 = node1.next;
/* 1996 */                 if (node2 != null) {
/* 1997 */                   node2.next = (Node)node3; break;
/*      */                 } 
/* 1999 */                 setTabAt((Node<?, ?>[])arrayOfNode1, k, node3);
/*      */                 
/*      */                 break;
/*      */               } 
/* 2003 */               node2 = node1;
/* 2004 */               if ((node1 = node1.next) == null) {
/* 2005 */                 b = 1;
/* 2006 */                 v = paramV;
/* 2007 */                 node2.next = new Node<>(i, paramK, v, null);
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 2013 */           } else if (node instanceof TreeBin) {
/* 2014 */             b1 = 2;
/* 2015 */             TreeBin<K, V> treeBin = (TreeBin)node;
/* 2016 */             TreeNode<K, V> treeNode1 = treeBin.root;
/*      */             
/* 2018 */             TreeNode<K, V> treeNode2 = (treeNode1 == null) ? null : treeNode1.findTreeNode(i, paramK, (Class<?>)null);
/*      */             
/* 2020 */             v = (treeNode2 == null) ? paramV : paramBiFunction.apply(treeNode2.val, paramV);
/* 2021 */             if (v != null) {
/* 2022 */               if (treeNode2 != null) {
/* 2023 */                 treeNode2.val = v;
/*      */               } else {
/* 2025 */                 b = 1;
/* 2026 */                 treeBin.putTreeVal(i, paramK, v);
/*      */               }
/*      */             
/* 2029 */             } else if (treeNode2 != null) {
/* 2030 */               b = -1;
/* 2031 */               if (treeBin.removeTreeNode(treeNode2)) {
/* 2032 */                 setTabAt((Node<?, ?>[])arrayOfNode1, k, untreeify(treeBin.first));
/*      */               }
/*      */             } 
/*      */           }  
/*      */       } 
/* 2037 */       if (b1 != 0) {
/* 2038 */         if (b1 >= 8) {
/* 2039 */           treeifyBin((Node<K, V>[])arrayOfNode1, k);
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 2044 */     if (b != 0)
/* 2045 */       addCount(b, b1); 
/* 2046 */     return v;
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
/*      */   public boolean contains(Object paramObject) {
/* 2067 */     return containsValue(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<K> keys() {
/*      */     Node<K, V>[] arrayOfNode;
/* 2078 */     boolean bool = ((arrayOfNode = this.table) == null) ? false : arrayOfNode.length;
/* 2079 */     return new KeyIterator<>(arrayOfNode, bool, 0, bool, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<V> elements() {
/*      */     Node<K, V>[] arrayOfNode;
/* 2090 */     boolean bool = ((arrayOfNode = this.table) == null) ? false : arrayOfNode.length;
/* 2091 */     return new ValueIterator<>(arrayOfNode, bool, 0, bool, this);
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
/*      */   public long mappingCount() {
/* 2107 */     long l = sumCount();
/* 2108 */     return (l < 0L) ? 0L : l;
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
/*      */   public static <K> KeySetView<K, Boolean> newKeySet() {
/* 2120 */     return new KeySetView<>(new ConcurrentHashMap<>(), Boolean.TRUE);
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
/*      */   public static <K> KeySetView<K, Boolean> newKeySet(int paramInt) {
/* 2137 */     return new KeySetView<>(new ConcurrentHashMap<>(paramInt), Boolean.TRUE);
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
/*      */   public KeySetView<K, V> keySet(V paramV) {
/* 2153 */     if (paramV == null)
/* 2154 */       throw new NullPointerException(); 
/* 2155 */     return new KeySetView<>(this, paramV);
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForwardingNode<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     final ConcurrentHashMap.Node<K, V>[] nextTable;
/*      */ 
/*      */     
/*      */     ForwardingNode(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode) {
/* 2166 */       super(-1, null, null, null);
/* 2167 */       this.nextTable = param1ArrayOfNode;
/*      */     }
/*      */ 
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> find(int param1Int, Object param1Object) {
/* 2172 */       ConcurrentHashMap.Node<K, V>[] arrayOfNode = this.nextTable; label27: while (true) {
/*      */         ConcurrentHashMap.Node<K, V> node; int i;
/* 2174 */         if (param1Object == null || arrayOfNode == null || (i = arrayOfNode.length) == 0 || (
/* 2175 */           node = ConcurrentHashMap.<K, V>tabAt(arrayOfNode, i - 1 & param1Int)) == null)
/* 2176 */           return null;  while (true) {
/*      */           int j;
/*      */           K k;
/* 2179 */           if ((j = node.hash) == param1Int && ((k = node.key) == param1Object || (k != null && param1Object
/* 2180 */             .equals(k))))
/* 2181 */             return node; 
/* 2182 */           if (j < 0) {
/* 2183 */             if (node instanceof ForwardingNode) {
/* 2184 */               arrayOfNode = ((ForwardingNode)node).nextTable;
/*      */               
/*      */               continue label27;
/*      */             } 
/* 2188 */             return node.find(param1Int, param1Object);
/*      */           } 
/* 2190 */           if ((node = node.next) == null)
/* 2191 */             return null; 
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReservationNode<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     ReservationNode() {
/* 2202 */       super(-3, null, null, null);
/*      */     }
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> find(int param1Int, Object param1Object) {
/* 2206 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int resizeStamp(int paramInt) {
/* 2217 */     return Integer.numberOfLeadingZeros(paramInt) | 1 << RESIZE_STAMP_BITS - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final Node<K, V>[] initTable() {
/*      */     Node[] arrayOfNode;
/*      */     Node<K, V>[] arrayOfNode1;
/* 2225 */     while ((arrayOfNode1 = this.table) == null || arrayOfNode1.length == 0) {
/* 2226 */       int i; if ((i = this.sizeCtl) < 0) {
/* 2227 */         Thread.yield(); continue;
/* 2228 */       }  if (U.compareAndSwapInt(this, SIZECTL, i, -1)) {
/*      */         try {
/* 2230 */           if ((arrayOfNode1 = this.table) == null || arrayOfNode1.length == 0) {
/* 2231 */             byte b = (i > 0) ? i : 16;
/*      */             
/* 2233 */             Node[] arrayOfNode2 = new Node[b];
/* 2234 */             this.table = (Node<K, V>[])(arrayOfNode = arrayOfNode2);
/* 2235 */             i = b - (b >>> 2);
/*      */           } 
/*      */         } finally {
/* 2238 */           this.sizeCtl = i;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 2243 */     return (Node<K, V>[])arrayOfNode;
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
/*      */   private final void addCount(long paramLong, int paramInt) {
/*      */     CounterCell[] arrayOfCounterCell;
/*      */     long l1;
/*      */     long l2;
/* 2258 */     if ((arrayOfCounterCell = this.counterCells) != null || 
/* 2259 */       !U.compareAndSwapLong(this, BASECOUNT, l1 = this.baseCount, l2 = l1 + paramLong)) {
/*      */       
/* 2261 */       boolean bool = true; CounterCell counterCell; long l; int i;
/* 2262 */       if (arrayOfCounterCell == null || (i = arrayOfCounterCell.length - 1) < 0 || (
/* 2263 */         counterCell = arrayOfCounterCell[ThreadLocalRandom.getProbe() & i]) == null || 
/*      */         
/* 2265 */         !(bool = U.compareAndSwapLong(counterCell, CELLVALUE, l = counterCell.value, l + paramLong))) {
/* 2266 */         fullAddCount(paramLong, bool);
/*      */         return;
/*      */       } 
/* 2269 */       if (paramInt <= 1)
/*      */         return; 
/* 2271 */       l2 = sumCount();
/*      */     } 
/* 2273 */     if (paramInt >= 0) {
/*      */       Node<K, V>[] arrayOfNode; int i; int j;
/* 2275 */       while (l2 >= (j = this.sizeCtl) && (arrayOfNode = this.table) != null && (i = arrayOfNode.length) < 1073741824) {
/*      */         
/* 2277 */         int k = resizeStamp(i);
/* 2278 */         if (j < 0) {
/* 2279 */           Node<K, V>[] arrayOfNode1; if (j >>> RESIZE_STAMP_SHIFT != k || j == k + 1 || j == k + MAX_RESIZERS || (arrayOfNode1 = this.nextTable) == null || this.transferIndex <= 0) {
/*      */             break;
/*      */           }
/*      */           
/* 2283 */           if (U.compareAndSwapInt(this, SIZECTL, j, j + 1)) {
/* 2284 */             transfer(arrayOfNode, arrayOfNode1);
/*      */           }
/* 2286 */         } else if (U.compareAndSwapInt(this, SIZECTL, j, (k << RESIZE_STAMP_SHIFT) + 2)) {
/*      */           
/* 2288 */           transfer(arrayOfNode, null);
/* 2289 */         }  l2 = sumCount();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node<K, V>[] helpTransfer(Node<K, V>[] paramArrayOfNode, Node<K, V> paramNode) {
/*      */     Node[] arrayOfNode;
/* 2299 */     if (paramArrayOfNode != null && paramNode instanceof ForwardingNode && (arrayOfNode = ((ForwardingNode)paramNode).nextTable) != null) {
/*      */       
/* 2301 */       int j = resizeStamp(paramArrayOfNode.length); int i;
/* 2302 */       while (arrayOfNode == this.nextTable && this.table == paramArrayOfNode && (i = this.sizeCtl) < 0) {
/*      */         
/* 2304 */         if (i >>> RESIZE_STAMP_SHIFT != j || i == j + 1 || i == j + MAX_RESIZERS || this.transferIndex <= 0) {
/*      */           break;
/*      */         }
/* 2307 */         if (U.compareAndSwapInt(this, SIZECTL, i, i + 1)) {
/* 2308 */           transfer(paramArrayOfNode, (Node<K, V>[])arrayOfNode);
/*      */           break;
/*      */         } 
/*      */       } 
/* 2312 */       return (Node<K, V>[])arrayOfNode;
/*      */     } 
/* 2314 */     return this.table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void tryPresize(int paramInt) {
/* 2324 */     int i = (paramInt >= 536870912) ? 1073741824 : tableSizeFor(paramInt + (paramInt >>> 1) + 1);
/*      */     int j;
/* 2326 */     while ((j = this.sizeCtl) >= 0) {
/* 2327 */       Node<K, V>[] arrayOfNode = this.table; int k;
/* 2328 */       if (arrayOfNode == null || (k = arrayOfNode.length) == 0) {
/* 2329 */         k = (j > i) ? j : i;
/* 2330 */         if (U.compareAndSwapInt(this, SIZECTL, j, -1))
/*      */           try {
/* 2332 */             if (this.table == arrayOfNode) {
/*      */               
/* 2334 */               Node[] arrayOfNode1 = new Node[k];
/* 2335 */               this.table = (Node<K, V>[])arrayOfNode1;
/* 2336 */               j = k - (k >>> 2);
/*      */             } 
/*      */           } finally {
/* 2339 */             this.sizeCtl = j;
/*      */           }  
/*      */         continue;
/*      */       } 
/* 2343 */       if (i <= j || k >= 1073741824)
/*      */         break; 
/* 2345 */       if (arrayOfNode == this.table) {
/* 2346 */         int m = resizeStamp(k);
/* 2347 */         if (j < 0) {
/*      */           Node<K, V>[] arrayOfNode1;
/* 2349 */           if (j >>> RESIZE_STAMP_SHIFT != m || j == m + 1 || j == m + MAX_RESIZERS || (arrayOfNode1 = this.nextTable) == null || this.transferIndex <= 0) {
/*      */             break;
/*      */           }
/*      */           
/* 2353 */           if (U.compareAndSwapInt(this, SIZECTL, j, j + 1))
/* 2354 */             transfer(arrayOfNode, arrayOfNode1);  continue;
/*      */         } 
/* 2356 */         if (U.compareAndSwapInt(this, SIZECTL, j, (m << RESIZE_STAMP_SHIFT) + 2))
/*      */         {
/* 2358 */           transfer(arrayOfNode, null);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void transfer(Node<K, V>[] paramArrayOfNode1, Node<K, V>[] paramArrayOfNode2) {
/*      */     Node[] arrayOfNode;
/* 2368 */     int i = paramArrayOfNode1.length; int j;
/* 2369 */     if ((j = (NCPU > 1) ? ((i >>> 3) / NCPU) : i) < 16)
/* 2370 */       j = 16; 
/* 2371 */     if (paramArrayOfNode2 == null) {
/*      */       
/*      */       try {
/* 2374 */         Node[] arrayOfNode1 = new Node[i << 1];
/* 2375 */         arrayOfNode = arrayOfNode1;
/* 2376 */       } catch (Throwable throwable) {
/* 2377 */         this.sizeCtl = Integer.MAX_VALUE;
/*      */         return;
/*      */       } 
/* 2380 */       this.nextTable = (Node<K, V>[])arrayOfNode;
/* 2381 */       this.transferIndex = i;
/*      */     } 
/* 2383 */     int k = arrayOfNode.length;
/* 2384 */     ForwardingNode<Object, Object> forwardingNode = new ForwardingNode<>((Node<?, ?>[])arrayOfNode);
/* 2385 */     boolean bool = true;
/* 2386 */     boolean bool1 = false;
/* 2387 */     int m = 0; byte b = 0;
/*      */     while (true) {
/* 2389 */       while (bool) {
/*      */         
/* 2391 */         if (--m >= b || bool1) {
/* 2392 */           bool = false; continue;
/* 2393 */         }  int i1; if ((i1 = this.transferIndex) <= 0) {
/* 2394 */           m = -1;
/* 2395 */           bool = false; continue;
/*      */         }  boolean bool2;
/* 2397 */         if (U
/* 2398 */           .compareAndSwapInt(this, TRANSFERINDEX, i1, bool2 = (i1 > j) ? (i1 - j) : false)) {
/*      */ 
/*      */           
/* 2401 */           b = bool2;
/* 2402 */           m = i1 - 1;
/* 2403 */           bool = false;
/*      */         } 
/*      */       } 
/* 2406 */       if (m < 0 || m >= i || m + i >= k) {
/*      */         
/* 2408 */         if (bool1) {
/* 2409 */           this.nextTable = null;
/* 2410 */           this.table = (Node<K, V>[])arrayOfNode;
/* 2411 */           this.sizeCtl = (i << 1) - (i >>> 1); return;
/*      */         } 
/*      */         int i1;
/* 2414 */         if (U.compareAndSwapInt(this, SIZECTL, i1 = this.sizeCtl, i1 - 1)) {
/* 2415 */           if (i1 - 2 != resizeStamp(i) << RESIZE_STAMP_SHIFT)
/*      */             return; 
/* 2417 */           bool1 = bool = true;
/* 2418 */           m = i;
/*      */         }  continue;
/*      */       }  Node<K, V> node;
/* 2421 */       if ((node = tabAt(paramArrayOfNode1, m)) == null) {
/* 2422 */         bool = casTabAt(paramArrayOfNode1, m, null, (Node)forwardingNode); continue;
/* 2423 */       }  int n; if ((n = node.hash) == -1) {
/* 2424 */         bool = true; continue;
/*      */       } 
/* 2426 */       synchronized (node) {
/* 2427 */         if (tabAt(paramArrayOfNode1, m) == node)
/*      */         {
/* 2429 */           if (n >= 0) {
/* 2430 */             Node<K, V> node1, node2; int i1 = n & i;
/* 2431 */             Node<K, V> node3 = node; Node<K, V> node4;
/* 2432 */             for (node4 = node.next; node4 != null; node4 = node4.next) {
/* 2433 */               int i2 = node4.hash & i;
/* 2434 */               if (i2 != i1) {
/* 2435 */                 i1 = i2;
/* 2436 */                 node3 = node4;
/*      */               } 
/*      */             } 
/* 2439 */             if (i1 == 0) {
/* 2440 */               node1 = node3;
/* 2441 */               node2 = null;
/*      */             } else {
/*      */               
/* 2444 */               node2 = node3;
/* 2445 */               node1 = null;
/*      */             } 
/* 2447 */             for (node4 = node; node4 != node3; node4 = node4.next) {
/* 2448 */               int i2 = node4.hash; K k1 = node4.key; V v = node4.val;
/* 2449 */               if ((i2 & i) == 0) {
/* 2450 */                 node1 = new Node<>(i2, k1, v, node1);
/*      */               } else {
/* 2452 */                 node2 = new Node<>(i2, k1, v, node2);
/*      */               } 
/* 2454 */             }  setTabAt((Node<K, V>[])arrayOfNode, m, node1);
/* 2455 */             setTabAt((Node<K, V>[])arrayOfNode, m + i, node2);
/* 2456 */             setTabAt(paramArrayOfNode1, m, (Node)forwardingNode);
/* 2457 */             bool = true;
/*      */           }
/* 2459 */           else if (node instanceof TreeBin) {
/* 2460 */             TreeBin treeBin = (TreeBin)node;
/* 2461 */             TreeNode<K, V> treeNode1 = null, treeNode2 = null;
/* 2462 */             TreeNode<K, V> treeNode3 = null, treeNode4 = null;
/* 2463 */             byte b1 = 0, b2 = 0;
/* 2464 */             for (TreeNode treeNode = treeBin.first; treeNode != null; node3 = treeNode.next) {
/* 2465 */               Node node3; int i1 = treeNode.hash;
/* 2466 */               TreeNode<K, V> treeNode5 = new TreeNode<>(i1, treeNode.key, treeNode.val, null, null);
/*      */               
/* 2468 */               if ((i1 & i) == 0) {
/* 2469 */                 if ((treeNode5.prev = treeNode2) == null) {
/* 2470 */                   treeNode1 = treeNode5;
/*      */                 } else {
/* 2472 */                   treeNode2.next = treeNode5;
/* 2473 */                 }  treeNode2 = treeNode5;
/* 2474 */                 b1++;
/*      */               } else {
/*      */                 
/* 2477 */                 if ((treeNode5.prev = treeNode4) == null) {
/* 2478 */                   treeNode3 = treeNode5;
/*      */                 } else {
/* 2480 */                   treeNode4.next = treeNode5;
/* 2481 */                 }  treeNode4 = treeNode5;
/* 2482 */                 b2++;
/*      */               } 
/*      */             } 
/* 2485 */             Node<?, ?> node1 = (b1 <= 6) ? untreeify(treeNode1) : ((b2 != 0) ? new TreeBin<>(treeNode1) : treeBin);
/*      */             
/* 2487 */             Node<?, ?> node2 = (b2 <= 6) ? untreeify(treeNode3) : ((b1 != 0) ? new TreeBin<>(treeNode3) : treeBin);
/*      */             
/* 2489 */             setTabAt((Node<?, ?>[])arrayOfNode, m, node1);
/* 2490 */             setTabAt((Node<?, ?>[])arrayOfNode, m + i, node2);
/* 2491 */             setTabAt(paramArrayOfNode1, m, (Node)forwardingNode);
/* 2492 */             bool = true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Contended
/*      */   static final class CounterCell
/*      */   {
/*      */     volatile long value;
/*      */ 
/*      */     
/*      */     CounterCell(long param1Long) {
/* 2508 */       this.value = param1Long;
/*      */     } }
/*      */   
/*      */   final long sumCount() {
/* 2512 */     CounterCell[] arrayOfCounterCell = this.counterCells;
/* 2513 */     long l = this.baseCount;
/* 2514 */     if (arrayOfCounterCell != null)
/* 2515 */       for (byte b = 0; b < arrayOfCounterCell.length; b++) {
/* 2516 */         CounterCell counterCell; if ((counterCell = arrayOfCounterCell[b]) != null) {
/* 2517 */           l += counterCell.value;
/*      */         }
/*      */       }  
/* 2520 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void fullAddCount(long paramLong, boolean paramBoolean) {
/*      */     int i;
/* 2526 */     if ((i = ThreadLocalRandom.getProbe()) == 0) {
/* 2527 */       ThreadLocalRandom.localInit();
/* 2528 */       i = ThreadLocalRandom.getProbe();
/* 2529 */       paramBoolean = true;
/*      */     } 
/* 2531 */     boolean bool = false; while (true) {
/*      */       CounterCell[] arrayOfCounterCell;
/*      */       int j;
/* 2534 */       if ((arrayOfCounterCell = this.counterCells) != null && (j = arrayOfCounterCell.length) > 0) {
/* 2535 */         CounterCell counterCell; if ((counterCell = arrayOfCounterCell[j - 1 & i]) == null)
/* 2536 */         { if (this.cellsBusy == 0) {
/* 2537 */             CounterCell counterCell1 = new CounterCell(paramLong);
/* 2538 */             if (this.cellsBusy == 0 && U
/* 2539 */               .compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
/* 2540 */               boolean bool1 = false; try {
/*      */                 CounterCell[] arrayOfCounterCell1; int k;
/*      */                 int m;
/* 2543 */                 if ((arrayOfCounterCell1 = this.counterCells) != null && (k = arrayOfCounterCell1.length) > 0 && arrayOfCounterCell1[m = k - 1 & i] == null) {
/*      */ 
/*      */                   
/* 2546 */                   arrayOfCounterCell1[m] = counterCell1;
/* 2547 */                   bool1 = true;
/*      */                 } 
/*      */               } finally {
/* 2550 */                 this.cellsBusy = 0;
/*      */               } 
/* 2552 */               if (bool1)
/*      */                 break; 
/*      */               continue;
/*      */             } 
/*      */           } 
/* 2557 */           bool = false; }
/*      */         
/* 2559 */         else if (!paramBoolean)
/* 2560 */         { paramBoolean = true; }
/* 2561 */         else { long l1; if (U.compareAndSwapLong(counterCell, CELLVALUE, l1 = counterCell.value, l1 + paramLong))
/*      */             break; 
/* 2563 */           if (this.counterCells != arrayOfCounterCell || j >= NCPU) {
/* 2564 */             bool = false;
/* 2565 */           } else if (!bool) {
/* 2566 */             bool = true;
/* 2567 */           } else if (this.cellsBusy == 0 && U
/* 2568 */             .compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
/*      */             try {
/* 2570 */               if (this.counterCells == arrayOfCounterCell) {
/* 2571 */                 CounterCell[] arrayOfCounterCell1 = new CounterCell[j << 1];
/* 2572 */                 for (byte b = 0; b < j; b++)
/* 2573 */                   arrayOfCounterCell1[b] = arrayOfCounterCell[b]; 
/* 2574 */                 this.counterCells = arrayOfCounterCell1;
/*      */               } 
/*      */             } finally {
/* 2577 */               this.cellsBusy = 0;
/*      */             } 
/* 2579 */             bool = false; continue;
/*      */           }  }
/*      */         
/* 2582 */         i = ThreadLocalRandom.advanceProbe(i); continue;
/*      */       } 
/* 2584 */       if (this.cellsBusy == 0 && this.counterCells == arrayOfCounterCell && U
/* 2585 */         .compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
/* 2586 */         boolean bool1 = false;
/*      */         try {
/* 2588 */           if (this.counterCells == arrayOfCounterCell) {
/* 2589 */             CounterCell[] arrayOfCounterCell1 = new CounterCell[2];
/* 2590 */             arrayOfCounterCell1[i & 0x1] = new CounterCell(paramLong);
/* 2591 */             this.counterCells = arrayOfCounterCell1;
/* 2592 */             bool1 = true;
/*      */           } 
/*      */         } finally {
/* 2595 */           this.cellsBusy = 0;
/*      */         } 
/* 2597 */         if (bool1)
/*      */           break;  continue;
/*      */       }  long l;
/* 2600 */       if (U.compareAndSwapLong(this, BASECOUNT, l = this.baseCount, l + paramLong)) {
/*      */         break;
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
/*      */   private final void treeifyBin(Node<K, V>[] paramArrayOfNode, int paramInt) {
/* 2613 */     if (paramArrayOfNode != null) {
/* 2614 */       int i; if ((i = paramArrayOfNode.length) < 64)
/* 2615 */       { tryPresize(i << 1); }
/* 2616 */       else { Node<K, V> node; if ((node = tabAt(paramArrayOfNode, paramInt)) != null && node.hash >= 0) {
/* 2617 */           synchronized (node) {
/* 2618 */             if (tabAt(paramArrayOfNode, paramInt) == node) {
/* 2619 */               TreeNode<K, V> treeNode1 = null, treeNode2 = null;
/* 2620 */               for (Node<K, V> node1 = node; node1 != null; node1 = node1.next) {
/* 2621 */                 TreeNode<K, V> treeNode = new TreeNode<>(node1.hash, node1.key, node1.val, null, null);
/*      */ 
/*      */                 
/* 2624 */                 if ((treeNode.prev = treeNode2) == null) {
/* 2625 */                   treeNode1 = treeNode;
/*      */                 } else {
/* 2627 */                   treeNode2.next = treeNode;
/* 2628 */                 }  treeNode2 = treeNode;
/*      */               } 
/* 2630 */               setTabAt(paramArrayOfNode, paramInt, new TreeBin<>(treeNode1));
/*      */             } 
/*      */           } 
/*      */         } }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> Node<K, V> untreeify(Node<K, V> paramNode) {
/* 2641 */     Node<K, V> node1 = null, node2 = null;
/* 2642 */     for (Node<K, V> node3 = paramNode; node3 != null; node3 = node3.next) {
/* 2643 */       Node<K, V> node = new Node<>(node3.hash, node3.key, node3.val, null);
/* 2644 */       if (node2 == null) {
/* 2645 */         node1 = node;
/*      */       } else {
/* 2647 */         node2.next = node;
/* 2648 */       }  node2 = node;
/*      */     } 
/* 2650 */     return node1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class TreeNode<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     TreeNode<K, V> parent;
/*      */     
/*      */     TreeNode<K, V> left;
/*      */     
/*      */     TreeNode<K, V> right;
/*      */     
/*      */     TreeNode<K, V> prev;
/*      */     boolean red;
/*      */     
/*      */     TreeNode(int param1Int, K param1K, V param1V, ConcurrentHashMap.Node<K, V> param1Node, TreeNode<K, V> param1TreeNode) {
/* 2667 */       super(param1Int, param1K, param1V, param1Node);
/* 2668 */       this.parent = param1TreeNode;
/*      */     }
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> find(int param1Int, Object param1Object) {
/* 2672 */       return findTreeNode(param1Int, param1Object, (Class<?>)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final TreeNode<K, V> findTreeNode(int param1Int, Object param1Object, Class<?> param1Class) {
/* 2680 */       if (param1Object != null) {
/* 2681 */         TreeNode<K, V> treeNode = this;
/*      */         
/*      */         do {
/* 2684 */           TreeNode<K, V> treeNode1 = treeNode.left, treeNode2 = treeNode.right; int i;
/* 2685 */           if ((i = treeNode.hash) > param1Int)
/* 2686 */           { treeNode = treeNode1; }
/* 2687 */           else if (i < param1Int)
/* 2688 */           { treeNode = treeNode2; }
/* 2689 */           else { K k; if ((k = treeNode.key) == param1Object || (k != null && param1Object.equals(k)))
/* 2690 */               return treeNode; 
/* 2691 */             if (treeNode1 == null)
/* 2692 */             { treeNode = treeNode2; }
/* 2693 */             else if (treeNode2 == null)
/* 2694 */             { treeNode = treeNode1; }
/* 2695 */             else { int j; if ((param1Class != null || (
/* 2696 */                 param1Class = ConcurrentHashMap.comparableClassFor(param1Object)) != null) && (
/* 2697 */                 j = ConcurrentHashMap.compareComparables(param1Class, param1Object, k)) != 0)
/* 2698 */               { treeNode = (j < 0) ? treeNode1 : treeNode2; }
/* 2699 */               else { TreeNode<K, V> treeNode3; if ((treeNode3 = treeNode2.findTreeNode(param1Int, param1Object, param1Class)) != null) {
/* 2700 */                   return treeNode3;
/*      */                 }
/* 2702 */                 treeNode = treeNode1; }  }  } 
/* 2703 */         } while (treeNode != null);
/*      */       } 
/* 2705 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class TreeBin<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     ConcurrentHashMap.TreeNode<K, V> root;
/*      */ 
/*      */     
/*      */     volatile ConcurrentHashMap.TreeNode<K, V> first;
/*      */ 
/*      */     
/*      */     volatile Thread waiter;
/*      */     
/*      */     volatile int lockState;
/*      */     
/*      */     static final int WRITER = 1;
/*      */     
/*      */     static final int WAITER = 2;
/*      */     
/*      */     static final int READER = 4;
/*      */     
/*      */     private static final Unsafe U;
/*      */     
/*      */     private static final long LOCKSTATE;
/*      */ 
/*      */     
/*      */     static int tieBreakOrder(Object param1Object1, Object param1Object2) {
/*      */       int i;
/* 2737 */       if (param1Object1 == null || param1Object2 == null || (
/*      */         
/* 2739 */         i = param1Object1.getClass().getName().compareTo(param1Object2.getClass().getName())) == 0) {
/* 2740 */         i = (System.identityHashCode(param1Object1) <= System.identityHashCode(param1Object2)) ? -1 : 1;
/*      */       }
/* 2742 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TreeBin(ConcurrentHashMap.TreeNode<K, V> param1TreeNode) {
/* 2749 */       super(-2, null, null, null);
/* 2750 */       this.first = param1TreeNode;
/* 2751 */       ConcurrentHashMap.TreeNode<K, V> treeNode1 = null;
/* 2752 */       for (ConcurrentHashMap.TreeNode<K, V> treeNode2 = param1TreeNode; treeNode2 != null; treeNode2 = treeNode) {
/* 2753 */         ConcurrentHashMap.TreeNode<K, V> treeNode = (ConcurrentHashMap.TreeNode)treeNode2.next;
/* 2754 */         treeNode2.left = treeNode2.right = null;
/* 2755 */         if (treeNode1 == null) {
/* 2756 */           treeNode2.parent = null;
/* 2757 */           treeNode2.red = false;
/* 2758 */           treeNode1 = treeNode2;
/*      */         } else {
/*      */           
/* 2761 */           K k = treeNode2.key;
/* 2762 */           int i = treeNode2.hash;
/* 2763 */           Class<?> clazz = null;
/* 2764 */           ConcurrentHashMap.TreeNode<K, V> treeNode3 = treeNode1; while (true) {
/*      */             int j;
/* 2766 */             K k1 = treeNode3.key; int m;
/* 2767 */             if ((m = treeNode3.hash) > i) {
/* 2768 */               j = -1;
/* 2769 */             } else if (m < i) {
/* 2770 */               j = 1;
/* 2771 */             } else if ((clazz == null && (
/* 2772 */               clazz = ConcurrentHashMap.comparableClassFor(k)) == null) || (
/* 2773 */               j = ConcurrentHashMap.compareComparables(clazz, k, k1)) == 0) {
/* 2774 */               j = tieBreakOrder(k, k1);
/* 2775 */             }  ConcurrentHashMap.TreeNode<K, V> treeNode4 = treeNode3;
/* 2776 */             if ((treeNode3 = (ConcurrentHashMap.TreeNode<K, V>)((j <= 0) ? treeNode3.left : treeNode3.right)) == null) {
/* 2777 */               treeNode2.parent = treeNode4;
/* 2778 */               if (j <= 0) {
/* 2779 */                 treeNode4.left = treeNode2;
/*      */               } else {
/* 2781 */                 treeNode4.right = treeNode2;
/* 2782 */               }  treeNode1 = balanceInsertion(treeNode1, treeNode2);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2788 */       this.root = treeNode1;
/* 2789 */       assert checkInvariants(this.root);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final void lockRoot() {
/* 2796 */       if (!U.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
/* 2797 */         contendedLock();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private final void unlockRoot() {
/* 2804 */       this.lockState = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final void contendedLock() {
/* 2811 */       boolean bool = false; while (true) {
/*      */         int i;
/* 2813 */         while (((i = this.lockState) & 0xFFFFFFFD) == 0) {
/* 2814 */           if (U.compareAndSwapInt(this, LOCKSTATE, i, 1)) {
/* 2815 */             if (bool)
/* 2816 */               this.waiter = null; 
/*      */             return;
/*      */           } 
/*      */         } 
/* 2820 */         if ((i & 0x2) == 0) {
/* 2821 */           if (U.compareAndSwapInt(this, LOCKSTATE, i, i | 0x2)) {
/* 2822 */             bool = true;
/* 2823 */             this.waiter = Thread.currentThread();
/*      */           }  continue;
/*      */         } 
/* 2826 */         if (bool) {
/* 2827 */           LockSupport.park(this);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMap.Node<K, V> find(int param1Int, Object param1Object) {
/* 2837 */       if (param1Object != null) {
/* 2838 */         for (ConcurrentHashMap.TreeNode<K, V> treeNode = this.first; treeNode != null; ) {
/*      */           int i;
/* 2840 */           if (((i = this.lockState) & 0x3) != 0) {
/* 2841 */             K k; if (treeNode.hash == param1Int && ((k = treeNode.key) == param1Object || (k != null && param1Object
/* 2842 */               .equals(k))))
/* 2843 */               return treeNode; 
/* 2844 */             ConcurrentHashMap.Node<K, V> node = treeNode.next; continue;
/*      */           } 
/* 2846 */           if (U.compareAndSwapInt(this, LOCKSTATE, i, i + 4)) {
/*      */             ConcurrentHashMap.TreeNode<K, V> treeNode1;
/*      */             
/*      */             try {
/*      */               ConcurrentHashMap.TreeNode<K, V> treeNode2;
/* 2851 */               treeNode1 = ((treeNode2 = this.root) == null) ? null : treeNode2.findTreeNode(param1Int, param1Object, (Class<?>)null);
/*      */             } finally {
/*      */               Thread thread;
/* 2854 */               if (U.getAndAddInt(this, LOCKSTATE, -4) == 6 && (thread = this.waiter) != null)
/*      */               {
/* 2856 */                 LockSupport.unpark(thread); } 
/*      */             } 
/* 2858 */             return treeNode1;
/*      */           } 
/*      */         } 
/*      */       }
/* 2862 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMap.TreeNode<K, V> putTreeVal(int param1Int, K param1K, V param1V) {
/* 2870 */       Class<?> clazz = null;
/* 2871 */       boolean bool = false;
/* 2872 */       ConcurrentHashMap.TreeNode<K, V> treeNode = this.root; while (true) {
/*      */         int i;
/* 2874 */         if (treeNode == null) {
/* 2875 */           this.first = this.root = new ConcurrentHashMap.TreeNode<>(param1Int, param1K, param1V, null, null); break;
/*      */         } 
/*      */         int j;
/* 2878 */         if ((j = treeNode.hash) > param1Int)
/* 2879 */         { i = -1; }
/* 2880 */         else if (j < param1Int)
/* 2881 */         { i = 1; }
/* 2882 */         else { K k; if ((k = treeNode.key) == param1K || (k != null && param1K.equals(k)))
/* 2883 */             return treeNode; 
/* 2884 */           if ((clazz == null && (
/* 2885 */             clazz = ConcurrentHashMap.comparableClassFor(param1K)) == null) || (
/* 2886 */             i = ConcurrentHashMap.compareComparables(clazz, param1K, k)) == 0) {
/* 2887 */             if (!bool) {
/*      */               
/* 2889 */               bool = true; ConcurrentHashMap.TreeNode<K, V> treeNode2, treeNode3;
/* 2890 */               if (((treeNode3 = treeNode.left) != null && (
/* 2891 */                 treeNode2 = treeNode3.findTreeNode(param1Int, param1K, clazz)) != null) || ((treeNode3 = treeNode.right) != null && (
/*      */                 
/* 2893 */                 treeNode2 = treeNode3.findTreeNode(param1Int, param1K, clazz)) != null))
/* 2894 */                 return treeNode2; 
/*      */             } 
/* 2896 */             i = tieBreakOrder(param1K, k);
/*      */           }  }
/*      */         
/* 2899 */         ConcurrentHashMap.TreeNode<K, V> treeNode1 = treeNode;
/* 2900 */         if ((treeNode = (ConcurrentHashMap.TreeNode<K, V>)((i <= 0) ? treeNode.left : treeNode.right)) == null) {
/* 2901 */           ConcurrentHashMap.TreeNode<K, V> treeNode3 = this.first;
/* 2902 */           ConcurrentHashMap.TreeNode<K, V> treeNode2 = new ConcurrentHashMap.TreeNode<>(param1Int, param1K, param1V, treeNode3, treeNode1);
/* 2903 */           if (treeNode3 != null)
/* 2904 */             treeNode3.prev = treeNode2; 
/* 2905 */           if (i <= 0) {
/* 2906 */             treeNode1.left = treeNode2;
/*      */           } else {
/* 2908 */             treeNode1.right = treeNode2;
/* 2909 */           }  if (!treeNode1.red) {
/* 2910 */             treeNode2.red = true; break;
/*      */           } 
/* 2912 */           lockRoot();
/*      */           try {
/* 2914 */             this.root = balanceInsertion(this.root, treeNode2);
/*      */           } finally {
/* 2916 */             unlockRoot();
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2922 */       assert checkInvariants(this.root);
/* 2923 */       return null;
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
/*      */     final boolean removeTreeNode(ConcurrentHashMap.TreeNode<K, V> param1TreeNode) {
/* 2937 */       ConcurrentHashMap.TreeNode<K, V> treeNode1 = (ConcurrentHashMap.TreeNode)param1TreeNode.next;
/* 2938 */       ConcurrentHashMap.TreeNode<K, V> treeNode2 = param1TreeNode.prev;
/*      */       
/* 2940 */       if (treeNode2 == null) {
/* 2941 */         this.first = treeNode1;
/*      */       } else {
/* 2943 */         treeNode2.next = treeNode1;
/* 2944 */       }  if (treeNode1 != null)
/* 2945 */         treeNode1.prev = treeNode2; 
/* 2946 */       if (this.first == null) {
/* 2947 */         this.root = null;
/* 2948 */         return true;
/*      */       }  ConcurrentHashMap.TreeNode<K, V> treeNode3, treeNode4;
/* 2950 */       if ((treeNode3 = this.root) == null || treeNode3.right == null || (treeNode4 = treeNode3.left) == null || treeNode4.left == null)
/*      */       {
/* 2952 */         return true; } 
/* 2953 */       lockRoot();
/*      */       
/*      */       try {
/* 2956 */         ConcurrentHashMap.TreeNode<K, V> treeNode5, treeNode6 = param1TreeNode.left;
/* 2957 */         ConcurrentHashMap.TreeNode<K, V> treeNode7 = param1TreeNode.right;
/* 2958 */         if (treeNode6 != null && treeNode7 != null) {
/* 2959 */           ConcurrentHashMap.TreeNode<K, V> treeNode8 = treeNode7; ConcurrentHashMap.TreeNode<K, V> treeNode9;
/* 2960 */           while ((treeNode9 = treeNode8.left) != null)
/* 2961 */             treeNode8 = treeNode9; 
/* 2962 */           boolean bool = treeNode8.red; treeNode8.red = param1TreeNode.red; param1TreeNode.red = bool;
/* 2963 */           ConcurrentHashMap.TreeNode<K, V> treeNode10 = treeNode8.right;
/* 2964 */           ConcurrentHashMap.TreeNode<K, V> treeNode11 = param1TreeNode.parent;
/* 2965 */           if (treeNode8 == treeNode7) {
/* 2966 */             param1TreeNode.parent = treeNode8;
/* 2967 */             treeNode8.right = param1TreeNode;
/*      */           } else {
/*      */             
/* 2970 */             ConcurrentHashMap.TreeNode<K, V> treeNode = treeNode8.parent;
/* 2971 */             if ((param1TreeNode.parent = treeNode) != null)
/* 2972 */               if (treeNode8 == treeNode.left) {
/* 2973 */                 treeNode.left = param1TreeNode;
/*      */               } else {
/* 2975 */                 treeNode.right = param1TreeNode;
/*      */               }  
/* 2977 */             if ((treeNode8.right = treeNode7) != null)
/* 2978 */               treeNode7.parent = treeNode8; 
/*      */           } 
/* 2980 */           param1TreeNode.left = null;
/* 2981 */           if ((param1TreeNode.right = treeNode10) != null)
/* 2982 */             treeNode10.parent = param1TreeNode; 
/* 2983 */           if ((treeNode8.left = treeNode6) != null)
/* 2984 */             treeNode6.parent = treeNode8; 
/* 2985 */           if ((treeNode8.parent = treeNode11) == null) {
/* 2986 */             treeNode3 = treeNode8;
/* 2987 */           } else if (param1TreeNode == treeNode11.left) {
/* 2988 */             treeNode11.left = treeNode8;
/*      */           } else {
/* 2990 */             treeNode11.right = treeNode8;
/* 2991 */           }  if (treeNode10 != null) {
/* 2992 */             treeNode5 = treeNode10;
/*      */           } else {
/* 2994 */             treeNode5 = param1TreeNode;
/*      */           } 
/* 2996 */         } else if (treeNode6 != null) {
/* 2997 */           treeNode5 = treeNode6;
/* 2998 */         } else if (treeNode7 != null) {
/* 2999 */           treeNode5 = treeNode7;
/*      */         } else {
/* 3001 */           treeNode5 = param1TreeNode;
/* 3002 */         }  if (treeNode5 != param1TreeNode) {
/* 3003 */           ConcurrentHashMap.TreeNode<K, V> treeNode = treeNode5.parent = param1TreeNode.parent;
/* 3004 */           if (treeNode == null) {
/* 3005 */             treeNode3 = treeNode5;
/* 3006 */           } else if (param1TreeNode == treeNode.left) {
/* 3007 */             treeNode.left = treeNode5;
/*      */           } else {
/* 3009 */             treeNode.right = treeNode5;
/* 3010 */           }  param1TreeNode.left = param1TreeNode.right = param1TreeNode.parent = null;
/*      */         } 
/*      */         
/* 3013 */         this.root = param1TreeNode.red ? treeNode3 : balanceDeletion(treeNode3, treeNode5);
/*      */         
/* 3015 */         if (param1TreeNode == treeNode5) {
/*      */           ConcurrentHashMap.TreeNode<K, V> treeNode;
/* 3017 */           if ((treeNode = param1TreeNode.parent) != null) {
/* 3018 */             if (param1TreeNode == treeNode.left) {
/* 3019 */               treeNode.left = null;
/* 3020 */             } else if (param1TreeNode == treeNode.right) {
/* 3021 */               treeNode.right = null;
/* 3022 */             }  param1TreeNode.parent = null;
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 3026 */         unlockRoot();
/*      */       } 
/* 3028 */       assert checkInvariants(this.root);
/* 3029 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> rotateLeft(ConcurrentHashMap.TreeNode<K, V> param1TreeNode1, ConcurrentHashMap.TreeNode<K, V> param1TreeNode2) {
/*      */       ConcurrentHashMap.TreeNode<K, V> treeNode;
/* 3038 */       if (param1TreeNode2 != null && (treeNode = param1TreeNode2.right) != null) {
/* 3039 */         ConcurrentHashMap.TreeNode<K, V> treeNode2; if ((treeNode2 = param1TreeNode2.right = treeNode.left) != null)
/* 3040 */           treeNode2.parent = param1TreeNode2;  ConcurrentHashMap.TreeNode<K, V> treeNode1;
/* 3041 */         if ((treeNode1 = treeNode.parent = param1TreeNode2.parent) == null) {
/* 3042 */           (param1TreeNode1 = treeNode).red = false;
/* 3043 */         } else if (treeNode1.left == param1TreeNode2) {
/* 3044 */           treeNode1.left = treeNode;
/*      */         } else {
/* 3046 */           treeNode1.right = treeNode;
/* 3047 */         }  treeNode.left = param1TreeNode2;
/* 3048 */         param1TreeNode2.parent = treeNode;
/*      */       } 
/* 3050 */       return param1TreeNode1;
/*      */     }
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> rotateRight(ConcurrentHashMap.TreeNode<K, V> param1TreeNode1, ConcurrentHashMap.TreeNode<K, V> param1TreeNode2) {
/*      */       ConcurrentHashMap.TreeNode<K, V> treeNode;
/* 3056 */       if (param1TreeNode2 != null && (treeNode = param1TreeNode2.left) != null) {
/* 3057 */         ConcurrentHashMap.TreeNode<K, V> treeNode2; if ((treeNode2 = param1TreeNode2.left = treeNode.right) != null)
/* 3058 */           treeNode2.parent = param1TreeNode2;  ConcurrentHashMap.TreeNode<K, V> treeNode1;
/* 3059 */         if ((treeNode1 = treeNode.parent = param1TreeNode2.parent) == null) {
/* 3060 */           (param1TreeNode1 = treeNode).red = false;
/* 3061 */         } else if (treeNode1.right == param1TreeNode2) {
/* 3062 */           treeNode1.right = treeNode;
/*      */         } else {
/* 3064 */           treeNode1.left = treeNode;
/* 3065 */         }  treeNode.right = param1TreeNode2;
/* 3066 */         param1TreeNode2.parent = treeNode;
/*      */       } 
/* 3068 */       return param1TreeNode1;
/*      */     }
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> balanceInsertion(ConcurrentHashMap.TreeNode<K, V> param1TreeNode1, ConcurrentHashMap.TreeNode<K, V> param1TreeNode2) {
/* 3073 */       param1TreeNode2.red = true; while (true) {
/*      */         ConcurrentHashMap.TreeNode<K, V> treeNode1;
/* 3075 */         if ((treeNode1 = param1TreeNode2.parent) == null) {
/* 3076 */           param1TreeNode2.red = false;
/* 3077 */           return param1TreeNode2;
/*      */         }  ConcurrentHashMap.TreeNode<K, V> treeNode2;
/* 3079 */         if (!treeNode1.red || (treeNode2 = treeNode1.parent) == null)
/* 3080 */           return param1TreeNode1;  ConcurrentHashMap.TreeNode<K, V> treeNode3;
/* 3081 */         if (treeNode1 == (treeNode3 = treeNode2.left)) {
/* 3082 */           ConcurrentHashMap.TreeNode<K, V> treeNode; if ((treeNode = treeNode2.right) != null && treeNode.red) {
/* 3083 */             treeNode.red = false;
/* 3084 */             treeNode1.red = false;
/* 3085 */             treeNode2.red = true;
/* 3086 */             param1TreeNode2 = treeNode2;
/*      */             continue;
/*      */           } 
/* 3089 */           if (param1TreeNode2 == treeNode1.right) {
/* 3090 */             param1TreeNode1 = rotateLeft(param1TreeNode1, param1TreeNode2 = treeNode1);
/* 3091 */             treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.parent;
/*      */           } 
/* 3093 */           if (treeNode1 != null) {
/* 3094 */             treeNode1.red = false;
/* 3095 */             if (treeNode2 != null) {
/* 3096 */               treeNode2.red = true;
/* 3097 */               param1TreeNode1 = rotateRight(param1TreeNode1, treeNode2);
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/* 3103 */         if (treeNode3 != null && treeNode3.red) {
/* 3104 */           treeNode3.red = false;
/* 3105 */           treeNode1.red = false;
/* 3106 */           treeNode2.red = true;
/* 3107 */           param1TreeNode2 = treeNode2;
/*      */           continue;
/*      */         } 
/* 3110 */         if (param1TreeNode2 == treeNode1.left) {
/* 3111 */           param1TreeNode1 = rotateRight(param1TreeNode1, param1TreeNode2 = treeNode1);
/* 3112 */           treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.parent;
/*      */         } 
/* 3114 */         if (treeNode1 != null) {
/* 3115 */           treeNode1.red = false;
/* 3116 */           if (treeNode2 != null) {
/* 3117 */             treeNode2.red = true;
/* 3118 */             param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> balanceDeletion(ConcurrentHashMap.TreeNode<K, V> param1TreeNode1, ConcurrentHashMap.TreeNode<K, V> param1TreeNode2) {
/*      */       while (true) {
/* 3129 */         if (param1TreeNode2 == null || param1TreeNode2 == param1TreeNode1)
/* 3130 */           return param1TreeNode1;  ConcurrentHashMap.TreeNode<K, V> treeNode1;
/* 3131 */         if ((treeNode1 = param1TreeNode2.parent) == null) {
/* 3132 */           param1TreeNode2.red = false;
/* 3133 */           return param1TreeNode2;
/*      */         } 
/* 3135 */         if (param1TreeNode2.red) {
/* 3136 */           param1TreeNode2.red = false;
/* 3137 */           return param1TreeNode1;
/*      */         }  ConcurrentHashMap.TreeNode<K, V> treeNode2;
/* 3139 */         if ((treeNode2 = treeNode1.left) == param1TreeNode2) {
/* 3140 */           ConcurrentHashMap.TreeNode<K, V> treeNode5; if ((treeNode5 = treeNode1.right) != null && treeNode5.red) {
/* 3141 */             treeNode5.red = false;
/* 3142 */             treeNode1.red = true;
/* 3143 */             param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode1);
/* 3144 */             treeNode5 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.right;
/*      */           } 
/* 3146 */           if (treeNode5 == null) {
/* 3147 */             param1TreeNode2 = treeNode1; continue;
/*      */           } 
/* 3149 */           ConcurrentHashMap.TreeNode<K, V> treeNode6 = treeNode5.left, treeNode7 = treeNode5.right;
/* 3150 */           if ((treeNode7 == null || !treeNode7.red) && (treeNode6 == null || !treeNode6.red)) {
/*      */             
/* 3152 */             treeNode5.red = true;
/* 3153 */             param1TreeNode2 = treeNode1;
/*      */             continue;
/*      */           } 
/* 3156 */           if (treeNode7 == null || !treeNode7.red) {
/* 3157 */             if (treeNode6 != null)
/* 3158 */               treeNode6.red = false; 
/* 3159 */             treeNode5.red = true;
/* 3160 */             param1TreeNode1 = rotateRight(param1TreeNode1, treeNode5);
/* 3161 */             treeNode5 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.right;
/*      */           } 
/*      */           
/* 3164 */           if (treeNode5 != null) {
/* 3165 */             treeNode5.red = (treeNode1 == null) ? false : treeNode1.red;
/* 3166 */             if ((treeNode7 = treeNode5.right) != null)
/* 3167 */               treeNode7.red = false; 
/*      */           } 
/* 3169 */           if (treeNode1 != null) {
/* 3170 */             treeNode1.red = false;
/* 3171 */             param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode1);
/*      */           } 
/* 3173 */           param1TreeNode2 = param1TreeNode1;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 3178 */         if (treeNode2 != null && treeNode2.red) {
/* 3179 */           treeNode2.red = false;
/* 3180 */           treeNode1.red = true;
/* 3181 */           param1TreeNode1 = rotateRight(param1TreeNode1, treeNode1);
/* 3182 */           treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.left;
/*      */         } 
/* 3184 */         if (treeNode2 == null) {
/* 3185 */           param1TreeNode2 = treeNode1; continue;
/*      */         } 
/* 3187 */         ConcurrentHashMap.TreeNode<K, V> treeNode3 = treeNode2.left, treeNode4 = treeNode2.right;
/* 3188 */         if ((treeNode3 == null || !treeNode3.red) && (treeNode4 == null || !treeNode4.red)) {
/*      */           
/* 3190 */           treeNode2.red = true;
/* 3191 */           param1TreeNode2 = treeNode1;
/*      */           continue;
/*      */         } 
/* 3194 */         if (treeNode3 == null || !treeNode3.red) {
/* 3195 */           if (treeNode4 != null)
/* 3196 */             treeNode4.red = false; 
/* 3197 */           treeNode2.red = true;
/* 3198 */           param1TreeNode1 = rotateLeft(param1TreeNode1, treeNode2);
/* 3199 */           treeNode2 = ((treeNode1 = param1TreeNode2.parent) == null) ? null : treeNode1.left;
/*      */         } 
/*      */         
/* 3202 */         if (treeNode2 != null) {
/* 3203 */           treeNode2.red = (treeNode1 == null) ? false : treeNode1.red;
/* 3204 */           if ((treeNode3 = treeNode2.left) != null)
/* 3205 */             treeNode3.red = false; 
/*      */         } 
/* 3207 */         if (treeNode1 != null) {
/* 3208 */           treeNode1.red = false;
/* 3209 */           param1TreeNode1 = rotateRight(param1TreeNode1, treeNode1);
/*      */         } 
/* 3211 */         param1TreeNode2 = param1TreeNode1;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> boolean checkInvariants(ConcurrentHashMap.TreeNode<K, V> param1TreeNode) {
/* 3222 */       ConcurrentHashMap.TreeNode<K, V> treeNode1 = param1TreeNode.parent, treeNode2 = param1TreeNode.left, treeNode3 = param1TreeNode.right;
/* 3223 */       ConcurrentHashMap.TreeNode<K, V> treeNode4 = param1TreeNode.prev; ConcurrentHashMap.TreeNode treeNode = (ConcurrentHashMap.TreeNode)param1TreeNode.next;
/* 3224 */       if (treeNode4 != null && treeNode4.next != param1TreeNode)
/* 3225 */         return false; 
/* 3226 */       if (treeNode != null && treeNode.prev != param1TreeNode)
/* 3227 */         return false; 
/* 3228 */       if (treeNode1 != null && param1TreeNode != treeNode1.left && param1TreeNode != treeNode1.right)
/* 3229 */         return false; 
/* 3230 */       if (treeNode2 != null && (treeNode2.parent != param1TreeNode || treeNode2.hash > param1TreeNode.hash))
/* 3231 */         return false; 
/* 3232 */       if (treeNode3 != null && (treeNode3.parent != param1TreeNode || treeNode3.hash < param1TreeNode.hash))
/* 3233 */         return false; 
/* 3234 */       if (param1TreeNode.red && treeNode2 != null && treeNode2.red && treeNode3 != null && treeNode3.red)
/* 3235 */         return false; 
/* 3236 */       if (treeNode2 != null && !checkInvariants(treeNode2))
/* 3237 */         return false; 
/* 3238 */       if (treeNode3 != null && !checkInvariants(treeNode3))
/* 3239 */         return false; 
/* 3240 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/* 3247 */         U = Unsafe.getUnsafe();
/* 3248 */         Class<TreeBin> clazz = TreeBin.class;
/*      */         
/* 3250 */         LOCKSTATE = U.objectFieldOffset(clazz.getDeclaredField("lockState"));
/* 3251 */       } catch (Exception exception) {
/* 3252 */         throw new Error(exception);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class TableStack<K, V>
/*      */   {
/*      */     int length;
/*      */ 
/*      */     
/*      */     int index;
/*      */ 
/*      */     
/*      */     ConcurrentHashMap.Node<K, V>[] tab;
/*      */ 
/*      */     
/*      */     TableStack<K, V> next;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Traverser<K, V>
/*      */   {
/*      */     ConcurrentHashMap.Node<K, V>[] tab;
/*      */ 
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> next;
/*      */ 
/*      */     
/*      */     ConcurrentHashMap.TableStack<K, V> stack;
/*      */ 
/*      */     
/*      */     ConcurrentHashMap.TableStack<K, V> spare;
/*      */ 
/*      */     
/*      */     int index;
/*      */ 
/*      */     
/*      */     int baseIndex;
/*      */ 
/*      */     
/*      */     int baseLimit;
/*      */ 
/*      */     
/*      */     final int baseSize;
/*      */ 
/*      */     
/*      */     Traverser(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3) {
/* 3302 */       this.tab = param1ArrayOfNode;
/* 3303 */       this.baseSize = param1Int1;
/* 3304 */       this.baseIndex = this.index = param1Int2;
/* 3305 */       this.baseLimit = param1Int3;
/* 3306 */       this.next = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMap.Node<K, V> advance() {
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3314 */       if ((node = this.next) != null) {
/* 3315 */         node = node.next;
/*      */       }
/*      */       while (true) {
/* 3318 */         if (node != null)
/* 3319 */           return this.next = node;  ConcurrentHashMap.Node<K, V>[] arrayOfNode; int i; int j;
/* 3320 */         if (this.baseIndex >= this.baseLimit || (arrayOfNode = this.tab) == null || (j = arrayOfNode.length) <= (i = this.index) || i < 0)
/*      */         {
/* 3322 */           return this.next = null; } 
/* 3323 */         if ((node = ConcurrentHashMap.<K, V>tabAt(arrayOfNode, i)) != null && node.hash < 0) {
/* 3324 */           if (node instanceof ConcurrentHashMap.ForwardingNode) {
/* 3325 */             this.tab = (ConcurrentHashMap.Node<K, V>[])((ConcurrentHashMap.ForwardingNode)node).nextTable;
/* 3326 */             node = null;
/* 3327 */             pushState(arrayOfNode, i, j);
/*      */             continue;
/*      */           } 
/* 3330 */           if (node instanceof ConcurrentHashMap.TreeBin) {
/* 3331 */             node = ((ConcurrentHashMap.TreeBin)node).first;
/*      */           } else {
/* 3333 */             node = null;
/*      */           } 
/* 3335 */         }  if (this.stack != null) {
/* 3336 */           recoverState(j); continue;
/* 3337 */         }  if ((this.index = i + this.baseSize) >= j) {
/* 3338 */           this.index = ++this.baseIndex;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void pushState(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2) {
/* 3346 */       ConcurrentHashMap.TableStack<K, V> tableStack = this.spare;
/* 3347 */       if (tableStack != null) {
/* 3348 */         this.spare = tableStack.next;
/*      */       } else {
/* 3350 */         tableStack = new ConcurrentHashMap.TableStack<>();
/* 3351 */       }  tableStack.tab = param1ArrayOfNode;
/* 3352 */       tableStack.length = param1Int2;
/* 3353 */       tableStack.index = param1Int1;
/* 3354 */       tableStack.next = this.stack;
/* 3355 */       this.stack = tableStack;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void recoverState(int param1Int) {
/*      */       ConcurrentHashMap.TableStack<K, V> tableStack;
/*      */       int i;
/* 3365 */       while ((tableStack = this.stack) != null && (this.index += i = tableStack.length) >= param1Int) {
/* 3366 */         param1Int = i;
/* 3367 */         this.index = tableStack.index;
/* 3368 */         this.tab = tableStack.tab;
/* 3369 */         tableStack.tab = null;
/* 3370 */         ConcurrentHashMap.TableStack<K, V> tableStack1 = tableStack.next;
/* 3371 */         tableStack.next = this.spare;
/* 3372 */         this.stack = tableStack1;
/* 3373 */         this.spare = tableStack;
/*      */       } 
/* 3375 */       if (tableStack == null && (this.index += this.baseSize) >= param1Int) {
/* 3376 */         this.index = ++this.baseIndex;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class BaseIterator<K, V>
/*      */     extends Traverser<K, V>
/*      */   {
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     ConcurrentHashMap.Node<K, V> lastReturned;
/*      */     
/*      */     BaseIterator(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 3389 */       super(param1ArrayOfNode, param1Int1, param1Int2, param1Int3);
/* 3390 */       this.map = param1ConcurrentHashMap;
/* 3391 */       advance();
/*      */     }
/*      */     
/* 3394 */     public final boolean hasNext() { return (this.next != null); } public final boolean hasMoreElements() {
/* 3395 */       return (this.next != null);
/*      */     }
/*      */     public final void remove() {
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3399 */       if ((node = this.lastReturned) == null)
/* 3400 */         throw new IllegalStateException(); 
/* 3401 */       this.lastReturned = null;
/* 3402 */       this.map.replaceNode(node.key, null, null);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeyIterator<K, V>
/*      */     extends BaseIterator<K, V>
/*      */     implements Iterator<K>, Enumeration<K> {
/*      */     KeyIterator(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 3410 */       super(param1ArrayOfNode, param1Int1, param1Int2, param1Int3, param1ConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public final K next() {
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3415 */       if ((node = this.next) == null)
/* 3416 */         throw new NoSuchElementException(); 
/* 3417 */       K k = node.key;
/* 3418 */       this.lastReturned = node;
/* 3419 */       advance();
/* 3420 */       return k;
/*      */     }
/*      */     public final K nextElement() {
/* 3423 */       return next();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueIterator<K, V>
/*      */     extends BaseIterator<K, V> implements Iterator<V>, Enumeration<V> {
/*      */     ValueIterator(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 3430 */       super(param1ArrayOfNode, param1Int1, param1Int2, param1Int3, param1ConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public final V next() {
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3435 */       if ((node = this.next) == null)
/* 3436 */         throw new NoSuchElementException(); 
/* 3437 */       V v = node.val;
/* 3438 */       this.lastReturned = node;
/* 3439 */       advance();
/* 3440 */       return v;
/*      */     }
/*      */     public final V nextElement() {
/* 3443 */       return next();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntryIterator<K, V>
/*      */     extends BaseIterator<K, V> implements Iterator<Map.Entry<K, V>> {
/*      */     EntryIterator(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 3450 */       super(param1ArrayOfNode, param1Int1, param1Int2, param1Int3, param1ConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> next() {
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3455 */       if ((node = this.next) == null)
/* 3456 */         throw new NoSuchElementException(); 
/* 3457 */       K k = node.key;
/* 3458 */       V v = node.val;
/* 3459 */       this.lastReturned = node;
/* 3460 */       advance();
/* 3461 */       return new ConcurrentHashMap.MapEntry<>(k, v, this.map);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class MapEntry<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final K key;
/*      */     V val;
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     
/*      */     MapEntry(K param1K, V param1V, ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 3473 */       this.key = param1K;
/* 3474 */       this.val = param1V;
/* 3475 */       this.map = param1ConcurrentHashMap;
/*      */     }
/* 3477 */     public K getKey() { return this.key; }
/* 3478 */     public V getValue() { return this.val; }
/* 3479 */     public int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); }
/* 3480 */     public String toString() { return (new StringBuilder()).append(this.key).append("=").append(this.val).toString(); } public boolean equals(Object param1Object) {
/*      */       K k;
/*      */       Object object;
/*      */       Map.Entry<Object, ?> entry;
/* 3484 */       return (param1Object instanceof Map.Entry && (
/* 3485 */         k = (K)(entry = (Map.Entry)param1Object).getKey()) != null && (
/* 3486 */         object = entry.getValue()) != null && (k == this.key || k
/* 3487 */         .equals(this.key)) && (object == this.val || object
/* 3488 */         .equals(this.val)));
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
/*      */     public V setValue(V param1V) {
/* 3500 */       if (param1V == null) throw new NullPointerException(); 
/* 3501 */       V v = this.val;
/* 3502 */       this.val = param1V;
/* 3503 */       this.map.put(this.key, param1V);
/* 3504 */       return v;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends Traverser<K, V> implements Spliterator<K> {
/*      */     long est;
/*      */     
/*      */     KeySpliterator(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3, long param1Long) {
/* 3513 */       super(param1ArrayOfNode, param1Int1, param1Int2, param1Int3);
/* 3514 */       this.est = param1Long;
/*      */     } public Spliterator<K> trySplit() {
/*      */       int i;
/*      */       int j;
/*      */       int k;
/* 3519 */       return ((k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i) ? null : new KeySpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1L);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> param1Consumer) {
/* 3525 */       if (param1Consumer == null) throw new NullPointerException();  ConcurrentHashMap.Node<K, V> node;
/* 3526 */       while ((node = advance()) != null)
/* 3527 */         param1Consumer.accept(node.key); 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> param1Consumer) {
/* 3531 */       if (param1Consumer == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3533 */       if ((node = advance()) == null)
/* 3534 */         return false; 
/* 3535 */       param1Consumer.accept(node.key);
/* 3536 */       return true;
/*      */     }
/*      */     public long estimateSize() {
/* 3539 */       return this.est;
/*      */     }
/*      */     public int characteristics() {
/* 3542 */       return 4353;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends Traverser<K, V>
/*      */     implements Spliterator<V> {
/*      */     long est;
/*      */     
/*      */     ValueSpliterator(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3, long param1Long) {
/* 3552 */       super(param1ArrayOfNode, param1Int1, param1Int2, param1Int3);
/* 3553 */       this.est = param1Long;
/*      */     } public Spliterator<V> trySplit() {
/*      */       int i;
/*      */       int j;
/*      */       int k;
/* 3558 */       return ((k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i) ? null : new ValueSpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1L);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super V> param1Consumer) {
/* 3564 */       if (param1Consumer == null) throw new NullPointerException();  ConcurrentHashMap.Node<K, V> node;
/* 3565 */       while ((node = advance()) != null)
/* 3566 */         param1Consumer.accept(node.val); 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> param1Consumer) {
/* 3570 */       if (param1Consumer == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3572 */       if ((node = advance()) == null)
/* 3573 */         return false; 
/* 3574 */       param1Consumer.accept(node.val);
/* 3575 */       return true;
/*      */     }
/*      */     public long estimateSize() {
/* 3578 */       return this.est;
/*      */     }
/*      */     public int characteristics() {
/* 3581 */       return 4352;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends Traverser<K, V> implements Spliterator<Map.Entry<K, V>> {
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     long est;
/*      */     
/*      */     EntrySpliterator(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2, int param1Int3, long param1Long, ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 3591 */       super(param1ArrayOfNode, param1Int1, param1Int2, param1Int3);
/* 3592 */       this.map = param1ConcurrentHashMap;
/* 3593 */       this.est = param1Long;
/*      */     } public Spliterator<Map.Entry<K, V>> trySplit() {
/*      */       int i;
/*      */       int j;
/*      */       int k;
/* 3598 */       return ((k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i) ? null : new EntrySpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1L, this.map);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 3604 */       if (param1Consumer == null) throw new NullPointerException();  ConcurrentHashMap.Node<K, V> node;
/* 3605 */       while ((node = advance()) != null)
/* 3606 */         param1Consumer.accept(new ConcurrentHashMap.MapEntry<>(node.key, node.val, this.map)); 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 3610 */       if (param1Consumer == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 3612 */       if ((node = advance()) == null)
/* 3613 */         return false; 
/* 3614 */       param1Consumer.accept(new ConcurrentHashMap.MapEntry<>(node.key, node.val, this.map));
/* 3615 */       return true;
/*      */     }
/*      */     public long estimateSize() {
/* 3618 */       return this.est;
/*      */     }
/*      */     public int characteristics() {
/* 3621 */       return 4353;
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
/*      */   final int batchFor(long paramLong) {
/*      */     long l;
/* 3638 */     if (paramLong == Long.MAX_VALUE || (l = sumCount()) <= 1L || l < paramLong)
/* 3639 */       return 0; 
/* 3640 */     int i = ForkJoinPool.getCommonPoolParallelism() << 2;
/* 3641 */     return (paramLong <= 0L || (l /= paramLong) >= i) ? i : (int)l;
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
/*      */   public void forEach(long paramLong, BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 3654 */     if (paramBiConsumer == null) throw new NullPointerException(); 
/* 3655 */     (new ForEachMappingTask<>(null, 
/* 3656 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramBiConsumer))
/* 3657 */       .invoke();
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
/*      */   public <U> void forEach(long paramLong, BiFunction<? super K, ? super V, ? extends U> paramBiFunction, Consumer<? super U> paramConsumer) {
/* 3676 */     if (paramBiFunction == null || paramConsumer == null)
/* 3677 */       throw new NullPointerException(); 
/* 3678 */     (new ForEachTransformedMappingTask<>(null, 
/* 3679 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramBiFunction, paramConsumer))
/* 3680 */       .invoke();
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
/*      */   public <U> U search(long paramLong, BiFunction<? super K, ? super V, ? extends U> paramBiFunction) {
/* 3701 */     if (paramBiFunction == null) throw new NullPointerException(); 
/* 3702 */     return (new SearchMappingsTask<>(null, 
/* 3703 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramBiFunction, new AtomicReference<>()))
/* 3704 */       .invoke();
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
/*      */   public <U> U reduce(long paramLong, BiFunction<? super K, ? super V, ? extends U> paramBiFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction1) {
/* 3726 */     if (paramBiFunction == null || paramBiFunction1 == null)
/* 3727 */       throw new NullPointerException(); 
/* 3728 */     return (new MapReduceMappingsTask<>(null, 
/* 3729 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramBiFunction, paramBiFunction1))
/* 3730 */       .invoke();
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
/*      */   public double reduceToDouble(long paramLong, ToDoubleBiFunction<? super K, ? super V> paramToDoubleBiFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 3752 */     if (paramToDoubleBiFunction == null || paramDoubleBinaryOperator == null)
/* 3753 */       throw new NullPointerException(); 
/* 3754 */     return (new MapReduceMappingsToDoubleTask<>(null, 
/* 3755 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToDoubleBiFunction, paramDouble, paramDoubleBinaryOperator))
/* 3756 */       .invoke().doubleValue();
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
/*      */   public long reduceToLong(long paramLong1, ToLongBiFunction<? super K, ? super V> paramToLongBiFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator) {
/* 3778 */     if (paramToLongBiFunction == null || paramLongBinaryOperator == null)
/* 3779 */       throw new NullPointerException(); 
/* 3780 */     return (new MapReduceMappingsToLongTask<>(null, 
/* 3781 */         batchFor(paramLong1), 0, 0, (Node<?, ?>[])this.table, null, paramToLongBiFunction, paramLong2, paramLongBinaryOperator))
/* 3782 */       .invoke().longValue();
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
/*      */   public int reduceToInt(long paramLong, ToIntBiFunction<? super K, ? super V> paramToIntBiFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/* 3804 */     if (paramToIntBiFunction == null || paramIntBinaryOperator == null)
/* 3805 */       throw new NullPointerException(); 
/* 3806 */     return (new MapReduceMappingsToIntTask<>(null, 
/* 3807 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToIntBiFunction, paramInt, paramIntBinaryOperator))
/* 3808 */       .invoke().intValue();
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
/*      */   public void forEachKey(long paramLong, Consumer<? super K> paramConsumer) {
/* 3821 */     if (paramConsumer == null) throw new NullPointerException(); 
/* 3822 */     (new ForEachKeyTask<>(null, 
/* 3823 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramConsumer))
/* 3824 */       .invoke();
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
/*      */   public <U> void forEachKey(long paramLong, Function<? super K, ? extends U> paramFunction, Consumer<? super U> paramConsumer) {
/* 3843 */     if (paramFunction == null || paramConsumer == null)
/* 3844 */       throw new NullPointerException(); 
/* 3845 */     (new ForEachTransformedKeyTask<>(null, 
/* 3846 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramFunction, paramConsumer))
/* 3847 */       .invoke();
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
/*      */   public <U> U searchKeys(long paramLong, Function<? super K, ? extends U> paramFunction) {
/* 3868 */     if (paramFunction == null) throw new NullPointerException(); 
/* 3869 */     return (new SearchKeysTask<>(null, 
/* 3870 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramFunction, new AtomicReference<>()))
/* 3871 */       .invoke();
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
/*      */   public K reduceKeys(long paramLong, BiFunction<? super K, ? super K, ? extends K> paramBiFunction) {
/* 3887 */     if (paramBiFunction == null) throw new NullPointerException(); 
/* 3888 */     return (new ReduceKeysTask<>(null, 
/* 3889 */         batchFor(paramLong), 0, 0, (Node<K, ?>[])this.table, null, paramBiFunction))
/* 3890 */       .invoke();
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
/*      */   public <U> U reduceKeys(long paramLong, Function<? super K, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction) {
/* 3912 */     if (paramFunction == null || paramBiFunction == null)
/* 3913 */       throw new NullPointerException(); 
/* 3914 */     return (new MapReduceKeysTask<>(null, 
/* 3915 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramFunction, paramBiFunction))
/* 3916 */       .invoke();
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
/*      */   public double reduceKeysToDouble(long paramLong, ToDoubleFunction<? super K> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 3938 */     if (paramToDoubleFunction == null || paramDoubleBinaryOperator == null)
/* 3939 */       throw new NullPointerException(); 
/* 3940 */     return (new MapReduceKeysToDoubleTask<>(null, 
/* 3941 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToDoubleFunction, paramDouble, paramDoubleBinaryOperator))
/* 3942 */       .invoke().doubleValue();
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
/*      */   public long reduceKeysToLong(long paramLong1, ToLongFunction<? super K> paramToLongFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator) {
/* 3964 */     if (paramToLongFunction == null || paramLongBinaryOperator == null)
/* 3965 */       throw new NullPointerException(); 
/* 3966 */     return (new MapReduceKeysToLongTask<>(null, 
/* 3967 */         batchFor(paramLong1), 0, 0, (Node<?, ?>[])this.table, null, paramToLongFunction, paramLong2, paramLongBinaryOperator))
/* 3968 */       .invoke().longValue();
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
/*      */   public int reduceKeysToInt(long paramLong, ToIntFunction<? super K> paramToIntFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/* 3990 */     if (paramToIntFunction == null || paramIntBinaryOperator == null)
/* 3991 */       throw new NullPointerException(); 
/* 3992 */     return (new MapReduceKeysToIntTask<>(null, 
/* 3993 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToIntFunction, paramInt, paramIntBinaryOperator))
/* 3994 */       .invoke().intValue();
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
/*      */   public void forEachValue(long paramLong, Consumer<? super V> paramConsumer) {
/* 4007 */     if (paramConsumer == null)
/* 4008 */       throw new NullPointerException(); 
/* 4009 */     (new ForEachValueTask<>(null, 
/* 4010 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramConsumer))
/* 4011 */       .invoke();
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
/*      */   public <U> void forEachValue(long paramLong, Function<? super V, ? extends U> paramFunction, Consumer<? super U> paramConsumer) {
/* 4030 */     if (paramFunction == null || paramConsumer == null)
/* 4031 */       throw new NullPointerException(); 
/* 4032 */     (new ForEachTransformedValueTask<>(null, 
/* 4033 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramFunction, paramConsumer))
/* 4034 */       .invoke();
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
/*      */   public <U> U searchValues(long paramLong, Function<? super V, ? extends U> paramFunction) {
/* 4055 */     if (paramFunction == null) throw new NullPointerException(); 
/* 4056 */     return (new SearchValuesTask<>(null, 
/* 4057 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramFunction, new AtomicReference<>()))
/* 4058 */       .invoke();
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
/*      */   public V reduceValues(long paramLong, BiFunction<? super V, ? super V, ? extends V> paramBiFunction) {
/* 4073 */     if (paramBiFunction == null) throw new NullPointerException(); 
/* 4074 */     return (new ReduceValuesTask<>(null, 
/* 4075 */         batchFor(paramLong), 0, 0, (Node<?, V>[])this.table, null, paramBiFunction))
/* 4076 */       .invoke();
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
/*      */   public <U> U reduceValues(long paramLong, Function<? super V, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction) {
/* 4098 */     if (paramFunction == null || paramBiFunction == null)
/* 4099 */       throw new NullPointerException(); 
/* 4100 */     return (new MapReduceValuesTask<>(null, 
/* 4101 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramFunction, paramBiFunction))
/* 4102 */       .invoke();
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
/*      */   public double reduceValuesToDouble(long paramLong, ToDoubleFunction<? super V> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 4124 */     if (paramToDoubleFunction == null || paramDoubleBinaryOperator == null)
/* 4125 */       throw new NullPointerException(); 
/* 4126 */     return (new MapReduceValuesToDoubleTask<>(null, 
/* 4127 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToDoubleFunction, paramDouble, paramDoubleBinaryOperator))
/* 4128 */       .invoke().doubleValue();
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
/*      */   public long reduceValuesToLong(long paramLong1, ToLongFunction<? super V> paramToLongFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator) {
/* 4150 */     if (paramToLongFunction == null || paramLongBinaryOperator == null)
/* 4151 */       throw new NullPointerException(); 
/* 4152 */     return (new MapReduceValuesToLongTask<>(null, 
/* 4153 */         batchFor(paramLong1), 0, 0, (Node<?, ?>[])this.table, null, paramToLongFunction, paramLong2, paramLongBinaryOperator))
/* 4154 */       .invoke().longValue();
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
/*      */   public int reduceValuesToInt(long paramLong, ToIntFunction<? super V> paramToIntFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/* 4176 */     if (paramToIntFunction == null || paramIntBinaryOperator == null)
/* 4177 */       throw new NullPointerException(); 
/* 4178 */     return (new MapReduceValuesToIntTask<>(null, 
/* 4179 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToIntFunction, paramInt, paramIntBinaryOperator))
/* 4180 */       .invoke().intValue();
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
/*      */   public void forEachEntry(long paramLong, Consumer<? super Map.Entry<K, V>> paramConsumer) {
/* 4193 */     if (paramConsumer == null) throw new NullPointerException(); 
/* 4194 */     (new ForEachEntryTask<>(null, batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramConsumer))
/* 4195 */       .invoke();
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
/*      */   public <U> void forEachEntry(long paramLong, Function<Map.Entry<K, V>, ? extends U> paramFunction, Consumer<? super U> paramConsumer) {
/* 4214 */     if (paramFunction == null || paramConsumer == null)
/* 4215 */       throw new NullPointerException(); 
/* 4216 */     (new ForEachTransformedEntryTask<>(null, 
/* 4217 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramFunction, paramConsumer))
/* 4218 */       .invoke();
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
/*      */   public <U> U searchEntries(long paramLong, Function<Map.Entry<K, V>, ? extends U> paramFunction) {
/* 4239 */     if (paramFunction == null) throw new NullPointerException(); 
/* 4240 */     return (new SearchEntriesTask<>(null, 
/* 4241 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, paramFunction, new AtomicReference<>()))
/* 4242 */       .invoke();
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
/*      */   public Map.Entry<K, V> reduceEntries(long paramLong, BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> paramBiFunction) {
/* 4257 */     if (paramBiFunction == null) throw new NullPointerException(); 
/* 4258 */     return (new ReduceEntriesTask<>(null, 
/* 4259 */         batchFor(paramLong), 0, 0, this.table, null, paramBiFunction))
/* 4260 */       .invoke();
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
/*      */   public <U> U reduceEntries(long paramLong, Function<Map.Entry<K, V>, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction) {
/* 4282 */     if (paramFunction == null || paramBiFunction == null)
/* 4283 */       throw new NullPointerException(); 
/* 4284 */     return (new MapReduceEntriesTask<>(null, 
/* 4285 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramFunction, paramBiFunction))
/* 4286 */       .invoke();
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
/*      */   public double reduceEntriesToDouble(long paramLong, ToDoubleFunction<Map.Entry<K, V>> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 4308 */     if (paramToDoubleFunction == null || paramDoubleBinaryOperator == null)
/* 4309 */       throw new NullPointerException(); 
/* 4310 */     return (new MapReduceEntriesToDoubleTask<>(null, 
/* 4311 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToDoubleFunction, paramDouble, paramDoubleBinaryOperator))
/* 4312 */       .invoke().doubleValue();
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
/*      */   public long reduceEntriesToLong(long paramLong1, ToLongFunction<Map.Entry<K, V>> paramToLongFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator) {
/* 4334 */     if (paramToLongFunction == null || paramLongBinaryOperator == null)
/* 4335 */       throw new NullPointerException(); 
/* 4336 */     return (new MapReduceEntriesToLongTask<>(null, 
/* 4337 */         batchFor(paramLong1), 0, 0, (Node<?, ?>[])this.table, null, paramToLongFunction, paramLong2, paramLongBinaryOperator))
/* 4338 */       .invoke().longValue();
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
/*      */   public int reduceEntriesToInt(long paramLong, ToIntFunction<Map.Entry<K, V>> paramToIntFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/* 4360 */     if (paramToIntFunction == null || paramIntBinaryOperator == null)
/* 4361 */       throw new NullPointerException(); 
/* 4362 */     return (new MapReduceEntriesToIntTask<>(null, 
/* 4363 */         batchFor(paramLong), 0, 0, (Node<?, ?>[])this.table, null, paramToIntFunction, paramInt, paramIntBinaryOperator))
/* 4364 */       .invoke().intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class CollectionView<K, V, E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */     
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     private static final String oomeMsg = "Required array size too large";
/*      */     
/*      */     CollectionView(ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 4377 */       this.map = param1ConcurrentHashMap;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ConcurrentHashMap<K, V> getMap() {
/* 4384 */       return this.map;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 4390 */       this.map.clear();
/* 4391 */     } public final int size() { return this.map.size(); } public final boolean isEmpty() {
/* 4392 */       return this.map.isEmpty();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract Iterator<E> iterator();
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract boolean contains(Object param1Object);
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract boolean remove(Object param1Object);
/*      */ 
/*      */ 
/*      */     
/*      */     public final Object[] toArray() {
/* 4411 */       long l = this.map.mappingCount();
/* 4412 */       if (l > 2147483639L)
/* 4413 */         throw new OutOfMemoryError("Required array size too large"); 
/* 4414 */       int i = (int)l;
/* 4415 */       Object[] arrayOfObject = new Object[i];
/* 4416 */       int j = 0;
/* 4417 */       for (E e : this) {
/* 4418 */         if (j == i) {
/* 4419 */           if (i >= 2147483639)
/* 4420 */             throw new OutOfMemoryError("Required array size too large"); 
/* 4421 */           if (i >= 1073741819) {
/* 4422 */             i = 2147483639;
/*      */           } else {
/* 4424 */             i += (i >>> 1) + 1;
/* 4425 */           }  arrayOfObject = Arrays.copyOf(arrayOfObject, i);
/*      */         } 
/* 4427 */         arrayOfObject[j++] = e;
/*      */       } 
/* 4429 */       return (j == i) ? arrayOfObject : Arrays.<Object>copyOf(arrayOfObject, j);
/*      */     }
/*      */ 
/*      */     
/*      */     public final <T> T[] toArray(T[] param1ArrayOfT) {
/* 4434 */       long l = this.map.mappingCount();
/* 4435 */       if (l > 2147483639L)
/* 4436 */         throw new OutOfMemoryError("Required array size too large"); 
/* 4437 */       int i = (int)l;
/*      */ 
/*      */       
/* 4440 */       Object[] arrayOfObject = (Object[])((param1ArrayOfT.length >= i) ? (Object)param1ArrayOfT : Array.newInstance(param1ArrayOfT.getClass().getComponentType(), i));
/* 4441 */       int j = arrayOfObject.length;
/* 4442 */       int k = 0;
/* 4443 */       for (E e : this) {
/* 4444 */         if (k == j) {
/* 4445 */           if (j >= 2147483639)
/* 4446 */             throw new OutOfMemoryError("Required array size too large"); 
/* 4447 */           if (j >= 1073741819) {
/* 4448 */             j = 2147483639;
/*      */           } else {
/* 4450 */             j += (j >>> 1) + 1;
/* 4451 */           }  arrayOfObject = Arrays.copyOf(arrayOfObject, j);
/*      */         } 
/* 4453 */         arrayOfObject[k++] = e;
/*      */       } 
/* 4455 */       if (param1ArrayOfT == arrayOfObject && k < j) {
/* 4456 */         arrayOfObject[k] = null;
/* 4457 */         return (T[])arrayOfObject;
/*      */       } 
/* 4459 */       return (k == j) ? (T[])arrayOfObject : Arrays.<T>copyOf((T[])arrayOfObject, k);
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
/*      */     public final String toString() {
/* 4474 */       StringBuilder stringBuilder = new StringBuilder();
/* 4475 */       stringBuilder.append('[');
/* 4476 */       Iterator<E> iterator = iterator();
/* 4477 */       if (iterator.hasNext()) {
/*      */         while (true) {
/* 4479 */           E e = iterator.next();
/* 4480 */           stringBuilder.append((e == this) ? "(this Collection)" : e);
/* 4481 */           if (!iterator.hasNext())
/*      */             break; 
/* 4483 */           stringBuilder.append(',').append(' ');
/*      */         } 
/*      */       }
/* 4486 */       return stringBuilder.append(']').toString();
/*      */     }
/*      */     
/*      */     public final boolean containsAll(Collection<?> param1Collection) {
/* 4490 */       if (param1Collection != this)
/* 4491 */         for (Object object : param1Collection) {
/* 4492 */           if (object == null || !contains(object)) {
/* 4493 */             return false;
/*      */           }
/*      */         }  
/* 4496 */       return true;
/*      */     }
/*      */     
/*      */     public final boolean removeAll(Collection<?> param1Collection) {
/* 4500 */       if (param1Collection == null) throw new NullPointerException(); 
/* 4501 */       boolean bool = false;
/* 4502 */       for (Iterator<E> iterator = iterator(); iterator.hasNext();) {
/* 4503 */         if (param1Collection.contains(iterator.next())) {
/* 4504 */           iterator.remove();
/* 4505 */           bool = true;
/*      */         } 
/*      */       } 
/* 4508 */       return bool;
/*      */     }
/*      */     
/*      */     public final boolean retainAll(Collection<?> param1Collection) {
/* 4512 */       if (param1Collection == null) throw new NullPointerException(); 
/* 4513 */       boolean bool = false;
/* 4514 */       for (Iterator<E> iterator = iterator(); iterator.hasNext();) {
/* 4515 */         if (!param1Collection.contains(iterator.next())) {
/* 4516 */           iterator.remove();
/* 4517 */           bool = true;
/*      */         } 
/*      */       } 
/* 4520 */       return bool;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class KeySetView<K, V>
/*      */     extends CollectionView<K, V, K>
/*      */     implements Set<K>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */ 
/*      */ 
/*      */     
/*      */     private final V value;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     KeySetView(ConcurrentHashMap<K, V> param1ConcurrentHashMap, V param1V) {
/* 4541 */       super(param1ConcurrentHashMap);
/* 4542 */       this.value = param1V;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V getMappedValue() {
/* 4552 */       return this.value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 4558 */       return this.map.containsKey(param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object) {
/* 4569 */       return (this.map.remove(param1Object) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<K> iterator() {
/* 4576 */       ConcurrentHashMap<K, V> concurrentHashMap = this.map; ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4577 */       boolean bool = ((arrayOfNode = concurrentHashMap.table) == null) ? false : arrayOfNode.length;
/* 4578 */       return new ConcurrentHashMap.KeyIterator<>(arrayOfNode, bool, 0, bool, concurrentHashMap);
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
/*      */     public boolean add(K param1K) {
/*      */       V v;
/* 4593 */       if ((v = this.value) == null)
/* 4594 */         throw new UnsupportedOperationException(); 
/* 4595 */       return (this.map.putVal(param1K, v, true) == null);
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
/*      */     public boolean addAll(Collection<? extends K> param1Collection) {
/*      */       // Byte code:
/*      */       //   0: iconst_0
/*      */       //   1: istore_2
/*      */       //   2: aload_0
/*      */       //   3: getfield value : Ljava/lang/Object;
/*      */       //   6: dup
/*      */       //   7: astore_3
/*      */       //   8: ifnonnull -> 19
/*      */       //   11: new java/lang/UnsupportedOperationException
/*      */       //   14: dup
/*      */       //   15: invokespecial <init> : ()V
/*      */       //   18: athrow
/*      */       //   19: aload_1
/*      */       //   20: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */       //   25: astore #4
/*      */       //   27: aload #4
/*      */       //   29: invokeinterface hasNext : ()Z
/*      */       //   34: ifeq -> 65
/*      */       //   37: aload #4
/*      */       //   39: invokeinterface next : ()Ljava/lang/Object;
/*      */       //   44: astore #5
/*      */       //   46: aload_0
/*      */       //   47: getfield map : Ljava/util/concurrent/ConcurrentHashMap;
/*      */       //   50: aload #5
/*      */       //   52: aload_3
/*      */       //   53: iconst_1
/*      */       //   54: invokevirtual putVal : (Ljava/lang/Object;Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */       //   57: ifnonnull -> 62
/*      */       //   60: iconst_1
/*      */       //   61: istore_2
/*      */       //   62: goto -> 27
/*      */       //   65: iload_2
/*      */       //   66: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #4610	-> 0
/*      */       //   #4612	-> 2
/*      */       //   #4613	-> 11
/*      */       //   #4614	-> 19
/*      */       //   #4615	-> 46
/*      */       //   #4616	-> 60
/*      */       //   #4617	-> 62
/*      */       //   #4618	-> 65
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
/*      */     public int hashCode() {
/* 4622 */       int i = 0;
/* 4623 */       for (K k : this)
/* 4624 */         i += k.hashCode(); 
/* 4625 */       return i;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*      */       Set<?> set;
/* 4630 */       return (param1Object instanceof Set && ((set = (Set)param1Object) == this || (
/*      */         
/* 4632 */         containsAll(set) && set.containsAll(this))));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<K> spliterator() {
/* 4637 */       ConcurrentHashMap<K, V> concurrentHashMap = this.map;
/* 4638 */       long l = concurrentHashMap.sumCount(); ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4639 */       boolean bool = ((arrayOfNode = concurrentHashMap.table) == null) ? false : arrayOfNode.length;
/* 4640 */       return new ConcurrentHashMap.KeySpliterator<>(arrayOfNode, bool, 0, bool, (l < 0L) ? 0L : l);
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super K> param1Consumer) {
/* 4644 */       if (param1Consumer == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4646 */       if ((arrayOfNode = this.map.table) != null) {
/* 4647 */         ConcurrentHashMap.Traverser<K, V> traverser = new ConcurrentHashMap.Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); ConcurrentHashMap.Node<K, V> node;
/* 4648 */         while ((node = traverser.advance()) != null) {
/* 4649 */           param1Consumer.accept(node.key);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValuesView<K, V>
/*      */     extends CollectionView<K, V, V>
/*      */     implements Collection<V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/*      */     ValuesView(ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 4662 */       super(param1ConcurrentHashMap);
/*      */     } public final boolean contains(Object param1Object) {
/* 4664 */       return this.map.containsValue(param1Object);
/*      */     }
/*      */     
/*      */     public final boolean remove(Object param1Object) {
/* 4668 */       if (param1Object != null) {
/* 4669 */         for (Iterator<V> iterator = iterator(); iterator.hasNext();) {
/* 4670 */           if (param1Object.equals(iterator.next())) {
/* 4671 */             iterator.remove();
/* 4672 */             return true;
/*      */           } 
/*      */         } 
/*      */       }
/* 4676 */       return false;
/*      */     }
/*      */     
/*      */     public final Iterator<V> iterator() {
/* 4680 */       ConcurrentHashMap<K, V> concurrentHashMap = this.map;
/*      */       ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4682 */       boolean bool = ((arrayOfNode = concurrentHashMap.table) == null) ? false : arrayOfNode.length;
/* 4683 */       return new ConcurrentHashMap.ValueIterator<>(arrayOfNode, bool, 0, bool, concurrentHashMap);
/*      */     }
/*      */     
/*      */     public final boolean add(V param1V) {
/* 4687 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public final boolean addAll(Collection<? extends V> param1Collection) {
/* 4690 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<V> spliterator() {
/* 4695 */       ConcurrentHashMap<K, V> concurrentHashMap = this.map;
/* 4696 */       long l = concurrentHashMap.sumCount(); ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4697 */       boolean bool = ((arrayOfNode = concurrentHashMap.table) == null) ? false : arrayOfNode.length;
/* 4698 */       return new ConcurrentHashMap.ValueSpliterator<>(arrayOfNode, bool, 0, bool, (l < 0L) ? 0L : l);
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super V> param1Consumer) {
/* 4702 */       if (param1Consumer == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4704 */       if ((arrayOfNode = this.map.table) != null) {
/* 4705 */         ConcurrentHashMap.Traverser<K, V> traverser = new ConcurrentHashMap.Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); ConcurrentHashMap.Node<K, V> node;
/* 4706 */         while ((node = traverser.advance()) != null) {
/* 4707 */           param1Consumer.accept(node.val);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySetView<K, V>
/*      */     extends CollectionView<K, V, Map.Entry<K, V>>
/*      */     implements Set<Map.Entry<K, V>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/*      */     EntrySetView(ConcurrentHashMap<K, V> param1ConcurrentHashMap) {
/* 4720 */       super(param1ConcurrentHashMap); } public boolean contains(Object param1Object) { Object object;
/*      */       Object object1;
/*      */       V v;
/*      */       Map.Entry<Object, ?> entry;
/* 4724 */       return (param1Object instanceof Map.Entry && (
/* 4725 */         object = (entry = (Map.Entry)param1Object).getKey()) != null && (
/* 4726 */         v = this.map.get(object)) != null && (
/* 4727 */         object1 = entry.getValue()) != null && (object1 == v || object1
/* 4728 */         .equals(v))); }
/*      */      public boolean remove(Object param1Object) {
/*      */       Object object;
/*      */       Object object1;
/*      */       Map.Entry<Object, ?> entry;
/* 4733 */       return (param1Object instanceof Map.Entry && (
/* 4734 */         object = (entry = (Map.Entry)param1Object).getKey()) != null && (
/* 4735 */         object1 = entry.getValue()) != null && this.map
/* 4736 */         .remove(object, object1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 4743 */       ConcurrentHashMap<K, V> concurrentHashMap = this.map;
/*      */       ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4745 */       boolean bool = ((arrayOfNode = concurrentHashMap.table) == null) ? false : arrayOfNode.length;
/* 4746 */       return new ConcurrentHashMap.EntryIterator<>(arrayOfNode, bool, 0, bool, concurrentHashMap);
/*      */     }
/*      */     
/*      */     public boolean add(Map.Entry<K, V> param1Entry) {
/* 4750 */       return (this.map.putVal(param1Entry.getKey(), param1Entry.getValue(), false) == null);
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Map.Entry<K, V>> param1Collection) {
/* 4754 */       boolean bool = false;
/* 4755 */       for (Map.Entry<K, V> entry : param1Collection) {
/* 4756 */         if (add(entry))
/* 4757 */           bool = true; 
/*      */       } 
/* 4759 */       return bool;
/*      */     }
/*      */     
/*      */     public final int hashCode() {
/* 4763 */       int i = 0;
/*      */       ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4765 */       if ((arrayOfNode = this.map.table) != null) {
/* 4766 */         ConcurrentHashMap.Traverser<K, V> traverser = new ConcurrentHashMap.Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); ConcurrentHashMap.Node<K, V> node;
/* 4767 */         while ((node = traverser.advance()) != null) {
/* 4768 */           i += node.hashCode();
/*      */         }
/*      */       } 
/* 4771 */       return i;
/*      */     }
/*      */     
/*      */     public final boolean equals(Object param1Object) {
/*      */       Set<?> set;
/* 4776 */       return (param1Object instanceof Set && ((set = (Set)param1Object) == this || (
/*      */         
/* 4778 */         containsAll(set) && set.containsAll(this))));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> spliterator() {
/* 4783 */       ConcurrentHashMap<K, V> concurrentHashMap = this.map;
/* 4784 */       long l = concurrentHashMap.sumCount(); ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4785 */       boolean bool = ((arrayOfNode = concurrentHashMap.table) == null) ? false : arrayOfNode.length;
/* 4786 */       return new ConcurrentHashMap.EntrySpliterator<>(arrayOfNode, bool, 0, bool, (l < 0L) ? 0L : l, concurrentHashMap);
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 4790 */       if (param1Consumer == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMap.Node<K, V>[] arrayOfNode;
/* 4792 */       if ((arrayOfNode = this.map.table) != null) {
/* 4793 */         ConcurrentHashMap.Traverser<K, V> traverser = new ConcurrentHashMap.Traverser<>(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length); ConcurrentHashMap.Node<K, V> node;
/* 4794 */         while ((node = traverser.advance()) != null) {
/* 4795 */           param1Consumer.accept(new ConcurrentHashMap.MapEntry<>(node.key, node.val, this.map));
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class BulkTask<K, V, R>
/*      */     extends CountedCompleter<R>
/*      */   {
/*      */     ConcurrentHashMap.Node<K, V>[] tab;
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> next;
/*      */     
/*      */     ConcurrentHashMap.TableStack<K, V> stack;
/*      */     
/*      */     ConcurrentHashMap.TableStack<K, V> spare;
/*      */     int index;
/*      */     int baseIndex;
/*      */     int baseLimit;
/*      */     final int baseSize;
/*      */     int batch;
/*      */     
/*      */     BulkTask(BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode) {
/* 4819 */       super(param1BulkTask);
/* 4820 */       this.batch = param1Int1;
/* 4821 */       this.index = this.baseIndex = param1Int2;
/* 4822 */       if ((this.tab = param1ArrayOfNode) == null) {
/* 4823 */         this.baseSize = this.baseLimit = 0;
/* 4824 */       } else if (param1BulkTask == null) {
/* 4825 */         this.baseSize = this.baseLimit = param1ArrayOfNode.length;
/*      */       } else {
/* 4827 */         this.baseLimit = param1Int3;
/* 4828 */         this.baseSize = param1BulkTask.baseSize;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMap.Node<K, V> advance() {
/*      */       ConcurrentHashMap.Node<K, V> node;
/* 4837 */       if ((node = this.next) != null) {
/* 4838 */         node = node.next;
/*      */       }
/*      */       while (true) {
/* 4841 */         if (node != null)
/* 4842 */           return this.next = node;  ConcurrentHashMap.Node<K, V>[] arrayOfNode; int i; int j;
/* 4843 */         if (this.baseIndex >= this.baseLimit || (arrayOfNode = this.tab) == null || (j = arrayOfNode.length) <= (i = this.index) || i < 0)
/*      */         {
/* 4845 */           return this.next = null; } 
/* 4846 */         if ((node = ConcurrentHashMap.<K, V>tabAt(arrayOfNode, i)) != null && node.hash < 0) {
/* 4847 */           if (node instanceof ConcurrentHashMap.ForwardingNode) {
/* 4848 */             this.tab = (ConcurrentHashMap.Node<K, V>[])((ConcurrentHashMap.ForwardingNode)node).nextTable;
/* 4849 */             node = null;
/* 4850 */             pushState(arrayOfNode, i, j);
/*      */             continue;
/*      */           } 
/* 4853 */           if (node instanceof ConcurrentHashMap.TreeBin) {
/* 4854 */             node = ((ConcurrentHashMap.TreeBin)node).first;
/*      */           } else {
/* 4856 */             node = null;
/*      */           } 
/* 4858 */         }  if (this.stack != null) {
/* 4859 */           recoverState(j); continue;
/* 4860 */         }  if ((this.index = i + this.baseSize) >= j)
/* 4861 */           this.index = ++this.baseIndex; 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void pushState(ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, int param1Int1, int param1Int2) {
/* 4866 */       ConcurrentHashMap.TableStack<K, V> tableStack = this.spare;
/* 4867 */       if (tableStack != null) {
/* 4868 */         this.spare = tableStack.next;
/*      */       } else {
/* 4870 */         tableStack = new ConcurrentHashMap.TableStack<>();
/* 4871 */       }  tableStack.tab = param1ArrayOfNode;
/* 4872 */       tableStack.length = param1Int2;
/* 4873 */       tableStack.index = param1Int1;
/* 4874 */       tableStack.next = this.stack;
/* 4875 */       this.stack = tableStack;
/*      */     }
/*      */     private void recoverState(int param1Int) {
/*      */       ConcurrentHashMap.TableStack<K, V> tableStack;
/*      */       int i;
/* 4880 */       while ((tableStack = this.stack) != null && (this.index += i = tableStack.length) >= param1Int) {
/* 4881 */         param1Int = i;
/* 4882 */         this.index = tableStack.index;
/* 4883 */         this.tab = tableStack.tab;
/* 4884 */         tableStack.tab = null;
/* 4885 */         ConcurrentHashMap.TableStack<K, V> tableStack1 = tableStack.next;
/* 4886 */         tableStack.next = this.spare;
/* 4887 */         this.stack = tableStack1;
/* 4888 */         this.spare = tableStack;
/*      */       } 
/* 4890 */       if (tableStack == null && (this.index += this.baseSize) >= param1Int) {
/* 4891 */         this.index = ++this.baseIndex;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ForEachKeyTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final Consumer<? super K> action;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ForEachKeyTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Consumer<? super K> param1Consumer) {
/* 4909 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 4910 */       this.action = param1Consumer;
/*      */     }
/*      */     public final void compute() {
/*      */       Consumer<? super K> consumer;
/* 4914 */       if ((consumer = this.action) != null) {
/* 4915 */         int j; int k; for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4917 */           addToPendingCount(1);
/* 4918 */           (new ForEachKeyTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, consumer))
/*      */             
/* 4920 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 4922 */         while ((node = advance()) != null)
/* 4923 */           consumer.accept(node.key); 
/* 4924 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachValueTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final Consumer<? super V> action;
/*      */     
/*      */     ForEachValueTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Consumer<? super V> param1Consumer) {
/* 4936 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 4937 */       this.action = param1Consumer;
/*      */     }
/*      */     public final void compute() {
/*      */       Consumer<? super V> consumer;
/* 4941 */       if ((consumer = this.action) != null) {
/* 4942 */         int j; int k; for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4944 */           addToPendingCount(1);
/* 4945 */           (new ForEachValueTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, consumer))
/*      */             
/* 4947 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 4949 */         while ((node = advance()) != null)
/* 4950 */           consumer.accept(node.val); 
/* 4951 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachEntryTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final Consumer<? super Map.Entry<K, V>> action;
/*      */     
/*      */     ForEachEntryTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Consumer<? super Map.Entry<K, V>> param1Consumer) {
/* 4963 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 4964 */       this.action = param1Consumer;
/*      */     }
/*      */     public final void compute() {
/*      */       Consumer<? super Map.Entry<K, V>> consumer;
/* 4968 */       if ((consumer = this.action) != null) {
/* 4969 */         int j; int k; for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4971 */           addToPendingCount(1);
/* 4972 */           (new ForEachEntryTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, consumer))
/*      */             
/* 4974 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 4976 */         while ((node = advance()) != null)
/* 4977 */           consumer.accept(node); 
/* 4978 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachMappingTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final BiConsumer<? super K, ? super V> action;
/*      */     
/*      */     ForEachMappingTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 4990 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 4991 */       this.action = param1BiConsumer;
/*      */     }
/*      */     public final void compute() {
/*      */       BiConsumer<? super K, ? super V> biConsumer;
/* 4995 */       if ((biConsumer = this.action) != null) {
/* 4996 */         int j; int k; for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4998 */           addToPendingCount(1);
/* 4999 */           (new ForEachMappingTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, biConsumer))
/*      */             
/* 5001 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5003 */         while ((node = advance()) != null)
/* 5004 */           biConsumer.accept(node.key, node.val); 
/* 5005 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedKeyTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final Function<? super K, ? extends U> transformer;
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedKeyTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Function<? super K, ? extends U> param1Function, Consumer<? super U> param1Consumer) {
/* 5018 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5019 */       this.transformer = param1Function; this.action = param1Consumer;
/*      */     }
/*      */     public final void compute() {
/*      */       Function<? super K, ? extends U> function;
/*      */       Consumer<? super U> consumer;
/* 5024 */       if ((function = this.transformer) != null && (consumer = this.action) != null) {
/*      */         int j; int k;
/* 5026 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5028 */           addToPendingCount(1);
/* 5029 */           (new ForEachTransformedKeyTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, function, consumer))
/*      */             
/* 5031 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5033 */         while ((node = advance()) != null) {
/*      */           
/* 5035 */           if ((j = function.apply(node.key)) != null)
/* 5036 */             consumer.accept(j); 
/*      */         } 
/* 5038 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedValueTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final Function<? super V, ? extends U> transformer;
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedValueTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Function<? super V, ? extends U> param1Function, Consumer<? super U> param1Consumer) {
/* 5051 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5052 */       this.transformer = param1Function; this.action = param1Consumer;
/*      */     }
/*      */     public final void compute() {
/*      */       Function<? super V, ? extends U> function;
/*      */       Consumer<? super U> consumer;
/* 5057 */       if ((function = this.transformer) != null && (consumer = this.action) != null) {
/*      */         int j; int k;
/* 5059 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5061 */           addToPendingCount(1);
/* 5062 */           (new ForEachTransformedValueTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, function, consumer))
/*      */             
/* 5064 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5066 */         while ((node = advance()) != null) {
/*      */           
/* 5068 */           if ((j = function.apply(node.val)) != null)
/* 5069 */             consumer.accept(j); 
/*      */         } 
/* 5071 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedEntryTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final Function<Map.Entry<K, V>, ? extends U> transformer;
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedEntryTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Function<Map.Entry<K, V>, ? extends U> param1Function, Consumer<? super U> param1Consumer) {
/* 5084 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5085 */       this.transformer = param1Function; this.action = param1Consumer;
/*      */     }
/*      */     public final void compute() {
/*      */       Function<Map.Entry<K, V>, ? extends U> function;
/*      */       Consumer<? super U> consumer;
/* 5090 */       if ((function = this.transformer) != null && (consumer = this.action) != null) {
/*      */         int j; int k;
/* 5092 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5094 */           addToPendingCount(1);
/* 5095 */           (new ForEachTransformedEntryTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, function, consumer))
/*      */             
/* 5097 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5099 */         while ((node = advance()) != null) {
/*      */           
/* 5101 */           if ((j = function.apply(node)) != null)
/* 5102 */             consumer.accept(j); 
/*      */         } 
/* 5104 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedMappingTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final BiFunction<? super K, ? super V, ? extends U> transformer;
/*      */     
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedMappingTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, BiFunction<? super K, ? super V, ? extends U> param1BiFunction, Consumer<? super U> param1Consumer) {
/* 5118 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5119 */       this.transformer = param1BiFunction; this.action = param1Consumer;
/*      */     }
/*      */     public final void compute() {
/*      */       BiFunction<? super K, ? super V, ? extends U> biFunction;
/*      */       Consumer<? super U> consumer;
/* 5124 */       if ((biFunction = this.transformer) != null && (consumer = this.action) != null) {
/*      */         int j; int k;
/* 5126 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5128 */           addToPendingCount(1);
/* 5129 */           (new ForEachTransformedMappingTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, biFunction, consumer))
/*      */             
/* 5131 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5133 */         while ((node = advance()) != null) {
/*      */           
/* 5135 */           if ((j = biFunction.apply(node.key, node.val)) != null)
/* 5136 */             consumer.accept(j); 
/*      */         } 
/* 5138 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchKeysTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super K, ? extends U> searchFunction;
/*      */     
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchKeysTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Function<? super K, ? extends U> param1Function, AtomicReference<U> param1AtomicReference) {
/* 5152 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5153 */       this.searchFunction = param1Function; this.result = param1AtomicReference;
/*      */     } public final U getRawResult() {
/* 5155 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       Function<? super K, ? extends U> function;
/*      */       AtomicReference<U> atomicReference;
/* 5159 */       if ((function = this.searchFunction) != null && (atomicReference = this.result) != null) {
/*      */         int i; int j; int k;
/* 5161 */         for (i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5163 */           if (atomicReference.get() != null)
/*      */             return; 
/* 5165 */           addToPendingCount(1);
/* 5166 */           (new SearchKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, function, atomicReference))
/*      */             
/* 5168 */             .fork();
/*      */         } 
/* 5170 */         while (atomicReference.get() == null) {
/*      */           ConcurrentHashMap.Node<K, V> node;
/*      */           
/* 5173 */           if ((node = advance()) == null) {
/* 5174 */             propagateCompletion();
/*      */             break;
/*      */           } 
/* 5177 */           if ((i = function.apply(node.key)) != null) {
/* 5178 */             if (atomicReference.compareAndSet(null, i)) {
/* 5179 */               quietlyCompleteRoot();
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchValuesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super V, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchValuesTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Function<? super V, ? extends U> param1Function, AtomicReference<U> param1AtomicReference) {
/* 5196 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5197 */       this.searchFunction = param1Function; this.result = param1AtomicReference;
/*      */     } public final U getRawResult() {
/* 5199 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       Function<? super V, ? extends U> function;
/*      */       AtomicReference<U> atomicReference;
/* 5203 */       if ((function = this.searchFunction) != null && (atomicReference = this.result) != null) {
/*      */         int i; int j; int k;
/* 5205 */         for (i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5207 */           if (atomicReference.get() != null)
/*      */             return; 
/* 5209 */           addToPendingCount(1);
/* 5210 */           (new SearchValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, function, atomicReference))
/*      */             
/* 5212 */             .fork();
/*      */         } 
/* 5214 */         while (atomicReference.get() == null) {
/*      */           ConcurrentHashMap.Node<K, V> node;
/*      */           
/* 5217 */           if ((node = advance()) == null) {
/* 5218 */             propagateCompletion();
/*      */             break;
/*      */           } 
/* 5221 */           if ((i = function.apply(node.val)) != null) {
/* 5222 */             if (atomicReference.compareAndSet(null, i)) {
/* 5223 */               quietlyCompleteRoot();
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchEntriesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final Function<Map.Entry<K, V>, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchEntriesTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, Function<Map.Entry<K, V>, ? extends U> param1Function, AtomicReference<U> param1AtomicReference) {
/* 5240 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5241 */       this.searchFunction = param1Function; this.result = param1AtomicReference;
/*      */     } public final U getRawResult() {
/* 5243 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       Function<Map.Entry<K, V>, ? extends U> function;
/*      */       AtomicReference<U> atomicReference;
/* 5247 */       if ((function = this.searchFunction) != null && (atomicReference = this.result) != null) {
/*      */         int i; int j; int k;
/* 5249 */         for (i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5251 */           if (atomicReference.get() != null)
/*      */             return; 
/* 5253 */           addToPendingCount(1);
/* 5254 */           (new SearchEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, function, atomicReference))
/*      */             
/* 5256 */             .fork();
/*      */         } 
/* 5258 */         while (atomicReference.get() == null) {
/*      */           ConcurrentHashMap.Node<K, V> node;
/*      */           
/* 5261 */           if ((node = advance()) == null) {
/* 5262 */             propagateCompletion();
/*      */             break;
/*      */           } 
/* 5265 */           if ((i = function.apply(node)) != null) {
/* 5266 */             if (atomicReference.compareAndSet(null, i)) {
/* 5267 */               quietlyCompleteRoot();
/*      */             }
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchMappingsTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final BiFunction<? super K, ? super V, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchMappingsTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, BiFunction<? super K, ? super V, ? extends U> param1BiFunction, AtomicReference<U> param1AtomicReference) {
/* 5284 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode);
/* 5285 */       this.searchFunction = param1BiFunction; this.result = param1AtomicReference;
/*      */     } public final U getRawResult() {
/* 5287 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       BiFunction<? super K, ? super V, ? extends U> biFunction;
/*      */       AtomicReference<U> atomicReference;
/* 5291 */       if ((biFunction = this.searchFunction) != null && (atomicReference = this.result) != null) {
/*      */         int i; int j; int k;
/* 5293 */         for (i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5295 */           if (atomicReference.get() != null)
/*      */             return; 
/* 5297 */           addToPendingCount(1);
/* 5298 */           (new SearchMappingsTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, biFunction, atomicReference))
/*      */             
/* 5300 */             .fork();
/*      */         } 
/* 5302 */         while (atomicReference.get() == null) {
/*      */           ConcurrentHashMap.Node<K, V> node;
/*      */           
/* 5305 */           if ((node = advance()) == null) {
/* 5306 */             propagateCompletion();
/*      */             break;
/*      */           } 
/* 5309 */           if ((i = biFunction.apply(node.key, node.val)) != null) {
/* 5310 */             if (atomicReference.compareAndSet(null, i)) {
/* 5311 */               quietlyCompleteRoot();
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReduceKeysTask<K, V>
/*      */     extends BulkTask<K, V, K>
/*      */   {
/*      */     final BiFunction<? super K, ? super K, ? extends K> reducer;
/*      */     K result;
/*      */     ReduceKeysTask<K, V> rights;
/*      */     ReduceKeysTask<K, V> nextRight;
/*      */     
/*      */     ReduceKeysTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, ReduceKeysTask<K, V> param1ReduceKeysTask, BiFunction<? super K, ? super K, ? extends K> param1BiFunction) {
/* 5329 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1ReduceKeysTask;
/* 5330 */       this.reducer = param1BiFunction;
/*      */     } public final K getRawResult() {
/* 5332 */       return this.result;
/*      */     } public final void compute() {
/*      */       BiFunction<? super K, ? super K, ? extends K> biFunction;
/* 5335 */       if ((biFunction = this.reducer) != null) {
/* 5336 */         int j, m; for (int i = this.baseIndex; this.batch > 0 && (m = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5338 */           addToPendingCount(1);
/* 5339 */           (this.rights = new ReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = m, j, this.tab, this.rights, biFunction))
/*      */             
/* 5341 */             .fork();
/*      */         } 
/* 5343 */         K k = null; ConcurrentHashMap.Node<K, V> node;
/* 5344 */         while ((node = advance()) != null) {
/* 5345 */           K k1 = node.key;
/* 5346 */           k = (K)((k == null) ? (Object)k1 : ((k1 == null) ? (Object)k : (Object)biFunction.apply(k, k1)));
/*      */         } 
/* 5348 */         this.result = k;
/*      */         
/* 5350 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5353 */           ReduceKeysTask reduceKeysTask = (ReduceKeysTask)countedCompleter;
/* 5354 */           ReduceKeysTask<K, V> reduceKeysTask1 = reduceKeysTask.rights;
/* 5355 */           while (reduceKeysTask1 != null) {
/*      */             K k1;
/* 5357 */             if ((k1 = reduceKeysTask1.result) != null) {
/* 5358 */               K k2; reduceKeysTask
/* 5359 */                 .result = ((k2 = reduceKeysTask.result) == null) ? k1 : biFunction.apply(k2, k1);
/* 5360 */             }  reduceKeysTask1 = reduceKeysTask.rights = reduceKeysTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ReduceValuesTask<K, V>
/*      */     extends BulkTask<K, V, V>
/*      */   {
/*      */     final BiFunction<? super V, ? super V, ? extends V> reducer;
/*      */     V result;
/*      */     ReduceValuesTask<K, V> rights;
/*      */     ReduceValuesTask<K, V> nextRight;
/*      */     
/*      */     ReduceValuesTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, ReduceValuesTask<K, V> param1ReduceValuesTask, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 5377 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1ReduceValuesTask;
/* 5378 */       this.reducer = param1BiFunction;
/*      */     } public final V getRawResult() {
/* 5380 */       return this.result;
/*      */     } public final void compute() {
/*      */       BiFunction<? super V, ? super V, ? extends V> biFunction;
/* 5383 */       if ((biFunction = this.reducer) != null) {
/* 5384 */         int j, k; for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5386 */           addToPendingCount(1);
/* 5387 */           (this.rights = new ReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, biFunction))
/*      */             
/* 5389 */             .fork();
/*      */         } 
/* 5391 */         V v = null; ConcurrentHashMap.Node<K, V> node;
/* 5392 */         while ((node = advance()) != null) {
/* 5393 */           V v1 = node.val;
/* 5394 */           v = (V)((v == null) ? (Object)v1 : (Object)biFunction.apply(v, v1));
/*      */         } 
/* 5396 */         this.result = v;
/*      */         
/* 5398 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5401 */           ReduceValuesTask reduceValuesTask = (ReduceValuesTask)countedCompleter;
/* 5402 */           ReduceValuesTask<K, V> reduceValuesTask1 = reduceValuesTask.rights;
/* 5403 */           while (reduceValuesTask1 != null) {
/*      */             V v1;
/* 5405 */             if ((v1 = reduceValuesTask1.result) != null) {
/* 5406 */               V v2; reduceValuesTask
/* 5407 */                 .result = ((v2 = reduceValuesTask.result) == null) ? v1 : biFunction.apply(v2, v1);
/* 5408 */             }  reduceValuesTask1 = reduceValuesTask.rights = reduceValuesTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ReduceEntriesTask<K, V>
/*      */     extends BulkTask<K, V, Map.Entry<K, V>>
/*      */   {
/*      */     final BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
/*      */     Map.Entry<K, V> result;
/*      */     ReduceEntriesTask<K, V> rights;
/*      */     ReduceEntriesTask<K, V> nextRight;
/*      */     
/*      */     ReduceEntriesTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, ReduceEntriesTask<K, V> param1ReduceEntriesTask, BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> param1BiFunction) {
/* 5425 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1ReduceEntriesTask;
/* 5426 */       this.reducer = param1BiFunction;
/*      */     } public final Map.Entry<K, V> getRawResult() {
/* 5428 */       return this.result;
/*      */     } public final void compute() {
/*      */       BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> biFunction;
/* 5431 */       if ((biFunction = this.reducer) != null) {
/* 5432 */         int j, k; for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5434 */           addToPendingCount(1);
/* 5435 */           (this.rights = new ReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, biFunction))
/*      */             
/* 5437 */             .fork();
/*      */         } 
/* 5439 */         Map.Entry<K, V> entry = null; ConcurrentHashMap.Node<K, V> node;
/* 5440 */         while ((node = advance()) != null)
/* 5441 */           entry = (entry == null) ? node : biFunction.apply(entry, node); 
/* 5442 */         this.result = entry;
/*      */         
/* 5444 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5447 */           ReduceEntriesTask reduceEntriesTask = (ReduceEntriesTask)countedCompleter;
/* 5448 */           ReduceEntriesTask<K, V> reduceEntriesTask1 = reduceEntriesTask.rights;
/* 5449 */           while (reduceEntriesTask1 != null) {
/*      */             Map.Entry<K, V> entry1;
/* 5451 */             if ((entry1 = reduceEntriesTask1.result) != null) {
/* 5452 */               Map.Entry<K, V> entry2; reduceEntriesTask
/* 5453 */                 .result = ((entry2 = reduceEntriesTask.result) == null) ? entry1 : biFunction.apply(entry2, entry1);
/* 5454 */             }  reduceEntriesTask1 = reduceEntriesTask.rights = reduceEntriesTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super K, ? extends U> transformer;
/*      */     
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceKeysTask<K, V, U> rights;
/*      */     MapReduceKeysTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceKeysTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceKeysTask<K, V, U> param1MapReduceKeysTask, Function<? super K, ? extends U> param1Function, BiFunction<? super U, ? super U, ? extends U> param1BiFunction) {
/* 5473 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceKeysTask;
/* 5474 */       this.transformer = param1Function;
/* 5475 */       this.reducer = param1BiFunction;
/*      */     } public final U getRawResult() {
/* 5477 */       return this.result;
/*      */     } public final void compute() {
/*      */       Function<? super K, ? extends U> function;
/*      */       BiFunction<? super U, ? super U, ? extends U> biFunction;
/* 5481 */       if ((function = this.transformer) != null && (biFunction = this.reducer) != null) {
/*      */         int j, k;
/* 5483 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5485 */           addToPendingCount(1);
/* 5486 */           (this.rights = new MapReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, function, biFunction))
/*      */             
/* 5488 */             .fork();
/*      */         } 
/* 5490 */         U u = null; ConcurrentHashMap.Node<K, V> node;
/* 5491 */         while ((node = advance()) != null) {
/*      */           
/* 5493 */           if ((k = function.apply(node.key)) != null)
/* 5494 */             u = (U)((u == null) ? k : (Object)biFunction.apply(u, k)); 
/*      */         } 
/* 5496 */         this.result = u;
/*      */         
/* 5498 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5501 */           MapReduceKeysTask mapReduceKeysTask = (MapReduceKeysTask)countedCompleter;
/* 5502 */           MapReduceKeysTask<K, V, U> mapReduceKeysTask1 = mapReduceKeysTask.rights;
/* 5503 */           while (mapReduceKeysTask1 != null) {
/*      */             U u1;
/* 5505 */             if ((u1 = mapReduceKeysTask1.result) != null) {
/* 5506 */               U u2; mapReduceKeysTask
/* 5507 */                 .result = ((u2 = mapReduceKeysTask.result) == null) ? u1 : biFunction.apply(u2, u1);
/* 5508 */             }  mapReduceKeysTask1 = mapReduceKeysTask.rights = mapReduceKeysTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super V, ? extends U> transformer;
/*      */     
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceValuesTask<K, V, U> rights;
/*      */     MapReduceValuesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceValuesTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceValuesTask<K, V, U> param1MapReduceValuesTask, Function<? super V, ? extends U> param1Function, BiFunction<? super U, ? super U, ? extends U> param1BiFunction) {
/* 5527 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceValuesTask;
/* 5528 */       this.transformer = param1Function;
/* 5529 */       this.reducer = param1BiFunction;
/*      */     } public final U getRawResult() {
/* 5531 */       return this.result;
/*      */     } public final void compute() {
/*      */       Function<? super V, ? extends U> function;
/*      */       BiFunction<? super U, ? super U, ? extends U> biFunction;
/* 5535 */       if ((function = this.transformer) != null && (biFunction = this.reducer) != null) {
/*      */         int j, k;
/* 5537 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5539 */           addToPendingCount(1);
/* 5540 */           (this.rights = new MapReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, function, biFunction))
/*      */             
/* 5542 */             .fork();
/*      */         } 
/* 5544 */         U u = null; ConcurrentHashMap.Node<K, V> node;
/* 5545 */         while ((node = advance()) != null) {
/*      */           
/* 5547 */           if ((k = function.apply(node.val)) != null)
/* 5548 */             u = (U)((u == null) ? k : (Object)biFunction.apply(u, k)); 
/*      */         } 
/* 5550 */         this.result = u;
/*      */         
/* 5552 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5555 */           MapReduceValuesTask mapReduceValuesTask = (MapReduceValuesTask)countedCompleter;
/* 5556 */           MapReduceValuesTask<K, V, U> mapReduceValuesTask1 = mapReduceValuesTask.rights;
/* 5557 */           while (mapReduceValuesTask1 != null) {
/*      */             U u1;
/* 5559 */             if ((u1 = mapReduceValuesTask1.result) != null) {
/* 5560 */               U u2; mapReduceValuesTask
/* 5561 */                 .result = ((u2 = mapReduceValuesTask.result) == null) ? u1 : biFunction.apply(u2, u1);
/* 5562 */             }  mapReduceValuesTask1 = mapReduceValuesTask.rights = mapReduceValuesTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final Function<Map.Entry<K, V>, ? extends U> transformer;
/*      */     
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceEntriesTask<K, V, U> rights;
/*      */     MapReduceEntriesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceEntriesTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceEntriesTask<K, V, U> param1MapReduceEntriesTask, Function<Map.Entry<K, V>, ? extends U> param1Function, BiFunction<? super U, ? super U, ? extends U> param1BiFunction) {
/* 5581 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceEntriesTask;
/* 5582 */       this.transformer = param1Function;
/* 5583 */       this.reducer = param1BiFunction;
/*      */     } public final U getRawResult() {
/* 5585 */       return this.result;
/*      */     } public final void compute() {
/*      */       Function<Map.Entry<K, V>, ? extends U> function;
/*      */       BiFunction<? super U, ? super U, ? extends U> biFunction;
/* 5589 */       if ((function = this.transformer) != null && (biFunction = this.reducer) != null) {
/*      */         int j, k;
/* 5591 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5593 */           addToPendingCount(1);
/* 5594 */           (this.rights = new MapReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, function, biFunction))
/*      */             
/* 5596 */             .fork();
/*      */         } 
/* 5598 */         U u = null; ConcurrentHashMap.Node<K, V> node;
/* 5599 */         while ((node = advance()) != null) {
/*      */           
/* 5601 */           if ((k = function.apply(node)) != null)
/* 5602 */             u = (U)((u == null) ? k : (Object)biFunction.apply(u, k)); 
/*      */         } 
/* 5604 */         this.result = u;
/*      */         
/* 5606 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5609 */           MapReduceEntriesTask mapReduceEntriesTask = (MapReduceEntriesTask)countedCompleter;
/* 5610 */           MapReduceEntriesTask<K, V, U> mapReduceEntriesTask1 = mapReduceEntriesTask.rights;
/* 5611 */           while (mapReduceEntriesTask1 != null) {
/*      */             U u1;
/* 5613 */             if ((u1 = mapReduceEntriesTask1.result) != null) {
/* 5614 */               U u2; mapReduceEntriesTask
/* 5615 */                 .result = ((u2 = mapReduceEntriesTask.result) == null) ? u1 : biFunction.apply(u2, u1);
/* 5616 */             }  mapReduceEntriesTask1 = mapReduceEntriesTask.rights = mapReduceEntriesTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final BiFunction<? super K, ? super V, ? extends U> transformer;
/*      */     
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceMappingsTask<K, V, U> rights;
/*      */     MapReduceMappingsTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceMappingsTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceMappingsTask<K, V, U> param1MapReduceMappingsTask, BiFunction<? super K, ? super V, ? extends U> param1BiFunction, BiFunction<? super U, ? super U, ? extends U> param1BiFunction1) {
/* 5635 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceMappingsTask;
/* 5636 */       this.transformer = param1BiFunction;
/* 5637 */       this.reducer = param1BiFunction1;
/*      */     } public final U getRawResult() {
/* 5639 */       return this.result;
/*      */     } public final void compute() {
/*      */       BiFunction<? super K, ? super V, ? extends U> biFunction;
/*      */       BiFunction<? super U, ? super U, ? extends U> biFunction1;
/* 5643 */       if ((biFunction = this.transformer) != null && (biFunction1 = this.reducer) != null) {
/*      */         int j, k;
/* 5645 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5647 */           addToPendingCount(1);
/* 5648 */           (this.rights = new MapReduceMappingsTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, biFunction, biFunction1))
/*      */             
/* 5650 */             .fork();
/*      */         } 
/* 5652 */         U u = null; ConcurrentHashMap.Node<K, V> node;
/* 5653 */         while ((node = advance()) != null) {
/*      */           
/* 5655 */           if ((k = biFunction.apply(node.key, node.val)) != null)
/* 5656 */             u = (U)((u == null) ? k : (Object)biFunction1.apply(u, k)); 
/*      */         } 
/* 5658 */         this.result = u;
/*      */         
/* 5660 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5663 */           MapReduceMappingsTask mapReduceMappingsTask = (MapReduceMappingsTask)countedCompleter;
/* 5664 */           MapReduceMappingsTask<K, V, U> mapReduceMappingsTask1 = mapReduceMappingsTask.rights;
/* 5665 */           while (mapReduceMappingsTask1 != null) {
/*      */             U u1;
/* 5667 */             if ((u1 = mapReduceMappingsTask1.result) != null) {
/* 5668 */               U u2; mapReduceMappingsTask
/* 5669 */                 .result = ((u2 = mapReduceMappingsTask.result) == null) ? u1 : biFunction1.apply(u2, u1);
/* 5670 */             }  mapReduceMappingsTask1 = mapReduceMappingsTask.rights = mapReduceMappingsTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleFunction<? super K> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceKeysToDoubleTask<K, V> rights;
/*      */     MapReduceKeysToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceKeysToDoubleTask<K, V> param1MapReduceKeysToDoubleTask, ToDoubleFunction<? super K> param1ToDoubleFunction, double param1Double, DoubleBinaryOperator param1DoubleBinaryOperator) {
/* 5691 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceKeysToDoubleTask;
/* 5692 */       this.transformer = param1ToDoubleFunction;
/* 5693 */       this.basis = param1Double; this.reducer = param1DoubleBinaryOperator;
/*      */     } public final Double getRawResult() {
/* 5695 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToDoubleFunction<? super K> toDoubleFunction;
/*      */       DoubleBinaryOperator doubleBinaryOperator;
/* 5699 */       if ((toDoubleFunction = this.transformer) != null && (doubleBinaryOperator = this.reducer) != null) {
/*      */         
/* 5701 */         double d = this.basis; int j, k;
/* 5702 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5704 */           addToPendingCount(1);
/* 5705 */           (this.rights = new MapReduceKeysToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toDoubleFunction, d, doubleBinaryOperator))
/*      */             
/* 5707 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5709 */         while ((node = advance()) != null)
/* 5710 */           d = doubleBinaryOperator.applyAsDouble(d, toDoubleFunction.applyAsDouble(node.key)); 
/* 5711 */         this.result = d;
/*      */         
/* 5713 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5716 */           MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask = (MapReduceKeysToDoubleTask)countedCompleter;
/* 5717 */           MapReduceKeysToDoubleTask<K, V> mapReduceKeysToDoubleTask1 = mapReduceKeysToDoubleTask.rights;
/* 5718 */           while (mapReduceKeysToDoubleTask1 != null) {
/* 5719 */             mapReduceKeysToDoubleTask.result = doubleBinaryOperator.applyAsDouble(mapReduceKeysToDoubleTask.result, mapReduceKeysToDoubleTask1.result);
/* 5720 */             mapReduceKeysToDoubleTask1 = mapReduceKeysToDoubleTask.rights = mapReduceKeysToDoubleTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleFunction<? super V> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceValuesToDoubleTask<K, V> rights;
/*      */     MapReduceValuesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceValuesToDoubleTask<K, V> param1MapReduceValuesToDoubleTask, ToDoubleFunction<? super V> param1ToDoubleFunction, double param1Double, DoubleBinaryOperator param1DoubleBinaryOperator) {
/* 5741 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceValuesToDoubleTask;
/* 5742 */       this.transformer = param1ToDoubleFunction;
/* 5743 */       this.basis = param1Double; this.reducer = param1DoubleBinaryOperator;
/*      */     } public final Double getRawResult() {
/* 5745 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToDoubleFunction<? super V> toDoubleFunction;
/*      */       DoubleBinaryOperator doubleBinaryOperator;
/* 5749 */       if ((toDoubleFunction = this.transformer) != null && (doubleBinaryOperator = this.reducer) != null) {
/*      */         
/* 5751 */         double d = this.basis; int j, k;
/* 5752 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5754 */           addToPendingCount(1);
/* 5755 */           (this.rights = new MapReduceValuesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toDoubleFunction, d, doubleBinaryOperator))
/*      */             
/* 5757 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5759 */         while ((node = advance()) != null)
/* 5760 */           d = doubleBinaryOperator.applyAsDouble(d, toDoubleFunction.applyAsDouble(node.val)); 
/* 5761 */         this.result = d;
/*      */         
/* 5763 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5766 */           MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask = (MapReduceValuesToDoubleTask)countedCompleter;
/* 5767 */           MapReduceValuesToDoubleTask<K, V> mapReduceValuesToDoubleTask1 = mapReduceValuesToDoubleTask.rights;
/* 5768 */           while (mapReduceValuesToDoubleTask1 != null) {
/* 5769 */             mapReduceValuesToDoubleTask.result = doubleBinaryOperator.applyAsDouble(mapReduceValuesToDoubleTask.result, mapReduceValuesToDoubleTask1.result);
/* 5770 */             mapReduceValuesToDoubleTask1 = mapReduceValuesToDoubleTask.rights = mapReduceValuesToDoubleTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleFunction<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceEntriesToDoubleTask<K, V> rights;
/*      */     MapReduceEntriesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceEntriesToDoubleTask<K, V> param1MapReduceEntriesToDoubleTask, ToDoubleFunction<Map.Entry<K, V>> param1ToDoubleFunction, double param1Double, DoubleBinaryOperator param1DoubleBinaryOperator) {
/* 5791 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceEntriesToDoubleTask;
/* 5792 */       this.transformer = param1ToDoubleFunction;
/* 5793 */       this.basis = param1Double; this.reducer = param1DoubleBinaryOperator;
/*      */     } public final Double getRawResult() {
/* 5795 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToDoubleFunction<Map.Entry<K, V>> toDoubleFunction;
/*      */       DoubleBinaryOperator doubleBinaryOperator;
/* 5799 */       if ((toDoubleFunction = this.transformer) != null && (doubleBinaryOperator = this.reducer) != null) {
/*      */         
/* 5801 */         double d = this.basis; int j, k;
/* 5802 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5804 */           addToPendingCount(1);
/* 5805 */           (this.rights = new MapReduceEntriesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toDoubleFunction, d, doubleBinaryOperator))
/*      */             
/* 5807 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5809 */         while ((node = advance()) != null)
/* 5810 */           d = doubleBinaryOperator.applyAsDouble(d, toDoubleFunction.applyAsDouble(node)); 
/* 5811 */         this.result = d;
/*      */         
/* 5813 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5816 */           MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask = (MapReduceEntriesToDoubleTask)countedCompleter;
/* 5817 */           MapReduceEntriesToDoubleTask<K, V> mapReduceEntriesToDoubleTask1 = mapReduceEntriesToDoubleTask.rights;
/* 5818 */           while (mapReduceEntriesToDoubleTask1 != null) {
/* 5819 */             mapReduceEntriesToDoubleTask.result = doubleBinaryOperator.applyAsDouble(mapReduceEntriesToDoubleTask.result, mapReduceEntriesToDoubleTask1.result);
/* 5820 */             mapReduceEntriesToDoubleTask1 = mapReduceEntriesToDoubleTask.rights = mapReduceEntriesToDoubleTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleBiFunction<? super K, ? super V> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceMappingsToDoubleTask<K, V> rights;
/*      */     MapReduceMappingsToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceMappingsToDoubleTask<K, V> param1MapReduceMappingsToDoubleTask, ToDoubleBiFunction<? super K, ? super V> param1ToDoubleBiFunction, double param1Double, DoubleBinaryOperator param1DoubleBinaryOperator) {
/* 5841 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceMappingsToDoubleTask;
/* 5842 */       this.transformer = param1ToDoubleBiFunction;
/* 5843 */       this.basis = param1Double; this.reducer = param1DoubleBinaryOperator;
/*      */     } public final Double getRawResult() {
/* 5845 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToDoubleBiFunction<? super K, ? super V> toDoubleBiFunction;
/*      */       DoubleBinaryOperator doubleBinaryOperator;
/* 5849 */       if ((toDoubleBiFunction = this.transformer) != null && (doubleBinaryOperator = this.reducer) != null) {
/*      */         
/* 5851 */         double d = this.basis; int j, k;
/* 5852 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5854 */           addToPendingCount(1);
/* 5855 */           (this.rights = new MapReduceMappingsToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toDoubleBiFunction, d, doubleBinaryOperator))
/*      */             
/* 5857 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5859 */         while ((node = advance()) != null)
/* 5860 */           d = doubleBinaryOperator.applyAsDouble(d, toDoubleBiFunction.applyAsDouble(node.key, node.val)); 
/* 5861 */         this.result = d;
/*      */         
/* 5863 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5866 */           MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask = (MapReduceMappingsToDoubleTask)countedCompleter;
/* 5867 */           MapReduceMappingsToDoubleTask<K, V> mapReduceMappingsToDoubleTask1 = mapReduceMappingsToDoubleTask.rights;
/* 5868 */           while (mapReduceMappingsToDoubleTask1 != null) {
/* 5869 */             mapReduceMappingsToDoubleTask.result = doubleBinaryOperator.applyAsDouble(mapReduceMappingsToDoubleTask.result, mapReduceMappingsToDoubleTask1.result);
/* 5870 */             mapReduceMappingsToDoubleTask1 = mapReduceMappingsToDoubleTask.rights = mapReduceMappingsToDoubleTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongFunction<? super K> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceKeysToLongTask<K, V> rights;
/*      */     MapReduceKeysToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceKeysToLongTask<K, V> param1MapReduceKeysToLongTask, ToLongFunction<? super K> param1ToLongFunction, long param1Long, LongBinaryOperator param1LongBinaryOperator) {
/* 5891 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceKeysToLongTask;
/* 5892 */       this.transformer = param1ToLongFunction;
/* 5893 */       this.basis = param1Long; this.reducer = param1LongBinaryOperator;
/*      */     } public final Long getRawResult() {
/* 5895 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToLongFunction<? super K> toLongFunction;
/*      */       LongBinaryOperator longBinaryOperator;
/* 5899 */       if ((toLongFunction = this.transformer) != null && (longBinaryOperator = this.reducer) != null) {
/*      */         
/* 5901 */         long l = this.basis; int j, k;
/* 5902 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5904 */           addToPendingCount(1);
/* 5905 */           (this.rights = new MapReduceKeysToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toLongFunction, l, longBinaryOperator))
/*      */             
/* 5907 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5909 */         while ((node = advance()) != null)
/* 5910 */           l = longBinaryOperator.applyAsLong(l, toLongFunction.applyAsLong(node.key)); 
/* 5911 */         this.result = l;
/*      */         
/* 5913 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5916 */           MapReduceKeysToLongTask mapReduceKeysToLongTask = (MapReduceKeysToLongTask)countedCompleter;
/* 5917 */           MapReduceKeysToLongTask<K, V> mapReduceKeysToLongTask1 = mapReduceKeysToLongTask.rights;
/* 5918 */           while (mapReduceKeysToLongTask1 != null) {
/* 5919 */             mapReduceKeysToLongTask.result = longBinaryOperator.applyAsLong(mapReduceKeysToLongTask.result, mapReduceKeysToLongTask1.result);
/* 5920 */             mapReduceKeysToLongTask1 = mapReduceKeysToLongTask.rights = mapReduceKeysToLongTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongFunction<? super V> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceValuesToLongTask<K, V> rights;
/*      */     MapReduceValuesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceValuesToLongTask<K, V> param1MapReduceValuesToLongTask, ToLongFunction<? super V> param1ToLongFunction, long param1Long, LongBinaryOperator param1LongBinaryOperator) {
/* 5941 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceValuesToLongTask;
/* 5942 */       this.transformer = param1ToLongFunction;
/* 5943 */       this.basis = param1Long; this.reducer = param1LongBinaryOperator;
/*      */     } public final Long getRawResult() {
/* 5945 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToLongFunction<? super V> toLongFunction;
/*      */       LongBinaryOperator longBinaryOperator;
/* 5949 */       if ((toLongFunction = this.transformer) != null && (longBinaryOperator = this.reducer) != null) {
/*      */         
/* 5951 */         long l = this.basis; int j, k;
/* 5952 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5954 */           addToPendingCount(1);
/* 5955 */           (this.rights = new MapReduceValuesToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toLongFunction, l, longBinaryOperator))
/*      */             
/* 5957 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 5959 */         while ((node = advance()) != null)
/* 5960 */           l = longBinaryOperator.applyAsLong(l, toLongFunction.applyAsLong(node.val)); 
/* 5961 */         this.result = l;
/*      */         
/* 5963 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 5966 */           MapReduceValuesToLongTask mapReduceValuesToLongTask = (MapReduceValuesToLongTask)countedCompleter;
/* 5967 */           MapReduceValuesToLongTask<K, V> mapReduceValuesToLongTask1 = mapReduceValuesToLongTask.rights;
/* 5968 */           while (mapReduceValuesToLongTask1 != null) {
/* 5969 */             mapReduceValuesToLongTask.result = longBinaryOperator.applyAsLong(mapReduceValuesToLongTask.result, mapReduceValuesToLongTask1.result);
/* 5970 */             mapReduceValuesToLongTask1 = mapReduceValuesToLongTask.rights = mapReduceValuesToLongTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongFunction<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceEntriesToLongTask<K, V> rights;
/*      */     MapReduceEntriesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceEntriesToLongTask<K, V> param1MapReduceEntriesToLongTask, ToLongFunction<Map.Entry<K, V>> param1ToLongFunction, long param1Long, LongBinaryOperator param1LongBinaryOperator) {
/* 5991 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceEntriesToLongTask;
/* 5992 */       this.transformer = param1ToLongFunction;
/* 5993 */       this.basis = param1Long; this.reducer = param1LongBinaryOperator;
/*      */     } public final Long getRawResult() {
/* 5995 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToLongFunction<Map.Entry<K, V>> toLongFunction;
/*      */       LongBinaryOperator longBinaryOperator;
/* 5999 */       if ((toLongFunction = this.transformer) != null && (longBinaryOperator = this.reducer) != null) {
/*      */         
/* 6001 */         long l = this.basis; int j, k;
/* 6002 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 6004 */           addToPendingCount(1);
/* 6005 */           (this.rights = new MapReduceEntriesToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toLongFunction, l, longBinaryOperator))
/*      */             
/* 6007 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 6009 */         while ((node = advance()) != null)
/* 6010 */           l = longBinaryOperator.applyAsLong(l, toLongFunction.applyAsLong(node)); 
/* 6011 */         this.result = l;
/*      */         
/* 6013 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 6016 */           MapReduceEntriesToLongTask mapReduceEntriesToLongTask = (MapReduceEntriesToLongTask)countedCompleter;
/* 6017 */           MapReduceEntriesToLongTask<K, V> mapReduceEntriesToLongTask1 = mapReduceEntriesToLongTask.rights;
/* 6018 */           while (mapReduceEntriesToLongTask1 != null) {
/* 6019 */             mapReduceEntriesToLongTask.result = longBinaryOperator.applyAsLong(mapReduceEntriesToLongTask.result, mapReduceEntriesToLongTask1.result);
/* 6020 */             mapReduceEntriesToLongTask1 = mapReduceEntriesToLongTask.rights = mapReduceEntriesToLongTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongBiFunction<? super K, ? super V> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceMappingsToLongTask<K, V> rights;
/*      */     MapReduceMappingsToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceMappingsToLongTask<K, V> param1MapReduceMappingsToLongTask, ToLongBiFunction<? super K, ? super V> param1ToLongBiFunction, long param1Long, LongBinaryOperator param1LongBinaryOperator) {
/* 6041 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceMappingsToLongTask;
/* 6042 */       this.transformer = param1ToLongBiFunction;
/* 6043 */       this.basis = param1Long; this.reducer = param1LongBinaryOperator;
/*      */     } public final Long getRawResult() {
/* 6045 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToLongBiFunction<? super K, ? super V> toLongBiFunction;
/*      */       LongBinaryOperator longBinaryOperator;
/* 6049 */       if ((toLongBiFunction = this.transformer) != null && (longBinaryOperator = this.reducer) != null) {
/*      */         
/* 6051 */         long l = this.basis; int j, k;
/* 6052 */         for (int i = this.baseIndex; this.batch > 0 && (k = (j = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 6054 */           addToPendingCount(1);
/* 6055 */           (this.rights = new MapReduceMappingsToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, toLongBiFunction, l, longBinaryOperator))
/*      */             
/* 6057 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 6059 */         while ((node = advance()) != null)
/* 6060 */           l = longBinaryOperator.applyAsLong(l, toLongBiFunction.applyAsLong(node.key, node.val)); 
/* 6061 */         this.result = l;
/*      */         
/* 6063 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 6066 */           MapReduceMappingsToLongTask mapReduceMappingsToLongTask = (MapReduceMappingsToLongTask)countedCompleter;
/* 6067 */           MapReduceMappingsToLongTask<K, V> mapReduceMappingsToLongTask1 = mapReduceMappingsToLongTask.rights;
/* 6068 */           while (mapReduceMappingsToLongTask1 != null) {
/* 6069 */             mapReduceMappingsToLongTask.result = longBinaryOperator.applyAsLong(mapReduceMappingsToLongTask.result, mapReduceMappingsToLongTask1.result);
/* 6070 */             mapReduceMappingsToLongTask1 = mapReduceMappingsToLongTask.rights = mapReduceMappingsToLongTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntFunction<? super K> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceKeysToIntTask<K, V> rights;
/*      */     MapReduceKeysToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceKeysToIntTask<K, V> param1MapReduceKeysToIntTask, ToIntFunction<? super K> param1ToIntFunction, int param1Int4, IntBinaryOperator param1IntBinaryOperator) {
/* 6091 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceKeysToIntTask;
/* 6092 */       this.transformer = param1ToIntFunction;
/* 6093 */       this.basis = param1Int4; this.reducer = param1IntBinaryOperator;
/*      */     } public final Integer getRawResult() {
/* 6095 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToIntFunction<? super K> toIntFunction;
/*      */       IntBinaryOperator intBinaryOperator;
/* 6099 */       if ((toIntFunction = this.transformer) != null && (intBinaryOperator = this.reducer) != null) {
/*      */         
/* 6101 */         int i = this.basis; int k, m;
/* 6102 */         for (int j = this.baseIndex; this.batch > 0 && (m = (k = this.baseLimit) + j >>> 1) > j; ) {
/*      */           
/* 6104 */           addToPendingCount(1);
/* 6105 */           (this.rights = new MapReduceKeysToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, toIntFunction, i, intBinaryOperator))
/*      */             
/* 6107 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 6109 */         while ((node = advance()) != null)
/* 6110 */           i = intBinaryOperator.applyAsInt(i, toIntFunction.applyAsInt(node.key)); 
/* 6111 */         this.result = i;
/*      */         
/* 6113 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 6116 */           MapReduceKeysToIntTask mapReduceKeysToIntTask = (MapReduceKeysToIntTask)countedCompleter;
/* 6117 */           MapReduceKeysToIntTask<K, V> mapReduceKeysToIntTask1 = mapReduceKeysToIntTask.rights;
/* 6118 */           while (mapReduceKeysToIntTask1 != null) {
/* 6119 */             mapReduceKeysToIntTask.result = intBinaryOperator.applyAsInt(mapReduceKeysToIntTask.result, mapReduceKeysToIntTask1.result);
/* 6120 */             mapReduceKeysToIntTask1 = mapReduceKeysToIntTask.rights = mapReduceKeysToIntTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntFunction<? super V> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceValuesToIntTask<K, V> rights;
/*      */     MapReduceValuesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceValuesToIntTask<K, V> param1MapReduceValuesToIntTask, ToIntFunction<? super V> param1ToIntFunction, int param1Int4, IntBinaryOperator param1IntBinaryOperator) {
/* 6141 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceValuesToIntTask;
/* 6142 */       this.transformer = param1ToIntFunction;
/* 6143 */       this.basis = param1Int4; this.reducer = param1IntBinaryOperator;
/*      */     } public final Integer getRawResult() {
/* 6145 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToIntFunction<? super V> toIntFunction;
/*      */       IntBinaryOperator intBinaryOperator;
/* 6149 */       if ((toIntFunction = this.transformer) != null && (intBinaryOperator = this.reducer) != null) {
/*      */         
/* 6151 */         int i = this.basis; int k, m;
/* 6152 */         for (int j = this.baseIndex; this.batch > 0 && (m = (k = this.baseLimit) + j >>> 1) > j; ) {
/*      */           
/* 6154 */           addToPendingCount(1);
/* 6155 */           (this.rights = new MapReduceValuesToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, toIntFunction, i, intBinaryOperator))
/*      */             
/* 6157 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 6159 */         while ((node = advance()) != null)
/* 6160 */           i = intBinaryOperator.applyAsInt(i, toIntFunction.applyAsInt(node.val)); 
/* 6161 */         this.result = i;
/*      */         
/* 6163 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 6166 */           MapReduceValuesToIntTask mapReduceValuesToIntTask = (MapReduceValuesToIntTask)countedCompleter;
/* 6167 */           MapReduceValuesToIntTask<K, V> mapReduceValuesToIntTask1 = mapReduceValuesToIntTask.rights;
/* 6168 */           while (mapReduceValuesToIntTask1 != null) {
/* 6169 */             mapReduceValuesToIntTask.result = intBinaryOperator.applyAsInt(mapReduceValuesToIntTask.result, mapReduceValuesToIntTask1.result);
/* 6170 */             mapReduceValuesToIntTask1 = mapReduceValuesToIntTask.rights = mapReduceValuesToIntTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntFunction<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceEntriesToIntTask<K, V> rights;
/*      */     MapReduceEntriesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceEntriesToIntTask<K, V> param1MapReduceEntriesToIntTask, ToIntFunction<Map.Entry<K, V>> param1ToIntFunction, int param1Int4, IntBinaryOperator param1IntBinaryOperator) {
/* 6191 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceEntriesToIntTask;
/* 6192 */       this.transformer = param1ToIntFunction;
/* 6193 */       this.basis = param1Int4; this.reducer = param1IntBinaryOperator;
/*      */     } public final Integer getRawResult() {
/* 6195 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToIntFunction<Map.Entry<K, V>> toIntFunction;
/*      */       IntBinaryOperator intBinaryOperator;
/* 6199 */       if ((toIntFunction = this.transformer) != null && (intBinaryOperator = this.reducer) != null) {
/*      */         
/* 6201 */         int i = this.basis; int k, m;
/* 6202 */         for (int j = this.baseIndex; this.batch > 0 && (m = (k = this.baseLimit) + j >>> 1) > j; ) {
/*      */           
/* 6204 */           addToPendingCount(1);
/* 6205 */           (this.rights = new MapReduceEntriesToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, toIntFunction, i, intBinaryOperator))
/*      */             
/* 6207 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 6209 */         while ((node = advance()) != null)
/* 6210 */           i = intBinaryOperator.applyAsInt(i, toIntFunction.applyAsInt(node)); 
/* 6211 */         this.result = i;
/*      */         
/* 6213 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 6216 */           MapReduceEntriesToIntTask mapReduceEntriesToIntTask = (MapReduceEntriesToIntTask)countedCompleter;
/* 6217 */           MapReduceEntriesToIntTask<K, V> mapReduceEntriesToIntTask1 = mapReduceEntriesToIntTask.rights;
/* 6218 */           while (mapReduceEntriesToIntTask1 != null) {
/* 6219 */             mapReduceEntriesToIntTask.result = intBinaryOperator.applyAsInt(mapReduceEntriesToIntTask.result, mapReduceEntriesToIntTask1.result);
/* 6220 */             mapReduceEntriesToIntTask1 = mapReduceEntriesToIntTask.rights = mapReduceEntriesToIntTask1.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntBiFunction<? super K, ? super V> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceMappingsToIntTask<K, V> rights;
/*      */     MapReduceMappingsToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> param1BulkTask, int param1Int1, int param1Int2, int param1Int3, ConcurrentHashMap.Node<K, V>[] param1ArrayOfNode, MapReduceMappingsToIntTask<K, V> param1MapReduceMappingsToIntTask, ToIntBiFunction<? super K, ? super V> param1ToIntBiFunction, int param1Int4, IntBinaryOperator param1IntBinaryOperator) {
/* 6241 */       super(param1BulkTask, param1Int1, param1Int2, param1Int3, param1ArrayOfNode); this.nextRight = param1MapReduceMappingsToIntTask;
/* 6242 */       this.transformer = param1ToIntBiFunction;
/* 6243 */       this.basis = param1Int4; this.reducer = param1IntBinaryOperator;
/*      */     } public final Integer getRawResult() {
/* 6245 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ToIntBiFunction<? super K, ? super V> toIntBiFunction;
/*      */       IntBinaryOperator intBinaryOperator;
/* 6249 */       if ((toIntBiFunction = this.transformer) != null && (intBinaryOperator = this.reducer) != null) {
/*      */         
/* 6251 */         int i = this.basis; int k, m;
/* 6252 */         for (int j = this.baseIndex; this.batch > 0 && (m = (k = this.baseLimit) + j >>> 1) > j; ) {
/*      */           
/* 6254 */           addToPendingCount(1);
/* 6255 */           (this.rights = new MapReduceMappingsToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, toIntBiFunction, i, intBinaryOperator))
/*      */             
/* 6257 */             .fork();
/*      */         }  ConcurrentHashMap.Node<K, V> node;
/* 6259 */         while ((node = advance()) != null)
/* 6260 */           i = intBinaryOperator.applyAsInt(i, toIntBiFunction.applyAsInt(node.key, node.val)); 
/* 6261 */         this.result = i;
/*      */         
/* 6263 */         for (CountedCompleter<?> countedCompleter = firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
/*      */ 
/*      */           
/* 6266 */           MapReduceMappingsToIntTask mapReduceMappingsToIntTask = (MapReduceMappingsToIntTask)countedCompleter;
/* 6267 */           MapReduceMappingsToIntTask<K, V> mapReduceMappingsToIntTask1 = mapReduceMappingsToIntTask.rights;
/* 6268 */           while (mapReduceMappingsToIntTask1 != null) {
/* 6269 */             mapReduceMappingsToIntTask.result = intBinaryOperator.applyAsInt(mapReduceMappingsToIntTask.result, mapReduceMappingsToIntTask1.result);
/* 6270 */             mapReduceMappingsToIntTask1 = mapReduceMappingsToIntTask.rights = mapReduceMappingsToIntTask1.nextRight;
/*      */           } 
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
/*      */   static {
/*      */     try {
/* 6289 */       U = Unsafe.getUnsafe();
/* 6290 */       Class<ConcurrentHashMap> clazz = ConcurrentHashMap.class;
/*      */       
/* 6292 */       SIZECTL = U.objectFieldOffset(clazz.getDeclaredField("sizeCtl"));
/*      */       
/* 6294 */       TRANSFERINDEX = U.objectFieldOffset(clazz.getDeclaredField("transferIndex"));
/*      */       
/* 6296 */       BASECOUNT = U.objectFieldOffset(clazz.getDeclaredField("baseCount"));
/*      */       
/* 6298 */       CELLSBUSY = U.objectFieldOffset(clazz.getDeclaredField("cellsBusy"));
/* 6299 */       Class<CounterCell> clazz1 = CounterCell.class;
/*      */       
/* 6301 */       CELLVALUE = U.objectFieldOffset(clazz1.getDeclaredField("value"));
/* 6302 */       Class<Node[]> clazz2 = Node[].class;
/* 6303 */       ABASE = U.arrayBaseOffset(clazz2);
/* 6304 */       int i = U.arrayIndexScale(clazz2);
/* 6305 */       if ((i & i - 1) != 0)
/* 6306 */         throw new Error("data type scale not a power of two"); 
/* 6307 */       ASHIFT = 31 - Integer.numberOfLeadingZeros(i);
/* 6308 */     } catch (Exception exception) {
/* 6309 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ConcurrentHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */