/*      */ package sun.misc;
/*      */ 
/*      */ import java.math.BigInteger;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FDBigInteger
/*      */ {
/*   67 */   static final int[] SMALL_5_POW = new int[] { 1, 5, 25, 125, 625, 3125, 15625, 78125, 390625, 1953125, 9765625, 48828125, 244140625, 1220703125 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   static final long[] LONG_5_POW = new long[] { 1L, 5L, 25L, 125L, 625L, 3125L, 15625L, 78125L, 390625L, 1953125L, 9765625L, 48828125L, 244140625L, 1220703125L, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_FIVE_POW = 340;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   private static final FDBigInteger[] POW_5_CACHE = new FDBigInteger[340]; static {
/*  123 */     byte b = 0;
/*  124 */     while (b < SMALL_5_POW.length) {
/*  125 */       FDBigInteger fDBigInteger1 = new FDBigInteger(new int[] { SMALL_5_POW[b] }, 0);
/*  126 */       fDBigInteger1.makeImmutable();
/*  127 */       POW_5_CACHE[b] = fDBigInteger1;
/*  128 */       b++;
/*      */     } 
/*  130 */     FDBigInteger fDBigInteger = POW_5_CACHE[b - 1];
/*  131 */     while (b < 'Ŕ') {
/*  132 */       POW_5_CACHE[b] = fDBigInteger = fDBigInteger.mult(5);
/*  133 */       fDBigInteger.makeImmutable();
/*  134 */       b++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  139 */   public static final FDBigInteger ZERO = new FDBigInteger(new int[0], 0); private static final long LONG_MASK = 4294967295L;
/*      */   private int[] data;
/*      */   
/*      */   static {
/*  143 */     ZERO.makeImmutable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int offset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nWords;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isImmutable = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FDBigInteger(int[] paramArrayOfint, int paramInt) {
/*  186 */     this.data = paramArrayOfint;
/*  187 */     this.offset = paramInt;
/*  188 */     this.nWords = paramArrayOfint.length;
/*  189 */     trimLeadingZeros();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDBigInteger(long paramLong, char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  208 */     int i = Math.max((paramInt2 + 8) / 9, 2);
/*  209 */     this.data = new int[i];
/*  210 */     this.data[0] = (int)paramLong;
/*  211 */     this.data[1] = (int)(paramLong >>> 32L);
/*  212 */     this.offset = 0;
/*  213 */     this.nWords = 2;
/*  214 */     int j = paramInt1;
/*  215 */     int k = paramInt2 - 5;
/*      */     
/*  217 */     while (j < k) {
/*  218 */       int i2 = j + 5;
/*  219 */       int i1 = paramArrayOfchar[j++] - 48;
/*  220 */       while (j < i2) {
/*  221 */         i1 = 10 * i1 + paramArrayOfchar[j++] - 48;
/*      */       }
/*  223 */       multAddMe(100000, i1);
/*      */     } 
/*  225 */     int n = 1;
/*  226 */     int m = 0;
/*  227 */     while (j < paramInt2) {
/*  228 */       m = 10 * m + paramArrayOfchar[j++] - 48;
/*  229 */       n *= 10;
/*      */     } 
/*  231 */     if (n != 1) {
/*  232 */       multAddMe(n, m);
/*      */     }
/*  234 */     trimLeadingZeros();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FDBigInteger valueOfPow52(int paramInt1, int paramInt2) {
/*  251 */     if (paramInt1 != 0) {
/*  252 */       if (paramInt2 == 0)
/*  253 */         return big5pow(paramInt1); 
/*  254 */       if (paramInt1 < SMALL_5_POW.length) {
/*  255 */         int i = SMALL_5_POW[paramInt1];
/*  256 */         int j = paramInt2 >> 5;
/*  257 */         int k = paramInt2 & 0x1F;
/*  258 */         if (k == 0) {
/*  259 */           return new FDBigInteger(new int[] { i }, j);
/*      */         }
/*  261 */         return new FDBigInteger(new int[] { i << k, i >>> 32 - k }, j);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  267 */       return big5pow(paramInt1).leftShift(paramInt2);
/*      */     } 
/*      */     
/*  270 */     return valueOfPow2(paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FDBigInteger valueOfMulPow52(long paramLong, int paramInt1, int paramInt2) {
/*  289 */     assert paramInt1 >= 0 : paramInt1;
/*  290 */     assert paramInt2 >= 0 : paramInt2;
/*  291 */     int i = (int)paramLong;
/*  292 */     int j = (int)(paramLong >>> 32L);
/*  293 */     int k = paramInt2 >> 5;
/*  294 */     int m = paramInt2 & 0x1F;
/*  295 */     if (paramInt1 != 0) {
/*  296 */       int[] arrayOfInt; if (paramInt1 < SMALL_5_POW.length) {
/*  297 */         long l1 = SMALL_5_POW[paramInt1] & 0xFFFFFFFFL;
/*  298 */         long l2 = (i & 0xFFFFFFFFL) * l1;
/*  299 */         i = (int)l2;
/*  300 */         l2 >>>= 32L;
/*  301 */         l2 = (j & 0xFFFFFFFFL) * l1 + l2;
/*  302 */         j = (int)l2;
/*  303 */         int n = (int)(l2 >>> 32L);
/*  304 */         if (m == 0) {
/*  305 */           return new FDBigInteger(new int[] { i, j, n }, k);
/*      */         }
/*  307 */         return new FDBigInteger(new int[] { i << m, j << m | i >>> 32 - m, n << m | j >>> 32 - m, n >>> 32 - m }, k);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  315 */       FDBigInteger fDBigInteger = big5pow(paramInt1);
/*      */       
/*  317 */       if (j == 0) {
/*  318 */         arrayOfInt = new int[fDBigInteger.nWords + 1 + ((paramInt2 != 0) ? 1 : 0)];
/*  319 */         mult(fDBigInteger.data, fDBigInteger.nWords, i, arrayOfInt);
/*      */       } else {
/*  321 */         arrayOfInt = new int[fDBigInteger.nWords + 2 + ((paramInt2 != 0) ? 1 : 0)];
/*  322 */         mult(fDBigInteger.data, fDBigInteger.nWords, i, j, arrayOfInt);
/*      */       } 
/*  324 */       return (new FDBigInteger(arrayOfInt, fDBigInteger.offset)).leftShift(paramInt2);
/*      */     } 
/*  326 */     if (paramInt2 != 0) {
/*  327 */       if (m == 0) {
/*  328 */         return new FDBigInteger(new int[] { i, j }, k);
/*      */       }
/*  330 */       return new FDBigInteger(new int[] { i << m, j << m | i >>> 32 - m, j >>> 32 - m }, k);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  337 */     return new FDBigInteger(new int[] { i, j }, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FDBigInteger valueOfPow2(int paramInt) {
/*  353 */     int i = paramInt >> 5;
/*  354 */     int j = paramInt & 0x1F;
/*  355 */     return new FDBigInteger(new int[] { 1 << j }, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void trimLeadingZeros() {
/*  370 */     int i = this.nWords;
/*  371 */     if (i > 0 && this.data[--i] == 0) {
/*      */       
/*  373 */       while (i > 0 && this.data[i - 1] == 0) {
/*  374 */         i--;
/*      */       }
/*  376 */       this.nWords = i;
/*  377 */       if (i == 0) {
/*  378 */         this.offset = 0;
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
/*      */   public int getNormalizationBias() {
/*  396 */     if (this.nWords == 0) {
/*  397 */       throw new IllegalArgumentException("Zero value cannot be normalized");
/*      */     }
/*  399 */     int i = Integer.numberOfLeadingZeros(this.data[this.nWords - 1]);
/*  400 */     return (i < 4) ? (28 + i) : (i - 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void leftShift(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3, int paramInt4) {
/*  421 */     for (; paramInt1 > 0; paramInt1--) {
/*  422 */       int j = paramInt4 << paramInt2;
/*  423 */       paramInt4 = paramArrayOfint1[paramInt1 - 1];
/*  424 */       j |= paramInt4 >>> paramInt3;
/*  425 */       paramArrayOfint2[paramInt1] = j;
/*      */     } 
/*  427 */     int i = paramInt4 << paramInt2;
/*  428 */     paramArrayOfint2[0] = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDBigInteger leftShift(int paramInt) {
/*  458 */     if (paramInt == 0 || this.nWords == 0) {
/*  459 */       return this;
/*      */     }
/*  461 */     int i = paramInt >> 5;
/*  462 */     int j = paramInt & 0x1F;
/*  463 */     if (this.isImmutable) {
/*  464 */       int[] arrayOfInt; if (j == 0) {
/*  465 */         return new FDBigInteger(Arrays.copyOf(this.data, this.nWords), this.offset + i);
/*      */       }
/*  467 */       int k = 32 - j;
/*  468 */       int m = this.nWords - 1;
/*  469 */       int n = this.data[m];
/*  470 */       int i1 = n >>> k;
/*      */       
/*  472 */       if (i1 != 0) {
/*  473 */         arrayOfInt = new int[this.nWords + 1];
/*  474 */         arrayOfInt[this.nWords] = i1;
/*      */       } else {
/*  476 */         arrayOfInt = new int[this.nWords];
/*      */       } 
/*  478 */       leftShift(this.data, m, arrayOfInt, j, k, n);
/*  479 */       return new FDBigInteger(arrayOfInt, this.offset + i);
/*      */     } 
/*      */     
/*  482 */     if (j != 0) {
/*  483 */       int k = 32 - j;
/*  484 */       if (this.data[0] << j == 0) {
/*  485 */         byte b = 0;
/*  486 */         int m = this.data[b];
/*  487 */         for (; b < this.nWords - 1; b++) {
/*  488 */           int i1 = m >>> k;
/*  489 */           m = this.data[b + 1];
/*  490 */           i1 |= m << j;
/*  491 */           this.data[b] = i1;
/*      */         } 
/*  493 */         int n = m >>> k;
/*  494 */         this.data[b] = n;
/*  495 */         if (n == 0) {
/*  496 */           this.nWords--;
/*      */         }
/*  498 */         this.offset++;
/*      */       } else {
/*  500 */         int m = this.nWords - 1;
/*  501 */         int n = this.data[m];
/*  502 */         int i1 = n >>> k;
/*  503 */         int[] arrayOfInt1 = this.data;
/*  504 */         int[] arrayOfInt2 = this.data;
/*  505 */         if (i1 != 0) {
/*  506 */           if (this.nWords == this.data.length) {
/*  507 */             this.data = arrayOfInt1 = new int[this.nWords + 1];
/*      */           }
/*  509 */           arrayOfInt1[this.nWords++] = i1;
/*      */         } 
/*  511 */         leftShift(arrayOfInt2, m, arrayOfInt1, j, k, n);
/*      */       } 
/*      */     } 
/*  514 */     this.offset += i;
/*  515 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int size() {
/*  534 */     return this.nWords + this.offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int quoRemIteration(FDBigInteger paramFDBigInteger) throws IllegalArgumentException {
/*  564 */     assert !this.isImmutable : "cannot modify immutable value";
/*      */ 
/*      */ 
/*      */     
/*  568 */     int i = size();
/*  569 */     int j = paramFDBigInteger.size();
/*  570 */     if (i < j) {
/*      */ 
/*      */       
/*  573 */       int m = multAndCarryBy10(this.data, this.nWords, this.data);
/*  574 */       if (m != 0) {
/*  575 */         this.data[this.nWords++] = m;
/*      */       } else {
/*  577 */         trimLeadingZeros();
/*      */       } 
/*  579 */       return 0;
/*  580 */     }  if (i > j) {
/*  581 */       throw new IllegalArgumentException("disparate values");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  586 */     long l1 = (this.data[this.nWords - 1] & 0xFFFFFFFFL) / (paramFDBigInteger.data[paramFDBigInteger.nWords - 1] & 0xFFFFFFFFL);
/*  587 */     long l2 = multDiffMe(l1, paramFDBigInteger);
/*  588 */     if (l2 != 0L) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  596 */       long l = 0L;
/*  597 */       int m = paramFDBigInteger.offset - this.offset;
/*      */       
/*  599 */       int[] arrayOfInt1 = paramFDBigInteger.data;
/*  600 */       int[] arrayOfInt2 = this.data;
/*  601 */       while (l == 0L) {
/*  602 */         byte b; int n; for (b = 0, n = m; n < this.nWords; b++, n++) {
/*  603 */           l += (arrayOfInt2[n] & 0xFFFFFFFFL) + (arrayOfInt1[b] & 0xFFFFFFFFL);
/*  604 */           arrayOfInt2[n] = (int)l;
/*  605 */           l >>>= 32L;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  615 */         assert l == 0L || l == 1L : l;
/*  616 */         l1--;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  622 */     int k = multAndCarryBy10(this.data, this.nWords, this.data);
/*  623 */     assert k == 0 : k;
/*  624 */     trimLeadingZeros();
/*  625 */     return (int)l1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDBigInteger multBy10() {
/*  654 */     if (this.nWords == 0) {
/*  655 */       return this;
/*      */     }
/*  657 */     if (this.isImmutable) {
/*  658 */       int[] arrayOfInt = new int[this.nWords + 1];
/*  659 */       arrayOfInt[this.nWords] = multAndCarryBy10(this.data, this.nWords, arrayOfInt);
/*  660 */       return new FDBigInteger(arrayOfInt, this.offset);
/*      */     } 
/*  662 */     int i = multAndCarryBy10(this.data, this.nWords, this.data);
/*  663 */     if (i != 0) {
/*  664 */       if (this.nWords == this.data.length) {
/*  665 */         if (this.data[0] == 0) {
/*  666 */           System.arraycopy(this.data, 1, this.data, 0, --this.nWords);
/*  667 */           this.offset++;
/*      */         } else {
/*  669 */           this.data = Arrays.copyOf(this.data, this.data.length + 1);
/*      */         } 
/*      */       }
/*  672 */       this.data[this.nWords++] = i;
/*      */     } else {
/*  674 */       trimLeadingZeros();
/*      */     } 
/*  676 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDBigInteger multByPow52(int paramInt1, int paramInt2) {
/*  709 */     if (this.nWords == 0) {
/*  710 */       return this;
/*      */     }
/*  712 */     FDBigInteger fDBigInteger = this;
/*  713 */     if (paramInt1 != 0) {
/*      */       
/*  715 */       byte b = (paramInt2 != 0) ? 1 : 0;
/*  716 */       if (paramInt1 < SMALL_5_POW.length) {
/*  717 */         int[] arrayOfInt = new int[this.nWords + 1 + b];
/*  718 */         mult(this.data, this.nWords, SMALL_5_POW[paramInt1], arrayOfInt);
/*  719 */         fDBigInteger = new FDBigInteger(arrayOfInt, this.offset);
/*      */       } else {
/*  721 */         FDBigInteger fDBigInteger1 = big5pow(paramInt1);
/*  722 */         int[] arrayOfInt = new int[this.nWords + fDBigInteger1.size() + b];
/*  723 */         mult(this.data, this.nWords, fDBigInteger1.data, fDBigInteger1.nWords, arrayOfInt);
/*  724 */         fDBigInteger = new FDBigInteger(arrayOfInt, this.offset + fDBigInteger1.offset);
/*      */       } 
/*      */     } 
/*  727 */     return fDBigInteger.leftShift(paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void mult(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int[] paramArrayOfint3) {
/*  746 */     for (byte b = 0; b < paramInt1; b++) {
/*  747 */       long l1 = paramArrayOfint1[b] & 0xFFFFFFFFL;
/*  748 */       long l2 = 0L;
/*  749 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/*  750 */         l2 += (paramArrayOfint3[b + b1] & 0xFFFFFFFFL) + l1 * (paramArrayOfint2[b1] & 0xFFFFFFFFL);
/*  751 */         paramArrayOfint3[b + b1] = (int)l2;
/*  752 */         l2 >>>= 32L;
/*      */       } 
/*  754 */       paramArrayOfint3[b + paramInt2] = (int)l2;
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
/*      */   public FDBigInteger leftInplaceSub(FDBigInteger paramFDBigInteger) {
/*      */     FDBigInteger fDBigInteger;
/*  782 */     assert size() >= paramFDBigInteger.size() : "result should be positive";
/*      */     
/*  784 */     if (this.isImmutable) {
/*  785 */       fDBigInteger = new FDBigInteger((int[])this.data.clone(), this.offset);
/*      */     } else {
/*  787 */       fDBigInteger = this;
/*      */     } 
/*  789 */     int i = paramFDBigInteger.offset - fDBigInteger.offset;
/*  790 */     int[] arrayOfInt1 = paramFDBigInteger.data;
/*  791 */     int[] arrayOfInt2 = fDBigInteger.data;
/*  792 */     int j = paramFDBigInteger.nWords;
/*  793 */     int k = fDBigInteger.nWords;
/*  794 */     if (i < 0) {
/*      */       
/*  796 */       int n = k - i;
/*  797 */       if (n < arrayOfInt2.length) {
/*  798 */         System.arraycopy(arrayOfInt2, 0, arrayOfInt2, -i, k);
/*  799 */         Arrays.fill(arrayOfInt2, 0, -i, 0);
/*      */       } else {
/*  801 */         int[] arrayOfInt = new int[n];
/*  802 */         System.arraycopy(arrayOfInt2, 0, arrayOfInt, -i, k);
/*  803 */         fDBigInteger.data = arrayOfInt2 = arrayOfInt;
/*      */       } 
/*  805 */       fDBigInteger.offset = paramFDBigInteger.offset;
/*  806 */       fDBigInteger.nWords = k = n;
/*  807 */       i = 0;
/*      */     } 
/*  809 */     long l = 0L;
/*  810 */     int m = i;
/*  811 */     for (byte b = 0; b < j && m < k; b++, m++) {
/*  812 */       long l1 = (arrayOfInt2[m] & 0xFFFFFFFFL) - (arrayOfInt1[b] & 0xFFFFFFFFL) + l;
/*  813 */       arrayOfInt2[m] = (int)l1;
/*  814 */       l = l1 >> 32L;
/*      */     } 
/*  816 */     for (; l != 0L && m < k; m++) {
/*  817 */       long l1 = (arrayOfInt2[m] & 0xFFFFFFFFL) + l;
/*  818 */       arrayOfInt2[m] = (int)l1;
/*  819 */       l = l1 >> 32L;
/*      */     } 
/*  821 */     assert l == 0L : l;
/*      */     
/*  823 */     fDBigInteger.trimLeadingZeros();
/*  824 */     return fDBigInteger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDBigInteger rightInplaceSub(FDBigInteger paramFDBigInteger) {
/*  851 */     assert size() >= paramFDBigInteger.size() : "result should be positive";
/*  852 */     FDBigInteger fDBigInteger = this;
/*  853 */     if (paramFDBigInteger.isImmutable) {
/*  854 */       paramFDBigInteger = new FDBigInteger((int[])paramFDBigInteger.data.clone(), paramFDBigInteger.offset);
/*      */     }
/*  856 */     int i = fDBigInteger.offset - paramFDBigInteger.offset;
/*  857 */     int[] arrayOfInt1 = paramFDBigInteger.data;
/*  858 */     int[] arrayOfInt2 = fDBigInteger.data;
/*  859 */     int j = paramFDBigInteger.nWords;
/*  860 */     int k = fDBigInteger.nWords;
/*  861 */     if (i < 0) {
/*  862 */       int m = k;
/*  863 */       if (m < arrayOfInt1.length) {
/*  864 */         System.arraycopy(arrayOfInt1, 0, arrayOfInt1, -i, j);
/*  865 */         Arrays.fill(arrayOfInt1, 0, -i, 0);
/*      */       } else {
/*  867 */         int[] arrayOfInt = new int[m];
/*  868 */         System.arraycopy(arrayOfInt1, 0, arrayOfInt, -i, j);
/*  869 */         paramFDBigInteger.data = arrayOfInt1 = arrayOfInt;
/*      */       } 
/*  871 */       paramFDBigInteger.offset = fDBigInteger.offset;
/*  872 */       j -= i;
/*  873 */       i = 0;
/*      */     } else {
/*  875 */       int m = k + i;
/*  876 */       if (m >= arrayOfInt1.length) {
/*  877 */         paramFDBigInteger.data = arrayOfInt1 = Arrays.copyOf(arrayOfInt1, m);
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
/*  888 */     byte b1 = 0;
/*  889 */     long l = 0L;
/*  890 */     for (; b1 < i; b1++) {
/*  891 */       long l1 = 0L - (arrayOfInt1[b1] & 0xFFFFFFFFL) + l;
/*  892 */       arrayOfInt1[b1] = (int)l1;
/*  893 */       l = l1 >> 32L;
/*      */     } 
/*      */     
/*  896 */     for (byte b2 = 0; b2 < k; b1++, b2++) {
/*      */       
/*  898 */       long l1 = (arrayOfInt2[b2] & 0xFFFFFFFFL) - (arrayOfInt1[b1] & 0xFFFFFFFFL) + l;
/*  899 */       arrayOfInt1[b1] = (int)l1;
/*  900 */       l = l1 >> 32L;
/*      */     } 
/*  902 */     assert l == 0L : l;
/*      */     
/*  904 */     paramFDBigInteger.nWords = b1;
/*  905 */     paramFDBigInteger.trimLeadingZeros();
/*  906 */     return paramFDBigInteger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkZeroTail(int[] paramArrayOfint, int paramInt) {
/*  923 */     while (paramInt > 0) {
/*  924 */       if (paramArrayOfint[--paramInt] != 0) {
/*  925 */         return 1;
/*      */       }
/*      */     } 
/*  928 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int cmp(FDBigInteger paramFDBigInteger) {
/*  948 */     int i = this.nWords + this.offset;
/*  949 */     int j = paramFDBigInteger.nWords + paramFDBigInteger.offset;
/*  950 */     if (i > j)
/*  951 */       return 1; 
/*  952 */     if (i < j) {
/*  953 */       return -1;
/*      */     }
/*  955 */     int k = this.nWords;
/*  956 */     int m = paramFDBigInteger.nWords;
/*  957 */     while (k > 0 && m > 0) {
/*  958 */       int n = this.data[--k];
/*  959 */       int i1 = paramFDBigInteger.data[--m];
/*  960 */       if (n != i1) {
/*  961 */         return ((n & 0xFFFFFFFFL) < (i1 & 0xFFFFFFFFL)) ? -1 : 1;
/*      */       }
/*      */     } 
/*  964 */     if (k > 0) {
/*  965 */       return checkZeroTail(this.data, k);
/*      */     }
/*  967 */     if (m > 0) {
/*  968 */       return -checkZeroTail(paramFDBigInteger.data, m);
/*      */     }
/*  970 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int cmpPow52(int paramInt1, int paramInt2) {
/*  992 */     if (paramInt1 == 0) {
/*  993 */       int i = paramInt2 >> 5;
/*  994 */       int j = paramInt2 & 0x1F;
/*  995 */       int k = this.nWords + this.offset;
/*  996 */       if (k > i + 1)
/*  997 */         return 1; 
/*  998 */       if (k < i + 1) {
/*  999 */         return -1;
/*      */       }
/* 1001 */       int m = this.data[this.nWords - 1];
/* 1002 */       int n = 1 << j;
/* 1003 */       if (m != n) {
/* 1004 */         return ((m & 0xFFFFFFFFL) < (n & 0xFFFFFFFFL)) ? -1 : 1;
/*      */       }
/* 1006 */       return checkZeroTail(this.data, this.nWords - 1);
/*      */     } 
/* 1008 */     return cmp(big5pow(paramInt1).leftShift(paramInt2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int addAndCmp(FDBigInteger paramFDBigInteger1, FDBigInteger paramFDBigInteger2) {
/*      */     FDBigInteger fDBigInteger1, fDBigInteger2;
/* 1029 */     int k, m, i = paramFDBigInteger1.size();
/* 1030 */     int j = paramFDBigInteger2.size();
/*      */ 
/*      */     
/* 1033 */     if (i >= j) {
/* 1034 */       fDBigInteger1 = paramFDBigInteger1;
/* 1035 */       fDBigInteger2 = paramFDBigInteger2;
/* 1036 */       k = i;
/* 1037 */       m = j;
/*      */     } else {
/* 1039 */       fDBigInteger1 = paramFDBigInteger2;
/* 1040 */       fDBigInteger2 = paramFDBigInteger1;
/* 1041 */       k = j;
/* 1042 */       m = i;
/*      */     } 
/* 1044 */     int n = size();
/* 1045 */     if (k == 0) {
/* 1046 */       return (n == 0) ? 0 : 1;
/*      */     }
/* 1048 */     if (m == 0) {
/* 1049 */       return cmp(fDBigInteger1);
/*      */     }
/* 1051 */     if (k > n) {
/* 1052 */       return -1;
/*      */     }
/* 1054 */     if (k + 1 < n) {
/* 1055 */       return 1;
/*      */     }
/* 1057 */     long l = fDBigInteger1.data[fDBigInteger1.nWords - 1] & 0xFFFFFFFFL;
/* 1058 */     if (m == k) {
/* 1059 */       l += fDBigInteger2.data[fDBigInteger2.nWords - 1] & 0xFFFFFFFFL;
/*      */     }
/* 1061 */     if (l >>> 32L == 0L) {
/* 1062 */       if (l + 1L >>> 32L == 0L) {
/*      */         
/* 1064 */         if (k < n) {
/* 1065 */           return 1;
/*      */         }
/*      */         
/* 1068 */         long l1 = this.data[this.nWords - 1] & 0xFFFFFFFFL;
/* 1069 */         if (l1 < l) {
/* 1070 */           return -1;
/*      */         }
/* 1072 */         if (l1 > l + 1L) {
/* 1073 */           return 1;
/*      */         }
/*      */       } 
/*      */     } else {
/* 1077 */       if (k + 1 > n) {
/* 1078 */         return -1;
/*      */       }
/*      */       
/* 1081 */       l >>>= 32L;
/* 1082 */       long l1 = this.data[this.nWords - 1] & 0xFFFFFFFFL;
/* 1083 */       if (l1 < l) {
/* 1084 */         return -1;
/*      */       }
/* 1086 */       if (l1 > l + 1L) {
/* 1087 */         return 1;
/*      */       }
/*      */     } 
/* 1090 */     return cmp(fDBigInteger1.add(fDBigInteger2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeImmutable() {
/* 1101 */     this.isImmutable = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FDBigInteger mult(int paramInt) {
/* 1122 */     if (this.nWords == 0) {
/* 1123 */       return this;
/*      */     }
/* 1125 */     int[] arrayOfInt = new int[this.nWords + 1];
/* 1126 */     mult(this.data, this.nWords, paramInt, arrayOfInt);
/* 1127 */     return new FDBigInteger(arrayOfInt, this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FDBigInteger mult(FDBigInteger paramFDBigInteger) {
/* 1154 */     if (this.nWords == 0) {
/* 1155 */       return this;
/*      */     }
/* 1157 */     if (size() == 1) {
/* 1158 */       return paramFDBigInteger.mult(this.data[0]);
/*      */     }
/* 1160 */     if (paramFDBigInteger.nWords == 0) {
/* 1161 */       return paramFDBigInteger;
/*      */     }
/* 1163 */     if (paramFDBigInteger.size() == 1) {
/* 1164 */       return mult(paramFDBigInteger.data[0]);
/*      */     }
/* 1166 */     int[] arrayOfInt = new int[this.nWords + paramFDBigInteger.nWords];
/* 1167 */     mult(this.data, this.nWords, paramFDBigInteger.data, paramFDBigInteger.nWords, arrayOfInt);
/* 1168 */     return new FDBigInteger(arrayOfInt, this.offset + paramFDBigInteger.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FDBigInteger add(FDBigInteger paramFDBigInteger) {
/*      */     FDBigInteger fDBigInteger1, fDBigInteger2;
/* 1184 */     int i, j, k = size();
/* 1185 */     int m = paramFDBigInteger.size();
/* 1186 */     if (k >= m) {
/* 1187 */       fDBigInteger1 = this;
/* 1188 */       i = k;
/* 1189 */       fDBigInteger2 = paramFDBigInteger;
/* 1190 */       j = m;
/*      */     } else {
/* 1192 */       fDBigInteger1 = paramFDBigInteger;
/* 1193 */       i = m;
/* 1194 */       fDBigInteger2 = this;
/* 1195 */       j = k;
/*      */     } 
/* 1197 */     int[] arrayOfInt = new int[i + 1];
/* 1198 */     byte b = 0;
/* 1199 */     long l = 0L;
/* 1200 */     for (; b < j; b++) {
/* 1201 */       l += ((b < fDBigInteger1.offset) ? 0L : (fDBigInteger1.data[b - fDBigInteger1.offset] & 0xFFFFFFFFL)) + ((b < fDBigInteger2.offset) ? 0L : (fDBigInteger2.data[b - fDBigInteger2.offset] & 0xFFFFFFFFL));
/*      */       
/* 1203 */       arrayOfInt[b] = (int)l;
/* 1204 */       l >>= 32L;
/*      */     } 
/* 1206 */     for (; b < i; b++) {
/* 1207 */       l += (b < fDBigInteger1.offset) ? 0L : (fDBigInteger1.data[b - fDBigInteger1.offset] & 0xFFFFFFFFL);
/* 1208 */       arrayOfInt[b] = (int)l;
/* 1209 */       l >>= 32L;
/*      */     } 
/* 1211 */     arrayOfInt[i] = (int)l;
/* 1212 */     return new FDBigInteger(arrayOfInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void multAddMe(int paramInt1, int paramInt2) {
/* 1234 */     long l1 = paramInt1 & 0xFFFFFFFFL;
/*      */     
/* 1236 */     long l2 = l1 * (this.data[0] & 0xFFFFFFFFL) + (paramInt2 & 0xFFFFFFFFL);
/* 1237 */     this.data[0] = (int)l2;
/* 1238 */     l2 >>>= 32L;
/* 1239 */     for (byte b = 1; b < this.nWords; b++) {
/* 1240 */       l2 += l1 * (this.data[b] & 0xFFFFFFFFL);
/* 1241 */       this.data[b] = (int)l2;
/* 1242 */       l2 >>>= 32L;
/*      */     } 
/* 1244 */     if (l2 != 0L) {
/* 1245 */       this.data[this.nWords++] = (int)l2;
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
/*      */   private long multDiffMe(long paramLong, FDBigInteger paramFDBigInteger) {
/* 1290 */     long l = 0L;
/* 1291 */     if (paramLong != 0L) {
/* 1292 */       int i = paramFDBigInteger.offset - this.offset;
/* 1293 */       if (i >= 0) {
/* 1294 */         int[] arrayOfInt1 = paramFDBigInteger.data;
/* 1295 */         int[] arrayOfInt2 = this.data; byte b; int j;
/* 1296 */         for (b = 0, j = i; b < paramFDBigInteger.nWords; b++, j++) {
/* 1297 */           l += (arrayOfInt2[j] & 0xFFFFFFFFL) - paramLong * (arrayOfInt1[b] & 0xFFFFFFFFL);
/* 1298 */           arrayOfInt2[j] = (int)l;
/* 1299 */           l >>= 32L;
/*      */         } 
/*      */       } else {
/* 1302 */         i = -i;
/* 1303 */         int[] arrayOfInt1 = new int[this.nWords + i];
/* 1304 */         byte b1 = 0;
/* 1305 */         byte b2 = 0;
/* 1306 */         int[] arrayOfInt2 = paramFDBigInteger.data;
/* 1307 */         for (; b2 < i && b1 < paramFDBigInteger.nWords; b1++, b2++) {
/* 1308 */           l -= paramLong * (arrayOfInt2[b1] & 0xFFFFFFFFL);
/* 1309 */           arrayOfInt1[b2] = (int)l;
/* 1310 */           l >>= 32L;
/*      */         } 
/* 1312 */         byte b3 = 0;
/* 1313 */         int[] arrayOfInt3 = this.data;
/* 1314 */         for (; b1 < paramFDBigInteger.nWords; b1++, b3++, b2++) {
/* 1315 */           l += (arrayOfInt3[b3] & 0xFFFFFFFFL) - paramLong * (arrayOfInt2[b1] & 0xFFFFFFFFL);
/* 1316 */           arrayOfInt1[b2] = (int)l;
/* 1317 */           l >>= 32L;
/*      */         } 
/* 1319 */         this.nWords += i;
/* 1320 */         this.offset -= i;
/* 1321 */         this.data = arrayOfInt1;
/*      */       } 
/*      */     } 
/* 1324 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int multAndCarryBy10(int[] paramArrayOfint1, int paramInt, int[] paramArrayOfint2) {
/* 1344 */     long l = 0L;
/* 1345 */     for (byte b = 0; b < paramInt; b++) {
/* 1346 */       long l1 = (paramArrayOfint1[b] & 0xFFFFFFFFL) * 10L + l;
/* 1347 */       paramArrayOfint2[b] = (int)l1;
/* 1348 */       l = l1 >>> 32L;
/*      */     } 
/* 1350 */     return (int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void mult(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2) {
/* 1368 */     long l1 = paramInt2 & 0xFFFFFFFFL;
/* 1369 */     long l2 = 0L;
/* 1370 */     for (byte b = 0; b < paramInt1; b++) {
/* 1371 */       long l = (paramArrayOfint1[b] & 0xFFFFFFFFL) * l1 + l2;
/* 1372 */       paramArrayOfint2[b] = (int)l;
/* 1373 */       l2 = l >>> 32L;
/*      */     } 
/* 1375 */     paramArrayOfint2[paramInt1] = (int)l2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void mult(int[] paramArrayOfint1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint2) {
/* 1395 */     long l1 = paramInt2 & 0xFFFFFFFFL;
/* 1396 */     long l2 = 0L; byte b;
/* 1397 */     for (b = 0; b < paramInt1; b++) {
/* 1398 */       long l = l1 * (paramArrayOfint1[b] & 0xFFFFFFFFL) + l2;
/* 1399 */       paramArrayOfint2[b] = (int)l;
/* 1400 */       l2 = l >>> 32L;
/*      */     } 
/* 1402 */     paramArrayOfint2[paramInt1] = (int)l2;
/* 1403 */     l1 = paramInt3 & 0xFFFFFFFFL;
/* 1404 */     l2 = 0L;
/* 1405 */     for (b = 0; b < paramInt1; b++) {
/* 1406 */       long l = (paramArrayOfint2[b + 1] & 0xFFFFFFFFL) + l1 * (paramArrayOfint1[b] & 0xFFFFFFFFL) + l2;
/* 1407 */       paramArrayOfint2[b + 1] = (int)l;
/* 1408 */       l2 = l >>> 32L;
/*      */     } 
/* 1410 */     paramArrayOfint2[paramInt1 + 1] = (int)l2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FDBigInteger big5pow(int paramInt) {
/* 1421 */     assert paramInt >= 0 : paramInt;
/* 1422 */     if (paramInt < 340) {
/* 1423 */       return POW_5_CACHE[paramInt];
/*      */     }
/* 1425 */     return big5powRec(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FDBigInteger big5powRec(int paramInt) {
/* 1436 */     if (paramInt < 340) {
/* 1437 */       return POW_5_CACHE[paramInt];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1446 */     int i = paramInt >> 1;
/* 1447 */     int j = paramInt - i;
/* 1448 */     FDBigInteger fDBigInteger = big5powRec(i);
/* 1449 */     if (j < SMALL_5_POW.length) {
/* 1450 */       return fDBigInteger.mult(SMALL_5_POW[j]);
/*      */     }
/* 1452 */     return fDBigInteger.mult(big5powRec(j));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toHexString() {
/* 1463 */     if (this.nWords == 0) {
/* 1464 */       return "0";
/*      */     }
/* 1466 */     StringBuilder stringBuilder = new StringBuilder((this.nWords + this.offset) * 8); int i;
/* 1467 */     for (i = this.nWords - 1; i >= 0; i--) {
/* 1468 */       String str = Integer.toHexString(this.data[i]);
/* 1469 */       for (int j = str.length(); j < 8; j++) {
/* 1470 */         stringBuilder.append('0');
/*      */       }
/* 1472 */       stringBuilder.append(str);
/*      */     } 
/* 1474 */     for (i = this.offset; i > 0; i--) {
/* 1475 */       stringBuilder.append("00000000");
/*      */     }
/* 1477 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger toBigInteger() {
/* 1487 */     byte[] arrayOfByte = new byte[this.nWords * 4 + 1];
/* 1488 */     for (byte b = 0; b < this.nWords; b++) {
/* 1489 */       int i = this.data[b];
/* 1490 */       arrayOfByte[arrayOfByte.length - 4 * b - 1] = (byte)i;
/* 1491 */       arrayOfByte[arrayOfByte.length - 4 * b - 2] = (byte)(i >> 8);
/* 1492 */       arrayOfByte[arrayOfByte.length - 4 * b - 3] = (byte)(i >> 16);
/* 1493 */       arrayOfByte[arrayOfByte.length - 4 * b - 4] = (byte)(i >> 24);
/*      */     } 
/* 1495 */     return (new BigInteger(arrayOfByte)).shiftLeft(this.offset * 32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1506 */     return toBigInteger().toString();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/FDBigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */