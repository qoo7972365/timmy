/*    */ package java.util.function;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface BinaryOperator<T>
/*    */   extends BiFunction<T, T, T>
/*    */ {
/*    */   static <T> BinaryOperator<T> minBy(Comparator<? super T> paramComparator) {
/* 58 */     Objects.requireNonNull(paramComparator);
/* 59 */     return (paramObject1, paramObject2) -> (paramComparator.compare(paramObject1, paramObject2) <= 0) ? paramObject1 : paramObject2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <T> BinaryOperator<T> maxBy(Comparator<? super T> paramComparator) {
/* 73 */     Objects.requireNonNull(paramComparator);
/* 74 */     return (paramObject1, paramObject2) -> (paramComparator.compare(paramObject1, paramObject2) >= 0) ? paramObject1 : paramObject2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/function/BinaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */