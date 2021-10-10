/*      */ package java.lang;
/*      */ 
/*      */ import java.util.Random;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Math
/*      */ {
/*      */   public static final double E = 2.718281828459045D;
/*      */   public static final double PI = 3.141592653589793D;
/*      */   
/*      */   public static double sin(double paramDouble) {
/*  139 */     return StrictMath.sin(paramDouble);
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
/*      */   public static double cos(double paramDouble) {
/*  154 */     return StrictMath.cos(paramDouble);
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
/*      */   public static double tan(double paramDouble) {
/*  171 */     return StrictMath.tan(paramDouble);
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
/*      */   public static double asin(double paramDouble) {
/*  189 */     return StrictMath.asin(paramDouble);
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
/*      */   public static double acos(double paramDouble) {
/*  205 */     return StrictMath.acos(paramDouble);
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
/*      */   public static double atan(double paramDouble) {
/*  222 */     return StrictMath.atan(paramDouble);
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
/*      */   public static double toRadians(double paramDouble) {
/*  236 */     return paramDouble / 180.0D * Math.PI;
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
/*      */   public static double toDegrees(double paramDouble) {
/*  252 */     return paramDouble * 180.0D / Math.PI;
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
/*      */   public static double exp(double paramDouble) {
/*  272 */     return StrictMath.exp(paramDouble);
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
/*      */   public static double log(double paramDouble) {
/*  293 */     return StrictMath.log(paramDouble);
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
/*      */   public static double log10(double paramDouble) {
/*  318 */     return StrictMath.log10(paramDouble);
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
/*      */   public static double sqrt(double paramDouble) {
/*  339 */     return StrictMath.sqrt(paramDouble);
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
/*      */   public static double cbrt(double paramDouble) {
/*  374 */     return StrictMath.cbrt(paramDouble);
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
/*      */   public static double IEEEremainder(double paramDouble1, double paramDouble2) {
/*  400 */     return StrictMath.IEEEremainder(paramDouble1, paramDouble2);
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
/*      */   public static double ceil(double paramDouble) {
/*  423 */     return StrictMath.ceil(paramDouble);
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
/*      */   public static double floor(double paramDouble) {
/*  442 */     return StrictMath.floor(paramDouble);
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
/*      */   public static double rint(double paramDouble) {
/*  461 */     return StrictMath.rint(paramDouble);
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
/*      */   public static double atan2(double paramDouble1, double paramDouble2) {
/*  517 */     return StrictMath.atan2(paramDouble1, paramDouble2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double pow(double paramDouble1, double paramDouble2) {
/*  644 */     return StrictMath.pow(paramDouble1, paramDouble2);
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
/*      */   public static int round(float paramFloat) {
/*  668 */     int i = Float.floatToRawIntBits(paramFloat);
/*  669 */     int j = (i & 0x7F800000) >> 23;
/*      */     
/*  671 */     int k = 149 - j;
/*      */     
/*  673 */     if ((k & 0xFFFFFFE0) == 0) {
/*      */       
/*  675 */       int m = i & 0x7FFFFF | 0x800000;
/*      */       
/*  677 */       if (i < 0) {
/*  678 */         m = -m;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  686 */       return (m >> k) + 1 >> 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  692 */     return (int)paramFloat;
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
/*      */   public static long round(double paramDouble) {
/*  717 */     long l1 = Double.doubleToRawLongBits(paramDouble);
/*  718 */     long l2 = (l1 & 0x7FF0000000000000L) >> 52L;
/*      */     
/*  720 */     long l3 = 1074L - l2;
/*      */     
/*  722 */     if ((l3 & 0xFFFFFFFFFFFFFFC0L) == 0L) {
/*      */       
/*  724 */       long l = l1 & 0xFFFFFFFFFFFFFL | 0x10000000000000L;
/*      */       
/*  726 */       if (l1 < 0L) {
/*  727 */         l = -l;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  735 */       return (l >> (int)l3) + 1L >> 1L;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  741 */     return (long)paramDouble;
/*      */   }
/*      */   
/*      */   private static final class RandomNumberGeneratorHolder
/*      */   {
/*  746 */     static final Random randomNumberGenerator = new Random();
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
/*      */   public static double random() {
/*  773 */     return RandomNumberGeneratorHolder.randomNumberGenerator.nextDouble();
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
/*      */   public static int addExact(int paramInt1, int paramInt2) {
/*  787 */     int i = paramInt1 + paramInt2;
/*      */     
/*  789 */     if (((paramInt1 ^ i) & (paramInt2 ^ i)) < 0) {
/*  790 */       throw new ArithmeticException("integer overflow");
/*      */     }
/*  792 */     return i;
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
/*      */   public static long addExact(long paramLong1, long paramLong2) {
/*  806 */     long l = paramLong1 + paramLong2;
/*      */     
/*  808 */     if (((paramLong1 ^ l) & (paramLong2 ^ l)) < 0L) {
/*  809 */       throw new ArithmeticException("long overflow");
/*      */     }
/*  811 */     return l;
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
/*      */   public static int subtractExact(int paramInt1, int paramInt2) {
/*  825 */     int i = paramInt1 - paramInt2;
/*      */ 
/*      */     
/*  828 */     if (((paramInt1 ^ paramInt2) & (paramInt1 ^ i)) < 0) {
/*  829 */       throw new ArithmeticException("integer overflow");
/*      */     }
/*  831 */     return i;
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
/*      */   public static long subtractExact(long paramLong1, long paramLong2) {
/*  845 */     long l = paramLong1 - paramLong2;
/*      */ 
/*      */     
/*  848 */     if (((paramLong1 ^ paramLong2) & (paramLong1 ^ l)) < 0L) {
/*  849 */       throw new ArithmeticException("long overflow");
/*      */     }
/*  851 */     return l;
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
/*      */   public static int multiplyExact(int paramInt1, int paramInt2) {
/*  865 */     long l = paramInt1 * paramInt2;
/*  866 */     if ((int)l != l) {
/*  867 */       throw new ArithmeticException("integer overflow");
/*      */     }
/*  869 */     return (int)l;
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
/*      */   public static long multiplyExact(long paramLong1, long paramLong2) {
/*  883 */     long l1 = paramLong1 * paramLong2;
/*  884 */     long l2 = abs(paramLong1);
/*  885 */     long l3 = abs(paramLong2);
/*  886 */     if ((l2 | l3) >>> 31L != 0L)
/*      */     {
/*      */ 
/*      */       
/*  890 */       if ((paramLong2 != 0L && l1 / paramLong2 != paramLong1) || (paramLong1 == Long.MIN_VALUE && paramLong2 == -1L))
/*      */       {
/*  892 */         throw new ArithmeticException("long overflow");
/*      */       }
/*      */     }
/*  895 */     return l1;
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
/*      */   public static int incrementExact(int paramInt) {
/*  908 */     if (paramInt == Integer.MAX_VALUE) {
/*  909 */       throw new ArithmeticException("integer overflow");
/*      */     }
/*      */     
/*  912 */     return paramInt + 1;
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
/*      */   public static long incrementExact(long paramLong) {
/*  925 */     if (paramLong == Long.MAX_VALUE) {
/*  926 */       throw new ArithmeticException("long overflow");
/*      */     }
/*      */     
/*  929 */     return paramLong + 1L;
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
/*      */   public static int decrementExact(int paramInt) {
/*  942 */     if (paramInt == Integer.MIN_VALUE) {
/*  943 */       throw new ArithmeticException("integer overflow");
/*      */     }
/*      */     
/*  946 */     return paramInt - 1;
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
/*      */   public static long decrementExact(long paramLong) {
/*  959 */     if (paramLong == Long.MIN_VALUE) {
/*  960 */       throw new ArithmeticException("long overflow");
/*      */     }
/*      */     
/*  963 */     return paramLong - 1L;
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
/*      */   public static int negateExact(int paramInt) {
/*  976 */     if (paramInt == Integer.MIN_VALUE) {
/*  977 */       throw new ArithmeticException("integer overflow");
/*      */     }
/*      */     
/*  980 */     return -paramInt;
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
/*      */   public static long negateExact(long paramLong) {
/*  993 */     if (paramLong == Long.MIN_VALUE) {
/*  994 */       throw new ArithmeticException("long overflow");
/*      */     }
/*      */     
/*  997 */     return -paramLong;
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
/*      */   public static int toIntExact(long paramLong) {
/* 1010 */     if ((int)paramLong != paramLong) {
/* 1011 */       throw new ArithmeticException("integer overflow");
/*      */     }
/* 1013 */     return (int)paramLong;
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
/*      */   public static int floorDiv(int paramInt1, int paramInt2) {
/* 1052 */     int i = paramInt1 / paramInt2;
/*      */     
/* 1054 */     if ((paramInt1 ^ paramInt2) < 0 && i * paramInt2 != paramInt1) {
/* 1055 */       i--;
/*      */     }
/* 1057 */     return i;
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
/*      */   public static long floorDiv(long paramLong1, long paramLong2) {
/* 1086 */     long l = paramLong1 / paramLong2;
/*      */     
/* 1088 */     if ((paramLong1 ^ paramLong2) < 0L && l * paramLong2 != paramLong1) {
/* 1089 */       l--;
/*      */     }
/* 1091 */     return l;
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
/*      */   public static int floorMod(int paramInt1, int paramInt2) {
/* 1139 */     return paramInt1 - floorDiv(paramInt1, paramInt2) * paramInt2;
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
/*      */   public static long floorMod(long paramLong1, long paramLong2) {
/* 1166 */     return paramLong1 - floorDiv(paramLong1, paramLong2) * paramLong2;
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
/*      */   public static int abs(int paramInt) {
/* 1183 */     return (paramInt < 0) ? -paramInt : paramInt;
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
/*      */   public static long abs(long paramLong) {
/* 1200 */     return (paramLong < 0L) ? -paramLong : paramLong;
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
/*      */   public static float abs(float paramFloat) {
/* 1219 */     return (paramFloat <= 0.0F) ? (0.0F - paramFloat) : paramFloat;
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
/*      */   public static double abs(double paramDouble) {
/* 1238 */     return (paramDouble <= 0.0D) ? (0.0D - paramDouble) : paramDouble;
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
/*      */   public static int max(int paramInt1, int paramInt2) {
/* 1252 */     return (paramInt1 >= paramInt2) ? paramInt1 : paramInt2;
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
/*      */   public static long max(long paramLong1, long paramLong2) {
/* 1266 */     return (paramLong1 >= paramLong2) ? paramLong1 : paramLong2;
/*      */   }
/*      */ 
/*      */   
/* 1270 */   private static long negativeZeroFloatBits = Float.floatToRawIntBits(-0.0F);
/* 1271 */   private static long negativeZeroDoubleBits = Double.doubleToRawLongBits(-0.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float paramFloat1, float paramFloat2) {
/* 1288 */     if (paramFloat1 != paramFloat1)
/* 1289 */       return paramFloat1; 
/* 1290 */     if (paramFloat1 == 0.0F && paramFloat2 == 0.0F && 
/*      */       
/* 1292 */       Float.floatToRawIntBits(paramFloat1) == negativeZeroFloatBits)
/*      */     {
/* 1294 */       return paramFloat2;
/*      */     }
/* 1296 */     return (paramFloat1 >= paramFloat2) ? paramFloat1 : paramFloat2;
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
/*      */   public static double max(double paramDouble1, double paramDouble2) {
/* 1314 */     if (paramDouble1 != paramDouble1)
/* 1315 */       return paramDouble1; 
/* 1316 */     if (paramDouble1 == 0.0D && paramDouble2 == 0.0D && 
/*      */       
/* 1318 */       Double.doubleToRawLongBits(paramDouble1) == negativeZeroDoubleBits)
/*      */     {
/* 1320 */       return paramDouble2;
/*      */     }
/* 1322 */     return (paramDouble1 >= paramDouble2) ? paramDouble1 : paramDouble2;
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
/*      */   public static int min(int paramInt1, int paramInt2) {
/* 1336 */     return (paramInt1 <= paramInt2) ? paramInt1 : paramInt2;
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
/*      */   public static long min(long paramLong1, long paramLong2) {
/* 1350 */     return (paramLong1 <= paramLong2) ? paramLong1 : paramLong2;
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
/*      */   public static float min(float paramFloat1, float paramFloat2) {
/* 1368 */     if (paramFloat1 != paramFloat1)
/* 1369 */       return paramFloat1; 
/* 1370 */     if (paramFloat1 == 0.0F && paramFloat2 == 0.0F && 
/*      */       
/* 1372 */       Float.floatToRawIntBits(paramFloat2) == negativeZeroFloatBits)
/*      */     {
/* 1374 */       return paramFloat2;
/*      */     }
/* 1376 */     return (paramFloat1 <= paramFloat2) ? paramFloat1 : paramFloat2;
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
/*      */   public static double min(double paramDouble1, double paramDouble2) {
/* 1394 */     if (paramDouble1 != paramDouble1)
/* 1395 */       return paramDouble1; 
/* 1396 */     if (paramDouble1 == 0.0D && paramDouble2 == 0.0D && 
/*      */       
/* 1398 */       Double.doubleToRawLongBits(paramDouble2) == negativeZeroDoubleBits)
/*      */     {
/* 1400 */       return paramDouble2;
/*      */     }
/* 1402 */     return (paramDouble1 <= paramDouble2) ? paramDouble1 : paramDouble2;
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
/*      */   public static double ulp(double paramDouble) {
/* 1429 */     int i = getExponent(paramDouble);
/*      */     
/* 1431 */     switch (i) {
/*      */       case 1024:
/* 1433 */         return abs(paramDouble);
/*      */       
/*      */       case -1023:
/* 1436 */         return Double.MIN_VALUE;
/*      */     } 
/*      */     
/* 1439 */     assert i <= 1023 && i >= -1022;
/*      */ 
/*      */     
/* 1442 */     i -= 52;
/* 1443 */     if (i >= -1022) {
/* 1444 */       return powerOfTwoD(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1450 */     return Double.longBitsToDouble(1L << i - -1074);
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
/*      */   public static float ulp(float paramFloat) {
/* 1480 */     int i = getExponent(paramFloat);
/*      */     
/* 1482 */     switch (i) {
/*      */       case 128:
/* 1484 */         return abs(paramFloat);
/*      */       
/*      */       case -127:
/* 1487 */         return Float.MIN_VALUE;
/*      */     } 
/*      */     
/* 1490 */     assert i <= 127 && i >= -126;
/*      */ 
/*      */     
/* 1493 */     i -= 23;
/* 1494 */     if (i >= -126) {
/* 1495 */       return powerOfTwoF(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1501 */     return Float.intBitsToFloat(1 << i - -149);
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
/*      */   public static double signum(double paramDouble) {
/* 1525 */     return (paramDouble == 0.0D || Double.isNaN(paramDouble)) ? paramDouble : copySign(1.0D, paramDouble);
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
/*      */   public static float signum(float paramFloat) {
/* 1546 */     return (paramFloat == 0.0F || Float.isNaN(paramFloat)) ? paramFloat : copySign(1.0F, paramFloat);
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
/*      */   public static double sinh(double paramDouble) {
/* 1575 */     return StrictMath.sinh(paramDouble);
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
/*      */   public static double cosh(double paramDouble) {
/* 1603 */     return StrictMath.cosh(paramDouble);
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
/*      */   public static double tanh(double paramDouble) {
/* 1643 */     return StrictMath.tanh(paramDouble);
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
/*      */   public static double hypot(double paramDouble1, double paramDouble2) {
/* 1672 */     return StrictMath.hypot(paramDouble1, paramDouble2);
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
/*      */   public static double expm1(double paramDouble) {
/* 1710 */     return StrictMath.expm1(paramDouble);
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
/*      */   public static double log1p(double paramDouble) {
/* 1747 */     return StrictMath.log1p(paramDouble);
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
/*      */   public static double copySign(double paramDouble1, double paramDouble2) {
/* 1766 */     return Double.longBitsToDouble(Double.doubleToRawLongBits(paramDouble2) & Long.MIN_VALUE | 
/*      */         
/* 1768 */         Double.doubleToRawLongBits(paramDouble1) & Long.MAX_VALUE);
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
/*      */   public static float copySign(float paramFloat1, float paramFloat2) {
/* 1789 */     return Float.intBitsToFloat(Float.floatToRawIntBits(paramFloat2) & Integer.MIN_VALUE | 
/*      */         
/* 1791 */         Float.floatToRawIntBits(paramFloat1) & Integer.MAX_VALUE);
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
/*      */   public static int getExponent(float paramFloat) {
/* 1816 */     return ((Float.floatToRawIntBits(paramFloat) & 0x7F800000) >> 23) - 127;
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
/*      */   public static int getExponent(double paramDouble) {
/* 1840 */     return (int)(((Double.doubleToRawLongBits(paramDouble) & 0x7FF0000000000000L) >> 52L) - 1023L);
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
/*      */   public static double nextAfter(double paramDouble1, double paramDouble2) {
/* 1897 */     if (Double.isNaN(paramDouble1) || Double.isNaN(paramDouble2))
/*      */     {
/* 1899 */       return paramDouble1 + paramDouble2; } 
/* 1900 */     if (paramDouble1 == paramDouble2) {
/* 1901 */       return paramDouble2;
/*      */     }
/*      */ 
/*      */     
/* 1905 */     long l = Double.doubleToRawLongBits(paramDouble1 + 0.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1919 */     if (paramDouble2 > paramDouble1) {
/* 1920 */       l += (l >= 0L) ? 1L : -1L;
/*      */     } else {
/* 1922 */       assert paramDouble2 < paramDouble1;
/* 1923 */       if (l > 0L) {
/* 1924 */         l--;
/*      */       }
/* 1926 */       else if (l < 0L) {
/* 1927 */         l++;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1937 */         l = -9223372036854775807L;
/*      */       } 
/*      */     } 
/* 1940 */     return Double.longBitsToDouble(l);
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
/*      */   public static float nextAfter(float paramFloat, double paramDouble) {
/* 1996 */     if (Float.isNaN(paramFloat) || Double.isNaN(paramDouble))
/*      */     {
/* 1998 */       return paramFloat + (float)paramDouble; } 
/* 1999 */     if (paramFloat == paramDouble) {
/* 2000 */       return (float)paramDouble;
/*      */     }
/*      */ 
/*      */     
/* 2004 */     int i = Float.floatToRawIntBits(paramFloat + 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2018 */     if (paramDouble > paramFloat) {
/* 2019 */       i += (i >= 0) ? 1 : -1;
/*      */     } else {
/* 2021 */       assert paramDouble < paramFloat;
/* 2022 */       if (i > 0) {
/* 2023 */         i--;
/*      */       }
/* 2025 */       else if (i < 0) {
/* 2026 */         i++;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2036 */         i = -2147483647;
/*      */       } 
/*      */     } 
/* 2039 */     return Float.intBitsToFloat(i);
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
/*      */   public static double nextUp(double paramDouble) {
/* 2069 */     if (Double.isNaN(paramDouble) || paramDouble == Double.POSITIVE_INFINITY) {
/* 2070 */       return paramDouble;
/*      */     }
/* 2072 */     paramDouble += 0.0D;
/* 2073 */     return Double.longBitsToDouble(Double.doubleToRawLongBits(paramDouble) + ((paramDouble >= 0.0D) ? 1L : -1L));
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
/*      */   public static float nextUp(float paramFloat) {
/* 2104 */     if (Float.isNaN(paramFloat) || paramFloat == Float.POSITIVE_INFINITY) {
/* 2105 */       return paramFloat;
/*      */     }
/* 2107 */     paramFloat += 0.0F;
/* 2108 */     return Float.intBitsToFloat(Float.floatToRawIntBits(paramFloat) + ((paramFloat >= 0.0F) ? 1 : -1));
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
/*      */   public static double nextDown(double paramDouble) {
/* 2139 */     if (Double.isNaN(paramDouble) || paramDouble == Double.NEGATIVE_INFINITY) {
/* 2140 */       return paramDouble;
/*      */     }
/* 2142 */     if (paramDouble == 0.0D) {
/* 2143 */       return -4.9E-324D;
/*      */     }
/* 2145 */     return Double.longBitsToDouble(Double.doubleToRawLongBits(paramDouble) + ((paramDouble > 0.0D) ? -1L : 1L));
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
/*      */   public static float nextDown(float paramFloat) {
/* 2176 */     if (Float.isNaN(paramFloat) || paramFloat == Float.NEGATIVE_INFINITY) {
/* 2177 */       return paramFloat;
/*      */     }
/* 2179 */     if (paramFloat == 0.0F) {
/* 2180 */       return -1.4E-45F;
/*      */     }
/* 2182 */     return Float.intBitsToFloat(Float.floatToRawIntBits(paramFloat) + ((paramFloat > 0.0F) ? -1 : 1));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double scalb(double paramDouble, int paramInt) {
/* 2262 */     int i = 0;
/* 2263 */     short s = 0;
/* 2264 */     double d = Double.NaN;
/*      */ 
/*      */ 
/*      */     
/* 2268 */     if (paramInt < 0) {
/* 2269 */       paramInt = max(paramInt, -2099);
/* 2270 */       s = -512;
/* 2271 */       d = twoToTheDoubleScaleDown;
/*      */     } else {
/*      */       
/* 2274 */       paramInt = min(paramInt, 2099);
/* 2275 */       s = 512;
/* 2276 */       d = twoToTheDoubleScaleUp;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2281 */     int j = paramInt >> 8 >>> 23;
/* 2282 */     i = (paramInt + j & 0x1FF) - j;
/*      */     
/* 2284 */     paramDouble *= powerOfTwoD(i);
/* 2285 */     paramInt -= i;
/*      */     
/* 2287 */     while (paramInt != 0) {
/* 2288 */       paramDouble *= d;
/* 2289 */       paramInt -= s;
/*      */     } 
/* 2291 */     return paramDouble;
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
/*      */   public static float scalb(float paramFloat, int paramInt) {
/* 2333 */     paramInt = max(min(paramInt, 278), -278);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2344 */     return (float)(paramFloat * powerOfTwoD(paramInt));
/*      */   }
/*      */ 
/*      */   
/* 2348 */   static double twoToTheDoubleScaleUp = powerOfTwoD(512);
/* 2349 */   static double twoToTheDoubleScaleDown = powerOfTwoD(-512);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double powerOfTwoD(int paramInt) {
/* 2355 */     assert paramInt >= -1022 && paramInt <= 1023;
/* 2356 */     return Double.longBitsToDouble(paramInt + 1023L << 52L & 0x7FF0000000000000L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static float powerOfTwoF(int paramInt) {
/* 2365 */     assert paramInt >= -126 && paramInt <= 127;
/* 2366 */     return Float.intBitsToFloat(paramInt + 127 << 23 & 0x7F800000);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Math.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */