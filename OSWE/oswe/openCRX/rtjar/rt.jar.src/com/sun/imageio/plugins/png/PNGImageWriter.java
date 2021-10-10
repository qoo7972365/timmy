/*      */ package com.sun.imageio.plugins.png;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.util.Iterator;
/*      */ import java.util.zip.DeflaterOutputStream;
/*      */ import javax.imageio.IIOException;
/*      */ import javax.imageio.IIOImage;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.ImageWriteParam;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.metadata.IIOMetadata;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PNGImageWriter
/*      */   extends ImageWriter
/*      */ {
/*  280 */   ImageOutputStream stream = null;
/*      */   
/*  282 */   PNGMetadata metadata = null;
/*      */ 
/*      */   
/*  285 */   int sourceXOffset = 0;
/*  286 */   int sourceYOffset = 0;
/*  287 */   int sourceWidth = 0;
/*  288 */   int sourceHeight = 0;
/*  289 */   int[] sourceBands = null;
/*  290 */   int periodX = 1;
/*  291 */   int periodY = 1;
/*      */   
/*      */   int numBands;
/*      */   
/*      */   int bpp;
/*  296 */   RowFilter rowFilter = new RowFilter();
/*  297 */   byte[] prevRow = null;
/*  298 */   byte[] currRow = null;
/*  299 */   byte[][] filteredRows = (byte[][])null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  309 */   int[] sampleSize = null;
/*  310 */   int scalingBitDepth = -1;
/*      */ 
/*      */   
/*  313 */   byte[][] scale = (byte[][])null;
/*  314 */   byte[] scale0 = null;
/*      */ 
/*      */   
/*  317 */   byte[][] scaleh = (byte[][])null;
/*  318 */   byte[][] scalel = (byte[][])null;
/*      */   
/*      */   int totalPixels;
/*      */   int pixelsDone;
/*      */   
/*      */   public PNGImageWriter(ImageWriterSpi paramImageWriterSpi) {
/*  324 */     super(paramImageWriterSpi);
/*      */   }
/*      */   
/*      */   public void setOutput(Object paramObject) {
/*  328 */     super.setOutput(paramObject);
/*  329 */     if (paramObject != null) {
/*  330 */       if (!(paramObject instanceof ImageOutputStream)) {
/*  331 */         throw new IllegalArgumentException("output not an ImageOutputStream!");
/*      */       }
/*  333 */       this.stream = (ImageOutputStream)paramObject;
/*      */     } else {
/*  335 */       this.stream = null;
/*      */     } 
/*      */   }
/*      */   
/*  339 */   private static int[] allowedProgressivePasses = new int[] { 1, 7 };
/*      */   
/*      */   public ImageWriteParam getDefaultWriteParam() {
/*  342 */     return new PNGImageWriteParam(getLocale());
/*      */   }
/*      */   
/*      */   public IIOMetadata getDefaultStreamMetadata(ImageWriteParam paramImageWriteParam) {
/*  346 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/*  351 */     PNGMetadata pNGMetadata = new PNGMetadata();
/*  352 */     pNGMetadata.initialize(paramImageTypeSpecifier, paramImageTypeSpecifier.getSampleModel().getNumBands());
/*  353 */     return pNGMetadata;
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadata convertStreamMetadata(IIOMetadata paramIIOMetadata, ImageWriteParam paramImageWriteParam) {
/*  358 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOMetadata convertImageMetadata(IIOMetadata paramIIOMetadata, ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/*  365 */     if (paramIIOMetadata instanceof PNGMetadata) {
/*  366 */       return (PNGMetadata)((PNGMetadata)paramIIOMetadata).clone();
/*      */     }
/*  368 */     return new PNGMetadata(paramIIOMetadata);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void write_magic() throws IOException {
/*  374 */     byte[] arrayOfByte = { -119, 80, 78, 71, 13, 10, 26, 10 };
/*  375 */     this.stream.write(arrayOfByte);
/*      */   }
/*      */ 
/*      */   
/*      */   private void write_IHDR() throws IOException {
/*  380 */     ChunkStream chunkStream = new ChunkStream(1229472850, this.stream);
/*  381 */     chunkStream.writeInt(this.metadata.IHDR_width);
/*  382 */     chunkStream.writeInt(this.metadata.IHDR_height);
/*  383 */     chunkStream.writeByte(this.metadata.IHDR_bitDepth);
/*  384 */     chunkStream.writeByte(this.metadata.IHDR_colorType);
/*  385 */     if (this.metadata.IHDR_compressionMethod != 0) {
/*  386 */       throw new IIOException("Only compression method 0 is defined in PNG 1.1");
/*      */     }
/*      */     
/*  389 */     chunkStream.writeByte(this.metadata.IHDR_compressionMethod);
/*  390 */     if (this.metadata.IHDR_filterMethod != 0) {
/*  391 */       throw new IIOException("Only filter method 0 is defined in PNG 1.1");
/*      */     }
/*      */     
/*  394 */     chunkStream.writeByte(this.metadata.IHDR_filterMethod);
/*  395 */     if (this.metadata.IHDR_interlaceMethod < 0 || this.metadata.IHDR_interlaceMethod > 1)
/*      */     {
/*  397 */       throw new IIOException("Only interlace methods 0 (node) and 1 (adam7) are defined in PNG 1.1");
/*      */     }
/*      */     
/*  400 */     chunkStream.writeByte(this.metadata.IHDR_interlaceMethod);
/*  401 */     chunkStream.finish();
/*      */   }
/*      */   
/*      */   private void write_cHRM() throws IOException {
/*  405 */     if (this.metadata.cHRM_present) {
/*  406 */       ChunkStream chunkStream = new ChunkStream(1665684045, this.stream);
/*  407 */       chunkStream.writeInt(this.metadata.cHRM_whitePointX);
/*  408 */       chunkStream.writeInt(this.metadata.cHRM_whitePointY);
/*  409 */       chunkStream.writeInt(this.metadata.cHRM_redX);
/*  410 */       chunkStream.writeInt(this.metadata.cHRM_redY);
/*  411 */       chunkStream.writeInt(this.metadata.cHRM_greenX);
/*  412 */       chunkStream.writeInt(this.metadata.cHRM_greenY);
/*  413 */       chunkStream.writeInt(this.metadata.cHRM_blueX);
/*  414 */       chunkStream.writeInt(this.metadata.cHRM_blueY);
/*  415 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_gAMA() throws IOException {
/*  420 */     if (this.metadata.gAMA_present) {
/*  421 */       ChunkStream chunkStream = new ChunkStream(1732332865, this.stream);
/*  422 */       chunkStream.writeInt(this.metadata.gAMA_gamma);
/*  423 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_iCCP() throws IOException {
/*  428 */     if (this.metadata.iCCP_present) {
/*  429 */       ChunkStream chunkStream = new ChunkStream(1766015824, this.stream);
/*  430 */       chunkStream.writeBytes(this.metadata.iCCP_profileName);
/*  431 */       chunkStream.writeByte(0);
/*      */       
/*  433 */       chunkStream.writeByte(this.metadata.iCCP_compressionMethod);
/*  434 */       chunkStream.write(this.metadata.iCCP_compressedProfile);
/*  435 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_sBIT() throws IOException {
/*  440 */     if (this.metadata.sBIT_present) {
/*  441 */       ChunkStream chunkStream = new ChunkStream(1933723988, this.stream);
/*  442 */       int i = this.metadata.IHDR_colorType;
/*  443 */       if (this.metadata.sBIT_colorType != i) {
/*  444 */         processWarningOccurred(0, "sBIT metadata has wrong color type.\nThe chunk will not be written.");
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  450 */       if (i == 0 || i == 4) {
/*      */         
/*  452 */         chunkStream.writeByte(this.metadata.sBIT_grayBits);
/*  453 */       } else if (i == 2 || i == 3 || i == 6) {
/*      */ 
/*      */         
/*  456 */         chunkStream.writeByte(this.metadata.sBIT_redBits);
/*  457 */         chunkStream.writeByte(this.metadata.sBIT_greenBits);
/*  458 */         chunkStream.writeByte(this.metadata.sBIT_blueBits);
/*      */       } 
/*      */       
/*  461 */       if (i == 4 || i == 6)
/*      */       {
/*  463 */         chunkStream.writeByte(this.metadata.sBIT_alphaBits);
/*      */       }
/*  465 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_sRGB() throws IOException {
/*  470 */     if (this.metadata.sRGB_present) {
/*  471 */       ChunkStream chunkStream = new ChunkStream(1934772034, this.stream);
/*  472 */       chunkStream.writeByte(this.metadata.sRGB_renderingIntent);
/*  473 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_PLTE() throws IOException {
/*  478 */     if (this.metadata.PLTE_present) {
/*  479 */       if (this.metadata.IHDR_colorType == 0 || this.metadata.IHDR_colorType == 4) {
/*      */ 
/*      */ 
/*      */         
/*  483 */         processWarningOccurred(0, "A PLTE chunk may not appear in a gray or gray alpha image.\nThe chunk will not be written");
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  489 */       ChunkStream chunkStream = new ChunkStream(1347179589, this.stream);
/*      */       
/*  491 */       int i = this.metadata.PLTE_red.length;
/*  492 */       byte[] arrayOfByte = new byte[i * 3];
/*  493 */       byte b1 = 0;
/*  494 */       for (byte b2 = 0; b2 < i; b2++) {
/*  495 */         arrayOfByte[b1++] = this.metadata.PLTE_red[b2];
/*  496 */         arrayOfByte[b1++] = this.metadata.PLTE_green[b2];
/*  497 */         arrayOfByte[b1++] = this.metadata.PLTE_blue[b2];
/*      */       } 
/*      */       
/*  500 */       chunkStream.write(arrayOfByte);
/*  501 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_hIST() throws IOException, IIOException {
/*  506 */     if (this.metadata.hIST_present) {
/*  507 */       ChunkStream chunkStream = new ChunkStream(1749635924, this.stream);
/*      */       
/*  509 */       if (!this.metadata.PLTE_present) {
/*  510 */         throw new IIOException("hIST chunk without PLTE chunk!");
/*      */       }
/*      */       
/*  513 */       chunkStream.writeChars(this.metadata.hIST_histogram, 0, this.metadata.hIST_histogram.length);
/*      */       
/*  515 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_tRNS() throws IOException, IIOException {
/*  520 */     if (this.metadata.tRNS_present) {
/*  521 */       ChunkStream chunkStream = new ChunkStream(1951551059, this.stream);
/*  522 */       int i = this.metadata.IHDR_colorType;
/*  523 */       int j = this.metadata.tRNS_colorType;
/*      */ 
/*      */ 
/*      */       
/*  527 */       int k = this.metadata.tRNS_red;
/*  528 */       int m = this.metadata.tRNS_green;
/*  529 */       int n = this.metadata.tRNS_blue;
/*  530 */       if (i == 2 && j == 0) {
/*      */         
/*  532 */         j = i;
/*  533 */         k = m = n = this.metadata.tRNS_gray;
/*      */       } 
/*      */ 
/*      */       
/*  537 */       if (j != i) {
/*  538 */         processWarningOccurred(0, "tRNS metadata has incompatible color type.\nThe chunk will not be written.");
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  544 */       if (i == 3) {
/*  545 */         if (!this.metadata.PLTE_present) {
/*  546 */           throw new IIOException("tRNS chunk without PLTE chunk!");
/*      */         }
/*  548 */         chunkStream.write(this.metadata.tRNS_alpha);
/*  549 */       } else if (i == 0) {
/*  550 */         chunkStream.writeShort(this.metadata.tRNS_gray);
/*  551 */       } else if (i == 2) {
/*  552 */         chunkStream.writeShort(k);
/*  553 */         chunkStream.writeShort(m);
/*  554 */         chunkStream.writeShort(n);
/*      */       } else {
/*  556 */         throw new IIOException("tRNS chunk for color type 4 or 6!");
/*      */       } 
/*  558 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_bKGD() throws IOException {
/*  563 */     if (this.metadata.bKGD_present) {
/*  564 */       ChunkStream chunkStream = new ChunkStream(1649100612, this.stream);
/*  565 */       int i = this.metadata.IHDR_colorType & 0x3;
/*  566 */       int j = this.metadata.bKGD_colorType;
/*      */ 
/*      */ 
/*      */       
/*  570 */       int k = this.metadata.bKGD_red;
/*  571 */       int m = this.metadata.bKGD_red;
/*  572 */       int n = this.metadata.bKGD_red;
/*  573 */       if (i == 2 && j == 0) {
/*      */ 
/*      */         
/*  576 */         j = i;
/*  577 */         k = m = n = this.metadata.bKGD_gray;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  582 */       if (j != i) {
/*  583 */         processWarningOccurred(0, "bKGD metadata has incompatible color type.\nThe chunk will not be written.");
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  589 */       if (i == 3) {
/*  590 */         chunkStream.writeByte(this.metadata.bKGD_index);
/*  591 */       } else if (i == 0 || i == 4) {
/*      */         
/*  593 */         chunkStream.writeShort(this.metadata.bKGD_gray);
/*      */       } else {
/*      */         
/*  596 */         chunkStream.writeShort(k);
/*  597 */         chunkStream.writeShort(m);
/*  598 */         chunkStream.writeShort(n);
/*      */       } 
/*  600 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_pHYs() throws IOException {
/*  605 */     if (this.metadata.pHYs_present) {
/*  606 */       ChunkStream chunkStream = new ChunkStream(1883789683, this.stream);
/*  607 */       chunkStream.writeInt(this.metadata.pHYs_pixelsPerUnitXAxis);
/*  608 */       chunkStream.writeInt(this.metadata.pHYs_pixelsPerUnitYAxis);
/*  609 */       chunkStream.writeByte(this.metadata.pHYs_unitSpecifier);
/*  610 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_sPLT() throws IOException {
/*  615 */     if (this.metadata.sPLT_present) {
/*  616 */       ChunkStream chunkStream = new ChunkStream(1934642260, this.stream);
/*      */       
/*  618 */       chunkStream.writeBytes(this.metadata.sPLT_paletteName);
/*  619 */       chunkStream.writeByte(0);
/*      */       
/*  621 */       chunkStream.writeByte(this.metadata.sPLT_sampleDepth);
/*  622 */       int i = this.metadata.sPLT_red.length;
/*      */       
/*  624 */       if (this.metadata.sPLT_sampleDepth == 8) {
/*  625 */         for (byte b = 0; b < i; b++) {
/*  626 */           chunkStream.writeByte(this.metadata.sPLT_red[b]);
/*  627 */           chunkStream.writeByte(this.metadata.sPLT_green[b]);
/*  628 */           chunkStream.writeByte(this.metadata.sPLT_blue[b]);
/*  629 */           chunkStream.writeByte(this.metadata.sPLT_alpha[b]);
/*  630 */           chunkStream.writeShort(this.metadata.sPLT_frequency[b]);
/*      */         } 
/*      */       } else {
/*  633 */         for (byte b = 0; b < i; b++) {
/*  634 */           chunkStream.writeShort(this.metadata.sPLT_red[b]);
/*  635 */           chunkStream.writeShort(this.metadata.sPLT_green[b]);
/*  636 */           chunkStream.writeShort(this.metadata.sPLT_blue[b]);
/*  637 */           chunkStream.writeShort(this.metadata.sPLT_alpha[b]);
/*  638 */           chunkStream.writeShort(this.metadata.sPLT_frequency[b]);
/*      */         } 
/*      */       } 
/*  641 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_tIME() throws IOException {
/*  646 */     if (this.metadata.tIME_present) {
/*  647 */       ChunkStream chunkStream = new ChunkStream(1950960965, this.stream);
/*  648 */       chunkStream.writeShort(this.metadata.tIME_year);
/*  649 */       chunkStream.writeByte(this.metadata.tIME_month);
/*  650 */       chunkStream.writeByte(this.metadata.tIME_day);
/*  651 */       chunkStream.writeByte(this.metadata.tIME_hour);
/*  652 */       chunkStream.writeByte(this.metadata.tIME_minute);
/*  653 */       chunkStream.writeByte(this.metadata.tIME_second);
/*  654 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_tEXt() throws IOException {
/*  659 */     Iterator<String> iterator1 = this.metadata.tEXt_keyword.iterator();
/*  660 */     Iterator<String> iterator2 = this.metadata.tEXt_text.iterator();
/*      */     
/*  662 */     while (iterator1.hasNext()) {
/*  663 */       ChunkStream chunkStream = new ChunkStream(1950701684, this.stream);
/*  664 */       String str1 = iterator1.next();
/*  665 */       chunkStream.writeBytes(str1);
/*  666 */       chunkStream.writeByte(0);
/*      */       
/*  668 */       String str2 = iterator2.next();
/*  669 */       chunkStream.writeBytes(str2);
/*  670 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private byte[] deflate(byte[] paramArrayOfbyte) throws IOException {
/*  675 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*  676 */     DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
/*  677 */     deflaterOutputStream.write(paramArrayOfbyte);
/*  678 */     deflaterOutputStream.close();
/*  679 */     return byteArrayOutputStream.toByteArray();
/*      */   }
/*      */   
/*      */   private void write_iTXt() throws IOException {
/*  683 */     Iterator<String> iterator1 = this.metadata.iTXt_keyword.iterator();
/*  684 */     Iterator<Boolean> iterator = this.metadata.iTXt_compressionFlag.iterator();
/*  685 */     Iterator<Integer> iterator2 = this.metadata.iTXt_compressionMethod.iterator();
/*  686 */     Iterator<String> iterator3 = this.metadata.iTXt_languageTag.iterator();
/*      */     
/*  688 */     Iterator<String> iterator4 = this.metadata.iTXt_translatedKeyword.iterator();
/*  689 */     Iterator<String> iterator5 = this.metadata.iTXt_text.iterator();
/*      */     
/*  691 */     while (iterator1.hasNext()) {
/*  692 */       ChunkStream chunkStream = new ChunkStream(1767135348, this.stream);
/*      */       
/*  694 */       chunkStream.writeBytes(iterator1.next());
/*  695 */       chunkStream.writeByte(0);
/*      */       
/*  697 */       Boolean bool = iterator.next();
/*  698 */       chunkStream.writeByte(bool.booleanValue() ? 1 : 0);
/*      */       
/*  700 */       chunkStream.writeByte(((Integer)iterator2.next()).intValue());
/*      */       
/*  702 */       chunkStream.writeBytes(iterator3.next());
/*  703 */       chunkStream.writeByte(0);
/*      */ 
/*      */       
/*  706 */       chunkStream.write(((String)iterator4.next()).getBytes("UTF8"));
/*  707 */       chunkStream.writeByte(0);
/*      */       
/*  709 */       String str = iterator5.next();
/*  710 */       if (bool.booleanValue()) {
/*  711 */         chunkStream.write(deflate(str.getBytes("UTF8")));
/*      */       } else {
/*  713 */         chunkStream.write(str.getBytes("UTF8"));
/*      */       } 
/*  715 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write_zTXt() throws IOException {
/*  720 */     Iterator<String> iterator1 = this.metadata.zTXt_keyword.iterator();
/*  721 */     Iterator<Integer> iterator = this.metadata.zTXt_compressionMethod.iterator();
/*  722 */     Iterator<String> iterator2 = this.metadata.zTXt_text.iterator();
/*      */     
/*  724 */     while (iterator1.hasNext()) {
/*  725 */       ChunkStream chunkStream = new ChunkStream(2052348020, this.stream);
/*  726 */       String str1 = iterator1.next();
/*  727 */       chunkStream.writeBytes(str1);
/*  728 */       chunkStream.writeByte(0);
/*      */       
/*  730 */       int i = ((Integer)iterator.next()).intValue();
/*  731 */       chunkStream.writeByte(i);
/*      */       
/*  733 */       String str2 = iterator2.next();
/*  734 */       chunkStream.write(deflate(str2.getBytes("ISO-8859-1")));
/*  735 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeUnknownChunks() throws IOException {
/*  740 */     Iterator<String> iterator = this.metadata.unknownChunkType.iterator();
/*  741 */     Iterator<byte> iterator1 = this.metadata.unknownChunkData.iterator();
/*      */     
/*  743 */     while (iterator.hasNext() && iterator1.hasNext()) {
/*  744 */       String str = iterator.next();
/*  745 */       ChunkStream chunkStream = new ChunkStream(chunkType(str), this.stream);
/*  746 */       byte[] arrayOfByte = (byte[])iterator1.next();
/*  747 */       chunkStream.write(arrayOfByte);
/*  748 */       chunkStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int chunkType(String paramString) {
/*  753 */     char c1 = paramString.charAt(0);
/*  754 */     char c2 = paramString.charAt(1);
/*  755 */     char c3 = paramString.charAt(2);
/*  756 */     char c4 = paramString.charAt(3);
/*      */     
/*  758 */     return c1 << 24 | c2 << 16 | c3 << 8 | c4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void encodePass(ImageOutputStream paramImageOutputStream, RenderedImage paramRenderedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IOException {
/*  766 */     int i = this.sourceXOffset;
/*  767 */     int j = this.sourceYOffset;
/*  768 */     int k = this.sourceWidth;
/*  769 */     int m = this.sourceHeight;
/*      */ 
/*      */     
/*  772 */     paramInt1 *= this.periodX;
/*  773 */     paramInt3 *= this.periodX;
/*  774 */     paramInt2 *= this.periodY;
/*  775 */     paramInt4 *= this.periodY;
/*      */ 
/*      */     
/*  778 */     int n = (k - paramInt1 + paramInt3 - 1) / paramInt3;
/*  779 */     int i1 = (m - paramInt2 + paramInt4 - 1) / paramInt4;
/*  780 */     if (n == 0 || i1 == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  785 */     paramInt1 *= this.numBands;
/*  786 */     paramInt3 *= this.numBands;
/*      */ 
/*      */     
/*  789 */     int i2 = 8 / this.metadata.IHDR_bitDepth;
/*  790 */     int i3 = k * this.numBands;
/*  791 */     int[] arrayOfInt = new int[i3];
/*      */     
/*  793 */     int i4 = n * this.numBands;
/*  794 */     if (this.metadata.IHDR_bitDepth < 8) {
/*  795 */       i4 = (i4 + i2 - 1) / i2;
/*  796 */     } else if (this.metadata.IHDR_bitDepth == 16) {
/*  797 */       i4 *= 2;
/*      */     } 
/*      */     
/*  800 */     IndexColorModel indexColorModel = null;
/*  801 */     if (this.metadata.IHDR_colorType == 4 && paramRenderedImage
/*  802 */       .getColorModel() instanceof IndexColorModel) {
/*      */ 
/*      */       
/*  805 */       i4 *= 2;
/*      */ 
/*      */       
/*  808 */       indexColorModel = (IndexColorModel)paramRenderedImage.getColorModel();
/*      */     } 
/*      */     
/*  811 */     this.currRow = new byte[i4 + this.bpp];
/*  812 */     this.prevRow = new byte[i4 + this.bpp];
/*  813 */     this.filteredRows = new byte[5][i4 + this.bpp];
/*      */     
/*  815 */     int i5 = this.metadata.IHDR_bitDepth; int i6;
/*  816 */     for (i6 = j + paramInt2; i6 < j + m; i6 += paramInt4) {
/*  817 */       int i10; Rectangle rectangle = new Rectangle(i, i6, k, 1);
/*  818 */       Raster raster = paramRenderedImage.getData(rectangle);
/*  819 */       if (this.sourceBands != null) {
/*  820 */         raster = raster.createChild(i, i6, k, 1, i, i6, this.sourceBands);
/*      */       }
/*      */ 
/*      */       
/*  824 */       raster.getPixels(i, i6, k, 1, arrayOfInt);
/*      */       
/*  826 */       if (paramRenderedImage.getColorModel().isAlphaPremultiplied()) {
/*  827 */         WritableRaster writableRaster = raster.createCompatibleWritableRaster();
/*  828 */         writableRaster.setPixels(writableRaster.getMinX(), writableRaster.getMinY(), writableRaster
/*  829 */             .getWidth(), writableRaster.getHeight(), arrayOfInt);
/*      */ 
/*      */         
/*  832 */         paramRenderedImage.getColorModel().coerceData(writableRaster, false);
/*  833 */         writableRaster.getPixels(writableRaster.getMinX(), writableRaster.getMinY(), writableRaster
/*  834 */             .getWidth(), writableRaster.getHeight(), arrayOfInt);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  839 */       int[] arrayOfInt1 = this.metadata.PLTE_order;
/*  840 */       if (arrayOfInt1 != null) {
/*  841 */         for (byte b1 = 0; b1 < i3; b1++) {
/*  842 */           arrayOfInt[b1] = arrayOfInt1[arrayOfInt[b1]];
/*      */         }
/*      */       }
/*      */       
/*  846 */       int i7 = this.bpp;
/*  847 */       byte b = 0;
/*  848 */       int i8 = 0;
/*      */       
/*  850 */       switch (i5) {
/*      */         case 1:
/*      */         case 2:
/*      */         case 4:
/*  854 */           i9 = i2 - 1;
/*  855 */           for (i10 = paramInt1; i10 < i3; i10 += paramInt3) {
/*  856 */             byte b1 = this.scale0[arrayOfInt[i10]];
/*  857 */             i8 = i8 << i5 | b1;
/*      */             
/*  859 */             if ((b++ & i9) == i9) {
/*  860 */               this.currRow[i7++] = (byte)i8;
/*  861 */               i8 = 0;
/*  862 */               b = 0;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  867 */           if ((b & i9) != 0) {
/*  868 */             i8 <<= (8 / i5 - b) * i5;
/*  869 */             this.currRow[i7++] = (byte)i8;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 8:
/*  874 */           if (this.numBands == 1) {
/*  875 */             for (i10 = paramInt1; i10 < i3; i10 += paramInt3) {
/*  876 */               this.currRow[i7++] = this.scale0[arrayOfInt[i10]];
/*  877 */               if (indexColorModel != null)
/*  878 */                 this.currRow[i7++] = this.scale0[indexColorModel
/*  879 */                     .getAlpha(0xFF & arrayOfInt[i10])]; 
/*      */             } 
/*      */             break;
/*      */           } 
/*  883 */           for (i10 = paramInt1; i10 < i3; i10 += paramInt3) {
/*  884 */             for (byte b1 = 0; b1 < this.numBands; b1++) {
/*  885 */               this.currRow[i7++] = this.scale[b1][arrayOfInt[i10 + b1]];
/*      */             }
/*      */           } 
/*      */           break;
/*      */ 
/*      */         
/*      */         case 16:
/*  892 */           for (i10 = paramInt1; i10 < i3; i10 += paramInt3) {
/*  893 */             for (byte b1 = 0; b1 < this.numBands; b1++) {
/*  894 */               this.currRow[i7++] = this.scaleh[b1][arrayOfInt[i10 + b1]];
/*  895 */               this.currRow[i7++] = this.scalel[b1][arrayOfInt[i10 + b1]];
/*      */             } 
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/*  902 */       int i9 = this.rowFilter.filterRow(this.metadata.IHDR_colorType, this.currRow, this.prevRow, this.filteredRows, i4, this.bpp);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  907 */       paramImageOutputStream.write(i9);
/*  908 */       paramImageOutputStream.write(this.filteredRows[i9], this.bpp, i4);
/*      */ 
/*      */       
/*  911 */       byte[] arrayOfByte = this.currRow;
/*  912 */       this.currRow = this.prevRow;
/*  913 */       this.prevRow = arrayOfByte;
/*      */       
/*  915 */       this.pixelsDone += n;
/*  916 */       processImageProgress(100.0F * this.pixelsDone / this.totalPixels);
/*      */ 
/*      */ 
/*      */       
/*  920 */       if (abortRequested()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void write_IDAT(RenderedImage paramRenderedImage) throws IOException {
/*  928 */     IDATOutputStream iDATOutputStream = new IDATOutputStream(this.stream, 32768);
/*      */     try {
/*  930 */       if (this.metadata.IHDR_interlaceMethod == 1) {
/*  931 */         for (byte b = 0; b < 7; b++) {
/*  932 */           encodePass(iDATOutputStream, paramRenderedImage, PNGImageReader.adam7XOffset[b], PNGImageReader.adam7YOffset[b], PNGImageReader.adam7XSubsampling[b], PNGImageReader.adam7YSubsampling[b]);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  937 */           if (abortRequested()) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } else {
/*  942 */         encodePass(iDATOutputStream, paramRenderedImage, 0, 0, 1, 1);
/*      */       } 
/*      */     } finally {
/*  945 */       iDATOutputStream.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeIEND() throws IOException {
/*  950 */     ChunkStream chunkStream = new ChunkStream(1229278788, this.stream);
/*  951 */     chunkStream.finish();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean equals(int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  957 */     if (paramArrayOfint1 == null || paramArrayOfint2 == null) {
/*  958 */       return false;
/*      */     }
/*  960 */     if (paramArrayOfint1.length != paramArrayOfint2.length) {
/*  961 */       return false;
/*      */     }
/*  963 */     for (byte b = 0; b < paramArrayOfint1.length; b++) {
/*  964 */       if (paramArrayOfint1[b] != paramArrayOfint2[b]) {
/*  965 */         return false;
/*      */       }
/*      */     } 
/*  968 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeScaleTables(int[] paramArrayOfint) {
/*  975 */     int i = this.metadata.IHDR_bitDepth;
/*      */ 
/*      */     
/*  978 */     if (i == this.scalingBitDepth && 
/*  979 */       equals(paramArrayOfint, this.sampleSize)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  984 */     this.sampleSize = paramArrayOfint;
/*  985 */     this.scalingBitDepth = i;
/*  986 */     int j = (1 << i) - 1;
/*  987 */     if (i <= 8) {
/*  988 */       this.scale = new byte[this.numBands][];
/*  989 */       for (byte b = 0; b < this.numBands; b++) {
/*  990 */         int k = (1 << paramArrayOfint[b]) - 1;
/*  991 */         int m = k / 2;
/*  992 */         this.scale[b] = new byte[k + 1];
/*  993 */         for (byte b1 = 0; b1 <= k; b1++) {
/*  994 */           this.scale[b][b1] = (byte)((b1 * j + m) / k);
/*      */         }
/*      */       } 
/*      */       
/*  998 */       this.scale0 = this.scale[0];
/*  999 */       this.scaleh = this.scalel = (byte[][])null;
/*      */     } else {
/*      */       
/* 1002 */       this.scaleh = new byte[this.numBands][];
/* 1003 */       this.scalel = new byte[this.numBands][];
/*      */       
/* 1005 */       for (byte b = 0; b < this.numBands; b++) {
/* 1006 */         int k = (1 << paramArrayOfint[b]) - 1;
/* 1007 */         int m = k / 2;
/* 1008 */         this.scaleh[b] = new byte[k + 1];
/* 1009 */         this.scalel[b] = new byte[k + 1];
/* 1010 */         for (byte b1 = 0; b1 <= k; b1++) {
/* 1011 */           int n = (b1 * j + m) / k;
/* 1012 */           this.scaleh[b][b1] = (byte)(n >> 8);
/* 1013 */           this.scalel[b][b1] = (byte)(n & 0xFF);
/*      */         } 
/*      */       } 
/* 1016 */       this.scale = (byte[][])null;
/* 1017 */       this.scale0 = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(IIOMetadata paramIIOMetadata, IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IIOException {
/* 1024 */     if (this.stream == null) {
/* 1025 */       throw new IllegalStateException("output == null!");
/*      */     }
/* 1027 */     if (paramIIOImage == null) {
/* 1028 */       throw new IllegalArgumentException("image == null!");
/*      */     }
/* 1030 */     if (paramIIOImage.hasRaster()) {
/* 1031 */       throw new UnsupportedOperationException("image has a Raster!");
/*      */     }
/*      */     
/* 1034 */     RenderedImage renderedImage = paramIIOImage.getRenderedImage();
/* 1035 */     SampleModel sampleModel = renderedImage.getSampleModel();
/* 1036 */     this.numBands = sampleModel.getNumBands();
/*      */ 
/*      */     
/* 1039 */     this.sourceXOffset = renderedImage.getMinX();
/* 1040 */     this.sourceYOffset = renderedImage.getMinY();
/* 1041 */     this.sourceWidth = renderedImage.getWidth();
/* 1042 */     this.sourceHeight = renderedImage.getHeight();
/* 1043 */     this.sourceBands = null;
/* 1044 */     this.periodX = 1;
/* 1045 */     this.periodY = 1;
/*      */     
/* 1047 */     if (paramImageWriteParam != null) {
/*      */       
/* 1049 */       Rectangle rectangle = paramImageWriteParam.getSourceRegion();
/* 1050 */       if (rectangle != null) {
/*      */ 
/*      */ 
/*      */         
/* 1054 */         Rectangle rectangle1 = new Rectangle(renderedImage.getMinX(), renderedImage.getMinY(), renderedImage.getWidth(), renderedImage.getHeight());
/*      */         
/* 1056 */         rectangle = rectangle.intersection(rectangle1);
/* 1057 */         this.sourceXOffset = rectangle.x;
/* 1058 */         this.sourceYOffset = rectangle.y;
/* 1059 */         this.sourceWidth = rectangle.width;
/* 1060 */         this.sourceHeight = rectangle.height;
/*      */       } 
/*      */ 
/*      */       
/* 1064 */       int k = paramImageWriteParam.getSubsamplingXOffset();
/* 1065 */       int m = paramImageWriteParam.getSubsamplingYOffset();
/* 1066 */       this.sourceXOffset += k;
/* 1067 */       this.sourceYOffset += m;
/* 1068 */       this.sourceWidth -= k;
/* 1069 */       this.sourceHeight -= m;
/*      */ 
/*      */       
/* 1072 */       this.periodX = paramImageWriteParam.getSourceXSubsampling();
/* 1073 */       this.periodY = paramImageWriteParam.getSourceYSubsampling();
/*      */       
/* 1075 */       int[] arrayOfInt = paramImageWriteParam.getSourceBands();
/* 1076 */       if (arrayOfInt != null) {
/* 1077 */         this.sourceBands = arrayOfInt;
/* 1078 */         this.numBands = this.sourceBands.length;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1083 */     int i = (this.sourceWidth + this.periodX - 1) / this.periodX;
/* 1084 */     int j = (this.sourceHeight + this.periodY - 1) / this.periodY;
/* 1085 */     if (i <= 0 || j <= 0) {
/* 1086 */       throw new IllegalArgumentException("Empty source region!");
/*      */     }
/*      */ 
/*      */     
/* 1090 */     this.totalPixels = i * j;
/* 1091 */     this.pixelsDone = 0;
/*      */ 
/*      */     
/* 1094 */     IIOMetadata iIOMetadata = paramIIOImage.getMetadata();
/* 1095 */     if (iIOMetadata != null) {
/* 1096 */       this.metadata = (PNGMetadata)convertImageMetadata(iIOMetadata, 
/* 1097 */           ImageTypeSpecifier.createFromRenderedImage(renderedImage), (ImageWriteParam)null);
/*      */     } else {
/*      */       
/* 1100 */       this.metadata = new PNGMetadata();
/*      */     } 
/*      */     
/* 1103 */     if (paramImageWriteParam != null)
/*      */     {
/* 1105 */       switch (paramImageWriteParam.getProgressiveMode()) {
/*      */         case 1:
/* 1107 */           this.metadata.IHDR_interlaceMethod = 1;
/*      */           break;
/*      */         case 0:
/* 1110 */           this.metadata.IHDR_interlaceMethod = 0;
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1118 */     this.metadata.initialize(new ImageTypeSpecifier(renderedImage), this.numBands);
/*      */ 
/*      */     
/* 1121 */     this.metadata.IHDR_width = i;
/* 1122 */     this.metadata.IHDR_height = j;
/*      */     
/* 1124 */     this.bpp = this.numBands * ((this.metadata.IHDR_bitDepth == 16) ? 2 : 1);
/*      */ 
/*      */     
/* 1127 */     initializeScaleTables(sampleModel.getSampleSize());
/*      */     
/* 1129 */     clearAbortRequest();
/*      */     
/* 1131 */     processImageStarted(0);
/*      */     
/*      */     try {
/* 1134 */       write_magic();
/* 1135 */       write_IHDR();
/*      */       
/* 1137 */       write_cHRM();
/* 1138 */       write_gAMA();
/* 1139 */       write_iCCP();
/* 1140 */       write_sBIT();
/* 1141 */       write_sRGB();
/*      */       
/* 1143 */       write_PLTE();
/*      */       
/* 1145 */       write_hIST();
/* 1146 */       write_tRNS();
/* 1147 */       write_bKGD();
/*      */       
/* 1149 */       write_pHYs();
/* 1150 */       write_sPLT();
/* 1151 */       write_tIME();
/* 1152 */       write_tEXt();
/* 1153 */       write_iTXt();
/* 1154 */       write_zTXt();
/*      */       
/* 1156 */       writeUnknownChunks();
/*      */       
/* 1158 */       write_IDAT(renderedImage);
/*      */       
/* 1160 */       if (abortRequested()) {
/* 1161 */         processWriteAborted();
/*      */       } else {
/*      */         
/* 1164 */         writeIEND();
/* 1165 */         processImageComplete();
/*      */       } 
/* 1167 */     } catch (IOException iOException) {
/* 1168 */       throw new IIOException("I/O error writing PNG file!", iOException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/PNGImageWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */