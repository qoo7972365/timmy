/*     */ package sun.net.idn;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import sun.text.normalizer.UCharacter;
/*     */ import sun.text.normalizer.UTF16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Punycode
/*     */ {
/*     */   private static final int BASE = 36;
/*     */   private static final int TMIN = 1;
/*     */   private static final int TMAX = 26;
/*     */   private static final int SKEW = 38;
/*     */   private static final int DAMP = 700;
/*     */   private static final int INITIAL_BIAS = 72;
/*     */   private static final int INITIAL_N = 128;
/*     */   private static final int HYPHEN = 45;
/*     */   private static final int DELIMITER = 45;
/*     */   private static final int ZERO = 48;
/*     */   private static final int NINE = 57;
/*     */   private static final int SMALL_A = 97;
/*     */   private static final int SMALL_Z = 122;
/*     */   private static final int CAPITAL_A = 65;
/*     */   private static final int CAPITAL_Z = 90;
/*     */   private static final int MAX_CP_COUNT = 256;
/*     */   private static final int UINT_MAGIC = -2147483648;
/*     */   private static final long ULONG_MAGIC = -9223372036854775808L;
/*     */   
/*     */   private static int adaptBias(int paramInt1, int paramInt2, boolean paramBoolean) {
/*  82 */     if (paramBoolean) {
/*  83 */       paramInt1 /= 700;
/*     */     } else {
/*  85 */       paramInt1 /= 2;
/*     */     } 
/*  87 */     paramInt1 += paramInt1 / paramInt2;
/*     */     
/*  89 */     byte b = 0;
/*  90 */     for (; paramInt1 > 455; b += true) {
/*  91 */       paramInt1 /= 35;
/*     */     }
/*     */     
/*  94 */     return b + 36 * paramInt1 / (paramInt1 + 38);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   static final int[] basicToDigit = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char asciiCaseMap(char paramChar, boolean paramBoolean) {
/* 129 */     if (paramBoolean) {
/* 130 */       if ('a' <= paramChar && paramChar <= 'z') {
/* 131 */         paramChar = (char)(paramChar - 32);
/*     */       }
/*     */     }
/* 134 */     else if ('A' <= paramChar && paramChar <= 'Z') {
/* 135 */       paramChar = (char)(paramChar + 32);
/*     */     } 
/*     */     
/* 138 */     return paramChar;
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
/*     */   private static char digitToBasic(int paramInt, boolean paramBoolean) {
/* 150 */     if (paramInt < 26) {
/* 151 */       if (paramBoolean) {
/* 152 */         return (char)(65 + paramInt);
/*     */       }
/* 154 */       return (char)(97 + paramInt);
/*     */     } 
/*     */     
/* 157 */     return (char)(22 + paramInt);
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
/*     */   public static StringBuffer encode(StringBuffer paramStringBuffer, boolean[] paramArrayOfboolean) throws ParseException {
/* 172 */     int[] arrayOfInt = new int[256];
/*     */ 
/*     */     
/* 175 */     int m = paramStringBuffer.length();
/* 176 */     char c = 'Ā';
/* 177 */     char[] arrayOfChar = new char[c];
/* 178 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     byte b3 = 0, b5 = b3;
/*     */     byte b4;
/* 185 */     for (b4 = 0; b4 < m; b4++) {
/* 186 */       if (b5 == 'Ā')
/*     */       {
/* 188 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 190 */       char c1 = paramStringBuffer.charAt(b4);
/* 191 */       if (isBasic(c1)) {
/* 192 */         if (b3 < c) {
/* 193 */           arrayOfInt[b5++] = 0;
/* 194 */           arrayOfChar[b3] = (paramArrayOfboolean != null) ? 
/*     */             
/* 196 */             asciiCaseMap(c1, paramArrayOfboolean[b4]) : c1;
/*     */         } 
/*     */         
/* 199 */         b3++;
/*     */       } else {
/* 201 */         int n = ((paramArrayOfboolean != null && paramArrayOfboolean[b4]) ? 1 : 0) << 31;
/* 202 */         if (!UTF16.isSurrogate(c1))
/* 203 */         { n |= c1; }
/* 204 */         else { char c2; if (UTF16.isLeadSurrogate(c1) && b4 + 1 < m && UTF16.isTrailSurrogate(c2 = paramStringBuffer.charAt(b4 + 1))) {
/* 205 */             b4++;
/*     */             
/* 207 */             n |= UCharacter.getCodePoint(c1, c2);
/*     */           } else {
/*     */             
/* 210 */             throw new ParseException("Illegal char found", -1);
/*     */           }  }
/* 212 */          arrayOfInt[b5++] = n;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 217 */     byte b2 = b3;
/* 218 */     if (b2 > 0) {
/* 219 */       if (b3 < c) {
/* 220 */         arrayOfChar[b3] = '-';
/*     */       }
/* 222 */       b3++;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     int i = 128;
/* 233 */     int j = 0;
/* 234 */     int k = 72;
/*     */ 
/*     */     
/* 237 */     for (byte b1 = b2; b1 < b5; ) {
/*     */       int n;
/*     */ 
/*     */ 
/*     */       
/* 242 */       for (n = Integer.MAX_VALUE, b4 = 0; b4 < b5; b4++) {
/* 243 */         int i1 = arrayOfInt[b4] & Integer.MAX_VALUE;
/* 244 */         if (i <= i1 && i1 < n) {
/* 245 */           n = i1;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 253 */       if (n - i > (2147483391 - j) / (b1 + 1)) {
/* 254 */         throw new RuntimeException("Internal program error");
/*     */       }
/* 256 */       j += (n - i) * (b1 + 1);
/* 257 */       i = n;
/*     */ 
/*     */       
/* 260 */       for (b4 = 0; b4 < b5; b4++) {
/* 261 */         int i1 = arrayOfInt[b4] & Integer.MAX_VALUE;
/* 262 */         if (i1 < i) {
/* 263 */           j++;
/* 264 */         } else if (i1 == i) {
/*     */           byte b;
/* 266 */           for (i1 = j, b = 36;; b += 36) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 278 */             int i2 = b - k;
/* 279 */             if (i2 < 1) {
/* 280 */               i2 = 1;
/* 281 */             } else if (b >= k + 26) {
/* 282 */               i2 = 26;
/*     */             } 
/*     */             
/* 285 */             if (i1 < i2) {
/*     */               break;
/*     */             }
/*     */             
/* 289 */             if (b3 < c) {
/* 290 */               arrayOfChar[b3++] = digitToBasic(i2 + (i1 - i2) % (36 - i2), false);
/*     */             }
/* 292 */             i1 = (i1 - i2) / (36 - i2);
/*     */           } 
/*     */           
/* 295 */           if (b3 < c) {
/* 296 */             arrayOfChar[b3++] = digitToBasic(i1, (arrayOfInt[b4] < 0));
/*     */           }
/* 298 */           k = adaptBias(j, b1 + 1, (b1 == b2));
/* 299 */           j = 0;
/* 300 */           b1++;
/*     */         } 
/*     */       } 
/*     */       
/* 304 */       j++;
/* 305 */       i++;
/*     */     } 
/*     */     
/* 308 */     return stringBuffer.append(arrayOfChar, 0, b3);
/*     */   }
/*     */   
/*     */   private static boolean isBasic(int paramInt) {
/* 312 */     return (paramInt < 128);
/*     */   }
/*     */   
/*     */   private static boolean isBasicUpperCase(int paramInt) {
/* 316 */     return (65 <= paramInt && paramInt <= 90);
/*     */   }
/*     */   
/*     */   private static boolean isSurrogate(int paramInt) {
/* 320 */     return ((paramInt & 0xFFFFF800) == 55296);
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
/*     */   public static StringBuffer decode(StringBuffer paramStringBuffer, boolean[] paramArrayOfboolean) throws ParseException {
/* 333 */     int i = paramStringBuffer.length();
/* 334 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */     
/* 338 */     char c = 'Ā';
/* 339 */     char[] arrayOfChar = new char[c];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     int i2 = i; do {  } while (i2 > 0 && 
/* 350 */       paramStringBuffer.charAt(--i2) != '-');
/*     */ 
/*     */ 
/*     */     
/* 354 */     int i3 = i2, i1 = i3, k = i1;
/*     */     
/* 356 */     while (i2 > 0) {
/* 357 */       char c1 = paramStringBuffer.charAt(--i2);
/* 358 */       if (!isBasic(c1)) {
/* 359 */         throw new ParseException("Illegal char found", -1);
/*     */       }
/*     */       
/* 362 */       if (i2 < c) {
/* 363 */         arrayOfChar[i2] = c1;
/*     */         
/* 365 */         if (paramArrayOfboolean != null) {
/* 366 */           paramArrayOfboolean[i2] = isBasicUpperCase(c1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 372 */     int j = 128;
/* 373 */     int m = 0;
/* 374 */     int n = 72;
/* 375 */     int i4 = 1000000000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     for (byte b = (i1 > 0) ? (i1 + 1) : 0; b < i; ) {
/*     */       int i5, i6;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       byte b1;
/*     */ 
/*     */ 
/*     */       
/* 392 */       for (i5 = m, i6 = 1, b1 = 36;; b1 += 36) {
/* 393 */         if (b >= i) {
/* 394 */           throw new ParseException("Illegal char found", -1);
/*     */         }
/*     */         
/* 397 */         int i8 = basicToDigit[(byte)paramStringBuffer.charAt(b++)];
/* 398 */         if (i8 < 0) {
/* 399 */           throw new ParseException("Invalid char found", -1);
/*     */         }
/* 401 */         if (i8 > (Integer.MAX_VALUE - m) / i6)
/*     */         {
/* 403 */           throw new ParseException("Illegal char found", -1);
/*     */         }
/*     */         
/* 406 */         m += i8 * i6;
/* 407 */         int i9 = b1 - n;
/* 408 */         if (i9 < 1) {
/* 409 */           i9 = 1;
/* 410 */         } else if (b1 >= n + 26) {
/* 411 */           i9 = 26;
/*     */         } 
/* 413 */         if (i8 < i9) {
/*     */           break;
/*     */         }
/*     */         
/* 417 */         if (i6 > Integer.MAX_VALUE / (36 - i9))
/*     */         {
/* 419 */           throw new ParseException("Illegal char found", -1);
/*     */         }
/* 421 */         i6 *= 36 - i9;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 429 */       i3++;
/* 430 */       n = adaptBias(m - i5, i3, (i5 == 0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 436 */       if (m / i3 > Integer.MAX_VALUE - j)
/*     */       {
/* 438 */         throw new ParseException("Illegal char found", -1);
/*     */       }
/*     */       
/* 441 */       j += m / i3;
/* 442 */       m %= i3;
/*     */ 
/*     */ 
/*     */       
/* 446 */       if (j > 1114111 || isSurrogate(j))
/*     */       {
/* 448 */         throw new ParseException("Illegal char found", -1);
/*     */       }
/*     */ 
/*     */       
/* 452 */       int i7 = UTF16.getCharCount(j);
/* 453 */       if (k + i7 < c) {
/*     */         int i8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 466 */         if (m <= i4) {
/* 467 */           i8 = m;
/* 468 */           if (i7 > 1) {
/* 469 */             i4 = i8;
/*     */           } else {
/* 471 */             i4++;
/*     */           } 
/*     */         } else {
/* 474 */           i8 = i4;
/* 475 */           i8 = UTF16.moveCodePointOffset(arrayOfChar, 0, k, i8, m - i8);
/*     */         } 
/*     */ 
/*     */         
/* 479 */         if (i8 < k) {
/* 480 */           System.arraycopy(arrayOfChar, i8, arrayOfChar, i8 + i7, k - i8);
/*     */ 
/*     */           
/* 483 */           if (paramArrayOfboolean != null) {
/* 484 */             System.arraycopy(paramArrayOfboolean, i8, paramArrayOfboolean, i8 + i7, k - i8);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 489 */         if (i7 == 1) {
/*     */           
/* 491 */           arrayOfChar[i8] = (char)j;
/*     */         } else {
/*     */           
/* 494 */           arrayOfChar[i8] = UTF16.getLeadSurrogate(j);
/* 495 */           arrayOfChar[i8 + 1] = UTF16.getTrailSurrogate(j);
/*     */         } 
/* 497 */         if (paramArrayOfboolean != null) {
/*     */           
/* 499 */           paramArrayOfboolean[i8] = isBasicUpperCase(paramStringBuffer.charAt(b - 1));
/* 500 */           if (i7 == 2) {
/* 501 */             paramArrayOfboolean[i8 + 1] = false;
/*     */           }
/*     */         } 
/*     */       } 
/* 505 */       k += i7;
/* 506 */       m++;
/*     */     } 
/* 508 */     stringBuffer.append(arrayOfChar, 0, k);
/* 509 */     return stringBuffer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/idn/Punycode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */