/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
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
/*     */ final class Histogram
/*     */ {
/*     */   protected final int[][] matrix;
/*     */   protected final int totalWeight;
/*     */   protected final int[] values;
/*     */   protected final int[] counts;
/*     */   private static final long LOW32 = 4294967295L;
/*     */   
/*     */   public Histogram(int[] paramArrayOfint) {
/*  55 */     long[] arrayOfLong = computeHistogram2Col(maybeSort(paramArrayOfint));
/*  56 */     int[][] arrayOfInt = makeTable(arrayOfLong);
/*  57 */     this.values = arrayOfInt[0];
/*  58 */     this.counts = arrayOfInt[1];
/*  59 */     this.matrix = makeMatrix(arrayOfLong);
/*  60 */     this.totalWeight = paramArrayOfint.length;
/*  61 */     assert assertWellFormed(paramArrayOfint);
/*     */   }
/*     */   
/*     */   public Histogram(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  65 */     this(sortedSlice(paramArrayOfint, paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Histogram(int[][] paramArrayOfint) {
/*  72 */     paramArrayOfint = normalizeMatrix(paramArrayOfint);
/*  73 */     this.matrix = paramArrayOfint;
/*  74 */     int i = 0;
/*  75 */     int j = 0;
/*  76 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/*  77 */       int k = (paramArrayOfint[b1]).length - 1;
/*  78 */       i += k;
/*  79 */       j += paramArrayOfint[b1][0] * k;
/*     */     } 
/*  81 */     this.totalWeight = j;
/*  82 */     long[] arrayOfLong = new long[i];
/*  83 */     byte b2 = 0;
/*  84 */     for (byte b3 = 0; b3 < paramArrayOfint.length; b3++) {
/*  85 */       for (byte b = 1; b < (paramArrayOfint[b3]).length; b++)
/*     */       {
/*  87 */         arrayOfLong[b2++] = paramArrayOfint[b3][b] << 32L | 0xFFFFFFFFL & paramArrayOfint[b3][0];
/*     */       }
/*     */     } 
/*     */     
/*  91 */     assert b2 == arrayOfLong.length;
/*  92 */     Arrays.sort(arrayOfLong);
/*  93 */     int[][] arrayOfInt = makeTable(arrayOfLong);
/*  94 */     this.values = arrayOfInt[1];
/*  95 */     this.counts = arrayOfInt[0];
/*  96 */     assert assertWellFormed(null);
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
/*     */   public int[][] getMatrix() {
/* 119 */     return this.matrix;
/*     */   }
/*     */   public int getRowCount() {
/* 122 */     return this.matrix.length;
/*     */   }
/*     */   public int getRowFrequency(int paramInt) {
/* 125 */     return this.matrix[paramInt][0];
/*     */   }
/*     */   public int getRowLength(int paramInt) {
/* 128 */     return (this.matrix[paramInt]).length - 1;
/*     */   }
/*     */   public int getRowValue(int paramInt1, int paramInt2) {
/* 131 */     return this.matrix[paramInt1][paramInt2 + 1];
/*     */   }
/*     */   
/*     */   public int getRowWeight(int paramInt) {
/* 135 */     return getRowFrequency(paramInt) * getRowLength(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTotalWeight() {
/* 140 */     return this.totalWeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTotalLength() {
/* 145 */     return this.values.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getAllValues() {
/* 152 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getAllFrequencies() {
/* 160 */     return this.counts;
/*     */   }
/*     */   
/* 163 */   private static double log2 = Math.log(2.0D);
/*     */ 
/*     */   
/*     */   public int getFrequency(int paramInt) {
/* 167 */     int i = Arrays.binarySearch(this.values, paramInt);
/* 168 */     if (i < 0) return 0; 
/* 169 */     assert this.values[i] == paramInt;
/* 170 */     return this.counts[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public double getBitLength(int paramInt) {
/* 175 */     double d = getFrequency(paramInt) / getTotalWeight();
/* 176 */     return -Math.log(d) / log2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getRowBitLength(int paramInt) {
/* 181 */     double d = getRowFrequency(paramInt) / getTotalWeight();
/* 182 */     return -Math.log(d) / log2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   private final BitMetric bitMetric = new BitMetric() {
/*     */       public double getBitLength(int param1Int) {
/* 191 */         return Histogram.this.getBitLength(param1Int);
/*     */       }
/*     */     };
/*     */   public BitMetric getBitMetric() {
/* 195 */     return this.bitMetric;
/*     */   }
/*     */   public static interface BitMetric {
/*     */     double getBitLength(int param1Int); }
/*     */   
/*     */   public double getBitLength() {
/* 201 */     double d = 0.0D;
/* 202 */     for (byte b = 0; b < this.matrix.length; b++) {
/* 203 */       d += getRowBitLength(b) * getRowWeight(b);
/*     */     }
/* 205 */     assert 0.1D > Math.abs(d - getBitLength(this.bitMetric));
/* 206 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBitLength(BitMetric paramBitMetric) {
/* 212 */     double d = 0.0D;
/* 213 */     for (byte b = 0; b < this.matrix.length; b++) {
/* 214 */       for (byte b1 = 1; b1 < (this.matrix[b]).length; b1++) {
/* 215 */         d += this.matrix[b][0] * paramBitMetric.getBitLength(this.matrix[b][b1]);
/*     */       }
/*     */     } 
/* 218 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   private static double round(double paramDouble1, double paramDouble2) {
/* 223 */     return Math.round(paramDouble1 * paramDouble2) / paramDouble2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[][] normalizeMatrix(int[][] paramArrayOfint) {
/* 231 */     long[] arrayOfLong = new long[paramArrayOfint.length];
/* 232 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/* 233 */       if ((paramArrayOfint[b1]).length > 1) {
/* 234 */         int j = paramArrayOfint[b1][0];
/* 235 */         if (j > 0)
/* 236 */           arrayOfLong[b1] = j << 32L | b1; 
/*     */       } 
/* 238 */     }  Arrays.sort(arrayOfLong);
/* 239 */     int[][] arrayOfInt = new int[paramArrayOfint.length][];
/* 240 */     int i = -1;
/* 241 */     byte b2 = 0;
/* 242 */     byte b3 = 0;
/* 243 */     for (byte b4 = 0;; b4++) {
/*     */       int[] arrayOfInt1;
/* 245 */       if (b4 < paramArrayOfint.length) {
/* 246 */         long l = arrayOfLong[arrayOfLong.length - b4 - 1];
/* 247 */         if (l == 0L)
/* 248 */           continue;  arrayOfInt1 = paramArrayOfint[(int)l];
/* 249 */         assert l >>> 32L == arrayOfInt1[0];
/*     */       } else {
/* 251 */         arrayOfInt1 = new int[] { -1 };
/*     */       } 
/* 253 */       if (arrayOfInt1[0] != i && b3 > b2) {
/*     */         
/* 255 */         int j = 0;
/* 256 */         for (byte b5 = b2; b5 < b3; b5++) {
/* 257 */           int[] arrayOfInt3 = arrayOfInt[b5];
/* 258 */           assert arrayOfInt3[0] == i;
/* 259 */           j += arrayOfInt3.length - 1;
/*     */         } 
/* 261 */         int[] arrayOfInt2 = new int[1 + j];
/* 262 */         arrayOfInt2[0] = i;
/* 263 */         int k = 1; byte b6;
/* 264 */         for (b6 = b2; b6 < b3; b6++) {
/* 265 */           int[] arrayOfInt3 = arrayOfInt[b6];
/* 266 */           assert arrayOfInt3[0] == i;
/* 267 */           System.arraycopy(arrayOfInt3, 1, arrayOfInt2, k, arrayOfInt3.length - 1);
/* 268 */           k += arrayOfInt3.length - 1;
/*     */         } 
/* 270 */         if (!isSorted(arrayOfInt2, 1, true)) {
/* 271 */           Arrays.sort(arrayOfInt2, 1, arrayOfInt2.length);
/* 272 */           b6 = 2;
/*     */           
/* 274 */           for (byte b = 2; b < arrayOfInt2.length; b++) {
/* 275 */             if (arrayOfInt2[b] != arrayOfInt2[b - 1])
/* 276 */               arrayOfInt2[b6++] = arrayOfInt2[b]; 
/*     */           } 
/* 278 */           if (b6 < arrayOfInt2.length) {
/*     */             
/* 280 */             int[] arrayOfInt3 = new int[b6];
/* 281 */             System.arraycopy(arrayOfInt2, 0, arrayOfInt3, 0, b6);
/* 282 */             arrayOfInt2 = arrayOfInt3;
/*     */           } 
/*     */         } 
/* 285 */         arrayOfInt[b2++] = arrayOfInt2;
/* 286 */         b3 = b2;
/*     */       } 
/* 288 */       if (b4 == paramArrayOfint.length)
/*     */         break; 
/* 290 */       i = arrayOfInt1[0];
/* 291 */       arrayOfInt[b3++] = arrayOfInt1; continue;
/*     */     } 
/* 293 */     assert b2 == b3;
/*     */     
/* 295 */     paramArrayOfint = arrayOfInt;
/* 296 */     if (b2 < paramArrayOfint.length) {
/* 297 */       arrayOfInt = new int[b2][];
/* 298 */       System.arraycopy(paramArrayOfint, 0, arrayOfInt, 0, b2);
/* 299 */       paramArrayOfint = arrayOfInt;
/*     */     } 
/* 301 */     return paramArrayOfint;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getRowTitles(String paramString) {
/* 306 */     int i = getTotalLength();
/* 307 */     int j = getTotalWeight();
/* 308 */     String[] arrayOfString = new String[this.matrix.length];
/* 309 */     int k = 0;
/* 310 */     int m = 0;
/* 311 */     for (byte b = 0; b < this.matrix.length; b++) {
/* 312 */       int n = getRowFrequency(b);
/* 313 */       int i1 = getRowLength(b);
/* 314 */       int i2 = getRowWeight(b);
/* 315 */       k += i2;
/* 316 */       m += i1;
/* 317 */       long l1 = (k * 100L + (j / 2)) / j;
/* 318 */       long l2 = (m * 100L + (i / 2)) / i;
/* 319 */       double d = getRowBitLength(b);
/* 320 */       assert 0.1D > Math.abs(d - getBitLength(this.matrix[b][1]));
/* 321 */       arrayOfString[b] = paramString + "[" + b + "] len=" + 
/* 322 */         round(d, 10.0D) + " (" + n + "*[" + i1 + "]) (" + k + ":" + l1 + "%) [" + m + ":" + l2 + "%]";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 327 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(PrintStream paramPrintStream) {
/* 334 */     print("hist", paramPrintStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(String paramString, PrintStream paramPrintStream) {
/* 341 */     print(paramString, getRowTitles(paramString), paramPrintStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(String paramString, String[] paramArrayOfString, PrintStream paramPrintStream) {
/* 348 */     int i = getTotalLength();
/* 349 */     int j = getTotalWeight();
/* 350 */     double d1 = getBitLength();
/* 351 */     double d2 = d1 / j;
/* 352 */     double d3 = j / i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     String str = paramString + " len=" + round(d1, 10.0D) + " avgLen=" + round(d2, 10.0D) + " weight(" + j + ") unique[" + i + "] avgWeight(" + round(d3, 100.0D) + ")";
/* 359 */     if (paramArrayOfString == null) {
/* 360 */       paramPrintStream.println(str);
/*     */     } else {
/* 362 */       paramPrintStream.println(str + " {");
/* 363 */       StringBuffer stringBuffer = new StringBuffer();
/* 364 */       for (byte b = 0; b < this.matrix.length; b++) {
/* 365 */         stringBuffer.setLength(0);
/* 366 */         stringBuffer.append("  ").append(paramArrayOfString[b]).append(" {");
/* 367 */         for (byte b1 = 1; b1 < (this.matrix[b]).length; b1++) {
/* 368 */           stringBuffer.append(" ").append(this.matrix[b][b1]);
/*     */         }
/* 370 */         stringBuffer.append(" }");
/* 371 */         paramPrintStream.println(stringBuffer);
/*     */       } 
/* 373 */       paramPrintStream.println("}");
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
/*     */   private static int[][] makeMatrix(long[] paramArrayOflong) {
/* 391 */     Arrays.sort(paramArrayOflong);
/* 392 */     int[] arrayOfInt = new int[paramArrayOflong.length];
/* 393 */     for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
/* 394 */       arrayOfInt[b1] = (int)(paramArrayOflong[b1] >>> 32L);
/*     */     }
/* 396 */     long[] arrayOfLong = computeHistogram2Col(arrayOfInt);
/* 397 */     int[][] arrayOfInt1 = new int[arrayOfLong.length][];
/* 398 */     byte b2 = 0;
/* 399 */     byte b3 = 0;
/*     */     
/* 401 */     for (int i = arrayOfInt1.length; --i >= 0; ) {
/* 402 */       long l = arrayOfLong[b3++];
/* 403 */       int j = (int)l;
/* 404 */       int k = (int)(l >>> 32L);
/* 405 */       int[] arrayOfInt2 = new int[1 + k];
/* 406 */       arrayOfInt2[0] = j;
/* 407 */       for (byte b = 0; b < k; b++) {
/* 408 */         long l1 = paramArrayOflong[b2++];
/* 409 */         assert l1 >>> 32L == j;
/* 410 */         arrayOfInt2[1 + b] = (int)l1;
/*     */       } 
/* 412 */       arrayOfInt1[i] = arrayOfInt2;
/*     */     } 
/* 414 */     assert b2 == paramArrayOflong.length;
/* 415 */     return arrayOfInt1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[][] makeTable(long[] paramArrayOflong) {
/* 420 */     int[][] arrayOfInt = new int[2][paramArrayOflong.length];
/*     */ 
/*     */     
/* 423 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 424 */       arrayOfInt[0][b] = (int)paramArrayOflong[b];
/* 425 */       arrayOfInt[1][b] = (int)(paramArrayOflong[b] >>> 32L);
/*     */     } 
/* 427 */     return arrayOfInt;
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
/*     */   private static long[] computeHistogram2Col(int[] paramArrayOfint) {
/* 447 */     switch (paramArrayOfint.length) {
/*     */       case 0:
/* 449 */         return new long[0];
/*     */       case 1:
/* 451 */         return new long[] { 0x100000000L | 0xFFFFFFFFL & paramArrayOfint[0] };
/*     */     } 
/* 453 */     long[] arrayOfLong = null;
/* 454 */     boolean bool = true; while (true) {
/* 455 */       byte b = -1;
/* 456 */       int i = paramArrayOfint[0] ^ 0xFFFFFFFF;
/* 457 */       byte b1 = 0;
/* 458 */       for (byte b2 = 0; b2 <= paramArrayOfint.length; b2++) {
/*     */         int j;
/* 460 */         if (b2 < paramArrayOfint.length) {
/* 461 */           j = paramArrayOfint[b2];
/*     */         } else {
/* 463 */           j = i ^ 0xFFFFFFFF;
/* 464 */         }  if (j == i) {
/* 465 */           b1++;
/*     */         } else {
/*     */           
/* 468 */           if (!bool && b1 != 0)
/*     */           {
/* 470 */             arrayOfLong[b] = b1 << 32L | 0xFFFFFFFFL & i;
/*     */           }
/*     */           
/* 473 */           i = j;
/* 474 */           b1 = 1;
/* 475 */           b++;
/*     */         } 
/*     */       } 
/* 478 */       if (bool) {
/*     */         
/* 480 */         arrayOfLong = new long[b];
/*     */         bool = false;
/*     */       } 
/*     */       break;
/*     */     } 
/* 485 */     return arrayOfLong;
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
/*     */   private static int[][] regroupHistogram(int[][] paramArrayOfint, int[] paramArrayOfint1) {
/* 497 */     long l1 = 0L;
/* 498 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/* 499 */       l1 += ((paramArrayOfint[b1]).length - 1);
/*     */     }
/* 501 */     long l2 = 0L; int i;
/* 502 */     for (i = 0; i < paramArrayOfint1.length; i++) {
/* 503 */       l2 += paramArrayOfint1[i];
/*     */     }
/* 505 */     if (l2 > l1) {
/* 506 */       i = paramArrayOfint1.length;
/* 507 */       long l = l1;
/* 508 */       for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 509 */         if (l < paramArrayOfint1[b]) {
/* 510 */           int[] arrayOfInt1 = new int[b + 1];
/* 511 */           System.arraycopy(paramArrayOfint1, 0, arrayOfInt1, 0, b + 1);
/* 512 */           paramArrayOfint1 = arrayOfInt1;
/* 513 */           paramArrayOfint1[b] = (int)l;
/* 514 */           l = 0L;
/*     */           break;
/*     */         } 
/* 517 */         l -= paramArrayOfint1[b];
/*     */       } 
/*     */     } else {
/* 520 */       long l = l1 - l2;
/* 521 */       int[] arrayOfInt1 = new int[paramArrayOfint1.length + 1];
/* 522 */       System.arraycopy(paramArrayOfint1, 0, arrayOfInt1, 0, paramArrayOfint1.length);
/* 523 */       arrayOfInt1[paramArrayOfint1.length] = (int)l;
/* 524 */       paramArrayOfint1 = arrayOfInt1;
/*     */     } 
/* 526 */     int[][] arrayOfInt = new int[paramArrayOfint1.length][];
/*     */     
/* 528 */     byte b2 = 0;
/* 529 */     int j = 1;
/* 530 */     int k = (paramArrayOfint[b2]).length;
/* 531 */     for (byte b3 = 0; b3 < paramArrayOfint1.length; b3++) {
/* 532 */       int m = paramArrayOfint1[b3];
/* 533 */       int[] arrayOfInt1 = new int[1 + m];
/* 534 */       long l = 0L;
/* 535 */       arrayOfInt[b3] = arrayOfInt1;
/* 536 */       int n = 1;
/* 537 */       while (n < arrayOfInt1.length) {
/* 538 */         int i1 = arrayOfInt1.length - n;
/* 539 */         while (j == k) {
/* 540 */           j = 1;
/* 541 */           k = (paramArrayOfint[++b2]).length;
/*     */         } 
/* 543 */         if (i1 > k - j) i1 = k - j; 
/* 544 */         l += paramArrayOfint[b2][0] * i1;
/* 545 */         System.arraycopy(paramArrayOfint[b2], k - i1, arrayOfInt1, n, i1);
/* 546 */         k -= i1;
/* 547 */         n += i1;
/*     */       } 
/* 549 */       Arrays.sort(arrayOfInt1, 1, arrayOfInt1.length);
/*     */       
/* 551 */       arrayOfInt1[0] = (int)((l + (m / 2)) / m);
/*     */     } 
/* 553 */     assert j == k;
/* 554 */     assert b2 == paramArrayOfint.length - 1;
/* 555 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Histogram makeByteHistogram(InputStream paramInputStream) throws IOException {
/* 560 */     byte[] arrayOfByte = new byte[4096];
/* 561 */     int[] arrayOfInt = new int[256]; int i;
/* 562 */     while ((i = paramInputStream.read(arrayOfByte)) > 0) {
/* 563 */       for (byte b1 = 0; b1 < i; b1++) {
/* 564 */         arrayOfInt[arrayOfByte[b1] & 0xFF] = arrayOfInt[arrayOfByte[b1] & 0xFF] + 1;
/*     */       }
/*     */     } 
/*     */     
/* 568 */     int[][] arrayOfInt1 = new int[256][2];
/* 569 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 570 */       arrayOfInt1[b][0] = arrayOfInt[b];
/* 571 */       arrayOfInt1[b][1] = b;
/*     */     } 
/* 573 */     return new Histogram(arrayOfInt1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] sortedSlice(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 579 */     if (paramInt1 == 0 && paramInt2 == paramArrayOfint.length && 
/* 580 */       isSorted(paramArrayOfint, 0, false)) {
/* 581 */       return paramArrayOfint;
/*     */     }
/* 583 */     int[] arrayOfInt = new int[paramInt2 - paramInt1];
/* 584 */     System.arraycopy(paramArrayOfint, paramInt1, arrayOfInt, 0, arrayOfInt.length);
/* 585 */     Arrays.sort(arrayOfInt);
/* 586 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSorted(int[] paramArrayOfint, int paramInt, boolean paramBoolean) {
/* 593 */     for (int i = paramInt + 1; i < paramArrayOfint.length; i++) {
/* 594 */       if (paramBoolean ? (paramArrayOfint[i - 1] >= paramArrayOfint[i]) : (paramArrayOfint[i - 1] > paramArrayOfint[i]))
/*     */       {
/* 596 */         return false;
/*     */       }
/*     */     } 
/* 599 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] maybeSort(int[] paramArrayOfint) {
/* 605 */     if (!isSorted(paramArrayOfint, 0, false)) {
/* 606 */       paramArrayOfint = (int[])paramArrayOfint.clone();
/* 607 */       Arrays.sort(paramArrayOfint);
/*     */     } 
/* 609 */     return paramArrayOfint;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean assertWellFormed(int[] paramArrayOfint) {
/* 660 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Histogram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */