/*      */ package java.math;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.Arrays;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigDecimal
/*      */   extends Number
/*      */   implements Comparable<BigDecimal>
/*      */ {
/*      */   private final BigInteger intVal;
/*      */   private final int scale;
/*      */   private transient int precision;
/*      */   private transient String stringCache;
/*      */   static final long INFLATED = -9223372036854775808L;
/*  261 */   private static final BigInteger INFLATED_BIGINT = BigInteger.valueOf(Long.MIN_VALUE);
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient long intCompact;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_COMPACT_DIGITS = 18;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 6108874887143696463L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  278 */   private static final ThreadLocal<StringBuilderHelper> threadLocalStringBuilderHelper = new ThreadLocal<StringBuilderHelper>()
/*      */     {
/*      */       protected BigDecimal.StringBuilderHelper initialValue() {
/*  281 */         return new BigDecimal.StringBuilderHelper();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  286 */   private static final BigDecimal[] zeroThroughTen = new BigDecimal[] { new BigDecimal(BigInteger.ZERO, 0L, 0, 1), new BigDecimal(BigInteger.ONE, 1L, 0, 1), new BigDecimal(
/*      */ 
/*      */         
/*  289 */         BigInteger.valueOf(2L), 2L, 0, 1), new BigDecimal(
/*  290 */         BigInteger.valueOf(3L), 3L, 0, 1), new BigDecimal(
/*  291 */         BigInteger.valueOf(4L), 4L, 0, 1), new BigDecimal(
/*  292 */         BigInteger.valueOf(5L), 5L, 0, 1), new BigDecimal(
/*  293 */         BigInteger.valueOf(6L), 6L, 0, 1), new BigDecimal(
/*  294 */         BigInteger.valueOf(7L), 7L, 0, 1), new BigDecimal(
/*  295 */         BigInteger.valueOf(8L), 8L, 0, 1), new BigDecimal(
/*  296 */         BigInteger.valueOf(9L), 9L, 0, 1), new BigDecimal(BigInteger.TEN, 10L, 0, 2) };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  301 */   private static final BigDecimal[] ZERO_SCALED_BY = new BigDecimal[] { zeroThroughTen[0], new BigDecimal(BigInteger.ZERO, 0L, 1, 1), new BigDecimal(BigInteger.ZERO, 0L, 2, 1), new BigDecimal(BigInteger.ZERO, 0L, 3, 1), new BigDecimal(BigInteger.ZERO, 0L, 4, 1), new BigDecimal(BigInteger.ZERO, 0L, 5, 1), new BigDecimal(BigInteger.ZERO, 0L, 6, 1), new BigDecimal(BigInteger.ZERO, 0L, 7, 1), new BigDecimal(BigInteger.ZERO, 0L, 8, 1), new BigDecimal(BigInteger.ZERO, 0L, 9, 1), new BigDecimal(BigInteger.ZERO, 0L, 10, 1), new BigDecimal(BigInteger.ZERO, 0L, 11, 1), new BigDecimal(BigInteger.ZERO, 0L, 12, 1), new BigDecimal(BigInteger.ZERO, 0L, 13, 1), new BigDecimal(BigInteger.ZERO, 0L, 14, 1), new BigDecimal(BigInteger.ZERO, 0L, 15, 1) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long HALF_LONG_MAX_VALUE = 4611686018427387903L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long HALF_LONG_MIN_VALUE = -4611686018427387904L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  330 */   public static final BigDecimal ZERO = zeroThroughTen[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  338 */   public static final BigDecimal ONE = zeroThroughTen[1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  346 */   public static final BigDecimal TEN = zeroThroughTen[10];
/*      */   public static final int ROUND_UP = 0;
/*      */   public static final int ROUND_DOWN = 1;
/*      */   public static final int ROUND_CEILING = 2;
/*      */   public static final int ROUND_FLOOR = 3;
/*      */   public static final int ROUND_HALF_UP = 4;
/*      */   public static final int ROUND_HALF_DOWN = 5;
/*      */   public static final int ROUND_HALF_EVEN = 6;
/*      */   public static final int ROUND_UNNECESSARY = 7;
/*      */   
/*      */   BigDecimal(BigInteger paramBigInteger, long paramLong, int paramInt1, int paramInt2) {
/*  357 */     this.scale = paramInt1;
/*  358 */     this.precision = paramInt2;
/*  359 */     this.intCompact = paramLong;
/*  360 */     this.intVal = paramBigInteger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  383 */     this(paramArrayOfchar, paramInt1, paramInt2, MathContext.UNLIMITED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(char[] paramArrayOfchar, int paramInt1, int paramInt2, MathContext paramMathContext) {
/*  411 */     if ((paramArrayOfchar.length | paramInt2 | paramInt1) < 0 || paramInt2 > paramArrayOfchar.length - paramInt1) {
/*  412 */       throw new NumberFormatException("Bad offset or len arguments for char[] input.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  422 */     int i = 0;
/*  423 */     int j = 0;
/*  424 */     long l = 0L;
/*  425 */     BigInteger bigInteger = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  430 */       boolean bool1 = false;
/*  431 */       if (paramArrayOfchar[paramInt1] == '-') {
/*  432 */         bool1 = true;
/*  433 */         paramInt1++;
/*  434 */         paramInt2--;
/*  435 */       } else if (paramArrayOfchar[paramInt1] == '+') {
/*  436 */         paramInt1++;
/*  437 */         paramInt2--;
/*      */       } 
/*      */ 
/*      */       
/*  441 */       boolean bool2 = false;
/*  442 */       long l1 = 0L;
/*      */       
/*  444 */       boolean bool3 = (paramInt2 <= 18) ? true : false;
/*      */ 
/*      */       
/*  447 */       byte b = 0;
/*  448 */       if (bool3) {
/*      */ 
/*      */         
/*  451 */         for (; paramInt2 > 0; paramInt1++, paramInt2--) {
/*  452 */           char c = paramArrayOfchar[paramInt1];
/*  453 */           if (c == '0')
/*  454 */           { if (!i) {
/*  455 */               i = 1;
/*  456 */             } else if (l != 0L) {
/*  457 */               l *= 10L;
/*  458 */               i++;
/*      */             } 
/*  460 */             if (bool2)
/*  461 */               j++;  }
/*  462 */           else if (c >= '1' && c <= '9')
/*  463 */           { int n = c - 48;
/*  464 */             if (i != 1 || l != 0L)
/*  465 */               i++; 
/*  466 */             l = l * 10L + n;
/*  467 */             if (bool2)
/*  468 */               j++;  }
/*  469 */           else if (c == '.')
/*      */           
/*  471 */           { if (bool2)
/*  472 */               throw new NumberFormatException(); 
/*  473 */             bool2 = true; }
/*  474 */           else if (Character.isDigit(c))
/*  475 */           { int n = Character.digit(c, 10);
/*  476 */             if (n == 0) {
/*  477 */               if (i == 0) {
/*  478 */                 i = 1;
/*  479 */               } else if (l != 0L) {
/*  480 */                 l *= 10L;
/*  481 */                 i++;
/*      */               } 
/*      */             } else {
/*  484 */               if (i != 1 || l != 0L)
/*  485 */                 i++; 
/*  486 */               l = l * 10L + n;
/*      */             } 
/*  488 */             if (bool2)
/*  489 */               j++;  }
/*  490 */           else { if (c == 'e' || c == 'E') {
/*  491 */               l1 = parseExp(paramArrayOfchar, paramInt1, paramInt2);
/*      */               
/*  493 */               if ((int)l1 != l1)
/*  494 */                 throw new NumberFormatException(); 
/*      */               break;
/*      */             } 
/*  497 */             throw new NumberFormatException(); }
/*      */         
/*      */         } 
/*  500 */         if (i == 0) {
/*  501 */           throw new NumberFormatException();
/*      */         }
/*  503 */         if (l1 != 0L) {
/*  504 */           j = adjustScale(j, l1);
/*      */         }
/*  506 */         l = bool1 ? -l : l;
/*  507 */         int k = paramMathContext.precision;
/*  508 */         int m = i - k;
/*      */         
/*  510 */         if (k > 0 && m > 0) {
/*  511 */           while (m > 0) {
/*  512 */             j = checkScaleNonZero(j - m);
/*  513 */             l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], paramMathContext.roundingMode.oldMode);
/*  514 */             i = longDigitLength(l);
/*  515 */             m = i - k;
/*      */           } 
/*      */         }
/*      */       } else {
/*  519 */         char[] arrayOfChar = new char[paramInt2];
/*  520 */         for (; paramInt2 > 0; paramInt1++, paramInt2--) {
/*  521 */           char c = paramArrayOfchar[paramInt1];
/*      */           
/*  523 */           if ((c >= '0' && c <= '9') || Character.isDigit(c)) {
/*      */ 
/*      */             
/*  526 */             if (c == '0' || Character.digit(c, 10) == 0) {
/*  527 */               if (i == 0) {
/*  528 */                 arrayOfChar[b] = c;
/*  529 */                 i = 1;
/*  530 */               } else if (b) {
/*  531 */                 arrayOfChar[b++] = c;
/*  532 */                 i++;
/*      */               } 
/*      */             } else {
/*  535 */               if (i != 1 || b != 0)
/*  536 */                 i++; 
/*  537 */               arrayOfChar[b++] = c;
/*      */             } 
/*  539 */             if (bool2) {
/*  540 */               j++;
/*      */             
/*      */             }
/*      */           }
/*  544 */           else if (c == '.') {
/*      */             
/*  546 */             if (bool2)
/*  547 */               throw new NumberFormatException(); 
/*  548 */             bool2 = true;
/*      */           }
/*      */           else {
/*      */             
/*  552 */             if (c != 'e' && c != 'E')
/*  553 */               throw new NumberFormatException(); 
/*  554 */             l1 = parseExp(paramArrayOfchar, paramInt1, paramInt2);
/*      */             
/*  556 */             if ((int)l1 != l1)
/*  557 */               throw new NumberFormatException(); 
/*      */             break;
/*      */           } 
/*      */         } 
/*  561 */         if (i == 0) {
/*  562 */           throw new NumberFormatException();
/*      */         }
/*  564 */         if (l1 != 0L) {
/*  565 */           j = adjustScale(j, l1);
/*      */         }
/*      */         
/*  568 */         bigInteger = new BigInteger(arrayOfChar, bool1 ? -1 : 1, i);
/*  569 */         l = compactValFor(bigInteger);
/*  570 */         int k = paramMathContext.precision;
/*  571 */         if (k > 0 && i > k) {
/*  572 */           if (l == Long.MIN_VALUE) {
/*  573 */             int m = i - k;
/*  574 */             while (m > 0) {
/*  575 */               j = checkScaleNonZero(j - m);
/*  576 */               bigInteger = divideAndRoundByTenPow(bigInteger, m, paramMathContext.roundingMode.oldMode);
/*  577 */               l = compactValFor(bigInteger);
/*  578 */               if (l != Long.MIN_VALUE) {
/*  579 */                 i = longDigitLength(l);
/*      */                 break;
/*      */               } 
/*  582 */               i = bigDigitLength(bigInteger);
/*  583 */               m = i - k;
/*      */             } 
/*      */           } 
/*  586 */           if (l != Long.MIN_VALUE) {
/*  587 */             int m = i - k;
/*  588 */             while (m > 0) {
/*  589 */               j = checkScaleNonZero(j - m);
/*  590 */               l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], paramMathContext.roundingMode.oldMode);
/*  591 */               i = longDigitLength(l);
/*  592 */               m = i - k;
/*      */             } 
/*  594 */             bigInteger = null;
/*      */           } 
/*      */         } 
/*      */       } 
/*  598 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*  599 */       throw new NumberFormatException();
/*  600 */     } catch (NegativeArraySizeException negativeArraySizeException) {
/*  601 */       throw new NumberFormatException();
/*      */     } 
/*  603 */     this.scale = j;
/*  604 */     this.precision = i;
/*  605 */     this.intCompact = l;
/*  606 */     this.intVal = bigInteger;
/*      */   }
/*      */   
/*      */   private int adjustScale(int paramInt, long paramLong) {
/*  610 */     long l = paramInt - paramLong;
/*  611 */     if (l > 2147483647L || l < -2147483648L)
/*  612 */       throw new NumberFormatException("Scale out of range."); 
/*  613 */     paramInt = (int)l;
/*  614 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long parseExp(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  621 */     long l = 0L;
/*  622 */     paramInt1++;
/*  623 */     char c = paramArrayOfchar[paramInt1];
/*  624 */     paramInt2--;
/*  625 */     boolean bool = (c == '-') ? true : false;
/*      */     
/*  627 */     if (bool || c == '+') {
/*  628 */       paramInt1++;
/*  629 */       c = paramArrayOfchar[paramInt1];
/*  630 */       paramInt2--;
/*      */     } 
/*  632 */     if (paramInt2 <= 0) {
/*  633 */       throw new NumberFormatException();
/*      */     }
/*  635 */     while (paramInt2 > 10 && (c == '0' || Character.digit(c, 10) == 0)) {
/*  636 */       paramInt1++;
/*  637 */       c = paramArrayOfchar[paramInt1];
/*  638 */       paramInt2--;
/*      */     } 
/*  640 */     if (paramInt2 > 10) {
/*  641 */       throw new NumberFormatException();
/*      */     }
/*  643 */     for (;; paramInt2--) {
/*      */       int i;
/*  645 */       if (c >= '0' && c <= '9') {
/*  646 */         i = c - 48;
/*      */       } else {
/*  648 */         i = Character.digit(c, 10);
/*  649 */         if (i < 0)
/*  650 */           throw new NumberFormatException(); 
/*      */       } 
/*  652 */       l = l * 10L + i;
/*  653 */       if (paramInt2 == 1)
/*      */         break; 
/*  655 */       paramInt1++;
/*  656 */       c = paramArrayOfchar[paramInt1];
/*      */     } 
/*  658 */     if (bool)
/*  659 */       l = -l; 
/*  660 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(char[] paramArrayOfchar) {
/*  680 */     this(paramArrayOfchar, 0, paramArrayOfchar.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(char[] paramArrayOfchar, MathContext paramMathContext) {
/*  704 */     this(paramArrayOfchar, 0, paramArrayOfchar.length, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(String paramString) {
/*  809 */     this(paramString.toCharArray(), 0, paramString.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(String paramString, MathContext paramMathContext) {
/*  827 */     this(paramString.toCharArray(), 0, paramString.length(), paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(double paramDouble) {
/*  875 */     this(paramDouble, MathContext.UNLIMITED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(double paramDouble, MathContext paramMathContext) {
/*      */     BigInteger bigInteger;
/*  897 */     if (Double.isInfinite(paramDouble) || Double.isNaN(paramDouble)) {
/*  898 */       throw new NumberFormatException("Infinite or NaN");
/*      */     }
/*      */     
/*  901 */     long l1 = Double.doubleToLongBits(paramDouble);
/*  902 */     boolean bool = (l1 >> 63L == 0L) ? true : true;
/*  903 */     int i = (int)(l1 >> 52L & 0x7FFL);
/*  904 */     long l2 = (i == 0) ? ((l1 & 0xFFFFFFFFFFFFFL) << 1L) : (l1 & 0xFFFFFFFFFFFFFL | 0x10000000000000L);
/*      */ 
/*      */     
/*  907 */     i -= 1075;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  914 */     if (l2 == 0L) {
/*  915 */       this.intVal = BigInteger.ZERO;
/*  916 */       this.scale = 0;
/*  917 */       this.intCompact = 0L;
/*  918 */       this.precision = 1;
/*      */       
/*      */       return;
/*      */     } 
/*  922 */     while ((l2 & 0x1L) == 0L) {
/*  923 */       l2 >>= 1L;
/*  924 */       i++;
/*      */     } 
/*  926 */     int j = 0;
/*      */ 
/*      */     
/*  929 */     long l3 = bool * l2;
/*  930 */     if (i == 0) {
/*  931 */       bigInteger = (l3 == Long.MIN_VALUE) ? INFLATED_BIGINT : null;
/*      */     } else {
/*  933 */       if (i < 0) {
/*  934 */         bigInteger = BigInteger.valueOf(5L).pow(-i).multiply(l3);
/*  935 */         j = -i;
/*      */       } else {
/*  937 */         bigInteger = BigInteger.valueOf(2L).pow(i).multiply(l3);
/*      */       } 
/*  939 */       l3 = compactValFor(bigInteger);
/*      */     } 
/*  941 */     int k = 0;
/*  942 */     int m = paramMathContext.precision;
/*  943 */     if (m > 0) {
/*  944 */       int n = paramMathContext.roundingMode.oldMode;
/*      */       
/*  946 */       if (l3 == Long.MIN_VALUE) {
/*  947 */         k = bigDigitLength(bigInteger);
/*  948 */         int i1 = k - m;
/*  949 */         while (i1 > 0) {
/*  950 */           j = checkScaleNonZero(j - i1);
/*  951 */           bigInteger = divideAndRoundByTenPow(bigInteger, i1, n);
/*  952 */           l3 = compactValFor(bigInteger);
/*  953 */           if (l3 != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/*  956 */           k = bigDigitLength(bigInteger);
/*  957 */           i1 = k - m;
/*      */         } 
/*      */       } 
/*  960 */       if (l3 != Long.MIN_VALUE) {
/*  961 */         k = longDigitLength(l3);
/*  962 */         int i1 = k - m;
/*  963 */         while (i1 > 0) {
/*  964 */           j = checkScaleNonZero(j - i1);
/*  965 */           l3 = divideAndRound(l3, LONG_TEN_POWERS_TABLE[i1], paramMathContext.roundingMode.oldMode);
/*  966 */           k = longDigitLength(l3);
/*  967 */           i1 = k - m;
/*      */         } 
/*  969 */         bigInteger = null;
/*      */       } 
/*      */     } 
/*  972 */     this.intVal = bigInteger;
/*  973 */     this.intCompact = l3;
/*  974 */     this.scale = j;
/*  975 */     this.precision = k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(BigInteger paramBigInteger) {
/*  986 */     this.scale = 0;
/*  987 */     this.intVal = paramBigInteger;
/*  988 */     this.intCompact = compactValFor(paramBigInteger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(BigInteger paramBigInteger, MathContext paramMathContext) {
/* 1004 */     this(paramBigInteger, 0, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(BigInteger paramBigInteger, int paramInt) {
/* 1018 */     this.intVal = paramBigInteger;
/* 1019 */     this.intCompact = compactValFor(paramBigInteger);
/* 1020 */     this.scale = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(BigInteger paramBigInteger, int paramInt, MathContext paramMathContext) {
/* 1039 */     long l = compactValFor(paramBigInteger);
/* 1040 */     int i = paramMathContext.precision;
/* 1041 */     int j = 0;
/* 1042 */     if (i > 0) {
/* 1043 */       int k = paramMathContext.roundingMode.oldMode;
/* 1044 */       if (l == Long.MIN_VALUE) {
/* 1045 */         j = bigDigitLength(paramBigInteger);
/* 1046 */         int m = j - i;
/* 1047 */         while (m > 0) {
/* 1048 */           paramInt = checkScaleNonZero(paramInt - m);
/* 1049 */           paramBigInteger = divideAndRoundByTenPow(paramBigInteger, m, k);
/* 1050 */           l = compactValFor(paramBigInteger);
/* 1051 */           if (l != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/* 1054 */           j = bigDigitLength(paramBigInteger);
/* 1055 */           m = j - i;
/*      */         } 
/*      */       } 
/* 1058 */       if (l != Long.MIN_VALUE) {
/* 1059 */         j = longDigitLength(l);
/* 1060 */         int m = j - i;
/* 1061 */         while (m > 0) {
/* 1062 */           paramInt = checkScaleNonZero(paramInt - m);
/* 1063 */           l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], k);
/* 1064 */           j = longDigitLength(l);
/* 1065 */           m = j - i;
/*      */         } 
/* 1067 */         paramBigInteger = null;
/*      */       } 
/*      */     } 
/* 1070 */     this.intVal = paramBigInteger;
/* 1071 */     this.intCompact = l;
/* 1072 */     this.scale = paramInt;
/* 1073 */     this.precision = j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(int paramInt) {
/* 1085 */     this.intCompact = paramInt;
/* 1086 */     this.scale = 0;
/* 1087 */     this.intVal = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(int paramInt, MathContext paramMathContext) {
/* 1102 */     int i = paramMathContext.precision;
/* 1103 */     long l = paramInt;
/* 1104 */     int j = 0;
/* 1105 */     int k = 0;
/* 1106 */     if (i > 0) {
/* 1107 */       k = longDigitLength(l);
/* 1108 */       int m = k - i;
/* 1109 */       while (m > 0) {
/* 1110 */         j = checkScaleNonZero(j - m);
/* 1111 */         l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], paramMathContext.roundingMode.oldMode);
/* 1112 */         k = longDigitLength(l);
/* 1113 */         m = k - i;
/*      */       } 
/*      */     } 
/* 1116 */     this.intVal = null;
/* 1117 */     this.intCompact = l;
/* 1118 */     this.scale = j;
/* 1119 */     this.precision = k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(long paramLong) {
/* 1130 */     this.intCompact = paramLong;
/* 1131 */     this.intVal = (paramLong == Long.MIN_VALUE) ? INFLATED_BIGINT : null;
/* 1132 */     this.scale = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal(long paramLong, MathContext paramMathContext) {
/* 1147 */     int i = paramMathContext.precision;
/* 1148 */     int j = paramMathContext.roundingMode.oldMode;
/* 1149 */     int k = 0;
/* 1150 */     int m = 0;
/* 1151 */     BigInteger bigInteger = (paramLong == Long.MIN_VALUE) ? INFLATED_BIGINT : null;
/* 1152 */     if (i > 0) {
/* 1153 */       if (paramLong == Long.MIN_VALUE) {
/* 1154 */         k = 19;
/* 1155 */         int n = k - i;
/* 1156 */         while (n > 0) {
/* 1157 */           m = checkScaleNonZero(m - n);
/* 1158 */           bigInteger = divideAndRoundByTenPow(bigInteger, n, j);
/* 1159 */           paramLong = compactValFor(bigInteger);
/* 1160 */           if (paramLong != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/* 1163 */           k = bigDigitLength(bigInteger);
/* 1164 */           n = k - i;
/*      */         } 
/*      */       } 
/* 1167 */       if (paramLong != Long.MIN_VALUE) {
/* 1168 */         k = longDigitLength(paramLong);
/* 1169 */         int n = k - i;
/* 1170 */         while (n > 0) {
/* 1171 */           m = checkScaleNonZero(m - n);
/* 1172 */           paramLong = divideAndRound(paramLong, LONG_TEN_POWERS_TABLE[n], paramMathContext.roundingMode.oldMode);
/* 1173 */           k = longDigitLength(paramLong);
/* 1174 */           n = k - i;
/*      */         } 
/* 1176 */         bigInteger = null;
/*      */       } 
/*      */     } 
/* 1179 */     this.intVal = bigInteger;
/* 1180 */     this.intCompact = paramLong;
/* 1181 */     this.scale = m;
/* 1182 */     this.precision = k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal valueOf(long paramLong, int paramInt) {
/* 1200 */     if (paramInt == 0)
/* 1201 */       return valueOf(paramLong); 
/* 1202 */     if (paramLong == 0L) {
/* 1203 */       return zeroValueOf(paramInt);
/*      */     }
/* 1205 */     return new BigDecimal((paramLong == Long.MIN_VALUE) ? INFLATED_BIGINT : null, paramLong, paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal valueOf(long paramLong) {
/* 1221 */     if (paramLong >= 0L && paramLong < zeroThroughTen.length)
/* 1222 */       return zeroThroughTen[(int)paramLong]; 
/* 1223 */     if (paramLong != Long.MIN_VALUE)
/* 1224 */       return new BigDecimal(null, paramLong, 0, 0); 
/* 1225 */     return new BigDecimal(INFLATED_BIGINT, paramLong, 0, 0);
/*      */   }
/*      */   
/*      */   static BigDecimal valueOf(long paramLong, int paramInt1, int paramInt2) {
/* 1229 */     if (paramInt1 == 0 && paramLong >= 0L && paramLong < zeroThroughTen.length)
/* 1230 */       return zeroThroughTen[(int)paramLong]; 
/* 1231 */     if (paramLong == 0L) {
/* 1232 */       return zeroValueOf(paramInt1);
/*      */     }
/* 1234 */     return new BigDecimal((paramLong == Long.MIN_VALUE) ? INFLATED_BIGINT : null, paramLong, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   static BigDecimal valueOf(BigInteger paramBigInteger, int paramInt1, int paramInt2) {
/* 1239 */     long l = compactValFor(paramBigInteger);
/* 1240 */     if (l == 0L)
/* 1241 */       return zeroValueOf(paramInt1); 
/* 1242 */     if (paramInt1 == 0 && l >= 0L && l < zeroThroughTen.length) {
/* 1243 */       return zeroThroughTen[(int)l];
/*      */     }
/* 1245 */     return new BigDecimal(paramBigInteger, l, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   static BigDecimal zeroValueOf(int paramInt) {
/* 1249 */     if (paramInt >= 0 && paramInt < ZERO_SCALED_BY.length) {
/* 1250 */       return ZERO_SCALED_BY[paramInt];
/*      */     }
/* 1252 */     return new BigDecimal(BigInteger.ZERO, 0L, paramInt, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal valueOf(double paramDouble) {
/* 1277 */     return new BigDecimal(Double.toString(paramDouble));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal add(BigDecimal paramBigDecimal) {
/* 1290 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1291 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1292 */         return add(this.intCompact, this.scale, paramBigDecimal.intCompact, paramBigDecimal.scale);
/*      */       }
/* 1294 */       return add(this.intCompact, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale);
/*      */     } 
/*      */     
/* 1297 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1298 */       return add(paramBigDecimal.intCompact, paramBigDecimal.scale, this.intVal, this.scale);
/*      */     }
/* 1300 */     return add(this.intVal, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal add(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 1320 */     if (paramMathContext.precision == 0)
/* 1321 */       return add(paramBigDecimal); 
/* 1322 */     BigDecimal bigDecimal = this;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1327 */     boolean bool1 = (bigDecimal.signum() == 0) ? true : false;
/* 1328 */     boolean bool2 = (paramBigDecimal.signum() == 0) ? true : false;
/*      */     
/* 1330 */     if (bool1 || bool2) {
/* 1331 */       int i = Math.max(bigDecimal.scale(), paramBigDecimal.scale());
/*      */ 
/*      */       
/* 1334 */       if (bool1 && bool2)
/* 1335 */         return zeroValueOf(i); 
/* 1336 */       BigDecimal bigDecimal1 = bool1 ? doRound(paramBigDecimal, paramMathContext) : doRound(bigDecimal, paramMathContext);
/*      */       
/* 1338 */       if (bigDecimal1.scale() == i)
/* 1339 */         return bigDecimal1; 
/* 1340 */       if (bigDecimal1.scale() > i) {
/* 1341 */         return stripZerosToMatchScale(bigDecimal1.intVal, bigDecimal1.intCompact, bigDecimal1.scale, i);
/*      */       }
/* 1343 */       int j = paramMathContext.precision - bigDecimal1.precision();
/* 1344 */       int k = i - bigDecimal1.scale();
/*      */       
/* 1346 */       if (j >= k) {
/* 1347 */         return bigDecimal1.setScale(i);
/*      */       }
/* 1349 */       return bigDecimal1.setScale(bigDecimal1.scale() + j);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1354 */     long l = bigDecimal.scale - paramBigDecimal.scale;
/* 1355 */     if (l != 0L) {
/* 1356 */       BigDecimal[] arrayOfBigDecimal = preAlign(bigDecimal, paramBigDecimal, l, paramMathContext);
/* 1357 */       matchScale(arrayOfBigDecimal);
/* 1358 */       bigDecimal = arrayOfBigDecimal[0];
/* 1359 */       paramBigDecimal = arrayOfBigDecimal[1];
/*      */     } 
/* 1361 */     return doRound(bigDecimal.inflated().add(paramBigDecimal.inflated()), bigDecimal.scale, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigDecimal[] preAlign(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, long paramLong, MathContext paramMathContext) {
/*      */     BigDecimal bigDecimal1, bigDecimal2;
/* 1387 */     assert paramLong != 0L;
/*      */ 
/*      */ 
/*      */     
/* 1391 */     if (paramLong < 0L) {
/* 1392 */       bigDecimal1 = paramBigDecimal1;
/* 1393 */       bigDecimal2 = paramBigDecimal2;
/*      */     } else {
/* 1395 */       bigDecimal1 = paramBigDecimal2;
/* 1396 */       bigDecimal2 = paramBigDecimal1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1405 */     long l1 = bigDecimal1.scale - bigDecimal1.precision() + paramMathContext.precision;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1416 */     long l2 = bigDecimal2.scale - bigDecimal2.precision() + 1L;
/* 1417 */     if (l2 > (bigDecimal1.scale + 2) && l2 > l1 + 2L)
/*      */     {
/* 1419 */       bigDecimal2 = valueOf(bigDecimal2.signum(), checkScale(Math.max(bigDecimal1.scale, l1) + 3L));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1424 */     return new BigDecimal[] { bigDecimal1, bigDecimal2 };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal subtract(BigDecimal paramBigDecimal) {
/* 1437 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1438 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1439 */         return add(this.intCompact, this.scale, -paramBigDecimal.intCompact, paramBigDecimal.scale);
/*      */       }
/* 1441 */       return add(this.intCompact, this.scale, paramBigDecimal.intVal.negate(), paramBigDecimal.scale);
/*      */     } 
/*      */     
/* 1444 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE)
/*      */     {
/*      */ 
/*      */       
/* 1448 */       return add(-paramBigDecimal.intCompact, paramBigDecimal.scale, this.intVal, this.scale);
/*      */     }
/* 1450 */     return add(this.intVal, this.scale, paramBigDecimal.intVal.negate(), paramBigDecimal.scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal subtract(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 1470 */     if (paramMathContext.precision == 0) {
/* 1471 */       return subtract(paramBigDecimal);
/*      */     }
/* 1473 */     return add(paramBigDecimal.negate(), paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal multiply(BigDecimal paramBigDecimal) {
/* 1485 */     int i = checkScale(this.scale + paramBigDecimal.scale);
/* 1486 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1487 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1488 */         return multiply(this.intCompact, paramBigDecimal.intCompact, i);
/*      */       }
/* 1490 */       return multiply(this.intCompact, paramBigDecimal.intVal, i);
/*      */     } 
/*      */     
/* 1493 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1494 */       return multiply(paramBigDecimal.intCompact, this.intVal, i);
/*      */     }
/* 1496 */     return multiply(this.intVal, paramBigDecimal.intVal, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal multiply(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 1513 */     if (paramMathContext.precision == 0)
/* 1514 */       return multiply(paramBigDecimal); 
/* 1515 */     int i = checkScale(this.scale + paramBigDecimal.scale);
/* 1516 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1517 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1518 */         return multiplyAndRound(this.intCompact, paramBigDecimal.intCompact, i, paramMathContext);
/*      */       }
/* 1520 */       return multiplyAndRound(this.intCompact, paramBigDecimal.intVal, i, paramMathContext);
/*      */     } 
/*      */     
/* 1523 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1524 */       return multiplyAndRound(paramBigDecimal.intCompact, this.intVal, i, paramMathContext);
/*      */     }
/* 1526 */     return multiplyAndRound(this.intVal, paramBigDecimal.intVal, i, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, int paramInt1, int paramInt2) {
/* 1560 */     if (paramInt2 < 0 || paramInt2 > 7)
/* 1561 */       throw new IllegalArgumentException("Invalid rounding mode"); 
/* 1562 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1563 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1564 */         return divide(this.intCompact, this.scale, paramBigDecimal.intCompact, paramBigDecimal.scale, paramInt1, paramInt2);
/*      */       }
/* 1566 */       return divide(this.intCompact, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale, paramInt1, paramInt2);
/*      */     } 
/*      */     
/* 1569 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1570 */       return divide(this.intVal, this.scale, paramBigDecimal.intCompact, paramBigDecimal.scale, paramInt1, paramInt2);
/*      */     }
/* 1572 */     return divide(this.intVal, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, int paramInt, RoundingMode paramRoundingMode) {
/* 1594 */     return divide(paramBigDecimal, paramInt, paramRoundingMode.oldMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, int paramInt) {
/* 1625 */     return divide(paramBigDecimal, this.scale, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, RoundingMode paramRoundingMode) {
/* 1644 */     return divide(paramBigDecimal, this.scale, paramRoundingMode.oldMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal) {
/*      */     BigDecimal bigDecimal;
/* 1665 */     if (paramBigDecimal.signum() == 0) {
/* 1666 */       if (signum() == 0)
/* 1667 */         throw new ArithmeticException("Division undefined"); 
/* 1668 */       throw new ArithmeticException("Division by zero");
/*      */     } 
/*      */ 
/*      */     
/* 1672 */     int i = saturateLong(this.scale - paramBigDecimal.scale);
/*      */     
/* 1674 */     if (signum() == 0) {
/* 1675 */       return zeroValueOf(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1685 */     MathContext mathContext = new MathContext((int)Math.min(precision() + 
/* 1686 */           (long)Math.ceil(10.0D * paramBigDecimal.precision() / 3.0D), 2147483647L), RoundingMode.UNNECESSARY);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1691 */       bigDecimal = divide(paramBigDecimal, mathContext);
/* 1692 */     } catch (ArithmeticException arithmeticException) {
/* 1693 */       throw new ArithmeticException("Non-terminating decimal expansion; no exact representable decimal result.");
/*      */     } 
/*      */ 
/*      */     
/* 1697 */     int j = bigDecimal.scale();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1703 */     if (i > j) {
/* 1704 */       return bigDecimal.setScale(i, 7);
/*      */     }
/* 1706 */     return bigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 1724 */     int i = paramMathContext.precision;
/* 1725 */     if (i == 0) {
/* 1726 */       return divide(paramBigDecimal);
/*      */     }
/* 1728 */     BigDecimal bigDecimal = this;
/* 1729 */     long l = bigDecimal.scale - paramBigDecimal.scale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1742 */     if (paramBigDecimal.signum() == 0) {
/* 1743 */       if (bigDecimal.signum() == 0)
/* 1744 */         throw new ArithmeticException("Division undefined"); 
/* 1745 */       throw new ArithmeticException("Division by zero");
/*      */     } 
/* 1747 */     if (bigDecimal.signum() == 0)
/* 1748 */       return zeroValueOf(saturateLong(l)); 
/* 1749 */     int j = bigDecimal.precision();
/* 1750 */     int k = paramBigDecimal.precision();
/* 1751 */     if (bigDecimal.intCompact != Long.MIN_VALUE) {
/* 1752 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1753 */         return divide(bigDecimal.intCompact, j, paramBigDecimal.intCompact, k, l, paramMathContext);
/*      */       }
/* 1755 */       return divide(bigDecimal.intCompact, j, paramBigDecimal.intVal, k, l, paramMathContext);
/*      */     } 
/*      */     
/* 1758 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1759 */       return divide(bigDecimal.intVal, j, paramBigDecimal.intCompact, k, l, paramMathContext);
/*      */     }
/* 1761 */     return divide(bigDecimal.intVal, j, paramBigDecimal.intVal, k, l, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divideToIntegralValue(BigDecimal paramBigDecimal) {
/* 1779 */     int i = saturateLong(this.scale - paramBigDecimal.scale);
/* 1780 */     if (compareMagnitude(paramBigDecimal) < 0)
/*      */     {
/* 1782 */       return zeroValueOf(i);
/*      */     }
/*      */     
/* 1785 */     if (signum() == 0 && paramBigDecimal.signum() != 0) {
/* 1786 */       return setScale(i, 7);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1791 */     int j = (int)Math.min(precision() + 
/* 1792 */         (long)Math.ceil(10.0D * paramBigDecimal.precision() / 3.0D) + 
/* 1793 */         Math.abs(scale() - paramBigDecimal.scale()) + 2L, 2147483647L);
/*      */     
/* 1795 */     BigDecimal bigDecimal = divide(paramBigDecimal, new MathContext(j, RoundingMode.DOWN));
/*      */     
/* 1797 */     if (bigDecimal.scale > 0) {
/* 1798 */       bigDecimal = bigDecimal.setScale(0, RoundingMode.DOWN);
/* 1799 */       bigDecimal = stripZerosToMatchScale(bigDecimal.intVal, bigDecimal.intCompact, bigDecimal.scale, i);
/*      */     } 
/*      */     
/* 1802 */     if (bigDecimal.scale < i)
/*      */     {
/* 1804 */       bigDecimal = bigDecimal.setScale(i, 7);
/*      */     }
/*      */     
/* 1807 */     return bigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal divideToIntegralValue(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 1831 */     if (paramMathContext.precision == 0 || 
/* 1832 */       compareMagnitude(paramBigDecimal) < 0) {
/* 1833 */       return divideToIntegralValue(paramBigDecimal);
/*      */     }
/*      */     
/* 1836 */     int i = saturateLong(this.scale - paramBigDecimal.scale);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1845 */     BigDecimal bigDecimal = divide(paramBigDecimal, new MathContext(paramMathContext.precision, RoundingMode.DOWN));
/*      */     
/* 1847 */     if (bigDecimal.scale() < 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1853 */       BigDecimal bigDecimal1 = bigDecimal.multiply(paramBigDecimal);
/*      */ 
/*      */       
/* 1856 */       if (subtract(bigDecimal1).compareMagnitude(paramBigDecimal) >= 0) {
/* 1857 */         throw new ArithmeticException("Division impossible");
/*      */       }
/* 1859 */     } else if (bigDecimal.scale() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1865 */       bigDecimal = bigDecimal.setScale(0, RoundingMode.DOWN);
/*      */     } 
/*      */     
/*      */     int j;
/*      */     
/* 1870 */     if (i > bigDecimal.scale() && (
/* 1871 */       j = paramMathContext.precision - bigDecimal.precision()) > 0) {
/* 1872 */       return bigDecimal.setScale(bigDecimal.scale() + 
/* 1873 */           Math.min(j, i - bigDecimal.scale));
/*      */     }
/* 1875 */     return stripZerosToMatchScale(bigDecimal.intVal, bigDecimal.intCompact, bigDecimal.scale, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal remainder(BigDecimal paramBigDecimal) {
/* 1893 */     BigDecimal[] arrayOfBigDecimal = divideAndRemainder(paramBigDecimal);
/* 1894 */     return arrayOfBigDecimal[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal remainder(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 1923 */     BigDecimal[] arrayOfBigDecimal = divideAndRemainder(paramBigDecimal, paramMathContext);
/* 1924 */     return arrayOfBigDecimal[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal[] divideAndRemainder(BigDecimal paramBigDecimal) {
/* 1949 */     BigDecimal[] arrayOfBigDecimal = new BigDecimal[2];
/*      */     
/* 1951 */     arrayOfBigDecimal[0] = divideToIntegralValue(paramBigDecimal);
/* 1952 */     arrayOfBigDecimal[1] = subtract(arrayOfBigDecimal[0].multiply(paramBigDecimal));
/* 1953 */     return arrayOfBigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal[] divideAndRemainder(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 1983 */     if (paramMathContext.precision == 0) {
/* 1984 */       return divideAndRemainder(paramBigDecimal);
/*      */     }
/* 1986 */     BigDecimal[] arrayOfBigDecimal = new BigDecimal[2];
/* 1987 */     BigDecimal bigDecimal = this;
/*      */     
/* 1989 */     arrayOfBigDecimal[0] = bigDecimal.divideToIntegralValue(paramBigDecimal, paramMathContext);
/* 1990 */     arrayOfBigDecimal[1] = bigDecimal.subtract(arrayOfBigDecimal[0].multiply(paramBigDecimal));
/* 1991 */     return arrayOfBigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal pow(int paramInt) {
/* 2012 */     if (paramInt < 0 || paramInt > 999999999) {
/* 2013 */       throw new ArithmeticException("Invalid operation");
/*      */     }
/*      */     
/* 2016 */     int i = checkScale(this.scale * paramInt);
/* 2017 */     return new BigDecimal(inflated().pow(paramInt), i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal pow(int paramInt, MathContext paramMathContext) {
/* 2072 */     if (paramMathContext.precision == 0)
/* 2073 */       return pow(paramInt); 
/* 2074 */     if (paramInt < -999999999 || paramInt > 999999999)
/* 2075 */       throw new ArithmeticException("Invalid operation"); 
/* 2076 */     if (paramInt == 0)
/* 2077 */       return ONE; 
/* 2078 */     BigDecimal bigDecimal1 = this;
/* 2079 */     MathContext mathContext = paramMathContext;
/* 2080 */     int i = Math.abs(paramInt);
/* 2081 */     if (paramMathContext.precision > 0) {
/* 2082 */       int j = longDigitLength(i);
/* 2083 */       if (j > paramMathContext.precision)
/* 2084 */         throw new ArithmeticException("Invalid operation"); 
/* 2085 */       mathContext = new MathContext(paramMathContext.precision + j + 1, paramMathContext.roundingMode);
/*      */     } 
/*      */ 
/*      */     
/* 2089 */     BigDecimal bigDecimal2 = ONE;
/* 2090 */     boolean bool = false;
/* 2091 */     for (byte b = 1;; b++) {
/* 2092 */       i += i;
/* 2093 */       if (i < 0) {
/* 2094 */         bool = true;
/* 2095 */         bigDecimal2 = bigDecimal2.multiply(bigDecimal1, mathContext);
/*      */       } 
/* 2097 */       if (b == 31)
/*      */         break; 
/* 2099 */       if (bool) {
/* 2100 */         bigDecimal2 = bigDecimal2.multiply(bigDecimal2, mathContext);
/*      */       }
/*      */     } 
/*      */     
/* 2104 */     if (paramInt < 0) {
/* 2105 */       bigDecimal2 = ONE.divide(bigDecimal2, mathContext);
/*      */     }
/* 2107 */     return doRound(bigDecimal2, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal abs() {
/* 2118 */     return (signum() < 0) ? negate() : this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal abs(MathContext paramMathContext) {
/* 2133 */     return (signum() < 0) ? negate(paramMathContext) : plus(paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal negate() {
/* 2143 */     if (this.intCompact == Long.MIN_VALUE) {
/* 2144 */       return new BigDecimal(this.intVal.negate(), Long.MIN_VALUE, this.scale, this.precision);
/*      */     }
/* 2146 */     return valueOf(-this.intCompact, this.scale, this.precision);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal negate(MathContext paramMathContext) {
/* 2161 */     return negate().plus(paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal plus() {
/* 2177 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal plus(MathContext paramMathContext) {
/* 2196 */     if (paramMathContext.precision == 0)
/* 2197 */       return this; 
/* 2198 */     return doRound(this, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int signum() {
/* 2208 */     return (this.intCompact != Long.MIN_VALUE) ? 
/* 2209 */       Long.signum(this.intCompact) : this.intVal
/* 2210 */       .signum();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int scale() {
/* 2224 */     return this.scale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int precision() {
/* 2237 */     int i = this.precision;
/* 2238 */     if (i == 0) {
/* 2239 */       long l = this.intCompact;
/* 2240 */       if (l != Long.MIN_VALUE) {
/* 2241 */         i = longDigitLength(l);
/*      */       } else {
/* 2243 */         i = bigDigitLength(this.intVal);
/* 2244 */       }  this.precision = i;
/*      */     } 
/* 2246 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger unscaledValue() {
/* 2259 */     return inflated();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal round(MathContext paramMathContext) {
/* 2356 */     return plus(paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal setScale(int paramInt, RoundingMode paramRoundingMode) {
/* 2389 */     return setScale(paramInt, paramRoundingMode.oldMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal setScale(int paramInt1, int paramInt2) {
/* 2433 */     if (paramInt2 < 0 || paramInt2 > 7) {
/* 2434 */       throw new IllegalArgumentException("Invalid rounding mode");
/*      */     }
/* 2436 */     int i = this.scale;
/* 2437 */     if (paramInt1 == i)
/* 2438 */       return this; 
/* 2439 */     if (signum() == 0)
/* 2440 */       return zeroValueOf(paramInt1); 
/* 2441 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2442 */       long l = this.intCompact;
/* 2443 */       if (paramInt1 > i) {
/* 2444 */         int m = checkScale(paramInt1 - i);
/* 2445 */         if ((l = longMultiplyPowerTen(l, m)) != Long.MIN_VALUE) {
/* 2446 */           return valueOf(l, paramInt1);
/*      */         }
/* 2448 */         BigInteger bigInteger = bigMultiplyPowerTen(m);
/* 2449 */         return new BigDecimal(bigInteger, Long.MIN_VALUE, paramInt1, (this.precision > 0) ? (this.precision + m) : 0);
/*      */       } 
/*      */ 
/*      */       
/* 2453 */       int k = checkScale(i - paramInt1);
/* 2454 */       if (k < LONG_TEN_POWERS_TABLE.length) {
/* 2455 */         return divideAndRound(l, LONG_TEN_POWERS_TABLE[k], paramInt1, paramInt2, paramInt1);
/*      */       }
/* 2457 */       return divideAndRound(inflated(), bigTenToThe(k), paramInt1, paramInt2, paramInt1);
/*      */     } 
/*      */ 
/*      */     
/* 2461 */     if (paramInt1 > i) {
/* 2462 */       int k = checkScale(paramInt1 - i);
/* 2463 */       BigInteger bigInteger = bigMultiplyPowerTen(this.intVal, k);
/* 2464 */       return new BigDecimal(bigInteger, Long.MIN_VALUE, paramInt1, (this.precision > 0) ? (this.precision + k) : 0);
/*      */     } 
/*      */ 
/*      */     
/* 2468 */     int j = checkScale(i - paramInt1);
/* 2469 */     if (j < LONG_TEN_POWERS_TABLE.length) {
/* 2470 */       return divideAndRound(this.intVal, LONG_TEN_POWERS_TABLE[j], paramInt1, paramInt2, paramInt1);
/*      */     }
/*      */     
/* 2473 */     return divideAndRound(this.intVal, bigTenToThe(j), paramInt1, paramInt2, paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal setScale(int paramInt) {
/* 2515 */     return setScale(paramInt, 7);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal movePointLeft(int paramInt) {
/* 2537 */     int i = checkScale(this.scale + paramInt);
/* 2538 */     BigDecimal bigDecimal = new BigDecimal(this.intVal, this.intCompact, i, 0);
/* 2539 */     return (bigDecimal.scale < 0) ? bigDecimal.setScale(0, 7) : bigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal movePointRight(int paramInt) {
/* 2559 */     int i = checkScale(this.scale - paramInt);
/* 2560 */     BigDecimal bigDecimal = new BigDecimal(this.intVal, this.intCompact, i, 0);
/* 2561 */     return (bigDecimal.scale < 0) ? bigDecimal.setScale(0, 7) : bigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal scaleByPowerOfTen(int paramInt) {
/* 2578 */     return new BigDecimal(this.intVal, this.intCompact, 
/* 2579 */         checkScale(this.scale - paramInt), this.precision);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal stripTrailingZeros() {
/* 2598 */     if (this.intCompact == 0L || (this.intVal != null && this.intVal.signum() == 0))
/* 2599 */       return ZERO; 
/* 2600 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2601 */       return createAndStripZerosToMatchScale(this.intCompact, this.scale, Long.MIN_VALUE);
/*      */     }
/* 2603 */     return createAndStripZerosToMatchScale(this.intVal, this.scale, Long.MIN_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(BigDecimal paramBigDecimal) {
/* 2628 */     if (this.scale == paramBigDecimal.scale) {
/* 2629 */       long l1 = this.intCompact;
/* 2630 */       long l2 = paramBigDecimal.intCompact;
/* 2631 */       if (l1 != Long.MIN_VALUE && l2 != Long.MIN_VALUE)
/* 2632 */         return (l1 != l2) ? ((l1 > l2) ? 1 : -1) : 0; 
/*      */     } 
/* 2634 */     int i = signum();
/* 2635 */     int j = paramBigDecimal.signum();
/* 2636 */     if (i != j)
/* 2637 */       return (i > j) ? 1 : -1; 
/* 2638 */     if (i == 0)
/* 2639 */       return 0; 
/* 2640 */     int k = compareMagnitude(paramBigDecimal);
/* 2641 */     return (i > 0) ? k : -k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int compareMagnitude(BigDecimal paramBigDecimal) {
/* 2649 */     long l1 = paramBigDecimal.intCompact;
/* 2650 */     long l2 = this.intCompact;
/* 2651 */     if (l2 == 0L)
/* 2652 */       return (l1 == 0L) ? 0 : -1; 
/* 2653 */     if (l1 == 0L) {
/* 2654 */       return 1;
/*      */     }
/* 2656 */     long l3 = this.scale - paramBigDecimal.scale;
/* 2657 */     if (l3 != 0L) {
/*      */       
/* 2659 */       long l4 = precision() - this.scale;
/* 2660 */       long l5 = paramBigDecimal.precision() - paramBigDecimal.scale;
/* 2661 */       if (l4 < l5)
/* 2662 */         return -1; 
/* 2663 */       if (l4 > l5)
/* 2664 */         return 1; 
/* 2665 */       BigInteger bigInteger = null;
/* 2666 */       if (l3 < 0L) {
/*      */         
/* 2668 */         if (l3 > -2147483648L && (l2 == Long.MIN_VALUE || (
/*      */           
/* 2670 */           l2 = longMultiplyPowerTen(l2, (int)-l3)) == Long.MIN_VALUE) && l1 == Long.MIN_VALUE)
/*      */         {
/* 2672 */           bigInteger = bigMultiplyPowerTen((int)-l3);
/* 2673 */           return bigInteger.compareMagnitude(paramBigDecimal.intVal);
/*      */         }
/*      */       
/*      */       }
/* 2677 */       else if (l3 <= 2147483647L && (l1 == Long.MIN_VALUE || (
/*      */         
/* 2679 */         l1 = longMultiplyPowerTen(l1, (int)l3)) == Long.MIN_VALUE) && l2 == Long.MIN_VALUE) {
/*      */         
/* 2681 */         bigInteger = paramBigDecimal.bigMultiplyPowerTen((int)l3);
/* 2682 */         return this.intVal.compareMagnitude(bigInteger);
/*      */       } 
/*      */     } 
/*      */     
/* 2686 */     if (l2 != Long.MIN_VALUE)
/* 2687 */       return (l1 != Long.MIN_VALUE) ? longCompareMagnitude(l2, l1) : -1; 
/* 2688 */     if (l1 != Long.MIN_VALUE) {
/* 2689 */       return 1;
/*      */     }
/* 2691 */     return this.intVal.compareMagnitude(paramBigDecimal.intVal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 2712 */     if (!(paramObject instanceof BigDecimal))
/* 2713 */       return false; 
/* 2714 */     BigDecimal bigDecimal = (BigDecimal)paramObject;
/* 2715 */     if (paramObject == this)
/* 2716 */       return true; 
/* 2717 */     if (this.scale != bigDecimal.scale)
/* 2718 */       return false; 
/* 2719 */     long l1 = this.intCompact;
/* 2720 */     long l2 = bigDecimal.intCompact;
/* 2721 */     if (l1 != Long.MIN_VALUE) {
/* 2722 */       if (l2 == Long.MIN_VALUE)
/* 2723 */         l2 = compactValFor(bigDecimal.intVal); 
/* 2724 */       return (l2 == l1);
/* 2725 */     }  if (l2 != Long.MIN_VALUE) {
/* 2726 */       return (l2 == compactValFor(this.intVal));
/*      */     }
/* 2728 */     return inflated().equals(bigDecimal.inflated());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal min(BigDecimal paramBigDecimal) {
/* 2743 */     return (compareTo(paramBigDecimal) <= 0) ? this : paramBigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal max(BigDecimal paramBigDecimal) {
/* 2757 */     return (compareTo(paramBigDecimal) >= 0) ? this : paramBigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2773 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2774 */       long l = (this.intCompact < 0L) ? -this.intCompact : this.intCompact;
/* 2775 */       int i = (int)(((int)(l >>> 32L) * 31) + (l & 0xFFFFFFFFL));
/*      */       
/* 2777 */       return 31 * ((this.intCompact < 0L) ? -i : i) + this.scale;
/*      */     } 
/* 2779 */     return 31 * this.intVal.hashCode() + this.scale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2885 */     String str = this.stringCache;
/* 2886 */     if (str == null)
/* 2887 */       this.stringCache = str = layoutChars(true); 
/* 2888 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toEngineeringString() {
/* 2916 */     return layoutChars(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toPlainString() {
/*      */     String str;
/* 2953 */     if (this.scale == 0) {
/* 2954 */       if (this.intCompact != Long.MIN_VALUE) {
/* 2955 */         return Long.toString(this.intCompact);
/*      */       }
/* 2957 */       return this.intVal.toString();
/*      */     } 
/*      */     
/* 2960 */     if (this.scale < 0) {
/* 2961 */       StringBuilder stringBuilder; if (signum() == 0) {
/* 2962 */         return "0";
/*      */       }
/* 2964 */       int i = checkScaleNonZero(-(this.scale));
/*      */       
/* 2966 */       if (this.intCompact != Long.MIN_VALUE) {
/* 2967 */         stringBuilder = new StringBuilder(20 + i);
/* 2968 */         stringBuilder.append(this.intCompact);
/*      */       } else {
/* 2970 */         String str1 = this.intVal.toString();
/* 2971 */         stringBuilder = new StringBuilder(str1.length() + i);
/* 2972 */         stringBuilder.append(str1);
/*      */       } 
/* 2974 */       for (byte b = 0; b < i; b++)
/* 2975 */         stringBuilder.append('0'); 
/* 2976 */       return stringBuilder.toString();
/*      */     } 
/*      */     
/* 2979 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2980 */       str = Long.toString(Math.abs(this.intCompact));
/*      */     } else {
/* 2982 */       str = this.intVal.abs().toString();
/*      */     } 
/* 2984 */     return getValueString(signum(), str, this.scale);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getValueString(int paramInt1, String paramString, int paramInt2) {
/*      */     StringBuilder stringBuilder;
/* 2991 */     int i = paramString.length() - paramInt2;
/* 2992 */     if (i == 0)
/* 2993 */       return ((paramInt1 < 0) ? "-0." : "0.") + paramString; 
/* 2994 */     if (i > 0) {
/* 2995 */       stringBuilder = new StringBuilder(paramString);
/* 2996 */       stringBuilder.insert(i, '.');
/* 2997 */       if (paramInt1 < 0)
/* 2998 */         stringBuilder.insert(0, '-'); 
/*      */     } else {
/* 3000 */       stringBuilder = new StringBuilder(3 - i + paramString.length());
/* 3001 */       stringBuilder.append((paramInt1 < 0) ? "-0." : "0.");
/* 3002 */       for (byte b = 0; b < -i; b++)
/* 3003 */         stringBuilder.append('0'); 
/* 3004 */       stringBuilder.append(paramString);
/*      */     } 
/* 3006 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger toBigInteger() {
/* 3028 */     return setScale(0, 1).inflated();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger toBigIntegerExact() {
/* 3043 */     return setScale(0, 7).inflated();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long longValue() {
/* 3063 */     if (this.intCompact != Long.MIN_VALUE && this.scale == 0) {
/* 3064 */       return this.intCompact;
/*      */     }
/*      */     
/* 3067 */     if (signum() == 0 || fractionOnly() || this.scale <= -64)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3075 */       return 0L;
/*      */     }
/* 3077 */     return toBigInteger().longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fractionOnly() {
/* 3087 */     assert signum() != 0;
/* 3088 */     return (precision() - this.scale <= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long longValueExact() {
/* 3104 */     if (this.intCompact != Long.MIN_VALUE && this.scale == 0) {
/* 3105 */       return this.intCompact;
/*      */     }
/*      */     
/* 3108 */     if (signum() == 0) {
/* 3109 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/* 3113 */     if (fractionOnly()) {
/* 3114 */       throw new ArithmeticException("Rounding necessary");
/*      */     }
/*      */     
/* 3117 */     if (precision() - this.scale > 19) {
/* 3118 */       throw new ArithmeticException("Overflow");
/*      */     }
/*      */     
/* 3121 */     BigDecimal bigDecimal = setScale(0, 7);
/* 3122 */     if (bigDecimal.precision() >= 19)
/* 3123 */       LongOverflow.check(bigDecimal); 
/* 3124 */     return bigDecimal.inflated().longValue();
/*      */   }
/*      */   
/*      */   private static class LongOverflow
/*      */   {
/* 3129 */     private static final BigInteger LONGMIN = BigInteger.valueOf(Long.MIN_VALUE);
/*      */ 
/*      */     
/* 3132 */     private static final BigInteger LONGMAX = BigInteger.valueOf(Long.MAX_VALUE);
/*      */     
/*      */     public static void check(BigDecimal param1BigDecimal) {
/* 3135 */       BigInteger bigInteger = param1BigDecimal.inflated();
/* 3136 */       if (bigInteger.compareTo(LONGMIN) < 0 || bigInteger
/* 3137 */         .compareTo(LONGMAX) > 0) {
/* 3138 */         throw new ArithmeticException("Overflow");
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
/*      */   public int intValue() {
/* 3159 */     return (this.intCompact != Long.MIN_VALUE && this.scale == 0) ? (int)this.intCompact : 
/*      */       
/* 3161 */       (int)longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int intValueExact() {
/* 3178 */     long l = longValueExact();
/* 3179 */     if ((int)l != l)
/* 3180 */       throw new ArithmeticException("Overflow"); 
/* 3181 */     return (int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short shortValueExact() {
/* 3198 */     long l = longValueExact();
/* 3199 */     if ((short)(int)l != l)
/* 3200 */       throw new ArithmeticException("Overflow"); 
/* 3201 */     return (short)(int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte byteValueExact() {
/* 3218 */     long l = longValueExact();
/* 3219 */     if ((byte)(int)l != l)
/* 3220 */       throw new ArithmeticException("Overflow"); 
/* 3221 */     return (byte)(int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float floatValue() {
/* 3241 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3242 */       if (this.scale == 0) {
/* 3243 */         return (float)this.intCompact;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3251 */       if (Math.abs(this.intCompact) < 4194304L) {
/*      */ 
/*      */ 
/*      */         
/* 3255 */         if (this.scale > 0 && this.scale < float10pow.length)
/* 3256 */           return (float)this.intCompact / float10pow[this.scale]; 
/* 3257 */         if (this.scale < 0 && this.scale > -float10pow.length) {
/* 3258 */           return (float)this.intCompact * float10pow[-this.scale];
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3264 */     return Float.parseFloat(toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double doubleValue() {
/* 3284 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3285 */       if (this.scale == 0) {
/* 3286 */         return this.intCompact;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3294 */       if (Math.abs(this.intCompact) < 4503599627370496L) {
/*      */ 
/*      */ 
/*      */         
/* 3298 */         if (this.scale > 0 && this.scale < double10pow.length)
/* 3299 */           return this.intCompact / double10pow[this.scale]; 
/* 3300 */         if (this.scale < 0 && this.scale > -double10pow.length) {
/* 3301 */           return this.intCompact * double10pow[-this.scale];
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3307 */     return Double.parseDouble(toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3314 */   private static final double[] double10pow = new double[] { 1.0D, 10.0D, 100.0D, 1000.0D, 10000.0D, 100000.0D, 1000000.0D, 1.0E7D, 1.0E8D, 1.0E9D, 1.0E10D, 1.0E11D, 1.0E12D, 1.0E13D, 1.0E14D, 1.0E15D, 1.0E16D, 1.0E17D, 1.0E18D, 1.0E19D, 1.0E20D, 1.0E21D, 1.0E22D };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3325 */   private static final float[] float10pow = new float[] { 1.0F, 10.0F, 100.0F, 1000.0F, 10000.0F, 100000.0F, 1000000.0F, 1.0E7F, 1.0E8F, 1.0E9F, 1.0E10F };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal ulp() {
/* 3345 */     return valueOf(1L, scale(), 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class StringBuilderHelper
/*      */   {
/* 3360 */     final StringBuilder sb = new StringBuilder();
/*      */     
/* 3362 */     final char[] cmpCharArray = new char[19];
/*      */ 
/*      */ 
/*      */     
/*      */     StringBuilder getStringBuilder() {
/* 3367 */       this.sb.setLength(0);
/* 3368 */       return this.sb;
/*      */     }
/*      */     
/*      */     char[] getCompactCharArray() {
/* 3372 */       return this.cmpCharArray;
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
/*      */     int putIntCompact(long param1Long) {
/* 3385 */       assert param1Long >= 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3391 */       int i = this.cmpCharArray.length;
/*      */ 
/*      */       
/* 3394 */       while (param1Long > 2147483647L) {
/* 3395 */         long l = param1Long / 100L;
/* 3396 */         int k = (int)(param1Long - l * 100L);
/* 3397 */         param1Long = l;
/* 3398 */         this.cmpCharArray[--i] = DIGIT_ONES[k];
/* 3399 */         this.cmpCharArray[--i] = DIGIT_TENS[k];
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3404 */       int j = (int)param1Long;
/* 3405 */       while (j >= 100) {
/* 3406 */         int m = j / 100;
/* 3407 */         int k = j - m * 100;
/* 3408 */         j = m;
/* 3409 */         this.cmpCharArray[--i] = DIGIT_ONES[k];
/* 3410 */         this.cmpCharArray[--i] = DIGIT_TENS[k];
/*      */       } 
/*      */       
/* 3413 */       this.cmpCharArray[--i] = DIGIT_ONES[j];
/* 3414 */       if (j >= 10) {
/* 3415 */         this.cmpCharArray[--i] = DIGIT_TENS[j];
/*      */       }
/* 3417 */       return i;
/*      */     }
/*      */     
/* 3420 */     static final char[] DIGIT_TENS = new char[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3433 */     static final char[] DIGIT_ONES = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String layoutChars(boolean paramBoolean) {
/*      */     char[] arrayOfChar;
/*      */     byte b;
/* 3457 */     if (this.scale == 0)
/* 3458 */       return (this.intCompact != Long.MIN_VALUE) ? 
/* 3459 */         Long.toString(this.intCompact) : this.intVal
/* 3460 */         .toString(); 
/* 3461 */     if (this.scale == 2 && this.intCompact >= 0L && this.intCompact < 2147483647L) {
/*      */ 
/*      */       
/* 3464 */       int j = (int)this.intCompact % 100;
/* 3465 */       int k = (int)this.intCompact / 100;
/* 3466 */       return Integer.toString(k) + '.' + StringBuilderHelper.DIGIT_TENS[j] + StringBuilderHelper.DIGIT_ONES[j];
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3471 */     StringBuilderHelper stringBuilderHelper = threadLocalStringBuilderHelper.get();
/*      */ 
/*      */ 
/*      */     
/* 3475 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3476 */       b = stringBuilderHelper.putIntCompact(Math.abs(this.intCompact));
/* 3477 */       arrayOfChar = stringBuilderHelper.getCompactCharArray();
/*      */     } else {
/* 3479 */       b = 0;
/* 3480 */       arrayOfChar = this.intVal.abs().toString().toCharArray();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3487 */     StringBuilder stringBuilder = stringBuilderHelper.getStringBuilder();
/* 3488 */     if (signum() < 0)
/* 3489 */       stringBuilder.append('-'); 
/* 3490 */     int i = arrayOfChar.length - b;
/* 3491 */     long l = -(this.scale) + (i - 1);
/* 3492 */     if (this.scale >= 0 && l >= -6L) {
/* 3493 */       int j = this.scale - i;
/* 3494 */       if (j >= 0) {
/* 3495 */         stringBuilder.append('0');
/* 3496 */         stringBuilder.append('.');
/* 3497 */         for (; j > 0; j--) {
/* 3498 */           stringBuilder.append('0');
/*      */         }
/* 3500 */         stringBuilder.append(arrayOfChar, b, i);
/*      */       } else {
/* 3502 */         stringBuilder.append(arrayOfChar, b, -j);
/* 3503 */         stringBuilder.append('.');
/* 3504 */         stringBuilder.append(arrayOfChar, -j + b, this.scale);
/*      */       } 
/*      */     } else {
/* 3507 */       if (paramBoolean) {
/* 3508 */         stringBuilder.append(arrayOfChar[b]);
/* 3509 */         if (i > 1) {
/* 3510 */           stringBuilder.append('.');
/* 3511 */           stringBuilder.append(arrayOfChar, b + 1, i - 1);
/*      */         } 
/*      */       } else {
/* 3514 */         int j = (int)(l % 3L);
/* 3515 */         if (j < 0)
/* 3516 */           j += 3; 
/* 3517 */         l -= j;
/* 3518 */         j++;
/* 3519 */         if (signum() == 0) {
/* 3520 */           switch (j) {
/*      */             case 1:
/* 3522 */               stringBuilder.append('0');
/*      */               break;
/*      */             case 2:
/* 3525 */               stringBuilder.append("0.00");
/* 3526 */               l += 3L;
/*      */               break;
/*      */             case 3:
/* 3529 */               stringBuilder.append("0.0");
/* 3530 */               l += 3L;
/*      */               break;
/*      */             default:
/* 3533 */               throw new AssertionError("Unexpected sig value " + j);
/*      */           } 
/* 3535 */         } else if (j >= i) {
/* 3536 */           stringBuilder.append(arrayOfChar, b, i);
/*      */           
/* 3538 */           for (int k = j - i; k > 0; k--)
/* 3539 */             stringBuilder.append('0'); 
/*      */         } else {
/* 3541 */           stringBuilder.append(arrayOfChar, b, j);
/* 3542 */           stringBuilder.append('.');
/* 3543 */           stringBuilder.append(arrayOfChar, b + j, i - j);
/*      */         } 
/*      */       } 
/* 3546 */       if (l != 0L) {
/* 3547 */         stringBuilder.append('E');
/* 3548 */         if (l > 0L)
/* 3549 */           stringBuilder.append('+'); 
/* 3550 */         stringBuilder.append(l);
/*      */       } 
/*      */     } 
/* 3553 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger bigTenToThe(int paramInt) {
/* 3563 */     if (paramInt < 0) {
/* 3564 */       return BigInteger.ZERO;
/*      */     }
/* 3566 */     if (paramInt < BIG_TEN_POWERS_TABLE_MAX) {
/* 3567 */       BigInteger[] arrayOfBigInteger = BIG_TEN_POWERS_TABLE;
/* 3568 */       if (paramInt < arrayOfBigInteger.length) {
/* 3569 */         return arrayOfBigInteger[paramInt];
/*      */       }
/* 3571 */       return expandBigIntegerTenPowers(paramInt);
/*      */     } 
/*      */     
/* 3574 */     return BigInteger.TEN.pow(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger expandBigIntegerTenPowers(int paramInt) {
/* 3586 */     synchronized (BigDecimal.class) {
/* 3587 */       BigInteger[] arrayOfBigInteger = BIG_TEN_POWERS_TABLE;
/* 3588 */       int i = arrayOfBigInteger.length;
/*      */ 
/*      */       
/* 3591 */       if (i <= paramInt) {
/* 3592 */         int j = i << 1;
/* 3593 */         while (j <= paramInt)
/* 3594 */           j <<= 1; 
/* 3595 */         arrayOfBigInteger = Arrays.<BigInteger>copyOf(arrayOfBigInteger, j);
/* 3596 */         for (int k = i; k < j; k++) {
/* 3597 */           arrayOfBigInteger[k] = arrayOfBigInteger[k - 1].multiply(BigInteger.TEN);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3602 */         BIG_TEN_POWERS_TABLE = arrayOfBigInteger;
/*      */       } 
/* 3604 */       return arrayOfBigInteger[paramInt];
/*      */     } 
/*      */   }
/*      */   
/* 3608 */   private static final long[] LONG_TEN_POWERS_TABLE = new long[] { 1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3630 */   private static volatile BigInteger[] BIG_TEN_POWERS_TABLE = new BigInteger[] { BigInteger.ONE, 
/*      */       
/* 3632 */       BigInteger.valueOf(10L), 
/* 3633 */       BigInteger.valueOf(100L), 
/* 3634 */       BigInteger.valueOf(1000L), 
/* 3635 */       BigInteger.valueOf(10000L), 
/* 3636 */       BigInteger.valueOf(100000L), 
/* 3637 */       BigInteger.valueOf(1000000L), 
/* 3638 */       BigInteger.valueOf(10000000L), 
/* 3639 */       BigInteger.valueOf(100000000L), 
/* 3640 */       BigInteger.valueOf(1000000000L), 
/* 3641 */       BigInteger.valueOf(10000000000L), 
/* 3642 */       BigInteger.valueOf(100000000000L), 
/* 3643 */       BigInteger.valueOf(1000000000000L), 
/* 3644 */       BigInteger.valueOf(10000000000000L), 
/* 3645 */       BigInteger.valueOf(100000000000000L), 
/* 3646 */       BigInteger.valueOf(1000000000000000L), 
/* 3647 */       BigInteger.valueOf(10000000000000000L), 
/* 3648 */       BigInteger.valueOf(100000000000000000L), 
/* 3649 */       BigInteger.valueOf(1000000000000000000L) };
/*      */ 
/*      */   
/* 3652 */   private static final int BIG_TEN_POWERS_TABLE_INITLEN = BIG_TEN_POWERS_TABLE.length;
/*      */   
/* 3654 */   private static final int BIG_TEN_POWERS_TABLE_MAX = 16 * BIG_TEN_POWERS_TABLE_INITLEN;
/*      */ 
/*      */   
/* 3657 */   private static final long[] THRESHOLDS_TABLE = new long[] { Long.MAX_VALUE, 922337203685477580L, 92233720368547758L, 9223372036854775L, 922337203685477L, 92233720368547L, 9223372036854L, 922337203685L, 92233720368L, 9223372036L, 922337203L, 92233720L, 9223372L, 922337L, 92233L, 9223L, 922L, 92L, 9L };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long DIV_NUM_BASE = 4294967296L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long longMultiplyPowerTen(long paramLong, int paramInt) {
/* 3684 */     if (paramLong == 0L || paramInt <= 0)
/* 3685 */       return paramLong; 
/* 3686 */     long[] arrayOfLong1 = LONG_TEN_POWERS_TABLE;
/* 3687 */     long[] arrayOfLong2 = THRESHOLDS_TABLE;
/* 3688 */     if (paramInt < arrayOfLong1.length && paramInt < arrayOfLong2.length) {
/* 3689 */       long l = arrayOfLong1[paramInt];
/* 3690 */       if (paramLong == 1L)
/* 3691 */         return l; 
/* 3692 */       if (Math.abs(paramLong) <= arrayOfLong2[paramInt])
/* 3693 */         return paramLong * l; 
/*      */     } 
/* 3695 */     return Long.MIN_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger bigMultiplyPowerTen(int paramInt) {
/* 3703 */     if (paramInt <= 0) {
/* 3704 */       return inflated();
/*      */     }
/* 3706 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3707 */       return bigTenToThe(paramInt).multiply(this.intCompact);
/*      */     }
/* 3709 */     return this.intVal.multiply(bigTenToThe(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger inflated() {
/* 3717 */     if (this.intVal == null) {
/* 3718 */       return BigInteger.valueOf(this.intCompact);
/*      */     }
/* 3720 */     return this.intVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void matchScale(BigDecimal[] paramArrayOfBigDecimal) {
/* 3737 */     if ((paramArrayOfBigDecimal[0]).scale == (paramArrayOfBigDecimal[1]).scale)
/*      */       return; 
/* 3739 */     if ((paramArrayOfBigDecimal[0]).scale < (paramArrayOfBigDecimal[1]).scale) {
/* 3740 */       paramArrayOfBigDecimal[0] = paramArrayOfBigDecimal[0].setScale((paramArrayOfBigDecimal[1]).scale, 7);
/* 3741 */     } else if ((paramArrayOfBigDecimal[1]).scale < (paramArrayOfBigDecimal[0]).scale) {
/* 3742 */       paramArrayOfBigDecimal[1] = paramArrayOfBigDecimal[1].setScale((paramArrayOfBigDecimal[0]).scale, 7);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static class UnsafeHolder { private static final Unsafe unsafe;
/*      */     private static final long intCompactOffset;
/*      */     private static final long intValOffset;
/*      */     
/*      */     static {
/*      */       try {
/* 3752 */         unsafe = Unsafe.getUnsafe();
/*      */         
/* 3754 */         intCompactOffset = unsafe.objectFieldOffset(BigDecimal.class.getDeclaredField("intCompact"));
/*      */         
/* 3756 */         intValOffset = unsafe.objectFieldOffset(BigDecimal.class.getDeclaredField("intVal"));
/* 3757 */       } catch (Exception exception) {
/* 3758 */         throw new ExceptionInInitializerError(exception);
/*      */       } 
/*      */     }
/*      */     static void setIntCompactVolatile(BigDecimal param1BigDecimal, long param1Long) {
/* 3762 */       unsafe.putLongVolatile(param1BigDecimal, intCompactOffset, param1Long);
/*      */     }
/*      */     
/*      */     static void setIntValVolatile(BigDecimal param1BigDecimal, BigInteger param1BigInteger) {
/* 3766 */       unsafe.putObjectVolatile(param1BigDecimal, intValOffset, param1BigInteger);
/*      */     } }
/*      */ 
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
/* 3779 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 3781 */     if (this.intVal == null) {
/* 3782 */       String str = "BigDecimal: null intVal in stream";
/* 3783 */       throw new StreamCorruptedException(str);
/*      */     } 
/*      */     
/* 3786 */     UnsafeHolder.setIntCompactVolatile(this, compactValFor(this.intVal));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 3797 */     if (this.intVal == null) {
/* 3798 */       UnsafeHolder.setIntValVolatile(this, BigInteger.valueOf(this.intCompact));
/*      */     }
/* 3800 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int longDigitLength(long paramLong) {
/* 3824 */     assert paramLong != Long.MIN_VALUE;
/* 3825 */     if (paramLong < 0L)
/* 3826 */       paramLong = -paramLong; 
/* 3827 */     if (paramLong < 10L)
/* 3828 */       return 1; 
/* 3829 */     int i = (64 - Long.numberOfLeadingZeros(paramLong) + 1) * 1233 >>> 12;
/* 3830 */     long[] arrayOfLong = LONG_TEN_POWERS_TABLE;
/*      */     
/* 3832 */     return (i >= arrayOfLong.length || paramLong < arrayOfLong[i]) ? i : (i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int bigDigitLength(BigInteger paramBigInteger) {
/* 3848 */     if (paramBigInteger.signum == 0)
/* 3849 */       return 1; 
/* 3850 */     int i = (int)((paramBigInteger.bitLength() + 1L) * 646456993L >>> 31L);
/* 3851 */     return (paramBigInteger.compareMagnitude(bigTenToThe(i)) < 0) ? i : (i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int checkScale(long paramLong) {
/* 3866 */     int i = (int)paramLong;
/* 3867 */     if (i != paramLong) {
/* 3868 */       i = (paramLong > 2147483647L) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
/*      */       BigInteger bigInteger;
/* 3870 */       if (this.intCompact != 0L && ((bigInteger = this.intVal) == null || bigInteger
/* 3871 */         .signum() != 0))
/* 3872 */         throw new ArithmeticException((i > 0) ? "Underflow" : "Overflow"); 
/*      */     } 
/* 3874 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long compactValFor(BigInteger paramBigInteger) {
/* 3883 */     int[] arrayOfInt = paramBigInteger.mag;
/* 3884 */     int i = arrayOfInt.length;
/* 3885 */     if (i == 0)
/* 3886 */       return 0L; 
/* 3887 */     int j = arrayOfInt[0];
/* 3888 */     if (i > 2 || (i == 2 && j < 0)) {
/* 3889 */       return Long.MIN_VALUE;
/*      */     }
/* 3891 */     long l = (i == 2) ? ((arrayOfInt[1] & 0xFFFFFFFFL) + (j << 32L)) : (j & 0xFFFFFFFFL);
/*      */ 
/*      */     
/* 3894 */     return (paramBigInteger.signum < 0) ? -l : l;
/*      */   }
/*      */   
/*      */   private static int longCompareMagnitude(long paramLong1, long paramLong2) {
/* 3898 */     if (paramLong1 < 0L)
/* 3899 */       paramLong1 = -paramLong1; 
/* 3900 */     if (paramLong2 < 0L)
/* 3901 */       paramLong2 = -paramLong2; 
/* 3902 */     return (paramLong1 < paramLong2) ? -1 : ((paramLong1 == paramLong2) ? 0 : 1);
/*      */   }
/*      */   
/*      */   private static int saturateLong(long paramLong) {
/* 3906 */     int i = (int)paramLong;
/* 3907 */     return (paramLong == i) ? i : ((paramLong < 0L) ? Integer.MIN_VALUE : Integer.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void print(String paramString, BigDecimal paramBigDecimal) {
/* 3914 */     System.err.format("%s:\tintCompact %d\tintVal %d\tscale %d\tprecision %d%n", new Object[] { paramString, 
/*      */           
/* 3916 */           Long.valueOf(paramBigDecimal.intCompact), paramBigDecimal.intVal, 
/*      */           
/* 3918 */           Integer.valueOf(paramBigDecimal.scale), 
/* 3919 */           Integer.valueOf(paramBigDecimal.precision) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigDecimal audit() {
/* 3942 */     if (this.intCompact == Long.MIN_VALUE) {
/* 3943 */       if (this.intVal == null) {
/* 3944 */         print("audit", this);
/* 3945 */         throw new AssertionError("null intVal");
/*      */       } 
/*      */       
/* 3948 */       if (this.precision > 0 && this.precision != bigDigitLength(this.intVal)) {
/* 3949 */         print("audit", this);
/* 3950 */         throw new AssertionError("precision mismatch");
/*      */       } 
/*      */     } else {
/* 3953 */       if (this.intVal != null) {
/* 3954 */         long l = this.intVal.longValue();
/* 3955 */         if (l != this.intCompact) {
/* 3956 */           print("audit", this);
/* 3957 */           throw new AssertionError("Inconsistent state, intCompact=" + this.intCompact + "\t intVal=" + l);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3962 */       if (this.precision > 0 && this.precision != longDigitLength(this.intCompact)) {
/* 3963 */         print("audit", this);
/* 3964 */         throw new AssertionError("precision mismatch");
/*      */       } 
/*      */     } 
/* 3967 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int checkScaleNonZero(long paramLong) {
/* 3972 */     int i = (int)paramLong;
/* 3973 */     if (i != paramLong) {
/* 3974 */       throw new ArithmeticException((i > 0) ? "Underflow" : "Overflow");
/*      */     }
/* 3976 */     return i;
/*      */   }
/*      */   
/*      */   private static int checkScale(long paramLong1, long paramLong2) {
/* 3980 */     int i = (int)paramLong2;
/* 3981 */     if (i != paramLong2) {
/* 3982 */       i = (paramLong2 > 2147483647L) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
/* 3983 */       if (paramLong1 != 0L)
/* 3984 */         throw new ArithmeticException((i > 0) ? "Underflow" : "Overflow"); 
/*      */     } 
/* 3986 */     return i;
/*      */   }
/*      */   
/*      */   private static int checkScale(BigInteger paramBigInteger, long paramLong) {
/* 3990 */     int i = (int)paramLong;
/* 3991 */     if (i != paramLong) {
/* 3992 */       i = (paramLong > 2147483647L) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
/* 3993 */       if (paramBigInteger.signum() != 0)
/* 3994 */         throw new ArithmeticException((i > 0) ? "Underflow" : "Overflow"); 
/*      */     } 
/* 3996 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal doRound(BigDecimal paramBigDecimal, MathContext paramMathContext) {
/* 4013 */     int i = paramMathContext.precision;
/* 4014 */     boolean bool = false;
/* 4015 */     if (i > 0) {
/* 4016 */       BigInteger bigInteger = paramBigDecimal.intVal;
/* 4017 */       long l = paramBigDecimal.intCompact;
/* 4018 */       int j = paramBigDecimal.scale;
/* 4019 */       int k = paramBigDecimal.precision();
/* 4020 */       int m = paramMathContext.roundingMode.oldMode;
/*      */       
/* 4022 */       if (l == Long.MIN_VALUE) {
/* 4023 */         int n = k - i;
/* 4024 */         while (n > 0) {
/* 4025 */           j = checkScaleNonZero(j - n);
/* 4026 */           bigInteger = divideAndRoundByTenPow(bigInteger, n, m);
/* 4027 */           bool = true;
/* 4028 */           l = compactValFor(bigInteger);
/* 4029 */           if (l != Long.MIN_VALUE) {
/* 4030 */             k = longDigitLength(l);
/*      */             break;
/*      */           } 
/* 4033 */           k = bigDigitLength(bigInteger);
/* 4034 */           n = k - i;
/*      */         } 
/*      */       } 
/* 4037 */       if (l != Long.MIN_VALUE) {
/* 4038 */         int n = k - i;
/* 4039 */         while (n > 0) {
/* 4040 */           j = checkScaleNonZero(j - n);
/* 4041 */           l = divideAndRound(l, LONG_TEN_POWERS_TABLE[n], paramMathContext.roundingMode.oldMode);
/* 4042 */           bool = true;
/* 4043 */           k = longDigitLength(l);
/* 4044 */           n = k - i;
/* 4045 */           bigInteger = null;
/*      */         } 
/*      */       } 
/* 4048 */       return bool ? new BigDecimal(bigInteger, l, j, k) : paramBigDecimal;
/*      */     } 
/* 4050 */     return paramBigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal doRound(long paramLong, int paramInt, MathContext paramMathContext) {
/* 4058 */     int i = paramMathContext.precision;
/* 4059 */     if (i > 0 && i < 19) {
/* 4060 */       int j = longDigitLength(paramLong);
/* 4061 */       int k = j - i;
/* 4062 */       while (k > 0) {
/* 4063 */         paramInt = checkScaleNonZero(paramInt - k);
/* 4064 */         paramLong = divideAndRound(paramLong, LONG_TEN_POWERS_TABLE[k], paramMathContext.roundingMode.oldMode);
/* 4065 */         j = longDigitLength(paramLong);
/* 4066 */         k = j - i;
/*      */       } 
/* 4068 */       return valueOf(paramLong, paramInt, j);
/*      */     } 
/* 4070 */     return valueOf(paramLong, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal doRound(BigInteger paramBigInteger, int paramInt, MathContext paramMathContext) {
/* 4078 */     int i = paramMathContext.precision;
/* 4079 */     int j = 0;
/* 4080 */     if (i > 0) {
/* 4081 */       long l = compactValFor(paramBigInteger);
/* 4082 */       int k = paramMathContext.roundingMode.oldMode;
/*      */       
/* 4084 */       if (l == Long.MIN_VALUE) {
/* 4085 */         j = bigDigitLength(paramBigInteger);
/* 4086 */         int m = j - i;
/* 4087 */         while (m > 0) {
/* 4088 */           paramInt = checkScaleNonZero(paramInt - m);
/* 4089 */           paramBigInteger = divideAndRoundByTenPow(paramBigInteger, m, k);
/* 4090 */           l = compactValFor(paramBigInteger);
/* 4091 */           if (l != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/* 4094 */           j = bigDigitLength(paramBigInteger);
/* 4095 */           m = j - i;
/*      */         } 
/*      */       } 
/* 4098 */       if (l != Long.MIN_VALUE) {
/* 4099 */         j = longDigitLength(l);
/* 4100 */         int m = j - i;
/* 4101 */         while (m > 0) {
/* 4102 */           paramInt = checkScaleNonZero(paramInt - m);
/* 4103 */           l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], paramMathContext.roundingMode.oldMode);
/* 4104 */           j = longDigitLength(l);
/* 4105 */           m = j - i;
/*      */         } 
/* 4107 */         return valueOf(l, paramInt, j);
/*      */       } 
/*      */     } 
/* 4110 */     return new BigDecimal(paramBigInteger, Long.MIN_VALUE, paramInt, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger divideAndRoundByTenPow(BigInteger paramBigInteger, int paramInt1, int paramInt2) {
/* 4117 */     if (paramInt1 < LONG_TEN_POWERS_TABLE.length) {
/* 4118 */       paramBigInteger = divideAndRound(paramBigInteger, LONG_TEN_POWERS_TABLE[paramInt1], paramInt2);
/*      */     } else {
/* 4120 */       paramBigInteger = divideAndRound(paramBigInteger, bigTenToThe(paramInt1), paramInt2);
/* 4121 */     }  return paramBigInteger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divideAndRound(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3) {
/* 4137 */     long l1 = paramLong1 / paramLong2;
/* 4138 */     if (paramInt2 == 1 && paramInt1 == paramInt3)
/* 4139 */       return valueOf(l1, paramInt1); 
/* 4140 */     long l2 = paramLong1 % paramLong2;
/* 4141 */     boolean bool = (((paramLong1 < 0L) ? true : false) == ((paramLong2 < 0L) ? true : false)) ? true : true;
/* 4142 */     if (l2 != 0L) {
/* 4143 */       boolean bool1 = needIncrement(paramLong2, paramInt2, bool, l1, l2);
/* 4144 */       return valueOf(bool1 ? (l1 + bool) : l1, paramInt1);
/*      */     } 
/* 4146 */     if (paramInt3 != paramInt1) {
/* 4147 */       return createAndStripZerosToMatchScale(l1, paramInt1, paramInt3);
/*      */     }
/* 4149 */     return valueOf(l1, paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long divideAndRound(long paramLong1, long paramLong2, int paramInt) {
/* 4159 */     long l1 = paramLong1 / paramLong2;
/* 4160 */     if (paramInt == 1)
/* 4161 */       return l1; 
/* 4162 */     long l2 = paramLong1 % paramLong2;
/* 4163 */     boolean bool = (((paramLong1 < 0L) ? true : false) == ((paramLong2 < 0L) ? true : false)) ? true : true;
/* 4164 */     if (l2 != 0L) {
/* 4165 */       boolean bool1 = needIncrement(paramLong2, paramInt, bool, l1, l2);
/* 4166 */       return bool1 ? (l1 + bool) : l1;
/*      */     } 
/* 4168 */     return l1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean commonNeedIncrement(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 4177 */     switch (paramInt1) {
/*      */       case 7:
/* 4179 */         throw new ArithmeticException("Rounding necessary");
/*      */       
/*      */       case 0:
/* 4182 */         return true;
/*      */       
/*      */       case 1:
/* 4185 */         return false;
/*      */       
/*      */       case 2:
/* 4188 */         return (paramInt2 > 0);
/*      */       
/*      */       case 3:
/* 4191 */         return (paramInt2 < 0);
/*      */     } 
/*      */     
/* 4194 */     assert paramInt1 >= 4 && paramInt1 <= 6 : "Unexpected rounding mode" + 
/* 4195 */       RoundingMode.valueOf(paramInt1);
/*      */     
/* 4197 */     if (paramInt3 < 0)
/* 4198 */       return false; 
/* 4199 */     if (paramInt3 > 0) {
/* 4200 */       return true;
/*      */     }
/* 4202 */     assert paramInt3 == 0;
/*      */     
/* 4204 */     switch (paramInt1) {
/*      */       case 5:
/* 4206 */         return false;
/*      */       
/*      */       case 4:
/* 4209 */         return true;
/*      */       
/*      */       case 6:
/* 4212 */         return paramBoolean;
/*      */     } 
/*      */     
/* 4215 */     throw new AssertionError("Unexpected rounding mode" + paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean needIncrement(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3) {
/*      */     int i;
/* 4226 */     assert paramLong3 != 0L;
/*      */ 
/*      */     
/* 4229 */     if (paramLong3 <= -4611686018427387904L || paramLong3 > 4611686018427387903L) {
/* 4230 */       i = 1;
/*      */     } else {
/* 4232 */       i = longCompareMagnitude(2L * paramLong3, paramLong1);
/*      */     } 
/*      */     
/* 4235 */     return commonNeedIncrement(paramInt1, paramInt2, i, ((paramLong2 & 0x1L) != 0L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger divideAndRound(BigInteger paramBigInteger, long paramLong, int paramInt) {
/* 4245 */     long l = 0L;
/* 4246 */     MutableBigInteger mutableBigInteger1 = null;
/*      */     
/* 4248 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger(paramBigInteger.mag);
/* 4249 */     mutableBigInteger1 = new MutableBigInteger();
/* 4250 */     l = mutableBigInteger2.divide(paramLong, mutableBigInteger1);
/* 4251 */     boolean bool = (l == 0L) ? true : false;
/* 4252 */     int i = (paramLong < 0L) ? -paramBigInteger.signum : paramBigInteger.signum;
/* 4253 */     if (!bool && 
/* 4254 */       needIncrement(paramLong, paramInt, i, mutableBigInteger1, l)) {
/* 4255 */       mutableBigInteger1.add(MutableBigInteger.ONE);
/*      */     }
/*      */     
/* 4258 */     return mutableBigInteger1.toBigInteger(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divideAndRound(BigInteger paramBigInteger, long paramLong, int paramInt1, int paramInt2, int paramInt3) {
/* 4274 */     long l = 0L;
/* 4275 */     MutableBigInteger mutableBigInteger1 = null;
/*      */     
/* 4277 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger(paramBigInteger.mag);
/* 4278 */     mutableBigInteger1 = new MutableBigInteger();
/* 4279 */     l = mutableBigInteger2.divide(paramLong, mutableBigInteger1);
/* 4280 */     boolean bool = (l == 0L) ? true : false;
/* 4281 */     int i = (paramLong < 0L) ? -paramBigInteger.signum : paramBigInteger.signum;
/* 4282 */     if (!bool) {
/* 4283 */       if (needIncrement(paramLong, paramInt2, i, mutableBigInteger1, l)) {
/* 4284 */         mutableBigInteger1.add(MutableBigInteger.ONE);
/*      */       }
/* 4286 */       return mutableBigInteger1.toBigDecimal(i, paramInt1);
/*      */     } 
/* 4288 */     if (paramInt3 != paramInt1) {
/* 4289 */       long l1 = mutableBigInteger1.toCompactValue(i);
/* 4290 */       if (l1 != Long.MIN_VALUE) {
/* 4291 */         return createAndStripZerosToMatchScale(l1, paramInt1, paramInt3);
/*      */       }
/* 4293 */       BigInteger bigInteger = mutableBigInteger1.toBigInteger(i);
/* 4294 */       return createAndStripZerosToMatchScale(bigInteger, paramInt1, paramInt3);
/*      */     } 
/* 4296 */     return mutableBigInteger1.toBigDecimal(i, paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean needIncrement(long paramLong1, int paramInt1, int paramInt2, MutableBigInteger paramMutableBigInteger, long paramLong2) {
/*      */     int i;
/* 4306 */     assert paramLong2 != 0L;
/*      */ 
/*      */     
/* 4309 */     if (paramLong2 <= -4611686018427387904L || paramLong2 > 4611686018427387903L) {
/* 4310 */       i = 1;
/*      */     } else {
/* 4312 */       i = longCompareMagnitude(2L * paramLong2, paramLong1);
/*      */     } 
/*      */     
/* 4315 */     return commonNeedIncrement(paramInt1, paramInt2, i, paramMutableBigInteger.isOdd());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger divideAndRound(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt) {
/* 4326 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(paramBigInteger1.mag);
/* 4327 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
/* 4328 */     MutableBigInteger mutableBigInteger3 = new MutableBigInteger(paramBigInteger2.mag);
/* 4329 */     MutableBigInteger mutableBigInteger4 = mutableBigInteger1.divide(mutableBigInteger3, mutableBigInteger2);
/* 4330 */     boolean bool = mutableBigInteger4.isZero();
/* 4331 */     boolean bool1 = (paramBigInteger1.signum != paramBigInteger2.signum) ? true : true;
/* 4332 */     if (!bool && 
/* 4333 */       needIncrement(mutableBigInteger3, paramInt, bool1, mutableBigInteger2, mutableBigInteger4)) {
/* 4334 */       mutableBigInteger2.add(MutableBigInteger.ONE);
/*      */     }
/*      */     
/* 4337 */     return mutableBigInteger2.toBigInteger(bool1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divideAndRound(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt1, int paramInt2, int paramInt3) {
/* 4354 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(paramBigInteger1.mag);
/* 4355 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
/* 4356 */     MutableBigInteger mutableBigInteger3 = new MutableBigInteger(paramBigInteger2.mag);
/* 4357 */     MutableBigInteger mutableBigInteger4 = mutableBigInteger1.divide(mutableBigInteger3, mutableBigInteger2);
/* 4358 */     boolean bool = mutableBigInteger4.isZero();
/* 4359 */     boolean bool1 = (paramBigInteger1.signum != paramBigInteger2.signum) ? true : true;
/* 4360 */     if (!bool) {
/* 4361 */       if (needIncrement(mutableBigInteger3, paramInt2, bool1, mutableBigInteger2, mutableBigInteger4)) {
/* 4362 */         mutableBigInteger2.add(MutableBigInteger.ONE);
/*      */       }
/* 4364 */       return mutableBigInteger2.toBigDecimal(bool1, paramInt1);
/*      */     } 
/* 4366 */     if (paramInt3 != paramInt1) {
/* 4367 */       long l = mutableBigInteger2.toCompactValue(bool1);
/* 4368 */       if (l != Long.MIN_VALUE) {
/* 4369 */         return createAndStripZerosToMatchScale(l, paramInt1, paramInt3);
/*      */       }
/* 4371 */       BigInteger bigInteger = mutableBigInteger2.toBigInteger(bool1);
/* 4372 */       return createAndStripZerosToMatchScale(bigInteger, paramInt1, paramInt3);
/*      */     } 
/* 4374 */     return mutableBigInteger2.toBigDecimal(bool1, paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean needIncrement(MutableBigInteger paramMutableBigInteger1, int paramInt1, int paramInt2, MutableBigInteger paramMutableBigInteger2, MutableBigInteger paramMutableBigInteger3) {
/* 4384 */     assert !paramMutableBigInteger3.isZero();
/* 4385 */     int i = paramMutableBigInteger3.compareHalf(paramMutableBigInteger1);
/* 4386 */     return commonNeedIncrement(paramInt1, paramInt2, i, paramMutableBigInteger2.isOdd());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal createAndStripZerosToMatchScale(BigInteger paramBigInteger, int paramInt, long paramLong) {
/* 4400 */     while (paramBigInteger.compareMagnitude(BigInteger.TEN) >= 0 && paramInt > paramLong) {
/*      */       
/* 4402 */       if (paramBigInteger.testBit(0))
/*      */         break; 
/* 4404 */       BigInteger[] arrayOfBigInteger = paramBigInteger.divideAndRemainder(BigInteger.TEN);
/* 4405 */       if (arrayOfBigInteger[1].signum() != 0)
/*      */         break; 
/* 4407 */       paramBigInteger = arrayOfBigInteger[0];
/* 4408 */       paramInt = checkScale(paramBigInteger, paramInt - 1L);
/*      */     } 
/* 4410 */     return valueOf(paramBigInteger, paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal createAndStripZerosToMatchScale(long paramLong1, int paramInt, long paramLong2) {
/* 4423 */     while (Math.abs(paramLong1) >= 10L && paramInt > paramLong2 && (
/* 4424 */       paramLong1 & 0x1L) == 0L) {
/*      */       
/* 4426 */       long l = paramLong1 % 10L;
/* 4427 */       if (l != 0L)
/*      */         break; 
/* 4429 */       paramLong1 /= 10L;
/* 4430 */       paramInt = checkScale(paramLong1, paramInt - 1L);
/*      */     } 
/* 4432 */     return valueOf(paramLong1, paramInt);
/*      */   }
/*      */   
/*      */   private static BigDecimal stripZerosToMatchScale(BigInteger paramBigInteger, long paramLong, int paramInt1, int paramInt2) {
/* 4436 */     if (paramLong != Long.MIN_VALUE) {
/* 4437 */       return createAndStripZerosToMatchScale(paramLong, paramInt1, paramInt2);
/*      */     }
/* 4439 */     return createAndStripZerosToMatchScale((paramBigInteger == null) ? INFLATED_BIGINT : paramBigInteger, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long add(long paramLong1, long paramLong2) {
/* 4448 */     long l = paramLong1 + paramLong2;
/*      */ 
/*      */     
/* 4451 */     if (((l ^ paramLong1) & (l ^ paramLong2)) >= 0L) {
/* 4452 */       return l;
/*      */     }
/* 4454 */     return Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */   private static BigDecimal add(long paramLong1, long paramLong2, int paramInt) {
/* 4458 */     long l = add(paramLong1, paramLong2);
/* 4459 */     if (l != Long.MIN_VALUE)
/* 4460 */       return valueOf(l, paramInt); 
/* 4461 */     return new BigDecimal(BigInteger.valueOf(paramLong1).add(paramLong2), paramInt);
/*      */   }
/*      */   
/*      */   private static BigDecimal add(long paramLong1, int paramInt1, long paramLong2, int paramInt2) {
/* 4465 */     long l1 = paramInt1 - paramInt2;
/* 4466 */     if (l1 == 0L)
/* 4467 */       return add(paramLong1, paramLong2, paramInt1); 
/* 4468 */     if (l1 < 0L) {
/* 4469 */       int j = checkScale(paramLong1, -l1);
/* 4470 */       long l = longMultiplyPowerTen(paramLong1, j);
/* 4471 */       if (l != Long.MIN_VALUE) {
/* 4472 */         return add(l, paramLong2, paramInt2);
/*      */       }
/* 4474 */       BigInteger bigInteger1 = bigMultiplyPowerTen(paramLong1, j).add(paramLong2);
/* 4475 */       return ((paramLong1 ^ paramLong2) >= 0L) ? new BigDecimal(bigInteger1, Long.MIN_VALUE, paramInt2, 0) : 
/*      */         
/* 4477 */         valueOf(bigInteger1, paramInt2, 0);
/*      */     } 
/*      */     
/* 4480 */     int i = checkScale(paramLong2, l1);
/* 4481 */     long l2 = longMultiplyPowerTen(paramLong2, i);
/* 4482 */     if (l2 != Long.MIN_VALUE) {
/* 4483 */       return add(paramLong1, l2, paramInt1);
/*      */     }
/* 4485 */     BigInteger bigInteger = bigMultiplyPowerTen(paramLong2, i).add(paramLong1);
/* 4486 */     return ((paramLong1 ^ paramLong2) >= 0L) ? new BigDecimal(bigInteger, Long.MIN_VALUE, paramInt1, 0) : 
/*      */       
/* 4488 */       valueOf(bigInteger, paramInt1, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static BigDecimal add(long paramLong, int paramInt1, BigInteger paramBigInteger, int paramInt2) {
/*      */     BigInteger bigInteger;
/* 4494 */     int i = paramInt1;
/* 4495 */     long l = i - paramInt2;
/* 4496 */     boolean bool = (Long.signum(paramLong) == paramBigInteger.signum) ? true : false;
/*      */     
/* 4498 */     if (l < 0L) {
/* 4499 */       int j = checkScale(paramLong, -l);
/* 4500 */       i = paramInt2;
/* 4501 */       long l1 = longMultiplyPowerTen(paramLong, j);
/* 4502 */       if (l1 == Long.MIN_VALUE) {
/* 4503 */         bigInteger = paramBigInteger.add(bigMultiplyPowerTen(paramLong, j));
/*      */       } else {
/* 4505 */         bigInteger = paramBigInteger.add(l1);
/*      */       } 
/*      */     } else {
/* 4508 */       int j = checkScale(paramBigInteger, l);
/* 4509 */       paramBigInteger = bigMultiplyPowerTen(paramBigInteger, j);
/* 4510 */       bigInteger = paramBigInteger.add(paramLong);
/*      */     } 
/* 4512 */     return bool ? new BigDecimal(bigInteger, Long.MIN_VALUE, i, 0) : 
/*      */       
/* 4514 */       valueOf(bigInteger, i, 0);
/*      */   }
/*      */   
/*      */   private static BigDecimal add(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2) {
/* 4518 */     int i = paramInt1;
/* 4519 */     long l = i - paramInt2;
/* 4520 */     if (l != 0L) {
/* 4521 */       if (l < 0L) {
/* 4522 */         int j = checkScale(paramBigInteger1, -l);
/* 4523 */         i = paramInt2;
/* 4524 */         paramBigInteger1 = bigMultiplyPowerTen(paramBigInteger1, j);
/*      */       } else {
/* 4526 */         int j = checkScale(paramBigInteger2, l);
/* 4527 */         paramBigInteger2 = bigMultiplyPowerTen(paramBigInteger2, j);
/*      */       } 
/*      */     }
/* 4530 */     BigInteger bigInteger = paramBigInteger1.add(paramBigInteger2);
/* 4531 */     return (paramBigInteger1.signum == paramBigInteger2.signum) ? new BigDecimal(bigInteger, Long.MIN_VALUE, i, 0) : 
/*      */       
/* 4533 */       valueOf(bigInteger, i, 0);
/*      */   }
/*      */   
/*      */   private static BigInteger bigMultiplyPowerTen(long paramLong, int paramInt) {
/* 4537 */     if (paramInt <= 0)
/* 4538 */       return BigInteger.valueOf(paramLong); 
/* 4539 */     return bigTenToThe(paramInt).multiply(paramLong);
/*      */   }
/*      */   
/*      */   private static BigInteger bigMultiplyPowerTen(BigInteger paramBigInteger, int paramInt) {
/* 4543 */     if (paramInt <= 0)
/* 4544 */       return paramBigInteger; 
/* 4545 */     if (paramInt < LONG_TEN_POWERS_TABLE.length) {
/* 4546 */       return paramBigInteger.multiply(LONG_TEN_POWERS_TABLE[paramInt]);
/*      */     }
/* 4548 */     return paramBigInteger.multiply(bigTenToThe(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divideSmallFastPath(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, MathContext paramMathContext) {
/*      */     BigDecimal bigDecimal;
/* 4561 */     int i = paramMathContext.precision;
/* 4562 */     int j = paramMathContext.roundingMode.oldMode;
/*      */     
/* 4564 */     assert paramInt1 <= paramInt2 && paramInt2 < 18 && i < 18;
/* 4565 */     int k = paramInt2 - paramInt1;
/*      */     
/* 4567 */     long l = (k == 0) ? paramLong1 : longMultiplyPowerTen(paramLong1, k);
/*      */ 
/*      */     
/* 4570 */     int m = longCompareMagnitude(l, paramLong2);
/* 4571 */     if (m > 0) {
/* 4572 */       paramInt2--;
/* 4573 */       int n = checkScaleNonZero(paramLong3 + paramInt2 - paramInt1 + i);
/* 4574 */       if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/*      */         
/* 4576 */         int i1 = checkScaleNonZero(i + paramInt2 - paramInt1);
/*      */         long l1;
/* 4578 */         if ((l1 = longMultiplyPowerTen(paramLong1, i1)) == Long.MIN_VALUE) {
/* 4579 */           bigDecimal = null;
/* 4580 */           if (i - 1 >= 0 && i - 1 < LONG_TEN_POWERS_TABLE.length) {
/* 4581 */             bigDecimal = multiplyDivideAndRound(LONG_TEN_POWERS_TABLE[i - 1], l, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */           }
/* 4583 */           if (bigDecimal == null) {
/* 4584 */             BigInteger bigInteger = bigMultiplyPowerTen(l, i - 1);
/* 4585 */             bigDecimal = divideAndRound(bigInteger, paramLong2, n, j, 
/* 4586 */                 checkScaleNonZero(paramLong3));
/*      */           } 
/*      */         } else {
/* 4589 */           bigDecimal = divideAndRound(l1, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */         } 
/*      */       } else {
/* 4592 */         int i1 = checkScaleNonZero(paramInt1 - i);
/*      */         
/* 4594 */         if (i1 == paramInt2) {
/* 4595 */           bigDecimal = divideAndRound(paramLong1, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */         } else {
/* 4597 */           int i2 = checkScaleNonZero(i1 - paramInt2);
/*      */           long l1;
/* 4599 */           if ((l1 = longMultiplyPowerTen(paramLong2, i2)) == Long.MIN_VALUE) {
/* 4600 */             BigInteger bigInteger = bigMultiplyPowerTen(paramLong2, i2);
/* 4601 */             bigDecimal = divideAndRound(BigInteger.valueOf(paramLong1), bigInteger, n, j, 
/* 4602 */                 checkScaleNonZero(paramLong3));
/*      */           } else {
/* 4604 */             bigDecimal = divideAndRound(paramLong1, l1, n, j, checkScaleNonZero(paramLong3));
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 4611 */       int n = checkScaleNonZero(paramLong3 + paramInt2 - paramInt1 + i);
/* 4612 */       if (m == 0) {
/*      */         
/* 4614 */         bigDecimal = roundedTenPower((((l < 0L) ? true : false) == ((paramLong2 < 0L) ? true : false)) ? 1 : -1, i, n, checkScaleNonZero(paramLong3));
/*      */       } else {
/*      */         long l1;
/*      */         
/* 4618 */         if ((l1 = longMultiplyPowerTen(l, i)) == Long.MIN_VALUE) {
/* 4619 */           bigDecimal = null;
/* 4620 */           if (i < LONG_TEN_POWERS_TABLE.length) {
/* 4621 */             bigDecimal = multiplyDivideAndRound(LONG_TEN_POWERS_TABLE[i], l, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */           }
/* 4623 */           if (bigDecimal == null) {
/* 4624 */             BigInteger bigInteger = bigMultiplyPowerTen(l, i);
/* 4625 */             bigDecimal = divideAndRound(bigInteger, paramLong2, n, j, 
/* 4626 */                 checkScaleNonZero(paramLong3));
/*      */           } 
/*      */         } else {
/* 4629 */           bigDecimal = divideAndRound(l1, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4634 */     return doRound(bigDecimal, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divide(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, MathContext paramMathContext) {
/*      */     BigDecimal bigDecimal;
/* 4642 */     int i = paramMathContext.precision;
/* 4643 */     if (paramInt1 <= paramInt2 && paramInt2 < 18 && i < 18) {
/* 4644 */       return divideSmallFastPath(paramLong1, paramInt1, paramLong2, paramInt2, paramLong3, paramMathContext);
/*      */     }
/* 4646 */     if (compareMagnitudeNormalized(paramLong1, paramInt1, paramLong2, paramInt2) > 0) {
/* 4647 */       paramInt2--;
/*      */     }
/* 4649 */     int j = paramMathContext.roundingMode.oldMode;
/*      */ 
/*      */ 
/*      */     
/* 4653 */     int k = checkScaleNonZero(paramLong3 + paramInt2 - paramInt1 + i);
/*      */     
/* 4655 */     if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4656 */       int m = checkScaleNonZero(i + paramInt2 - paramInt1);
/*      */       long l;
/* 4658 */       if ((l = longMultiplyPowerTen(paramLong1, m)) == Long.MIN_VALUE) {
/* 4659 */         BigInteger bigInteger = bigMultiplyPowerTen(paramLong1, m);
/* 4660 */         bigDecimal = divideAndRound(bigInteger, paramLong2, k, j, checkScaleNonZero(paramLong3));
/*      */       } else {
/* 4662 */         bigDecimal = divideAndRound(l, paramLong2, k, j, checkScaleNonZero(paramLong3));
/*      */       } 
/*      */     } else {
/* 4665 */       int m = checkScaleNonZero(paramInt1 - i);
/*      */       
/* 4667 */       if (m == paramInt2) {
/* 4668 */         bigDecimal = divideAndRound(paramLong1, paramLong2, k, j, checkScaleNonZero(paramLong3));
/*      */       } else {
/* 4670 */         int n = checkScaleNonZero(m - paramInt2);
/*      */         long l;
/* 4672 */         if ((l = longMultiplyPowerTen(paramLong2, n)) == Long.MIN_VALUE) {
/* 4673 */           BigInteger bigInteger = bigMultiplyPowerTen(paramLong2, n);
/* 4674 */           bigDecimal = divideAndRound(BigInteger.valueOf(paramLong1), bigInteger, k, j, 
/* 4675 */               checkScaleNonZero(paramLong3));
/*      */         } else {
/* 4677 */           bigDecimal = divideAndRound(paramLong1, l, k, j, checkScaleNonZero(paramLong3));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4682 */     return doRound(bigDecimal, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divide(BigInteger paramBigInteger, int paramInt1, long paramLong1, int paramInt2, long paramLong2, MathContext paramMathContext) {
/*      */     BigDecimal bigDecimal;
/* 4691 */     if (-compareMagnitudeNormalized(paramLong1, paramInt2, paramBigInteger, paramInt1) > 0) {
/* 4692 */       paramInt2--;
/*      */     }
/* 4694 */     int i = paramMathContext.precision;
/* 4695 */     int j = paramMathContext.roundingMode.oldMode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4701 */     int k = checkScaleNonZero(paramLong2 + paramInt2 - paramInt1 + i);
/* 4702 */     if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4703 */       int m = checkScaleNonZero(i + paramInt2 - paramInt1);
/* 4704 */       BigInteger bigInteger = bigMultiplyPowerTen(paramBigInteger, m);
/* 4705 */       bigDecimal = divideAndRound(bigInteger, paramLong1, k, j, checkScaleNonZero(paramLong2));
/*      */     } else {
/* 4707 */       int m = checkScaleNonZero(paramInt1 - i);
/*      */       
/* 4709 */       if (m == paramInt2) {
/* 4710 */         bigDecimal = divideAndRound(paramBigInteger, paramLong1, k, j, checkScaleNonZero(paramLong2));
/*      */       } else {
/* 4712 */         int n = checkScaleNonZero(m - paramInt2);
/*      */         long l;
/* 4714 */         if ((l = longMultiplyPowerTen(paramLong1, n)) == Long.MIN_VALUE) {
/* 4715 */           BigInteger bigInteger = bigMultiplyPowerTen(paramLong1, n);
/* 4716 */           bigDecimal = divideAndRound(paramBigInteger, bigInteger, k, j, checkScaleNonZero(paramLong2));
/*      */         } else {
/* 4718 */           bigDecimal = divideAndRound(paramBigInteger, l, k, j, checkScaleNonZero(paramLong2));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4723 */     return doRound(bigDecimal, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divide(long paramLong1, int paramInt1, BigInteger paramBigInteger, int paramInt2, long paramLong2, MathContext paramMathContext) {
/*      */     BigDecimal bigDecimal;
/* 4732 */     if (compareMagnitudeNormalized(paramLong1, paramInt1, paramBigInteger, paramInt2) > 0) {
/* 4733 */       paramInt2--;
/*      */     }
/* 4735 */     int i = paramMathContext.precision;
/* 4736 */     int j = paramMathContext.roundingMode.oldMode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4742 */     int k = checkScaleNonZero(paramLong2 + paramInt2 - paramInt1 + i);
/* 4743 */     if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4744 */       int m = checkScaleNonZero(i + paramInt2 - paramInt1);
/* 4745 */       BigInteger bigInteger = bigMultiplyPowerTen(paramLong1, m);
/* 4746 */       bigDecimal = divideAndRound(bigInteger, paramBigInteger, k, j, checkScaleNonZero(paramLong2));
/*      */     } else {
/* 4748 */       int m = checkScaleNonZero(paramInt1 - i);
/* 4749 */       int n = checkScaleNonZero(m - paramInt2);
/* 4750 */       BigInteger bigInteger = bigMultiplyPowerTen(paramBigInteger, n);
/* 4751 */       bigDecimal = divideAndRound(BigInteger.valueOf(paramLong1), bigInteger, k, j, checkScaleNonZero(paramLong2));
/*      */     } 
/*      */     
/* 4754 */     return doRound(bigDecimal, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divide(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2, long paramLong, MathContext paramMathContext) {
/*      */     BigDecimal bigDecimal;
/* 4763 */     if (compareMagnitudeNormalized(paramBigInteger1, paramInt1, paramBigInteger2, paramInt2) > 0) {
/* 4764 */       paramInt2--;
/*      */     }
/* 4766 */     int i = paramMathContext.precision;
/* 4767 */     int j = paramMathContext.roundingMode.oldMode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4773 */     int k = checkScaleNonZero(paramLong + paramInt2 - paramInt1 + i);
/* 4774 */     if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4775 */       int m = checkScaleNonZero(i + paramInt2 - paramInt1);
/* 4776 */       BigInteger bigInteger = bigMultiplyPowerTen(paramBigInteger1, m);
/* 4777 */       bigDecimal = divideAndRound(bigInteger, paramBigInteger2, k, j, checkScaleNonZero(paramLong));
/*      */     } else {
/* 4779 */       int m = checkScaleNonZero(paramInt1 - i);
/* 4780 */       int n = checkScaleNonZero(m - paramInt2);
/* 4781 */       BigInteger bigInteger = bigMultiplyPowerTen(paramBigInteger2, n);
/* 4782 */       bigDecimal = divideAndRound(paramBigInteger1, bigInteger, k, j, checkScaleNonZero(paramLong));
/*      */     } 
/*      */     
/* 4785 */     return doRound(bigDecimal, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal multiplyDivideAndRound(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, int paramInt3) {
/* 4794 */     int i = Long.signum(paramLong1) * Long.signum(paramLong2) * Long.signum(paramLong3);
/* 4795 */     paramLong1 = Math.abs(paramLong1);
/* 4796 */     paramLong2 = Math.abs(paramLong2);
/* 4797 */     paramLong3 = Math.abs(paramLong3);
/*      */     
/* 4799 */     long l1 = paramLong1 >>> 32L;
/* 4800 */     long l2 = paramLong1 & 0xFFFFFFFFL;
/* 4801 */     long l3 = paramLong2 >>> 32L;
/* 4802 */     long l4 = paramLong2 & 0xFFFFFFFFL;
/* 4803 */     long l5 = l2 * l4;
/* 4804 */     long l6 = l5 & 0xFFFFFFFFL;
/* 4805 */     long l7 = l5 >>> 32L;
/* 4806 */     l5 = l1 * l4 + l7;
/* 4807 */     l7 = l5 & 0xFFFFFFFFL;
/* 4808 */     long l8 = l5 >>> 32L;
/* 4809 */     l5 = l2 * l3 + l7;
/* 4810 */     l7 = l5 & 0xFFFFFFFFL;
/* 4811 */     l8 += l5 >>> 32L;
/* 4812 */     long l9 = l8 >>> 32L;
/* 4813 */     l8 &= 0xFFFFFFFFL;
/* 4814 */     l5 = l1 * l3 + l8;
/* 4815 */     l8 = l5 & 0xFFFFFFFFL;
/* 4816 */     l9 = (l5 >>> 32L) + l9 & 0xFFFFFFFFL;
/* 4817 */     long l10 = make64(l9, l8);
/* 4818 */     long l11 = make64(l7, l6);
/*      */     
/* 4820 */     return divideAndRound128(l10, l11, paramLong3, i, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal divideAndRound128(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     long l7, l8, l9;
/* 4832 */     if (paramLong1 >= paramLong3) {
/* 4833 */       return null;
/*      */     }
/*      */     
/* 4836 */     int i = Long.numberOfLeadingZeros(paramLong3);
/* 4837 */     paramLong3 <<= i;
/*      */     
/* 4839 */     long l1 = paramLong3 >>> 32L;
/* 4840 */     long l2 = paramLong3 & 0xFFFFFFFFL;
/*      */     
/* 4842 */     long l3 = paramLong2 << i;
/* 4843 */     long l4 = l3 >>> 32L;
/* 4844 */     long l5 = l3 & 0xFFFFFFFFL;
/*      */     
/* 4846 */     l3 = paramLong1 << i | paramLong2 >>> 64 - i;
/* 4847 */     long l6 = l3 & 0xFFFFFFFFL;
/*      */     
/* 4849 */     if (l1 == 1L) {
/* 4850 */       l7 = l3;
/* 4851 */       l8 = 0L;
/* 4852 */     } else if (l3 >= 0L) {
/* 4853 */       l7 = l3 / l1;
/* 4854 */       l8 = l3 - l7 * l1;
/*      */     } else {
/* 4856 */       long[] arrayOfLong = divRemNegativeLong(l3, l1);
/* 4857 */       l7 = arrayOfLong[1];
/* 4858 */       l8 = arrayOfLong[0];
/*      */     } 
/*      */     
/* 4861 */     while (l7 >= 4294967296L || unsignedLongCompare(l7 * l2, make64(l8, l4))) {
/* 4862 */       l7--;
/* 4863 */       l8 += l1;
/* 4864 */       if (l8 >= 4294967296L) {
/*      */         break;
/*      */       }
/*      */     } 
/* 4868 */     l3 = mulsub(l6, l4, l1, l2, l7);
/* 4869 */     l4 = l3 & 0xFFFFFFFFL;
/*      */     
/* 4871 */     if (l1 == 1L) {
/* 4872 */       l9 = l3;
/* 4873 */       l8 = 0L;
/* 4874 */     } else if (l3 >= 0L) {
/* 4875 */       l9 = l3 / l1;
/* 4876 */       l8 = l3 - l9 * l1;
/*      */     } else {
/* 4878 */       long[] arrayOfLong = divRemNegativeLong(l3, l1);
/* 4879 */       l9 = arrayOfLong[1];
/* 4880 */       l8 = arrayOfLong[0];
/*      */     } 
/*      */     
/* 4883 */     while (l9 >= 4294967296L || unsignedLongCompare(l9 * l2, make64(l8, l5))) {
/* 4884 */       l9--;
/* 4885 */       l8 += l1;
/* 4886 */       if (l8 >= 4294967296L) {
/*      */         break;
/*      */       }
/*      */     } 
/* 4890 */     if ((int)l7 < 0) {
/*      */ 
/*      */       
/* 4893 */       MutableBigInteger mutableBigInteger = new MutableBigInteger(new int[] { (int)l7, (int)l9 });
/* 4894 */       if (paramInt3 == 1 && paramInt2 == paramInt4) {
/* 4895 */         return mutableBigInteger.toBigDecimal(paramInt1, paramInt2);
/*      */       }
/* 4897 */       long l = mulsub(l4, l5, l1, l2, l9) >>> i;
/* 4898 */       if (l != 0L) {
/* 4899 */         if (needIncrement(paramLong3 >>> i, paramInt3, paramInt1, mutableBigInteger, l)) {
/* 4900 */           mutableBigInteger.add(MutableBigInteger.ONE);
/*      */         }
/* 4902 */         return mutableBigInteger.toBigDecimal(paramInt1, paramInt2);
/*      */       } 
/* 4904 */       if (paramInt4 != paramInt2) {
/* 4905 */         BigInteger bigInteger = mutableBigInteger.toBigInteger(paramInt1);
/* 4906 */         return createAndStripZerosToMatchScale(bigInteger, paramInt2, paramInt4);
/*      */       } 
/* 4908 */       return mutableBigInteger.toBigDecimal(paramInt1, paramInt2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4913 */     long l10 = make64(l7, l9);
/* 4914 */     l10 *= paramInt1;
/*      */     
/* 4916 */     if (paramInt3 == 1 && paramInt2 == paramInt4) {
/* 4917 */       return valueOf(l10, paramInt2);
/*      */     }
/* 4919 */     long l11 = mulsub(l4, l5, l1, l2, l9) >>> i;
/* 4920 */     if (l11 != 0L) {
/* 4921 */       boolean bool = needIncrement(paramLong3 >>> i, paramInt3, paramInt1, l10, l11);
/* 4922 */       return valueOf(bool ? (l10 + paramInt1) : l10, paramInt2);
/*      */     } 
/* 4924 */     if (paramInt4 != paramInt2) {
/* 4925 */       return createAndStripZerosToMatchScale(l10, paramInt2, paramInt4);
/*      */     }
/* 4927 */     return valueOf(l10, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal roundedTenPower(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 4937 */     if (paramInt3 > paramInt4) {
/* 4938 */       int i = paramInt3 - paramInt4;
/* 4939 */       if (i < paramInt2) {
/* 4940 */         return scaledTenPow(paramInt2 - i, paramInt1, paramInt4);
/*      */       }
/* 4942 */       return valueOf(paramInt1, paramInt3 - paramInt2);
/*      */     } 
/*      */     
/* 4945 */     return scaledTenPow(paramInt2, paramInt1, paramInt3);
/*      */   }
/*      */ 
/*      */   
/*      */   static BigDecimal scaledTenPow(int paramInt1, int paramInt2, int paramInt3) {
/* 4950 */     if (paramInt1 < LONG_TEN_POWERS_TABLE.length) {
/* 4951 */       return valueOf(paramInt2 * LONG_TEN_POWERS_TABLE[paramInt1], paramInt3);
/*      */     }
/* 4953 */     BigInteger bigInteger = bigTenToThe(paramInt1);
/* 4954 */     if (paramInt2 == -1) {
/* 4955 */       bigInteger = bigInteger.negate();
/*      */     }
/* 4957 */     return new BigDecimal(bigInteger, Long.MIN_VALUE, paramInt3, paramInt1 + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long[] divRemNegativeLong(long paramLong1, long paramLong2) {
/* 4971 */     assert paramLong1 < 0L : "Non-negative numerator " + paramLong1;
/* 4972 */     assert paramLong2 != 1L : "Unity denominator";
/*      */ 
/*      */     
/* 4975 */     long l1 = (paramLong1 >>> 1L) / (paramLong2 >>> 1L);
/* 4976 */     long l2 = paramLong1 - l1 * paramLong2;
/*      */ 
/*      */     
/* 4979 */     while (l2 < 0L) {
/* 4980 */       l2 += paramLong2;
/* 4981 */       l1--;
/*      */     } 
/* 4983 */     while (l2 >= paramLong2) {
/* 4984 */       l2 -= paramLong2;
/* 4985 */       l1++;
/*      */     } 
/*      */ 
/*      */     
/* 4989 */     return new long[] { l2, l1 };
/*      */   }
/*      */   
/*      */   private static long make64(long paramLong1, long paramLong2) {
/* 4993 */     return paramLong1 << 32L | paramLong2;
/*      */   }
/*      */   
/*      */   private static long mulsub(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5) {
/* 4997 */     long l = paramLong2 - paramLong5 * paramLong4;
/* 4998 */     return make64(paramLong1 + (l >>> 32L) - paramLong5 * paramLong3, l & 0xFFFFFFFFL);
/*      */   }
/*      */   
/*      */   private static boolean unsignedLongCompare(long paramLong1, long paramLong2) {
/* 5002 */     return (paramLong1 + Long.MIN_VALUE > paramLong2 + Long.MIN_VALUE);
/*      */   }
/*      */   
/*      */   private static boolean unsignedLongCompareEq(long paramLong1, long paramLong2) {
/* 5006 */     return (paramLong1 + Long.MIN_VALUE >= paramLong2 + Long.MIN_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int compareMagnitudeNormalized(long paramLong1, int paramInt1, long paramLong2, int paramInt2) {
/* 5013 */     int i = paramInt1 - paramInt2;
/* 5014 */     if (i != 0) {
/* 5015 */       if (i < 0) {
/* 5016 */         paramLong1 = longMultiplyPowerTen(paramLong1, -i);
/*      */       } else {
/* 5018 */         paramLong2 = longMultiplyPowerTen(paramLong2, i);
/*      */       } 
/*      */     }
/* 5021 */     if (paramLong1 != Long.MIN_VALUE) {
/* 5022 */       return (paramLong2 != Long.MIN_VALUE) ? longCompareMagnitude(paramLong1, paramLong2) : -1;
/*      */     }
/* 5024 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int compareMagnitudeNormalized(long paramLong, int paramInt1, BigInteger paramBigInteger, int paramInt2) {
/* 5030 */     if (paramLong == 0L)
/* 5031 */       return -1; 
/* 5032 */     int i = paramInt1 - paramInt2;
/* 5033 */     if (i < 0 && 
/* 5034 */       longMultiplyPowerTen(paramLong, -i) == Long.MIN_VALUE) {
/* 5035 */       return bigMultiplyPowerTen(paramLong, -i).compareMagnitude(paramBigInteger);
/*      */     }
/*      */     
/* 5038 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int compareMagnitudeNormalized(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2) {
/* 5043 */     int i = paramInt1 - paramInt2;
/* 5044 */     if (i < 0) {
/* 5045 */       return bigMultiplyPowerTen(paramBigInteger1, -i).compareMagnitude(paramBigInteger2);
/*      */     }
/* 5047 */     return paramBigInteger1.compareMagnitude(bigMultiplyPowerTen(paramBigInteger2, i));
/*      */   }
/*      */ 
/*      */   
/*      */   private static long multiply(long paramLong1, long paramLong2) {
/* 5052 */     long l1 = paramLong1 * paramLong2;
/* 5053 */     long l2 = Math.abs(paramLong1);
/* 5054 */     long l3 = Math.abs(paramLong2);
/* 5055 */     if ((l2 | l3) >>> 31L == 0L || paramLong2 == 0L || l1 / paramLong2 == paramLong1) {
/* 5056 */       return l1;
/*      */     }
/* 5058 */     return Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */   private static BigDecimal multiply(long paramLong1, long paramLong2, int paramInt) {
/* 5062 */     long l = multiply(paramLong1, paramLong2);
/* 5063 */     if (l != Long.MIN_VALUE) {
/* 5064 */       return valueOf(l, paramInt);
/*      */     }
/* 5066 */     return new BigDecimal(BigInteger.valueOf(paramLong1).multiply(paramLong2), Long.MIN_VALUE, paramInt, 0);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiply(long paramLong, BigInteger paramBigInteger, int paramInt) {
/* 5070 */     if (paramLong == 0L) {
/* 5071 */       return zeroValueOf(paramInt);
/*      */     }
/* 5073 */     return new BigDecimal(paramBigInteger.multiply(paramLong), Long.MIN_VALUE, paramInt, 0);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiply(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt) {
/* 5077 */     return new BigDecimal(paramBigInteger1.multiply(paramBigInteger2), Long.MIN_VALUE, paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal multiplyAndRound(long paramLong1, long paramLong2, int paramInt, MathContext paramMathContext) {
/* 5084 */     long l1 = multiply(paramLong1, paramLong2);
/* 5085 */     if (l1 != Long.MIN_VALUE) {
/* 5086 */       return doRound(l1, paramInt, paramMathContext);
/*      */     }
/*      */     
/* 5089 */     int i = 1;
/* 5090 */     if (paramLong1 < 0L) {
/* 5091 */       paramLong1 = -paramLong1;
/* 5092 */       i = -1;
/*      */     } 
/* 5094 */     if (paramLong2 < 0L) {
/* 5095 */       paramLong2 = -paramLong2;
/* 5096 */       i *= -1;
/*      */     } 
/*      */     
/* 5099 */     long l2 = paramLong1 >>> 32L;
/* 5100 */     long l3 = paramLong1 & 0xFFFFFFFFL;
/* 5101 */     long l4 = paramLong2 >>> 32L;
/* 5102 */     long l5 = paramLong2 & 0xFFFFFFFFL;
/* 5103 */     l1 = l3 * l5;
/* 5104 */     long l6 = l1 & 0xFFFFFFFFL;
/* 5105 */     long l7 = l1 >>> 32L;
/* 5106 */     l1 = l2 * l5 + l7;
/* 5107 */     l7 = l1 & 0xFFFFFFFFL;
/* 5108 */     long l8 = l1 >>> 32L;
/* 5109 */     l1 = l3 * l4 + l7;
/* 5110 */     l7 = l1 & 0xFFFFFFFFL;
/* 5111 */     l8 += l1 >>> 32L;
/* 5112 */     long l9 = l8 >>> 32L;
/* 5113 */     l8 &= 0xFFFFFFFFL;
/* 5114 */     l1 = l2 * l4 + l8;
/* 5115 */     l8 = l1 & 0xFFFFFFFFL;
/* 5116 */     l9 = (l1 >>> 32L) + l9 & 0xFFFFFFFFL;
/* 5117 */     long l10 = make64(l9, l8);
/* 5118 */     long l11 = make64(l7, l6);
/* 5119 */     BigDecimal bigDecimal = doRound128(l10, l11, i, paramInt, paramMathContext);
/* 5120 */     if (bigDecimal != null) {
/* 5121 */       return bigDecimal;
/*      */     }
/* 5123 */     bigDecimal = new BigDecimal(BigInteger.valueOf(paramLong1).multiply(paramLong2 * i), Long.MIN_VALUE, paramInt, 0);
/* 5124 */     return doRound(bigDecimal, paramMathContext);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiplyAndRound(long paramLong, BigInteger paramBigInteger, int paramInt, MathContext paramMathContext) {
/* 5128 */     if (paramLong == 0L) {
/* 5129 */       return zeroValueOf(paramInt);
/*      */     }
/* 5131 */     return doRound(paramBigInteger.multiply(paramLong), paramInt, paramMathContext);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiplyAndRound(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt, MathContext paramMathContext) {
/* 5135 */     return doRound(paramBigInteger1.multiply(paramBigInteger2), paramInt, paramMathContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigDecimal doRound128(long paramLong1, long paramLong2, int paramInt1, int paramInt2, MathContext paramMathContext) {
/* 5143 */     int i = paramMathContext.precision;
/*      */     
/* 5145 */     BigDecimal bigDecimal = null; int j;
/* 5146 */     if ((j = precision(paramLong1, paramLong2) - i) > 0 && j < LONG_TEN_POWERS_TABLE.length) {
/* 5147 */       paramInt2 = checkScaleNonZero(paramInt2 - j);
/* 5148 */       bigDecimal = divideAndRound128(paramLong1, paramLong2, LONG_TEN_POWERS_TABLE[j], paramInt1, paramInt2, paramMathContext.roundingMode.oldMode, paramInt2);
/*      */     } 
/* 5150 */     if (bigDecimal != null) {
/* 5151 */       return doRound(bigDecimal, paramMathContext);
/*      */     }
/* 5153 */     return null;
/*      */   }
/*      */   
/* 5156 */   private static final long[][] LONGLONG_TEN_POWERS_TABLE = new long[][] { { 0L, -8446744073709551616L }, { 5L, 7766279631452241920L }, { 54L, 3875820019684212736L }, { 542L, 1864712049423024128L }, { 5421L, 200376420520689664L }, { 54210L, 2003764205206896640L }, { 542101L, 1590897978359414784L }, { 5421010L, -2537764290115403776L }, { 54210108L, -6930898827444486144L }, { 542101086L, 4477988020393345024L }, { 5421010862L, 7886392056514347008L }, { 54210108624L, 5076944270305263616L }, { 542101086242L, -4570789518076018688L }, { 5421010862427L, -8814407033341083648L }, { 54210108624275L, 4089650035136921600L }, { 542101086242752L, 4003012203950112768L }, { 5421010862427522L, 3136633892082024448L }, { 54210108624275221L, -5527149226598858752L }, { 542101086242752217L, 68739955140067328L }, { 5421010862427522170L, 687399551400673280L } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int precision(long paramLong1, long paramLong2) {
/* 5183 */     if (paramLong1 == 0L) {
/* 5184 */       if (paramLong2 >= 0L) {
/* 5185 */         return longDigitLength(paramLong2);
/*      */       }
/* 5187 */       return unsignedLongCompareEq(paramLong2, LONGLONG_TEN_POWERS_TABLE[0][1]) ? 20 : 19;
/*      */     } 
/*      */     
/* 5190 */     int i = (128 - Long.numberOfLeadingZeros(paramLong1) + 1) * 1233 >>> 12;
/* 5191 */     int j = i - 19;
/* 5192 */     return (j >= LONGLONG_TEN_POWERS_TABLE.length || longLongCompareMagnitude(paramLong1, paramLong2, LONGLONG_TEN_POWERS_TABLE[j][0], LONGLONG_TEN_POWERS_TABLE[j][1])) ? i : (i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean longLongCompareMagnitude(long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
/* 5201 */     if (paramLong1 != paramLong3) {
/* 5202 */       return (paramLong1 < paramLong3);
/*      */     }
/* 5204 */     return (paramLong2 + Long.MIN_VALUE < paramLong4 + Long.MIN_VALUE);
/*      */   }
/*      */   
/*      */   private static BigDecimal divide(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, int paramInt4) {
/* 5208 */     if (checkScale(paramLong1, paramInt3 + paramInt2) > paramInt1) {
/* 5209 */       int k = paramInt3 + paramInt2;
/* 5210 */       int m = k - paramInt1;
/* 5211 */       if (m < LONG_TEN_POWERS_TABLE.length) {
/* 5212 */         long l1 = paramLong1;
/* 5213 */         if ((l1 = longMultiplyPowerTen(l1, m)) != Long.MIN_VALUE) {
/* 5214 */           return divideAndRound(l1, paramLong2, paramInt3, paramInt4, paramInt3);
/*      */         }
/* 5216 */         BigDecimal bigDecimal = multiplyDivideAndRound(LONG_TEN_POWERS_TABLE[m], paramLong1, paramLong2, paramInt3, paramInt4, paramInt3);
/* 5217 */         if (bigDecimal != null) {
/* 5218 */           return bigDecimal;
/*      */         }
/*      */       } 
/* 5221 */       BigInteger bigInteger1 = bigMultiplyPowerTen(paramLong1, m);
/* 5222 */       return divideAndRound(bigInteger1, paramLong2, paramInt3, paramInt4, paramInt3);
/*      */     } 
/* 5224 */     int i = checkScale(paramLong2, paramInt1 - paramInt3);
/* 5225 */     int j = i - paramInt2;
/*      */     
/* 5227 */     long l = paramLong2;
/* 5228 */     if (j < LONG_TEN_POWERS_TABLE.length && (l = longMultiplyPowerTen(l, j)) != Long.MIN_VALUE) {
/* 5229 */       return divideAndRound(paramLong1, l, paramInt3, paramInt4, paramInt3);
/*      */     }
/*      */     
/* 5232 */     BigInteger bigInteger = bigMultiplyPowerTen(paramLong2, j);
/* 5233 */     return divideAndRound(BigInteger.valueOf(paramLong1), bigInteger, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */ 
/*      */   
/*      */   private static BigDecimal divide(BigInteger paramBigInteger, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4) {
/* 5238 */     if (checkScale(paramBigInteger, paramInt3 + paramInt2) > paramInt1) {
/* 5239 */       int k = paramInt3 + paramInt2;
/* 5240 */       int m = k - paramInt1;
/* 5241 */       BigInteger bigInteger1 = bigMultiplyPowerTen(paramBigInteger, m);
/* 5242 */       return divideAndRound(bigInteger1, paramLong, paramInt3, paramInt4, paramInt3);
/*      */     } 
/* 5244 */     int i = checkScale(paramLong, paramInt1 - paramInt3);
/* 5245 */     int j = i - paramInt2;
/*      */     
/* 5247 */     long l = paramLong;
/* 5248 */     if (j < LONG_TEN_POWERS_TABLE.length && (l = longMultiplyPowerTen(l, j)) != Long.MIN_VALUE) {
/* 5249 */       return divideAndRound(paramBigInteger, l, paramInt3, paramInt4, paramInt3);
/*      */     }
/*      */     
/* 5252 */     BigInteger bigInteger = bigMultiplyPowerTen(paramLong, j);
/* 5253 */     return divideAndRound(paramBigInteger, bigInteger, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */ 
/*      */   
/*      */   private static BigDecimal divide(long paramLong, int paramInt1, BigInteger paramBigInteger, int paramInt2, int paramInt3, int paramInt4) {
/* 5258 */     if (checkScale(paramLong, paramInt3 + paramInt2) > paramInt1) {
/* 5259 */       int k = paramInt3 + paramInt2;
/* 5260 */       int m = k - paramInt1;
/* 5261 */       BigInteger bigInteger1 = bigMultiplyPowerTen(paramLong, m);
/* 5262 */       return divideAndRound(bigInteger1, paramBigInteger, paramInt3, paramInt4, paramInt3);
/*      */     } 
/* 5264 */     int i = checkScale(paramBigInteger, paramInt1 - paramInt3);
/* 5265 */     int j = i - paramInt2;
/* 5266 */     BigInteger bigInteger = bigMultiplyPowerTen(paramBigInteger, j);
/* 5267 */     return divideAndRound(BigInteger.valueOf(paramLong), bigInteger, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */ 
/*      */   
/*      */   private static BigDecimal divide(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2, int paramInt3, int paramInt4) {
/* 5272 */     if (checkScale(paramBigInteger1, paramInt3 + paramInt2) > paramInt1) {
/* 5273 */       int k = paramInt3 + paramInt2;
/* 5274 */       int m = k - paramInt1;
/* 5275 */       BigInteger bigInteger1 = bigMultiplyPowerTen(paramBigInteger1, m);
/* 5276 */       return divideAndRound(bigInteger1, paramBigInteger2, paramInt3, paramInt4, paramInt3);
/*      */     } 
/* 5278 */     int i = checkScale(paramBigInteger2, paramInt1 - paramInt3);
/* 5279 */     int j = i - paramInt2;
/* 5280 */     BigInteger bigInteger = bigMultiplyPowerTen(paramBigInteger2, j);
/* 5281 */     return divideAndRound(paramBigInteger1, bigInteger, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/math/BigDecimal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */