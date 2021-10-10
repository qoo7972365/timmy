/*      */ package sun.awt.image;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.ComponentSampleModel;
/*      */ import java.awt.image.DataBuffer;
/*      */ import java.awt.image.DataBufferByte;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RasterFormatException;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.SinglePixelPackedSampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ByteInterleavedRaster
/*      */   extends ByteComponentRaster
/*      */ {
/*      */   boolean inOrder;
/*      */   int dbOffset;
/*      */   int dbOffsetPacked;
/*      */   boolean packed = false;
/*      */   int[] bitMasks;
/*      */   int[] bitOffsets;
/*      */   private int maxX;
/*      */   private int maxY;
/*      */   
/*      */   public ByteInterleavedRaster(SampleModel paramSampleModel, Point paramPoint) {
/*   89 */     this(paramSampleModel, paramSampleModel
/*   90 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*      */ 
/*      */           
/*   93 */           .getWidth(), paramSampleModel
/*   94 */           .getHeight()), paramPoint, (ByteInterleavedRaster)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteInterleavedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/*  113 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*      */ 
/*      */ 
/*      */           
/*  117 */           .getWidth(), paramSampleModel
/*  118 */           .getHeight()), paramPoint, (ByteInterleavedRaster)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isInterleaved(ComponentSampleModel paramComponentSampleModel) {
/*  134 */     int i = this.sampleModel.getNumBands();
/*  135 */     if (i == 1) {
/*  136 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  140 */     int[] arrayOfInt1 = paramComponentSampleModel.getBankIndices();
/*  141 */     for (byte b1 = 0; b1 < i; b1++) {
/*  142 */       if (arrayOfInt1[b1] != 0) {
/*  143 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  148 */     int[] arrayOfInt2 = paramComponentSampleModel.getBandOffsets();
/*  149 */     int j = arrayOfInt2[0];
/*  150 */     int k = j;
/*  151 */     for (byte b2 = 1; b2 < i; b2++) {
/*  152 */       int m = arrayOfInt2[b2];
/*  153 */       if (m < j) {
/*  154 */         j = m;
/*      */       }
/*  156 */       if (m > k) {
/*  157 */         k = m;
/*      */       }
/*      */     } 
/*  160 */     if (k - j >= paramComponentSampleModel.getPixelStride()) {
/*  161 */       return false;
/*      */     }
/*      */     
/*  164 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteInterleavedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, ByteInterleavedRaster paramByteInterleavedRaster) {
/*  191 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramByteInterleavedRaster);
/*  192 */     this.maxX = this.minX + this.width;
/*  193 */     this.maxY = this.minY + this.height;
/*      */     
/*  195 */     if (!(paramDataBuffer instanceof DataBufferByte)) {
/*  196 */       throw new RasterFormatException("ByteInterleavedRasters must have byte DataBuffers");
/*      */     }
/*      */ 
/*      */     
/*  200 */     DataBufferByte dataBufferByte = (DataBufferByte)paramDataBuffer;
/*  201 */     this.data = stealData(dataBufferByte, 0);
/*      */     
/*  203 */     int i = paramRectangle.x - paramPoint.x;
/*  204 */     int j = paramRectangle.y - paramPoint.y;
/*  205 */     if (paramSampleModel instanceof java.awt.image.PixelInterleavedSampleModel || (paramSampleModel instanceof ComponentSampleModel && 
/*      */       
/*  207 */       isInterleaved((ComponentSampleModel)paramSampleModel))) {
/*  208 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramSampleModel;
/*  209 */       this.scanlineStride = componentSampleModel.getScanlineStride();
/*  210 */       this.pixelStride = componentSampleModel.getPixelStride();
/*  211 */       this.dataOffsets = componentSampleModel.getBandOffsets();
/*  212 */       for (byte b = 0; b < getNumDataElements(); b++) {
/*  213 */         this.dataOffsets[b] = this.dataOffsets[b] + i * this.pixelStride + j * this.scanlineStride;
/*      */       }
/*  215 */     } else if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/*  216 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*      */       
/*  218 */       this.packed = true;
/*  219 */       this.bitMasks = singlePixelPackedSampleModel.getBitMasks();
/*  220 */       this.bitOffsets = singlePixelPackedSampleModel.getBitOffsets();
/*  221 */       this.scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
/*  222 */       this.pixelStride = 1;
/*  223 */       this.dataOffsets = new int[1];
/*  224 */       this.dataOffsets[0] = dataBufferByte.getOffset();
/*  225 */       this.dataOffsets[0] = this.dataOffsets[0] + i * this.pixelStride + j * this.scanlineStride;
/*      */     } else {
/*  227 */       throw new RasterFormatException("ByteInterleavedRasters must have PixelInterleavedSampleModel, SinglePixelPackedSampleModel or interleaved ComponentSampleModel.  Sample model is " + paramSampleModel);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  232 */     this.bandOffset = this.dataOffsets[0];
/*      */     
/*  234 */     this.dbOffsetPacked = paramDataBuffer.getOffset() - this.sampleModelTranslateY * this.scanlineStride - this.sampleModelTranslateX * this.pixelStride;
/*      */ 
/*      */     
/*  237 */     this.dbOffset = this.dbOffsetPacked - i * this.pixelStride + j * this.scanlineStride;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  242 */     this.inOrder = false;
/*  243 */     if (this.numDataElements == this.pixelStride) {
/*  244 */       this.inOrder = true;
/*  245 */       for (byte b = 1; b < this.numDataElements; b++) {
/*  246 */         if (this.dataOffsets[b] - this.dataOffsets[0] != b) {
/*  247 */           this.inOrder = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  253 */     verify();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getDataOffsets() {
/*  262 */     return (int[])this.dataOffsets.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDataOffset(int paramInt) {
/*  272 */     return this.dataOffsets[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getScanlineStride() {
/*  281 */     return this.scanlineStride;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPixelStride() {
/*  289 */     return this.pixelStride;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getDataStorage() {
/*  296 */     return this.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getDataElements(int paramInt1, int paramInt2, Object paramObject) {
/*      */     byte[] arrayOfByte;
/*  316 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*      */     {
/*  318 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  322 */     if (paramObject == null) {
/*  323 */       arrayOfByte = new byte[this.numDataElements];
/*      */     } else {
/*  325 */       arrayOfByte = (byte[])paramObject;
/*      */     } 
/*  327 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*      */ 
/*      */     
/*  330 */     for (byte b = 0; b < this.numDataElements; b++) {
/*  331 */       arrayOfByte[b] = this.data[this.dataOffsets[b] + i];
/*      */     }
/*      */     
/*  334 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*  364 */     return getByteData(paramInt1, paramInt2, paramInt3, paramInt4, (byte[])paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfbyte) {
/*  389 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  391 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  394 */     if (paramArrayOfbyte == null) {
/*  395 */       paramArrayOfbyte = new byte[paramInt3 * paramInt4];
/*      */     }
/*  397 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*      */ 
/*      */     
/*  400 */     int j = 0;
/*      */ 
/*      */ 
/*      */     
/*  404 */     if (this.pixelStride == 1) {
/*  405 */       if (this.scanlineStride == paramInt3) {
/*  406 */         System.arraycopy(this.data, i, paramArrayOfbyte, 0, paramInt3 * paramInt4);
/*      */       } else {
/*  408 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  409 */           System.arraycopy(this.data, i, paramArrayOfbyte, j, paramInt3);
/*  410 */           j += paramInt3;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  414 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  415 */         int k = i;
/*  416 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/*  417 */           paramArrayOfbyte[j++] = this.data[k];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  422 */     return paramArrayOfbyte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/*  447 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  449 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  452 */     if (paramArrayOfbyte == null) {
/*  453 */       paramArrayOfbyte = new byte[this.numDataElements * paramInt3 * paramInt4];
/*      */     }
/*  455 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*      */ 
/*      */     
/*  458 */     int j = 0;
/*      */ 
/*      */ 
/*      */     
/*  462 */     if (this.inOrder) {
/*  463 */       i += this.dataOffsets[0];
/*  464 */       int k = paramInt3 * this.pixelStride;
/*  465 */       if (this.scanlineStride == k) {
/*  466 */         System.arraycopy(this.data, i, paramArrayOfbyte, j, k * paramInt4);
/*      */       } else {
/*  468 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  469 */           System.arraycopy(this.data, i, paramArrayOfbyte, j, k);
/*  470 */           j += k;
/*      */         } 
/*      */       } 
/*  473 */     } else if (this.numDataElements == 1) {
/*  474 */       i += this.dataOffsets[0];
/*  475 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  476 */         int k = i;
/*  477 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/*  478 */           paramArrayOfbyte[j++] = this.data[k];
/*      */         }
/*      */       } 
/*  481 */     } else if (this.numDataElements == 2) {
/*  482 */       i += this.dataOffsets[0];
/*  483 */       int k = this.dataOffsets[1] - this.dataOffsets[0];
/*  484 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  485 */         int m = i;
/*  486 */         for (byte b1 = 0; b1 < paramInt3; b1++, m += this.pixelStride) {
/*  487 */           paramArrayOfbyte[j++] = this.data[m];
/*  488 */           paramArrayOfbyte[j++] = this.data[m + k];
/*      */         } 
/*      */       } 
/*  491 */     } else if (this.numDataElements == 3) {
/*  492 */       i += this.dataOffsets[0];
/*  493 */       int k = this.dataOffsets[1] - this.dataOffsets[0];
/*  494 */       int m = this.dataOffsets[2] - this.dataOffsets[0];
/*  495 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  496 */         int n = i;
/*  497 */         for (byte b1 = 0; b1 < paramInt3; b1++, n += this.pixelStride) {
/*  498 */           paramArrayOfbyte[j++] = this.data[n];
/*  499 */           paramArrayOfbyte[j++] = this.data[n + k];
/*  500 */           paramArrayOfbyte[j++] = this.data[n + m];
/*      */         } 
/*      */       } 
/*  503 */     } else if (this.numDataElements == 4) {
/*  504 */       i += this.dataOffsets[0];
/*  505 */       int k = this.dataOffsets[1] - this.dataOffsets[0];
/*  506 */       int m = this.dataOffsets[2] - this.dataOffsets[0];
/*  507 */       int n = this.dataOffsets[3] - this.dataOffsets[0];
/*  508 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  509 */         int i1 = i;
/*  510 */         for (byte b1 = 0; b1 < paramInt3; b1++, i1 += this.pixelStride) {
/*  511 */           paramArrayOfbyte[j++] = this.data[i1];
/*  512 */           paramArrayOfbyte[j++] = this.data[i1 + k];
/*  513 */           paramArrayOfbyte[j++] = this.data[i1 + m];
/*  514 */           paramArrayOfbyte[j++] = this.data[i1 + n];
/*      */         } 
/*      */       } 
/*      */     } else {
/*  518 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  519 */         int k = i;
/*  520 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/*  521 */           for (byte b2 = 0; b2 < this.numDataElements; b2++) {
/*  522 */             paramArrayOfbyte[j++] = this.data[this.dataOffsets[b2] + k];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  528 */     return paramArrayOfbyte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject) {
/*  544 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*      */     {
/*  546 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  549 */     byte[] arrayOfByte = (byte[])paramObject;
/*  550 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*      */ 
/*      */     
/*  553 */     for (byte b = 0; b < this.numDataElements; b++) {
/*  554 */       this.data[this.dataOffsets[b] + i] = arrayOfByte[b];
/*      */     }
/*      */     
/*  557 */     markDirty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataElements(int paramInt1, int paramInt2, Raster paramRaster) {
/*  569 */     int i = paramRaster.getMinX();
/*  570 */     int j = paramRaster.getMinY();
/*  571 */     int k = paramInt1 + i;
/*  572 */     int m = paramInt2 + j;
/*  573 */     int n = paramRaster.getWidth();
/*  574 */     int i1 = paramRaster.getHeight();
/*  575 */     if (k < this.minX || m < this.minY || k + n > this.maxX || m + i1 > this.maxY)
/*      */     {
/*  577 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  581 */     setDataElements(k, m, i, j, n, i1, paramRaster);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Raster paramRaster) {
/*  606 */     if (paramInt5 <= 0 || paramInt6 <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  612 */     int i = paramRaster.getMinX();
/*  613 */     int j = paramRaster.getMinY();
/*  614 */     Object object = null;
/*      */     
/*  616 */     if (paramRaster instanceof ByteInterleavedRaster) {
/*  617 */       ByteInterleavedRaster byteInterleavedRaster = (ByteInterleavedRaster)paramRaster;
/*  618 */       byte[] arrayOfByte = byteInterleavedRaster.getDataStorage();
/*      */       
/*  620 */       if (this.inOrder && byteInterleavedRaster.inOrder && this.pixelStride == byteInterleavedRaster.pixelStride) {
/*  621 */         int k = byteInterleavedRaster.getDataOffset(0);
/*  622 */         int m = byteInterleavedRaster.getScanlineStride();
/*  623 */         int n = byteInterleavedRaster.getPixelStride();
/*      */         
/*  625 */         int i1 = k + (paramInt4 - j) * m + (paramInt3 - i) * n;
/*      */ 
/*      */         
/*  628 */         int i2 = this.dataOffsets[0] + (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*      */ 
/*      */ 
/*      */         
/*  632 */         int i3 = paramInt5 * this.pixelStride;
/*  633 */         for (byte b1 = 0; b1 < paramInt6; b1++) {
/*  634 */           System.arraycopy(arrayOfByte, i1, this.data, i2, i3);
/*      */           
/*  636 */           i1 += m;
/*  637 */           i2 += this.scanlineStride;
/*      */         } 
/*  639 */         markDirty();
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  644 */     for (byte b = 0; b < paramInt6; b++) {
/*      */       
/*  646 */       object = paramRaster.getDataElements(i, j + b, paramInt5, 1, object);
/*      */       
/*  648 */       setDataElements(paramInt1, paramInt2 + b, paramInt5, 1, object);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*  675 */     putByteData(paramInt1, paramInt2, paramInt3, paramInt4, (byte[])paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfbyte) {
/*  699 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  701 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  704 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride + this.dataOffsets[paramInt5];
/*      */ 
/*      */     
/*  707 */     int j = 0;
/*      */ 
/*      */ 
/*      */     
/*  711 */     if (this.pixelStride == 1) {
/*  712 */       if (this.scanlineStride == paramInt3) {
/*  713 */         System.arraycopy(paramArrayOfbyte, 0, this.data, i, paramInt3 * paramInt4);
/*      */       } else {
/*      */         
/*  716 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  717 */           System.arraycopy(paramArrayOfbyte, j, this.data, i, paramInt3);
/*  718 */           j += paramInt3;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  723 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  724 */         int k = i;
/*  725 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/*  726 */           this.data[k] = paramArrayOfbyte[j++];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  731 */     markDirty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/*  752 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  754 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  757 */     int i = (paramInt2 - this.minY) * this.scanlineStride + (paramInt1 - this.minX) * this.pixelStride;
/*      */ 
/*      */ 
/*      */     
/*  761 */     int j = 0;
/*      */ 
/*      */ 
/*      */     
/*  765 */     if (this.inOrder) {
/*  766 */       i += this.dataOffsets[0];
/*  767 */       int k = paramInt3 * this.pixelStride;
/*  768 */       if (k == this.scanlineStride) {
/*  769 */         System.arraycopy(paramArrayOfbyte, 0, this.data, i, k * paramInt4);
/*      */       } else {
/*  771 */         for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  772 */           System.arraycopy(paramArrayOfbyte, j, this.data, i, k);
/*  773 */           j += k;
/*      */         } 
/*      */       } 
/*  776 */     } else if (this.numDataElements == 1) {
/*  777 */       i += this.dataOffsets[0];
/*  778 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  779 */         int k = i;
/*  780 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/*  781 */           this.data[k] = paramArrayOfbyte[j++];
/*      */         }
/*      */       } 
/*  784 */     } else if (this.numDataElements == 2) {
/*  785 */       i += this.dataOffsets[0];
/*  786 */       int k = this.dataOffsets[1] - this.dataOffsets[0];
/*  787 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  788 */         int m = i;
/*  789 */         for (byte b1 = 0; b1 < paramInt3; b1++, m += this.pixelStride) {
/*  790 */           this.data[m] = paramArrayOfbyte[j++];
/*  791 */           this.data[m + k] = paramArrayOfbyte[j++];
/*      */         } 
/*      */       } 
/*  794 */     } else if (this.numDataElements == 3) {
/*  795 */       i += this.dataOffsets[0];
/*  796 */       int k = this.dataOffsets[1] - this.dataOffsets[0];
/*  797 */       int m = this.dataOffsets[2] - this.dataOffsets[0];
/*  798 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  799 */         int n = i;
/*  800 */         for (byte b1 = 0; b1 < paramInt3; b1++, n += this.pixelStride) {
/*  801 */           this.data[n] = paramArrayOfbyte[j++];
/*  802 */           this.data[n + k] = paramArrayOfbyte[j++];
/*  803 */           this.data[n + m] = paramArrayOfbyte[j++];
/*      */         } 
/*      */       } 
/*  806 */     } else if (this.numDataElements == 4) {
/*  807 */       i += this.dataOffsets[0];
/*  808 */       int k = this.dataOffsets[1] - this.dataOffsets[0];
/*  809 */       int m = this.dataOffsets[2] - this.dataOffsets[0];
/*  810 */       int n = this.dataOffsets[3] - this.dataOffsets[0];
/*  811 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  812 */         int i1 = i;
/*  813 */         for (byte b1 = 0; b1 < paramInt3; b1++, i1 += this.pixelStride) {
/*  814 */           this.data[i1] = paramArrayOfbyte[j++];
/*  815 */           this.data[i1 + k] = paramArrayOfbyte[j++];
/*  816 */           this.data[i1 + m] = paramArrayOfbyte[j++];
/*  817 */           this.data[i1 + n] = paramArrayOfbyte[j++];
/*      */         } 
/*      */       } 
/*      */     } else {
/*  821 */       for (byte b = 0; b < paramInt4; b++, i += this.scanlineStride) {
/*  822 */         int k = i;
/*  823 */         for (byte b1 = 0; b1 < paramInt3; b1++, k += this.pixelStride) {
/*  824 */           for (byte b2 = 0; b2 < this.numDataElements; b2++) {
/*  825 */             this.data[this.dataOffsets[b2] + k] = paramArrayOfbyte[j++];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  831 */     markDirty();
/*      */   }
/*      */   
/*      */   public int getSample(int paramInt1, int paramInt2, int paramInt3) {
/*  835 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*      */     {
/*  837 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  840 */     if (this.packed) {
/*  841 */       int j = paramInt2 * this.scanlineStride + paramInt1 + this.dbOffsetPacked;
/*  842 */       byte b = this.data[j];
/*  843 */       return (b & this.bitMasks[paramInt3]) >>> this.bitOffsets[paramInt3];
/*      */     } 
/*  845 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.dbOffset;
/*  846 */     return this.data[i + this.dataOffsets[paramInt3]] & 0xFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  851 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*      */     {
/*  853 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  856 */     if (this.packed) {
/*  857 */       int i = paramInt2 * this.scanlineStride + paramInt1 + this.dbOffsetPacked;
/*  858 */       int j = this.bitMasks[paramInt3];
/*      */       
/*  860 */       byte b = this.data[i];
/*  861 */       b = (byte)(b & (j ^ 0xFFFFFFFF));
/*  862 */       b = (byte)(b | paramInt4 << this.bitOffsets[paramInt3] & j);
/*  863 */       this.data[i] = b;
/*      */     } else {
/*  865 */       int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.dbOffset;
/*  866 */       this.data[i + this.dataOffsets[paramInt3]] = (byte)paramInt4;
/*      */     } 
/*      */     
/*  869 */     markDirty();
/*      */   }
/*      */   
/*      */   public int[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
/*      */     int[] arrayOfInt;
/*  874 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  876 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  880 */     if (paramArrayOfint != null) {
/*  881 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  883 */       arrayOfInt = new int[paramInt3 * paramInt4];
/*      */     } 
/*      */     
/*  886 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*  887 */     byte b = 0;
/*      */     
/*  889 */     if (this.packed) {
/*  890 */       i += this.dbOffsetPacked;
/*  891 */       int j = this.bitMasks[paramInt5];
/*  892 */       int k = this.bitOffsets[paramInt5];
/*      */       
/*  894 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  895 */         int m = i;
/*  896 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/*  897 */           byte b3 = this.data[m++];
/*  898 */           arrayOfInt[b++] = (b3 & j) >>> k;
/*      */         } 
/*  900 */         i += this.scanlineStride;
/*      */       } 
/*      */     } else {
/*  903 */       i += this.dbOffset + this.dataOffsets[paramInt5];
/*  904 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  905 */         int j = i;
/*  906 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/*  907 */           arrayOfInt[b++] = this.data[j] & 0xFF;
/*  908 */           j += this.pixelStride;
/*      */         } 
/*  910 */         i += this.scanlineStride;
/*      */       } 
/*      */     } 
/*      */     
/*  914 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
/*  918 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  920 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  923 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*  924 */     byte b = 0;
/*      */     
/*  926 */     if (this.packed) {
/*  927 */       i += this.dbOffsetPacked;
/*  928 */       int j = this.bitMasks[paramInt5];
/*      */       
/*  930 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  931 */         int k = i;
/*  932 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/*  933 */           byte b3 = this.data[k];
/*  934 */           b3 = (byte)(b3 & (j ^ 0xFFFFFFFF));
/*  935 */           int m = paramArrayOfint[b++];
/*  936 */           b3 = (byte)(b3 | m << this.bitOffsets[paramInt5] & j);
/*  937 */           this.data[k++] = b3;
/*      */         } 
/*  939 */         i += this.scanlineStride;
/*      */       } 
/*      */     } else {
/*  942 */       i += this.dbOffset + this.dataOffsets[paramInt5];
/*  943 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  944 */         int j = i;
/*  945 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/*  946 */           this.data[j] = (byte)paramArrayOfint[b++];
/*  947 */           j += this.pixelStride;
/*      */         } 
/*  949 */         i += this.scanlineStride;
/*      */       } 
/*      */     } 
/*      */     
/*  953 */     markDirty();
/*      */   }
/*      */   public int[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/*      */     int[] arrayOfInt;
/*  957 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  959 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  963 */     if (paramArrayOfint != null) {
/*  964 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  966 */       arrayOfInt = new int[paramInt3 * paramInt4 * this.numBands];
/*      */     } 
/*      */     
/*  969 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*  970 */     byte b = 0;
/*      */     
/*  972 */     if (this.packed) {
/*  973 */       i += this.dbOffsetPacked;
/*  974 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  975 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/*  976 */           byte b3 = this.data[i + b2];
/*  977 */           for (byte b4 = 0; b4 < this.numBands; b4++) {
/*  978 */             arrayOfInt[b++] = (b3 & this.bitMasks[b4]) >>> this.bitOffsets[b4];
/*      */           }
/*      */         } 
/*      */         
/*  982 */         i += this.scanlineStride;
/*      */       } 
/*      */     } else {
/*  985 */       i += this.dbOffset;
/*  986 */       int j = this.dataOffsets[0];
/*      */       
/*  988 */       if (this.numBands == 1) {
/*  989 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  990 */           int k = i + j;
/*  991 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/*  992 */             arrayOfInt[b++] = this.data[k] & 0xFF;
/*  993 */             k += this.pixelStride;
/*      */           } 
/*  995 */           i += this.scanlineStride;
/*      */         } 
/*  997 */       } else if (this.numBands == 2) {
/*  998 */         int k = this.dataOffsets[1] - j;
/*  999 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1000 */           int m = i + j;
/* 1001 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1002 */             arrayOfInt[b++] = this.data[m] & 0xFF;
/* 1003 */             arrayOfInt[b++] = this.data[m + k] & 0xFF;
/* 1004 */             m += this.pixelStride;
/*      */           } 
/* 1006 */           i += this.scanlineStride;
/*      */         } 
/* 1008 */       } else if (this.numBands == 3) {
/* 1009 */         int k = this.dataOffsets[1] - j;
/* 1010 */         int m = this.dataOffsets[2] - j;
/* 1011 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1012 */           int n = i + j;
/* 1013 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1014 */             arrayOfInt[b++] = this.data[n] & 0xFF;
/* 1015 */             arrayOfInt[b++] = this.data[n + k] & 0xFF;
/* 1016 */             arrayOfInt[b++] = this.data[n + m] & 0xFF;
/* 1017 */             n += this.pixelStride;
/*      */           } 
/* 1019 */           i += this.scanlineStride;
/*      */         } 
/* 1021 */       } else if (this.numBands == 4) {
/* 1022 */         int k = this.dataOffsets[1] - j;
/* 1023 */         int m = this.dataOffsets[2] - j;
/* 1024 */         int n = this.dataOffsets[3] - j;
/* 1025 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1026 */           int i1 = i + j;
/* 1027 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1028 */             arrayOfInt[b++] = this.data[i1] & 0xFF;
/* 1029 */             arrayOfInt[b++] = this.data[i1 + k] & 0xFF;
/* 1030 */             arrayOfInt[b++] = this.data[i1 + m] & 0xFF;
/* 1031 */             arrayOfInt[b++] = this.data[i1 + n] & 0xFF;
/* 1032 */             i1 += this.pixelStride;
/*      */           } 
/* 1034 */           i += this.scanlineStride;
/*      */         } 
/*      */       } else {
/* 1037 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1038 */           int k = i;
/* 1039 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1040 */             for (byte b3 = 0; b3 < this.numBands; b3++) {
/* 1041 */               arrayOfInt[b++] = this.data[k + this.dataOffsets[b3]] & 0xFF;
/*      */             }
/*      */             
/* 1044 */             k += this.pixelStride;
/*      */           } 
/* 1046 */           i += this.scanlineStride;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1051 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 1055 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/* 1057 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1060 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/* 1061 */     byte b = 0;
/*      */     
/* 1063 */     if (this.packed) {
/* 1064 */       i += this.dbOffsetPacked;
/* 1065 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1066 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1067 */           int j = 0;
/* 1068 */           for (byte b3 = 0; b3 < this.numBands; b3++) {
/* 1069 */             int k = paramArrayOfint[b++];
/* 1070 */             j |= k << this.bitOffsets[b3] & this.bitMasks[b3];
/*      */           } 
/*      */           
/* 1073 */           this.data[i + b2] = (byte)j;
/*      */         } 
/* 1075 */         i += this.scanlineStride;
/*      */       } 
/*      */     } else {
/* 1078 */       i += this.dbOffset;
/* 1079 */       int j = this.dataOffsets[0];
/*      */       
/* 1081 */       if (this.numBands == 1) {
/* 1082 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1083 */           int k = i + j;
/* 1084 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1085 */             this.data[k] = (byte)paramArrayOfint[b++];
/* 1086 */             k += this.pixelStride;
/*      */           } 
/* 1088 */           i += this.scanlineStride;
/*      */         } 
/* 1090 */       } else if (this.numBands == 2) {
/* 1091 */         int k = this.dataOffsets[1] - j;
/* 1092 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1093 */           int m = i + j;
/* 1094 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1095 */             this.data[m] = (byte)paramArrayOfint[b++];
/* 1096 */             this.data[m + k] = (byte)paramArrayOfint[b++];
/* 1097 */             m += this.pixelStride;
/*      */           } 
/* 1099 */           i += this.scanlineStride;
/*      */         } 
/* 1101 */       } else if (this.numBands == 3) {
/* 1102 */         int k = this.dataOffsets[1] - j;
/* 1103 */         int m = this.dataOffsets[2] - j;
/* 1104 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1105 */           int n = i + j;
/* 1106 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1107 */             this.data[n] = (byte)paramArrayOfint[b++];
/* 1108 */             this.data[n + k] = (byte)paramArrayOfint[b++];
/* 1109 */             this.data[n + m] = (byte)paramArrayOfint[b++];
/* 1110 */             n += this.pixelStride;
/*      */           } 
/* 1112 */           i += this.scanlineStride;
/*      */         } 
/* 1114 */       } else if (this.numBands == 4) {
/* 1115 */         int k = this.dataOffsets[1] - j;
/* 1116 */         int m = this.dataOffsets[2] - j;
/* 1117 */         int n = this.dataOffsets[3] - j;
/* 1118 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1119 */           int i1 = i + j;
/* 1120 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1121 */             this.data[i1] = (byte)paramArrayOfint[b++];
/* 1122 */             this.data[i1 + k] = (byte)paramArrayOfint[b++];
/* 1123 */             this.data[i1 + m] = (byte)paramArrayOfint[b++];
/* 1124 */             this.data[i1 + n] = (byte)paramArrayOfint[b++];
/* 1125 */             i1 += this.pixelStride;
/*      */           } 
/* 1127 */           i += this.scanlineStride;
/*      */         } 
/*      */       } else {
/* 1130 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 1131 */           int k = i;
/* 1132 */           for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 1133 */             for (byte b3 = 0; b3 < this.numBands; b3++) {
/* 1134 */               this.data[k + this.dataOffsets[b3]] = (byte)paramArrayOfint[b++];
/*      */             }
/*      */             
/* 1137 */             k += this.pixelStride;
/*      */           } 
/* 1139 */           i += this.scanlineStride;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1144 */     markDirty();
/*      */   }
/*      */   
/*      */   public void setRect(int paramInt1, int paramInt2, Raster paramRaster) {
/* 1148 */     if (!(paramRaster instanceof ByteInterleavedRaster)) {
/* 1149 */       super.setRect(paramInt1, paramInt2, paramRaster);
/*      */       
/*      */       return;
/*      */     } 
/* 1153 */     int i = paramRaster.getWidth();
/* 1154 */     int j = paramRaster.getHeight();
/* 1155 */     int k = paramRaster.getMinX();
/* 1156 */     int m = paramRaster.getMinY();
/* 1157 */     int n = paramInt1 + k;
/* 1158 */     int i1 = paramInt2 + m;
/*      */ 
/*      */     
/* 1161 */     if (n < this.minX) {
/* 1162 */       int i2 = this.minX - n;
/* 1163 */       i -= i2;
/* 1164 */       k += i2;
/* 1165 */       n = this.minX;
/*      */     } 
/* 1167 */     if (i1 < this.minY) {
/* 1168 */       int i2 = this.minY - i1;
/* 1169 */       j -= i2;
/* 1170 */       m += i2;
/* 1171 */       i1 = this.minY;
/*      */     } 
/* 1173 */     if (n + i > this.maxX) {
/* 1174 */       i = this.maxX - n;
/*      */     }
/* 1176 */     if (i1 + j > this.maxY) {
/* 1177 */       j = this.maxY - i1;
/*      */     }
/*      */     
/* 1180 */     setDataElements(n, i1, k, m, i, j, paramRaster);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Raster createChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/* 1208 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster createWritableChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/*      */     SampleModel sampleModel;
/* 1238 */     if (paramInt1 < this.minX) {
/* 1239 */       throw new RasterFormatException("x lies outside the raster");
/*      */     }
/* 1241 */     if (paramInt2 < this.minY) {
/* 1242 */       throw new RasterFormatException("y lies outside the raster");
/*      */     }
/* 1244 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 1245 */       throw new RasterFormatException("(x + width) is outside of Raster");
/*      */     }
/* 1247 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 1248 */       throw new RasterFormatException("(y + height) is outside of Raster");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1253 */     if (paramArrayOfint != null) {
/* 1254 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*      */     } else {
/* 1256 */       sampleModel = this.sampleModel;
/*      */     } 
/* 1258 */     int i = paramInt5 - paramInt1;
/* 1259 */     int j = paramInt6 - paramInt2;
/*      */     
/* 1261 */     return new ByteInterleavedRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 1274 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 1275 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*      */     }
/*      */ 
/*      */     
/* 1279 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*      */     
/* 1281 */     return new ByteInterleavedRaster(sampleModel, new Point(0, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster createCompatibleWritableRaster() {
/* 1292 */     return createCompatibleWritableRaster(this.width, this.height);
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1296 */     return new String("ByteInterleavedRaster: width = " + this.width + " height = " + this.height + " #numDataElements " + this.numDataElements + " dataOff[0] = " + this.dataOffsets[0]);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ByteInterleavedRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */