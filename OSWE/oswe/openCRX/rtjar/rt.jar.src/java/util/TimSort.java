/*     */ package java.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TimSort<T>
/*     */ {
/*     */   private static final int MIN_MERGE = 32;
/*     */   private final T[] a;
/*     */   private final Comparator<? super T> c;
/*     */   private static final int MIN_GALLOP = 7;
/* 103 */   private int minGallop = 7;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T[] tmp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int tmpBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int tmpLen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   private int stackSize = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] runBase;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] runLen;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TimSort(T[] paramArrayOfT1, Comparator<? super T> paramComparator, T[] paramArrayOfT2, int paramInt1, int paramInt2) {
/* 147 */     this.a = paramArrayOfT1;
/* 148 */     this.c = paramComparator;
/*     */ 
/*     */     
/* 151 */     int i = paramArrayOfT1.length;
/* 152 */     byte b1 = (i < 512) ? (i >>> 1) : 256;
/*     */     
/* 154 */     if (paramArrayOfT2 == null || paramInt2 < b1 || paramInt1 + b1 > paramArrayOfT2.length) {
/*     */ 
/*     */       
/* 157 */       Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT1.getClass().getComponentType(), b1);
/* 158 */       this.tmp = (T[])arrayOfObject;
/* 159 */       this.tmpBase = 0;
/* 160 */       this.tmpLen = b1;
/*     */     } else {
/*     */       
/* 163 */       this.tmp = paramArrayOfT2;
/* 164 */       this.tmpBase = paramInt1;
/* 165 */       this.tmpLen = paramInt2;
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
/* 182 */     byte b2 = (i < 120) ? 5 : ((i < 1542) ? 10 : ((i < 119151) ? 24 : 49));
/*     */ 
/*     */     
/* 185 */     this.runBase = new int[b2];
/* 186 */     this.runLen = new int[b2];
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
/*     */   static <T> void sort(T[] paramArrayOfT1, int paramInt1, int paramInt2, Comparator<? super T> paramComparator, T[] paramArrayOfT2, int paramInt3, int paramInt4) {
/* 212 */     assert paramComparator != null && paramArrayOfT1 != null && paramInt1 >= 0 && paramInt1 <= paramInt2 && paramInt2 <= paramArrayOfT1.length;
/*     */     
/* 214 */     int i = paramInt2 - paramInt1;
/* 215 */     if (i < 2) {
/*     */       return;
/*     */     }
/*     */     
/* 219 */     if (i < 32) {
/* 220 */       int k = countRunAndMakeAscending(paramArrayOfT1, paramInt1, paramInt2, paramComparator);
/* 221 */       binarySort(paramArrayOfT1, paramInt1, paramInt2, paramInt1 + k, paramComparator);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 230 */     TimSort<T> timSort = new TimSort<>(paramArrayOfT1, paramComparator, paramArrayOfT2, paramInt3, paramInt4);
/* 231 */     int j = minRunLength(i);
/*     */     
/*     */     do {
/* 234 */       int k = countRunAndMakeAscending(paramArrayOfT1, paramInt1, paramInt2, paramComparator);
/*     */ 
/*     */       
/* 237 */       if (k < j) {
/* 238 */         int m = (i <= j) ? i : j;
/* 239 */         binarySort(paramArrayOfT1, paramInt1, paramInt1 + m, paramInt1 + k, paramComparator);
/* 240 */         k = m;
/*     */       } 
/*     */ 
/*     */       
/* 244 */       timSort.pushRun(paramInt1, k);
/* 245 */       timSort.mergeCollapse();
/*     */ 
/*     */       
/* 248 */       paramInt1 += k;
/* 249 */       i -= k;
/* 250 */     } while (i != 0);
/*     */ 
/*     */     
/* 253 */     assert paramInt1 == paramInt2;
/* 254 */     timSort.mergeForceCollapse();
/* 255 */     assert timSort.stackSize == 1;
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
/*     */   private static <T> void binarySort(T[] paramArrayOfT, int paramInt1, int paramInt2, int paramInt3, Comparator<? super T> paramComparator) {
/* 279 */     assert paramInt1 <= paramInt3 && paramInt3 <= paramInt2;
/* 280 */     if (paramInt3 == paramInt1)
/* 281 */       paramInt3++; 
/* 282 */     for (; paramInt3 < paramInt2; paramInt3++) {
/* 283 */       T t = paramArrayOfT[paramInt3];
/*     */ 
/*     */       
/* 286 */       int i = paramInt1;
/* 287 */       int j = paramInt3;
/* 288 */       assert i <= j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 294 */       while (i < j) {
/* 295 */         int m = i + j >>> 1;
/* 296 */         if (paramComparator.compare(t, paramArrayOfT[m]) < 0) {
/* 297 */           j = m; continue;
/*     */         } 
/* 299 */         i = m + 1;
/*     */       } 
/* 301 */       assert i == j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 310 */       int k = paramInt3 - i;
/*     */       
/* 312 */       switch (k) { case 2:
/* 313 */           paramArrayOfT[i + 2] = paramArrayOfT[i + 1];
/* 314 */         case 1: paramArrayOfT[i + 1] = paramArrayOfT[i]; break;
/*     */         default:
/* 316 */           System.arraycopy(paramArrayOfT, i, paramArrayOfT, i + 1, k); break; }
/*     */       
/* 318 */       paramArrayOfT[i] = t;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> int countRunAndMakeAscending(T[] paramArrayOfT, int paramInt1, int paramInt2, Comparator<? super T> paramComparator) {
/* 349 */     assert paramInt1 < paramInt2;
/* 350 */     int i = paramInt1 + 1;
/* 351 */     if (i == paramInt2) {
/* 352 */       return 1;
/*     */     }
/*     */     
/* 355 */     if (paramComparator.compare(paramArrayOfT[i++], paramArrayOfT[paramInt1]) < 0) {
/* 356 */       while (i < paramInt2 && paramComparator.compare(paramArrayOfT[i], paramArrayOfT[i - 1]) < 0)
/* 357 */         i++; 
/* 358 */       reverseRange((Object[])paramArrayOfT, paramInt1, i);
/*     */     } else {
/* 360 */       while (i < paramInt2 && paramComparator.compare(paramArrayOfT[i], paramArrayOfT[i - 1]) >= 0) {
/* 361 */         i++;
/*     */       }
/*     */     } 
/* 364 */     return i - paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void reverseRange(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/* 375 */     paramInt2--;
/* 376 */     while (paramInt1 < paramInt2) {
/* 377 */       Object object = paramArrayOfObject[paramInt1];
/* 378 */       paramArrayOfObject[paramInt1++] = paramArrayOfObject[paramInt2];
/* 379 */       paramArrayOfObject[paramInt2--] = object;
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
/*     */ 
/*     */   
/*     */   private static int minRunLength(int paramInt) {
/* 401 */     assert paramInt >= 0;
/* 402 */     int i = 0;
/* 403 */     while (paramInt >= 32) {
/* 404 */       i |= paramInt & 0x1;
/* 405 */       paramInt >>= 1;
/*     */     } 
/* 407 */     return paramInt + i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pushRun(int paramInt1, int paramInt2) {
/* 417 */     this.runBase[this.stackSize] = paramInt1;
/* 418 */     this.runLen[this.stackSize] = paramInt2;
/* 419 */     this.stackSize++;
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
/*     */   private void mergeCollapse() {
/* 434 */     while (this.stackSize > 1) {
/* 435 */       int i = this.stackSize - 2;
/* 436 */       if (i > 0 && this.runLen[i - 1] <= this.runLen[i] + this.runLen[i + 1]) {
/* 437 */         if (this.runLen[i - 1] < this.runLen[i + 1])
/* 438 */           i--; 
/* 439 */         mergeAt(i); continue;
/* 440 */       }  if (this.runLen[i] <= this.runLen[i + 1]) {
/* 441 */         mergeAt(i);
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
/*     */   private void mergeForceCollapse() {
/* 453 */     while (this.stackSize > 1) {
/* 454 */       int i = this.stackSize - 2;
/* 455 */       if (i > 0 && this.runLen[i - 1] < this.runLen[i + 1])
/* 456 */         i--; 
/* 457 */       mergeAt(i);
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
/*     */   private void mergeAt(int paramInt) {
/* 469 */     assert this.stackSize >= 2;
/* 470 */     assert paramInt >= 0;
/* 471 */     assert paramInt == this.stackSize - 2 || paramInt == this.stackSize - 3;
/*     */     
/* 473 */     int i = this.runBase[paramInt];
/* 474 */     int j = this.runLen[paramInt];
/* 475 */     int k = this.runBase[paramInt + 1];
/* 476 */     int m = this.runLen[paramInt + 1];
/* 477 */     assert j > 0 && m > 0;
/* 478 */     assert i + j == k;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 485 */     this.runLen[paramInt] = j + m;
/* 486 */     if (paramInt == this.stackSize - 3) {
/* 487 */       this.runBase[paramInt + 1] = this.runBase[paramInt + 2];
/* 488 */       this.runLen[paramInt + 1] = this.runLen[paramInt + 2];
/*     */     } 
/* 490 */     this.stackSize--;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 496 */     int n = gallopRight(this.a[k], this.a, i, j, 0, this.c);
/* 497 */     assert n >= 0;
/* 498 */     i += n;
/* 499 */     j -= n;
/* 500 */     if (j == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     m = gallopLeft(this.a[i + j - 1], this.a, k, m, m - 1, this.c);
/* 508 */     assert m >= 0;
/* 509 */     if (m == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 513 */     if (j <= m) {
/* 514 */       mergeLo(i, j, k, m);
/*     */     } else {
/* 516 */       mergeHi(i, j, k, m);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> int gallopLeft(T paramT, T[] paramArrayOfT, int paramInt1, int paramInt2, int paramInt3, Comparator<? super T> paramComparator) {
/* 539 */     assert paramInt2 > 0 && paramInt3 >= 0 && paramInt3 < paramInt2;
/* 540 */     int i = 0;
/* 541 */     int j = 1;
/* 542 */     if (paramComparator.compare(paramT, paramArrayOfT[paramInt1 + paramInt3]) > 0) {
/*     */       
/* 544 */       int k = paramInt2 - paramInt3;
/* 545 */       while (j < k && paramComparator.compare(paramT, paramArrayOfT[paramInt1 + paramInt3 + j]) > 0) {
/* 546 */         i = j;
/* 547 */         j = (j << 1) + 1;
/* 548 */         if (j <= 0)
/* 549 */           j = k; 
/*     */       } 
/* 551 */       if (j > k) {
/* 552 */         j = k;
/*     */       }
/*     */       
/* 555 */       i += paramInt3;
/* 556 */       j += paramInt3;
/*     */     } else {
/*     */       
/* 559 */       int k = paramInt3 + 1;
/* 560 */       while (j < k && paramComparator.compare(paramT, paramArrayOfT[paramInt1 + paramInt3 - j]) <= 0) {
/* 561 */         i = j;
/* 562 */         j = (j << 1) + 1;
/* 563 */         if (j <= 0)
/* 564 */           j = k; 
/*     */       } 
/* 566 */       if (j > k) {
/* 567 */         j = k;
/*     */       }
/*     */       
/* 570 */       int m = i;
/* 571 */       i = paramInt3 - j;
/* 572 */       j = paramInt3 - m;
/*     */     } 
/* 574 */     assert -1 <= i && i < j && j <= paramInt2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     i++;
/* 582 */     while (i < j) {
/* 583 */       int k = i + (j - i >>> 1);
/*     */       
/* 585 */       if (paramComparator.compare(paramT, paramArrayOfT[paramInt1 + k]) > 0) {
/* 586 */         i = k + 1; continue;
/*     */       } 
/* 588 */       j = k;
/*     */     } 
/* 590 */     assert i == j;
/* 591 */     return j;
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
/*     */   private static <T> int gallopRight(T paramT, T[] paramArrayOfT, int paramInt1, int paramInt2, int paramInt3, Comparator<? super T> paramComparator) {
/* 609 */     assert paramInt2 > 0 && paramInt3 >= 0 && paramInt3 < paramInt2;
/*     */     
/* 611 */     int i = 1;
/* 612 */     int j = 0;
/* 613 */     if (paramComparator.compare(paramT, paramArrayOfT[paramInt1 + paramInt3]) < 0) {
/*     */       
/* 615 */       int k = paramInt3 + 1;
/* 616 */       while (i < k && paramComparator.compare(paramT, paramArrayOfT[paramInt1 + paramInt3 - i]) < 0) {
/* 617 */         j = i;
/* 618 */         i = (i << 1) + 1;
/* 619 */         if (i <= 0)
/* 620 */           i = k; 
/*     */       } 
/* 622 */       if (i > k) {
/* 623 */         i = k;
/*     */       }
/*     */       
/* 626 */       int m = j;
/* 627 */       j = paramInt3 - i;
/* 628 */       i = paramInt3 - m;
/*     */     } else {
/*     */       
/* 631 */       int k = paramInt2 - paramInt3;
/* 632 */       while (i < k && paramComparator.compare(paramT, paramArrayOfT[paramInt1 + paramInt3 + i]) >= 0) {
/* 633 */         j = i;
/* 634 */         i = (i << 1) + 1;
/* 635 */         if (i <= 0)
/* 636 */           i = k; 
/*     */       } 
/* 638 */       if (i > k) {
/* 639 */         i = k;
/*     */       }
/*     */       
/* 642 */       j += paramInt3;
/* 643 */       i += paramInt3;
/*     */     } 
/* 645 */     assert -1 <= j && j < i && i <= paramInt2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 652 */     j++;
/* 653 */     while (j < i) {
/* 654 */       int k = j + (i - j >>> 1);
/*     */       
/* 656 */       if (paramComparator.compare(paramT, paramArrayOfT[paramInt1 + k]) < 0) {
/* 657 */         i = k; continue;
/*     */       } 
/* 659 */       j = k + 1;
/*     */     } 
/* 661 */     assert j == i;
/* 662 */     return i;
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
/*     */   private void mergeLo(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 682 */     assert paramInt2 > 0 && paramInt4 > 0 && paramInt1 + paramInt2 == paramInt3;
/*     */ 
/*     */     
/* 685 */     T[] arrayOfT1 = this.a;
/* 686 */     T[] arrayOfT2 = ensureCapacity(paramInt2);
/* 687 */     int i = this.tmpBase;
/* 688 */     int j = paramInt3;
/* 689 */     int k = paramInt1;
/* 690 */     System.arraycopy(arrayOfT1, paramInt1, arrayOfT2, i, paramInt2);
/*     */ 
/*     */     
/* 693 */     arrayOfT1[k++] = arrayOfT1[j++];
/* 694 */     if (--paramInt4 == 0) {
/* 695 */       System.arraycopy(arrayOfT2, i, arrayOfT1, k, paramInt2);
/*     */       return;
/*     */     } 
/* 698 */     if (paramInt2 == 1) {
/* 699 */       System.arraycopy(arrayOfT1, j, arrayOfT1, k, paramInt4);
/* 700 */       arrayOfT1[k + paramInt4] = arrayOfT2[i];
/*     */       
/*     */       return;
/*     */     } 
/* 704 */     Comparator<? super T> comparator = this.c;
/* 705 */     int m = this.minGallop;
/*     */     
/*     */     while (true) {
/* 708 */       int n = 0;
/* 709 */       int i1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 716 */         assert paramInt2 > 1 && paramInt4 > 0;
/* 717 */         if (comparator.compare(arrayOfT1[j], arrayOfT2[i]) < 0) {
/* 718 */           arrayOfT1[k++] = arrayOfT1[j++];
/* 719 */           i1++;
/* 720 */           n = 0;
/* 721 */           if (--paramInt4 == 0)
/*     */             break; 
/*     */         } else {
/* 724 */           arrayOfT1[k++] = arrayOfT2[i++];
/* 725 */           n++;
/* 726 */           i1 = 0;
/* 727 */           if (--paramInt2 == 1)
/*     */             break; 
/*     */         } 
/* 730 */         if ((n | i1) >= m)
/*     */         
/*     */         { 
/*     */           
/*     */           label79: while (true)
/*     */           
/*     */           { 
/*     */             
/* 738 */             assert paramInt2 > 1 && paramInt4 > 0;
/* 739 */             n = gallopRight(arrayOfT1[j], arrayOfT2, i, paramInt2, 0, comparator);
/* 740 */             if (n != 0) {
/* 741 */               System.arraycopy(arrayOfT2, i, arrayOfT1, k, n);
/* 742 */               k += n;
/* 743 */               i += n;
/* 744 */               paramInt2 -= n;
/* 745 */               if (paramInt2 <= 1)
/*     */                 break; 
/*     */             } 
/* 748 */             arrayOfT1[k++] = arrayOfT1[j++];
/* 749 */             if (--paramInt4 == 0) {
/*     */               break;
/*     */             }
/* 752 */             i1 = gallopLeft(arrayOfT2[i], arrayOfT1, j, paramInt4, 0, comparator);
/* 753 */             if (i1 != 0) {
/* 754 */               System.arraycopy(arrayOfT1, j, arrayOfT1, k, i1);
/* 755 */               k += i1;
/* 756 */               j += i1;
/* 757 */               paramInt4 -= i1;
/* 758 */               if (paramInt4 == 0)
/*     */                 break; 
/*     */             } 
/* 761 */             arrayOfT1[k++] = arrayOfT2[i++];
/* 762 */             if (--paramInt2 == 1)
/*     */               break; 
/* 764 */             m--;
/* 765 */             if ((((n >= 7) ? 1 : 0) | ((i1 >= 7) ? 1 : 0)) == 0)
/* 766 */             { if (m < 0)
/* 767 */               { m = 0;
/* 768 */                 m += 2; continue; }  break label79; }  }  break; } 
/*     */       }  break;
/* 770 */     }  this.minGallop = (m < 1) ? 1 : m;
/*     */     
/* 772 */     if (paramInt2 == 1)
/* 773 */     { assert paramInt4 > 0;
/* 774 */       System.arraycopy(arrayOfT1, j, arrayOfT1, k, paramInt4);
/* 775 */       arrayOfT1[k + paramInt4] = arrayOfT2[i]; }
/* 776 */     else { if (paramInt2 == 0) {
/* 777 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 780 */       assert paramInt4 == 0;
/* 781 */       assert paramInt2 > 1;
/* 782 */       System.arraycopy(arrayOfT2, i, arrayOfT1, k, paramInt2); }
/*     */   
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
/*     */   private void mergeHi(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 798 */     assert paramInt2 > 0 && paramInt4 > 0 && paramInt1 + paramInt2 == paramInt3;
/*     */ 
/*     */     
/* 801 */     T[] arrayOfT1 = this.a;
/* 802 */     T[] arrayOfT2 = ensureCapacity(paramInt4);
/* 803 */     int i = this.tmpBase;
/* 804 */     System.arraycopy(arrayOfT1, paramInt3, arrayOfT2, i, paramInt4);
/*     */     
/* 806 */     int j = paramInt1 + paramInt2 - 1;
/* 807 */     int k = i + paramInt4 - 1;
/* 808 */     int m = paramInt3 + paramInt4 - 1;
/*     */ 
/*     */     
/* 811 */     arrayOfT1[m--] = arrayOfT1[j--];
/* 812 */     if (--paramInt2 == 0) {
/* 813 */       System.arraycopy(arrayOfT2, i, arrayOfT1, m - paramInt4 - 1, paramInt4);
/*     */       return;
/*     */     } 
/* 816 */     if (paramInt4 == 1) {
/* 817 */       m -= paramInt2;
/* 818 */       j -= paramInt2;
/* 819 */       System.arraycopy(arrayOfT1, j + 1, arrayOfT1, m + 1, paramInt2);
/* 820 */       arrayOfT1[m] = arrayOfT2[k];
/*     */       
/*     */       return;
/*     */     } 
/* 824 */     Comparator<? super T> comparator = this.c;
/* 825 */     int n = this.minGallop;
/*     */     
/*     */     while (true) {
/* 828 */       int i1 = 0;
/* 829 */       int i2 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 836 */         assert paramInt2 > 0 && paramInt4 > 1;
/* 837 */         if (comparator.compare(arrayOfT2[k], arrayOfT1[j]) < 0) {
/* 838 */           arrayOfT1[m--] = arrayOfT1[j--];
/* 839 */           i1++;
/* 840 */           i2 = 0;
/* 841 */           if (--paramInt2 == 0)
/*     */             break; 
/*     */         } else {
/* 844 */           arrayOfT1[m--] = arrayOfT2[k--];
/* 845 */           i2++;
/* 846 */           i1 = 0;
/* 847 */           if (--paramInt4 == 1)
/*     */             break; 
/*     */         } 
/* 850 */         if ((i1 | i2) >= n)
/*     */         
/*     */         { 
/*     */           
/*     */           label79: while (true)
/*     */           
/*     */           { 
/*     */             
/* 858 */             assert paramInt2 > 0 && paramInt4 > 1;
/* 859 */             i1 = paramInt2 - gallopRight(arrayOfT2[k], arrayOfT1, paramInt1, paramInt2, paramInt2 - 1, comparator);
/* 860 */             if (i1 != 0) {
/* 861 */               m -= i1;
/* 862 */               j -= i1;
/* 863 */               paramInt2 -= i1;
/* 864 */               System.arraycopy(arrayOfT1, j + 1, arrayOfT1, m + 1, i1);
/* 865 */               if (paramInt2 == 0)
/*     */                 break; 
/*     */             } 
/* 868 */             arrayOfT1[m--] = arrayOfT2[k--];
/* 869 */             if (--paramInt4 == 1) {
/*     */               break;
/*     */             }
/* 872 */             i2 = paramInt4 - gallopLeft(arrayOfT1[j], arrayOfT2, i, paramInt4, paramInt4 - 1, comparator);
/* 873 */             if (i2 != 0) {
/* 874 */               m -= i2;
/* 875 */               k -= i2;
/* 876 */               paramInt4 -= i2;
/* 877 */               System.arraycopy(arrayOfT2, k + 1, arrayOfT1, m + 1, i2);
/* 878 */               if (paramInt4 <= 1)
/*     */                 break; 
/*     */             } 
/* 881 */             arrayOfT1[m--] = arrayOfT1[j--];
/* 882 */             if (--paramInt2 == 0)
/*     */               break; 
/* 884 */             n--;
/* 885 */             if ((((i1 >= 7) ? 1 : 0) | ((i2 >= 7) ? 1 : 0)) == 0)
/* 886 */             { if (n < 0)
/* 887 */               { n = 0;
/* 888 */                 n += 2; continue; }  break label79; }  }  break; } 
/*     */       }  break;
/* 890 */     }  this.minGallop = (n < 1) ? 1 : n;
/*     */     
/* 892 */     if (paramInt4 == 1)
/* 893 */     { assert paramInt2 > 0;
/* 894 */       m -= paramInt2;
/* 895 */       j -= paramInt2;
/* 896 */       System.arraycopy(arrayOfT1, j + 1, arrayOfT1, m + 1, paramInt2);
/* 897 */       arrayOfT1[m] = arrayOfT2[k]; }
/* 898 */     else { if (paramInt4 == 0) {
/* 899 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 902 */       assert paramInt2 == 0;
/* 903 */       assert paramInt4 > 0;
/* 904 */       System.arraycopy(arrayOfT2, i, arrayOfT1, m - paramInt4 - 1, paramInt4); }
/*     */   
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
/*     */   private T[] ensureCapacity(int paramInt) {
/* 917 */     if (this.tmpLen < paramInt) {
/*     */       
/* 919 */       int i = paramInt;
/* 920 */       i |= i >> 1;
/* 921 */       i |= i >> 2;
/* 922 */       i |= i >> 4;
/* 923 */       i |= i >> 8;
/* 924 */       i |= i >> 16;
/* 925 */       i++;
/*     */       
/* 927 */       if (i < 0) {
/* 928 */         i = paramInt;
/*     */       } else {
/* 930 */         i = Math.min(i, this.a.length >>> 1);
/*     */       } 
/*     */ 
/*     */       
/* 934 */       Object[] arrayOfObject = (Object[])Array.newInstance(this.a.getClass().getComponentType(), i);
/* 935 */       this.tmp = (T[])arrayOfObject;
/* 936 */       this.tmpLen = i;
/* 937 */       this.tmpBase = 0;
/*     */     } 
/* 939 */     return this.tmp;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/TimSort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */