/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface PrimitiveIterator<T, T_CONS>
/*     */   extends Iterator<T>
/*     */ {
/*     */   void forEachRemaining(T_CONS paramT_CONS);
/*     */   
/*     */   public static interface OfInt
/*     */     extends PrimitiveIterator<Integer, IntConsumer>
/*     */   {
/*     */     default void forEachRemaining(IntConsumer param1IntConsumer) {
/* 113 */       Objects.requireNonNull(param1IntConsumer);
/* 114 */       while (hasNext()) {
/* 115 */         param1IntConsumer.accept(nextInt());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default Integer next() {
/* 126 */       if (Tripwire.ENABLED)
/* 127 */         Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfInt.nextInt()"); 
/* 128 */       return Integer.valueOf(nextInt());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default void forEachRemaining(Consumer<? super Integer> param1Consumer) {
/* 142 */       if (param1Consumer instanceof IntConsumer) {
/* 143 */         forEachRemaining((IntConsumer)param1Consumer);
/*     */       }
/*     */       else {
/*     */         
/* 147 */         Objects.requireNonNull(param1Consumer);
/* 148 */         if (Tripwire.ENABLED)
/* 149 */           Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)"); 
/* 150 */         forEachRemaining(param1Consumer::accept);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int nextInt();
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
/*     */   public static interface OfLong
/*     */     extends PrimitiveIterator<Long, LongConsumer>
/*     */   {
/*     */     default void forEachRemaining(LongConsumer param1LongConsumer) {
/* 187 */       Objects.requireNonNull(param1LongConsumer);
/* 188 */       while (hasNext()) {
/* 189 */         param1LongConsumer.accept(nextLong());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default Long next() {
/* 200 */       if (Tripwire.ENABLED)
/* 201 */         Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfLong.nextLong()"); 
/* 202 */       return Long.valueOf(nextLong());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default void forEachRemaining(Consumer<? super Long> param1Consumer) {
/* 216 */       if (param1Consumer instanceof LongConsumer) {
/* 217 */         forEachRemaining((LongConsumer)param1Consumer);
/*     */       }
/*     */       else {
/*     */         
/* 221 */         Objects.requireNonNull(param1Consumer);
/* 222 */         if (Tripwire.ENABLED)
/* 223 */           Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)"); 
/* 224 */         forEachRemaining(param1Consumer::accept);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     long nextLong();
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
/*     */   public static interface OfDouble
/*     */     extends PrimitiveIterator<Double, DoubleConsumer>
/*     */   {
/*     */     default void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/* 260 */       Objects.requireNonNull(param1DoubleConsumer);
/* 261 */       while (hasNext()) {
/* 262 */         param1DoubleConsumer.accept(nextDouble());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default Double next() {
/* 273 */       if (Tripwire.ENABLED)
/* 274 */         Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfDouble.nextLong()"); 
/* 275 */       return Double.valueOf(nextDouble());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default void forEachRemaining(Consumer<? super Double> param1Consumer) {
/* 290 */       if (param1Consumer instanceof DoubleConsumer) {
/* 291 */         forEachRemaining((DoubleConsumer)param1Consumer);
/*     */       }
/*     */       else {
/*     */         
/* 295 */         Objects.requireNonNull(param1Consumer);
/* 296 */         if (Tripwire.ENABLED)
/* 297 */           Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)"); 
/* 298 */         forEachRemaining(param1Consumer::accept);
/*     */       } 
/*     */     }
/*     */     
/*     */     double nextDouble();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/PrimitiveIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */