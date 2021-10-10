/*      */ package java.util.stream;
/*      */ 
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.DoubleSummaryStatistics;
/*      */ import java.util.EnumSet;
/*      */ import java.util.IntSummaryStatistics;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.LongSummaryStatistics;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.StringJoiner;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BinaryOperator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.function.ToDoubleFunction;
/*      */ import java.util.function.ToIntFunction;
/*      */ import java.util.function.ToLongFunction;
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
/*      */ public final class Collectors
/*      */ {
/*  107 */   static final Set<Collector.Characteristics> CH_CONCURRENT_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
/*      */ 
/*      */ 
/*      */   
/*  111 */   static final Set<Collector.Characteristics> CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED));
/*      */ 
/*      */   
/*  114 */   static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
/*      */   
/*  116 */   static final Set<Collector.Characteristics> CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
/*      */   
/*  118 */   static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();
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
/*      */   private static <T> BinaryOperator<T> throwingMerger() {
/*  133 */     return (paramObject1, paramObject2) -> {
/*      */         throw new IllegalStateException(String.format("Duplicate key %s", new Object[] { paramObject1 }));
/*      */       };
/*      */   }
/*      */   private static <I, R> Function<I, R> castingIdentity() {
/*  138 */     return paramObject -> paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CollectorImpl<T, A, R>
/*      */     implements Collector<T, A, R>
/*      */   {
/*      */     private final Supplier<A> supplier;
/*      */ 
/*      */     
/*      */     private final BiConsumer<A, T> accumulator;
/*      */     
/*      */     private final BinaryOperator<A> combiner;
/*      */     
/*      */     private final Function<A, R> finisher;
/*      */     
/*      */     private final Set<Collector.Characteristics> characteristics;
/*      */ 
/*      */     
/*      */     CollectorImpl(Supplier<A> param1Supplier, BiConsumer<A, T> param1BiConsumer, BinaryOperator<A> param1BinaryOperator, Function<A, R> param1Function, Set<Collector.Characteristics> param1Set) {
/*  159 */       this.supplier = param1Supplier;
/*  160 */       this.accumulator = param1BiConsumer;
/*  161 */       this.combiner = param1BinaryOperator;
/*  162 */       this.finisher = param1Function;
/*  163 */       this.characteristics = param1Set;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CollectorImpl(Supplier<A> param1Supplier, BiConsumer<A, T> param1BiConsumer, BinaryOperator<A> param1BinaryOperator, Set<Collector.Characteristics> param1Set) {
/*  170 */       this(param1Supplier, param1BiConsumer, param1BinaryOperator, Collectors.castingIdentity(), param1Set);
/*      */     }
/*      */ 
/*      */     
/*      */     public BiConsumer<A, T> accumulator() {
/*  175 */       return this.accumulator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Supplier<A> supplier() {
/*  180 */       return this.supplier;
/*      */     }
/*      */ 
/*      */     
/*      */     public BinaryOperator<A> combiner() {
/*  185 */       return this.combiner;
/*      */     }
/*      */ 
/*      */     
/*      */     public Function<A, R> finisher() {
/*  190 */       return this.finisher;
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Collector.Characteristics> characteristics() {
/*  195 */       return this.characteristics;
/*      */     }
/*      */   }
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
/*      */   public static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> paramSupplier) {
/*  213 */     return new CollectorImpl<>(paramSupplier, Collection::add, (paramCollection1, paramCollection2) -> { paramCollection1.addAll(paramCollection2); return paramCollection1; }CH_ID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, List<T>> toList() {
/*  230 */     return new CollectorImpl<>(java.util.ArrayList::new, List::add, (paramList1, paramList2) -> { paramList1.addAll(paramList2); return paramList1; }CH_ID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Set<T>> toSet() {
/*  251 */     return new CollectorImpl<>(java.util.HashSet::new, Set::add, (paramSet1, paramSet2) -> { paramSet1.addAll(paramSet2); return paramSet1; }CH_UNORDERED_ID);
/*      */   }
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
/*      */   public static Collector<CharSequence, ?, String> joining() {
/*  264 */     return new CollectorImpl<>(StringBuilder::new, StringBuilder::append, (paramStringBuilder1, paramStringBuilder2) -> { paramStringBuilder1.append(paramStringBuilder2); return paramStringBuilder1; }StringBuilder::toString, CH_NOID);
/*      */   }
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
/*      */   public static Collector<CharSequence, ?, String> joining(CharSequence paramCharSequence) {
/*  279 */     return joining(paramCharSequence, "", "");
/*      */   }
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
/*      */   public static Collector<CharSequence, ?, String> joining(CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3) {
/*  298 */     return new CollectorImpl<>(() -> new StringJoiner(paramCharSequence1, paramCharSequence2, paramCharSequence3), StringJoiner::add, StringJoiner::merge, StringJoiner::toString, CH_NOID);
/*      */   }
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
/*      */   private static <K, V, M extends Map<K, V>> BinaryOperator<M> mapMerger(BinaryOperator<V> paramBinaryOperator) {
/*  318 */     return (paramMap1, paramMap2) -> {
/*      */         for (Map.Entry entry : paramMap2.entrySet()) {
/*      */           paramMap1.merge(entry.getKey(), entry.getValue(), paramBinaryOperator);
/*      */         }
/*      */         return paramMap1;
/*      */       };
/*      */   }
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
/*      */   public static <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> paramFunction, Collector<? super U, A, R> paramCollector) {
/*  353 */     BiConsumer<A, ? super U> biConsumer = paramCollector.accumulator();
/*  354 */     return new CollectorImpl<>(paramCollector.supplier(), (paramObject1, paramObject2) -> paramBiConsumer.accept(paramObject1, paramFunction.apply(paramObject2)), paramCollector
/*      */         
/*  356 */         .combiner(), paramCollector.finisher(), paramCollector
/*  357 */         .characteristics());
/*      */   }
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
/*      */   public static <T, A, R, RR> Collector<T, A, RR> collectingAndThen(Collector<T, A, R> paramCollector, Function<R, RR> paramFunction) {
/*  380 */     Set<Collector.Characteristics> set = paramCollector.characteristics();
/*  381 */     if (set.contains(Collector.Characteristics.IDENTITY_FINISH)) {
/*  382 */       if (set.size() == 1) {
/*  383 */         set = CH_NOID;
/*      */       } else {
/*  385 */         set = EnumSet.copyOf(set);
/*  386 */         set.remove(Collector.Characteristics.IDENTITY_FINISH);
/*  387 */         set = Collections.unmodifiableSet(set);
/*      */       } 
/*      */     }
/*  390 */     return new CollectorImpl<>(paramCollector.supplier(), paramCollector
/*  391 */         .accumulator(), paramCollector
/*  392 */         .combiner(), paramCollector
/*  393 */         .finisher().andThen(paramFunction), set);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Long> counting() {
/*  413 */     return reducing(Long.valueOf(0L), paramObject -> Long.valueOf(1L), Long::sum);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> paramComparator) {
/*  432 */     return reducing(BinaryOperator.minBy(paramComparator));
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> paramComparator) {
/*  451 */     return reducing(BinaryOperator.maxBy(paramComparator));
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Integer> summingInt(ToIntFunction<? super T> paramToIntFunction) {
/*  465 */     return new CollectorImpl<>(() -> new int[1], (paramArrayOfint, paramObject) -> paramArrayOfint[0] = paramArrayOfint[0] + paramToIntFunction.applyAsInt((T)paramObject), (paramArrayOfint1, paramArrayOfint2) -> { paramArrayOfint1[0] = paramArrayOfint1[0] + paramArrayOfint2[0]; return paramArrayOfint1; }paramArrayOfint -> Integer.valueOf(paramArrayOfint[0]), CH_NOID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Long> summingLong(ToLongFunction<? super T> paramToLongFunction) {
/*  483 */     return new CollectorImpl<>(() -> new long[1], (paramArrayOflong, paramObject) -> paramArrayOflong[0] = paramArrayOflong[0] + paramToLongFunction.applyAsLong((T)paramObject), (paramArrayOflong1, paramArrayOflong2) -> { paramArrayOflong1[0] = paramArrayOflong1[0] + paramArrayOflong2[0]; return paramArrayOflong1; }paramArrayOflong -> Long.valueOf(paramArrayOflong[0]), CH_NOID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Double> summingDouble(ToDoubleFunction<? super T> paramToDoubleFunction) {
/*  516 */     return new CollectorImpl<>(() -> new double[3], (paramArrayOfdouble, paramObject) -> { sumWithCompensation(paramArrayOfdouble, paramToDoubleFunction.applyAsDouble(paramObject)); paramArrayOfdouble[2] = paramArrayOfdouble[2] + paramToDoubleFunction.applyAsDouble((T)paramObject); }(paramArrayOfdouble1, paramArrayOfdouble2) -> { sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[0]); paramArrayOfdouble1[2] = paramArrayOfdouble1[2] + paramArrayOfdouble2[2]; return sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[1]); }paramArrayOfdouble -> Double.valueOf(computeFinalSum(paramArrayOfdouble)), CH_NOID);
/*      */   }
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
/*      */   static double[] sumWithCompensation(double[] paramArrayOfdouble, double paramDouble) {
/*  539 */     double d1 = paramDouble - paramArrayOfdouble[1];
/*  540 */     double d2 = paramArrayOfdouble[0];
/*  541 */     double d3 = d2 + d1;
/*  542 */     paramArrayOfdouble[1] = d3 - d2 - d1;
/*  543 */     paramArrayOfdouble[0] = d3;
/*  544 */     return paramArrayOfdouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double computeFinalSum(double[] paramArrayOfdouble) {
/*  554 */     double d1 = paramArrayOfdouble[0] + paramArrayOfdouble[1];
/*  555 */     double d2 = paramArrayOfdouble[paramArrayOfdouble.length - 1];
/*  556 */     if (Double.isNaN(d1) && Double.isInfinite(d2)) {
/*  557 */       return d2;
/*      */     }
/*  559 */     return d1;
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> paramToIntFunction) {
/*  573 */     return new CollectorImpl<>(() -> new long[2], (paramArrayOflong, paramObject) -> { paramArrayOflong[0] = paramArrayOflong[0] + paramToIntFunction.applyAsInt((T)paramObject); paramArrayOflong[1] = paramArrayOflong[1] + 1L; }(paramArrayOflong1, paramArrayOflong2) -> { paramArrayOflong1[0] = paramArrayOflong1[0] + paramArrayOflong2[0]; paramArrayOflong1[1] = paramArrayOflong1[1] + paramArrayOflong2[1]; return paramArrayOflong1; }paramArrayOflong -> Double.valueOf((paramArrayOflong[1] == 0L) ? 0.0D : (paramArrayOflong[0] / paramArrayOflong[1])), CH_NOID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Double> averagingLong(ToLongFunction<? super T> paramToLongFunction) {
/*  591 */     return new CollectorImpl<>(() -> new long[2], (paramArrayOflong, paramObject) -> { paramArrayOflong[0] = paramArrayOflong[0] + paramToLongFunction.applyAsLong((T)paramObject); paramArrayOflong[1] = paramArrayOflong[1] + 1L; }(paramArrayOflong1, paramArrayOflong2) -> { paramArrayOflong1[0] = paramArrayOflong1[0] + paramArrayOflong2[0]; paramArrayOflong1[1] = paramArrayOflong1[1] + paramArrayOflong2[1]; return paramArrayOflong1; }paramArrayOflong -> Double.valueOf((paramArrayOflong[1] == 0L) ? 0.0D : (paramArrayOflong[0] / paramArrayOflong[1])), CH_NOID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Double> averagingDouble(ToDoubleFunction<? super T> paramToDoubleFunction) {
/*  628 */     return new CollectorImpl<>(() -> new double[4], (paramArrayOfdouble, paramObject) -> { sumWithCompensation(paramArrayOfdouble, paramToDoubleFunction.applyAsDouble(paramObject)); paramArrayOfdouble[2] = paramArrayOfdouble[2] + 1.0D; paramArrayOfdouble[3] = paramArrayOfdouble[3] + paramToDoubleFunction.applyAsDouble((T)paramObject); }(paramArrayOfdouble1, paramArrayOfdouble2) -> { sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[0]); sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[1]); paramArrayOfdouble1[2] = paramArrayOfdouble1[2] + paramArrayOfdouble2[2]; paramArrayOfdouble1[3] = paramArrayOfdouble1[3] + paramArrayOfdouble2[3]; return paramArrayOfdouble1; }paramArrayOfdouble -> Double.valueOf((paramArrayOfdouble[2] == 0.0D) ? 0.0D : (computeFinalSum(paramArrayOfdouble) / paramArrayOfdouble[2])), CH_NOID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, T> reducing(T paramT, BinaryOperator<T> paramBinaryOperator) {
/*  658 */     return new CollectorImpl<>(
/*  659 */         boxSupplier(paramT), (paramArrayOfObject, paramObject) -> paramArrayOfObject[0] = paramBinaryOperator.apply(paramArrayOfObject[0], paramObject), (paramArrayOfObject1, paramArrayOfObject2) -> { paramArrayOfObject1[0] = paramBinaryOperator.apply(paramArrayOfObject1[0], paramArrayOfObject2[0]); return paramArrayOfObject1; }paramArrayOfObject -> paramArrayOfObject[0], CH_NOID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> Supplier<T[]> boxSupplier(T paramT) {
/*  668 */     return () -> new Object[] { paramObject };
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Optional<T>> reducing(final BinaryOperator<T> op) {
/*  715 */     return new CollectorImpl<>(() -> new OptionalBox(), OptionalBox::accept, (paramOptionalBox1, paramOptionalBox2) -> { if (paramOptionalBox2.present) { class OptionalBox implements Consumer<T> { T value; boolean present; OptionalBox() { this.value = null; this.present = false; } public void accept(T param1T) { if (this.present) { this.value = op.apply(this.value, param1T); } else { this.value = param1T; this.present = true; }  } }; paramOptionalBox1.accept(paramOptionalBox2.value); }  return paramOptionalBox1; }paramOptionalBox -> Optional.ofNullable(paramOptionalBox.value), CH_NOID);
/*      */   }
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
/*      */   public static <T, U> Collector<T, ?, U> reducing(U paramU, Function<? super T, ? extends U> paramFunction, BinaryOperator<U> paramBinaryOperator) {
/*  759 */     return new CollectorImpl<>(
/*  760 */         boxSupplier(paramU), (paramArrayOfObject, paramObject) -> paramArrayOfObject[0] = paramBinaryOperator.apply(paramArrayOfObject[0], paramFunction.apply(paramObject)), (paramArrayOfObject1, paramArrayOfObject2) -> { paramArrayOfObject1[0] = paramBinaryOperator.apply(paramArrayOfObject1[0], paramArrayOfObject2[0]); return paramArrayOfObject1; }paramArrayOfObject -> paramArrayOfObject[0], CH_NOID);
/*      */   }
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
/*      */   public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> paramFunction) {
/*  805 */     return groupingBy(paramFunction, (Collector)toList());
/*      */   }
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
/*      */   public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> paramFunction, Collector<? super T, A, D> paramCollector) {
/*  853 */     return groupingBy(paramFunction, java.util.HashMap::new, paramCollector);
/*      */   }
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
/*      */   public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> paramFunction, Supplier<M> paramSupplier, Collector<? super T, A, D> paramCollector) {
/*  904 */     Supplier<A> supplier = paramCollector.supplier();
/*  905 */     BiConsumer<A, ? super T> biConsumer = paramCollector.accumulator();
/*  906 */     BiConsumer<?, T> biConsumer1 = (paramMap, paramObject) -> {
/*      */         Object object = Objects.requireNonNull(paramFunction.apply(paramObject), "element cannot be mapped to a null key");
/*      */         Object object1 = paramMap.computeIfAbsent(object, ());
/*      */         paramBiConsumer.accept(object1, paramObject);
/*      */       };
/*  911 */     BinaryOperator<Map<?, ?>> binaryOperator = mapMerger(paramCollector.combiner());
/*      */     
/*  913 */     Supplier<M> supplier1 = paramSupplier;
/*      */     
/*  915 */     if (paramCollector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
/*  916 */       return new CollectorImpl<>(supplier1, biConsumer1, binaryOperator, CH_ID);
/*      */     }
/*      */ 
/*      */     
/*  920 */     Function<A, D> function = paramCollector.finisher();
/*  921 */     Function<?, M> function1 = paramMap -> {
/*      */         paramMap.replaceAll(());
/*      */         
/*      */         return paramMap;
/*      */       };
/*      */     
/*  927 */     return new CollectorImpl<>(supplier1, biConsumer1, binaryOperator, function1, CH_NOID);
/*      */   }
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
/*      */   public static <T, K> Collector<T, ?, ConcurrentMap<K, List<T>>> groupingByConcurrent(Function<? super T, ? extends K> paramFunction) {
/*  967 */     return groupingByConcurrent(paramFunction, java.util.concurrent.ConcurrentHashMap::new, toList());
/*      */   }
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
/*      */   public static <T, K, A, D> Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> paramFunction, Collector<? super T, A, D> paramCollector) {
/* 1008 */     return groupingByConcurrent(paramFunction, java.util.concurrent.ConcurrentHashMap::new, paramCollector);
/*      */   }
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
/*      */   public static <T, K, A, D, M extends ConcurrentMap<K, D>> Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> paramFunction, Supplier<M> paramSupplier, Collector<? super T, A, D> paramCollector) {
/*      */     BiConsumer<?, T> biConsumer1;
/* 1055 */     Supplier<A> supplier = paramCollector.supplier();
/* 1056 */     BiConsumer<A, ? super T> biConsumer = paramCollector.accumulator();
/* 1057 */     BinaryOperator<Map<?, ?>> binaryOperator = mapMerger(paramCollector.combiner());
/*      */     
/* 1059 */     Supplier<M> supplier1 = paramSupplier;
/*      */     
/* 1061 */     if (paramCollector.characteristics().contains(Collector.Characteristics.CONCURRENT)) {
/* 1062 */       biConsumer1 = ((paramConcurrentMap, paramObject) -> {
/*      */           Object object = Objects.requireNonNull(paramFunction.apply(paramObject), "element cannot be mapped to a null key");
/*      */           
/*      */           Object object1 = paramConcurrentMap.computeIfAbsent(object, ());
/*      */           paramBiConsumer.accept(object1, paramObject);
/*      */         });
/*      */     } else {
/* 1069 */       biConsumer1 = ((paramConcurrentMap, paramObject) -> {
/*      */           Object object = Objects.requireNonNull(paramFunction.apply(paramObject), "element cannot be mapped to a null key");
/*      */           
/*      */           Object object1 = paramConcurrentMap.computeIfAbsent(object, ());
/*      */           synchronized (object1) {
/*      */             paramBiConsumer.accept(object1, paramObject);
/*      */           } 
/*      */         });
/*      */     } 
/* 1078 */     if (paramCollector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
/* 1079 */       return new CollectorImpl<>(supplier1, biConsumer1, binaryOperator, CH_CONCURRENT_ID);
/*      */     }
/*      */ 
/*      */     
/* 1083 */     Function<A, D> function = paramCollector.finisher();
/* 1084 */     Function<?, M> function1 = paramConcurrentMap -> {
/*      */         paramConcurrentMap.replaceAll(());
/*      */         
/*      */         return paramConcurrentMap;
/*      */       };
/*      */     
/* 1090 */     return new CollectorImpl<>(supplier1, biConsumer1, binaryOperator, function1, CH_CONCURRENT_NOID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> paramPredicate) {
/* 1110 */     return partitioningBy(paramPredicate, (Collector)toList());
/*      */   }
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
/*      */   public static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> paramPredicate, Collector<? super T, A, D> paramCollector) {
/* 1137 */     BiConsumer<A, ? super T> biConsumer = paramCollector.accumulator();
/* 1138 */     BiConsumer<?, T> biConsumer1 = (paramPartition, paramObject) -> paramBiConsumer.accept(paramPredicate.test(paramObject) ? paramPartition.forTrue : paramPartition.forFalse, paramObject);
/*      */     
/* 1140 */     BinaryOperator<A> binaryOperator = paramCollector.combiner();
/* 1141 */     BinaryOperator<?> binaryOperator1 = (paramPartition1, paramPartition2) -> new Partition(paramBinaryOperator.apply(paramPartition1.forTrue, paramPartition2.forTrue), paramBinaryOperator.apply(paramPartition1.forFalse, paramPartition2.forFalse));
/*      */ 
/*      */     
/* 1144 */     Supplier<?> supplier = () -> new Partition(paramCollector.supplier().get(), paramCollector.supplier().get());
/*      */ 
/*      */     
/* 1147 */     if (paramCollector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
/* 1148 */       return new CollectorImpl<>(supplier, biConsumer1, binaryOperator1, CH_ID);
/*      */     }
/*      */     
/* 1151 */     Function<?, Map<Boolean, D>> function = paramPartition -> new Partition(paramCollector.finisher().apply(paramPartition.forTrue), paramCollector.finisher().apply(paramPartition.forFalse));
/*      */ 
/*      */     
/* 1154 */     return new CollectorImpl<>(supplier, biConsumer1, binaryOperator1, function, CH_NOID);
/*      */   }
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
/*      */   public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends U> paramFunction1) {
/* 1212 */     return toMap(paramFunction, paramFunction1, throwingMerger(), java.util.HashMap::new);
/*      */   }
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
/*      */   public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends U> paramFunction1, BinaryOperator<U> paramBinaryOperator) {
/* 1271 */     return toMap(paramFunction, paramFunction1, paramBinaryOperator, java.util.HashMap::new);
/*      */   }
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
/*      */   public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends U> paramFunction1, BinaryOperator<U> paramBinaryOperator, Supplier<M> paramSupplier) {
/* 1319 */     BiConsumer<?, T> biConsumer = (paramMap, paramObject) -> paramMap.merge(paramFunction1.apply(paramObject), paramFunction2.apply(paramObject), paramBinaryOperator);
/*      */ 
/*      */     
/* 1322 */     return new CollectorImpl<>(paramSupplier, biConsumer, mapMerger(paramBinaryOperator), CH_ID);
/*      */   }
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
/*      */   public static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends U> paramFunction1) {
/* 1375 */     return toConcurrentMap(paramFunction, paramFunction1, throwingMerger(), java.util.concurrent.ConcurrentHashMap::new);
/*      */   }
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
/*      */   public static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends U> paramFunction1, BinaryOperator<U> paramBinaryOperator) {
/* 1429 */     return toConcurrentMap(paramFunction, paramFunction1, paramBinaryOperator, java.util.concurrent.ConcurrentHashMap::new);
/*      */   }
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
/*      */   public static <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends U> paramFunction1, BinaryOperator<U> paramBinaryOperator, Supplier<M> paramSupplier) {
/* 1471 */     BiConsumer<?, T> biConsumer = (paramConcurrentMap, paramObject) -> paramConcurrentMap.merge(paramFunction1.apply(paramObject), paramFunction2.apply(paramObject), paramBinaryOperator);
/*      */ 
/*      */     
/* 1474 */     return new CollectorImpl<>(paramSupplier, biConsumer, mapMerger(paramBinaryOperator), CH_CONCURRENT_ID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> paramToIntFunction) {
/* 1491 */     return new CollectorImpl<>(IntSummaryStatistics::new, (paramIntSummaryStatistics, paramObject) -> paramIntSummaryStatistics.accept(paramToIntFunction.applyAsInt(paramObject)), (paramIntSummaryStatistics1, paramIntSummaryStatistics2) -> { paramIntSummaryStatistics1.combine(paramIntSummaryStatistics2); return paramIntSummaryStatistics1; }CH_ID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> paramToLongFunction) {
/* 1511 */     return new CollectorImpl<>(LongSummaryStatistics::new, (paramLongSummaryStatistics, paramObject) -> paramLongSummaryStatistics.accept(paramToLongFunction.applyAsLong(paramObject)), (paramLongSummaryStatistics1, paramLongSummaryStatistics2) -> { paramLongSummaryStatistics1.combine(paramLongSummaryStatistics2); return paramLongSummaryStatistics1; }CH_ID);
/*      */   }
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
/*      */   public static <T> Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> paramToDoubleFunction) {
/* 1531 */     return new CollectorImpl<>(DoubleSummaryStatistics::new, (paramDoubleSummaryStatistics, paramObject) -> paramDoubleSummaryStatistics.accept(paramToDoubleFunction.applyAsDouble(paramObject)), (paramDoubleSummaryStatistics1, paramDoubleSummaryStatistics2) -> { paramDoubleSummaryStatistics1.combine(paramDoubleSummaryStatistics2); return paramDoubleSummaryStatistics1; }CH_ID);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Partition<T>
/*      */     extends AbstractMap<Boolean, T>
/*      */     implements Map<Boolean, T>
/*      */   {
/*      */     final T forTrue;
/*      */ 
/*      */     
/*      */     final T forFalse;
/*      */ 
/*      */     
/*      */     Partition(T param1T1, T param1T2) {
/* 1547 */       this.forTrue = param1T1;
/* 1548 */       this.forFalse = param1T2;
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<Boolean, T>> entrySet() {
/* 1553 */       return new AbstractSet<Map.Entry<Boolean, T>>()
/*      */         {
/*      */           public Iterator<Map.Entry<Boolean, T>> iterator() {
/* 1556 */             AbstractMap.SimpleImmutableEntry<Boolean, T> simpleImmutableEntry1 = new AbstractMap.SimpleImmutableEntry<>(Boolean.valueOf(false), Collectors.Partition.this.forFalse);
/* 1557 */             AbstractMap.SimpleImmutableEntry<Boolean, T> simpleImmutableEntry2 = new AbstractMap.SimpleImmutableEntry<>(Boolean.valueOf(true), Collectors.Partition.this.forTrue);
/* 1558 */             return Arrays.<Map.Entry<Boolean, T>>asList((Map.Entry<Boolean, T>[])new Map.Entry[] { simpleImmutableEntry1, simpleImmutableEntry2 }).iterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1563 */             return 2;
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/Collectors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */