/*     */ package java.util.concurrent.atomic;
/*     */ 
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
/*     */ public class AtomicStampedReference<V>
/*     */ {
/*     */   private volatile Pair<V> pair;
/*     */   
/*     */   private static class Pair<T>
/*     */   {
/*     */     final T reference;
/*     */     final int stamp;
/*     */     
/*     */     private Pair(T param1T, int param1Int) {
/*  56 */       this.reference = param1T;
/*  57 */       this.stamp = param1Int;
/*     */     }
/*     */     static <T> Pair<T> of(T param1T, int param1Int) {
/*  60 */       return new Pair<>(param1T, param1Int);
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
/*     */   public AtomicStampedReference(V paramV, int paramInt) {
/*  74 */     this.pair = Pair.of(paramV, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getReference() {
/*  83 */     return (V)this.pair.reference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStamp() {
/*  92 */     return this.pair.stamp;
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
/*     */   public V get(int[] paramArrayOfint) {
/* 104 */     Pair<V> pair = this.pair;
/* 105 */     paramArrayOfint[0] = pair.stamp;
/* 106 */     return (V)pair.reference;
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
/*     */   public boolean weakCompareAndSet(V paramV1, V paramV2, int paramInt1, int paramInt2) {
/* 129 */     return compareAndSet(paramV1, paramV2, paramInt1, paramInt2);
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
/*     */   public boolean compareAndSet(V paramV1, V paramV2, int paramInt1, int paramInt2) {
/* 149 */     Pair<V> pair = this.pair;
/* 150 */     return (paramV1 == pair.reference && paramInt1 == pair.stamp && ((paramV2 == pair.reference && paramInt2 == pair.stamp) || 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       casPair(pair, Pair.of(paramV2, paramInt2))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(V paramV, int paramInt) {
/* 165 */     Pair<V> pair = this.pair;
/* 166 */     if (paramV != pair.reference || paramInt != pair.stamp) {
/* 167 */       this.pair = Pair.of(paramV, paramInt);
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
/*     */   public boolean attemptStamp(V paramV, int paramInt) {
/* 184 */     Pair<V> pair = this.pair;
/* 185 */     return (paramV == pair.reference && (paramInt == pair.stamp || 
/*     */ 
/*     */       
/* 188 */       casPair(pair, Pair.of(paramV, paramInt))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 193 */   private static final Unsafe UNSAFE = Unsafe.getUnsafe();
/*     */   
/* 195 */   private static final long pairOffset = objectFieldOffset(UNSAFE, "pair", AtomicStampedReference.class);
/*     */   
/*     */   private boolean casPair(Pair<V> paramPair1, Pair<V> paramPair2) {
/* 198 */     return UNSAFE.compareAndSwapObject(this, pairOffset, paramPair1, paramPair2);
/*     */   }
/*     */ 
/*     */   
/*     */   static long objectFieldOffset(Unsafe paramUnsafe, String paramString, Class<?> paramClass) {
/*     */     try {
/* 204 */       return paramUnsafe.objectFieldOffset(paramClass.getDeclaredField(paramString));
/* 205 */     } catch (NoSuchFieldException noSuchFieldException) {
/*     */       
/* 207 */       NoSuchFieldError noSuchFieldError = new NoSuchFieldError(paramString);
/* 208 */       noSuchFieldError.initCause(noSuchFieldException);
/* 209 */       throw noSuchFieldError;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicStampedReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */