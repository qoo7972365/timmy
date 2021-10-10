/*     */ package java.util;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.stream.DoubleStream;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.LongStream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SplittableRandom
/*     */ {
/*     */   private static final long GOLDEN_GAMMA = -7046029254386353131L;
/*     */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/*     */   private long seed;
/*     */   private final long gamma;
/*     */   
/*     */   private SplittableRandom(long paramLong1, long paramLong2) {
/* 183 */     this.seed = paramLong1;
/* 184 */     this.gamma = paramLong2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long mix64(long paramLong) {
/* 191 */     paramLong = (paramLong ^ paramLong >>> 30L) * -4658895280553007687L;
/* 192 */     paramLong = (paramLong ^ paramLong >>> 27L) * -7723592293110705685L;
/* 193 */     return paramLong ^ paramLong >>> 31L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int mix32(long paramLong) {
/* 200 */     paramLong = (paramLong ^ paramLong >>> 33L) * 7109453100751455733L;
/* 201 */     return (int)((paramLong ^ paramLong >>> 28L) * -3808689974395783757L >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long mixGamma(long paramLong) {
/* 208 */     paramLong = (paramLong ^ paramLong >>> 33L) * -49064778989728563L;
/* 209 */     paramLong = (paramLong ^ paramLong >>> 33L) * -4265267296055464877L;
/* 210 */     paramLong = paramLong ^ paramLong >>> 33L | 0x1L;
/* 211 */     int i = Long.bitCount(paramLong ^ paramLong >>> 1L);
/* 212 */     return (i < 24) ? (paramLong ^ 0xAAAAAAAAAAAAAAAAL) : paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long nextSeed() {
/* 219 */     return this.seed += this.gamma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   private static final AtomicLong defaultGen = new AtomicLong(initialSeed());
/*     */   
/*     */   private static long initialSeed() {
/* 228 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.util.secureRandomSeed"));
/*     */ 
/*     */     
/* 231 */     if (str != null && str.equalsIgnoreCase("true")) {
/* 232 */       byte[] arrayOfByte = SecureRandom.getSeed(8);
/* 233 */       long l = arrayOfByte[0] & 0xFFL;
/* 234 */       for (byte b = 1; b < 8; b++)
/* 235 */         l = l << 8L | arrayOfByte[b] & 0xFFL; 
/* 236 */       return l;
/*     */     } 
/* 238 */     return mix64(System.currentTimeMillis()) ^ 
/* 239 */       mix64(System.nanoTime());
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
/*     */   static final String BadBound = "bound must be positive";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String BadRange = "bound must be greater than origin";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String BadSize = "size must be non-negative";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final long internalNextLong(long paramLong1, long paramLong2) {
/* 290 */     long l = mix64(nextSeed());
/* 291 */     if (paramLong1 < paramLong2) {
/* 292 */       long l1 = paramLong2 - paramLong1, l2 = l1 - 1L;
/* 293 */       if ((l1 & l2) == 0L) {
/* 294 */         l = (l & l2) + paramLong1;
/* 295 */       } else if (l1 > 0L) {
/* 296 */         long l3 = l >>> 1L;
/* 297 */         while (l3 + l2 - (l = l3 % l1) < 0L) {
/* 298 */           l3 = mix64(nextSeed()) >>> 1L;
/*     */         }
/* 300 */         l += paramLong1;
/*     */       } else {
/*     */         
/* 303 */         while (l < paramLong1 || l >= paramLong2)
/* 304 */           l = mix64(nextSeed()); 
/*     */       } 
/*     */     } 
/* 307 */     return l;
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
/*     */   final int internalNextInt(int paramInt1, int paramInt2) {
/* 319 */     int i = mix32(nextSeed());
/* 320 */     if (paramInt1 < paramInt2) {
/* 321 */       int j = paramInt2 - paramInt1, k = j - 1;
/* 322 */       if ((j & k) == 0) {
/* 323 */         i = (i & k) + paramInt1;
/* 324 */       } else if (j > 0) {
/* 325 */         int m = i >>> 1;
/* 326 */         while (m + k - (i = m % j) < 0) {
/* 327 */           m = mix32(nextSeed()) >>> 1;
/*     */         }
/* 329 */         i += paramInt1;
/*     */       } else {
/*     */         
/* 332 */         while (i < paramInt1 || i >= paramInt2)
/* 333 */           i = mix32(nextSeed()); 
/*     */       } 
/*     */     } 
/* 336 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final double internalNextDouble(double paramDouble1, double paramDouble2) {
/* 347 */     double d = (nextLong() >>> 11L) * 1.1102230246251565E-16D;
/* 348 */     if (paramDouble1 < paramDouble2) {
/* 349 */       d = d * (paramDouble2 - paramDouble1) + paramDouble1;
/* 350 */       if (d >= paramDouble2)
/* 351 */         d = Double.longBitsToDouble(Double.doubleToLongBits(paramDouble2) - 1L); 
/*     */     } 
/* 353 */     return d;
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
/*     */   public SplittableRandom(long paramLong) {
/* 366 */     this(paramLong, -7046029254386353131L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SplittableRandom() {
/* 376 */     long l = defaultGen.getAndAdd(4354685564936845354L);
/* 377 */     this.seed = mix64(l);
/* 378 */     this.gamma = mixGamma(l + -7046029254386353131L);
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
/*     */   public SplittableRandom split() {
/* 396 */     return new SplittableRandom(nextLong(), mixGamma(nextSeed()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 405 */     return mix32(nextSeed());
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
/*     */   public int nextInt(int paramInt) {
/* 418 */     if (paramInt <= 0) {
/* 419 */       throw new IllegalArgumentException("bound must be positive");
/*     */     }
/* 421 */     int i = mix32(nextSeed());
/* 422 */     int j = paramInt - 1;
/* 423 */     if ((paramInt & j) == 0) {
/* 424 */       i &= j;
/*     */     } else {
/* 426 */       int k = i >>> 1;
/* 427 */       while (k + j - (i = k % paramInt) < 0) {
/* 428 */         k = mix32(nextSeed()) >>> 1;
/*     */       }
/*     */     } 
/* 431 */     return i;
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
/*     */   public int nextInt(int paramInt1, int paramInt2) {
/* 446 */     if (paramInt1 >= paramInt2)
/* 447 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 448 */     return internalNextInt(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 457 */     return mix64(nextSeed());
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
/*     */   public long nextLong(long paramLong) {
/* 470 */     if (paramLong <= 0L) {
/* 471 */       throw new IllegalArgumentException("bound must be positive");
/*     */     }
/* 473 */     long l1 = mix64(nextSeed());
/* 474 */     long l2 = paramLong - 1L;
/* 475 */     if ((paramLong & l2) == 0L) {
/* 476 */       l1 &= l2;
/*     */     } else {
/* 478 */       long l = l1 >>> 1L;
/* 479 */       while (l + l2 - (l1 = l % paramLong) < 0L) {
/* 480 */         l = mix64(nextSeed()) >>> 1L;
/*     */       }
/*     */     } 
/* 483 */     return l1;
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
/*     */   public long nextLong(long paramLong1, long paramLong2) {
/* 498 */     if (paramLong1 >= paramLong2)
/* 499 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 500 */     return internalNextLong(paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/* 511 */     return (mix64(nextSeed()) >>> 11L) * 1.1102230246251565E-16D;
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
/*     */   public double nextDouble(double paramDouble) {
/* 524 */     if (paramDouble <= 0.0D)
/* 525 */       throw new IllegalArgumentException("bound must be positive"); 
/* 526 */     double d = (mix64(nextSeed()) >>> 11L) * 1.1102230246251565E-16D * paramDouble;
/* 527 */     return (d < paramDouble) ? d : 
/* 528 */       Double.longBitsToDouble(Double.doubleToLongBits(paramDouble) - 1L);
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
/*     */   public double nextDouble(double paramDouble1, double paramDouble2) {
/* 543 */     if (paramDouble1 >= paramDouble2)
/* 544 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 545 */     return internalNextDouble(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() {
/* 554 */     return (mix32(nextSeed()) < 0);
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
/*     */   public IntStream ints(long paramLong) {
/* 571 */     if (paramLong < 0L)
/* 572 */       throw new IllegalArgumentException("size must be non-negative"); 
/* 573 */     return 
/* 574 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, 2147483647, 0), false);
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
/*     */   public IntStream ints() {
/* 589 */     return 
/* 590 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, 2147483647, 0), false);
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
/*     */   public IntStream ints(long paramLong, int paramInt1, int paramInt2) {
/* 612 */     if (paramLong < 0L)
/* 613 */       throw new IllegalArgumentException("size must be non-negative"); 
/* 614 */     if (paramInt1 >= paramInt2)
/* 615 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 616 */     return 
/* 617 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, paramInt1, paramInt2), false);
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
/*     */   public IntStream ints(int paramInt1, int paramInt2) {
/* 638 */     if (paramInt1 >= paramInt2)
/* 639 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 640 */     return 
/* 641 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, paramInt1, paramInt2), false);
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
/*     */   public LongStream longs(long paramLong) {
/* 657 */     if (paramLong < 0L)
/* 658 */       throw new IllegalArgumentException("size must be non-negative"); 
/* 659 */     return 
/* 660 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong, Long.MAX_VALUE, 0L), false);
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
/*     */   public LongStream longs() {
/* 675 */     return 
/* 676 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
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
/*     */   public LongStream longs(long paramLong1, long paramLong2, long paramLong3) {
/* 698 */     if (paramLong1 < 0L)
/* 699 */       throw new IllegalArgumentException("size must be non-negative"); 
/* 700 */     if (paramLong2 >= paramLong3)
/* 701 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 702 */     return 
/* 703 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong1, paramLong2, paramLong3), false);
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
/*     */   public LongStream longs(long paramLong1, long paramLong2) {
/* 724 */     if (paramLong1 >= paramLong2)
/* 725 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 726 */     return 
/* 727 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, paramLong1, paramLong2), false);
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
/*     */   public DoubleStream doubles(long paramLong) {
/* 743 */     if (paramLong < 0L)
/* 744 */       throw new IllegalArgumentException("size must be non-negative"); 
/* 745 */     return 
/* 746 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, Double.MAX_VALUE, 0.0D), false);
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
/*     */   public DoubleStream doubles() {
/* 762 */     return 
/* 763 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0D), false);
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
/*     */   public DoubleStream doubles(long paramLong, double paramDouble1, double paramDouble2) {
/* 786 */     if (paramLong < 0L)
/* 787 */       throw new IllegalArgumentException("size must be non-negative"); 
/* 788 */     if (paramDouble1 >= paramDouble2)
/* 789 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 790 */     return 
/* 791 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, paramDouble1, paramDouble2), false);
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
/*     */   public DoubleStream doubles(double paramDouble1, double paramDouble2) {
/* 812 */     if (paramDouble1 >= paramDouble2)
/* 813 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/* 814 */     return 
/* 815 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, paramDouble1, paramDouble2), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class RandomIntsSpliterator
/*     */     implements Spliterator.OfInt
/*     */   {
/*     */     final SplittableRandom rng;
/*     */ 
/*     */     
/*     */     long index;
/*     */     
/*     */     final long fence;
/*     */     
/*     */     final int origin;
/*     */     
/*     */     final int bound;
/*     */ 
/*     */     
/*     */     RandomIntsSpliterator(SplittableRandom param1SplittableRandom, long param1Long1, long param1Long2, int param1Int1, int param1Int2) {
/* 836 */       this.rng = param1SplittableRandom; this.index = param1Long1; this.fence = param1Long2;
/* 837 */       this.origin = param1Int1; this.bound = param1Int2;
/*     */     }
/*     */     
/*     */     public RandomIntsSpliterator trySplit() {
/* 841 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/* 842 */       return (l2 <= l1) ? null : new RandomIntsSpliterator(this.rng
/* 843 */           .split(), l1, this.index = l2, this.origin, this.bound);
/*     */     }
/*     */     
/*     */     public long estimateSize() {
/* 847 */       return this.fence - this.index;
/*     */     }
/*     */     
/*     */     public int characteristics() {
/* 851 */       return 17728;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/* 856 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/* 857 */       long l1 = this.index, l2 = this.fence;
/* 858 */       if (l1 < l2) {
/* 859 */         param1IntConsumer.accept(this.rng.internalNextInt(this.origin, this.bound));
/* 860 */         this.index = l1 + 1L;
/* 861 */         return true;
/*     */       } 
/* 863 */       return false;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 867 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/* 868 */       long l1 = this.index, l2 = this.fence;
/* 869 */       if (l1 < l2) {
/* 870 */         this.index = l2;
/* 871 */         SplittableRandom splittableRandom = this.rng;
/* 872 */         int i = this.origin, j = this.bound;
/*     */         do {
/* 874 */           param1IntConsumer.accept(splittableRandom.internalNextInt(i, j));
/* 875 */         } while (++l1 < l2);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class RandomLongsSpliterator
/*     */     implements Spliterator.OfLong
/*     */   {
/*     */     final SplittableRandom rng;
/*     */     long index;
/*     */     final long fence;
/*     */     final long origin;
/*     */     final long bound;
/*     */     
/*     */     RandomLongsSpliterator(SplittableRandom param1SplittableRandom, long param1Long1, long param1Long2, long param1Long3, long param1Long4) {
/* 891 */       this.rng = param1SplittableRandom; this.index = param1Long1; this.fence = param1Long2;
/* 892 */       this.origin = param1Long3; this.bound = param1Long4;
/*     */     }
/*     */     
/*     */     public RandomLongsSpliterator trySplit() {
/* 896 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/* 897 */       return (l2 <= l1) ? null : new RandomLongsSpliterator(this.rng
/* 898 */           .split(), l1, this.index = l2, this.origin, this.bound);
/*     */     }
/*     */     
/*     */     public long estimateSize() {
/* 902 */       return this.fence - this.index;
/*     */     }
/*     */     
/*     */     public int characteristics() {
/* 906 */       return 17728;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/* 911 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/* 912 */       long l1 = this.index, l2 = this.fence;
/* 913 */       if (l1 < l2) {
/* 914 */         param1LongConsumer.accept(this.rng.internalNextLong(this.origin, this.bound));
/* 915 */         this.index = l1 + 1L;
/* 916 */         return true;
/*     */       } 
/* 918 */       return false;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/* 922 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/* 923 */       long l1 = this.index, l2 = this.fence;
/* 924 */       if (l1 < l2) {
/* 925 */         this.index = l2;
/* 926 */         SplittableRandom splittableRandom = this.rng;
/* 927 */         long l3 = this.origin, l4 = this.bound;
/*     */         do {
/* 929 */           param1LongConsumer.accept(splittableRandom.internalNextLong(l3, l4));
/* 930 */         } while (++l1 < l2);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class RandomDoublesSpliterator
/*     */     implements Spliterator.OfDouble
/*     */   {
/*     */     final SplittableRandom rng;
/*     */     
/*     */     long index;
/*     */     final long fence;
/*     */     final double origin;
/*     */     final double bound;
/*     */     
/*     */     RandomDoublesSpliterator(SplittableRandom param1SplittableRandom, long param1Long1, long param1Long2, double param1Double1, double param1Double2) {
/* 947 */       this.rng = param1SplittableRandom; this.index = param1Long1; this.fence = param1Long2;
/* 948 */       this.origin = param1Double1; this.bound = param1Double2;
/*     */     }
/*     */     
/*     */     public RandomDoublesSpliterator trySplit() {
/* 952 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/* 953 */       return (l2 <= l1) ? null : new RandomDoublesSpliterator(this.rng
/* 954 */           .split(), l1, this.index = l2, this.origin, this.bound);
/*     */     }
/*     */     
/*     */     public long estimateSize() {
/* 958 */       return this.fence - this.index;
/*     */     }
/*     */     
/*     */     public int characteristics() {
/* 962 */       return 17728;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/* 967 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/* 968 */       long l1 = this.index, l2 = this.fence;
/* 969 */       if (l1 < l2) {
/* 970 */         param1DoubleConsumer.accept(this.rng.internalNextDouble(this.origin, this.bound));
/* 971 */         this.index = l1 + 1L;
/* 972 */         return true;
/*     */       } 
/* 974 */       return false;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/* 978 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/* 979 */       long l1 = this.index, l2 = this.fence;
/* 980 */       if (l1 < l2) {
/* 981 */         this.index = l2;
/* 982 */         SplittableRandom splittableRandom = this.rng;
/* 983 */         double d1 = this.origin, d2 = this.bound;
/*     */         do {
/* 985 */           param1DoubleConsumer.accept(splittableRandom.internalNextDouble(d1, d2));
/* 986 */         } while (++l1 < l2);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/SplittableRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */