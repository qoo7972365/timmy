/*     */ package sun.security.util.math.intpoly;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntegerPolynomial1305
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   protected static final int SUBTRAHEND = 5;
/*     */   protected static final int NUM_LIMBS = 5;
/*     */   private static final int POWER = 130;
/*     */   private static final int BITS_PER_LIMB = 26;
/*  43 */   private static final BigInteger MODULUS = TWO
/*  44 */     .pow(130).subtract(BigInteger.valueOf(5L));
/*     */   
/*     */   public IntegerPolynomial1305() {
/*  47 */     super(26, 5, 1, MODULUS);
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
/*     */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/*  62 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/*  63 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/*  64 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/*  65 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/*  66 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/*  67 */     long l6 = paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1];
/*  68 */     long l7 = paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2];
/*  69 */     long l8 = paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3];
/*  70 */     long l9 = paramArrayOflong1[4] * paramArrayOflong2[4];
/*     */     
/*  72 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9) {
/*  78 */     paramArrayOflong[2] = paramLong3 + paramLong8 * 5L;
/*  79 */     paramLong4 += paramLong9 * 5L;
/*     */ 
/*     */     
/*  82 */     long l1 = carryValue(paramLong4);
/*  83 */     paramArrayOflong[3] = paramLong4 - (l1 << 26L);
/*  84 */     paramLong5 += l1;
/*     */     
/*  86 */     long l2 = carryValue(paramLong5);
/*  87 */     paramArrayOflong[4] = paramLong5 - (l2 << 26L);
/*  88 */     paramLong6 += l2;
/*     */ 
/*     */     
/*  91 */     paramArrayOflong[0] = paramLong1 + paramLong6 * 5L;
/*  92 */     paramArrayOflong[1] = paramLong2 + paramLong7 * 5L;
/*     */ 
/*     */     
/*  95 */     carry(paramArrayOflong);
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
/*     */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 111 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 112 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 113 */     long l3 = 2L * paramArrayOflong1[0] * paramArrayOflong1[2] + paramArrayOflong1[1] * paramArrayOflong1[1];
/* 114 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 115 */     long l5 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]) + paramArrayOflong1[2] * paramArrayOflong1[2];
/* 116 */     long l6 = 2L * (paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 117 */     long l7 = 2L * paramArrayOflong1[2] * paramArrayOflong1[4] + paramArrayOflong1[3] * paramArrayOflong1[3];
/* 118 */     long l8 = 2L * paramArrayOflong1[3] * paramArrayOflong1[4];
/* 119 */     long l9 = paramArrayOflong1[4] * paramArrayOflong1[4];
/*     */     
/* 121 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ByteBuffer paramByteBuffer, int paramInt, byte paramByte, long[] paramArrayOflong) {
/* 127 */     if (paramInt == 16) {
/* 128 */       long l1 = paramByteBuffer.getLong();
/* 129 */       long l2 = paramByteBuffer.getLong();
/* 130 */       encode(l2, l1, paramByte, paramArrayOflong);
/*     */     } else {
/* 132 */       super.encode(paramByteBuffer, paramInt, paramByte, paramArrayOflong);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void encode(long paramLong1, long paramLong2, byte paramByte, long[] paramArrayOflong) {
/* 137 */     paramArrayOflong[0] = paramLong2 & 0x3FFFFFFL;
/* 138 */     paramArrayOflong[1] = paramLong2 >>> 26L & 0x3FFFFFFL;
/* 139 */     paramArrayOflong[2] = (paramLong2 >>> 52L) + ((paramLong1 & 0x3FFFL) << 12L);
/* 140 */     paramArrayOflong[3] = paramLong1 >>> 14L & 0x3FFFFFFL;
/* 141 */     paramArrayOflong[4] = (paramLong1 >>> 40L) + (paramByte << 24);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte, long[] paramArrayOflong) {
/* 146 */     if (paramInt2 == 16) {
/*     */       
/* 148 */       ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte, paramInt1, paramInt2).order(ByteOrder.LITTLE_ENDIAN);
/* 149 */       long l1 = byteBuffer.getLong();
/* 150 */       long l2 = byteBuffer.getLong();
/* 151 */       encode(l2, l1, paramByte, paramArrayOflong);
/*     */     } else {
/* 153 */       super.encode(paramArrayOfbyte, paramInt1, paramInt2, paramByte, paramArrayOflong);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void modReduceIn(long[] paramArrayOflong, int paramInt, long paramLong) {
/* 159 */     long l = paramLong * 5L;
/* 160 */     paramArrayOflong[paramInt - 5] = paramArrayOflong[paramInt - 5] + l;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/* 165 */     long l = paramArrayOflong[this.numLimbs - 1] >> this.bitsPerLimb;
/* 166 */     paramArrayOflong[this.numLimbs - 1] = paramArrayOflong[this.numLimbs - 1] - (l << this.bitsPerLimb);
/* 167 */     modReduceIn(paramArrayOflong, this.numLimbs, l);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void modReduce(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 172 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 173 */       modReduceIn(paramArrayOflong, i, paramArrayOflong[i]);
/* 174 */       paramArrayOflong[i] = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void modReduce(long[] paramArrayOflong) {
/* 180 */     modReduce(paramArrayOflong, 5, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long carryValue(long paramLong) {
/* 188 */     return paramLong >> 26L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postEncodeCarry(long[] paramArrayOflong) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/* 198 */     long l1 = carryOut(paramArrayOflong, 3);
/* 199 */     long l2 = l1 + paramArrayOflong[4];
/*     */     
/* 201 */     long l3 = carryValue(l2);
/* 202 */     paramArrayOflong[4] = l2 - (l3 << 26L);
/*     */     
/* 204 */     modReduceIn(paramArrayOflong, 5, l3);
/* 205 */     carry(paramArrayOflong);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/IntegerPolynomial1305.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */