/*     */ package java.text;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.math.RoundingMode;
/*     */ import sun.misc.FloatingDecimal;
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
/*     */ final class DigitList
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int MAX_COUNT = 19;
/* 103 */   public int decimalAt = 0;
/* 104 */   public int count = 0;
/* 105 */   public char[] digits = new char[19];
/*     */   
/*     */   private char[] data;
/* 108 */   private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
/*     */ 
/*     */   
/*     */   private boolean isNegative = false;
/*     */ 
/*     */   
/*     */   boolean isZero() {
/* 115 */     for (byte b = 0; b < this.count; b++) {
/* 116 */       if (this.digits[b] != '0') {
/* 117 */         return false;
/*     */       }
/*     */     } 
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setRoundingMode(RoundingMode paramRoundingMode) {
/* 127 */     this.roundingMode = paramRoundingMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 138 */     this.decimalAt = 0;
/* 139 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(char paramChar) {
/* 146 */     if (this.count == this.digits.length) {
/* 147 */       char[] arrayOfChar = new char[this.count + 100];
/* 148 */       System.arraycopy(this.digits, 0, arrayOfChar, 0, this.count);
/* 149 */       this.digits = arrayOfChar;
/*     */     } 
/* 151 */     this.digits[this.count++] = paramChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getDouble() {
/* 160 */     if (this.count == 0) {
/* 161 */       return 0.0D;
/*     */     }
/*     */     
/* 164 */     StringBuffer stringBuffer = getStringBuffer();
/* 165 */     stringBuffer.append('.');
/* 166 */     stringBuffer.append(this.digits, 0, this.count);
/* 167 */     stringBuffer.append('E');
/* 168 */     stringBuffer.append(this.decimalAt);
/* 169 */     return Double.parseDouble(stringBuffer.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getLong() {
/* 179 */     if (this.count == 0) {
/* 180 */       return 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     if (isLongMIN_VALUE()) {
/* 187 */       return Long.MIN_VALUE;
/*     */     }
/*     */     
/* 190 */     StringBuffer stringBuffer = getStringBuffer();
/* 191 */     stringBuffer.append(this.digits, 0, this.count);
/* 192 */     for (int i = this.count; i < this.decimalAt; i++) {
/* 193 */       stringBuffer.append('0');
/*     */     }
/* 195 */     return Long.parseLong(stringBuffer.toString());
/*     */   }
/*     */   
/*     */   public final BigDecimal getBigDecimal() {
/* 199 */     if (this.count == 0) {
/* 200 */       if (this.decimalAt == 0) {
/* 201 */         return BigDecimal.ZERO;
/*     */       }
/* 203 */       return new BigDecimal("0E" + this.decimalAt);
/*     */     } 
/*     */ 
/*     */     
/* 207 */     if (this.decimalAt == this.count) {
/* 208 */       return new BigDecimal(this.digits, 0, this.count);
/*     */     }
/* 210 */     return (new BigDecimal(this.digits, 0, this.count)).scaleByPowerOfTen(this.decimalAt - this.count);
/*     */   }
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
/*     */   boolean fitsIntoLong(boolean paramBoolean1, boolean paramBoolean2) {
/* 230 */     while (this.count > 0 && this.digits[this.count - 1] == '0') {
/* 231 */       this.count--;
/*     */     }
/*     */     
/* 234 */     if (this.count == 0)
/*     */     {
/*     */       
/* 237 */       return (paramBoolean1 || paramBoolean2);
/*     */     }
/*     */     
/* 240 */     if (this.decimalAt < this.count || this.decimalAt > 19) {
/* 241 */       return false;
/*     */     }
/*     */     
/* 244 */     if (this.decimalAt < 19) return true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     for (byte b = 0; b < this.count; b++) {
/* 250 */       char c1 = this.digits[b], c2 = LONG_MIN_REP[b];
/* 251 */       if (c1 > c2) return false; 
/* 252 */       if (c1 < c2) return true;
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 257 */     if (this.count < this.decimalAt) return true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     return !paramBoolean1;
/*     */   }
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
/*     */   final void set(boolean paramBoolean, double paramDouble, int paramInt) {
/* 275 */     set(paramBoolean, paramDouble, paramInt, true);
/*     */   }
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
/*     */   final void set(boolean paramBoolean1, double paramDouble, int paramInt, boolean paramBoolean2) {
/* 291 */     FloatingDecimal.BinaryToASCIIConverter binaryToASCIIConverter = FloatingDecimal.getBinaryToASCIIConverter(paramDouble);
/* 292 */     boolean bool1 = binaryToASCIIConverter.digitsRoundedUp();
/* 293 */     boolean bool2 = binaryToASCIIConverter.decimalDigitsExact();
/* 294 */     assert !binaryToASCIIConverter.isExceptional();
/* 295 */     String str = binaryToASCIIConverter.toJavaFormatString();
/*     */     
/* 297 */     set(paramBoolean1, str, bool1, bool2, paramInt, paramBoolean2);
/*     */   }
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
/*     */   private void set(boolean paramBoolean1, String paramString, boolean paramBoolean2, boolean paramBoolean3, int paramInt, boolean paramBoolean4) {
/* 313 */     this.isNegative = paramBoolean1;
/* 314 */     int i = paramString.length();
/* 315 */     char[] arrayOfChar = getDataChars(i);
/* 316 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */     
/* 318 */     this.decimalAt = -1;
/* 319 */     this.count = 0;
/* 320 */     int j = 0;
/*     */ 
/*     */     
/* 323 */     byte b1 = 0;
/* 324 */     boolean bool = false;
/*     */     
/* 326 */     for (byte b2 = 0; b2 < i; ) {
/* 327 */       char c = arrayOfChar[b2++];
/* 328 */       if (c == '.') {
/* 329 */         this.decimalAt = this.count; continue;
/* 330 */       }  if (c == 'e' || c == 'E') {
/* 331 */         j = parseInt(arrayOfChar, b2, i);
/*     */         break;
/*     */       } 
/* 334 */       if (!bool) {
/* 335 */         bool = (c != '0') ? true : false;
/* 336 */         if (!bool && this.decimalAt != -1)
/* 337 */           b1++; 
/*     */       } 
/* 339 */       if (bool) {
/* 340 */         this.digits[this.count++] = c;
/*     */       }
/*     */     } 
/*     */     
/* 344 */     if (this.decimalAt == -1) {
/* 345 */       this.decimalAt = this.count;
/*     */     }
/* 347 */     if (bool) {
/* 348 */       this.decimalAt += j - b1;
/*     */     }
/*     */     
/* 351 */     if (paramBoolean4) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       if (-this.decimalAt > paramInt) {
/*     */ 
/*     */         
/* 360 */         this.count = 0; return;
/*     */       } 
/* 362 */       if (-this.decimalAt == paramInt) {
/*     */ 
/*     */         
/* 365 */         if (shouldRoundUp(0, paramBoolean2, paramBoolean3)) {
/* 366 */           this.count = 1;
/* 367 */           this.decimalAt++;
/* 368 */           this.digits[0] = '1';
/*     */         } else {
/* 370 */           this.count = 0;
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 378 */     while (this.count > 1 && this.digits[this.count - 1] == '0') {
/* 379 */       this.count--;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 384 */     round(paramBoolean4 ? (paramInt + this.decimalAt) : paramInt, paramBoolean2, paramBoolean3);
/*     */   }
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
/*     */   private final void round(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 403 */     if (paramInt >= 0 && paramInt < this.count) {
/* 404 */       if (shouldRoundUp(paramInt, paramBoolean1, paramBoolean2)) {
/*     */ 
/*     */         
/*     */         do {
/*     */           
/* 409 */           paramInt--;
/* 410 */           if (paramInt < 0) {
/*     */ 
/*     */             
/* 413 */             this.digits[0] = '1';
/* 414 */             this.decimalAt++;
/* 415 */             paramInt = 0;
/*     */             
/*     */             break;
/*     */           } 
/* 419 */           this.digits[paramInt] = (char)(this.digits[paramInt] + 1);
/* 420 */         } while (this.digits[paramInt] > '9');
/*     */ 
/*     */         
/* 423 */         paramInt++;
/*     */       } 
/* 425 */       this.count = paramInt;
/*     */ 
/*     */       
/* 428 */       while (this.count > 1 && this.digits[this.count - 1] == '0') {
/* 429 */         this.count--;
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   private boolean shouldRoundUp(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 456 */     if (paramInt < this.count)
/*     */     { int i;
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
/* 503 */       switch (this.roundingMode)
/*     */       { case UP:
/* 505 */           for (i = paramInt; i < this.count; i++) {
/* 506 */             if (this.digits[i] != '0') {
/* 507 */               return true;
/*     */             }
/*     */           } 
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
/*     */         case DOWN:
/* 599 */           return false;case CEILING: for (i = paramInt; i < this.count; i++) { if (this.digits[i] != '0') return !this.isNegative;  } case FLOOR: for (i = paramInt; i < this.count; i++) { if (this.digits[i] != '0') return this.isNegative;  } case HALF_UP: case HALF_DOWN: if (this.digits[paramInt] > '5') return true;  if (this.digits[paramInt] == '5') { if (paramInt != this.count - 1)
/*     */               return true;  if (paramBoolean2)
/*     */               return (this.roundingMode == RoundingMode.HALF_UP);  return !paramBoolean1; } case HALF_EVEN: if (this.digits[paramInt] > '5')
/*     */             return true;  if (this.digits[paramInt] == '5') { if (paramInt == this.count - 1) { if (paramBoolean1)
/*     */                 return false;  if (!paramBoolean2)
/*     */                 return true;  return (paramInt > 0 && this.digits[paramInt - 1] % 2 != 0); }  for (i = paramInt + 1; i < this.count; i++) { if (this.digits[i] != '0')
/*     */                 return true;  }  } case UNNECESSARY: for (i = paramInt; i < this.count; i++) { if (this.digits[i] != '0')
/* 606 */               throw new ArithmeticException("Rounding needed with the rounding mode being set to RoundingMode.UNNECESSARY");  }  }  assert false; }  } final void set(boolean paramBoolean, long paramLong) { set(paramBoolean, paramLong, 0); }
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
/*     */   final void set(boolean paramBoolean, long paramLong, int paramInt) {
/* 619 */     this.isNegative = paramBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 627 */     if (paramLong <= 0L) {
/* 628 */       if (paramLong == Long.MIN_VALUE) {
/* 629 */         this.decimalAt = this.count = 19;
/* 630 */         System.arraycopy(LONG_MIN_REP, 0, this.digits, 0, this.count);
/*     */       } else {
/* 632 */         this.decimalAt = this.count = 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 637 */       byte b1 = 19;
/*     */       
/* 639 */       while (paramLong > 0L) {
/* 640 */         this.digits[--b1] = (char)(int)(48L + paramLong % 10L);
/* 641 */         paramLong /= 10L;
/*     */       } 
/* 643 */       this.decimalAt = 19 - b1;
/*     */       
/*     */       byte b2;
/* 646 */       for (b2 = 18; this.digits[b2] == '0'; b2--);
/*     */       
/* 648 */       this.count = b2 - b1 + 1;
/* 649 */       System.arraycopy(this.digits, b1, this.digits, 0, this.count);
/*     */     } 
/* 651 */     if (paramInt > 0) round(paramInt, false, true);
/*     */   
/*     */   }
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
/*     */   final void set(boolean paramBoolean1, BigDecimal paramBigDecimal, int paramInt, boolean paramBoolean2) {
/* 665 */     String str = paramBigDecimal.toString();
/* 666 */     extendDigits(str.length());
/*     */     
/* 668 */     set(paramBoolean1, str, false, true, paramInt, paramBoolean2);
/*     */   }
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
/*     */   final void set(boolean paramBoolean, BigInteger paramBigInteger, int paramInt) {
/* 682 */     this.isNegative = paramBoolean;
/* 683 */     String str = paramBigInteger.toString();
/* 684 */     int i = str.length();
/* 685 */     extendDigits(i);
/* 686 */     str.getChars(0, i, this.digits, 0);
/*     */     
/* 688 */     this.decimalAt = i;
/*     */     int j;
/* 690 */     for (j = i - 1; j >= 0 && this.digits[j] == '0'; j--);
/*     */     
/* 692 */     this.count = j + 1;
/*     */     
/* 694 */     if (paramInt > 0) {
/* 695 */       round(paramInt, false, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 703 */     if (this == paramObject)
/* 704 */       return true; 
/* 705 */     if (!(paramObject instanceof DigitList))
/* 706 */       return false; 
/* 707 */     DigitList digitList = (DigitList)paramObject;
/* 708 */     if (this.count != digitList.count || this.decimalAt != digitList.decimalAt)
/*     */     {
/* 710 */       return false; } 
/* 711 */     for (byte b = 0; b < this.count; b++) {
/* 712 */       if (this.digits[b] != digitList.digits[b])
/* 713 */         return false; 
/* 714 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 721 */     int i = this.decimalAt;
/*     */     
/* 723 */     for (byte b = 0; b < this.count; b++) {
/* 724 */       i = i * 37 + this.digits[b];
/*     */     }
/*     */     
/* 727 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 736 */       DigitList digitList = (DigitList)super.clone();
/* 737 */       char[] arrayOfChar = new char[this.digits.length];
/* 738 */       System.arraycopy(this.digits, 0, arrayOfChar, 0, this.digits.length);
/* 739 */       digitList.digits = arrayOfChar;
/* 740 */       digitList.tempBuffer = null;
/* 741 */       return digitList;
/* 742 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 743 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isLongMIN_VALUE() {
/* 752 */     if (this.decimalAt != this.count || this.count != 19) {
/* 753 */       return false;
/*     */     }
/*     */     
/* 756 */     for (byte b = 0; b < this.count; b++) {
/* 757 */       if (this.digits[b] != LONG_MIN_REP[b]) return false;
/*     */     
/*     */     } 
/* 760 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int parseInt(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 765 */     boolean bool = true; char c;
/* 766 */     if ((c = paramArrayOfchar[paramInt1]) == '-') {
/* 767 */       bool = false;
/* 768 */       paramInt1++;
/* 769 */     } else if (c == '+') {
/* 770 */       paramInt1++;
/*     */     } 
/*     */     
/* 773 */     int i = 0;
/* 774 */     while (paramInt1 < paramInt2) {
/* 775 */       c = paramArrayOfchar[paramInt1++];
/* 776 */       if (c >= '0' && c <= '9') {
/* 777 */         i = i * 10 + c - 48;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 782 */     return bool ? i : -i;
/*     */   }
/*     */ 
/*     */   
/* 786 */   private static final char[] LONG_MIN_REP = "9223372036854775808".toCharArray(); private StringBuffer tempBuffer;
/*     */   
/*     */   public String toString() {
/* 789 */     if (isZero()) {
/* 790 */       return "0";
/*     */     }
/* 792 */     StringBuffer stringBuffer = getStringBuffer();
/* 793 */     stringBuffer.append("0.");
/* 794 */     stringBuffer.append(this.digits, 0, this.count);
/* 795 */     stringBuffer.append("x10^");
/* 796 */     stringBuffer.append(this.decimalAt);
/* 797 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuffer getStringBuffer() {
/* 803 */     if (this.tempBuffer == null) {
/* 804 */       this.tempBuffer = new StringBuffer(19);
/*     */     } else {
/* 806 */       this.tempBuffer.setLength(0);
/*     */     } 
/* 808 */     return this.tempBuffer;
/*     */   }
/*     */   
/*     */   private void extendDigits(int paramInt) {
/* 812 */     if (paramInt > this.digits.length) {
/* 813 */       this.digits = new char[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   private final char[] getDataChars(int paramInt) {
/* 818 */     if (this.data == null || this.data.length < paramInt) {
/* 819 */       this.data = new char[paramInt];
/*     */     }
/* 821 */     return this.data;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/DigitList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */