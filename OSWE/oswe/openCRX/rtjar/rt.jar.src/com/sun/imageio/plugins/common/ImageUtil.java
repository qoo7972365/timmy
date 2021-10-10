/*      */ package com.sun.imageio.plugins.common;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ComponentColorModel;
/*      */ import java.awt.image.ComponentSampleModel;
/*      */ import java.awt.image.DataBuffer;
/*      */ import java.awt.image.DataBufferByte;
/*      */ import java.awt.image.DataBufferInt;
/*      */ import java.awt.image.DataBufferShort;
/*      */ import java.awt.image.DataBufferUShort;
/*      */ import java.awt.image.DirectColorModel;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.MultiPixelPackedSampleModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.SinglePixelPackedSampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import javax.imageio.IIOException;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.spi.ImageWriterSpi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ImageUtil
/*      */ {
/*      */   public static final ColorModel createColorModel(SampleModel paramSampleModel) {
/*      */     IndexColorModel indexColorModel;
/*  141 */     if (paramSampleModel == null) {
/*  142 */       throw new IllegalArgumentException("sampleModel == null!");
/*      */     }
/*      */ 
/*      */     
/*  146 */     int i = paramSampleModel.getDataType();
/*      */ 
/*      */     
/*  149 */     switch (i) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */         break;
/*      */       
/*      */       default:
/*  159 */         return null;
/*      */     } 
/*      */ 
/*      */     
/*  163 */     ComponentColorModel componentColorModel = null;
/*      */ 
/*      */     
/*  166 */     int[] arrayOfInt = paramSampleModel.getSampleSize();
/*      */ 
/*      */     
/*  169 */     if (paramSampleModel instanceof ComponentSampleModel) {
/*      */       
/*  171 */       int j = paramSampleModel.getNumBands();
/*      */ 
/*      */       
/*  174 */       ColorSpace colorSpace = null;
/*  175 */       if (j <= 2) {
/*  176 */         colorSpace = ColorSpace.getInstance(1003);
/*  177 */       } else if (j <= 4) {
/*  178 */         colorSpace = ColorSpace.getInstance(1000);
/*      */       } else {
/*  180 */         colorSpace = new BogusColorSpace(j);
/*      */       } 
/*      */       
/*  183 */       boolean bool1 = (j == 2 || j == 4) ? true : false;
/*  184 */       boolean bool2 = false;
/*  185 */       boolean bool3 = bool1 ? true : true;
/*      */ 
/*      */       
/*  188 */       componentColorModel = new ComponentColorModel(colorSpace, arrayOfInt, bool1, bool2, bool3, i);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  194 */       if (paramSampleModel.getNumBands() <= 4 && paramSampleModel instanceof SinglePixelPackedSampleModel) {
/*      */         
/*  196 */         SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*      */ 
/*      */         
/*  199 */         int[] arrayOfInt1 = singlePixelPackedSampleModel.getBitMasks();
/*  200 */         int j = 0;
/*  201 */         int k = 0;
/*  202 */         int m = 0;
/*  203 */         int n = 0;
/*      */         
/*  205 */         int i1 = arrayOfInt1.length;
/*  206 */         if (i1 <= 2) {
/*  207 */           j = k = m = arrayOfInt1[0];
/*  208 */           if (i1 == 2) {
/*  209 */             n = arrayOfInt1[1];
/*      */           }
/*      */         } else {
/*  212 */           j = arrayOfInt1[0];
/*  213 */           k = arrayOfInt1[1];
/*  214 */           m = arrayOfInt1[2];
/*  215 */           if (i1 == 4) {
/*  216 */             n = arrayOfInt1[3];
/*      */           }
/*      */         } 
/*      */         
/*  220 */         int i2 = 0;
/*  221 */         for (byte b = 0; b < arrayOfInt.length; b++) {
/*  222 */           i2 += arrayOfInt[b];
/*      */         }
/*      */         
/*  225 */         return new DirectColorModel(i2, j, k, m, n);
/*      */       } 
/*  227 */       if (paramSampleModel instanceof MultiPixelPackedSampleModel) {
/*      */         
/*  229 */         int j = arrayOfInt[0];
/*  230 */         int k = 1 << j;
/*  231 */         byte[] arrayOfByte = new byte[k];
/*  232 */         for (byte b = 0; b < k; b++) {
/*  233 */           arrayOfByte[b] = (byte)(b * 255 / (k - 1));
/*      */         }
/*      */         
/*  236 */         indexColorModel = new IndexColorModel(j, k, arrayOfByte, arrayOfByte, arrayOfByte);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  241 */     return indexColorModel;
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
/*      */   public static byte[] getPackedBinaryData(Raster paramRaster, Rectangle paramRectangle) {
/*  261 */     SampleModel sampleModel = paramRaster.getSampleModel();
/*  262 */     if (!isBinary(sampleModel)) {
/*  263 */       throw new IllegalArgumentException(I18N.getString("ImageUtil0"));
/*      */     }
/*      */     
/*  266 */     int i = paramRectangle.x;
/*  267 */     int j = paramRectangle.y;
/*  268 */     int k = paramRectangle.width;
/*  269 */     int m = paramRectangle.height;
/*      */     
/*  271 */     DataBuffer dataBuffer = paramRaster.getDataBuffer();
/*      */     
/*  273 */     int n = i - paramRaster.getSampleModelTranslateX();
/*  274 */     int i1 = j - paramRaster.getSampleModelTranslateY();
/*      */     
/*  276 */     MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)sampleModel;
/*  277 */     int i2 = multiPixelPackedSampleModel.getScanlineStride();
/*  278 */     int i3 = dataBuffer.getOffset() + multiPixelPackedSampleModel.getOffset(n, i1);
/*  279 */     int i4 = multiPixelPackedSampleModel.getBitOffset(n);
/*      */     
/*  281 */     int i5 = (k + 7) / 8;
/*  282 */     if (dataBuffer instanceof DataBufferByte && i3 == 0 && i4 == 0 && i5 == i2 && (((DataBufferByte)dataBuffer)
/*      */ 
/*      */       
/*  285 */       .getData()).length == i5 * m)
/*      */     {
/*  287 */       return ((DataBufferByte)dataBuffer).getData();
/*      */     }
/*      */     
/*  290 */     byte[] arrayOfByte = new byte[i5 * m];
/*      */     
/*  292 */     byte b = 0;
/*      */     
/*  294 */     if (i4 == 0) {
/*  295 */       if (dataBuffer instanceof DataBufferByte) {
/*  296 */         byte[] arrayOfByte1 = ((DataBufferByte)dataBuffer).getData();
/*  297 */         int i6 = i5;
/*  298 */         int i7 = 0;
/*  299 */         for (byte b1 = 0; b1 < m; b1++) {
/*  300 */           System.arraycopy(arrayOfByte1, i3, arrayOfByte, i7, i6);
/*      */ 
/*      */           
/*  303 */           i7 += i6;
/*  304 */           i3 += i2;
/*      */         } 
/*  306 */       } else if (dataBuffer instanceof DataBufferShort || dataBuffer instanceof DataBufferUShort) {
/*      */ 
/*      */ 
/*      */         
/*  310 */         short[] arrayOfShort = (dataBuffer instanceof DataBufferShort) ? ((DataBufferShort)dataBuffer).getData() : ((DataBufferUShort)dataBuffer).getData();
/*      */         
/*  312 */         for (byte b1 = 0; b1 < m; b1++) {
/*  313 */           int i6 = k;
/*  314 */           int i7 = i3;
/*  315 */           while (i6 > 8) {
/*  316 */             short s = arrayOfShort[i7++];
/*  317 */             arrayOfByte[b++] = (byte)(s >>> 8 & 0xFF);
/*  318 */             arrayOfByte[b++] = (byte)(s & 0xFF);
/*  319 */             i6 -= 16;
/*      */           } 
/*  321 */           if (i6 > 0) {
/*  322 */             arrayOfByte[b++] = (byte)(arrayOfShort[i7] >>> 8 & 0xFF);
/*      */           }
/*  324 */           i3 += i2;
/*      */         } 
/*  326 */       } else if (dataBuffer instanceof DataBufferInt) {
/*  327 */         int[] arrayOfInt = ((DataBufferInt)dataBuffer).getData();
/*      */         
/*  329 */         for (byte b1 = 0; b1 < m; b1++) {
/*  330 */           int i6 = k;
/*  331 */           int i7 = i3;
/*  332 */           while (i6 > 24) {
/*  333 */             int i8 = arrayOfInt[i7++];
/*  334 */             arrayOfByte[b++] = (byte)(i8 >>> 24 & 0xFF);
/*  335 */             arrayOfByte[b++] = (byte)(i8 >>> 16 & 0xFF);
/*  336 */             arrayOfByte[b++] = (byte)(i8 >>> 8 & 0xFF);
/*  337 */             arrayOfByte[b++] = (byte)(i8 & 0xFF);
/*  338 */             i6 -= 32;
/*      */           } 
/*  340 */           byte b2 = 24;
/*  341 */           while (i6 > 0) {
/*  342 */             arrayOfByte[b++] = (byte)(arrayOfInt[i7] >>> b2 & 0xFF);
/*      */             
/*  344 */             b2 -= 8;
/*  345 */             i6 -= 8;
/*      */           } 
/*  347 */           i3 += i2;
/*      */         }
/*      */       
/*      */       } 
/*  351 */     } else if (dataBuffer instanceof DataBufferByte) {
/*  352 */       byte[] arrayOfByte1 = ((DataBufferByte)dataBuffer).getData();
/*      */       
/*  354 */       if ((i4 & 0x7) == 0) {
/*  355 */         int i6 = i5;
/*  356 */         int i7 = 0;
/*  357 */         for (byte b1 = 0; b1 < m; b1++) {
/*  358 */           System.arraycopy(arrayOfByte1, i3, arrayOfByte, i7, i6);
/*      */ 
/*      */           
/*  361 */           i7 += i6;
/*  362 */           i3 += i2;
/*      */         } 
/*      */       } else {
/*  365 */         int i6 = i4 & 0x7;
/*  366 */         int i7 = 8 - i6;
/*  367 */         for (byte b1 = 0; b1 < m; b1++) {
/*  368 */           int i8 = i3;
/*  369 */           int i9 = k;
/*  370 */           while (i9 > 0) {
/*  371 */             if (i9 > i7) {
/*  372 */               arrayOfByte[b++] = (byte)((arrayOfByte1[i8++] & 0xFF) << i6 | (arrayOfByte1[i8] & 0xFF) >>> i7);
/*      */             }
/*      */             else {
/*      */               
/*  376 */               arrayOfByte[b++] = (byte)((arrayOfByte1[i8] & 0xFF) << i6);
/*      */             } 
/*      */             
/*  379 */             i9 -= 8;
/*      */           } 
/*  381 */           i3 += i2;
/*      */         } 
/*      */       } 
/*  384 */     } else if (dataBuffer instanceof DataBufferShort || dataBuffer instanceof DataBufferUShort) {
/*      */ 
/*      */ 
/*      */       
/*  388 */       short[] arrayOfShort = (dataBuffer instanceof DataBufferShort) ? ((DataBufferShort)dataBuffer).getData() : ((DataBufferUShort)dataBuffer).getData();
/*      */       
/*  390 */       for (byte b1 = 0; b1 < m; b1++) {
/*  391 */         int i6 = i4;
/*  392 */         for (byte b2 = 0; b2 < k; b2 += 8, i6 += 8) {
/*  393 */           int i7 = i3 + i6 / 16;
/*  394 */           int i8 = i6 % 16;
/*  395 */           int i9 = arrayOfShort[i7] & 0xFFFF;
/*  396 */           if (i8 <= 8) {
/*  397 */             arrayOfByte[b++] = (byte)(i9 >>> 8 - i8);
/*      */           } else {
/*  399 */             int i10 = i8 - 8;
/*  400 */             int i11 = arrayOfShort[i7 + 1] & 0xFFFF;
/*  401 */             arrayOfByte[b++] = (byte)(i9 << i10 | i11 >>> 16 - i10);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  406 */         i3 += i2;
/*      */       } 
/*  408 */     } else if (dataBuffer instanceof DataBufferInt) {
/*  409 */       int[] arrayOfInt = ((DataBufferInt)dataBuffer).getData();
/*      */       
/*  411 */       for (byte b1 = 0; b1 < m; b1++) {
/*  412 */         int i6 = i4;
/*  413 */         for (byte b2 = 0; b2 < k; b2 += 8, i6 += 8) {
/*  414 */           int i7 = i3 + i6 / 32;
/*  415 */           int i8 = i6 % 32;
/*  416 */           int i9 = arrayOfInt[i7];
/*  417 */           if (i8 <= 24) {
/*  418 */             arrayOfByte[b++] = (byte)(i9 >>> 24 - i8);
/*      */           } else {
/*      */             
/*  421 */             int i10 = i8 - 24;
/*  422 */             int i11 = arrayOfInt[i7 + 1];
/*  423 */             arrayOfByte[b++] = (byte)(i9 << i10 | i11 >>> 32 - i10);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  428 */         i3 += i2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  433 */     return arrayOfByte;
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
/*      */   public static byte[] getUnpackedBinaryData(Raster paramRaster, Rectangle paramRectangle) {
/*  446 */     SampleModel sampleModel = paramRaster.getSampleModel();
/*  447 */     if (!isBinary(sampleModel)) {
/*  448 */       throw new IllegalArgumentException(I18N.getString("ImageUtil0"));
/*      */     }
/*      */     
/*  451 */     int i = paramRectangle.x;
/*  452 */     int j = paramRectangle.y;
/*  453 */     int k = paramRectangle.width;
/*  454 */     int m = paramRectangle.height;
/*      */     
/*  456 */     DataBuffer dataBuffer = paramRaster.getDataBuffer();
/*      */     
/*  458 */     int n = i - paramRaster.getSampleModelTranslateX();
/*  459 */     int i1 = j - paramRaster.getSampleModelTranslateY();
/*      */     
/*  461 */     MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)sampleModel;
/*  462 */     int i2 = multiPixelPackedSampleModel.getScanlineStride();
/*  463 */     int i3 = dataBuffer.getOffset() + multiPixelPackedSampleModel.getOffset(n, i1);
/*  464 */     int i4 = multiPixelPackedSampleModel.getBitOffset(n);
/*      */     
/*  466 */     byte[] arrayOfByte = new byte[k * m];
/*  467 */     int i5 = j + m;
/*  468 */     int i6 = i + k;
/*  469 */     byte b = 0;
/*      */     
/*  471 */     if (dataBuffer instanceof DataBufferByte) {
/*  472 */       byte[] arrayOfByte1 = ((DataBufferByte)dataBuffer).getData();
/*  473 */       for (int i7 = j; i7 < i5; i7++) {
/*  474 */         int i8 = i3 * 8 + i4;
/*  475 */         for (int i9 = i; i9 < i6; i9++) {
/*  476 */           byte b1 = arrayOfByte1[i8 / 8];
/*  477 */           arrayOfByte[b++] = (byte)(b1 >>> (7 - i8 & 0x7) & 0x1);
/*      */           
/*  479 */           i8++;
/*      */         } 
/*  481 */         i3 += i2;
/*      */       } 
/*  483 */     } else if (dataBuffer instanceof DataBufferShort || dataBuffer instanceof DataBufferUShort) {
/*      */ 
/*      */ 
/*      */       
/*  487 */       short[] arrayOfShort = (dataBuffer instanceof DataBufferShort) ? ((DataBufferShort)dataBuffer).getData() : ((DataBufferUShort)dataBuffer).getData();
/*  488 */       for (int i7 = j; i7 < i5; i7++) {
/*  489 */         int i8 = i3 * 16 + i4;
/*  490 */         for (int i9 = i; i9 < i6; i9++) {
/*  491 */           short s = arrayOfShort[i8 / 16];
/*  492 */           arrayOfByte[b++] = (byte)(s >>> 15 - i8 % 16 & 0x1);
/*      */ 
/*      */           
/*  495 */           i8++;
/*      */         } 
/*  497 */         i3 += i2;
/*      */       } 
/*  499 */     } else if (dataBuffer instanceof DataBufferInt) {
/*  500 */       int[] arrayOfInt = ((DataBufferInt)dataBuffer).getData();
/*  501 */       for (int i7 = j; i7 < i5; i7++) {
/*  502 */         int i8 = i3 * 32 + i4;
/*  503 */         for (int i9 = i; i9 < i6; i9++) {
/*  504 */           int i10 = arrayOfInt[i8 / 32];
/*  505 */           arrayOfByte[b++] = (byte)(i10 >>> 31 - i8 % 32 & 0x1);
/*      */ 
/*      */           
/*  508 */           i8++;
/*      */         } 
/*  510 */         i3 += i2;
/*      */       } 
/*      */     } 
/*      */     
/*  514 */     return arrayOfByte;
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
/*      */   public static void setPackedBinaryData(byte[] paramArrayOfbyte, WritableRaster paramWritableRaster, Rectangle paramRectangle) {
/*  529 */     SampleModel sampleModel = paramWritableRaster.getSampleModel();
/*  530 */     if (!isBinary(sampleModel)) {
/*  531 */       throw new IllegalArgumentException(I18N.getString("ImageUtil0"));
/*      */     }
/*      */     
/*  534 */     int i = paramRectangle.x;
/*  535 */     int j = paramRectangle.y;
/*  536 */     int k = paramRectangle.width;
/*  537 */     int m = paramRectangle.height;
/*      */     
/*  539 */     DataBuffer dataBuffer = paramWritableRaster.getDataBuffer();
/*      */     
/*  541 */     int n = i - paramWritableRaster.getSampleModelTranslateX();
/*  542 */     int i1 = j - paramWritableRaster.getSampleModelTranslateY();
/*      */     
/*  544 */     MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)sampleModel;
/*  545 */     int i2 = multiPixelPackedSampleModel.getScanlineStride();
/*  546 */     int i3 = dataBuffer.getOffset() + multiPixelPackedSampleModel.getOffset(n, i1);
/*  547 */     int i4 = multiPixelPackedSampleModel.getBitOffset(n);
/*      */     
/*  549 */     byte b = 0;
/*      */     
/*  551 */     if (i4 == 0) {
/*  552 */       if (dataBuffer instanceof DataBufferByte) {
/*  553 */         byte[] arrayOfByte = ((DataBufferByte)dataBuffer).getData();
/*  554 */         if (arrayOfByte == paramArrayOfbyte) {
/*      */           return;
/*      */         }
/*      */         
/*  558 */         int i5 = (k + 7) / 8;
/*  559 */         int i6 = 0;
/*  560 */         for (byte b1 = 0; b1 < m; b1++) {
/*  561 */           System.arraycopy(paramArrayOfbyte, i6, arrayOfByte, i3, i5);
/*      */ 
/*      */           
/*  564 */           i6 += i5;
/*  565 */           i3 += i2;
/*      */         } 
/*  567 */       } else if (dataBuffer instanceof DataBufferShort || dataBuffer instanceof DataBufferUShort) {
/*      */ 
/*      */ 
/*      */         
/*  571 */         short[] arrayOfShort = (dataBuffer instanceof DataBufferShort) ? ((DataBufferShort)dataBuffer).getData() : ((DataBufferUShort)dataBuffer).getData();
/*      */         
/*  573 */         for (byte b1 = 0; b1 < m; b1++) {
/*  574 */           int i5 = k;
/*  575 */           int i6 = i3;
/*  576 */           while (i5 > 8) {
/*  577 */             arrayOfShort[i6++] = (short)((paramArrayOfbyte[b++] & 0xFF) << 8 | paramArrayOfbyte[b++] & 0xFF);
/*      */ 
/*      */             
/*  580 */             i5 -= 16;
/*      */           } 
/*  582 */           if (i5 > 0) {
/*  583 */             arrayOfShort[i6++] = (short)((paramArrayOfbyte[b++] & 0xFF) << 8);
/*      */           }
/*      */           
/*  586 */           i3 += i2;
/*      */         } 
/*  588 */       } else if (dataBuffer instanceof DataBufferInt) {
/*  589 */         int[] arrayOfInt = ((DataBufferInt)dataBuffer).getData();
/*      */         
/*  591 */         for (byte b1 = 0; b1 < m; b1++) {
/*  592 */           int i5 = k;
/*  593 */           int i6 = i3;
/*  594 */           while (i5 > 24) {
/*  595 */             arrayOfInt[i6++] = (paramArrayOfbyte[b++] & 0xFF) << 24 | (paramArrayOfbyte[b++] & 0xFF) << 16 | (paramArrayOfbyte[b++] & 0xFF) << 8 | paramArrayOfbyte[b++] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  600 */             i5 -= 32;
/*      */           } 
/*  602 */           byte b2 = 24;
/*  603 */           while (i5 > 0) {
/*  604 */             arrayOfInt[i6] = arrayOfInt[i6] | (paramArrayOfbyte[b++] & 0xFF) << b2;
/*      */             
/*  606 */             b2 -= 8;
/*  607 */             i5 -= 8;
/*      */           } 
/*  609 */           i3 += i2;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  613 */       int i5 = (k + 7) / 8;
/*  614 */       int i6 = 0;
/*  615 */       if (dataBuffer instanceof DataBufferByte) {
/*  616 */         byte[] arrayOfByte = ((DataBufferByte)dataBuffer).getData();
/*      */         
/*  618 */         if ((i4 & 0x7) == 0) {
/*  619 */           for (byte b1 = 0; b1 < m; b1++) {
/*  620 */             System.arraycopy(paramArrayOfbyte, i6, arrayOfByte, i3, i5);
/*      */ 
/*      */             
/*  623 */             i6 += i5;
/*  624 */             i3 += i2;
/*      */           } 
/*      */         } else {
/*  627 */           int i7 = i4 & 0x7;
/*  628 */           int i8 = 8 - i7;
/*  629 */           int i9 = 8 + i8;
/*  630 */           byte b1 = (byte)(255 << i8);
/*  631 */           byte b2 = (byte)(b1 ^ 0xFFFFFFFF);
/*      */           
/*  633 */           for (byte b3 = 0; b3 < m; b3++) {
/*  634 */             int i10 = i3;
/*  635 */             int i11 = k;
/*  636 */             while (i11 > 0) {
/*  637 */               byte b4 = paramArrayOfbyte[b++];
/*      */               
/*  639 */               if (i11 > i9) {
/*      */ 
/*      */                 
/*  642 */                 arrayOfByte[i10] = (byte)(arrayOfByte[i10] & b1 | (b4 & 0xFF) >>> i7);
/*      */                 
/*  644 */                 arrayOfByte[++i10] = (byte)((b4 & 0xFF) << i8);
/*  645 */               } else if (i11 > i8) {
/*      */ 
/*      */ 
/*      */                 
/*  649 */                 arrayOfByte[i10] = (byte)(arrayOfByte[i10] & b1 | (b4 & 0xFF) >>> i7);
/*      */                 
/*  651 */                 i10++;
/*  652 */                 arrayOfByte[i10] = (byte)(arrayOfByte[i10] & b2 | (b4 & 0xFF) << i8);
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/*  657 */                 int i12 = (1 << i8 - i11) - 1;
/*  658 */                 arrayOfByte[i10] = (byte)(arrayOfByte[i10] & (b1 | i12) | (b4 & 0xFF) >>> i7 & (i12 ^ 0xFFFFFFFF));
/*      */               } 
/*      */ 
/*      */               
/*  662 */               i11 -= 8;
/*      */             } 
/*  664 */             i3 += i2;
/*      */           } 
/*      */         } 
/*  667 */       } else if (dataBuffer instanceof DataBufferShort || dataBuffer instanceof DataBufferUShort) {
/*      */ 
/*      */ 
/*      */         
/*  671 */         short[] arrayOfShort = (dataBuffer instanceof DataBufferShort) ? ((DataBufferShort)dataBuffer).getData() : ((DataBufferUShort)dataBuffer).getData();
/*      */         
/*  673 */         int i7 = i4 & 0x7;
/*  674 */         int i8 = 8 - i7;
/*  675 */         int i9 = 16 + i8;
/*  676 */         short s1 = (short)(255 << i8 ^ 0xFFFFFFFF);
/*  677 */         short s2 = (short)(65535 << i8);
/*  678 */         short s3 = (short)(s2 ^ 0xFFFFFFFF);
/*      */         
/*  680 */         for (byte b1 = 0; b1 < m; b1++) {
/*  681 */           int i10 = i4;
/*  682 */           int i11 = k;
/*  683 */           for (byte b2 = 0; b2 < k; 
/*  684 */             b2 += 8, i10 += 8, i11 -= 8) {
/*  685 */             int i12 = i3 + (i10 >> 4);
/*  686 */             int i13 = i10 & 0xF;
/*  687 */             int i14 = paramArrayOfbyte[b++] & 0xFF;
/*  688 */             if (i13 <= 8) {
/*      */               
/*  690 */               if (i11 < 8)
/*      */               {
/*  692 */                 i14 &= 255 << 8 - i11;
/*      */               }
/*  694 */               arrayOfShort[i12] = (short)(arrayOfShort[i12] & s1 | i14 << i8);
/*  695 */             } else if (i11 > i9) {
/*      */               
/*  697 */               arrayOfShort[i12] = (short)(arrayOfShort[i12] & s2 | i14 >>> i7 & 0xFFFF);
/*  698 */               arrayOfShort[++i12] = (short)(i14 << i8 & 0xFFFF);
/*      */             }
/*  700 */             else if (i11 > i8) {
/*      */ 
/*      */               
/*  703 */               arrayOfShort[i12] = (short)(arrayOfShort[i12] & s2 | i14 >>> i7 & 0xFFFF);
/*  704 */               i12++;
/*  705 */               arrayOfShort[i12] = (short)(arrayOfShort[i12] & s3 | i14 << i8 & 0xFFFF);
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  710 */               int i15 = (1 << i8 - i11) - 1;
/*  711 */               arrayOfShort[i12] = (short)(arrayOfShort[i12] & (s2 | i15) | i14 >>> i7 & 0xFFFF & (i15 ^ 0xFFFFFFFF));
/*      */             } 
/*      */           } 
/*      */           
/*  715 */           i3 += i2;
/*      */         } 
/*  717 */       } else if (dataBuffer instanceof DataBufferInt) {
/*  718 */         int[] arrayOfInt = ((DataBufferInt)dataBuffer).getData();
/*  719 */         int i7 = i4 & 0x7;
/*  720 */         int i8 = 8 - i7;
/*  721 */         int i9 = 32 + i8;
/*  722 */         int i10 = -1 << i8;
/*  723 */         int i11 = i10 ^ 0xFFFFFFFF;
/*      */         
/*  725 */         for (byte b1 = 0; b1 < m; b1++) {
/*  726 */           int i12 = i4;
/*  727 */           int i13 = k;
/*  728 */           for (byte b2 = 0; b2 < k; 
/*  729 */             b2 += 8, i12 += 8, i13 -= 8) {
/*  730 */             int i14 = i3 + (i12 >> 5);
/*  731 */             int i15 = i12 & 0x1F;
/*  732 */             int i16 = paramArrayOfbyte[b++] & 0xFF;
/*  733 */             if (i15 <= 24) {
/*      */               
/*  735 */               int i17 = 24 - i15;
/*  736 */               if (i13 < 8)
/*      */               {
/*  738 */                 i16 &= 255 << 8 - i13;
/*      */               }
/*  740 */               arrayOfInt[i14] = arrayOfInt[i14] & (255 << i17 ^ 0xFFFFFFFF) | i16 << i17;
/*  741 */             } else if (i13 > i9) {
/*      */               
/*  743 */               arrayOfInt[i14] = arrayOfInt[i14] & i10 | i16 >>> i7;
/*  744 */               arrayOfInt[++i14] = i16 << i8;
/*  745 */             } else if (i13 > i8) {
/*      */ 
/*      */               
/*  748 */               arrayOfInt[i14] = arrayOfInt[i14] & i10 | i16 >>> i7;
/*  749 */               i14++;
/*  750 */               arrayOfInt[i14] = arrayOfInt[i14] & i11 | i16 << i8;
/*      */             } else {
/*      */               
/*  753 */               int i17 = (1 << i8 - i13) - 1;
/*  754 */               arrayOfInt[i14] = arrayOfInt[i14] & (i10 | i17) | i16 >>> i7 & (i17 ^ 0xFFFFFFFF);
/*      */             } 
/*      */           } 
/*      */           
/*  758 */           i3 += i2;
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
/*      */   public static void setUnpackedBinaryData(byte[] paramArrayOfbyte, WritableRaster paramWritableRaster, Rectangle paramRectangle) {
/*  779 */     SampleModel sampleModel = paramWritableRaster.getSampleModel();
/*  780 */     if (!isBinary(sampleModel)) {
/*  781 */       throw new IllegalArgumentException(I18N.getString("ImageUtil0"));
/*      */     }
/*      */     
/*  784 */     int i = paramRectangle.x;
/*  785 */     int j = paramRectangle.y;
/*  786 */     int k = paramRectangle.width;
/*  787 */     int m = paramRectangle.height;
/*      */     
/*  789 */     DataBuffer dataBuffer = paramWritableRaster.getDataBuffer();
/*      */     
/*  791 */     int n = i - paramWritableRaster.getSampleModelTranslateX();
/*  792 */     int i1 = j - paramWritableRaster.getSampleModelTranslateY();
/*      */     
/*  794 */     MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)sampleModel;
/*  795 */     int i2 = multiPixelPackedSampleModel.getScanlineStride();
/*  796 */     int i3 = dataBuffer.getOffset() + multiPixelPackedSampleModel.getOffset(n, i1);
/*  797 */     int i4 = multiPixelPackedSampleModel.getBitOffset(n);
/*      */     
/*  799 */     byte b = 0;
/*      */     
/*  801 */     if (dataBuffer instanceof DataBufferByte) {
/*  802 */       byte[] arrayOfByte = ((DataBufferByte)dataBuffer).getData();
/*  803 */       for (byte b1 = 0; b1 < m; b1++) {
/*  804 */         int i5 = i3 * 8 + i4;
/*  805 */         for (byte b2 = 0; b2 < k; b2++) {
/*  806 */           if (paramArrayOfbyte[b++] != 0) {
/*  807 */             arrayOfByte[i5 / 8] = (byte)(arrayOfByte[i5 / 8] | (byte)(1 << (7 - i5 & 0x7)));
/*      */           }
/*      */           
/*  810 */           i5++;
/*      */         } 
/*  812 */         i3 += i2;
/*      */       } 
/*  814 */     } else if (dataBuffer instanceof DataBufferShort || dataBuffer instanceof DataBufferUShort) {
/*      */ 
/*      */ 
/*      */       
/*  818 */       short[] arrayOfShort = (dataBuffer instanceof DataBufferShort) ? ((DataBufferShort)dataBuffer).getData() : ((DataBufferUShort)dataBuffer).getData();
/*  819 */       for (byte b1 = 0; b1 < m; b1++) {
/*  820 */         int i5 = i3 * 16 + i4;
/*  821 */         for (byte b2 = 0; b2 < k; b2++) {
/*  822 */           if (paramArrayOfbyte[b++] != 0) {
/*  823 */             arrayOfShort[i5 / 16] = (short)(arrayOfShort[i5 / 16] | (short)(1 << 15 - i5 % 16));
/*      */           }
/*      */ 
/*      */           
/*  827 */           i5++;
/*      */         } 
/*  829 */         i3 += i2;
/*      */       } 
/*  831 */     } else if (dataBuffer instanceof DataBufferInt) {
/*  832 */       int[] arrayOfInt = ((DataBufferInt)dataBuffer).getData();
/*  833 */       for (byte b1 = 0; b1 < m; b1++) {
/*  834 */         int i5 = i3 * 32 + i4;
/*  835 */         for (byte b2 = 0; b2 < k; b2++) {
/*  836 */           if (paramArrayOfbyte[b++] != 0) {
/*  837 */             arrayOfInt[i5 / 32] = arrayOfInt[i5 / 32] | 1 << 31 - i5 % 32;
/*      */           }
/*      */ 
/*      */           
/*  841 */           i5++;
/*      */         } 
/*  843 */         i3 += i2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isBinary(SampleModel paramSampleModel) {
/*  849 */     return (paramSampleModel instanceof MultiPixelPackedSampleModel && ((MultiPixelPackedSampleModel)paramSampleModel)
/*  850 */       .getPixelBitStride() == 1 && paramSampleModel
/*  851 */       .getNumBands() == 1);
/*      */   }
/*      */   
/*      */   public static ColorModel createColorModel(ColorSpace paramColorSpace, SampleModel paramSampleModel) {
/*      */     IndexColorModel indexColorModel;
/*  856 */     ComponentColorModel componentColorModel = null;
/*      */     
/*  858 */     if (paramSampleModel == null) {
/*  859 */       throw new IllegalArgumentException(I18N.getString("ImageUtil1"));
/*      */     }
/*      */     
/*  862 */     int i = paramSampleModel.getNumBands();
/*  863 */     if (i < 1 || i > 4) {
/*  864 */       return null;
/*      */     }
/*      */     
/*  867 */     int j = paramSampleModel.getDataType();
/*  868 */     if (paramSampleModel instanceof ComponentSampleModel) {
/*  869 */       if (j < 0 || j > 5)
/*      */       {
/*      */         
/*  872 */         return null;
/*      */       }
/*      */       
/*  875 */       if (paramColorSpace == null)
/*      */       {
/*      */ 
/*      */         
/*  879 */         paramColorSpace = (i <= 2) ? ColorSpace.getInstance(1003) : ColorSpace.getInstance(1000);
/*      */       }
/*  881 */       boolean bool1 = (i == 2 || i == 4) ? true : false;
/*  882 */       boolean bool2 = bool1 ? true : true;
/*      */ 
/*      */       
/*  885 */       boolean bool3 = false;
/*      */       
/*  887 */       int k = DataBuffer.getDataTypeSize(j);
/*  888 */       int[] arrayOfInt = new int[i];
/*  889 */       for (byte b = 0; b < i; b++) {
/*  890 */         arrayOfInt[b] = k;
/*      */       }
/*      */       
/*  893 */       componentColorModel = new ComponentColorModel(paramColorSpace, arrayOfInt, bool1, bool3, bool2, j);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  899 */     else if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/*  900 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*      */ 
/*      */       
/*  903 */       int[] arrayOfInt1 = singlePixelPackedSampleModel.getBitMasks();
/*  904 */       int k = 0;
/*  905 */       int m = 0;
/*  906 */       int n = 0;
/*  907 */       int i1 = 0;
/*      */       
/*  909 */       i = arrayOfInt1.length;
/*  910 */       if (i <= 2) {
/*  911 */         k = m = n = arrayOfInt1[0];
/*  912 */         if (i == 2) {
/*  913 */           i1 = arrayOfInt1[1];
/*      */         }
/*      */       } else {
/*  916 */         k = arrayOfInt1[0];
/*  917 */         m = arrayOfInt1[1];
/*  918 */         n = arrayOfInt1[2];
/*  919 */         if (i == 4) {
/*  920 */           i1 = arrayOfInt1[3];
/*      */         }
/*      */       } 
/*      */       
/*  924 */       int[] arrayOfInt2 = singlePixelPackedSampleModel.getSampleSize();
/*  925 */       int i2 = 0;
/*  926 */       for (byte b = 0; b < arrayOfInt2.length; b++) {
/*  927 */         i2 += arrayOfInt2[b];
/*      */       }
/*      */       
/*  930 */       if (paramColorSpace == null) {
/*  931 */         paramColorSpace = ColorSpace.getInstance(1000);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  937 */       DirectColorModel directColorModel = new DirectColorModel(paramColorSpace, i2, k, m, n, i1, false, paramSampleModel.getDataType());
/*  938 */     } else if (paramSampleModel instanceof MultiPixelPackedSampleModel) {
/*      */       
/*  940 */       int k = ((MultiPixelPackedSampleModel)paramSampleModel).getPixelBitStride();
/*  941 */       int m = 1 << k;
/*  942 */       byte[] arrayOfByte = new byte[m];
/*      */       
/*  944 */       for (byte b = 0; b < m; b++) {
/*  945 */         arrayOfByte[b] = (byte)(255 * b / (m - 1));
/*      */       }
/*  947 */       indexColorModel = new IndexColorModel(k, m, arrayOfByte, arrayOfByte, arrayOfByte);
/*      */     } 
/*      */     
/*  950 */     return indexColorModel;
/*      */   }
/*      */   
/*      */   public static int getElementSize(SampleModel paramSampleModel) {
/*  954 */     int i = DataBuffer.getDataTypeSize(paramSampleModel.getDataType());
/*      */     
/*  956 */     if (paramSampleModel instanceof MultiPixelPackedSampleModel) {
/*  957 */       MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)paramSampleModel;
/*      */       
/*  959 */       return multiPixelPackedSampleModel.getSampleSize(0) * multiPixelPackedSampleModel.getNumBands();
/*  960 */     }  if (paramSampleModel instanceof ComponentSampleModel)
/*  961 */       return paramSampleModel.getNumBands() * i; 
/*  962 */     if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/*  963 */       return i;
/*      */     }
/*      */     
/*  966 */     return i * paramSampleModel.getNumBands();
/*      */   }
/*      */ 
/*      */   
/*      */   public static long getTileSize(SampleModel paramSampleModel) {
/*  971 */     int i = DataBuffer.getDataTypeSize(paramSampleModel.getDataType());
/*      */     
/*  973 */     if (paramSampleModel instanceof MultiPixelPackedSampleModel) {
/*  974 */       MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)paramSampleModel;
/*      */       
/*  976 */       return ((multiPixelPackedSampleModel.getScanlineStride() * multiPixelPackedSampleModel.getHeight() + (multiPixelPackedSampleModel
/*  977 */         .getDataBitOffset() + i - 1) / i) * (i + 7) / 8);
/*      */     } 
/*  979 */     if (paramSampleModel instanceof ComponentSampleModel) {
/*  980 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramSampleModel;
/*  981 */       int[] arrayOfInt1 = componentSampleModel.getBandOffsets();
/*  982 */       int j = arrayOfInt1[0];
/*  983 */       for (byte b1 = 1; b1 < arrayOfInt1.length; b1++) {
/*  984 */         j = Math.max(j, arrayOfInt1[b1]);
/*      */       }
/*  986 */       long l = 0L;
/*  987 */       int k = componentSampleModel.getPixelStride();
/*  988 */       int m = componentSampleModel.getScanlineStride();
/*  989 */       if (j >= 0)
/*  990 */         l += (j + 1); 
/*  991 */       if (k > 0)
/*  992 */         l += (k * (paramSampleModel.getWidth() - 1)); 
/*  993 */       if (m > 0) {
/*  994 */         l += (m * (paramSampleModel.getHeight() - 1));
/*      */       }
/*  996 */       int[] arrayOfInt2 = componentSampleModel.getBankIndices();
/*  997 */       j = arrayOfInt2[0];
/*  998 */       for (byte b2 = 1; b2 < arrayOfInt2.length; b2++)
/*  999 */         j = Math.max(j, arrayOfInt2[b2]); 
/* 1000 */       return l * (j + 1) * ((i + 7) / 8);
/* 1001 */     }  if (paramSampleModel instanceof SinglePixelPackedSampleModel) {
/* 1002 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)paramSampleModel;
/*      */ 
/*      */       
/* 1005 */       long l = (singlePixelPackedSampleModel.getScanlineStride() * (singlePixelPackedSampleModel.getHeight() - 1) + singlePixelPackedSampleModel.getWidth());
/* 1006 */       return l * ((i + 7) / 8);
/*      */     } 
/*      */     
/* 1009 */     return 0L;
/*      */   }
/*      */   
/*      */   public static long getBandSize(SampleModel paramSampleModel) {
/* 1013 */     int i = DataBuffer.getDataTypeSize(paramSampleModel.getDataType());
/*      */     
/* 1015 */     if (paramSampleModel instanceof ComponentSampleModel) {
/* 1016 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramSampleModel;
/* 1017 */       int j = componentSampleModel.getPixelStride();
/* 1018 */       int k = componentSampleModel.getScanlineStride();
/* 1019 */       long l = Math.min(j, k);
/*      */       
/* 1021 */       if (j > 0)
/* 1022 */         l += (j * (paramSampleModel.getWidth() - 1)); 
/* 1023 */       if (k > 0)
/* 1024 */         l += (k * (paramSampleModel.getHeight() - 1)); 
/* 1025 */       return l * ((i + 7) / 8);
/*      */     } 
/* 1027 */     return getTileSize(paramSampleModel);
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
/*      */   public static boolean isIndicesForGrayscale(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/* 1039 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length || paramArrayOfbyte1.length != paramArrayOfbyte3.length) {
/* 1040 */       return false;
/*      */     }
/* 1042 */     int i = paramArrayOfbyte1.length;
/*      */     
/* 1044 */     if (i != 256) {
/* 1045 */       return false;
/*      */     }
/* 1047 */     for (byte b = 0; b < i; b++) {
/* 1048 */       byte b1 = (byte)b;
/*      */       
/* 1050 */       if (paramArrayOfbyte1[b] != b1 || paramArrayOfbyte2[b] != b1 || paramArrayOfbyte3[b] != b1) {
/* 1051 */         return false;
/*      */       }
/*      */     } 
/* 1054 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String convertObjectToString(Object paramObject) {
/* 1059 */     if (paramObject == null) {
/* 1060 */       return "";
/*      */     }
/* 1062 */     String str = "";
/* 1063 */     if (paramObject instanceof byte[]) {
/* 1064 */       byte[] arrayOfByte = (byte[])paramObject;
/* 1065 */       for (byte b = 0; b < arrayOfByte.length; b++)
/* 1066 */         str = str + arrayOfByte[b] + " "; 
/* 1067 */       return str;
/*      */     } 
/*      */     
/* 1070 */     if (paramObject instanceof int[]) {
/* 1071 */       int[] arrayOfInt = (int[])paramObject;
/* 1072 */       for (byte b = 0; b < arrayOfInt.length; b++)
/* 1073 */         str = str + arrayOfInt[b] + " "; 
/* 1074 */       return str;
/*      */     } 
/*      */     
/* 1077 */     if (paramObject instanceof short[]) {
/* 1078 */       short[] arrayOfShort = (short[])paramObject;
/* 1079 */       for (byte b = 0; b < arrayOfShort.length; b++)
/* 1080 */         str = str + arrayOfShort[b] + " "; 
/* 1081 */       return str;
/*      */     } 
/*      */     
/* 1084 */     return paramObject.toString();
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
/*      */   public static final void canEncodeImage(ImageWriter paramImageWriter, ImageTypeSpecifier paramImageTypeSpecifier) throws IIOException {
/* 1098 */     ImageWriterSpi imageWriterSpi = paramImageWriter.getOriginatingProvider();
/*      */     
/* 1100 */     if (paramImageTypeSpecifier != null && imageWriterSpi != null && !imageWriterSpi.canEncodeImage(paramImageTypeSpecifier)) {
/* 1101 */       throw new IIOException(I18N.getString("ImageUtil2") + " " + paramImageWriter
/* 1102 */           .getClass().getName());
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
/*      */   public static final void canEncodeImage(ImageWriter paramImageWriter, ColorModel paramColorModel, SampleModel paramSampleModel) throws IIOException {
/* 1118 */     ImageTypeSpecifier imageTypeSpecifier = null;
/* 1119 */     if (paramColorModel != null && paramSampleModel != null)
/* 1120 */       imageTypeSpecifier = new ImageTypeSpecifier(paramColorModel, paramSampleModel); 
/* 1121 */     canEncodeImage(paramImageWriter, imageTypeSpecifier);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean imageIsContiguous(RenderedImage paramRenderedImage) {
/*      */     SampleModel sampleModel;
/* 1129 */     if (paramRenderedImage instanceof BufferedImage) {
/* 1130 */       WritableRaster writableRaster = ((BufferedImage)paramRenderedImage).getRaster();
/* 1131 */       sampleModel = writableRaster.getSampleModel();
/*      */     } else {
/* 1133 */       sampleModel = paramRenderedImage.getSampleModel();
/*      */     } 
/*      */     
/* 1136 */     if (sampleModel instanceof ComponentSampleModel) {
/*      */ 
/*      */       
/* 1139 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)sampleModel;
/*      */       
/* 1141 */       if (componentSampleModel.getPixelStride() != componentSampleModel.getNumBands()) {
/* 1142 */         return false;
/*      */       }
/*      */       
/* 1145 */       int[] arrayOfInt1 = componentSampleModel.getBandOffsets();
/* 1146 */       for (byte b1 = 0; b1 < arrayOfInt1.length; b1++) {
/* 1147 */         if (arrayOfInt1[b1] != b1) {
/* 1148 */           return false;
/*      */         }
/*      */       } 
/*      */       
/* 1152 */       int[] arrayOfInt2 = componentSampleModel.getBankIndices();
/* 1153 */       for (byte b2 = 0; b2 < arrayOfInt1.length; b2++) {
/* 1154 */         if (arrayOfInt2[b2] != 0) {
/* 1155 */           return false;
/*      */         }
/*      */       } 
/*      */       
/* 1159 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     return isBinary(sampleModel);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/ImageUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */