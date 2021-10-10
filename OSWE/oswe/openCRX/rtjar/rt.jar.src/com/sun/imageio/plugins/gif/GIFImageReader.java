/*      */ package com.sun.imageio.plugins.gif;
/*      */ 
/*      */ import com.sun.imageio.plugins.common.ReaderUtil;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.MultiPixelPackedSampleModel;
/*      */ import java.awt.image.PixelInterleavedSampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javax.imageio.IIOException;
/*      */ import javax.imageio.ImageReadParam;
/*      */ import javax.imageio.ImageReader;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.metadata.IIOMetadata;
/*      */ import javax.imageio.spi.ImageReaderSpi;
/*      */ import javax.imageio.stream.ImageInputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GIFImageReader
/*      */   extends ImageReader
/*      */ {
/*   56 */   ImageInputStream stream = null;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean gotHeader = false;
/*      */ 
/*      */ 
/*      */   
/*   64 */   GIFStreamMetadata streamMetadata = null;
/*      */ 
/*      */   
/*   67 */   int currIndex = -1;
/*      */ 
/*      */   
/*   70 */   GIFImageMetadata imageMetadata = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   75 */   List imageStartPosition = new ArrayList();
/*      */ 
/*      */ 
/*      */   
/*      */   int imageMetadataLength;
/*      */ 
/*      */   
/*   82 */   int numImages = -1;
/*      */ 
/*      */   
/*   85 */   byte[] block = new byte[255];
/*   86 */   int blockLength = 0;
/*   87 */   int bitPos = 0;
/*   88 */   int nextByte = 0;
/*      */   
/*      */   int initCodeSize;
/*      */   
/*      */   int clearCode;
/*      */   int eofCode;
/*   94 */   int next32Bits = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean lastBlockFound = false;
/*      */ 
/*      */   
/*  101 */   BufferedImage theImage = null;
/*      */ 
/*      */   
/*  104 */   WritableRaster theTile = null;
/*      */ 
/*      */   
/*  107 */   int width = -1, height = -1;
/*      */ 
/*      */   
/*  110 */   int streamX = -1, streamY = -1;
/*      */ 
/*      */   
/*  113 */   int rowsDone = 0;
/*      */ 
/*      */   
/*  116 */   int interlacePass = 0;
/*      */   
/*  118 */   private byte[] fallbackColorTable = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   static final int[] interlaceIncrement = new int[] { 8, 8, 4, 2, -1 };
/*  124 */   static final int[] interlaceOffset = new int[] { 0, 4, 2, 1, -1 };
/*      */   Rectangle sourceRegion;
/*      */   int sourceXSubsampling;
/*  127 */   int sourceYSubsampling; int sourceMinProgressivePass; int sourceMaxProgressivePass; Point destinationOffset; Rectangle destinationRegion; int updateMinY; int updateYStep; boolean decodeThisRow; int destY; byte[] rowBuf; public void setInput(Object paramObject, boolean paramBoolean1, boolean paramBoolean2) { super.setInput(paramObject, paramBoolean1, paramBoolean2); if (paramObject != null) { if (!(paramObject instanceof ImageInputStream)) throw new IllegalArgumentException("input not an ImageInputStream!");  this.stream = (ImageInputStream)paramObject; } else { this.stream = null; }  resetStreamSettings(); } public int getNumImages(boolean paramBoolean) throws IIOException { if (this.stream == null) throw new IllegalStateException("Input not set!");  if (this.seekForwardOnly && paramBoolean) throw new IllegalStateException("seekForwardOnly and allowSearch can't both be true!");  if (this.numImages > 0) return this.numImages;  if (paramBoolean) this.numImages = locateImage(2147483647) + 1;  return this.numImages; } private void checkIndex(int paramInt) { if (paramInt < this.minIndex) throw new IndexOutOfBoundsException("imageIndex < minIndex!");  if (this.seekForwardOnly) this.minIndex = paramInt;  } public GIFImageReader(ImageReaderSpi paramImageReaderSpi) { super(paramImageReaderSpi);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     this.decodeThisRow = true;
/*  403 */     this.destY = 0; }
/*      */   public int getWidth(int paramInt) throws IIOException { checkIndex(paramInt); int i = locateImage(paramInt); if (i != paramInt)
/*      */       throw new IndexOutOfBoundsException();  readMetadata(); return this.imageMetadata.imageWidth; }
/*      */   public int getHeight(int paramInt) throws IIOException { checkIndex(paramInt); int i = locateImage(paramInt); if (i != paramInt)
/*      */       throw new IndexOutOfBoundsException();  readMetadata(); return this.imageMetadata.imageHeight; }
/*      */   private ImageTypeSpecifier createIndexed(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) { IndexColorModel indexColorModel; MultiPixelPackedSampleModel multiPixelPackedSampleModel; if (this.imageMetadata.transparentColorFlag) { int i = Math.min(this.imageMetadata.transparentColorIndex, paramArrayOfbyte1.length - 1); indexColorModel = new IndexColorModel(paramInt, paramArrayOfbyte1.length, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, i); } else { indexColorModel = new IndexColorModel(paramInt, paramArrayOfbyte1.length, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3); }  if (paramInt == 8) { int[] arrayOfInt = { 0 }; PixelInterleavedSampleModel pixelInterleavedSampleModel = new PixelInterleavedSampleModel(0, 1, 1, 1, 1, arrayOfInt); } else { multiPixelPackedSampleModel = new MultiPixelPackedSampleModel(0, 1, 1, paramInt); }
/*  409 */      return new ImageTypeSpecifier(indexColorModel, multiPixelPackedSampleModel); } private void outputRow() { int i = Math.min(this.sourceRegion.width, this.destinationRegion.width * this.sourceXSubsampling);
/*      */     
/*  411 */     int j = this.destinationRegion.x;
/*      */     
/*  413 */     if (this.sourceXSubsampling == 1) {
/*  414 */       this.theTile.setDataElements(j, this.destY, i, 1, this.rowBuf);
/*      */     } else {
/*  416 */       for (int k = 0; k < i; k += this.sourceXSubsampling, j++) {
/*  417 */         this.theTile.setSample(j, this.destY, 0, this.rowBuf[k] & 0xFF);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  422 */     if (this.updateListeners != null)
/*  423 */     { int[] arrayOfInt = { 0 };
/*      */ 
/*      */       
/*  426 */       processImageUpdate(this.theImage, j, this.destY, i, 1, 1, this.updateYStep, arrayOfInt); }  } public Iterator getImageTypes(int paramInt) throws IIOException { byte[] arrayOfByte1; byte b1; checkIndex(paramInt); int i = locateImage(paramInt); if (i != paramInt)
/*      */       throw new IndexOutOfBoundsException();  readMetadata(); ArrayList<ImageTypeSpecifier> arrayList = new ArrayList(1); if (this.imageMetadata.localColorTable != null) { arrayOfByte1 = this.imageMetadata.localColorTable; this.fallbackColorTable = this.imageMetadata.localColorTable; } else { arrayOfByte1 = this.streamMetadata.globalColorTable; }  if (arrayOfByte1 == null) { if (this.fallbackColorTable == null) { processWarningOccurred("Use default color table."); this.fallbackColorTable = getDefaultPalette(); }  arrayOfByte1 = this.fallbackColorTable; }  int j = arrayOfByte1.length / 3; if (j == 2) { b1 = 1; } else if (j == 4) { b1 = 2; }
/*      */     else if (j == 8 || j == 16) { b1 = 4; }
/*      */     else { b1 = 8; }
/*      */      int k = 1 << b1; byte[] arrayOfByte2 = new byte[k]; byte[] arrayOfByte3 = new byte[k]; byte[] arrayOfByte4 = new byte[k]; byte b2 = 0; for (byte b3 = 0; b3 < j; b3++) { arrayOfByte2[b3] = arrayOfByte1[b2++]; arrayOfByte3[b3] = arrayOfByte1[b2++]; arrayOfByte4[b3] = arrayOfByte1[b2++]; }
/*      */      arrayList.add(createIndexed(arrayOfByte2, arrayOfByte3, arrayOfByte4, b1)); return arrayList.iterator(); }
/*      */   public ImageReadParam getDefaultReadParam() { return new ImageReadParam(); }
/*      */   public IIOMetadata getStreamMetadata() throws IIOException { readHeader(); return this.streamMetadata; }
/*  434 */   private void computeDecodeThisRow() { this.decodeThisRow = (this.destY < this.destinationRegion.y + this.destinationRegion.height && this.streamY >= this.sourceRegion.y && this.streamY < this.sourceRegion.y + this.sourceRegion.height && (this.streamY - this.sourceRegion.y) % this.sourceYSubsampling == 0); } public IIOMetadata getImageMetadata(int paramInt) throws IIOException { checkIndex(paramInt); int i = locateImage(paramInt); if (i != paramInt)
/*      */       throw new IndexOutOfBoundsException("Bad image index!");  readMetadata(); return this.imageMetadata; }
/*      */   private void initNext32Bits() { this.next32Bits = this.block[0] & 0xFF; this.next32Bits |= (this.block[1] & 0xFF) << 8; this.next32Bits |= (this.block[2] & 0xFF) << 16; this.next32Bits |= this.block[3] << 24; this.nextByte = 4; }
/*      */   private int getCode(int paramInt1, int paramInt2) throws IOException { if (this.bitPos + paramInt1 > 32)
/*      */       return this.eofCode;  int i = this.next32Bits >> this.bitPos & paramInt2; this.bitPos += paramInt1; while (this.bitPos >= 8 && !this.lastBlockFound) { this.next32Bits >>>= 8; this.bitPos -= 8; if (this.nextByte >= this.blockLength) { this.blockLength = this.stream.readUnsignedByte(); if (this.blockLength == 0) { this.lastBlockFound = true; return i; }  int j = this.blockLength; int k = 0; while (j > 0) { int m = this.stream.read(this.block, k, j); k += m; j -= m; }  this.nextByte = 0; }  this.next32Bits |= this.block[this.nextByte++] << 24; }  return i; }
/*      */   public void initializeStringTable(int[] paramArrayOfint1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int[] paramArrayOfint2) { int i = 1 << this.initCodeSize; int j; for (j = 0; j < i; j++) { paramArrayOfint1[j] = -1; paramArrayOfbyte1[j] = (byte)j; paramArrayOfbyte2[j] = (byte)j; paramArrayOfint2[j] = 1; }
/*      */      for (j = i; j < 4096; j++) { paramArrayOfint1[j] = -1; paramArrayOfint2[j] = 1; }
/*      */      }
/*  442 */   private void outputPixels(byte[] paramArrayOfbyte, int paramInt) { if (this.interlacePass < this.sourceMinProgressivePass || this.interlacePass > this.sourceMaxProgressivePass) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  447 */     for (byte b = 0; b < paramInt; b++) {
/*  448 */       if (this.streamX >= this.sourceRegion.x) {
/*  449 */         this.rowBuf[this.streamX - this.sourceRegion.x] = paramArrayOfbyte[b];
/*      */       }
/*      */ 
/*      */       
/*  453 */       this.streamX++;
/*  454 */       if (this.streamX == this.width) {
/*      */         
/*  456 */         this.rowsDone++;
/*  457 */         processImageProgress(100.0F * this.rowsDone / this.height);
/*      */         
/*  459 */         if (this.decodeThisRow) {
/*  460 */           outputRow();
/*      */         }
/*      */         
/*  463 */         this.streamX = 0;
/*  464 */         if (this.imageMetadata.interlaceFlag) {
/*  465 */           this.streamY += interlaceIncrement[this.interlacePass];
/*  466 */           if (this.streamY >= this.height) {
/*      */             
/*  468 */             if (this.updateListeners != null) {
/*  469 */               processPassComplete(this.theImage);
/*      */             }
/*      */             
/*  472 */             this.interlacePass++;
/*  473 */             if (this.interlacePass > this.sourceMaxProgressivePass) {
/*      */               return;
/*      */             }
/*  476 */             this.streamY = interlaceOffset[this.interlacePass];
/*  477 */             startPass(this.interlacePass);
/*      */           } 
/*      */         } else {
/*  480 */           this.streamY++;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  485 */         this.destY = this.destinationRegion.y + (this.streamY - this.sourceRegion.y) / this.sourceYSubsampling;
/*      */         
/*  487 */         computeDecodeThisRow();
/*      */       } 
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readHeader() throws IIOException {
/*  495 */     if (this.gotHeader) {
/*      */       return;
/*      */     }
/*  498 */     if (this.stream == null) {
/*  499 */       throw new IllegalStateException("Input not set!");
/*      */     }
/*      */ 
/*      */     
/*  503 */     this.streamMetadata = new GIFStreamMetadata();
/*      */     
/*      */     try {
/*  506 */       this.stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
/*      */       
/*  508 */       byte[] arrayOfByte = new byte[6];
/*  509 */       this.stream.readFully(arrayOfByte);
/*      */       
/*  511 */       StringBuffer stringBuffer = new StringBuffer(3);
/*  512 */       stringBuffer.append((char)arrayOfByte[3]);
/*  513 */       stringBuffer.append((char)arrayOfByte[4]);
/*  514 */       stringBuffer.append((char)arrayOfByte[5]);
/*  515 */       this.streamMetadata.version = stringBuffer.toString();
/*      */       
/*  517 */       this.streamMetadata.logicalScreenWidth = this.stream.readUnsignedShort();
/*  518 */       this.streamMetadata.logicalScreenHeight = this.stream.readUnsignedShort();
/*      */       
/*  520 */       int i = this.stream.readUnsignedByte();
/*  521 */       boolean bool = ((i & 0x80) != 0) ? true : false;
/*  522 */       this.streamMetadata.colorResolution = (i >> 4 & 0x7) + 1;
/*  523 */       this.streamMetadata.sortFlag = ((i & 0x8) != 0);
/*  524 */       int j = 1 << (i & 0x7) + 1;
/*      */       
/*  526 */       this.streamMetadata.backgroundColorIndex = this.stream.readUnsignedByte();
/*  527 */       this.streamMetadata.pixelAspectRatio = this.stream.readUnsignedByte();
/*      */       
/*  529 */       if (bool) {
/*  530 */         this.streamMetadata.globalColorTable = new byte[3 * j];
/*  531 */         this.stream.readFully(this.streamMetadata.globalColorTable);
/*      */       } else {
/*  533 */         this.streamMetadata.globalColorTable = null;
/*      */       } 
/*      */ 
/*      */       
/*  537 */       this.imageStartPosition.add(Long.valueOf(this.stream.getStreamPosition()));
/*  538 */     } catch (IOException iOException) {
/*  539 */       throw new IIOException("I/O error reading header!", iOException);
/*      */     } 
/*      */     
/*  542 */     this.gotHeader = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean skipImage() throws IIOException {
/*      */     try {
/*      */       label31: while (true) {
/*  551 */         int i = this.stream.readUnsignedByte();
/*      */         
/*  553 */         if (i == 44) {
/*  554 */           this.stream.skipBytes(8);
/*      */           
/*  556 */           int k = this.stream.readUnsignedByte();
/*  557 */           if ((k & 0x80) != 0) {
/*      */             
/*  559 */             int n = (k & 0x7) + 1;
/*  560 */             this.stream.skipBytes(3 * (1 << n));
/*      */           } 
/*      */           
/*  563 */           this.stream.skipBytes(1);
/*      */           
/*  565 */           int m = 0;
/*      */           do {
/*  567 */             m = this.stream.readUnsignedByte();
/*  568 */             this.stream.skipBytes(m);
/*  569 */           } while (m > 0);
/*      */           
/*  571 */           return true;
/*  572 */         }  if (i == 59)
/*  573 */           return false; 
/*  574 */         if (i == 33)
/*  575 */         { int k = this.stream.readUnsignedByte();
/*      */           
/*  577 */           int m = 0;
/*      */           while (true)
/*  579 */           { m = this.stream.readUnsignedByte();
/*  580 */             this.stream.skipBytes(m);
/*  581 */             if (m <= 0)
/*  582 */               continue label31;  }  break; }  if (i == 0)
/*      */         {
/*  584 */           return false;
/*      */         }
/*  586 */         int j = 0;
/*      */         while (true)
/*  588 */         { j = this.stream.readUnsignedByte();
/*  589 */           this.stream.skipBytes(j);
/*  590 */           if (j <= 0)
/*      */             continue label31;  }  break;
/*      */       } 
/*  593 */     } catch (EOFException eOFException) {
/*  594 */       return false;
/*  595 */     } catch (IOException iOException) {
/*  596 */       throw new IIOException("I/O error locating image!", iOException);
/*      */     } 
/*      */   }
/*      */   
/*      */   private int locateImage(int paramInt) throws IIOException {
/*  601 */     readHeader();
/*      */ 
/*      */     
/*      */     try {
/*  605 */       int i = Math.min(paramInt, this.imageStartPosition.size() - 1);
/*      */ 
/*      */       
/*  608 */       Long long_ = this.imageStartPosition.get(i);
/*  609 */       this.stream.seek(long_.longValue());
/*      */ 
/*      */       
/*  612 */       while (i < paramInt) {
/*  613 */         if (!skipImage()) {
/*  614 */           i--;
/*  615 */           return i;
/*      */         } 
/*      */         
/*  618 */         Long long_1 = new Long(this.stream.getStreamPosition());
/*  619 */         this.imageStartPosition.add(long_1);
/*  620 */         i++;
/*      */       } 
/*  622 */     } catch (IOException iOException) {
/*  623 */       throw new IIOException("Couldn't seek!", iOException);
/*      */     } 
/*      */     
/*  626 */     if (this.currIndex != paramInt) {
/*  627 */       this.imageMetadata = null;
/*      */     }
/*  629 */     this.currIndex = paramInt;
/*  630 */     return paramInt;
/*      */   }
/*      */ 
/*      */   
/*      */   private byte[] concatenateBlocks() throws IOException {
/*  635 */     byte[] arrayOfByte = new byte[0];
/*      */     while (true) {
/*  637 */       int i = this.stream.readUnsignedByte();
/*  638 */       if (i == 0) {
/*      */         break;
/*      */       }
/*  641 */       byte[] arrayOfByte1 = new byte[arrayOfByte.length + i];
/*  642 */       System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, arrayOfByte.length);
/*  643 */       this.stream.readFully(arrayOfByte1, arrayOfByte.length, i);
/*  644 */       arrayOfByte = arrayOfByte1;
/*      */     } 
/*      */     
/*  647 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   
/*      */   private void readMetadata() throws IIOException {
/*  652 */     if (this.stream == null) {
/*  653 */       throw new IllegalStateException("Input not set!");
/*      */     }
/*      */     
/*      */     try {
/*      */       int i;
/*  658 */       this.imageMetadata = new GIFImageMetadata();
/*      */       
/*  660 */       long l = this.stream.getStreamPosition();
/*      */       label68: while (true) {
/*  662 */         i = this.stream.readUnsignedByte();
/*  663 */         if (i == 44) {
/*  664 */           this.imageMetadata
/*  665 */             .imageLeftPosition = this.stream.readUnsignedShort();
/*  666 */           this.imageMetadata
/*  667 */             .imageTopPosition = this.stream.readUnsignedShort();
/*  668 */           this.imageMetadata.imageWidth = this.stream.readUnsignedShort();
/*  669 */           this.imageMetadata.imageHeight = this.stream.readUnsignedShort();
/*      */           
/*  671 */           int j = this.stream.readUnsignedByte();
/*  672 */           boolean bool = ((j & 0x80) != 0) ? true : false;
/*      */           
/*  674 */           this.imageMetadata.interlaceFlag = ((j & 0x40) != 0);
/*  675 */           this.imageMetadata.sortFlag = ((j & 0x20) != 0);
/*  676 */           int k = 1 << (j & 0x7) + 1;
/*      */           
/*  678 */           if (bool) {
/*      */             
/*  680 */             this.imageMetadata.localColorTable = new byte[3 * k];
/*      */             
/*  682 */             this.stream.readFully(this.imageMetadata.localColorTable);
/*      */           } else {
/*  684 */             this.imageMetadata.localColorTable = null;
/*      */           } 
/*      */ 
/*      */           
/*  688 */           this
/*  689 */             .imageMetadataLength = (int)(this.stream.getStreamPosition() - l);
/*      */           
/*      */           return;
/*      */         } 
/*  693 */         if (i == 33)
/*  694 */         { int j = this.stream.readUnsignedByte();
/*      */           
/*  696 */           if (j == 249) {
/*  697 */             int m = this.stream.readUnsignedByte();
/*  698 */             int n = this.stream.readUnsignedByte();
/*  699 */             this.imageMetadata.disposalMethod = n >> 2 & 0x3;
/*      */             
/*  701 */             this.imageMetadata.userInputFlag = ((n & 0x2) != 0);
/*      */             
/*  703 */             this.imageMetadata.transparentColorFlag = ((n & 0x1) != 0);
/*      */ 
/*      */             
/*  706 */             this.imageMetadata.delayTime = this.stream.readUnsignedShort();
/*  707 */             this.imageMetadata
/*  708 */               .transparentColorIndex = this.stream.readUnsignedByte();
/*      */             
/*  710 */             int i1 = this.stream.readUnsignedByte(); continue;
/*  711 */           }  if (j == 1) {
/*  712 */             int m = this.stream.readUnsignedByte();
/*  713 */             this.imageMetadata.hasPlainTextExtension = true;
/*  714 */             this.imageMetadata
/*  715 */               .textGridLeft = this.stream.readUnsignedShort();
/*  716 */             this.imageMetadata
/*  717 */               .textGridTop = this.stream.readUnsignedShort();
/*  718 */             this.imageMetadata
/*  719 */               .textGridWidth = this.stream.readUnsignedShort();
/*  720 */             this.imageMetadata
/*  721 */               .textGridHeight = this.stream.readUnsignedShort();
/*  722 */             this.imageMetadata
/*  723 */               .characterCellWidth = this.stream.readUnsignedByte();
/*  724 */             this.imageMetadata
/*  725 */               .characterCellHeight = this.stream.readUnsignedByte();
/*  726 */             this.imageMetadata
/*  727 */               .textForegroundColor = this.stream.readUnsignedByte();
/*  728 */             this.imageMetadata
/*  729 */               .textBackgroundColor = this.stream.readUnsignedByte();
/*  730 */             this.imageMetadata.text = concatenateBlocks(); continue;
/*  731 */           }  if (j == 254) {
/*  732 */             byte[] arrayOfByte = concatenateBlocks();
/*  733 */             if (this.imageMetadata.comments == null) {
/*  734 */               this.imageMetadata.comments = new ArrayList();
/*      */             }
/*  736 */             this.imageMetadata.comments.add(arrayOfByte); continue;
/*  737 */           }  if (j == 255) {
/*  738 */             int m = this.stream.readUnsignedByte();
/*  739 */             byte[] arrayOfByte1 = new byte[8];
/*  740 */             byte[] arrayOfByte2 = new byte[3];
/*      */ 
/*      */             
/*  743 */             byte[] arrayOfByte3 = new byte[m];
/*  744 */             this.stream.readFully(arrayOfByte3);
/*      */             
/*  746 */             int n = copyData(arrayOfByte3, 0, arrayOfByte1);
/*  747 */             n = copyData(arrayOfByte3, n, arrayOfByte2);
/*      */             
/*  749 */             byte[] arrayOfByte4 = concatenateBlocks();
/*      */             
/*  751 */             if (n < m) {
/*  752 */               int i1 = m - n;
/*  753 */               byte[] arrayOfByte = new byte[i1 + arrayOfByte4.length];
/*      */ 
/*      */               
/*  756 */               System.arraycopy(arrayOfByte3, n, arrayOfByte, 0, i1);
/*  757 */               System.arraycopy(arrayOfByte4, 0, arrayOfByte, i1, arrayOfByte4.length);
/*      */ 
/*      */               
/*  760 */               arrayOfByte4 = arrayOfByte;
/*      */             } 
/*      */ 
/*      */             
/*  764 */             if (this.imageMetadata.applicationIDs == null) {
/*  765 */               this.imageMetadata.applicationIDs = new ArrayList();
/*  766 */               this.imageMetadata.authenticationCodes = new ArrayList();
/*      */               
/*  768 */               this.imageMetadata.applicationData = new ArrayList();
/*      */             } 
/*  770 */             this.imageMetadata.applicationIDs.add(arrayOfByte1);
/*  771 */             this.imageMetadata.authenticationCodes.add(arrayOfByte2);
/*  772 */             this.imageMetadata.applicationData.add(arrayOfByte4);
/*      */             continue;
/*      */           } 
/*  775 */           int k = 0;
/*      */           while (true)
/*  777 */           { k = this.stream.readUnsignedByte();
/*  778 */             this.stream.skipBytes(k);
/*  779 */             if (k <= 0)
/*      */               continue label68;  }  }  break;
/*  781 */       }  if (i == 59) {
/*  782 */         throw new IndexOutOfBoundsException("Attempt to read past end of image sequence!");
/*      */       }
/*      */       
/*  785 */       throw new IIOException("Unexpected block type " + i + "!");
/*      */ 
/*      */     
/*      */     }
/*  789 */     catch (IIOException iIOException) {
/*  790 */       throw iIOException;
/*  791 */     } catch (IOException iOException) {
/*  792 */       throw new IIOException("I/O error reading image metadata!", iOException);
/*      */     } 
/*      */   }
/*      */   
/*      */   private int copyData(byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) {
/*  797 */     int i = paramArrayOfbyte2.length;
/*  798 */     int j = paramArrayOfbyte1.length - paramInt;
/*  799 */     if (i > j) {
/*  800 */       i = j;
/*      */     }
/*  802 */     System.arraycopy(paramArrayOfbyte1, paramInt, paramArrayOfbyte2, 0, i);
/*  803 */     return paramInt + i;
/*      */   }
/*      */   
/*      */   private void startPass(int paramInt) {
/*  807 */     if (this.updateListeners == null || !this.imageMetadata.interlaceFlag) {
/*      */       return;
/*      */     }
/*      */     
/*  811 */     int i = interlaceOffset[this.interlacePass];
/*  812 */     int j = interlaceIncrement[this.interlacePass];
/*      */ 
/*      */     
/*  815 */     int[] arrayOfInt1 = ReaderUtil.computeUpdatedPixels(this.sourceRegion, this.destinationOffset, this.destinationRegion.x, this.destinationRegion.y, this.destinationRegion.x + this.destinationRegion.width - 1, this.destinationRegion.y + this.destinationRegion.height - 1, this.sourceXSubsampling, this.sourceYSubsampling, 0, i, this.destinationRegion.width, (this.destinationRegion.height + j - 1) / j, 1, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  833 */     this.updateMinY = arrayOfInt1[1];
/*  834 */     this.updateYStep = arrayOfInt1[5];
/*      */ 
/*      */     
/*  837 */     int[] arrayOfInt2 = { 0 };
/*      */     
/*  839 */     processPassStarted(this.theImage, this.interlacePass, this.sourceMinProgressivePass, this.sourceMaxProgressivePass, 0, this.updateMinY, 1, this.updateYStep, arrayOfInt2);
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
/*      */   public BufferedImage read(int paramInt, ImageReadParam paramImageReadParam) throws IIOException {
/*  852 */     if (this.stream == null) {
/*  853 */       throw new IllegalStateException("Input not set!");
/*      */     }
/*  855 */     checkIndex(paramInt);
/*      */     
/*  857 */     int i = locateImage(paramInt);
/*  858 */     if (i != paramInt) {
/*  859 */       throw new IndexOutOfBoundsException("imageIndex out of bounds!");
/*      */     }
/*      */     
/*  862 */     clearAbortRequest();
/*  863 */     readMetadata();
/*      */ 
/*      */     
/*  866 */     if (paramImageReadParam == null) {
/*  867 */       paramImageReadParam = getDefaultReadParam();
/*      */     }
/*      */ 
/*      */     
/*  871 */     Iterator<ImageTypeSpecifier> iterator = getImageTypes(paramInt);
/*  872 */     this.theImage = getDestination(paramImageReadParam, iterator, this.imageMetadata.imageWidth, this.imageMetadata.imageHeight);
/*      */ 
/*      */ 
/*      */     
/*  876 */     this.theTile = this.theImage.getWritableTile(0, 0);
/*  877 */     this.width = this.imageMetadata.imageWidth;
/*  878 */     this.height = this.imageMetadata.imageHeight;
/*  879 */     this.streamX = 0;
/*  880 */     this.streamY = 0;
/*  881 */     this.rowsDone = 0;
/*  882 */     this.interlacePass = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  887 */     this.sourceRegion = new Rectangle(0, 0, 0, 0);
/*  888 */     this.destinationRegion = new Rectangle(0, 0, 0, 0);
/*  889 */     computeRegions(paramImageReadParam, this.width, this.height, this.theImage, this.sourceRegion, this.destinationRegion);
/*      */     
/*  891 */     this.destinationOffset = new Point(this.destinationRegion.x, this.destinationRegion.y);
/*      */ 
/*      */     
/*  894 */     this.sourceXSubsampling = paramImageReadParam.getSourceXSubsampling();
/*  895 */     this.sourceYSubsampling = paramImageReadParam.getSourceYSubsampling();
/*  896 */     this
/*  897 */       .sourceMinProgressivePass = Math.max(paramImageReadParam.getSourceMinProgressivePass(), 0);
/*  898 */     this
/*  899 */       .sourceMaxProgressivePass = Math.min(paramImageReadParam.getSourceMaxProgressivePass(), 3);
/*      */     
/*  901 */     this.destY = this.destinationRegion.y + (this.streamY - this.sourceRegion.y) / this.sourceYSubsampling;
/*      */     
/*  903 */     computeDecodeThisRow();
/*      */ 
/*      */     
/*  906 */     processImageStarted(paramInt);
/*  907 */     startPass(0);
/*      */     
/*  909 */     this.rowBuf = new byte[this.width];
/*      */ 
/*      */     
/*      */     try {
/*  913 */       this.initCodeSize = this.stream.readUnsignedByte();
/*      */ 
/*      */       
/*  916 */       this.blockLength = this.stream.readUnsignedByte();
/*  917 */       int j = this.blockLength;
/*  918 */       int k = 0;
/*  919 */       while (j > 0) {
/*  920 */         int i3 = this.stream.read(this.block, k, j);
/*  921 */         j -= i3;
/*  922 */         k += i3;
/*      */       } 
/*      */       
/*  925 */       this.bitPos = 0;
/*  926 */       this.nextByte = 0;
/*  927 */       this.lastBlockFound = false;
/*  928 */       this.interlacePass = 0;
/*      */ 
/*      */       
/*  931 */       initNext32Bits();
/*      */       
/*  933 */       this.clearCode = 1 << this.initCodeSize;
/*  934 */       this.eofCode = this.clearCode + 1;
/*      */       
/*  936 */       int m = 0;
/*      */       
/*  938 */       int[] arrayOfInt1 = new int[4096];
/*  939 */       byte[] arrayOfByte1 = new byte[4096];
/*  940 */       byte[] arrayOfByte2 = new byte[4096];
/*  941 */       int[] arrayOfInt2 = new int[4096];
/*  942 */       byte[] arrayOfByte3 = new byte[4096];
/*      */       
/*  944 */       initializeStringTable(arrayOfInt1, arrayOfByte1, arrayOfByte2, arrayOfInt2);
/*  945 */       int n = (1 << this.initCodeSize) + 2;
/*  946 */       int i1 = this.initCodeSize + 1;
/*  947 */       int i2 = (1 << i1) - 1;
/*      */       
/*  949 */       while (!abortRequested()) {
/*  950 */         int i3 = getCode(i1, i2);
/*      */         
/*  952 */         if (i3 == this.clearCode)
/*  953 */         { initializeStringTable(arrayOfInt1, arrayOfByte1, arrayOfByte2, arrayOfInt2);
/*  954 */           n = (1 << this.initCodeSize) + 2;
/*  955 */           i1 = this.initCodeSize + 1;
/*  956 */           i2 = (1 << i1) - 1;
/*      */           
/*  958 */           i3 = getCode(i1, i2);
/*  959 */           if (i3 == this.eofCode) {
/*      */             
/*  961 */             processImageComplete();
/*  962 */             return this.theImage;
/*      */           }  }
/*  964 */         else { int i7; if (i3 == this.eofCode) {
/*      */             
/*  966 */             processImageComplete();
/*  967 */             return this.theImage;
/*      */           } 
/*      */           
/*  970 */           if (i3 < n) {
/*  971 */             i7 = i3;
/*      */           } else {
/*  973 */             i7 = m;
/*  974 */             if (i3 != n)
/*      */             {
/*      */               
/*  977 */               processWarningOccurred("Out-of-sequence code!");
/*      */             }
/*      */           } 
/*      */           
/*  981 */           int i8 = n;
/*  982 */           int i9 = m;
/*      */           
/*  984 */           arrayOfInt1[i8] = i9;
/*  985 */           arrayOfByte1[i8] = arrayOfByte2[i7];
/*  986 */           arrayOfByte2[i8] = arrayOfByte2[i9];
/*  987 */           arrayOfInt2[i8] = arrayOfInt2[i9] + 1;
/*      */           
/*  989 */           n++;
/*  990 */           if (n == 1 << i1 && n < 4096) {
/*      */             
/*  992 */             i1++;
/*  993 */             i2 = (1 << i1) - 1;
/*      */           }  }
/*      */ 
/*      */ 
/*      */         
/*  998 */         int i4 = i3;
/*  999 */         int i5 = arrayOfInt2[i4];
/* 1000 */         for (int i6 = i5 - 1; i6 >= 0; i6--) {
/* 1001 */           arrayOfByte3[i6] = arrayOfByte1[i4];
/* 1002 */           i4 = arrayOfInt1[i4];
/*      */         } 
/*      */         
/* 1005 */         outputPixels(arrayOfByte3, i5);
/* 1006 */         m = i3;
/*      */       } 
/*      */       
/* 1009 */       processReadAborted();
/* 1010 */       return this.theImage;
/* 1011 */     } catch (IOException iOException) {
/* 1012 */       iOException.printStackTrace();
/* 1013 */       throw new IIOException("I/O error reading image!", iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/* 1022 */     super.reset();
/* 1023 */     resetStreamSettings();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetStreamSettings() {
/* 1030 */     this.gotHeader = false;
/* 1031 */     this.streamMetadata = null;
/* 1032 */     this.currIndex = -1;
/* 1033 */     this.imageMetadata = null;
/* 1034 */     this.imageStartPosition = new ArrayList();
/* 1035 */     this.numImages = -1;
/*      */ 
/*      */     
/* 1038 */     this.blockLength = 0;
/* 1039 */     this.bitPos = 0;
/* 1040 */     this.nextByte = 0;
/*      */     
/* 1042 */     this.next32Bits = 0;
/* 1043 */     this.lastBlockFound = false;
/*      */     
/* 1045 */     this.theImage = null;
/* 1046 */     this.theTile = null;
/* 1047 */     this.width = -1;
/* 1048 */     this.height = -1;
/* 1049 */     this.streamX = -1;
/* 1050 */     this.streamY = -1;
/* 1051 */     this.rowsDone = 0;
/* 1052 */     this.interlacePass = 0;
/*      */     
/* 1054 */     this.fallbackColorTable = null;
/*      */   }
/*      */   
/* 1057 */   private static byte[] defaultPalette = null;
/*      */   
/*      */   private static synchronized byte[] getDefaultPalette() {
/* 1060 */     if (defaultPalette == null) {
/* 1061 */       BufferedImage bufferedImage = new BufferedImage(1, 1, 13);
/*      */       
/* 1063 */       IndexColorModel indexColorModel = (IndexColorModel)bufferedImage.getColorModel();
/*      */       
/* 1065 */       int i = indexColorModel.getMapSize();
/* 1066 */       byte[] arrayOfByte1 = new byte[i];
/* 1067 */       byte[] arrayOfByte2 = new byte[i];
/* 1068 */       byte[] arrayOfByte3 = new byte[i];
/* 1069 */       indexColorModel.getReds(arrayOfByte1);
/* 1070 */       indexColorModel.getGreens(arrayOfByte2);
/* 1071 */       indexColorModel.getBlues(arrayOfByte3);
/*      */       
/* 1073 */       defaultPalette = new byte[i * 3];
/*      */       
/* 1075 */       for (byte b = 0; b < i; b++) {
/* 1076 */         defaultPalette[3 * b + 0] = arrayOfByte1[b];
/* 1077 */         defaultPalette[3 * b + 1] = arrayOfByte2[b];
/* 1078 */         defaultPalette[3 * b + 2] = arrayOfByte3[b];
/*      */       } 
/*      */     } 
/* 1081 */     return defaultPalette;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFImageReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */