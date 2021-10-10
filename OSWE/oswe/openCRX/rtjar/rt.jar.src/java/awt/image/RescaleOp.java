/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import sun.awt.image.ImagingLib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RescaleOp
/*     */   implements BufferedImageOp, RasterOp
/*     */ {
/*     */   float[] scaleFactors;
/*     */   float[] offsets;
/*  86 */   int length = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RenderingHints hints;
/*     */ 
/*     */ 
/*     */   
/*     */   private int srcNbits;
/*     */ 
/*     */ 
/*     */   
/*     */   private int dstNbits;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RescaleOp(float[] paramArrayOffloat1, float[] paramArrayOffloat2, RenderingHints paramRenderingHints) {
/* 105 */     this.length = paramArrayOffloat1.length;
/* 106 */     if (this.length > paramArrayOffloat2.length) this.length = paramArrayOffloat2.length;
/*     */     
/* 108 */     this.scaleFactors = new float[this.length];
/* 109 */     this.offsets = new float[this.length];
/* 110 */     for (byte b = 0; b < this.length; b++) {
/* 111 */       this.scaleFactors[b] = paramArrayOffloat1[b];
/* 112 */       this.offsets[b] = paramArrayOffloat2[b];
/*     */     } 
/* 114 */     this.hints = paramRenderingHints;
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
/*     */   public RescaleOp(float paramFloat1, float paramFloat2, RenderingHints paramRenderingHints) {
/* 129 */     this.length = 1;
/* 130 */     this.scaleFactors = new float[1];
/* 131 */     this.offsets = new float[1];
/* 132 */     this.scaleFactors[0] = paramFloat1;
/* 133 */     this.offsets[0] = paramFloat2;
/* 134 */     this.hints = paramRenderingHints;
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
/*     */   public final float[] getScaleFactors(float[] paramArrayOffloat) {
/* 146 */     if (paramArrayOffloat == null) {
/* 147 */       return (float[])this.scaleFactors.clone();
/*     */     }
/* 149 */     System.arraycopy(this.scaleFactors, 0, paramArrayOffloat, 0, 
/* 150 */         Math.min(this.scaleFactors.length, paramArrayOffloat.length));
/*     */     
/* 152 */     return paramArrayOffloat;
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
/*     */   public final float[] getOffsets(float[] paramArrayOffloat) {
/* 164 */     if (paramArrayOffloat == null) {
/* 165 */       return (float[])this.offsets.clone();
/*     */     }
/*     */     
/* 168 */     System.arraycopy(this.offsets, 0, paramArrayOffloat, 0, 
/* 169 */         Math.min(this.offsets.length, paramArrayOffloat.length));
/* 170 */     return paramArrayOffloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getNumFactors() {
/* 180 */     return this.length;
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
/*     */   private ByteLookupTable createByteLut(float[] paramArrayOffloat1, float[] paramArrayOffloat2, int paramInt1, int paramInt2) {
/* 196 */     byte[][] arrayOfByte = new byte[paramArrayOffloat1.length][paramInt2];
/*     */     
/* 198 */     for (byte b = 0; b < paramArrayOffloat1.length; b++) {
/* 199 */       float f1 = paramArrayOffloat1[b];
/* 200 */       float f2 = paramArrayOffloat2[b];
/* 201 */       byte[] arrayOfByte1 = arrayOfByte[b];
/* 202 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 203 */         int i = (int)(b1 * f1 + f2);
/* 204 */         if ((i & 0xFFFFFF00) != 0) {
/* 205 */           if (i < 0) {
/* 206 */             i = 0;
/*     */           } else {
/* 208 */             i = 255;
/*     */           } 
/*     */         }
/* 211 */         arrayOfByte1[b1] = (byte)i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 216 */     return new ByteLookupTable(0, arrayOfByte);
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
/*     */   private ShortLookupTable createShortLut(float[] paramArrayOffloat1, float[] paramArrayOffloat2, int paramInt1, int paramInt2) {
/* 231 */     short[][] arrayOfShort = new short[paramArrayOffloat1.length][paramInt2];
/*     */     
/* 233 */     for (byte b = 0; b < paramArrayOffloat1.length; b++) {
/* 234 */       float f1 = paramArrayOffloat1[b];
/* 235 */       float f2 = paramArrayOffloat2[b];
/* 236 */       short[] arrayOfShort1 = arrayOfShort[b];
/* 237 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 238 */         int i = (int)(b1 * f1 + f2);
/* 239 */         if ((i & 0xFFFF0000) != 0) {
/* 240 */           if (i < 0) {
/* 241 */             i = 0;
/*     */           } else {
/* 243 */             i = 65535;
/*     */           } 
/*     */         }
/* 246 */         arrayOfShort1[b1] = (short)i;
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     return new ShortLookupTable(0, arrayOfShort);
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
/*     */   private boolean canUseLookup(Raster paramRaster1, Raster paramRaster2) {
/* 266 */     int i = paramRaster1.getDataBuffer().getDataType();
/* 267 */     if (i != 0 && i != 1)
/*     */     {
/* 269 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     SampleModel sampleModel1 = paramRaster2.getSampleModel();
/* 276 */     this.dstNbits = sampleModel1.getSampleSize(0);
/*     */     
/* 278 */     if (this.dstNbits != 8 && this.dstNbits != 16) {
/* 279 */       return false;
/*     */     }
/* 281 */     for (byte b1 = 1; b1 < paramRaster1.getNumBands(); b1++) {
/* 282 */       int j = sampleModel1.getSampleSize(b1);
/* 283 */       if (j != this.dstNbits) {
/* 284 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     SampleModel sampleModel2 = paramRaster1.getSampleModel();
/* 292 */     this.srcNbits = sampleModel2.getSampleSize(0);
/* 293 */     if (this.srcNbits > 16) {
/* 294 */       return false;
/*     */     }
/* 296 */     for (byte b2 = 1; b2 < paramRaster1.getNumBands(); b2++) {
/* 297 */       int j = sampleModel2.getSampleSize(b2);
/* 298 */       if (j != this.srcNbits) {
/* 299 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 303 */     return true;
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
/*     */   public final BufferedImage filter(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2) {
/* 327 */     ColorModel colorModel2, colorModel1 = paramBufferedImage1.getColorModel();
/*     */     
/* 329 */     int i = colorModel1.getNumColorComponents();
/*     */ 
/*     */     
/* 332 */     if (colorModel1 instanceof IndexColorModel) {
/* 333 */       throw new IllegalArgumentException("Rescaling cannot be performed on an indexed image");
/*     */     }
/*     */ 
/*     */     
/* 337 */     if (this.length != 1 && this.length != i && this.length != colorModel1
/* 338 */       .getNumComponents())
/*     */     {
/* 340 */       throw new IllegalArgumentException("Number of scaling constants does not equal the number of of color or color/alpha  components");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 346 */     boolean bool = false;
/*     */ 
/*     */     
/* 349 */     if (this.length > i && colorModel1.hasAlpha()) {
/* 350 */       this.length = i + 1;
/*     */     }
/*     */     
/* 353 */     int j = paramBufferedImage1.getWidth();
/* 354 */     int k = paramBufferedImage1.getHeight();
/*     */     
/* 356 */     if (paramBufferedImage2 == null) {
/* 357 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 358 */       colorModel2 = colorModel1;
/*     */     } else {
/*     */       
/* 361 */       if (j != paramBufferedImage2.getWidth()) {
/* 362 */         throw new IllegalArgumentException("Src width (" + j + ") not equal to dst width (" + paramBufferedImage2
/*     */ 
/*     */             
/* 365 */             .getWidth() + ")");
/*     */       }
/* 367 */       if (k != paramBufferedImage2.getHeight()) {
/* 368 */         throw new IllegalArgumentException("Src height (" + k + ") not equal to dst height (" + paramBufferedImage2
/*     */ 
/*     */             
/* 371 */             .getHeight() + ")");
/*     */       }
/*     */       
/* 374 */       colorModel2 = paramBufferedImage2.getColorModel();
/* 375 */       if (colorModel1.getColorSpace().getType() != colorModel2
/* 376 */         .getColorSpace().getType()) {
/* 377 */         bool = true;
/* 378 */         paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 383 */     BufferedImage bufferedImage = paramBufferedImage2;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     if (ImagingLib.filter(this, paramBufferedImage1, paramBufferedImage2) == null) {
/*     */ 
/*     */ 
/*     */       
/* 392 */       WritableRaster writableRaster1 = paramBufferedImage1.getRaster();
/* 393 */       WritableRaster writableRaster2 = paramBufferedImage2.getRaster();
/*     */       
/* 395 */       if (colorModel1.hasAlpha() && (
/* 396 */         i - 1 == this.length || this.length == 1)) {
/* 397 */         int m = writableRaster1.getMinX();
/* 398 */         int n = writableRaster1.getMinY();
/* 399 */         int[] arrayOfInt = new int[i - 1];
/* 400 */         for (byte b = 0; b < i - 1; b++) {
/* 401 */           arrayOfInt[b] = b;
/*     */         }
/*     */         
/* 404 */         writableRaster1 = writableRaster1.createWritableChild(m, n, writableRaster1
/* 405 */             .getWidth(), writableRaster1
/* 406 */             .getHeight(), m, n, arrayOfInt);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 411 */       if (colorModel2.hasAlpha()) {
/* 412 */         int m = writableRaster2.getNumBands();
/* 413 */         if (m - 1 == this.length || this.length == 1) {
/* 414 */           int n = writableRaster2.getMinX();
/* 415 */           int i1 = writableRaster2.getMinY();
/* 416 */           int[] arrayOfInt = new int[i - 1];
/* 417 */           for (byte b = 0; b < i - 1; b++) {
/* 418 */             arrayOfInt[b] = b;
/*     */           }
/*     */           
/* 421 */           writableRaster2 = writableRaster2.createWritableChild(n, i1, writableRaster2
/* 422 */               .getWidth(), writableRaster2
/* 423 */               .getHeight(), n, i1, arrayOfInt);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 432 */       filter(writableRaster1, writableRaster2);
/*     */     } 
/*     */ 
/*     */     
/* 436 */     if (bool) {
/*     */       
/* 438 */       ColorConvertOp colorConvertOp = new ColorConvertOp(this.hints);
/* 439 */       colorConvertOp.filter(paramBufferedImage2, bufferedImage);
/*     */     } 
/*     */     
/* 442 */     return bufferedImage;
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
/*     */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster) {
/* 464 */     int i = paramRaster.getNumBands();
/* 465 */     int j = paramRaster.getWidth();
/* 466 */     int k = paramRaster.getHeight();
/* 467 */     int[] arrayOfInt = null;
/* 468 */     byte b = 0;
/* 469 */     int m = 0;
/*     */ 
/*     */     
/* 472 */     if (paramWritableRaster == null) {
/* 473 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*     */     } else {
/* 475 */       if (k != paramWritableRaster.getHeight() || j != paramWritableRaster.getWidth()) {
/* 476 */         throw new IllegalArgumentException("Width or height of Rasters do not match");
/*     */       }
/*     */ 
/*     */       
/* 480 */       if (i != paramWritableRaster.getNumBands())
/*     */       {
/* 482 */         throw new IllegalArgumentException("Number of bands in src " + i + " does not equal number of bands in dest " + paramWritableRaster
/*     */ 
/*     */             
/* 485 */             .getNumBands());
/*     */       }
/*     */     } 
/*     */     
/* 489 */     if (this.length != 1 && this.length != paramRaster.getNumBands()) {
/* 490 */       throw new IllegalArgumentException("Number of scaling constants does not equal the number of of bands in the src raster");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 499 */     if (ImagingLib.filter(this, paramRaster, paramWritableRaster) != null) {
/* 500 */       return paramWritableRaster;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     if (canUseLookup(paramRaster, paramWritableRaster)) {
/* 508 */       int n = 1 << this.srcNbits;
/* 509 */       int i1 = 1 << this.dstNbits;
/*     */       
/* 511 */       if (i1 == 256) {
/* 512 */         ByteLookupTable byteLookupTable = createByteLut(this.scaleFactors, this.offsets, i, n);
/*     */         
/* 514 */         LookupOp lookupOp = new LookupOp(byteLookupTable, this.hints);
/* 515 */         lookupOp.filter(paramRaster, paramWritableRaster);
/*     */       } else {
/* 517 */         ShortLookupTable shortLookupTable = createShortLut(this.scaleFactors, this.offsets, i, n);
/*     */         
/* 519 */         LookupOp lookupOp = new LookupOp(shortLookupTable, this.hints);
/* 520 */         lookupOp.filter(paramRaster, paramWritableRaster);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 526 */       if (this.length > 1) {
/* 527 */         b = 1;
/*     */       }
/*     */       
/* 530 */       int n = paramRaster.getMinX();
/* 531 */       int i1 = paramRaster.getMinY();
/* 532 */       int i2 = paramWritableRaster.getMinX();
/* 533 */       int i3 = paramWritableRaster.getMinY();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 543 */       int[] arrayOfInt1 = new int[i];
/* 544 */       int[] arrayOfInt2 = new int[i];
/* 545 */       SampleModel sampleModel = paramWritableRaster.getSampleModel(); int i4;
/* 546 */       for (i4 = 0; i4 < i; i4++) {
/* 547 */         int i5 = sampleModel.getSampleSize(i4);
/* 548 */         arrayOfInt1[i4] = (1 << i5) - 1;
/* 549 */         arrayOfInt2[i4] = arrayOfInt1[i4] ^ 0xFFFFFFFF;
/*     */       } 
/*     */ 
/*     */       
/* 553 */       for (byte b1 = 0; b1 < k; b1++, i1++, i3++) {
/* 554 */         int i6 = i2;
/* 555 */         int i5 = n;
/* 556 */         for (byte b2 = 0; b2 < j; b2++, i5++, i6++) {
/*     */           
/* 558 */           arrayOfInt = paramRaster.getPixel(i5, i1, arrayOfInt);
/* 559 */           m = 0;
/* 560 */           for (byte b3 = 0; b3 < i; b3++, m += b) {
/* 561 */             i4 = (int)(arrayOfInt[b3] * this.scaleFactors[m] + this.offsets[m]);
/*     */ 
/*     */             
/* 564 */             if ((i4 & arrayOfInt2[b3]) != 0) {
/* 565 */               if (i4 < 0) {
/* 566 */                 i4 = 0;
/*     */               } else {
/* 568 */                 i4 = arrayOfInt1[b3];
/*     */               } 
/*     */             }
/* 571 */             arrayOfInt[b3] = i4;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 576 */           paramWritableRaster.setPixel(i6, i3, arrayOfInt);
/*     */         } 
/*     */       } 
/*     */     } 
/* 580 */     return paramWritableRaster;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage) {
/* 589 */     return getBounds2D(paramBufferedImage.getRaster());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Rectangle2D getBounds2D(Raster paramRaster) {
/* 600 */     return paramRaster.getBounds();
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
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel) {
/*     */     BufferedImage bufferedImage;
/* 614 */     if (paramColorModel == null) {
/* 615 */       ColorModel colorModel = paramBufferedImage.getColorModel();
/*     */ 
/*     */       
/* 618 */       bufferedImage = new BufferedImage(colorModel, paramBufferedImage.getRaster().createCompatibleWritableRaster(), colorModel.isAlphaPremultiplied(), null);
/*     */     }
/*     */     else {
/*     */       
/* 622 */       int i = paramBufferedImage.getWidth();
/* 623 */       int j = paramBufferedImage.getHeight();
/*     */ 
/*     */       
/* 626 */       bufferedImage = new BufferedImage(paramColorModel, paramColorModel.createCompatibleWritableRaster(i, j), paramColorModel.isAlphaPremultiplied(), null);
/*     */     } 
/*     */     
/* 629 */     return bufferedImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleDestRaster(Raster paramRaster) {
/* 639 */     return paramRaster.createCompatibleWritableRaster(paramRaster.getWidth(), paramRaster.getHeight());
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
/*     */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 652 */     if (paramPoint2D2 == null) {
/* 653 */       paramPoint2D2 = new Point2D.Float();
/*     */     }
/* 655 */     paramPoint2D2.setLocation(paramPoint2D1.getX(), paramPoint2D1.getY());
/* 656 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderingHints getRenderingHints() {
/* 664 */     return this.hints;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/RescaleOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */