/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PopulationCoding
/*     */   implements CodingMethod
/*     */ {
/*     */   Histogram vHist;
/*     */   int[] fValues;
/*     */   int fVlen;
/*     */   long[] symtab;
/*     */   CodingMethod favoredCoding;
/*     */   CodingMethod tokenCoding;
/*     */   CodingMethod unfavoredCoding;
/*  53 */   int L = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFavoredValues(int[] paramArrayOfint, int paramInt) {
/*  58 */     assert paramArrayOfint[0] == 0;
/*  59 */     assert this.fValues == null;
/*  60 */     this.fValues = paramArrayOfint;
/*  61 */     this.fVlen = paramInt;
/*  62 */     if (this.L >= 0)
/*  63 */       setL(this.L); 
/*     */   }
/*     */   
/*     */   public void setFavoredValues(int[] paramArrayOfint) {
/*  67 */     int i = paramArrayOfint.length - 1;
/*  68 */     setFavoredValues(paramArrayOfint, i);
/*     */   }
/*     */   public void setHistogram(Histogram paramHistogram) {
/*  71 */     this.vHist = paramHistogram;
/*     */   }
/*     */   public void setL(int paramInt) {
/*  74 */     this.L = paramInt;
/*  75 */     if (paramInt >= 0 && this.fValues != null && this.tokenCoding == null) {
/*  76 */       this.tokenCoding = fitTokenCoding(this.fVlen, paramInt);
/*  77 */       assert this.tokenCoding != null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Coding fitTokenCoding(int paramInt1, int paramInt2) {
/*  83 */     if (paramInt1 < 256)
/*     */     {
/*  85 */       return BandStructure.BYTE1; } 
/*  86 */     Coding coding1 = BandStructure.UNSIGNED5.setL(paramInt2);
/*  87 */     if (!coding1.canRepresentUnsigned(paramInt1))
/*  88 */       return null; 
/*  89 */     Coding coding2 = coding1;
/*  90 */     Coding coding3 = coding1; while (true) {
/*  91 */       coding3 = coding3.setB(coding3.B() - 1);
/*  92 */       if (coding3.umax() < paramInt1)
/*     */         break; 
/*  94 */       coding2 = coding3;
/*     */     } 
/*  96 */     return coding2;
/*     */   }
/*     */   
/*     */   public void setFavoredCoding(CodingMethod paramCodingMethod) {
/* 100 */     this.favoredCoding = paramCodingMethod;
/*     */   }
/*     */   public void setTokenCoding(CodingMethod paramCodingMethod) {
/* 103 */     this.tokenCoding = paramCodingMethod;
/* 104 */     this.L = -1;
/* 105 */     if (paramCodingMethod instanceof Coding && this.fValues != null) {
/* 106 */       Coding coding = (Coding)paramCodingMethod;
/* 107 */       if (coding == fitTokenCoding(this.fVlen, coding.L()))
/* 108 */         this.L = coding.L(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUnfavoredCoding(CodingMethod paramCodingMethod) {
/* 113 */     this.unfavoredCoding = paramCodingMethod;
/*     */   }
/*     */   
/*     */   public int favoredValueMaxLength() {
/* 117 */     if (this.L == 0) {
/* 118 */       return Integer.MAX_VALUE;
/*     */     }
/* 120 */     return BandStructure.UNSIGNED5.setL(this.L).umax();
/*     */   }
/*     */   
/*     */   public void resortFavoredValues() {
/* 124 */     Coding coding = (Coding)this.tokenCoding;
/*     */     
/* 126 */     this.fValues = BandStructure.realloc(this.fValues, 1 + this.fVlen);
/*     */     
/* 128 */     int i = 1;
/* 129 */     for (byte b = 1; b <= coding.B(); b++) {
/* 130 */       int j = coding.byteMax(b);
/* 131 */       if (j > this.fVlen)
/* 132 */         j = this.fVlen; 
/* 133 */       if (j < coding.byteMin(b))
/*     */         break; 
/* 135 */       int k = i;
/* 136 */       int m = j + 1;
/* 137 */       if (m != k) {
/* 138 */         assert m > k : m + "!>" + k;
/*     */         
/* 140 */         assert coding.getLength(k) == b : b + " != len(" + k + ") == " + coding
/*     */           
/* 142 */           .getLength(k);
/* 143 */         assert coding.getLength(m - 1) == b : b + " != len(" + (m - true) + ") == " + coding
/*     */           
/* 145 */           .getLength(m - true);
/* 146 */         int n = k + (m - k) / 2;
/* 147 */         int i1 = k;
/*     */         
/* 149 */         int i2 = -1;
/* 150 */         int i3 = k;
/* 151 */         for (int i4 = k; i4 < m; i4++) {
/* 152 */           int i5 = this.fValues[i4];
/* 153 */           int i6 = this.vHist.getFrequency(i5);
/* 154 */           if (i2 != i6) {
/* 155 */             if (b == 1) {
/*     */ 
/*     */               
/* 158 */               Arrays.sort(this.fValues, i3, i4);
/* 159 */             } else if (Math.abs(i1 - n) > 
/* 160 */               Math.abs(i4 - n)) {
/*     */ 
/*     */               
/* 163 */               i1 = i4;
/*     */             } 
/* 165 */             i2 = i6;
/* 166 */             i3 = i4;
/*     */           } 
/*     */         } 
/* 169 */         if (b == 1) {
/* 170 */           Arrays.sort(this.fValues, i3, m);
/*     */         } else {
/*     */           
/* 173 */           Arrays.sort(this.fValues, k, i1);
/* 174 */           Arrays.sort(this.fValues, i1, m);
/*     */         } 
/* 176 */         assert coding.getLength(k) == coding.getLength(i1);
/* 177 */         assert coding.getLength(k) == coding.getLength(m - 1);
/* 178 */         i = j + 1;
/*     */       } 
/* 180 */     }  assert i == this.fValues.length;
/*     */ 
/*     */     
/* 183 */     this.symtab = null;
/*     */   }
/*     */   
/*     */   public int getToken(int paramInt) {
/* 187 */     if (this.symtab == null)
/* 188 */       this.symtab = makeSymtab(); 
/* 189 */     int i = Arrays.binarySearch(this.symtab, paramInt << 32L);
/* 190 */     if (i < 0) i = -i - 1; 
/* 191 */     if (i < this.symtab.length && paramInt == (int)(this.symtab[i] >>> 32L)) {
/* 192 */       return (int)this.symtab[i];
/*     */     }
/* 194 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[][] encodeValues(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 199 */     int[] arrayOfInt1 = new int[paramInt2 - paramInt1];
/* 200 */     byte b1 = 0;
/* 201 */     for (byte b2 = 0; b2 < arrayOfInt1.length; b2++) {
/* 202 */       int i = paramArrayOfint[paramInt1 + b2];
/* 203 */       int j = getToken(i);
/* 204 */       if (j != 0) {
/* 205 */         arrayOfInt1[b2] = j;
/*     */       } else {
/* 207 */         b1++;
/*     */       } 
/*     */     } 
/* 210 */     int[] arrayOfInt2 = new int[b1];
/* 211 */     b1 = 0;
/* 212 */     for (byte b3 = 0; b3 < arrayOfInt1.length; b3++) {
/* 213 */       if (arrayOfInt1[b3] == 0) {
/* 214 */         int i = paramArrayOfint[paramInt1 + b3];
/* 215 */         arrayOfInt2[b1++] = i;
/*     */       } 
/* 217 */     }  assert b1 == arrayOfInt2.length;
/* 218 */     return new int[][] { arrayOfInt1, arrayOfInt2 };
/*     */   }
/*     */   
/*     */   private long[] makeSymtab() {
/* 222 */     long[] arrayOfLong = new long[this.fVlen];
/* 223 */     for (byte b = 1; b <= this.fVlen; b++) {
/* 224 */       arrayOfLong[b - 1] = this.fValues[b] << 32L | b;
/*     */     }
/*     */     
/* 227 */     Arrays.sort(arrayOfLong);
/* 228 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   private Coding getTailCoding(CodingMethod paramCodingMethod) {
/* 232 */     while (paramCodingMethod instanceof AdaptiveCoding)
/* 233 */       paramCodingMethod = ((AdaptiveCoding)paramCodingMethod).tailCoding; 
/* 234 */     return (Coding)paramCodingMethod;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeArrayTo(OutputStream paramOutputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/* 239 */     int[][] arrayOfInt = encodeValues(paramArrayOfint, paramInt1, paramInt2);
/* 240 */     writeSequencesTo(paramOutputStream, arrayOfInt[0], arrayOfInt[1]);
/*     */   }
/*     */   void writeSequencesTo(OutputStream paramOutputStream, int[] paramArrayOfint1, int[] paramArrayOfint2) throws IOException {
/* 243 */     this.favoredCoding.writeArrayTo(paramOutputStream, this.fValues, 1, 1 + this.fVlen);
/* 244 */     getTailCoding(this.favoredCoding).writeTo(paramOutputStream, computeSentinelValue());
/* 245 */     this.tokenCoding.writeArrayTo(paramOutputStream, paramArrayOfint1, 0, paramArrayOfint1.length);
/* 246 */     if (paramArrayOfint2.length > 0)
/* 247 */       this.unfavoredCoding.writeArrayTo(paramOutputStream, paramArrayOfint2, 0, paramArrayOfint2.length); 
/*     */   }
/*     */   
/*     */   int computeSentinelValue() {
/* 251 */     Coding coding = getTailCoding(this.favoredCoding);
/* 252 */     if (coding.isDelta())
/*     */     {
/* 254 */       return 0;
/*     */     }
/*     */     
/* 257 */     int i = this.fValues[1];
/* 258 */     int j = i;
/*     */     
/* 260 */     for (byte b = 2; b <= this.fVlen; b++) {
/* 261 */       j = this.fValues[b];
/* 262 */       i = moreCentral(i, j);
/*     */     } 
/*     */     
/* 265 */     if (coding.getLength(i) <= coding.getLength(j)) {
/* 266 */       return i;
/*     */     }
/* 268 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readArrayFrom(InputStream paramInputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/* 274 */     setFavoredValues(readFavoredValuesFrom(paramInputStream, paramInt2 - paramInt1));
/*     */     
/* 276 */     this.tokenCoding.readArrayFrom(paramInputStream, paramArrayOfint, paramInt1, paramInt2);
/*     */     
/* 278 */     int i = 0, j = -1;
/* 279 */     byte b1 = 0;
/* 280 */     for (int k = paramInt1; k < paramInt2; k++) {
/* 281 */       int m = paramArrayOfint[k];
/* 282 */       if (m == 0) {
/*     */         
/* 284 */         if (j < 0) {
/* 285 */           i = k;
/*     */         } else {
/* 287 */           paramArrayOfint[j] = k;
/*     */         } 
/* 289 */         j = k;
/* 290 */         b1++;
/*     */       } else {
/* 292 */         paramArrayOfint[k] = this.fValues[m];
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     int[] arrayOfInt = new int[b1];
/* 297 */     if (b1 > 0)
/* 298 */       this.unfavoredCoding.readArrayFrom(paramInputStream, arrayOfInt, 0, b1); 
/* 299 */     for (byte b2 = 0; b2 < b1; b2++) {
/* 300 */       int m = paramArrayOfint[i];
/* 301 */       paramArrayOfint[i] = arrayOfInt[b2];
/* 302 */       i = m;
/*     */     } 
/*     */   }
/*     */   
/*     */   int[] readFavoredValuesFrom(InputStream paramInputStream, int paramInt) throws IOException {
/* 307 */     int[] arrayOfInt = new int[1000];
/*     */ 
/*     */ 
/*     */     
/* 311 */     HashSet<Integer> hashSet = null;
/* 312 */     assert (hashSet = new HashSet()) != null;
/* 313 */     byte b = 1;
/* 314 */     paramInt += b;
/* 315 */     int i = Integer.MIN_VALUE;
/*     */     
/* 317 */     int j = 0;
/* 318 */     CodingMethod codingMethod = this.favoredCoding;
/* 319 */     while (codingMethod instanceof AdaptiveCoding) {
/* 320 */       AdaptiveCoding adaptiveCoding = (AdaptiveCoding)codingMethod;
/* 321 */       int k = adaptiveCoding.headLength;
/* 322 */       while (b + k > arrayOfInt.length) {
/* 323 */         arrayOfInt = BandStructure.realloc(arrayOfInt);
/*     */       }
/* 325 */       int m = b + k;
/* 326 */       adaptiveCoding.headCoding.readArrayFrom(paramInputStream, arrayOfInt, b, m);
/* 327 */       while (b < m) {
/* 328 */         int n = arrayOfInt[b++];
/* 329 */         assert hashSet.add(Integer.valueOf(n));
/* 330 */         assert b <= paramInt;
/* 331 */         j = n;
/* 332 */         i = moreCentral(i, n);
/*     */       } 
/*     */       
/* 335 */       codingMethod = adaptiveCoding.tailCoding;
/*     */     } 
/* 337 */     Coding coding = (Coding)codingMethod;
/* 338 */     if (coding.isDelta()) {
/* 339 */       long l = 0L; while (true) {
/*     */         int k;
/* 341 */         l += coding.readFrom(paramInputStream);
/*     */         
/* 343 */         if (coding.isSubrange()) {
/* 344 */           k = coding.reduceToUnsignedRange(l);
/*     */         } else {
/* 346 */           k = (int)l;
/* 347 */         }  l = k;
/* 348 */         if (b > 1 && (k == j || k == i))
/*     */           break; 
/* 350 */         if (b == arrayOfInt.length)
/* 351 */           arrayOfInt = BandStructure.realloc(arrayOfInt); 
/* 352 */         arrayOfInt[b++] = k;
/* 353 */         assert hashSet.add(Integer.valueOf(k));
/* 354 */         assert b <= paramInt;
/* 355 */         j = k;
/* 356 */         i = moreCentral(i, k);
/*     */       } 
/*     */     } else {
/*     */       
/*     */       while (true) {
/* 361 */         int k = coding.readFrom(paramInputStream);
/* 362 */         if (b > 1 && (k == j || k == i))
/*     */           break; 
/* 364 */         if (b == arrayOfInt.length)
/* 365 */           arrayOfInt = BandStructure.realloc(arrayOfInt); 
/* 366 */         arrayOfInt[b++] = k;
/* 367 */         assert hashSet.add(Integer.valueOf(k));
/* 368 */         assert b <= paramInt;
/* 369 */         j = k;
/* 370 */         i = moreCentral(i, k);
/*     */       } 
/*     */     } 
/*     */     
/* 374 */     return BandStructure.realloc(arrayOfInt, b);
/*     */   }
/*     */   
/*     */   private static int moreCentral(int paramInt1, int paramInt2) {
/* 378 */     int i = paramInt1 >> 31 ^ paramInt1 << 1;
/* 379 */     int j = paramInt2 >> 31 ^ paramInt2 << 1;
/*     */     
/* 381 */     i -= Integer.MIN_VALUE;
/* 382 */     j -= Integer.MIN_VALUE;
/* 383 */     int k = (i < j) ? paramInt1 : paramInt2;
/*     */     
/* 385 */     assert k == moreCentralSlow(paramInt1, paramInt2);
/* 386 */     return k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int moreCentralSlow(int paramInt1, int paramInt2) {
/* 397 */     int i = paramInt1;
/* 398 */     if (i < 0) i = -i; 
/* 399 */     if (i < 0) return paramInt2; 
/* 400 */     int j = paramInt2;
/* 401 */     if (j < 0) j = -j; 
/* 402 */     if (j < 0) return paramInt1; 
/* 403 */     if (i < j) return paramInt1; 
/* 404 */     if (i > j) return paramInt2;
/*     */     
/* 406 */     return (paramInt1 < paramInt2) ? paramInt1 : paramInt2;
/*     */   }
/*     */   
/* 409 */   static final int[] LValuesCoded = new int[] { -1, 4, 8, 16, 32, 64, 128, 192, 224, 240, 248, 252 };
/*     */ 
/*     */   
/*     */   public byte[] getMetaCoding(Coding paramCoding) {
/* 413 */     int i = this.fVlen;
/* 414 */     byte b1 = 0;
/* 415 */     if (this.tokenCoding instanceof Coding) {
/* 416 */       Coding coding = (Coding)this.tokenCoding;
/* 417 */       if (coding.B() == 1) {
/* 418 */         b1 = 1;
/* 419 */       } else if (this.L >= 0) {
/* 420 */         assert this.L == coding.L();
/* 421 */         for (byte b = 1; b < LValuesCoded.length; b++) {
/* 422 */           if (LValuesCoded[b] == this.L) { b1 = b; break; }
/*     */         
/*     */         } 
/*     */       } 
/* 426 */     }  CodingMethod codingMethod = null;
/* 427 */     if (b1 != 0 && this.tokenCoding == fitTokenCoding(this.fVlen, this.L))
/*     */     {
/* 429 */       codingMethod = this.tokenCoding;
/*     */     }
/* 431 */     byte b2 = (this.favoredCoding == paramCoding) ? 1 : 0;
/* 432 */     byte b3 = (this.unfavoredCoding == paramCoding || this.unfavoredCoding == null) ? 1 : 0;
/* 433 */     boolean bool = (this.tokenCoding == codingMethod) ? true : false;
/* 434 */     byte b4 = (bool == true) ? b1 : 0;
/* 435 */     assert bool == (b4 ? true : false);
/* 436 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
/* 437 */     byteArrayOutputStream.write(141 + b2 + 2 * b3 + 4 * b4);
/*     */     try {
/* 439 */       if (b2 == 0) byteArrayOutputStream.write(this.favoredCoding.getMetaCoding(paramCoding)); 
/* 440 */       if (!bool) byteArrayOutputStream.write(this.tokenCoding.getMetaCoding(paramCoding)); 
/* 441 */       if (b3 == 0) byteArrayOutputStream.write(this.unfavoredCoding.getMetaCoding(paramCoding)); 
/* 442 */     } catch (IOException iOException) {
/* 443 */       throw new RuntimeException(iOException);
/*     */     } 
/* 445 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */   public static int parseMetaCoding(byte[] paramArrayOfbyte, int paramInt, Coding paramCoding, CodingMethod[] paramArrayOfCodingMethod) {
/* 448 */     int i = paramArrayOfbyte[paramInt++] & 0xFF;
/* 449 */     if (i < 141 || i >= 189) return paramInt - 1; 
/* 450 */     i -= 141;
/* 451 */     int j = i % 2;
/* 452 */     int k = i / 2 % 2;
/* 453 */     int m = i / 4;
/* 454 */     boolean bool = (m > 0) ? true : false;
/* 455 */     int n = LValuesCoded[m];
/* 456 */     CodingMethod[] arrayOfCodingMethod1 = { paramCoding }, arrayOfCodingMethod2 = { null }, arrayOfCodingMethod3 = { paramCoding };
/* 457 */     if (j == 0)
/* 458 */       paramInt = BandStructure.parseMetaCoding(paramArrayOfbyte, paramInt, paramCoding, arrayOfCodingMethod1); 
/* 459 */     if (!bool)
/* 460 */       paramInt = BandStructure.parseMetaCoding(paramArrayOfbyte, paramInt, paramCoding, arrayOfCodingMethod2); 
/* 461 */     if (k == 0)
/* 462 */       paramInt = BandStructure.parseMetaCoding(paramArrayOfbyte, paramInt, paramCoding, arrayOfCodingMethod3); 
/* 463 */     PopulationCoding populationCoding = new PopulationCoding();
/* 464 */     populationCoding.L = n;
/* 465 */     populationCoding.favoredCoding = arrayOfCodingMethod1[0];
/* 466 */     populationCoding.tokenCoding = arrayOfCodingMethod2[0];
/* 467 */     populationCoding.unfavoredCoding = arrayOfCodingMethod3[0];
/* 468 */     paramArrayOfCodingMethod[0] = populationCoding;
/* 469 */     return paramInt;
/*     */   }
/*     */   
/*     */   private String keyString(CodingMethod paramCodingMethod) {
/* 473 */     if (paramCodingMethod instanceof Coding)
/* 474 */       return ((Coding)paramCodingMethod).keyString(); 
/* 475 */     if (paramCodingMethod == null)
/* 476 */       return "none"; 
/* 477 */     return paramCodingMethod.toString();
/*     */   }
/*     */   public String toString() {
/* 480 */     PropMap propMap = Utils.currentPropMap();
/*     */ 
/*     */     
/* 483 */     boolean bool = (propMap != null && propMap.getBoolean("com.sun.java.util.jar.pack.verbose.pop")) ? true : false;
/* 484 */     StringBuilder stringBuilder = new StringBuilder(100);
/* 485 */     stringBuilder.append("pop(").append("fVlen=").append(this.fVlen);
/* 486 */     if (bool && this.fValues != null) {
/* 487 */       stringBuilder.append(" fV=[");
/* 488 */       for (byte b = 1; b <= this.fVlen; b++) {
/* 489 */         stringBuilder.append((b == 1) ? "" : ",").append(this.fValues[b]);
/*     */       }
/* 491 */       stringBuilder.append(";").append(computeSentinelValue());
/* 492 */       stringBuilder.append("]");
/*     */     } 
/* 494 */     stringBuilder.append(" fc=").append(keyString(this.favoredCoding));
/* 495 */     stringBuilder.append(" tc=").append(keyString(this.tokenCoding));
/* 496 */     stringBuilder.append(" uc=").append(keyString(this.unfavoredCoding));
/* 497 */     stringBuilder.append(")");
/* 498 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/PopulationCoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */