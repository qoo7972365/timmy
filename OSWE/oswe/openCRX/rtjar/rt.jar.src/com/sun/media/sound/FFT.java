/*     */ package com.sun.media.sound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FFT
/*     */ {
/*     */   private final double[] w;
/*     */   private final int fftFrameSize;
/*     */   private final int sign;
/*     */   private final int[] bitm_array;
/*     */   private final int fftFrameSize2;
/*     */   
/*     */   public FFT(int paramInt1, int paramInt2) {
/*  45 */     this.w = computeTwiddleFactors(paramInt1, paramInt2);
/*     */     
/*  47 */     this.fftFrameSize = paramInt1;
/*  48 */     this.sign = paramInt2;
/*  49 */     this.fftFrameSize2 = paramInt1 << 1;
/*     */ 
/*     */     
/*  52 */     this.bitm_array = new int[this.fftFrameSize2];
/*  53 */     for (byte b = 2; b < this.fftFrameSize2; b += 2) {
/*     */       int i;
/*     */       int j;
/*  56 */       for (j = 2, i = 0; j < this.fftFrameSize2; j <<= 1) {
/*  57 */         if ((b & j) != 0)
/*  58 */           i++; 
/*  59 */         i <<= 1;
/*     */       } 
/*  61 */       this.bitm_array[b] = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void transform(double[] paramArrayOfdouble) {
/*  67 */     bitreversal(paramArrayOfdouble);
/*  68 */     calc(this.fftFrameSize, paramArrayOfdouble, this.sign, this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double[] computeTwiddleFactors(int paramInt1, int paramInt2) {
/*  74 */     int i = (int)(Math.log(paramInt1) / Math.log(2.0D));
/*     */     
/*  76 */     double[] arrayOfDouble = new double[(paramInt1 - 1) * 4];
/*  77 */     byte b = 0;
/*     */     int j, k;
/*  79 */     for (j = 0, k = 2; j < i; j++) {
/*  80 */       int n = k;
/*  81 */       k <<= 1;
/*     */       
/*  83 */       double d1 = 1.0D;
/*  84 */       double d2 = 0.0D;
/*     */       
/*  86 */       double d3 = Math.PI / (n >> 1);
/*  87 */       double d4 = Math.cos(d3);
/*  88 */       double d5 = paramInt2 * Math.sin(d3);
/*     */       
/*  90 */       for (byte b1 = 0; b1 < n; b1 += 2) {
/*  91 */         arrayOfDouble[b++] = d1;
/*  92 */         arrayOfDouble[b++] = d2;
/*     */         
/*  94 */         double d = d1;
/*  95 */         d1 = d * d4 - d2 * d5;
/*  96 */         d2 = d * d5 + d2 * d4;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     b = 0;
/* 104 */     j = arrayOfDouble.length >> 1; int m;
/* 105 */     for (k = 0, m = 2; k < i - 1; k++) {
/* 106 */       int n = m;
/* 107 */       m *= 2;
/*     */       
/* 109 */       int i1 = b + n;
/* 110 */       for (byte b1 = 0; b1 < n; b1 += 2) {
/* 111 */         double d1 = arrayOfDouble[b++];
/* 112 */         double d2 = arrayOfDouble[b++];
/* 113 */         double d3 = arrayOfDouble[i1++];
/* 114 */         double d4 = arrayOfDouble[i1++];
/* 115 */         arrayOfDouble[j++] = d1 * d3 - d2 * d4;
/* 116 */         arrayOfDouble[j++] = d1 * d4 + d2 * d3;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 122 */     return arrayOfDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void calc(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2) {
/* 128 */     int i = paramInt1 << 1;
/*     */     
/* 130 */     byte b = 2;
/*     */     
/* 132 */     if (b >= i)
/*     */       return; 
/* 134 */     int j = b - 2;
/* 135 */     if (paramInt2 == -1) {
/* 136 */       calcF4F(paramInt1, paramArrayOfdouble1, j, b, paramArrayOfdouble2);
/*     */     } else {
/* 138 */       calcF4I(paramInt1, paramArrayOfdouble1, j, b, paramArrayOfdouble2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final void calcF2E(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, int paramInt3, double[] paramArrayOfdouble2) {
/* 144 */     int i = paramInt3;
/* 145 */     for (byte b = 0; b < i; b += 2) {
/* 146 */       double d1 = paramArrayOfdouble2[paramInt2++];
/* 147 */       double d2 = paramArrayOfdouble2[paramInt2++];
/* 148 */       int j = b + i;
/* 149 */       double d3 = paramArrayOfdouble1[j];
/* 150 */       double d4 = paramArrayOfdouble1[j + 1];
/* 151 */       double d5 = paramArrayOfdouble1[b];
/* 152 */       double d6 = paramArrayOfdouble1[b + 1];
/* 153 */       double d7 = d3 * d1 - d4 * d2;
/* 154 */       double d8 = d3 * d2 + d4 * d1;
/* 155 */       paramArrayOfdouble1[j] = d5 - d7;
/* 156 */       paramArrayOfdouble1[j + 1] = d6 - d8;
/* 157 */       paramArrayOfdouble1[b] = d5 + d7;
/* 158 */       paramArrayOfdouble1[b + 1] = d6 + d8;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void calcF4F(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, int paramInt3, double[] paramArrayOfdouble2) {
/* 168 */     int i = paramInt1 << 1;
/*     */ 
/*     */     
/* 171 */     int j = paramArrayOfdouble2.length >> 1;
/* 172 */     while (paramInt3 < i) {
/*     */       
/* 174 */       if (paramInt3 << 2 == i) {
/*     */ 
/*     */         
/* 177 */         calcF4FE(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */         return;
/*     */       } 
/* 180 */       int k = paramInt3;
/* 181 */       int m = paramInt3 << 1;
/* 182 */       if (m == i) {
/*     */         
/* 184 */         calcF2E(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */         return;
/*     */       } 
/* 187 */       paramInt3 <<= 2;
/* 188 */       int n = paramInt2 + k;
/* 189 */       int i1 = paramInt2 + j;
/*     */ 
/*     */       
/* 192 */       paramInt2 += 2;
/* 193 */       n += 2;
/* 194 */       i1 += 2;
/*     */       int i2;
/* 196 */       for (i2 = 0; i2 < i; i2 += paramInt3) {
/* 197 */         int i3 = i2 + k;
/*     */         
/* 199 */         double d1 = paramArrayOfdouble1[i3];
/* 200 */         double d2 = paramArrayOfdouble1[i3 + 1];
/* 201 */         double d3 = paramArrayOfdouble1[i2];
/* 202 */         double d4 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 204 */         i2 += m;
/* 205 */         i3 += m;
/* 206 */         double d5 = paramArrayOfdouble1[i3];
/* 207 */         double d6 = paramArrayOfdouble1[i3 + 1];
/* 208 */         double d7 = paramArrayOfdouble1[i2];
/* 209 */         double d8 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 211 */         double d9 = d1;
/* 212 */         double d10 = d2;
/*     */         
/* 214 */         d1 = d3 - d9;
/* 215 */         d2 = d4 - d10;
/* 216 */         d3 += d9;
/* 217 */         d4 += d10;
/*     */         
/* 219 */         double d11 = d7;
/* 220 */         double d12 = d8;
/* 221 */         double d13 = d5;
/* 222 */         double d14 = d6;
/*     */         
/* 224 */         d9 = d13 - d11;
/* 225 */         d10 = d14 - d12;
/*     */         
/* 227 */         d5 = d1 + d10;
/* 228 */         d6 = d2 - d9;
/* 229 */         d1 -= d10;
/* 230 */         d2 += d9;
/*     */         
/* 232 */         d9 = d11 + d13;
/* 233 */         d10 = d12 + d14;
/*     */         
/* 235 */         d7 = d3 - d9;
/* 236 */         d8 = d4 - d10;
/* 237 */         d3 += d9;
/* 238 */         d4 += d10;
/*     */         
/* 240 */         paramArrayOfdouble1[i3] = d5;
/* 241 */         paramArrayOfdouble1[i3 + 1] = d6;
/* 242 */         paramArrayOfdouble1[i2] = d7;
/* 243 */         paramArrayOfdouble1[i2 + 1] = d8;
/*     */         
/* 245 */         i2 -= m;
/* 246 */         i3 -= m;
/* 247 */         paramArrayOfdouble1[i3] = d1;
/* 248 */         paramArrayOfdouble1[i3 + 1] = d2;
/* 249 */         paramArrayOfdouble1[i2] = d3;
/* 250 */         paramArrayOfdouble1[i2 + 1] = d4;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 255 */       for (i2 = 2; i2 < k; i2 += 2) {
/* 256 */         double d1 = paramArrayOfdouble2[paramInt2++];
/* 257 */         double d2 = paramArrayOfdouble2[paramInt2++];
/* 258 */         double d3 = paramArrayOfdouble2[n++];
/* 259 */         double d4 = paramArrayOfdouble2[n++];
/* 260 */         double d5 = paramArrayOfdouble2[i1++];
/* 261 */         double d6 = paramArrayOfdouble2[i1++];
/*     */ 
/*     */         
/*     */         int i3;
/*     */         
/* 266 */         for (i3 = i2; i3 < i; i3 += paramInt3) {
/* 267 */           int i4 = i3 + k;
/*     */           
/* 269 */           double d7 = paramArrayOfdouble1[i4];
/* 270 */           double d8 = paramArrayOfdouble1[i4 + 1];
/* 271 */           double d9 = paramArrayOfdouble1[i3];
/* 272 */           double d10 = paramArrayOfdouble1[i3 + 1];
/*     */           
/* 274 */           i3 += m;
/* 275 */           i4 += m;
/* 276 */           double d11 = paramArrayOfdouble1[i4];
/* 277 */           double d12 = paramArrayOfdouble1[i4 + 1];
/* 278 */           double d13 = paramArrayOfdouble1[i3];
/* 279 */           double d14 = paramArrayOfdouble1[i3 + 1];
/*     */           
/* 281 */           double d15 = d7 * d1 - d8 * d2;
/* 282 */           double d16 = d7 * d2 + d8 * d1;
/*     */           
/* 284 */           d7 = d9 - d15;
/* 285 */           d8 = d10 - d16;
/* 286 */           d9 += d15;
/* 287 */           d10 += d16;
/*     */           
/* 289 */           double d17 = d13 * d3 - d14 * d4;
/* 290 */           double d18 = d13 * d4 + d14 * d3;
/* 291 */           double d19 = d11 * d5 - d12 * d6;
/* 292 */           double d20 = d11 * d6 + d12 * d5;
/*     */           
/* 294 */           d15 = d19 - d17;
/* 295 */           d16 = d20 - d18;
/*     */           
/* 297 */           d11 = d7 + d16;
/* 298 */           d12 = d8 - d15;
/* 299 */           d7 -= d16;
/* 300 */           d8 += d15;
/*     */           
/* 302 */           d15 = d17 + d19;
/* 303 */           d16 = d18 + d20;
/*     */           
/* 305 */           d13 = d9 - d15;
/* 306 */           d14 = d10 - d16;
/* 307 */           d9 += d15;
/* 308 */           d10 += d16;
/*     */           
/* 310 */           paramArrayOfdouble1[i4] = d11;
/* 311 */           paramArrayOfdouble1[i4 + 1] = d12;
/* 312 */           paramArrayOfdouble1[i3] = d13;
/* 313 */           paramArrayOfdouble1[i3 + 1] = d14;
/*     */           
/* 315 */           i3 -= m;
/* 316 */           i4 -= m;
/* 317 */           paramArrayOfdouble1[i4] = d7;
/* 318 */           paramArrayOfdouble1[i4 + 1] = d8;
/* 319 */           paramArrayOfdouble1[i3] = d9;
/* 320 */           paramArrayOfdouble1[i3 + 1] = d10;
/*     */         } 
/*     */       } 
/*     */       
/* 324 */       paramInt2 += k << 1;
/*     */     } 
/*     */ 
/*     */     
/* 328 */     calcF2E(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void calcF4I(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, int paramInt3, double[] paramArrayOfdouble2) {
/* 336 */     int i = paramInt1 << 1;
/*     */ 
/*     */     
/* 339 */     int j = paramArrayOfdouble2.length >> 1;
/* 340 */     while (paramInt3 < i) {
/*     */       
/* 342 */       if (paramInt3 << 2 == i) {
/*     */ 
/*     */         
/* 345 */         calcF4IE(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */         return;
/*     */       } 
/* 348 */       int k = paramInt3;
/* 349 */       int m = paramInt3 << 1;
/* 350 */       if (m == i) {
/*     */         
/* 352 */         calcF2E(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */         return;
/*     */       } 
/* 355 */       paramInt3 <<= 2;
/* 356 */       int n = paramInt2 + k;
/* 357 */       int i1 = paramInt2 + j;
/*     */       
/* 359 */       paramInt2 += 2;
/* 360 */       n += 2;
/* 361 */       i1 += 2;
/*     */       int i2;
/* 363 */       for (i2 = 0; i2 < i; i2 += paramInt3) {
/* 364 */         int i3 = i2 + k;
/*     */         
/* 366 */         double d1 = paramArrayOfdouble1[i3];
/* 367 */         double d2 = paramArrayOfdouble1[i3 + 1];
/* 368 */         double d3 = paramArrayOfdouble1[i2];
/* 369 */         double d4 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 371 */         i2 += m;
/* 372 */         i3 += m;
/* 373 */         double d5 = paramArrayOfdouble1[i3];
/* 374 */         double d6 = paramArrayOfdouble1[i3 + 1];
/* 375 */         double d7 = paramArrayOfdouble1[i2];
/* 376 */         double d8 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 378 */         double d9 = d1;
/* 379 */         double d10 = d2;
/*     */         
/* 381 */         d1 = d3 - d9;
/* 382 */         d2 = d4 - d10;
/* 383 */         d3 += d9;
/* 384 */         d4 += d10;
/*     */         
/* 386 */         double d11 = d7;
/* 387 */         double d12 = d8;
/* 388 */         double d13 = d5;
/* 389 */         double d14 = d6;
/*     */         
/* 391 */         d9 = d11 - d13;
/* 392 */         d10 = d12 - d14;
/*     */         
/* 394 */         d5 = d1 + d10;
/* 395 */         d6 = d2 - d9;
/* 396 */         d1 -= d10;
/* 397 */         d2 += d9;
/*     */         
/* 399 */         d9 = d11 + d13;
/* 400 */         d10 = d12 + d14;
/*     */         
/* 402 */         d7 = d3 - d9;
/* 403 */         d8 = d4 - d10;
/* 404 */         d3 += d9;
/* 405 */         d4 += d10;
/*     */         
/* 407 */         paramArrayOfdouble1[i3] = d5;
/* 408 */         paramArrayOfdouble1[i3 + 1] = d6;
/* 409 */         paramArrayOfdouble1[i2] = d7;
/* 410 */         paramArrayOfdouble1[i2 + 1] = d8;
/*     */         
/* 412 */         i2 -= m;
/* 413 */         i3 -= m;
/* 414 */         paramArrayOfdouble1[i3] = d1;
/* 415 */         paramArrayOfdouble1[i3 + 1] = d2;
/* 416 */         paramArrayOfdouble1[i2] = d3;
/* 417 */         paramArrayOfdouble1[i2 + 1] = d4;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 422 */       for (i2 = 2; i2 < k; i2 += 2) {
/* 423 */         double d1 = paramArrayOfdouble2[paramInt2++];
/* 424 */         double d2 = paramArrayOfdouble2[paramInt2++];
/* 425 */         double d3 = paramArrayOfdouble2[n++];
/* 426 */         double d4 = paramArrayOfdouble2[n++];
/* 427 */         double d5 = paramArrayOfdouble2[i1++];
/* 428 */         double d6 = paramArrayOfdouble2[i1++];
/*     */ 
/*     */         
/*     */         int i3;
/*     */         
/* 433 */         for (i3 = i2; i3 < i; i3 += paramInt3) {
/* 434 */           int i4 = i3 + k;
/*     */           
/* 436 */           double d7 = paramArrayOfdouble1[i4];
/* 437 */           double d8 = paramArrayOfdouble1[i4 + 1];
/* 438 */           double d9 = paramArrayOfdouble1[i3];
/* 439 */           double d10 = paramArrayOfdouble1[i3 + 1];
/*     */           
/* 441 */           i3 += m;
/* 442 */           i4 += m;
/* 443 */           double d11 = paramArrayOfdouble1[i4];
/* 444 */           double d12 = paramArrayOfdouble1[i4 + 1];
/* 445 */           double d13 = paramArrayOfdouble1[i3];
/* 446 */           double d14 = paramArrayOfdouble1[i3 + 1];
/*     */           
/* 448 */           double d15 = d7 * d1 - d8 * d2;
/* 449 */           double d16 = d7 * d2 + d8 * d1;
/*     */           
/* 451 */           d7 = d9 - d15;
/* 452 */           d8 = d10 - d16;
/* 453 */           d9 += d15;
/* 454 */           d10 += d16;
/*     */           
/* 456 */           double d17 = d13 * d3 - d14 * d4;
/* 457 */           double d18 = d13 * d4 + d14 * d3;
/* 458 */           double d19 = d11 * d5 - d12 * d6;
/* 459 */           double d20 = d11 * d6 + d12 * d5;
/*     */           
/* 461 */           d15 = d17 - d19;
/* 462 */           d16 = d18 - d20;
/*     */           
/* 464 */           d11 = d7 + d16;
/* 465 */           d12 = d8 - d15;
/* 466 */           d7 -= d16;
/* 467 */           d8 += d15;
/*     */           
/* 469 */           d15 = d17 + d19;
/* 470 */           d16 = d18 + d20;
/*     */           
/* 472 */           d13 = d9 - d15;
/* 473 */           d14 = d10 - d16;
/* 474 */           d9 += d15;
/* 475 */           d10 += d16;
/*     */           
/* 477 */           paramArrayOfdouble1[i4] = d11;
/* 478 */           paramArrayOfdouble1[i4 + 1] = d12;
/* 479 */           paramArrayOfdouble1[i3] = d13;
/* 480 */           paramArrayOfdouble1[i3 + 1] = d14;
/*     */           
/* 482 */           i3 -= m;
/* 483 */           i4 -= m;
/* 484 */           paramArrayOfdouble1[i4] = d7;
/* 485 */           paramArrayOfdouble1[i4 + 1] = d8;
/* 486 */           paramArrayOfdouble1[i3] = d9;
/* 487 */           paramArrayOfdouble1[i3 + 1] = d10;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 492 */       paramInt2 += k << 1;
/*     */     } 
/*     */ 
/*     */     
/* 496 */     calcF2E(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void calcF4FE(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, int paramInt3, double[] paramArrayOfdouble2) {
/* 504 */     int i = paramInt1 << 1;
/*     */ 
/*     */     
/* 507 */     int j = paramArrayOfdouble2.length >> 1;
/* 508 */     while (paramInt3 < i) {
/*     */       
/* 510 */       int k = paramInt3;
/* 511 */       int m = paramInt3 << 1;
/* 512 */       if (m == i) {
/*     */         
/* 514 */         calcF2E(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */         return;
/*     */       } 
/* 517 */       paramInt3 <<= 2;
/* 518 */       int n = paramInt2 + k;
/* 519 */       int i1 = paramInt2 + j;
/* 520 */       for (int i2 = 0; i2 < k; i2 += 2) {
/* 521 */         double d1 = paramArrayOfdouble2[paramInt2++];
/* 522 */         double d2 = paramArrayOfdouble2[paramInt2++];
/* 523 */         double d3 = paramArrayOfdouble2[n++];
/* 524 */         double d4 = paramArrayOfdouble2[n++];
/* 525 */         double d5 = paramArrayOfdouble2[i1++];
/* 526 */         double d6 = paramArrayOfdouble2[i1++];
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 531 */         int i3 = i2 + k;
/*     */         
/* 533 */         double d7 = paramArrayOfdouble1[i3];
/* 534 */         double d8 = paramArrayOfdouble1[i3 + 1];
/* 535 */         double d9 = paramArrayOfdouble1[i2];
/* 536 */         double d10 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 538 */         i2 += m;
/* 539 */         i3 += m;
/* 540 */         double d11 = paramArrayOfdouble1[i3];
/* 541 */         double d12 = paramArrayOfdouble1[i3 + 1];
/* 542 */         double d13 = paramArrayOfdouble1[i2];
/* 543 */         double d14 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 545 */         double d15 = d7 * d1 - d8 * d2;
/* 546 */         double d16 = d7 * d2 + d8 * d1;
/*     */         
/* 548 */         d7 = d9 - d15;
/* 549 */         d8 = d10 - d16;
/* 550 */         d9 += d15;
/* 551 */         d10 += d16;
/*     */         
/* 553 */         double d17 = d13 * d3 - d14 * d4;
/* 554 */         double d18 = d13 * d4 + d14 * d3;
/* 555 */         double d19 = d11 * d5 - d12 * d6;
/* 556 */         double d20 = d11 * d6 + d12 * d5;
/*     */         
/* 558 */         d15 = d19 - d17;
/* 559 */         d16 = d20 - d18;
/*     */         
/* 561 */         d11 = d7 + d16;
/* 562 */         d12 = d8 - d15;
/* 563 */         d7 -= d16;
/* 564 */         d8 += d15;
/*     */         
/* 566 */         d15 = d17 + d19;
/* 567 */         d16 = d18 + d20;
/*     */         
/* 569 */         d13 = d9 - d15;
/* 570 */         d14 = d10 - d16;
/* 571 */         d9 += d15;
/* 572 */         d10 += d16;
/*     */         
/* 574 */         paramArrayOfdouble1[i3] = d11;
/* 575 */         paramArrayOfdouble1[i3 + 1] = d12;
/* 576 */         paramArrayOfdouble1[i2] = d13;
/* 577 */         paramArrayOfdouble1[i2 + 1] = d14;
/*     */         
/* 579 */         i2 -= m;
/* 580 */         i3 -= m;
/* 581 */         paramArrayOfdouble1[i3] = d7;
/* 582 */         paramArrayOfdouble1[i3 + 1] = d8;
/* 583 */         paramArrayOfdouble1[i2] = d9;
/* 584 */         paramArrayOfdouble1[i2 + 1] = d10;
/*     */       } 
/*     */ 
/*     */       
/* 588 */       paramInt2 += k << 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void calcF4IE(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, int paramInt3, double[] paramArrayOfdouble2) {
/* 598 */     int i = paramInt1 << 1;
/*     */ 
/*     */     
/* 601 */     int j = paramArrayOfdouble2.length >> 1;
/* 602 */     while (paramInt3 < i) {
/*     */       
/* 604 */       int k = paramInt3;
/* 605 */       int m = paramInt3 << 1;
/* 606 */       if (m == i) {
/*     */         
/* 608 */         calcF2E(paramInt1, paramArrayOfdouble1, paramInt2, paramInt3, paramArrayOfdouble2);
/*     */         return;
/*     */       } 
/* 611 */       paramInt3 <<= 2;
/* 612 */       int n = paramInt2 + k;
/* 613 */       int i1 = paramInt2 + j;
/* 614 */       for (int i2 = 0; i2 < k; i2 += 2) {
/* 615 */         double d1 = paramArrayOfdouble2[paramInt2++];
/* 616 */         double d2 = paramArrayOfdouble2[paramInt2++];
/* 617 */         double d3 = paramArrayOfdouble2[n++];
/* 618 */         double d4 = paramArrayOfdouble2[n++];
/* 619 */         double d5 = paramArrayOfdouble2[i1++];
/* 620 */         double d6 = paramArrayOfdouble2[i1++];
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 625 */         int i3 = i2 + k;
/*     */         
/* 627 */         double d7 = paramArrayOfdouble1[i3];
/* 628 */         double d8 = paramArrayOfdouble1[i3 + 1];
/* 629 */         double d9 = paramArrayOfdouble1[i2];
/* 630 */         double d10 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 632 */         i2 += m;
/* 633 */         i3 += m;
/* 634 */         double d11 = paramArrayOfdouble1[i3];
/* 635 */         double d12 = paramArrayOfdouble1[i3 + 1];
/* 636 */         double d13 = paramArrayOfdouble1[i2];
/* 637 */         double d14 = paramArrayOfdouble1[i2 + 1];
/*     */         
/* 639 */         double d15 = d7 * d1 - d8 * d2;
/* 640 */         double d16 = d7 * d2 + d8 * d1;
/*     */         
/* 642 */         d7 = d9 - d15;
/* 643 */         d8 = d10 - d16;
/* 644 */         d9 += d15;
/* 645 */         d10 += d16;
/*     */         
/* 647 */         double d17 = d13 * d3 - d14 * d4;
/* 648 */         double d18 = d13 * d4 + d14 * d3;
/* 649 */         double d19 = d11 * d5 - d12 * d6;
/* 650 */         double d20 = d11 * d6 + d12 * d5;
/*     */         
/* 652 */         d15 = d17 - d19;
/* 653 */         d16 = d18 - d20;
/*     */         
/* 655 */         d11 = d7 + d16;
/* 656 */         d12 = d8 - d15;
/* 657 */         d7 -= d16;
/* 658 */         d8 += d15;
/*     */         
/* 660 */         d15 = d17 + d19;
/* 661 */         d16 = d18 + d20;
/*     */         
/* 663 */         d13 = d9 - d15;
/* 664 */         d14 = d10 - d16;
/* 665 */         d9 += d15;
/* 666 */         d10 += d16;
/*     */         
/* 668 */         paramArrayOfdouble1[i3] = d11;
/* 669 */         paramArrayOfdouble1[i3 + 1] = d12;
/* 670 */         paramArrayOfdouble1[i2] = d13;
/* 671 */         paramArrayOfdouble1[i2 + 1] = d14;
/*     */         
/* 673 */         i2 -= m;
/* 674 */         i3 -= m;
/* 675 */         paramArrayOfdouble1[i3] = d7;
/* 676 */         paramArrayOfdouble1[i3 + 1] = d8;
/* 677 */         paramArrayOfdouble1[i2] = d9;
/* 678 */         paramArrayOfdouble1[i2 + 1] = d10;
/*     */       } 
/*     */ 
/*     */       
/* 682 */       paramInt2 += k << 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final void bitreversal(double[] paramArrayOfdouble) {
/* 689 */     if (this.fftFrameSize < 4) {
/*     */       return;
/*     */     }
/* 692 */     int i = this.fftFrameSize2 - 2;
/* 693 */     for (byte b = 0; b < this.fftFrameSize; b += 4) {
/* 694 */       int j = this.bitm_array[b];
/*     */ 
/*     */       
/* 697 */       if (b < j) {
/*     */         
/* 699 */         int n = b;
/* 700 */         int i1 = j;
/*     */ 
/*     */ 
/*     */         
/* 704 */         double d3 = paramArrayOfdouble[n];
/* 705 */         paramArrayOfdouble[n] = paramArrayOfdouble[i1];
/* 706 */         paramArrayOfdouble[i1] = d3;
/*     */         
/* 708 */         n++;
/* 709 */         i1++;
/* 710 */         double d4 = paramArrayOfdouble[n];
/* 711 */         paramArrayOfdouble[n] = paramArrayOfdouble[i1];
/* 712 */         paramArrayOfdouble[i1] = d4;
/*     */         
/* 714 */         n = i - b;
/* 715 */         i1 = i - j;
/*     */ 
/*     */ 
/*     */         
/* 719 */         d3 = paramArrayOfdouble[n];
/* 720 */         paramArrayOfdouble[n] = paramArrayOfdouble[i1];
/* 721 */         paramArrayOfdouble[i1] = d3;
/*     */         
/* 723 */         n++;
/* 724 */         i1++;
/* 725 */         d4 = paramArrayOfdouble[n];
/* 726 */         paramArrayOfdouble[n] = paramArrayOfdouble[i1];
/* 727 */         paramArrayOfdouble[i1] = d4;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 732 */       int k = j + this.fftFrameSize;
/*     */ 
/*     */       
/* 735 */       int m = b + 2;
/* 736 */       double d1 = paramArrayOfdouble[m];
/* 737 */       paramArrayOfdouble[m] = paramArrayOfdouble[k];
/* 738 */       paramArrayOfdouble[k] = d1;
/*     */       
/* 740 */       m++;
/* 741 */       k++;
/* 742 */       double d2 = paramArrayOfdouble[m];
/* 743 */       paramArrayOfdouble[m] = paramArrayOfdouble[k];
/* 744 */       paramArrayOfdouble[k] = d2;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/FFT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */