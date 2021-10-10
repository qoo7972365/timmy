/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ComponentSampleModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
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
/*     */ public class ByteComponentRaster
/*     */   extends SunWritableRaster
/*     */ {
/*     */   protected int bandOffset;
/*     */   protected int[] dataOffsets;
/*     */   protected int scanlineStride;
/*     */   protected int pixelStride;
/*     */   protected byte[] data;
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
/*     */   public ByteComponentRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  96 */     this(paramSampleModel, paramSampleModel
/*  97 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */           
/* 100 */           .getWidth(), paramSampleModel
/* 101 */           .getHeight()), paramPoint, (ByteComponentRaster)null);
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
/*     */   public ByteComponentRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/* 120 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*     */ 
/*     */ 
/*     */           
/* 124 */           .getWidth(), paramSampleModel
/* 125 */           .getHeight()), paramPoint, (ByteComponentRaster)null);
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
/*     */   public ByteComponentRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, ByteComponentRaster paramByteComponentRaster) {
/* 154 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramByteComponentRaster);
/* 155 */     this.maxX = this.minX + this.width;
/* 156 */     this.maxY = this.minY + this.height;
/*     */     
/* 158 */     if (!(paramDataBuffer instanceof DataBufferByte)) {
/* 159 */       throw new RasterFormatException("ByteComponentRasters must have byte DataBuffers");
/*     */     }
/*     */ 
/*     */     
/* 163 */     DataBufferByte dataBufferByte = (DataBufferByte)paramDataBuffer;
/* 164 */     this.data = stealData(dataBufferByte, 0);
/* 165 */     if (dataBufferByte.getNumBanks() != 1) {
/* 166 */       throw new RasterFormatException("DataBuffer for ByteComponentRasters must only have 1 bank.");
/*     */     }
/*     */ 
/*     */     
/* 170 */     int i = dataBufferByte.getOffset();
/*     */     
/* 172 */     if (paramSampleModel instanceof ComponentSampleModel) {
/* 173 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramSampleModel;
/* 174 */       this.type = 1;
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
/* 187 */       this.type = 7;
/* 188 */       this.scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
/* 189 */       this.pixelStride = 1;
/* 190 */       this.dataOffsets = new int[1];
/* 191 */       this.dataOffsets[0] = i;
/* 192 */       int j = paramRectangle.x - paramPoint.x;
/* 193 */       int k = paramRectangle.y - paramPoint.y;
/* 194 */       this.dataOffsets[0] = this.dataOffsets[0] + j * this.pixelStride + k * this.scanlineStride;
/*     */     } else {
/* 196 */       throw new RasterFormatException("IntegerComponentRasters must have ComponentSampleModel or SinglePixelPackedSampleModel");
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
/*     */   
/*     */   public int getScanlineStride() {
/* 229 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelStride() {
/* 237 */     return this.pixelStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDataStorage() {
/* 244 */     return this.data;
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
/* 264 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 266 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 270 */     if (paramObject == null) {
/* 271 */       arrayOfByte = new byte[this.numDataElements];
/*     */     } else {
/* 273 */       arrayOfByte = (byte[])paramObject;
/*     */     } 
/* 275 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 278 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 279 */       arrayOfByte[b] = this.data[this.dataOffsets[b] + i];
/*     */     }
/*     */     
/* 282 */     return arrayOfByte;
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
/* 312 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 314 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 318 */     if (paramObject == null) {
/* 319 */       arrayOfByte = new byte[paramInt3 * paramInt4 * this.numDataElements];
/*     */     } else {
/* 321 */       arrayOfByte = (byte[])paramObject;
/*     */     } 
/*     */     
/* 324 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 327 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 331 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 332 */       int j = i;
/* 333 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 334 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 335 */           arrayOfByte[b1++] = this.data[this.dataOffsets[b3] + j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 340 */     return arrayOfByte;
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
/* 365 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 367 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 370 */     if (paramArrayOfbyte == null) {
/* 371 */       paramArrayOfbyte = new byte[this.scanlineStride * paramInt4];
/*     */     }
/* 373 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*     */ 
/*     */     
/* 376 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 380 */     if (this.pixelStride == 1) {
/* 381 */       if (this.scanlineStride == paramInt3) {
/* 382 */         System.arraycopy(this.data, i, paramArrayOfbyte, 0, paramInt3 * paramInt4);
/*     */       } else {
/*     */         
/* 385 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 386 */           System.arraycopy(this.data, i, paramArrayOfbyte, j, paramInt3);
/* 387 */           j += paramInt3;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 392 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 393 */         int k = i;
/* 394 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 395 */           paramArrayOfbyte[j++] = this.data[k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 400 */     return paramArrayOfbyte;
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
/* 425 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 427 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 430 */     if (paramArrayOfbyte == null) {
/* 431 */       paramArrayOfbyte = new byte[this.numDataElements * this.scanlineStride * paramInt4];
/*     */     }
/* 433 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 436 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 442 */       int j = i;
/* 443 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 444 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 445 */           paramArrayOfbyte[b1++] = this.data[this.dataOffsets[b3] + j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 450 */     return paramArrayOfbyte;
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
/* 466 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*     */     {
/* 468 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 471 */     byte[] arrayOfByte = (byte[])paramObject;
/* 472 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 475 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 476 */       this.data[this.dataOffsets[b] + i] = arrayOfByte[b];
/*     */     }
/*     */     
/* 479 */     markDirty();
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
/* 491 */     int i = paramRaster.getMinX() + paramInt1;
/* 492 */     int j = paramRaster.getMinY() + paramInt2;
/* 493 */     int k = paramRaster.getWidth();
/* 494 */     int m = paramRaster.getHeight();
/* 495 */     if (i < this.minX || j < this.minY || i + k > this.maxX || j + m > this.maxY)
/*     */     {
/* 497 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 501 */     setDataElements(i, j, k, m, paramRaster);
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
/* 520 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 524 */     int i = paramRaster.getMinX();
/* 525 */     int j = paramRaster.getMinY();
/* 526 */     Object object = null;
/*     */     
/* 528 */     if (paramRaster instanceof ByteComponentRaster) {
/* 529 */       ByteComponentRaster byteComponentRaster = (ByteComponentRaster)paramRaster;
/* 530 */       byte[] arrayOfByte = byteComponentRaster.getDataStorage();
/*     */       
/* 532 */       if (this.numDataElements == 1) {
/* 533 */         int k = byteComponentRaster.getDataOffset(0);
/* 534 */         int m = byteComponentRaster.getScanlineStride();
/*     */         
/* 536 */         int n = k;
/* 537 */         int i1 = this.dataOffsets[0] + (paramInt2 - this.minY) * this.scanlineStride + paramInt1 - this.minX;
/*     */ 
/*     */ 
/*     */         
/* 541 */         if (this.pixelStride == byteComponentRaster.getPixelStride()) {
/* 542 */           paramInt3 *= this.pixelStride;
/* 543 */           for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 544 */             System.arraycopy(arrayOfByte, n, this.data, i1, paramInt3);
/*     */             
/* 546 */             n += m;
/* 547 */             i1 += this.scanlineStride;
/*     */           } 
/* 549 */           markDirty();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 555 */     for (byte b = 0; b < paramInt4; b++) {
/*     */       
/* 557 */       object = paramRaster.getDataElements(i, j + b, paramInt3, 1, object);
/*     */       
/* 559 */       setDataElements(paramInt1, paramInt2 + b, paramInt3, 1, object);
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
/* 586 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 588 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 591 */     byte[] arrayOfByte = (byte[])paramObject;
/* 592 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */     
/* 595 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 599 */     if (this.numDataElements == 1) {
/* 600 */       int j = 0;
/* 601 */       int k = i + this.dataOffsets[0];
/* 602 */       for (byte b = 0; b < paramInt4; b++) {
/* 603 */         int m = i;
/* 604 */         System.arraycopy(arrayOfByte, j, this.data, k, paramInt3);
/*     */ 
/*     */         
/* 607 */         j += paramInt3;
/* 608 */         k += this.scanlineStride;
/*     */       } 
/* 610 */       markDirty();
/*     */       
/*     */       return;
/*     */     } 
/* 614 */     for (byte b2 = 0; b2 < paramInt4; b2++, i += this.scanlineStride) {
/* 615 */       int j = i;
/* 616 */       for (byte b = 0; b < paramInt3; b++, j += this.pixelStride) {
/* 617 */         for (byte b3 = 0; b3 < this.numDataElements; b3++) {
/* 618 */           this.data[this.dataOffsets[b3] + j] = arrayOfByte[b1++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 623 */     markDirty();
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
/* 647 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 649 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 652 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*     */ 
/*     */     
/* 655 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 659 */     if (this.pixelStride == 1) {
/* 660 */       if (this.scanlineStride == paramInt3) {
/* 661 */         System.arraycopy(paramArrayOfbyte, 0, this.data, i, paramInt3 * paramInt4);
/*     */       } else {
/*     */         
/* 664 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 665 */           System.arraycopy(paramArrayOfbyte, j, this.data, i, paramInt3);
/* 666 */           j += paramInt3;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 671 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 672 */         int k = i;
/* 673 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 674 */           this.data[k] = paramArrayOfbyte[j++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 679 */     markDirty();
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
/* 700 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*     */     {
/* 702 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 705 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*     */ 
/*     */ 
/*     */     
/* 709 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/* 713 */     if (this.numDataElements == 1) {
/* 714 */       i += this.dataOffsets[0];
/* 715 */       if (this.pixelStride == 1) {
/* 716 */         if (this.scanlineStride == paramInt3) {
/* 717 */           System.arraycopy(paramArrayOfbyte, 0, this.data, i, paramInt3 * paramInt4);
/*     */         } else {
/*     */           
/* 720 */           for (byte b = 0; b < paramInt4; b++) {
/* 721 */             System.arraycopy(paramArrayOfbyte, j, this.data, i, paramInt3);
/* 722 */             j += paramInt3;
/* 723 */             i += this.scanlineStride;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 728 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 729 */           int k = i;
/* 730 */           for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 731 */             this.data[k] = paramArrayOfbyte[j++];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 737 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/* 738 */         int k = i;
/* 739 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/* 740 */           for (byte b2 = 0; b2 < this.numDataElements; b2++) {
/* 741 */             this.data[this.dataOffsets[b2] + k] = paramArrayOfbyte[j++];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 747 */     markDirty();
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
/* 772 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
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
/* 802 */     if (paramInt1 < this.minX) {
/* 803 */       throw new RasterFormatException("x lies outside the raster");
/*     */     }
/* 805 */     if (paramInt2 < this.minY) {
/* 806 */       throw new RasterFormatException("y lies outside the raster");
/*     */     }
/* 808 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 809 */       throw new RasterFormatException("(x + width) is outside of Raster");
/*     */     }
/* 811 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 812 */       throw new RasterFormatException("(y + height) is outside of Raster");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 817 */     if (paramArrayOfint != null) {
/* 818 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*     */     } else {
/* 820 */       sampleModel = this.sampleModel;
/*     */     } 
/* 822 */     int i = paramInt5 - paramInt1;
/* 823 */     int j = paramInt6 - paramInt2;
/*     */     
/* 825 */     return new ByteComponentRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/* 838 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 839 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*     */     }
/*     */ 
/*     */     
/* 843 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*     */     
/* 845 */     return new ByteComponentRaster(sampleModel, new Point(0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleWritableRaster() {
/* 856 */     return createCompatibleWritableRaster(this.width, this.height);
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
/* 874 */     if (this.width <= 0 || this.height <= 0 || this.height > Integer.MAX_VALUE / this.width)
/*     */     {
/*     */       
/* 877 */       throw new RasterFormatException("Invalid raster dimension");
/*     */     }
/*     */     int i;
/* 880 */     for (i = 0; i < this.dataOffsets.length; i++) {
/* 881 */       if (this.dataOffsets[i] < 0) {
/* 882 */         throw new RasterFormatException("Data offsets for band " + i + "(" + this.dataOffsets[i] + ") must be >= 0");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 888 */     if (this.minX - this.sampleModelTranslateX < 0L || this.minY - this.sampleModelTranslateY < 0L)
/*     */     {
/*     */       
/* 891 */       throw new RasterFormatException("Incorrect origin/translate: (" + this.minX + ", " + this.minY + ") / (" + this.sampleModelTranslateX + ", " + this.sampleModelTranslateY + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 897 */     if (this.scanlineStride < 0 || this.scanlineStride > Integer.MAX_VALUE / this.height)
/*     */     {
/*     */ 
/*     */       
/* 901 */       throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */     }
/*     */ 
/*     */     
/* 905 */     if (this.height > 1 || this.minY - this.sampleModelTranslateY > 0)
/*     */     {
/* 907 */       if (this.scanlineStride > this.data.length) {
/* 908 */         throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 913 */     i = (this.height - 1) * this.scanlineStride;
/*     */     
/* 915 */     if (this.pixelStride < 0 || this.pixelStride > Integer.MAX_VALUE / this.width || this.pixelStride > this.data.length)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 920 */       throw new RasterFormatException("Incorrect pixel stride: " + this.pixelStride);
/*     */     }
/*     */     
/* 923 */     int j = (this.width - 1) * this.pixelStride;
/*     */     
/* 925 */     if (j > Integer.MAX_VALUE - i)
/*     */     {
/* 927 */       throw new RasterFormatException("Incorrect raster attributes");
/*     */     }
/* 929 */     j += i;
/*     */ 
/*     */     
/* 932 */     int k = 0;
/* 933 */     for (byte b = 0; b < this.numDataElements; b++) {
/* 934 */       if (this.dataOffsets[b] > Integer.MAX_VALUE - j) {
/* 935 */         throw new RasterFormatException("Incorrect band offset: " + this.dataOffsets[b]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 940 */       int m = j + this.dataOffsets[b];
/*     */       
/* 942 */       if (m > k) {
/* 943 */         k = m;
/*     */       }
/*     */     } 
/* 946 */     if (this.data.length <= k) {
/* 947 */       throw new RasterFormatException("Data array too small (should be > " + k + " )");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 953 */     return new String("ByteComponentRaster: width = " + this.width + " height = " + this.height + " #numDataElements " + this.numDataElements + " dataOff[0] = " + this.dataOffsets[0]);
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ByteComponentRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */