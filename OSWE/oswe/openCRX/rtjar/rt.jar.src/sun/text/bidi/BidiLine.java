/*     */ package sun.text.bidi;
/*     */ 
/*     */ import java.text.Bidi;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BidiLine
/*     */ {
/*     */   static void setTrailingWSStart(BidiBase paramBidiBase) {
/* 114 */     byte[] arrayOfByte1 = paramBidiBase.dirProps;
/* 115 */     byte[] arrayOfByte2 = paramBidiBase.levels;
/* 116 */     int i = paramBidiBase.length;
/* 117 */     byte b = paramBidiBase.paraLevel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (BidiBase.NoContextRTL(arrayOfByte1[i - 1]) == 7) {
/* 126 */       paramBidiBase.trailingWSStart = i;
/*     */       
/*     */       return;
/*     */     } 
/* 130 */     while (i > 0 && (
/* 131 */       BidiBase.DirPropFlagNC(arrayOfByte1[i - 1]) & BidiBase.MASK_WS) != 0) {
/* 132 */       i--;
/*     */     }
/*     */ 
/*     */     
/* 136 */     while (i > 0 && arrayOfByte2[i - 1] == b) {
/* 137 */       i--;
/*     */     }
/*     */     
/* 140 */     paramBidiBase.trailingWSStart = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Bidi setLine(Bidi paramBidi1, BidiBase paramBidiBase1, Bidi paramBidi2, BidiBase paramBidiBase2, int paramInt1, int paramInt2) {
/* 148 */     BidiBase bidiBase = paramBidiBase2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     int i = bidiBase.length = bidiBase.originalLength = bidiBase.resultLength = paramInt2 - paramInt1;
/*     */ 
/*     */     
/* 159 */     bidiBase.text = new char[i];
/* 160 */     System.arraycopy(paramBidiBase1.text, paramInt1, bidiBase.text, 0, i);
/* 161 */     bidiBase.paraLevel = paramBidiBase1.GetParaLevelAt(paramInt1);
/* 162 */     bidiBase.paraCount = paramBidiBase1.paraCount;
/* 163 */     bidiBase.runs = new BidiRun[0];
/* 164 */     if (paramBidiBase1.controlCount > 0) {
/*     */       
/* 166 */       for (int j = paramInt1; j < paramInt2; j++) {
/* 167 */         if (BidiBase.IsBidiControlChar(paramBidiBase1.text[j])) {
/* 168 */           bidiBase.controlCount++;
/*     */         }
/*     */       } 
/* 171 */       bidiBase.resultLength -= bidiBase.controlCount;
/*     */     } 
/*     */     
/* 174 */     bidiBase.getDirPropsMemory(i);
/* 175 */     bidiBase.dirProps = bidiBase.dirPropsMemory;
/* 176 */     System.arraycopy(paramBidiBase1.dirProps, paramInt1, bidiBase.dirProps, 0, i);
/*     */ 
/*     */     
/* 179 */     bidiBase.getLevelsMemory(i);
/* 180 */     bidiBase.levels = bidiBase.levelsMemory;
/* 181 */     System.arraycopy(paramBidiBase1.levels, paramInt1, bidiBase.levels, 0, i);
/*     */     
/* 183 */     bidiBase.runCount = -1;
/*     */     
/* 185 */     if (paramBidiBase1.direction != 2) {
/*     */       
/* 187 */       bidiBase.direction = paramBidiBase1.direction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 194 */       if (paramBidiBase1.trailingWSStart <= paramInt1) {
/* 195 */         bidiBase.trailingWSStart = 0;
/* 196 */       } else if (paramBidiBase1.trailingWSStart < paramInt2) {
/* 197 */         paramBidiBase1.trailingWSStart -= paramInt1;
/*     */       } else {
/* 199 */         bidiBase.trailingWSStart = i;
/*     */       } 
/*     */     } else {
/* 202 */       byte[] arrayOfByte = bidiBase.levels;
/*     */ 
/*     */ 
/*     */       
/* 206 */       setTrailingWSStart(bidiBase);
/* 207 */       int j = bidiBase.trailingWSStart;
/*     */ 
/*     */       
/* 210 */       if (j == 0) {
/*     */         
/* 212 */         bidiBase.direction = (byte)(bidiBase.paraLevel & 0x1);
/*     */       } else {
/*     */         
/* 215 */         byte b = (byte)(arrayOfByte[0] & 0x1);
/*     */ 
/*     */ 
/*     */         
/* 219 */         if (j < i && (bidiBase.paraLevel & 0x1) != b) {
/*     */ 
/*     */ 
/*     */           
/* 223 */           bidiBase.direction = 2;
/*     */         }
/*     */         else {
/*     */           
/* 227 */           for (int k = 1;; k++) {
/* 228 */             if (k == j) {
/*     */               
/* 230 */               bidiBase.direction = b; break;
/*     */             } 
/* 232 */             if ((arrayOfByte[k] & 0x1) != b) {
/* 233 */               bidiBase.direction = 2;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 240 */       switch (bidiBase.direction) {
/*     */         
/*     */         case 0:
/* 243 */           bidiBase.paraLevel = (byte)(bidiBase.paraLevel + 1 & 0xFFFFFFFE);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 248 */           bidiBase.trailingWSStart = 0;
/*     */           break;
/*     */         
/*     */         case 1:
/* 252 */           bidiBase.paraLevel = (byte)(bidiBase.paraLevel | 0x1);
/*     */ 
/*     */ 
/*     */           
/* 256 */           bidiBase.trailingWSStart = 0;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 263 */     paramBidiBase2.paraBidi = paramBidiBase1;
/* 264 */     return paramBidi2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte getLevelAt(BidiBase paramBidiBase, int paramInt) {
/* 270 */     if (paramBidiBase.direction != 2 || paramInt >= paramBidiBase.trailingWSStart) {
/* 271 */       return paramBidiBase.GetParaLevelAt(paramInt);
/*     */     }
/* 273 */     return paramBidiBase.levels[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] getLevels(BidiBase paramBidiBase) {
/* 279 */     int i = paramBidiBase.trailingWSStart;
/* 280 */     int j = paramBidiBase.length;
/*     */     
/* 282 */     if (i != j) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 293 */       Arrays.fill(paramBidiBase.levels, i, j, paramBidiBase.paraLevel);
/*     */ 
/*     */       
/* 296 */       paramBidiBase.trailingWSStart = j;
/*     */     } 
/* 298 */     if (j < paramBidiBase.levels.length) {
/* 299 */       byte[] arrayOfByte = new byte[j];
/* 300 */       System.arraycopy(paramBidiBase.levels, 0, arrayOfByte, 0, j);
/* 301 */       return arrayOfByte;
/*     */     } 
/* 303 */     return paramBidiBase.levels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BidiRun getLogicalRun(BidiBase paramBidiBase, int paramInt) {
/* 311 */     BidiRun bidiRun1 = new BidiRun();
/* 312 */     getRuns(paramBidiBase);
/* 313 */     int i = paramBidiBase.runCount;
/* 314 */     int j = 0, k = 0;
/* 315 */     BidiRun bidiRun2 = paramBidiBase.runs[0];
/*     */     
/* 317 */     for (byte b = 0; b < i; b++) {
/* 318 */       bidiRun2 = paramBidiBase.runs[b];
/* 319 */       k = bidiRun2.start + bidiRun2.limit - j;
/* 320 */       if (paramInt >= bidiRun2.start && paramInt < k) {
/*     */         break;
/*     */       }
/*     */       
/* 324 */       j = bidiRun2.limit;
/*     */     } 
/* 326 */     bidiRun1.start = bidiRun2.start;
/* 327 */     bidiRun1.limit = k;
/* 328 */     bidiRun1.level = bidiRun2.level;
/* 329 */     return bidiRun1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getSingleRun(BidiBase paramBidiBase, byte paramByte) {
/* 335 */     paramBidiBase.runs = paramBidiBase.simpleRuns;
/* 336 */     paramBidiBase.runCount = 1;
/*     */ 
/*     */     
/* 339 */     paramBidiBase.runs[0] = new BidiRun(0, paramBidiBase.length, paramByte);
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
/*     */   
/*     */   private static void reorderLine(BidiBase paramBidiBase, byte paramByte1, byte paramByte2) {
/* 378 */     if (paramByte2 <= (paramByte1 | 0x1)) {
/*     */       return;
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
/* 392 */     paramByte1 = (byte)(paramByte1 + 1);
/*     */     
/* 394 */     BidiRun[] arrayOfBidiRun = paramBidiBase.runs;
/* 395 */     byte[] arrayOfByte = paramBidiBase.levels;
/* 396 */     int i = paramBidiBase.runCount;
/*     */ 
/*     */     
/* 399 */     if (paramBidiBase.trailingWSStart < paramBidiBase.length) {
/* 400 */       i--;
/*     */     }
/*     */     label41: while (true) {
/* 403 */       paramByte2 = (byte)(paramByte2 - 1); if (paramByte2 >= paramByte1) {
/* 404 */         int j = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         while (true) {
/* 410 */           if (j < i && arrayOfByte[(arrayOfBidiRun[j]).start] < paramByte2) {
/* 411 */             j++; continue;
/*     */           } 
/* 413 */           if (j >= i) {
/*     */             continue label41;
/*     */           }
/*     */           
/*     */           int m;
/* 418 */           for (m = j; ++m < i && arrayOfByte[(arrayOfBidiRun[m]).start] >= paramByte2;);
/*     */ 
/*     */ 
/*     */           
/* 422 */           int k = m - 1;
/* 423 */           while (j < k) {
/* 424 */             BidiRun bidiRun = arrayOfBidiRun[j];
/* 425 */             arrayOfBidiRun[j] = arrayOfBidiRun[k];
/* 426 */             arrayOfBidiRun[k] = bidiRun;
/* 427 */             j++;
/* 428 */             k--;
/*     */           } 
/*     */           
/* 431 */           if (m == i) {
/*     */             continue label41;
/*     */           }
/* 434 */           j = m + 1;
/*     */         } 
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/* 440 */     if ((paramByte1 & 0x1) == 0) {
/* 441 */       byte b = 0;
/*     */ 
/*     */       
/* 444 */       if (paramBidiBase.trailingWSStart == paramBidiBase.length) {
/* 445 */         i--;
/*     */       }
/*     */ 
/*     */       
/* 449 */       while (b < i) {
/* 450 */         BidiRun bidiRun = arrayOfBidiRun[b];
/* 451 */         arrayOfBidiRun[b] = arrayOfBidiRun[i];
/* 452 */         arrayOfBidiRun[i] = bidiRun;
/* 453 */         b++;
/* 454 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getRunFromLogicalIndex(BidiBase paramBidiBase, int paramInt) {
/* 462 */     BidiRun[] arrayOfBidiRun = paramBidiBase.runs;
/* 463 */     int i = paramBidiBase.runCount, j = 0;
/*     */     
/* 465 */     for (byte b = 0; b < i; b++) {
/* 466 */       int k = (arrayOfBidiRun[b]).limit - j;
/* 467 */       int m = (arrayOfBidiRun[b]).start;
/* 468 */       if (paramInt >= m && paramInt < m + k) {
/* 469 */         return b;
/*     */       }
/* 471 */       j += k;
/*     */     } 
/*     */     
/* 474 */     throw new IllegalStateException("Internal ICU error in getRunFromLogicalIndex");
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
/*     */   static void getRuns(BidiBase paramBidiBase) {
/* 493 */     if (paramBidiBase.runCount >= 0) {
/*     */       return;
/*     */     }
/* 496 */     if (paramBidiBase.direction != 2) {
/*     */ 
/*     */       
/* 499 */       getSingleRun(paramBidiBase, paramBidiBase.paraLevel);
/*     */     } else {
/*     */       
/* 502 */       int i = paramBidiBase.length;
/* 503 */       byte[] arrayOfByte = paramBidiBase.levels;
/*     */       
/* 505 */       byte b = 126;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 517 */       int j = paramBidiBase.trailingWSStart;
/*     */       
/* 519 */       byte b2 = 0; byte b1;
/* 520 */       for (b1 = 0; b1 < j; b1++) {
/*     */         
/* 522 */         if (arrayOfByte[b1] != b) {
/* 523 */           b2++;
/* 524 */           b = arrayOfByte[b1];
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 532 */       if (b2 == 1 && j == i) {
/*     */         
/* 534 */         getSingleRun(paramBidiBase, arrayOfByte[0]);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 539 */         byte b4 = 62;
/* 540 */         byte b5 = 0;
/*     */ 
/*     */         
/* 543 */         if (j < i) {
/* 544 */           b2++;
/*     */         }
/*     */ 
/*     */         
/* 548 */         paramBidiBase.getRunsMemory(b2);
/* 549 */         BidiRun[] arrayOfBidiRun = paramBidiBase.runsMemory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 557 */         byte b3 = 0;
/*     */ 
/*     */         
/* 560 */         b1 = 0;
/*     */         
/*     */         do {
/* 563 */           byte b6 = b1;
/* 564 */           b = arrayOfByte[b1];
/* 565 */           if (b < b4) {
/* 566 */             b4 = b;
/*     */           }
/* 568 */           if (b > b5) {
/* 569 */             b5 = b;
/*     */           }
/*     */ 
/*     */           
/* 573 */           while (++b1 < j && arrayOfByte[b1] == b);
/*     */ 
/*     */           
/* 576 */           arrayOfBidiRun[b3] = new BidiRun(b6, b1 - b6, b);
/* 577 */           b3++;
/* 578 */         } while (b1 < j);
/*     */         
/* 580 */         if (j < i) {
/*     */           
/* 582 */           arrayOfBidiRun[b3] = new BidiRun(j, i - j, paramBidiBase.paraLevel);
/*     */ 
/*     */           
/* 585 */           if (paramBidiBase.paraLevel < b4) {
/* 586 */             b4 = paramBidiBase.paraLevel;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 591 */         paramBidiBase.runs = arrayOfBidiRun;
/* 592 */         paramBidiBase.runCount = b2;
/*     */         
/* 594 */         reorderLine(paramBidiBase, b4, b5);
/*     */ 
/*     */ 
/*     */         
/* 598 */         j = 0;
/* 599 */         for (b1 = 0; b1 < b2; b1++) {
/* 600 */           (arrayOfBidiRun[b1]).level = arrayOfByte[(arrayOfBidiRun[b1]).start];
/* 601 */           j = (arrayOfBidiRun[b1]).limit += j;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 608 */         if (b3 < b2) {
/* 609 */           boolean bool = ((paramBidiBase.paraLevel & 0x1) != 0) ? false : b3;
/* 610 */           (arrayOfBidiRun[bool]).level = paramBidiBase.paraLevel;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 616 */     if (paramBidiBase.insertPoints.size > 0)
/*     */     {
/*     */       
/* 619 */       for (byte b = 0; b < paramBidiBase.insertPoints.size; b++) {
/* 620 */         BidiBase.Point point = paramBidiBase.insertPoints.points[b];
/* 621 */         int i = getRunFromLogicalIndex(paramBidiBase, point.pos);
/* 622 */         (paramBidiBase.runs[i]).insertRemove |= point.flag;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 627 */     if (paramBidiBase.controlCount > 0)
/*     */     {
/*     */       
/* 630 */       for (byte b = 0; b < paramBidiBase.length; b++) {
/* 631 */         char c = paramBidiBase.text[b];
/* 632 */         if (BidiBase.IsBidiControlChar(c)) {
/* 633 */           int i = getRunFromLogicalIndex(paramBidiBase, b);
/* 634 */           (paramBidiBase.runs[i]).insertRemove--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] prepareReorder(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/* 645 */     if (paramArrayOfbyte1 == null || paramArrayOfbyte1.length <= 0) {
/* 646 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 650 */     byte b1 = 62;
/* 651 */     byte b2 = 0; int i;
/* 652 */     for (i = paramArrayOfbyte1.length; i > 0; ) {
/* 653 */       byte b = paramArrayOfbyte1[--i];
/* 654 */       if (b > 62) {
/* 655 */         return null;
/*     */       }
/* 657 */       if (b < b1) {
/* 658 */         b1 = b;
/*     */       }
/* 660 */       if (b > b2) {
/* 661 */         b2 = b;
/*     */       }
/*     */     } 
/* 664 */     paramArrayOfbyte2[0] = b1;
/* 665 */     paramArrayOfbyte3[0] = b2;
/*     */ 
/*     */     
/* 668 */     int[] arrayOfInt = new int[paramArrayOfbyte1.length];
/* 669 */     for (i = paramArrayOfbyte1.length; i > 0; ) {
/* 670 */       i--;
/* 671 */       arrayOfInt[i] = i;
/*     */     } 
/*     */     
/* 674 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   
/*     */   static int[] reorderVisual(byte[] paramArrayOfbyte) {
/* 679 */     byte[] arrayOfByte1 = new byte[1];
/* 680 */     byte[] arrayOfByte2 = new byte[1];
/*     */ 
/*     */ 
/*     */     
/* 684 */     int[] arrayOfInt = prepareReorder(paramArrayOfbyte, arrayOfByte1, arrayOfByte2);
/* 685 */     if (arrayOfInt == null) {
/* 686 */       return null;
/*     */     }
/*     */     
/* 689 */     byte b1 = arrayOfByte1[0];
/* 690 */     byte b2 = arrayOfByte2[0];
/*     */ 
/*     */     
/* 693 */     if (b1 == b2 && (b1 & 0x1) == 0) {
/* 694 */       return arrayOfInt;
/*     */     }
/*     */ 
/*     */     
/* 698 */     b1 = (byte)(b1 | 0x1);
/*     */ 
/*     */ 
/*     */     
/* 702 */     do { int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 708 */         if (i < paramArrayOfbyte.length && paramArrayOfbyte[i] < b2) {
/* 709 */           i++; continue;
/*     */         } 
/* 711 */         if (i >= paramArrayOfbyte.length) {
/*     */           break;
/*     */         }
/*     */         
/*     */         int k;
/* 716 */         for (k = i; ++k < paramArrayOfbyte.length && paramArrayOfbyte[k] >= b2;);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 724 */         int j = k - 1;
/* 725 */         while (i < j) {
/* 726 */           int m = arrayOfInt[i];
/* 727 */           arrayOfInt[i] = arrayOfInt[j];
/* 728 */           arrayOfInt[j] = m;
/*     */           
/* 730 */           i++;
/* 731 */           j--;
/*     */         } 
/*     */         
/* 734 */         if (k == paramArrayOfbyte.length) {
/*     */           break;
/*     */         }
/* 737 */         i = k + 1;
/*     */       } 
/*     */       
/* 740 */       b2 = (byte)(b2 - 1); } while (b2 >= b1);
/*     */     
/* 742 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] getVisualMap(BidiBase paramBidiBase) {
/* 748 */     BidiRun[] arrayOfBidiRun = paramBidiBase.runs;
/*     */     
/* 750 */     int j = (paramBidiBase.length > paramBidiBase.resultLength) ? paramBidiBase.length : paramBidiBase.resultLength;
/*     */     
/* 752 */     int[] arrayOfInt1 = new int[j];
/*     */     
/* 754 */     int i = 0;
/* 755 */     byte b = 0; int k;
/* 756 */     for (k = 0; k < paramBidiBase.runCount; k++) {
/* 757 */       int m = (arrayOfBidiRun[k]).start;
/* 758 */       int n = (arrayOfBidiRun[k]).limit;
/* 759 */       if (arrayOfBidiRun[k].isEvenRun()) {
/*     */         do {
/* 761 */           arrayOfInt1[b++] = m++;
/* 762 */         } while (++i < n);
/*     */       } else {
/* 764 */         m += n - i;
/*     */         do {
/* 766 */           arrayOfInt1[b++] = --m;
/* 767 */         } while (++i < n);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 772 */     if (paramBidiBase.insertPoints.size > 0) {
/* 773 */       k = 0; int m = paramBidiBase.runCount;
/*     */       
/* 775 */       arrayOfBidiRun = paramBidiBase.runs;
/*     */       int n;
/* 777 */       for (n = 0; n < m; n++) {
/* 778 */         int i2 = (arrayOfBidiRun[n]).insertRemove;
/* 779 */         if ((i2 & 0x5) > 0) {
/* 780 */           k++;
/*     */         }
/* 782 */         if ((i2 & 0xA) > 0) {
/* 783 */           k++;
/*     */         }
/*     */       } 
/*     */       
/* 787 */       int i1 = paramBidiBase.resultLength;
/* 788 */       for (n = m - 1; n >= 0 && k > 0; n--) {
/* 789 */         int i2 = (arrayOfBidiRun[n]).insertRemove;
/* 790 */         if ((i2 & 0xA) > 0) {
/* 791 */           arrayOfInt1[--i1] = -1;
/* 792 */           k--;
/*     */         } 
/* 794 */         i = (n > 0) ? (arrayOfBidiRun[n - 1]).limit : 0;
/* 795 */         for (int i3 = (arrayOfBidiRun[n]).limit - 1; i3 >= i && k > 0; i3--) {
/* 796 */           arrayOfInt1[--i1] = arrayOfInt1[i3];
/*     */         }
/* 798 */         if ((i2 & 0x5) > 0) {
/* 799 */           arrayOfInt1[--i1] = -1;
/* 800 */           k--;
/*     */         }
/*     */       
/*     */       } 
/* 804 */     } else if (paramBidiBase.controlCount > 0) {
/* 805 */       k = paramBidiBase.runCount;
/*     */ 
/*     */ 
/*     */       
/* 809 */       arrayOfBidiRun = paramBidiBase.runs;
/* 810 */       i = 0;
/*     */       
/* 812 */       int m = 0;
/* 813 */       for (byte b1 = 0; b1 < k; b1++, i += i1) {
/* 814 */         int i1 = (arrayOfBidiRun[b1]).limit - i;
/* 815 */         int n = (arrayOfBidiRun[b1]).insertRemove;
/*     */         
/* 817 */         if (n == 0 && m == i) {
/* 818 */           m += i1;
/*     */ 
/*     */         
/*     */         }
/* 822 */         else if (n == 0) {
/* 823 */           int i2 = (arrayOfBidiRun[b1]).limit;
/* 824 */           for (int i3 = i; i3 < i2; i3++) {
/* 825 */             arrayOfInt1[m++] = arrayOfInt1[i3];
/*     */           }
/*     */         } else {
/*     */           
/* 829 */           int i2 = (arrayOfBidiRun[b1]).start;
/* 830 */           boolean bool = arrayOfBidiRun[b1].isEvenRun();
/* 831 */           int i3 = i2 + i1 - 1;
/* 832 */           for (byte b2 = 0; b2 < i1; b2++) {
/* 833 */             int i4 = bool ? (i2 + b2) : (i3 - b2);
/* 834 */             char c = paramBidiBase.text[i4];
/* 835 */             if (!BidiBase.IsBidiControlChar(c))
/* 836 */               arrayOfInt1[m++] = i4; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 841 */     if (j == paramBidiBase.resultLength) {
/* 842 */       return arrayOfInt1;
/*     */     }
/* 844 */     int[] arrayOfInt2 = new int[paramBidiBase.resultLength];
/* 845 */     System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, paramBidiBase.resultLength);
/* 846 */     return arrayOfInt2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/bidi/BidiLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */