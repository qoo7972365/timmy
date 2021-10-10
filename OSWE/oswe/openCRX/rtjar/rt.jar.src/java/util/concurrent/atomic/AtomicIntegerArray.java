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
/*     */ public class AtomicIntegerArray
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2862133569453604235L;
/*  52 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*  53 */   private static final int base = unsafe.arrayBaseOffset(int[].class);
/*     */   private static final int shift;
/*     */   private final int[] array;
/*     */   
/*     */   static {
/*  58 */     int i = unsafe.arrayIndexScale(int[].class);
/*  59 */     if ((i & i - 1) != 0)
/*  60 */       throw new Error("data type scale not a power of two"); 
/*  61 */     shift = 31 - Integer.numberOfLeadingZeros(i);
/*     */   }
/*     */   
/*     */   private long checkedByteOffset(int paramInt) {
/*  65 */     if (paramInt < 0 || paramInt >= this.array.length) {
/*  66 */       throw new IndexOutOfBoundsException("index " + paramInt);
/*     */     }
/*  68 */     return byteOffset(paramInt);
/*     */   }
/*     */   
/*     */   private static long byteOffset(int paramInt) {
/*  72 */     return (paramInt << shift) + base;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicIntegerArray(int paramInt) {
/*  82 */     this.array = new int[paramInt];
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
/*     */   public AtomicIntegerArray(int[] paramArrayOfint) {
/*  94 */     this.array = (int[])paramArrayOfint.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int length() {
/* 103 */     return this.array.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int get(int paramInt) {
/* 113 */     return getRaw(checkedByteOffset(paramInt));
/*     */   }
/*     */   
/*     */   private int getRaw(long paramLong) {
/* 117 */     return unsafe.getIntVolatile(this.array, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int paramInt1, int paramInt2) {
/* 127 */     unsafe.putIntVolatile(this.array, checkedByteOffset(paramInt1), paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void lazySet(int paramInt1, int paramInt2) {
/* 138 */     unsafe.putOrderedInt(this.array, checkedByteOffset(paramInt1), paramInt2);
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
/*     */   public final int getAndSet(int paramInt1, int paramInt2) {
/* 150 */     return unsafe.getAndSetInt(this.array, checkedByteOffset(paramInt1), paramInt2);
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
/*     */   public final boolean compareAndSet(int paramInt1, int paramInt2, int paramInt3) {
/* 164 */     return compareAndSetRaw(checkedByteOffset(paramInt1), paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   private boolean compareAndSetRaw(long paramLong, int paramInt1, int paramInt2) {
/* 168 */     return unsafe.compareAndSwapInt(this.array, paramLong, paramInt1, paramInt2);
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
/*     */   public final boolean weakCompareAndSet(int paramInt1, int paramInt2, int paramInt3) {
/* 185 */     return compareAndSet(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAndIncrement(int paramInt) {
/* 195 */     return getAndAdd(paramInt, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAndDecrement(int paramInt) {
/* 205 */     return getAndAdd(paramInt, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAndAdd(int paramInt1, int paramInt2) {
/* 216 */     return unsafe.getAndAddInt(this.array, checkedByteOffset(paramInt1), paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int incrementAndGet(int paramInt) {
/* 226 */     return getAndAdd(paramInt, 1) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int decrementAndGet(int paramInt) {
/* 236 */     return getAndAdd(paramInt, -1) - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int addAndGet(int paramInt1, int paramInt2) {
/* 247 */     return getAndAdd(paramInt1, paramInt2) + paramInt2;
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
/*     */   public final int getAndUpdate(int paramInt, IntUnaryOperator paramIntUnaryOperator) {
/* 263 */     long l = checkedByteOffset(paramInt);
/*     */     
/*     */     while (true) {
/* 266 */       int i = getRaw(l);
/* 267 */       int j = paramIntUnaryOperator.applyAsInt(i);
/* 268 */       if (compareAndSetRaw(l, i, j)) {
/* 269 */         return i;
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
/*     */   public final int updateAndGet(int paramInt, IntUnaryOperator paramIntUnaryOperator) {
/* 284 */     long l = checkedByteOffset(paramInt);
/*     */     
/*     */     while (true) {
/* 287 */       int i = getRaw(l);
/* 288 */       int j = paramIntUnaryOperator.applyAsInt(i);
/* 289 */       if (compareAndSetRaw(l, i, j)) {
/* 290 */         return j;
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
/*     */   public final int getAndAccumulate(int paramInt1, int paramInt2, IntBinaryOperator paramIntBinaryOperator) {
/* 310 */     long l = checkedByteOffset(paramInt1);
/*     */     
/*     */     while (true) {
/* 313 */       int i = getRaw(l);
/* 314 */       int j = paramIntBinaryOperator.applyAsInt(i, paramInt2);
/* 315 */       if (compareAndSetRaw(l, i, j)) {
/* 316 */         return i;
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
/*     */   public final int accumulateAndGet(int paramInt1, int paramInt2, IntBinaryOperator paramIntBinaryOperator) {
/* 336 */     long l = checkedByteOffset(paramInt1);
/*     */     
/*     */     while (true) {
/* 339 */       int i = getRaw(l);
/* 340 */       int j = paramIntBinaryOperator.applyAsInt(i, paramInt2);
/* 341 */       if (compareAndSetRaw(l, i, j)) {
/* 342 */         return j;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 350 */     int i = this.array.length - 1;
/* 351 */     if (i == -1) {
/* 352 */       return "[]";
/*     */     }
/* 354 */     StringBuilder stringBuilder = new StringBuilder();
/* 355 */     stringBuilder.append('[');
/* 356 */     for (int j = 0;; j++) {
/* 357 */       stringBuilder.append(getRaw(byteOffset(j)));
/* 358 */       if (j == i)
/* 359 */         return stringBuilder.append(']').toString(); 
/* 360 */       stringBuilder.append(',').append(' ');
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicIntegerArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */