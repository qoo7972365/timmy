/*      */ package java.util;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.security.AccessController;
/*      */ import java.util.concurrent.ForkJoinPool;
/*      */ import java.util.function.BinaryOperator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleBinaryOperator;
/*      */ import java.util.function.IntBinaryOperator;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntToDoubleFunction;
/*      */ import java.util.function.IntToLongFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ import java.util.function.LongBinaryOperator;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.DoubleStream;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Arrays
/*      */ {
/*      */   private static final int MIN_ARRAY_SORT_GRAN = 8192;
/*      */   private static final int INSERTIONSORT_THRESHOLD = 7;
/*      */   
/*      */   static final class NaturalOrder
/*      */     implements Comparator<Object>
/*      */   {
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/*  102 */       return ((Comparable<Object>)param1Object1).compareTo(param1Object2);
/*      */     }
/*  104 */     static final NaturalOrder INSTANCE = new NaturalOrder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rangeCheck(int paramInt1, int paramInt2, int paramInt3) {
/*  112 */     if (paramInt2 > paramInt3) {
/*  113 */       throw new IllegalArgumentException("fromIndex(" + paramInt2 + ") > toIndex(" + paramInt3 + ")");
/*      */     }
/*      */     
/*  116 */     if (paramInt2 < 0) {
/*  117 */       throw new ArrayIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  119 */     if (paramInt3 > paramInt1) {
/*  120 */       throw new ArrayIndexOutOfBoundsException(paramInt3);
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
/*      */   public static void sort(int[] paramArrayOfint) {
/*  144 */     DualPivotQuicksort.sort(paramArrayOfint, 0, paramArrayOfint.length - 1, (int[])null, 0, 0);
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
/*      */   public static void sort(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  168 */     rangeCheck(paramArrayOfint.length, paramInt1, paramInt2);
/*  169 */     DualPivotQuicksort.sort(paramArrayOfint, paramInt1, paramInt2 - 1, (int[])null, 0, 0);
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
/*      */   public static void sort(long[] paramArrayOflong) {
/*  184 */     DualPivotQuicksort.sort(paramArrayOflong, 0, paramArrayOflong.length - 1, (long[])null, 0, 0);
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
/*      */   public static void sort(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/*  208 */     rangeCheck(paramArrayOflong.length, paramInt1, paramInt2);
/*  209 */     DualPivotQuicksort.sort(paramArrayOflong, paramInt1, paramInt2 - 1, (long[])null, 0, 0);
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
/*      */   public static void sort(short[] paramArrayOfshort) {
/*  224 */     DualPivotQuicksort.sort(paramArrayOfshort, 0, paramArrayOfshort.length - 1, (short[])null, 0, 0);
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
/*      */   public static void sort(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/*  248 */     rangeCheck(paramArrayOfshort.length, paramInt1, paramInt2);
/*  249 */     DualPivotQuicksort.sort(paramArrayOfshort, paramInt1, paramInt2 - 1, (short[])null, 0, 0);
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
/*      */   public static void sort(char[] paramArrayOfchar) {
/*  264 */     DualPivotQuicksort.sort(paramArrayOfchar, 0, paramArrayOfchar.length - 1, (char[])null, 0, 0);
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
/*      */   public static void sort(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  288 */     rangeCheck(paramArrayOfchar.length, paramInt1, paramInt2);
/*  289 */     DualPivotQuicksort.sort(paramArrayOfchar, paramInt1, paramInt2 - 1, (char[])null, 0, 0);
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
/*      */   public static void sort(byte[] paramArrayOfbyte) {
/*  304 */     DualPivotQuicksort.sort(paramArrayOfbyte, 0, paramArrayOfbyte.length - 1);
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
/*      */   public static void sort(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  328 */     rangeCheck(paramArrayOfbyte.length, paramInt1, paramInt2);
/*  329 */     DualPivotQuicksort.sort(paramArrayOfbyte, paramInt1, paramInt2 - 1);
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
/*      */   public static void sort(float[] paramArrayOffloat) {
/*  352 */     DualPivotQuicksort.sort(paramArrayOffloat, 0, paramArrayOffloat.length - 1, (float[])null, 0, 0);
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
/*      */   public static void sort(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/*  384 */     rangeCheck(paramArrayOffloat.length, paramInt1, paramInt2);
/*  385 */     DualPivotQuicksort.sort(paramArrayOffloat, paramInt1, paramInt2 - 1, (float[])null, 0, 0);
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
/*      */   public static void sort(double[] paramArrayOfdouble) {
/*  408 */     DualPivotQuicksort.sort(paramArrayOfdouble, 0, paramArrayOfdouble.length - 1, (double[])null, 0, 0);
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
/*      */   public static void sort(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/*  440 */     rangeCheck(paramArrayOfdouble.length, paramInt1, paramInt2);
/*  441 */     DualPivotQuicksort.sort(paramArrayOfdouble, paramInt1, paramInt2 - 1, (double[])null, 0, 0);
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
/*      */   public static void parallelSort(byte[] paramArrayOfbyte) {
/*  463 */     int i = paramArrayOfbyte.length; int j;
/*  464 */     if (i <= 8192 || (
/*  465 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  466 */       DualPivotQuicksort.sort(paramArrayOfbyte, 0, i - 1);
/*      */     } else {
/*  468 */       int k; (new ArraysParallelSortHelpers.FJByte.Sorter(null, paramArrayOfbyte, new byte[i], 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  471 */         .invoke();
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
/*      */   public static void parallelSort(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  502 */     rangeCheck(paramArrayOfbyte.length, paramInt1, paramInt2);
/*  503 */     int i = paramInt2 - paramInt1; int j;
/*  504 */     if (i <= 8192 || (
/*  505 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  506 */       DualPivotQuicksort.sort(paramArrayOfbyte, paramInt1, paramInt2 - 1);
/*      */     } else {
/*  508 */       int k; (new ArraysParallelSortHelpers.FJByte.Sorter(null, paramArrayOfbyte, new byte[i], paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  511 */         .invoke();
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
/*      */   public static void parallelSort(char[] paramArrayOfchar) {
/*  533 */     int i = paramArrayOfchar.length; int j;
/*  534 */     if (i <= 8192 || (
/*  535 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  536 */       DualPivotQuicksort.sort(paramArrayOfchar, 0, i - 1, (char[])null, 0, 0);
/*      */     } else {
/*  538 */       int k; (new ArraysParallelSortHelpers.FJChar.Sorter(null, paramArrayOfchar, new char[i], 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  541 */         .invoke();
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
/*      */   public static void parallelSort(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  572 */     rangeCheck(paramArrayOfchar.length, paramInt1, paramInt2);
/*  573 */     int i = paramInt2 - paramInt1; int j;
/*  574 */     if (i <= 8192 || (
/*  575 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  576 */       DualPivotQuicksort.sort(paramArrayOfchar, paramInt1, paramInt2 - 1, (char[])null, 0, 0);
/*      */     } else {
/*  578 */       int k; (new ArraysParallelSortHelpers.FJChar.Sorter(null, paramArrayOfchar, new char[i], paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  581 */         .invoke();
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
/*      */   public static void parallelSort(short[] paramArrayOfshort) {
/*  603 */     int i = paramArrayOfshort.length; int j;
/*  604 */     if (i <= 8192 || (
/*  605 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  606 */       DualPivotQuicksort.sort(paramArrayOfshort, 0, i - 1, (short[])null, 0, 0);
/*      */     } else {
/*  608 */       int k; (new ArraysParallelSortHelpers.FJShort.Sorter(null, paramArrayOfshort, new short[i], 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  611 */         .invoke();
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
/*      */   public static void parallelSort(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/*  642 */     rangeCheck(paramArrayOfshort.length, paramInt1, paramInt2);
/*  643 */     int i = paramInt2 - paramInt1; int j;
/*  644 */     if (i <= 8192 || (
/*  645 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  646 */       DualPivotQuicksort.sort(paramArrayOfshort, paramInt1, paramInt2 - 1, (short[])null, 0, 0);
/*      */     } else {
/*  648 */       int k; (new ArraysParallelSortHelpers.FJShort.Sorter(null, paramArrayOfshort, new short[i], paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  651 */         .invoke();
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
/*      */   public static void parallelSort(int[] paramArrayOfint) {
/*  673 */     int i = paramArrayOfint.length; int j;
/*  674 */     if (i <= 8192 || (
/*  675 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  676 */       DualPivotQuicksort.sort(paramArrayOfint, 0, i - 1, (int[])null, 0, 0);
/*      */     } else {
/*  678 */       int k; (new ArraysParallelSortHelpers.FJInt.Sorter(null, paramArrayOfint, new int[i], 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  681 */         .invoke();
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
/*      */   public static void parallelSort(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  712 */     rangeCheck(paramArrayOfint.length, paramInt1, paramInt2);
/*  713 */     int i = paramInt2 - paramInt1; int j;
/*  714 */     if (i <= 8192 || (
/*  715 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  716 */       DualPivotQuicksort.sort(paramArrayOfint, paramInt1, paramInt2 - 1, (int[])null, 0, 0);
/*      */     } else {
/*  718 */       int k; (new ArraysParallelSortHelpers.FJInt.Sorter(null, paramArrayOfint, new int[i], paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  721 */         .invoke();
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
/*      */   public static void parallelSort(long[] paramArrayOflong) {
/*  743 */     int i = paramArrayOflong.length; int j;
/*  744 */     if (i <= 8192 || (
/*  745 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  746 */       DualPivotQuicksort.sort(paramArrayOflong, 0, i - 1, (long[])null, 0, 0);
/*      */     } else {
/*  748 */       int k; (new ArraysParallelSortHelpers.FJLong.Sorter(null, paramArrayOflong, new long[i], 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  751 */         .invoke();
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
/*      */   public static void parallelSort(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/*  782 */     rangeCheck(paramArrayOflong.length, paramInt1, paramInt2);
/*  783 */     int i = paramInt2 - paramInt1; int j;
/*  784 */     if (i <= 8192 || (
/*  785 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  786 */       DualPivotQuicksort.sort(paramArrayOflong, paramInt1, paramInt2 - 1, (long[])null, 0, 0);
/*      */     } else {
/*  788 */       int k; (new ArraysParallelSortHelpers.FJLong.Sorter(null, paramArrayOflong, new long[i], paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  791 */         .invoke();
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
/*      */   public static void parallelSort(float[] paramArrayOffloat) {
/*  821 */     int i = paramArrayOffloat.length; int j;
/*  822 */     if (i <= 8192 || (
/*  823 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  824 */       DualPivotQuicksort.sort(paramArrayOffloat, 0, i - 1, (float[])null, 0, 0);
/*      */     } else {
/*  826 */       int k; (new ArraysParallelSortHelpers.FJFloat.Sorter(null, paramArrayOffloat, new float[i], 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  829 */         .invoke();
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
/*      */   public static void parallelSort(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/*  868 */     rangeCheck(paramArrayOffloat.length, paramInt1, paramInt2);
/*  869 */     int i = paramInt2 - paramInt1; int j;
/*  870 */     if (i <= 8192 || (
/*  871 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  872 */       DualPivotQuicksort.sort(paramArrayOffloat, paramInt1, paramInt2 - 1, (float[])null, 0, 0);
/*      */     } else {
/*  874 */       int k; (new ArraysParallelSortHelpers.FJFloat.Sorter(null, paramArrayOffloat, new float[i], paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  877 */         .invoke();
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
/*      */   public static void parallelSort(double[] paramArrayOfdouble) {
/*  907 */     int i = paramArrayOfdouble.length; int j;
/*  908 */     if (i <= 8192 || (
/*  909 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  910 */       DualPivotQuicksort.sort(paramArrayOfdouble, 0, i - 1, (double[])null, 0, 0);
/*      */     } else {
/*  912 */       int k; (new ArraysParallelSortHelpers.FJDouble.Sorter(null, paramArrayOfdouble, new double[i], 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  915 */         .invoke();
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
/*      */   public static void parallelSort(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/*  954 */     rangeCheck(paramArrayOfdouble.length, paramInt1, paramInt2);
/*  955 */     int i = paramInt2 - paramInt1; int j;
/*  956 */     if (i <= 8192 || (
/*  957 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/*  958 */       DualPivotQuicksort.sort(paramArrayOfdouble, paramInt1, paramInt2 - 1, (double[])null, 0, 0);
/*      */     } else {
/*  960 */       int k; (new ArraysParallelSortHelpers.FJDouble.Sorter(null, paramArrayOfdouble, new double[i], paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k))
/*      */ 
/*      */         
/*  963 */         .invoke();
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
/*      */   public static <T extends Comparable<? super T>> void parallelSort(T[] paramArrayOfT) {
/* 1002 */     int i = paramArrayOfT.length; int j;
/* 1003 */     if (i <= 8192 || (
/* 1004 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/* 1005 */       TimSort.sort(paramArrayOfT, 0, i, NaturalOrder.INSTANCE, null, 0, 0);
/*      */     } else {
/* 1007 */       int k; (new ArraysParallelSortHelpers.FJObject.Sorter(null, (Object[])paramArrayOfT, 
/*      */           
/* 1009 */           (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i), 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k, NaturalOrder.INSTANCE))
/*      */         
/* 1011 */         .invoke();
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
/*      */   public static <T extends Comparable<? super T>> void parallelSort(T[] paramArrayOfT, int paramInt1, int paramInt2) {
/* 1060 */     rangeCheck(paramArrayOfT.length, paramInt1, paramInt2);
/* 1061 */     int i = paramInt2 - paramInt1; int j;
/* 1062 */     if (i <= 8192 || (
/* 1063 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/* 1064 */       TimSort.sort(paramArrayOfT, paramInt1, paramInt2, NaturalOrder.INSTANCE, null, 0, 0);
/*      */     } else {
/* 1066 */       int k; (new ArraysParallelSortHelpers.FJObject.Sorter(null, (Object[])paramArrayOfT, 
/*      */           
/* 1068 */           (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i), paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k, NaturalOrder.INSTANCE))
/*      */         
/* 1070 */         .invoke();
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
/*      */   public static <T> void parallelSort(T[] paramArrayOfT, Comparator<? super T> paramComparator) {
/* 1108 */     if (paramComparator == null)
/* 1109 */       paramComparator = NaturalOrder.INSTANCE; 
/* 1110 */     int i = paramArrayOfT.length; int j;
/* 1111 */     if (i <= 8192 || (
/* 1112 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/* 1113 */       TimSort.sort(paramArrayOfT, 0, i, paramComparator, null, 0, 0);
/*      */     } else {
/* 1115 */       int k; (new ArraysParallelSortHelpers.FJObject.Sorter(null, (Object[])paramArrayOfT, 
/*      */           
/* 1117 */           (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i), 0, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k, paramComparator))
/*      */         
/* 1119 */         .invoke();
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
/*      */   public static <T> void parallelSort(T[] paramArrayOfT, int paramInt1, int paramInt2, Comparator<? super T> paramComparator) {
/* 1168 */     rangeCheck(paramArrayOfT.length, paramInt1, paramInt2);
/* 1169 */     if (paramComparator == null)
/* 1170 */       paramComparator = NaturalOrder.INSTANCE; 
/* 1171 */     int i = paramInt2 - paramInt1; int j;
/* 1172 */     if (i <= 8192 || (
/* 1173 */       j = ForkJoinPool.getCommonPoolParallelism()) == 1) {
/* 1174 */       TimSort.sort(paramArrayOfT, paramInt1, paramInt2, paramComparator, null, 0, 0);
/*      */     } else {
/* 1176 */       int k; (new ArraysParallelSortHelpers.FJObject.Sorter(null, (Object[])paramArrayOfT, 
/*      */           
/* 1178 */           (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i), paramInt1, i, 0, ((k = i / (j << 2)) <= 8192) ? 8192 : k, paramComparator))
/*      */         
/* 1180 */         .invoke();
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
/*      */   static final class LegacyMergeSort
/*      */   {
/* 1195 */     private static final boolean userRequested = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("java.util.Arrays.useLegacyMergeSort")))
/*      */       
/* 1197 */       .booleanValue();
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
/*      */   public static void sort(Object[] paramArrayOfObject) {
/* 1243 */     if (LegacyMergeSort.userRequested) {
/* 1244 */       legacyMergeSort(paramArrayOfObject);
/*      */     } else {
/* 1246 */       ComparableTimSort.sort(paramArrayOfObject, 0, paramArrayOfObject.length, null, 0, 0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void legacyMergeSort(Object[] paramArrayOfObject) {
/* 1251 */     Object[] arrayOfObject = (Object[])paramArrayOfObject.clone();
/* 1252 */     mergeSort(arrayOfObject, paramArrayOfObject, 0, paramArrayOfObject.length, 0);
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
/*      */   public static void sort(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/* 1308 */     rangeCheck(paramArrayOfObject.length, paramInt1, paramInt2);
/* 1309 */     if (LegacyMergeSort.userRequested) {
/* 1310 */       legacyMergeSort(paramArrayOfObject, paramInt1, paramInt2);
/*      */     } else {
/* 1312 */       ComparableTimSort.sort(paramArrayOfObject, paramInt1, paramInt2, null, 0, 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void legacyMergeSort(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/* 1318 */     Object[] arrayOfObject = copyOfRange(paramArrayOfObject, paramInt1, paramInt2);
/* 1319 */     mergeSort(arrayOfObject, paramArrayOfObject, paramInt1, paramInt2, -paramInt1);
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
/*      */   private static void mergeSort(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, int paramInt1, int paramInt2, int paramInt3) {
/* 1343 */     int i = paramInt2 - paramInt1;
/*      */ 
/*      */     
/* 1346 */     if (i < 7) {
/* 1347 */       for (int i3 = paramInt1; i3 < paramInt2; i3++) {
/* 1348 */         for (int i4 = i3; i4 > paramInt1 && ((Comparable<Object>)paramArrayOfObject2[i4 - 1])
/* 1349 */           .compareTo(paramArrayOfObject2[i4]) > 0; i4--) {
/* 1350 */           swap(paramArrayOfObject2, i4, i4 - 1);
/*      */         }
/*      */       } 
/*      */       return;
/*      */     } 
/* 1355 */     int j = paramInt1;
/* 1356 */     int k = paramInt2;
/* 1357 */     paramInt1 += paramInt3;
/* 1358 */     paramInt2 += paramInt3;
/* 1359 */     int m = paramInt1 + paramInt2 >>> 1;
/* 1360 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, paramInt1, m, -paramInt3);
/* 1361 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, m, paramInt2, -paramInt3);
/*      */ 
/*      */ 
/*      */     
/* 1365 */     if (((Comparable<Object>)paramArrayOfObject1[m - 1]).compareTo(paramArrayOfObject1[m]) <= 0) {
/* 1366 */       System.arraycopy(paramArrayOfObject1, paramInt1, paramArrayOfObject2, j, i);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1371 */     for (int n = j, i1 = paramInt1, i2 = m; n < k; n++) {
/* 1372 */       if (i2 >= paramInt2 || (i1 < m && ((Comparable<Object>)paramArrayOfObject1[i1]).compareTo(paramArrayOfObject1[i2]) <= 0)) {
/* 1373 */         paramArrayOfObject2[n] = paramArrayOfObject1[i1++];
/*      */       } else {
/* 1375 */         paramArrayOfObject2[n] = paramArrayOfObject1[i2++];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void swap(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/* 1383 */     Object object = paramArrayOfObject[paramInt1];
/* 1384 */     paramArrayOfObject[paramInt1] = paramArrayOfObject[paramInt2];
/* 1385 */     paramArrayOfObject[paramInt2] = object;
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
/*      */   public static <T> void sort(T[] paramArrayOfT, Comparator<? super T> paramComparator) {
/* 1432 */     if (paramComparator == null) {
/* 1433 */       sort((Object[])paramArrayOfT);
/*      */     }
/* 1435 */     else if (LegacyMergeSort.userRequested) {
/* 1436 */       legacyMergeSort(paramArrayOfT, paramComparator);
/*      */     } else {
/* 1438 */       TimSort.sort(paramArrayOfT, 0, paramArrayOfT.length, paramComparator, null, 0, 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> void legacyMergeSort(T[] paramArrayOfT, Comparator<? super T> paramComparator) {
/* 1444 */     Object[] arrayOfObject = (Object[])paramArrayOfT.clone();
/* 1445 */     if (paramComparator == null) {
/* 1446 */       mergeSort(arrayOfObject, (Object[])paramArrayOfT, 0, paramArrayOfT.length, 0);
/*      */     } else {
/* 1448 */       mergeSort(arrayOfObject, (Object[])paramArrayOfT, 0, paramArrayOfT.length, 0, paramComparator);
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
/*      */   public static <T> void sort(T[] paramArrayOfT, int paramInt1, int paramInt2, Comparator<? super T> paramComparator) {
/* 1505 */     if (paramComparator == null) {
/* 1506 */       sort((Object[])paramArrayOfT, paramInt1, paramInt2);
/*      */     } else {
/* 1508 */       rangeCheck(paramArrayOfT.length, paramInt1, paramInt2);
/* 1509 */       if (LegacyMergeSort.userRequested) {
/* 1510 */         legacyMergeSort(paramArrayOfT, paramInt1, paramInt2, paramComparator);
/*      */       } else {
/* 1512 */         TimSort.sort(paramArrayOfT, paramInt1, paramInt2, paramComparator, null, 0, 0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> void legacyMergeSort(T[] paramArrayOfT, int paramInt1, int paramInt2, Comparator<? super T> paramComparator) {
/* 1519 */     Object[] arrayOfObject = copyOfRange((Object[])paramArrayOfT, paramInt1, paramInt2);
/* 1520 */     if (paramComparator == null) {
/* 1521 */       mergeSort(arrayOfObject, (Object[])paramArrayOfT, paramInt1, paramInt2, -paramInt1);
/*      */     } else {
/* 1523 */       mergeSort(arrayOfObject, (Object[])paramArrayOfT, paramInt1, paramInt2, -paramInt1, paramComparator);
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
/*      */   private static void mergeSort(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, int paramInt1, int paramInt2, int paramInt3, Comparator<Object> paramComparator) {
/* 1539 */     int i = paramInt2 - paramInt1;
/*      */ 
/*      */     
/* 1542 */     if (i < 7) {
/* 1543 */       for (int i3 = paramInt1; i3 < paramInt2; i3++) {
/* 1544 */         for (int i4 = i3; i4 > paramInt1 && paramComparator.compare(paramArrayOfObject2[i4 - 1], paramArrayOfObject2[i4]) > 0; i4--) {
/* 1545 */           swap(paramArrayOfObject2, i4, i4 - 1);
/*      */         }
/*      */       } 
/*      */       return;
/*      */     } 
/* 1550 */     int j = paramInt1;
/* 1551 */     int k = paramInt2;
/* 1552 */     paramInt1 += paramInt3;
/* 1553 */     paramInt2 += paramInt3;
/* 1554 */     int m = paramInt1 + paramInt2 >>> 1;
/* 1555 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, paramInt1, m, -paramInt3, paramComparator);
/* 1556 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, m, paramInt2, -paramInt3, paramComparator);
/*      */ 
/*      */ 
/*      */     
/* 1560 */     if (paramComparator.compare(paramArrayOfObject1[m - 1], paramArrayOfObject1[m]) <= 0) {
/* 1561 */       System.arraycopy(paramArrayOfObject1, paramInt1, paramArrayOfObject2, j, i);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1566 */     for (int n = j, i1 = paramInt1, i2 = m; n < k; n++) {
/* 1567 */       if (i2 >= paramInt2 || (i1 < m && paramComparator.compare(paramArrayOfObject1[i1], paramArrayOfObject1[i2]) <= 0)) {
/* 1568 */         paramArrayOfObject2[n] = paramArrayOfObject1[i1++];
/*      */       } else {
/* 1570 */         paramArrayOfObject2[n] = paramArrayOfObject1[i2++];
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
/*      */   public static <T> void parallelPrefix(T[] paramArrayOfT, BinaryOperator<T> paramBinaryOperator) {
/* 1592 */     Objects.requireNonNull(paramBinaryOperator);
/* 1593 */     if (paramArrayOfT.length > 0) {
/* 1594 */       (new ArrayPrefixHelpers.CumulateTask(null, paramBinaryOperator, (Object[])paramArrayOfT, 0, paramArrayOfT.length))
/* 1595 */         .invoke();
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
/*      */   public static <T> void parallelPrefix(T[] paramArrayOfT, int paramInt1, int paramInt2, BinaryOperator<T> paramBinaryOperator) {
/* 1616 */     Objects.requireNonNull(paramBinaryOperator);
/* 1617 */     rangeCheck(paramArrayOfT.length, paramInt1, paramInt2);
/* 1618 */     if (paramInt1 < paramInt2) {
/* 1619 */       (new ArrayPrefixHelpers.CumulateTask(null, paramBinaryOperator, (Object[])paramArrayOfT, paramInt1, paramInt2))
/* 1620 */         .invoke();
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
/*      */   public static void parallelPrefix(long[] paramArrayOflong, LongBinaryOperator paramLongBinaryOperator) {
/* 1638 */     Objects.requireNonNull(paramLongBinaryOperator);
/* 1639 */     if (paramArrayOflong.length > 0) {
/* 1640 */       (new ArrayPrefixHelpers.LongCumulateTask(null, paramLongBinaryOperator, paramArrayOflong, 0, paramArrayOflong.length))
/* 1641 */         .invoke();
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
/*      */   public static void parallelPrefix(long[] paramArrayOflong, int paramInt1, int paramInt2, LongBinaryOperator paramLongBinaryOperator) {
/* 1661 */     Objects.requireNonNull(paramLongBinaryOperator);
/* 1662 */     rangeCheck(paramArrayOflong.length, paramInt1, paramInt2);
/* 1663 */     if (paramInt1 < paramInt2) {
/* 1664 */       (new ArrayPrefixHelpers.LongCumulateTask(null, paramLongBinaryOperator, paramArrayOflong, paramInt1, paramInt2))
/* 1665 */         .invoke();
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
/*      */   public static void parallelPrefix(double[] paramArrayOfdouble, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 1686 */     Objects.requireNonNull(paramDoubleBinaryOperator);
/* 1687 */     if (paramArrayOfdouble.length > 0) {
/* 1688 */       (new ArrayPrefixHelpers.DoubleCumulateTask(null, paramDoubleBinaryOperator, paramArrayOfdouble, 0, paramArrayOfdouble.length))
/* 1689 */         .invoke();
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
/*      */   public static void parallelPrefix(double[] paramArrayOfdouble, int paramInt1, int paramInt2, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 1709 */     Objects.requireNonNull(paramDoubleBinaryOperator);
/* 1710 */     rangeCheck(paramArrayOfdouble.length, paramInt1, paramInt2);
/* 1711 */     if (paramInt1 < paramInt2) {
/* 1712 */       (new ArrayPrefixHelpers.DoubleCumulateTask(null, paramDoubleBinaryOperator, paramArrayOfdouble, paramInt1, paramInt2))
/* 1713 */         .invoke();
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
/*      */   public static void parallelPrefix(int[] paramArrayOfint, IntBinaryOperator paramIntBinaryOperator) {
/* 1731 */     Objects.requireNonNull(paramIntBinaryOperator);
/* 1732 */     if (paramArrayOfint.length > 0) {
/* 1733 */       (new ArrayPrefixHelpers.IntCumulateTask(null, paramIntBinaryOperator, paramArrayOfint, 0, paramArrayOfint.length))
/* 1734 */         .invoke();
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
/*      */   public static void parallelPrefix(int[] paramArrayOfint, int paramInt1, int paramInt2, IntBinaryOperator paramIntBinaryOperator) {
/* 1754 */     Objects.requireNonNull(paramIntBinaryOperator);
/* 1755 */     rangeCheck(paramArrayOfint.length, paramInt1, paramInt2);
/* 1756 */     if (paramInt1 < paramInt2) {
/* 1757 */       (new ArrayPrefixHelpers.IntCumulateTask(null, paramIntBinaryOperator, paramArrayOfint, paramInt1, paramInt2))
/* 1758 */         .invoke();
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
/*      */   public static int binarySearch(long[] paramArrayOflong, long paramLong) {
/* 1783 */     return binarySearch0(paramArrayOflong, 0, paramArrayOflong.length, paramLong);
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
/*      */   public static int binarySearch(long[] paramArrayOflong, int paramInt1, int paramInt2, long paramLong) {
/* 1820 */     rangeCheck(paramArrayOflong.length, paramInt1, paramInt2);
/* 1821 */     return binarySearch0(paramArrayOflong, paramInt1, paramInt2, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(long[] paramArrayOflong, int paramInt1, int paramInt2, long paramLong) {
/* 1827 */     int i = paramInt1;
/* 1828 */     int j = paramInt2 - 1;
/*      */     
/* 1830 */     while (i <= j) {
/* 1831 */       int k = i + j >>> 1;
/* 1832 */       long l = paramArrayOflong[k];
/*      */       
/* 1834 */       if (l < paramLong) {
/* 1835 */         i = k + 1; continue;
/* 1836 */       }  if (l > paramLong) {
/* 1837 */         j = k - 1; continue;
/*      */       } 
/* 1839 */       return k;
/*      */     } 
/* 1841 */     return -(i + 1);
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
/*      */   public static int binarySearch(int[] paramArrayOfint, int paramInt) {
/* 1864 */     return binarySearch0(paramArrayOfint, 0, paramArrayOfint.length, paramInt);
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
/*      */   public static int binarySearch(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
/* 1901 */     rangeCheck(paramArrayOfint.length, paramInt1, paramInt2);
/* 1902 */     return binarySearch0(paramArrayOfint, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
/* 1908 */     int i = paramInt1;
/* 1909 */     int j = paramInt2 - 1;
/*      */     
/* 1911 */     while (i <= j) {
/* 1912 */       int k = i + j >>> 1;
/* 1913 */       int m = paramArrayOfint[k];
/*      */       
/* 1915 */       if (m < paramInt3) {
/* 1916 */         i = k + 1; continue;
/* 1917 */       }  if (m > paramInt3) {
/* 1918 */         j = k - 1; continue;
/*      */       } 
/* 1920 */       return k;
/*      */     } 
/* 1922 */     return -(i + 1);
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
/*      */   public static int binarySearch(short[] paramArrayOfshort, short paramShort) {
/* 1945 */     return binarySearch0(paramArrayOfshort, 0, paramArrayOfshort.length, paramShort);
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
/*      */   public static int binarySearch(short[] paramArrayOfshort, int paramInt1, int paramInt2, short paramShort) {
/* 1982 */     rangeCheck(paramArrayOfshort.length, paramInt1, paramInt2);
/* 1983 */     return binarySearch0(paramArrayOfshort, paramInt1, paramInt2, paramShort);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(short[] paramArrayOfshort, int paramInt1, int paramInt2, short paramShort) {
/* 1989 */     int i = paramInt1;
/* 1990 */     int j = paramInt2 - 1;
/*      */     
/* 1992 */     while (i <= j) {
/* 1993 */       int k = i + j >>> 1;
/* 1994 */       short s = paramArrayOfshort[k];
/*      */       
/* 1996 */       if (s < paramShort) {
/* 1997 */         i = k + 1; continue;
/* 1998 */       }  if (s > paramShort) {
/* 1999 */         j = k - 1; continue;
/*      */       } 
/* 2001 */       return k;
/*      */     } 
/* 2003 */     return -(i + 1);
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
/*      */   public static int binarySearch(char[] paramArrayOfchar, char paramChar) {
/* 2026 */     return binarySearch0(paramArrayOfchar, 0, paramArrayOfchar.length, paramChar);
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
/*      */   public static int binarySearch(char[] paramArrayOfchar, int paramInt1, int paramInt2, char paramChar) {
/* 2063 */     rangeCheck(paramArrayOfchar.length, paramInt1, paramInt2);
/* 2064 */     return binarySearch0(paramArrayOfchar, paramInt1, paramInt2, paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(char[] paramArrayOfchar, int paramInt1, int paramInt2, char paramChar) {
/* 2070 */     int i = paramInt1;
/* 2071 */     int j = paramInt2 - 1;
/*      */     
/* 2073 */     while (i <= j) {
/* 2074 */       int k = i + j >>> 1;
/* 2075 */       char c = paramArrayOfchar[k];
/*      */       
/* 2077 */       if (c < paramChar) {
/* 2078 */         i = k + 1; continue;
/* 2079 */       }  if (c > paramChar) {
/* 2080 */         j = k - 1; continue;
/*      */       } 
/* 2082 */       return k;
/*      */     } 
/* 2084 */     return -(i + 1);
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
/*      */   public static int binarySearch(byte[] paramArrayOfbyte, byte paramByte) {
/* 2107 */     return binarySearch0(paramArrayOfbyte, 0, paramArrayOfbyte.length, paramByte);
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
/*      */   public static int binarySearch(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte) {
/* 2144 */     rangeCheck(paramArrayOfbyte.length, paramInt1, paramInt2);
/* 2145 */     return binarySearch0(paramArrayOfbyte, paramInt1, paramInt2, paramByte);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte) {
/* 2151 */     int i = paramInt1;
/* 2152 */     int j = paramInt2 - 1;
/*      */     
/* 2154 */     while (i <= j) {
/* 2155 */       int k = i + j >>> 1;
/* 2156 */       byte b = paramArrayOfbyte[k];
/*      */       
/* 2158 */       if (b < paramByte) {
/* 2159 */         i = k + 1; continue;
/* 2160 */       }  if (b > paramByte) {
/* 2161 */         j = k - 1; continue;
/*      */       } 
/* 2163 */       return k;
/*      */     } 
/* 2165 */     return -(i + 1);
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
/*      */   public static int binarySearch(double[] paramArrayOfdouble, double paramDouble) {
/* 2189 */     return binarySearch0(paramArrayOfdouble, 0, paramArrayOfdouble.length, paramDouble);
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
/*      */   public static int binarySearch(double[] paramArrayOfdouble, int paramInt1, int paramInt2, double paramDouble) {
/* 2227 */     rangeCheck(paramArrayOfdouble.length, paramInt1, paramInt2);
/* 2228 */     return binarySearch0(paramArrayOfdouble, paramInt1, paramInt2, paramDouble);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(double[] paramArrayOfdouble, int paramInt1, int paramInt2, double paramDouble) {
/* 2234 */     int i = paramInt1;
/* 2235 */     int j = paramInt2 - 1;
/*      */     
/* 2237 */     while (i <= j) {
/* 2238 */       int k = i + j >>> 1;
/* 2239 */       double d = paramArrayOfdouble[k];
/*      */       
/* 2241 */       if (d < paramDouble) {
/* 2242 */         i = k + 1; continue;
/* 2243 */       }  if (d > paramDouble) {
/* 2244 */         j = k - 1; continue;
/*      */       } 
/* 2246 */       long l1 = Double.doubleToLongBits(d);
/* 2247 */       long l2 = Double.doubleToLongBits(paramDouble);
/* 2248 */       if (l1 == l2)
/* 2249 */         return k; 
/* 2250 */       if (l1 < l2) {
/* 2251 */         i = k + 1; continue;
/*      */       } 
/* 2253 */       j = k - 1;
/*      */     } 
/*      */     
/* 2256 */     return -(i + 1);
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
/*      */   public static int binarySearch(float[] paramArrayOffloat, float paramFloat) {
/* 2280 */     return binarySearch0(paramArrayOffloat, 0, paramArrayOffloat.length, paramFloat);
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
/*      */   public static int binarySearch(float[] paramArrayOffloat, int paramInt1, int paramInt2, float paramFloat) {
/* 2318 */     rangeCheck(paramArrayOffloat.length, paramInt1, paramInt2);
/* 2319 */     return binarySearch0(paramArrayOffloat, paramInt1, paramInt2, paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(float[] paramArrayOffloat, int paramInt1, int paramInt2, float paramFloat) {
/* 2325 */     int i = paramInt1;
/* 2326 */     int j = paramInt2 - 1;
/*      */     
/* 2328 */     while (i <= j) {
/* 2329 */       int k = i + j >>> 1;
/* 2330 */       float f = paramArrayOffloat[k];
/*      */       
/* 2332 */       if (f < paramFloat) {
/* 2333 */         i = k + 1; continue;
/* 2334 */       }  if (f > paramFloat) {
/* 2335 */         j = k - 1; continue;
/*      */       } 
/* 2337 */       int m = Float.floatToIntBits(f);
/* 2338 */       int n = Float.floatToIntBits(paramFloat);
/* 2339 */       if (m == n)
/* 2340 */         return k; 
/* 2341 */       if (m < n) {
/* 2342 */         i = k + 1; continue;
/*      */       } 
/* 2344 */       j = k - 1;
/*      */     } 
/*      */     
/* 2347 */     return -(i + 1);
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
/*      */   public static int binarySearch(Object[] paramArrayOfObject, Object paramObject) {
/* 2379 */     return binarySearch0(paramArrayOfObject, 0, paramArrayOfObject.length, paramObject);
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
/*      */   public static int binarySearch(Object[] paramArrayOfObject, int paramInt1, int paramInt2, Object paramObject) {
/* 2424 */     rangeCheck(paramArrayOfObject.length, paramInt1, paramInt2);
/* 2425 */     return binarySearch0(paramArrayOfObject, paramInt1, paramInt2, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int binarySearch0(Object[] paramArrayOfObject, int paramInt1, int paramInt2, Object paramObject) {
/* 2431 */     int i = paramInt1;
/* 2432 */     int j = paramInt2 - 1;
/*      */     
/* 2434 */     while (i <= j) {
/* 2435 */       int k = i + j >>> 1;
/*      */       
/* 2437 */       Comparable<Object> comparable = (Comparable)paramArrayOfObject[k];
/*      */       
/* 2439 */       int m = comparable.compareTo(paramObject);
/*      */       
/* 2441 */       if (m < 0) {
/* 2442 */         i = k + 1; continue;
/* 2443 */       }  if (m > 0) {
/* 2444 */         j = k - 1; continue;
/*      */       } 
/* 2446 */       return k;
/*      */     } 
/* 2448 */     return -(i + 1);
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
/*      */   public static <T> int binarySearch(T[] paramArrayOfT, T paramT, Comparator<? super T> paramComparator) {
/* 2482 */     return binarySearch0(paramArrayOfT, 0, paramArrayOfT.length, paramT, paramComparator);
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
/*      */   public static <T> int binarySearch(T[] paramArrayOfT, int paramInt1, int paramInt2, T paramT, Comparator<? super T> paramComparator) {
/* 2529 */     rangeCheck(paramArrayOfT.length, paramInt1, paramInt2);
/* 2530 */     return binarySearch0(paramArrayOfT, paramInt1, paramInt2, paramT, paramComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> int binarySearch0(T[] paramArrayOfT, int paramInt1, int paramInt2, T paramT, Comparator<? super T> paramComparator) {
/* 2536 */     if (paramComparator == null) {
/* 2537 */       return binarySearch0((Object[])paramArrayOfT, paramInt1, paramInt2, paramT);
/*      */     }
/* 2539 */     int i = paramInt1;
/* 2540 */     int j = paramInt2 - 1;
/*      */     
/* 2542 */     while (i <= j) {
/* 2543 */       int k = i + j >>> 1;
/* 2544 */       T t = paramArrayOfT[k];
/* 2545 */       int m = paramComparator.compare(t, paramT);
/* 2546 */       if (m < 0) {
/* 2547 */         i = k + 1; continue;
/* 2548 */       }  if (m > 0) {
/* 2549 */         j = k - 1; continue;
/*      */       } 
/* 2551 */       return k;
/*      */     } 
/* 2553 */     return -(i + 1);
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
/*      */   public static boolean equals(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 2571 */     if (paramArrayOflong1 == paramArrayOflong2)
/* 2572 */       return true; 
/* 2573 */     if (paramArrayOflong1 == null || paramArrayOflong2 == null) {
/* 2574 */       return false;
/*      */     }
/* 2576 */     int i = paramArrayOflong1.length;
/* 2577 */     if (paramArrayOflong2.length != i) {
/* 2578 */       return false;
/*      */     }
/* 2580 */     for (byte b = 0; b < i; b++) {
/* 2581 */       if (paramArrayOflong1[b] != paramArrayOflong2[b])
/* 2582 */         return false; 
/*      */     } 
/* 2584 */     return true;
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
/*      */   public static boolean equals(int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 2600 */     if (paramArrayOfint1 == paramArrayOfint2)
/* 2601 */       return true; 
/* 2602 */     if (paramArrayOfint1 == null || paramArrayOfint2 == null) {
/* 2603 */       return false;
/*      */     }
/* 2605 */     int i = paramArrayOfint1.length;
/* 2606 */     if (paramArrayOfint2.length != i) {
/* 2607 */       return false;
/*      */     }
/* 2609 */     for (byte b = 0; b < i; b++) {
/* 2610 */       if (paramArrayOfint1[b] != paramArrayOfint2[b])
/* 2611 */         return false; 
/*      */     } 
/* 2613 */     return true;
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
/*      */   public static boolean equals(short[] paramArrayOfshort1, short[] paramArrayOfshort2) {
/* 2629 */     if (paramArrayOfshort1 == paramArrayOfshort2)
/* 2630 */       return true; 
/* 2631 */     if (paramArrayOfshort1 == null || paramArrayOfshort2 == null) {
/* 2632 */       return false;
/*      */     }
/* 2634 */     int i = paramArrayOfshort1.length;
/* 2635 */     if (paramArrayOfshort2.length != i) {
/* 2636 */       return false;
/*      */     }
/* 2638 */     for (byte b = 0; b < i; b++) {
/* 2639 */       if (paramArrayOfshort1[b] != paramArrayOfshort2[b])
/* 2640 */         return false; 
/*      */     } 
/* 2642 */     return true;
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
/*      */   public static boolean equals(char[] paramArrayOfchar1, char[] paramArrayOfchar2) {
/* 2658 */     if (paramArrayOfchar1 == paramArrayOfchar2)
/* 2659 */       return true; 
/* 2660 */     if (paramArrayOfchar1 == null || paramArrayOfchar2 == null) {
/* 2661 */       return false;
/*      */     }
/* 2663 */     int i = paramArrayOfchar1.length;
/* 2664 */     if (paramArrayOfchar2.length != i) {
/* 2665 */       return false;
/*      */     }
/* 2667 */     for (byte b = 0; b < i; b++) {
/* 2668 */       if (paramArrayOfchar1[b] != paramArrayOfchar2[b])
/* 2669 */         return false; 
/*      */     } 
/* 2671 */     return true;
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
/*      */   public static boolean equals(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 2687 */     if (paramArrayOfbyte1 == paramArrayOfbyte2)
/* 2688 */       return true; 
/* 2689 */     if (paramArrayOfbyte1 == null || paramArrayOfbyte2 == null) {
/* 2690 */       return false;
/*      */     }
/* 2692 */     int i = paramArrayOfbyte1.length;
/* 2693 */     if (paramArrayOfbyte2.length != i) {
/* 2694 */       return false;
/*      */     }
/* 2696 */     for (byte b = 0; b < i; b++) {
/* 2697 */       if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b])
/* 2698 */         return false; 
/*      */     } 
/* 2700 */     return true;
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
/*      */   public static boolean equals(boolean[] paramArrayOfboolean1, boolean[] paramArrayOfboolean2) {
/* 2716 */     if (paramArrayOfboolean1 == paramArrayOfboolean2)
/* 2717 */       return true; 
/* 2718 */     if (paramArrayOfboolean1 == null || paramArrayOfboolean2 == null) {
/* 2719 */       return false;
/*      */     }
/* 2721 */     int i = paramArrayOfboolean1.length;
/* 2722 */     if (paramArrayOfboolean2.length != i) {
/* 2723 */       return false;
/*      */     }
/* 2725 */     for (byte b = 0; b < i; b++) {
/* 2726 */       if (paramArrayOfboolean1[b] != paramArrayOfboolean2[b])
/* 2727 */         return false; 
/*      */     } 
/* 2729 */     return true;
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
/*      */   public static boolean equals(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 2751 */     if (paramArrayOfdouble1 == paramArrayOfdouble2)
/* 2752 */       return true; 
/* 2753 */     if (paramArrayOfdouble1 == null || paramArrayOfdouble2 == null) {
/* 2754 */       return false;
/*      */     }
/* 2756 */     int i = paramArrayOfdouble1.length;
/* 2757 */     if (paramArrayOfdouble2.length != i) {
/* 2758 */       return false;
/*      */     }
/* 2760 */     for (byte b = 0; b < i; b++) {
/* 2761 */       if (Double.doubleToLongBits(paramArrayOfdouble1[b]) != Double.doubleToLongBits(paramArrayOfdouble2[b]))
/* 2762 */         return false; 
/*      */     } 
/* 2764 */     return true;
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
/*      */   public static boolean equals(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/* 2786 */     if (paramArrayOffloat1 == paramArrayOffloat2)
/* 2787 */       return true; 
/* 2788 */     if (paramArrayOffloat1 == null || paramArrayOffloat2 == null) {
/* 2789 */       return false;
/*      */     }
/* 2791 */     int i = paramArrayOffloat1.length;
/* 2792 */     if (paramArrayOffloat2.length != i) {
/* 2793 */       return false;
/*      */     }
/* 2795 */     for (byte b = 0; b < i; b++) {
/* 2796 */       if (Float.floatToIntBits(paramArrayOffloat1[b]) != Float.floatToIntBits(paramArrayOffloat2[b]))
/* 2797 */         return false; 
/*      */     } 
/* 2799 */     return true;
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
/*      */   public static boolean equals(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) {
/* 2817 */     if (paramArrayOfObject1 == paramArrayOfObject2)
/* 2818 */       return true; 
/* 2819 */     if (paramArrayOfObject1 == null || paramArrayOfObject2 == null) {
/* 2820 */       return false;
/*      */     }
/* 2822 */     int i = paramArrayOfObject1.length;
/* 2823 */     if (paramArrayOfObject2.length != i) {
/* 2824 */       return false;
/*      */     }
/* 2826 */     for (byte b = 0; b < i; ) {
/* 2827 */       Object object1 = paramArrayOfObject1[b];
/* 2828 */       Object object2 = paramArrayOfObject2[b];
/* 2829 */       if ((object1 == null) ? (object2 == null) : object1.equals(object2)) { b++; continue; }
/* 2830 */        return false;
/*      */     } 
/*      */     
/* 2833 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(long[] paramArrayOflong, long paramLong) {
/*      */     byte b;
/*      */     int i;
/* 2846 */     for (b = 0, i = paramArrayOflong.length; b < i; b++) {
/* 2847 */       paramArrayOflong[b] = paramLong;
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
/*      */   public static void fill(long[] paramArrayOflong, int paramInt1, int paramInt2, long paramLong) {
/* 2868 */     rangeCheck(paramArrayOflong.length, paramInt1, paramInt2);
/* 2869 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 2870 */       paramArrayOflong[i] = paramLong;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(int[] paramArrayOfint, int paramInt) {
/*      */     byte b;
/*      */     int i;
/* 2881 */     for (b = 0, i = paramArrayOfint.length; b < i; b++) {
/* 2882 */       paramArrayOfint[b] = paramInt;
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
/*      */   public static void fill(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
/* 2903 */     rangeCheck(paramArrayOfint.length, paramInt1, paramInt2);
/* 2904 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 2905 */       paramArrayOfint[i] = paramInt3;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(short[] paramArrayOfshort, short paramShort) {
/*      */     byte b;
/*      */     int i;
/* 2916 */     for (b = 0, i = paramArrayOfshort.length; b < i; b++) {
/* 2917 */       paramArrayOfshort[b] = paramShort;
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
/*      */   public static void fill(short[] paramArrayOfshort, int paramInt1, int paramInt2, short paramShort) {
/* 2938 */     rangeCheck(paramArrayOfshort.length, paramInt1, paramInt2);
/* 2939 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 2940 */       paramArrayOfshort[i] = paramShort;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(char[] paramArrayOfchar, char paramChar) {
/*      */     byte b;
/*      */     int i;
/* 2951 */     for (b = 0, i = paramArrayOfchar.length; b < i; b++) {
/* 2952 */       paramArrayOfchar[b] = paramChar;
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
/*      */   public static void fill(char[] paramArrayOfchar, int paramInt1, int paramInt2, char paramChar) {
/* 2973 */     rangeCheck(paramArrayOfchar.length, paramInt1, paramInt2);
/* 2974 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 2975 */       paramArrayOfchar[i] = paramChar;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(byte[] paramArrayOfbyte, byte paramByte) {
/*      */     byte b;
/*      */     int i;
/* 2986 */     for (b = 0, i = paramArrayOfbyte.length; b < i; b++) {
/* 2987 */       paramArrayOfbyte[b] = paramByte;
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
/*      */   public static void fill(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte) {
/* 3008 */     rangeCheck(paramArrayOfbyte.length, paramInt1, paramInt2);
/* 3009 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 3010 */       paramArrayOfbyte[i] = paramByte;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(boolean[] paramArrayOfboolean, boolean paramBoolean) {
/*      */     byte b;
/*      */     int i;
/* 3021 */     for (b = 0, i = paramArrayOfboolean.length; b < i; b++) {
/* 3022 */       paramArrayOfboolean[b] = paramBoolean;
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
/*      */   public static void fill(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 3044 */     rangeCheck(paramArrayOfboolean.length, paramInt1, paramInt2);
/* 3045 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 3046 */       paramArrayOfboolean[i] = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(double[] paramArrayOfdouble, double paramDouble) {
/*      */     byte b;
/*      */     int i;
/* 3057 */     for (b = 0, i = paramArrayOfdouble.length; b < i; b++) {
/* 3058 */       paramArrayOfdouble[b] = paramDouble;
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
/*      */   public static void fill(double[] paramArrayOfdouble, int paramInt1, int paramInt2, double paramDouble) {
/* 3079 */     rangeCheck(paramArrayOfdouble.length, paramInt1, paramInt2);
/* 3080 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 3081 */       paramArrayOfdouble[i] = paramDouble;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(float[] paramArrayOffloat, float paramFloat) {
/*      */     byte b;
/*      */     int i;
/* 3092 */     for (b = 0, i = paramArrayOffloat.length; b < i; b++) {
/* 3093 */       paramArrayOffloat[b] = paramFloat;
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
/*      */   public static void fill(float[] paramArrayOffloat, int paramInt1, int paramInt2, float paramFloat) {
/* 3114 */     rangeCheck(paramArrayOffloat.length, paramInt1, paramInt2);
/* 3115 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 3116 */       paramArrayOffloat[i] = paramFloat;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(Object[] paramArrayOfObject, Object paramObject) {
/*      */     byte b;
/*      */     int i;
/* 3129 */     for (b = 0, i = paramArrayOfObject.length; b < i; b++) {
/* 3130 */       paramArrayOfObject[b] = paramObject;
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
/*      */   public static void fill(Object[] paramArrayOfObject, int paramInt1, int paramInt2, Object paramObject) {
/* 3153 */     rangeCheck(paramArrayOfObject.length, paramInt1, paramInt2);
/* 3154 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 3155 */       paramArrayOfObject[i] = paramObject;
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
/*      */   public static <T> T[] copyOf(T[] paramArrayOfT, int paramInt) {
/* 3181 */     return (T[])copyOf(paramArrayOfT, paramInt, (Class)paramArrayOfT.getClass());
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
/*      */   public static <T, U> T[] copyOf(U[] paramArrayOfU, int paramInt, Class<? extends T[]> paramClass) {
/* 3212 */     Object[] arrayOfObject = (paramClass == Object[].class) ? new Object[paramInt] : (Object[])Array.newInstance(paramClass.getComponentType(), paramInt);
/* 3213 */     System.arraycopy(paramArrayOfU, 0, arrayOfObject, 0, 
/* 3214 */         Math.min(paramArrayOfU.length, paramInt));
/* 3215 */     return (T[])arrayOfObject;
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
/*      */   public static byte[] copyOf(byte[] paramArrayOfbyte, int paramInt) {
/* 3236 */     byte[] arrayOfByte = new byte[paramInt];
/* 3237 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, 
/* 3238 */         Math.min(paramArrayOfbyte.length, paramInt));
/* 3239 */     return arrayOfByte;
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
/*      */   public static short[] copyOf(short[] paramArrayOfshort, int paramInt) {
/* 3260 */     short[] arrayOfShort = new short[paramInt];
/* 3261 */     System.arraycopy(paramArrayOfshort, 0, arrayOfShort, 0, 
/* 3262 */         Math.min(paramArrayOfshort.length, paramInt));
/* 3263 */     return arrayOfShort;
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
/*      */   public static int[] copyOf(int[] paramArrayOfint, int paramInt) {
/* 3284 */     int[] arrayOfInt = new int[paramInt];
/* 3285 */     System.arraycopy(paramArrayOfint, 0, arrayOfInt, 0, 
/* 3286 */         Math.min(paramArrayOfint.length, paramInt));
/* 3287 */     return arrayOfInt;
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
/*      */   public static long[] copyOf(long[] paramArrayOflong, int paramInt) {
/* 3308 */     long[] arrayOfLong = new long[paramInt];
/* 3309 */     System.arraycopy(paramArrayOflong, 0, arrayOfLong, 0, 
/* 3310 */         Math.min(paramArrayOflong.length, paramInt));
/* 3311 */     return arrayOfLong;
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
/*      */   public static char[] copyOf(char[] paramArrayOfchar, int paramInt) {
/* 3332 */     char[] arrayOfChar = new char[paramInt];
/* 3333 */     System.arraycopy(paramArrayOfchar, 0, arrayOfChar, 0, 
/* 3334 */         Math.min(paramArrayOfchar.length, paramInt));
/* 3335 */     return arrayOfChar;
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
/*      */   public static float[] copyOf(float[] paramArrayOffloat, int paramInt) {
/* 3356 */     float[] arrayOfFloat = new float[paramInt];
/* 3357 */     System.arraycopy(paramArrayOffloat, 0, arrayOfFloat, 0, 
/* 3358 */         Math.min(paramArrayOffloat.length, paramInt));
/* 3359 */     return arrayOfFloat;
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
/*      */   public static double[] copyOf(double[] paramArrayOfdouble, int paramInt) {
/* 3380 */     double[] arrayOfDouble = new double[paramInt];
/* 3381 */     System.arraycopy(paramArrayOfdouble, 0, arrayOfDouble, 0, 
/* 3382 */         Math.min(paramArrayOfdouble.length, paramInt));
/* 3383 */     return arrayOfDouble;
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
/*      */   public static boolean[] copyOf(boolean[] paramArrayOfboolean, int paramInt) {
/* 3404 */     boolean[] arrayOfBoolean = new boolean[paramInt];
/* 3405 */     System.arraycopy(paramArrayOfboolean, 0, arrayOfBoolean, 0, 
/* 3406 */         Math.min(paramArrayOfboolean.length, paramInt));
/* 3407 */     return arrayOfBoolean;
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
/*      */   public static <T> T[] copyOfRange(T[] paramArrayOfT, int paramInt1, int paramInt2) {
/* 3441 */     return copyOfRange(paramArrayOfT, paramInt1, paramInt2, (Class)paramArrayOfT.getClass());
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
/*      */   public static <T, U> T[] copyOfRange(U[] paramArrayOfU, int paramInt1, int paramInt2, Class<? extends T[]> paramClass) {
/* 3478 */     int i = paramInt2 - paramInt1;
/* 3479 */     if (i < 0) {
/* 3480 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2);
/*      */     }
/*      */ 
/*      */     
/* 3484 */     Object[] arrayOfObject = (paramClass == Object[].class) ? new Object[i] : (Object[])Array.newInstance(paramClass.getComponentType(), i);
/* 3485 */     System.arraycopy(paramArrayOfU, paramInt1, arrayOfObject, 0, 
/* 3486 */         Math.min(paramArrayOfU.length - paramInt1, i));
/* 3487 */     return (T[])arrayOfObject;
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
/*      */   public static byte[] copyOfRange(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 3517 */     int i = paramInt2 - paramInt1;
/* 3518 */     if (i < 0)
/* 3519 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3520 */     byte[] arrayOfByte = new byte[i];
/* 3521 */     System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, 
/* 3522 */         Math.min(paramArrayOfbyte.length - paramInt1, i));
/* 3523 */     return arrayOfByte;
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
/*      */   public static short[] copyOfRange(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 3553 */     int i = paramInt2 - paramInt1;
/* 3554 */     if (i < 0)
/* 3555 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3556 */     short[] arrayOfShort = new short[i];
/* 3557 */     System.arraycopy(paramArrayOfshort, paramInt1, arrayOfShort, 0, 
/* 3558 */         Math.min(paramArrayOfshort.length - paramInt1, i));
/* 3559 */     return arrayOfShort;
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
/*      */   public static int[] copyOfRange(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 3589 */     int i = paramInt2 - paramInt1;
/* 3590 */     if (i < 0)
/* 3591 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3592 */     int[] arrayOfInt = new int[i];
/* 3593 */     System.arraycopy(paramArrayOfint, paramInt1, arrayOfInt, 0, 
/* 3594 */         Math.min(paramArrayOfint.length - paramInt1, i));
/* 3595 */     return arrayOfInt;
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
/*      */   public static long[] copyOfRange(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 3625 */     int i = paramInt2 - paramInt1;
/* 3626 */     if (i < 0)
/* 3627 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3628 */     long[] arrayOfLong = new long[i];
/* 3629 */     System.arraycopy(paramArrayOflong, paramInt1, arrayOfLong, 0, 
/* 3630 */         Math.min(paramArrayOflong.length - paramInt1, i));
/* 3631 */     return arrayOfLong;
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
/*      */   public static char[] copyOfRange(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 3661 */     int i = paramInt2 - paramInt1;
/* 3662 */     if (i < 0)
/* 3663 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3664 */     char[] arrayOfChar = new char[i];
/* 3665 */     System.arraycopy(paramArrayOfchar, paramInt1, arrayOfChar, 0, 
/* 3666 */         Math.min(paramArrayOfchar.length - paramInt1, i));
/* 3667 */     return arrayOfChar;
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
/*      */   public static float[] copyOfRange(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 3697 */     int i = paramInt2 - paramInt1;
/* 3698 */     if (i < 0)
/* 3699 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3700 */     float[] arrayOfFloat = new float[i];
/* 3701 */     System.arraycopy(paramArrayOffloat, paramInt1, arrayOfFloat, 0, 
/* 3702 */         Math.min(paramArrayOffloat.length - paramInt1, i));
/* 3703 */     return arrayOfFloat;
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
/*      */   public static double[] copyOfRange(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 3733 */     int i = paramInt2 - paramInt1;
/* 3734 */     if (i < 0)
/* 3735 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3736 */     double[] arrayOfDouble = new double[i];
/* 3737 */     System.arraycopy(paramArrayOfdouble, paramInt1, arrayOfDouble, 0, 
/* 3738 */         Math.min(paramArrayOfdouble.length - paramInt1, i));
/* 3739 */     return arrayOfDouble;
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
/*      */   public static boolean[] copyOfRange(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
/* 3769 */     int i = paramInt2 - paramInt1;
/* 3770 */     if (i < 0)
/* 3771 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2); 
/* 3772 */     boolean[] arrayOfBoolean = new boolean[i];
/* 3773 */     System.arraycopy(paramArrayOfboolean, paramInt1, arrayOfBoolean, 0, 
/* 3774 */         Math.min(paramArrayOfboolean.length - paramInt1, i));
/* 3775 */     return arrayOfBoolean;
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
/*      */   @SafeVarargs
/*      */   public static <T> List<T> asList(T... paramVarArgs) {
/* 3800 */     return new ArrayList<>(paramVarArgs);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ArrayList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -2764017481108945198L;
/*      */     
/*      */     private final E[] a;
/*      */     
/*      */     ArrayList(E[] param1ArrayOfE) {
/* 3813 */       this.a = (E[])Objects.<Object[]>requireNonNull((Object[])param1ArrayOfE);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3818 */       return this.a.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object[] toArray() {
/* 3823 */       return (Object[])this.a.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 3829 */       int i = size();
/* 3830 */       if (param1ArrayOfT.length < i)
/* 3831 */         return Arrays.copyOf(this.a, i, (Class)param1ArrayOfT
/* 3832 */             .getClass()); 
/* 3833 */       System.arraycopy(this.a, 0, param1ArrayOfT, 0, i);
/* 3834 */       if (param1ArrayOfT.length > i)
/* 3835 */         param1ArrayOfT[i] = null; 
/* 3836 */       return param1ArrayOfT;
/*      */     }
/*      */ 
/*      */     
/*      */     public E get(int param1Int) {
/* 3841 */       return this.a[param1Int];
/*      */     }
/*      */ 
/*      */     
/*      */     public E set(int param1Int, E param1E) {
/* 3846 */       E e = this.a[param1Int];
/* 3847 */       this.a[param1Int] = param1E;
/* 3848 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object param1Object) {
/* 3853 */       E[] arrayOfE = this.a;
/* 3854 */       if (param1Object == null)
/* 3855 */       { for (byte b = 0; b < arrayOfE.length; b++) {
/* 3856 */           if (arrayOfE[b] == null)
/* 3857 */             return b; 
/*      */         }  }
/* 3859 */       else { for (byte b = 0; b < arrayOfE.length; b++) {
/* 3860 */           if (param1Object.equals(arrayOfE[b]))
/* 3861 */             return b; 
/*      */         }  }
/* 3863 */        return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 3868 */       return (indexOf(param1Object) != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 3873 */       return Spliterators.spliterator((Object[])this.a, 16);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 3878 */       Objects.requireNonNull(param1Consumer);
/* 3879 */       for (E e : this.a) {
/* 3880 */         param1Consumer.accept(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 3886 */       Objects.requireNonNull(param1UnaryOperator);
/* 3887 */       E[] arrayOfE = this.a;
/* 3888 */       for (byte b = 0; b < arrayOfE.length; b++) {
/* 3889 */         arrayOfE[b] = param1UnaryOperator.apply(arrayOfE[b]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 3895 */       Arrays.sort(this.a, param1Comparator);
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
/*      */   public static int hashCode(long[] paramArrayOflong) {
/* 3916 */     if (paramArrayOflong == null) {
/* 3917 */       return 0;
/*      */     }
/* 3919 */     int i = 1;
/* 3920 */     for (long l : paramArrayOflong) {
/* 3921 */       int j = (int)(l ^ l >>> 32L);
/* 3922 */       i = 31 * i + j;
/*      */     } 
/*      */     
/* 3925 */     return i;
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
/*      */   public static int hashCode(int[] paramArrayOfint) {
/* 3945 */     if (paramArrayOfint == null) {
/* 3946 */       return 0;
/*      */     }
/* 3948 */     int i = 1;
/* 3949 */     for (int j : paramArrayOfint) {
/* 3950 */       i = 31 * i + j;
/*      */     }
/* 3952 */     return i;
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
/*      */   public static int hashCode(short[] paramArrayOfshort) {
/* 3972 */     if (paramArrayOfshort == null) {
/* 3973 */       return 0;
/*      */     }
/* 3975 */     int i = 1;
/* 3976 */     for (short s : paramArrayOfshort) {
/* 3977 */       i = 31 * i + s;
/*      */     }
/* 3979 */     return i;
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
/*      */   public static int hashCode(char[] paramArrayOfchar) {
/* 3999 */     if (paramArrayOfchar == null) {
/* 4000 */       return 0;
/*      */     }
/* 4002 */     int i = 1;
/* 4003 */     for (char c : paramArrayOfchar) {
/* 4004 */       i = 31 * i + c;
/*      */     }
/* 4006 */     return i;
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
/*      */   public static int hashCode(byte[] paramArrayOfbyte) {
/* 4026 */     if (paramArrayOfbyte == null) {
/* 4027 */       return 0;
/*      */     }
/* 4029 */     int i = 1;
/* 4030 */     for (byte b : paramArrayOfbyte) {
/* 4031 */       i = 31 * i + b;
/*      */     }
/* 4033 */     return i;
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
/*      */   public static int hashCode(boolean[] paramArrayOfboolean) {
/* 4053 */     if (paramArrayOfboolean == null) {
/* 4054 */       return 0;
/*      */     }
/* 4056 */     int i = 1;
/* 4057 */     for (boolean bool : paramArrayOfboolean) {
/* 4058 */       i = 31 * i + (bool ? 1231 : 1237);
/*      */     }
/* 4060 */     return i;
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
/*      */   public static int hashCode(float[] paramArrayOffloat) {
/* 4080 */     if (paramArrayOffloat == null) {
/* 4081 */       return 0;
/*      */     }
/* 4083 */     int i = 1;
/* 4084 */     for (float f : paramArrayOffloat) {
/* 4085 */       i = 31 * i + Float.floatToIntBits(f);
/*      */     }
/* 4087 */     return i;
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
/*      */   public static int hashCode(double[] paramArrayOfdouble) {
/* 4107 */     if (paramArrayOfdouble == null) {
/* 4108 */       return 0;
/*      */     }
/* 4110 */     int i = 1;
/* 4111 */     for (double d : paramArrayOfdouble) {
/* 4112 */       long l = Double.doubleToLongBits(d);
/* 4113 */       i = 31 * i + (int)(l ^ l >>> 32L);
/*      */     } 
/* 4115 */     return i;
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
/*      */   public static int hashCode(Object[] paramArrayOfObject) {
/* 4140 */     if (paramArrayOfObject == null) {
/* 4141 */       return 0;
/*      */     }
/* 4143 */     int i = 1;
/*      */     
/* 4145 */     for (Object object : paramArrayOfObject) {
/* 4146 */       i = 31 * i + ((object == null) ? 0 : object.hashCode());
/*      */     }
/* 4148 */     return i;
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
/*      */   public static int deepHashCode(Object[] paramArrayOfObject) {
/* 4181 */     if (paramArrayOfObject == null) {
/* 4182 */       return 0;
/*      */     }
/* 4184 */     int i = 1;
/*      */     
/* 4186 */     for (Object object : paramArrayOfObject) {
/* 4187 */       int j = 0;
/* 4188 */       if (object instanceof Object[]) {
/* 4189 */         j = deepHashCode((Object[])object);
/* 4190 */       } else if (object instanceof byte[]) {
/* 4191 */         j = hashCode((byte[])object);
/* 4192 */       } else if (object instanceof short[]) {
/* 4193 */         j = hashCode((short[])object);
/* 4194 */       } else if (object instanceof int[]) {
/* 4195 */         j = hashCode((int[])object);
/* 4196 */       } else if (object instanceof long[]) {
/* 4197 */         j = hashCode((long[])object);
/* 4198 */       } else if (object instanceof char[]) {
/* 4199 */         j = hashCode((char[])object);
/* 4200 */       } else if (object instanceof float[]) {
/* 4201 */         j = hashCode((float[])object);
/* 4202 */       } else if (object instanceof double[]) {
/* 4203 */         j = hashCode((double[])object);
/* 4204 */       } else if (object instanceof boolean[]) {
/* 4205 */         j = hashCode((boolean[])object);
/* 4206 */       } else if (object != null) {
/* 4207 */         j = object.hashCode();
/*      */       } 
/* 4209 */       i = 31 * i + j;
/*      */     } 
/*      */     
/* 4212 */     return i;
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
/*      */   public static boolean deepEquals(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) {
/* 4251 */     if (paramArrayOfObject1 == paramArrayOfObject2)
/* 4252 */       return true; 
/* 4253 */     if (paramArrayOfObject1 == null || paramArrayOfObject2 == null)
/* 4254 */       return false; 
/* 4255 */     int i = paramArrayOfObject1.length;
/* 4256 */     if (paramArrayOfObject2.length != i) {
/* 4257 */       return false;
/*      */     }
/* 4259 */     for (byte b = 0; b < i; b++) {
/* 4260 */       Object object1 = paramArrayOfObject1[b];
/* 4261 */       Object object2 = paramArrayOfObject2[b];
/*      */       
/* 4263 */       if (object1 != object2) {
/*      */         
/* 4265 */         if (object1 == null) {
/* 4266 */           return false;
/*      */         }
/*      */         
/* 4269 */         boolean bool = deepEquals0(object1, object2);
/*      */         
/* 4271 */         if (!bool)
/* 4272 */           return false; 
/*      */       } 
/* 4274 */     }  return true;
/*      */   }
/*      */   static boolean deepEquals0(Object paramObject1, Object paramObject2) {
/*      */     boolean bool;
/* 4278 */     assert paramObject1 != null;
/*      */     
/* 4280 */     if (paramObject1 instanceof Object[] && paramObject2 instanceof Object[]) {
/* 4281 */       bool = deepEquals((Object[])paramObject1, (Object[])paramObject2);
/* 4282 */     } else if (paramObject1 instanceof byte[] && paramObject2 instanceof byte[]) {
/* 4283 */       bool = equals((byte[])paramObject1, (byte[])paramObject2);
/* 4284 */     } else if (paramObject1 instanceof short[] && paramObject2 instanceof short[]) {
/* 4285 */       bool = equals((short[])paramObject1, (short[])paramObject2);
/* 4286 */     } else if (paramObject1 instanceof int[] && paramObject2 instanceof int[]) {
/* 4287 */       bool = equals((int[])paramObject1, (int[])paramObject2);
/* 4288 */     } else if (paramObject1 instanceof long[] && paramObject2 instanceof long[]) {
/* 4289 */       bool = equals((long[])paramObject1, (long[])paramObject2);
/* 4290 */     } else if (paramObject1 instanceof char[] && paramObject2 instanceof char[]) {
/* 4291 */       bool = equals((char[])paramObject1, (char[])paramObject2);
/* 4292 */     } else if (paramObject1 instanceof float[] && paramObject2 instanceof float[]) {
/* 4293 */       bool = equals((float[])paramObject1, (float[])paramObject2);
/* 4294 */     } else if (paramObject1 instanceof double[] && paramObject2 instanceof double[]) {
/* 4295 */       bool = equals((double[])paramObject1, (double[])paramObject2);
/* 4296 */     } else if (paramObject1 instanceof boolean[] && paramObject2 instanceof boolean[]) {
/* 4297 */       bool = equals((boolean[])paramObject1, (boolean[])paramObject2);
/*      */     } else {
/* 4299 */       bool = paramObject1.equals(paramObject2);
/* 4300 */     }  return bool;
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
/*      */   public static String toString(long[] paramArrayOflong) {
/* 4317 */     if (paramArrayOflong == null)
/* 4318 */       return "null"; 
/* 4319 */     int i = paramArrayOflong.length - 1;
/* 4320 */     if (i == -1) {
/* 4321 */       return "[]";
/*      */     }
/* 4323 */     StringBuilder stringBuilder = new StringBuilder();
/* 4324 */     stringBuilder.append('[');
/* 4325 */     for (int j = 0;; j++) {
/* 4326 */       stringBuilder.append(paramArrayOflong[j]);
/* 4327 */       if (j == i)
/* 4328 */         return stringBuilder.append(']').toString(); 
/* 4329 */       stringBuilder.append(", ");
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
/*      */   public static String toString(int[] paramArrayOfint) {
/* 4347 */     if (paramArrayOfint == null)
/* 4348 */       return "null"; 
/* 4349 */     int i = paramArrayOfint.length - 1;
/* 4350 */     if (i == -1) {
/* 4351 */       return "[]";
/*      */     }
/* 4353 */     StringBuilder stringBuilder = new StringBuilder();
/* 4354 */     stringBuilder.append('[');
/* 4355 */     for (int j = 0;; j++) {
/* 4356 */       stringBuilder.append(paramArrayOfint[j]);
/* 4357 */       if (j == i)
/* 4358 */         return stringBuilder.append(']').toString(); 
/* 4359 */       stringBuilder.append(", ");
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
/*      */   public static String toString(short[] paramArrayOfshort) {
/* 4377 */     if (paramArrayOfshort == null)
/* 4378 */       return "null"; 
/* 4379 */     int i = paramArrayOfshort.length - 1;
/* 4380 */     if (i == -1) {
/* 4381 */       return "[]";
/*      */     }
/* 4383 */     StringBuilder stringBuilder = new StringBuilder();
/* 4384 */     stringBuilder.append('[');
/* 4385 */     for (int j = 0;; j++) {
/* 4386 */       stringBuilder.append(paramArrayOfshort[j]);
/* 4387 */       if (j == i)
/* 4388 */         return stringBuilder.append(']').toString(); 
/* 4389 */       stringBuilder.append(", ");
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
/*      */   public static String toString(char[] paramArrayOfchar) {
/* 4407 */     if (paramArrayOfchar == null)
/* 4408 */       return "null"; 
/* 4409 */     int i = paramArrayOfchar.length - 1;
/* 4410 */     if (i == -1) {
/* 4411 */       return "[]";
/*      */     }
/* 4413 */     StringBuilder stringBuilder = new StringBuilder();
/* 4414 */     stringBuilder.append('[');
/* 4415 */     for (int j = 0;; j++) {
/* 4416 */       stringBuilder.append(paramArrayOfchar[j]);
/* 4417 */       if (j == i)
/* 4418 */         return stringBuilder.append(']').toString(); 
/* 4419 */       stringBuilder.append(", ");
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
/*      */   public static String toString(byte[] paramArrayOfbyte) {
/* 4437 */     if (paramArrayOfbyte == null)
/* 4438 */       return "null"; 
/* 4439 */     int i = paramArrayOfbyte.length - 1;
/* 4440 */     if (i == -1) {
/* 4441 */       return "[]";
/*      */     }
/* 4443 */     StringBuilder stringBuilder = new StringBuilder();
/* 4444 */     stringBuilder.append('[');
/* 4445 */     for (int j = 0;; j++) {
/* 4446 */       stringBuilder.append(paramArrayOfbyte[j]);
/* 4447 */       if (j == i)
/* 4448 */         return stringBuilder.append(']').toString(); 
/* 4449 */       stringBuilder.append(", ");
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
/*      */   public static String toString(boolean[] paramArrayOfboolean) {
/* 4467 */     if (paramArrayOfboolean == null)
/* 4468 */       return "null"; 
/* 4469 */     int i = paramArrayOfboolean.length - 1;
/* 4470 */     if (i == -1) {
/* 4471 */       return "[]";
/*      */     }
/* 4473 */     StringBuilder stringBuilder = new StringBuilder();
/* 4474 */     stringBuilder.append('[');
/* 4475 */     for (int j = 0;; j++) {
/* 4476 */       stringBuilder.append(paramArrayOfboolean[j]);
/* 4477 */       if (j == i)
/* 4478 */         return stringBuilder.append(']').toString(); 
/* 4479 */       stringBuilder.append(", ");
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
/*      */   public static String toString(float[] paramArrayOffloat) {
/* 4497 */     if (paramArrayOffloat == null) {
/* 4498 */       return "null";
/*      */     }
/* 4500 */     int i = paramArrayOffloat.length - 1;
/* 4501 */     if (i == -1) {
/* 4502 */       return "[]";
/*      */     }
/* 4504 */     StringBuilder stringBuilder = new StringBuilder();
/* 4505 */     stringBuilder.append('[');
/* 4506 */     for (int j = 0;; j++) {
/* 4507 */       stringBuilder.append(paramArrayOffloat[j]);
/* 4508 */       if (j == i)
/* 4509 */         return stringBuilder.append(']').toString(); 
/* 4510 */       stringBuilder.append(", ");
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
/*      */   public static String toString(double[] paramArrayOfdouble) {
/* 4528 */     if (paramArrayOfdouble == null)
/* 4529 */       return "null"; 
/* 4530 */     int i = paramArrayOfdouble.length - 1;
/* 4531 */     if (i == -1) {
/* 4532 */       return "[]";
/*      */     }
/* 4534 */     StringBuilder stringBuilder = new StringBuilder();
/* 4535 */     stringBuilder.append('[');
/* 4536 */     for (int j = 0;; j++) {
/* 4537 */       stringBuilder.append(paramArrayOfdouble[j]);
/* 4538 */       if (j == i)
/* 4539 */         return stringBuilder.append(']').toString(); 
/* 4540 */       stringBuilder.append(", ");
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
/*      */   public static String toString(Object[] paramArrayOfObject) {
/* 4561 */     if (paramArrayOfObject == null) {
/* 4562 */       return "null";
/*      */     }
/* 4564 */     int i = paramArrayOfObject.length - 1;
/* 4565 */     if (i == -1) {
/* 4566 */       return "[]";
/*      */     }
/* 4568 */     StringBuilder stringBuilder = new StringBuilder();
/* 4569 */     stringBuilder.append('[');
/* 4570 */     for (int j = 0;; j++) {
/* 4571 */       stringBuilder.append(String.valueOf(paramArrayOfObject[j]));
/* 4572 */       if (j == i)
/* 4573 */         return stringBuilder.append(']').toString(); 
/* 4574 */       stringBuilder.append(", ");
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
/*      */   public static String deepToString(Object[] paramArrayOfObject) {
/* 4612 */     if (paramArrayOfObject == null) {
/* 4613 */       return "null";
/*      */     }
/* 4615 */     int i = 20 * paramArrayOfObject.length;
/* 4616 */     if (paramArrayOfObject.length != 0 && i <= 0)
/* 4617 */       i = Integer.MAX_VALUE; 
/* 4618 */     StringBuilder stringBuilder = new StringBuilder(i);
/* 4619 */     deepToString(paramArrayOfObject, stringBuilder, new HashSet());
/* 4620 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void deepToString(Object[] paramArrayOfObject, StringBuilder paramStringBuilder, Set<Object[]> paramSet) {
/* 4625 */     if (paramArrayOfObject == null) {
/* 4626 */       paramStringBuilder.append("null");
/*      */       return;
/*      */     } 
/* 4629 */     int i = paramArrayOfObject.length - 1;
/* 4630 */     if (i == -1) {
/* 4631 */       paramStringBuilder.append("[]");
/*      */       
/*      */       return;
/*      */     } 
/* 4635 */     paramSet.add(paramArrayOfObject);
/* 4636 */     paramStringBuilder.append('[');
/* 4637 */     for (int j = 0;; j++) {
/*      */       
/* 4639 */       Object object = paramArrayOfObject[j];
/* 4640 */       if (object == null) {
/* 4641 */         paramStringBuilder.append("null");
/*      */       } else {
/* 4643 */         Class<?> clazz = object.getClass();
/*      */         
/* 4645 */         if (clazz.isArray()) {
/* 4646 */           if (clazz == byte[].class) {
/* 4647 */             paramStringBuilder.append(toString((byte[])object));
/* 4648 */           } else if (clazz == short[].class) {
/* 4649 */             paramStringBuilder.append(toString((short[])object));
/* 4650 */           } else if (clazz == int[].class) {
/* 4651 */             paramStringBuilder.append(toString((int[])object));
/* 4652 */           } else if (clazz == long[].class) {
/* 4653 */             paramStringBuilder.append(toString((long[])object));
/* 4654 */           } else if (clazz == char[].class) {
/* 4655 */             paramStringBuilder.append(toString((char[])object));
/* 4656 */           } else if (clazz == float[].class) {
/* 4657 */             paramStringBuilder.append(toString((float[])object));
/* 4658 */           } else if (clazz == double[].class) {
/* 4659 */             paramStringBuilder.append(toString((double[])object));
/* 4660 */           } else if (clazz == boolean[].class) {
/* 4661 */             paramStringBuilder.append(toString((boolean[])object));
/*      */           }
/* 4663 */           else if (paramSet.contains(object)) {
/* 4664 */             paramStringBuilder.append("[...]");
/*      */           } else {
/* 4666 */             deepToString((Object[])object, paramStringBuilder, paramSet);
/*      */           } 
/*      */         } else {
/* 4669 */           paramStringBuilder.append(object.toString());
/*      */         } 
/*      */       } 
/* 4672 */       if (j == i)
/*      */         break; 
/* 4674 */       paramStringBuilder.append(", ");
/*      */     } 
/* 4676 */     paramStringBuilder.append(']');
/* 4677 */     paramSet.remove(paramArrayOfObject);
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
/*      */   public static <T> void setAll(T[] paramArrayOfT, IntFunction<? extends T> paramIntFunction) {
/* 4696 */     Objects.requireNonNull(paramIntFunction);
/* 4697 */     for (byte b = 0; b < paramArrayOfT.length; b++) {
/* 4698 */       paramArrayOfT[b] = paramIntFunction.apply(b);
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
/*      */   public static <T> void parallelSetAll(T[] paramArrayOfT, IntFunction<? extends T> paramIntFunction) {
/* 4717 */     Objects.requireNonNull(paramIntFunction);
/* 4718 */     IntStream.range(0, paramArrayOfT.length).parallel().forEach(paramInt -> paramArrayOfObject[paramInt] = paramIntFunction.apply(paramInt));
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
/*      */   public static void setAll(int[] paramArrayOfint, IntUnaryOperator paramIntUnaryOperator) {
/* 4735 */     Objects.requireNonNull(paramIntUnaryOperator);
/* 4736 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 4737 */       paramArrayOfint[b] = paramIntUnaryOperator.applyAsInt(b);
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
/*      */   public static void parallelSetAll(int[] paramArrayOfint, IntUnaryOperator paramIntUnaryOperator) {
/* 4755 */     Objects.requireNonNull(paramIntUnaryOperator);
/* 4756 */     IntStream.range(0, paramArrayOfint.length).parallel().forEach(paramInt -> paramArrayOfint[paramInt] = paramIntUnaryOperator.applyAsInt(paramInt));
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
/*      */   public static void setAll(long[] paramArrayOflong, IntToLongFunction paramIntToLongFunction) {
/* 4773 */     Objects.requireNonNull(paramIntToLongFunction);
/* 4774 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 4775 */       paramArrayOflong[b] = paramIntToLongFunction.applyAsLong(b);
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
/*      */   public static void parallelSetAll(long[] paramArrayOflong, IntToLongFunction paramIntToLongFunction) {
/* 4793 */     Objects.requireNonNull(paramIntToLongFunction);
/* 4794 */     IntStream.range(0, paramArrayOflong.length).parallel().forEach(paramInt -> paramArrayOflong[paramInt] = paramIntToLongFunction.applyAsLong(paramInt));
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
/*      */   public static void setAll(double[] paramArrayOfdouble, IntToDoubleFunction paramIntToDoubleFunction) {
/* 4811 */     Objects.requireNonNull(paramIntToDoubleFunction);
/* 4812 */     for (byte b = 0; b < paramArrayOfdouble.length; b++) {
/* 4813 */       paramArrayOfdouble[b] = paramIntToDoubleFunction.applyAsDouble(b);
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
/*      */   public static void parallelSetAll(double[] paramArrayOfdouble, IntToDoubleFunction paramIntToDoubleFunction) {
/* 4831 */     Objects.requireNonNull(paramIntToDoubleFunction);
/* 4832 */     IntStream.range(0, paramArrayOfdouble.length).parallel().forEach(paramInt -> paramArrayOfdouble[paramInt] = paramIntToDoubleFunction.applyAsDouble(paramInt));
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
/*      */   public static <T> Spliterator<T> spliterator(T[] paramArrayOfT) {
/* 4848 */     return Spliterators.spliterator((Object[])paramArrayOfT, 1040);
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
/*      */   public static <T> Spliterator<T> spliterator(T[] paramArrayOfT, int paramInt1, int paramInt2) {
/* 4872 */     return Spliterators.spliterator((Object[])paramArrayOfT, paramInt1, paramInt2, 1040);
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
/*      */   public static Spliterator.OfInt spliterator(int[] paramArrayOfint) {
/* 4888 */     return Spliterators.spliterator(paramArrayOfint, 1040);
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
/*      */   public static Spliterator.OfInt spliterator(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 4911 */     return Spliterators.spliterator(paramArrayOfint, paramInt1, paramInt2, 1040);
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
/*      */   public static Spliterator.OfLong spliterator(long[] paramArrayOflong) {
/* 4927 */     return Spliterators.spliterator(paramArrayOflong, 1040);
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
/*      */   public static Spliterator.OfLong spliterator(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 4950 */     return Spliterators.spliterator(paramArrayOflong, paramInt1, paramInt2, 1040);
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
/*      */   public static Spliterator.OfDouble spliterator(double[] paramArrayOfdouble) {
/* 4967 */     return Spliterators.spliterator(paramArrayOfdouble, 1040);
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
/*      */   public static Spliterator.OfDouble spliterator(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 4990 */     return Spliterators.spliterator(paramArrayOfdouble, paramInt1, paramInt2, 1040);
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
/*      */   public static <T> Stream<T> stream(T[] paramArrayOfT) {
/* 5004 */     return stream(paramArrayOfT, 0, paramArrayOfT.length);
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
/*      */   public static <T> Stream<T> stream(T[] paramArrayOfT, int paramInt1, int paramInt2) {
/* 5023 */     return StreamSupport.stream(spliterator(paramArrayOfT, paramInt1, paramInt2), false);
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
/*      */   public static IntStream stream(int[] paramArrayOfint) {
/* 5035 */     return stream(paramArrayOfint, 0, paramArrayOfint.length);
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
/*      */   public static IntStream stream(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 5053 */     return StreamSupport.intStream(spliterator(paramArrayOfint, paramInt1, paramInt2), false);
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
/*      */   public static LongStream stream(long[] paramArrayOflong) {
/* 5065 */     return stream(paramArrayOflong, 0, paramArrayOflong.length);
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
/*      */   public static LongStream stream(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 5083 */     return StreamSupport.longStream(spliterator(paramArrayOflong, paramInt1, paramInt2), false);
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
/*      */   public static DoubleStream stream(double[] paramArrayOfdouble) {
/* 5095 */     return stream(paramArrayOfdouble, 0, paramArrayOfdouble.length);
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
/*      */   public static DoubleStream stream(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 5113 */     return StreamSupport.doubleStream(spliterator(paramArrayOfdouble, paramInt1, paramInt2), false);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Arrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */