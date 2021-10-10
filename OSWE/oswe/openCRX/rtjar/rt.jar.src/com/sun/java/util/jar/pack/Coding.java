/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Coding
/*     */   implements Comparable<Coding>, CodingMethod, Histogram.BitMetric
/*     */ {
/*     */   public static final int B_MAX = 5;
/*     */   public static final int H_MAX = 256;
/*     */   public static final int S_MAX = 2;
/*     */   private final int B;
/*     */   private final int H;
/*     */   private final int L;
/*     */   private final int S;
/*     */   private final int del;
/*     */   private final int min;
/*     */   private final int max;
/*     */   private final int umin;
/*     */   private final int umax;
/*     */   private final int[] byteMin;
/*     */   private final int[] byteMax;
/*     */   private static Map<Coding, Coding> codeMap;
/*     */   
/*     */   private static int saturate32(long paramLong) {
/* 158 */     if (paramLong > 2147483647L) return Integer.MAX_VALUE; 
/* 159 */     if (paramLong < -2147483648L) return Integer.MIN_VALUE; 
/* 160 */     return (int)paramLong;
/*     */   }
/*     */   private static long codeRangeLong(int paramInt1, int paramInt2) {
/* 163 */     return codeRangeLong(paramInt1, paramInt2, paramInt1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long codeRangeLong(int paramInt1, int paramInt2, int paramInt3) {
/* 169 */     assert paramInt3 >= 0 && paramInt3 <= paramInt1;
/* 170 */     assert paramInt1 >= 1 && paramInt1 <= 5;
/* 171 */     assert paramInt2 >= 1 && paramInt2 <= 256;
/* 172 */     if (paramInt3 == 0) return 0L; 
/* 173 */     if (paramInt1 == 1) return paramInt2; 
/* 174 */     int i = 256 - paramInt2;
/* 175 */     long l1 = 0L;
/* 176 */     long l2 = 1L;
/* 177 */     for (byte b = 1; b <= paramInt3; b++) {
/* 178 */       l1 += l2;
/* 179 */       l2 *= paramInt2;
/*     */     } 
/* 181 */     l1 *= i;
/* 182 */     if (paramInt3 == paramInt1)
/* 183 */       l1 += l2; 
/* 184 */     return l1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int codeMax(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 189 */     long l1 = codeRangeLong(paramInt1, paramInt2, paramInt4);
/* 190 */     if (l1 == 0L)
/* 191 */       return -1; 
/* 192 */     if (paramInt3 == 0 || l1 >= 4294967296L)
/* 193 */       return saturate32(l1 - 1L); 
/* 194 */     long l2 = l1 - 1L;
/* 195 */     while (isNegativeCode(l2, paramInt3)) {
/* 196 */       l2--;
/*     */     }
/* 198 */     if (l2 < 0L) return -1; 
/* 199 */     int i = decodeSign32(l2, paramInt3);
/*     */     
/* 201 */     if (i < 0)
/* 202 */       return Integer.MAX_VALUE; 
/* 203 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int codeMin(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 211 */     long l1 = codeRangeLong(paramInt1, paramInt2, paramInt4);
/* 212 */     if (l1 >= 4294967296L && paramInt4 == paramInt1)
/*     */     {
/* 214 */       return Integer.MIN_VALUE;
/*     */     }
/* 216 */     if (paramInt3 == 0) {
/* 217 */       return 0;
/*     */     }
/* 219 */     long l2 = l1 - 1L;
/* 220 */     while (!isNegativeCode(l2, paramInt3)) {
/* 221 */       l2--;
/*     */     }
/* 223 */     if (l2 < 0L) return 0; 
/* 224 */     return decodeSign32(l2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long toUnsigned32(int paramInt) {
/* 232 */     return paramInt << 32L >>> 32L;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isNegativeCode(long paramLong, int paramInt) {
/* 237 */     assert paramInt > 0;
/* 238 */     assert paramLong >= -1L;
/* 239 */     int i = (1 << paramInt) - 1;
/* 240 */     return (((int)paramLong + 1 & i) == 0);
/*     */   }
/*     */   private static boolean hasNegativeCode(int paramInt1, int paramInt2) {
/* 243 */     assert paramInt2 > 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     return (0 > paramInt1 && paramInt1 >= (-1 >>> paramInt2 ^ 0xFFFFFFFF));
/*     */   } private static int decodeSign32(long paramLong, int paramInt) {
/*     */     int i;
/* 253 */     assert paramLong == toUnsigned32((int)paramLong) : 
/* 254 */       Long.toHexString(paramLong);
/* 255 */     if (paramInt == 0) {
/* 256 */       return (int)paramLong;
/*     */     }
/*     */     
/* 259 */     if (isNegativeCode(paramLong, paramInt)) {
/*     */       
/* 261 */       i = (int)paramLong >>> paramInt ^ 0xFFFFFFFF;
/*     */     } else {
/*     */       
/* 264 */       i = (int)paramLong - ((int)paramLong >>> paramInt);
/*     */     } 
/*     */     
/* 267 */     assert paramInt != 1 || i == ((int)paramLong >>> 1 ^ -((int)paramLong & 0x1));
/* 268 */     return i;
/*     */   }
/*     */   private static long encodeSign32(int paramInt1, int paramInt2) {
/* 271 */     if (paramInt2 == 0) {
/* 272 */       return toUnsigned32(paramInt1);
/*     */     }
/* 274 */     int i = (1 << paramInt2) - 1;
/*     */     
/* 276 */     if (!hasNegativeCode(paramInt1, paramInt2)) {
/*     */       
/* 278 */       l = paramInt1 + toUnsigned32(paramInt1) / i;
/*     */     } else {
/*     */       
/* 281 */       l = ((-paramInt1 << paramInt2) - 1);
/*     */     } 
/* 283 */     long l = toUnsigned32((int)l);
/* 284 */     assert paramInt1 == decodeSign32(l, paramInt2) : 
/* 285 */       Long.toHexString(l) + " -> " + 
/* 286 */       Integer.toHexString(paramInt1) + " != " + 
/* 287 */       Integer.toHexString(decodeSign32(l, paramInt2));
/* 288 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeInt(byte[] paramArrayOfbyte, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 293 */     long l1 = encodeSign32(paramInt1, paramInt4);
/* 294 */     assert l1 == toUnsigned32((int)l1);
/* 295 */     assert l1 < codeRangeLong(paramInt2, paramInt3) : 
/* 296 */       Long.toHexString(l1);
/* 297 */     int i = 256 - paramInt3;
/* 298 */     long l2 = l1;
/* 299 */     int j = paramArrayOfint[0];
/* 300 */     for (byte b = 0; b < paramInt2 - 1 && 
/* 301 */       l2 >= i; b++) {
/*     */       
/* 303 */       l2 -= i;
/* 304 */       int k = (int)(i + l2 % paramInt3);
/* 305 */       l2 /= paramInt3;
/* 306 */       paramArrayOfbyte[j++] = (byte)k;
/*     */     } 
/* 308 */     paramArrayOfbyte[j++] = (byte)(int)l2;
/*     */     
/* 310 */     paramArrayOfint[0] = j;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int readInt(byte[] paramArrayOfbyte, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
/* 316 */     int i = 256 - paramInt2;
/* 317 */     long l1 = 0L;
/* 318 */     long l2 = 1L;
/* 319 */     int j = paramArrayOfint[0];
/* 320 */     for (byte b = 0; b < paramInt1; b++) {
/* 321 */       int k = paramArrayOfbyte[j++] & 0xFF;
/* 322 */       l1 += k * l2;
/* 323 */       l2 *= paramInt2;
/* 324 */       if (k < i) {
/*     */         break;
/*     */       }
/*     */     } 
/* 328 */     paramArrayOfint[0] = j;
/* 329 */     return decodeSign32(l1, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int readIntFrom(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3) throws IOException {
/* 334 */     int i = 256 - paramInt2;
/* 335 */     long l1 = 0L;
/* 336 */     long l2 = 1L;
/* 337 */     for (byte b = 0; b < paramInt1; b++) {
/* 338 */       int j = paramInputStream.read();
/* 339 */       if (j < 0) throw new RuntimeException("unexpected EOF"); 
/* 340 */       l1 += j * l2;
/* 341 */       l2 *= paramInt2;
/* 342 */       if (j < i)
/*     */         break; 
/* 344 */     }  assert l1 >= 0L && l1 < codeRangeLong(paramInt1, paramInt2);
/* 345 */     return decodeSign32(l1, paramInt3);
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
/*     */   private Coding(int paramInt1, int paramInt2, int paramInt3) {
/* 367 */     this(paramInt1, paramInt2, paramInt3, 0);
/*     */   }
/*     */   private Coding(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 370 */     this.B = paramInt1;
/* 371 */     this.H = paramInt2;
/* 372 */     this.L = 256 - paramInt2;
/* 373 */     this.S = paramInt3;
/* 374 */     this.del = paramInt4;
/* 375 */     this.min = codeMin(paramInt1, paramInt2, paramInt3, paramInt1);
/* 376 */     this.max = codeMax(paramInt1, paramInt2, paramInt3, paramInt1);
/* 377 */     this.umin = codeMin(paramInt1, paramInt2, 0, paramInt1);
/* 378 */     this.umax = codeMax(paramInt1, paramInt2, 0, paramInt1);
/* 379 */     this.byteMin = new int[paramInt1];
/* 380 */     this.byteMax = new int[paramInt1];
/*     */     
/* 382 */     for (byte b = 1; b <= paramInt1; b++) {
/* 383 */       this.byteMin[b - 1] = codeMin(paramInt1, paramInt2, paramInt3, b);
/* 384 */       this.byteMax[b - 1] = codeMax(paramInt1, paramInt2, paramInt3, b);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 389 */     if (!(paramObject instanceof Coding)) return false; 
/* 390 */     Coding coding = (Coding)paramObject;
/* 391 */     if (this.B != coding.B) return false; 
/* 392 */     if (this.H != coding.H) return false; 
/* 393 */     if (this.S != coding.S) return false; 
/* 394 */     if (this.del != coding.del) return false; 
/* 395 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 399 */     return (this.del << 14) + (this.S << 11) + (this.B << 8) + (this.H << 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized Coding of(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 405 */     if (codeMap == null) codeMap = new HashMap<>(); 
/* 406 */     Coding coding1 = new Coding(paramInt1, paramInt2, paramInt3, paramInt4);
/* 407 */     Coding coding2 = codeMap.get(coding1);
/* 408 */     if (coding2 == null) codeMap.put(coding1, coding2 = coding1); 
/* 409 */     return coding2;
/*     */   }
/*     */   
/*     */   public static Coding of(int paramInt1, int paramInt2) {
/* 413 */     return of(paramInt1, paramInt2, 0, 0);
/*     */   }
/*     */   
/*     */   public static Coding of(int paramInt1, int paramInt2, int paramInt3) {
/* 417 */     return of(paramInt1, paramInt2, paramInt3, 0);
/*     */   }
/*     */   
/*     */   public boolean canRepresentValue(int paramInt) {
/* 421 */     if (isSubrange()) {
/* 422 */       return canRepresentUnsigned(paramInt);
/*     */     }
/* 424 */     return canRepresentSigned(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRepresentSigned(int paramInt) {
/* 433 */     return (paramInt >= this.min && paramInt <= this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRepresentUnsigned(int paramInt) {
/* 442 */     return (paramInt >= this.umin && paramInt <= this.umax);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readFrom(byte[] paramArrayOfbyte, int[] paramArrayOfint) {
/* 447 */     return readInt(paramArrayOfbyte, paramArrayOfint, this.B, this.H, this.S);
/*     */   }
/*     */   public void writeTo(byte[] paramArrayOfbyte, int[] paramArrayOfint, int paramInt) {
/* 450 */     writeInt(paramArrayOfbyte, paramArrayOfint, paramInt, this.B, this.H, this.S);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readFrom(InputStream paramInputStream) throws IOException {
/* 455 */     return readIntFrom(paramInputStream, this.B, this.H, this.S);
/*     */   }
/*     */   public void writeTo(OutputStream paramOutputStream, int paramInt) throws IOException {
/* 458 */     byte[] arrayOfByte = new byte[this.B];
/* 459 */     int[] arrayOfInt = new int[1];
/* 460 */     writeInt(arrayOfByte, arrayOfInt, paramInt, this.B, this.H, this.S);
/* 461 */     paramOutputStream.write(arrayOfByte, 0, arrayOfInt[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readArrayFrom(InputStream paramInputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/*     */     int i;
/* 467 */     for (i = paramInt1; i < paramInt2; i++) {
/* 468 */       paramArrayOfint[i] = readFrom(paramInputStream);
/*     */     }
/* 470 */     for (i = 0; i < this.del; i++) {
/* 471 */       long l = 0L;
/* 472 */       for (int j = paramInt1; j < paramInt2; j++) {
/* 473 */         l += paramArrayOfint[j];
/*     */         
/* 475 */         if (isSubrange()) {
/* 476 */           l = reduceToUnsignedRange(l);
/*     */         }
/* 478 */         paramArrayOfint[j] = (int)l;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void writeArrayTo(OutputStream paramOutputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/* 483 */     if (paramInt2 <= paramInt1)
/* 484 */       return;  for (byte b = 0; b < this.del; b++) {
/*     */       int[] arrayOfInt1;
/* 486 */       if (!isSubrange()) {
/* 487 */         arrayOfInt1 = makeDeltas(paramArrayOfint, paramInt1, paramInt2, 0, 0);
/*     */       } else {
/* 489 */         arrayOfInt1 = makeDeltas(paramArrayOfint, paramInt1, paramInt2, this.min, this.max);
/* 490 */       }  paramArrayOfint = arrayOfInt1;
/* 491 */       paramInt1 = 0;
/* 492 */       paramInt2 = arrayOfInt1.length;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 497 */     byte[] arrayOfByte = new byte[256];
/* 498 */     int i = arrayOfByte.length - this.B;
/* 499 */     int[] arrayOfInt = { 0 };
/* 500 */     for (int j = paramInt1; j < paramInt2; ) {
/* 501 */       while (arrayOfInt[0] <= i) {
/* 502 */         writeTo(arrayOfByte, arrayOfInt, paramArrayOfint[j++]);
/* 503 */         if (j >= paramInt2)
/*     */           break; 
/* 505 */       }  paramOutputStream.write(arrayOfByte, 0, arrayOfInt[0]);
/* 506 */       arrayOfInt[0] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSubrange() {
/* 514 */     return (this.max < Integer.MAX_VALUE && this.max - this.min + 1L <= 2147483647L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isFullRange() {
/* 523 */     return (this.max == Integer.MAX_VALUE && this.min == Integer.MIN_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   int getRange() {
/* 528 */     assert isSubrange();
/* 529 */     return this.max - this.min + 1;
/*     */   }
/*     */   
/* 532 */   Coding setB(int paramInt) { return of(paramInt, this.H, this.S, this.del); }
/* 533 */   Coding setH(int paramInt) { return of(this.B, paramInt, this.S, this.del); }
/* 534 */   Coding setS(int paramInt) { return of(this.B, this.H, paramInt, this.del); }
/* 535 */   Coding setL(int paramInt) { return setH(256 - paramInt); }
/* 536 */   Coding setD(int paramInt) { return of(this.B, this.H, this.S, paramInt); } Coding getDeltaCoding() {
/* 537 */     return setD(this.del + 1);
/*     */   }
/*     */   
/*     */   Coding getValueCoding() {
/* 541 */     if (isDelta()) {
/* 542 */       return of(this.B, this.H, 0, this.del - 1);
/*     */     }
/* 544 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int reduceToUnsignedRange(long paramLong) {
/* 551 */     if (paramLong == (int)paramLong && canRepresentUnsigned((int)paramLong))
/*     */     {
/* 553 */       return (int)paramLong; } 
/* 554 */     int i = getRange();
/* 555 */     assert i > 0;
/* 556 */     paramLong %= i;
/* 557 */     if (paramLong < 0L) paramLong += i; 
/* 558 */     assert canRepresentUnsigned((int)paramLong);
/* 559 */     return (int)paramLong;
/*     */   }
/*     */   
/*     */   int reduceToSignedRange(int paramInt) {
/* 563 */     if (canRepresentSigned(paramInt))
/*     */     {
/* 565 */       return paramInt; } 
/* 566 */     return reduceToSignedRange(paramInt, this.min, this.max);
/*     */   }
/*     */   static int reduceToSignedRange(int paramInt1, int paramInt2, int paramInt3) {
/* 569 */     int i = paramInt3 - paramInt2 + 1;
/* 570 */     assert i > 0;
/* 571 */     int j = paramInt1;
/* 572 */     paramInt1 -= paramInt2;
/* 573 */     if (paramInt1 < 0 && j >= 0) {
/*     */       
/* 575 */       paramInt1 -= i;
/* 576 */       assert paramInt1 >= 0;
/*     */     } 
/* 578 */     paramInt1 %= i;
/* 579 */     if (paramInt1 < 0) paramInt1 += i; 
/* 580 */     paramInt1 += paramInt2;
/* 581 */     assert paramInt2 <= paramInt1 && paramInt1 <= paramInt3;
/* 582 */     return paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSigned() {
/* 589 */     return (this.min < 0);
/*     */   }
/*     */   
/*     */   boolean isDelta() {
/* 593 */     return (this.del != 0);
/*     */   }
/*     */   
/* 596 */   public int B() { return this.B; }
/* 597 */   public int H() { return this.H; }
/* 598 */   public int L() { return this.L; }
/* 599 */   public int S() { return this.S; }
/* 600 */   public int del() { return this.del; }
/* 601 */   public int min() { return this.min; }
/* 602 */   public int max() { return this.max; }
/* 603 */   public int umin() { return this.umin; }
/* 604 */   public int umax() { return this.umax; }
/* 605 */   public int byteMin(int paramInt) { return this.byteMin[paramInt - 1]; } public int byteMax(int paramInt) {
/* 606 */     return this.byteMax[paramInt - 1];
/*     */   }
/*     */   public int compareTo(Coding paramCoding) {
/* 609 */     int i = this.del - paramCoding.del;
/* 610 */     if (i == 0)
/* 611 */       i = this.B - paramCoding.B; 
/* 612 */     if (i == 0)
/* 613 */       i = this.H - paramCoding.H; 
/* 614 */     if (i == 0)
/* 615 */       i = this.S - paramCoding.S; 
/* 616 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int distanceFrom(Coding paramCoding) {
/* 621 */     int m, i = this.del - paramCoding.del;
/* 622 */     if (i < 0) i = -i; 
/* 623 */     int j = this.S - paramCoding.S;
/* 624 */     if (j < 0) j = -j; 
/* 625 */     int k = this.B - paramCoding.B;
/* 626 */     if (k < 0) k = -k;
/*     */     
/* 628 */     if (this.H == paramCoding.H) {
/* 629 */       m = 0;
/*     */     } else {
/*     */       
/* 632 */       int i1 = getHL();
/* 633 */       int i2 = paramCoding.getHL();
/*     */       
/* 635 */       i1 *= i1;
/* 636 */       i2 *= i2;
/* 637 */       if (i1 > i2) {
/* 638 */         m = ceil_lg2(1 + (i1 - 1) / i2);
/*     */       } else {
/* 640 */         m = ceil_lg2(1 + (i2 - 1) / i1);
/*     */       } 
/* 642 */     }  int n = 5 * (i + j + k) + m;
/* 643 */     assert n != 0 || compareTo(paramCoding) == 0;
/* 644 */     return n;
/*     */   }
/*     */   
/*     */   private int getHL() {
/* 648 */     if (this.H <= 128) return this.H; 
/* 649 */     if (this.L >= 1) return 16384 / this.L; 
/* 650 */     return 32768;
/*     */   }
/*     */ 
/*     */   
/*     */   static int ceil_lg2(int paramInt) {
/* 655 */     assert paramInt - 1 >= 0;
/* 656 */     paramInt--;
/* 657 */     byte b = 0;
/* 658 */     while (paramInt != 0) {
/* 659 */       b++;
/* 660 */       paramInt >>= 1;
/*     */     } 
/* 662 */     return b;
/*     */   }
/*     */   
/* 665 */   private static final byte[] byteBitWidths = new byte[256]; static {
/*     */     int i;
/* 667 */     for (i = 0; i < byteBitWidths.length; i++) {
/* 668 */       byteBitWidths[i] = (byte)ceil_lg2(i + 1);
/*     */     }
/* 670 */     for (i = 10; i >= 0; i = (i << 1) - (i >> 3)) {
/* 671 */       assert bitWidth(i) == ceil_lg2(i + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int bitWidth(int paramInt) {
/* 679 */     if (paramInt < 0) paramInt ^= 0xFFFFFFFF; 
/* 680 */     int i = 0;
/* 681 */     int j = paramInt;
/* 682 */     if (j < byteBitWidths.length) {
/* 683 */       return byteBitWidths[j];
/*     */     }
/* 685 */     int k = j >>> 16;
/* 686 */     if (k != 0) {
/* 687 */       j = k;
/* 688 */       i += true;
/*     */     } 
/* 690 */     k = j >>> 8;
/* 691 */     if (k != 0) {
/* 692 */       j = k;
/* 693 */       i += true;
/*     */     } 
/* 695 */     i += byteBitWidths[j];
/*     */     
/* 697 */     return i;
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
/*     */   static int[] makeDeltas(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 709 */     assert paramInt4 >= paramInt3;
/* 710 */     int i = paramInt2 - paramInt1;
/* 711 */     int[] arrayOfInt = new int[i];
/* 712 */     int j = 0;
/* 713 */     if (paramInt3 == paramInt4) {
/* 714 */       for (byte b = 0; b < i; b++) {
/* 715 */         int k = paramArrayOfint[paramInt1 + b];
/* 716 */         arrayOfInt[b] = k - j;
/* 717 */         j = k;
/*     */       } 
/*     */     } else {
/* 720 */       for (byte b = 0; b < i; b++) {
/* 721 */         int k = paramArrayOfint[paramInt1 + b];
/* 722 */         assert k >= 0 && k + paramInt3 <= paramInt4;
/* 723 */         int m = k - j;
/* 724 */         assert m == k - j;
/* 725 */         j = k;
/*     */         
/* 727 */         m = reduceToSignedRange(m, paramInt3, paramInt4);
/* 728 */         arrayOfInt[b] = m;
/*     */       } 
/*     */     } 
/* 731 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   boolean canRepresent(int paramInt1, int paramInt2) {
/* 735 */     assert paramInt1 <= paramInt2;
/* 736 */     if (this.del > 0) {
/* 737 */       if (isSubrange())
/*     */       {
/* 739 */         return (canRepresentUnsigned(paramInt2) && 
/* 740 */           canRepresentUnsigned(paramInt1));
/*     */       }
/*     */       
/* 743 */       return isFullRange();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 748 */     return (canRepresentSigned(paramInt2) && 
/* 749 */       canRepresentSigned(paramInt1));
/*     */   }
/*     */   
/*     */   boolean canRepresent(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 753 */     int i = paramInt2 - paramInt1;
/* 754 */     if (i == 0) return true; 
/* 755 */     if (isFullRange()) return true;
/*     */     
/* 757 */     int j = paramArrayOfint[paramInt1];
/* 758 */     int k = j;
/* 759 */     for (byte b = 1; b < i; b++) {
/* 760 */       int m = paramArrayOfint[paramInt1 + b];
/* 761 */       if (j < m) j = m; 
/* 762 */       if (k > m) k = m; 
/*     */     } 
/* 764 */     return canRepresent(k, j);
/*     */   }
/*     */   
/*     */   public double getBitLength(int paramInt) {
/* 768 */     return getLength(paramInt) * 8.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength(int paramInt) {
/* 777 */     if (isDelta() && isSubrange()) {
/* 778 */       if (!canRepresentUnsigned(paramInt))
/* 779 */         return Integer.MAX_VALUE; 
/* 780 */       paramInt = reduceToSignedRange(paramInt);
/*     */     } 
/* 782 */     if (paramInt >= 0) {
/* 783 */       for (byte b = 0; b < this.B; b++) {
/* 784 */         if (paramInt <= this.byteMax[b]) return b + 1; 
/*     */       } 
/*     */     } else {
/* 787 */       for (byte b = 0; b < this.B; b++) {
/* 788 */         if (paramInt >= this.byteMin[b]) return b + 1; 
/*     */       } 
/*     */     } 
/* 791 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public int getLength(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 795 */     int i = paramInt2 - paramInt1;
/* 796 */     if (this.B == 1) return i; 
/* 797 */     if (this.L == 0) return i * this.B; 
/* 798 */     if (isDelta()) {
/*     */       int[] arrayOfInt;
/* 800 */       if (!isSubrange()) {
/* 801 */         arrayOfInt = makeDeltas(paramArrayOfint, paramInt1, paramInt2, 0, 0);
/*     */       } else {
/* 803 */         arrayOfInt = makeDeltas(paramArrayOfint, paramInt1, paramInt2, this.min, this.max);
/*     */       } 
/* 805 */       paramArrayOfint = arrayOfInt;
/* 806 */       paramInt1 = 0;
/*     */     } 
/* 808 */     int j = i;
/*     */     
/* 810 */     for (byte b = 1; b <= this.B; b++) {
/*     */       
/* 812 */       int k = this.byteMax[b - 1];
/* 813 */       int m = this.byteMin[b - 1];
/* 814 */       byte b1 = 0;
/* 815 */       for (byte b2 = 0; b2 < i; b2++) {
/* 816 */         int n = paramArrayOfint[paramInt1 + b2];
/* 817 */         if (n >= 0)
/* 818 */         { if (n > k) b1++;
/*     */            }
/* 820 */         else if (n < m) { b1++; }
/*     */       
/*     */       } 
/* 823 */       if (b1 == 0)
/* 824 */         break;  if (b == this.B) return Integer.MAX_VALUE; 
/* 825 */       j += b1;
/*     */     } 
/* 827 */     return j;
/*     */   }
/*     */   
/*     */   public byte[] getMetaCoding(Coding paramCoding) {
/* 831 */     if (paramCoding == this) return new byte[] { 0 }; 
/* 832 */     int i = BandStructure.indexOf(this);
/* 833 */     if (i > 0)
/* 834 */       return new byte[] { (byte)i }; 
/* 835 */     return new byte[] { 116, (byte)(this.del + 2 * this.S + 8 * (this.B - 1)), (byte)(this.H - 1) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseMetaCoding(byte[] paramArrayOfbyte, int paramInt, Coding paramCoding, CodingMethod[] paramArrayOfCodingMethod) {
/* 842 */     int i = paramArrayOfbyte[paramInt++] & 0xFF;
/* 843 */     if (1 <= i && i <= 115) {
/* 844 */       Coding coding = BandStructure.codingForIndex(i);
/* 845 */       assert coding != null;
/* 846 */       paramArrayOfCodingMethod[0] = coding;
/* 847 */       return paramInt;
/*     */     } 
/* 849 */     if (i == 116) {
/* 850 */       int j = paramArrayOfbyte[paramInt++] & 0xFF;
/* 851 */       int k = paramArrayOfbyte[paramInt++] & 0xFF;
/* 852 */       int m = j % 2;
/* 853 */       int n = j / 2 % 4;
/* 854 */       int i1 = j / 8 + 1;
/* 855 */       int i2 = k + 1;
/* 856 */       if (1 > i1 || i1 > 5 || 0 > n || n > 2 || 1 > i2 || i2 > 256 || 0 > m || m > 1 || (i1 == 1 && i2 != 256) || (i1 == 5 && i2 == 256))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 862 */         throw new RuntimeException("Bad arb. coding: (" + i1 + "," + i2 + "," + n + "," + m);
/*     */       }
/* 864 */       paramArrayOfCodingMethod[0] = of(i1, i2, n, m);
/* 865 */       return paramInt;
/*     */     } 
/* 867 */     return paramInt - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String keyString() {
/* 872 */     return "(" + this.B + "," + this.H + "," + this.S + "," + this.del + ")";
/*     */   }
/*     */   
/*     */   public String toString() {
/* 876 */     return "Coding" + keyString();
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean verboseStringForDebug = false;
/*     */ 
/*     */   
/*     */   String stringForDebug() {
/* 884 */     String str1 = (this.min == Integer.MIN_VALUE) ? "min" : ("" + this.min);
/* 885 */     String str2 = (this.max == Integer.MAX_VALUE) ? "max" : ("" + this.max);
/* 886 */     String str3 = keyString() + " L=" + this.L + " r=[" + str1 + "," + str2 + "]";
/* 887 */     if (isSubrange()) {
/* 888 */       str3 = str3 + " subrange";
/* 889 */     } else if (!isFullRange()) {
/* 890 */       str3 = str3 + " MIDRANGE";
/* 891 */     }  if (verboseStringForDebug) {
/* 892 */       str3 = str3 + " {";
/* 893 */       int i = 0;
/* 894 */       for (byte b = 1; b <= this.B; b++) {
/* 895 */         int j = saturate32(this.byteMax[b - 1] - this.byteMin[b - 1] + 1L);
/* 896 */         assert j == saturate32(codeRangeLong(this.B, this.H, b));
/* 897 */         j -= i;
/* 898 */         i = j;
/* 899 */         String str = (j == Integer.MAX_VALUE) ? "max" : ("" + j);
/* 900 */         str3 = str3 + " #" + b + "=" + str;
/*     */       } 
/* 902 */       str3 = str3 + " }";
/*     */     } 
/* 904 */     return str3;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Coding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */