/*      */ package com.sun.imageio.plugins.gif;
/*      */ 
/*      */ import com.sun.imageio.plugins.common.LZWCompressor;
/*      */ import com.sun.imageio.plugins.common.PaletteBuilder;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ComponentSampleModel;
/*      */ import java.awt.image.DataBufferByte;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import javax.imageio.IIOException;
/*      */ import javax.imageio.IIOImage;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.ImageWriteParam;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.metadata.IIOInvalidTreeException;
/*      */ import javax.imageio.metadata.IIOMetadata;
/*      */ import javax.imageio.metadata.IIOMetadataNode;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import sun.awt.image.ByteComponentRaster;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GIFImageWriter
/*      */   extends ImageWriter
/*      */ {
/*      */   private static final boolean DEBUG = false;
/*      */   static final String STANDARD_METADATA_NAME = "javax_imageio_1.0";
/*      */   static final String STREAM_METADATA_NAME = "javax_imageio_gif_stream_1.0";
/*      */   static final String IMAGE_METADATA_NAME = "javax_imageio_gif_image_1.0";
/*   75 */   private ImageOutputStream stream = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isWritingSequence = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean wroteSequenceHeader = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   90 */   private GIFWritableStreamMetadata theStreamMetadata = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   private int imageIndex = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getNumBits(int paramInt) throws IOException {
/*      */     byte b;
/*  103 */     switch (paramInt) {
/*      */       case 2:
/*  105 */         b = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  132 */         return b;case 4: b = 2; return b;case 8: b = 3; return b;case 16: b = 4; return b;case 32: b = 5; return b;case 64: b = 6; return b;case 128: b = 7; return b;case 256: b = 8; return b;
/*      */     } 
/*      */     throw new IOException("Bad palette length: " + paramInt + "!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void computeRegions(Rectangle paramRectangle, Dimension paramDimension, ImageWriteParam paramImageWriteParam) {
/*  143 */     int i = 1;
/*  144 */     int j = 1;
/*  145 */     if (paramImageWriteParam != null) {
/*  146 */       int[] arrayOfInt = paramImageWriteParam.getSourceBands();
/*  147 */       if (arrayOfInt != null && (arrayOfInt.length != 1 || arrayOfInt[0] != 0))
/*      */       {
/*      */         
/*  150 */         throw new IllegalArgumentException("Cannot sub-band image!");
/*      */       }
/*      */ 
/*      */       
/*  154 */       Rectangle rectangle = paramImageWriteParam.getSourceRegion();
/*  155 */       if (rectangle != null) {
/*      */         
/*  157 */         rectangle = rectangle.intersection(paramRectangle);
/*  158 */         paramRectangle.setBounds(rectangle);
/*      */       } 
/*      */ 
/*      */       
/*  162 */       int k = paramImageWriteParam.getSubsamplingXOffset();
/*  163 */       int m = paramImageWriteParam.getSubsamplingYOffset();
/*  164 */       paramRectangle.x += k;
/*  165 */       paramRectangle.y += m;
/*  166 */       paramRectangle.width -= k;
/*  167 */       paramRectangle.height -= m;
/*      */ 
/*      */       
/*  170 */       i = paramImageWriteParam.getSourceXSubsampling();
/*  171 */       j = paramImageWriteParam.getSourceYSubsampling();
/*      */     } 
/*      */ 
/*      */     
/*  175 */     paramDimension.setSize((paramRectangle.width + i - 1) / i, (paramRectangle.height + j - 1) / j);
/*      */     
/*  177 */     if (paramDimension.width <= 0 || paramDimension.height <= 0) {
/*  178 */       throw new IllegalArgumentException("Empty source region!");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] createColorTable(ColorModel paramColorModel, SampleModel paramSampleModel) {
/*      */     byte[] arrayOfByte;
/*  189 */     if (paramColorModel instanceof IndexColorModel) {
/*  190 */       IndexColorModel indexColorModel = (IndexColorModel)paramColorModel;
/*  191 */       int i = indexColorModel.getMapSize();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  198 */       int j = getGifPaletteSize(i);
/*      */       
/*  200 */       byte[] arrayOfByte1 = new byte[j];
/*  201 */       byte[] arrayOfByte2 = new byte[j];
/*  202 */       byte[] arrayOfByte3 = new byte[j];
/*  203 */       indexColorModel.getReds(arrayOfByte1);
/*  204 */       indexColorModel.getGreens(arrayOfByte2);
/*  205 */       indexColorModel.getBlues(arrayOfByte3);
/*      */ 
/*      */       
/*      */       int k;
/*      */ 
/*      */       
/*  211 */       for (k = i; k < j; k++) {
/*  212 */         arrayOfByte1[k] = arrayOfByte1[0];
/*  213 */         arrayOfByte2[k] = arrayOfByte2[0];
/*  214 */         arrayOfByte3[k] = arrayOfByte3[0];
/*      */       } 
/*      */       
/*  217 */       arrayOfByte = new byte[3 * j];
/*  218 */       k = 0;
/*  219 */       for (byte b = 0; b < j; b++) {
/*  220 */         arrayOfByte[k++] = arrayOfByte1[b];
/*  221 */         arrayOfByte[k++] = arrayOfByte2[b];
/*  222 */         arrayOfByte[k++] = arrayOfByte3[b];
/*      */       } 
/*  224 */     } else if (paramSampleModel.getNumBands() == 1) {
/*      */       
/*  226 */       int i = paramSampleModel.getSampleSize()[0];
/*  227 */       if (i > 8) {
/*  228 */         i = 8;
/*      */       }
/*  230 */       int j = 3 * (1 << i);
/*  231 */       arrayOfByte = new byte[j];
/*  232 */       for (byte b = 0; b < j; b++) {
/*  233 */         arrayOfByte[b] = (byte)(b / 3);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  238 */       arrayOfByte = null;
/*      */     } 
/*      */     
/*  241 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getGifPaletteSize(int paramInt) {
/*  249 */     if (paramInt <= 2) {
/*  250 */       return 2;
/*      */     }
/*  252 */     paramInt--;
/*  253 */     paramInt |= paramInt >> 1;
/*  254 */     paramInt |= paramInt >> 2;
/*  255 */     paramInt |= paramInt >> 4;
/*  256 */     paramInt |= paramInt >> 8;
/*  257 */     paramInt |= paramInt >> 16;
/*  258 */     return paramInt + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public GIFImageWriter(GIFImageWriterSpi paramGIFImageWriterSpi) {
/*  264 */     super(paramGIFImageWriterSpi);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteSequence() {
/*  271 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void convertMetadata(String paramString, IIOMetadata paramIIOMetadata1, IIOMetadata paramIIOMetadata2) {
/*  282 */     String str1 = null;
/*      */     
/*  284 */     String str2 = paramIIOMetadata1.getNativeMetadataFormatName();
/*  285 */     if (str2 != null && str2
/*  286 */       .equals(paramString)) {
/*  287 */       str1 = paramString;
/*      */     } else {
/*  289 */       String[] arrayOfString = paramIIOMetadata1.getExtraMetadataFormatNames();
/*      */       
/*  291 */       if (arrayOfString != null) {
/*  292 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*  293 */           if (arrayOfString[b].equals(paramString)) {
/*  294 */             str1 = paramString;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*  301 */     if (str1 == null && paramIIOMetadata1
/*  302 */       .isStandardMetadataFormatSupported()) {
/*  303 */       str1 = "javax_imageio_1.0";
/*      */     }
/*      */     
/*  306 */     if (str1 != null) {
/*      */       try {
/*  308 */         Node node = paramIIOMetadata1.getAsTree(str1);
/*  309 */         paramIIOMetadata2.mergeTree(str1, node);
/*  310 */       } catch (IIOInvalidTreeException iIOInvalidTreeException) {}
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
/*      */   public IIOMetadata convertStreamMetadata(IIOMetadata paramIIOMetadata, ImageWriteParam paramImageWriteParam) {
/*  322 */     if (paramIIOMetadata == null) {
/*  323 */       throw new IllegalArgumentException("inData == null!");
/*      */     }
/*      */     
/*  326 */     IIOMetadata iIOMetadata = getDefaultStreamMetadata(paramImageWriteParam);
/*      */     
/*  328 */     convertMetadata("javax_imageio_gif_stream_1.0", paramIIOMetadata, iIOMetadata);
/*      */     
/*  330 */     return iIOMetadata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOMetadata convertImageMetadata(IIOMetadata paramIIOMetadata, ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/*  340 */     if (paramIIOMetadata == null) {
/*  341 */       throw new IllegalArgumentException("inData == null!");
/*      */     }
/*  343 */     if (paramImageTypeSpecifier == null) {
/*  344 */       throw new IllegalArgumentException("imageType == null!");
/*      */     }
/*      */ 
/*      */     
/*  348 */     GIFWritableImageMetadata gIFWritableImageMetadata = (GIFWritableImageMetadata)getDefaultImageMetadata(paramImageTypeSpecifier, paramImageWriteParam);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  353 */     boolean bool = gIFWritableImageMetadata.interlaceFlag;
/*      */     
/*  355 */     convertMetadata("javax_imageio_gif_image_1.0", paramIIOMetadata, gIFWritableImageMetadata);
/*      */ 
/*      */ 
/*      */     
/*  359 */     if (paramImageWriteParam != null && paramImageWriteParam.canWriteProgressive() && paramImageWriteParam
/*  360 */       .getProgressiveMode() != 3) {
/*  361 */       gIFWritableImageMetadata.interlaceFlag = bool;
/*      */     }
/*      */     
/*  364 */     return gIFWritableImageMetadata;
/*      */   }
/*      */   
/*      */   public void endWriteSequence() throws IOException {
/*  368 */     if (this.stream == null) {
/*  369 */       throw new IllegalStateException("output == null!");
/*      */     }
/*  371 */     if (!this.isWritingSequence) {
/*  372 */       throw new IllegalStateException("prepareWriteSequence() was not invoked!");
/*      */     }
/*  374 */     writeTrailer();
/*  375 */     resetLocal();
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/*  380 */     GIFWritableImageMetadata gIFWritableImageMetadata = new GIFWritableImageMetadata();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  385 */     SampleModel sampleModel = paramImageTypeSpecifier.getSampleModel();
/*      */ 
/*      */     
/*  388 */     Rectangle rectangle = new Rectangle(sampleModel.getWidth(), sampleModel.getHeight());
/*  389 */     Dimension dimension = new Dimension();
/*  390 */     computeRegions(rectangle, dimension, paramImageWriteParam);
/*      */     
/*  392 */     gIFWritableImageMetadata.imageWidth = dimension.width;
/*  393 */     gIFWritableImageMetadata.imageHeight = dimension.height;
/*      */ 
/*      */ 
/*      */     
/*  397 */     if (paramImageWriteParam != null && paramImageWriteParam.canWriteProgressive() && paramImageWriteParam
/*  398 */       .getProgressiveMode() == 0) {
/*  399 */       gIFWritableImageMetadata.interlaceFlag = false;
/*      */     } else {
/*  401 */       gIFWritableImageMetadata.interlaceFlag = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  406 */     ColorModel colorModel = paramImageTypeSpecifier.getColorModel();
/*      */     
/*  408 */     gIFWritableImageMetadata
/*  409 */       .localColorTable = createColorTable(colorModel, sampleModel);
/*      */ 
/*      */ 
/*      */     
/*  413 */     if (colorModel instanceof IndexColorModel) {
/*      */       
/*  415 */       int i = ((IndexColorModel)colorModel).getTransparentPixel();
/*  416 */       if (i != -1) {
/*  417 */         gIFWritableImageMetadata.transparentColorFlag = true;
/*  418 */         gIFWritableImageMetadata.transparentColorIndex = i;
/*      */       } 
/*      */     } 
/*      */     
/*  422 */     return gIFWritableImageMetadata;
/*      */   }
/*      */   
/*      */   public IIOMetadata getDefaultStreamMetadata(ImageWriteParam paramImageWriteParam) {
/*  426 */     GIFWritableStreamMetadata gIFWritableStreamMetadata = new GIFWritableStreamMetadata();
/*      */     
/*  428 */     gIFWritableStreamMetadata.version = "89a";
/*  429 */     return gIFWritableStreamMetadata;
/*      */   }
/*      */   
/*      */   public ImageWriteParam getDefaultWriteParam() {
/*  433 */     return new GIFImageWriteParam(getLocale());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareWriteSequence(IIOMetadata paramIIOMetadata) throws IOException {
/*  439 */     if (this.stream == null) {
/*  440 */       throw new IllegalStateException("Output is not set.");
/*      */     }
/*      */     
/*  443 */     resetLocal();
/*      */ 
/*      */     
/*  446 */     if (paramIIOMetadata == null) {
/*  447 */       this
/*  448 */         .theStreamMetadata = (GIFWritableStreamMetadata)getDefaultStreamMetadata((ImageWriteParam)null);
/*      */     } else {
/*  450 */       this.theStreamMetadata = new GIFWritableStreamMetadata();
/*  451 */       convertMetadata("javax_imageio_gif_stream_1.0", paramIIOMetadata, this.theStreamMetadata);
/*      */     } 
/*      */ 
/*      */     
/*  455 */     this.isWritingSequence = true;
/*      */   }
/*      */   
/*      */   public void reset() {
/*  459 */     super.reset();
/*  460 */     resetLocal();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetLocal() {
/*  467 */     this.isWritingSequence = false;
/*  468 */     this.wroteSequenceHeader = false;
/*  469 */     this.theStreamMetadata = null;
/*  470 */     this.imageIndex = 0;
/*      */   }
/*      */   
/*      */   public void setOutput(Object paramObject) {
/*  474 */     super.setOutput(paramObject);
/*  475 */     if (paramObject != null) {
/*  476 */       if (!(paramObject instanceof ImageOutputStream)) {
/*  477 */         throw new IllegalArgumentException("output is not an ImageOutputStream");
/*      */       }
/*      */       
/*  480 */       this.stream = (ImageOutputStream)paramObject;
/*  481 */       this.stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
/*      */     } else {
/*  483 */       this.stream = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void write(IIOMetadata paramIIOMetadata, IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/*      */     GIFWritableStreamMetadata gIFWritableStreamMetadata;
/*  490 */     if (this.stream == null) {
/*  491 */       throw new IllegalStateException("output == null!");
/*      */     }
/*  493 */     if (paramIIOImage == null) {
/*  494 */       throw new IllegalArgumentException("iioimage == null!");
/*      */     }
/*  496 */     if (paramIIOImage.hasRaster()) {
/*  497 */       throw new UnsupportedOperationException("canWriteRasters() == false!");
/*      */     }
/*      */     
/*  500 */     resetLocal();
/*      */ 
/*      */     
/*  503 */     if (paramIIOMetadata == null) {
/*      */       
/*  505 */       gIFWritableStreamMetadata = (GIFWritableStreamMetadata)getDefaultStreamMetadata(paramImageWriteParam);
/*      */     } else {
/*      */       
/*  508 */       gIFWritableStreamMetadata = (GIFWritableStreamMetadata)convertStreamMetadata(paramIIOMetadata, paramImageWriteParam);
/*      */     } 
/*      */     
/*  511 */     write(true, true, gIFWritableStreamMetadata, paramIIOImage, paramImageWriteParam);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeToSequence(IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/*  516 */     if (this.stream == null) {
/*  517 */       throw new IllegalStateException("output == null!");
/*      */     }
/*  519 */     if (paramIIOImage == null) {
/*  520 */       throw new IllegalArgumentException("image == null!");
/*      */     }
/*  522 */     if (paramIIOImage.hasRaster()) {
/*  523 */       throw new UnsupportedOperationException("canWriteRasters() == false!");
/*      */     }
/*  525 */     if (!this.isWritingSequence) {
/*  526 */       throw new IllegalStateException("prepareWriteSequence() was not invoked!");
/*      */     }
/*      */     
/*  529 */     write(!this.wroteSequenceHeader, false, this.theStreamMetadata, paramIIOImage, paramImageWriteParam);
/*      */ 
/*      */     
/*  532 */     if (!this.wroteSequenceHeader) {
/*  533 */       this.wroteSequenceHeader = true;
/*      */     }
/*      */     
/*  536 */     this.imageIndex++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needToCreateIndex(RenderedImage paramRenderedImage) {
/*  542 */     SampleModel sampleModel = paramRenderedImage.getSampleModel();
/*  543 */     ColorModel colorModel = paramRenderedImage.getColorModel();
/*      */     
/*  545 */     return (sampleModel.getNumBands() != 1 || sampleModel
/*  546 */       .getSampleSize()[0] > 8 || colorModel
/*  547 */       .getComponentSize()[0] > 8);
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
/*      */   private void write(boolean paramBoolean1, boolean paramBoolean2, IIOMetadata paramIIOMetadata, IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/*  577 */     clearAbortRequest();
/*      */     
/*  579 */     RenderedImage renderedImage = paramIIOImage.getRenderedImage();
/*      */ 
/*      */     
/*  582 */     if (needToCreateIndex(renderedImage)) {
/*  583 */       renderedImage = PaletteBuilder.createIndexedImage(renderedImage);
/*  584 */       paramIIOImage.setRenderedImage(renderedImage);
/*      */     } 
/*      */     
/*  587 */     ColorModel colorModel = renderedImage.getColorModel();
/*  588 */     SampleModel sampleModel = renderedImage.getSampleModel();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  594 */     Rectangle rectangle = new Rectangle(renderedImage.getMinX(), renderedImage.getMinY(), renderedImage.getWidth(), renderedImage.getHeight());
/*  595 */     Dimension dimension = new Dimension();
/*  596 */     computeRegions(rectangle, dimension, paramImageWriteParam);
/*      */ 
/*      */     
/*  599 */     GIFWritableImageMetadata gIFWritableImageMetadata = null;
/*  600 */     if (paramIIOImage.getMetadata() != null) {
/*  601 */       gIFWritableImageMetadata = new GIFWritableImageMetadata();
/*  602 */       convertMetadata("javax_imageio_gif_image_1.0", paramIIOImage.getMetadata(), gIFWritableImageMetadata);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  611 */       if (gIFWritableImageMetadata.localColorTable == null) {
/*  612 */         gIFWritableImageMetadata
/*  613 */           .localColorTable = createColorTable(colorModel, sampleModel);
/*      */ 
/*      */ 
/*      */         
/*  617 */         if (colorModel instanceof IndexColorModel) {
/*  618 */           IndexColorModel indexColorModel = (IndexColorModel)colorModel;
/*      */           
/*  620 */           int i = indexColorModel.getTransparentPixel();
/*  621 */           gIFWritableImageMetadata.transparentColorFlag = (i != -1);
/*  622 */           if (gIFWritableImageMetadata.transparentColorFlag) {
/*  623 */             gIFWritableImageMetadata.transparentColorIndex = i;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  636 */     byte[] arrayOfByte = null;
/*      */ 
/*      */ 
/*      */     
/*  640 */     if (paramBoolean1) {
/*  641 */       int i; if (paramIIOMetadata == null) {
/*  642 */         throw new IllegalArgumentException("Cannot write null header!");
/*      */       }
/*      */       
/*  645 */       GIFWritableStreamMetadata gIFWritableStreamMetadata = (GIFWritableStreamMetadata)paramIIOMetadata;
/*      */ 
/*      */ 
/*      */       
/*  649 */       if (gIFWritableStreamMetadata.version == null) {
/*  650 */         gIFWritableStreamMetadata.version = "89a";
/*      */       }
/*      */ 
/*      */       
/*  654 */       if (gIFWritableStreamMetadata.logicalScreenWidth == -1)
/*      */       {
/*      */         
/*  657 */         gIFWritableStreamMetadata.logicalScreenWidth = dimension.width;
/*      */       }
/*      */       
/*  660 */       if (gIFWritableStreamMetadata.logicalScreenHeight == -1)
/*      */       {
/*      */         
/*  663 */         gIFWritableStreamMetadata.logicalScreenHeight = dimension.height;
/*      */       }
/*      */       
/*  666 */       if (gIFWritableStreamMetadata.colorResolution == -1)
/*      */       {
/*      */         
/*  669 */         gIFWritableStreamMetadata
/*      */           
/*  671 */           .colorResolution = (colorModel != null) ? colorModel.getComponentSize()[0] : sampleModel.getSampleSize()[0];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  676 */       if (gIFWritableStreamMetadata.globalColorTable == null) {
/*  677 */         if (this.isWritingSequence && gIFWritableImageMetadata != null && gIFWritableImageMetadata.localColorTable != null) {
/*      */ 
/*      */ 
/*      */           
/*  681 */           gIFWritableStreamMetadata.globalColorTable = gIFWritableImageMetadata.localColorTable;
/*      */         }
/*  683 */         else if (gIFWritableImageMetadata == null || gIFWritableImageMetadata.localColorTable == null) {
/*      */ 
/*      */           
/*  686 */           gIFWritableStreamMetadata
/*  687 */             .globalColorTable = createColorTable(colorModel, sampleModel);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  700 */       arrayOfByte = gIFWritableStreamMetadata.globalColorTable;
/*      */ 
/*      */ 
/*      */       
/*  704 */       if (arrayOfByte != null) {
/*  705 */         i = getNumBits(arrayOfByte.length / 3);
/*  706 */       } else if (gIFWritableImageMetadata != null && gIFWritableImageMetadata.localColorTable != null) {
/*      */ 
/*      */         
/*  709 */         i = getNumBits(gIFWritableImageMetadata.localColorTable.length / 3);
/*      */       } else {
/*  711 */         i = sampleModel.getSampleSize(0);
/*      */       } 
/*  713 */       writeHeader(gIFWritableStreamMetadata, i);
/*  714 */     } else if (this.isWritingSequence) {
/*  715 */       arrayOfByte = this.theStreamMetadata.globalColorTable;
/*      */     } else {
/*  717 */       throw new IllegalArgumentException("Must write header for single image!");
/*      */     } 
/*      */ 
/*      */     
/*  721 */     writeImage(paramIIOImage.getRenderedImage(), gIFWritableImageMetadata, paramImageWriteParam, arrayOfByte, rectangle, dimension);
/*      */ 
/*      */ 
/*      */     
/*  725 */     if (paramBoolean2) {
/*  726 */       writeTrailer();
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
/*      */   private void writeImage(RenderedImage paramRenderedImage, GIFWritableImageMetadata paramGIFWritableImageMetadata, ImageWriteParam paramImageWriteParam, byte[] paramArrayOfbyte, Rectangle paramRectangle, Dimension paramDimension) throws IOException {
/*      */     boolean bool;
/*  744 */     ColorModel colorModel = paramRenderedImage.getColorModel();
/*  745 */     SampleModel sampleModel = paramRenderedImage.getSampleModel();
/*      */ 
/*      */     
/*  748 */     if (paramGIFWritableImageMetadata == null) {
/*      */       
/*  750 */       paramGIFWritableImageMetadata = (GIFWritableImageMetadata)getDefaultImageMetadata(new ImageTypeSpecifier(paramRenderedImage), paramImageWriteParam);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  755 */       bool = paramGIFWritableImageMetadata.transparentColorFlag;
/*      */     } else {
/*      */       
/*  758 */       NodeList nodeList = null;
/*      */       
/*      */       try {
/*  761 */         IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)paramGIFWritableImageMetadata.getAsTree("javax_imageio_gif_image_1.0");
/*  762 */         nodeList = iIOMetadataNode.getElementsByTagName("GraphicControlExtension");
/*  763 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  769 */       bool = (nodeList != null && nodeList.getLength() > 0) ? true : false;
/*      */ 
/*      */ 
/*      */       
/*  773 */       if (paramImageWriteParam != null && paramImageWriteParam.canWriteProgressive()) {
/*  774 */         if (paramImageWriteParam.getProgressiveMode() == 0) {
/*      */           
/*  776 */           paramGIFWritableImageMetadata.interlaceFlag = false;
/*  777 */         } else if (paramImageWriteParam.getProgressiveMode() == 1) {
/*      */           
/*  779 */           paramGIFWritableImageMetadata.interlaceFlag = true;
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  785 */     if (Arrays.equals(paramArrayOfbyte, paramGIFWritableImageMetadata.localColorTable)) {
/*  786 */       paramGIFWritableImageMetadata.localColorTable = null;
/*      */     }
/*      */ 
/*      */     
/*  790 */     paramGIFWritableImageMetadata.imageWidth = paramDimension.width;
/*  791 */     paramGIFWritableImageMetadata.imageHeight = paramDimension.height;
/*      */ 
/*      */     
/*  794 */     if (bool) {
/*  795 */       writeGraphicControlExtension(paramGIFWritableImageMetadata);
/*      */     }
/*      */ 
/*      */     
/*  799 */     writePlainTextExtension(paramGIFWritableImageMetadata);
/*  800 */     writeApplicationExtension(paramGIFWritableImageMetadata);
/*  801 */     writeCommentExtension(paramGIFWritableImageMetadata);
/*      */ 
/*      */ 
/*      */     
/*  805 */     int i = getNumBits((paramGIFWritableImageMetadata.localColorTable == null) ? ((paramArrayOfbyte == null) ? sampleModel
/*      */         
/*  807 */         .getSampleSize(0) : (paramArrayOfbyte.length / 3)) : (paramGIFWritableImageMetadata.localColorTable.length / 3));
/*      */ 
/*      */     
/*  810 */     writeImageDescriptor(paramGIFWritableImageMetadata, i);
/*      */ 
/*      */     
/*  813 */     writeRasterData(paramRenderedImage, paramRectangle, paramDimension, paramImageWriteParam, paramGIFWritableImageMetadata.interlaceFlag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeRows(RenderedImage paramRenderedImage, LZWCompressor paramLZWCompressor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11) throws IOException {
/*  824 */     int[] arrayOfInt = new int[paramInt5];
/*  825 */     byte[] arrayOfByte = new byte[paramInt8];
/*      */ 
/*      */ 
/*      */     
/*  829 */     Raster raster = (paramRenderedImage.getNumXTiles() == 1 && paramRenderedImage.getNumYTiles() == 1) ? paramRenderedImage.getTile(0, 0) : paramRenderedImage.getData(); int i;
/*  830 */     for (i = paramInt6; i < paramInt9; i += paramInt7) {
/*  831 */       if (paramInt10 % paramInt11 == 0) {
/*  832 */         if (abortRequested()) {
/*  833 */           processWriteAborted();
/*      */           return;
/*      */         } 
/*  836 */         processImageProgress(paramInt10 * 100.0F / paramInt9);
/*      */       } 
/*      */       
/*  839 */       raster.getSamples(paramInt1, paramInt3, paramInt5, 1, 0, arrayOfInt); int j;
/*  840 */       for (byte b = 0; b < paramInt8; b++, j += paramInt2) {
/*  841 */         arrayOfByte[b] = (byte)arrayOfInt[j];
/*      */       }
/*  843 */       paramLZWCompressor.compress(arrayOfByte, 0, paramInt8);
/*  844 */       paramInt10++;
/*  845 */       paramInt3 += paramInt4;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeRowsOpt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, LZWCompressor paramLZWCompressor, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) throws IOException {
/*  856 */     paramInt1 += paramInt3 * paramInt2;
/*  857 */     paramInt2 *= paramInt4; int i;
/*  858 */     for (i = paramInt3; i < paramInt6; i += paramInt4) {
/*  859 */       if (paramInt7 % paramInt8 == 0) {
/*  860 */         if (abortRequested()) {
/*  861 */           processWriteAborted();
/*      */           return;
/*      */         } 
/*  864 */         processImageProgress(paramInt7 * 100.0F / paramInt6);
/*      */       } 
/*      */       
/*  867 */       paramLZWCompressor.compress(paramArrayOfbyte, paramInt1, paramInt5);
/*  868 */       paramInt7++;
/*  869 */       paramInt1 += paramInt2;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeRasterData(RenderedImage paramRenderedImage, Rectangle paramRectangle, Dimension paramDimension, ImageWriteParam paramImageWriteParam, boolean paramBoolean) throws IOException {
/*  879 */     int i2, i3, i = paramRectangle.x;
/*  880 */     int j = paramRectangle.y;
/*  881 */     int k = paramRectangle.width;
/*  882 */     int m = paramRectangle.height;
/*      */     
/*  884 */     int n = paramDimension.width;
/*  885 */     int i1 = paramDimension.height;
/*      */ 
/*      */ 
/*      */     
/*  889 */     if (paramImageWriteParam == null) {
/*  890 */       i2 = 1;
/*  891 */       i3 = 1;
/*      */     } else {
/*  893 */       i2 = paramImageWriteParam.getSourceXSubsampling();
/*  894 */       i3 = paramImageWriteParam.getSourceYSubsampling();
/*      */     } 
/*      */     
/*  897 */     SampleModel sampleModel = paramRenderedImage.getSampleModel();
/*  898 */     int i4 = sampleModel.getSampleSize()[0];
/*      */     
/*  900 */     int i5 = i4;
/*  901 */     if (i5 == 1) {
/*  902 */       i5++;
/*      */     }
/*  904 */     this.stream.write(i5);
/*      */     
/*  906 */     LZWCompressor lZWCompressor = new LZWCompressor(this.stream, i5, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  921 */     boolean bool = (i2 == 1 && i3 == 1 && paramRenderedImage.getNumXTiles() == 1 && paramRenderedImage.getNumYTiles() == 1 && sampleModel instanceof ComponentSampleModel && paramRenderedImage.getTile(0, 0) instanceof ByteComponentRaster && paramRenderedImage.getTile(0, 0).getDataBuffer() instanceof DataBufferByte) ? true : false;
/*      */     
/*  923 */     int i6 = 0;
/*      */     
/*  925 */     int i7 = Math.max(i1 / 20, 1);
/*      */     
/*  927 */     processImageStarted(this.imageIndex);
/*      */     
/*  929 */     if (paramBoolean) {
/*      */ 
/*      */       
/*  932 */       if (bool)
/*      */       {
/*  934 */         ByteComponentRaster byteComponentRaster = (ByteComponentRaster)paramRenderedImage.getTile(0, 0);
/*  935 */         byte[] arrayOfByte = ((DataBufferByte)byteComponentRaster.getDataBuffer()).getData();
/*      */         
/*  937 */         ComponentSampleModel componentSampleModel = (ComponentSampleModel)byteComponentRaster.getSampleModel();
/*  938 */         int i8 = componentSampleModel.getOffset(i, j, 0);
/*      */         
/*  940 */         i8 += byteComponentRaster.getDataOffset(0);
/*  941 */         int i9 = componentSampleModel.getScanlineStride();
/*      */         
/*  943 */         writeRowsOpt(arrayOfByte, i8, i9, lZWCompressor, 0, 8, n, i1, i6, i7);
/*      */ 
/*      */ 
/*      */         
/*  947 */         if (abortRequested()) {
/*      */           return;
/*      */         }
/*      */         
/*  951 */         i6 += i1 / 8;
/*      */         
/*  953 */         writeRowsOpt(arrayOfByte, i8, i9, lZWCompressor, 4, 8, n, i1, i6, i7);
/*      */ 
/*      */ 
/*      */         
/*  957 */         if (abortRequested()) {
/*      */           return;
/*      */         }
/*      */         
/*  961 */         i6 += (i1 - 4) / 8;
/*      */         
/*  963 */         writeRowsOpt(arrayOfByte, i8, i9, lZWCompressor, 2, 4, n, i1, i6, i7);
/*      */ 
/*      */ 
/*      */         
/*  967 */         if (abortRequested()) {
/*      */           return;
/*      */         }
/*      */         
/*  971 */         i6 += (i1 - 2) / 4;
/*      */         
/*  973 */         writeRowsOpt(arrayOfByte, i8, i9, lZWCompressor, 1, 2, n, i1, i6, i7);
/*      */       }
/*      */       else
/*      */       {
/*  977 */         writeRows(paramRenderedImage, lZWCompressor, i, i2, j, 8 * i3, k, 0, 8, n, i1, i6, i7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  984 */         if (abortRequested()) {
/*      */           return;
/*      */         }
/*      */         
/*  988 */         i6 += i1 / 8;
/*      */         
/*  990 */         writeRows(paramRenderedImage, lZWCompressor, i, i2, j + 4 * i3, 8 * i3, k, 4, 8, n, i1, i6, i7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  996 */         if (abortRequested()) {
/*      */           return;
/*      */         }
/*      */         
/* 1000 */         i6 += (i1 - 4) / 8;
/*      */         
/* 1002 */         writeRows(paramRenderedImage, lZWCompressor, i, i2, j + 2 * i3, 4 * i3, k, 2, 4, n, i1, i6, i7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1008 */         if (abortRequested()) {
/*      */           return;
/*      */         }
/*      */         
/* 1012 */         i6 += (i1 - 2) / 4;
/*      */         
/* 1014 */         writeRows(paramRenderedImage, lZWCompressor, i, i2, j + i3, 2 * i3, k, 1, 2, n, i1, i6, i7);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1023 */     else if (bool) {
/* 1024 */       Raster raster = paramRenderedImage.getTile(0, 0);
/* 1025 */       byte[] arrayOfByte = ((DataBufferByte)raster.getDataBuffer()).getData();
/*      */       
/* 1027 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)raster.getSampleModel();
/* 1028 */       int i8 = componentSampleModel.getOffset(i, j, 0);
/* 1029 */       int i9 = componentSampleModel.getScanlineStride();
/*      */       
/* 1031 */       writeRowsOpt(arrayOfByte, i8, i9, lZWCompressor, 0, 1, n, i1, i6, i7);
/*      */     }
/*      */     else {
/*      */       
/* 1035 */       writeRows(paramRenderedImage, lZWCompressor, i, i2, j, i3, k, 0, 1, n, i1, i6, i7);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1044 */     if (abortRequested()) {
/*      */       return;
/*      */     }
/*      */     
/* 1048 */     processImageProgress(100.0F);
/*      */     
/* 1050 */     lZWCompressor.flush();
/*      */     
/* 1052 */     this.stream.write(0);
/*      */     
/* 1054 */     processImageComplete();
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
/*      */   private void writeHeader(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6, byte[] paramArrayOfbyte) throws IOException {
/*      */     try {
/* 1068 */       this.stream.writeBytes("GIF" + paramString);
/*      */ 
/*      */ 
/*      */       
/* 1072 */       this.stream.writeShort((short)paramInt1);
/*      */ 
/*      */       
/* 1075 */       this.stream.writeShort((short)paramInt2);
/*      */ 
/*      */ 
/*      */       
/* 1079 */       int i = (paramArrayOfbyte != null) ? 128 : 0;
/* 1080 */       i |= (paramInt3 - 1 & 0x7) << 4;
/* 1081 */       if (paramBoolean) {
/* 1082 */         i |= 0x8;
/*      */       }
/* 1084 */       i |= paramInt6 - 1;
/* 1085 */       this.stream.write(i);
/*      */ 
/*      */       
/* 1088 */       this.stream.write(paramInt5);
/*      */ 
/*      */       
/* 1091 */       this.stream.write(paramInt4);
/*      */ 
/*      */       
/* 1094 */       if (paramArrayOfbyte != null) {
/* 1095 */         this.stream.write(paramArrayOfbyte);
/*      */       }
/* 1097 */     } catch (IOException iOException) {
/* 1098 */       throw new IIOException("I/O error writing header!", iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeHeader(IIOMetadata paramIIOMetadata, int paramInt) throws IOException {
/*      */     GIFWritableStreamMetadata gIFWritableStreamMetadata;
/* 1106 */     if (paramIIOMetadata instanceof GIFWritableStreamMetadata) {
/* 1107 */       gIFWritableStreamMetadata = (GIFWritableStreamMetadata)paramIIOMetadata;
/*      */     } else {
/* 1109 */       gIFWritableStreamMetadata = new GIFWritableStreamMetadata();
/*      */       
/* 1111 */       Node node = paramIIOMetadata.getAsTree("javax_imageio_gif_stream_1.0");
/* 1112 */       gIFWritableStreamMetadata.setFromTree("javax_imageio_gif_stream_1.0", node);
/*      */     } 
/*      */     
/* 1115 */     writeHeader(gIFWritableStreamMetadata.version, gIFWritableStreamMetadata.logicalScreenWidth, gIFWritableStreamMetadata.logicalScreenHeight, gIFWritableStreamMetadata.colorResolution, gIFWritableStreamMetadata.pixelAspectRatio, gIFWritableStreamMetadata.backgroundColorIndex, gIFWritableStreamMetadata.sortFlag, paramInt, gIFWritableStreamMetadata.globalColorTable);
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
/*      */   private void writeGraphicControlExtension(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2, int paramInt3) throws IOException {
/*      */     try {
/* 1133 */       this.stream.write(33);
/* 1134 */       this.stream.write(249);
/*      */       
/* 1136 */       this.stream.write(4);
/*      */       
/* 1138 */       int i = (paramInt1 & 0x3) << 2;
/* 1139 */       if (paramBoolean1) {
/* 1140 */         i |= 0x2;
/*      */       }
/* 1142 */       if (paramBoolean2) {
/* 1143 */         i |= 0x1;
/*      */       }
/* 1145 */       this.stream.write(i);
/*      */       
/* 1147 */       this.stream.writeShort((short)paramInt2);
/*      */       
/* 1149 */       this.stream.write(paramInt3);
/* 1150 */       this.stream.write(0);
/* 1151 */     } catch (IOException iOException) {
/* 1152 */       throw new IIOException("I/O error writing Graphic Control Extension!", iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeGraphicControlExtension(GIFWritableImageMetadata paramGIFWritableImageMetadata) throws IOException {
/* 1158 */     writeGraphicControlExtension(paramGIFWritableImageMetadata.disposalMethod, paramGIFWritableImageMetadata.userInputFlag, paramGIFWritableImageMetadata.transparentColorFlag, paramGIFWritableImageMetadata.delayTime, paramGIFWritableImageMetadata.transparentColorIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeBlocks(byte[] paramArrayOfbyte) throws IOException {
/* 1166 */     if (paramArrayOfbyte != null && paramArrayOfbyte.length > 0) {
/* 1167 */       int i = 0;
/* 1168 */       while (i < paramArrayOfbyte.length) {
/* 1169 */         int j = Math.min(paramArrayOfbyte.length - i, 255);
/* 1170 */         this.stream.write(j);
/* 1171 */         this.stream.write(paramArrayOfbyte, i, j);
/* 1172 */         i += j;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writePlainTextExtension(GIFWritableImageMetadata paramGIFWritableImageMetadata) throws IOException {
/* 1179 */     if (paramGIFWritableImageMetadata.hasPlainTextExtension) {
/*      */       try {
/* 1181 */         this.stream.write(33);
/* 1182 */         this.stream.write(1);
/*      */         
/* 1184 */         this.stream.write(12);
/*      */         
/* 1186 */         this.stream.writeShort(paramGIFWritableImageMetadata.textGridLeft);
/* 1187 */         this.stream.writeShort(paramGIFWritableImageMetadata.textGridTop);
/* 1188 */         this.stream.writeShort(paramGIFWritableImageMetadata.textGridWidth);
/* 1189 */         this.stream.writeShort(paramGIFWritableImageMetadata.textGridHeight);
/* 1190 */         this.stream.write(paramGIFWritableImageMetadata.characterCellWidth);
/* 1191 */         this.stream.write(paramGIFWritableImageMetadata.characterCellHeight);
/* 1192 */         this.stream.write(paramGIFWritableImageMetadata.textForegroundColor);
/* 1193 */         this.stream.write(paramGIFWritableImageMetadata.textBackgroundColor);
/*      */         
/* 1195 */         writeBlocks(paramGIFWritableImageMetadata.text);
/*      */         
/* 1197 */         this.stream.write(0);
/* 1198 */       } catch (IOException iOException) {
/* 1199 */         throw new IIOException("I/O error writing Plain Text Extension!", iOException);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeApplicationExtension(GIFWritableImageMetadata paramGIFWritableImageMetadata) throws IOException {
/* 1206 */     if (paramGIFWritableImageMetadata.applicationIDs != null) {
/* 1207 */       Iterator<byte[]> iterator1 = paramGIFWritableImageMetadata.applicationIDs.iterator();
/* 1208 */       Iterator<byte[]> iterator2 = paramGIFWritableImageMetadata.authenticationCodes.iterator();
/* 1209 */       Iterator<byte[]> iterator3 = paramGIFWritableImageMetadata.applicationData.iterator();
/*      */       
/* 1211 */       while (iterator1.hasNext()) {
/*      */         try {
/* 1213 */           this.stream.write(33);
/* 1214 */           this.stream.write(255);
/*      */           
/* 1216 */           this.stream.write(11);
/* 1217 */           this.stream.write(iterator1.next(), 0, 8);
/* 1218 */           this.stream.write(iterator2.next(), 0, 3);
/*      */           
/* 1220 */           writeBlocks(iterator3.next());
/*      */           
/* 1222 */           this.stream.write(0);
/* 1223 */         } catch (IOException iOException) {
/* 1224 */           throw new IIOException("I/O error writing Application Extension!", iOException);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeCommentExtension(GIFWritableImageMetadata paramGIFWritableImageMetadata) throws IOException {
/* 1232 */     if (paramGIFWritableImageMetadata.comments != null) {
/*      */       try {
/* 1234 */         Iterator<byte[]> iterator = paramGIFWritableImageMetadata.comments.iterator();
/* 1235 */         while (iterator.hasNext()) {
/* 1236 */           this.stream.write(33);
/* 1237 */           this.stream.write(254);
/* 1238 */           writeBlocks(iterator.next());
/* 1239 */           this.stream.write(0);
/*      */         } 
/* 1241 */       } catch (IOException iOException) {
/* 1242 */         throw new IIOException("I/O error writing Comment Extension!", iOException);
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
/*      */   private void writeImageDescriptor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, int paramInt5, byte[] paramArrayOfbyte) throws IOException {
/*      */     try {
/* 1258 */       this.stream.write(44);
/*      */       
/* 1260 */       this.stream.writeShort((short)paramInt1);
/* 1261 */       this.stream.writeShort((short)paramInt2);
/* 1262 */       this.stream.writeShort((short)paramInt3);
/* 1263 */       this.stream.writeShort((short)paramInt4);
/*      */       
/* 1265 */       int i = (paramArrayOfbyte != null) ? 128 : 0;
/* 1266 */       if (paramBoolean1) {
/* 1267 */         i |= 0x40;
/*      */       }
/* 1269 */       if (paramBoolean2) {
/* 1270 */         i |= 0x8;
/*      */       }
/* 1272 */       i |= paramInt5 - 1;
/* 1273 */       this.stream.write(i);
/*      */       
/* 1275 */       if (paramArrayOfbyte != null) {
/* 1276 */         this.stream.write(paramArrayOfbyte);
/*      */       }
/* 1278 */     } catch (IOException iOException) {
/* 1279 */       throw new IIOException("I/O error writing Image Descriptor!", iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeImageDescriptor(GIFWritableImageMetadata paramGIFWritableImageMetadata, int paramInt) throws IOException {
/* 1287 */     writeImageDescriptor(paramGIFWritableImageMetadata.imageLeftPosition, paramGIFWritableImageMetadata.imageTopPosition, paramGIFWritableImageMetadata.imageWidth, paramGIFWritableImageMetadata.imageHeight, paramGIFWritableImageMetadata.interlaceFlag, paramGIFWritableImageMetadata.sortFlag, paramInt, paramGIFWritableImageMetadata.localColorTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeTrailer() throws IOException {
/* 1298 */     this.stream.write(59);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFImageWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */