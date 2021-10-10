/*     */ package sun.java2d.pisces;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import sun.awt.geom.PathConsumer2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Renderer
/*     */   implements PathConsumer2D
/*     */ {
/*     */   private static final int YMAX = 0;
/*     */   private static final int CURX = 1;
/*     */   private static final int OR = 2;
/*     */   private static final int SLOPE = 3;
/*     */   private static final int NEXT = 4;
/*     */   
/*     */   private class ScanlineIterator
/*     */   {
/*     */     private int[] crossings;
/*     */     private final int maxY;
/*     */     private int nextY;
/*     */     private int edgeCount;
/*     */     private int[] edgePtrs;
/*     */     private static final int INIT_CROSSINGS_SIZE = 10;
/*     */     
/*     */     private ScanlineIterator(int param1Int1, int param1Int2) {
/*  55 */       this.crossings = new int[10];
/*  56 */       this.edgePtrs = new int[10];
/*     */       
/*  58 */       this.nextY = param1Int1;
/*  59 */       this.maxY = param1Int2;
/*  60 */       this.edgeCount = 0;
/*     */     }
/*     */     
/*     */     private int next() {
/*  64 */       int i = this.nextY++;
/*  65 */       int j = i - Renderer.this.boundsMinY;
/*  66 */       int k = this.edgeCount;
/*  67 */       int[] arrayOfInt1 = this.edgePtrs;
/*  68 */       int m = Renderer.this.edgeBucketCounts[j];
/*  69 */       if ((m & 0x1) != 0) {
/*  70 */         byte b1 = 0;
/*  71 */         for (byte b2 = 0; b2 < k; b2++) {
/*  72 */           int i1 = arrayOfInt1[b2];
/*  73 */           if (Renderer.this.edges[i1 + 0] > i) {
/*  74 */             arrayOfInt1[b1++] = i1;
/*     */           }
/*     */         } 
/*  77 */         k = b1;
/*     */       } 
/*  79 */       arrayOfInt1 = Helpers.widenArray(arrayOfInt1, k, m >> 1); int n;
/*  80 */       for (n = Renderer.this.edgeBuckets[j]; n != -5; n = (int)Renderer.this.edges[n + 4]) {
/*  81 */         arrayOfInt1[k++] = n;
/*     */       }
/*     */       
/*  84 */       this.edgePtrs = arrayOfInt1;
/*  85 */       this.edgeCount = k;
/*     */ 
/*     */ 
/*     */       
/*  89 */       int[] arrayOfInt2 = this.crossings;
/*  90 */       if (arrayOfInt2.length < k) {
/*  91 */         this.crossings = arrayOfInt2 = new int[arrayOfInt1.length];
/*     */       }
/*  93 */       for (byte b = 0; b < k; b++) {
/*  94 */         int i1 = arrayOfInt1[b];
/*  95 */         float f = Renderer.this.edges[i1 + 1];
/*  96 */         int i2 = (int)f << 1;
/*  97 */         Renderer.this.edges[i1 + 1] = f + Renderer.this.edges[i1 + 3];
/*  98 */         if (Renderer.this.edges[i1 + 2] > 0.0F) {
/*  99 */           i2 |= 0x1;
/*     */         }
/* 101 */         byte b1 = b;
/* 102 */         while (--b1 >= 0) {
/* 103 */           int i3 = arrayOfInt2[b1];
/* 104 */           if (i3 <= i2) {
/*     */             break;
/*     */           }
/* 107 */           arrayOfInt2[b1 + 1] = i3;
/* 108 */           arrayOfInt1[b1 + 1] = arrayOfInt1[b1];
/*     */         } 
/* 110 */         arrayOfInt2[b1 + 1] = i2;
/* 111 */         arrayOfInt1[b1 + 1] = i1;
/*     */       } 
/* 113 */       return k;
/*     */     }
/*     */     
/*     */     private boolean hasNext() {
/* 117 */       return (this.nextY < this.maxY);
/*     */     }
/*     */     
/*     */     private int curY() {
/* 121 */       return this.nextY - 1;
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
/* 142 */   private float edgeMinY = Float.POSITIVE_INFINITY;
/* 143 */   private float edgeMaxY = Float.NEGATIVE_INFINITY;
/* 144 */   private float edgeMinX = Float.POSITIVE_INFINITY;
/* 145 */   private float edgeMaxX = Float.NEGATIVE_INFINITY;
/*     */   
/*     */   private static final int SIZEOF_EDGE = 5;
/*     */   
/*     */   private static final int NULL = -5;
/* 150 */   private float[] edges = null;
/*     */   private static final int INIT_NUM_EDGES = 8;
/* 152 */   private int[] edgeBuckets = null;
/* 153 */   private int[] edgeBucketCounts = null; private int numEdges; private static final float DEC_BND = 20.0F; private static final float INC_BND = 8.0F; public static final int WIND_EVEN_ODD = 0; public static final int WIND_NON_ZERO = 1;
/*     */   private final int SUBPIXEL_LG_POSITIONS_X;
/*     */   private final int SUBPIXEL_LG_POSITIONS_Y;
/*     */   private final int SUBPIXEL_POSITIONS_X;
/*     */   private final int SUBPIXEL_POSITIONS_Y;
/*     */   private final int SUBPIXEL_MASK_X;
/*     */   private final int SUBPIXEL_MASK_Y;
/*     */   
/*     */   private void addEdgeToBucket(int paramInt1, int paramInt2) {
/* 162 */     this.edges[paramInt1 + 4] = this.edgeBuckets[paramInt2];
/* 163 */     this.edgeBuckets[paramInt2] = paramInt1;
/* 164 */     this.edgeBucketCounts[paramInt2] = this.edgeBucketCounts[paramInt2] + 2;
/*     */   }
/*     */   final int MAX_AA_ALPHA; PiscesCache cache; private final int boundsMinX; private final int boundsMinY; private final int boundsMaxX;
/*     */   private final int boundsMaxY;
/*     */   private final int windingRule;
/*     */   private float x0;
/*     */   private float y0;
/*     */   private float pix_sx0;
/*     */   private float pix_sy0;
/*     */   private Curve c;
/*     */   
/*     */   private void quadBreakIntoLinesAndAdd(float paramFloat1, float paramFloat2, Curve paramCurve, float paramFloat3, float paramFloat4) {
/* 176 */     int i = 16;
/* 177 */     int j = i * i;
/* 178 */     float f1 = Math.max(paramCurve.dbx / j, paramCurve.dby / j);
/* 179 */     while (f1 > 32.0F) {
/* 180 */       f1 /= 4.0F;
/* 181 */       i <<= 1;
/*     */     } 
/*     */     
/* 184 */     j = i * i;
/* 185 */     float f2 = paramCurve.dbx / j;
/* 186 */     float f3 = paramCurve.dby / j;
/* 187 */     float f4 = paramCurve.bx / j + paramCurve.cx / i;
/* 188 */     float f5 = paramCurve.by / j + paramCurve.cy / i;
/*     */     
/* 190 */     while (i-- > 1) {
/* 191 */       float f6 = paramFloat1 + f4;
/* 192 */       f4 += f2;
/* 193 */       float f7 = paramFloat2 + f5;
/* 194 */       f5 += f3;
/* 195 */       addLine(paramFloat1, paramFloat2, f6, f7);
/* 196 */       paramFloat1 = f6;
/* 197 */       paramFloat2 = f7;
/*     */     } 
/* 199 */     addLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
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
/*     */   private void curveBreakIntoLinesAndAdd(float paramFloat1, float paramFloat2, Curve paramCurve, float paramFloat3, float paramFloat4) {
/* 212 */     int i = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     float f1 = 2.0F * paramCurve.dax / 512.0F;
/* 218 */     float f2 = 2.0F * paramCurve.day / 512.0F;
/*     */     
/* 220 */     float f3 = f1 + paramCurve.dbx / 64.0F;
/* 221 */     float f4 = f2 + paramCurve.dby / 64.0F;
/* 222 */     float f5 = paramCurve.ax / 512.0F + paramCurve.bx / 64.0F + paramCurve.cx / 8.0F;
/* 223 */     float f6 = paramCurve.ay / 512.0F + paramCurve.by / 64.0F + paramCurve.cy / 8.0F;
/*     */ 
/*     */     
/* 226 */     float f7 = paramFloat1, f8 = paramFloat2;
/* 227 */     while (i > 0) {
/* 228 */       while (Math.abs(f3) > 20.0F || Math.abs(f4) > 20.0F) {
/* 229 */         f1 /= 8.0F;
/* 230 */         f2 /= 8.0F;
/* 231 */         f3 = f3 / 4.0F - f1;
/* 232 */         f4 = f4 / 4.0F - f2;
/* 233 */         f5 = (f5 - f3) / 2.0F;
/* 234 */         f6 = (f6 - f4) / 2.0F;
/* 235 */         i <<= 1;
/*     */       } 
/*     */       
/* 238 */       while (i % 2 == 0 && Math.abs(f5) <= 8.0F && Math.abs(f6) <= 8.0F) {
/* 239 */         f5 = 2.0F * f5 + f3;
/* 240 */         f6 = 2.0F * f6 + f4;
/* 241 */         f3 = 4.0F * (f3 + f1);
/* 242 */         f4 = 4.0F * (f4 + f2);
/* 243 */         f1 = 8.0F * f1;
/* 244 */         f2 = 8.0F * f2;
/* 245 */         i >>= 1;
/*     */       } 
/* 247 */       i--;
/* 248 */       if (i > 0) {
/* 249 */         f7 += f5;
/* 250 */         f5 += f3;
/* 251 */         f3 += f1;
/* 252 */         f8 += f6;
/* 253 */         f6 += f4;
/* 254 */         f4 += f2;
/*     */       } else {
/* 256 */         f7 = paramFloat3;
/* 257 */         f8 = paramFloat4;
/*     */       } 
/* 259 */       addLine(paramFloat1, paramFloat2, f7, f8);
/* 260 */       paramFloat1 = f7;
/* 261 */       paramFloat2 = f8;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 266 */     float f1 = 1.0F;
/* 267 */     if (paramFloat4 < paramFloat2) {
/* 268 */       f1 = paramFloat4;
/* 269 */       paramFloat4 = paramFloat2;
/* 270 */       paramFloat2 = f1;
/* 271 */       f1 = paramFloat3;
/* 272 */       paramFloat3 = paramFloat1;
/* 273 */       paramFloat1 = f1;
/* 274 */       f1 = 0.0F;
/*     */     } 
/* 276 */     int i = Math.max((int)Math.ceil(paramFloat2), this.boundsMinY);
/* 277 */     int j = Math.min((int)Math.ceil(paramFloat4), this.boundsMaxY);
/* 278 */     if (i >= j) {
/*     */       return;
/*     */     }
/* 281 */     if (paramFloat2 < this.edgeMinY) this.edgeMinY = paramFloat2; 
/* 282 */     if (paramFloat4 > this.edgeMaxY) this.edgeMaxY = paramFloat4;
/*     */     
/* 284 */     float f2 = (paramFloat3 - paramFloat1) / (paramFloat4 - paramFloat2);
/*     */     
/* 286 */     if (f2 > 0.0F) {
/* 287 */       if (paramFloat1 < this.edgeMinX) this.edgeMinX = paramFloat1; 
/* 288 */       if (paramFloat3 > this.edgeMaxX) this.edgeMaxX = paramFloat3; 
/*     */     } else {
/* 290 */       if (paramFloat3 < this.edgeMinX) this.edgeMinX = paramFloat3; 
/* 291 */       if (paramFloat1 > this.edgeMaxX) this.edgeMaxX = paramFloat1;
/*     */     
/*     */     } 
/* 294 */     int k = this.numEdges * 5;
/* 295 */     this.edges = Helpers.widenArray(this.edges, k, 5);
/* 296 */     this.numEdges++;
/* 297 */     this.edges[k + 2] = f1;
/* 298 */     this.edges[k + 1] = paramFloat1 + (i - paramFloat2) * f2;
/* 299 */     this.edges[k + 3] = f2;
/* 300 */     this.edges[k + 0] = j;
/* 301 */     int m = i - this.boundsMinY;
/* 302 */     addEdgeToBucket(k, m);
/* 303 */     this.edgeBucketCounts[j - this.boundsMinY] = this.edgeBucketCounts[j - this.boundsMinY] | 0x1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float tosubpixx(float paramFloat) {
/* 365 */     return paramFloat * this.SUBPIXEL_POSITIONS_X;
/*     */   }
/*     */   private float tosubpixy(float paramFloat) {
/* 368 */     return paramFloat * this.SUBPIXEL_POSITIONS_Y;
/*     */   }
/*     */   
/*     */   public void moveTo(float paramFloat1, float paramFloat2) {
/* 372 */     closePath();
/* 373 */     this.pix_sx0 = paramFloat1;
/* 374 */     this.pix_sy0 = paramFloat2;
/* 375 */     this.y0 = tosubpixy(paramFloat2);
/* 376 */     this.x0 = tosubpixx(paramFloat1);
/*     */   }
/*     */   
/*     */   public void lineTo(float paramFloat1, float paramFloat2) {
/* 380 */     float f1 = tosubpixx(paramFloat1);
/* 381 */     float f2 = tosubpixy(paramFloat2);
/* 382 */     addLine(this.x0, this.y0, f1, f2);
/* 383 */     this.x0 = f1;
/* 384 */     this.y0 = f2;
/*     */   }
/*     */   
/* 387 */   public Renderer(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) { this.c = new Curve(); this.SUBPIXEL_LG_POSITIONS_X = paramInt1; this.SUBPIXEL_LG_POSITIONS_Y = paramInt2; this.SUBPIXEL_MASK_X = (1 << this.SUBPIXEL_LG_POSITIONS_X) - 1; this.SUBPIXEL_MASK_Y = (1 << this.SUBPIXEL_LG_POSITIONS_Y) - 1; this.SUBPIXEL_POSITIONS_X = 1 << this.SUBPIXEL_LG_POSITIONS_X; this.SUBPIXEL_POSITIONS_Y = 1 << this.SUBPIXEL_LG_POSITIONS_Y; this.MAX_AA_ALPHA = this.SUBPIXEL_POSITIONS_X * this.SUBPIXEL_POSITIONS_Y; this.windingRule = paramInt7; this.boundsMinX = paramInt3 * this.SUBPIXEL_POSITIONS_X; this.boundsMinY = paramInt4 * this.SUBPIXEL_POSITIONS_Y; this.boundsMaxX = (paramInt3 + paramInt5) * this.SUBPIXEL_POSITIONS_X; this.boundsMaxY = (paramInt4 + paramInt6) * this.SUBPIXEL_POSITIONS_Y;
/*     */     this.edges = new float[40];
/*     */     this.numEdges = 0;
/*     */     this.edgeBuckets = new int[this.boundsMaxY - this.boundsMinY];
/*     */     Arrays.fill(this.edgeBuckets, -5);
/* 392 */     this.edgeBucketCounts = new int[this.edgeBuckets.length + 1]; } public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) { float f1 = tosubpixx(paramFloat5);
/* 393 */     float f2 = tosubpixy(paramFloat6);
/* 394 */     this.c.set(this.x0, this.y0, tosubpixx(paramFloat1), tosubpixy(paramFloat2), tosubpixx(paramFloat3), tosubpixy(paramFloat4), f1, f2);
/* 395 */     curveBreakIntoLinesAndAdd(this.x0, this.y0, this.c, f1, f2);
/* 396 */     this.x0 = f1;
/* 397 */     this.y0 = f2; }
/*     */ 
/*     */   
/*     */   public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 401 */     float f1 = tosubpixx(paramFloat3);
/* 402 */     float f2 = tosubpixy(paramFloat4);
/* 403 */     this.c.set(this.x0, this.y0, tosubpixx(paramFloat1), tosubpixy(paramFloat2), f1, f2);
/* 404 */     quadBreakIntoLinesAndAdd(this.x0, this.y0, this.c, f1, f2);
/* 405 */     this.x0 = f1;
/* 406 */     this.y0 = f2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closePath() {
/* 411 */     lineTo(this.pix_sx0, this.pix_sy0);
/*     */   }
/*     */   
/*     */   public void pathDone() {
/* 415 */     closePath();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getNativeConsumer() {
/* 421 */     throw new InternalError("Renderer does not use a native consumer.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void _endRendering(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 429 */     byte b = (this.windingRule == 0) ? 1 : -1;
/*     */ 
/*     */     
/* 432 */     int i = paramInt2 - paramInt1;
/* 433 */     int[] arrayOfInt = new int[i + 2];
/*     */     
/* 435 */     int j = paramInt1 << this.SUBPIXEL_LG_POSITIONS_X;
/* 436 */     int k = paramInt2 << this.SUBPIXEL_LG_POSITIONS_X;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 444 */     int m = Integer.MIN_VALUE;
/* 445 */     int n = Integer.MAX_VALUE;
/*     */     
/* 447 */     int i1 = this.boundsMinY;
/* 448 */     ScanlineIterator scanlineIterator = new ScanlineIterator(paramInt3, paramInt4);
/* 449 */     while (scanlineIterator.hasNext()) {
/* 450 */       int i2 = scanlineIterator.next();
/* 451 */       int[] arrayOfInt1 = scanlineIterator.crossings;
/* 452 */       i1 = scanlineIterator.curY();
/*     */       
/* 454 */       if (i2 > 0) {
/* 455 */         int i5 = arrayOfInt1[0] >> 1;
/* 456 */         int i6 = arrayOfInt1[i2 - 1] >> 1;
/* 457 */         int i7 = Math.max(i5, j);
/* 458 */         int i8 = Math.min(i6, k);
/*     */         
/* 460 */         n = Math.min(n, i7 >> this.SUBPIXEL_LG_POSITIONS_X);
/* 461 */         m = Math.max(m, i8 >> this.SUBPIXEL_LG_POSITIONS_X);
/*     */       } 
/*     */       
/* 464 */       int i3 = 0;
/* 465 */       int i4 = j;
/* 466 */       for (byte b1 = 0; b1 < i2; b1++) {
/* 467 */         int i5 = arrayOfInt1[b1];
/* 468 */         int i6 = i5 >> 1;
/*     */         
/* 470 */         int i7 = ((i5 & 0x1) << 1) - 1;
/* 471 */         if ((i3 & b) != 0) {
/* 472 */           int i8 = Math.max(i4, j);
/* 473 */           int i9 = Math.min(i6, k);
/* 474 */           if (i8 < i9) {
/* 475 */             i8 -= j;
/* 476 */             i9 -= j;
/*     */             
/* 478 */             int i10 = i8 >> this.SUBPIXEL_LG_POSITIONS_X;
/* 479 */             int i11 = i9 - 1 >> this.SUBPIXEL_LG_POSITIONS_X;
/*     */             
/* 481 */             if (i10 == i11) {
/*     */               
/* 483 */               arrayOfInt[i10] = arrayOfInt[i10] + i9 - i8;
/* 484 */               arrayOfInt[i10 + 1] = arrayOfInt[i10 + 1] - i9 - i8;
/*     */             } else {
/* 486 */               int i12 = i9 >> this.SUBPIXEL_LG_POSITIONS_X;
/* 487 */               arrayOfInt[i10] = arrayOfInt[i10] + this.SUBPIXEL_POSITIONS_X - (i8 & this.SUBPIXEL_MASK_X);
/* 488 */               arrayOfInt[i10 + 1] = arrayOfInt[i10 + 1] + (i8 & this.SUBPIXEL_MASK_X);
/* 489 */               arrayOfInt[i12] = arrayOfInt[i12] - this.SUBPIXEL_POSITIONS_X - (i9 & this.SUBPIXEL_MASK_X);
/* 490 */               arrayOfInt[i12 + 1] = arrayOfInt[i12 + 1] - (i9 & this.SUBPIXEL_MASK_X);
/*     */             } 
/*     */           } 
/*     */         } 
/* 494 */         i3 += i7;
/* 495 */         i4 = i6;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 501 */       if ((i1 & this.SUBPIXEL_MASK_Y) == this.SUBPIXEL_MASK_Y) {
/* 502 */         emitRow(arrayOfInt, i1 >> this.SUBPIXEL_LG_POSITIONS_Y, n, m);
/* 503 */         n = Integer.MAX_VALUE;
/* 504 */         m = Integer.MIN_VALUE;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 509 */     if (m >= n) {
/* 510 */       emitRow(arrayOfInt, i1 >> this.SUBPIXEL_LG_POSITIONS_Y, n, m);
/*     */     }
/*     */   }
/*     */   
/*     */   public void endRendering() {
/* 515 */     int i = Math.max((int)Math.ceil(this.edgeMinX), this.boundsMinX);
/* 516 */     int j = Math.min((int)Math.ceil(this.edgeMaxX), this.boundsMaxX);
/* 517 */     int k = Math.max((int)Math.ceil(this.edgeMinY), this.boundsMinY);
/* 518 */     int m = Math.min((int)Math.ceil(this.edgeMaxY), this.boundsMaxY);
/*     */     
/* 520 */     int n = i >> this.SUBPIXEL_LG_POSITIONS_X;
/* 521 */     int i1 = j + this.SUBPIXEL_MASK_X >> this.SUBPIXEL_LG_POSITIONS_X;
/* 522 */     int i2 = k >> this.SUBPIXEL_LG_POSITIONS_Y;
/* 523 */     int i3 = m + this.SUBPIXEL_MASK_Y >> this.SUBPIXEL_LG_POSITIONS_Y;
/*     */     
/* 525 */     if (n > i1 || i2 > i3) {
/* 526 */       this.cache = new PiscesCache(this.boundsMinX >> this.SUBPIXEL_LG_POSITIONS_X, this.boundsMinY >> this.SUBPIXEL_LG_POSITIONS_Y, this.boundsMaxX >> this.SUBPIXEL_LG_POSITIONS_X, this.boundsMaxY >> this.SUBPIXEL_LG_POSITIONS_Y);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 533 */     this.cache = new PiscesCache(n, i2, i1, i3);
/* 534 */     _endRendering(n, i1, k, m);
/*     */   }
/*     */   
/*     */   public PiscesCache getCache() {
/* 538 */     if (this.cache == null) {
/* 539 */       throw new InternalError("cache not yet initialized");
/*     */     }
/* 541 */     return this.cache;
/*     */   }
/*     */ 
/*     */   
/*     */   private void emitRow(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
/* 546 */     if (this.cache != null && 
/* 547 */       paramInt3 >= paramInt2) {
/* 548 */       this.cache.startRow(paramInt1, paramInt2);
/*     */ 
/*     */       
/* 551 */       int i = paramInt2 - this.cache.bboxX0;
/* 552 */       int j = paramInt3 - this.cache.bboxX0;
/*     */       
/* 554 */       byte b = 1;
/* 555 */       int k = paramArrayOfint[i];
/* 556 */       for (int m = i + 1; m <= j; m++) {
/* 557 */         int n = k + paramArrayOfint[m];
/* 558 */         if (n == k) {
/* 559 */           b++;
/*     */         } else {
/* 561 */           this.cache.addRLERun(k, b);
/* 562 */           b = 1;
/* 563 */           k = n;
/*     */         } 
/*     */       } 
/* 566 */       this.cache.addRLERun(k, b);
/*     */     } 
/*     */     
/* 569 */     Arrays.fill(paramArrayOfint, 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pisces/Renderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */