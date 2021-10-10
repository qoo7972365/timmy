/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongUnaryOperator;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AtomicLong
/*     */   extends Number
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1927816293512124184L;
/*  58 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long valueOffset;
/*     */ 
/*     */ 
/*     */   
/*  67 */   static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     
/*  78 */     try { valueOffset = unsafe.objectFieldOffset(AtomicLong.class.getDeclaredField("value")); }
/*  79 */     catch (Exception exception) { throw new Error(exception); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicLong(long paramLong) {
/*  90 */     this.value = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicLong() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long get() {
/* 105 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(long paramLong) {
/* 114 */     this.value = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void lazySet(long paramLong) {
/* 124 */     unsafe.putOrderedLong(this, valueOffset, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getAndSet(long paramLong) {
/* 134 */     return unsafe.getAndSetLong(this, valueOffset, paramLong);
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
/*     */   public final boolean compareAndSet(long paramLong1, long paramLong2) {
/* 147 */     return unsafe.compareAndSwapLong(this, valueOffset, paramLong1, paramLong2);
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
/*     */   public final boolean weakCompareAndSet(long paramLong1, long paramLong2) {
/* 163 */     return unsafe.compareAndSwapLong(this, valueOffset, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getAndIncrement() {
/* 172 */     return unsafe.getAndAddLong(this, valueOffset, 1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getAndDecrement() {
/* 181 */     return unsafe.getAndAddLong(this, valueOffset, -1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getAndAdd(long paramLong) {
/* 191 */     return unsafe.getAndAddLong(this, valueOffset, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long incrementAndGet() {
/* 200 */     return unsafe.getAndAddLong(this, valueOffset, 1L) + 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long decrementAndGet() {
/* 209 */     return unsafe.getAndAddLong(this, valueOffset, -1L) - 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long addAndGet(long paramLong) {
/* 219 */     return unsafe.getAndAddLong(this, valueOffset, paramLong) + paramLong;
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
/*     */   public final long getAndUpdate(LongUnaryOperator paramLongUnaryOperator) {
/*     */     while (true) {
/* 235 */       long l1 = get();
/* 236 */       long l2 = paramLongUnaryOperator.applyAsLong(l1);
/* 237 */       if (compareAndSet(l1, l2)) {
/* 238 */         return l1;
/*     */       }
/*     */     } 
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
/*     */   public final long updateAndGet(LongUnaryOperator paramLongUnaryOperator) {
/*     */     while (true) {
/* 254 */       long l1 = get();
/* 255 */       long l2 = paramLongUnaryOperator.applyAsLong(l1);
/* 256 */       if (compareAndSet(l1, l2)) {
/* 257 */         return l2;
/*     */       }
/*     */     } 
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
/*     */   public final long getAndAccumulate(long paramLong, LongBinaryOperator paramLongBinaryOperator) {
/*     */     while (true) {
/* 278 */       long l1 = get();
/* 279 */       long l2 = paramLongBinaryOperator.applyAsLong(l1, paramLong);
/* 280 */       if (compareAndSet(l1, l2)) {
/* 281 */         return l1;
/*     */       }
/*     */     } 
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
/*     */   public final long accumulateAndGet(long paramLong, LongBinaryOperator paramLongBinaryOperator) {
/*     */     while (true) {
/* 302 */       long l1 = get();
/* 303 */       long l2 = paramLongBinaryOperator.applyAsLong(l1, paramLong);
/* 304 */       if (compareAndSet(l1, l2)) {
/* 305 */         return l2;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 313 */     return Long.toString(get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 322 */     return (int)get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 329 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 338 */     return (float)get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 347 */     return get();
/*     */   }
/*     */   
/*     */   private static native boolean VMSupportsCS8();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicLong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */