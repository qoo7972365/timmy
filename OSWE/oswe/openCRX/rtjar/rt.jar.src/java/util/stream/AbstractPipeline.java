/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.IntFunction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractPipeline<E_IN, E_OUT, S extends BaseStream<E_OUT, S>>
/*     */   extends PipelineHelper<E_OUT>
/*     */   implements BaseStream<E_OUT, S>
/*     */ {
/*     */   private static final String MSG_STREAM_LINKED = "stream has already been operated upon or closed";
/*     */   private static final String MSG_CONSUMED = "source already consumed or closed";
/*     */   private final AbstractPipeline sourceStage;
/*     */   private final AbstractPipeline previousStage;
/*     */   protected final int sourceOrOpFlags;
/*     */   private AbstractPipeline nextStage;
/*     */   private int depth;
/*     */   private int combinedFlags;
/*     */   private Spliterator<?> sourceSpliterator;
/*     */   private Supplier<? extends Spliterator<?>> sourceSupplier;
/*     */   private boolean linkedOrConsumed;
/*     */   private boolean sourceAnyStateful;
/*     */   private Runnable sourceCloseAction;
/*     */   private boolean parallel;
/*     */   
/*     */   AbstractPipeline(Supplier<? extends Spliterator<?>> paramSupplier, int paramInt, boolean paramBoolean) {
/* 161 */     this.previousStage = null;
/* 162 */     this.sourceSupplier = paramSupplier;
/* 163 */     this.sourceStage = this;
/* 164 */     this.sourceOrOpFlags = paramInt & StreamOpFlag.STREAM_MASK;
/*     */ 
/*     */     
/* 167 */     this.combinedFlags = (this.sourceOrOpFlags << 1 ^ 0xFFFFFFFF) & StreamOpFlag.INITIAL_OPS_VALUE;
/* 168 */     this.depth = 0;
/* 169 */     this.parallel = paramBoolean;
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
/*     */   AbstractPipeline(Spliterator<?> paramSpliterator, int paramInt, boolean paramBoolean) {
/* 182 */     this.previousStage = null;
/* 183 */     this.sourceSpliterator = paramSpliterator;
/* 184 */     this.sourceStage = this;
/* 185 */     this.sourceOrOpFlags = paramInt & StreamOpFlag.STREAM_MASK;
/*     */ 
/*     */     
/* 188 */     this.combinedFlags = (this.sourceOrOpFlags << 1 ^ 0xFFFFFFFF) & StreamOpFlag.INITIAL_OPS_VALUE;
/* 189 */     this.depth = 0;
/* 190 */     this.parallel = paramBoolean;
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
/*     */   AbstractPipeline(AbstractPipeline<?, E_IN, ?> paramAbstractPipeline, int paramInt) {
/* 202 */     if (paramAbstractPipeline.linkedOrConsumed)
/* 203 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 204 */     paramAbstractPipeline.linkedOrConsumed = true;
/* 205 */     paramAbstractPipeline.nextStage = this;
/*     */     
/* 207 */     this.previousStage = paramAbstractPipeline;
/* 208 */     this.sourceOrOpFlags = paramInt & StreamOpFlag.OP_MASK;
/* 209 */     this.combinedFlags = StreamOpFlag.combineOpFlags(paramInt, paramAbstractPipeline.combinedFlags);
/* 210 */     this.sourceStage = paramAbstractPipeline.sourceStage;
/* 211 */     if (opIsStateful())
/* 212 */       this.sourceStage.sourceAnyStateful = true; 
/* 213 */     this.depth = paramAbstractPipeline.depth + 1;
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
/*     */   final <R> R evaluate(TerminalOp<E_OUT, R> paramTerminalOp) {
/* 227 */     assert getOutputShape() == paramTerminalOp.inputShape();
/* 228 */     if (this.linkedOrConsumed)
/* 229 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 230 */     this.linkedOrConsumed = true;
/*     */     
/* 232 */     return isParallel() ? paramTerminalOp
/* 233 */       .evaluateParallel(this, sourceSpliterator(paramTerminalOp.getOpFlags())) : paramTerminalOp
/* 234 */       .evaluateSequential(this, sourceSpliterator(paramTerminalOp.getOpFlags()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Node<E_OUT> evaluateToArrayNode(IntFunction<E_OUT[]> paramIntFunction) {
/* 245 */     if (this.linkedOrConsumed)
/* 246 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 247 */     this.linkedOrConsumed = true;
/*     */ 
/*     */ 
/*     */     
/* 251 */     if (isParallel() && this.previousStage != null && opIsStateful()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       this.depth = 0;
/* 257 */       return opEvaluateParallel(this.previousStage, this.previousStage.sourceSpliterator(0), paramIntFunction);
/*     */     } 
/*     */     
/* 260 */     return evaluate(sourceSpliterator(0), true, paramIntFunction);
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
/*     */   final Spliterator<E_OUT> sourceStageSpliterator() {
/* 275 */     if (this != this.sourceStage) {
/* 276 */       throw new IllegalStateException();
/*     */     }
/* 278 */     if (this.linkedOrConsumed)
/* 279 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 280 */     this.linkedOrConsumed = true;
/*     */     
/* 282 */     if (this.sourceStage.sourceSpliterator != null) {
/*     */       
/* 284 */       Spliterator<?> spliterator = this.sourceStage.sourceSpliterator;
/* 285 */       this.sourceStage.sourceSpliterator = null;
/* 286 */       return (Spliterator)spliterator;
/*     */     } 
/* 288 */     if (this.sourceStage.sourceSupplier != null) {
/*     */       
/* 290 */       Spliterator<E_OUT> spliterator = (Spliterator)this.sourceStage.sourceSupplier.get();
/* 291 */       this.sourceStage.sourceSupplier = null;
/* 292 */       return spliterator;
/*     */     } 
/*     */     
/* 295 */     throw new IllegalStateException("source already consumed or closed");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final S sequential() {
/* 304 */     this.sourceStage.parallel = false;
/* 305 */     return (S)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final S parallel() {
/* 311 */     this.sourceStage.parallel = true;
/* 312 */     return (S)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 317 */     this.linkedOrConsumed = true;
/* 318 */     this.sourceSupplier = null;
/* 319 */     this.sourceSpliterator = null;
/* 320 */     if (this.sourceStage.sourceCloseAction != null) {
/* 321 */       Runnable runnable = this.sourceStage.sourceCloseAction;
/* 322 */       this.sourceStage.sourceCloseAction = null;
/* 323 */       runnable.run();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public S onClose(Runnable paramRunnable) {
/* 330 */     Objects.requireNonNull(paramRunnable);
/* 331 */     Runnable runnable = this.sourceStage.sourceCloseAction;
/* 332 */     this.sourceStage
/*     */ 
/*     */       
/* 335 */       .sourceCloseAction = (runnable == null) ? paramRunnable : Streams.composeWithExceptions(runnable, paramRunnable);
/* 336 */     return (S)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spliterator<E_OUT> spliterator() {
/* 343 */     if (this.linkedOrConsumed)
/* 344 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 345 */     this.linkedOrConsumed = true;
/*     */     
/* 347 */     if (this == this.sourceStage) {
/* 348 */       if (this.sourceStage.sourceSpliterator != null) {
/*     */         
/* 350 */         Spliterator<?> spliterator = this.sourceStage.sourceSpliterator;
/* 351 */         this.sourceStage.sourceSpliterator = null;
/* 352 */         return (Spliterator)spliterator;
/*     */       } 
/* 354 */       if (this.sourceStage.sourceSupplier != null) {
/*     */         
/* 356 */         Supplier<? extends Spliterator<?>> supplier = this.sourceStage.sourceSupplier;
/* 357 */         this.sourceStage.sourceSupplier = null;
/* 358 */         return lazySpliterator((Supplier)supplier);
/*     */       } 
/*     */       
/* 361 */       throw new IllegalStateException("source already consumed or closed");
/*     */     } 
/*     */ 
/*     */     
/* 365 */     return wrap(this, () -> sourceSpliterator(0), isParallel());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isParallel() {
/* 371 */     return this.sourceStage.parallel;
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
/*     */   final int getStreamFlags() {
/* 384 */     return StreamOpFlag.toStreamFlags(this.combinedFlags);
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
/*     */   private Spliterator<?> sourceSpliterator(int paramInt) {
/* 397 */     Spliterator<?> spliterator = null;
/* 398 */     if (this.sourceStage.sourceSpliterator != null) {
/* 399 */       spliterator = this.sourceStage.sourceSpliterator;
/* 400 */       this.sourceStage.sourceSpliterator = null;
/*     */     }
/* 402 */     else if (this.sourceStage.sourceSupplier != null) {
/* 403 */       spliterator = this.sourceStage.sourceSupplier.get();
/* 404 */       this.sourceStage.sourceSupplier = null;
/*     */     } else {
/*     */       
/* 407 */       throw new IllegalStateException("source already consumed or closed");
/*     */     } 
/*     */     
/* 410 */     if (isParallel() && this.sourceStage.sourceAnyStateful) {
/*     */ 
/*     */ 
/*     */       
/* 414 */       byte b = 1;
/* 415 */       AbstractPipeline abstractPipeline1 = this.sourceStage, abstractPipeline2 = this.sourceStage.nextStage, abstractPipeline3 = this;
/* 416 */       for (; abstractPipeline1 != abstractPipeline3; 
/* 417 */         abstractPipeline1 = abstractPipeline2, abstractPipeline2 = abstractPipeline2.nextStage) {
/*     */         
/* 419 */         int i = abstractPipeline2.sourceOrOpFlags;
/* 420 */         if (abstractPipeline2.opIsStateful()) {
/* 421 */           b = 0;
/*     */           
/* 423 */           if (StreamOpFlag.SHORT_CIRCUIT.isKnown(i))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 429 */             i &= StreamOpFlag.IS_SHORT_CIRCUIT ^ 0xFFFFFFFF;
/*     */           }
/*     */           
/* 432 */           spliterator = abstractPipeline2.opEvaluateParallelLazy(abstractPipeline1, spliterator);
/*     */ 
/*     */ 
/*     */           
/* 436 */           i = spliterator.hasCharacteristics(64) ? (i & (StreamOpFlag.NOT_SIZED ^ 0xFFFFFFFF) | StreamOpFlag.IS_SIZED) : (i & (StreamOpFlag.IS_SIZED ^ 0xFFFFFFFF) | StreamOpFlag.NOT_SIZED);
/*     */         } 
/*     */ 
/*     */         
/* 440 */         abstractPipeline2.depth = b++;
/* 441 */         abstractPipeline2.combinedFlags = StreamOpFlag.combineOpFlags(i, abstractPipeline1.combinedFlags);
/*     */       } 
/*     */     } 
/*     */     
/* 445 */     if (paramInt != 0)
/*     */     {
/* 447 */       this.combinedFlags = StreamOpFlag.combineOpFlags(paramInt, this.combinedFlags);
/*     */     }
/*     */     
/* 450 */     return spliterator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getSourceShape() {
/* 458 */     AbstractPipeline abstractPipeline = this;
/* 459 */     while (abstractPipeline.depth > 0) {
/* 460 */       abstractPipeline = abstractPipeline.previousStage;
/*     */     }
/* 462 */     return abstractPipeline.getOutputShape();
/*     */   }
/*     */ 
/*     */   
/*     */   final <P_IN> long exactOutputSizeIfKnown(Spliterator<P_IN> paramSpliterator) {
/* 467 */     return StreamOpFlag.SIZED.isKnown(getStreamAndOpFlags()) ? paramSpliterator.getExactSizeIfKnown() : -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   final <P_IN, S extends Sink<E_OUT>> S wrapAndCopyInto(S paramS, Spliterator<P_IN> paramSpliterator) {
/* 472 */     copyInto(wrapSink(Objects.<Sink>requireNonNull((Sink)paramS)), paramSpliterator);
/* 473 */     return paramS;
/*     */   }
/*     */ 
/*     */   
/*     */   final <P_IN> void copyInto(Sink<P_IN> paramSink, Spliterator<P_IN> paramSpliterator) {
/* 478 */     Objects.requireNonNull(paramSink);
/*     */     
/* 480 */     if (!StreamOpFlag.SHORT_CIRCUIT.isKnown(getStreamAndOpFlags())) {
/* 481 */       paramSink.begin(paramSpliterator.getExactSizeIfKnown());
/* 482 */       paramSpliterator.forEachRemaining(paramSink);
/* 483 */       paramSink.end();
/*     */     } else {
/*     */       
/* 486 */       copyIntoWithCancel(paramSink, paramSpliterator);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> void copyIntoWithCancel(Sink<P_IN> paramSink, Spliterator<P_IN> paramSpliterator) {
/* 494 */     AbstractPipeline abstractPipeline = this;
/* 495 */     while (abstractPipeline.depth > 0) {
/* 496 */       abstractPipeline = abstractPipeline.previousStage;
/*     */     }
/* 498 */     paramSink.begin(paramSpliterator.getExactSizeIfKnown());
/* 499 */     abstractPipeline.forEachWithCancel(paramSpliterator, paramSink);
/* 500 */     paramSink.end();
/*     */   }
/*     */ 
/*     */   
/*     */   final int getStreamAndOpFlags() {
/* 505 */     return this.combinedFlags;
/*     */   }
/*     */   
/*     */   final boolean isOrdered() {
/* 509 */     return StreamOpFlag.ORDERED.isKnown(this.combinedFlags);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Sink<P_IN> wrapSink(Sink<E_OUT> paramSink) {
/* 515 */     Objects.requireNonNull(paramSink);
/*     */     
/* 517 */     for (AbstractPipeline abstractPipeline = this; abstractPipeline.depth > 0; abstractPipeline = abstractPipeline.previousStage) {
/* 518 */       paramSink = abstractPipeline.opWrapSink(abstractPipeline.previousStage.combinedFlags, paramSink);
/*     */     }
/* 520 */     return paramSink;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<E_OUT> wrapSpliterator(Spliterator<P_IN> paramSpliterator) {
/* 526 */     if (this.depth == 0) {
/* 527 */       return paramSpliterator;
/*     */     }
/*     */     
/* 530 */     return wrap(this, () -> paramSpliterator, isParallel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<E_OUT> evaluate(Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<E_OUT[]> paramIntFunction) {
/* 539 */     if (isParallel())
/*     */     {
/* 541 */       return evaluateToNode(this, paramSpliterator, paramBoolean, paramIntFunction);
/*     */     }
/*     */     
/* 544 */     Node.Builder<E_OUT> builder = makeNodeBuilder(
/* 545 */         exactOutputSizeIfKnown(paramSpliterator), paramIntFunction);
/* 546 */     return ((Node.Builder<E_OUT>)wrapAndCopyInto(builder, paramSpliterator)).build();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHelper<E_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, IntFunction<E_OUT[]> paramIntFunction) {
/* 679 */     throw new UnsupportedOperationException("Parallel evaluation is not supported");
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
/*     */ 
/*     */ 
/*     */   
/*     */   <P_IN> Spliterator<E_OUT> opEvaluateParallelLazy(PipelineHelper<E_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator) {
/* 705 */     return opEvaluateParallel(paramPipelineHelper, paramSpliterator, paramInt -> new Object[paramInt]).spliterator();
/*     */   }
/*     */   
/*     */   abstract StreamShape getOutputShape();
/*     */   
/*     */   abstract <P_IN> Node<E_OUT> evaluateToNode(PipelineHelper<E_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<E_OUT[]> paramIntFunction);
/*     */   
/*     */   abstract <P_IN> Spliterator<E_OUT> wrap(PipelineHelper<E_OUT> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean);
/*     */   
/*     */   abstract Spliterator<E_OUT> lazySpliterator(Supplier<? extends Spliterator<E_OUT>> paramSupplier);
/*     */   
/*     */   abstract void forEachWithCancel(Spliterator<E_OUT> paramSpliterator, Sink<E_OUT> paramSink);
/*     */   
/*     */   abstract Node.Builder<E_OUT> makeNodeBuilder(long paramLong, IntFunction<E_OUT[]> paramIntFunction);
/*     */   
/*     */   abstract boolean opIsStateful();
/*     */   
/*     */   abstract Sink<E_IN> opWrapSink(int paramInt, Sink<E_OUT> paramSink);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/AbstractPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */