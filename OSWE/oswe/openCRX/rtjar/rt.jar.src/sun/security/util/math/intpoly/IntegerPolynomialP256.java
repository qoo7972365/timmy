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
/*     */ public class IntegerPolynomialP256
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   private static final int BITS_PER_LIMB = 26;
/*     */   private static final int NUM_LIMBS = 10;
/*     */   private static final int MAX_ADDS = 2;
/*  37 */   public static final BigInteger MODULUS = evaluateModulus();
/*     */   private static final long CARRY_ADD = 33554432L;
/*     */   private static final int LIMB_MASK = 67108863;
/*     */   
/*     */   public IntegerPolynomialP256() {
/*  42 */     super(26, 10, 2, MODULUS);
/*     */   }
/*     */   
/*     */   private static BigInteger evaluateModulus() {
/*  46 */     BigInteger bigInteger = BigInteger.valueOf(2L).pow(256);
/*  47 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(224));
/*  48 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(192));
/*  49 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(96));
/*  50 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(1L));
/*  51 */     return bigInteger;
/*     */   }
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*  55 */     long l = paramArrayOflong[9] >> 22L;
/*  56 */     paramArrayOflong[9] = paramArrayOflong[9] - (l << 22L);
/*  57 */     paramArrayOflong[8] = paramArrayOflong[8] + (l << 16L & 0x3FFFFFFL);
/*  58 */     paramArrayOflong[9] = paramArrayOflong[9] + (l >> 10L);
/*  59 */     paramArrayOflong[7] = paramArrayOflong[7] - (l << 10L & 0x3FFFFFFL);
/*  60 */     paramArrayOflong[8] = paramArrayOflong[8] - (l >> 16L);
/*  61 */     paramArrayOflong[3] = paramArrayOflong[3] - (l << 18L & 0x3FFFFFFL);
/*  62 */     paramArrayOflong[4] = paramArrayOflong[4] - (l >> 8L);
/*  63 */     paramArrayOflong[0] = paramArrayOflong[0] + l;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19) {
/*  66 */     long l = 0L;
/*     */     
/*  68 */     paramLong17 += paramLong19 << 20L & 0x3FFFFFFL;
/*  69 */     paramLong18 += paramLong19 >> 6L;
/*  70 */     paramLong16 -= paramLong19 << 14L & 0x3FFFFFFL;
/*  71 */     paramLong17 -= paramLong19 >> 12L;
/*  72 */     paramLong12 -= paramLong19 << 22L & 0x3FFFFFFL;
/*  73 */     paramLong13 -= paramLong19 >> 4L;
/*  74 */     paramLong9 += paramLong19 << 4L & 0x3FFFFFFL;
/*  75 */     paramLong10 += paramLong19 >> 22L;
/*     */     
/*  77 */     paramLong16 += paramLong18 << 20L & 0x3FFFFFFL;
/*  78 */     paramLong17 += paramLong18 >> 6L;
/*  79 */     paramLong15 -= paramLong18 << 14L & 0x3FFFFFFL;
/*  80 */     paramLong16 -= paramLong18 >> 12L;
/*  81 */     paramLong11 -= paramLong18 << 22L & 0x3FFFFFFL;
/*  82 */     paramLong12 -= paramLong18 >> 4L;
/*  83 */     paramLong8 += paramLong18 << 4L & 0x3FFFFFFL;
/*  84 */     paramLong9 += paramLong18 >> 22L;
/*     */     
/*  86 */     paramLong15 += paramLong17 << 20L & 0x3FFFFFFL;
/*  87 */     paramLong16 += paramLong17 >> 6L;
/*  88 */     paramLong14 -= paramLong17 << 14L & 0x3FFFFFFL;
/*  89 */     paramLong15 -= paramLong17 >> 12L;
/*  90 */     paramLong10 -= paramLong17 << 22L & 0x3FFFFFFL;
/*  91 */     paramLong11 -= paramLong17 >> 4L;
/*  92 */     paramLong7 += paramLong17 << 4L & 0x3FFFFFFL;
/*  93 */     paramLong8 += paramLong17 >> 22L;
/*     */     
/*  95 */     paramLong14 += paramLong16 << 20L & 0x3FFFFFFL;
/*  96 */     paramLong15 += paramLong16 >> 6L;
/*  97 */     paramLong13 -= paramLong16 << 14L & 0x3FFFFFFL;
/*  98 */     paramLong14 -= paramLong16 >> 12L;
/*  99 */     paramLong9 -= paramLong16 << 22L & 0x3FFFFFFL;
/* 100 */     paramLong10 -= paramLong16 >> 4L;
/* 101 */     paramLong6 += paramLong16 << 4L & 0x3FFFFFFL;
/* 102 */     paramLong7 += paramLong16 >> 22L;
/*     */     
/* 104 */     paramLong13 += paramLong15 << 20L & 0x3FFFFFFL;
/* 105 */     paramLong14 += paramLong15 >> 6L;
/* 106 */     paramLong12 -= paramLong15 << 14L & 0x3FFFFFFL;
/* 107 */     paramLong13 -= paramLong15 >> 12L;
/* 108 */     paramLong8 -= paramLong15 << 22L & 0x3FFFFFFL;
/* 109 */     paramLong9 -= paramLong15 >> 4L;
/* 110 */     paramLong5 += paramLong15 << 4L & 0x3FFFFFFL;
/* 111 */     paramLong6 += paramLong15 >> 22L;
/*     */     
/* 113 */     paramLong12 += paramLong14 << 20L & 0x3FFFFFFL;
/* 114 */     paramLong13 += paramLong14 >> 6L;
/* 115 */     paramLong11 -= paramLong14 << 14L & 0x3FFFFFFL;
/* 116 */     paramLong12 -= paramLong14 >> 12L;
/* 117 */     paramLong7 -= paramLong14 << 22L & 0x3FFFFFFL;
/* 118 */     paramLong8 -= paramLong14 >> 4L;
/* 119 */     paramLong4 += paramLong14 << 4L & 0x3FFFFFFL;
/* 120 */     paramLong5 += paramLong14 >> 22L;
/*     */     
/* 122 */     paramLong11 += paramLong13 << 20L & 0x3FFFFFFL;
/* 123 */     paramLong12 += paramLong13 >> 6L;
/* 124 */     paramLong10 -= paramLong13 << 14L & 0x3FFFFFFL;
/* 125 */     paramLong11 -= paramLong13 >> 12L;
/* 126 */     paramLong6 -= paramLong13 << 22L & 0x3FFFFFFL;
/* 127 */     paramLong7 -= paramLong13 >> 4L;
/* 128 */     paramLong3 += paramLong13 << 4L & 0x3FFFFFFL;
/* 129 */     paramLong4 += paramLong13 >> 22L;
/*     */     
/* 131 */     paramLong10 += paramLong12 << 20L & 0x3FFFFFFL;
/* 132 */     paramLong11 += paramLong12 >> 6L;
/* 133 */     paramLong9 -= paramLong12 << 14L & 0x3FFFFFFL;
/* 134 */     paramLong10 -= paramLong12 >> 12L;
/* 135 */     paramLong5 -= paramLong12 << 22L & 0x3FFFFFFL;
/* 136 */     paramLong6 -= paramLong12 >> 4L;
/* 137 */     paramLong2 += paramLong12 << 4L & 0x3FFFFFFL;
/* 138 */     paramLong3 += paramLong12 >> 22L;
/*     */     
/* 140 */     paramLong9 += paramLong11 << 20L & 0x3FFFFFFL;
/* 141 */     paramLong10 += paramLong11 >> 6L;
/* 142 */     paramLong8 -= paramLong11 << 14L & 0x3FFFFFFL;
/* 143 */     paramLong9 -= paramLong11 >> 12L;
/* 144 */     paramLong4 -= paramLong11 << 22L & 0x3FFFFFFL;
/* 145 */     paramLong5 -= paramLong11 >> 4L;
/* 146 */     paramLong1 += paramLong11 << 4L & 0x3FFFFFFL;
/* 147 */     paramLong2 += paramLong11 >> 22L;
/* 148 */     paramLong11 = 0L;
/*     */     
/* 150 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, l);
/*     */   }
/*     */ 
/*     */   
/*     */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20) {
/* 155 */     long l = paramLong9 + 33554432L >> 26L;
/* 156 */     paramLong9 -= l << 26L;
/* 157 */     paramLong10 += l;
/*     */     
/* 159 */     l = paramLong10 + 33554432L >> 26L;
/* 160 */     paramLong10 -= l << 26L;
/* 161 */     paramLong11 += l;
/*     */     
/* 163 */     paramLong9 += paramLong11 << 20L & 0x3FFFFFFL;
/* 164 */     paramLong10 += paramLong11 >> 6L;
/* 165 */     paramLong8 -= paramLong11 << 14L & 0x3FFFFFFL;
/* 166 */     paramLong9 -= paramLong11 >> 12L;
/* 167 */     paramLong4 -= paramLong11 << 22L & 0x3FFFFFFL;
/* 168 */     paramLong5 -= paramLong11 >> 4L;
/* 169 */     paramLong1 += paramLong11 << 4L & 0x3FFFFFFL;
/* 170 */     paramLong2 += paramLong11 >> 22L;
/*     */     
/* 172 */     l = paramLong1 + 33554432L >> 26L;
/* 173 */     paramLong1 -= l << 26L;
/* 174 */     paramLong2 += l;
/*     */     
/* 176 */     l = paramLong2 + 33554432L >> 26L;
/* 177 */     paramLong2 -= l << 26L;
/* 178 */     paramLong3 += l;
/*     */     
/* 180 */     l = paramLong3 + 33554432L >> 26L;
/* 181 */     paramLong3 -= l << 26L;
/* 182 */     paramLong4 += l;
/*     */     
/* 184 */     l = paramLong4 + 33554432L >> 26L;
/* 185 */     paramLong4 -= l << 26L;
/* 186 */     paramLong5 += l;
/*     */     
/* 188 */     l = paramLong5 + 33554432L >> 26L;
/* 189 */     paramLong5 -= l << 26L;
/* 190 */     paramLong6 += l;
/*     */     
/* 192 */     l = paramLong6 + 33554432L >> 26L;
/* 193 */     paramLong6 -= l << 26L;
/* 194 */     paramLong7 += l;
/*     */     
/* 196 */     l = paramLong7 + 33554432L >> 26L;
/* 197 */     paramLong7 -= l << 26L;
/* 198 */     paramLong8 += l;
/*     */     
/* 200 */     l = paramLong8 + 33554432L >> 26L;
/* 201 */     paramLong8 -= l << 26L;
/* 202 */     paramLong9 += l;
/*     */     
/* 204 */     l = paramLong9 + 33554432L >> 26L;
/* 205 */     paramLong9 -= l << 26L;
/* 206 */     paramLong10 += l;
/*     */     
/* 208 */     paramArrayOflong[0] = paramLong1;
/* 209 */     paramArrayOflong[1] = paramLong2;
/* 210 */     paramArrayOflong[2] = paramLong3;
/* 211 */     paramArrayOflong[3] = paramLong4;
/* 212 */     paramArrayOflong[4] = paramLong5;
/* 213 */     paramArrayOflong[5] = paramLong6;
/* 214 */     paramArrayOflong[6] = paramLong7;
/* 215 */     paramArrayOflong[7] = paramLong8;
/* 216 */     paramArrayOflong[8] = paramLong9;
/* 217 */     paramArrayOflong[9] = paramLong10;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10) {
/* 220 */     long l1 = 0L;
/*     */     
/* 222 */     long l2 = paramLong9 + 33554432L >> 26L;
/* 223 */     paramLong9 -= l2 << 26L;
/* 224 */     paramLong10 += l2;
/*     */     
/* 226 */     l2 = paramLong10 + 33554432L >> 26L;
/* 227 */     paramLong10 -= l2 << 26L;
/* 228 */     l1 += l2;
/*     */     
/* 230 */     paramLong9 += l1 << 20L & 0x3FFFFFFL;
/* 231 */     paramLong10 += l1 >> 6L;
/* 232 */     paramLong8 -= l1 << 14L & 0x3FFFFFFL;
/* 233 */     paramLong9 -= l1 >> 12L;
/* 234 */     paramLong4 -= l1 << 22L & 0x3FFFFFFL;
/* 235 */     paramLong5 -= l1 >> 4L;
/* 236 */     paramLong1 += l1 << 4L & 0x3FFFFFFL;
/* 237 */     paramLong2 += l1 >> 22L;
/*     */     
/* 239 */     l2 = paramLong1 + 33554432L >> 26L;
/* 240 */     paramLong1 -= l2 << 26L;
/* 241 */     paramLong2 += l2;
/*     */     
/* 243 */     l2 = paramLong2 + 33554432L >> 26L;
/* 244 */     paramLong2 -= l2 << 26L;
/* 245 */     paramLong3 += l2;
/*     */     
/* 247 */     l2 = paramLong3 + 33554432L >> 26L;
/* 248 */     paramLong3 -= l2 << 26L;
/* 249 */     paramLong4 += l2;
/*     */     
/* 251 */     l2 = paramLong4 + 33554432L >> 26L;
/* 252 */     paramLong4 -= l2 << 26L;
/* 253 */     paramLong5 += l2;
/*     */     
/* 255 */     l2 = paramLong5 + 33554432L >> 26L;
/* 256 */     paramLong5 -= l2 << 26L;
/* 257 */     paramLong6 += l2;
/*     */     
/* 259 */     l2 = paramLong6 + 33554432L >> 26L;
/* 260 */     paramLong6 -= l2 << 26L;
/* 261 */     paramLong7 += l2;
/*     */     
/* 263 */     l2 = paramLong7 + 33554432L >> 26L;
/* 264 */     paramLong7 -= l2 << 26L;
/* 265 */     paramLong8 += l2;
/*     */     
/* 267 */     l2 = paramLong8 + 33554432L >> 26L;
/* 268 */     paramLong8 -= l2 << 26L;
/* 269 */     paramLong9 += l2;
/*     */     
/* 271 */     l2 = paramLong9 + 33554432L >> 26L;
/* 272 */     paramLong9 -= l2 << 26L;
/* 273 */     paramLong10 += l2;
/*     */     
/* 275 */     paramArrayOflong[0] = paramLong1;
/* 276 */     paramArrayOflong[1] = paramLong2;
/* 277 */     paramArrayOflong[2] = paramLong3;
/* 278 */     paramArrayOflong[3] = paramLong4;
/* 279 */     paramArrayOflong[4] = paramLong5;
/* 280 */     paramArrayOflong[5] = paramLong6;
/* 281 */     paramArrayOflong[6] = paramLong7;
/* 282 */     paramArrayOflong[7] = paramLong8;
/* 283 */     paramArrayOflong[8] = paramLong9;
/* 284 */     paramArrayOflong[9] = paramLong10;
/*     */   }
/*     */   
/*     */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 288 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/* 289 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/* 290 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/* 291 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/* 292 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/* 293 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/* 294 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/* 295 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 296 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 297 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 298 */     long l11 = paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1];
/* 299 */     long l12 = paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2];
/* 300 */     long l13 = paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3];
/* 301 */     long l14 = paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4];
/* 302 */     long l15 = paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5];
/* 303 */     long l16 = paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6];
/* 304 */     long l17 = paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7];
/* 305 */     long l18 = paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8];
/* 306 */     long l19 = paramArrayOflong1[9] * paramArrayOflong2[9];
/*     */     
/* 308 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19);
/*     */   }
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/* 312 */     carryReduce(paramArrayOflong, paramArrayOflong[0], paramArrayOflong[1], paramArrayOflong[2], paramArrayOflong[3], paramArrayOflong[4], paramArrayOflong[5], paramArrayOflong[6], paramArrayOflong[7], paramArrayOflong[8], paramArrayOflong[9]);
/*     */   }
/*     */   
/*     */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 316 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 317 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 318 */     long l3 = 2L * paramArrayOflong1[0] * paramArrayOflong1[2] + paramArrayOflong1[1] * paramArrayOflong1[1];
/* 319 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 320 */     long l5 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]) + paramArrayOflong1[2] * paramArrayOflong1[2];
/* 321 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 322 */     long l7 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]) + paramArrayOflong1[3] * paramArrayOflong1[3];
/* 323 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 324 */     long l9 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]) + paramArrayOflong1[4] * paramArrayOflong1[4];
/* 325 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 326 */     long l11 = 2L * (paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]) + paramArrayOflong1[5] * paramArrayOflong1[5];
/* 327 */     long l12 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 328 */     long l13 = 2L * (paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]) + paramArrayOflong1[6] * paramArrayOflong1[6];
/* 329 */     long l14 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 330 */     long l15 = 2L * (paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]) + paramArrayOflong1[7] * paramArrayOflong1[7];
/* 331 */     long l16 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 332 */     long l17 = 2L * paramArrayOflong1[7] * paramArrayOflong1[9] + paramArrayOflong1[8] * paramArrayOflong1[8];
/* 333 */     long l18 = 2L * paramArrayOflong1[8] * paramArrayOflong1[9];
/* 334 */     long l19 = paramArrayOflong1[9] * paramArrayOflong1[9];
/*     */     
/* 336 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/IntegerPolynomialP256.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */