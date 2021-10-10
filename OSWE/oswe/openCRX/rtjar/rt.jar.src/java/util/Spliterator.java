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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Spliterator<T>
/*     */ {
/*     */   public static final int ORDERED = 16;
/*     */   public static final int DISTINCT = 1;
/*     */   public static final int SORTED = 4;
/*     */   public static final int SIZED = 64;
/*     */   public static final int NONNULL = 256;
/*     */   public static final int IMMUTABLE = 1024;
/*     */   public static final int CONCURRENT = 4096;
/*     */   public static final int SUBSIZED = 16384;
/*     */   
/*     */   boolean tryAdvance(Consumer<? super T> paramConsumer);
/*     */   
/*     */   default void forEachRemaining(Consumer<? super T> paramConsumer) {
/*     */     do {
/*     */     
/* 326 */     } while (tryAdvance(paramConsumer));
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
/*     */   Spliterator<T> trySplit();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long estimateSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long getExactSizeIfKnown() {
/* 408 */     return ((characteristics() & 0x40) == 0) ? -1L : estimateSize();
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
/*     */   int characteristics();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean hasCharacteristics(int paramInt) {
/* 447 */     return ((characteristics() & paramInt) == paramInt);
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
/*     */   default Comparator<? super T> getComparator() {
/* 465 */     throw new IllegalStateException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface OfPrimitive<T, T_CONS, T_SPLITR extends OfPrimitive<T, T_CONS, T_SPLITR>>
/*     */     extends Spliterator<T>
/*     */   {
/*     */     default void forEachRemaining(T_CONS param1T_CONS) {
/*     */       do {
/*     */       
/* 636 */       } while (tryAdvance(param1T_CONS));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean tryAdvance(T_CONS param1T_CONS);
/*     */ 
/*     */     
/*     */     T_SPLITR trySplit();
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface OfInt
/*     */     extends OfPrimitive<Integer, IntConsumer, OfInt>
/*     */   {
/*     */     default void forEachRemaining(IntConsumer param1IntConsumer) {
/*     */       do {
/*     */       
/* 654 */       } while (tryAdvance(param1IntConsumer));
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
/*     */     default boolean tryAdvance(Consumer<? super Integer> param1Consumer) {
/* 669 */       if (param1Consumer instanceof IntConsumer) {
/* 670 */         return tryAdvance((IntConsumer)param1Consumer);
/*     */       }
/*     */       
/* 673 */       if (Tripwire.ENABLED) {
/* 674 */         Tripwire.trip(getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
/*     */       }
/* 676 */       return tryAdvance(param1Consumer::accept);
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
/*     */     default void forEachRemaining(Consumer<? super Integer> param1Consumer) {
/* 692 */       if (param1Consumer instanceof IntConsumer) {
/* 693 */         forEachRemaining((IntConsumer)param1Consumer);
/*     */       } else {
/*     */         
/* 696 */         if (Tripwire.ENABLED) {
/* 697 */           Tripwire.trip(getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
/*     */         }
/* 699 */         forEachRemaining(param1Consumer::accept);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     OfInt trySplit();
/*     */ 
/*     */     
/*     */     boolean tryAdvance(IntConsumer param1IntConsumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface OfLong
/*     */     extends OfPrimitive<Long, LongConsumer, OfLong>
/*     */   {
/*     */     default void forEachRemaining(LongConsumer param1LongConsumer) {
/*     */       do {
/*     */       
/* 718 */       } while (tryAdvance(param1LongConsumer));
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
/*     */     default boolean tryAdvance(Consumer<? super Long> param1Consumer) {
/* 733 */       if (param1Consumer instanceof LongConsumer) {
/* 734 */         return tryAdvance((LongConsumer)param1Consumer);
/*     */       }
/*     */       
/* 737 */       if (Tripwire.ENABLED) {
/* 738 */         Tripwire.trip(getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
/*     */       }
/* 740 */       return tryAdvance(param1Consumer::accept);
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
/*     */     default void forEachRemaining(Consumer<? super Long> param1Consumer) {
/* 756 */       if (param1Consumer instanceof LongConsumer) {
/* 757 */         forEachRemaining((LongConsumer)param1Consumer);
/*     */       } else {
/*     */         
/* 760 */         if (Tripwire.ENABLED) {
/* 761 */           Tripwire.trip(getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
/*     */         }
/* 763 */         forEachRemaining(param1Consumer::accept);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     OfLong trySplit();
/*     */ 
/*     */     
/*     */     boolean tryAdvance(LongConsumer param1LongConsumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface OfDouble
/*     */     extends OfPrimitive<Double, DoubleConsumer, OfDouble>
/*     */   {
/*     */     default void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/*     */       do {
/*     */       
/* 782 */       } while (tryAdvance(param1DoubleConsumer));
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
/*     */     default boolean tryAdvance(Consumer<? super Double> param1Consumer) {
/* 797 */       if (param1Consumer instanceof DoubleConsumer) {
/* 798 */         return tryAdvance((DoubleConsumer)param1Consumer);
/*     */       }
/*     */       
/* 801 */       if (Tripwire.ENABLED) {
/* 802 */         Tripwire.trip(getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
/*     */       }
/* 804 */       return tryAdvance(param1Consumer::accept);
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
/*     */     default void forEachRemaining(Consumer<? super Double> param1Consumer) {
/* 821 */       if (param1Consumer instanceof DoubleConsumer) {
/* 822 */         forEachRemaining((DoubleConsumer)param1Consumer);
/*     */       } else {
/*     */         
/* 825 */         if (Tripwire.ENABLED) {
/* 826 */           Tripwire.trip(getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
/*     */         }
/* 828 */         forEachRemaining(param1Consumer::accept);
/*     */       } 
/*     */     }
/*     */     
/*     */     OfDouble trySplit();
/*     */     
/*     */     boolean tryAdvance(DoubleConsumer param1DoubleConsumer);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Spliterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */