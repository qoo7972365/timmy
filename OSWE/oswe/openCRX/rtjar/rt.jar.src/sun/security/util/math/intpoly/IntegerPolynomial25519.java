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
/*     */ public class IntegerPolynomial25519
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   private static final int POWER = 255;
/*     */   private static final int SUBTRAHEND = 19;
/*     */   private static final int NUM_LIMBS = 10;
/*     */   private static final int BITS_PER_LIMB = 26;
/*  41 */   public static final BigInteger MODULUS = TWO
/*  42 */     .pow(255).subtract(BigInteger.valueOf(19L));
/*     */   
/*     */   private static final int BIT_OFFSET = 5;
/*     */   
/*     */   private static final int LIMB_MASK = 67108863;
/*     */   
/*     */   private static final int RIGHT_BIT_OFFSET = 21;
/*     */   
/*     */   public IntegerPolynomial25519() {
/*  51 */     super(26, 10, 1, MODULUS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*  57 */     long l = paramArrayOflong[this.numLimbs - 1] >> 21L;
/*  58 */     paramArrayOflong[this.numLimbs - 1] = paramArrayOflong[this.numLimbs - 1] - (l << 21L);
/*  59 */     paramArrayOflong[0] = paramArrayOflong[0] + l * 19L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/*  66 */     long l1 = carryValue(paramArrayOflong[8]);
/*  67 */     paramArrayOflong[8] = paramArrayOflong[8] - (l1 << 26L);
/*  68 */     paramArrayOflong[9] = paramArrayOflong[9] + l1;
/*     */     
/*  70 */     long l2 = carryValue(paramArrayOflong[9]);
/*  71 */     paramArrayOflong[9] = paramArrayOflong[9] - (l2 << 26L);
/*     */ 
/*     */     
/*  74 */     long l3 = l2 * 19L;
/*  75 */     paramArrayOflong[0] = paramArrayOflong[0] + (l3 << 5L & 0x3FFFFFFL);
/*  76 */     paramArrayOflong[1] = paramArrayOflong[1] + (l3 >> 21L);
/*     */ 
/*     */     
/*  79 */     carry(paramArrayOflong, 0, 9);
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
/*  95 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/*  96 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/*  97 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/*  98 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/*  99 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/* 100 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/* 101 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/* 102 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 103 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 104 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 105 */     long l11 = paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1];
/* 106 */     long l12 = paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2];
/* 107 */     long l13 = paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3];
/* 108 */     long l14 = paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4];
/* 109 */     long l15 = paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5];
/* 110 */     long l16 = paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6];
/* 111 */     long l17 = paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7];
/* 112 */     long l18 = paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8];
/* 113 */     long l19 = paramArrayOflong1[9] * paramArrayOflong2[9];
/*     */     
/* 115 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19) {
/* 126 */     long l1 = paramLong18 * 19L;
/* 127 */     paramLong8 += l1 << 5L & 0x3FFFFFFL;
/* 128 */     paramLong9 += l1 >> 21L;
/*     */     
/* 130 */     long l2 = paramLong19 * 19L;
/* 131 */     paramLong9 += l2 << 5L & 0x3FFFFFFL;
/* 132 */     paramLong10 += l2 >> 21L;
/*     */ 
/*     */     
/* 135 */     long l3 = carryValue(paramLong9);
/* 136 */     paramArrayOflong[8] = paramLong9 - (l3 << 26L);
/* 137 */     paramLong10 += l3;
/*     */     
/* 139 */     long l4 = carryValue(paramLong10);
/* 140 */     paramArrayOflong[9] = paramLong10 - (l4 << 26L);
/* 141 */     paramLong11 += l4;
/*     */ 
/*     */     
/* 144 */     long l5 = paramLong11 * 19L;
/* 145 */     paramArrayOflong[0] = paramLong1 + (l5 << 5L & 0x3FFFFFFL);
/* 146 */     paramLong2 += l5 >> 21L;
/*     */     
/* 148 */     long l6 = paramLong12 * 19L;
/* 149 */     paramArrayOflong[1] = paramLong2 + (l6 << 5L & 0x3FFFFFFL);
/* 150 */     paramLong3 += l6 >> 21L;
/*     */     
/* 152 */     long l7 = paramLong13 * 19L;
/* 153 */     paramArrayOflong[2] = paramLong3 + (l7 << 5L & 0x3FFFFFFL);
/* 154 */     paramLong4 += l7 >> 21L;
/*     */     
/* 156 */     long l8 = paramLong14 * 19L;
/* 157 */     paramArrayOflong[3] = paramLong4 + (l8 << 5L & 0x3FFFFFFL);
/* 158 */     paramLong5 += l8 >> 21L;
/*     */     
/* 160 */     long l9 = paramLong15 * 19L;
/* 161 */     paramArrayOflong[4] = paramLong5 + (l9 << 5L & 0x3FFFFFFL);
/* 162 */     paramLong6 += l9 >> 21L;
/*     */     
/* 164 */     long l10 = paramLong16 * 19L;
/* 165 */     paramArrayOflong[5] = paramLong6 + (l10 << 5L & 0x3FFFFFFL);
/* 166 */     paramLong7 += l10 >> 21L;
/*     */     
/* 168 */     long l11 = paramLong17 * 19L;
/* 169 */     paramArrayOflong[6] = paramLong7 + (l11 << 5L & 0x3FFFFFFL);
/* 170 */     paramArrayOflong[7] = paramLong8 + (l11 >> 21L);
/*     */ 
/*     */     
/* 173 */     carry(paramArrayOflong, 0, 9);
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
/* 190 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 191 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 192 */     long l3 = paramArrayOflong1[1] * paramArrayOflong1[1] + 2L * paramArrayOflong1[0] * paramArrayOflong1[2];
/* 193 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 194 */     long l5 = paramArrayOflong1[2] * paramArrayOflong1[2] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]);
/* 195 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 196 */     long l7 = paramArrayOflong1[3] * paramArrayOflong1[3] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]);
/* 197 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 198 */     long l9 = paramArrayOflong1[4] * paramArrayOflong1[4] + 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]);
/* 199 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 200 */     long l11 = paramArrayOflong1[5] * paramArrayOflong1[5] + 2L * (paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]);
/* 201 */     long l12 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 202 */     long l13 = paramArrayOflong1[6] * paramArrayOflong1[6] + 2L * (paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]);
/* 203 */     long l14 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 204 */     long l15 = paramArrayOflong1[7] * paramArrayOflong1[7] + 2L * (paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]);
/* 205 */     long l16 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 206 */     long l17 = paramArrayOflong1[8] * paramArrayOflong1[8] + 2L * paramArrayOflong1[7] * paramArrayOflong1[9];
/* 207 */     long l18 = 2L * paramArrayOflong1[8] * paramArrayOflong1[9];
/* 208 */     long l19 = paramArrayOflong1[9] * paramArrayOflong1[9];
/*     */     
/* 210 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/IntegerPolynomial25519.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */