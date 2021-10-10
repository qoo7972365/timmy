/*      */ package java.math;
/*      */ 
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
/*      */ class MutableBigInteger
/*      */ {
/*      */   int[] value;
/*      */   int intLen;
/*   68 */   int offset = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   76 */   static final MutableBigInteger ONE = new MutableBigInteger(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int KNUTH_POW2_THRESH_LEN = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int KNUTH_POW2_THRESH_ZEROS = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger() {
/*  103 */     this.value = new int[1];
/*  104 */     this.intLen = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger(int paramInt) {
/*  112 */     this.value = new int[1];
/*  113 */     this.intLen = 1;
/*  114 */     this.value[0] = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger(int[] paramArrayOfint) {
/*  122 */     this.value = paramArrayOfint;
/*  123 */     this.intLen = paramArrayOfint.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger(BigInteger paramBigInteger) {
/*  131 */     this.intLen = paramBigInteger.mag.length;
/*  132 */     this.value = Arrays.copyOf(paramBigInteger.mag, this.intLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger(MutableBigInteger paramMutableBigInteger) {
/*  140 */     this.intLen = paramMutableBigInteger.intLen;
/*  141 */     this.value = Arrays.copyOfRange(paramMutableBigInteger.value, paramMutableBigInteger.offset, paramMutableBigInteger.offset + this.intLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ones(int paramInt) {
/*  151 */     if (paramInt > this.value.length)
/*  152 */       this.value = new int[paramInt]; 
/*  153 */     Arrays.fill(this.value, -1);
/*  154 */     this.offset = 0;
/*  155 */     this.intLen = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] getMagnitudeArray() {
/*  163 */     if (this.offset > 0 || this.value.length != this.intLen)
/*  164 */       return Arrays.copyOfRange(this.value, this.offset, this.offset + this.intLen); 
/*  165 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long toLong() {
/*  173 */     assert this.intLen <= 2 : "this MutableBigInteger exceeds the range of long";
/*  174 */     if (this.intLen == 0)
/*  175 */       return 0L; 
/*  176 */     long l = this.value[this.offset] & 0xFFFFFFFFL;
/*  177 */     return (this.intLen == 2) ? (l << 32L | this.value[this.offset + 1] & 0xFFFFFFFFL) : l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   BigInteger toBigInteger(int paramInt) {
/*  184 */     if (this.intLen == 0 || paramInt == 0)
/*  185 */       return BigInteger.ZERO; 
/*  186 */     return new BigInteger(getMagnitudeArray(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   BigInteger toBigInteger() {
/*  193 */     normalize();
/*  194 */     return toBigInteger(isZero() ? 0 : 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   BigDecimal toBigDecimal(int paramInt1, int paramInt2) {
/*  202 */     if (this.intLen == 0 || paramInt1 == 0)
/*  203 */       return BigDecimal.zeroValueOf(paramInt2); 
/*  204 */     int[] arrayOfInt = getMagnitudeArray();
/*  205 */     int i = arrayOfInt.length;
/*  206 */     int j = arrayOfInt[0];
/*      */ 
/*      */     
/*  209 */     if (i > 2 || (j < 0 && i == 2))
/*  210 */       return new BigDecimal(new BigInteger(arrayOfInt, paramInt1), Long.MIN_VALUE, paramInt2, 0); 
/*  211 */     long l = (i == 2) ? (arrayOfInt[1] & 0xFFFFFFFFL | (j & 0xFFFFFFFFL) << 32L) : (j & 0xFFFFFFFFL);
/*      */ 
/*      */     
/*  214 */     return BigDecimal.valueOf((paramInt1 == -1) ? -l : l, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long toCompactValue(int paramInt) {
/*  223 */     if (this.intLen == 0 || paramInt == 0)
/*  224 */       return 0L; 
/*  225 */     int[] arrayOfInt = getMagnitudeArray();
/*  226 */     int i = arrayOfInt.length;
/*  227 */     int j = arrayOfInt[0];
/*      */ 
/*      */     
/*  230 */     if (i > 2 || (j < 0 && i == 2))
/*  231 */       return Long.MIN_VALUE; 
/*  232 */     long l = (i == 2) ? (arrayOfInt[1] & 0xFFFFFFFFL | (j & 0xFFFFFFFFL) << 32L) : (j & 0xFFFFFFFFL);
/*      */ 
/*      */     
/*  235 */     return (paramInt == -1) ? -l : l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void clear() {
/*  242 */     this.offset = this.intLen = 0; byte b; int i;
/*  243 */     for (b = 0, i = this.value.length; b < i; b++) {
/*  244 */       this.value[b] = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void reset() {
/*  251 */     this.offset = this.intLen = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compare(MutableBigInteger paramMutableBigInteger) {
/*  260 */     int i = paramMutableBigInteger.intLen;
/*  261 */     if (this.intLen < i)
/*  262 */       return -1; 
/*  263 */     if (this.intLen > i) {
/*  264 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*  268 */     int[] arrayOfInt = paramMutableBigInteger.value;
/*  269 */     for (int j = this.offset, k = paramMutableBigInteger.offset; j < this.intLen + this.offset; j++, k++) {
/*  270 */       int m = this.value[j] + Integer.MIN_VALUE;
/*  271 */       int n = arrayOfInt[k] + Integer.MIN_VALUE;
/*  272 */       if (m < n)
/*  273 */         return -1; 
/*  274 */       if (m > n)
/*  275 */         return 1; 
/*      */     } 
/*  277 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int compareShifted(MutableBigInteger paramMutableBigInteger, int paramInt) {
/*  285 */     int i = paramMutableBigInteger.intLen;
/*  286 */     int j = this.intLen - paramInt;
/*  287 */     if (j < i)
/*  288 */       return -1; 
/*  289 */     if (j > i) {
/*  290 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*  294 */     int[] arrayOfInt = paramMutableBigInteger.value;
/*  295 */     for (int k = this.offset, m = paramMutableBigInteger.offset; k < j + this.offset; k++, m++) {
/*  296 */       int n = this.value[k] + Integer.MIN_VALUE;
/*  297 */       int i1 = arrayOfInt[m] + Integer.MIN_VALUE;
/*  298 */       if (n < i1)
/*  299 */         return -1; 
/*  300 */       if (n > i1)
/*  301 */         return 1; 
/*      */     } 
/*  303 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compareHalf(MutableBigInteger paramMutableBigInteger) {
/*  313 */     int i = paramMutableBigInteger.intLen;
/*  314 */     int j = this.intLen;
/*  315 */     if (j <= 0)
/*  316 */       return (i <= 0) ? 0 : -1; 
/*  317 */     if (j > i)
/*  318 */       return 1; 
/*  319 */     if (j < i - 1)
/*  320 */       return -1; 
/*  321 */     int[] arrayOfInt1 = paramMutableBigInteger.value;
/*  322 */     byte b1 = 0;
/*  323 */     int k = 0;
/*      */     
/*  325 */     if (j != i) {
/*  326 */       if (arrayOfInt1[b1] == 1) {
/*  327 */         b1++;
/*  328 */         k = Integer.MIN_VALUE;
/*      */       } else {
/*  330 */         return -1;
/*      */       } 
/*      */     }
/*      */     
/*  334 */     int[] arrayOfInt2 = this.value; int m; byte b2;
/*  335 */     for (m = this.offset, b2 = b1; m < j + this.offset; ) {
/*  336 */       int n = arrayOfInt1[b2++];
/*  337 */       long l1 = ((n >>> 1) + k) & 0xFFFFFFFFL;
/*  338 */       long l2 = arrayOfInt2[m++] & 0xFFFFFFFFL;
/*  339 */       if (l2 != l1)
/*  340 */         return (l2 < l1) ? -1 : 1; 
/*  341 */       k = (n & 0x1) << 31;
/*      */     } 
/*  343 */     return (k == 0) ? 0 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int getLowestSetBit() {
/*  351 */     if (this.intLen == 0)
/*  352 */       return -1; 
/*      */     int i;
/*  354 */     for (i = this.intLen - 1; i > 0 && this.value[i + this.offset] == 0; i--);
/*      */     
/*  356 */     int j = this.value[i + this.offset];
/*  357 */     if (j == 0)
/*  358 */       return -1; 
/*  359 */     return (this.intLen - 1 - i << 5) + Integer.numberOfTrailingZeros(j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int getInt(int paramInt) {
/*  368 */     return this.value[this.offset + paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final long getLong(int paramInt) {
/*  377 */     return this.value[this.offset + paramInt] & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void normalize() {
/*  386 */     if (this.intLen == 0) {
/*  387 */       this.offset = 0;
/*      */       
/*      */       return;
/*      */     } 
/*  391 */     int i = this.offset;
/*  392 */     if (this.value[i] != 0) {
/*      */       return;
/*      */     }
/*  395 */     int j = i + this.intLen;
/*      */     do {
/*  397 */       i++;
/*  398 */     } while (i < j && this.value[i] == 0);
/*      */     
/*  400 */     int k = i - this.offset;
/*  401 */     this.intLen -= k;
/*  402 */     this.offset = (this.intLen == 0) ? 0 : (this.offset + k);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void ensureCapacity(int paramInt) {
/*  410 */     if (this.value.length < paramInt) {
/*  411 */       this.value = new int[paramInt];
/*  412 */       this.offset = 0;
/*  413 */       this.intLen = paramInt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int[] toIntArray() {
/*  422 */     int[] arrayOfInt = new int[this.intLen];
/*  423 */     for (byte b = 0; b < this.intLen; b++)
/*  424 */       arrayOfInt[b] = this.value[this.offset + b]; 
/*  425 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setInt(int paramInt1, int paramInt2) {
/*  434 */     this.value[this.offset + paramInt1] = paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setValue(int[] paramArrayOfint, int paramInt) {
/*  442 */     this.value = paramArrayOfint;
/*  443 */     this.intLen = paramInt;
/*  444 */     this.offset = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void copyValue(MutableBigInteger paramMutableBigInteger) {
/*  452 */     int i = paramMutableBigInteger.intLen;
/*  453 */     if (this.value.length < i)
/*  454 */       this.value = new int[i]; 
/*  455 */     System.arraycopy(paramMutableBigInteger.value, paramMutableBigInteger.offset, this.value, 0, i);
/*  456 */     this.intLen = i;
/*  457 */     this.offset = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void copyValue(int[] paramArrayOfint) {
/*  465 */     int i = paramArrayOfint.length;
/*  466 */     if (this.value.length < i)
/*  467 */       this.value = new int[i]; 
/*  468 */     System.arraycopy(paramArrayOfint, 0, this.value, 0, i);
/*  469 */     this.intLen = i;
/*  470 */     this.offset = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isOne() {
/*  477 */     return (this.intLen == 1 && this.value[this.offset] == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isZero() {
/*  484 */     return (this.intLen == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isEven() {
/*  491 */     return (this.intLen == 0 || (this.value[this.offset + this.intLen - 1] & 0x1) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isOdd() {
/*  498 */     return isZero() ? false : (((this.value[this.offset + this.intLen - 1] & 0x1) == 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isNormal() {
/*  507 */     if (this.intLen + this.offset > this.value.length)
/*  508 */       return false; 
/*  509 */     if (this.intLen == 0)
/*  510 */       return true; 
/*  511 */     return (this.value[this.offset] != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  518 */     BigInteger bigInteger = toBigInteger(1);
/*  519 */     return bigInteger.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void safeRightShift(int paramInt) {
/*  526 */     if (paramInt / 32 >= this.intLen) {
/*  527 */       reset();
/*      */     } else {
/*  529 */       rightShift(paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void rightShift(int paramInt) {
/*  538 */     if (this.intLen == 0)
/*      */       return; 
/*  540 */     int i = paramInt >>> 5;
/*  541 */     int j = paramInt & 0x1F;
/*  542 */     this.intLen -= i;
/*  543 */     if (j == 0)
/*      */       return; 
/*  545 */     int k = BigInteger.bitLengthForInt(this.value[this.offset]);
/*  546 */     if (j >= k) {
/*  547 */       primitiveLeftShift(32 - j);
/*  548 */       this.intLen--;
/*      */     } else {
/*  550 */       primitiveRightShift(j);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void safeLeftShift(int paramInt) {
/*  558 */     if (paramInt > 0) {
/*  559 */       leftShift(paramInt);
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
/*      */   void leftShift(int paramInt) {
/*  573 */     if (this.intLen == 0)
/*      */       return; 
/*  575 */     int i = paramInt >>> 5;
/*  576 */     int j = paramInt & 0x1F;
/*  577 */     int k = BigInteger.bitLengthForInt(this.value[this.offset]);
/*      */ 
/*      */     
/*  580 */     if (paramInt <= 32 - k) {
/*  581 */       primitiveLeftShift(j);
/*      */       
/*      */       return;
/*      */     } 
/*  585 */     int m = this.intLen + i + 1;
/*  586 */     if (j <= 32 - k)
/*  587 */       m--; 
/*  588 */     if (this.value.length < m) {
/*      */       
/*  590 */       int[] arrayOfInt = new int[m];
/*  591 */       for (byte b = 0; b < this.intLen; b++)
/*  592 */         arrayOfInt[b] = this.value[this.offset + b]; 
/*  593 */       setValue(arrayOfInt, m);
/*  594 */     } else if (this.value.length - this.offset >= m) {
/*      */       
/*  596 */       for (byte b = 0; b < m - this.intLen; b++)
/*  597 */         this.value[this.offset + this.intLen + b] = 0; 
/*      */     } else {
/*      */       int n;
/*  600 */       for (n = 0; n < this.intLen; n++)
/*  601 */         this.value[n] = this.value[this.offset + n]; 
/*  602 */       for (n = this.intLen; n < m; n++)
/*  603 */         this.value[n] = 0; 
/*  604 */       this.offset = 0;
/*      */     } 
/*  606 */     this.intLen = m;
/*  607 */     if (j == 0)
/*      */       return; 
/*  609 */     if (j <= 32 - k) {
/*  610 */       primitiveLeftShift(j);
/*      */     } else {
/*  612 */       primitiveRightShift(32 - j);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int divadd(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  621 */     long l = 0L;
/*      */     
/*  623 */     for (int i = paramArrayOfint1.length - 1; i >= 0; i--) {
/*  624 */       long l1 = (paramArrayOfint1[i] & 0xFFFFFFFFL) + (paramArrayOfint2[i + paramInt] & 0xFFFFFFFFL) + l;
/*      */       
/*  626 */       paramArrayOfint2[i + paramInt] = (int)l1;
/*  627 */       l = l1 >>> 32L;
/*      */     } 
/*  629 */     return (int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int mulsub(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3) {
/*  638 */     long l1 = paramInt1 & 0xFFFFFFFFL;
/*  639 */     long l2 = 0L;
/*  640 */     paramInt3 += paramInt2;
/*      */     
/*  642 */     for (int i = paramInt2 - 1; i >= 0; i--) {
/*  643 */       long l3 = (paramArrayOfint2[i] & 0xFFFFFFFFL) * l1 + l2;
/*  644 */       long l4 = paramArrayOfint1[paramInt3] - l3;
/*  645 */       paramArrayOfint1[paramInt3--] = (int)l4;
/*  646 */       l2 = (l3 >>> 32L) + (((l4 & 0xFFFFFFFFL) > (((int)l3 ^ 0xFFFFFFFF) & 0xFFFFFFFFL)) ? 1L : 0L);
/*      */     } 
/*      */ 
/*      */     
/*  650 */     return (int)l2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int mulsubBorrow(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3) {
/*  658 */     long l1 = paramInt1 & 0xFFFFFFFFL;
/*  659 */     long l2 = 0L;
/*  660 */     paramInt3 += paramInt2;
/*  661 */     for (int i = paramInt2 - 1; i >= 0; i--) {
/*  662 */       long l3 = (paramArrayOfint2[i] & 0xFFFFFFFFL) * l1 + l2;
/*  663 */       long l4 = paramArrayOfint1[paramInt3--] - l3;
/*  664 */       l2 = (l3 >>> 32L) + (((l4 & 0xFFFFFFFFL) > (((int)l3 ^ 0xFFFFFFFF) & 0xFFFFFFFFL)) ? 1L : 0L);
/*      */     } 
/*      */ 
/*      */     
/*  668 */     return (int)l2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void primitiveRightShift(int paramInt) {
/*  677 */     int[] arrayOfInt = this.value;
/*  678 */     int i = 32 - paramInt;
/*  679 */     for (int j = this.offset + this.intLen - 1, k = arrayOfInt[j]; j > this.offset; j--) {
/*  680 */       int m = k;
/*  681 */       k = arrayOfInt[j - 1];
/*  682 */       arrayOfInt[j] = k << i | m >>> paramInt;
/*      */     } 
/*  684 */     arrayOfInt[this.offset] = arrayOfInt[this.offset] >>> paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void primitiveLeftShift(int paramInt) {
/*  693 */     int[] arrayOfInt = this.value;
/*  694 */     int i = 32 - paramInt;
/*  695 */     for (int j = this.offset, k = arrayOfInt[j], m = j + this.intLen - 1; j < m; j++) {
/*  696 */       int n = k;
/*  697 */       k = arrayOfInt[j + 1];
/*  698 */       arrayOfInt[j] = n << paramInt | k >>> i;
/*      */     } 
/*  700 */     arrayOfInt[this.offset + this.intLen - 1] = arrayOfInt[this.offset + this.intLen - 1] << paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger getLower(int paramInt) {
/*  708 */     if (isZero())
/*  709 */       return BigInteger.ZERO; 
/*  710 */     if (this.intLen < paramInt) {
/*  711 */       return toBigInteger(1);
/*      */     }
/*      */     
/*  714 */     int i = paramInt;
/*  715 */     while (i > 0 && this.value[this.offset + this.intLen - i] == 0)
/*  716 */       i--; 
/*  717 */     boolean bool = (i > 0) ? true : false;
/*  718 */     return new BigInteger(Arrays.copyOfRange(this.value, this.offset + this.intLen - i, this.offset + this.intLen), bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void keepLower(int paramInt) {
/*  726 */     if (this.intLen >= paramInt) {
/*  727 */       this.offset += this.intLen - paramInt;
/*  728 */       this.intLen = paramInt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void add(MutableBigInteger paramMutableBigInteger) {
/*  738 */     int i = this.intLen;
/*  739 */     int j = paramMutableBigInteger.intLen;
/*  740 */     int k = (this.intLen > paramMutableBigInteger.intLen) ? this.intLen : paramMutableBigInteger.intLen;
/*  741 */     int[] arrayOfInt = (this.value.length < k) ? new int[k] : this.value;
/*      */     
/*  743 */     int m = arrayOfInt.length - 1;
/*      */     
/*  745 */     long l = 0L;
/*      */ 
/*      */     
/*  748 */     while (i > 0 && j > 0) {
/*  749 */       i--; j--;
/*  750 */       long l1 = (this.value[i + this.offset] & 0xFFFFFFFFL) + (paramMutableBigInteger.value[j + paramMutableBigInteger.offset] & 0xFFFFFFFFL) + l;
/*      */       
/*  752 */       arrayOfInt[m--] = (int)l1;
/*  753 */       l = l1 >>> 32L;
/*      */     } 
/*      */ 
/*      */     
/*  757 */     while (i > 0) {
/*  758 */       i--;
/*  759 */       if (l == 0L && arrayOfInt == this.value && m == i + this.offset)
/*      */         return; 
/*  761 */       long l1 = (this.value[i + this.offset] & 0xFFFFFFFFL) + l;
/*  762 */       arrayOfInt[m--] = (int)l1;
/*  763 */       l = l1 >>> 32L;
/*      */     } 
/*  765 */     while (j > 0) {
/*  766 */       j--;
/*  767 */       long l1 = (paramMutableBigInteger.value[j + paramMutableBigInteger.offset] & 0xFFFFFFFFL) + l;
/*  768 */       arrayOfInt[m--] = (int)l1;
/*  769 */       l = l1 >>> 32L;
/*      */     } 
/*      */     
/*  772 */     if (l > 0L) {
/*  773 */       k++;
/*  774 */       if (arrayOfInt.length < k) {
/*  775 */         int[] arrayOfInt1 = new int[k];
/*      */ 
/*      */         
/*  778 */         System.arraycopy(arrayOfInt, 0, arrayOfInt1, 1, arrayOfInt.length);
/*  779 */         arrayOfInt1[0] = 1;
/*  780 */         arrayOfInt = arrayOfInt1;
/*      */       } else {
/*  782 */         arrayOfInt[m--] = 1;
/*      */       } 
/*      */     } 
/*      */     
/*  786 */     this.value = arrayOfInt;
/*  787 */     this.intLen = k;
/*  788 */     this.offset = arrayOfInt.length - k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addShifted(MutableBigInteger paramMutableBigInteger, int paramInt) {
/*  797 */     if (paramMutableBigInteger.isZero()) {
/*      */       return;
/*      */     }
/*      */     
/*  801 */     int i = this.intLen;
/*  802 */     int j = paramMutableBigInteger.intLen + paramInt;
/*  803 */     int k = (this.intLen > j) ? this.intLen : j;
/*  804 */     int[] arrayOfInt = (this.value.length < k) ? new int[k] : this.value;
/*      */     
/*  806 */     int m = arrayOfInt.length - 1;
/*      */     
/*  808 */     long l = 0L;
/*      */ 
/*      */     
/*  811 */     while (i > 0 && j > 0) {
/*  812 */       i--; j--;
/*  813 */       boolean bool = (j + paramMutableBigInteger.offset < paramMutableBigInteger.value.length) ? paramMutableBigInteger.value[j + paramMutableBigInteger.offset] : false;
/*  814 */       long l1 = (this.value[i + this.offset] & 0xFFFFFFFFL) + (bool & 0xFFFFFFFFL) + l;
/*      */       
/*  816 */       arrayOfInt[m--] = (int)l1;
/*  817 */       l = l1 >>> 32L;
/*      */     } 
/*      */ 
/*      */     
/*  821 */     while (i > 0) {
/*  822 */       i--;
/*  823 */       if (l == 0L && arrayOfInt == this.value && m == i + this.offset) {
/*      */         return;
/*      */       }
/*  826 */       long l1 = (this.value[i + this.offset] & 0xFFFFFFFFL) + l;
/*  827 */       arrayOfInt[m--] = (int)l1;
/*  828 */       l = l1 >>> 32L;
/*      */     } 
/*  830 */     while (j > 0) {
/*  831 */       j--;
/*  832 */       boolean bool = (j + paramMutableBigInteger.offset < paramMutableBigInteger.value.length) ? paramMutableBigInteger.value[j + paramMutableBigInteger.offset] : false;
/*  833 */       long l1 = (bool & 0xFFFFFFFFL) + l;
/*  834 */       arrayOfInt[m--] = (int)l1;
/*  835 */       l = l1 >>> 32L;
/*      */     } 
/*      */     
/*  838 */     if (l > 0L) {
/*  839 */       k++;
/*  840 */       if (arrayOfInt.length < k) {
/*  841 */         int[] arrayOfInt1 = new int[k];
/*      */ 
/*      */         
/*  844 */         System.arraycopy(arrayOfInt, 0, arrayOfInt1, 1, arrayOfInt.length);
/*  845 */         arrayOfInt1[0] = 1;
/*  846 */         arrayOfInt = arrayOfInt1;
/*      */       } else {
/*  848 */         arrayOfInt[m--] = 1;
/*      */       } 
/*      */     } 
/*      */     
/*  852 */     this.value = arrayOfInt;
/*  853 */     this.intLen = k;
/*  854 */     this.offset = arrayOfInt.length - k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addDisjoint(MutableBigInteger paramMutableBigInteger, int paramInt) {
/*      */     int[] arrayOfInt;
/*  863 */     if (paramMutableBigInteger.isZero()) {
/*      */       return;
/*      */     }
/*  866 */     int i = this.intLen;
/*  867 */     int j = paramMutableBigInteger.intLen + paramInt;
/*  868 */     int k = (this.intLen > j) ? this.intLen : j;
/*      */     
/*  870 */     if (this.value.length < k) {
/*  871 */       arrayOfInt = new int[k];
/*      */     } else {
/*  873 */       arrayOfInt = this.value;
/*  874 */       Arrays.fill(this.value, this.offset + this.intLen, this.value.length, 0);
/*      */     } 
/*      */     
/*  877 */     int m = arrayOfInt.length - 1;
/*      */ 
/*      */     
/*  880 */     System.arraycopy(this.value, this.offset, arrayOfInt, m + 1 - i, i);
/*  881 */     j -= i;
/*  882 */     m -= i;
/*      */     
/*  884 */     int n = Math.min(j, paramMutableBigInteger.value.length - paramMutableBigInteger.offset);
/*  885 */     System.arraycopy(paramMutableBigInteger.value, paramMutableBigInteger.offset, arrayOfInt, m + 1 - j, n);
/*      */ 
/*      */     
/*  888 */     for (int i1 = m + 1 - j + n; i1 < m + 1; i1++) {
/*  889 */       arrayOfInt[i1] = 0;
/*      */     }
/*  891 */     this.value = arrayOfInt;
/*  892 */     this.intLen = k;
/*  893 */     this.offset = arrayOfInt.length - k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addLower(MutableBigInteger paramMutableBigInteger, int paramInt) {
/*  900 */     MutableBigInteger mutableBigInteger = new MutableBigInteger(paramMutableBigInteger);
/*  901 */     if (mutableBigInteger.offset + mutableBigInteger.intLen >= paramInt) {
/*  902 */       mutableBigInteger.offset = mutableBigInteger.offset + mutableBigInteger.intLen - paramInt;
/*  903 */       mutableBigInteger.intLen = paramInt;
/*      */     } 
/*  905 */     mutableBigInteger.normalize();
/*  906 */     add(mutableBigInteger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int subtract(MutableBigInteger paramMutableBigInteger) {
/*  914 */     MutableBigInteger mutableBigInteger = this;
/*      */     
/*  916 */     int[] arrayOfInt = this.value;
/*  917 */     int i = mutableBigInteger.compare(paramMutableBigInteger);
/*      */     
/*  919 */     if (i == 0) {
/*  920 */       reset();
/*  921 */       return 0;
/*      */     } 
/*  923 */     if (i < 0) {
/*  924 */       MutableBigInteger mutableBigInteger1 = mutableBigInteger;
/*  925 */       mutableBigInteger = paramMutableBigInteger;
/*  926 */       paramMutableBigInteger = mutableBigInteger1;
/*      */     } 
/*      */     
/*  929 */     int j = mutableBigInteger.intLen;
/*  930 */     if (arrayOfInt.length < j) {
/*  931 */       arrayOfInt = new int[j];
/*      */     }
/*  933 */     long l = 0L;
/*  934 */     int k = mutableBigInteger.intLen;
/*  935 */     int m = paramMutableBigInteger.intLen;
/*  936 */     int n = arrayOfInt.length - 1;
/*      */ 
/*      */     
/*  939 */     while (m > 0) {
/*  940 */       k--; m--;
/*      */       
/*  942 */       l = (mutableBigInteger.value[k + mutableBigInteger.offset] & 0xFFFFFFFFL) - (paramMutableBigInteger.value[m + paramMutableBigInteger.offset] & 0xFFFFFFFFL) - (int)-(l >> 32L);
/*      */       
/*  944 */       arrayOfInt[n--] = (int)l;
/*      */     } 
/*      */     
/*  947 */     while (k > 0) {
/*  948 */       k--;
/*  949 */       l = (mutableBigInteger.value[k + mutableBigInteger.offset] & 0xFFFFFFFFL) - (int)-(l >> 32L);
/*  950 */       arrayOfInt[n--] = (int)l;
/*      */     } 
/*      */     
/*  953 */     this.value = arrayOfInt;
/*  954 */     this.intLen = j;
/*  955 */     this.offset = this.value.length - j;
/*  956 */     normalize();
/*  957 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int difference(MutableBigInteger paramMutableBigInteger) {
/*  966 */     MutableBigInteger mutableBigInteger = this;
/*  967 */     int i = mutableBigInteger.compare(paramMutableBigInteger);
/*  968 */     if (i == 0)
/*  969 */       return 0; 
/*  970 */     if (i < 0) {
/*  971 */       MutableBigInteger mutableBigInteger1 = mutableBigInteger;
/*  972 */       mutableBigInteger = paramMutableBigInteger;
/*  973 */       paramMutableBigInteger = mutableBigInteger1;
/*      */     } 
/*      */     
/*  976 */     long l = 0L;
/*  977 */     int j = mutableBigInteger.intLen;
/*  978 */     int k = paramMutableBigInteger.intLen;
/*      */ 
/*      */     
/*  981 */     while (k > 0) {
/*  982 */       j--; k--;
/*  983 */       l = (mutableBigInteger.value[mutableBigInteger.offset + j] & 0xFFFFFFFFL) - (paramMutableBigInteger.value[paramMutableBigInteger.offset + k] & 0xFFFFFFFFL) - (int)-(l >> 32L);
/*      */       
/*  985 */       mutableBigInteger.value[mutableBigInteger.offset + j] = (int)l;
/*      */     } 
/*      */     
/*  988 */     while (j > 0) {
/*  989 */       j--;
/*  990 */       l = (mutableBigInteger.value[mutableBigInteger.offset + j] & 0xFFFFFFFFL) - (int)-(l >> 32L);
/*  991 */       mutableBigInteger.value[mutableBigInteger.offset + j] = (int)l;
/*      */     } 
/*      */     
/*  994 */     mutableBigInteger.normalize();
/*  995 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void multiply(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2) {
/* 1003 */     int i = this.intLen;
/* 1004 */     int j = paramMutableBigInteger1.intLen;
/* 1005 */     int k = i + j;
/*      */ 
/*      */     
/* 1008 */     if (paramMutableBigInteger2.value.length < k)
/* 1009 */       paramMutableBigInteger2.value = new int[k]; 
/* 1010 */     paramMutableBigInteger2.offset = 0;
/* 1011 */     paramMutableBigInteger2.intLen = k;
/*      */ 
/*      */     
/* 1014 */     long l = 0L; int m, n;
/* 1015 */     for (m = j - 1, n = j + i - 1; m >= 0; m--, n--) {
/* 1016 */       long l1 = (paramMutableBigInteger1.value[m + paramMutableBigInteger1.offset] & 0xFFFFFFFFL) * (this.value[i - 1 + this.offset] & 0xFFFFFFFFL) + l;
/*      */       
/* 1018 */       paramMutableBigInteger2.value[n] = (int)l1;
/* 1019 */       l = l1 >>> 32L;
/*      */     } 
/* 1021 */     paramMutableBigInteger2.value[i - 1] = (int)l;
/*      */ 
/*      */     
/* 1024 */     for (m = i - 2; m >= 0; m--) {
/* 1025 */       l = 0L; int i1;
/* 1026 */       for (n = j - 1, i1 = j + m; n >= 0; n--, i1--) {
/* 1027 */         long l1 = (paramMutableBigInteger1.value[n + paramMutableBigInteger1.offset] & 0xFFFFFFFFL) * (this.value[m + this.offset] & 0xFFFFFFFFL) + (paramMutableBigInteger2.value[i1] & 0xFFFFFFFFL) + l;
/*      */ 
/*      */         
/* 1030 */         paramMutableBigInteger2.value[i1] = (int)l1;
/* 1031 */         l = l1 >>> 32L;
/*      */       } 
/* 1033 */       paramMutableBigInteger2.value[m] = (int)l;
/*      */     } 
/*      */ 
/*      */     
/* 1037 */     paramMutableBigInteger2.normalize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void mul(int paramInt, MutableBigInteger paramMutableBigInteger) {
/* 1045 */     if (paramInt == 1) {
/* 1046 */       paramMutableBigInteger.copyValue(this);
/*      */       
/*      */       return;
/*      */     } 
/* 1050 */     if (paramInt == 0) {
/* 1051 */       paramMutableBigInteger.clear();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1056 */     long l1 = paramInt & 0xFFFFFFFFL;
/* 1057 */     int[] arrayOfInt = (paramMutableBigInteger.value.length < this.intLen + 1) ? new int[this.intLen + 1] : paramMutableBigInteger.value;
/*      */     
/* 1059 */     long l2 = 0L;
/* 1060 */     for (int i = this.intLen - 1; i >= 0; i--) {
/* 1061 */       long l = l1 * (this.value[i + this.offset] & 0xFFFFFFFFL) + l2;
/* 1062 */       arrayOfInt[i + 1] = (int)l;
/* 1063 */       l2 = l >>> 32L;
/*      */     } 
/*      */     
/* 1066 */     if (l2 == 0L) {
/* 1067 */       paramMutableBigInteger.offset = 1;
/* 1068 */       paramMutableBigInteger.intLen = this.intLen;
/*      */     } else {
/* 1070 */       paramMutableBigInteger.offset = 0;
/* 1071 */       this.intLen++;
/* 1072 */       arrayOfInt[0] = (int)l2;
/*      */     } 
/* 1074 */     paramMutableBigInteger.value = arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int divideOneWord(int paramInt, MutableBigInteger paramMutableBigInteger) {
/* 1086 */     long l1 = paramInt & 0xFFFFFFFFL;
/*      */ 
/*      */     
/* 1089 */     if (this.intLen == 1) {
/* 1090 */       long l = this.value[this.offset] & 0xFFFFFFFFL;
/* 1091 */       int m = (int)(l / l1);
/* 1092 */       int n = (int)(l - m * l1);
/* 1093 */       paramMutableBigInteger.value[0] = m;
/* 1094 */       paramMutableBigInteger.intLen = (m == 0) ? 0 : 1;
/* 1095 */       paramMutableBigInteger.offset = 0;
/* 1096 */       return n;
/*      */     } 
/*      */     
/* 1099 */     if (paramMutableBigInteger.value.length < this.intLen)
/* 1100 */       paramMutableBigInteger.value = new int[this.intLen]; 
/* 1101 */     paramMutableBigInteger.offset = 0;
/* 1102 */     paramMutableBigInteger.intLen = this.intLen;
/*      */ 
/*      */     
/* 1105 */     int i = Integer.numberOfLeadingZeros(paramInt);
/*      */     
/* 1107 */     int j = this.value[this.offset];
/* 1108 */     long l2 = j & 0xFFFFFFFFL;
/* 1109 */     if (l2 < l1) {
/* 1110 */       paramMutableBigInteger.value[0] = 0;
/*      */     } else {
/* 1112 */       paramMutableBigInteger.value[0] = (int)(l2 / l1);
/* 1113 */       j = (int)(l2 - paramMutableBigInteger.value[0] * l1);
/* 1114 */       l2 = j & 0xFFFFFFFFL;
/*      */     } 
/* 1116 */     int k = this.intLen;
/* 1117 */     while (--k > 0) {
/* 1118 */       int m; long l = l2 << 32L | this.value[this.offset + this.intLen - k] & 0xFFFFFFFFL;
/*      */ 
/*      */       
/* 1121 */       if (l >= 0L) {
/* 1122 */         m = (int)(l / l1);
/* 1123 */         j = (int)(l - m * l1);
/*      */       } else {
/* 1125 */         long l3 = divWord(l, paramInt);
/* 1126 */         m = (int)(l3 & 0xFFFFFFFFL);
/* 1127 */         j = (int)(l3 >>> 32L);
/*      */       } 
/* 1129 */       paramMutableBigInteger.value[this.intLen - k] = m;
/* 1130 */       l2 = j & 0xFFFFFFFFL;
/*      */     } 
/*      */     
/* 1133 */     paramMutableBigInteger.normalize();
/*      */     
/* 1135 */     if (i > 0) {
/* 1136 */       return j % paramInt;
/*      */     }
/* 1138 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger divide(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2) {
/* 1147 */     return divide(paramMutableBigInteger1, paramMutableBigInteger2, true);
/*      */   }
/*      */   
/*      */   MutableBigInteger divide(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, boolean paramBoolean) {
/* 1151 */     if (paramMutableBigInteger1.intLen < 80 || this.intLen - paramMutableBigInteger1.intLen < 40)
/*      */     {
/* 1153 */       return divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2, paramBoolean);
/*      */     }
/* 1155 */     return divideAndRemainderBurnikelZiegler(paramMutableBigInteger1, paramMutableBigInteger2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger divideKnuth(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2) {
/* 1163 */     return divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger divideKnuth(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, boolean paramBoolean) {
/* 1178 */     if (paramMutableBigInteger1.intLen == 0) {
/* 1179 */       throw new ArithmeticException("BigInteger divide by zero");
/*      */     }
/*      */     
/* 1182 */     if (this.intLen == 0) {
/* 1183 */       paramMutableBigInteger2.intLen = paramMutableBigInteger2.offset = 0;
/* 1184 */       return paramBoolean ? new MutableBigInteger() : null;
/*      */     } 
/*      */     
/* 1187 */     int i = compare(paramMutableBigInteger1);
/*      */     
/* 1189 */     if (i < 0) {
/* 1190 */       paramMutableBigInteger2.intLen = paramMutableBigInteger2.offset = 0;
/* 1191 */       return paramBoolean ? new MutableBigInteger(this) : null;
/*      */     } 
/*      */     
/* 1194 */     if (i == 0) {
/* 1195 */       paramMutableBigInteger2.value[0] = paramMutableBigInteger2.intLen = 1;
/* 1196 */       paramMutableBigInteger2.offset = 0;
/* 1197 */       return paramBoolean ? new MutableBigInteger() : null;
/*      */     } 
/*      */     
/* 1200 */     paramMutableBigInteger2.clear();
/*      */     
/* 1202 */     if (paramMutableBigInteger1.intLen == 1) {
/* 1203 */       int j = divideOneWord(paramMutableBigInteger1.value[paramMutableBigInteger1.offset], paramMutableBigInteger2);
/* 1204 */       if (paramBoolean) {
/* 1205 */         if (j == 0)
/* 1206 */           return new MutableBigInteger(); 
/* 1207 */         return new MutableBigInteger(j);
/*      */       } 
/* 1209 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1214 */     if (this.intLen >= 6) {
/* 1215 */       int j = Math.min(getLowestSetBit(), paramMutableBigInteger1.getLowestSetBit());
/* 1216 */       if (j >= 96) {
/* 1217 */         MutableBigInteger mutableBigInteger1 = new MutableBigInteger(this);
/* 1218 */         paramMutableBigInteger1 = new MutableBigInteger(paramMutableBigInteger1);
/* 1219 */         mutableBigInteger1.rightShift(j);
/* 1220 */         paramMutableBigInteger1.rightShift(j);
/* 1221 */         MutableBigInteger mutableBigInteger2 = mutableBigInteger1.divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2);
/* 1222 */         mutableBigInteger2.leftShift(j);
/* 1223 */         return mutableBigInteger2;
/*      */       } 
/*      */     } 
/*      */     
/* 1227 */     return divideMagnitude(paramMutableBigInteger1, paramMutableBigInteger2, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger divideAndRemainderBurnikelZiegler(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2) {
/* 1242 */     int i = this.intLen;
/* 1243 */     int j = paramMutableBigInteger1.intLen;
/*      */ 
/*      */     
/* 1246 */     paramMutableBigInteger2.offset = paramMutableBigInteger2.intLen = 0;
/*      */     
/* 1248 */     if (i < j) {
/* 1249 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1256 */     int k = 1 << 32 - Integer.numberOfLeadingZeros(j / 80);
/*      */     
/* 1258 */     int m = (j + k - 1) / k;
/* 1259 */     int n = m * k;
/* 1260 */     long l = 32L * n;
/* 1261 */     int i1 = (int)Math.max(0L, l - paramMutableBigInteger1.bitLength());
/* 1262 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(paramMutableBigInteger1);
/* 1263 */     mutableBigInteger1.safeLeftShift(i1);
/* 1264 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger(this);
/* 1265 */     mutableBigInteger2.safeLeftShift(i1);
/*      */ 
/*      */     
/* 1268 */     int i2 = (int)((mutableBigInteger2.bitLength() + l) / l);
/* 1269 */     if (i2 < 2) {
/* 1270 */       i2 = 2;
/*      */     }
/*      */ 
/*      */     
/* 1274 */     MutableBigInteger mutableBigInteger3 = mutableBigInteger2.getBlock(i2 - 1, i2, n);
/*      */ 
/*      */     
/* 1277 */     MutableBigInteger mutableBigInteger4 = mutableBigInteger2.getBlock(i2 - 2, i2, n);
/* 1278 */     mutableBigInteger4.addDisjoint(mutableBigInteger3, n);
/*      */ 
/*      */     
/* 1281 */     MutableBigInteger mutableBigInteger5 = new MutableBigInteger();
/*      */     
/* 1283 */     for (int i3 = i2 - 2; i3 > 0; i3--) {
/*      */       
/* 1285 */       MutableBigInteger mutableBigInteger = mutableBigInteger4.divide2n1n(mutableBigInteger1, mutableBigInteger5);
/*      */ 
/*      */       
/* 1288 */       mutableBigInteger4 = mutableBigInteger2.getBlock(i3 - 1, i2, n);
/* 1289 */       mutableBigInteger4.addDisjoint(mutableBigInteger, n);
/* 1290 */       paramMutableBigInteger2.addShifted(mutableBigInteger5, i3 * n);
/*      */     } 
/*      */     
/* 1293 */     MutableBigInteger mutableBigInteger6 = mutableBigInteger4.divide2n1n(mutableBigInteger1, mutableBigInteger5);
/* 1294 */     paramMutableBigInteger2.add(mutableBigInteger5);
/*      */     
/* 1296 */     mutableBigInteger6.rightShift(i1);
/* 1297 */     return mutableBigInteger6;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MutableBigInteger divide2n1n(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2) {
/* 1312 */     int i = paramMutableBigInteger1.intLen;
/*      */ 
/*      */     
/* 1315 */     if (i % 2 != 0 || i < 80) {
/* 1316 */       return divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2);
/*      */     }
/*      */ 
/*      */     
/* 1320 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(this);
/* 1321 */     mutableBigInteger1.safeRightShift(32 * i / 2);
/* 1322 */     keepLower(i / 2);
/*      */ 
/*      */     
/* 1325 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
/* 1326 */     MutableBigInteger mutableBigInteger3 = mutableBigInteger1.divide3n2n(paramMutableBigInteger1, mutableBigInteger2);
/*      */ 
/*      */     
/* 1329 */     addDisjoint(mutableBigInteger3, i / 2);
/* 1330 */     MutableBigInteger mutableBigInteger4 = divide3n2n(paramMutableBigInteger1, paramMutableBigInteger2);
/*      */ 
/*      */     
/* 1333 */     paramMutableBigInteger2.addDisjoint(mutableBigInteger2, i / 2);
/* 1334 */     return mutableBigInteger4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MutableBigInteger divide3n2n(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2) {
/*      */     MutableBigInteger mutableBigInteger3, mutableBigInteger4;
/* 1347 */     int i = paramMutableBigInteger1.intLen / 2;
/*      */ 
/*      */     
/* 1350 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(this);
/* 1351 */     mutableBigInteger1.safeRightShift(32 * i);
/*      */ 
/*      */     
/* 1354 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger(paramMutableBigInteger1);
/* 1355 */     mutableBigInteger2.safeRightShift(i * 32);
/* 1356 */     BigInteger bigInteger = paramMutableBigInteger1.getLower(i);
/*      */ 
/*      */ 
/*      */     
/* 1360 */     if (compareShifted(paramMutableBigInteger1, i) < 0) {
/*      */       
/* 1362 */       mutableBigInteger3 = mutableBigInteger1.divide2n1n(mutableBigInteger2, paramMutableBigInteger2);
/*      */ 
/*      */       
/* 1365 */       mutableBigInteger4 = new MutableBigInteger(paramMutableBigInteger2.toBigInteger().multiply(bigInteger));
/*      */     } else {
/*      */       
/* 1368 */       paramMutableBigInteger2.ones(i);
/* 1369 */       mutableBigInteger1.add(mutableBigInteger2);
/* 1370 */       mutableBigInteger2.leftShift(32 * i);
/* 1371 */       mutableBigInteger1.subtract(mutableBigInteger2);
/* 1372 */       mutableBigInteger3 = mutableBigInteger1;
/*      */ 
/*      */       
/* 1375 */       mutableBigInteger4 = new MutableBigInteger(bigInteger);
/* 1376 */       mutableBigInteger4.leftShift(32 * i);
/* 1377 */       mutableBigInteger4.subtract(new MutableBigInteger(bigInteger));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1382 */     mutableBigInteger3.leftShift(32 * i);
/* 1383 */     mutableBigInteger3.addLower(this, i);
/*      */ 
/*      */     
/* 1386 */     while (mutableBigInteger3.compare(mutableBigInteger4) < 0) {
/* 1387 */       mutableBigInteger3.add(paramMutableBigInteger1);
/* 1388 */       paramMutableBigInteger2.subtract(ONE);
/*      */     } 
/* 1390 */     mutableBigInteger3.subtract(mutableBigInteger4);
/*      */     
/* 1392 */     return mutableBigInteger3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MutableBigInteger getBlock(int paramInt1, int paramInt2, int paramInt3) {
/* 1405 */     int j, i = paramInt1 * paramInt3;
/* 1406 */     if (i >= this.intLen) {
/* 1407 */       return new MutableBigInteger();
/*      */     }
/*      */ 
/*      */     
/* 1411 */     if (paramInt1 == paramInt2 - 1) {
/* 1412 */       j = this.intLen;
/*      */     } else {
/* 1414 */       j = (paramInt1 + 1) * paramInt3;
/*      */     } 
/* 1416 */     if (j > this.intLen) {
/* 1417 */       return new MutableBigInteger();
/*      */     }
/*      */     
/* 1420 */     int[] arrayOfInt = Arrays.copyOfRange(this.value, this.offset + this.intLen - j, this.offset + this.intLen - i);
/* 1421 */     return new MutableBigInteger(arrayOfInt);
/*      */   }
/*      */ 
/*      */   
/*      */   long bitLength() {
/* 1426 */     if (this.intLen == 0)
/* 1427 */       return 0L; 
/* 1428 */     return this.intLen * 32L - Integer.numberOfLeadingZeros(this.value[this.offset]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long divide(long paramLong, MutableBigInteger paramMutableBigInteger) {
/* 1439 */     if (paramLong == 0L) {
/* 1440 */       throw new ArithmeticException("BigInteger divide by zero");
/*      */     }
/*      */     
/* 1443 */     if (this.intLen == 0) {
/* 1444 */       paramMutableBigInteger.intLen = paramMutableBigInteger.offset = 0;
/* 1445 */       return 0L;
/*      */     } 
/* 1447 */     if (paramLong < 0L) {
/* 1448 */       paramLong = -paramLong;
/*      */     }
/* 1450 */     int i = (int)(paramLong >>> 32L);
/* 1451 */     paramMutableBigInteger.clear();
/*      */     
/* 1453 */     if (i == 0) {
/* 1454 */       return divideOneWord((int)paramLong, paramMutableBigInteger) & 0xFFFFFFFFL;
/*      */     }
/* 1456 */     return divideLongMagnitude(paramLong, paramMutableBigInteger).toLong();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void copyAndShift(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4) {
/* 1461 */     int i = 32 - paramInt4;
/* 1462 */     int j = paramArrayOfint1[paramInt1];
/* 1463 */     for (byte b = 0; b < paramInt2 - 1; b++) {
/* 1464 */       int k = j;
/* 1465 */       j = paramArrayOfint1[++paramInt1];
/* 1466 */       paramArrayOfint2[paramInt3 + b] = k << paramInt4 | j >>> i;
/*      */     } 
/* 1468 */     paramArrayOfint2[paramInt3 + paramInt2 - 1] = j << paramInt4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MutableBigInteger divideMagnitude(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, boolean paramBoolean) {
/*      */     int[] arrayOfInt1;
/*      */     MutableBigInteger mutableBigInteger;
/* 1481 */     int i = Integer.numberOfLeadingZeros(paramMutableBigInteger1.value[paramMutableBigInteger1.offset]);
/*      */     
/* 1483 */     int j = paramMutableBigInteger1.intLen;
/*      */ 
/*      */     
/* 1486 */     if (i > 0) {
/* 1487 */       arrayOfInt1 = new int[j];
/* 1488 */       copyAndShift(paramMutableBigInteger1.value, paramMutableBigInteger1.offset, j, arrayOfInt1, 0, i);
/* 1489 */       if (Integer.numberOfLeadingZeros(this.value[this.offset]) >= i) {
/* 1490 */         int[] arrayOfInt = new int[this.intLen + 1];
/* 1491 */         mutableBigInteger = new MutableBigInteger(arrayOfInt);
/* 1492 */         mutableBigInteger.intLen = this.intLen;
/* 1493 */         mutableBigInteger.offset = 1;
/* 1494 */         copyAndShift(this.value, this.offset, this.intLen, arrayOfInt, 1, i);
/*      */       } else {
/* 1496 */         int[] arrayOfInt = new int[this.intLen + 2];
/* 1497 */         mutableBigInteger = new MutableBigInteger(arrayOfInt);
/* 1498 */         this.intLen++;
/* 1499 */         mutableBigInteger.offset = 1;
/* 1500 */         int i7 = this.offset;
/* 1501 */         int i8 = 0;
/* 1502 */         int i9 = 32 - i;
/* 1503 */         for (byte b = 1; b < this.intLen + 1; b++, i7++) {
/* 1504 */           int i10 = i8;
/* 1505 */           i8 = this.value[i7];
/* 1506 */           arrayOfInt[b] = i10 << i | i8 >>> i9;
/*      */         } 
/* 1508 */         arrayOfInt[this.intLen + 1] = i8 << i;
/*      */       } 
/*      */     } else {
/* 1511 */       arrayOfInt1 = Arrays.copyOfRange(paramMutableBigInteger1.value, paramMutableBigInteger1.offset, paramMutableBigInteger1.offset + paramMutableBigInteger1.intLen);
/* 1512 */       mutableBigInteger = new MutableBigInteger(new int[this.intLen + 1]);
/* 1513 */       System.arraycopy(this.value, this.offset, mutableBigInteger.value, 1, this.intLen);
/* 1514 */       mutableBigInteger.intLen = this.intLen;
/* 1515 */       mutableBigInteger.offset = 1;
/*      */     } 
/*      */     
/* 1518 */     int k = mutableBigInteger.intLen;
/*      */ 
/*      */     
/* 1521 */     int m = k - j + 1;
/* 1522 */     if (paramMutableBigInteger2.value.length < m) {
/* 1523 */       paramMutableBigInteger2.value = new int[m];
/* 1524 */       paramMutableBigInteger2.offset = 0;
/*      */     } 
/* 1526 */     paramMutableBigInteger2.intLen = m;
/* 1527 */     int[] arrayOfInt2 = paramMutableBigInteger2.value;
/*      */ 
/*      */ 
/*      */     
/* 1531 */     if (mutableBigInteger.intLen == k) {
/* 1532 */       mutableBigInteger.offset = 0;
/* 1533 */       mutableBigInteger.value[0] = 0;
/* 1534 */       mutableBigInteger.intLen++;
/*      */     } 
/*      */     
/* 1537 */     int n = arrayOfInt1[0];
/* 1538 */     long l = n & 0xFFFFFFFFL;
/* 1539 */     int i1 = arrayOfInt1[1];
/*      */     
/*      */     int i2;
/* 1542 */     for (i2 = 0; i2 < m - 1; i2++) {
/*      */ 
/*      */       
/* 1545 */       int i7 = 0;
/* 1546 */       int i8 = 0;
/* 1547 */       boolean bool1 = false;
/* 1548 */       int i9 = mutableBigInteger.value[i2 + mutableBigInteger.offset];
/* 1549 */       int i10 = i9 + Integer.MIN_VALUE;
/* 1550 */       int i11 = mutableBigInteger.value[i2 + 1 + mutableBigInteger.offset];
/*      */       
/* 1552 */       if (i9 == n) {
/* 1553 */         i7 = -1;
/* 1554 */         i8 = i9 + i11;
/* 1555 */         bool1 = (i8 + Integer.MIN_VALUE < i10) ? true : false;
/*      */       } else {
/* 1557 */         long l1 = i9 << 32L | i11 & 0xFFFFFFFFL;
/* 1558 */         if (l1 >= 0L) {
/* 1559 */           i7 = (int)(l1 / l);
/* 1560 */           i8 = (int)(l1 - i7 * l);
/*      */         } else {
/* 1562 */           long l2 = divWord(l1, n);
/* 1563 */           i7 = (int)(l2 & 0xFFFFFFFFL);
/* 1564 */           i8 = (int)(l2 >>> 32L);
/*      */         } 
/*      */       } 
/*      */       
/* 1568 */       if (i7 != 0) {
/*      */ 
/*      */         
/* 1571 */         if (!bool1) {
/* 1572 */           long l1 = mutableBigInteger.value[i2 + 2 + mutableBigInteger.offset] & 0xFFFFFFFFL;
/* 1573 */           long l2 = (i8 & 0xFFFFFFFFL) << 32L | l1;
/* 1574 */           long l3 = (i1 & 0xFFFFFFFFL) * (i7 & 0xFFFFFFFFL);
/*      */           
/* 1576 */           if (unsignedLongCompare(l3, l2)) {
/* 1577 */             i7--;
/* 1578 */             i8 = (int)((i8 & 0xFFFFFFFFL) + l);
/* 1579 */             if ((i8 & 0xFFFFFFFFL) >= l) {
/* 1580 */               l3 -= i1 & 0xFFFFFFFFL;
/* 1581 */               l2 = (i8 & 0xFFFFFFFFL) << 32L | l1;
/* 1582 */               if (unsignedLongCompare(l3, l2)) {
/* 1583 */                 i7--;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1589 */         mutableBigInteger.value[i2 + mutableBigInteger.offset] = 0;
/* 1590 */         int i12 = mulsub(mutableBigInteger.value, arrayOfInt1, i7, j, i2 + mutableBigInteger.offset);
/*      */ 
/*      */         
/* 1593 */         if (i12 + Integer.MIN_VALUE > i10) {
/*      */           
/* 1595 */           divadd(arrayOfInt1, mutableBigInteger.value, i2 + 1 + mutableBigInteger.offset);
/* 1596 */           i7--;
/*      */         } 
/*      */ 
/*      */         
/* 1600 */         arrayOfInt2[i2] = i7;
/*      */       } 
/*      */     } 
/*      */     
/* 1604 */     i2 = 0;
/* 1605 */     int i3 = 0;
/* 1606 */     boolean bool = false;
/* 1607 */     int i4 = mutableBigInteger.value[m - 1 + mutableBigInteger.offset];
/* 1608 */     int i5 = i4 + Integer.MIN_VALUE;
/* 1609 */     int i6 = mutableBigInteger.value[m + mutableBigInteger.offset];
/*      */     
/* 1611 */     if (i4 == n) {
/* 1612 */       i2 = -1;
/* 1613 */       i3 = i4 + i6;
/* 1614 */       bool = (i3 + Integer.MIN_VALUE < i5) ? true : false;
/*      */     } else {
/* 1616 */       long l1 = i4 << 32L | i6 & 0xFFFFFFFFL;
/* 1617 */       if (l1 >= 0L) {
/* 1618 */         i2 = (int)(l1 / l);
/* 1619 */         i3 = (int)(l1 - i2 * l);
/*      */       } else {
/* 1621 */         long l2 = divWord(l1, n);
/* 1622 */         i2 = (int)(l2 & 0xFFFFFFFFL);
/* 1623 */         i3 = (int)(l2 >>> 32L);
/*      */       } 
/*      */     } 
/* 1626 */     if (i2 != 0) {
/* 1627 */       int i7; if (!bool) {
/* 1628 */         long l1 = mutableBigInteger.value[m + 1 + mutableBigInteger.offset] & 0xFFFFFFFFL;
/* 1629 */         long l2 = (i3 & 0xFFFFFFFFL) << 32L | l1;
/* 1630 */         long l3 = (i1 & 0xFFFFFFFFL) * (i2 & 0xFFFFFFFFL);
/*      */         
/* 1632 */         if (unsignedLongCompare(l3, l2)) {
/* 1633 */           i2--;
/* 1634 */           i3 = (int)((i3 & 0xFFFFFFFFL) + l);
/* 1635 */           if ((i3 & 0xFFFFFFFFL) >= l) {
/* 1636 */             l3 -= i1 & 0xFFFFFFFFL;
/* 1637 */             l2 = (i3 & 0xFFFFFFFFL) << 32L | l1;
/* 1638 */             if (unsignedLongCompare(l3, l2)) {
/* 1639 */               i2--;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1647 */       mutableBigInteger.value[m - 1 + mutableBigInteger.offset] = 0;
/* 1648 */       if (paramBoolean) {
/* 1649 */         i7 = mulsub(mutableBigInteger.value, arrayOfInt1, i2, j, m - 1 + mutableBigInteger.offset);
/*      */       } else {
/* 1651 */         i7 = mulsubBorrow(mutableBigInteger.value, arrayOfInt1, i2, j, m - 1 + mutableBigInteger.offset);
/*      */       } 
/*      */       
/* 1654 */       if (i7 + Integer.MIN_VALUE > i5) {
/*      */         
/* 1656 */         if (paramBoolean)
/* 1657 */           divadd(arrayOfInt1, mutableBigInteger.value, m - 1 + 1 + mutableBigInteger.offset); 
/* 1658 */         i2--;
/*      */       } 
/*      */ 
/*      */       
/* 1662 */       arrayOfInt2[m - 1] = i2;
/*      */     } 
/*      */ 
/*      */     
/* 1666 */     if (paramBoolean) {
/*      */       
/* 1668 */       if (i > 0)
/* 1669 */         mutableBigInteger.rightShift(i); 
/* 1670 */       mutableBigInteger.normalize();
/*      */     } 
/* 1672 */     paramMutableBigInteger2.normalize();
/* 1673 */     return paramBoolean ? mutableBigInteger : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MutableBigInteger divideLongMagnitude(long paramLong, MutableBigInteger paramMutableBigInteger) {
/* 1683 */     MutableBigInteger mutableBigInteger = new MutableBigInteger(new int[this.intLen + 1]);
/* 1684 */     System.arraycopy(this.value, this.offset, mutableBigInteger.value, 1, this.intLen);
/* 1685 */     mutableBigInteger.intLen = this.intLen;
/* 1686 */     mutableBigInteger.offset = 1;
/*      */     
/* 1688 */     int i = mutableBigInteger.intLen;
/*      */     
/* 1690 */     int j = i - 2 + 1;
/* 1691 */     if (paramMutableBigInteger.value.length < j) {
/* 1692 */       paramMutableBigInteger.value = new int[j];
/* 1693 */       paramMutableBigInteger.offset = 0;
/*      */     } 
/* 1695 */     paramMutableBigInteger.intLen = j;
/* 1696 */     int[] arrayOfInt = paramMutableBigInteger.value;
/*      */ 
/*      */     
/* 1699 */     int k = Long.numberOfLeadingZeros(paramLong);
/* 1700 */     if (k > 0) {
/* 1701 */       paramLong <<= k;
/* 1702 */       mutableBigInteger.leftShift(k);
/*      */     } 
/*      */ 
/*      */     
/* 1706 */     if (mutableBigInteger.intLen == i) {
/* 1707 */       mutableBigInteger.offset = 0;
/* 1708 */       mutableBigInteger.value[0] = 0;
/* 1709 */       mutableBigInteger.intLen++;
/*      */     } 
/*      */     
/* 1712 */     int m = (int)(paramLong >>> 32L);
/* 1713 */     long l = m & 0xFFFFFFFFL;
/* 1714 */     int n = (int)(paramLong & 0xFFFFFFFFL);
/*      */ 
/*      */     
/* 1717 */     for (byte b = 0; b < j; b++) {
/*      */ 
/*      */       
/* 1720 */       int i1 = 0;
/* 1721 */       int i2 = 0;
/* 1722 */       boolean bool = false;
/* 1723 */       int i3 = mutableBigInteger.value[b + mutableBigInteger.offset];
/* 1724 */       int i4 = i3 + Integer.MIN_VALUE;
/* 1725 */       int i5 = mutableBigInteger.value[b + 1 + mutableBigInteger.offset];
/*      */       
/* 1727 */       if (i3 == m) {
/* 1728 */         i1 = -1;
/* 1729 */         i2 = i3 + i5;
/* 1730 */         bool = (i2 + Integer.MIN_VALUE < i4) ? true : false;
/*      */       } else {
/* 1732 */         long l1 = i3 << 32L | i5 & 0xFFFFFFFFL;
/* 1733 */         if (l1 >= 0L) {
/* 1734 */           i1 = (int)(l1 / l);
/* 1735 */           i2 = (int)(l1 - i1 * l);
/*      */         } else {
/* 1737 */           long l2 = divWord(l1, m);
/* 1738 */           i1 = (int)(l2 & 0xFFFFFFFFL);
/* 1739 */           i2 = (int)(l2 >>> 32L);
/*      */         } 
/*      */       } 
/*      */       
/* 1743 */       if (i1 != 0) {
/*      */ 
/*      */         
/* 1746 */         if (!bool) {
/* 1747 */           long l1 = mutableBigInteger.value[b + 2 + mutableBigInteger.offset] & 0xFFFFFFFFL;
/* 1748 */           long l2 = (i2 & 0xFFFFFFFFL) << 32L | l1;
/* 1749 */           long l3 = (n & 0xFFFFFFFFL) * (i1 & 0xFFFFFFFFL);
/*      */           
/* 1751 */           if (unsignedLongCompare(l3, l2)) {
/* 1752 */             i1--;
/* 1753 */             i2 = (int)((i2 & 0xFFFFFFFFL) + l);
/* 1754 */             if ((i2 & 0xFFFFFFFFL) >= l) {
/* 1755 */               l3 -= n & 0xFFFFFFFFL;
/* 1756 */               l2 = (i2 & 0xFFFFFFFFL) << 32L | l1;
/* 1757 */               if (unsignedLongCompare(l3, l2)) {
/* 1758 */                 i1--;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1764 */         mutableBigInteger.value[b + mutableBigInteger.offset] = 0;
/* 1765 */         int i6 = mulsubLong(mutableBigInteger.value, m, n, i1, b + mutableBigInteger.offset);
/*      */ 
/*      */         
/* 1768 */         if (i6 + Integer.MIN_VALUE > i4) {
/*      */           
/* 1770 */           divaddLong(m, n, mutableBigInteger.value, b + 1 + mutableBigInteger.offset);
/* 1771 */           i1--;
/*      */         } 
/*      */ 
/*      */         
/* 1775 */         arrayOfInt[b] = i1;
/*      */       } 
/*      */     } 
/*      */     
/* 1779 */     if (k > 0) {
/* 1780 */       mutableBigInteger.rightShift(k);
/*      */     }
/* 1782 */     paramMutableBigInteger.normalize();
/* 1783 */     mutableBigInteger.normalize();
/* 1784 */     return mutableBigInteger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int divaddLong(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 1793 */     long l1 = 0L;
/*      */     
/* 1795 */     long l2 = (paramInt2 & 0xFFFFFFFFL) + (paramArrayOfint[1 + paramInt3] & 0xFFFFFFFFL);
/* 1796 */     paramArrayOfint[1 + paramInt3] = (int)l2;
/*      */     
/* 1798 */     l2 = (paramInt1 & 0xFFFFFFFFL) + (paramArrayOfint[paramInt3] & 0xFFFFFFFFL) + l1;
/* 1799 */     paramArrayOfint[paramInt3] = (int)l2;
/* 1800 */     l1 = l2 >>> 32L;
/* 1801 */     return (int)l1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int mulsubLong(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1810 */     long l1 = paramInt3 & 0xFFFFFFFFL;
/* 1811 */     paramInt4 += 2;
/* 1812 */     long l2 = (paramInt2 & 0xFFFFFFFFL) * l1;
/* 1813 */     long l3 = paramArrayOfint[paramInt4] - l2;
/* 1814 */     paramArrayOfint[paramInt4--] = (int)l3;
/* 1815 */     long l4 = (l2 >>> 32L) + (((l3 & 0xFFFFFFFFL) > (((int)l2 ^ 0xFFFFFFFF) & 0xFFFFFFFFL)) ? 1L : 0L);
/*      */ 
/*      */     
/* 1818 */     l2 = (paramInt1 & 0xFFFFFFFFL) * l1 + l4;
/* 1819 */     l3 = paramArrayOfint[paramInt4] - l2;
/* 1820 */     paramArrayOfint[paramInt4--] = (int)l3;
/* 1821 */     l4 = (l2 >>> 32L) + (((l3 & 0xFFFFFFFFL) > (((int)l2 ^ 0xFFFFFFFF) & 0xFFFFFFFFL)) ? 1L : 0L);
/*      */ 
/*      */     
/* 1824 */     return (int)l4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean unsignedLongCompare(long paramLong1, long paramLong2) {
/* 1832 */     return (paramLong1 + Long.MIN_VALUE > paramLong2 + Long.MIN_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long divWord(long paramLong, int paramInt) {
/* 1843 */     long l1 = paramInt & 0xFFFFFFFFL;
/*      */ 
/*      */     
/* 1846 */     if (l1 == 1L) {
/* 1847 */       long l5 = (int)paramLong;
/* 1848 */       long l4 = 0L;
/* 1849 */       return l4 << 32L | l5 & 0xFFFFFFFFL;
/*      */     } 
/*      */ 
/*      */     
/* 1853 */     long l3 = (paramLong >>> 1L) / (l1 >>> 1L);
/* 1854 */     long l2 = paramLong - l3 * l1;
/*      */ 
/*      */     
/* 1857 */     while (l2 < 0L) {
/* 1858 */       l2 += l1;
/* 1859 */       l3--;
/*      */     } 
/* 1861 */     while (l2 >= l1) {
/* 1862 */       l2 -= l1;
/* 1863 */       l3++;
/*      */     } 
/*      */     
/* 1866 */     return l2 << 32L | l3 & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger hybridGCD(MutableBigInteger paramMutableBigInteger) {
/* 1875 */     MutableBigInteger mutableBigInteger1 = this;
/* 1876 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
/*      */     
/* 1878 */     while (paramMutableBigInteger.intLen != 0) {
/* 1879 */       if (Math.abs(mutableBigInteger1.intLen - paramMutableBigInteger.intLen) < 2) {
/* 1880 */         return mutableBigInteger1.binaryGCD(paramMutableBigInteger);
/*      */       }
/* 1882 */       MutableBigInteger mutableBigInteger = mutableBigInteger1.divide(paramMutableBigInteger, mutableBigInteger2);
/* 1883 */       mutableBigInteger1 = paramMutableBigInteger;
/* 1884 */       paramMutableBigInteger = mutableBigInteger;
/*      */     } 
/* 1886 */     return mutableBigInteger1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MutableBigInteger binaryGCD(MutableBigInteger paramMutableBigInteger) {
/* 1895 */     MutableBigInteger mutableBigInteger1 = this;
/* 1896 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
/*      */ 
/*      */     
/* 1899 */     int i = mutableBigInteger1.getLowestSetBit();
/* 1900 */     int j = paramMutableBigInteger.getLowestSetBit();
/* 1901 */     int k = (i < j) ? i : j;
/* 1902 */     if (k != 0) {
/* 1903 */       mutableBigInteger1.rightShift(k);
/* 1904 */       paramMutableBigInteger.rightShift(k);
/*      */     } 
/*      */ 
/*      */     
/* 1908 */     boolean bool = (k == i) ? true : false;
/* 1909 */     MutableBigInteger mutableBigInteger3 = bool ? paramMutableBigInteger : mutableBigInteger1;
/* 1910 */     int m = bool ? -1 : 1;
/*      */     
/*      */     int n;
/* 1913 */     while ((n = mutableBigInteger3.getLowestSetBit()) >= 0) {
/*      */       
/* 1915 */       mutableBigInteger3.rightShift(n);
/*      */       
/* 1917 */       if (m) {
/* 1918 */         mutableBigInteger1 = mutableBigInteger3;
/*      */       } else {
/* 1920 */         paramMutableBigInteger = mutableBigInteger3;
/*      */       } 
/*      */       
/* 1923 */       if (mutableBigInteger1.intLen < 2 && paramMutableBigInteger.intLen < 2) {
/* 1924 */         int i1 = mutableBigInteger1.value[mutableBigInteger1.offset];
/* 1925 */         int i2 = paramMutableBigInteger.value[paramMutableBigInteger.offset];
/* 1926 */         i1 = binaryGcd(i1, i2);
/* 1927 */         mutableBigInteger2.value[0] = i1;
/* 1928 */         mutableBigInteger2.intLen = 1;
/* 1929 */         mutableBigInteger2.offset = 0;
/* 1930 */         if (k > 0)
/* 1931 */           mutableBigInteger2.leftShift(k); 
/* 1932 */         return mutableBigInteger2;
/*      */       } 
/*      */ 
/*      */       
/* 1936 */       if ((m = mutableBigInteger1.difference(paramMutableBigInteger)) == 0)
/*      */         break; 
/* 1938 */       mutableBigInteger3 = (m >= 0) ? mutableBigInteger1 : paramMutableBigInteger;
/*      */     } 
/*      */     
/* 1941 */     if (k > 0)
/* 1942 */       mutableBigInteger1.leftShift(k); 
/* 1943 */     return mutableBigInteger1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int binaryGcd(int paramInt1, int paramInt2) {
/* 1950 */     if (paramInt2 == 0)
/* 1951 */       return paramInt1; 
/* 1952 */     if (paramInt1 == 0) {
/* 1953 */       return paramInt2;
/*      */     }
/*      */     
/* 1956 */     int i = Integer.numberOfTrailingZeros(paramInt1);
/* 1957 */     int j = Integer.numberOfTrailingZeros(paramInt2);
/* 1958 */     paramInt1 >>>= i;
/* 1959 */     paramInt2 >>>= j;
/*      */     
/* 1961 */     int k = (i < j) ? i : j;
/*      */     
/* 1963 */     while (paramInt1 != paramInt2) {
/* 1964 */       if (paramInt1 + Integer.MIN_VALUE > paramInt2 + Integer.MIN_VALUE) {
/* 1965 */         paramInt1 -= paramInt2;
/* 1966 */         paramInt1 >>>= Integer.numberOfTrailingZeros(paramInt1); continue;
/*      */       } 
/* 1968 */       paramInt2 -= paramInt1;
/* 1969 */       paramInt2 >>>= Integer.numberOfTrailingZeros(paramInt2);
/*      */     } 
/*      */     
/* 1972 */     return paramInt1 << k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger mutableModInverse(MutableBigInteger paramMutableBigInteger) {
/* 1981 */     if (paramMutableBigInteger.isOdd()) {
/* 1982 */       return modInverse(paramMutableBigInteger);
/*      */     }
/*      */     
/* 1985 */     if (isEven()) {
/* 1986 */       throw new ArithmeticException("BigInteger not invertible.");
/*      */     }
/*      */     
/* 1989 */     int i = paramMutableBigInteger.getLowestSetBit();
/*      */ 
/*      */     
/* 1992 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(paramMutableBigInteger);
/* 1993 */     mutableBigInteger1.rightShift(i);
/*      */     
/* 1995 */     if (mutableBigInteger1.isOne()) {
/* 1996 */       return modInverseMP2(i);
/*      */     }
/*      */     
/* 1999 */     MutableBigInteger mutableBigInteger2 = modInverse(mutableBigInteger1);
/*      */ 
/*      */     
/* 2002 */     MutableBigInteger mutableBigInteger3 = modInverseMP2(i);
/*      */ 
/*      */     
/* 2005 */     MutableBigInteger mutableBigInteger4 = modInverseBP2(mutableBigInteger1, i);
/* 2006 */     MutableBigInteger mutableBigInteger5 = mutableBigInteger1.modInverseMP2(i);
/*      */     
/* 2008 */     MutableBigInteger mutableBigInteger6 = new MutableBigInteger();
/* 2009 */     MutableBigInteger mutableBigInteger7 = new MutableBigInteger();
/* 2010 */     MutableBigInteger mutableBigInteger8 = new MutableBigInteger();
/*      */     
/* 2012 */     mutableBigInteger2.leftShift(i);
/* 2013 */     mutableBigInteger2.multiply(mutableBigInteger4, mutableBigInteger8);
/*      */     
/* 2015 */     mutableBigInteger3.multiply(mutableBigInteger1, mutableBigInteger6);
/* 2016 */     mutableBigInteger6.multiply(mutableBigInteger5, mutableBigInteger7);
/*      */     
/* 2018 */     mutableBigInteger8.add(mutableBigInteger7);
/* 2019 */     return mutableBigInteger8.divide(paramMutableBigInteger, mutableBigInteger6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger modInverseMP2(int paramInt) {
/* 2026 */     if (isEven()) {
/* 2027 */       throw new ArithmeticException("Non-invertible. (GCD != 1)");
/*      */     }
/* 2029 */     if (paramInt > 64) {
/* 2030 */       return euclidModInverse(paramInt);
/*      */     }
/* 2032 */     int i = inverseMod32(this.value[this.offset + this.intLen - 1]);
/*      */     
/* 2034 */     if (paramInt < 33) {
/* 2035 */       i = (paramInt == 32) ? i : (i & (1 << paramInt) - 1);
/* 2036 */       return new MutableBigInteger(i);
/*      */     } 
/*      */     
/* 2039 */     long l1 = this.value[this.offset + this.intLen - 1] & 0xFFFFFFFFL;
/* 2040 */     if (this.intLen > 1)
/* 2041 */       l1 |= this.value[this.offset + this.intLen - 2] << 32L; 
/* 2042 */     long l2 = i & 0xFFFFFFFFL;
/* 2043 */     l2 *= 2L - l1 * l2;
/* 2044 */     l2 = (paramInt == 64) ? l2 : (l2 & (1L << paramInt) - 1L);
/*      */     
/* 2046 */     MutableBigInteger mutableBigInteger = new MutableBigInteger(new int[2]);
/* 2047 */     mutableBigInteger.value[0] = (int)(l2 >>> 32L);
/* 2048 */     mutableBigInteger.value[1] = (int)l2;
/* 2049 */     mutableBigInteger.intLen = 2;
/* 2050 */     mutableBigInteger.normalize();
/* 2051 */     return mutableBigInteger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int inverseMod32(int paramInt) {
/* 2059 */     int i = paramInt;
/* 2060 */     i *= 2 - paramInt * i;
/* 2061 */     i *= 2 - paramInt * i;
/* 2062 */     i *= 2 - paramInt * i;
/* 2063 */     i *= 2 - paramInt * i;
/* 2064 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long inverseMod64(long paramLong) {
/* 2072 */     long l = paramLong;
/* 2073 */     l *= 2L - paramLong * l;
/* 2074 */     l *= 2L - paramLong * l;
/* 2075 */     l *= 2L - paramLong * l;
/* 2076 */     l *= 2L - paramLong * l;
/* 2077 */     l *= 2L - paramLong * l;
/* 2078 */     assert l * paramLong == 1L;
/* 2079 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MutableBigInteger modInverseBP2(MutableBigInteger paramMutableBigInteger, int paramInt) {
/* 2087 */     return fixup(new MutableBigInteger(1), new MutableBigInteger(paramMutableBigInteger), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MutableBigInteger modInverse(MutableBigInteger paramMutableBigInteger) {
/* 2100 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(paramMutableBigInteger);
/* 2101 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger(this);
/* 2102 */     MutableBigInteger mutableBigInteger3 = new MutableBigInteger(mutableBigInteger1);
/* 2103 */     SignedMutableBigInteger signedMutableBigInteger1 = new SignedMutableBigInteger(1);
/* 2104 */     SignedMutableBigInteger signedMutableBigInteger2 = new SignedMutableBigInteger();
/* 2105 */     MutableBigInteger mutableBigInteger4 = null;
/* 2106 */     SignedMutableBigInteger signedMutableBigInteger3 = null;
/*      */     
/* 2108 */     int i = 0;
/*      */     
/* 2110 */     if (mutableBigInteger2.isEven()) {
/* 2111 */       int j = mutableBigInteger2.getLowestSetBit();
/* 2112 */       mutableBigInteger2.rightShift(j);
/* 2113 */       signedMutableBigInteger2.leftShift(j);
/* 2114 */       i = j;
/*      */     } 
/*      */ 
/*      */     
/* 2118 */     while (!mutableBigInteger2.isOne()) {
/*      */       
/* 2120 */       if (mutableBigInteger2.isZero()) {
/* 2121 */         throw new ArithmeticException("BigInteger not invertible.");
/*      */       }
/*      */       
/* 2124 */       if (mutableBigInteger2.compare(mutableBigInteger3) < 0) {
/* 2125 */         mutableBigInteger4 = mutableBigInteger2; mutableBigInteger2 = mutableBigInteger3; mutableBigInteger3 = mutableBigInteger4;
/* 2126 */         signedMutableBigInteger3 = signedMutableBigInteger2; signedMutableBigInteger2 = signedMutableBigInteger1; signedMutableBigInteger1 = signedMutableBigInteger3;
/*      */       } 
/*      */ 
/*      */       
/* 2130 */       if (((mutableBigInteger2.value[mutableBigInteger2.offset + mutableBigInteger2.intLen - 1] ^ mutableBigInteger3.value[mutableBigInteger3.offset + mutableBigInteger3.intLen - 1]) & 0x3) == 0) {
/*      */         
/* 2132 */         mutableBigInteger2.subtract(mutableBigInteger3);
/* 2133 */         signedMutableBigInteger1.signedSubtract(signedMutableBigInteger2);
/*      */       } else {
/* 2135 */         mutableBigInteger2.add(mutableBigInteger3);
/* 2136 */         signedMutableBigInteger1.signedAdd(signedMutableBigInteger2);
/*      */       } 
/*      */ 
/*      */       
/* 2140 */       int j = mutableBigInteger2.getLowestSetBit();
/* 2141 */       mutableBigInteger2.rightShift(j);
/* 2142 */       signedMutableBigInteger2.leftShift(j);
/* 2143 */       i += j;
/*      */     } 
/*      */     
/* 2146 */     while (signedMutableBigInteger1.sign < 0) {
/* 2147 */       signedMutableBigInteger1.signedAdd(mutableBigInteger1);
/*      */     }
/* 2149 */     return fixup(signedMutableBigInteger1, mutableBigInteger1, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MutableBigInteger fixup(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, int paramInt) {
/* 2159 */     MutableBigInteger mutableBigInteger = new MutableBigInteger();
/*      */     
/* 2161 */     int i = -inverseMod32(paramMutableBigInteger2.value[paramMutableBigInteger2.offset + paramMutableBigInteger2.intLen - 1]);
/*      */     int j, k;
/* 2163 */     for (j = 0, k = paramInt >> 5; j < k; j++) {
/*      */       
/* 2165 */       int m = i * paramMutableBigInteger1.value[paramMutableBigInteger1.offset + paramMutableBigInteger1.intLen - 1];
/*      */       
/* 2167 */       paramMutableBigInteger2.mul(m, mutableBigInteger);
/* 2168 */       paramMutableBigInteger1.add(mutableBigInteger);
/*      */       
/* 2170 */       paramMutableBigInteger1.intLen--;
/*      */     } 
/* 2172 */     j = paramInt & 0x1F;
/* 2173 */     if (j != 0) {
/*      */       
/* 2175 */       k = i * paramMutableBigInteger1.value[paramMutableBigInteger1.offset + paramMutableBigInteger1.intLen - 1];
/* 2176 */       k &= (1 << j) - 1;
/*      */       
/* 2178 */       paramMutableBigInteger2.mul(k, mutableBigInteger);
/* 2179 */       paramMutableBigInteger1.add(mutableBigInteger);
/*      */       
/* 2181 */       paramMutableBigInteger1.rightShift(j);
/*      */     } 
/*      */ 
/*      */     
/* 2185 */     while (paramMutableBigInteger1.compare(paramMutableBigInteger2) >= 0) {
/* 2186 */       paramMutableBigInteger1.subtract(paramMutableBigInteger2);
/*      */     }
/* 2188 */     return paramMutableBigInteger1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MutableBigInteger euclidModInverse(int paramInt) {
/* 2196 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(1);
/* 2197 */     mutableBigInteger1.leftShift(paramInt);
/* 2198 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger(mutableBigInteger1);
/*      */     
/* 2200 */     MutableBigInteger mutableBigInteger3 = new MutableBigInteger(this);
/* 2201 */     MutableBigInteger mutableBigInteger4 = new MutableBigInteger();
/* 2202 */     MutableBigInteger mutableBigInteger5 = mutableBigInteger1.divide(mutableBigInteger3, mutableBigInteger4);
/*      */     
/* 2204 */     MutableBigInteger mutableBigInteger6 = mutableBigInteger1;
/*      */     
/* 2206 */     mutableBigInteger1 = mutableBigInteger5;
/* 2207 */     mutableBigInteger5 = mutableBigInteger6;
/*      */     
/* 2209 */     MutableBigInteger mutableBigInteger7 = new MutableBigInteger(mutableBigInteger4);
/* 2210 */     MutableBigInteger mutableBigInteger8 = new MutableBigInteger(1);
/* 2211 */     MutableBigInteger mutableBigInteger9 = new MutableBigInteger();
/*      */     
/* 2213 */     while (!mutableBigInteger1.isOne()) {
/* 2214 */       mutableBigInteger5 = mutableBigInteger3.divide(mutableBigInteger1, mutableBigInteger4);
/*      */       
/* 2216 */       if (mutableBigInteger5.intLen == 0) {
/* 2217 */         throw new ArithmeticException("BigInteger not invertible.");
/*      */       }
/* 2219 */       mutableBigInteger6 = mutableBigInteger5;
/* 2220 */       mutableBigInteger3 = mutableBigInteger6;
/*      */       
/* 2222 */       if (mutableBigInteger4.intLen == 1) {
/* 2223 */         mutableBigInteger7.mul(mutableBigInteger4.value[mutableBigInteger4.offset], mutableBigInteger9);
/*      */       } else {
/* 2225 */         mutableBigInteger4.multiply(mutableBigInteger7, mutableBigInteger9);
/* 2226 */       }  mutableBigInteger6 = mutableBigInteger4;
/* 2227 */       mutableBigInteger4 = mutableBigInteger9;
/* 2228 */       mutableBigInteger9 = mutableBigInteger6;
/* 2229 */       mutableBigInteger8.add(mutableBigInteger4);
/*      */       
/* 2231 */       if (mutableBigInteger3.isOne()) {
/* 2232 */         return mutableBigInteger8;
/*      */       }
/* 2234 */       mutableBigInteger5 = mutableBigInteger1.divide(mutableBigInteger3, mutableBigInteger4);
/*      */       
/* 2236 */       if (mutableBigInteger5.intLen == 0) {
/* 2237 */         throw new ArithmeticException("BigInteger not invertible.");
/*      */       }
/* 2239 */       mutableBigInteger6 = mutableBigInteger1;
/* 2240 */       mutableBigInteger1 = mutableBigInteger5;
/*      */       
/* 2242 */       if (mutableBigInteger4.intLen == 1) {
/* 2243 */         mutableBigInteger8.mul(mutableBigInteger4.value[mutableBigInteger4.offset], mutableBigInteger9);
/*      */       } else {
/* 2245 */         mutableBigInteger4.multiply(mutableBigInteger8, mutableBigInteger9);
/* 2246 */       }  mutableBigInteger6 = mutableBigInteger4; mutableBigInteger4 = mutableBigInteger9; mutableBigInteger9 = mutableBigInteger6;
/*      */       
/* 2248 */       mutableBigInteger7.add(mutableBigInteger4);
/*      */     } 
/* 2250 */     mutableBigInteger2.subtract(mutableBigInteger7);
/* 2251 */     return mutableBigInteger2;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/math/MutableBigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */