/*      */ package java.util;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class DualPivotQuicksort
/*      */ {
/*      */   private static final int MAX_RUN_COUNT = 67;
/*      */   private static final int MAX_RUN_LENGTH = 33;
/*      */   private static final int QUICKSORT_THRESHOLD = 286;
/*      */   private static final int INSERTION_SORT_THRESHOLD = 47;
/*      */   private static final int COUNTING_SORT_THRESHOLD_FOR_BYTE = 29;
/*      */   private static final int COUNTING_SORT_THRESHOLD_FOR_SHORT_OR_CHAR = 3200;
/*      */   private static final int NUM_SHORT_VALUES = 65536;
/*      */   private static final int NUM_CHAR_VALUES = 65536;
/*      */   private static final int NUM_BYTE_VALUES = 256;
/*      */   
/*      */   static void sort(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4) {
/*      */     int arrayOfInt2[], k, m;
/*  110 */     if (paramInt2 - paramInt1 < 286) {
/*  111 */       sort(paramArrayOfint1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  119 */     int[] arrayOfInt1 = new int[68];
/*  120 */     byte b = 0; arrayOfInt1[0] = paramInt1;
/*      */     
/*      */     int i;
/*  123 */     for (i = paramInt1; i < paramInt2; arrayOfInt1[b] = i) {
/*  124 */       if (paramArrayOfint1[i] < paramArrayOfint1[i + 1]) {
/*  125 */         while (++i <= paramInt2 && paramArrayOfint1[i - 1] <= paramArrayOfint1[i]);
/*  126 */       } else if (paramArrayOfint1[i] > paramArrayOfint1[i + 1]) {
/*  127 */         while (++i <= paramInt2 && paramArrayOfint1[i - 1] >= paramArrayOfint1[i]);
/*  128 */         for (int i1 = arrayOfInt1[b] - 1; ++i1 < --k; ) {
/*  129 */           m = paramArrayOfint1[i1]; paramArrayOfint1[i1] = paramArrayOfint1[k]; paramArrayOfint1[k] = m;
/*      */         } 
/*      */       } else {
/*  132 */         for (byte b1 = 33; ++i <= paramInt2 && paramArrayOfint1[i - 1] == paramArrayOfint1[i];) {
/*  133 */           if (--b1 == 0) {
/*  134 */             sort(paramArrayOfint1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  144 */       if (++b == 67) {
/*  145 */         sort(paramArrayOfint1, paramInt1, paramInt2, true);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  152 */     if (arrayOfInt1[b] == paramInt2++) {
/*  153 */       arrayOfInt1[++b] = paramInt2;
/*  154 */     } else if (b == 1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  159 */     i = 0;
/*  160 */     for (int j = 1; (j <<= 1) < b; i = (byte)(i ^ 0x1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  165 */     int n = paramInt2 - paramInt1;
/*  166 */     if (paramArrayOfint2 == null || paramInt4 < n || paramInt3 + n > paramArrayOfint2.length) {
/*  167 */       paramArrayOfint2 = new int[n];
/*  168 */       paramInt3 = 0;
/*      */     } 
/*  170 */     if (i == 0) {
/*  171 */       System.arraycopy(paramArrayOfint1, paramInt1, paramArrayOfint2, paramInt3, n);
/*  172 */       arrayOfInt2 = paramArrayOfint1;
/*  173 */       m = 0;
/*  174 */       paramArrayOfint1 = paramArrayOfint2;
/*  175 */       k = paramInt3 - paramInt1;
/*      */     } else {
/*  177 */       arrayOfInt2 = paramArrayOfint2;
/*  178 */       k = 0;
/*  179 */       m = paramInt3 - paramInt1;
/*      */     } 
/*      */ 
/*      */     
/*  183 */     for (; b > 1; b = b1) {
/*  184 */       byte b1; int i1; for (i1 = (b1 = 0) + 2; i1 <= b; i1 += 2) {
/*  185 */         int i3 = arrayOfInt1[i1], i4 = arrayOfInt1[i1 - 1];
/*  186 */         for (int i5 = arrayOfInt1[i1 - 2], i6 = i5, i7 = i4; i5 < i3; i5++) {
/*  187 */           if (i7 >= i3 || (i6 < i4 && paramArrayOfint1[i6 + k] <= paramArrayOfint1[i7 + k])) {
/*  188 */             arrayOfInt2[i5 + m] = paramArrayOfint1[i6++ + k];
/*      */           } else {
/*  190 */             arrayOfInt2[i5 + m] = paramArrayOfint1[i7++ + k];
/*      */           } 
/*      */         } 
/*  193 */         arrayOfInt1[++b1] = i3;
/*      */       } 
/*  195 */       if ((b & 0x1) != 0) {
/*  196 */         int i3; for (i1 = paramInt2, i3 = arrayOfInt1[b - 1]; --i1 >= i3;) {
/*  197 */           arrayOfInt2[i1 + m] = paramArrayOfint1[i1 + k];
/*      */         }
/*  199 */         arrayOfInt1[++b1] = paramInt2;
/*      */       } 
/*  201 */       int[] arrayOfInt = paramArrayOfint1; paramArrayOfint1 = arrayOfInt2; arrayOfInt2 = arrayOfInt;
/*  202 */       int i2 = k; k = m; m = i2;
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
/*      */   private static void sort(int[] paramArrayOfint, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  215 */     int i = paramInt2 - paramInt1 + 1;
/*      */ 
/*      */     
/*  218 */     if (i < 47) {
/*  219 */       if (paramBoolean) {
/*      */         int i6;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  225 */         for (int i5 = paramInt1; i5 < paramInt2; i6 = ++i5) {
/*  226 */           int i7 = paramArrayOfint[i5 + 1];
/*  227 */           while (i7 < paramArrayOfint[i6]) {
/*  228 */             paramArrayOfint[i6 + 1] = paramArrayOfint[i6];
/*  229 */             if (i6-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           } 
/*  233 */           paramArrayOfint[i6 + 1] = i7;
/*      */         } 
/*      */       } else {
/*      */ 
/*      */         
/*      */         do {
/*      */           
/*  240 */           if (paramInt1 >= paramInt2) {
/*      */             return;
/*      */           }
/*  243 */         } while (paramArrayOfint[++paramInt1] >= paramArrayOfint[paramInt1 - 1]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  253 */         for (i5 = paramInt1; ++paramInt1 <= paramInt2; i5 = ++paramInt1) {
/*  254 */           int i6 = paramArrayOfint[i5], i7 = paramArrayOfint[paramInt1];
/*      */           
/*  256 */           if (i6 < i7) {
/*  257 */             i7 = i6; i6 = paramArrayOfint[paramInt1];
/*      */           } 
/*  259 */           while (i6 < paramArrayOfint[--i5]) {
/*  260 */             paramArrayOfint[i5 + 2] = paramArrayOfint[i5];
/*      */           }
/*  262 */           paramArrayOfint[++i5 + 1] = i6;
/*      */           
/*  264 */           while (i7 < paramArrayOfint[--i5]) {
/*  265 */             paramArrayOfint[i5 + 1] = paramArrayOfint[i5];
/*      */           }
/*  267 */           paramArrayOfint[i5 + 1] = i7;
/*      */         } 
/*  269 */         i5 = paramArrayOfint[paramInt2];
/*      */         
/*  271 */         while (i5 < paramArrayOfint[--paramInt2]) {
/*  272 */           paramArrayOfint[paramInt2 + 1] = paramArrayOfint[paramInt2];
/*      */         }
/*  274 */         paramArrayOfint[paramInt2 + 1] = i5;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  280 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  289 */     int k = paramInt1 + paramInt2 >>> 1;
/*  290 */     int m = k - j;
/*  291 */     int n = m - j;
/*  292 */     int i1 = k + j;
/*  293 */     int i2 = i1 + j;
/*      */ 
/*      */     
/*  296 */     if (paramArrayOfint[m] < paramArrayOfint[n]) { int i5 = paramArrayOfint[m]; paramArrayOfint[m] = paramArrayOfint[n]; paramArrayOfint[n] = i5; }
/*      */     
/*  298 */     if (paramArrayOfint[k] < paramArrayOfint[m]) { int i5 = paramArrayOfint[k]; paramArrayOfint[k] = paramArrayOfint[m]; paramArrayOfint[m] = i5;
/*  299 */       if (i5 < paramArrayOfint[n]) { paramArrayOfint[m] = paramArrayOfint[n]; paramArrayOfint[n] = i5; }
/*      */        }
/*  301 */      if (paramArrayOfint[i1] < paramArrayOfint[k]) { int i5 = paramArrayOfint[i1]; paramArrayOfint[i1] = paramArrayOfint[k]; paramArrayOfint[k] = i5;
/*  302 */       if (i5 < paramArrayOfint[m]) { paramArrayOfint[k] = paramArrayOfint[m]; paramArrayOfint[m] = i5;
/*  303 */         if (i5 < paramArrayOfint[n]) { paramArrayOfint[m] = paramArrayOfint[n]; paramArrayOfint[n] = i5; }
/*      */          }
/*      */        }
/*  306 */      if (paramArrayOfint[i2] < paramArrayOfint[i1]) { int i5 = paramArrayOfint[i2]; paramArrayOfint[i2] = paramArrayOfint[i1]; paramArrayOfint[i1] = i5;
/*  307 */       if (i5 < paramArrayOfint[k]) { paramArrayOfint[i1] = paramArrayOfint[k]; paramArrayOfint[k] = i5;
/*  308 */         if (i5 < paramArrayOfint[m]) { paramArrayOfint[k] = paramArrayOfint[m]; paramArrayOfint[m] = i5;
/*  309 */           if (i5 < paramArrayOfint[n]) { paramArrayOfint[m] = paramArrayOfint[n]; paramArrayOfint[n] = i5; }
/*      */            }
/*      */          }
/*      */        }
/*      */ 
/*      */     
/*  315 */     int i3 = paramInt1;
/*  316 */     int i4 = paramInt2;
/*      */     
/*  318 */     if (paramArrayOfint[n] != paramArrayOfint[m] && paramArrayOfint[m] != paramArrayOfint[k] && paramArrayOfint[k] != paramArrayOfint[i1] && paramArrayOfint[i1] != paramArrayOfint[i2]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  324 */       int i5 = paramArrayOfint[m];
/*  325 */       int i6 = paramArrayOfint[i1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  333 */       paramArrayOfint[m] = paramArrayOfint[paramInt1];
/*  334 */       paramArrayOfint[i1] = paramArrayOfint[paramInt2];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  339 */       while (paramArrayOfint[++i3] < i5);
/*  340 */       while (paramArrayOfint[--i4] > i6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  362 */       label149: for (i7 = i3 - 1; ++i7 <= i4; ) {
/*  363 */         int i8 = paramArrayOfint[i7];
/*  364 */         if (i8 < i5) {
/*  365 */           paramArrayOfint[i7] = paramArrayOfint[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  370 */           paramArrayOfint[i3] = i8;
/*  371 */           i3++; continue;
/*  372 */         }  if (i8 > i6) {
/*  373 */           while (paramArrayOfint[i4] > i6) {
/*  374 */             if (i4-- == i7) {
/*      */               break label149;
/*      */             }
/*      */           } 
/*  378 */           if (paramArrayOfint[i4] < i5) {
/*  379 */             paramArrayOfint[i7] = paramArrayOfint[i3];
/*  380 */             paramArrayOfint[i3] = paramArrayOfint[i4];
/*  381 */             i3++;
/*      */           } else {
/*  383 */             paramArrayOfint[i7] = paramArrayOfint[i4];
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  389 */           paramArrayOfint[i4] = i8;
/*  390 */           i4--;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  395 */       paramArrayOfint[paramInt1] = paramArrayOfint[i3 - 1]; paramArrayOfint[i3 - 1] = i5;
/*  396 */       paramArrayOfint[paramInt2] = paramArrayOfint[i4 + 1]; paramArrayOfint[i4 + 1] = i6;
/*      */ 
/*      */       
/*  399 */       sort(paramArrayOfint, paramInt1, i3 - 2, paramBoolean);
/*  400 */       sort(paramArrayOfint, i4 + 2, paramInt2, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  406 */       if (i3 < n && i2 < i4) {
/*      */ 
/*      */ 
/*      */         
/*  410 */         while (paramArrayOfint[i3] == i5) {
/*  411 */           i3++;
/*      */         }
/*      */         
/*  414 */         while (paramArrayOfint[i4] == i6) {
/*  415 */           i4--;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  438 */         for (i7 = i3 - 1; ++i7 <= i4; ) {
/*  439 */           int i8 = paramArrayOfint[i7];
/*  440 */           if (i8 == i5) {
/*  441 */             paramArrayOfint[i7] = paramArrayOfint[i3];
/*  442 */             paramArrayOfint[i3] = i8;
/*  443 */             i3++; continue;
/*  444 */           }  if (i8 == i6) {
/*  445 */             while (paramArrayOfint[i4] == i6) {
/*  446 */               if (i4-- == i7) {
/*      */                 // Byte code: goto -> 1033
/*      */               }
/*      */             } 
/*  450 */             if (paramArrayOfint[i4] == i5) {
/*  451 */               paramArrayOfint[i7] = paramArrayOfint[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  460 */               paramArrayOfint[i3] = i5;
/*  461 */               i3++;
/*      */             } else {
/*  463 */               paramArrayOfint[i7] = paramArrayOfint[i4];
/*      */             } 
/*  465 */             paramArrayOfint[i4] = i8;
/*  466 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  472 */       sort(paramArrayOfint, i3, i4, false);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  479 */       int i5 = paramArrayOfint[k];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  501 */       for (int i6 = i3; i6 <= i4; i6++) {
/*  502 */         if (paramArrayOfint[i6] != i5) {
/*      */ 
/*      */           
/*  505 */           int i7 = paramArrayOfint[i6];
/*  506 */           if (i7 < i5) {
/*  507 */             paramArrayOfint[i6] = paramArrayOfint[i3];
/*  508 */             paramArrayOfint[i3] = i7;
/*  509 */             i3++;
/*      */           } else {
/*  511 */             while (paramArrayOfint[i4] > i5) {
/*  512 */               i4--;
/*      */             }
/*  514 */             if (paramArrayOfint[i4] < i5) {
/*  515 */               paramArrayOfint[i6] = paramArrayOfint[i3];
/*  516 */               paramArrayOfint[i3] = paramArrayOfint[i4];
/*  517 */               i3++;
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  527 */               paramArrayOfint[i6] = i5;
/*      */             } 
/*  529 */             paramArrayOfint[i4] = i7;
/*  530 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  539 */       sort(paramArrayOfint, paramInt1, i3 - 1, paramBoolean);
/*  540 */       sort(paramArrayOfint, i4 + 1, paramInt2, false);
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
/*      */   static void sort(long[] paramArrayOflong1, int paramInt1, int paramInt2, long[] paramArrayOflong2, int paramInt3, int paramInt4) {
/*      */     long[] arrayOfLong;
/*      */     int k, m;
/*  558 */     if (paramInt2 - paramInt1 < 286) {
/*  559 */       sort(paramArrayOflong1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  567 */     int[] arrayOfInt = new int[68];
/*  568 */     byte b = 0; arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int i;
/*  571 */     for (i = paramInt1; i < paramInt2; arrayOfInt[b] = i) {
/*  572 */       if (paramArrayOflong1[i] < paramArrayOflong1[i + 1]) {
/*  573 */         while (++i <= paramInt2 && paramArrayOflong1[i - 1] <= paramArrayOflong1[i]);
/*  574 */       } else if (paramArrayOflong1[i] > paramArrayOflong1[i + 1]) {
/*  575 */         while (++i <= paramInt2 && paramArrayOflong1[i - 1] >= paramArrayOflong1[i]);
/*  576 */         for (int i1 = arrayOfInt[b] - 1; ++i1 < --k; ) {
/*  577 */           long l = paramArrayOflong1[i1]; paramArrayOflong1[i1] = paramArrayOflong1[k]; paramArrayOflong1[k] = l;
/*      */         } 
/*      */       } else {
/*  580 */         for (byte b1 = 33; ++i <= paramInt2 && paramArrayOflong1[i - 1] == paramArrayOflong1[i];) {
/*  581 */           if (--b1 == 0) {
/*  582 */             sort(paramArrayOflong1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  592 */       if (++b == 67) {
/*  593 */         sort(paramArrayOflong1, paramInt1, paramInt2, true);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  600 */     if (arrayOfInt[b] == paramInt2++) {
/*  601 */       arrayOfInt[++b] = paramInt2;
/*  602 */     } else if (b == 1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  607 */     i = 0;
/*  608 */     for (int j = 1; (j <<= 1) < b; i = (byte)(i ^ 0x1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  613 */     int n = paramInt2 - paramInt1;
/*  614 */     if (paramArrayOflong2 == null || paramInt4 < n || paramInt3 + n > paramArrayOflong2.length) {
/*  615 */       paramArrayOflong2 = new long[n];
/*  616 */       paramInt3 = 0;
/*      */     } 
/*  618 */     if (i == 0) {
/*  619 */       System.arraycopy(paramArrayOflong1, paramInt1, paramArrayOflong2, paramInt3, n);
/*  620 */       arrayOfLong = paramArrayOflong1;
/*  621 */       m = 0;
/*  622 */       paramArrayOflong1 = paramArrayOflong2;
/*  623 */       k = paramInt3 - paramInt1;
/*      */     } else {
/*  625 */       arrayOfLong = paramArrayOflong2;
/*  626 */       k = 0;
/*  627 */       m = paramInt3 - paramInt1;
/*      */     } 
/*      */ 
/*      */     
/*  631 */     for (; b > 1; b = b1) {
/*  632 */       byte b1; int i1; for (i1 = (b1 = 0) + 2; i1 <= b; i1 += 2) {
/*  633 */         int i3 = arrayOfInt[i1], i4 = arrayOfInt[i1 - 1];
/*  634 */         for (int i5 = arrayOfInt[i1 - 2], i6 = i5, i7 = i4; i5 < i3; i5++) {
/*  635 */           if (i7 >= i3 || (i6 < i4 && paramArrayOflong1[i6 + k] <= paramArrayOflong1[i7 + k])) {
/*  636 */             arrayOfLong[i5 + m] = paramArrayOflong1[i6++ + k];
/*      */           } else {
/*  638 */             arrayOfLong[i5 + m] = paramArrayOflong1[i7++ + k];
/*      */           } 
/*      */         } 
/*  641 */         arrayOfInt[++b1] = i3;
/*      */       } 
/*  643 */       if ((b & 0x1) != 0) {
/*  644 */         int i3; for (i1 = paramInt2, i3 = arrayOfInt[b - 1]; --i1 >= i3;) {
/*  645 */           arrayOfLong[i1 + m] = paramArrayOflong1[i1 + k];
/*      */         }
/*  647 */         arrayOfInt[++b1] = paramInt2;
/*      */       } 
/*  649 */       long[] arrayOfLong1 = paramArrayOflong1; paramArrayOflong1 = arrayOfLong; arrayOfLong = arrayOfLong1;
/*  650 */       int i2 = k; k = m; m = i2;
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
/*      */   private static void sort(long[] paramArrayOflong, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  663 */     int i = paramInt2 - paramInt1 + 1;
/*      */ 
/*      */     
/*  666 */     if (i < 47) {
/*  667 */       if (paramBoolean) {
/*      */         int i6;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  673 */         for (int i5 = paramInt1; i5 < paramInt2; i6 = ++i5) {
/*  674 */           long l = paramArrayOflong[i5 + 1];
/*  675 */           while (l < paramArrayOflong[i6]) {
/*  676 */             paramArrayOflong[i6 + 1] = paramArrayOflong[i6];
/*  677 */             if (i6-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           } 
/*  681 */           paramArrayOflong[i6 + 1] = l;
/*      */         } 
/*      */       } else {
/*      */ 
/*      */         
/*      */         do {
/*      */           
/*  688 */           if (paramInt1 >= paramInt2) {
/*      */             return;
/*      */           }
/*  691 */         } while (paramArrayOflong[++paramInt1] >= paramArrayOflong[paramInt1 - 1]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  701 */         for (i5 = paramInt1; ++paramInt1 <= paramInt2; i5 = ++paramInt1) {
/*  702 */           long l1 = paramArrayOflong[i5], l2 = paramArrayOflong[paramInt1];
/*      */           
/*  704 */           if (l1 < l2) {
/*  705 */             l2 = l1; l1 = paramArrayOflong[paramInt1];
/*      */           } 
/*  707 */           while (l1 < paramArrayOflong[--i5]) {
/*  708 */             paramArrayOflong[i5 + 2] = paramArrayOflong[i5];
/*      */           }
/*  710 */           paramArrayOflong[++i5 + 1] = l1;
/*      */           
/*  712 */           while (l2 < paramArrayOflong[--i5]) {
/*  713 */             paramArrayOflong[i5 + 1] = paramArrayOflong[i5];
/*      */           }
/*  715 */           paramArrayOflong[i5 + 1] = l2;
/*      */         } 
/*  717 */         long l = paramArrayOflong[paramInt2];
/*      */         
/*  719 */         while (l < paramArrayOflong[--paramInt2]) {
/*  720 */           paramArrayOflong[paramInt2 + 1] = paramArrayOflong[paramInt2];
/*      */         }
/*  722 */         paramArrayOflong[paramInt2 + 1] = l;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  728 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  737 */     int k = paramInt1 + paramInt2 >>> 1;
/*  738 */     int m = k - j;
/*  739 */     int n = m - j;
/*  740 */     int i1 = k + j;
/*  741 */     int i2 = i1 + j;
/*      */ 
/*      */     
/*  744 */     if (paramArrayOflong[m] < paramArrayOflong[n]) { long l = paramArrayOflong[m]; paramArrayOflong[m] = paramArrayOflong[n]; paramArrayOflong[n] = l; }
/*      */     
/*  746 */     if (paramArrayOflong[k] < paramArrayOflong[m]) { long l = paramArrayOflong[k]; paramArrayOflong[k] = paramArrayOflong[m]; paramArrayOflong[m] = l;
/*  747 */       if (l < paramArrayOflong[n]) { paramArrayOflong[m] = paramArrayOflong[n]; paramArrayOflong[n] = l; }
/*      */        }
/*  749 */      if (paramArrayOflong[i1] < paramArrayOflong[k]) { long l = paramArrayOflong[i1]; paramArrayOflong[i1] = paramArrayOflong[k]; paramArrayOflong[k] = l;
/*  750 */       if (l < paramArrayOflong[m]) { paramArrayOflong[k] = paramArrayOflong[m]; paramArrayOflong[m] = l;
/*  751 */         if (l < paramArrayOflong[n]) { paramArrayOflong[m] = paramArrayOflong[n]; paramArrayOflong[n] = l; }
/*      */          }
/*      */        }
/*  754 */      if (paramArrayOflong[i2] < paramArrayOflong[i1]) { long l = paramArrayOflong[i2]; paramArrayOflong[i2] = paramArrayOflong[i1]; paramArrayOflong[i1] = l;
/*  755 */       if (l < paramArrayOflong[k]) { paramArrayOflong[i1] = paramArrayOflong[k]; paramArrayOflong[k] = l;
/*  756 */         if (l < paramArrayOflong[m]) { paramArrayOflong[k] = paramArrayOflong[m]; paramArrayOflong[m] = l;
/*  757 */           if (l < paramArrayOflong[n]) { paramArrayOflong[m] = paramArrayOflong[n]; paramArrayOflong[n] = l; }
/*      */            }
/*      */          }
/*      */        }
/*      */ 
/*      */     
/*  763 */     int i3 = paramInt1;
/*  764 */     int i4 = paramInt2;
/*      */     
/*  766 */     if (paramArrayOflong[n] != paramArrayOflong[m] && paramArrayOflong[m] != paramArrayOflong[k] && paramArrayOflong[k] != paramArrayOflong[i1] && paramArrayOflong[i1] != paramArrayOflong[i2]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  772 */       long l1 = paramArrayOflong[m];
/*  773 */       long l2 = paramArrayOflong[i1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  781 */       paramArrayOflong[m] = paramArrayOflong[paramInt1];
/*  782 */       paramArrayOflong[i1] = paramArrayOflong[paramInt2];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  787 */       while (paramArrayOflong[++i3] < l1);
/*  788 */       while (paramArrayOflong[--i4] > l2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  810 */       label149: for (i5 = i3 - 1; ++i5 <= i4; ) {
/*  811 */         long l = paramArrayOflong[i5];
/*  812 */         if (l < l1) {
/*  813 */           paramArrayOflong[i5] = paramArrayOflong[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  818 */           paramArrayOflong[i3] = l;
/*  819 */           i3++; continue;
/*  820 */         }  if (l > l2) {
/*  821 */           while (paramArrayOflong[i4] > l2) {
/*  822 */             if (i4-- == i5) {
/*      */               break label149;
/*      */             }
/*      */           } 
/*  826 */           if (paramArrayOflong[i4] < l1) {
/*  827 */             paramArrayOflong[i5] = paramArrayOflong[i3];
/*  828 */             paramArrayOflong[i3] = paramArrayOflong[i4];
/*  829 */             i3++;
/*      */           } else {
/*  831 */             paramArrayOflong[i5] = paramArrayOflong[i4];
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  837 */           paramArrayOflong[i4] = l;
/*  838 */           i4--;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  843 */       paramArrayOflong[paramInt1] = paramArrayOflong[i3 - 1]; paramArrayOflong[i3 - 1] = l1;
/*  844 */       paramArrayOflong[paramInt2] = paramArrayOflong[i4 + 1]; paramArrayOflong[i4 + 1] = l2;
/*      */ 
/*      */       
/*  847 */       sort(paramArrayOflong, paramInt1, i3 - 2, paramBoolean);
/*  848 */       sort(paramArrayOflong, i4 + 2, paramInt2, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  854 */       if (i3 < n && i2 < i4) {
/*      */ 
/*      */ 
/*      */         
/*  858 */         while (paramArrayOflong[i3] == l1) {
/*  859 */           i3++;
/*      */         }
/*      */         
/*  862 */         while (paramArrayOflong[i4] == l2) {
/*  863 */           i4--;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  886 */         for (i5 = i3 - 1; ++i5 <= i4; ) {
/*  887 */           long l = paramArrayOflong[i5];
/*  888 */           if (l == l1) {
/*  889 */             paramArrayOflong[i5] = paramArrayOflong[i3];
/*  890 */             paramArrayOflong[i3] = l;
/*  891 */             i3++; continue;
/*  892 */           }  if (l == l2) {
/*  893 */             while (paramArrayOflong[i4] == l2) {
/*  894 */               if (i4-- == i5) {
/*      */                 // Byte code: goto -> 1065
/*      */               }
/*      */             } 
/*  898 */             if (paramArrayOflong[i4] == l1) {
/*  899 */               paramArrayOflong[i5] = paramArrayOflong[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  908 */               paramArrayOflong[i3] = l1;
/*  909 */               i3++;
/*      */             } else {
/*  911 */               paramArrayOflong[i5] = paramArrayOflong[i4];
/*      */             } 
/*  913 */             paramArrayOflong[i4] = l;
/*  914 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  920 */       sort(paramArrayOflong, i3, i4, false);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  927 */       long l = paramArrayOflong[k];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  949 */       for (int i5 = i3; i5 <= i4; i5++) {
/*  950 */         if (paramArrayOflong[i5] != l) {
/*      */ 
/*      */           
/*  953 */           long l1 = paramArrayOflong[i5];
/*  954 */           if (l1 < l) {
/*  955 */             paramArrayOflong[i5] = paramArrayOflong[i3];
/*  956 */             paramArrayOflong[i3] = l1;
/*  957 */             i3++;
/*      */           } else {
/*  959 */             while (paramArrayOflong[i4] > l) {
/*  960 */               i4--;
/*      */             }
/*  962 */             if (paramArrayOflong[i4] < l) {
/*  963 */               paramArrayOflong[i5] = paramArrayOflong[i3];
/*  964 */               paramArrayOflong[i3] = paramArrayOflong[i4];
/*  965 */               i3++;
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  975 */               paramArrayOflong[i5] = l;
/*      */             } 
/*  977 */             paramArrayOflong[i4] = l1;
/*  978 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  987 */       sort(paramArrayOflong, paramInt1, i3 - 1, paramBoolean);
/*  988 */       sort(paramArrayOflong, i4 + 1, paramInt2, false);
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
/*      */   static void sort(short[] paramArrayOfshort1, int paramInt1, int paramInt2, short[] paramArrayOfshort2, int paramInt3, int paramInt4) {
/* 1006 */     if (paramInt2 - paramInt1 > 3200)
/* 1007 */     { int[] arrayOfInt = new int[65536];
/*      */       int i;
/* 1009 */       for (i = paramInt1 - 1; ++i <= paramInt2;)
/* 1010 */         arrayOfInt[paramArrayOfshort1[i] - -32768] = arrayOfInt[paramArrayOfshort1[i] - -32768] + 1; 
/*      */       int j;
/* 1012 */       label21: for (i = 65536, j = paramInt2 + 1; j > paramInt1; ) {
/* 1013 */         while (arrayOfInt[--i] == 0);
/* 1014 */         short s = (short)(i + -32768);
/* 1015 */         int k = arrayOfInt[i];
/*      */         
/*      */         while (true)
/* 1018 */         { paramArrayOfshort1[--j] = s;
/* 1019 */           if (--k <= 0)
/*      */             continue label21;  } 
/*      */       }  }
/* 1022 */     else { doSort(paramArrayOfshort1, paramInt1, paramInt2, paramArrayOfshort2, paramInt3, paramInt4); }
/*      */   
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
/*      */   private static void doSort(short[] paramArrayOfshort1, int paramInt1, int paramInt2, short[] paramArrayOfshort2, int paramInt3, int paramInt4) {
/*      */     short[] arrayOfShort;
/*      */     int k, m;
/* 1042 */     if (paramInt2 - paramInt1 < 286) {
/* 1043 */       sort(paramArrayOfshort1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1051 */     int[] arrayOfInt = new int[68];
/* 1052 */     byte b = 0; arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int i;
/* 1055 */     for (i = paramInt1; i < paramInt2; arrayOfInt[b] = i) {
/* 1056 */       if (paramArrayOfshort1[i] < paramArrayOfshort1[i + 1]) {
/* 1057 */         while (++i <= paramInt2 && paramArrayOfshort1[i - 1] <= paramArrayOfshort1[i]);
/* 1058 */       } else if (paramArrayOfshort1[i] > paramArrayOfshort1[i + 1]) {
/* 1059 */         while (++i <= paramInt2 && paramArrayOfshort1[i - 1] >= paramArrayOfshort1[i]);
/* 1060 */         for (int i1 = arrayOfInt[b] - 1; ++i1 < --k; ) {
/* 1061 */           m = paramArrayOfshort1[i1]; paramArrayOfshort1[i1] = paramArrayOfshort1[k]; paramArrayOfshort1[k] = m;
/*      */         } 
/*      */       } else {
/* 1064 */         for (byte b1 = 33; ++i <= paramInt2 && paramArrayOfshort1[i - 1] == paramArrayOfshort1[i];) {
/* 1065 */           if (--b1 == 0) {
/* 1066 */             sort(paramArrayOfshort1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1076 */       if (++b == 67) {
/* 1077 */         sort(paramArrayOfshort1, paramInt1, paramInt2, true);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 1084 */     if (arrayOfInt[b] == paramInt2++) {
/* 1085 */       arrayOfInt[++b] = paramInt2;
/* 1086 */     } else if (b == 1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1091 */     i = 0;
/* 1092 */     for (int j = 1; (j <<= 1) < b; i = (byte)(i ^ 0x1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1097 */     int n = paramInt2 - paramInt1;
/* 1098 */     if (paramArrayOfshort2 == null || paramInt4 < n || paramInt3 + n > paramArrayOfshort2.length) {
/* 1099 */       paramArrayOfshort2 = new short[n];
/* 1100 */       paramInt3 = 0;
/*      */     } 
/* 1102 */     if (i == 0) {
/* 1103 */       System.arraycopy(paramArrayOfshort1, paramInt1, paramArrayOfshort2, paramInt3, n);
/* 1104 */       arrayOfShort = paramArrayOfshort1;
/* 1105 */       m = 0;
/* 1106 */       paramArrayOfshort1 = paramArrayOfshort2;
/* 1107 */       k = paramInt3 - paramInt1;
/*      */     } else {
/* 1109 */       arrayOfShort = paramArrayOfshort2;
/* 1110 */       k = 0;
/* 1111 */       m = paramInt3 - paramInt1;
/*      */     } 
/*      */ 
/*      */     
/* 1115 */     for (; b > 1; b = b1) {
/* 1116 */       byte b1; int i1; for (i1 = (b1 = 0) + 2; i1 <= b; i1 += 2) {
/* 1117 */         int i3 = arrayOfInt[i1], i4 = arrayOfInt[i1 - 1];
/* 1118 */         for (int i5 = arrayOfInt[i1 - 2], i6 = i5, i7 = i4; i5 < i3; i5++) {
/* 1119 */           if (i7 >= i3 || (i6 < i4 && paramArrayOfshort1[i6 + k] <= paramArrayOfshort1[i7 + k])) {
/* 1120 */             arrayOfShort[i5 + m] = paramArrayOfshort1[i6++ + k];
/*      */           } else {
/* 1122 */             arrayOfShort[i5 + m] = paramArrayOfshort1[i7++ + k];
/*      */           } 
/*      */         } 
/* 1125 */         arrayOfInt[++b1] = i3;
/*      */       } 
/* 1127 */       if ((b & 0x1) != 0) {
/* 1128 */         int i3; for (i1 = paramInt2, i3 = arrayOfInt[b - 1]; --i1 >= i3;) {
/* 1129 */           arrayOfShort[i1 + m] = paramArrayOfshort1[i1 + k];
/*      */         }
/* 1131 */         arrayOfInt[++b1] = paramInt2;
/*      */       } 
/* 1133 */       short[] arrayOfShort1 = paramArrayOfshort1; paramArrayOfshort1 = arrayOfShort; arrayOfShort = arrayOfShort1;
/* 1134 */       int i2 = k; k = m; m = i2;
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
/*      */   private static void sort(short[] paramArrayOfshort, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1147 */     int i = paramInt2 - paramInt1 + 1;
/*      */ 
/*      */     
/* 1150 */     if (i < 47) {
/* 1151 */       if (paramBoolean) {
/*      */         int i6;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1157 */         for (int i5 = paramInt1; i5 < paramInt2; i6 = ++i5) {
/* 1158 */           short s = paramArrayOfshort[i5 + 1];
/* 1159 */           while (s < paramArrayOfshort[i6]) {
/* 1160 */             paramArrayOfshort[i6 + 1] = paramArrayOfshort[i6];
/* 1161 */             if (i6-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           } 
/* 1165 */           paramArrayOfshort[i6 + 1] = s;
/*      */         } 
/*      */       } else {
/*      */ 
/*      */         
/*      */         do {
/*      */           
/* 1172 */           if (paramInt1 >= paramInt2) {
/*      */             return;
/*      */           }
/* 1175 */         } while (paramArrayOfshort[++paramInt1] >= paramArrayOfshort[paramInt1 - 1]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1185 */         for (i5 = paramInt1; ++paramInt1 <= paramInt2; i5 = ++paramInt1) {
/* 1186 */           short s1 = paramArrayOfshort[i5], s2 = paramArrayOfshort[paramInt1];
/*      */           
/* 1188 */           if (s1 < s2) {
/* 1189 */             s2 = s1; s1 = paramArrayOfshort[paramInt1];
/*      */           } 
/* 1191 */           while (s1 < paramArrayOfshort[--i5]) {
/* 1192 */             paramArrayOfshort[i5 + 2] = paramArrayOfshort[i5];
/*      */           }
/* 1194 */           paramArrayOfshort[++i5 + 1] = s1;
/*      */           
/* 1196 */           while (s2 < paramArrayOfshort[--i5]) {
/* 1197 */             paramArrayOfshort[i5 + 1] = paramArrayOfshort[i5];
/*      */           }
/* 1199 */           paramArrayOfshort[i5 + 1] = s2;
/*      */         } 
/* 1201 */         i5 = paramArrayOfshort[paramInt2];
/*      */         
/* 1203 */         while (i5 < paramArrayOfshort[--paramInt2]) {
/* 1204 */           paramArrayOfshort[paramInt2 + 1] = paramArrayOfshort[paramInt2];
/*      */         }
/* 1206 */         paramArrayOfshort[paramInt2 + 1] = i5;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1212 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1221 */     int k = paramInt1 + paramInt2 >>> 1;
/* 1222 */     int m = k - j;
/* 1223 */     int n = m - j;
/* 1224 */     int i1 = k + j;
/* 1225 */     int i2 = i1 + j;
/*      */ 
/*      */     
/* 1228 */     if (paramArrayOfshort[m] < paramArrayOfshort[n]) { short s = paramArrayOfshort[m]; paramArrayOfshort[m] = paramArrayOfshort[n]; paramArrayOfshort[n] = s; }
/*      */     
/* 1230 */     if (paramArrayOfshort[k] < paramArrayOfshort[m]) { short s = paramArrayOfshort[k]; paramArrayOfshort[k] = paramArrayOfshort[m]; paramArrayOfshort[m] = s;
/* 1231 */       if (s < paramArrayOfshort[n]) { paramArrayOfshort[m] = paramArrayOfshort[n]; paramArrayOfshort[n] = s; }
/*      */        }
/* 1233 */      if (paramArrayOfshort[i1] < paramArrayOfshort[k]) { short s = paramArrayOfshort[i1]; paramArrayOfshort[i1] = paramArrayOfshort[k]; paramArrayOfshort[k] = s;
/* 1234 */       if (s < paramArrayOfshort[m]) { paramArrayOfshort[k] = paramArrayOfshort[m]; paramArrayOfshort[m] = s;
/* 1235 */         if (s < paramArrayOfshort[n]) { paramArrayOfshort[m] = paramArrayOfshort[n]; paramArrayOfshort[n] = s; }
/*      */          }
/*      */        }
/* 1238 */      if (paramArrayOfshort[i2] < paramArrayOfshort[i1]) { short s = paramArrayOfshort[i2]; paramArrayOfshort[i2] = paramArrayOfshort[i1]; paramArrayOfshort[i1] = s;
/* 1239 */       if (s < paramArrayOfshort[k]) { paramArrayOfshort[i1] = paramArrayOfshort[k]; paramArrayOfshort[k] = s;
/* 1240 */         if (s < paramArrayOfshort[m]) { paramArrayOfshort[k] = paramArrayOfshort[m]; paramArrayOfshort[m] = s;
/* 1241 */           if (s < paramArrayOfshort[n]) { paramArrayOfshort[m] = paramArrayOfshort[n]; paramArrayOfshort[n] = s; }
/*      */            }
/*      */          }
/*      */        }
/*      */ 
/*      */     
/* 1247 */     int i3 = paramInt1;
/* 1248 */     int i4 = paramInt2;
/*      */     
/* 1250 */     if (paramArrayOfshort[n] != paramArrayOfshort[m] && paramArrayOfshort[m] != paramArrayOfshort[k] && paramArrayOfshort[k] != paramArrayOfshort[i1] && paramArrayOfshort[i1] != paramArrayOfshort[i2]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1256 */       short s1 = paramArrayOfshort[m];
/* 1257 */       short s2 = paramArrayOfshort[i1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1265 */       paramArrayOfshort[m] = paramArrayOfshort[paramInt1];
/* 1266 */       paramArrayOfshort[i1] = paramArrayOfshort[paramInt2];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1271 */       while (paramArrayOfshort[++i3] < s1);
/* 1272 */       while (paramArrayOfshort[--i4] > s2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1294 */       label149: for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 1295 */         short s = paramArrayOfshort[i5];
/* 1296 */         if (s < s1) {
/* 1297 */           paramArrayOfshort[i5] = paramArrayOfshort[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1302 */           paramArrayOfshort[i3] = s;
/* 1303 */           i3++; continue;
/* 1304 */         }  if (s > s2) {
/* 1305 */           while (paramArrayOfshort[i4] > s2) {
/* 1306 */             if (i4-- == i5) {
/*      */               break label149;
/*      */             }
/*      */           } 
/* 1310 */           if (paramArrayOfshort[i4] < s1) {
/* 1311 */             paramArrayOfshort[i5] = paramArrayOfshort[i3];
/* 1312 */             paramArrayOfshort[i3] = paramArrayOfshort[i4];
/* 1313 */             i3++;
/*      */           } else {
/* 1315 */             paramArrayOfshort[i5] = paramArrayOfshort[i4];
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1321 */           paramArrayOfshort[i4] = s;
/* 1322 */           i4--;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1327 */       paramArrayOfshort[paramInt1] = paramArrayOfshort[i3 - 1]; paramArrayOfshort[i3 - 1] = s1;
/* 1328 */       paramArrayOfshort[paramInt2] = paramArrayOfshort[i4 + 1]; paramArrayOfshort[i4 + 1] = s2;
/*      */ 
/*      */       
/* 1331 */       sort(paramArrayOfshort, paramInt1, i3 - 2, paramBoolean);
/* 1332 */       sort(paramArrayOfshort, i4 + 2, paramInt2, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1338 */       if (i3 < n && i2 < i4) {
/*      */ 
/*      */ 
/*      */         
/* 1342 */         while (paramArrayOfshort[i3] == s1) {
/* 1343 */           i3++;
/*      */         }
/*      */         
/* 1346 */         while (paramArrayOfshort[i4] == s2) {
/* 1347 */           i4--;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1370 */         for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 1371 */           short s = paramArrayOfshort[i5];
/* 1372 */           if (s == s1) {
/* 1373 */             paramArrayOfshort[i5] = paramArrayOfshort[i3];
/* 1374 */             paramArrayOfshort[i3] = s;
/* 1375 */             i3++; continue;
/* 1376 */           }  if (s == s2) {
/* 1377 */             while (paramArrayOfshort[i4] == s2) {
/* 1378 */               if (i4-- == i5) {
/*      */                 // Byte code: goto -> 1033
/*      */               }
/*      */             } 
/* 1382 */             if (paramArrayOfshort[i4] == s1) {
/* 1383 */               paramArrayOfshort[i5] = paramArrayOfshort[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1392 */               paramArrayOfshort[i3] = s1;
/* 1393 */               i3++;
/*      */             } else {
/* 1395 */               paramArrayOfshort[i5] = paramArrayOfshort[i4];
/*      */             } 
/* 1397 */             paramArrayOfshort[i4] = s;
/* 1398 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1404 */       sort(paramArrayOfshort, i3, i4, false);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1411 */       short s = paramArrayOfshort[k];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1433 */       for (int i5 = i3; i5 <= i4; i5++) {
/* 1434 */         if (paramArrayOfshort[i5] != s) {
/*      */ 
/*      */           
/* 1437 */           short s1 = paramArrayOfshort[i5];
/* 1438 */           if (s1 < s) {
/* 1439 */             paramArrayOfshort[i5] = paramArrayOfshort[i3];
/* 1440 */             paramArrayOfshort[i3] = s1;
/* 1441 */             i3++;
/*      */           } else {
/* 1443 */             while (paramArrayOfshort[i4] > s) {
/* 1444 */               i4--;
/*      */             }
/* 1446 */             if (paramArrayOfshort[i4] < s) {
/* 1447 */               paramArrayOfshort[i5] = paramArrayOfshort[i3];
/* 1448 */               paramArrayOfshort[i3] = paramArrayOfshort[i4];
/* 1449 */               i3++;
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1459 */               paramArrayOfshort[i5] = s;
/*      */             } 
/* 1461 */             paramArrayOfshort[i4] = s1;
/* 1462 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1471 */       sort(paramArrayOfshort, paramInt1, i3 - 1, paramBoolean);
/* 1472 */       sort(paramArrayOfshort, i4 + 1, paramInt2, false);
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
/*      */   static void sort(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4) {
/* 1490 */     if (paramInt2 - paramInt1 > 3200)
/* 1491 */     { int[] arrayOfInt = new int[65536];
/*      */       int i;
/* 1493 */       for (i = paramInt1 - 1; ++i <= paramInt2;)
/* 1494 */         arrayOfInt[paramArrayOfchar1[i]] = arrayOfInt[paramArrayOfchar1[i]] + 1; 
/*      */       int j;
/* 1496 */       label21: for (i = 65536, j = paramInt2 + 1; j > paramInt1; ) {
/* 1497 */         while (arrayOfInt[--i] == 0);
/* 1498 */         char c = (char)i;
/* 1499 */         int k = arrayOfInt[i];
/*      */         
/*      */         while (true)
/* 1502 */         { paramArrayOfchar1[--j] = c;
/* 1503 */           if (--k <= 0)
/*      */             continue label21;  } 
/*      */       }  }
/* 1506 */     else { doSort(paramArrayOfchar1, paramInt1, paramInt2, paramArrayOfchar2, paramInt3, paramInt4); }
/*      */   
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
/*      */   private static void doSort(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4) {
/*      */     char[] arrayOfChar;
/*      */     int k, m;
/* 1526 */     if (paramInt2 - paramInt1 < 286) {
/* 1527 */       sort(paramArrayOfchar1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1535 */     int[] arrayOfInt = new int[68];
/* 1536 */     byte b = 0; arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int i;
/* 1539 */     for (i = paramInt1; i < paramInt2; arrayOfInt[b] = i) {
/* 1540 */       if (paramArrayOfchar1[i] < paramArrayOfchar1[i + 1]) {
/* 1541 */         while (++i <= paramInt2 && paramArrayOfchar1[i - 1] <= paramArrayOfchar1[i]);
/* 1542 */       } else if (paramArrayOfchar1[i] > paramArrayOfchar1[i + 1]) {
/* 1543 */         while (++i <= paramInt2 && paramArrayOfchar1[i - 1] >= paramArrayOfchar1[i]);
/* 1544 */         for (int i1 = arrayOfInt[b] - 1; ++i1 < --k; ) {
/* 1545 */           m = paramArrayOfchar1[i1]; paramArrayOfchar1[i1] = paramArrayOfchar1[k]; paramArrayOfchar1[k] = m;
/*      */         } 
/*      */       } else {
/* 1548 */         for (byte b1 = 33; ++i <= paramInt2 && paramArrayOfchar1[i - 1] == paramArrayOfchar1[i];) {
/* 1549 */           if (--b1 == 0) {
/* 1550 */             sort(paramArrayOfchar1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1560 */       if (++b == 67) {
/* 1561 */         sort(paramArrayOfchar1, paramInt1, paramInt2, true);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 1568 */     if (arrayOfInt[b] == paramInt2++) {
/* 1569 */       arrayOfInt[++b] = paramInt2;
/* 1570 */     } else if (b == 1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1575 */     i = 0;
/* 1576 */     for (int j = 1; (j <<= 1) < b; i = (byte)(i ^ 0x1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1581 */     int n = paramInt2 - paramInt1;
/* 1582 */     if (paramArrayOfchar2 == null || paramInt4 < n || paramInt3 + n > paramArrayOfchar2.length) {
/* 1583 */       paramArrayOfchar2 = new char[n];
/* 1584 */       paramInt3 = 0;
/*      */     } 
/* 1586 */     if (i == 0) {
/* 1587 */       System.arraycopy(paramArrayOfchar1, paramInt1, paramArrayOfchar2, paramInt3, n);
/* 1588 */       arrayOfChar = paramArrayOfchar1;
/* 1589 */       m = 0;
/* 1590 */       paramArrayOfchar1 = paramArrayOfchar2;
/* 1591 */       k = paramInt3 - paramInt1;
/*      */     } else {
/* 1593 */       arrayOfChar = paramArrayOfchar2;
/* 1594 */       k = 0;
/* 1595 */       m = paramInt3 - paramInt1;
/*      */     } 
/*      */ 
/*      */     
/* 1599 */     for (; b > 1; b = b1) {
/* 1600 */       byte b1; int i1; for (i1 = (b1 = 0) + 2; i1 <= b; i1 += 2) {
/* 1601 */         int i3 = arrayOfInt[i1], i4 = arrayOfInt[i1 - 1];
/* 1602 */         for (int i5 = arrayOfInt[i1 - 2], i6 = i5, i7 = i4; i5 < i3; i5++) {
/* 1603 */           if (i7 >= i3 || (i6 < i4 && paramArrayOfchar1[i6 + k] <= paramArrayOfchar1[i7 + k])) {
/* 1604 */             arrayOfChar[i5 + m] = paramArrayOfchar1[i6++ + k];
/*      */           } else {
/* 1606 */             arrayOfChar[i5 + m] = paramArrayOfchar1[i7++ + k];
/*      */           } 
/*      */         } 
/* 1609 */         arrayOfInt[++b1] = i3;
/*      */       } 
/* 1611 */       if ((b & 0x1) != 0) {
/* 1612 */         int i3; for (i1 = paramInt2, i3 = arrayOfInt[b - 1]; --i1 >= i3;) {
/* 1613 */           arrayOfChar[i1 + m] = paramArrayOfchar1[i1 + k];
/*      */         }
/* 1615 */         arrayOfInt[++b1] = paramInt2;
/*      */       } 
/* 1617 */       char[] arrayOfChar1 = paramArrayOfchar1; paramArrayOfchar1 = arrayOfChar; arrayOfChar = arrayOfChar1;
/* 1618 */       int i2 = k; k = m; m = i2;
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
/*      */   private static void sort(char[] paramArrayOfchar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1631 */     int i = paramInt2 - paramInt1 + 1;
/*      */ 
/*      */     
/* 1634 */     if (i < 47) {
/* 1635 */       if (paramBoolean) {
/*      */         int i6;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1641 */         for (int i5 = paramInt1; i5 < paramInt2; i6 = ++i5) {
/* 1642 */           char c = paramArrayOfchar[i5 + 1];
/* 1643 */           while (c < paramArrayOfchar[i6]) {
/* 1644 */             paramArrayOfchar[i6 + 1] = paramArrayOfchar[i6];
/* 1645 */             if (i6-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           } 
/* 1649 */           paramArrayOfchar[i6 + 1] = c;
/*      */         } 
/*      */       } else {
/*      */ 
/*      */         
/*      */         do {
/*      */           
/* 1656 */           if (paramInt1 >= paramInt2) {
/*      */             return;
/*      */           }
/* 1659 */         } while (paramArrayOfchar[++paramInt1] >= paramArrayOfchar[paramInt1 - 1]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1669 */         for (i5 = paramInt1; ++paramInt1 <= paramInt2; i5 = ++paramInt1) {
/* 1670 */           char c1 = paramArrayOfchar[i5], c2 = paramArrayOfchar[paramInt1];
/*      */           
/* 1672 */           if (c1 < c2) {
/* 1673 */             c2 = c1; c1 = paramArrayOfchar[paramInt1];
/*      */           } 
/* 1675 */           while (c1 < paramArrayOfchar[--i5]) {
/* 1676 */             paramArrayOfchar[i5 + 2] = paramArrayOfchar[i5];
/*      */           }
/* 1678 */           paramArrayOfchar[++i5 + 1] = c1;
/*      */           
/* 1680 */           while (c2 < paramArrayOfchar[--i5]) {
/* 1681 */             paramArrayOfchar[i5 + 1] = paramArrayOfchar[i5];
/*      */           }
/* 1683 */           paramArrayOfchar[i5 + 1] = c2;
/*      */         } 
/* 1685 */         i5 = paramArrayOfchar[paramInt2];
/*      */         
/* 1687 */         while (i5 < paramArrayOfchar[--paramInt2]) {
/* 1688 */           paramArrayOfchar[paramInt2 + 1] = paramArrayOfchar[paramInt2];
/*      */         }
/* 1690 */         paramArrayOfchar[paramInt2 + 1] = i5;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1696 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1705 */     int k = paramInt1 + paramInt2 >>> 1;
/* 1706 */     int m = k - j;
/* 1707 */     int n = m - j;
/* 1708 */     int i1 = k + j;
/* 1709 */     int i2 = i1 + j;
/*      */ 
/*      */     
/* 1712 */     if (paramArrayOfchar[m] < paramArrayOfchar[n]) { char c = paramArrayOfchar[m]; paramArrayOfchar[m] = paramArrayOfchar[n]; paramArrayOfchar[n] = c; }
/*      */     
/* 1714 */     if (paramArrayOfchar[k] < paramArrayOfchar[m]) { char c = paramArrayOfchar[k]; paramArrayOfchar[k] = paramArrayOfchar[m]; paramArrayOfchar[m] = c;
/* 1715 */       if (c < paramArrayOfchar[n]) { paramArrayOfchar[m] = paramArrayOfchar[n]; paramArrayOfchar[n] = c; }
/*      */        }
/* 1717 */      if (paramArrayOfchar[i1] < paramArrayOfchar[k]) { char c = paramArrayOfchar[i1]; paramArrayOfchar[i1] = paramArrayOfchar[k]; paramArrayOfchar[k] = c;
/* 1718 */       if (c < paramArrayOfchar[m]) { paramArrayOfchar[k] = paramArrayOfchar[m]; paramArrayOfchar[m] = c;
/* 1719 */         if (c < paramArrayOfchar[n]) { paramArrayOfchar[m] = paramArrayOfchar[n]; paramArrayOfchar[n] = c; }
/*      */          }
/*      */        }
/* 1722 */      if (paramArrayOfchar[i2] < paramArrayOfchar[i1]) { char c = paramArrayOfchar[i2]; paramArrayOfchar[i2] = paramArrayOfchar[i1]; paramArrayOfchar[i1] = c;
/* 1723 */       if (c < paramArrayOfchar[k]) { paramArrayOfchar[i1] = paramArrayOfchar[k]; paramArrayOfchar[k] = c;
/* 1724 */         if (c < paramArrayOfchar[m]) { paramArrayOfchar[k] = paramArrayOfchar[m]; paramArrayOfchar[m] = c;
/* 1725 */           if (c < paramArrayOfchar[n]) { paramArrayOfchar[m] = paramArrayOfchar[n]; paramArrayOfchar[n] = c; }
/*      */            }
/*      */          }
/*      */        }
/*      */ 
/*      */     
/* 1731 */     int i3 = paramInt1;
/* 1732 */     int i4 = paramInt2;
/*      */     
/* 1734 */     if (paramArrayOfchar[n] != paramArrayOfchar[m] && paramArrayOfchar[m] != paramArrayOfchar[k] && paramArrayOfchar[k] != paramArrayOfchar[i1] && paramArrayOfchar[i1] != paramArrayOfchar[i2]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1740 */       char c1 = paramArrayOfchar[m];
/* 1741 */       char c2 = paramArrayOfchar[i1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1749 */       paramArrayOfchar[m] = paramArrayOfchar[paramInt1];
/* 1750 */       paramArrayOfchar[i1] = paramArrayOfchar[paramInt2];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1755 */       while (paramArrayOfchar[++i3] < c1);
/* 1756 */       while (paramArrayOfchar[--i4] > c2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1778 */       label149: for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 1779 */         char c = paramArrayOfchar[i5];
/* 1780 */         if (c < c1) {
/* 1781 */           paramArrayOfchar[i5] = paramArrayOfchar[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1786 */           paramArrayOfchar[i3] = c;
/* 1787 */           i3++; continue;
/* 1788 */         }  if (c > c2) {
/* 1789 */           while (paramArrayOfchar[i4] > c2) {
/* 1790 */             if (i4-- == i5) {
/*      */               break label149;
/*      */             }
/*      */           } 
/* 1794 */           if (paramArrayOfchar[i4] < c1) {
/* 1795 */             paramArrayOfchar[i5] = paramArrayOfchar[i3];
/* 1796 */             paramArrayOfchar[i3] = paramArrayOfchar[i4];
/* 1797 */             i3++;
/*      */           } else {
/* 1799 */             paramArrayOfchar[i5] = paramArrayOfchar[i4];
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1805 */           paramArrayOfchar[i4] = c;
/* 1806 */           i4--;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1811 */       paramArrayOfchar[paramInt1] = paramArrayOfchar[i3 - 1]; paramArrayOfchar[i3 - 1] = c1;
/* 1812 */       paramArrayOfchar[paramInt2] = paramArrayOfchar[i4 + 1]; paramArrayOfchar[i4 + 1] = c2;
/*      */ 
/*      */       
/* 1815 */       sort(paramArrayOfchar, paramInt1, i3 - 2, paramBoolean);
/* 1816 */       sort(paramArrayOfchar, i4 + 2, paramInt2, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1822 */       if (i3 < n && i2 < i4) {
/*      */ 
/*      */ 
/*      */         
/* 1826 */         while (paramArrayOfchar[i3] == c1) {
/* 1827 */           i3++;
/*      */         }
/*      */         
/* 1830 */         while (paramArrayOfchar[i4] == c2) {
/* 1831 */           i4--;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1854 */         for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 1855 */           char c = paramArrayOfchar[i5];
/* 1856 */           if (c == c1) {
/* 1857 */             paramArrayOfchar[i5] = paramArrayOfchar[i3];
/* 1858 */             paramArrayOfchar[i3] = c;
/* 1859 */             i3++; continue;
/* 1860 */           }  if (c == c2) {
/* 1861 */             while (paramArrayOfchar[i4] == c2) {
/* 1862 */               if (i4-- == i5) {
/*      */                 // Byte code: goto -> 1033
/*      */               }
/*      */             } 
/* 1866 */             if (paramArrayOfchar[i4] == c1) {
/* 1867 */               paramArrayOfchar[i5] = paramArrayOfchar[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1876 */               paramArrayOfchar[i3] = c1;
/* 1877 */               i3++;
/*      */             } else {
/* 1879 */               paramArrayOfchar[i5] = paramArrayOfchar[i4];
/*      */             } 
/* 1881 */             paramArrayOfchar[i4] = c;
/* 1882 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1888 */       sort(paramArrayOfchar, i3, i4, false);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1895 */       char c = paramArrayOfchar[k];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1917 */       for (int i5 = i3; i5 <= i4; i5++) {
/* 1918 */         if (paramArrayOfchar[i5] != c) {
/*      */ 
/*      */           
/* 1921 */           char c1 = paramArrayOfchar[i5];
/* 1922 */           if (c1 < c) {
/* 1923 */             paramArrayOfchar[i5] = paramArrayOfchar[i3];
/* 1924 */             paramArrayOfchar[i3] = c1;
/* 1925 */             i3++;
/*      */           } else {
/* 1927 */             while (paramArrayOfchar[i4] > c) {
/* 1928 */               i4--;
/*      */             }
/* 1930 */             if (paramArrayOfchar[i4] < c) {
/* 1931 */               paramArrayOfchar[i5] = paramArrayOfchar[i3];
/* 1932 */               paramArrayOfchar[i3] = paramArrayOfchar[i4];
/* 1933 */               i3++;
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1943 */               paramArrayOfchar[i5] = c;
/*      */             } 
/* 1945 */             paramArrayOfchar[i4] = c1;
/* 1946 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1955 */       sort(paramArrayOfchar, paramInt1, i3 - 1, paramBoolean);
/* 1956 */       sort(paramArrayOfchar, i4 + 1, paramInt2, false);
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
/*      */   static void sort(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 1972 */     if (paramInt2 - paramInt1 > 29)
/* 1973 */     { int[] arrayOfInt = new int[256];
/*      */       int i;
/* 1975 */       for (i = paramInt1 - 1; ++i <= paramInt2;)
/* 1976 */         arrayOfInt[paramArrayOfbyte[i] - -128] = arrayOfInt[paramArrayOfbyte[i] - -128] + 1; 
/*      */       int j;
/* 1978 */       label31: for (i = 256, j = paramInt2 + 1; j > paramInt1; ) {
/* 1979 */         while (arrayOfInt[--i] == 0);
/* 1980 */         byte b = (byte)(i + -128);
/* 1981 */         int k = arrayOfInt[i];
/*      */         
/*      */         while (true)
/* 1984 */         { paramArrayOfbyte[--j] = b;
/* 1985 */           if (--k <= 0)
/*      */             continue label31;  } 
/*      */       }  }
/* 1988 */     else { int j; for (int i = paramInt1; i < paramInt2; j = ++i) {
/* 1989 */         byte b = paramArrayOfbyte[i + 1];
/* 1990 */         while (b < paramArrayOfbyte[j]) {
/* 1991 */           paramArrayOfbyte[j + 1] = paramArrayOfbyte[j];
/* 1992 */           if (j-- == paramInt1) {
/*      */             break;
/*      */           }
/*      */         } 
/* 1996 */         paramArrayOfbyte[j + 1] = b;
/*      */       }  }
/*      */   
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
/*      */   static void sort(float[] paramArrayOffloat1, int paramInt1, int paramInt2, float[] paramArrayOffloat2, int paramInt3, int paramInt4) {
/* 2017 */     while (paramInt1 <= paramInt2 && Float.isNaN(paramArrayOffloat1[paramInt2]))
/* 2018 */       paramInt2--; 
/*      */     int i;
/* 2020 */     for (i = paramInt2; --i >= paramInt1; ) {
/* 2021 */       float f = paramArrayOffloat1[i];
/* 2022 */       if (f != f) {
/* 2023 */         paramArrayOffloat1[i] = paramArrayOffloat1[paramInt2];
/* 2024 */         paramArrayOffloat1[paramInt2] = f;
/* 2025 */         paramInt2--;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2032 */     doSort(paramArrayOffloat1, paramInt1, paramInt2, paramArrayOffloat2, paramInt3, paramInt4);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2037 */     i = paramInt2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2042 */     while (paramInt1 < i) {
/* 2043 */       int m = paramInt1 + i >>> 1;
/* 2044 */       float f = paramArrayOffloat1[m];
/*      */       
/* 2046 */       if (f < 0.0F) {
/* 2047 */         paramInt1 = m + 1; continue;
/*      */       } 
/* 2049 */       i = m;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2056 */     while (paramInt1 <= paramInt2 && Float.floatToRawIntBits(paramArrayOffloat1[paramInt1]) < 0) {
/* 2057 */       paramInt1++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2081 */     for (int j = paramInt1, k = paramInt1 - 1; ++j <= paramInt2; ) {
/* 2082 */       float f = paramArrayOffloat1[j];
/* 2083 */       if (f != 0.0F) {
/*      */         break;
/*      */       }
/* 2086 */       if (Float.floatToRawIntBits(f) < 0) {
/* 2087 */         paramArrayOffloat1[j] = 0.0F;
/* 2088 */         paramArrayOffloat1[++k] = -0.0F;
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
/*      */   private static void doSort(float[] paramArrayOffloat1, int paramInt1, int paramInt2, float[] paramArrayOffloat2, int paramInt3, int paramInt4) {
/*      */     float[] arrayOfFloat;
/*      */     int k, m;
/* 2106 */     if (paramInt2 - paramInt1 < 286) {
/* 2107 */       sort(paramArrayOffloat1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 2115 */     int[] arrayOfInt = new int[68];
/* 2116 */     byte b = 0; arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int i;
/* 2119 */     for (i = paramInt1; i < paramInt2; arrayOfInt[b] = i) {
/* 2120 */       if (paramArrayOffloat1[i] < paramArrayOffloat1[i + 1]) {
/* 2121 */         while (++i <= paramInt2 && paramArrayOffloat1[i - 1] <= paramArrayOffloat1[i]);
/* 2122 */       } else if (paramArrayOffloat1[i] > paramArrayOffloat1[i + 1]) {
/* 2123 */         while (++i <= paramInt2 && paramArrayOffloat1[i - 1] >= paramArrayOffloat1[i]);
/* 2124 */         for (int i1 = arrayOfInt[b] - 1; ++i1 < --k; ) {
/* 2125 */           float f = paramArrayOffloat1[i1]; paramArrayOffloat1[i1] = paramArrayOffloat1[k]; paramArrayOffloat1[k] = f;
/*      */         } 
/*      */       } else {
/* 2128 */         for (byte b1 = 33; ++i <= paramInt2 && paramArrayOffloat1[i - 1] == paramArrayOffloat1[i];) {
/* 2129 */           if (--b1 == 0) {
/* 2130 */             sort(paramArrayOffloat1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2140 */       if (++b == 67) {
/* 2141 */         sort(paramArrayOffloat1, paramInt1, paramInt2, true);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 2148 */     if (arrayOfInt[b] == paramInt2++) {
/* 2149 */       arrayOfInt[++b] = paramInt2;
/* 2150 */     } else if (b == 1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 2155 */     i = 0;
/* 2156 */     for (int j = 1; (j <<= 1) < b; i = (byte)(i ^ 0x1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2161 */     int n = paramInt2 - paramInt1;
/* 2162 */     if (paramArrayOffloat2 == null || paramInt4 < n || paramInt3 + n > paramArrayOffloat2.length) {
/* 2163 */       paramArrayOffloat2 = new float[n];
/* 2164 */       paramInt3 = 0;
/*      */     } 
/* 2166 */     if (i == 0) {
/* 2167 */       System.arraycopy(paramArrayOffloat1, paramInt1, paramArrayOffloat2, paramInt3, n);
/* 2168 */       arrayOfFloat = paramArrayOffloat1;
/* 2169 */       m = 0;
/* 2170 */       paramArrayOffloat1 = paramArrayOffloat2;
/* 2171 */       k = paramInt3 - paramInt1;
/*      */     } else {
/* 2173 */       arrayOfFloat = paramArrayOffloat2;
/* 2174 */       k = 0;
/* 2175 */       m = paramInt3 - paramInt1;
/*      */     } 
/*      */ 
/*      */     
/* 2179 */     for (; b > 1; b = b1) {
/* 2180 */       byte b1; int i1; for (i1 = (b1 = 0) + 2; i1 <= b; i1 += 2) {
/* 2181 */         int i3 = arrayOfInt[i1], i4 = arrayOfInt[i1 - 1];
/* 2182 */         for (int i5 = arrayOfInt[i1 - 2], i6 = i5, i7 = i4; i5 < i3; i5++) {
/* 2183 */           if (i7 >= i3 || (i6 < i4 && paramArrayOffloat1[i6 + k] <= paramArrayOffloat1[i7 + k])) {
/* 2184 */             arrayOfFloat[i5 + m] = paramArrayOffloat1[i6++ + k];
/*      */           } else {
/* 2186 */             arrayOfFloat[i5 + m] = paramArrayOffloat1[i7++ + k];
/*      */           } 
/*      */         } 
/* 2189 */         arrayOfInt[++b1] = i3;
/*      */       } 
/* 2191 */       if ((b & 0x1) != 0) {
/* 2192 */         int i3; for (i1 = paramInt2, i3 = arrayOfInt[b - 1]; --i1 >= i3;) {
/* 2193 */           arrayOfFloat[i1 + m] = paramArrayOffloat1[i1 + k];
/*      */         }
/* 2195 */         arrayOfInt[++b1] = paramInt2;
/*      */       } 
/* 2197 */       float[] arrayOfFloat1 = paramArrayOffloat1; paramArrayOffloat1 = arrayOfFloat; arrayOfFloat = arrayOfFloat1;
/* 2198 */       int i2 = k; k = m; m = i2;
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
/*      */   private static void sort(float[] paramArrayOffloat, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2211 */     int i = paramInt2 - paramInt1 + 1;
/*      */ 
/*      */     
/* 2214 */     if (i < 47) {
/* 2215 */       if (paramBoolean) {
/*      */         int i6;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2221 */         for (int i5 = paramInt1; i5 < paramInt2; i6 = ++i5) {
/* 2222 */           float f = paramArrayOffloat[i5 + 1];
/* 2223 */           while (f < paramArrayOffloat[i6]) {
/* 2224 */             paramArrayOffloat[i6 + 1] = paramArrayOffloat[i6];
/* 2225 */             if (i6-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           } 
/* 2229 */           paramArrayOffloat[i6 + 1] = f;
/*      */         } 
/*      */       } else {
/*      */ 
/*      */         
/*      */         do {
/*      */           
/* 2236 */           if (paramInt1 >= paramInt2) {
/*      */             return;
/*      */           }
/* 2239 */         } while (paramArrayOffloat[++paramInt1] >= paramArrayOffloat[paramInt1 - 1]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2249 */         for (i5 = paramInt1; ++paramInt1 <= paramInt2; i5 = ++paramInt1) {
/* 2250 */           float f1 = paramArrayOffloat[i5], f2 = paramArrayOffloat[paramInt1];
/*      */           
/* 2252 */           if (f1 < f2) {
/* 2253 */             f2 = f1; f1 = paramArrayOffloat[paramInt1];
/*      */           } 
/* 2255 */           while (f1 < paramArrayOffloat[--i5]) {
/* 2256 */             paramArrayOffloat[i5 + 2] = paramArrayOffloat[i5];
/*      */           }
/* 2258 */           paramArrayOffloat[++i5 + 1] = f1;
/*      */           
/* 2260 */           while (f2 < paramArrayOffloat[--i5]) {
/* 2261 */             paramArrayOffloat[i5 + 1] = paramArrayOffloat[i5];
/*      */           }
/* 2263 */           paramArrayOffloat[i5 + 1] = f2;
/*      */         } 
/* 2265 */         float f = paramArrayOffloat[paramInt2];
/*      */         
/* 2267 */         while (f < paramArrayOffloat[--paramInt2]) {
/* 2268 */           paramArrayOffloat[paramInt2 + 1] = paramArrayOffloat[paramInt2];
/*      */         }
/* 2270 */         paramArrayOffloat[paramInt2 + 1] = f;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2276 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2285 */     int k = paramInt1 + paramInt2 >>> 1;
/* 2286 */     int m = k - j;
/* 2287 */     int n = m - j;
/* 2288 */     int i1 = k + j;
/* 2289 */     int i2 = i1 + j;
/*      */ 
/*      */     
/* 2292 */     if (paramArrayOffloat[m] < paramArrayOffloat[n]) { float f = paramArrayOffloat[m]; paramArrayOffloat[m] = paramArrayOffloat[n]; paramArrayOffloat[n] = f; }
/*      */     
/* 2294 */     if (paramArrayOffloat[k] < paramArrayOffloat[m]) { float f = paramArrayOffloat[k]; paramArrayOffloat[k] = paramArrayOffloat[m]; paramArrayOffloat[m] = f;
/* 2295 */       if (f < paramArrayOffloat[n]) { paramArrayOffloat[m] = paramArrayOffloat[n]; paramArrayOffloat[n] = f; }
/*      */        }
/* 2297 */      if (paramArrayOffloat[i1] < paramArrayOffloat[k]) { float f = paramArrayOffloat[i1]; paramArrayOffloat[i1] = paramArrayOffloat[k]; paramArrayOffloat[k] = f;
/* 2298 */       if (f < paramArrayOffloat[m]) { paramArrayOffloat[k] = paramArrayOffloat[m]; paramArrayOffloat[m] = f;
/* 2299 */         if (f < paramArrayOffloat[n]) { paramArrayOffloat[m] = paramArrayOffloat[n]; paramArrayOffloat[n] = f; }
/*      */          }
/*      */        }
/* 2302 */      if (paramArrayOffloat[i2] < paramArrayOffloat[i1]) { float f = paramArrayOffloat[i2]; paramArrayOffloat[i2] = paramArrayOffloat[i1]; paramArrayOffloat[i1] = f;
/* 2303 */       if (f < paramArrayOffloat[k]) { paramArrayOffloat[i1] = paramArrayOffloat[k]; paramArrayOffloat[k] = f;
/* 2304 */         if (f < paramArrayOffloat[m]) { paramArrayOffloat[k] = paramArrayOffloat[m]; paramArrayOffloat[m] = f;
/* 2305 */           if (f < paramArrayOffloat[n]) { paramArrayOffloat[m] = paramArrayOffloat[n]; paramArrayOffloat[n] = f; }
/*      */            }
/*      */          }
/*      */        }
/*      */ 
/*      */     
/* 2311 */     int i3 = paramInt1;
/* 2312 */     int i4 = paramInt2;
/*      */     
/* 2314 */     if (paramArrayOffloat[n] != paramArrayOffloat[m] && paramArrayOffloat[m] != paramArrayOffloat[k] && paramArrayOffloat[k] != paramArrayOffloat[i1] && paramArrayOffloat[i1] != paramArrayOffloat[i2]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2320 */       float f1 = paramArrayOffloat[m];
/* 2321 */       float f2 = paramArrayOffloat[i1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2329 */       paramArrayOffloat[m] = paramArrayOffloat[paramInt1];
/* 2330 */       paramArrayOffloat[i1] = paramArrayOffloat[paramInt2];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2335 */       while (paramArrayOffloat[++i3] < f1);
/* 2336 */       while (paramArrayOffloat[--i4] > f2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2358 */       label149: for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 2359 */         float f = paramArrayOffloat[i5];
/* 2360 */         if (f < f1) {
/* 2361 */           paramArrayOffloat[i5] = paramArrayOffloat[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2366 */           paramArrayOffloat[i3] = f;
/* 2367 */           i3++; continue;
/* 2368 */         }  if (f > f2) {
/* 2369 */           while (paramArrayOffloat[i4] > f2) {
/* 2370 */             if (i4-- == i5) {
/*      */               break label149;
/*      */             }
/*      */           } 
/* 2374 */           if (paramArrayOffloat[i4] < f1) {
/* 2375 */             paramArrayOffloat[i5] = paramArrayOffloat[i3];
/* 2376 */             paramArrayOffloat[i3] = paramArrayOffloat[i4];
/* 2377 */             i3++;
/*      */           } else {
/* 2379 */             paramArrayOffloat[i5] = paramArrayOffloat[i4];
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2385 */           paramArrayOffloat[i4] = f;
/* 2386 */           i4--;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2391 */       paramArrayOffloat[paramInt1] = paramArrayOffloat[i3 - 1]; paramArrayOffloat[i3 - 1] = f1;
/* 2392 */       paramArrayOffloat[paramInt2] = paramArrayOffloat[i4 + 1]; paramArrayOffloat[i4 + 1] = f2;
/*      */ 
/*      */       
/* 2395 */       sort(paramArrayOffloat, paramInt1, i3 - 2, paramBoolean);
/* 2396 */       sort(paramArrayOffloat, i4 + 2, paramInt2, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2402 */       if (i3 < n && i2 < i4) {
/*      */ 
/*      */ 
/*      */         
/* 2406 */         while (paramArrayOffloat[i3] == f1) {
/* 2407 */           i3++;
/*      */         }
/*      */         
/* 2410 */         while (paramArrayOffloat[i4] == f2) {
/* 2411 */           i4--;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2434 */         for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 2435 */           float f = paramArrayOffloat[i5];
/* 2436 */           if (f == f1) {
/* 2437 */             paramArrayOffloat[i5] = paramArrayOffloat[i3];
/* 2438 */             paramArrayOffloat[i3] = f;
/* 2439 */             i3++; continue;
/* 2440 */           }  if (f == f2) {
/* 2441 */             while (paramArrayOffloat[i4] == f2) {
/* 2442 */               if (i4-- == i5) {
/*      */                 // Byte code: goto -> 1067
/*      */               }
/*      */             } 
/* 2446 */             if (paramArrayOffloat[i4] == f1) {
/* 2447 */               paramArrayOffloat[i5] = paramArrayOffloat[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2456 */               paramArrayOffloat[i3] = paramArrayOffloat[i4];
/* 2457 */               i3++;
/*      */             } else {
/* 2459 */               paramArrayOffloat[i5] = paramArrayOffloat[i4];
/*      */             } 
/* 2461 */             paramArrayOffloat[i4] = f;
/* 2462 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2468 */       sort(paramArrayOffloat, i3, i4, false);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2475 */       float f = paramArrayOffloat[k];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2497 */       for (int i5 = i3; i5 <= i4; i5++) {
/* 2498 */         if (paramArrayOffloat[i5] != f) {
/*      */ 
/*      */           
/* 2501 */           float f1 = paramArrayOffloat[i5];
/* 2502 */           if (f1 < f) {
/* 2503 */             paramArrayOffloat[i5] = paramArrayOffloat[i3];
/* 2504 */             paramArrayOffloat[i3] = f1;
/* 2505 */             i3++;
/*      */           } else {
/* 2507 */             while (paramArrayOffloat[i4] > f) {
/* 2508 */               i4--;
/*      */             }
/* 2510 */             if (paramArrayOffloat[i4] < f) {
/* 2511 */               paramArrayOffloat[i5] = paramArrayOffloat[i3];
/* 2512 */               paramArrayOffloat[i3] = paramArrayOffloat[i4];
/* 2513 */               i3++;
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2523 */               paramArrayOffloat[i5] = paramArrayOffloat[i4];
/*      */             } 
/* 2525 */             paramArrayOffloat[i4] = f1;
/* 2526 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2535 */       sort(paramArrayOffloat, paramInt1, i3 - 1, paramBoolean);
/* 2536 */       sort(paramArrayOffloat, i4 + 1, paramInt2, false);
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
/*      */   static void sort(double[] paramArrayOfdouble1, int paramInt1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3, int paramInt4) {
/* 2556 */     while (paramInt1 <= paramInt2 && Double.isNaN(paramArrayOfdouble1[paramInt2]))
/* 2557 */       paramInt2--; 
/*      */     int i;
/* 2559 */     for (i = paramInt2; --i >= paramInt1; ) {
/* 2560 */       double d = paramArrayOfdouble1[i];
/* 2561 */       if (d != d) {
/* 2562 */         paramArrayOfdouble1[i] = paramArrayOfdouble1[paramInt2];
/* 2563 */         paramArrayOfdouble1[paramInt2] = d;
/* 2564 */         paramInt2--;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2571 */     doSort(paramArrayOfdouble1, paramInt1, paramInt2, paramArrayOfdouble2, paramInt3, paramInt4);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2576 */     i = paramInt2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2581 */     while (paramInt1 < i) {
/* 2582 */       int m = paramInt1 + i >>> 1;
/* 2583 */       double d = paramArrayOfdouble1[m];
/*      */       
/* 2585 */       if (d < 0.0D) {
/* 2586 */         paramInt1 = m + 1; continue;
/*      */       } 
/* 2588 */       i = m;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2595 */     while (paramInt1 <= paramInt2 && Double.doubleToRawLongBits(paramArrayOfdouble1[paramInt1]) < 0L) {
/* 2596 */       paramInt1++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2620 */     for (int j = paramInt1, k = paramInt1 - 1; ++j <= paramInt2; ) {
/* 2621 */       double d = paramArrayOfdouble1[j];
/* 2622 */       if (d != 0.0D) {
/*      */         break;
/*      */       }
/* 2625 */       if (Double.doubleToRawLongBits(d) < 0L) {
/* 2626 */         paramArrayOfdouble1[j] = 0.0D;
/* 2627 */         paramArrayOfdouble1[++k] = -0.0D;
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
/*      */   private static void doSort(double[] paramArrayOfdouble1, int paramInt1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3, int paramInt4) {
/*      */     double[] arrayOfDouble;
/*      */     int k, m;
/* 2645 */     if (paramInt2 - paramInt1 < 286) {
/* 2646 */       sort(paramArrayOfdouble1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 2654 */     int[] arrayOfInt = new int[68];
/* 2655 */     byte b = 0; arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int i;
/* 2658 */     for (i = paramInt1; i < paramInt2; arrayOfInt[b] = i) {
/* 2659 */       if (paramArrayOfdouble1[i] < paramArrayOfdouble1[i + 1]) {
/* 2660 */         while (++i <= paramInt2 && paramArrayOfdouble1[i - 1] <= paramArrayOfdouble1[i]);
/* 2661 */       } else if (paramArrayOfdouble1[i] > paramArrayOfdouble1[i + 1]) {
/* 2662 */         while (++i <= paramInt2 && paramArrayOfdouble1[i - 1] >= paramArrayOfdouble1[i]);
/* 2663 */         for (int i1 = arrayOfInt[b] - 1; ++i1 < --k; ) {
/* 2664 */           double d = paramArrayOfdouble1[i1]; paramArrayOfdouble1[i1] = paramArrayOfdouble1[k]; paramArrayOfdouble1[k] = d;
/*      */         } 
/*      */       } else {
/* 2667 */         for (byte b1 = 33; ++i <= paramInt2 && paramArrayOfdouble1[i - 1] == paramArrayOfdouble1[i];) {
/* 2668 */           if (--b1 == 0) {
/* 2669 */             sort(paramArrayOfdouble1, paramInt1, paramInt2, true);
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2679 */       if (++b == 67) {
/* 2680 */         sort(paramArrayOfdouble1, paramInt1, paramInt2, true);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 2687 */     if (arrayOfInt[b] == paramInt2++) {
/* 2688 */       arrayOfInt[++b] = paramInt2;
/* 2689 */     } else if (b == 1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 2694 */     i = 0;
/* 2695 */     for (int j = 1; (j <<= 1) < b; i = (byte)(i ^ 0x1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2700 */     int n = paramInt2 - paramInt1;
/* 2701 */     if (paramArrayOfdouble2 == null || paramInt4 < n || paramInt3 + n > paramArrayOfdouble2.length) {
/* 2702 */       paramArrayOfdouble2 = new double[n];
/* 2703 */       paramInt3 = 0;
/*      */     } 
/* 2705 */     if (i == 0) {
/* 2706 */       System.arraycopy(paramArrayOfdouble1, paramInt1, paramArrayOfdouble2, paramInt3, n);
/* 2707 */       arrayOfDouble = paramArrayOfdouble1;
/* 2708 */       m = 0;
/* 2709 */       paramArrayOfdouble1 = paramArrayOfdouble2;
/* 2710 */       k = paramInt3 - paramInt1;
/*      */     } else {
/* 2712 */       arrayOfDouble = paramArrayOfdouble2;
/* 2713 */       k = 0;
/* 2714 */       m = paramInt3 - paramInt1;
/*      */     } 
/*      */ 
/*      */     
/* 2718 */     for (; b > 1; b = b1) {
/* 2719 */       byte b1; int i1; for (i1 = (b1 = 0) + 2; i1 <= b; i1 += 2) {
/* 2720 */         int i3 = arrayOfInt[i1], i4 = arrayOfInt[i1 - 1];
/* 2721 */         for (int i5 = arrayOfInt[i1 - 2], i6 = i5, i7 = i4; i5 < i3; i5++) {
/* 2722 */           if (i7 >= i3 || (i6 < i4 && paramArrayOfdouble1[i6 + k] <= paramArrayOfdouble1[i7 + k])) {
/* 2723 */             arrayOfDouble[i5 + m] = paramArrayOfdouble1[i6++ + k];
/*      */           } else {
/* 2725 */             arrayOfDouble[i5 + m] = paramArrayOfdouble1[i7++ + k];
/*      */           } 
/*      */         } 
/* 2728 */         arrayOfInt[++b1] = i3;
/*      */       } 
/* 2730 */       if ((b & 0x1) != 0) {
/* 2731 */         int i3; for (i1 = paramInt2, i3 = arrayOfInt[b - 1]; --i1 >= i3;) {
/* 2732 */           arrayOfDouble[i1 + m] = paramArrayOfdouble1[i1 + k];
/*      */         }
/* 2734 */         arrayOfInt[++b1] = paramInt2;
/*      */       } 
/* 2736 */       double[] arrayOfDouble1 = paramArrayOfdouble1; paramArrayOfdouble1 = arrayOfDouble; arrayOfDouble = arrayOfDouble1;
/* 2737 */       int i2 = k; k = m; m = i2;
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
/*      */   private static void sort(double[] paramArrayOfdouble, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2750 */     int i = paramInt2 - paramInt1 + 1;
/*      */ 
/*      */     
/* 2753 */     if (i < 47) {
/* 2754 */       if (paramBoolean) {
/*      */         int i6;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2760 */         for (int i5 = paramInt1; i5 < paramInt2; i6 = ++i5) {
/* 2761 */           double d = paramArrayOfdouble[i5 + 1];
/* 2762 */           while (d < paramArrayOfdouble[i6]) {
/* 2763 */             paramArrayOfdouble[i6 + 1] = paramArrayOfdouble[i6];
/* 2764 */             if (i6-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           } 
/* 2768 */           paramArrayOfdouble[i6 + 1] = d;
/*      */         } 
/*      */       } else {
/*      */ 
/*      */         
/*      */         do {
/*      */           
/* 2775 */           if (paramInt1 >= paramInt2) {
/*      */             return;
/*      */           }
/* 2778 */         } while (paramArrayOfdouble[++paramInt1] >= paramArrayOfdouble[paramInt1 - 1]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2788 */         for (i5 = paramInt1; ++paramInt1 <= paramInt2; i5 = ++paramInt1) {
/* 2789 */           double d1 = paramArrayOfdouble[i5], d2 = paramArrayOfdouble[paramInt1];
/*      */           
/* 2791 */           if (d1 < d2) {
/* 2792 */             d2 = d1; d1 = paramArrayOfdouble[paramInt1];
/*      */           } 
/* 2794 */           while (d1 < paramArrayOfdouble[--i5]) {
/* 2795 */             paramArrayOfdouble[i5 + 2] = paramArrayOfdouble[i5];
/*      */           }
/* 2797 */           paramArrayOfdouble[++i5 + 1] = d1;
/*      */           
/* 2799 */           while (d2 < paramArrayOfdouble[--i5]) {
/* 2800 */             paramArrayOfdouble[i5 + 1] = paramArrayOfdouble[i5];
/*      */           }
/* 2802 */           paramArrayOfdouble[i5 + 1] = d2;
/*      */         } 
/* 2804 */         double d = paramArrayOfdouble[paramInt2];
/*      */         
/* 2806 */         while (d < paramArrayOfdouble[--paramInt2]) {
/* 2807 */           paramArrayOfdouble[paramInt2 + 1] = paramArrayOfdouble[paramInt2];
/*      */         }
/* 2809 */         paramArrayOfdouble[paramInt2 + 1] = d;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2815 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2824 */     int k = paramInt1 + paramInt2 >>> 1;
/* 2825 */     int m = k - j;
/* 2826 */     int n = m - j;
/* 2827 */     int i1 = k + j;
/* 2828 */     int i2 = i1 + j;
/*      */ 
/*      */     
/* 2831 */     if (paramArrayOfdouble[m] < paramArrayOfdouble[n]) { double d = paramArrayOfdouble[m]; paramArrayOfdouble[m] = paramArrayOfdouble[n]; paramArrayOfdouble[n] = d; }
/*      */     
/* 2833 */     if (paramArrayOfdouble[k] < paramArrayOfdouble[m]) { double d = paramArrayOfdouble[k]; paramArrayOfdouble[k] = paramArrayOfdouble[m]; paramArrayOfdouble[m] = d;
/* 2834 */       if (d < paramArrayOfdouble[n]) { paramArrayOfdouble[m] = paramArrayOfdouble[n]; paramArrayOfdouble[n] = d; }
/*      */        }
/* 2836 */      if (paramArrayOfdouble[i1] < paramArrayOfdouble[k]) { double d = paramArrayOfdouble[i1]; paramArrayOfdouble[i1] = paramArrayOfdouble[k]; paramArrayOfdouble[k] = d;
/* 2837 */       if (d < paramArrayOfdouble[m]) { paramArrayOfdouble[k] = paramArrayOfdouble[m]; paramArrayOfdouble[m] = d;
/* 2838 */         if (d < paramArrayOfdouble[n]) { paramArrayOfdouble[m] = paramArrayOfdouble[n]; paramArrayOfdouble[n] = d; }
/*      */          }
/*      */        }
/* 2841 */      if (paramArrayOfdouble[i2] < paramArrayOfdouble[i1]) { double d = paramArrayOfdouble[i2]; paramArrayOfdouble[i2] = paramArrayOfdouble[i1]; paramArrayOfdouble[i1] = d;
/* 2842 */       if (d < paramArrayOfdouble[k]) { paramArrayOfdouble[i1] = paramArrayOfdouble[k]; paramArrayOfdouble[k] = d;
/* 2843 */         if (d < paramArrayOfdouble[m]) { paramArrayOfdouble[k] = paramArrayOfdouble[m]; paramArrayOfdouble[m] = d;
/* 2844 */           if (d < paramArrayOfdouble[n]) { paramArrayOfdouble[m] = paramArrayOfdouble[n]; paramArrayOfdouble[n] = d; }
/*      */            }
/*      */          }
/*      */        }
/*      */ 
/*      */     
/* 2850 */     int i3 = paramInt1;
/* 2851 */     int i4 = paramInt2;
/*      */     
/* 2853 */     if (paramArrayOfdouble[n] != paramArrayOfdouble[m] && paramArrayOfdouble[m] != paramArrayOfdouble[k] && paramArrayOfdouble[k] != paramArrayOfdouble[i1] && paramArrayOfdouble[i1] != paramArrayOfdouble[i2]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2859 */       double d1 = paramArrayOfdouble[m];
/* 2860 */       double d2 = paramArrayOfdouble[i1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2868 */       paramArrayOfdouble[m] = paramArrayOfdouble[paramInt1];
/* 2869 */       paramArrayOfdouble[i1] = paramArrayOfdouble[paramInt2];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2874 */       while (paramArrayOfdouble[++i3] < d1);
/* 2875 */       while (paramArrayOfdouble[--i4] > d2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2897 */       label149: for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 2898 */         double d = paramArrayOfdouble[i5];
/* 2899 */         if (d < d1) {
/* 2900 */           paramArrayOfdouble[i5] = paramArrayOfdouble[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2905 */           paramArrayOfdouble[i3] = d;
/* 2906 */           i3++; continue;
/* 2907 */         }  if (d > d2) {
/* 2908 */           while (paramArrayOfdouble[i4] > d2) {
/* 2909 */             if (i4-- == i5) {
/*      */               break label149;
/*      */             }
/*      */           } 
/* 2913 */           if (paramArrayOfdouble[i4] < d1) {
/* 2914 */             paramArrayOfdouble[i5] = paramArrayOfdouble[i3];
/* 2915 */             paramArrayOfdouble[i3] = paramArrayOfdouble[i4];
/* 2916 */             i3++;
/*      */           } else {
/* 2918 */             paramArrayOfdouble[i5] = paramArrayOfdouble[i4];
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2924 */           paramArrayOfdouble[i4] = d;
/* 2925 */           i4--;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2930 */       paramArrayOfdouble[paramInt1] = paramArrayOfdouble[i3 - 1]; paramArrayOfdouble[i3 - 1] = d1;
/* 2931 */       paramArrayOfdouble[paramInt2] = paramArrayOfdouble[i4 + 1]; paramArrayOfdouble[i4 + 1] = d2;
/*      */ 
/*      */       
/* 2934 */       sort(paramArrayOfdouble, paramInt1, i3 - 2, paramBoolean);
/* 2935 */       sort(paramArrayOfdouble, i4 + 2, paramInt2, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2941 */       if (i3 < n && i2 < i4) {
/*      */ 
/*      */ 
/*      */         
/* 2945 */         while (paramArrayOfdouble[i3] == d1) {
/* 2946 */           i3++;
/*      */         }
/*      */         
/* 2949 */         while (paramArrayOfdouble[i4] == d2) {
/* 2950 */           i4--;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2973 */         for (i5 = i3 - 1; ++i5 <= i4; ) {
/* 2974 */           double d = paramArrayOfdouble[i5];
/* 2975 */           if (d == d1) {
/* 2976 */             paramArrayOfdouble[i5] = paramArrayOfdouble[i3];
/* 2977 */             paramArrayOfdouble[i3] = d;
/* 2978 */             i3++; continue;
/* 2979 */           }  if (d == d2) {
/* 2980 */             while (paramArrayOfdouble[i4] == d2) {
/* 2981 */               if (i4-- == i5) {
/*      */                 // Byte code: goto -> 1067
/*      */               }
/*      */             } 
/* 2985 */             if (paramArrayOfdouble[i4] == d1) {
/* 2986 */               paramArrayOfdouble[i5] = paramArrayOfdouble[i3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2995 */               paramArrayOfdouble[i3] = paramArrayOfdouble[i4];
/* 2996 */               i3++;
/*      */             } else {
/* 2998 */               paramArrayOfdouble[i5] = paramArrayOfdouble[i4];
/*      */             } 
/* 3000 */             paramArrayOfdouble[i4] = d;
/* 3001 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3007 */       sort(paramArrayOfdouble, i3, i4, false);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 3014 */       double d = paramArrayOfdouble[k];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3036 */       for (int i5 = i3; i5 <= i4; i5++) {
/* 3037 */         if (paramArrayOfdouble[i5] != d) {
/*      */ 
/*      */           
/* 3040 */           double d1 = paramArrayOfdouble[i5];
/* 3041 */           if (d1 < d) {
/* 3042 */             paramArrayOfdouble[i5] = paramArrayOfdouble[i3];
/* 3043 */             paramArrayOfdouble[i3] = d1;
/* 3044 */             i3++;
/*      */           } else {
/* 3046 */             while (paramArrayOfdouble[i4] > d) {
/* 3047 */               i4--;
/*      */             }
/* 3049 */             if (paramArrayOfdouble[i4] < d) {
/* 3050 */               paramArrayOfdouble[i5] = paramArrayOfdouble[i3];
/* 3051 */               paramArrayOfdouble[i3] = paramArrayOfdouble[i4];
/* 3052 */               i3++;
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3062 */               paramArrayOfdouble[i5] = paramArrayOfdouble[i4];
/*      */             } 
/* 3064 */             paramArrayOfdouble[i4] = d1;
/* 3065 */             i4--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3074 */       sort(paramArrayOfdouble, paramInt1, i3 - 1, paramBoolean);
/* 3075 */       sort(paramArrayOfdouble, i4 + 1, paramInt2, false);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/DualPivotQuicksort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */