/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.DoubleSummaryStatistics;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.DoublePredicate;
/*     */ import java.util.function.DoubleToIntFunction;
/*     */ import java.util.function.DoubleToLongFunction;
/*     */ import java.util.function.DoubleUnaryOperator;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.ObjDoubleConsumer;
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
/*     */ abstract class DoublePipeline<E_IN>
/*     */   extends AbstractPipeline<E_IN, Double, DoubleStream>
/*     */   implements DoubleStream
/*     */ {
/*     */   DoublePipeline(Supplier<? extends Spliterator<Double>> paramSupplier, int paramInt, boolean paramBoolean) {
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
/*     */   DoublePipeline(Spliterator<Double> paramSpliterator, int paramInt, boolean paramBoolean) {
/*  79 */     super(paramSpliterator, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DoublePipeline(AbstractPipeline<?, E_IN, ?> paramAbstractPipeline, int paramInt) {
/*  90 */     super(paramAbstractPipeline, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DoubleConsumer adapt(Sink<Double> paramSink) {
/*  98 */     if (paramSink instanceof DoubleConsumer) {
/*  99 */       return (DoubleConsumer)paramSink;
/*     */     }
/* 101 */     if (Tripwire.ENABLED) {
/* 102 */       Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Sink<Double> s)");
/*     */     }
/* 104 */     return paramSink::accept;
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
/*     */   private static Spliterator.OfDouble adapt(Spliterator<Double> paramSpliterator) {
/* 116 */     if (paramSpliterator instanceof Spliterator.OfDouble) {
/* 117 */       return (Spliterator.OfDouble)paramSpliterator;
/*     */     }
/* 119 */     if (Tripwire.ENABLED) {
/* 120 */       Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Spliterator<Double> s)");
/*     */     }
/* 122 */     throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getOutputShape() {
/* 131 */     return StreamShape.DOUBLE_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<Double> evaluateToNode(PipelineHelper<Double> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<Double[]> paramIntFunction) {
/* 139 */     return Nodes.collectDouble(paramPipelineHelper, paramSpliterator, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<Double> wrap(PipelineHelper<Double> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean) {
/* 146 */     return new StreamSpliterators.DoubleWrappingSpliterator<>(paramPipelineHelper, paramSupplier, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final Spliterator.OfDouble lazySpliterator(Supplier<? extends Spliterator<Double>> paramSupplier) {
/* 152 */     return new StreamSpliterators.DelegatingSpliterator.OfDouble((Supplier)paramSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   final void forEachWithCancel(Spliterator<Double> paramSpliterator, Sink<Double> paramSink) {
/* 157 */     Spliterator.OfDouble ofDouble = adapt(paramSpliterator);
/* 158 */     DoubleConsumer doubleConsumer = adapt(paramSink); do {  }
/* 159 */     while (!paramSink.cancellationRequested() && ofDouble.tryAdvance(doubleConsumer));
/*     */   }
/*     */ 
/*     */   
/*     */   final Node.Builder<Double> makeNodeBuilder(long paramLong, IntFunction<Double[]> paramIntFunction) {
/* 164 */     return Nodes.doubleBuilder(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PrimitiveIterator.OfDouble iterator() {
/* 172 */     return Spliterators.iterator(spliterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public final Spliterator.OfDouble spliterator() {
/* 177 */     return adapt(super.spliterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Stream<Double> boxed() {
/* 184 */     return mapToObj(Double::valueOf);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream map(final DoubleUnaryOperator mapper) {
/* 189 */     Objects.requireNonNull(mapper);
/* 190 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 194 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 197 */                 this.downstream.accept(mapper.applyAsDouble(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <U> Stream<U> mapToObj(final DoubleFunction<? extends U> mapper) {
/* 206 */     Objects.requireNonNull(mapper);
/* 207 */     return new ReferencePipeline.StatelessOp<Double, U>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<U> param1Sink)
/*     */         {
/* 211 */           return new Sink.ChainedDouble(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 214 */                 this.downstream.accept(mapper.apply(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream mapToInt(final DoubleToIntFunction mapper) {
/* 223 */     Objects.requireNonNull(mapper);
/* 224 */     return new IntPipeline.StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 228 */           return new Sink.ChainedDouble<Integer>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 231 */                 this.downstream.accept(mapper.applyAsInt(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream mapToLong(final DoubleToLongFunction mapper) {
/* 240 */     Objects.requireNonNull(mapper);
/* 241 */     return new LongPipeline.StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 245 */           return new Sink.ChainedDouble<Long>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 248 */                 this.downstream.accept(mapper.applyAsLong(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream flatMap(final DoubleFunction<? extends DoubleStream> mapper) {
/* 257 */     Objects.requireNonNull(mapper);
/* 258 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 262 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               boolean cancellationRequestedCalled;
/*     */ 
/*     */               
/* 267 */               DoubleConsumer downstreamAsDouble = this.downstream::accept;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 271 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(double param2Double) {
/* 276 */                 try (DoubleStream null = (DoubleStream)mapper.apply(param2Double)) {
/* 277 */                   if (doubleStream != null) {
/* 278 */                     if (!this.cancellationRequestedCalled) {
/* 279 */                       doubleStream.sequential().forEach(this.downstreamAsDouble);
/*     */                     } else {
/*     */                       
/* 282 */                       Spliterator.OfDouble ofDouble = doubleStream.sequential().spliterator(); do {  }
/* 283 */                       while (!this.downstream.cancellationRequested() && ofDouble.tryAdvance(this.downstreamAsDouble));
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
/* 295 */                 this.cancellationRequestedCalled = true;
/* 296 */                 return this.downstream.cancellationRequested();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleStream unordered() {
/* 305 */     if (!isOrdered())
/* 306 */       return this; 
/* 307 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_ORDERED)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink) {
/* 310 */           return param1Sink;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream filter(final DoublePredicate predicate) {
/* 317 */     Objects.requireNonNull(predicate);
/* 318 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 322 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 325 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(double param2Double) {
/* 330 */                 if (predicate.test(param2Double)) {
/* 331 */                   this.downstream.accept(param2Double);
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final DoubleStream peek(final DoubleConsumer action) {
/* 340 */     Objects.requireNonNull(action);
/* 341 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, 0)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 345 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 348 */                 action.accept(param2Double);
/* 349 */                 this.downstream.accept(param2Double);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleStream limit(long paramLong) {
/* 360 */     if (paramLong < 0L)
/* 361 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 362 */     return SliceOps.makeDouble(this, 0L, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream skip(long paramLong) {
/* 367 */     if (paramLong < 0L)
/* 368 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 369 */     if (paramLong == 0L) {
/* 370 */       return this;
/*     */     }
/* 372 */     long l = -1L;
/* 373 */     return SliceOps.makeDouble(this, paramLong, l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleStream sorted() {
/* 379 */     return SortedOps.makeDouble(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleStream distinct() {
/* 386 */     return boxed().distinct().mapToDouble(paramDouble -> paramDouble.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(DoubleConsumer paramDoubleConsumer) {
/* 393 */     evaluate(ForEachOps.makeDouble(paramDoubleConsumer, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachOrdered(DoubleConsumer paramDoubleConsumer) {
/* 398 */     evaluate(ForEachOps.makeDouble(paramDoubleConsumer, true));
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
/*     */   public final double sum() {
/* 411 */     double[] arrayOfDouble = collect(() -> new double[3], (paramArrayOfdouble, paramDouble) -> {
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble, paramDouble);
/*     */           
/*     */           paramArrayOfdouble[2] = paramArrayOfdouble[2] + paramDouble;
/*     */         }(paramArrayOfdouble1, paramArrayOfdouble2) -> {
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[0]);
/*     */           
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[1]);
/*     */           
/*     */           paramArrayOfdouble1[2] = paramArrayOfdouble1[2] + paramArrayOfdouble2[2];
/*     */         });
/* 422 */     return Collectors.computeFinalSum(arrayOfDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble min() {
/* 427 */     return reduce(Math::min);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble max() {
/* 432 */     return reduce(Math::max);
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
/*     */   public final OptionalDouble average() {
/* 453 */     double[] arrayOfDouble = collect(() -> new double[4], (paramArrayOfdouble, paramDouble) -> {
/*     */           paramArrayOfdouble[2] = paramArrayOfdouble[2] + 1.0D;
/*     */           
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble, paramDouble);
/*     */           
/*     */           paramArrayOfdouble[3] = paramArrayOfdouble[3] + paramDouble;
/*     */         }(paramArrayOfdouble1, paramArrayOfdouble2) -> {
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[0]);
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[1]);
/*     */           paramArrayOfdouble1[2] = paramArrayOfdouble1[2] + paramArrayOfdouble2[2];
/*     */           paramArrayOfdouble1[3] = paramArrayOfdouble1[3] + paramArrayOfdouble2[3];
/*     */         });
/* 465 */     return (arrayOfDouble[2] > 0.0D) ? 
/* 466 */       OptionalDouble.of(Collectors.computeFinalSum(arrayOfDouble) / arrayOfDouble[2]) : 
/* 467 */       OptionalDouble.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long count() {
/* 472 */     return mapToLong(paramDouble -> 1L).sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleSummaryStatistics summaryStatistics() {
/* 477 */     return collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final double reduce(double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 483 */     return ((Double)evaluate(ReduceOps.makeDouble(paramDouble, paramDoubleBinaryOperator))).doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble reduce(DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 488 */     return (OptionalDouble)evaluate(ReduceOps.makeDouble(paramDoubleBinaryOperator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R> R collect(Supplier<R> paramSupplier, ObjDoubleConsumer<R> paramObjDoubleConsumer, BiConsumer<R, R> paramBiConsumer) {
/* 495 */     Objects.requireNonNull(paramBiConsumer);
/* 496 */     BinaryOperator<R> binaryOperator = (paramObject1, paramObject2) -> {
/*     */         paramBiConsumer.accept(paramObject1, paramObject2);
/*     */         return paramObject1;
/*     */       };
/* 500 */     return (R)evaluate(ReduceOps.makeDouble(paramSupplier, paramObjDoubleConsumer, binaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean anyMatch(DoublePredicate paramDoublePredicate) {
/* 505 */     return ((Boolean)evaluate(MatchOps.makeDouble(paramDoublePredicate, MatchOps.MatchKind.ANY))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean allMatch(DoublePredicate paramDoublePredicate) {
/* 510 */     return ((Boolean)evaluate(MatchOps.makeDouble(paramDoublePredicate, MatchOps.MatchKind.ALL))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean noneMatch(DoublePredicate paramDoublePredicate) {
/* 515 */     return ((Boolean)evaluate(MatchOps.makeDouble(paramDoublePredicate, MatchOps.MatchKind.NONE))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble findFirst() {
/* 520 */     return (OptionalDouble)evaluate(FindOps.makeDouble(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble findAny() {
/* 525 */     return (OptionalDouble)evaluate(FindOps.makeDouble(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public final double[] toArray() {
/* 530 */     return Nodes.flattenDouble((Node.OfDouble)evaluateToArrayNode(paramInt -> new Double[paramInt]))
/* 531 */       .asPrimitiveArray();
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
/*     */   static class Head<E_IN>
/*     */     extends DoublePipeline<E_IN>
/*     */   {
/*     */     Head(Supplier<? extends Spliterator<Double>> param1Supplier, int param1Int, boolean param1Boolean) {
/* 553 */       super(param1Supplier, param1Int, param1Boolean);
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
/*     */     Head(Spliterator<Double> param1Spliterator, int param1Int, boolean param1Boolean) {
/* 566 */       super(param1Spliterator, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 571 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     final Sink<E_IN> opWrapSink(int param1Int, Sink<Double> param1Sink) {
/* 576 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer param1DoubleConsumer) {
/* 583 */       if (!isParallel()) {
/* 584 */         DoublePipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1DoubleConsumer);
/*     */       } else {
/*     */         
/* 587 */         super.forEach(param1DoubleConsumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachOrdered(DoubleConsumer param1DoubleConsumer) {
/* 593 */       if (!isParallel()) {
/* 594 */         DoublePipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1DoubleConsumer);
/*     */       } else {
/*     */         
/* 597 */         super.forEachOrdered(param1DoubleConsumer);
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
/*     */   static abstract class StatelessOp<E_IN>
/*     */     extends DoublePipeline<E_IN>
/*     */   {
/*     */     StatelessOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 621 */       super(param1AbstractPipeline, param1Int);
/* 622 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 627 */       return false;
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
/*     */   static abstract class StatefulOp<E_IN>
/*     */     extends DoublePipeline<E_IN>
/*     */   {
/*     */     StatefulOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 649 */       super(param1AbstractPipeline, param1Int);
/* 650 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 655 */       return true;
/*     */     }
/*     */     
/*     */     abstract <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Double[]> param1IntFunction);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/DoublePipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */