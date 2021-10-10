/*      */ package sun.security.util.math.intpoly;
/*      */ 
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class P521OrderField
/*      */   extends IntegerPolynomial
/*      */ {
/*      */   private static final int BITS_PER_LIMB = 28;
/*      */   private static final int NUM_LIMBS = 19;
/*      */   private static final int MAX_ADDS = 1;
/*   37 */   public static final BigInteger MODULUS = evaluateModulus();
/*      */   private static final long CARRY_ADD = 134217728L;
/*      */   private static final int LIMB_MASK = 268435455;
/*      */   
/*      */   public P521OrderField() {
/*   42 */     super(28, 19, 1, MODULUS);
/*      */   }
/*      */   
/*      */   private static BigInteger evaluateModulus() {
/*   46 */     BigInteger bigInteger = BigInteger.valueOf(2L).pow(521);
/*   47 */     bigInteger = bigInteger.add(BigInteger.valueOf(20472841L));
/*   48 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(28).multiply(BigInteger.valueOf(117141993L)));
/*   49 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(56).multiply(BigInteger.valueOf(62411077L)));
/*   50 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(84).multiply(BigInteger.valueOf(56915814L)));
/*   51 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(112).multiply(BigInteger.valueOf(97532854L)));
/*   52 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(140).multiply(BigInteger.valueOf(76509338L)));
/*   53 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(168).multiply(BigInteger.valueOf(75510783L)));
/*   54 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(196).multiply(BigInteger.valueOf(67962521L)));
/*   55 */     bigInteger = bigInteger.add(BigInteger.valueOf(2L).pow(224).multiply(BigInteger.valueOf(25593732L)));
/*   56 */     bigInteger = bigInteger.subtract(BigInteger.valueOf(2L).pow(252).multiply(BigInteger.valueOf(91L)));
/*   57 */     return bigInteger;
/*      */   }
/*      */   
/*      */   protected void finalCarryReduceLast(long[] paramArrayOflong) {
/*   61 */     long l1 = paramArrayOflong[18] >> 17L;
/*   62 */     paramArrayOflong[18] = paramArrayOflong[18] - (l1 << 17L);
/*   63 */     long l2 = -20472841L * l1;
/*   64 */     paramArrayOflong[0] = paramArrayOflong[0] + l2;
/*   65 */     l2 = -117141993L * l1;
/*   66 */     paramArrayOflong[1] = paramArrayOflong[1] + l2;
/*   67 */     l2 = 62411077L * l1;
/*   68 */     paramArrayOflong[2] = paramArrayOflong[2] + l2;
/*   69 */     l2 = 56915814L * l1;
/*   70 */     paramArrayOflong[3] = paramArrayOflong[3] + l2;
/*   71 */     l2 = -97532854L * l1;
/*   72 */     paramArrayOflong[4] = paramArrayOflong[4] + l2;
/*   73 */     l2 = -76509338L * l1;
/*   74 */     paramArrayOflong[5] = paramArrayOflong[5] + l2;
/*   75 */     l2 = 75510783L * l1;
/*   76 */     paramArrayOflong[6] = paramArrayOflong[6] + l2;
/*   77 */     l2 = 67962521L * l1;
/*   78 */     paramArrayOflong[7] = paramArrayOflong[7] + l2;
/*   79 */     l2 = -25593732L * l1;
/*   80 */     paramArrayOflong[8] = paramArrayOflong[8] + l2;
/*   81 */     l2 = 91L * l1;
/*   82 */     paramArrayOflong[9] = paramArrayOflong[9] + l2;
/*      */   }
/*      */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31, long paramLong32, long paramLong33, long paramLong34, long paramLong35, long paramLong36, long paramLong37) {
/*   85 */     long l1 = 0L;
/*      */     
/*   87 */     long l2 = paramLong1 + 134217728L >> 28L;
/*   88 */     paramLong1 -= l2 << 28L;
/*   89 */     paramLong2 += l2;
/*      */     
/*   91 */     l2 = paramLong2 + 134217728L >> 28L;
/*   92 */     paramLong2 -= l2 << 28L;
/*   93 */     paramLong3 += l2;
/*      */     
/*   95 */     l2 = paramLong3 + 134217728L >> 28L;
/*   96 */     paramLong3 -= l2 << 28L;
/*   97 */     paramLong4 += l2;
/*      */     
/*   99 */     l2 = paramLong4 + 134217728L >> 28L;
/*  100 */     paramLong4 -= l2 << 28L;
/*  101 */     paramLong5 += l2;
/*      */     
/*  103 */     l2 = paramLong5 + 134217728L >> 28L;
/*  104 */     paramLong5 -= l2 << 28L;
/*  105 */     paramLong6 += l2;
/*      */     
/*  107 */     l2 = paramLong6 + 134217728L >> 28L;
/*  108 */     paramLong6 -= l2 << 28L;
/*  109 */     paramLong7 += l2;
/*      */     
/*  111 */     l2 = paramLong7 + 134217728L >> 28L;
/*  112 */     paramLong7 -= l2 << 28L;
/*  113 */     paramLong8 += l2;
/*      */     
/*  115 */     l2 = paramLong8 + 134217728L >> 28L;
/*  116 */     paramLong8 -= l2 << 28L;
/*  117 */     paramLong9 += l2;
/*      */     
/*  119 */     l2 = paramLong9 + 134217728L >> 28L;
/*  120 */     paramLong9 -= l2 << 28L;
/*  121 */     paramLong10 += l2;
/*      */     
/*  123 */     l2 = paramLong10 + 134217728L >> 28L;
/*  124 */     paramLong10 -= l2 << 28L;
/*  125 */     paramLong11 += l2;
/*      */     
/*  127 */     l2 = paramLong11 + 134217728L >> 28L;
/*  128 */     paramLong11 -= l2 << 28L;
/*  129 */     paramLong12 += l2;
/*      */     
/*  131 */     l2 = paramLong12 + 134217728L >> 28L;
/*  132 */     paramLong12 -= l2 << 28L;
/*  133 */     paramLong13 += l2;
/*      */     
/*  135 */     l2 = paramLong13 + 134217728L >> 28L;
/*  136 */     paramLong13 -= l2 << 28L;
/*  137 */     paramLong14 += l2;
/*      */     
/*  139 */     l2 = paramLong14 + 134217728L >> 28L;
/*  140 */     paramLong14 -= l2 << 28L;
/*  141 */     paramLong15 += l2;
/*      */     
/*  143 */     l2 = paramLong15 + 134217728L >> 28L;
/*  144 */     paramLong15 -= l2 << 28L;
/*  145 */     paramLong16 += l2;
/*      */     
/*  147 */     l2 = paramLong16 + 134217728L >> 28L;
/*  148 */     paramLong16 -= l2 << 28L;
/*  149 */     paramLong17 += l2;
/*      */     
/*  151 */     l2 = paramLong17 + 134217728L >> 28L;
/*  152 */     paramLong17 -= l2 << 28L;
/*  153 */     paramLong18 += l2;
/*      */     
/*  155 */     l2 = paramLong18 + 134217728L >> 28L;
/*  156 */     paramLong18 -= l2 << 28L;
/*  157 */     paramLong19 += l2;
/*      */     
/*  159 */     l2 = paramLong19 + 134217728L >> 28L;
/*  160 */     paramLong19 -= l2 << 28L;
/*  161 */     paramLong20 += l2;
/*      */     
/*  163 */     l2 = paramLong20 + 134217728L >> 28L;
/*  164 */     paramLong20 -= l2 << 28L;
/*  165 */     paramLong21 += l2;
/*      */     
/*  167 */     l2 = paramLong21 + 134217728L >> 28L;
/*  168 */     paramLong21 -= l2 << 28L;
/*  169 */     paramLong22 += l2;
/*      */     
/*  171 */     l2 = paramLong22 + 134217728L >> 28L;
/*  172 */     paramLong22 -= l2 << 28L;
/*  173 */     paramLong23 += l2;
/*      */     
/*  175 */     l2 = paramLong23 + 134217728L >> 28L;
/*  176 */     paramLong23 -= l2 << 28L;
/*  177 */     paramLong24 += l2;
/*      */     
/*  179 */     l2 = paramLong24 + 134217728L >> 28L;
/*  180 */     paramLong24 -= l2 << 28L;
/*  181 */     paramLong25 += l2;
/*      */     
/*  183 */     l2 = paramLong25 + 134217728L >> 28L;
/*  184 */     paramLong25 -= l2 << 28L;
/*  185 */     paramLong26 += l2;
/*      */     
/*  187 */     l2 = paramLong26 + 134217728L >> 28L;
/*  188 */     paramLong26 -= l2 << 28L;
/*  189 */     paramLong27 += l2;
/*      */     
/*  191 */     l2 = paramLong27 + 134217728L >> 28L;
/*  192 */     paramLong27 -= l2 << 28L;
/*  193 */     paramLong28 += l2;
/*      */     
/*  195 */     l2 = paramLong28 + 134217728L >> 28L;
/*  196 */     paramLong28 -= l2 << 28L;
/*  197 */     paramLong29 += l2;
/*      */     
/*  199 */     l2 = paramLong29 + 134217728L >> 28L;
/*  200 */     paramLong29 -= l2 << 28L;
/*  201 */     paramLong30 += l2;
/*      */     
/*  203 */     l2 = paramLong30 + 134217728L >> 28L;
/*  204 */     paramLong30 -= l2 << 28L;
/*  205 */     paramLong31 += l2;
/*      */     
/*  207 */     l2 = paramLong31 + 134217728L >> 28L;
/*  208 */     paramLong31 -= l2 << 28L;
/*  209 */     paramLong32 += l2;
/*      */     
/*  211 */     l2 = paramLong32 + 134217728L >> 28L;
/*  212 */     paramLong32 -= l2 << 28L;
/*  213 */     paramLong33 += l2;
/*      */     
/*  215 */     l2 = paramLong33 + 134217728L >> 28L;
/*  216 */     paramLong33 -= l2 << 28L;
/*  217 */     paramLong34 += l2;
/*      */     
/*  219 */     l2 = paramLong34 + 134217728L >> 28L;
/*  220 */     paramLong34 -= l2 << 28L;
/*  221 */     paramLong35 += l2;
/*      */     
/*  223 */     l2 = paramLong35 + 134217728L >> 28L;
/*  224 */     paramLong35 -= l2 << 28L;
/*  225 */     paramLong36 += l2;
/*      */     
/*  227 */     l2 = paramLong36 + 134217728L >> 28L;
/*  228 */     paramLong36 -= l2 << 28L;
/*  229 */     paramLong37 += l2;
/*      */     
/*  231 */     l2 = paramLong37 + 134217728L >> 28L;
/*  232 */     paramLong37 -= l2 << 28L;
/*  233 */     l1 += l2;
/*      */     
/*  235 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, paramLong28, paramLong29, paramLong30, paramLong31, paramLong32, paramLong33, paramLong34, paramLong35, paramLong36, paramLong37, l1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31, long paramLong32, long paramLong33, long paramLong34, long paramLong35, long paramLong36, long paramLong37, long paramLong38) {
/*  241 */     long l = -20472841L * paramLong38;
/*  242 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  243 */     paramLong20 += l >> 17L;
/*  244 */     l = -117141993L * paramLong38;
/*  245 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  246 */     paramLong21 += l >> 17L;
/*  247 */     l = 62411077L * paramLong38;
/*  248 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  249 */     paramLong22 += l >> 17L;
/*  250 */     l = 56915814L * paramLong38;
/*  251 */     paramLong22 += l << 11L & 0xFFFFFFFL;
/*  252 */     paramLong23 += l >> 17L;
/*  253 */     l = -97532854L * paramLong38;
/*  254 */     paramLong23 += l << 11L & 0xFFFFFFFL;
/*  255 */     paramLong24 += l >> 17L;
/*  256 */     l = -76509338L * paramLong38;
/*  257 */     paramLong24 += l << 11L & 0xFFFFFFFL;
/*  258 */     paramLong25 += l >> 17L;
/*  259 */     l = 75510783L * paramLong38;
/*  260 */     paramLong25 += l << 11L & 0xFFFFFFFL;
/*  261 */     paramLong26 += l >> 17L;
/*  262 */     l = 67962521L * paramLong38;
/*  263 */     paramLong26 += l << 11L & 0xFFFFFFFL;
/*  264 */     paramLong27 += l >> 17L;
/*  265 */     l = -25593732L * paramLong38;
/*  266 */     paramLong27 += l << 11L & 0xFFFFFFFL;
/*  267 */     paramLong28 += l >> 17L;
/*  268 */     l = 91L * paramLong38;
/*  269 */     paramLong28 += l << 11L & 0xFFFFFFFL;
/*  270 */     paramLong29 += l >> 17L;
/*      */     
/*  272 */     l = -20472841L * paramLong37;
/*  273 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  274 */     paramLong19 += l >> 17L;
/*  275 */     l = -117141993L * paramLong37;
/*  276 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  277 */     paramLong20 += l >> 17L;
/*  278 */     l = 62411077L * paramLong37;
/*  279 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  280 */     paramLong21 += l >> 17L;
/*  281 */     l = 56915814L * paramLong37;
/*  282 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  283 */     paramLong22 += l >> 17L;
/*  284 */     l = -97532854L * paramLong37;
/*  285 */     paramLong22 += l << 11L & 0xFFFFFFFL;
/*  286 */     paramLong23 += l >> 17L;
/*  287 */     l = -76509338L * paramLong37;
/*  288 */     paramLong23 += l << 11L & 0xFFFFFFFL;
/*  289 */     paramLong24 += l >> 17L;
/*  290 */     l = 75510783L * paramLong37;
/*  291 */     paramLong24 += l << 11L & 0xFFFFFFFL;
/*  292 */     paramLong25 += l >> 17L;
/*  293 */     l = 67962521L * paramLong37;
/*  294 */     paramLong25 += l << 11L & 0xFFFFFFFL;
/*  295 */     paramLong26 += l >> 17L;
/*  296 */     l = -25593732L * paramLong37;
/*  297 */     paramLong26 += l << 11L & 0xFFFFFFFL;
/*  298 */     paramLong27 += l >> 17L;
/*  299 */     l = 91L * paramLong37;
/*  300 */     paramLong27 += l << 11L & 0xFFFFFFFL;
/*  301 */     paramLong28 += l >> 17L;
/*      */     
/*  303 */     l = -20472841L * paramLong36;
/*  304 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  305 */     paramLong18 += l >> 17L;
/*  306 */     l = -117141993L * paramLong36;
/*  307 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  308 */     paramLong19 += l >> 17L;
/*  309 */     l = 62411077L * paramLong36;
/*  310 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  311 */     paramLong20 += l >> 17L;
/*  312 */     l = 56915814L * paramLong36;
/*  313 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  314 */     paramLong21 += l >> 17L;
/*  315 */     l = -97532854L * paramLong36;
/*  316 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  317 */     paramLong22 += l >> 17L;
/*  318 */     l = -76509338L * paramLong36;
/*  319 */     paramLong22 += l << 11L & 0xFFFFFFFL;
/*  320 */     paramLong23 += l >> 17L;
/*  321 */     l = 75510783L * paramLong36;
/*  322 */     paramLong23 += l << 11L & 0xFFFFFFFL;
/*  323 */     paramLong24 += l >> 17L;
/*  324 */     l = 67962521L * paramLong36;
/*  325 */     paramLong24 += l << 11L & 0xFFFFFFFL;
/*  326 */     paramLong25 += l >> 17L;
/*  327 */     l = -25593732L * paramLong36;
/*  328 */     paramLong25 += l << 11L & 0xFFFFFFFL;
/*  329 */     paramLong26 += l >> 17L;
/*  330 */     l = 91L * paramLong36;
/*  331 */     paramLong26 += l << 11L & 0xFFFFFFFL;
/*  332 */     paramLong27 += l >> 17L;
/*      */     
/*  334 */     l = -20472841L * paramLong35;
/*  335 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  336 */     paramLong17 += l >> 17L;
/*  337 */     l = -117141993L * paramLong35;
/*  338 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  339 */     paramLong18 += l >> 17L;
/*  340 */     l = 62411077L * paramLong35;
/*  341 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  342 */     paramLong19 += l >> 17L;
/*  343 */     l = 56915814L * paramLong35;
/*  344 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  345 */     paramLong20 += l >> 17L;
/*  346 */     l = -97532854L * paramLong35;
/*  347 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  348 */     paramLong21 += l >> 17L;
/*  349 */     l = -76509338L * paramLong35;
/*  350 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  351 */     paramLong22 += l >> 17L;
/*  352 */     l = 75510783L * paramLong35;
/*  353 */     paramLong22 += l << 11L & 0xFFFFFFFL;
/*  354 */     paramLong23 += l >> 17L;
/*  355 */     l = 67962521L * paramLong35;
/*  356 */     paramLong23 += l << 11L & 0xFFFFFFFL;
/*  357 */     paramLong24 += l >> 17L;
/*  358 */     l = -25593732L * paramLong35;
/*  359 */     paramLong24 += l << 11L & 0xFFFFFFFL;
/*  360 */     paramLong25 += l >> 17L;
/*  361 */     l = 91L * paramLong35;
/*  362 */     paramLong25 += l << 11L & 0xFFFFFFFL;
/*  363 */     paramLong26 += l >> 17L;
/*      */     
/*  365 */     l = -20472841L * paramLong34;
/*  366 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  367 */     paramLong16 += l >> 17L;
/*  368 */     l = -117141993L * paramLong34;
/*  369 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  370 */     paramLong17 += l >> 17L;
/*  371 */     l = 62411077L * paramLong34;
/*  372 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  373 */     paramLong18 += l >> 17L;
/*  374 */     l = 56915814L * paramLong34;
/*  375 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  376 */     paramLong19 += l >> 17L;
/*  377 */     l = -97532854L * paramLong34;
/*  378 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  379 */     paramLong20 += l >> 17L;
/*  380 */     l = -76509338L * paramLong34;
/*  381 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  382 */     paramLong21 += l >> 17L;
/*  383 */     l = 75510783L * paramLong34;
/*  384 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  385 */     paramLong22 += l >> 17L;
/*  386 */     l = 67962521L * paramLong34;
/*  387 */     paramLong22 += l << 11L & 0xFFFFFFFL;
/*  388 */     paramLong23 += l >> 17L;
/*  389 */     l = -25593732L * paramLong34;
/*  390 */     paramLong23 += l << 11L & 0xFFFFFFFL;
/*  391 */     paramLong24 += l >> 17L;
/*  392 */     l = 91L * paramLong34;
/*  393 */     paramLong24 += l << 11L & 0xFFFFFFFL;
/*  394 */     paramLong25 += l >> 17L;
/*      */     
/*  396 */     l = -20472841L * paramLong33;
/*  397 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  398 */     paramLong15 += l >> 17L;
/*  399 */     l = -117141993L * paramLong33;
/*  400 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  401 */     paramLong16 += l >> 17L;
/*  402 */     l = 62411077L * paramLong33;
/*  403 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  404 */     paramLong17 += l >> 17L;
/*  405 */     l = 56915814L * paramLong33;
/*  406 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  407 */     paramLong18 += l >> 17L;
/*  408 */     l = -97532854L * paramLong33;
/*  409 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  410 */     paramLong19 += l >> 17L;
/*  411 */     l = -76509338L * paramLong33;
/*  412 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  413 */     paramLong20 += l >> 17L;
/*  414 */     l = 75510783L * paramLong33;
/*  415 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  416 */     paramLong21 += l >> 17L;
/*  417 */     l = 67962521L * paramLong33;
/*  418 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  419 */     paramLong22 += l >> 17L;
/*  420 */     l = -25593732L * paramLong33;
/*  421 */     paramLong22 += l << 11L & 0xFFFFFFFL;
/*  422 */     paramLong23 += l >> 17L;
/*  423 */     l = 91L * paramLong33;
/*  424 */     paramLong23 += l << 11L & 0xFFFFFFFL;
/*  425 */     paramLong24 += l >> 17L;
/*      */     
/*  427 */     l = -20472841L * paramLong32;
/*  428 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  429 */     paramLong14 += l >> 17L;
/*  430 */     l = -117141993L * paramLong32;
/*  431 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  432 */     paramLong15 += l >> 17L;
/*  433 */     l = 62411077L * paramLong32;
/*  434 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  435 */     paramLong16 += l >> 17L;
/*  436 */     l = 56915814L * paramLong32;
/*  437 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  438 */     paramLong17 += l >> 17L;
/*  439 */     l = -97532854L * paramLong32;
/*  440 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  441 */     paramLong18 += l >> 17L;
/*  442 */     l = -76509338L * paramLong32;
/*  443 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  444 */     paramLong19 += l >> 17L;
/*  445 */     l = 75510783L * paramLong32;
/*  446 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  447 */     paramLong20 += l >> 17L;
/*  448 */     l = 67962521L * paramLong32;
/*  449 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  450 */     paramLong21 += l >> 17L;
/*  451 */     l = -25593732L * paramLong32;
/*  452 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  453 */     paramLong22 += l >> 17L;
/*  454 */     l = 91L * paramLong32;
/*  455 */     paramLong22 += l << 11L & 0xFFFFFFFL;
/*  456 */     paramLong23 += l >> 17L;
/*      */     
/*  458 */     l = -20472841L * paramLong31;
/*  459 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  460 */     paramLong13 += l >> 17L;
/*  461 */     l = -117141993L * paramLong31;
/*  462 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  463 */     paramLong14 += l >> 17L;
/*  464 */     l = 62411077L * paramLong31;
/*  465 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  466 */     paramLong15 += l >> 17L;
/*  467 */     l = 56915814L * paramLong31;
/*  468 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  469 */     paramLong16 += l >> 17L;
/*  470 */     l = -97532854L * paramLong31;
/*  471 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  472 */     paramLong17 += l >> 17L;
/*  473 */     l = -76509338L * paramLong31;
/*  474 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  475 */     paramLong18 += l >> 17L;
/*  476 */     l = 75510783L * paramLong31;
/*  477 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  478 */     paramLong19 += l >> 17L;
/*  479 */     l = 67962521L * paramLong31;
/*  480 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  481 */     paramLong20 += l >> 17L;
/*  482 */     l = -25593732L * paramLong31;
/*  483 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  484 */     paramLong21 += l >> 17L;
/*  485 */     l = 91L * paramLong31;
/*  486 */     paramLong21 += l << 11L & 0xFFFFFFFL;
/*  487 */     paramLong22 += l >> 17L;
/*      */     
/*  489 */     l = -20472841L * paramLong30;
/*  490 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  491 */     paramLong12 += l >> 17L;
/*  492 */     l = -117141993L * paramLong30;
/*  493 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  494 */     paramLong13 += l >> 17L;
/*  495 */     l = 62411077L * paramLong30;
/*  496 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  497 */     paramLong14 += l >> 17L;
/*  498 */     l = 56915814L * paramLong30;
/*  499 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  500 */     paramLong15 += l >> 17L;
/*  501 */     l = -97532854L * paramLong30;
/*  502 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  503 */     paramLong16 += l >> 17L;
/*  504 */     l = -76509338L * paramLong30;
/*  505 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  506 */     paramLong17 += l >> 17L;
/*  507 */     l = 75510783L * paramLong30;
/*  508 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  509 */     paramLong18 += l >> 17L;
/*  510 */     l = 67962521L * paramLong30;
/*  511 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  512 */     paramLong19 += l >> 17L;
/*  513 */     l = -25593732L * paramLong30;
/*  514 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  515 */     paramLong20 += l >> 17L;
/*  516 */     l = 91L * paramLong30;
/*  517 */     paramLong20 += l << 11L & 0xFFFFFFFL;
/*  518 */     paramLong21 += l >> 17L;
/*      */     
/*  520 */     l = -20472841L * paramLong29;
/*  521 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  522 */     paramLong11 += l >> 17L;
/*  523 */     l = -117141993L * paramLong29;
/*  524 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  525 */     paramLong12 += l >> 17L;
/*  526 */     l = 62411077L * paramLong29;
/*  527 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  528 */     paramLong13 += l >> 17L;
/*  529 */     l = 56915814L * paramLong29;
/*  530 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  531 */     paramLong14 += l >> 17L;
/*  532 */     l = -97532854L * paramLong29;
/*  533 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  534 */     paramLong15 += l >> 17L;
/*  535 */     l = -76509338L * paramLong29;
/*  536 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  537 */     paramLong16 += l >> 17L;
/*  538 */     l = 75510783L * paramLong29;
/*  539 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  540 */     paramLong17 += l >> 17L;
/*  541 */     l = 67962521L * paramLong29;
/*  542 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  543 */     paramLong18 += l >> 17L;
/*  544 */     l = -25593732L * paramLong29;
/*  545 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  546 */     paramLong19 += l >> 17L;
/*  547 */     l = 91L * paramLong29;
/*  548 */     paramLong19 += l << 11L & 0xFFFFFFFL;
/*  549 */     paramLong20 += l >> 17L;
/*      */     
/*  551 */     carryReduce1(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, paramLong28, paramLong29, paramLong30, paramLong31, paramLong32, paramLong33, paramLong34, paramLong35, paramLong36, paramLong37, paramLong38);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void carryReduce1(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31, long paramLong32, long paramLong33, long paramLong34, long paramLong35, long paramLong36, long paramLong37, long paramLong38) {
/*  557 */     long l = paramLong20 + 134217728L >> 28L;
/*  558 */     paramLong20 -= l << 28L;
/*  559 */     paramLong21 += l;
/*      */     
/*  561 */     l = paramLong21 + 134217728L >> 28L;
/*  562 */     paramLong21 -= l << 28L;
/*  563 */     paramLong22 += l;
/*      */     
/*  565 */     l = paramLong22 + 134217728L >> 28L;
/*  566 */     paramLong22 -= l << 28L;
/*  567 */     paramLong23 += l;
/*      */     
/*  569 */     l = paramLong23 + 134217728L >> 28L;
/*  570 */     paramLong23 -= l << 28L;
/*  571 */     paramLong24 += l;
/*      */     
/*  573 */     l = paramLong24 + 134217728L >> 28L;
/*  574 */     paramLong24 -= l << 28L;
/*  575 */     paramLong25 += l;
/*      */     
/*  577 */     l = paramLong25 + 134217728L >> 28L;
/*  578 */     paramLong25 -= l << 28L;
/*  579 */     paramLong26 += l;
/*      */     
/*  581 */     l = paramLong26 + 134217728L >> 28L;
/*  582 */     paramLong26 -= l << 28L;
/*  583 */     paramLong27 += l;
/*      */     
/*  585 */     l = paramLong27 + 134217728L >> 28L;
/*  586 */     paramLong27 -= l << 28L;
/*  587 */     paramLong28 += l;
/*      */     
/*  589 */     l = -20472841L * paramLong28;
/*  590 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  591 */     paramLong10 += l >> 17L;
/*  592 */     l = -117141993L * paramLong28;
/*  593 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  594 */     paramLong11 += l >> 17L;
/*  595 */     l = 62411077L * paramLong28;
/*  596 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  597 */     paramLong12 += l >> 17L;
/*  598 */     l = 56915814L * paramLong28;
/*  599 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  600 */     paramLong13 += l >> 17L;
/*  601 */     l = -97532854L * paramLong28;
/*  602 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  603 */     paramLong14 += l >> 17L;
/*  604 */     l = -76509338L * paramLong28;
/*  605 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  606 */     paramLong15 += l >> 17L;
/*  607 */     l = 75510783L * paramLong28;
/*  608 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  609 */     paramLong16 += l >> 17L;
/*  610 */     l = 67962521L * paramLong28;
/*  611 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  612 */     paramLong17 += l >> 17L;
/*  613 */     l = -25593732L * paramLong28;
/*  614 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  615 */     paramLong18 += l >> 17L;
/*  616 */     l = 91L * paramLong28;
/*  617 */     paramLong18 += l << 11L & 0xFFFFFFFL;
/*  618 */     paramLong19 += l >> 17L;
/*      */     
/*  620 */     l = -20472841L * paramLong27;
/*  621 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  622 */     paramLong9 += l >> 17L;
/*  623 */     l = -117141993L * paramLong27;
/*  624 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  625 */     paramLong10 += l >> 17L;
/*  626 */     l = 62411077L * paramLong27;
/*  627 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  628 */     paramLong11 += l >> 17L;
/*  629 */     l = 56915814L * paramLong27;
/*  630 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  631 */     paramLong12 += l >> 17L;
/*  632 */     l = -97532854L * paramLong27;
/*  633 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  634 */     paramLong13 += l >> 17L;
/*  635 */     l = -76509338L * paramLong27;
/*  636 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  637 */     paramLong14 += l >> 17L;
/*  638 */     l = 75510783L * paramLong27;
/*  639 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  640 */     paramLong15 += l >> 17L;
/*  641 */     l = 67962521L * paramLong27;
/*  642 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  643 */     paramLong16 += l >> 17L;
/*  644 */     l = -25593732L * paramLong27;
/*  645 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  646 */     paramLong17 += l >> 17L;
/*  647 */     l = 91L * paramLong27;
/*  648 */     paramLong17 += l << 11L & 0xFFFFFFFL;
/*  649 */     paramLong18 += l >> 17L;
/*      */     
/*  651 */     l = -20472841L * paramLong26;
/*  652 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  653 */     paramLong8 += l >> 17L;
/*  654 */     l = -117141993L * paramLong26;
/*  655 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  656 */     paramLong9 += l >> 17L;
/*  657 */     l = 62411077L * paramLong26;
/*  658 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  659 */     paramLong10 += l >> 17L;
/*  660 */     l = 56915814L * paramLong26;
/*  661 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  662 */     paramLong11 += l >> 17L;
/*  663 */     l = -97532854L * paramLong26;
/*  664 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  665 */     paramLong12 += l >> 17L;
/*  666 */     l = -76509338L * paramLong26;
/*  667 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  668 */     paramLong13 += l >> 17L;
/*  669 */     l = 75510783L * paramLong26;
/*  670 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  671 */     paramLong14 += l >> 17L;
/*  672 */     l = 67962521L * paramLong26;
/*  673 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  674 */     paramLong15 += l >> 17L;
/*  675 */     l = -25593732L * paramLong26;
/*  676 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  677 */     paramLong16 += l >> 17L;
/*  678 */     l = 91L * paramLong26;
/*  679 */     paramLong16 += l << 11L & 0xFFFFFFFL;
/*  680 */     paramLong17 += l >> 17L;
/*      */     
/*  682 */     l = -20472841L * paramLong25;
/*  683 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/*  684 */     paramLong7 += l >> 17L;
/*  685 */     l = -117141993L * paramLong25;
/*  686 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  687 */     paramLong8 += l >> 17L;
/*  688 */     l = 62411077L * paramLong25;
/*  689 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  690 */     paramLong9 += l >> 17L;
/*  691 */     l = 56915814L * paramLong25;
/*  692 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  693 */     paramLong10 += l >> 17L;
/*  694 */     l = -97532854L * paramLong25;
/*  695 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  696 */     paramLong11 += l >> 17L;
/*  697 */     l = -76509338L * paramLong25;
/*  698 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  699 */     paramLong12 += l >> 17L;
/*  700 */     l = 75510783L * paramLong25;
/*  701 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  702 */     paramLong13 += l >> 17L;
/*  703 */     l = 67962521L * paramLong25;
/*  704 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  705 */     paramLong14 += l >> 17L;
/*  706 */     l = -25593732L * paramLong25;
/*  707 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  708 */     paramLong15 += l >> 17L;
/*  709 */     l = 91L * paramLong25;
/*  710 */     paramLong15 += l << 11L & 0xFFFFFFFL;
/*  711 */     paramLong16 += l >> 17L;
/*      */     
/*  713 */     l = -20472841L * paramLong24;
/*  714 */     paramLong5 += l << 11L & 0xFFFFFFFL;
/*  715 */     paramLong6 += l >> 17L;
/*  716 */     l = -117141993L * paramLong24;
/*  717 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/*  718 */     paramLong7 += l >> 17L;
/*  719 */     l = 62411077L * paramLong24;
/*  720 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  721 */     paramLong8 += l >> 17L;
/*  722 */     l = 56915814L * paramLong24;
/*  723 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  724 */     paramLong9 += l >> 17L;
/*  725 */     l = -97532854L * paramLong24;
/*  726 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  727 */     paramLong10 += l >> 17L;
/*  728 */     l = -76509338L * paramLong24;
/*  729 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  730 */     paramLong11 += l >> 17L;
/*  731 */     l = 75510783L * paramLong24;
/*  732 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  733 */     paramLong12 += l >> 17L;
/*  734 */     l = 67962521L * paramLong24;
/*  735 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  736 */     paramLong13 += l >> 17L;
/*  737 */     l = -25593732L * paramLong24;
/*  738 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  739 */     paramLong14 += l >> 17L;
/*  740 */     l = 91L * paramLong24;
/*  741 */     paramLong14 += l << 11L & 0xFFFFFFFL;
/*  742 */     paramLong15 += l >> 17L;
/*      */     
/*  744 */     l = -20472841L * paramLong23;
/*  745 */     paramLong4 += l << 11L & 0xFFFFFFFL;
/*  746 */     paramLong5 += l >> 17L;
/*  747 */     l = -117141993L * paramLong23;
/*  748 */     paramLong5 += l << 11L & 0xFFFFFFFL;
/*  749 */     paramLong6 += l >> 17L;
/*  750 */     l = 62411077L * paramLong23;
/*  751 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/*  752 */     paramLong7 += l >> 17L;
/*  753 */     l = 56915814L * paramLong23;
/*  754 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  755 */     paramLong8 += l >> 17L;
/*  756 */     l = -97532854L * paramLong23;
/*  757 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  758 */     paramLong9 += l >> 17L;
/*  759 */     l = -76509338L * paramLong23;
/*  760 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  761 */     paramLong10 += l >> 17L;
/*  762 */     l = 75510783L * paramLong23;
/*  763 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  764 */     paramLong11 += l >> 17L;
/*  765 */     l = 67962521L * paramLong23;
/*  766 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  767 */     paramLong12 += l >> 17L;
/*  768 */     l = -25593732L * paramLong23;
/*  769 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  770 */     paramLong13 += l >> 17L;
/*  771 */     l = 91L * paramLong23;
/*  772 */     paramLong13 += l << 11L & 0xFFFFFFFL;
/*  773 */     paramLong14 += l >> 17L;
/*      */     
/*  775 */     l = -20472841L * paramLong22;
/*  776 */     paramLong3 += l << 11L & 0xFFFFFFFL;
/*  777 */     paramLong4 += l >> 17L;
/*  778 */     l = -117141993L * paramLong22;
/*  779 */     paramLong4 += l << 11L & 0xFFFFFFFL;
/*  780 */     paramLong5 += l >> 17L;
/*  781 */     l = 62411077L * paramLong22;
/*  782 */     paramLong5 += l << 11L & 0xFFFFFFFL;
/*  783 */     paramLong6 += l >> 17L;
/*  784 */     l = 56915814L * paramLong22;
/*  785 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/*  786 */     paramLong7 += l >> 17L;
/*  787 */     l = -97532854L * paramLong22;
/*  788 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  789 */     paramLong8 += l >> 17L;
/*  790 */     l = -76509338L * paramLong22;
/*  791 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  792 */     paramLong9 += l >> 17L;
/*  793 */     l = 75510783L * paramLong22;
/*  794 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  795 */     paramLong10 += l >> 17L;
/*  796 */     l = 67962521L * paramLong22;
/*  797 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  798 */     paramLong11 += l >> 17L;
/*  799 */     l = -25593732L * paramLong22;
/*  800 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  801 */     paramLong12 += l >> 17L;
/*  802 */     l = 91L * paramLong22;
/*  803 */     paramLong12 += l << 11L & 0xFFFFFFFL;
/*  804 */     paramLong13 += l >> 17L;
/*      */     
/*  806 */     l = -20472841L * paramLong21;
/*  807 */     paramLong2 += l << 11L & 0xFFFFFFFL;
/*  808 */     paramLong3 += l >> 17L;
/*  809 */     l = -117141993L * paramLong21;
/*  810 */     paramLong3 += l << 11L & 0xFFFFFFFL;
/*  811 */     paramLong4 += l >> 17L;
/*  812 */     l = 62411077L * paramLong21;
/*  813 */     paramLong4 += l << 11L & 0xFFFFFFFL;
/*  814 */     paramLong5 += l >> 17L;
/*  815 */     l = 56915814L * paramLong21;
/*  816 */     paramLong5 += l << 11L & 0xFFFFFFFL;
/*  817 */     paramLong6 += l >> 17L;
/*  818 */     l = -97532854L * paramLong21;
/*  819 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/*  820 */     paramLong7 += l >> 17L;
/*  821 */     l = -76509338L * paramLong21;
/*  822 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  823 */     paramLong8 += l >> 17L;
/*  824 */     l = 75510783L * paramLong21;
/*  825 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  826 */     paramLong9 += l >> 17L;
/*  827 */     l = 67962521L * paramLong21;
/*  828 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  829 */     paramLong10 += l >> 17L;
/*  830 */     l = -25593732L * paramLong21;
/*  831 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  832 */     paramLong11 += l >> 17L;
/*  833 */     l = 91L * paramLong21;
/*  834 */     paramLong11 += l << 11L & 0xFFFFFFFL;
/*  835 */     paramLong12 += l >> 17L;
/*      */     
/*  837 */     l = -20472841L * paramLong20;
/*  838 */     paramLong1 += l << 11L & 0xFFFFFFFL;
/*  839 */     paramLong2 += l >> 17L;
/*  840 */     l = -117141993L * paramLong20;
/*  841 */     paramLong2 += l << 11L & 0xFFFFFFFL;
/*  842 */     paramLong3 += l >> 17L;
/*  843 */     l = 62411077L * paramLong20;
/*  844 */     paramLong3 += l << 11L & 0xFFFFFFFL;
/*  845 */     paramLong4 += l >> 17L;
/*  846 */     l = 56915814L * paramLong20;
/*  847 */     paramLong4 += l << 11L & 0xFFFFFFFL;
/*  848 */     paramLong5 += l >> 17L;
/*  849 */     l = -97532854L * paramLong20;
/*  850 */     paramLong5 += l << 11L & 0xFFFFFFFL;
/*  851 */     paramLong6 += l >> 17L;
/*  852 */     l = -76509338L * paramLong20;
/*  853 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/*  854 */     paramLong7 += l >> 17L;
/*  855 */     l = 75510783L * paramLong20;
/*  856 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  857 */     paramLong8 += l >> 17L;
/*  858 */     l = 67962521L * paramLong20;
/*  859 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  860 */     paramLong9 += l >> 17L;
/*  861 */     l = -25593732L * paramLong20;
/*  862 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  863 */     paramLong10 += l >> 17L;
/*  864 */     l = 91L * paramLong20;
/*  865 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  866 */     paramLong11 += l >> 17L;
/*  867 */     paramLong20 = 0L;
/*      */     
/*  869 */     carryReduce2(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, paramLong28, paramLong29, paramLong30, paramLong31, paramLong32, paramLong33, paramLong34, paramLong35, paramLong36, paramLong37, paramLong38);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void carryReduce2(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31, long paramLong32, long paramLong33, long paramLong34, long paramLong35, long paramLong36, long paramLong37, long paramLong38) {
/*  875 */     long l = paramLong1 + 134217728L >> 28L;
/*  876 */     paramLong1 -= l << 28L;
/*  877 */     paramLong2 += l;
/*      */     
/*  879 */     l = paramLong2 + 134217728L >> 28L;
/*  880 */     paramLong2 -= l << 28L;
/*  881 */     paramLong3 += l;
/*      */     
/*  883 */     l = paramLong3 + 134217728L >> 28L;
/*  884 */     paramLong3 -= l << 28L;
/*  885 */     paramLong4 += l;
/*      */     
/*  887 */     l = paramLong4 + 134217728L >> 28L;
/*  888 */     paramLong4 -= l << 28L;
/*  889 */     paramLong5 += l;
/*      */     
/*  891 */     l = paramLong5 + 134217728L >> 28L;
/*  892 */     paramLong5 -= l << 28L;
/*  893 */     paramLong6 += l;
/*      */     
/*  895 */     l = paramLong6 + 134217728L >> 28L;
/*  896 */     paramLong6 -= l << 28L;
/*  897 */     paramLong7 += l;
/*      */     
/*  899 */     l = paramLong7 + 134217728L >> 28L;
/*  900 */     paramLong7 -= l << 28L;
/*  901 */     paramLong8 += l;
/*      */     
/*  903 */     l = paramLong8 + 134217728L >> 28L;
/*  904 */     paramLong8 -= l << 28L;
/*  905 */     paramLong9 += l;
/*      */     
/*  907 */     l = paramLong9 + 134217728L >> 28L;
/*  908 */     paramLong9 -= l << 28L;
/*  909 */     paramLong10 += l;
/*      */     
/*  911 */     l = paramLong10 + 134217728L >> 28L;
/*  912 */     paramLong10 -= l << 28L;
/*  913 */     paramLong11 += l;
/*      */     
/*  915 */     l = paramLong11 + 134217728L >> 28L;
/*  916 */     paramLong11 -= l << 28L;
/*  917 */     paramLong12 += l;
/*      */     
/*  919 */     l = paramLong12 + 134217728L >> 28L;
/*  920 */     paramLong12 -= l << 28L;
/*  921 */     paramLong13 += l;
/*      */     
/*  923 */     l = paramLong13 + 134217728L >> 28L;
/*  924 */     paramLong13 -= l << 28L;
/*  925 */     paramLong14 += l;
/*      */     
/*  927 */     l = paramLong14 + 134217728L >> 28L;
/*  928 */     paramLong14 -= l << 28L;
/*  929 */     paramLong15 += l;
/*      */     
/*  931 */     l = paramLong15 + 134217728L >> 28L;
/*  932 */     paramLong15 -= l << 28L;
/*  933 */     paramLong16 += l;
/*      */     
/*  935 */     l = paramLong16 + 134217728L >> 28L;
/*  936 */     paramLong16 -= l << 28L;
/*  937 */     paramLong17 += l;
/*      */     
/*  939 */     l = paramLong17 + 134217728L >> 28L;
/*  940 */     paramLong17 -= l << 28L;
/*  941 */     paramLong18 += l;
/*      */     
/*  943 */     l = paramLong18 + 134217728L >> 28L;
/*  944 */     paramLong18 -= l << 28L;
/*  945 */     paramLong19 += l;
/*      */     
/*  947 */     l = paramLong19 + 134217728L >> 28L;
/*  948 */     paramLong19 -= l << 28L;
/*  949 */     paramLong20 += l;
/*      */     
/*  951 */     carryReduce3(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, paramLong20, paramLong21, paramLong22, paramLong23, paramLong24, paramLong25, paramLong26, paramLong27, paramLong28, paramLong29, paramLong30, paramLong31, paramLong32, paramLong33, paramLong34, paramLong35, paramLong36, paramLong37, paramLong38);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void carryReduce3(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20, long paramLong21, long paramLong22, long paramLong23, long paramLong24, long paramLong25, long paramLong26, long paramLong27, long paramLong28, long paramLong29, long paramLong30, long paramLong31, long paramLong32, long paramLong33, long paramLong34, long paramLong35, long paramLong36, long paramLong37, long paramLong38) {
/*  957 */     long l = -20472841L * paramLong20;
/*  958 */     paramLong1 += l << 11L & 0xFFFFFFFL;
/*  959 */     paramLong2 += l >> 17L;
/*  960 */     l = -117141993L * paramLong20;
/*  961 */     paramLong2 += l << 11L & 0xFFFFFFFL;
/*  962 */     paramLong3 += l >> 17L;
/*  963 */     l = 62411077L * paramLong20;
/*  964 */     paramLong3 += l << 11L & 0xFFFFFFFL;
/*  965 */     paramLong4 += l >> 17L;
/*  966 */     l = 56915814L * paramLong20;
/*  967 */     paramLong4 += l << 11L & 0xFFFFFFFL;
/*  968 */     paramLong5 += l >> 17L;
/*  969 */     l = -97532854L * paramLong20;
/*  970 */     paramLong5 += l << 11L & 0xFFFFFFFL;
/*  971 */     paramLong6 += l >> 17L;
/*  972 */     l = -76509338L * paramLong20;
/*  973 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/*  974 */     paramLong7 += l >> 17L;
/*  975 */     l = 75510783L * paramLong20;
/*  976 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/*  977 */     paramLong8 += l >> 17L;
/*  978 */     l = 67962521L * paramLong20;
/*  979 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/*  980 */     paramLong9 += l >> 17L;
/*  981 */     l = -25593732L * paramLong20;
/*  982 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/*  983 */     paramLong10 += l >> 17L;
/*  984 */     l = 91L * paramLong20;
/*  985 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/*  986 */     paramLong11 += l >> 17L;
/*      */     
/*  988 */     l = paramLong1 + 134217728L >> 28L;
/*  989 */     paramLong1 -= l << 28L;
/*  990 */     paramLong2 += l;
/*      */     
/*  992 */     l = paramLong2 + 134217728L >> 28L;
/*  993 */     paramLong2 -= l << 28L;
/*  994 */     paramLong3 += l;
/*      */     
/*  996 */     l = paramLong3 + 134217728L >> 28L;
/*  997 */     paramLong3 -= l << 28L;
/*  998 */     paramLong4 += l;
/*      */     
/* 1000 */     l = paramLong4 + 134217728L >> 28L;
/* 1001 */     paramLong4 -= l << 28L;
/* 1002 */     paramLong5 += l;
/*      */     
/* 1004 */     l = paramLong5 + 134217728L >> 28L;
/* 1005 */     paramLong5 -= l << 28L;
/* 1006 */     paramLong6 += l;
/*      */     
/* 1008 */     l = paramLong6 + 134217728L >> 28L;
/* 1009 */     paramLong6 -= l << 28L;
/* 1010 */     paramLong7 += l;
/*      */     
/* 1012 */     l = paramLong7 + 134217728L >> 28L;
/* 1013 */     paramLong7 -= l << 28L;
/* 1014 */     paramLong8 += l;
/*      */     
/* 1016 */     l = paramLong8 + 134217728L >> 28L;
/* 1017 */     paramLong8 -= l << 28L;
/* 1018 */     paramLong9 += l;
/*      */     
/* 1020 */     l = paramLong9 + 134217728L >> 28L;
/* 1021 */     paramLong9 -= l << 28L;
/* 1022 */     paramLong10 += l;
/*      */     
/* 1024 */     l = paramLong10 + 134217728L >> 28L;
/* 1025 */     paramLong10 -= l << 28L;
/* 1026 */     paramLong11 += l;
/*      */     
/* 1028 */     l = paramLong11 + 134217728L >> 28L;
/* 1029 */     paramLong11 -= l << 28L;
/* 1030 */     paramLong12 += l;
/*      */     
/* 1032 */     l = paramLong12 + 134217728L >> 28L;
/* 1033 */     paramLong12 -= l << 28L;
/* 1034 */     paramLong13 += l;
/*      */     
/* 1036 */     l = paramLong13 + 134217728L >> 28L;
/* 1037 */     paramLong13 -= l << 28L;
/* 1038 */     paramLong14 += l;
/*      */     
/* 1040 */     l = paramLong14 + 134217728L >> 28L;
/* 1041 */     paramLong14 -= l << 28L;
/* 1042 */     paramLong15 += l;
/*      */     
/* 1044 */     l = paramLong15 + 134217728L >> 28L;
/* 1045 */     paramLong15 -= l << 28L;
/* 1046 */     paramLong16 += l;
/*      */     
/* 1048 */     l = paramLong16 + 134217728L >> 28L;
/* 1049 */     paramLong16 -= l << 28L;
/* 1050 */     paramLong17 += l;
/*      */     
/* 1052 */     l = paramLong17 + 134217728L >> 28L;
/* 1053 */     paramLong17 -= l << 28L;
/* 1054 */     paramLong18 += l;
/*      */     
/* 1056 */     l = paramLong18 + 134217728L >> 28L;
/* 1057 */     paramLong18 -= l << 28L;
/* 1058 */     paramLong19 += l;
/*      */     
/* 1060 */     paramArrayOflong[0] = paramLong1;
/* 1061 */     paramArrayOflong[1] = paramLong2;
/* 1062 */     paramArrayOflong[2] = paramLong3;
/* 1063 */     paramArrayOflong[3] = paramLong4;
/* 1064 */     paramArrayOflong[4] = paramLong5;
/* 1065 */     paramArrayOflong[5] = paramLong6;
/* 1066 */     paramArrayOflong[6] = paramLong7;
/* 1067 */     paramArrayOflong[7] = paramLong8;
/* 1068 */     paramArrayOflong[8] = paramLong9;
/* 1069 */     paramArrayOflong[9] = paramLong10;
/* 1070 */     paramArrayOflong[10] = paramLong11;
/* 1071 */     paramArrayOflong[11] = paramLong12;
/* 1072 */     paramArrayOflong[12] = paramLong13;
/* 1073 */     paramArrayOflong[13] = paramLong14;
/* 1074 */     paramArrayOflong[14] = paramLong15;
/* 1075 */     paramArrayOflong[15] = paramLong16;
/* 1076 */     paramArrayOflong[16] = paramLong17;
/* 1077 */     paramArrayOflong[17] = paramLong18;
/* 1078 */     paramArrayOflong[18] = paramLong19;
/*      */   }
/*      */   private void carryReduce(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19) {
/* 1081 */     long l1 = 0L;
/*      */     
/* 1083 */     long l2 = paramLong1 + 134217728L >> 28L;
/* 1084 */     paramLong1 -= l2 << 28L;
/* 1085 */     paramLong2 += l2;
/*      */     
/* 1087 */     l2 = paramLong2 + 134217728L >> 28L;
/* 1088 */     paramLong2 -= l2 << 28L;
/* 1089 */     paramLong3 += l2;
/*      */     
/* 1091 */     l2 = paramLong3 + 134217728L >> 28L;
/* 1092 */     paramLong3 -= l2 << 28L;
/* 1093 */     paramLong4 += l2;
/*      */     
/* 1095 */     l2 = paramLong4 + 134217728L >> 28L;
/* 1096 */     paramLong4 -= l2 << 28L;
/* 1097 */     paramLong5 += l2;
/*      */     
/* 1099 */     l2 = paramLong5 + 134217728L >> 28L;
/* 1100 */     paramLong5 -= l2 << 28L;
/* 1101 */     paramLong6 += l2;
/*      */     
/* 1103 */     l2 = paramLong6 + 134217728L >> 28L;
/* 1104 */     paramLong6 -= l2 << 28L;
/* 1105 */     paramLong7 += l2;
/*      */     
/* 1107 */     l2 = paramLong7 + 134217728L >> 28L;
/* 1108 */     paramLong7 -= l2 << 28L;
/* 1109 */     paramLong8 += l2;
/*      */     
/* 1111 */     l2 = paramLong8 + 134217728L >> 28L;
/* 1112 */     paramLong8 -= l2 << 28L;
/* 1113 */     paramLong9 += l2;
/*      */     
/* 1115 */     l2 = paramLong9 + 134217728L >> 28L;
/* 1116 */     paramLong9 -= l2 << 28L;
/* 1117 */     paramLong10 += l2;
/*      */     
/* 1119 */     l2 = paramLong10 + 134217728L >> 28L;
/* 1120 */     paramLong10 -= l2 << 28L;
/* 1121 */     paramLong11 += l2;
/*      */     
/* 1123 */     l2 = paramLong11 + 134217728L >> 28L;
/* 1124 */     paramLong11 -= l2 << 28L;
/* 1125 */     paramLong12 += l2;
/*      */     
/* 1127 */     l2 = paramLong12 + 134217728L >> 28L;
/* 1128 */     paramLong12 -= l2 << 28L;
/* 1129 */     paramLong13 += l2;
/*      */     
/* 1131 */     l2 = paramLong13 + 134217728L >> 28L;
/* 1132 */     paramLong13 -= l2 << 28L;
/* 1133 */     paramLong14 += l2;
/*      */     
/* 1135 */     l2 = paramLong14 + 134217728L >> 28L;
/* 1136 */     paramLong14 -= l2 << 28L;
/* 1137 */     paramLong15 += l2;
/*      */     
/* 1139 */     l2 = paramLong15 + 134217728L >> 28L;
/* 1140 */     paramLong15 -= l2 << 28L;
/* 1141 */     paramLong16 += l2;
/*      */     
/* 1143 */     l2 = paramLong16 + 134217728L >> 28L;
/* 1144 */     paramLong16 -= l2 << 28L;
/* 1145 */     paramLong17 += l2;
/*      */     
/* 1147 */     l2 = paramLong17 + 134217728L >> 28L;
/* 1148 */     paramLong17 -= l2 << 28L;
/* 1149 */     paramLong18 += l2;
/*      */     
/* 1151 */     l2 = paramLong18 + 134217728L >> 28L;
/* 1152 */     paramLong18 -= l2 << 28L;
/* 1153 */     paramLong19 += l2;
/*      */     
/* 1155 */     l2 = paramLong19 + 134217728L >> 28L;
/* 1156 */     paramLong19 -= l2 << 28L;
/* 1157 */     l1 += l2;
/*      */     
/* 1159 */     carryReduce0(paramArrayOflong, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7, paramLong8, paramLong9, paramLong10, paramLong11, paramLong12, paramLong13, paramLong14, paramLong15, paramLong16, paramLong17, paramLong18, paramLong19, l1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void carryReduce0(long[] paramArrayOflong, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, long paramLong15, long paramLong16, long paramLong17, long paramLong18, long paramLong19, long paramLong20) {
/* 1165 */     long l = -20472841L * paramLong20;
/* 1166 */     paramLong1 += l << 11L & 0xFFFFFFFL;
/* 1167 */     paramLong2 += l >> 17L;
/* 1168 */     l = -117141993L * paramLong20;
/* 1169 */     paramLong2 += l << 11L & 0xFFFFFFFL;
/* 1170 */     paramLong3 += l >> 17L;
/* 1171 */     l = 62411077L * paramLong20;
/* 1172 */     paramLong3 += l << 11L & 0xFFFFFFFL;
/* 1173 */     paramLong4 += l >> 17L;
/* 1174 */     l = 56915814L * paramLong20;
/* 1175 */     paramLong4 += l << 11L & 0xFFFFFFFL;
/* 1176 */     paramLong5 += l >> 17L;
/* 1177 */     l = -97532854L * paramLong20;
/* 1178 */     paramLong5 += l << 11L & 0xFFFFFFFL;
/* 1179 */     paramLong6 += l >> 17L;
/* 1180 */     l = -76509338L * paramLong20;
/* 1181 */     paramLong6 += l << 11L & 0xFFFFFFFL;
/* 1182 */     paramLong7 += l >> 17L;
/* 1183 */     l = 75510783L * paramLong20;
/* 1184 */     paramLong7 += l << 11L & 0xFFFFFFFL;
/* 1185 */     paramLong8 += l >> 17L;
/* 1186 */     l = 67962521L * paramLong20;
/* 1187 */     paramLong8 += l << 11L & 0xFFFFFFFL;
/* 1188 */     paramLong9 += l >> 17L;
/* 1189 */     l = -25593732L * paramLong20;
/* 1190 */     paramLong9 += l << 11L & 0xFFFFFFFL;
/* 1191 */     paramLong10 += l >> 17L;
/* 1192 */     l = 91L * paramLong20;
/* 1193 */     paramLong10 += l << 11L & 0xFFFFFFFL;
/* 1194 */     paramLong11 += l >> 17L;
/*      */     
/* 1196 */     l = paramLong1 + 134217728L >> 28L;
/* 1197 */     paramLong1 -= l << 28L;
/* 1198 */     paramLong2 += l;
/*      */     
/* 1200 */     l = paramLong2 + 134217728L >> 28L;
/* 1201 */     paramLong2 -= l << 28L;
/* 1202 */     paramLong3 += l;
/*      */     
/* 1204 */     l = paramLong3 + 134217728L >> 28L;
/* 1205 */     paramLong3 -= l << 28L;
/* 1206 */     paramLong4 += l;
/*      */     
/* 1208 */     l = paramLong4 + 134217728L >> 28L;
/* 1209 */     paramLong4 -= l << 28L;
/* 1210 */     paramLong5 += l;
/*      */     
/* 1212 */     l = paramLong5 + 134217728L >> 28L;
/* 1213 */     paramLong5 -= l << 28L;
/* 1214 */     paramLong6 += l;
/*      */     
/* 1216 */     l = paramLong6 + 134217728L >> 28L;
/* 1217 */     paramLong6 -= l << 28L;
/* 1218 */     paramLong7 += l;
/*      */     
/* 1220 */     l = paramLong7 + 134217728L >> 28L;
/* 1221 */     paramLong7 -= l << 28L;
/* 1222 */     paramLong8 += l;
/*      */     
/* 1224 */     l = paramLong8 + 134217728L >> 28L;
/* 1225 */     paramLong8 -= l << 28L;
/* 1226 */     paramLong9 += l;
/*      */     
/* 1228 */     l = paramLong9 + 134217728L >> 28L;
/* 1229 */     paramLong9 -= l << 28L;
/* 1230 */     paramLong10 += l;
/*      */     
/* 1232 */     l = paramLong10 + 134217728L >> 28L;
/* 1233 */     paramLong10 -= l << 28L;
/* 1234 */     paramLong11 += l;
/*      */     
/* 1236 */     l = paramLong11 + 134217728L >> 28L;
/* 1237 */     paramLong11 -= l << 28L;
/* 1238 */     paramLong12 += l;
/*      */     
/* 1240 */     l = paramLong12 + 134217728L >> 28L;
/* 1241 */     paramLong12 -= l << 28L;
/* 1242 */     paramLong13 += l;
/*      */     
/* 1244 */     l = paramLong13 + 134217728L >> 28L;
/* 1245 */     paramLong13 -= l << 28L;
/* 1246 */     paramLong14 += l;
/*      */     
/* 1248 */     l = paramLong14 + 134217728L >> 28L;
/* 1249 */     paramLong14 -= l << 28L;
/* 1250 */     paramLong15 += l;
/*      */     
/* 1252 */     l = paramLong15 + 134217728L >> 28L;
/* 1253 */     paramLong15 -= l << 28L;
/* 1254 */     paramLong16 += l;
/*      */     
/* 1256 */     l = paramLong16 + 134217728L >> 28L;
/* 1257 */     paramLong16 -= l << 28L;
/* 1258 */     paramLong17 += l;
/*      */     
/* 1260 */     l = paramLong17 + 134217728L >> 28L;
/* 1261 */     paramLong17 -= l << 28L;
/* 1262 */     paramLong18 += l;
/*      */     
/* 1264 */     l = paramLong18 + 134217728L >> 28L;
/* 1265 */     paramLong18 -= l << 28L;
/* 1266 */     paramLong19 += l;
/*      */     
/* 1268 */     paramArrayOflong[0] = paramLong1;
/* 1269 */     paramArrayOflong[1] = paramLong2;
/* 1270 */     paramArrayOflong[2] = paramLong3;
/* 1271 */     paramArrayOflong[3] = paramLong4;
/* 1272 */     paramArrayOflong[4] = paramLong5;
/* 1273 */     paramArrayOflong[5] = paramLong6;
/* 1274 */     paramArrayOflong[6] = paramLong7;
/* 1275 */     paramArrayOflong[7] = paramLong8;
/* 1276 */     paramArrayOflong[8] = paramLong9;
/* 1277 */     paramArrayOflong[9] = paramLong10;
/* 1278 */     paramArrayOflong[10] = paramLong11;
/* 1279 */     paramArrayOflong[11] = paramLong12;
/* 1280 */     paramArrayOflong[12] = paramLong13;
/* 1281 */     paramArrayOflong[13] = paramLong14;
/* 1282 */     paramArrayOflong[14] = paramLong15;
/* 1283 */     paramArrayOflong[15] = paramLong16;
/* 1284 */     paramArrayOflong[16] = paramLong17;
/* 1285 */     paramArrayOflong[17] = paramLong18;
/* 1286 */     paramArrayOflong[18] = paramLong19;
/*      */   }
/*      */   
/*      */   protected void mult(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 1290 */     long l1 = paramArrayOflong1[0] * paramArrayOflong2[0];
/* 1291 */     long l2 = paramArrayOflong1[0] * paramArrayOflong2[1] + paramArrayOflong1[1] * paramArrayOflong2[0];
/* 1292 */     long l3 = paramArrayOflong1[0] * paramArrayOflong2[2] + paramArrayOflong1[1] * paramArrayOflong2[1] + paramArrayOflong1[2] * paramArrayOflong2[0];
/* 1293 */     long l4 = paramArrayOflong1[0] * paramArrayOflong2[3] + paramArrayOflong1[1] * paramArrayOflong2[2] + paramArrayOflong1[2] * paramArrayOflong2[1] + paramArrayOflong1[3] * paramArrayOflong2[0];
/* 1294 */     long l5 = paramArrayOflong1[0] * paramArrayOflong2[4] + paramArrayOflong1[1] * paramArrayOflong2[3] + paramArrayOflong1[2] * paramArrayOflong2[2] + paramArrayOflong1[3] * paramArrayOflong2[1] + paramArrayOflong1[4] * paramArrayOflong2[0];
/* 1295 */     long l6 = paramArrayOflong1[0] * paramArrayOflong2[5] + paramArrayOflong1[1] * paramArrayOflong2[4] + paramArrayOflong1[2] * paramArrayOflong2[3] + paramArrayOflong1[3] * paramArrayOflong2[2] + paramArrayOflong1[4] * paramArrayOflong2[1] + paramArrayOflong1[5] * paramArrayOflong2[0];
/* 1296 */     long l7 = paramArrayOflong1[0] * paramArrayOflong2[6] + paramArrayOflong1[1] * paramArrayOflong2[5] + paramArrayOflong1[2] * paramArrayOflong2[4] + paramArrayOflong1[3] * paramArrayOflong2[3] + paramArrayOflong1[4] * paramArrayOflong2[2] + paramArrayOflong1[5] * paramArrayOflong2[1] + paramArrayOflong1[6] * paramArrayOflong2[0];
/* 1297 */     long l8 = paramArrayOflong1[0] * paramArrayOflong2[7] + paramArrayOflong1[1] * paramArrayOflong2[6] + paramArrayOflong1[2] * paramArrayOflong2[5] + paramArrayOflong1[3] * paramArrayOflong2[4] + paramArrayOflong1[4] * paramArrayOflong2[3] + paramArrayOflong1[5] * paramArrayOflong2[2] + paramArrayOflong1[6] * paramArrayOflong2[1] + paramArrayOflong1[7] * paramArrayOflong2[0];
/* 1298 */     long l9 = paramArrayOflong1[0] * paramArrayOflong2[8] + paramArrayOflong1[1] * paramArrayOflong2[7] + paramArrayOflong1[2] * paramArrayOflong2[6] + paramArrayOflong1[3] * paramArrayOflong2[5] + paramArrayOflong1[4] * paramArrayOflong2[4] + paramArrayOflong1[5] * paramArrayOflong2[3] + paramArrayOflong1[6] * paramArrayOflong2[2] + paramArrayOflong1[7] * paramArrayOflong2[1] + paramArrayOflong1[8] * paramArrayOflong2[0];
/* 1299 */     long l10 = paramArrayOflong1[0] * paramArrayOflong2[9] + paramArrayOflong1[1] * paramArrayOflong2[8] + paramArrayOflong1[2] * paramArrayOflong2[7] + paramArrayOflong1[3] * paramArrayOflong2[6] + paramArrayOflong1[4] * paramArrayOflong2[5] + paramArrayOflong1[5] * paramArrayOflong2[4] + paramArrayOflong1[6] * paramArrayOflong2[3] + paramArrayOflong1[7] * paramArrayOflong2[2] + paramArrayOflong1[8] * paramArrayOflong2[1] + paramArrayOflong1[9] * paramArrayOflong2[0];
/* 1300 */     long l11 = paramArrayOflong1[0] * paramArrayOflong2[10] + paramArrayOflong1[1] * paramArrayOflong2[9] + paramArrayOflong1[2] * paramArrayOflong2[8] + paramArrayOflong1[3] * paramArrayOflong2[7] + paramArrayOflong1[4] * paramArrayOflong2[6] + paramArrayOflong1[5] * paramArrayOflong2[5] + paramArrayOflong1[6] * paramArrayOflong2[4] + paramArrayOflong1[7] * paramArrayOflong2[3] + paramArrayOflong1[8] * paramArrayOflong2[2] + paramArrayOflong1[9] * paramArrayOflong2[1] + paramArrayOflong1[10] * paramArrayOflong2[0];
/* 1301 */     long l12 = paramArrayOflong1[0] * paramArrayOflong2[11] + paramArrayOflong1[1] * paramArrayOflong2[10] + paramArrayOflong1[2] * paramArrayOflong2[9] + paramArrayOflong1[3] * paramArrayOflong2[8] + paramArrayOflong1[4] * paramArrayOflong2[7] + paramArrayOflong1[5] * paramArrayOflong2[6] + paramArrayOflong1[6] * paramArrayOflong2[5] + paramArrayOflong1[7] * paramArrayOflong2[4] + paramArrayOflong1[8] * paramArrayOflong2[3] + paramArrayOflong1[9] * paramArrayOflong2[2] + paramArrayOflong1[10] * paramArrayOflong2[1] + paramArrayOflong1[11] * paramArrayOflong2[0];
/* 1302 */     long l13 = paramArrayOflong1[0] * paramArrayOflong2[12] + paramArrayOflong1[1] * paramArrayOflong2[11] + paramArrayOflong1[2] * paramArrayOflong2[10] + paramArrayOflong1[3] * paramArrayOflong2[9] + paramArrayOflong1[4] * paramArrayOflong2[8] + paramArrayOflong1[5] * paramArrayOflong2[7] + paramArrayOflong1[6] * paramArrayOflong2[6] + paramArrayOflong1[7] * paramArrayOflong2[5] + paramArrayOflong1[8] * paramArrayOflong2[4] + paramArrayOflong1[9] * paramArrayOflong2[3] + paramArrayOflong1[10] * paramArrayOflong2[2] + paramArrayOflong1[11] * paramArrayOflong2[1] + paramArrayOflong1[12] * paramArrayOflong2[0];
/* 1303 */     long l14 = paramArrayOflong1[0] * paramArrayOflong2[13] + paramArrayOflong1[1] * paramArrayOflong2[12] + paramArrayOflong1[2] * paramArrayOflong2[11] + paramArrayOflong1[3] * paramArrayOflong2[10] + paramArrayOflong1[4] * paramArrayOflong2[9] + paramArrayOflong1[5] * paramArrayOflong2[8] + paramArrayOflong1[6] * paramArrayOflong2[7] + paramArrayOflong1[7] * paramArrayOflong2[6] + paramArrayOflong1[8] * paramArrayOflong2[5] + paramArrayOflong1[9] * paramArrayOflong2[4] + paramArrayOflong1[10] * paramArrayOflong2[3] + paramArrayOflong1[11] * paramArrayOflong2[2] + paramArrayOflong1[12] * paramArrayOflong2[1] + paramArrayOflong1[13] * paramArrayOflong2[0];
/* 1304 */     long l15 = paramArrayOflong1[0] * paramArrayOflong2[14] + paramArrayOflong1[1] * paramArrayOflong2[13] + paramArrayOflong1[2] * paramArrayOflong2[12] + paramArrayOflong1[3] * paramArrayOflong2[11] + paramArrayOflong1[4] * paramArrayOflong2[10] + paramArrayOflong1[5] * paramArrayOflong2[9] + paramArrayOflong1[6] * paramArrayOflong2[8] + paramArrayOflong1[7] * paramArrayOflong2[7] + paramArrayOflong1[8] * paramArrayOflong2[6] + paramArrayOflong1[9] * paramArrayOflong2[5] + paramArrayOflong1[10] * paramArrayOflong2[4] + paramArrayOflong1[11] * paramArrayOflong2[3] + paramArrayOflong1[12] * paramArrayOflong2[2] + paramArrayOflong1[13] * paramArrayOflong2[1] + paramArrayOflong1[14] * paramArrayOflong2[0];
/* 1305 */     long l16 = paramArrayOflong1[0] * paramArrayOflong2[15] + paramArrayOflong1[1] * paramArrayOflong2[14] + paramArrayOflong1[2] * paramArrayOflong2[13] + paramArrayOflong1[3] * paramArrayOflong2[12] + paramArrayOflong1[4] * paramArrayOflong2[11] + paramArrayOflong1[5] * paramArrayOflong2[10] + paramArrayOflong1[6] * paramArrayOflong2[9] + paramArrayOflong1[7] * paramArrayOflong2[8] + paramArrayOflong1[8] * paramArrayOflong2[7] + paramArrayOflong1[9] * paramArrayOflong2[6] + paramArrayOflong1[10] * paramArrayOflong2[5] + paramArrayOflong1[11] * paramArrayOflong2[4] + paramArrayOflong1[12] * paramArrayOflong2[3] + paramArrayOflong1[13] * paramArrayOflong2[2] + paramArrayOflong1[14] * paramArrayOflong2[1] + paramArrayOflong1[15] * paramArrayOflong2[0];
/* 1306 */     long l17 = paramArrayOflong1[0] * paramArrayOflong2[16] + paramArrayOflong1[1] * paramArrayOflong2[15] + paramArrayOflong1[2] * paramArrayOflong2[14] + paramArrayOflong1[3] * paramArrayOflong2[13] + paramArrayOflong1[4] * paramArrayOflong2[12] + paramArrayOflong1[5] * paramArrayOflong2[11] + paramArrayOflong1[6] * paramArrayOflong2[10] + paramArrayOflong1[7] * paramArrayOflong2[9] + paramArrayOflong1[8] * paramArrayOflong2[8] + paramArrayOflong1[9] * paramArrayOflong2[7] + paramArrayOflong1[10] * paramArrayOflong2[6] + paramArrayOflong1[11] * paramArrayOflong2[5] + paramArrayOflong1[12] * paramArrayOflong2[4] + paramArrayOflong1[13] * paramArrayOflong2[3] + paramArrayOflong1[14] * paramArrayOflong2[2] + paramArrayOflong1[15] * paramArrayOflong2[1] + paramArrayOflong1[16] * paramArrayOflong2[0];
/* 1307 */     long l18 = paramArrayOflong1[0] * paramArrayOflong2[17] + paramArrayOflong1[1] * paramArrayOflong2[16] + paramArrayOflong1[2] * paramArrayOflong2[15] + paramArrayOflong1[3] * paramArrayOflong2[14] + paramArrayOflong1[4] * paramArrayOflong2[13] + paramArrayOflong1[5] * paramArrayOflong2[12] + paramArrayOflong1[6] * paramArrayOflong2[11] + paramArrayOflong1[7] * paramArrayOflong2[10] + paramArrayOflong1[8] * paramArrayOflong2[9] + paramArrayOflong1[9] * paramArrayOflong2[8] + paramArrayOflong1[10] * paramArrayOflong2[7] + paramArrayOflong1[11] * paramArrayOflong2[6] + paramArrayOflong1[12] * paramArrayOflong2[5] + paramArrayOflong1[13] * paramArrayOflong2[4] + paramArrayOflong1[14] * paramArrayOflong2[3] + paramArrayOflong1[15] * paramArrayOflong2[2] + paramArrayOflong1[16] * paramArrayOflong2[1] + paramArrayOflong1[17] * paramArrayOflong2[0];
/* 1308 */     long l19 = paramArrayOflong1[0] * paramArrayOflong2[18] + paramArrayOflong1[1] * paramArrayOflong2[17] + paramArrayOflong1[2] * paramArrayOflong2[16] + paramArrayOflong1[3] * paramArrayOflong2[15] + paramArrayOflong1[4] * paramArrayOflong2[14] + paramArrayOflong1[5] * paramArrayOflong2[13] + paramArrayOflong1[6] * paramArrayOflong2[12] + paramArrayOflong1[7] * paramArrayOflong2[11] + paramArrayOflong1[8] * paramArrayOflong2[10] + paramArrayOflong1[9] * paramArrayOflong2[9] + paramArrayOflong1[10] * paramArrayOflong2[8] + paramArrayOflong1[11] * paramArrayOflong2[7] + paramArrayOflong1[12] * paramArrayOflong2[6] + paramArrayOflong1[13] * paramArrayOflong2[5] + paramArrayOflong1[14] * paramArrayOflong2[4] + paramArrayOflong1[15] * paramArrayOflong2[3] + paramArrayOflong1[16] * paramArrayOflong2[2] + paramArrayOflong1[17] * paramArrayOflong2[1] + paramArrayOflong1[18] * paramArrayOflong2[0];
/* 1309 */     long l20 = paramArrayOflong1[1] * paramArrayOflong2[18] + paramArrayOflong1[2] * paramArrayOflong2[17] + paramArrayOflong1[3] * paramArrayOflong2[16] + paramArrayOflong1[4] * paramArrayOflong2[15] + paramArrayOflong1[5] * paramArrayOflong2[14] + paramArrayOflong1[6] * paramArrayOflong2[13] + paramArrayOflong1[7] * paramArrayOflong2[12] + paramArrayOflong1[8] * paramArrayOflong2[11] + paramArrayOflong1[9] * paramArrayOflong2[10] + paramArrayOflong1[10] * paramArrayOflong2[9] + paramArrayOflong1[11] * paramArrayOflong2[8] + paramArrayOflong1[12] * paramArrayOflong2[7] + paramArrayOflong1[13] * paramArrayOflong2[6] + paramArrayOflong1[14] * paramArrayOflong2[5] + paramArrayOflong1[15] * paramArrayOflong2[4] + paramArrayOflong1[16] * paramArrayOflong2[3] + paramArrayOflong1[17] * paramArrayOflong2[2] + paramArrayOflong1[18] * paramArrayOflong2[1];
/* 1310 */     long l21 = paramArrayOflong1[2] * paramArrayOflong2[18] + paramArrayOflong1[3] * paramArrayOflong2[17] + paramArrayOflong1[4] * paramArrayOflong2[16] + paramArrayOflong1[5] * paramArrayOflong2[15] + paramArrayOflong1[6] * paramArrayOflong2[14] + paramArrayOflong1[7] * paramArrayOflong2[13] + paramArrayOflong1[8] * paramArrayOflong2[12] + paramArrayOflong1[9] * paramArrayOflong2[11] + paramArrayOflong1[10] * paramArrayOflong2[10] + paramArrayOflong1[11] * paramArrayOflong2[9] + paramArrayOflong1[12] * paramArrayOflong2[8] + paramArrayOflong1[13] * paramArrayOflong2[7] + paramArrayOflong1[14] * paramArrayOflong2[6] + paramArrayOflong1[15] * paramArrayOflong2[5] + paramArrayOflong1[16] * paramArrayOflong2[4] + paramArrayOflong1[17] * paramArrayOflong2[3] + paramArrayOflong1[18] * paramArrayOflong2[2];
/* 1311 */     long l22 = paramArrayOflong1[3] * paramArrayOflong2[18] + paramArrayOflong1[4] * paramArrayOflong2[17] + paramArrayOflong1[5] * paramArrayOflong2[16] + paramArrayOflong1[6] * paramArrayOflong2[15] + paramArrayOflong1[7] * paramArrayOflong2[14] + paramArrayOflong1[8] * paramArrayOflong2[13] + paramArrayOflong1[9] * paramArrayOflong2[12] + paramArrayOflong1[10] * paramArrayOflong2[11] + paramArrayOflong1[11] * paramArrayOflong2[10] + paramArrayOflong1[12] * paramArrayOflong2[9] + paramArrayOflong1[13] * paramArrayOflong2[8] + paramArrayOflong1[14] * paramArrayOflong2[7] + paramArrayOflong1[15] * paramArrayOflong2[6] + paramArrayOflong1[16] * paramArrayOflong2[5] + paramArrayOflong1[17] * paramArrayOflong2[4] + paramArrayOflong1[18] * paramArrayOflong2[3];
/* 1312 */     long l23 = paramArrayOflong1[4] * paramArrayOflong2[18] + paramArrayOflong1[5] * paramArrayOflong2[17] + paramArrayOflong1[6] * paramArrayOflong2[16] + paramArrayOflong1[7] * paramArrayOflong2[15] + paramArrayOflong1[8] * paramArrayOflong2[14] + paramArrayOflong1[9] * paramArrayOflong2[13] + paramArrayOflong1[10] * paramArrayOflong2[12] + paramArrayOflong1[11] * paramArrayOflong2[11] + paramArrayOflong1[12] * paramArrayOflong2[10] + paramArrayOflong1[13] * paramArrayOflong2[9] + paramArrayOflong1[14] * paramArrayOflong2[8] + paramArrayOflong1[15] * paramArrayOflong2[7] + paramArrayOflong1[16] * paramArrayOflong2[6] + paramArrayOflong1[17] * paramArrayOflong2[5] + paramArrayOflong1[18] * paramArrayOflong2[4];
/* 1313 */     long l24 = paramArrayOflong1[5] * paramArrayOflong2[18] + paramArrayOflong1[6] * paramArrayOflong2[17] + paramArrayOflong1[7] * paramArrayOflong2[16] + paramArrayOflong1[8] * paramArrayOflong2[15] + paramArrayOflong1[9] * paramArrayOflong2[14] + paramArrayOflong1[10] * paramArrayOflong2[13] + paramArrayOflong1[11] * paramArrayOflong2[12] + paramArrayOflong1[12] * paramArrayOflong2[11] + paramArrayOflong1[13] * paramArrayOflong2[10] + paramArrayOflong1[14] * paramArrayOflong2[9] + paramArrayOflong1[15] * paramArrayOflong2[8] + paramArrayOflong1[16] * paramArrayOflong2[7] + paramArrayOflong1[17] * paramArrayOflong2[6] + paramArrayOflong1[18] * paramArrayOflong2[5];
/* 1314 */     long l25 = paramArrayOflong1[6] * paramArrayOflong2[18] + paramArrayOflong1[7] * paramArrayOflong2[17] + paramArrayOflong1[8] * paramArrayOflong2[16] + paramArrayOflong1[9] * paramArrayOflong2[15] + paramArrayOflong1[10] * paramArrayOflong2[14] + paramArrayOflong1[11] * paramArrayOflong2[13] + paramArrayOflong1[12] * paramArrayOflong2[12] + paramArrayOflong1[13] * paramArrayOflong2[11] + paramArrayOflong1[14] * paramArrayOflong2[10] + paramArrayOflong1[15] * paramArrayOflong2[9] + paramArrayOflong1[16] * paramArrayOflong2[8] + paramArrayOflong1[17] * paramArrayOflong2[7] + paramArrayOflong1[18] * paramArrayOflong2[6];
/* 1315 */     long l26 = paramArrayOflong1[7] * paramArrayOflong2[18] + paramArrayOflong1[8] * paramArrayOflong2[17] + paramArrayOflong1[9] * paramArrayOflong2[16] + paramArrayOflong1[10] * paramArrayOflong2[15] + paramArrayOflong1[11] * paramArrayOflong2[14] + paramArrayOflong1[12] * paramArrayOflong2[13] + paramArrayOflong1[13] * paramArrayOflong2[12] + paramArrayOflong1[14] * paramArrayOflong2[11] + paramArrayOflong1[15] * paramArrayOflong2[10] + paramArrayOflong1[16] * paramArrayOflong2[9] + paramArrayOflong1[17] * paramArrayOflong2[8] + paramArrayOflong1[18] * paramArrayOflong2[7];
/* 1316 */     long l27 = paramArrayOflong1[8] * paramArrayOflong2[18] + paramArrayOflong1[9] * paramArrayOflong2[17] + paramArrayOflong1[10] * paramArrayOflong2[16] + paramArrayOflong1[11] * paramArrayOflong2[15] + paramArrayOflong1[12] * paramArrayOflong2[14] + paramArrayOflong1[13] * paramArrayOflong2[13] + paramArrayOflong1[14] * paramArrayOflong2[12] + paramArrayOflong1[15] * paramArrayOflong2[11] + paramArrayOflong1[16] * paramArrayOflong2[10] + paramArrayOflong1[17] * paramArrayOflong2[9] + paramArrayOflong1[18] * paramArrayOflong2[8];
/* 1317 */     long l28 = paramArrayOflong1[9] * paramArrayOflong2[18] + paramArrayOflong1[10] * paramArrayOflong2[17] + paramArrayOflong1[11] * paramArrayOflong2[16] + paramArrayOflong1[12] * paramArrayOflong2[15] + paramArrayOflong1[13] * paramArrayOflong2[14] + paramArrayOflong1[14] * paramArrayOflong2[13] + paramArrayOflong1[15] * paramArrayOflong2[12] + paramArrayOflong1[16] * paramArrayOflong2[11] + paramArrayOflong1[17] * paramArrayOflong2[10] + paramArrayOflong1[18] * paramArrayOflong2[9];
/* 1318 */     long l29 = paramArrayOflong1[10] * paramArrayOflong2[18] + paramArrayOflong1[11] * paramArrayOflong2[17] + paramArrayOflong1[12] * paramArrayOflong2[16] + paramArrayOflong1[13] * paramArrayOflong2[15] + paramArrayOflong1[14] * paramArrayOflong2[14] + paramArrayOflong1[15] * paramArrayOflong2[13] + paramArrayOflong1[16] * paramArrayOflong2[12] + paramArrayOflong1[17] * paramArrayOflong2[11] + paramArrayOflong1[18] * paramArrayOflong2[10];
/* 1319 */     long l30 = paramArrayOflong1[11] * paramArrayOflong2[18] + paramArrayOflong1[12] * paramArrayOflong2[17] + paramArrayOflong1[13] * paramArrayOflong2[16] + paramArrayOflong1[14] * paramArrayOflong2[15] + paramArrayOflong1[15] * paramArrayOflong2[14] + paramArrayOflong1[16] * paramArrayOflong2[13] + paramArrayOflong1[17] * paramArrayOflong2[12] + paramArrayOflong1[18] * paramArrayOflong2[11];
/* 1320 */     long l31 = paramArrayOflong1[12] * paramArrayOflong2[18] + paramArrayOflong1[13] * paramArrayOflong2[17] + paramArrayOflong1[14] * paramArrayOflong2[16] + paramArrayOflong1[15] * paramArrayOflong2[15] + paramArrayOflong1[16] * paramArrayOflong2[14] + paramArrayOflong1[17] * paramArrayOflong2[13] + paramArrayOflong1[18] * paramArrayOflong2[12];
/* 1321 */     long l32 = paramArrayOflong1[13] * paramArrayOflong2[18] + paramArrayOflong1[14] * paramArrayOflong2[17] + paramArrayOflong1[15] * paramArrayOflong2[16] + paramArrayOflong1[16] * paramArrayOflong2[15] + paramArrayOflong1[17] * paramArrayOflong2[14] + paramArrayOflong1[18] * paramArrayOflong2[13];
/* 1322 */     long l33 = paramArrayOflong1[14] * paramArrayOflong2[18] + paramArrayOflong1[15] * paramArrayOflong2[17] + paramArrayOflong1[16] * paramArrayOflong2[16] + paramArrayOflong1[17] * paramArrayOflong2[15] + paramArrayOflong1[18] * paramArrayOflong2[14];
/* 1323 */     long l34 = paramArrayOflong1[15] * paramArrayOflong2[18] + paramArrayOflong1[16] * paramArrayOflong2[17] + paramArrayOflong1[17] * paramArrayOflong2[16] + paramArrayOflong1[18] * paramArrayOflong2[15];
/* 1324 */     long l35 = paramArrayOflong1[16] * paramArrayOflong2[18] + paramArrayOflong1[17] * paramArrayOflong2[17] + paramArrayOflong1[18] * paramArrayOflong2[16];
/* 1325 */     long l36 = paramArrayOflong1[17] * paramArrayOflong2[18] + paramArrayOflong1[18] * paramArrayOflong2[17];
/* 1326 */     long l37 = paramArrayOflong1[18] * paramArrayOflong2[18];
/*      */     
/* 1328 */     carryReduce(paramArrayOflong3, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32, l33, l34, l35, l36, l37);
/*      */   }
/*      */   
/*      */   protected void reduce(long[] paramArrayOflong) {
/* 1332 */     carryReduce(paramArrayOflong, paramArrayOflong[0], paramArrayOflong[1], paramArrayOflong[2], paramArrayOflong[3], paramArrayOflong[4], paramArrayOflong[5], paramArrayOflong[6], paramArrayOflong[7], paramArrayOflong[8], paramArrayOflong[9], paramArrayOflong[10], paramArrayOflong[11], paramArrayOflong[12], paramArrayOflong[13], paramArrayOflong[14], paramArrayOflong[15], paramArrayOflong[16], paramArrayOflong[17], paramArrayOflong[18]);
/*      */   }
/*      */   
/*      */   protected void square(long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 1336 */     long l1 = paramArrayOflong1[0] * paramArrayOflong1[0];
/* 1337 */     long l2 = 2L * paramArrayOflong1[0] * paramArrayOflong1[1];
/* 1338 */     long l3 = 2L * paramArrayOflong1[0] * paramArrayOflong1[2] + paramArrayOflong1[1] * paramArrayOflong1[1];
/* 1339 */     long l4 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[3] + paramArrayOflong1[1] * paramArrayOflong1[2]);
/* 1340 */     long l5 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[4] + paramArrayOflong1[1] * paramArrayOflong1[3]) + paramArrayOflong1[2] * paramArrayOflong1[2];
/* 1341 */     long l6 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[5] + paramArrayOflong1[1] * paramArrayOflong1[4] + paramArrayOflong1[2] * paramArrayOflong1[3]);
/* 1342 */     long l7 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[6] + paramArrayOflong1[1] * paramArrayOflong1[5] + paramArrayOflong1[2] * paramArrayOflong1[4]) + paramArrayOflong1[3] * paramArrayOflong1[3];
/* 1343 */     long l8 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[7] + paramArrayOflong1[1] * paramArrayOflong1[6] + paramArrayOflong1[2] * paramArrayOflong1[5] + paramArrayOflong1[3] * paramArrayOflong1[4]);
/* 1344 */     long l9 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[8] + paramArrayOflong1[1] * paramArrayOflong1[7] + paramArrayOflong1[2] * paramArrayOflong1[6] + paramArrayOflong1[3] * paramArrayOflong1[5]) + paramArrayOflong1[4] * paramArrayOflong1[4];
/* 1345 */     long l10 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[9] + paramArrayOflong1[1] * paramArrayOflong1[8] + paramArrayOflong1[2] * paramArrayOflong1[7] + paramArrayOflong1[3] * paramArrayOflong1[6] + paramArrayOflong1[4] * paramArrayOflong1[5]);
/* 1346 */     long l11 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[10] + paramArrayOflong1[1] * paramArrayOflong1[9] + paramArrayOflong1[2] * paramArrayOflong1[8] + paramArrayOflong1[3] * paramArrayOflong1[7] + paramArrayOflong1[4] * paramArrayOflong1[6]) + paramArrayOflong1[5] * paramArrayOflong1[5];
/* 1347 */     long l12 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[11] + paramArrayOflong1[1] * paramArrayOflong1[10] + paramArrayOflong1[2] * paramArrayOflong1[9] + paramArrayOflong1[3] * paramArrayOflong1[8] + paramArrayOflong1[4] * paramArrayOflong1[7] + paramArrayOflong1[5] * paramArrayOflong1[6]);
/* 1348 */     long l13 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[12] + paramArrayOflong1[1] * paramArrayOflong1[11] + paramArrayOflong1[2] * paramArrayOflong1[10] + paramArrayOflong1[3] * paramArrayOflong1[9] + paramArrayOflong1[4] * paramArrayOflong1[8] + paramArrayOflong1[5] * paramArrayOflong1[7]) + paramArrayOflong1[6] * paramArrayOflong1[6];
/* 1349 */     long l14 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[13] + paramArrayOflong1[1] * paramArrayOflong1[12] + paramArrayOflong1[2] * paramArrayOflong1[11] + paramArrayOflong1[3] * paramArrayOflong1[10] + paramArrayOflong1[4] * paramArrayOflong1[9] + paramArrayOflong1[5] * paramArrayOflong1[8] + paramArrayOflong1[6] * paramArrayOflong1[7]);
/* 1350 */     long l15 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[14] + paramArrayOflong1[1] * paramArrayOflong1[13] + paramArrayOflong1[2] * paramArrayOflong1[12] + paramArrayOflong1[3] * paramArrayOflong1[11] + paramArrayOflong1[4] * paramArrayOflong1[10] + paramArrayOflong1[5] * paramArrayOflong1[9] + paramArrayOflong1[6] * paramArrayOflong1[8]) + paramArrayOflong1[7] * paramArrayOflong1[7];
/* 1351 */     long l16 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[15] + paramArrayOflong1[1] * paramArrayOflong1[14] + paramArrayOflong1[2] * paramArrayOflong1[13] + paramArrayOflong1[3] * paramArrayOflong1[12] + paramArrayOflong1[4] * paramArrayOflong1[11] + paramArrayOflong1[5] * paramArrayOflong1[10] + paramArrayOflong1[6] * paramArrayOflong1[9] + paramArrayOflong1[7] * paramArrayOflong1[8]);
/* 1352 */     long l17 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[16] + paramArrayOflong1[1] * paramArrayOflong1[15] + paramArrayOflong1[2] * paramArrayOflong1[14] + paramArrayOflong1[3] * paramArrayOflong1[13] + paramArrayOflong1[4] * paramArrayOflong1[12] + paramArrayOflong1[5] * paramArrayOflong1[11] + paramArrayOflong1[6] * paramArrayOflong1[10] + paramArrayOflong1[7] * paramArrayOflong1[9]) + paramArrayOflong1[8] * paramArrayOflong1[8];
/* 1353 */     long l18 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[17] + paramArrayOflong1[1] * paramArrayOflong1[16] + paramArrayOflong1[2] * paramArrayOflong1[15] + paramArrayOflong1[3] * paramArrayOflong1[14] + paramArrayOflong1[4] * paramArrayOflong1[13] + paramArrayOflong1[5] * paramArrayOflong1[12] + paramArrayOflong1[6] * paramArrayOflong1[11] + paramArrayOflong1[7] * paramArrayOflong1[10] + paramArrayOflong1[8] * paramArrayOflong1[9]);
/* 1354 */     long l19 = 2L * (paramArrayOflong1[0] * paramArrayOflong1[18] + paramArrayOflong1[1] * paramArrayOflong1[17] + paramArrayOflong1[2] * paramArrayOflong1[16] + paramArrayOflong1[3] * paramArrayOflong1[15] + paramArrayOflong1[4] * paramArrayOflong1[14] + paramArrayOflong1[5] * paramArrayOflong1[13] + paramArrayOflong1[6] * paramArrayOflong1[12] + paramArrayOflong1[7] * paramArrayOflong1[11] + paramArrayOflong1[8] * paramArrayOflong1[10]) + paramArrayOflong1[9] * paramArrayOflong1[9];
/* 1355 */     long l20 = 2L * (paramArrayOflong1[1] * paramArrayOflong1[18] + paramArrayOflong1[2] * paramArrayOflong1[17] + paramArrayOflong1[3] * paramArrayOflong1[16] + paramArrayOflong1[4] * paramArrayOflong1[15] + paramArrayOflong1[5] * paramArrayOflong1[14] + paramArrayOflong1[6] * paramArrayOflong1[13] + paramArrayOflong1[7] * paramArrayOflong1[12] + paramArrayOflong1[8] * paramArrayOflong1[11] + paramArrayOflong1[9] * paramArrayOflong1[10]);
/* 1356 */     long l21 = 2L * (paramArrayOflong1[2] * paramArrayOflong1[18] + paramArrayOflong1[3] * paramArrayOflong1[17] + paramArrayOflong1[4] * paramArrayOflong1[16] + paramArrayOflong1[5] * paramArrayOflong1[15] + paramArrayOflong1[6] * paramArrayOflong1[14] + paramArrayOflong1[7] * paramArrayOflong1[13] + paramArrayOflong1[8] * paramArrayOflong1[12] + paramArrayOflong1[9] * paramArrayOflong1[11]) + paramArrayOflong1[10] * paramArrayOflong1[10];
/* 1357 */     long l22 = 2L * (paramArrayOflong1[3] * paramArrayOflong1[18] + paramArrayOflong1[4] * paramArrayOflong1[17] + paramArrayOflong1[5] * paramArrayOflong1[16] + paramArrayOflong1[6] * paramArrayOflong1[15] + paramArrayOflong1[7] * paramArrayOflong1[14] + paramArrayOflong1[8] * paramArrayOflong1[13] + paramArrayOflong1[9] * paramArrayOflong1[12] + paramArrayOflong1[10] * paramArrayOflong1[11]);
/* 1358 */     long l23 = 2L * (paramArrayOflong1[4] * paramArrayOflong1[18] + paramArrayOflong1[5] * paramArrayOflong1[17] + paramArrayOflong1[6] * paramArrayOflong1[16] + paramArrayOflong1[7] * paramArrayOflong1[15] + paramArrayOflong1[8] * paramArrayOflong1[14] + paramArrayOflong1[9] * paramArrayOflong1[13] + paramArrayOflong1[10] * paramArrayOflong1[12]) + paramArrayOflong1[11] * paramArrayOflong1[11];
/* 1359 */     long l24 = 2L * (paramArrayOflong1[5] * paramArrayOflong1[18] + paramArrayOflong1[6] * paramArrayOflong1[17] + paramArrayOflong1[7] * paramArrayOflong1[16] + paramArrayOflong1[8] * paramArrayOflong1[15] + paramArrayOflong1[9] * paramArrayOflong1[14] + paramArrayOflong1[10] * paramArrayOflong1[13] + paramArrayOflong1[11] * paramArrayOflong1[12]);
/* 1360 */     long l25 = 2L * (paramArrayOflong1[6] * paramArrayOflong1[18] + paramArrayOflong1[7] * paramArrayOflong1[17] + paramArrayOflong1[8] * paramArrayOflong1[16] + paramArrayOflong1[9] * paramArrayOflong1[15] + paramArrayOflong1[10] * paramArrayOflong1[14] + paramArrayOflong1[11] * paramArrayOflong1[13]) + paramArrayOflong1[12] * paramArrayOflong1[12];
/* 1361 */     long l26 = 2L * (paramArrayOflong1[7] * paramArrayOflong1[18] + paramArrayOflong1[8] * paramArrayOflong1[17] + paramArrayOflong1[9] * paramArrayOflong1[16] + paramArrayOflong1[10] * paramArrayOflong1[15] + paramArrayOflong1[11] * paramArrayOflong1[14] + paramArrayOflong1[12] * paramArrayOflong1[13]);
/* 1362 */     long l27 = 2L * (paramArrayOflong1[8] * paramArrayOflong1[18] + paramArrayOflong1[9] * paramArrayOflong1[17] + paramArrayOflong1[10] * paramArrayOflong1[16] + paramArrayOflong1[11] * paramArrayOflong1[15] + paramArrayOflong1[12] * paramArrayOflong1[14]) + paramArrayOflong1[13] * paramArrayOflong1[13];
/* 1363 */     long l28 = 2L * (paramArrayOflong1[9] * paramArrayOflong1[18] + paramArrayOflong1[10] * paramArrayOflong1[17] + paramArrayOflong1[11] * paramArrayOflong1[16] + paramArrayOflong1[12] * paramArrayOflong1[15] + paramArrayOflong1[13] * paramArrayOflong1[14]);
/* 1364 */     long l29 = 2L * (paramArrayOflong1[10] * paramArrayOflong1[18] + paramArrayOflong1[11] * paramArrayOflong1[17] + paramArrayOflong1[12] * paramArrayOflong1[16] + paramArrayOflong1[13] * paramArrayOflong1[15]) + paramArrayOflong1[14] * paramArrayOflong1[14];
/* 1365 */     long l30 = 2L * (paramArrayOflong1[11] * paramArrayOflong1[18] + paramArrayOflong1[12] * paramArrayOflong1[17] + paramArrayOflong1[13] * paramArrayOflong1[16] + paramArrayOflong1[14] * paramArrayOflong1[15]);
/* 1366 */     long l31 = 2L * (paramArrayOflong1[12] * paramArrayOflong1[18] + paramArrayOflong1[13] * paramArrayOflong1[17] + paramArrayOflong1[14] * paramArrayOflong1[16]) + paramArrayOflong1[15] * paramArrayOflong1[15];
/* 1367 */     long l32 = 2L * (paramArrayOflong1[13] * paramArrayOflong1[18] + paramArrayOflong1[14] * paramArrayOflong1[17] + paramArrayOflong1[15] * paramArrayOflong1[16]);
/* 1368 */     long l33 = 2L * (paramArrayOflong1[14] * paramArrayOflong1[18] + paramArrayOflong1[15] * paramArrayOflong1[17]) + paramArrayOflong1[16] * paramArrayOflong1[16];
/* 1369 */     long l34 = 2L * (paramArrayOflong1[15] * paramArrayOflong1[18] + paramArrayOflong1[16] * paramArrayOflong1[17]);
/* 1370 */     long l35 = 2L * paramArrayOflong1[16] * paramArrayOflong1[18] + paramArrayOflong1[17] * paramArrayOflong1[17];
/* 1371 */     long l36 = 2L * paramArrayOflong1[17] * paramArrayOflong1[18];
/* 1372 */     long l37 = paramArrayOflong1[18] * paramArrayOflong1[18];
/*      */     
/* 1374 */     carryReduce(paramArrayOflong2, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32, l33, l34, l35, l36, l37);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/intpoly/P521OrderField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */