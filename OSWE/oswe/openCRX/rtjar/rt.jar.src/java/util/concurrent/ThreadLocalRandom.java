/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.security.SecureRandom;
/*      */ import java.util.Random;
/*      */ import java.util.Spliterator;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.stream.DoubleStream;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.misc.VM;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ThreadLocalRandom
/*      */   extends Random
/*      */ {
/*  130 */   private static final AtomicInteger probeGenerator = new AtomicInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  136 */   private static final AtomicLong seeder = new AtomicLong(initialSeed());
/*      */   
/*      */   private static long initialSeed() {
/*  139 */     String str = VM.getSavedProperty("java.util.secureRandomSeed");
/*  140 */     if (Boolean.parseBoolean(str)) {
/*  141 */       byte[] arrayOfByte = SecureRandom.getSeed(8);
/*  142 */       long l = arrayOfByte[0] & 0xFFL;
/*  143 */       for (byte b = 1; b < 8; b++)
/*  144 */         l = l << 8L | arrayOfByte[b] & 0xFFL; 
/*  145 */       return l;
/*      */     } 
/*  147 */     return mix64(System.currentTimeMillis()) ^ 
/*  148 */       mix64(System.nanoTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long GAMMA = -7046029254386353131L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PROBE_INCREMENT = -1640531527;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long SEEDER_INCREMENT = -4942790177534073029L;
/*      */ 
/*      */   
/*      */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/*      */ 
/*      */   
/*      */   private static final float FLOAT_UNIT = 5.9604645E-8F;
/*      */ 
/*      */   
/*  171 */   private static final ThreadLocal<Double> nextLocalGaussian = new ThreadLocal<>();
/*      */   boolean initialized;
/*      */   
/*      */   private static long mix64(long paramLong) {
/*  175 */     paramLong = (paramLong ^ paramLong >>> 33L) * -49064778989728563L;
/*  176 */     paramLong = (paramLong ^ paramLong >>> 33L) * -4265267296055464877L;
/*  177 */     return paramLong ^ paramLong >>> 33L;
/*      */   }
/*      */   
/*      */   private static int mix32(long paramLong) {
/*  181 */     paramLong = (paramLong ^ paramLong >>> 33L) * -49064778989728563L;
/*  182 */     return (int)((paramLong ^ paramLong >>> 33L) * -4265267296055464877L >>> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ThreadLocalRandom() {
/*  193 */     this.initialized = true;
/*      */   }
/*      */ 
/*      */   
/*  197 */   static final ThreadLocalRandom instance = new ThreadLocalRandom();
/*      */   
/*      */   static final String BadBound = "bound must be positive";
/*      */   
/*      */   static final String BadRange = "bound must be greater than origin";
/*      */   
/*      */   static final String BadSize = "size must be non-negative";
/*      */   private static final long serialVersionUID = -5851777807851030925L;
/*      */   
/*      */   static final void localInit() {
/*  207 */     int i = probeGenerator.addAndGet(-1640531527);
/*  208 */     boolean bool = (i == 0) ? true : i;
/*  209 */     long l = mix64(seeder.getAndAdd(-4942790177534073029L));
/*  210 */     Thread thread = Thread.currentThread();
/*  211 */     UNSAFE.putLong(thread, SEED, l);
/*  212 */     UNSAFE.putInt(thread, PROBE, bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ThreadLocalRandom current() {
/*  221 */     if (UNSAFE.getInt(Thread.currentThread(), PROBE) == 0)
/*  222 */       localInit(); 
/*  223 */     return instance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeed(long paramLong) {
/*  234 */     if (this.initialized)
/*  235 */       throw new UnsupportedOperationException(); 
/*      */   }
/*      */   final long nextSeed() {
/*      */     Thread thread;
/*      */     long l;
/*  240 */     UNSAFE.putLong(thread = Thread.currentThread(), SEED, 
/*  241 */         l = UNSAFE.getLong(thread, SEED) + -7046029254386353131L);
/*  242 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int next(int paramInt) {
/*  247 */     return (int)(mix64(nextSeed()) >>> 64 - paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final long internalNextLong(long paramLong1, long paramLong2) {
/*  265 */     long l = mix64(nextSeed());
/*  266 */     if (paramLong1 < paramLong2) {
/*  267 */       long l1 = paramLong2 - paramLong1, l2 = l1 - 1L;
/*  268 */       if ((l1 & l2) == 0L) {
/*  269 */         l = (l & l2) + paramLong1;
/*  270 */       } else if (l1 > 0L) {
/*  271 */         long l3 = l >>> 1L;
/*  272 */         while (l3 + l2 - (l = l3 % l1) < 0L) {
/*  273 */           l3 = mix64(nextSeed()) >>> 1L;
/*      */         }
/*  275 */         l += paramLong1;
/*      */       } else {
/*      */         
/*  278 */         while (l < paramLong1 || l >= paramLong2)
/*  279 */           l = mix64(nextSeed()); 
/*      */       } 
/*      */     } 
/*  282 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int internalNextInt(int paramInt1, int paramInt2) {
/*  294 */     int i = mix32(nextSeed());
/*  295 */     if (paramInt1 < paramInt2) {
/*  296 */       int j = paramInt2 - paramInt1, k = j - 1;
/*  297 */       if ((j & k) == 0) {
/*  298 */         i = (i & k) + paramInt1;
/*  299 */       } else if (j > 0) {
/*  300 */         int m = i >>> 1;
/*  301 */         while (m + k - (i = m % j) < 0) {
/*  302 */           m = mix32(nextSeed()) >>> 1;
/*      */         }
/*  304 */         i += paramInt1;
/*      */       } else {
/*      */         
/*  307 */         while (i < paramInt1 || i >= paramInt2)
/*  308 */           i = mix32(nextSeed()); 
/*      */       } 
/*      */     } 
/*  311 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final double internalNextDouble(double paramDouble1, double paramDouble2) {
/*  322 */     double d = (nextLong() >>> 11L) * 1.1102230246251565E-16D;
/*  323 */     if (paramDouble1 < paramDouble2) {
/*  324 */       d = d * (paramDouble2 - paramDouble1) + paramDouble1;
/*  325 */       if (d >= paramDouble2)
/*  326 */         d = Double.longBitsToDouble(Double.doubleToLongBits(paramDouble2) - 1L); 
/*      */     } 
/*  328 */     return d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int nextInt() {
/*  337 */     return mix32(nextSeed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int nextInt(int paramInt) {
/*  350 */     if (paramInt <= 0)
/*  351 */       throw new IllegalArgumentException("bound must be positive"); 
/*  352 */     int i = mix32(nextSeed());
/*  353 */     int j = paramInt - 1;
/*  354 */     if ((paramInt & j) == 0) {
/*  355 */       i &= j;
/*      */     } else {
/*  357 */       int k = i >>> 1;
/*  358 */       while (k + j - (i = k % paramInt) < 0) {
/*  359 */         k = mix32(nextSeed()) >>> 1;
/*      */       }
/*      */     } 
/*  362 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int nextInt(int paramInt1, int paramInt2) {
/*  377 */     if (paramInt1 >= paramInt2)
/*  378 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  379 */     return internalNextInt(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long nextLong() {
/*  388 */     return mix64(nextSeed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long nextLong(long paramLong) {
/*  401 */     if (paramLong <= 0L)
/*  402 */       throw new IllegalArgumentException("bound must be positive"); 
/*  403 */     long l1 = mix64(nextSeed());
/*  404 */     long l2 = paramLong - 1L;
/*  405 */     if ((paramLong & l2) == 0L) {
/*  406 */       l1 &= l2;
/*      */     } else {
/*  408 */       long l = l1 >>> 1L;
/*  409 */       while (l + l2 - (l1 = l % paramLong) < 0L) {
/*  410 */         l = mix64(nextSeed()) >>> 1L;
/*      */       }
/*      */     } 
/*  413 */     return l1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long nextLong(long paramLong1, long paramLong2) {
/*  428 */     if (paramLong1 >= paramLong2)
/*  429 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  430 */     return internalNextLong(paramLong1, paramLong2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double nextDouble() {
/*  441 */     return (mix64(nextSeed()) >>> 11L) * 1.1102230246251565E-16D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double nextDouble(double paramDouble) {
/*  454 */     if (paramDouble <= 0.0D)
/*  455 */       throw new IllegalArgumentException("bound must be positive"); 
/*  456 */     double d = (mix64(nextSeed()) >>> 11L) * 1.1102230246251565E-16D * paramDouble;
/*  457 */     return (d < paramDouble) ? d : 
/*  458 */       Double.longBitsToDouble(Double.doubleToLongBits(paramDouble) - 1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double nextDouble(double paramDouble1, double paramDouble2) {
/*  473 */     if (paramDouble1 >= paramDouble2)
/*  474 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  475 */     return internalNextDouble(paramDouble1, paramDouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nextBoolean() {
/*  484 */     return (mix32(nextSeed()) < 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float nextFloat() {
/*  495 */     return (mix32(nextSeed()) >>> 8) * 5.9604645E-8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public double nextGaussian() {
/*  500 */     Double double_ = nextLocalGaussian.get();
/*  501 */     if (double_ != null) {
/*  502 */       nextLocalGaussian.set(null);
/*  503 */       return double_.doubleValue();
/*      */     } 
/*      */     
/*      */     while (true) {
/*  507 */       double d1 = 2.0D * nextDouble() - 1.0D;
/*  508 */       double d2 = 2.0D * nextDouble() - 1.0D;
/*  509 */       double d3 = d1 * d1 + d2 * d2;
/*  510 */       if (d3 < 1.0D && d3 != 0.0D) {
/*  511 */         double d = StrictMath.sqrt(-2.0D * StrictMath.log(d3) / d3);
/*  512 */         nextLocalGaussian.set(new Double(d2 * d));
/*  513 */         return d1 * d;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntStream ints(long paramLong) {
/*  530 */     if (paramLong < 0L)
/*  531 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  532 */     return 
/*  533 */       StreamSupport.intStream(new RandomIntsSpliterator(0L, paramLong, 2147483647, 0), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntStream ints() {
/*  549 */     return 
/*  550 */       StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, 2147483647, 0), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntStream ints(long paramLong, int paramInt1, int paramInt2) {
/*  572 */     if (paramLong < 0L)
/*  573 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  574 */     if (paramInt1 >= paramInt2)
/*  575 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  576 */     return 
/*  577 */       StreamSupport.intStream(new RandomIntsSpliterator(0L, paramLong, paramInt1, paramInt2), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntStream ints(int paramInt1, int paramInt2) {
/*  599 */     if (paramInt1 >= paramInt2)
/*  600 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  601 */     return 
/*  602 */       StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, paramInt1, paramInt2), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongStream longs(long paramLong) {
/*  618 */     if (paramLong < 0L)
/*  619 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  620 */     return 
/*  621 */       StreamSupport.longStream(new RandomLongsSpliterator(0L, paramLong, Long.MAX_VALUE, 0L), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongStream longs() {
/*  637 */     return 
/*  638 */       StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongStream longs(long paramLong1, long paramLong2, long paramLong3) {
/*  660 */     if (paramLong1 < 0L)
/*  661 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  662 */     if (paramLong2 >= paramLong3)
/*  663 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  664 */     return 
/*  665 */       StreamSupport.longStream(new RandomLongsSpliterator(0L, paramLong1, paramLong2, paramLong3), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongStream longs(long paramLong1, long paramLong2) {
/*  687 */     if (paramLong1 >= paramLong2)
/*  688 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  689 */     return 
/*  690 */       StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, paramLong1, paramLong2), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleStream doubles(long paramLong) {
/*  707 */     if (paramLong < 0L)
/*  708 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  709 */     return 
/*  710 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, paramLong, Double.MAX_VALUE, 0.0D), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleStream doubles() {
/*  727 */     return 
/*  728 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0D), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleStream doubles(long paramLong, double paramDouble1, double paramDouble2) {
/*  751 */     if (paramLong < 0L)
/*  752 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  753 */     if (paramDouble1 >= paramDouble2)
/*  754 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  755 */     return 
/*  756 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, paramLong, paramDouble1, paramDouble2), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleStream doubles(double paramDouble1, double paramDouble2) {
/*  778 */     if (paramDouble1 >= paramDouble2)
/*  779 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  780 */     return 
/*  781 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, paramDouble1, paramDouble2), false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class RandomIntsSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     long index;
/*      */ 
/*      */     
/*      */     final long fence;
/*      */ 
/*      */     
/*      */     final int origin;
/*      */     
/*      */     final int bound;
/*      */ 
/*      */     
/*      */     RandomIntsSpliterator(long param1Long1, long param1Long2, int param1Int1, int param1Int2) {
/*  801 */       this.index = param1Long1; this.fence = param1Long2;
/*  802 */       this.origin = param1Int1; this.bound = param1Int2;
/*      */     }
/*      */     
/*      */     public RandomIntsSpliterator trySplit() {
/*  806 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/*  807 */       return (l2 <= l1) ? null : new RandomIntsSpliterator(l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  812 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  816 */       return 17728;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/*  821 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/*  822 */       long l1 = this.index, l2 = this.fence;
/*  823 */       if (l1 < l2) {
/*  824 */         param1IntConsumer.accept(ThreadLocalRandom.current().internalNextInt(this.origin, this.bound));
/*  825 */         this.index = l1 + 1L;
/*  826 */         return true;
/*      */       } 
/*  828 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/*  832 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/*  833 */       long l1 = this.index, l2 = this.fence;
/*  834 */       if (l1 < l2) {
/*  835 */         this.index = l2;
/*  836 */         int i = this.origin, j = this.bound;
/*  837 */         ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
/*      */         do {
/*  839 */           param1IntConsumer.accept(threadLocalRandom.internalNextInt(i, j));
/*  840 */         } while (++l1 < l2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class RandomLongsSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     long index;
/*      */     final long fence;
/*      */     final long origin;
/*      */     final long bound;
/*      */     
/*      */     RandomLongsSpliterator(long param1Long1, long param1Long2, long param1Long3, long param1Long4) {
/*  855 */       this.index = param1Long1; this.fence = param1Long2;
/*  856 */       this.origin = param1Long3; this.bound = param1Long4;
/*      */     }
/*      */     
/*      */     public RandomLongsSpliterator trySplit() {
/*  860 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/*  861 */       return (l2 <= l1) ? null : new RandomLongsSpliterator(l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  866 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  870 */       return 17728;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/*  875 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/*  876 */       long l1 = this.index, l2 = this.fence;
/*  877 */       if (l1 < l2) {
/*  878 */         param1LongConsumer.accept(ThreadLocalRandom.current().internalNextLong(this.origin, this.bound));
/*  879 */         this.index = l1 + 1L;
/*  880 */         return true;
/*      */       } 
/*  882 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/*  886 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/*  887 */       long l1 = this.index, l2 = this.fence;
/*  888 */       if (l1 < l2) {
/*  889 */         this.index = l2;
/*  890 */         long l3 = this.origin, l4 = this.bound;
/*  891 */         ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
/*      */         do {
/*  893 */           param1LongConsumer.accept(threadLocalRandom.internalNextLong(l3, l4));
/*  894 */         } while (++l1 < l2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class RandomDoublesSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     long index;
/*      */     
/*      */     final long fence;
/*      */     final double origin;
/*      */     final double bound;
/*      */     
/*      */     RandomDoublesSpliterator(long param1Long1, long param1Long2, double param1Double1, double param1Double2) {
/*  910 */       this.index = param1Long1; this.fence = param1Long2;
/*  911 */       this.origin = param1Double1; this.bound = param1Double2;
/*      */     }
/*      */     
/*      */     public RandomDoublesSpliterator trySplit() {
/*  915 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/*  916 */       return (l2 <= l1) ? null : new RandomDoublesSpliterator(l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  921 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  925 */       return 17728;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/*  930 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/*  931 */       long l1 = this.index, l2 = this.fence;
/*  932 */       if (l1 < l2) {
/*  933 */         param1DoubleConsumer.accept(ThreadLocalRandom.current().internalNextDouble(this.origin, this.bound));
/*  934 */         this.index = l1 + 1L;
/*  935 */         return true;
/*      */       } 
/*  937 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/*  941 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/*  942 */       long l1 = this.index, l2 = this.fence;
/*  943 */       if (l1 < l2) {
/*  944 */         this.index = l2;
/*  945 */         double d1 = this.origin, d2 = this.bound;
/*  946 */         ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
/*      */         do {
/*  948 */           param1DoubleConsumer.accept(threadLocalRandom.internalNextDouble(d1, d2));
/*  949 */         } while (++l1 < l2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int getProbe() {
/*  980 */     return UNSAFE.getInt(Thread.currentThread(), PROBE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int advanceProbe(int paramInt) {
/*  988 */     paramInt ^= paramInt << 13;
/*  989 */     paramInt ^= paramInt >>> 17;
/*  990 */     paramInt ^= paramInt << 5;
/*  991 */     UNSAFE.putInt(Thread.currentThread(), PROBE, paramInt);
/*  992 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int nextSecondarySeed() {
/* 1000 */     Thread thread = Thread.currentThread(); int i;
/* 1001 */     if ((i = UNSAFE.getInt(thread, SECONDARY)) != 0) {
/* 1002 */       i ^= i << 13;
/* 1003 */       i ^= i >>> 17;
/* 1004 */       i ^= i << 5;
/*      */     } else {
/*      */       
/* 1007 */       localInit();
/* 1008 */       if ((i = (int)UNSAFE.getLong(thread, SEED)) == 0)
/* 1009 */         i = 1; 
/*      */     } 
/* 1011 */     UNSAFE.putInt(thread, SECONDARY, i);
/* 1012 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1025 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("rnd", long.class), new ObjectStreamField("initialized", boolean.class) };
/*      */ 
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */   private static final long SEED;
/*      */   
/*      */   private static final long PROBE;
/*      */   
/*      */   private static final long SECONDARY;
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1038 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 1039 */     putField.put("rnd", UNSAFE.getLong(Thread.currentThread(), SEED));
/* 1040 */     putField.put("initialized", true);
/* 1041 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object readResolve() {
/* 1049 */     return current();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1059 */       UNSAFE = Unsafe.getUnsafe();
/* 1060 */       Class<Thread> clazz = Thread.class;
/*      */       
/* 1062 */       SEED = UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomSeed"));
/*      */       
/* 1064 */       PROBE = UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomProbe"));
/*      */       
/* 1066 */       SECONDARY = UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomSecondarySeed"));
/* 1067 */     } catch (Exception exception) {
/* 1068 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ThreadLocalRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */