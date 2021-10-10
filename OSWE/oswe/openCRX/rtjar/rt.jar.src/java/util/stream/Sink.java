/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ interface Sink<T>
/*     */   extends Consumer<T>
/*     */ {
/*     */   default void begin(long paramLong) {}
/*     */   
/*     */   default void end() {}
/*     */   
/*     */   default boolean cancellationRequested() {
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void accept(int paramInt) {
/* 159 */     throw new IllegalStateException("called wrong accept method");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void accept(long paramLong) {
/* 170 */     throw new IllegalStateException("called wrong accept method");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void accept(double paramDouble) {
/* 181 */     throw new IllegalStateException("called wrong accept method");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface OfInt
/*     */     extends Sink<Integer>, IntConsumer
/*     */   {
/*     */     default void accept(Integer param1Integer) {
/* 195 */       if (Tripwire.ENABLED)
/* 196 */         Tripwire.trip(getClass(), "{0} calling Sink.OfInt.accept(Integer)"); 
/* 197 */       accept(param1Integer.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void accept(int param1Int);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface OfLong
/*     */     extends Sink<Long>, LongConsumer
/*     */   {
/*     */     default void accept(Long param1Long) {
/* 212 */       if (Tripwire.ENABLED)
/* 213 */         Tripwire.trip(getClass(), "{0} calling Sink.OfLong.accept(Long)"); 
/* 214 */       accept(param1Long.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void accept(long param1Long);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface OfDouble
/*     */     extends Sink<Double>, DoubleConsumer
/*     */   {
/*     */     default void accept(Double param1Double) {
/* 229 */       if (Tripwire.ENABLED)
/* 230 */         Tripwire.trip(getClass(), "{0} calling Sink.OfDouble.accept(Double)"); 
/* 231 */       accept(param1Double.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void accept(double param1Double);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class ChainedReference<T, E_OUT>
/*     */     implements Sink<T>
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */ 
/*     */     
/*     */     public ChainedReference(Sink<? super E_OUT> param1Sink) {
/* 248 */       this.downstream = Objects.<Sink<? super E_OUT>>requireNonNull(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 253 */       this.downstream.begin(param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 258 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancellationRequested() {
/* 263 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class ChainedInt<E_OUT>
/*     */     implements OfInt
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ChainedInt(Sink<? super E_OUT> param1Sink) {
/* 280 */       this.downstream = Objects.<Sink<? super E_OUT>>requireNonNull(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 285 */       this.downstream.begin(param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 290 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancellationRequested() {
/* 295 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class ChainedLong<E_OUT>
/*     */     implements OfLong
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ChainedLong(Sink<? super E_OUT> param1Sink) {
/* 312 */       this.downstream = Objects.<Sink<? super E_OUT>>requireNonNull(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 317 */       this.downstream.begin(param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 322 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancellationRequested() {
/* 327 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class ChainedDouble<E_OUT>
/*     */     implements OfDouble
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ChainedDouble(Sink<? super E_OUT> param1Sink) {
/* 344 */       this.downstream = Objects.<Sink<? super E_OUT>>requireNonNull(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 349 */       this.downstream.begin(param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 354 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancellationRequested() {
/* 359 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/Sink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */