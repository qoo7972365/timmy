/*      */ package java.math;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ThreadLocalRandom;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigInteger
/*      */   extends Number
/*      */   implements Comparable<BigInteger>
/*      */ {
/*      */   final int signum;
/*      */   final int[] mag;
/*      */   @Deprecated
/*      */   private int bitCount;
/*      */   @Deprecated
/*      */   private int bitLength;
/*      */   @Deprecated
/*      */   private int lowestSetBit;
/*      */   @Deprecated
/*      */   private int firstNonzeroIntNum;
/*      */   static final long LONG_MASK = 4294967295L;
/*      */   private static final int MAX_MAG_LENGTH = 67108864;
/*      */   private static final int PRIME_SEARCH_BIT_LENGTH_LIMIT = 500000000;
/*      */   private static final int KARATSUBA_THRESHOLD = 80;
/*      */   private static final int TOOM_COOK_THRESHOLD = 240;
/*      */   private static final int KARATSUBA_SQUARE_THRESHOLD = 128;
/*      */   private static final int TOOM_COOK_SQUARE_THRESHOLD = 216;
/*      */   static final int BURNIKEL_ZIEGLER_THRESHOLD = 80;
/*      */   static final int BURNIKEL_ZIEGLER_OFFSET = 40;
/*      */   private static final int SCHOENHAGE_BASE_CONVERSION_THRESHOLD = 20;
/*      */   private static final int MULTIPLY_SQUARE_THRESHOLD = 20;
/*      */   private static final int MONTGOMERY_INTRINSIC_THRESHOLD = 512;
/*      */   
/*      */   public BigInteger(byte[] paramArrayOfbyte) {
/*  301 */     if (paramArrayOfbyte.length == 0) {
/*  302 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*  304 */     if (paramArrayOfbyte[0] < 0) {
/*  305 */       this.mag = makePositive(paramArrayOfbyte);
/*  306 */       this.signum = -1;
/*      */     } else {
/*  308 */       this.mag = stripLeadingZeroBytes(paramArrayOfbyte);
/*  309 */       this.signum = (this.mag.length == 0) ? 0 : 1;
/*      */     } 
/*  311 */     if (this.mag.length >= 67108864) {
/*  312 */       checkRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger(int[] paramArrayOfint) {
/*  323 */     if (paramArrayOfint.length == 0) {
/*  324 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*  326 */     if (paramArrayOfint[0] < 0) {
/*  327 */       this.mag = makePositive(paramArrayOfint);
/*  328 */       this.signum = -1;
/*      */     } else {
/*  330 */       this.mag = trustedStripLeadingZeroInts(paramArrayOfint);
/*  331 */       this.signum = (this.mag.length == 0) ? 0 : 1;
/*      */     } 
/*  333 */     if (this.mag.length >= 67108864) {
/*  334 */       checkRange();
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
/*      */   public BigInteger(int paramInt, byte[] paramArrayOfbyte) {
/*  355 */     this.mag = stripLeadingZeroBytes(paramArrayOfbyte);
/*      */     
/*  357 */     if (paramInt < -1 || paramInt > 1) {
/*  358 */       throw new NumberFormatException("Invalid signum value");
/*      */     }
/*  360 */     if (this.mag.length == 0) {
/*  361 */       this.signum = 0;
/*      */     } else {
/*  363 */       if (paramInt == 0)
/*  364 */         throw new NumberFormatException("signum-magnitude mismatch"); 
/*  365 */       this.signum = paramInt;
/*      */     } 
/*  367 */     if (this.mag.length >= 67108864) {
/*  368 */       checkRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger(int paramInt, int[] paramArrayOfint) {
/*  379 */     this.mag = stripLeadingZeroInts(paramArrayOfint);
/*      */     
/*  381 */     if (paramInt < -1 || paramInt > 1) {
/*  382 */       throw new NumberFormatException("Invalid signum value");
/*      */     }
/*  384 */     if (this.mag.length == 0) {
/*  385 */       this.signum = 0;
/*      */     } else {
/*  387 */       if (paramInt == 0)
/*  388 */         throw new NumberFormatException("signum-magnitude mismatch"); 
/*  389 */       this.signum = paramInt;
/*      */     } 
/*  391 */     if (this.mag.length >= 67108864) {
/*  392 */       checkRange();
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
/*      */   public BigInteger(String paramString, int paramInt) {
/*  414 */     int i = 0;
/*  415 */     int k = paramString.length();
/*      */     
/*  417 */     if (paramInt < 2 || paramInt > 36)
/*  418 */       throw new NumberFormatException("Radix out of range"); 
/*  419 */     if (k == 0) {
/*  420 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*      */     
/*  423 */     byte b = 1;
/*  424 */     int m = paramString.lastIndexOf('-');
/*  425 */     int n = paramString.lastIndexOf('+');
/*  426 */     if (m >= 0) {
/*  427 */       if (m != 0 || n >= 0) {
/*  428 */         throw new NumberFormatException("Illegal embedded sign character");
/*      */       }
/*  430 */       b = -1;
/*  431 */       i = 1;
/*  432 */     } else if (n >= 0) {
/*  433 */       if (n != 0) {
/*  434 */         throw new NumberFormatException("Illegal embedded sign character");
/*      */       }
/*  436 */       i = 1;
/*      */     } 
/*  438 */     if (i == k) {
/*  439 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*      */     
/*  442 */     while (i < k && 
/*  443 */       Character.digit(paramString.charAt(i), paramInt) == 0) {
/*  444 */       i++;
/*      */     }
/*      */     
/*  447 */     if (i == k) {
/*  448 */       this.signum = 0;
/*  449 */       this.mag = ZERO.mag;
/*      */       
/*      */       return;
/*      */     } 
/*  453 */     int j = k - i;
/*  454 */     this.signum = b;
/*      */ 
/*      */ 
/*      */     
/*  458 */     long l = (j * bitsPerDigit[paramInt] >>> 10L) + 1L;
/*  459 */     if (l + 31L >= 4294967296L) {
/*  460 */       reportOverflow();
/*      */     }
/*  462 */     int i1 = (int)(l + 31L) >>> 5;
/*  463 */     int[] arrayOfInt = new int[i1];
/*      */ 
/*      */     
/*  466 */     int i2 = j % digitsPerInt[paramInt];
/*  467 */     if (i2 == 0)
/*  468 */       i2 = digitsPerInt[paramInt]; 
/*  469 */     String str = paramString.substring(i, i += i2);
/*  470 */     arrayOfInt[i1 - 1] = Integer.parseInt(str, paramInt);
/*  471 */     if (arrayOfInt[i1 - 1] < 0) {
/*  472 */       throw new NumberFormatException("Illegal digit");
/*      */     }
/*      */     
/*  475 */     int i3 = intRadix[paramInt];
/*  476 */     int i4 = 0;
/*  477 */     while (i < k) {
/*  478 */       str = paramString.substring(i, i += digitsPerInt[paramInt]);
/*  479 */       i4 = Integer.parseInt(str, paramInt);
/*  480 */       if (i4 < 0)
/*  481 */         throw new NumberFormatException("Illegal digit"); 
/*  482 */       destructiveMulAdd(arrayOfInt, i3, i4);
/*      */     } 
/*      */     
/*  485 */     this.mag = trustedStripLeadingZeroInts(arrayOfInt);
/*  486 */     if (this.mag.length >= 67108864) {
/*  487 */       checkRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   BigInteger(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  496 */     int k, i = 0;
/*      */ 
/*      */     
/*  499 */     while (i < paramInt2 && Character.digit(paramArrayOfchar[i], 10) == 0) {
/*  500 */       i++;
/*      */     }
/*  502 */     if (i == paramInt2) {
/*  503 */       this.signum = 0;
/*  504 */       this.mag = ZERO.mag;
/*      */       
/*      */       return;
/*      */     } 
/*  508 */     int j = paramInt2 - i;
/*  509 */     this.signum = paramInt1;
/*      */ 
/*      */     
/*  512 */     if (paramInt2 < 10) {
/*  513 */       k = 1;
/*      */     } else {
/*  515 */       long l = (j * bitsPerDigit[10] >>> 10L) + 1L;
/*  516 */       if (l + 31L >= 4294967296L) {
/*  517 */         reportOverflow();
/*      */       }
/*  519 */       k = (int)(l + 31L) >>> 5;
/*      */     } 
/*  521 */     int[] arrayOfInt = new int[k];
/*      */ 
/*      */     
/*  524 */     int m = j % digitsPerInt[10];
/*  525 */     if (m == 0)
/*  526 */       m = digitsPerInt[10]; 
/*  527 */     arrayOfInt[k - 1] = parseInt(paramArrayOfchar, i, i += m);
/*      */ 
/*      */     
/*  530 */     while (i < paramInt2) {
/*  531 */       int n = parseInt(paramArrayOfchar, i, i += digitsPerInt[10]);
/*  532 */       destructiveMulAdd(arrayOfInt, intRadix[10], n);
/*      */     } 
/*  534 */     this.mag = trustedStripLeadingZeroInts(arrayOfInt);
/*  535 */     if (this.mag.length >= 67108864) {
/*  536 */       checkRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseInt(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  544 */     int i = Character.digit(paramArrayOfchar[paramInt1++], 10);
/*  545 */     if (i == -1) {
/*  546 */       throw new NumberFormatException(new String(paramArrayOfchar));
/*      */     }
/*  548 */     for (int j = paramInt1; j < paramInt2; j++) {
/*  549 */       int k = Character.digit(paramArrayOfchar[j], 10);
/*  550 */       if (k == -1)
/*  551 */         throw new NumberFormatException(new String(paramArrayOfchar)); 
/*  552 */       i = 10 * i + k;
/*      */     } 
/*      */     
/*  555 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  560 */   private static long[] bitsPerDigit = new long[] { 0L, 0L, 1024L, 1624L, 2048L, 2378L, 2648L, 2875L, 3072L, 3247L, 3402L, 3543L, 3672L, 3790L, 3899L, 4001L, 4096L, 4186L, 4271L, 4350L, 4426L, 4498L, 4567L, 4633L, 4696L, 4756L, 4814L, 4870L, 4923L, 4975L, 5025L, 5074L, 5120L, 5166L, 5210L, 5253L, 5295L };
/*      */ 
/*      */   
/*      */   private static final int SMALL_PRIME_THRESHOLD = 95;
/*      */   
/*      */   private static final int DEFAULT_PRIME_CERTAINTY = 100;
/*      */ 
/*      */   
/*      */   private static void destructiveMulAdd(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  569 */     long l1 = paramInt1 & 0xFFFFFFFFL;
/*  570 */     long l2 = paramInt2 & 0xFFFFFFFFL;
/*  571 */     int i = paramArrayOfint.length;
/*      */     
/*  573 */     long l3 = 0L;
/*  574 */     long l4 = 0L;
/*  575 */     for (int j = i - 1; j >= 0; j--) {
/*  576 */       l3 = l1 * (paramArrayOfint[j] & 0xFFFFFFFFL) + l4;
/*  577 */       paramArrayOfint[j] = (int)l3;
/*  578 */       l4 = l3 >>> 32L;
/*      */     } 
/*      */ 
/*      */     
/*  582 */     long l5 = (paramArrayOfint[i - 1] & 0xFFFFFFFFL) + l2;
/*  583 */     paramArrayOfint[i - 1] = (int)l5;
/*  584 */     l4 = l5 >>> 32L;
/*  585 */     for (int k = i - 2; k >= 0; k--) {
/*  586 */       l5 = (paramArrayOfint[k] & 0xFFFFFFFFL) + l4;
/*  587 */       paramArrayOfint[k] = (int)l5;
/*  588 */       l4 = l5 >>> 32L;
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
/*      */   public BigInteger(String paramString) {
/*  606 */     this(paramString, 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger(int paramInt, Random paramRandom) {
/*  623 */     this(1, randomBits(paramInt, paramRandom));
/*      */   }
/*      */   
/*      */   private static byte[] randomBits(int paramInt, Random paramRandom) {
/*  627 */     if (paramInt < 0)
/*  628 */       throw new IllegalArgumentException("numBits must be non-negative"); 
/*  629 */     int i = (int)((paramInt + 7L) / 8L);
/*  630 */     byte[] arrayOfByte = new byte[i];
/*      */ 
/*      */     
/*  633 */     if (i > 0) {
/*  634 */       paramRandom.nextBytes(arrayOfByte);
/*  635 */       int j = 8 * i - paramInt;
/*  636 */       arrayOfByte[0] = (byte)(arrayOfByte[0] & (1 << 8 - j) - 1);
/*      */     } 
/*  638 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger(int paramInt1, int paramInt2, Random paramRandom) {
/*  663 */     if (paramInt1 < 2) {
/*  664 */       throw new ArithmeticException("bitLength < 2");
/*      */     }
/*      */     
/*  667 */     BigInteger bigInteger = (paramInt1 < 95) ? smallPrime(paramInt1, paramInt2, paramRandom) : largePrime(paramInt1, paramInt2, paramRandom);
/*  668 */     this.signum = 1;
/*  669 */     this.mag = bigInteger.mag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger probablePrime(int paramInt, Random paramRandom) {
/*  694 */     if (paramInt < 2) {
/*  695 */       throw new ArithmeticException("bitLength < 2");
/*      */     }
/*  697 */     return (paramInt < 95) ? 
/*  698 */       smallPrime(paramInt, 100, paramRandom) : 
/*  699 */       largePrime(paramInt, 100, paramRandom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger smallPrime(int paramInt1, int paramInt2, Random paramRandom) {
/*  710 */     int i = paramInt1 + 31 >>> 5;
/*  711 */     int[] arrayOfInt = new int[i];
/*  712 */     int j = 1 << (paramInt1 + 31 & 0x1F);
/*  713 */     int k = (j << 1) - 1;
/*      */ 
/*      */     
/*      */     while (true) {
/*  717 */       for (byte b = 0; b < i; b++)
/*  718 */         arrayOfInt[b] = paramRandom.nextInt(); 
/*  719 */       arrayOfInt[0] = arrayOfInt[0] & k | j;
/*  720 */       if (paramInt1 > 2) {
/*  721 */         arrayOfInt[i - 1] = arrayOfInt[i - 1] | 0x1;
/*      */       }
/*  723 */       BigInteger bigInteger = new BigInteger(arrayOfInt, 1);
/*      */ 
/*      */       
/*  726 */       if (paramInt1 > 6) {
/*  727 */         long l = bigInteger.remainder(SMALL_PRIME_PRODUCT).longValue();
/*  728 */         if (l % 3L == 0L || l % 5L == 0L || l % 7L == 0L || l % 11L == 0L || l % 13L == 0L || l % 17L == 0L || l % 19L == 0L || l % 23L == 0L || l % 29L == 0L || l % 31L == 0L || l % 37L == 0L || l % 41L == 0L) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  735 */       if (paramInt1 < 4) {
/*  736 */         return bigInteger;
/*      */       }
/*      */       
/*  739 */       if (bigInteger.primeToCertainty(paramInt2, paramRandom)) {
/*  740 */         return bigInteger;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*  745 */   private static final BigInteger SMALL_PRIME_PRODUCT = valueOf(152125131763605L);
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_CONSTANT = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger largePrime(int paramInt1, int paramInt2, Random paramRandom) {
/*  755 */     BigInteger bigInteger1 = (new BigInteger(paramInt1, paramRandom)).setBit(paramInt1 - 1);
/*  756 */     bigInteger1.mag[bigInteger1.mag.length - 1] = bigInteger1.mag[bigInteger1.mag.length - 1] & 0xFFFFFFFE;
/*      */ 
/*      */     
/*  759 */     int i = getPrimeSearchLen(paramInt1);
/*  760 */     BitSieve bitSieve = new BitSieve(bigInteger1, i);
/*  761 */     BigInteger bigInteger2 = bitSieve.retrieve(bigInteger1, paramInt2, paramRandom);
/*      */     
/*  763 */     while (bigInteger2 == null || bigInteger2.bitLength() != paramInt1) {
/*  764 */       bigInteger1 = bigInteger1.add(valueOf((2 * i)));
/*  765 */       if (bigInteger1.bitLength() != paramInt1)
/*  766 */         bigInteger1 = (new BigInteger(paramInt1, paramRandom)).setBit(paramInt1 - 1); 
/*  767 */       bigInteger1.mag[bigInteger1.mag.length - 1] = bigInteger1.mag[bigInteger1.mag.length - 1] & 0xFFFFFFFE;
/*  768 */       bitSieve = new BitSieve(bigInteger1, i);
/*  769 */       bigInteger2 = bitSieve.retrieve(bigInteger1, paramInt2, paramRandom);
/*      */     } 
/*  771 */     return bigInteger2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger nextProbablePrime() {
/*  787 */     if (this.signum < 0) {
/*  788 */       throw new ArithmeticException("start < 0: " + this);
/*      */     }
/*      */     
/*  791 */     if (this.signum == 0 || equals(ONE)) {
/*  792 */       return TWO;
/*      */     }
/*  794 */     BigInteger bigInteger = add(ONE);
/*      */ 
/*      */     
/*  797 */     if (bigInteger.bitLength() < 95) {
/*      */ 
/*      */       
/*  800 */       if (!bigInteger.testBit(0)) {
/*  801 */         bigInteger = bigInteger.add(ONE);
/*      */       }
/*      */       
/*      */       while (true) {
/*  805 */         if (bigInteger.bitLength() > 6) {
/*  806 */           long l = bigInteger.remainder(SMALL_PRIME_PRODUCT).longValue();
/*  807 */           if (l % 3L == 0L || l % 5L == 0L || l % 7L == 0L || l % 11L == 0L || l % 13L == 0L || l % 17L == 0L || l % 19L == 0L || l % 23L == 0L || l % 29L == 0L || l % 31L == 0L || l % 37L == 0L || l % 41L == 0L) {
/*      */ 
/*      */             
/*  810 */             bigInteger = bigInteger.add(TWO);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/*  816 */         if (bigInteger.bitLength() < 4) {
/*  817 */           return bigInteger;
/*      */         }
/*      */         
/*  820 */         if (bigInteger.primeToCertainty(100, (Random)null)) {
/*  821 */           return bigInteger;
/*      */         }
/*  823 */         bigInteger = bigInteger.add(TWO);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  828 */     if (bigInteger.testBit(0)) {
/*  829 */       bigInteger = bigInteger.subtract(ONE);
/*      */     }
/*      */     
/*  832 */     int i = getPrimeSearchLen(bigInteger.bitLength());
/*      */     
/*      */     while (true) {
/*  835 */       BitSieve bitSieve = new BitSieve(bigInteger, i);
/*  836 */       BigInteger bigInteger1 = bitSieve.retrieve(bigInteger, 100, null);
/*      */       
/*  838 */       if (bigInteger1 != null)
/*  839 */         return bigInteger1; 
/*  840 */       bigInteger = bigInteger.add(valueOf((2 * i)));
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getPrimeSearchLen(int paramInt) {
/*  845 */     if (paramInt > 500000001) {
/*  846 */       throw new ArithmeticException("Prime search implementation restriction on bitLength");
/*      */     }
/*  848 */     return paramInt / 20 * 64;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean primeToCertainty(int paramInt, Random paramRandom) {
/*  866 */     byte b = 0;
/*  867 */     int i = (Math.min(paramInt, 2147483646) + 1) / 2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  872 */     int j = bitLength();
/*  873 */     if (j < 100) {
/*  874 */       b = 50;
/*  875 */       b = (i < b) ? i : b;
/*  876 */       return passesMillerRabin(b, paramRandom);
/*      */     } 
/*      */     
/*  879 */     if (j < 256) {
/*  880 */       b = 27;
/*  881 */     } else if (j < 512) {
/*  882 */       b = 15;
/*  883 */     } else if (j < 768) {
/*  884 */       b = 8;
/*  885 */     } else if (j < 1024) {
/*  886 */       b = 4;
/*      */     } else {
/*  888 */       b = 2;
/*      */     } 
/*  890 */     b = (i < b) ? i : b;
/*      */     
/*  892 */     return (passesMillerRabin(b, paramRandom) && passesLucasLehmer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean passesLucasLehmer() {
/*  902 */     BigInteger bigInteger1 = add(ONE);
/*      */ 
/*      */     
/*  905 */     int i = 5;
/*  906 */     while (jacobiSymbol(i, this) != -1)
/*      */     {
/*  908 */       i = (i < 0) ? (Math.abs(i) + 2) : -(i + 2);
/*      */     }
/*      */ 
/*      */     
/*  912 */     BigInteger bigInteger2 = lucasLehmerSequence(i, bigInteger1, this);
/*      */ 
/*      */     
/*  915 */     return bigInteger2.mod(this).equals(ZERO);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int jacobiSymbol(int paramInt, BigInteger paramBigInteger) {
/*  923 */     if (paramInt == 0) {
/*  924 */       return 0;
/*      */     }
/*      */     
/*  927 */     byte b = 1;
/*  928 */     int i = paramBigInteger.mag[paramBigInteger.mag.length - 1];
/*      */ 
/*      */     
/*  931 */     if (paramInt < 0) {
/*  932 */       paramInt = -paramInt;
/*  933 */       int j = i & 0x7;
/*  934 */       if (j == 3 || j == 7) {
/*  935 */         b = -b;
/*      */       }
/*      */     } 
/*      */     
/*  939 */     while ((paramInt & 0x3) == 0)
/*  940 */       paramInt >>= 2; 
/*  941 */     if ((paramInt & 0x1) == 0) {
/*  942 */       paramInt >>= 1;
/*  943 */       if (((i ^ i >> 1) & 0x2) != 0)
/*  944 */         b = -b; 
/*      */     } 
/*  946 */     if (paramInt == 1) {
/*  947 */       return b;
/*      */     }
/*  949 */     if ((paramInt & i & 0x2) != 0) {
/*  950 */       b = -b;
/*      */     }
/*  952 */     i = paramBigInteger.mod(valueOf(paramInt)).intValue();
/*      */ 
/*      */     
/*  955 */     while (i != 0) {
/*  956 */       while ((i & 0x3) == 0)
/*  957 */         i >>= 2; 
/*  958 */       if ((i & 0x1) == 0) {
/*  959 */         i >>= 1;
/*  960 */         if (((paramInt ^ paramInt >> 1) & 0x2) != 0)
/*  961 */           b = -b; 
/*      */       } 
/*  963 */       if (i == 1) {
/*  964 */         return b;
/*      */       }
/*  966 */       assert i < paramInt;
/*  967 */       int j = i; i = paramInt; paramInt = j;
/*  968 */       if ((i & paramInt & 0x2) != 0) {
/*  969 */         b = -b;
/*      */       }
/*  971 */       i %= paramInt;
/*      */     } 
/*  973 */     return 0;
/*      */   }
/*      */   
/*      */   private static BigInteger lucasLehmerSequence(int paramInt, BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
/*  977 */     BigInteger bigInteger1 = valueOf(paramInt);
/*  978 */     BigInteger bigInteger2 = ONE;
/*  979 */     BigInteger bigInteger3 = ONE;
/*      */     
/*  981 */     for (int i = paramBigInteger1.bitLength() - 2; i >= 0; i--) {
/*  982 */       BigInteger bigInteger4 = bigInteger2.multiply(bigInteger3).mod(paramBigInteger2);
/*      */       
/*  984 */       BigInteger bigInteger5 = bigInteger3.square().add(bigInteger1.multiply(bigInteger2.square())).mod(paramBigInteger2);
/*  985 */       if (bigInteger5.testBit(0)) {
/*  986 */         bigInteger5 = bigInteger5.subtract(paramBigInteger2);
/*      */       }
/*  988 */       bigInteger5 = bigInteger5.shiftRight(1);
/*      */       
/*  990 */       bigInteger2 = bigInteger4; bigInteger3 = bigInteger5;
/*  991 */       if (paramBigInteger1.testBit(i)) {
/*  992 */         bigInteger4 = bigInteger2.add(bigInteger3).mod(paramBigInteger2);
/*  993 */         if (bigInteger4.testBit(0)) {
/*  994 */           bigInteger4 = bigInteger4.subtract(paramBigInteger2);
/*      */         }
/*  996 */         bigInteger4 = bigInteger4.shiftRight(1);
/*  997 */         bigInteger5 = bigInteger3.add(bigInteger1.multiply(bigInteger2)).mod(paramBigInteger2);
/*  998 */         if (bigInteger5.testBit(0))
/*  999 */           bigInteger5 = bigInteger5.subtract(paramBigInteger2); 
/* 1000 */         bigInteger5 = bigInteger5.shiftRight(1);
/*      */         
/* 1002 */         bigInteger2 = bigInteger4; bigInteger3 = bigInteger5;
/*      */       } 
/*      */     } 
/* 1005 */     return bigInteger2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean passesMillerRabin(int paramInt, Random paramRandom) {
/* 1019 */     BigInteger bigInteger1 = subtract(ONE);
/* 1020 */     BigInteger bigInteger2 = bigInteger1;
/* 1021 */     int i = bigInteger2.getLowestSetBit();
/* 1022 */     bigInteger2 = bigInteger2.shiftRight(i);
/*      */ 
/*      */     
/* 1025 */     if (paramRandom == null) {
/* 1026 */       paramRandom = ThreadLocalRandom.current();
/*      */     }
/* 1028 */     for (byte b = 0; b < paramInt; b++) {
/*      */       BigInteger bigInteger3;
/*      */       
/*      */       do {
/* 1032 */         bigInteger3 = new BigInteger(bitLength(), paramRandom);
/* 1033 */       } while (bigInteger3.compareTo(ONE) <= 0 || bigInteger3.compareTo(this) >= 0);
/*      */       
/* 1035 */       byte b1 = 0;
/* 1036 */       BigInteger bigInteger4 = bigInteger3.modPow(bigInteger2, this);
/* 1037 */       while ((b1 || !bigInteger4.equals(ONE)) && !bigInteger4.equals(bigInteger1)) {
/* 1038 */         if ((b1 && bigInteger4.equals(ONE)) || ++b1 == i)
/* 1039 */           return false; 
/* 1040 */         bigInteger4 = bigInteger4.modPow(TWO, this);
/*      */       } 
/*      */     } 
/* 1043 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   BigInteger(int[] paramArrayOfint, int paramInt) {
/* 1052 */     this.signum = (paramArrayOfint.length == 0) ? 0 : paramInt;
/* 1053 */     this.mag = paramArrayOfint;
/* 1054 */     if (this.mag.length >= 67108864) {
/* 1055 */       checkRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger(byte[] paramArrayOfbyte, int paramInt) {
/* 1064 */     this.signum = (paramArrayOfbyte.length == 0) ? 0 : paramInt;
/* 1065 */     this.mag = stripLeadingZeroBytes(paramArrayOfbyte);
/* 1066 */     if (this.mag.length >= 67108864) {
/* 1067 */       checkRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkRange() {
/* 1078 */     if (this.mag.length > 67108864 || (this.mag.length == 67108864 && this.mag[0] < 0)) {
/* 1079 */       reportOverflow();
/*      */     }
/*      */   }
/*      */   
/*      */   private static void reportOverflow() {
/* 1084 */     throw new ArithmeticException("BigInteger would overflow supported range");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger valueOf(long paramLong) {
/* 1100 */     if (paramLong == 0L)
/* 1101 */       return ZERO; 
/* 1102 */     if (paramLong > 0L && paramLong <= 16L)
/* 1103 */       return posConst[(int)paramLong]; 
/* 1104 */     if (paramLong < 0L && paramLong >= -16L) {
/* 1105 */       return negConst[(int)-paramLong];
/*      */     }
/* 1107 */     return new BigInteger(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger(long paramLong) {
/* 1114 */     if (paramLong < 0L) {
/* 1115 */       paramLong = -paramLong;
/* 1116 */       this.signum = -1;
/*      */     } else {
/* 1118 */       this.signum = 1;
/*      */     } 
/*      */     
/* 1121 */     int i = (int)(paramLong >>> 32L);
/* 1122 */     if (i == 0) {
/* 1123 */       this.mag = new int[1];
/* 1124 */       this.mag[0] = (int)paramLong;
/*      */     } else {
/* 1126 */       this.mag = new int[2];
/* 1127 */       this.mag[0] = i;
/* 1128 */       this.mag[1] = (int)paramLong;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BigInteger valueOf(int[] paramArrayOfint) {
/* 1138 */     return (paramArrayOfint[0] > 0) ? new BigInteger(paramArrayOfint, 1) : new BigInteger(paramArrayOfint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1147 */   private static BigInteger[] posConst = new BigInteger[17];
/* 1148 */   private static BigInteger[] negConst = new BigInteger[17];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static volatile BigInteger[][] powerCache;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double[] logCache;
/*      */ 
/*      */ 
/*      */   
/* 1161 */   private static final double LOG_TWO = Math.log(2.0D); public static final BigInteger ZERO; public static final BigInteger ONE; private static final BigInteger TWO; private static final BigInteger NEGATIVE_ONE; public static final BigInteger TEN;
/*      */   static int[] bnExpModThreshTable;
/*      */   private static String[] zeros;
/*      */   private static int[] digitsPerLong;
/*      */   private static BigInteger[] longRadix;
/*      */   private static int[] digitsPerInt;
/*      */   private static int[] intRadix;
/*      */   private static final long serialVersionUID = -8287574255936472291L;
/*      */   private static final ObjectStreamField[] serialPersistentFields;
/*      */   
/*      */   static { byte b;
/* 1172 */     for (b = 1; b <= 16; b++) {
/* 1173 */       int[] arrayOfInt = new int[1];
/* 1174 */       arrayOfInt[0] = b;
/* 1175 */       posConst[b] = new BigInteger(arrayOfInt, 1);
/* 1176 */       negConst[b] = new BigInteger(arrayOfInt, -1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     powerCache = new BigInteger[37][];
/* 1185 */     logCache = new double[37];
/*      */     
/* 1187 */     for (b = 2; b <= 36; b++) {
/* 1188 */       (new BigInteger[1])[0] = valueOf(b); powerCache[b] = new BigInteger[1];
/* 1189 */       logCache[b] = Math.log(b);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1198 */     ZERO = new BigInteger(new int[0], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1205 */     ONE = valueOf(1L);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1210 */     TWO = valueOf(2L);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1215 */     NEGATIVE_ONE = valueOf(-1L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1222 */     TEN = valueOf(10L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2717 */     bnExpModThreshTable = new int[] { 7, 25, 81, 241, 673, 1793, Integer.MAX_VALUE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3938 */     zeros = new String[64];
/*      */     
/* 3940 */     zeros[63] = "000000000000000000000000000000000000000000000000000000000000000";
/*      */     
/* 3942 */     for (b = 0; b < 63; b++) {
/* 3943 */       zeros[b] = zeros[63].substring(0, b);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4354 */     digitsPerLong = new int[] { 0, 0, 62, 39, 31, 27, 24, 22, 20, 19, 18, 18, 17, 17, 16, 16, 15, 15, 15, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 12, 12 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4376 */     longRadix = new BigInteger[] { null, null, valueOf(4611686018427387904L), valueOf(4052555153018976267L), valueOf(4611686018427387904L), valueOf(7450580596923828125L), valueOf(4738381338321616896L), valueOf(3909821048582988049L), valueOf(1152921504606846976L), valueOf(1350851717672992089L), valueOf(1000000000000000000L), valueOf(5559917313492231481L), valueOf(2218611106740436992L), valueOf(8650415919381337933L), valueOf(2177953337809371136L), valueOf(6568408355712890625L), valueOf(1152921504606846976L), valueOf(2862423051509815793L), valueOf(6746640616477458432L), valueOf(799006685782884121L), valueOf(1638400000000000000L), valueOf(3243919932521508681L), valueOf(6221821273427820544L), valueOf(504036361936467383L), valueOf(876488338465357824L), valueOf(1490116119384765625L), valueOf(2481152873203736576L), valueOf(4052555153018976267L), valueOf(6502111422497947648L), valueOf(353814783205469041L), valueOf(531441000000000000L), valueOf(787662783788549761L), valueOf(1152921504606846976L), valueOf(1667889514952984961L), valueOf(2386420683693101056L), valueOf(3379220508056640625L), valueOf(4738381338321616896L) };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4381 */     digitsPerInt = new int[] { 0, 0, 30, 19, 15, 13, 11, 11, 10, 9, 9, 8, 8, 8, 8, 7, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5 };
/*      */ 
/*      */ 
/*      */     
/* 4385 */     intRadix = new int[] { 0, 0, 1073741824, 1162261467, 1073741824, 1220703125, 362797056, 1977326743, 1073741824, 387420489, 1000000000, 214358881, 429981696, 815730721, 1475789056, 170859375, 268435456, 410338673, 612220032, 893871739, 1280000000, 1801088541, 113379904, 148035889, 191102976, 244140625, 308915776, 387420489, 481890304, 594823321, 729000000, 887503681, 1073741824, 1291467969, 1544804416, 1838265625, 60466176 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4475 */     serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("signum", int.class), new ObjectStreamField("magnitude", byte[].class), new ObjectStreamField("bitCount", int.class), new ObjectStreamField("bitLength", int.class), new ObjectStreamField("firstNonzeroByteNum", int.class), new ObjectStreamField("lowestSetBit", int.class) }; }
/*      */   public BigInteger add(BigInteger paramBigInteger) { if (paramBigInteger.signum == 0) return this;  if (this.signum == 0) return paramBigInteger;  if (paramBigInteger.signum == this.signum) return new BigInteger(add(this.mag, paramBigInteger.mag), this.signum);  int i = compareMagnitude(paramBigInteger); if (i == 0) return ZERO;  int[] arrayOfInt = (i > 0) ? subtract(this.mag, paramBigInteger.mag) : subtract(paramBigInteger.mag, this.mag); arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt); return new BigInteger(arrayOfInt, (i == this.signum) ? 1 : -1); }
/*      */   BigInteger add(long paramLong) { if (paramLong == 0L) return this;  if (this.signum == 0) return valueOf(paramLong);  if (Long.signum(paramLong) == this.signum) return new BigInteger(add(this.mag, Math.abs(paramLong)), this.signum);  int i = compareMagnitude(paramLong); if (i == 0) return ZERO;  int[] arrayOfInt = (i > 0) ? subtract(this.mag, Math.abs(paramLong)) : subtract(Math.abs(paramLong), this.mag); arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt); return new BigInteger(arrayOfInt, (i == this.signum) ? 1 : -1); }
/*      */   private static int[] add(int[] paramArrayOfint, long paramLong) { int[] arrayOfInt; long l = 0L; int i = paramArrayOfint.length; int j = (int)(paramLong >>> 32L); if (j == 0) { arrayOfInt = new int[i]; l = (paramArrayOfint[--i] & 0xFFFFFFFFL) + paramLong; arrayOfInt[i] = (int)l; } else { if (i == 1) { int[] arrayOfInt1 = new int[2]; l = paramLong + (paramArrayOfint[0] & 0xFFFFFFFFL); arrayOfInt1[1] = (int)l; arrayOfInt1[0] = (int)(l >>> 32L); return arrayOfInt1; }  arrayOfInt = new int[i]; l = (paramArrayOfint[--i] & 0xFFFFFFFFL) + (paramLong & 0xFFFFFFFFL); arrayOfInt[i] = (int)l; l = (paramArrayOfint[--i] & 0xFFFFFFFFL) + (j & 0xFFFFFFFFL) + (l >>> 32L); arrayOfInt[i] = (int)l; }  boolean bool = (l >>> 32L != 0L) ? true : false; while (i > 0 && bool) { arrayOfInt[--i] = paramArrayOfint[i] + 1; bool = (paramArrayOfint[i] + 1 == 0) ? true : false; }  while (i > 0) arrayOfInt[--i] = paramArrayOfint[i];  if (bool) { int[] arrayOfInt1 = new int[arrayOfInt.length + 1]; System.arraycopy(arrayOfInt, 0, arrayOfInt1, 1, arrayOfInt.length); arrayOfInt1[0] = 1; return arrayOfInt1; }  return arrayOfInt; }
/*      */   private static int[] add(int[] paramArrayOfint1, int[] paramArrayOfint2) { if (paramArrayOfint1.length < paramArrayOfint2.length) { int[] arrayOfInt1 = paramArrayOfint1; paramArrayOfint1 = paramArrayOfint2; paramArrayOfint2 = arrayOfInt1; }  int i = paramArrayOfint1.length; int j = paramArrayOfint2.length; int[] arrayOfInt = new int[i]; long l = 0L; if (j == 1) { l = (paramArrayOfint1[--i] & 0xFFFFFFFFL) + (paramArrayOfint2[0] & 0xFFFFFFFFL); arrayOfInt[i] = (int)l; } else { while (j > 0) { l = (paramArrayOfint1[--i] & 0xFFFFFFFFL) + (paramArrayOfint2[--j] & 0xFFFFFFFFL) + (l >>> 32L); arrayOfInt[i] = (int)l; }  }  boolean bool = (l >>> 32L != 0L) ? true : false; while (i > 0 && bool) { arrayOfInt[--i] = paramArrayOfint1[i] + 1; bool = (paramArrayOfint1[i] + 1 == 0) ? true : false; }  while (i > 0) arrayOfInt[--i] = paramArrayOfint1[i];  if (bool) { int[] arrayOfInt1 = new int[arrayOfInt.length + 1]; System.arraycopy(arrayOfInt, 0, arrayOfInt1, 1, arrayOfInt.length); arrayOfInt1[0] = 1; return arrayOfInt1; }  return arrayOfInt; }
/*      */   private static int[] subtract(long paramLong, int[] paramArrayOfint) { int i = (int)(paramLong >>> 32L); if (i == 0) { int[] arrayOfInt1 = new int[1]; arrayOfInt1[0] = (int)(paramLong - (paramArrayOfint[0] & 0xFFFFFFFFL)); return arrayOfInt1; }  int[] arrayOfInt = new int[2]; if (paramArrayOfint.length == 1) { long l1 = ((int)paramLong & 0xFFFFFFFFL) - (paramArrayOfint[0] & 0xFFFFFFFFL); arrayOfInt[1] = (int)l1; boolean bool = (l1 >> 32L != 0L) ? true : false; if (bool) { arrayOfInt[0] = i - 1; } else { arrayOfInt[0] = i; }  return arrayOfInt; }  long l = ((int)paramLong & 0xFFFFFFFFL) - (paramArrayOfint[1] & 0xFFFFFFFFL); arrayOfInt[1] = (int)l; l = (i & 0xFFFFFFFFL) - (paramArrayOfint[0] & 0xFFFFFFFFL) + (l >> 32L); arrayOfInt[0] = (int)l; return arrayOfInt; }
/*      */   private static int[] subtract(int[] paramArrayOfint, long paramLong) { int i = (int)(paramLong >>> 32L); int j = paramArrayOfint.length; int[] arrayOfInt = new int[j]; long l = 0L; if (i == 0) { l = (paramArrayOfint[--j] & 0xFFFFFFFFL) - paramLong; arrayOfInt[j] = (int)l; } else { l = (paramArrayOfint[--j] & 0xFFFFFFFFL) - (paramLong & 0xFFFFFFFFL); arrayOfInt[j] = (int)l; l = (paramArrayOfint[--j] & 0xFFFFFFFFL) - (i & 0xFFFFFFFFL) + (l >> 32L); arrayOfInt[j] = (int)l; }  boolean bool = (l >> 32L != 0L) ? true : false; while (j > 0 && bool) { arrayOfInt[--j] = paramArrayOfint[j] - 1; bool = (paramArrayOfint[j] - 1 == -1) ? true : false; }  while (j > 0) arrayOfInt[--j] = paramArrayOfint[j];  return arrayOfInt; }
/*      */   public BigInteger subtract(BigInteger paramBigInteger) { if (paramBigInteger.signum == 0) return this;  if (this.signum == 0) return paramBigInteger.negate();  if (paramBigInteger.signum != this.signum) return new BigInteger(add(this.mag, paramBigInteger.mag), this.signum);  int i = compareMagnitude(paramBigInteger); if (i == 0) return ZERO;  int[] arrayOfInt = (i > 0) ? subtract(this.mag, paramBigInteger.mag) : subtract(paramBigInteger.mag, this.mag); arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt); return new BigInteger(arrayOfInt, (i == this.signum) ? 1 : -1); }
/*      */   private static int[] subtract(int[] paramArrayOfint1, int[] paramArrayOfint2) { int i = paramArrayOfint1.length; int[] arrayOfInt = new int[i]; int j = paramArrayOfint2.length; long l = 0L; while (j > 0) { l = (paramArrayOfint1[--i] & 0xFFFFFFFFL) - (paramArrayOfint2[--j] & 0xFFFFFFFFL) + (l >> 32L); arrayOfInt[i] = (int)l; }  boolean bool = (l >> 32L != 0L) ? true : false; while (i > 0 && bool) { arrayOfInt[--i] = paramArrayOfint1[i] - 1; bool = (paramArrayOfint1[i] - 1 == -1) ? true : false; }  while (i > 0) arrayOfInt[--i] = paramArrayOfint1[i];  return arrayOfInt; }
/*      */   public BigInteger multiply(BigInteger paramBigInteger) { return multiply(paramBigInteger, false); }
/*      */   private BigInteger multiply(BigInteger paramBigInteger, boolean paramBoolean) { if (paramBigInteger.signum == 0 || this.signum == 0) return ZERO;  int i = this.mag.length; if (paramBigInteger == this && i > 20) return square();  int j = paramBigInteger.mag.length; if (i < 80 || j < 80) { boolean bool = (this.signum == paramBigInteger.signum) ? true : true; if (paramBigInteger.mag.length == 1) return multiplyByInt(this.mag, paramBigInteger.mag[0], bool);  if (this.mag.length == 1) return multiplyByInt(paramBigInteger.mag, this.mag[0], bool);  int[] arrayOfInt = multiplyToLen(this.mag, i, paramBigInteger.mag, j, (int[])null); arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt); return new BigInteger(arrayOfInt, bool); }  if (i < 240 && j < 240) return multiplyKaratsuba(this, paramBigInteger);  if (!paramBoolean) if ((bitLength(this.mag, this.mag.length) + bitLength(paramBigInteger.mag, paramBigInteger.mag.length)) > 2147483648L) reportOverflow();   return multiplyToomCook3(this, paramBigInteger); }
/*      */   private static BigInteger multiplyByInt(int[] paramArrayOfint, int paramInt1, int paramInt2) { if (Integer.bitCount(paramInt1) == 1) return new BigInteger(shiftLeft(paramArrayOfint, Integer.numberOfTrailingZeros(paramInt1)), paramInt2);  int i = paramArrayOfint.length; int[] arrayOfInt = new int[i + 1]; long l1 = 0L; long l2 = paramInt1 & 0xFFFFFFFFL; int j = arrayOfInt.length - 1; for (int k = i - 1; k >= 0; k--) { long l = (paramArrayOfint[k] & 0xFFFFFFFFL) * l2 + l1; arrayOfInt[j--] = (int)l; l1 = l >>> 32L; }  if (l1 == 0L) { arrayOfInt = Arrays.copyOfRange(arrayOfInt, 1, arrayOfInt.length); } else { arrayOfInt[j] = (int)l1; }  return new BigInteger(arrayOfInt, paramInt2); }
/*      */   BigInteger multiply(long paramLong) { if (paramLong == 0L || this.signum == 0) return ZERO;  if (paramLong == Long.MIN_VALUE) return multiply(valueOf(paramLong));  int i = (paramLong > 0L) ? this.signum : -this.signum; if (paramLong < 0L) paramLong = -paramLong;  long l1 = paramLong >>> 32L; long l2 = paramLong & 0xFFFFFFFFL; int j = this.mag.length; int[] arrayOfInt1 = this.mag; int[] arrayOfInt2 = (l1 == 0L) ? new int[j + 1] : new int[j + 2]; long l3 = 0L; int k = arrayOfInt2.length - 1; int m; for (m = j - 1; m >= 0; m--) { long l = (arrayOfInt1[m] & 0xFFFFFFFFL) * l2 + l3; arrayOfInt2[k--] = (int)l; l3 = l >>> 32L; }  arrayOfInt2[k] = (int)l3; if (l1 != 0L) { l3 = 0L; k = arrayOfInt2.length - 2; for (m = j - 1; m >= 0; m--) { long l = (arrayOfInt1[m] & 0xFFFFFFFFL) * l1 + (arrayOfInt2[k] & 0xFFFFFFFFL) + l3; arrayOfInt2[k--] = (int)l; l3 = l >>> 32L; }  arrayOfInt2[0] = (int)l3; }  if (l3 == 0L) arrayOfInt2 = Arrays.copyOfRange(arrayOfInt2, 1, arrayOfInt2.length);  return new BigInteger(arrayOfInt2, i); }
/*      */   private static int[] multiplyToLen(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int[] paramArrayOfint3) { int i = paramInt1 - 1; int j = paramInt2 - 1; if (paramArrayOfint3 == null || paramArrayOfint3.length < paramInt1 + paramInt2) paramArrayOfint3 = new int[paramInt1 + paramInt2];  long l = 0L; int k, m; for (k = j, m = j + 1 + i; k >= 0; k--, m--) { long l1 = (paramArrayOfint2[k] & 0xFFFFFFFFL) * (paramArrayOfint1[i] & 0xFFFFFFFFL) + l; paramArrayOfint3[m] = (int)l1; l = l1 >>> 32L; }  paramArrayOfint3[i] = (int)l; for (k = i - 1; k >= 0; k--) { l = 0L; int n; for (m = j, n = j + 1 + k; m >= 0; m--, n--) { long l1 = (paramArrayOfint2[m] & 0xFFFFFFFFL) * (paramArrayOfint1[k] & 0xFFFFFFFFL) + (paramArrayOfint3[n] & 0xFFFFFFFFL) + l; paramArrayOfint3[n] = (int)l1; l = l1 >>> 32L; }  paramArrayOfint3[k] = (int)l; }  return paramArrayOfint3; }
/*      */   private static BigInteger multiplyKaratsuba(BigInteger paramBigInteger1, BigInteger paramBigInteger2) { int i = paramBigInteger1.mag.length; int j = paramBigInteger2.mag.length; int k = (Math.max(i, j) + 1) / 2; BigInteger bigInteger1 = paramBigInteger1.getLower(k); BigInteger bigInteger2 = paramBigInteger1.getUpper(k); BigInteger bigInteger3 = paramBigInteger2.getLower(k); BigInteger bigInteger4 = paramBigInteger2.getUpper(k); BigInteger bigInteger5 = bigInteger2.multiply(bigInteger4); BigInteger bigInteger6 = bigInteger1.multiply(bigInteger3); BigInteger bigInteger7 = bigInteger2.add(bigInteger1).multiply(bigInteger4.add(bigInteger3)); BigInteger bigInteger8 = bigInteger5.shiftLeft(32 * k).add(bigInteger7.subtract(bigInteger5).subtract(bigInteger6)).shiftLeft(32 * k).add(bigInteger6); if (paramBigInteger1.signum != paramBigInteger2.signum) return bigInteger8.negate();  return bigInteger8; }
/*      */   private static BigInteger multiplyToomCook3(BigInteger paramBigInteger1, BigInteger paramBigInteger2) { int i = paramBigInteger1.mag.length; int j = paramBigInteger2.mag.length; int k = Math.max(i, j); int m = (k + 2) / 3; int n = k - 2 * m; BigInteger bigInteger3 = paramBigInteger1.getToomSlice(m, n, 0, k); BigInteger bigInteger2 = paramBigInteger1.getToomSlice(m, n, 1, k); BigInteger bigInteger1 = paramBigInteger1.getToomSlice(m, n, 2, k); BigInteger bigInteger6 = paramBigInteger2.getToomSlice(m, n, 0, k); BigInteger bigInteger5 = paramBigInteger2.getToomSlice(m, n, 1, k); BigInteger bigInteger4 = paramBigInteger2.getToomSlice(m, n, 2, k); BigInteger bigInteger7 = bigInteger1.multiply(bigInteger4, true); BigInteger bigInteger15 = bigInteger3.add(bigInteger1); BigInteger bigInteger16 = bigInteger6.add(bigInteger4); BigInteger bigInteger10 = bigInteger15.subtract(bigInteger2).multiply(bigInteger16.subtract(bigInteger5), true); bigInteger15 = bigInteger15.add(bigInteger2); bigInteger16 = bigInteger16.add(bigInteger5); BigInteger bigInteger8 = bigInteger15.multiply(bigInteger16, true); BigInteger bigInteger9 = bigInteger15.add(bigInteger3).shiftLeft(1).subtract(bigInteger1).multiply(bigInteger16.add(bigInteger6).shiftLeft(1).subtract(bigInteger4), true); BigInteger bigInteger11 = bigInteger3.multiply(bigInteger6, true); BigInteger bigInteger13 = bigInteger9.subtract(bigInteger10).exactDivideBy3(); BigInteger bigInteger14 = bigInteger8.subtract(bigInteger10).shiftRight(1); BigInteger bigInteger12 = bigInteger8.subtract(bigInteger7); bigInteger13 = bigInteger13.subtract(bigInteger12).shiftRight(1); bigInteger12 = bigInteger12.subtract(bigInteger14).subtract(bigInteger11); bigInteger13 = bigInteger13.subtract(bigInteger11.shiftLeft(1)); bigInteger14 = bigInteger14.subtract(bigInteger13); int i1 = m * 32; BigInteger bigInteger17 = bigInteger11.shiftLeft(i1).add(bigInteger13).shiftLeft(i1).add(bigInteger12).shiftLeft(i1).add(bigInteger14).shiftLeft(i1).add(bigInteger7); if (paramBigInteger1.signum != paramBigInteger2.signum) return bigInteger17.negate();  return bigInteger17; }
/*      */   private BigInteger getToomSlice(int paramInt1, int paramInt2, int paramInt3, int paramInt4) { int i, j, m = this.mag.length; int n = paramInt4 - m; if (paramInt3 == 0) { i = 0 - n; j = paramInt2 - 1 - n; } else { i = paramInt2 + (paramInt3 - 1) * paramInt1 - n; j = i + paramInt1 - 1; }  if (i < 0) i = 0;  if (j < 0) return ZERO;  int k = j - i + 1; if (k <= 0) return ZERO;  if (i == 0 && k >= m) return abs();  int[] arrayOfInt = new int[k]; System.arraycopy(this.mag, i, arrayOfInt, 0, k); return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1); }
/*      */   private BigInteger exactDivideBy3() { int i = this.mag.length; int[] arrayOfInt = new int[i]; long l = 0L; for (int j = i - 1; j >= 0; j--) { long l1 = this.mag[j] & 0xFFFFFFFFL; long l2 = l1 - l; if (l > l1) { l = 1L; } else { l = 0L; }  long l3 = l2 * 2863311531L & 0xFFFFFFFFL; arrayOfInt[j] = (int)l3; if (l3 >= 1431655766L) { l++; if (l3 >= 2863311531L) l++;  }  }  arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt); return new BigInteger(arrayOfInt, this.signum); }
/*      */   private BigInteger getLower(int paramInt) { int i = this.mag.length; if (i <= paramInt) return abs();  int[] arrayOfInt = new int[paramInt]; System.arraycopy(this.mag, i - paramInt, arrayOfInt, 0, paramInt); return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1); }
/*      */   private BigInteger getUpper(int paramInt) { int i = this.mag.length; if (i <= paramInt) return ZERO;  int j = i - paramInt; int[] arrayOfInt = new int[j]; System.arraycopy(this.mag, 0, arrayOfInt, 0, j); return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1); }
/*      */   private BigInteger square() { return square(false); }
/*      */   private BigInteger square(boolean paramBoolean) { if (this.signum == 0) return ZERO;  int i = this.mag.length; if (i < 128) { int[] arrayOfInt = squareToLen(this.mag, i, (int[])null); return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1); }  if (i < 216) return squareKaratsuba();  if (!paramBoolean && bitLength(this.mag, this.mag.length) > 1073741824L) reportOverflow();  return squareToomCook3(); }
/*      */   private static final int[] squareToLen(int[] paramArrayOfint1, int paramInt, int[] paramArrayOfint2) { int i = paramInt << 1; if (paramArrayOfint2 == null || paramArrayOfint2.length < i) paramArrayOfint2 = new int[i];  implSquareToLenChecks(paramArrayOfint1, paramInt, paramArrayOfint2, i); return implSquareToLen(paramArrayOfint1, paramInt, paramArrayOfint2, i); }
/*      */   private static void implSquareToLenChecks(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2) throws RuntimeException { if (paramInt1 < 1) throw new IllegalArgumentException("invalid input length: " + paramInt1);  if (paramInt1 > paramArrayOfint1.length) throw new IllegalArgumentException("input length out of bound: " + paramInt1 + " > " + paramArrayOfint1.length);  if (paramInt1 * 2 > paramArrayOfint2.length) throw new IllegalArgumentException("input length out of bound: " + (paramInt1 * 2) + " > " + paramArrayOfint2.length);  if (paramInt2 < 1) throw new IllegalArgumentException("invalid input length: " + paramInt2);  if (paramInt2 > paramArrayOfint2.length) throw new IllegalArgumentException("input length out of bound: " + paramInt1 + " > " + paramArrayOfint2.length);  }
/*      */   private static final int[] implSquareToLen(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2) { int i = 0; int j; byte b; for (j = 0, b = 0; j < paramInt1; j++) { long l1 = paramArrayOfint1[j] & 0xFFFFFFFFL; long l2 = l1 * l1; paramArrayOfint2[b++] = i << 31 | (int)(l2 >>> 33L); paramArrayOfint2[b++] = (int)(l2 >>> 1L); i = (int)l2; }  for (j = paramInt1, b = 1; j > 0; j--, b += 2) { int k = paramArrayOfint1[j - 1]; k = mulAdd(paramArrayOfint2, paramArrayOfint1, b, j - 1, k); addOne(paramArrayOfint2, b - 1, j, k); }  primitiveLeftShift(paramArrayOfint2, paramInt2, 1); paramArrayOfint2[paramInt2 - 1] = paramArrayOfint2[paramInt2 - 1] | paramArrayOfint1[paramInt1 - 1] & 0x1; return paramArrayOfint2; }
/*      */   private BigInteger squareKaratsuba() { int i = (this.mag.length + 1) / 2; BigInteger bigInteger1 = getLower(i); BigInteger bigInteger2 = getUpper(i); BigInteger bigInteger3 = bigInteger2.square(); BigInteger bigInteger4 = bigInteger1.square(); return bigInteger3.shiftLeft(i * 32).add(bigInteger1.add(bigInteger2).square().subtract(bigInteger3.add(bigInteger4))).shiftLeft(i * 32).add(bigInteger4); }
/*      */   private BigInteger squareToomCook3() { int i = this.mag.length; int j = (i + 2) / 3; int k = i - 2 * j; BigInteger bigInteger3 = getToomSlice(j, k, 0, i); BigInteger bigInteger2 = getToomSlice(j, k, 1, i); BigInteger bigInteger1 = getToomSlice(j, k, 2, i); BigInteger bigInteger4 = bigInteger1.square(true); BigInteger bigInteger12 = bigInteger3.add(bigInteger1); BigInteger bigInteger7 = bigInteger12.subtract(bigInteger2).square(true); bigInteger12 = bigInteger12.add(bigInteger2); BigInteger bigInteger5 = bigInteger12.square(true); BigInteger bigInteger8 = bigInteger3.square(true); BigInteger bigInteger6 = bigInteger12.add(bigInteger3).shiftLeft(1).subtract(bigInteger1).square(true); BigInteger bigInteger10 = bigInteger6.subtract(bigInteger7).exactDivideBy3(); BigInteger bigInteger11 = bigInteger5.subtract(bigInteger7).shiftRight(1); BigInteger bigInteger9 = bigInteger5.subtract(bigInteger4); bigInteger10 = bigInteger10.subtract(bigInteger9).shiftRight(1); bigInteger9 = bigInteger9.subtract(bigInteger11).subtract(bigInteger8); bigInteger10 = bigInteger10.subtract(bigInteger8.shiftLeft(1)); bigInteger11 = bigInteger11.subtract(bigInteger10); int m = j * 32; return bigInteger8.shiftLeft(m).add(bigInteger10).shiftLeft(m).add(bigInteger9).shiftLeft(m).add(bigInteger11).shiftLeft(m).add(bigInteger4); }
/*      */   public BigInteger divide(BigInteger paramBigInteger) { if (paramBigInteger.mag.length < 80 || this.mag.length - paramBigInteger.mag.length < 40) return divideKnuth(paramBigInteger);  return divideBurnikelZiegler(paramBigInteger); }
/*      */   private BigInteger divideKnuth(BigInteger paramBigInteger) { MutableBigInteger mutableBigInteger1 = new MutableBigInteger(); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(this.mag); MutableBigInteger mutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag); mutableBigInteger2.divideKnuth(mutableBigInteger3, mutableBigInteger1, false); return mutableBigInteger1.toBigInteger(this.signum * paramBigInteger.signum); }
/*      */   public BigInteger[] divideAndRemainder(BigInteger paramBigInteger) { if (paramBigInteger.mag.length < 80 || this.mag.length - paramBigInteger.mag.length < 40) return divideAndRemainderKnuth(paramBigInteger);  return divideAndRemainderBurnikelZiegler(paramBigInteger); }
/*      */   private BigInteger[] divideAndRemainderKnuth(BigInteger paramBigInteger) { BigInteger[] arrayOfBigInteger = new BigInteger[2]; MutableBigInteger mutableBigInteger1 = new MutableBigInteger(); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(this.mag); MutableBigInteger mutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag); MutableBigInteger mutableBigInteger4 = mutableBigInteger2.divideKnuth(mutableBigInteger3, mutableBigInteger1); arrayOfBigInteger[0] = mutableBigInteger1.toBigInteger((this.signum == paramBigInteger.signum) ? 1 : -1); arrayOfBigInteger[1] = mutableBigInteger4.toBigInteger(this.signum); return arrayOfBigInteger; }
/*      */   public BigInteger remainder(BigInteger paramBigInteger) { if (paramBigInteger.mag.length < 80 || this.mag.length - paramBigInteger.mag.length < 40) return remainderKnuth(paramBigInteger);  return remainderBurnikelZiegler(paramBigInteger); }
/* 4507 */   private BigInteger remainderKnuth(BigInteger paramBigInteger) { MutableBigInteger mutableBigInteger1 = new MutableBigInteger(); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(this.mag); MutableBigInteger mutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag); return mutableBigInteger2.divideKnuth(mutableBigInteger3, mutableBigInteger1).toBigInteger(this.signum); } private BigInteger divideBurnikelZiegler(BigInteger paramBigInteger) { return divideAndRemainderBurnikelZiegler(paramBigInteger)[0]; } private BigInteger remainderBurnikelZiegler(BigInteger paramBigInteger) { return divideAndRemainderBurnikelZiegler(paramBigInteger)[1]; } private BigInteger[] divideAndRemainderBurnikelZiegler(BigInteger paramBigInteger) { MutableBigInteger mutableBigInteger1 = new MutableBigInteger(); MutableBigInteger mutableBigInteger2 = (new MutableBigInteger(this)).divideAndRemainderBurnikelZiegler(new MutableBigInteger(paramBigInteger), mutableBigInteger1); BigInteger bigInteger1 = mutableBigInteger1.isZero() ? ZERO : mutableBigInteger1.toBigInteger(this.signum * paramBigInteger.signum); BigInteger bigInteger2 = mutableBigInteger2.isZero() ? ZERO : mutableBigInteger2.toBigInteger(this.signum); return new BigInteger[] { bigInteger1, bigInteger2 }; } public BigInteger pow(int paramInt) { int k; if (paramInt < 0) throw new ArithmeticException("Negative exponent");  if (this.signum == 0) return (paramInt == 0) ? ONE : this;  BigInteger bigInteger1 = abs(); int i = bigInteger1.getLowestSetBit(); long l1 = i * paramInt; if (l1 > 2147483647L) reportOverflow();  int j = (int)l1; if (i > 0) { bigInteger1 = bigInteger1.shiftRight(i); k = bigInteger1.bitLength(); if (k == 1) { if (this.signum < 0 && (paramInt & 0x1) == 1) return NEGATIVE_ONE.shiftLeft(j);  return ONE.shiftLeft(j); }  } else { k = bigInteger1.bitLength(); if (k == 1) { if (this.signum < 0 && (paramInt & 0x1) == 1) return NEGATIVE_ONE;  return ONE; }  }  long l2 = k * paramInt; if (bigInteger1.mag.length == 1 && l2 <= 62L) { boolean bool = (this.signum < 0 && (paramInt & 0x1) == 1) ? true : true; long l3 = 1L; long l4 = bigInteger1.mag[0] & 0xFFFFFFFFL; int n = paramInt; while (n != 0) { if ((n & 0x1) == 1) l3 *= l4;  if ((n >>>= 1) != 0) l4 *= l4;  }  if (i > 0) { if (j + l2 <= 62L) return valueOf((l3 << j) * bool);  return valueOf(l3 * bool).shiftLeft(j); }  return valueOf(l3 * bool); }  if (bitLength() * paramInt / 32L > 67108864L) reportOverflow();  BigInteger bigInteger2 = ONE; int m = paramInt; while (m != 0) { if ((m & 0x1) == 1) bigInteger2 = bigInteger2.multiply(bigInteger1);  if ((m >>>= 1) != 0) bigInteger1 = bigInteger1.square();  }  if (i > 0) bigInteger2 = bigInteger2.shiftLeft(j);  if (this.signum < 0 && (paramInt & 0x1) == 1) return bigInteger2.negate();  return bigInteger2; } public BigInteger gcd(BigInteger paramBigInteger) { if (paramBigInteger.signum == 0) return abs();  if (this.signum == 0) return paramBigInteger.abs();  MutableBigInteger mutableBigInteger1 = new MutableBigInteger(this); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(paramBigInteger); MutableBigInteger mutableBigInteger3 = mutableBigInteger1.hybridGCD(mutableBigInteger2); return mutableBigInteger3.toBigInteger(1); } static int bitLengthForInt(int paramInt) { return 32 - Integer.numberOfLeadingZeros(paramInt); } private static int[] leftShift(int[] paramArrayOfint, int paramInt1, int paramInt2) { int i = paramInt2 >>> 5; int j = paramInt2 & 0x1F; int k = bitLengthForInt(paramArrayOfint[0]); if (paramInt2 <= 32 - k) { primitiveLeftShift(paramArrayOfint, paramInt1, j); return paramArrayOfint; }  if (j <= 32 - k) { int[] arrayOfInt1 = new int[i + paramInt1]; System.arraycopy(paramArrayOfint, 0, arrayOfInt1, 0, paramInt1); primitiveLeftShift(arrayOfInt1, arrayOfInt1.length, j); return arrayOfInt1; }  int[] arrayOfInt = new int[i + paramInt1 + 1]; System.arraycopy(paramArrayOfint, 0, arrayOfInt, 0, paramInt1); primitiveRightShift(arrayOfInt, arrayOfInt.length, 32 - j); return arrayOfInt; } static void primitiveRightShift(int[] paramArrayOfint, int paramInt1, int paramInt2) { int i = 32 - paramInt2; for (int j = paramInt1 - 1, k = paramArrayOfint[j]; j > 0; j--) { int m = k; k = paramArrayOfint[j - 1]; paramArrayOfint[j] = k << i | m >>> paramInt2; }  paramArrayOfint[0] = paramArrayOfint[0] >>> paramInt2; } static void primitiveLeftShift(int[] paramArrayOfint, int paramInt1, int paramInt2) { if (paramInt1 == 0 || paramInt2 == 0) return;  int i = 32 - paramInt2; byte b; int j, k; for (b = 0, j = paramArrayOfint[b], k = b + paramInt1 - 1; b < k; b++) { int m = j; j = paramArrayOfint[b + 1]; paramArrayOfint[b] = m << paramInt2 | j >>> i; }  paramArrayOfint[paramInt1 - 1] = paramArrayOfint[paramInt1 - 1] << paramInt2; } private static int bitLength(int[] paramArrayOfint, int paramInt) { if (paramInt == 0) return 0;  return (paramInt - 1 << 5) + bitLengthForInt(paramArrayOfint[0]); } public BigInteger abs() { return (this.signum >= 0) ? this : negate(); } public BigInteger negate() { return new BigInteger(this.mag, -this.signum); } public int signum() { return this.signum; } public BigInteger mod(BigInteger paramBigInteger) { if (paramBigInteger.signum <= 0) throw new ArithmeticException("BigInteger: modulus not positive");  BigInteger bigInteger = remainder(paramBigInteger); return (bigInteger.signum >= 0) ? bigInteger : bigInteger.add(paramBigInteger); } public BigInteger modPow(BigInteger paramBigInteger1, BigInteger paramBigInteger2) { BigInteger bigInteger2; if (paramBigInteger2.signum <= 0) throw new ArithmeticException("BigInteger: modulus not positive");  if (paramBigInteger1.signum == 0) return paramBigInteger2.equals(ONE) ? ZERO : ONE;  if (equals(ONE)) return paramBigInteger2.equals(ONE) ? ZERO : ONE;  if (equals(ZERO) && paramBigInteger1.signum >= 0) return ZERO;  if (equals(negConst[1]) && !paramBigInteger1.testBit(0)) return paramBigInteger2.equals(ONE) ? ZERO : ONE;  boolean bool; if (bool = (paramBigInteger1.signum < 0) ? true : false) paramBigInteger1 = paramBigInteger1.negate();  BigInteger bigInteger1 = (this.signum < 0 || compareTo(paramBigInteger2) >= 0) ? mod(paramBigInteger2) : this; if (paramBigInteger2.testBit(0)) { bigInteger2 = bigInteger1.oddModPow(paramBigInteger1, paramBigInteger2); } else { int i = paramBigInteger2.getLowestSetBit(); BigInteger bigInteger3 = paramBigInteger2.shiftRight(i); BigInteger bigInteger4 = ONE.shiftLeft(i); BigInteger bigInteger5 = (this.signum < 0 || compareTo(bigInteger3) >= 0) ? mod(bigInteger3) : this; BigInteger bigInteger6 = bigInteger3.equals(ONE) ? ZERO : bigInteger5.oddModPow(paramBigInteger1, bigInteger3); BigInteger bigInteger7 = bigInteger1.modPow2(paramBigInteger1, i); BigInteger bigInteger8 = bigInteger4.modInverse(bigInteger3); BigInteger bigInteger9 = bigInteger3.modInverse(bigInteger4); if (paramBigInteger2.mag.length < 33554432) { bigInteger2 = bigInteger6.multiply(bigInteger4).multiply(bigInteger8).add(bigInteger7.multiply(bigInteger3).multiply(bigInteger9)).mod(paramBigInteger2); } else { MutableBigInteger mutableBigInteger1 = new MutableBigInteger(); (new MutableBigInteger(bigInteger6.multiply(bigInteger4))).multiply(new MutableBigInteger(bigInteger8), mutableBigInteger1); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(); (new MutableBigInteger(bigInteger7.multiply(bigInteger3))).multiply(new MutableBigInteger(bigInteger9), mutableBigInteger2); mutableBigInteger1.add(mutableBigInteger2); MutableBigInteger mutableBigInteger3 = new MutableBigInteger(); bigInteger2 = mutableBigInteger1.divide(new MutableBigInteger(paramBigInteger2), mutableBigInteger3).toBigInteger(); }  }  return bool ? bigInteger2.modInverse(paramBigInteger2) : bigInteger2; } private static int[] montgomeryMultiply(int[] paramArrayOfint1, int[] paramArrayOfint2, int[] paramArrayOfint3, int paramInt, long paramLong, int[] paramArrayOfint4) { implMontgomeryMultiplyChecks(paramArrayOfint1, paramArrayOfint2, paramArrayOfint3, paramInt, paramArrayOfint4); if (paramInt > 512) { paramArrayOfint4 = multiplyToLen(paramArrayOfint1, paramInt, paramArrayOfint2, paramInt, paramArrayOfint4); return montReduce(paramArrayOfint4, paramArrayOfint3, paramInt, (int)paramLong); }  return implMontgomeryMultiply(paramArrayOfint1, paramArrayOfint2, paramArrayOfint3, paramInt, paramLong, materialize(paramArrayOfint4, paramInt)); } private static int[] montgomerySquare(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt, long paramLong, int[] paramArrayOfint3) { implMontgomeryMultiplyChecks(paramArrayOfint1, paramArrayOfint1, paramArrayOfint2, paramInt, paramArrayOfint3); if (paramInt > 512) { paramArrayOfint3 = squareToLen(paramArrayOfint1, paramInt, paramArrayOfint3); return montReduce(paramArrayOfint3, paramArrayOfint2, paramInt, (int)paramLong); }  return implMontgomerySquare(paramArrayOfint1, paramArrayOfint2, paramInt, paramLong, materialize(paramArrayOfint3, paramInt)); } private static void implMontgomeryMultiplyChecks(int[] paramArrayOfint1, int[] paramArrayOfint2, int[] paramArrayOfint3, int paramInt, int[] paramArrayOfint4) throws RuntimeException { if (paramInt % 2 != 0) throw new IllegalArgumentException("input array length must be even: " + paramInt);  if (paramInt < 1) throw new IllegalArgumentException("invalid input length: " + paramInt);  if (paramInt > paramArrayOfint1.length || paramInt > paramArrayOfint2.length || paramInt > paramArrayOfint3.length || (paramArrayOfint4 != null && paramInt > paramArrayOfint4.length)) throw new IllegalArgumentException("input array length out of bound: " + paramInt);  } private static int[] materialize(int[] paramArrayOfint, int paramInt) { if (paramArrayOfint == null || paramArrayOfint.length < paramInt) paramArrayOfint = new int[paramInt];  return paramArrayOfint; } private static int[] implMontgomeryMultiply(int[] paramArrayOfint1, int[] paramArrayOfint2, int[] paramArrayOfint3, int paramInt, long paramLong, int[] paramArrayOfint4) { paramArrayOfint4 = multiplyToLen(paramArrayOfint1, paramInt, paramArrayOfint2, paramInt, paramArrayOfint4); return montReduce(paramArrayOfint4, paramArrayOfint3, paramInt, (int)paramLong); } private static int[] implMontgomerySquare(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt, long paramLong, int[] paramArrayOfint3) { paramArrayOfint3 = squareToLen(paramArrayOfint1, paramInt, paramArrayOfint3); return montReduce(paramArrayOfint3, paramArrayOfint2, paramInt, (int)paramLong); } private BigInteger oddModPow(BigInteger paramBigInteger1, BigInteger paramBigInteger2) { if (paramBigInteger1.equals(ONE)) return this;  if (this.signum == 0) return ZERO;  int[] arrayOfInt1 = (int[])this.mag.clone(); int[] arrayOfInt2 = paramBigInteger1.mag; int[] arrayOfInt3 = paramBigInteger2.mag; int i = arrayOfInt3.length; if ((i & 0x1) != 0) { int[] arrayOfInt9 = new int[i + 1]; System.arraycopy(arrayOfInt3, 0, arrayOfInt9, 1, i); arrayOfInt3 = arrayOfInt9; i++; }  byte b1 = 0; int j = bitLength(arrayOfInt2, arrayOfInt2.length); if (j != 17 || arrayOfInt2[0] != 65537) while (j > bnExpModThreshTable[b1]) b1++;   int k = 1 << b1; int[][] arrayOfInt = new int[k][]; for (byte b2 = 0; b2 < k; b2++) arrayOfInt[b2] = new int[i];  long l1 = (arrayOfInt3[i - 1] & 0xFFFFFFFFL) + ((arrayOfInt3[i - 2] & 0xFFFFFFFFL) << 32L); long l2 = -MutableBigInteger.inverseMod64(l1); int[] arrayOfInt4 = leftShift(arrayOfInt1, arrayOfInt1.length, i << 5); MutableBigInteger mutableBigInteger1 = new MutableBigInteger(); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(arrayOfInt4); MutableBigInteger mutableBigInteger3 = new MutableBigInteger(arrayOfInt3); mutableBigInteger3.normalize(); MutableBigInteger mutableBigInteger4 = mutableBigInteger2.divide(mutableBigInteger3, mutableBigInteger1); arrayOfInt[0] = mutableBigInteger4.toIntArray(); if ((arrayOfInt[0]).length < i) { int i3 = i - (arrayOfInt[0]).length; int[] arrayOfInt9 = new int[i]; System.arraycopy(arrayOfInt[0], 0, arrayOfInt9, i3, (arrayOfInt[0]).length); arrayOfInt[0] = arrayOfInt9; }  int[] arrayOfInt5 = montgomerySquare(arrayOfInt[0], arrayOfInt3, i, l2, (int[])null); int[] arrayOfInt6 = Arrays.copyOf(arrayOfInt5, i); int m; for (m = 1; m < k; m++) arrayOfInt[m] = montgomeryMultiply(arrayOfInt6, arrayOfInt[m - 1], arrayOfInt3, i, l2, (int[])null);  m = 1 << (j - 1 & 0x1F); int n = 0; int i1 = arrayOfInt2.length; byte b3 = 0; int i2; for (i2 = 0; i2 <= b1; i2++) { n = n << 1 | (((arrayOfInt2[b3] & m) != 0) ? 1 : 0); m >>>= 1; if (m == 0) { b3++; m = Integer.MIN_VALUE; i1--; }  }  i2 = j; j--; boolean bool = true; i2 = j - b1; while ((n & 0x1) == 0) { n >>>= 1; i2++; }  int[] arrayOfInt7 = arrayOfInt[n >>> 1]; n = 0; if (i2 == j) bool = false;  while (true) { j--; n <<= 1; if (i1 != 0) { n |= ((arrayOfInt2[b3] & m) != 0) ? 1 : 0; m >>>= 1; if (m == 0) { b3++; m = Integer.MIN_VALUE; i1--; }  }  if ((n & k) != 0) { i2 = j - b1; while ((n & 0x1) == 0) { n >>>= 1; i2++; }  arrayOfInt7 = arrayOfInt[n >>> 1]; n = 0; }  if (j == i2) if (bool) { arrayOfInt5 = (int[])arrayOfInt7.clone(); bool = false; } else { arrayOfInt6 = arrayOfInt5; arrayOfInt4 = montgomeryMultiply(arrayOfInt6, arrayOfInt7, arrayOfInt3, i, l2, arrayOfInt4); arrayOfInt6 = arrayOfInt4; arrayOfInt4 = arrayOfInt5; arrayOfInt5 = arrayOfInt6; }   if (j == 0) break;  if (!bool) { arrayOfInt6 = arrayOfInt5; arrayOfInt4 = montgomerySquare(arrayOfInt6, arrayOfInt3, i, l2, arrayOfInt4); arrayOfInt6 = arrayOfInt4; arrayOfInt4 = arrayOfInt5; arrayOfInt5 = arrayOfInt6; }  }  int[] arrayOfInt8 = new int[2 * i]; System.arraycopy(arrayOfInt5, 0, arrayOfInt8, i, i); arrayOfInt5 = montReduce(arrayOfInt8, arrayOfInt3, i, (int)l2); arrayOfInt8 = Arrays.copyOf(arrayOfInt5, i); return new BigInteger(1, arrayOfInt8); } private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException { ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*      */ 
/*      */     
/* 4510 */     int i = getField.get("signum", -2);
/* 4511 */     byte[] arrayOfByte = (byte[])getField.get("magnitude", (Object)null);
/*      */ 
/*      */     
/* 4514 */     if (i < -1 || i > 1) {
/* 4515 */       String str = "BigInteger: Invalid signum value";
/* 4516 */       if (getField.defaulted("signum"))
/* 4517 */         str = "BigInteger: Signum not present in stream"; 
/* 4518 */       throw new StreamCorruptedException(str);
/*      */     } 
/* 4520 */     int[] arrayOfInt = stripLeadingZeroBytes(arrayOfByte);
/* 4521 */     if (((arrayOfInt.length == 0) ? true : false) != ((i == 0) ? true : false)) {
/* 4522 */       String str = "BigInteger: signum-magnitude mismatch";
/* 4523 */       if (getField.defaulted("magnitude"))
/* 4524 */         str = "BigInteger: Magnitude not present in stream"; 
/* 4525 */       throw new StreamCorruptedException(str);
/*      */     } 
/*      */ 
/*      */     
/* 4529 */     UnsafeHolder.putSign(this, i);
/*      */ 
/*      */     
/* 4532 */     UnsafeHolder.putMag(this, arrayOfInt);
/* 4533 */     if (arrayOfInt.length >= 67108864)
/*      */       
/* 4535 */       try { checkRange(); }
/* 4536 */       catch (ArithmeticException arithmeticException)
/* 4537 */       { throw new StreamCorruptedException("BigInteger: Out of the supported range"); }   }
/*      */   private static int[] montReduce(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2) { int i = 0; int j = paramInt1; byte b = 0; do { int k = paramArrayOfint1[paramArrayOfint1.length - 1 - b]; int m = mulAdd(paramArrayOfint1, paramArrayOfint2, b, paramInt1, paramInt2 * k); i += addOne(paramArrayOfint1, b, paramInt1, m); b++; } while (--j > 0); while (i > 0) i += subN(paramArrayOfint1, paramArrayOfint2, paramInt1);  while (intArrayCmpToLen(paramArrayOfint1, paramArrayOfint2, paramInt1) >= 0) subN(paramArrayOfint1, paramArrayOfint2, paramInt1);  return paramArrayOfint1; }
/*      */   private static int intArrayCmpToLen(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) { for (byte b = 0; b < paramInt; b++) { long l1 = paramArrayOfint1[b] & 0xFFFFFFFFL; long l2 = paramArrayOfint2[b] & 0xFFFFFFFFL; if (l1 < l2) return -1;  if (l1 > l2) return 1;  }  return 0; }
/*      */   private static int subN(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) { long l = 0L; while (--paramInt >= 0) { l = (paramArrayOfint1[paramInt] & 0xFFFFFFFFL) - (paramArrayOfint2[paramInt] & 0xFFFFFFFFL) + (l >> 32L); paramArrayOfint1[paramInt] = (int)l; }  return (int)(l >> 32L); }
/*      */   static int mulAdd(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3) { implMulAddCheck(paramArrayOfint1, paramArrayOfint2, paramInt1, paramInt2, paramInt3); return implMulAdd(paramArrayOfint1, paramArrayOfint2, paramInt1, paramInt2, paramInt3); }
/*      */   private static void implMulAddCheck(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3) { if (paramInt2 > paramArrayOfint2.length) throw new IllegalArgumentException("input length is out of bound: " + paramInt2 + " > " + paramArrayOfint2.length);  if (paramInt1 < 0) throw new IllegalArgumentException("input offset is invalid: " + paramInt1);  if (paramInt1 > paramArrayOfint1.length - 1) throw new IllegalArgumentException("input offset is out of bound: " + paramInt1 + " > " + (paramArrayOfint1.length - 1));  if (paramInt2 > paramArrayOfint1.length - paramInt1) throw new IllegalArgumentException("input len is out of bound: " + paramInt2 + " > " + (paramArrayOfint1.length - paramInt1));  }
/*      */   private static int implMulAdd(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3) { long l1 = paramInt3 & 0xFFFFFFFFL; long l2 = 0L; paramInt1 = paramArrayOfint1.length - paramInt1 - 1; for (int i = paramInt2 - 1; i >= 0; i--) { long l = (paramArrayOfint2[i] & 0xFFFFFFFFL) * l1 + (paramArrayOfint1[paramInt1] & 0xFFFFFFFFL) + l2; paramArrayOfint1[paramInt1--] = (int)l; l2 = l >>> 32L; }  return (int)l2; }
/*      */   static int addOne(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) { paramInt1 = paramArrayOfint.length - 1 - paramInt2 - paramInt1; long l = (paramArrayOfint[paramInt1] & 0xFFFFFFFFL) + (paramInt3 & 0xFFFFFFFFL); paramArrayOfint[paramInt1] = (int)l; if (l >>> 32L == 0L) return 0;  while (--paramInt2 >= 0) { if (--paramInt1 < 0) return 1;  paramArrayOfint[paramInt1] = paramArrayOfint[paramInt1] + 1; if (paramArrayOfint[paramInt1] != 0) return 0;  }  return 1; }
/*      */   private BigInteger modPow2(BigInteger paramBigInteger, int paramInt) { BigInteger bigInteger1 = ONE; BigInteger bigInteger2 = mod2(paramInt); byte b = 0; int i = paramBigInteger.bitLength(); if (testBit(0)) i = (paramInt - 1 < i) ? (paramInt - 1) : i;  while (b < i) { if (paramBigInteger.testBit(b)) bigInteger1 = bigInteger1.multiply(bigInteger2).mod2(paramInt);  b++; if (b < i) bigInteger2 = bigInteger2.square().mod2(paramInt);  }  return bigInteger1; }
/*      */   private BigInteger mod2(int paramInt) { if (bitLength() <= paramInt) return this;  int i = paramInt + 31 >>> 5; int[] arrayOfInt = new int[i]; System.arraycopy(this.mag, this.mag.length - i, arrayOfInt, 0, i); int j = (i << 5) - paramInt; arrayOfInt[0] = (int)(arrayOfInt[0] & (1L << 32 - j) - 1L); return (arrayOfInt[0] == 0) ? new BigInteger(1, arrayOfInt) : new BigInteger(arrayOfInt, 1); }
/*      */   public BigInteger modInverse(BigInteger paramBigInteger) { if (paramBigInteger.signum != 1) throw new ArithmeticException("BigInteger: modulus not positive");  if (paramBigInteger.equals(ONE)) return ZERO;  BigInteger bigInteger = this; if (this.signum < 0 || compareMagnitude(paramBigInteger) >= 0) bigInteger = mod(paramBigInteger);  if (bigInteger.equals(ONE)) return ONE;  MutableBigInteger mutableBigInteger1 = new MutableBigInteger(bigInteger); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(paramBigInteger); MutableBigInteger mutableBigInteger3 = mutableBigInteger1.mutableModInverse(mutableBigInteger2); return mutableBigInteger3.toBigInteger(1); } public BigInteger shiftLeft(int paramInt) { if (this.signum == 0) return ZERO;  if (paramInt > 0) return new BigInteger(shiftLeft(this.mag, paramInt), this.signum);  if (paramInt == 0) return this;  return shiftRightImpl(-paramInt); } private static int[] shiftLeft(int[] paramArrayOfint, int paramInt) { int i = paramInt >>> 5; int j = paramInt & 0x1F; int k = paramArrayOfint.length; int[] arrayOfInt = null; if (j == 0) { arrayOfInt = new int[k + i]; System.arraycopy(paramArrayOfint, 0, arrayOfInt, 0, k); } else { byte b1 = 0; int m = 32 - j; int n = paramArrayOfint[0] >>> m; if (n != 0) { arrayOfInt = new int[k + i + 1]; arrayOfInt[b1++] = n; } else { arrayOfInt = new int[k + i]; }  byte b2 = 0; while (b2 < k - 1) arrayOfInt[b1++] = paramArrayOfint[b2++] << j | paramArrayOfint[b2] >>> m;  arrayOfInt[b1] = paramArrayOfint[b2] << j; }  return arrayOfInt; } public BigInteger shiftRight(int paramInt) { if (this.signum == 0) return ZERO;  if (paramInt > 0) return shiftRightImpl(paramInt);  if (paramInt == 0) return this;  return new BigInteger(shiftLeft(this.mag, -paramInt), this.signum); } private BigInteger shiftRightImpl(int paramInt) { int i = paramInt >>> 5; int j = paramInt & 0x1F; int k = this.mag.length; int[] arrayOfInt = null; if (i >= k) return (this.signum >= 0) ? ZERO : negConst[1];  if (j == 0) { int m = k - i; arrayOfInt = Arrays.copyOf(this.mag, m); } else { byte b1 = 0; int m = this.mag[0] >>> j; if (m != 0) { arrayOfInt = new int[k - i]; arrayOfInt[b1++] = m; } else { arrayOfInt = new int[k - i - 1]; }  int n = 32 - j; byte b2 = 0; while (b2 < k - i - 1) arrayOfInt[b1++] = this.mag[b2++] << n | this.mag[b2] >>> j;  }  if (this.signum < 0) { boolean bool = false; for (int m = k - 1, n = k - i; m >= n && !bool; m--) bool = (this.mag[m] != 0) ? true : false;  if (!bool && j != 0) bool = (this.mag[k - i - 1] << 32 - j != 0) ? true : false;  if (bool) arrayOfInt = javaIncrement(arrayOfInt);  }  return new BigInteger(arrayOfInt, this.signum); } int[] javaIncrement(int[] paramArrayOfint) { int i = 0; for (int j = paramArrayOfint.length - 1; j >= 0 && !i; j--) i = paramArrayOfint[j] = paramArrayOfint[j] + 1;  if (i == 0) { paramArrayOfint = new int[paramArrayOfint.length + 1]; paramArrayOfint[0] = 1; }  return paramArrayOfint; } public BigInteger and(BigInteger paramBigInteger) { int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[b] = getInt(arrayOfInt.length - b - 1) & paramBigInteger.getInt(arrayOfInt.length - b - 1);  return valueOf(arrayOfInt); } public BigInteger or(BigInteger paramBigInteger) { int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[b] = getInt(arrayOfInt.length - b - 1) | paramBigInteger.getInt(arrayOfInt.length - b - 1);  return valueOf(arrayOfInt); } public BigInteger xor(BigInteger paramBigInteger) { int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[b] = getInt(arrayOfInt.length - b - 1) ^ paramBigInteger.getInt(arrayOfInt.length - b - 1);  return valueOf(arrayOfInt); } public BigInteger not() { int[] arrayOfInt = new int[intLength()]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[b] = getInt(arrayOfInt.length - b - 1) ^ 0xFFFFFFFF;  return valueOf(arrayOfInt); } public BigInteger andNot(BigInteger paramBigInteger) { int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[b] = getInt(arrayOfInt.length - b - 1) & (paramBigInteger.getInt(arrayOfInt.length - b - 1) ^ 0xFFFFFFFF);  return valueOf(arrayOfInt); } public boolean testBit(int paramInt) { if (paramInt < 0) throw new ArithmeticException("Negative bit address");  return ((getInt(paramInt >>> 5) & 1 << (paramInt & 0x1F)) != 0); } public BigInteger setBit(int paramInt) { if (paramInt < 0) throw new ArithmeticException("Negative bit address");  int i = paramInt >>> 5; int[] arrayOfInt = new int[Math.max(intLength(), i + 2)]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[arrayOfInt.length - b - 1] = getInt(b);  arrayOfInt[arrayOfInt.length - i - 1] = arrayOfInt[arrayOfInt.length - i - 1] | 1 << (paramInt & 0x1F); return valueOf(arrayOfInt); } public BigInteger clearBit(int paramInt) { if (paramInt < 0) throw new ArithmeticException("Negative bit address");  int i = paramInt >>> 5; int[] arrayOfInt = new int[Math.max(intLength(), (paramInt + 1 >>> 5) + 1)]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[arrayOfInt.length - b - 1] = getInt(b);  arrayOfInt[arrayOfInt.length - i - 1] = arrayOfInt[arrayOfInt.length - i - 1] & (1 << (paramInt & 0x1F) ^ 0xFFFFFFFF); return valueOf(arrayOfInt); } public BigInteger flipBit(int paramInt) { if (paramInt < 0) throw new ArithmeticException("Negative bit address");  int i = paramInt >>> 5; int[] arrayOfInt = new int[Math.max(intLength(), i + 2)]; for (byte b = 0; b < arrayOfInt.length; b++) arrayOfInt[arrayOfInt.length - b - 1] = getInt(b);  arrayOfInt[arrayOfInt.length - i - 1] = arrayOfInt[arrayOfInt.length - i - 1] ^ 1 << (paramInt & 0x1F); return valueOf(arrayOfInt); } public int getLowestSetBit() { int i = this.lowestSetBit - 2; if (i == -2) { i = 0; if (this.signum == 0) { i--; } else { byte b; int j; for (b = 0; (j = getInt(b)) == 0; b++); i += (b << 5) + Integer.numberOfTrailingZeros(j); }  this.lowestSetBit = i + 2; }  return i; } public int bitLength() { int i = this.bitLength - 1; if (i == -1) { int[] arrayOfInt = this.mag; int j = arrayOfInt.length; if (j == 0) { i = 0; } else { int k = (j - 1 << 5) + bitLengthForInt(this.mag[0]); if (this.signum < 0) { boolean bool = (Integer.bitCount(this.mag[0]) == 1) ? true : false; for (byte b = 1; b < j && bool; b++) bool = (this.mag[b] == 0) ? true : false;  i = bool ? (k - 1) : k; } else { i = k; }  }  this.bitLength = i + 1; }  return i; } public int bitCount() { int i = this.bitCount - 1; if (i == -1) { i = 0; int j; for (j = 0; j < this.mag.length; j++) i += Integer.bitCount(this.mag[j]);  if (this.signum < 0) { j = 0; int k; for (k = this.mag.length - 1; this.mag[k] == 0; k--) j += 32;  j += Integer.numberOfTrailingZeros(this.mag[k]); i += j - 1; }  this.bitCount = i + 1; }  return i; } public boolean isProbablePrime(int paramInt) { if (paramInt <= 0) return true;  BigInteger bigInteger = abs(); if (bigInteger.equals(TWO)) return true;  if (!bigInteger.testBit(0) || bigInteger.equals(ONE)) return false;  return bigInteger.primeToCertainty(paramInt, (Random)null); } public int compareTo(BigInteger paramBigInteger) { if (this.signum == paramBigInteger.signum) { switch (this.signum) { case 1: return compareMagnitude(paramBigInteger);case -1: return paramBigInteger.compareMagnitude(this); }  return 0; }  return (this.signum > paramBigInteger.signum) ? 1 : -1; } final int compareMagnitude(BigInteger paramBigInteger) { int[] arrayOfInt1 = this.mag; int i = arrayOfInt1.length; int[] arrayOfInt2 = paramBigInteger.mag; int j = arrayOfInt2.length; if (i < j) return -1;  if (i > j) return 1;  for (byte b = 0; b < i; b++) { int k = arrayOfInt1[b]; int m = arrayOfInt2[b]; if (k != m) return ((k & 0xFFFFFFFFL) < (m & 0xFFFFFFFFL)) ? -1 : 1;  }  return 0; } final int compareMagnitude(long paramLong) { assert paramLong != Long.MIN_VALUE; int[] arrayOfInt = this.mag; int i = arrayOfInt.length; if (i > 2) return 1;  if (paramLong < 0L) paramLong = -paramLong;  int j = (int)(paramLong >>> 32L); if (j == 0) { if (i < 1) return -1;  if (i > 1) return 1;  int n = arrayOfInt[0]; int i1 = (int)paramLong; if (n != i1) return ((n & 0xFFFFFFFFL) < (i1 & 0xFFFFFFFFL)) ? -1 : 1;  return 0; }  if (i < 2) return -1;  int k = arrayOfInt[0]; int m = j; if (k != m) return ((k & 0xFFFFFFFFL) < (m & 0xFFFFFFFFL)) ? -1 : 1;  k = arrayOfInt[1]; m = (int)paramLong; if (k != m) return ((k & 0xFFFFFFFFL) < (m & 0xFFFFFFFFL)) ? -1 : 1;  return 0; } public boolean equals(Object paramObject) { if (paramObject == this) return true;  if (!(paramObject instanceof BigInteger)) return false;  BigInteger bigInteger = (BigInteger)paramObject; if (bigInteger.signum != this.signum) return false;  int[] arrayOfInt1 = this.mag; int i = arrayOfInt1.length; int[] arrayOfInt2 = bigInteger.mag; if (i != arrayOfInt2.length) return false;  for (byte b = 0; b < i; b++) { if (arrayOfInt2[b] != arrayOfInt1[b]) return false;  }  return true; } public BigInteger min(BigInteger paramBigInteger) { return (compareTo(paramBigInteger) < 0) ? this : paramBigInteger; } public BigInteger max(BigInteger paramBigInteger) { return (compareTo(paramBigInteger) > 0) ? this : paramBigInteger; } public int hashCode() { int i = 0; for (byte b = 0; b < this.mag.length; b++) i = (int)((31 * i) + (this.mag[b] & 0xFFFFFFFFL));  return i * this.signum; } public String toString(int paramInt) { if (this.signum == 0) return "0";  if (paramInt < 2 || paramInt > 36) paramInt = 10;  if (this.mag.length <= 20) return smallToString(paramInt);  StringBuilder stringBuilder = new StringBuilder(); if (this.signum < 0) { toString(negate(), stringBuilder, paramInt, 0); stringBuilder.insert(0, '-'); } else { toString(this, stringBuilder, paramInt, 0); }  return stringBuilder.toString(); } private String smallToString(int paramInt) { if (this.signum == 0) return "0";  int i = (4 * this.mag.length + 6) / 7; String[] arrayOfString = new String[i]; BigInteger bigInteger = abs(); byte b = 0; while (bigInteger.signum != 0) { BigInteger bigInteger1 = longRadix[paramInt]; MutableBigInteger mutableBigInteger1 = new MutableBigInteger(); MutableBigInteger mutableBigInteger2 = new MutableBigInteger(bigInteger.mag); MutableBigInteger mutableBigInteger3 = new MutableBigInteger(bigInteger1.mag); MutableBigInteger mutableBigInteger4 = mutableBigInteger2.divide(mutableBigInteger3, mutableBigInteger1); BigInteger bigInteger2 = mutableBigInteger1.toBigInteger(bigInteger.signum * bigInteger1.signum); BigInteger bigInteger3 = mutableBigInteger4.toBigInteger(bigInteger.signum * bigInteger1.signum); arrayOfString[b++] = Long.toString(bigInteger3.longValue(), paramInt); bigInteger = bigInteger2; }  StringBuilder stringBuilder = new StringBuilder(b * digitsPerLong[paramInt] + 1); if (this.signum < 0) stringBuilder.append('-');  stringBuilder.append(arrayOfString[b - 1]); for (int j = b - 2; j >= 0; j--) { int k = digitsPerLong[paramInt] - arrayOfString[j].length(); if (k != 0) stringBuilder.append(zeros[k]);  stringBuilder.append(arrayOfString[j]); }  return stringBuilder.toString(); } private static void toString(BigInteger paramBigInteger, StringBuilder paramStringBuilder, int paramInt1, int paramInt2) { if (paramBigInteger.mag.length <= 20) { String str = paramBigInteger.smallToString(paramInt1); if (str.length() < paramInt2 && paramStringBuilder.length() > 0) for (int m = str.length(); m < paramInt2; m++) paramStringBuilder.append('0');   paramStringBuilder.append(str); return; }  int i = paramBigInteger.bitLength(); int j = (int)Math.round(Math.log(i * LOG_TWO / logCache[paramInt1]) / LOG_TWO - 1.0D); BigInteger bigInteger = getRadixConversionCache(paramInt1, j); BigInteger[] arrayOfBigInteger = paramBigInteger.divideAndRemainder(bigInteger); int k = 1 << j; toString(arrayOfBigInteger[0], paramStringBuilder, paramInt1, paramInt2 - k); toString(arrayOfBigInteger[1], paramStringBuilder, paramInt1, k); } private static BigInteger getRadixConversionCache(int paramInt1, int paramInt2) { BigInteger[] arrayOfBigInteger = powerCache[paramInt1]; if (paramInt2 < arrayOfBigInteger.length) return arrayOfBigInteger[paramInt2];  int i = arrayOfBigInteger.length; arrayOfBigInteger = Arrays.<BigInteger>copyOf(arrayOfBigInteger, paramInt2 + 1); for (int j = i; j <= paramInt2; j++) arrayOfBigInteger[j] = arrayOfBigInteger[j - 1].pow(2);  BigInteger[][] arrayOfBigInteger1 = powerCache; if (paramInt2 >= (arrayOfBigInteger1[paramInt1]).length) { arrayOfBigInteger1 = (BigInteger[][])arrayOfBigInteger1.clone(); arrayOfBigInteger1[paramInt1] = arrayOfBigInteger; powerCache = arrayOfBigInteger1; }  return arrayOfBigInteger[paramInt2]; } public String toString() { return toString(10); } public byte[] toByteArray() { int i = bitLength() / 8 + 1; byte[] arrayOfByte = new byte[i]; int j; byte b1; int k; byte b2; for (j = i - 1, b1 = 4, k = 0, b2 = 0; j >= 0; j--) { if (b1 == 4) { k = getInt(b2++); b1 = 1; } else { k >>>= 8; b1++; }  arrayOfByte[j] = (byte)k; }  return arrayOfByte; } public int intValue() { int i = 0; i = getInt(0); return i; } public long longValue() { long l = 0L; for (byte b = 1; b; b--) l = (l << 32L) + (getInt(b) & 0xFFFFFFFFL);  return l; } public float floatValue() { int k; if (this.signum == 0) return 0.0F;  int i = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1; if (i < 63) return (float)longValue();  if (i > 127) return (this.signum > 0) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;  int j = i - 24; int m = j & 0x1F; int n = 32 - m; if (m == 0) { k = this.mag[0]; } else { k = this.mag[0] >>> m; if (k == 0) k = this.mag[0] << n | this.mag[1] >>> m;  }  int i1 = k >> 1; i1 &= 0x7FFFFF; boolean bool = ((k & 0x1) != 0 && ((i1 & 0x1) != 0 || abs().getLowestSetBit() < j)) ? true : false; int i2 = bool ? (i1 + 1) : i1; int i3 = i + 127 << 23; i3 += i2; i3 |= this.signum & Integer.MIN_VALUE; return Float.intBitsToFloat(i3); } public double doubleValue() { int n, i1; if (this.signum == 0) return 0.0D;  int i = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1; if (i < 63) return longValue();  if (i > 1023) return (this.signum > 0) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;  int j = i - 53; int k = j & 0x1F; int m = 32 - k; if (k == 0) { n = this.mag[0]; i1 = this.mag[1]; } else { n = this.mag[0] >>> k; i1 = this.mag[0] << m | this.mag[1] >>> k; if (n == 0) { n = i1; i1 = this.mag[1] << m | this.mag[2] >>> k; }  }  long l1 = (n & 0xFFFFFFFFL) << 32L | i1 & 0xFFFFFFFFL; long l2 = l1 >> 1L; l2 &= 0xFFFFFFFFFFFFFL; boolean bool = ((l1 & 0x1L) != 0L && ((l2 & 0x1L) != 0L || abs().getLowestSetBit() < j)) ? true : false; long l3 = bool ? (l2 + 1L) : l2; long l4 = (i + 1023) << 52L; l4 += l3; l4 |= this.signum & Long.MIN_VALUE; return Double.longBitsToDouble(l4); } private static int[] stripLeadingZeroInts(int[] paramArrayOfint) { int i = paramArrayOfint.length; byte b; for (b = 0; b < i && paramArrayOfint[b] == 0; b++); return Arrays.copyOfRange(paramArrayOfint, b, i); } private static int[] trustedStripLeadingZeroInts(int[] paramArrayOfint) { int i = paramArrayOfint.length; byte b; for (b = 0; b < i && paramArrayOfint[b] == 0; b++); return (b == 0) ? paramArrayOfint : Arrays.copyOfRange(paramArrayOfint, b, i); } private static int[] stripLeadingZeroBytes(byte[] paramArrayOfbyte) { int i = paramArrayOfbyte.length; byte b; for (b = 0; b < i && paramArrayOfbyte[b] == 0; b++); int j = i - b + 3 >>> 2; int[] arrayOfInt = new int[j]; int k = i - 1; for (int m = j - 1; m >= 0; m--) { arrayOfInt[m] = paramArrayOfbyte[k--] & 0xFF; int n = k - b + 1; int i1 = Math.min(3, n); for (byte b1 = 8; b1 <= i1 << 3; b1 += 8) arrayOfInt[m] = arrayOfInt[m] | (paramArrayOfbyte[k--] & 0xFF) << b1;  }  return arrayOfInt; } private static int[] makePositive(byte[] paramArrayOfbyte) { int i = paramArrayOfbyte.length; byte b1; for (b1 = 0; b1 < i && paramArrayOfbyte[b1] == -1; b1++); byte b2; for (b2 = b1; b2 < i && paramArrayOfbyte[b2] == 0; b2++); byte b3 = (b2 == i) ? 1 : 0; int j = i - b1 + b3 + 3 >>> 2; int[] arrayOfInt = new int[j]; int k = i - 1; int m; for (m = j - 1; m >= 0; m--) { arrayOfInt[m] = paramArrayOfbyte[k--] & 0xFF; int n = Math.min(3, k - b1 + 1); if (n < 0) n = 0;  int i1; for (i1 = 8; i1 <= 8 * n; i1 += 8) arrayOfInt[m] = arrayOfInt[m] | (paramArrayOfbyte[k--] & 0xFF) << i1;  i1 = -1 >>> 8 * (3 - n); arrayOfInt[m] = (arrayOfInt[m] ^ 0xFFFFFFFF) & i1; }  for (m = arrayOfInt.length - 1; m >= 0; m--) { arrayOfInt[m] = (int)((arrayOfInt[m] & 0xFFFFFFFFL) + 1L); if (arrayOfInt[m] != 0) break;  }  return arrayOfInt; } private static int[] makePositive(int[] paramArrayOfint) { byte b1; for (b1 = 0; b1 < paramArrayOfint.length && paramArrayOfint[b1] == -1; b1++); byte b2; for (b2 = b1; b2 < paramArrayOfint.length && paramArrayOfint[b2] == 0; b2++); byte b3 = (b2 == paramArrayOfint.length) ? 1 : 0; int[] arrayOfInt = new int[paramArrayOfint.length - b1 + b3]; int i; for (i = b1; i < paramArrayOfint.length; i++) arrayOfInt[i - b1 + b3] = paramArrayOfint[i] ^ 0xFFFFFFFF;  for (i = arrayOfInt.length - 1, arrayOfInt[i] = arrayOfInt[i] + 1; arrayOfInt[i] + 1 == 0; i--); return arrayOfInt; } private int intLength() { return (bitLength() >>> 5) + 1; } private int signBit() { return (this.signum < 0) ? 1 : 0; } private int signInt() { return (this.signum < 0) ? -1 : 0; } private int getInt(int paramInt) { if (paramInt < 0) return 0;  if (paramInt >= this.mag.length) return signInt();  int i = this.mag[this.mag.length - paramInt - 1]; return (this.signum >= 0) ? i : ((paramInt <= firstNonzeroIntNum()) ? -i : (i ^ 0xFFFFFFFF)); } private int firstNonzeroIntNum() { int i = this.firstNonzeroIntNum - 2; if (i == -2) { i = 0; int k = this.mag.length; int j; for (j = k - 1; j >= 0 && this.mag[j] == 0; j--); i = k - j - 1; this.firstNonzeroIntNum = i + 2; }  return i; } private static class UnsafeHolder
/*      */   {
/* 4549 */     private static final Unsafe unsafe; private static final long signumOffset; private static final long magOffset; static { try { unsafe = Unsafe.getUnsafe();
/*      */         
/* 4551 */         signumOffset = unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("signum"));
/*      */         
/* 4553 */         magOffset = unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("mag")); }
/* 4554 */       catch (Exception exception)
/* 4555 */       { throw new ExceptionInInitializerError(exception); }
/*      */        }
/*      */ 
/*      */     
/*      */     static void putSign(BigInteger param1BigInteger, int param1Int) {
/* 4560 */       unsafe.putIntVolatile(param1BigInteger, signumOffset, param1Int);
/*      */     }
/*      */     
/*      */     static void putMag(BigInteger param1BigInteger, int[] param1ArrayOfint) {
/* 4564 */       unsafe.putObjectVolatile(param1BigInteger, magOffset, param1ArrayOfint);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 4578 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 4579 */     putField.put("signum", this.signum);
/* 4580 */     putField.put("magnitude", magSerializedForm());
/*      */ 
/*      */     
/* 4583 */     putField.put("bitCount", -1);
/* 4584 */     putField.put("bitLength", -1);
/* 4585 */     putField.put("lowestSetBit", -2);
/* 4586 */     putField.put("firstNonzeroByteNum", -2);
/*      */ 
/*      */     
/* 4589 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] magSerializedForm() {
/* 4596 */     int i = this.mag.length;
/*      */     
/* 4598 */     byte b1 = (i == 0) ? 0 : ((i - 1 << 5) + bitLengthForInt(this.mag[0]));
/* 4599 */     int j = b1 + 7 >>> 3;
/* 4600 */     byte[] arrayOfByte = new byte[j];
/*      */     
/* 4602 */     int k = j - 1; byte b2 = 4; int m = i - 1, n = 0;
/* 4603 */     for (; k >= 0; k--) {
/* 4604 */       if (b2 == 4) {
/* 4605 */         n = this.mag[m--];
/* 4606 */         b2 = 1;
/*      */       } else {
/* 4608 */         n >>>= 8;
/* 4609 */         b2++;
/*      */       } 
/* 4611 */       arrayOfByte[k] = (byte)n;
/*      */     } 
/* 4613 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4629 */     if (this.mag.length <= 2 && bitLength() <= 63) {
/* 4630 */       return longValue();
/*      */     }
/* 4632 */     throw new ArithmeticException("BigInteger out of long range");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4648 */     if (this.mag.length <= 1 && bitLength() <= 31) {
/* 4649 */       return intValue();
/*      */     }
/* 4651 */     throw new ArithmeticException("BigInteger out of int range");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4667 */     if (this.mag.length <= 1 && bitLength() <= 31) {
/* 4668 */       int i = intValue();
/* 4669 */       if (i >= -32768 && i <= 32767)
/* 4670 */         return shortValue(); 
/*      */     } 
/* 4672 */     throw new ArithmeticException("BigInteger out of short range");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4688 */     if (this.mag.length <= 1 && bitLength() <= 31) {
/* 4689 */       int i = intValue();
/* 4690 */       if (i >= -128 && i <= 127)
/* 4691 */         return byteValue(); 
/*      */     } 
/* 4693 */     throw new ArithmeticException("BigInteger out of byte range");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/math/BigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */