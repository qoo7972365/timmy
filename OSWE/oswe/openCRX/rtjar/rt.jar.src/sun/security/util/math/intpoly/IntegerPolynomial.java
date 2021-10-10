/*     */ package sun.security.util.math.intpoly;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Arrays;
/*     */ import sun.security.util.math.ImmutableIntegerModuloP;
/*     */ import sun.security.util.math.IntegerFieldModuloP;
/*     */ import sun.security.util.math.IntegerModuloP;
/*     */ import sun.security.util.math.MutableIntegerModuloP;
/*     */ import sun.security.util.math.SmallValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class IntegerPolynomial
/*     */   implements IntegerFieldModuloP
/*     */ {
/*  66 */   protected static final BigInteger TWO = BigInteger.valueOf(2L);
/*     */ 
/*     */   
/*     */   protected final int numLimbs;
/*     */ 
/*     */   
/*     */   private final BigInteger modulus;
/*     */ 
/*     */   
/*     */   protected final int bitsPerLimb;
/*     */ 
/*     */   
/*     */   private final long[] posModLimbs;
/*     */ 
/*     */   
/*     */   private final int maxAdds;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void multByInt(long[] paramArrayOflong, long paramLong) {
/*  86 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/*  87 */       paramArrayOflong[b] = paramArrayOflong[b] * paramLong;
/*     */     }
/*  89 */     reduce(paramArrayOflong);
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
/*     */   IntegerPolynomial(int paramInt1, int paramInt2, int paramInt3, BigInteger paramBigInteger) {
/* 114 */     this.numLimbs = paramInt2;
/* 115 */     this.modulus = paramBigInteger;
/* 116 */     this.bitsPerLimb = paramInt1;
/* 117 */     this.maxAdds = paramInt3;
/*     */     
/* 119 */     this.posModLimbs = setPosModLimbs();
/*     */   }
/*     */   
/*     */   private long[] setPosModLimbs() {
/* 123 */     long[] arrayOfLong = new long[this.numLimbs];
/* 124 */     setLimbsValuePositive(this.modulus, arrayOfLong);
/* 125 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   protected int getNumLimbs() {
/* 129 */     return this.numLimbs;
/*     */   }
/*     */   
/*     */   public int getMaxAdds() {
/* 133 */     return this.maxAdds;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getSize() {
/* 138 */     return this.modulus;
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableElement get0() {
/* 143 */     return new ImmutableElement(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableElement get1() {
/* 148 */     return new ImmutableElement(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableElement getElement(BigInteger paramBigInteger) {
/* 153 */     return new ImmutableElement(paramBigInteger);
/*     */   }
/*     */ 
/*     */   
/*     */   public SmallValue getSmallValue(int paramInt) {
/* 158 */     int i = 1 << this.bitsPerLimb - 1;
/* 159 */     if (Math.abs(paramInt) >= i) {
/* 160 */       throw new IllegalArgumentException("max magnitude is " + i);
/*     */     }
/*     */     
/* 163 */     return new Limb(paramInt);
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
/*     */   protected void encode(ByteBuffer paramByteBuffer, int paramInt, byte paramByte, long[] paramArrayOflong) {
/* 176 */     int i = 32 - Integer.numberOfLeadingZeros(paramByte);
/* 177 */     int j = 8 * paramInt + i;
/* 178 */     int k = (j + this.bitsPerLimb - 1) / this.bitsPerLimb;
/* 179 */     if (k > this.numLimbs) {
/* 180 */       long[] arrayOfLong = new long[k];
/* 181 */       encodeSmall(paramByteBuffer, paramInt, paramByte, arrayOfLong);
/*     */       
/* 183 */       System.arraycopy(arrayOfLong, 0, paramArrayOflong, 0, paramArrayOflong.length);
/*     */     } else {
/* 185 */       encodeSmall(paramByteBuffer, paramInt, paramByte, paramArrayOflong);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeSmall(ByteBuffer paramByteBuffer, int paramInt, byte paramByte, long[] paramArrayOflong) {
/* 192 */     byte b1 = 0;
/* 193 */     long l = 0L;
/* 194 */     int i = 0;
/* 195 */     for (byte b2 = 0; b2 < paramInt; b2++) {
/* 196 */       long l1 = (paramByteBuffer.get() & 0xFF);
/*     */       
/* 198 */       if (i + 8 >= this.bitsPerLimb) {
/* 199 */         int j = this.bitsPerLimb - i;
/* 200 */         l += (l1 & (255 >> 8 - j)) << i;
/* 201 */         paramArrayOflong[b1++] = l;
/* 202 */         l = l1 >> j;
/* 203 */         i = 8 - j;
/*     */       } else {
/*     */         
/* 206 */         l += l1 << i;
/* 207 */         i += 8;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 212 */     if (paramByte != 0) {
/* 213 */       long l1 = (paramByte & 0xFF);
/* 214 */       if (i + 8 >= this.bitsPerLimb) {
/* 215 */         int j = this.bitsPerLimb - i;
/* 216 */         l += (l1 & (255 >> 8 - j)) << i;
/* 217 */         paramArrayOflong[b1++] = l;
/* 218 */         l = l1 >> j;
/*     */       } else {
/*     */         
/* 221 */         l += l1 << i;
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     if (b1 < paramArrayOflong.length) {
/* 226 */       paramArrayOflong[b1++] = l;
/*     */     }
/* 228 */     Arrays.fill(paramArrayOflong, b1, paramArrayOflong.length, 0L);
/*     */     
/* 230 */     postEncodeCarry(paramArrayOflong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte, long[] paramArrayOflong) {
/* 236 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte, paramInt1, paramInt2);
/* 237 */     byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 238 */     encode(byteBuffer, paramInt2, paramByte, paramArrayOflong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postEncodeCarry(long[] paramArrayOflong) {
/* 244 */     reduce(paramArrayOflong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableElement getElement(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte) {
/* 250 */     long[] arrayOfLong = new long[this.numLimbs];
/*     */     
/* 252 */     encode(paramArrayOfbyte, paramInt1, paramInt2, paramByte, arrayOfLong);
/*     */     
/* 254 */     return new ImmutableElement(arrayOfLong, 0);
/*     */   }
/*     */   
/*     */   protected BigInteger evaluate(long[] paramArrayOflong) {
/* 258 */     BigInteger bigInteger = BigInteger.ZERO;
/* 259 */     for (int i = paramArrayOflong.length - 1; i >= 0; i--)
/*     */     {
/* 261 */       bigInteger = bigInteger.shiftLeft(this.bitsPerLimb).add(BigInteger.valueOf(paramArrayOflong[i]));
/*     */     }
/* 263 */     return bigInteger.mod(this.modulus);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long carryValue(long paramLong) {
/* 270 */     return paramLong + (1 << this.bitsPerLimb - 1) >> this.bitsPerLimb;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void carry(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 275 */     for (int i = paramInt1; i < paramInt2; i++) {
/*     */       
/* 277 */       long l = carryOut(paramArrayOflong, i);
/* 278 */       paramArrayOflong[i + 1] = paramArrayOflong[i + 1] + l;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void carry(long[] paramArrayOflong) {
/* 284 */     carry(paramArrayOflong, 0, paramArrayOflong.length - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long carryOut(long[] paramArrayOflong, int paramInt) {
/* 291 */     long l = carryValue(paramArrayOflong[paramInt]);
/* 292 */     paramArrayOflong[paramInt] = paramArrayOflong[paramInt] - (l << this.bitsPerLimb);
/* 293 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLimbsValue(BigInteger paramBigInteger, long[] paramArrayOflong) {
/* 298 */     setLimbsValuePositive(paramBigInteger, paramArrayOflong);
/* 299 */     carry(paramArrayOflong);
/*     */   }
/*     */   
/*     */   protected void setLimbsValuePositive(BigInteger paramBigInteger, long[] paramArrayOflong) {
/* 303 */     BigInteger bigInteger = BigInteger.valueOf((1 << this.bitsPerLimb));
/* 304 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 305 */       paramArrayOflong[b] = paramBigInteger.mod(bigInteger).longValue();
/* 306 */       paramBigInteger = paramBigInteger.shiftRight(this.bitsPerLimb);
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
/*     */   protected void finalReduce(long[] paramArrayOflong) {
/*     */     int i;
/* 338 */     for (i = 0; i < 2; i++) {
/*     */ 
/*     */       
/* 341 */       finalCarryReduceLast(paramArrayOflong);
/*     */ 
/*     */       
/* 344 */       long l = 0L;
/* 345 */       for (byte b = 0; b < this.numLimbs - 1; b++) {
/* 346 */         paramArrayOflong[b] = paramArrayOflong[b] + l;
/* 347 */         l = paramArrayOflong[b] >> this.bitsPerLimb;
/* 348 */         paramArrayOflong[b] = paramArrayOflong[b] - (l << this.bitsPerLimb);
/*     */       } 
/* 350 */       paramArrayOflong[this.numLimbs - 1] = paramArrayOflong[this.numLimbs - 1] + l;
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
/* 361 */     i = 1;
/* 362 */     long[] arrayOfLong = new long[this.numLimbs];
/* 363 */     for (int j = this.numLimbs - 1; j >= 0; j--) {
/* 364 */       arrayOfLong[j] = paramArrayOflong[j] - this.posModLimbs[j];
/*     */ 
/*     */       
/* 367 */       i *= (int)(arrayOfLong[j] >> 63L) + 1;
/*     */     } 
/* 369 */     conditionalSwap(i, paramArrayOflong, arrayOfLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(long[] paramArrayOflong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 379 */     byte b1 = 0;
/* 380 */     long l = paramArrayOflong[b1++];
/* 381 */     int i = 0;
/* 382 */     for (byte b2 = 0; b2 < paramInt2; b2++) {
/*     */       
/* 384 */       int j = b2 + paramInt1;
/* 385 */       if (i + 8 >= this.bitsPerLimb) {
/* 386 */         paramArrayOfbyte[j] = (byte)(int)l;
/* 387 */         l = 0L;
/* 388 */         if (b1 < paramArrayOflong.length) {
/* 389 */           l = paramArrayOflong[b1++];
/*     */         }
/* 391 */         int k = this.bitsPerLimb - i;
/* 392 */         int m = 8 - k;
/*     */         
/* 394 */         paramArrayOfbyte[j] = (byte)(int)(paramArrayOfbyte[j] + ((l & (255 >> k)) << k));
/*     */         
/* 396 */         l >>= m;
/* 397 */         i = m;
/*     */       } else {
/* 399 */         paramArrayOfbyte[j] = (byte)(int)l;
/* 400 */         l >>= 8L;
/* 401 */         i += 8;
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
/*     */   protected void addLimbs(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 413 */     for (byte b = 0; b < paramArrayOflong3.length; b++) {
/* 414 */       paramArrayOflong3[b] = paramArrayOflong1[b] + paramArrayOflong2[b];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void conditionalAssign(int paramInt, long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 425 */     int i = 0 - paramInt;
/* 426 */     for (byte b = 0; b < paramArrayOflong1.length; b++) {
/* 427 */       long l = i & (paramArrayOflong1[b] ^ paramArrayOflong2[b]);
/* 428 */       paramArrayOflong1[b] = l ^ paramArrayOflong1[b];
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
/*     */   protected static void conditionalSwap(int paramInt, long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 440 */     int i = 0 - paramInt;
/* 441 */     for (byte b = 0; b < paramArrayOflong1.length; b++) {
/* 442 */       long l = i & (paramArrayOflong1[b] ^ paramArrayOflong2[b]);
/* 443 */       paramArrayOflong1[b] = l ^ paramArrayOflong1[b];
/* 444 */       paramArrayOflong2[b] = l ^ paramArrayOflong2[b];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void limbsToByteArray(long[] paramArrayOflong, byte[] paramArrayOfbyte) {
/* 453 */     long[] arrayOfLong = (long[])paramArrayOflong.clone();
/* 454 */     finalReduce(arrayOfLong);
/*     */     
/* 456 */     decode(arrayOfLong, paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addLimbsModPowerTwo(long[] paramArrayOflong1, long[] paramArrayOflong2, byte[] paramArrayOfbyte) {
/* 467 */     long[] arrayOfLong1 = (long[])paramArrayOflong2.clone();
/* 468 */     long[] arrayOfLong2 = (long[])paramArrayOflong1.clone();
/* 469 */     finalReduce(arrayOfLong1);
/* 470 */     finalReduce(arrayOfLong2);
/*     */     
/* 472 */     addLimbs(arrayOfLong2, arrayOfLong1, arrayOfLong2);
/*     */ 
/*     */     
/* 475 */     long l = 0L;
/* 476 */     for (byte b = 0; b < this.numLimbs; b++) {
/* 477 */       arrayOfLong2[b] = arrayOfLong2[b] + l;
/* 478 */       l = arrayOfLong2[b] >> this.bitsPerLimb;
/* 479 */       arrayOfLong2[b] = arrayOfLong2[b] - (l << this.bitsPerLimb);
/*     */     } 
/*     */     
/* 482 */     decode(arrayOfLong2, paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   protected abstract void reduce(long[] paramArrayOflong);
/*     */   protected abstract void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3);
/*     */   protected abstract void square(long[] paramArrayOflong1, long[] paramArrayOflong2);
/*     */   protected abstract void finalCarryReduceLast(long[] paramArrayOflong);
/*     */   private abstract class Element implements IntegerModuloP { protected long[] limbs;
/*     */     
/*     */     public Element(BigInteger param1BigInteger) {
/* 491 */       this.limbs = new long[IntegerPolynomial.this.numLimbs];
/* 492 */       setValue(param1BigInteger);
/*     */     }
/*     */     protected int numAdds;
/*     */     public Element(boolean param1Boolean) {
/* 496 */       this.limbs = new long[IntegerPolynomial.this.numLimbs];
/* 497 */       this.limbs[0] = param1Boolean ? 1L : 0L;
/* 498 */       this.numAdds = 0;
/*     */     }
/*     */     
/*     */     private Element(long[] param1ArrayOflong, int param1Int) {
/* 502 */       this.limbs = param1ArrayOflong;
/* 503 */       this.numAdds = param1Int;
/*     */     }
/*     */     
/*     */     private void setValue(BigInteger param1BigInteger) {
/* 507 */       IntegerPolynomial.this.setLimbsValue(param1BigInteger, this.limbs);
/* 508 */       this.numAdds = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntegerFieldModuloP getField() {
/* 513 */       return IntegerPolynomial.this;
/*     */     }
/*     */ 
/*     */     
/*     */     public BigInteger asBigInteger() {
/* 518 */       return IntegerPolynomial.this.evaluate(this.limbs);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntegerPolynomial.MutableElement mutable() {
/* 523 */       return new IntegerPolynomial.MutableElement((long[])this.limbs.clone(), this.numAdds);
/*     */     }
/*     */     
/*     */     protected boolean isSummand() {
/* 527 */       return (this.numAdds < IntegerPolynomial.this.maxAdds);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntegerPolynomial.ImmutableElement add(IntegerModuloP param1IntegerModuloP) {
/* 533 */       Element element = (Element)param1IntegerModuloP;
/* 534 */       if (!isSummand() || !element.isSummand()) {
/* 535 */         throw new ArithmeticException("Not a valid summand");
/*     */       }
/*     */       
/* 538 */       long[] arrayOfLong = new long[this.limbs.length]; int i;
/* 539 */       for (i = 0; i < this.limbs.length; i++) {
/* 540 */         arrayOfLong[i] = this.limbs[i] + element.limbs[i];
/*     */       }
/*     */       
/* 543 */       i = Math.max(this.numAdds, element.numAdds) + 1;
/* 544 */       return new IntegerPolynomial.ImmutableElement(arrayOfLong, i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntegerPolynomial.ImmutableElement additiveInverse() {
/* 550 */       long[] arrayOfLong = new long[this.limbs.length];
/* 551 */       for (byte b = 0; b < this.limbs.length; b++) {
/* 552 */         arrayOfLong[b] = -this.limbs[b];
/*     */       }
/*     */       
/* 555 */       return new IntegerPolynomial.ImmutableElement(arrayOfLong, this.numAdds);
/*     */     }
/*     */ 
/*     */     
/*     */     protected long[] cloneLow(long[] param1ArrayOflong) {
/* 560 */       long[] arrayOfLong = new long[IntegerPolynomial.this.numLimbs];
/* 561 */       copyLow(param1ArrayOflong, arrayOfLong);
/* 562 */       return arrayOfLong;
/*     */     }
/*     */     protected void copyLow(long[] param1ArrayOflong1, long[] param1ArrayOflong2) {
/* 565 */       System.arraycopy(param1ArrayOflong1, 0, param1ArrayOflong2, 0, param1ArrayOflong2.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntegerPolynomial.ImmutableElement multiply(IntegerModuloP param1IntegerModuloP) {
/* 571 */       Element element = (Element)param1IntegerModuloP;
/*     */       
/* 573 */       long[] arrayOfLong = new long[this.limbs.length];
/* 574 */       IntegerPolynomial.this.mult(this.limbs, element.limbs, arrayOfLong);
/* 575 */       return new IntegerPolynomial.ImmutableElement(arrayOfLong, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntegerPolynomial.ImmutableElement square() {
/* 580 */       long[] arrayOfLong = new long[this.limbs.length];
/* 581 */       IntegerPolynomial.this.square(this.limbs, arrayOfLong);
/* 582 */       return new IntegerPolynomial.ImmutableElement(arrayOfLong, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addModPowerTwo(IntegerModuloP param1IntegerModuloP, byte[] param1ArrayOfbyte) {
/* 587 */       Element element = (Element)param1IntegerModuloP;
/* 588 */       if (!isSummand() || !element.isSummand()) {
/* 589 */         throw new ArithmeticException("Not a valid summand");
/*     */       }
/* 591 */       IntegerPolynomial.this.addLimbsModPowerTwo(this.limbs, element.limbs, param1ArrayOfbyte);
/*     */     }
/*     */     
/*     */     public void asByteArray(byte[] param1ArrayOfbyte) {
/* 595 */       if (!isSummand()) {
/* 596 */         throw new ArithmeticException("Not a valid summand");
/*     */       }
/* 598 */       IntegerPolynomial.this.limbsToByteArray(this.limbs, param1ArrayOfbyte);
/*     */     } }
/*     */ 
/*     */   
/*     */   protected class MutableElement
/*     */     extends Element
/*     */     implements MutableIntegerModuloP {
/*     */     protected MutableElement(long[] param1ArrayOflong, int param1Int) {
/* 606 */       super(param1ArrayOflong, param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntegerPolynomial.ImmutableElement fixed() {
/* 611 */       return new IntegerPolynomial.ImmutableElement((long[])this.limbs.clone(), this.numAdds);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void conditionalSet(IntegerModuloP param1IntegerModuloP, int param1Int) {
/* 617 */       IntegerPolynomial.Element element = (IntegerPolynomial.Element)param1IntegerModuloP;
/*     */       
/* 619 */       IntegerPolynomial.conditionalAssign(param1Int, this.limbs, element.limbs);
/* 620 */       this.numAdds = element.numAdds;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void conditionalSwapWith(MutableIntegerModuloP param1MutableIntegerModuloP, int param1Int) {
/* 626 */       MutableElement mutableElement = (MutableElement)param1MutableIntegerModuloP;
/*     */       
/* 628 */       IntegerPolynomial.conditionalSwap(param1Int, this.limbs, mutableElement.limbs);
/* 629 */       int i = this.numAdds;
/* 630 */       this.numAdds = mutableElement.numAdds;
/* 631 */       mutableElement.numAdds = i;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableElement setValue(IntegerModuloP param1IntegerModuloP) {
/* 637 */       IntegerPolynomial.Element element = (IntegerPolynomial.Element)param1IntegerModuloP;
/*     */       
/* 639 */       System.arraycopy(element.limbs, 0, this.limbs, 0, element.limbs.length);
/* 640 */       this.numAdds = element.numAdds;
/* 641 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableElement setValue(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, byte param1Byte) {
/* 648 */       IntegerPolynomial.this.encode(param1ArrayOfbyte, param1Int1, param1Int2, param1Byte, this.limbs);
/* 649 */       this.numAdds = 0;
/*     */       
/* 651 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableElement setValue(ByteBuffer param1ByteBuffer, int param1Int, byte param1Byte) {
/* 658 */       IntegerPolynomial.this.encode(param1ByteBuffer, param1Int, param1Byte, this.limbs);
/* 659 */       this.numAdds = 0;
/*     */       
/* 661 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableElement setProduct(IntegerModuloP param1IntegerModuloP) {
/* 666 */       IntegerPolynomial.Element element = (IntegerPolynomial.Element)param1IntegerModuloP;
/* 667 */       IntegerPolynomial.this.mult(this.limbs, element.limbs, this.limbs);
/* 668 */       this.numAdds = 0;
/* 669 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableElement setProduct(SmallValue param1SmallValue) {
/* 674 */       int i = ((IntegerPolynomial.Limb)param1SmallValue).value;
/* 675 */       IntegerPolynomial.this.multByInt(this.limbs, i);
/* 676 */       this.numAdds = 0;
/* 677 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableElement setSum(IntegerModuloP param1IntegerModuloP) {
/* 683 */       IntegerPolynomial.Element element = (IntegerPolynomial.Element)param1IntegerModuloP;
/* 684 */       if (!isSummand() || !element.isSummand()) {
/* 685 */         throw new ArithmeticException("Not a valid summand");
/*     */       }
/*     */       
/* 688 */       for (byte b = 0; b < this.limbs.length; b++) {
/* 689 */         this.limbs[b] = this.limbs[b] + element.limbs[b];
/*     */       }
/*     */       
/* 692 */       this.numAdds = Math.max(this.numAdds, element.numAdds) + 1;
/* 693 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableElement setDifference(IntegerModuloP param1IntegerModuloP) {
/* 699 */       IntegerPolynomial.Element element = (IntegerPolynomial.Element)param1IntegerModuloP;
/* 700 */       if (!isSummand() || !element.isSummand()) {
/* 701 */         throw new ArithmeticException("Not a valid summand");
/*     */       }
/*     */       
/* 704 */       for (byte b = 0; b < this.limbs.length; b++) {
/* 705 */         this.limbs[b] = this.limbs[b] - element.limbs[b];
/*     */       }
/*     */       
/* 708 */       this.numAdds = Math.max(this.numAdds, element.numAdds) + 1;
/* 709 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableElement setSquare() {
/* 714 */       IntegerPolynomial.this.square(this.limbs, this.limbs);
/* 715 */       this.numAdds = 0;
/* 716 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableElement setAdditiveInverse() {
/* 722 */       for (byte b = 0; b < this.limbs.length; b++) {
/* 723 */         this.limbs[b] = -this.limbs[b];
/*     */       }
/* 725 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableElement setReduced() {
/* 731 */       IntegerPolynomial.this.reduce(this.limbs);
/* 732 */       this.numAdds = 0;
/* 733 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   class ImmutableElement
/*     */     extends Element implements ImmutableIntegerModuloP {
/*     */     protected ImmutableElement(BigInteger param1BigInteger) {
/* 740 */       super(param1BigInteger);
/*     */     }
/*     */     
/*     */     protected ImmutableElement(boolean param1Boolean) {
/* 744 */       super(param1Boolean);
/*     */     }
/*     */     
/*     */     protected ImmutableElement(long[] param1ArrayOflong, int param1Int) {
/* 748 */       super(param1ArrayOflong, param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     public ImmutableElement fixed() {
/* 753 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   class Limb
/*     */     implements SmallValue {
/*     */     int value;
/*     */     
/*     */     Limb(int param1Int) {
/* 762 */       this.value = param1Int;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/IntegerPolynomial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */