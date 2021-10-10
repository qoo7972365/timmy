/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ComponentSampleModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferUShort;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.RasterFormatException;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShortInterleavedRaster
/*     */   extends ShortComponentRaster
/*     */ {
/*     */   private int maxX;
/*     */   private int maxY;
/*     */   
/*     */   public ShortInterleavedRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  73 */     this(paramSampleModel, paramSampleModel
/*  74 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/*  77 */           .getWidth(), paramSampleModel
/*  78 */           .getHeight()), paramPoint, (ShortInterleavedRaster)null);
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
/*     */   public ShortInterleavedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/*  97 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */ 
/*     */           
/* 101 */           .getWidth(), paramSampleModel
/* 102 */           .getHeight()), paramPoint, (ShortInterleavedRaster)null);
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
/*     */   public ShortInterleavedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, ShortInterleavedRaster paramShortInterleavedRaster) {
/* 131 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramShortInterleavedRaster);
/* 132 */     this.maxX = this.minX + this.width;
/* 133 */     this.maxY = this.minY + this.height;
/*     */     
/* 135 */     if (!(paramDataBuffer instanceof DataBufferUShort)) {
/* 136 */       throw new RasterFormatException("ShortInterleavedRasters must have ushort DataBuffers");
/*     */     }
/*     */ 
/*     */     
/* 140 */     DataBufferUShort dataBufferUShort = (DataBufferUShort)paramDataBuffer;
/* 141 */     this.data = stealData(dataBufferUShort, 0);
/*     */ 
/*     */     
/* 144 */     if (paramSampleModel instanceof java.awt.image.PixelInterleavedSampleModel || (paramSampleModel instanceof ComponentSampleModel && paramSampleModel
/*     */       
/* 146 */       .getNumBands() == 1)) {
/* 147 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramSampleModel;
/*     */       
/* 149 */       this.scanlineStride = componentSampleModel.getScanlineStride();
/* 150 */       this.pixelStride = componentSampleModel.getPixelStride();
/* 151 */       this.dataOffsets = componentSampleModel.getBandOffsets();
/* 152 */       int i = paramRectangle.x - paramPoint.x;
/* 153 */       int j = paramRectangle.y - paramPoint.y;
/* 154 */       for (byte b = 0; b < getNumDataElements(); b++) {
/* 155 */         this.dataOffsets[b] = this.dataOffsets[b] + i * this.pixelStride + j * this.scanlineStride;
/*     */       }
/* 157 */     } else if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/* 158 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*     */       
/* 160 */       this.scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
/* 161 */       this.pixelStride = 1;
/* 162 */       this.dataOffsets = new int[1];
/* 163 */       this.dataOffsets[0] = dataBufferUShort.getOffset();
/* 164 */       int i = paramRectangle.x - paramPoint.x;
/* 165 */       int j = paramRectangle.y - paramPoint.y;
/* 166 */       this.dataOffsets[0] = this.dataOffsets[0] + i + j * this.scanlineStride;
/*     */     } else {
/* 168 */       throw new RasterFormatException("ShortInterleavedRasters must have PixelInterleavedSampleModel, SinglePixelPackedSampleModel or 1 band ComponentSampleModel.  Sample model is " + paramSampleModel);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 173 */     this.bandOffset = this.dataOffsets[0];
/* 174 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataOffsets() {
/* 183 */     return (int[])this.dataOffsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataOffset(int paramInt) {
/* 193 */     return this.dataOffsets[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScanlineStride() {
/* 201 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelStride() {
/* 209 */     return this.pixelStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getDataStorage() {
/* 216 */     return this.data;
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
/*     */   public Object getDataElements(int paramInt1, int paramInt2, Object paramObject) {
/*     */     short[] arrayOfShort;
/* 236 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 238 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 242 */     if (paramObject == null) {
/* 243 */       arrayOfShort = new short[this.numDataElements];
/*     */     } else {
/* 245 */       arrayOfShort = (short[])paramObject;
/*     */     } 
/* 247 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 250 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 251 */       arrayOfShort[b] = this.data[this.dataOffsets[b] + i];
/*     */     }
/*     */     
/* 254 */     return arrayOfShort;
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
/*     */   public Object getDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*     */     short[] arrayOfShort;
/* 284 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 286 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 290 */     if (paramObject == null) {
/* 291 */       arrayOfShort = new short[paramInt3 * paramInt4 * this.numDataElements];
/*     */     } else {
/* 293 */       arrayOfShort = (short[])paramObject;
/*     */     } 
/* 295 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */ 
/*     */     
/* 299 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 303 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 304 */       int j = i;
/* 305 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 306 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 307 */           arrayOfShort[b1++] = this.data[this.dataOffsets[b3] + j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 312 */     return arrayOfShort;
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
/*     */   public short[] getShortData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, short[] paramArrayOfshort) {
/* 337 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 339 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 342 */     if (paramArrayOfshort == null) {
/* 343 */       paramArrayOfshort = new short[this.numDataElements * paramInt3 * paramInt4];
/*     */     }
/* 345 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*     */ 
/*     */     
/* 348 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 352 */     if (this.pixelStride == 1) {
/* 353 */       if (this.scanlineStride == paramInt3) {
/* 354 */         System.arraycopy(this.data, i, paramArrayOfshort, 0, paramInt3 * paramInt4);
/*     */       } else {
/*     */         
/* 357 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 358 */           System.arraycopy(this.data, i, paramArrayOfshort, j, paramInt3);
/* 359 */           j += paramInt3;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 364 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 365 */         int k = i;
/* 366 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 367 */           paramArrayOfshort[j++] = this.data[k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 372 */     return paramArrayOfshort;
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
/*     */   public short[] getShortData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short[] paramArrayOfshort) {
/* 397 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 399 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 402 */     if (paramArrayOfshort == null) {
/* 403 */       paramArrayOfshort = new short[this.numDataElements * paramInt3 * paramInt4];
/*     */     }
/* 405 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 408 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 412 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 413 */       int j = i;
/* 414 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 415 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 416 */           paramArrayOfshort[b1++] = this.data[this.dataOffsets[b3] + j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 421 */     return paramArrayOfshort;
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject) {
/* 437 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 439 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 442 */     short[] arrayOfShort = (short[])paramObject;
/* 443 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */     
/* 445 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 446 */       this.data[this.dataOffsets[b] + i] = arrayOfShort[b];
/*     */     }
/* 448 */     markDirty();
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Raster paramRaster) {
/* 460 */     int i = paramInt1 + paramRaster.getMinX();
/* 461 */     int j = paramInt2 + paramRaster.getMinY();
/* 462 */     int k = paramRaster.getWidth();
/* 463 */     int m = paramRaster.getHeight();
/* 464 */     if (i < this.minX || j < this.minY || i + k > this.maxX || j + m > this.maxY)
/*     */     {
/* 466 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 470 */     setDataElements(i, j, k, m, paramRaster);
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
/*     */   private void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Raster paramRaster) {
/* 489 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 495 */     int i = paramRaster.getMinX();
/* 496 */     int j = paramRaster.getMinY();
/* 497 */     Object object = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 503 */     for (byte b = 0; b < paramInt4; b++) {
/*     */       
/* 505 */       object = paramRaster.getDataElements(i, j + b, paramInt3, 1, object);
/*     */       
/* 507 */       setDataElements(paramInt1, paramInt2 + b, paramInt3, 1, object);
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/* 534 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 536 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 539 */     short[] arrayOfShort = (short[])paramObject;
/* 540 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 543 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 547 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 548 */       int j = i;
/* 549 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 550 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 551 */           this.data[this.dataOffsets[b3] + j] = arrayOfShort[b1++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 556 */     markDirty();
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
/*     */   public void putShortData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, short[] paramArrayOfshort) {
/* 580 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 582 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 585 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*     */ 
/*     */     
/* 588 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 592 */     if (this.pixelStride == 1) {
/* 593 */       if (this.scanlineStride == paramInt3) {
/* 594 */         System.arraycopy(paramArrayOfshort, 0, this.data, i, paramInt3 * paramInt4);
/*     */       } else {
/*     */         
/* 597 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 598 */           System.arraycopy(paramArrayOfshort, j, this.data, i, paramInt3);
/* 599 */           j += paramInt3;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 604 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 605 */         int k = i;
/* 606 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 607 */           this.data[k] = paramArrayOfshort[j++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 612 */     markDirty();
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
/*     */   public void putShortData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short[] paramArrayOfshort) {
/* 633 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 635 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 638 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 641 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 645 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 646 */       int j = i;
/* 647 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 648 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 649 */           this.data[this.dataOffsets[b3] + j] = paramArrayOfshort[b1++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 654 */     markDirty();
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
/*     */   public Raster createChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/* 679 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
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
/*     */   public WritableRaster createWritableChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/*     */     SampleModel sampleModel;
/* 709 */     if (paramInt1 < this.minX) {
/* 710 */       throw new RasterFormatException("x lies outside the raster");
/*     */     }
/* 712 */     if (paramInt2 < this.minY) {
/* 713 */       throw new RasterFormatException("y lies outside the raster");
/*     */     }
/* 715 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 716 */       throw new RasterFormatException("(x + width) is outside of Raster");
/*     */     }
/* 718 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 719 */       throw new RasterFormatException("(y + height) is outside of Raster");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 724 */     if (paramArrayOfint != null) {
/* 725 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/* 727 */       sampleModel = this.sampleModel;
/*     */     } 
/* 729 */     int i = paramInt5 - paramInt1;
/* 730 */     int j = paramInt6 - paramInt2;
/*     */     
/* 732 */     return new ShortInterleavedRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/*     */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 745 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 746 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*     */     }
/*     */ 
/*     */     
/* 750 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*     */     
/* 752 */     return new ShortInterleavedRaster(sampleModel, new Point(0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster() {
/* 762 */     return createCompatibleWritableRaster(this.width, this.height);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 766 */     return new String("ShortInterleavedRaster: width = " + this.width + " height = " + this.height + " #numDataElements " + this.numDataElements);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ShortInterleavedRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */