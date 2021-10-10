/*      */ package sun.awt.image;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.DataBuffer;
/*      */ import java.awt.image.DataBufferByte;
/*      */ import java.awt.image.MultiPixelPackedSampleModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RasterFormatException;
/*      */ import java.awt.image.SampleModel;
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
/*      */ public class BytePackedRaster
/*      */   extends SunWritableRaster
/*      */ {
/*      */   int dataBitOffset;
/*      */   int scanlineStride;
/*      */   int pixelBitStride;
/*      */   int bitMask;
/*      */   byte[] data;
/*      */   int shiftOffset;
/*      */   int type;
/*      */   private int maxX;
/*      */   private int maxY;
/*      */   
/*      */   static {
/*   79 */     NativeLibLoader.loadLibraries();
/*   80 */     initIDs();
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
/*      */   public BytePackedRaster(SampleModel paramSampleModel, Point paramPoint) {
/*   94 */     this(paramSampleModel, paramSampleModel
/*   95 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*      */ 
/*      */           
/*   98 */           .getWidth(), paramSampleModel
/*   99 */           .getHeight()), paramPoint, (BytePackedRaster)null);
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
/*      */   public BytePackedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/*  117 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*      */ 
/*      */ 
/*      */           
/*  121 */           .getWidth(), paramSampleModel
/*  122 */           .getHeight()), paramPoint, (BytePackedRaster)null);
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
/*      */   
/*      */   public BytePackedRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, BytePackedRaster paramBytePackedRaster) {
/*  153 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramBytePackedRaster);
/*  154 */     this.maxX = this.minX + this.width;
/*  155 */     this.maxY = this.minY + this.height;
/*      */     
/*  157 */     if (!(paramDataBuffer instanceof DataBufferByte)) {
/*  158 */       throw new RasterFormatException("BytePackedRasters must havebyte DataBuffers");
/*      */     }
/*      */     
/*  161 */     DataBufferByte dataBufferByte = (DataBufferByte)paramDataBuffer;
/*  162 */     this.data = stealData(dataBufferByte, 0);
/*  163 */     if (dataBufferByte.getNumBanks() != 1) {
/*  164 */       throw new RasterFormatException("DataBuffer for BytePackedRasters must only have 1 bank.");
/*      */     }
/*      */ 
/*      */     
/*  168 */     int i = dataBufferByte.getOffset();
/*      */     
/*  170 */     if (paramSampleModel instanceof MultiPixelPackedSampleModel) {
/*  171 */       MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)paramSampleModel;
/*      */       
/*  173 */       this.type = 11;
/*  174 */       this.pixelBitStride = multiPixelPackedSampleModel.getPixelBitStride();
/*  175 */       if (this.pixelBitStride != 1 && this.pixelBitStride != 2 && this.pixelBitStride != 4)
/*      */       {
/*      */         
/*  178 */         throw new RasterFormatException("BytePackedRasters must have a bit depth of 1, 2, or 4");
/*      */       }
/*      */       
/*  181 */       this.scanlineStride = multiPixelPackedSampleModel.getScanlineStride();
/*  182 */       this.dataBitOffset = multiPixelPackedSampleModel.getDataBitOffset() + i * 8;
/*  183 */       int j = paramRectangle.x - paramPoint.x;
/*  184 */       int k = paramRectangle.y - paramPoint.y;
/*  185 */       this.dataBitOffset += j * this.pixelBitStride + k * this.scanlineStride * 8;
/*  186 */       this.bitMask = (1 << this.pixelBitStride) - 1;
/*  187 */       this.shiftOffset = 8 - this.pixelBitStride;
/*      */     } else {
/*  189 */       throw new RasterFormatException("BytePackedRasters must haveMultiPixelPackedSampleModel");
/*      */     } 
/*      */     
/*  192 */     verify(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDataBitOffset() {
/*  201 */     return this.dataBitOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getScanlineStride() {
/*  210 */     return this.scanlineStride;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPixelBitStride() {
/*  218 */     return this.pixelBitStride;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getDataStorage() {
/*  225 */     return this.data;
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
/*  245 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*      */     {
/*  247 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  251 */     if (paramObject == null) {
/*  252 */       arrayOfByte = new byte[this.numDataElements];
/*      */     } else {
/*  254 */       arrayOfByte = (byte[])paramObject;
/*      */     } 
/*  256 */     int i = this.dataBitOffset + (paramInt1 - this.minX) * this.pixelBitStride;
/*      */     
/*  258 */     int j = this.data[(paramInt2 - this.minY) * this.scanlineStride + (i >> 3)] & 0xFF;
/*  259 */     int k = this.shiftOffset - (i & 0x7);
/*  260 */     arrayOfByte[0] = (byte)(j >> k & this.bitMask);
/*  261 */     return arrayOfByte;
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
/*      */   public Object getDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*  290 */     return getByteData(paramInt1, paramInt2, paramInt3, paramInt4, (byte[])paramObject);
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
/*      */   public Object getPixelData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*      */     byte[] arrayOfByte1;
/*  319 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  321 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  325 */     if (paramObject == null) {
/*  326 */       arrayOfByte1 = new byte[this.numDataElements * paramInt3 * paramInt4];
/*      */     } else {
/*  328 */       arrayOfByte1 = (byte[])paramObject;
/*      */     } 
/*  330 */     int i = this.pixelBitStride;
/*  331 */     int j = this.dataBitOffset + (paramInt1 - this.minX) * i;
/*  332 */     int k = (paramInt2 - this.minY) * this.scanlineStride;
/*  333 */     byte b1 = 0;
/*  334 */     byte[] arrayOfByte2 = this.data;
/*      */     
/*  336 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/*  337 */       int m = j;
/*  338 */       for (byte b = 0; b < paramInt3; b++) {
/*  339 */         int n = this.shiftOffset - (m & 0x7);
/*  340 */         arrayOfByte1[b1++] = (byte)(this.bitMask & arrayOfByte2[k + (m >> 3)] >> n);
/*      */         
/*  342 */         m += i;
/*      */       } 
/*  344 */       k += this.scanlineStride;
/*      */     } 
/*  346 */     return arrayOfByte1;
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
/*      */   public byte[] getByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfbyte) {
/*  370 */     return getByteData(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfbyte);
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
/*      */   public byte[] getByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/*  393 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  395 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  398 */     if (paramArrayOfbyte == null) {
/*  399 */       paramArrayOfbyte = new byte[paramInt3 * paramInt4];
/*      */     }
/*  401 */     int i = this.pixelBitStride;
/*  402 */     int j = this.dataBitOffset + (paramInt1 - this.minX) * i;
/*  403 */     int k = (paramInt2 - this.minY) * this.scanlineStride;
/*  404 */     byte b1 = 0;
/*  405 */     byte[] arrayOfByte = this.data;
/*      */     
/*  407 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/*  408 */       int m = j;
/*      */ 
/*      */ 
/*      */       
/*  412 */       byte b = 0;
/*  413 */       while (b < paramInt3 && (m & 0x7) != 0) {
/*  414 */         int i1 = this.shiftOffset - (m & 0x7);
/*  415 */         paramArrayOfbyte[b1++] = (byte)(this.bitMask & arrayOfByte[k + (m >> 3)] >> i1);
/*      */         
/*  417 */         m += i;
/*  418 */         b++;
/*      */       } 
/*      */ 
/*      */       
/*  422 */       int n = k + (m >> 3);
/*  423 */       switch (i) {
/*      */         case 1:
/*  425 */           for (; b < paramInt3 - 7; b += 8) {
/*  426 */             byte b3 = arrayOfByte[n++];
/*  427 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 7 & 0x1);
/*  428 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 6 & 0x1);
/*  429 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 5 & 0x1);
/*  430 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 4 & 0x1);
/*  431 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 3 & 0x1);
/*  432 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 2 & 0x1);
/*  433 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 1 & 0x1);
/*  434 */             paramArrayOfbyte[b1++] = (byte)(b3 & 0x1);
/*  435 */             m += 8;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/*  440 */           for (; b < paramInt3 - 7; b += 8) {
/*  441 */             byte b3 = arrayOfByte[n++];
/*  442 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 6 & 0x3);
/*  443 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 4 & 0x3);
/*  444 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 2 & 0x3);
/*  445 */             paramArrayOfbyte[b1++] = (byte)(b3 & 0x3);
/*      */             
/*  447 */             b3 = arrayOfByte[n++];
/*  448 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 6 & 0x3);
/*  449 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 4 & 0x3);
/*  450 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 2 & 0x3);
/*  451 */             paramArrayOfbyte[b1++] = (byte)(b3 & 0x3);
/*      */             
/*  453 */             m += 16;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 4:
/*  458 */           for (; b < paramInt3 - 7; b += 8) {
/*  459 */             byte b3 = arrayOfByte[n++];
/*  460 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 4 & 0xF);
/*  461 */             paramArrayOfbyte[b1++] = (byte)(b3 & 0xF);
/*      */             
/*  463 */             b3 = arrayOfByte[n++];
/*  464 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 4 & 0xF);
/*  465 */             paramArrayOfbyte[b1++] = (byte)(b3 & 0xF);
/*      */             
/*  467 */             b3 = arrayOfByte[n++];
/*  468 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 4 & 0xF);
/*  469 */             paramArrayOfbyte[b1++] = (byte)(b3 & 0xF);
/*      */             
/*  471 */             b3 = arrayOfByte[n++];
/*  472 */             paramArrayOfbyte[b1++] = (byte)(b3 >> 4 & 0xF);
/*  473 */             paramArrayOfbyte[b1++] = (byte)(b3 & 0xF);
/*      */             
/*  475 */             m += 32;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/*  481 */       for (; b < paramInt3; b++) {
/*  482 */         int i1 = this.shiftOffset - (m & 0x7);
/*  483 */         paramArrayOfbyte[b1++] = (byte)(this.bitMask & arrayOfByte[k + (m >> 3)] >> i1);
/*      */         
/*  485 */         m += i;
/*      */       } 
/*      */       
/*  488 */       k += this.scanlineStride;
/*      */     } 
/*      */     
/*  491 */     return paramArrayOfbyte;
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
/*  507 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 >= this.maxX || paramInt2 >= this.maxY)
/*      */     {
/*  509 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  512 */     byte[] arrayOfByte = (byte[])paramObject;
/*  513 */     int i = this.dataBitOffset + (paramInt1 - this.minX) * this.pixelBitStride;
/*  514 */     int j = (paramInt2 - this.minY) * this.scanlineStride + (i >> 3);
/*  515 */     int k = this.shiftOffset - (i & 0x7);
/*      */     
/*  517 */     byte b = this.data[j];
/*  518 */     b = (byte)(b & (this.bitMask << k ^ 0xFFFFFFFF));
/*  519 */     b = (byte)(b | (arrayOfByte[0] & this.bitMask) << k);
/*  520 */     this.data[j] = b;
/*      */     
/*  522 */     markDirty();
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
/*      */   public void setDataElements(int paramInt1, int paramInt2, Raster paramRaster) {
/*  535 */     if (!(paramRaster instanceof BytePackedRaster) || ((BytePackedRaster)paramRaster).pixelBitStride != this.pixelBitStride) {
/*      */       
/*  537 */       super.setDataElements(paramInt1, paramInt2, paramRaster);
/*      */       
/*      */       return;
/*      */     } 
/*  541 */     int i = paramRaster.getMinX();
/*  542 */     int j = paramRaster.getMinY();
/*  543 */     int k = i + paramInt1;
/*  544 */     int m = j + paramInt2;
/*  545 */     int n = paramRaster.getWidth();
/*  546 */     int i1 = paramRaster.getHeight();
/*  547 */     if (k < this.minX || m < this.minY || k + n > this.maxX || m + i1 > this.maxY)
/*      */     {
/*  549 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  552 */     setDataElements(k, m, i, j, n, i1, (BytePackedRaster)paramRaster);
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
/*      */   private void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, BytePackedRaster paramBytePackedRaster) {
/*  579 */     if (paramInt5 <= 0 || paramInt6 <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  583 */     byte[] arrayOfByte1 = paramBytePackedRaster.data;
/*  584 */     byte[] arrayOfByte2 = this.data;
/*      */     
/*  586 */     int i = paramBytePackedRaster.scanlineStride;
/*  587 */     int j = this.scanlineStride;
/*  588 */     int k = paramBytePackedRaster.dataBitOffset + 8 * (paramInt4 - paramBytePackedRaster.minY) * i + (paramInt3 - paramBytePackedRaster.minX) * paramBytePackedRaster.pixelBitStride;
/*      */ 
/*      */     
/*  591 */     int m = this.dataBitOffset + 8 * (paramInt2 - this.minY) * j + (paramInt1 - this.minX) * this.pixelBitStride;
/*      */ 
/*      */     
/*  594 */     int n = paramInt5 * this.pixelBitStride;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  600 */     if ((k & 0x7) == (m & 0x7)) {
/*      */       
/*  602 */       int i1 = m & 0x7;
/*  603 */       if (i1 != 0) {
/*  604 */         int i2 = 8 - i1;
/*      */         
/*  606 */         int i3 = k >> 3;
/*  607 */         int i4 = m >> 3;
/*  608 */         int i5 = 255 >> i1;
/*  609 */         if (n < i2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  617 */           i5 &= 255 << i2 - n;
/*  618 */           i2 = n;
/*      */         } 
/*  620 */         for (byte b = 0; b < paramInt6; b++) {
/*  621 */           byte b1 = arrayOfByte2[i4];
/*  622 */           int i6 = b1 & (i5 ^ 0xFFFFFFFF);
/*  623 */           i6 |= arrayOfByte1[i3] & i5;
/*  624 */           arrayOfByte2[i4] = (byte)i6;
/*  625 */           i3 += i;
/*  626 */           i4 += j;
/*      */         } 
/*  628 */         k += i2;
/*  629 */         m += i2;
/*  630 */         n -= i2;
/*      */       } 
/*  632 */       if (n >= 8) {
/*      */         
/*  634 */         int i2 = k >> 3;
/*  635 */         int i3 = m >> 3;
/*  636 */         int i4 = n >> 3;
/*  637 */         if (i4 == i && i == j) {
/*  638 */           System.arraycopy(arrayOfByte1, i2, arrayOfByte2, i3, i * paramInt6);
/*      */         }
/*      */         else {
/*      */           
/*  642 */           for (byte b = 0; b < paramInt6; b++) {
/*  643 */             System.arraycopy(arrayOfByte1, i2, arrayOfByte2, i3, i4);
/*      */ 
/*      */             
/*  646 */             i2 += i;
/*  647 */             i3 += j;
/*      */           } 
/*      */         } 
/*      */         
/*  651 */         int i5 = i4 * 8;
/*  652 */         k += i5;
/*  653 */         m += i5;
/*  654 */         n -= i5;
/*      */       } 
/*  656 */       if (n > 0)
/*      */       {
/*  658 */         int i2 = k >> 3;
/*  659 */         int i3 = m >> 3;
/*  660 */         int i4 = 65280 >> n & 0xFF;
/*  661 */         for (byte b = 0; b < paramInt6; b++) {
/*  662 */           byte b1 = arrayOfByte2[i3];
/*  663 */           int i5 = b1 & (i4 ^ 0xFFFFFFFF);
/*  664 */           i5 |= arrayOfByte1[i2] & i4;
/*  665 */           arrayOfByte2[i3] = (byte)i5;
/*  666 */           i2 += i;
/*  667 */           i3 += j;
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  677 */       int i1 = m & 0x7;
/*  678 */       if (i1 != 0 || n < 8) {
/*  679 */         int i2 = 8 - i1;
/*  680 */         int i3 = k >> 3;
/*  681 */         int i4 = m >> 3;
/*      */         
/*  683 */         int i5 = k & 0x7;
/*  684 */         int i6 = 8 - i5;
/*  685 */         int i7 = 255 >> i1;
/*  686 */         if (n < i2) {
/*      */           
/*  688 */           i7 &= 255 << i2 - n;
/*  689 */           i2 = n;
/*      */         } 
/*  691 */         int i8 = arrayOfByte1.length - 1;
/*  692 */         for (byte b = 0; b < paramInt6; b++) {
/*      */ 
/*      */ 
/*      */           
/*  696 */           byte b1 = arrayOfByte1[i3];
/*  697 */           byte b2 = 0;
/*  698 */           if (i3 < i8) {
/*  699 */             b2 = arrayOfByte1[i3 + 1];
/*      */           }
/*      */ 
/*      */           
/*  703 */           byte b3 = arrayOfByte2[i4];
/*  704 */           int i9 = b3 & (i7 ^ 0xFFFFFFFF);
/*  705 */           i9 |= (b1 << i5 | (b2 & 0xFF) >> i6) >> i1 & i7;
/*      */ 
/*      */           
/*  708 */           arrayOfByte2[i4] = (byte)i9;
/*  709 */           i3 += i;
/*  710 */           i4 += j;
/*      */         } 
/*      */         
/*  713 */         k += i2;
/*  714 */         m += i2;
/*  715 */         n -= i2;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  723 */       if (n >= 8) {
/*  724 */         int i2 = k >> 3;
/*  725 */         int i3 = m >> 3;
/*  726 */         int i4 = n >> 3;
/*  727 */         int i5 = k & 0x7;
/*  728 */         int i6 = 8 - i5;
/*      */         int i7;
/*  730 */         for (i7 = 0; i7 < paramInt6; i7++) {
/*  731 */           int i8 = i2 + i7 * i;
/*  732 */           int i9 = i3 + i7 * j;
/*      */           
/*  734 */           byte b = arrayOfByte1[i8];
/*      */           
/*  736 */           for (byte b1 = 0; b1 < i4; b1++) {
/*  737 */             byte b2 = arrayOfByte1[i8 + 1];
/*  738 */             int i10 = b << i5 | (b2 & 0xFF) >> i6;
/*      */             
/*  740 */             arrayOfByte2[i9] = (byte)i10;
/*  741 */             b = b2;
/*      */             
/*  743 */             i8++;
/*  744 */             i9++;
/*      */           } 
/*      */         } 
/*      */         
/*  748 */         i7 = i4 * 8;
/*  749 */         k += i7;
/*  750 */         m += i7;
/*  751 */         n -= i7;
/*      */       } 
/*      */ 
/*      */       
/*  755 */       if (n > 0) {
/*  756 */         int i2 = k >> 3;
/*  757 */         int i3 = m >> 3;
/*  758 */         int i4 = 65280 >> n & 0xFF;
/*  759 */         int i5 = k & 0x7;
/*  760 */         int i6 = 8 - i5;
/*      */         
/*  762 */         int i7 = arrayOfByte1.length - 1;
/*  763 */         for (byte b = 0; b < paramInt6; b++) {
/*  764 */           byte b1 = arrayOfByte1[i2];
/*  765 */           byte b2 = 0;
/*  766 */           if (i2 < i7) {
/*  767 */             b2 = arrayOfByte1[i2 + 1];
/*      */           }
/*      */ 
/*      */           
/*  771 */           byte b3 = arrayOfByte2[i3];
/*  772 */           int i8 = b3 & (i4 ^ 0xFFFFFFFF);
/*  773 */           i8 |= (b1 << i5 | (b2 & 0xFF) >> i6) & i4;
/*      */           
/*  775 */           arrayOfByte2[i3] = (byte)i8;
/*      */           
/*  777 */           i2 += i;
/*  778 */           i3 += j;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  783 */     markDirty();
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
/*      */   public void setRect(int paramInt1, int paramInt2, Raster paramRaster) {
/*  804 */     if (!(paramRaster instanceof BytePackedRaster) || ((BytePackedRaster)paramRaster).pixelBitStride != this.pixelBitStride) {
/*      */       
/*  806 */       super.setRect(paramInt1, paramInt2, paramRaster);
/*      */       
/*      */       return;
/*      */     } 
/*  810 */     int i = paramRaster.getWidth();
/*  811 */     int j = paramRaster.getHeight();
/*  812 */     int k = paramRaster.getMinX();
/*  813 */     int m = paramRaster.getMinY();
/*  814 */     int n = paramInt1 + k;
/*  815 */     int i1 = paramInt2 + m;
/*      */ 
/*      */     
/*  818 */     if (n < this.minX) {
/*  819 */       int i2 = this.minX - n;
/*  820 */       i -= i2;
/*  821 */       k += i2;
/*  822 */       n = this.minX;
/*      */     } 
/*  824 */     if (i1 < this.minY) {
/*  825 */       int i2 = this.minY - i1;
/*  826 */       j -= i2;
/*  827 */       m += i2;
/*  828 */       i1 = this.minY;
/*      */     } 
/*  830 */     if (n + i > this.maxX) {
/*  831 */       i = this.maxX - n;
/*      */     }
/*  833 */     if (i1 + j > this.maxY) {
/*  834 */       j = this.maxY - i1;
/*      */     }
/*      */     
/*  837 */     setDataElements(n, i1, k, m, i, j, (BytePackedRaster)paramRaster);
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
/*      */   public void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/*  866 */     putByteData(paramInt1, paramInt2, paramInt3, paramInt4, (byte[])paramObject);
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
/*      */   public void putByteData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfbyte) {
/*  889 */     putByteData(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfbyte);
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
/*  910 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/*  912 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  915 */     if (paramInt3 == 0 || paramInt4 == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  919 */     int i = this.pixelBitStride;
/*  920 */     int j = this.dataBitOffset + (paramInt1 - this.minX) * i;
/*  921 */     int k = (paramInt2 - this.minY) * this.scanlineStride;
/*  922 */     byte b1 = 0;
/*  923 */     byte[] arrayOfByte = this.data;
/*  924 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/*  925 */       int m = j;
/*      */ 
/*      */ 
/*      */       
/*  929 */       byte b = 0;
/*  930 */       while (b < paramInt3 && (m & 0x7) != 0) {
/*  931 */         int i2 = this.shiftOffset - (m & 0x7);
/*  932 */         byte b3 = arrayOfByte[k + (m >> 3)];
/*  933 */         int i1 = b3 & (this.bitMask << i2 ^ 0xFFFFFFFF);
/*  934 */         i1 |= (paramArrayOfbyte[b1++] & this.bitMask) << i2;
/*  935 */         arrayOfByte[k + (m >> 3)] = (byte)i1;
/*      */         
/*  937 */         m += i;
/*  938 */         b++;
/*      */       } 
/*      */ 
/*      */       
/*  942 */       int n = k + (m >> 3);
/*  943 */       switch (i) {
/*      */         case 1:
/*  945 */           for (; b < paramInt3 - 7; b += 8) {
/*  946 */             int i1 = (paramArrayOfbyte[b1++] & 0x1) << 7;
/*  947 */             i1 |= (paramArrayOfbyte[b1++] & 0x1) << 6;
/*  948 */             i1 |= (paramArrayOfbyte[b1++] & 0x1) << 5;
/*  949 */             i1 |= (paramArrayOfbyte[b1++] & 0x1) << 4;
/*  950 */             i1 |= (paramArrayOfbyte[b1++] & 0x1) << 3;
/*  951 */             i1 |= (paramArrayOfbyte[b1++] & 0x1) << 2;
/*  952 */             i1 |= (paramArrayOfbyte[b1++] & 0x1) << 1;
/*  953 */             i1 |= paramArrayOfbyte[b1++] & 0x1;
/*      */             
/*  955 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/*  957 */             m += 8;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/*  962 */           for (; b < paramInt3 - 7; b += 8) {
/*  963 */             int i1 = (paramArrayOfbyte[b1++] & 0x3) << 6;
/*  964 */             i1 |= (paramArrayOfbyte[b1++] & 0x3) << 4;
/*  965 */             i1 |= (paramArrayOfbyte[b1++] & 0x3) << 2;
/*  966 */             i1 |= paramArrayOfbyte[b1++] & 0x3;
/*  967 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/*  969 */             i1 = (paramArrayOfbyte[b1++] & 0x3) << 6;
/*  970 */             i1 |= (paramArrayOfbyte[b1++] & 0x3) << 4;
/*  971 */             i1 |= (paramArrayOfbyte[b1++] & 0x3) << 2;
/*  972 */             i1 |= paramArrayOfbyte[b1++] & 0x3;
/*  973 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/*  975 */             m += 16;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 4:
/*  980 */           for (; b < paramInt3 - 7; b += 8) {
/*  981 */             int i1 = (paramArrayOfbyte[b1++] & 0xF) << 4;
/*  982 */             i1 |= paramArrayOfbyte[b1++] & 0xF;
/*  983 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/*  985 */             i1 = (paramArrayOfbyte[b1++] & 0xF) << 4;
/*  986 */             i1 |= paramArrayOfbyte[b1++] & 0xF;
/*  987 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/*  989 */             i1 = (paramArrayOfbyte[b1++] & 0xF) << 4;
/*  990 */             i1 |= paramArrayOfbyte[b1++] & 0xF;
/*  991 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/*  993 */             i1 = (paramArrayOfbyte[b1++] & 0xF) << 4;
/*  994 */             i1 |= paramArrayOfbyte[b1++] & 0xF;
/*  995 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/*  997 */             m += 32;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1003 */       for (; b < paramInt3; b++) {
/* 1004 */         int i2 = this.shiftOffset - (m & 0x7);
/*      */         
/* 1006 */         byte b3 = arrayOfByte[k + (m >> 3)];
/* 1007 */         int i1 = b3 & (this.bitMask << i2 ^ 0xFFFFFFFF);
/* 1008 */         i1 |= (paramArrayOfbyte[b1++] & this.bitMask) << i2;
/* 1009 */         arrayOfByte[k + (m >> 3)] = (byte)i1;
/*      */         
/* 1011 */         m += i;
/*      */       } 
/*      */       
/* 1014 */       k += this.scanlineStride;
/*      */     } 
/*      */     
/* 1017 */     markDirty();
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
/*      */   public int[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 1032 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/* 1034 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1037 */     if (paramArrayOfint == null) {
/* 1038 */       paramArrayOfint = new int[paramInt3 * paramInt4];
/*      */     }
/* 1040 */     int i = this.pixelBitStride;
/* 1041 */     int j = this.dataBitOffset + (paramInt1 - this.minX) * i;
/* 1042 */     int k = (paramInt2 - this.minY) * this.scanlineStride;
/* 1043 */     byte b1 = 0;
/* 1044 */     byte[] arrayOfByte = this.data;
/*      */     
/* 1046 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 1047 */       int m = j;
/*      */ 
/*      */ 
/*      */       
/* 1051 */       byte b = 0;
/* 1052 */       while (b < paramInt3 && (m & 0x7) != 0) {
/* 1053 */         int i1 = this.shiftOffset - (m & 0x7);
/* 1054 */         paramArrayOfint[b1++] = this.bitMask & arrayOfByte[k + (m >> 3)] >> i1;
/*      */         
/* 1056 */         m += i;
/* 1057 */         b++;
/*      */       } 
/*      */ 
/*      */       
/* 1061 */       int n = k + (m >> 3);
/* 1062 */       switch (i) {
/*      */         case 1:
/* 1064 */           for (; b < paramInt3 - 7; b += 8) {
/* 1065 */             byte b3 = arrayOfByte[n++];
/* 1066 */             paramArrayOfint[b1++] = b3 >> 7 & 0x1;
/* 1067 */             paramArrayOfint[b1++] = b3 >> 6 & 0x1;
/* 1068 */             paramArrayOfint[b1++] = b3 >> 5 & 0x1;
/* 1069 */             paramArrayOfint[b1++] = b3 >> 4 & 0x1;
/* 1070 */             paramArrayOfint[b1++] = b3 >> 3 & 0x1;
/* 1071 */             paramArrayOfint[b1++] = b3 >> 2 & 0x1;
/* 1072 */             paramArrayOfint[b1++] = b3 >> 1 & 0x1;
/* 1073 */             paramArrayOfint[b1++] = b3 & 0x1;
/* 1074 */             m += 8;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/* 1079 */           for (; b < paramInt3 - 7; b += 8) {
/* 1080 */             byte b3 = arrayOfByte[n++];
/* 1081 */             paramArrayOfint[b1++] = b3 >> 6 & 0x3;
/* 1082 */             paramArrayOfint[b1++] = b3 >> 4 & 0x3;
/* 1083 */             paramArrayOfint[b1++] = b3 >> 2 & 0x3;
/* 1084 */             paramArrayOfint[b1++] = b3 & 0x3;
/*      */             
/* 1086 */             b3 = arrayOfByte[n++];
/* 1087 */             paramArrayOfint[b1++] = b3 >> 6 & 0x3;
/* 1088 */             paramArrayOfint[b1++] = b3 >> 4 & 0x3;
/* 1089 */             paramArrayOfint[b1++] = b3 >> 2 & 0x3;
/* 1090 */             paramArrayOfint[b1++] = b3 & 0x3;
/*      */             
/* 1092 */             m += 16;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 4:
/* 1097 */           for (; b < paramInt3 - 7; b += 8) {
/* 1098 */             byte b3 = arrayOfByte[n++];
/* 1099 */             paramArrayOfint[b1++] = b3 >> 4 & 0xF;
/* 1100 */             paramArrayOfint[b1++] = b3 & 0xF;
/*      */             
/* 1102 */             b3 = arrayOfByte[n++];
/* 1103 */             paramArrayOfint[b1++] = b3 >> 4 & 0xF;
/* 1104 */             paramArrayOfint[b1++] = b3 & 0xF;
/*      */             
/* 1106 */             b3 = arrayOfByte[n++];
/* 1107 */             paramArrayOfint[b1++] = b3 >> 4 & 0xF;
/* 1108 */             paramArrayOfint[b1++] = b3 & 0xF;
/*      */             
/* 1110 */             b3 = arrayOfByte[n++];
/* 1111 */             paramArrayOfint[b1++] = b3 >> 4 & 0xF;
/* 1112 */             paramArrayOfint[b1++] = b3 & 0xF;
/*      */             
/* 1114 */             m += 32;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1120 */       for (; b < paramInt3; b++) {
/* 1121 */         int i1 = this.shiftOffset - (m & 0x7);
/* 1122 */         paramArrayOfint[b1++] = this.bitMask & arrayOfByte[k + (m >> 3)] >> i1;
/*      */         
/* 1124 */         m += i;
/*      */       } 
/*      */       
/* 1127 */       k += this.scanlineStride;
/*      */     } 
/*      */     
/* 1130 */     return paramArrayOfint;
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
/*      */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 1145 */     if (paramInt1 < this.minX || paramInt2 < this.minY || paramInt1 + paramInt3 > this.maxX || paramInt2 + paramInt4 > this.maxY)
/*      */     {
/* 1147 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1150 */     int i = this.pixelBitStride;
/* 1151 */     int j = this.dataBitOffset + (paramInt1 - this.minX) * i;
/* 1152 */     int k = (paramInt2 - this.minY) * this.scanlineStride;
/* 1153 */     byte b1 = 0;
/* 1154 */     byte[] arrayOfByte = this.data;
/* 1155 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 1156 */       int m = j;
/*      */ 
/*      */ 
/*      */       
/* 1160 */       byte b = 0;
/* 1161 */       while (b < paramInt3 && (m & 0x7) != 0) {
/* 1162 */         int i2 = this.shiftOffset - (m & 0x7);
/* 1163 */         byte b3 = arrayOfByte[k + (m >> 3)];
/* 1164 */         int i1 = b3 & (this.bitMask << i2 ^ 0xFFFFFFFF);
/* 1165 */         i1 |= (paramArrayOfint[b1++] & this.bitMask) << i2;
/* 1166 */         arrayOfByte[k + (m >> 3)] = (byte)i1;
/*      */         
/* 1168 */         m += i;
/* 1169 */         b++;
/*      */       } 
/*      */ 
/*      */       
/* 1173 */       int n = k + (m >> 3);
/* 1174 */       switch (i) {
/*      */         case 1:
/* 1176 */           for (; b < paramInt3 - 7; b += 8) {
/* 1177 */             int i1 = (paramArrayOfint[b1++] & 0x1) << 7;
/* 1178 */             i1 |= (paramArrayOfint[b1++] & 0x1) << 6;
/* 1179 */             i1 |= (paramArrayOfint[b1++] & 0x1) << 5;
/* 1180 */             i1 |= (paramArrayOfint[b1++] & 0x1) << 4;
/* 1181 */             i1 |= (paramArrayOfint[b1++] & 0x1) << 3;
/* 1182 */             i1 |= (paramArrayOfint[b1++] & 0x1) << 2;
/* 1183 */             i1 |= (paramArrayOfint[b1++] & 0x1) << 1;
/* 1184 */             i1 |= paramArrayOfint[b1++] & 0x1;
/* 1185 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/* 1187 */             m += 8;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/* 1192 */           for (; b < paramInt3 - 7; b += 8) {
/* 1193 */             int i1 = (paramArrayOfint[b1++] & 0x3) << 6;
/* 1194 */             i1 |= (paramArrayOfint[b1++] & 0x3) << 4;
/* 1195 */             i1 |= (paramArrayOfint[b1++] & 0x3) << 2;
/* 1196 */             i1 |= paramArrayOfint[b1++] & 0x3;
/* 1197 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/* 1199 */             i1 = (paramArrayOfint[b1++] & 0x3) << 6;
/* 1200 */             i1 |= (paramArrayOfint[b1++] & 0x3) << 4;
/* 1201 */             i1 |= (paramArrayOfint[b1++] & 0x3) << 2;
/* 1202 */             i1 |= paramArrayOfint[b1++] & 0x3;
/* 1203 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/* 1205 */             m += 16;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 4:
/* 1210 */           for (; b < paramInt3 - 7; b += 8) {
/* 1211 */             int i1 = (paramArrayOfint[b1++] & 0xF) << 4;
/* 1212 */             i1 |= paramArrayOfint[b1++] & 0xF;
/* 1213 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/* 1215 */             i1 = (paramArrayOfint[b1++] & 0xF) << 4;
/* 1216 */             i1 |= paramArrayOfint[b1++] & 0xF;
/* 1217 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/* 1219 */             i1 = (paramArrayOfint[b1++] & 0xF) << 4;
/* 1220 */             i1 |= paramArrayOfint[b1++] & 0xF;
/* 1221 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/* 1223 */             i1 = (paramArrayOfint[b1++] & 0xF) << 4;
/* 1224 */             i1 |= paramArrayOfint[b1++] & 0xF;
/* 1225 */             arrayOfByte[n++] = (byte)i1;
/*      */             
/* 1227 */             m += 32;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1233 */       for (; b < paramInt3; b++) {
/* 1234 */         int i2 = this.shiftOffset - (m & 0x7);
/*      */         
/* 1236 */         byte b3 = arrayOfByte[k + (m >> 3)];
/* 1237 */         int i1 = b3 & (this.bitMask << i2 ^ 0xFFFFFFFF);
/* 1238 */         i1 |= (paramArrayOfint[b1++] & this.bitMask) << i2;
/* 1239 */         arrayOfByte[k + (m >> 3)] = (byte)i1;
/*      */         
/* 1241 */         m += i;
/*      */       } 
/*      */       
/* 1244 */       k += this.scanlineStride;
/*      */     } 
/*      */     
/* 1247 */     markDirty();
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
/*      */   public Raster createChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/* 1270 */     return createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
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
/*      */   public WritableRaster createWritableChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/*      */     SampleModel sampleModel;
/* 1299 */     if (paramInt1 < this.minX) {
/* 1300 */       throw new RasterFormatException("x lies outside the raster");
/*      */     }
/* 1302 */     if (paramInt2 < this.minY) {
/* 1303 */       throw new RasterFormatException("y lies outside the raster");
/*      */     }
/* 1305 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.minX + this.width) {
/* 1306 */       throw new RasterFormatException("(x + width) is outside of Raster");
/*      */     }
/* 1308 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.minY + this.height) {
/* 1309 */       throw new RasterFormatException("(y + height) is outside of Raster");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1314 */     if (paramArrayOfint != null) {
/* 1315 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*      */     } else {
/*      */       
/* 1318 */       sampleModel = this.sampleModel;
/*      */     } 
/*      */     
/* 1321 */     int i = paramInt5 - paramInt1;
/* 1322 */     int j = paramInt6 - paramInt2;
/*      */     
/* 1324 */     return new BytePackedRaster(sampleModel, this.dataBuffer, new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/* 1337 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 1338 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*      */     }
/*      */ 
/*      */     
/* 1342 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*      */     
/* 1344 */     return new BytePackedRaster(sampleModel, new Point(0, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster createCompatibleWritableRaster() {
/* 1352 */     return createCompatibleWritableRaster(this.width, this.height);
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
/*      */   private void verify(boolean paramBoolean) {
/* 1367 */     if (this.dataBitOffset < 0) {
/* 1368 */       throw new RasterFormatException("Data offsets must be >= 0");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1374 */     if (this.width <= 0 || this.height <= 0 || this.height > Integer.MAX_VALUE / this.width)
/*      */     {
/*      */       
/* 1377 */       throw new RasterFormatException("Invalid raster dimension");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1385 */     if (this.width - 1 > Integer.MAX_VALUE / this.pixelBitStride) {
/* 1386 */       throw new RasterFormatException("Invalid raster dimension");
/*      */     }
/*      */     
/* 1389 */     if (this.minX - this.sampleModelTranslateX < 0L || this.minY - this.sampleModelTranslateY < 0L)
/*      */     {
/*      */       
/* 1392 */       throw new RasterFormatException("Incorrect origin/translate: (" + this.minX + ", " + this.minY + ") / (" + this.sampleModelTranslateX + ", " + this.sampleModelTranslateY + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1397 */     if (this.scanlineStride < 0 || this.scanlineStride > Integer.MAX_VALUE / this.height)
/*      */     {
/*      */       
/* 1400 */       throw new RasterFormatException("Invalid scanline stride");
/*      */     }
/*      */     
/* 1403 */     if (this.height > 1 || this.minY - this.sampleModelTranslateY > 0)
/*      */     {
/* 1405 */       if (this.scanlineStride > this.data.length) {
/* 1406 */         throw new RasterFormatException("Incorrect scanline stride: " + this.scanlineStride);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1411 */     long l = this.dataBitOffset + (this.height - 1) * this.scanlineStride * 8L + (this.width - 1) * this.pixelBitStride + this.pixelBitStride - 1L;
/*      */ 
/*      */ 
/*      */     
/* 1415 */     if (l < 0L || l / 8L >= this.data.length) {
/* 1416 */       throw new RasterFormatException("raster dimensions overflow array bounds");
/*      */     }
/*      */     
/* 1419 */     if (paramBoolean && 
/* 1420 */       this.height > 1) {
/* 1421 */       l = (this.width * this.pixelBitStride - 1);
/* 1422 */       if (l / 8L >= this.scanlineStride) {
/* 1423 */         throw new RasterFormatException("data for adjacent scanlines overlaps");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1431 */     return new String("BytePackedRaster: width = " + this.width + " height = " + this.height + " #channels " + this.numBands + " xOff = " + this.sampleModelTranslateX + " yOff = " + this.sampleModelTranslateY);
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/BytePackedRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */