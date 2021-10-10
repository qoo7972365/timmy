/*     */ package sun.net.www;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.util.BitSet;
/*     */ import sun.nio.cs.ThreadLocalCoders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParseUtil
/*     */ {
/*  52 */   static BitSet encodedInPath = new BitSet(256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  59 */     encodedInPath.set(61);
/*  60 */     encodedInPath.set(59);
/*  61 */     encodedInPath.set(63);
/*  62 */     encodedInPath.set(47);
/*     */ 
/*     */ 
/*     */     
/*  66 */     encodedInPath.set(35);
/*  67 */     encodedInPath.set(32);
/*  68 */     encodedInPath.set(60);
/*  69 */     encodedInPath.set(62);
/*  70 */     encodedInPath.set(37);
/*  71 */     encodedInPath.set(34);
/*  72 */     encodedInPath.set(123);
/*  73 */     encodedInPath.set(125);
/*  74 */     encodedInPath.set(124);
/*  75 */     encodedInPath.set(92);
/*  76 */     encodedInPath.set(94);
/*  77 */     encodedInPath.set(91);
/*  78 */     encodedInPath.set(93);
/*  79 */     encodedInPath.set(96);
/*     */ 
/*     */     
/*  82 */     for (byte b = 0; b < 32; b++)
/*  83 */       encodedInPath.set(b); 
/*  84 */     encodedInPath.set(127);
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
/*     */   public static String encodePath(String paramString) {
/*  97 */     return encodePath(paramString, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodePath(String paramString, boolean paramBoolean) {
/* 105 */     char[] arrayOfChar1 = new char[paramString.length() * 2 + 16];
/* 106 */     int i = 0;
/* 107 */     char[] arrayOfChar2 = paramString.toCharArray();
/*     */     
/* 109 */     int j = paramString.length();
/* 110 */     for (byte b = 0; b < j; b++) {
/* 111 */       char c = arrayOfChar2[b];
/* 112 */       if ((!paramBoolean && c == '/') || (paramBoolean && c == File.separatorChar)) {
/* 113 */         arrayOfChar1[i++] = '/';
/*     */       }
/* 115 */       else if (c <= '') {
/* 116 */         if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))
/*     */         
/*     */         { 
/* 119 */           arrayOfChar1[i++] = c; }
/*     */         
/* 121 */         else if (encodedInPath.get(c))
/* 122 */         { i = escape(arrayOfChar1, c, i); }
/*     */         else
/* 124 */         { arrayOfChar1[i++] = c; } 
/* 125 */       } else if (c > '߿') {
/* 126 */         i = escape(arrayOfChar1, (char)(0xE0 | c >> 12 & 0xF), i);
/* 127 */         i = escape(arrayOfChar1, (char)(0x80 | c >> 6 & 0x3F), i);
/* 128 */         i = escape(arrayOfChar1, (char)(0x80 | c >> 0 & 0x3F), i);
/*     */       } else {
/* 130 */         i = escape(arrayOfChar1, (char)(0xC0 | c >> 6 & 0x1F), i);
/* 131 */         i = escape(arrayOfChar1, (char)(0x80 | c >> 0 & 0x3F), i);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 136 */       if (i + 9 > arrayOfChar1.length) {
/* 137 */         int k = arrayOfChar1.length * 2 + 16;
/* 138 */         if (k < 0) {
/* 139 */           k = Integer.MAX_VALUE;
/*     */         }
/* 141 */         char[] arrayOfChar = new char[k];
/* 142 */         System.arraycopy(arrayOfChar1, 0, arrayOfChar, 0, i);
/* 143 */         arrayOfChar1 = arrayOfChar;
/*     */       } 
/*     */     } 
/* 146 */     return new String(arrayOfChar1, 0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int escape(char[] paramArrayOfchar, char paramChar, int paramInt) {
/* 154 */     paramArrayOfchar[paramInt++] = '%';
/* 155 */     paramArrayOfchar[paramInt++] = Character.forDigit(paramChar >> 4 & 0xF, 16);
/* 156 */     paramArrayOfchar[paramInt++] = Character.forDigit(paramChar & 0xF, 16);
/* 157 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte unescape(String paramString, int paramInt) {
/* 164 */     return (byte)Integer.parseInt(paramString.substring(paramInt + 1, paramInt + 3), 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String decode(String paramString) {
/* 174 */     int i = paramString.length();
/* 175 */     if (i == 0 || paramString.indexOf('%') < 0) {
/* 176 */       return paramString;
/*     */     }
/* 178 */     StringBuilder stringBuilder = new StringBuilder(i);
/* 179 */     ByteBuffer byteBuffer = ByteBuffer.allocate(i);
/* 180 */     CharBuffer charBuffer = CharBuffer.allocate(i);
/*     */ 
/*     */     
/* 183 */     CharsetDecoder charsetDecoder = ThreadLocalCoders.decoderFor("UTF-8").onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
/*     */     
/* 185 */     char c = paramString.charAt(0);
/* 186 */     for (byte b = 0; b < i; ) {
/* 187 */       assert c == paramString.charAt(b);
/* 188 */       if (c != '%') {
/* 189 */         stringBuilder.append(c);
/* 190 */         if (++b >= i)
/*     */           break; 
/* 192 */         c = paramString.charAt(b);
/*     */         continue;
/*     */       } 
/* 195 */       byteBuffer.clear();
/* 196 */       byte b1 = b;
/*     */       do {
/* 198 */         assert i - b >= 2;
/*     */         try {
/* 200 */           byteBuffer.put(unescape(paramString, b));
/* 201 */         } catch (NumberFormatException numberFormatException) {
/* 202 */           throw new IllegalArgumentException();
/*     */         } 
/* 204 */         b += 3;
/* 205 */         if (b >= i)
/*     */           break; 
/* 207 */         c = paramString.charAt(b);
/* 208 */       } while (c == '%');
/*     */ 
/*     */       
/* 211 */       byteBuffer.flip();
/* 212 */       charBuffer.clear();
/* 213 */       charsetDecoder.reset();
/* 214 */       CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, true);
/* 215 */       if (coderResult.isError())
/* 216 */         throw new IllegalArgumentException("Error decoding percent encoded characters"); 
/* 217 */       coderResult = charsetDecoder.flush(charBuffer);
/* 218 */       if (coderResult.isError())
/* 219 */         throw new IllegalArgumentException("Error decoding percent encoded characters"); 
/* 220 */       stringBuilder.append(charBuffer.flip().toString());
/*     */     } 
/*     */     
/* 223 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String canonizeString(String paramString) {
/* 230 */     int i = 0;
/* 231 */     int j = paramString.length();
/*     */ 
/*     */     
/* 234 */     while ((i = paramString.indexOf("/../")) >= 0) {
/* 235 */       if ((j = paramString.lastIndexOf('/', i - 1)) >= 0) {
/* 236 */         paramString = paramString.substring(0, j) + paramString.substring(i + 3); continue;
/*     */       } 
/* 238 */       paramString = paramString.substring(i + 3);
/*     */     } 
/*     */ 
/*     */     
/* 242 */     while ((i = paramString.indexOf("/./")) >= 0) {
/* 243 */       paramString = paramString.substring(0, i) + paramString.substring(i + 2);
/*     */     }
/*     */     
/* 246 */     while (paramString.endsWith("/..")) {
/* 247 */       i = paramString.indexOf("/..");
/* 248 */       if ((j = paramString.lastIndexOf('/', i - 1)) >= 0) {
/* 249 */         paramString = paramString.substring(0, j + 1); continue;
/*     */       } 
/* 251 */       paramString = paramString.substring(0, i);
/*     */     } 
/*     */ 
/*     */     
/* 255 */     if (paramString.endsWith("/.")) {
/* 256 */       paramString = paramString.substring(0, paramString.length() - 1);
/*     */     }
/* 258 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL fileToEncodedURL(File paramFile) throws MalformedURLException {
/* 264 */     String str = paramFile.getAbsolutePath();
/* 265 */     str = encodePath(str);
/* 266 */     if (!str.startsWith("/")) {
/* 267 */       str = "/" + str;
/*     */     }
/* 269 */     if (!str.endsWith("/") && paramFile.isDirectory()) {
/* 270 */       str = str + "/";
/*     */     }
/* 272 */     return new URL("file", "", str);
/*     */   }
/*     */   public static URI toURI(URL paramURL) {
/*     */     URI uRI;
/* 276 */     String str1 = paramURL.getProtocol();
/* 277 */     String str2 = paramURL.getAuthority();
/* 278 */     String str3 = paramURL.getPath();
/* 279 */     String str4 = paramURL.getQuery();
/* 280 */     String str5 = paramURL.getRef();
/* 281 */     if (str3 != null && !str3.startsWith("/")) {
/* 282 */       str3 = "/" + str3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     if (str2 != null && str2.endsWith(":-1")) {
/* 289 */       str2 = str2.substring(0, str2.length() - 3);
/*     */     }
/*     */     
/*     */     try {
/* 293 */       uRI = createURI(str1, str2, str3, str4, str5);
/* 294 */     } catch (URISyntaxException uRISyntaxException) {
/* 295 */       uRI = null;
/*     */     } 
/* 297 */     return uRI;
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
/*     */   private static URI createURI(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws URISyntaxException {
/* 316 */     String str = toString(paramString1, null, paramString2, null, null, -1, paramString3, paramString4, paramString5);
/*     */ 
/*     */     
/* 319 */     checkPath(str, paramString1, paramString3);
/* 320 */     return new URI(str);
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
/*     */   private static String toString(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, String paramString6, String paramString7, String paramString8) {
/* 333 */     StringBuffer stringBuffer = new StringBuffer();
/* 334 */     if (paramString1 != null) {
/* 335 */       stringBuffer.append(paramString1);
/* 336 */       stringBuffer.append(':');
/*     */     } 
/* 338 */     appendSchemeSpecificPart(stringBuffer, paramString2, paramString3, paramString4, paramString5, paramInt, paramString6, paramString7);
/*     */ 
/*     */     
/* 341 */     appendFragment(stringBuffer, paramString8);
/* 342 */     return stringBuffer.toString();
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
/*     */   private static void appendSchemeSpecificPart(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5, String paramString6) {
/* 354 */     if (paramString1 != null) {
/*     */ 
/*     */ 
/*     */       
/* 358 */       if (paramString1.startsWith("//[")) {
/* 359 */         int i = paramString1.indexOf("]");
/* 360 */         if (i != -1 && paramString1.indexOf(":") != -1) {
/*     */           String str1, str2;
/* 362 */           if (i == paramString1.length()) {
/* 363 */             str2 = paramString1;
/* 364 */             str1 = "";
/*     */           } else {
/* 366 */             str2 = paramString1.substring(0, i + 1);
/* 367 */             str1 = paramString1.substring(i + 1);
/*     */           } 
/* 369 */           paramStringBuffer.append(str2);
/* 370 */           paramStringBuffer.append(quote(str1, L_URIC, H_URIC));
/*     */         } 
/*     */       } else {
/* 373 */         paramStringBuffer.append(quote(paramString1, L_URIC, H_URIC));
/*     */       } 
/*     */     } else {
/* 376 */       appendAuthority(paramStringBuffer, paramString2, paramString3, paramString4, paramInt);
/* 377 */       if (paramString5 != null)
/* 378 */         paramStringBuffer.append(quote(paramString5, L_PATH, H_PATH)); 
/* 379 */       if (paramString6 != null) {
/* 380 */         paramStringBuffer.append('?');
/* 381 */         paramStringBuffer.append(quote(paramString6, L_URIC, H_URIC));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void appendAuthority(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, int paramInt) {
/* 392 */     if (paramString3 != null) {
/* 393 */       paramStringBuffer.append("//");
/* 394 */       if (paramString2 != null) {
/* 395 */         paramStringBuffer.append(quote(paramString2, L_USERINFO, H_USERINFO));
/* 396 */         paramStringBuffer.append('@');
/*     */       } 
/*     */ 
/*     */       
/* 400 */       boolean bool = (paramString3.indexOf(':') >= 0 && !paramString3.startsWith("[") && !paramString3.endsWith("]")) ? true : false;
/* 401 */       if (bool) paramStringBuffer.append('['); 
/* 402 */       paramStringBuffer.append(paramString3);
/* 403 */       if (bool) paramStringBuffer.append(']'); 
/* 404 */       if (paramInt != -1) {
/* 405 */         paramStringBuffer.append(':');
/* 406 */         paramStringBuffer.append(paramInt);
/*     */       } 
/* 408 */     } else if (paramString1 != null) {
/* 409 */       paramStringBuffer.append("//");
/* 410 */       if (paramString1.startsWith("[")) {
/* 411 */         int i = paramString1.indexOf("]");
/* 412 */         if (i != -1 && paramString1.indexOf(":") != -1) {
/*     */           String str1, str2;
/* 414 */           if (i == paramString1.length()) {
/* 415 */             str2 = paramString1;
/* 416 */             str1 = "";
/*     */           } else {
/* 418 */             str2 = paramString1.substring(0, i + 1);
/* 419 */             str1 = paramString1.substring(i + 1);
/*     */           } 
/* 421 */           paramStringBuffer.append(str2);
/* 422 */           paramStringBuffer.append(quote(str1, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 427 */         paramStringBuffer.append(quote(paramString1, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void appendFragment(StringBuffer paramStringBuffer, String paramString) {
/* 435 */     if (paramString != null) {
/* 436 */       paramStringBuffer.append('#');
/* 437 */       paramStringBuffer.append(quote(paramString, L_URIC, H_URIC));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String quote(String paramString, long paramLong1, long paramLong2) {
/* 445 */     int i = paramString.length();
/* 446 */     StringBuffer stringBuffer = null;
/* 447 */     boolean bool = ((paramLong1 & 0x1L) != 0L) ? true : false;
/* 448 */     for (byte b = 0; b < paramString.length(); b++) {
/* 449 */       char c = paramString.charAt(b);
/* 450 */       if (c < '') {
/* 451 */         if (!match(c, paramLong1, paramLong2) && !isEscaped(paramString, b)) {
/* 452 */           if (stringBuffer == null) {
/* 453 */             stringBuffer = new StringBuffer();
/* 454 */             stringBuffer.append(paramString.substring(0, b));
/*     */           } 
/* 456 */           appendEscape(stringBuffer, (byte)c);
/*     */         }
/* 458 */         else if (stringBuffer != null) {
/* 459 */           stringBuffer.append(c);
/*     */         } 
/* 461 */       } else if (bool && (
/* 462 */         Character.isSpaceChar(c) || 
/* 463 */         Character.isISOControl(c))) {
/* 464 */         if (stringBuffer == null) {
/* 465 */           stringBuffer = new StringBuffer();
/* 466 */           stringBuffer.append(paramString.substring(0, b));
/*     */         } 
/* 468 */         appendEncoded(stringBuffer, c);
/*     */       }
/* 470 */       else if (stringBuffer != null) {
/* 471 */         stringBuffer.append(c);
/*     */       } 
/*     */     } 
/* 474 */     return (stringBuffer == null) ? paramString : stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isEscaped(String paramString, int paramInt) {
/* 482 */     if (paramString == null || paramString.length() <= paramInt + 2) {
/* 483 */       return false;
/*     */     }
/* 485 */     return (paramString.charAt(paramInt) == '%' && 
/* 486 */       match(paramString.charAt(paramInt + 1), L_HEX, H_HEX) && 
/* 487 */       match(paramString.charAt(paramInt + 2), L_HEX, H_HEX));
/*     */   }
/*     */   
/*     */   private static void appendEncoded(StringBuffer paramStringBuffer, char paramChar) {
/* 491 */     ByteBuffer byteBuffer = null;
/*     */     
/*     */     try {
/* 494 */       byteBuffer = ThreadLocalCoders.encoderFor("UTF-8").encode(CharBuffer.wrap("" + paramChar));
/* 495 */     } catch (CharacterCodingException characterCodingException) {
/*     */       assert false;
/*     */     } 
/* 498 */     while (byteBuffer.hasRemaining()) {
/* 499 */       int i = byteBuffer.get() & 0xFF;
/* 500 */       if (i >= 128) {
/* 501 */         appendEscape(paramStringBuffer, (byte)i); continue;
/*     */       } 
/* 503 */       paramStringBuffer.append((char)i);
/*     */     } 
/*     */   }
/*     */   
/* 507 */   private static final char[] hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void appendEscape(StringBuffer paramStringBuffer, byte paramByte) {
/* 513 */     paramStringBuffer.append('%');
/* 514 */     paramStringBuffer.append(hexDigits[paramByte >> 4 & 0xF]);
/* 515 */     paramStringBuffer.append(hexDigits[paramByte >> 0 & 0xF]);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean match(char paramChar, long paramLong1, long paramLong2) {
/* 520 */     if (paramChar < '@')
/* 521 */       return ((1L << paramChar & paramLong1) != 0L); 
/* 522 */     if (paramChar < '')
/* 523 */       return ((1L << paramChar - 64 & paramLong2) != 0L); 
/* 524 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkPath(String paramString1, String paramString2, String paramString3) throws URISyntaxException {
/* 532 */     if (paramString2 != null && 
/* 533 */       paramString3 != null && paramString3
/* 534 */       .length() > 0 && paramString3.charAt(0) != '/') {
/* 535 */       throw new URISyntaxException(paramString1, "Relative path in absolute URI");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lowMask(char paramChar1, char paramChar2) {
/* 546 */     long l = 0L;
/* 547 */     int i = Math.max(Math.min(paramChar1, 63), 0);
/* 548 */     int j = Math.max(Math.min(paramChar2, 63), 0);
/* 549 */     for (int k = i; k <= j; k++)
/* 550 */       l |= 1L << k; 
/* 551 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long lowMask(String paramString) {
/* 556 */     int i = paramString.length();
/* 557 */     long l = 0L;
/* 558 */     for (byte b = 0; b < i; b++) {
/* 559 */       char c = paramString.charAt(b);
/* 560 */       if (c < '@')
/* 561 */         l |= 1L << c; 
/*     */     } 
/* 563 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long highMask(char paramChar1, char paramChar2) {
/* 569 */     long l = 0L;
/* 570 */     int i = Math.max(Math.min(paramChar1, 127), 64) - 64;
/* 571 */     int j = Math.max(Math.min(paramChar2, 127), 64) - 64;
/* 572 */     for (int k = i; k <= j; k++)
/* 573 */       l |= 1L << k; 
/* 574 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long highMask(String paramString) {
/* 579 */     int i = paramString.length();
/* 580 */     long l = 0L;
/* 581 */     for (byte b = 0; b < i; b++) {
/* 582 */       char c = paramString.charAt(b);
/* 583 */       if (c >= '@' && c < '')
/* 584 */         l |= 1L << c - 64; 
/*     */     } 
/* 586 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 594 */   private static final long L_DIGIT = lowMask('0', '9');
/*     */ 
/*     */   
/*     */   private static final long H_DIGIT = 0L;
/*     */   
/* 599 */   private static final long L_HEX = L_DIGIT;
/* 600 */   private static final long H_HEX = highMask('A', 'F') | highMask('a', 'f');
/*     */ 
/*     */   
/*     */   private static final long L_UPALPHA = 0L;
/*     */ 
/*     */   
/* 606 */   private static final long H_UPALPHA = highMask('A', 'Z');
/*     */ 
/*     */   
/*     */   private static final long L_LOWALPHA = 0L;
/*     */ 
/*     */   
/* 612 */   private static final long H_LOWALPHA = highMask('a', 'z');
/*     */   
/*     */   private static final long L_ALPHA = 0L;
/*     */   
/* 616 */   private static final long H_ALPHA = H_LOWALPHA | H_UPALPHA;
/*     */ 
/*     */   
/* 619 */   private static final long L_ALPHANUM = L_DIGIT | 0x0L;
/* 620 */   private static final long H_ALPHANUM = 0x0L | H_ALPHA;
/*     */ 
/*     */ 
/*     */   
/* 624 */   private static final long L_MARK = lowMask("-_.!~*'()");
/* 625 */   private static final long H_MARK = highMask("-_.!~*'()");
/*     */ 
/*     */   
/* 628 */   private static final long L_UNRESERVED = L_ALPHANUM | L_MARK;
/* 629 */   private static final long H_UNRESERVED = H_ALPHANUM | H_MARK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 634 */   private static final long L_RESERVED = lowMask(";/?:@&=+$,[]");
/* 635 */   private static final long H_RESERVED = highMask(";/?:@&=+$,[]");
/*     */ 
/*     */   
/*     */   private static final long L_ESCAPED = 1L;
/*     */ 
/*     */   
/*     */   private static final long H_ESCAPED = 0L;
/*     */   
/* 643 */   private static final long L_DASH = lowMask("-");
/* 644 */   private static final long H_DASH = highMask("-");
/*     */ 
/*     */   
/* 647 */   private static final long L_URIC = L_RESERVED | L_UNRESERVED | 0x1L;
/* 648 */   private static final long H_URIC = H_RESERVED | H_UNRESERVED | 0x0L;
/*     */ 
/*     */ 
/*     */   
/* 652 */   private static final long L_PCHAR = L_UNRESERVED | 0x1L | 
/* 653 */     lowMask(":@&=+$,");
/* 654 */   private static final long H_PCHAR = H_UNRESERVED | 0x0L | 
/* 655 */     highMask(":@&=+$,");
/*     */ 
/*     */   
/* 658 */   private static final long L_PATH = L_PCHAR | lowMask(";/");
/* 659 */   private static final long H_PATH = H_PCHAR | highMask(";/");
/*     */ 
/*     */ 
/*     */   
/* 663 */   private static final long L_USERINFO = L_UNRESERVED | 0x1L | 
/* 664 */     lowMask(";:&=+$,");
/* 665 */   private static final long H_USERINFO = H_UNRESERVED | 0x0L | 
/* 666 */     highMask(";:&=+$,");
/*     */ 
/*     */ 
/*     */   
/* 670 */   private static final long L_REG_NAME = L_UNRESERVED | 0x1L | 
/* 671 */     lowMask("$,;:@&=+");
/* 672 */   private static final long H_REG_NAME = H_UNRESERVED | 0x0L | 
/* 673 */     highMask("$,;:@&=+");
/*     */ 
/*     */   
/* 676 */   private static final long L_SERVER = L_USERINFO | L_ALPHANUM | L_DASH | 
/* 677 */     lowMask(".:@[]");
/* 678 */   private static final long H_SERVER = H_USERINFO | H_ALPHANUM | H_DASH | 
/* 679 */     highMask(".:@[]");
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/ParseUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */