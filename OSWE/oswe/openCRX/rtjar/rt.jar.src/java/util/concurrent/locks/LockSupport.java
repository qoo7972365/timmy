/*     */ package java.util.concurrent.locks;
/*     */ 
/*     */ import java.util.concurrent.ThreadLocalRandom;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LockSupport
/*     */ {
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final long parkBlockerOffset;
/*     */   private static final long SEED;
/*     */   private static final long PROBE;
/*     */   private static final long SECONDARY;
/*     */   
/*     */   private static void setBlocker(Thread paramThread, Object paramObject) {
/* 125 */     UNSAFE.putObject(paramThread, parkBlockerOffset, paramObject);
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
/*     */   public static void unpark(Thread paramThread) {
/* 140 */     if (paramThread != null) {
/* 141 */       UNSAFE.unpark(paramThread);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void park(Object paramObject) {
/* 173 */     Thread thread = Thread.currentThread();
/* 174 */     setBlocker(thread, paramObject);
/* 175 */     UNSAFE.park(false, 0L);
/* 176 */     setBlocker(thread, null);
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
/*     */   public static void parkNanos(Object paramObject, long paramLong) {
/* 212 */     if (paramLong > 0L) {
/* 213 */       Thread thread = Thread.currentThread();
/* 214 */       setBlocker(thread, paramObject);
/* 215 */       UNSAFE.park(false, paramLong);
/* 216 */       setBlocker(thread, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void parkUntil(Object paramObject, long paramLong) {
/* 254 */     Thread thread = Thread.currentThread();
/* 255 */     setBlocker(thread, paramObject);
/* 256 */     UNSAFE.park(true, paramLong);
/* 257 */     setBlocker(thread, null);
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
/*     */   public static Object getBlocker(Thread paramThread) {
/* 273 */     if (paramThread == null)
/* 274 */       throw new NullPointerException(); 
/* 275 */     return UNSAFE.getObjectVolatile(paramThread, parkBlockerOffset);
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
/*     */   public static void park() {
/* 304 */     UNSAFE.park(false, 0L);
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
/*     */   public static void parkNanos(long paramLong) {
/* 337 */     if (paramLong > 0L) {
/* 338 */       UNSAFE.park(false, paramLong);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void parkUntil(long paramLong) {
/* 372 */     UNSAFE.park(true, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int nextSecondarySeed() {
/* 381 */     Thread thread = Thread.currentThread(); int i;
/* 382 */     if ((i = UNSAFE.getInt(thread, SECONDARY)) != 0) {
/* 383 */       i ^= i << 13;
/* 384 */       i ^= i >>> 17;
/* 385 */       i ^= i << 5;
/*     */     }
/* 387 */     else if ((i = ThreadLocalRandom.current().nextInt()) == 0) {
/* 388 */       i = 1;
/* 389 */     }  UNSAFE.putInt(thread, SECONDARY, i);
/* 390 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     
/* 401 */     try { UNSAFE = Unsafe.getUnsafe();
/* 402 */       Class<Thread> clazz = Thread.class;
/*     */       
/* 404 */       parkBlockerOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("parkBlocker"));
/*     */       
/* 406 */       SEED = UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomSeed"));
/*     */       
/* 408 */       PROBE = UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomProbe"));
/*     */       
/* 410 */       SECONDARY = UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomSecondarySeed")); }
/* 411 */     catch (Exception exception) { throw new Error(exception); }
/*     */   
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/locks/LockSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */