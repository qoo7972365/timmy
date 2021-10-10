/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.ColorModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RadialGradientPaintContext
/*     */   extends MultipleGradientPaintContext
/*     */ {
/*     */   private boolean isSimpleFocus = false;
/*     */   private boolean isNonCyclic = false;
/*     */   private float radius;
/*     */   private float centerX;
/*     */   private float centerY;
/*     */   private float focusX;
/*     */   private float focusY;
/*     */   private float radiusSq;
/*     */   private float constA;
/*     */   private float constB;
/*     */   private float gDeltaDelta;
/*     */   private float trivial;
/*     */   private static final float SCALEBACK = 0.99F;
/*     */   private static final int SQRT_LUT_SIZE = 2048;
/*     */   
/*     */   RadialGradientPaintContext(RadialGradientPaint paramRadialGradientPaint, ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod, MultipleGradientPaint.ColorSpaceType paramColorSpaceType) {
/* 123 */     super(paramRadialGradientPaint, paramColorModel, paramRectangle, paramRectangle2D, paramAffineTransform, paramRenderingHints, paramArrayOffloat, paramArrayOfColor, paramCycleMethod, paramColorSpaceType);
/*     */ 
/*     */ 
/*     */     
/* 127 */     this.centerX = paramFloat1;
/* 128 */     this.centerY = paramFloat2;
/* 129 */     this.focusX = paramFloat4;
/* 130 */     this.focusY = paramFloat5;
/* 131 */     this.radius = paramFloat3;
/*     */     
/* 133 */     this.isSimpleFocus = (this.focusX == this.centerX && this.focusY == this.centerY);
/* 134 */     this.isNonCyclic = (paramCycleMethod == MultipleGradientPaint.CycleMethod.NO_CYCLE);
/*     */ 
/*     */     
/* 137 */     this.radiusSq = this.radius * this.radius;
/*     */     
/* 139 */     float f1 = this.focusX - this.centerX;
/* 140 */     float f2 = this.focusY - this.centerY;
/*     */     
/* 142 */     double d = (f1 * f1 + f2 * f2);
/*     */ 
/*     */     
/* 145 */     if (d > (this.radiusSq * 0.99F)) {
/*     */       
/* 147 */       float f = (float)Math.sqrt((this.radiusSq * 0.99F) / d);
/* 148 */       f1 *= f;
/* 149 */       f2 *= f;
/* 150 */       this.focusX = this.centerX + f1;
/* 151 */       this.focusY = this.centerY + f2;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 156 */     this.trivial = (float)Math.sqrt((this.radiusSq - f1 * f1));
/*     */ 
/*     */     
/* 159 */     this.constA = this.a02 - this.centerX;
/* 160 */     this.constB = this.a12 - this.centerY;
/*     */ 
/*     */     
/* 163 */     this.gDeltaDelta = 2.0F * (this.a00 * this.a00 + this.a10 * this.a10) / this.radiusSq;
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
/*     */   protected void fillRaster(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 176 */     if (this.isSimpleFocus && this.isNonCyclic && this.isSimpleLookup) {
/* 177 */       simpleNonCyclicFillRaster(paramArrayOfint, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     } else {
/* 179 */       cyclicCircularGradientFillRaster(paramArrayOfint, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void simpleNonCyclicFillRaster(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 226 */     float f1 = this.a00 * paramInt3 + this.a01 * paramInt4 + this.constA;
/* 227 */     float f2 = this.a10 * paramInt3 + this.a11 * paramInt4 + this.constB;
/*     */ 
/*     */     
/* 230 */     float f3 = this.gDeltaDelta;
/*     */ 
/*     */     
/* 233 */     paramInt2 += paramInt5;
/*     */ 
/*     */     
/* 236 */     int i = this.gradient[this.fastGradientArraySize];
/*     */     
/* 238 */     for (byte b = 0; b < paramInt6; b++) {
/*     */       
/* 240 */       float f4 = (f1 * f1 + f2 * f2) / this.radiusSq;
/* 241 */       float f5 = 2.0F * (this.a00 * f1 + this.a10 * f2) / this.radiusSq + f3 / 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       byte b1 = 0;
/*     */       
/* 261 */       while (b1 < paramInt5 && f4 >= 1.0F) {
/* 262 */         paramArrayOfint[paramInt1 + b1] = i;
/* 263 */         f4 += f5;
/* 264 */         f5 += f3;
/* 265 */         b1++;
/*     */       } 
/*     */       
/* 268 */       while (b1 < paramInt5 && f4 < 1.0F) {
/*     */         int j;
/*     */         
/* 271 */         if (f4 <= 0.0F) {
/* 272 */           j = 0;
/*     */         } else {
/* 274 */           float f6 = f4 * 2048.0F;
/* 275 */           int k = (int)f6;
/* 276 */           float f7 = sqrtLut[k];
/* 277 */           float f8 = sqrtLut[k + 1] - f7;
/* 278 */           f6 = f7 + (f6 - k) * f8;
/* 279 */           j = (int)(f6 * this.fastGradientArraySize);
/*     */         } 
/*     */ 
/*     */         
/* 283 */         paramArrayOfint[paramInt1 + b1] = this.gradient[j];
/*     */ 
/*     */         
/* 286 */         f4 += f5;
/* 287 */         f5 += f3;
/* 288 */         b1++;
/*     */       } 
/*     */       
/* 291 */       while (b1 < paramInt5) {
/* 292 */         paramArrayOfint[paramInt1 + b1] = i;
/* 293 */         b1++;
/*     */       } 
/*     */       
/* 296 */       paramInt1 += paramInt2;
/* 297 */       f1 += this.a01;
/* 298 */       f2 += this.a11;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 304 */   private static float[] sqrtLut = new float[2049];
/*     */   static {
/* 306 */     for (byte b = 0; b < sqrtLut.length; b++) {
/* 307 */       sqrtLut[b] = (float)Math.sqrt((b / 2048.0F));
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
/*     */   private void cyclicCircularGradientFillRaster(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 336 */     double d = (-this.radiusSq + this.centerX * this.centerX + this.centerY * this.centerY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     float f1 = this.a00 * paramInt3 + this.a01 * paramInt4 + this.a02;
/* 350 */     float f2 = this.a10 * paramInt3 + this.a11 * paramInt4 + this.a12;
/*     */ 
/*     */     
/* 353 */     float f3 = 2.0F * this.centerY;
/* 354 */     float f4 = -2.0F * this.centerX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     int i = paramInt1;
/*     */ 
/*     */     
/* 375 */     int j = paramInt5 + paramInt2;
/*     */ 
/*     */     
/* 378 */     for (byte b = 0; b < paramInt6; b++) {
/*     */ 
/*     */       
/* 381 */       float f5 = this.a01 * b + f1;
/* 382 */       float f6 = this.a11 * b + f2;
/*     */ 
/*     */       
/* 385 */       for (byte b1 = 0; b1 < paramInt5; b1++) {
/*     */         double d1, d2;
/* 387 */         if (f5 == this.focusX) {
/*     */           
/* 389 */           d1 = this.focusX;
/* 390 */           d2 = this.centerY;
/* 391 */           d2 += (f6 > this.focusY) ? this.trivial : -this.trivial;
/*     */         } else {
/*     */           
/* 394 */           double d6 = ((f6 - this.focusY) / (f5 - this.focusX));
/* 395 */           double d7 = f6 - d6 * f5;
/*     */ 
/*     */ 
/*     */           
/* 399 */           double d3 = d6 * d6 + 1.0D;
/* 400 */           double d4 = f4 + -2.0D * d6 * (this.centerY - d7);
/* 401 */           double d5 = d + d7 * (d7 - f3);
/*     */           
/* 403 */           float f = (float)Math.sqrt(d4 * d4 - 4.0D * d3 * d5);
/* 404 */           d1 = -d4;
/*     */ 
/*     */ 
/*     */           
/* 408 */           d1 += (f5 < this.focusX) ? -f : f;
/* 409 */           d1 /= 2.0D * d3;
/* 410 */           d2 = d6 * d1 + d7;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 418 */         float f10 = f5 - this.focusX;
/* 419 */         f10 *= f10;
/*     */         
/* 421 */         float f11 = f6 - this.focusY;
/* 422 */         f11 *= f11;
/*     */         
/* 424 */         float f8 = f10 + f11;
/*     */         
/* 426 */         f10 = (float)d1 - this.focusX;
/* 427 */         f10 *= f10;
/*     */         
/* 429 */         f11 = (float)d2 - this.focusY;
/* 430 */         f11 *= f11;
/*     */         
/* 432 */         float f9 = f10 + f11;
/*     */ 
/*     */ 
/*     */         
/* 436 */         float f7 = (float)Math.sqrt((f8 / f9));
/*     */ 
/*     */         
/* 439 */         paramArrayOfint[i + b1] = indexIntoGradientsArrays(f7);
/*     */ 
/*     */         
/* 442 */         f5 += this.a00;
/* 443 */         f6 += this.a10;
/*     */       } 
/*     */       
/* 446 */       i += j;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/RadialGradientPaintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */