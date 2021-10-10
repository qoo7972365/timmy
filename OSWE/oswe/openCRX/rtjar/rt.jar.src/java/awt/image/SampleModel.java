/*      */ package java.awt.image;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class SampleModel
/*      */ {
/*      */   protected int width;
/*      */   protected int height;
/*      */   protected int numBands;
/*      */   protected int dataType;
/*      */   
/*      */   static {
/*  104 */     ColorModel.loadLibraries();
/*  105 */     initIDs();
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
/*      */   public SampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  124 */     long l = paramInt2 * paramInt3;
/*  125 */     if (paramInt2 <= 0 || paramInt3 <= 0) {
/*  126 */       throw new IllegalArgumentException("Width (" + paramInt2 + ") and height (" + paramInt3 + ") must be > 0");
/*      */     }
/*      */     
/*  129 */     if (l >= 2147483647L) {
/*  130 */       throw new IllegalArgumentException("Dimensions (width=" + paramInt2 + " height=" + paramInt3 + ") are too large");
/*      */     }
/*      */ 
/*      */     
/*  134 */     if (paramInt1 < 0 || (paramInt1 > 5 && paramInt1 != 32))
/*      */     {
/*      */ 
/*      */       
/*  138 */       throw new IllegalArgumentException("Unsupported dataType: " + paramInt1);
/*      */     }
/*      */ 
/*      */     
/*  142 */     if (paramInt4 <= 0) {
/*  143 */       throw new IllegalArgumentException("Number of bands must be > 0");
/*      */     }
/*      */     
/*  146 */     this.dataType = paramInt1;
/*  147 */     this.width = paramInt2;
/*  148 */     this.height = paramInt3;
/*  149 */     this.numBands = paramInt4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getWidth() {
/*  157 */     return this.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getHeight() {
/*  165 */     return this.height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumBands() {
/*  173 */     return this.numBands;
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
/*      */   public final int getDataType() {
/*  197 */     return this.dataType;
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
/*      */   public int getTransferType() {
/*  218 */     return this.dataType;
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
/*      */   public int[] getPixel(int paramInt1, int paramInt2, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*      */     int[] arrayOfInt;
/*  241 */     if (paramArrayOfint != null) {
/*  242 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  244 */       arrayOfInt = new int[this.numBands];
/*      */     } 
/*  246 */     for (byte b = 0; b < this.numBands; b++) {
/*  247 */       arrayOfInt[b] = getSample(paramInt1, paramInt2, b, paramDataBuffer);
/*      */     }
/*      */     
/*  250 */     return arrayOfInt;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject, DataBuffer paramDataBuffer) {
/*      */     byte[] arrayOfByte;
/*      */     int n;
/*      */     short[] arrayOfShort;
/*      */     int i1, arrayOfInt[], i2;
/*      */     float[] arrayOfFloat;
/*      */     int i3;
/*      */     double[] arrayOfDouble;
/*  356 */     int i4, i = getTransferType();
/*  357 */     int j = getNumDataElements();
/*  358 */     byte b = 0;
/*  359 */     Object object = null;
/*      */     
/*  361 */     int k = paramInt1 + paramInt3;
/*  362 */     int m = paramInt2 + paramInt4;
/*      */     
/*  364 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || k < 0 || k > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || m < 0 || m > this.height)
/*      */     {
/*      */       
/*  367 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/*  370 */     switch (i) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*  377 */         if (paramObject == null) {
/*  378 */           arrayOfByte = new byte[j * paramInt3 * paramInt4];
/*      */         } else {
/*  380 */           arrayOfByte = (byte[])paramObject;
/*      */         } 
/*  382 */         for (n = paramInt2; n < m; n++) {
/*  383 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  384 */             object = getDataElements(i5, n, object, paramDataBuffer);
/*  385 */             byte[] arrayOfByte1 = (byte[])object;
/*  386 */             for (byte b1 = 0; b1 < j; b1++) {
/*  387 */               arrayOfByte[b++] = arrayOfByte1[b1];
/*      */             }
/*      */           } 
/*      */         } 
/*  391 */         paramObject = arrayOfByte;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 2:
/*  400 */         if (paramObject == null) {
/*  401 */           arrayOfShort = new short[j * paramInt3 * paramInt4];
/*      */         } else {
/*  403 */           arrayOfShort = (short[])paramObject;
/*      */         } 
/*  405 */         for (i1 = paramInt2; i1 < m; i1++) {
/*  406 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  407 */             object = getDataElements(i5, i1, object, paramDataBuffer);
/*  408 */             short[] arrayOfShort1 = (short[])object;
/*  409 */             for (byte b1 = 0; b1 < j; b1++) {
/*  410 */               arrayOfShort[b++] = arrayOfShort1[b1];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  415 */         paramObject = arrayOfShort;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  423 */         if (paramObject == null) {
/*  424 */           arrayOfInt = new int[j * paramInt3 * paramInt4];
/*      */         } else {
/*  426 */           arrayOfInt = (int[])paramObject;
/*      */         } 
/*  428 */         for (i2 = paramInt2; i2 < m; i2++) {
/*  429 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  430 */             object = getDataElements(i5, i2, object, paramDataBuffer);
/*  431 */             int[] arrayOfInt1 = (int[])object;
/*  432 */             for (byte b1 = 0; b1 < j; b1++) {
/*  433 */               arrayOfInt[b++] = arrayOfInt1[b1];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  438 */         paramObject = arrayOfInt;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  446 */         if (paramObject == null) {
/*  447 */           arrayOfFloat = new float[j * paramInt3 * paramInt4];
/*      */         } else {
/*  449 */           arrayOfFloat = (float[])paramObject;
/*      */         } 
/*  451 */         for (i3 = paramInt2; i3 < m; i3++) {
/*  452 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  453 */             object = getDataElements(i5, i3, object, paramDataBuffer);
/*  454 */             float[] arrayOfFloat1 = (float[])object;
/*  455 */             for (byte b1 = 0; b1 < j; b1++) {
/*  456 */               arrayOfFloat[b++] = arrayOfFloat1[b1];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  461 */         paramObject = arrayOfFloat;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/*  469 */         if (paramObject == null) {
/*  470 */           arrayOfDouble = new double[j * paramInt3 * paramInt4];
/*      */         } else {
/*  472 */           arrayOfDouble = (double[])paramObject;
/*      */         } 
/*  474 */         for (i4 = paramInt2; i4 < m; i4++) {
/*  475 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  476 */             object = getDataElements(i5, i4, object, paramDataBuffer);
/*  477 */             double[] arrayOfDouble1 = (double[])object;
/*  478 */             for (byte b1 = 0; b1 < j; b1++) {
/*  479 */               arrayOfDouble[b++] = arrayOfDouble1[b1];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  484 */         paramObject = arrayOfDouble;
/*      */         break;
/*      */     } 
/*      */     
/*  488 */     return paramObject;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject, DataBuffer paramDataBuffer) {
/*      */     byte[] arrayOfByte1, arrayOfByte2;
/*      */     int n;
/*      */     short[] arrayOfShort1, arrayOfShort2;
/*      */     int i1, arrayOfInt1[], arrayOfInt2[], i2;
/*      */     float[] arrayOfFloat1, arrayOfFloat2;
/*      */     int i3;
/*      */     double[] arrayOfDouble1, arrayOfDouble2;
/*      */     int i4;
/*  586 */     byte b = 0;
/*  587 */     Object object = null;
/*  588 */     int i = getTransferType();
/*  589 */     int j = getNumDataElements();
/*      */     
/*  591 */     int k = paramInt1 + paramInt3;
/*  592 */     int m = paramInt2 + paramInt4;
/*      */     
/*  594 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || k < 0 || k > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || m < 0 || m > this.height)
/*      */     {
/*      */       
/*  597 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/*  600 */     switch (i) {
/*      */ 
/*      */       
/*      */       case 0:
/*  604 */         arrayOfByte1 = (byte[])paramObject;
/*  605 */         arrayOfByte2 = new byte[j];
/*      */         
/*  607 */         for (n = paramInt2; n < m; n++) {
/*  608 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  609 */             for (byte b1 = 0; b1 < j; b1++) {
/*  610 */               arrayOfByte2[b1] = arrayOfByte1[b++];
/*      */             }
/*      */             
/*  613 */             setDataElements(i5, n, arrayOfByte2, paramDataBuffer);
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 2:
/*  621 */         arrayOfShort1 = (short[])paramObject;
/*  622 */         arrayOfShort2 = new short[j];
/*      */         
/*  624 */         for (i1 = paramInt2; i1 < m; i1++) {
/*  625 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  626 */             for (byte b1 = 0; b1 < j; b1++) {
/*  627 */               arrayOfShort2[b1] = arrayOfShort1[b++];
/*      */             }
/*      */             
/*  630 */             setDataElements(i5, i1, arrayOfShort2, paramDataBuffer);
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  637 */         arrayOfInt1 = (int[])paramObject;
/*  638 */         arrayOfInt2 = new int[j];
/*      */         
/*  640 */         for (i2 = paramInt2; i2 < m; i2++) {
/*  641 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  642 */             for (byte b1 = 0; b1 < j; b1++) {
/*  643 */               arrayOfInt2[b1] = arrayOfInt1[b++];
/*      */             }
/*      */             
/*  646 */             setDataElements(i5, i2, arrayOfInt2, paramDataBuffer);
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  653 */         arrayOfFloat1 = (float[])paramObject;
/*  654 */         arrayOfFloat2 = new float[j];
/*      */         
/*  656 */         for (i3 = paramInt2; i3 < m; i3++) {
/*  657 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  658 */             for (byte b1 = 0; b1 < j; b1++) {
/*  659 */               arrayOfFloat2[b1] = arrayOfFloat1[b++];
/*      */             }
/*      */             
/*  662 */             setDataElements(i5, i3, arrayOfFloat2, paramDataBuffer);
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/*  669 */         arrayOfDouble1 = (double[])paramObject;
/*  670 */         arrayOfDouble2 = new double[j];
/*      */         
/*  672 */         for (i4 = paramInt2; i4 < m; i4++) {
/*  673 */           for (int i5 = paramInt1; i5 < k; i5++) {
/*  674 */             for (byte b1 = 0; b1 < j; b1++) {
/*  675 */               arrayOfDouble2[b1] = arrayOfDouble1[b++];
/*      */             }
/*      */             
/*  678 */             setDataElements(i5, i4, arrayOfDouble2, paramDataBuffer);
/*      */           } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getPixel(int paramInt1, int paramInt2, float[] paramArrayOffloat, DataBuffer paramDataBuffer) {
/*      */     float[] arrayOfFloat;
/*  706 */     if (paramArrayOffloat != null) {
/*  707 */       arrayOfFloat = paramArrayOffloat;
/*      */     } else {
/*  709 */       arrayOfFloat = new float[this.numBands];
/*      */     } 
/*  711 */     for (byte b = 0; b < this.numBands; b++) {
/*  712 */       arrayOfFloat[b] = getSampleFloat(paramInt1, paramInt2, b, paramDataBuffer);
/*      */     }
/*  714 */     return arrayOfFloat;
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
/*      */   public double[] getPixel(int paramInt1, int paramInt2, double[] paramArrayOfdouble, DataBuffer paramDataBuffer) {
/*      */     double[] arrayOfDouble;
/*  737 */     if (paramArrayOfdouble != null) {
/*  738 */       arrayOfDouble = paramArrayOfdouble;
/*      */     } else {
/*  740 */       arrayOfDouble = new double[this.numBands];
/*      */     } 
/*  742 */     for (byte b = 0; b < this.numBands; b++) {
/*  743 */       arrayOfDouble[b] = getSampleDouble(paramInt1, paramInt2, b, paramDataBuffer);
/*      */     }
/*  745 */     return arrayOfDouble;
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
/*      */   public int[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*      */     int[] arrayOfInt;
/*  770 */     byte b = 0;
/*  771 */     int i = paramInt1 + paramInt3;
/*  772 */     int j = paramInt2 + paramInt4;
/*      */     
/*  774 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/*  777 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/*  780 */     if (paramArrayOfint != null) {
/*  781 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  783 */       arrayOfInt = new int[this.numBands * paramInt3 * paramInt4];
/*      */     } 
/*  785 */     for (int k = paramInt2; k < j; k++) {
/*  786 */       for (int m = paramInt1; m < i; m++) {
/*  787 */         for (byte b1 = 0; b1 < this.numBands; b1++) {
/*  788 */           arrayOfInt[b++] = getSample(m, k, b1, paramDataBuffer);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  793 */     return arrayOfInt;
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
/*      */   public float[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat, DataBuffer paramDataBuffer) {
/*      */     float[] arrayOfFloat;
/*  818 */     byte b = 0;
/*  819 */     int i = paramInt1 + paramInt3;
/*  820 */     int j = paramInt2 + paramInt4;
/*      */     
/*  822 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/*  825 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/*  828 */     if (paramArrayOffloat != null) {
/*  829 */       arrayOfFloat = paramArrayOffloat;
/*      */     } else {
/*  831 */       arrayOfFloat = new float[this.numBands * paramInt3 * paramInt4];
/*      */     } 
/*  833 */     for (int k = paramInt2; k < j; k++) {
/*  834 */       for (int m = paramInt1; m < i; m++) {
/*  835 */         for (byte b1 = 0; b1 < this.numBands; b1++) {
/*  836 */           arrayOfFloat[b++] = getSampleFloat(m, k, b1, paramDataBuffer);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  841 */     return arrayOfFloat;
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
/*      */   public double[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble, DataBuffer paramDataBuffer) {
/*      */     double[] arrayOfDouble;
/*  865 */     byte b = 0;
/*  866 */     int i = paramInt1 + paramInt3;
/*  867 */     int j = paramInt2 + paramInt4;
/*      */     
/*  869 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/*  872 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/*  875 */     if (paramArrayOfdouble != null) {
/*  876 */       arrayOfDouble = paramArrayOfdouble;
/*      */     } else {
/*  878 */       arrayOfDouble = new double[this.numBands * paramInt3 * paramInt4];
/*      */     } 
/*      */     
/*  881 */     for (int k = paramInt2; k < j; k++) {
/*  882 */       for (int m = paramInt1; m < i; m++) {
/*  883 */         for (byte b1 = 0; b1 < this.numBands; b1++) {
/*  884 */           arrayOfDouble[b++] = getSampleDouble(m, k, b1, paramDataBuffer);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  889 */     return arrayOfDouble;
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
/*      */   public float getSampleFloat(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/*  930 */     return getSample(paramInt1, paramInt2, paramInt3, paramDataBuffer);
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
/*      */   public double getSampleDouble(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer) {
/*  953 */     return getSample(paramInt1, paramInt2, paramInt3, paramDataBuffer);
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
/*      */   public int[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/*      */     int[] arrayOfInt;
/*  981 */     byte b = 0;
/*  982 */     int i = paramInt1 + paramInt3;
/*  983 */     int j = paramInt2 + paramInt4;
/*      */     
/*  985 */     if (paramInt1 < 0 || i < paramInt1 || i > this.width || paramInt2 < 0 || j < paramInt2 || j > this.height)
/*      */     {
/*      */       
/*  988 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/*  991 */     if (paramArrayOfint != null) {
/*  992 */       arrayOfInt = paramArrayOfint;
/*      */     } else {
/*  994 */       arrayOfInt = new int[paramInt3 * paramInt4];
/*      */     } 
/*  996 */     for (int k = paramInt2; k < j; k++) {
/*  997 */       for (int m = paramInt1; m < i; m++) {
/*  998 */         arrayOfInt[b++] = getSample(m, k, paramInt5, paramDataBuffer);
/*      */       }
/*      */     } 
/*      */     
/* 1002 */     return arrayOfInt;
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
/*      */   public float[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[] paramArrayOffloat, DataBuffer paramDataBuffer) {
/*      */     float[] arrayOfFloat;
/* 1030 */     byte b = 0;
/* 1031 */     int i = paramInt1 + paramInt3;
/* 1032 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1034 */     if (paramInt1 < 0 || i < paramInt1 || i > this.width || paramInt2 < 0 || j < paramInt2 || j > this.height)
/*      */     {
/*      */       
/* 1037 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates");
/*      */     }
/*      */     
/* 1040 */     if (paramArrayOffloat != null) {
/* 1041 */       arrayOfFloat = paramArrayOffloat;
/*      */     } else {
/* 1043 */       arrayOfFloat = new float[paramInt3 * paramInt4];
/*      */     } 
/* 1045 */     for (int k = paramInt2; k < j; k++) {
/* 1046 */       for (int m = paramInt1; m < i; m++) {
/* 1047 */         arrayOfFloat[b++] = getSampleFloat(m, k, paramInt5, paramDataBuffer);
/*      */       }
/*      */     } 
/*      */     
/* 1051 */     return arrayOfFloat;
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
/*      */   public double[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double[] paramArrayOfdouble, DataBuffer paramDataBuffer) {
/*      */     double[] arrayOfDouble;
/* 1079 */     byte b = 0;
/* 1080 */     int i = paramInt1 + paramInt3;
/* 1081 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1083 */     if (paramInt1 < 0 || i < paramInt1 || i > this.width || paramInt2 < 0 || j < paramInt2 || j > this.height)
/*      */     {
/*      */       
/* 1086 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates");
/*      */     }
/*      */     
/* 1089 */     if (paramArrayOfdouble != null) {
/* 1090 */       arrayOfDouble = paramArrayOfdouble;
/*      */     } else {
/* 1092 */       arrayOfDouble = new double[paramInt3 * paramInt4];
/*      */     } 
/* 1094 */     for (int k = paramInt2; k < j; k++) {
/* 1095 */       for (int m = paramInt1; m < i; m++) {
/* 1096 */         arrayOfDouble[b++] = getSampleDouble(m, k, paramInt5, paramDataBuffer);
/*      */       }
/*      */     } 
/*      */     
/* 1100 */     return arrayOfDouble;
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
/*      */   public void setPixel(int paramInt1, int paramInt2, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 1119 */     for (byte b = 0; b < this.numBands; b++) {
/* 1120 */       setSample(paramInt1, paramInt2, b, paramArrayOfint[b], paramDataBuffer);
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
/*      */   public void setPixel(int paramInt1, int paramInt2, float[] paramArrayOffloat, DataBuffer paramDataBuffer) {
/* 1139 */     for (byte b = 0; b < this.numBands; b++) {
/* 1140 */       setSample(paramInt1, paramInt2, b, paramArrayOffloat[b], paramDataBuffer);
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
/*      */   public void setPixel(int paramInt1, int paramInt2, double[] paramArrayOfdouble, DataBuffer paramDataBuffer) {
/* 1158 */     for (byte b = 0; b < this.numBands; b++) {
/* 1159 */       setSample(paramInt1, paramInt2, b, paramArrayOfdouble[b], paramDataBuffer);
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
/*      */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 1181 */     byte b = 0;
/* 1182 */     int i = paramInt1 + paramInt3;
/* 1183 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1185 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/* 1188 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/* 1191 */     for (int k = paramInt2; k < j; k++) {
/* 1192 */       for (int m = paramInt1; m < i; m++) {
/* 1193 */         for (byte b1 = 0; b1 < this.numBands; b1++) {
/* 1194 */           setSample(m, k, b1, paramArrayOfint[b++], paramDataBuffer);
/*      */         }
/*      */       } 
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
/*      */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat, DataBuffer paramDataBuffer) {
/* 1219 */     byte b = 0;
/* 1220 */     int i = paramInt1 + paramInt3;
/* 1221 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1223 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/* 1226 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/* 1229 */     for (int k = paramInt2; k < j; k++) {
/* 1230 */       for (int m = paramInt1; m < i; m++) {
/* 1231 */         for (byte b1 = 0; b1 < this.numBands; b1++) {
/* 1232 */           setSample(m, k, b1, paramArrayOffloat[b++], paramDataBuffer);
/*      */         }
/*      */       } 
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
/*      */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble, DataBuffer paramDataBuffer) {
/* 1257 */     byte b = 0;
/* 1258 */     int i = paramInt1 + paramInt3;
/* 1259 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1261 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/* 1264 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/* 1267 */     for (int k = paramInt2; k < j; k++) {
/* 1268 */       for (int m = paramInt1; m < i; m++) {
/* 1269 */         for (byte b1 = 0; b1 < this.numBands; b1++) {
/* 1270 */           setSample(m, k, b1, paramArrayOfdouble[b++], paramDataBuffer);
/*      */         }
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1319 */     int i = (int)paramFloat;
/*      */     
/* 1321 */     setSample(paramInt1, paramInt2, paramInt3, i, paramDataBuffer);
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
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, double paramDouble, DataBuffer paramDataBuffer) {
/* 1347 */     int i = (int)paramDouble;
/*      */     
/* 1349 */     setSample(paramInt1, paramInt2, paramInt3, i, paramDataBuffer);
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
/*      */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, DataBuffer paramDataBuffer) {
/* 1374 */     byte b = 0;
/* 1375 */     int i = paramInt1 + paramInt3;
/* 1376 */     int j = paramInt2 + paramInt4;
/* 1377 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/* 1380 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/* 1383 */     for (int k = paramInt2; k < j; k++) {
/* 1384 */       for (int m = paramInt1; m < i; m++) {
/* 1385 */         setSample(m, k, paramInt5, paramArrayOfint[b++], paramDataBuffer);
/*      */       }
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
/*      */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[] paramArrayOffloat, DataBuffer paramDataBuffer) {
/* 1411 */     byte b = 0;
/* 1412 */     int i = paramInt1 + paramInt3;
/* 1413 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1415 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/* 1418 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/* 1421 */     for (int k = paramInt2; k < j; k++) {
/* 1422 */       for (int m = paramInt1; m < i; m++) {
/* 1423 */         setSample(m, k, paramInt5, paramArrayOffloat[b++], paramDataBuffer);
/*      */       }
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
/*      */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double[] paramArrayOfdouble, DataBuffer paramDataBuffer) {
/* 1449 */     byte b = 0;
/* 1450 */     int i = paramInt1 + paramInt3;
/* 1451 */     int j = paramInt2 + paramInt4;
/*      */ 
/*      */     
/* 1454 */     if (paramInt1 < 0 || paramInt1 >= this.width || paramInt3 > this.width || i < 0 || i > this.width || paramInt2 < 0 || paramInt2 >= this.height || paramInt4 > this.height || j < 0 || j > this.height)
/*      */     {
/*      */       
/* 1457 */       throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
/*      */     }
/*      */     
/* 1460 */     for (int k = paramInt2; k < j; k++) {
/* 1461 */       for (int m = paramInt1; m < i; m++)
/* 1462 */         setSample(m, k, paramInt5, paramArrayOfdouble[b++], paramDataBuffer); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   public abstract int getNumDataElements();
/*      */   
/*      */   public abstract Object getDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer);
/*      */   
/*      */   public abstract void setDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer);
/*      */   
/*      */   public abstract int getSample(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer);
/*      */   
/*      */   public abstract void setSample(int paramInt1, int paramInt2, int paramInt3, int paramInt4, DataBuffer paramDataBuffer);
/*      */   
/*      */   public abstract SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract SampleModel createSubsetSampleModel(int[] paramArrayOfint);
/*      */   
/*      */   public abstract DataBuffer createDataBuffer();
/*      */   
/*      */   public abstract int[] getSampleSize();
/*      */   
/*      */   public abstract int getSampleSize(int paramInt);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/SampleModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */