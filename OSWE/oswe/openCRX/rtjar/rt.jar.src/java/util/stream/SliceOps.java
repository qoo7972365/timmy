/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.CountedCompleter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SliceOps
/*     */ {
/*     */   private static long calcSize(long paramLong1, long paramLong2, long paramLong3) {
/*  53 */     return (paramLong1 >= 0L) ? Math.max(-1L, Math.min(paramLong1 - paramLong2, paramLong3)) : -1L;
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
/*     */   private static long calcSliceFence(long paramLong1, long paramLong2) {
/*  65 */     long l = (paramLong2 >= 0L) ? (paramLong1 + paramLong2) : Long.MAX_VALUE;
/*     */     
/*  67 */     return (l >= 0L) ? l : Long.MAX_VALUE;
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
/*     */   private static <P_IN> Spliterator<P_IN> sliceSpliterator(StreamShape paramStreamShape, Spliterator<P_IN> paramSpliterator, long paramLong1, long paramLong2) {
/*  79 */     assert paramSpliterator.hasCharacteristics(16384);
/*  80 */     long l = calcSliceFence(paramLong1, paramLong2);
/*  81 */     switch (paramStreamShape) {
/*     */       case REFERENCE:
/*  83 */         return new StreamSpliterators.SliceSpliterator.OfRef<>(paramSpliterator, paramLong1, l);
/*     */       
/*     */       case INT_VALUE:
/*  86 */         return new StreamSpliterators.SliceSpliterator.OfInt((Spliterator.OfInt)paramSpliterator, paramLong1, l);
/*     */       
/*     */       case LONG_VALUE:
/*  89 */         return new StreamSpliterators.SliceSpliterator.OfLong((Spliterator.OfLong)paramSpliterator, paramLong1, l);
/*     */       
/*     */       case DOUBLE_VALUE:
/*  92 */         return new StreamSpliterators.SliceSpliterator.OfDouble((Spliterator.OfDouble)paramSpliterator, paramLong1, l);
/*     */     } 
/*     */     
/*  95 */     throw new IllegalStateException("Unknown shape " + paramStreamShape);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> IntFunction<T[]> castingArray() {
/* 101 */     return paramInt -> new Object[paramInt];
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
/*     */   public static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> paramAbstractPipeline, final long skip, final long limit) {
/* 116 */     if (skip < 0L) {
/* 117 */       throw new IllegalArgumentException("Skip must be non-negative: " + skip);
/*     */     }
/* 119 */     return new ReferencePipeline.StatefulOp<T, T>(paramAbstractPipeline, StreamShape.REFERENCE, 
/* 120 */         flags(limit))
/*     */       {
/*     */         Spliterator<T> unorderedSkipLimitSpliterator(Spliterator<T> param1Spliterator, long param1Long1, long param1Long2, long param1Long3) {
/* 123 */           if (param1Long1 <= param1Long3) {
/*     */ 
/*     */             
/* 126 */             param1Long2 = (param1Long2 >= 0L) ? Math.min(param1Long2, param1Long3 - param1Long1) : (param1Long3 - param1Long1);
/* 127 */             param1Long1 = 0L;
/*     */           } 
/* 129 */           return new StreamSpliterators.UnorderedSliceSpliterator.OfRef<>(param1Spliterator, param1Long1, param1Long2);
/*     */         }
/*     */ 
/*     */         
/*     */         <P_IN> Spliterator<T> opEvaluateParallelLazy(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) {
/* 134 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 135 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384))
/* 136 */             return new StreamSpliterators.SliceSpliterator.OfRef<>(param1PipelineHelper
/* 137 */                 .wrapSpliterator(param1Spliterator), skip, SliceOps
/*     */                 
/* 139 */                 .calcSliceFence(skip, limit)); 
/* 140 */           if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 141 */             return unorderedSkipLimitSpliterator(param1PipelineHelper
/* 142 */                 .wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 154 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, SliceOps.castingArray(), skip, limit))
/* 155 */             .invoke().spliterator();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<T[]> param1IntFunction) {
/* 163 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 164 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 170 */             Spliterator<?> spliterator = SliceOps.sliceSpliterator(param1PipelineHelper.getSourceShape(), param1Spliterator, skip, limit);
/* 171 */             return Nodes.collect(param1PipelineHelper, spliterator, true, param1IntFunction);
/* 172 */           }  if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 173 */             Spliterator<T> spliterator = unorderedSkipLimitSpliterator(param1PipelineHelper
/* 174 */                 .wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 180 */             return Nodes.collect(this, spliterator, true, param1IntFunction);
/*     */           } 
/*     */           
/* 183 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, param1IntFunction, skip, limit))
/* 184 */             .invoke();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         Sink<T> opWrapSink(int param1Int, Sink<T> param1Sink) {
/* 190 */           return new Sink.ChainedReference<T, T>(param1Sink) {
/* 191 */               long n = skip;
/* 192 */               long m = (limit >= 0L) ? limit : Long.MAX_VALUE;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 196 */                 this.downstream.begin(SliceOps.calcSize(param2Long, skip, this.m));
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(T param2T) {
/* 201 */                 if (this.n == 0L) {
/* 202 */                   if (this.m > 0L) {
/* 203 */                     this.m--;
/* 204 */                     this.downstream.accept(param2T);
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 208 */                   this.n--;
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 214 */                 return (this.m == 0L || this.downstream.cancellationRequested());
/*     */               }
/*     */             };
/*     */         }
/*     */       };
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
/*     */   public static IntStream makeInt(AbstractPipeline<?, Integer, ?> paramAbstractPipeline, final long skip, final long limit) {
/* 232 */     if (skip < 0L) {
/* 233 */       throw new IllegalArgumentException("Skip must be non-negative: " + skip);
/*     */     }
/* 235 */     return new IntPipeline.StatefulOp<Integer>(paramAbstractPipeline, StreamShape.INT_VALUE, 
/* 236 */         flags(limit))
/*     */       {
/*     */         Spliterator.OfInt unorderedSkipLimitSpliterator(Spliterator.OfInt param1OfInt, long param1Long1, long param1Long2, long param1Long3) {
/* 239 */           if (param1Long1 <= param1Long3) {
/*     */ 
/*     */             
/* 242 */             param1Long2 = (param1Long2 >= 0L) ? Math.min(param1Long2, param1Long3 - param1Long1) : (param1Long3 - param1Long1);
/* 243 */             param1Long1 = 0L;
/*     */           } 
/* 245 */           return new StreamSpliterators.UnorderedSliceSpliterator.OfInt(param1OfInt, param1Long1, param1Long2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Spliterator<Integer> opEvaluateParallelLazy(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) {
/* 251 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 252 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384))
/* 253 */             return new StreamSpliterators.SliceSpliterator.OfInt((Spliterator.OfInt)param1PipelineHelper
/* 254 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, SliceOps
/*     */                 
/* 256 */                 .calcSliceFence(skip, limit)); 
/* 257 */           if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 258 */             return unorderedSkipLimitSpliterator((Spliterator.OfInt)param1PipelineHelper
/* 259 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */           }
/*     */ 
/*     */           
/* 263 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, param1Int -> new Integer[param1Int], skip, limit))
/* 264 */             .invoke().spliterator();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Integer[]> param1IntFunction) {
/* 272 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 273 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 279 */             Spliterator<?> spliterator = SliceOps.sliceSpliterator(param1PipelineHelper.getSourceShape(), param1Spliterator, skip, limit);
/* 280 */             return Nodes.collectInt(param1PipelineHelper, spliterator, true);
/* 281 */           }  if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 282 */             Spliterator.OfInt ofInt = unorderedSkipLimitSpliterator((Spliterator.OfInt)param1PipelineHelper
/* 283 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 289 */             return Nodes.collectInt(this, ofInt, true);
/*     */           } 
/*     */           
/* 292 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, param1IntFunction, skip, limit))
/* 293 */             .invoke();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink) {
/* 299 */           return new Sink.ChainedInt<Integer>(param1Sink) {
/* 300 */               long n = skip;
/* 301 */               long m = (limit >= 0L) ? limit : Long.MAX_VALUE;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 305 */                 this.downstream.begin(SliceOps.calcSize(param2Long, skip, this.m));
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(int param2Int) {
/* 310 */                 if (this.n == 0L) {
/* 311 */                   if (this.m > 0L) {
/* 312 */                     this.m--;
/* 313 */                     this.downstream.accept(param2Int);
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 317 */                   this.n--;
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 323 */                 return (this.m == 0L || this.downstream.cancellationRequested());
/*     */               }
/*     */             };
/*     */         }
/*     */       };
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
/*     */   public static LongStream makeLong(AbstractPipeline<?, Long, ?> paramAbstractPipeline, final long skip, final long limit) {
/* 341 */     if (skip < 0L) {
/* 342 */       throw new IllegalArgumentException("Skip must be non-negative: " + skip);
/*     */     }
/* 344 */     return new LongPipeline.StatefulOp<Long>(paramAbstractPipeline, StreamShape.LONG_VALUE, 
/* 345 */         flags(limit))
/*     */       {
/*     */         Spliterator.OfLong unorderedSkipLimitSpliterator(Spliterator.OfLong param1OfLong, long param1Long1, long param1Long2, long param1Long3) {
/* 348 */           if (param1Long1 <= param1Long3) {
/*     */ 
/*     */             
/* 351 */             param1Long2 = (param1Long2 >= 0L) ? Math.min(param1Long2, param1Long3 - param1Long1) : (param1Long3 - param1Long1);
/* 352 */             param1Long1 = 0L;
/*     */           } 
/* 354 */           return new StreamSpliterators.UnorderedSliceSpliterator.OfLong(param1OfLong, param1Long1, param1Long2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Spliterator<Long> opEvaluateParallelLazy(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) {
/* 360 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 361 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384))
/* 362 */             return new StreamSpliterators.SliceSpliterator.OfLong((Spliterator.OfLong)param1PipelineHelper
/* 363 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, SliceOps
/*     */                 
/* 365 */                 .calcSliceFence(skip, limit)); 
/* 366 */           if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 367 */             return unorderedSkipLimitSpliterator((Spliterator.OfLong)param1PipelineHelper
/* 368 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */           }
/*     */ 
/*     */           
/* 372 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, param1Int -> new Long[param1Int], skip, limit))
/* 373 */             .invoke().spliterator();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Long[]> param1IntFunction) {
/* 381 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 382 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 388 */             Spliterator<?> spliterator = SliceOps.sliceSpliterator(param1PipelineHelper.getSourceShape(), param1Spliterator, skip, limit);
/* 389 */             return Nodes.collectLong(param1PipelineHelper, spliterator, true);
/* 390 */           }  if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 391 */             Spliterator.OfLong ofLong = unorderedSkipLimitSpliterator((Spliterator.OfLong)param1PipelineHelper
/* 392 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 398 */             return Nodes.collectLong(this, ofLong, true);
/*     */           } 
/*     */           
/* 401 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, param1IntFunction, skip, limit))
/* 402 */             .invoke();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink) {
/* 408 */           return new Sink.ChainedLong<Long>(param1Sink) {
/* 409 */               long n = skip;
/* 410 */               long m = (limit >= 0L) ? limit : Long.MAX_VALUE;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 414 */                 this.downstream.begin(SliceOps.calcSize(param2Long, skip, this.m));
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(long param2Long) {
/* 419 */                 if (this.n == 0L) {
/* 420 */                   if (this.m > 0L) {
/* 421 */                     this.m--;
/* 422 */                     this.downstream.accept(param2Long);
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 426 */                   this.n--;
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 432 */                 return (this.m == 0L || this.downstream.cancellationRequested());
/*     */               }
/*     */             };
/*     */         }
/*     */       };
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
/*     */   public static DoubleStream makeDouble(AbstractPipeline<?, Double, ?> paramAbstractPipeline, final long skip, final long limit) {
/* 450 */     if (skip < 0L) {
/* 451 */       throw new IllegalArgumentException("Skip must be non-negative: " + skip);
/*     */     }
/* 453 */     return new DoublePipeline.StatefulOp<Double>(paramAbstractPipeline, StreamShape.DOUBLE_VALUE, 
/* 454 */         flags(limit))
/*     */       {
/*     */         Spliterator.OfDouble unorderedSkipLimitSpliterator(Spliterator.OfDouble param1OfDouble, long param1Long1, long param1Long2, long param1Long3) {
/* 457 */           if (param1Long1 <= param1Long3) {
/*     */ 
/*     */             
/* 460 */             param1Long2 = (param1Long2 >= 0L) ? Math.min(param1Long2, param1Long3 - param1Long1) : (param1Long3 - param1Long1);
/* 461 */             param1Long1 = 0L;
/*     */           } 
/* 463 */           return new StreamSpliterators.UnorderedSliceSpliterator.OfDouble(param1OfDouble, param1Long1, param1Long2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Spliterator<Double> opEvaluateParallelLazy(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) {
/* 469 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 470 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384))
/* 471 */             return new StreamSpliterators.SliceSpliterator.OfDouble((Spliterator.OfDouble)param1PipelineHelper
/* 472 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, SliceOps
/*     */                 
/* 474 */                 .calcSliceFence(skip, limit)); 
/* 475 */           if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 476 */             return unorderedSkipLimitSpliterator((Spliterator.OfDouble)param1PipelineHelper
/* 477 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */           }
/*     */ 
/*     */           
/* 481 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, param1Int -> new Double[param1Int], skip, limit))
/* 482 */             .invoke().spliterator();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Double[]> param1IntFunction) {
/* 490 */           long l = param1PipelineHelper.exactOutputSizeIfKnown(param1Spliterator);
/* 491 */           if (l > 0L && param1Spliterator.hasCharacteristics(16384)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 497 */             Spliterator<?> spliterator = SliceOps.sliceSpliterator(param1PipelineHelper.getSourceShape(), param1Spliterator, skip, limit);
/* 498 */             return Nodes.collectDouble(param1PipelineHelper, spliterator, true);
/* 499 */           }  if (!StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 500 */             Spliterator.OfDouble ofDouble = unorderedSkipLimitSpliterator((Spliterator.OfDouble)param1PipelineHelper
/* 501 */                 .<P_IN>wrapSpliterator(param1Spliterator), skip, limit, l);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 507 */             return Nodes.collectDouble(this, ofDouble, true);
/*     */           } 
/*     */           
/* 510 */           return (new SliceOps.SliceTask<>(this, param1PipelineHelper, param1Spliterator, param1IntFunction, skip, limit))
/* 511 */             .invoke();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink) {
/* 517 */           return new Sink.ChainedDouble<Double>(param1Sink) {
/* 518 */               long n = skip;
/* 519 */               long m = (limit >= 0L) ? limit : Long.MAX_VALUE;
/*     */ 
/*     */               
/*     */               public void begin(long param2Long) {
/* 523 */                 this.downstream.begin(SliceOps.calcSize(param2Long, skip, this.m));
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(double param2Double) {
/* 528 */                 if (this.n == 0L) {
/* 529 */                   if (this.m > 0L) {
/* 530 */                     this.m--;
/* 531 */                     this.downstream.accept(param2Double);
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 535 */                   this.n--;
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean cancellationRequested() {
/* 541 */                 return (this.m == 0L || this.downstream.cancellationRequested());
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static int flags(long paramLong) {
/* 549 */     return StreamOpFlag.NOT_SIZED | ((paramLong != -1L) ? StreamOpFlag.IS_SHORT_CIRCUIT : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class SliceTask<P_IN, P_OUT>
/*     */     extends AbstractShortCircuitTask<P_IN, P_OUT, Node<P_OUT>, SliceTask<P_IN, P_OUT>>
/*     */   {
/*     */     private final AbstractPipeline<P_OUT, P_OUT, ?> op;
/*     */ 
/*     */     
/*     */     private final IntFunction<P_OUT[]> generator;
/*     */ 
/*     */     
/*     */     private final long targetOffset;
/*     */     
/*     */     private final long targetSize;
/*     */     
/*     */     private long thisNodeSize;
/*     */     
/*     */     private volatile boolean completed;
/*     */ 
/*     */     
/*     */     SliceTask(AbstractPipeline<P_OUT, P_OUT, ?> param1AbstractPipeline, PipelineHelper<P_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<P_OUT[]> param1IntFunction, long param1Long1, long param1Long2) {
/* 573 */       super(param1PipelineHelper, param1Spliterator);
/* 574 */       this.op = param1AbstractPipeline;
/* 575 */       this.generator = param1IntFunction;
/* 576 */       this.targetOffset = param1Long1;
/* 577 */       this.targetSize = param1Long2;
/*     */     }
/*     */     
/*     */     SliceTask(SliceTask<P_IN, P_OUT> param1SliceTask, Spliterator<P_IN> param1Spliterator) {
/* 581 */       super(param1SliceTask, param1Spliterator);
/* 582 */       this.op = param1SliceTask.op;
/* 583 */       this.generator = param1SliceTask.generator;
/* 584 */       this.targetOffset = param1SliceTask.targetOffset;
/* 585 */       this.targetSize = param1SliceTask.targetSize;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SliceTask<P_IN, P_OUT> makeChild(Spliterator<P_IN> param1Spliterator) {
/* 590 */       return new SliceTask(this, param1Spliterator);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final Node<P_OUT> getEmptyResult() {
/* 595 */       return Nodes.emptyNode(this.op.getOutputShape());
/*     */     }
/*     */ 
/*     */     
/*     */     protected final Node<P_OUT> doLeaf() {
/* 600 */       if (isRoot()) {
/*     */         
/* 602 */         long l = StreamOpFlag.SIZED.isPreserved(this.op.sourceOrOpFlags) ? this.op.<P_IN>exactOutputSizeIfKnown(this.spliterator) : -1L;
/*     */         
/* 604 */         Node.Builder<P_OUT> builder = this.op.makeNodeBuilder(l, this.generator);
/* 605 */         Sink<P_OUT> sink = this.op.opWrapSink(this.helper.getStreamAndOpFlags(), builder);
/* 606 */         this.helper.copyIntoWithCancel(this.helper.wrapSink(sink), this.spliterator);
/*     */ 
/*     */         
/* 609 */         return builder.build();
/*     */       } 
/*     */ 
/*     */       
/* 613 */       Node<P_OUT> node = ((Node.Builder)this.helper.<P_IN, Node.Builder>wrapAndCopyInto(this.helper.makeNodeBuilder(-1L, this.generator), this.spliterator)).build();
/* 614 */       this.thisNodeSize = node.count();
/* 615 */       this.completed = true;
/* 616 */       this.spliterator = null;
/* 617 */       return node;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void onCompletion(CountedCompleter<?> param1CountedCompleter) {
/* 623 */       if (!isLeaf()) {
/*     */         Node<?> node;
/* 625 */         this.thisNodeSize = this.leftChild.thisNodeSize + this.rightChild.thisNodeSize;
/* 626 */         if (this.canceled) {
/* 627 */           this.thisNodeSize = 0L;
/* 628 */           node = getEmptyResult();
/*     */         }
/* 630 */         else if (this.thisNodeSize == 0L) {
/* 631 */           node = getEmptyResult();
/* 632 */         } else if (this.leftChild.thisNodeSize == 0L) {
/* 633 */           node = this.rightChild.getLocalResult();
/*     */         } else {
/* 635 */           node = Nodes.conc(this.op.getOutputShape(), this.leftChild
/* 636 */               .getLocalResult(), this.rightChild.getLocalResult());
/*     */         } 
/* 638 */         setLocalResult(isRoot() ? doTruncate((Node)node) : (Node)node);
/* 639 */         this.completed = true;
/*     */       } 
/* 641 */       if (this.targetSize >= 0L && 
/* 642 */         !isRoot() && 
/* 643 */         isLeftCompleted(this.targetOffset + this.targetSize)) {
/* 644 */         cancelLaterNodes();
/*     */       }
/* 646 */       super.onCompletion(param1CountedCompleter);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void cancel() {
/* 651 */       super.cancel();
/* 652 */       if (this.completed)
/* 653 */         setLocalResult(getEmptyResult()); 
/*     */     }
/*     */     
/*     */     private Node<P_OUT> doTruncate(Node<P_OUT> param1Node) {
/* 657 */       long l = (this.targetSize >= 0L) ? Math.min(param1Node.count(), this.targetOffset + this.targetSize) : this.thisNodeSize;
/* 658 */       return param1Node.truncate(this.targetOffset, l, this.generator);
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
/*     */     private boolean isLeftCompleted(long param1Long) {
/* 670 */       long l = this.completed ? this.thisNodeSize : completedSize(param1Long);
/* 671 */       if (l >= param1Long)
/* 672 */         return true; 
/* 673 */       SliceTask<P_IN, P_OUT> sliceTask1 = getParent(), sliceTask2 = this;
/* 674 */       for (; sliceTask1 != null; 
/* 675 */         sliceTask2 = sliceTask1, sliceTask1 = sliceTask1.getParent()) {
/* 676 */         if (sliceTask2 == sliceTask1.rightChild) {
/* 677 */           SliceTask<P_IN, P_OUT> sliceTask = sliceTask1.leftChild;
/* 678 */           if (sliceTask != null) {
/* 679 */             l += sliceTask.completedSize(param1Long);
/* 680 */             if (l >= param1Long)
/* 681 */               return true; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 685 */       return (l >= param1Long);
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
/*     */     private long completedSize(long param1Long) {
/* 699 */       if (this.completed) {
/* 700 */         return this.thisNodeSize;
/*     */       }
/* 702 */       SliceTask<P_IN, P_OUT> sliceTask1 = this.leftChild;
/* 703 */       SliceTask<P_IN, P_OUT> sliceTask2 = this.rightChild;
/* 704 */       if (sliceTask1 == null || sliceTask2 == null)
/*     */       {
/* 706 */         return this.thisNodeSize;
/*     */       }
/*     */       
/* 709 */       long l = sliceTask1.completedSize(param1Long);
/* 710 */       return (l >= param1Long) ? l : (l + sliceTask2.completedSize(param1Long));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/SliceOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */