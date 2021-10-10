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
/*     */ public final class Utility
/*     */ {
/*     */   public static final boolean arrayRegionMatches(char[] paramArrayOfchar1, int paramInt1, char[] paramArrayOfchar2, int paramInt2, int paramInt3) {
/*  51 */     int i = paramInt1 + paramInt3;
/*  52 */     int j = paramInt2 - paramInt1;
/*  53 */     for (int k = paramInt1; k < i; k++) {
/*  54 */       if (paramArrayOfchar1[k] != paramArrayOfchar2[k + j])
/*  55 */         return false; 
/*     */     } 
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String escape(String paramString) {
/*  65 */     StringBuffer stringBuffer = new StringBuffer();
/*  66 */     for (int i = 0; i < paramString.length(); ) {
/*  67 */       int j = UTF16.charAt(paramString, i);
/*  68 */       i += UTF16.getCharCount(j);
/*  69 */       if (j >= 32 && j <= 127) {
/*  70 */         if (j == 92) {
/*  71 */           stringBuffer.append("\\\\"); continue;
/*     */         } 
/*  73 */         stringBuffer.append((char)j);
/*     */         continue;
/*     */       } 
/*  76 */       boolean bool = (j <= 65535) ? true : false;
/*  77 */       stringBuffer.append(bool ? "\\u" : "\\U");
/*  78 */       hex(j, bool ? 4 : 8, stringBuffer);
/*     */     } 
/*     */     
/*  81 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*  85 */   private static final char[] UNESCAPE_MAP = new char[] { 'a', '\007', 'b', '\b', 'e', '\033', 'f', '\f', 'n', '\n', 'r', '\r', 't', '\t', 'v', '\013' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int unescapeAt(String paramString, int[] paramArrayOfint) {
/* 110 */     int k, j = 0;
/* 111 */     byte b1 = 0;
/* 112 */     byte b2 = 0;
/* 113 */     byte b3 = 0;
/* 114 */     byte b4 = 4;
/*     */ 
/*     */     
/* 117 */     boolean bool = false;
/*     */ 
/*     */     
/* 120 */     int m = paramArrayOfint[0];
/* 121 */     int n = paramString.length();
/* 122 */     if (m < 0 || m >= n) {
/* 123 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 127 */     int i = UTF16.charAt(paramString, m);
/* 128 */     m += UTF16.getCharCount(i);
/*     */ 
/*     */     
/* 131 */     switch (i) {
/*     */       case 117:
/* 133 */         b2 = b3 = 4;
/*     */         break;
/*     */       case 85:
/* 136 */         b2 = b3 = 8;
/*     */         break;
/*     */       case 120:
/* 139 */         b2 = 1;
/* 140 */         if (m < n && UTF16.charAt(paramString, m) == 123) {
/* 141 */           m++;
/* 142 */           bool = true;
/* 143 */           b3 = 8; break;
/*     */         } 
/* 145 */         b3 = 2;
/*     */         break;
/*     */       
/*     */       default:
/* 149 */         k = UCharacter.digit(i, 8);
/* 150 */         if (k >= 0) {
/* 151 */           b2 = 1;
/* 152 */           b3 = 3;
/* 153 */           b1 = 1;
/* 154 */           b4 = 3;
/* 155 */           j = k;
/*     */         } 
/*     */         break;
/*     */     } 
/* 159 */     if (b2 != 0) {
/* 160 */       while (m < n && b1 < b3) {
/* 161 */         i = UTF16.charAt(paramString, m);
/* 162 */         k = UCharacter.digit(i, (b4 == 3) ? 8 : 16);
/* 163 */         if (k < 0) {
/*     */           break;
/*     */         }
/* 166 */         j = j << b4 | k;
/* 167 */         m += UTF16.getCharCount(i);
/* 168 */         b1++;
/*     */       } 
/* 170 */       if (b1 < b2) {
/* 171 */         return -1;
/*     */       }
/* 173 */       if (bool) {
/* 174 */         if (i != 125) {
/* 175 */           return -1;
/*     */         }
/* 177 */         m++;
/*     */       } 
/* 179 */       if (j < 0 || j >= 1114112) {
/* 180 */         return -1;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       if (m < n && 
/* 187 */         UTF16.isLeadSurrogate((char)j)) {
/* 188 */         int i1 = m + 1;
/* 189 */         i = paramString.charAt(m);
/* 190 */         if (i == 92 && i1 < n) {
/* 191 */           int[] arrayOfInt = { i1 };
/* 192 */           i = unescapeAt(paramString, arrayOfInt);
/* 193 */           i1 = arrayOfInt[0];
/*     */         } 
/* 195 */         if (UTF16.isTrailSurrogate((char)i)) {
/* 196 */           m = i1;
/* 197 */           j = UCharacterProperty.getRawSupplementary((char)j, (char)i);
/*     */         } 
/*     */       } 
/*     */       
/* 201 */       paramArrayOfint[0] = m;
/* 202 */       return j;
/*     */     } 
/*     */ 
/*     */     
/* 206 */     for (byte b5 = 0; b5 < UNESCAPE_MAP.length; b5 += 2) {
/* 207 */       if (i == UNESCAPE_MAP[b5]) {
/* 208 */         paramArrayOfint[0] = m;
/* 209 */         return UNESCAPE_MAP[b5 + 1];
/* 210 */       }  if (i < UNESCAPE_MAP[b5]) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 216 */     if (i == 99 && m < n) {
/* 217 */       i = UTF16.charAt(paramString, m);
/* 218 */       paramArrayOfint[0] = m + UTF16.getCharCount(i);
/* 219 */       return 0x1F & i;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 224 */     paramArrayOfint[0] = m;
/* 225 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringBuffer hex(int paramInt1, int paramInt2, StringBuffer paramStringBuffer) {
/* 235 */     return appendNumber(paramStringBuffer, paramInt1, 16, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hex(int paramInt1, int paramInt2) {
/* 244 */     StringBuffer stringBuffer = new StringBuffer();
/* 245 */     return appendNumber(stringBuffer, paramInt1, 16, paramInt2).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int skipWhitespace(String paramString, int paramInt) {
/* 254 */     while (paramInt < paramString.length()) {
/* 255 */       int i = UTF16.charAt(paramString, paramInt);
/* 256 */       if (!UCharacterProperty.isRuleWhiteSpace(i)) {
/*     */         break;
/*     */       }
/* 259 */       paramInt += UTF16.getCharCount(i);
/*     */     } 
/* 261 */     return paramInt;
/*     */   }
/*     */   
/* 264 */   static final char[] DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void recursiveAppendNumber(StringBuffer paramStringBuffer, int paramInt1, int paramInt2, int paramInt3) {
/* 285 */     int i = paramInt1 % paramInt2;
/*     */     
/* 287 */     if (paramInt1 >= paramInt2 || paramInt3 > 1) {
/* 288 */       recursiveAppendNumber(paramStringBuffer, paramInt1 / paramInt2, paramInt2, paramInt3 - 1);
/*     */     }
/*     */     
/* 291 */     paramStringBuffer.append(DIGITS[i]);
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
/*     */   public static StringBuffer appendNumber(StringBuffer paramStringBuffer, int paramInt1, int paramInt2, int paramInt3) throws IllegalArgumentException {
/* 311 */     if (paramInt2 < 2 || paramInt2 > 36) {
/* 312 */       throw new IllegalArgumentException("Illegal radix " + paramInt2);
/*     */     }
/*     */ 
/*     */     
/* 316 */     int i = paramInt1;
/*     */     
/* 318 */     if (paramInt1 < 0) {
/* 319 */       i = -paramInt1;
/* 320 */       paramStringBuffer.append("-");
/*     */     } 
/*     */     
/* 323 */     recursiveAppendNumber(paramStringBuffer, i, paramInt2, paramInt3);
/*     */     
/* 325 */     return paramStringBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUnprintable(int paramInt) {
/* 333 */     return (paramInt < 32 || paramInt > 126);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean escapeUnprintable(StringBuffer paramStringBuffer, int paramInt) {
/* 344 */     if (isUnprintable(paramInt)) {
/* 345 */       paramStringBuffer.append('\\');
/* 346 */       if ((paramInt & 0xFFFF0000) != 0) {
/* 347 */         paramStringBuffer.append('U');
/* 348 */         paramStringBuffer.append(DIGITS[0xF & paramInt >> 28]);
/* 349 */         paramStringBuffer.append(DIGITS[0xF & paramInt >> 24]);
/* 350 */         paramStringBuffer.append(DIGITS[0xF & paramInt >> 20]);
/* 351 */         paramStringBuffer.append(DIGITS[0xF & paramInt >> 16]);
/*     */       } else {
/* 353 */         paramStringBuffer.append('u');
/*     */       } 
/* 355 */       paramStringBuffer.append(DIGITS[0xF & paramInt >> 12]);
/* 356 */       paramStringBuffer.append(DIGITS[0xF & paramInt >> 8]);
/* 357 */       paramStringBuffer.append(DIGITS[0xF & paramInt >> 4]);
/* 358 */       paramStringBuffer.append(DIGITS[0xF & paramInt]);
/* 359 */       return true;
/*     */     } 
/* 361 */     return false;
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
/*     */   public static void getChars(StringBuffer paramStringBuffer, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3) {
/* 379 */     if (paramInt1 == paramInt2) {
/*     */       return;
/*     */     }
/* 382 */     paramStringBuffer.getChars(paramInt1, paramInt2, paramArrayOfchar, paramInt3);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/Utility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */