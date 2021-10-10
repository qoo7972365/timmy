/*     */ package sun.net.util;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IPAddressUtil
/*     */ {
/*     */   private static final int INADDR4SZ = 4;
/*     */   private static final int INADDR16SZ = 16;
/*     */   private static final int INT16SZ = 2;
/*     */   private static final long L_IPV6_DELIMS = 0L;
/*     */   private static final long H_IPV6_DELIMS = 671088640L;
/*     */   private static final long L_GEN_DELIMS = -8935000888854970368L;
/*     */   private static final long H_GEN_DELIMS = 671088641L;
/*     */   private static final long L_AUTH_DELIMS = 288230376151711744L;
/*     */   private static final long H_AUTH_DELIMS = 671088641L;
/*     */   private static final long L_COLON = 288230376151711744L;
/*     */   private static final long H_COLON = 0L;
/*     */   private static final long L_SLASH = 140737488355328L;
/*     */   private static final long H_SLASH = 0L;
/*     */   private static final long L_BACKSLASH = 0L;
/*     */   private static final long H_BACKSLASH = 268435456L;
/*     */   private static final long L_NON_PRINTABLE = 4294967295L;
/*     */   private static final long H_NON_PRINTABLE = -9223372036854775808L;
/*     */   private static final long L_EXCLUDE = -8935000884560003073L;
/*     */   private static final long H_EXCLUDE = -9223372035915251711L;
/*     */   
/*     */   public static byte[] textToNumericFormatV4(String paramString) {
/*  46 */     byte[] arrayOfByte = new byte[4];
/*     */     
/*  48 */     long l = 0L;
/*  49 */     byte b1 = 0;
/*  50 */     boolean bool = true;
/*     */     
/*  52 */     int i = paramString.length();
/*  53 */     if (i == 0 || i > 15) {
/*  54 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     for (byte b2 = 0; b2 < i; b2++) {
/*  82 */       char c = paramString.charAt(b2);
/*  83 */       if (c == '.') {
/*  84 */         if (bool || l < 0L || l > 255L || b1 == 3) {
/*  85 */           return null;
/*     */         }
/*  87 */         arrayOfByte[b1++] = (byte)(int)(l & 0xFFL);
/*  88 */         l = 0L;
/*  89 */         bool = true;
/*     */       } else {
/*  91 */         int j = Character.digit(c, 10);
/*  92 */         if (j < 0) {
/*  93 */           return null;
/*     */         }
/*  95 */         l *= 10L;
/*  96 */         l += j;
/*  97 */         bool = false;
/*     */       } 
/*     */     } 
/* 100 */     if (bool || l < 0L || l >= 1L << (4 - b1) * 8) {
/* 101 */       return null;
/*     */     }
/* 103 */     switch (b1) {
/*     */       case 0:
/* 105 */         arrayOfByte[0] = (byte)(int)(l >> 24L & 0xFFL);
/*     */       case 1:
/* 107 */         arrayOfByte[1] = (byte)(int)(l >> 16L & 0xFFL);
/*     */       case 2:
/* 109 */         arrayOfByte[2] = (byte)(int)(l >> 8L & 0xFFL);
/*     */       case 3:
/* 111 */         arrayOfByte[3] = (byte)(int)(l >> 0L & 0xFFL); break;
/*     */     } 
/* 113 */     return arrayOfByte;
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
/*     */   public static byte[] textToNumericFormatV6(String paramString) {
/* 129 */     if (paramString.length() < 2) {
/* 130 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     char[] arrayOfChar = paramString.toCharArray();
/* 138 */     byte[] arrayOfByte1 = new byte[16];
/*     */     
/* 140 */     int j = arrayOfChar.length;
/* 141 */     int k = paramString.indexOf("%");
/* 142 */     if (k == j - 1) {
/* 143 */       return null;
/*     */     }
/*     */     
/* 146 */     if (k != -1) {
/* 147 */       j = k;
/*     */     }
/*     */     
/* 150 */     byte b = -1;
/* 151 */     byte b1 = 0, b2 = 0;
/*     */     
/* 153 */     if (arrayOfChar[b1] == ':' && 
/* 154 */       arrayOfChar[++b1] != ':')
/* 155 */       return null; 
/* 156 */     byte b3 = b1;
/* 157 */     boolean bool = false;
/* 158 */     int i = 0;
/* 159 */     while (b1 < j) {
/* 160 */       char c = arrayOfChar[b1++];
/* 161 */       int m = Character.digit(c, 16);
/* 162 */       if (m != -1) {
/* 163 */         i <<= 4;
/* 164 */         i |= m;
/* 165 */         if (i > 65535)
/* 166 */           return null; 
/* 167 */         bool = true;
/*     */         continue;
/*     */       } 
/* 170 */       if (c == ':') {
/* 171 */         b3 = b1;
/* 172 */         if (!bool) {
/* 173 */           if (b != -1)
/* 174 */             return null; 
/* 175 */           b = b2; continue;
/*     */         } 
/* 177 */         if (b1 == j) {
/* 178 */           return null;
/*     */         }
/* 180 */         if (b2 + 2 > 16)
/* 181 */           return null; 
/* 182 */         arrayOfByte1[b2++] = (byte)(i >> 8 & 0xFF);
/* 183 */         arrayOfByte1[b2++] = (byte)(i & 0xFF);
/* 184 */         bool = false;
/* 185 */         i = 0;
/*     */         continue;
/*     */       } 
/* 188 */       if (c == '.' && b2 + 4 <= 16) {
/* 189 */         String str = paramString.substring(b3, j);
/*     */         
/* 191 */         byte b4 = 0; int n = 0;
/* 192 */         while ((n = str.indexOf('.', n)) != -1) {
/* 193 */           b4++;
/* 194 */           n++;
/*     */         } 
/* 196 */         if (b4 != 3) {
/* 197 */           return null;
/*     */         }
/* 199 */         byte[] arrayOfByte = textToNumericFormatV4(str);
/* 200 */         if (arrayOfByte == null) {
/* 201 */           return null;
/*     */         }
/* 203 */         for (byte b5 = 0; b5 < 4; b5++) {
/* 204 */           arrayOfByte1[b2++] = arrayOfByte[b5];
/*     */         }
/* 206 */         bool = false;
/*     */         break;
/*     */       } 
/* 209 */       return null;
/*     */     } 
/* 211 */     if (bool) {
/* 212 */       if (b2 + 2 > 16)
/* 213 */         return null; 
/* 214 */       arrayOfByte1[b2++] = (byte)(i >> 8 & 0xFF);
/* 215 */       arrayOfByte1[b2++] = (byte)(i & 0xFF);
/*     */     } 
/*     */     
/* 218 */     if (b != -1) {
/* 219 */       int m = b2 - b;
/*     */       
/* 221 */       if (b2 == 16)
/* 222 */         return null; 
/* 223 */       for (b1 = 1; b1 <= m; b1++) {
/* 224 */         arrayOfByte1[16 - b1] = arrayOfByte1[b + m - b1];
/* 225 */         arrayOfByte1[b + m - b1] = 0;
/*     */       } 
/* 227 */       b2 = 16;
/*     */     } 
/* 229 */     if (b2 != 16)
/* 230 */       return null; 
/* 231 */     byte[] arrayOfByte2 = convertFromIPv4MappedAddress(arrayOfByte1);
/* 232 */     if (arrayOfByte2 != null) {
/* 233 */       return arrayOfByte2;
/*     */     }
/* 235 */     return arrayOfByte1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isIPv4LiteralAddress(String paramString) {
/* 244 */     return (textToNumericFormatV4(paramString) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isIPv6LiteralAddress(String paramString) {
/* 252 */     return (textToNumericFormatV6(paramString) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] convertFromIPv4MappedAddress(byte[] paramArrayOfbyte) {
/* 263 */     if (isIPv4MappedAddress(paramArrayOfbyte)) {
/* 264 */       byte[] arrayOfByte = new byte[4];
/* 265 */       System.arraycopy(paramArrayOfbyte, 12, arrayOfByte, 0, 4);
/* 266 */       return arrayOfByte;
/*     */     } 
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isIPv4MappedAddress(byte[] paramArrayOfbyte) {
/* 279 */     if (paramArrayOfbyte.length < 16) {
/* 280 */       return false;
/*     */     }
/* 282 */     if (paramArrayOfbyte[0] == 0 && paramArrayOfbyte[1] == 0 && paramArrayOfbyte[2] == 0 && paramArrayOfbyte[3] == 0 && paramArrayOfbyte[4] == 0 && paramArrayOfbyte[5] == 0 && paramArrayOfbyte[6] == 0 && paramArrayOfbyte[7] == 0 && paramArrayOfbyte[8] == 0 && paramArrayOfbyte[9] == 0 && paramArrayOfbyte[10] == -1 && paramArrayOfbyte[11] == -1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 289 */       return true;
/*     */     }
/* 291 */     return false;
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
/* 322 */   private static final char[] OTHERS = new char[] { '⁇', '⁈', '⁉', '℀', '℁', '℅', '℆', '⩴', '﹕', '﹖', '﹟', '﹫', '＃', '／', '：', '？', '＠' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean match(char paramChar, long paramLong1, long paramLong2) {
/* 329 */     if (paramChar < '@')
/* 330 */       return ((1L << paramChar & paramLong1) != 0L); 
/* 331 */     if (paramChar < '')
/* 332 */       return ((1L << paramChar - 64 & paramLong2) != 0L); 
/* 333 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int scan(String paramString, long paramLong1, long paramLong2) {
/* 340 */     byte b = -1; int i;
/* 341 */     if (paramString == null || (i = paramString.length()) == 0) return -1; 
/* 342 */     boolean bool = false;
/* 343 */     while (++b < i && !(bool = match(paramString.charAt(b), paramLong1, paramLong2)));
/* 344 */     if (bool) return b; 
/* 345 */     return -1;
/*     */   }
/*     */   
/*     */   public static int scan(String paramString, long paramLong1, long paramLong2, char[] paramArrayOfchar) {
/* 349 */     byte b = -1; int i;
/* 350 */     if (paramString == null || (i = paramString.length()) == 0) return -1; 
/* 351 */     boolean bool = false;
/* 352 */     char c2 = paramArrayOfchar[0]; char c1;
/* 353 */     while (++b < i && !(bool = match(c1 = paramString.charAt(b), paramLong1, paramLong2))) {
/* 354 */       if (c1 >= c2 && Arrays.binarySearch(paramArrayOfchar, c1) > -1) {
/* 355 */         bool = true; break;
/*     */       } 
/*     */     } 
/* 358 */     if (bool) return b;
/*     */     
/* 360 */     return -1;
/*     */   }
/*     */   
/*     */   private static String describeChar(char paramChar) {
/* 364 */     if (paramChar < ' ' || paramChar == '') {
/* 365 */       if (paramChar == '\n') return "LF"; 
/* 366 */       if (paramChar == '\r') return "CR"; 
/* 367 */       return "control char (code=" + paramChar + ")";
/*     */     } 
/* 369 */     if (paramChar == '\\') return "'\\'"; 
/* 370 */     return "'" + paramChar + "'";
/*     */   }
/*     */ 
/*     */   
/*     */   private static String checkUserInfo(String paramString) {
/* 375 */     int i = scan(paramString, -9223231260711714817L, -9223372035915251711L);
/*     */     
/* 377 */     if (i >= 0) {
/* 378 */       return "Illegal character found in user-info: " + 
/* 379 */         describeChar(paramString.charAt(i));
/*     */     }
/* 381 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String checkHost(String paramString) {
/* 386 */     if (paramString.startsWith("[") && paramString.endsWith("]")) {
/* 387 */       paramString = paramString.substring(1, paramString.length() - 1);
/* 388 */       if (isIPv6LiteralAddress(paramString)) {
/* 389 */         int j = paramString.indexOf('%');
/* 390 */         if (j >= 0) {
/* 391 */           j = scan(paramString = paramString.substring(j), 4294967295L, -9223372036183687168L);
/*     */ 
/*     */           
/* 394 */           if (j >= 0) {
/* 395 */             return "Illegal character found in IPv6 scoped address: " + 
/* 396 */               describeChar(paramString.charAt(j));
/*     */           }
/*     */         } 
/* 399 */         return null;
/*     */       } 
/* 401 */       return "Unrecognized IPv6 address format";
/*     */     } 
/* 403 */     int i = scan(paramString, -8935000884560003073L, -9223372035915251711L);
/* 404 */     if (i >= 0) {
/* 405 */       return "Illegal character found in host: " + 
/* 406 */         describeChar(paramString.charAt(i));
/*     */     }
/*     */     
/* 409 */     return null;
/*     */   }
/*     */   
/*     */   private static String checkAuth(String paramString) {
/* 413 */     int i = scan(paramString, -9223231260711714817L, -9223372036586340352L);
/*     */ 
/*     */     
/* 416 */     if (i >= 0) {
/* 417 */       return "Illegal character found in authority: " + 
/* 418 */         describeChar(paramString.charAt(i));
/*     */     }
/* 420 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String checkAuthority(URL paramURL) {
/* 427 */     if (paramURL == null) return null;  String str1; String str2;
/* 428 */     if ((str1 = checkUserInfo(str2 = paramURL.getUserInfo())) != null)
/* 429 */       return str1; 
/*     */     String str3;
/* 431 */     if ((str1 = checkHost(str3 = paramURL.getHost())) != null) {
/* 432 */       return str1;
/*     */     }
/* 434 */     if (str3 == null && str2 == null) {
/* 435 */       return checkAuth(paramURL.getAuthority());
/*     */     }
/* 437 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String checkExternalForm(URL paramURL) {
/* 444 */     if (paramURL == null) return null;  String str;
/* 445 */     int i = scan(str = paramURL.getUserInfo(), 140741783322623L, Long.MIN_VALUE);
/*     */ 
/*     */     
/* 448 */     if (i >= 0) {
/* 449 */       return "Illegal character found in authority: " + 
/* 450 */         describeChar(str.charAt(i));
/*     */     }
/* 452 */     if ((str = checkHostString(paramURL.getHost())) != null) {
/* 453 */       return str;
/*     */     }
/* 455 */     return null;
/*     */   }
/*     */   
/*     */   public static String checkHostString(String paramString) {
/* 459 */     if (paramString == null) return null; 
/* 460 */     int i = scan(paramString, 140741783322623L, Long.MIN_VALUE, OTHERS);
/*     */ 
/*     */ 
/*     */     
/* 464 */     if (i >= 0) {
/* 465 */       return "Illegal character found in host: " + 
/* 466 */         describeChar(paramString.charAt(i));
/*     */     }
/* 468 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/util/IPAddressUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */