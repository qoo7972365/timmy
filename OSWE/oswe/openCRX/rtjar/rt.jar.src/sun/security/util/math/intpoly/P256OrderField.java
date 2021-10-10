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
/*     */ public class P256OrderField
/*     */   extends IntegerPolynomial
/*     */ {
/*     */   private static final int BITS_PER_LIMB = 26;
/*     */   private static final int NUM_LIMBS = 10;
/*     */   private static final int MAX_ADDS = 1;
/*  37 */   public static final BigInteger MODULUS = evaluateModulus();
/*     */   private static final long CARRY_ADD = 33554432L;
/*     */   private static final int LIMB_MASK = 67108863;
/*     */   
/*     */   public P256OrderField() {
/*  42 */     super(26, 10, 1, MODULUS);
/*     */   }
/*     */   
/*     */   private static BigInteger evaluateModulus() {
/*  46 */     BigInteger bigInteger = BigInteger.valueOf(2L).pow(256);
/*  47 */     bigInteger = bigInteger.add(BigInteger.valueOf(6497617L));
/*  48 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(26).multiply(BigInteger.valueOf(26038081L)));
/*  49 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(52).multiply(BigInteger.valueOf(32001852L)));
/*  50 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(78).multiply(BigInteger.valueOf(21586850L)));
/*  51 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(104).multiply(BigInteger.valueOf(4397317L)));
/*  52 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(182).multiply(BigInteger.valueOf(1024L)));
/*  53 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(208).multiply(BigInteger.valueOf(65536L)));
/*  54 */     return bigInteger;
/*     */   }
/*     */   
/*     */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*  58 */     long l1 = paramArrayOflong[9] >> 22L;
/*  59 */     paramArrayOflong[9] = paramArrayOflong[9] - (l1 << 22L);
/*  60 */     long l2 = -6497617L * l1;
/*  61 */     paramArrayOflong[0] = paramArrayOflong[0] + l2;
/*  62 */     l2 = 26038081L * l1;
/*  63 */     paramArrayOflong[1] = paramArrayOflong[1] + l2;
/*  64 */     l2 = -32001852L * l1;
/*  65 */     paramArrayOflong[2] = paramArrayOflong[2] + l2;
/*  66 */     l2 = 21586850L * l1;
/*  67 */     paramArrayOflong[3] = paramArrayOflong[3] + l2;
/*  68 */     l2 = 4397317L * l1;
/*  69 */     paramArrayOflong[4] = paramArrayOflong[4] + l2;
/*  70 */     l2 = -1024L * l1;
/*  71 */     paramArrayOflong[7] = paramArrayOflong[7] + l2;
/*  72 */     l2 = 65536L * l1;
/*  73 */     paramArrayOflong[8] = paramArrayOflong[8] + l2;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19) {
/*  76 */     long l1 = 0L;
/*     */     
/*  78 */     long l2 = paramLong1 + 33554432L >> 26L;
/*  79 */     paramLong1 -= l2 << 26L;
/*  80 */     paramLong2 += l2;
/*     */     
/*  82 */     l2 = paramLong2 + 33554432L >> 26L;
/*  83 */     paramLong2 -= l2 << 26L;
/*  84 */     paramLong3 += l2;
/*     */     
/*  86 */     l2 = paramLong3 + 33554432L >> 26L;
/*  87 */     paramLong3 -= l2 << 26L;
/*  88 */     paramLong4 += l2;
/*     */     
/*  90 */     l2 = paramLong4 + 33554432L >> 26L;
/*  91 */     paramLong4 -= l2 << 26L;
/*  92 */     paramLong5 += l2;
/*     */     
/*  94 */     l2 = paramLong5 + 33554432L >> 26L;
/*  95 */     paramLong5 -= l2 << 26L;
/*  96 */     paramLong6 += l2;
/*     */     
/*  98 */     l2 = paramLong6 + 33554432L >> 26L;
/*  99 */     paramLong6 -= l2 << 26L;
/* 100 */     paramLong7 += l2;
/*     */     
/* 102 */     l2 = paramLong7 + 33554432L >> 26L;
/* 103 */     paramLong7 -= l2 << 26L;
/* 104 */     paramLong8 += l2;
/*     */     
/* 106 */     l2 = paramLong8 + 33554432L >> 26L;
/* 107 */     paramLong8 -= l2 << 26L;
/* 108 */     paramLong9 += l2;
/*     */     
/* 110 */     l2 = paramLong9 + 33554432L >> 26L;
/* 111 */     paramLong9 -= l2 << 26L;
/* 112 */     paramLong10 += l2;
/*     */     
/* 114 */     l2 = paramLong10 + 33554432L >> 26L;
/* 115 */     paramLong10 -= l2 << 26L;
/* 116 */     paramLong11 += l2;
/*     */     
/* 118 */     l2 = paramLong11 + 33554432L >> 26L;
/* 119 */     paramLong11 -= l2 << 26L;
/* 120 */     paramLong12 += l2;
/*     */     
/* 122 */     l2 = paramLong12 + 33554432L >> 26L;
/* 123 */     paramLong12 -= l2 << 26L;
/* 124 */     paramLong13 += l2;
/*     */     
/* 126 */     l2 = paramLong13 + 33554432L >> 26L;
/* 127 */     paramLong13 -= l2 << 26L;
/* 128 */     paramLong14 += l2;
/*     */     
/* 130 */     l2 = paramLong14 + 33554432L >> 26L;
/* 131 */     paramLong14 -= l2 << 26L;
/* 132 */     paramLong15 += l2;
/*     */     
/* 134 */     l2 = paramLong15 + 33554432L >> 26L;
/* 135 */     paramLong15 -= l2 << 26L;
/* 136 */     paramLong16 += l2;
/*     */     
/* 138 */     l2 = paramLong16 + 33554432L >> 26L;
/* 139 */     paramLong16 -= l2 << 26L;
/* 140 */     paramLong17 += l2;
/*     */     
/* 142 */     l2 = paramLong17 + 33554432L >> 26L;
/* 143 */     paramLong17 -= l2 << 26L;
/* 144 */     paramLong18 += l2;
/*     */     
/* 146 */     l2 = paramLong18 + 33554432L >> 26L;
/* 147 */     paramLong18 -= l2 << 26L;
/* 148 */     paramLong19 += l2;
/*     */     
/* 150 */     l2 = paramLong19 + 33554432L >> 26L;
/* 151 */     paramLong19 -= l2 << 26L;
/* 152 */     l1 += l2;
/*     */     
/* 154 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, l1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20) {
/* 160 */     long l = -6497617L * paramLong20;
/* 161 */     paramLong10 += l << 4L & 0x3FFFFFFL;
/* 162 */     paramLong11 += l >> 22L;
/* 163 */     l = 26038081L * paramLong20;
/* 164 */     paramLong11 += l << 4L & 0x3FFFFFFL;
/* 165 */     paramLong12 += l >> 22L;
/* 166 */     l = -32001852L * paramLong20;
/* 167 */     paramLong12 += l << 4L & 0x3FFFFFFL;
/* 168 */     paramLong13 += l >> 22L;
/* 169 */     l = 21586850L * paramLong20;
/* 170 */     paramLong13 += l << 4L & 0x3FFFFFFL;
/* 171 */     paramLong14 += l >> 22L;
/* 172 */     l = 4397317L * paramLong20;
/* 173 */     paramLong14 += l << 4L & 0x3FFFFFFL;
/* 174 */     paramLong15 += l >> 22L;
/* 175 */     l = -1024L * paramLong20;
/* 176 */     paramLong17 += l << 4L & 0x3FFFFFFL;
/* 177 */     paramLong18 += l >> 22L;
/* 178 */     l = 65536L * paramLong20;
/* 179 */     paramLong18 += l << 4L & 0x3FFFFFFL;
/* 180 */     paramLong19 += l >> 22L;
/*     */     
/* 182 */     l = -6497617L * paramLong19;
/* 183 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 184 */     paramLong10 += l >> 22L;
/* 185 */     l = 26038081L * paramLong19;
/* 186 */     paramLong10 += l << 4L & 0x3FFFFFFL;
/* 187 */     paramLong11 += l >> 22L;
/* 188 */     l = -32001852L * paramLong19;
/* 189 */     paramLong11 += l << 4L & 0x3FFFFFFL;
/* 190 */     paramLong12 += l >> 22L;
/* 191 */     l = 21586850L * paramLong19;
/* 192 */     paramLong12 += l << 4L & 0x3FFFFFFL;
/* 193 */     paramLong13 += l >> 22L;
/* 194 */     l = 4397317L * paramLong19;
/* 195 */     paramLong13 += l << 4L & 0x3FFFFFFL;
/* 196 */     paramLong14 += l >> 22L;
/* 197 */     l = -1024L * paramLong19;
/* 198 */     paramLong16 += l << 4L & 0x3FFFFFFL;
/* 199 */     paramLong17 += l >> 22L;
/* 200 */     l = 65536L * paramLong19;
/* 201 */     paramLong17 += l << 4L & 0x3FFFFFFL;
/* 202 */     paramLong18 += l >> 22L;
/*     */     
/* 204 */     l = -6497617L * paramLong18;
/* 205 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 206 */     paramLong9 += l >> 22L;
/* 207 */     l = 26038081L * paramLong18;
/* 208 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 209 */     paramLong10 += l >> 22L;
/* 210 */     l = -32001852L * paramLong18;
/* 211 */     paramLong10 += l << 4L & 0x3FFFFFFL;
/* 212 */     paramLong11 += l >> 22L;
/* 213 */     l = 21586850L * paramLong18;
/* 214 */     paramLong11 += l << 4L & 0x3FFFFFFL;
/* 215 */     paramLong12 += l >> 22L;
/* 216 */     l = 4397317L * paramLong18;
/* 217 */     paramLong12 += l << 4L & 0x3FFFFFFL;
/* 218 */     paramLong13 += l >> 22L;
/* 219 */     l = -1024L * paramLong18;
/* 220 */     paramLong15 += l << 4L & 0x3FFFFFFL;
/* 221 */     paramLong16 += l >> 22L;
/* 222 */     l = 65536L * paramLong18;
/* 223 */     paramLong16 += l << 4L & 0x3FFFFFFL;
/* 224 */     paramLong17 += l >> 22L;
/*     */     
/* 226 */     l = -6497617L * paramLong17;
/* 227 */     paramLong7 += l << 4L & 0x3FFFFFFL;
/* 228 */     paramLong8 += l >> 22L;
/* 229 */     l = 26038081L * paramLong17;
/* 230 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 231 */     paramLong9 += l >> 22L;
/* 232 */     l = -32001852L * paramLong17;
/* 233 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 234 */     paramLong10 += l >> 22L;
/* 235 */     l = 21586850L * paramLong17;
/* 236 */     paramLong10 += l << 4L & 0x3FFFFFFL;
/* 237 */     paramLong11 += l >> 22L;
/* 238 */     l = 4397317L * paramLong17;
/* 239 */     paramLong11 += l << 4L & 0x3FFFFFFL;
/* 240 */     paramLong12 += l >> 22L;
/* 241 */     l = -1024L * paramLong17;
/* 242 */     paramLong14 += l << 4L & 0x3FFFFFFL;
/* 243 */     paramLong15 += l >> 22L;
/* 244 */     l = 65536L * paramLong17;
/* 245 */     paramLong15 += l << 4L & 0x3FFFFFFL;
/* 246 */     paramLong16 += l >> 22L;
/*     */     
/* 248 */     l = -6497617L * paramLong16;
/* 249 */     paramLong6 += l << 4L & 0x3FFFFFFL;
/* 250 */     paramLong7 += l >> 22L;
/* 251 */     l = 26038081L * paramLong16;
/* 252 */     paramLong7 += l << 4L & 0x3FFFFFFL;
/* 253 */     paramLong8 += l >> 22L;
/* 254 */     l = -32001852L * paramLong16;
/* 255 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 256 */     paramLong9 += l >> 22L;
/* 257 */     l = 21586850L * paramLong16;
/* 258 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 259 */     paramLong10 += l >> 22L;
/* 260 */     l = 4397317L * paramLong16;
/* 261 */     paramLong10 += l << 4L & 0x3FFFFFFL;
/* 262 */     paramLong11 += l >> 22L;
/* 263 */     l = -1024L * paramLong16;
/* 264 */     paramLong13 += l << 4L & 0x3FFFFFFL;
/* 265 */     paramLong14 += l >> 22L;
/* 266 */     l = 65536L * paramLong16;
/* 267 */     paramLong14 += l << 4L & 0x3FFFFFFL;
/* 268 */     paramLong15 += l >> 22L;
/*     */     
/* 270 */     l = -6497617L * paramLong15;
/* 271 */     paramLong5 += l << 4L & 0x3FFFFFFL;
/* 272 */     paramLong6 += l >> 22L;
/* 273 */     l = 26038081L * paramLong15;
/* 274 */     paramLong6 += l << 4L & 0x3FFFFFFL;
/* 275 */     paramLong7 += l >> 22L;
/* 276 */     l = -32001852L * paramLong15;
/* 277 */     paramLong7 += l << 4L & 0x3FFFFFFL;
/* 278 */     paramLong8 += l >> 22L;
/* 279 */     l = 21586850L * paramLong15;
/* 280 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 281 */     paramLong9 += l >> 22L;
/* 282 */     l = 4397317L * paramLong15;
/* 283 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 284 */     paramLong10 += l >> 22L;
/* 285 */     l = -1024L * paramLong15;
/* 286 */     paramLong12 += l << 4L & 0x3FFFFFFL;
/* 287 */     paramLong13 += l >> 22L;
/* 288 */     l = 65536L * paramLong15;
/* 289 */     paramLong13 += l << 4L & 0x3FFFFFFL;
/* 290 */     paramLong14 += l >> 22L;
/*     */     
/* 292 */     l = -6497617L * paramLong14;
/* 293 */     paramLong4 += l << 4L & 0x3FFFFFFL;
/* 294 */     paramLong5 += l >> 22L;
/* 295 */     l = 26038081L * paramLong14;
/* 296 */     paramLong5 += l << 4L & 0x3FFFFFFL;
/* 297 */     paramLong6 += l >> 22L;
/* 298 */     l = -32001852L * paramLong14;
/* 299 */     paramLong6 += l << 4L & 0x3FFFFFFL;
/* 300 */     paramLong7 += l >> 22L;
/* 301 */     l = 21586850L * paramLong14;
/* 302 */     paramLong7 += l << 4L & 0x3FFFFFFL;
/* 303 */     paramLong8 += l >> 22L;
/* 304 */     l = 4397317L * paramLong14;
/* 305 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 306 */     paramLong9 += l >> 22L;
/* 307 */     l = -1024L * paramLong14;
/* 308 */     paramLong11 += l << 4L & 0x3FFFFFFL;
/* 309 */     paramLong12 += l >> 22L;
/* 310 */     l = 65536L * paramLong14;
/* 311 */     paramLong12 += l << 4L & 0x3FFFFFFL;
/* 312 */     paramLong13 += l >> 22L;
/*     */     
/* 314 */     l = -6497617L * paramLong13;
/* 315 */     paramLong3 += l << 4L & 0x3FFFFFFL;
/* 316 */     paramLong4 += l >> 22L;
/* 317 */     l = 26038081L * paramLong13;
/* 318 */     paramLong4 += l << 4L & 0x3FFFFFFL;
/* 319 */     paramLong5 += l >> 22L;
/* 320 */     l = -32001852L * paramLong13;
/* 321 */     paramLong5 += l << 4L & 0x3FFFFFFL;
/* 322 */     paramLong6 += l >> 22L;
/* 323 */     l = 21586850L * paramLong13;
/* 324 */     paramLong6 += l << 4L & 0x3FFFFFFL;
/* 325 */     paramLong7 += l >> 22L;
/* 326 */     l = 4397317L * paramLong13;
/* 327 */     paramLong7 += l << 4L & 0x3FFFFFFL;
/* 328 */     paramLong8 += l >> 22L;
/* 329 */     l = -1024L * paramLong13;
/* 330 */     paramLong10 += l << 4L & 0x3FFFFFFL;
/* 331 */     paramLong11 += l >> 22L;
/* 332 */     l = 65536L * paramLong13;
/* 333 */     paramLong11 += l << 4L & 0x3FFFFFFL;
/* 334 */     paramLong12 += l >> 22L;
/*     */     
/* 336 */     l = -6497617L * paramLong12;
/* 337 */     paramLong2 += l << 4L & 0x3FFFFFFL;
/* 338 */     paramLong3 += l >> 22L;
/* 339 */     l = 26038081L * paramLong12;
/* 340 */     paramLong3 += l << 4L & 0x3FFFFFFL;
/* 341 */     paramLong4 += l >> 22L;
/* 342 */     l = -32001852L * paramLong12;
/* 343 */     paramLong4 += l << 4L & 0x3FFFFFFL;
/* 344 */     paramLong5 += l >> 22L;
/* 345 */     l = 21586850L * paramLong12;
/* 346 */     paramLong5 += l << 4L & 0x3FFFFFFL;
/* 347 */     paramLong6 += l >> 22L;
/* 348 */     l = 4397317L * paramLong12;
/* 349 */     paramLong6 += l << 4L & 0x3FFFFFFL;
/* 350 */     paramLong7 += l >> 22L;
/* 351 */     l = -1024L * paramLong12;
/* 352 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 353 */     paramLong10 += l >> 22L;
/* 354 */     l = 65536L * paramLong12;
/* 355 */     paramLong10 += l << 4L & 0x3FFFFFFL;
/* 356 */     paramLong11 += l >> 22L;
/*     */     
/* 358 */     l = -6497617L * paramLong11;
/* 359 */     paramLong1 += l << 4L & 0x3FFFFFFL;
/* 360 */     paramLong2 += l >> 22L;
/* 361 */     l = 26038081L * paramLong11;
/* 362 */     paramLong2 += l << 4L & 0x3FFFFFFL;
/* 363 */     paramLong3 += l >> 22L;
/* 364 */     l = -32001852L * paramLong11;
/* 365 */     paramLong3 += l << 4L & 0x3FFFFFFL;
/* 366 */     paramLong4 += l >> 22L;
/* 367 */     l = 21586850L * paramLong11;
/* 368 */     paramLong4 += l << 4L & 0x3FFFFFFL;
/* 369 */     paramLong5 += l >> 22L;
/* 370 */     l = 4397317L * paramLong11;
/* 371 */     paramLong5 += l << 4L & 0x3FFFFFFL;
/* 372 */     paramLong6 += l >> 22L;
/* 373 */     l = -1024L * paramLong11;
/* 374 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 375 */     paramLong9 += l >> 22L;
/* 376 */     l = 65536L * paramLong11;
/* 377 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 378 */     paramLong10 += l >> 22L;
/* 379 */     paramLong11 = 0L;
/*     */     
/* 381 */     carryReduce1(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce1(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20) {
/* 387 */     long l = paramLong1 + 33554432L >> 26L;
/* 388 */     paramLong1 -= l << 26L;
/* 389 */     paramLong2 += l;
/*     */     
/* 391 */     l = paramLong2 + 33554432L >> 26L;
/* 392 */     paramLong2 -= l << 26L;
/* 393 */     paramLong3 += l;
/*     */     
/* 395 */     l = paramLong3 + 33554432L >> 26L;
/* 396 */     paramLong3 -= l << 26L;
/* 397 */     paramLong4 += l;
/*     */     
/* 399 */     l = paramLong4 + 33554432L >> 26L;
/* 400 */     paramLong4 -= l << 26L;
/* 401 */     paramLong5 += l;
/*     */     
/* 403 */     l = paramLong5 + 33554432L >> 26L;
/* 404 */     paramLong5 -= l << 26L;
/* 405 */     paramLong6 += l;
/*     */     
/* 407 */     l = paramLong6 + 33554432L >> 26L;
/* 408 */     paramLong6 -= l << 26L;
/* 409 */     paramLong7 += l;
/*     */     
/* 411 */     l = paramLong7 + 33554432L >> 26L;
/* 412 */     paramLong7 -= l << 26L;
/* 413 */     paramLong8 += l;
/*     */     
/* 415 */     l = paramLong8 + 33554432L >> 26L;
/* 416 */     paramLong8 -= l << 26L;
/* 417 */     paramLong9 += l;
/*     */     
/* 419 */     l = paramLong9 + 33554432L >> 26L;
/* 420 */     paramLong9 -= l << 26L;
/* 421 */     paramLong10 += l;
/*     */     
/* 423 */     l = paramLong10 + 33554432L >> 26L;
/* 424 */     paramLong10 -= l << 26L;
/* 425 */     paramLong11 += l;
/*     */     
/* 427 */     carryReduce2(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce2(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20) {
/* 433 */     long l = -6497617L * paramLong11;
/* 434 */     paramLong1 += l << 4L & 0x3FFFFFFL;
/* 435 */     paramLong2 += l >> 22L;
/* 436 */     l = 26038081L * paramLong11;
/* 437 */     paramLong2 += l << 4L & 0x3FFFFFFL;
/* 438 */     paramLong3 += l >> 22L;
/* 439 */     l = -32001852L * paramLong11;
/* 440 */     paramLong3 += l << 4L & 0x3FFFFFFL;
/* 441 */     paramLong4 += l >> 22L;
/* 442 */     l = 21586850L * paramLong11;
/* 443 */     paramLong4 += l << 4L & 0x3FFFFFFL;
/* 444 */     paramLong5 += l >> 22L;
/* 445 */     l = 4397317L * paramLong11;
/* 446 */     paramLong5 += l << 4L & 0x3FFFFFFL;
/* 447 */     paramLong6 += l >> 22L;
/* 448 */     l = -1024L * paramLong11;
/* 449 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 450 */     paramLong9 += l >> 22L;
/* 451 */     l = 65536L * paramLong11;
/* 452 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 453 */     paramLong10 += l >> 22L;
/*     */     
/* 455 */     l = paramLong1 + 33554432L >> 26L;
/* 456 */     paramLong1 -= l << 26L;
/* 457 */     paramLong2 += l;
/*     */     
/* 459 */     l = paramLong2 + 33554432L >> 26L;
/* 460 */     paramLong2 -= l << 26L;
/* 461 */     paramLong3 += l;
/*     */     
/* 463 */     l = paramLong3 + 33554432L >> 26L;
/* 464 */     paramLong3 -= l << 26L;
/* 465 */     paramLong4 += l;
/*     */     
/* 467 */     l = paramLong4 + 33554432L >> 26L;
/* 468 */     paramLong4 -= l << 26L;
/* 469 */     paramLong5 += l;
/*     */     
/* 471 */     l = paramLong5 + 33554432L >> 26L;
/* 472 */     paramLong5 -= l << 26L;
/* 473 */     paramLong6 += l;
/*     */     
/* 475 */     l = paramLong6 + 33554432L >> 26L;
/* 476 */     paramLong6 -= l << 26L;
/* 477 */     paramLong7 += l;
/*     */     
/* 479 */     l = paramLong7 + 33554432L >> 26L;
/* 480 */     paramLong7 -= l << 26L;
/* 481 */     paramLong8 += l;
/*     */     
/* 483 */     l = paramLong8 + 33554432L >> 26L;
/* 484 */     paramLong8 -= l << 26L;
/* 485 */     paramLong9 += l;
/*     */     
/* 487 */     l = paramLong9 + 33554432L >> 26L;
/* 488 */     paramLong9 -= l << 26L;
/* 489 */     paramLong10 += l;
/*     */     
/* 491 */     paramArrayOflong[0] = paramLong1;
/* 492 */     paramArrayOflong[1] = paramLong2;
/* 493 */     paramArrayOflong[2] = paramLong3;
/* 494 */     paramArrayOflong[3] = paramLong4;
/* 495 */     paramArrayOflong[4] = paramLong5;
/* 496 */     paramArrayOflong[5] = paramLong6;
/* 497 */     paramArrayOflong[6] = paramLong7;
/* 498 */     paramArrayOflong[7] = paramLong8;
/* 499 */     paramArrayOflong[8] = paramLong9;
/* 500 */     paramArrayOflong[9] = paramLong10;
/*     */   }
/*     */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10) {
/* 503 */     long l1 = 0L;
/*     */     
/* 505 */     long l2 = paramLong1 + 33554432L >> 26L;
/* 506 */     paramLong1 -= l2 << 26L;
/* 507 */     paramLong2 += l2;
/*     */     
/* 509 */     l2 = paramLong2 + 33554432L >> 26L;
/* 510 */     paramLong2 -= l2 << 26L;
/* 511 */     paramLong3 += l2;
/*     */     
/* 513 */     l2 = paramLong3 + 33554432L >> 26L;
/* 514 */     paramLong3 -= l2 << 26L;
/* 515 */     paramLong4 += l2;
/*     */     
/* 517 */     l2 = paramLong4 + 33554432L >> 26L;
/* 518 */     paramLong4 -= l2 << 26L;
/* 519 */     paramLong5 += l2;
/*     */     
/* 521 */     l2 = paramLong5 + 33554432L >> 26L;
/* 522 */     paramLong5 -= l2 << 26L;
/* 523 */     paramLong6 += l2;
/*     */     
/* 525 */     l2 = paramLong6 + 33554432L >> 26L;
/* 526 */     paramLong6 -= l2 << 26L;
/* 527 */     paramLong7 += l2;
/*     */     
/* 529 */     l2 = paramLong7 + 33554432L >> 26L;
/* 530 */     paramLong7 -= l2 << 26L;
/* 531 */     paramLong8 += l2;
/*     */     
/* 533 */     l2 = paramLong8 + 33554432L >> 26L;
/* 534 */     paramLong8 -= l2 << 26L;
/* 535 */     paramLong9 += l2;
/*     */     
/* 537 */     l2 = paramLong9 + 33554432L >> 26L;
/* 538 */     paramLong9 -= l2 << 26L;
/* 539 */     paramLong10 += l2;
/*     */     
/* 541 */     l2 = paramLong10 + 33554432L >> 26L;
/* 542 */     paramLong10 -= l2 << 26L;
/* 543 */     l1 += l2;
/*     */     
/* 545 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, l1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11) {
/* 551 */     long l = -6497617L * paramLong11;
/* 552 */     paramLong1 += l << 4L & 0x3FFFFFFL;
/* 553 */     paramLong2 += l >> 22L;
/* 554 */     l = 26038081L * paramLong11;
/* 555 */     paramLong2 += l << 4L & 0x3FFFFFFL;
/* 556 */     paramLong3 += l >> 22L;
/* 557 */     l = -32001852L * paramLong11;
/* 558 */     paramLong3 += l << 4L & 0x3FFFFFFL;
/* 559 */     paramLong4 += l >> 22L;
/* 560 */     l = 21586850L * paramLong11;
/* 561 */     paramLong4 += l << 4L & 0x3FFFFFFL;
/* 562 */     paramLong5 += l >> 22L;
/* 563 */     l = 4397317L * paramLong11;
/* 564 */     paramLong5 += l << 4L & 0x3FFFFFFL;
/* 565 */     paramLong6 += l >> 22L;
/* 566 */     l = -1024L * paramLong11;
/* 567 */     paramLong8 += l << 4L & 0x3FFFFFFL;
/* 568 */     paramLong9 += l >> 22L;
/* 569 */     l = 65536L * paramLong11;
/* 570 */     paramLong9 += l << 4L & 0x3FFFFFFL;
/* 571 */     paramLong10 += l >> 22L;
/*     */     
/* 573 */     l = paramLong1 + 33554432L >> 26L;
/* 574 */     paramLong1 -= l << 26L;
/* 575 */     paramLong2 += l;
/*     */     
/* 577 */     l = paramLong2 + 33554432L >> 26L;
/* 578 */     paramLong2 -= l << 26L;
/* 579 */     paramLong3 += l;
/*     */     
/* 581 */     l = paramLong3 + 33554432L >> 26L;
/* 582 */     paramLong3 -= l << 26L;
/* 583 */     paramLong4 += l;
/*     */     
/* 585 */     l = paramLong4 + 33554432L >> 26L;
/* 586 */     paramLong4 -= l << 26L;
/* 587 */     paramLong5 += l;
/*     */     
/* 589 */     l = paramLong5 + 33554432L >> 26L;
/* 590 */     paramLong5 -= l << 26L;
/* 591 */     paramLong6 += l;
/*     */     
/* 593 */     l = paramLong6 + 33554432L >> 26L;
/* 594 */     paramLong6 -= l << 26L;
/* 595 */     paramLong7 += l;
/*     */     
/* 597 */     l = paramLong7 + 33554432L >> 26L;
/* 598 */     paramLong7 -= l << 26L;
/* 599 */     paramLong8 += l;
/*     */     
/* 601 */     l = paramLong8 + 33554432L >> 26L;
/* 602 */     paramLong8 -= l << 26L;
/* 603 */     paramLong9 += l;
/*     */     
/* 605 */     l = paramLong9 + 33554432L >> 26L;
/* 606 */     paramLong9 -= l << 26L;
/* 607 */     paramLong10 += l;
/*     */     
/* 609 */     paramArrayOflong[0] = paramLong1;
/* 610 */     paramArrayOflong[1] = paramLong2;
/* 611 */     paramArrayOflong[2] = paramLong3;
/* 612 */     paramArrayOflong[3] = paramLong4;
/* 613 */     paramArrayOflong[4] = paramLong5;
/* 614 */     paramArrayOflong[5] = paramLong6;
/* 615 */     paramArrayOflong[6] = paramLong7;
/* 616 */     paramArrayOflong[7] = paramLong8;
/* 617 */     paramArrayOflong[8] = paramLong9;
/* 618 */     paramArrayOflong[9] = paramLong10;
/*     */   }
/*     */   
/*     */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 622 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/* 623 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/* 624 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/* 625 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/* 626 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/* 627 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/* 628 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/* 629 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 630 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 631 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 632 */     long l11 = paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1];
/* 633 */     long l12 = paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2];
/* 634 */     long l13 = paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3];
/* 635 */     long l14 = paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4];
/* 636 */     long l15 = paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5];
/* 637 */     long l16 = paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6];
/* 638 */     long l17 = paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7];
/* 639 */     long l18 = paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8];
/* 640 */     long l19 = paramArrayOflong1[9] * paramArrayOflong2[9];
/*     */     
/* 642 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19);
/*     */   }
/*     */   
/*     */   protected void reduce(long[] paramArrayOflong) {
/* 646 */     carryReduce(paramArrayOflong, paramArrayOflong[0], paramArrayOflong[1], paramArrayOflong[2], paramArrayOflong[3], paramArrayOflong[4], paramArrayOflong[5], paramArrayOflong[6], paramArrayOflong[7], paramArrayOflong[8], paramArrayOflong[9]);
/*     */   }
/*     */   
/*     */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 650 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 651 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 652 */     long l3 = 2L * paramArrayOflong1[0] * paramArrayOflong1[2] + paramArrayOflong1[1] * paramArrayOflong1[1];
/* 653 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 654 */     long l5 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]) + paramArrayOflong1[2] * paramArrayOflong1[2];
/* 655 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 656 */     long l7 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]) + paramArrayOflong1[3] * paramArrayOflong1[3];
/* 657 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 658 */     long l9 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]) + paramArrayOflong1[4] * paramArrayOflong1[4];
/* 659 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 660 */     long l11 = 2L * (paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]) + paramArrayOflong1[5] * paramArrayOflong1[5];
/* 661 */     long l12 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 662 */     long l13 = 2L * (paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]) + paramArrayOflong1[6] * paramArrayOflong1[6];
/* 663 */     long l14 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 664 */     long l15 = 2L * (paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]) + paramArrayOflong1[7] * paramArrayOflong1[7];
/* 665 */     long l16 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 666 */     long l17 = 2L * paramArrayOflong1[7] * paramArrayOflong1[9] + paramArrayOflong1[8] * paramArrayOflong1[8];
/* 667 */     long l18 = 2L * paramArrayOflong1[8] * paramArrayOflong1[9];
/* 668 */     long l19 = paramArrayOflong1[9] * paramArrayOflong1[9];
/*     */     
/* 670 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/P256OrderField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */