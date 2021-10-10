/*      */ package com.sun.imageio.plugins.png;
/*      */ 
/*      */ import com.sun.imageio.plugins.common.ReaderUtil;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.DataBuffer;
/*      */ import java.awt.image.DataBufferByte;
/*      */ import java.awt.image.DataBufferUShort;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.SequenceInputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.zip.Inflater;
/*      */ import java.util.zip.InflaterInputStream;
/*      */ import java.util.zip.ZipException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PNGImageReader
/*      */   extends ImageReader
/*      */ {
/*      */   static final int IHDR_TYPE = 1229472850;
/*      */   static final int PLTE_TYPE = 1347179589;
/*      */   static final int IDAT_TYPE = 1229209940;
/*      */   static final int IEND_TYPE = 1229278788;
/*      */   static final int bKGD_TYPE = 1649100612;
/*      */   static final int cHRM_TYPE = 1665684045;
/*      */   static final int gAMA_TYPE = 1732332865;
/*      */   static final int hIST_TYPE = 1749635924;
/*      */   static final int iCCP_TYPE = 1766015824;
/*      */   static final int iTXt_TYPE = 1767135348;
/*      */   static final int pHYs_TYPE = 1883789683;
/*      */   static final int sBIT_TYPE = 1933723988;
/*      */   static final int sPLT_TYPE = 1934642260;
/*      */   static final int sRGB_TYPE = 1934772034;
/*      */   static final int tEXt_TYPE = 1950701684;
/*      */   static final int tIME_TYPE = 1950960965;
/*      */   static final int tRNS_TYPE = 1951551059;
/*      */   static final int zTXt_TYPE = 2052348020;
/*      */   static final int PNG_COLOR_GRAY = 0;
/*      */   static final int PNG_COLOR_RGB = 2;
/*      */   static final int PNG_COLOR_PALETTE = 3;
/*      */   static final int PNG_COLOR_GRAY_ALPHA = 4;
/*      */   static final int PNG_COLOR_RGB_ALPHA = 6;
/*  144 */   static final int[] inputBandsForColorType = new int[] { 1, -1, 3, 1, 2, -1, 4 };
/*      */ 
/*      */   
/*      */   static final int PNG_FILTER_NONE = 0;
/*      */ 
/*      */   
/*      */   static final int PNG_FILTER_SUB = 1;
/*      */ 
/*      */   
/*      */   static final int PNG_FILTER_UP = 2;
/*      */ 
/*      */   
/*      */   static final int PNG_FILTER_AVERAGE = 3;
/*      */   
/*      */   static final int PNG_FILTER_PAETH = 4;
/*      */   
/*  160 */   static final int[] adam7XOffset = new int[] { 0, 4, 0, 2, 0, 1, 0 };
/*  161 */   static final int[] adam7YOffset = new int[] { 0, 0, 4, 0, 2, 0, 1 };
/*  162 */   static final int[] adam7XSubsampling = new int[] { 8, 8, 4, 4, 2, 2, 1, 1 };
/*  163 */   static final int[] adam7YSubsampling = new int[] { 8, 8, 8, 4, 4, 2, 2, 1 };
/*      */   
/*      */   private static final boolean debug = true;
/*      */   
/*  167 */   ImageInputStream stream = null;
/*      */   
/*      */   boolean gotHeader = false;
/*      */   
/*      */   boolean gotMetadata = false;
/*  172 */   ImageReadParam lastParam = null;
/*      */   
/*  174 */   long imageStartPosition = -1L;
/*      */   
/*  176 */   Rectangle sourceRegion = null;
/*  177 */   int sourceXSubsampling = -1;
/*  178 */   int sourceYSubsampling = -1;
/*  179 */   int sourceMinProgressivePass = 0;
/*  180 */   int sourceMaxProgressivePass = 6;
/*  181 */   int[] sourceBands = null;
/*  182 */   int[] destinationBands = null;
/*  183 */   Point destinationOffset = new Point(0, 0);
/*      */   
/*  185 */   PNGMetadata metadata = new PNGMetadata();
/*      */   
/*  187 */   DataInputStream pixelStream = null;
/*      */   
/*  189 */   BufferedImage theImage = null;
/*      */ 
/*      */   
/*  192 */   int pixelsDone = 0;
/*      */   
/*      */   int totalPixels;
/*      */ 
/*      */   
/*      */   public PNGImageReader(ImageReaderSpi paramImageReaderSpi) {
/*  198 */     super(paramImageReaderSpi);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInput(Object paramObject, boolean paramBoolean1, boolean paramBoolean2) {
/*  204 */     super.setInput(paramObject, paramBoolean1, paramBoolean2);
/*  205 */     this.stream = (ImageInputStream)paramObject;
/*      */ 
/*      */     
/*  208 */     resetStreamSettings();
/*      */   }
/*      */   
/*      */   private String readNullTerminatedString(String paramString, int paramInt) throws IOException {
/*  212 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*      */     
/*  214 */     byte b = 0; int i;
/*  215 */     while (paramInt > b++ && (i = this.stream.read()) != 0) {
/*  216 */       if (i == -1) throw new EOFException(); 
/*  217 */       byteArrayOutputStream.write(i);
/*      */     } 
/*  219 */     return new String(byteArrayOutputStream.toByteArray(), paramString);
/*      */   }
/*      */   
/*      */   private void readHeader() throws IIOException {
/*  223 */     if (this.gotHeader) {
/*      */       return;
/*      */     }
/*  226 */     if (this.stream == null) {
/*  227 */       throw new IllegalStateException("Input source not set!");
/*      */     }
/*      */     
/*      */     try {
/*  231 */       byte[] arrayOfByte = new byte[8];
/*  232 */       this.stream.readFully(arrayOfByte);
/*      */       
/*  234 */       if (arrayOfByte[0] != -119 || arrayOfByte[1] != 80 || arrayOfByte[2] != 78 || arrayOfByte[3] != 71 || arrayOfByte[4] != 13 || arrayOfByte[5] != 10 || arrayOfByte[6] != 26 || arrayOfByte[7] != 10)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  242 */         throw new IIOException("Bad PNG signature!");
/*      */       }
/*      */       
/*  245 */       int i = this.stream.readInt();
/*  246 */       if (i != 13) {
/*  247 */         throw new IIOException("Bad length for IHDR chunk!");
/*      */       }
/*  249 */       int j = this.stream.readInt();
/*  250 */       if (j != 1229472850) {
/*  251 */         throw new IIOException("Bad type for IHDR chunk!");
/*      */       }
/*      */       
/*  254 */       this.metadata = new PNGMetadata();
/*      */       
/*  256 */       int k = this.stream.readInt();
/*  257 */       int m = this.stream.readInt();
/*      */ 
/*      */       
/*  260 */       this.stream.readFully(arrayOfByte, 0, 5);
/*  261 */       int n = arrayOfByte[0] & 0xFF;
/*  262 */       int i1 = arrayOfByte[1] & 0xFF;
/*  263 */       int i2 = arrayOfByte[2] & 0xFF;
/*  264 */       int i3 = arrayOfByte[3] & 0xFF;
/*  265 */       int i4 = arrayOfByte[4] & 0xFF;
/*      */ 
/*      */       
/*  268 */       this.stream.skipBytes(4);
/*      */       
/*  270 */       this.stream.flushBefore(this.stream.getStreamPosition());
/*      */       
/*  272 */       if (k == 0) {
/*  273 */         throw new IIOException("Image width == 0!");
/*      */       }
/*  275 */       if (m == 0) {
/*  276 */         throw new IIOException("Image height == 0!");
/*      */       }
/*  278 */       if (n != 1 && n != 2 && n != 4 && n != 8 && n != 16)
/*      */       {
/*  280 */         throw new IIOException("Bit depth must be 1, 2, 4, 8, or 16!");
/*      */       }
/*  282 */       if (i1 != 0 && i1 != 2 && i1 != 3 && i1 != 4 && i1 != 6)
/*      */       {
/*  284 */         throw new IIOException("Color type must be 0, 2, 3, 4, or 6!");
/*      */       }
/*  286 */       if (i1 == 3 && n == 16) {
/*  287 */         throw new IIOException("Bad color type/bit depth combination!");
/*      */       }
/*  289 */       if ((i1 == 2 || i1 == 6 || i1 == 4) && n != 8 && n != 16)
/*      */       {
/*      */ 
/*      */         
/*  293 */         throw new IIOException("Bad color type/bit depth combination!");
/*      */       }
/*  295 */       if (i2 != 0) {
/*  296 */         throw new IIOException("Unknown compression method (not 0)!");
/*      */       }
/*  298 */       if (i3 != 0) {
/*  299 */         throw new IIOException("Unknown filter method (not 0)!");
/*      */       }
/*  301 */       if (i4 != 0 && i4 != 1) {
/*  302 */         throw new IIOException("Unknown interlace method (not 0 or 1)!");
/*      */       }
/*      */       
/*  305 */       this.metadata.IHDR_present = true;
/*  306 */       this.metadata.IHDR_width = k;
/*  307 */       this.metadata.IHDR_height = m;
/*  308 */       this.metadata.IHDR_bitDepth = n;
/*  309 */       this.metadata.IHDR_colorType = i1;
/*  310 */       this.metadata.IHDR_compressionMethod = i2;
/*  311 */       this.metadata.IHDR_filterMethod = i3;
/*  312 */       this.metadata.IHDR_interlaceMethod = i4;
/*  313 */       this.gotHeader = true;
/*  314 */     } catch (IOException iOException) {
/*  315 */       throw new IIOException("I/O error reading PNG header!", iOException);
/*      */     } 
/*      */   }
/*      */   private void parse_PLTE_chunk(int paramInt) throws IOException {
/*      */     byte b1;
/*  320 */     if (this.metadata.PLTE_present) {
/*  321 */       processWarningOccurred("A PNG image may not contain more than one PLTE chunk.\nThe chunk wil be ignored.");
/*      */       
/*      */       return;
/*      */     } 
/*  325 */     if (this.metadata.IHDR_colorType == 0 || this.metadata.IHDR_colorType == 4) {
/*      */       
/*  327 */       processWarningOccurred("A PNG gray or gray alpha image cannot have a PLTE chunk.\nThe chunk wil be ignored.");
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  333 */     byte[] arrayOfByte = new byte[paramInt];
/*  334 */     this.stream.readFully(arrayOfByte);
/*      */     
/*  336 */     int i = paramInt / 3;
/*  337 */     if (this.metadata.IHDR_colorType == 3) {
/*  338 */       b1 = 1 << this.metadata.IHDR_bitDepth;
/*  339 */       if (i > b1) {
/*  340 */         processWarningOccurred("PLTE chunk contains too many entries for bit depth, ignoring extras.");
/*      */         
/*  342 */         i = b1;
/*      */       } 
/*  344 */       i = Math.min(i, b1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  349 */     if (i > 16) {
/*  350 */       b1 = 256;
/*  351 */     } else if (i > 4) {
/*  352 */       b1 = 16;
/*  353 */     } else if (i > 2) {
/*  354 */       b1 = 4;
/*      */     } else {
/*  356 */       b1 = 2;
/*      */     } 
/*      */     
/*  359 */     this.metadata.PLTE_present = true;
/*  360 */     this.metadata.PLTE_red = new byte[b1];
/*  361 */     this.metadata.PLTE_green = new byte[b1];
/*  362 */     this.metadata.PLTE_blue = new byte[b1];
/*      */     
/*  364 */     byte b2 = 0;
/*  365 */     for (byte b3 = 0; b3 < i; b3++) {
/*  366 */       this.metadata.PLTE_red[b3] = arrayOfByte[b2++];
/*  367 */       this.metadata.PLTE_green[b3] = arrayOfByte[b2++];
/*  368 */       this.metadata.PLTE_blue[b3] = arrayOfByte[b2++];
/*      */     } 
/*      */   }
/*      */   
/*      */   private void parse_bKGD_chunk() throws IOException {
/*  373 */     if (this.metadata.IHDR_colorType == 3) {
/*  374 */       this.metadata.bKGD_colorType = 3;
/*  375 */       this.metadata.bKGD_index = this.stream.readUnsignedByte();
/*  376 */     } else if (this.metadata.IHDR_colorType == 0 || this.metadata.IHDR_colorType == 4) {
/*      */       
/*  378 */       this.metadata.bKGD_colorType = 0;
/*  379 */       this.metadata.bKGD_gray = this.stream.readUnsignedShort();
/*      */     } else {
/*  381 */       this.metadata.bKGD_colorType = 2;
/*  382 */       this.metadata.bKGD_red = this.stream.readUnsignedShort();
/*  383 */       this.metadata.bKGD_green = this.stream.readUnsignedShort();
/*  384 */       this.metadata.bKGD_blue = this.stream.readUnsignedShort();
/*      */     } 
/*      */     
/*  387 */     this.metadata.bKGD_present = true;
/*      */   }
/*      */   
/*      */   private void parse_cHRM_chunk() throws IOException {
/*  391 */     this.metadata.cHRM_whitePointX = this.stream.readInt();
/*  392 */     this.metadata.cHRM_whitePointY = this.stream.readInt();
/*  393 */     this.metadata.cHRM_redX = this.stream.readInt();
/*  394 */     this.metadata.cHRM_redY = this.stream.readInt();
/*  395 */     this.metadata.cHRM_greenX = this.stream.readInt();
/*  396 */     this.metadata.cHRM_greenY = this.stream.readInt();
/*  397 */     this.metadata.cHRM_blueX = this.stream.readInt();
/*  398 */     this.metadata.cHRM_blueY = this.stream.readInt();
/*      */     
/*  400 */     this.metadata.cHRM_present = true;
/*      */   }
/*      */   
/*      */   private void parse_gAMA_chunk() throws IOException {
/*  404 */     int i = this.stream.readInt();
/*  405 */     this.metadata.gAMA_gamma = i;
/*      */     
/*  407 */     this.metadata.gAMA_present = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void parse_hIST_chunk(int paramInt) throws IOException, IIOException {
/*  413 */     if (!this.metadata.PLTE_present) {
/*  414 */       throw new IIOException("hIST chunk without prior PLTE chunk!");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  422 */     this.metadata.hIST_histogram = new char[paramInt / 2];
/*  423 */     this.stream.readFully(this.metadata.hIST_histogram, 0, this.metadata.hIST_histogram.length);
/*      */ 
/*      */     
/*  426 */     this.metadata.hIST_present = true;
/*      */   }
/*      */   
/*      */   private void parse_iCCP_chunk(int paramInt) throws IOException {
/*  430 */     String str = readNullTerminatedString("ISO-8859-1", 80);
/*  431 */     this.metadata.iCCP_profileName = str;
/*      */     
/*  433 */     this.metadata.iCCP_compressionMethod = this.stream.readUnsignedByte();
/*      */ 
/*      */     
/*  436 */     byte[] arrayOfByte = new byte[paramInt - str.length() - 2];
/*  437 */     this.stream.readFully(arrayOfByte);
/*  438 */     this.metadata.iCCP_compressedProfile = arrayOfByte;
/*      */     
/*  440 */     this.metadata.iCCP_present = true;
/*      */   }
/*      */   private void parse_iTXt_chunk(int paramInt) throws IOException {
/*      */     String str4;
/*  444 */     long l1 = this.stream.getStreamPosition();
/*      */     
/*  446 */     String str1 = readNullTerminatedString("ISO-8859-1", 80);
/*  447 */     this.metadata.iTXt_keyword.add(str1);
/*      */     
/*  449 */     int i = this.stream.readUnsignedByte();
/*  450 */     this.metadata.iTXt_compressionFlag.add(Boolean.valueOf((i == 1)));
/*      */     
/*  452 */     int j = this.stream.readUnsignedByte();
/*  453 */     this.metadata.iTXt_compressionMethod.add(Integer.valueOf(j));
/*      */     
/*  455 */     String str2 = readNullTerminatedString("UTF8", 80);
/*  456 */     this.metadata.iTXt_languageTag.add(str2);
/*      */     
/*  458 */     long l2 = this.stream.getStreamPosition();
/*  459 */     int k = (int)(l1 + paramInt - l2);
/*      */     
/*  461 */     String str3 = readNullTerminatedString("UTF8", k);
/*  462 */     this.metadata.iTXt_translatedKeyword.add(str3);
/*      */ 
/*      */     
/*  465 */     l2 = this.stream.getStreamPosition();
/*  466 */     byte[] arrayOfByte = new byte[(int)(l1 + paramInt - l2)];
/*  467 */     this.stream.readFully(arrayOfByte);
/*      */     
/*  469 */     if (i == 1) {
/*  470 */       str4 = new String(inflate(arrayOfByte), "UTF8");
/*      */     } else {
/*  472 */       str4 = new String(arrayOfByte, "UTF8");
/*      */     } 
/*  474 */     this.metadata.iTXt_text.add(str4);
/*      */   }
/*      */   
/*      */   private void parse_pHYs_chunk() throws IOException {
/*  478 */     this.metadata.pHYs_pixelsPerUnitXAxis = this.stream.readInt();
/*  479 */     this.metadata.pHYs_pixelsPerUnitYAxis = this.stream.readInt();
/*  480 */     this.metadata.pHYs_unitSpecifier = this.stream.readUnsignedByte();
/*      */     
/*  482 */     this.metadata.pHYs_present = true;
/*      */   }
/*      */   
/*      */   private void parse_sBIT_chunk() throws IOException {
/*  486 */     int i = this.metadata.IHDR_colorType;
/*  487 */     if (i == 0 || i == 4) {
/*      */       
/*  489 */       this.metadata.sBIT_grayBits = this.stream.readUnsignedByte();
/*  490 */     } else if (i == 2 || i == 3 || i == 6) {
/*      */ 
/*      */       
/*  493 */       this.metadata.sBIT_redBits = this.stream.readUnsignedByte();
/*  494 */       this.metadata.sBIT_greenBits = this.stream.readUnsignedByte();
/*  495 */       this.metadata.sBIT_blueBits = this.stream.readUnsignedByte();
/*      */     } 
/*      */     
/*  498 */     if (i == 4 || i == 6)
/*      */     {
/*  500 */       this.metadata.sBIT_alphaBits = this.stream.readUnsignedByte();
/*      */     }
/*      */     
/*  503 */     this.metadata.sBIT_colorType = i;
/*  504 */     this.metadata.sBIT_present = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void parse_sPLT_chunk(int paramInt) throws IOException, IIOException {
/*  509 */     this.metadata.sPLT_paletteName = readNullTerminatedString("ISO-8859-1", 80);
/*  510 */     paramInt -= this.metadata.sPLT_paletteName.length() + 1;
/*      */     
/*  512 */     int i = this.stream.readUnsignedByte();
/*  513 */     this.metadata.sPLT_sampleDepth = i;
/*      */     
/*  515 */     int j = paramInt / (4 * i / 8 + 2);
/*  516 */     this.metadata.sPLT_red = new int[j];
/*  517 */     this.metadata.sPLT_green = new int[j];
/*  518 */     this.metadata.sPLT_blue = new int[j];
/*  519 */     this.metadata.sPLT_alpha = new int[j];
/*  520 */     this.metadata.sPLT_frequency = new int[j];
/*      */     
/*  522 */     if (i == 8) {
/*  523 */       for (byte b = 0; b < j; b++) {
/*  524 */         this.metadata.sPLT_red[b] = this.stream.readUnsignedByte();
/*  525 */         this.metadata.sPLT_green[b] = this.stream.readUnsignedByte();
/*  526 */         this.metadata.sPLT_blue[b] = this.stream.readUnsignedByte();
/*  527 */         this.metadata.sPLT_alpha[b] = this.stream.readUnsignedByte();
/*  528 */         this.metadata.sPLT_frequency[b] = this.stream.readUnsignedShort();
/*      */       } 
/*  530 */     } else if (i == 16) {
/*  531 */       for (byte b = 0; b < j; b++) {
/*  532 */         this.metadata.sPLT_red[b] = this.stream.readUnsignedShort();
/*  533 */         this.metadata.sPLT_green[b] = this.stream.readUnsignedShort();
/*  534 */         this.metadata.sPLT_blue[b] = this.stream.readUnsignedShort();
/*  535 */         this.metadata.sPLT_alpha[b] = this.stream.readUnsignedShort();
/*  536 */         this.metadata.sPLT_frequency[b] = this.stream.readUnsignedShort();
/*      */       } 
/*      */     } else {
/*  539 */       throw new IIOException("sPLT sample depth not 8 or 16!");
/*      */     } 
/*      */     
/*  542 */     this.metadata.sPLT_present = true;
/*      */   }
/*      */   
/*      */   private void parse_sRGB_chunk() throws IOException {
/*  546 */     this.metadata.sRGB_renderingIntent = this.stream.readUnsignedByte();
/*      */     
/*  548 */     this.metadata.sRGB_present = true;
/*      */   }
/*      */   
/*      */   private void parse_tEXt_chunk(int paramInt) throws IOException {
/*  552 */     String str = readNullTerminatedString("ISO-8859-1", 80);
/*  553 */     this.metadata.tEXt_keyword.add(str);
/*      */     
/*  555 */     byte[] arrayOfByte = new byte[paramInt - str.length() - 1];
/*  556 */     this.stream.readFully(arrayOfByte);
/*  557 */     this.metadata.tEXt_text.add(new String(arrayOfByte, "ISO-8859-1"));
/*      */   }
/*      */   
/*      */   private void parse_tIME_chunk() throws IOException {
/*  561 */     this.metadata.tIME_year = this.stream.readUnsignedShort();
/*  562 */     this.metadata.tIME_month = this.stream.readUnsignedByte();
/*  563 */     this.metadata.tIME_day = this.stream.readUnsignedByte();
/*  564 */     this.metadata.tIME_hour = this.stream.readUnsignedByte();
/*  565 */     this.metadata.tIME_minute = this.stream.readUnsignedByte();
/*  566 */     this.metadata.tIME_second = this.stream.readUnsignedByte();
/*      */     
/*  568 */     this.metadata.tIME_present = true;
/*      */   }
/*      */   
/*      */   private void parse_tRNS_chunk(int paramInt) throws IOException {
/*  572 */     int i = this.metadata.IHDR_colorType;
/*  573 */     if (i == 3) {
/*  574 */       if (!this.metadata.PLTE_present) {
/*  575 */         processWarningOccurred("tRNS chunk without prior PLTE chunk, ignoring it.");
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  581 */       int j = this.metadata.PLTE_red.length;
/*  582 */       int k = paramInt;
/*  583 */       if (k > j) {
/*  584 */         processWarningOccurred("tRNS chunk has more entries than prior PLTE chunk, ignoring extras.");
/*      */         
/*  586 */         k = j;
/*      */       } 
/*  588 */       this.metadata.tRNS_alpha = new byte[k];
/*  589 */       this.metadata.tRNS_colorType = 3;
/*  590 */       this.stream.read(this.metadata.tRNS_alpha, 0, k);
/*  591 */       this.stream.skipBytes(paramInt - k);
/*  592 */     } else if (i == 0) {
/*  593 */       if (paramInt != 2) {
/*  594 */         processWarningOccurred("tRNS chunk for gray image must have length 2, ignoring chunk.");
/*      */         
/*  596 */         this.stream.skipBytes(paramInt);
/*      */         return;
/*      */       } 
/*  599 */       this.metadata.tRNS_gray = this.stream.readUnsignedShort();
/*  600 */       this.metadata.tRNS_colorType = 0;
/*  601 */     } else if (i == 2) {
/*  602 */       if (paramInt != 6) {
/*  603 */         processWarningOccurred("tRNS chunk for RGB image must have length 6, ignoring chunk.");
/*      */         
/*  605 */         this.stream.skipBytes(paramInt);
/*      */         return;
/*      */       } 
/*  608 */       this.metadata.tRNS_red = this.stream.readUnsignedShort();
/*  609 */       this.metadata.tRNS_green = this.stream.readUnsignedShort();
/*  610 */       this.metadata.tRNS_blue = this.stream.readUnsignedShort();
/*  611 */       this.metadata.tRNS_colorType = 2;
/*      */     } else {
/*  613 */       processWarningOccurred("Gray+Alpha and RGBS images may not have a tRNS chunk, ignoring it.");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  618 */     this.metadata.tRNS_present = true;
/*      */   }
/*      */   
/*      */   private static byte[] inflate(byte[] paramArrayOfbyte) throws IOException {
/*  622 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*  623 */     InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
/*  624 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*      */     
/*      */     try {
/*      */       int i;
/*  628 */       while ((i = inflaterInputStream.read()) != -1) {
/*  629 */         byteArrayOutputStream.write(i);
/*      */       }
/*      */     } finally {
/*  632 */       inflaterInputStream.close();
/*      */     } 
/*  634 */     return byteArrayOutputStream.toByteArray();
/*      */   }
/*      */   
/*      */   private void parse_zTXt_chunk(int paramInt) throws IOException {
/*  638 */     String str = readNullTerminatedString("ISO-8859-1", 80);
/*  639 */     this.metadata.zTXt_keyword.add(str);
/*      */     
/*  641 */     int i = this.stream.readUnsignedByte();
/*  642 */     this.metadata.zTXt_compressionMethod.add(new Integer(i));
/*      */     
/*  644 */     byte[] arrayOfByte = new byte[paramInt - str.length() - 2];
/*  645 */     this.stream.readFully(arrayOfByte);
/*  646 */     this.metadata.zTXt_text.add(new String(inflate(arrayOfByte), "ISO-8859-1"));
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
/*      */   private void readMetadata() throws IIOException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield gotMetadata : Z
/*      */     //   4: ifeq -> 8
/*      */     //   7: return
/*      */     //   8: aload_0
/*      */     //   9: invokespecial readHeader : ()V
/*      */     //   12: aload_0
/*      */     //   13: getfield metadata : Lcom/sun/imageio/plugins/png/PNGMetadata;
/*      */     //   16: getfield IHDR_colorType : I
/*      */     //   19: istore_1
/*      */     //   20: aload_0
/*      */     //   21: getfield ignoreMetadata : Z
/*      */     //   24: ifeq -> 123
/*      */     //   27: iload_1
/*      */     //   28: iconst_3
/*      */     //   29: if_icmpeq -> 123
/*      */     //   32: aload_0
/*      */     //   33: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   36: invokeinterface readInt : ()I
/*      */     //   41: istore_2
/*      */     //   42: aload_0
/*      */     //   43: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   46: invokeinterface readInt : ()I
/*      */     //   51: istore_3
/*      */     //   52: iload_3
/*      */     //   53: ldc 1229209940
/*      */     //   55: if_icmpne -> 86
/*      */     //   58: aload_0
/*      */     //   59: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   62: bipush #-8
/*      */     //   64: invokeinterface skipBytes : (I)I
/*      */     //   69: pop
/*      */     //   70: aload_0
/*      */     //   71: aload_0
/*      */     //   72: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   75: invokeinterface getStreamPosition : ()J
/*      */     //   80: putfield imageStartPosition : J
/*      */     //   83: goto -> 102
/*      */     //   86: aload_0
/*      */     //   87: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   90: iload_2
/*      */     //   91: iconst_4
/*      */     //   92: iadd
/*      */     //   93: invokeinterface skipBytes : (I)I
/*      */     //   98: pop
/*      */     //   99: goto -> 32
/*      */     //   102: goto -> 117
/*      */     //   105: astore_2
/*      */     //   106: new javax/imageio/IIOException
/*      */     //   109: dup
/*      */     //   110: ldc 'Error skipping PNG metadata'
/*      */     //   112: aload_2
/*      */     //   113: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   116: athrow
/*      */     //   117: aload_0
/*      */     //   118: iconst_1
/*      */     //   119: putfield gotMetadata : Z
/*      */     //   122: return
/*      */     //   123: aload_0
/*      */     //   124: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   127: invokeinterface readInt : ()I
/*      */     //   132: istore_2
/*      */     //   133: aload_0
/*      */     //   134: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   137: invokeinterface readInt : ()I
/*      */     //   142: istore_3
/*      */     //   143: iload_2
/*      */     //   144: ifge -> 174
/*      */     //   147: new javax/imageio/IIOException
/*      */     //   150: dup
/*      */     //   151: new java/lang/StringBuilder
/*      */     //   154: dup
/*      */     //   155: invokespecial <init> : ()V
/*      */     //   158: ldc 'Invalid chunk lenght '
/*      */     //   160: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   163: iload_2
/*      */     //   164: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   167: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   170: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   173: athrow
/*      */     //   174: aload_0
/*      */     //   175: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   178: invokeinterface mark : ()V
/*      */     //   183: aload_0
/*      */     //   184: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   187: aload_0
/*      */     //   188: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   191: invokeinterface getStreamPosition : ()J
/*      */     //   196: iload_2
/*      */     //   197: i2l
/*      */     //   198: ladd
/*      */     //   199: invokeinterface seek : (J)V
/*      */     //   204: aload_0
/*      */     //   205: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   208: invokeinterface readInt : ()I
/*      */     //   213: istore #4
/*      */     //   215: aload_0
/*      */     //   216: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   219: invokeinterface reset : ()V
/*      */     //   224: goto -> 256
/*      */     //   227: astore #5
/*      */     //   229: new javax/imageio/IIOException
/*      */     //   232: dup
/*      */     //   233: new java/lang/StringBuilder
/*      */     //   236: dup
/*      */     //   237: invokespecial <init> : ()V
/*      */     //   240: ldc 'Invalid chunk length '
/*      */     //   242: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   245: iload_2
/*      */     //   246: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   249: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   252: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   255: athrow
/*      */     //   256: iload_3
/*      */     //   257: lookupswitch default -> 579, 1229209940 -> 396, 1347179589 -> 424, 1649100612 -> 432, 1665684045 -> 439, 1732332865 -> 446, 1749635924 -> 453, 1766015824 -> 461, 1767135348 -> 469, 1883789683 -> 498, 1933723988 -> 505, 1934642260 -> 512, 1934772034 -> 520, 1950701684 -> 527, 1950960965 -> 535, 1951551059 -> 542, 2052348020 -> 550
/*      */     //   396: aload_0
/*      */     //   397: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   400: bipush #-8
/*      */     //   402: invokeinterface skipBytes : (I)I
/*      */     //   407: pop
/*      */     //   408: aload_0
/*      */     //   409: aload_0
/*      */     //   410: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   413: invokeinterface getStreamPosition : ()J
/*      */     //   418: putfield imageStartPosition : J
/*      */     //   421: goto -> 766
/*      */     //   424: aload_0
/*      */     //   425: iload_2
/*      */     //   426: invokespecial parse_PLTE_chunk : (I)V
/*      */     //   429: goto -> 704
/*      */     //   432: aload_0
/*      */     //   433: invokespecial parse_bKGD_chunk : ()V
/*      */     //   436: goto -> 704
/*      */     //   439: aload_0
/*      */     //   440: invokespecial parse_cHRM_chunk : ()V
/*      */     //   443: goto -> 704
/*      */     //   446: aload_0
/*      */     //   447: invokespecial parse_gAMA_chunk : ()V
/*      */     //   450: goto -> 704
/*      */     //   453: aload_0
/*      */     //   454: iload_2
/*      */     //   455: invokespecial parse_hIST_chunk : (I)V
/*      */     //   458: goto -> 704
/*      */     //   461: aload_0
/*      */     //   462: iload_2
/*      */     //   463: invokespecial parse_iCCP_chunk : (I)V
/*      */     //   466: goto -> 704
/*      */     //   469: aload_0
/*      */     //   470: getfield ignoreMetadata : Z
/*      */     //   473: ifeq -> 490
/*      */     //   476: aload_0
/*      */     //   477: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   480: iload_2
/*      */     //   481: invokeinterface skipBytes : (I)I
/*      */     //   486: pop
/*      */     //   487: goto -> 704
/*      */     //   490: aload_0
/*      */     //   491: iload_2
/*      */     //   492: invokespecial parse_iTXt_chunk : (I)V
/*      */     //   495: goto -> 704
/*      */     //   498: aload_0
/*      */     //   499: invokespecial parse_pHYs_chunk : ()V
/*      */     //   502: goto -> 704
/*      */     //   505: aload_0
/*      */     //   506: invokespecial parse_sBIT_chunk : ()V
/*      */     //   509: goto -> 704
/*      */     //   512: aload_0
/*      */     //   513: iload_2
/*      */     //   514: invokespecial parse_sPLT_chunk : (I)V
/*      */     //   517: goto -> 704
/*      */     //   520: aload_0
/*      */     //   521: invokespecial parse_sRGB_chunk : ()V
/*      */     //   524: goto -> 704
/*      */     //   527: aload_0
/*      */     //   528: iload_2
/*      */     //   529: invokespecial parse_tEXt_chunk : (I)V
/*      */     //   532: goto -> 704
/*      */     //   535: aload_0
/*      */     //   536: invokespecial parse_tIME_chunk : ()V
/*      */     //   539: goto -> 704
/*      */     //   542: aload_0
/*      */     //   543: iload_2
/*      */     //   544: invokespecial parse_tRNS_chunk : (I)V
/*      */     //   547: goto -> 704
/*      */     //   550: aload_0
/*      */     //   551: getfield ignoreMetadata : Z
/*      */     //   554: ifeq -> 571
/*      */     //   557: aload_0
/*      */     //   558: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   561: iload_2
/*      */     //   562: invokeinterface skipBytes : (I)I
/*      */     //   567: pop
/*      */     //   568: goto -> 704
/*      */     //   571: aload_0
/*      */     //   572: iload_2
/*      */     //   573: invokespecial parse_zTXt_chunk : (I)V
/*      */     //   576: goto -> 704
/*      */     //   579: iload_2
/*      */     //   580: newarray byte
/*      */     //   582: astore #5
/*      */     //   584: aload_0
/*      */     //   585: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   588: aload #5
/*      */     //   590: invokeinterface readFully : ([B)V
/*      */     //   595: new java/lang/StringBuilder
/*      */     //   598: dup
/*      */     //   599: iconst_4
/*      */     //   600: invokespecial <init> : (I)V
/*      */     //   603: astore #6
/*      */     //   605: aload #6
/*      */     //   607: iload_3
/*      */     //   608: bipush #24
/*      */     //   610: iushr
/*      */     //   611: i2c
/*      */     //   612: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   615: pop
/*      */     //   616: aload #6
/*      */     //   618: iload_3
/*      */     //   619: bipush #16
/*      */     //   621: ishr
/*      */     //   622: sipush #255
/*      */     //   625: iand
/*      */     //   626: i2c
/*      */     //   627: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   630: pop
/*      */     //   631: aload #6
/*      */     //   633: iload_3
/*      */     //   634: bipush #8
/*      */     //   636: ishr
/*      */     //   637: sipush #255
/*      */     //   640: iand
/*      */     //   641: i2c
/*      */     //   642: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   645: pop
/*      */     //   646: aload #6
/*      */     //   648: iload_3
/*      */     //   649: sipush #255
/*      */     //   652: iand
/*      */     //   653: i2c
/*      */     //   654: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   657: pop
/*      */     //   658: iload_3
/*      */     //   659: bipush #28
/*      */     //   661: iushr
/*      */     //   662: istore #7
/*      */     //   664: iload #7
/*      */     //   666: ifne -> 675
/*      */     //   669: aload_0
/*      */     //   670: ldc 'Encountered unknown chunk with critical bit set!'
/*      */     //   672: invokevirtual processWarningOccurred : (Ljava/lang/String;)V
/*      */     //   675: aload_0
/*      */     //   676: getfield metadata : Lcom/sun/imageio/plugins/png/PNGMetadata;
/*      */     //   679: getfield unknownChunkType : Ljava/util/ArrayList;
/*      */     //   682: aload #6
/*      */     //   684: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   687: invokevirtual add : (Ljava/lang/Object;)Z
/*      */     //   690: pop
/*      */     //   691: aload_0
/*      */     //   692: getfield metadata : Lcom/sun/imageio/plugins/png/PNGMetadata;
/*      */     //   695: getfield unknownChunkData : Ljava/util/ArrayList;
/*      */     //   698: aload #5
/*      */     //   700: invokevirtual add : (Ljava/lang/Object;)Z
/*      */     //   703: pop
/*      */     //   704: iload #4
/*      */     //   706: aload_0
/*      */     //   707: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   710: invokeinterface readInt : ()I
/*      */     //   715: if_icmpeq -> 745
/*      */     //   718: new javax/imageio/IIOException
/*      */     //   721: dup
/*      */     //   722: new java/lang/StringBuilder
/*      */     //   725: dup
/*      */     //   726: invokespecial <init> : ()V
/*      */     //   729: ldc 'Failed to read a chunk of type '
/*      */     //   731: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   734: iload_3
/*      */     //   735: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   738: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   741: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   744: athrow
/*      */     //   745: aload_0
/*      */     //   746: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   749: aload_0
/*      */     //   750: getfield stream : Ljavax/imageio/stream/ImageInputStream;
/*      */     //   753: invokeinterface getStreamPosition : ()J
/*      */     //   758: invokeinterface flushBefore : (J)V
/*      */     //   763: goto -> 123
/*      */     //   766: goto -> 781
/*      */     //   769: astore_2
/*      */     //   770: new javax/imageio/IIOException
/*      */     //   773: dup
/*      */     //   774: ldc 'Error reading PNG metadata'
/*      */     //   776: aload_2
/*      */     //   777: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   780: athrow
/*      */     //   781: aload_0
/*      */     //   782: iconst_1
/*      */     //   783: putfield gotMetadata : Z
/*      */     //   786: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #650	-> 0
/*      */     //   #651	-> 7
/*      */     //   #654	-> 8
/*      */     //   #662	-> 12
/*      */     //   #663	-> 20
/*      */     //   #666	-> 32
/*      */     //   #667	-> 42
/*      */     //   #669	-> 52
/*      */     //   #671	-> 58
/*      */     //   #672	-> 70
/*      */     //   #673	-> 83
/*      */     //   #676	-> 86
/*      */     //   #678	-> 99
/*      */     //   #681	-> 102
/*      */     //   #679	-> 105
/*      */     //   #680	-> 106
/*      */     //   #683	-> 117
/*      */     //   #684	-> 122
/*      */     //   #689	-> 123
/*      */     //   #690	-> 133
/*      */     //   #694	-> 143
/*      */     //   #695	-> 147
/*      */     //   #699	-> 174
/*      */     //   #700	-> 183
/*      */     //   #701	-> 204
/*      */     //   #702	-> 215
/*      */     //   #705	-> 224
/*      */     //   #703	-> 227
/*      */     //   #704	-> 229
/*      */     //   #707	-> 256
/*      */     //   #710	-> 396
/*      */     //   #711	-> 408
/*      */     //   #712	-> 421
/*      */     //   #714	-> 424
/*      */     //   #715	-> 429
/*      */     //   #717	-> 432
/*      */     //   #718	-> 436
/*      */     //   #720	-> 439
/*      */     //   #721	-> 443
/*      */     //   #723	-> 446
/*      */     //   #724	-> 450
/*      */     //   #726	-> 453
/*      */     //   #727	-> 458
/*      */     //   #729	-> 461
/*      */     //   #730	-> 466
/*      */     //   #732	-> 469
/*      */     //   #733	-> 476
/*      */     //   #735	-> 490
/*      */     //   #737	-> 495
/*      */     //   #739	-> 498
/*      */     //   #740	-> 502
/*      */     //   #742	-> 505
/*      */     //   #743	-> 509
/*      */     //   #745	-> 512
/*      */     //   #746	-> 517
/*      */     //   #748	-> 520
/*      */     //   #749	-> 524
/*      */     //   #751	-> 527
/*      */     //   #752	-> 532
/*      */     //   #754	-> 535
/*      */     //   #755	-> 539
/*      */     //   #757	-> 542
/*      */     //   #758	-> 547
/*      */     //   #760	-> 550
/*      */     //   #761	-> 557
/*      */     //   #763	-> 571
/*      */     //   #765	-> 576
/*      */     //   #768	-> 579
/*      */     //   #769	-> 584
/*      */     //   #771	-> 595
/*      */     //   #772	-> 605
/*      */     //   #773	-> 616
/*      */     //   #774	-> 631
/*      */     //   #775	-> 646
/*      */     //   #777	-> 658
/*      */     //   #778	-> 664
/*      */     //   #779	-> 669
/*      */     //   #783	-> 675
/*      */     //   #784	-> 691
/*      */     //   #789	-> 704
/*      */     //   #790	-> 718
/*      */     //   #793	-> 745
/*      */     //   #794	-> 763
/*      */     //   #797	-> 766
/*      */     //   #795	-> 769
/*      */     //   #796	-> 770
/*      */     //   #799	-> 781
/*      */     //   #800	-> 786
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   32	102	105	java/io/IOException
/*      */     //   123	766	769	java/io/IOException
/*      */     //   174	224	227	java/io/IOException
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
/*      */   private static void decodeSubFilter(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
/*  806 */     for (int i = paramInt3; i < paramInt2; i++) {
/*      */ 
/*      */       
/*  809 */       int j = paramArrayOfbyte[i + paramInt1] & 0xFF;
/*  810 */       j += paramArrayOfbyte[i + paramInt1 - paramInt3] & 0xFF;
/*      */       
/*  812 */       paramArrayOfbyte[i + paramInt1] = (byte)j;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void decodeUpFilter(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) {
/*  819 */     for (byte b = 0; b < paramInt3; b++) {
/*  820 */       int i = paramArrayOfbyte1[b + paramInt1] & 0xFF;
/*  821 */       int j = paramArrayOfbyte2[b + paramInt2] & 0xFF;
/*      */       
/*  823 */       paramArrayOfbyte1[b + paramInt1] = (byte)(i + j);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void decodeAverageFilter(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3, int paramInt4) {
/*      */     int i;
/*  832 */     for (i = 0; i < paramInt4; i++) {
/*  833 */       int j = paramArrayOfbyte1[i + paramInt1] & 0xFF;
/*  834 */       int k = paramArrayOfbyte2[i + paramInt2] & 0xFF;
/*      */       
/*  836 */       paramArrayOfbyte1[i + paramInt1] = (byte)(j + k / 2);
/*      */     } 
/*      */     
/*  839 */     for (i = paramInt4; i < paramInt3; i++) {
/*  840 */       int j = paramArrayOfbyte1[i + paramInt1] & 0xFF;
/*  841 */       int k = paramArrayOfbyte1[i + paramInt1 - paramInt4] & 0xFF;
/*  842 */       int m = paramArrayOfbyte2[i + paramInt2] & 0xFF;
/*      */       
/*  844 */       paramArrayOfbyte1[i + paramInt1] = (byte)(j + (k + m) / 2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int paethPredictor(int paramInt1, int paramInt2, int paramInt3) {
/*  849 */     int i = paramInt1 + paramInt2 - paramInt3;
/*  850 */     int j = Math.abs(i - paramInt1);
/*  851 */     int k = Math.abs(i - paramInt2);
/*  852 */     int m = Math.abs(i - paramInt3);
/*      */     
/*  854 */     if (j <= k && j <= m)
/*  855 */       return paramInt1; 
/*  856 */     if (k <= m) {
/*  857 */       return paramInt2;
/*      */     }
/*  859 */     return paramInt3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void decodePaethFilter(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3, int paramInt4) {
/*      */     int i;
/*  868 */     for (i = 0; i < paramInt4; i++) {
/*  869 */       int j = paramArrayOfbyte1[i + paramInt1] & 0xFF;
/*  870 */       int k = paramArrayOfbyte2[i + paramInt2] & 0xFF;
/*      */       
/*  872 */       paramArrayOfbyte1[i + paramInt1] = (byte)(j + k);
/*      */     } 
/*      */     
/*  875 */     for (i = paramInt4; i < paramInt3; i++) {
/*  876 */       int j = paramArrayOfbyte1[i + paramInt1] & 0xFF;
/*  877 */       int k = paramArrayOfbyte1[i + paramInt1 - paramInt4] & 0xFF;
/*  878 */       int m = paramArrayOfbyte2[i + paramInt2] & 0xFF;
/*  879 */       int n = paramArrayOfbyte2[i + paramInt2 - paramInt4] & 0xFF;
/*      */       
/*  881 */       paramArrayOfbyte1[i + paramInt1] = (byte)(j + paethPredictor(k, m, n));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  887 */   private static final int[][] bandOffsets = new int[][] { null, { 0 }, { 0, 1 }, { 0, 1, 2 }, { 0, 1, 2, 3 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WritableRaster createRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  900 */     WritableRaster writableRaster = null;
/*  901 */     Point point = new Point(0, 0);
/*  902 */     if (paramInt5 < 8 && paramInt3 == 1) {
/*  903 */       DataBufferByte dataBufferByte = new DataBufferByte(paramInt2 * paramInt4);
/*  904 */       writableRaster = Raster.createPackedRaster(dataBufferByte, paramInt1, paramInt2, paramInt5, point);
/*      */ 
/*      */     
/*      */     }
/*  908 */     else if (paramInt5 <= 8) {
/*  909 */       DataBufferByte dataBufferByte = new DataBufferByte(paramInt2 * paramInt4);
/*  910 */       writableRaster = Raster.createInterleavedRaster(dataBufferByte, paramInt1, paramInt2, paramInt4, paramInt3, bandOffsets[paramInt3], point);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  917 */       DataBufferUShort dataBufferUShort = new DataBufferUShort(paramInt2 * paramInt4);
/*  918 */       writableRaster = Raster.createInterleavedRaster(dataBufferUShort, paramInt1, paramInt2, paramInt4, paramInt3, bandOffsets[paramInt3], point);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  926 */     return writableRaster;
/*      */   }
/*      */ 
/*      */   
/*      */   private void skipPass(int paramInt1, int paramInt2) throws IOException, IIOException {
/*  931 */     if (paramInt1 == 0 || paramInt2 == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  935 */     int i = inputBandsForColorType[this.metadata.IHDR_colorType];
/*  936 */     int j = (i * paramInt1 * this.metadata.IHDR_bitDepth + 7) / 8;
/*      */ 
/*      */     
/*  939 */     for (byte b = 0; b < paramInt2; b++) {
/*      */       
/*  941 */       this.pixelStream.skipBytes(1 + j);
/*      */ 
/*      */ 
/*      */       
/*  945 */       if (abortRequested()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateImageProgress(int paramInt) {
/*  952 */     this.pixelsDone += paramInt;
/*  953 */     processImageProgress(100.0F * this.pixelsDone / this.totalPixels);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void decodePass(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws IOException {
/*  961 */     if (paramInt6 == 0 || paramInt7 == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  965 */     WritableRaster writableRaster1 = this.theImage.getWritableTile(0, 0);
/*  966 */     int i = writableRaster1.getMinX();
/*  967 */     int j = i + writableRaster1.getWidth() - 1;
/*  968 */     int k = writableRaster1.getMinY();
/*  969 */     int m = k + writableRaster1.getHeight() - 1;
/*      */ 
/*      */ 
/*      */     
/*  973 */     int[] arrayOfInt1 = ReaderUtil.computeUpdatedPixels(this.sourceRegion, this.destinationOffset, i, k, j, m, this.sourceXSubsampling, this.sourceYSubsampling, paramInt2, paramInt3, paramInt6, paramInt7, paramInt4, paramInt5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  982 */     int n = arrayOfInt1[0];
/*  983 */     int i1 = arrayOfInt1[1];
/*  984 */     int i2 = arrayOfInt1[2];
/*  985 */     int i3 = arrayOfInt1[4];
/*  986 */     int i4 = arrayOfInt1[5];
/*      */     
/*  988 */     int i5 = this.metadata.IHDR_bitDepth;
/*  989 */     int i6 = inputBandsForColorType[this.metadata.IHDR_colorType];
/*  990 */     int i7 = (i5 == 16) ? 2 : 1;
/*  991 */     i7 *= i6;
/*      */     
/*  993 */     int i8 = (i6 * paramInt6 * i5 + 7) / 8;
/*  994 */     int i9 = (i5 == 16) ? (i8 / 2) : i8;
/*      */ 
/*      */     
/*  997 */     if (i2 == 0) {
/*  998 */       for (byte b = 0; b < paramInt7; b++) {
/*      */         
/* 1000 */         updateImageProgress(paramInt6);
/*      */         
/* 1002 */         this.pixelStream.skipBytes(1 + i8);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1011 */     int i10 = (n - this.destinationOffset.x) * this.sourceXSubsampling + this.sourceRegion.x;
/*      */ 
/*      */     
/* 1014 */     int i11 = (i10 - paramInt2) / paramInt4;
/*      */ 
/*      */     
/* 1017 */     int i12 = i3 * this.sourceXSubsampling / paramInt4;
/*      */     
/* 1019 */     byte[] arrayOfByte1 = null;
/* 1020 */     short[] arrayOfShort = null;
/* 1021 */     byte[] arrayOfByte2 = new byte[i8];
/* 1022 */     byte[] arrayOfByte3 = new byte[i8];
/*      */ 
/*      */     
/* 1025 */     WritableRaster writableRaster2 = createRaster(paramInt6, 1, i6, i9, i5);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1030 */     int[] arrayOfInt2 = writableRaster2.getPixel(0, 0, (int[])null);
/*      */     
/* 1032 */     DataBuffer dataBuffer = writableRaster2.getDataBuffer();
/* 1033 */     int i13 = dataBuffer.getDataType();
/* 1034 */     if (i13 == 0) {
/* 1035 */       arrayOfByte1 = ((DataBufferByte)dataBuffer).getData();
/*      */     } else {
/* 1037 */       arrayOfShort = ((DataBufferUShort)dataBuffer).getData();
/*      */     } 
/*      */     
/* 1040 */     processPassStarted(this.theImage, paramInt1, this.sourceMinProgressivePass, this.sourceMaxProgressivePass, n, i1, i3, i4, this.destinationBands);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1049 */     if (this.sourceBands != null) {
/* 1050 */       writableRaster2 = writableRaster2.createWritableChild(0, 0, writableRaster2
/* 1051 */           .getWidth(), 1, 0, 0, this.sourceBands);
/*      */     }
/*      */ 
/*      */     
/* 1055 */     if (this.destinationBands != null) {
/* 1056 */       writableRaster1 = writableRaster1.createWritableChild(0, 0, writableRaster1
/* 1057 */           .getWidth(), writableRaster1
/* 1058 */           .getHeight(), 0, 0, this.destinationBands);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1065 */     boolean bool1 = false;
/* 1066 */     int[] arrayOfInt3 = writableRaster1.getSampleModel().getSampleSize();
/* 1067 */     int i14 = arrayOfInt3.length;
/* 1068 */     for (byte b1 = 0; b1 < i14; b1++) {
/* 1069 */       if (arrayOfInt3[b1] != i5) {
/* 1070 */         bool1 = true;
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1077 */     int[][] arrayOfInt = (int[][])null;
/* 1078 */     if (bool1) {
/* 1079 */       int i15 = (1 << i5) - 1;
/* 1080 */       int i16 = i15 / 2;
/* 1081 */       arrayOfInt = new int[i14][];
/* 1082 */       for (byte b = 0; b < i14; b++) {
/* 1083 */         int i17 = (1 << arrayOfInt3[b]) - 1;
/* 1084 */         arrayOfInt[b] = new int[i15 + 1];
/* 1085 */         for (byte b3 = 0; b3 <= i15; b3++) {
/* 1086 */           arrayOfInt[b][b3] = (b3 * i17 + i16) / i15;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1094 */     boolean bool2 = (i12 == 1 && i3 == 1 && !bool1 && writableRaster1 instanceof sun.awt.image.ByteInterleavedRaster) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1099 */     if (bool2) {
/* 1100 */       writableRaster2 = writableRaster2.createWritableChild(i11, 0, i2, 1, 0, 0, (int[])null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1107 */     for (byte b2 = 0; b2 < paramInt7; b2++) {
/*      */       
/* 1109 */       updateImageProgress(paramInt6);
/*      */ 
/*      */       
/* 1112 */       int i15 = this.pixelStream.read();
/*      */       
/*      */       try {
/* 1115 */         byte[] arrayOfByte = arrayOfByte3;
/* 1116 */         arrayOfByte3 = arrayOfByte2;
/* 1117 */         arrayOfByte2 = arrayOfByte;
/*      */         
/* 1119 */         this.pixelStream.readFully(arrayOfByte2, 0, i8);
/* 1120 */       } catch (ZipException zipException) {
/*      */         
/* 1122 */         throw zipException;
/*      */       } 
/*      */       
/* 1125 */       switch (i15) {
/*      */         case 0:
/*      */           break;
/*      */         case 1:
/* 1129 */           decodeSubFilter(arrayOfByte2, 0, i8, i7);
/*      */           break;
/*      */         case 2:
/* 1132 */           decodeUpFilter(arrayOfByte2, 0, arrayOfByte3, 0, i8);
/*      */           break;
/*      */         case 3:
/* 1135 */           decodeAverageFilter(arrayOfByte2, 0, arrayOfByte3, 0, i8, i7);
/*      */           break;
/*      */         
/*      */         case 4:
/* 1139 */           decodePaethFilter(arrayOfByte2, 0, arrayOfByte3, 0, i8, i7);
/*      */           break;
/*      */         
/*      */         default:
/* 1143 */           throw new IIOException("Unknown row filter type (= " + i15 + ")!");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1148 */       if (i5 < 16) {
/* 1149 */         System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, i8);
/*      */       } else {
/* 1151 */         byte b3 = 0;
/* 1152 */         for (byte b4 = 0; b4 < i9; b4++) {
/* 1153 */           arrayOfShort[b4] = (short)(arrayOfByte2[b3] << 8 | arrayOfByte2[b3 + 1] & 0xFF);
/*      */           
/* 1155 */           b3 += 2;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1160 */       int i16 = b2 * paramInt5 + paramInt3;
/* 1161 */       if (i16 >= this.sourceRegion.y && i16 < this.sourceRegion.y + this.sourceRegion.height && (i16 - this.sourceRegion.y) % this.sourceYSubsampling == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1166 */         int i17 = this.destinationOffset.y + (i16 - this.sourceRegion.y) / this.sourceYSubsampling;
/*      */         
/* 1168 */         if (i17 >= k) {
/*      */ 
/*      */           
/* 1171 */           if (i17 > m) {
/*      */             break;
/*      */           }
/*      */           
/* 1175 */           if (bool2) {
/* 1176 */             writableRaster1.setRect(n, i17, writableRaster2);
/*      */           } else {
/* 1178 */             int i18 = i11;
/*      */             
/* 1180 */             int i19 = n;
/* 1181 */             for (; i19 < n + i2; 
/* 1182 */               i19 += i3) {
/*      */               
/* 1184 */               writableRaster2.getPixel(i18, 0, arrayOfInt2);
/* 1185 */               if (bool1) {
/* 1186 */                 for (byte b = 0; b < i14; b++) {
/* 1187 */                   arrayOfInt2[b] = arrayOfInt[b][arrayOfInt2[b]];
/*      */                 }
/*      */               }
/* 1190 */               writableRaster1.setPixel(i19, i17, arrayOfInt2);
/* 1191 */               i18 += i12;
/*      */             } 
/*      */           } 
/*      */           
/* 1195 */           processImageUpdate(this.theImage, n, i17, i2, 1, i3, i4, this.destinationBands);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1203 */           if (abortRequested()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1209 */     processPassComplete(this.theImage);
/*      */   }
/*      */ 
/*      */   
/*      */   private void decodeImage() throws IOException, IIOException {
/* 1214 */     int i = this.metadata.IHDR_width;
/* 1215 */     int j = this.metadata.IHDR_height;
/*      */     
/* 1217 */     this.pixelsDone = 0;
/* 1218 */     this.totalPixels = i * j;
/*      */     
/* 1220 */     clearAbortRequest();
/*      */     
/* 1222 */     if (this.metadata.IHDR_interlaceMethod == 0) {
/* 1223 */       decodePass(0, 0, 0, 1, 1, i, j);
/*      */     } else {
/* 1225 */       for (byte b = 0; b <= this.sourceMaxProgressivePass; b++) {
/* 1226 */         int k = adam7XOffset[b];
/* 1227 */         int m = adam7YOffset[b];
/* 1228 */         int n = adam7XSubsampling[b];
/* 1229 */         int i1 = adam7YSubsampling[b];
/* 1230 */         int i2 = adam7XSubsampling[b + 1] - 1;
/* 1231 */         int i3 = adam7YSubsampling[b + 1] - 1;
/*      */         
/* 1233 */         if (b >= this.sourceMinProgressivePass) {
/* 1234 */           decodePass(b, k, m, n, i1, (i + i2) / n, (j + i3) / i1);
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/* 1242 */           skipPass((i + i2) / n, (j + i3) / i1);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1248 */         if (abortRequested()) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readImage(ImageReadParam paramImageReadParam) throws IIOException {
/* 1256 */     readMetadata();
/*      */     
/* 1258 */     int i = this.metadata.IHDR_width;
/* 1259 */     int j = this.metadata.IHDR_height;
/*      */ 
/*      */     
/* 1262 */     this.sourceXSubsampling = 1;
/* 1263 */     this.sourceYSubsampling = 1;
/* 1264 */     this.sourceMinProgressivePass = 0;
/* 1265 */     this.sourceMaxProgressivePass = 6;
/* 1266 */     this.sourceBands = null;
/* 1267 */     this.destinationBands = null;
/* 1268 */     this.destinationOffset = new Point(0, 0);
/*      */ 
/*      */     
/* 1271 */     if (paramImageReadParam != null) {
/* 1272 */       this.sourceXSubsampling = paramImageReadParam.getSourceXSubsampling();
/* 1273 */       this.sourceYSubsampling = paramImageReadParam.getSourceYSubsampling();
/*      */       
/* 1275 */       this
/* 1276 */         .sourceMinProgressivePass = Math.max(paramImageReadParam.getSourceMinProgressivePass(), 0);
/* 1277 */       this
/* 1278 */         .sourceMaxProgressivePass = Math.min(paramImageReadParam.getSourceMaxProgressivePass(), 6);
/*      */       
/* 1280 */       this.sourceBands = paramImageReadParam.getSourceBands();
/* 1281 */       this.destinationBands = paramImageReadParam.getDestinationBands();
/* 1282 */       this.destinationOffset = paramImageReadParam.getDestinationOffset();
/*      */     } 
/* 1284 */     Inflater inflater = null;
/*      */     try {
/* 1286 */       this.stream.seek(this.imageStartPosition);
/*      */       
/* 1288 */       PNGImageDataEnumeration pNGImageDataEnumeration = new PNGImageDataEnumeration(this.stream);
/* 1289 */       SequenceInputStream sequenceInputStream = new SequenceInputStream(pNGImageDataEnumeration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1302 */       inflater = new Inflater();
/* 1303 */       InflaterInputStream inflaterInputStream = new InflaterInputStream(sequenceInputStream, inflater);
/* 1304 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(inflaterInputStream);
/* 1305 */       this.pixelStream = new DataInputStream(bufferedInputStream);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       this.theImage = getDestination(paramImageReadParam, 
/* 1318 */           getImageTypes(0), i, j);
/*      */ 
/*      */ 
/*      */       
/* 1322 */       Rectangle rectangle = new Rectangle(0, 0, 0, 0);
/* 1323 */       this.sourceRegion = new Rectangle(0, 0, 0, 0);
/* 1324 */       computeRegions(paramImageReadParam, i, j, this.theImage, this.sourceRegion, rectangle);
/*      */ 
/*      */       
/* 1327 */       this.destinationOffset.setLocation(rectangle.getLocation());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1332 */       int k = this.metadata.IHDR_colorType;
/* 1333 */       checkReadParamBandSettings(paramImageReadParam, inputBandsForColorType[k], this.theImage
/*      */           
/* 1335 */           .getSampleModel().getNumBands());
/*      */       
/* 1337 */       processImageStarted(0);
/* 1338 */       decodeImage();
/* 1339 */       if (abortRequested()) {
/* 1340 */         processReadAborted();
/*      */       } else {
/* 1342 */         processImageComplete();
/*      */       } 
/* 1344 */     } catch (IOException iOException) {
/* 1345 */       throw new IIOException("Error reading PNG image data", iOException);
/*      */     } finally {
/* 1347 */       if (inflater != null) {
/* 1348 */         inflater.end();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getNumImages(boolean paramBoolean) throws IIOException {
/* 1354 */     if (this.stream == null) {
/* 1355 */       throw new IllegalStateException("No input source set!");
/*      */     }
/* 1357 */     if (this.seekForwardOnly && paramBoolean) {
/* 1358 */       throw new IllegalStateException("seekForwardOnly and allowSearch can't both be true!");
/*      */     }
/*      */     
/* 1361 */     return 1;
/*      */   }
/*      */   
/*      */   public int getWidth(int paramInt) throws IIOException {
/* 1365 */     if (paramInt != 0) {
/* 1366 */       throw new IndexOutOfBoundsException("imageIndex != 0!");
/*      */     }
/*      */     
/* 1369 */     readHeader();
/*      */     
/* 1371 */     return this.metadata.IHDR_width;
/*      */   }
/*      */   
/*      */   public int getHeight(int paramInt) throws IIOException {
/* 1375 */     if (paramInt != 0) {
/* 1376 */       throw new IndexOutOfBoundsException("imageIndex != 0!");
/*      */     }
/*      */     
/* 1379 */     readHeader();
/*      */     
/* 1381 */     return this.metadata.IHDR_height; } public Iterator<ImageTypeSpecifier> getImageTypes(int paramInt) throws IIOException {
/*      */     ColorSpace colorSpace1, colorSpace2;
/*      */     int[] arrayOfInt;
/*      */     boolean bool;
/*      */     int k;
/*      */     byte[] arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4;
/* 1387 */     if (paramInt != 0) {
/* 1388 */       throw new IndexOutOfBoundsException("imageIndex != 0!");
/*      */     }
/*      */     
/* 1391 */     readHeader();
/*      */     
/* 1393 */     ArrayList<ImageTypeSpecifier> arrayList = new ArrayList(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1400 */     int i = this.metadata.IHDR_bitDepth;
/* 1401 */     int j = this.metadata.IHDR_colorType;
/*      */ 
/*      */     
/* 1404 */     if (i <= 8) {
/* 1405 */       bool = false;
/*      */     } else {
/* 1407 */       bool = true;
/*      */     } 
/*      */     
/* 1410 */     switch (j) {
/*      */       
/*      */       case 0:
/* 1413 */         arrayList.add(ImageTypeSpecifier.createGrayscale(i, bool, false));
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1419 */         if (i == 8) {
/*      */ 
/*      */           
/* 1422 */           arrayList.add(ImageTypeSpecifier.createFromBufferedImageType(5));
/*      */ 
/*      */           
/* 1425 */           arrayList.add(ImageTypeSpecifier.createFromBufferedImageType(1));
/*      */ 
/*      */           
/* 1428 */           arrayList.add(ImageTypeSpecifier.createFromBufferedImageType(4));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1433 */         colorSpace1 = ColorSpace.getInstance(1000);
/* 1434 */         arrayOfInt = new int[3];
/* 1435 */         arrayOfInt[0] = 0;
/* 1436 */         arrayOfInt[1] = 1;
/* 1437 */         arrayOfInt[2] = 2;
/* 1438 */         arrayList.add(ImageTypeSpecifier.createInterleaved(colorSpace1, arrayOfInt, bool, false, false));
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1446 */         readMetadata();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1472 */         k = 1 << i;
/*      */         
/* 1474 */         arrayOfByte1 = this.metadata.PLTE_red;
/* 1475 */         arrayOfByte2 = this.metadata.PLTE_green;
/* 1476 */         arrayOfByte3 = this.metadata.PLTE_blue;
/*      */         
/* 1478 */         if (this.metadata.PLTE_red.length < k) {
/* 1479 */           arrayOfByte1 = Arrays.copyOf(this.metadata.PLTE_red, k);
/* 1480 */           Arrays.fill(arrayOfByte1, this.metadata.PLTE_red.length, k, this.metadata.PLTE_red[this.metadata.PLTE_red.length - 1]);
/*      */ 
/*      */           
/* 1483 */           arrayOfByte2 = Arrays.copyOf(this.metadata.PLTE_green, k);
/* 1484 */           Arrays.fill(arrayOfByte2, this.metadata.PLTE_green.length, k, this.metadata.PLTE_green[this.metadata.PLTE_green.length - 1]);
/*      */ 
/*      */           
/* 1487 */           arrayOfByte3 = Arrays.copyOf(this.metadata.PLTE_blue, k);
/* 1488 */           Arrays.fill(arrayOfByte3, this.metadata.PLTE_blue.length, k, this.metadata.PLTE_blue[this.metadata.PLTE_blue.length - 1]);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1496 */         arrayOfByte4 = null;
/* 1497 */         if (this.metadata.tRNS_present && this.metadata.tRNS_alpha != null) {
/* 1498 */           if (this.metadata.tRNS_alpha.length == arrayOfByte1.length) {
/* 1499 */             arrayOfByte4 = this.metadata.tRNS_alpha;
/*      */           } else {
/* 1501 */             arrayOfByte4 = Arrays.copyOf(this.metadata.tRNS_alpha, arrayOfByte1.length);
/* 1502 */             Arrays.fill(arrayOfByte4, this.metadata.tRNS_alpha.length, arrayOfByte1.length, (byte)-1);
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1508 */         arrayList.add(ImageTypeSpecifier.createIndexed(arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4, i, 0));
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1516 */         colorSpace2 = ColorSpace.getInstance(1003);
/* 1517 */         arrayOfInt = new int[2];
/* 1518 */         arrayOfInt[0] = 0;
/* 1519 */         arrayOfInt[1] = 1;
/* 1520 */         arrayList.add(ImageTypeSpecifier.createInterleaved(colorSpace2, arrayOfInt, bool, true, false));
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 1528 */         if (i == 8) {
/*      */ 
/*      */           
/* 1531 */           arrayList.add(ImageTypeSpecifier.createFromBufferedImageType(6));
/*      */ 
/*      */           
/* 1534 */           arrayList.add(ImageTypeSpecifier.createFromBufferedImageType(2));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1539 */         colorSpace1 = ColorSpace.getInstance(1000);
/* 1540 */         arrayOfInt = new int[4];
/* 1541 */         arrayOfInt[0] = 0;
/* 1542 */         arrayOfInt[1] = 1;
/* 1543 */         arrayOfInt[2] = 2;
/* 1544 */         arrayOfInt[3] = 3;
/*      */         
/* 1546 */         arrayList.add(ImageTypeSpecifier.createInterleaved(colorSpace1, arrayOfInt, bool, true, false));
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1557 */     return arrayList.iterator();
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
/*      */   public ImageTypeSpecifier getRawImageType(int paramInt) throws IOException {
/* 1583 */     Iterator<ImageTypeSpecifier> iterator = getImageTypes(paramInt);
/* 1584 */     ImageTypeSpecifier imageTypeSpecifier = null;
/*      */     while (true) {
/* 1586 */       imageTypeSpecifier = iterator.next();
/* 1587 */       if (!iterator.hasNext())
/* 1588 */         return imageTypeSpecifier; 
/*      */     } 
/*      */   }
/*      */   public ImageReadParam getDefaultReadParam() {
/* 1592 */     return new ImageReadParam();
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadata getStreamMetadata() throws IIOException {
/* 1597 */     return null;
/*      */   }
/*      */   
/*      */   public IIOMetadata getImageMetadata(int paramInt) throws IIOException {
/* 1601 */     if (paramInt != 0) {
/* 1602 */       throw new IndexOutOfBoundsException("imageIndex != 0!");
/*      */     }
/* 1604 */     readMetadata();
/* 1605 */     return this.metadata;
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferedImage read(int paramInt, ImageReadParam paramImageReadParam) throws IIOException {
/* 1610 */     if (paramInt != 0) {
/* 1611 */       throw new IndexOutOfBoundsException("imageIndex != 0!");
/*      */     }
/*      */     
/* 1614 */     readImage(paramImageReadParam);
/* 1615 */     return this.theImage;
/*      */   }
/*      */   
/*      */   public void reset() {
/* 1619 */     super.reset();
/* 1620 */     resetStreamSettings();
/*      */   }
/*      */   
/*      */   private void resetStreamSettings() {
/* 1624 */     this.gotHeader = false;
/* 1625 */     this.gotMetadata = false;
/* 1626 */     this.metadata = null;
/* 1627 */     this.pixelStream = null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/PNGImageReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */