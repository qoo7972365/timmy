/*      */ package com.sun.imageio.plugins.bmp;
/*      */ 
/*      */ import com.sun.imageio.plugins.common.I18N;
/*      */ import com.sun.imageio.plugins.common.ImageUtil;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.ColorModel;
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
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.Iterator;
/*      */ import javax.imageio.IIOImage;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.ImageWriteParam;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.event.IIOWriteProgressListener;
/*      */ import javax.imageio.event.IIOWriteWarningListener;
/*      */ import javax.imageio.metadata.IIOMetadata;
/*      */ import javax.imageio.plugins.bmp.BMPImageWriteParam;
/*      */ import javax.imageio.spi.ImageWriterSpi;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BMPImageWriter
/*      */   extends ImageWriter
/*      */   implements BMPConstants
/*      */ {
/*   78 */   private ImageOutputStream stream = null;
/*   79 */   private ByteArrayOutputStream embedded_stream = null; private int version;
/*      */   private int compressionType;
/*      */   private boolean isTopDown;
/*      */   private int w;
/*      */   private int h;
/*   84 */   private int compImageSize = 0;
/*      */   
/*      */   private int[] bitMasks;
/*      */   
/*      */   private int[] bitPos;
/*      */   
/*      */   private byte[] bpixels;
/*      */   private short[] spixels;
/*      */   private int[] ipixels;
/*      */   
/*      */   public BMPImageWriter(ImageWriterSpi paramImageWriterSpi) {
/*   95 */     super(paramImageWriterSpi);
/*      */   }
/*      */   
/*      */   public void setOutput(Object paramObject) {
/*   99 */     super.setOutput(paramObject);
/*  100 */     if (paramObject != null) {
/*  101 */       if (!(paramObject instanceof ImageOutputStream))
/*  102 */         throw new IllegalArgumentException(I18N.getString("BMPImageWriter0")); 
/*  103 */       this.stream = (ImageOutputStream)paramObject;
/*  104 */       this.stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
/*      */     } else {
/*  106 */       this.stream = null;
/*      */     } 
/*      */   }
/*      */   public ImageWriteParam getDefaultWriteParam() {
/*  110 */     return new BMPImageWriteParam();
/*      */   }
/*      */   
/*      */   public IIOMetadata getDefaultStreamMetadata(ImageWriteParam paramImageWriteParam) {
/*  114 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/*  119 */     BMPMetadata bMPMetadata = new BMPMetadata();
/*  120 */     bMPMetadata.bmpVersion = "BMP v. 3.x";
/*  121 */     bMPMetadata.compression = getPreferredCompressionType(paramImageTypeSpecifier);
/*  122 */     if (paramImageWriteParam != null && paramImageWriteParam
/*  123 */       .getCompressionMode() == 2) {
/*  124 */       bMPMetadata.compression = BMPCompressionTypes.getType(paramImageWriteParam.getCompressionType());
/*      */     }
/*  126 */     bMPMetadata.bitsPerPixel = (short)paramImageTypeSpecifier.getColorModel().getPixelSize();
/*  127 */     return bMPMetadata;
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadata convertStreamMetadata(IIOMetadata paramIIOMetadata, ImageWriteParam paramImageWriteParam) {
/*  132 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOMetadata convertImageMetadata(IIOMetadata paramIIOMetadata, ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/*  138 */     return null;
/*      */   }
/*      */   
/*      */   public boolean canWriteRasters() {
/*  142 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(IIOMetadata paramIIOMetadata, IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/*  149 */     if (this.stream == null) {
/*  150 */       throw new IllegalStateException(I18N.getString("BMPImageWriter7"));
/*      */     }
/*      */     
/*  153 */     if (paramIIOImage == null) {
/*  154 */       throw new IllegalArgumentException(I18N.getString("BMPImageWriter8"));
/*      */     }
/*      */     
/*  157 */     clearAbortRequest();
/*  158 */     processImageStarted(0);
/*  159 */     if (paramImageWriteParam == null) {
/*  160 */       paramImageWriteParam = getDefaultWriteParam();
/*      */     }
/*  162 */     BMPImageWriteParam bMPImageWriteParam = (BMPImageWriteParam)paramImageWriteParam;
/*      */ 
/*      */     
/*  165 */     int i = 24;
/*  166 */     boolean bool1 = false;
/*  167 */     int j = 0;
/*  168 */     IndexColorModel indexColorModel = null;
/*      */     
/*  170 */     RenderedImage renderedImage = null;
/*  171 */     Raster raster = null;
/*  172 */     boolean bool = paramIIOImage.hasRaster();
/*  173 */     Rectangle rectangle1 = paramImageWriteParam.getSourceRegion();
/*  174 */     SampleModel sampleModel = null;
/*  175 */     ColorModel colorModel = null;
/*      */     
/*  177 */     this.compImageSize = 0;
/*      */     
/*  179 */     if (bool)
/*  180 */     { raster = paramIIOImage.getRaster();
/*  181 */       sampleModel = raster.getSampleModel();
/*  182 */       colorModel = ImageUtil.createColorModel(null, sampleModel);
/*  183 */       if (rectangle1 == null) {
/*  184 */         rectangle1 = raster.getBounds();
/*      */       } else {
/*  186 */         rectangle1 = rectangle1.intersection(raster.getBounds());
/*      */       }  }
/*  188 */     else { renderedImage = paramIIOImage.getRenderedImage();
/*  189 */       sampleModel = renderedImage.getSampleModel();
/*  190 */       colorModel = renderedImage.getColorModel();
/*      */       
/*  192 */       Rectangle rectangle = new Rectangle(renderedImage.getMinX(), renderedImage.getMinY(), renderedImage.getWidth(), renderedImage.getHeight());
/*  193 */       if (rectangle1 == null) {
/*  194 */         rectangle1 = rectangle;
/*      */       } else {
/*  196 */         rectangle1 = rectangle1.intersection(rectangle);
/*      */       }  }
/*      */     
/*  199 */     IIOMetadata iIOMetadata = paramIIOImage.getMetadata();
/*  200 */     BMPMetadata bMPMetadata = null;
/*  201 */     if (iIOMetadata != null && iIOMetadata instanceof BMPMetadata) {
/*      */ 
/*      */       
/*  204 */       bMPMetadata = (BMPMetadata)iIOMetadata;
/*      */     } else {
/*  206 */       ImageTypeSpecifier imageTypeSpecifier = new ImageTypeSpecifier(colorModel, sampleModel);
/*      */ 
/*      */       
/*  209 */       bMPMetadata = (BMPMetadata)getDefaultImageMetadata(imageTypeSpecifier, paramImageWriteParam);
/*      */     } 
/*      */ 
/*      */     
/*  213 */     if (rectangle1.isEmpty()) {
/*  214 */       throw new RuntimeException(I18N.getString("BMPImageWrite0"));
/*      */     }
/*  216 */     int k = paramImageWriteParam.getSourceXSubsampling();
/*  217 */     int m = paramImageWriteParam.getSourceYSubsampling();
/*  218 */     int n = paramImageWriteParam.getSubsamplingXOffset();
/*  219 */     int i1 = paramImageWriteParam.getSubsamplingYOffset();
/*      */ 
/*      */     
/*  222 */     int i2 = sampleModel.getDataType();
/*      */     
/*  224 */     rectangle1.translate(n, i1);
/*  225 */     rectangle1.width -= n;
/*  226 */     rectangle1.height -= i1;
/*      */     
/*  228 */     int i3 = rectangle1.x / k;
/*  229 */     int i4 = rectangle1.y / m;
/*  230 */     this.w = (rectangle1.width + k - 1) / k;
/*  231 */     this.h = (rectangle1.height + m - 1) / m;
/*  232 */     n = rectangle1.x % k;
/*  233 */     i1 = rectangle1.y % m;
/*      */     
/*  235 */     Rectangle rectangle2 = new Rectangle(i3, i4, this.w, this.h);
/*  236 */     int i6 = rectangle2.equals(rectangle1);
/*      */ 
/*      */     
/*  239 */     int[] arrayOfInt1 = paramImageWriteParam.getSourceBands();
/*  240 */     boolean bool2 = true;
/*  241 */     int i7 = sampleModel.getNumBands();
/*      */     
/*  243 */     if (arrayOfInt1 != null) {
/*  244 */       sampleModel = sampleModel.createSubsetSampleModel(arrayOfInt1);
/*  245 */       colorModel = null;
/*  246 */       bool2 = false;
/*  247 */       i7 = sampleModel.getNumBands();
/*      */     } else {
/*  249 */       arrayOfInt1 = new int[i7];
/*  250 */       for (byte b = 0; b < i7; b++) {
/*  251 */         arrayOfInt1[b] = b;
/*      */       }
/*      */     } 
/*  254 */     int[] arrayOfInt2 = null;
/*  255 */     int i8 = 1;
/*      */     
/*  257 */     if (sampleModel instanceof ComponentSampleModel) {
/*  258 */       arrayOfInt2 = ((ComponentSampleModel)sampleModel).getBandOffsets();
/*  259 */       if (sampleModel instanceof java.awt.image.BandedSampleModel)
/*      */       {
/*      */         
/*  262 */         i8 = 0;
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*  267 */         for (byte b = 0; b < arrayOfInt2.length; b++) {
/*  268 */           i8 &= (arrayOfInt2[b] == arrayOfInt2.length - b - 1) ? 1 : 0;
/*      */         }
/*      */       }
/*      */     
/*  272 */     } else if (sampleModel instanceof SinglePixelPackedSampleModel) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  277 */       int[] arrayOfInt = ((SinglePixelPackedSampleModel)sampleModel).getBitOffsets();
/*  278 */       for (byte b = 0; b < arrayOfInt.length - 1; b++) {
/*  279 */         i8 &= (arrayOfInt[b] > arrayOfInt[b + 1]) ? 1 : 0;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  284 */     if (arrayOfInt2 == null) {
/*      */ 
/*      */       
/*  287 */       arrayOfInt2 = new int[i7];
/*  288 */       for (byte b = 0; b < i7; b++) {
/*  289 */         arrayOfInt2[b] = b;
/*      */       }
/*      */     } 
/*  292 */     int i5 = i6 & i8;
/*      */     
/*  294 */     int[] arrayOfInt3 = sampleModel.getSampleSize();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  299 */     int i9 = this.w * i7;
/*      */     
/*  301 */     switch (bMPImageWriteParam.getCompressionMode()) {
/*      */       case 2:
/*  303 */         this.compressionType = BMPCompressionTypes.getType(bMPImageWriteParam.getCompressionType());
/*      */         break;
/*      */       case 3:
/*  306 */         this.compressionType = bMPMetadata.compression;
/*      */         break;
/*      */       case 1:
/*  309 */         this.compressionType = getPreferredCompressionType(colorModel, sampleModel);
/*      */         break;
/*      */       
/*      */       default:
/*  313 */         this.compressionType = 0;
/*      */         break;
/*      */     } 
/*  316 */     if (!canEncodeImage(this.compressionType, colorModel, sampleModel)) {
/*  317 */       throw new IOException("Image can not be encoded with compression type " + 
/*  318 */           BMPCompressionTypes.getName(this.compressionType));
/*      */     }
/*      */     
/*  321 */     byte[] arrayOfByte1 = null, arrayOfByte2 = null, arrayOfByte3 = null, arrayOfByte4 = null;
/*      */     
/*  323 */     if (this.compressionType == 3) {
/*      */       
/*  325 */       i = DataBuffer.getDataTypeSize(sampleModel.getDataType());
/*      */       
/*  327 */       if (i != 16 && i != 32) {
/*      */ 
/*      */         
/*  330 */         i = 32;
/*      */ 
/*      */ 
/*      */         
/*  334 */         i5 = 0;
/*      */       } 
/*      */       
/*  337 */       i9 = this.w * i + 7 >> 3;
/*      */       
/*  339 */       bool1 = true;
/*  340 */       j = 3;
/*  341 */       arrayOfByte1 = new byte[j];
/*  342 */       arrayOfByte2 = new byte[j];
/*  343 */       arrayOfByte3 = new byte[j];
/*  344 */       arrayOfByte4 = new byte[j];
/*      */       
/*  346 */       int i18 = 16711680;
/*  347 */       int i19 = 65280;
/*  348 */       int i20 = 255;
/*      */       
/*  350 */       if (i == 16)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  357 */         if (colorModel instanceof DirectColorModel) {
/*  358 */           DirectColorModel directColorModel = (DirectColorModel)colorModel;
/*  359 */           i18 = directColorModel.getRedMask();
/*  360 */           i19 = directColorModel.getGreenMask();
/*  361 */           i20 = directColorModel.getBlueMask();
/*      */         }
/*      */         else {
/*      */           
/*  365 */           throw new IOException("Image can not be encoded with compression type " + 
/*      */               
/*  367 */               BMPCompressionTypes.getName(this.compressionType));
/*      */         } 
/*      */       }
/*  370 */       writeMaskToPalette(i18, 0, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4);
/*  371 */       writeMaskToPalette(i19, 1, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4);
/*  372 */       writeMaskToPalette(i20, 2, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4);
/*      */       
/*  374 */       if (i5 == 0) {
/*      */         
/*  376 */         this.bitMasks = new int[3];
/*  377 */         this.bitMasks[0] = i18;
/*  378 */         this.bitMasks[1] = i19;
/*  379 */         this.bitMasks[2] = i20;
/*      */         
/*  381 */         this.bitPos = new int[3];
/*  382 */         this.bitPos[0] = firstLowBit(i18);
/*  383 */         this.bitPos[1] = firstLowBit(i19);
/*  384 */         this.bitPos[2] = firstLowBit(i20);
/*      */       } 
/*      */       
/*  387 */       if (colorModel instanceof IndexColorModel) {
/*  388 */         indexColorModel = (IndexColorModel)colorModel;
/*      */       }
/*      */     }
/*  391 */     else if (colorModel instanceof IndexColorModel) {
/*  392 */       bool1 = true;
/*  393 */       indexColorModel = (IndexColorModel)colorModel;
/*  394 */       j = indexColorModel.getMapSize();
/*      */       
/*  396 */       if (j <= 2) {
/*  397 */         i = 1;
/*  398 */         i9 = this.w + 7 >> 3;
/*  399 */       } else if (j <= 16) {
/*  400 */         i = 4;
/*  401 */         i9 = this.w + 1 >> 1;
/*  402 */       } else if (j <= 256) {
/*  403 */         i = 8;
/*      */       }
/*      */       else {
/*      */         
/*  407 */         i = 24;
/*  408 */         bool1 = false;
/*  409 */         j = 0;
/*  410 */         i9 = this.w * 3;
/*      */       } 
/*      */       
/*  413 */       if (bool1 == true) {
/*  414 */         arrayOfByte1 = new byte[j];
/*  415 */         arrayOfByte2 = new byte[j];
/*  416 */         arrayOfByte3 = new byte[j];
/*  417 */         arrayOfByte4 = new byte[j];
/*      */         
/*  419 */         indexColorModel.getAlphas(arrayOfByte4);
/*  420 */         indexColorModel.getReds(arrayOfByte1);
/*  421 */         indexColorModel.getGreens(arrayOfByte2);
/*  422 */         indexColorModel.getBlues(arrayOfByte3);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  427 */     else if (i7 == 1) {
/*      */       
/*  429 */       bool1 = true;
/*  430 */       j = 256;
/*  431 */       i = arrayOfInt3[0];
/*      */       
/*  433 */       i9 = this.w * i + 7 >> 3;
/*      */       
/*  435 */       arrayOfByte1 = new byte[256];
/*  436 */       arrayOfByte2 = new byte[256];
/*  437 */       arrayOfByte3 = new byte[256];
/*  438 */       arrayOfByte4 = new byte[256];
/*      */       
/*  440 */       for (byte b = 0; b < 'Ā'; b++) {
/*  441 */         arrayOfByte1[b] = (byte)b;
/*  442 */         arrayOfByte2[b] = (byte)b;
/*  443 */         arrayOfByte3[b] = (byte)b;
/*  444 */         arrayOfByte4[b] = -1;
/*      */       }
/*      */     
/*      */     }
/*  448 */     else if (sampleModel instanceof SinglePixelPackedSampleModel && bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  457 */       int[] arrayOfInt = sampleModel.getSampleSize();
/*  458 */       i = 0;
/*  459 */       for (int i18 : arrayOfInt) {
/*  460 */         i += i18;
/*      */       }
/*  462 */       i = roundBpp(i);
/*  463 */       if (i != DataBuffer.getDataTypeSize(sampleModel.getDataType())) {
/*  464 */         i5 = 0;
/*      */       }
/*  466 */       i9 = this.w * i + 7 >> 3;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  473 */     int i10 = 0;
/*  474 */     int i11 = 0;
/*  475 */     byte b1 = 0;
/*  476 */     int i12 = 0;
/*  477 */     boolean bool3 = false;
/*  478 */     boolean bool4 = false;
/*  479 */     boolean bool5 = false;
/*  480 */     int i13 = j;
/*      */ 
/*      */     
/*  483 */     int i14 = i9 % 4;
/*  484 */     if (i14 != 0) {
/*  485 */       i14 = 4 - i14;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  491 */     i11 = 54 + j * 4;
/*      */     
/*  493 */     i12 = (i9 + i14) * this.h;
/*  494 */     i10 = i12 + i11;
/*  495 */     b1 = 40;
/*      */     
/*  497 */     long l = this.stream.getStreamPosition();
/*      */     
/*  499 */     writeFileHeader(i10, i11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  506 */     if (this.compressionType == 0 || this.compressionType == 3) {
/*      */ 
/*      */       
/*  509 */       this.isTopDown = bMPImageWriteParam.isTopDown();
/*      */     } else {
/*  511 */       this.isTopDown = false;
/*      */     } 
/*      */     
/*  514 */     writeInfoHeader(b1, i);
/*      */ 
/*      */     
/*  517 */     this.stream.writeInt(this.compressionType);
/*      */ 
/*      */     
/*  520 */     this.stream.writeInt(i12);
/*      */ 
/*      */     
/*  523 */     this.stream.writeInt(bool3);
/*      */ 
/*      */     
/*  526 */     this.stream.writeInt(bool4);
/*      */ 
/*      */     
/*  529 */     this.stream.writeInt(bool5);
/*      */ 
/*      */     
/*  532 */     this.stream.writeInt(i13);
/*      */ 
/*      */     
/*  535 */     if (bool1 == true)
/*      */     {
/*      */       
/*  538 */       if (this.compressionType == 3) {
/*      */         
/*  540 */         for (byte b = 0; b < 3; b++) {
/*  541 */           int i18 = (arrayOfByte4[b] & 0xFF) + (arrayOfByte1[b] & 0xFF) * 256 + (arrayOfByte2[b] & 0xFF) * 65536 + (arrayOfByte3[b] & 0xFF) * 16777216;
/*  542 */           this.stream.writeInt(i18);
/*      */         } 
/*      */       } else {
/*  545 */         for (byte b = 0; b < j; b++) {
/*  546 */           this.stream.writeByte(arrayOfByte3[b]);
/*  547 */           this.stream.writeByte(arrayOfByte2[b]);
/*  548 */           this.stream.writeByte(arrayOfByte1[b]);
/*  549 */           this.stream.writeByte(arrayOfByte4[b]);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  555 */     int i15 = this.w * i7;
/*      */ 
/*      */     
/*  558 */     int[] arrayOfInt4 = new int[i15 * k];
/*      */ 
/*      */ 
/*      */     
/*  562 */     this.bpixels = new byte[i9];
/*      */ 
/*      */ 
/*      */     
/*  566 */     if (this.compressionType == 4 || this.compressionType == 5) {
/*      */ 
/*      */ 
/*      */       
/*  570 */       this.embedded_stream = new ByteArrayOutputStream();
/*  571 */       writeEmbedded(paramIIOImage, bMPImageWriteParam);
/*      */       
/*  573 */       this.embedded_stream.flush();
/*  574 */       i12 = this.embedded_stream.size();
/*      */       
/*  576 */       long l1 = this.stream.getStreamPosition();
/*  577 */       i10 = i11 + i12;
/*  578 */       this.stream.seek(l);
/*  579 */       writeSize(i10, 2);
/*  580 */       this.stream.seek(l);
/*  581 */       writeSize(i12, 34);
/*  582 */       this.stream.seek(l1);
/*  583 */       this.stream.write(this.embedded_stream.toByteArray());
/*  584 */       this.embedded_stream = null;
/*      */       
/*  586 */       if (abortRequested()) {
/*  587 */         processWriteAborted();
/*      */       } else {
/*  589 */         processImageComplete();
/*  590 */         this.stream.flushBefore(this.stream.getStreamPosition());
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  596 */     int i16 = arrayOfInt2[0];
/*  597 */     for (byte b2 = 1; b2 < arrayOfInt2.length; b2++) {
/*  598 */       if (arrayOfInt2[b2] > i16)
/*  599 */         i16 = arrayOfInt2[b2]; 
/*      */     } 
/*  601 */     int[] arrayOfInt5 = new int[i16 + 1];
/*      */     
/*  603 */     int i17 = i9;
/*      */     
/*  605 */     if (i5 != 0 && bool2) {
/*  606 */       i17 = i9 / (DataBuffer.getDataTypeSize(i2) >> 3);
/*      */     }
/*  608 */     for (byte b3 = 0; b3 < this.h && 
/*  609 */       !abortRequested(); b3++) {
/*      */ 
/*      */ 
/*      */       
/*  613 */       int i18 = i4 + b3;
/*      */       
/*  615 */       if (!this.isTopDown) {
/*  616 */         i18 = i4 + this.h - b3 - 1;
/*      */       }
/*      */       
/*  619 */       Raster raster1 = raster;
/*      */       
/*  621 */       Rectangle rectangle = new Rectangle(i3 * k + n, i18 * m + i1, (this.w - 1) * k + 1, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  626 */       if (!bool) {
/*  627 */         raster1 = renderedImage.getData(rectangle);
/*      */       }
/*  629 */       if (i5 != 0 && bool2) {
/*  630 */         SampleModel sampleModel1 = raster1.getSampleModel();
/*  631 */         int i19 = 0;
/*  632 */         int i20 = rectangle.x - raster1.getSampleModelTranslateX();
/*  633 */         int i21 = rectangle.y - raster1.getSampleModelTranslateY();
/*  634 */         if (sampleModel1 instanceof ComponentSampleModel) {
/*  635 */           ComponentSampleModel componentSampleModel = (ComponentSampleModel)sampleModel1;
/*  636 */           i19 = componentSampleModel.getOffset(i20, i21, 0);
/*  637 */           for (byte b = 1; b < componentSampleModel.getNumBands(); b++) {
/*  638 */             if (i19 > componentSampleModel.getOffset(i20, i21, b)) {
/*  639 */               i19 = componentSampleModel.getOffset(i20, i21, b);
/*      */             }
/*      */           } 
/*  642 */         } else if (sampleModel1 instanceof MultiPixelPackedSampleModel) {
/*  643 */           MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)sampleModel1;
/*      */           
/*  645 */           i19 = multiPixelPackedSampleModel.getOffset(i20, i21);
/*  646 */         } else if (sampleModel1 instanceof SinglePixelPackedSampleModel) {
/*  647 */           SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)sampleModel1;
/*      */           
/*  649 */           i19 = singlePixelPackedSampleModel.getOffset(i20, i21);
/*      */         } 
/*      */         
/*  652 */         if (this.compressionType == 0 || this.compressionType == 3) {
/*  653 */           byte[] arrayOfByte; short[] arrayOfShort1; short[] arrayOfShort2; int[] arrayOfInt; switch (i2) {
/*      */             
/*      */             case 0:
/*  656 */               arrayOfByte = ((DataBufferByte)raster1.getDataBuffer()).getData();
/*  657 */               this.stream.write(arrayOfByte, i19, i17);
/*      */               break;
/*      */ 
/*      */             
/*      */             case 2:
/*  662 */               arrayOfShort1 = ((DataBufferShort)raster1.getDataBuffer()).getData();
/*  663 */               this.stream.writeShorts(arrayOfShort1, i19, i17);
/*      */               break;
/*      */ 
/*      */             
/*      */             case 1:
/*  668 */               arrayOfShort2 = ((DataBufferUShort)raster1.getDataBuffer()).getData();
/*  669 */               this.stream.writeShorts(arrayOfShort2, i19, i17);
/*      */               break;
/*      */ 
/*      */             
/*      */             case 3:
/*  674 */               arrayOfInt = ((DataBufferInt)raster1.getDataBuffer()).getData();
/*  675 */               this.stream.writeInts(arrayOfInt, i19, i17);
/*      */               break;
/*      */           } 
/*      */           
/*  679 */           for (byte b = 0; b < i14; b++) {
/*  680 */             this.stream.writeByte(0);
/*      */           }
/*  682 */         } else if (this.compressionType == 2) {
/*  683 */           if (this.bpixels == null || this.bpixels.length < i15)
/*  684 */             this.bpixels = new byte[i15]; 
/*  685 */           raster1.getPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height, arrayOfInt4);
/*      */           
/*  687 */           for (byte b = 0; b < i15; b++) {
/*  688 */             this.bpixels[b] = (byte)arrayOfInt4[b];
/*      */           }
/*  690 */           encodeRLE4(this.bpixels, i15);
/*  691 */         } else if (this.compressionType == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  696 */           if (this.bpixels == null || this.bpixels.length < i15)
/*  697 */             this.bpixels = new byte[i15]; 
/*  698 */           raster1.getPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height, arrayOfInt4);
/*      */           
/*  700 */           for (byte b = 0; b < i15; b++) {
/*  701 */             this.bpixels[b] = (byte)arrayOfInt4[b];
/*      */           }
/*      */           
/*  704 */           encodeRLE8(this.bpixels, i15);
/*      */         } 
/*      */       } else {
/*  707 */         raster1.getPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height, arrayOfInt4);
/*      */ 
/*      */         
/*  710 */         if (k != 1 || i16 != i7 - 1) {
/*  711 */           byte b; int i19; int i20; for (b = 0, i19 = 0, i20 = 0; b < this.w; 
/*  712 */             b++, i19 += k * i7, i20 += i7) {
/*      */             
/*  714 */             System.arraycopy(arrayOfInt4, i19, arrayOfInt5, 0, arrayOfInt5.length);
/*      */             
/*  716 */             for (byte b4 = 0; b4 < i7; b4++)
/*      */             {
/*  718 */               arrayOfInt4[i20 + b4] = arrayOfInt5[arrayOfInt1[b4]];
/*      */             }
/*      */           } 
/*      */         } 
/*  722 */         writePixels(0, i15, i, arrayOfInt4, i14, i7, indexColorModel);
/*      */       } 
/*      */ 
/*      */       
/*  726 */       processImageProgress(100.0F * b3 / this.h);
/*      */     } 
/*      */     
/*  729 */     if (this.compressionType == 2 || this.compressionType == 1) {
/*      */ 
/*      */       
/*  732 */       this.stream.writeByte(0);
/*  733 */       this.stream.writeByte(1);
/*  734 */       incCompImageSize(2);
/*      */       
/*  736 */       i12 = this.compImageSize;
/*  737 */       i10 = this.compImageSize + i11;
/*  738 */       long l1 = this.stream.getStreamPosition();
/*  739 */       this.stream.seek(l);
/*  740 */       writeSize(i10, 2);
/*  741 */       this.stream.seek(l);
/*  742 */       writeSize(i12, 34);
/*  743 */       this.stream.seek(l1);
/*      */     } 
/*      */     
/*  746 */     if (abortRequested()) {
/*  747 */       processWriteAborted();
/*      */     } else {
/*  749 */       processImageComplete();
/*  750 */       this.stream.flushBefore(this.stream.getStreamPosition());
/*      */     } 
/*      */   }
/*      */   private void writePixels(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint, int paramInt4, int paramInt5, IndexColorModel paramIndexColorModel) throws IOException {
/*      */     int j;
/*      */     byte b2;
/*      */     byte[] arrayOfByte1, arrayOfByte2, arrayOfByte3;
/*      */     byte b3;
/*  758 */     int i = 0;
/*  759 */     byte b1 = 0;
/*  760 */     switch (paramInt3) {
/*      */ 
/*      */       
/*      */       case 1:
/*  764 */         for (j = 0; j < paramInt2 / 8; j++) {
/*  765 */           this.bpixels[b1++] = (byte)(paramArrayOfint[paramInt1++] << 7 | paramArrayOfint[paramInt1++] << 6 | paramArrayOfint[paramInt1++] << 5 | paramArrayOfint[paramInt1++] << 4 | paramArrayOfint[paramInt1++] << 3 | paramArrayOfint[paramInt1++] << 2 | paramArrayOfint[paramInt1++] << 1 | paramArrayOfint[paramInt1++]);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  776 */         if (paramInt2 % 8 > 0) {
/*  777 */           i = 0;
/*  778 */           for (j = 0; j < paramInt2 % 8; j++) {
/*  779 */             i |= paramArrayOfint[paramInt1++] << 7 - j;
/*      */           }
/*  781 */           this.bpixels[b1++] = (byte)i;
/*      */         } 
/*  783 */         this.stream.write(this.bpixels, 0, (paramInt2 + 7) / 8);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  788 */         if (this.compressionType == 2) {
/*  789 */           byte[] arrayOfByte = new byte[paramInt2];
/*  790 */           for (byte b = 0; b < paramInt2; b++) {
/*  791 */             arrayOfByte[b] = (byte)paramArrayOfint[paramInt1++];
/*      */           }
/*  793 */           encodeRLE4(arrayOfByte, paramInt2); break;
/*      */         } 
/*  795 */         for (j = 0; j < paramInt2 / 2; j++) {
/*  796 */           i = paramArrayOfint[paramInt1++] << 4 | paramArrayOfint[paramInt1++];
/*  797 */           this.bpixels[b1++] = (byte)i;
/*      */         } 
/*      */         
/*  800 */         if (paramInt2 % 2 == 1) {
/*  801 */           i = paramArrayOfint[paramInt1] << 4;
/*  802 */           this.bpixels[b1++] = (byte)i;
/*      */         } 
/*  804 */         this.stream.write(this.bpixels, 0, (paramInt2 + 1) / 2);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 8:
/*  809 */         if (this.compressionType == 1) {
/*  810 */           for (j = 0; j < paramInt2; j++) {
/*  811 */             this.bpixels[j] = (byte)paramArrayOfint[paramInt1++];
/*      */           }
/*  813 */           encodeRLE8(this.bpixels, paramInt2); break;
/*      */         } 
/*  815 */         for (j = 0; j < paramInt2; j++) {
/*  816 */           this.bpixels[j] = (byte)paramArrayOfint[paramInt1++];
/*      */         }
/*  818 */         this.stream.write(this.bpixels, 0, paramInt2);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 16:
/*  823 */         if (this.spixels == null) {
/*  824 */           this.spixels = new short[paramInt2 / paramInt5];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  834 */         for (j = 0, b2 = 0; j < paramInt2; b2++) {
/*  835 */           this.spixels[b2] = 0;
/*  836 */           if (this.compressionType == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  841 */             this.spixels[b2] = (short)((0x1F & paramArrayOfint[j]) << 10 | (0x1F & paramArrayOfint[j + 1]) << 5 | 0x1F & paramArrayOfint[j + 2]);
/*      */ 
/*      */ 
/*      */             
/*  845 */             j += 3;
/*      */           } else {
/*  847 */             for (byte b = 0; b < paramInt5; b++, j++) {
/*  848 */               this.spixels[b2] = (short)(this.spixels[b2] | paramArrayOfint[j] << this.bitPos[b] & this.bitMasks[b]);
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  853 */         this.stream.writeShorts(this.spixels, 0, this.spixels.length);
/*      */         break;
/*      */       
/*      */       case 24:
/*  857 */         if (paramInt5 == 3) {
/*  858 */           for (j = 0; j < paramInt2; j += 3) {
/*      */             
/*  860 */             this.bpixels[b1++] = (byte)paramArrayOfint[paramInt1 + 2];
/*  861 */             this.bpixels[b1++] = (byte)paramArrayOfint[paramInt1 + 1];
/*  862 */             this.bpixels[b1++] = (byte)paramArrayOfint[paramInt1];
/*  863 */             paramInt1 += 3;
/*      */           } 
/*  865 */           this.stream.write(this.bpixels, 0, paramInt2);
/*      */           break;
/*      */         } 
/*  868 */         j = paramIndexColorModel.getMapSize();
/*      */         
/*  870 */         arrayOfByte1 = new byte[j];
/*  871 */         arrayOfByte2 = new byte[j];
/*  872 */         arrayOfByte3 = new byte[j];
/*      */         
/*  874 */         paramIndexColorModel.getReds(arrayOfByte1);
/*  875 */         paramIndexColorModel.getGreens(arrayOfByte2);
/*  876 */         paramIndexColorModel.getBlues(arrayOfByte3);
/*      */ 
/*      */         
/*  879 */         for (b3 = 0; b3 < paramInt2; b3++) {
/*  880 */           int k = paramArrayOfint[paramInt1];
/*  881 */           this.bpixels[b1++] = arrayOfByte3[k];
/*  882 */           this.bpixels[b1++] = arrayOfByte2[k];
/*  883 */           this.bpixels[b1++] = arrayOfByte3[k];
/*  884 */           paramInt1++;
/*      */         } 
/*  886 */         this.stream.write(this.bpixels, 0, paramInt2 * 3);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 32:
/*  891 */         if (this.ipixels == null)
/*  892 */           this.ipixels = new int[paramInt2 / paramInt5]; 
/*  893 */         if (paramInt5 == 3) {
/*      */           byte b;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  903 */           for (j = 0, b = 0; j < paramInt2; b++) {
/*  904 */             this.ipixels[b] = 0;
/*  905 */             if (this.compressionType == 0) {
/*  906 */               this.ipixels[b] = (0xFF & paramArrayOfint[j + 2]) << 16 | (0xFF & paramArrayOfint[j + 1]) << 8 | 0xFF & paramArrayOfint[j];
/*      */ 
/*      */ 
/*      */               
/*  910 */               j += 3;
/*      */             } else {
/*  912 */               for (byte b4 = 0; b4 < paramInt5; b4++, j++) {
/*  913 */                 this.ipixels[b] = this.ipixels[b] | paramArrayOfint[j] << this.bitPos[b4] & this.bitMasks[b4];
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  926 */           for (j = 0; j < paramInt2; j++) {
/*  927 */             if (paramIndexColorModel != null) {
/*  928 */               this.ipixels[j] = paramIndexColorModel.getRGB(paramArrayOfint[j]);
/*      */             } else {
/*  930 */               this.ipixels[j] = paramArrayOfint[j] << 16 | paramArrayOfint[j] << 8 | paramArrayOfint[j];
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  935 */         this.stream.writeInts(this.ipixels, 0, this.ipixels.length);
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  940 */     if (this.compressionType == 0 || this.compressionType == 3)
/*      */     {
/*      */       
/*  943 */       for (b1 = 0; b1 < paramInt4; b1++) {
/*  944 */         this.stream.writeByte(0);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void encodeRLE8(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/*  952 */     byte b = 1; byte b1 = -1, b2 = -1;
/*  953 */     byte b3 = 0, b4 = 0;
/*      */     
/*  955 */     b3 = paramArrayOfbyte[++b2];
/*  956 */     byte[] arrayOfByte = new byte[256];
/*      */     
/*  958 */     while (b2 < paramInt - 1) {
/*  959 */       b4 = paramArrayOfbyte[++b2];
/*  960 */       if (b4 == b3) {
/*  961 */         if (b1 >= 3) {
/*      */           
/*  963 */           this.stream.writeByte(0);
/*  964 */           this.stream.writeByte(b1);
/*  965 */           incCompImageSize(2);
/*  966 */           for (byte b5 = 0; b5 < b1; b5++) {
/*  967 */             this.stream.writeByte(arrayOfByte[b5]);
/*  968 */             incCompImageSize(1);
/*      */           } 
/*  970 */           if (!isEven(b1))
/*      */           {
/*  972 */             this.stream.writeByte(0);
/*  973 */             incCompImageSize(1);
/*      */           }
/*      */         
/*  976 */         } else if (b1 > -1) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  981 */           for (byte b5 = 0; b5 < b1; b5++) {
/*  982 */             this.stream.writeByte(1);
/*  983 */             this.stream.writeByte(arrayOfByte[b5]);
/*  984 */             incCompImageSize(2);
/*      */           } 
/*      */         } 
/*  987 */         b1 = -1;
/*  988 */         b++;
/*  989 */         if (b == 'Ā') {
/*      */           
/*  991 */           this.stream.writeByte(b - 1);
/*  992 */           this.stream.writeByte(b3);
/*  993 */           incCompImageSize(2);
/*  994 */           b = 1;
/*      */         } 
/*      */       } else {
/*      */         
/*  998 */         if (b > 1) {
/*      */           
/* 1000 */           this.stream.writeByte(b);
/* 1001 */           this.stream.writeByte(b3);
/* 1002 */           incCompImageSize(2);
/* 1003 */         } else if (b1 < 0) {
/*      */           
/* 1005 */           arrayOfByte[++b1] = b3;
/* 1006 */           arrayOfByte[++b1] = b4;
/* 1007 */         } else if (b1 < 254) {
/*      */           
/* 1009 */           arrayOfByte[++b1] = b4;
/*      */         } else {
/* 1011 */           this.stream.writeByte(0);
/* 1012 */           this.stream.writeByte(b1 + 1);
/* 1013 */           incCompImageSize(2);
/* 1014 */           for (byte b5 = 0; b5 <= b1; b5++) {
/* 1015 */             this.stream.writeByte(arrayOfByte[b5]);
/* 1016 */             incCompImageSize(1);
/*      */           } 
/*      */           
/* 1019 */           this.stream.writeByte(0);
/* 1020 */           incCompImageSize(1);
/* 1021 */           b1 = -1;
/*      */         } 
/* 1023 */         b3 = b4;
/* 1024 */         b = 1;
/*      */       } 
/*      */       
/* 1027 */       if (b2 == paramInt - 1) {
/*      */         
/* 1029 */         if (b1 == -1) {
/* 1030 */           this.stream.writeByte(b);
/* 1031 */           this.stream.writeByte(b3);
/* 1032 */           incCompImageSize(2);
/* 1033 */           b = 1;
/*      */ 
/*      */         
/*      */         }
/* 1037 */         else if (b1 >= 2) {
/* 1038 */           this.stream.writeByte(0);
/* 1039 */           this.stream.writeByte(b1 + 1);
/* 1040 */           incCompImageSize(2);
/* 1041 */           for (byte b5 = 0; b5 <= b1; b5++) {
/* 1042 */             this.stream.writeByte(arrayOfByte[b5]);
/* 1043 */             incCompImageSize(1);
/*      */           } 
/* 1045 */           if (!isEven(b1 + 1))
/*      */           {
/* 1047 */             this.stream.writeByte(0);
/* 1048 */             incCompImageSize(1);
/*      */           }
/*      */         
/*      */         }
/* 1052 */         else if (b1 > -1) {
/* 1053 */           for (byte b5 = 0; b5 <= b1; b5++) {
/* 1054 */             this.stream.writeByte(1);
/* 1055 */             this.stream.writeByte(arrayOfByte[b5]);
/* 1056 */             incCompImageSize(2);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1062 */         this.stream.writeByte(0);
/* 1063 */         this.stream.writeByte(0);
/* 1064 */         incCompImageSize(2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void encodeRLE4(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 1072 */     byte b1 = 2, b2 = -1, b3 = -1; int i = 0, j = 0;
/* 1073 */     byte b4 = 0, b5 = 0, b6 = 0, b7 = 0;
/* 1074 */     byte[] arrayOfByte = new byte[256];
/*      */ 
/*      */     
/* 1077 */     b4 = paramArrayOfbyte[++b3];
/* 1078 */     b5 = paramArrayOfbyte[++b3];
/*      */     
/* 1080 */     while (b3 < paramInt - 2) {
/* 1081 */       b6 = paramArrayOfbyte[++b3];
/* 1082 */       b7 = paramArrayOfbyte[++b3];
/*      */       
/* 1084 */       if (b6 == b4) {
/*      */ 
/*      */         
/* 1087 */         if (b2 >= 4) {
/* 1088 */           this.stream.writeByte(0);
/* 1089 */           this.stream.writeByte(b2 - 1);
/* 1090 */           incCompImageSize(2);
/*      */ 
/*      */           
/* 1093 */           for (byte b = 0; b < b2 - 2; b += 2) {
/* 1094 */             i = arrayOfByte[b] << 4 | arrayOfByte[b + 1];
/* 1095 */             this.stream.writeByte((byte)i);
/* 1096 */             incCompImageSize(1);
/*      */           } 
/*      */           
/* 1099 */           if (!isEven(b2 - 1)) {
/* 1100 */             j = arrayOfByte[b2 - 2] << 4 | 0x0;
/* 1101 */             this.stream.writeByte(j);
/* 1102 */             incCompImageSize(1);
/*      */           } 
/*      */           
/* 1105 */           if (!isEven((int)Math.ceil(((b2 - 1) / 2)))) {
/* 1106 */             this.stream.writeByte(0);
/* 1107 */             incCompImageSize(1);
/*      */           } 
/* 1109 */         } else if (b2 > -1) {
/* 1110 */           this.stream.writeByte(2);
/* 1111 */           i = arrayOfByte[0] << 4 | arrayOfByte[1];
/* 1112 */           this.stream.writeByte(i);
/* 1113 */           incCompImageSize(2);
/*      */         } 
/* 1115 */         b2 = -1;
/*      */         
/* 1117 */         if (b7 == b5) {
/*      */           
/* 1119 */           b1 += 2;
/* 1120 */           if (b1 == 256) {
/* 1121 */             this.stream.writeByte(b1 - 1);
/* 1122 */             i = b4 << 4 | b5;
/* 1123 */             this.stream.writeByte(i);
/* 1124 */             incCompImageSize(2);
/* 1125 */             b1 = 2;
/* 1126 */             if (b3 < paramInt - 1) {
/* 1127 */               b4 = b5;
/* 1128 */               b5 = paramArrayOfbyte[++b3];
/*      */             } else {
/* 1130 */               this.stream.writeByte(1);
/* 1131 */               int k = b5 << 4 | 0x0;
/* 1132 */               this.stream.writeByte(k);
/* 1133 */               incCompImageSize(2);
/* 1134 */               b1 = -1;
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1141 */           b1++;
/* 1142 */           i = b4 << 4 | b5;
/* 1143 */           this.stream.writeByte(b1);
/* 1144 */           this.stream.writeByte(i);
/* 1145 */           incCompImageSize(2);
/* 1146 */           b1 = 2;
/* 1147 */           b4 = b7;
/*      */           
/* 1149 */           if (b3 < paramInt - 1) {
/* 1150 */             b5 = paramArrayOfbyte[++b3];
/*      */           } else {
/* 1152 */             this.stream.writeByte(1);
/* 1153 */             int k = b7 << 4 | 0x0;
/* 1154 */             this.stream.writeByte(k);
/* 1155 */             incCompImageSize(2);
/* 1156 */             b1 = -1;
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/* 1162 */         if (b1 > 2) {
/* 1163 */           i = b4 << 4 | b5;
/* 1164 */           this.stream.writeByte(b1);
/* 1165 */           this.stream.writeByte(i);
/* 1166 */           incCompImageSize(2);
/* 1167 */         } else if (b2 < 0) {
/* 1168 */           arrayOfByte[++b2] = b4;
/* 1169 */           arrayOfByte[++b2] = b5;
/* 1170 */           arrayOfByte[++b2] = b6;
/* 1171 */           arrayOfByte[++b2] = b7;
/* 1172 */         } else if (b2 < 253) {
/* 1173 */           arrayOfByte[++b2] = b6;
/* 1174 */           arrayOfByte[++b2] = b7;
/*      */         } else {
/* 1176 */           this.stream.writeByte(0);
/* 1177 */           this.stream.writeByte(b2 + 1);
/* 1178 */           incCompImageSize(2);
/* 1179 */           for (byte b = 0; b < b2; b += 2) {
/* 1180 */             i = arrayOfByte[b] << 4 | arrayOfByte[b + 1];
/* 1181 */             this.stream.writeByte((byte)i);
/* 1182 */             incCompImageSize(1);
/*      */           } 
/*      */ 
/*      */           
/* 1186 */           this.stream.writeByte(0);
/* 1187 */           incCompImageSize(1);
/* 1188 */           b2 = -1;
/*      */         } 
/*      */         
/* 1191 */         b4 = b6;
/* 1192 */         b5 = b7;
/* 1193 */         b1 = 2;
/*      */       } 
/*      */       
/* 1196 */       if (b3 >= paramInt - 2) {
/* 1197 */         if (b2 == -1 && b1 >= 2) {
/* 1198 */           if (b3 == paramInt - 2) {
/* 1199 */             if (paramArrayOfbyte[++b3] == b4) {
/* 1200 */               b1++;
/* 1201 */               i = b4 << 4 | b5;
/* 1202 */               this.stream.writeByte(b1);
/* 1203 */               this.stream.writeByte(i);
/* 1204 */               incCompImageSize(2);
/*      */             } else {
/* 1206 */               i = b4 << 4 | b5;
/* 1207 */               this.stream.writeByte(b1);
/* 1208 */               this.stream.writeByte(i);
/* 1209 */               this.stream.writeByte(1);
/* 1210 */               i = paramArrayOfbyte[b3] << 4 | 0x0;
/* 1211 */               this.stream.writeByte(i);
/* 1212 */               int k = paramArrayOfbyte[b3] << 4 | 0x0;
/* 1213 */               incCompImageSize(4);
/*      */             } 
/*      */           } else {
/* 1216 */             this.stream.writeByte(b1);
/* 1217 */             i = b4 << 4 | b5;
/* 1218 */             this.stream.writeByte(i);
/* 1219 */             incCompImageSize(2);
/*      */           } 
/* 1221 */         } else if (b2 > -1) {
/* 1222 */           if (b3 == paramInt - 2) {
/* 1223 */             arrayOfByte[++b2] = paramArrayOfbyte[++b3];
/*      */           }
/* 1225 */           if (b2 >= 2) {
/* 1226 */             this.stream.writeByte(0);
/* 1227 */             this.stream.writeByte(b2 + 1);
/* 1228 */             incCompImageSize(2);
/* 1229 */             for (byte b = 0; b < b2; b += 2) {
/* 1230 */               i = arrayOfByte[b] << 4 | arrayOfByte[b + 1];
/* 1231 */               this.stream.writeByte((byte)i);
/* 1232 */               incCompImageSize(1);
/*      */             } 
/* 1234 */             if (!isEven(b2 + 1)) {
/* 1235 */               j = arrayOfByte[b2] << 4 | 0x0;
/* 1236 */               this.stream.writeByte(j);
/* 1237 */               incCompImageSize(1);
/*      */             } 
/*      */ 
/*      */             
/* 1241 */             if (!isEven((int)Math.ceil(((b2 + 1) / 2)))) {
/* 1242 */               this.stream.writeByte(0);
/* 1243 */               incCompImageSize(1);
/*      */             } 
/*      */           } else {
/*      */             int k;
/* 1247 */             switch (b2) {
/*      */               case 0:
/* 1249 */                 this.stream.writeByte(1);
/* 1250 */                 k = arrayOfByte[0] << 4 | 0x0;
/* 1251 */                 this.stream.writeByte(k);
/* 1252 */                 incCompImageSize(2);
/*      */                 break;
/*      */               case 1:
/* 1255 */                 this.stream.writeByte(2);
/* 1256 */                 i = arrayOfByte[0] << 4 | arrayOfByte[1];
/* 1257 */                 this.stream.writeByte(i);
/* 1258 */                 incCompImageSize(2);
/*      */                 break;
/*      */             } 
/*      */           
/*      */           } 
/*      */         } 
/* 1264 */         this.stream.writeByte(0);
/* 1265 */         this.stream.writeByte(0);
/* 1266 */         incCompImageSize(2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized void incCompImageSize(int paramInt) {
/* 1273 */     this.compImageSize += paramInt;
/*      */   }
/*      */   
/*      */   private boolean isEven(int paramInt) {
/* 1277 */     return (paramInt % 2 == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFileHeader(int paramInt1, int paramInt2) throws IOException {
/* 1282 */     this.stream.writeByte(66);
/* 1283 */     this.stream.writeByte(77);
/*      */ 
/*      */     
/* 1286 */     this.stream.writeInt(paramInt1);
/*      */ 
/*      */     
/* 1289 */     this.stream.writeInt(0);
/*      */ 
/*      */     
/* 1292 */     this.stream.writeInt(paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeInfoHeader(int paramInt1, int paramInt2) throws IOException {
/* 1299 */     this.stream.writeInt(paramInt1);
/*      */ 
/*      */     
/* 1302 */     this.stream.writeInt(this.w);
/*      */ 
/*      */     
/* 1305 */     this.stream.writeInt(this.isTopDown ? -this.h : this.h);
/*      */ 
/*      */     
/* 1308 */     this.stream.writeShort(1);
/*      */ 
/*      */     
/* 1311 */     this.stream.writeShort(paramInt2);
/*      */   }
/*      */   
/*      */   private void writeSize(int paramInt1, int paramInt2) throws IOException {
/* 1315 */     this.stream.skipBytes(paramInt2);
/* 1316 */     this.stream.writeInt(paramInt1);
/*      */   }
/*      */   
/*      */   public void reset() {
/* 1320 */     super.reset();
/* 1321 */     this.stream = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeEmbedded(IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/* 1326 */     String str = (this.compressionType == 4) ? "jpeg" : "png";
/*      */     
/* 1328 */     Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(str);
/* 1329 */     ImageWriter imageWriter = null;
/* 1330 */     if (iterator.hasNext())
/* 1331 */       imageWriter = iterator.next(); 
/* 1332 */     if (imageWriter != null) {
/* 1333 */       if (this.embedded_stream == null) {
/* 1334 */         throw new RuntimeException("No stream for writing embedded image!");
/*      */       }
/*      */       
/* 1337 */       imageWriter.addIIOWriteProgressListener(new IIOWriteProgressAdapter() {
/*      */             public void imageProgress(ImageWriter param1ImageWriter, float param1Float) {
/* 1339 */               BMPImageWriter.this.processImageProgress(param1Float);
/*      */             }
/*      */           });
/*      */       
/* 1343 */       imageWriter.addIIOWriteWarningListener(new IIOWriteWarningListener() {
/*      */             public void warningOccurred(ImageWriter param1ImageWriter, int param1Int, String param1String) {
/* 1345 */               BMPImageWriter.this.processWarningOccurred(param1Int, param1String);
/*      */             }
/*      */           });
/*      */       
/* 1349 */       imageWriter.setOutput(ImageIO.createImageOutputStream(this.embedded_stream));
/* 1350 */       ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
/*      */       
/* 1352 */       imageWriteParam.setDestinationOffset(paramImageWriteParam.getDestinationOffset());
/* 1353 */       imageWriteParam.setSourceBands(paramImageWriteParam.getSourceBands());
/* 1354 */       imageWriteParam.setSourceRegion(paramImageWriteParam.getSourceRegion());
/* 1355 */       imageWriteParam.setSourceSubsampling(paramImageWriteParam.getSourceXSubsampling(), paramImageWriteParam
/* 1356 */           .getSourceYSubsampling(), paramImageWriteParam
/* 1357 */           .getSubsamplingXOffset(), paramImageWriteParam
/* 1358 */           .getSubsamplingYOffset());
/* 1359 */       imageWriter.write(null, paramIIOImage, imageWriteParam);
/*      */     } else {
/* 1361 */       throw new RuntimeException(I18N.getString("BMPImageWrite5") + " " + str);
/*      */     } 
/*      */   }
/*      */   
/*      */   private int firstLowBit(int paramInt) {
/* 1366 */     byte b = 0;
/* 1367 */     while ((paramInt & 0x1) == 0) {
/* 1368 */       b++;
/* 1369 */       paramInt >>>= 1;
/*      */     } 
/* 1371 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class IIOWriteProgressAdapter
/*      */     implements IIOWriteProgressListener
/*      */   {
/*      */     private IIOWriteProgressAdapter() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void imageComplete(ImageWriter param1ImageWriter) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void imageProgress(ImageWriter param1ImageWriter, float param1Float) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void imageStarted(ImageWriter param1ImageWriter, int param1Int) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void thumbnailComplete(ImageWriter param1ImageWriter) {}
/*      */ 
/*      */     
/*      */     public void thumbnailProgress(ImageWriter param1ImageWriter, float param1Float) {}
/*      */ 
/*      */     
/*      */     public void thumbnailStarted(ImageWriter param1ImageWriter, int param1Int1, int param1Int2) {}
/*      */ 
/*      */     
/*      */     public void writeAborted(ImageWriter param1ImageWriter) {}
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getPreferredCompressionType(ColorModel paramColorModel, SampleModel paramSampleModel) {
/* 1409 */     ImageTypeSpecifier imageTypeSpecifier = new ImageTypeSpecifier(paramColorModel, paramSampleModel);
/* 1410 */     return getPreferredCompressionType(imageTypeSpecifier);
/*      */   }
/*      */   
/*      */   protected int getPreferredCompressionType(ImageTypeSpecifier paramImageTypeSpecifier) {
/* 1414 */     if (paramImageTypeSpecifier.getBufferedImageType() == 8) {
/* 1415 */       return 3;
/*      */     }
/* 1417 */     return 0;
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
/*      */   protected boolean canEncodeImage(int paramInt, ColorModel paramColorModel, SampleModel paramSampleModel) {
/* 1429 */     ImageTypeSpecifier imageTypeSpecifier = new ImageTypeSpecifier(paramColorModel, paramSampleModel);
/* 1430 */     return canEncodeImage(paramInt, imageTypeSpecifier);
/*      */   }
/*      */   
/*      */   protected boolean canEncodeImage(int paramInt, ImageTypeSpecifier paramImageTypeSpecifier) {
/* 1434 */     ImageWriterSpi imageWriterSpi = getOriginatingProvider();
/* 1435 */     if (!imageWriterSpi.canEncodeImage(paramImageTypeSpecifier)) {
/* 1436 */       return false;
/*      */     }
/* 1438 */     int i = paramImageTypeSpecifier.getBufferedImageType();
/* 1439 */     int j = paramImageTypeSpecifier.getColorModel().getPixelSize();
/* 1440 */     if (this.compressionType == 2 && j != 4)
/*      */     {
/* 1442 */       return false;
/*      */     }
/* 1444 */     if (this.compressionType == 1 && j != 8)
/*      */     {
/* 1446 */       return false;
/*      */     }
/* 1448 */     if (j == 16) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1475 */       int k = 0;
/* 1476 */       int m = 0;
/*      */       
/* 1478 */       SampleModel sampleModel = paramImageTypeSpecifier.getSampleModel();
/* 1479 */       if (sampleModel instanceof SinglePixelPackedSampleModel) {
/*      */         
/* 1481 */         int[] arrayOfInt = ((SinglePixelPackedSampleModel)sampleModel).getSampleSize();
/*      */         
/* 1483 */         k = 1;
/* 1484 */         m = 1;
/* 1485 */         for (byte b = 0; b < arrayOfInt.length; b++) {
/* 1486 */           k &= (arrayOfInt[b] == 5) ? 1 : 0;
/* 1487 */           m &= (arrayOfInt[b] == 5 || (b == 1 && arrayOfInt[b] == 6)) ? 1 : 0;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1492 */       return ((this.compressionType == 0 && k != 0) || (this.compressionType == 3 && m != 0));
/*      */     } 
/*      */     
/* 1495 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeMaskToPalette(int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) {
/* 1500 */     paramArrayOfbyte3[paramInt2] = (byte)(0xFF & paramInt1 >> 24);
/* 1501 */     paramArrayOfbyte2[paramInt2] = (byte)(0xFF & paramInt1 >> 16);
/* 1502 */     paramArrayOfbyte1[paramInt2] = (byte)(0xFF & paramInt1 >> 8);
/* 1503 */     paramArrayOfbyte4[paramInt2] = (byte)(0xFF & paramInt1);
/*      */   }
/*      */   
/*      */   private int roundBpp(int paramInt) {
/* 1507 */     if (paramInt <= 8)
/* 1508 */       return 8; 
/* 1509 */     if (paramInt <= 16)
/* 1510 */       return 16; 
/* 1511 */     if (paramInt <= 24) {
/* 1512 */       return 24;
/*      */     }
/* 1514 */     return 32;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/bmp/BMPImageWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */