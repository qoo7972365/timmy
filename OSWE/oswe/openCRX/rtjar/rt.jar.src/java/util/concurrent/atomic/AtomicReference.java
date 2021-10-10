/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.UnaryOperator;
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
/*     */ public class AtomicReference<V>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1848883965231344442L;
/*  52 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   private static final long valueOffset;
/*     */   private volatile V value;
/*     */   
/*     */   static {
/*     */     
/*  58 */     try { valueOffset = unsafe.objectFieldOffset(AtomicReference.class.getDeclaredField("value")); }
/*  59 */     catch (Exception exception) { throw new Error(exception); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicReference(V paramV) {
/*  70 */     this.value = paramV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicReference() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V get() {
/*  85 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(V paramV) {
/*  94 */     this.value = paramV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void lazySet(V paramV) {
/* 104 */     unsafe.putOrderedObject(this, valueOffset, paramV);
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
/*     */   public final boolean compareAndSet(V paramV1, V paramV2) {
/* 116 */     return unsafe.compareAndSwapObject(this, valueOffset, paramV1, paramV2);
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
/*     */   public final boolean weakCompareAndSet(V paramV1, V paramV2) {
/* 132 */     return unsafe.compareAndSwapObject(this, valueOffset, paramV1, paramV2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V getAndSet(V paramV) {
/* 143 */     return (V)unsafe.getAndSetObject(this, valueOffset, paramV);
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
/*     */   public final V getAndUpdate(UnaryOperator<V> paramUnaryOperator) {
/*     */     while (true) {
/* 159 */       V v1 = get();
/* 160 */       V v2 = paramUnaryOperator.apply(v1);
/* 161 */       if (compareAndSet(v1, v2)) {
/* 162 */         return v1;
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
/*     */   public final V updateAndGet(UnaryOperator<V> paramUnaryOperator) {
/*     */     while (true) {
/* 178 */       V v1 = get();
/* 179 */       V v2 = paramUnaryOperator.apply(v1);
/* 180 */       if (compareAndSet(v1, v2)) {
/* 181 */         return v2;
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
/*     */   public final V getAndAccumulate(V paramV, BinaryOperator<V> paramBinaryOperator) {
/*     */     while (true) {
/* 202 */       V v1 = get();
/* 203 */       V v2 = paramBinaryOperator.apply(v1, paramV);
/* 204 */       if (compareAndSet(v1, v2)) {
/* 205 */         return v1;
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
/*     */   public final V accumulateAndGet(V paramV, BinaryOperator<V> paramBinaryOperator) {
/*     */     while (true) {
/* 226 */       V v1 = get();
/* 227 */       V v2 = paramBinaryOperator.apply(v1, paramV);
/* 228 */       if (compareAndSet(v1, v2)) {
/* 229 */         return v2;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 237 */     return String.valueOf(get());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */