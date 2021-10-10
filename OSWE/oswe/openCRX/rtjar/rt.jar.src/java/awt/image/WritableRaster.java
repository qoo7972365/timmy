/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WritableRaster
/*     */   extends Raster
/*     */ {
/*     */   protected WritableRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  65 */     this(paramSampleModel, paramSampleModel
/*  66 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/*  69 */           .getWidth(), paramSampleModel
/*  70 */           .getHeight()), paramPoint, (WritableRaster)null);
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
/*     */   protected WritableRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/*  91 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */ 
/*     */           
/*  95 */           .getWidth(), paramSampleModel
/*  96 */           .getHeight()), paramPoint, (WritableRaster)null);
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
/*     */   protected WritableRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, WritableRaster paramWritableRaster) {
/* 129 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramWritableRaster);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster getWritableParent() {
/* 138 */     return (WritableRaster)this.parent;
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
/*     */   public WritableRaster createWritableTranslatedChild(int paramInt1, int paramInt2) {
/* 158 */     return createWritableChild(this.minX, this.minY, this.width, this.height, paramInt1, paramInt2, (int[])null);
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
/*     */   public WritableRaster createWritableChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/*     */     SampleModel sampleModel;
/* 221 */     if (paramInt1 < this.minX) {
/* 222 */       throw new RasterFormatException("parentX lies outside raster");
/*     */     }
/* 224 */     if (paramInt2 < this.minY) {
/* 225 */       throw new RasterFormatException("parentY lies outside raster");
/*     */     }
/* 227 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.width + this.minX) {
/* 228 */       throw new RasterFormatException("(parentX + width) is outside raster");
/*     */     }
/* 230 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.height + this.minY) {
/* 231 */       throw new RasterFormatException("(parentY + height) is outside raster");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     if (paramArrayOfint != null) {
/* 241 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/*     */       
/* 244 */       sampleModel = this.sampleModel;
/*     */     } 
/*     */     
/* 247 */     int i = paramInt5 - paramInt1;
/* 248 */     int j = paramInt6 - paramInt2;
/*     */     
/* 250 */     return new WritableRaster(sampleModel, 
/* 251 */         getDataBuffer(), new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject) {
/* 283 */     this.sampleModel.setDataElements(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramObject, this.dataBuffer);
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Raster paramRaster) {
/* 307 */     int i = paramInt1 + paramRaster.getMinX();
/* 308 */     int j = paramInt2 + paramRaster.getMinY();
/* 309 */     int k = paramRaster.getWidth();
/* 310 */     int m = paramRaster.getHeight();
/* 311 */     if (i < this.minX || j < this.minY || i + k > this.minX + this.width || j + m > this.minY + this.height)
/*     */     {
/*     */       
/* 314 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 318 */     int n = paramRaster.getMinX();
/* 319 */     int i1 = paramRaster.getMinY();
/* 320 */     Object object = null;
/*     */     
/* 322 */     for (byte b = 0; b < m; b++) {
/* 323 */       object = paramRaster.getDataElements(n, i1 + b, k, 1, object);
/*     */       
/* 325 */       setDataElements(i, j + b, k, 1, object);
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/* 358 */     this.sampleModel.setDataElements(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramObject, this.dataBuffer);
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
/*     */   public void setRect(Raster paramRaster) {
/* 403 */     setRect(0, 0, paramRaster);
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
/*     */   public void setRect(int paramInt1, int paramInt2, Raster paramRaster) {
/*     */     int[] arrayOfInt;
/*     */     byte b1;
/*     */     float[] arrayOfFloat;
/*     */     byte b2;
/*     */     double[] arrayOfDouble;
/*     */     byte b3;
/* 425 */     int i = paramRaster.getWidth();
/* 426 */     int j = paramRaster.getHeight();
/* 427 */     int k = paramRaster.getMinX();
/* 428 */     int m = paramRaster.getMinY();
/* 429 */     int n = paramInt1 + k;
/* 430 */     int i1 = paramInt2 + m;
/*     */ 
/*     */     
/* 433 */     if (n < this.minX) {
/* 434 */       int i2 = this.minX - n;
/* 435 */       i -= i2;
/* 436 */       k += i2;
/* 437 */       n = this.minX;
/*     */     } 
/* 439 */     if (i1 < this.minY) {
/* 440 */       int i2 = this.minY - i1;
/* 441 */       j -= i2;
/* 442 */       m += i2;
/* 443 */       i1 = this.minY;
/*     */     } 
/* 445 */     if (n + i > this.minX + this.width) {
/* 446 */       i = this.minX + this.width - n;
/*     */     }
/* 448 */     if (i1 + j > this.minY + this.height) {
/* 449 */       j = this.minY + this.height - i1;
/*     */     }
/*     */     
/* 452 */     if (i <= 0 || j <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 456 */     switch (paramRaster.getSampleModel().getDataType()) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/* 461 */         arrayOfInt = null;
/* 462 */         for (b1 = 0; b1 < j; b1++) {
/*     */ 
/*     */           
/* 465 */           arrayOfInt = paramRaster.getPixels(k, m + b1, i, 1, arrayOfInt);
/*     */           
/* 467 */           setPixels(n, i1 + b1, i, 1, arrayOfInt);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 4:
/* 472 */         arrayOfFloat = null;
/* 473 */         for (b2 = 0; b2 < j; b2++) {
/*     */           
/* 475 */           arrayOfFloat = paramRaster.getPixels(k, m + b2, i, 1, arrayOfFloat);
/*     */           
/* 477 */           setPixels(n, i1 + b2, i, 1, arrayOfFloat);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 5:
/* 482 */         arrayOfDouble = null;
/* 483 */         for (b3 = 0; b3 < j; b3++) {
/*     */ 
/*     */           
/* 486 */           arrayOfDouble = paramRaster.getPixels(k, m + b3, i, 1, arrayOfDouble);
/*     */           
/* 488 */           setPixels(n, i1 + b3, i, 1, arrayOfDouble);
/*     */         } 
/*     */         break;
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
/*     */   public void setPixel(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 508 */     this.sampleModel.setPixel(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramArrayOfint, this.dataBuffer);
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
/*     */   public void setPixel(int paramInt1, int paramInt2, float[] paramArrayOffloat) {
/* 526 */     this.sampleModel.setPixel(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramArrayOffloat, this.dataBuffer);
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
/*     */   public void setPixel(int paramInt1, int paramInt2, double[] paramArrayOfdouble) {
/* 544 */     this.sampleModel.setPixel(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramArrayOfdouble, this.dataBuffer);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 565 */     this.sampleModel.setPixels(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramArrayOfint, this.dataBuffer);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat) {
/* 586 */     this.sampleModel.setPixels(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramArrayOffloat, this.dataBuffer);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble) {
/* 607 */     this.sampleModel.setPixels(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramArrayOfdouble, this.dataBuffer);
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
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 626 */     this.sampleModel.setSample(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, this.dataBuffer);
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
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
/* 646 */     this.sampleModel.setSample(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramFloat, this.dataBuffer);
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
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, double paramDouble) {
/* 665 */     this.sampleModel.setSample(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramDouble, this.dataBuffer);
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
/*     */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
/* 689 */     this.sampleModel.setSamples(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramInt5, paramArrayOfint, this.dataBuffer);
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
/*     */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[] paramArrayOffloat) {
/* 713 */     this.sampleModel.setSamples(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramInt5, paramArrayOffloat, this.dataBuffer);
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
/*     */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double[] paramArrayOfdouble) {
/* 737 */     this.sampleModel.setSamples(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramInt5, paramArrayOfdouble, this.dataBuffer);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/WritableRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */