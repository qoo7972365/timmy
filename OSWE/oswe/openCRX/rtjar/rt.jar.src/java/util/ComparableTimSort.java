/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ComparableTimSort
/*     */ {
/*     */   private static final int MIN_MERGE = 32;
/*     */   private final Object[] a;
/*     */   private static final int MIN_GALLOP = 7;
/*  78 */   private int minGallop = 7;
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
/*     */   private Object[] tmp;
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
/* 108 */   private int stackSize = 0;
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
/*     */   private ComparableTimSort(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, int paramInt1, int paramInt2) {
/* 121 */     this.a = paramArrayOfObject1;
/*     */ 
/*     */     
/* 124 */     int i = paramArrayOfObject1.length;
/* 125 */     byte b1 = (i < 512) ? (i >>> 1) : 256;
/*     */     
/* 127 */     if (paramArrayOfObject2 == null || paramInt2 < b1 || paramInt1 + b1 > paramArrayOfObject2.length) {
/* 128 */       this.tmp = new Object[b1];
/* 129 */       this.tmpBase = 0;
/* 130 */       this.tmpLen = b1;
/*     */     } else {
/*     */       
/* 133 */       this.tmp = paramArrayOfObject2;
/* 134 */       this.tmpBase = paramInt1;
/* 135 */       this.tmpLen = paramInt2;
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
/* 152 */     byte b2 = (i < 120) ? 5 : ((i < 1542) ? 10 : ((i < 119151) ? 24 : 49));
/*     */ 
/*     */     
/* 155 */     this.runBase = new int[b2];
/* 156 */     this.runLen = new int[b2];
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
/*     */   static void sort(Object[] paramArrayOfObject1, int paramInt1, int paramInt2, Object[] paramArrayOfObject2, int paramInt3, int paramInt4) {
/* 180 */     assert paramArrayOfObject1 != null && paramInt1 >= 0 && paramInt1 <= paramInt2 && paramInt2 <= paramArrayOfObject1.length;
/*     */     
/* 182 */     int i = paramInt2 - paramInt1;
/* 183 */     if (i < 2) {
/*     */       return;
/*     */     }
/*     */     
/* 187 */     if (i < 32) {
/* 188 */       int k = countRunAndMakeAscending(paramArrayOfObject1, paramInt1, paramInt2);
/* 189 */       binarySort(paramArrayOfObject1, paramInt1, paramInt2, paramInt1 + k);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 198 */     ComparableTimSort comparableTimSort = new ComparableTimSort(paramArrayOfObject1, paramArrayOfObject2, paramInt3, paramInt4);
/* 199 */     int j = minRunLength(i);
/*     */     
/*     */     do {
/* 202 */       int k = countRunAndMakeAscending(paramArrayOfObject1, paramInt1, paramInt2);
/*     */ 
/*     */       
/* 205 */       if (k < j) {
/* 206 */         int m = (i <= j) ? i : j;
/* 207 */         binarySort(paramArrayOfObject1, paramInt1, paramInt1 + m, paramInt1 + k);
/* 208 */         k = m;
/*     */       } 
/*     */ 
/*     */       
/* 212 */       comparableTimSort.pushRun(paramInt1, k);
/* 213 */       comparableTimSort.mergeCollapse();
/*     */ 
/*     */       
/* 216 */       paramInt1 += k;
/* 217 */       i -= k;
/* 218 */     } while (i != 0);
/*     */ 
/*     */     
/* 221 */     assert paramInt1 == paramInt2;
/* 222 */     comparableTimSort.mergeForceCollapse();
/* 223 */     assert comparableTimSort.stackSize == 1;
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
/*     */   private static void binarySort(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3) {
/* 245 */     assert paramInt1 <= paramInt3 && paramInt3 <= paramInt2;
/* 246 */     if (paramInt3 == paramInt1)
/* 247 */       paramInt3++; 
/* 248 */     for (; paramInt3 < paramInt2; paramInt3++) {
/* 249 */       Comparable<Object> comparable = (Comparable)paramArrayOfObject[paramInt3];
/*     */ 
/*     */       
/* 252 */       int i = paramInt1;
/* 253 */       int j = paramInt3;
/* 254 */       assert i <= j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 260 */       while (i < j) {
/* 261 */         int m = i + j >>> 1;
/* 262 */         if (comparable.compareTo(paramArrayOfObject[m]) < 0) {
/* 263 */           j = m; continue;
/*     */         } 
/* 265 */         i = m + 1;
/*     */       } 
/* 267 */       assert i == j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 276 */       int k = paramInt3 - i;
/*     */       
/* 278 */       switch (k) { case 2:
/* 279 */           paramArrayOfObject[i + 2] = paramArrayOfObject[i + 1];
/* 280 */         case 1: paramArrayOfObject[i + 1] = paramArrayOfObject[i]; break;
/*     */         default:
/* 282 */           System.arraycopy(paramArrayOfObject, i, paramArrayOfObject, i + 1, k); break; }
/*     */       
/* 284 */       paramArrayOfObject[i] = comparable;
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
/*     */   private static int countRunAndMakeAscending(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/* 314 */     assert paramInt1 < paramInt2;
/* 315 */     int i = paramInt1 + 1;
/* 316 */     if (i == paramInt2) {
/* 317 */       return 1;
/*     */     }
/*     */     
/* 320 */     if (((Comparable<Object>)paramArrayOfObject[i++]).compareTo(paramArrayOfObject[paramInt1]) < 0) {
/* 321 */       while (i < paramInt2 && ((Comparable<Object>)paramArrayOfObject[i]).compareTo(paramArrayOfObject[i - 1]) < 0)
/* 322 */         i++; 
/* 323 */       reverseRange(paramArrayOfObject, paramInt1, i);
/*     */     } else {
/* 325 */       while (i < paramInt2 && ((Comparable<Object>)paramArrayOfObject[i]).compareTo(paramArrayOfObject[i - 1]) >= 0) {
/* 326 */         i++;
/*     */       }
/*     */     } 
/* 329 */     return i - paramInt1;
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
/* 340 */     paramInt2--;
/* 341 */     while (paramInt1 < paramInt2) {
/* 342 */       Object object = paramArrayOfObject[paramInt1];
/* 343 */       paramArrayOfObject[paramInt1++] = paramArrayOfObject[paramInt2];
/* 344 */       paramArrayOfObject[paramInt2--] = object;
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
/* 366 */     assert paramInt >= 0;
/* 367 */     int i = 0;
/* 368 */     while (paramInt >= 32) {
/* 369 */       i |= paramInt & 0x1;
/* 370 */       paramInt >>= 1;
/*     */     } 
/* 372 */     return paramInt + i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pushRun(int paramInt1, int paramInt2) {
/* 382 */     this.runBase[this.stackSize] = paramInt1;
/* 383 */     this.runLen[this.stackSize] = paramInt2;
/* 384 */     this.stackSize++;
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
/* 399 */     while (this.stackSize > 1) {
/* 400 */       int i = this.stackSize - 2;
/* 401 */       if (i > 0 && this.runLen[i - 1] <= this.runLen[i] + this.runLen[i + 1]) {
/* 402 */         if (this.runLen[i - 1] < this.runLen[i + 1])
/* 403 */           i--; 
/* 404 */         mergeAt(i); continue;
/* 405 */       }  if (this.runLen[i] <= this.runLen[i + 1]) {
/* 406 */         mergeAt(i);
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
/* 418 */     while (this.stackSize > 1) {
/* 419 */       int i = this.stackSize - 2;
/* 420 */       if (i > 0 && this.runLen[i - 1] < this.runLen[i + 1])
/* 421 */         i--; 
/* 422 */       mergeAt(i);
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
/*     */   private void mergeAt(int paramInt) {
/* 435 */     assert this.stackSize >= 2;
/* 436 */     assert paramInt >= 0;
/* 437 */     assert paramInt == this.stackSize - 2 || paramInt == this.stackSize - 3;
/*     */     
/* 439 */     int i = this.runBase[paramInt];
/* 440 */     int j = this.runLen[paramInt];
/* 441 */     int k = this.runBase[paramInt + 1];
/* 442 */     int m = this.runLen[paramInt + 1];
/* 443 */     assert j > 0 && m > 0;
/* 444 */     assert i + j == k;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 451 */     this.runLen[paramInt] = j + m;
/* 452 */     if (paramInt == this.stackSize - 3) {
/* 453 */       this.runBase[paramInt + 1] = this.runBase[paramInt + 2];
/* 454 */       this.runLen[paramInt + 1] = this.runLen[paramInt + 2];
/*     */     } 
/* 456 */     this.stackSize--;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     int n = gallopRight((Comparable<Object>)this.a[k], this.a, i, j, 0);
/* 463 */     assert n >= 0;
/* 464 */     i += n;
/* 465 */     j -= n;
/* 466 */     if (j == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 473 */     m = gallopLeft((Comparable<Object>)this.a[i + j - 1], this.a, k, m, m - 1);
/*     */     
/* 475 */     assert m >= 0;
/* 476 */     if (m == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 480 */     if (j <= m) {
/* 481 */       mergeLo(i, j, k, m);
/*     */     } else {
/* 483 */       mergeHi(i, j, k, m);
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
/*     */   private static int gallopLeft(Comparable<Object> paramComparable, Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3) {
/* 505 */     assert paramInt2 > 0 && paramInt3 >= 0 && paramInt3 < paramInt2;
/*     */     
/* 507 */     int i = 0;
/* 508 */     int j = 1;
/* 509 */     if (paramComparable.compareTo(paramArrayOfObject[paramInt1 + paramInt3]) > 0) {
/*     */       
/* 511 */       int k = paramInt2 - paramInt3;
/* 512 */       while (j < k && paramComparable.compareTo(paramArrayOfObject[paramInt1 + paramInt3 + j]) > 0) {
/* 513 */         i = j;
/* 514 */         j = (j << 1) + 1;
/* 515 */         if (j <= 0)
/* 516 */           j = k; 
/*     */       } 
/* 518 */       if (j > k) {
/* 519 */         j = k;
/*     */       }
/*     */       
/* 522 */       i += paramInt3;
/* 523 */       j += paramInt3;
/*     */     } else {
/*     */       
/* 526 */       int k = paramInt3 + 1;
/* 527 */       while (j < k && paramComparable.compareTo(paramArrayOfObject[paramInt1 + paramInt3 - j]) <= 0) {
/* 528 */         i = j;
/* 529 */         j = (j << 1) + 1;
/* 530 */         if (j <= 0)
/* 531 */           j = k; 
/*     */       } 
/* 533 */       if (j > k) {
/* 534 */         j = k;
/*     */       }
/*     */       
/* 537 */       int m = i;
/* 538 */       i = paramInt3 - j;
/* 539 */       j = paramInt3 - m;
/*     */     } 
/* 541 */     assert -1 <= i && i < j && j <= paramInt2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 548 */     i++;
/* 549 */     while (i < j) {
/* 550 */       int k = i + (j - i >>> 1);
/*     */       
/* 552 */       if (paramComparable.compareTo(paramArrayOfObject[paramInt1 + k]) > 0) {
/* 553 */         i = k + 1; continue;
/*     */       } 
/* 555 */       j = k;
/*     */     } 
/* 557 */     assert i == j;
/* 558 */     return j;
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
/*     */   private static int gallopRight(Comparable<Object> paramComparable, Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3) {
/* 575 */     assert paramInt2 > 0 && paramInt3 >= 0 && paramInt3 < paramInt2;
/*     */     
/* 577 */     int i = 1;
/* 578 */     int j = 0;
/* 579 */     if (paramComparable.compareTo(paramArrayOfObject[paramInt1 + paramInt3]) < 0) {
/*     */       
/* 581 */       int k = paramInt3 + 1;
/* 582 */       while (i < k && paramComparable.compareTo(paramArrayOfObject[paramInt1 + paramInt3 - i]) < 0) {
/* 583 */         j = i;
/* 584 */         i = (i << 1) + 1;
/* 585 */         if (i <= 0)
/* 586 */           i = k; 
/*     */       } 
/* 588 */       if (i > k) {
/* 589 */         i = k;
/*     */       }
/*     */       
/* 592 */       int m = j;
/* 593 */       j = paramInt3 - i;
/* 594 */       i = paramInt3 - m;
/*     */     } else {
/*     */       
/* 597 */       int k = paramInt2 - paramInt3;
/* 598 */       while (i < k && paramComparable.compareTo(paramArrayOfObject[paramInt1 + paramInt3 + i]) >= 0) {
/* 599 */         j = i;
/* 600 */         i = (i << 1) + 1;
/* 601 */         if (i <= 0)
/* 602 */           i = k; 
/*     */       } 
/* 604 */       if (i > k) {
/* 605 */         i = k;
/*     */       }
/*     */       
/* 608 */       j += paramInt3;
/* 609 */       i += paramInt3;
/*     */     } 
/* 611 */     assert -1 <= j && j < i && i <= paramInt2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 618 */     j++;
/* 619 */     while (j < i) {
/* 620 */       int k = j + (i - j >>> 1);
/*     */       
/* 622 */       if (paramComparable.compareTo(paramArrayOfObject[paramInt1 + k]) < 0) {
/* 623 */         i = k; continue;
/*     */       } 
/* 625 */       j = k + 1;
/*     */     } 
/* 627 */     assert j == i;
/* 628 */     return i;
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
/*     */   private void mergeLo(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 649 */     assert paramInt2 > 0 && paramInt4 > 0 && paramInt1 + paramInt2 == paramInt3;
/*     */ 
/*     */     
/* 652 */     Object[] arrayOfObject1 = this.a;
/* 653 */     Object[] arrayOfObject2 = ensureCapacity(paramInt2);
/*     */     
/* 655 */     int i = this.tmpBase;
/* 656 */     int j = paramInt3;
/* 657 */     int k = paramInt1;
/* 658 */     System.arraycopy(arrayOfObject1, paramInt1, arrayOfObject2, i, paramInt2);
/*     */ 
/*     */     
/* 661 */     arrayOfObject1[k++] = arrayOfObject1[j++];
/* 662 */     if (--paramInt4 == 0) {
/* 663 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, paramInt2);
/*     */       return;
/*     */     } 
/* 666 */     if (paramInt2 == 1) {
/* 667 */       System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, paramInt4);
/* 668 */       arrayOfObject1[k + paramInt4] = arrayOfObject2[i];
/*     */       
/*     */       return;
/*     */     } 
/* 672 */     int m = this.minGallop;
/*     */     
/*     */     while (true) {
/* 675 */       int n = 0;
/* 676 */       int i1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 683 */         assert paramInt2 > 1 && paramInt4 > 0;
/* 684 */         if (((Comparable<Object>)arrayOfObject1[j]).compareTo(arrayOfObject2[i]) < 0) {
/* 685 */           arrayOfObject1[k++] = arrayOfObject1[j++];
/* 686 */           i1++;
/* 687 */           n = 0;
/* 688 */           if (--paramInt4 == 0)
/*     */             break; 
/*     */         } else {
/* 691 */           arrayOfObject1[k++] = arrayOfObject2[i++];
/* 692 */           n++;
/* 693 */           i1 = 0;
/* 694 */           if (--paramInt2 == 1)
/*     */             break; 
/*     */         } 
/* 697 */         if ((n | i1) >= m)
/*     */         
/*     */         { 
/*     */           
/*     */           label79: while (true)
/*     */           
/*     */           { 
/*     */             
/* 705 */             assert paramInt2 > 1 && paramInt4 > 0;
/* 706 */             n = gallopRight((Comparable<Object>)arrayOfObject1[j], arrayOfObject2, i, paramInt2, 0);
/* 707 */             if (n != 0) {
/* 708 */               System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, n);
/* 709 */               k += n;
/* 710 */               i += n;
/* 711 */               paramInt2 -= n;
/* 712 */               if (paramInt2 <= 1)
/*     */                 break; 
/*     */             } 
/* 715 */             arrayOfObject1[k++] = arrayOfObject1[j++];
/* 716 */             if (--paramInt4 == 0) {
/*     */               break;
/*     */             }
/* 719 */             i1 = gallopLeft((Comparable<Object>)arrayOfObject2[i], arrayOfObject1, j, paramInt4, 0);
/* 720 */             if (i1 != 0) {
/* 721 */               System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, i1);
/* 722 */               k += i1;
/* 723 */               j += i1;
/* 724 */               paramInt4 -= i1;
/* 725 */               if (paramInt4 == 0)
/*     */                 break; 
/*     */             } 
/* 728 */             arrayOfObject1[k++] = arrayOfObject2[i++];
/* 729 */             if (--paramInt2 == 1)
/*     */               break; 
/* 731 */             m--;
/* 732 */             if ((((n >= 7) ? 1 : 0) | ((i1 >= 7) ? 1 : 0)) == 0)
/* 733 */             { if (m < 0)
/* 734 */               { m = 0;
/* 735 */                 m += 2; continue; }  break label79; }  }  break; } 
/*     */       }  break;
/* 737 */     }  this.minGallop = (m < 1) ? 1 : m;
/*     */     
/* 739 */     if (paramInt2 == 1)
/* 740 */     { assert paramInt4 > 0;
/* 741 */       System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, paramInt4);
/* 742 */       arrayOfObject1[k + paramInt4] = arrayOfObject2[i]; }
/* 743 */     else { if (paramInt2 == 0) {
/* 744 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 747 */       assert paramInt4 == 0;
/* 748 */       assert paramInt2 > 1;
/* 749 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, paramInt2); }
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
/*     */   
/*     */   private void mergeHi(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 766 */     assert paramInt2 > 0 && paramInt4 > 0 && paramInt1 + paramInt2 == paramInt3;
/*     */ 
/*     */     
/* 769 */     Object[] arrayOfObject1 = this.a;
/* 770 */     Object[] arrayOfObject2 = ensureCapacity(paramInt4);
/* 771 */     int i = this.tmpBase;
/* 772 */     System.arraycopy(arrayOfObject1, paramInt3, arrayOfObject2, i, paramInt4);
/*     */     
/* 774 */     int j = paramInt1 + paramInt2 - 1;
/* 775 */     int k = i + paramInt4 - 1;
/* 776 */     int m = paramInt3 + paramInt4 - 1;
/*     */ 
/*     */     
/* 779 */     arrayOfObject1[m--] = arrayOfObject1[j--];
/* 780 */     if (--paramInt2 == 0) {
/* 781 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, m - paramInt4 - 1, paramInt4);
/*     */       return;
/*     */     } 
/* 784 */     if (paramInt4 == 1) {
/* 785 */       m -= paramInt2;
/* 786 */       j -= paramInt2;
/* 787 */       System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, paramInt2);
/* 788 */       arrayOfObject1[m] = arrayOfObject2[k];
/*     */       
/*     */       return;
/*     */     } 
/* 792 */     int n = this.minGallop;
/*     */     
/*     */     while (true) {
/* 795 */       int i1 = 0;
/* 796 */       int i2 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 803 */         assert paramInt2 > 0 && paramInt4 > 1;
/* 804 */         if (((Comparable<Object>)arrayOfObject2[k]).compareTo(arrayOfObject1[j]) < 0) {
/* 805 */           arrayOfObject1[m--] = arrayOfObject1[j--];
/* 806 */           i1++;
/* 807 */           i2 = 0;
/* 808 */           if (--paramInt2 == 0)
/*     */             break; 
/*     */         } else {
/* 811 */           arrayOfObject1[m--] = arrayOfObject2[k--];
/* 812 */           i2++;
/* 813 */           i1 = 0;
/* 814 */           if (--paramInt4 == 1)
/*     */             break; 
/*     */         } 
/* 817 */         if ((i1 | i2) >= n)
/*     */         
/*     */         { 
/*     */           
/*     */           label79: while (true)
/*     */           
/*     */           { 
/*     */             
/* 825 */             assert paramInt2 > 0 && paramInt4 > 1;
/* 826 */             i1 = paramInt2 - gallopRight((Comparable<Object>)arrayOfObject2[k], arrayOfObject1, paramInt1, paramInt2, paramInt2 - 1);
/* 827 */             if (i1 != 0) {
/* 828 */               m -= i1;
/* 829 */               j -= i1;
/* 830 */               paramInt2 -= i1;
/* 831 */               System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, i1);
/* 832 */               if (paramInt2 == 0)
/*     */                 break; 
/*     */             } 
/* 835 */             arrayOfObject1[m--] = arrayOfObject2[k--];
/* 836 */             if (--paramInt4 == 1) {
/*     */               break;
/*     */             }
/* 839 */             i2 = paramInt4 - gallopLeft((Comparable<Object>)arrayOfObject1[j], arrayOfObject2, i, paramInt4, paramInt4 - 1);
/* 840 */             if (i2 != 0) {
/* 841 */               m -= i2;
/* 842 */               k -= i2;
/* 843 */               paramInt4 -= i2;
/* 844 */               System.arraycopy(arrayOfObject2, k + 1, arrayOfObject1, m + 1, i2);
/* 845 */               if (paramInt4 <= 1)
/*     */                 break; 
/*     */             } 
/* 848 */             arrayOfObject1[m--] = arrayOfObject1[j--];
/* 849 */             if (--paramInt2 == 0)
/*     */               break; 
/* 851 */             n--;
/* 852 */             if ((((i1 >= 7) ? 1 : 0) | ((i2 >= 7) ? 1 : 0)) == 0)
/* 853 */             { if (n < 0)
/* 854 */               { n = 0;
/* 855 */                 n += 2; continue; }  break label79; }  }  break; } 
/*     */       }  break;
/* 857 */     }  this.minGallop = (n < 1) ? 1 : n;
/*     */     
/* 859 */     if (paramInt4 == 1)
/* 860 */     { assert paramInt2 > 0;
/* 861 */       m -= paramInt2;
/* 862 */       j -= paramInt2;
/* 863 */       System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, paramInt2);
/* 864 */       arrayOfObject1[m] = arrayOfObject2[k]; }
/* 865 */     else { if (paramInt4 == 0) {
/* 866 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 869 */       assert paramInt2 == 0;
/* 870 */       assert paramInt4 > 0;
/* 871 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, m - paramInt4 - 1, paramInt4); }
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
/*     */   private Object[] ensureCapacity(int paramInt) {
/* 884 */     if (this.tmpLen < paramInt) {
/*     */       
/* 886 */       int i = paramInt;
/* 887 */       i |= i >> 1;
/* 888 */       i |= i >> 2;
/* 889 */       i |= i >> 4;
/* 890 */       i |= i >> 8;
/* 891 */       i |= i >> 16;
/* 892 */       i++;
/*     */       
/* 894 */       if (i < 0) {
/* 895 */         i = paramInt;
/*     */       } else {
/* 897 */         i = Math.min(i, this.a.length >>> 1);
/*     */       } 
/*     */       
/* 900 */       Object[] arrayOfObject = new Object[i];
/* 901 */       this.tmp = arrayOfObject;
/* 902 */       this.tmpLen = i;
/* 903 */       this.tmpBase = 0;
/*     */     } 
/* 905 */     return this.tmp;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ComparableTimSort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */