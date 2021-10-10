/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.stream.DoubleStream;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.misc.Unsafe;
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
/*      */ public class Random
/*      */   implements Serializable
/*      */ {
/*      */   static final long serialVersionUID = 3905348978240129619L;
/*      */   private final AtomicLong seed;
/*      */   private static final long multiplier = 25214903917L;
/*      */   private static final long addend = 11L;
/*      */   private static final long mask = 281474976710655L;
/*      */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/*      */   static final String BadBound = "bound must be positive";
/*      */   static final String BadRange = "bound must be greater than origin";
/*      */   static final String BadSize = "size must be non-negative";
/*      */   
/*      */   public Random() {
/*  105 */     this(seedUniquifier() ^ System.nanoTime());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static long seedUniquifier() {
/*      */     while (true) {
/*  112 */       long l1 = seedUniquifier.get();
/*  113 */       long l2 = l1 * 181783497276652981L;
/*  114 */       if (seedUniquifier.compareAndSet(l1, l2))
/*  115 */         return l2; 
/*      */     } 
/*      */   }
/*      */   
/*  119 */   private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);
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
/*      */   private double nextNextGaussian;
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
/*      */   private boolean haveNextNextGaussian;
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
/*      */   private static long initialScramble(long paramLong) {
/*      */     return (paramLong ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
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
/*      */   public synchronized void setSeed(long paramLong) {
/*      */     this.seed.set(initialScramble(paramLong));
/*      */     this.haveNextNextGaussian = false;
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
/*      */   protected int next(int paramInt) {
/*      */     AtomicLong atomicLong = this.seed;
/*      */     while (true) {
/*      */       long l1 = atomicLong.get();
/*      */       long l2 = l1 * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/*      */       if (atomicLong.compareAndSet(l1, l2)) {
/*      */         return (int)(l2 >>> 48 - paramInt);
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
/*      */   public Random(long paramLong) {
/*  536 */     this.haveNextNextGaussian = false;
/*      */     if (getClass() == Random.class) {
/*      */       this.seed = new AtomicLong(initialScramble(paramLong));
/*      */     } else {
/*      */       this.seed = new AtomicLong();
/*      */       setSeed(paramLong);
/*      */     } 
/*      */   } public void nextBytes(byte[] paramArrayOfbyte) {
/*      */     byte b;
/*      */     int i;
/*      */     for (b = 0, i = paramArrayOfbyte.length; b < i; ) {
/*      */       int j = nextInt();
/*      */       int k = Math.min(i - b, 4);
/*      */       for (; k-- > 0; j >>= 8)
/*      */         paramArrayOfbyte[b++] = (byte)j; 
/*      */     } 
/*      */   }
/*      */   final long internalNextLong(long paramLong1, long paramLong2) {
/*      */     long l = nextLong();
/*      */     if (paramLong1 < paramLong2) {
/*      */       long l1 = paramLong2 - paramLong1, l2 = l1 - 1L;
/*      */       if ((l1 & l2) == 0L) {
/*      */         l = (l & l2) + paramLong1;
/*      */       } else if (l1 > 0L) {
/*      */         long l3 = l >>> 1L;
/*      */         while (l3 + l2 - (l = l3 % l1) < 0L)
/*      */           l3 = nextLong() >>> 1L; 
/*      */         l += paramLong1;
/*      */       } else {
/*      */         while (l < paramLong1 || l >= paramLong2)
/*      */           l = nextLong(); 
/*      */       } 
/*      */     } 
/*      */     return l;
/*      */   }
/*      */   final int internalNextInt(int paramInt1, int paramInt2) {
/*      */     if (paramInt1 < paramInt2) {
/*      */       int i = paramInt2 - paramInt1;
/*      */       if (i > 0)
/*      */         return nextInt(i) + paramInt1; 
/*      */       while (true) {
/*      */         int j = nextInt();
/*      */         if (j >= paramInt1 && j < paramInt2)
/*      */           return j; 
/*      */       } 
/*      */     } 
/*      */     return nextInt();
/*      */   }
/*      */   public synchronized double nextGaussian() {
/*  585 */     if (this.haveNextNextGaussian) {
/*  586 */       this.haveNextNextGaussian = false;
/*  587 */       return this.nextNextGaussian;
/*      */     } 
/*      */     
/*      */     while (true) {
/*  591 */       double d1 = 2.0D * nextDouble() - 1.0D;
/*  592 */       double d2 = 2.0D * nextDouble() - 1.0D;
/*  593 */       double d3 = d1 * d1 + d2 * d2;
/*  594 */       if (d3 < 1.0D && d3 != 0.0D) {
/*  595 */         double d = StrictMath.sqrt(-2.0D * StrictMath.log(d3) / d3);
/*  596 */         this.nextNextGaussian = d2 * d;
/*  597 */         this.haveNextNextGaussian = true;
/*  598 */         return d1 * d;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final double internalNextDouble(double paramDouble1, double paramDouble2) {
/*      */     double d = nextDouble();
/*      */     if (paramDouble1 < paramDouble2) {
/*      */       d = d * (paramDouble2 - paramDouble1) + paramDouble1;
/*      */       if (d >= paramDouble2) {
/*      */         d = Double.longBitsToDouble(Double.doubleToLongBits(paramDouble2) - 1L);
/*      */       }
/*      */     } 
/*      */     return d;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IntStream ints(long paramLong) {
/*  619 */     if (paramLong < 0L)
/*  620 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  621 */     return 
/*  622 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, 2147483647, 0), false);
/*      */   }
/*      */   public int nextInt() { return next(32); }
/*      */   public int nextInt(int paramInt) { if (paramInt <= 0)
/*      */       throw new IllegalArgumentException("bound must be positive"); 
/*      */     int i = next(31);
/*      */     int j = paramInt - 1;
/*      */     if ((paramInt & j) == 0) {
/*      */       i = (int)(paramInt * i >> 31L);
/*      */     } else {
/*      */       int k = i;
/*      */       while (k - (i = k % paramInt) + j < 0)
/*      */         k = next(31); 
/*      */     } 
/*      */     return i; }
/*      */   public long nextLong() { return (next(32) << 32L) + next(32); }
/*      */   public boolean nextBoolean() { return (next(1) != 0); } public float nextFloat() { return next(24) / 1.6777216E7F; } public double nextDouble() {
/*      */     return ((next(26) << 27L) + next(27)) * 1.1102230246251565E-16D;
/*      */   } public IntStream ints() {
/*  641 */     return 
/*  642 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, 2147483647, 0), false);
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
/*  681 */     if (paramLong < 0L)
/*  682 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  683 */     if (paramInt1 >= paramInt2)
/*  684 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  685 */     return 
/*  686 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, paramInt1, paramInt2), false);
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
/*  725 */     if (paramInt1 >= paramInt2)
/*  726 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  727 */     return 
/*  728 */       StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, paramInt1, paramInt2), false);
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
/*      */   public LongStream longs(long paramLong) {
/*  747 */     if (paramLong < 0L)
/*  748 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  749 */     return 
/*  750 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong, Long.MAX_VALUE, 0L), false);
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
/*      */   public LongStream longs() {
/*  769 */     return 
/*  770 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
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
/*  814 */     if (paramLong1 < 0L)
/*  815 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  816 */     if (paramLong2 >= paramLong3)
/*  817 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  818 */     return 
/*  819 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong1, paramLong2, paramLong3), false);
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
/*  863 */     if (paramLong1 >= paramLong2)
/*  864 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  865 */     return 
/*  866 */       StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, paramLong1, paramLong2), false);
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
/*      */   public DoubleStream doubles(long paramLong) {
/*  886 */     if (paramLong < 0L)
/*  887 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  888 */     return 
/*  889 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, Double.MAX_VALUE, 0.0D), false);
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
/*      */   public DoubleStream doubles() {
/*  909 */     return 
/*  910 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0D), false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleStream doubles(long paramLong, double paramDouble1, double paramDouble2) {
/*  944 */     if (paramLong < 0L)
/*  945 */       throw new IllegalArgumentException("size must be non-negative"); 
/*  946 */     if (paramDouble1 >= paramDouble2)
/*  947 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  948 */     return 
/*  949 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, paramDouble1, paramDouble2), false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleStream doubles(double paramDouble1, double paramDouble2) {
/*  982 */     if (paramDouble1 >= paramDouble2)
/*  983 */       throw new IllegalArgumentException("bound must be greater than origin"); 
/*  984 */     return 
/*  985 */       StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, paramDouble1, paramDouble2), false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class RandomIntsSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     final Random rng;
/*      */ 
/*      */     
/*      */     long index;
/*      */     
/*      */     final long fence;
/*      */     
/*      */     final int origin;
/*      */     
/*      */     final int bound;
/*      */ 
/*      */     
/*      */     RandomIntsSpliterator(Random param1Random, long param1Long1, long param1Long2, int param1Int1, int param1Int2) {
/* 1006 */       this.rng = param1Random; this.index = param1Long1; this.fence = param1Long2;
/* 1007 */       this.origin = param1Int1; this.bound = param1Int2;
/*      */     }
/*      */     
/*      */     public RandomIntsSpliterator trySplit() {
/* 1011 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/* 1012 */       return (l2 <= l1) ? null : new RandomIntsSpliterator(this.rng, l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1017 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1021 */       return 17728;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/* 1026 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/* 1027 */       long l1 = this.index, l2 = this.fence;
/* 1028 */       if (l1 < l2) {
/* 1029 */         param1IntConsumer.accept(this.rng.internalNextInt(this.origin, this.bound));
/* 1030 */         this.index = l1 + 1L;
/* 1031 */         return true;
/*      */       } 
/* 1033 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 1037 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/* 1038 */       long l1 = this.index, l2 = this.fence;
/* 1039 */       if (l1 < l2) {
/* 1040 */         this.index = l2;
/* 1041 */         Random random = this.rng;
/* 1042 */         int i = this.origin, j = this.bound;
/*      */         do {
/* 1044 */           param1IntConsumer.accept(random.internalNextInt(i, j));
/* 1045 */         } while (++l1 < l2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class RandomLongsSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     final Random rng;
/*      */     long index;
/*      */     final long fence;
/*      */     final long origin;
/*      */     final long bound;
/*      */     
/*      */     RandomLongsSpliterator(Random param1Random, long param1Long1, long param1Long2, long param1Long3, long param1Long4) {
/* 1061 */       this.rng = param1Random; this.index = param1Long1; this.fence = param1Long2;
/* 1062 */       this.origin = param1Long3; this.bound = param1Long4;
/*      */     }
/*      */     
/*      */     public RandomLongsSpliterator trySplit() {
/* 1066 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/* 1067 */       return (l2 <= l1) ? null : new RandomLongsSpliterator(this.rng, l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1072 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1076 */       return 17728;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/* 1081 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/* 1082 */       long l1 = this.index, l2 = this.fence;
/* 1083 */       if (l1 < l2) {
/* 1084 */         param1LongConsumer.accept(this.rng.internalNextLong(this.origin, this.bound));
/* 1085 */         this.index = l1 + 1L;
/* 1086 */         return true;
/*      */       } 
/* 1088 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/* 1092 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/* 1093 */       long l1 = this.index, l2 = this.fence;
/* 1094 */       if (l1 < l2) {
/* 1095 */         this.index = l2;
/* 1096 */         Random random = this.rng;
/* 1097 */         long l3 = this.origin, l4 = this.bound;
/*      */         do {
/* 1099 */           param1LongConsumer.accept(random.internalNextLong(l3, l4));
/* 1100 */         } while (++l1 < l2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class RandomDoublesSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     final Random rng;
/*      */     
/*      */     long index;
/*      */     final long fence;
/*      */     final double origin;
/*      */     final double bound;
/*      */     
/*      */     RandomDoublesSpliterator(Random param1Random, long param1Long1, long param1Long2, double param1Double1, double param1Double2) {
/* 1117 */       this.rng = param1Random; this.index = param1Long1; this.fence = param1Long2;
/* 1118 */       this.origin = param1Double1; this.bound = param1Double2;
/*      */     }
/*      */     
/*      */     public RandomDoublesSpliterator trySplit() {
/* 1122 */       long l1 = this.index, l2 = l1 + this.fence >>> 1L;
/* 1123 */       return (l2 <= l1) ? null : new RandomDoublesSpliterator(this.rng, l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1128 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1132 */       return 17728;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/* 1137 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/* 1138 */       long l1 = this.index, l2 = this.fence;
/* 1139 */       if (l1 < l2) {
/* 1140 */         param1DoubleConsumer.accept(this.rng.internalNextDouble(this.origin, this.bound));
/* 1141 */         this.index = l1 + 1L;
/* 1142 */         return true;
/*      */       } 
/* 1144 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/* 1148 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/* 1149 */       long l1 = this.index, l2 = this.fence;
/* 1150 */       if (l1 < l2) {
/* 1151 */         this.index = l2;
/* 1152 */         Random random = this.rng;
/* 1153 */         double d1 = this.origin, d2 = this.bound;
/*      */         do {
/* 1155 */           param1DoubleConsumer.accept(random.internalNextDouble(d1, d2));
/* 1156 */         } while (++l1 < l2);
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
/* 1171 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("seed", long.class), new ObjectStreamField("nextNextGaussian", double.class), new ObjectStreamField("haveNextNextGaussian", boolean.class) };
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1184 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*      */ 
/*      */ 
/*      */     
/* 1188 */     long l = getField.get("seed", -1L);
/* 1189 */     if (l < 0L) {
/* 1190 */       throw new StreamCorruptedException("Random: invalid seed");
/*      */     }
/* 1192 */     resetSeed(l);
/* 1193 */     this.nextNextGaussian = getField.get("nextNextGaussian", 0.0D);
/* 1194 */     this.haveNextNextGaussian = getField.get("haveNextNextGaussian", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1204 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/*      */ 
/*      */     
/* 1207 */     putField.put("seed", this.seed.get());
/* 1208 */     putField.put("nextNextGaussian", this.nextNextGaussian);
/* 1209 */     putField.put("haveNextNextGaussian", this.haveNextNextGaussian);
/*      */ 
/*      */     
/* 1212 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */ 
/*      */   
/* 1216 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */   private static final long seedOffset;
/*      */   
/*      */   static {
/*      */     
/* 1221 */     try { seedOffset = unsafe.objectFieldOffset(Random.class.getDeclaredField("seed")); }
/* 1222 */     catch (Exception exception) { throw new Error(exception); }
/*      */   
/*      */   } private void resetSeed(long paramLong) {
/* 1225 */     unsafe.putObjectVolatile(this, seedOffset, new AtomicLong(paramLong));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Random.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */