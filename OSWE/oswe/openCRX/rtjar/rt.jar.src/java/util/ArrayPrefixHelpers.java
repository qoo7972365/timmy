/*     */ package java.util;
/*     */ 
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ArrayPrefixHelpers
/*     */ {
/*     */   static final int CUMULATE = 1;
/*     */   static final int SUMMED = 2;
/*     */   static final int FINISHED = 4;
/*     */   static final int MIN_PARTITION = 16;
/*     */   
/*     */   static final class CumulateTask<T>
/*     */     extends CountedCompleter<Void>
/*     */   {
/*     */     final T[] array;
/*     */     final BinaryOperator<T> function;
/*     */     CumulateTask<T> left;
/*     */     CumulateTask<T> right;
/*     */     T in;
/*     */     T out;
/*     */     final int lo;
/*     */     final int hi;
/*     */     final int origin;
/*     */     final int fence;
/*     */     final int threshold;
/*     */     
/*     */     public CumulateTask(CumulateTask<T> param1CumulateTask, BinaryOperator<T> param1BinaryOperator, T[] param1ArrayOfT, int param1Int1, int param1Int2) {
/* 111 */       super(param1CumulateTask);
/* 112 */       this.function = param1BinaryOperator; this.array = param1ArrayOfT;
/* 113 */       this.lo = this.origin = param1Int1; this.hi = this.fence = param1Int2;
/*     */       int i;
/* 115 */       this
/* 116 */         .threshold = ((i = (param1Int2 - param1Int1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16) ? 16 : i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CumulateTask(CumulateTask<T> param1CumulateTask, BinaryOperator<T> param1BinaryOperator, T[] param1ArrayOfT, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 124 */       super(param1CumulateTask);
/* 125 */       this.function = param1BinaryOperator; this.array = param1ArrayOfT;
/* 126 */       this.origin = param1Int1; this.fence = param1Int2;
/* 127 */       this.threshold = param1Int3;
/* 128 */       this.lo = param1Int4; this.hi = param1Int5;
/*     */     }
/*     */ 
/*     */     
/*     */     public final void compute() {
/*     */       BinaryOperator<T> binaryOperator;
/*     */       T[] arrayOfT;
/* 135 */       if ((binaryOperator = this.function) == null || (arrayOfT = this.array) == null)
/* 136 */         throw new NullPointerException(); 
/* 137 */       int i = this.threshold, j = this.origin, k = this.fence;
/* 138 */       CumulateTask<T> cumulateTask = this; int m, n;
/* 139 */       while ((m = cumulateTask.lo) >= 0 && (n = cumulateTask.hi) <= arrayOfT.length) {
/* 140 */         if (n - m > i) {
/* 141 */           CumulateTask<T> cumulateTask3, cumulateTask1 = cumulateTask.left, cumulateTask2 = cumulateTask.right;
/* 142 */           if (cumulateTask1 == null) {
/* 143 */             int i2 = m + n >>> 1;
/* 144 */             cumulateTask3 = cumulateTask2 = cumulateTask.right = new CumulateTask(cumulateTask, binaryOperator, arrayOfT, j, k, i, i2, n);
/*     */             
/* 146 */             cumulateTask = cumulateTask1 = cumulateTask.left = new CumulateTask(cumulateTask, binaryOperator, arrayOfT, j, k, i, m, i2);
/*     */           }
/*     */           else {
/*     */             
/* 150 */             T t = cumulateTask.in;
/* 151 */             cumulateTask1.in = t;
/* 152 */             cumulateTask3 = cumulateTask = null;
/* 153 */             if (cumulateTask2 != null) {
/* 154 */               T t1 = cumulateTask1.out;
/* 155 */               cumulateTask2
/* 156 */                 .in = (m == j) ? t1 : binaryOperator.apply(t, t1);
/*     */               int i3;
/* 158 */               while (((i3 = cumulateTask2.getPendingCount()) & 0x1) == 0) {
/*     */                 
/* 160 */                 if (cumulateTask2.compareAndSetPendingCount(i3, i3 | 0x1)) {
/* 161 */                   cumulateTask = cumulateTask2;
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             int i2;
/* 167 */             while (((i2 = cumulateTask1.getPendingCount()) & 0x1) == 0) {
/*     */               
/* 169 */               if (cumulateTask1.compareAndSetPendingCount(i2, i2 | 0x1)) {
/* 170 */                 if (cumulateTask != null)
/* 171 */                   cumulateTask3 = cumulateTask; 
/* 172 */                 cumulateTask = cumulateTask1;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 176 */             if (cumulateTask == null)
/*     */               break; 
/*     */           } 
/* 179 */           if (cumulateTask3 != null) {
/* 180 */             cumulateTask3.fork();
/*     */           }
/*     */           continue;
/*     */         } 
/*     */         int i1;
/* 185 */         while (((i1 = cumulateTask.getPendingCount()) & 0x4) == 0) {
/*     */           
/* 187 */           byte b = ((i1 & 0x1) != 0) ? 4 : ((m > j) ? 2 : 6);
/*     */           
/* 189 */           if (cumulateTask.compareAndSetPendingCount(i1, i1 | b)) {
/*     */             T t;
/*     */ 
/*     */ 
/*     */             
/* 194 */             if (b != 2) {
/*     */               int i2;
/* 196 */               if (m == j) {
/* 197 */                 t = arrayOfT[j];
/* 198 */                 i2 = j + 1;
/*     */               } else {
/*     */                 
/* 201 */                 t = cumulateTask.in;
/* 202 */                 i2 = m;
/*     */               } 
/* 204 */               for (int i3 = i2; i3 < n; i3++) {
/* 205 */                 arrayOfT[i3] = t = binaryOperator.apply(t, arrayOfT[i3]);
/*     */               }
/* 207 */             } else if (n < k) {
/* 208 */               t = arrayOfT[m];
/* 209 */               for (int i2 = m + 1; i2 < n; i2++) {
/* 210 */                 t = binaryOperator.apply(t, arrayOfT[i2]);
/*     */               }
/*     */             } else {
/* 213 */               t = cumulateTask.in;
/* 214 */             }  cumulateTask.out = t; while (true) {
/*     */               CumulateTask<T> cumulateTask1;
/* 216 */               if ((cumulateTask1 = (CumulateTask)cumulateTask.getCompleter()) == null) {
/* 217 */                 if ((b & 0x4) != 0)
/* 218 */                   cumulateTask.quietlyComplete(); 
/*     */                 break;
/*     */               } 
/* 221 */               int i2 = cumulateTask1.getPendingCount();
/* 222 */               if ((i2 & b & 0x4) != 0) {
/* 223 */                 cumulateTask = cumulateTask1; continue;
/* 224 */               }  if ((i2 & b & 0x2) != 0) {
/*     */                 CumulateTask<T> cumulateTask2, cumulateTask3;
/* 226 */                 if ((cumulateTask2 = cumulateTask1.left) != null && (cumulateTask3 = cumulateTask1.right) != null) {
/*     */                   
/* 228 */                   T t1 = cumulateTask2.out;
/* 229 */                   cumulateTask1
/* 230 */                     .out = (cumulateTask3.hi == k) ? t1 : binaryOperator.apply(t1, cumulateTask3.out);
/*     */                 } 
/* 232 */                 boolean bool = ((i2 & 0x1) == 0 && cumulateTask1.lo == j) ? true : false;
/*     */                 int i3;
/* 234 */                 if ((i3 = i2 | b | bool) == i2 || cumulateTask1
/* 235 */                   .compareAndSetPendingCount(i2, i3)) {
/* 236 */                   b = 2;
/* 237 */                   cumulateTask = cumulateTask1;
/* 238 */                   if (bool)
/* 239 */                     cumulateTask1.fork(); 
/*     */                 }  continue;
/*     */               } 
/* 242 */               if (cumulateTask1.compareAndSetPendingCount(i2, i2 | b))
/*     */                 break; 
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static final class LongCumulateTask
/*     */     extends CountedCompleter<Void> {
/*     */     final long[] array;
/*     */     final LongBinaryOperator function;
/*     */     LongCumulateTask left;
/*     */     LongCumulateTask right;
/*     */     long in;
/*     */     
/*     */     public LongCumulateTask(LongCumulateTask param1LongCumulateTask, LongBinaryOperator param1LongBinaryOperator, long[] param1ArrayOflong, int param1Int1, int param1Int2) {
/* 261 */       super(param1LongCumulateTask);
/* 262 */       this.function = param1LongBinaryOperator; this.array = param1ArrayOflong;
/* 263 */       this.lo = this.origin = param1Int1; this.hi = this.fence = param1Int2;
/*     */       int i;
/* 265 */       this
/* 266 */         .threshold = ((i = (param1Int2 - param1Int1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16) ? 16 : i;
/*     */     }
/*     */     
/*     */     long out;
/*     */     final int lo;
/*     */     final int hi;
/*     */     
/*     */     LongCumulateTask(LongCumulateTask param1LongCumulateTask, LongBinaryOperator param1LongBinaryOperator, long[] param1ArrayOflong, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 274 */       super(param1LongCumulateTask);
/* 275 */       this.function = param1LongBinaryOperator; this.array = param1ArrayOflong;
/* 276 */       this.origin = param1Int1; this.fence = param1Int2;
/* 277 */       this.threshold = param1Int3;
/* 278 */       this.lo = param1Int4; this.hi = param1Int5;
/*     */     }
/*     */     final int origin; final int fence; final int threshold;
/*     */     public final void compute() {
/*     */       LongBinaryOperator longBinaryOperator;
/*     */       long[] arrayOfLong;
/* 284 */       if ((longBinaryOperator = this.function) == null || (arrayOfLong = this.array) == null)
/* 285 */         throw new NullPointerException(); 
/* 286 */       int i = this.threshold, j = this.origin, k = this.fence;
/* 287 */       LongCumulateTask longCumulateTask = this; int m, n;
/* 288 */       while ((m = longCumulateTask.lo) >= 0 && (n = longCumulateTask.hi) <= arrayOfLong.length) {
/* 289 */         if (n - m > i) {
/* 290 */           LongCumulateTask longCumulateTask3, longCumulateTask1 = longCumulateTask.left, longCumulateTask2 = longCumulateTask.right;
/* 291 */           if (longCumulateTask1 == null) {
/* 292 */             int i2 = m + n >>> 1;
/* 293 */             longCumulateTask3 = longCumulateTask2 = longCumulateTask.right = new LongCumulateTask(longCumulateTask, longBinaryOperator, arrayOfLong, j, k, i, i2, n);
/*     */             
/* 295 */             longCumulateTask = longCumulateTask1 = longCumulateTask.left = new LongCumulateTask(longCumulateTask, longBinaryOperator, arrayOfLong, j, k, i, m, i2);
/*     */           }
/*     */           else {
/*     */             
/* 299 */             long l = longCumulateTask.in;
/* 300 */             longCumulateTask1.in = l;
/* 301 */             longCumulateTask3 = longCumulateTask = null;
/* 302 */             if (longCumulateTask2 != null) {
/* 303 */               long l1 = longCumulateTask1.out;
/* 304 */               longCumulateTask2
/* 305 */                 .in = (m == j) ? l1 : longBinaryOperator.applyAsLong(l, l1);
/*     */               int i3;
/* 307 */               while (((i3 = longCumulateTask2.getPendingCount()) & 0x1) == 0) {
/*     */                 
/* 309 */                 if (longCumulateTask2.compareAndSetPendingCount(i3, i3 | 0x1)) {
/* 310 */                   longCumulateTask = longCumulateTask2;
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             int i2;
/* 316 */             while (((i2 = longCumulateTask1.getPendingCount()) & 0x1) == 0) {
/*     */               
/* 318 */               if (longCumulateTask1.compareAndSetPendingCount(i2, i2 | 0x1)) {
/* 319 */                 if (longCumulateTask != null)
/* 320 */                   longCumulateTask3 = longCumulateTask; 
/* 321 */                 longCumulateTask = longCumulateTask1;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 325 */             if (longCumulateTask == null)
/*     */               break; 
/*     */           } 
/* 328 */           if (longCumulateTask3 != null) {
/* 329 */             longCumulateTask3.fork();
/*     */           }
/*     */           continue;
/*     */         } 
/*     */         int i1;
/* 334 */         while (((i1 = longCumulateTask.getPendingCount()) & 0x4) == 0) {
/*     */           
/* 336 */           byte b = ((i1 & 0x1) != 0) ? 4 : ((m > j) ? 2 : 6);
/*     */           
/* 338 */           if (longCumulateTask.compareAndSetPendingCount(i1, i1 | b)) {
/*     */             long l;
/*     */ 
/*     */ 
/*     */             
/* 343 */             if (b != 2) {
/*     */               int i2;
/* 345 */               if (m == j) {
/* 346 */                 l = arrayOfLong[j];
/* 347 */                 i2 = j + 1;
/*     */               } else {
/*     */                 
/* 350 */                 l = longCumulateTask.in;
/* 351 */                 i2 = m;
/*     */               } 
/* 353 */               for (int i3 = i2; i3 < n; i3++) {
/* 354 */                 arrayOfLong[i3] = l = longBinaryOperator.applyAsLong(l, arrayOfLong[i3]);
/*     */               }
/* 356 */             } else if (n < k) {
/* 357 */               l = arrayOfLong[m];
/* 358 */               for (int i2 = m + 1; i2 < n; i2++) {
/* 359 */                 l = longBinaryOperator.applyAsLong(l, arrayOfLong[i2]);
/*     */               }
/*     */             } else {
/* 362 */               l = longCumulateTask.in;
/* 363 */             }  longCumulateTask.out = l; while (true) {
/*     */               LongCumulateTask longCumulateTask1;
/* 365 */               if ((longCumulateTask1 = (LongCumulateTask)longCumulateTask.getCompleter()) == null) {
/* 366 */                 if ((b & 0x4) != 0)
/* 367 */                   longCumulateTask.quietlyComplete(); 
/*     */                 break;
/*     */               } 
/* 370 */               int i2 = longCumulateTask1.getPendingCount();
/* 371 */               if ((i2 & b & 0x4) != 0) {
/* 372 */                 longCumulateTask = longCumulateTask1; continue;
/* 373 */               }  if ((i2 & b & 0x2) != 0) {
/*     */                 LongCumulateTask longCumulateTask2, longCumulateTask3;
/* 375 */                 if ((longCumulateTask2 = longCumulateTask1.left) != null && (longCumulateTask3 = longCumulateTask1.right) != null) {
/*     */                   
/* 377 */                   long l1 = longCumulateTask2.out;
/* 378 */                   longCumulateTask1
/* 379 */                     .out = (longCumulateTask3.hi == k) ? l1 : longBinaryOperator.applyAsLong(l1, longCumulateTask3.out);
/*     */                 } 
/* 381 */                 boolean bool = ((i2 & 0x1) == 0 && longCumulateTask1.lo == j) ? true : false;
/*     */                 int i3;
/* 383 */                 if ((i3 = i2 | b | bool) == i2 || longCumulateTask1
/* 384 */                   .compareAndSetPendingCount(i2, i3)) {
/* 385 */                   b = 2;
/* 386 */                   longCumulateTask = longCumulateTask1;
/* 387 */                   if (bool)
/* 388 */                     longCumulateTask1.fork(); 
/*     */                 }  continue;
/*     */               } 
/* 391 */               if (longCumulateTask1.compareAndSetPendingCount(i2, i2 | b))
/*     */                 break; 
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static final class DoubleCumulateTask
/*     */     extends CountedCompleter<Void> {
/*     */     final double[] array;
/*     */     final DoubleBinaryOperator function;
/*     */     DoubleCumulateTask left;
/*     */     DoubleCumulateTask right;
/*     */     double in;
/*     */     
/*     */     public DoubleCumulateTask(DoubleCumulateTask param1DoubleCumulateTask, DoubleBinaryOperator param1DoubleBinaryOperator, double[] param1ArrayOfdouble, int param1Int1, int param1Int2) {
/* 410 */       super(param1DoubleCumulateTask);
/* 411 */       this.function = param1DoubleBinaryOperator; this.array = param1ArrayOfdouble;
/* 412 */       this.lo = this.origin = param1Int1; this.hi = this.fence = param1Int2;
/*     */       int i;
/* 414 */       this
/* 415 */         .threshold = ((i = (param1Int2 - param1Int1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16) ? 16 : i;
/*     */     }
/*     */     double out; final int lo; final int hi;
/*     */     final int origin;
/*     */     final int fence;
/*     */     final int threshold;
/*     */     
/*     */     DoubleCumulateTask(DoubleCumulateTask param1DoubleCumulateTask, DoubleBinaryOperator param1DoubleBinaryOperator, double[] param1ArrayOfdouble, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 423 */       super(param1DoubleCumulateTask);
/* 424 */       this.function = param1DoubleBinaryOperator; this.array = param1ArrayOfdouble;
/* 425 */       this.origin = param1Int1; this.fence = param1Int2;
/* 426 */       this.threshold = param1Int3;
/* 427 */       this.lo = param1Int4; this.hi = param1Int5;
/*     */     }
/*     */     
/*     */     public final void compute() {
/*     */       DoubleBinaryOperator doubleBinaryOperator;
/*     */       double[] arrayOfDouble;
/* 433 */       if ((doubleBinaryOperator = this.function) == null || (arrayOfDouble = this.array) == null)
/* 434 */         throw new NullPointerException(); 
/* 435 */       int i = this.threshold, j = this.origin, k = this.fence;
/* 436 */       DoubleCumulateTask doubleCumulateTask = this; int m, n;
/* 437 */       while ((m = doubleCumulateTask.lo) >= 0 && (n = doubleCumulateTask.hi) <= arrayOfDouble.length) {
/* 438 */         if (n - m > i) {
/* 439 */           DoubleCumulateTask doubleCumulateTask3, doubleCumulateTask1 = doubleCumulateTask.left, doubleCumulateTask2 = doubleCumulateTask.right;
/* 440 */           if (doubleCumulateTask1 == null) {
/* 441 */             int i2 = m + n >>> 1;
/* 442 */             doubleCumulateTask3 = doubleCumulateTask2 = doubleCumulateTask.right = new DoubleCumulateTask(doubleCumulateTask, doubleBinaryOperator, arrayOfDouble, j, k, i, i2, n);
/*     */             
/* 444 */             doubleCumulateTask = doubleCumulateTask1 = doubleCumulateTask.left = new DoubleCumulateTask(doubleCumulateTask, doubleBinaryOperator, arrayOfDouble, j, k, i, m, i2);
/*     */           }
/*     */           else {
/*     */             
/* 448 */             double d = doubleCumulateTask.in;
/* 449 */             doubleCumulateTask1.in = d;
/* 450 */             doubleCumulateTask3 = doubleCumulateTask = null;
/* 451 */             if (doubleCumulateTask2 != null) {
/* 452 */               double d1 = doubleCumulateTask1.out;
/* 453 */               doubleCumulateTask2
/* 454 */                 .in = (m == j) ? d1 : doubleBinaryOperator.applyAsDouble(d, d1);
/*     */               int i3;
/* 456 */               while (((i3 = doubleCumulateTask2.getPendingCount()) & 0x1) == 0) {
/*     */                 
/* 458 */                 if (doubleCumulateTask2.compareAndSetPendingCount(i3, i3 | 0x1)) {
/* 459 */                   doubleCumulateTask = doubleCumulateTask2;
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             int i2;
/* 465 */             while (((i2 = doubleCumulateTask1.getPendingCount()) & 0x1) == 0) {
/*     */               
/* 467 */               if (doubleCumulateTask1.compareAndSetPendingCount(i2, i2 | 0x1)) {
/* 468 */                 if (doubleCumulateTask != null)
/* 469 */                   doubleCumulateTask3 = doubleCumulateTask; 
/* 470 */                 doubleCumulateTask = doubleCumulateTask1;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 474 */             if (doubleCumulateTask == null)
/*     */               break; 
/*     */           } 
/* 477 */           if (doubleCumulateTask3 != null) {
/* 478 */             doubleCumulateTask3.fork();
/*     */           }
/*     */           continue;
/*     */         } 
/*     */         int i1;
/* 483 */         while (((i1 = doubleCumulateTask.getPendingCount()) & 0x4) == 0) {
/*     */           
/* 485 */           byte b = ((i1 & 0x1) != 0) ? 4 : ((m > j) ? 2 : 6);
/*     */           
/* 487 */           if (doubleCumulateTask.compareAndSetPendingCount(i1, i1 | b)) {
/*     */             double d;
/*     */ 
/*     */ 
/*     */             
/* 492 */             if (b != 2) {
/*     */               int i2;
/* 494 */               if (m == j) {
/* 495 */                 d = arrayOfDouble[j];
/* 496 */                 i2 = j + 1;
/*     */               } else {
/*     */                 
/* 499 */                 d = doubleCumulateTask.in;
/* 500 */                 i2 = m;
/*     */               } 
/* 502 */               for (int i3 = i2; i3 < n; i3++) {
/* 503 */                 arrayOfDouble[i3] = d = doubleBinaryOperator.applyAsDouble(d, arrayOfDouble[i3]);
/*     */               }
/* 505 */             } else if (n < k) {
/* 506 */               d = arrayOfDouble[m];
/* 507 */               for (int i2 = m + 1; i2 < n; i2++) {
/* 508 */                 d = doubleBinaryOperator.applyAsDouble(d, arrayOfDouble[i2]);
/*     */               }
/*     */             } else {
/* 511 */               d = doubleCumulateTask.in;
/* 512 */             }  doubleCumulateTask.out = d; while (true) {
/*     */               DoubleCumulateTask doubleCumulateTask1;
/* 514 */               if ((doubleCumulateTask1 = (DoubleCumulateTask)doubleCumulateTask.getCompleter()) == null) {
/* 515 */                 if ((b & 0x4) != 0)
/* 516 */                   doubleCumulateTask.quietlyComplete(); 
/*     */                 break;
/*     */               } 
/* 519 */               int i2 = doubleCumulateTask1.getPendingCount();
/* 520 */               if ((i2 & b & 0x4) != 0) {
/* 521 */                 doubleCumulateTask = doubleCumulateTask1; continue;
/* 522 */               }  if ((i2 & b & 0x2) != 0) {
/*     */                 DoubleCumulateTask doubleCumulateTask2, doubleCumulateTask3;
/* 524 */                 if ((doubleCumulateTask2 = doubleCumulateTask1.left) != null && (doubleCumulateTask3 = doubleCumulateTask1.right) != null) {
/*     */                   
/* 526 */                   double d1 = doubleCumulateTask2.out;
/* 527 */                   doubleCumulateTask1
/* 528 */                     .out = (doubleCumulateTask3.hi == k) ? d1 : doubleBinaryOperator.applyAsDouble(d1, doubleCumulateTask3.out);
/*     */                 } 
/* 530 */                 boolean bool = ((i2 & 0x1) == 0 && doubleCumulateTask1.lo == j) ? true : false;
/*     */                 int i3;
/* 532 */                 if ((i3 = i2 | b | bool) == i2 || doubleCumulateTask1
/* 533 */                   .compareAndSetPendingCount(i2, i3)) {
/* 534 */                   b = 2;
/* 535 */                   doubleCumulateTask = doubleCumulateTask1;
/* 536 */                   if (bool)
/* 537 */                     doubleCumulateTask1.fork(); 
/*     */                 }  continue;
/*     */               } 
/* 540 */               if (doubleCumulateTask1.compareAndSetPendingCount(i2, i2 | b))
/*     */                 break; 
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static final class IntCumulateTask
/*     */     extends CountedCompleter<Void> {
/*     */     final int[] array;
/*     */     final IntBinaryOperator function;
/*     */     IntCumulateTask left;
/*     */     IntCumulateTask right;
/*     */     int in;
/*     */     
/*     */     public IntCumulateTask(IntCumulateTask param1IntCumulateTask, IntBinaryOperator param1IntBinaryOperator, int[] param1ArrayOfint, int param1Int1, int param1Int2) {
/* 559 */       super(param1IntCumulateTask);
/* 560 */       this.function = param1IntBinaryOperator; this.array = param1ArrayOfint;
/* 561 */       this.lo = this.origin = param1Int1; this.hi = this.fence = param1Int2;
/*     */       int i;
/* 563 */       this
/* 564 */         .threshold = ((i = (param1Int2 - param1Int1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16) ? 16 : i;
/*     */     }
/*     */     int out; final int lo; final int hi;
/*     */     final int origin;
/*     */     final int fence;
/*     */     final int threshold;
/*     */     
/*     */     IntCumulateTask(IntCumulateTask param1IntCumulateTask, IntBinaryOperator param1IntBinaryOperator, int[] param1ArrayOfint, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 572 */       super(param1IntCumulateTask);
/* 573 */       this.function = param1IntBinaryOperator; this.array = param1ArrayOfint;
/* 574 */       this.origin = param1Int1; this.fence = param1Int2;
/* 575 */       this.threshold = param1Int3;
/* 576 */       this.lo = param1Int4; this.hi = param1Int5;
/*     */     }
/*     */     
/*     */     public final void compute() {
/*     */       IntBinaryOperator intBinaryOperator;
/*     */       int[] arrayOfInt;
/* 582 */       if ((intBinaryOperator = this.function) == null || (arrayOfInt = this.array) == null)
/* 583 */         throw new NullPointerException(); 
/* 584 */       int i = this.threshold, j = this.origin, k = this.fence;
/* 585 */       IntCumulateTask intCumulateTask = this; int m, n;
/* 586 */       while ((m = intCumulateTask.lo) >= 0 && (n = intCumulateTask.hi) <= arrayOfInt.length) {
/* 587 */         if (n - m > i) {
/* 588 */           IntCumulateTask intCumulateTask3, intCumulateTask1 = intCumulateTask.left, intCumulateTask2 = intCumulateTask.right;
/* 589 */           if (intCumulateTask1 == null) {
/* 590 */             int i2 = m + n >>> 1;
/* 591 */             intCumulateTask3 = intCumulateTask2 = intCumulateTask.right = new IntCumulateTask(intCumulateTask, intBinaryOperator, arrayOfInt, j, k, i, i2, n);
/*     */             
/* 593 */             intCumulateTask = intCumulateTask1 = intCumulateTask.left = new IntCumulateTask(intCumulateTask, intBinaryOperator, arrayOfInt, j, k, i, m, i2);
/*     */           }
/*     */           else {
/*     */             
/* 597 */             int i2 = intCumulateTask.in;
/* 598 */             intCumulateTask1.in = i2;
/* 599 */             intCumulateTask3 = intCumulateTask = null;
/* 600 */             if (intCumulateTask2 != null) {
/* 601 */               int i4 = intCumulateTask1.out;
/* 602 */               intCumulateTask2
/* 603 */                 .in = (m == j) ? i4 : intBinaryOperator.applyAsInt(i2, i4);
/*     */               int i5;
/* 605 */               while (((i5 = intCumulateTask2.getPendingCount()) & 0x1) == 0) {
/*     */                 
/* 607 */                 if (intCumulateTask2.compareAndSetPendingCount(i5, i5 | 0x1)) {
/* 608 */                   intCumulateTask = intCumulateTask2;
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             int i3;
/* 614 */             while (((i3 = intCumulateTask1.getPendingCount()) & 0x1) == 0) {
/*     */               
/* 616 */               if (intCumulateTask1.compareAndSetPendingCount(i3, i3 | 0x1)) {
/* 617 */                 if (intCumulateTask != null)
/* 618 */                   intCumulateTask3 = intCumulateTask; 
/* 619 */                 intCumulateTask = intCumulateTask1;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 623 */             if (intCumulateTask == null)
/*     */               break; 
/*     */           } 
/* 626 */           if (intCumulateTask3 != null) {
/* 627 */             intCumulateTask3.fork();
/*     */           }
/*     */           continue;
/*     */         } 
/*     */         int i1;
/* 632 */         while (((i1 = intCumulateTask.getPendingCount()) & 0x4) == 0) {
/*     */           
/* 634 */           byte b = ((i1 & 0x1) != 0) ? 4 : ((m > j) ? 2 : 6);
/*     */           
/* 636 */           if (intCumulateTask.compareAndSetPendingCount(i1, i1 | b)) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 641 */             if (b != 2) {
/*     */               int i2;
/* 643 */               if (m == j) {
/* 644 */                 i1 = arrayOfInt[j];
/* 645 */                 i2 = j + 1;
/*     */               } else {
/*     */                 
/* 648 */                 i1 = intCumulateTask.in;
/* 649 */                 i2 = m;
/*     */               } 
/* 651 */               for (int i3 = i2; i3 < n; i3++) {
/* 652 */                 arrayOfInt[i3] = i1 = intBinaryOperator.applyAsInt(i1, arrayOfInt[i3]);
/*     */               }
/* 654 */             } else if (n < k) {
/* 655 */               i1 = arrayOfInt[m];
/* 656 */               for (int i2 = m + 1; i2 < n; i2++) {
/* 657 */                 i1 = intBinaryOperator.applyAsInt(i1, arrayOfInt[i2]);
/*     */               }
/*     */             } else {
/* 660 */               i1 = intCumulateTask.in;
/* 661 */             }  intCumulateTask.out = i1; while (true) {
/*     */               IntCumulateTask intCumulateTask1;
/* 663 */               if ((intCumulateTask1 = (IntCumulateTask)intCumulateTask.getCompleter()) == null) {
/* 664 */                 if ((b & 0x4) != 0)
/* 665 */                   intCumulateTask.quietlyComplete(); 
/*     */                 break;
/*     */               } 
/* 668 */               int i2 = intCumulateTask1.getPendingCount();
/* 669 */               if ((i2 & b & 0x4) != 0) {
/* 670 */                 intCumulateTask = intCumulateTask1; continue;
/* 671 */               }  if ((i2 & b & 0x2) != 0) {
/*     */                 IntCumulateTask intCumulateTask2, intCumulateTask3;
/* 673 */                 if ((intCumulateTask2 = intCumulateTask1.left) != null && (intCumulateTask3 = intCumulateTask1.right) != null) {
/*     */                   
/* 675 */                   int i4 = intCumulateTask2.out;
/* 676 */                   intCumulateTask1
/* 677 */                     .out = (intCumulateTask3.hi == k) ? i4 : intBinaryOperator.applyAsInt(i4, intCumulateTask3.out);
/*     */                 } 
/* 679 */                 boolean bool = ((i2 & 0x1) == 0 && intCumulateTask1.lo == j) ? true : false;
/*     */                 int i3;
/* 681 */                 if ((i3 = i2 | b | bool) == i2 || intCumulateTask1
/* 682 */                   .compareAndSetPendingCount(i2, i3)) {
/* 683 */                   b = 2;
/* 684 */                   intCumulateTask = intCumulateTask1;
/* 685 */                   if (bool)
/* 686 */                     intCumulateTask1.fork(); 
/*     */                 }  continue;
/*     */               } 
/* 689 */               if (intCumulateTask1.compareAndSetPendingCount(i2, i2 | b))
/*     */                 break; 
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ArrayPrefixHelpers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */