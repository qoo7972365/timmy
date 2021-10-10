/*      */ package java.util;
/*      */ 
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Spliterators
/*      */ {
/*      */   public static <T> Spliterator<T> emptySpliterator() {
/*   60 */     return (Spliterator)EMPTY_SPLITERATOR;
/*      */   }
/*      */   
/*   63 */   private static final Spliterator<Object> EMPTY_SPLITERATOR = new EmptySpliterator.OfRef();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Spliterator.OfInt emptyIntSpliterator() {
/*   76 */     return EMPTY_INT_SPLITERATOR;
/*      */   }
/*      */   
/*   79 */   private static final Spliterator.OfInt EMPTY_INT_SPLITERATOR = new EmptySpliterator.OfInt();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Spliterator.OfLong emptyLongSpliterator() {
/*   92 */     return EMPTY_LONG_SPLITERATOR;
/*      */   }
/*      */   
/*   95 */   private static final Spliterator.OfLong EMPTY_LONG_SPLITERATOR = new EmptySpliterator.OfLong();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Spliterator.OfDouble emptyDoubleSpliterator() {
/*  108 */     return EMPTY_DOUBLE_SPLITERATOR;
/*      */   }
/*      */   
/*  111 */   private static final Spliterator.OfDouble EMPTY_DOUBLE_SPLITERATOR = new EmptySpliterator.OfDouble();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Spliterator<T> spliterator(Object[] paramArrayOfObject, int paramInt) {
/*  142 */     return new ArraySpliterator<>(Objects.<Object[]>requireNonNull(paramArrayOfObject), paramInt);
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
/*      */   public static <T> Spliterator<T> spliterator(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3) {
/*  177 */     checkFromToBounds(((Object[])Objects.requireNonNull((T)paramArrayOfObject)).length, paramInt1, paramInt2);
/*  178 */     return new ArraySpliterator<>(paramArrayOfObject, paramInt1, paramInt2, paramInt3);
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
/*      */   public static Spliterator.OfInt spliterator(int[] paramArrayOfint, int paramInt) {
/*  206 */     return new IntArraySpliterator(Objects.<int[]>requireNonNull(paramArrayOfint), paramInt);
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
/*      */   public static Spliterator.OfInt spliterator(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
/*  239 */     checkFromToBounds(((int[])Objects.requireNonNull((T)paramArrayOfint)).length, paramInt1, paramInt2);
/*  240 */     return new IntArraySpliterator(paramArrayOfint, paramInt1, paramInt2, paramInt3);
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
/*      */   public static Spliterator.OfLong spliterator(long[] paramArrayOflong, int paramInt) {
/*  268 */     return new LongArraySpliterator(Objects.<long[]>requireNonNull(paramArrayOflong), paramInt);
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
/*      */   public static Spliterator.OfLong spliterator(long[] paramArrayOflong, int paramInt1, int paramInt2, int paramInt3) {
/*  305 */     checkFromToBounds(((long[])Objects.requireNonNull((T)paramArrayOflong)).length, paramInt1, paramInt2);
/*  306 */     return new LongArraySpliterator(paramArrayOflong, paramInt1, paramInt2, paramInt3);
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
/*      */   public static Spliterator.OfDouble spliterator(double[] paramArrayOfdouble, int paramInt) {
/*  334 */     return new DoubleArraySpliterator(Objects.<double[]>requireNonNull(paramArrayOfdouble), paramInt);
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
/*      */   public static Spliterator.OfDouble spliterator(double[] paramArrayOfdouble, int paramInt1, int paramInt2, int paramInt3) {
/*  371 */     checkFromToBounds(((double[])Objects.requireNonNull((T)paramArrayOfdouble)).length, paramInt1, paramInt2);
/*  372 */     return new DoubleArraySpliterator(paramArrayOfdouble, paramInt1, paramInt2, paramInt3);
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
/*      */   private static void checkFromToBounds(int paramInt1, int paramInt2, int paramInt3) {
/*  386 */     if (paramInt2 > paramInt3) {
/*  387 */       throw new ArrayIndexOutOfBoundsException("origin(" + paramInt2 + ") > fence(" + paramInt3 + ")");
/*      */     }
/*      */     
/*  390 */     if (paramInt2 < 0) {
/*  391 */       throw new ArrayIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  393 */     if (paramInt3 > paramInt1) {
/*  394 */       throw new ArrayIndexOutOfBoundsException(paramInt3);
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
/*      */   public static <T> Spliterator<T> spliterator(Collection<? extends T> paramCollection, int paramInt) {
/*  420 */     return new IteratorSpliterator<>(Objects.<Collection<? extends T>>requireNonNull(paramCollection), paramInt);
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
/*      */   public static <T> Spliterator<T> spliterator(Iterator<? extends T> paramIterator, long paramLong, int paramInt) {
/*  451 */     return new IteratorSpliterator<>(Objects.<Iterator<? extends T>>requireNonNull(paramIterator), paramLong, paramInt);
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
/*      */   public static <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> paramIterator, int paramInt) {
/*  478 */     return new IteratorSpliterator<>(Objects.<Iterator<? extends T>>requireNonNull(paramIterator), paramInt);
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
/*      */   public static Spliterator.OfInt spliterator(PrimitiveIterator.OfInt paramOfInt, long paramLong, int paramInt) {
/*  508 */     return new IntIteratorSpliterator(Objects.<PrimitiveIterator.OfInt>requireNonNull(paramOfInt), paramLong, paramInt);
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
/*      */   public static Spliterator.OfInt spliteratorUnknownSize(PrimitiveIterator.OfInt paramOfInt, int paramInt) {
/*  535 */     return new IntIteratorSpliterator(Objects.<PrimitiveIterator.OfInt>requireNonNull(paramOfInt), paramInt);
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
/*      */   public static Spliterator.OfLong spliterator(PrimitiveIterator.OfLong paramOfLong, long paramLong, int paramInt) {
/*  565 */     return new LongIteratorSpliterator(Objects.<PrimitiveIterator.OfLong>requireNonNull(paramOfLong), paramLong, paramInt);
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
/*      */   public static Spliterator.OfLong spliteratorUnknownSize(PrimitiveIterator.OfLong paramOfLong, int paramInt) {
/*  592 */     return new LongIteratorSpliterator(Objects.<PrimitiveIterator.OfLong>requireNonNull(paramOfLong), paramInt);
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
/*      */   public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble paramOfDouble, long paramLong, int paramInt) {
/*  622 */     return new DoubleIteratorSpliterator(Objects.<PrimitiveIterator.OfDouble>requireNonNull(paramOfDouble), paramLong, paramInt);
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
/*      */   public static Spliterator.OfDouble spliteratorUnknownSize(PrimitiveIterator.OfDouble paramOfDouble, int paramInt) {
/*  649 */     return new DoubleIteratorSpliterator(Objects.<PrimitiveIterator.OfDouble>requireNonNull(paramOfDouble), paramInt);
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
/*      */   public static <T> Iterator<T> iterator(final Spliterator<? extends T> spliterator) {
/*  667 */     Objects.requireNonNull(spliterator);
/*      */     class Adapter
/*      */       implements Iterator<T>, Consumer<T> {
/*      */       boolean valueReady = false;
/*      */       T nextElement;
/*      */       
/*      */       public void accept(T param1T) {
/*  674 */         this.valueReady = true;
/*  675 */         this.nextElement = param1T;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/*  680 */         if (!this.valueReady)
/*  681 */           spliterator.tryAdvance(this); 
/*  682 */         return this.valueReady;
/*      */       }
/*      */ 
/*      */       
/*      */       public T next() {
/*  687 */         if (!this.valueReady && !hasNext()) {
/*  688 */           throw new NoSuchElementException();
/*      */         }
/*  690 */         this.valueReady = false;
/*  691 */         return this.nextElement;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*  696 */     return new Adapter();
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
/*      */   public static PrimitiveIterator.OfInt iterator(final Spliterator.OfInt spliterator) {
/*  712 */     Objects.requireNonNull(spliterator);
/*      */     class Adapter
/*      */       implements PrimitiveIterator.OfInt, IntConsumer {
/*      */       boolean valueReady = false;
/*      */       int nextElement;
/*      */       
/*      */       public void accept(int param1Int) {
/*  719 */         this.valueReady = true;
/*  720 */         this.nextElement = param1Int;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/*  725 */         if (!this.valueReady)
/*  726 */           spliterator.tryAdvance(this); 
/*  727 */         return this.valueReady;
/*      */       }
/*      */ 
/*      */       
/*      */       public int nextInt() {
/*  732 */         if (!this.valueReady && !hasNext()) {
/*  733 */           throw new NoSuchElementException();
/*      */         }
/*  735 */         this.valueReady = false;
/*  736 */         return this.nextElement;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*  741 */     return new Adapter();
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
/*      */   public static PrimitiveIterator.OfLong iterator(final Spliterator.OfLong spliterator) {
/*  757 */     Objects.requireNonNull(spliterator);
/*      */     class Adapter
/*      */       implements PrimitiveIterator.OfLong, LongConsumer {
/*      */       boolean valueReady = false;
/*      */       long nextElement;
/*      */       
/*      */       public void accept(long param1Long) {
/*  764 */         this.valueReady = true;
/*  765 */         this.nextElement = param1Long;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/*  770 */         if (!this.valueReady)
/*  771 */           spliterator.tryAdvance(this); 
/*  772 */         return this.valueReady;
/*      */       }
/*      */ 
/*      */       
/*      */       public long nextLong() {
/*  777 */         if (!this.valueReady && !hasNext()) {
/*  778 */           throw new NoSuchElementException();
/*      */         }
/*  780 */         this.valueReady = false;
/*  781 */         return this.nextElement;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*  786 */     return new Adapter();
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
/*      */   public static PrimitiveIterator.OfDouble iterator(final Spliterator.OfDouble spliterator) {
/*  802 */     Objects.requireNonNull(spliterator);
/*      */     class Adapter
/*      */       implements PrimitiveIterator.OfDouble, DoubleConsumer {
/*      */       boolean valueReady = false;
/*      */       double nextElement;
/*      */       
/*      */       public void accept(double param1Double) {
/*  809 */         this.valueReady = true;
/*  810 */         this.nextElement = param1Double;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/*  815 */         if (!this.valueReady)
/*  816 */           spliterator.tryAdvance(this); 
/*  817 */         return this.valueReady;
/*      */       }
/*      */ 
/*      */       
/*      */       public double nextDouble() {
/*  822 */         if (!this.valueReady && !hasNext()) {
/*  823 */           throw new NoSuchElementException();
/*      */         }
/*  825 */         this.valueReady = false;
/*  826 */         return this.nextElement;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*  831 */     return new Adapter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class EmptySpliterator<T, S extends Spliterator<T>, C>
/*      */   {
/*      */     public S trySplit() {
/*  841 */       return null;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(C param1C) {
/*  845 */       Objects.requireNonNull(param1C);
/*  846 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(C param1C) {
/*  850 */       Objects.requireNonNull(param1C);
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  854 */       return 0L;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  858 */       return 16448;
/*      */     }
/*      */ 
/*      */     
/*      */     private static final class OfRef<T>
/*      */       extends EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
/*      */       implements Spliterator<T> {}
/*      */ 
/*      */     
/*      */     private static final class OfInt
/*      */       extends EmptySpliterator<Integer, Spliterator.OfInt, IntConsumer>
/*      */       implements Spliterator.OfInt {}
/*      */ 
/*      */     
/*      */     private static final class OfLong
/*      */       extends EmptySpliterator<Long, Spliterator.OfLong, LongConsumer>
/*      */       implements Spliterator.OfLong {}
/*      */ 
/*      */     
/*      */     private static final class OfDouble
/*      */       extends EmptySpliterator<Double, Spliterator.OfDouble, DoubleConsumer>
/*      */       implements Spliterator.OfDouble {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class OfRef<T>
/*      */     extends EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
/*      */     implements Spliterator<T> {}
/*      */ 
/*      */   
/*      */   private static final class OfInt
/*      */     extends EmptySpliterator<Integer, Spliterator.OfInt, IntConsumer>
/*      */     implements Spliterator.OfInt {}
/*      */ 
/*      */   
/*      */   private static final class OfLong
/*      */     extends EmptySpliterator<Long, Spliterator.OfLong, LongConsumer>
/*      */     implements Spliterator.OfLong {}
/*      */ 
/*      */   
/*      */   private static final class OfDouble
/*      */     extends EmptySpliterator<Double, Spliterator.OfDouble, DoubleConsumer>
/*      */     implements Spliterator.OfDouble {}
/*      */ 
/*      */   
/*      */   static final class ArraySpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     private final Object[] array;
/*      */     
/*      */     private int index;
/*      */     private final int fence;
/*      */     private final int characteristics;
/*      */     
/*      */     public ArraySpliterator(Object[] param1ArrayOfObject, int param1Int) {
/*  913 */       this(param1ArrayOfObject, 0, param1ArrayOfObject.length, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ArraySpliterator(Object[] param1ArrayOfObject, int param1Int1, int param1Int2, int param1Int3) {
/*  926 */       this.array = param1ArrayOfObject;
/*  927 */       this.index = param1Int1;
/*  928 */       this.fence = param1Int2;
/*  929 */       this.characteristics = param1Int3 | 0x40 | 0x4000;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<T> trySplit() {
/*  934 */       int i = this.index, j = i + this.fence >>> 1;
/*  935 */       return (i >= j) ? null : new ArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/*  944 */       if (param1Consumer == null)
/*  945 */         throw new NullPointerException();  Object[] arrayOfObject; int i; int j;
/*  946 */       if ((arrayOfObject = this.array).length >= (j = this.fence) && (i = this.index) >= 0 && i < (this.index = j)) {
/*      */         
/*  948 */         do { param1Consumer.accept((T)arrayOfObject[i]); } while (++i < j);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/*  954 */       if (param1Consumer == null)
/*  955 */         throw new NullPointerException(); 
/*  956 */       if (this.index >= 0 && this.index < this.fence) {
/*  957 */         Object object = this.array[this.index++];
/*  958 */         param1Consumer.accept((T)object);
/*  959 */         return true;
/*      */       } 
/*  961 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  965 */       return (this.fence - this.index);
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  969 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super T> getComparator() {
/*  974 */       if (hasCharacteristics(4))
/*  975 */         return null; 
/*  976 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class IntArraySpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     private final int[] array;
/*      */ 
/*      */     
/*      */     private int index;
/*      */ 
/*      */     
/*      */     private final int fence;
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */     
/*      */     public IntArraySpliterator(int[] param1ArrayOfint, int param1Int) {
/*  998 */       this(param1ArrayOfint, 0, param1ArrayOfint.length, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IntArraySpliterator(int[] param1ArrayOfint, int param1Int1, int param1Int2, int param1Int3) {
/* 1011 */       this.array = param1ArrayOfint;
/* 1012 */       this.index = param1Int1;
/* 1013 */       this.fence = param1Int2;
/* 1014 */       this.characteristics = param1Int3 | 0x40 | 0x4000;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfInt trySplit() {
/* 1019 */       int i = this.index, j = i + this.fence >>> 1;
/* 1020 */       return (i >= j) ? null : new IntArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 1028 */       if (param1IntConsumer == null)
/* 1029 */         throw new NullPointerException();  int[] arrayOfInt; int i; int j;
/* 1030 */       if ((arrayOfInt = this.array).length >= (j = this.fence) && (i = this.index) >= 0 && i < (this.index = j)) {
/*      */         
/* 1032 */         do { param1IntConsumer.accept(arrayOfInt[i]); } while (++i < j);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/* 1038 */       if (param1IntConsumer == null)
/* 1039 */         throw new NullPointerException(); 
/* 1040 */       if (this.index >= 0 && this.index < this.fence) {
/* 1041 */         param1IntConsumer.accept(this.array[this.index++]);
/* 1042 */         return true;
/*      */       } 
/* 1044 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1048 */       return (this.fence - this.index);
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1052 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Integer> getComparator() {
/* 1057 */       if (hasCharacteristics(4))
/* 1058 */         return null; 
/* 1059 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LongArraySpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     private final long[] array;
/*      */ 
/*      */     
/*      */     private int index;
/*      */ 
/*      */     
/*      */     private final int fence;
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */     
/*      */     public LongArraySpliterator(long[] param1ArrayOflong, int param1Int) {
/* 1081 */       this(param1ArrayOflong, 0, param1ArrayOflong.length, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LongArraySpliterator(long[] param1ArrayOflong, int param1Int1, int param1Int2, int param1Int3) {
/* 1094 */       this.array = param1ArrayOflong;
/* 1095 */       this.index = param1Int1;
/* 1096 */       this.fence = param1Int2;
/* 1097 */       this.characteristics = param1Int3 | 0x40 | 0x4000;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfLong trySplit() {
/* 1102 */       int i = this.index, j = i + this.fence >>> 1;
/* 1103 */       return (i >= j) ? null : new LongArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/* 1111 */       if (param1LongConsumer == null)
/* 1112 */         throw new NullPointerException();  long[] arrayOfLong; int i; int j;
/* 1113 */       if ((arrayOfLong = this.array).length >= (j = this.fence) && (i = this.index) >= 0 && i < (this.index = j)) {
/*      */         
/* 1115 */         do { param1LongConsumer.accept(arrayOfLong[i]); } while (++i < j);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/* 1121 */       if (param1LongConsumer == null)
/* 1122 */         throw new NullPointerException(); 
/* 1123 */       if (this.index >= 0 && this.index < this.fence) {
/* 1124 */         param1LongConsumer.accept(this.array[this.index++]);
/* 1125 */         return true;
/*      */       } 
/* 1127 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1131 */       return (this.fence - this.index);
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1135 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Long> getComparator() {
/* 1140 */       if (hasCharacteristics(4))
/* 1141 */         return null; 
/* 1142 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class DoubleArraySpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     private final double[] array;
/*      */ 
/*      */     
/*      */     private int index;
/*      */ 
/*      */     
/*      */     private final int fence;
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */     
/*      */     public DoubleArraySpliterator(double[] param1ArrayOfdouble, int param1Int) {
/* 1164 */       this(param1ArrayOfdouble, 0, param1ArrayOfdouble.length, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleArraySpliterator(double[] param1ArrayOfdouble, int param1Int1, int param1Int2, int param1Int3) {
/* 1177 */       this.array = param1ArrayOfdouble;
/* 1178 */       this.index = param1Int1;
/* 1179 */       this.fence = param1Int2;
/* 1180 */       this.characteristics = param1Int3 | 0x40 | 0x4000;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfDouble trySplit() {
/* 1185 */       int i = this.index, j = i + this.fence >>> 1;
/* 1186 */       return (i >= j) ? null : new DoubleArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/* 1194 */       if (param1DoubleConsumer == null)
/* 1195 */         throw new NullPointerException();  double[] arrayOfDouble; int i; int j;
/* 1196 */       if ((arrayOfDouble = this.array).length >= (j = this.fence) && (i = this.index) >= 0 && i < (this.index = j)) {
/*      */         
/* 1198 */         do { param1DoubleConsumer.accept(arrayOfDouble[i]); } while (++i < j);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/* 1204 */       if (param1DoubleConsumer == null)
/* 1205 */         throw new NullPointerException(); 
/* 1206 */       if (this.index >= 0 && this.index < this.fence) {
/* 1207 */         param1DoubleConsumer.accept(this.array[this.index++]);
/* 1208 */         return true;
/*      */       } 
/* 1210 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1214 */       return (this.fence - this.index);
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1218 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Double> getComparator() {
/* 1223 */       if (hasCharacteristics(4))
/* 1224 */         return null; 
/* 1225 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class AbstractSpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int MAX_BATCH = 33554432;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long est;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int batch;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AbstractSpliterator(long param1Long, int param1Int) {
/* 1274 */       this.est = param1Long;
/* 1275 */       this.characteristics = ((param1Int & 0x40) != 0) ? (param1Int | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     static final class HoldingConsumer<T>
/*      */       implements Consumer<T>
/*      */     {
/*      */       Object value;
/*      */       
/*      */       public void accept(T param2T) {
/* 1285 */         this.value = param2T;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator<T> trySplit() {
/* 1309 */       HoldingConsumer<? super T> holdingConsumer = new HoldingConsumer();
/* 1310 */       long l = this.est;
/* 1311 */       if (l > 1L && tryAdvance(holdingConsumer)) {
/* 1312 */         int i = this.batch + 1024;
/* 1313 */         if (i > l)
/* 1314 */           i = (int)l; 
/* 1315 */         if (i > 33554432)
/* 1316 */           i = 33554432; 
/* 1317 */         Object[] arrayOfObject = new Object[i];
/* 1318 */         byte b = 0; 
/* 1319 */         do { arrayOfObject[b] = holdingConsumer.value; } while (++b < i && tryAdvance(holdingConsumer));
/* 1320 */         this.batch = b;
/* 1321 */         if (this.est != Long.MAX_VALUE)
/* 1322 */           this.est -= b; 
/* 1323 */         return new Spliterators.ArraySpliterator<>(arrayOfObject, 0, b, characteristics());
/*      */       } 
/* 1325 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1338 */       return this.est;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1350 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class AbstractIntSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     static final int MAX_BATCH = 33554432;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int BATCH_UNIT = 1024;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long est;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int batch;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AbstractIntSpliterator(long param1Long, int param1Int) {
/* 1397 */       this.est = param1Long;
/* 1398 */       this.characteristics = ((param1Int & 0x40) != 0) ? (param1Int | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     static final class HoldingIntConsumer
/*      */       implements IntConsumer
/*      */     {
/*      */       int value;
/*      */       
/*      */       public void accept(int param2Int) {
/* 1408 */         this.value = param2Int;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator.OfInt trySplit() {
/* 1419 */       HoldingIntConsumer holdingIntConsumer = new HoldingIntConsumer();
/* 1420 */       long l = this.est;
/* 1421 */       if (l > 1L && tryAdvance(holdingIntConsumer)) {
/* 1422 */         int i = this.batch + 1024;
/* 1423 */         if (i > l)
/* 1424 */           i = (int)l; 
/* 1425 */         if (i > 33554432)
/* 1426 */           i = 33554432; 
/* 1427 */         int[] arrayOfInt = new int[i];
/* 1428 */         byte b = 0; 
/* 1429 */         do { arrayOfInt[b] = holdingIntConsumer.value; } while (++b < i && tryAdvance(holdingIntConsumer));
/* 1430 */         this.batch = b;
/* 1431 */         if (this.est != Long.MAX_VALUE)
/* 1432 */           this.est -= b; 
/* 1433 */         return new Spliterators.IntArraySpliterator(arrayOfInt, 0, b, characteristics());
/*      */       } 
/* 1435 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1448 */       return this.est;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1460 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class AbstractLongSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     static final int MAX_BATCH = 33554432;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int BATCH_UNIT = 1024;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long est;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int batch;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AbstractLongSpliterator(long param1Long, int param1Int) {
/* 1507 */       this.est = param1Long;
/* 1508 */       this.characteristics = ((param1Int & 0x40) != 0) ? (param1Int | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     static final class HoldingLongConsumer
/*      */       implements LongConsumer
/*      */     {
/*      */       long value;
/*      */       
/*      */       public void accept(long param2Long) {
/* 1518 */         this.value = param2Long;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator.OfLong trySplit() {
/* 1529 */       HoldingLongConsumer holdingLongConsumer = new HoldingLongConsumer();
/* 1530 */       long l = this.est;
/* 1531 */       if (l > 1L && tryAdvance(holdingLongConsumer)) {
/* 1532 */         int i = this.batch + 1024;
/* 1533 */         if (i > l)
/* 1534 */           i = (int)l; 
/* 1535 */         if (i > 33554432)
/* 1536 */           i = 33554432; 
/* 1537 */         long[] arrayOfLong = new long[i];
/* 1538 */         byte b = 0; 
/* 1539 */         do { arrayOfLong[b] = holdingLongConsumer.value; } while (++b < i && tryAdvance(holdingLongConsumer));
/* 1540 */         this.batch = b;
/* 1541 */         if (this.est != Long.MAX_VALUE)
/* 1542 */           this.est -= b; 
/* 1543 */         return new Spliterators.LongArraySpliterator(arrayOfLong, 0, b, characteristics());
/*      */       } 
/* 1545 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1558 */       return this.est;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1570 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class AbstractDoubleSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     static final int MAX_BATCH = 33554432;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int BATCH_UNIT = 1024;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long est;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int batch;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AbstractDoubleSpliterator(long param1Long, int param1Int) {
/* 1617 */       this.est = param1Long;
/* 1618 */       this.characteristics = ((param1Int & 0x40) != 0) ? (param1Int | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     static final class HoldingDoubleConsumer
/*      */       implements DoubleConsumer
/*      */     {
/*      */       double value;
/*      */       
/*      */       public void accept(double param2Double) {
/* 1628 */         this.value = param2Double;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator.OfDouble trySplit() {
/* 1639 */       HoldingDoubleConsumer holdingDoubleConsumer = new HoldingDoubleConsumer();
/* 1640 */       long l = this.est;
/* 1641 */       if (l > 1L && tryAdvance(holdingDoubleConsumer)) {
/* 1642 */         int i = this.batch + 1024;
/* 1643 */         if (i > l)
/* 1644 */           i = (int)l; 
/* 1645 */         if (i > 33554432)
/* 1646 */           i = 33554432; 
/* 1647 */         double[] arrayOfDouble = new double[i];
/* 1648 */         byte b = 0; 
/* 1649 */         do { arrayOfDouble[b] = holdingDoubleConsumer.value; } while (++b < i && tryAdvance(holdingDoubleConsumer));
/* 1650 */         this.batch = b;
/* 1651 */         if (this.est != Long.MAX_VALUE)
/* 1652 */           this.est -= b; 
/* 1653 */         return new Spliterators.DoubleArraySpliterator(arrayOfDouble, 0, b, characteristics());
/*      */       } 
/* 1655 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1668 */       return this.est;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1680 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class IteratorSpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */ 
/*      */     
/*      */     static final int MAX_BATCH = 33554432;
/*      */ 
/*      */     
/*      */     private final Collection<? extends T> collection;
/*      */ 
/*      */     
/*      */     private Iterator<? extends T> it;
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */     
/*      */     private long est;
/*      */ 
/*      */     
/*      */     private int batch;
/*      */ 
/*      */     
/*      */     public IteratorSpliterator(Collection<? extends T> param1Collection, int param1Int) {
/* 1711 */       this.collection = param1Collection;
/* 1712 */       this.it = null;
/* 1713 */       this.characteristics = ((param1Int & 0x1000) == 0) ? (param1Int | 0x40 | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IteratorSpliterator(Iterator<? extends T> param1Iterator, long param1Long, int param1Int) {
/* 1729 */       this.collection = null;
/* 1730 */       this.it = param1Iterator;
/* 1731 */       this.est = param1Long;
/* 1732 */       this.characteristics = ((param1Int & 0x1000) == 0) ? (param1Int | 0x40 | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IteratorSpliterator(Iterator<? extends T> param1Iterator, int param1Int) {
/* 1747 */       this.collection = null;
/* 1748 */       this.it = param1Iterator;
/* 1749 */       this.est = Long.MAX_VALUE;
/* 1750 */       this.characteristics = param1Int & 0xFFFFBFBF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator<T> trySplit() {
/* 1771 */       Iterator<? extends T> iterator = this.it = this.collection.iterator();
/* 1772 */       long l = this.est = this.collection.size();
/*      */ 
/*      */       
/* 1775 */       l = this.est;
/* 1776 */       if (l > 1L && iterator.hasNext()) {
/* 1777 */         int i = this.batch + 1024;
/* 1778 */         if (i > l)
/* 1779 */           i = (int)l; 
/* 1780 */         if (i > 33554432)
/* 1781 */           i = 33554432; 
/* 1782 */         Object[] arrayOfObject = new Object[i];
/* 1783 */         byte b = 0; 
/* 1784 */         do { arrayOfObject[b] = iterator.next(); } while (++b < i && iterator.hasNext());
/* 1785 */         this.batch = b;
/* 1786 */         if (this.est != Long.MAX_VALUE)
/* 1787 */           this.est -= b; 
/* 1788 */         return new Spliterators.ArraySpliterator<>(arrayOfObject, 0, b, this.characteristics);
/*      */       } 
/* 1790 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/* 1795 */       if (param1Consumer == null) throw new NullPointerException(); 
/*      */       Iterator<? extends T> iterator;
/* 1797 */       if ((iterator = this.it) == null) {
/* 1798 */         iterator = this.it = this.collection.iterator();
/* 1799 */         this.est = this.collection.size();
/*      */       } 
/* 1801 */       iterator.forEachRemaining(param1Consumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/* 1806 */       if (param1Consumer == null) throw new NullPointerException(); 
/* 1807 */       if (this.it == null) {
/* 1808 */         this.it = this.collection.iterator();
/* 1809 */         this.est = this.collection.size();
/*      */       } 
/* 1811 */       if (this.it.hasNext()) {
/* 1812 */         param1Consumer.accept(this.it.next());
/* 1813 */         return true;
/*      */       } 
/* 1815 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1820 */       if (this.it == null) {
/* 1821 */         this.it = this.collection.iterator();
/* 1822 */         return this.est = this.collection.size();
/*      */       } 
/* 1824 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1828 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super T> getComparator() {
/* 1832 */       if (hasCharacteristics(4))
/* 1833 */         return null; 
/* 1834 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class IntIteratorSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */ 
/*      */     
/*      */     static final int MAX_BATCH = 33554432;
/*      */ 
/*      */     
/*      */     private PrimitiveIterator.OfInt it;
/*      */ 
/*      */     
/*      */     private final int characteristics;
/*      */ 
/*      */     
/*      */     private long est;
/*      */ 
/*      */     
/*      */     private int batch;
/*      */ 
/*      */     
/*      */     public IntIteratorSpliterator(PrimitiveIterator.OfInt param1OfInt, long param1Long, int param1Int) {
/* 1862 */       this.it = param1OfInt;
/* 1863 */       this.est = param1Long;
/* 1864 */       this.characteristics = ((param1Int & 0x1000) == 0) ? (param1Int | 0x40 | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IntIteratorSpliterator(PrimitiveIterator.OfInt param1OfInt, int param1Int) {
/* 1879 */       this.it = param1OfInt;
/* 1880 */       this.est = Long.MAX_VALUE;
/* 1881 */       this.characteristics = param1Int & 0xFFFFBFBF;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfInt trySplit() {
/* 1886 */       PrimitiveIterator.OfInt ofInt = this.it;
/* 1887 */       long l = this.est;
/* 1888 */       if (l > 1L && ofInt.hasNext()) {
/* 1889 */         int i = this.batch + 1024;
/* 1890 */         if (i > l)
/* 1891 */           i = (int)l; 
/* 1892 */         if (i > 33554432)
/* 1893 */           i = 33554432; 
/* 1894 */         int[] arrayOfInt = new int[i];
/* 1895 */         byte b = 0; 
/* 1896 */         do { arrayOfInt[b] = ofInt.nextInt(); } while (++b < i && ofInt.hasNext());
/* 1897 */         this.batch = b;
/* 1898 */         if (this.est != Long.MAX_VALUE)
/* 1899 */           this.est -= b; 
/* 1900 */         return new Spliterators.IntArraySpliterator(arrayOfInt, 0, b, this.characteristics);
/*      */       } 
/* 1902 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 1907 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/* 1908 */       this.it.forEachRemaining(param1IntConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/* 1913 */       if (param1IntConsumer == null) throw new NullPointerException(); 
/* 1914 */       if (this.it.hasNext()) {
/* 1915 */         param1IntConsumer.accept(this.it.nextInt());
/* 1916 */         return true;
/*      */       } 
/* 1918 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1923 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1927 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Integer> getComparator() {
/* 1931 */       if (hasCharacteristics(4))
/* 1932 */         return null; 
/* 1933 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LongIteratorSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */     private PrimitiveIterator.OfLong it;
/*      */     
/*      */     private final int characteristics;
/*      */     
/*      */     private long est;
/*      */     
/*      */     private int batch;
/*      */ 
/*      */     
/*      */     public LongIteratorSpliterator(PrimitiveIterator.OfLong param1OfLong, long param1Long, int param1Int) {
/* 1956 */       this.it = param1OfLong;
/* 1957 */       this.est = param1Long;
/* 1958 */       this.characteristics = ((param1Int & 0x1000) == 0) ? (param1Int | 0x40 | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LongIteratorSpliterator(PrimitiveIterator.OfLong param1OfLong, int param1Int) {
/* 1973 */       this.it = param1OfLong;
/* 1974 */       this.est = Long.MAX_VALUE;
/* 1975 */       this.characteristics = param1Int & 0xFFFFBFBF;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfLong trySplit() {
/* 1980 */       PrimitiveIterator.OfLong ofLong = this.it;
/* 1981 */       long l = this.est;
/* 1982 */       if (l > 1L && ofLong.hasNext()) {
/* 1983 */         int i = this.batch + 1024;
/* 1984 */         if (i > l)
/* 1985 */           i = (int)l; 
/* 1986 */         if (i > 33554432)
/* 1987 */           i = 33554432; 
/* 1988 */         long[] arrayOfLong = new long[i];
/* 1989 */         byte b = 0; 
/* 1990 */         do { arrayOfLong[b] = ofLong.nextLong(); } while (++b < i && ofLong.hasNext());
/* 1991 */         this.batch = b;
/* 1992 */         if (this.est != Long.MAX_VALUE)
/* 1993 */           this.est -= b; 
/* 1994 */         return new Spliterators.LongArraySpliterator(arrayOfLong, 0, b, this.characteristics);
/*      */       } 
/* 1996 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/* 2001 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/* 2002 */       this.it.forEachRemaining(param1LongConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/* 2007 */       if (param1LongConsumer == null) throw new NullPointerException(); 
/* 2008 */       if (this.it.hasNext()) {
/* 2009 */         param1LongConsumer.accept(this.it.nextLong());
/* 2010 */         return true;
/*      */       } 
/* 2012 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 2017 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 2021 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Long> getComparator() {
/* 2025 */       if (hasCharacteristics(4))
/* 2026 */         return null; 
/* 2027 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class DoubleIteratorSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */     private PrimitiveIterator.OfDouble it;
/*      */     
/*      */     private final int characteristics;
/*      */     
/*      */     private long est;
/*      */     
/*      */     private int batch;
/*      */ 
/*      */     
/*      */     public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble param1OfDouble, long param1Long, int param1Int) {
/* 2050 */       this.it = param1OfDouble;
/* 2051 */       this.est = param1Long;
/* 2052 */       this.characteristics = ((param1Int & 0x1000) == 0) ? (param1Int | 0x40 | 0x4000) : param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble param1OfDouble, int param1Int) {
/* 2067 */       this.it = param1OfDouble;
/* 2068 */       this.est = Long.MAX_VALUE;
/* 2069 */       this.characteristics = param1Int & 0xFFFFBFBF;
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfDouble trySplit() {
/* 2074 */       PrimitiveIterator.OfDouble ofDouble = this.it;
/* 2075 */       long l = this.est;
/* 2076 */       if (l > 1L && ofDouble.hasNext()) {
/* 2077 */         int i = this.batch + 1024;
/* 2078 */         if (i > l)
/* 2079 */           i = (int)l; 
/* 2080 */         if (i > 33554432)
/* 2081 */           i = 33554432; 
/* 2082 */         double[] arrayOfDouble = new double[i];
/* 2083 */         byte b = 0; 
/* 2084 */         do { arrayOfDouble[b] = ofDouble.nextDouble(); } while (++b < i && ofDouble.hasNext());
/* 2085 */         this.batch = b;
/* 2086 */         if (this.est != Long.MAX_VALUE)
/* 2087 */           this.est -= b; 
/* 2088 */         return new Spliterators.DoubleArraySpliterator(arrayOfDouble, 0, b, this.characteristics);
/*      */       } 
/* 2090 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/* 2095 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/* 2096 */       this.it.forEachRemaining(param1DoubleConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/* 2101 */       if (param1DoubleConsumer == null) throw new NullPointerException(); 
/* 2102 */       if (this.it.hasNext()) {
/* 2103 */         param1DoubleConsumer.accept(this.it.nextDouble());
/* 2104 */         return true;
/*      */       } 
/* 2106 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 2111 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 2115 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Double> getComparator() {
/* 2119 */       if (hasCharacteristics(4))
/* 2120 */         return null; 
/* 2121 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Spliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */