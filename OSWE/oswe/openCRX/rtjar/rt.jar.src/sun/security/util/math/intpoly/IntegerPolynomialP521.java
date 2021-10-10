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
/*     */ public class IntegerPolynomialP521
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   private static final int BITS_PER_LIMB = 28;
/*     */   private static final int NUM_LIMBS = 19;
/*     */   private static final int MAX_ADDS = 2;
/*  37 */   public static final BigInteger MODULUS = evaluateModulus();
/*     */   private static final long CARRY_ADD = 134217728L;
/*     */   private static final int LIMB_MASK = 268435455;
/*     */   
/*     */   public IntegerPolynomialP521() {
/*  42 */     super(28, 19, 2, MODULUS);
/*     */   }
/*     */   
/*     */   private static BigInteger evaluateModulus() {
/*  46 */     BigInteger bigInteger = BigInteger.valueOf(2L).pow(521);
/*  47 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(1L));
/*  48 */     return bigInteger;
/*     */   }
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*  52 */     long l = paramArrayOflong[18] >> 17L;
/*  53 */     paramArrayOflong[18] = paramArrayOflong[18] - (l << 17L);
/*  54 */     paramArrayOflong[0] = paramArrayOflong[0] + l;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31, long paramLong32, long paramLong33, long paramLong34, long paramLong35, long paramLong36, long paramLong37) {
/*  57 */     long l = 0L;
/*     */     
/*  59 */     paramLong18 += paramLong37 << 11L & 0xFFFFFFFL;
/*  60 */     paramLong19 += paramLong37 >> 17L;
/*     */     
/*  62 */     paramLong17 += paramLong36 << 11L & 0xFFFFFFFL;
/*  63 */     paramLong18 += paramLong36 >> 17L;
/*     */     
/*  65 */     paramLong16 += paramLong35 << 11L & 0xFFFFFFFL;
/*  66 */     paramLong17 += paramLong35 >> 17L;
/*     */     
/*  68 */     paramLong15 += paramLong34 << 11L & 0xFFFFFFFL;
/*  69 */     paramLong16 += paramLong34 >> 17L;
/*     */     
/*  71 */     paramLong14 += paramLong33 << 11L & 0xFFFFFFFL;
/*  72 */     paramLong15 += paramLong33 >> 17L;
/*     */     
/*  74 */     paramLong13 += paramLong32 << 11L & 0xFFFFFFFL;
/*  75 */     paramLong14 += paramLong32 >> 17L;
/*     */     
/*  77 */     paramLong12 += paramLong31 << 11L & 0xFFFFFFFL;
/*  78 */     paramLong13 += paramLong31 >> 17L;
/*     */     
/*  80 */     paramLong11 += paramLong30 << 11L & 0xFFFFFFFL;
/*  81 */     paramLong12 += paramLong30 >> 17L;
/*     */     
/*  83 */     paramLong10 += paramLong29 << 11L & 0xFFFFFFFL;
/*  84 */     paramLong11 += paramLong29 >> 17L;
/*     */     
/*  86 */     paramLong9 += paramLong28 << 11L & 0xFFFFFFFL;
/*  87 */     paramLong10 += paramLong28 >> 17L;
/*     */     
/*  89 */     paramLong8 += paramLong27 << 11L & 0xFFFFFFFL;
/*  90 */     paramLong9 += paramLong27 >> 17L;
/*     */     
/*  92 */     paramLong7 += paramLong26 << 11L & 0xFFFFFFFL;
/*  93 */     paramLong8 += paramLong26 >> 17L;
/*     */     
/*  95 */     paramLong6 += paramLong25 << 11L & 0xFFFFFFFL;
/*  96 */     paramLong7 += paramLong25 >> 17L;
/*     */     
/*  98 */     paramLong5 += paramLong24 << 11L & 0xFFFFFFFL;
/*  99 */     paramLong6 += paramLong24 >> 17L;
/*     */     
/* 101 */     paramLong4 += paramLong23 << 11L & 0xFFFFFFFL;
/* 102 */     paramLong5 += paramLong23 >> 17L;
/*     */     
/* 104 */     paramLong3 += paramLong22 << 11L & 0xFFFFFFFL;
/* 105 */     paramLong4 += paramLong22 >> 17L;
/*     */     
/* 107 */     paramLong2 += paramLong21 << 11L & 0xFFFFFFFL;
/* 108 */     paramLong3 += paramLong21 >> 17L;
/*     */     
/* 110 */     paramLong1 += paramLong20 << 11L & 0xFFFFFFFL;
/* 111 */     paramLong2 += paramLong20 >> 17L;
/* 112 */     paramLong20 = 0L;
/*     */     
/* 114 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, paramLong28, paramLong29, paramLong30, paramLong31, paramLong32, paramLong33, paramLong34, paramLong35, paramLong36, paramLong37, l);
/*     */   }
/*     */ 
/*     */   
/*     */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31, long paramLong32, long paramLong33, long paramLong34, long paramLong35, long paramLong36, long paramLong37, long paramLong38) {
/* 119 */     long l = paramLong18 + 134217728L >> 28L;
/* 120 */     paramLong18 -= l << 28L;
/* 121 */     paramLong19 += l;
/*     */     
/* 123 */     l = paramLong19 + 134217728L >> 28L;
/* 124 */     paramLong19 -= l << 28L;
/* 125 */     paramLong20 += l;
/*     */     
/* 127 */     paramLong1 += paramLong20 << 11L & 0xFFFFFFFL;
/* 128 */     paramLong2 += paramLong20 >> 17L;
/*     */     
/* 130 */     l = paramLong1 + 134217728L >> 28L;
/* 131 */     paramLong1 -= l << 28L;
/* 132 */     paramLong2 += l;
/*     */     
/* 134 */     l = paramLong2 + 134217728L >> 28L;
/* 135 */     paramLong2 -= l << 28L;
/* 136 */     paramLong3 += l;
/*     */     
/* 138 */     l = paramLong3 + 134217728L >> 28L;
/* 139 */     paramLong3 -= l << 28L;
/* 140 */     paramLong4 += l;
/*     */     
/* 142 */     l = paramLong4 + 134217728L >> 28L;
/* 143 */     paramLong4 -= l << 28L;
/* 144 */     paramLong5 += l;
/*     */     
/* 146 */     l = paramLong5 + 134217728L >> 28L;
/* 147 */     paramLong5 -= l << 28L;
/* 148 */     paramLong6 += l;
/*     */     
/* 150 */     l = paramLong6 + 134217728L >> 28L;
/* 151 */     paramLong6 -= l << 28L;
/* 152 */     paramLong7 += l;
/*     */     
/* 154 */     l = paramLong7 + 134217728L >> 28L;
/* 155 */     paramLong7 -= l << 28L;
/* 156 */     paramLong8 += l;
/*     */     
/* 158 */     l = paramLong8 + 134217728L >> 28L;
/* 159 */     paramLong8 -= l << 28L;
/* 160 */     paramLong9 += l;
/*     */     
/* 162 */     l = paramLong9 + 134217728L >> 28L;
/* 163 */     paramLong9 -= l << 28L;
/* 164 */     paramLong10 += l;
/*     */     
/* 166 */     l = paramLong10 + 134217728L >> 28L;
/* 167 */     paramLong10 -= l << 28L;
/* 168 */     paramLong11 += l;
/*     */     
/* 170 */     l = paramLong11 + 134217728L >> 28L;
/* 171 */     paramLong11 -= l << 28L;
/* 172 */     paramLong12 += l;
/*     */     
/* 174 */     l = paramLong12 + 134217728L >> 28L;
/* 175 */     paramLong12 -= l << 28L;
/* 176 */     paramLong13 += l;
/*     */     
/* 178 */     l = paramLong13 + 134217728L >> 28L;
/* 179 */     paramLong13 -= l << 28L;
/* 180 */     paramLong14 += l;
/*     */     
/* 182 */     l = paramLong14 + 134217728L >> 28L;
/* 183 */     paramLong14 -= l << 28L;
/* 184 */     paramLong15 += l;
/*     */     
/* 186 */     l = paramLong15 + 134217728L >> 28L;
/* 187 */     paramLong15 -= l << 28L;
/* 188 */     paramLong16 += l;
/*     */     
/* 190 */     l = paramLong16 + 134217728L >> 28L;
/* 191 */     paramLong16 -= l << 28L;
/* 192 */     paramLong17 += l;
/*     */     
/* 194 */     l = paramLong17 + 134217728L >> 28L;
/* 195 */     paramLong17 -= l << 28L;
/* 196 */     paramLong18 += l;
/*     */     
/* 198 */     l = paramLong18 + 134217728L >> 28L;
/* 199 */     paramLong18 -= l << 28L;
/* 200 */     paramLong19 += l;
/*     */     
/* 202 */     paramArrayOflong[0] = paramLong1;
/* 203 */     paramArrayOflong[1] = paramLong2;
/* 204 */     paramArrayOflong[2] = paramLong3;
/* 205 */     paramArrayOflong[3] = paramLong4;
/* 206 */     paramArrayOflong[4] = paramLong5;
/* 207 */     paramArrayOflong[5] = paramLong6;
/* 208 */     paramArrayOflong[6] = paramLong7;
/* 209 */     paramArrayOflong[7] = paramLong8;
/* 210 */     paramArrayOflong[8] = paramLong9;
/* 211 */     paramArrayOflong[9] = paramLong10;
/* 212 */     paramArrayOflong[10] = paramLong11;
/* 213 */     paramArrayOflong[11] = paramLong12;
/* 214 */     paramArrayOflong[12] = paramLong13;
/* 215 */     paramArrayOflong[13] = paramLong14;
/* 216 */     paramArrayOflong[14] = paramLong15;
/* 217 */     paramArrayOflong[15] = paramLong16;
/* 218 */     paramArrayOflong[16] = paramLong17;
/* 219 */     paramArrayOflong[17] = paramLong18;
/* 220 */     paramArrayOflong[18] = paramLong19;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19) {
/* 223 */     long l1 = 0L;
/*     */     
/* 225 */     long l2 = paramLong18 + 134217728L >> 28L;
/* 226 */     paramLong18 -= l2 << 28L;
/* 227 */     paramLong19 += l2;
/*     */     
/* 229 */     l2 = paramLong19 + 134217728L >> 28L;
/* 230 */     paramLong19 -= l2 << 28L;
/* 231 */     l1 += l2;
/*     */     
/* 233 */     paramLong1 += l1 << 11L & 0xFFFFFFFL;
/* 234 */     paramLong2 += l1 >> 17L;
/*     */     
/* 236 */     l2 = paramLong1 + 134217728L >> 28L;
/* 237 */     paramLong1 -= l2 << 28L;
/* 238 */     paramLong2 += l2;
/*     */     
/* 240 */     l2 = paramLong2 + 134217728L >> 28L;
/* 241 */     paramLong2 -= l2 << 28L;
/* 242 */     paramLong3 += l2;
/*     */     
/* 244 */     l2 = paramLong3 + 134217728L >> 28L;
/* 245 */     paramLong3 -= l2 << 28L;
/* 246 */     paramLong4 += l2;
/*     */     
/* 248 */     l2 = paramLong4 + 134217728L >> 28L;
/* 249 */     paramLong4 -= l2 << 28L;
/* 250 */     paramLong5 += l2;
/*     */     
/* 252 */     l2 = paramLong5 + 134217728L >> 28L;
/* 253 */     paramLong5 -= l2 << 28L;
/* 254 */     paramLong6 += l2;
/*     */     
/* 256 */     l2 = paramLong6 + 134217728L >> 28L;
/* 257 */     paramLong6 -= l2 << 28L;
/* 258 */     paramLong7 += l2;
/*     */     
/* 260 */     l2 = paramLong7 + 134217728L >> 28L;
/* 261 */     paramLong7 -= l2 << 28L;
/* 262 */     paramLong8 += l2;
/*     */     
/* 264 */     l2 = paramLong8 + 134217728L >> 28L;
/* 265 */     paramLong8 -= l2 << 28L;
/* 266 */     paramLong9 += l2;
/*     */     
/* 268 */     l2 = paramLong9 + 134217728L >> 28L;
/* 269 */     paramLong9 -= l2 << 28L;
/* 270 */     paramLong10 += l2;
/*     */     
/* 272 */     l2 = paramLong10 + 134217728L >> 28L;
/* 273 */     paramLong10 -= l2 << 28L;
/* 274 */     paramLong11 += l2;
/*     */     
/* 276 */     l2 = paramLong11 + 134217728L >> 28L;
/* 277 */     paramLong11 -= l2 << 28L;
/* 278 */     paramLong12 += l2;
/*     */     
/* 280 */     l2 = paramLong12 + 134217728L >> 28L;
/* 281 */     paramLong12 -= l2 << 28L;
/* 282 */     paramLong13 += l2;
/*     */     
/* 284 */     l2 = paramLong13 + 134217728L >> 28L;
/* 285 */     paramLong13 -= l2 << 28L;
/* 286 */     paramLong14 += l2;
/*     */     
/* 288 */     l2 = paramLong14 + 134217728L >> 28L;
/* 289 */     paramLong14 -= l2 << 28L;
/* 290 */     paramLong15 += l2;
/*     */     
/* 292 */     l2 = paramLong15 + 134217728L >> 28L;
/* 293 */     paramLong15 -= l2 << 28L;
/* 294 */     paramLong16 += l2;
/*     */     
/* 296 */     l2 = paramLong16 + 134217728L >> 28L;
/* 297 */     paramLong16 -= l2 << 28L;
/* 298 */     paramLong17 += l2;
/*     */     
/* 300 */     l2 = paramLong17 + 134217728L >> 28L;
/* 301 */     paramLong17 -= l2 << 28L;
/* 302 */     paramLong18 += l2;
/*     */     
/* 304 */     l2 = paramLong18 + 134217728L >> 28L;
/* 305 */     paramLong18 -= l2 << 28L;
/* 306 */     paramLong19 += l2;
/*     */     
/* 308 */     paramArrayOflong[0] = paramLong1;
/* 309 */     paramArrayOflong[1] = paramLong2;
/* 310 */     paramArrayOflong[2] = paramLong3;
/* 311 */     paramArrayOflong[3] = paramLong4;
/* 312 */     paramArrayOflong[4] = paramLong5;
/* 313 */     paramArrayOflong[5] = paramLong6;
/* 314 */     paramArrayOflong[6] = paramLong7;
/* 315 */     paramArrayOflong[7] = paramLong8;
/* 316 */     paramArrayOflong[8] = paramLong9;
/* 317 */     paramArrayOflong[9] = paramLong10;
/* 318 */     paramArrayOflong[10] = paramLong11;
/* 319 */     paramArrayOflong[11] = paramLong12;
/* 320 */     paramArrayOflong[12] = paramLong13;
/* 321 */     paramArrayOflong[13] = paramLong14;
/* 322 */     paramArrayOflong[14] = paramLong15;
/* 323 */     paramArrayOflong[15] = paramLong16;
/* 324 */     paramArrayOflong[16] = paramLong17;
/* 325 */     paramArrayOflong[17] = paramLong18;
/* 326 */     paramArrayOflong[18] = paramLong19;
/*     */   }
/*     */   
/*     */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 330 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/* 331 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/* 332 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/* 333 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/* 334 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/* 335 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/* 336 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/* 337 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 338 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 339 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 340 */     long l11 = paramArrayOflong1[0] * paramArrayOflong2[10] + paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1] + paramArrayOflong1[10] * paramArrayOflong2[0];
/* 341 */     long l12 = paramArrayOflong1[0] * paramArrayOflong2[11] + paramArrayOflong1[1] * paramArrayOflong2[10] + paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2] + paramArrayOflong1[10] * paramArrayOflong2[1] + paramArrayOflong1[11] * paramArrayOflong2[0];
/* 342 */     long l13 = paramArrayOflong1[0] * paramArrayOflong2[12] + paramArrayOflong1[1] * paramArrayOflong2[11] + paramArrayOflong1[2] * paramArrayOflong2[10] + paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3] + paramArrayOflong1[10] * paramArrayOflong2[2] + paramArrayOflong1[11] * paramArrayOflong2[1] + paramArrayOflong1[12] * paramArrayOflong2[0];
/* 343 */     long l14 = paramArrayOflong1[0] * paramArrayOflong2[13] + paramArrayOflong1[1] * paramArrayOflong2[12] + paramArrayOflong1[2] * paramArrayOflong2[11] + paramArrayOflong1[3] * paramArrayOflong2[10] + paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4] + paramArrayOflong1[10] * paramArrayOflong2[3] + paramArrayOflong1[11] * paramArrayOflong2[2] + paramArrayOflong1[12] * paramArrayOflong2[1] + paramArrayOflong1[13] * paramArrayOflong2[0];
/* 344 */     long l15 = paramArrayOflong1[0] * paramArrayOflong2[14] + paramArrayOflong1[1] * paramArrayOflong2[13] + paramArrayOflong1[2] * paramArrayOflong2[12] + paramArrayOflong1[3] * paramArrayOflong2[11] + paramArrayOflong1[4] * paramArrayOflong2[10] + paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5] + paramArrayOflong1[10] * paramArrayOflong2[4] + paramArrayOflong1[11] * paramArrayOflong2[3] + paramArrayOflong1[12] * paramArrayOflong2[2] + paramArrayOflong1[13] * paramArrayOflong2[1] + paramArrayOflong1[14] * paramArrayOflong2[0];
/* 345 */     long l16 = paramArrayOflong1[0] * paramArrayOflong2[15] + paramArrayOflong1[1] * paramArrayOflong2[14] + paramArrayOflong1[2] * paramArrayOflong2[13] + paramArrayOflong1[3] * paramArrayOflong2[12] + paramArrayOflong1[4] * paramArrayOflong2[11] + paramArrayOflong1[5] * paramArrayOflong2[10] + paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6] + paramArrayOflong1[10] * paramArrayOflong2[5] + paramArrayOflong1[11] * paramArrayOflong2[4] + paramArrayOflong1[12] * paramArrayOflong2[3] + paramArrayOflong1[13] * paramArrayOflong2[2] + paramArrayOflong1[14] * paramArrayOflong2[1] + paramArrayOflong1[15] * paramArrayOflong2[0];
/* 346 */     long l17 = paramArrayOflong1[0] * paramArrayOflong2[16] + paramArrayOflong1[1] * paramArrayOflong2[15] + paramArrayOflong1[2] * paramArrayOflong2[14] + paramArrayOflong1[3] * paramArrayOflong2[13] + paramArrayOflong1[4] * paramArrayOflong2[12] + paramArrayOflong1[5] * paramArrayOflong2[11] + paramArrayOflong1[6] * paramArrayOflong2[10] + paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7] + paramArrayOflong1[10] * paramArrayOflong2[6] + paramArrayOflong1[11] * paramArrayOflong2[5] + paramArrayOflong1[12] * paramArrayOflong2[4] + paramArrayOflong1[13] * paramArrayOflong2[3] + paramArrayOflong1[14] * paramArrayOflong2[2] + paramArrayOflong1[15] * paramArrayOflong2[1] + paramArrayOflong1[16] * paramArrayOflong2[0];
/* 347 */     long l18 = paramArrayOflong1[0] * paramArrayOflong2[17] + paramArrayOflong1[1] * paramArrayOflong2[16] + paramArrayOflong1[2] * paramArrayOflong2[15] + paramArrayOflong1[3] * paramArrayOflong2[14] + paramArrayOflong1[4] * paramArrayOflong2[13] + paramArrayOflong1[5] * paramArrayOflong2[12] + paramArrayOflong1[6] * paramArrayOflong2[11] + paramArrayOflong1[7] * paramArrayOflong2[10] + paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8] + paramArrayOflong1[10] * paramArrayOflong2[7] + paramArrayOflong1[11] * paramArrayOflong2[6] + paramArrayOflong1[12] * paramArrayOflong2[5] + paramArrayOflong1[13] * paramArrayOflong2[4] + paramArrayOflong1[14] * paramArrayOflong2[3] + paramArrayOflong1[15] * paramArrayOflong2[2] + paramArrayOflong1[16] * paramArrayOflong2[1] + paramArrayOflong1[17] * paramArrayOflong2[0];
/* 348 */     long l19 = paramArrayOflong1[0] * paramArrayOflong2[18] + paramArrayOflong1[1] * paramArrayOflong2[17] + paramArrayOflong1[2] * paramArrayOflong2[16] + paramArrayOflong1[3] * paramArrayOflong2[15] + paramArrayOflong1[4] * paramArrayOflong2[14] + paramArrayOflong1[5] * paramArrayOflong2[13] + paramArrayOflong1[6] * paramArrayOflong2[12] + paramArrayOflong1[7] * paramArrayOflong2[11] + paramArrayOflong1[8] * paramArrayOflong2[10] + paramArrayOflong1[9] * paramArrayOflong2[9] + paramArrayOflong1[10] * paramArrayOflong2[8] + paramArrayOflong1[11] * paramArrayOflong2[7] + paramArrayOflong1[12] * paramArrayOflong2[6] + paramArrayOflong1[13] * paramArrayOflong2[5] + paramArrayOflong1[14] * paramArrayOflong2[4] + paramArrayOflong1[15] * paramArrayOflong2[3] + paramArrayOflong1[16] * paramArrayOflong2[2] + paramArrayOflong1[17] * paramArrayOflong2[1] + paramArrayOflong1[18] * paramArrayOflong2[0];
/* 349 */     long l20 = paramArrayOflong1[1] * paramArrayOflong2[18] + paramArrayOflong1[2] * paramArrayOflong2[17] + paramArrayOflong1[3] * paramArrayOflong2[16] + paramArrayOflong1[4] * paramArrayOflong2[15] + paramArrayOflong1[5] * paramArrayOflong2[14] + paramArrayOflong1[6] * paramArrayOflong2[13] + paramArrayOflong1[7] * paramArrayOflong2[12] + paramArrayOflong1[8] * paramArrayOflong2[11] + paramArrayOflong1[9] * paramArrayOflong2[10] + paramArrayOflong1[10] * paramArrayOflong2[9] + paramArrayOflong1[11] * paramArrayOflong2[8] + paramArrayOflong1[12] * paramArrayOflong2[7] + paramArrayOflong1[13] * paramArrayOflong2[6] + paramArrayOflong1[14] * paramArrayOflong2[5] + paramArrayOflong1[15] * paramArrayOflong2[4] + paramArrayOflong1[16] * paramArrayOflong2[3] + paramArrayOflong1[17] * paramArrayOflong2[2] + paramArrayOflong1[18] * paramArrayOflong2[1];
/* 350 */     long l21 = paramArrayOflong1[2] * paramArrayOflong2[18] + paramArrayOflong1[3] * paramArrayOflong2[17] + paramArrayOflong1[4] * paramArrayOflong2[16] + paramArrayOflong1[5] * paramArrayOflong2[15] + paramArrayOflong1[6] * paramArrayOflong2[14] + paramArrayOflong1[7] * paramArrayOflong2[13] + paramArrayOflong1[8] * paramArrayOflong2[12] + paramArrayOflong1[9] * paramArrayOflong2[11] + paramArrayOflong1[10] * paramArrayOflong2[10] + paramArrayOflong1[11] * paramArrayOflong2[9] + paramArrayOflong1[12] * paramArrayOflong2[8] + paramArrayOflong1[13] * paramArrayOflong2[7] + paramArrayOflong1[14] * paramArrayOflong2[6] + paramArrayOflong1[15] * paramArrayOflong2[5] + paramArrayOflong1[16] * paramArrayOflong2[4] + paramArrayOflong1[17] * paramArrayOflong2[3] + paramArrayOflong1[18] * paramArrayOflong2[2];
/* 351 */     long l22 = paramArrayOflong1[3] * paramArrayOflong2[18] + paramArrayOflong1[4] * paramArrayOflong2[17] + paramArrayOflong1[5] * paramArrayOflong2[16] + paramArrayOflong1[6] * paramArrayOflong2[15] + paramArrayOflong1[7] * paramArrayOflong2[14] + paramArrayOflong1[8] * paramArrayOflong2[13] + paramArrayOflong1[9] * paramArrayOflong2[12] + paramArrayOflong1[10] * paramArrayOflong2[11] + paramArrayOflong1[11] * paramArrayOflong2[10] + paramArrayOflong1[12] * paramArrayOflong2[9] + paramArrayOflong1[13] * paramArrayOflong2[8] + paramArrayOflong1[14] * paramArrayOflong2[7] + paramArrayOflong1[15] * paramArrayOflong2[6] + paramArrayOflong1[16] * paramArrayOflong2[5] + paramArrayOflong1[17] * paramArrayOflong2[4] + paramArrayOflong1[18] * paramArrayOflong2[3];
/* 352 */     long l23 = paramArrayOflong1[4] * paramArrayOflong2[18] + paramArrayOflong1[5] * paramArrayOflong2[17] + paramArrayOflong1[6] * paramArrayOflong2[16] + paramArrayOflong1[7] * paramArrayOflong2[15] + paramArrayOflong1[8] * paramArrayOflong2[14] + paramArrayOflong1[9] * paramArrayOflong2[13] + paramArrayOflong1[10] * paramArrayOflong2[12] + paramArrayOflong1[11] * paramArrayOflong2[11] + paramArrayOflong1[12] * paramArrayOflong2[10] + paramArrayOflong1[13] * paramArrayOflong2[9] + paramArrayOflong1[14] * paramArrayOflong2[8] + paramArrayOflong1[15] * paramArrayOflong2[7] + paramArrayOflong1[16] * paramArrayOflong2[6] + paramArrayOflong1[17] * paramArrayOflong2[5] + paramArrayOflong1[18] * paramArrayOflong2[4];
/* 353 */     long l24 = paramArrayOflong1[5] * paramArrayOflong2[18] + paramArrayOflong1[6] * paramArrayOflong2[17] + paramArrayOflong1[7] * paramArrayOflong2[16] + paramArrayOflong1[8] * paramArrayOflong2[15] + paramArrayOflong1[9] * paramArrayOflong2[14] + paramArrayOflong1[10] * paramArrayOflong2[13] + paramArrayOflong1[11] * paramArrayOflong2[12] + paramArrayOflong1[12] * paramArrayOflong2[11] + paramArrayOflong1[13] * paramArrayOflong2[10] + paramArrayOflong1[14] * paramArrayOflong2[9] + paramArrayOflong1[15] * paramArrayOflong2[8] + paramArrayOflong1[16] * paramArrayOflong2[7] + paramArrayOflong1[17] * paramArrayOflong2[6] + paramArrayOflong1[18] * paramArrayOflong2[5];
/* 354 */     long l25 = paramArrayOflong1[6] * paramArrayOflong2[18] + paramArrayOflong1[7] * paramArrayOflong2[17] + paramArrayOflong1[8] * paramArrayOflong2[16] + paramArrayOflong1[9] * paramArrayOflong2[15] + paramArrayOflong1[10] * paramArrayOflong2[14] + paramArrayOflong1[11] * paramArrayOflong2[13] + paramArrayOflong1[12] * paramArrayOflong2[12] + paramArrayOflong1[13] * paramArrayOflong2[11] + paramArrayOflong1[14] * paramArrayOflong2[10] + paramArrayOflong1[15] * paramArrayOflong2[9] + paramArrayOflong1[16] * paramArrayOflong2[8] + paramArrayOflong1[17] * paramArrayOflong2[7] + paramArrayOflong1[18] * paramArrayOflong2[6];
/* 355 */     long l26 = paramArrayOflong1[7] * paramArrayOflong2[18] + paramArrayOflong1[8] * paramArrayOflong2[17] + paramArrayOflong1[9] * paramArrayOflong2[16] + paramArrayOflong1[10] * paramArrayOflong2[15] + paramArrayOflong1[11] * paramArrayOflong2[14] + paramArrayOflong1[12] * paramArrayOflong2[13] + paramArrayOflong1[13] * paramArrayOflong2[12] + paramArrayOflong1[14] * paramArrayOflong2[11] + paramArrayOflong1[15] * paramArrayOflong2[10] + paramArrayOflong1[16] * paramArrayOflong2[9] + paramArrayOflong1[17] * paramArrayOflong2[8] + paramArrayOflong1[18] * paramArrayOflong2[7];
/* 356 */     long l27 = paramArrayOflong1[8] * paramArrayOflong2[18] + paramArrayOflong1[9] * paramArrayOflong2[17] + paramArrayOflong1[10] * paramArrayOflong2[16] + paramArrayOflong1[11] * paramArrayOflong2[15] + paramArrayOflong1[12] * paramArrayOflong2[14] + paramArrayOflong1[13] * paramArrayOflong2[13] + paramArrayOflong1[14] * paramArrayOflong2[12] + paramArrayOflong1[15] * paramArrayOflong2[11] + paramArrayOflong1[16] * paramArrayOflong2[10] + paramArrayOflong1[17] * paramArrayOflong2[9] + paramArrayOflong1[18] * paramArrayOflong2[8];
/* 357 */     long l28 = paramArrayOflong1[9] * paramArrayOflong2[18] + paramArrayOflong1[10] * paramArrayOflong2[17] + paramArrayOflong1[11] * paramArrayOflong2[16] + paramArrayOflong1[12] * paramArrayOflong2[15] + paramArrayOflong1[13] * paramArrayOflong2[14] + paramArrayOflong1[14] * paramArrayOflong2[13] + paramArrayOflong1[15] * paramArrayOflong2[12] + paramArrayOflong1[16] * paramArrayOflong2[11] + paramArrayOflong1[17] * paramArrayOflong2[10] + paramArrayOflong1[18] * paramArrayOflong2[9];
/* 358 */     long l29 = paramArrayOflong1[10] * paramArrayOflong2[18] + paramArrayOflong1[11] * paramArrayOflong2[17] + paramArrayOflong1[12] * paramArrayOflong2[16] + paramArrayOflong1[13] * paramArrayOflong2[15] + paramArrayOflong1[14] * paramArrayOflong2[14] + paramArrayOflong1[15] * paramArrayOflong2[13] + paramArrayOflong1[16] * paramArrayOflong2[12] + paramArrayOflong1[17] * paramArrayOflong2[11] + paramArrayOflong1[18] * paramArrayOflong2[10];
/* 359 */     long l30 = paramArrayOflong1[11] * paramArrayOflong2[18] + paramArrayOflong1[12] * paramArrayOflong2[17] + paramArrayOflong1[13] * paramArrayOflong2[16] + paramArrayOflong1[14] * paramArrayOflong2[15] + paramArrayOflong1[15] * paramArrayOflong2[14] + paramArrayOflong1[16] * paramArrayOflong2[13] + paramArrayOflong1[17] * paramArrayOflong2[12] + paramArrayOflong1[18] * paramArrayOflong2[11];
/* 360 */     long l31 = paramArrayOflong1[12] * paramArrayOflong2[18] + paramArrayOflong1[13] * paramArrayOflong2[17] + paramArrayOflong1[14] * paramArrayOflong2[16] + paramArrayOflong1[15] * paramArrayOflong2[15] + paramArrayOflong1[16] * paramArrayOflong2[14] + paramArrayOflong1[17] * paramArrayOflong2[13] + paramArrayOflong1[18] * paramArrayOflong2[12];
/* 361 */     long l32 = paramArrayOflong1[13] * paramArrayOflong2[18] + paramArrayOflong1[14] * paramArrayOflong2[17] + paramArrayOflong1[15] * paramArrayOflong2[16] + paramArrayOflong1[16] * paramArrayOflong2[15] + paramArrayOflong1[17] * paramArrayOflong2[14] + paramArrayOflong1[18] * paramArrayOflong2[13];
/* 362 */     long l33 = paramArrayOflong1[14] * paramArrayOflong2[18] + paramArrayOflong1[15] * paramArrayOflong2[17] + paramArrayOflong1[16] * paramArrayOflong2[16] + paramArrayOflong1[17] * paramArrayOflong2[15] + paramArrayOflong1[18] * paramArrayOflong2[14];
/* 363 */     long l34 = paramArrayOflong1[15] * paramArrayOflong2[18] + paramArrayOflong1[16] * paramArrayOflong2[17] + paramArrayOflong1[17] * paramArrayOflong2[16] + paramArrayOflong1[18] * paramArrayOflong2[15];
/* 364 */     long l35 = paramArrayOflong1[16] * paramArrayOflong2[18] + paramArrayOflong1[17] * paramArrayOflong2[17] + paramArrayOflong1[18] * paramArrayOflong2[16];
/* 365 */     long l36 = paramArrayOflong1[17] * paramArrayOflong2[18] + paramArrayOflong1[18] * paramArrayOflong2[17];
/* 366 */     long l37 = paramArrayOflong1[18] * paramArrayOflong2[18];
/*     */     
/* 368 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32, l33, l34, l35, l36, l37);
/*     */   }
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/* 372 */     carryReduce(paramArrayOflong, paramArrayOflong[0], paramArrayOflong[1], paramArrayOflong[2], paramArrayOflong[3], paramArrayOflong[4], paramArrayOflong[5], paramArrayOflong[6], paramArrayOflong[7], paramArrayOflong[8], paramArrayOflong[9], paramArrayOflong[10], paramArrayOflong[11], paramArrayOflong[12], paramArrayOflong[13], paramArrayOflong[14], paramArrayOflong[15], paramArrayOflong[16], paramArrayOflong[17], paramArrayOflong[18]);
/*     */   }
/*     */   
/*     */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 376 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 377 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 378 */     long l3 = 2L * paramArrayOflong1[0] * paramArrayOflong1[2] + paramArrayOflong1[1] * paramArrayOflong1[1];
/* 379 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 380 */     long l5 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]) + paramArrayOflong1[2] * paramArrayOflong1[2];
/* 381 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 382 */     long l7 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]) + paramArrayOflong1[3] * paramArrayOflong1[3];
/* 383 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 384 */     long l9 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]) + paramArrayOflong1[4] * paramArrayOflong1[4];
/* 385 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 386 */     long l11 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[10] + paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]) + paramArrayOflong1[5] * paramArrayOflong1[5];
/* 387 */     long l12 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[11] + paramArrayOflong1[1] * paramArrayOflong1[10] + paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 388 */     long l13 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[12] + paramArrayOflong1[1] * paramArrayOflong1[11] + paramArrayOflong1[2] * paramArrayOflong1[10] + paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]) + paramArrayOflong1[6] * paramArrayOflong1[6];
/* 389 */     long l14 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[13] + paramArrayOflong1[1] * paramArrayOflong1[12] + paramArrayOflong1[2] * paramArrayOflong1[11] + paramArrayOflong1[3] * paramArrayOflong1[10] + paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 390 */     long l15 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[14] + paramArrayOflong1[1] * paramArrayOflong1[13] + paramArrayOflong1[2] * paramArrayOflong1[12] + paramArrayOflong1[3] * paramArrayOflong1[11] + paramArrayOflong1[4] * paramArrayOflong1[10] + paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]) + paramArrayOflong1[7] * paramArrayOflong1[7];
/* 391 */     long l16 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[15] + paramArrayOflong1[1] * paramArrayOflong1[14] + paramArrayOflong1[2] * paramArrayOflong1[13] + paramArrayOflong1[3] * paramArrayOflong1[12] + paramArrayOflong1[4] * paramArrayOflong1[11] + paramArrayOflong1[5] * paramArrayOflong1[10] + paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 392 */     long l17 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[16] + paramArrayOflong1[1] * paramArrayOflong1[15] + paramArrayOflong1[2] * paramArrayOflong1[14] + paramArrayOflong1[3] * paramArrayOflong1[13] + paramArrayOflong1[4] * paramArrayOflong1[12] + paramArrayOflong1[5] * paramArrayOflong1[11] + paramArrayOflong1[6] * paramArrayOflong1[10] + paramArrayOflong1[7] * paramArrayOflong1[9]) + paramArrayOflong1[8] * paramArrayOflong1[8];
/* 393 */     long l18 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[17] + paramArrayOflong1[1] * paramArrayOflong1[16] + paramArrayOflong1[2] * paramArrayOflong1[15] + paramArrayOflong1[3] * paramArrayOflong1[14] + paramArrayOflong1[4] * paramArrayOflong1[13] + paramArrayOflong1[5] * paramArrayOflong1[12] + paramArrayOflong1[6] * paramArrayOflong1[11] + paramArrayOflong1[7] * paramArrayOflong1[10] + paramArrayOflong1[8] * paramArrayOflong1[9]);
/* 394 */     long l19 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[18] + paramArrayOflong1[1] * paramArrayOflong1[17] + paramArrayOflong1[2] * paramArrayOflong1[16] + paramArrayOflong1[3] * paramArrayOflong1[15] + paramArrayOflong1[4] * paramArrayOflong1[14] + paramArrayOflong1[5] * paramArrayOflong1[13] + paramArrayOflong1[6] * paramArrayOflong1[12] + paramArrayOflong1[7] * paramArrayOflong1[11] + paramArrayOflong1[8] * paramArrayOflong1[10]) + paramArrayOflong1[9] * paramArrayOflong1[9];
/* 395 */     long l20 = 2L * (paramArrayOflong1[1] * paramArrayOflong1[18] + paramArrayOflong1[2] * paramArrayOflong1[17] + paramArrayOflong1[3] * paramArrayOflong1[16] + paramArrayOflong1[4] * paramArrayOflong1[15] + paramArrayOflong1[5] * paramArrayOflong1[14] + paramArrayOflong1[6] * paramArrayOflong1[13] + paramArrayOflong1[7] * paramArrayOflong1[12] + paramArrayOflong1[8] * paramArrayOflong1[11] + paramArrayOflong1[9] * paramArrayOflong1[10]);
/* 396 */     long l21 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[18] + paramArrayOflong1[3] * paramArrayOflong1[17] + paramArrayOflong1[4] * paramArrayOflong1[16] + paramArrayOflong1[5] * paramArrayOflong1[15] + paramArrayOflong1[6] * paramArrayOflong1[14] + paramArrayOflong1[7] * paramArrayOflong1[13] + paramArrayOflong1[8] * paramArrayOflong1[12] + paramArrayOflong1[9] * paramArrayOflong1[11]) + paramArrayOflong1[10] * paramArrayOflong1[10];
/* 397 */     long l22 = 2L * (paramArrayOflong1[3] * paramArrayOflong1[18] + paramArrayOflong1[4] * paramArrayOflong1[17] + paramArrayOflong1[5] * paramArrayOflong1[16] + paramArrayOflong1[6] * paramArrayOflong1[15] + paramArrayOflong1[7] * paramArrayOflong1[14] + paramArrayOflong1[8] * paramArrayOflong1[13] + paramArrayOflong1[9] * paramArrayOflong1[12] + paramArrayOflong1[10] * paramArrayOflong1[11]);
/* 398 */     long l23 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[18] + paramArrayOflong1[5] * paramArrayOflong1[17] + paramArrayOflong1[6] * paramArrayOflong1[16] + paramArrayOflong1[7] * paramArrayOflong1[15] + paramArrayOflong1[8] * paramArrayOflong1[14] + paramArrayOflong1[9] * paramArrayOflong1[13] + paramArrayOflong1[10] * paramArrayOflong1[12]) + paramArrayOflong1[11] * paramArrayOflong1[11];
/* 399 */     long l24 = 2L * (paramArrayOflong1[5] * paramArrayOflong1[18] + paramArrayOflong1[6] * paramArrayOflong1[17] + paramArrayOflong1[7] * paramArrayOflong1[16] + paramArrayOflong1[8] * paramArrayOflong1[15] + paramArrayOflong1[9] * paramArrayOflong1[14] + paramArrayOflong1[10] * paramArrayOflong1[13] + paramArrayOflong1[11] * paramArrayOflong1[12]);
/* 400 */     long l25 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[18] + paramArrayOflong1[7] * paramArrayOflong1[17] + paramArrayOflong1[8] * paramArrayOflong1[16] + paramArrayOflong1[9] * paramArrayOflong1[15] + paramArrayOflong1[10] * paramArrayOflong1[14] + paramArrayOflong1[11] * paramArrayOflong1[13]) + paramArrayOflong1[12] * paramArrayOflong1[12];
/* 401 */     long l26 = 2L * (paramArrayOflong1[7] * paramArrayOflong1[18] + paramArrayOflong1[8] * paramArrayOflong1[17] + paramArrayOflong1[9] * paramArrayOflong1[16] + paramArrayOflong1[10] * paramArrayOflong1[15] + paramArrayOflong1[11] * paramArrayOflong1[14] + paramArrayOflong1[12] * paramArrayOflong1[13]);
/* 402 */     long l27 = 2L * (paramArrayOflong1[8] * paramArrayOflong1[18] + paramArrayOflong1[9] * paramArrayOflong1[17] + paramArrayOflong1[10] * paramArrayOflong1[16] + paramArrayOflong1[11] * paramArrayOflong1[15] + paramArrayOflong1[12] * paramArrayOflong1[14]) + paramArrayOflong1[13] * paramArrayOflong1[13];
/* 403 */     long l28 = 2L * (paramArrayOflong1[9] * paramArrayOflong1[18] + paramArrayOflong1[10] * paramArrayOflong1[17] + paramArrayOflong1[11] * paramArrayOflong1[16] + paramArrayOflong1[12] * paramArrayOflong1[15] + paramArrayOflong1[13] * paramArrayOflong1[14]);
/* 404 */     long l29 = 2L * (paramArrayOflong1[10] * paramArrayOflong1[18] + paramArrayOflong1[11] * paramArrayOflong1[17] + paramArrayOflong1[12] * paramArrayOflong1[16] + paramArrayOflong1[13] * paramArrayOflong1[15]) + paramArrayOflong1[14] * paramArrayOflong1[14];
/* 405 */     long l30 = 2L * (paramArrayOflong1[11] * paramArrayOflong1[18] + paramArrayOflong1[12] * paramArrayOflong1[17] + paramArrayOflong1[13] * paramArrayOflong1[16] + paramArrayOflong1[14] * paramArrayOflong1[15]);
/* 406 */     long l31 = 2L * (paramArrayOflong1[12] * paramArrayOflong1[18] + paramArrayOflong1[13] * paramArrayOflong1[17] + paramArrayOflong1[14] * paramArrayOflong1[16]) + paramArrayOflong1[15] * paramArrayOflong1[15];
/* 407 */     long l32 = 2L * (paramArrayOflong1[13] * paramArrayOflong1[18] + paramArrayOflong1[14] * paramArrayOflong1[17] + paramArrayOflong1[15] * paramArrayOflong1[16]);
/* 408 */     long l33 = 2L * (paramArrayOflong1[14] * paramArrayOflong1[18] + paramArrayOflong1[15] * paramArrayOflong1[17]) + paramArrayOflong1[16] * paramArrayOflong1[16];
/* 409 */     long l34 = 2L * (paramArrayOflong1[15] * paramArrayOflong1[18] + paramArrayOflong1[16] * paramArrayOflong1[17]);
/* 410 */     long l35 = 2L * paramArrayOflong1[16] * paramArrayOflong1[18] + paramArrayOflong1[17] * paramArrayOflong1[17];
/* 411 */     long l36 = 2L * paramArrayOflong1[17] * paramArrayOflong1[18];
/* 412 */     long l37 = paramArrayOflong1[18] * paramArrayOflong1[18];
/*     */     
/* 414 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32, l33, l34, l35, l36, l37);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/IntegerPolynomialP521.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */