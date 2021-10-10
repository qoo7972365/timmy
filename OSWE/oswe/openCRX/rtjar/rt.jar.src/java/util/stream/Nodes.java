/*      */ package java.util.stream;
/*      */ 
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.CountedCompleter;
/*      */ import java.util.function.BinaryOperator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class Nodes
/*      */ {
/*      */   static final long MAX_ARRAY_SIZE = 2147483639L;
/*      */   static final String BAD_SIZE = "Stream size exceeds max array size";
/*      */   
/*      */   private Nodes() {
/*   55 */     throw new Error("no instances");
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
/*   67 */   private static final Node EMPTY_NODE = new EmptyNode.OfRef();
/*   68 */   private static final Node.OfInt EMPTY_INT_NODE = new EmptyNode.OfInt();
/*   69 */   private static final Node.OfLong EMPTY_LONG_NODE = new EmptyNode.OfLong();
/*   70 */   private static final Node.OfDouble EMPTY_DOUBLE_NODE = new EmptyNode.OfDouble();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> Node<T> emptyNode(StreamShape paramStreamShape) {
/*   83 */     switch (paramStreamShape) { case REFERENCE:
/*   84 */         return EMPTY_NODE;
/*   85 */       case INT_VALUE: return EMPTY_INT_NODE;
/*   86 */       case LONG_VALUE: return EMPTY_LONG_NODE;
/*   87 */       case DOUBLE_VALUE: return EMPTY_DOUBLE_NODE; }
/*      */     
/*   89 */     throw new IllegalStateException("Unknown shape " + paramStreamShape);
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
/*      */   static <T> Node<T> conc(StreamShape paramStreamShape, Node<T> paramNode1, Node<T> paramNode2) {
/*  114 */     switch (paramStreamShape) {
/*      */       case REFERENCE:
/*  116 */         return new ConcNode<>(paramNode1, paramNode2);
/*      */       case INT_VALUE:
/*  118 */         return new ConcNode.OfInt((Node.OfInt)paramNode1, (Node.OfInt)paramNode2);
/*      */       case LONG_VALUE:
/*  120 */         return new ConcNode.OfLong((Node.OfLong)paramNode1, (Node.OfLong)paramNode2);
/*      */       case DOUBLE_VALUE:
/*  122 */         return new ConcNode.OfDouble((Node.OfDouble)paramNode1, (Node.OfDouble)paramNode2);
/*      */     } 
/*  124 */     throw new IllegalStateException("Unknown shape " + paramStreamShape);
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
/*      */   static <T> Node<T> node(T[] paramArrayOfT) {
/*  140 */     return new ArrayNode<>(paramArrayOfT);
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
/*      */   static <T> Node<T> node(Collection<T> paramCollection) {
/*  153 */     return new CollectionNode<>(paramCollection);
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
/*      */   static <T> Node.Builder<T> builder(long paramLong, IntFunction<T[]> paramIntFunction) {
/*  167 */     return (paramLong >= 0L && paramLong < 2147483639L) ? new FixedNodeBuilder<>(paramLong, paramIntFunction) : 
/*      */       
/*  169 */       builder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> Node.Builder<T> builder() {
/*  179 */     return new SpinedNodeBuilder<>();
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
/*      */   static Node.OfInt node(int[] paramArrayOfint) {
/*  193 */     return new IntArrayNode(paramArrayOfint);
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
/*      */   static Node.Builder.OfInt intBuilder(long paramLong) {
/*  205 */     return (paramLong >= 0L && paramLong < 2147483639L) ? new IntFixedNodeBuilder(paramLong) : 
/*      */       
/*  207 */       intBuilder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Node.Builder.OfInt intBuilder() {
/*  216 */     return new IntSpinedNodeBuilder();
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
/*      */   static Node.OfLong node(long[] paramArrayOflong) {
/*  230 */     return new LongArrayNode(paramArrayOflong);
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
/*      */   static Node.Builder.OfLong longBuilder(long paramLong) {
/*  242 */     return (paramLong >= 0L && paramLong < 2147483639L) ? new LongFixedNodeBuilder(paramLong) : 
/*      */       
/*  244 */       longBuilder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Node.Builder.OfLong longBuilder() {
/*  253 */     return new LongSpinedNodeBuilder();
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
/*      */   static Node.OfDouble node(double[] paramArrayOfdouble) {
/*  267 */     return new DoubleArrayNode(paramArrayOfdouble);
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
/*      */   static Node.Builder.OfDouble doubleBuilder(long paramLong) {
/*  279 */     return (paramLong >= 0L && paramLong < 2147483639L) ? new DoubleFixedNodeBuilder(paramLong) : 
/*      */       
/*  281 */       doubleBuilder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Node.Builder.OfDouble doubleBuilder() {
/*  290 */     return new DoubleSpinedNodeBuilder();
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
/*      */   public static <P_IN, P_OUT> Node<P_OUT> collect(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<P_OUT[]> paramIntFunction) {
/*  320 */     long l = paramPipelineHelper.exactOutputSizeIfKnown(paramSpliterator);
/*  321 */     if (l >= 0L && paramSpliterator.hasCharacteristics(16384)) {
/*  322 */       if (l >= 2147483639L)
/*  323 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  324 */       Object[] arrayOfObject = (Object[])paramIntFunction.apply((int)l);
/*  325 */       (new SizedCollectorTask.OfRef<>(paramSpliterator, paramPipelineHelper, arrayOfObject)).invoke();
/*  326 */       return node((P_OUT[])arrayOfObject);
/*      */     } 
/*  328 */     Node<?> node = (new CollectorTask.OfRef<>(paramPipelineHelper, paramIntFunction, paramSpliterator)).invoke();
/*  329 */     return paramBoolean ? flatten((Node)node, paramIntFunction) : (Node)node;
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
/*      */   public static <P_IN> Node.OfInt collectInt(PipelineHelper<Integer> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean) {
/*  357 */     long l = paramPipelineHelper.exactOutputSizeIfKnown(paramSpliterator);
/*  358 */     if (l >= 0L && paramSpliterator.hasCharacteristics(16384)) {
/*  359 */       if (l >= 2147483639L)
/*  360 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  361 */       int[] arrayOfInt = new int[(int)l];
/*  362 */       (new SizedCollectorTask.OfInt(paramSpliterator, paramPipelineHelper, arrayOfInt)).invoke();
/*  363 */       return node(arrayOfInt);
/*      */     } 
/*      */     
/*  366 */     Node.OfInt ofInt = (new CollectorTask.OfInt(paramPipelineHelper, paramSpliterator)).invoke();
/*  367 */     return paramBoolean ? flattenInt(ofInt) : ofInt;
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
/*      */   public static <P_IN> Node.OfLong collectLong(PipelineHelper<Long> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean) {
/*  395 */     long l = paramPipelineHelper.exactOutputSizeIfKnown(paramSpliterator);
/*  396 */     if (l >= 0L && paramSpliterator.hasCharacteristics(16384)) {
/*  397 */       if (l >= 2147483639L)
/*  398 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  399 */       long[] arrayOfLong = new long[(int)l];
/*  400 */       (new SizedCollectorTask.OfLong(paramSpliterator, paramPipelineHelper, arrayOfLong)).invoke();
/*  401 */       return node(arrayOfLong);
/*      */     } 
/*      */     
/*  404 */     Node.OfLong ofLong = (new CollectorTask.OfLong(paramPipelineHelper, paramSpliterator)).invoke();
/*  405 */     return paramBoolean ? flattenLong(ofLong) : ofLong;
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
/*      */   public static <P_IN> Node.OfDouble collectDouble(PipelineHelper<Double> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean) {
/*  433 */     long l = paramPipelineHelper.exactOutputSizeIfKnown(paramSpliterator);
/*  434 */     if (l >= 0L && paramSpliterator.hasCharacteristics(16384)) {
/*  435 */       if (l >= 2147483639L)
/*  436 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  437 */       double[] arrayOfDouble = new double[(int)l];
/*  438 */       (new SizedCollectorTask.OfDouble(paramSpliterator, paramPipelineHelper, arrayOfDouble)).invoke();
/*  439 */       return node(arrayOfDouble);
/*      */     } 
/*      */     
/*  442 */     Node.OfDouble ofDouble = (new CollectorTask.OfDouble(paramPipelineHelper, paramSpliterator)).invoke();
/*  443 */     return paramBoolean ? flattenDouble(ofDouble) : ofDouble;
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
/*      */   public static <T> Node<T> flatten(Node<T> paramNode, IntFunction<T[]> paramIntFunction) {
/*  465 */     if (paramNode.getChildCount() > 0) {
/*  466 */       long l = paramNode.count();
/*  467 */       if (l >= 2147483639L)
/*  468 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  469 */       Object[] arrayOfObject = (Object[])paramIntFunction.apply((int)l);
/*  470 */       (new ToArrayTask.OfRef(paramNode, arrayOfObject, 0)).invoke();
/*  471 */       return node((T[])arrayOfObject);
/*      */     } 
/*  473 */     return paramNode;
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
/*      */   public static Node.OfInt flattenInt(Node.OfInt paramOfInt) {
/*  491 */     if (paramOfInt.getChildCount() > 0) {
/*  492 */       long l = paramOfInt.count();
/*  493 */       if (l >= 2147483639L)
/*  494 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  495 */       int[] arrayOfInt = new int[(int)l];
/*  496 */       (new ToArrayTask.OfInt(paramOfInt, arrayOfInt, 0)).invoke();
/*  497 */       return node(arrayOfInt);
/*      */     } 
/*  499 */     return paramOfInt;
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
/*      */   public static Node.OfLong flattenLong(Node.OfLong paramOfLong) {
/*  517 */     if (paramOfLong.getChildCount() > 0) {
/*  518 */       long l = paramOfLong.count();
/*  519 */       if (l >= 2147483639L)
/*  520 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  521 */       long[] arrayOfLong = new long[(int)l];
/*  522 */       (new ToArrayTask.OfLong(paramOfLong, arrayOfLong, 0)).invoke();
/*  523 */       return node(arrayOfLong);
/*      */     } 
/*  525 */     return paramOfLong;
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
/*      */   public static Node.OfDouble flattenDouble(Node.OfDouble paramOfDouble) {
/*  543 */     if (paramOfDouble.getChildCount() > 0) {
/*  544 */       long l = paramOfDouble.count();
/*  545 */       if (l >= 2147483639L)
/*  546 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  547 */       double[] arrayOfDouble = new double[(int)l];
/*  548 */       (new ToArrayTask.OfDouble(paramOfDouble, arrayOfDouble, 0)).invoke();
/*  549 */       return node(arrayOfDouble);
/*      */     } 
/*  551 */     return paramOfDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class EmptyNode<T, T_ARR, T_CONS>
/*      */     implements Node<T>
/*      */   {
/*      */     public T[] asArray(IntFunction<T[]> param1IntFunction) {
/*  562 */       return param1IntFunction.apply(0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyInto(T_ARR param1T_ARR, int param1Int) {}
/*      */     
/*      */     public long count() {
/*  569 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(T_CONS param1T_CONS) {}
/*      */     
/*      */     private static class OfRef<T>
/*      */       extends EmptyNode<T, T[], Consumer<? super T>>
/*      */     {
/*      */       private OfRef() {}
/*      */       
/*      */       public Spliterator<T> spliterator() {
/*  581 */         return Spliterators.emptySpliterator();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class OfInt
/*      */       extends EmptyNode<Integer, int[], IntConsumer>
/*      */       implements Node.OfInt
/*      */     {
/*      */       public Spliterator.OfInt spliterator() {
/*  593 */         return Spliterators.emptyIntSpliterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public int[] asPrimitiveArray() {
/*  598 */         return Nodes.EMPTY_INT_ARRAY;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class OfLong
/*      */       extends EmptyNode<Long, long[], LongConsumer>
/*      */       implements Node.OfLong
/*      */     {
/*      */       public Spliterator.OfLong spliterator() {
/*  610 */         return Spliterators.emptyLongSpliterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public long[] asPrimitiveArray() {
/*  615 */         return Nodes.EMPTY_LONG_ARRAY;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class OfDouble
/*      */       extends EmptyNode<Double, double[], DoubleConsumer>
/*      */       implements Node.OfDouble
/*      */     {
/*      */       public Spliterator.OfDouble spliterator() {
/*  627 */         return Spliterators.emptyDoubleSpliterator();
/*      */       }
/*      */       
/*      */       public double[] asPrimitiveArray()
/*      */       {
/*  632 */         return Nodes.EMPTY_DOUBLE_ARRAY; } } } private static class OfRef<T> extends EmptyNode<T, T[], Consumer<? super T>> { private OfRef() {} public Spliterator<T> spliterator() { return Spliterators.emptySpliterator(); } } private static final class OfInt extends EmptyNode<Integer, int[], IntConsumer> implements Node.OfInt { public Spliterator.OfInt spliterator() { return Spliterators.emptyIntSpliterator(); } public int[] asPrimitiveArray() { return Nodes.EMPTY_INT_ARRAY; } } private static final class OfLong extends EmptyNode<Long, long[], LongConsumer> implements Node.OfLong { public Spliterator.OfLong spliterator() { return Spliterators.emptyLongSpliterator(); } public long[] asPrimitiveArray() { return Nodes.EMPTY_LONG_ARRAY; } } private static final class OfDouble extends EmptyNode<Double, double[], DoubleConsumer> implements Node.OfDouble { public Spliterator.OfDouble spliterator() { return Spliterators.emptyDoubleSpliterator(); } public double[] asPrimitiveArray() { return Nodes.EMPTY_DOUBLE_ARRAY; }
/*      */      }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ArrayNode<T>
/*      */     implements Node<T>
/*      */   {
/*      */     final T[] array;
/*      */     int curSize;
/*      */     
/*      */     ArrayNode(long param1Long, IntFunction<T[]> param1IntFunction) {
/*  644 */       if (param1Long >= 2147483639L)
/*  645 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  646 */       this.array = param1IntFunction.apply((int)param1Long);
/*  647 */       this.curSize = 0;
/*      */     }
/*      */     
/*      */     ArrayNode(T[] param1ArrayOfT) {
/*  651 */       this.array = param1ArrayOfT;
/*  652 */       this.curSize = param1ArrayOfT.length;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator<T> spliterator() {
/*  659 */       return Arrays.spliterator(this.array, 0, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyInto(T[] param1ArrayOfT, int param1Int) {
/*  664 */       System.arraycopy(this.array, 0, param1ArrayOfT, param1Int, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public T[] asArray(IntFunction<T[]> param1IntFunction) {
/*  669 */       if (this.array.length == this.curSize) {
/*  670 */         return this.array;
/*      */       }
/*  672 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long count() {
/*  678 */       return this.curSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super T> param1Consumer) {
/*  683 */       for (byte b = 0; b < this.curSize; b++) {
/*  684 */         param1Consumer.accept(this.array[b]);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  692 */       return String.format("ArrayNode[%d][%s]", new Object[] {
/*  693 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString((Object[])this.array)
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class CollectionNode<T> implements Node<T> {
/*      */     private final Collection<T> c;
/*      */     
/*      */     CollectionNode(Collection<T> param1Collection) {
/*  702 */       this.c = param1Collection;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator<T> spliterator() {
/*  709 */       return this.c.stream().spliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(T[] param1ArrayOfT, int param1Int) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield c : Ljava/util/Collection;
/*      */       //   4: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */       //   9: astore_3
/*      */       //   10: aload_3
/*      */       //   11: invokeinterface hasNext : ()Z
/*      */       //   16: ifeq -> 38
/*      */       //   19: aload_3
/*      */       //   20: invokeinterface next : ()Ljava/lang/Object;
/*      */       //   25: astore #4
/*      */       //   27: aload_1
/*      */       //   28: iload_2
/*      */       //   29: iinc #2, 1
/*      */       //   32: aload #4
/*      */       //   34: aastore
/*      */       //   35: goto -> 10
/*      */       //   38: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #714	-> 0
/*      */       //   #715	-> 27
/*      */       //   #716	-> 38
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public T[] asArray(IntFunction<T[]> param1IntFunction) {
/*  721 */       return this.c.toArray(param1IntFunction.apply(this.c.size()));
/*      */     }
/*      */ 
/*      */     
/*      */     public long count() {
/*  726 */       return this.c.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super T> param1Consumer) {
/*  731 */       this.c.forEach(param1Consumer);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  738 */       return String.format("CollectionNode[%d][%s]", new Object[] { Integer.valueOf(this.c.size()), this.c });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class AbstractConcNode<T, T_NODE extends Node<T>>
/*      */     implements Node<T>
/*      */   {
/*      */     protected final T_NODE left;
/*      */     protected final T_NODE right;
/*      */     private final long size;
/*      */     
/*      */     AbstractConcNode(T_NODE param1T_NODE1, T_NODE param1T_NODE2) {
/*  751 */       this.left = param1T_NODE1;
/*  752 */       this.right = param1T_NODE2;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  757 */       this.size = param1T_NODE1.count() + param1T_NODE2.count();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getChildCount() {
/*  762 */       return 2;
/*      */     }
/*      */ 
/*      */     
/*      */     public T_NODE getChild(int param1Int) {
/*  767 */       if (param1Int == 0) return this.left; 
/*  768 */       if (param1Int == 1) return this.right; 
/*  769 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long count() {
/*  774 */       return this.size;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ConcNode<T>
/*      */     extends AbstractConcNode<T, Node<T>>
/*      */     implements Node<T>
/*      */   {
/*      */     ConcNode(Node<T> param1Node1, Node<T> param1Node2) {
/*  783 */       super(param1Node1, param1Node2);
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<T> spliterator() {
/*  788 */       return new Nodes.InternalNodeSpliterator.OfRef<>(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyInto(T[] param1ArrayOfT, int param1Int) {
/*  793 */       Objects.requireNonNull(param1ArrayOfT);
/*  794 */       this.left.copyInto(param1ArrayOfT, param1Int);
/*      */ 
/*      */       
/*  797 */       this.right.copyInto(param1ArrayOfT, param1Int + (int)this.left.count());
/*      */     }
/*      */ 
/*      */     
/*      */     public T[] asArray(IntFunction<T[]> param1IntFunction) {
/*  802 */       long l = count();
/*  803 */       if (l >= 2147483639L)
/*  804 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  805 */       Object[] arrayOfObject = (Object[])param1IntFunction.apply((int)l);
/*  806 */       copyInto((T[])arrayOfObject, 0);
/*  807 */       return (T[])arrayOfObject;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super T> param1Consumer) {
/*  812 */       this.left.forEach(param1Consumer);
/*  813 */       this.right.forEach(param1Consumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public Node<T> truncate(long param1Long1, long param1Long2, IntFunction<T[]> param1IntFunction) {
/*  818 */       if (param1Long1 == 0L && param1Long2 == count())
/*  819 */         return this; 
/*  820 */       long l = this.left.count();
/*  821 */       if (param1Long1 >= l)
/*  822 */         return this.right.truncate(param1Long1 - l, param1Long2 - l, param1IntFunction); 
/*  823 */       if (param1Long2 <= l) {
/*  824 */         return this.left.truncate(param1Long1, param1Long2, param1IntFunction);
/*      */       }
/*  826 */       return Nodes.conc(getShape(), this.left.truncate(param1Long1, l, param1IntFunction), this.right
/*  827 */           .truncate(0L, param1Long2 - l, param1IntFunction));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  833 */       if (count() < 32L) {
/*  834 */         return String.format("ConcNode[%s.%s]", new Object[] { this.left, this.right });
/*      */       }
/*  836 */       return String.format("ConcNode[size=%d]", new Object[] { Long.valueOf(count()) });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static abstract class OfPrimitive<E, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<E, T_CONS, T_SPLITR>, T_NODE extends Node.OfPrimitive<E, T_CONS, T_ARR, T_SPLITR, T_NODE>>
/*      */       extends Nodes.AbstractConcNode<E, T_NODE>
/*      */       implements Node.OfPrimitive<E, T_CONS, T_ARR, T_SPLITR, T_NODE>
/*      */     {
/*      */       OfPrimitive(T_NODE param2T_NODE1, T_NODE param2T_NODE2) {
/*  847 */         super(param2T_NODE1, param2T_NODE2);
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEach(T_CONS param2T_CONS) {
/*  852 */         ((Node.OfPrimitive)this.left).forEach(param2T_CONS);
/*  853 */         ((Node.OfPrimitive)this.right).forEach(param2T_CONS);
/*      */       }
/*      */ 
/*      */       
/*      */       public void copyInto(T_ARR param2T_ARR, int param2Int) {
/*  858 */         ((Node.OfPrimitive)this.left).copyInto(param2T_ARR, param2Int);
/*      */ 
/*      */         
/*  861 */         ((Node.OfPrimitive)this.right).copyInto(param2T_ARR, param2Int + (int)((Node.OfPrimitive)this.left).count());
/*      */       }
/*      */ 
/*      */       
/*      */       public T_ARR asPrimitiveArray() {
/*  866 */         long l = count();
/*  867 */         if (l >= 2147483639L)
/*  868 */           throw new IllegalArgumentException("Stream size exceeds max array size"); 
/*  869 */         T_ARR t_ARR = newArray((int)l);
/*  870 */         copyInto(t_ARR, 0);
/*  871 */         return t_ARR;
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/*  876 */         if (count() < 32L) {
/*  877 */           return String.format("%s[%s.%s]", new Object[] { getClass().getName(), this.left, this.right });
/*      */         }
/*  879 */         return String.format("%s[size=%d]", new Object[] { getClass().getName(), Long.valueOf(count()) });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt>
/*      */       implements Node.OfInt
/*      */     {
/*      */       OfInt(Node.OfInt param2OfInt1, Node.OfInt param2OfInt2) {
/*  888 */         super(param2OfInt1, param2OfInt2);
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfInt spliterator() {
/*  893 */         return new Nodes.InternalNodeSpliterator.OfInt(this);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong>
/*      */       implements Node.OfLong
/*      */     {
/*      */       OfLong(Node.OfLong param2OfLong1, Node.OfLong param2OfLong2) {
/*  902 */         super(param2OfLong1, param2OfLong2);
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfLong spliterator() {
/*  907 */         return new Nodes.InternalNodeSpliterator.OfLong(this);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble>
/*      */       implements Node.OfDouble
/*      */     {
/*      */       OfDouble(Node.OfDouble param2OfDouble1, Node.OfDouble param2OfDouble2) {
/*  916 */         super(param2OfDouble1, param2OfDouble2);
/*      */       }
/*      */       
/*      */       public Spliterator.OfDouble spliterator()
/*      */       {
/*  921 */         return new Nodes.InternalNodeSpliterator.OfDouble(this); } } } static final class OfInt extends ConcNode.OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt> implements Node.OfInt { OfInt(Node.OfInt param1OfInt1, Node.OfInt param1OfInt2) { super(param1OfInt1, param1OfInt2); } public Spliterator.OfInt spliterator() { return new Nodes.InternalNodeSpliterator.OfInt(this); } } static final class OfLong extends ConcNode.OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong> implements Node.OfLong { OfLong(Node.OfLong param1OfLong1, Node.OfLong param1OfLong2) { super(param1OfLong1, param1OfLong2); } public Spliterator.OfLong spliterator() { return new Nodes.InternalNodeSpliterator.OfLong(this); } } static final class OfDouble extends ConcNode.OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble> implements Node.OfDouble { OfDouble(Node.OfDouble param1OfDouble1, Node.OfDouble param1OfDouble2) { super(param1OfDouble1, param1OfDouble2); } public Spliterator.OfDouble spliterator() { return new Nodes.InternalNodeSpliterator.OfDouble(this); }
/*      */      }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class InternalNodeSpliterator<T, S extends Spliterator<T>, N extends Node<T>>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     N curNode;
/*      */ 
/*      */ 
/*      */     
/*      */     int curChildIndex;
/*      */ 
/*      */ 
/*      */     
/*      */     S lastNodeSpliterator;
/*      */ 
/*      */ 
/*      */     
/*      */     S tryAdvanceSpliterator;
/*      */ 
/*      */ 
/*      */     
/*      */     Deque<N> tryAdvanceStack;
/*      */ 
/*      */ 
/*      */     
/*      */     InternalNodeSpliterator(N param1N) {
/*  952 */       this.curNode = param1N;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final Deque<N> initStack() {
/*  963 */       ArrayDeque<N> arrayDeque = new ArrayDeque(8);
/*  964 */       for (int i = this.curNode.getChildCount() - 1; i >= this.curChildIndex; i--)
/*  965 */         arrayDeque.addFirst(this.curNode.getChild(i)); 
/*  966 */       return arrayDeque;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final N findNextLeafNode(Deque<N> param1Deque) {
/*  975 */       Node node = null;
/*  976 */       while ((node = (Node)param1Deque.pollFirst()) != null) {
/*  977 */         if (node.getChildCount() == 0) {
/*  978 */           if (node.count() > 0L)
/*  979 */             return (N)node;  continue;
/*      */         } 
/*  981 */         for (int i = node.getChildCount() - 1; i >= 0; i--) {
/*  982 */           param1Deque.addFirst((N)node.getChild(i));
/*      */         }
/*      */       } 
/*      */       
/*  986 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final boolean initTryAdvance() {
/*  991 */       if (this.curNode == null) {
/*  992 */         return false;
/*      */       }
/*  994 */       if (this.tryAdvanceSpliterator == null)
/*  995 */         if (this.lastNodeSpliterator == null) {
/*      */           
/*  997 */           this.tryAdvanceStack = initStack();
/*  998 */           N n = findNextLeafNode(this.tryAdvanceStack);
/*  999 */           if (n != null) {
/* 1000 */             this.tryAdvanceSpliterator = (S)n.spliterator();
/*      */           }
/*      */           else {
/*      */             
/* 1004 */             this.curNode = null;
/* 1005 */             return false;
/*      */           } 
/*      */         } else {
/*      */           
/* 1009 */           this.tryAdvanceSpliterator = this.lastNodeSpliterator;
/*      */         }  
/* 1011 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final S trySplit() {
/* 1017 */       if (this.curNode == null || this.tryAdvanceSpliterator != null)
/* 1018 */         return null; 
/* 1019 */       if (this.lastNodeSpliterator != null)
/* 1020 */         return (S)this.lastNodeSpliterator.trySplit(); 
/* 1021 */       if (this.curChildIndex < this.curNode.getChildCount() - 1) {
/* 1022 */         return (S)this.curNode.getChild(this.curChildIndex++).spliterator();
/*      */       }
/* 1024 */       this.curNode = (N)this.curNode.getChild(this.curChildIndex);
/* 1025 */       if (this.curNode.getChildCount() == 0) {
/* 1026 */         this.lastNodeSpliterator = (S)this.curNode.spliterator();
/* 1027 */         return (S)this.lastNodeSpliterator.trySplit();
/*      */       } 
/*      */       
/* 1030 */       this.curChildIndex = 0;
/* 1031 */       return (S)this.curNode.getChild(this.curChildIndex++).spliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final long estimateSize() {
/* 1038 */       if (this.curNode == null) {
/* 1039 */         return 0L;
/*      */       }
/*      */ 
/*      */       
/* 1043 */       if (this.lastNodeSpliterator != null) {
/* 1044 */         return this.lastNodeSpliterator.estimateSize();
/*      */       }
/* 1046 */       long l = 0L;
/* 1047 */       for (int i = this.curChildIndex; i < this.curNode.getChildCount(); i++)
/* 1048 */         l += this.curNode.getChild(i).count(); 
/* 1049 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final int characteristics() {
/* 1055 */       return 64;
/*      */     }
/*      */     
/*      */     private static final class OfRef<T>
/*      */       extends InternalNodeSpliterator<T, Spliterator<T>, Node<T>>
/*      */     {
/*      */       OfRef(Node<T> param2Node) {
/* 1062 */         super(param2Node);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/* 1067 */         if (!initTryAdvance()) {
/* 1068 */           return false;
/*      */         }
/* 1070 */         boolean bool = this.tryAdvanceSpliterator.tryAdvance(param2Consumer);
/* 1071 */         if (!bool) {
/* 1072 */           if (this.lastNodeSpliterator == null) {
/*      */             
/* 1074 */             Node<T> node = findNextLeafNode(this.tryAdvanceStack);
/* 1075 */             if (node != null) {
/* 1076 */               this.tryAdvanceSpliterator = node.spliterator();
/*      */               
/* 1078 */               return this.tryAdvanceSpliterator.tryAdvance(param2Consumer);
/*      */             } 
/*      */           } 
/*      */           
/* 1082 */           this.curNode = null;
/*      */         } 
/* 1084 */         return bool;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super T> param2Consumer) {
/* 1089 */         if (this.curNode == null) {
/*      */           return;
/*      */         }
/* 1092 */         if (this.tryAdvanceSpliterator == null) {
/* 1093 */           if (this.lastNodeSpliterator == null) {
/* 1094 */             Deque<Node<T>> deque = initStack();
/*      */             Node<T> node;
/* 1096 */             while ((node = findNextLeafNode(deque)) != null) {
/* 1097 */               node.forEach(param2Consumer);
/*      */             }
/* 1099 */             this.curNode = null;
/*      */           } else {
/*      */             
/* 1102 */             this.lastNodeSpliterator.forEachRemaining(param2Consumer);
/*      */           } 
/*      */         } else {
/* 1105 */           while (tryAdvance(param2Consumer));
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private static abstract class OfPrimitive<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, N extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, N>>
/*      */       extends InternalNodeSpliterator<T, T_SPLITR, N>
/*      */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>
/*      */     {
/*      */       OfPrimitive(N param2N) {
/* 1116 */         super(param2N);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(T_CONS param2T_CONS) {
/* 1121 */         if (!initTryAdvance()) {
/* 1122 */           return false;
/*      */         }
/* 1124 */         boolean bool = ((Spliterator.OfPrimitive)this.tryAdvanceSpliterator).tryAdvance(param2T_CONS);
/* 1125 */         if (!bool) {
/* 1126 */           if (this.lastNodeSpliterator == null) {
/*      */             
/* 1128 */             Node.OfPrimitive ofPrimitive = (Node.OfPrimitive)findNextLeafNode(this.tryAdvanceStack);
/* 1129 */             if (ofPrimitive != null) {
/* 1130 */               this.tryAdvanceSpliterator = (T_SPLITR)ofPrimitive.spliterator();
/*      */               
/* 1132 */               return ((Spliterator.OfPrimitive)this.tryAdvanceSpliterator).tryAdvance(param2T_CONS);
/*      */             } 
/*      */           } 
/*      */           
/* 1136 */           this.curNode = null;
/*      */         } 
/* 1138 */         return bool;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(T_CONS param2T_CONS) {
/* 1143 */         if (this.curNode == null) {
/*      */           return;
/*      */         }
/* 1146 */         if (this.tryAdvanceSpliterator == null) {
/* 1147 */           if (this.lastNodeSpliterator == null) {
/* 1148 */             Deque<N> deque = initStack();
/*      */             Node.OfPrimitive ofPrimitive;
/* 1150 */             while ((ofPrimitive = (Node.OfPrimitive)findNextLeafNode(deque)) != null) {
/* 1151 */               ofPrimitive.forEach(param2T_CONS);
/*      */             }
/* 1153 */             this.curNode = null;
/*      */           } else {
/*      */             
/* 1156 */             ((Spliterator.OfPrimitive)this.lastNodeSpliterator).forEachRemaining(param2T_CONS);
/*      */           } 
/*      */         } else {
/* 1159 */           while (tryAdvance(param2T_CONS));
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfInt
/*      */       extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt>
/*      */       implements Spliterator.OfInt {
/*      */       OfInt(Node.OfInt param2OfInt) {
/* 1168 */         super(param2OfInt);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfLong
/*      */       extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong>
/*      */       implements Spliterator.OfLong
/*      */     {
/*      */       OfLong(Node.OfLong param2OfLong) {
/* 1177 */         super(param2OfLong);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfDouble
/*      */       extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble>
/*      */       implements Spliterator.OfDouble
/*      */     {
/*      */       OfDouble(Node.OfDouble param2OfDouble) {
/* 1186 */         super(param2OfDouble);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class FixedNodeBuilder<T>
/*      */     extends ArrayNode<T>
/*      */     implements Node.Builder<T>
/*      */   {
/*      */     FixedNodeBuilder(long param1Long, IntFunction<T[]> param1IntFunction) {
/* 1199 */       super(param1Long, param1IntFunction);
/* 1200 */       assert param1Long < 2147483639L;
/*      */     }
/*      */ 
/*      */     
/*      */     public Node<T> build() {
/* 1205 */       if (this.curSize < this.array.length)
/* 1206 */         throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", new Object[] {
/* 1207 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length) })); 
/* 1208 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1213 */       if (param1Long != this.array.length)
/* 1214 */         throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", new Object[] {
/* 1215 */                 Long.valueOf(param1Long), Integer.valueOf(this.array.length) })); 
/* 1216 */       this.curSize = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(T param1T) {
/* 1221 */       if (this.curSize < this.array.length) {
/* 1222 */         this.array[this.curSize++] = param1T;
/*      */       } else {
/* 1224 */         throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", new Object[] {
/* 1225 */                 Integer.valueOf(this.array.length)
/*      */               }));
/*      */       } 
/*      */     }
/*      */     
/*      */     public void end() {
/* 1231 */       if (this.curSize < this.array.length)
/* 1232 */         throw new IllegalStateException(String.format("End size %d is less than fixed size %d", new Object[] {
/* 1233 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)
/*      */               })); 
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1238 */       return String.format("FixedNodeBuilder[%d][%s]", new Object[] {
/* 1239 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString((Object[])this.array)
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class SpinedNodeBuilder<T>
/*      */     extends SpinedBuffer<T>
/*      */     implements Node<T>, Node.Builder<T>
/*      */   {
/*      */     private boolean building = false;
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator<T> spliterator() {
/* 1255 */       assert !this.building : "during building";
/* 1256 */       return super.spliterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super T> param1Consumer) {
/* 1261 */       assert !this.building : "during building";
/* 1262 */       super.forEach(param1Consumer);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1268 */       assert !this.building : "was already building";
/* 1269 */       this.building = true;
/* 1270 */       clear();
/* 1271 */       ensureCapacity(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(T param1T) {
/* 1276 */       assert this.building : "not building";
/* 1277 */       super.accept(param1T);
/*      */     }
/*      */ 
/*      */     
/*      */     public void end() {
/* 1282 */       assert this.building : "was not building";
/* 1283 */       this.building = false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(T[] param1ArrayOfT, int param1Int) {
/* 1289 */       assert !this.building : "during building";
/* 1290 */       super.copyInto(param1ArrayOfT, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public T[] asArray(IntFunction<T[]> param1IntFunction) {
/* 1295 */       assert !this.building : "during building";
/* 1296 */       return super.asArray(param1IntFunction);
/*      */     }
/*      */ 
/*      */     
/*      */     public Node<T> build() {
/* 1301 */       assert !this.building : "during building";
/* 1302 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1308 */   private static final int[] EMPTY_INT_ARRAY = new int[0];
/* 1309 */   private static final long[] EMPTY_LONG_ARRAY = new long[0];
/* 1310 */   private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
/*      */   
/*      */   private static class IntArrayNode implements Node.OfInt {
/*      */     final int[] array;
/*      */     int curSize;
/*      */     
/*      */     IntArrayNode(long param1Long) {
/* 1317 */       if (param1Long >= 2147483639L)
/* 1318 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 1319 */       this.array = new int[(int)param1Long];
/* 1320 */       this.curSize = 0;
/*      */     }
/*      */     
/*      */     IntArrayNode(int[] param1ArrayOfint) {
/* 1324 */       this.array = param1ArrayOfint;
/* 1325 */       this.curSize = param1ArrayOfint.length;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator.OfInt spliterator() {
/* 1332 */       return Arrays.spliterator(this.array, 0, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] asPrimitiveArray() {
/* 1337 */       if (this.array.length == this.curSize) {
/* 1338 */         return this.array;
/*      */       }
/* 1340 */       return Arrays.copyOf(this.array, this.curSize);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(int[] param1ArrayOfint, int param1Int) {
/* 1346 */       System.arraycopy(this.array, 0, param1ArrayOfint, param1Int, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public long count() {
/* 1351 */       return this.curSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer param1IntConsumer) {
/* 1356 */       for (byte b = 0; b < this.curSize; b++) {
/* 1357 */         param1IntConsumer.accept(this.array[b]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1363 */       return String.format("IntArrayNode[%d][%s]", new Object[] {
/* 1364 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array) });
/*      */     }
/*      */   }
/*      */   
/*      */   private static class LongArrayNode implements Node.OfLong {
/*      */     final long[] array;
/*      */     int curSize;
/*      */     
/*      */     LongArrayNode(long param1Long) {
/* 1373 */       if (param1Long >= 2147483639L)
/* 1374 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 1375 */       this.array = new long[(int)param1Long];
/* 1376 */       this.curSize = 0;
/*      */     }
/*      */     
/*      */     LongArrayNode(long[] param1ArrayOflong) {
/* 1380 */       this.array = param1ArrayOflong;
/* 1381 */       this.curSize = param1ArrayOflong.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfLong spliterator() {
/* 1386 */       return Arrays.spliterator(this.array, 0, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public long[] asPrimitiveArray() {
/* 1391 */       if (this.array.length == this.curSize) {
/* 1392 */         return this.array;
/*      */       }
/* 1394 */       return Arrays.copyOf(this.array, this.curSize);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(long[] param1ArrayOflong, int param1Int) {
/* 1400 */       System.arraycopy(this.array, 0, param1ArrayOflong, param1Int, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public long count() {
/* 1405 */       return this.curSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer param1LongConsumer) {
/* 1410 */       for (byte b = 0; b < this.curSize; b++) {
/* 1411 */         param1LongConsumer.accept(this.array[b]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1417 */       return String.format("LongArrayNode[%d][%s]", new Object[] {
/* 1418 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array) });
/*      */     }
/*      */   }
/*      */   
/*      */   private static class DoubleArrayNode implements Node.OfDouble {
/*      */     final double[] array;
/*      */     int curSize;
/*      */     
/*      */     DoubleArrayNode(long param1Long) {
/* 1427 */       if (param1Long >= 2147483639L)
/* 1428 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 1429 */       this.array = new double[(int)param1Long];
/* 1430 */       this.curSize = 0;
/*      */     }
/*      */     
/*      */     DoubleArrayNode(double[] param1ArrayOfdouble) {
/* 1434 */       this.array = param1ArrayOfdouble;
/* 1435 */       this.curSize = param1ArrayOfdouble.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfDouble spliterator() {
/* 1440 */       return Arrays.spliterator(this.array, 0, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public double[] asPrimitiveArray() {
/* 1445 */       if (this.array.length == this.curSize) {
/* 1446 */         return this.array;
/*      */       }
/* 1448 */       return Arrays.copyOf(this.array, this.curSize);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(double[] param1ArrayOfdouble, int param1Int) {
/* 1454 */       System.arraycopy(this.array, 0, param1ArrayOfdouble, param1Int, this.curSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public long count() {
/* 1459 */       return this.curSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer param1DoubleConsumer) {
/* 1464 */       for (byte b = 0; b < this.curSize; b++) {
/* 1465 */         param1DoubleConsumer.accept(this.array[b]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1471 */       return String.format("DoubleArrayNode[%d][%s]", new Object[] {
/* 1472 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array)
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class IntFixedNodeBuilder
/*      */     extends IntArrayNode
/*      */     implements Node.Builder.OfInt {
/*      */     IntFixedNodeBuilder(long param1Long) {
/* 1481 */       super(param1Long);
/* 1482 */       assert param1Long < 2147483639L;
/*      */     }
/*      */ 
/*      */     
/*      */     public Node.OfInt build() {
/* 1487 */       if (this.curSize < this.array.length) {
/* 1488 */         throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", new Object[] {
/* 1489 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/* 1492 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1497 */       if (param1Long != this.array.length) {
/* 1498 */         throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", new Object[] {
/* 1499 */                 Long.valueOf(param1Long), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/* 1502 */       this.curSize = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(int param1Int) {
/* 1507 */       if (this.curSize < this.array.length) {
/* 1508 */         this.array[this.curSize++] = param1Int;
/*      */       } else {
/* 1510 */         throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", new Object[] {
/* 1511 */                 Integer.valueOf(this.array.length)
/*      */               }));
/*      */       } 
/*      */     }
/*      */     
/*      */     public void end() {
/* 1517 */       if (this.curSize < this.array.length) {
/* 1518 */         throw new IllegalStateException(String.format("End size %d is less than fixed size %d", new Object[] {
/* 1519 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1525 */       return String.format("IntFixedNodeBuilder[%d][%s]", new Object[] {
/* 1526 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array)
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class LongFixedNodeBuilder
/*      */     extends LongArrayNode
/*      */     implements Node.Builder.OfLong {
/*      */     LongFixedNodeBuilder(long param1Long) {
/* 1535 */       super(param1Long);
/* 1536 */       assert param1Long < 2147483639L;
/*      */     }
/*      */ 
/*      */     
/*      */     public Node.OfLong build() {
/* 1541 */       if (this.curSize < this.array.length) {
/* 1542 */         throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", new Object[] {
/* 1543 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/* 1546 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1551 */       if (param1Long != this.array.length) {
/* 1552 */         throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", new Object[] {
/* 1553 */                 Long.valueOf(param1Long), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/* 1556 */       this.curSize = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(long param1Long) {
/* 1561 */       if (this.curSize < this.array.length) {
/* 1562 */         this.array[this.curSize++] = param1Long;
/*      */       } else {
/* 1564 */         throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", new Object[] {
/* 1565 */                 Integer.valueOf(this.array.length)
/*      */               }));
/*      */       } 
/*      */     }
/*      */     
/*      */     public void end() {
/* 1571 */       if (this.curSize < this.array.length) {
/* 1572 */         throw new IllegalStateException(String.format("End size %d is less than fixed size %d", new Object[] {
/* 1573 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1579 */       return String.format("LongFixedNodeBuilder[%d][%s]", new Object[] {
/* 1580 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array)
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class DoubleFixedNodeBuilder
/*      */     extends DoubleArrayNode
/*      */     implements Node.Builder.OfDouble {
/*      */     DoubleFixedNodeBuilder(long param1Long) {
/* 1589 */       super(param1Long);
/* 1590 */       assert param1Long < 2147483639L;
/*      */     }
/*      */ 
/*      */     
/*      */     public Node.OfDouble build() {
/* 1595 */       if (this.curSize < this.array.length) {
/* 1596 */         throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", new Object[] {
/* 1597 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/* 1600 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1605 */       if (param1Long != this.array.length) {
/* 1606 */         throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", new Object[] {
/* 1607 */                 Long.valueOf(param1Long), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/* 1610 */       this.curSize = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(double param1Double) {
/* 1615 */       if (this.curSize < this.array.length) {
/* 1616 */         this.array[this.curSize++] = param1Double;
/*      */       } else {
/* 1618 */         throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", new Object[] {
/* 1619 */                 Integer.valueOf(this.array.length)
/*      */               }));
/*      */       } 
/*      */     }
/*      */     
/*      */     public void end() {
/* 1625 */       if (this.curSize < this.array.length) {
/* 1626 */         throw new IllegalStateException(String.format("End size %d is less than fixed size %d", new Object[] {
/* 1627 */                 Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)
/*      */               }));
/*      */       }
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1633 */       return String.format("DoubleFixedNodeBuilder[%d][%s]", new Object[] {
/* 1634 */             Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array)
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class IntSpinedNodeBuilder
/*      */     extends SpinedBuffer.OfInt
/*      */     implements Node.OfInt, Node.Builder.OfInt
/*      */   {
/*      */     private boolean building = false;
/*      */     
/*      */     public Spliterator.OfInt spliterator() {
/* 1647 */       assert !this.building : "during building";
/* 1648 */       return super.spliterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer param1IntConsumer) {
/* 1653 */       assert !this.building : "during building";
/* 1654 */       super.forEach(param1IntConsumer);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1660 */       assert !this.building : "was already building";
/* 1661 */       this.building = true;
/* 1662 */       clear();
/* 1663 */       ensureCapacity(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(int param1Int) {
/* 1668 */       assert this.building : "not building";
/* 1669 */       super.accept(param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public void end() {
/* 1674 */       assert this.building : "was not building";
/* 1675 */       this.building = false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(int[] param1ArrayOfint, int param1Int) throws IndexOutOfBoundsException {
/* 1681 */       assert !this.building : "during building";
/* 1682 */       super.copyInto(param1ArrayOfint, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] asPrimitiveArray() {
/* 1687 */       assert !this.building : "during building";
/* 1688 */       return super.asPrimitiveArray();
/*      */     }
/*      */ 
/*      */     
/*      */     public Node.OfInt build() {
/* 1693 */       assert !this.building : "during building";
/* 1694 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class LongSpinedNodeBuilder
/*      */     extends SpinedBuffer.OfLong
/*      */     implements Node.OfLong, Node.Builder.OfLong
/*      */   {
/*      */     private boolean building = false;
/*      */ 
/*      */     
/*      */     public Spliterator.OfLong spliterator() {
/* 1707 */       assert !this.building : "during building";
/* 1708 */       return super.spliterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer param1LongConsumer) {
/* 1713 */       assert !this.building : "during building";
/* 1714 */       super.forEach(param1LongConsumer);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1720 */       assert !this.building : "was already building";
/* 1721 */       this.building = true;
/* 1722 */       clear();
/* 1723 */       ensureCapacity(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(long param1Long) {
/* 1728 */       assert this.building : "not building";
/* 1729 */       super.accept(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public void end() {
/* 1734 */       assert this.building : "was not building";
/* 1735 */       this.building = false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(long[] param1ArrayOflong, int param1Int) {
/* 1741 */       assert !this.building : "during building";
/* 1742 */       super.copyInto(param1ArrayOflong, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public long[] asPrimitiveArray() {
/* 1747 */       assert !this.building : "during building";
/* 1748 */       return super.asPrimitiveArray();
/*      */     }
/*      */ 
/*      */     
/*      */     public Node.OfLong build() {
/* 1753 */       assert !this.building : "during building";
/* 1754 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class DoubleSpinedNodeBuilder
/*      */     extends SpinedBuffer.OfDouble
/*      */     implements Node.OfDouble, Node.Builder.OfDouble
/*      */   {
/*      */     private boolean building = false;
/*      */ 
/*      */     
/*      */     public Spliterator.OfDouble spliterator() {
/* 1767 */       assert !this.building : "during building";
/* 1768 */       return super.spliterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer param1DoubleConsumer) {
/* 1773 */       assert !this.building : "during building";
/* 1774 */       super.forEach(param1DoubleConsumer);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void begin(long param1Long) {
/* 1780 */       assert !this.building : "was already building";
/* 1781 */       this.building = true;
/* 1782 */       clear();
/* 1783 */       ensureCapacity(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(double param1Double) {
/* 1788 */       assert this.building : "not building";
/* 1789 */       super.accept(param1Double);
/*      */     }
/*      */ 
/*      */     
/*      */     public void end() {
/* 1794 */       assert this.building : "was not building";
/* 1795 */       this.building = false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyInto(double[] param1ArrayOfdouble, int param1Int) {
/* 1801 */       assert !this.building : "during building";
/* 1802 */       super.copyInto(param1ArrayOfdouble, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public double[] asPrimitiveArray() {
/* 1807 */       assert !this.building : "during building";
/* 1808 */       return super.asPrimitiveArray();
/*      */     }
/*      */ 
/*      */     
/*      */     public Node.OfDouble build() {
/* 1813 */       assert !this.building : "during building";
/* 1814 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class SizedCollectorTask<P_IN, P_OUT, T_SINK extends Sink<P_OUT>, K extends SizedCollectorTask<P_IN, P_OUT, T_SINK, K>>
/*      */     extends CountedCompleter<Void>
/*      */     implements Sink<P_OUT>
/*      */   {
/*      */     protected final Spliterator<P_IN> spliterator;
/*      */     
/*      */     protected final PipelineHelper<P_OUT> helper;
/*      */     
/*      */     protected final long targetSize;
/*      */     
/*      */     protected long offset;
/*      */     
/*      */     protected long length;
/*      */     
/*      */     protected int index;
/*      */     protected int fence;
/*      */     
/*      */     SizedCollectorTask(Spliterator<P_IN> param1Spliterator, PipelineHelper<P_OUT> param1PipelineHelper, int param1Int) {
/* 1837 */       assert param1Spliterator.hasCharacteristics(16384);
/* 1838 */       this.spliterator = param1Spliterator;
/* 1839 */       this.helper = param1PipelineHelper;
/* 1840 */       this.targetSize = AbstractTask.suggestTargetSize(param1Spliterator.estimateSize());
/* 1841 */       this.offset = 0L;
/* 1842 */       this.length = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     SizedCollectorTask(K param1K, Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2, int param1Int) {
/* 1847 */       super((CountedCompleter<?>)param1K);
/* 1848 */       assert param1Spliterator.hasCharacteristics(16384);
/* 1849 */       this.spliterator = param1Spliterator;
/* 1850 */       this.helper = ((SizedCollectorTask)param1K).helper;
/* 1851 */       this.targetSize = ((SizedCollectorTask)param1K).targetSize;
/* 1852 */       this.offset = param1Long1;
/* 1853 */       this.length = param1Long2;
/*      */       
/* 1855 */       if (param1Long1 < 0L || param1Long2 < 0L || param1Long1 + param1Long2 - 1L >= param1Int) {
/* 1856 */         throw new IllegalArgumentException(
/* 1857 */             String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", new Object[] {
/* 1858 */                 Long.valueOf(param1Long1), Long.valueOf(param1Long1), Long.valueOf(param1Long2), Integer.valueOf(param1Int)
/*      */               }));
/*      */       }
/*      */     }
/*      */     
/*      */     public void compute() {
/* 1864 */       SizedCollectorTask sizedCollectorTask1 = this;
/* 1865 */       Spliterator<P_IN> spliterator1 = this.spliterator; Spliterator<P_IN> spliterator2;
/* 1866 */       while (spliterator1.estimateSize() > sizedCollectorTask1.targetSize && (
/* 1867 */         spliterator2 = spliterator1.trySplit()) != null) {
/* 1868 */         sizedCollectorTask1.setPendingCount(1);
/* 1869 */         long l = spliterator2.estimateSize();
/* 1870 */         sizedCollectorTask1.makeChild(spliterator2, sizedCollectorTask1.offset, l).fork();
/* 1871 */         sizedCollectorTask1 = (SizedCollectorTask)sizedCollectorTask1.makeChild(spliterator1, sizedCollectorTask1.offset + l, sizedCollectorTask1.length - l);
/*      */       } 
/*      */ 
/*      */       
/* 1875 */       assert sizedCollectorTask1.offset + sizedCollectorTask1.length < 2147483639L;
/*      */       
/* 1877 */       SizedCollectorTask sizedCollectorTask2 = sizedCollectorTask1;
/* 1878 */       sizedCollectorTask1.helper.wrapAndCopyInto(sizedCollectorTask2, spliterator1);
/* 1879 */       sizedCollectorTask1.propagateCompletion();
/*      */     }
/*      */ 
/*      */     
/*      */     abstract K makeChild(Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2);
/*      */     
/*      */     public void begin(long param1Long) {
/* 1886 */       if (param1Long > this.length) {
/* 1887 */         throw new IllegalStateException("size passed to Sink.begin exceeds array length");
/*      */       }
/*      */ 
/*      */       
/* 1891 */       this.index = (int)this.offset;
/* 1892 */       this.fence = this.index + (int)this.length;
/*      */     }
/*      */     
/*      */     static final class OfRef<P_IN, P_OUT>
/*      */       extends SizedCollectorTask<P_IN, P_OUT, Sink<P_OUT>, OfRef<P_IN, P_OUT>>
/*      */       implements Sink<P_OUT>
/*      */     {
/*      */       private final P_OUT[] array;
/*      */       
/*      */       OfRef(Spliterator<P_IN> param2Spliterator, PipelineHelper<P_OUT> param2PipelineHelper, P_OUT[] param2ArrayOfP_OUT) {
/* 1902 */         super(param2Spliterator, param2PipelineHelper, param2ArrayOfP_OUT.length);
/* 1903 */         this.array = param2ArrayOfP_OUT;
/*      */       }
/*      */ 
/*      */       
/*      */       OfRef(OfRef<P_IN, P_OUT> param2OfRef, Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 1908 */         super(param2OfRef, param2Spliterator, param2Long1, param2Long2, param2OfRef.array.length);
/* 1909 */         this.array = param2OfRef.array;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       OfRef<P_IN, P_OUT> makeChild(Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 1915 */         return new OfRef(this, param2Spliterator, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(P_OUT param2P_OUT) {
/* 1920 */         if (this.index >= this.fence) {
/* 1921 */           throw new IndexOutOfBoundsException(Integer.toString(this.index));
/*      */         }
/* 1923 */         this.array[this.index++] = param2P_OUT;
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfInt<P_IN>
/*      */       extends SizedCollectorTask<P_IN, Integer, Sink.OfInt, OfInt<P_IN>>
/*      */       implements Sink.OfInt
/*      */     {
/*      */       private final int[] array;
/*      */       
/*      */       OfInt(Spliterator<P_IN> param2Spliterator, PipelineHelper<Integer> param2PipelineHelper, int[] param2ArrayOfint) {
/* 1934 */         super(param2Spliterator, param2PipelineHelper, param2ArrayOfint.length);
/* 1935 */         this.array = param2ArrayOfint;
/*      */       }
/*      */ 
/*      */       
/*      */       OfInt(OfInt<P_IN> param2OfInt, Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 1940 */         super(param2OfInt, param2Spliterator, param2Long1, param2Long2, param2OfInt.array.length);
/* 1941 */         this.array = param2OfInt.array;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       OfInt<P_IN> makeChild(Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 1947 */         return new OfInt(this, param2Spliterator, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(int param2Int) {
/* 1952 */         if (this.index >= this.fence) {
/* 1953 */           throw new IndexOutOfBoundsException(Integer.toString(this.index));
/*      */         }
/* 1955 */         this.array[this.index++] = param2Int;
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong<P_IN>
/*      */       extends SizedCollectorTask<P_IN, Long, Sink.OfLong, OfLong<P_IN>>
/*      */       implements Sink.OfLong
/*      */     {
/*      */       private final long[] array;
/*      */       
/*      */       OfLong(Spliterator<P_IN> param2Spliterator, PipelineHelper<Long> param2PipelineHelper, long[] param2ArrayOflong) {
/* 1966 */         super(param2Spliterator, param2PipelineHelper, param2ArrayOflong.length);
/* 1967 */         this.array = param2ArrayOflong;
/*      */       }
/*      */ 
/*      */       
/*      */       OfLong(OfLong<P_IN> param2OfLong, Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 1972 */         super(param2OfLong, param2Spliterator, param2Long1, param2Long2, param2OfLong.array.length);
/* 1973 */         this.array = param2OfLong.array;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       OfLong<P_IN> makeChild(Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 1979 */         return new OfLong(this, param2Spliterator, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(long param2Long) {
/* 1984 */         if (this.index >= this.fence) {
/* 1985 */           throw new IndexOutOfBoundsException(Integer.toString(this.index));
/*      */         }
/* 1987 */         this.array[this.index++] = param2Long;
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble<P_IN>
/*      */       extends SizedCollectorTask<P_IN, Double, Sink.OfDouble, OfDouble<P_IN>>
/*      */       implements Sink.OfDouble
/*      */     {
/*      */       private final double[] array;
/*      */       
/*      */       OfDouble(Spliterator<P_IN> param2Spliterator, PipelineHelper<Double> param2PipelineHelper, double[] param2ArrayOfdouble) {
/* 1998 */         super(param2Spliterator, param2PipelineHelper, param2ArrayOfdouble.length);
/* 1999 */         this.array = param2ArrayOfdouble;
/*      */       }
/*      */ 
/*      */       
/*      */       OfDouble(OfDouble<P_IN> param2OfDouble, Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 2004 */         super(param2OfDouble, param2Spliterator, param2Long1, param2Long2, param2OfDouble.array.length);
/* 2005 */         this.array = param2OfDouble.array;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       OfDouble<P_IN> makeChild(Spliterator<P_IN> param2Spliterator, long param2Long1, long param2Long2) {
/* 2011 */         return new OfDouble(this, param2Spliterator, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       public void accept(double param2Double)
/*      */       {
/* 2016 */         if (this.index >= this.fence) {
/* 2017 */           throw new IndexOutOfBoundsException(Integer.toString(this.index));
/*      */         }
/* 2019 */         this.array[this.index++] = param2Double; } } } static final class OfRef<P_IN, P_OUT> extends SizedCollectorTask<P_IN, P_OUT, Sink<P_OUT>, SizedCollectorTask.OfRef<P_IN, P_OUT>> implements Sink<P_OUT> { private final P_OUT[] array; OfRef(Spliterator<P_IN> param1Spliterator, PipelineHelper<P_OUT> param1PipelineHelper, P_OUT[] param1ArrayOfP_OUT) { super(param1Spliterator, param1PipelineHelper, param1ArrayOfP_OUT.length); this.array = param1ArrayOfP_OUT; } OfRef(OfRef<P_IN, P_OUT> param1OfRef, Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) { super(param1OfRef, param1Spliterator, param1Long1, param1Long2, param1OfRef.array.length); this.array = param1OfRef.array; } OfRef<P_IN, P_OUT> makeChild(Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) { return new OfRef(this, param1Spliterator, param1Long1, param1Long2); } public void accept(P_OUT param1P_OUT) { if (this.index >= this.fence) throw new IndexOutOfBoundsException(Integer.toString(this.index));  this.array[this.index++] = param1P_OUT; } } static final class OfInt<P_IN> extends SizedCollectorTask<P_IN, Integer, Sink.OfInt, SizedCollectorTask.OfInt<P_IN>> implements Sink.OfInt { private final int[] array; OfInt(Spliterator<P_IN> param1Spliterator, PipelineHelper<Integer> param1PipelineHelper, int[] param1ArrayOfint) { super(param1Spliterator, param1PipelineHelper, param1ArrayOfint.length); this.array = param1ArrayOfint; } OfInt(OfInt<P_IN> param1OfInt, Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) { super(param1OfInt, param1Spliterator, param1Long1, param1Long2, param1OfInt.array.length); this.array = param1OfInt.array; } OfInt<P_IN> makeChild(Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) { return new OfInt(this, param1Spliterator, param1Long1, param1Long2); } public void accept(int param1Int) { if (this.index >= this.fence) throw new IndexOutOfBoundsException(Integer.toString(this.index));  this.array[this.index++] = param1Int; } } static final class OfLong<P_IN> extends SizedCollectorTask<P_IN, Long, Sink.OfLong, SizedCollectorTask.OfLong<P_IN>> implements Sink.OfLong { private final long[] array; OfLong(Spliterator<P_IN> param1Spliterator, PipelineHelper<Long> param1PipelineHelper, long[] param1ArrayOflong) { super(param1Spliterator, param1PipelineHelper, param1ArrayOflong.length); this.array = param1ArrayOflong; } OfLong(OfLong<P_IN> param1OfLong, Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) { super(param1OfLong, param1Spliterator, param1Long1, param1Long2, param1OfLong.array.length); this.array = param1OfLong.array; } OfLong<P_IN> makeChild(Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) { return new OfLong(this, param1Spliterator, param1Long1, param1Long2); } public void accept(long param1Long) { if (this.index >= this.fence) throw new IndexOutOfBoundsException(Integer.toString(this.index));  this.array[this.index++] = param1Long; } } static final class OfDouble<P_IN> extends SizedCollectorTask<P_IN, Double, Sink.OfDouble, SizedCollectorTask.OfDouble<P_IN>> implements Sink.OfDouble { private final double[] array; public void accept(double param1Double) { if (this.index >= this.fence) throw new IndexOutOfBoundsException(Integer.toString(this.index));  this.array[this.index++] = param1Double; }
/*      */      OfDouble(Spliterator<P_IN> param1Spliterator, PipelineHelper<Double> param1PipelineHelper, double[] param1ArrayOfdouble) {
/*      */       super(param1Spliterator, param1PipelineHelper, param1ArrayOfdouble.length);
/*      */       this.array = param1ArrayOfdouble;
/*      */     } OfDouble(OfDouble<P_IN> param1OfDouble, Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) {
/*      */       super(param1OfDouble, param1Spliterator, param1Long1, param1Long2, param1OfDouble.array.length);
/*      */       this.array = param1OfDouble.array;
/*      */     }
/*      */     OfDouble<P_IN> makeChild(Spliterator<P_IN> param1Spliterator, long param1Long1, long param1Long2) {
/*      */       return new OfDouble(this, param1Spliterator, param1Long1, param1Long2);
/*      */     } }
/*      */   private static abstract class ToArrayTask<T, T_NODE extends Node<T>, K extends ToArrayTask<T, T_NODE, K>> extends CountedCompleter<Void> { protected final T_NODE node; protected final int offset;
/*      */     ToArrayTask(T_NODE param1T_NODE, int param1Int) {
/* 2032 */       this.node = param1T_NODE;
/* 2033 */       this.offset = param1Int;
/*      */     }
/*      */     
/*      */     ToArrayTask(K param1K, T_NODE param1T_NODE, int param1Int) {
/* 2037 */       super((CountedCompleter<?>)param1K);
/* 2038 */       this.node = param1T_NODE;
/* 2039 */       this.offset = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     abstract void copyNodeToArray();
/*      */     
/*      */     abstract K makeChild(int param1Int1, int param1Int2);
/*      */     
/*      */     public void compute() {
/* 2048 */       ToArrayTask toArrayTask = this;
/*      */       while (true) {
/* 2050 */         if (toArrayTask.node.getChildCount() == 0) {
/* 2051 */           toArrayTask.copyNodeToArray();
/* 2052 */           toArrayTask.propagateCompletion();
/*      */           
/*      */           return;
/*      */         } 
/* 2056 */         toArrayTask.setPendingCount(toArrayTask.node.getChildCount() - 1);
/*      */         
/* 2058 */         int i = 0;
/* 2059 */         byte b = 0;
/* 2060 */         for (; b < toArrayTask.node.getChildCount() - 1; b++) {
/* 2061 */           Object object = toArrayTask.makeChild(b, toArrayTask.offset + i);
/* 2062 */           i = (int)(i + ((ToArrayTask)object).node.count());
/* 2063 */           object.fork();
/*      */         } 
/* 2065 */         toArrayTask = (ToArrayTask)toArrayTask.makeChild(b, toArrayTask.offset + i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static final class OfRef<T>
/*      */       extends ToArrayTask<T, Node<T>, OfRef<T>>
/*      */     {
/*      */       private final T[] array;
/*      */       
/*      */       private OfRef(Node<T> param2Node, T[] param2ArrayOfT, int param2Int) {
/* 2076 */         super(param2Node, param2Int);
/* 2077 */         this.array = param2ArrayOfT;
/*      */       }
/*      */       
/*      */       private OfRef(OfRef<T> param2OfRef, Node<T> param2Node, int param2Int) {
/* 2081 */         super(param2OfRef, param2Node, param2Int);
/* 2082 */         this.array = param2OfRef.array;
/*      */       }
/*      */ 
/*      */       
/*      */       OfRef<T> makeChild(int param2Int1, int param2Int2) {
/* 2087 */         return new OfRef(this, this.node.getChild(param2Int1), param2Int2);
/*      */       }
/*      */ 
/*      */       
/*      */       void copyNodeToArray() {
/* 2092 */         this.node.copyInto(this.array, this.offset);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private static class OfPrimitive<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_NODE extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>>
/*      */       extends ToArrayTask<T, T_NODE, OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>>
/*      */     {
/*      */       private final T_ARR array;
/*      */ 
/*      */       
/*      */       private OfPrimitive(T_NODE param2T_NODE, T_ARR param2T_ARR, int param2Int) {
/* 2104 */         super(param2T_NODE, param2Int);
/* 2105 */         this.array = param2T_ARR;
/*      */       }
/*      */       
/*      */       private OfPrimitive(OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE> param2OfPrimitive, T_NODE param2T_NODE, int param2Int) {
/* 2109 */         super(param2OfPrimitive, param2T_NODE, param2Int);
/* 2110 */         this.array = param2OfPrimitive.array;
/*      */       }
/*      */ 
/*      */       
/*      */       OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE> makeChild(int param2Int1, int param2Int2) {
/* 2115 */         return new OfPrimitive(this, (T_NODE)((Node.OfPrimitive)this.node).getChild(param2Int1), param2Int2);
/*      */       }
/*      */ 
/*      */       
/*      */       void copyNodeToArray() {
/* 2120 */         ((Node.OfPrimitive)this.node).copyInto(this.array, this.offset);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfInt
/*      */       extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt>
/*      */     {
/*      */       private OfInt(Node.OfInt param2OfInt, int[] param2ArrayOfint, int param2Int) {
/* 2128 */         super(param2OfInt, param2ArrayOfint, param2Int);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfLong
/*      */       extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong>
/*      */     {
/*      */       private OfLong(Node.OfLong param2OfLong, long[] param2ArrayOflong, int param2Int) {
/* 2136 */         super(param2OfLong, param2ArrayOflong, param2Int);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfDouble
/*      */       extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble>
/*      */     {
/*      */       private OfDouble(Node.OfDouble param2OfDouble, double[] param2ArrayOfdouble, int param2Int) {
/* 2144 */         super(param2OfDouble, param2ArrayOfdouble, param2Int); } } } private static final class OfRef<T> extends ToArrayTask<T, Node<T>, ToArrayTask.OfRef<T>> { private final T[] array; private OfRef(Node<T> param1Node, T[] param1ArrayOfT, int param1Int) { super(param1Node, param1Int); this.array = param1ArrayOfT; } private OfRef(OfRef<T> param1OfRef, Node<T> param1Node, int param1Int) { super(param1OfRef, param1Node, param1Int); this.array = param1OfRef.array; } OfRef<T> makeChild(int param1Int1, int param1Int2) { return new OfRef(this, this.node.getChild(param1Int1), param1Int2); } void copyNodeToArray() { this.node.copyInto(this.array, this.offset); } } private static final class OfInt extends ToArrayTask.OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt> { private OfInt(Node.OfInt param1OfInt, int[] param1ArrayOfint, int param1Int) { super(param1OfInt, param1ArrayOfint, param1Int); } } private static final class OfLong extends ToArrayTask.OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong> { private OfLong(Node.OfLong param1OfLong, long[] param1ArrayOflong, int param1Int) { super(param1OfLong, param1ArrayOflong, param1Int); } } private static final class OfDouble extends ToArrayTask.OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble> { private OfDouble(Node.OfDouble param1OfDouble, double[] param1ArrayOfdouble, int param1Int) { super(param1OfDouble, param1ArrayOfdouble, param1Int); }
/*      */      }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CollectorTask<P_IN, P_OUT, T_NODE extends Node<P_OUT>, T_BUILDER extends Node.Builder<P_OUT>>
/*      */     extends AbstractTask<P_IN, P_OUT, T_NODE, CollectorTask<P_IN, P_OUT, T_NODE, T_BUILDER>>
/*      */   {
/*      */     protected final PipelineHelper<P_OUT> helper;
/*      */     
/*      */     protected final LongFunction<T_BUILDER> builderFactory;
/*      */     
/*      */     protected final BinaryOperator<T_NODE> concFactory;
/*      */ 
/*      */     
/*      */     CollectorTask(PipelineHelper<P_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, LongFunction<T_BUILDER> param1LongFunction, BinaryOperator<T_NODE> param1BinaryOperator) {
/* 2160 */       super(param1PipelineHelper, param1Spliterator);
/* 2161 */       this.helper = param1PipelineHelper;
/* 2162 */       this.builderFactory = param1LongFunction;
/* 2163 */       this.concFactory = param1BinaryOperator;
/*      */     }
/*      */ 
/*      */     
/*      */     CollectorTask(CollectorTask<P_IN, P_OUT, T_NODE, T_BUILDER> param1CollectorTask, Spliterator<P_IN> param1Spliterator) {
/* 2168 */       super(param1CollectorTask, param1Spliterator);
/* 2169 */       this.helper = param1CollectorTask.helper;
/* 2170 */       this.builderFactory = param1CollectorTask.builderFactory;
/* 2171 */       this.concFactory = param1CollectorTask.concFactory;
/*      */     }
/*      */ 
/*      */     
/*      */     protected CollectorTask<P_IN, P_OUT, T_NODE, T_BUILDER> makeChild(Spliterator<P_IN> param1Spliterator) {
/* 2176 */       return new CollectorTask(this, param1Spliterator);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected T_NODE doLeaf() {
/* 2182 */       Node.Builder builder = (Node.Builder)this.builderFactory.apply(this.helper.exactOutputSizeIfKnown(this.spliterator));
/* 2183 */       return (T_NODE)((Node.Builder)this.helper.<P_IN, Node.Builder>wrapAndCopyInto(builder, this.spliterator)).build();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onCompletion(CountedCompleter<?> param1CountedCompleter) {
/* 2188 */       if (!isLeaf())
/* 2189 */         setLocalResult(this.concFactory.apply(this.leftChild.getLocalResult(), this.rightChild.getLocalResult())); 
/* 2190 */       super.onCompletion(param1CountedCompleter);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class OfRef<P_IN, P_OUT>
/*      */       extends CollectorTask<P_IN, P_OUT, Node<P_OUT>, Node.Builder<P_OUT>>
/*      */     {
/*      */       OfRef(PipelineHelper<P_OUT> param2PipelineHelper, IntFunction<P_OUT[]> param2IntFunction, Spliterator<P_IN> param2Spliterator) {
/* 2199 */         super(param2PipelineHelper, param2Spliterator, param2Long -> Nodes.builder(param2Long, param2IntFunction), ConcNode::new);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfInt<P_IN>
/*      */       extends CollectorTask<P_IN, Integer, Node.OfInt, Node.Builder.OfInt>
/*      */     {
/*      */       OfInt(PipelineHelper<Integer> param2PipelineHelper, Spliterator<P_IN> param2Spliterator) {
/* 2207 */         super(param2PipelineHelper, param2Spliterator, Nodes::intBuilder, OfInt::new);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfLong<P_IN>
/*      */       extends CollectorTask<P_IN, Long, Node.OfLong, Node.Builder.OfLong>
/*      */     {
/*      */       OfLong(PipelineHelper<Long> param2PipelineHelper, Spliterator<P_IN> param2Spliterator) {
/* 2215 */         super(param2PipelineHelper, param2Spliterator, Nodes::longBuilder, OfLong::new);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class OfDouble<P_IN>
/*      */       extends CollectorTask<P_IN, Double, Node.OfDouble, Node.Builder.OfDouble>
/*      */     {
/*      */       OfDouble(PipelineHelper<Double> param2PipelineHelper, Spliterator<P_IN> param2Spliterator) {
/* 2223 */         super(param2PipelineHelper, param2Spliterator, Nodes::doubleBuilder, OfDouble::new); } } } private static final class OfRef<P_IN, P_OUT> extends CollectorTask<P_IN, P_OUT, Node<P_OUT>, Node.Builder<P_OUT>> { OfRef(PipelineHelper<P_OUT> param1PipelineHelper, IntFunction<P_OUT[]> param1IntFunction, Spliterator<P_IN> param1Spliterator) { super(param1PipelineHelper, param1Spliterator, param1Long -> Nodes.builder(param1Long, param1IntFunction), ConcNode::new); } } private static final class OfInt<P_IN> extends CollectorTask<P_IN, Integer, Node.OfInt, Node.Builder.OfInt> { OfInt(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) { super(param1PipelineHelper, param1Spliterator, Nodes::intBuilder, OfInt::new); } } private static final class OfLong<P_IN> extends CollectorTask<P_IN, Long, Node.OfLong, Node.Builder.OfLong> { OfLong(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) { super(param1PipelineHelper, param1Spliterator, Nodes::longBuilder, OfLong::new); } } private static final class OfDouble<P_IN> extends CollectorTask<P_IN, Double, Node.OfDouble, Node.Builder.OfDouble> { OfDouble(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) { super(param1PipelineHelper, param1Spliterator, Nodes::doubleBuilder, OfDouble::new); }
/*      */      }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/Nodes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */