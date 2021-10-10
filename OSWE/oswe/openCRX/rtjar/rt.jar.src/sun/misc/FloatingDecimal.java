/*      */ package sun.misc;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FloatingDecimal
/*      */ {
/*      */   static final int EXP_SHIFT = 52;
/*      */   static final long FRACT_HOB = 4503599627370496L;
/*      */   static final long EXP_ONE = 4607182418800017408L;
/*      */   static final int MAX_SMALL_BIN_EXP = 62;
/*      */   static final int MIN_SMALL_BIN_EXP = -21;
/*      */   static final int MAX_DECIMAL_DIGITS = 15;
/*      */   static final int MAX_DECIMAL_EXPONENT = 308;
/*      */   static final int MIN_DECIMAL_EXPONENT = -324;
/*      */   static final int BIG_DECIMAL_EXPONENT = 324;
/*      */   static final int MAX_NDIGITS = 1100;
/*      */   static final int SINGLE_EXP_SHIFT = 23;
/*      */   static final int SINGLE_FRACT_HOB = 8388608;
/*      */   static final int SINGLE_MAX_DECIMAL_DIGITS = 7;
/*      */   static final int SINGLE_MAX_DECIMAL_EXPONENT = 38;
/*      */   static final int SINGLE_MIN_DECIMAL_EXPONENT = -45;
/*      */   static final int SINGLE_MAX_NDIGITS = 200;
/*      */   static final int INT_DECIMAL_DIGITS = 9;
/*      */   private static final String INFINITY_REP = "Infinity";
/*      */   
/*      */   public static String toJavaFormatString(double paramDouble) {
/*   70 */     return getBinaryToASCIIConverter(paramDouble).toJavaFormatString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toJavaFormatString(float paramFloat) {
/*   80 */     return getBinaryToASCIIConverter(paramFloat).toJavaFormatString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void appendTo(double paramDouble, Appendable paramAppendable) {
/*   89 */     getBinaryToASCIIConverter(paramDouble).appendTo(paramAppendable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void appendTo(float paramFloat, Appendable paramAppendable) {
/*   98 */     getBinaryToASCIIConverter(paramFloat).appendTo(paramAppendable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double parseDouble(String paramString) throws NumberFormatException {
/*  110 */     return readJavaFormatString(paramString).doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float parseFloat(String paramString) throws NumberFormatException {
/*  122 */     return readJavaFormatString(paramString).floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ExceptionalBinaryToASCIIBuffer
/*      */     implements BinaryToASCIIConverter
/*      */   {
/*      */     private final String image;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isNegative;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ExceptionalBinaryToASCIIBuffer(String param1String, boolean param1Boolean) {
/*  194 */       this.image = param1String;
/*  195 */       this.isNegative = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toJavaFormatString() {
/*  200 */       return this.image;
/*      */     }
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable param1Appendable) {
/*  205 */       if (param1Appendable instanceof StringBuilder) {
/*  206 */         ((StringBuilder)param1Appendable).append(this.image);
/*  207 */       } else if (param1Appendable instanceof StringBuffer) {
/*  208 */         ((StringBuffer)param1Appendable).append(this.image);
/*      */       } else {
/*      */         assert false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int getDecimalExponent() {
/*  216 */       throw new IllegalArgumentException("Exceptional value does not have an exponent");
/*      */     }
/*      */ 
/*      */     
/*      */     public int getDigits(char[] param1ArrayOfchar) {
/*  221 */       throw new IllegalArgumentException("Exceptional value does not have digits");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isNegative() {
/*  226 */       return this.isNegative;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isExceptional() {
/*  231 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean digitsRoundedUp() {
/*  236 */       throw new IllegalArgumentException("Exceptional value is not rounded");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean decimalDigitsExact() {
/*  241 */       throw new IllegalArgumentException("Exceptional value is not exact");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  246 */   private static final int INFINITY_LENGTH = "Infinity".length();
/*      */   private static final String NAN_REP = "NaN";
/*  248 */   private static final int NAN_LENGTH = "NaN".length();
/*      */   
/*  250 */   private static final BinaryToASCIIConverter B2AC_POSITIVE_INFINITY = new ExceptionalBinaryToASCIIBuffer("Infinity", false);
/*  251 */   private static final BinaryToASCIIConverter B2AC_NEGATIVE_INFINITY = new ExceptionalBinaryToASCIIBuffer("-Infinity", true);
/*  252 */   private static final BinaryToASCIIConverter B2AC_NOT_A_NUMBER = new ExceptionalBinaryToASCIIBuffer("NaN", false);
/*  253 */   private static final BinaryToASCIIConverter B2AC_POSITIVE_ZERO = new BinaryToASCIIBuffer(false, new char[] { '0' });
/*  254 */   private static final BinaryToASCIIConverter B2AC_NEGATIVE_ZERO = new BinaryToASCIIBuffer(true, new char[] { '0' });
/*      */ 
/*      */   
/*      */   static class BinaryToASCIIBuffer
/*      */     implements BinaryToASCIIConverter
/*      */   {
/*      */     private boolean isNegative;
/*      */     private int decExponent;
/*      */     private int firstDigitIndex;
/*      */     private int nDigits;
/*      */     private final char[] digits;
/*  265 */     private final char[] buffer = new char[26];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean exactDecimalConversion = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean decimalDigitsRoundedUp = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BinaryToASCIIBuffer() {
/*  285 */       this.digits = new char[20];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BinaryToASCIIBuffer(boolean param1Boolean, char[] param1ArrayOfchar) {
/*  292 */       this.isNegative = param1Boolean;
/*  293 */       this.decExponent = 0;
/*  294 */       this.digits = param1ArrayOfchar;
/*  295 */       this.firstDigitIndex = 0;
/*  296 */       this.nDigits = param1ArrayOfchar.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toJavaFormatString() {
/*  301 */       int i = getChars(this.buffer);
/*  302 */       return new String(this.buffer, 0, i);
/*      */     }
/*      */ 
/*      */     
/*      */     public void appendTo(Appendable param1Appendable) {
/*  307 */       int i = getChars(this.buffer);
/*  308 */       if (param1Appendable instanceof StringBuilder) {
/*  309 */         ((StringBuilder)param1Appendable).append(this.buffer, 0, i);
/*  310 */       } else if (param1Appendable instanceof StringBuffer) {
/*  311 */         ((StringBuffer)param1Appendable).append(this.buffer, 0, i);
/*      */       } else {
/*      */         assert false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int getDecimalExponent() {
/*  319 */       return this.decExponent;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getDigits(char[] param1ArrayOfchar) {
/*  324 */       System.arraycopy(this.digits, this.firstDigitIndex, param1ArrayOfchar, 0, this.nDigits);
/*  325 */       return this.nDigits;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isNegative() {
/*  330 */       return this.isNegative;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isExceptional() {
/*  335 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean digitsRoundedUp() {
/*  340 */       return this.decimalDigitsRoundedUp;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean decimalDigitsExact() {
/*  345 */       return this.exactDecimalConversion;
/*      */     }
/*      */     
/*      */     private void setSign(boolean param1Boolean) {
/*  349 */       this.isNegative = param1Boolean;
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
/*      */     private void developLongDigits(int param1Int1, long param1Long, int param1Int2) {
/*  368 */       if (param1Int2 != 0) {
/*      */ 
/*      */         
/*  371 */         long l1 = FDBigInteger.LONG_5_POW[param1Int2] << param1Int2;
/*  372 */         long l2 = param1Long % l1;
/*  373 */         param1Long /= l1;
/*  374 */         param1Int1 += param1Int2;
/*  375 */         if (l2 >= l1 >> 1L)
/*      */         {
/*  377 */           param1Long++;
/*      */         }
/*      */       } 
/*  380 */       int i = this.digits.length - 1;
/*      */       
/*  382 */       if (param1Long <= 2147483647L) {
/*  383 */         assert param1Long > 0L : param1Long;
/*      */ 
/*      */         
/*  386 */         int k = (int)param1Long;
/*  387 */         int j = k % 10;
/*  388 */         k /= 10;
/*  389 */         while (j == 0) {
/*  390 */           param1Int1++;
/*  391 */           j = k % 10;
/*  392 */           k /= 10;
/*      */         } 
/*  394 */         while (k != 0) {
/*  395 */           this.digits[i--] = (char)(j + 48);
/*  396 */           param1Int1++;
/*  397 */           j = k % 10;
/*  398 */           k /= 10;
/*      */         } 
/*  400 */         this.digits[i] = (char)(j + 48);
/*      */       }
/*      */       else {
/*      */         
/*  404 */         int j = (int)(param1Long % 10L);
/*  405 */         param1Long /= 10L;
/*  406 */         while (j == 0) {
/*  407 */           param1Int1++;
/*  408 */           j = (int)(param1Long % 10L);
/*  409 */           param1Long /= 10L;
/*      */         } 
/*  411 */         while (param1Long != 0L) {
/*  412 */           this.digits[i--] = (char)(j + 48);
/*  413 */           param1Int1++;
/*  414 */           j = (int)(param1Long % 10L);
/*  415 */           param1Long /= 10L;
/*      */         } 
/*  417 */         this.digits[i] = (char)(j + 48);
/*      */       } 
/*  419 */       this.decExponent = param1Int1 + 1;
/*  420 */       this.firstDigitIndex = i;
/*  421 */       this.nDigits = this.digits.length - i;
/*      */     }
/*      */     private void dtoa(int param1Int1, long param1Long, int param1Int2, boolean param1Boolean) {
/*      */       boolean bool1, bool2;
/*      */       long l;
/*  426 */       assert param1Long > 0L;
/*  427 */       assert (param1Long & 0x10000000000000L) != 0L;
/*      */ 
/*      */ 
/*      */       
/*  431 */       int i = Long.numberOfTrailingZeros(param1Long);
/*      */ 
/*      */       
/*  434 */       int j = 53 - i;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  439 */       this.decimalDigitsRoundedUp = false;
/*  440 */       this.exactDecimalConversion = false;
/*      */ 
/*      */       
/*  443 */       int k = Math.max(0, j - param1Int1 - 1);
/*  444 */       if (param1Int1 <= 62 && param1Int1 >= -21)
/*      */       {
/*      */ 
/*      */         
/*  448 */         if (k < FDBigInteger.LONG_5_POW.length && j + N_5_BITS[k] < 64)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  464 */           if (k == 0) {
/*      */             boolean bool;
/*  466 */             if (param1Int1 > param1Int2) {
/*  467 */               bool = insignificantDigitsForPow2(param1Int1 - param1Int2 - 1);
/*      */             } else {
/*  469 */               bool = false;
/*      */             } 
/*  471 */             if (param1Int1 >= 52) {
/*  472 */               param1Long <<= param1Int1 - 52;
/*      */             } else {
/*  474 */               param1Long >>>= 52 - param1Int1;
/*      */             } 
/*  476 */             developLongDigits(0, param1Long, bool);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  515 */       int m = estimateDecExp(param1Long, param1Int1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  520 */       int i1 = Math.max(0, -m);
/*  521 */       int n = i1 + k + param1Int1;
/*      */       
/*  523 */       int i3 = Math.max(0, m);
/*  524 */       int i2 = i3 + k;
/*      */       
/*  526 */       int i5 = i1;
/*  527 */       int i4 = n - param1Int2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  537 */       param1Long >>>= i;
/*  538 */       n -= j - 1;
/*  539 */       int i6 = Math.min(n, i2);
/*  540 */       n -= i6;
/*  541 */       i2 -= i6;
/*  542 */       i4 -= i6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  550 */       if (j == 1) {
/*  551 */         i4--;
/*      */       }
/*      */       
/*  554 */       if (i4 < 0) {
/*      */ 
/*      */ 
/*      */         
/*  558 */         n -= i4;
/*  559 */         i2 -= i4;
/*  560 */         i4 = 0;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  570 */       byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  591 */       int i7 = j + n + ((i1 < N_5_BITS.length) ? N_5_BITS[i1] : (i1 * 3));
/*      */ 
/*      */       
/*  594 */       int i8 = i2 + 1 + ((i3 + 1 < N_5_BITS.length) ? N_5_BITS[i3 + 1] : ((i3 + 1) * 3));
/*  595 */       if (i7 < 64 && i8 < 64) {
/*  596 */         if (i7 < 32 && i8 < 32) {
/*      */           
/*  598 */           int i10 = (int)param1Long * FDBigInteger.SMALL_5_POW[i1] << n;
/*  599 */           int i11 = FDBigInteger.SMALL_5_POW[i3] << i2;
/*  600 */           int i12 = FDBigInteger.SMALL_5_POW[i5] << i4;
/*  601 */           int i13 = i11 * 10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  607 */           b = 0;
/*  608 */           int i9 = i10 / i11;
/*  609 */           i10 = 10 * i10 % i11;
/*  610 */           i12 *= 10;
/*  611 */           bool1 = (i10 < i12) ? true : false;
/*  612 */           bool2 = (i10 + i12 > i13) ? true : false;
/*  613 */           assert i9 < 10 : i9;
/*  614 */           if (i9 == 0 && !bool2) {
/*      */             
/*  616 */             m--;
/*      */           } else {
/*  618 */             this.digits[b++] = (char)(48 + i9);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  626 */           if (!param1Boolean || m < -3 || m >= 8) {
/*  627 */             bool2 = bool1 = false;
/*      */           }
/*  629 */           while (!bool1 && !bool2) {
/*  630 */             i9 = i10 / i11;
/*  631 */             i10 = 10 * i10 % i11;
/*  632 */             i12 *= 10;
/*  633 */             assert i9 < 10 : i9;
/*  634 */             if (i12 > 0L) {
/*  635 */               bool1 = (i10 < i12) ? true : false;
/*  636 */               bool2 = (i10 + i12 > i13) ? true : false;
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  643 */               bool1 = true;
/*  644 */               bool2 = true;
/*      */             } 
/*  646 */             this.digits[b++] = (char)(48 + i9);
/*      */           } 
/*  648 */           l = ((i10 << 1) - i13);
/*  649 */           this.exactDecimalConversion = (i10 == 0);
/*      */         } else {
/*      */           
/*  652 */           long l1 = param1Long * FDBigInteger.LONG_5_POW[i1] << n;
/*  653 */           long l2 = FDBigInteger.LONG_5_POW[i3] << i2;
/*  654 */           long l3 = FDBigInteger.LONG_5_POW[i5] << i4;
/*  655 */           long l4 = l2 * 10L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  661 */           b = 0;
/*  662 */           int i9 = (int)(l1 / l2);
/*  663 */           l1 = 10L * l1 % l2;
/*  664 */           l3 *= 10L;
/*  665 */           bool1 = (l1 < l3) ? true : false;
/*  666 */           bool2 = (l1 + l3 > l4) ? true : false;
/*  667 */           assert i9 < 10 : i9;
/*  668 */           if (i9 == 0 && !bool2) {
/*      */             
/*  670 */             m--;
/*      */           } else {
/*  672 */             this.digits[b++] = (char)(48 + i9);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  680 */           if (!param1Boolean || m < -3 || m >= 8) {
/*  681 */             bool2 = bool1 = false;
/*      */           }
/*  683 */           while (!bool1 && !bool2) {
/*  684 */             i9 = (int)(l1 / l2);
/*  685 */             l1 = 10L * l1 % l2;
/*  686 */             l3 *= 10L;
/*  687 */             assert i9 < 10 : i9;
/*  688 */             if (l3 > 0L) {
/*  689 */               bool1 = (l1 < l3) ? true : false;
/*  690 */               bool2 = (l1 + l3 > l4) ? true : false;
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  697 */               bool1 = true;
/*  698 */               bool2 = true;
/*      */             } 
/*  700 */             this.digits[b++] = (char)(48 + i9);
/*      */           } 
/*  702 */           l = (l1 << 1L) - l4;
/*  703 */           this.exactDecimalConversion = (l1 == 0L);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  710 */         FDBigInteger fDBigInteger1 = FDBigInteger.valueOfPow52(i3, i2);
/*  711 */         int i10 = fDBigInteger1.getNormalizationBias();
/*  712 */         fDBigInteger1 = fDBigInteger1.leftShift(i10);
/*      */         
/*  714 */         FDBigInteger fDBigInteger2 = FDBigInteger.valueOfMulPow52(param1Long, i1, n + i10);
/*  715 */         FDBigInteger fDBigInteger3 = FDBigInteger.valueOfPow52(i5 + 1, i4 + i10 + 1);
/*      */         
/*  717 */         FDBigInteger fDBigInteger4 = FDBigInteger.valueOfPow52(i3 + 1, i2 + i10 + 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  723 */         b = 0;
/*  724 */         int i9 = fDBigInteger2.quoRemIteration(fDBigInteger1);
/*  725 */         bool1 = (fDBigInteger2.cmp(fDBigInteger3) < 0) ? true : false;
/*  726 */         bool2 = (fDBigInteger4.addAndCmp(fDBigInteger2, fDBigInteger3) <= 0) ? true : false;
/*      */         
/*  728 */         assert i9 < 10 : i9;
/*  729 */         if (i9 == 0 && !bool2) {
/*      */           
/*  731 */           m--;
/*      */         } else {
/*  733 */           this.digits[b++] = (char)(48 + i9);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  741 */         if (!param1Boolean || m < -3 || m >= 8) {
/*  742 */           bool2 = bool1 = false;
/*      */         }
/*  744 */         while (!bool1 && !bool2) {
/*  745 */           i9 = fDBigInteger2.quoRemIteration(fDBigInteger1);
/*  746 */           assert i9 < 10 : i9;
/*  747 */           fDBigInteger3 = fDBigInteger3.multBy10();
/*  748 */           bool1 = (fDBigInteger2.cmp(fDBigInteger3) < 0) ? true : false;
/*  749 */           bool2 = (fDBigInteger4.addAndCmp(fDBigInteger2, fDBigInteger3) <= 0) ? true : false;
/*  750 */           this.digits[b++] = (char)(48 + i9);
/*      */         } 
/*  752 */         if (bool2 && bool1) {
/*  753 */           fDBigInteger2 = fDBigInteger2.leftShift(1);
/*  754 */           l = fDBigInteger2.cmp(fDBigInteger4);
/*      */         } else {
/*  756 */           l = 0L;
/*      */         } 
/*  758 */         this.exactDecimalConversion = (fDBigInteger2.cmp(FDBigInteger.ZERO) == 0);
/*      */       } 
/*  760 */       this.decExponent = m + 1;
/*  761 */       this.firstDigitIndex = 0;
/*  762 */       this.nDigits = b;
/*      */ 
/*      */ 
/*      */       
/*  766 */       if (bool2) {
/*  767 */         if (bool1) {
/*  768 */           if (l == 0L) {
/*      */ 
/*      */             
/*  771 */             if ((this.digits[this.firstDigitIndex + this.nDigits - 1] & 0x1) != 0) {
/*  772 */               roundup();
/*      */             }
/*  774 */           } else if (l > 0L) {
/*  775 */             roundup();
/*      */           } 
/*      */         } else {
/*  778 */           roundup();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void roundup() {
/*  789 */       int i = this.firstDigitIndex + this.nDigits - 1;
/*  790 */       char c = this.digits[i];
/*  791 */       if (c == '9') {
/*  792 */         while (c == '9' && i > this.firstDigitIndex) {
/*  793 */           this.digits[i] = '0';
/*  794 */           c = this.digits[--i];
/*      */         } 
/*  796 */         if (c == '9') {
/*      */           
/*  798 */           this.decExponent++;
/*  799 */           this.digits[this.firstDigitIndex] = '1';
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  804 */       this.digits[i] = (char)(c + 1);
/*  805 */       this.decimalDigitsRoundedUp = true;
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
/*      */     static int estimateDecExp(long param1Long, int param1Int) {
/*  820 */       double d1 = Double.longBitsToDouble(0x3FF0000000000000L | param1Long & 0xFFFFFFFFFFFFFL);
/*  821 */       double d2 = (d1 - 1.5D) * 0.289529654D + 0.176091259D + param1Int * 0.301029995663981D;
/*  822 */       long l = Double.doubleToRawLongBits(d2);
/*  823 */       int i = (int)((l & 0x7FF0000000000000L) >> 52L) - 1023;
/*  824 */       boolean bool = ((l & Long.MIN_VALUE) != 0L) ? true : false;
/*  825 */       if (i >= 0 && i < 52) {
/*  826 */         long l1 = 4503599627370495L >> i;
/*  827 */         int j = (int)((l & 0xFFFFFFFFFFFFFL | 0x10000000000000L) >> 52 - i);
/*  828 */         return bool ? (((l1 & l) == 0L) ? -j : (-j - 1)) : j;
/*  829 */       }  if (i < 0) {
/*  830 */         return ((l & Long.MAX_VALUE) == 0L) ? 0 : (bool ? -1 : 0);
/*      */       }
/*      */       
/*  833 */       return (int)d2;
/*      */     }
/*      */ 
/*      */     
/*      */     private static int insignificantDigits(int param1Int) {
/*      */       byte b;
/*  839 */       for (b = 0; param1Int >= 10L; b++) {
/*  840 */         param1Int = (int)(param1Int / 10L);
/*      */       }
/*  842 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int insignificantDigitsForPow2(int param1Int) {
/*  852 */       if (param1Int > 1 && param1Int < insignificantDigitsNumber.length) {
/*  853 */         return insignificantDigitsNumber[param1Int];
/*      */       }
/*  855 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  865 */     private static int[] insignificantDigitsNumber = new int[] { 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     private static final int[] N_5_BITS = new int[] { 0, 3, 5, 7, 10, 12, 14, 17, 19, 21, 24, 26, 28, 31, 33, 35, 38, 40, 42, 45, 47, 49, 52, 54, 56, 59, 61 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getChars(char[] param1ArrayOfchar) {
/*  906 */       assert this.nDigits <= 19 : this.nDigits;
/*  907 */       int i = 0;
/*  908 */       if (this.isNegative) {
/*  909 */         param1ArrayOfchar[0] = '-';
/*  910 */         i = 1;
/*      */       } 
/*  912 */       if (this.decExponent > 0 && this.decExponent < 8) {
/*      */         
/*  914 */         int j = Math.min(this.nDigits, this.decExponent);
/*  915 */         System.arraycopy(this.digits, this.firstDigitIndex, param1ArrayOfchar, i, j);
/*  916 */         i += j;
/*  917 */         if (j < this.decExponent) {
/*  918 */           j = this.decExponent - j;
/*  919 */           Arrays.fill(param1ArrayOfchar, i, i + j, '0');
/*  920 */           i += j;
/*  921 */           param1ArrayOfchar[i++] = '.';
/*  922 */           param1ArrayOfchar[i++] = '0';
/*      */         } else {
/*  924 */           param1ArrayOfchar[i++] = '.';
/*  925 */           if (j < this.nDigits) {
/*  926 */             int k = this.nDigits - j;
/*  927 */             System.arraycopy(this.digits, this.firstDigitIndex + j, param1ArrayOfchar, i, k);
/*  928 */             i += k;
/*      */           } else {
/*  930 */             param1ArrayOfchar[i++] = '0';
/*      */           } 
/*      */         } 
/*  933 */       } else if (this.decExponent <= 0 && this.decExponent > -3) {
/*  934 */         param1ArrayOfchar[i++] = '0';
/*  935 */         param1ArrayOfchar[i++] = '.';
/*  936 */         if (this.decExponent != 0) {
/*  937 */           Arrays.fill(param1ArrayOfchar, i, i - this.decExponent, '0');
/*  938 */           i -= this.decExponent;
/*      */         } 
/*  940 */         System.arraycopy(this.digits, this.firstDigitIndex, param1ArrayOfchar, i, this.nDigits);
/*  941 */         i += this.nDigits;
/*      */       } else {
/*  943 */         int j; param1ArrayOfchar[i++] = this.digits[this.firstDigitIndex];
/*  944 */         param1ArrayOfchar[i++] = '.';
/*  945 */         if (this.nDigits > 1) {
/*  946 */           System.arraycopy(this.digits, this.firstDigitIndex + 1, param1ArrayOfchar, i, this.nDigits - 1);
/*  947 */           i += this.nDigits - 1;
/*      */         } else {
/*  949 */           param1ArrayOfchar[i++] = '0';
/*      */         } 
/*  951 */         param1ArrayOfchar[i++] = 'E';
/*      */         
/*  953 */         if (this.decExponent <= 0) {
/*  954 */           param1ArrayOfchar[i++] = '-';
/*  955 */           j = -this.decExponent + 1;
/*      */         } else {
/*  957 */           j = this.decExponent - 1;
/*      */         } 
/*      */         
/*  960 */         if (j <= 9) {
/*  961 */           param1ArrayOfchar[i++] = (char)(j + 48);
/*  962 */         } else if (j <= 99) {
/*  963 */           param1ArrayOfchar[i++] = (char)(j / 10 + 48);
/*  964 */           param1ArrayOfchar[i++] = (char)(j % 10 + 48);
/*      */         } else {
/*  966 */           param1ArrayOfchar[i++] = (char)(j / 100 + 48);
/*  967 */           j %= 100;
/*  968 */           param1ArrayOfchar[i++] = (char)(j / 10 + 48);
/*  969 */           param1ArrayOfchar[i++] = (char)(j % 10 + 48);
/*      */         } 
/*      */       } 
/*  972 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  977 */   private static final ThreadLocal<BinaryToASCIIBuffer> threadLocalBinaryToASCIIBuffer = new ThreadLocal<BinaryToASCIIBuffer>()
/*      */     {
/*      */       protected FloatingDecimal.BinaryToASCIIBuffer initialValue()
/*      */       {
/*  981 */         return new FloatingDecimal.BinaryToASCIIBuffer();
/*      */       }
/*      */     };
/*      */   
/*      */   private static BinaryToASCIIBuffer getBinaryToASCIIBuffer() {
/*  986 */     return threadLocalBinaryToASCIIBuffer.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class PreparedASCIIToBinaryBuffer
/*      */     implements ASCIIToBinaryConverter
/*      */   {
/*      */     private final double doubleVal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final float floatVal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PreparedASCIIToBinaryBuffer(double param1Double, float param1Float) {
/* 1010 */       this.doubleVal = param1Double;
/* 1011 */       this.floatVal = param1Float;
/*      */     }
/*      */ 
/*      */     
/*      */     public double doubleValue() {
/* 1016 */       return this.doubleVal;
/*      */     }
/*      */ 
/*      */     
/*      */     public float floatValue() {
/* 1021 */       return this.floatVal;
/*      */     }
/*      */   }
/*      */   
/* 1025 */   static final ASCIIToBinaryConverter A2BC_POSITIVE_INFINITY = new PreparedASCIIToBinaryBuffer(Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
/* 1026 */   static final ASCIIToBinaryConverter A2BC_NEGATIVE_INFINITY = new PreparedASCIIToBinaryBuffer(Double.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
/* 1027 */   static final ASCIIToBinaryConverter A2BC_NOT_A_NUMBER = new PreparedASCIIToBinaryBuffer(Double.NaN, Float.NaN);
/* 1028 */   static final ASCIIToBinaryConverter A2BC_POSITIVE_ZERO = new PreparedASCIIToBinaryBuffer(0.0D, 0.0F);
/* 1029 */   static final ASCIIToBinaryConverter A2BC_NEGATIVE_ZERO = new PreparedASCIIToBinaryBuffer(-0.0D, -0.0F);
/*      */ 
/*      */   
/*      */   static class ASCIIToBinaryBuffer
/*      */     implements ASCIIToBinaryConverter
/*      */   {
/*      */     boolean isNegative;
/*      */     
/*      */     int decExponent;
/*      */     char[] digits;
/*      */     int nDigits;
/*      */     
/*      */     ASCIIToBinaryBuffer(boolean param1Boolean, int param1Int1, char[] param1ArrayOfchar, int param1Int2) {
/* 1042 */       this.isNegative = param1Boolean;
/* 1043 */       this.decExponent = param1Int1;
/* 1044 */       this.digits = param1ArrayOfchar;
/* 1045 */       this.nDigits = param1Int2;
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
/*      */     public double doubleValue() {
/* 1058 */       int i = Math.min(this.nDigits, 16);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1063 */       int j = this.digits[0] - 48;
/* 1064 */       int k = Math.min(i, 9);
/* 1065 */       for (byte b = 1; b < k; b++) {
/* 1066 */         j = j * 10 + this.digits[b] - 48;
/*      */       }
/* 1068 */       long l1 = j;
/* 1069 */       for (int m = k; m < i; m++) {
/* 1070 */         l1 = l1 * 10L + (this.digits[m] - 48);
/*      */       }
/* 1072 */       double d = l1;
/* 1073 */       int n = this.decExponent - i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1080 */       if (this.nDigits <= 15) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1091 */         if (n == 0 || d == 0.0D) {
/* 1092 */           return this.isNegative ? -d : d;
/*      */         }
/* 1094 */         if (n >= 0) {
/* 1095 */           if (n <= MAX_SMALL_TEN) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1100 */             double d1 = d * SMALL_10_POW[n];
/* 1101 */             return this.isNegative ? -d1 : d1;
/*      */           } 
/* 1103 */           int i4 = 15 - i;
/* 1104 */           if (n <= MAX_SMALL_TEN + i4)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1111 */             d *= SMALL_10_POW[i4];
/* 1112 */             double d1 = d * SMALL_10_POW[n - i4];
/* 1113 */             return this.isNegative ? -d1 : d1;
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/* 1119 */         else if (n >= -MAX_SMALL_TEN) {
/*      */ 
/*      */ 
/*      */           
/* 1123 */           double d1 = d / SMALL_10_POW[-n];
/* 1124 */           return this.isNegative ? -d1 : d1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1140 */       if (n > 0) {
/* 1141 */         if (this.decExponent > 309)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1146 */           return this.isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
/*      */         }
/* 1148 */         if ((n & 0xF) != 0) {
/* 1149 */           d *= SMALL_10_POW[n & 0xF];
/*      */         }
/* 1151 */         if ((n >>= 4) != 0) {
/*      */           byte b1;
/* 1153 */           for (b1 = 0; n > 1; b1++, n >>= 1) {
/* 1154 */             if ((n & 0x1) != 0) {
/* 1155 */               d *= BIG_10_POW[b1];
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1164 */           double d1 = d * BIG_10_POW[b1];
/* 1165 */           if (Double.isInfinite(d1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1178 */             d1 = d / 2.0D;
/* 1179 */             d1 *= BIG_10_POW[b1];
/* 1180 */             if (Double.isInfinite(d1)) {
/* 1181 */               return this.isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
/*      */             }
/* 1183 */             d1 = Double.MAX_VALUE;
/*      */           } 
/* 1185 */           d = d1;
/*      */         } 
/* 1187 */       } else if (n < 0) {
/* 1188 */         n = -n;
/* 1189 */         if (this.decExponent < -325)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1194 */           return this.isNegative ? -0.0D : 0.0D;
/*      */         }
/* 1196 */         if ((n & 0xF) != 0) {
/* 1197 */           d /= SMALL_10_POW[n & 0xF];
/*      */         }
/* 1199 */         if ((n >>= 4) != 0) {
/*      */           byte b1;
/* 1201 */           for (b1 = 0; n > 1; b1++, n >>= 1) {
/* 1202 */             if ((n & 0x1) != 0) {
/* 1203 */               d *= TINY_10_POW[b1];
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1212 */           double d1 = d * TINY_10_POW[b1];
/* 1213 */           if (d1 == 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1226 */             d1 = d * 2.0D;
/* 1227 */             d1 *= TINY_10_POW[b1];
/* 1228 */             if (d1 == 0.0D) {
/* 1229 */               return this.isNegative ? -0.0D : 0.0D;
/*      */             }
/* 1231 */             d1 = Double.MIN_VALUE;
/*      */           } 
/* 1233 */           d = d1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1244 */       if (this.nDigits > 1100) {
/* 1245 */         this.nDigits = 1101;
/* 1246 */         this.digits[1100] = '1';
/*      */       } 
/* 1248 */       FDBigInteger fDBigInteger1 = new FDBigInteger(l1, this.digits, i, this.nDigits);
/* 1249 */       n = this.decExponent - this.nDigits;
/*      */       
/* 1251 */       long l2 = Double.doubleToRawLongBits(d);
/* 1252 */       int i1 = Math.max(0, -n);
/* 1253 */       int i2 = Math.max(0, n);
/* 1254 */       fDBigInteger1 = fDBigInteger1.multByPow52(i2, 0);
/* 1255 */       fDBigInteger1.makeImmutable();
/* 1256 */       FDBigInteger fDBigInteger2 = null;
/* 1257 */       int i3 = 0;
/*      */       do {
/*      */         int i11;
/*      */         FDBigInteger fDBigInteger4;
/*      */         boolean bool;
/* 1262 */         int i4 = (int)(l2 >>> 52L);
/* 1263 */         long l = l2 & 0xFFFFFFFFFFFFFL;
/* 1264 */         if (i4 > 0) {
/* 1265 */           l |= 0x10000000000000L;
/*      */         } else {
/* 1267 */           assert l != 0L : l;
/* 1268 */           int i14 = Long.numberOfLeadingZeros(l);
/* 1269 */           int i15 = i14 - 11;
/* 1270 */           l <<= i15;
/* 1271 */           i4 = 1 - i15;
/*      */         } 
/* 1273 */         i4 -= 1023;
/* 1274 */         int i5 = Long.numberOfTrailingZeros(l);
/* 1275 */         l >>>= i5;
/* 1276 */         int i6 = i4 - 52 + i5;
/* 1277 */         int i7 = 53 - i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1288 */         int i8 = i1;
/* 1289 */         int i9 = i2;
/*      */         
/* 1291 */         if (i6 >= 0) {
/* 1292 */           i8 += i6;
/*      */         } else {
/* 1294 */           i9 -= i6;
/*      */         } 
/* 1296 */         int i10 = i8;
/*      */ 
/*      */ 
/*      */         
/* 1300 */         if (i4 <= -1023) {
/*      */ 
/*      */ 
/*      */           
/* 1304 */           i11 = i4 + i5 + 1023;
/*      */         } else {
/* 1306 */           i11 = 1 + i5;
/*      */         } 
/* 1308 */         i8 += i11;
/* 1309 */         i9 += i11;
/*      */ 
/*      */         
/* 1312 */         int i12 = Math.min(i8, Math.min(i9, i10));
/* 1313 */         i8 -= i12;
/* 1314 */         i9 -= i12;
/* 1315 */         i10 -= i12;
/*      */         
/* 1317 */         FDBigInteger fDBigInteger3 = FDBigInteger.valueOfMulPow52(l, i1, i8);
/* 1318 */         if (fDBigInteger2 == null || i3 != i9) {
/* 1319 */           fDBigInteger2 = fDBigInteger1.leftShift(i9);
/* 1320 */           i3 = i9;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i13;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1339 */         if ((i13 = fDBigInteger3.cmp(fDBigInteger2)) > 0) {
/* 1340 */           bool = true;
/* 1341 */           fDBigInteger4 = fDBigInteger3.leftInplaceSub(fDBigInteger2);
/* 1342 */           if (i7 == 1 && i6 > -1022) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1347 */             i10--;
/* 1348 */             if (i10 < 0) {
/*      */ 
/*      */               
/* 1351 */               i10 = 0;
/* 1352 */               fDBigInteger4 = fDBigInteger4.leftShift(1);
/*      */             } 
/*      */           } 
/* 1355 */         } else if (i13 < 0) {
/* 1356 */           bool = false;
/* 1357 */           fDBigInteger4 = fDBigInteger2.rightInplaceSub(fDBigInteger3);
/*      */         } else {
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 1363 */         i13 = fDBigInteger4.cmpPow52(i1, i10);
/* 1364 */         if (i13 < 0) {
/*      */           break;
/*      */         }
/*      */         
/* 1368 */         if (i13 == 0) {
/*      */ 
/*      */           
/* 1371 */           if ((l2 & 0x1L) != 0L) {
/* 1372 */             l2 += bool ? -1L : 1L;
/*      */           }
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 1380 */         l2 += bool ? -1L : 1L;
/* 1381 */       } while (l2 != 0L && l2 != 9218868437227405312L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1388 */       if (this.isNegative) {
/* 1389 */         l2 |= Long.MIN_VALUE;
/*      */       }
/* 1391 */       return Double.longBitsToDouble(l2);
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
/*      */     public float floatValue() {
/* 1405 */       int i = Math.min(this.nDigits, 8);
/*      */ 
/*      */ 
/*      */       
/* 1409 */       int j = this.digits[0] - 48;
/* 1410 */       for (byte b = 1; b < i; b++) {
/* 1411 */         j = j * 10 + this.digits[b] - 48;
/*      */       }
/* 1413 */       float f = j;
/* 1414 */       int k = this.decExponent - i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1421 */       if (this.nDigits <= 7) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1432 */         if (k == 0 || f == 0.0F)
/* 1433 */           return this.isNegative ? -f : f; 
/* 1434 */         if (k >= 0) {
/* 1435 */           if (k <= SINGLE_MAX_SMALL_TEN) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1440 */             f *= SINGLE_SMALL_10_POW[k];
/* 1441 */             return this.isNegative ? -f : f;
/*      */           } 
/* 1443 */           int i3 = 7 - i;
/* 1444 */           if (k <= SINGLE_MAX_SMALL_TEN + i3)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1451 */             f *= SINGLE_SMALL_10_POW[i3];
/* 1452 */             f *= SINGLE_SMALL_10_POW[k - i3];
/* 1453 */             return this.isNegative ? -f : f;
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/* 1459 */         else if (k >= -SINGLE_MAX_SMALL_TEN) {
/*      */ 
/*      */ 
/*      */           
/* 1463 */           f /= SINGLE_SMALL_10_POW[-k];
/* 1464 */           return this.isNegative ? -f : f;
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1470 */       else if (this.decExponent >= this.nDigits && this.nDigits + this.decExponent <= 15) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1480 */         long l = j;
/* 1481 */         for (int i3 = i; i3 < this.nDigits; i3++) {
/* 1482 */           l = l * 10L + (this.digits[i3] - 48);
/*      */         }
/* 1484 */         double d1 = l;
/* 1485 */         k = this.decExponent - this.nDigits;
/* 1486 */         d1 *= SMALL_10_POW[k];
/* 1487 */         f = (float)d1;
/* 1488 */         return this.isNegative ? -f : f;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1500 */       double d = f;
/* 1501 */       if (k > 0) {
/* 1502 */         if (this.decExponent > 39)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1507 */           return this.isNegative ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
/*      */         }
/* 1509 */         if ((k & 0xF) != 0) {
/* 1510 */           d *= SMALL_10_POW[k & 0xF];
/*      */         }
/* 1512 */         if ((k >>= 4) != 0)
/*      */         {
/* 1514 */           for (byte b1 = 0; k > 0; b1++, k >>= 1) {
/* 1515 */             if ((k & 0x1) != 0) {
/* 1516 */               d *= BIG_10_POW[b1];
/*      */             }
/*      */           } 
/*      */         }
/* 1520 */       } else if (k < 0) {
/* 1521 */         k = -k;
/* 1522 */         if (this.decExponent < -46)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1527 */           return this.isNegative ? -0.0F : 0.0F;
/*      */         }
/* 1529 */         if ((k & 0xF) != 0) {
/* 1530 */           d /= SMALL_10_POW[k & 0xF];
/*      */         }
/* 1532 */         if ((k >>= 4) != 0)
/*      */         {
/* 1534 */           for (byte b1 = 0; k > 0; b1++, k >>= 1) {
/* 1535 */             if ((k & 0x1) != 0) {
/* 1536 */               d *= TINY_10_POW[b1];
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/* 1541 */       f = Math.max(Float.MIN_VALUE, Math.min(Float.MAX_VALUE, (float)d));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1550 */       if (this.nDigits > 200) {
/* 1551 */         this.nDigits = 201;
/* 1552 */         this.digits[200] = '1';
/*      */       } 
/* 1554 */       FDBigInteger fDBigInteger1 = new FDBigInteger(j, this.digits, i, this.nDigits);
/* 1555 */       k = this.decExponent - this.nDigits;
/*      */       
/* 1557 */       int m = Float.floatToRawIntBits(f);
/* 1558 */       int n = Math.max(0, -k);
/* 1559 */       int i1 = Math.max(0, k);
/* 1560 */       fDBigInteger1 = fDBigInteger1.multByPow52(i1, 0);
/* 1561 */       fDBigInteger1.makeImmutable();
/* 1562 */       FDBigInteger fDBigInteger2 = null;
/* 1563 */       int i2 = 0;
/*      */       do {
/*      */         int i11;
/*      */         FDBigInteger fDBigInteger4;
/*      */         boolean bool;
/* 1568 */         int i3 = m >>> 23;
/* 1569 */         int i4 = m & 0x7FFFFF;
/* 1570 */         if (i3 > 0) {
/* 1571 */           i4 |= 0x800000;
/*      */         } else {
/* 1573 */           assert i4 != 0 : i4;
/* 1574 */           int i14 = Integer.numberOfLeadingZeros(i4);
/* 1575 */           int i15 = i14 - 8;
/* 1576 */           i4 <<= i15;
/* 1577 */           i3 = 1 - i15;
/*      */         } 
/* 1579 */         i3 -= 127;
/* 1580 */         int i5 = Integer.numberOfTrailingZeros(i4);
/* 1581 */         i4 >>>= i5;
/* 1582 */         int i6 = i3 - 23 + i5;
/* 1583 */         int i7 = 24 - i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1594 */         int i8 = n;
/* 1595 */         int i9 = i1;
/*      */         
/* 1597 */         if (i6 >= 0) {
/* 1598 */           i8 += i6;
/*      */         } else {
/* 1600 */           i9 -= i6;
/*      */         } 
/* 1602 */         int i10 = i8;
/*      */ 
/*      */ 
/*      */         
/* 1606 */         if (i3 <= -127) {
/*      */ 
/*      */ 
/*      */           
/* 1610 */           i11 = i3 + i5 + 127;
/*      */         } else {
/* 1612 */           i11 = 1 + i5;
/*      */         } 
/* 1614 */         i8 += i11;
/* 1615 */         i9 += i11;
/*      */ 
/*      */         
/* 1618 */         int i12 = Math.min(i8, Math.min(i9, i10));
/* 1619 */         i8 -= i12;
/* 1620 */         i9 -= i12;
/* 1621 */         i10 -= i12;
/*      */         
/* 1623 */         FDBigInteger fDBigInteger3 = FDBigInteger.valueOfMulPow52(i4, n, i8);
/* 1624 */         if (fDBigInteger2 == null || i2 != i9) {
/* 1625 */           fDBigInteger2 = fDBigInteger1.leftShift(i9);
/* 1626 */           i2 = i9;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i13;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1645 */         if ((i13 = fDBigInteger3.cmp(fDBigInteger2)) > 0) {
/* 1646 */           bool = true;
/* 1647 */           fDBigInteger4 = fDBigInteger3.leftInplaceSub(fDBigInteger2);
/* 1648 */           if (i7 == 1 && i6 > -126) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1653 */             i10--;
/* 1654 */             if (i10 < 0) {
/*      */ 
/*      */               
/* 1657 */               i10 = 0;
/* 1658 */               fDBigInteger4 = fDBigInteger4.leftShift(1);
/*      */             } 
/*      */           } 
/* 1661 */         } else if (i13 < 0) {
/* 1662 */           bool = false;
/* 1663 */           fDBigInteger4 = fDBigInteger2.rightInplaceSub(fDBigInteger3);
/*      */         } else {
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 1669 */         i13 = fDBigInteger4.cmpPow52(n, i10);
/* 1670 */         if (i13 < 0) {
/*      */           break;
/*      */         }
/*      */         
/* 1674 */         if (i13 == 0) {
/*      */ 
/*      */           
/* 1677 */           if ((m & 0x1) != 0) {
/* 1678 */             m += bool ? -1 : 1;
/*      */           }
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 1686 */         m += bool ? -1 : 1;
/* 1687 */       } while (m != 0 && m != 2139095040);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1694 */       if (this.isNegative) {
/* 1695 */         m |= Integer.MIN_VALUE;
/*      */       }
/* 1697 */       return Float.intBitsToFloat(m);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1705 */     private static final double[] SMALL_10_POW = new double[] { 1.0D, 10.0D, 100.0D, 1000.0D, 10000.0D, 100000.0D, 1000000.0D, 1.0E7D, 1.0E8D, 1.0E9D, 1.0E10D, 1.0E11D, 1.0E12D, 1.0E13D, 1.0E14D, 1.0E15D, 1.0E16D, 1.0E17D, 1.0E18D, 1.0E19D, 1.0E20D, 1.0E21D, 1.0E22D };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1714 */     private static final float[] SINGLE_SMALL_10_POW = new float[] { 1.0F, 10.0F, 100.0F, 1000.0F, 10000.0F, 100000.0F, 1000000.0F, 1.0E7F, 1.0E8F, 1.0E9F, 1.0E10F };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1720 */     private static final double[] BIG_10_POW = new double[] { 1.0E16D, 1.0E32D, 1.0E64D, 1.0E128D, 1.0E256D };
/*      */     
/* 1722 */     private static final double[] TINY_10_POW = new double[] { 1.0E-16D, 1.0E-32D, 1.0E-64D, 1.0E-128D, 1.0E-256D };
/*      */ 
/*      */     
/* 1725 */     private static final int MAX_SMALL_TEN = SMALL_10_POW.length - 1;
/* 1726 */     private static final int SINGLE_MAX_SMALL_TEN = SINGLE_SMALL_10_POW.length - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BinaryToASCIIConverter getBinaryToASCIIConverter(double paramDouble) {
/* 1738 */     return getBinaryToASCIIConverter(paramDouble, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BinaryToASCIIConverter getBinaryToASCIIConverter(double paramDouble, boolean paramBoolean) {
/*      */     byte b;
/* 1750 */     long l1 = Double.doubleToRawLongBits(paramDouble);
/* 1751 */     boolean bool = ((l1 & Long.MIN_VALUE) != 0L) ? true : false;
/* 1752 */     long l2 = l1 & 0xFFFFFFFFFFFFFL;
/* 1753 */     int i = (int)((l1 & 0x7FF0000000000000L) >> 52L);
/*      */     
/* 1755 */     if (i == 2047) {
/* 1756 */       if (l2 == 0L) {
/* 1757 */         return bool ? B2AC_NEGATIVE_INFINITY : B2AC_POSITIVE_INFINITY;
/*      */       }
/* 1759 */       return B2AC_NOT_A_NUMBER;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1767 */     if (i == 0) {
/* 1768 */       if (l2 == 0L)
/*      */       {
/* 1770 */         return bool ? B2AC_NEGATIVE_ZERO : B2AC_POSITIVE_ZERO;
/*      */       }
/* 1772 */       int j = Long.numberOfLeadingZeros(l2);
/* 1773 */       int k = j - 11;
/* 1774 */       l2 <<= k;
/* 1775 */       i = 1 - k;
/* 1776 */       b = 64 - j;
/*      */     } else {
/* 1778 */       l2 |= 0x10000000000000L;
/* 1779 */       b = 53;
/*      */     } 
/* 1781 */     i -= 1023;
/* 1782 */     BinaryToASCIIBuffer binaryToASCIIBuffer = getBinaryToASCIIBuffer();
/* 1783 */     binaryToASCIIBuffer.setSign(bool);
/*      */     
/* 1785 */     binaryToASCIIBuffer.dtoa(i, l2, b, paramBoolean);
/* 1786 */     return binaryToASCIIBuffer;
/*      */   }
/*      */   private static BinaryToASCIIConverter getBinaryToASCIIConverter(float paramFloat) {
/*      */     byte b;
/* 1790 */     int i = Float.floatToRawIntBits(paramFloat);
/* 1791 */     boolean bool = ((i & Integer.MIN_VALUE) != 0) ? true : false;
/* 1792 */     int j = i & 0x7FFFFF;
/* 1793 */     int k = (i & 0x7F800000) >> 23;
/*      */     
/* 1795 */     if (k == 255) {
/* 1796 */       if (j == 0L) {
/* 1797 */         return bool ? B2AC_NEGATIVE_INFINITY : B2AC_POSITIVE_INFINITY;
/*      */       }
/* 1799 */       return B2AC_NOT_A_NUMBER;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1807 */     if (k == 0) {
/* 1808 */       if (j == 0)
/*      */       {
/* 1810 */         return bool ? B2AC_NEGATIVE_ZERO : B2AC_POSITIVE_ZERO;
/*      */       }
/* 1812 */       int m = Integer.numberOfLeadingZeros(j);
/* 1813 */       int n = m - 8;
/* 1814 */       j <<= n;
/* 1815 */       k = 1 - n;
/* 1816 */       b = 32 - m;
/*      */     } else {
/* 1818 */       j |= 0x800000;
/* 1819 */       b = 24;
/*      */     } 
/* 1821 */     k -= 127;
/* 1822 */     BinaryToASCIIBuffer binaryToASCIIBuffer = getBinaryToASCIIBuffer();
/* 1823 */     binaryToASCIIBuffer.setSign(bool);
/*      */     
/* 1825 */     binaryToASCIIBuffer.dtoa(k, j << 29L, b, true);
/* 1826 */     return binaryToASCIIBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   static ASCIIToBinaryConverter readJavaFormatString(String paramString) throws NumberFormatException {
/* 1831 */     boolean bool1 = false;
/* 1832 */     boolean bool2 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1838 */     try { paramString = paramString.trim();
/*      */       
/* 1840 */       int i = paramString.length();
/* 1841 */       if (i == 0) {
/* 1842 */         throw new NumberFormatException("empty String");
/*      */       }
/* 1844 */       byte b = 0;
/* 1845 */       switch (paramString.charAt(b)) {
/*      */         case '-':
/* 1847 */           bool1 = true;
/*      */         
/*      */         case '+':
/* 1850 */           b++;
/* 1851 */           bool2 = true; break;
/*      */       } 
/* 1853 */       char c = paramString.charAt(b);
/* 1854 */       if (c == 'N')
/* 1855 */       { if (i - b == NAN_LENGTH && paramString.indexOf("NaN", b) == b) {
/* 1856 */           return A2BC_NOT_A_NUMBER;
/*      */         
/*      */         } }
/*      */       
/* 1860 */       else if (c == 'I')
/* 1861 */       { if (i - b == INFINITY_LENGTH && paramString.indexOf("Infinity", b) == b) {
/* 1862 */           return bool1 ? A2BC_NEGATIVE_INFINITY : A2BC_POSITIVE_INFINITY;
/*      */         } }
/*      */       else
/*      */       
/* 1866 */       { if (c == '0' && 
/* 1867 */           i > b + 1) {
/* 1868 */           char c1 = paramString.charAt(b + 1);
/* 1869 */           if (c1 == 'x' || c1 == 'X') {
/* 1870 */             return parseHexString(paramString);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1875 */         char[] arrayOfChar = new char[i];
/* 1876 */         int j = 0;
/* 1877 */         boolean bool3 = false;
/* 1878 */         byte b1 = 0;
/* 1879 */         byte b2 = 0;
/* 1880 */         byte b3 = 0;
/*      */ 
/*      */         
/* 1883 */         while (b < i) {
/* 1884 */           c = paramString.charAt(b);
/* 1885 */           if (c == '0') {
/* 1886 */             b2++;
/* 1887 */           } else if (c == '.') {
/* 1888 */             if (bool3)
/*      */             {
/* 1890 */               throw new NumberFormatException("multiple points");
/*      */             }
/* 1892 */             b1 = b;
/* 1893 */             if (bool2) {
/* 1894 */               b1--;
/*      */             }
/* 1896 */             bool3 = true;
/*      */           } else {
/*      */             break;
/*      */           } 
/* 1900 */           b++;
/*      */         } 
/*      */         
/* 1903 */         while (b < i) {
/* 1904 */           c = paramString.charAt(b);
/* 1905 */           if (c >= '1' && c <= '9') {
/* 1906 */             arrayOfChar[j++] = c;
/* 1907 */             b3 = 0;
/* 1908 */           } else if (c == '0') {
/* 1909 */             arrayOfChar[j++] = c;
/* 1910 */             b3++;
/* 1911 */           } else if (c == '.') {
/* 1912 */             if (bool3)
/*      */             {
/* 1914 */               throw new NumberFormatException("multiple points");
/*      */             }
/* 1916 */             b1 = b;
/* 1917 */             if (bool2) {
/* 1918 */               b1--;
/*      */             }
/* 1920 */             bool3 = true;
/*      */           } else {
/*      */             break;
/*      */           } 
/* 1924 */           b++;
/*      */         } 
/* 1926 */         j -= b3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1946 */         boolean bool4 = (j == 0) ? true : false;
/* 1947 */         if (!bool4 || b2 != 0)
/*      */         { int k;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1958 */           if (bool3) {
/* 1959 */             k = b1 - b2;
/*      */           } else {
/* 1961 */             k = j + b3;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1967 */           if (b < i && ((c = paramString.charAt(b)) == 'e' || c == 'E'))
/* 1968 */           { byte b4 = 1;
/* 1969 */             int m = 0;
/* 1970 */             int n = 214748364;
/* 1971 */             boolean bool = false;
/* 1972 */             switch (paramString.charAt(++b)) {
/*      */               case '-':
/* 1974 */                 b4 = -1;
/*      */               
/*      */               case '+':
/* 1977 */                 b++; break;
/*      */             } 
/* 1979 */             byte b5 = b;
/*      */             
/* 1981 */             while (b < i) {
/* 1982 */               if (m >= n)
/*      */               {
/*      */                 
/* 1985 */                 bool = true;
/*      */               }
/* 1987 */               c = paramString.charAt(b++);
/* 1988 */               if (c >= '0' && c <= '9') {
/* 1989 */                 m = m * 10 + c - 48; continue;
/*      */               } 
/* 1991 */               b--;
/*      */             } 
/*      */ 
/*      */             
/* 1995 */             int i1 = 324 + j + b3;
/* 1996 */             if (bool || m > i1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2009 */               k = b4 * i1;
/*      */             }
/*      */             else {
/*      */               
/* 2013 */               k += b4 * m;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2022 */             if (b == b5)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2043 */               throw new NumberFormatException("For input string: \"" + paramString + "\""); }  }  if (b >= i || (b == i - 1 && (paramString.charAt(b) == 'f' || paramString.charAt(b) == 'F' || paramString.charAt(b) == 'd' || paramString.charAt(b) == 'D'))) { if (bool4) return bool1 ? A2BC_NEGATIVE_ZERO : A2BC_POSITIVE_ZERO;  return new ASCIIToBinaryBuffer(bool1, k, arrayOfChar, j); }  }  }  } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {} throw new NumberFormatException("For input string: \"" + paramString + "\"");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class HexFloatPattern
/*      */   {
/* 2051 */     private static final Pattern VALUE = Pattern.compile("([-+])?0[xX](((\\p{XDigit}+)\\.?)|((\\p{XDigit}*)\\.(\\p{XDigit}+)))[pP]([-+])?(\\p{Digit}+)[fFdD]?");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ASCIIToBinaryConverter parseHexString(String paramString) {
/*      */     long l1;
/* 2067 */     Matcher matcher = HexFloatPattern.VALUE.matcher(paramString);
/* 2068 */     boolean bool = matcher.matches();
/* 2069 */     if (!bool)
/*      */     {
/* 2071 */       throw new NumberFormatException("For input string: \"" + paramString + "\"");
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
/* 2097 */     String str1 = matcher.group(1);
/* 2098 */     boolean bool1 = (str1 != null && str1.equals("-")) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2129 */     String str2 = null;
/* 2130 */     int i = 0;
/* 2131 */     int j = 0;
/*      */     
/* 2133 */     int k = 0;
/*      */ 
/*      */     
/* 2136 */     int m = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String str4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2150 */     if ((str4 = matcher.group(4)) != null) {
/*      */       
/* 2152 */       str2 = stripLeadingZeros(str4);
/* 2153 */       k = str2.length();
/*      */     }
/*      */     else {
/*      */       
/* 2157 */       String str5 = stripLeadingZeros(matcher.group(6));
/* 2158 */       k = str5.length();
/*      */ 
/*      */       
/* 2161 */       String str6 = matcher.group(7);
/* 2162 */       m = str6.length();
/*      */ 
/*      */       
/* 2165 */       str2 = ((str5 == null) ? "" : str5) + str6;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2171 */     str2 = stripLeadingZeros(str2);
/* 2172 */     i = str2.length();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2177 */     if (k >= 1) {
/* 2178 */       j = 4 * (k - 1);
/*      */     } else {
/* 2180 */       j = -4 * (m - i + 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2186 */     if (i == 0) {
/* 2187 */       return bool1 ? A2BC_NEGATIVE_ZERO : A2BC_POSITIVE_ZERO;
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
/* 2199 */     String str3 = matcher.group(8);
/* 2200 */     m = (str3 == null || str3.equals("+")) ? 1 : 0;
/*      */     
/*      */     try {
/* 2203 */       l1 = Integer.parseInt(matcher.group(9));
/*      */     }
/* 2205 */     catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2219 */       return bool1 ? ((m != 0) ? A2BC_NEGATIVE_INFINITY : A2BC_NEGATIVE_ZERO) : ((m != 0) ? A2BC_POSITIVE_INFINITY : A2BC_POSITIVE_ZERO);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2225 */     long l2 = ((m != 0) ? 1L : -1L) * l1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2230 */     long l3 = l2 + j;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2236 */     boolean bool2 = false;
/* 2237 */     boolean bool3 = false;
/* 2238 */     byte b1 = 0;
/* 2239 */     long l4 = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2246 */     long l5 = getHexDigit(str2, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2257 */     if (l5 == 1L) {
/* 2258 */       l4 |= l5 << 52L;
/* 2259 */       b1 = 48;
/*      */     }
/* 2261 */     else if (l5 <= 3L) {
/* 2262 */       l4 |= l5 << 51L;
/* 2263 */       b1 = 47;
/* 2264 */       l3++;
/* 2265 */     } else if (l5 <= 7L) {
/* 2266 */       l4 |= l5 << 50L;
/* 2267 */       b1 = 46;
/* 2268 */       l3 += 2L;
/* 2269 */     } else if (l5 <= 15L) {
/* 2270 */       l4 |= l5 << 49L;
/* 2271 */       b1 = 45;
/* 2272 */       l3 += 3L;
/*      */     } else {
/* 2274 */       throw new AssertionError("Result from digit conversion too large!");
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
/* 2295 */     byte b2 = 0;
/* 2296 */     b2 = 1;
/* 2297 */     for (; b2 < i && b1 >= 0; 
/* 2298 */       b2++) {
/* 2299 */       long l = getHexDigit(str2, b2);
/* 2300 */       l4 |= l << b1;
/* 2301 */       b1 -= 4;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2309 */     if (b2 < i) {
/* 2310 */       long l = getHexDigit(str2, b2);
/*      */ 
/*      */ 
/*      */       
/* 2314 */       switch (b1) {
/*      */ 
/*      */         
/*      */         case -1:
/* 2318 */           l4 |= (l & 0xEL) >> 1L;
/* 2319 */           bool2 = ((l & 0x1L) != 0L) ? true : false;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case -2:
/* 2325 */           l4 |= (l & 0xCL) >> 2L;
/* 2326 */           bool2 = ((l & 0x2L) != 0L) ? true : false;
/* 2327 */           bool3 = ((l & 0x1L) != 0L) ? true : false;
/*      */           break;
/*      */ 
/*      */         
/*      */         case -3:
/* 2332 */           l4 |= (l & 0x8L) >> 3L;
/*      */           
/* 2334 */           bool2 = ((l & 0x4L) != 0L) ? true : false;
/* 2335 */           bool3 = ((l & 0x3L) != 0L) ? true : false;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case -4:
/* 2341 */           bool2 = ((l & 0x8L) != 0L) ? true : false;
/*      */           
/* 2343 */           bool3 = ((l & 0x7L) != 0L) ? true : false;
/*      */           break;
/*      */         
/*      */         default:
/* 2347 */           throw new AssertionError("Unexpected shift distance remainder.");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2356 */       b2++;
/* 2357 */       while (b2 < i && !bool3) {
/* 2358 */         l = getHexDigit(str2, b2);
/* 2359 */         bool3 = (bool3 || l != 0L) ? true : false;
/* 2360 */         b2++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2368 */     int n = bool1 ? Integer.MIN_VALUE : 0;
/* 2369 */     if (l3 >= -126L) {
/* 2370 */       if (l3 > 127L) {
/*      */         
/* 2372 */         n |= 0x7F800000;
/*      */       } else {
/* 2374 */         byte b = 28;
/* 2375 */         boolean bool5 = ((l4 & (1L << b) - 1L) != 0L || bool2 || bool3) ? true : false;
/* 2376 */         int i1 = (int)(l4 >>> b);
/* 2377 */         if ((i1 & 0x3) != 1 || bool5) {
/* 2378 */           i1++;
/*      */         }
/* 2380 */         n |= ((int)l3 + 126 << 23) + (i1 >> 1);
/*      */       }
/*      */     
/* 2383 */     } else if (l3 >= -150L) {
/*      */ 
/*      */ 
/*      */       
/* 2387 */       int i1 = (int)(-98L - l3);
/* 2388 */       assert i1 >= 29;
/* 2389 */       assert i1 < 53;
/* 2390 */       boolean bool5 = ((l4 & (1L << i1) - 1L) != 0L || bool2 || bool3) ? true : false;
/* 2391 */       int i2 = (int)(l4 >>> i1);
/* 2392 */       if ((i2 & 0x3) != 1 || bool5) {
/* 2393 */         i2++;
/*      */       }
/* 2395 */       n |= i2 >> 1;
/*      */     } 
/*      */     
/* 2398 */     float f = Float.intBitsToFloat(n);
/*      */ 
/*      */     
/* 2401 */     if (l3 > 1023L)
/*      */     {
/* 2403 */       return bool1 ? A2BC_NEGATIVE_INFINITY : A2BC_POSITIVE_INFINITY;
/*      */     }
/* 2405 */     if (l3 <= 1023L && l3 >= -1022L) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2419 */       l4 = l3 + 1023L << 52L & 0x7FF0000000000000L | 0xFFFFFFFFFFFFFL & l4;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2428 */       if (l3 < -1075L)
/*      */       {
/*      */ 
/*      */         
/* 2432 */         return bool1 ? A2BC_NEGATIVE_ZERO : A2BC_POSITIVE_ZERO;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2440 */       bool3 = (bool3 || bool2) ? true : false;
/* 2441 */       bool2 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2449 */       int i1 = 53 - (int)l3 - -1074 + 1;
/*      */       
/* 2451 */       assert i1 >= 1 && i1 <= 53;
/*      */ 
/*      */ 
/*      */       
/* 2455 */       bool2 = ((l4 & 1L << i1 - 1) != 0L) ? true : false;
/* 2456 */       if (i1 > 1) {
/*      */ 
/*      */         
/* 2459 */         long l = -1L << i1 - 1 ^ 0xFFFFFFFFFFFFFFFFL;
/* 2460 */         bool3 = (bool3 || (l4 & l) != 0L) ? true : false;
/*      */       } 
/*      */ 
/*      */       
/* 2464 */       l4 >>= i1;
/*      */       
/* 2466 */       l4 = 0x0L | 0xFFFFFFFFFFFFFL & l4;
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
/* 2498 */     boolean bool4 = ((l4 & 0x1L) == 0L) ? true : false;
/* 2499 */     if ((bool4 && bool2 && bool3) || (!bool4 && bool2))
/*      */     {
/* 2501 */       l4++;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2506 */     double d = bool1 ? Double.longBitsToDouble(l4 | Long.MIN_VALUE) : Double.longBitsToDouble(l4);
/*      */     
/* 2508 */     return new PreparedASCIIToBinaryBuffer(d, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String stripLeadingZeros(String paramString) {
/* 2518 */     if (!paramString.isEmpty() && paramString.charAt(0) == '0') {
/* 2519 */       for (byte b = 1; b < paramString.length(); b++) {
/* 2520 */         if (paramString.charAt(b) != '0') {
/* 2521 */           return paramString.substring(b);
/*      */         }
/*      */       } 
/* 2524 */       return "";
/*      */     } 
/* 2526 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getHexDigit(String paramString, int paramInt) {
/* 2534 */     int i = Character.digit(paramString.charAt(paramInt), 16);
/* 2535 */     if (i <= -1 || i >= 16) {
/* 2536 */       throw new AssertionError("Unexpected failure of digit conversion of " + paramString
/* 2537 */           .charAt(paramInt));
/*      */     }
/* 2539 */     return i;
/*      */   }
/*      */   
/*      */   static interface ASCIIToBinaryConverter {
/*      */     double doubleValue();
/*      */     
/*      */     float floatValue();
/*      */   }
/*      */   
/*      */   public static interface BinaryToASCIIConverter {
/*      */     String toJavaFormatString();
/*      */     
/*      */     void appendTo(Appendable param1Appendable);
/*      */     
/*      */     int getDecimalExponent();
/*      */     
/*      */     int getDigits(char[] param1ArrayOfchar);
/*      */     
/*      */     boolean isNegative();
/*      */     
/*      */     boolean isExceptional();
/*      */     
/*      */     boolean digitsRoundedUp();
/*      */     
/*      */     boolean decimalDigitsExact();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/FloatingDecimal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */