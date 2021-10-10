/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class AtomicBoolean
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4654671469794556979L;
/*  53 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   private static final long valueOffset;
/*     */   private volatile int value;
/*     */   
/*     */   static {
/*     */     
/*  59 */     try { valueOffset = unsafe.objectFieldOffset(AtomicBoolean.class.getDeclaredField("value")); }
/*  60 */     catch (Exception exception) { throw new Error(exception); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicBoolean(boolean paramBoolean) {
/*  71 */     this.value = paramBoolean ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicBoolean() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean get() {
/*  86 */     return (this.value != 0);
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
/*     */   public final boolean compareAndSet(boolean paramBoolean1, boolean paramBoolean2) {
/*  99 */     boolean bool1 = paramBoolean1 ? true : false;
/* 100 */     boolean bool2 = paramBoolean2 ? true : false;
/* 101 */     return unsafe.compareAndSwapInt(this, valueOffset, bool1, bool2);
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
/*     */   public boolean weakCompareAndSet(boolean paramBoolean1, boolean paramBoolean2) {
/* 117 */     boolean bool1 = paramBoolean1 ? true : false;
/* 118 */     boolean bool2 = paramBoolean2 ? true : false;
/* 119 */     return unsafe.compareAndSwapInt(this, valueOffset, bool1, bool2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(boolean paramBoolean) {
/* 128 */     this.value = paramBoolean ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void lazySet(boolean paramBoolean) {
/* 138 */     boolean bool = paramBoolean ? true : false;
/* 139 */     unsafe.putOrderedInt(this, valueOffset, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getAndSet(boolean paramBoolean) {
/*     */     while (true) {
/* 151 */       boolean bool = get();
/* 152 */       if (compareAndSet(bool, paramBoolean)) {
/* 153 */         return bool;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     return Boolean.toString(get());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicBoolean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */