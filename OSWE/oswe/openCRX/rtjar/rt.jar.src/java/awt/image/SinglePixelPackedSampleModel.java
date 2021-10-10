/*     */ package java.awt.image;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SinglePixelPackedSampleModel
/*     */   extends SampleModel
/*     */ {
/*     */   private int[] bitMasks;
/*     */   private int[] bitOffsets;
/*     */   private int[] bitSizes;
/*     */   private int maxBitSize;
/*     */   private int scanlineStride;
/*     */   
/*     */   static {
/*  87 */     ColorModel.loadLibraries();
/*  88 */     initIDs();
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
/*     */   public SinglePixelPackedSampleModel(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint) {
/* 110 */     this(paramInt1, paramInt2, paramInt3, paramInt2, paramArrayOfint);
/* 111 */     if (paramInt1 != 0 && paramInt1 != 1 && paramInt1 != 3)
/*     */     {
/*     */       
/* 114 */       throw new IllegalArgumentException("Unsupported data type " + paramInt1);
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
/*     */   public SinglePixelPackedSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 144 */     super(paramInt1, paramInt2, paramInt3, paramArrayOfint.length);
/* 145 */     if (paramInt1 != 0 && paramInt1 != 1 && paramInt1 != 3)
/*     */     {
/*     */       
/* 148 */       throw new IllegalArgumentException("Unsupported data type " + paramInt1);
/*     */     }
/*     */     
/* 151 */     this.dataType = paramInt1;
/* 152 */     this.bitMasks = (int[])paramArrayOfint.clone();
/* 153 */     this.scanlineStride = paramInt4;
/*     */     
/* 155 */     this.bitOffsets = new int[this.numBands];
/* 156 */     this.bitSizes = new int[this.numBands];
/*     */     
/* 158 */     int i = (int)((1L << DataBuffer.getDataTypeSize(paramInt1)) - 1L);
/*     */     
/* 160 */     this.maxBitSize = 0;
/* 161 */     for (byte b = 0; b < this.numBands; b++) {
/* 162 */       byte b1 = 0, b2 = 0;
/* 163 */       this.bitMasks[b] = this.bitMasks[b] & i;
/* 164 */       int j = this.bitMasks[b];
/* 165 */       if (j != 0) {
/* 166 */         while ((j & 0x1) == 0) {
/* 167 */           j >>>= 1;
/* 168 */           b1++;
/*     */         } 
/* 170 */         while ((j & 0x1) == 1) {
/* 171 */           j >>>= 1;
/* 172 */           b2++;
/*     */         } 
/* 174 */         if (j != 0) {
/* 175 */           throw new IllegalArgumentException("Mask " + paramArrayOfint[b] + " must be contiguous");
/*     */         }
/*     */       } 
/*     */       
/* 179 */       this.bitOffsets[b] = b1;
/* 180 */       this.bitSizes[b] = b2;
/* 181 */       if (b2 > this.maxBitSize) {
/* 182 */         this.maxBitSize = b2;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumDataElements() {
/* 193 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getBufferSize() {
/* 202 */     return (this.scanlineStride * (this.height - 1) + this.width);
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
/*     */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2) {
/* 219 */     return new SinglePixelPackedSampleModel(this.dataType, paramInt1, paramInt2, this.bitMasks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataBuffer createDataBuffer() {
/*     */     DataBufferUShort dataBufferUShort;
/*     */     DataBufferInt dataBufferInt;
/* 231 */     DataBufferByte dataBufferByte = null;
/*     */     
/* 233 */     int i = (int)getBufferSize();
/* 234 */     switch (this.dataType) {
/*     */       case 0:
/* 236 */         dataBufferByte = new DataBufferByte(i);
/*     */         break;
/*     */       case 1:
/* 239 */         dataBufferUShort = new DataBufferUShort(i);
/*     */         break;
/*     */       case 3:
/* 242 */         dataBufferInt = new DataBufferInt(i);
/*     */         break;
/*     */     } 
/* 245 */     return dataBufferInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSampleSize() {
/* 250 */     return (int[])this.bitSizes.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSampleSize(int paramInt) {
/* 255 */     return this.bitSizes[paramInt];
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
/*     */   public int getOffset(int paramInt1, int paramInt2) {
/* 270 */     return paramInt2 * this.scanlineStride + paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getBitOffsets() {
/* 279 */     return (int[])this.bitOffsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getBitMasks() {
/* 286 */     return (int[])this.bitMasks.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScanlineStride() {
/* 294 */     return this.scanlineStride;
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
/*     */   public SampleModel createSubsetSampleModel(int[] paramArrayOfint) {
/* 310 */     if (paramArrayOfint.length > this.numBands) {
/* 311 */       throw new RasterFormatException("There are only " + this.numBands + " bands");
/*     */     }
/*     */     
/* 314 */     int[] arrayOfInt = new int[paramArrayOfint.length];
/* 315 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 316 */       arrayOfInt[b] = this.bitMasks[paramArrayOfint[b]];
/*     */     }
/* 318 */     return new SinglePixelPackedSampleModel(this.dataType, this.width, this.height, this.scanlineStride, arrayOfInt);
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
/*     */   public Object getDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer) {
/*     */     byte[] arrayOfByte;
/*     */     short[] arrayOfShort;
/*     */     int[] arrayOfInt;
/* 363 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 364 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 368 */     int i = getTransferType();
/*     */     
/* 370 */     switch (i) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 376 */         if (paramObject == null) {
/* 377 */           arrayOfByte = new byte[1];
/*     */         } else {
/* 379 */           arrayOfByte = (byte[])paramObject;
/*     */         } 
/* 381 */         arrayOfByte[0] = (byte)paramDataBuffer.getElem(paramInt2 * this.scanlineStride + paramInt1);
/*     */         
/* 383 */         paramObject = arrayOfByte;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 390 */         if (paramObject == null) {
/* 391 */           arrayOfShort = new short[1];
/*     */         } else {
/* 393 */           arrayOfShort = (short[])paramObject;
/*     */         } 
/* 395 */         arrayOfShort[0] = (short)paramDataBuffer.getElem(paramInt2 * this.scanlineStride + paramInt1);
/*     */         
/* 397 */         paramObject = arrayOfShort;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 404 */         if (paramObject == null) {
/* 405 */           arrayOfInt = new int[1];
/*     */         } else {
/* 407 */           arrayOfInt = (int[])paramObject;
/*     */         } 
/* 409 */         arrayOfInt[0] = paramDataBuffer.getElem(paramInt2 * this.scanlineStride + paramInt1);
/*     */         
/* 411 */         paramObject = arrayOfInt;
/*     */         break;
/*     */     } 
/*     */     
/* 415 */     return paramObject;
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
/*     */   public int[] getPixel(int paramInt1, int paramInt2, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*     */     int[] arrayOfInt;
/* 430 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 431 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 435 */     if (paramArrayOfint == null) {
/* 436 */       arrayOfInt = new int[this.numBands];
/*     */     } else {
/* 438 */       arrayOfInt = paramArrayOfint;
/*     */     } 
/*     */     
/* 441 */     int i = paramDataBuffer.getElem(paramInt2 * this.scanlineStride + paramInt1);
/* 442 */     for (byte b = 0; b < this.numBands; b++) {
/* 443 */       arrayOfInt[b] = (i & this.bitMasks[b]) >>> this.bitOffsets[b];
/*     */     }
/* 445 */     return arrayOfInt;
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
/*     */   public int[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 464 */     int arrayOfInt[], i = paramInt1 + paramInt3;
/* 465 */     int j = paramInt2 + paramInt4;
/*     */     
/* 467 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*     */     {
/*     */       
/* 470 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 474 */     if (paramArrayOfint != null) {
/* 475 */       arrayOfInt = paramArrayOfint;
/*     */     } else {
/* 477 */       arrayOfInt = new int[paramInt3 * paramInt4 * this.numBands];
/*     */     } 
/* 479 */     int k = paramInt2 * this.scanlineStride + paramInt1;
/* 480 */     byte b1 = 0;
/*     */     
/* 482 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 483 */       for (byte b = 0; b < paramInt3; b++) {
/* 484 */         int m = paramDataBuffer.getElem(k + b);
/* 485 */         for (byte b3 = 0; b3 < this.numBands; b3++) {
/* 486 */           arrayOfInt[b1++] = (m & this.bitMasks[b3]) >>> this.bitOffsets[b3];
/*     */         }
/*     */       } 
/*     */       
/* 490 */       k += this.scanlineStride;
/*     */     } 
/* 492 */     return arrayOfInt;
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
/*     */   public int getSample(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/* 510 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 511 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 514 */     int i = paramDataBuffer.getElem(paramInt2 * this.scanlineStride + paramInt1);
/* 515 */     return (i & this.bitMasks[paramInt3]) >>> this.bitOffsets[paramInt3];
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
/*     */   public int[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*     */     int[] arrayOfInt;
/* 537 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt3 > this.width || paramInt2 + paramInt4 > this.height) {
/* 538 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 542 */     if (paramArrayOfint != null) {
/* 543 */       arrayOfInt = paramArrayOfint;
/*     */     } else {
/* 545 */       arrayOfInt = new int[paramInt3 * paramInt4];
/*     */     } 
/* 547 */     int i = paramInt2 * this.scanlineStride + paramInt1;
/* 548 */     byte b1 = 0;
/*     */     
/* 550 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 551 */       for (byte b = 0; b < paramInt3; b++) {
/* 552 */         int j = paramDataBuffer.getElem(i + b);
/* 553 */         arrayOfInt[b1++] = (j & this.bitMasks[paramInt5]) >>> this.bitOffsets[paramInt5];
/*     */       } 
/*     */       
/* 556 */       i += this.scanlineStride;
/*     */     } 
/* 558 */     return arrayOfInt;
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer) {
/*     */     byte[] arrayOfByte;
/*     */     short[] arrayOfShort;
/*     */     int[] arrayOfInt;
/* 597 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 598 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 602 */     int i = getTransferType();
/*     */     
/* 604 */     switch (i) {
/*     */ 
/*     */       
/*     */       case 0:
/* 608 */         arrayOfByte = (byte[])paramObject;
/* 609 */         paramDataBuffer.setElem(paramInt2 * this.scanlineStride + paramInt1, arrayOfByte[0] & 0xFF);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 614 */         arrayOfShort = (short[])paramObject;
/* 615 */         paramDataBuffer.setElem(paramInt2 * this.scanlineStride + paramInt1, arrayOfShort[0] & 0xFFFF);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 620 */         arrayOfInt = (int[])paramObject;
/* 621 */         paramDataBuffer.setElem(paramInt2 * this.scanlineStride + paramInt1, arrayOfInt[0]);
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
/*     */   public void setPixel(int paramInt1, int paramInt2, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 639 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 640 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 643 */     int i = paramInt2 * this.scanlineStride + paramInt1;
/* 644 */     int j = paramDataBuffer.getElem(i);
/* 645 */     for (byte b = 0; b < this.numBands; b++) {
/* 646 */       j &= this.bitMasks[b] ^ 0xFFFFFFFF;
/* 647 */       j |= paramArrayOfint[b] << this.bitOffsets[b] & this.bitMasks[b];
/*     */     } 
/* 649 */     paramDataBuffer.setElem(i, j);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 667 */     int i = paramInt1 + paramInt3;
/* 668 */     int j = paramInt2 + paramInt4;
/*     */     
/* 670 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*     */     {
/*     */       
/* 673 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 677 */     int k = paramInt2 * this.scanlineStride + paramInt1;
/* 678 */     byte b1 = 0;
/*     */     
/* 680 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 681 */       for (byte b = 0; b < paramInt3; b++) {
/* 682 */         int m = paramDataBuffer.getElem(k + b);
/* 683 */         for (byte b3 = 0; b3 < this.numBands; b3++) {
/* 684 */           m &= this.bitMasks[b3] ^ 0xFFFFFFFF;
/* 685 */           int n = paramArrayOfint[b1++];
/* 686 */           m |= n << this.bitOffsets[b3] & this.bitMasks[b3];
/*     */         } 
/*     */         
/* 689 */         paramDataBuffer.setElem(k + b, m);
/*     */       } 
/* 691 */       k += this.scanlineStride;
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
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, int paramInt4, DataBuffer paramDataBuffer) {
/* 710 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 711 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 714 */     int i = paramDataBuffer.getElem(paramInt2 * this.scanlineStride + paramInt1);
/* 715 */     i &= this.bitMasks[paramInt3] ^ 0xFFFFFFFF;
/* 716 */     i |= paramInt4 << this.bitOffsets[paramInt3] & this.bitMasks[paramInt3];
/* 717 */     paramDataBuffer.setElem(paramInt2 * this.scanlineStride + paramInt1, i);
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
/*     */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 737 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt3 > this.width || paramInt2 + paramInt4 > this.height) {
/* 738 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 741 */     int i = paramInt2 * this.scanlineStride + paramInt1;
/* 742 */     byte b1 = 0;
/*     */     
/* 744 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 745 */       for (byte b = 0; b < paramInt3; b++) {
/* 746 */         int j = paramDataBuffer.getElem(i + b);
/* 747 */         j &= this.bitMasks[paramInt5] ^ 0xFFFFFFFF;
/* 748 */         int k = paramArrayOfint[b1++];
/* 749 */         j |= k << this.bitOffsets[paramInt5] & this.bitMasks[paramInt5];
/* 750 */         paramDataBuffer.setElem(i + b, j);
/*     */       } 
/* 752 */       i += this.scanlineStride;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 757 */     if (paramObject == null || !(paramObject instanceof SinglePixelPackedSampleModel)) {
/* 758 */       return false;
/*     */     }
/*     */     
/* 761 */     SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramObject;
/* 762 */     return (this.width == singlePixelPackedSampleModel.width && this.height == singlePixelPackedSampleModel.height && this.numBands == singlePixelPackedSampleModel.numBands && this.dataType == singlePixelPackedSampleModel.dataType && 
/*     */ 
/*     */ 
/*     */       
/* 766 */       Arrays.equals(this.bitMasks, singlePixelPackedSampleModel.bitMasks) && 
/* 767 */       Arrays.equals(this.bitOffsets, singlePixelPackedSampleModel.bitOffsets) && 
/* 768 */       Arrays.equals(this.bitSizes, singlePixelPackedSampleModel.bitSizes) && this.maxBitSize == singlePixelPackedSampleModel.maxBitSize && this.scanlineStride == singlePixelPackedSampleModel.scanlineStride);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 775 */     int i = 0;
/* 776 */     i = this.width;
/* 777 */     i <<= 8;
/* 778 */     i ^= this.height;
/* 779 */     i <<= 8;
/* 780 */     i ^= this.numBands;
/* 781 */     i <<= 8;
/* 782 */     i ^= this.dataType;
/* 783 */     i <<= 8; byte b;
/* 784 */     for (b = 0; b < this.bitMasks.length; b++) {
/* 785 */       i ^= this.bitMasks[b];
/* 786 */       i <<= 8;
/*     */     } 
/* 788 */     for (b = 0; b < this.bitOffsets.length; b++) {
/* 789 */       i ^= this.bitOffsets[b];
/* 790 */       i <<= 8;
/*     */     } 
/* 792 */     for (b = 0; b < this.bitSizes.length; b++) {
/* 793 */       i ^= this.bitSizes[b];
/* 794 */       i <<= 8;
/*     */     } 
/* 796 */     i ^= this.maxBitSize;
/* 797 */     i <<= 8;
/* 798 */     i ^= this.scanlineStride;
/* 799 */     return i;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/SinglePixelPackedSampleModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */