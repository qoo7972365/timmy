/*      */ package java.util.stream;
/*      */ 
/*      */ import java.util.Comparator;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoubleSupplier;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntSupplier;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongSupplier;
/*      */ import java.util.function.Supplier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class StreamSpliterators
/*      */ {
/*      */   private static abstract class AbstractWrappingSpliterator<P_IN, P_OUT, T_BUFFER extends AbstractSpinedBuffer>
/*      */     implements Spliterator<P_OUT>
/*      */   {
/*      */     final boolean isParallel;
/*      */     final PipelineHelper<P_OUT> ph;
/*      */     private Supplier<Spliterator<P_IN>> spliteratorSupplier;
/*      */     Spliterator<P_IN> spliterator;
/*      */     Sink<P_IN> bufferSink;
/*      */     BooleanSupplier pusher;
/*      */     long nextToConsume;
/*      */     T_BUFFER buffer;
/*      */     boolean finished;
/*      */     
/*      */     AbstractWrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  119 */       this.ph = param1PipelineHelper;
/*  120 */       this.spliteratorSupplier = param1Supplier;
/*  121 */       this.spliterator = null;
/*  122 */       this.isParallel = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AbstractWrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  132 */       this.ph = param1PipelineHelper;
/*  133 */       this.spliteratorSupplier = null;
/*  134 */       this.spliterator = param1Spliterator;
/*  135 */       this.isParallel = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void init() {
/*  142 */       if (this.spliterator == null) {
/*  143 */         this.spliterator = this.spliteratorSupplier.get();
/*  144 */         this.spliteratorSupplier = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean doAdvance() {
/*  154 */       if (this.buffer == null) {
/*  155 */         if (this.finished) {
/*  156 */           return false;
/*      */         }
/*  158 */         init();
/*  159 */         initPartialTraversalState();
/*  160 */         this.nextToConsume = 0L;
/*  161 */         this.bufferSink.begin(this.spliterator.getExactSizeIfKnown());
/*  162 */         return fillBuffer();
/*      */       } 
/*      */       
/*  165 */       this.nextToConsume++;
/*  166 */       boolean bool = (this.nextToConsume < this.buffer.count());
/*  167 */       if (!bool) {
/*  168 */         this.nextToConsume = 0L;
/*  169 */         this.buffer.clear();
/*  170 */         bool = fillBuffer();
/*      */       } 
/*  172 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract AbstractWrappingSpliterator<P_IN, P_OUT, ?> wrap(Spliterator<P_IN> param1Spliterator);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void initPartialTraversalState();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator<P_OUT> trySplit() {
/*  190 */       if (this.isParallel && !this.finished) {
/*  191 */         init();
/*      */         
/*  193 */         Spliterator<P_IN> spliterator = this.spliterator.trySplit();
/*  194 */         return (spliterator == null) ? null : wrap(spliterator);
/*      */       } 
/*      */       
/*  197 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean fillBuffer() {
/*  206 */       while (this.buffer.count() == 0L) {
/*  207 */         if (this.bufferSink.cancellationRequested() || !this.pusher.getAsBoolean()) {
/*  208 */           if (this.finished) {
/*  209 */             return false;
/*      */           }
/*  211 */           this.bufferSink.end();
/*  212 */           this.finished = true;
/*      */         } 
/*      */       } 
/*      */       
/*  216 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public final long estimateSize() {
/*  221 */       init();
/*      */ 
/*      */ 
/*      */       
/*  225 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public final long getExactSizeIfKnown() {
/*  230 */       init();
/*  231 */       return StreamOpFlag.SIZED.isKnown(this.ph.getStreamAndOpFlags()) ? this.spliterator
/*  232 */         .getExactSizeIfKnown() : -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final int characteristics() {
/*  238 */       init();
/*      */ 
/*      */       
/*  241 */       int i = StreamOpFlag.toCharacteristics(StreamOpFlag.toStreamFlags(this.ph.getStreamAndOpFlags()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  249 */       if ((i & 0x40) != 0) {
/*  250 */         i &= 0xFFFFBFBF;
/*  251 */         i |= this.spliterator.characteristics() & 0x4040;
/*      */       } 
/*      */       
/*  254 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super P_OUT> getComparator() {
/*  259 */       if (!hasCharacteristics(4))
/*  260 */         throw new IllegalStateException(); 
/*  261 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public final String toString() {
/*  266 */       return String.format("%s[%s]", new Object[] { getClass().getName(), this.spliterator });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class WrappingSpliterator<P_IN, P_OUT>
/*      */     extends AbstractWrappingSpliterator<P_IN, P_OUT, SpinedBuffer<P_OUT>>
/*      */   {
/*      */     WrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  276 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     WrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  282 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     WrappingSpliterator<P_IN, P_OUT> wrap(Spliterator<P_IN> param1Spliterator) {
/*  287 */       return new WrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  292 */       SpinedBuffer<P_OUT> spinedBuffer = new SpinedBuffer();
/*  293 */       this.buffer = spinedBuffer;
/*  294 */       this.bufferSink = this.ph.wrapSink(spinedBuffer::accept);
/*  295 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super P_OUT> param1Consumer) {
/*  300 */       Objects.requireNonNull(param1Consumer);
/*  301 */       boolean bool = doAdvance();
/*  302 */       if (bool)
/*  303 */         param1Consumer.accept(this.buffer.get(this.nextToConsume)); 
/*  304 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super P_OUT> param1Consumer) {
/*  309 */       if (this.buffer == null && !this.finished)
/*  310 */       { Objects.requireNonNull(param1Consumer);
/*  311 */         init();
/*      */         
/*  313 */         this.ph.wrapAndCopyInto(param1Consumer::accept, this.spliterator);
/*  314 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  317 */         } while (tryAdvance(param1Consumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class IntWrappingSpliterator<P_IN>
/*      */     extends AbstractWrappingSpliterator<P_IN, Integer, SpinedBuffer.OfInt>
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     IntWrappingSpliterator(PipelineHelper<Integer> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  329 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     IntWrappingSpliterator(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  335 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     StreamSpliterators.AbstractWrappingSpliterator<P_IN, Integer, ?> wrap(Spliterator<P_IN> param1Spliterator) {
/*  340 */       return new IntWrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  345 */       SpinedBuffer.OfInt ofInt = new SpinedBuffer.OfInt();
/*  346 */       this.buffer = ofInt;
/*  347 */       this.bufferSink = this.ph.wrapSink(ofInt::accept);
/*  348 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfInt trySplit() {
/*  353 */       return (Spliterator.OfInt)super.trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/*  358 */       Objects.requireNonNull(param1IntConsumer);
/*  359 */       boolean bool = doAdvance();
/*  360 */       if (bool)
/*  361 */         param1IntConsumer.accept(this.buffer.get(this.nextToConsume)); 
/*  362 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/*  367 */       if (this.buffer == null && !this.finished)
/*  368 */       { Objects.requireNonNull(param1IntConsumer);
/*  369 */         init();
/*      */         
/*  371 */         this.ph.wrapAndCopyInto(param1IntConsumer::accept, this.spliterator);
/*  372 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  375 */         } while (tryAdvance(param1IntConsumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LongWrappingSpliterator<P_IN>
/*      */     extends AbstractWrappingSpliterator<P_IN, Long, SpinedBuffer.OfLong>
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     LongWrappingSpliterator(PipelineHelper<Long> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  387 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     LongWrappingSpliterator(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  393 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     StreamSpliterators.AbstractWrappingSpliterator<P_IN, Long, ?> wrap(Spliterator<P_IN> param1Spliterator) {
/*  398 */       return new LongWrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  403 */       SpinedBuffer.OfLong ofLong = new SpinedBuffer.OfLong();
/*  404 */       this.buffer = ofLong;
/*  405 */       this.bufferSink = this.ph.wrapSink(ofLong::accept);
/*  406 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfLong trySplit() {
/*  411 */       return (Spliterator.OfLong)super.trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/*  416 */       Objects.requireNonNull(param1LongConsumer);
/*  417 */       boolean bool = doAdvance();
/*  418 */       if (bool)
/*  419 */         param1LongConsumer.accept(this.buffer.get(this.nextToConsume)); 
/*  420 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/*  425 */       if (this.buffer == null && !this.finished)
/*  426 */       { Objects.requireNonNull(param1LongConsumer);
/*  427 */         init();
/*      */         
/*  429 */         this.ph.wrapAndCopyInto(param1LongConsumer::accept, this.spliterator);
/*  430 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  433 */         } while (tryAdvance(param1LongConsumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class DoubleWrappingSpliterator<P_IN>
/*      */     extends AbstractWrappingSpliterator<P_IN, Double, SpinedBuffer.OfDouble>
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     DoubleWrappingSpliterator(PipelineHelper<Double> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  445 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleWrappingSpliterator(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  451 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     StreamSpliterators.AbstractWrappingSpliterator<P_IN, Double, ?> wrap(Spliterator<P_IN> param1Spliterator) {
/*  456 */       return new DoubleWrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  461 */       SpinedBuffer.OfDouble ofDouble = new SpinedBuffer.OfDouble();
/*  462 */       this.buffer = ofDouble;
/*  463 */       this.bufferSink = this.ph.wrapSink(ofDouble::accept);
/*  464 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfDouble trySplit() {
/*  469 */       return (Spliterator.OfDouble)super.trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/*  474 */       Objects.requireNonNull(param1DoubleConsumer);
/*  475 */       boolean bool = doAdvance();
/*  476 */       if (bool)
/*  477 */         param1DoubleConsumer.accept(this.buffer.get(this.nextToConsume)); 
/*  478 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/*  483 */       if (this.buffer == null && !this.finished)
/*  484 */       { Objects.requireNonNull(param1DoubleConsumer);
/*  485 */         init();
/*      */         
/*  487 */         this.ph.wrapAndCopyInto(param1DoubleConsumer::accept, this.spliterator);
/*  488 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  491 */         } while (tryAdvance(param1DoubleConsumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class DelegatingSpliterator<T, T_SPLITR extends Spliterator<T>>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     private final Supplier<? extends T_SPLITR> supplier;
/*      */ 
/*      */     
/*      */     private T_SPLITR s;
/*      */ 
/*      */ 
/*      */     
/*      */     DelegatingSpliterator(Supplier<? extends T_SPLITR> param1Supplier) {
/*  509 */       this.supplier = param1Supplier;
/*      */     }
/*      */     
/*      */     T_SPLITR get() {
/*  513 */       if (this.s == null) {
/*  514 */         this.s = this.supplier.get();
/*      */       }
/*  516 */       return this.s;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public T_SPLITR trySplit() {
/*  522 */       return (T_SPLITR)get().trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/*  527 */       return get().tryAdvance(param1Consumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/*  532 */       get().forEachRemaining(param1Consumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  537 */       return get().estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  542 */       return get().characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super T> getComparator() {
/*  547 */       return get().getComparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getExactSizeIfKnown() {
/*  552 */       return get().getExactSizeIfKnown();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  557 */       return getClass().getName() + "[" + get() + "]";
/*      */     }
/*      */     
/*      */     static class OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
/*      */       extends DelegatingSpliterator<T, T_SPLITR>
/*      */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
/*      */       OfPrimitive(Supplier<? extends T_SPLITR> param2Supplier) {
/*  564 */         super(param2Supplier);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(T_CONS param2T_CONS) {
/*  569 */         return ((Spliterator.OfPrimitive)get()).tryAdvance(param2T_CONS);
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(T_CONS param2T_CONS) {
/*  574 */         ((Spliterator.OfPrimitive)get()).forEachRemaining(param2T_CONS);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<Integer, IntConsumer, Spliterator.OfInt>
/*      */       implements Spliterator.OfInt
/*      */     {
/*      */       OfInt(Supplier<Spliterator.OfInt> param2Supplier) {
/*  583 */         super(param2Supplier);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends OfPrimitive<Long, LongConsumer, Spliterator.OfLong>
/*      */       implements Spliterator.OfLong
/*      */     {
/*      */       OfLong(Supplier<Spliterator.OfLong> param2Supplier) {
/*  592 */         super(param2Supplier);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble>
/*      */       implements Spliterator.OfDouble
/*      */     {
/*      */       OfDouble(Supplier<Spliterator.OfDouble> param2Supplier) {
/*  601 */         super(param2Supplier);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class SliceSpliterator<T, T_SPLITR extends Spliterator<T>>
/*      */   {
/*      */     final long sliceOrigin;
/*      */ 
/*      */     
/*      */     final long sliceFence;
/*      */ 
/*      */     
/*      */     T_SPLITR s;
/*      */ 
/*      */     
/*      */     long index;
/*      */     
/*      */     long fence;
/*      */ 
/*      */     
/*      */     SliceSpliterator(T_SPLITR param1T_SPLITR, long param1Long1, long param1Long2, long param1Long3, long param1Long4) {
/*  625 */       assert param1T_SPLITR.hasCharacteristics(16384);
/*  626 */       this.s = param1T_SPLITR;
/*  627 */       this.sliceOrigin = param1Long1;
/*  628 */       this.sliceFence = param1Long2;
/*  629 */       this.index = param1Long3;
/*  630 */       this.fence = param1Long4;
/*      */     }
/*      */     public T_SPLITR trySplit() {
/*      */       Spliterator spliterator;
/*      */       long l1;
/*      */       long l2;
/*  636 */       if (this.sliceOrigin >= this.fence) {
/*  637 */         return null;
/*      */       }
/*  639 */       if (this.index >= this.fence) {
/*  640 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  649 */         spliterator = this.s.trySplit();
/*  650 */         if (spliterator == null) {
/*  651 */           return null;
/*      */         }
/*  653 */         l1 = this.index + spliterator.estimateSize();
/*  654 */         l2 = Math.min(l1, this.sliceFence);
/*  655 */         if (this.sliceOrigin >= l2) {
/*      */ 
/*      */ 
/*      */           
/*  659 */           this.index = l2; continue;
/*      */         } 
/*  661 */         if (l2 >= this.sliceFence) {
/*      */ 
/*      */ 
/*      */           
/*  665 */           this.s = (T_SPLITR)spliterator;
/*  666 */           this.fence = l2; continue;
/*      */         }  break;
/*  668 */       }  if (this.index >= this.sliceOrigin && l1 <= this.sliceFence) {
/*      */ 
/*      */         
/*  671 */         this.index = l2;
/*  672 */         return (T_SPLITR)spliterator;
/*      */       } 
/*      */ 
/*      */       
/*  676 */       return makeSpliterator((T_SPLITR)spliterator, this.sliceOrigin, this.sliceFence, this.index, this.index = l2);
/*      */     }
/*      */     
/*      */     protected abstract T_SPLITR makeSpliterator(T_SPLITR param1T_SPLITR, long param1Long1, long param1Long2, long param1Long3, long param1Long4);
/*      */     
/*      */     public long estimateSize() {
/*  682 */       return (this.sliceOrigin < this.fence) ? (this.fence - 
/*  683 */         Math.max(this.sliceOrigin, this.index)) : 0L;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  687 */       return this.s.characteristics();
/*      */     }
/*      */     
/*      */     static final class OfRef<T>
/*      */       extends SliceSpliterator<T, Spliterator<T>>
/*      */       implements Spliterator<T>
/*      */     {
/*      */       OfRef(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2) {
/*  695 */         this(param2Spliterator, param2Long1, param2Long2, 0L, Math.min(param2Spliterator.estimateSize(), param2Long2));
/*      */       }
/*      */ 
/*      */       
/*      */       private OfRef(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  700 */         super(param2Spliterator, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator<T> makeSpliterator(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  707 */         return new OfRef(param2Spliterator, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/*  712 */         Objects.requireNonNull(param2Consumer);
/*      */         
/*  714 */         if (this.sliceOrigin >= this.fence) {
/*  715 */           return false;
/*      */         }
/*  717 */         while (this.sliceOrigin > this.index) {
/*  718 */           this.s.tryAdvance(param2Object -> { 
/*  719 */               }); this.index++;
/*      */         } 
/*      */         
/*  722 */         if (this.index >= this.fence) {
/*  723 */           return false;
/*      */         }
/*  725 */         this.index++;
/*  726 */         return this.s.tryAdvance(param2Consumer);
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super T> param2Consumer) {
/*  731 */         Objects.requireNonNull(param2Consumer);
/*      */         
/*  733 */         if (this.sliceOrigin >= this.fence) {
/*      */           return;
/*      */         }
/*  736 */         if (this.index >= this.fence) {
/*      */           return;
/*      */         }
/*  739 */         if (this.index >= this.sliceOrigin && this.index + this.s.estimateSize() <= this.sliceFence) {
/*      */           
/*  741 */           this.s.forEachRemaining(param2Consumer);
/*  742 */           this.index = this.fence;
/*      */         } else {
/*      */           
/*  745 */           while (this.sliceOrigin > this.index) {
/*  746 */             this.s.tryAdvance(param2Object -> { 
/*  747 */                 }); this.index++;
/*      */           } 
/*      */           
/*  750 */           for (; this.index < this.fence; this.index++) {
/*  751 */             this.s.tryAdvance(param2Consumer);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static abstract class OfPrimitive<T, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_CONS>
/*      */       extends SliceSpliterator<T, T_SPLITR>
/*      */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>
/*      */     {
/*      */       OfPrimitive(T_SPLITR param2T_SPLITR, long param2Long1, long param2Long2) {
/*  764 */         this(param2T_SPLITR, param2Long1, param2Long2, 0L, Math.min(param2T_SPLITR.estimateSize(), param2Long2));
/*      */       }
/*      */ 
/*      */       
/*      */       private OfPrimitive(T_SPLITR param2T_SPLITR, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  769 */         super(param2T_SPLITR, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(T_CONS param2T_CONS) {
/*  774 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/*  776 */         if (this.sliceOrigin >= this.fence) {
/*  777 */           return false;
/*      */         }
/*  779 */         while (this.sliceOrigin > this.index) {
/*  780 */           ((Spliterator.OfPrimitive)this.s).tryAdvance(emptyConsumer());
/*  781 */           this.index++;
/*      */         } 
/*      */         
/*  784 */         if (this.index >= this.fence) {
/*  785 */           return false;
/*      */         }
/*  787 */         this.index++;
/*  788 */         return ((Spliterator.OfPrimitive)this.s).tryAdvance(param2T_CONS);
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(T_CONS param2T_CONS) {
/*  793 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/*  795 */         if (this.sliceOrigin >= this.fence) {
/*      */           return;
/*      */         }
/*  798 */         if (this.index >= this.fence) {
/*      */           return;
/*      */         }
/*  801 */         if (this.index >= this.sliceOrigin && this.index + ((Spliterator.OfPrimitive)this.s).estimateSize() <= this.sliceFence) {
/*      */           
/*  803 */           ((Spliterator.OfPrimitive)this.s).forEachRemaining(param2T_CONS);
/*  804 */           this.index = this.fence;
/*      */         } else {
/*      */           
/*  807 */           while (this.sliceOrigin > this.index) {
/*  808 */             ((Spliterator.OfPrimitive)this.s).tryAdvance(emptyConsumer());
/*  809 */             this.index++;
/*      */           } 
/*      */           
/*  812 */           for (; this.index < this.fence; this.index++)
/*  813 */             ((Spliterator.OfPrimitive)this.s).tryAdvance(param2T_CONS); 
/*      */         } 
/*      */       }
/*      */       
/*      */       protected abstract T_CONS emptyConsumer();
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<Integer, Spliterator.OfInt, IntConsumer>
/*      */       implements Spliterator.OfInt {
/*      */       OfInt(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2) {
/*  824 */         super(param2OfInt, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       OfInt(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  829 */         super(param2OfInt, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator.OfInt makeSpliterator(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  836 */         return new OfInt(param2OfInt, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       protected IntConsumer emptyConsumer() {
/*  841 */         return param2Int -> {
/*      */           
/*      */           };
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong extends OfPrimitive<Long, Spliterator.OfLong, LongConsumer> implements Spliterator.OfLong { OfLong(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2) {
/*  848 */         super(param2OfLong, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       OfLong(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  853 */         super(param2OfLong, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator.OfLong makeSpliterator(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  860 */         return new OfLong(param2OfLong, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       protected LongConsumer emptyConsumer() {
/*  865 */         return param2Long -> {
/*      */           
/*      */           };
/*      */       } }
/*      */     
/*      */     static final class OfDouble extends OfPrimitive<Double, Spliterator.OfDouble, DoubleConsumer> implements Spliterator.OfDouble {
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2) {
/*  872 */         super(param2OfDouble, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  877 */         super(param2OfDouble, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  884 */         return new OfDouble(param2OfDouble, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       protected DoubleConsumer emptyConsumer() {
/*  889 */         return param2Double -> {
/*      */           
/*      */           };
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class UnorderedSliceSpliterator<T, T_SPLITR extends Spliterator<T>>
/*      */   {
/*      */     static final int CHUNK_SIZE = 128;
/*      */     
/*      */     protected final T_SPLITR s;
/*      */     
/*      */     protected final boolean unlimited;
/*      */     
/*      */     protected final int chunkSize;
/*      */     
/*      */     private final long skipThreshold;
/*      */     
/*      */     private final AtomicLong permits;
/*      */ 
/*      */     
/*      */     UnorderedSliceSpliterator(T_SPLITR param1T_SPLITR, long param1Long1, long param1Long2) {
/*  914 */       this.s = param1T_SPLITR;
/*  915 */       this.unlimited = (param1Long2 < 0L);
/*  916 */       this.skipThreshold = (param1Long2 >= 0L) ? param1Long2 : 0L;
/*  917 */       this.chunkSize = (param1Long2 >= 0L) ? (int)Math.min(128L, (param1Long1 + param1Long2) / 
/*  918 */           AbstractTask.getLeafTarget() + 1L) : 128;
/*  919 */       this.permits = new AtomicLong((param1Long2 >= 0L) ? (param1Long1 + param1Long2) : param1Long1);
/*      */     }
/*      */ 
/*      */     
/*      */     UnorderedSliceSpliterator(T_SPLITR param1T_SPLITR, UnorderedSliceSpliterator<T, T_SPLITR> param1UnorderedSliceSpliterator) {
/*  924 */       this.s = param1T_SPLITR;
/*  925 */       this.unlimited = param1UnorderedSliceSpliterator.unlimited;
/*  926 */       this.permits = param1UnorderedSliceSpliterator.permits;
/*  927 */       this.skipThreshold = param1UnorderedSliceSpliterator.skipThreshold;
/*  928 */       this.chunkSize = param1UnorderedSliceSpliterator.chunkSize;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final long acquirePermits(long param1Long) {
/*      */       long l1;
/*      */       long l2;
/*  949 */       assert param1Long > 0L;
/*      */       do {
/*  951 */         l1 = this.permits.get();
/*  952 */         if (l1 == 0L)
/*  953 */           return this.unlimited ? param1Long : 0L; 
/*  954 */         l2 = Math.min(l1, param1Long);
/*  955 */       } while (l2 > 0L && 
/*  956 */         !this.permits.compareAndSet(l1, l1 - l2));
/*      */       
/*  958 */       if (this.unlimited)
/*  959 */         return Math.max(param1Long - l2, 0L); 
/*  960 */       if (l1 > this.skipThreshold) {
/*  961 */         return Math.max(l2 - l1 - this.skipThreshold, 0L);
/*      */       }
/*  963 */       return l2;
/*      */     }
/*      */     
/*  966 */     enum PermitStatus { NO_MORE, MAYBE_MORE, UNLIMITED; }
/*      */ 
/*      */     
/*      */     protected final PermitStatus permitStatus() {
/*  970 */       if (this.permits.get() > 0L) {
/*  971 */         return PermitStatus.MAYBE_MORE;
/*      */       }
/*  973 */       return this.unlimited ? PermitStatus.UNLIMITED : PermitStatus.NO_MORE;
/*      */     }
/*      */ 
/*      */     
/*      */     public final T_SPLITR trySplit() {
/*  978 */       if (this.permits.get() == 0L) {
/*  979 */         return null;
/*      */       }
/*  981 */       Spliterator spliterator = this.s.trySplit();
/*  982 */       return (spliterator == null) ? null : makeSpliterator((T_SPLITR)spliterator);
/*      */     }
/*      */     
/*      */     protected abstract T_SPLITR makeSpliterator(T_SPLITR param1T_SPLITR);
/*      */     
/*      */     public final long estimateSize() {
/*  988 */       return this.s.estimateSize();
/*      */     }
/*      */     
/*      */     public final int characteristics() {
/*  992 */       return this.s.characteristics() & 0xFFFFBFAF;
/*      */     }
/*      */     
/*      */     static final class OfRef<T>
/*      */       extends UnorderedSliceSpliterator<T, Spliterator<T>>
/*      */       implements Spliterator<T>, Consumer<T> {
/*      */       T tmpSlot;
/*      */       
/*      */       OfRef(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2) {
/* 1001 */         super(param2Spliterator, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfRef(Spliterator<T> param2Spliterator, OfRef<T> param2OfRef) {
/* 1005 */         super(param2Spliterator, param2OfRef);
/*      */       }
/*      */ 
/*      */       
/*      */       public final void accept(T param2T) {
/* 1010 */         this.tmpSlot = param2T;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/* 1015 */         Objects.requireNonNull(param2Consumer);
/*      */         
/* 1017 */         while (permitStatus() != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1018 */           if (!this.s.tryAdvance(this))
/* 1019 */             return false; 
/* 1020 */           if (acquirePermits(1L) == 1L) {
/* 1021 */             param2Consumer.accept(this.tmpSlot);
/* 1022 */             this.tmpSlot = null;
/* 1023 */             return true;
/*      */           } 
/*      */         } 
/* 1026 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super T> param2Consumer) {
/* 1031 */         Objects.requireNonNull(param2Consumer);
/*      */         
/* 1033 */         StreamSpliterators.ArrayBuffer.OfRef<? super T> ofRef = null;
/*      */         StreamSpliterators.UnorderedSliceSpliterator.PermitStatus permitStatus;
/* 1035 */         while ((permitStatus = permitStatus()) != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1036 */           if (permitStatus == StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.MAYBE_MORE) {
/*      */             
/* 1038 */             if (ofRef == null) {
/* 1039 */               ofRef = new StreamSpliterators.ArrayBuffer.OfRef(this.chunkSize);
/*      */             } else {
/* 1041 */               ofRef.reset();
/* 1042 */             }  long l = 0L; do {  }
/* 1043 */             while (this.s.tryAdvance(ofRef) && ++l < this.chunkSize);
/* 1044 */             if (l == 0L)
/*      */               return; 
/* 1046 */             ofRef.forEach(param2Consumer, acquirePermits(l));
/*      */             
/*      */             continue;
/*      */           } 
/* 1050 */           this.s.forEachRemaining(param2Consumer);
/*      */           return;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator<T> makeSpliterator(Spliterator<T> param2Spliterator) {
/* 1058 */         return new OfRef(param2Spliterator, this);
/*      */       }
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
/*      */     static abstract class OfPrimitive<T, T_CONS, T_BUFF extends StreamSpliterators.ArrayBuffer.OfPrimitive<T_CONS>, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
/*      */       extends UnorderedSliceSpliterator<T, T_SPLITR>
/*      */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>
/*      */     {
/*      */       OfPrimitive(T_SPLITR param2T_SPLITR, long param2Long1, long param2Long2) {
/* 1076 */         super(param2T_SPLITR, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfPrimitive(T_SPLITR param2T_SPLITR, OfPrimitive<T, T_CONS, T_BUFF, T_SPLITR> param2OfPrimitive) {
/* 1080 */         super(param2T_SPLITR, param2OfPrimitive);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(T_CONS param2T_CONS) {
/* 1085 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/* 1087 */         OfPrimitive ofPrimitive = this;
/*      */         
/* 1089 */         while (permitStatus() != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1090 */           if (!((Spliterator.OfPrimitive)this.s).tryAdvance(ofPrimitive))
/* 1091 */             return false; 
/* 1092 */           if (acquirePermits(1L) == 1L) {
/* 1093 */             acceptConsumed(param2T_CONS);
/* 1094 */             return true;
/*      */           } 
/*      */         } 
/* 1097 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(T_CONS param2T_CONS) {
/* 1104 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/* 1106 */         T_BUFF t_BUFF = null;
/*      */         StreamSpliterators.UnorderedSliceSpliterator.PermitStatus permitStatus;
/* 1108 */         while ((permitStatus = permitStatus()) != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1109 */           if (permitStatus == StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.MAYBE_MORE) {
/*      */             
/* 1111 */             if (t_BUFF == null) {
/* 1112 */               t_BUFF = bufferCreate(this.chunkSize);
/*      */             } else {
/* 1114 */               t_BUFF.reset();
/*      */             } 
/* 1116 */             T_BUFF t_BUFF1 = t_BUFF;
/* 1117 */             long l = 0L; do {  }
/* 1118 */             while (((Spliterator.OfPrimitive)this.s).tryAdvance(t_BUFF1) && ++l < this.chunkSize);
/* 1119 */             if (l == 0L)
/*      */               return; 
/* 1121 */             t_BUFF.forEach(param2T_CONS, acquirePermits(l));
/*      */             
/*      */             continue;
/*      */           } 
/* 1125 */           ((Spliterator.OfPrimitive)this.s).forEachRemaining(param2T_CONS);
/*      */           return;
/*      */         } 
/*      */       }
/*      */       
/*      */       protected abstract void acceptConsumed(T_CONS param2T_CONS);
/*      */       
/*      */       protected abstract T_BUFF bufferCreate(int param2Int);
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<Integer, IntConsumer, StreamSpliterators.ArrayBuffer.OfInt, Spliterator.OfInt>
/*      */       implements Spliterator.OfInt, IntConsumer {
/*      */       int tmpValue;
/*      */       
/*      */       OfInt(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2) {
/* 1141 */         super(param2OfInt, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfInt(Spliterator.OfInt param2OfInt, OfInt param2OfInt1) {
/* 1145 */         super(param2OfInt, param2OfInt1);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(int param2Int) {
/* 1150 */         this.tmpValue = param2Int;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void acceptConsumed(IntConsumer param2IntConsumer) {
/* 1155 */         param2IntConsumer.accept(this.tmpValue);
/*      */       }
/*      */ 
/*      */       
/*      */       protected StreamSpliterators.ArrayBuffer.OfInt bufferCreate(int param2Int) {
/* 1160 */         return new StreamSpliterators.ArrayBuffer.OfInt(param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       protected Spliterator.OfInt makeSpliterator(Spliterator.OfInt param2OfInt) {
/* 1165 */         return new OfInt(param2OfInt, this);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends OfPrimitive<Long, LongConsumer, StreamSpliterators.ArrayBuffer.OfLong, Spliterator.OfLong>
/*      */       implements Spliterator.OfLong, LongConsumer
/*      */     {
/*      */       long tmpValue;
/*      */       
/*      */       OfLong(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2) {
/* 1176 */         super(param2OfLong, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfLong(Spliterator.OfLong param2OfLong, OfLong param2OfLong1) {
/* 1180 */         super(param2OfLong, param2OfLong1);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(long param2Long) {
/* 1185 */         this.tmpValue = param2Long;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void acceptConsumed(LongConsumer param2LongConsumer) {
/* 1190 */         param2LongConsumer.accept(this.tmpValue);
/*      */       }
/*      */ 
/*      */       
/*      */       protected StreamSpliterators.ArrayBuffer.OfLong bufferCreate(int param2Int) {
/* 1195 */         return new StreamSpliterators.ArrayBuffer.OfLong(param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       protected Spliterator.OfLong makeSpliterator(Spliterator.OfLong param2OfLong) {
/* 1200 */         return new OfLong(param2OfLong, this);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends OfPrimitive<Double, DoubleConsumer, StreamSpliterators.ArrayBuffer.OfDouble, Spliterator.OfDouble>
/*      */       implements Spliterator.OfDouble, DoubleConsumer
/*      */     {
/*      */       double tmpValue;
/*      */       
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2) {
/* 1211 */         super(param2OfDouble, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, OfDouble param2OfDouble1) {
/* 1215 */         super(param2OfDouble, param2OfDouble1);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(double param2Double) {
/* 1220 */         this.tmpValue = param2Double;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void acceptConsumed(DoubleConsumer param2DoubleConsumer) {
/* 1225 */         param2DoubleConsumer.accept(this.tmpValue);
/*      */       }
/*      */ 
/*      */       
/*      */       protected StreamSpliterators.ArrayBuffer.OfDouble bufferCreate(int param2Int) {
/* 1230 */         return new StreamSpliterators.ArrayBuffer.OfDouble(param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       protected Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble param2OfDouble) {
/* 1235 */         return new OfDouble(param2OfDouble, this);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class DistinctSpliterator<T>
/*      */     implements Spliterator<T>, Consumer<T>
/*      */   {
/* 1247 */     private static final Object NULL_VALUE = new Object();
/*      */ 
/*      */     
/*      */     private final Spliterator<T> s;
/*      */ 
/*      */     
/*      */     private final ConcurrentHashMap<T, Boolean> seen;
/*      */     
/*      */     private T tmpSlot;
/*      */ 
/*      */     
/*      */     DistinctSpliterator(Spliterator<T> param1Spliterator) {
/* 1259 */       this(param1Spliterator, new ConcurrentHashMap<>());
/*      */     }
/*      */     
/*      */     private DistinctSpliterator(Spliterator<T> param1Spliterator, ConcurrentHashMap<T, Boolean> param1ConcurrentHashMap) {
/* 1263 */       this.s = param1Spliterator;
/* 1264 */       this.seen = param1ConcurrentHashMap;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(T param1T) {
/* 1269 */       this.tmpSlot = param1T;
/*      */     }
/*      */ 
/*      */     
/*      */     private T mapNull(T param1T) {
/* 1274 */       return (param1T != null) ? param1T : (T)NULL_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/* 1279 */       while (this.s.tryAdvance(this)) {
/* 1280 */         if (this.seen.putIfAbsent(mapNull(this.tmpSlot), Boolean.TRUE) == null) {
/* 1281 */           param1Consumer.accept(this.tmpSlot);
/* 1282 */           this.tmpSlot = null;
/* 1283 */           return true;
/*      */         } 
/*      */       } 
/* 1286 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/* 1291 */       this.s.forEachRemaining(param1Object -> {
/*      */             if (this.seen.putIfAbsent(mapNull((T)param1Object), Boolean.TRUE) == null) {
/*      */               param1Consumer.accept(param1Object);
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<T> trySplit() {
/* 1300 */       Spliterator<T> spliterator = this.s.trySplit();
/* 1301 */       return (spliterator != null) ? new DistinctSpliterator(spliterator, this.seen) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1306 */       return this.s.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1311 */       return this.s.characteristics() & 0xFFFFBFAB | 0x1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Comparator<? super T> getComparator() {
/* 1318 */       return this.s.getComparator();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class InfiniteSupplyingSpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     long estimate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected InfiniteSupplyingSpliterator(long param1Long) {
/* 1336 */       this.estimate = param1Long;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1341 */       return this.estimate;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1346 */       return 1024;
/*      */     }
/*      */     
/*      */     static final class OfRef<T> extends InfiniteSupplyingSpliterator<T> {
/*      */       final Supplier<T> s;
/*      */       
/*      */       OfRef(long param2Long, Supplier<T> param2Supplier) {
/* 1353 */         super(param2Long);
/* 1354 */         this.s = param2Supplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/* 1359 */         Objects.requireNonNull(param2Consumer);
/*      */         
/* 1361 */         param2Consumer.accept(this.s.get());
/* 1362 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator<T> trySplit() {
/* 1367 */         if (this.estimate == 0L)
/* 1368 */           return null; 
/* 1369 */         return new OfRef(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends InfiniteSupplyingSpliterator<Integer> implements Spliterator.OfInt {
/*      */       final IntSupplier s;
/*      */       
/*      */       OfInt(long param2Long, IntSupplier param2IntSupplier) {
/* 1378 */         super(param2Long);
/* 1379 */         this.s = param2IntSupplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(IntConsumer param2IntConsumer) {
/* 1384 */         Objects.requireNonNull(param2IntConsumer);
/*      */         
/* 1386 */         param2IntConsumer.accept(this.s.getAsInt());
/* 1387 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfInt trySplit() {
/* 1392 */         if (this.estimate == 0L)
/* 1393 */           return null; 
/* 1394 */         return new OfInt(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends InfiniteSupplyingSpliterator<Long> implements Spliterator.OfLong {
/*      */       final LongSupplier s;
/*      */       
/*      */       OfLong(long param2Long, LongSupplier param2LongSupplier) {
/* 1403 */         super(param2Long);
/* 1404 */         this.s = param2LongSupplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(LongConsumer param2LongConsumer) {
/* 1409 */         Objects.requireNonNull(param2LongConsumer);
/*      */         
/* 1411 */         param2LongConsumer.accept(this.s.getAsLong());
/* 1412 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfLong trySplit() {
/* 1417 */         if (this.estimate == 0L)
/* 1418 */           return null; 
/* 1419 */         return new OfLong(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends InfiniteSupplyingSpliterator<Double> implements Spliterator.OfDouble {
/*      */       final DoubleSupplier s;
/*      */       
/*      */       OfDouble(long param2Long, DoubleSupplier param2DoubleSupplier) {
/* 1428 */         super(param2Long);
/* 1429 */         this.s = param2DoubleSupplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(DoubleConsumer param2DoubleConsumer) {
/* 1434 */         Objects.requireNonNull(param2DoubleConsumer);
/*      */         
/* 1436 */         param2DoubleConsumer.accept(this.s.getAsDouble());
/* 1437 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfDouble trySplit() {
/* 1442 */         if (this.estimate == 0L)
/* 1443 */           return null; 
/* 1444 */         return new OfDouble(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class ArrayBuffer
/*      */   {
/*      */     int index;
/*      */     
/*      */     void reset() {
/* 1454 */       this.index = 0;
/*      */     }
/*      */     
/*      */     static final class OfRef<T> extends ArrayBuffer implements Consumer<T> {
/*      */       final Object[] array;
/*      */       
/*      */       OfRef(int param2Int) {
/* 1461 */         this.array = new Object[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(T param2T) {
/* 1466 */         this.array[this.index++] = param2T;
/*      */       }
/*      */       
/*      */       public void forEach(Consumer<? super T> param2Consumer, long param2Long) {
/* 1470 */         for (byte b = 0; b < param2Long; b++) {
/*      */           
/* 1472 */           Object object = this.array[b];
/* 1473 */           param2Consumer.accept((T)object);
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     static abstract class OfPrimitive<T_CONS>
/*      */       extends ArrayBuffer {
/*      */       int index;
/*      */       
/*      */       void reset() {
/* 1483 */         this.index = 0;
/*      */       }
/*      */       
/*      */       abstract void forEach(T_CONS param2T_CONS, long param2Long);
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<IntConsumer> implements IntConsumer {
/*      */       final int[] array;
/*      */       
/*      */       OfInt(int param2Int) {
/* 1494 */         this.array = new int[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(int param2Int) {
/* 1499 */         this.array[this.index++] = param2Int;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEach(IntConsumer param2IntConsumer, long param2Long) {
/* 1504 */         for (byte b = 0; b < param2Long; b++)
/* 1505 */           param2IntConsumer.accept(this.array[b]); 
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends OfPrimitive<LongConsumer>
/*      */       implements LongConsumer {
/*      */       final long[] array;
/*      */       
/*      */       OfLong(int param2Int) {
/* 1515 */         this.array = new long[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(long param2Long) {
/* 1520 */         this.array[this.index++] = param2Long;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEach(LongConsumer param2LongConsumer, long param2Long) {
/* 1525 */         for (byte b = 0; b < param2Long; b++)
/* 1526 */           param2LongConsumer.accept(this.array[b]); 
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends OfPrimitive<DoubleConsumer>
/*      */       implements DoubleConsumer {
/*      */       final double[] array;
/*      */       
/*      */       OfDouble(int param2Int) {
/* 1536 */         this.array = new double[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(double param2Double) {
/* 1541 */         this.array[this.index++] = param2Double;
/*      */       }
/*      */ 
/*      */       
/*      */       void forEach(DoubleConsumer param2DoubleConsumer, long param2Long) {
/* 1546 */         for (byte b = 0; b < param2Long; b++)
/* 1547 */           param2DoubleConsumer.accept(this.array[b]); 
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/StreamSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */