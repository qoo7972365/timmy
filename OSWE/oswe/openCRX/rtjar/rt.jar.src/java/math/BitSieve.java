/*     */ package java.math;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BitSieve
/*     */ {
/*     */   private long[] bits;
/*     */   private int length;
/*  62 */   private static BitSieve smallSieve = new BitSieve();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BitSieve() {
/*  76 */     this.length = 9600;
/*  77 */     this.bits = new long[unitIndex(this.length - 1) + 1];
/*     */ 
/*     */     
/*  80 */     set(0);
/*  81 */     int i = 1;
/*  82 */     int j = 3;
/*     */ 
/*     */     
/*     */     do {
/*  86 */       sieveSingle(this.length, i + j, j);
/*  87 */       i = sieveSearch(this.length, i + 1);
/*  88 */       j = 2 * i + 1;
/*  89 */     } while (i > 0 && j < this.length);
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
/*     */   BitSieve(BigInteger paramBigInteger, int paramInt) {
/* 105 */     this.bits = new long[unitIndex(paramInt - 1) + 1];
/* 106 */     this.length = paramInt;
/* 107 */     int i = 0;
/*     */     
/* 109 */     int j = smallSieve.sieveSearch(smallSieve.length, i);
/* 110 */     int k = j * 2 + 1;
/*     */ 
/*     */     
/* 113 */     MutableBigInteger mutableBigInteger1 = new MutableBigInteger(paramBigInteger);
/* 114 */     MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
/*     */     
/*     */     do {
/* 117 */       i = mutableBigInteger1.divideOneWord(k, mutableBigInteger2);
/*     */ 
/*     */       
/* 120 */       i = k - i;
/* 121 */       if (i % 2 == 0)
/* 122 */         i += k; 
/* 123 */       sieveSingle(paramInt, (i - 1) / 2, k);
/*     */ 
/*     */       
/* 126 */       j = smallSieve.sieveSearch(smallSieve.length, j + 1);
/* 127 */       k = j * 2 + 1;
/* 128 */     } while (j > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int unitIndex(int paramInt) {
/* 135 */     return paramInt >>> 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long bit(int paramInt) {
/* 142 */     return 1L << (paramInt & 0x3F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean get(int paramInt) {
/* 149 */     int i = unitIndex(paramInt);
/* 150 */     return ((this.bits[i] & bit(paramInt)) != 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void set(int paramInt) {
/* 157 */     int i = unitIndex(paramInt);
/* 158 */     this.bits[i] = this.bits[i] | bit(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int sieveSearch(int paramInt1, int paramInt2) {
/* 167 */     if (paramInt2 >= paramInt1) {
/* 168 */       return -1;
/*     */     }
/* 170 */     int i = paramInt2;
/*     */     while (true) {
/* 172 */       if (!get(i))
/* 173 */         return i; 
/* 174 */       i++;
/* 175 */       if (i >= paramInt1 - 1) {
/* 176 */         return -1;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sieveSingle(int paramInt1, int paramInt2, int paramInt3) {
/* 185 */     while (paramInt2 < paramInt1) {
/* 186 */       set(paramInt2);
/* 187 */       paramInt2 += paramInt3;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BigInteger retrieve(BigInteger paramBigInteger, int paramInt, Random paramRandom) {
/* 196 */     boolean bool = true;
/* 197 */     for (byte b = 0; b < this.bits.length; b++) {
/* 198 */       long l = this.bits[b] ^ 0xFFFFFFFFFFFFFFFFL;
/* 199 */       for (byte b1 = 0; b1 < 64; b1++) {
/* 200 */         if ((l & 0x1L) == 1L) {
/* 201 */           BigInteger bigInteger = paramBigInteger.add(
/* 202 */               BigInteger.valueOf(bool));
/* 203 */           if (bigInteger.primeToCertainty(paramInt, paramRandom))
/* 204 */             return bigInteger; 
/*     */         } 
/* 206 */         l >>>= 1L;
/* 207 */         bool += true;
/*     */       } 
/*     */     } 
/* 210 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/math/BitSieve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */