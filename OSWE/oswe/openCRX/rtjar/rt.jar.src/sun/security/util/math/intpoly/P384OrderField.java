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
/*     */ public class P384OrderField
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   private static final int BITS_PER_LIMB = 28;
/*     */   private static final int NUM_LIMBS = 14;
/*     */   private static final int MAX_ADDS = 1;
/*  37 */   public static final BigInteger MODULUS = evaluateModulus();
/*     */   private static final long CARRY_ADD = 134217728L;
/*     */   private static final int LIMB_MASK = 268435455;
/*     */   
/*     */   public P384OrderField() {
/*  42 */     super(28, 14, 1, MODULUS);
/*     */   }
/*     */   
/*     */   private static BigInteger evaluateModulus() {
/*  46 */     BigInteger bigInteger = BigInteger.valueOf(2L).pow(384);
/*  47 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(54187661L));
/*  48 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(28).multiply(BigInteger.valueOf(20867411L)));
/*  49 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(56).multiply(BigInteger.valueOf(10975981L)));
/*  50 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(84).multiply(BigInteger.valueOf(14361739L)));
/*  51 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(112).multiply(BigInteger.valueOf(35694566L)));
/*  52 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(140).multiply(BigInteger.valueOf(132168845L)));
/*  53 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(168).multiply(BigInteger.valueOf(3710130L)));
/*  54 */     return bigInteger;
/*     */   }
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*  58 */     long l1 = paramArrayOflong[13] >> 20L;
/*  59 */     paramArrayOflong[13] = paramArrayOflong[13] - (l1 << 20L);
/*  60 */     long l2 = 54187661L * l1;
/*  61 */     paramArrayOflong[0] = paramArrayOflong[0] + l2;
/*  62 */     l2 = 20867411L * l1;
/*  63 */     paramArrayOflong[1] = paramArrayOflong[1] + l2;
/*  64 */     l2 = -10975981L * l1;
/*  65 */     paramArrayOflong[2] = paramArrayOflong[2] + l2;
/*  66 */     l2 = -14361739L * l1;
/*  67 */     paramArrayOflong[3] = paramArrayOflong[3] + l2;
/*  68 */     l2 = 35694566L * l1;
/*  69 */     paramArrayOflong[4] = paramArrayOflong[4] + l2;
/*  70 */     l2 = 132168845L * l1;
/*  71 */     paramArrayOflong[5] = paramArrayOflong[5] + l2;
/*  72 */     l2 = 3710130L * l1;
/*  73 */     paramArrayOflong[6] = paramArrayOflong[6] + l2;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27) {
/*  76 */     long l1 = 0L;
/*     */     
/*  78 */     long l2 = paramLong1 + 134217728L >> 28L;
/*  79 */     paramLong1 -= l2 << 28L;
/*  80 */     paramLong2 += l2;
/*     */     
/*  82 */     l2 = paramLong2 + 134217728L >> 28L;
/*  83 */     paramLong2 -= l2 << 28L;
/*  84 */     paramLong3 += l2;
/*     */     
/*  86 */     l2 = paramLong3 + 134217728L >> 28L;
/*  87 */     paramLong3 -= l2 << 28L;
/*  88 */     paramLong4 += l2;
/*     */     
/*  90 */     l2 = paramLong4 + 134217728L >> 28L;
/*  91 */     paramLong4 -= l2 << 28L;
/*  92 */     paramLong5 += l2;
/*     */     
/*  94 */     l2 = paramLong5 + 134217728L >> 28L;
/*  95 */     paramLong5 -= l2 << 28L;
/*  96 */     paramLong6 += l2;
/*     */     
/*  98 */     l2 = paramLong6 + 134217728L >> 28L;
/*  99 */     paramLong6 -= l2 << 28L;
/* 100 */     paramLong7 += l2;
/*     */     
/* 102 */     l2 = paramLong7 + 134217728L >> 28L;
/* 103 */     paramLong7 -= l2 << 28L;
/* 104 */     paramLong8 += l2;
/*     */     
/* 106 */     l2 = paramLong8 + 134217728L >> 28L;
/* 107 */     paramLong8 -= l2 << 28L;
/* 108 */     paramLong9 += l2;
/*     */     
/* 110 */     l2 = paramLong9 + 134217728L >> 28L;
/* 111 */     paramLong9 -= l2 << 28L;
/* 112 */     paramLong10 += l2;
/*     */     
/* 114 */     l2 = paramLong10 + 134217728L >> 28L;
/* 115 */     paramLong10 -= l2 << 28L;
/* 116 */     paramLong11 += l2;
/*     */     
/* 118 */     l2 = paramLong11 + 134217728L >> 28L;
/* 119 */     paramLong11 -= l2 << 28L;
/* 120 */     paramLong12 += l2;
/*     */     
/* 122 */     l2 = paramLong12 + 134217728L >> 28L;
/* 123 */     paramLong12 -= l2 << 28L;
/* 124 */     paramLong13 += l2;
/*     */     
/* 126 */     l2 = paramLong13 + 134217728L >> 28L;
/* 127 */     paramLong13 -= l2 << 28L;
/* 128 */     paramLong14 += l2;
/*     */     
/* 130 */     l2 = paramLong14 + 134217728L >> 28L;
/* 131 */     paramLong14 -= l2 << 28L;
/* 132 */     paramLong15 += l2;
/*     */     
/* 134 */     l2 = paramLong15 + 134217728L >> 28L;
/* 135 */     paramLong15 -= l2 << 28L;
/* 136 */     paramLong16 += l2;
/*     */     
/* 138 */     l2 = paramLong16 + 134217728L >> 28L;
/* 139 */     paramLong16 -= l2 << 28L;
/* 140 */     paramLong17 += l2;
/*     */     
/* 142 */     l2 = paramLong17 + 134217728L >> 28L;
/* 143 */     paramLong17 -= l2 << 28L;
/* 144 */     paramLong18 += l2;
/*     */     
/* 146 */     l2 = paramLong18 + 134217728L >> 28L;
/* 147 */     paramLong18 -= l2 << 28L;
/* 148 */     paramLong19 += l2;
/*     */     
/* 150 */     l2 = paramLong19 + 134217728L >> 28L;
/* 151 */     paramLong19 -= l2 << 28L;
/* 152 */     paramLong20 += l2;
/*     */     
/* 154 */     l2 = paramLong20 + 134217728L >> 28L;
/* 155 */     paramLong20 -= l2 << 28L;
/* 156 */     paramLong21 += l2;
/*     */     
/* 158 */     l2 = paramLong21 + 134217728L >> 28L;
/* 159 */     paramLong21 -= l2 << 28L;
/* 160 */     paramLong22 += l2;
/*     */     
/* 162 */     l2 = paramLong22 + 134217728L >> 28L;
/* 163 */     paramLong22 -= l2 << 28L;
/* 164 */     paramLong23 += l2;
/*     */     
/* 166 */     l2 = paramLong23 + 134217728L >> 28L;
/* 167 */     paramLong23 -= l2 << 28L;
/* 168 */     paramLong24 += l2;
/*     */     
/* 170 */     l2 = paramLong24 + 134217728L >> 28L;
/* 171 */     paramLong24 -= l2 << 28L;
/* 172 */     paramLong25 += l2;
/*     */     
/* 174 */     l2 = paramLong25 + 134217728L >> 28L;
/* 175 */     paramLong25 -= l2 << 28L;
/* 176 */     paramLong26 += l2;
/*     */     
/* 178 */     l2 = paramLong26 + 134217728L >> 28L;
/* 179 */     paramLong26 -= l2 << 28L;
/* 180 */     paramLong27 += l2;
/*     */     
/* 182 */     l2 = paramLong27 + 134217728L >> 28L;
/* 183 */     paramLong27 -= l2 << 28L;
/* 184 */     l1 += l2;
/*     */     
/* 186 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, l1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28) {
/* 192 */     long l = 54187661L * paramLong28;
/* 193 */     paramLong14 += l << 8L & 0xFFFFFFFL;
/* 194 */     paramLong15 += l >> 20L;
/* 195 */     l = 20867411L * paramLong28;
/* 196 */     paramLong15 += l << 8L & 0xFFFFFFFL;
/* 197 */     paramLong16 += l >> 20L;
/* 198 */     l = -10975981L * paramLong28;
/* 199 */     paramLong16 += l << 8L & 0xFFFFFFFL;
/* 200 */     paramLong17 += l >> 20L;
/* 201 */     l = -14361739L * paramLong28;
/* 202 */     paramLong17 += l << 8L & 0xFFFFFFFL;
/* 203 */     paramLong18 += l >> 20L;
/* 204 */     l = 35694566L * paramLong28;
/* 205 */     paramLong18 += l << 8L & 0xFFFFFFFL;
/* 206 */     paramLong19 += l >> 20L;
/* 207 */     l = 132168845L * paramLong28;
/* 208 */     paramLong19 += l << 8L & 0xFFFFFFFL;
/* 209 */     paramLong20 += l >> 20L;
/* 210 */     l = 3710130L * paramLong28;
/* 211 */     paramLong20 += l << 8L & 0xFFFFFFFL;
/* 212 */     paramLong21 += l >> 20L;
/*     */     
/* 214 */     l = 54187661L * paramLong27;
/* 215 */     paramLong13 += l << 8L & 0xFFFFFFFL;
/* 216 */     paramLong14 += l >> 20L;
/* 217 */     l = 20867411L * paramLong27;
/* 218 */     paramLong14 += l << 8L & 0xFFFFFFFL;
/* 219 */     paramLong15 += l >> 20L;
/* 220 */     l = -10975981L * paramLong27;
/* 221 */     paramLong15 += l << 8L & 0xFFFFFFFL;
/* 222 */     paramLong16 += l >> 20L;
/* 223 */     l = -14361739L * paramLong27;
/* 224 */     paramLong16 += l << 8L & 0xFFFFFFFL;
/* 225 */     paramLong17 += l >> 20L;
/* 226 */     l = 35694566L * paramLong27;
/* 227 */     paramLong17 += l << 8L & 0xFFFFFFFL;
/* 228 */     paramLong18 += l >> 20L;
/* 229 */     l = 132168845L * paramLong27;
/* 230 */     paramLong18 += l << 8L & 0xFFFFFFFL;
/* 231 */     paramLong19 += l >> 20L;
/* 232 */     l = 3710130L * paramLong27;
/* 233 */     paramLong19 += l << 8L & 0xFFFFFFFL;
/* 234 */     paramLong20 += l >> 20L;
/*     */     
/* 236 */     l = 54187661L * paramLong26;
/* 237 */     paramLong12 += l << 8L & 0xFFFFFFFL;
/* 238 */     paramLong13 += l >> 20L;
/* 239 */     l = 20867411L * paramLong26;
/* 240 */     paramLong13 += l << 8L & 0xFFFFFFFL;
/* 241 */     paramLong14 += l >> 20L;
/* 242 */     l = -10975981L * paramLong26;
/* 243 */     paramLong14 += l << 8L & 0xFFFFFFFL;
/* 244 */     paramLong15 += l >> 20L;
/* 245 */     l = -14361739L * paramLong26;
/* 246 */     paramLong15 += l << 8L & 0xFFFFFFFL;
/* 247 */     paramLong16 += l >> 20L;
/* 248 */     l = 35694566L * paramLong26;
/* 249 */     paramLong16 += l << 8L & 0xFFFFFFFL;
/* 250 */     paramLong17 += l >> 20L;
/* 251 */     l = 132168845L * paramLong26;
/* 252 */     paramLong17 += l << 8L & 0xFFFFFFFL;
/* 253 */     paramLong18 += l >> 20L;
/* 254 */     l = 3710130L * paramLong26;
/* 255 */     paramLong18 += l << 8L & 0xFFFFFFFL;
/* 256 */     paramLong19 += l >> 20L;
/*     */     
/* 258 */     l = 54187661L * paramLong25;
/* 259 */     paramLong11 += l << 8L & 0xFFFFFFFL;
/* 260 */     paramLong12 += l >> 20L;
/* 261 */     l = 20867411L * paramLong25;
/* 262 */     paramLong12 += l << 8L & 0xFFFFFFFL;
/* 263 */     paramLong13 += l >> 20L;
/* 264 */     l = -10975981L * paramLong25;
/* 265 */     paramLong13 += l << 8L & 0xFFFFFFFL;
/* 266 */     paramLong14 += l >> 20L;
/* 267 */     l = -14361739L * paramLong25;
/* 268 */     paramLong14 += l << 8L & 0xFFFFFFFL;
/* 269 */     paramLong15 += l >> 20L;
/* 270 */     l = 35694566L * paramLong25;
/* 271 */     paramLong15 += l << 8L & 0xFFFFFFFL;
/* 272 */     paramLong16 += l >> 20L;
/* 273 */     l = 132168845L * paramLong25;
/* 274 */     paramLong16 += l << 8L & 0xFFFFFFFL;
/* 275 */     paramLong17 += l >> 20L;
/* 276 */     l = 3710130L * paramLong25;
/* 277 */     paramLong17 += l << 8L & 0xFFFFFFFL;
/* 278 */     paramLong18 += l >> 20L;
/*     */     
/* 280 */     l = 54187661L * paramLong24;
/* 281 */     paramLong10 += l << 8L & 0xFFFFFFFL;
/* 282 */     paramLong11 += l >> 20L;
/* 283 */     l = 20867411L * paramLong24;
/* 284 */     paramLong11 += l << 8L & 0xFFFFFFFL;
/* 285 */     paramLong12 += l >> 20L;
/* 286 */     l = -10975981L * paramLong24;
/* 287 */     paramLong12 += l << 8L & 0xFFFFFFFL;
/* 288 */     paramLong13 += l >> 20L;
/* 289 */     l = -14361739L * paramLong24;
/* 290 */     paramLong13 += l << 8L & 0xFFFFFFFL;
/* 291 */     paramLong14 += l >> 20L;
/* 292 */     l = 35694566L * paramLong24;
/* 293 */     paramLong14 += l << 8L & 0xFFFFFFFL;
/* 294 */     paramLong15 += l >> 20L;
/* 295 */     l = 132168845L * paramLong24;
/* 296 */     paramLong15 += l << 8L & 0xFFFFFFFL;
/* 297 */     paramLong16 += l >> 20L;
/* 298 */     l = 3710130L * paramLong24;
/* 299 */     paramLong16 += l << 8L & 0xFFFFFFFL;
/* 300 */     paramLong17 += l >> 20L;
/*     */     
/* 302 */     l = 54187661L * paramLong23;
/* 303 */     paramLong9 += l << 8L & 0xFFFFFFFL;
/* 304 */     paramLong10 += l >> 20L;
/* 305 */     l = 20867411L * paramLong23;
/* 306 */     paramLong10 += l << 8L & 0xFFFFFFFL;
/* 307 */     paramLong11 += l >> 20L;
/* 308 */     l = -10975981L * paramLong23;
/* 309 */     paramLong11 += l << 8L & 0xFFFFFFFL;
/* 310 */     paramLong12 += l >> 20L;
/* 311 */     l = -14361739L * paramLong23;
/* 312 */     paramLong12 += l << 8L & 0xFFFFFFFL;
/* 313 */     paramLong13 += l >> 20L;
/* 314 */     l = 35694566L * paramLong23;
/* 315 */     paramLong13 += l << 8L & 0xFFFFFFFL;
/* 316 */     paramLong14 += l >> 20L;
/* 317 */     l = 132168845L * paramLong23;
/* 318 */     paramLong14 += l << 8L & 0xFFFFFFFL;
/* 319 */     paramLong15 += l >> 20L;
/* 320 */     l = 3710130L * paramLong23;
/* 321 */     paramLong15 += l << 8L & 0xFFFFFFFL;
/* 322 */     paramLong16 += l >> 20L;
/*     */     
/* 324 */     l = 54187661L * paramLong22;
/* 325 */     paramLong8 += l << 8L & 0xFFFFFFFL;
/* 326 */     paramLong9 += l >> 20L;
/* 327 */     l = 20867411L * paramLong22;
/* 328 */     paramLong9 += l << 8L & 0xFFFFFFFL;
/* 329 */     paramLong10 += l >> 20L;
/* 330 */     l = -10975981L * paramLong22;
/* 331 */     paramLong10 += l << 8L & 0xFFFFFFFL;
/* 332 */     paramLong11 += l >> 20L;
/* 333 */     l = -14361739L * paramLong22;
/* 334 */     paramLong11 += l << 8L & 0xFFFFFFFL;
/* 335 */     paramLong12 += l >> 20L;
/* 336 */     l = 35694566L * paramLong22;
/* 337 */     paramLong12 += l << 8L & 0xFFFFFFFL;
/* 338 */     paramLong13 += l >> 20L;
/* 339 */     l = 132168845L * paramLong22;
/* 340 */     paramLong13 += l << 8L & 0xFFFFFFFL;
/* 341 */     paramLong14 += l >> 20L;
/* 342 */     l = 3710130L * paramLong22;
/* 343 */     paramLong14 += l << 8L & 0xFFFFFFFL;
/* 344 */     paramLong15 += l >> 20L;
/*     */     
/* 346 */     l = 54187661L * paramLong21;
/* 347 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 348 */     paramLong8 += l >> 20L;
/* 349 */     l = 20867411L * paramLong21;
/* 350 */     paramLong8 += l << 8L & 0xFFFFFFFL;
/* 351 */     paramLong9 += l >> 20L;
/* 352 */     l = -10975981L * paramLong21;
/* 353 */     paramLong9 += l << 8L & 0xFFFFFFFL;
/* 354 */     paramLong10 += l >> 20L;
/* 355 */     l = -14361739L * paramLong21;
/* 356 */     paramLong10 += l << 8L & 0xFFFFFFFL;
/* 357 */     paramLong11 += l >> 20L;
/* 358 */     l = 35694566L * paramLong21;
/* 359 */     paramLong11 += l << 8L & 0xFFFFFFFL;
/* 360 */     paramLong12 += l >> 20L;
/* 361 */     l = 132168845L * paramLong21;
/* 362 */     paramLong12 += l << 8L & 0xFFFFFFFL;
/* 363 */     paramLong13 += l >> 20L;
/* 364 */     l = 3710130L * paramLong21;
/* 365 */     paramLong13 += l << 8L & 0xFFFFFFFL;
/* 366 */     paramLong14 += l >> 20L;
/*     */     
/* 368 */     l = 54187661L * paramLong20;
/* 369 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 370 */     paramLong7 += l >> 20L;
/* 371 */     l = 20867411L * paramLong20;
/* 372 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 373 */     paramLong8 += l >> 20L;
/* 374 */     l = -10975981L * paramLong20;
/* 375 */     paramLong8 += l << 8L & 0xFFFFFFFL;
/* 376 */     paramLong9 += l >> 20L;
/* 377 */     l = -14361739L * paramLong20;
/* 378 */     paramLong9 += l << 8L & 0xFFFFFFFL;
/* 379 */     paramLong10 += l >> 20L;
/* 380 */     l = 35694566L * paramLong20;
/* 381 */     paramLong10 += l << 8L & 0xFFFFFFFL;
/* 382 */     paramLong11 += l >> 20L;
/* 383 */     l = 132168845L * paramLong20;
/* 384 */     paramLong11 += l << 8L & 0xFFFFFFFL;
/* 385 */     paramLong12 += l >> 20L;
/* 386 */     l = 3710130L * paramLong20;
/* 387 */     paramLong12 += l << 8L & 0xFFFFFFFL;
/* 388 */     paramLong13 += l >> 20L;
/*     */     
/* 390 */     l = 54187661L * paramLong19;
/* 391 */     paramLong5 += l << 8L & 0xFFFFFFFL;
/* 392 */     paramLong6 += l >> 20L;
/* 393 */     l = 20867411L * paramLong19;
/* 394 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 395 */     paramLong7 += l >> 20L;
/* 396 */     l = -10975981L * paramLong19;
/* 397 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 398 */     paramLong8 += l >> 20L;
/* 399 */     l = -14361739L * paramLong19;
/* 400 */     paramLong8 += l << 8L & 0xFFFFFFFL;
/* 401 */     paramLong9 += l >> 20L;
/* 402 */     l = 35694566L * paramLong19;
/* 403 */     paramLong9 += l << 8L & 0xFFFFFFFL;
/* 404 */     paramLong10 += l >> 20L;
/* 405 */     l = 132168845L * paramLong19;
/* 406 */     paramLong10 += l << 8L & 0xFFFFFFFL;
/* 407 */     paramLong11 += l >> 20L;
/* 408 */     l = 3710130L * paramLong19;
/* 409 */     paramLong11 += l << 8L & 0xFFFFFFFL;
/* 410 */     paramLong12 += l >> 20L;
/*     */     
/* 412 */     l = 54187661L * paramLong18;
/* 413 */     paramLong4 += l << 8L & 0xFFFFFFFL;
/* 414 */     paramLong5 += l >> 20L;
/* 415 */     l = 20867411L * paramLong18;
/* 416 */     paramLong5 += l << 8L & 0xFFFFFFFL;
/* 417 */     paramLong6 += l >> 20L;
/* 418 */     l = -10975981L * paramLong18;
/* 419 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 420 */     paramLong7 += l >> 20L;
/* 421 */     l = -14361739L * paramLong18;
/* 422 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 423 */     paramLong8 += l >> 20L;
/* 424 */     l = 35694566L * paramLong18;
/* 425 */     paramLong8 += l << 8L & 0xFFFFFFFL;
/* 426 */     paramLong9 += l >> 20L;
/* 427 */     l = 132168845L * paramLong18;
/* 428 */     paramLong9 += l << 8L & 0xFFFFFFFL;
/* 429 */     paramLong10 += l >> 20L;
/* 430 */     l = 3710130L * paramLong18;
/* 431 */     paramLong10 += l << 8L & 0xFFFFFFFL;
/* 432 */     paramLong11 += l >> 20L;
/*     */     
/* 434 */     l = 54187661L * paramLong17;
/* 435 */     paramLong3 += l << 8L & 0xFFFFFFFL;
/* 436 */     paramLong4 += l >> 20L;
/* 437 */     l = 20867411L * paramLong17;
/* 438 */     paramLong4 += l << 8L & 0xFFFFFFFL;
/* 439 */     paramLong5 += l >> 20L;
/* 440 */     l = -10975981L * paramLong17;
/* 441 */     paramLong5 += l << 8L & 0xFFFFFFFL;
/* 442 */     paramLong6 += l >> 20L;
/* 443 */     l = -14361739L * paramLong17;
/* 444 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 445 */     paramLong7 += l >> 20L;
/* 446 */     l = 35694566L * paramLong17;
/* 447 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 448 */     paramLong8 += l >> 20L;
/* 449 */     l = 132168845L * paramLong17;
/* 450 */     paramLong8 += l << 8L & 0xFFFFFFFL;
/* 451 */     paramLong9 += l >> 20L;
/* 452 */     l = 3710130L * paramLong17;
/* 453 */     paramLong9 += l << 8L & 0xFFFFFFFL;
/* 454 */     paramLong10 += l >> 20L;
/*     */     
/* 456 */     l = 54187661L * paramLong16;
/* 457 */     paramLong2 += l << 8L & 0xFFFFFFFL;
/* 458 */     paramLong3 += l >> 20L;
/* 459 */     l = 20867411L * paramLong16;
/* 460 */     paramLong3 += l << 8L & 0xFFFFFFFL;
/* 461 */     paramLong4 += l >> 20L;
/* 462 */     l = -10975981L * paramLong16;
/* 463 */     paramLong4 += l << 8L & 0xFFFFFFFL;
/* 464 */     paramLong5 += l >> 20L;
/* 465 */     l = -14361739L * paramLong16;
/* 466 */     paramLong5 += l << 8L & 0xFFFFFFFL;
/* 467 */     paramLong6 += l >> 20L;
/* 468 */     l = 35694566L * paramLong16;
/* 469 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 470 */     paramLong7 += l >> 20L;
/* 471 */     l = 132168845L * paramLong16;
/* 472 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 473 */     paramLong8 += l >> 20L;
/* 474 */     l = 3710130L * paramLong16;
/* 475 */     paramLong8 += l << 8L & 0xFFFFFFFL;
/* 476 */     paramLong9 += l >> 20L;
/*     */     
/* 478 */     l = 54187661L * paramLong15;
/* 479 */     paramLong1 += l << 8L & 0xFFFFFFFL;
/* 480 */     paramLong2 += l >> 20L;
/* 481 */     l = 20867411L * paramLong15;
/* 482 */     paramLong2 += l << 8L & 0xFFFFFFFL;
/* 483 */     paramLong3 += l >> 20L;
/* 484 */     l = -10975981L * paramLong15;
/* 485 */     paramLong3 += l << 8L & 0xFFFFFFFL;
/* 486 */     paramLong4 += l >> 20L;
/* 487 */     l = -14361739L * paramLong15;
/* 488 */     paramLong4 += l << 8L & 0xFFFFFFFL;
/* 489 */     paramLong5 += l >> 20L;
/* 490 */     l = 35694566L * paramLong15;
/* 491 */     paramLong5 += l << 8L & 0xFFFFFFFL;
/* 492 */     paramLong6 += l >> 20L;
/* 493 */     l = 132168845L * paramLong15;
/* 494 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 495 */     paramLong7 += l >> 20L;
/* 496 */     l = 3710130L * paramLong15;
/* 497 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 498 */     paramLong8 += l >> 20L;
/* 499 */     paramLong15 = 0L;
/*     */     
/* 501 */     carryReduce1(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, paramLong28);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce1(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28) {
/* 507 */     long l = paramLong1 + 134217728L >> 28L;
/* 508 */     paramLong1 -= l << 28L;
/* 509 */     paramLong2 += l;
/*     */     
/* 511 */     l = paramLong2 + 134217728L >> 28L;
/* 512 */     paramLong2 -= l << 28L;
/* 513 */     paramLong3 += l;
/*     */     
/* 515 */     l = paramLong3 + 134217728L >> 28L;
/* 516 */     paramLong3 -= l << 28L;
/* 517 */     paramLong4 += l;
/*     */     
/* 519 */     l = paramLong4 + 134217728L >> 28L;
/* 520 */     paramLong4 -= l << 28L;
/* 521 */     paramLong5 += l;
/*     */     
/* 523 */     l = paramLong5 + 134217728L >> 28L;
/* 524 */     paramLong5 -= l << 28L;
/* 525 */     paramLong6 += l;
/*     */     
/* 527 */     l = paramLong6 + 134217728L >> 28L;
/* 528 */     paramLong6 -= l << 28L;
/* 529 */     paramLong7 += l;
/*     */     
/* 531 */     l = paramLong7 + 134217728L >> 28L;
/* 532 */     paramLong7 -= l << 28L;
/* 533 */     paramLong8 += l;
/*     */     
/* 535 */     l = paramLong8 + 134217728L >> 28L;
/* 536 */     paramLong8 -= l << 28L;
/* 537 */     paramLong9 += l;
/*     */     
/* 539 */     l = paramLong9 + 134217728L >> 28L;
/* 540 */     paramLong9 -= l << 28L;
/* 541 */     paramLong10 += l;
/*     */     
/* 543 */     l = paramLong10 + 134217728L >> 28L;
/* 544 */     paramLong10 -= l << 28L;
/* 545 */     paramLong11 += l;
/*     */     
/* 547 */     l = paramLong11 + 134217728L >> 28L;
/* 548 */     paramLong11 -= l << 28L;
/* 549 */     paramLong12 += l;
/*     */     
/* 551 */     l = paramLong12 + 134217728L >> 28L;
/* 552 */     paramLong12 -= l << 28L;
/* 553 */     paramLong13 += l;
/*     */     
/* 555 */     l = paramLong13 + 134217728L >> 28L;
/* 556 */     paramLong13 -= l << 28L;
/* 557 */     paramLong14 += l;
/*     */     
/* 559 */     l = paramLong14 + 134217728L >> 28L;
/* 560 */     paramLong14 -= l << 28L;
/* 561 */     paramLong15 += l;
/*     */     
/* 563 */     carryReduce2(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, paramLong28);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce2(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28) {
/* 569 */     long l = 54187661L * paramLong15;
/* 570 */     paramLong1 += l << 8L & 0xFFFFFFFL;
/* 571 */     paramLong2 += l >> 20L;
/* 572 */     l = 20867411L * paramLong15;
/* 573 */     paramLong2 += l << 8L & 0xFFFFFFFL;
/* 574 */     paramLong3 += l >> 20L;
/* 575 */     l = -10975981L * paramLong15;
/* 576 */     paramLong3 += l << 8L & 0xFFFFFFFL;
/* 577 */     paramLong4 += l >> 20L;
/* 578 */     l = -14361739L * paramLong15;
/* 579 */     paramLong4 += l << 8L & 0xFFFFFFFL;
/* 580 */     paramLong5 += l >> 20L;
/* 581 */     l = 35694566L * paramLong15;
/* 582 */     paramLong5 += l << 8L & 0xFFFFFFFL;
/* 583 */     paramLong6 += l >> 20L;
/* 584 */     l = 132168845L * paramLong15;
/* 585 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 586 */     paramLong7 += l >> 20L;
/* 587 */     l = 3710130L * paramLong15;
/* 588 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 589 */     paramLong8 += l >> 20L;
/*     */     
/* 591 */     l = paramLong1 + 134217728L >> 28L;
/* 592 */     paramLong1 -= l << 28L;
/* 593 */     paramLong2 += l;
/*     */     
/* 595 */     l = paramLong2 + 134217728L >> 28L;
/* 596 */     paramLong2 -= l << 28L;
/* 597 */     paramLong3 += l;
/*     */     
/* 599 */     l = paramLong3 + 134217728L >> 28L;
/* 600 */     paramLong3 -= l << 28L;
/* 601 */     paramLong4 += l;
/*     */     
/* 603 */     l = paramLong4 + 134217728L >> 28L;
/* 604 */     paramLong4 -= l << 28L;
/* 605 */     paramLong5 += l;
/*     */     
/* 607 */     l = paramLong5 + 134217728L >> 28L;
/* 608 */     paramLong5 -= l << 28L;
/* 609 */     paramLong6 += l;
/*     */     
/* 611 */     l = paramLong6 + 134217728L >> 28L;
/* 612 */     paramLong6 -= l << 28L;
/* 613 */     paramLong7 += l;
/*     */     
/* 615 */     l = paramLong7 + 134217728L >> 28L;
/* 616 */     paramLong7 -= l << 28L;
/* 617 */     paramLong8 += l;
/*     */     
/* 619 */     l = paramLong8 + 134217728L >> 28L;
/* 620 */     paramLong8 -= l << 28L;
/* 621 */     paramLong9 += l;
/*     */     
/* 623 */     l = paramLong9 + 134217728L >> 28L;
/* 624 */     paramLong9 -= l << 28L;
/* 625 */     paramLong10 += l;
/*     */     
/* 627 */     l = paramLong10 + 134217728L >> 28L;
/* 628 */     paramLong10 -= l << 28L;
/* 629 */     paramLong11 += l;
/*     */     
/* 631 */     l = paramLong11 + 134217728L >> 28L;
/* 632 */     paramLong11 -= l << 28L;
/* 633 */     paramLong12 += l;
/*     */     
/* 635 */     l = paramLong12 + 134217728L >> 28L;
/* 636 */     paramLong12 -= l << 28L;
/* 637 */     paramLong13 += l;
/*     */     
/* 639 */     l = paramLong13 + 134217728L >> 28L;
/* 640 */     paramLong13 -= l << 28L;
/* 641 */     paramLong14 += l;
/*     */     
/* 643 */     paramArrayOflong[0] = paramLong1;
/* 644 */     paramArrayOflong[1] = paramLong2;
/* 645 */     paramArrayOflong[2] = paramLong3;
/* 646 */     paramArrayOflong[3] = paramLong4;
/* 647 */     paramArrayOflong[4] = paramLong5;
/* 648 */     paramArrayOflong[5] = paramLong6;
/* 649 */     paramArrayOflong[6] = paramLong7;
/* 650 */     paramArrayOflong[7] = paramLong8;
/* 651 */     paramArrayOflong[8] = paramLong9;
/* 652 */     paramArrayOflong[9] = paramLong10;
/* 653 */     paramArrayOflong[10] = paramLong11;
/* 654 */     paramArrayOflong[11] = paramLong12;
/* 655 */     paramArrayOflong[12] = paramLong13;
/* 656 */     paramArrayOflong[13] = paramLong14;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14) {
/* 659 */     long l1 = 0L;
/*     */     
/* 661 */     long l2 = paramLong1 + 134217728L >> 28L;
/* 662 */     paramLong1 -= l2 << 28L;
/* 663 */     paramLong2 += l2;
/*     */     
/* 665 */     l2 = paramLong2 + 134217728L >> 28L;
/* 666 */     paramLong2 -= l2 << 28L;
/* 667 */     paramLong3 += l2;
/*     */     
/* 669 */     l2 = paramLong3 + 134217728L >> 28L;
/* 670 */     paramLong3 -= l2 << 28L;
/* 671 */     paramLong4 += l2;
/*     */     
/* 673 */     l2 = paramLong4 + 134217728L >> 28L;
/* 674 */     paramLong4 -= l2 << 28L;
/* 675 */     paramLong5 += l2;
/*     */     
/* 677 */     l2 = paramLong5 + 134217728L >> 28L;
/* 678 */     paramLong5 -= l2 << 28L;
/* 679 */     paramLong6 += l2;
/*     */     
/* 681 */     l2 = paramLong6 + 134217728L >> 28L;
/* 682 */     paramLong6 -= l2 << 28L;
/* 683 */     paramLong7 += l2;
/*     */     
/* 685 */     l2 = paramLong7 + 134217728L >> 28L;
/* 686 */     paramLong7 -= l2 << 28L;
/* 687 */     paramLong8 += l2;
/*     */     
/* 689 */     l2 = paramLong8 + 134217728L >> 28L;
/* 690 */     paramLong8 -= l2 << 28L;
/* 691 */     paramLong9 += l2;
/*     */     
/* 693 */     l2 = paramLong9 + 134217728L >> 28L;
/* 694 */     paramLong9 -= l2 << 28L;
/* 695 */     paramLong10 += l2;
/*     */     
/* 697 */     l2 = paramLong10 + 134217728L >> 28L;
/* 698 */     paramLong10 -= l2 << 28L;
/* 699 */     paramLong11 += l2;
/*     */     
/* 701 */     l2 = paramLong11 + 134217728L >> 28L;
/* 702 */     paramLong11 -= l2 << 28L;
/* 703 */     paramLong12 += l2;
/*     */     
/* 705 */     l2 = paramLong12 + 134217728L >> 28L;
/* 706 */     paramLong12 -= l2 << 28L;
/* 707 */     paramLong13 += l2;
/*     */     
/* 709 */     l2 = paramLong13 + 134217728L >> 28L;
/* 710 */     paramLong13 -= l2 << 28L;
/* 711 */     paramLong14 += l2;
/*     */     
/* 713 */     l2 = paramLong14 + 134217728L >> 28L;
/* 714 */     paramLong14 -= l2 << 28L;
/* 715 */     l1 += l2;
/*     */     
/* 717 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, l1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15) {
/* 723 */     long l = 54187661L * paramLong15;
/* 724 */     paramLong1 += l << 8L & 0xFFFFFFFL;
/* 725 */     paramLong2 += l >> 20L;
/* 726 */     l = 20867411L * paramLong15;
/* 727 */     paramLong2 += l << 8L & 0xFFFFFFFL;
/* 728 */     paramLong3 += l >> 20L;
/* 729 */     l = -10975981L * paramLong15;
/* 730 */     paramLong3 += l << 8L & 0xFFFFFFFL;
/* 731 */     paramLong4 += l >> 20L;
/* 732 */     l = -14361739L * paramLong15;
/* 733 */     paramLong4 += l << 8L & 0xFFFFFFFL;
/* 734 */     paramLong5 += l >> 20L;
/* 735 */     l = 35694566L * paramLong15;
/* 736 */     paramLong5 += l << 8L & 0xFFFFFFFL;
/* 737 */     paramLong6 += l >> 20L;
/* 738 */     l = 132168845L * paramLong15;
/* 739 */     paramLong6 += l << 8L & 0xFFFFFFFL;
/* 740 */     paramLong7 += l >> 20L;
/* 741 */     l = 3710130L * paramLong15;
/* 742 */     paramLong7 += l << 8L & 0xFFFFFFFL;
/* 743 */     paramLong8 += l >> 20L;
/*     */     
/* 745 */     l = paramLong1 + 134217728L >> 28L;
/* 746 */     paramLong1 -= l << 28L;
/* 747 */     paramLong2 += l;
/*     */     
/* 749 */     l = paramLong2 + 134217728L >> 28L;
/* 750 */     paramLong2 -= l << 28L;
/* 751 */     paramLong3 += l;
/*     */     
/* 753 */     l = paramLong3 + 134217728L >> 28L;
/* 754 */     paramLong3 -= l << 28L;
/* 755 */     paramLong4 += l;
/*     */     
/* 757 */     l = paramLong4 + 134217728L >> 28L;
/* 758 */     paramLong4 -= l << 28L;
/* 759 */     paramLong5 += l;
/*     */     
/* 761 */     l = paramLong5 + 134217728L >> 28L;
/* 762 */     paramLong5 -= l << 28L;
/* 763 */     paramLong6 += l;
/*     */     
/* 765 */     l = paramLong6 + 134217728L >> 28L;
/* 766 */     paramLong6 -= l << 28L;
/* 767 */     paramLong7 += l;
/*     */     
/* 769 */     l = paramLong7 + 134217728L >> 28L;
/* 770 */     paramLong7 -= l << 28L;
/* 771 */     paramLong8 += l;
/*     */     
/* 773 */     l = paramLong8 + 134217728L >> 28L;
/* 774 */     paramLong8 -= l << 28L;
/* 775 */     paramLong9 += l;
/*     */     
/* 777 */     l = paramLong9 + 134217728L >> 28L;
/* 778 */     paramLong9 -= l << 28L;
/* 779 */     paramLong10 += l;
/*     */     
/* 781 */     l = paramLong10 + 134217728L >> 28L;
/* 782 */     paramLong10 -= l << 28L;
/* 783 */     paramLong11 += l;
/*     */     
/* 785 */     l = paramLong11 + 134217728L >> 28L;
/* 786 */     paramLong11 -= l << 28L;
/* 787 */     paramLong12 += l;
/*     */     
/* 789 */     l = paramLong12 + 134217728L >> 28L;
/* 790 */     paramLong12 -= l << 28L;
/* 791 */     paramLong13 += l;
/*     */     
/* 793 */     l = paramLong13 + 134217728L >> 28L;
/* 794 */     paramLong13 -= l << 28L;
/* 795 */     paramLong14 += l;
/*     */     
/* 797 */     paramArrayOflong[0] = paramLong1;
/* 798 */     paramArrayOflong[1] = paramLong2;
/* 799 */     paramArrayOflong[2] = paramLong3;
/* 800 */     paramArrayOflong[3] = paramLong4;
/* 801 */     paramArrayOflong[4] = paramLong5;
/* 802 */     paramArrayOflong[5] = paramLong6;
/* 803 */     paramArrayOflong[6] = paramLong7;
/* 804 */     paramArrayOflong[7] = paramLong8;
/* 805 */     paramArrayOflong[8] = paramLong9;
/* 806 */     paramArrayOflong[9] = paramLong10;
/* 807 */     paramArrayOflong[10] = paramLong11;
/* 808 */     paramArrayOflong[11] = paramLong12;
/* 809 */     paramArrayOflong[12] = paramLong13;
/* 810 */     paramArrayOflong[13] = paramLong14;
/*     */   }
/*     */   
/*     */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 814 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/* 815 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/* 816 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/* 817 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/* 818 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/* 819 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/* 820 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/* 821 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 822 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 823 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 824 */     long l11 = paramArrayOflong1[0] * paramArrayOflong2[10] + paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1] + paramArrayOflong1[10] * paramArrayOflong2[0];
/* 825 */     long l12 = paramArrayOflong1[0] * paramArrayOflong2[11] + paramArrayOflong1[1] * paramArrayOflong2[10] + paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2] + paramArrayOflong1[10] * paramArrayOflong2[1] + paramArrayOflong1[11] * paramArrayOflong2[0];
/* 826 */     long l13 = paramArrayOflong1[0] * paramArrayOflong2[12] + paramArrayOflong1[1] * paramArrayOflong2[11] + paramArrayOflong1[2] * paramArrayOflong2[10] + paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3] + paramArrayOflong1[10] * paramArrayOflong2[2] + paramArrayOflong1[11] * paramArrayOflong2[1] + paramArrayOflong1[12] * paramArrayOflong2[0];
/* 827 */     long l14 = paramArrayOflong1[0] * paramArrayOflong2[13] + paramArrayOflong1[1] * paramArrayOflong2[12] + paramArrayOflong1[2] * paramArrayOflong2[11] + paramArrayOflong1[3] * paramArrayOflong2[10] + paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4] + paramArrayOflong1[10] * paramArrayOflong2[3] + paramArrayOflong1[11] * paramArrayOflong2[2] + paramArrayOflong1[12] * paramArrayOflong2[1] + paramArrayOflong1[13] * paramArrayOflong2[0];
/* 828 */     long l15 = paramArrayOflong1[1] * paramArrayOflong2[13] + paramArrayOflong1[2] * paramArrayOflong2[12] + paramArrayOflong1[3] * paramArrayOflong2[11] + paramArrayOflong1[4] * paramArrayOflong2[10] + paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5] + paramArrayOflong1[10] * paramArrayOflong2[4] + paramArrayOflong1[11] * paramArrayOflong2[3] + paramArrayOflong1[12] * paramArrayOflong2[2] + paramArrayOflong1[13] * paramArrayOflong2[1];
/* 829 */     long l16 = paramArrayOflong1[2] * paramArrayOflong2[13] + paramArrayOflong1[3] * paramArrayOflong2[12] + paramArrayOflong1[4] * paramArrayOflong2[11] + paramArrayOflong1[5] * paramArrayOflong2[10] + paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6] + paramArrayOflong1[10] * paramArrayOflong2[5] + paramArrayOflong1[11] * paramArrayOflong2[4] + paramArrayOflong1[12] * paramArrayOflong2[3] + paramArrayOflong1[13] * paramArrayOflong2[2];
/* 830 */     long l17 = paramArrayOflong1[3] * paramArrayOflong2[13] + paramArrayOflong1[4] * paramArrayOflong2[12] + paramArrayOflong1[5] * paramArrayOflong2[11] + paramArrayOflong1[6] * paramArrayOflong2[10] + paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7] + paramArrayOflong1[10] * paramArrayOflong2[6] + paramArrayOflong1[11] * paramArrayOflong2[5] + paramArrayOflong1[12] * paramArrayOflong2[4] + paramArrayOflong1[13] * paramArrayOflong2[3];
/* 831 */     long l18 = paramArrayOflong1[4] * paramArrayOflong2[13] + paramArrayOflong1[5] * paramArrayOflong2[12] + paramArrayOflong1[6] * paramArrayOflong2[11] + paramArrayOflong1[7] * paramArrayOflong2[10] + paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8] + paramArrayOflong1[10] * paramArrayOflong2[7] + paramArrayOflong1[11] * paramArrayOflong2[6] + paramArrayOflong1[12] * paramArrayOflong2[5] + paramArrayOflong1[13] * paramArrayOflong2[4];
/* 832 */     long l19 = paramArrayOflong1[5] * paramArrayOflong2[13] + paramArrayOflong1[6] * paramArrayOflong2[12] + paramArrayOflong1[7] * paramArrayOflong2[11] + paramArrayOflong1[8] * paramArrayOflong2[10] + paramArrayOflong1[9] * paramArrayOflong2[9] + paramArrayOflong1[10] * paramArrayOflong2[8] + paramArrayOflong1[11] * paramArrayOflong2[7] + paramArrayOflong1[12] * paramArrayOflong2[6] + paramArrayOflong1[13] * paramArrayOflong2[5];
/* 833 */     long l20 = paramArrayOflong1[6] * paramArrayOflong2[13] + paramArrayOflong1[7] * paramArrayOflong2[12] + paramArrayOflong1[8] * paramArrayOflong2[11] + paramArrayOflong1[9] * paramArrayOflong2[10] + paramArrayOflong1[10] * paramArrayOflong2[9] + paramArrayOflong1[11] * paramArrayOflong2[8] + paramArrayOflong1[12] * paramArrayOflong2[7] + paramArrayOflong1[13] * paramArrayOflong2[6];
/* 834 */     long l21 = paramArrayOflong1[7] * paramArrayOflong2[13] + paramArrayOflong1[8] * paramArrayOflong2[12] + paramArrayOflong1[9] * paramArrayOflong2[11] + paramArrayOflong1[10] * paramArrayOflong2[10] + paramArrayOflong1[11] * paramArrayOflong2[9] + paramArrayOflong1[12] * paramArrayOflong2[8] + paramArrayOflong1[13] * paramArrayOflong2[7];
/* 835 */     long l22 = paramArrayOflong1[8] * paramArrayOflong2[13] + paramArrayOflong1[9] * paramArrayOflong2[12] + paramArrayOflong1[10] * paramArrayOflong2[11] + paramArrayOflong1[11] * paramArrayOflong2[10] + paramArrayOflong1[12] * paramArrayOflong2[9] + paramArrayOflong1[13] * paramArrayOflong2[8];
/* 836 */     long l23 = paramArrayOflong1[9] * paramArrayOflong2[13] + paramArrayOflong1[10] * paramArrayOflong2[12] + paramArrayOflong1[11] * paramArrayOflong2[11] + paramArrayOflong1[12] * paramArrayOflong2[10] + paramArrayOflong1[13] * paramArrayOflong2[9];
/* 837 */     long l24 = paramArrayOflong1[10] * paramArrayOflong2[13] + paramArrayOflong1[11] * paramArrayOflong2[12] + paramArrayOflong1[12] * paramArrayOflong2[11] + paramArrayOflong1[13] * paramArrayOflong2[10];
/* 838 */     long l25 = paramArrayOflong1[11] * paramArrayOflong2[13] + paramArrayOflong1[12] * paramArrayOflong2[12] + paramArrayOflong1[13] * paramArrayOflong2[11];
/* 839 */     long l26 = paramArrayOflong1[12] * paramArrayOflong2[13] + paramArrayOflong1[13] * paramArrayOflong2[12];
/* 840 */     long l27 = paramArrayOflong1[13] * paramArrayOflong2[13];
/*     */     
/* 842 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27);
/*     */   }
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/* 846 */     carryReduce(paramArrayOflong, paramArrayOflong[0], paramArrayOflong[1], paramArrayOflong[2], paramArrayOflong[3], paramArrayOflong[4], paramArrayOflong[5], paramArrayOflong[6], paramArrayOflong[7], paramArrayOflong[8], paramArrayOflong[9], paramArrayOflong[10], paramArrayOflong[11], paramArrayOflong[12], paramArrayOflong[13]);
/*     */   }
/*     */   
/*     */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 850 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 851 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 852 */     long l3 = 2L * paramArrayOflong1[0] * paramArrayOflong1[2] + paramArrayOflong1[1] * paramArrayOflong1[1];
/* 853 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 854 */     long l5 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]) + paramArrayOflong1[2] * paramArrayOflong1[2];
/* 855 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 856 */     long l7 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]) + paramArrayOflong1[3] * paramArrayOflong1[3];
/* 857 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 858 */     long l9 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]) + paramArrayOflong1[4] * paramArrayOflong1[4];
/* 859 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 860 */     long l11 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[10] + paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]) + paramArrayOflong1[5] * paramArrayOflong1[5];
/* 861 */     long l12 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[11] + paramArrayOflong1[1] * paramArrayOflong1[10] + paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 862 */     long l13 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[12] + paramArrayOflong1[1] * paramArrayOflong1[11] + paramArrayOflong1[2] * paramArrayOflong1[10] + paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]) + paramArrayOflong1[6] * paramArrayOflong1[6];
/* 863 */     long l14 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[13] + paramArrayOflong1[1] * paramArrayOflong1[12] + paramArrayOflong1[2] * paramArrayOflong1[11] + paramArrayOflong1[3] * paramArrayOflong1[10] + paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 864 */     long l15 = 2L * (paramArrayOflong1[1] * paramArrayOflong1[13] + paramArrayOflong1[2] * paramArrayOflong1[12] + paramArrayOflong1[3] * paramArrayOflong1[11] + paramArrayOflong1[4] * paramArrayOflong1[10] + paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]) + paramArrayOflong1[7] * paramArrayOflong1[7];
/* 865 */     long l16 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[13] + paramArrayOflong1[3] * paramArrayOflong1[12] + paramArrayOflong1[4] * paramArrayOflong1[11] + paramArrayOflong1[5] * paramArrayOflong1[10] + paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 866 */     long l17 = 2L * (paramArrayOflong1[3] * paramArrayOflong1[13] + paramArrayOflong1[4] * paramArrayOflong1[12] + paramArrayOflong1[5] * paramArrayOflong1[11] + paramArrayOflong1[6] * paramArrayOflong1[10] + paramArrayOflong1[7] * paramArrayOflong1[9]) + paramArrayOflong1[8] * paramArrayOflong1[8];
/* 867 */     long l18 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[13] + paramArrayOflong1[5] * paramArrayOflong1[12] + paramArrayOflong1[6] * paramArrayOflong1[11] + paramArrayOflong1[7] * paramArrayOflong1[10] + paramArrayOflong1[8] * paramArrayOflong1[9]);
/* 868 */     long l19 = 2L * (paramArrayOflong1[5] * paramArrayOflong1[13] + paramArrayOflong1[6] * paramArrayOflong1[12] + paramArrayOflong1[7] * paramArrayOflong1[11] + paramArrayOflong1[8] * paramArrayOflong1[10]) + paramArrayOflong1[9] * paramArrayOflong1[9];
/* 869 */     long l20 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[13] + paramArrayOflong1[7] * paramArrayOflong1[12] + paramArrayOflong1[8] * paramArrayOflong1[11] + paramArrayOflong1[9] * paramArrayOflong1[10]);
/* 870 */     long l21 = 2L * (paramArrayOflong1[7] * paramArrayOflong1[13] + paramArrayOflong1[8] * paramArrayOflong1[12] + paramArrayOflong1[9] * paramArrayOflong1[11]) + paramArrayOflong1[10] * paramArrayOflong1[10];
/* 871 */     long l22 = 2L * (paramArrayOflong1[8] * paramArrayOflong1[13] + paramArrayOflong1[9] * paramArrayOflong1[12] + paramArrayOflong1[10] * paramArrayOflong1[11]);
/* 872 */     long l23 = 2L * (paramArrayOflong1[9] * paramArrayOflong1[13] + paramArrayOflong1[10] * paramArrayOflong1[12]) + paramArrayOflong1[11] * paramArrayOflong1[11];
/* 873 */     long l24 = 2L * (paramArrayOflong1[10] * paramArrayOflong1[13] + paramArrayOflong1[11] * paramArrayOflong1[12]);
/* 874 */     long l25 = 2L * paramArrayOflong1[11] * paramArrayOflong1[13] + paramArrayOflong1[12] * paramArrayOflong1[12];
/* 875 */     long l26 = 2L * paramArrayOflong1[12] * paramArrayOflong1[13];
/* 876 */     long l27 = paramArrayOflong1[13] * paramArrayOflong1[13];
/*     */     
/* 878 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/P384OrderField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */