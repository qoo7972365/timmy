/*     */ package sun.invoke.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BytecodeName
/*     */ {
/*     */   public static String toBytecodeName(String paramString) {
/* 266 */     String str = mangle(paramString);
/* 267 */     assert str == paramString || looksMangled(str) : str;
/* 268 */     assert paramString.equals(toSourceName(str)) : paramString;
/* 269 */     return str;
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
/*     */   public static String toSourceName(String paramString) {
/* 281 */     checkSafeBytecodeName(paramString);
/* 282 */     String str = paramString;
/* 283 */     if (looksMangled(paramString)) {
/* 284 */       str = demangle(paramString);
/* 285 */       assert paramString.equals(mangle(str)) : paramString + " => " + str + " => " + mangle(str);
/*     */     } 
/* 287 */     return str;
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
/*     */   public static Object[] parseBytecodeName(String paramString) {
/* 305 */     int i = paramString.length();
/* 306 */     Object[] arrayOfObject = null;
/* 307 */     for (byte b = 0; b <= 1; b++) {
/* 308 */       byte b1 = 0;
/* 309 */       int j = 0;
/* 310 */       for (byte b2 = 0; b2 <= i; b2++) {
/* 311 */         int k = -1;
/* 312 */         if (b2 < i) {
/* 313 */           k = "\\/.;:$[]<>".indexOf(paramString.charAt(b2));
/* 314 */           if (k < 1)
/*     */             continue; 
/*     */         } 
/* 317 */         if (j < b2) {
/*     */           
/* 319 */           if (b != 0)
/* 320 */             arrayOfObject[b1] = toSourceName(paramString.substring(j, b2)); 
/* 321 */           b1++;
/* 322 */           j = b2 + 1;
/*     */         } 
/* 324 */         if (k >= 1) {
/* 325 */           if (b != 0)
/* 326 */             arrayOfObject[b1] = DANGEROUS_CHARS_CA[k]; 
/* 327 */           b1++;
/* 328 */           j = b2 + 1;
/*     */         }  continue;
/*     */       } 
/* 331 */       if (b != 0)
/*     */         break; 
/* 333 */       arrayOfObject = new Object[b1];
/* 334 */       if (b1 <= 1 && j == 0) {
/* 335 */         if (b1 != 0) arrayOfObject[0] = toSourceName(paramString); 
/*     */         break;
/*     */       } 
/*     */     } 
/* 339 */     return arrayOfObject;
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
/*     */   public static String unparseBytecodeName(Object[] paramArrayOfObject) {
/* 354 */     Object[] arrayOfObject = paramArrayOfObject;
/* 355 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 356 */       Object object = paramArrayOfObject[b];
/* 357 */       if (object instanceof String) {
/* 358 */         String str = toBytecodeName((String)object);
/* 359 */         if (b == 0 && paramArrayOfObject.length == 1)
/* 360 */           return str; 
/* 361 */         if (str != object) {
/* 362 */           if (paramArrayOfObject == arrayOfObject)
/* 363 */             paramArrayOfObject = (Object[])paramArrayOfObject.clone(); 
/* 364 */           paramArrayOfObject[b] = object = str;
/*     */         } 
/*     */       } 
/*     */     } 
/* 368 */     return appendAll(paramArrayOfObject);
/*     */   }
/*     */   private static String appendAll(Object[] paramArrayOfObject) {
/* 371 */     if (paramArrayOfObject.length <= 1) {
/* 372 */       if (paramArrayOfObject.length == 1) {
/* 373 */         return String.valueOf(paramArrayOfObject[0]);
/*     */       }
/* 375 */       return "";
/*     */     } 
/* 377 */     int i = 0;
/* 378 */     for (Object object : paramArrayOfObject) {
/* 379 */       if (object instanceof String) {
/* 380 */         i += String.valueOf(object).length();
/*     */       } else {
/* 382 */         i++;
/*     */       } 
/* 384 */     }  StringBuilder stringBuilder = new StringBuilder(i);
/* 385 */     for (Object object : paramArrayOfObject) {
/* 386 */       stringBuilder.append(object);
/*     */     }
/* 388 */     return stringBuilder.toString();
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
/*     */   public static String toDisplayName(String paramString) {
/* 409 */     Object[] arrayOfObject = parseBytecodeName(paramString);
/* 410 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 411 */       if (arrayOfObject[b] instanceof String) {
/*     */         
/* 413 */         String str = (String)arrayOfObject[b];
/*     */ 
/*     */         
/* 416 */         if (!isJavaIdent(str) || str.indexOf('$') >= 0)
/* 417 */           arrayOfObject[b] = quoteDisplay(str); 
/*     */       } 
/*     */     } 
/* 420 */     return appendAll(arrayOfObject);
/*     */   }
/*     */   private static boolean isJavaIdent(String paramString) {
/* 423 */     int i = paramString.length();
/* 424 */     if (i == 0) return false; 
/* 425 */     if (!Character.isJavaIdentifierStart(paramString.charAt(0)))
/* 426 */       return false; 
/* 427 */     for (byte b = 1; b < i; b++) {
/* 428 */       if (!Character.isJavaIdentifierPart(paramString.charAt(b)))
/* 429 */         return false; 
/*     */     } 
/* 431 */     return true;
/*     */   }
/*     */   
/*     */   private static String quoteDisplay(String paramString) {
/* 435 */     return "'" + paramString.replaceAll("['\\\\]", "\\\\$0") + "'";
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkSafeBytecodeName(String paramString) throws IllegalArgumentException {
/* 440 */     if (!isSafeBytecodeName(paramString)) {
/* 441 */       throw new IllegalArgumentException(paramString);
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
/*     */   public static boolean isSafeBytecodeName(String paramString) {
/* 454 */     if (paramString.length() == 0) return false;
/*     */     
/* 456 */     for (char c : DANGEROUS_CHARS_A) {
/* 457 */       if (c != ESCAPE_C && 
/* 458 */         paramString.indexOf(c) >= 0) return false; 
/*     */     } 
/* 460 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSafeBytecodeChar(char paramChar) {
/* 471 */     return ("\\/.;:$[]<>".indexOf(paramChar) < 1);
/*     */   }
/*     */   
/*     */   private static boolean looksMangled(String paramString) {
/* 475 */     return (paramString.charAt(0) == ESCAPE_C);
/*     */   }
/*     */   
/*     */   private static String mangle(String paramString) {
/* 479 */     if (paramString.length() == 0) {
/* 480 */       return NULL_ESCAPE;
/*     */     }
/*     */     
/* 483 */     StringBuilder stringBuilder = null; byte b;
/*     */     int i;
/* 485 */     for (b = 0, i = paramString.length(); b < i; b++) {
/* 486 */       char c = paramString.charAt(b);
/*     */       
/* 488 */       boolean bool = false;
/* 489 */       if (c == ESCAPE_C) {
/* 490 */         if (b + 1 < i) {
/* 491 */           char c1 = paramString.charAt(b + 1);
/* 492 */           if ((b == 0 && c1 == NULL_ESCAPE_C) || c1 != 
/* 493 */             originalOfReplacement(c1))
/*     */           {
/* 495 */             bool = true;
/*     */           }
/*     */         } 
/*     */       } else {
/* 499 */         bool = isDangerous(c);
/*     */       } 
/*     */       
/* 502 */       if (!bool) {
/* 503 */         if (stringBuilder != null) stringBuilder.append(c);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 508 */         if (stringBuilder == null) {
/* 509 */           stringBuilder = new StringBuilder(paramString.length() + 10);
/*     */           
/* 511 */           if (paramString.charAt(0) != ESCAPE_C && b > 0) {
/* 512 */             stringBuilder.append(NULL_ESCAPE);
/*     */           }
/* 514 */           stringBuilder.append(paramString.substring(0, b));
/*     */         } 
/*     */ 
/*     */         
/* 518 */         stringBuilder.append(ESCAPE_C);
/* 519 */         stringBuilder.append(replacementOf(c));
/*     */       } 
/*     */     } 
/* 522 */     if (stringBuilder != null) return stringBuilder.toString();
/*     */     
/* 524 */     return paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String demangle(String paramString) {
/* 529 */     StringBuilder stringBuilder = null;
/*     */     
/* 531 */     byte b1 = 0;
/* 532 */     if (paramString.startsWith(NULL_ESCAPE))
/* 533 */       b1 = 2;  byte b2;
/*     */     int i;
/* 535 */     for (b2 = b1, i = paramString.length(); b2 < i; b2++) {
/* 536 */       char c = paramString.charAt(b2);
/*     */       
/* 538 */       if (c == ESCAPE_C && b2 + 1 < i) {
/*     */         
/* 540 */         char c1 = paramString.charAt(b2 + 1);
/* 541 */         char c2 = originalOfReplacement(c1);
/* 542 */         if (c2 != c1) {
/*     */           
/* 544 */           if (stringBuilder == null) {
/* 545 */             stringBuilder = new StringBuilder(paramString.length());
/*     */             
/* 547 */             stringBuilder.append(paramString.substring(b1, b2));
/*     */           } 
/* 549 */           b2++;
/* 550 */           c = c2;
/*     */         } 
/*     */       } 
/*     */       
/* 554 */       if (stringBuilder != null) {
/* 555 */         stringBuilder.append(c);
/*     */       }
/*     */     } 
/* 558 */     if (stringBuilder != null) return stringBuilder.toString();
/*     */     
/* 560 */     return paramString.substring(b1);
/*     */   }
/*     */   
/* 563 */   static char ESCAPE_C = '\\';
/*     */   
/* 565 */   static char NULL_ESCAPE_C = '=';
/* 566 */   static String NULL_ESCAPE = ESCAPE_C + "" + NULL_ESCAPE_C;
/*     */   
/*     */   static final String DANGEROUS_CHARS = "\\/.;:$[]<>";
/*     */   static final String REPLACEMENT_CHARS = "-|,?!%{}^_";
/*     */   static final int DANGEROUS_CHAR_FIRST_INDEX = 1;
/* 571 */   static char[] DANGEROUS_CHARS_A = "\\/.;:$[]<>".toCharArray();
/* 572 */   static char[] REPLACEMENT_CHARS_A = "-|,?!%{}^_".toCharArray(); static final Character[] DANGEROUS_CHARS_CA;
/*     */   
/*     */   static {
/* 575 */     Character[] arrayOfCharacter = new Character["\\/.;:$[]<>".length()];
/* 576 */     for (byte b = 0; b < arrayOfCharacter.length; b++)
/* 577 */       arrayOfCharacter[b] = Character.valueOf("\\/.;:$[]<>".charAt(b)); 
/* 578 */     DANGEROUS_CHARS_CA = arrayOfCharacter;
/*     */   }
/*     */   
/* 581 */   static final long[] SPECIAL_BITMAP = new long[2];
/*     */   static {
/* 583 */     String str = "\\/.;:$[]<>-|,?!%{}^_";
/*     */     
/* 585 */     for (char c : str.toCharArray())
/* 586 */       SPECIAL_BITMAP[c >>> 6] = SPECIAL_BITMAP[c >>> 6] | 1L << c; 
/*     */   }
/*     */   
/*     */   static boolean isSpecial(char paramChar) {
/* 590 */     if (paramChar >>> 6 < SPECIAL_BITMAP.length) {
/* 591 */       return ((SPECIAL_BITMAP[paramChar >>> 6] >> paramChar & 0x1L) != 0L);
/*     */     }
/* 593 */     return false;
/*     */   }
/*     */   static char replacementOf(char paramChar) {
/* 596 */     if (!isSpecial(paramChar)) return paramChar; 
/* 597 */     int i = "\\/.;:$[]<>".indexOf(paramChar);
/* 598 */     if (i < 0) return paramChar; 
/* 599 */     return "-|,?!%{}^_".charAt(i);
/*     */   }
/*     */   static char originalOfReplacement(char paramChar) {
/* 602 */     if (!isSpecial(paramChar)) return paramChar; 
/* 603 */     int i = "-|,?!%{}^_".indexOf(paramChar);
/* 604 */     if (i < 0) return paramChar; 
/* 605 */     return "\\/.;:$[]<>".charAt(i);
/*     */   }
/*     */   static boolean isDangerous(char paramChar) {
/* 608 */     if (!isSpecial(paramChar)) return false; 
/* 609 */     return ("\\/.;:$[]<>".indexOf(paramChar) >= 1);
/*     */   }
/*     */   static int indexOfDangerousChar(String paramString, int paramInt) {
/* 612 */     for (int i = paramInt, j = paramString.length(); i < j; i++) {
/* 613 */       if (isDangerous(paramString.charAt(i)))
/* 614 */         return i; 
/*     */     } 
/* 616 */     return -1;
/*     */   }
/*     */   static int lastIndexOfDangerousChar(String paramString, int paramInt) {
/* 619 */     for (int i = Math.min(paramInt, paramString.length() - 1); i >= 0; i--) {
/* 620 */       if (isDangerous(paramString.charAt(i)))
/* 621 */         return i; 
/*     */     } 
/* 623 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/invoke/util/BytecodeName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */