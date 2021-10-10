/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BandedSampleModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferUShort;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.RasterFormatException;
/*     */ import java.awt.image.SampleModel;
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
/*     */ 
/*     */ 
/*     */ public class ShortBandedRaster
/*     */   extends SunWritableRaster
/*     */ {
/*     */   int[] dataOffsets;
/*     */   int scanlineStride;
/*     */   short[][] data;
/*     */   private int maxX;
/*     */   private int maxY;
/*     */   
/*     */   public ShortBandedRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  77 */     this(paramSampleModel, paramSampleModel
/*  78 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/*  81 */           .getWidth(), paramSampleModel
/*  82 */           .getHeight()), paramPoint, (ShortBandedRaster)null);
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
/*     */   public ShortBandedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/* 100 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */           
/* 102 */           .getWidth(), paramSampleModel
/* 103 */           .getHeight()), paramPoint, (ShortBandedRaster)null);
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
/*     */   public ShortBandedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, ShortBandedRaster paramShortBandedRaster) {
/* 131 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramShortBandedRaster);
/* 132 */     this.maxX = this.minX + this.width;
/* 133 */     this.maxY = this.minY + this.height;
/* 134 */     if (!(paramDataBuffer instanceof DataBufferUShort)) {
/* 135 */       throw new RasterFormatException("ShortBandedRaster must have ushort DataBuffers");
/*     */     }
/*     */     
/* 138 */     DataBufferUShort dataBufferUShort = (DataBufferUShort)paramDataBuffer;
/*     */     
/* 140 */     if (paramSampleModel instanceof BandedSampleModel) {
/* 141 */       BandedSampleModel bandedSampleModel = (BandedSampleModel)paramSampleModel;
/* 142 */       this.scanlineStride = bandedSampleModel.getScanlineStride();
/* 143 */       int[] arrayOfInt1 = bandedSampleModel.getBankIndices();
/* 144 */       int[] arrayOfInt2 = bandedSampleModel.getBandOffsets();
/* 145 */       int[] arrayOfInt3 = dataBufferUShort.getOffsets();
/* 146 */       this.dataOffsets = new int[arrayOfInt1.length];
/* 147 */       this.data = new short[arrayOfInt1.length][];
/* 148 */       int i = paramRectangle.x - paramPoint.x;
/* 149 */       int j = paramRectangle.y - paramPoint.y;
/* 150 */       for (byte b = 0; b < arrayOfInt1.length; b++) {
/* 151 */         this.data[b] = stealData(dataBufferUShort, arrayOfInt1[b]);
/* 152 */         this.dataOffsets[b] = arrayOfInt3[arrayOfInt1[b]] + i + j * this.scanlineStride + arrayOfInt2[b];
/*     */       } 
/*     */     } else {
/*     */       
/* 156 */       throw new RasterFormatException("ShortBandedRasters must have BandedSampleModels");
/*     */     } 
/*     */     
/* 159 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataOffsets() {
/* 168 */     return (int[])this.dataOffsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataOffset(int paramInt) {
/* 178 */     return this.dataOffsets[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScanlineStride() {
/* 187 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelStride() {
/* 195 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[][] getDataStorage() {
/* 202 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getDataStorage(int paramInt) {
/* 209 */     return this.data[paramInt];
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
/* 229 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 231 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 235 */     if (paramObject == null) {
/* 236 */       arrayOfShort = new short[this.numDataElements];
/*     */     } else {
/* 238 */       arrayOfShort = (short[])paramObject;
/*     */     } 
/*     */     
/* 241 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 243 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 244 */       arrayOfShort[b] = this.data[b][this.dataOffsets[b] + i];
/*     */     }
/*     */     
/* 247 */     return arrayOfShort;
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
/*     */   public Object getDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*     */     short[] arrayOfShort;
/* 275 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 277 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 281 */     if (paramObject == null) {
/* 282 */       arrayOfShort = new short[this.numDataElements * paramInt3 * paramInt4];
/*     */     } else {
/* 284 */       arrayOfShort = (short[])paramObject;
/*     */     } 
/* 286 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 288 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 289 */       int j = b;
/* 290 */       short[] arrayOfShort1 = this.data[b];
/* 291 */       int k = this.dataOffsets[b];
/*     */       
/* 293 */       int m = i;
/* 294 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 295 */         int n = k + m;
/* 296 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 297 */           arrayOfShort[j] = arrayOfShort1[n++];
/* 298 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     return arrayOfShort;
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
/* 328 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 330 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 333 */     if (paramArrayOfshort == null) {
/* 334 */       paramArrayOfshort = new short[this.scanlineStride * paramInt4];
/*     */     }
/* 336 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[paramInt5];
/*     */     
/* 338 */     if (this.scanlineStride == paramInt3) {
/* 339 */       System.arraycopy(this.data[paramInt5], i, paramArrayOfshort, 0, paramInt3 * paramInt4);
/*     */     } else {
/* 341 */       int j = 0;
/* 342 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 343 */         System.arraycopy(this.data[paramInt5], i, paramArrayOfshort, j, paramInt3);
/* 344 */         j += paramInt3;
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     return paramArrayOfshort;
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
/*     */   public short[] getShortData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short[] paramArrayOfshort) {
/* 374 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 376 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 379 */     if (paramArrayOfshort == null) {
/* 380 */       paramArrayOfshort = new short[this.numDataElements * this.scanlineStride * paramInt4];
/*     */     }
/* 382 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 384 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 385 */       int j = b;
/* 386 */       short[] arrayOfShort = this.data[b];
/* 387 */       int k = this.dataOffsets[b];
/*     */       
/* 389 */       int m = i;
/* 390 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 391 */         int n = k + m;
/* 392 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 393 */           paramArrayOfshort[j] = arrayOfShort[n++];
/* 394 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 399 */     return paramArrayOfshort;
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
/* 415 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 417 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 420 */     short[] arrayOfShort = (short[])paramObject;
/* 421 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/* 422 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 423 */       this.data[b][this.dataOffsets[b] + i] = arrayOfShort[b];
/*     */     }
/*     */     
/* 426 */     markDirty();
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
/* 438 */     int i = paramInt1 + paramRaster.getMinX();
/* 439 */     int j = paramInt2 + paramRaster.getMinY();
/* 440 */     int k = paramRaster.getWidth();
/* 441 */     int m = paramRaster.getHeight();
/* 442 */     if (i < this.minX || j < this.minY || i + k > this.maxX || j + m > this.maxY)
/*     */     {
/* 444 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 448 */     setDataElements(i, j, k, m, paramRaster);
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
/* 467 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 473 */     int i = paramRaster.getMinX();
/* 474 */     int j = paramRaster.getMinY();
/* 475 */     Object object = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     for (byte b = 0; b < paramInt4; b++) {
/*     */       
/* 483 */       object = paramRaster.getDataElements(i, j + b, paramInt3, 1, object);
/*     */       
/* 485 */       setDataElements(paramInt1, paramInt2 + b, paramInt3, 1, object);
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
/* 512 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 514 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 517 */     short[] arrayOfShort = (short[])paramObject;
/* 518 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 520 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 521 */       int j = b;
/* 522 */       short[] arrayOfShort1 = this.data[b];
/* 523 */       int k = this.dataOffsets[b];
/*     */       
/* 525 */       int m = i;
/* 526 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 527 */         int n = k + m;
/* 528 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 529 */           arrayOfShort1[n++] = arrayOfShort[j];
/* 530 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 535 */     markDirty();
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
/* 559 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 561 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 564 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[paramInt5];
/*     */     
/* 566 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 570 */     if (this.scanlineStride == paramInt3) {
/* 571 */       System.arraycopy(paramArrayOfshort, 0, this.data[paramInt5], i, paramInt3 * paramInt4);
/*     */     } else {
/* 573 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 574 */         System.arraycopy(paramArrayOfshort, j, this.data[paramInt5], i, paramInt3);
/* 575 */         j += paramInt3;
/*     */       } 
/*     */     } 
/*     */     
/* 579 */     markDirty();
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
/* 600 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 602 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 605 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 607 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 608 */       int j = b;
/* 609 */       short[] arrayOfShort = this.data[b];
/* 610 */       int k = this.dataOffsets[b];
/*     */       
/* 612 */       int m = i;
/* 613 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 614 */         int n = k + m;
/* 615 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 616 */           arrayOfShort[n++] = paramArrayOfshort[j];
/* 617 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 622 */     markDirty();
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
/*     */   public WritableRaster createWritableChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/*     */     SampleModel sampleModel;
/* 649 */     if (paramInt1 < this.minX) {
/* 650 */       throw new RasterFormatException("x lies outside raster");
/*     */     }
/* 652 */     if (paramInt2 < this.minY) {
/* 653 */       throw new RasterFormatException("y lies outside raster");
/*     */     }
/* 655 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 656 */       throw new RasterFormatException("(x + width) is outside of Raster");
/*     */     }
/* 658 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 659 */       throw new RasterFormatException("(y + height) is outside of Raster");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 664 */     if (paramArrayOfint != null) {
/* 665 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/* 667 */       sampleModel = this.sampleModel;
/*     */     } 
/* 669 */     int i = paramInt5 - paramInt1;
/* 670 */     int j = paramInt6 - paramInt2;
/*     */     
/* 672 */     return new ShortBandedRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/*     */   public Raster createChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/* 704 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 712 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 713 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*     */     }
/*     */ 
/*     */     
/* 717 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*     */     
/* 719 */     return new ShortBandedRaster(sampleModel, new Point(0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster() {
/* 729 */     return createCompatibleWritableRaster(this.width, this.height);
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
/*     */   private void verify() {
/* 743 */     if (this.width <= 0 || this.height <= 0 || this.height > Integer.MAX_VALUE / this.width)
/*     */     {
/*     */       
/* 746 */       throw new RasterFormatException("Invalid raster dimension");
/*     */     }
/*     */     
/* 749 */     if (this.scanlineStride < 0 || this.scanlineStride > Integer.MAX_VALUE / this.height)
/*     */     {
/*     */ 
/*     */       
/* 753 */       throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */     }
/*     */ 
/*     */     
/* 757 */     if (this.minX - this.sampleModelTranslateX < 0L || this.minY - this.sampleModelTranslateY < 0L)
/*     */     {
/*     */       
/* 760 */       throw new RasterFormatException("Incorrect origin/translate: (" + this.minX + ", " + this.minY + ") / (" + this.sampleModelTranslateX + ", " + this.sampleModelTranslateY + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 765 */     if (this.height > 1 || this.minY - this.sampleModelTranslateY > 0)
/*     */     {
/* 767 */       for (byte b1 = 0; b1 < this.data.length; b1++) {
/* 768 */         if (this.scanlineStride > (this.data[b1]).length) {
/* 769 */           throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     int i;
/*     */     
/* 776 */     for (i = 0; i < this.dataOffsets.length; i++) {
/* 777 */       if (this.dataOffsets[i] < 0) {
/* 778 */         throw new RasterFormatException("Data offsets for band " + i + "(" + this.dataOffsets[i] + ") must be >= 0");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 784 */     i = (this.height - 1) * this.scanlineStride;
/* 785 */     if (this.width - 1 > Integer.MAX_VALUE - i) {
/* 786 */       throw new RasterFormatException("Invalid raster dimension");
/*     */     }
/* 788 */     int j = i + this.width - 1;
/*     */     
/* 790 */     int k = 0;
/*     */     
/*     */     byte b;
/* 793 */     for (b = 0; b < this.numDataElements; b++) {
/* 794 */       if (this.dataOffsets[b] > Integer.MAX_VALUE - j) {
/* 795 */         throw new RasterFormatException("Invalid raster dimension");
/*     */       }
/* 797 */       int m = j + this.dataOffsets[b];
/* 798 */       if (m > k) {
/* 799 */         k = m;
/*     */       }
/*     */     } 
/* 802 */     for (b = 0; b < this.numDataElements; b++) {
/* 803 */       if ((this.data[b]).length <= k) {
/* 804 */         throw new RasterFormatException("Data array too small (should be > " + k + " )");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 811 */     return new String("ShortBandedRaster: width = " + this.width + " height = " + this.height + " #numBands " + this.numBands + " #dataElements " + this.numDataElements);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ShortBandedRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */