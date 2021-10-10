/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntUnaryOperator;
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
/*     */ public class AtomicInteger
/*     */   extends Number
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6214790243416807050L;
/*  58 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   private static final long valueOffset;
/*     */   private volatile int value;
/*     */   
/*     */   static {
/*     */     
/*  64 */     try { valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")); }
/*  65 */     catch (Exception exception) { throw new Error(exception); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicInteger(int paramInt) {
/*  76 */     this.value = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicInteger() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int get() {
/*  91 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int paramInt) {
/* 100 */     this.value = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void lazySet(int paramInt) {
/* 110 */     unsafe.putOrderedInt(this, valueOffset, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAndSet(int paramInt) {
/* 120 */     return unsafe.getAndSetInt(this, valueOffset, paramInt);
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
/*     */   public final boolean compareAndSet(int paramInt1, int paramInt2) {
/* 133 */     return unsafe.compareAndSwapInt(this, valueOffset, paramInt1, paramInt2);
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
/*     */   public final boolean weakCompareAndSet(int paramInt1, int paramInt2) {
/* 149 */     return unsafe.compareAndSwapInt(this, valueOffset, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAndIncrement() {
/* 158 */     return unsafe.getAndAddInt(this, valueOffset, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAndDecrement() {
/* 167 */     return unsafe.getAndAddInt(this, valueOffset, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAndAdd(int paramInt) {
/* 177 */     return unsafe.getAndAddInt(this, valueOffset, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int incrementAndGet() {
/* 186 */     return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int decrementAndGet() {
/* 195 */     return unsafe.getAndAddInt(this, valueOffset, -1) - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int addAndGet(int paramInt) {
/* 205 */     return unsafe.getAndAddInt(this, valueOffset, paramInt) + paramInt;
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
/*     */   public final int getAndUpdate(IntUnaryOperator paramIntUnaryOperator) {
/*     */     while (true) {
/* 221 */       int i = get();
/* 222 */       int j = paramIntUnaryOperator.applyAsInt(i);
/* 223 */       if (compareAndSet(i, j)) {
/* 224 */         return i;
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
/*     */   public final int updateAndGet(IntUnaryOperator paramIntUnaryOperator) {
/*     */     while (true) {
/* 240 */       int i = get();
/* 241 */       int j = paramIntUnaryOperator.applyAsInt(i);
/* 242 */       if (compareAndSet(i, j)) {
/* 243 */         return j;
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
/*     */   public final int getAndAccumulate(int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/*     */     while (true) {
/* 264 */       int i = get();
/* 265 */       int j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 266 */       if (compareAndSet(i, j)) {
/* 267 */         return i;
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
/*     */   public final int accumulateAndGet(int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/*     */     while (true) {
/* 288 */       int i = get();
/* 289 */       int j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 290 */       if (compareAndSet(i, j)) {
/* 291 */         return j;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 299 */     return Integer.toString(get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 306 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 315 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 324 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 333 */     return get();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */