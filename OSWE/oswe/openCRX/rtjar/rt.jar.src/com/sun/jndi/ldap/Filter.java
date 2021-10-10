/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.InvalidSearchFilterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Filter
/*     */ {
/*     */   private static final boolean dbg = false;
/*     */   
/*     */   static void encodeFilterString(BerEncoder paramBerEncoder, String paramString, boolean paramBoolean) throws IOException, NamingException {
/*     */     byte[] arrayOfByte;
/*  56 */     if (paramString == null || paramString.equals("")) {
/*  57 */       throw new InvalidSearchFilterException("Empty filter");
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (paramBoolean) {
/*  62 */       arrayOfByte = paramString.getBytes("UTF8");
/*     */     } else {
/*  64 */       arrayOfByte = paramString.getBytes("8859_1");
/*     */     } 
/*  66 */     int i = arrayOfByte.length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     encodeFilter(paramBerEncoder, arrayOfByte, 0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void encodeFilter(BerEncoder paramBerEncoder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException, NamingException {
/*  85 */     if (paramInt2 - paramInt1 <= 0) {
/*  86 */       throw new InvalidSearchFilterException("Empty filter");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     byte b = 0;
/*     */     
/*  95 */     int[] arrayOfInt = new int[1];
/*     */     
/*  97 */     for (arrayOfInt[0] = paramInt1; arrayOfInt[0] < paramInt2; ) {
/*  98 */       int i; byte b1; boolean bool; switch (paramArrayOfbyte[arrayOfInt[0]]) {
/*     */         case 40:
/* 100 */           arrayOfInt[0] = arrayOfInt[0] + 1;
/* 101 */           b++;
/* 102 */           switch (paramArrayOfbyte[arrayOfInt[0]]) {
/*     */             case 38:
/* 104 */               encodeComplexFilter(paramBerEncoder, paramArrayOfbyte, 160, arrayOfInt, paramInt2);
/*     */ 
/*     */               
/* 107 */               b--;
/*     */               break;
/*     */             
/*     */             case 124:
/* 111 */               encodeComplexFilter(paramBerEncoder, paramArrayOfbyte, 161, arrayOfInt, paramInt2);
/*     */ 
/*     */               
/* 114 */               b--;
/*     */               break;
/*     */             
/*     */             case 33:
/* 118 */               encodeComplexFilter(paramBerEncoder, paramArrayOfbyte, 162, arrayOfInt, paramInt2);
/*     */ 
/*     */               
/* 121 */               b--;
/*     */               break;
/*     */           } 
/*     */           
/* 125 */           b1 = 1;
/* 126 */           bool = false;
/* 127 */           i = arrayOfInt[0];
/* 128 */           while (i < paramInt2 && b1) {
/* 129 */             if (!bool)
/* 130 */               if (paramArrayOfbyte[i] == 40) {
/* 131 */                 b1++;
/* 132 */               } else if (paramArrayOfbyte[i] == 41) {
/* 133 */                 b1--;
/*     */               }  
/* 135 */             if (paramArrayOfbyte[i] == 92 && !bool) {
/* 136 */               bool = true;
/*     */             } else {
/* 138 */               bool = false;
/* 139 */             }  if (b1 > 0)
/* 140 */               i++; 
/*     */           } 
/* 142 */           if (b1 != 0) {
/* 143 */             throw new InvalidSearchFilterException("Unbalanced parenthesis");
/*     */           }
/*     */           
/* 146 */           encodeSimpleFilter(paramBerEncoder, paramArrayOfbyte, arrayOfInt[0], i);
/*     */ 
/*     */           
/* 149 */           arrayOfInt[0] = i + 1;
/*     */           
/* 151 */           b--;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 41:
/* 161 */           paramBerEncoder.endSeq();
/* 162 */           arrayOfInt[0] = arrayOfInt[0] + 1;
/* 163 */           b--;
/*     */           break;
/*     */         
/*     */         case 32:
/* 167 */           arrayOfInt[0] = arrayOfInt[0] + 1;
/*     */           break;
/*     */         
/*     */         default:
/* 171 */           encodeSimpleFilter(paramBerEncoder, paramArrayOfbyte, arrayOfInt[0], paramInt2);
/* 172 */           arrayOfInt[0] = paramInt2;
/*     */           break;
/*     */       } 
/*     */       
/* 176 */       if (b < 0) {
/* 177 */         throw new InvalidSearchFilterException("Unbalanced parenthesis");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 182 */     if (b != 0) {
/* 183 */       throw new InvalidSearchFilterException("Unbalanced parenthesis");
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
/*     */   private static int hexchar2int(byte paramByte) {
/* 198 */     if (paramByte >= 48 && paramByte <= 57) {
/* 199 */       return paramByte - 48;
/*     */     }
/* 201 */     if (paramByte >= 65 && paramByte <= 70) {
/* 202 */       return paramByte - 65 + 10;
/*     */     }
/* 204 */     if (paramByte >= 97 && paramByte <= 102) {
/* 205 */       return paramByte - 97 + 10;
/*     */     }
/* 207 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] unescapeFilterValue(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws NamingException {
/* 213 */     boolean bool1 = false, bool2 = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     int i = paramInt2 - paramInt1;
/* 222 */     byte[] arrayOfByte1 = new byte[i];
/* 223 */     byte b = 0;
/* 224 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 225 */       byte b1 = paramArrayOfbyte[j];
/* 226 */       if (bool1) {
/*     */         int k;
/* 228 */         if ((k = hexchar2int(b1)) < 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 235 */           if (bool2) {
/*     */             
/* 237 */             bool1 = false;
/* 238 */             arrayOfByte1[b++] = b1;
/*     */           } else {
/*     */             
/* 241 */             throw new InvalidSearchFilterException("invalid escape sequence: " + paramArrayOfbyte);
/*     */           }
/*     */         
/* 244 */         } else if (bool2) {
/* 245 */           arrayOfByte1[b] = (byte)(k << 4);
/* 246 */           bool2 = false;
/*     */         } else {
/* 248 */           arrayOfByte1[b++] = (byte)(arrayOfByte1[b++] | (byte)k);
/* 249 */           bool1 = false;
/*     */         }
/*     */       
/* 252 */       } else if (b1 != 92) {
/* 253 */         arrayOfByte1[b++] = b1;
/* 254 */         bool1 = false;
/*     */       } else {
/* 256 */         bool2 = bool1 = true;
/*     */       } 
/*     */     } 
/* 259 */     byte[] arrayOfByte2 = new byte[b];
/* 260 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, b);
/*     */ 
/*     */ 
/*     */     
/* 264 */     return arrayOfByte2;
/*     */   }
/*     */   
/*     */   private static int indexOf(byte[] paramArrayOfbyte, char paramChar, int paramInt1, int paramInt2) {
/* 268 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 269 */       if (paramArrayOfbyte[i] == paramChar)
/* 270 */         return i; 
/*     */     } 
/* 272 */     return -1;
/*     */   }
/*     */   
/*     */   private static int indexOf(byte[] paramArrayOfbyte, String paramString, int paramInt1, int paramInt2) {
/* 276 */     int i = indexOf(paramArrayOfbyte, paramString.charAt(0), paramInt1, paramInt2);
/* 277 */     if (i >= 0) {
/* 278 */       for (byte b = 1; b < paramString.length(); b++) {
/* 279 */         if (paramArrayOfbyte[i + b] != paramString.charAt(b)) {
/* 280 */           return -1;
/*     */         }
/*     */       } 
/*     */     }
/* 284 */     return i;
/*     */   }
/*     */   
/*     */   private static int findUnescaped(byte[] paramArrayOfbyte, char paramChar, int paramInt1, int paramInt2) {
/* 288 */     while (paramInt1 < paramInt2) {
/* 289 */       int i = indexOf(paramArrayOfbyte, paramChar, paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 301 */       byte b = 0;
/* 302 */       int j = i - 1;
/* 303 */       while (j >= paramInt1 && paramArrayOfbyte[j] == 92) {
/* 304 */         j--; b++;
/*     */       } 
/*     */       
/* 307 */       if (i == paramInt1 || i == -1 || b % 2 == 0) {
/* 308 */         return i;
/*     */       }
/*     */       
/* 311 */       paramInt1 = i + 1;
/*     */     } 
/* 313 */     return -1;
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
/*     */   private static void encodeSimpleFilter(BerEncoder paramBerEncoder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException, NamingException {
/*     */     int m;
/*     */     char c;
/*     */     int n;
/* 329 */     if ((n = indexOf(paramArrayOfbyte, '=', paramInt1, paramInt2)) == -1) {
/* 330 */       throw new InvalidSearchFilterException("Missing 'equals'");
/*     */     }
/*     */ 
/*     */     
/* 334 */     int i = n + 1;
/* 335 */     int j = paramInt2;
/* 336 */     int k = paramInt1;
/*     */ 
/*     */ 
/*     */     
/* 340 */     switch (paramArrayOfbyte[n - 1]) {
/*     */       case 60:
/* 342 */         c = '¦';
/* 343 */         m = n - 1;
/*     */         break;
/*     */       case 62:
/* 346 */         c = '¥';
/* 347 */         m = n - 1;
/*     */         break;
/*     */       case 126:
/* 350 */         c = '¨';
/* 351 */         m = n - 1;
/*     */         break;
/*     */       case 58:
/* 354 */         c = '©';
/* 355 */         m = n - 1;
/*     */         break;
/*     */       default:
/* 358 */         m = n;
/*     */         
/* 360 */         c = Character.MIN_VALUE;
/*     */         break;
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
/* 387 */     int i1 = -1;
/* 388 */     int i2 = -1;
/* 389 */     if ((paramArrayOfbyte[k] >= 48 && paramArrayOfbyte[k] <= 57) || (paramArrayOfbyte[k] >= 65 && paramArrayOfbyte[k] <= 90) || (paramArrayOfbyte[k] >= 97 && paramArrayOfbyte[k] <= 122)) {
/*     */ 
/*     */ 
/*     */       
/* 393 */       boolean bool = (paramArrayOfbyte[k] >= 48 && paramArrayOfbyte[k] <= 57) ? true : false;
/*     */       
/* 395 */       for (int i3 = k + 1; i3 < m; i3++)
/*     */       {
/* 397 */         if (paramArrayOfbyte[i3] == 59) {
/* 398 */           if (bool && paramArrayOfbyte[i3 - 1] == 46) {
/* 399 */             throw new InvalidSearchFilterException("invalid attribute description");
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 404 */           i1 = i3;
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 409 */         if (paramArrayOfbyte[i3] == 58 && c == '©') {
/* 410 */           if (bool && paramArrayOfbyte[i3 - 1] == 46) {
/* 411 */             throw new InvalidSearchFilterException("invalid attribute description");
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 416 */           i2 = i3;
/*     */           
/*     */           break;
/*     */         } 
/* 420 */         if (bool)
/*     */         {
/* 422 */           if ((paramArrayOfbyte[i3] == 46 && paramArrayOfbyte[i3 - 1] == 46) || (paramArrayOfbyte[i3] != 46 && (paramArrayOfbyte[i3] < 48 || paramArrayOfbyte[i3] > 57)))
/*     */           {
/*     */             
/* 425 */             throw new InvalidSearchFilterException("invalid attribute description");
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 433 */         else if (paramArrayOfbyte[i3] != 45 && paramArrayOfbyte[i3] != 95 && (paramArrayOfbyte[i3] < 48 || paramArrayOfbyte[i3] > 57) && (paramArrayOfbyte[i3] < 65 || paramArrayOfbyte[i3] > 90) && (paramArrayOfbyte[i3] < 97 || paramArrayOfbyte[i3] > 122))
/*     */         {
/*     */ 
/*     */           
/* 437 */           throw new InvalidSearchFilterException("invalid attribute description");
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 442 */     } else if (c == '©' && paramArrayOfbyte[k] == 58) {
/*     */       
/* 444 */       i2 = k;
/*     */     } else {
/* 446 */       throw new InvalidSearchFilterException("invalid attribute description");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 451 */     if (i1 > 0) {
/* 452 */       for (int i3 = i1 + 1; i3 < m; i3++) {
/* 453 */         if (paramArrayOfbyte[i3] == 59) {
/* 454 */           if (paramArrayOfbyte[i3 - 1] == 59) {
/* 455 */             throw new InvalidSearchFilterException("invalid attribute description");
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 462 */           if (paramArrayOfbyte[i3] == 58 && c == '©') {
/* 463 */             if (paramArrayOfbyte[i3 - 1] == 59) {
/* 464 */               throw new InvalidSearchFilterException("invalid attribute description");
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 469 */             i2 = i3;
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */ 
/*     */           
/* 476 */           if (paramArrayOfbyte[i3] != 45 && paramArrayOfbyte[i3] != 95 && (paramArrayOfbyte[i3] < 48 || paramArrayOfbyte[i3] > 57) && (paramArrayOfbyte[i3] < 65 || paramArrayOfbyte[i3] > 90) && (paramArrayOfbyte[i3] < 97 || paramArrayOfbyte[i3] > 122))
/*     */           {
/*     */ 
/*     */             
/* 480 */             throw new InvalidSearchFilterException("invalid attribute description");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 487 */     if (i2 > 0) {
/* 488 */       boolean bool = false;
/* 489 */       for (int i3 = i2 + 1; i3 < m; i3++) {
/* 490 */         if (paramArrayOfbyte[i3] == 58) {
/* 491 */           throw new InvalidSearchFilterException("invalid attribute description");
/*     */         }
/* 493 */         if ((paramArrayOfbyte[i3] >= 48 && paramArrayOfbyte[i3] <= 57) || (paramArrayOfbyte[i3] >= 65 && paramArrayOfbyte[i3] <= 90) || (paramArrayOfbyte[i3] >= 97 && paramArrayOfbyte[i3] <= 122)) {
/*     */ 
/*     */           
/* 496 */           boolean bool1 = (paramArrayOfbyte[i3] >= 48 && paramArrayOfbyte[i3] <= 57) ? true : false;
/*     */           
/* 498 */           for (int i4 = ++i3; i4 < m; i4++, i3++) {
/*     */             
/* 500 */             if (paramArrayOfbyte[i4] == 58) {
/* 501 */               if (bool) {
/* 502 */                 throw new InvalidSearchFilterException("invalid attribute description");
/*     */               }
/*     */               
/* 505 */               if (bool1 && paramArrayOfbyte[i4 - 1] == 46) {
/* 506 */                 throw new InvalidSearchFilterException("invalid attribute description");
/*     */               }
/*     */ 
/*     */               
/* 510 */               bool = true;
/*     */               
/*     */               break;
/*     */             } 
/* 514 */             if (bool1)
/*     */             {
/* 516 */               if ((paramArrayOfbyte[i4] == 46 && paramArrayOfbyte[i4 - 1] == 46) || (paramArrayOfbyte[i4] != 46 && (paramArrayOfbyte[i4] < 48 || paramArrayOfbyte[i4] > 57)))
/*     */               {
/*     */                 
/* 519 */                 throw new InvalidSearchFilterException("invalid attribute description");
/*     */ 
/*     */ 
/*     */               
/*     */               }
/*     */ 
/*     */             
/*     */             }
/* 527 */             else if (paramArrayOfbyte[i4] != 45 && paramArrayOfbyte[i4] != 95 && (paramArrayOfbyte[i4] < 48 || paramArrayOfbyte[i4] > 57) && (paramArrayOfbyte[i4] < 65 || paramArrayOfbyte[i4] > 90) && (paramArrayOfbyte[i4] < 97 || paramArrayOfbyte[i4] > 122))
/*     */             {
/*     */ 
/*     */               
/* 531 */               throw new InvalidSearchFilterException("invalid attribute description");
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 537 */           throw new InvalidSearchFilterException("invalid attribute description");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 544 */     if (paramArrayOfbyte[m - 1] == 46 || paramArrayOfbyte[m - 1] == 59 || paramArrayOfbyte[m - 1] == 58)
/*     */     {
/* 546 */       throw new InvalidSearchFilterException("invalid attribute description");
/*     */     }
/*     */ 
/*     */     
/* 550 */     if (m == n) {
/* 551 */       if (findUnescaped(paramArrayOfbyte, '*', i, j) == -1) {
/* 552 */         c = '£';
/* 553 */       } else if (paramArrayOfbyte[i] == 42 && i == j - 1) {
/*     */         
/* 555 */         c = '';
/*     */       } else {
/* 557 */         encodeSubstringFilter(paramBerEncoder, paramArrayOfbyte, k, m, i, j);
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 563 */     if (c == '') {
/* 564 */       paramBerEncoder.encodeOctetString(paramArrayOfbyte, c, k, m - k);
/* 565 */     } else if (c == '©') {
/* 566 */       encodeExtensibleMatch(paramBerEncoder, paramArrayOfbyte, k, m, i, j);
/*     */     } else {
/*     */       
/* 569 */       paramBerEncoder.beginSeq(c);
/* 570 */       paramBerEncoder.encodeOctetString(paramArrayOfbyte, 4, k, m - k);
/*     */       
/* 572 */       paramBerEncoder.encodeOctetString(
/* 573 */           unescapeFilterValue(paramArrayOfbyte, i, j), 4);
/*     */       
/* 575 */       paramBerEncoder.endSeq();
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
/*     */   private static void encodeSubstringFilter(BerEncoder paramBerEncoder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IOException, NamingException {
/* 593 */     paramBerEncoder.beginSeq(164);
/* 594 */     paramBerEncoder.encodeOctetString(paramArrayOfbyte, 4, paramInt1, paramInt2 - paramInt1);
/*     */     
/* 596 */     paramBerEncoder.beginSeq(48);
/*     */     
/* 598 */     int j = paramInt3; int i;
/* 599 */     while ((i = findUnescaped(paramArrayOfbyte, '*', j, paramInt4)) != -1) {
/* 600 */       if (j == paramInt3) {
/* 601 */         if (j < i)
/*     */         {
/*     */ 
/*     */           
/* 605 */           paramBerEncoder.encodeOctetString(
/* 606 */               unescapeFilterValue(paramArrayOfbyte, j, i), 128);
/*     */         
/*     */         }
/*     */       }
/* 610 */       else if (j < i) {
/*     */ 
/*     */         
/* 613 */         paramBerEncoder.encodeOctetString(
/* 614 */             unescapeFilterValue(paramArrayOfbyte, j, i), 129);
/*     */       } 
/*     */ 
/*     */       
/* 618 */       j = i + 1;
/*     */     } 
/* 620 */     if (j < paramInt4)
/*     */     {
/*     */       
/* 623 */       paramBerEncoder.encodeOctetString(
/* 624 */           unescapeFilterValue(paramArrayOfbyte, j, paramInt4), 130);
/*     */     }
/*     */     
/* 627 */     paramBerEncoder.endSeq();
/* 628 */     paramBerEncoder.endSeq();
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
/*     */   private static void encodeComplexFilter(BerEncoder paramBerEncoder, byte[] paramArrayOfbyte, int paramInt1, int[] paramArrayOfint, int paramInt2) throws IOException, NamingException {
/* 652 */     paramArrayOfint[0] = paramArrayOfint[0] + 1;
/*     */     
/* 654 */     paramBerEncoder.beginSeq(paramInt1);
/*     */     
/* 656 */     int[] arrayOfInt = findRightParen(paramArrayOfbyte, paramArrayOfint, paramInt2);
/* 657 */     encodeFilterList(paramBerEncoder, paramArrayOfbyte, paramInt1, arrayOfInt[0], arrayOfInt[1]);
/*     */     
/* 659 */     paramBerEncoder.endSeq();
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
/*     */   private static int[] findRightParen(byte[] paramArrayOfbyte, int[] paramArrayOfint, int paramInt) throws IOException, NamingException {
/* 675 */     byte b = 1;
/* 676 */     boolean bool = false;
/* 677 */     int i = paramArrayOfint[0];
/*     */     
/* 679 */     while (i < paramInt && b) {
/* 680 */       if (!bool)
/* 681 */         if (paramArrayOfbyte[i] == 40) {
/* 682 */           b++;
/* 683 */         } else if (paramArrayOfbyte[i] == 41) {
/* 684 */           b--;
/*     */         }  
/* 686 */       if (paramArrayOfbyte[i] == 92 && !bool) {
/* 687 */         bool = true;
/*     */       } else {
/* 689 */         bool = false;
/* 690 */       }  if (b > 0)
/* 691 */         i++; 
/*     */     } 
/* 693 */     if (b != 0) {
/* 694 */       throw new InvalidSearchFilterException("Unbalanced parenthesis");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 699 */     int[] arrayOfInt = { paramArrayOfint[0], i };
/*     */     
/* 701 */     paramArrayOfint[0] = i + 1;
/*     */     
/* 703 */     return arrayOfInt;
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
/*     */   private static void encodeFilterList(BerEncoder paramBerEncoder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws IOException, NamingException {
/* 718 */     int[] arrayOfInt = new int[1];
/* 719 */     byte b = 0;
/* 720 */     for (arrayOfInt[0] = paramInt2; arrayOfInt[0] < paramInt3; arrayOfInt[0] = arrayOfInt[0] + 1) {
/* 721 */       if (!Character.isSpaceChar((char)paramArrayOfbyte[arrayOfInt[0]])) {
/*     */ 
/*     */         
/* 724 */         if (paramInt1 == 162 && b) {
/* 725 */           throw new InvalidSearchFilterException("Filter (!) cannot be followed by more than one filters");
/*     */         }
/*     */ 
/*     */         
/* 729 */         if (paramArrayOfbyte[arrayOfInt[0]] != 40) {
/*     */ 
/*     */ 
/*     */           
/* 733 */           int[] arrayOfInt1 = findRightParen(paramArrayOfbyte, arrayOfInt, paramInt3);
/*     */ 
/*     */           
/* 736 */           int i = arrayOfInt1[1] - arrayOfInt1[0];
/* 737 */           byte[] arrayOfByte = new byte[i + 2];
/* 738 */           System.arraycopy(paramArrayOfbyte, arrayOfInt1[0], arrayOfByte, 1, i);
/* 739 */           arrayOfByte[0] = 40;
/* 740 */           arrayOfByte[i + 1] = 41;
/* 741 */           encodeFilter(paramBerEncoder, arrayOfByte, 0, arrayOfByte.length);
/*     */           
/* 743 */           b++;
/*     */         } 
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
/*     */   private static void encodeExtensibleMatch(BerEncoder paramBerEncoder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IOException, NamingException {
/* 759 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 764 */     paramBerEncoder.beginSeq(169);
/*     */     
/*     */     int i;
/* 767 */     if ((i = indexOf(paramArrayOfbyte, ':', paramInt1, paramInt2)) >= 0) {
/*     */       int k;
/*     */       
/* 770 */       if ((k = indexOf(paramArrayOfbyte, ":dn", i, paramInt2)) >= 0) {
/* 771 */         bool = true;
/*     */       }
/*     */       
/*     */       int j;
/* 775 */       if ((j = indexOf(paramArrayOfbyte, ':', i + 1, paramInt2)) >= 0 || k == -1)
/*     */       {
/*     */         
/* 778 */         if (k == i) {
/* 779 */           paramBerEncoder.encodeOctetString(paramArrayOfbyte, 129, j + 1, paramInt2 - j + 1);
/*     */         
/*     */         }
/* 782 */         else if (k == j && k >= 0) {
/* 783 */           paramBerEncoder.encodeOctetString(paramArrayOfbyte, 129, i + 1, j - i + 1);
/*     */         }
/*     */         else {
/*     */           
/* 787 */           paramBerEncoder.encodeOctetString(paramArrayOfbyte, 129, i + 1, paramInt2 - i + 1);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 793 */       if (i > paramInt1) {
/* 794 */         paramBerEncoder.encodeOctetString(paramArrayOfbyte, 130, paramInt1, i - paramInt1);
/*     */       }
/*     */     } else {
/*     */       
/* 798 */       paramBerEncoder.encodeOctetString(paramArrayOfbyte, 130, paramInt1, paramInt2 - paramInt1);
/*     */     } 
/*     */ 
/*     */     
/* 802 */     paramBerEncoder.encodeOctetString(
/* 803 */         unescapeFilterValue(paramArrayOfbyte, paramInt3, paramInt4), 131);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 811 */     paramBerEncoder.encodeBoolean(bool, 132);
/*     */     
/* 813 */     paramBerEncoder.endSeq();
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
/* 824 */   private static int dbgIndent = 0; static final int LDAP_FILTER_AND = 160; static final int LDAP_FILTER_OR = 161; static final int LDAP_FILTER_NOT = 162; static final int LDAP_FILTER_EQUALITY = 163; static final int LDAP_FILTER_SUBSTRINGS = 164; static final int LDAP_FILTER_GE = 165; static final int LDAP_FILTER_LE = 166; static final int LDAP_FILTER_PRESENT = 135;
/*     */   
/*     */   private static void dprint(String paramString) {
/* 827 */     dprint(paramString, new byte[0], 0, 0);
/*     */   }
/*     */   static final int LDAP_FILTER_APPROX = 168; static final int LDAP_FILTER_EXT = 169; static final int LDAP_FILTER_EXT_RULE = 129; static final int LDAP_FILTER_EXT_TYPE = 130; static final int LDAP_FILTER_EXT_VAL = 131; static final int LDAP_FILTER_EXT_DN = 132; static final int LDAP_SUBSTRING_INITIAL = 128; static final int LDAP_SUBSTRING_ANY = 129; static final int LDAP_SUBSTRING_FINAL = 130;
/*     */   private static void dprint(String paramString, byte[] paramArrayOfbyte) {
/* 831 */     dprint(paramString, paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   private static void dprint(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 835 */     String str = "  ";
/* 836 */     int i = dbgIndent;
/* 837 */     while (i-- > 0) {
/* 838 */       str = str + "  ";
/*     */     }
/* 840 */     str = str + paramString;
/*     */     
/* 842 */     System.err.print(str);
/* 843 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 844 */       System.err.print((char)paramArrayOfbyte[j]);
/*     */     }
/* 846 */     System.err.println();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */