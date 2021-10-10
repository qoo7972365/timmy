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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShortComponentRaster
/*     */   extends SunWritableRaster
/*     */ {
/*     */   protected int bandOffset;
/*     */   protected int[] dataOffsets;
/*     */   protected int scanlineStride;
/*     */   protected int pixelStride;
/*     */   protected short[] data;
/*     */   int type;
/*     */   private int maxX;
/*     */   private int maxY;
/*     */   
/*     */   static {
/*  82 */     NativeLibLoader.loadLibraries();
/*  83 */     initIDs();
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
/*     */   public ShortComponentRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  96 */     this(paramSampleModel, paramSampleModel
/*  97 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/* 100 */           .getWidth(), paramSampleModel
/* 101 */           .getHeight()), paramPoint, (ShortComponentRaster)null);
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
/*     */   public ShortComponentRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/* 120 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */ 
/*     */           
/* 124 */           .getWidth(), paramSampleModel
/* 125 */           .getHeight()), paramPoint, (ShortComponentRaster)null);
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
/*     */   public ShortComponentRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, ShortComponentRaster paramShortComponentRaster) {
/* 154 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramShortComponentRaster);
/* 155 */     this.maxX = this.minX + this.width;
/* 156 */     this.maxY = this.minY + this.height;
/*     */     
/* 158 */     if (!(paramDataBuffer instanceof DataBufferUShort)) {
/* 159 */       throw new RasterFormatException("ShortComponentRasters must have short DataBuffers");
/*     */     }
/*     */ 
/*     */     
/* 163 */     DataBufferUShort dataBufferUShort = (DataBufferUShort)paramDataBuffer;
/* 164 */     this.data = stealData(dataBufferUShort, 0);
/* 165 */     if (dataBufferUShort.getNumBanks() != 1) {
/* 166 */       throw new RasterFormatException("DataBuffer for ShortComponentRasters must only have 1 bank.");
/*     */     }
/*     */ 
/*     */     
/* 170 */     int i = dataBufferUShort.getOffset();
/*     */     
/* 172 */     if (paramSampleModel instanceof ComponentSampleModel) {
/* 173 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramSampleModel;
/* 174 */       this.type = 2;
/* 175 */       this.scanlineStride = componentSampleModel.getScanlineStride();
/* 176 */       this.pixelStride = componentSampleModel.getPixelStride();
/* 177 */       this.dataOffsets = componentSampleModel.getBandOffsets();
/* 178 */       int j = paramRectangle.x - paramPoint.x;
/* 179 */       int k = paramRectangle.y - paramPoint.y;
/* 180 */       for (byte b = 0; b < getNumDataElements(); b++) {
/* 181 */         this.dataOffsets[b] = this.dataOffsets[b] + i + j * this.pixelStride + k * this.scanlineStride;
/*     */       }
/*     */     }
/* 184 */     else if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/* 185 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*     */       
/* 187 */       this.type = 8;
/* 188 */       this.scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
/* 189 */       this.pixelStride = 1;
/* 190 */       this.dataOffsets = new int[1];
/* 191 */       this.dataOffsets[0] = i;
/* 192 */       int j = paramRectangle.x - paramPoint.x;
/* 193 */       int k = paramRectangle.y - paramPoint.y;
/* 194 */       this.dataOffsets[0] = this.dataOffsets[0] + j + k * this.scanlineStride;
/*     */     } else {
/* 196 */       throw new RasterFormatException("ShortComponentRasters must haveComponentSampleModel or SinglePixelPackedSampleModel");
/*     */     } 
/*     */     
/* 199 */     this.bandOffset = this.dataOffsets[0];
/*     */     
/* 201 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataOffsets() {
/* 210 */     return (int[])this.dataOffsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataOffset(int paramInt) {
/* 220 */     return this.dataOffsets[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScanlineStride() {
/* 228 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelStride() {
/* 236 */     return this.pixelStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getDataStorage() {
/* 243 */     return this.data;
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
/* 263 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 265 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 269 */     if (paramObject == null) {
/* 270 */       arrayOfShort = new short[this.numDataElements];
/*     */     } else {
/* 272 */       arrayOfShort = (short[])paramObject;
/*     */     } 
/* 274 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 277 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 278 */       arrayOfShort[b] = this.data[this.dataOffsets[b] + i];
/*     */     }
/*     */     
/* 281 */     return arrayOfShort;
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
/* 311 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 313 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 317 */     if (paramObject == null) {
/* 318 */       arrayOfShort = new short[paramInt3 * paramInt4 * this.numDataElements];
/*     */     } else {
/* 320 */       arrayOfShort = (short[])paramObject;
/*     */     } 
/* 322 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */ 
/*     */     
/* 326 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 330 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 331 */       int j = i;
/* 332 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 333 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 334 */           arrayOfShort[b1++] = this.data[this.dataOffsets[b3] + j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 339 */     return arrayOfShort;
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
/* 364 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 366 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 369 */     if (paramArrayOfshort == null) {
/* 370 */       paramArrayOfshort = new short[this.numDataElements * paramInt3 * paramInt4];
/*     */     }
/* 372 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*     */ 
/*     */     
/* 375 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 379 */     if (this.pixelStride == 1) {
/* 380 */       if (this.scanlineStride == paramInt3) {
/* 381 */         System.arraycopy(this.data, i, paramArrayOfshort, 0, paramInt3 * paramInt4);
/*     */       } else {
/*     */         
/* 384 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 385 */           System.arraycopy(this.data, i, paramArrayOfshort, j, paramInt3);
/* 386 */           j += paramInt3;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 391 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 392 */         int k = i;
/* 393 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 394 */           paramArrayOfshort[j++] = this.data[k];
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
/* 424 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 426 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 429 */     if (paramArrayOfshort == null) {
/* 430 */       paramArrayOfshort = new short[this.numDataElements * paramInt3 * paramInt4];
/*     */     }
/* 432 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 435 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 439 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 440 */       int j = i;
/* 441 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 442 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 443 */           paramArrayOfshort[b1++] = this.data[this.dataOffsets[b3] + j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 448 */     return paramArrayOfshort;
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
/* 464 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 466 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 469 */     short[] arrayOfShort = (short[])paramObject;
/* 470 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */     
/* 472 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 473 */       this.data[this.dataOffsets[b] + i] = arrayOfShort[b];
/*     */     }
/*     */     
/* 476 */     markDirty();
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
/* 488 */     int i = paramInt1 + paramRaster.getMinX();
/* 489 */     int j = paramInt2 + paramRaster.getMinY();
/* 490 */     int k = paramRaster.getWidth();
/* 491 */     int m = paramRaster.getHeight();
/* 492 */     if (i < this.minX || j < this.minY || i + k > this.maxX || j + m > this.maxY)
/*     */     {
/* 494 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 498 */     setDataElements(i, j, k, m, paramRaster);
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
/* 517 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 523 */     int i = paramRaster.getMinX();
/* 524 */     int j = paramRaster.getMinY();
/* 525 */     Object object = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 531 */     for (byte b = 0; b < paramInt4; b++) {
/*     */       
/* 533 */       object = paramRaster.getDataElements(i, j + b, paramInt3, 1, object);
/*     */       
/* 535 */       setDataElements(paramInt1, paramInt2 + b, paramInt3, 1, object);
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
/* 562 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 564 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 567 */     short[] arrayOfShort = (short[])paramObject;
/* 568 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 571 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 575 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 576 */       int j = i;
/* 577 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 578 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 579 */           this.data[this.dataOffsets[b3] + j] = arrayOfShort[b1++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 584 */     markDirty();
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
/* 608 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 610 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 613 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*     */ 
/*     */     
/* 616 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 620 */     if (this.pixelStride == 1) {
/* 621 */       if (this.scanlineStride == paramInt3) {
/* 622 */         System.arraycopy(paramArrayOfshort, 0, this.data, i, paramInt3 * paramInt4);
/*     */       } else {
/*     */         
/* 625 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 626 */           System.arraycopy(paramArrayOfshort, j, this.data, i, paramInt3);
/* 627 */           j += paramInt3;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 632 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 633 */         int k = i;
/* 634 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 635 */           this.data[k] = paramArrayOfshort[j++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 640 */     markDirty();
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
/* 661 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 663 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 666 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 669 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 673 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 674 */       int j = i;
/* 675 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 676 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 677 */           this.data[this.dataOffsets[b3] + j] = paramArrayOfshort[b1++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 682 */     markDirty();
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
/* 707 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
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
/* 737 */     if (paramInt1 < this.minX) {
/* 738 */       throw new RasterFormatException("x lies outside the raster");
/*     */     }
/* 740 */     if (paramInt2 < this.minY) {
/* 741 */       throw new RasterFormatException("y lies outside the raster");
/*     */     }
/* 743 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 744 */       throw new RasterFormatException("(x + width) is outside of Raster");
/*     */     }
/* 746 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 747 */       throw new RasterFormatException("(y + height) is outside of Raster");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 752 */     if (paramArrayOfint != null) {
/* 753 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/* 755 */       sampleModel = this.sampleModel;
/*     */     } 
/* 757 */     int i = paramInt5 - paramInt1;
/* 758 */     int j = paramInt6 - paramInt2;
/*     */     
/* 760 */     return new ShortComponentRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/* 773 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 774 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*     */     }
/*     */ 
/*     */     
/* 778 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*     */     
/* 780 */     return new ShortComponentRaster(sampleModel, new Point(0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster() {
/* 790 */     return createCompatibleWritableRaster(this.width, this.height);
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
/*     */   protected final void verify() {
/* 808 */     if (this.width <= 0 || this.height <= 0 || this.height > Integer.MAX_VALUE / this.width)
/*     */     {
/*     */       
/* 811 */       throw new RasterFormatException("Invalid raster dimension");
/*     */     }
/*     */     int i;
/* 814 */     for (i = 0; i < this.dataOffsets.length; i++) {
/* 815 */       if (this.dataOffsets[i] < 0) {
/* 816 */         throw new RasterFormatException("Data offsets for band " + i + "(" + this.dataOffsets[i] + ") must be >= 0");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 822 */     if (this.minX - this.sampleModelTranslateX < 0L || this.minY - this.sampleModelTranslateY < 0L)
/*     */     {
/*     */       
/* 825 */       throw new RasterFormatException("Incorrect origin/translate: (" + this.minX + ", " + this.minY + ") / (" + this.sampleModelTranslateX + ", " + this.sampleModelTranslateY + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 831 */     if (this.scanlineStride < 0 || this.scanlineStride > Integer.MAX_VALUE / this.height)
/*     */     {
/*     */ 
/*     */       
/* 835 */       throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */     }
/*     */ 
/*     */     
/* 839 */     if (this.height > 1 || this.minY - this.sampleModelTranslateY > 0)
/*     */     {
/* 841 */       if (this.scanlineStride > this.data.length) {
/* 842 */         throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 847 */     i = (this.height - 1) * this.scanlineStride;
/*     */     
/* 849 */     if (this.pixelStride < 0 || this.pixelStride > Integer.MAX_VALUE / this.width || this.pixelStride > this.data.length)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 854 */       throw new RasterFormatException("Incorrect pixel stride: " + this.pixelStride);
/*     */     }
/*     */     
/* 857 */     int j = (this.width - 1) * this.pixelStride;
/*     */     
/* 859 */     if (j > Integer.MAX_VALUE - i)
/*     */     {
/* 861 */       throw new RasterFormatException("Incorrect raster attributes");
/*     */     }
/* 863 */     j += i;
/*     */ 
/*     */     
/* 866 */     int k = 0;
/* 867 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 868 */       if (this.dataOffsets[b] > Integer.MAX_VALUE - j) {
/* 869 */         throw new RasterFormatException("Incorrect band offset: " + this.dataOffsets[b]);
/*     */       }
/*     */ 
/*     */       
/* 873 */       int m = j + this.dataOffsets[b];
/*     */       
/* 875 */       if (m > k) {
/* 876 */         k = m;
/*     */       }
/*     */     } 
/* 879 */     if (this.data.length <= k) {
/* 880 */       throw new RasterFormatException("Data array too small (should be > " + k + " )");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 886 */     return new String("ShortComponentRaster: width = " + this.width + " height = " + this.height + " #numDataElements " + this.numDataElements);
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ShortComponentRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */