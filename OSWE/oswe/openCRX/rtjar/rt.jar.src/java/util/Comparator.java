/*     */ package java.util;
/*     */ 
/*     */ import java.lang.invoke.SerializedLambda;
/*     */ import java.util.function.Function;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface Comparator<T>
/*     */ {
/*     */   default Comparator<T> reversed() {
/* 185 */     return Collections.reverseOrder(this);
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
/*     */   default Comparator<T> thenComparing(Comparator<? super T> paramComparator) {
/* 214 */     Objects.requireNonNull(paramComparator);
/* 215 */     return (paramObject1, paramObject2) -> {
/*     */         int i = compare((T)paramObject1, (T)paramObject2);
/*     */         return (i != 0) ? i : paramComparator.compare(paramObject1, paramObject2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <U> Comparator<T> thenComparing(Function<? super T, ? extends U> paramFunction, Comparator<? super U> paramComparator) {
/* 242 */     return thenComparing(comparing(paramFunction, paramComparator));
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
/*     */   default <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> paramFunction) {
/* 265 */     return thenComparing(comparing(paramFunction));
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
/*     */   default Comparator<T> thenComparingInt(ToIntFunction<? super T> paramToIntFunction) {
/* 284 */     return thenComparing(comparingInt(paramToIntFunction));
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
/*     */   default Comparator<T> thenComparingLong(ToLongFunction<? super T> paramToLongFunction) {
/* 303 */     return thenComparing(comparingLong(paramToLongFunction));
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
/*     */   default Comparator<T> thenComparingDouble(ToDoubleFunction<? super T> paramToDoubleFunction) {
/* 322 */     return thenComparing(comparingDouble(paramToDoubleFunction));
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
/*     */   static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
/* 339 */     return Collections.reverseOrder();
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
/*     */   static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
/* 357 */     return Comparators.NaturalOrderComparator.INSTANCE;
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
/*     */   static <T> Comparator<T> nullsFirst(Comparator<? super T> paramComparator) {
/* 378 */     return new Comparators.NullComparator<>(true, paramComparator);
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
/*     */   static <T> Comparator<T> nullsLast(Comparator<? super T> paramComparator) {
/* 399 */     return new Comparators.NullComparator<>(false, paramComparator);
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
/*     */   static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> paramFunction, Comparator<? super U> paramComparator) {
/* 433 */     Objects.requireNonNull(paramFunction);
/* 434 */     Objects.requireNonNull(paramComparator);
/* 435 */     return (paramObject1, paramObject2) -> paramComparator.compare(paramFunction.apply(paramObject1), paramFunction.apply(paramObject2));
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
/*     */   static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> paramFunction) {
/* 467 */     Objects.requireNonNull(paramFunction);
/* 468 */     return (paramObject1, paramObject2) -> ((Comparable)paramFunction.apply(paramObject1)).compareTo(paramFunction.apply(paramObject2));
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
/*     */   static <T> Comparator<T> comparingInt(ToIntFunction<? super T> paramToIntFunction) {
/* 488 */     Objects.requireNonNull(paramToIntFunction);
/* 489 */     return (paramObject1, paramObject2) -> Integer.compare(paramToIntFunction.applyAsInt(paramObject1), paramToIntFunction.applyAsInt(paramObject2));
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
/*     */   static <T> Comparator<T> comparingLong(ToLongFunction<? super T> paramToLongFunction) {
/* 509 */     Objects.requireNonNull(paramToLongFunction);
/* 510 */     return (paramObject1, paramObject2) -> Long.compare(paramToLongFunction.applyAsLong(paramObject1), paramToLongFunction.applyAsLong(paramObject2));
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
/*     */   static <T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> paramToDoubleFunction) {
/* 530 */     Objects.requireNonNull(paramToDoubleFunction);
/* 531 */     return (paramObject1, paramObject2) -> Double.compare(paramToDoubleFunction.applyAsDouble(paramObject1), paramToDoubleFunction.applyAsDouble(paramObject2));
/*     */   }
/*     */   
/*     */   int compare(T paramT1, T paramT2);
/*     */   
/*     */   boolean equals(Object paramObject);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Comparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */