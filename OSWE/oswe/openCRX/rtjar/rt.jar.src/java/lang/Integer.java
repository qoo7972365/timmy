/*      */ package java.lang;
/*      */ 
/*      */ import sun.misc.VM;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Integer
/*      */   extends Number
/*      */   implements Comparable<Integer>
/*      */ {
/*      */   public static final int MIN_VALUE = -2147483648;
/*      */   public static final int MAX_VALUE = 2147483647;
/*   72 */   public static final Class<Integer> TYPE = (Class)Class.getPrimitiveClass("int");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   77 */   static final char[] digits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(int paramInt1, int paramInt2) {
/*  131 */     if (paramInt2 < 2 || paramInt2 > 36) {
/*  132 */       paramInt2 = 10;
/*      */     }
/*      */     
/*  135 */     if (paramInt2 == 10) {
/*  136 */       return toString(paramInt1);
/*      */     }
/*      */     
/*  139 */     char[] arrayOfChar = new char[33];
/*  140 */     boolean bool = (paramInt1 < 0) ? true : false;
/*  141 */     byte b = 32;
/*      */     
/*  143 */     if (!bool) {
/*  144 */       paramInt1 = -paramInt1;
/*      */     }
/*      */     
/*  147 */     while (paramInt1 <= -paramInt2) {
/*  148 */       arrayOfChar[b--] = digits[-(paramInt1 % paramInt2)];
/*  149 */       paramInt1 /= paramInt2;
/*      */     } 
/*  151 */     arrayOfChar[b] = digits[-paramInt1];
/*      */     
/*  153 */     if (bool) {
/*  154 */       arrayOfChar[--b] = '-';
/*      */     }
/*      */     
/*  157 */     return new String(arrayOfChar, b, 33 - b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toUnsignedString(int paramInt1, int paramInt2) {
/*  187 */     return Long.toUnsignedString(toUnsignedLong(paramInt1), paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toHexString(int paramInt) {
/*  233 */     return toUnsignedString0(paramInt, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toOctalString(int paramInt) {
/*  271 */     return toUnsignedString0(paramInt, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toBinaryString(int paramInt) {
/*  303 */     return toUnsignedString0(paramInt, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toUnsignedString0(int paramInt1, int paramInt2) {
/*  311 */     int i = 32 - numberOfLeadingZeros(paramInt1);
/*  312 */     int j = Math.max((i + paramInt2 - 1) / paramInt2, 1);
/*  313 */     char[] arrayOfChar = new char[j];
/*      */     
/*  315 */     formatUnsignedInt(paramInt1, paramInt2, arrayOfChar, 0, j);
/*      */ 
/*      */     
/*  318 */     return new String(arrayOfChar, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int formatUnsignedInt(int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) {
/*  331 */     int i = paramInt4;
/*  332 */     int j = 1 << paramInt2;
/*  333 */     int k = j - 1;
/*      */     do {
/*  335 */       paramArrayOfchar[paramInt3 + --i] = digits[paramInt1 & k];
/*  336 */       paramInt1 >>>= paramInt2;
/*  337 */     } while (paramInt1 != 0 && i > 0);
/*      */     
/*  339 */     return i;
/*      */   }
/*      */   
/*  342 */   static final char[] DigitTens = new char[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  355 */   static final char[] DigitOnes = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(int paramInt) {
/*  398 */     if (paramInt == Integer.MIN_VALUE)
/*  399 */       return "-2147483648"; 
/*  400 */     int i = (paramInt < 0) ? (stringSize(-paramInt) + 1) : stringSize(paramInt);
/*  401 */     char[] arrayOfChar = new char[i];
/*  402 */     getChars(paramInt, i, arrayOfChar);
/*  403 */     return new String(arrayOfChar, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toUnsignedString(int paramInt) {
/*  421 */     return Long.toString(toUnsignedLong(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void getChars(int paramInt1, int paramInt2, char[] paramArrayOfchar) {
/*  435 */     int i = paramInt2;
/*  436 */     byte b = 0;
/*      */     
/*  438 */     if (paramInt1 < 0) {
/*  439 */       b = 45;
/*  440 */       paramInt1 = -paramInt1;
/*      */     } 
/*      */ 
/*      */     
/*  444 */     while (paramInt1 >= 65536) {
/*  445 */       int j = paramInt1 / 100;
/*      */       
/*  447 */       int k = paramInt1 - (j << 6) + (j << 5) + (j << 2);
/*  448 */       paramInt1 = j;
/*  449 */       paramArrayOfchar[--i] = DigitOnes[k];
/*  450 */       paramArrayOfchar[--i] = DigitTens[k];
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     do {
/*  456 */       int j = paramInt1 * 52429 >>> 19;
/*  457 */       int k = paramInt1 - (j << 3) + (j << 1);
/*  458 */       paramArrayOfchar[--i] = digits[k];
/*  459 */       paramInt1 = j;
/*  460 */     } while (paramInt1 != 0);
/*      */     
/*  462 */     if (b != 0) {
/*  463 */       paramArrayOfchar[--i] = b;
/*      */     }
/*      */   }
/*      */   
/*  467 */   static final int[] sizeTable = new int[] { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };
/*      */   private final int value;
/*      */   public static final int SIZE = 32;
/*      */   
/*      */   static int stringSize(int paramInt) {
/*  472 */     for (byte b = 0;; b++) {
/*  473 */       if (paramInt <= sizeTable[b]) {
/*  474 */         return b + 1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int BYTES = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 1360826667806852920L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseInt(String paramString, int paramInt) throws NumberFormatException {
/*  541 */     if (paramString == null) {
/*  542 */       throw new NumberFormatException("null");
/*      */     }
/*      */     
/*  545 */     if (paramInt < 2) {
/*  546 */       throw new NumberFormatException("radix " + paramInt + " less than Character.MIN_RADIX");
/*      */     }
/*      */ 
/*      */     
/*  550 */     if (paramInt > 36) {
/*  551 */       throw new NumberFormatException("radix " + paramInt + " greater than Character.MAX_RADIX");
/*      */     }
/*      */ 
/*      */     
/*  555 */     int i = 0;
/*  556 */     boolean bool = false;
/*  557 */     byte b = 0; int j = paramString.length();
/*  558 */     int k = -2147483647;
/*      */ 
/*      */ 
/*      */     
/*  562 */     if (j > 0) {
/*  563 */       char c = paramString.charAt(0);
/*  564 */       if (c < '0') {
/*  565 */         if (c == '-') {
/*  566 */           bool = true;
/*  567 */           k = Integer.MIN_VALUE;
/*  568 */         } else if (c != '+') {
/*  569 */           throw NumberFormatException.forInputString(paramString);
/*      */         } 
/*  571 */         if (j == 1)
/*  572 */           throw NumberFormatException.forInputString(paramString); 
/*  573 */         b++;
/*      */       } 
/*  575 */       int m = k / paramInt;
/*  576 */       while (b < j) {
/*      */         
/*  578 */         int n = Character.digit(paramString.charAt(b++), paramInt);
/*  579 */         if (n < 0) {
/*  580 */           throw NumberFormatException.forInputString(paramString);
/*      */         }
/*  582 */         if (i < m) {
/*  583 */           throw NumberFormatException.forInputString(paramString);
/*      */         }
/*  585 */         i *= paramInt;
/*  586 */         if (i < k + n) {
/*  587 */           throw NumberFormatException.forInputString(paramString);
/*      */         }
/*  589 */         i -= n;
/*      */       } 
/*      */     } else {
/*  592 */       throw NumberFormatException.forInputString(paramString);
/*      */     } 
/*  594 */     return bool ? i : -i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseInt(String paramString) throws NumberFormatException {
/*  615 */     return parseInt(paramString, 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseUnsignedInt(String paramString, int paramInt) throws NumberFormatException {
/*  663 */     if (paramString == null) {
/*  664 */       throw new NumberFormatException("null");
/*      */     }
/*      */     
/*  667 */     int i = paramString.length();
/*  668 */     if (i > 0) {
/*  669 */       char c = paramString.charAt(0);
/*  670 */       if (c == '-') {
/*  671 */         throw new NumberFormatException(
/*  672 */             String.format("Illegal leading minus sign on unsigned string %s.", new Object[] { paramString }));
/*      */       }
/*      */       
/*  675 */       if (i <= 5 || (paramInt == 10 && i <= 9))
/*      */       {
/*  677 */         return parseInt(paramString, paramInt);
/*      */       }
/*  679 */       long l = Long.parseLong(paramString, paramInt);
/*  680 */       if ((l & 0xFFFFFFFF00000000L) == 0L) {
/*  681 */         return (int)l;
/*      */       }
/*  683 */       throw new NumberFormatException(
/*  684 */           String.format("String value %s exceeds range of unsigned int.", new Object[] { paramString }));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  690 */     throw NumberFormatException.forInputString(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseUnsignedInt(String paramString) throws NumberFormatException {
/*  711 */     return parseUnsignedInt(paramString, 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer valueOf(String paramString, int paramInt) throws NumberFormatException {
/*  740 */     return valueOf(parseInt(paramString, paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer valueOf(String paramString) throws NumberFormatException {
/*  766 */     return valueOf(parseInt(paramString, 10));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class IntegerCache
/*      */   {
/*      */     static final int low = -128;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int high;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*  787 */       int i = 127;
/*      */       
/*  789 */       String str = VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
/*  790 */       if (str != null) {
/*      */         try {
/*  792 */           int j = Integer.parseInt(str);
/*  793 */           j = Math.max(j, 127);
/*      */           
/*  795 */           i = Math.min(j, 2147483518);
/*  796 */         } catch (NumberFormatException numberFormatException) {}
/*      */       }
/*      */ 
/*      */       
/*  800 */       high = i;
/*      */     }
/*  802 */     static final Integer[] cache = new Integer[high - -128 + 1]; static {
/*  803 */       byte b = -128;
/*  804 */       for (byte b1 = 0; b1 < cache.length; b1++) {
/*  805 */         cache[b1] = new Integer(b++);
/*      */       }
/*      */       
/*  808 */       assert high >= 127;
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
/*      */   public static Integer valueOf(int paramInt) {
/*  830 */     if (paramInt >= -128 && paramInt <= IntegerCache.high)
/*  831 */       return IntegerCache.cache[paramInt + 128]; 
/*  832 */     return new Integer(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer(int paramInt) {
/*  850 */     this.value = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer(String paramString) throws NumberFormatException {
/*  867 */     this.value = parseInt(paramString, 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte byteValue() {
/*  876 */     return (byte)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short shortValue() {
/*  885 */     return (short)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int intValue() {
/*  893 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long longValue() {
/*  903 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float floatValue() {
/*  912 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double doubleValue() {
/*  921 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
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
/*  935 */     return toString(this.value);
/*      */   }
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
/*  947 */     return hashCode(this.value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hashCode(int paramInt) {
/*  960 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
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
/*  974 */     if (paramObject instanceof Integer) {
/*  975 */       return (this.value == ((Integer)paramObject).intValue());
/*      */     }
/*  977 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer getInteger(String paramString) {
/* 1011 */     return getInteger(paramString, (Integer)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer getInteger(String paramString, int paramInt) {
/* 1057 */     Integer integer = getInteger(paramString, (Integer)null);
/* 1058 */     return (integer == null) ? valueOf(paramInt) : integer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer getInteger(String paramString, Integer paramInteger) {
/* 1099 */     String str = null;
/*      */     try {
/* 1101 */       str = System.getProperty(paramString);
/* 1102 */     } catch (IllegalArgumentException|NullPointerException illegalArgumentException) {}
/*      */     
/* 1104 */     if (str != null) {
/*      */       try {
/* 1106 */         return decode(str);
/* 1107 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */     
/* 1110 */     return paramInteger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer decode(String paramString) throws NumberFormatException {
/*      */     Integer integer;
/* 1156 */     byte b1 = 10;
/* 1157 */     byte b2 = 0;
/* 1158 */     boolean bool = false;
/*      */ 
/*      */     
/* 1161 */     if (paramString.length() == 0)
/* 1162 */       throw new NumberFormatException("Zero length string"); 
/* 1163 */     char c = paramString.charAt(0);
/*      */     
/* 1165 */     if (c == '-') {
/* 1166 */       bool = true;
/* 1167 */       b2++;
/* 1168 */     } else if (c == '+') {
/* 1169 */       b2++;
/*      */     } 
/*      */     
/* 1172 */     if (paramString.startsWith("0x", b2) || paramString.startsWith("0X", b2)) {
/* 1173 */       b2 += 2;
/* 1174 */       b1 = 16;
/*      */     }
/* 1176 */     else if (paramString.startsWith("#", b2)) {
/* 1177 */       b2++;
/* 1178 */       b1 = 16;
/*      */     }
/* 1180 */     else if (paramString.startsWith("0", b2) && paramString.length() > 1 + b2) {
/* 1181 */       b2++;
/* 1182 */       b1 = 8;
/*      */     } 
/*      */     
/* 1185 */     if (paramString.startsWith("-", b2) || paramString.startsWith("+", b2)) {
/* 1186 */       throw new NumberFormatException("Sign character in wrong position");
/*      */     }
/*      */     try {
/* 1189 */       integer = valueOf(paramString.substring(b2), b1);
/* 1190 */       integer = bool ? valueOf(-integer.intValue()) : integer;
/* 1191 */     } catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1196 */       String str = bool ? ("-" + paramString.substring(b2)) : paramString.substring(b2);
/* 1197 */       integer = valueOf(str, b1);
/*      */     } 
/* 1199 */     return integer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(Integer paramInteger) {
/* 1216 */     return compare(this.value, paramInteger.value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(int paramInt1, int paramInt2) {
/* 1234 */     return (paramInt1 < paramInt2) ? -1 : ((paramInt1 == paramInt2) ? 0 : 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compareUnsigned(int paramInt1, int paramInt2) {
/* 1250 */     return compare(paramInt1 + Integer.MIN_VALUE, paramInt2 + Integer.MIN_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long toUnsignedLong(int paramInt) {
/* 1271 */     return paramInt & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int divideUnsigned(int paramInt1, int paramInt2) {
/* 1294 */     return (int)(toUnsignedLong(paramInt1) / toUnsignedLong(paramInt2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int remainderUnsigned(int paramInt1, int paramInt2) {
/* 1311 */     return (int)(toUnsignedLong(paramInt1) % toUnsignedLong(paramInt2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int highestOneBit(int paramInt) {
/* 1348 */     paramInt |= paramInt >> 1;
/* 1349 */     paramInt |= paramInt >> 2;
/* 1350 */     paramInt |= paramInt >> 4;
/* 1351 */     paramInt |= paramInt >> 8;
/* 1352 */     paramInt |= paramInt >> 16;
/* 1353 */     return paramInt - (paramInt >>> 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int lowestOneBit(int paramInt) {
/* 1371 */     return paramInt & -paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int numberOfLeadingZeros(int paramInt) {
/* 1397 */     if (paramInt == 0)
/* 1398 */       return 32; 
/* 1399 */     int i = 1;
/* 1400 */     if (paramInt >>> 16 == 0) { i += true; paramInt <<= 16; }
/* 1401 */      if (paramInt >>> 24 == 0) { i += true; paramInt <<= 8; }
/* 1402 */      if (paramInt >>> 28 == 0) { i += true; paramInt <<= 4; }
/* 1403 */      if (paramInt >>> 30 == 0) { i += true; paramInt <<= 2; }
/* 1404 */      i -= paramInt >>> 31;
/* 1405 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int numberOfTrailingZeros(int paramInt) {
/* 1425 */     if (paramInt == 0) return 32; 
/* 1426 */     int j = 31;
/* 1427 */     int i = paramInt << 16; if (i != 0) { j -= 16; paramInt = i; }
/* 1428 */      i = paramInt << 8; if (i != 0) { j -= 8; paramInt = i; }
/* 1429 */      i = paramInt << 4; if (i != 0) { j -= 4; paramInt = i; }
/* 1430 */      i = paramInt << 2; if (i != 0) { j -= 2; paramInt = i; }
/* 1431 */      return j - (paramInt << 1 >>> 31);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int bitCount(int paramInt) {
/* 1446 */     paramInt -= paramInt >>> 1 & 0x55555555;
/* 1447 */     paramInt = (paramInt & 0x33333333) + (paramInt >>> 2 & 0x33333333);
/* 1448 */     paramInt = paramInt + (paramInt >>> 4) & 0xF0F0F0F;
/* 1449 */     paramInt += paramInt >>> 8;
/* 1450 */     paramInt += paramInt >>> 16;
/* 1451 */     return paramInt & 0x3F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int rotateLeft(int paramInt1, int paramInt2) {
/* 1475 */     return paramInt1 << paramInt2 | paramInt1 >>> -paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int rotateRight(int paramInt1, int paramInt2) {
/* 1499 */     return paramInt1 >>> paramInt2 | paramInt1 << -paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int reverse(int paramInt) {
/* 1514 */     paramInt = (paramInt & 0x55555555) << 1 | paramInt >>> 1 & 0x55555555;
/* 1515 */     paramInt = (paramInt & 0x33333333) << 2 | paramInt >>> 2 & 0x33333333;
/* 1516 */     paramInt = (paramInt & 0xF0F0F0F) << 4 | paramInt >>> 4 & 0xF0F0F0F;
/* 1517 */     paramInt = paramInt << 24 | (paramInt & 0xFF00) << 8 | paramInt >>> 8 & 0xFF00 | paramInt >>> 24;
/*      */     
/* 1519 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int signum(int paramInt) {
/* 1533 */     return paramInt >> 31 | -paramInt >>> 31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int reverseBytes(int paramInt) {
/* 1546 */     return paramInt >>> 24 | paramInt >> 8 & 0xFF00 | paramInt << 8 & 0xFF0000 | paramInt << 24;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int sum(int paramInt1, int paramInt2) {
/* 1562 */     return paramInt1 + paramInt2;
/*      */   }
/*      */ 
/*      */ 
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
/* 1576 */     return Math.max(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
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
/* 1590 */     return Math.min(paramInt1, paramInt2);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Integer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */