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
/*     */ public class AtomicMarkableReference<V>
/*     */ {
/*     */   private volatile Pair<V> pair;
/*     */   
/*     */   private static class Pair<T>
/*     */   {
/*     */     final T reference;
/*     */     final boolean mark;
/*     */     
/*     */     private Pair(T param1T, boolean param1Boolean) {
/*  56 */       this.reference = param1T;
/*  57 */       this.mark = param1Boolean;
/*     */     }
/*     */     static <T> Pair<T> of(T param1T, boolean param1Boolean) {
/*  60 */       return new Pair<>(param1T, param1Boolean);
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
/*     */   public AtomicMarkableReference(V paramV, boolean paramBoolean) {
/*  74 */     this.pair = Pair.of(paramV, paramBoolean);
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
/*     */   public boolean isMarked() {
/*  92 */     return this.pair.mark;
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
/*     */   public V get(boolean[] paramArrayOfboolean) {
/* 104 */     Pair<V> pair = this.pair;
/* 105 */     paramArrayOfboolean[0] = pair.mark;
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
/*     */   public boolean weakCompareAndSet(V paramV1, V paramV2, boolean paramBoolean1, boolean paramBoolean2) {
/* 129 */     return compareAndSet(paramV1, paramV2, paramBoolean1, paramBoolean2);
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
/*     */   public boolean compareAndSet(V paramV1, V paramV2, boolean paramBoolean1, boolean paramBoolean2) {
/* 149 */     Pair<V> pair = this.pair;
/* 150 */     return (paramV1 == pair.reference && paramBoolean1 == pair.mark && ((paramV2 == pair.reference && paramBoolean2 == pair.mark) || 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       casPair(pair, Pair.of(paramV2, paramBoolean2))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(V paramV, boolean paramBoolean) {
/* 165 */     Pair<V> pair = this.pair;
/* 166 */     if (paramV != pair.reference || paramBoolean != pair.mark) {
/* 167 */       this.pair = Pair.of(paramV, paramBoolean);
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
/*     */   public boolean attemptMark(V paramV, boolean paramBoolean) {
/* 184 */     Pair<V> pair = this.pair;
/* 185 */     return (paramV == pair.reference && (paramBoolean == pair.mark || 
/*     */ 
/*     */       
/* 188 */       casPair(pair, Pair.of(paramV, paramBoolean))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 193 */   private static final Unsafe UNSAFE = Unsafe.getUnsafe();
/*     */   
/* 195 */   private static final long pairOffset = objectFieldOffset(UNSAFE, "pair", AtomicMarkableReference.class);
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


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicMarkableReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */