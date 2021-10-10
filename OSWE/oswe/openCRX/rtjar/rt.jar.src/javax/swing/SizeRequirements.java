/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class SizeRequirements
/*     */   implements Serializable
/*     */ {
/*     */   public int minimum;
/*     */   public int preferred;
/*     */   public int maximum;
/*     */   public float alignment;
/*     */   
/*     */   public SizeRequirements() {
/* 135 */     this.minimum = 0;
/* 136 */     this.preferred = 0;
/* 137 */     this.maximum = 0;
/* 138 */     this.alignment = 0.5F;
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
/*     */   public SizeRequirements(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
/* 151 */     this.minimum = paramInt1;
/* 152 */     this.preferred = paramInt2;
/* 153 */     this.maximum = paramInt3;
/* 154 */     this.alignment = (paramFloat > 1.0F) ? 1.0F : ((paramFloat < 0.0F) ? 0.0F : paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 164 */     return "[" + this.minimum + "," + this.preferred + "," + this.maximum + "]@" + this.alignment;
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
/*     */ 
/*     */   
/*     */   public static SizeRequirements getTiledSizeRequirements(SizeRequirements[] paramArrayOfSizeRequirements) {
/* 183 */     SizeRequirements sizeRequirements = new SizeRequirements();
/* 184 */     for (byte b = 0; b < paramArrayOfSizeRequirements.length; b++) {
/* 185 */       SizeRequirements sizeRequirements1 = paramArrayOfSizeRequirements[b];
/* 186 */       sizeRequirements.minimum = (int)Math.min(sizeRequirements.minimum + sizeRequirements1.minimum, 2147483647L);
/* 187 */       sizeRequirements.preferred = (int)Math.min(sizeRequirements.preferred + sizeRequirements1.preferred, 2147483647L);
/* 188 */       sizeRequirements.maximum = (int)Math.min(sizeRequirements.maximum + sizeRequirements1.maximum, 2147483647L);
/*     */     } 
/* 190 */     return sizeRequirements;
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
/*     */   public static SizeRequirements getAlignedSizeRequirements(SizeRequirements[] paramArrayOfSizeRequirements) {
/* 206 */     SizeRequirements sizeRequirements1 = new SizeRequirements();
/* 207 */     SizeRequirements sizeRequirements2 = new SizeRequirements(); int i;
/* 208 */     for (i = 0; i < paramArrayOfSizeRequirements.length; i++) {
/* 209 */       SizeRequirements sizeRequirements = paramArrayOfSizeRequirements[i];
/*     */       
/* 211 */       int m = (int)(sizeRequirements.alignment * sizeRequirements.minimum);
/* 212 */       int n = sizeRequirements.minimum - m;
/* 213 */       sizeRequirements1.minimum = Math.max(m, sizeRequirements1.minimum);
/* 214 */       sizeRequirements2.minimum = Math.max(n, sizeRequirements2.minimum);
/*     */       
/* 216 */       m = (int)(sizeRequirements.alignment * sizeRequirements.preferred);
/* 217 */       n = sizeRequirements.preferred - m;
/* 218 */       sizeRequirements1.preferred = Math.max(m, sizeRequirements1.preferred);
/* 219 */       sizeRequirements2.preferred = Math.max(n, sizeRequirements2.preferred);
/*     */       
/* 221 */       m = (int)(sizeRequirements.alignment * sizeRequirements.maximum);
/* 222 */       n = sizeRequirements.maximum - m;
/* 223 */       sizeRequirements1.maximum = Math.max(m, sizeRequirements1.maximum);
/* 224 */       sizeRequirements2.maximum = Math.max(n, sizeRequirements2.maximum);
/*     */     } 
/* 226 */     i = (int)Math.min(sizeRequirements1.minimum + sizeRequirements2.minimum, 2147483647L);
/* 227 */     int j = (int)Math.min(sizeRequirements1.preferred + sizeRequirements2.preferred, 2147483647L);
/* 228 */     int k = (int)Math.min(sizeRequirements1.maximum + sizeRequirements2.maximum, 2147483647L);
/* 229 */     float f = 0.0F;
/* 230 */     if (i > 0) {
/* 231 */       f = sizeRequirements1.minimum / i;
/* 232 */       f = (f > 1.0F) ? 1.0F : ((f < 0.0F) ? 0.0F : f);
/*     */     } 
/* 234 */     return new SizeRequirements(i, j, k, f);
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
/*     */   public static void calculateTiledPositions(int paramInt, SizeRequirements paramSizeRequirements, SizeRequirements[] paramArrayOfSizeRequirements, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 263 */     calculateTiledPositions(paramInt, paramSizeRequirements, paramArrayOfSizeRequirements, paramArrayOfint1, paramArrayOfint2, true);
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
/*     */   public static void calculateTiledPositions(int paramInt, SizeRequirements paramSizeRequirements, SizeRequirements[] paramArrayOfSizeRequirements, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean) {
/* 308 */     long l1 = 0L;
/* 309 */     long l2 = 0L;
/* 310 */     long l3 = 0L;
/* 311 */     for (byte b = 0; b < paramArrayOfSizeRequirements.length; b++) {
/* 312 */       l1 += (paramArrayOfSizeRequirements[b]).minimum;
/* 313 */       l2 += (paramArrayOfSizeRequirements[b]).preferred;
/* 314 */       l3 += (paramArrayOfSizeRequirements[b]).maximum;
/*     */     } 
/* 316 */     if (paramInt >= l2) {
/* 317 */       expandedTile(paramInt, l1, l2, l3, paramArrayOfSizeRequirements, paramArrayOfint1, paramArrayOfint2, paramBoolean);
/*     */     } else {
/* 319 */       compressedTile(paramInt, l1, l2, l3, paramArrayOfSizeRequirements, paramArrayOfint1, paramArrayOfint2, paramBoolean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void compressedTile(int paramInt, long paramLong1, long paramLong2, long paramLong3, SizeRequirements[] paramArrayOfSizeRequirements, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean) {
/* 329 */     float f1 = (float)Math.min(paramLong2 - paramInt, paramLong2 - paramLong1);
/* 330 */     float f2 = (paramLong2 - paramLong1 == 0L) ? 0.0F : (f1 / (float)(paramLong2 - paramLong1));
/*     */ 
/*     */ 
/*     */     
/* 334 */     if (paramBoolean) {
/*     */       
/* 336 */       int i = 0;
/* 337 */       for (byte b = 0; b < paramArrayOfint2.length; b++) {
/* 338 */         paramArrayOfint1[b] = i;
/* 339 */         SizeRequirements sizeRequirements = paramArrayOfSizeRequirements[b];
/* 340 */         float f = f2 * (sizeRequirements.preferred - sizeRequirements.minimum);
/* 341 */         paramArrayOfint2[b] = (int)(sizeRequirements.preferred - f);
/* 342 */         i = (int)Math.min(i + paramArrayOfint2[b], 2147483647L);
/*     */       } 
/*     */     } else {
/*     */       
/* 346 */       int i = paramInt;
/* 347 */       for (byte b = 0; b < paramArrayOfint2.length; b++) {
/* 348 */         SizeRequirements sizeRequirements = paramArrayOfSizeRequirements[b];
/* 349 */         float f = f2 * (sizeRequirements.preferred - sizeRequirements.minimum);
/* 350 */         paramArrayOfint2[b] = (int)(sizeRequirements.preferred - f);
/* 351 */         paramArrayOfint1[b] = i - paramArrayOfint2[b];
/* 352 */         i = (int)Math.max(i - paramArrayOfint2[b], 0L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void expandedTile(int paramInt, long paramLong1, long paramLong2, long paramLong3, SizeRequirements[] paramArrayOfSizeRequirements, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean) {
/* 363 */     float f1 = (float)Math.min(paramInt - paramLong2, paramLong3 - paramLong2);
/* 364 */     float f2 = (paramLong3 - paramLong2 == 0L) ? 0.0F : (f1 / (float)(paramLong3 - paramLong2));
/*     */ 
/*     */ 
/*     */     
/* 368 */     if (paramBoolean) {
/*     */       
/* 370 */       int i = 0;
/* 371 */       for (byte b = 0; b < paramArrayOfint2.length; b++) {
/* 372 */         paramArrayOfint1[b] = i;
/* 373 */         SizeRequirements sizeRequirements = paramArrayOfSizeRequirements[b];
/* 374 */         int j = (int)(f2 * (sizeRequirements.maximum - sizeRequirements.preferred));
/* 375 */         paramArrayOfint2[b] = (int)Math.min(sizeRequirements.preferred + j, 2147483647L);
/* 376 */         i = (int)Math.min(i + paramArrayOfint2[b], 2147483647L);
/*     */       } 
/*     */     } else {
/*     */       
/* 380 */       int i = paramInt;
/* 381 */       for (byte b = 0; b < paramArrayOfint2.length; b++) {
/* 382 */         SizeRequirements sizeRequirements = paramArrayOfSizeRequirements[b];
/* 383 */         int j = (int)(f2 * (sizeRequirements.maximum - sizeRequirements.preferred));
/* 384 */         paramArrayOfint2[b] = (int)Math.min(sizeRequirements.preferred + j, 2147483647L);
/* 385 */         paramArrayOfint1[b] = i - paramArrayOfint2[b];
/* 386 */         i = (int)Math.max(i - paramArrayOfint2[b], 0L);
/*     */       } 
/*     */     } 
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
/*     */   public static void calculateAlignedPositions(int paramInt, SizeRequirements paramSizeRequirements, SizeRequirements[] paramArrayOfSizeRequirements, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 421 */     calculateAlignedPositions(paramInt, paramSizeRequirements, paramArrayOfSizeRequirements, paramArrayOfint1, paramArrayOfint2, true);
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
/*     */   public static void calculateAlignedPositions(int paramInt, SizeRequirements paramSizeRequirements, SizeRequirements[] paramArrayOfSizeRequirements, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean) {
/* 461 */     float f = paramBoolean ? paramSizeRequirements.alignment : (1.0F - paramSizeRequirements.alignment);
/* 462 */     int i = (int)(paramInt * f);
/* 463 */     int j = paramInt - i;
/* 464 */     for (byte b = 0; b < paramArrayOfSizeRequirements.length; b++) {
/* 465 */       SizeRequirements sizeRequirements = paramArrayOfSizeRequirements[b];
/* 466 */       float f1 = paramBoolean ? sizeRequirements.alignment : (1.0F - sizeRequirements.alignment);
/* 467 */       int k = (int)(sizeRequirements.maximum * f1);
/* 468 */       int m = sizeRequirements.maximum - k;
/* 469 */       int n = Math.min(i, k);
/* 470 */       int i1 = Math.min(j, m);
/*     */       
/* 472 */       paramArrayOfint1[b] = i - n;
/* 473 */       paramArrayOfint2[b] = (int)Math.min(n + i1, 2147483647L);
/*     */     } 
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
/*     */   public static int[] adjustSizes(int paramInt, SizeRequirements[] paramArrayOfSizeRequirements) {
/* 486 */     return new int[0];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SizeRequirements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */