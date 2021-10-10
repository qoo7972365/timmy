/*     */ package sun.text.normalizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UTF16
/*     */ {
/*     */   public static final int CODEPOINT_MIN_VALUE = 0;
/*     */   public static final int CODEPOINT_MAX_VALUE = 1114111;
/*     */   public static final int SUPPLEMENTARY_MIN_VALUE = 65536;
/*     */   public static final int LEAD_SURROGATE_MIN_VALUE = 55296;
/*     */   public static final int TRAIL_SURROGATE_MIN_VALUE = 56320;
/*     */   public static final int LEAD_SURROGATE_MAX_VALUE = 56319;
/*     */   public static final int TRAIL_SURROGATE_MAX_VALUE = 57343;
/*     */   public static final int SURROGATE_MIN_VALUE = 55296;
/*     */   private static final int LEAD_SURROGATE_SHIFT_ = 10;
/*     */   private static final int TRAIL_SURROGATE_MASK_ = 1023;
/*     */   private static final int LEAD_SURROGATE_OFFSET_ = 55232;
/*     */   
/*     */   public static int charAt(String paramString, int paramInt) {
/* 187 */     char c = paramString.charAt(paramInt);
/* 188 */     if (c < '?') {
/* 189 */       return c;
/*     */     }
/* 191 */     return _charAt(paramString, paramInt, c);
/*     */   }
/*     */   
/*     */   private static int _charAt(String paramString, int paramInt, char paramChar) {
/* 195 */     if (paramChar > '?') {
/* 196 */       return paramChar;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     if (paramChar <= '?') {
/* 204 */       paramInt++;
/* 205 */       if (paramString.length() != paramInt) {
/* 206 */         char c = paramString.charAt(paramInt);
/* 207 */         if (c >= '?' && c <= '?') {
/* 208 */           return UCharacterProperty.getRawSupplementary(paramChar, c);
/*     */         }
/*     */       } 
/*     */     } else {
/* 212 */       paramInt--;
/* 213 */       if (paramInt >= 0) {
/*     */         
/* 215 */         char c = paramString.charAt(paramInt);
/* 216 */         if (c >= '?' && c <= '?') {
/* 217 */           return UCharacterProperty.getRawSupplementary(c, paramChar);
/*     */         }
/*     */       } 
/*     */     } 
/* 221 */     return paramChar;
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
/*     */ 
/*     */   
/*     */   public static int charAt(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3) {
/* 248 */     paramInt3 += paramInt1;
/* 249 */     if (paramInt3 < paramInt1 || paramInt3 >= paramInt2) {
/* 250 */       throw new ArrayIndexOutOfBoundsException(paramInt3);
/*     */     }
/*     */     
/* 253 */     char c = paramArrayOfchar[paramInt3];
/* 254 */     if (!isSurrogate(c)) {
/* 255 */       return c;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     if (c <= '?') {
/* 262 */       paramInt3++;
/* 263 */       if (paramInt3 >= paramInt2) {
/* 264 */         return c;
/*     */       }
/* 266 */       char c1 = paramArrayOfchar[paramInt3];
/* 267 */       if (isTrailSurrogate(c1)) {
/* 268 */         return UCharacterProperty.getRawSupplementary(c, c1);
/*     */       }
/*     */     } else {
/*     */       
/* 272 */       if (paramInt3 == paramInt1) {
/* 273 */         return c;
/*     */       }
/* 275 */       paramInt3--;
/* 276 */       char c1 = paramArrayOfchar[paramInt3];
/* 277 */       if (isLeadSurrogate(c1))
/* 278 */         return UCharacterProperty.getRawSupplementary(c1, c); 
/*     */     } 
/* 280 */     return c;
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
/*     */   public static int getCharCount(int paramInt) {
/* 294 */     if (paramInt < 65536) {
/* 295 */       return 1;
/*     */     }
/* 297 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSurrogate(char paramChar) {
/* 308 */     return ('?' <= paramChar && paramChar <= '?');
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
/*     */   public static boolean isTrailSurrogate(char paramChar) {
/* 320 */     return ('?' <= paramChar && paramChar <= '?');
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
/*     */   public static boolean isLeadSurrogate(char paramChar) {
/* 332 */     return ('?' <= paramChar && paramChar <= '?');
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
/*     */   public static char getLeadSurrogate(int paramInt) {
/* 348 */     if (paramInt >= 65536) {
/* 349 */       return (char)(55232 + (paramInt >> 10));
/*     */     }
/*     */ 
/*     */     
/* 353 */     return Character.MIN_VALUE;
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
/*     */   public static char getTrailSurrogate(int paramInt) {
/* 368 */     if (paramInt >= 65536) {
/* 369 */       return (char)(56320 + (paramInt & 0x3FF));
/*     */     }
/*     */ 
/*     */     
/* 373 */     return (char)paramInt;
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
/*     */   public static String valueOf(int paramInt) {
/* 390 */     if (paramInt < 0 || paramInt > 1114111) {
/* 391 */       throw new IllegalArgumentException("Illegal codepoint");
/*     */     }
/* 393 */     return toString(paramInt);
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
/*     */   public static StringBuffer append(StringBuffer paramStringBuffer, int paramInt) {
/* 411 */     if (paramInt < 0 || paramInt > 1114111) {
/* 412 */       throw new IllegalArgumentException("Illegal codepoint: " + Integer.toHexString(paramInt));
/*     */     }
/*     */ 
/*     */     
/* 416 */     if (paramInt >= 65536) {
/*     */       
/* 418 */       paramStringBuffer.append(getLeadSurrogate(paramInt));
/* 419 */       paramStringBuffer.append(getTrailSurrogate(paramInt));
/*     */     } else {
/*     */       
/* 422 */       paramStringBuffer.append((char)paramInt);
/*     */     } 
/* 424 */     return paramStringBuffer;
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
/*     */   public static int moveCodePointOffset(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 444 */     int j, i = paramArrayOfchar.length;
/*     */ 
/*     */     
/* 447 */     int k = paramInt3 + paramInt1;
/* 448 */     if (paramInt1 < 0 || paramInt2 < paramInt1) {
/* 449 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*     */     }
/* 451 */     if (paramInt2 > i) {
/* 452 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*     */     }
/* 454 */     if (paramInt3 < 0 || k > paramInt2) {
/* 455 */       throw new StringIndexOutOfBoundsException(paramInt3);
/*     */     }
/* 457 */     if (paramInt4 > 0) {
/* 458 */       if (paramInt4 + k > i) {
/* 459 */         throw new StringIndexOutOfBoundsException(k);
/*     */       }
/* 461 */       j = paramInt4;
/* 462 */       while (k < paramInt2 && j > 0) {
/*     */         
/* 464 */         char c = paramArrayOfchar[k];
/* 465 */         if (isLeadSurrogate(c) && k + 1 < paramInt2 && 
/* 466 */           isTrailSurrogate(paramArrayOfchar[k + 1])) {
/* 467 */           k++;
/*     */         }
/* 469 */         j--;
/* 470 */         k++;
/*     */       } 
/*     */     } else {
/* 473 */       if (k + paramInt4 < paramInt1) {
/* 474 */         throw new StringIndexOutOfBoundsException(k);
/*     */       }
/* 476 */       j = -paramInt4;
/* 477 */       k--;
/* 478 */       for (; j > 0 && k >= paramInt1; j--) {
/*     */ 
/*     */         
/* 481 */         char c = paramArrayOfchar[k];
/* 482 */         if (isTrailSurrogate(c) && k > paramInt1 && isLeadSurrogate(paramArrayOfchar[k - 1])) {
/* 483 */           k--;
/*     */         }
/*     */       } 
/*     */     } 
/* 487 */     if (j != 0) {
/* 488 */       throw new StringIndexOutOfBoundsException(paramInt4);
/*     */     }
/* 490 */     k -= paramInt1;
/* 491 */     return k;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toString(int paramInt) {
/* 529 */     if (paramInt < 65536) {
/* 530 */       return String.valueOf((char)paramInt);
/*     */     }
/*     */     
/* 533 */     StringBuffer stringBuffer = new StringBuffer();
/* 534 */     stringBuffer.append(getLeadSurrogate(paramInt));
/* 535 */     stringBuffer.append(getTrailSurrogate(paramInt));
/* 536 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/UTF16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */