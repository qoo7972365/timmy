/*      */ package java.awt.image;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ComponentSampleModel
/*      */   extends SampleModel
/*      */ {
/*      */   protected int[] bandOffsets;
/*      */   protected int[] bankIndices;
/*   88 */   protected int numBands = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   protected int numBanks = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int scanlineStride;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int pixelStride;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  109 */     ColorModel.loadLibraries();
/*  110 */     initIDs();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ComponentSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
/*  146 */     super(paramInt1, paramInt2, paramInt3, paramArrayOfint.length);
/*  147 */     this.dataType = paramInt1;
/*  148 */     this.pixelStride = paramInt4;
/*  149 */     this.scanlineStride = paramInt5;
/*  150 */     this.bandOffsets = (int[])paramArrayOfint.clone();
/*  151 */     this.numBands = this.bandOffsets.length;
/*  152 */     if (paramInt4 < 0) {
/*  153 */       throw new IllegalArgumentException("Pixel stride must be >= 0");
/*      */     }
/*      */     
/*  156 */     if (paramInt5 < 0) {
/*  157 */       throw new IllegalArgumentException("Scanline stride must be >= 0");
/*      */     }
/*  159 */     if (this.numBands < 1) {
/*  160 */       throw new IllegalArgumentException("Must have at least one band.");
/*      */     }
/*  162 */     if (paramInt1 < 0 || paramInt1 > 5)
/*      */     {
/*  164 */       throw new IllegalArgumentException("Unsupported dataType.");
/*      */     }
/*  166 */     this.bankIndices = new int[this.numBands];
/*  167 */     for (byte b = 0; b < this.numBands; b++) {
/*  168 */       this.bankIndices[b] = 0;
/*      */     }
/*  170 */     verify();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ComponentSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  210 */     super(paramInt1, paramInt2, paramInt3, paramArrayOfint2.length);
/*  211 */     this.dataType = paramInt1;
/*  212 */     this.pixelStride = paramInt4;
/*  213 */     this.scanlineStride = paramInt5;
/*  214 */     this.bandOffsets = (int[])paramArrayOfint2.clone();
/*  215 */     this.bankIndices = (int[])paramArrayOfint1.clone();
/*  216 */     if (paramInt4 < 0) {
/*  217 */       throw new IllegalArgumentException("Pixel stride must be >= 0");
/*      */     }
/*      */     
/*  220 */     if (paramInt5 < 0) {
/*  221 */       throw new IllegalArgumentException("Scanline stride must be >= 0");
/*      */     }
/*  223 */     if (paramInt1 < 0 || paramInt1 > 5)
/*      */     {
/*  225 */       throw new IllegalArgumentException("Unsupported dataType.");
/*      */     }
/*  227 */     int i = this.bankIndices[0];
/*  228 */     if (i < 0) {
/*  229 */       throw new IllegalArgumentException("Index of bank 0 is less than 0 (" + i + ")");
/*      */     }
/*      */     
/*  232 */     for (byte b = 1; b < this.bankIndices.length; b++) {
/*  233 */       if (this.bankIndices[b] > i) {
/*  234 */         i = this.bankIndices[b];
/*      */       }
/*  236 */       else if (this.bankIndices[b] < 0) {
/*  237 */         throw new IllegalArgumentException("Index of bank " + b + " is less than 0 (" + i + ")");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  242 */     this.numBanks = i + 1;
/*  243 */     this.numBands = this.bandOffsets.length;
/*  244 */     if (this.bandOffsets.length != this.bankIndices.length) {
/*  245 */       throw new IllegalArgumentException("Length of bandOffsets must equal length of bankIndices.");
/*      */     }
/*      */     
/*  248 */     verify();
/*      */   }
/*      */   
/*      */   private void verify() {
/*  252 */     int i = getBufferSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getBufferSize() {
/*  260 */     int i = this.bandOffsets[0]; int j;
/*  261 */     for (j = 1; j < this.bandOffsets.length; j++) {
/*  262 */       i = Math.max(i, this.bandOffsets[j]);
/*      */     }
/*      */     
/*  265 */     if (i < 0 || i > 2147483646) {
/*  266 */       throw new IllegalArgumentException("Invalid band offset");
/*      */     }
/*      */     
/*  269 */     if (this.pixelStride < 0 || this.pixelStride > Integer.MAX_VALUE / this.width) {
/*  270 */       throw new IllegalArgumentException("Invalid pixel stride");
/*      */     }
/*      */     
/*  273 */     if (this.scanlineStride < 0 || this.scanlineStride > Integer.MAX_VALUE / this.height) {
/*  274 */       throw new IllegalArgumentException("Invalid scanline stride");
/*      */     }
/*      */     
/*  277 */     j = i + 1;
/*      */     
/*  279 */     int k = this.pixelStride * (this.width - 1);
/*      */     
/*  281 */     if (k > Integer.MAX_VALUE - j) {
/*  282 */       throw new IllegalArgumentException("Invalid pixel stride");
/*      */     }
/*      */     
/*  285 */     j += k;
/*      */     
/*  287 */     k = this.scanlineStride * (this.height - 1);
/*      */     
/*  289 */     if (k > Integer.MAX_VALUE - j) {
/*  290 */       throw new IllegalArgumentException("Invalid scan stride");
/*      */     }
/*      */     
/*  293 */     j += k;
/*      */     
/*  295 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int[] orderBands(int[] paramArrayOfint, int paramInt) {
/*  302 */     int[] arrayOfInt1 = new int[paramArrayOfint.length];
/*  303 */     int[] arrayOfInt2 = new int[paramArrayOfint.length];
/*      */     byte b;
/*  305 */     for (b = 0; b < arrayOfInt1.length; ) { arrayOfInt1[b] = b; b++; }
/*      */     
/*  307 */     for (b = 0; b < arrayOfInt2.length; b++) {
/*  308 */       int i = b;
/*  309 */       for (int j = b + 1; j < arrayOfInt2.length; j++) {
/*  310 */         if (paramArrayOfint[arrayOfInt1[i]] > paramArrayOfint[arrayOfInt1[j]]) {
/*  311 */           i = j;
/*      */         }
/*      */       } 
/*  314 */       arrayOfInt2[arrayOfInt1[i]] = b * paramInt;
/*  315 */       arrayOfInt1[i] = arrayOfInt1[b];
/*      */     } 
/*  317 */     return arrayOfInt2;
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
/*      */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2) {
/*      */     int[] arrayOfInt;
/*  332 */     Object object = null;
/*      */     
/*  334 */     int i = this.bandOffsets[0];
/*  335 */     int j = this.bandOffsets[0]; int k;
/*  336 */     for (k = 1; k < this.bandOffsets.length; k++) {
/*  337 */       i = Math.min(i, this.bandOffsets[k]);
/*  338 */       j = Math.max(j, this.bandOffsets[k]);
/*      */     } 
/*  340 */     j -= i;
/*      */     
/*  342 */     k = this.bandOffsets.length;
/*      */     
/*  344 */     int m = Math.abs(this.pixelStride);
/*  345 */     int n = Math.abs(this.scanlineStride);
/*  346 */     int i1 = Math.abs(j);
/*      */     
/*  348 */     if (m > n) {
/*  349 */       if (m > i1) {
/*  350 */         if (n > i1) {
/*  351 */           arrayOfInt = new int[this.bandOffsets.length];
/*  352 */           for (byte b1 = 0; b1 < k; b1++)
/*  353 */             arrayOfInt[b1] = this.bandOffsets[b1] - i; 
/*  354 */           n = i1 + 1;
/*  355 */           m = n * paramInt2;
/*      */         } else {
/*  357 */           arrayOfInt = orderBands(this.bandOffsets, n * paramInt2);
/*  358 */           m = k * n * paramInt2;
/*      */         } 
/*      */       } else {
/*  361 */         m = n * paramInt2;
/*  362 */         arrayOfInt = orderBands(this.bandOffsets, m * paramInt1);
/*      */       }
/*      */     
/*  365 */     } else if (m > i1) {
/*  366 */       arrayOfInt = new int[this.bandOffsets.length];
/*  367 */       for (byte b1 = 0; b1 < k; b1++)
/*  368 */         arrayOfInt[b1] = this.bandOffsets[b1] - i; 
/*  369 */       m = i1 + 1;
/*  370 */       n = m * paramInt1;
/*      */     }
/*  372 */     else if (n > i1) {
/*  373 */       arrayOfInt = orderBands(this.bandOffsets, m * paramInt1);
/*  374 */       n = k * m * paramInt1;
/*      */     } else {
/*  376 */       n = m * paramInt1;
/*  377 */       arrayOfInt = orderBands(this.bandOffsets, n * paramInt2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  383 */     int i2 = 0;
/*  384 */     if (this.scanlineStride < 0) {
/*  385 */       i2 += n * paramInt2;
/*  386 */       n *= -1;
/*      */     } 
/*  388 */     if (this.pixelStride < 0) {
/*  389 */       i2 += m * paramInt1;
/*  390 */       m *= -1;
/*      */     } 
/*      */     
/*  393 */     for (byte b = 0; b < k; b++)
/*  394 */       arrayOfInt[b] = arrayOfInt[b] + i2; 
/*  395 */     return new ComponentSampleModel(this.dataType, paramInt1, paramInt2, m, n, this.bankIndices, arrayOfInt);
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
/*      */   public SampleModel createSubsetSampleModel(int[] paramArrayOfint) {
/*  412 */     if (paramArrayOfint.length > this.bankIndices.length) {
/*  413 */       throw new RasterFormatException("There are only " + this.bankIndices.length + " bands");
/*      */     }
/*      */     
/*  416 */     int[] arrayOfInt1 = new int[paramArrayOfint.length];
/*  417 */     int[] arrayOfInt2 = new int[paramArrayOfint.length];
/*      */     
/*  419 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/*  420 */       arrayOfInt1[b] = this.bankIndices[paramArrayOfint[b]];
/*  421 */       arrayOfInt2[b] = this.bandOffsets[paramArrayOfint[b]];
/*      */     } 
/*      */     
/*  424 */     return new ComponentSampleModel(this.dataType, this.width, this.height, this.pixelStride, this.scanlineStride, arrayOfInt1, arrayOfInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataBuffer createDataBuffer() {
/*      */     DataBufferUShort dataBufferUShort;
/*      */     DataBufferShort dataBufferShort;
/*      */     DataBufferInt dataBufferInt;
/*      */     DataBufferFloat dataBufferFloat;
/*      */     DataBufferDouble dataBufferDouble;
/*  440 */     DataBufferByte dataBufferByte = null;
/*      */     
/*  442 */     int i = getBufferSize();
/*  443 */     switch (this.dataType) {
/*      */       case 0:
/*  445 */         dataBufferByte = new DataBufferByte(i, this.numBanks);
/*      */         break;
/*      */       case 1:
/*  448 */         dataBufferUShort = new DataBufferUShort(i, this.numBanks);
/*      */         break;
/*      */       case 2:
/*  451 */         dataBufferShort = new DataBufferShort(i, this.numBanks);
/*      */         break;
/*      */       case 3:
/*  454 */         dataBufferInt = new DataBufferInt(i, this.numBanks);
/*      */         break;
/*      */       case 4:
/*  457 */         dataBufferFloat = new DataBufferFloat(i, this.numBanks);
/*      */         break;
/*      */       case 5:
/*  460 */         dataBufferDouble = new DataBufferDouble(i, this.numBanks);
/*      */         break;
/*      */     } 
/*      */     
/*  464 */     return dataBufferDouble;
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
/*      */   public int getOffset(int paramInt1, int paramInt2) {
/*  481 */     return paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[0];
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
/*      */   public int getOffset(int paramInt1, int paramInt2, int paramInt3) {
/*  498 */     return paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int[] getSampleSize() {
/*  508 */     int[] arrayOfInt = new int[this.numBands];
/*  509 */     int i = getSampleSize(0);
/*      */     
/*  511 */     for (byte b = 0; b < this.numBands; b++) {
/*  512 */       arrayOfInt[b] = i;
/*      */     }
/*  514 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getSampleSize(int paramInt) {
/*  522 */     return DataBuffer.getDataTypeSize(this.dataType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int[] getBankIndices() {
/*  529 */     return (int[])this.bankIndices.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int[] getBandOffsets() {
/*  536 */     return (int[])this.bandOffsets.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getScanlineStride() {
/*  543 */     return this.scanlineStride;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getPixelStride() {
/*  550 */     return this.pixelStride;
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
/*      */   public final int getNumDataElements() {
/*  568 */     return getNumBands();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer) {
/*      */     byte[] arrayOfByte;
/*      */     byte b1;
/*      */     short[] arrayOfShort;
/*      */     byte b2;
/*      */     int[] arrayOfInt;
/*      */     byte b3;
/*      */     float[] arrayOfFloat;
/*      */     byte b4;
/*      */     double[] arrayOfDouble;
/*      */     byte b5;
/*  620 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/*  621 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  625 */     int i = getTransferType();
/*  626 */     int j = getNumDataElements();
/*  627 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*      */     
/*  629 */     switch (i) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*  635 */         if (paramObject == null) {
/*  636 */           arrayOfByte = new byte[j];
/*      */         } else {
/*  638 */           arrayOfByte = (byte[])paramObject;
/*      */         } 
/*  640 */         for (b1 = 0; b1 < j; b1++) {
/*  641 */           arrayOfByte[b1] = (byte)paramDataBuffer.getElem(this.bankIndices[b1], k + this.bandOffsets[b1]);
/*      */         }
/*      */ 
/*      */         
/*  645 */         paramObject = arrayOfByte;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 2:
/*  653 */         if (paramObject == null) {
/*  654 */           arrayOfShort = new short[j];
/*      */         } else {
/*  656 */           arrayOfShort = (short[])paramObject;
/*      */         } 
/*  658 */         for (b2 = 0; b2 < j; b2++) {
/*  659 */           arrayOfShort[b2] = (short)paramDataBuffer.getElem(this.bankIndices[b2], k + this.bandOffsets[b2]);
/*      */         }
/*      */ 
/*      */         
/*  663 */         paramObject = arrayOfShort;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  670 */         if (paramObject == null) {
/*  671 */           arrayOfInt = new int[j];
/*      */         } else {
/*  673 */           arrayOfInt = (int[])paramObject;
/*      */         } 
/*  675 */         for (b3 = 0; b3 < j; b3++) {
/*  676 */           arrayOfInt[b3] = paramDataBuffer.getElem(this.bankIndices[b3], k + this.bandOffsets[b3]);
/*      */         }
/*      */ 
/*      */         
/*  680 */         paramObject = arrayOfInt;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  687 */         if (paramObject == null) {
/*  688 */           arrayOfFloat = new float[j];
/*      */         } else {
/*  690 */           arrayOfFloat = (float[])paramObject;
/*      */         } 
/*  692 */         for (b4 = 0; b4 < j; b4++) {
/*  693 */           arrayOfFloat[b4] = paramDataBuffer.getElemFloat(this.bankIndices[b4], k + this.bandOffsets[b4]);
/*      */         }
/*      */ 
/*      */         
/*  697 */         paramObject = arrayOfFloat;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/*  704 */         if (paramObject == null) {
/*  705 */           arrayOfDouble = new double[j];
/*      */         } else {
/*  707 */           arrayOfDouble = (double[])paramObject;
/*      */         } 
/*  709 */         for (b5 = 0; b5 < j; b5++) {
/*  710 */           arrayOfDouble[b5] = paramDataBuffer.getElemDouble(this.bankIndices[b5], k + this.bandOffsets[b5]);
/*      */         }
/*      */ 
/*      */         
/*  714 */         paramObject = arrayOfDouble;
/*      */         break;
/*      */     } 
/*      */     
/*  718 */     return paramObject;
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
/*      */   public int[] getPixel(int paramInt1, int paramInt2, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*      */     int[] arrayOfInt;
/*  738 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/*  739 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  743 */     if (paramArrayOfint != null) {
/*  744 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  746 */       arrayOfInt = new int[this.numBands];
/*      */     } 
/*  748 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*  749 */     for (byte b = 0; b < this.numBands; b++) {
/*  750 */       arrayOfInt[b] = paramDataBuffer.getElem(this.bankIndices[b], i + this.bandOffsets[b]);
/*      */     }
/*      */     
/*  753 */     return arrayOfInt;
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
/*      */   public int[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*  772 */     int arrayOfInt[], i = paramInt1 + paramInt3;
/*  773 */     int j = paramInt2 + paramInt4;
/*      */     
/*  775 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt2 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/*  778 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  782 */     if (paramArrayOfint != null) {
/*  783 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  785 */       arrayOfInt = new int[paramInt3 * paramInt4 * this.numBands];
/*      */     } 
/*  787 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*  788 */     byte b1 = 0;
/*      */     
/*  790 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/*  791 */       int m = k;
/*  792 */       for (byte b = 0; b < paramInt3; b++) {
/*  793 */         for (byte b3 = 0; b3 < this.numBands; b3++) {
/*  794 */           arrayOfInt[b1++] = paramDataBuffer
/*  795 */             .getElem(this.bankIndices[b3], m + this.bandOffsets[b3]);
/*      */         }
/*  797 */         m += this.pixelStride;
/*      */       } 
/*  799 */       k += this.scanlineStride;
/*      */     } 
/*  801 */     return arrayOfInt;
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
/*      */   public int getSample(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/*  818 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/*  819 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  822 */     return paramDataBuffer.getElem(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3]);
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
/*      */   public float getSampleFloat(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/*  842 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/*  843 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  847 */     return paramDataBuffer.getElemFloat(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3]);
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
/*      */   public double getSampleDouble(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/*  867 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/*  868 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  872 */     return paramDataBuffer.getElemDouble(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3]);
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
/*      */   public int[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*      */     int[] arrayOfInt;
/*  897 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt3 > this.width || paramInt2 + paramInt4 > this.height) {
/*  898 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  902 */     if (paramArrayOfint != null) {
/*  903 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  905 */       arrayOfInt = new int[paramInt3 * paramInt4];
/*      */     } 
/*  907 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt5];
/*  908 */     byte b1 = 0;
/*      */     
/*  910 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/*  911 */       int j = i;
/*  912 */       for (byte b = 0; b < paramInt3; b++) {
/*  913 */         arrayOfInt[b1++] = paramDataBuffer.getElem(this.bankIndices[paramInt5], j);
/*      */         
/*  915 */         j += this.pixelStride;
/*      */       } 
/*  917 */       i += this.scanlineStride;
/*      */     } 
/*  919 */     return arrayOfInt;
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
/*      */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer) {
/*      */     byte[] arrayOfByte;
/*      */     byte b1;
/*      */     short[] arrayOfShort;
/*      */     byte b2;
/*      */     int[] arrayOfInt;
/*      */     byte b3;
/*      */     float[] arrayOfFloat;
/*      */     byte b4;
/*      */     double[] arrayOfDouble;
/*      */     byte b5;
/*  960 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/*  961 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/*  965 */     int i = getTransferType();
/*  966 */     int j = getNumDataElements();
/*  967 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*      */     
/*  969 */     switch (i) {
/*      */ 
/*      */       
/*      */       case 0:
/*  973 */         arrayOfByte = (byte[])paramObject;
/*      */         
/*  975 */         for (b1 = 0; b1 < j; b1++) {
/*  976 */           paramDataBuffer.setElem(this.bankIndices[b1], k + this.bandOffsets[b1], arrayOfByte[b1] & 0xFF);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 2:
/*  984 */         arrayOfShort = (short[])paramObject;
/*      */         
/*  986 */         for (b2 = 0; b2 < j; b2++) {
/*  987 */           paramDataBuffer.setElem(this.bankIndices[b2], k + this.bandOffsets[b2], arrayOfShort[b2] & 0xFFFF);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  994 */         arrayOfInt = (int[])paramObject;
/*      */         
/*  996 */         for (b3 = 0; b3 < j; b3++) {
/*  997 */           paramDataBuffer.setElem(this.bankIndices[b3], k + this.bandOffsets[b3], arrayOfInt[b3]);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1004 */         arrayOfFloat = (float[])paramObject;
/*      */         
/* 1006 */         for (b4 = 0; b4 < j; b4++) {
/* 1007 */           paramDataBuffer.setElemFloat(this.bankIndices[b4], k + this.bandOffsets[b4], arrayOfFloat[b4]);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 1014 */         arrayOfDouble = (double[])paramObject;
/*      */         
/* 1016 */         for (b5 = 0; b5 < j; b5++) {
/* 1017 */           paramDataBuffer.setElemDouble(this.bankIndices[b5], k + this.bandOffsets[b5], arrayOfDouble[b5]);
/*      */         }
/*      */         break;
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
/*      */   public void setPixel(int paramInt1, int paramInt2, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 1037 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 1038 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1041 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/* 1042 */     for (byte b = 0; b < this.numBands; b++) {
/* 1043 */       paramDataBuffer.setElem(this.bankIndices[b], i + this.bandOffsets[b], paramArrayOfint[b]);
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
/*      */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 1063 */     int i = paramInt1 + paramInt3;
/* 1064 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1066 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/* 1069 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */ 
/*      */     
/* 1073 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/* 1074 */     byte b1 = 0;
/*      */     
/* 1076 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 1077 */       int m = k;
/* 1078 */       for (byte b = 0; b < paramInt3; b++) {
/* 1079 */         for (byte b3 = 0; b3 < this.numBands; b3++) {
/* 1080 */           paramDataBuffer.setElem(this.bankIndices[b3], m + this.bandOffsets[b3], paramArrayOfint[b1++]);
/*      */         }
/*      */         
/* 1083 */         m += this.pixelStride;
/*      */       } 
/* 1085 */       k += this.scanlineStride;
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
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, int paramInt4, DataBuffer paramDataBuffer) {
/* 1104 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 1105 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1108 */     paramDataBuffer.setElem(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3], paramInt4);
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
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, float paramFloat, DataBuffer paramDataBuffer) {
/* 1128 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 1129 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1132 */     paramDataBuffer.setElemFloat(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3], paramFloat);
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
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, double paramDouble, DataBuffer paramDataBuffer) {
/* 1153 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.width || paramInt2 >= this.height) {
/* 1154 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1157 */     paramDataBuffer.setElemDouble(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3], paramDouble);
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
/*      */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 1179 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt3 > this.width || paramInt2 + paramInt4 > this.height) {
/* 1180 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1183 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt5];
/* 1184 */     byte b1 = 0;
/*      */     
/* 1186 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 1187 */       int j = i;
/* 1188 */       for (byte b = 0; b < paramInt3; b++) {
/* 1189 */         paramDataBuffer.setElem(this.bankIndices[paramInt5], j, paramArrayOfint[b1++]);
/* 1190 */         j += this.pixelStride;
/*      */       } 
/* 1192 */       i += this.scanlineStride;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 1197 */     if (paramObject == null || !(paramObject instanceof ComponentSampleModel)) {
/* 1198 */       return false;
/*      */     }
/*      */     
/* 1201 */     ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramObject;
/* 1202 */     return (this.width == componentSampleModel.width && this.height == componentSampleModel.height && this.numBands == componentSampleModel.numBands && this.dataType == componentSampleModel.dataType && 
/*      */ 
/*      */ 
/*      */       
/* 1206 */       Arrays.equals(this.bandOffsets, componentSampleModel.bandOffsets) && 
/* 1207 */       Arrays.equals(this.bankIndices, componentSampleModel.bankIndices) && this.numBands == componentSampleModel.numBands && this.numBanks == componentSampleModel.numBanks && this.scanlineStride == componentSampleModel.scanlineStride && this.pixelStride == componentSampleModel.pixelStride);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1216 */     int i = 0;
/* 1217 */     i = this.width;
/* 1218 */     i <<= 8;
/* 1219 */     i ^= this.height;
/* 1220 */     i <<= 8;
/* 1221 */     i ^= this.numBands;
/* 1222 */     i <<= 8;
/* 1223 */     i ^= this.dataType;
/* 1224 */     i <<= 8; byte b;
/* 1225 */     for (b = 0; b < this.bandOffsets.length; b++) {
/* 1226 */       i ^= this.bandOffsets[b];
/* 1227 */       i <<= 8;
/*      */     } 
/* 1229 */     for (b = 0; b < this.bankIndices.length; b++) {
/* 1230 */       i ^= this.bankIndices[b];
/* 1231 */       i <<= 8;
/*      */     } 
/* 1233 */     i ^= this.numBands;
/* 1234 */     i <<= 8;
/* 1235 */     i ^= this.numBanks;
/* 1236 */     i <<= 8;
/* 1237 */     i ^= this.scanlineStride;
/* 1238 */     i <<= 8;
/* 1239 */     i ^= this.pixelStride;
/* 1240 */     return i;
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ComponentSampleModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */