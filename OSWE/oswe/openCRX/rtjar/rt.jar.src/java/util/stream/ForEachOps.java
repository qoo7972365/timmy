/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ForEachOps
/*     */ {
/*     */   public static <T> TerminalOp<T, Void> makeRef(Consumer<? super T> paramConsumer, boolean paramBoolean) {
/*  71 */     Objects.requireNonNull(paramConsumer);
/*  72 */     return new ForEachOp.OfRef<>(paramConsumer, paramBoolean);
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
/*     */   public static TerminalOp<Integer, Void> makeInt(IntConsumer paramIntConsumer, boolean paramBoolean) {
/*  86 */     Objects.requireNonNull(paramIntConsumer);
/*  87 */     return new ForEachOp.OfInt(paramIntConsumer, paramBoolean);
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
/*     */   public static TerminalOp<Long, Void> makeLong(LongConsumer paramLongConsumer, boolean paramBoolean) {
/* 101 */     Objects.requireNonNull(paramLongConsumer);
/* 102 */     return new ForEachOp.OfLong(paramLongConsumer, paramBoolean);
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
/*     */   public static TerminalOp<Double, Void> makeDouble(DoubleConsumer paramDoubleConsumer, boolean paramBoolean) {
/* 116 */     Objects.requireNonNull(paramDoubleConsumer);
/* 117 */     return new ForEachOp.OfDouble(paramDoubleConsumer, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class ForEachOp<T>
/*     */     implements TerminalOp<T, Void>, TerminalSink<T, Void>
/*     */   {
/*     */     private final boolean ordered;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ForEachOp(boolean param1Boolean) {
/* 137 */       this.ordered = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getOpFlags() {
/* 144 */       return this.ordered ? 0 : StreamOpFlag.NOT_ORDERED;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> Void evaluateSequential(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator) {
/* 150 */       return ((ForEachOp)param1PipelineHelper.wrapAndCopyInto(this, param1Spliterator)).get();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> Void evaluateParallel(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator) {
/* 156 */       if (this.ordered) {
/* 157 */         (new ForEachOps.ForEachOrderedTask<>(param1PipelineHelper, param1Spliterator, this)).invoke();
/*     */       } else {
/* 159 */         (new ForEachOps.ForEachTask<>(param1PipelineHelper, param1Spliterator, param1PipelineHelper.wrapSink(this))).invoke();
/* 160 */       }  return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Void get() {
/* 167 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     static final class OfRef<T>
/*     */       extends ForEachOp<T>
/*     */     {
/*     */       final Consumer<? super T> consumer;
/*     */       
/*     */       OfRef(Consumer<? super T> param2Consumer, boolean param2Boolean) {
/* 177 */         super(param2Boolean);
/* 178 */         this.consumer = param2Consumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public void accept(T param2T) {
/* 183 */         this.consumer.accept(param2T);
/*     */       }
/*     */     }
/*     */     
/*     */     static final class OfInt
/*     */       extends ForEachOp<Integer>
/*     */       implements Sink.OfInt {
/*     */       final IntConsumer consumer;
/*     */       
/*     */       OfInt(IntConsumer param2IntConsumer, boolean param2Boolean) {
/* 193 */         super(param2Boolean);
/* 194 */         this.consumer = param2IntConsumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamShape inputShape() {
/* 199 */         return StreamShape.INT_VALUE;
/*     */       }
/*     */ 
/*     */       
/*     */       public void accept(int param2Int) {
/* 204 */         this.consumer.accept(param2Int);
/*     */       }
/*     */     }
/*     */     
/*     */     static final class OfLong
/*     */       extends ForEachOp<Long>
/*     */       implements Sink.OfLong {
/*     */       final LongConsumer consumer;
/*     */       
/*     */       OfLong(LongConsumer param2LongConsumer, boolean param2Boolean) {
/* 214 */         super(param2Boolean);
/* 215 */         this.consumer = param2LongConsumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamShape inputShape() {
/* 220 */         return StreamShape.LONG_VALUE;
/*     */       }
/*     */ 
/*     */       
/*     */       public void accept(long param2Long) {
/* 225 */         this.consumer.accept(param2Long);
/*     */       }
/*     */     }
/*     */     
/*     */     static final class OfDouble
/*     */       extends ForEachOp<Double>
/*     */       implements Sink.OfDouble {
/*     */       final DoubleConsumer consumer;
/*     */       
/*     */       OfDouble(DoubleConsumer param2DoubleConsumer, boolean param2Boolean) {
/* 235 */         super(param2Boolean);
/* 236 */         this.consumer = param2DoubleConsumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamShape inputShape() {
/* 241 */         return StreamShape.DOUBLE_VALUE;
/*     */       }
/*     */       
/*     */       public void accept(double param2Double)
/*     */       {
/* 246 */         this.consumer.accept(param2Double); } } } static final class OfRef<T> extends ForEachOp<T> { final Consumer<? super T> consumer; OfRef(Consumer<? super T> param1Consumer, boolean param1Boolean) { super(param1Boolean); this.consumer = param1Consumer; } public void accept(T param1T) { this.consumer.accept(param1T); } } static final class OfInt extends ForEachOp<Integer> implements Sink.OfInt { final IntConsumer consumer; OfInt(IntConsumer param1IntConsumer, boolean param1Boolean) { super(param1Boolean); this.consumer = param1IntConsumer; } public StreamShape inputShape() { return StreamShape.INT_VALUE; } public void accept(int param1Int) { this.consumer.accept(param1Int); } } static final class OfLong extends ForEachOp<Long> implements Sink.OfLong { final LongConsumer consumer; OfLong(LongConsumer param1LongConsumer, boolean param1Boolean) { super(param1Boolean); this.consumer = param1LongConsumer; } public StreamShape inputShape() { return StreamShape.LONG_VALUE; } public void accept(long param1Long) { this.consumer.accept(param1Long); } } static final class OfDouble extends ForEachOp<Double> implements Sink.OfDouble { public void accept(double param1Double) { this.consumer.accept(param1Double); }
/*     */     
/*     */     final DoubleConsumer consumer;
/*     */     OfDouble(DoubleConsumer param1DoubleConsumer, boolean param1Boolean) {
/*     */       super(param1Boolean);
/*     */       this.consumer = param1DoubleConsumer;
/*     */     }
/*     */     public StreamShape inputShape() {
/*     */       return StreamShape.DOUBLE_VALUE;
/*     */     } }
/*     */   static final class ForEachTask<S, T> extends CountedCompleter<Void> { private Spliterator<S> spliterator;
/*     */     private final Sink<S> sink;
/*     */     private final PipelineHelper<T> helper;
/*     */     private long targetSize;
/*     */     
/*     */     ForEachTask(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator, Sink<S> param1Sink) {
/* 262 */       super((CountedCompleter<?>)null);
/* 263 */       this.sink = param1Sink;
/* 264 */       this.helper = param1PipelineHelper;
/* 265 */       this.spliterator = param1Spliterator;
/* 266 */       this.targetSize = 0L;
/*     */     }
/*     */     
/*     */     ForEachTask(ForEachTask<S, T> param1ForEachTask, Spliterator<S> param1Spliterator) {
/* 270 */       super(param1ForEachTask);
/* 271 */       this.spliterator = param1Spliterator;
/* 272 */       this.sink = param1ForEachTask.sink;
/* 273 */       this.targetSize = param1ForEachTask.targetSize;
/* 274 */       this.helper = param1ForEachTask.helper;
/*     */     }
/*     */ 
/*     */     
/*     */     public void compute() {
/* 279 */       Spliterator<S> spliterator = this.spliterator;
/* 280 */       long l1 = spliterator.estimateSize(); long l2;
/* 281 */       if ((l2 = this.targetSize) == 0L)
/* 282 */         this.targetSize = l2 = AbstractTask.suggestTargetSize(l1); 
/* 283 */       boolean bool = StreamOpFlag.SHORT_CIRCUIT.isKnown(this.helper.getStreamAndOpFlags());
/* 284 */       boolean bool1 = false;
/* 285 */       Sink<S> sink = this.sink;
/* 286 */       ForEachTask<S, T> forEachTask = this;
/* 287 */       while (!bool || !sink.cancellationRequested()) {
/* 288 */         ForEachTask<S, T> forEachTask2; Spliterator<S> spliterator1; if (l1 <= l2 || (
/* 289 */           spliterator1 = spliterator.trySplit()) == null) {
/* 290 */           forEachTask.helper.copyInto(sink, spliterator);
/*     */           break;
/*     */         } 
/* 293 */         ForEachTask<S, T> forEachTask1 = new ForEachTask(forEachTask, spliterator1);
/* 294 */         forEachTask.addToPendingCount(1);
/*     */         
/* 296 */         if (bool1) {
/* 297 */           bool1 = false;
/* 298 */           spliterator = spliterator1;
/* 299 */           forEachTask2 = forEachTask;
/* 300 */           forEachTask = forEachTask1;
/*     */         } else {
/*     */           
/* 303 */           bool1 = true;
/* 304 */           forEachTask2 = forEachTask1;
/*     */         } 
/* 306 */         forEachTask2.fork();
/* 307 */         l1 = spliterator.estimateSize();
/*     */       } 
/* 309 */       forEachTask.spliterator = null;
/* 310 */       forEachTask.propagateCompletion();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ForEachOrderedTask<S, T>
/*     */     extends CountedCompleter<Void>
/*     */   {
/*     */     private final PipelineHelper<T> helper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Spliterator<S> spliterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final long targetSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ConcurrentHashMap<ForEachOrderedTask<S, T>, ForEachOrderedTask<S, T>> completionMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Sink<T> action;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ForEachOrderedTask<S, T> leftPredecessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Node<T> node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ForEachOrderedTask(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator, Sink<T> param1Sink) {
/* 375 */       super((CountedCompleter<?>)null);
/* 376 */       this.helper = param1PipelineHelper;
/* 377 */       this.spliterator = param1Spliterator;
/* 378 */       this.targetSize = AbstractTask.suggestTargetSize(param1Spliterator.estimateSize());
/*     */       
/* 380 */       this.completionMap = new ConcurrentHashMap<>(Math.max(16, AbstractTask.getLeafTarget() << 1));
/* 381 */       this.action = param1Sink;
/* 382 */       this.leftPredecessor = null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     ForEachOrderedTask(ForEachOrderedTask<S, T> param1ForEachOrderedTask1, Spliterator<S> param1Spliterator, ForEachOrderedTask<S, T> param1ForEachOrderedTask2) {
/* 388 */       super(param1ForEachOrderedTask1);
/* 389 */       this.helper = param1ForEachOrderedTask1.helper;
/* 390 */       this.spliterator = param1Spliterator;
/* 391 */       this.targetSize = param1ForEachOrderedTask1.targetSize;
/* 392 */       this.completionMap = param1ForEachOrderedTask1.completionMap;
/* 393 */       this.action = param1ForEachOrderedTask1.action;
/* 394 */       this.leftPredecessor = param1ForEachOrderedTask2;
/*     */     }
/*     */ 
/*     */     
/*     */     public final void compute() {
/* 399 */       doCompute(this);
/*     */     }
/*     */     
/*     */     private static <S, T> void doCompute(ForEachOrderedTask<S, T> param1ForEachOrderedTask) {
/* 403 */       Spliterator<S> spliterator1 = param1ForEachOrderedTask.spliterator;
/* 404 */       long l = param1ForEachOrderedTask.targetSize;
/* 405 */       boolean bool = false; Spliterator<S> spliterator2;
/* 406 */       while (spliterator1.estimateSize() > l && (
/* 407 */         spliterator2 = spliterator1.trySplit()) != null) {
/* 408 */         ForEachOrderedTask<S, T> forEachOrderedTask3, forEachOrderedTask1 = new ForEachOrderedTask<>(param1ForEachOrderedTask, spliterator2, param1ForEachOrderedTask.leftPredecessor);
/*     */         
/* 410 */         ForEachOrderedTask<S, T> forEachOrderedTask2 = new ForEachOrderedTask<>(param1ForEachOrderedTask, spliterator1, forEachOrderedTask1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 416 */         param1ForEachOrderedTask.addToPendingCount(1);
/*     */ 
/*     */         
/* 419 */         forEachOrderedTask2.addToPendingCount(1);
/* 420 */         param1ForEachOrderedTask.completionMap.put(forEachOrderedTask1, forEachOrderedTask2);
/*     */ 
/*     */         
/* 423 */         if (param1ForEachOrderedTask.leftPredecessor != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 433 */           forEachOrderedTask1.addToPendingCount(1);
/*     */ 
/*     */           
/* 436 */           if (param1ForEachOrderedTask.completionMap.replace(param1ForEachOrderedTask.leftPredecessor, param1ForEachOrderedTask, forEachOrderedTask1)) {
/*     */ 
/*     */             
/* 439 */             param1ForEachOrderedTask.addToPendingCount(-1);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 444 */             forEachOrderedTask1.addToPendingCount(-1);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 449 */         if (bool) {
/* 450 */           bool = false;
/* 451 */           spliterator1 = spliterator2;
/* 452 */           param1ForEachOrderedTask = forEachOrderedTask1;
/* 453 */           forEachOrderedTask3 = forEachOrderedTask2;
/*     */         } else {
/*     */           
/* 456 */           bool = true;
/* 457 */           param1ForEachOrderedTask = forEachOrderedTask2;
/* 458 */           forEachOrderedTask3 = forEachOrderedTask1;
/*     */         } 
/* 460 */         forEachOrderedTask3.fork();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 471 */       if (param1ForEachOrderedTask.getPendingCount() > 0) {
/*     */ 
/*     */ 
/*     */         
/* 475 */         IntFunction<T[]> intFunction = param1Int -> new Object[param1Int];
/* 476 */         Node.Builder<T> builder = param1ForEachOrderedTask.helper.makeNodeBuilder(param1ForEachOrderedTask.helper
/* 477 */             .exactOutputSizeIfKnown(spliterator1), intFunction);
/*     */         
/* 479 */         param1ForEachOrderedTask.node = ((Node.Builder<T>)param1ForEachOrderedTask.helper.wrapAndCopyInto(builder, spliterator1)).build();
/* 480 */         param1ForEachOrderedTask.spliterator = null;
/*     */       } 
/* 482 */       param1ForEachOrderedTask.tryComplete();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onCompletion(CountedCompleter<?> param1CountedCompleter) {
/* 487 */       if (this.node != null) {
/*     */         
/* 489 */         this.node.forEach(this.action);
/* 490 */         this.node = null;
/*     */       }
/* 492 */       else if (this.spliterator != null) {
/*     */         
/* 494 */         this.helper.wrapAndCopyInto(this.action, this.spliterator);
/* 495 */         this.spliterator = null;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 502 */       ForEachOrderedTask forEachOrderedTask = this.completionMap.remove(this);
/* 503 */       if (forEachOrderedTask != null)
/* 504 */         forEachOrderedTask.tryComplete(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/ForEachOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */