/*      */ package com.sun.imageio.plugins.bmp;
/*      */ 
/*      */ import com.sun.imageio.plugins.common.I18N;
/*      */ import com.sun.imageio.plugins.common.ImageUtil;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
/*      */ import java.awt.color.ICC_Profile;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ComponentSampleModel;
/*      */ import java.awt.image.DataBufferByte;
/*      */ import java.awt.image.DataBufferInt;
/*      */ import java.awt.image.DataBufferUShort;
/*      */ import java.awt.image.DirectColorModel;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.MultiPixelPackedSampleModel;
/*      */ import java.awt.image.PixelInterleavedSampleModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.SinglePixelPackedSampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteOrder;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import javax.imageio.IIOException;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.imageio.ImageReadParam;
/*      */ import javax.imageio.ImageReader;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.event.IIOReadProgressListener;
/*      */ import javax.imageio.event.IIOReadUpdateListener;
/*      */ import javax.imageio.event.IIOReadWarningListener;
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
/*      */ public class BMPImageReader
/*      */   extends ImageReader
/*      */   implements BMPConstants
/*      */ {
/*      */   private static final int VERSION_2_1_BIT = 0;
/*      */   private static final int VERSION_2_4_BIT = 1;
/*      */   private static final int VERSION_2_8_BIT = 2;
/*      */   private static final int VERSION_2_24_BIT = 3;
/*      */   private static final int VERSION_3_1_BIT = 4;
/*      */   private static final int VERSION_3_4_BIT = 5;
/*      */   private static final int VERSION_3_8_BIT = 6;
/*      */   private static final int VERSION_3_24_BIT = 7;
/*      */   private static final int VERSION_3_NT_16_BIT = 8;
/*      */   private static final int VERSION_3_NT_32_BIT = 9;
/*      */   private static final int VERSION_4_1_BIT = 10;
/*      */   private static final int VERSION_4_4_BIT = 11;
/*      */   private static final int VERSION_4_8_BIT = 12;
/*      */   private static final int VERSION_4_16_BIT = 13;
/*      */   private static final int VERSION_4_24_BIT = 14;
/*      */   private static final int VERSION_4_32_BIT = 15;
/*      */   private static final int VERSION_3_XP_EMBEDDED = 16;
/*      */   private static final int VERSION_4_XP_EMBEDDED = 17;
/*      */   private static final int VERSION_5_XP_EMBEDDED = 18;
/*      */   private long bitmapFileSize;
/*      */   private long bitmapOffset;
/*      */   private long compression;
/*      */   private long imageSize;
/*      */   private byte[] palette;
/*      */   private int imageType;
/*      */   private int numBands;
/*      */   private boolean isBottomUp;
/*      */   private int bitsPerPixel;
/*      */   private int redMask;
/*      */   private int greenMask;
/*      */   private int blueMask;
/*      */   private int alphaMask;
/*      */   private SampleModel sampleModel;
/*      */   private SampleModel originalSampleModel;
/*      */   private ColorModel colorModel;
/*      */   private ColorModel originalColorModel;
/*  124 */   private ImageInputStream iis = null;
/*      */ 
/*      */   
/*      */   private boolean gotHeader = false;
/*      */ 
/*      */   
/*      */   private int width;
/*      */ 
/*      */   
/*      */   private int height;
/*      */ 
/*      */   
/*      */   private Rectangle destinationRegion;
/*      */ 
/*      */   
/*      */   private Rectangle sourceRegion;
/*      */ 
/*      */   
/*      */   private BMPMetadata metadata;
/*      */ 
/*      */   
/*      */   private BufferedImage bi;
/*      */ 
/*      */   
/*      */   private boolean noTransform = true;
/*      */ 
/*      */   
/*      */   private boolean seleBand = false;
/*      */ 
/*      */   
/*      */   private int scaleX;
/*      */ 
/*      */   
/*      */   private int scaleY;
/*      */   
/*      */   private int[] sourceBands;
/*      */   
/*      */   private int[] destBands;
/*      */ 
/*      */   
/*      */   public BMPImageReader(ImageReaderSpi paramImageReaderSpi) {
/*  165 */     super(paramImageReaderSpi);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInput(Object paramObject, boolean paramBoolean1, boolean paramBoolean2) {
/*  172 */     super.setInput(paramObject, paramBoolean1, paramBoolean2);
/*  173 */     this.iis = (ImageInputStream)paramObject;
/*  174 */     if (this.iis != null)
/*  175 */       this.iis.setByteOrder(ByteOrder.LITTLE_ENDIAN); 
/*  176 */     resetHeaderInfo();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNumImages(boolean paramBoolean) throws IOException {
/*  181 */     if (this.iis == null) {
/*  182 */       throw new IllegalStateException(I18N.getString("GetNumImages0"));
/*      */     }
/*  184 */     if (this.seekForwardOnly && paramBoolean) {
/*  185 */       throw new IllegalStateException(I18N.getString("GetNumImages1"));
/*      */     }
/*  187 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getWidth(int paramInt) throws IOException {
/*  192 */     checkIndex(paramInt);
/*      */     try {
/*  194 */       readHeader();
/*  195 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  196 */       throw new IIOException(I18N.getString("BMPImageReader6"), illegalArgumentException);
/*      */     } 
/*  198 */     return this.width;
/*      */   }
/*      */   
/*      */   public int getHeight(int paramInt) throws IOException {
/*  202 */     checkIndex(paramInt);
/*      */     try {
/*  204 */       readHeader();
/*  205 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  206 */       throw new IIOException(I18N.getString("BMPImageReader6"), illegalArgumentException);
/*      */     } 
/*  208 */     return this.height;
/*      */   }
/*      */   
/*      */   private void checkIndex(int paramInt) {
/*  212 */     if (paramInt != 0) {
/*  213 */       throw new IndexOutOfBoundsException(I18N.getString("BMPImageReader0"));
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
/*      */   protected void readHeader() throws IOException, IllegalArgumentException {
/*  229 */     if (this.gotHeader) {
/*      */       return;
/*      */     }
/*  232 */     if (this.iis == null) {
/*  233 */       throw new IllegalStateException("Input source not set!");
/*      */     }
/*  235 */     int i = 0, j = 0;
/*      */     
/*  237 */     this.metadata = new BMPMetadata();
/*  238 */     this.iis.mark();
/*      */ 
/*      */     
/*  241 */     byte[] arrayOfByte = new byte[2];
/*  242 */     this.iis.read(arrayOfByte);
/*  243 */     if (arrayOfByte[0] != 66 || arrayOfByte[1] != 77) {
/*  244 */       throw new IllegalArgumentException(I18N.getString("BMPImageReader1"));
/*      */     }
/*      */     
/*  247 */     this.bitmapFileSize = this.iis.readUnsignedInt();
/*      */     
/*  249 */     this.iis.skipBytes(4);
/*      */ 
/*      */     
/*  252 */     this.bitmapOffset = this.iis.readUnsignedInt();
/*      */ 
/*      */ 
/*      */     
/*  256 */     long l = this.iis.readUnsignedInt();
/*      */     
/*  258 */     if (l == 12L) {
/*  259 */       this.width = this.iis.readShort();
/*  260 */       this.height = this.iis.readShort();
/*      */     } else {
/*  262 */       this.width = this.iis.readInt();
/*  263 */       this.height = this.iis.readInt();
/*      */     } 
/*      */     
/*  266 */     this.metadata.width = this.width;
/*  267 */     this.metadata.height = this.height;
/*      */     
/*  269 */     int k = this.iis.readUnsignedShort();
/*  270 */     this.bitsPerPixel = this.iis.readUnsignedShort();
/*      */ 
/*      */     
/*  273 */     this.metadata.bitsPerPixel = (short)this.bitsPerPixel;
/*      */ 
/*      */ 
/*      */     
/*  277 */     this.numBands = 3;
/*      */     
/*  279 */     if (l == 12L) {
/*      */       
/*  281 */       this.metadata.bmpVersion = "BMP v. 2.x";
/*      */ 
/*      */       
/*  284 */       if (this.bitsPerPixel == 1) {
/*  285 */         this.imageType = 0;
/*  286 */       } else if (this.bitsPerPixel == 4) {
/*  287 */         this.imageType = 1;
/*  288 */       } else if (this.bitsPerPixel == 8) {
/*  289 */         this.imageType = 2;
/*  290 */       } else if (this.bitsPerPixel == 24) {
/*  291 */         this.imageType = 3;
/*      */       } 
/*      */ 
/*      */       
/*  295 */       int m = (int)((this.bitmapOffset - 14L - l) / 3L);
/*  296 */       int n = m * 3;
/*  297 */       this.palette = new byte[n];
/*  298 */       this.iis.readFully(this.palette, 0, n);
/*  299 */       this.metadata.palette = this.palette;
/*  300 */       this.metadata.paletteSize = m;
/*      */     } else {
/*  302 */       this.compression = this.iis.readUnsignedInt();
/*  303 */       this.imageSize = this.iis.readUnsignedInt();
/*  304 */       long l1 = this.iis.readInt();
/*  305 */       long l2 = this.iis.readInt();
/*  306 */       long l3 = this.iis.readUnsignedInt();
/*  307 */       long l4 = this.iis.readUnsignedInt();
/*      */       
/*  309 */       this.metadata.compression = (int)this.compression;
/*  310 */       this.metadata.xPixelsPerMeter = (int)l1;
/*  311 */       this.metadata.yPixelsPerMeter = (int)l2;
/*  312 */       this.metadata.colorsUsed = (int)l3;
/*  313 */       this.metadata.colorsImportant = (int)l4;
/*      */       
/*  315 */       if (l == 40L) {
/*      */         int m; int n;
/*  317 */         switch ((int)this.compression) {
/*      */           
/*      */           case 4:
/*      */           case 5:
/*  321 */             this.metadata.bmpVersion = "BMP v. 3.x";
/*  322 */             this.imageType = 16;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 0:
/*      */           case 1:
/*      */           case 2:
/*  330 */             if (this.bitmapOffset < l + 14L) {
/*  331 */               throw new IIOException(I18N.getString("BMPImageReader7"));
/*      */             }
/*  333 */             m = (int)((this.bitmapOffset - 14L - l) / 4L);
/*  334 */             n = m * 4;
/*  335 */             this.palette = new byte[n];
/*  336 */             this.iis.readFully(this.palette, 0, n);
/*      */             
/*  338 */             this.metadata.palette = this.palette;
/*  339 */             this.metadata.paletteSize = m;
/*      */             
/*  341 */             if (this.bitsPerPixel == 1) {
/*  342 */               this.imageType = 4;
/*  343 */             } else if (this.bitsPerPixel == 4) {
/*  344 */               this.imageType = 5;
/*  345 */             } else if (this.bitsPerPixel == 8) {
/*  346 */               this.imageType = 6;
/*  347 */             } else if (this.bitsPerPixel == 24) {
/*  348 */               this.imageType = 7;
/*  349 */             } else if (this.bitsPerPixel == 16) {
/*  350 */               this.imageType = 8;
/*      */               
/*  352 */               this.redMask = 31744;
/*  353 */               this.greenMask = 992;
/*  354 */               this.blueMask = 31;
/*  355 */               this.metadata.redMask = this.redMask;
/*  356 */               this.metadata.greenMask = this.greenMask;
/*  357 */               this.metadata.blueMask = this.blueMask;
/*  358 */             } else if (this.bitsPerPixel == 32) {
/*  359 */               this.imageType = 9;
/*  360 */               this.redMask = 16711680;
/*  361 */               this.greenMask = 65280;
/*  362 */               this.blueMask = 255;
/*  363 */               this.metadata.redMask = this.redMask;
/*  364 */               this.metadata.greenMask = this.greenMask;
/*  365 */               this.metadata.blueMask = this.blueMask;
/*      */             } 
/*      */             
/*  368 */             this.metadata.bmpVersion = "BMP v. 3.x";
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  373 */             if (this.bitsPerPixel == 16) {
/*  374 */               this.imageType = 8;
/*  375 */             } else if (this.bitsPerPixel == 32) {
/*  376 */               this.imageType = 9;
/*      */             } 
/*      */ 
/*      */             
/*  380 */             this.redMask = (int)this.iis.readUnsignedInt();
/*  381 */             this.greenMask = (int)this.iis.readUnsignedInt();
/*  382 */             this.blueMask = (int)this.iis.readUnsignedInt();
/*  383 */             this.metadata.redMask = this.redMask;
/*  384 */             this.metadata.greenMask = this.greenMask;
/*  385 */             this.metadata.blueMask = this.blueMask;
/*      */             
/*  387 */             if (l3 != 0L) {
/*      */               
/*  389 */               n = (int)l3 * 4;
/*  390 */               this.palette = new byte[n];
/*  391 */               this.iis.readFully(this.palette, 0, n);
/*      */               
/*  393 */               this.metadata.palette = this.palette;
/*  394 */               this.metadata.paletteSize = (int)l3;
/*      */             } 
/*  396 */             this.metadata.bmpVersion = "BMP v. 3.x NT";
/*      */             break;
/*      */           
/*      */           default:
/*  400 */             throw new IIOException(
/*  401 */                 I18N.getString("BMPImageReader2"));
/*      */         } 
/*  403 */       } else if (l == 108L || l == 124L) {
/*      */         
/*  405 */         if (l == 108L) {
/*  406 */           this.metadata.bmpVersion = "BMP v. 4.x";
/*  407 */         } else if (l == 124L) {
/*  408 */           this.metadata.bmpVersion = "BMP v. 5.x";
/*      */         } 
/*      */         
/*  411 */         this.redMask = (int)this.iis.readUnsignedInt();
/*  412 */         this.greenMask = (int)this.iis.readUnsignedInt();
/*  413 */         this.blueMask = (int)this.iis.readUnsignedInt();
/*      */         
/*  415 */         this.alphaMask = (int)this.iis.readUnsignedInt();
/*  416 */         long l5 = this.iis.readUnsignedInt();
/*  417 */         int m = this.iis.readInt();
/*  418 */         int n = this.iis.readInt();
/*  419 */         int i1 = this.iis.readInt();
/*  420 */         int i2 = this.iis.readInt();
/*  421 */         int i3 = this.iis.readInt();
/*  422 */         int i4 = this.iis.readInt();
/*  423 */         int i5 = this.iis.readInt();
/*  424 */         int i6 = this.iis.readInt();
/*  425 */         int i7 = this.iis.readInt();
/*  426 */         long l6 = this.iis.readUnsignedInt();
/*  427 */         long l7 = this.iis.readUnsignedInt();
/*  428 */         long l8 = this.iis.readUnsignedInt();
/*      */         
/*  430 */         if (l == 124L) {
/*  431 */           this.metadata.intent = this.iis.readInt();
/*  432 */           i = this.iis.readInt();
/*  433 */           j = this.iis.readInt();
/*  434 */           this.iis.skipBytes(4);
/*      */         } 
/*      */         
/*  437 */         this.metadata.colorSpace = (int)l5;
/*      */         
/*  439 */         if (l5 == 0L) {
/*      */           
/*  441 */           this.metadata.redX = m;
/*  442 */           this.metadata.redY = n;
/*  443 */           this.metadata.redZ = i1;
/*  444 */           this.metadata.greenX = i2;
/*  445 */           this.metadata.greenY = i3;
/*  446 */           this.metadata.greenZ = i4;
/*  447 */           this.metadata.blueX = i5;
/*  448 */           this.metadata.blueY = i6;
/*  449 */           this.metadata.blueZ = i7;
/*  450 */           this.metadata.gammaRed = (int)l6;
/*  451 */           this.metadata.gammaGreen = (int)l7;
/*  452 */           this.metadata.gammaBlue = (int)l8;
/*      */         } 
/*      */ 
/*      */         
/*  456 */         int i8 = (int)((this.bitmapOffset - 14L - l) / 4L);
/*  457 */         int i9 = i8 * 4;
/*  458 */         this.palette = new byte[i9];
/*  459 */         this.iis.readFully(this.palette, 0, i9);
/*  460 */         this.metadata.palette = this.palette;
/*  461 */         this.metadata.paletteSize = i8;
/*      */         
/*  463 */         switch ((int)this.compression) {
/*      */           case 4:
/*      */           case 5:
/*  466 */             if (l == 108L) {
/*  467 */               this.imageType = 17; break;
/*  468 */             }  if (l == 124L) {
/*  469 */               this.imageType = 18;
/*      */             }
/*      */             break;
/*      */           default:
/*  473 */             if (this.bitsPerPixel == 1) {
/*  474 */               this.imageType = 10;
/*  475 */             } else if (this.bitsPerPixel == 4) {
/*  476 */               this.imageType = 11;
/*  477 */             } else if (this.bitsPerPixel == 8) {
/*  478 */               this.imageType = 12;
/*  479 */             } else if (this.bitsPerPixel == 16) {
/*  480 */               this.imageType = 13;
/*  481 */               if ((int)this.compression == 0) {
/*  482 */                 this.redMask = 31744;
/*  483 */                 this.greenMask = 992;
/*  484 */                 this.blueMask = 31;
/*      */               } 
/*  486 */             } else if (this.bitsPerPixel == 24) {
/*  487 */               this.imageType = 14;
/*  488 */             } else if (this.bitsPerPixel == 32) {
/*  489 */               this.imageType = 15;
/*  490 */               if ((int)this.compression == 0) {
/*  491 */                 this.redMask = 16711680;
/*  492 */                 this.greenMask = 65280;
/*  493 */                 this.blueMask = 255;
/*      */               } 
/*      */             } 
/*      */             
/*  497 */             this.metadata.redMask = this.redMask;
/*  498 */             this.metadata.greenMask = this.greenMask;
/*  499 */             this.metadata.blueMask = this.blueMask;
/*  500 */             this.metadata.alphaMask = this.alphaMask; break;
/*      */         } 
/*      */       } else {
/*  503 */         throw new IIOException(
/*  504 */             I18N.getString("BMPImageReader3"));
/*      */       } 
/*      */     } 
/*      */     
/*  508 */     if (this.height > 0) {
/*      */       
/*  510 */       this.isBottomUp = true;
/*      */     } else {
/*      */       
/*  513 */       this.isBottomUp = false;
/*  514 */       this.height = Math.abs(this.height);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  519 */     ColorSpace colorSpace = ColorSpace.getInstance(1000);
/*  520 */     if (this.metadata.colorSpace == 3 || this.metadata.colorSpace == 4) {
/*      */ 
/*      */       
/*  523 */       this.iis.mark();
/*  524 */       this.iis.skipBytes(i - l);
/*  525 */       byte[] arrayOfByte1 = new byte[j];
/*  526 */       this.iis.readFully(arrayOfByte1, 0, j);
/*  527 */       this.iis.reset();
/*      */       
/*      */       try {
/*  530 */         if (this.metadata.colorSpace == 3 && 
/*  531 */           isLinkedProfileAllowed() && 
/*  532 */           !isUncOrDevicePath(arrayOfByte1)) {
/*      */           
/*  534 */           String str = new String(arrayOfByte1, "windows-1252");
/*      */ 
/*      */           
/*  537 */           colorSpace = new ICC_ColorSpace(ICC_Profile.getInstance(str));
/*      */         } else {
/*      */           
/*  540 */           colorSpace = new ICC_ColorSpace(ICC_Profile.getInstance(arrayOfByte1));
/*      */         } 
/*  542 */       } catch (Exception exception) {
/*  543 */         colorSpace = ColorSpace.getInstance(1000);
/*      */       } 
/*      */     } 
/*      */     
/*  547 */     if (this.bitsPerPixel == 0 || this.compression == 4L || this.compression == 5L) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  552 */       this.colorModel = null;
/*  553 */       this.sampleModel = null;
/*  554 */     } else if (this.bitsPerPixel == 1 || this.bitsPerPixel == 4 || this.bitsPerPixel == 8) {
/*      */       byte[] arrayOfByte1, arrayOfByte2, arrayOfByte3;
/*  556 */       this.numBands = 1;
/*      */       
/*  558 */       if (this.bitsPerPixel == 8) {
/*  559 */         int[] arrayOfInt = new int[this.numBands];
/*  560 */         for (byte b = 0; b < this.numBands; b++) {
/*  561 */           arrayOfInt[b] = this.numBands - 1 - b;
/*      */         }
/*  563 */         this.sampleModel = new PixelInterleavedSampleModel(0, this.width, this.height, this.numBands, this.numBands * this.width, arrayOfInt);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/*  571 */         this.sampleModel = new MultiPixelPackedSampleModel(0, this.width, this.height, this.bitsPerPixel);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  579 */       if (this.imageType == 0 || this.imageType == 1 || this.imageType == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  584 */         l = (this.palette.length / 3);
/*      */         
/*  586 */         if (l > 256L) {
/*  587 */           l = 256L;
/*      */         }
/*      */ 
/*      */         
/*  591 */         arrayOfByte1 = new byte[(int)l];
/*  592 */         arrayOfByte2 = new byte[(int)l];
/*  593 */         arrayOfByte3 = new byte[(int)l];
/*  594 */         for (byte b = 0; b < (int)l; b++) {
/*  595 */           int m = 3 * b;
/*  596 */           arrayOfByte3[b] = this.palette[m];
/*  597 */           arrayOfByte2[b] = this.palette[m + 1];
/*  598 */           arrayOfByte1[b] = this.palette[m + 2];
/*      */         } 
/*      */       } else {
/*  601 */         l = (this.palette.length / 4);
/*      */         
/*  603 */         if (l > 256L) {
/*  604 */           l = 256L;
/*      */         }
/*      */ 
/*      */         
/*  608 */         arrayOfByte1 = new byte[(int)l];
/*  609 */         arrayOfByte2 = new byte[(int)l];
/*  610 */         arrayOfByte3 = new byte[(int)l];
/*  611 */         for (byte b = 0; b < l; b++) {
/*  612 */           int m = 4 * b;
/*  613 */           arrayOfByte3[b] = this.palette[m];
/*  614 */           arrayOfByte2[b] = this.palette[m + 1];
/*  615 */           arrayOfByte1[b] = this.palette[m + 2];
/*      */         } 
/*      */       } 
/*      */       
/*  619 */       if (ImageUtil.isIndicesForGrayscale(arrayOfByte1, arrayOfByte2, arrayOfByte3))
/*  620 */       { this
/*  621 */           .colorModel = ImageUtil.createColorModel(null, this.sampleModel); }
/*      */       else
/*  623 */       { this.colorModel = new IndexColorModel(this.bitsPerPixel, (int)l, arrayOfByte1, arrayOfByte2, arrayOfByte3); } 
/*  624 */     } else if (this.bitsPerPixel == 16) {
/*  625 */       this.numBands = 3;
/*  626 */       this.sampleModel = new SinglePixelPackedSampleModel(1, this.width, this.height, new int[] { this.redMask, this.greenMask, this.blueMask });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  631 */       this.colorModel = new DirectColorModel(colorSpace, 16, this.redMask, this.greenMask, this.blueMask, 0, false, 1);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  636 */     else if (this.bitsPerPixel == 32) {
/*  637 */       this.numBands = (this.alphaMask == 0) ? 3 : 4;
/*      */ 
/*      */ 
/*      */       
/*  641 */       (new int[3])[0] = this.redMask; (new int[3])[1] = this.greenMask; (new int[3])[2] = this.blueMask; (new int[4])[0] = this.redMask; (new int[4])[1] = this.greenMask; (new int[4])[2] = this.blueMask; (new int[4])[3] = this.alphaMask; int[] arrayOfInt = (this.numBands == 3) ? new int[3] : new int[4];
/*      */ 
/*      */ 
/*      */       
/*  645 */       this.sampleModel = new SinglePixelPackedSampleModel(3, this.width, this.height, arrayOfInt);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  650 */       this.colorModel = new DirectColorModel(colorSpace, 32, this.redMask, this.greenMask, this.blueMask, this.alphaMask, false, 3);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  655 */       this.numBands = 3;
/*      */       
/*  657 */       int[] arrayOfInt = new int[this.numBands];
/*  658 */       for (byte b = 0; b < this.numBands; b++) {
/*  659 */         arrayOfInt[b] = this.numBands - 1 - b;
/*      */       }
/*      */       
/*  662 */       this.sampleModel = new PixelInterleavedSampleModel(0, this.width, this.height, this.numBands, this.numBands * this.width, arrayOfInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  669 */       this
/*  670 */         .colorModel = ImageUtil.createColorModel(colorSpace, this.sampleModel);
/*      */     } 
/*      */     
/*  673 */     this.originalSampleModel = this.sampleModel;
/*  674 */     this.originalColorModel = this.colorModel;
/*      */ 
/*      */ 
/*      */     
/*  678 */     this.iis.reset();
/*  679 */     this.iis.skipBytes(this.bitmapOffset);
/*  680 */     this.gotHeader = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator getImageTypes(int paramInt) throws IOException {
/*  685 */     checkIndex(paramInt);
/*      */     try {
/*  687 */       readHeader();
/*  688 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  689 */       throw new IIOException(I18N.getString("BMPImageReader6"), illegalArgumentException);
/*      */     } 
/*  691 */     ArrayList<ImageTypeSpecifier> arrayList = new ArrayList(1);
/*  692 */     arrayList.add(new ImageTypeSpecifier(this.originalColorModel, this.originalSampleModel));
/*      */     
/*  694 */     return arrayList.iterator();
/*      */   }
/*      */   
/*      */   public ImageReadParam getDefaultReadParam() {
/*  698 */     return new ImageReadParam();
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadata getImageMetadata(int paramInt) throws IOException {
/*  703 */     checkIndex(paramInt);
/*  704 */     if (this.metadata == null) {
/*      */       try {
/*  706 */         readHeader();
/*  707 */       } catch (IllegalArgumentException illegalArgumentException) {
/*  708 */         throw new IIOException(I18N.getString("BMPImageReader6"), illegalArgumentException);
/*      */       } 
/*      */     }
/*  711 */     return this.metadata;
/*      */   }
/*      */   
/*      */   public IIOMetadata getStreamMetadata() throws IOException {
/*  715 */     return null;
/*      */   }
/*      */   
/*      */   public boolean isRandomAccessEasy(int paramInt) throws IOException {
/*  719 */     checkIndex(paramInt);
/*      */     try {
/*  721 */       readHeader();
/*  722 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  723 */       throw new IIOException(I18N.getString("BMPImageReader6"), illegalArgumentException);
/*      */     } 
/*  725 */     return (this.metadata.compression == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage read(int paramInt, ImageReadParam paramImageReadParam) throws IOException {
/*  731 */     if (this.iis == null) {
/*  732 */       throw new IllegalStateException(I18N.getString("BMPImageReader5"));
/*      */     }
/*      */     
/*  735 */     checkIndex(paramInt);
/*  736 */     clearAbortRequest();
/*  737 */     processImageStarted(paramInt);
/*      */     
/*  739 */     if (paramImageReadParam == null) {
/*  740 */       paramImageReadParam = getDefaultReadParam();
/*      */     }
/*      */     
/*      */     try {
/*  744 */       readHeader();
/*  745 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  746 */       throw new IIOException(I18N.getString("BMPImageReader6"), illegalArgumentException);
/*      */     } 
/*      */     
/*  749 */     this.sourceRegion = new Rectangle(0, 0, 0, 0);
/*  750 */     this.destinationRegion = new Rectangle(0, 0, 0, 0);
/*      */     
/*  752 */     computeRegions(paramImageReadParam, this.width, this.height, paramImageReadParam
/*  753 */         .getDestination(), this.sourceRegion, this.destinationRegion);
/*      */ 
/*      */ 
/*      */     
/*  757 */     this.scaleX = paramImageReadParam.getSourceXSubsampling();
/*  758 */     this.scaleY = paramImageReadParam.getSourceYSubsampling();
/*      */ 
/*      */     
/*  761 */     this.sourceBands = paramImageReadParam.getSourceBands();
/*  762 */     this.destBands = paramImageReadParam.getDestinationBands();
/*      */     
/*  764 */     this.seleBand = (this.sourceBands != null && this.destBands != null);
/*  765 */     this
/*  766 */       .noTransform = (this.destinationRegion.equals(new Rectangle(0, 0, this.width, this.height)) || this.seleBand);
/*      */ 
/*      */     
/*  769 */     if (!this.seleBand) {
/*  770 */       this.sourceBands = new int[this.numBands];
/*  771 */       this.destBands = new int[this.numBands];
/*  772 */       for (byte b = 0; b < this.numBands; b++) {
/*  773 */         this.sourceBands[b] = b; this.destBands[b] = b;
/*      */       } 
/*      */     } 
/*      */     
/*  777 */     this.bi = paramImageReadParam.getDestination();
/*      */ 
/*      */     
/*  780 */     WritableRaster writableRaster = null;
/*      */     
/*  782 */     if (this.bi == null) {
/*  783 */       if (this.sampleModel != null && this.colorModel != null) {
/*  784 */         this
/*  785 */           .sampleModel = this.sampleModel.createCompatibleSampleModel(this.destinationRegion.x + this.destinationRegion.width, this.destinationRegion.y + this.destinationRegion.height);
/*      */ 
/*      */ 
/*      */         
/*  789 */         if (this.seleBand)
/*  790 */           this.sampleModel = this.sampleModel.createSubsetSampleModel(this.sourceBands); 
/*  791 */         writableRaster = Raster.createWritableRaster(this.sampleModel, new Point());
/*  792 */         this.bi = new BufferedImage(this.colorModel, writableRaster, false, null);
/*      */       } 
/*      */     } else {
/*  795 */       writableRaster = this.bi.getWritableTile(0, 0);
/*  796 */       this.sampleModel = this.bi.getSampleModel();
/*  797 */       this.colorModel = this.bi.getColorModel();
/*      */       
/*  799 */       this.noTransform &= this.destinationRegion.equals(writableRaster.getBounds());
/*      */     } 
/*      */     
/*  802 */     byte[] arrayOfByte = null;
/*  803 */     short[] arrayOfShort = null;
/*  804 */     int[] arrayOfInt = null;
/*      */ 
/*      */     
/*  807 */     if (this.sampleModel != null) {
/*  808 */       if (this.sampleModel.getDataType() == 0) {
/*      */         
/*  810 */         arrayOfByte = ((DataBufferByte)writableRaster.getDataBuffer()).getData();
/*  811 */       } else if (this.sampleModel.getDataType() == 1) {
/*      */         
/*  813 */         arrayOfShort = ((DataBufferUShort)writableRaster.getDataBuffer()).getData();
/*  814 */       } else if (this.sampleModel.getDataType() == 3) {
/*      */         
/*  816 */         arrayOfInt = ((DataBufferInt)writableRaster.getDataBuffer()).getData();
/*      */       } 
/*      */     }
/*      */     
/*  820 */     switch (this.imageType) {
/*      */ 
/*      */       
/*      */       case 0:
/*  824 */         read1Bit(arrayOfByte);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  829 */         read4Bit(arrayOfByte);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  834 */         read8Bit(arrayOfByte);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  839 */         read24Bit(arrayOfByte);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  844 */         read1Bit(arrayOfByte);
/*      */         break;
/*      */       
/*      */       case 5:
/*  848 */         switch ((int)this.compression) {
/*      */           case 0:
/*  850 */             read4Bit(arrayOfByte);
/*      */             break;
/*      */           
/*      */           case 2:
/*  854 */             readRLE4(arrayOfByte);
/*      */             break;
/*      */         } 
/*      */         
/*  858 */         throw new IIOException(
/*  859 */             I18N.getString("BMPImageReader1"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*  864 */         switch ((int)this.compression) {
/*      */           case 0:
/*  866 */             read8Bit(arrayOfByte);
/*      */             break;
/*      */           
/*      */           case 1:
/*  870 */             readRLE8(arrayOfByte);
/*      */             break;
/*      */         } 
/*      */         
/*  874 */         throw new IIOException(
/*  875 */             I18N.getString("BMPImageReader1"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/*  882 */         read24Bit(arrayOfByte);
/*      */         break;
/*      */       
/*      */       case 8:
/*  886 */         read16Bit(arrayOfShort);
/*      */         break;
/*      */       
/*      */       case 9:
/*  890 */         read32Bit(arrayOfInt);
/*      */         break;
/*      */       
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*  896 */         this.bi = readEmbedded((int)this.compression, this.bi, paramImageReadParam);
/*      */         break;
/*      */       
/*      */       case 10:
/*  900 */         read1Bit(arrayOfByte);
/*      */         break;
/*      */       
/*      */       case 11:
/*  904 */         switch ((int)this.compression) {
/*      */           
/*      */           case 0:
/*  907 */             read4Bit(arrayOfByte);
/*      */             break;
/*      */           
/*      */           case 2:
/*  911 */             readRLE4(arrayOfByte);
/*      */             break;
/*      */         } 
/*      */         
/*  915 */         throw new IIOException(
/*  916 */             I18N.getString("BMPImageReader1"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/*  921 */         switch ((int)this.compression) {
/*      */           
/*      */           case 0:
/*  924 */             read8Bit(arrayOfByte);
/*      */             break;
/*      */           
/*      */           case 1:
/*  928 */             readRLE8(arrayOfByte);
/*      */             break;
/*      */         } 
/*      */         
/*  932 */         throw new IIOException(
/*  933 */             I18N.getString("BMPImageReader1"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 13:
/*  938 */         read16Bit(arrayOfShort);
/*      */         break;
/*      */       
/*      */       case 14:
/*  942 */         read24Bit(arrayOfByte);
/*      */         break;
/*      */       
/*      */       case 15:
/*  946 */         read32Bit(arrayOfInt);
/*      */         break;
/*      */     } 
/*      */     
/*  950 */     if (abortRequested()) {
/*  951 */       processReadAborted();
/*      */     } else {
/*  953 */       processImageComplete();
/*      */     } 
/*  955 */     return this.bi;
/*      */   }
/*      */   
/*      */   public boolean canReadRaster() {
/*  959 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Raster readRaster(int paramInt, ImageReadParam paramImageReadParam) throws IOException {
/*  964 */     BufferedImage bufferedImage = read(paramInt, paramImageReadParam);
/*  965 */     return bufferedImage.getData();
/*      */   }
/*      */   
/*      */   private void resetHeaderInfo() {
/*  969 */     this.gotHeader = false;
/*  970 */     this.bi = null;
/*  971 */     this.sampleModel = this.originalSampleModel = null;
/*  972 */     this.colorModel = this.originalColorModel = null;
/*      */   }
/*      */   
/*      */   public void reset() {
/*  976 */     super.reset();
/*  977 */     this.iis = null;
/*  978 */     resetHeaderInfo();
/*      */   }
/*      */ 
/*      */   
/*      */   private void read1Bit(byte[] paramArrayOfbyte) throws IOException {
/*  983 */     int i = (this.width + 7) / 8;
/*  984 */     int j = i % 4;
/*  985 */     if (j != 0) {
/*  986 */       j = 4 - j;
/*      */     }
/*      */     
/*  989 */     int k = i + j;
/*      */     
/*  991 */     if (this.noTransform) {
/*  992 */       int m = this.isBottomUp ? ((this.height - 1) * i) : 0;
/*      */       
/*  994 */       for (byte b = 0; b < this.height && 
/*  995 */         !abortRequested(); b++) {
/*      */ 
/*      */         
/*  998 */         this.iis.readFully(paramArrayOfbyte, m, i);
/*  999 */         this.iis.skipBytes(j);
/* 1000 */         m += this.isBottomUp ? -i : i;
/* 1001 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1004 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } else {
/* 1007 */       byte[] arrayOfByte = new byte[k];
/*      */       
/* 1009 */       int m = ((MultiPixelPackedSampleModel)this.sampleModel).getScanlineStride();
/*      */       
/* 1011 */       if (this.isBottomUp) {
/* 1012 */         int i4 = this.sourceRegion.y + (this.destinationRegion.height - 1) * this.scaleY;
/*      */         
/* 1014 */         this.iis.skipBytes(k * (this.height - 1 - i4));
/*      */       } else {
/* 1016 */         this.iis.skipBytes(k * this.sourceRegion.y);
/*      */       } 
/* 1018 */       int n = k * (this.scaleY - 1);
/*      */ 
/*      */       
/* 1021 */       int[] arrayOfInt1 = new int[this.destinationRegion.width];
/* 1022 */       int[] arrayOfInt2 = new int[this.destinationRegion.width];
/* 1023 */       int[] arrayOfInt3 = new int[this.destinationRegion.width];
/* 1024 */       int[] arrayOfInt4 = new int[this.destinationRegion.width];
/*      */       
/* 1026 */       int i1 = this.destinationRegion.x, i2 = this.sourceRegion.x, i3 = 0;
/* 1027 */       for (; i1 < this.destinationRegion.x + this.destinationRegion.width; 
/* 1028 */         i1++, i3++, i2 += this.scaleX) {
/* 1029 */         arrayOfInt3[i3] = i2 >> 3;
/* 1030 */         arrayOfInt1[i3] = 7 - (i2 & 0x7);
/* 1031 */         arrayOfInt4[i3] = i1 >> 3;
/* 1032 */         arrayOfInt2[i3] = 7 - (i1 & 0x7);
/*      */       } 
/*      */       
/* 1035 */       i1 = this.destinationRegion.y * m;
/* 1036 */       if (this.isBottomUp) {
/* 1037 */         i1 += (this.destinationRegion.height - 1) * m;
/*      */       }
/* 1039 */       i2 = 0; i3 = this.sourceRegion.y;
/* 1040 */       for (; i2 < this.destinationRegion.height; i2++, i3 += this.scaleY) {
/*      */         
/* 1042 */         if (abortRequested())
/*      */           break; 
/* 1044 */         this.iis.read(arrayOfByte, 0, k);
/* 1045 */         for (byte b = 0; b < this.destinationRegion.width; b++) {
/*      */           
/* 1047 */           int i4 = arrayOfByte[arrayOfInt3[b]] >> arrayOfInt1[b] & 0x1;
/* 1048 */           paramArrayOfbyte[i1 + arrayOfInt4[b]] = (byte)(paramArrayOfbyte[i1 + arrayOfInt4[b]] | i4 << arrayOfInt2[b]);
/*      */         } 
/*      */         
/* 1051 */         i1 += this.isBottomUp ? -m : m;
/* 1052 */         this.iis.skipBytes(n);
/* 1053 */         processImageUpdate(this.bi, 0, i2, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1056 */         processImageProgress(100.0F * i2 / this.destinationRegion.height);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void read4Bit(byte[] paramArrayOfbyte) throws IOException {
/* 1064 */     int i = (this.width + 1) / 2;
/*      */ 
/*      */     
/* 1067 */     int j = i % 4;
/* 1068 */     if (j != 0) {
/* 1069 */       j = 4 - j;
/*      */     }
/* 1071 */     int k = i + j;
/*      */     
/* 1073 */     if (this.noTransform) {
/* 1074 */       int m = this.isBottomUp ? ((this.height - 1) * i) : 0;
/*      */       
/* 1076 */       for (byte b = 0; b < this.height && 
/* 1077 */         !abortRequested(); b++) {
/*      */ 
/*      */         
/* 1080 */         this.iis.readFully(paramArrayOfbyte, m, i);
/* 1081 */         this.iis.skipBytes(j);
/* 1082 */         m += this.isBottomUp ? -i : i;
/* 1083 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1086 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } else {
/* 1089 */       byte[] arrayOfByte = new byte[k];
/*      */       
/* 1091 */       int m = ((MultiPixelPackedSampleModel)this.sampleModel).getScanlineStride();
/*      */       
/* 1093 */       if (this.isBottomUp) {
/* 1094 */         int i4 = this.sourceRegion.y + (this.destinationRegion.height - 1) * this.scaleY;
/*      */         
/* 1096 */         this.iis.skipBytes(k * (this.height - 1 - i4));
/*      */       } else {
/* 1098 */         this.iis.skipBytes(k * this.sourceRegion.y);
/*      */       } 
/* 1100 */       int n = k * (this.scaleY - 1);
/*      */ 
/*      */       
/* 1103 */       int[] arrayOfInt1 = new int[this.destinationRegion.width];
/* 1104 */       int[] arrayOfInt2 = new int[this.destinationRegion.width];
/* 1105 */       int[] arrayOfInt3 = new int[this.destinationRegion.width];
/* 1106 */       int[] arrayOfInt4 = new int[this.destinationRegion.width];
/*      */       
/* 1108 */       int i1 = this.destinationRegion.x, i2 = this.sourceRegion.x, i3 = 0;
/* 1109 */       for (; i1 < this.destinationRegion.x + this.destinationRegion.width; 
/* 1110 */         i1++, i3++, i2 += this.scaleX) {
/* 1111 */         arrayOfInt3[i3] = i2 >> 1;
/* 1112 */         arrayOfInt1[i3] = 1 - (i2 & 0x1) << 2;
/* 1113 */         arrayOfInt4[i3] = i1 >> 1;
/* 1114 */         arrayOfInt2[i3] = 1 - (i1 & 0x1) << 2;
/*      */       } 
/*      */       
/* 1117 */       i1 = this.destinationRegion.y * m;
/* 1118 */       if (this.isBottomUp) {
/* 1119 */         i1 += (this.destinationRegion.height - 1) * m;
/*      */       }
/* 1121 */       i2 = 0; i3 = this.sourceRegion.y;
/* 1122 */       for (; i2 < this.destinationRegion.height; i2++, i3 += this.scaleY) {
/*      */         
/* 1124 */         if (abortRequested())
/*      */           break; 
/* 1126 */         this.iis.read(arrayOfByte, 0, k);
/* 1127 */         for (byte b = 0; b < this.destinationRegion.width; b++) {
/*      */           
/* 1129 */           int i4 = arrayOfByte[arrayOfInt3[b]] >> arrayOfInt1[b] & 0xF;
/* 1130 */           paramArrayOfbyte[i1 + arrayOfInt4[b]] = (byte)(paramArrayOfbyte[i1 + arrayOfInt4[b]] | i4 << arrayOfInt2[b]);
/*      */         } 
/*      */         
/* 1133 */         i1 += this.isBottomUp ? -m : m;
/* 1134 */         this.iis.skipBytes(n);
/* 1135 */         processImageUpdate(this.bi, 0, i2, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1138 */         processImageProgress(100.0F * i2 / this.destinationRegion.height);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void read8Bit(byte[] paramArrayOfbyte) throws IOException {
/* 1147 */     int i = this.width % 4;
/* 1148 */     if (i != 0) {
/* 1149 */       i = 4 - i;
/*      */     }
/*      */     
/* 1152 */     int j = this.width + i;
/*      */     
/* 1154 */     if (this.noTransform) {
/* 1155 */       int k = this.isBottomUp ? ((this.height - 1) * this.width) : 0;
/*      */       
/* 1157 */       for (byte b = 0; b < this.height && 
/* 1158 */         !abortRequested(); b++) {
/*      */ 
/*      */         
/* 1161 */         this.iis.readFully(paramArrayOfbyte, k, this.width);
/* 1162 */         this.iis.skipBytes(i);
/* 1163 */         k += this.isBottomUp ? -this.width : this.width;
/* 1164 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1167 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } else {
/* 1170 */       byte[] arrayOfByte = new byte[j];
/*      */       
/* 1172 */       int k = ((ComponentSampleModel)this.sampleModel).getScanlineStride();
/*      */       
/* 1174 */       if (this.isBottomUp) {
/* 1175 */         int i2 = this.sourceRegion.y + (this.destinationRegion.height - 1) * this.scaleY;
/*      */         
/* 1177 */         this.iis.skipBytes(j * (this.height - 1 - i2));
/*      */       } else {
/* 1179 */         this.iis.skipBytes(j * this.sourceRegion.y);
/*      */       } 
/* 1181 */       int m = j * (this.scaleY - 1);
/*      */       
/* 1183 */       int n = this.destinationRegion.y * k;
/* 1184 */       if (this.isBottomUp)
/* 1185 */         n += (this.destinationRegion.height - 1) * k; 
/* 1186 */       n += this.destinationRegion.x;
/*      */       
/* 1188 */       byte b = 0; int i1 = this.sourceRegion.y;
/* 1189 */       for (; b < this.destinationRegion.height; b++, i1 += this.scaleY) {
/*      */         
/* 1191 */         if (abortRequested())
/*      */           break; 
/* 1193 */         this.iis.read(arrayOfByte, 0, j);
/* 1194 */         byte b1 = 0; int i2 = this.sourceRegion.x;
/* 1195 */         for (; b1 < this.destinationRegion.width; b1++, i2 += this.scaleX)
/*      */         {
/* 1197 */           paramArrayOfbyte[n + b1] = arrayOfByte[i2];
/*      */         }
/*      */         
/* 1200 */         n += this.isBottomUp ? -k : k;
/* 1201 */         this.iis.skipBytes(m);
/* 1202 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1205 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void read24Bit(byte[] paramArrayOfbyte) throws IOException {
/* 1214 */     int i = this.width * 3 % 4;
/* 1215 */     if (i != 0) {
/* 1216 */       i = 4 - i;
/*      */     }
/* 1218 */     int j = this.width * 3;
/* 1219 */     int k = j + i;
/*      */     
/* 1221 */     if (this.noTransform) {
/* 1222 */       int m = this.isBottomUp ? ((this.height - 1) * this.width * 3) : 0;
/*      */       
/* 1224 */       for (byte b = 0; b < this.height && 
/* 1225 */         !abortRequested(); b++) {
/*      */ 
/*      */         
/* 1228 */         this.iis.readFully(paramArrayOfbyte, m, j);
/* 1229 */         this.iis.skipBytes(i);
/* 1230 */         m += this.isBottomUp ? -j : j;
/* 1231 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1234 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } else {
/* 1237 */       byte[] arrayOfByte = new byte[k];
/*      */       
/* 1239 */       j = ((ComponentSampleModel)this.sampleModel).getScanlineStride();
/*      */       
/* 1241 */       if (this.isBottomUp) {
/* 1242 */         int i2 = this.sourceRegion.y + (this.destinationRegion.height - 1) * this.scaleY;
/*      */         
/* 1244 */         this.iis.skipBytes(k * (this.height - 1 - i2));
/*      */       } else {
/* 1246 */         this.iis.skipBytes(k * this.sourceRegion.y);
/*      */       } 
/* 1248 */       int m = k * (this.scaleY - 1);
/*      */       
/* 1250 */       int n = this.destinationRegion.y * j;
/* 1251 */       if (this.isBottomUp)
/* 1252 */         n += (this.destinationRegion.height - 1) * j; 
/* 1253 */       n += this.destinationRegion.x * 3;
/*      */       
/* 1255 */       byte b = 0; int i1 = this.sourceRegion.y;
/* 1256 */       for (; b < this.destinationRegion.height; b++, i1 += this.scaleY) {
/*      */         
/* 1258 */         if (abortRequested())
/*      */           break; 
/* 1260 */         this.iis.read(arrayOfByte, 0, k);
/* 1261 */         byte b1 = 0; int i2 = 3 * this.sourceRegion.x;
/* 1262 */         for (; b1 < this.destinationRegion.width; b1++, i2 += 3 * this.scaleX) {
/*      */           
/* 1264 */           int i3 = 3 * b1 + n;
/* 1265 */           for (byte b2 = 0; b2 < this.destBands.length; b2++) {
/* 1266 */             paramArrayOfbyte[i3 + this.destBands[b2]] = arrayOfByte[i2 + this.sourceBands[b2]];
/*      */           }
/*      */         } 
/* 1269 */         n += this.isBottomUp ? -j : j;
/* 1270 */         this.iis.skipBytes(m);
/* 1271 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1274 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void read16Bit(short[] paramArrayOfshort) throws IOException {
/* 1282 */     int i = this.width * 2 % 4;
/*      */     
/* 1284 */     if (i != 0) {
/* 1285 */       i = 4 - i;
/*      */     }
/* 1287 */     int j = this.width + i / 2;
/*      */     
/* 1289 */     if (this.noTransform) {
/* 1290 */       int k = this.isBottomUp ? ((this.height - 1) * this.width) : 0;
/* 1291 */       for (byte b = 0; b < this.height && 
/* 1292 */         !abortRequested(); b++) {
/*      */ 
/*      */ 
/*      */         
/* 1296 */         this.iis.readFully(paramArrayOfshort, k, this.width);
/* 1297 */         this.iis.skipBytes(i);
/*      */         
/* 1299 */         k += this.isBottomUp ? -this.width : this.width;
/* 1300 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1303 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } else {
/* 1306 */       short[] arrayOfShort = new short[j];
/*      */       
/* 1308 */       int k = ((SinglePixelPackedSampleModel)this.sampleModel).getScanlineStride();
/*      */       
/* 1310 */       if (this.isBottomUp) {
/* 1311 */         int i2 = this.sourceRegion.y + (this.destinationRegion.height - 1) * this.scaleY;
/*      */         
/* 1313 */         this.iis.skipBytes(j * (this.height - 1 - i2) << 1);
/*      */       } else {
/* 1315 */         this.iis.skipBytes(j * this.sourceRegion.y << 1);
/*      */       } 
/* 1317 */       int m = j * (this.scaleY - 1) << 1;
/*      */       
/* 1319 */       int n = this.destinationRegion.y * k;
/* 1320 */       if (this.isBottomUp)
/* 1321 */         n += (this.destinationRegion.height - 1) * k; 
/* 1322 */       n += this.destinationRegion.x;
/*      */       
/* 1324 */       byte b = 0; int i1 = this.sourceRegion.y;
/* 1325 */       for (; b < this.destinationRegion.height; b++, i1 += this.scaleY) {
/*      */         
/* 1327 */         if (abortRequested())
/*      */           break; 
/* 1329 */         this.iis.readFully(arrayOfShort, 0, j);
/* 1330 */         byte b1 = 0; int i2 = this.sourceRegion.x;
/* 1331 */         for (; b1 < this.destinationRegion.width; b1++, i2 += this.scaleX)
/*      */         {
/* 1333 */           paramArrayOfshort[n + b1] = arrayOfShort[i2];
/*      */         }
/*      */         
/* 1336 */         n += this.isBottomUp ? -k : k;
/* 1337 */         this.iis.skipBytes(m);
/* 1338 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1341 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void read32Bit(int[] paramArrayOfint) throws IOException {
/* 1347 */     if (this.noTransform) {
/* 1348 */       int i = this.isBottomUp ? ((this.height - 1) * this.width) : 0;
/*      */       
/* 1350 */       for (byte b = 0; b < this.height && 
/* 1351 */         !abortRequested(); b++) {
/*      */ 
/*      */         
/* 1354 */         this.iis.readFully(paramArrayOfint, i, this.width);
/* 1355 */         i += this.isBottomUp ? -this.width : this.width;
/* 1356 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1359 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } else {
/* 1362 */       int[] arrayOfInt = new int[this.width];
/*      */       
/* 1364 */       int i = ((SinglePixelPackedSampleModel)this.sampleModel).getScanlineStride();
/*      */       
/* 1366 */       if (this.isBottomUp) {
/* 1367 */         int n = this.sourceRegion.y + (this.destinationRegion.height - 1) * this.scaleY;
/*      */         
/* 1369 */         this.iis.skipBytes(this.width * (this.height - 1 - n) << 2);
/*      */       } else {
/* 1371 */         this.iis.skipBytes(this.width * this.sourceRegion.y << 2);
/*      */       } 
/* 1373 */       int j = this.width * (this.scaleY - 1) << 2;
/*      */       
/* 1375 */       int k = this.destinationRegion.y * i;
/* 1376 */       if (this.isBottomUp)
/* 1377 */         k += (this.destinationRegion.height - 1) * i; 
/* 1378 */       k += this.destinationRegion.x;
/*      */       
/* 1380 */       byte b = 0; int m = this.sourceRegion.y;
/* 1381 */       for (; b < this.destinationRegion.height; b++, m += this.scaleY) {
/*      */         
/* 1383 */         if (abortRequested())
/*      */           break; 
/* 1385 */         this.iis.readFully(arrayOfInt, 0, this.width);
/* 1386 */         byte b1 = 0; int n = this.sourceRegion.x;
/* 1387 */         for (; b1 < this.destinationRegion.width; b1++, n += this.scaleX)
/*      */         {
/* 1389 */           paramArrayOfint[k + b1] = arrayOfInt[n];
/*      */         }
/*      */         
/* 1392 */         k += this.isBottomUp ? -i : i;
/* 1393 */         this.iis.skipBytes(j);
/* 1394 */         processImageUpdate(this.bi, 0, b, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */         
/* 1397 */         processImageProgress(100.0F * b / this.destinationRegion.height);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readRLE8(byte[] paramArrayOfbyte) throws IOException {
/* 1404 */     int i = (int)this.imageSize;
/* 1405 */     if (i == 0) {
/* 1406 */       i = (int)(this.bitmapFileSize - this.bitmapOffset);
/*      */     }
/*      */     
/* 1409 */     int j = 0;
/*      */ 
/*      */     
/* 1412 */     int k = this.width % 4;
/* 1413 */     if (k != 0) {
/* 1414 */       j = 4 - k;
/*      */     }
/*      */ 
/*      */     
/* 1418 */     byte[] arrayOfByte = new byte[i];
/* 1419 */     boolean bool = false;
/* 1420 */     this.iis.readFully(arrayOfByte, 0, i);
/*      */ 
/*      */     
/* 1423 */     decodeRLE8(i, j, arrayOfByte, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void decodeRLE8(int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws IOException {
/* 1431 */     byte[] arrayOfByte = new byte[this.width * this.height];
/* 1432 */     int i = 0, j = 0;
/*      */     
/* 1434 */     boolean bool = false;
/* 1435 */     int k = this.isBottomUp ? (this.height - 1) : 0;
/*      */     
/* 1437 */     int m = ((ComponentSampleModel)this.sampleModel).getScanlineStride();
/* 1438 */     byte b = 0;
/*      */     
/* 1440 */     while (i != paramInt1) {
/* 1441 */       int n = paramArrayOfbyte1[i++] & 0xFF;
/* 1442 */       if (n == 0) {
/* 1443 */         int i1; int i2; int i3; byte b1; switch (paramArrayOfbyte1[i++] & 0xFF) {
/*      */ 
/*      */           
/*      */           case 0:
/* 1447 */             if (k >= this.sourceRegion.y && k < this.sourceRegion.y + this.sourceRegion.height)
/*      */             {
/* 1449 */               if (this.noTransform) {
/* 1450 */                 int i4 = k * this.width;
/* 1451 */                 for (byte b2 = 0; b2 < this.width; b2++)
/* 1452 */                   paramArrayOfbyte2[i4++] = arrayOfByte[b2]; 
/* 1453 */                 processImageUpdate(this.bi, 0, k, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */                 
/* 1456 */                 b++;
/* 1457 */               } else if ((k - this.sourceRegion.y) % this.scaleY == 0) {
/* 1458 */                 int i4 = (k - this.sourceRegion.y) / this.scaleY + this.destinationRegion.y;
/*      */                 
/* 1460 */                 int i5 = i4 * m;
/* 1461 */                 i5 += this.destinationRegion.x;
/* 1462 */                 int i6 = this.sourceRegion.x;
/* 1463 */                 for (; i6 < this.sourceRegion.x + this.sourceRegion.width; 
/* 1464 */                   i6 += this.scaleX)
/* 1465 */                   paramArrayOfbyte2[i5++] = arrayOfByte[i6]; 
/* 1466 */                 processImageUpdate(this.bi, 0, i4, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */                 
/* 1469 */                 b++;
/*      */               } 
/*      */             }
/* 1472 */             processImageProgress(100.0F * b / this.destinationRegion.height);
/* 1473 */             k += this.isBottomUp ? -1 : 1;
/* 1474 */             j = 0;
/*      */             
/* 1476 */             if (abortRequested()) {
/* 1477 */               bool = true;
/*      */             }
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 1:
/* 1484 */             bool = true;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 2:
/* 1489 */             i1 = paramArrayOfbyte1[i++] & 0xFF;
/* 1490 */             i2 = paramArrayOfbyte1[i] & 0xFF;
/*      */             
/* 1492 */             j += i1 + i2 * this.width;
/*      */             break;
/*      */           
/*      */           default:
/* 1496 */             i3 = paramArrayOfbyte1[i - 1] & 0xFF;
/* 1497 */             for (b1 = 0; b1 < i3; b1++) {
/* 1498 */               arrayOfByte[j++] = (byte)(paramArrayOfbyte1[i++] & 0xFF);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1503 */             if ((i3 & 0x1) == 1)
/* 1504 */               i++; 
/*      */             break;
/*      */         } 
/*      */       } else {
/* 1508 */         for (byte b1 = 0; b1 < n; b1++) {
/* 1509 */           arrayOfByte[j++] = (byte)(paramArrayOfbyte1[i] & 0xFF);
/*      */         }
/*      */         
/* 1512 */         i++;
/*      */       } 
/*      */ 
/*      */       
/* 1516 */       if (bool) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readRLE4(byte[] paramArrayOfbyte) throws IOException {
/* 1525 */     int i = (int)this.imageSize;
/* 1526 */     if (i == 0) {
/* 1527 */       i = (int)(this.bitmapFileSize - this.bitmapOffset);
/*      */     }
/*      */     
/* 1530 */     int j = 0;
/*      */ 
/*      */     
/* 1533 */     int k = this.width % 4;
/* 1534 */     if (k != 0) {
/* 1535 */       j = 4 - k;
/*      */     }
/*      */ 
/*      */     
/* 1539 */     byte[] arrayOfByte = new byte[i];
/* 1540 */     this.iis.readFully(arrayOfByte, 0, i);
/*      */ 
/*      */     
/* 1543 */     decodeRLE4(i, j, arrayOfByte, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void decodeRLE4(int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws IOException {
/* 1550 */     byte[] arrayOfByte = new byte[this.width];
/* 1551 */     int i = 0, j = 0;
/*      */     
/* 1553 */     boolean bool = false;
/* 1554 */     int k = this.isBottomUp ? (this.height - 1) : 0;
/*      */     
/* 1556 */     int m = ((MultiPixelPackedSampleModel)this.sampleModel).getScanlineStride();
/* 1557 */     byte b = 0;
/*      */     
/* 1559 */     while (i != paramInt1) {
/*      */       
/* 1561 */       int n = paramArrayOfbyte1[i++] & 0xFF;
/* 1562 */       if (n == 0) {
/*      */         int i1; int i2;
/*      */         int i3;
/*      */         byte b1;
/* 1566 */         switch (paramArrayOfbyte1[i++] & 0xFF) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 0:
/* 1571 */             if (k >= this.sourceRegion.y && k < this.sourceRegion.y + this.sourceRegion.height)
/*      */             {
/* 1573 */               if (this.noTransform) {
/* 1574 */                 int i4 = k * (this.width + 1 >> 1);
/* 1575 */                 for (byte b2 = 0, b3 = 0; b2 < this.width >> 1; b2++) {
/* 1576 */                   paramArrayOfbyte2[i4++] = (byte)(arrayOfByte[b3++] << 4 | arrayOfByte[b3++]);
/*      */                 }
/* 1578 */                 if ((this.width & 0x1) == 1) {
/* 1579 */                   paramArrayOfbyte2[i4] = (byte)(paramArrayOfbyte2[i4] | arrayOfByte[this.width - 1] << 4);
/*      */                 }
/* 1581 */                 processImageUpdate(this.bi, 0, k, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */                 
/* 1584 */                 b++;
/* 1585 */               } else if ((k - this.sourceRegion.y) % this.scaleY == 0) {
/* 1586 */                 int i4 = (k - this.sourceRegion.y) / this.scaleY + this.destinationRegion.y;
/*      */                 
/* 1588 */                 int i5 = i4 * m;
/* 1589 */                 i5 += this.destinationRegion.x >> 1;
/* 1590 */                 int i6 = 1 - (this.destinationRegion.x & 0x1) << 2;
/* 1591 */                 int i7 = this.sourceRegion.x;
/* 1592 */                 for (; i7 < this.sourceRegion.x + this.sourceRegion.width; 
/* 1593 */                   i7 += this.scaleX) {
/* 1594 */                   paramArrayOfbyte2[i5] = (byte)(paramArrayOfbyte2[i5] | arrayOfByte[i7] << i6);
/* 1595 */                   i6 += 4;
/* 1596 */                   if (i6 == 4) {
/* 1597 */                     i5++;
/*      */                   }
/* 1599 */                   i6 &= 0x7;
/*      */                 } 
/* 1601 */                 processImageUpdate(this.bi, 0, i4, this.destinationRegion.width, 1, 1, 1, new int[] { 0 });
/*      */ 
/*      */                 
/* 1604 */                 b++;
/*      */               } 
/*      */             }
/* 1607 */             processImageProgress(100.0F * b / this.destinationRegion.height);
/* 1608 */             k += this.isBottomUp ? -1 : 1;
/* 1609 */             j = 0;
/*      */             
/* 1611 */             if (abortRequested()) {
/* 1612 */               bool = true;
/*      */             }
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 1:
/* 1619 */             bool = true;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 2:
/* 1624 */             i1 = paramArrayOfbyte1[i++] & 0xFF;
/* 1625 */             i2 = paramArrayOfbyte1[i] & 0xFF;
/*      */             
/* 1627 */             j += i1 + i2 * this.width;
/*      */             break;
/*      */           
/*      */           default:
/* 1631 */             i3 = paramArrayOfbyte1[i - 1] & 0xFF;
/* 1632 */             for (b1 = 0; b1 < i3; b1++) {
/* 1633 */               arrayOfByte[j++] = (byte)(((b1 & 0x1) == 0) ? ((paramArrayOfbyte1[i] & 0xF0) >> 4) : (paramArrayOfbyte1[i++] & 0xF));
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1639 */             if ((i3 & 0x1) == 1) {
/* 1640 */               i++;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1645 */             if (((int)Math.ceil((i3 / 2)) & 0x1) == 1) {
/* 1646 */               i++;
/*      */             }
/*      */             break;
/*      */         } 
/*      */       
/*      */       } else {
/* 1652 */         int[] arrayOfInt = { (paramArrayOfbyte1[i] & 0xF0) >> 4, paramArrayOfbyte1[i] & 0xF };
/*      */         
/* 1654 */         for (byte b1 = 0; b1 < n && j < this.width; b1++) {
/* 1655 */           arrayOfByte[j++] = (byte)arrayOfInt[b1 & 0x1];
/*      */         }
/*      */         
/* 1658 */         i++;
/*      */       } 
/*      */ 
/*      */       
/* 1662 */       if (bool) {
/*      */         break;
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
/*      */   private BufferedImage readEmbedded(int paramInt, BufferedImage paramBufferedImage, ImageReadParam paramImageReadParam) throws IOException {
/*      */     String str;
/* 1681 */     switch (paramInt) {
/*      */       case 4:
/* 1683 */         str = "JPEG";
/*      */         break;
/*      */       case 5:
/* 1686 */         str = "PNG";
/*      */         break;
/*      */       default:
/* 1689 */         throw new IOException("Unexpected compression type: " + paramInt);
/*      */     } 
/*      */ 
/*      */     
/* 1693 */     ImageReader imageReader = ImageIO.getImageReadersByFormatName(str).next();
/* 1694 */     if (imageReader == null) {
/* 1695 */       throw new RuntimeException(I18N.getString("BMPImageReader4") + " " + str);
/*      */     }
/*      */ 
/*      */     
/* 1699 */     byte[] arrayOfByte = new byte[(int)this.imageSize];
/* 1700 */     this.iis.read(arrayOfByte);
/* 1701 */     imageReader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(arrayOfByte)));
/* 1702 */     if (paramBufferedImage == null) {
/* 1703 */       ImageTypeSpecifier imageTypeSpecifier = imageReader.getImageTypes(0).next();
/* 1704 */       paramBufferedImage = imageTypeSpecifier.createBufferedImage(this.destinationRegion.x + this.destinationRegion.width, this.destinationRegion.y + this.destinationRegion.height);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1710 */     imageReader.addIIOReadProgressListener(new EmbeddedProgressAdapter()
/*      */         {
/*      */           public void imageProgress(ImageReader param1ImageReader, float param1Float)
/*      */           {
/* 1714 */             BMPImageReader.this.processImageProgress(param1Float);
/*      */           }
/*      */         });
/*      */     
/* 1718 */     imageReader.addIIOReadUpdateListener(new IIOReadUpdateListener()
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void imageUpdate(ImageReader param1ImageReader, BufferedImage param1BufferedImage, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int[] param1ArrayOfint)
/*      */           {
/* 1726 */             BMPImageReader.this.processImageUpdate(param1BufferedImage, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1ArrayOfint);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void passComplete(ImageReader param1ImageReader, BufferedImage param1BufferedImage) {
/* 1733 */             BMPImageReader.this.processPassComplete(param1BufferedImage);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void passStarted(ImageReader param1ImageReader, BufferedImage param1BufferedImage, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int[] param1ArrayOfint) {
/* 1743 */             BMPImageReader.this.processPassStarted(param1BufferedImage, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7, param1ArrayOfint);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void thumbnailPassComplete(ImageReader param1ImageReader, BufferedImage param1BufferedImage) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void thumbnailPassStarted(ImageReader param1ImageReader, BufferedImage param1BufferedImage, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int[] param1ArrayOfint) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void thumbnailUpdate(ImageReader param1ImageReader, BufferedImage param1BufferedImage, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int[] param1ArrayOfint) {}
/*      */         });
/* 1764 */     imageReader.addIIOReadWarningListener(new IIOReadWarningListener()
/*      */         {
/*      */           public void warningOccurred(ImageReader param1ImageReader, String param1String) {
/* 1767 */             BMPImageReader.this.processWarningOccurred(param1String);
/*      */           }
/*      */         });
/*      */     
/* 1771 */     ImageReadParam imageReadParam = imageReader.getDefaultReadParam();
/* 1772 */     imageReadParam.setDestination(paramBufferedImage);
/* 1773 */     imageReadParam.setDestinationBands(paramImageReadParam.getDestinationBands());
/* 1774 */     imageReadParam.setDestinationOffset(paramImageReadParam.getDestinationOffset());
/* 1775 */     imageReadParam.setSourceBands(paramImageReadParam.getSourceBands());
/* 1776 */     imageReadParam.setSourceRegion(paramImageReadParam.getSourceRegion());
/* 1777 */     imageReadParam.setSourceSubsampling(paramImageReadParam.getSourceXSubsampling(), paramImageReadParam
/* 1778 */         .getSourceYSubsampling(), paramImageReadParam
/* 1779 */         .getSubsamplingXOffset(), paramImageReadParam
/* 1780 */         .getSubsamplingYOffset());
/* 1781 */     imageReader.read(0, imageReadParam);
/* 1782 */     return paramBufferedImage;
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
/* 1797 */   private static Boolean isLinkedProfileDisabled = null; private class EmbeddedProgressAdapter implements IIOReadProgressListener {
/*      */     private EmbeddedProgressAdapter() {} public void imageComplete(ImageReader param1ImageReader) {} public void imageProgress(ImageReader param1ImageReader, float param1Float) {} public void imageStarted(ImageReader param1ImageReader, int param1Int) {} public void thumbnailComplete(ImageReader param1ImageReader) {} public void thumbnailProgress(ImageReader param1ImageReader, float param1Float) {} public void thumbnailStarted(ImageReader param1ImageReader, int param1Int1, int param1Int2) {} public void sequenceComplete(ImageReader param1ImageReader) {} public void sequenceStarted(ImageReader param1ImageReader, int param1Int) {} public void readAborted(ImageReader param1ImageReader) {} }
/*      */   private static boolean isLinkedProfileAllowed() {
/* 1800 */     if (isLinkedProfileDisabled == null) {
/* 1801 */       PrivilegedAction<Boolean> privilegedAction = new PrivilegedAction<Boolean>() {
/*      */           public Boolean run() {
/* 1803 */             return Boolean.valueOf(Boolean.getBoolean("sun.imageio.plugins.bmp.disableLinkedProfiles"));
/*      */           }
/*      */         };
/* 1806 */       isLinkedProfileDisabled = AccessController.<Boolean>doPrivileged(privilegedAction);
/*      */     } 
/* 1808 */     return !isLinkedProfileDisabled.booleanValue();
/*      */   }
/*      */   
/* 1811 */   private static Boolean isWindowsPlatform = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isUncOrDevicePath(byte[] paramArrayOfbyte) {
/* 1825 */     if (isWindowsPlatform == null) {
/* 1826 */       PrivilegedAction<Boolean> privilegedAction = new PrivilegedAction<Boolean>() {
/*      */           public Boolean run() {
/* 1828 */             String str = System.getProperty("os.name");
/* 1829 */             return Boolean.valueOf((str != null && str
/* 1830 */                 .toLowerCase().startsWith("win")));
/*      */           }
/*      */         };
/* 1833 */       isWindowsPlatform = AccessController.<Boolean>doPrivileged(privilegedAction);
/*      */     } 
/*      */     
/* 1836 */     if (!isWindowsPlatform.booleanValue())
/*      */     {
/* 1838 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1842 */     if (paramArrayOfbyte[0] == 47) paramArrayOfbyte[0] = 92; 
/* 1843 */     if (paramArrayOfbyte[1] == 47) paramArrayOfbyte[1] = 92; 
/* 1844 */     if (paramArrayOfbyte[3] == 47) paramArrayOfbyte[3] = 92;
/*      */ 
/*      */     
/* 1847 */     if (paramArrayOfbyte[0] == 92 && paramArrayOfbyte[1] == 92) {
/* 1848 */       if (paramArrayOfbyte[2] == 63 && paramArrayOfbyte[3] == 92)
/*      */       {
/* 1850 */         return ((paramArrayOfbyte[4] == 85 || paramArrayOfbyte[4] == 117) && (paramArrayOfbyte[5] == 78 || paramArrayOfbyte[5] == 110) && (paramArrayOfbyte[6] == 67 || paramArrayOfbyte[6] == 99));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1855 */       return true;
/*      */     } 
/*      */     
/* 1858 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/bmp/BMPImageReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */