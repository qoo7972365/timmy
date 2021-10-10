/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LongSummaryStatistics;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.function.LongFunction;
/*     */ import java.util.function.LongPredicate;
/*     */ import java.util.function.LongSupplier;
/*     */ import java.util.function.LongToDoubleFunction;
/*     */ import java.util.function.LongToIntFunction;
/*     */ import java.util.function.LongUnaryOperator;
/*     */ import java.util.function.ObjLongConsumer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LongStream
/*     */   extends BaseStream<Long, LongStream>
/*     */ {
/*     */   static Builder builder() {
/* 686 */     return new Streams.LongStreamBuilderImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LongStream empty() {
/* 695 */     return StreamSupport.longStream(Spliterators.emptyLongSpliterator(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LongStream of(long paramLong) {
/* 705 */     return StreamSupport.longStream(new Streams.LongStreamBuilderImpl(paramLong), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LongStream of(long... paramVarArgs) {
/* 715 */     return Arrays.stream(paramVarArgs);
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
/*     */   static LongStream iterate(final long seed, final LongUnaryOperator f) {
/* 735 */     Objects.requireNonNull(f);
/* 736 */     PrimitiveIterator.OfLong ofLong = new PrimitiveIterator.OfLong() {
/* 737 */         long t = seed;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 741 */           return true;
/*     */         }
/*     */ 
/*     */         
/*     */         public long nextLong() {
/* 746 */           long l = this.t;
/* 747 */           this.t = f.applyAsLong(this.t);
/* 748 */           return l;
/*     */         }
/*     */       };
/* 751 */     return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(ofLong, 1296), false);
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
/*     */   static LongStream generate(LongSupplier paramLongSupplier) {
/* 765 */     Objects.requireNonNull(paramLongSupplier);
/* 766 */     return StreamSupport.longStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfLong(Long.MAX_VALUE, paramLongSupplier), false);
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
/*     */   static LongStream range(long paramLong1, long paramLong2) {
/* 788 */     if (paramLong1 >= paramLong2)
/* 789 */       return empty(); 
/* 790 */     if (paramLong2 - paramLong1 < 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 795 */       long l = paramLong1 + Long.divideUnsigned(paramLong2 - paramLong1, 2L) + 1L;
/* 796 */       return concat(range(paramLong1, l), range(l, paramLong2));
/*     */     } 
/* 798 */     return StreamSupport.longStream(new Streams.RangeLongSpliterator(paramLong1, paramLong2, false), false);
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
/*     */   static LongStream rangeClosed(long paramLong1, long paramLong2) {
/* 821 */     if (paramLong1 > paramLong2)
/* 822 */       return empty(); 
/* 823 */     if (paramLong2 - paramLong1 + 1L <= 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 829 */       long l = paramLong1 + Long.divideUnsigned(paramLong2 - paramLong1, 2L) + 1L;
/* 830 */       return concat(range(paramLong1, l), rangeClosed(l, paramLong2));
/*     */     } 
/* 832 */     return StreamSupport.longStream(new Streams.RangeLongSpliterator(paramLong1, paramLong2, true), false);
/*     */   } LongStream filter(LongPredicate paramLongPredicate);
/*     */   LongStream map(LongUnaryOperator paramLongUnaryOperator);
/*     */   <U> Stream<U> mapToObj(LongFunction<? extends U> paramLongFunction);
/*     */   IntStream mapToInt(LongToIntFunction paramLongToIntFunction);
/*     */   DoubleStream mapToDouble(LongToDoubleFunction paramLongToDoubleFunction);
/*     */   LongStream flatMap(LongFunction<? extends LongStream> paramLongFunction);
/*     */   LongStream distinct();
/*     */   LongStream sorted();
/*     */   LongStream peek(LongConsumer paramLongConsumer);
/*     */   LongStream limit(long paramLong);
/*     */   LongStream skip(long paramLong);
/*     */   void forEach(LongConsumer paramLongConsumer);
/*     */   void forEachOrdered(LongConsumer paramLongConsumer);
/*     */   long[] toArray();
/*     */   long reduce(long paramLong, LongBinaryOperator paramLongBinaryOperator);
/*     */   OptionalLong reduce(LongBinaryOperator paramLongBinaryOperator);
/*     */   <R> R collect(Supplier<R> paramSupplier, ObjLongConsumer<R> paramObjLongConsumer, BiConsumer<R, R> paramBiConsumer);
/*     */   long sum();
/*     */   OptionalLong min();
/*     */   OptionalLong max();
/*     */   long count();
/*     */   static LongStream concat(LongStream paramLongStream1, LongStream paramLongStream2) {
/* 855 */     Objects.requireNonNull(paramLongStream1);
/* 856 */     Objects.requireNonNull(paramLongStream2);
/*     */ 
/*     */     
/* 859 */     Streams.ConcatSpliterator.OfLong ofLong = new Streams.ConcatSpliterator.OfLong(paramLongStream1.spliterator(), paramLongStream2.spliterator());
/* 860 */     LongStream longStream = StreamSupport.longStream(ofLong, (paramLongStream1.isParallel() || paramLongStream2.isParallel()));
/* 861 */     return longStream.onClose(Streams.composedClose(paramLongStream1, paramLongStream2));
/*     */   }
/*     */ 
/*     */   
/*     */   OptionalDouble average();
/*     */ 
/*     */   
/*     */   LongSummaryStatistics summaryStatistics();
/*     */ 
/*     */   
/*     */   boolean anyMatch(LongPredicate paramLongPredicate);
/*     */ 
/*     */   
/*     */   boolean allMatch(LongPredicate paramLongPredicate);
/*     */ 
/*     */   
/*     */   boolean noneMatch(LongPredicate paramLongPredicate);
/*     */ 
/*     */   
/*     */   OptionalLong findFirst();
/*     */ 
/*     */   
/*     */   OptionalLong findAny();
/*     */ 
/*     */   
/*     */   DoubleStream asDoubleStream();
/*     */ 
/*     */   
/*     */   Stream<Long> boxed();
/*     */ 
/*     */   
/*     */   LongStream sequential();
/*     */   
/*     */   LongStream parallel();
/*     */   
/*     */   PrimitiveIterator.OfLong iterator();
/*     */   
/*     */   Spliterator.OfLong spliterator();
/*     */   
/*     */   public static interface Builder
/*     */     extends LongConsumer
/*     */   {
/*     */     default Builder add(long param1Long) {
/* 904 */       accept(param1Long);
/* 905 */       return this;
/*     */     }
/*     */     
/*     */     void accept(long param1Long);
/*     */     
/*     */     LongStream build();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/LongStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */