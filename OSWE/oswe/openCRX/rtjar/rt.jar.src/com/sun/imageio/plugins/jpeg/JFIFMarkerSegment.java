/*      */ package com.sun.imageio.plugins.jpeg;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
/*      */ import java.awt.color.ICC_Profile;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ComponentColorModel;
/*      */ import java.awt.image.DataBufferByte;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import javax.imageio.IIOException;
/*      */ import javax.imageio.IIOImage;
/*      */ import javax.imageio.ImageReadParam;
/*      */ import javax.imageio.ImageReader;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.ImageWriteParam;
/*      */ import javax.imageio.event.IIOReadProgressListener;
/*      */ import javax.imageio.metadata.IIOInvalidTreeException;
/*      */ import javax.imageio.metadata.IIOMetadataNode;
/*      */ import javax.imageio.stream.ImageInputStream;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ import javax.imageio.stream.MemoryCacheImageOutputStream;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class JFIFMarkerSegment
/*      */   extends MarkerSegment
/*      */ {
/*      */   int majorVersion;
/*      */   int minorVersion;
/*      */   int resUnits;
/*      */   int Xdensity;
/*      */   int Ydensity;
/*      */   int thumbWidth;
/*      */   int thumbHeight;
/*   78 */   JFIFThumbRGB thumb = null;
/*   79 */   ArrayList extSegments = new ArrayList();
/*   80 */   ICCMarkerSegment iccSegment = null;
/*      */   private static final int THUMB_JPEG = 16;
/*      */   private static final int THUMB_PALETTE = 17;
/*      */   private static final int THUMB_UNASSIGNED = 18;
/*      */   private static final int THUMB_RGB = 19;
/*      */   private static final int DATA_SIZE = 14;
/*      */   private static final int ID_SIZE = 5;
/*   87 */   private final int MAX_THUMB_WIDTH = 255;
/*   88 */   private final int MAX_THUMB_HEIGHT = 255;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean debug = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean inICC = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   private ICCMarkerSegment tempICCSegment = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JFIFMarkerSegment() {
/*  113 */     super(224);
/*  114 */     this.majorVersion = 1;
/*  115 */     this.minorVersion = 2;
/*  116 */     this.resUnits = 0;
/*  117 */     this.Xdensity = 1;
/*  118 */     this.Ydensity = 1;
/*  119 */     this.thumbWidth = 0;
/*  120 */     this.thumbHeight = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JFIFMarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  128 */     super(paramJPEGBuffer);
/*  129 */     paramJPEGBuffer.bufPtr += 5;
/*      */     
/*  131 */     this.majorVersion = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++];
/*  132 */     this.minorVersion = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++];
/*  133 */     this.resUnits = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++];
/*  134 */     this.Xdensity = (paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF) << 8;
/*  135 */     this.Xdensity |= paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  136 */     this.Ydensity = (paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF) << 8;
/*  137 */     this.Ydensity |= paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  138 */     this.thumbWidth = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  139 */     this.thumbHeight = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  140 */     paramJPEGBuffer.bufAvail -= 14;
/*  141 */     if (this.thumbWidth > 0) {
/*  142 */       this.thumb = new JFIFThumbRGB(paramJPEGBuffer, this.thumbWidth, this.thumbHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JFIFMarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/*  150 */     this();
/*  151 */     updateFromNativeNode(paramNode, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object clone() {
/*  158 */     JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)super.clone();
/*  159 */     if (!this.extSegments.isEmpty()) {
/*  160 */       jFIFMarkerSegment.extSegments = new ArrayList();
/*  161 */       for (JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment : this.extSegments)
/*      */       {
/*      */         
/*  164 */         jFIFMarkerSegment.extSegments.add(jFIFExtensionMarkerSegment.clone());
/*      */       }
/*      */     } 
/*  167 */     if (this.iccSegment != null) {
/*  168 */       jFIFMarkerSegment.iccSegment = (ICCMarkerSegment)this.iccSegment.clone();
/*      */     }
/*  170 */     return jFIFMarkerSegment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addJFXX(JPEGBuffer paramJPEGBuffer, JPEGImageReader paramJPEGImageReader) throws IOException {
/*  179 */     this.extSegments.add(new JFIFExtensionMarkerSegment(paramJPEGBuffer, paramJPEGImageReader));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addICC(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  187 */     if (!this.inICC) {
/*  188 */       if (this.iccSegment != null) {
/*  189 */         throw new IIOException("> 1 ICC APP2 Marker Segment not supported");
/*      */       }
/*      */       
/*  192 */       this.tempICCSegment = new ICCMarkerSegment(paramJPEGBuffer);
/*  193 */       if (!this.inICC) {
/*  194 */         this.iccSegment = this.tempICCSegment;
/*  195 */         this.tempICCSegment = null;
/*      */       }
/*      */     
/*  198 */     } else if (this.tempICCSegment.addData(paramJPEGBuffer) == true) {
/*  199 */       this.iccSegment = this.tempICCSegment;
/*  200 */       this.tempICCSegment = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addICC(ICC_ColorSpace paramICC_ColorSpace) throws IOException {
/*  210 */     if (this.iccSegment != null) {
/*  211 */       throw new IIOException("> 1 ICC APP2 Marker Segment not supported");
/*      */     }
/*      */     
/*  214 */     this.iccSegment = new ICCMarkerSegment(paramICC_ColorSpace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   IIOMetadataNode getNativeNode() {
/*  222 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("app0JFIF");
/*  223 */     iIOMetadataNode.setAttribute("majorVersion", Integer.toString(this.majorVersion));
/*  224 */     iIOMetadataNode.setAttribute("minorVersion", Integer.toString(this.minorVersion));
/*  225 */     iIOMetadataNode.setAttribute("resUnits", Integer.toString(this.resUnits));
/*  226 */     iIOMetadataNode.setAttribute("Xdensity", Integer.toString(this.Xdensity));
/*  227 */     iIOMetadataNode.setAttribute("Ydensity", Integer.toString(this.Ydensity));
/*  228 */     iIOMetadataNode.setAttribute("thumbWidth", Integer.toString(this.thumbWidth));
/*  229 */     iIOMetadataNode.setAttribute("thumbHeight", Integer.toString(this.thumbHeight));
/*  230 */     if (!this.extSegments.isEmpty()) {
/*  231 */       IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("JFXX");
/*  232 */       iIOMetadataNode.appendChild(iIOMetadataNode1);
/*  233 */       for (JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment : this.extSegments)
/*      */       {
/*      */         
/*  236 */         iIOMetadataNode1.appendChild(jFIFExtensionMarkerSegment.getNativeNode());
/*      */       }
/*      */     } 
/*  239 */     if (this.iccSegment != null) {
/*  240 */       iIOMetadataNode.appendChild(this.iccSegment.getNativeNode());
/*      */     }
/*      */     
/*  243 */     return iIOMetadataNode;
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
/*      */   void updateFromNativeNode(Node paramNode, boolean paramBoolean) throws IIOInvalidTreeException {
/*  256 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/*  257 */     if (namedNodeMap.getLength() > 0) {
/*  258 */       int i = getAttributeValue(paramNode, namedNodeMap, "majorVersion", 0, 255, false);
/*      */       
/*  260 */       this.majorVersion = (i != -1) ? i : this.majorVersion;
/*  261 */       i = getAttributeValue(paramNode, namedNodeMap, "minorVersion", 0, 255, false);
/*      */       
/*  263 */       this.minorVersion = (i != -1) ? i : this.minorVersion;
/*  264 */       i = getAttributeValue(paramNode, namedNodeMap, "resUnits", 0, 2, false);
/*  265 */       this.resUnits = (i != -1) ? i : this.resUnits;
/*  266 */       i = getAttributeValue(paramNode, namedNodeMap, "Xdensity", 1, 65535, false);
/*  267 */       this.Xdensity = (i != -1) ? i : this.Xdensity;
/*  268 */       i = getAttributeValue(paramNode, namedNodeMap, "Ydensity", 1, 65535, false);
/*  269 */       this.Ydensity = (i != -1) ? i : this.Ydensity;
/*  270 */       i = getAttributeValue(paramNode, namedNodeMap, "thumbWidth", 0, 255, false);
/*  271 */       this.thumbWidth = (i != -1) ? i : this.thumbWidth;
/*  272 */       i = getAttributeValue(paramNode, namedNodeMap, "thumbHeight", 0, 255, false);
/*  273 */       this.thumbHeight = (i != -1) ? i : this.thumbHeight;
/*      */     } 
/*  275 */     if (paramNode.hasChildNodes()) {
/*  276 */       NodeList nodeList = paramNode.getChildNodes();
/*  277 */       int i = nodeList.getLength();
/*  278 */       if (i > 2) {
/*  279 */         throw new IIOInvalidTreeException("app0JFIF node cannot have > 2 children", paramNode);
/*      */       }
/*      */       
/*  282 */       for (byte b = 0; b < i; b++) {
/*  283 */         Node node = nodeList.item(b);
/*  284 */         String str = node.getNodeName();
/*  285 */         if (str.equals("JFXX")) {
/*  286 */           if (!this.extSegments.isEmpty() && paramBoolean) {
/*  287 */             throw new IIOInvalidTreeException("app0JFIF node cannot have > 1 JFXX node", paramNode);
/*      */           }
/*      */           
/*  290 */           NodeList nodeList1 = node.getChildNodes();
/*  291 */           int j = nodeList1.getLength();
/*  292 */           for (byte b1 = 0; b1 < j; b1++) {
/*  293 */             Node node1 = nodeList1.item(b1);
/*  294 */             this.extSegments.add(new JFIFExtensionMarkerSegment(node1));
/*      */           } 
/*      */         } 
/*  297 */         if (str.equals("app2ICC")) {
/*  298 */           if (this.iccSegment != null && paramBoolean) {
/*  299 */             throw new IIOInvalidTreeException("> 1 ICC APP2 Marker Segment not supported", paramNode);
/*      */           }
/*      */           
/*  302 */           this.iccSegment = new ICCMarkerSegment(node);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   int getThumbnailWidth(int paramInt) {
/*  309 */     if (this.thumb != null) {
/*  310 */       if (paramInt == 0) {
/*  311 */         return this.thumb.getWidth();
/*      */       }
/*  313 */       paramInt--;
/*      */     } 
/*      */     
/*  316 */     JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment = this.extSegments.get(paramInt);
/*  317 */     return jFIFExtensionMarkerSegment.thumb.getWidth();
/*      */   }
/*      */   
/*      */   int getThumbnailHeight(int paramInt) {
/*  321 */     if (this.thumb != null) {
/*  322 */       if (paramInt == 0) {
/*  323 */         return this.thumb.getHeight();
/*      */       }
/*  325 */       paramInt--;
/*      */     } 
/*      */     
/*  328 */     JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment = this.extSegments.get(paramInt);
/*  329 */     return jFIFExtensionMarkerSegment.thumb.getHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   BufferedImage getThumbnail(ImageInputStream paramImageInputStream, int paramInt, JPEGImageReader paramJPEGImageReader) throws IOException {
/*  335 */     paramJPEGImageReader.thumbnailStarted(paramInt);
/*  336 */     BufferedImage bufferedImage = null;
/*  337 */     if (this.thumb != null && paramInt == 0) {
/*  338 */       bufferedImage = this.thumb.getThumbnail(paramImageInputStream, paramJPEGImageReader);
/*      */     } else {
/*  340 */       if (this.thumb != null) {
/*  341 */         paramInt--;
/*      */       }
/*      */       
/*  344 */       JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment = this.extSegments.get(paramInt);
/*  345 */       bufferedImage = jFIFExtensionMarkerSegment.thumb.getThumbnail(paramImageInputStream, paramJPEGImageReader);
/*      */     } 
/*  347 */     paramJPEGImageReader.thumbnailComplete();
/*  348 */     return bufferedImage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void write(ImageOutputStream paramImageOutputStream, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/*  359 */     write(paramImageOutputStream, (BufferedImage)null, paramJPEGImageWriter);
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
/*      */   void write(ImageOutputStream paramImageOutputStream, BufferedImage paramBufferedImage, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/*  372 */     int i = 0;
/*  373 */     int j = 0;
/*  374 */     int k = 0;
/*  375 */     int[] arrayOfInt = null;
/*  376 */     if (paramBufferedImage != null) {
/*      */       
/*  378 */       i = paramBufferedImage.getWidth();
/*  379 */       j = paramBufferedImage.getHeight();
/*  380 */       if (i > 255 || j > 255)
/*      */       {
/*  382 */         paramJPEGImageWriter.warningOccurred(12);
/*      */       }
/*  384 */       i = Math.min(i, 255);
/*  385 */       j = Math.min(j, 255);
/*  386 */       arrayOfInt = paramBufferedImage.getRaster().getPixels(0, 0, i, j, (int[])null);
/*      */ 
/*      */       
/*  389 */       k = arrayOfInt.length;
/*      */     } 
/*  391 */     this.length = 16 + k;
/*  392 */     writeTag(paramImageOutputStream);
/*  393 */     byte[] arrayOfByte = { 74, 70, 73, 70, 0 };
/*  394 */     paramImageOutputStream.write(arrayOfByte);
/*  395 */     paramImageOutputStream.write(this.majorVersion);
/*  396 */     paramImageOutputStream.write(this.minorVersion);
/*  397 */     paramImageOutputStream.write(this.resUnits);
/*  398 */     write2bytes(paramImageOutputStream, this.Xdensity);
/*  399 */     write2bytes(paramImageOutputStream, this.Ydensity);
/*  400 */     paramImageOutputStream.write(i);
/*  401 */     paramImageOutputStream.write(j);
/*  402 */     if (arrayOfInt != null) {
/*  403 */       paramJPEGImageWriter.thumbnailStarted(0);
/*  404 */       writeThumbnailData(paramImageOutputStream, arrayOfInt, paramJPEGImageWriter);
/*  405 */       paramJPEGImageWriter.thumbnailComplete();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void writeThumbnailData(ImageOutputStream paramImageOutputStream, int[] paramArrayOfint, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/*  416 */     int i = paramArrayOfint.length / 20;
/*  417 */     if (i == 0) {
/*  418 */       i = 1;
/*      */     }
/*  420 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/*  421 */       paramImageOutputStream.write(paramArrayOfint[b]);
/*  422 */       if (b > i && b % i == 0) {
/*  423 */         paramJPEGImageWriter
/*  424 */           .thumbnailProgress(b * 100.0F / paramArrayOfint.length);
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
/*      */   void writeWithThumbs(ImageOutputStream paramImageOutputStream, List<BufferedImage> paramList, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/*  441 */     if (paramList != null) {
/*  442 */       JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment = null;
/*  443 */       if (paramList.size() == 1) {
/*  444 */         if (!this.extSegments.isEmpty()) {
/*  445 */           jFIFExtensionMarkerSegment = this.extSegments.get(0);
/*      */         }
/*  447 */         writeThumb(paramImageOutputStream, paramList
/*  448 */             .get(0), jFIFExtensionMarkerSegment, 0, true, paramJPEGImageWriter);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  455 */         write(paramImageOutputStream, paramJPEGImageWriter);
/*  456 */         for (byte b = 0; b < paramList.size(); b++) {
/*  457 */           jFIFExtensionMarkerSegment = null;
/*  458 */           if (b < this.extSegments.size()) {
/*  459 */             jFIFExtensionMarkerSegment = this.extSegments.get(b);
/*      */           }
/*  461 */           writeThumb(paramImageOutputStream, paramList
/*  462 */               .get(b), jFIFExtensionMarkerSegment, b, false, paramJPEGImageWriter);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  470 */       write(paramImageOutputStream, paramJPEGImageWriter);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeThumb(ImageOutputStream paramImageOutputStream, BufferedImage paramBufferedImage, JFIFExtensionMarkerSegment paramJFIFExtensionMarkerSegment, int paramInt, boolean paramBoolean, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/*  481 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*  482 */     ColorSpace colorSpace = colorModel.getColorSpace();
/*      */     
/*  484 */     if (colorModel instanceof IndexColorModel) {
/*      */ 
/*      */       
/*  487 */       if (paramBoolean) {
/*  488 */         write(paramImageOutputStream, paramJPEGImageWriter);
/*      */       }
/*  490 */       if (paramJFIFExtensionMarkerSegment == null || paramJFIFExtensionMarkerSegment.code == 17) {
/*      */         
/*  492 */         writeJFXXSegment(paramInt, paramBufferedImage, paramImageOutputStream, paramJPEGImageWriter);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  497 */         BufferedImage bufferedImage = ((IndexColorModel)colorModel).convertToIntDiscrete(paramBufferedImage.getRaster(), false);
/*  498 */         paramJFIFExtensionMarkerSegment.setThumbnail(bufferedImage);
/*  499 */         paramJPEGImageWriter.thumbnailStarted(paramInt);
/*  500 */         paramJFIFExtensionMarkerSegment.write(paramImageOutputStream, paramJPEGImageWriter);
/*  501 */         paramJPEGImageWriter.thumbnailComplete();
/*      */       } 
/*  503 */     } else if (colorSpace.getType() == 5) {
/*  504 */       if (paramJFIFExtensionMarkerSegment == null) {
/*  505 */         if (paramBoolean) {
/*  506 */           write(paramImageOutputStream, paramBufferedImage, paramJPEGImageWriter);
/*      */         } else {
/*  508 */           writeJFXXSegment(paramInt, paramBufferedImage, paramImageOutputStream, paramJPEGImageWriter);
/*      */         } 
/*      */       } else {
/*      */         
/*  512 */         if (paramBoolean) {
/*  513 */           write(paramImageOutputStream, paramJPEGImageWriter);
/*      */         }
/*  515 */         if (paramJFIFExtensionMarkerSegment.code == 17) {
/*  516 */           writeJFXXSegment(paramInt, paramBufferedImage, paramImageOutputStream, paramJPEGImageWriter);
/*  517 */           paramJPEGImageWriter
/*  518 */             .warningOccurred(14);
/*      */         } else {
/*  520 */           paramJFIFExtensionMarkerSegment.setThumbnail(paramBufferedImage);
/*  521 */           paramJPEGImageWriter.thumbnailStarted(paramInt);
/*  522 */           paramJFIFExtensionMarkerSegment.write(paramImageOutputStream, paramJPEGImageWriter);
/*  523 */           paramJPEGImageWriter.thumbnailComplete();
/*      */         } 
/*      */       } 
/*  526 */     } else if (colorSpace.getType() == 6) {
/*  527 */       if (paramJFIFExtensionMarkerSegment == null) {
/*  528 */         if (paramBoolean) {
/*  529 */           BufferedImage bufferedImage = expandGrayThumb(paramBufferedImage);
/*  530 */           write(paramImageOutputStream, bufferedImage, paramJPEGImageWriter);
/*      */         } else {
/*  532 */           writeJFXXSegment(paramInt, paramBufferedImage, paramImageOutputStream, paramJPEGImageWriter);
/*      */         } 
/*      */       } else {
/*      */         
/*  536 */         if (paramBoolean) {
/*  537 */           write(paramImageOutputStream, paramJPEGImageWriter);
/*      */         }
/*  539 */         if (paramJFIFExtensionMarkerSegment.code == 19) {
/*  540 */           BufferedImage bufferedImage = expandGrayThumb(paramBufferedImage);
/*  541 */           writeJFXXSegment(paramInt, bufferedImage, paramImageOutputStream, paramJPEGImageWriter);
/*  542 */         } else if (paramJFIFExtensionMarkerSegment.code == 16) {
/*  543 */           paramJFIFExtensionMarkerSegment.setThumbnail(paramBufferedImage);
/*  544 */           paramJPEGImageWriter.thumbnailStarted(paramInt);
/*  545 */           paramJFIFExtensionMarkerSegment.write(paramImageOutputStream, paramJPEGImageWriter);
/*  546 */           paramJPEGImageWriter.thumbnailComplete();
/*  547 */         } else if (paramJFIFExtensionMarkerSegment.code == 17) {
/*  548 */           writeJFXXSegment(paramInt, paramBufferedImage, paramImageOutputStream, paramJPEGImageWriter);
/*  549 */           paramJPEGImageWriter
/*  550 */             .warningOccurred(15);
/*      */         } 
/*      */       } 
/*      */     } else {
/*  554 */       paramJPEGImageWriter
/*  555 */         .warningOccurred(9);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class IllegalThumbException
/*      */     extends Exception
/*      */   {
/*      */     private IllegalThumbException() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeJFXXSegment(int paramInt, BufferedImage paramBufferedImage, ImageOutputStream paramImageOutputStream, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/*  570 */     JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment = null;
/*      */     try {
/*  572 */       jFIFExtensionMarkerSegment = new JFIFExtensionMarkerSegment(paramBufferedImage);
/*  573 */     } catch (IllegalThumbException illegalThumbException) {
/*  574 */       paramJPEGImageWriter
/*  575 */         .warningOccurred(9);
/*      */       return;
/*      */     } 
/*  578 */     paramJPEGImageWriter.thumbnailStarted(paramInt);
/*  579 */     jFIFExtensionMarkerSegment.write(paramImageOutputStream, paramJPEGImageWriter);
/*  580 */     paramJPEGImageWriter.thumbnailComplete();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BufferedImage expandGrayThumb(BufferedImage paramBufferedImage) {
/*  590 */     BufferedImage bufferedImage = new BufferedImage(paramBufferedImage.getWidth(), paramBufferedImage.getHeight(), 1);
/*      */     
/*  592 */     Graphics graphics = bufferedImage.getGraphics();
/*  593 */     graphics.drawImage(paramBufferedImage, 0, 0, null);
/*  594 */     return bufferedImage;
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
/*      */   static void writeDefaultJFIF(ImageOutputStream paramImageOutputStream, List paramList, ICC_Profile paramICC_Profile, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/*  612 */     JFIFMarkerSegment jFIFMarkerSegment = new JFIFMarkerSegment();
/*  613 */     jFIFMarkerSegment.writeWithThumbs(paramImageOutputStream, paramList, paramJPEGImageWriter);
/*  614 */     if (paramICC_Profile != null) {
/*  615 */       writeICC(paramICC_Profile, paramImageOutputStream);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void print() {
/*  623 */     printTag("JFIF");
/*  624 */     System.out.print("Version ");
/*  625 */     System.out.print(this.majorVersion);
/*  626 */     System.out.println(".0" + 
/*  627 */         Integer.toString(this.minorVersion));
/*  628 */     System.out.print("Resolution units: ");
/*  629 */     System.out.println(this.resUnits);
/*  630 */     System.out.print("X density: ");
/*  631 */     System.out.println(this.Xdensity);
/*  632 */     System.out.print("Y density: ");
/*  633 */     System.out.println(this.Ydensity);
/*  634 */     System.out.print("Thumbnail Width: ");
/*  635 */     System.out.println(this.thumbWidth);
/*  636 */     System.out.print("Thumbnail Height: ");
/*  637 */     System.out.println(this.thumbHeight);
/*  638 */     if (!this.extSegments.isEmpty()) {
/*  639 */       for (JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment : this.extSegments)
/*      */       {
/*      */         
/*  642 */         jFIFExtensionMarkerSegment.print();
/*      */       }
/*      */     }
/*  645 */     if (this.iccSegment != null) {
/*  646 */       this.iccSegment.print();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class JFIFExtensionMarkerSegment
/*      */     extends MarkerSegment
/*      */   {
/*      */     int code;
/*      */     
/*      */     JFIFMarkerSegment.JFIFThumb thumb;
/*      */     
/*      */     private static final int DATA_SIZE = 6;
/*      */     private static final int ID_SIZE = 5;
/*      */     
/*      */     JFIFExtensionMarkerSegment(JPEGBuffer param1JPEGBuffer, JPEGImageReader param1JPEGImageReader) throws IOException {
/*  662 */       super(param1JPEGBuffer);
/*  663 */       param1JPEGBuffer.bufPtr += 5;
/*      */       
/*  665 */       this.code = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xFF;
/*  666 */       param1JPEGBuffer.bufAvail -= 6;
/*  667 */       if (this.code == 16) {
/*  668 */         this.thumb = new JFIFMarkerSegment.JFIFThumbJPEG(param1JPEGBuffer, this.length, param1JPEGImageReader);
/*      */       } else {
/*  670 */         param1JPEGBuffer.loadBuf(2);
/*  671 */         int i = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xFF;
/*  672 */         int j = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xFF;
/*  673 */         param1JPEGBuffer.bufAvail -= 2;
/*      */         
/*  675 */         if (this.code == 17) {
/*  676 */           this.thumb = new JFIFMarkerSegment.JFIFThumbPalette(param1JPEGBuffer, i, j);
/*      */         } else {
/*  678 */           this.thumb = new JFIFMarkerSegment.JFIFThumbRGB(param1JPEGBuffer, i, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     JFIFExtensionMarkerSegment(Node param1Node) throws IIOInvalidTreeException {
/*  684 */       super(224);
/*  685 */       NamedNodeMap namedNodeMap = param1Node.getAttributes();
/*  686 */       if (namedNodeMap.getLength() > 0) {
/*  687 */         this.code = getAttributeValue(param1Node, namedNodeMap, "extensionCode", 16, 19, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  693 */         if (this.code == 18) {
/*  694 */           throw new IIOInvalidTreeException("invalid extensionCode attribute value", param1Node);
/*      */         }
/*      */       } else {
/*      */         
/*  698 */         this.code = 18;
/*      */       } 
/*      */       
/*  701 */       if (param1Node.getChildNodes().getLength() != 1) {
/*  702 */         throw new IIOInvalidTreeException("app0JFXX node must have exactly 1 child", param1Node);
/*      */       }
/*      */       
/*  705 */       Node node = param1Node.getFirstChild();
/*  706 */       String str = node.getNodeName();
/*  707 */       if (str.equals("JFIFthumbJPEG")) {
/*  708 */         if (this.code == 18) {
/*  709 */           this.code = 16;
/*      */         }
/*  711 */         this.thumb = new JFIFMarkerSegment.JFIFThumbJPEG(node);
/*  712 */       } else if (str.equals("JFIFthumbPalette")) {
/*  713 */         if (this.code == 18) {
/*  714 */           this.code = 17;
/*      */         }
/*  716 */         this.thumb = new JFIFMarkerSegment.JFIFThumbPalette(node);
/*  717 */       } else if (str.equals("JFIFthumbRGB")) {
/*  718 */         if (this.code == 18) {
/*  719 */           this.code = 19;
/*      */         }
/*  721 */         this.thumb = new JFIFMarkerSegment.JFIFThumbRGB(node);
/*      */       } else {
/*  723 */         throw new IIOInvalidTreeException("unrecognized app0JFXX child node", param1Node);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     JFIFExtensionMarkerSegment(BufferedImage param1BufferedImage) throws JFIFMarkerSegment.IllegalThumbException {
/*  731 */       super(224);
/*  732 */       ColorModel colorModel = param1BufferedImage.getColorModel();
/*  733 */       int i = colorModel.getColorSpace().getType();
/*  734 */       if (colorModel.hasAlpha()) {
/*  735 */         throw new JFIFMarkerSegment.IllegalThumbException();
/*      */       }
/*  737 */       if (colorModel instanceof IndexColorModel) {
/*  738 */         this.code = 17;
/*  739 */         this.thumb = new JFIFMarkerSegment.JFIFThumbPalette(param1BufferedImage);
/*  740 */       } else if (i == 5) {
/*  741 */         this.code = 19;
/*  742 */         this.thumb = new JFIFMarkerSegment.JFIFThumbRGB(param1BufferedImage);
/*  743 */       } else if (i == 6) {
/*  744 */         this.code = 16;
/*  745 */         this.thumb = new JFIFMarkerSegment.JFIFThumbJPEG(param1BufferedImage);
/*      */       } else {
/*  747 */         throw new JFIFMarkerSegment.IllegalThumbException();
/*      */       } 
/*      */     }
/*      */     
/*      */     void setThumbnail(BufferedImage param1BufferedImage) {
/*      */       try {
/*  753 */         switch (this.code) {
/*      */           case 17:
/*  755 */             this.thumb = new JFIFMarkerSegment.JFIFThumbPalette(param1BufferedImage);
/*      */             break;
/*      */           case 19:
/*  758 */             this.thumb = new JFIFMarkerSegment.JFIFThumbRGB(param1BufferedImage);
/*      */             break;
/*      */           case 16:
/*  761 */             this.thumb = new JFIFMarkerSegment.JFIFThumbJPEG(param1BufferedImage);
/*      */             break;
/*      */         } 
/*  764 */       } catch (IllegalThumbException illegalThumbException) {
/*      */         
/*  766 */         throw new InternalError("Illegal thumb in setThumbnail!", illegalThumbException);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected Object clone() {
/*  772 */       JFIFExtensionMarkerSegment jFIFExtensionMarkerSegment = (JFIFExtensionMarkerSegment)super.clone();
/*  773 */       if (this.thumb != null) {
/*  774 */         jFIFExtensionMarkerSegment.thumb = (JFIFMarkerSegment.JFIFThumb)this.thumb.clone();
/*      */       }
/*  776 */       return jFIFExtensionMarkerSegment;
/*      */     }
/*      */     
/*      */     IIOMetadataNode getNativeNode() {
/*  780 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("app0JFXX");
/*  781 */       iIOMetadataNode.setAttribute("extensionCode", Integer.toString(this.code));
/*  782 */       iIOMetadataNode.appendChild(this.thumb.getNativeNode());
/*  783 */       return iIOMetadataNode;
/*      */     }
/*      */ 
/*      */     
/*      */     void write(ImageOutputStream param1ImageOutputStream, JPEGImageWriter param1JPEGImageWriter) throws IOException {
/*  788 */       this.length = 8 + this.thumb.getLength();
/*  789 */       writeTag(param1ImageOutputStream);
/*  790 */       byte[] arrayOfByte = { 74, 70, 88, 88, 0 };
/*  791 */       param1ImageOutputStream.write(arrayOfByte);
/*  792 */       param1ImageOutputStream.write(this.code);
/*  793 */       this.thumb.write(param1ImageOutputStream, param1JPEGImageWriter);
/*      */     }
/*      */     
/*      */     void print() {
/*  797 */       printTag("JFXX");
/*  798 */       this.thumb.print();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   abstract class JFIFThumb
/*      */     implements Cloneable
/*      */   {
/*  807 */     long streamPos = -1L;
/*      */     abstract int getLength();
/*      */     
/*      */     abstract int getWidth();
/*      */     
/*      */     abstract int getHeight();
/*      */     
/*      */     abstract BufferedImage getThumbnail(ImageInputStream param1ImageInputStream, JPEGImageReader param1JPEGImageReader) throws IOException;
/*      */     
/*      */     protected JFIFThumb() {}
/*      */     
/*      */     protected JFIFThumb(JPEGBuffer param1JPEGBuffer) throws IOException {
/*  819 */       this.streamPos = param1JPEGBuffer.getStreamPosition();
/*      */     }
/*      */ 
/*      */     
/*      */     abstract void print();
/*      */     
/*      */     abstract IIOMetadataNode getNativeNode();
/*      */     
/*      */     abstract void write(ImageOutputStream param1ImageOutputStream, JPEGImageWriter param1JPEGImageWriter) throws IOException;
/*      */     
/*      */     protected Object clone() {
/*      */       try {
/*  831 */         return super.clone();
/*  832 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  833 */         return null;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   abstract class JFIFThumbUncompressed extends JFIFThumb {
/*  839 */     BufferedImage thumbnail = null;
/*      */ 
/*      */     
/*      */     int thumbWidth;
/*      */     
/*      */     int thumbHeight;
/*      */     
/*      */     String name;
/*      */ 
/*      */     
/*      */     JFIFThumbUncompressed(JPEGBuffer param1JPEGBuffer, int param1Int1, int param1Int2, int param1Int3, String param1String) throws IOException {
/*  850 */       super(param1JPEGBuffer);
/*  851 */       this.thumbWidth = param1Int1;
/*  852 */       this.thumbHeight = param1Int2;
/*      */       
/*  854 */       param1JPEGBuffer.skipData(param1Int3);
/*  855 */       this.name = param1String;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     JFIFThumbUncompressed(Node param1Node, String param1String) throws IIOInvalidTreeException {
/*  861 */       this.thumbWidth = 0;
/*  862 */       this.thumbHeight = 0;
/*  863 */       this.name = param1String;
/*  864 */       NamedNodeMap namedNodeMap = param1Node.getAttributes();
/*  865 */       int i = namedNodeMap.getLength();
/*  866 */       if (i > 2) {
/*  867 */         throw new IIOInvalidTreeException(param1String + " node cannot have > 2 attributes", param1Node);
/*      */       }
/*      */       
/*  870 */       if (i != 0) {
/*  871 */         int j = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "thumbWidth", 0, 255, false);
/*      */         
/*  873 */         this.thumbWidth = (j != -1) ? j : this.thumbWidth;
/*  874 */         j = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "thumbHeight", 0, 255, false);
/*      */         
/*  876 */         this.thumbHeight = (j != -1) ? j : this.thumbHeight;
/*      */       } 
/*      */     }
/*      */     
/*      */     JFIFThumbUncompressed(BufferedImage param1BufferedImage) {
/*  881 */       this.thumbnail = param1BufferedImage;
/*  882 */       this.thumbWidth = param1BufferedImage.getWidth();
/*  883 */       this.thumbHeight = param1BufferedImage.getHeight();
/*  884 */       this.name = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void readByteBuffer(ImageInputStream param1ImageInputStream, byte[] param1ArrayOfbyte, JPEGImageReader param1JPEGImageReader, float param1Float1, float param1Float2) throws IOException {
/*  892 */       int i = Math.max((int)((param1ArrayOfbyte.length / 20) / param1Float1), 1);
/*      */       
/*  894 */       int j = 0;
/*  895 */       while (j < param1ArrayOfbyte.length) {
/*  896 */         int k = Math.min(i, param1ArrayOfbyte.length - j);
/*  897 */         param1ImageInputStream.read(param1ArrayOfbyte, j, k);
/*  898 */         j += i;
/*  899 */         float f = j * 100.0F / param1ArrayOfbyte.length * param1Float1 + param1Float2;
/*      */ 
/*      */         
/*  902 */         if (f > 100.0F) {
/*  903 */           f = 100.0F;
/*      */         }
/*  905 */         param1JPEGImageReader.thumbnailProgress(f);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int getWidth() {
/*  911 */       return this.thumbWidth;
/*      */     }
/*      */     
/*      */     int getHeight() {
/*  915 */       return this.thumbHeight;
/*      */     }
/*      */     
/*      */     IIOMetadataNode getNativeNode() {
/*  919 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode(this.name);
/*  920 */       iIOMetadataNode.setAttribute("thumbWidth", Integer.toString(this.thumbWidth));
/*  921 */       iIOMetadataNode.setAttribute("thumbHeight", Integer.toString(this.thumbHeight));
/*  922 */       return iIOMetadataNode;
/*      */     }
/*      */ 
/*      */     
/*      */     void write(ImageOutputStream param1ImageOutputStream, JPEGImageWriter param1JPEGImageWriter) throws IOException {
/*  927 */       if (this.thumbWidth > 255 || this.thumbHeight > 255)
/*      */       {
/*  929 */         param1JPEGImageWriter.warningOccurred(12);
/*      */       }
/*  931 */       this.thumbWidth = Math.min(this.thumbWidth, 255);
/*  932 */       this.thumbHeight = Math.min(this.thumbHeight, 255);
/*  933 */       param1ImageOutputStream.write(this.thumbWidth);
/*  934 */       param1ImageOutputStream.write(this.thumbHeight);
/*      */     }
/*      */ 
/*      */     
/*      */     void writePixels(ImageOutputStream param1ImageOutputStream, JPEGImageWriter param1JPEGImageWriter) throws IOException {
/*  939 */       if (this.thumbWidth > 255 || this.thumbHeight > 255)
/*      */       {
/*  941 */         param1JPEGImageWriter.warningOccurred(12);
/*      */       }
/*  943 */       this.thumbWidth = Math.min(this.thumbWidth, 255);
/*  944 */       this.thumbHeight = Math.min(this.thumbHeight, 255);
/*  945 */       int[] arrayOfInt = this.thumbnail.getRaster().getPixels(0, 0, this.thumbWidth, this.thumbHeight, (int[])null);
/*      */ 
/*      */ 
/*      */       
/*  949 */       JFIFMarkerSegment.this.writeThumbnailData(param1ImageOutputStream, arrayOfInt, param1JPEGImageWriter);
/*      */     }
/*      */     
/*      */     void print() {
/*  953 */       System.out.print(this.name + " width: ");
/*  954 */       System.out.println(this.thumbWidth);
/*  955 */       System.out.print(this.name + " height: ");
/*  956 */       System.out.println(this.thumbHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class JFIFThumbRGB
/*      */     extends JFIFThumbUncompressed
/*      */   {
/*      */     JFIFThumbRGB(JPEGBuffer param1JPEGBuffer, int param1Int1, int param1Int2) throws IOException {
/*  970 */       super(param1JPEGBuffer, param1Int1, param1Int2, param1Int1 * param1Int2 * 3, "JFIFthumbRGB");
/*      */     }
/*      */     
/*      */     JFIFThumbRGB(Node param1Node) throws IIOInvalidTreeException {
/*  974 */       super(param1Node, "JFIFthumbRGB");
/*      */     }
/*      */     
/*      */     JFIFThumbRGB(BufferedImage param1BufferedImage) throws JFIFMarkerSegment.IllegalThumbException {
/*  978 */       super(param1BufferedImage);
/*      */     }
/*      */     
/*      */     int getLength() {
/*  982 */       return this.thumbWidth * this.thumbHeight * 3;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     BufferedImage getThumbnail(ImageInputStream param1ImageInputStream, JPEGImageReader param1JPEGImageReader) throws IOException {
/*  988 */       param1ImageInputStream.mark();
/*  989 */       param1ImageInputStream.seek(this.streamPos);
/*  990 */       DataBufferByte dataBufferByte = new DataBufferByte(getLength());
/*  991 */       readByteBuffer(param1ImageInputStream, dataBufferByte
/*  992 */           .getData(), param1JPEGImageReader, 1.0F, 0.0F);
/*      */ 
/*      */ 
/*      */       
/*  996 */       param1ImageInputStream.reset();
/*      */ 
/*      */       
/*  999 */       WritableRaster writableRaster = Raster.createInterleavedRaster(dataBufferByte, this.thumbWidth, this.thumbHeight, this.thumbWidth * 3, 3, new int[] { 0, 1, 2 }, (Point)null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1006 */       ComponentColorModel componentColorModel = new ComponentColorModel(JPEG.JCS.sRGB, false, false, 1, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1011 */       return new BufferedImage(componentColorModel, writableRaster, false, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void write(ImageOutputStream param1ImageOutputStream, JPEGImageWriter param1JPEGImageWriter) throws IOException {
/* 1019 */       super.write(param1ImageOutputStream, param1JPEGImageWriter);
/* 1020 */       writePixels(param1ImageOutputStream, param1JPEGImageWriter);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class JFIFThumbPalette
/*      */     extends JFIFThumbUncompressed
/*      */   {
/*      */     private static final int PALETTE_SIZE = 768;
/*      */ 
/*      */ 
/*      */     
/*      */     JFIFThumbPalette(JPEGBuffer param1JPEGBuffer, int param1Int1, int param1Int2) throws IOException {
/* 1034 */       super(param1JPEGBuffer, param1Int1, param1Int2, 768 + param1Int1 * param1Int2, "JFIFThumbPalette");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     JFIFThumbPalette(Node param1Node) throws IIOInvalidTreeException {
/* 1042 */       super(param1Node, "JFIFThumbPalette");
/*      */     }
/*      */     
/*      */     JFIFThumbPalette(BufferedImage param1BufferedImage) throws JFIFMarkerSegment.IllegalThumbException {
/* 1046 */       super(param1BufferedImage);
/* 1047 */       IndexColorModel indexColorModel = (IndexColorModel)this.thumbnail.getColorModel();
/* 1048 */       if (indexColorModel.getMapSize() > 256) {
/* 1049 */         throw new JFIFMarkerSegment.IllegalThumbException();
/*      */       }
/*      */     }
/*      */     
/*      */     int getLength() {
/* 1054 */       return this.thumbWidth * this.thumbHeight + 768;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     BufferedImage getThumbnail(ImageInputStream param1ImageInputStream, JPEGImageReader param1JPEGImageReader) throws IOException {
/* 1060 */       param1ImageInputStream.mark();
/* 1061 */       param1ImageInputStream.seek(this.streamPos);
/*      */       
/* 1063 */       byte[] arrayOfByte = new byte[768];
/* 1064 */       float f = 768.0F / getLength();
/* 1065 */       readByteBuffer(param1ImageInputStream, arrayOfByte, param1JPEGImageReader, f, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1070 */       DataBufferByte dataBufferByte = new DataBufferByte(this.thumbWidth * this.thumbHeight);
/* 1071 */       readByteBuffer(param1ImageInputStream, dataBufferByte
/* 1072 */           .getData(), param1JPEGImageReader, 1.0F - f, f);
/*      */ 
/*      */ 
/*      */       
/* 1076 */       param1ImageInputStream.read();
/* 1077 */       param1ImageInputStream.reset();
/*      */       
/* 1079 */       IndexColorModel indexColorModel = new IndexColorModel(8, 256, arrayOfByte, 0, false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1084 */       SampleModel sampleModel = indexColorModel.createCompatibleSampleModel(this.thumbWidth, this.thumbHeight);
/*      */ 
/*      */       
/* 1087 */       WritableRaster writableRaster = Raster.createWritableRaster(sampleModel, dataBufferByte, null);
/* 1088 */       return new BufferedImage(indexColorModel, writableRaster, false, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void write(ImageOutputStream param1ImageOutputStream, JPEGImageWriter param1JPEGImageWriter) throws IOException {
/* 1096 */       super.write(param1ImageOutputStream, param1JPEGImageWriter);
/*      */       
/* 1098 */       byte[] arrayOfByte1 = new byte[768];
/* 1099 */       IndexColorModel indexColorModel = (IndexColorModel)this.thumbnail.getColorModel();
/* 1100 */       byte[] arrayOfByte2 = new byte[256];
/* 1101 */       byte[] arrayOfByte3 = new byte[256];
/* 1102 */       byte[] arrayOfByte4 = new byte[256];
/* 1103 */       indexColorModel.getReds(arrayOfByte2);
/* 1104 */       indexColorModel.getGreens(arrayOfByte3);
/* 1105 */       indexColorModel.getBlues(arrayOfByte4);
/* 1106 */       for (byte b = 0; b < 'Ā'; b++) {
/* 1107 */         arrayOfByte1[b * 3] = arrayOfByte2[b];
/* 1108 */         arrayOfByte1[b * 3 + 1] = arrayOfByte3[b];
/* 1109 */         arrayOfByte1[b * 3 + 2] = arrayOfByte4[b];
/*      */       } 
/* 1111 */       param1ImageOutputStream.write(arrayOfByte1);
/* 1112 */       writePixels(param1ImageOutputStream, param1JPEGImageWriter);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class JFIFThumbJPEG
/*      */     extends JFIFThumb
/*      */   {
/* 1124 */     JPEGMetadata thumbMetadata = null;
/* 1125 */     byte[] data = null;
/*      */     
/*      */     private static final int PREAMBLE_SIZE = 6;
/*      */ 
/*      */     
/*      */     JFIFThumbJPEG(JPEGBuffer param1JPEGBuffer, int param1Int, JPEGImageReader param1JPEGImageReader) throws IOException {
/* 1131 */       super(param1JPEGBuffer);
/*      */       
/* 1133 */       long l = this.streamPos + (param1Int - 6);
/*      */ 
/*      */       
/* 1136 */       param1JPEGBuffer.iis.seek(this.streamPos);
/* 1137 */       this.thumbMetadata = new JPEGMetadata(false, true, param1JPEGBuffer.iis, param1JPEGImageReader);
/*      */       
/* 1139 */       param1JPEGBuffer.iis.seek(l);
/*      */       
/* 1141 */       param1JPEGBuffer.bufAvail = 0;
/* 1142 */       param1JPEGBuffer.bufPtr = 0;
/*      */     }
/*      */     
/*      */     JFIFThumbJPEG(Node param1Node) throws IIOInvalidTreeException {
/* 1146 */       if (param1Node.getChildNodes().getLength() > 1) {
/* 1147 */         throw new IIOInvalidTreeException("JFIFThumbJPEG node must have 0 or 1 child", param1Node);
/*      */       }
/*      */       
/* 1150 */       Node node = param1Node.getFirstChild();
/* 1151 */       if (node != null) {
/* 1152 */         String str = node.getNodeName();
/* 1153 */         if (!str.equals("markerSequence")) {
/* 1154 */           throw new IIOInvalidTreeException("JFIFThumbJPEG child must be a markerSequence node", param1Node);
/*      */         }
/*      */ 
/*      */         
/* 1158 */         this.thumbMetadata = new JPEGMetadata(false, true);
/* 1159 */         this.thumbMetadata.setFromMarkerSequenceNode(node);
/*      */       } 
/*      */     }
/*      */     
/*      */     JFIFThumbJPEG(BufferedImage param1BufferedImage) throws JFIFMarkerSegment.IllegalThumbException {
/* 1164 */       char c = 'က';
/* 1165 */       char c1 = '￷';
/*      */       try {
/* 1167 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(c);
/*      */         
/* 1169 */         MemoryCacheImageOutputStream memoryCacheImageOutputStream = new MemoryCacheImageOutputStream(byteArrayOutputStream);
/*      */ 
/*      */         
/* 1172 */         JPEGImageWriter jPEGImageWriter = new JPEGImageWriter(null);
/*      */         
/* 1174 */         jPEGImageWriter.setOutput(memoryCacheImageOutputStream);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1179 */         JPEGMetadata jPEGMetadata = (JPEGMetadata)jPEGImageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(param1BufferedImage), (ImageWriteParam)null);
/*      */ 
/*      */ 
/*      */         
/* 1183 */         MarkerSegment markerSegment = jPEGMetadata.findMarkerSegment(JFIFMarkerSegment.class, true);
/* 1184 */         if (markerSegment == null) {
/* 1185 */           throw new JFIFMarkerSegment.IllegalThumbException();
/*      */         }
/*      */         
/* 1188 */         jPEGMetadata.markerSequence.remove(markerSegment);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1211 */         jPEGImageWriter.write(new IIOImage(param1BufferedImage, null, jPEGMetadata));
/*      */         
/* 1213 */         jPEGImageWriter.dispose();
/*      */         
/* 1215 */         if (byteArrayOutputStream.size() > c1) {
/* 1216 */           throw new JFIFMarkerSegment.IllegalThumbException();
/*      */         }
/* 1218 */         this.data = byteArrayOutputStream.toByteArray();
/* 1219 */       } catch (IOException iOException) {
/* 1220 */         throw new JFIFMarkerSegment.IllegalThumbException();
/*      */       } 
/*      */     }
/*      */     
/*      */     int getWidth() {
/* 1225 */       int i = 0;
/*      */ 
/*      */       
/* 1228 */       SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)this.thumbMetadata.findMarkerSegment(SOFMarkerSegment.class, true);
/* 1229 */       if (sOFMarkerSegment != null) {
/* 1230 */         i = sOFMarkerSegment.samplesPerLine;
/*      */       }
/* 1232 */       return i;
/*      */     }
/*      */     
/*      */     int getHeight() {
/* 1236 */       int i = 0;
/*      */ 
/*      */       
/* 1239 */       SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)this.thumbMetadata.findMarkerSegment(SOFMarkerSegment.class, true);
/* 1240 */       if (sOFMarkerSegment != null) {
/* 1241 */         i = sOFMarkerSegment.numLines;
/*      */       }
/* 1243 */       return i;
/*      */     }
/*      */     
/*      */     private class ThumbnailReadListener
/*      */       implements IIOReadProgressListener {
/* 1248 */       JPEGImageReader reader = null; public void sequenceStarted(ImageReader param2ImageReader, int param2Int) {} public void sequenceComplete(ImageReader param2ImageReader) {}
/*      */       ThumbnailReadListener(JPEGImageReader param2JPEGImageReader) {
/* 1250 */         this.reader = param2JPEGImageReader;
/*      */       }
/*      */ 
/*      */       
/*      */       public void imageStarted(ImageReader param2ImageReader, int param2Int) {}
/*      */       
/*      */       public void imageProgress(ImageReader param2ImageReader, float param2Float) {
/* 1257 */         this.reader.thumbnailProgress(param2Float);
/*      */       }
/*      */       public void imageComplete(ImageReader param2ImageReader) {}
/*      */       
/*      */       public void thumbnailStarted(ImageReader param2ImageReader, int param2Int1, int param2Int2) {}
/*      */       
/*      */       public void thumbnailProgress(ImageReader param2ImageReader, float param2Float) {}
/*      */       
/*      */       public void thumbnailComplete(ImageReader param2ImageReader) {}
/*      */       
/*      */       public void readAborted(ImageReader param2ImageReader) {} }
/*      */     
/*      */     BufferedImage getThumbnail(ImageInputStream param1ImageInputStream, JPEGImageReader param1JPEGImageReader) throws IOException {
/* 1270 */       param1ImageInputStream.mark();
/* 1271 */       param1ImageInputStream.seek(this.streamPos);
/* 1272 */       JPEGImageReader jPEGImageReader = new JPEGImageReader(null);
/* 1273 */       jPEGImageReader.setInput(param1ImageInputStream);
/* 1274 */       jPEGImageReader
/* 1275 */         .addIIOReadProgressListener(new ThumbnailReadListener(param1JPEGImageReader));
/* 1276 */       BufferedImage bufferedImage = jPEGImageReader.read(0, (ImageReadParam)null);
/* 1277 */       jPEGImageReader.dispose();
/* 1278 */       param1ImageInputStream.reset();
/* 1279 */       return bufferedImage;
/*      */     }
/*      */     
/*      */     protected Object clone() {
/* 1283 */       JFIFThumbJPEG jFIFThumbJPEG = (JFIFThumbJPEG)super.clone();
/* 1284 */       if (this.thumbMetadata != null) {
/* 1285 */         jFIFThumbJPEG.thumbMetadata = (JPEGMetadata)this.thumbMetadata.clone();
/*      */       }
/* 1287 */       return jFIFThumbJPEG;
/*      */     }
/*      */     
/*      */     IIOMetadataNode getNativeNode() {
/* 1291 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("JFIFthumbJPEG");
/* 1292 */       if (this.thumbMetadata != null) {
/* 1293 */         iIOMetadataNode.appendChild(this.thumbMetadata.getNativeTree());
/*      */       }
/* 1295 */       return iIOMetadataNode;
/*      */     }
/*      */     
/*      */     int getLength() {
/* 1299 */       if (this.data == null) {
/* 1300 */         return 0;
/*      */       }
/* 1302 */       return this.data.length;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void write(ImageOutputStream param1ImageOutputStream, JPEGImageWriter param1JPEGImageWriter) throws IOException {
/* 1308 */       int i = this.data.length / 20;
/* 1309 */       if (i == 0) {
/* 1310 */         i = 1;
/*      */       }
/* 1312 */       int j = 0;
/* 1313 */       while (j < this.data.length) {
/* 1314 */         int k = Math.min(i, this.data.length - j);
/* 1315 */         param1ImageOutputStream.write(this.data, j, k);
/* 1316 */         j += i;
/* 1317 */         float f = j * 100.0F / this.data.length;
/* 1318 */         if (f > 100.0F) {
/* 1319 */           f = 100.0F;
/*      */         }
/* 1321 */         param1JPEGImageWriter.thumbnailProgress(f);
/*      */       } 
/*      */     }
/*      */     
/*      */     void print() {
/* 1326 */       System.out.println("JFIF thumbnail stored as JPEG");
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
/*      */   static void writeICC(ICC_Profile paramICC_Profile, ImageOutputStream paramImageOutputStream) throws IOException {
/* 1338 */     byte b1 = 2;
/*      */     
/* 1340 */     int i = "ICC_PROFILE".length() + 1;
/* 1341 */     byte b2 = 2;
/* 1342 */     int j = 65535 - b1 - i - b2;
/*      */ 
/*      */     
/* 1345 */     byte[] arrayOfByte = paramICC_Profile.getData();
/* 1346 */     int k = arrayOfByte.length / j;
/* 1347 */     if (arrayOfByte.length % j != 0) {
/* 1348 */       k++;
/*      */     }
/* 1350 */     byte b3 = 1;
/* 1351 */     int m = 0;
/* 1352 */     for (byte b4 = 0; b4 < k; b4++) {
/* 1353 */       int n = Math.min(arrayOfByte.length - m, j);
/* 1354 */       int i1 = n + b2 + i + b1;
/* 1355 */       paramImageOutputStream.write(255);
/* 1356 */       paramImageOutputStream.write(226);
/* 1357 */       MarkerSegment.write2bytes(paramImageOutputStream, i1);
/* 1358 */       byte[] arrayOfByte1 = "ICC_PROFILE".getBytes("US-ASCII");
/* 1359 */       paramImageOutputStream.write(arrayOfByte1);
/* 1360 */       paramImageOutputStream.write(0);
/* 1361 */       paramImageOutputStream.write(b3++);
/* 1362 */       paramImageOutputStream.write(k);
/* 1363 */       paramImageOutputStream.write(arrayOfByte, m, n);
/* 1364 */       m += n;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class ICCMarkerSegment
/*      */     extends MarkerSegment
/*      */   {
/* 1375 */     ArrayList chunks = null;
/* 1376 */     byte[] profile = null;
/*      */     
/*      */     private static final int ID_SIZE = 12;
/*      */     int chunksRead;
/*      */     int numChunks;
/*      */     
/*      */     ICCMarkerSegment(ICC_ColorSpace param1ICC_ColorSpace) {
/* 1383 */       super(226);
/* 1384 */       this.chunks = null;
/* 1385 */       this.chunksRead = 0;
/* 1386 */       this.numChunks = 0;
/* 1387 */       this.profile = param1ICC_ColorSpace.getProfile().getData();
/*      */     }
/*      */     
/*      */     ICCMarkerSegment(JPEGBuffer param1JPEGBuffer) throws IOException {
/* 1391 */       super(param1JPEGBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1395 */       param1JPEGBuffer.bufPtr += 12;
/* 1396 */       param1JPEGBuffer.bufAvail -= 12;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1402 */       this.length -= 12;
/*      */ 
/*      */       
/* 1405 */       int i = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr] & 0xFF;
/*      */       
/* 1407 */       this.numChunks = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr + 1] & 0xFF;
/*      */       
/* 1409 */       if (i > this.numChunks) {
/* 1410 */         throw new IIOException("Image format Error; chunk num > num chunks");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1415 */       if (this.numChunks == 1) {
/*      */         
/* 1417 */         this.length -= 2;
/* 1418 */         this.profile = new byte[this.length];
/* 1419 */         param1JPEGBuffer.bufPtr += 2;
/* 1420 */         param1JPEGBuffer.bufAvail -= 2;
/* 1421 */         param1JPEGBuffer.readData(this.profile);
/* 1422 */         JFIFMarkerSegment.this.inICC = false;
/*      */       } else {
/*      */         
/* 1425 */         byte[] arrayOfByte = new byte[this.length];
/*      */ 
/*      */         
/* 1428 */         this.length -= 2;
/* 1429 */         param1JPEGBuffer.readData(arrayOfByte);
/* 1430 */         this.chunks = new ArrayList();
/* 1431 */         this.chunks.add(arrayOfByte);
/* 1432 */         this.chunksRead = 1;
/* 1433 */         JFIFMarkerSegment.this.inICC = true;
/*      */       } 
/*      */     }
/*      */     
/*      */     ICCMarkerSegment(Node param1Node) throws IIOInvalidTreeException {
/* 1438 */       super(226);
/* 1439 */       if (param1Node instanceof IIOMetadataNode) {
/* 1440 */         IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)param1Node;
/* 1441 */         ICC_Profile iCC_Profile = (ICC_Profile)iIOMetadataNode.getUserObject();
/* 1442 */         if (iCC_Profile != null) {
/* 1443 */           this.profile = iCC_Profile.getData();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     protected Object clone() {
/* 1449 */       ICCMarkerSegment iCCMarkerSegment = (ICCMarkerSegment)super.clone();
/* 1450 */       if (this.profile != null) {
/* 1451 */         iCCMarkerSegment.profile = (byte[])this.profile.clone();
/*      */       }
/* 1453 */       return iCCMarkerSegment;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean addData(JPEGBuffer param1JPEGBuffer) throws IOException {
/* 1461 */       param1JPEGBuffer.bufPtr++;
/* 1462 */       param1JPEGBuffer.bufAvail--;
/*      */       
/* 1464 */       int i = (param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xFF) << 8;
/* 1465 */       i |= param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xFF;
/* 1466 */       param1JPEGBuffer.bufAvail -= 2;
/*      */       
/* 1468 */       i -= 2;
/*      */       
/* 1470 */       param1JPEGBuffer.bufPtr += 12;
/* 1471 */       param1JPEGBuffer.bufAvail -= 12;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1477 */       i -= 12;
/*      */ 
/*      */       
/* 1480 */       int j = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr] & 0xFF;
/* 1481 */       if (j > this.numChunks) {
/* 1482 */         throw new IIOException("Image format Error; chunk num > num chunks");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1487 */       int k = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr + 1] & 0xFF;
/* 1488 */       if (this.numChunks != k) {
/* 1489 */         throw new IIOException("Image format Error; icc num chunks mismatch");
/*      */       }
/*      */       
/* 1492 */       i -= 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1498 */       boolean bool = false;
/* 1499 */       byte[] arrayOfByte = new byte[i];
/* 1500 */       param1JPEGBuffer.readData(arrayOfByte);
/* 1501 */       this.chunks.add(arrayOfByte);
/* 1502 */       this.length += i;
/* 1503 */       this.chunksRead++;
/* 1504 */       if (this.chunksRead < this.numChunks) {
/* 1505 */         JFIFMarkerSegment.this.inICC = true;
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1512 */         this.profile = new byte[this.length];
/*      */ 
/*      */ 
/*      */         
/* 1516 */         int m = 0;
/* 1517 */         for (byte b = 1; b <= this.numChunks; b++) {
/* 1518 */           boolean bool1 = false;
/* 1519 */           for (byte b1 = 0; b1 < this.chunks.size(); b1++) {
/* 1520 */             byte[] arrayOfByte1 = this.chunks.get(b1);
/* 1521 */             if (arrayOfByte1[0] == b) {
/* 1522 */               System.arraycopy(arrayOfByte1, 2, this.profile, m, arrayOfByte1.length - 2);
/*      */ 
/*      */               
/* 1525 */               m += arrayOfByte1.length - 2;
/* 1526 */               bool1 = true;
/*      */             } 
/*      */           } 
/* 1529 */           if (!bool1) {
/* 1530 */             throw new IIOException("Image Format Error: Missing ICC chunk num " + b);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1535 */         this.chunks = null;
/* 1536 */         this.chunksRead = 0;
/* 1537 */         this.numChunks = 0;
/* 1538 */         JFIFMarkerSegment.this.inICC = false;
/* 1539 */         bool = true;
/*      */       } 
/* 1541 */       return bool;
/*      */     }
/*      */     
/*      */     IIOMetadataNode getNativeNode() {
/* 1545 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("app2ICC");
/* 1546 */       if (this.profile != null) {
/* 1547 */         iIOMetadataNode.setUserObject(ICC_Profile.getInstance(this.profile));
/*      */       }
/* 1549 */       return iIOMetadataNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void write(ImageOutputStream param1ImageOutputStream) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void print() {
/* 1561 */       printTag("ICC Profile APP2");
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JFIFMarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */