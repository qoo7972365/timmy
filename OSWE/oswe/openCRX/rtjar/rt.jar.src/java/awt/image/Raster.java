/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import sun.awt.image.ByteBandedRaster;
/*      */ import sun.awt.image.ByteInterleavedRaster;
/*      */ import sun.awt.image.BytePackedRaster;
/*      */ import sun.awt.image.IntegerInterleavedRaster;
/*      */ import sun.awt.image.ShortBandedRaster;
/*      */ import sun.awt.image.ShortInterleavedRaster;
/*      */ import sun.awt.image.SunWritableRaster;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Raster
/*      */ {
/*      */   protected SampleModel sampleModel;
/*      */   protected DataBuffer dataBuffer;
/*      */   protected int minX;
/*      */   protected int minY;
/*      */   protected int width;
/*      */   protected int height;
/*      */   protected int sampleModelTranslateX;
/*      */   protected int sampleModelTranslateY;
/*      */   protected int numBands;
/*      */   protected int numDataElements;
/*      */   protected Raster parent;
/*      */   
/*      */   static {
/*  172 */     ColorModel.loadLibraries();
/*  173 */     initIDs();
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
/*      */   public static WritableRaster createInterleavedRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Point paramPoint) {
/*  208 */     int[] arrayOfInt = new int[paramInt4];
/*  209 */     for (byte b = 0; b < paramInt4; b++) {
/*  210 */       arrayOfInt[b] = b;
/*      */     }
/*  212 */     return createInterleavedRaster(paramInt1, paramInt2, paramInt3, paramInt2 * paramInt4, paramInt4, arrayOfInt, paramPoint);
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
/*      */   public static WritableRaster createInterleavedRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint, Point paramPoint) {
/*      */     DataBufferByte dataBufferByte;
/*      */     DataBufferUShort dataBufferUShort;
/*  261 */     int i = paramInt4 * (paramInt3 - 1) + paramInt5 * paramInt2;
/*      */ 
/*      */     
/*  264 */     switch (paramInt1) {
/*      */       case 0:
/*  266 */         dataBufferByte = new DataBufferByte(i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  278 */         return createInterleavedRaster(dataBufferByte, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfint, paramPoint);case 1: dataBufferUShort = new DataBufferUShort(i); return createInterleavedRaster(dataBufferUShort, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfint, paramPoint);
/*      */     } 
/*      */     throw new IllegalArgumentException("Unsupported data type " + paramInt1);
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
/*      */   public static WritableRaster createBandedRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Point paramPoint) {
/*  312 */     if (paramInt4 < 1) {
/*  313 */       throw new ArrayIndexOutOfBoundsException("Number of bands (" + paramInt4 + ") must be greater than 0");
/*      */     }
/*      */ 
/*      */     
/*  317 */     int[] arrayOfInt1 = new int[paramInt4];
/*  318 */     int[] arrayOfInt2 = new int[paramInt4];
/*  319 */     for (byte b = 0; b < paramInt4; b++) {
/*  320 */       arrayOfInt1[b] = b;
/*  321 */       arrayOfInt2[b] = 0;
/*      */     } 
/*      */     
/*  324 */     return createBandedRaster(paramInt1, paramInt2, paramInt3, paramInt2, arrayOfInt1, arrayOfInt2, paramPoint);
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
/*      */   public static WritableRaster createBandedRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint1, int[] paramArrayOfint2, Point paramPoint) {
/*      */     DataBufferByte dataBufferByte;
/*      */     DataBufferUShort dataBufferUShort;
/*      */     DataBufferInt dataBufferInt;
/*  372 */     int i = paramArrayOfint2.length;
/*      */     
/*  374 */     if (paramArrayOfint1 == null) {
/*  375 */       throw new ArrayIndexOutOfBoundsException("Bank indices array is null");
/*      */     }
/*      */     
/*  378 */     if (paramArrayOfint2 == null) {
/*  379 */       throw new ArrayIndexOutOfBoundsException("Band offsets array is null");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  384 */     int j = paramArrayOfint1[0];
/*  385 */     int k = paramArrayOfint2[0]; int m;
/*  386 */     for (m = 1; m < i; m++) {
/*  387 */       if (paramArrayOfint1[m] > j) {
/*  388 */         j = paramArrayOfint1[m];
/*      */       }
/*  390 */       if (paramArrayOfint2[m] > k) {
/*  391 */         k = paramArrayOfint2[m];
/*      */       }
/*      */     } 
/*  394 */     m = j + 1;
/*  395 */     int n = k + paramInt4 * (paramInt3 - 1) + paramInt2;
/*      */ 
/*      */ 
/*      */     
/*  399 */     switch (paramInt1) {
/*      */       case 0:
/*  401 */         dataBufferByte = new DataBufferByte(n, m);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  417 */         return createBandedRaster(dataBufferByte, paramInt2, paramInt3, paramInt4, paramArrayOfint1, paramArrayOfint2, paramPoint);case 1: dataBufferUShort = new DataBufferUShort(n, m); return createBandedRaster(dataBufferUShort, paramInt2, paramInt3, paramInt4, paramArrayOfint1, paramArrayOfint2, paramPoint);case 3: dataBufferInt = new DataBufferInt(n, m); return createBandedRaster(dataBufferInt, paramInt2, paramInt3, paramInt4, paramArrayOfint1, paramArrayOfint2, paramPoint);
/*      */     } 
/*      */     throw new IllegalArgumentException("Unsupported data type " + paramInt1);
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
/*      */   public static WritableRaster createPackedRaster(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint, Point paramPoint) {
/*      */     DataBufferByte dataBufferByte;
/*      */     DataBufferUShort dataBufferUShort;
/*      */     DataBufferInt dataBufferInt;
/*  457 */     switch (paramInt1) {
/*      */       case 0:
/*  459 */         dataBufferByte = new DataBufferByte(paramInt2 * paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  475 */         return createPackedRaster(dataBufferByte, paramInt2, paramInt3, paramInt2, paramArrayOfint, paramPoint);case 1: dataBufferUShort = new DataBufferUShort(paramInt2 * paramInt3); return createPackedRaster(dataBufferUShort, paramInt2, paramInt3, paramInt2, paramArrayOfint, paramPoint);case 3: dataBufferInt = new DataBufferInt(paramInt2 * paramInt3); return createPackedRaster(dataBufferInt, paramInt2, paramInt3, paramInt2, paramArrayOfint, paramPoint);
/*      */     } 
/*      */     throw new IllegalArgumentException("Unsupported data type " + paramInt1);
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
/*      */   public static WritableRaster createPackedRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Point paramPoint) {
/*      */     DataBufferByte dataBufferByte;
/*      */     DataBufferUShort dataBufferUShort;
/*      */     DataBufferInt dataBufferInt;
/*  529 */     if (paramInt4 <= 0) {
/*  530 */       throw new IllegalArgumentException("Number of bands (" + paramInt4 + ") must be greater than 0");
/*      */     }
/*      */ 
/*      */     
/*  534 */     if (paramInt5 <= 0) {
/*  535 */       throw new IllegalArgumentException("Bits per band (" + paramInt5 + ") must be greater than 0");
/*      */     }
/*      */ 
/*      */     
/*  539 */     if (paramInt4 != 1) {
/*  540 */       int[] arrayOfInt = new int[paramInt4];
/*  541 */       int i = (1 << paramInt5) - 1;
/*  542 */       int j = (paramInt4 - 1) * paramInt5;
/*      */ 
/*      */       
/*  545 */       if (j + paramInt5 > DataBuffer.getDataTypeSize(paramInt1)) {
/*  546 */         throw new IllegalArgumentException("bitsPerBand(" + paramInt5 + ") * bands is  greater than data type size.");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  551 */       switch (paramInt1) {
/*      */         case 0:
/*      */         case 1:
/*      */         case 3:
/*      */           break;
/*      */         default:
/*  557 */           throw new IllegalArgumentException("Unsupported data type " + paramInt1);
/*      */       } 
/*      */ 
/*      */       
/*  561 */       for (byte b = 0; b < paramInt4; b++) {
/*  562 */         arrayOfInt[b] = i << j;
/*  563 */         j -= paramInt5;
/*      */       } 
/*      */       
/*  566 */       return createPackedRaster(paramInt1, paramInt2, paramInt3, arrayOfInt, paramPoint);
/*      */     } 
/*      */     
/*  569 */     double d = paramInt2;
/*  570 */     switch (paramInt1) {
/*      */       case 0:
/*  572 */         dataBufferByte = new DataBufferByte((int)Math.ceil(d / (8 / paramInt5)) * paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  588 */         return createPackedRaster(dataBufferByte, paramInt2, paramInt3, paramInt5, paramPoint);case 1: dataBufferUShort = new DataBufferUShort((int)Math.ceil(d / (16 / paramInt5)) * paramInt3); return createPackedRaster(dataBufferUShort, paramInt2, paramInt3, paramInt5, paramPoint);case 3: dataBufferInt = new DataBufferInt((int)Math.ceil(d / (32 / paramInt5)) * paramInt3); return createPackedRaster(dataBufferInt, paramInt2, paramInt3, paramInt5, paramPoint);
/*      */     } 
/*      */     throw new IllegalArgumentException("Unsupported data type " + paramInt1);
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
/*      */   public static WritableRaster createInterleavedRaster(DataBuffer paramDataBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, Point paramPoint) {
/*  633 */     if (paramDataBuffer == null) {
/*  634 */       throw new NullPointerException("DataBuffer cannot be null");
/*      */     }
/*  636 */     if (paramPoint == null) {
/*  637 */       paramPoint = new Point(0, 0);
/*      */     }
/*  639 */     int i = paramDataBuffer.getDataType();
/*      */     
/*  641 */     PixelInterleavedSampleModel pixelInterleavedSampleModel = new PixelInterleavedSampleModel(i, paramInt1, paramInt2, paramInt4, paramInt3, paramArrayOfint);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  646 */     switch (i) {
/*      */       case 0:
/*  648 */         return new ByteInterleavedRaster(pixelInterleavedSampleModel, paramDataBuffer, paramPoint);
/*      */       
/*      */       case 1:
/*  651 */         return new ShortInterleavedRaster(pixelInterleavedSampleModel, paramDataBuffer, paramPoint);
/*      */     } 
/*      */     
/*  654 */     throw new IllegalArgumentException("Unsupported data type " + i);
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
/*      */   public static WritableRaster createBandedRaster(DataBuffer paramDataBuffer, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint1, int[] paramArrayOfint2, Point paramPoint) {
/*  695 */     if (paramDataBuffer == null) {
/*  696 */       throw new NullPointerException("DataBuffer cannot be null");
/*      */     }
/*  698 */     if (paramPoint == null) {
/*  699 */       paramPoint = new Point(0, 0);
/*      */     }
/*  701 */     int i = paramDataBuffer.getDataType();
/*      */     
/*  703 */     int j = paramArrayOfint1.length;
/*  704 */     if (paramArrayOfint2.length != j) {
/*  705 */       throw new IllegalArgumentException("bankIndices.length != bandOffsets.length");
/*      */     }
/*      */ 
/*      */     
/*  709 */     BandedSampleModel bandedSampleModel = new BandedSampleModel(i, paramInt1, paramInt2, paramInt3, paramArrayOfint1, paramArrayOfint2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  714 */     switch (i) {
/*      */       case 0:
/*  716 */         return new ByteBandedRaster(bandedSampleModel, paramDataBuffer, paramPoint);
/*      */       
/*      */       case 1:
/*  719 */         return new ShortBandedRaster(bandedSampleModel, paramDataBuffer, paramPoint);
/*      */       
/*      */       case 3:
/*  722 */         return new SunWritableRaster(bandedSampleModel, paramDataBuffer, paramPoint);
/*      */     } 
/*      */     
/*  725 */     throw new IllegalArgumentException("Unsupported data type " + i);
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
/*      */   public static WritableRaster createPackedRaster(DataBuffer paramDataBuffer, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint, Point paramPoint) {
/*  765 */     if (paramDataBuffer == null) {
/*  766 */       throw new NullPointerException("DataBuffer cannot be null");
/*      */     }
/*  768 */     if (paramPoint == null) {
/*  769 */       paramPoint = new Point(0, 0);
/*      */     }
/*  771 */     int i = paramDataBuffer.getDataType();
/*      */     
/*  773 */     SinglePixelPackedSampleModel singlePixelPackedSampleModel = new SinglePixelPackedSampleModel(i, paramInt1, paramInt2, paramInt3, paramArrayOfint);
/*      */ 
/*      */ 
/*      */     
/*  777 */     switch (i) {
/*      */       case 0:
/*  779 */         return new ByteInterleavedRaster(singlePixelPackedSampleModel, paramDataBuffer, paramPoint);
/*      */       
/*      */       case 1:
/*  782 */         return new ShortInterleavedRaster(singlePixelPackedSampleModel, paramDataBuffer, paramPoint);
/*      */       
/*      */       case 3:
/*  785 */         return new IntegerInterleavedRaster(singlePixelPackedSampleModel, paramDataBuffer, paramPoint);
/*      */     } 
/*      */     
/*  788 */     throw new IllegalArgumentException("Unsupported data type " + i);
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
/*      */   public static WritableRaster createPackedRaster(DataBuffer paramDataBuffer, int paramInt1, int paramInt2, int paramInt3, Point paramPoint) {
/*  825 */     if (paramDataBuffer == null) {
/*  826 */       throw new NullPointerException("DataBuffer cannot be null");
/*      */     }
/*  828 */     if (paramPoint == null) {
/*  829 */       paramPoint = new Point(0, 0);
/*      */     }
/*  831 */     int i = paramDataBuffer.getDataType();
/*      */     
/*  833 */     if (i != 0 && i != 1 && i != 3)
/*      */     {
/*      */       
/*  836 */       throw new IllegalArgumentException("Unsupported data type " + i);
/*      */     }
/*      */ 
/*      */     
/*  840 */     if (paramDataBuffer.getNumBanks() != 1) {
/*  841 */       throw new RasterFormatException("DataBuffer for packed Rasters must only have 1 bank.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  846 */     MultiPixelPackedSampleModel multiPixelPackedSampleModel = new MultiPixelPackedSampleModel(i, paramInt1, paramInt2, paramInt3);
/*      */ 
/*      */     
/*  849 */     if (i == 0 && (paramInt3 == 1 || paramInt3 == 2 || paramInt3 == 4))
/*      */     {
/*  851 */       return new BytePackedRaster(multiPixelPackedSampleModel, paramDataBuffer, paramPoint);
/*      */     }
/*  853 */     return new SunWritableRaster(multiPixelPackedSampleModel, paramDataBuffer, paramPoint);
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
/*      */   public static Raster createRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/*  882 */     if (paramSampleModel == null || paramDataBuffer == null) {
/*  883 */       throw new NullPointerException("SampleModel and DataBuffer cannot be null");
/*      */     }
/*      */     
/*  886 */     if (paramPoint == null) {
/*  887 */       paramPoint = new Point(0, 0);
/*      */     }
/*  889 */     int i = paramSampleModel.getDataType();
/*      */     
/*  891 */     if (paramSampleModel instanceof PixelInterleavedSampleModel) {
/*  892 */       switch (i) {
/*      */         case 0:
/*  894 */           return new ByteInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */         
/*      */         case 1:
/*  897 */           return new ShortInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */       } 
/*  899 */     } else if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/*  900 */       switch (i) {
/*      */         case 0:
/*  902 */           return new ByteInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */         
/*      */         case 1:
/*  905 */           return new ShortInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */         
/*      */         case 3:
/*  908 */           return new IntegerInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */       } 
/*  910 */     } else if (paramSampleModel instanceof MultiPixelPackedSampleModel && i == 0 && paramSampleModel
/*      */       
/*  912 */       .getSampleSize(0) < 8) {
/*  913 */       return new BytePackedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  918 */     return new Raster(paramSampleModel, paramDataBuffer, paramPoint);
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
/*      */   public static WritableRaster createWritableRaster(SampleModel paramSampleModel, Point paramPoint) {
/*  937 */     if (paramPoint == null) {
/*  938 */       paramPoint = new Point(0, 0);
/*      */     }
/*      */     
/*  941 */     return createWritableRaster(paramSampleModel, paramSampleModel.createDataBuffer(), paramPoint);
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
/*      */   public static WritableRaster createWritableRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/*  968 */     if (paramSampleModel == null || paramDataBuffer == null) {
/*  969 */       throw new NullPointerException("SampleModel and DataBuffer cannot be null");
/*      */     }
/*  971 */     if (paramPoint == null) {
/*  972 */       paramPoint = new Point(0, 0);
/*      */     }
/*      */     
/*  975 */     int i = paramSampleModel.getDataType();
/*      */     
/*  977 */     if (paramSampleModel instanceof PixelInterleavedSampleModel) {
/*  978 */       switch (i) {
/*      */         case 0:
/*  980 */           return new ByteInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */         
/*      */         case 1:
/*  983 */           return new ShortInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */       } 
/*  985 */     } else if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/*  986 */       switch (i) {
/*      */         case 0:
/*  988 */           return new ByteInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */         
/*      */         case 1:
/*  991 */           return new ShortInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */         
/*      */         case 3:
/*  994 */           return new IntegerInterleavedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */       } 
/*  996 */     } else if (paramSampleModel instanceof MultiPixelPackedSampleModel && i == 0 && paramSampleModel
/*      */       
/*  998 */       .getSampleSize(0) < 8) {
/*  999 */       return new BytePackedRaster(paramSampleModel, paramDataBuffer, paramPoint);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1004 */     return new SunWritableRaster(paramSampleModel, paramDataBuffer, paramPoint);
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
/*      */   protected Raster(SampleModel paramSampleModel, Point paramPoint) {
/* 1023 */     this(paramSampleModel, paramSampleModel
/* 1024 */         .createDataBuffer(), new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*      */ 
/*      */           
/* 1027 */           .getWidth(), paramSampleModel
/* 1028 */           .getHeight()), paramPoint, null);
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
/*      */   protected Raster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/* 1051 */     this(paramSampleModel, paramDataBuffer, new Rectangle(paramPoint.x, paramPoint.y, paramSampleModel
/*      */ 
/*      */ 
/*      */           
/* 1055 */           .getWidth(), paramSampleModel
/* 1056 */           .getHeight()), paramPoint, null);
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
/*      */   protected Raster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, Raster paramRaster) {
/* 1093 */     if (paramSampleModel == null || paramDataBuffer == null || paramRectangle == null || paramPoint == null)
/*      */     {
/* 1095 */       throw new NullPointerException("SampleModel, dataBuffer, aRegion and sampleModelTranslate cannot be null");
/*      */     }
/*      */     
/* 1098 */     this.sampleModel = paramSampleModel;
/* 1099 */     this.dataBuffer = paramDataBuffer;
/* 1100 */     this.minX = paramRectangle.x;
/* 1101 */     this.minY = paramRectangle.y;
/* 1102 */     this.width = paramRectangle.width;
/* 1103 */     this.height = paramRectangle.height;
/* 1104 */     if (this.width <= 0 || this.height <= 0) {
/* 1105 */       throw new RasterFormatException("negative or zero " + ((this.width <= 0) ? "width" : "height"));
/*      */     }
/*      */     
/* 1108 */     if (this.minX + this.width < this.minX) {
/* 1109 */       throw new RasterFormatException("overflow condition for X coordinates of Raster");
/*      */     }
/*      */     
/* 1112 */     if (this.minY + this.height < this.minY) {
/* 1113 */       throw new RasterFormatException("overflow condition for Y coordinates of Raster");
/*      */     }
/*      */ 
/*      */     
/* 1117 */     this.sampleModelTranslateX = paramPoint.x;
/* 1118 */     this.sampleModelTranslateY = paramPoint.y;
/*      */     
/* 1120 */     this.numBands = paramSampleModel.getNumBands();
/* 1121 */     this.numDataElements = paramSampleModel.getNumDataElements();
/* 1122 */     this.parent = paramRaster;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Raster getParent() {
/* 1131 */     return this.parent;
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
/*      */   public final int getSampleModelTranslateX() {
/* 1143 */     return this.sampleModelTranslateX;
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
/*      */   public final int getSampleModelTranslateY() {
/* 1155 */     return this.sampleModelTranslateY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster createCompatibleWritableRaster() {
/* 1165 */     return new SunWritableRaster(this.sampleModel, new Point(0, 0));
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
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 1179 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 1180 */       throw new RasterFormatException("negative " + ((paramInt1 <= 0) ? "width" : "height"));
/*      */     }
/*      */ 
/*      */     
/* 1184 */     SampleModel sampleModel = this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*      */     
/* 1186 */     return new SunWritableRaster(sampleModel, new Point(0, 0));
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
/*      */   public WritableRaster createCompatibleWritableRaster(Rectangle paramRectangle) {
/* 1205 */     if (paramRectangle == null) {
/* 1206 */       throw new NullPointerException("Rect cannot be null");
/*      */     }
/* 1208 */     return createCompatibleWritableRaster(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
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
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1232 */     WritableRaster writableRaster = createCompatibleWritableRaster(paramInt3, paramInt4);
/* 1233 */     return writableRaster.createWritableChild(0, 0, paramInt3, paramInt4, paramInt1, paramInt2, null);
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
/*      */   public Raster createTranslatedChild(int paramInt1, int paramInt2) {
/* 1255 */     return createChild(this.minX, this.minY, this.width, this.height, paramInt1, paramInt2, null);
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
/*      */   public Raster createChild(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/*      */     SampleModel sampleModel;
/* 1315 */     if (paramInt1 < this.minX) {
/* 1316 */       throw new RasterFormatException("parentX lies outside raster");
/*      */     }
/* 1318 */     if (paramInt2 < this.minY) {
/* 1319 */       throw new RasterFormatException("parentY lies outside raster");
/*      */     }
/* 1321 */     if (paramInt1 + paramInt3 < paramInt1 || paramInt1 + paramInt3 > this.width + this.minX)
/*      */     {
/* 1323 */       throw new RasterFormatException("(parentX + width) is outside raster");
/*      */     }
/* 1325 */     if (paramInt2 + paramInt4 < paramInt2 || paramInt2 + paramInt4 > this.height + this.minY)
/*      */     {
/* 1327 */       throw new RasterFormatException("(parentY + height) is outside raster");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1336 */     if (paramArrayOfint == null) {
/* 1337 */       sampleModel = this.sampleModel;
/*      */     } else {
/* 1339 */       sampleModel = this.sampleModel.createSubsetSampleModel(paramArrayOfint);
/*      */     } 
/*      */     
/* 1342 */     int i = paramInt5 - paramInt1;
/* 1343 */     int j = paramInt6 - paramInt2;
/*      */     
/* 1345 */     return new Raster(sampleModel, getDataBuffer(), new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4), new Point(this.sampleModelTranslateX + i, this.sampleModelTranslateY + j), this);
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
/*      */   public Rectangle getBounds() {
/* 1357 */     return new Rectangle(this.minX, this.minY, this.width, this.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getMinX() {
/* 1364 */     return this.minX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getMinY() {
/* 1371 */     return this.minY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getWidth() {
/* 1378 */     return this.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getHeight() {
/* 1385 */     return this.height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumBands() {
/* 1392 */     return this.numBands;
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
/*      */   public final int getNumDataElements() {
/* 1407 */     return this.sampleModel.getNumDataElements();
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
/*      */   public final int getTransferType() {
/* 1423 */     return this.sampleModel.getTransferType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataBuffer getDataBuffer() {
/* 1430 */     return this.dataBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SampleModel getSampleModel() {
/* 1437 */     return this.sampleModel;
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
/*      */   public Object getDataElements(int paramInt1, int paramInt2, Object paramObject) {
/* 1466 */     return this.sampleModel.getDataElements(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramObject, this.dataBuffer);
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
/*      */   public Object getDataElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject) {
/* 1500 */     return this.sampleModel.getDataElements(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramObject, this.dataBuffer);
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
/*      */   public int[] getPixel(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 1519 */     return this.sampleModel.getPixel(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramArrayOfint, this.dataBuffer);
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
/*      */   public float[] getPixel(int paramInt1, int paramInt2, float[] paramArrayOffloat) {
/* 1539 */     return this.sampleModel.getPixel(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramArrayOffloat, this.dataBuffer);
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
/*      */   public double[] getPixel(int paramInt1, int paramInt2, double[] paramArrayOfdouble) {
/* 1558 */     return this.sampleModel.getPixel(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramArrayOfdouble, this.dataBuffer);
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
/*      */   public int[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 1580 */     return this.sampleModel.getPixels(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramArrayOfint, this.dataBuffer);
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
/*      */   public float[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat) {
/* 1603 */     return this.sampleModel.getPixels(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramArrayOffloat, this.dataBuffer);
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
/*      */   public double[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble) {
/* 1626 */     return this.sampleModel.getPixels(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramArrayOfdouble, this.dataBuffer);
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
/*      */   public int getSample(int paramInt1, int paramInt2, int paramInt3) {
/* 1648 */     return this.sampleModel.getSample(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, this.dataBuffer);
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
/*      */   public float getSampleFloat(int paramInt1, int paramInt2, int paramInt3) {
/* 1669 */     return this.sampleModel.getSampleFloat(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, this.dataBuffer);
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
/*      */   public double getSampleDouble(int paramInt1, int paramInt2, int paramInt3) {
/* 1690 */     return this.sampleModel.getSampleDouble(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, this.dataBuffer);
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
/*      */   public int[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
/* 1716 */     return this.sampleModel.getSamples(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramInt5, paramArrayOfint, this.dataBuffer);
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
/*      */   public float[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[] paramArrayOffloat) {
/* 1743 */     return this.sampleModel.getSamples(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramInt5, paramArrayOffloat, this.dataBuffer);
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
/*      */   public double[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double[] paramArrayOfdouble) {
/* 1769 */     return this.sampleModel.getSamples(paramInt1 - this.sampleModelTranslateX, paramInt2 - this.sampleModelTranslateY, paramInt3, paramInt4, paramInt5, paramArrayOfdouble, this.dataBuffer);
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/Raster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */