/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.ParseException;
/*     */ import sun.net.idn.Punycode;
/*     */ import sun.net.idn.StringPrep;
/*     */ import sun.text.normalizer.UCharacterIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IDN
/*     */ {
/*     */   public static final int ALLOW_UNASSIGNED = 1;
/*     */   public static final int USE_STD3_ASCII_RULES = 2;
/*     */   private static final String ACE_PREFIX = "xn--";
/*     */   
/*     */   public static String toASCII(String paramString, int paramInt) {
/* 113 */     int i = 0, j = 0;
/* 114 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 116 */     if (isRootLabel(paramString)) {
/* 117 */       return ".";
/*     */     }
/*     */     
/* 120 */     while (i < paramString.length()) {
/* 121 */       j = searchDots(paramString, i);
/* 122 */       stringBuffer.append(toASCIIInternal(paramString.substring(i, j), paramInt));
/* 123 */       if (j != paramString.length())
/*     */       {
/* 125 */         stringBuffer.append('.');
/*     */       }
/* 127 */       i = j + 1;
/*     */     } 
/*     */     
/* 130 */     return stringBuffer.toString();
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
/*     */   public static String toASCII(String paramString) {
/* 151 */     return toASCII(paramString, 0);
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
/*     */   public static String toUnicode(String paramString, int paramInt) {
/* 174 */     int i = 0, j = 0;
/* 175 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 177 */     if (isRootLabel(paramString)) {
/* 178 */       return ".";
/*     */     }
/*     */     
/* 181 */     while (i < paramString.length()) {
/* 182 */       j = searchDots(paramString, i);
/* 183 */       stringBuffer.append(toUnicodeInternal(paramString.substring(i, j), paramInt));
/* 184 */       if (j != paramString.length())
/*     */       {
/* 186 */         stringBuffer.append('.');
/*     */       }
/* 188 */       i = j + 1;
/*     */     } 
/*     */     
/* 191 */     return stringBuffer.toString();
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
/*     */   public static String toUnicode(String paramString) {
/* 210 */     return toUnicode(paramString, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   private static final int ACE_PREFIX_LENGTH = "xn--".length();
/*     */ 
/*     */   
/*     */   private static final int MAX_LABEL_LENGTH = 63;
/*     */   
/* 223 */   private static StringPrep namePrep = null;
/*     */   
/*     */   static {
/* 226 */     InputStream inputStream = null;
/*     */ 
/*     */     
/*     */     try {
/* 230 */       if (System.getSecurityManager() != null) {
/* 231 */         inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */               public InputStream run() {
/* 233 */                 return StringPrep.class.getResourceAsStream("uidna.spp");
/*     */               }
/*     */             });
/*     */       } else {
/* 237 */         inputStream = StringPrep.class.getResourceAsStream("uidna.spp");
/*     */       } 
/*     */       
/* 240 */       namePrep = new StringPrep(inputStream);
/* 241 */       inputStream.close();
/* 242 */     } catch (IOException iOException) {
/*     */       assert false;
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
/*     */   private static String toASCIIInternal(String paramString, int paramInt) {
/*     */     StringBuffer stringBuffer;
/* 264 */     boolean bool = isAllASCII(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     if (!bool) {
/* 270 */       UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(paramString);
/*     */       try {
/* 272 */         stringBuffer = namePrep.prepare(uCharacterIterator, paramInt);
/* 273 */       } catch (ParseException parseException) {
/* 274 */         throw new IllegalArgumentException(parseException);
/*     */       } 
/*     */     } else {
/* 277 */       stringBuffer = new StringBuffer(paramString);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 282 */     if (stringBuffer.length() == 0) {
/* 283 */       throw new IllegalArgumentException("Empty label is not a legal name");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     boolean bool1 = ((paramInt & 0x2) != 0) ? true : false;
/* 292 */     if (bool1) {
/* 293 */       for (byte b = 0; b < stringBuffer.length(); b++) {
/* 294 */         char c = stringBuffer.charAt(b);
/* 295 */         if (isNonLDHAsciiCodePoint(c)) {
/* 296 */           throw new IllegalArgumentException("Contains non-LDH ASCII characters");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 301 */       if (stringBuffer.charAt(0) == '-' || stringBuffer
/* 302 */         .charAt(stringBuffer.length() - 1) == '-')
/*     */       {
/* 304 */         throw new IllegalArgumentException("Has leading or trailing hyphen");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 309 */     if (!bool)
/*     */     {
/*     */       
/* 312 */       if (!isAllASCII(stringBuffer.toString()))
/*     */       {
/*     */         
/* 315 */         if (!startsWithACEPrefix(stringBuffer)) {
/*     */ 
/*     */           
/*     */           try {
/*     */             
/* 320 */             stringBuffer = Punycode.encode(stringBuffer, null);
/* 321 */           } catch (ParseException parseException) {
/* 322 */             throw new IllegalArgumentException(parseException);
/*     */           } 
/*     */           
/* 325 */           stringBuffer = toASCIILower(stringBuffer);
/*     */ 
/*     */ 
/*     */           
/* 329 */           stringBuffer.insert(0, "xn--");
/*     */         } else {
/* 331 */           throw new IllegalArgumentException("The input starts with the ACE Prefix");
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     if (stringBuffer.length() > 63) {
/* 340 */       throw new IllegalArgumentException("The label in the input is too long");
/*     */     }
/*     */     
/* 343 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toUnicodeInternal(String paramString, int paramInt) {
/*     */     StringBuffer stringBuffer;
/* 350 */     Object object = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     boolean bool = isAllASCII(paramString);
/*     */     
/* 357 */     if (!bool) {
/*     */ 
/*     */       
/*     */       try {
/* 361 */         UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(paramString);
/* 362 */         stringBuffer = namePrep.prepare(uCharacterIterator, paramInt);
/* 363 */       } catch (Exception exception) {
/*     */         
/* 365 */         return paramString;
/*     */       } 
/*     */     } else {
/* 368 */       stringBuffer = new StringBuffer(paramString);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 373 */     if (startsWithACEPrefix(stringBuffer)) {
/*     */ 
/*     */ 
/*     */       
/* 377 */       String str = stringBuffer.substring(ACE_PREFIX_LENGTH, stringBuffer.length());
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 382 */         StringBuffer stringBuffer1 = Punycode.decode(new StringBuffer(str), null);
/*     */ 
/*     */ 
/*     */         
/* 386 */         String str1 = toASCII(stringBuffer1.toString(), paramInt);
/*     */ 
/*     */ 
/*     */         
/* 390 */         if (str1.equalsIgnoreCase(stringBuffer.toString()))
/*     */         {
/*     */           
/* 393 */           return stringBuffer1.toString();
/*     */         }
/* 395 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     return paramString;
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
/*     */   private static boolean isNonLDHAsciiCodePoint(int paramInt) {
/* 415 */     return ((0 <= paramInt && paramInt <= 44) || (46 <= paramInt && paramInt <= 47) || (58 <= paramInt && paramInt <= 64) || (91 <= paramInt && paramInt <= 96) || (123 <= paramInt && paramInt <= 127));
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
/*     */   private static int searchDots(String paramString, int paramInt) {
/*     */     int i;
/* 430 */     for (i = paramInt; i < paramString.length() && 
/* 431 */       !isLabelSeparator(paramString.charAt(i)); i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isRootLabel(String paramString) {
/* 443 */     return (paramString.length() == 1 && isLabelSeparator(paramString.charAt(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLabelSeparator(char paramChar) {
/* 450 */     return (paramChar == '.' || paramChar == '。' || paramChar == '．' || paramChar == '｡');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAllASCII(String paramString) {
/* 457 */     boolean bool = true;
/* 458 */     for (byte b = 0; b < paramString.length(); b++) {
/* 459 */       char c = paramString.charAt(b);
/* 460 */       if (c > '') {
/* 461 */         bool = false;
/*     */         break;
/*     */       } 
/*     */     } 
/* 465 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean startsWithACEPrefix(StringBuffer paramStringBuffer) {
/* 472 */     boolean bool = true;
/*     */     
/* 474 */     if (paramStringBuffer.length() < ACE_PREFIX_LENGTH) {
/* 475 */       return false;
/*     */     }
/* 477 */     for (byte b = 0; b < ACE_PREFIX_LENGTH; b++) {
/* 478 */       if (toASCIILower(paramStringBuffer.charAt(b)) != "xn--".charAt(b)) {
/* 479 */         bool = false;
/*     */       }
/*     */     } 
/* 482 */     return bool;
/*     */   }
/*     */   
/*     */   private static char toASCIILower(char paramChar) {
/* 486 */     if ('A' <= paramChar && paramChar <= 'Z') {
/* 487 */       return (char)(paramChar + 97 - 65);
/*     */     }
/* 489 */     return paramChar;
/*     */   }
/*     */   
/*     */   private static StringBuffer toASCIILower(StringBuffer paramStringBuffer) {
/* 493 */     StringBuffer stringBuffer = new StringBuffer();
/* 494 */     for (byte b = 0; b < paramStringBuffer.length(); b++) {
/* 495 */       stringBuffer.append(toASCIILower(paramStringBuffer.charAt(b)));
/*     */     }
/* 497 */     return stringBuffer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/IDN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */