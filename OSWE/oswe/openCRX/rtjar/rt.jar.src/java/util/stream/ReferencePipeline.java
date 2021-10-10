/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.function.ToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ReferencePipeline<P_IN, P_OUT>
/*     */   extends AbstractPipeline<P_IN, P_OUT, Stream<P_OUT>>
/*     */   implements Stream<P_OUT>
/*     */ {
/*     */   ReferencePipeline(Supplier<? extends Spliterator<?>> paramSupplier, int paramInt, boolean paramBoolean) {
/*  71 */     super(paramSupplier, paramInt, paramBoolean);
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
/*     */   ReferencePipeline(Spliterator<?> paramSpliterator, int paramInt, boolean paramBoolean) {
/*  84 */     super(paramSpliterator, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReferencePipeline(AbstractPipeline<?, P_IN, ?> paramAbstractPipeline, int paramInt) {
/*  94 */     super(paramAbstractPipeline, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getOutputShape() {
/* 101 */     return StreamShape.REFERENCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<P_OUT> evaluateToNode(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<P_OUT[]> paramIntFunction) {
/* 109 */     return Nodes.collect(paramPipelineHelper, paramSpliterator, paramBoolean, paramIntFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<P_OUT> wrap(PipelineHelper<P_OUT> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean) {
/* 116 */     return new StreamSpliterators.WrappingSpliterator<>(paramPipelineHelper, paramSupplier, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   final Spliterator<P_OUT> lazySpliterator(Supplier<? extends Spliterator<P_OUT>> paramSupplier) {
/* 121 */     return new StreamSpliterators.DelegatingSpliterator<>(paramSupplier);
/*     */   }
/*     */   final void forEachWithCancel(Spliterator<P_OUT> paramSpliterator, Sink<P_OUT> paramSink) {
/*     */     do {
/*     */     
/* 126 */     } while (!paramSink.cancellationRequested() && paramSpliterator.tryAdvance(paramSink));
/*     */   }
/*     */ 
/*     */   
/*     */   final Node.Builder<P_OUT> makeNodeBuilder(long paramLong, IntFunction<P_OUT[]> paramIntFunction) {
/* 131 */     return Nodes.builder(paramLong, paramIntFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Iterator<P_OUT> iterator() {
/* 139 */     return Spliterators.iterator(spliterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<P_OUT> unordered() {
/* 149 */     if (!isOrdered())
/* 150 */       return this; 
/* 151 */     return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_ORDERED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<P_OUT> param1Sink) {
/* 154 */           return param1Sink;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> filter(final Predicate<? super P_OUT> predicate) {
/* 161 */     Objects.requireNonNull(predicate);
/* 162 */     return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<P_OUT> param1Sink)
/*     */         {
/* 166 */           return new Sink.ChainedReference<P_OUT, P_OUT>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 169 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 174 */                 if (predicate.test(param2P_OUT)) {
/* 175 */                   this.downstream.accept(param2P_OUT);
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <R> Stream<R> map(final Function<? super P_OUT, ? extends R> mapper) {
/* 185 */     Objects.requireNonNull(mapper);
/* 186 */     return new StatelessOp<P_OUT, R>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<R> param1Sink)
/*     */         {
/* 190 */           return new Sink.ChainedReference(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 193 */                 this.downstream.accept(mapper.apply(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream mapToInt(final ToIntFunction<? super P_OUT> mapper) {
/* 202 */     Objects.requireNonNull(mapper);
/* 203 */     return new IntPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 207 */           return new Sink.ChainedReference<P_OUT, Integer>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 210 */                 this.downstream.accept(mapper.applyAsInt(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream mapToLong(final ToLongFunction<? super P_OUT> mapper) {
/* 219 */     Objects.requireNonNull(mapper);
/* 220 */     return new LongPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 224 */           return new Sink.ChainedReference<P_OUT, Long>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 227 */                 this.downstream.accept(mapper.applyAsLong(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream mapToDouble(final ToDoubleFunction<? super P_OUT> mapper) {
/* 236 */     Objects.requireNonNull(mapper);
/* 237 */     return new DoublePipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 241 */           return new Sink.ChainedReference<P_OUT, Double>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 244 */                 this.downstream.accept(mapper.applyAsDouble(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <R> Stream<R> flatMap(final Function<? super P_OUT, ? extends Stream<? extends R>> mapper) {
/* 253 */     Objects.requireNonNull(mapper);
/* 254 */     return new StatelessOp<P_OUT, R>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<R> param1Sink)
/*     */         {
/* 258 */           return new Sink.ChainedReference(param1Sink)
/*     */             {
/*     */               boolean cancellationRequestedCalled;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 264 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 269 */                 try (Stream<R> null = (Stream)mapper.apply(param2P_OUT)) {
/* 270 */                   if (stream != null) {
/* 271 */                     if (!this.cancellationRequestedCalled) {
/* 272 */                       stream.sequential().forEach(this.downstream);
/*     */                     } else {
/*     */                       
/* 275 */                       Spliterator<R> spliterator = stream.sequential().spliterator(); do {  }
/* 276 */                       while (!this.downstream.cancellationRequested() && spliterator.tryAdvance(this.downstream));
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
/* 288 */                 this.cancellationRequestedCalled = true;
/* 289 */                 return this.downstream.cancellationRequested();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream flatMapToInt(final Function<? super P_OUT, ? extends IntStream> mapper) {
/* 298 */     Objects.requireNonNull(mapper);
/* 299 */     return new IntPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 303 */           return new Sink.ChainedReference<P_OUT, Integer>(param1Sink)
/*     */             {
/*     */               boolean cancellationRequestedCalled;
/*     */ 
/*     */               
/* 308 */               IntConsumer downstreamAsInt = this.downstream::accept;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 312 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 317 */                 try (IntStream null = (IntStream)mapper.apply(param2P_OUT)) {
/* 318 */                   if (intStream != null) {
/* 319 */                     if (!this.cancellationRequestedCalled) {
/* 320 */                       intStream.sequential().forEach(this.downstreamAsInt);
/*     */                     } else {
/*     */                       
/* 323 */                       Spliterator.OfInt ofInt = intStream.sequential().spliterator(); do {  }
/* 324 */                       while (!this.downstream.cancellationRequested() && ofInt.tryAdvance(this.downstreamAsInt));
/*     */                     } 
/*     */                   }
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 332 */                 this.cancellationRequestedCalled = true;
/* 333 */                 return this.downstream.cancellationRequested();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream flatMapToDouble(final Function<? super P_OUT, ? extends DoubleStream> mapper) {
/* 342 */     Objects.requireNonNull(mapper);
/* 343 */     return new DoublePipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 347 */           return new Sink.ChainedReference<P_OUT, Double>(param1Sink)
/*     */             {
/*     */               boolean cancellationRequestedCalled;
/*     */ 
/*     */               
/* 352 */               DoubleConsumer downstreamAsDouble = this.downstream::accept;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 356 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 361 */                 try (DoubleStream null = (DoubleStream)mapper.apply(param2P_OUT)) {
/* 362 */                   if (doubleStream != null) {
/* 363 */                     if (!this.cancellationRequestedCalled) {
/* 364 */                       doubleStream.sequential().forEach(this.downstreamAsDouble);
/*     */                     } else {
/*     */                       
/* 367 */                       Spliterator.OfDouble ofDouble = doubleStream.sequential().spliterator(); do {  }
/* 368 */                       while (!this.downstream.cancellationRequested() && ofDouble.tryAdvance(this.downstreamAsDouble));
/*     */                     } 
/*     */                   }
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 376 */                 this.cancellationRequestedCalled = true;
/* 377 */                 return this.downstream.cancellationRequested();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream flatMapToLong(final Function<? super P_OUT, ? extends LongStream> mapper) {
/* 386 */     Objects.requireNonNull(mapper);
/*     */     
/* 388 */     return new LongPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 392 */           return new Sink.ChainedReference<P_OUT, Long>(param1Sink)
/*     */             {
/*     */               boolean cancellationRequestedCalled;
/*     */ 
/*     */               
/* 397 */               LongConsumer downstreamAsLong = this.downstream::accept;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 401 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 406 */                 try (LongStream null = (LongStream)mapper.apply(param2P_OUT)) {
/* 407 */                   if (longStream != null) {
/* 408 */                     if (!this.cancellationRequestedCalled) {
/* 409 */                       longStream.sequential().forEach(this.downstreamAsLong);
/*     */                     } else {
/*     */                       
/* 412 */                       Spliterator.OfLong ofLong = longStream.sequential().spliterator(); do {  }
/* 413 */                       while (!this.downstream.cancellationRequested() && ofLong.tryAdvance(this.downstreamAsLong));
/*     */                     } 
/*     */                   }
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 421 */                 this.cancellationRequestedCalled = true;
/* 422 */                 return this.downstream.cancellationRequested();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> peek(final Consumer<? super P_OUT> action) {
/* 431 */     Objects.requireNonNull(action);
/* 432 */     return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, 0)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<P_OUT> param1Sink)
/*     */         {
/* 436 */           return new Sink.ChainedReference<P_OUT, P_OUT>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 439 */                 action.accept(param2P_OUT);
/* 440 */                 this.downstream.accept(param2P_OUT);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> distinct() {
/* 451 */     return DistinctOps.makeRef(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> sorted() {
/* 456 */     return SortedOps.makeRef(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> sorted(Comparator<? super P_OUT> paramComparator) {
/* 461 */     return SortedOps.makeRef(this, paramComparator);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> limit(long paramLong) {
/* 466 */     if (paramLong < 0L)
/* 467 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 468 */     return SliceOps.makeRef(this, 0L, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> skip(long paramLong) {
/* 473 */     if (paramLong < 0L)
/* 474 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 475 */     if (paramLong == 0L) {
/* 476 */       return this;
/*     */     }
/* 478 */     return SliceOps.makeRef(this, paramLong, -1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super P_OUT> paramConsumer) {
/* 485 */     evaluate(ForEachOps.makeRef(paramConsumer, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachOrdered(Consumer<? super P_OUT> paramConsumer) {
/* 490 */     evaluate(ForEachOps.makeRef(paramConsumer, true));
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
/*     */   public final <A> A[] toArray(IntFunction<A[]> paramIntFunction) {
/* 504 */     IntFunction<A[]> intFunction = paramIntFunction;
/* 505 */     return (A[])Nodes.<Object>flatten(evaluateToArrayNode(intFunction), (IntFunction)intFunction)
/* 506 */       .asArray((IntFunction)intFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object[] toArray() {
/* 511 */     return toArray(paramInt -> new Object[paramInt]);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean anyMatch(Predicate<? super P_OUT> paramPredicate) {
/* 516 */     return ((Boolean)evaluate(MatchOps.makeRef(paramPredicate, MatchOps.MatchKind.ANY))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean allMatch(Predicate<? super P_OUT> paramPredicate) {
/* 521 */     return ((Boolean)evaluate(MatchOps.makeRef(paramPredicate, MatchOps.MatchKind.ALL))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean noneMatch(Predicate<? super P_OUT> paramPredicate) {
/* 526 */     return ((Boolean)evaluate(MatchOps.makeRef(paramPredicate, MatchOps.MatchKind.NONE))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> findFirst() {
/* 531 */     return (Optional<P_OUT>)evaluate(FindOps.makeRef(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> findAny() {
/* 536 */     return (Optional<P_OUT>)evaluate(FindOps.makeRef(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public final P_OUT reduce(P_OUT paramP_OUT, BinaryOperator<P_OUT> paramBinaryOperator) {
/* 541 */     return (P_OUT)evaluate(ReduceOps.makeRef(paramP_OUT, paramBinaryOperator, paramBinaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> reduce(BinaryOperator<P_OUT> paramBinaryOperator) {
/* 546 */     return (Optional<P_OUT>)evaluate(ReduceOps.makeRef(paramBinaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final <R> R reduce(R paramR, BiFunction<R, ? super P_OUT, R> paramBiFunction, BinaryOperator<R> paramBinaryOperator) {
/* 551 */     return (R)evaluate(ReduceOps.makeRef(paramR, paramBiFunction, paramBinaryOperator));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R, A> R collect(Collector<? super P_OUT, A, R> paramCollector) {
/*     */     Object object;
/* 558 */     if (isParallel() && paramCollector
/* 559 */       .characteristics().contains(Collector.Characteristics.CONCURRENT) && (
/* 560 */       !isOrdered() || paramCollector.characteristics().contains(Collector.Characteristics.UNORDERED))) {
/* 561 */       object = paramCollector.supplier().get();
/* 562 */       BiConsumer<A, ? super P_OUT> biConsumer = paramCollector.accumulator();
/* 563 */       forEach(paramObject2 -> paramBiConsumer.accept(paramObject1, paramObject2));
/*     */     } else {
/*     */       
/* 566 */       object = evaluate(ReduceOps.makeRef(paramCollector));
/*     */     } 
/* 568 */     return paramCollector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH) ? (R)object : paramCollector
/*     */       
/* 570 */       .finisher().apply(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R> R collect(Supplier<R> paramSupplier, BiConsumer<R, ? super P_OUT> paramBiConsumer, BiConsumer<R, R> paramBiConsumer1) {
/* 577 */     return (R)evaluate(ReduceOps.makeRef(paramSupplier, paramBiConsumer, paramBiConsumer1));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> max(Comparator<? super P_OUT> paramComparator) {
/* 582 */     return reduce(BinaryOperator.maxBy(paramComparator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> min(Comparator<? super P_OUT> paramComparator) {
/* 587 */     return reduce(BinaryOperator.minBy(paramComparator));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long count() {
/* 593 */     return mapToLong(paramObject -> 1L).sum();
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
/*     */   static class Head<E_IN, E_OUT>
/*     */     extends ReferencePipeline<E_IN, E_OUT>
/*     */   {
/*     */     Head(Supplier<? extends Spliterator<?>> param1Supplier, int param1Int, boolean param1Boolean) {
/* 617 */       super(param1Supplier, param1Int, param1Boolean);
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
/*     */     Head(Spliterator<?> param1Spliterator, int param1Int, boolean param1Boolean) {
/* 629 */       super(param1Spliterator, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 634 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     final Sink<E_IN> opWrapSink(int param1Int, Sink<E_OUT> param1Sink) {
/* 639 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super E_OUT> param1Consumer) {
/* 646 */       if (!isParallel()) {
/* 647 */         sourceStageSpliterator().forEachRemaining(param1Consumer);
/*     */       } else {
/*     */         
/* 650 */         super.forEach(param1Consumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachOrdered(Consumer<? super E_OUT> param1Consumer) {
/* 656 */       if (!isParallel()) {
/* 657 */         sourceStageSpliterator().forEachRemaining(param1Consumer);
/*     */       } else {
/*     */         
/* 660 */         super.forEachOrdered(param1Consumer);
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
/*     */   static abstract class StatelessOp<E_IN, E_OUT>
/*     */     extends ReferencePipeline<E_IN, E_OUT>
/*     */   {
/*     */     StatelessOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 685 */       super(param1AbstractPipeline, param1Int);
/* 686 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 691 */       return false;
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
/*     */   static abstract class StatefulOp<E_IN, E_OUT>
/*     */     extends ReferencePipeline<E_IN, E_OUT>
/*     */   {
/*     */     StatefulOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 714 */       super(param1AbstractPipeline, param1Int);
/* 715 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 720 */       return true;
/*     */     }
/*     */     
/*     */     abstract <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHelper<E_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<E_OUT[]> param1IntFunction);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/ReferencePipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */