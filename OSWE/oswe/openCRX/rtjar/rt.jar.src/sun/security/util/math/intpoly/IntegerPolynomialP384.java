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
/*     */ public class IntegerPolynomialP384
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   private static final int BITS_PER_LIMB = 28;
/*     */   private static final int NUM_LIMBS = 14;
/*     */   private static final int MAX_ADDS = 2;
/*  37 */   public static final BigInteger MODULUS = evaluateModulus();
/*     */   private static final long CARRY_ADD = 134217728L;
/*     */   private static final int LIMB_MASK = 268435455;
/*     */   
/*     */   public IntegerPolynomialP384() {
/*  42 */     super(28, 14, 2, MODULUS);
/*     */   }
/*     */   
/*     */   private static BigInteger evaluateModulus() {
/*  46 */     BigInteger bigInteger = BigInteger.valueOf(2L).pow(384);
/*  47 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(128));
/*  48 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(96));
/*  49 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(32));
/*  50 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(1L));
/*  51 */     return bigInteger;
/*     */   }
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*  55 */     long l = paramArrayOflong[13] >> 20L;
/*  56 */     paramArrayOflong[13] = paramArrayOflong[13] - (l << 20L);
/*  57 */     paramArrayOflong[4] = paramArrayOflong[4] + (l << 16L & 0xFFFFFFFL);
/*  58 */     paramArrayOflong[5] = paramArrayOflong[5] + (l >> 12L);
/*  59 */     paramArrayOflong[3] = paramArrayOflong[3] + (l << 12L & 0xFFFFFFFL);
/*  60 */     paramArrayOflong[4] = paramArrayOflong[4] + (l >> 16L);
/*  61 */     paramArrayOflong[1] = paramArrayOflong[1] - (l << 4L & 0xFFFFFFFL);
/*  62 */     paramArrayOflong[2] = paramArrayOflong[2] - (l >> 24L);
/*  63 */     paramArrayOflong[0] = paramArrayOflong[0] + l;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27) {
/*  66 */     long l = 0L;
/*     */     
/*  68 */     paramLong17 += paramLong27 << 24L & 0xFFFFFFFL;
/*  69 */     paramLong18 += paramLong27 >> 4L;
/*  70 */     paramLong16 += paramLong27 << 20L & 0xFFFFFFFL;
/*  71 */     paramLong17 += paramLong27 >> 8L;
/*  72 */     paramLong14 -= paramLong27 << 12L & 0xFFFFFFFL;
/*  73 */     paramLong15 -= paramLong27 >> 16L;
/*  74 */     paramLong13 += paramLong27 << 8L & 0xFFFFFFFL;
/*  75 */     paramLong14 += paramLong27 >> 20L;
/*     */     
/*  77 */     paramLong16 += paramLong26 << 24L & 0xFFFFFFFL;
/*  78 */     paramLong17 += paramLong26 >> 4L;
/*  79 */     paramLong15 += paramLong26 << 20L & 0xFFFFFFFL;
/*  80 */     paramLong16 += paramLong26 >> 8L;
/*  81 */     paramLong13 -= paramLong26 << 12L & 0xFFFFFFFL;
/*  82 */     paramLong14 -= paramLong26 >> 16L;
/*  83 */     paramLong12 += paramLong26 << 8L & 0xFFFFFFFL;
/*  84 */     paramLong13 += paramLong26 >> 20L;
/*     */     
/*  86 */     paramLong15 += paramLong25 << 24L & 0xFFFFFFFL;
/*  87 */     paramLong16 += paramLong25 >> 4L;
/*  88 */     paramLong14 += paramLong25 << 20L & 0xFFFFFFFL;
/*  89 */     paramLong15 += paramLong25 >> 8L;
/*  90 */     paramLong12 -= paramLong25 << 12L & 0xFFFFFFFL;
/*  91 */     paramLong13 -= paramLong25 >> 16L;
/*  92 */     paramLong11 += paramLong25 << 8L & 0xFFFFFFFL;
/*  93 */     paramLong12 += paramLong25 >> 20L;
/*     */     
/*  95 */     paramLong14 += paramLong24 << 24L & 0xFFFFFFFL;
/*  96 */     paramLong15 += paramLong24 >> 4L;
/*  97 */     paramLong13 += paramLong24 << 20L & 0xFFFFFFFL;
/*  98 */     paramLong14 += paramLong24 >> 8L;
/*  99 */     paramLong11 -= paramLong24 << 12L & 0xFFFFFFFL;
/* 100 */     paramLong12 -= paramLong24 >> 16L;
/* 101 */     paramLong10 += paramLong24 << 8L & 0xFFFFFFFL;
/* 102 */     paramLong11 += paramLong24 >> 20L;
/*     */     
/* 104 */     paramLong13 += paramLong23 << 24L & 0xFFFFFFFL;
/* 105 */     paramLong14 += paramLong23 >> 4L;
/* 106 */     paramLong12 += paramLong23 << 20L & 0xFFFFFFFL;
/* 107 */     paramLong13 += paramLong23 >> 8L;
/* 108 */     paramLong10 -= paramLong23 << 12L & 0xFFFFFFFL;
/* 109 */     paramLong11 -= paramLong23 >> 16L;
/* 110 */     paramLong9 += paramLong23 << 8L & 0xFFFFFFFL;
/* 111 */     paramLong10 += paramLong23 >> 20L;
/*     */     
/* 113 */     paramLong12 += paramLong22 << 24L & 0xFFFFFFFL;
/* 114 */     paramLong13 += paramLong22 >> 4L;
/* 115 */     paramLong11 += paramLong22 << 20L & 0xFFFFFFFL;
/* 116 */     paramLong12 += paramLong22 >> 8L;
/* 117 */     paramLong9 -= paramLong22 << 12L & 0xFFFFFFFL;
/* 118 */     paramLong10 -= paramLong22 >> 16L;
/* 119 */     paramLong8 += paramLong22 << 8L & 0xFFFFFFFL;
/* 120 */     paramLong9 += paramLong22 >> 20L;
/*     */     
/* 122 */     paramLong11 += paramLong21 << 24L & 0xFFFFFFFL;
/* 123 */     paramLong12 += paramLong21 >> 4L;
/* 124 */     paramLong10 += paramLong21 << 20L & 0xFFFFFFFL;
/* 125 */     paramLong11 += paramLong21 >> 8L;
/* 126 */     paramLong8 -= paramLong21 << 12L & 0xFFFFFFFL;
/* 127 */     paramLong9 -= paramLong21 >> 16L;
/* 128 */     paramLong7 += paramLong21 << 8L & 0xFFFFFFFL;
/* 129 */     paramLong8 += paramLong21 >> 20L;
/*     */     
/* 131 */     paramLong10 += paramLong20 << 24L & 0xFFFFFFFL;
/* 132 */     paramLong11 += paramLong20 >> 4L;
/* 133 */     paramLong9 += paramLong20 << 20L & 0xFFFFFFFL;
/* 134 */     paramLong10 += paramLong20 >> 8L;
/* 135 */     paramLong7 -= paramLong20 << 12L & 0xFFFFFFFL;
/* 136 */     paramLong8 -= paramLong20 >> 16L;
/* 137 */     paramLong6 += paramLong20 << 8L & 0xFFFFFFFL;
/* 138 */     paramLong7 += paramLong20 >> 20L;
/*     */     
/* 140 */     paramLong9 += paramLong19 << 24L & 0xFFFFFFFL;
/* 141 */     paramLong10 += paramLong19 >> 4L;
/* 142 */     paramLong8 += paramLong19 << 20L & 0xFFFFFFFL;
/* 143 */     paramLong9 += paramLong19 >> 8L;
/* 144 */     paramLong6 -= paramLong19 << 12L & 0xFFFFFFFL;
/* 145 */     paramLong7 -= paramLong19 >> 16L;
/* 146 */     paramLong5 += paramLong19 << 8L & 0xFFFFFFFL;
/* 147 */     paramLong6 += paramLong19 >> 20L;
/*     */     
/* 149 */     paramLong8 += paramLong18 << 24L & 0xFFFFFFFL;
/* 150 */     paramLong9 += paramLong18 >> 4L;
/* 151 */     paramLong7 += paramLong18 << 20L & 0xFFFFFFFL;
/* 152 */     paramLong8 += paramLong18 >> 8L;
/* 153 */     paramLong5 -= paramLong18 << 12L & 0xFFFFFFFL;
/* 154 */     paramLong6 -= paramLong18 >> 16L;
/* 155 */     paramLong4 += paramLong18 << 8L & 0xFFFFFFFL;
/* 156 */     paramLong5 += paramLong18 >> 20L;
/*     */     
/* 158 */     paramLong7 += paramLong17 << 24L & 0xFFFFFFFL;
/* 159 */     paramLong8 += paramLong17 >> 4L;
/* 160 */     paramLong6 += paramLong17 << 20L & 0xFFFFFFFL;
/* 161 */     paramLong7 += paramLong17 >> 8L;
/* 162 */     paramLong4 -= paramLong17 << 12L & 0xFFFFFFFL;
/* 163 */     paramLong5 -= paramLong17 >> 16L;
/* 164 */     paramLong3 += paramLong17 << 8L & 0xFFFFFFFL;
/* 165 */     paramLong4 += paramLong17 >> 20L;
/*     */     
/* 167 */     paramLong6 += paramLong16 << 24L & 0xFFFFFFFL;
/* 168 */     paramLong7 += paramLong16 >> 4L;
/* 169 */     paramLong5 += paramLong16 << 20L & 0xFFFFFFFL;
/* 170 */     paramLong6 += paramLong16 >> 8L;
/* 171 */     paramLong3 -= paramLong16 << 12L & 0xFFFFFFFL;
/* 172 */     paramLong4 -= paramLong16 >> 16L;
/* 173 */     paramLong2 += paramLong16 << 8L & 0xFFFFFFFL;
/* 174 */     paramLong3 += paramLong16 >> 20L;
/*     */     
/* 176 */     paramLong5 += paramLong15 << 24L & 0xFFFFFFFL;
/* 177 */     paramLong6 += paramLong15 >> 4L;
/* 178 */     paramLong4 += paramLong15 << 20L & 0xFFFFFFFL;
/* 179 */     paramLong5 += paramLong15 >> 8L;
/* 180 */     paramLong2 -= paramLong15 << 12L & 0xFFFFFFFL;
/* 181 */     paramLong3 -= paramLong15 >> 16L;
/* 182 */     paramLong1 += paramLong15 << 8L & 0xFFFFFFFL;
/* 183 */     paramLong2 += paramLong15 >> 20L;
/* 184 */     paramLong15 = 0L;
/*     */     
/* 186 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, l);
/*     */   }
/*     */ 
/*     */   
/*     */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28) {
/* 191 */     long l = paramLong13 + 134217728L >> 28L;
/* 192 */     paramLong13 -= l << 28L;
/* 193 */     paramLong14 += l;
/*     */     
/* 195 */     l = paramLong14 + 134217728L >> 28L;
/* 196 */     paramLong14 -= l << 28L;
/* 197 */     paramLong15 += l;
/*     */     
/* 199 */     paramLong5 += paramLong15 << 24L & 0xFFFFFFFL;
/* 200 */     paramLong6 += paramLong15 >> 4L;
/* 201 */     paramLong4 += paramLong15 << 20L & 0xFFFFFFFL;
/* 202 */     paramLong5 += paramLong15 >> 8L;
/* 203 */     paramLong2 -= paramLong15 << 12L & 0xFFFFFFFL;
/* 204 */     paramLong3 -= paramLong15 >> 16L;
/* 205 */     paramLong1 += paramLong15 << 8L & 0xFFFFFFFL;
/* 206 */     paramLong2 += paramLong15 >> 20L;
/*     */     
/* 208 */     l = paramLong1 + 134217728L >> 28L;
/* 209 */     paramLong1 -= l << 28L;
/* 210 */     paramLong2 += l;
/*     */     
/* 212 */     l = paramLong2 + 134217728L >> 28L;
/* 213 */     paramLong2 -= l << 28L;
/* 214 */     paramLong3 += l;
/*     */     
/* 216 */     l = paramLong3 + 134217728L >> 28L;
/* 217 */     paramLong3 -= l << 28L;
/* 218 */     paramLong4 += l;
/*     */     
/* 220 */     l = paramLong4 + 134217728L >> 28L;
/* 221 */     paramLong4 -= l << 28L;
/* 222 */     paramLong5 += l;
/*     */     
/* 224 */     l = paramLong5 + 134217728L >> 28L;
/* 225 */     paramLong5 -= l << 28L;
/* 226 */     paramLong6 += l;
/*     */     
/* 228 */     l = paramLong6 + 134217728L >> 28L;
/* 229 */     paramLong6 -= l << 28L;
/* 230 */     paramLong7 += l;
/*     */     
/* 232 */     l = paramLong7 + 134217728L >> 28L;
/* 233 */     paramLong7 -= l << 28L;
/* 234 */     paramLong8 += l;
/*     */     
/* 236 */     l = paramLong8 + 134217728L >> 28L;
/* 237 */     paramLong8 -= l << 28L;
/* 238 */     paramLong9 += l;
/*     */     
/* 240 */     l = paramLong9 + 134217728L >> 28L;
/* 241 */     paramLong9 -= l << 28L;
/* 242 */     paramLong10 += l;
/*     */     
/* 244 */     l = paramLong10 + 134217728L >> 28L;
/* 245 */     paramLong10 -= l << 28L;
/* 246 */     paramLong11 += l;
/*     */     
/* 248 */     l = paramLong11 + 134217728L >> 28L;
/* 249 */     paramLong11 -= l << 28L;
/* 250 */     paramLong12 += l;
/*     */     
/* 252 */     l = paramLong12 + 134217728L >> 28L;
/* 253 */     paramLong12 -= l << 28L;
/* 254 */     paramLong13 += l;
/*     */     
/* 256 */     l = paramLong13 + 134217728L >> 28L;
/* 257 */     paramLong13 -= l << 28L;
/* 258 */     paramLong14 += l;
/*     */     
/* 260 */     paramArrayOflong[0] = paramLong1;
/* 261 */     paramArrayOflong[1] = paramLong2;
/* 262 */     paramArrayOflong[2] = paramLong3;
/* 263 */     paramArrayOflong[3] = paramLong4;
/* 264 */     paramArrayOflong[4] = paramLong5;
/* 265 */     paramArrayOflong[5] = paramLong6;
/* 266 */     paramArrayOflong[6] = paramLong7;
/* 267 */     paramArrayOflong[7] = paramLong8;
/* 268 */     paramArrayOflong[8] = paramLong9;
/* 269 */     paramArrayOflong[9] = paramLong10;
/* 270 */     paramArrayOflong[10] = paramLong11;
/* 271 */     paramArrayOflong[11] = paramLong12;
/* 272 */     paramArrayOflong[12] = paramLong13;
/* 273 */     paramArrayOflong[13] = paramLong14;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14) {
/* 276 */     long l1 = 0L;
/*     */     
/* 278 */     long l2 = paramLong13 + 134217728L >> 28L;
/* 279 */     paramLong13 -= l2 << 28L;
/* 280 */     paramLong14 += l2;
/*     */     
/* 282 */     l2 = paramLong14 + 134217728L >> 28L;
/* 283 */     paramLong14 -= l2 << 28L;
/* 284 */     l1 += l2;
/*     */     
/* 286 */     paramLong5 += l1 << 24L & 0xFFFFFFFL;
/* 287 */     paramLong6 += l1 >> 4L;
/* 288 */     paramLong4 += l1 << 20L & 0xFFFFFFFL;
/* 289 */     paramLong5 += l1 >> 8L;
/* 290 */     paramLong2 -= l1 << 12L & 0xFFFFFFFL;
/* 291 */     paramLong3 -= l1 >> 16L;
/* 292 */     paramLong1 += l1 << 8L & 0xFFFFFFFL;
/* 293 */     paramLong2 += l1 >> 20L;
/*     */     
/* 295 */     l2 = paramLong1 + 134217728L >> 28L;
/* 296 */     paramLong1 -= l2 << 28L;
/* 297 */     paramLong2 += l2;
/*     */     
/* 299 */     l2 = paramLong2 + 134217728L >> 28L;
/* 300 */     paramLong2 -= l2 << 28L;
/* 301 */     paramLong3 += l2;
/*     */     
/* 303 */     l2 = paramLong3 + 134217728L >> 28L;
/* 304 */     paramLong3 -= l2 << 28L;
/* 305 */     paramLong4 += l2;
/*     */     
/* 307 */     l2 = paramLong4 + 134217728L >> 28L;
/* 308 */     paramLong4 -= l2 << 28L;
/* 309 */     paramLong5 += l2;
/*     */     
/* 311 */     l2 = paramLong5 + 134217728L >> 28L;
/* 312 */     paramLong5 -= l2 << 28L;
/* 313 */     paramLong6 += l2;
/*     */     
/* 315 */     l2 = paramLong6 + 134217728L >> 28L;
/* 316 */     paramLong6 -= l2 << 28L;
/* 317 */     paramLong7 += l2;
/*     */     
/* 319 */     l2 = paramLong7 + 134217728L >> 28L;
/* 320 */     paramLong7 -= l2 << 28L;
/* 321 */     paramLong8 += l2;
/*     */     
/* 323 */     l2 = paramLong8 + 134217728L >> 28L;
/* 324 */     paramLong8 -= l2 << 28L;
/* 325 */     paramLong9 += l2;
/*     */     
/* 327 */     l2 = paramLong9 + 134217728L >> 28L;
/* 328 */     paramLong9 -= l2 << 28L;
/* 329 */     paramLong10 += l2;
/*     */     
/* 331 */     l2 = paramLong10 + 134217728L >> 28L;
/* 332 */     paramLong10 -= l2 << 28L;
/* 333 */     paramLong11 += l2;
/*     */     
/* 335 */     l2 = paramLong11 + 134217728L >> 28L;
/* 336 */     paramLong11 -= l2 << 28L;
/* 337 */     paramLong12 += l2;
/*     */     
/* 339 */     l2 = paramLong12 + 134217728L >> 28L;
/* 340 */     paramLong12 -= l2 << 28L;
/* 341 */     paramLong13 += l2;
/*     */     
/* 343 */     l2 = paramLong13 + 134217728L >> 28L;
/* 344 */     paramLong13 -= l2 << 28L;
/* 345 */     paramLong14 += l2;
/*     */     
/* 347 */     paramArrayOflong[0] = paramLong1;
/* 348 */     paramArrayOflong[1] = paramLong2;
/* 349 */     paramArrayOflong[2] = paramLong3;
/* 350 */     paramArrayOflong[3] = paramLong4;
/* 351 */     paramArrayOflong[4] = paramLong5;
/* 352 */     paramArrayOflong[5] = paramLong6;
/* 353 */     paramArrayOflong[6] = paramLong7;
/* 354 */     paramArrayOflong[7] = paramLong8;
/* 355 */     paramArrayOflong[8] = paramLong9;
/* 356 */     paramArrayOflong[9] = paramLong10;
/* 357 */     paramArrayOflong[10] = paramLong11;
/* 358 */     paramArrayOflong[11] = paramLong12;
/* 359 */     paramArrayOflong[12] = paramLong13;
/* 360 */     paramArrayOflong[13] = paramLong14;
/*     */   }
/*     */   
/*     */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 364 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/* 365 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/* 366 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/* 367 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/* 368 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/* 369 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/* 370 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/* 371 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 372 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 373 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 374 */     long l11 = paramArrayOflong1[0] * paramArrayOflong2[10] + paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1] + paramArrayOflong1[10] * paramArrayOflong2[0];
/* 375 */     long l12 = paramArrayOflong1[0] * paramArrayOflong2[11] + paramArrayOflong1[1] * paramArrayOflong2[10] + paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2] + paramArrayOflong1[10] * paramArrayOflong2[1] + paramArrayOflong1[11] * paramArrayOflong2[0];
/* 376 */     long l13 = paramArrayOflong1[0] * paramArrayOflong2[12] + paramArrayOflong1[1] * paramArrayOflong2[11] + paramArrayOflong1[2] * paramArrayOflong2[10] + paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3] + paramArrayOflong1[10] * paramArrayOflong2[2] + paramArrayOflong1[11] * paramArrayOflong2[1] + paramArrayOflong1[12] * paramArrayOflong2[0];
/* 377 */     long l14 = paramArrayOflong1[0] * paramArrayOflong2[13] + paramArrayOflong1[1] * paramArrayOflong2[12] + paramArrayOflong1[2] * paramArrayOflong2[11] + paramArrayOflong1[3] * paramArrayOflong2[10] + paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4] + paramArrayOflong1[10] * paramArrayOflong2[3] + paramArrayOflong1[11] * paramArrayOflong2[2] + paramArrayOflong1[12] * paramArrayOflong2[1] + paramArrayOflong1[13] * paramArrayOflong2[0];
/* 378 */     long l15 = paramArrayOflong1[1] * paramArrayOflong2[13] + paramArrayOflong1[2] * paramArrayOflong2[12] + paramArrayOflong1[3] * paramArrayOflong2[11] + paramArrayOflong1[4] * paramArrayOflong2[10] + paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5] + paramArrayOflong1[10] * paramArrayOflong2[4] + paramArrayOflong1[11] * paramArrayOflong2[3] + paramArrayOflong1[12] * paramArrayOflong2[2] + paramArrayOflong1[13] * paramArrayOflong2[1];
/* 379 */     long l16 = paramArrayOflong1[2] * paramArrayOflong2[13] + paramArrayOflong1[3] * paramArrayOflong2[12] + paramArrayOflong1[4] * paramArrayOflong2[11] + paramArrayOflong1[5] * paramArrayOflong2[10] + paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6] + paramArrayOflong1[10] * paramArrayOflong2[5] + paramArrayOflong1[11] * paramArrayOflong2[4] + paramArrayOflong1[12] * paramArrayOflong2[3] + paramArrayOflong1[13] * paramArrayOflong2[2];
/* 380 */     long l17 = paramArrayOflong1[3] * paramArrayOflong2[13] + paramArrayOflong1[4] * paramArrayOflong2[12] + paramArrayOflong1[5] * paramArrayOflong2[11] + paramArrayOflong1[6] * paramArrayOflong2[10] + paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7] + paramArrayOflong1[10] * paramArrayOflong2[6] + paramArrayOflong1[11] * paramArrayOflong2[5] + paramArrayOflong1[12] * paramArrayOflong2[4] + paramArrayOflong1[13] * paramArrayOflong2[3];
/* 381 */     long l18 = paramArrayOflong1[4] * paramArrayOflong2[13] + paramArrayOflong1[5] * paramArrayOflong2[12] + paramArrayOflong1[6] * paramArrayOflong2[11] + paramArrayOflong1[7] * paramArrayOflong2[10] + paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8] + paramArrayOflong1[10] * paramArrayOflong2[7] + paramArrayOflong1[11] * paramArrayOflong2[6] + paramArrayOflong1[12] * paramArrayOflong2[5] + paramArrayOflong1[13] * paramArrayOflong2[4];
/* 382 */     long l19 = paramArrayOflong1[5] * paramArrayOflong2[13] + paramArrayOflong1[6] * paramArrayOflong2[12] + paramArrayOflong1[7] * paramArrayOflong2[11] + paramArrayOflong1[8] * paramArrayOflong2[10] + paramArrayOflong1[9] * paramArrayOflong2[9] + paramArrayOflong1[10] * paramArrayOflong2[8] + paramArrayOflong1[11] * paramArrayOflong2[7] + paramArrayOflong1[12] * paramArrayOflong2[6] + paramArrayOflong1[13] * paramArrayOflong2[5];
/* 383 */     long l20 = paramArrayOflong1[6] * paramArrayOflong2[13] + paramArrayOflong1[7] * paramArrayOflong2[12] + paramArrayOflong1[8] * paramArrayOflong2[11] + paramArrayOflong1[9] * paramArrayOflong2[10] + paramArrayOflong1[10] * paramArrayOflong2[9] + paramArrayOflong1[11] * paramArrayOflong2[8] + paramArrayOflong1[12] * paramArrayOflong2[7] + paramArrayOflong1[13] * paramArrayOflong2[6];
/* 384 */     long l21 = paramArrayOflong1[7] * paramArrayOflong2[13] + paramArrayOflong1[8] * paramArrayOflong2[12] + paramArrayOflong1[9] * paramArrayOflong2[11] + paramArrayOflong1[10] * paramArrayOflong2[10] + paramArrayOflong1[11] * paramArrayOflong2[9] + paramArrayOflong1[12] * paramArrayOflong2[8] + paramArrayOflong1[13] * paramArrayOflong2[7];
/* 385 */     long l22 = paramArrayOflong1[8] * paramArrayOflong2[13] + paramArrayOflong1[9] * paramArrayOflong2[12] + paramArrayOflong1[10] * paramArrayOflong2[11] + paramArrayOflong1[11] * paramArrayOflong2[10] + paramArrayOflong1[12] * paramArrayOflong2[9] + paramArrayOflong1[13] * paramArrayOflong2[8];
/* 386 */     long l23 = paramArrayOflong1[9] * paramArrayOflong2[13] + paramArrayOflong1[10] * paramArrayOflong2[12] + paramArrayOflong1[11] * paramArrayOflong2[11] + paramArrayOflong1[12] * paramArrayOflong2[10] + paramArrayOflong1[13] * paramArrayOflong2[9];
/* 387 */     long l24 = paramArrayOflong1[10] * paramArrayOflong2[13] + paramArrayOflong1[11] * paramArrayOflong2[12] + paramArrayOflong1[12] * paramArrayOflong2[11] + paramArrayOflong1[13] * paramArrayOflong2[10];
/* 388 */     long l25 = paramArrayOflong1[11] * paramArrayOflong2[13] + paramArrayOflong1[12] * paramArrayOflong2[12] + paramArrayOflong1[13] * paramArrayOflong2[11];
/* 389 */     long l26 = paramArrayOflong1[12] * paramArrayOflong2[13] + paramArrayOflong1[13] * paramArrayOflong2[12];
/* 390 */     long l27 = paramArrayOflong1[13] * paramArrayOflong2[13];
/*     */     
/* 392 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27);
/*     */   }
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/* 396 */     carryReduce(paramArrayOflong, paramArrayOflong[0], paramArrayOflong[1], paramArrayOflong[2], paramArrayOflong[3], paramArrayOflong[4], paramArrayOflong[5], paramArrayOflong[6], paramArrayOflong[7], paramArrayOflong[8], paramArrayOflong[9], paramArrayOflong[10], paramArrayOflong[11], paramArrayOflong[12], paramArrayOflong[13]);
/*     */   }
/*     */   
/*     */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 400 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 401 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 402 */     long l3 = 2L * paramArrayOflong1[0] * paramArrayOflong1[2] + paramArrayOflong1[1] * paramArrayOflong1[1];
/* 403 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 404 */     long l5 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]) + paramArrayOflong1[2] * paramArrayOflong1[2];
/* 405 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 406 */     long l7 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]) + paramArrayOflong1[3] * paramArrayOflong1[3];
/* 407 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 408 */     long l9 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]) + paramArrayOflong1[4] * paramArrayOflong1[4];
/* 409 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 410 */     long l11 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[10] + paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]) + paramArrayOflong1[5] * paramArrayOflong1[5];
/* 411 */     long l12 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[11] + paramArrayOflong1[1] * paramArrayOflong1[10] + paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 412 */     long l13 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[12] + paramArrayOflong1[1] * paramArrayOflong1[11] + paramArrayOflong1[2] * paramArrayOflong1[10] + paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]) + paramArrayOflong1[6] * paramArrayOflong1[6];
/* 413 */     long l14 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[13] + paramArrayOflong1[1] * paramArrayOflong1[12] + paramArrayOflong1[2] * paramArrayOflong1[11] + paramArrayOflong1[3] * paramArrayOflong1[10] + paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 414 */     long l15 = 2L * (paramArrayOflong1[1] * paramArrayOflong1[13] + paramArrayOflong1[2] * paramArrayOflong1[12] + paramArrayOflong1[3] * paramArrayOflong1[11] + paramArrayOflong1[4] * paramArrayOflong1[10] + paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]) + paramArrayOflong1[7] * paramArrayOflong1[7];
/* 415 */     long l16 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[13] + paramArrayOflong1[3] * paramArrayOflong1[12] + paramArrayOflong1[4] * paramArrayOflong1[11] + paramArrayOflong1[5] * paramArrayOflong1[10] + paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 416 */     long l17 = 2L * (paramArrayOflong1[3] * paramArrayOflong1[13] + paramArrayOflong1[4] * paramArrayOflong1[12] + paramArrayOflong1[5] * paramArrayOflong1[11] + paramArrayOflong1[6] * paramArrayOflong1[10] + paramArrayOflong1[7] * paramArrayOflong1[9]) + paramArrayOflong1[8] * paramArrayOflong1[8];
/* 417 */     long l18 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[13] + paramArrayOflong1[5] * paramArrayOflong1[12] + paramArrayOflong1[6] * paramArrayOflong1[11] + paramArrayOflong1[7] * paramArrayOflong1[10] + paramArrayOflong1[8] * paramArrayOflong1[9]);
/* 418 */     long l19 = 2L * (paramArrayOflong1[5] * paramArrayOflong1[13] + paramArrayOflong1[6] * paramArrayOflong1[12] + paramArrayOflong1[7] * paramArrayOflong1[11] + paramArrayOflong1[8] * paramArrayOflong1[10]) + paramArrayOflong1[9] * paramArrayOflong1[9];
/* 419 */     long l20 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[13] + paramArrayOflong1[7] * paramArrayOflong1[12] + paramArrayOflong1[8] * paramArrayOflong1[11] + paramArrayOflong1[9] * paramArrayOflong1[10]);
/* 420 */     long l21 = 2L * (paramArrayOflong1[7] * paramArrayOflong1[13] + paramArrayOflong1[8] * paramArrayOflong1[12] + paramArrayOflong1[9] * paramArrayOflong1[11]) + paramArrayOflong1[10] * paramArrayOflong1[10];
/* 421 */     long l22 = 2L * (paramArrayOflong1[8] * paramArrayOflong1[13] + paramArrayOflong1[9] * paramArrayOflong1[12] + paramArrayOflong1[10] * paramArrayOflong1[11]);
/* 422 */     long l23 = 2L * (paramArrayOflong1[9] * paramArrayOflong1[13] + paramArrayOflong1[10] * paramArrayOflong1[12]) + paramArrayOflong1[11] * paramArrayOflong1[11];
/* 423 */     long l24 = 2L * (paramArrayOflong1[10] * paramArrayOflong1[13] + paramArrayOflong1[11] * paramArrayOflong1[12]);
/* 424 */     long l25 = 2L * paramArrayOflong1[11] * paramArrayOflong1[13] + paramArrayOflong1[12] * paramArrayOflong1[12];
/* 425 */     long l26 = 2L * paramArrayOflong1[12] * paramArrayOflong1[13];
/* 426 */     long l27 = paramArrayOflong1[13] * paramArrayOflong1[13];
/*     */     
/* 428 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/IntegerPolynomialP384.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */