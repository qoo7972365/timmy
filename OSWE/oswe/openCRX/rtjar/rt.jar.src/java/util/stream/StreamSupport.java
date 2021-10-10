/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
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
/*     */ public final class StreamSupport
/*     */ {
/*     */   public static <T> Stream<T> stream(Spliterator<T> paramSpliterator, boolean paramBoolean) {
/*  68 */     Objects.requireNonNull(paramSpliterator);
/*  69 */     return new ReferencePipeline.Head<>(paramSpliterator, 
/*  70 */         StreamOpFlag.fromCharacteristics(paramSpliterator), paramBoolean);
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
/*     */   public static <T> Stream<T> stream(Supplier<? extends Spliterator<T>> paramSupplier, int paramInt, boolean paramBoolean) {
/* 110 */     Objects.requireNonNull(paramSupplier);
/* 111 */     return new ReferencePipeline.Head<>(paramSupplier, 
/* 112 */         StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
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
/*     */   public static IntStream intStream(Spliterator.OfInt paramOfInt, boolean paramBoolean) {
/* 138 */     return new IntPipeline.Head(paramOfInt, 
/* 139 */         StreamOpFlag.fromCharacteristics(paramOfInt), paramBoolean);
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
/*     */   public static IntStream intStream(Supplier<? extends Spliterator.OfInt> paramSupplier, int paramInt, boolean paramBoolean) {
/* 178 */     return new IntPipeline.Head((Supplier)paramSupplier, 
/* 179 */         StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
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
/*     */   public static LongStream longStream(Spliterator.OfLong paramOfLong, boolean paramBoolean) {
/* 206 */     return new LongPipeline.Head(paramOfLong, 
/* 207 */         StreamOpFlag.fromCharacteristics(paramOfLong), paramBoolean);
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
/*     */   public static LongStream longStream(Supplier<? extends Spliterator.OfLong> paramSupplier, int paramInt, boolean paramBoolean) {
/* 246 */     return new LongPipeline.Head((Supplier)paramSupplier, 
/* 247 */         StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
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
/*     */   public static DoubleStream doubleStream(Spliterator.OfDouble paramOfDouble, boolean paramBoolean) {
/* 274 */     return new DoublePipeline.Head(paramOfDouble, 
/* 275 */         StreamOpFlag.fromCharacteristics(paramOfDouble), paramBoolean);
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
/*     */   public static DoubleStream doubleStream(Supplier<? extends Spliterator.OfDouble> paramSupplier, int paramInt, boolean paramBoolean) {
/* 314 */     return new DoublePipeline.Head((Supplier)paramSupplier, 
/* 315 */         StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/StreamSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */