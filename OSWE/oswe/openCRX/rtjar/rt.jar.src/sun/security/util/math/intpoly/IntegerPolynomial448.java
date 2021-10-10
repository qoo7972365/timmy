/*     */ package sun.security.util.math.intpoly;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntegerPolynomial448
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   private static final int POWER = 448;
/*     */   private static final int NUM_LIMBS = 16;
/*     */   private static final int BITS_PER_LIMB = 28;
/*  40 */   public static final BigInteger MODULUS = TWO
/*  41 */     .pow(448).subtract(TWO.pow(224))
/*  42 */     .subtract(BigInteger.valueOf(1L));
/*     */   
/*     */   public IntegerPolynomial448() {
/*  45 */     super(28, 16, 1, MODULUS);
/*     */   }
/*     */   
/*     */   private void modReduceIn(long[] paramArrayOflong, int paramInt, long paramLong) {
/*  49 */     paramArrayOflong[paramInt - 16] = paramArrayOflong[paramInt - 16] + paramLong;
/*  50 */     paramArrayOflong[paramInt - 8] = paramArrayOflong[paramInt - 8] + paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*  55 */     long l = paramArrayOflong[this.numLimbs - 1] >> this.bitsPerLimb;
/*  56 */     paramArrayOflong[this.numLimbs - 1] = paramArrayOflong[this.numLimbs - 1] - (l << this.bitsPerLimb);
/*  57 */     modReduceIn(paramArrayOflong, this.numLimbs, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/*  64 */     long l1 = carryValue(paramArrayOflong[14]);
/*  65 */     paramArrayOflong[14] = paramArrayOflong[14] - (l1 << 28L);
/*  66 */     paramArrayOflong[15] = paramArrayOflong[15] + l1;
/*     */     
/*  68 */     long l2 = carryValue(paramArrayOflong[15]);
/*  69 */     paramArrayOflong[15] = paramArrayOflong[15] - (l2 << 28L);
/*     */ 
/*     */     
/*  72 */     paramArrayOflong[0] = paramArrayOflong[0] + l2;
/*  73 */     paramArrayOflong[8] = paramArrayOflong[8] + l2;
/*     */ 
/*     */     
/*  76 */     carry(paramArrayOflong, 0, 15);
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
/*     */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/*  92 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/*  93 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/*  94 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/*  95 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/*  96 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/*  97 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/*  98 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/*  99 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 100 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 101 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 102 */     long l11 = paramArrayOflong1[0] * paramArrayOflong2[10] + paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1] + paramArrayOflong1[10] * paramArrayOflong2[0];
/* 103 */     long l12 = paramArrayOflong1[0] * paramArrayOflong2[11] + paramArrayOflong1[1] * paramArrayOflong2[10] + paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2] + paramArrayOflong1[10] * paramArrayOflong2[1] + paramArrayOflong1[11] * paramArrayOflong2[0];
/* 104 */     long l13 = paramArrayOflong1[0] * paramArrayOflong2[12] + paramArrayOflong1[1] * paramArrayOflong2[11] + paramArrayOflong1[2] * paramArrayOflong2[10] + paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3] + paramArrayOflong1[10] * paramArrayOflong2[2] + paramArrayOflong1[11] * paramArrayOflong2[1] + paramArrayOflong1[12] * paramArrayOflong2[0];
/* 105 */     long l14 = paramArrayOflong1[0] * paramArrayOflong2[13] + paramArrayOflong1[1] * paramArrayOflong2[12] + paramArrayOflong1[2] * paramArrayOflong2[11] + paramArrayOflong1[3] * paramArrayOflong2[10] + paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4] + paramArrayOflong1[10] * paramArrayOflong2[3] + paramArrayOflong1[11] * paramArrayOflong2[2] + paramArrayOflong1[12] * paramArrayOflong2[1] + paramArrayOflong1[13] * paramArrayOflong2[0];
/* 106 */     long l15 = paramArrayOflong1[0] * paramArrayOflong2[14] + paramArrayOflong1[1] * paramArrayOflong2[13] + paramArrayOflong1[2] * paramArrayOflong2[12] + paramArrayOflong1[3] * paramArrayOflong2[11] + paramArrayOflong1[4] * paramArrayOflong2[10] + paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5] + paramArrayOflong1[10] * paramArrayOflong2[4] + paramArrayOflong1[11] * paramArrayOflong2[3] + paramArrayOflong1[12] * paramArrayOflong2[2] + paramArrayOflong1[13] * paramArrayOflong2[1] + paramArrayOflong1[14] * paramArrayOflong2[0];
/* 107 */     long l16 = paramArrayOflong1[0] * paramArrayOflong2[15] + paramArrayOflong1[1] * paramArrayOflong2[14] + paramArrayOflong1[2] * paramArrayOflong2[13] + paramArrayOflong1[3] * paramArrayOflong2[12] + paramArrayOflong1[4] * paramArrayOflong2[11] + paramArrayOflong1[5] * paramArrayOflong2[10] + paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6] + paramArrayOflong1[10] * paramArrayOflong2[5] + paramArrayOflong1[11] * paramArrayOflong2[4] + paramArrayOflong1[12] * paramArrayOflong2[3] + paramArrayOflong1[13] * paramArrayOflong2[2] + paramArrayOflong1[14] * paramArrayOflong2[1] + paramArrayOflong1[15] * paramArrayOflong2[0];
/* 108 */     long l17 = paramArrayOflong1[1] * paramArrayOflong2[15] + paramArrayOflong1[2] * paramArrayOflong2[14] + paramArrayOflong1[3] * paramArrayOflong2[13] + paramArrayOflong1[4] * paramArrayOflong2[12] + paramArrayOflong1[5] * paramArrayOflong2[11] + paramArrayOflong1[6] * paramArrayOflong2[10] + paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7] + paramArrayOflong1[10] * paramArrayOflong2[6] + paramArrayOflong1[11] * paramArrayOflong2[5] + paramArrayOflong1[12] * paramArrayOflong2[4] + paramArrayOflong1[13] * paramArrayOflong2[3] + paramArrayOflong1[14] * paramArrayOflong2[2] + paramArrayOflong1[15] * paramArrayOflong2[1];
/* 109 */     long l18 = paramArrayOflong1[2] * paramArrayOflong2[15] + paramArrayOflong1[3] * paramArrayOflong2[14] + paramArrayOflong1[4] * paramArrayOflong2[13] + paramArrayOflong1[5] * paramArrayOflong2[12] + paramArrayOflong1[6] * paramArrayOflong2[11] + paramArrayOflong1[7] * paramArrayOflong2[10] + paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8] + paramArrayOflong1[10] * paramArrayOflong2[7] + paramArrayOflong1[11] * paramArrayOflong2[6] + paramArrayOflong1[12] * paramArrayOflong2[5] + paramArrayOflong1[13] * paramArrayOflong2[4] + paramArrayOflong1[14] * paramArrayOflong2[3] + paramArrayOflong1[15] * paramArrayOflong2[2];
/* 110 */     long l19 = paramArrayOflong1[3] * paramArrayOflong2[15] + paramArrayOflong1[4] * paramArrayOflong2[14] + paramArrayOflong1[5] * paramArrayOflong2[13] + paramArrayOflong1[6] * paramArrayOflong2[12] + paramArrayOflong1[7] * paramArrayOflong2[11] + paramArrayOflong1[8] * paramArrayOflong2[10] + paramArrayOflong1[9] * paramArrayOflong2[9] + paramArrayOflong1[10] * paramArrayOflong2[8] + paramArrayOflong1[11] * paramArrayOflong2[7] + paramArrayOflong1[12] * paramArrayOflong2[6] + paramArrayOflong1[13] * paramArrayOflong2[5] + paramArrayOflong1[14] * paramArrayOflong2[4] + paramArrayOflong1[15] * paramArrayOflong2[3];
/* 111 */     long l20 = paramArrayOflong1[4] * paramArrayOflong2[15] + paramArrayOflong1[5] * paramArrayOflong2[14] + paramArrayOflong1[6] * paramArrayOflong2[13] + paramArrayOflong1[7] * paramArrayOflong2[12] + paramArrayOflong1[8] * paramArrayOflong2[11] + paramArrayOflong1[9] * paramArrayOflong2[10] + paramArrayOflong1[10] * paramArrayOflong2[9] + paramArrayOflong1[11] * paramArrayOflong2[8] + paramArrayOflong1[12] * paramArrayOflong2[7] + paramArrayOflong1[13] * paramArrayOflong2[6] + paramArrayOflong1[14] * paramArrayOflong2[5] + paramArrayOflong1[15] * paramArrayOflong2[4];
/* 112 */     long l21 = paramArrayOflong1[5] * paramArrayOflong2[15] + paramArrayOflong1[6] * paramArrayOflong2[14] + paramArrayOflong1[7] * paramArrayOflong2[13] + paramArrayOflong1[8] * paramArrayOflong2[12] + paramArrayOflong1[9] * paramArrayOflong2[11] + paramArrayOflong1[10] * paramArrayOflong2[10] + paramArrayOflong1[11] * paramArrayOflong2[9] + paramArrayOflong1[12] * paramArrayOflong2[8] + paramArrayOflong1[13] * paramArrayOflong2[7] + paramArrayOflong1[14] * paramArrayOflong2[6] + paramArrayOflong1[15] * paramArrayOflong2[5];
/* 113 */     long l22 = paramArrayOflong1[6] * paramArrayOflong2[15] + paramArrayOflong1[7] * paramArrayOflong2[14] + paramArrayOflong1[8] * paramArrayOflong2[13] + paramArrayOflong1[9] * paramArrayOflong2[12] + paramArrayOflong1[10] * paramArrayOflong2[11] + paramArrayOflong1[11] * paramArrayOflong2[10] + paramArrayOflong1[12] * paramArrayOflong2[9] + paramArrayOflong1[13] * paramArrayOflong2[8] + paramArrayOflong1[14] * paramArrayOflong2[7] + paramArrayOflong1[15] * paramArrayOflong2[6];
/* 114 */     long l23 = paramArrayOflong1[7] * paramArrayOflong2[15] + paramArrayOflong1[8] * paramArrayOflong2[14] + paramArrayOflong1[9] * paramArrayOflong2[13] + paramArrayOflong1[10] * paramArrayOflong2[12] + paramArrayOflong1[11] * paramArrayOflong2[11] + paramArrayOflong1[12] * paramArrayOflong2[10] + paramArrayOflong1[13] * paramArrayOflong2[9] + paramArrayOflong1[14] * paramArrayOflong2[8] + paramArrayOflong1[15] * paramArrayOflong2[7];
/* 115 */     long l24 = paramArrayOflong1[8] * paramArrayOflong2[15] + paramArrayOflong1[9] * paramArrayOflong2[14] + paramArrayOflong1[10] * paramArrayOflong2[13] + paramArrayOflong1[11] * paramArrayOflong2[12] + paramArrayOflong1[12] * paramArrayOflong2[11] + paramArrayOflong1[13] * paramArrayOflong2[10] + paramArrayOflong1[14] * paramArrayOflong2[9] + paramArrayOflong1[15] * paramArrayOflong2[8];
/* 116 */     long l25 = paramArrayOflong1[9] * paramArrayOflong2[15] + paramArrayOflong1[10] * paramArrayOflong2[14] + paramArrayOflong1[11] * paramArrayOflong2[13] + paramArrayOflong1[12] * paramArrayOflong2[12] + paramArrayOflong1[13] * paramArrayOflong2[11] + paramArrayOflong1[14] * paramArrayOflong2[10] + paramArrayOflong1[15] * paramArrayOflong2[9];
/* 117 */     long l26 = paramArrayOflong1[10] * paramArrayOflong2[15] + paramArrayOflong1[11] * paramArrayOflong2[14] + paramArrayOflong1[12] * paramArrayOflong2[13] + paramArrayOflong1[13] * paramArrayOflong2[12] + paramArrayOflong1[14] * paramArrayOflong2[11] + paramArrayOflong1[15] * paramArrayOflong2[10];
/* 118 */     long l27 = paramArrayOflong1[11] * paramArrayOflong2[15] + paramArrayOflong1[12] * paramArrayOflong2[14] + paramArrayOflong1[13] * paramArrayOflong2[13] + paramArrayOflong1[14] * paramArrayOflong2[12] + paramArrayOflong1[15] * paramArrayOflong2[11];
/* 119 */     long l28 = paramArrayOflong1[12] * paramArrayOflong2[15] + paramArrayOflong1[13] * paramArrayOflong2[14] + paramArrayOflong1[14] * paramArrayOflong2[13] + paramArrayOflong1[15] * paramArrayOflong2[12];
/* 120 */     long l29 = paramArrayOflong1[13] * paramArrayOflong2[15] + paramArrayOflong1[14] * paramArrayOflong2[14] + paramArrayOflong1[15] * paramArrayOflong2[13];
/* 121 */     long l30 = paramArrayOflong1[14] * paramArrayOflong2[15] + paramArrayOflong1[15] * paramArrayOflong2[14];
/* 122 */     long l31 = paramArrayOflong1[15] * paramArrayOflong2[15];
/*     */     
/* 124 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31);
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
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31) {
/* 138 */     paramLong9 += paramLong25;
/* 139 */     paramLong17 += paramLong25;
/*     */     
/* 141 */     paramLong10 += paramLong26;
/* 142 */     paramLong18 += paramLong26;
/*     */     
/* 144 */     paramLong11 += paramLong27;
/* 145 */     paramLong19 += paramLong27;
/*     */     
/* 147 */     paramLong12 += paramLong28;
/* 148 */     paramLong20 += paramLong28;
/*     */     
/* 150 */     paramLong13 += paramLong29;
/* 151 */     paramLong21 += paramLong29;
/*     */     
/* 153 */     paramLong14 += paramLong30;
/* 154 */     paramLong22 += paramLong30;
/*     */     
/* 156 */     paramLong15 += paramLong31;
/* 157 */     paramLong23 += paramLong31;
/*     */ 
/*     */     
/* 160 */     paramArrayOflong[4] = paramLong5 + paramLong21;
/* 161 */     paramArrayOflong[12] = paramLong13 + paramLong21;
/*     */     
/* 163 */     paramArrayOflong[5] = paramLong6 + paramLong22;
/* 164 */     paramArrayOflong[13] = paramLong14 + paramLong22;
/*     */     
/* 166 */     paramArrayOflong[6] = paramLong7 + paramLong23;
/* 167 */     paramLong15 += paramLong23;
/*     */     
/* 169 */     paramArrayOflong[7] = paramLong8 + paramLong24;
/* 170 */     paramLong16 += paramLong24;
/*     */ 
/*     */     
/* 173 */     long l1 = carryValue(paramLong15);
/* 174 */     paramArrayOflong[14] = paramLong15 - (l1 << 28L);
/* 175 */     paramLong16 += l1;
/*     */     
/* 177 */     long l2 = carryValue(paramLong16);
/* 178 */     paramArrayOflong[15] = paramLong16 - (l2 << 28L);
/* 179 */     paramLong17 += l2;
/*     */ 
/*     */     
/* 182 */     paramArrayOflong[0] = paramLong1 + paramLong17;
/* 183 */     paramArrayOflong[8] = paramLong9 + paramLong17;
/*     */     
/* 185 */     paramArrayOflong[1] = paramLong2 + paramLong18;
/* 186 */     paramArrayOflong[9] = paramLong10 + paramLong18;
/*     */     
/* 188 */     paramArrayOflong[2] = paramLong3 + paramLong19;
/* 189 */     paramArrayOflong[10] = paramLong11 + paramLong19;
/*     */     
/* 191 */     paramArrayOflong[3] = paramLong4 + paramLong20;
/* 192 */     paramArrayOflong[11] = paramLong12 + paramLong20;
/*     */ 
/*     */     
/* 195 */     carry(paramArrayOflong, 0, 15);
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
/*     */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 212 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 213 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 214 */     long l3 = paramArrayOflong1[1] * paramArrayOflong1[1] + 2L * paramArrayOflong1[0] * paramArrayOflong1[2];
/* 215 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 216 */     long l5 = paramArrayOflong1[2] * paramArrayOflong1[2] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]);
/* 217 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 218 */     long l7 = paramArrayOflong1[3] * paramArrayOflong1[3] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]);
/* 219 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 220 */     long l9 = paramArrayOflong1[4] * paramArrayOflong1[4] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]);
/* 221 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 222 */     long l11 = paramArrayOflong1[5] * paramArrayOflong1[5] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[10] + paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]);
/* 223 */     long l12 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[11] + paramArrayOflong1[1] * paramArrayOflong1[10] + paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 224 */     long l13 = paramArrayOflong1[6] * paramArrayOflong1[6] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[12] + paramArrayOflong1[1] * paramArrayOflong1[11] + paramArrayOflong1[2] * paramArrayOflong1[10] + paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]);
/* 225 */     long l14 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[13] + paramArrayOflong1[1] * paramArrayOflong1[12] + paramArrayOflong1[2] * paramArrayOflong1[11] + paramArrayOflong1[3] * paramArrayOflong1[10] + paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 226 */     long l15 = paramArrayOflong1[7] * paramArrayOflong1[7] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[14] + paramArrayOflong1[1] * paramArrayOflong1[13] + paramArrayOflong1[2] * paramArrayOflong1[12] + paramArrayOflong1[3] * paramArrayOflong1[11] + paramArrayOflong1[4] * paramArrayOflong1[10] + paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]);
/* 227 */     long l16 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[15] + paramArrayOflong1[1] * paramArrayOflong1[14] + paramArrayOflong1[2] * paramArrayOflong1[13] + paramArrayOflong1[3] * paramArrayOflong1[12] + paramArrayOflong1[4] * paramArrayOflong1[11] + paramArrayOflong1[5] * paramArrayOflong1[10] + paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 228 */     long l17 = paramArrayOflong1[8] * paramArrayOflong1[8] + 2L * (paramArrayOflong1[1] * paramArrayOflong1[15] + paramArrayOflong1[2] * paramArrayOflong1[14] + paramArrayOflong1[3] * paramArrayOflong1[13] + paramArrayOflong1[4] * paramArrayOflong1[12] + paramArrayOflong1[5] * paramArrayOflong1[11] + paramArrayOflong1[6] * paramArrayOflong1[10] + paramArrayOflong1[7] * paramArrayOflong1[9]);
/* 229 */     long l18 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[15] + paramArrayOflong1[3] * paramArrayOflong1[14] + paramArrayOflong1[4] * paramArrayOflong1[13] + paramArrayOflong1[5] * paramArrayOflong1[12] + paramArrayOflong1[6] * paramArrayOflong1[11] + paramArrayOflong1[7] * paramArrayOflong1[10] + paramArrayOflong1[8] * paramArrayOflong1[9]);
/* 230 */     long l19 = paramArrayOflong1[9] * paramArrayOflong1[9] + 2L * (paramArrayOflong1[3] * paramArrayOflong1[15] + paramArrayOflong1[4] * paramArrayOflong1[14] + paramArrayOflong1[5] * paramArrayOflong1[13] + paramArrayOflong1[6] * paramArrayOflong1[12] + paramArrayOflong1[7] * paramArrayOflong1[11] + paramArrayOflong1[8] * paramArrayOflong1[10]);
/* 231 */     long l20 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[15] + paramArrayOflong1[5] * paramArrayOflong1[14] + paramArrayOflong1[6] * paramArrayOflong1[13] + paramArrayOflong1[7] * paramArrayOflong1[12] + paramArrayOflong1[8] * paramArrayOflong1[11] + paramArrayOflong1[9] * paramArrayOflong1[10]);
/* 232 */     long l21 = paramArrayOflong1[10] * paramArrayOflong1[10] + 2L * (paramArrayOflong1[5] * paramArrayOflong1[15] + paramArrayOflong1[6] * paramArrayOflong1[14] + paramArrayOflong1[7] * paramArrayOflong1[13] + paramArrayOflong1[8] * paramArrayOflong1[12] + paramArrayOflong1[9] * paramArrayOflong1[11]);
/* 233 */     long l22 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[15] + paramArrayOflong1[7] * paramArrayOflong1[14] + paramArrayOflong1[8] * paramArrayOflong1[13] + paramArrayOflong1[9] * paramArrayOflong1[12] + paramArrayOflong1[10] * paramArrayOflong1[11]);
/* 234 */     long l23 = paramArrayOflong1[11] * paramArrayOflong1[11] + 2L * (paramArrayOflong1[7] * paramArrayOflong1[15] + paramArrayOflong1[8] * paramArrayOflong1[14] + paramArrayOflong1[9] * paramArrayOflong1[13] + paramArrayOflong1[10] * paramArrayOflong1[12]);
/* 235 */     long l24 = 2L * (paramArrayOflong1[8] * paramArrayOflong1[15] + paramArrayOflong1[9] * paramArrayOflong1[14] + paramArrayOflong1[10] * paramArrayOflong1[13] + paramArrayOflong1[11] * paramArrayOflong1[12]);
/* 236 */     long l25 = paramArrayOflong1[12] * paramArrayOflong1[12] + 2L * (paramArrayOflong1[9] * paramArrayOflong1[15] + paramArrayOflong1[10] * paramArrayOflong1[14] + paramArrayOflong1[11] * paramArrayOflong1[13]);
/* 237 */     long l26 = 2L * (paramArrayOflong1[10] * paramArrayOflong1[15] + paramArrayOflong1[11] * paramArrayOflong1[14] + paramArrayOflong1[12] * paramArrayOflong1[13]);
/* 238 */     long l27 = paramArrayOflong1[13] * paramArrayOflong1[13] + 2L * (paramArrayOflong1[11] * paramArrayOflong1[15] + paramArrayOflong1[12] * paramArrayOflong1[14]);
/* 239 */     long l28 = 2L * (paramArrayOflong1[12] * paramArrayOflong1[15] + paramArrayOflong1[13] * paramArrayOflong1[14]);
/* 240 */     long l29 = paramArrayOflong1[14] * paramArrayOflong1[14] + 2L * paramArrayOflong1[13] * paramArrayOflong1[15];
/* 241 */     long l30 = 2L * paramArrayOflong1[14] * paramArrayOflong1[15];
/* 242 */     long l31 = paramArrayOflong1[15] * paramArrayOflong1[15];
/*     */     
/* 244 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/IntegerPolynomial448.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */