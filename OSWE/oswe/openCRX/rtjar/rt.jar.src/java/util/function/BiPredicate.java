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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface BiPredicate<T, U>
/*     */ {
/*     */   default BiPredicate<T, U> and(BiPredicate<? super T, ? super U> paramBiPredicate) {
/*  72 */     Objects.requireNonNull(paramBiPredicate);
/*  73 */     return (paramObject1, paramObject2) -> (test((T)paramObject1, (U)paramObject2) && paramBiPredicate.test(paramObject1, paramObject2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default BiPredicate<T, U> negate() {
/*  84 */     return (paramObject1, paramObject2) -> !test((T)paramObject1, (U)paramObject2);
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
/*     */   default BiPredicate<T, U> or(BiPredicate<? super T, ? super U> paramBiPredicate) {
/* 104 */     Objects.requireNonNull(paramBiPredicate);
/* 105 */     return (paramObject1, paramObject2) -> (test((T)paramObject1, (U)paramObject2) || paramBiPredicate.test(paramObject1, paramObject2));
/*     */   }
/*     */   
/*     */   boolean test(T paramT, U paramU);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/function/BiPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */