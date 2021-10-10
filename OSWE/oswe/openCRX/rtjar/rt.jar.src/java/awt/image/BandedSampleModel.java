/*     */ package java.awt.image;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BandedSampleModel
/*     */   extends ComponentSampleModel
/*     */ {
/*     */   public BandedSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  85 */     super(paramInt1, paramInt2, paramInt3, 1, paramInt2, 
/*  86 */         createIndicesArray(paramInt4), 
/*  87 */         createOffsetArray(paramInt4));
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
/*     */   public BandedSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 112 */     super(paramInt1, paramInt2, paramInt3, 1, paramInt4, paramArrayOfint1, paramArrayOfint2);
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
/*     */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2) {
/*     */     int[] arrayOfInt;
/* 136 */     if (this.numBanks == 1) {
/* 137 */       arrayOfInt = orderBands(this.bandOffsets, paramInt1 * paramInt2);
/*     */     } else {
/*     */       
/* 140 */       arrayOfInt = new int[this.bandOffsets.length];
/*     */     } 
/*     */     
/* 143 */     return new BandedSampleModel(this.dataType, paramInt1, paramInt2, paramInt1, this.bankIndices, arrayOfInt);
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
/*     */   public SampleModel createSubsetSampleModel(int[] paramArrayOfint) {
/* 161 */     if (paramArrayOfint.length > this.bankIndices.length) {
/* 162 */       throw new RasterFormatException("There are only " + this.bankIndices.length + " bands");
/*     */     }
/*     */     
/* 165 */     int[] arrayOfInt1 = new int[paramArrayOfint.length];
/* 166 */     int[] arrayOfInt2 = new int[paramArrayOfint.length];
/*     */     
/* 168 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 169 */       arrayOfInt1[b] = this.bankIndices[paramArrayOfint[b]];
/* 170 */       arrayOfInt2[b] = this.bandOffsets[paramArrayOfint[b]];
/*     */     } 
/*     */     
/* 173 */     return new BandedSampleModel(this.dataType, this.width, this.height, this.scanlineStride, arrayOfInt1, arrayOfInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataBuffer createDataBuffer() {
/*     */     DataBufferUShort dataBufferUShort;
/*     */     DataBufferShort dataBufferShort;
/*     */     DataBufferInt dataBufferInt;
/*     */     DataBufferFloat dataBufferFloat;
/* 186 */     DataBufferByte dataBufferByte = null;
/*     */     
/* 188 */     int i = this.scanlineStride * this.height;
/* 189 */     switch (this.dataType) {
/*     */       case 0:
/* 191 */         dataBufferByte = new DataBufferByte(i, this.numBanks);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 213 */         return dataBufferByte;
/*     */       case 1:
/*     */         return new DataBufferUShort(i, this.numBanks);
/*     */       case 2:
/*     */         return new DataBufferShort(i, this.numBanks);
/*     */       case 3:
/*     */         return new DataBufferInt(i, this.numBanks);
/*     */       case 4:
/*     */         return new DataBufferFloat(i, this.numBanks);
/*     */       case 5:
/*     */         return new DataBufferDouble(i, this.numBanks);
/*     */     } 
/*     */     throw new IllegalArgumentException("dataType is not one of the supported types.");
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
/*     */   public Object getDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer) {
/*     */     byte[] arrayOfByte;
/*     */     byte b1;
/*     */     short[] arrayOfShort;
/*     */     byte b2;
/*     */     int[] arrayOfInt;
/*     */     byte b3;
/*     */     float[] arrayOfFloat;
/*     */     byte b4;
/*     */     double[] arrayOfDouble;
/*     */     byte b5;
/* 257 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 258 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 261 */     int i = getTransferType();
/* 262 */     int j = getNumDataElements();
/* 263 */     int k = paramInt2 * this.scanlineStride + paramInt1;
/*     */     
/* 265 */     switch (i) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 271 */         if (paramObject == null) {
/* 272 */           arrayOfByte = new byte[j];
/*     */         } else {
/* 274 */           arrayOfByte = (byte[])paramObject;
/*     */         } 
/*     */         
/* 277 */         for (b1 = 0; b1 < j; b1++) {
/* 278 */           arrayOfByte[b1] = (byte)paramDataBuffer.getElem(this.bankIndices[b1], k + this.bandOffsets[b1]);
/*     */         }
/*     */ 
/*     */         
/* 282 */         paramObject = arrayOfByte;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 290 */         if (paramObject == null) {
/* 291 */           arrayOfShort = new short[j];
/*     */         } else {
/* 293 */           arrayOfShort = (short[])paramObject;
/*     */         } 
/*     */         
/* 296 */         for (b2 = 0; b2 < j; b2++) {
/* 297 */           arrayOfShort[b2] = (short)paramDataBuffer.getElem(this.bankIndices[b2], k + this.bandOffsets[b2]);
/*     */         }
/*     */ 
/*     */         
/* 301 */         paramObject = arrayOfShort;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 308 */         if (paramObject == null) {
/* 309 */           arrayOfInt = new int[j];
/*     */         } else {
/* 311 */           arrayOfInt = (int[])paramObject;
/*     */         } 
/*     */         
/* 314 */         for (b3 = 0; b3 < j; b3++) {
/* 315 */           arrayOfInt[b3] = paramDataBuffer.getElem(this.bankIndices[b3], k + this.bandOffsets[b3]);
/*     */         }
/*     */ 
/*     */         
/* 319 */         paramObject = arrayOfInt;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 326 */         if (paramObject == null) {
/* 327 */           arrayOfFloat = new float[j];
/*     */         } else {
/* 329 */           arrayOfFloat = (float[])paramObject;
/*     */         } 
/*     */         
/* 332 */         for (b4 = 0; b4 < j; b4++) {
/* 333 */           arrayOfFloat[b4] = paramDataBuffer.getElemFloat(this.bankIndices[b4], k + this.bandOffsets[b4]);
/*     */         }
/*     */ 
/*     */         
/* 337 */         paramObject = arrayOfFloat;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 344 */         if (paramObject == null) {
/* 345 */           arrayOfDouble = new double[j];
/*     */         } else {
/* 347 */           arrayOfDouble = (double[])paramObject;
/*     */         } 
/*     */         
/* 350 */         for (b5 = 0; b5 < j; b5++) {
/* 351 */           arrayOfDouble[b5] = paramDataBuffer.getElemDouble(this.bankIndices[b5], k + this.bandOffsets[b5]);
/*     */         }
/*     */ 
/*     */         
/* 355 */         paramObject = arrayOfDouble;
/*     */         break;
/*     */     } 
/*     */     
/* 359 */     return paramObject;
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
/* 374 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 375 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     if (paramArrayOfint != null) {
/* 382 */       arrayOfInt = paramArrayOfint;
/*     */     } else {
/* 384 */       arrayOfInt = new int[this.numBands];
/*     */     } 
/*     */     
/* 387 */     int i = paramInt2 * this.scanlineStride + paramInt1;
/* 388 */     for (byte b = 0; b < this.numBands; b++) {
/* 389 */       arrayOfInt[b] = paramDataBuffer.getElem(this.bankIndices[b], i + this.bandOffsets[b]);
/*     */     }
/*     */     
/* 392 */     return arrayOfInt;
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
/* 411 */     int arrayOfInt[], i = paramInt1 + paramInt3;
/* 412 */     int j = paramInt2 + paramInt4;
/*     */     
/* 414 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*     */     {
/*     */       
/* 417 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 422 */     if (paramArrayOfint != null) {
/* 423 */       arrayOfInt = paramArrayOfint;
/*     */     } else {
/* 425 */       arrayOfInt = new int[paramInt3 * paramInt4 * this.numBands];
/*     */     } 
/*     */     
/* 428 */     for (byte b = 0; b < this.numBands; b++) {
/* 429 */       int k = paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[b];
/* 430 */       int m = b;
/* 431 */       int n = this.bankIndices[b];
/*     */       
/* 433 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 434 */         int i1 = k;
/* 435 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 436 */           arrayOfInt[m] = paramDataBuffer.getElem(n, i1++);
/* 437 */           m += this.numBands;
/*     */         } 
/* 439 */         k += this.scanlineStride;
/*     */       } 
/*     */     } 
/* 442 */     return arrayOfInt;
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
/*     */   public int getSample(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/* 459 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 460 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 464 */     return paramDataBuffer.getElem(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt3]);
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
/*     */   public float getSampleFloat(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/* 483 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 484 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 488 */     return paramDataBuffer.getElemFloat(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt3]);
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
/*     */   public double getSampleDouble(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/* 507 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 508 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 512 */     return paramDataBuffer.getElemDouble(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt3]);
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
/*     */   public int[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*     */     int[] arrayOfInt;
/* 536 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt3 > this.width || paramInt2 + paramInt4 > this.height) {
/* 537 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 541 */     if (paramArrayOfint != null) {
/* 542 */       arrayOfInt = paramArrayOfint;
/*     */     } else {
/* 544 */       arrayOfInt = new int[paramInt3 * paramInt4];
/*     */     } 
/*     */     
/* 547 */     int i = paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt5];
/* 548 */     byte b1 = 0;
/* 549 */     int j = this.bankIndices[paramInt5];
/*     */     
/* 551 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 552 */       int k = i;
/* 553 */       for (byte b = 0; b < paramInt3; b++) {
/* 554 */         arrayOfInt[b1++] = paramDataBuffer.getElem(j, k++);
/*     */       }
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
/*     */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer) {
/*     */     byte[] arrayOfByte;
/*     */     byte b1;
/*     */     short[] arrayOfShort;
/*     */     byte b2;
/*     */     int[] arrayOfInt;
/*     */     byte b3;
/*     */     float[] arrayOfFloat;
/*     */     byte b4;
/*     */     double[] arrayOfDouble;
/*     */     byte b5;
/* 597 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 598 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 601 */     int i = getTransferType();
/* 602 */     int j = getNumDataElements();
/* 603 */     int k = paramInt2 * this.scanlineStride + paramInt1;
/*     */     
/* 605 */     switch (i) {
/*     */ 
/*     */       
/*     */       case 0:
/* 609 */         arrayOfByte = (byte[])paramObject;
/*     */         
/* 611 */         for (b1 = 0; b1 < j; b1++) {
/* 612 */           paramDataBuffer.setElem(this.bankIndices[b1], k + this.bandOffsets[b1], arrayOfByte[b1] & 0xFF);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 620 */         arrayOfShort = (short[])paramObject;
/*     */         
/* 622 */         for (b2 = 0; b2 < j; b2++) {
/* 623 */           paramDataBuffer.setElem(this.bankIndices[b2], k + this.bandOffsets[b2], arrayOfShort[b2] & 0xFFFF);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 630 */         arrayOfInt = (int[])paramObject;
/*     */         
/* 632 */         for (b3 = 0; b3 < j; b3++) {
/* 633 */           paramDataBuffer.setElem(this.bankIndices[b3], k + this.bandOffsets[b3], arrayOfInt[b3]);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 640 */         arrayOfFloat = (float[])paramObject;
/*     */         
/* 642 */         for (b4 = 0; b4 < j; b4++) {
/* 643 */           paramDataBuffer.setElemFloat(this.bankIndices[b4], k + this.bandOffsets[b4], arrayOfFloat[b4]);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 650 */         arrayOfDouble = (double[])paramObject;
/*     */         
/* 652 */         for (b5 = 0; b5 < j; b5++) {
/* 653 */           paramDataBuffer.setElemDouble(this.bankIndices[b5], k + this.bandOffsets[b5], arrayOfDouble[b5]);
/*     */         }
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
/* 672 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 673 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 676 */     int i = paramInt2 * this.scanlineStride + paramInt1;
/* 677 */     for (byte b = 0; b < this.numBands; b++) {
/* 678 */       paramDataBuffer.setElem(this.bankIndices[b], i + this.bandOffsets[b], paramArrayOfint[b]);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 698 */     int i = paramInt1 + paramInt3;
/* 699 */     int j = paramInt2 + paramInt4;
/*     */     
/* 701 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*     */     {
/*     */       
/* 704 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */ 
/*     */     
/* 708 */     for (byte b = 0; b < this.numBands; b++) {
/* 709 */       int k = paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[b];
/* 710 */       int m = b;
/* 711 */       int n = this.bankIndices[b];
/*     */       
/* 713 */       for (byte b1 = 0; b1 < paramInt4; b1++) {
/* 714 */         int i1 = k;
/* 715 */         for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 716 */           paramDataBuffer.setElem(n, i1++, paramArrayOfint[m]);
/* 717 */           m += this.numBands;
/*     */         } 
/* 719 */         k += this.scanlineStride;
/*     */       } 
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
/* 739 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 740 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 743 */     paramDataBuffer.setElem(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt3], paramInt4);
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
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, float paramFloat, DataBuffer paramDataBuffer) {
/* 763 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 764 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 767 */     paramDataBuffer.setElemFloat(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt3], paramFloat);
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
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, double paramDouble, DataBuffer paramDataBuffer) {
/* 787 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 788 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 791 */     paramDataBuffer.setElemDouble(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt3], paramDouble);
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
/*     */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 812 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt3 > this.width || paramInt2 + paramInt4 > this.height) {
/* 813 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*     */     }
/*     */     
/* 816 */     int i = paramInt2 * this.scanlineStride + paramInt1 + this.bandOffsets[paramInt5];
/* 817 */     byte b1 = 0;
/* 818 */     int j = this.bankIndices[paramInt5];
/*     */     
/* 820 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 821 */       int k = i;
/* 822 */       for (byte b = 0; b < paramInt3; b++) {
/* 823 */         paramDataBuffer.setElem(j, k++, paramArrayOfint[b1++]);
/*     */       }
/* 825 */       i += this.scanlineStride;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int[] createOffsetArray(int paramInt) {
/* 830 */     int[] arrayOfInt = new int[paramInt];
/* 831 */     for (byte b = 0; b < paramInt; b++) {
/* 832 */       arrayOfInt[b] = 0;
/*     */     }
/* 834 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   private static int[] createIndicesArray(int paramInt) {
/* 838 */     int[] arrayOfInt = new int[paramInt];
/* 839 */     for (byte b = 0; b < paramInt; b++) {
/* 840 */       arrayOfInt[b] = b;
/*     */     }
/* 842 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 847 */     return super.hashCode() ^ 0x2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/BandedSampleModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */