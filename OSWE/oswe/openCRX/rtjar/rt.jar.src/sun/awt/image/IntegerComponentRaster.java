/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferInt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntegerComponentRaster
/*     */   extends SunWritableRaster
/*     */ {
/*     */   static final int TYPE_CUSTOM = 0;
/*     */   static final int TYPE_BYTE_SAMPLES = 1;
/*     */   static final int TYPE_USHORT_SAMPLES = 2;
/*     */   static final int TYPE_INT_SAMPLES = 3;
/*     */   static final int TYPE_BYTE_BANDED_SAMPLES = 4;
/*     */   static final int TYPE_USHORT_BANDED_SAMPLES = 5;
/*     */   static final int TYPE_INT_BANDED_SAMPLES = 6;
/*     */   static final int TYPE_BYTE_PACKED_SAMPLES = 7;
/*     */   static final int TYPE_USHORT_PACKED_SAMPLES = 8;
/*     */   static final int TYPE_INT_PACKED_SAMPLES = 9;
/*     */   static final int TYPE_INT_8BIT_SAMPLES = 10;
/*     */   static final int TYPE_BYTE_BINARY_SAMPLES = 11;
/*     */   protected int bandOffset;
/*     */   protected int[] dataOffsets;
/*     */   protected int scanlineStride;
/*     */   protected int pixelStride;
/*     */   protected int[] data;
/*     */   protected int numDataElems;
/*     */   int type;
/*     */   private int maxX;
/*     */   private int maxY;
/*     */   
/*     */   static {
/*  97 */     NativeLibLoader.loadLibraries();
/*  98 */     initIDs();
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
/*     */   public IntegerComponentRaster(SampleModel paramSampleModel, Point paramPoint) {
/* 112 */     this(paramSampleModel, paramSampleModel
/* 113 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/* 116 */           .getWidth(), paramSampleModel
/* 117 */           .getHeight()), paramPoint, (IntegerComponentRaster)null);
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
/*     */   public IntegerComponentRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/* 135 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */ 
/*     */           
/* 139 */           .getWidth(), paramSampleModel
/* 140 */           .getHeight()), paramPoint, (IntegerComponentRaster)null);
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
/*     */   public IntegerComponentRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, IntegerComponentRaster paramIntegerComponentRaster) {
/* 168 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramIntegerComponentRaster);
/* 169 */     this.maxX = this.minX + this.width;
/* 170 */     this.maxY = this.minY + this.height;
/* 171 */     if (!(paramDataBuffer instanceof DataBufferInt)) {
/* 172 */       throw new RasterFormatException("IntegerComponentRasters must haveinteger DataBuffers");
/*     */     }
/*     */     
/* 175 */     DataBufferInt dataBufferInt = (DataBufferInt)paramDataBuffer;
/* 176 */     if (dataBufferInt.getNumBanks() != 1) {
/* 177 */       throw new RasterFormatException("DataBuffer for IntegerComponentRasters must only have 1 bank.");
/*     */     }
/*     */ 
/*     */     
/* 181 */     this.data = stealData(dataBufferInt, 0);
/*     */     
/* 183 */     if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/* 184 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*     */       
/* 186 */       int[] arrayOfInt = singlePixelPackedSampleModel.getBitOffsets();
/* 187 */       boolean bool = false; int i;
/* 188 */       for (i = 1; i < arrayOfInt.length; i++) {
/* 189 */         if (arrayOfInt[i] % 8 != 0) {
/* 190 */           bool = true;
/*     */         }
/*     */       } 
/* 193 */       this.type = bool ? 9 : 10;
/*     */ 
/*     */ 
/*     */       
/* 197 */       this.scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
/* 198 */       this.pixelStride = 1;
/* 199 */       this.dataOffsets = new int[1];
/* 200 */       this.dataOffsets[0] = dataBufferInt.getOffset();
/* 201 */       this.bandOffset = this.dataOffsets[0];
/* 202 */       i = paramRectangle.x - paramPoint.x;
/* 203 */       int j = paramRectangle.y - paramPoint.y;
/* 204 */       this.dataOffsets[0] = this.dataOffsets[0] + i + j * this.scanlineStride;
/* 205 */       this.numDataElems = singlePixelPackedSampleModel.getNumDataElements();
/*     */     } else {
/* 207 */       throw new RasterFormatException("IntegerComponentRasters must have SinglePixelPackedSampleModel");
/*     */     } 
/*     */ 
/*     */     
/* 211 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataOffsets() {
/* 221 */     return (int[])this.dataOffsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataOffset(int paramInt) {
/* 230 */     return this.dataOffsets[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScanlineStride() {
/* 239 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelStride() {
/* 247 */     return this.pixelStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataStorage() {
/* 254 */     return this.data;
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
/*     */     int[] arrayOfInt;
/* 274 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 276 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 280 */     if (paramObject == null) {
/* 281 */       arrayOfInt = new int[this.numDataElements];
/*     */     } else {
/* 283 */       arrayOfInt = (int[])paramObject;
/*     */     } 
/* 285 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */     
/* 287 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 288 */       arrayOfInt[b] = this.data[this.dataOffsets[b] + i];
/*     */     }
/*     */     
/* 291 */     return arrayOfInt;
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
/*     */   public Object getDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*     */     int[] arrayOfInt;
/* 322 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 324 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 328 */     if (paramObject instanceof int[]) {
/* 329 */       arrayOfInt = (int[])paramObject;
/*     */     } else {
/* 331 */       arrayOfInt = new int[this.numDataElements * paramInt3 * paramInt4];
/*     */     } 
/* 333 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 336 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 340 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 341 */       int j = i;
/* 342 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 343 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 344 */           arrayOfInt[b1++] = this.data[this.dataOffsets[b3] + j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 349 */     return arrayOfInt;
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject) {
/* 366 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 368 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 371 */     int[] arrayOfInt = (int[])paramObject;
/*     */     
/* 373 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 376 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 377 */       this.data[this.dataOffsets[b] + i] = arrayOfInt[b];
/*     */     }
/*     */     
/* 380 */     markDirty();
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Raster paramRaster) {
/* 394 */     int i = paramInt1 + paramRaster.getMinX();
/* 395 */     int j = paramInt2 + paramRaster.getMinY();
/* 396 */     int k = paramRaster.getWidth();
/* 397 */     int m = paramRaster.getHeight();
/* 398 */     if (i < this.minX || j < this.minY || i + k > this.maxX || j + m > this.maxY)
/*     */     {
/* 400 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 403 */     setDataElements(i, j, k, m, paramRaster);
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
/* 422 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 428 */     int i = paramRaster.getMinX();
/* 429 */     int j = paramRaster.getMinY();
/* 430 */     int[] arrayOfInt = null;
/*     */     
/* 432 */     if (paramRaster instanceof IntegerComponentRaster && this.pixelStride == 1 && this.numDataElements == 1) {
/*     */       
/* 434 */       IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)paramRaster;
/* 435 */       if (integerComponentRaster.getNumDataElements() != 1) {
/* 436 */         throw new ArrayIndexOutOfBoundsException("Number of bands does not match");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 441 */       arrayOfInt = integerComponentRaster.getDataStorage();
/* 442 */       int k = integerComponentRaster.getScanlineStride();
/* 443 */       int m = integerComponentRaster.getDataOffset(0);
/*     */       
/* 445 */       int n = m;
/*     */       
/* 447 */       int i1 = this.dataOffsets[0] + (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 452 */       if (integerComponentRaster.getPixelStride() == this.pixelStride) {
/* 453 */         paramInt3 *= this.pixelStride;
/*     */ 
/*     */         
/* 456 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 457 */           System.arraycopy(arrayOfInt, n, this.data, i1, paramInt3);
/* 458 */           n += k;
/* 459 */           i1 += this.scanlineStride;
/*     */         } 
/* 461 */         markDirty();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 466 */     Object object = null;
/* 467 */     for (byte b = 0; b < paramInt4; b++) {
/* 468 */       object = paramRaster.getDataElements(i, j + b, paramInt3, 1, object);
/*     */       
/* 470 */       setDataElements(paramInt1, paramInt2 + b, paramInt3, 1, object);
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/* 498 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 500 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 503 */     int[] arrayOfInt = (int[])paramObject;
/*     */     
/* 505 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 508 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 512 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 513 */       int j = i;
/* 514 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 515 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 516 */           this.data[this.dataOffsets[b3] + j] = arrayOfInt[b1++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 521 */     markDirty();
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
/* 548 */     if (paramInt1 < this.minX) {
/* 549 */       throw new RasterFormatException("x lies outside raster");
/*     */     }
/* 551 */     if (paramInt2 < this.minY) {
/* 552 */       throw new RasterFormatException("y lies outside raster");
/*     */     }
/* 554 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 555 */       throw new RasterFormatException("(x + width) is outside raster");
/*     */     }
/* 557 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 558 */       throw new RasterFormatException("(y + height) is outside raster");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 563 */     if (paramArrayOfint != null) {
/* 564 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/* 566 */       sampleModel = this.sampleModel;
/*     */     } 
/* 568 */     int i = paramInt5 - paramInt1;
/* 569 */     int j = paramInt6 - paramInt2;
/*     */     
/* 571 */     return new IntegerComponentRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/*     */   public Raster createChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/* 602 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 611 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 612 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*     */     }
/*     */ 
/*     */     
/* 616 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*     */     
/* 618 */     return new IntegerComponentRaster(sampleModel, new Point(0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster() {
/* 628 */     return createCompatibleWritableRaster(this.width, this.height);
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
/* 646 */     if (this.width <= 0 || this.height <= 0 || this.height > Integer.MAX_VALUE / this.width)
/*     */     {
/*     */       
/* 649 */       throw new RasterFormatException("Invalid raster dimension");
/*     */     }
/*     */     
/* 652 */     if (this.dataOffsets[0] < 0) {
/* 653 */       throw new RasterFormatException("Data offset (" + this.dataOffsets[0] + ") must be >= 0");
/*     */     }
/*     */ 
/*     */     
/* 657 */     if (this.minX - this.sampleModelTranslateX < 0L || this.minY - this.sampleModelTranslateY < 0L)
/*     */     {
/*     */       
/* 660 */       throw new RasterFormatException("Incorrect origin/translate: (" + this.minX + ", " + this.minY + ") / (" + this.sampleModelTranslateX + ", " + this.sampleModelTranslateY + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 666 */     if (this.scanlineStride < 0 || this.scanlineStride > Integer.MAX_VALUE / this.height)
/*     */     {
/*     */ 
/*     */       
/* 670 */       throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */     }
/*     */ 
/*     */     
/* 674 */     if (this.height > 1 || this.minY - this.sampleModelTranslateY > 0)
/*     */     {
/* 676 */       if (this.scanlineStride > this.data.length) {
/* 677 */         throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 682 */     int i = (this.height - 1) * this.scanlineStride;
/*     */     
/* 684 */     if (this.pixelStride < 0 || this.pixelStride > Integer.MAX_VALUE / this.width || this.pixelStride > this.data.length)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 689 */       throw new RasterFormatException("Incorrect pixel stride: " + this.pixelStride);
/*     */     }
/*     */     
/* 692 */     int j = (this.width - 1) * this.pixelStride;
/*     */     
/* 694 */     if (j > Integer.MAX_VALUE - i)
/*     */     {
/* 696 */       throw new RasterFormatException("Incorrect raster attributes");
/*     */     }
/* 698 */     j += i;
/*     */ 
/*     */     
/* 701 */     int k = 0;
/* 702 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 703 */       if (this.dataOffsets[b] > Integer.MAX_VALUE - j) {
/* 704 */         throw new RasterFormatException("Incorrect band offset: " + this.dataOffsets[b]);
/*     */       }
/*     */ 
/*     */       
/* 708 */       int m = j + this.dataOffsets[b];
/*     */       
/* 710 */       if (m > k) {
/* 711 */         k = m;
/*     */       }
/*     */     } 
/* 714 */     if (this.data.length <= k) {
/* 715 */       throw new RasterFormatException("Data array too small (should be > " + k + " )");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 721 */     return new String("IntegerComponentRaster: width = " + this.width + " height = " + this.height + " #Bands = " + this.numBands + " #DataElements " + this.numDataElements + " xOff = " + this.sampleModelTranslateX + " yOff = " + this.sampleModelTranslateY + " dataOffset[0] " + this.dataOffsets[0]);
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/IntegerComponentRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */