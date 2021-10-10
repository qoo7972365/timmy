/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MultipleGradientPaintContext
/*     */   implements PaintContext
/*     */ {
/*     */   protected ColorModel model;
/*  62 */   private static ColorModel xrgbmodel = new DirectColorModel(24, 16711680, 65280, 255);
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ColorModel cachedModel;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static WeakReference<Raster> cached;
/*     */ 
/*     */   
/*     */   protected Raster saved;
/*     */ 
/*     */   
/*     */   protected MultipleGradientPaint.CycleMethod cycleMethod;
/*     */ 
/*     */   
/*     */   protected MultipleGradientPaint.ColorSpaceType colorSpace;
/*     */ 
/*     */   
/*     */   protected float a00;
/*     */ 
/*     */   
/*     */   protected float a01;
/*     */ 
/*     */   
/*     */   protected float a10;
/*     */ 
/*     */   
/*     */   protected float a11;
/*     */ 
/*     */   
/*     */   protected float a02;
/*     */ 
/*     */   
/*     */   protected float a12;
/*     */ 
/*     */   
/*     */   protected boolean isSimpleLookup;
/*     */ 
/*     */   
/*     */   protected int fastGradientArraySize;
/*     */ 
/*     */   
/*     */   protected int[] gradient;
/*     */ 
/*     */   
/*     */   private int[][] gradients;
/*     */ 
/*     */   
/*     */   private float[] normalizedIntervals;
/*     */ 
/*     */   
/*     */   private float[] fractions;
/*     */ 
/*     */   
/*     */   private int transparencyTest;
/*     */ 
/*     */   
/* 121 */   private static final int[] SRGBtoLinearRGB = new int[256];
/* 122 */   private static final int[] LinearRGBtoSRGB = new int[256];
/*     */   protected static final int GRADIENT_SIZE = 256;
/*     */   
/*     */   static {
/* 126 */     for (byte b = 0; b < 'Ā'; b++) {
/* 127 */       SRGBtoLinearRGB[b] = convertSRGBtoLinearRGB(b);
/* 128 */       LinearRGBtoSRGB[b] = convertLinearRGBtoSRGB(b);
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
/*     */   protected static final int GRADIENT_SIZE_INDEX = 255;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_GRADIENT_ARRAY_SIZE = 5000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MultipleGradientPaintContext(MultipleGradientPaint paramMultipleGradientPaint, ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod, MultipleGradientPaint.ColorSpaceType paramColorSpaceType) {
/*     */     AffineTransform affineTransform;
/* 161 */     if (paramRectangle == null) {
/* 162 */       throw new NullPointerException("Device bounds cannot be null");
/*     */     }
/*     */     
/* 165 */     if (paramRectangle2D == null) {
/* 166 */       throw new NullPointerException("User bounds cannot be null");
/*     */     }
/*     */     
/* 169 */     if (paramAffineTransform == null) {
/* 170 */       throw new NullPointerException("Transform cannot be null");
/*     */     }
/*     */     
/* 173 */     if (paramRenderingHints == null) {
/* 174 */       throw new NullPointerException("RenderingHints cannot be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 183 */       paramAffineTransform.invert();
/* 184 */       affineTransform = paramAffineTransform;
/* 185 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/*     */ 
/*     */       
/* 188 */       affineTransform = new AffineTransform();
/*     */     } 
/* 190 */     double[] arrayOfDouble = new double[6];
/* 191 */     affineTransform.getMatrix(arrayOfDouble);
/* 192 */     this.a00 = (float)arrayOfDouble[0];
/* 193 */     this.a10 = (float)arrayOfDouble[1];
/* 194 */     this.a01 = (float)arrayOfDouble[2];
/* 195 */     this.a11 = (float)arrayOfDouble[3];
/* 196 */     this.a02 = (float)arrayOfDouble[4];
/* 197 */     this.a12 = (float)arrayOfDouble[5];
/*     */ 
/*     */     
/* 200 */     this.cycleMethod = paramCycleMethod;
/* 201 */     this.colorSpace = paramColorSpaceType;
/*     */ 
/*     */     
/* 204 */     this.fractions = paramArrayOffloat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     int[] arrayOfInt = (paramMultipleGradientPaint.gradient != null) ? paramMultipleGradientPaint.gradient.get() : null;
/*     */     
/* 212 */     int[][] arrayOfInt1 = (paramMultipleGradientPaint.gradients != null) ? paramMultipleGradientPaint.gradients.get() : (int[][])null;
/*     */     
/* 214 */     if (arrayOfInt == null && arrayOfInt1 == null) {
/*     */       
/* 216 */       calculateLookupData(paramArrayOfColor);
/*     */ 
/*     */ 
/*     */       
/* 220 */       paramMultipleGradientPaint.model = this.model;
/* 221 */       paramMultipleGradientPaint.normalizedIntervals = this.normalizedIntervals;
/* 222 */       paramMultipleGradientPaint.isSimpleLookup = this.isSimpleLookup;
/* 223 */       if (this.isSimpleLookup) {
/*     */         
/* 225 */         paramMultipleGradientPaint.fastGradientArraySize = this.fastGradientArraySize;
/* 226 */         paramMultipleGradientPaint.gradient = (SoftReference)new SoftReference<>(this.gradient);
/*     */       } else {
/*     */         
/* 229 */         paramMultipleGradientPaint.gradients = (SoftReference)new SoftReference<>(this.gradients);
/*     */       } 
/*     */     } else {
/*     */       
/* 233 */       this.model = paramMultipleGradientPaint.model;
/* 234 */       this.normalizedIntervals = paramMultipleGradientPaint.normalizedIntervals;
/* 235 */       this.isSimpleLookup = paramMultipleGradientPaint.isSimpleLookup;
/* 236 */       this.gradient = arrayOfInt;
/* 237 */       this.fastGradientArraySize = paramMultipleGradientPaint.fastGradientArraySize;
/* 238 */       this.gradients = arrayOfInt1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateLookupData(Color[] paramArrayOfColor) {
/*     */     Color[] arrayOfColor;
/* 249 */     if (this.colorSpace == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB) {
/*     */       
/* 251 */       arrayOfColor = new Color[paramArrayOfColor.length];
/*     */       
/* 253 */       for (byte b = 0; b < paramArrayOfColor.length; b++) {
/* 254 */         int j = paramArrayOfColor[b].getRGB();
/* 255 */         int k = j >>> 24;
/* 256 */         int m = SRGBtoLinearRGB[j >> 16 & 0xFF];
/* 257 */         int n = SRGBtoLinearRGB[j >> 8 & 0xFF];
/* 258 */         int i1 = SRGBtoLinearRGB[j & 0xFF];
/* 259 */         arrayOfColor[b] = new Color(m, n, i1, k);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 264 */       arrayOfColor = paramArrayOfColor;
/*     */     } 
/*     */ 
/*     */     
/* 268 */     this.normalizedIntervals = new float[this.fractions.length - 1];
/*     */ 
/*     */     
/* 271 */     for (byte b1 = 0; b1 < this.normalizedIntervals.length; b1++)
/*     */     {
/* 273 */       this.normalizedIntervals[b1] = this.fractions[b1 + 1] - this.fractions[b1];
/*     */     }
/*     */ 
/*     */     
/* 277 */     this.transparencyTest = -16777216;
/*     */ 
/*     */     
/* 280 */     this.gradients = new int[this.normalizedIntervals.length][];
/*     */ 
/*     */     
/* 283 */     float f = 1.0F; int i;
/* 284 */     for (i = 0; i < this.normalizedIntervals.length; i++) {
/* 285 */       f = (f > this.normalizedIntervals[i]) ? this.normalizedIntervals[i] : f;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     i = 0;
/* 295 */     for (byte b2 = 0; b2 < this.normalizedIntervals.length; b2++) {
/* 296 */       i = (int)(i + this.normalizedIntervals[b2] / f * 256.0F);
/*     */     }
/*     */     
/* 299 */     if (i > 5000) {
/*     */       
/* 301 */       calculateMultipleArrayGradient(arrayOfColor);
/*     */     } else {
/*     */       
/* 304 */       calculateSingleArrayGradient(arrayOfColor, f);
/*     */     } 
/*     */ 
/*     */     
/* 308 */     if (this.transparencyTest >>> 24 == 255) {
/* 309 */       this.model = xrgbmodel;
/*     */     } else {
/* 311 */       this.model = ColorModel.getRGBdefault();
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
/*     */   private void calculateSingleArrayGradient(Color[] paramArrayOfColor, float paramFloat) {
/* 340 */     this.isSimpleLookup = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 346 */     int i = 1;
/*     */     
/*     */     int j;
/* 349 */     for (j = 0; j < this.gradients.length; j++) {
/*     */ 
/*     */       
/* 352 */       int n = (int)(this.normalizedIntervals[j] / paramFloat * 255.0F);
/* 353 */       i += n;
/* 354 */       this.gradients[j] = new int[n];
/*     */ 
/*     */       
/* 357 */       int k = paramArrayOfColor[j].getRGB();
/* 358 */       int m = paramArrayOfColor[j + 1].getRGB();
/*     */ 
/*     */       
/* 361 */       interpolate(k, m, this.gradients[j]);
/*     */ 
/*     */ 
/*     */       
/* 365 */       this.transparencyTest &= k;
/* 366 */       this.transparencyTest &= m;
/*     */     } 
/*     */ 
/*     */     
/* 370 */     this.gradient = new int[i];
/* 371 */     j = 0; byte b;
/* 372 */     for (b = 0; b < this.gradients.length; b++) {
/* 373 */       System.arraycopy(this.gradients[b], 0, this.gradient, j, (this.gradients[b]).length);
/*     */       
/* 375 */       j += (this.gradients[b]).length;
/*     */     } 
/* 377 */     this.gradient[this.gradient.length - 1] = paramArrayOfColor[paramArrayOfColor.length - 1].getRGB();
/*     */ 
/*     */ 
/*     */     
/* 381 */     if (this.colorSpace == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB) {
/* 382 */       for (b = 0; b < this.gradient.length; b++) {
/* 383 */         this.gradient[b] = convertEntireColorLinearRGBtoSRGB(this.gradient[b]);
/*     */       }
/*     */     }
/*     */     
/* 387 */     this.fastGradientArraySize = this.gradient.length - 1;
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
/*     */   private void calculateMultipleArrayGradient(Color[] paramArrayOfColor) {
/* 410 */     this.isSimpleLookup = false;
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */     
/* 416 */     for (b = 0; b < this.gradients.length; b++) {
/*     */ 
/*     */       
/* 419 */       this.gradients[b] = new int[256];
/*     */ 
/*     */       
/* 422 */       int i = paramArrayOfColor[b].getRGB();
/* 423 */       int j = paramArrayOfColor[b + 1].getRGB();
/*     */ 
/*     */       
/* 426 */       interpolate(i, j, this.gradients[b]);
/*     */ 
/*     */ 
/*     */       
/* 430 */       this.transparencyTest &= i;
/* 431 */       this.transparencyTest &= j;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 436 */     if (this.colorSpace == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB) {
/* 437 */       for (b = 0; b < this.gradients.length; b++) {
/* 438 */         for (byte b1 = 0; b1 < (this.gradients[b]).length; b1++) {
/* 439 */           this.gradients[b][b1] = 
/* 440 */             convertEntireColorLinearRGBtoSRGB(this.gradients[b][b1]);
/*     */         }
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
/*     */   private void interpolate(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 459 */     float f = 1.0F / paramArrayOfint.length;
/*     */ 
/*     */     
/* 462 */     int i = paramInt1 >> 24 & 0xFF;
/* 463 */     int j = paramInt1 >> 16 & 0xFF;
/* 464 */     int k = paramInt1 >> 8 & 0xFF;
/* 465 */     int m = paramInt1 & 0xFF;
/*     */ 
/*     */     
/* 468 */     int n = (paramInt2 >> 24 & 0xFF) - i;
/* 469 */     int i1 = (paramInt2 >> 16 & 0xFF) - j;
/* 470 */     int i2 = (paramInt2 >> 8 & 0xFF) - k;
/* 471 */     int i3 = (paramInt2 & 0xFF) - m;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 476 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 477 */       paramArrayOfint[b] = (int)((i + (b * n) * f) + 0.5D) << 24 | (int)((j + (b * i1) * f) + 0.5D) << 16 | (int)((k + (b * i2) * f) + 0.5D) << 8 | (int)((m + (b * i3) * f) + 0.5D);
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
/*     */   private int convertEntireColorLinearRGBtoSRGB(int paramInt) {
/* 495 */     int i = paramInt >> 24 & 0xFF;
/* 496 */     int j = paramInt >> 16 & 0xFF;
/* 497 */     int k = paramInt >> 8 & 0xFF;
/* 498 */     int m = paramInt & 0xFF;
/*     */ 
/*     */     
/* 501 */     j = LinearRGBtoSRGB[j];
/* 502 */     k = LinearRGBtoSRGB[k];
/* 503 */     m = LinearRGBtoSRGB[m];
/*     */ 
/*     */     
/* 506 */     return i << 24 | j << 16 | k << 8 | m;
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
/*     */   protected final int indexIntoGradientsArrays(float paramFloat) {
/* 524 */     if (this.cycleMethod == MultipleGradientPaint.CycleMethod.NO_CYCLE) {
/* 525 */       if (paramFloat > 1.0F) {
/*     */         
/* 527 */         paramFloat = 1.0F;
/* 528 */       } else if (paramFloat < 0.0F) {
/*     */         
/* 530 */         paramFloat = 0.0F;
/*     */       } 
/* 532 */     } else if (this.cycleMethod == MultipleGradientPaint.CycleMethod.REPEAT) {
/*     */ 
/*     */       
/* 535 */       paramFloat -= (int)paramFloat;
/*     */ 
/*     */       
/* 538 */       if (paramFloat < 0.0F)
/*     */       {
/* 540 */         paramFloat++;
/*     */       }
/*     */     } else {
/* 543 */       if (paramFloat < 0.0F)
/*     */       {
/* 545 */         paramFloat = -paramFloat;
/*     */       }
/*     */ 
/*     */       
/* 549 */       int i = (int)paramFloat;
/*     */ 
/*     */       
/* 552 */       paramFloat -= i;
/*     */       
/* 554 */       if ((i & 0x1) == 1)
/*     */       {
/* 556 */         paramFloat = 1.0F - paramFloat;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 562 */     if (this.isSimpleLookup)
/*     */     {
/* 564 */       return this.gradient[(int)(paramFloat * this.fastGradientArraySize)];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 569 */     for (byte b = 0; b < this.gradients.length; b++) {
/* 570 */       if (paramFloat < this.fractions[b + 1]) {
/*     */         
/* 572 */         float f = paramFloat - this.fractions[b];
/*     */ 
/*     */         
/* 575 */         int i = (int)(f / this.normalizedIntervals[b] * 255.0F);
/*     */ 
/*     */         
/* 578 */         return this.gradients[b][i];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 583 */     return this.gradients[this.gradients.length - 1][255];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int convertSRGBtoLinearRGB(int paramInt) {
/* 593 */     float f2, f1 = paramInt / 255.0F;
/* 594 */     if (f1 <= 0.04045F) {
/* 595 */       f2 = f1 / 12.92F;
/*     */     } else {
/* 597 */       f2 = (float)Math.pow((f1 + 0.055D) / 1.055D, 2.4D);
/*     */     } 
/*     */     
/* 600 */     return Math.round(f2 * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int convertLinearRGBtoSRGB(int paramInt) {
/* 610 */     float f2, f1 = paramInt / 255.0F;
/* 611 */     if (f1 <= 0.0031308D) {
/* 612 */       f2 = f1 * 12.92F;
/*     */     } else {
/*     */       
/* 615 */       f2 = 1.055F * (float)Math.pow(f1, 0.4166666666666667D) - 0.055F;
/*     */     } 
/*     */     
/* 618 */     return Math.round(f2 * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 627 */     Raster raster = this.saved;
/* 628 */     if (raster == null || raster
/* 629 */       .getWidth() < paramInt3 || raster.getHeight() < paramInt4) {
/*     */       
/* 631 */       raster = getCachedRaster(this.model, paramInt3, paramInt4);
/* 632 */       this.saved = raster;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 642 */     DataBufferInt dataBufferInt = (DataBufferInt)raster.getDataBuffer();
/* 643 */     int[] arrayOfInt = dataBufferInt.getData(0);
/* 644 */     int i = dataBufferInt.getOffset();
/*     */     
/* 646 */     int j = ((SinglePixelPackedSampleModel)raster.getSampleModel()).getScanlineStride();
/* 647 */     int k = j - paramInt3;
/*     */     
/* 649 */     fillRaster(arrayOfInt, i, k, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 651 */     return raster;
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
/*     */   private static synchronized Raster getCachedRaster(ColorModel paramColorModel, int paramInt1, int paramInt2) {
/* 666 */     if (paramColorModel == cachedModel && 
/* 667 */       cached != null) {
/* 668 */       Raster raster = cached.get();
/* 669 */       if (raster != null && raster
/* 670 */         .getWidth() >= paramInt1 && raster
/* 671 */         .getHeight() >= paramInt2) {
/*     */         
/* 673 */         cached = null;
/* 674 */         return raster;
/*     */       } 
/*     */     } 
/*     */     
/* 678 */     return paramColorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void putCachedRaster(ColorModel paramColorModel, Raster paramRaster) {
/* 689 */     if (cached != null) {
/* 690 */       Raster raster = cached.get();
/* 691 */       if (raster != null) {
/* 692 */         int i = raster.getWidth();
/* 693 */         int j = raster.getHeight();
/* 694 */         int k = paramRaster.getWidth();
/* 695 */         int m = paramRaster.getHeight();
/* 696 */         if (i >= k && j >= m) {
/*     */           return;
/*     */         }
/* 699 */         if (i * j >= k * m) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 704 */     cachedModel = paramColorModel;
/* 705 */     cached = new WeakReference<>(paramRaster);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dispose() {
/* 712 */     if (this.saved != null) {
/* 713 */       putCachedRaster(this.model, this.saved);
/* 714 */       this.saved = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ColorModel getColorModel() {
/* 722 */     return this.model;
/*     */   }
/*     */   
/*     */   protected abstract void fillRaster(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/MultipleGradientPaintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */