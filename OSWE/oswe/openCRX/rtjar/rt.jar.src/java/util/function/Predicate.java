/*     */ package java.util.function;
/*     */ 
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Predicate<T>
/*     */ {
/*     */   default Predicate<T> and(Predicate<? super T> paramPredicate) {
/*  68 */     Objects.requireNonNull(paramPredicate);
/*  69 */     return paramObject -> (test((T)paramObject) && paramPredicate.test(paramObject));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Predicate<T> negate() {
/*  80 */     return paramObject -> !test((T)paramObject);
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
/*     */   default Predicate<T> or(Predicate<? super T> paramPredicate) {
/* 100 */     Objects.requireNonNull(paramPredicate);
/* 101 */     return paramObject -> (test((T)paramObject) || paramPredicate.test(paramObject));
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
/*     */   static <T> Predicate<T> isEqual(Object paramObject) {
/* 115 */     return (null == paramObject) ? Objects::isNull : (paramObject2 -> paramObject1.equals(paramObject2));
/*     */   }
/*     */   
/*     */   boolean test(T paramT);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/function/Predicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */