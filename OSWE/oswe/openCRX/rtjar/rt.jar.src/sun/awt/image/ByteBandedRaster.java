/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BandedSampleModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
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
/*     */ 
/*     */ 
/*     */ public class ByteBandedRaster
/*     */   extends SunWritableRaster
/*     */ {
/*     */   int[] dataOffsets;
/*     */   int scanlineStride;
/*     */   byte[][] data;
/*     */   private int maxX;
/*     */   private int maxY;
/*     */   
/*     */   public ByteBandedRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  79 */     this(paramSampleModel, paramSampleModel
/*  80 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/*  83 */           .getWidth(), paramSampleModel
/*  84 */           .getHeight()), paramPoint, (ByteBandedRaster)null);
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
/*     */   public ByteBandedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/* 102 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */           
/* 104 */           .getWidth(), paramSampleModel
/* 105 */           .getHeight()), paramPoint, (ByteBandedRaster)null);
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
/*     */   public ByteBandedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, ByteBandedRaster paramByteBandedRaster) {
/* 133 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramByteBandedRaster);
/* 134 */     this.maxX = this.minX + this.width;
/* 135 */     this.maxY = this.minY + this.height;
/*     */     
/* 137 */     if (!(paramDataBuffer instanceof DataBufferByte)) {
/* 138 */       throw new RasterFormatException("ByteBandedRaster must havebyte DataBuffers");
/*     */     }
/*     */     
/* 141 */     DataBufferByte dataBufferByte = (DataBufferByte)paramDataBuffer;
/*     */     
/* 143 */     if (paramSampleModel instanceof BandedSampleModel) {
/* 144 */       BandedSampleModel bandedSampleModel = (BandedSampleModel)paramSampleModel;
/* 145 */       this.scanlineStride = bandedSampleModel.getScanlineStride();
/* 146 */       int[] arrayOfInt1 = bandedSampleModel.getBankIndices();
/* 147 */       int[] arrayOfInt2 = bandedSampleModel.getBandOffsets();
/* 148 */       int[] arrayOfInt3 = dataBufferByte.getOffsets();
/* 149 */       this.dataOffsets = new int[arrayOfInt1.length];
/* 150 */       this.data = new byte[arrayOfInt1.length][];
/* 151 */       int i = paramRectangle.x - paramPoint.x;
/* 152 */       int j = paramRectangle.y - paramPoint.y;
/* 153 */       for (byte b = 0; b < arrayOfInt1.length; b++) {
/* 154 */         this.data[b] = stealData(dataBufferByte, arrayOfInt1[b]);
/* 155 */         this.dataOffsets[b] = arrayOfInt3[arrayOfInt1[b]] + i + j * this.scanlineStride + arrayOfInt2[b];
/*     */       } 
/*     */     } else {
/*     */       
/* 159 */       throw new RasterFormatException("ByteBandedRasters must haveBandedSampleModels");
/*     */     } 
/*     */     
/* 162 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDataOffsets() {
/* 172 */     return (int[])this.dataOffsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataOffset(int paramInt) {
/* 182 */     return this.dataOffsets[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScanlineStride() {
/* 191 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelStride() {
/* 199 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[][] getDataStorage() {
/* 206 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDataStorage(int paramInt) {
/* 213 */     return this.data[paramInt];
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
/*     */     byte[] arrayOfByte;
/* 233 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 235 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 239 */     if (paramObject == null) {
/* 240 */       arrayOfByte = new byte[this.numDataElements];
/*     */     } else {
/* 242 */       arrayOfByte = (byte[])paramObject;
/*     */     } 
/* 244 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 246 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 247 */       arrayOfByte[b] = this.data[b][this.dataOffsets[b] + i];
/*     */     }
/*     */     
/* 250 */     return arrayOfByte;
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
/*     */     byte[] arrayOfByte;
/* 280 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 282 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 286 */     if (paramObject == null) {
/* 287 */       arrayOfByte = new byte[this.numDataElements * paramInt3 * paramInt4];
/*     */     } else {
/* 289 */       arrayOfByte = (byte[])paramObject;
/*     */     } 
/* 291 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 293 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 294 */       int j = b;
/* 295 */       byte[] arrayOfByte1 = this.data[b];
/* 296 */       int k = this.dataOffsets[b];
/*     */       
/* 298 */       int m = i;
/* 299 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 300 */         int n = k + m;
/* 301 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 302 */           arrayOfByte[j] = arrayOfByte1[n++];
/* 303 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     return arrayOfByte;
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
/*     */   public byte[] getByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfbyte) {
/* 333 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 335 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 338 */     if (paramArrayOfbyte == null) {
/* 339 */       paramArrayOfbyte = new byte[this.scanlineStride * paramInt4];
/*     */     }
/* 341 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[paramInt5];
/*     */     
/* 343 */     if (this.scanlineStride == paramInt3) {
/* 344 */       System.arraycopy(this.data[paramInt5], i, paramArrayOfbyte, 0, paramInt3 * paramInt4);
/*     */     } else {
/* 346 */       int j = 0;
/* 347 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 348 */         System.arraycopy(this.data[paramInt5], i, paramArrayOfbyte, j, paramInt3);
/* 349 */         j += paramInt3;
/*     */       } 
/*     */     } 
/*     */     
/* 353 */     return paramArrayOfbyte;
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
/*     */   public byte[] getByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/* 378 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 380 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 383 */     if (paramArrayOfbyte == null) {
/* 384 */       paramArrayOfbyte = new byte[this.numDataElements * this.scanlineStride * paramInt4];
/*     */     }
/* 386 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 388 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 389 */       int j = b;
/* 390 */       byte[] arrayOfByte = this.data[b];
/* 391 */       int k = this.dataOffsets[b];
/*     */ 
/*     */       
/* 394 */       int m = i;
/* 395 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 396 */         int n = k + m;
/* 397 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 398 */           paramArrayOfbyte[j] = arrayOfByte[n++];
/* 399 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 404 */     return paramArrayOfbyte;
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
/* 420 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 422 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 425 */     byte[] arrayOfByte = (byte[])paramObject;
/* 426 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/* 427 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 428 */       this.data[b][this.dataOffsets[b] + i] = arrayOfByte[b];
/*     */     }
/* 430 */     markDirty();
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
/* 442 */     int i = paramRaster.getMinX() + paramInt1;
/* 443 */     int j = paramRaster.getMinY() + paramInt2;
/* 444 */     int k = paramRaster.getWidth();
/* 445 */     int m = paramRaster.getHeight();
/* 446 */     if (i < this.minX || j < this.minY || i + k > this.maxX || j + m > this.maxY)
/*     */     {
/* 448 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 452 */     setDataElements(i, j, k, m, paramRaster);
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
/* 471 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 475 */     int i = paramRaster.getMinX();
/* 476 */     int j = paramRaster.getMinY();
/* 477 */     Object object = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     for (byte b = 0; b < paramInt4; b++) {
/*     */       
/* 485 */       object = paramRaster.getDataElements(i, j + b, paramInt3, 1, object);
/*     */       
/* 487 */       setDataElements(paramInt1, paramInt2 + b, paramInt3, 1, object);
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
/* 514 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 516 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 519 */     byte[] arrayOfByte = (byte[])paramObject;
/* 520 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 522 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 523 */       int j = b;
/* 524 */       byte[] arrayOfByte1 = this.data[b];
/* 525 */       int k = this.dataOffsets[b];
/*     */       
/* 527 */       int m = i;
/* 528 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 529 */         int n = k + m;
/* 530 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 531 */           arrayOfByte1[n++] = arrayOfByte[j];
/* 532 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 537 */     markDirty();
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
/*     */   public void putByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfbyte) {
/* 561 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 563 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 566 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX + this.dataOffsets[paramInt5];
/*     */     
/* 568 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 572 */     if (this.scanlineStride == paramInt3) {
/* 573 */       System.arraycopy(paramArrayOfbyte, 0, this.data[paramInt5], i, paramInt3 * paramInt4);
/*     */     } else {
/* 575 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 576 */         System.arraycopy(paramArrayOfbyte, j, this.data[paramInt5], i, paramInt3);
/* 577 */         j += paramInt3;
/*     */       } 
/*     */     } 
/*     */     
/* 581 */     markDirty();
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
/*     */   public void putByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/* 602 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 604 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 607 */     int i = (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */     
/* 609 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 610 */       int j = b;
/* 611 */       byte[] arrayOfByte = this.data[b];
/* 612 */       int k = this.dataOffsets[b];
/*     */       
/* 614 */       int m = i;
/* 615 */       for (byte b1 = 0; b1 < paramInt4; b1++, m += this.scanlineStride) {
/* 616 */         int n = k + m;
/* 617 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 618 */           arrayOfByte[n++] = paramArrayOfbyte[j];
/* 619 */           j += this.numDataElements;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 624 */     markDirty();
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
/* 651 */     if (paramInt1 < this.minX) {
/* 652 */       throw new RasterFormatException("x lies outside raster");
/*     */     }
/* 654 */     if (paramInt2 < this.minY) {
/* 655 */       throw new RasterFormatException("y lies outside raster");
/*     */     }
/* 657 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.width + this.minX) {
/* 658 */       throw new RasterFormatException("(x + width) is outside raster");
/*     */     }
/* 660 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.height + this.minY) {
/* 661 */       throw new RasterFormatException("(y + height) is outside raster");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 666 */     if (paramArrayOfint != null) {
/* 667 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/* 669 */       sampleModel = this.sampleModel;
/*     */     } 
/* 671 */     int i = paramInt5 - paramInt1;
/* 672 */     int j = paramInt6 - paramInt2;
/*     */     
/* 674 */     return new ByteBandedRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/* 705 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 713 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 714 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*     */     }
/*     */ 
/*     */     
/* 718 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*     */     
/* 720 */     return new ByteBandedRaster(sampleModel, new Point(0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster() {
/* 730 */     return createCompatibleWritableRaster(this.width, this.height);
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
/* 744 */     if (this.width <= 0 || this.height <= 0 || this.height > Integer.MAX_VALUE / this.width)
/*     */     {
/*     */       
/* 747 */       throw new RasterFormatException("Invalid raster dimension");
/*     */     }
/*     */     
/* 750 */     if (this.scanlineStride < 0 || this.scanlineStride > Integer.MAX_VALUE / this.height)
/*     */     {
/*     */ 
/*     */       
/* 754 */       throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */     }
/*     */ 
/*     */     
/* 758 */     if (this.minX - this.sampleModelTranslateX < 0L || this.minY - this.sampleModelTranslateY < 0L)
/*     */     {
/*     */       
/* 761 */       throw new RasterFormatException("Incorrect origin/translate: (" + this.minX + ", " + this.minY + ") / (" + this.sampleModelTranslateX + ", " + this.sampleModelTranslateY + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 767 */     if (this.height > 1 || this.minY - this.sampleModelTranslateY > 0)
/*     */     {
/* 769 */       for (byte b1 = 0; b1 < this.data.length; b1++) {
/* 770 */         if (this.scanlineStride > (this.data[b1]).length) {
/* 771 */           throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     int i;
/*     */     
/* 778 */     for (i = 0; i < this.dataOffsets.length; i++) {
/* 779 */       if (this.dataOffsets[i] < 0) {
/* 780 */         throw new RasterFormatException("Data offsets for band " + i + "(" + this.dataOffsets[i] + ") must be >= 0");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 786 */     i = (this.height - 1) * this.scanlineStride;
/*     */     
/* 788 */     if (this.width - 1 > Integer.MAX_VALUE - i) {
/* 789 */       throw new RasterFormatException("Invalid raster dimension");
/*     */     }
/* 791 */     int j = i + this.width - 1;
/*     */     
/* 793 */     int k = 0;
/*     */     
/*     */     byte b;
/* 796 */     for (b = 0; b < this.numDataElements; b++) {
/* 797 */       if (this.dataOffsets[b] > Integer.MAX_VALUE - j) {
/* 798 */         throw new RasterFormatException("Invalid raster dimension");
/*     */       }
/* 800 */       int m = j + this.dataOffsets[b];
/* 801 */       if (m > k) {
/* 802 */         k = m;
/*     */       }
/*     */     } 
/*     */     
/* 806 */     if (this.data.length == 1) {
/* 807 */       if ((this.data[0]).length <= k * this.numDataElements) {
/* 808 */         throw new RasterFormatException("Data array too small (it is " + (this.data[0]).length + " and should be > " + (k * this.numDataElements) + " )");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 816 */       for (b = 0; b < this.numDataElements; b++) {
/* 817 */         if ((this.data[b]).length <= k) {
/* 818 */           throw new RasterFormatException("Data array too small (it is " + (this.data[b]).length + " and should be > " + k + " )");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 828 */     return new String("ByteBandedRaster: width = " + this.width + " height = " + this.height + " #bands " + this.numDataElements + " minX = " + this.minX + " minY = " + this.minY);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ByteBandedRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */