/*    */ package java.util.function;
/*    */ 
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
/*    */ public interface BiConsumer<T, U>
/*    */ {
/*    */   void accept(T paramT, U paramU);
/*    */   
/*    */   default BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> paramBiConsumer) {
/* 68 */     Objects.requireNonNull(paramBiConsumer);
/*    */     
/* 70 */     return (paramObject1, paramObject2) -> {
/*    */         accept((T)paramObject1, (U)paramObject2);
/*    */         paramBiConsumer.accept(paramObject1, paramObject2);
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/function/BiConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */