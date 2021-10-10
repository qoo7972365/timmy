/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.function.Predicate;
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
/*     */ final class FindOps
/*     */ {
/*     */   public static <T> TerminalOp<T, Optional<T>> makeRef(boolean paramBoolean) {
/*  58 */     return new FindOp<>(paramBoolean, StreamShape.REFERENCE, Optional.empty(), Optional::isPresent, OfRef::new);
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
/*     */   public static TerminalOp<Integer, OptionalInt> makeInt(boolean paramBoolean) {
/*  70 */     return new FindOp<>(paramBoolean, StreamShape.INT_VALUE, OptionalInt.empty(), OptionalInt::isPresent, OfInt::new);
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
/*     */   public static TerminalOp<Long, OptionalLong> makeLong(boolean paramBoolean) {
/*  82 */     return new FindOp<>(paramBoolean, StreamShape.LONG_VALUE, OptionalLong.empty(), OptionalLong::isPresent, OfLong::new);
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
/*     */   public static TerminalOp<Double, OptionalDouble> makeDouble(boolean paramBoolean) {
/*  94 */     return new FindOp<>(paramBoolean, StreamShape.DOUBLE_VALUE, OptionalDouble.empty(), OptionalDouble::isPresent, OfDouble::new);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class FindOp<T, O>
/*     */     implements TerminalOp<T, O>
/*     */   {
/*     */     private final StreamShape shape;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean mustFindFirst;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final O emptyValue;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final Predicate<O> presentPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final Supplier<TerminalSink<T, O>> sinkSupplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     FindOp(boolean param1Boolean, StreamShape param1StreamShape, O param1O, Predicate<O> param1Predicate, Supplier<TerminalSink<T, O>> param1Supplier) {
/* 132 */       this.mustFindFirst = param1Boolean;
/* 133 */       this.shape = param1StreamShape;
/* 134 */       this.emptyValue = param1O;
/* 135 */       this.presentPredicate = param1Predicate;
/* 136 */       this.sinkSupplier = param1Supplier;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOpFlags() {
/* 141 */       return StreamOpFlag.IS_SHORT_CIRCUIT | (this.mustFindFirst ? 0 : StreamOpFlag.NOT_ORDERED);
/*     */     }
/*     */ 
/*     */     
/*     */     public StreamShape inputShape() {
/* 146 */       return this.shape;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> O evaluateSequential(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator) {
/* 152 */       Object object = ((TerminalSink)param1PipelineHelper.wrapAndCopyInto(this.sinkSupplier.get(), param1Spliterator)).get();
/* 153 */       return (object != null) ? (O)object : this.emptyValue;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> O evaluateParallel(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) {
/* 159 */       return (new FindOps.FindTask<>(this, param1PipelineHelper, param1Spliterator)).invoke();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class FindSink<T, O>
/*     */     implements TerminalSink<T, O>
/*     */   {
/*     */     boolean hasValue;
/*     */ 
/*     */ 
/*     */     
/*     */     T value;
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(T param1T) {
/* 178 */       if (!this.hasValue) {
/* 179 */         this.hasValue = true;
/* 180 */         this.value = param1T;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancellationRequested() {
/* 186 */       return this.hasValue;
/*     */     }
/*     */     
/*     */     static final class OfRef<T>
/*     */       extends FindSink<T, Optional<T>>
/*     */     {
/*     */       public Optional<T> get() {
/* 193 */         return this.hasValue ? Optional.<T>of(this.value) : null;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     static final class OfInt
/*     */       extends FindSink<Integer, OptionalInt>
/*     */       implements Sink.OfInt
/*     */     {
/*     */       public void accept(int param2Int) {
/* 203 */         accept(Integer.valueOf(param2Int));
/*     */       }
/*     */ 
/*     */       
/*     */       public OptionalInt get() {
/* 208 */         return this.hasValue ? OptionalInt.of(this.value.intValue()) : null;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     static final class OfLong
/*     */       extends FindSink<Long, OptionalLong>
/*     */       implements Sink.OfLong
/*     */     {
/*     */       public void accept(long param2Long) {
/* 218 */         accept(Long.valueOf(param2Long));
/*     */       }
/*     */ 
/*     */       
/*     */       public OptionalLong get() {
/* 223 */         return this.hasValue ? OptionalLong.of(this.value.longValue()) : null;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     static final class OfDouble
/*     */       extends FindSink<Double, OptionalDouble>
/*     */       implements Sink.OfDouble
/*     */     {
/*     */       public void accept(double param2Double) {
/* 233 */         accept(Double.valueOf(param2Double));
/*     */       }
/*     */       
/*     */       public OptionalDouble get()
/*     */       {
/* 238 */         return this.hasValue ? OptionalDouble.of(this.value.doubleValue()) : null; } } } static final class OfRef<T> extends FindSink<T, Optional<T>> { public Optional<T> get() { return this.hasValue ? Optional.<T>of(this.value) : null; } } static final class OfInt extends FindSink<Integer, OptionalInt> implements Sink.OfInt { public void accept(int param1Int) { accept(Integer.valueOf(param1Int)); } public OptionalInt get() { return this.hasValue ? OptionalInt.of(this.value.intValue()) : null; } } static final class OfLong extends FindSink<Long, OptionalLong> implements Sink.OfLong { public void accept(long param1Long) { accept(Long.valueOf(param1Long)); } public OptionalLong get() { return this.hasValue ? OptionalLong.of(this.value.longValue()) : null; } } static final class OfDouble extends FindSink<Double, OptionalDouble> implements Sink.OfDouble { public OptionalDouble get() { return this.hasValue ? OptionalDouble.of(this.value.doubleValue()) : null; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(double param1Double) {
/*     */       accept(Double.valueOf(param1Double));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class FindTask<P_IN, P_OUT, O>
/*     */     extends AbstractShortCircuitTask<P_IN, P_OUT, O, FindTask<P_IN, P_OUT, O>>
/*     */   {
/*     */     private final FindOps.FindOp<P_OUT, O> op;
/*     */ 
/*     */     
/*     */     FindTask(FindOps.FindOp<P_OUT, O> param1FindOp, PipelineHelper<P_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) {
/* 257 */       super(param1PipelineHelper, param1Spliterator);
/* 258 */       this.op = param1FindOp;
/*     */     }
/*     */     
/*     */     FindTask(FindTask<P_IN, P_OUT, O> param1FindTask, Spliterator<P_IN> param1Spliterator) {
/* 262 */       super(param1FindTask, param1Spliterator);
/* 263 */       this.op = param1FindTask.op;
/*     */     }
/*     */ 
/*     */     
/*     */     protected FindTask<P_IN, P_OUT, O> makeChild(Spliterator<P_IN> param1Spliterator) {
/* 268 */       return new FindTask(this, param1Spliterator);
/*     */     }
/*     */ 
/*     */     
/*     */     protected O getEmptyResult() {
/* 273 */       return this.op.emptyValue;
/*     */     }
/*     */     
/*     */     private void foundResult(O param1O) {
/* 277 */       if (isLeftmostNode()) {
/* 278 */         shortCircuit(param1O);
/*     */       } else {
/* 280 */         cancelLaterNodes();
/*     */       } 
/*     */     }
/*     */     
/*     */     protected O doLeaf() {
/* 285 */       Object object = ((TerminalSink)this.helper.<P_IN, Sink>wrapAndCopyInto(this.op.sinkSupplier.get(), this.spliterator)).get();
/* 286 */       if (!this.op.mustFindFirst) {
/* 287 */         if (object != null)
/* 288 */           shortCircuit((O)object); 
/* 289 */         return null;
/*     */       } 
/*     */       
/* 292 */       if (object != null) {
/* 293 */         foundResult((O)object);
/* 294 */         return (O)object;
/*     */       } 
/*     */       
/* 297 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onCompletion(CountedCompleter<?> param1CountedCompleter) {
/* 303 */       if (this.op.mustFindFirst) {
/* 304 */         for (FindTask<P_IN, P_OUT, O> findTask1 = this.leftChild, findTask2 = null; findTask1 != findTask2; 
/* 305 */           findTask2 = findTask1, findTask1 = this.rightChild) {
/* 306 */           O o = findTask1.getLocalResult();
/* 307 */           if (o != null && this.op.presentPredicate.test(o)) {
/* 308 */             setLocalResult(o);
/* 309 */             foundResult(o);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 314 */       super.onCompletion(param1CountedCompleter);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/FindOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */