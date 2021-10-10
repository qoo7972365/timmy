/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.IntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SortedOps
/*     */ {
/*     */   static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> paramAbstractPipeline) {
/*  51 */     return new OfRef<>(paramAbstractPipeline);
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
/*     */   static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> paramAbstractPipeline, Comparator<? super T> paramComparator) {
/*  63 */     return new OfRef<>(paramAbstractPipeline, paramComparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> IntStream makeInt(AbstractPipeline<?, Integer, ?> paramAbstractPipeline) {
/*  73 */     return new OfInt(paramAbstractPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> LongStream makeLong(AbstractPipeline<?, Long, ?> paramAbstractPipeline) {
/*  83 */     return new OfLong(paramAbstractPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> DoubleStream makeDouble(AbstractPipeline<?, Double, ?> paramAbstractPipeline) {
/*  93 */     return new OfDouble(paramAbstractPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfRef<T>
/*     */     extends ReferencePipeline.StatefulOp<T, T>
/*     */   {
/*     */     private final boolean isNaturalSort;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Comparator<? super T> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     OfRef(AbstractPipeline<?, T, ?> param1AbstractPipeline) {
/* 111 */       super(param1AbstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */       
/* 113 */       this.isNaturalSort = true;
/*     */ 
/*     */       
/* 116 */       Comparator<Comparable> comparator = Comparator.naturalOrder();
/* 117 */       this.comparator = (Comparator)comparator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     OfRef(AbstractPipeline<?, T, ?> param1AbstractPipeline, Comparator<? super T> param1Comparator) {
/* 126 */       super(param1AbstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_ORDERED | StreamOpFlag.NOT_SORTED);
/*     */       
/* 128 */       this.isNaturalSort = false;
/* 129 */       this.comparator = Objects.<Comparator<? super T>>requireNonNull(param1Comparator);
/*     */     }
/*     */ 
/*     */     
/*     */     public Sink<T> opWrapSink(int param1Int, Sink<T> param1Sink) {
/* 134 */       Objects.requireNonNull(param1Sink);
/*     */ 
/*     */ 
/*     */       
/* 138 */       if (StreamOpFlag.SORTED.isKnown(param1Int) && this.isNaturalSort)
/* 139 */         return param1Sink; 
/* 140 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 141 */         return new SortedOps.SizedRefSortingSink<>(param1Sink, this.comparator);
/*     */       }
/* 143 */       return new SortedOps.RefSortingSink<>(param1Sink, this.comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<T[]> param1IntFunction) {
/* 152 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags()) && this.isNaturalSort) {
/* 153 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */ 
/*     */       
/* 157 */       Object[] arrayOfObject = param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction).asArray((IntFunction)param1IntFunction);
/* 158 */       Arrays.parallelSort(arrayOfObject, (Comparator)this.comparator);
/* 159 */       return Nodes.node((T[])arrayOfObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfInt
/*     */     extends IntPipeline.StatefulOp<Integer>
/*     */   {
/*     */     OfInt(AbstractPipeline<?, Integer, ?> param1AbstractPipeline) {
/* 169 */       super(param1AbstractPipeline, StreamShape.INT_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink) {
/* 175 */       Objects.requireNonNull(param1Sink);
/*     */       
/* 177 */       if (StreamOpFlag.SORTED.isKnown(param1Int))
/* 178 */         return param1Sink; 
/* 179 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 180 */         return new SortedOps.SizedIntSortingSink(param1Sink);
/*     */       }
/* 182 */       return new SortedOps.IntSortingSink(param1Sink);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Integer[]> param1IntFunction) {
/* 189 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 190 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */       
/* 193 */       Node.OfInt ofInt = (Node.OfInt)param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction);
/*     */       
/* 195 */       int[] arrayOfInt = ofInt.asPrimitiveArray();
/* 196 */       Arrays.parallelSort(arrayOfInt);
/*     */       
/* 198 */       return Nodes.node(arrayOfInt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfLong
/*     */     extends LongPipeline.StatefulOp<Long>
/*     */   {
/*     */     OfLong(AbstractPipeline<?, Long, ?> param1AbstractPipeline) {
/* 208 */       super(param1AbstractPipeline, StreamShape.LONG_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink) {
/* 214 */       Objects.requireNonNull(param1Sink);
/*     */       
/* 216 */       if (StreamOpFlag.SORTED.isKnown(param1Int))
/* 217 */         return param1Sink; 
/* 218 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 219 */         return new SortedOps.SizedLongSortingSink(param1Sink);
/*     */       }
/* 221 */       return new SortedOps.LongSortingSink(param1Sink);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Long[]> param1IntFunction) {
/* 228 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 229 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */       
/* 232 */       Node.OfLong ofLong = (Node.OfLong)param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction);
/*     */       
/* 234 */       long[] arrayOfLong = ofLong.asPrimitiveArray();
/* 235 */       Arrays.parallelSort(arrayOfLong);
/*     */       
/* 237 */       return Nodes.node(arrayOfLong);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfDouble
/*     */     extends DoublePipeline.StatefulOp<Double>
/*     */   {
/*     */     OfDouble(AbstractPipeline<?, Double, ?> param1AbstractPipeline) {
/* 247 */       super(param1AbstractPipeline, StreamShape.DOUBLE_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink) {
/* 253 */       Objects.requireNonNull(param1Sink);
/*     */       
/* 255 */       if (StreamOpFlag.SORTED.isKnown(param1Int))
/* 256 */         return param1Sink; 
/* 257 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 258 */         return new SortedOps.SizedDoubleSortingSink(param1Sink);
/*     */       }
/* 260 */       return new SortedOps.DoubleSortingSink(param1Sink);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Double[]> param1IntFunction) {
/* 267 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 268 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */       
/* 271 */       Node.OfDouble ofDouble = (Node.OfDouble)param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction);
/*     */       
/* 273 */       double[] arrayOfDouble = ofDouble.asPrimitiveArray();
/* 274 */       Arrays.parallelSort(arrayOfDouble);
/*     */       
/* 276 */       return Nodes.node(arrayOfDouble);
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
/*     */   private static abstract class AbstractRefSortingSink<T>
/*     */     extends Sink.ChainedReference<T, T>
/*     */   {
/*     */     protected final Comparator<? super T> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean cancellationRequestedCalled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AbstractRefSortingSink(Sink<? super T> param1Sink, Comparator<? super T> param1Comparator) {
/* 311 */       super(param1Sink);
/* 312 */       this.comparator = param1Comparator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 327 */       this.cancellationRequestedCalled = true;
/* 328 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedRefSortingSink<T>
/*     */     extends AbstractRefSortingSink<T>
/*     */   {
/*     */     private T[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedRefSortingSink(Sink<? super T> param1Sink, Comparator<? super T> param1Comparator) {
/* 340 */       super(param1Sink, param1Comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 346 */       if (param1Long >= 2147483639L)
/* 347 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 348 */       this.array = (T[])new Object[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 353 */       Arrays.sort(this.array, 0, this.offset, this.comparator);
/* 354 */       this.downstream.begin(this.offset);
/* 355 */       if (!this.cancellationRequestedCalled) {
/* 356 */         for (byte b = 0; b < this.offset; b++) {
/* 357 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 360 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 361 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 363 */       this.downstream.end();
/* 364 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(T param1T) {
/* 369 */       this.array[this.offset++] = param1T;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class RefSortingSink<T>
/*     */     extends AbstractRefSortingSink<T>
/*     */   {
/*     */     private ArrayList<T> list;
/*     */     
/*     */     RefSortingSink(Sink<? super T> param1Sink, Comparator<? super T> param1Comparator) {
/* 380 */       super(param1Sink, param1Comparator);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 385 */       if (param1Long >= 2147483639L)
/* 386 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 387 */       this.list = (param1Long >= 0L) ? new ArrayList<>((int)param1Long) : new ArrayList<>();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void end() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield list : Ljava/util/ArrayList;
/*     */       //   4: aload_0
/*     */       //   5: getfield comparator : Ljava/util/Comparator;
/*     */       //   8: invokevirtual sort : (Ljava/util/Comparator;)V
/*     */       //   11: aload_0
/*     */       //   12: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   15: aload_0
/*     */       //   16: getfield list : Ljava/util/ArrayList;
/*     */       //   19: invokevirtual size : ()I
/*     */       //   22: i2l
/*     */       //   23: invokeinterface begin : (J)V
/*     */       //   28: aload_0
/*     */       //   29: getfield cancellationRequestedCalled : Z
/*     */       //   32: ifne -> 59
/*     */       //   35: aload_0
/*     */       //   36: getfield list : Ljava/util/ArrayList;
/*     */       //   39: aload_0
/*     */       //   40: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   43: dup
/*     */       //   44: invokevirtual getClass : ()Ljava/lang/Class;
/*     */       //   47: pop
/*     */       //   48: <illegal opcode> accept : (Ljava/util/stream/Sink;)Ljava/util/function/Consumer;
/*     */       //   53: invokevirtual forEach : (Ljava/util/function/Consumer;)V
/*     */       //   56: goto -> 111
/*     */       //   59: aload_0
/*     */       //   60: getfield list : Ljava/util/ArrayList;
/*     */       //   63: invokevirtual iterator : ()Ljava/util/Iterator;
/*     */       //   66: astore_1
/*     */       //   67: aload_1
/*     */       //   68: invokeinterface hasNext : ()Z
/*     */       //   73: ifeq -> 111
/*     */       //   76: aload_1
/*     */       //   77: invokeinterface next : ()Ljava/lang/Object;
/*     */       //   82: astore_2
/*     */       //   83: aload_0
/*     */       //   84: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   87: invokeinterface cancellationRequested : ()Z
/*     */       //   92: ifeq -> 98
/*     */       //   95: goto -> 111
/*     */       //   98: aload_0
/*     */       //   99: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   102: aload_2
/*     */       //   103: invokeinterface accept : (Ljava/lang/Object;)V
/*     */       //   108: goto -> 67
/*     */       //   111: aload_0
/*     */       //   112: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   115: invokeinterface end : ()V
/*     */       //   120: aload_0
/*     */       //   121: aconst_null
/*     */       //   122: putfield list : Ljava/util/ArrayList;
/*     */       //   125: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #392	-> 0
/*     */       //   #393	-> 11
/*     */       //   #394	-> 28
/*     */       //   #395	-> 35
/*     */       //   #398	-> 59
/*     */       //   #399	-> 83
/*     */       //   #400	-> 98
/*     */       //   #401	-> 108
/*     */       //   #403	-> 111
/*     */       //   #404	-> 120
/*     */       //   #405	-> 125
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(T param1T) {
/* 409 */       this.list.add(param1T);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class AbstractIntSortingSink
/*     */     extends Sink.ChainedInt<Integer>
/*     */   {
/*     */     protected boolean cancellationRequestedCalled;
/*     */ 
/*     */     
/*     */     AbstractIntSortingSink(Sink<? super Integer> param1Sink) {
/* 421 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 426 */       this.cancellationRequestedCalled = true;
/* 427 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedIntSortingSink
/*     */     extends AbstractIntSortingSink
/*     */   {
/*     */     private int[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedIntSortingSink(Sink<? super Integer> param1Sink) {
/* 439 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 444 */       if (param1Long >= 2147483639L)
/* 445 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 446 */       this.array = new int[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 451 */       Arrays.sort(this.array, 0, this.offset);
/* 452 */       this.downstream.begin(this.offset);
/* 453 */       if (!this.cancellationRequestedCalled) {
/* 454 */         for (byte b = 0; b < this.offset; b++) {
/* 455 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 458 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 459 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 461 */       this.downstream.end();
/* 462 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(int param1Int) {
/* 467 */       this.array[this.offset++] = param1Int;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class IntSortingSink
/*     */     extends AbstractIntSortingSink
/*     */   {
/*     */     private SpinedBuffer.OfInt b;
/*     */     
/*     */     IntSortingSink(Sink<? super Integer> param1Sink) {
/* 478 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 483 */       if (param1Long >= 2147483639L)
/* 484 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 485 */       this.b = (param1Long > 0L) ? new SpinedBuffer.OfInt((int)param1Long) : new SpinedBuffer.OfInt();
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 490 */       int[] arrayOfInt = this.b.asPrimitiveArray();
/* 491 */       Arrays.sort(arrayOfInt);
/* 492 */       this.downstream.begin(arrayOfInt.length);
/* 493 */       if (!this.cancellationRequestedCalled) {
/* 494 */         for (int i : arrayOfInt) {
/* 495 */           this.downstream.accept(i);
/*     */         }
/*     */       } else {
/* 498 */         for (int i : arrayOfInt) {
/* 499 */           if (this.downstream.cancellationRequested())
/* 500 */             break;  this.downstream.accept(i);
/*     */         } 
/*     */       } 
/* 503 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(int param1Int) {
/* 508 */       this.b.accept(param1Int);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class AbstractLongSortingSink
/*     */     extends Sink.ChainedLong<Long>
/*     */   {
/*     */     protected boolean cancellationRequestedCalled;
/*     */ 
/*     */     
/*     */     AbstractLongSortingSink(Sink<? super Long> param1Sink) {
/* 520 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 525 */       this.cancellationRequestedCalled = true;
/* 526 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedLongSortingSink
/*     */     extends AbstractLongSortingSink
/*     */   {
/*     */     private long[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedLongSortingSink(Sink<? super Long> param1Sink) {
/* 538 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 543 */       if (param1Long >= 2147483639L)
/* 544 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 545 */       this.array = new long[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 550 */       Arrays.sort(this.array, 0, this.offset);
/* 551 */       this.downstream.begin(this.offset);
/* 552 */       if (!this.cancellationRequestedCalled) {
/* 553 */         for (byte b = 0; b < this.offset; b++) {
/* 554 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 557 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 558 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 560 */       this.downstream.end();
/* 561 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(long param1Long) {
/* 566 */       this.array[this.offset++] = param1Long;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class LongSortingSink
/*     */     extends AbstractLongSortingSink
/*     */   {
/*     */     private SpinedBuffer.OfLong b;
/*     */     
/*     */     LongSortingSink(Sink<? super Long> param1Sink) {
/* 577 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 582 */       if (param1Long >= 2147483639L)
/* 583 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 584 */       this.b = (param1Long > 0L) ? new SpinedBuffer.OfLong((int)param1Long) : new SpinedBuffer.OfLong();
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 589 */       long[] arrayOfLong = this.b.asPrimitiveArray();
/* 590 */       Arrays.sort(arrayOfLong);
/* 591 */       this.downstream.begin(arrayOfLong.length);
/* 592 */       if (!this.cancellationRequestedCalled) {
/* 593 */         for (long l : arrayOfLong) {
/* 594 */           this.downstream.accept(l);
/*     */         }
/*     */       } else {
/* 597 */         for (long l : arrayOfLong) {
/* 598 */           if (this.downstream.cancellationRequested())
/* 599 */             break;  this.downstream.accept(l);
/*     */         } 
/*     */       } 
/* 602 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(long param1Long) {
/* 607 */       this.b.accept(param1Long);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class AbstractDoubleSortingSink
/*     */     extends Sink.ChainedDouble<Double>
/*     */   {
/*     */     protected boolean cancellationRequestedCalled;
/*     */ 
/*     */     
/*     */     AbstractDoubleSortingSink(Sink<? super Double> param1Sink) {
/* 619 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 624 */       this.cancellationRequestedCalled = true;
/* 625 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedDoubleSortingSink
/*     */     extends AbstractDoubleSortingSink
/*     */   {
/*     */     private double[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedDoubleSortingSink(Sink<? super Double> param1Sink) {
/* 637 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 642 */       if (param1Long >= 2147483639L)
/* 643 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 644 */       this.array = new double[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 649 */       Arrays.sort(this.array, 0, this.offset);
/* 650 */       this.downstream.begin(this.offset);
/* 651 */       if (!this.cancellationRequestedCalled) {
/* 652 */         for (byte b = 0; b < this.offset; b++) {
/* 653 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 656 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 657 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 659 */       this.downstream.end();
/* 660 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(double param1Double) {
/* 665 */       this.array[this.offset++] = param1Double;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class DoubleSortingSink
/*     */     extends AbstractDoubleSortingSink
/*     */   {
/*     */     private SpinedBuffer.OfDouble b;
/*     */     
/*     */     DoubleSortingSink(Sink<? super Double> param1Sink) {
/* 676 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 681 */       if (param1Long >= 2147483639L)
/* 682 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 683 */       this.b = (param1Long > 0L) ? new SpinedBuffer.OfDouble((int)param1Long) : new SpinedBuffer.OfDouble();
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 688 */       double[] arrayOfDouble = this.b.asPrimitiveArray();
/* 689 */       Arrays.sort(arrayOfDouble);
/* 690 */       this.downstream.begin(arrayOfDouble.length);
/* 691 */       if (!this.cancellationRequestedCalled) {
/* 692 */         for (double d : arrayOfDouble) {
/* 693 */           this.downstream.accept(d);
/*     */         }
/*     */       } else {
/* 696 */         for (double d : arrayOfDouble) {
/* 697 */           if (this.downstream.cancellationRequested())
/* 698 */             break;  this.downstream.accept(d);
/*     */         } 
/*     */       } 
/* 701 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(double param1Double) {
/* 706 */       this.b.accept(param1Double);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/SortedOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */