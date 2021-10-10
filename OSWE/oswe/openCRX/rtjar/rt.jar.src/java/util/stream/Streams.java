/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Streams
/*     */ {
/*     */   private Streams() {
/*  47 */     throw new Error("no instances");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   static final Object NONE = new Object();
/*     */ 
/*     */   
/*     */   static final class RangeIntSpliterator
/*     */     implements Spliterator.OfInt
/*     */   {
/*     */     private int from;
/*     */     
/*     */     private final int upTo;
/*     */     
/*     */     private int last;
/*     */     
/*     */     private static final int BALANCED_SPLIT_THRESHOLD = 16777216;
/*     */     
/*     */     private static final int RIGHT_BALANCED_SPLIT_RATIO = 8;
/*     */     
/*     */     RangeIntSpliterator(int param1Int1, int param1Int2, boolean param1Boolean) {
/*  73 */       this(param1Int1, param1Int2, param1Boolean ? 1 : 0);
/*     */     }
/*     */     
/*     */     private RangeIntSpliterator(int param1Int1, int param1Int2, int param1Int3) {
/*  77 */       this.from = param1Int1;
/*  78 */       this.upTo = param1Int2;
/*  79 */       this.last = param1Int3;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/*  84 */       Objects.requireNonNull(param1IntConsumer);
/*     */       
/*  86 */       int i = this.from;
/*  87 */       if (i < this.upTo) {
/*  88 */         this.from++;
/*  89 */         param1IntConsumer.accept(i);
/*  90 */         return true;
/*     */       } 
/*  92 */       if (this.last > 0) {
/*  93 */         this.last = 0;
/*  94 */         param1IntConsumer.accept(i);
/*  95 */         return true;
/*     */       } 
/*  97 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 102 */       Objects.requireNonNull(param1IntConsumer);
/*     */       
/* 104 */       int i = this.from;
/* 105 */       int j = this.upTo;
/* 106 */       int k = this.last;
/* 107 */       this.from = this.upTo;
/* 108 */       this.last = 0;
/* 109 */       while (i < j) {
/* 110 */         param1IntConsumer.accept(i++);
/*     */       }
/* 112 */       if (k > 0)
/*     */       {
/* 114 */         param1IntConsumer.accept(i);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 121 */       return this.upTo - this.from + this.last;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 126 */       return 17749;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparator<? super Integer> getComparator() {
/* 133 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Spliterator.OfInt trySplit() {
/* 138 */       long l = estimateSize();
/* 139 */       return (l <= 1L) ? null : new RangeIntSpliterator(this.from, this.from += 
/*     */ 
/*     */           
/* 142 */           splitPoint(l), 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int splitPoint(long param1Long) {
/* 171 */       byte b = (param1Long < 16777216L) ? 2 : 8;
/*     */ 
/*     */ 
/*     */       
/* 175 */       return (int)(param1Long / b);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class RangeLongSpliterator
/*     */     implements Spliterator.OfLong
/*     */   {
/*     */     private long from;
/*     */ 
/*     */     
/*     */     private final long upTo;
/*     */     
/*     */     private int last;
/*     */     
/*     */     private static final long BALANCED_SPLIT_THRESHOLD = 16777216L;
/*     */     
/*     */     private static final long RIGHT_BALANCED_SPLIT_RATIO = 8L;
/*     */ 
/*     */     
/*     */     RangeLongSpliterator(long param1Long1, long param1Long2, boolean param1Boolean) {
/* 197 */       this(param1Long1, param1Long2, param1Boolean ? 1 : 0);
/*     */     }
/*     */     
/*     */     private RangeLongSpliterator(long param1Long1, long param1Long2, int param1Int) {
/* 201 */       assert param1Long2 - param1Long1 + param1Int > 0L;
/* 202 */       this.from = param1Long1;
/* 203 */       this.upTo = param1Long2;
/* 204 */       this.last = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/* 209 */       Objects.requireNonNull(param1LongConsumer);
/*     */       
/* 211 */       long l = this.from;
/* 212 */       if (l < this.upTo) {
/* 213 */         this.from++;
/* 214 */         param1LongConsumer.accept(l);
/* 215 */         return true;
/*     */       } 
/* 217 */       if (this.last > 0) {
/* 218 */         this.last = 0;
/* 219 */         param1LongConsumer.accept(l);
/* 220 */         return true;
/*     */       } 
/* 222 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */       //   4: pop
/*     */       //   5: aload_0
/*     */       //   6: getfield from : J
/*     */       //   9: lstore_2
/*     */       //   10: aload_0
/*     */       //   11: getfield upTo : J
/*     */       //   14: lstore #4
/*     */       //   16: aload_0
/*     */       //   17: getfield last : I
/*     */       //   20: istore #6
/*     */       //   22: aload_0
/*     */       //   23: aload_0
/*     */       //   24: getfield upTo : J
/*     */       //   27: putfield from : J
/*     */       //   30: aload_0
/*     */       //   31: iconst_0
/*     */       //   32: putfield last : I
/*     */       //   35: lload_2
/*     */       //   36: lload #4
/*     */       //   38: lcmp
/*     */       //   39: ifge -> 56
/*     */       //   42: aload_1
/*     */       //   43: lload_2
/*     */       //   44: dup2
/*     */       //   45: lconst_1
/*     */       //   46: ladd
/*     */       //   47: lstore_2
/*     */       //   48: invokeinterface accept : (J)V
/*     */       //   53: goto -> 35
/*     */       //   56: iload #6
/*     */       //   58: ifle -> 68
/*     */       //   61: aload_1
/*     */       //   62: lload_2
/*     */       //   63: invokeinterface accept : (J)V
/*     */       //   68: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #227	-> 0
/*     */       //   #229	-> 5
/*     */       //   #230	-> 10
/*     */       //   #231	-> 16
/*     */       //   #232	-> 22
/*     */       //   #233	-> 30
/*     */       //   #234	-> 35
/*     */       //   #235	-> 42
/*     */       //   #237	-> 56
/*     */       //   #239	-> 61
/*     */       //   #241	-> 68
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 245 */       return this.upTo - this.from + this.last;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 250 */       return 17749;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparator<? super Long> getComparator() {
/* 257 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Spliterator.OfLong trySplit() {
/* 262 */       long l = estimateSize();
/* 263 */       return (l <= 1L) ? null : new RangeLongSpliterator(this.from, this.from += 
/*     */ 
/*     */           
/* 266 */           splitPoint(l), 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long splitPoint(long param1Long) {
/* 295 */       long l = (param1Long < 16777216L) ? 2L : 8L;
/*     */       
/* 297 */       return param1Long / l;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class AbstractStreamBuilderImpl<T, S extends Spliterator<T>>
/*     */     implements Spliterator<T>
/*     */   {
/*     */     int count;
/*     */ 
/*     */ 
/*     */     
/*     */     private AbstractStreamBuilderImpl() {}
/*     */ 
/*     */     
/*     */     public S trySplit() {
/* 314 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 319 */       return (-this.count - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 324 */       return 17488;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class StreamBuilderImpl<T>
/*     */     extends AbstractStreamBuilderImpl<T, Spliterator<T>>
/*     */     implements Stream.Builder<T>
/*     */   {
/*     */     T first;
/*     */ 
/*     */ 
/*     */     
/*     */     SpinedBuffer<T> buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     StreamBuilderImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     StreamBuilderImpl(T param1T) {
/* 351 */       this.first = param1T;
/* 352 */       this.count = -2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(T param1T) {
/* 359 */       if (this.count == 0) {
/* 360 */         this.first = param1T;
/* 361 */         this.count++;
/*     */       }
/* 363 */       else if (this.count > 0) {
/* 364 */         if (this.buffer == null) {
/* 365 */           this.buffer = new SpinedBuffer<>();
/* 366 */           this.buffer.accept(this.first);
/* 367 */           this.count++;
/*     */         } 
/*     */         
/* 370 */         this.buffer.accept(param1T);
/*     */       } else {
/*     */         
/* 373 */         throw new IllegalStateException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Stream.Builder<T> add(T param1T) {
/* 378 */       accept(param1T);
/* 379 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<T> build() {
/* 384 */       int i = this.count;
/* 385 */       if (i >= 0) {
/*     */         
/* 387 */         this.count = -this.count - 1;
/*     */ 
/*     */         
/* 390 */         return (i < 2) ? StreamSupport.<T>stream(this, false) : StreamSupport.<T>stream(this.buffer.spliterator(), false);
/*     */       } 
/*     */       
/* 393 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/* 402 */       Objects.requireNonNull(param1Consumer);
/*     */       
/* 404 */       if (this.count == -2) {
/* 405 */         param1Consumer.accept(this.first);
/* 406 */         this.count = -1;
/* 407 */         return true;
/*     */       } 
/*     */       
/* 410 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/* 416 */       Objects.requireNonNull(param1Consumer);
/*     */       
/* 418 */       if (this.count == -2) {
/* 419 */         param1Consumer.accept(this.first);
/* 420 */         this.count = -1;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class IntStreamBuilderImpl
/*     */     extends AbstractStreamBuilderImpl<Integer, Spliterator.OfInt>
/*     */     implements IntStream.Builder, Spliterator.OfInt
/*     */   {
/*     */     int first;
/*     */ 
/*     */ 
/*     */     
/*     */     SpinedBuffer.OfInt buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     IntStreamBuilderImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     IntStreamBuilderImpl(int param1Int) {
/* 447 */       this.first = param1Int;
/* 448 */       this.count = -2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(int param1Int) {
/* 455 */       if (this.count == 0) {
/* 456 */         this.first = param1Int;
/* 457 */         this.count++;
/*     */       }
/* 459 */       else if (this.count > 0) {
/* 460 */         if (this.buffer == null) {
/* 461 */           this.buffer = new SpinedBuffer.OfInt();
/* 462 */           this.buffer.accept(this.first);
/* 463 */           this.count++;
/*     */         } 
/*     */         
/* 466 */         this.buffer.accept(param1Int);
/*     */       } else {
/*     */         
/* 469 */         throw new IllegalStateException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IntStream build() {
/* 475 */       int i = this.count;
/* 476 */       if (i >= 0) {
/*     */         
/* 478 */         this.count = -this.count - 1;
/*     */ 
/*     */         
/* 481 */         return (i < 2) ? StreamSupport.intStream(this, false) : StreamSupport.intStream(this.buffer.spliterator(), false);
/*     */       } 
/*     */       
/* 484 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/* 493 */       Objects.requireNonNull(param1IntConsumer);
/*     */       
/* 495 */       if (this.count == -2) {
/* 496 */         param1IntConsumer.accept(this.first);
/* 497 */         this.count = -1;
/* 498 */         return true;
/*     */       } 
/*     */       
/* 501 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 507 */       Objects.requireNonNull(param1IntConsumer);
/*     */       
/* 509 */       if (this.count == -2) {
/* 510 */         param1IntConsumer.accept(this.first);
/* 511 */         this.count = -1;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class LongStreamBuilderImpl
/*     */     extends AbstractStreamBuilderImpl<Long, Spliterator.OfLong>
/*     */     implements LongStream.Builder, Spliterator.OfLong
/*     */   {
/*     */     long first;
/*     */ 
/*     */ 
/*     */     
/*     */     SpinedBuffer.OfLong buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LongStreamBuilderImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LongStreamBuilderImpl(long param1Long) {
/* 538 */       this.first = param1Long;
/* 539 */       this.count = -2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(long param1Long) {
/* 546 */       if (this.count == 0) {
/* 547 */         this.first = param1Long;
/* 548 */         this.count++;
/*     */       }
/* 550 */       else if (this.count > 0) {
/* 551 */         if (this.buffer == null) {
/* 552 */           this.buffer = new SpinedBuffer.OfLong();
/* 553 */           this.buffer.accept(this.first);
/* 554 */           this.count++;
/*     */         } 
/*     */         
/* 557 */         this.buffer.accept(param1Long);
/*     */       } else {
/*     */         
/* 560 */         throw new IllegalStateException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public LongStream build() {
/* 566 */       int i = this.count;
/* 567 */       if (i >= 0) {
/*     */         
/* 569 */         this.count = -this.count - 1;
/*     */ 
/*     */         
/* 572 */         return (i < 2) ? StreamSupport.longStream(this, false) : StreamSupport.longStream(this.buffer.spliterator(), false);
/*     */       } 
/*     */       
/* 575 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/* 584 */       Objects.requireNonNull(param1LongConsumer);
/*     */       
/* 586 */       if (this.count == -2) {
/* 587 */         param1LongConsumer.accept(this.first);
/* 588 */         this.count = -1;
/* 589 */         return true;
/*     */       } 
/*     */       
/* 592 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/* 598 */       Objects.requireNonNull(param1LongConsumer);
/*     */       
/* 600 */       if (this.count == -2) {
/* 601 */         param1LongConsumer.accept(this.first);
/* 602 */         this.count = -1;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class DoubleStreamBuilderImpl
/*     */     extends AbstractStreamBuilderImpl<Double, Spliterator.OfDouble>
/*     */     implements DoubleStream.Builder, Spliterator.OfDouble
/*     */   {
/*     */     double first;
/*     */ 
/*     */ 
/*     */     
/*     */     SpinedBuffer.OfDouble buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DoubleStreamBuilderImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DoubleStreamBuilderImpl(double param1Double) {
/* 629 */       this.first = param1Double;
/* 630 */       this.count = -2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(double param1Double) {
/* 637 */       if (this.count == 0) {
/* 638 */         this.first = param1Double;
/* 639 */         this.count++;
/*     */       }
/* 641 */       else if (this.count > 0) {
/* 642 */         if (this.buffer == null) {
/* 643 */           this.buffer = new SpinedBuffer.OfDouble();
/* 644 */           this.buffer.accept(this.first);
/* 645 */           this.count++;
/*     */         } 
/*     */         
/* 648 */         this.buffer.accept(param1Double);
/*     */       } else {
/*     */         
/* 651 */         throw new IllegalStateException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleStream build() {
/* 657 */       int i = this.count;
/* 658 */       if (i >= 0) {
/*     */         
/* 660 */         this.count = -this.count - 1;
/*     */ 
/*     */         
/* 663 */         return (i < 2) ? StreamSupport.doubleStream(this, false) : StreamSupport.doubleStream(this.buffer.spliterator(), false);
/*     */       } 
/*     */       
/* 666 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/* 675 */       Objects.requireNonNull(param1DoubleConsumer);
/*     */       
/* 677 */       if (this.count == -2) {
/* 678 */         param1DoubleConsumer.accept(this.first);
/* 679 */         this.count = -1;
/* 680 */         return true;
/*     */       } 
/*     */       
/* 683 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/* 689 */       Objects.requireNonNull(param1DoubleConsumer);
/*     */       
/* 691 */       if (this.count == -2) {
/* 692 */         param1DoubleConsumer.accept(this.first);
/* 693 */         this.count = -1;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class ConcatSpliterator<T, T_SPLITR extends Spliterator<T>>
/*     */     implements Spliterator<T>
/*     */   {
/*     */     protected final T_SPLITR aSpliterator;
/*     */     protected final T_SPLITR bSpliterator;
/*     */     boolean beforeSplit;
/*     */     final boolean unsized;
/*     */     
/*     */     public ConcatSpliterator(T_SPLITR param1T_SPLITR1, T_SPLITR param1T_SPLITR2) {
/* 708 */       this.aSpliterator = param1T_SPLITR1;
/* 709 */       this.bSpliterator = param1T_SPLITR2;
/* 710 */       this.beforeSplit = true;
/*     */ 
/*     */       
/* 713 */       this.unsized = (param1T_SPLITR1.estimateSize() + param1T_SPLITR2.estimateSize() < 0L);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public T_SPLITR trySplit() {
/* 719 */       T_SPLITR t_SPLITR = (T_SPLITR)(this.beforeSplit ? (Object)this.aSpliterator : this.bSpliterator.trySplit());
/* 720 */       this.beforeSplit = false;
/* 721 */       return t_SPLITR;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/*     */       boolean bool;
/* 727 */       if (this.beforeSplit) {
/* 728 */         bool = this.aSpliterator.tryAdvance(param1Consumer);
/* 729 */         if (!bool) {
/* 730 */           this.beforeSplit = false;
/* 731 */           bool = this.bSpliterator.tryAdvance(param1Consumer);
/*     */         } 
/*     */       } else {
/*     */         
/* 735 */         bool = this.bSpliterator.tryAdvance(param1Consumer);
/* 736 */       }  return bool;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/* 741 */       if (this.beforeSplit)
/* 742 */         this.aSpliterator.forEachRemaining(param1Consumer); 
/* 743 */       this.bSpliterator.forEachRemaining(param1Consumer);
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 748 */       if (this.beforeSplit) {
/*     */ 
/*     */         
/* 751 */         long l = this.aSpliterator.estimateSize() + this.bSpliterator.estimateSize();
/* 752 */         return (l >= 0L) ? l : Long.MAX_VALUE;
/*     */       } 
/*     */       
/* 755 */       return this.bSpliterator.estimateSize();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 761 */       if (this.beforeSplit)
/*     */       {
/* 763 */         return this.aSpliterator.characteristics() & this.bSpliterator.characteristics() & ((0x5 | (this.unsized ? 16448 : 0)) ^ 0xFFFFFFFF);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 768 */       return this.bSpliterator.characteristics();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparator<? super T> getComparator() {
/* 774 */       if (this.beforeSplit)
/* 775 */         throw new IllegalStateException(); 
/* 776 */       return this.bSpliterator.getComparator();
/*     */     }
/*     */     
/*     */     static class OfRef<T> extends ConcatSpliterator<T, Spliterator<T>> {
/*     */       OfRef(Spliterator<T> param2Spliterator1, Spliterator<T> param2Spliterator2) {
/* 781 */         super(param2Spliterator1, param2Spliterator2);
/*     */       }
/*     */     }
/*     */     
/*     */     private static abstract class OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
/*     */       extends ConcatSpliterator<T, T_SPLITR>
/*     */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
/*     */       private OfPrimitive(T_SPLITR param2T_SPLITR1, T_SPLITR param2T_SPLITR2) {
/* 789 */         super(param2T_SPLITR1, param2T_SPLITR2);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean tryAdvance(T_CONS param2T_CONS) {
/*     */         boolean bool;
/* 795 */         if (this.beforeSplit) {
/* 796 */           bool = ((Spliterator.OfPrimitive)this.aSpliterator).tryAdvance(param2T_CONS);
/* 797 */           if (!bool) {
/* 798 */             this.beforeSplit = false;
/* 799 */             bool = ((Spliterator.OfPrimitive)this.bSpliterator).tryAdvance(param2T_CONS);
/*     */           } 
/*     */         } else {
/*     */           
/* 803 */           bool = ((Spliterator.OfPrimitive)this.bSpliterator).tryAdvance(param2T_CONS);
/* 804 */         }  return bool;
/*     */       }
/*     */ 
/*     */       
/*     */       public void forEachRemaining(T_CONS param2T_CONS) {
/* 809 */         if (this.beforeSplit)
/* 810 */           ((Spliterator.OfPrimitive)this.aSpliterator).forEachRemaining(param2T_CONS); 
/* 811 */         ((Spliterator.OfPrimitive)this.bSpliterator).forEachRemaining(param2T_CONS);
/*     */       }
/*     */     }
/*     */     
/*     */     static class OfInt
/*     */       extends OfPrimitive<Integer, IntConsumer, Spliterator.OfInt>
/*     */       implements Spliterator.OfInt {
/*     */       OfInt(Spliterator.OfInt param2OfInt1, Spliterator.OfInt param2OfInt2) {
/* 819 */         super(param2OfInt1, param2OfInt2);
/*     */       }
/*     */     }
/*     */     
/*     */     static class OfLong
/*     */       extends OfPrimitive<Long, LongConsumer, Spliterator.OfLong>
/*     */       implements Spliterator.OfLong {
/*     */       OfLong(Spliterator.OfLong param2OfLong1, Spliterator.OfLong param2OfLong2) {
/* 827 */         super(param2OfLong1, param2OfLong2);
/*     */       }
/*     */     }
/*     */     
/*     */     static class OfDouble
/*     */       extends OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble>
/*     */       implements Spliterator.OfDouble {
/*     */       OfDouble(Spliterator.OfDouble param2OfDouble1, Spliterator.OfDouble param2OfDouble2) {
/* 835 */         super(param2OfDouble1, param2OfDouble2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Runnable composeWithExceptions(final Runnable a, final Runnable b) {
/* 846 */     return new Runnable()
/*     */       {
/*     */         public void run() {
/*     */           try {
/* 850 */             a.run();
/*     */           }
/* 852 */           catch (Throwable throwable) {
/*     */             try {
/* 854 */               b.run();
/*     */             }
/* 856 */             catch (Throwable throwable1) {
/*     */               try {
/* 858 */                 throwable.addSuppressed(throwable1);
/* 859 */               } catch (Throwable throwable2) {}
/*     */             } 
/* 861 */             throw throwable;
/*     */           } 
/* 863 */           b.run();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Runnable composedClose(final BaseStream<?, ?> a, final BaseStream<?, ?> b) {
/* 875 */     return new Runnable()
/*     */       {
/*     */         public void run() {
/*     */           try {
/* 879 */             a.close();
/*     */           }
/* 881 */           catch (Throwable throwable) {
/*     */             try {
/* 883 */               b.close();
/*     */             }
/* 885 */             catch (Throwable throwable1) {
/*     */               try {
/* 887 */                 throwable.addSuppressed(throwable1);
/* 888 */               } catch (Throwable throwable2) {}
/*     */             } 
/* 890 */             throw throwable;
/*     */           } 
/* 892 */           b.close();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/Streams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */