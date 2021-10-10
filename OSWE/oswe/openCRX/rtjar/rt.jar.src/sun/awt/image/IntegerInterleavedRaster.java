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
/*     */ public class IntegerInterleavedRaster
/*     */   extends IntegerComponentRaster
/*     */ {
/*     */   private int maxX;
/*     */   private int maxY;
/*     */   
/*     */   public IntegerInterleavedRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  72 */     this(paramSampleModel, paramSampleModel
/*  73 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/*  76 */           .getWidth(), paramSampleModel
/*  77 */           .getHeight()), paramPoint, (IntegerInterleavedRaster)null);
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
/*     */   public IntegerInterleavedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/*  95 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */ 
/*     */           
/*  99 */           .getWidth(), paramSampleModel
/* 100 */           .getHeight()), paramPoint, (IntegerInterleavedRaster)null);
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
/*     */   public IntegerInterleavedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, IntegerInterleavedRaster paramIntegerInterleavedRaster) {
/* 128 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramIntegerInterleavedRaster);
/* 129 */     this.maxX = this.minX + this.width;
/* 130 */     this.maxY = this.minY + this.height;
/* 131 */     if (!(paramDataBuffer instanceof DataBufferInt)) {
/* 132 */       throw new RasterFormatException("IntegerInterleavedRasters must haveinteger DataBuffers");
/*     */     }
/*     */     
/* 135 */     DataBufferInt dataBufferInt = (DataBufferInt)paramDataBuffer;
/* 136 */     this.data = stealData(dataBufferInt, 0);
/*     */     
/* 138 */     if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/* 139 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*     */       
/* 141 */       this.scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
/* 142 */       this.pixelStride = 1;
/* 143 */       this.dataOffsets = new int[1];
/* 144 */       this.dataOffsets[0] = dataBufferInt.getOffset();
/* 145 */       this.bandOffset = this.dataOffsets[0];
/* 146 */       int i = paramRectangle.x - paramPoint.x;
/* 147 */       int j = paramRectangle.y - paramPoint.y;
/* 148 */       this.dataOffsets[0] = this.dataOffsets[0] + i + j * this.scanlineStride;
/* 149 */       this.numDataElems = singlePixelPackedSampleModel.getNumDataElements();
/*     */     } else {
/* 151 */       throw new RasterFormatException("IntegerInterleavedRasters must have SinglePixelPackedSampleModel");
/*     */     } 
/*     */     
/* 154 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataOffsets() {
/* 164 */     return (int[])this.dataOffsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataOffset(int paramInt) {
/* 173 */     return this.dataOffsets[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScanlineStride() {
/* 182 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelStride() {
/* 190 */     return this.pixelStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataStorage() {
/* 197 */     return this.data;
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
/* 217 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 219 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 223 */     if (paramObject == null) {
/* 224 */       arrayOfInt = new int[1];
/*     */     } else {
/* 226 */       arrayOfInt = (int[])paramObject;
/*     */     } 
/* 228 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[0];
/* 229 */     arrayOfInt[0] = this.data[i];
/*     */     
/* 231 */     return arrayOfInt;
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
/* 262 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 264 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 268 */     if (paramObject instanceof int[]) {
/* 269 */       arrayOfInt = (int[])paramObject;
/*     */     } else {
/* 271 */       arrayOfInt = new int[paramInt3 * paramInt4];
/*     */     } 
/* 273 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[0];
/* 274 */     int j = 0;
/*     */     
/* 276 */     for (byte b = 0; b < paramInt4; b++) {
/* 277 */       System.arraycopy(this.data, i, arrayOfInt, j, paramInt3);
/* 278 */       j += paramInt3;
/* 279 */       i += this.scanlineStride;
/*     */     } 
/*     */     
/* 282 */     return arrayOfInt;
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
/* 299 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 301 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 304 */     int[] arrayOfInt = (int[])paramObject;
/*     */     
/* 306 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[0];
/*     */     
/* 308 */     this.data[i] = arrayOfInt[0];
/*     */     
/* 310 */     markDirty();
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
/* 324 */     int i = paramInt1 + paramRaster.getMinX();
/* 325 */     int j = paramInt2 + paramRaster.getMinY();
/* 326 */     int k = paramRaster.getWidth();
/* 327 */     int m = paramRaster.getHeight();
/* 328 */     if (i < this.minX || j < this.minY || i + k > this.maxX || j + m > this.maxY)
/*     */     {
/* 330 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 334 */     setDataElements(i, j, k, m, paramRaster);
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
/* 353 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 359 */     int i = paramRaster.getMinX();
/* 360 */     int j = paramRaster.getMinY();
/* 361 */     int[] arrayOfInt = null;
/*     */     
/* 363 */     if (paramRaster instanceof IntegerInterleavedRaster) {
/* 364 */       IntegerInterleavedRaster integerInterleavedRaster = (IntegerInterleavedRaster)paramRaster;
/*     */ 
/*     */       
/* 367 */       arrayOfInt = integerInterleavedRaster.getDataStorage();
/* 368 */       int k = integerInterleavedRaster.getScanlineStride();
/* 369 */       int m = integerInterleavedRaster.getDataOffset(0);
/*     */       
/* 371 */       int n = m;
/* 372 */       int i1 = this.dataOffsets[0] + (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 378 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 379 */         System.arraycopy(arrayOfInt, n, this.data, i1, paramInt3);
/* 380 */         n += k;
/* 381 */         i1 += this.scanlineStride;
/*     */       } 
/* 383 */       markDirty();
/*     */       
/*     */       return;
/*     */     } 
/* 387 */     Object object = null;
/* 388 */     for (byte b = 0; b < paramInt4; b++) {
/*     */       
/* 390 */       object = paramRaster.getDataElements(i, j + b, paramInt3, 1, object);
/*     */       
/* 392 */       setDataElements(paramInt1, paramInt2 + b, paramInt3, 1, object);
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
/* 419 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 421 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 424 */     int[] arrayOfInt = (int[])paramObject;
/* 425 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[0];
/* 426 */     int j = 0;
/*     */     
/* 428 */     for (byte b = 0; b < paramInt4; b++) {
/* 429 */       System.arraycopy(arrayOfInt, j, this.data, i, paramInt3);
/* 430 */       j += paramInt3;
/* 431 */       i += this.scanlineStride;
/*     */     } 
/*     */     
/* 434 */     markDirty();
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
/*     */   public WritableRaster createWritableChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/*     */     SampleModel sampleModel;
/* 460 */     if (paramInt1 < this.minX) {
/* 461 */       throw new RasterFormatException("x lies outside raster");
/*     */     }
/* 463 */     if (paramInt2 < this.minY) {
/* 464 */       throw new RasterFormatException("y lies outside raster");
/*     */     }
/* 466 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 467 */       throw new RasterFormatException("(x + width) is outside raster");
/*     */     }
/* 469 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 470 */       throw new RasterFormatException("(y + height) is outside raster");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 475 */     if (paramArrayOfint != null) {
/* 476 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/* 478 */       sampleModel = this.sampleModel;
/*     */     } 
/* 480 */     int i = paramInt5 - paramInt1;
/* 481 */     int j = paramInt6 - paramInt2;
/*     */     
/* 483 */     return new IntegerInterleavedRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/* 514 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 523 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 524 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*     */     }
/*     */ 
/*     */     
/* 528 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*     */     
/* 530 */     return new IntegerInterleavedRaster(sampleModel, new Point(0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster() {
/* 540 */     return createCompatibleWritableRaster(this.width, this.height);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 544 */     return new String("IntegerInterleavedRaster: width = " + this.width + " height = " + this.height + " #Bands = " + this.numBands + " xOff = " + this.sampleModelTranslateX + " yOff = " + this.sampleModelTranslateY + " dataOffset[0] " + this.dataOffsets[0]);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/IntegerInterleavedRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */