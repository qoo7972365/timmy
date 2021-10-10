/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.IntSummaryStatistics;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.function.IntToDoubleFunction;
/*     */ import java.util.function.IntToLongFunction;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ import java.util.function.ObjIntConsumer;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class IntPipeline<E_IN>
/*     */   extends AbstractPipeline<E_IN, Integer, IntStream>
/*     */   implements IntStream
/*     */ {
/*     */   IntPipeline(Supplier<? extends Spliterator<Integer>> paramSupplier, int paramInt, boolean paramBoolean) {
/*  67 */     super(paramSupplier, paramInt, paramBoolean);
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
/*     */   IntPipeline(Spliterator<Integer> paramSpliterator, int paramInt, boolean paramBoolean) {
/*  80 */     super(paramSpliterator, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IntPipeline(AbstractPipeline<?, E_IN, ?> paramAbstractPipeline, int paramInt) {
/*  91 */     super(paramAbstractPipeline, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntConsumer adapt(Sink<Integer> paramSink) {
/*  99 */     if (paramSink instanceof IntConsumer) {
/* 100 */       return (IntConsumer)paramSink;
/*     */     }
/*     */     
/* 103 */     if (Tripwire.ENABLED) {
/* 104 */       Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Sink<Integer> s)");
/*     */     }
/* 106 */     return paramSink::accept;
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
/*     */   private static Spliterator.OfInt adapt(Spliterator<Integer> paramSpliterator) {
/* 118 */     if (paramSpliterator instanceof Spliterator.OfInt) {
/* 119 */       return (Spliterator.OfInt)paramSpliterator;
/*     */     }
/*     */     
/* 122 */     if (Tripwire.ENABLED) {
/* 123 */       Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Spliterator<Integer> s)");
/*     */     }
/* 125 */     throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getOutputShape() {
/* 134 */     return StreamShape.INT_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<Integer> evaluateToNode(PipelineHelper<Integer> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<Integer[]> paramIntFunction) {
/* 142 */     return Nodes.collectInt(paramPipelineHelper, paramSpliterator, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<Integer> wrap(PipelineHelper<Integer> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean) {
/* 149 */     return new StreamSpliterators.IntWrappingSpliterator<>(paramPipelineHelper, paramSupplier, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final Spliterator.OfInt lazySpliterator(Supplier<? extends Spliterator<Integer>> paramSupplier) {
/* 155 */     return new StreamSpliterators.DelegatingSpliterator.OfInt((Supplier)paramSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   final void forEachWithCancel(Spliterator<Integer> paramSpliterator, Sink<Integer> paramSink) {
/* 160 */     Spliterator.OfInt ofInt = adapt(paramSpliterator);
/* 161 */     IntConsumer intConsumer = adapt(paramSink); do {  }
/* 162 */     while (!paramSink.cancellationRequested() && ofInt.tryAdvance(intConsumer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final Node.Builder<Integer> makeNodeBuilder(long paramLong, IntFunction<Integer[]> paramIntFunction) {
/* 168 */     return Nodes.intBuilder(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PrimitiveIterator.OfInt iterator() {
/* 176 */     return Spliterators.iterator(spliterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public final Spliterator.OfInt spliterator() {
/* 181 */     return adapt(super.spliterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LongStream asLongStream() {
/* 188 */     return new LongPipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 192 */           return new Sink.ChainedInt<Long>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 195 */                 this.downstream.accept(param2Int);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream asDoubleStream() {
/* 204 */     return new DoublePipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 208 */           return new Sink.ChainedInt<Double>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 211 */                 this.downstream.accept(param2Int);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<Integer> boxed() {
/* 220 */     return mapToObj(Integer::valueOf);
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream map(final IntUnaryOperator mapper) {
/* 225 */     Objects.requireNonNull(mapper);
/* 226 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 230 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 233 */                 this.downstream.accept(mapper.applyAsInt(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <U> Stream<U> mapToObj(final IntFunction<? extends U> mapper) {
/* 242 */     Objects.requireNonNull(mapper);
/* 243 */     return new ReferencePipeline.StatelessOp<Integer, U>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<U> param1Sink)
/*     */         {
/* 247 */           return new Sink.ChainedInt(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 250 */                 this.downstream.accept(mapper.apply(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream mapToLong(final IntToLongFunction mapper) {
/* 259 */     Objects.requireNonNull(mapper);
/* 260 */     return new LongPipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 264 */           return new Sink.ChainedInt<Long>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 267 */                 this.downstream.accept(mapper.applyAsLong(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream mapToDouble(final IntToDoubleFunction mapper) {
/* 276 */     Objects.requireNonNull(mapper);
/* 277 */     return new DoublePipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 281 */           return new Sink.ChainedInt<Double>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 284 */                 this.downstream.accept(mapper.applyAsDouble(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream flatMap(final IntFunction<? extends IntStream> mapper) {
/* 293 */     Objects.requireNonNull(mapper);
/* 294 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 298 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               boolean cancellationRequestedCalled;
/*     */ 
/*     */               
/* 303 */               IntConsumer downstreamAsInt = this.downstream::accept;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 307 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(int param2Int) {
/* 312 */                 try (IntStream null = (IntStream)mapper.apply(param2Int)) {
/* 313 */                   if (intStream != null) {
/* 314 */                     if (!this.cancellationRequestedCalled) {
/* 315 */                       intStream.sequential().forEach(this.downstreamAsInt);
/*     */                     } else {
/*     */                       
/* 318 */                       Spliterator.OfInt ofInt = intStream.sequential().spliterator(); do {  }
/* 319 */                       while (!this.downstream.cancellationRequested() && ofInt.tryAdvance(this.downstreamAsInt));
/*     */                     } 
/*     */                   }
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 331 */                 this.cancellationRequestedCalled = true;
/* 332 */                 return this.downstream.cancellationRequested();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public IntStream unordered() {
/* 341 */     if (!isOrdered())
/* 342 */       return this; 
/* 343 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_ORDERED)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink) {
/* 346 */           return param1Sink;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream filter(final IntPredicate predicate) {
/* 353 */     Objects.requireNonNull(predicate);
/* 354 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 358 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 361 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(int param2Int) {
/* 366 */                 if (predicate.test(param2Int)) {
/* 367 */                   this.downstream.accept(param2Int);
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final IntStream peek(final IntConsumer action) {
/* 376 */     Objects.requireNonNull(action);
/* 377 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, 0)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 381 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 384 */                 action.accept(param2Int);
/* 385 */                 this.downstream.accept(param2Int);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final IntStream limit(long paramLong) {
/* 396 */     if (paramLong < 0L)
/* 397 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 398 */     return SliceOps.makeInt(this, 0L, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream skip(long paramLong) {
/* 403 */     if (paramLong < 0L)
/* 404 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 405 */     if (paramLong == 0L) {
/* 406 */       return this;
/*     */     }
/* 408 */     return SliceOps.makeInt(this, paramLong, -1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream sorted() {
/* 413 */     return SortedOps.makeInt(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final IntStream distinct() {
/* 420 */     return boxed().distinct().mapToInt(paramInteger -> paramInteger.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(IntConsumer paramIntConsumer) {
/* 427 */     evaluate(ForEachOps.makeInt(paramIntConsumer, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachOrdered(IntConsumer paramIntConsumer) {
/* 432 */     evaluate(ForEachOps.makeInt(paramIntConsumer, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int sum() {
/* 437 */     return reduce(0, Integer::sum);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt min() {
/* 442 */     return reduce(Math::min);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt max() {
/* 447 */     return reduce(Math::max);
/*     */   }
/*     */ 
/*     */   
/*     */   public final long count() {
/* 452 */     return mapToLong(paramInt -> 1L).sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble average() {
/* 457 */     long[] arrayOfLong = collect(() -> new long[2], (paramArrayOflong, paramInt) -> {
/*     */           paramArrayOflong[0] = paramArrayOflong[0] + 1L;
/*     */           
/*     */           paramArrayOflong[1] = paramArrayOflong[1] + paramInt;
/*     */         }(paramArrayOflong1, paramArrayOflong2) -> {
/*     */           paramArrayOflong1[0] = paramArrayOflong1[0] + paramArrayOflong2[0];
/*     */           
/*     */           paramArrayOflong1[1] = paramArrayOflong1[1] + paramArrayOflong2[1];
/*     */         });
/* 466 */     return (arrayOfLong[0] > 0L) ? 
/* 467 */       OptionalDouble.of(arrayOfLong[1] / arrayOfLong[0]) : 
/* 468 */       OptionalDouble.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntSummaryStatistics summaryStatistics() {
/* 473 */     return collect(IntSummaryStatistics::new, IntSummaryStatistics::accept, IntSummaryStatistics::combine);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int reduce(int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/* 479 */     return ((Integer)evaluate(ReduceOps.makeInt(paramInt, paramIntBinaryOperator))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt reduce(IntBinaryOperator paramIntBinaryOperator) {
/* 484 */     return (OptionalInt)evaluate(ReduceOps.makeInt(paramIntBinaryOperator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R> R collect(Supplier<R> paramSupplier, ObjIntConsumer<R> paramObjIntConsumer, BiConsumer<R, R> paramBiConsumer) {
/* 491 */     Objects.requireNonNull(paramBiConsumer);
/* 492 */     BinaryOperator<R> binaryOperator = (paramObject1, paramObject2) -> {
/*     */         paramBiConsumer.accept(paramObject1, paramObject2);
/*     */         return paramObject1;
/*     */       };
/* 496 */     return (R)evaluate(ReduceOps.makeInt(paramSupplier, paramObjIntConsumer, binaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean anyMatch(IntPredicate paramIntPredicate) {
/* 501 */     return ((Boolean)evaluate(MatchOps.makeInt(paramIntPredicate, MatchOps.MatchKind.ANY))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean allMatch(IntPredicate paramIntPredicate) {
/* 506 */     return ((Boolean)evaluate(MatchOps.makeInt(paramIntPredicate, MatchOps.MatchKind.ALL))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean noneMatch(IntPredicate paramIntPredicate) {
/* 511 */     return ((Boolean)evaluate(MatchOps.makeInt(paramIntPredicate, MatchOps.MatchKind.NONE))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt findFirst() {
/* 516 */     return (OptionalInt)evaluate(FindOps.makeInt(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt findAny() {
/* 521 */     return (OptionalInt)evaluate(FindOps.makeInt(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int[] toArray() {
/* 526 */     return Nodes.flattenInt((Node.OfInt)evaluateToArrayNode(paramInt -> new Integer[paramInt]))
/* 527 */       .asPrimitiveArray();
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
/*     */   static class Head<E_IN>
/*     */     extends IntPipeline<E_IN>
/*     */   {
/*     */     Head(Supplier<? extends Spliterator<Integer>> param1Supplier, int param1Int, boolean param1Boolean) {
/* 550 */       super(param1Supplier, param1Int, param1Boolean);
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
/*     */     Head(Spliterator<Integer> param1Spliterator, int param1Int, boolean param1Boolean) {
/* 563 */       super(param1Spliterator, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 568 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     final Sink<E_IN> opWrapSink(int param1Int, Sink<Integer> param1Sink) {
/* 573 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(IntConsumer param1IntConsumer) {
/* 580 */       if (!isParallel()) {
/* 581 */         IntPipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1IntConsumer);
/*     */       } else {
/*     */         
/* 584 */         super.forEach(param1IntConsumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachOrdered(IntConsumer param1IntConsumer) {
/* 590 */       if (!isParallel()) {
/* 591 */         IntPipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1IntConsumer);
/*     */       } else {
/*     */         
/* 594 */         super.forEachOrdered(param1IntConsumer);
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
/*     */   static abstract class StatelessOp<E_IN>
/*     */     extends IntPipeline<E_IN>
/*     */   {
/*     */     StatelessOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 616 */       super(param1AbstractPipeline, param1Int);
/* 617 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 622 */       return false;
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
/*     */   static abstract class StatefulOp<E_IN>
/*     */     extends IntPipeline<E_IN>
/*     */   {
/*     */     StatefulOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 643 */       super(param1AbstractPipeline, param1Int);
/* 644 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 649 */       return true;
/*     */     }
/*     */     
/*     */     abstract <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Integer[]> param1IntFunction);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/IntPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */